# 83. Listener 完整反编译分析

## 1. Listener 类完整清单

| # | 类名 | 实现接口 | 行数 | 角色 |
|---|------|---------|------|------|
| 1 | `AICodeUnloadPluginListener` | `DynamicPluginListener` | 6.1k | 插件卸载处理 |
| 2 | `ApplicationStartupListener` | `AppLifecycleListener` | 2.3k | 应用生命周期 |
| 3 | `AutoCodeGenerateListener` | `CommandListener` | 55k | 代码补全命令监听核心 |
| 4 | `AutoCodeGenerateListener$Q` | (data class) | 727b | 编辑器位置+修改序列快照 |
| 5 | `AutoCodeGenerateListener$T` | (data class) | 6.0k | 编辑器+文档修改序列快照 |
| 6 | `CodeEditorListener` | `EditorFactoryListener` | 5.3k | 编辑器工厂事件 |
| 7 | `CodeEditorListener$CodeSelectionListener` | `SelectionListener` | 4.7k | 文本选择变更 |
| 8 | `CodeFileEditorManagerListener` | `FileEditorManagerListener` | 30k | 文件编辑器事件核心 |
| 9 | `CodeFileEditorManagerListener$01` | `DocumentListener` | 13k | 文档变更监听 |
| 10 | `CodeLookupManagerListener` | `LookupManagerListener` | 8.1k | 代码补全弹窗事件 |
| 11 | `CodeLookupManagerListener$01` | `LookupListener` | 14k | 补全项选择事件 |
| 12 | `CommitHandlerFactory` | `CheckinHandlerFactory` | 3.9k | VCS 提交处理工厂 |
| 13 | `CommitHandlerFactory$o` | `CheckinHandler` | 37k | 提交处理实现(单元测试收集) |
| 14 | `FileWatchedAdapter` | `FileDocumentManagerListener` | 5.6k | 文件保存监听 |
| 15 | `GitBranchChangeListener` | (plain class + MessageBus) | 79k | Git 分支变更核心 |
| 16 | `GitBranchChangeListener$H` | `NotificationAction` | 5.8k | "忽略授权"通知动作 |
| 17 | `GitBranchChangeListener$R` | `NotificationAction` | 7.0k | "打开知识库"通知动作 |
| 18 | `GitBranchChangeListener$b` | `NotificationAction` | 5.8k | "授权仓库"通知动作 |
| 19 | `PluginDocumentListener` | `ProjectComponent` | 4.9k | 项目文档生命周期 |
| 20 | `PluginManagerListener` | `ProjectManagerListener` | 10k | 项目关闭处理 |
| 21 | `ThemeChangeListener` | `ApplicationComponent` | 18k | IDE 主题变更 |

**总计**: 21 个类, 约 6278 行字节码

---

## 2. 各 Listener 完整反编译结果

### 2.1 AICodeUnloadPluginListener

```
public class com.aicode.listener.AICodeUnloadPluginListener
  implements com.intellij.ide.plugins.DynamicPluginListener

  // --- 字段 ---
  (无实例字段)

  // --- 方法 ---

  // 插件卸载前回调
  public void beforePluginUnload(IdeaPluginDescriptor, boolean):
    1. 检查插件描述符是否为 null (否则抛 IllegalArgumentException)
    2. 调用 PluginInfoUtils.isAICodePlugin(descriptor) 判断是否为 iFlyCode 插件
    3. 如果是:
       a. 调用 BC() 遍历所有有效项目
       b. 对每个项目的选中编辑器:
          - 获取 EditorManagerService.getInstance()
          - 调用 disposeTips(editor, OperateActionEnum.UserOperate) 关闭提示
       c. 通过反射调用 MessageBundle.INSTANCE 的 getDynamicBundle() 方法
          (使用解混淆字符串获取方法名)
    4. 异常处理: 捕获 Exception 后静默返回

  // 遍历所有有效项目，关闭编辑器提示
  private static void BC():
    1. ApplicationUtil.findValidProjects() 获取所有打开项目
    2. 遍历每个项目:
       a. FileEditorManager.getInstance(project).getSelectedTextEditor()
       b. 如果编辑器非 null 且未 disposed:
          - EditorManagerService.getInstance().disposeTips(editor, OperateActionEnum.UserOperate)
```

**交互关系**:
- `PluginInfoUtils.isAICodePlugin()` -- 判断插件标识
- `EditorManagerService.disposeTips()` -- 关闭代码提示
- `MessageBundle.INSTANCE` -- 反射获取动态 Bundle

---

### 2.2 ApplicationStartupListener

```
public class com.aicode.listener.ApplicationStartupListener
  implements com.intellij.ide.AppLifecycleListener

  // --- 字段 ---
  private static final Logger enum  // 日志记录器

  // --- 方法 ---

  // 应用即将关闭
  public void appWillBeClosed(boolean):
    1. Logger.info(解混淆: "应用即将关闭, 正在清理资源")
    2. PluginStartupActivity.clear()  // 清理启动活动资源

  // 应用正在关闭
  public void appClosing():
    1. Logger.info(解混淆: "应用正在关闭")
    2. PluginStartupActivity.clear()
```

**交互关系**:
- `PluginStartupActivity.clear()` -- 清理插件启动时创建的资源(WebSocket连接、定时器等)

---

### 2.3 AutoCodeGenerateListener (核心 -- 55KB)

```
public class com.aicode.listener.AutoCodeGenerateListener
  implements com.intellij.openapi.command.CommandListener

  // --- 静态字段 ---
  public static final AtomicBoolean commandNameTab       // Tab 键触发标记
  public static final AtomicBoolean commandNameCtrlZ     // Ctrl+Z 撤销标记
  public static final AtomicBoolean ignoreLookupApply    // 忽略补全应用标记
  public static final AtomicBoolean ignoreApply          // 忽略应用标记
  public static final AtomicBoolean commandName          // 命令名匹配标记
  public static final AtomicBoolean isImitationDealFlag  // 仿写处理标记
  public static final AtomicBoolean inlineChatOperate    // Inline Chat 操作标记
  public static final AtomicBoolean isImitationBuryingPoint  // 仿写埋点标记

  // --- 实例字段 ---
  private final AtomicReference<AutoCodeGenerateListener$T> case  // 撤销快照
  private final Project final           // 关联项目
  private final AtomicBoolean try       // 命令进行中标记
  public final AtomicInteger atomicOperate  // 操作计数器

  // --- 私有静态字段 ---
  private static final Key<CommandCache> float   // 命令缓存 Key
  private static final Logger byte               // SLF4J 日志
  private static final Key<AutoCodeGenerateListener$Q> enum  // 位置快照 Key

  // --- 关键方法 ---

  // 获取文档修改序列号
  private static long wb(Document):
    - 如果 Document instanceof DocumentEx: 返回 getModificationSequence()
    - 否则: 返回 getModificationStamp()

  // 创建编辑器位置快照
  private static AutoCodeGenerateListener$Q Bb(Editor):
    - new Q(wb(editor.getDocument()), editor.getCaretModel().getVisualPosition())

  // 命令开始
  public void commandStarted(CommandEvent):
    1. atomicOperate.getAndIncrement()
    2. 如果 atomicOperate > 0: 记录日志并返回(嵌套命令)
    3. 如果 ignoreApply 为 true: 记录日志并返回
    4. 获取当前选中编辑器:
       a. 如果有文本选择:
          - 创建 CommandCache, 设置 startSelected=true, 记录 selectionStart
          - 存入编辑器 UserData (Key=float)
       b. 如果无文本选择:
          - 清除编辑器 UserData (Key=float)
    5. 检查是否应跳过(BB方法):
       - apiKey 为空 -> 跳过
       - autoTrigger 设置关闭 -> 跳过
    6. 设置 try = true (命令进行中)
    7. 存储编辑器位置快照到 UserData (Key=enum)
    8. 检测命令名:
       - commandName = "completion" (equalsIgnoreCase 解混淆)
       - isImitationDealFlag = "imitation" (equalsIgnoreCase 解混淆)
       - commandNameCtrlZ = 以 "Undo" 或 "撤销" 开头
       - commandNameTab = "Tab" (equalsIgnoreCase 解混淆)
    9. 调用 bb(editor) 处理撤销相关逻辑

  // 命令完成
  public void commandFinished(CommandEvent):
    1. atomicOperate.decrementAndGet()
    2. 如果 atomicOperate > 0: 记录日志并返回
    3. 如果 ignoreLookupApply 为 true:
       - 重置 ignoreLookupApply = false
       - 重置 ignoreApply = false
       - 返回
    4. 如果 ignoreApply 为 true:
       - 重置 ignoreApply = false
       - 返回
    5. 如果 inlineChatOperate 为 true:
       - 重置 inlineChatOperate = false
       - 返回
    6. 获取当前编辑器，检查:
       a. 无文本选择
       b. 有 CommandCache 且 startSelected=true
       c. 光标位置 > startSelectedStartOffset
       d. 选中文本为换行符 -> 清除缓存并返回
    7. 获取 EditorManagerService 实例
    8. 检查命令名标记(commandName)
    9. 检查是否应跳过(BB方法)
    10. 如果正在执行强制代码生成 -> disposeTips + exitForcedCodeGenerateAction
    11. 如果 commandNameTab 为 true -> 重置并返回
    12. 如果 try 为 true 且编辑器非 null:
        a. 检查语言支持
        b. 检查编辑器可用性
        c. 获取位置快照，比较新旧快照:
           - 如果位置改变(xA) -> 设置 isImitationBuryingPoint=true, editorChanged(Automatic)
           - 如果命令为 EmptyRunnable -> editorChanged(Automatic, false)
           - 如果仅修改序列改变(Va) -> disposeTips(CaretChange)

  // 撤销透明操作开始
  public void undoTransparentActionStarted():
    1. 获取当前编辑器
    2. 创建编辑器+文档快照 (Rc方法)
    3. 存入 case 字段 (AtomicReference)

  // 撤销透明操作完成
  public void undoTransparentActionFinished():
    1. 获取 case 中的快照
    2. 清除 case (设为 null)
    3. 获取当前编辑器
    4. 如果编辑器匹配快照中的编辑器且文档修改序列不同:
       - EditorManagerService.isAvailable(editor)
       - hasTipInlays(editor)
       - editorChanged(Forced, false)

  // 检测是否应跳过命令
  private boolean BB(CommandEvent):
    - apiKey 为空 -> 跳过
    - autoTrigger 设置关闭 -> 跳过
    - 否则不跳过

  // 处理撤销时的 APM 跨度
  private void bb(Editor):
    1. 如果 commandNameCtrlZ 为 true:
       a. 获取 RequestTipServiceImpl.LATEST_RESPONSE_DATA
       b. 获取 CODE_TIP_MAP 中的请求
       c. 如果存在: 设置 Span 属性 COMPLETE_RESULT, 结束 Span
       d. 从 CODE_TIP_MAP 和 LAST_REQUEST 中移除
    2. 异常处理: Logger.error

  // 位置快照比较
  private static boolean Va(Q old, Q new):
    - 如果 new.visualPosition != old.visualPosition -> 返回 true (位置改变)
    - 否则返回 false

  // 修改序列比较
  private static boolean xA(Q old, Q new):
    - 如果 new.modificationSequence != old.modificationSequence -> 返回 true
    - 否则返回 false
```

