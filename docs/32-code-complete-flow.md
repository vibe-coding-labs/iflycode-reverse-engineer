# 32 代码补全完整流程分析

> 基于 class 文件常量池静态分析 + 已有文档交叉验证

## 流程概览

```
用户输入字符
    │
    ▼
[1] AutoCodeGenerateListener (CommandListener)
    │  触发条件: Document 变更 + 自动补全启用 + 非选择/非命令模式
    ▼
[2] DocumentActionTracker$ActionListener
    │  追踪操作类型: 字符输入/删除/粘贴
    │  防抖处理: 连续快速输入不重复触发
    │  检查: InlineChatService 是否活跃 (活跃则不触发)
    │  检查: EditorUtil.isSelectedEditor (是否当前编辑器)
    ▼
[3] RequestTipServiceImpl.requestTip(Editor, CodeTipRequestType)
    │  构建 CodeTipRequestDto:
    │    - prefixCode: 光标上方代码
    │    - suffixCode: 光标下方代码
    │    - structure: 文件结构信息
    │    - imports: 导入信息
    │    - similarStr: 相似代码
    │    - language: 文件语言
    │    - filePath: 文件路径
    │    - cursorOffset: 光标位置
    ▼
[4] 构建 MessageDto
    │  command = "code_complete"
    │  data = CodeTipRequestDto (JSON 序列化)
    ▼
[5] PluginWebsocketClient.send(MessageDto)
    │  WebSocket 发送到 Agent (ws://127.0.0.1:&#123;port&#125;/ws/idea)
    ▼
[6] Agent 转发到云端
    │  HTTPS POST → 星火 API
    │  流式响应 (SSE)
    ▼
[7] 流式响应 → ResponseStreamDto
    │  字段: &#123; requestId, text, ended, data &#125;
    │  data → ResponseData → 解析为补全文本
    ▼
[8] AgentCodeTipList 处理响应
    │  将 ResponseStreamDto.ResponseData 转换为:
    │    - GetTipsResult.Tip (补全结果)
    │    - AgentCodeTip (Agent 补全数据)
    │    - CodeInlayList (Inlay 列表)
    ▼
[9] EditorManagerServiceImpl 接收补全
    │  内部类 $F (Flow.Subscriber):
    │    - onNext(List&lt;CodeInlayList&gt;): 处理补全数据
    │    - onComplete: 补全完成
    │    - onError: 处理 RequestTimeoutException
    ▼
[10] RequestResultList 管理
    │  存储多个补全建议:
    │    - request: EditorRequestService (请求信息)
    │    - inlayLists: ObjectLinkedOpenHashSet&lt;CodeInlayList&gt;
    │    - index: 当前显示索引
    │    - maxShownIndex: 最大显示索引
    │    - hasOnDemandCodeTips: 按需补全标志
    ▼
[11] TipInlayRenderer 渲染
    │  创建 IntelliJ Inlay 元素:
    │    - 灰色字体 + 斜体样式
    │    - 类型: Inline / AfterLineEnd / Block
    │    - ActionButton: Tab/ESC 提示
    ▼
[12] InlayRendering 样式
    │  TextAttributes 设置:
    │    - 前景色: 灰色
    │    - 效果类型: EffectType
    │    - 字体: 等宽字体
    ▼
[13] 用户交互
    │  Tab     → acceptInlay() → 替换文本
    │  Esc     → disposeTips(EscReject) → 清除 Inlay
    │  Alt+]   → cycleNext() → 切换下一个建议
    │  Alt+[   → cyclePrevious() → 切换上一个建议
    │  Ctrl+→  → acceptWord() → 接受一个词
    │  Ctrl+↓  → acceptLine() → 接受一行
```

## 核心类详解

### RequestTipServiceImpl — 补全请求服务

