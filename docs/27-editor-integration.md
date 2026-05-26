# 27 编辑器集成架构

> 基于 class 文件常量池静态分析

## 核心服务架构

```
EditorManagerService (接口)
    └── EditorManagerServiceImpl (实现)
        ├── 管理 CodeEditorInlay 生命周期
        ├── 创建/销毁 Inlay 渲染
        ├── 处理补全接受/拒绝/切换
        └── 协调 TipInlayRenderer 渲染

RequestTipService (接口)
    └── RequestTipServiceImpl (实现)
        ├── 构建代码补全请求
        ├── 发送 WebSocket 消息到 Agent
        ├── 处理流式响应
        └── 管理请求去重和取消
```

## EditorManagerServiceImpl — 编辑器管理核心

```
核心依赖:
  TipInlayRenderer        — Inlay 渲染器
  CodeEditorInlay         — 补全数据模型
  CodeInlayList           — 补全列表
  EditorUtil              — 编辑器工具
  CancelRequestTip        — 取消补全请求
  OperateActionEnum       — 操作枚举
  CodeTipType             — 补全类型
  AICodeSettingsState     — 设置
  PluginWebsocketClient   — WebSocket 通信
  RequestTipServiceImpl   — 补全请求

关键方法:
  hasTipInlays(Editor): boolean     — 检查是否有活跃 Inlay
  disposeTips(Editor, OperateActionEnum) — 清除所有 Inlay
  cycleNext(Editor)                 — 切换下一个补全建议
  cyclePrevious(Editor)             — 切换上一个补全建议
  acceptInlay(Editor)               — 接受当前补全
  acceptWord(Editor)                — 接受一个词
  acceptLine(Editor)                — 接受一行
  showTip(Editor, CodeTip)          — 显示补全 Inlay

内部类:
  $1 — CaretListener (光标移动时清除 Inlay)
  $2 — DocumentListener (文档变更时清除 Inlay)
  $3 — VisibleAreaListener (滚动时更新 Inlay 位置)
```

## RequestTipServiceImpl — 补全请求服务

```
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

关键方法:
  requestTip(Editor, CodeTipRequestType) — 发送补全请求
  cancelRequest(String requestId)        — 取消请求
  handleResponse(ResponseStreamDto)      — 处理流式响应

请求构建流程:
  1. 获取当前文件路径、语言、光标位置
  2. 获取光标上方代码 (prefixCode) 和下方代码 (suffixCode)
  3. 获取文件结构信息 (structure, imports)
  4. 获取相似代码 (similarStr)
  5. 构建 CodeTipRequestDto
  6. 填充 MessageDto (command = code_complete)
  7. 通过 WebSocket 发送
```

## DocumentActionTracker — 文档操作追踪

```
核心依赖:
  EditorManagerServiceImpl — 编辑器管理
  RequestTipServiceImpl    — 补全请求
  CodeTipRequestType       — 补全请求类型
  CommandCache             — 命令缓存
  AICodeSettingsState      — 设置

功能:
  追踪用户在编辑器中的操作序列，用于判断是否触发自动补全:
  - 字符输入 → 可能触发自动补全
  - 删除操作 → 可能触发自动补全
  - 选择操作 → 不触发
  - 粘贴操作 → 不触发
  - 连续快速输入 → 防抖处理
```

## InlayRendering — Inlay 渲染系统

```
核心依赖:
  TipInlayRenderer         — 渲染器
  CodeEditorInlay          — 数据模型
  CodeInlayList            — 补全列表
  EditorUtil               — 编辑器工具
  CodeTipType              — 补全类型
  AICodeSettingsState      — 设置
  HighlighterUtil          — 高亮工具
  IndentLineUtil           — 缩进工具
  PluginInfoUtils          — 插件信息

渲染类型:
  Inline          — 行内补全 (灰色文本)
  AfterLineEnd    — 行尾补全 (灰色文本)
  Block           — 块级补全 (多行灰色文本)

渲染流程:
  1. Agent 返回补全文本
  2. CodeCompleteService 解析响应
  3. 创建 CodeEditorInlay 对象
  4. TipInlayRenderer 创建 InlayModel 元素
  5. 设置灰色字体 + 斜体样式
  6. 添加到 Editor InlayModel
```

## TipInlayRenderer — 补全渲染器

```
核心依赖:
  CodeEditorInlay       — 数据模型
  CodeInlayList         — 补全列表
  EditorUtil            — 编辑器工具
  CodeTipType           — 补全类型
  HighlighterUtil       — 高亮工具
  IndentLineUtil        — 缩进工具
  Font                  — 字体
  Style                 — 样式
  RoundLineBorder       — 圆角边框
  ActionButton          — 操作按钮

渲染元素:
  灰色文本 Inlay       — 补全建议文本
  操作按钮面板         — Tab/ESC 提示
  状态指示器           — 加载中/错误状态
```

## CodeEditorInlay — 补全数据模型

```
字段:
  type: CodeTipType          — 补全类型
  editorOffset: int          — 编辑器偏移量
  completionLines: List<String> — 补全行列表

方法:
  getLines(): List<String>   — 获取补全行
  setLines(List<String>)     — 设置补全行
  getEditorOffset(): int     — 获取偏移量
  getType(): CodeTipType     — 获取类型
  toString(): String         — 调试输出
```

## CodeInlayList — 补全列表

```
管理多个补全建议:
  current: CodeEditorInlay   — 当前显示的补全
  alternatives: List<CodeEditorInlay> — 备选补全列表
  index: int                 — 当前索引

方法:
  cycleNext(): CodeEditorInlay   — 切换到下一个
  cyclePrevious(): CodeEditorInlay — 切换到上一个
  getCurrent(): CodeEditorInlay  — 获取当前
  add(CodeEditorInlay)           — 添加补全
  clear()                        — 清除所有
```

## 补全交互流程

```
1. 用户输入 → AutoCodeGenerateListener 触发
2. RequestTipServiceImpl 构建 CodeTipRequestDto
3. WebSocket 发送 code_complete 命令到 Agent
4. Agent 转发到云端，返回流式响应
5. CodeCompleteService 解析响应
6. 创建 CodeEditorInlay + CodeInlayList
7. TipInlayRenderer 渲染到 Editor

用户交互:
  Tab     → acceptInlay() → 替换文本 → log_accept
  Esc     → disposeTips(EscReject) → 清除 Inlay → log_reject
  Alt+]   → cycleNext() → 切换下一个建议
  Alt+[   → cyclePrevious() → 切换上一个建议
  Ctrl+→  → acceptWord() → 接受一个词
  Ctrl+↓  → acceptLine() → 接受一行

日志上报:
  log_display — Inlay 显示时上报
  log_accept  — 用户接受时上报
  log_reject  — 用户拒绝时上报
```

## 请求取消机制

```
CancelRequestTip — 取消单个请求
RequestsCancelledService — 管理所有取消请求

流程:
  1. 用户按 Esc 或切换文件
  2. EditorManagerServiceImpl.disposeTips()
  3. CancelRequestTip.cancel(requestId)
  4. RequestsCancelledService 记录取消
  5. WebSocket 发送 ABORT 命令到 Agent
  6. Agent 取消 HTTP fetch (AbortController)
  7. 返回取消确认
```