**交互关系**:
- `EditorManagerService` -- 代码补全核心服务
  - `editorChanged(editor, requestType, isCommandName)` -- 触发代码补全请求
  - `disposeTips(editor, actionEnum)` -- 关闭代码提示
  - `isAvailable(editor)` -- 检查编辑器可用性
  - `hasTipInlays(editor)` -- 检查是否有提示 Inlay
- `DocumentActionTracker` -- 文档动作追踪
  - `getExecutingForcedCodeGenerateAction()` -- 是否正在执行强制代码生成
  - `exitForcedCodeGenerateAction()` -- 退出强制代码生成
- `RequestTipServiceImpl` -- 请求提示服务
  - `LATEST_RESPONSE_DATA` -- 最新响应数据 Map
  - `CODE_TIP_MAP` -- 代码提示请求 Map
  - `LAST_REQUEST` -- 最后请求 Map
- `CommandCache` -- 命令缓存(域对象)
- `PluginStartupActivity.getApiKey()` -- 获取 API Key
- `AICodeSettingsState.autoTrigger` -- 自动触发设置

---

### 2.4 AutoCodeGenerateListener$Q (位置快照)

```
public final class com.aicode.listener.AutoCodeGenerateListener$Q
  // --- 字段 ---
  private final VisualPosition byte  // 光标可视位置
  private final long enum           // 文档修改序列号

  // --- 构造 ---
  Q(long modificationSequence, VisualPosition visualPosition)
```

---

### 2.5 AutoCodeGenerateListener$T (撤销快照)

```
public final class com.aicode.listener.AutoCodeGenerateListener$T
  // --- 字段 ---
  private final Editor byte  // 编辑器引用
  private final long enum    // 文档修改序列号

  // --- 方法 ---
  public Editor WA()  // 获取编辑器(含 null 检查)

  // --- 构造 ---
  T(Editor editor, long modificationSequence)
```

---

### 2.6 CodeEditorListener

```
public class com.aicode.listener.CodeEditorListener
  implements com.intellij.openapi.editor.event.EditorFactoryListener

  // --- 字段 ---
  private final CodeSelectionListener enum  // 选择监听器实例

  // --- 方法 ---

  // 编辑器创建
  public void editorCreated(EditorFactoryEvent):
    1. event.getEditor() 获取编辑器
    2. editor.getProject() 获取项目
    3. 如果项目非 null 且未 disposed:
       a. EditorManagerService.getInstance().isAvailable(editor)
       b. 如果可用:
          - Disposer.newDisposable(解混淆: "AICodeSelectionListener") 创建 Disposable
          - EditorUtil.disposeWithEditor(editor, disposable) 绑定生命周期
          - editor.getSelectionModel().addSelectionListener(selectionListener, disposable)
          - 注册选择监听器

  // --- 构造 ---
  CodeEditorListener():
    - 创建 CodeSelectionListener 实例
```

**交互关系**:
- `EditorManagerService.isAvailable()` -- 检查编辑器是否支持 AI 补全
- `SelectionModel.addSelectionListener()` -- 注册文本选择监听
- `Disposer` -- IntelliJ 生命周期管理

---

### 2.7 CodeEditorListener$CodeSelectionListener

```
public class com.aicode.listener.CodeEditorListener$CodeSelectionListener
  implements com.intellij.openapi.editor.event.SelectionListener

  // --- 方法 ---

  // 选择变更回调
  public void selectionChanged(SelectionEvent):
    1. event.getEditor() 获取编辑器
    2. editor.getProject() 获取项目
    3. 如果项目为 null 或已 disposed -> 返回
    4. EditorUtil.isSelectedEditor(editor) 检查是否为选中编辑器
    5. 如果不是 -> 返回
    6. event.getDocument().getText(event.getNewRange()) 获取新选中文本
    7. event.getDocument().getText(event.getOldRange()) 获取旧选中文本
    8. 如果新旧文本相同(StringUtils.equals) -> 返回(无实际变更)
```

**注意**: 此监听器目前仅检测选择变更，但字节码中未发现对服务的调用。可能是一个占位实现或被混淆隐藏了后续逻辑。

---

### 2.8 CodeFileEditorManagerListener (核心 -- 30KB)

