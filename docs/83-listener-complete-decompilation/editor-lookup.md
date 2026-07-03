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
