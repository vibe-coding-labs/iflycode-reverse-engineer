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