```
public class com.aicode.listener.CodeFileEditorManagerListener
  implements com.intellij.openapi.fileEditor.FileEditorManagerListener

  // --- 静态字段 ---
  private static final Map<Editor, DocumentListener> byte  // 编辑器->文档监听器映射
  private static final Logger enum                          // 日志

  // --- 关键方法 ---

  // 同步文档列表(发送给 Agent)
  public static void syncDocumentList(VirtualFile):
    1. 如果 file 为 null -> 抛异常
    2. 调用 wA(file) 发送同步消息

  // 文件打开(同步)
  public void fileOpenedSync(FileEditorManager, VirtualFile, Pair<FileEditor[], FileEditorProvider[]>):
    1. 验证参数非空
    2. 获取文件路径，检查文件存在
    3. 检查文件类型非二进制
    4. AICodeUtils.getEditor(manager, file) 获取编辑器
    5. addListener(editor) 注册文档监听器
    6. RecentFilesManager.fileOpened(project, path) 记录最近文件
    7. 如果 apiKey 非空: sendOpenDocument(file, path, editor)

  // 发送打开文档消息给 Agent
  public static void sendOpenDocument(VirtualFile, String, Editor):
    1. 创建 MessageDto(UUID, ACTION_OPEN_DOCUMENT)
    2. 设置 path 和 content(document.getText())
    3. 如果编辑器有 CaretModel:
       a. 获取光标偏移量
       b. 计算行号和列号
       c. 创建 RangeDTO(line, character) 两个(起止相同)
       d. 设置 range 字段
    4. PluginWebsocketClient.sendWsMessageWithOutApm(messageDto, project)

  // 选择变更(文件切换)
  public void selectionChanged(FileEditorManagerEvent):
    1. event.getNewFile() 获取新文件
    2. 验证路径非空、文件存在、非二进制
    3. 获取编辑器
    4. RecentFilesManager.fileOpened(project, path)
    5. 如果 apiKey 非空: syncDocumentList + sendOpenDocument

  // 文件关闭
  public void fileClosed(FileEditorManager, VirtualFile):
    1. 获取编辑器
    2. 如果编辑器非 null 且 byte Map 非空:
       a. 从 byte Map 获取 DocumentListener
       b. 如果存在: document.removeDocumentListener(listener)
       c. 从 byte Map 移除
    3. wA(file) 同步文档列表

  // 添加文档监听器
  public void addListener(Editor):
    1. Uc() 创建 DocumentListener (即 CodeFileEditorManagerListener$01)
    2. editor.getDocument().addDocumentListener(listener)
    3. byte Map.put(editor, listener)

  // 取消并添加定时请求
  public void cancelAllAndAddRequest(Runnable, Alarm, Object, int):
    1. 同步块(monitor on this)
    2. 如果 alarm.isDisposed() -> 返回
    3. alarm.cancelAllRequests()
    4. alarm.addRequest(runnable, delayMs)

  // 同步文档列表给 Agent
  private static void wA(VirtualFile):
    1. ProjectUtil.guessProjectForFile(file) 获取项目
    2. 如果项目为 null 或已 disposed -> 返回
    3. 创建 MessageDto(UUID, ACTION_SYNC_DOCUMENT_LIST)
    4. AICodeUtils.getOpenFilePathList(project) 获取打开文件列表
    5. messageDto.setData(filePathList)
    6. PluginWebsocketClient.sendWsMessageWithOutApm(messageDto, project)

  // 文档变更处理(读操作)
  private static void VB(Document, VirtualFile, String, Editor):
    1. Application.runReadAction 执行:
       a. 获取文档文本
       b. 获取光标偏移量
       c. 计算行号和列号
       d. 构建 CodeInfoDto
       e. 发送消息给 Agent
```

**交互关系**:
- `PluginWebsocketClient.sendWsMessageWithOutApm()` -- 发送 WebSocket 消息(不带 APM)
- `RecentFilesManager.fileOpened()` -- 记录最近打开文件
- `EditorManagerService` -- 编辑器管理服务
- `AICodeUtils` -- 工具类(getEditor, getOpenFilePathList)
- `DocumentActionTracker` -- 文档动作追踪

---

### 2.9 CodeFileEditorManagerListener$01 (文档变更监听)

```
public class com.aicode.listener.CodeFileEditorManagerListener$01
  implements com.intellij.openapi.editor.event.DocumentListener

  // --- 字段 ---
  public final CodeFileEditorManagerListener enum  // 外部类引用

  // --- 方法 ---

  // 文档变更后
  public void documentChanged(DocumentEvent):
    1. 如果 apiKey 为空 -> 返回
    2. event.getDocument() 获取文档
    3. FileDocumentManager.getInstance().getFile(document) 获取虚拟文件
    4. 如果文件为 null -> 返回
    5. 获取文件路径，检查文件存在
    6. 获取项目，检查在 projectListConcurrentHashMap 中
    7. 获取项目对应的 List(包含 Alarm 和锁对象)
    8. 如果 List.size() < 2 -> 返回
    9. Application.invokeLater 异步执行:
       a. 获取编辑器
       b. 检查 CommonService.isSupportJava(editor)
       c. 如果支持 Java:
          - cancelAllAndAddRequest(runnable, alarm, lock, 3000ms)  // 3秒延迟
       d. 如果不支持 Java:
          - cancelAllAndAddRequest(runnable, alarm, lock, 50ms)    // 50ms延迟

  // 文档变更前
  public void beforeDocumentChange(DocumentEvent):
    1. 获取文档和虚拟文件
    2. 如果 InlineChatStreamHandleService.HANDING_DATA 为 true -> 返回(Inline Chat 处理中)
    3. Application.invokeLater:
       a. 调用 wc(file) 清理 InlineChat 数据
       b. 在 ModalityState.defaultModalityState 下执行

  // 清理 InlineChat 数据
  private static void wc(VirtualFile):
    1. EditorKt.getInfoByVirtualFile(file) 获取 InlineChatInfo
    2. InlineChatService.cleanLastData(info) 清理
```

**交互关系**:
- `PluginStartupActivity.getApiKey()` -- API Key 检查
- `CommonService.isSupportJava()` -- Java 语言支持检查
- `InlineChatStreamHandleService.HANDING_DATA` -- Inline Chat 流处理标志
- `InlineChatService.cleanLastData()` -- 清理 Inline Chat 数据
- `PluginDocumentListener.projectListConcurrentHashMap` -- 项目文档映射

---

### 2.10 CodeLookupManagerListener

```
public class com.aicode.listener.CodeLookupManagerListener
  implements com.intellij.codeInsight.lookup.LookupManagerListener

  // --- 字段 ---
  private final LookupListener byte  // 补全列表监听器
  private static final Logger enum   // 日志

  // --- 方法 ---

  // 检查 Tab 快捷键绑定
  public static boolean getShortcutForAction(String actionId):
    1. KeymapManager.getInstance().getActiveKeymap()
    2. 获取 action 的快捷键
    3. 如果无快捷键 -> 返回 false
    4. 遍历快捷键:
       a. 如果是 KeyboardShortcut 且 firstKeyStroke.keyCode == 9(Tab) 且无 secondKeyStroke -> 返回 true
    5. 否则返回 false

  // 补全列表激活变更
  public void activeLookupChanged(Lookup oldLookup, Lookup newLookup):
    1. 如果 newLookup 非 null: 注册 LookupListener
    2. 获取 PsiFile (可能为 null)
    3. 如果 oldLookup 非 null 且 newLookup 为 null (补全关闭):
       a. 如果有 PsiFile 且编辑器为选中编辑器:
          - EditorManagerService.isAvailable(editor)
          - document.isInBulkUpdate() == false
          - editorChanged(editor, Forced, false)  // 触发强制补全
    4. 如果 newLookup 非 null 且 oldLookup 为 null (补全打开):
       a. 如果 showIdeCodeTips 设置关闭:
          - 获取编辑器
          - EditorManagerService.isAvailable(editor)
          - cancelTipRequests(editor)  // 取消 AI 补全请求
          - disposeTips(editor, IdeCompletion)  // 关闭 IDE 补全提示
```

**交互关系**:
- `EditorManagerService` -- 编辑器管理服务
  - `editorChanged(editor, Forced, false)` -- 触发强制补全
  - `cancelTipRequests(editor)` -- 取消补全请求
  - `disposeTips(editor, IdeCompletion)` -- 关闭 IDE 补全提示
- `AICodeRequestSettings.isShowIdeCodeTips()` -- IDE 代码提示设置

---

### 2.11 CodeLookupManagerListener$01 (补全项选择监听)

```
public class com.aicode.listener.CodeLookupManagerListener$01
  implements com.intellij.codeInsight.lookup.LookupListener

  // --- 字段 ---
  public final CodeLookupManagerListener enum  // 外部类引用

  // --- 方法 ---

  // 补全项选择前
  public boolean beforeItemSelected(LookupEvent):
    1. 获取补全字符(completionChar)和 Lookup/Editor
    2. 检查是否为 Tab 快捷键(getShortcutForAction)
    3. 如果是 Tab 且编辑器有 AI 提示 Inlay:
       a. 尝试 acceptTip() -- 接受 AI 补全
       b. 如果接受成功:
          - 设置 ignoreLookupApply = true
          - 隐藏 Lookup (hideLookup(true))
          - 返回 false (阻止 IDE 补全)
       c. 否则调用父类默认行为

  // 补全取消
  public void lookupCanceled(LookupEvent):
    1. 获取编辑器
    2. 如果编辑器有 AI 提示 Inlay:
       - editorChanged(editor, Automatic, true)  // 触发自动补全

  // 补全项选择
  public void itemSelected(LookupEvent):
    - 直接调用父类默认行为

  // 当前项变更
  public void currentItemChanged(LookupEvent):
    1. 获取编辑器
    2. 如果有 AI 提示 Inlay:
       - editorChanged(editor, Automatic, true)

  // 补全显示
  public void lookupShown(LookupEvent):
    1. 获取编辑器
    2. 如果有 AI 提示 Inlay:
       - editorChanged(editor, Automatic, true)
```