```
实现: RequestTipService (接口)

核心依赖:
  PluginWebsocketClient    — WebSocket 通信
  MessageDto               — 消息 DTO
  CodeTipRequestDto        — 补全请求 DTO
  EditorManagerServiceImpl — 编辑器管理
  DocumentActionTracker    — 文档操作追踪
  CommandCache             — 命令缓存
  LanguageInfoSupport      — 语言信息
  AICodeSettingsState      — 设置
  AICodeRequestSettings    — 请求设置
  CodeTipType              — 补全类型
  RequestCancellable       — 可取消请求
  RequestsCancelledService — 取消服务

内部类:
  $j — dealAgentTips 处理器 (Agent 补全结果处理)

关键方法:
  requestTip(Editor, CodeTipRequestType)
    — 发送补全请求

  cancelRequest(String requestId)
    — 取消请求

  handleResponse(ResponseStreamDto)
    — 处理流式响应

  dealAgentTips(...)
    — 处理 Agent 返回的补全数据

  fetchCachedTips(...)
    — 获取缓存的补全

  fetchTips(...)
    — 获取补全 (后台线程)

请求构建:
  1. EditorRequestService 获取编辑器状态
  2. 获取 prefixCode (光标上方代码)
  3. 获取 suffixCode (光标下方代码)
  4. 获取文件结构信息 (structure, imports)
  5. 获取相似代码 (similarStr)
  6. 获取语言信息 (language, filePath)
  7. 构建 CodeTipRequestDto
  8. 填充 MessageDto (command = "code_complete")
  9. 通过 WebSocket 发送
```

### EditorManagerServiceImpl — 编辑器管理核心

```
实现: EditorManagerService (接口)

核心字段:
  CACHE_KEY_LAST_REQUEST: Key&lt;RequestResultList&gt;
    — 编辑器用户数据中的缓存键
  KEY_LAST_REQUEST: Key
    — 最后请求键
  ACCEPT_CODE_FOR_WORD: OperateActionEnum
    — 逐词接受操作
  ACCEPT_CODE_FOR_LINE: OperateActionEnum
    — 逐行接受操作
  requestAlarm: CancelRequestTip
    — 请求定时器 (防抖/超时)
  docChangeCount: AtomicInteger
    — 文档变更计数
  keyMap: Map<String, String>
    — 快捷键映射

内部类:
  $B — CodeTipType 枚举数组 (补全类型)
  $F — Flow.Subscriber<List&lt;CodeInlayList&gt;>
    — 流式补全订阅者
    方法:
      onNext(List&lt;CodeInlayList&gt;) — 处理补全数据
      onComplete() — 补全完成
      onError(Throwable) — 处理错误 (RequestTimeoutException)

关键方法:
  hasTipInlays(Editor): boolean
    — 检查是否有活跃 Inlay

  disposeTips(Editor, OperateActionEnum)
    — 清除所有 Inlay
    - EscReject → 拒绝并清除
    - Cycling → 切换建议
    - Accept → 接受并清除

  cycleNext(Editor) / cyclePrevious(Editor)
    — 切换下一个/上一个补全建议
    - 从 RequestResultList 获取
    - 重新渲染 TipInlayRenderer

  acceptInlay(Editor)
    — 接受当前补全 (Tab)
    - 获取当前 CodeInlayList
    - 替换文本到编辑器
    - 日志上报: log_accept

  acceptWord(Editor)
    — 接受一个词 (Ctrl+→)
    - 从补全文本取第一个词
    - 插入到编辑器

  acceptLine(Editor)
    — 接受一行 (Ctrl+↓)
    - 从补全文本取第一行
    - 插入到编辑器

  showTip(Editor, CodeTip)
    — 显示补全 Inlay

  showPreviousInlaySet(Editor)
    — 显示上一组补全建议

  editorChanged(Editor)
    — 编辑器变更通知
```

### DocumentActionTracker — 文档操作追踪

```
核心字段:
  executingForcedCodeGenerateAction: AtomicInteger
    — 强制代码生成执行计数

内部类:
  $ActionListener — 操作监听器
    核心依赖:
      EditorKt              — Kotlin 编辑器工具
      InlineChatService     — 内联聊天服务
      EditorManagerService  — 编辑器管理
      EditorUtil            — 编辑器工具
      FileInfo              — 文件信息
      CodeTipRequestType    — 补全请求类型
      EditorUtils           — 编辑器工具

    字段:
      EDITOR: AtomicReference&lt;Editor&gt;
        — 当前编辑器引用

    方法:
      editorChanged(Editor)
        — 编辑器变更处理
        - 检查 InlineChatService 是否活跃
        - 检查 EditorUtil.isSelectedEditor
        - 触发补全请求

      exitForcedCodeGenerateAction()
        — 退出强制代码生成模式

      getExecutingForcedCodeGenerateAction()
        — 获取强制执行计数

    追踪的操作:
      - DeleteAction (删除键)
      - BackspaceAction (退格键)
      - 字符输入
      - 粘贴操作
```