**交互关系**:
- `EditorManagerService` -- 核心交互
  - `hasTipInlays(editor)` -- 检查 AI 提示
  - `acceptTip(editor)` -- 接受 AI 补全
  - `editorChanged()` -- 触发补全
- `AutoCodeGenerateListener.ignoreLookupApply` -- 全局标记

---

### 2.12 CommitHandlerFactory

```
public class com.aicode.listener.CommitHandlerFactory
  extends com.intellij.openapi.vcs.checkin.CheckinHandlerFactory

  // --- 方法 ---

  // 创建提交处理器
  public CheckinHandler createHandler(CheckinProjectPanel, CommitContext):
    1. 验证参数非空
    2. new CommitHandlerFactory$o(panel)  // 创建实际处理器
```

---

### 2.13 CommitHandlerFactory$o (提交处理实现 -- 37KB)

```
public class com.aicode.listener.CommitHandlerFactory$o
  extends com.intellij.openapi.vcs.checkin.CheckinHandler

  // --- 字段 ---
  private final Project final                    // 关联项目
  private static final Logger try                // SLF4J 日志
  private static final Map<Project, String> float // 项目->提交ID映射
  private final CheckinProjectPanel byte         // 提交面板
  private final Map<VirtualFile, List<Change>> enum // VCS根->变更列表映射

  // --- 关键方法 ---

  // 提交前处理
  public ReturnResult beforeCheckin():
    1. 如果 apiKey 非空:
       a. panel.getSelectedChanges() 获取选中变更
       b. Application.executeOnPooledThread 异步执行变更分析
    2. 返回 COMMIT (允许提交)

  // 提交成功处理
  public void checkinSuccessful():
    1. 如果 apiKey 为空 -> 返回
    2. 记录开始时间
    3. 遍历 enum Map(VCS根->变更列表):
       a. 对每个变更列表:
          - 创建 3 个 ArrayList: 方法列表、新增行数列表、总行数列表
          - 遍历每个 Change:
            * Fc(change, addedLinesList, totalLinesList) -- 分析变更方法
            * 合并到 methodList
          - 如果 methodList 非空:
            * GC(methodList, addedLinesList, totalLinesList, vcsRoot) -- 发送收集数据
    4. 记录结束时间和耗时

  // 分析变更方法
  private List<UnitTestMethodDto> Fc(Change, List<Integer>, List<Integer>):
    1. change.getVirtualFile() 获取文件
    2. 如果文件为 null -> 返回空列表
    3. 检查文件类型是否为 Java (equalsIgnoreCase)
    4. 检查文件状态(ADDED 或 MODIFIED)
    5. UnitTestCollectUtil.diffContent(changes, project) 获取 diff 内容
    6. 如果 diff 为空 -> 返回空列表
    7. Application.runReadAction 获取 Document
    8. 如果 Document 为 null -> 返回空列表
    9. 获取文档行数
    10. UnitTestCollectUtil.getChangeByDiff(diff, lineCount) 获取变更行
    11. 如果变更行为空 -> 返回空列表
    12. 记录行数到列表
    13. 如果是新增文件: 记录总行数
    14. 如果是修改文件: 统计新增方法数(通过 diff 分析)
    15. UnitTestCollectUtil.getAllMethods(project, document) 获取所有方法
    16. UnitTestCollectUtil.getChangeMethods(allMethods, changeLines, isAdded) 获取变更方法

  // 发送单元测试收集数据
  private void GC(List<UnitTestMethodDto>, List<Integer>, List<Integer>, VirtualFile):
    1. 计算新增行数总和
    2. 如果新增行数为 0 -> 返回
    3. 计算总行数总和
    4. 遍历方法列表:
       a. 如果 isUnitTestMethod 为 true:
          - 累加 methodLine 到 totalMethodLines
          - 累加 increment 到 totalIncrement
          - 如果 methodId 非空且 totalAccumulatedLines <= totalMethodLines:
            * 创建 CommitChangeDto(methodId, methodLine)
    5. 创建 UnitTestCollectDto:
       - repositoryName
       - addedLines
       - totalLines
       - totalMethodLines
       - totalIncrement
       - commitMessage (解混淆)
       - commitChangeList
    6. 设置 clientName (ApplicationInfo.getVersionName())
    7. 设置 clientVersion (ApplicationInfo.getApiVersion())
    8. 设置 pluginVersion (BasicActionsBundle.message)
    9. 创建 MessageDto:
       - command: LOG_TEST_COLLECTION_COMMIT
       - id: UUID
       - path: vcsRoot.getPath()
       - data: unitTestCollectDto
    10. PluginWebsocketClient.sendWsMessage(messageDto, project)

  // 获取 VCS 仓库当前修订版本
  private String Da(VirtualFile) throws Exception:
    1. VcsRepositoryManager.getInstance(project).getRepositoryForRoot(vcsRoot)
    2. 如果仓库为 null -> 返回 UUID
    3. repository.getCurrentRevision()
    4. 如果修订为空 -> 返回 UUID
    5. 检查缓存 Map 中是否已有该修订
    6. 如果有且包含当前修订 -> 返回 修订+时间戳
    7. 更新缓存 Map

  // 检查是否为 Git 提交哈希
  private static boolean vA(String):
    - 以 "0" + 小写字母开头 或 以 "`" 开头

  // 组织变更到 VCS 根映射
  private void Pa(Collection<Change>):
    1. 遍历变更集合
    2. 对每个变更:
       a. 获取虚拟文件
       b. ProjectLevelVcsManager.getInstance(project).getVcsRootObjectFor(file)
       c. 如果 VCS 根有效: 添加到 enum Map
```

**交互关系**:
- `UnitTestCollectUtil` -- 单元测试收集工具
  - `diffContent()` -- 获取 diff 内容
  - `getChangeByDiff()` -- 获取变更行
  - `getAllMethods()` -- 获取所有方法
  - `getChangeMethods()` -- 获取变更方法
- `PluginWebsocketClient.sendWsMessage()` -- 发送 WebSocket 消息
- `UnitTestMethodDto` -- 单元测试方法 DTO
- `UnitTestCollectDto` -- 单元测试收集 DTO
- `CommitChangeDto` -- 提交变更 DTO

---

### 2.14 FileWatchedAdapter

```
public class com.aicode.listener.FileWatchedAdapter
  implements com.intellij.openapi.fileEditor.FileDocumentManagerListener

  // --- 字段 ---
  private static final Logger enum  // SLF4J 日志

  // --- 方法 ---

  // 文档保存前
  public void beforeDocumentSaving(Document):
    1. FileDocumentManager.getInstance().getFile(document) 获取虚拟文件
    2. 如果文件非 null:
       a. 获取文件名
       b. mb(fileName) 获取文件扩展名
       c. LanguageEnum.isVaildLanguage(extension) 检查语言有效性
       d. 如果语言无效 -> 返回
    3. ProjectUtil.guessProjectForFile(file) 获取项目
    4. 异常处理: Logger.info(e.getMessage())

  // 获取文件扩展名
  private String mb(String fileName):
    - fileName.split(解混淆: ".") 最后一段
    - 如果无分割 -> 返回 null

  // --- 静态初始化 ---
  static:
    - LoggerFactory.getLogger(FileWatchedAdapter.class)