### RequestResultList — 补全结果列表

```
核心字段:
  request: EditorRequestService
    — 关联的补全请求
  inlayLists: ObjectLinkedOpenHashSet&lt;CodeInlayList&gt;
    — 补全 Inlay 列表 (有序集合, fastutil)
  inlayLock: Object
    — Inlay 操作锁
  index: int
    — 当前显示索引
  maxShownIndex: int
    — 最大显示索引
  hasOnDemandCodeTips: boolean
    — 是否有按需补全

关键方法:
  getCurrentCodeTip(): CodeInlayList
    — 获取当前显示的补全

  getPrevCodeTip(): CodeInlayList
    — 获取上一个补全

  addInlays(CodeInlayList)
    — 添加补全建议

  getRequest(): EditorRequestService
    — 获取关联请求

  getInlayLists(): ObjectLinkedOpenHashSet&lt;CodeInlayList&gt;
    — 获取所有补全列表

  toString(): "EditorRequestResultList(request=, inlayLock=, inlayLists=, index=, maxShownIndex=, hasOnDemandCodeTips=)"
```

### TipInlayRenderer — 补全渲染器

```
实现: TipRenderer (接口)

核心字段:
  lines: List&lt;String&gt;
    — 补全行列表
  content: String
    — 补全内容
  type: CodeTipType
    — 补全类型 (Inline/AfterLineEnd/Block)
  textAttributes: TextAttributes
    — 文本属性 (灰色/斜体)
  cachedWidth: int
    — 缓存宽度
  cachedHeight: int
    — 缓存高度
  request: EditorRequestService
    — 关联请求
  inlay: Inlay&lt;TipRenderer&gt;
    — IntelliJ Inlay 元素

关键方法:
  getType(): CodeTipType
    — 获取补全类型

  getContentLines(): List&lt;String&gt;
    — 获取补全行

  getInlay(): Inlay
    — 获取 Inlay 元素

  setInlay(Inlay)
    — 设置 Inlay 元素

渲染流程:
  1. 根据 CodeTipType 选择渲染位置
  2. 创建 InlayModel 元素
  3. 设置灰色字体 + 斜体样式
  4. 添加 ActionButton (Tab/ESC 提示)
  5. 添加到 Editor InlayModel
```

### CancelRequestTip — 请求取消定时器

```
核心字段:
  alarm: Alarm (IntelliJ)
    — IntelliJ Alarm 定时器
  request: Object
    — 当前请求对象

核心依赖:
  GitReviewService — Git 评审服务 (H() 字符串解码)
  FileExtensionLanguageDetails — 文件扩展语言 (H() 字符串解码)

关键方法:
  cancelAllRequests()
    — 取消所有请求

  cancelAllAndAddRequest(Runnable)
    — 取消所有并添加新请求 (防抖)

  addRequest(Runnable, int delayMillis)
    — 添加延迟请求
```

### EditorRequestService — 编辑器请求状态

```
核心字段:
  requestId: String
    — 请求 ID
  offset: int
    — 光标偏移量
  documentContent: String
    — 文档内容
  completionType: CodeTipRequestType
    — 补全类型
  sessionController: SessionController
    — 会话控制器
  requestTimestamp: long
    — 请求时间戳
  disposable: Disposable
    — 可释放资源

关键方法:
  getCurrentDocumentPrefix(): String
    — 获取光标上方代码 (prefixCode)

  getDocumentContent(): String
    — 获取完整文档内容

  getOffset(): int
    — 获取光标偏移量

  getFileLanguage(): String
    — 获取文件语言

  getLineInfo(): LineInfo
    — 获取行信息

  equalsRequest(EditorRequestService): boolean
    — 判断是否与另一请求相同 (去重)

  getRequestId(): String
    — 获取请求 ID

  isSelected(): boolean
    — 是否为当前选中编辑器

  isUseTabIndents(): boolean
    — 是否使用 Tab 缩进

  getTabWidth(): int
    — 获取 Tab 宽度
```