```

**注意**: 此监听器的 `beforeDocumentSaving` 方法获取了项目和文件信息，但字节码中未发现后续服务调用。可能是占位实现或逻辑被混淆。

---

### 2.15 GitBranchChangeListener (核心 -- 79KB)

```
public class com.aicode.listener.GitBranchChangeListener

  // --- 静态字段 ---
  public static final Map<String, String> CURRENT_REPO          // 当前仓库信息
  public static final Key<Boolean> NOTICE_CODE_KNOWLEDGE_REPO_STATUS  // 通知状态 Key
  public static final Key GIT_CODE_KNOWLEDGE_REPO_STATUS        // 代码知识库状态 Key
  private static final Map<String, String> enum                 // 仓库状态缓存
  public static final Key GIT_STATUS                            // Git 状态 Key

  // --- 实例字段 ---
  private final Project float                   // 关联项目
  private MessageBusConnection byte             // 消息总线连接

  // --- 关键方法 ---

  // 构造函数
  GitBranchChangeListener(Project):
    1. 验证项目非空
    2. 存储项目引用
    3. 调用 jC() 注册消息总线

  // 注册 Git 仓库变更监听
  private void jC():
    1. project.getMessageBus().connect() 获取 MessageBusConnection
    2. 订阅 GitRepository.GIT_REPO_CHANGE topic
    3. 使用 invokedynamic 创建 repositoryChanged 回调
    4. 回调中调用 kA(repository)

  // 仓库变更处理
  private void kA(GitRepository):
    1. 如果权限包含 CODE_KNOWLEDGE_BASE:
       a. Zb(project, repository) -- 检查仓库授权状态

  // 检查仓库授权状态
  private static void Zb(Project, GitRepository):
    1. 获取当前分支名
    2. 获取远程 URL
    3. 如果远程为空 -> 返回
    4. 检查远程分支是否包含当前分支
    5. 如果不包含: sendNoAuthStatusToWeb(project)
    6. 如果包含:
       a. 检查缓存 Map 中是否已有相同远程+分支
       b. 如果有且未变 -> 返回
       c. 更新缓存 Map
       d. 发送 GIT_CODE_KNOWLEDGE_REPO_STATUS 消息给 Agent

  // 发送无授权状态给 Web
  public static void sendNoAuthStatusToWeb(Project):
    1. getCurrentGitInfo(project) 获取当前 Git 信息
    2. 构建 JsonObject:
       - type: COMMON_SHOW_MESSAGE_IN_WEB
       - data: 包含 repositoryName, remoteUrl, branchName
       - status: -5 (未授权)
    3. dc(project, jsonObject) 发送给 Web

  // 发送消息给 Web (UI 线程)
  private static void dc(Project, JsonObject):
    1. Application.invokeLater 异步执行:
       a. 获取 WebViewWindowPanel
       b. 如果已加载: SocketMessageHandleListener.send2Web(project, data)
       c. 否则: project.putUserData(GIT_STATUS, data) 缓存

  // 代码知识库通知
  public static void codeKnowledgeNotification(Project):
    1. 检查 CODE_KNOWLEDGE_BASE 权限
    2. 如果已通知(NOTICE_CODE_KNOWLEDGE_REPO_STATUS) -> 返回
    3. 设置已通知标记
    4. 获取 GitRepositoryManager
    5. 遍历仓库: Zb(project, repository)

  // 获取当前 Git 信息
  public static void getCurrentGitInfo(Project):
    1. GitRepositoryManager.getInstance(project).getRepositories()
    2. 遍历仓库:
       a. 获取当前分支名
       b. 获取远程 URL
       c. 更新 CURRENT_REPO Map:
          - "remoteUrl" -> remoteUrl
          - "branchName" -> branchName
          - "currentBranchName" -> branchName
          - "currentRemoteUrl" -> remoteUrl

  // 处理 Git 仓库状态响应
  public static void handleGitRepoStatus(String, JsonObject, Project):
    1. 解析响应中的 data.status (int)
    2. 获取 AGENT_REQUEST 中的 MessageDto
    3. 提取 remoteUrl 和 branchName
    4. 调用 va(requestId, project, statusCode, remoteUrl, branchName)
    5. 清除 AGENT_REQUEST 缓存

  // 处理 Git 异常
  public static void handleGitException(String, String, Project, CommandEnum, String):
    1. 如果 command == GIT_SAVE_TOKEN:
       a. 构建 COMMON_SHOW_MESSAGE_IN_WEB 消息
       b. 包含错误信息和 2000ms 超时
       c. send2Web 发送给 Web
    2. 否则:
       a. 构建 GIT_CODE_KNOWLEDGE_REPO_STATUS 消息
       b. 包含 remoteUrl, branchName, repositoryName, command
       c. dc 发送给 Web
       d. 清除 AGENT_REQUEST 缓存

  // 处理 Git 响应
  public static void handleGitResponse(String, JsonObject, Project, CommandEnum):
    1. 如果 command == GIT_SAVE_TOKEN:
       a. 构建 COMMON_SHOW_MESSAGE_IN_WEB 成功消息
       b. 包含 2000ms 超时, autoClose=true
       c. send2Web 发送给 Web
    2. 否则:
       a. getCurrentGitInfo(project) 刷新 Git 信息
       b. 解析响应中的 data
       c. 构建状态消息:
          - type: COMMON_SHOW_MESSAGE_IN_WEB
          - status: 解析值
          - command: 当前命令类型
          - repositoryName: 从 remoteUrl 提取
       d. dc 发送给 Web
       e. 如果是 GIT_CODE_KNOWLEDGE_REPO_STATUS 且分支不在远程:
          sendNoAuthStatusToWeb(project)

  // 处理仓库状态(含通知)
  private static void va(String, Project, int, String, String):
    1. 如果 ignoreGitAuth 设置开启:
       a. 检查版本号是否匹配
       b. 如果匹配 -> 返回
       c. 重置 ignoreGitAuth = false
    2. 如果是 GIT_GET_STATUS 请求 -> 清除并返回
    3. 获取 GitRepoStatusEnum
    4. 如果状态为 UNAUTHORIZED:
       a. 从 remoteUrl 提取仓库平台名
       b. 格式化通知消息
    5. 创建 Notification:
       - group: 解混淆通知组
       - title: 解混淆标题
       - type: INFO
    6. 如果不需要跳过 Web:
       a. 添加 "打开知识库" 通知动作 ($R)
    7. 添加 "忽略" 通知动作 ($H)
    8. 通知显示在项目中

  // 从 URL 提取仓库名
  public static String getRepositoryNameFromUrl(String url):
    1. 如果 url 为空 -> 返回 null
    2. 如果以 ".git" 结尾 -> 截取掉
    3. lastIndexOf('/') + 1 截取
    4. 如果无 '/' -> 返回 null

  // 释放资源
  public void dispose():
    1. 如果 byte(MessageBusConnection) 非 null:
       - byte.disconnect()
```

**交互关系**:
- `PluginWebsocketClient` -- WebSocket 通信
  - `AGENT_REQUEST` -- Agent 请求缓存
  - `WEB_REQUEST` -- Web 请求缓存
  - `sendWsMessage()` -- 发送消息
- `SocketMessageHandleListener.send2Web()` -- 发送消息给 WebView
- `GitRepositoryManager` -- Git 仓库管理
- `AICodeSettingsState` -- 设置状态
  - `permissions` -- 权限集合
  - `ignoreGitAuth` -- 忽略 Git 授权
  - `ignoreVersion` -- 忽略版本
  - `codeKnowledgeWebUrl` -- 代码知识库 URL
- `ChatService.isCurrentBranchRemote()` -- 检查分支是否在远程
- `CommonService.messageBus()` -- 消息总线通知
- `WebViewWindowPanel` -- WebView 面板

---

### 2.16 GitBranchChangeListener$H ("忽略"通知动作)

```
public class com.aicode.listener.GitBranchChangeListener$H
  extends com.intellij.notification.NotificationAction

  // --- 字段 ---
  public final Project enum  // 关联项目

  // --- 方法 ---

  // 动作执行
  public void actionPerformed(AnActionEvent, Notification):
    1. notification.hideBalloon()  // 关闭通知
    2. CommonService.messageBus(project, 解混淆: "已忽略授权提醒", INFO)
    3. AICodeSettingsState.getInstance().ignoreGitAuth = true
    4. AICodeSettingsState.getInstance().ignoreVersion = BasicActionsBundle.message(解混淆)
```

---

### 2.17 GitBranchChangeListener$R ("打开知识库"通知动作)

```
public class com.aicode.listener.GitBranchChangeListener$R
  extends com.intellij.notification.NotificationAction

  // --- 方法 ---

  // 动作执行
  public void actionPerformed(AnActionEvent, Notification):
    1. notification.hideBalloon()
    2. 构建知识库 URL:
       a. 如果 codeKnowledgeWebUrl 以 "/#" 结尾: url + apiKey
       b. 如果以 "/" 结尾(非"/#"): url + "?token=" + apiKey
       c. 否则: 直接使用 codeKnowledgeWebUrl
    3. BrowserUtil.browse(url)  // 在浏览器中打开
```

---

### 2.18 GitBranchChangeListener$b ("授权仓库"通知动作)

```
public class com.aicode.listener.GitBranchChangeListener$b
  extends com.intellij.notification.NotificationAction

  // --- 字段 ---
  public final String float   // remoteUrl
  public final String byte    // branchName
  public final Project enum   // 关联项目

  // --- 方法 ---

  // 动作执行
  public void actionPerformed(AnActionEvent, Notification):
    1. notification.hideBalloon()
    2. 构建 JsonObject:
       - "remoteUrl" -> remoteUrl
       - "branchName" -> branchName
    3. PluginWebsocketClient.sendWsMessage(GIT_REPO_AUTHORIZE, data, project)
```

---

### 2.19 PluginDocumentListener

```
public class com.aicode.listener.PluginDocumentListener
  implements com.intellij.openapi.components.ProjectComponent

  // --- 静态字段 ---
  private static final Logger enum
  public static Map<Project, List<Object>> projectListConcurrentHashMap  // 项目->资源列表映射

  // --- 方法 ---

  // 项目打开
  public void projectOpened():
    1. ApplicationUtil.findCurrentProject() 获取项目
    2. 创建锁对象
    3. 创建 Alarm (POOLED_THREAD):
       a. 如果项目非 null: new Alarm(POOLED_THREAD, project)
       b. 如果项目为 null: new Alarm(POOLED_THREAD)
    4. 将 [Alarm, 锁对象] 存入 projectListConcurrentHashMap

  // 项目关闭
  public void projectClosed():
    1. 遍历 projectListConcurrentHashMap 的 keySet:
       a. 如果项目已 disposed: 从 Map 中移除

  // 获取组件名
  public String getComponentName():
    - 返回解混淆字符串 (项目文档监听器名称)
```

**交互关系**:
- `projectListConcurrentHashMap` -- 全局共享的项目资源映射，被 `CodeFileEditorManagerListener$01` 引用
- `Alarm` -- IntelliJ 定时器，用于延迟执行代码补全请求

---

### 2.20 PluginManagerListener

```
public class com.aicode.listener.PluginManagerListener
  implements com.intellij.openapi.project.ProjectManagerListener

  // --- 方法 ---

  // 项目关闭
  public void projectClosed(Project):
    1. project.getBasePath() 获取项目路径
    2. PluginWebsocketClient.closeWebsocket(basePath, 解混淆: "项目关闭")
    3. ChatService.SESSION_ID.remove(basePath)  // 清除 Chat 会话
    4. SqlService.SQL_SESSION_ID.remove(basePath) // 清除 SQL 会话

  // 项目关闭前保存
  public void projectClosingBeforeSave(Project):
    1. 调用 kc(project) -- 处理 Inline Chat 会话

  // 处理 Inline Chat 会话关闭
  private static void kc(Project):
    1. 创建 ArrayList 收集需要处理的 InlineChatInfo
    2. 遍历 EditorKt.inlineChatCacheData:
       a. 如果 info.getEditor().getProject() == 当前项目:
          - 添加到列表
    3. 遍历列表:
       a. 如果 sessionController.getInlineChatStepEnum() == SUCCESS:
          - sessionController.handleOperation(editor, DIALOG_ACCEPT)  // 自动接受
    4. 异常处理: 捕获 Throwable
```

**交互关系**:
- `PluginWebsocketClient.closeWebsocket()` -- 关闭 WebSocket 连接
- `ChatService.SESSION_ID` -- Chat 会话 ID 缓存
- `SqlService.SQL_SESSION_ID` -- SQL 会话 ID 缓存
- `EditorKt.inlineChatCacheData` -- Inline Chat 缓存数据
- `SessionController.handleOperation()` -- Inline Chat 操作处理

---

### 2.21 ThemeChangeListener

```
public class com.aicode.listener.ThemeChangeListener
  implements com.intellij.openapi.components.ApplicationComponent

  // --- 字段 ---
  private int float           // 字体大小缓存
  private String byte         // 主题名缓存
  private static final Logger enum

  // --- 方法 ---

  // 初始化组件
  public void initComponent():
    1. 创建 LafManagerListener (lookAndFeelChanged)
    2. 创建 EditorColorsListener (globalSchemeChange)
    3. Application.getMessageBus().connect()
    4. 订阅 LafManagerListener.TOPIC (lookAndFeelChanged)
    5. 订阅 EditorColorsManager.TOPIC (globalSchemeChange)

  // LookAndFeel 变更
  private void ac(LafManager):
    1. 通过反射获取 LafManager 的当前 LookAndFeelInfo
    2. 如果 byte(主题名) 为空: 设置为当前 LAF 名
    3. 如果 float(字体大小) 为 0: 从全局 Scheme 获取 consoleFontSize
    4. 如果新主题名与缓存不同:
       - changeTheme(themeName, fontSize)
    5. 更新 byte = 新主题名

  // EditorColorsScheme 变更
  private void yA(EditorColorsScheme):
    1. 获取当前全局 Scheme 的 consoleFontSize
    2. 如果字体大小与缓存不同:
       - changeTheme(cachedThemeName, newFontSize)
    3. 更新 float = 新字体大小

  // 初始化主题
  public static void initTheme():
    1. EditorColorsManager.getInstance().getGlobalScheme()
    2. 获取 scheme 名和 consoleFontSize
    3. changeTheme(schemeName, fontSize)

  // 获取主题(含图标切换)
  public static String getTheme(String themeName, ToolWindow toolWindow):
    1. 如果 themeName 非空且包含 "dark"(解混淆):
       a. 设置暗色图标: Icons.StatusBarIcon = dark 图标路径
       b. 设置 ToolWindow 图标为暗色
       c. 返回 "dark"
    2. 否则:
       a. 设置亮色图标: Icons.StatusBarIcon = light 图标路径
       b. 设置 ToolWindow 图标为亮色
       c. 返回 "light"

  // 切换主题(异步)
  public static void changeTheme(String themeName, int fontSize):
    1. Application.invokeLater:
       a. ea(themeName, fontSize) -- 实际执行

  // 实际主题切换
  private static void ea(String themeName, int fontSize):
    1. 获取插件版本号
    2. 遍历所有有效项目:
       a. 获取 ToolWindow
       b. getTheme(themeName, toolWindow) -- 获取主题+切换图标
       c. 构建 JsonObject:
          - "theme": 主题名
          - "fontSize": 字体大小
          - "type": SETTING_CHANGE_THEME
       d. SocketMessageHandleListener.send2Web(project, data) -- 发送给 WebView
       e. StatusBarPopup.update(project) -- 更新状态栏

  // 释放组件
  public void disposeComponent():
    - 空实现

  // 获取组件名
  public String getComponentName():
    - 返回解混淆字符串