## 补全类型 (CodeTipType)

```
Inline          — 行内补全 (灰色文本, 光标位置)
AfterLineEnd    — 行尾补全 (灰色文本, 当前行末尾)
Block           — 块级补全 (多行灰色文本)
```

## 请求类型 (CodeTipRequestType)

```
Automatic  — 自动触发 (用户输入)
Interact   — 交互触发 (快捷键)
Forced     — 强制触发
Manual     — 手动触发
```

## 请求取消机制

```
1. 用户按 Esc → Q.ua (EditorActionHandler)
   → EditorManagerService.disposeTips(editor, OperateActionEnum.EscReject)

2. 用户切换文件 → CodeFileEditorManagerListener
   → EditorManagerService.disposeTips()

3. IntelliJ 补全弹出 → CodeLookupManagerListener
   → EditorManagerService.disposeTips(editor, OperateActionEnum)
   (防止 IntelliJ 补全和 iFlyCode 补全同时显示)

4. 文档变更 → DocumentActionTracker
   → CancelRequestTip.cancelAllAndAddRequest()
   (取消旧请求, 添加新请求)

5. 请求超时 → RequestTimeoutException
   → EditorManagerServiceImpl$F.onError()
   → 清除 Inlay

6. WebSocket 断开 → PluginWebsocketClient
   → CancelRequestTip.cancelAllRequests()

7. 插件卸载 → AICodeUnloadPluginListener
   → EditorManagerService.disposeTips() + CodeCompleteService.stop()
```

## 日志上报

```
log_display — Inlay 显示时上报
  包含: requestId, 补全类型, 文件语言, 触发方式

log_accept  — 用户接受时上报
  包含: requestId, 接受方式 (Tab/Word/Line), 补全长度

log_reject  — 用户拒绝时上报
  包含: requestId, 拒绝方式 (Esc/Cycling/文件切换)
```

## 与 Agent 通信协议

```
请求 (IDE → Agent):
  WebSocket 消息:
    command: "code_complete"
    data: &#123;
      requestId: "uuid",
      prefixCode: "...",
      suffixCode: "...",
      structure: "...",
      imports: "...",
      similarStr: "...",
      language: "java",
      filePath: "/path/to/file.java",
      cursorOffset: 1234,
      lineInfo: &#123; ... &#125;
    &#125;

响应 (Agent → IDE, 流式):
  ResponseStreamDto:
    requestId: "uuid"
    text: "补全文本片段"
    ended: false/true
    data: ResponseData &#123;
      // 补全元数据
    &#125;
```

## OpenTelemetry 集成

```
EditorManagerService 引用 OpenTelemetryUtil:
  - 补全请求开始时创建 span
  - 补全响应到达时记录事件
  - 补全接受/拒绝时结束 span
  - traceparent 传播到 Agent → 云端
```

## 补全缓存

```
TipCache:
  - 缓存最近的补全结果
  - isLatestPrefix(): 判断当前输入是否为缓存前缀
  - updateLatest(): 更新缓存
  - clear(): 清除缓存
  - getLatest(): 获取最新缓存

CommandCache:
  - 命令结果缓存
  - 避免重复请求相同补全
```

## 补全与内联聊天冲突处理

```
DocumentActionTracker$ActionListener:
  - 检查 InlineChatService 是否活跃
  - 如果内联聊天活跃, 不触发自动补全

CodeTipTypedHandlerDelegate:
  - TypedHandlerDelegate 实现
  - 检查 AICodeRequestSettings.isShowIdeCodeTips
  - 检查 CodeGenerateRequestState 状态
  - 防止补全与内联聊天同时显示

TipTypedHandlerDelegate:
  - 另一个 TypedHandlerDelegate
  - 检查 RequestTimeoutException
  - 检查 ChatInputController 状态
  - 检查 CommandProcessor.getCurrentCommand()
```