```

**交互关系**:
- `SocketMessageHandleListener.send2Web()` -- 发送消息给 WebView
- `StatusBarPopup.update()` -- 更新状态栏图标
- `Icons.StatusBarIcon` -- 状态栏图标
- `ToolWindow.setIcon()` -- 工具窗口图标
- `WebViewResponseTypeEnum.SETTING_CHANGE_THEME` -- 主题变更响应类型
- `BasicActionsBundle.message()` -- 获取插件版本号

---

## 3. 事件订阅关系图

```
+---------------------------+     +-----------------------------------+
|   IntelliJ Platform       |     |   iFlyCode Listener              |
+---------------------------+     +-----------------------------------+
|                            |     |                                   |
| DynamicPluginListener     |---->| AICodeUnloadPluginListener       |
|  (beforePluginUnload)     |     |  -> EditorManagerService          |
|                            |     |     .disposeTips()                |
|----------------------------|     |-----------------------------------|
| AppLifecycleListener      |---->| ApplicationStartupListener       |
|  (appWillBeClosed)        |     |  -> PluginStartupActivity.clear()|
|  (appClosing)             |     |                                   |
|----------------------------|     |-----------------------------------|
| CommandListener           |---->| AutoCodeGenerateListener         |
|  (commandStarted)         |     |  -> EditorManagerService          |
|  (commandFinished)        |     |     .editorChanged()              |
|  (undoTransparent*)       |     |     .disposeTips()                |
|                            |     |  -> DocumentActionTracker         |
|                            |     |  -> RequestTipServiceImpl (APM)  |
|----------------------------|     |-----------------------------------|
| EditorFactoryListener     |---->| CodeEditorListener               |
|  (editorCreated)          |     |  -> SelectionModel                |
|                            |     |     .addSelectionListener()       |
|----------------------------|     |-----------------------------------|
| SelectionListener         |---->| CodeSelectionListener            |
|  (selectionChanged)       |     |  (文本选择变更检测)               |
|----------------------------|     |-----------------------------------|
| FileEditorManagerListener |---->| CodeFileEditorManagerListener    |
|  (fileOpenedSync)         |     |  -> PluginWebsocketClient         |
|  (selectionChanged)       |     |     .sendWsMessageWithOutApm()    |
|  (fileClosed)             |     |  -> RecentFilesManager            |
|                            |     |  -> InlineChatService             |
|----------------------------|     |-----------------------------------|
| DocumentListener          |---->| CodeFileEditorManagerListener$01 |
|  (documentChanged)         |     |  -> EditorManagerService          |
|  (beforeDocumentChange)   |     |  -> CommonService.isSupportJava() |
|                            |     |  -> InlineChatService.cleanLast() |
|----------------------------|     |-----------------------------------|
| LookupManagerListener     |---->| CodeLookupManagerListener        |
|  (activeLookupChanged)    |     |  -> EditorManagerService          |
|                            |     |     .editorChanged(Forced)        |
|                            |     |     .cancelTipRequests()          |
|                            |     |     .disposeTips(IdeCompletion)  |
|----------------------------|     |-----------------------------------|
| LookupListener            |---->| CodeLookupManagerListener$01     |
|  (beforeItemSelected)     |     |  -> EditorManagerService          |
|  (lookupCanceled)         |     |     .acceptTip()                  |
|  (currentItemChanged)     |     |     .editorChanged(Automatic)     |
|  (lookupShown)            |     |                                   |
|----------------------------|     |-----------------------------------|
| CheckinHandlerFactory     |---->| CommitHandlerFactory             |
|  (createHandler)          |     |  -> CommitHandlerFactory$o        |
|                            |     |     .UnitTestCollectUtil          |
|                            |     |     .PluginWebsocketClient        |
|----------------------------|     |-----------------------------------|
| FileDocumentManager       |---->| FileWatchedAdapter               |
|  Listener                  |     |  (文件保存检测, 当前为占位实现)  |
|  (beforeDocumentSaving)    |     |                                   |
|----------------------------|     |-----------------------------------|
| GitRepository.GIT_REPO    |---->| GitBranchChangeListener          |
|  _CHANGE (MessageBus)    |     |  -> PluginWebsocketClient         |
|                            |     |  -> SocketMessageHandleListener   |
|                            |     |  -> ChatService                   |
|                            |     |  -> AICodeSettingsState           |
|----------------------------|     |-----------------------------------|
| ProjectComponent          |---->| PluginDocumentListener           |
|  (projectOpened)          |     |  -> projectListConcurrentHashMap  |
|  (projectClosed)          |     |     (Alarm + lock)                |
|----------------------------|     |-----------------------------------|
| ProjectManagerListener    |---->| PluginManagerListener            |
|  (projectClosed)          |     |  -> PluginWebsocketClient         |
|  (projectClosingBefore*)  |     |     .closeWebsocket()             |
|                            |     |  -> ChatService/SqlService 清除   |
|                            |     |  -> SessionController             |
|                            |     |     .handleOperation(ACCEPT)      |
|----------------------------|     |-----------------------------------|
| ApplicationComponent      |---->| ThemeChangeListener             |
|  (initComponent)          |     |  -> SocketMessageHandleListener   |
| LafManagerListener        |     |     .send2Web()                   |
| EditorColorsListener      |     |  -> StatusBarPopup.update()      |
|                            |     |  -> Icons 切换                    |
+---------------------------+     +-----------------------------------+
```

---

## 4. Listener 之间的调用链

### 4.1 代码补全触发链

```
用户键入字符
  -> IntelliJ CommandListener.commandStarted()
    -> AutoCodeGenerateListener.commandStarted()
      -> 存储编辑器位置快照 (Q) 到 UserData
      -> 检测命令名: completion / Tab / Undo / 撤销 / imitation
      -> 处理撤销 APM (bb)

  -> IntelliJ CommandListener.commandFinished()
    -> AutoCodeGenerateListener.commandFinished()
      -> 检查 ignoreLookupApply / ignoreApply / inlineChatOperate 标记
      -> 获取当前编辑器
      -> 比较新旧位置快照:
        -> 位置改变 (Va) -> editorChanged(Automatic)
        -> 命令为 EmptyRunnable -> editorChanged(Automatic, false)
        -> 仅修改序列改变 (xA) -> disposeTips(CaretChange)
```

### 4.2 IDE 补全弹窗交互链

```
IDE 显示补全列表
  -> CodeLookupManagerListener.activeLookupChanged(null, lookup)
    -> 注册 LookupListener

用户按 Tab 选择补全项
  -> CodeLookupManagerListener$01.beforeItemSelected()
    -> 检查是否有 AI 提示 Inlay
    -> 如果有: acceptTip() -> 隐藏 Lookup -> 返回 false(阻止 IDE 补全)
    -> 设置 ignoreLookupApply = true

补全列表关闭
  -> CodeLookupManagerListener$01.lookupCanceled()
    -> 如果有 AI 提示: editorChanged(Automatic, true)

补全列表显示
  -> CodeLookupManagerListener.activeLookupChanged(lookup, null)
    -> 如果 showIdeCodeTips 关闭: cancelTipRequests + disposeTips(IdeCompletion)
```

### 4.3 文件编辑器事件链

```
文件打开
  -> CodeFileEditorManagerListener.fileOpenedSync()
    -> addListener() 注册 DocumentListener
    -> RecentFilesManager.fileOpened()
    -> sendOpenDocument() 发送 ACTION_OPEN_DOCUMENT

文件切换
  -> CodeFileEditorManagerListener.selectionChanged()
    -> RecentFilesManager.fileOpened()
    -> syncDocumentList() 发送 ACTION_SYNC_DOCUMENT_LIST
    -> sendOpenDocument()

文件关闭
  -> CodeFileEditorManagerListener.fileClosed()
    -> 移除 DocumentListener
    -> syncDocumentList()

文档变更
  -> CodeFileEditorManagerListener$01.documentChanged()
    -> 延迟触发:
      - Java 文件: 3000ms
      - 非 Java 文件: 50ms
    -> 发送代码变更消息给 Agent

文档变更前
  -> CodeFileEditorManagerListener$01.beforeDocumentChange()
    -> 如果 InlineChat 处理中: 跳过
    -> 否则: 清理 InlineChat 数据
```

### 4.4 Git 分支变更链

```
Git 仓库变更
  -> GitBranchChangeListener (MessageBus: GIT_REPO_CHANGE)
    -> kA(repository)
      -> Zb(project, repository) 检查授权
        -> 如果分支不在远程: sendNoAuthStatusToWeb()
        -> 否则: 发送 GIT_CODE_KNOWLEDGE_REPO_STATUS

Agent 响应
  -> GitBranchChangeListener.handleGitRepoStatus()
    -> va() 显示通知
      -> $R: 打开知识库 URL
      -> $H: 忽略授权
      -> $b: 授权仓库 (发送 GIT_REPO_AUTHORIZE)

Agent 响应(成功/失败)
  -> GitBranchChangeListener.handleGitResponse()
    -> 构建 COMMON_SHOW_MESSAGE_IN_WEB 消息
    -> dc() 发送给 WebView

  -> GitBranchChangeListener.handleGitException()
    -> 构建 COMMON_SHOW_MESSAGE_IN_WEB 错误消息
    -> dc() 发送给 WebView
```

### 4.5 项目关闭链

```
项目关闭前保存
  -> PluginManagerListener.projectClosingBeforeSave()
    -> kc(project) 处理 Inline Chat
      -> 遍历 inlineChatCacheData
      -> 如果 step == SUCCESS: handleOperation(DIALOG_ACCEPT)

项目关闭
  -> PluginManagerListener.projectClosed()
    -> PluginWebsocketClient.closeWebsocket(basePath, "项目关闭")
    -> ChatService.SESSION_ID.remove(basePath)
    -> SqlService.SQL_SESSION_ID.remove(basePath)
```

### 4.6 VCS 提交链

```
用户提交代码
  -> CommitHandlerFactory.createHandler()
    -> new CommitHandlerFactory$o(panel)

  -> CommitHandlerFactory$o.beforeCheckin()
    -> 异步分析变更文件 (executeOnPooledThread)

  -> CommitHandlerFactory$o.checkinSuccessful()
    -> 遍历 VCS 根变更:
      -> Fc() 分析 Java 文件变更方法
      -> GC() 构建 UnitTestCollectDto
      -> sendWsMessage(LOG_TEST_COLLECTION_COMMIT)
```

### 4.7 主题变更链

```
IDE 主题切换
  -> ThemeChangeListener.ac(LafManager)
    -> 检测主题名变更
    -> changeTheme(themeName, fontSize)

编辑器配色方案变更
  -> ThemeChangeListener.yA(EditorColorsScheme)
    -> 检测字体大小变更
    -> changeTheme(themeName, fontSize)

changeTheme()
  -> Application.invokeLater
    -> ea(themeName, fontSize)
      -> 遍历所有项目:
        -> getTheme() 切换图标
        -> send2Web(SETTING_CHANGE_THEME)
        -> StatusBarPopup.update()
```

---

## 5. 与 Service 层的交互关系

### 5.1 EditorManagerService (核心交互)

| Listener | 调用方法 | 用途 |
|----------|---------|------|
| AutoCodeGenerateListener | `editorChanged(editor, requestType, isCommandName)` | 触发代码补全请求 |
| AutoCodeGenerateListener | `disposeTips(editor, actionEnum)` | 关闭代码提示 |
| AutoCodeGenerateListener | `isAvailable(editor)` | 检查编辑器可用性 |
| AutoCodeGenerateListener | `hasTipInlays(editor)` | 检查 AI 提示 |
| AutoCodeGenerateListener | `cancelTipRequests(editor)` | 取消补全请求 |
| CodeEditorListener | `isAvailable(editor)` | 检查编辑器可用性 |
| CodeLookupManagerListener | `editorChanged(editor, Forced, false)` | 强制触发补全 |
| CodeLookupManagerListener | `cancelTipRequests(editor)` | 取消补全请求 |
| CodeLookupManagerListener | `disposeTips(editor, IdeCompletion)` | 关闭 IDE 补全提示 |
| CodeLookupManagerListener$01 | `acceptTip(editor)` | 接受 AI 补全 |
| CodeLookupManagerListener$01 | `hasTipInlays(editor)` | 检查 AI 提示 |
| AICodeUnloadPluginListener | `disposeTips(editor, UserOperate)` | 用户操作关闭提示 |

### 5.2 PluginWebsocketClient (WebSocket 通信)

| Listener | 调用方法 | 用途 |
|----------|---------|------|
| CodeFileEditorManagerListener | `sendWsMessageWithOutApm(msg, project)` | 发送文档打开/同步消息 |
| CommitHandlerFactory$o | `sendWsMessage(msg, project)` | 发送单元测试收集数据 |
| GitBranchChangeListener | `sendWsMessage(CommandEnum, data, project)` | 发送 Git 状态消息 |
| PluginManagerListener | `closeWebsocket(basePath, reason)` | 关闭 WebSocket 连接 |

### 5.3 SocketMessageHandleListener (WebView 通信)

| Listener | 调用方法 | 用途 |
|----------|---------|------|
| GitBranchChangeListener | `send2Web(project, data)` | 发送 Git 状态给 WebView |
| ThemeChangeListener | `send2Web(project, data)` | 发送主题变更给 WebView |

### 5.4 其他服务交互

| Listener | 服务 | 用途 |
|----------|------|------|
| AutoCodeGenerateListener | `DocumentActionTracker` | 追踪强制代码生成动作 |
| AutoCodeGenerateListener | `RequestTipServiceImpl` | APM 追踪(撤销时结束 Span) |
| CodeFileEditorManagerListener | `RecentFilesManager` | 记录最近打开文件 |
| CodeFileEditorManagerListener$01 | `InlineChatService` | 清理 Inline Chat 数据 |
| CodeFileEditorManagerListener$01 | `CommonService` | Java 语言支持检查 |
| CommitHandlerFactory$o | `UnitTestCollectUtil` | 分析变更方法和单元测试 |
| PluginManagerListener | `ChatService` | 清除 Chat 会话 ID |
| PluginManagerListener | `SqlService` | 清除 SQL 会话 ID |
| GitBranchChangeListener | `AICodeSettingsState` | 读取权限/忽略设置 |
| ThemeChangeListener | `StatusBarPopup` | 更新状态栏图标 |

---

## 6. 混淆字符串解密方法汇总

所有 Listener 中大量使用了字符串混淆，解密通过以下静态方法:

| 解密方法 | 来源包 | 用途 |
|---------|--------|------|
| `MethodGeneratorConfig.H(Object)` | `com.aicode.action.batch` | 通用解密 |
| `CodeCompleteService.H(Object)` | `com.aicode.agent.service` | 代码补全相关 |
| `GeneratorConfig.H(Object)` | `com.aicode.action.batch` | 代码生成相关 |
| `HandleCacheUtil.H(Object)` | `com.aicode.util` | 缓存相关 |
| `IndentLineUtil.H(Object)` | `com.aicode.util` | 缩进相关 |
| `PropertyUtils.H(Object)` | `com.aicode.util` | 属性相关 |
| `OverlayUtils.H(Object)` | `com.aicode.content.util` | 覆盖层相关 |
| `FileExtensionLanguageDetails.H(Object)` | `com.aicode.content.util.file` | 文件扩展名相关 |
| `InlineChatStatusServiceKt.H(Object)` | `com.aicode.inline.status` | Inline Chat 状态 |
| `CancelRequestTip.H(Object)` | `com.aicode.service.editor` | 取消请求提示 |
| `JComponentKt.H(Object)` | `com.aicode.util` | UI 组件相关 |
| `AICodeStringUtil.H(Object)` | `com.aicode.util` | 字符串工具 |
| `GenericUtils.H(Object)` | `com.aicode.diff` | 通用差异工具 |
| `FileService.H(Object)` | `com.aicode.diff` | 文件服务 |
| `RequestCancelException.H(Object)` | `com.aicode.exception` | 请求取消异常 |
| `FontKt.H(Object)` | `com.aicode.ui` | 字体相关 |
| `Maps.H(Object)` | `com.aicode.util` | Map 工具 |
| `NewFileUtils.H(Object)` | `com.aicode.util` | 新文件工具 |

---

## 7. 关键发现

### 7.1 代码补全触发机制

AutoCodeGenerateListener 是代码补全的核心触发器:
- 监听 IntelliJ 的 CommandListener，在 `commandFinished` 时检测编辑器位置变更
- 如果光标位置改变(VisualPosition 不同)，触发 `editorChanged(Automatic)` -- 自动补全
- 如果仅文档内容改变(modificationSequence 不同)，触发 `disposeTips(CaretChange)` -- 关闭提示
- Tab 键选择 IDE 补全时，如果存在 AI 提示，会拦截 IDE 补全，改为接受 AI 提示

### 7.2 文档变更延迟策略

CodeFileEditorManagerListener$01 对不同语言采用不同延迟:
- Java 文件: 3000ms 延迟后触发补全
- 非 Java 文件: 50ms 延迟后触发补全

这表明 Java 代码补全需要更多上下文分析时间。

### 7.3 Git 授权流程

GitBranchChangeListener 实现了完整的 Git 仓库授权流程:
1. 检测分支是否在远程仓库
2. 如果不在远程，标记为未授权(status=-5)
3. 通过 WebSocket 通知 Agent 和 WebView
4. 提供三种用户操作: 打开知识库、忽略授权、授权仓库
5. 授权通过 Agent WebSocket 发送 GIT_REPO_AUTHORIZE 命令

### 7.4 项目关闭清理

PluginManagerListener 在项目关闭时:
1. 先自动接受所有处于 SUCCESS 状态的 Inline Chat 会话
2. 关闭 WebSocket 连接
3. 清除 Chat 和 SQL 会话缓存

### 7.5 VCS 提交数据收集

CommitHandlerFactory$o 在代码提交成功后:
1. 分析所有 Java 变更文件
2. 提取变更方法信息
3. 收集单元测试相关数据
4. 通过 WebSocket 发送 LOG_TEST_COLLECTION_COMMIT 消息给 Agent

### 7.6 主题变更广播

ThemeChangeListener 在 IDE 主题或字体大小变更时:
1. 切换状态栏和工具窗口图标(暗色/亮色)
2. 通过 WebView 通信通道通知前端主题变更
3. 更新状态栏弹出组件