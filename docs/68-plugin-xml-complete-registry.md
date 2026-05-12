# iFlyCode plugin.xml 完整注册表分析

**版本**: 3.4.2-222
**插件 ID**: `com.iflytek`
**插件名称**: iFlyCode
**供应商**: Anhui Zhuojian Technology Co., Ltd
**最低 IDE 版本**: 222.3345.118 (2022.2+)
**资源 Bundle**: `messages.BasicActionsBundle`

---

## 1. 依赖声明

| 依赖模块 | 可选 | 配置文件 | 说明 |
|---|---|---|---|
| `com.intellij.modules.platform` | 否 | — | 平台基础模块 |
| `com.intellij.modules.lang` | 否 | — | 语言基础模块 |
| `Git4Idea` | 否 | — | Git 集成 |
| `com.intellij.modules.java` | 是 | `code-java.xml` | Java 支持 |
| `com.intellij.modules.python` | 是 | `code-python.xml` | Python 支持 |
| `com.intellij.modules.webstorm` | 是 | `code-javascript.xml` | JavaScript 支持 |

**code-java.xml 额外依赖**: `JUnit`, `Coverage`, `com.intellij.modules.coverage`

---

## 2. Action 注册

### 2.1 独立 Action

| Action ID | Class | Text | 快捷键 | 来源 |
|---|---|---|---|---|
| `AICode.LogoutAction` | `com.aicode.action.LogoutAction` | Disable Completions | (继承自自身 use-shortcut-of) | plugin.xml |
| `AICode.userInfo` | `com.aicode.action.UserInfoAction` | UserInfo | — | plugin.xml |
| `AICode.enableAutoTrigger` | `com.aicode.action.EnableAutoTriggerCodeGenerateAction` | Enable Auto Trigger Completions | — | plugin.xml |
| `AICode.setting` | `com.aicode.action.PluginSettingAction` | Plugin Setting | — | plugin.xml |
| `AICode.cyclePrevInlays` | `com.aicode.action.CyclePreviousEditorInlays` | Show Previous Completions | `$default`: Alt+[ / Ctrl+,; `Mac OS X`: Alt+[ / Ctrl+,; `Mac OS X 10.5+`: Alt+[ / Ctrl+, | plugin.xml |
| `AICode.cycleNextInlays` | `com.aicode.action.CycleNextEditorInlays` | Show Next Completions | `$default`: Alt+] / Ctrl+.; `Mac OS X`: Alt+] / Ctrl+.; `Mac OS X 10.5+`: Alt+] / Ctrl+. | plugin.xml |
| `AICode.applyInlays` | `com.aicode.action.AcceptInlaysAction` | Apply Completions to Editor | `$default`: Tab | plugin.xml |
| `AICode.applyWordInlays` | `com.aicode.action.AcceptWordInlaysAction` | Apply Word Completions to Editor | `$default`: Ctrl+Right | plugin.xml |
| `AICode.applyLineCodeInlays` | `com.aicode.action.AcceptLineCodeInlaysAction` | Apply Line Code Completions to Editor | `$default`: Ctrl+Down | plugin.xml |
| `AICode.disposeInlays` | `com.aicode.action.DisposeInlaysAction` | Hide Completions in Editor | `$default`: Escape | plugin.xml |
| `AICode.requestCompletions` | `com.aicode.action.RequestCodeGenerateAction` | Show Completions | `$default`: Alt+\ / Alt+C | plugin.xml |
| `AICode.openWindow` | `com.aicode.action.OpenWindowAction` | Open Window | `$default`: Ctrl+Q | plugin.xml |
| `TriggerCodeProblemsTreePopupAction` | `com.aicode.action.CodeProblemsTreePopupAction` | 星火飞码iFlyCode一键修复 | — | plugin.xml |

### 2.2 InlineChat 操作 Action（组 `AICode.InlineChat` 内）

| Action ID | Class | Text | 快捷键 | 来源 |
|---|---|---|---|---|
| `AICode.InlineChat.InlineChatStopAction` | `com.aicode.inline.action.operate.InlineChatStopAction` | Stop Edit | `$default`: Alt+Z | plugin.xml |
| `AICode.InlineChat.InlineChatUndoAction` | `com.aicode.inline.action.operate.InlineChatUndoAction` | Reject Edit By Undo | `$default`: Ctrl+Z; `Mac OS X 10.5+`: Meta+Z | plugin.xml |
| `AICode.InlineChat.AcceptAction` | `com.aicode.inline.action.operate.InlineChatAcceptAction` | Accept Edit | `$default`: Alt+Y | plugin.xml |
| `AICode.InlineChat.RejectAction` | `com.aicode.inline.action.operate.InlineChatRejectAction` | Reject Edit | `$default`: Alt+X | plugin.xml |
| `AICode.InlineChat.RetryAction` | `com.aicode.inline.action.operate.InlineChatRetryAction` | Retry Edit | `$default`: Alt+D | plugin.xml |

### 2.3 Action Group

| Group ID | 类型 | 属性 | 子项 | 来源 |
|---|---|---|---|---|
| `AICode.inlayContextMenu` | Group | — | `AICode.applyInlays`, separator, `AICode.applyWordInlays`, separator | plugin.xml |
| `AICode.statusBarPopup` | Group | — | `AICode.userInfo`, `AICode.enableAutoTrigger`, `AICode.setting`, `AICode.LogoutAction` | plugin.xml |
| `AICode.InlineChat` | Group (popup) | searchable=false, text="Operate Actions" | InlineChatStopAction, InlineChatUndoAction, AcceptAction, RejectAction, RetryAction | plugin.xml |
| `AICodeEditorPopup` | Group | — | `aicode.EditorActionGroup`, 添加到 `EditorPopupMenu` (anchor=first) | plugin.xml |
| `aicode.EditorActionGroup` | DefaultActionGroup (popup) | text="iFlyCode", icon=Icons.ToolWindowIcon | (子项由代码动态添加) | plugin.xml |

### 2.4 Action 添加到外部 Group

| Action/Group ID | 目标 Group | Anchor | Relative To | 来源 |
|---|---|---|---|---|
| `TriggerCodeProblemsTreePopupAction` | `ProblemsView.ToolWindow.TreePopup` | after | `ProblemsView.QuickFixes` | plugin.xml |
| `AICodeEditorPopup` (group) | `EditorPopupMenu` | first | — | plugin.xml |

### 2.5 快捷键汇总表

| 功能 | 快捷键 (Windows/Linux) | 快捷键 (macOS) |
|---|---|---|
| 接受补全 (Accept) | Tab | Tab |
| 拒绝补全 (Dispose) | Escape | Escape |
| 触发代码补全 | Alt+\ 或 Alt+C | Alt+\ 或 Alt+C |
| 上一个补全 | Alt+[ 或 Ctrl+, | Alt+[ 或 Ctrl+, |
| 下一个补全 | Alt+] 或 Ctrl+. | Alt+] 或 Ctrl+. |
| 逐词采纳 | Ctrl+Right | Ctrl+Right |
| 逐行采纳 | Ctrl+Down | Ctrl+Down |
| 打开窗口 | Ctrl+Q | Ctrl+Q |
| InlineChat 停止 | Alt+Z | Alt+Z |
| InlineChat 撤销 | Ctrl+Z | Meta+Z |
| InlineChat 接受 | Alt+Y | Alt+Y |
| InlineChat 拒绝 | Alt+X | Alt+X |
| InlineChat 重试 | Alt+D | Alt+D |

---

## 3. Service 注册

### 3.1 Application Service

| 接口 | 实现类 | Test 实现 | 来源 |
|---|---|---|---|
| — | `com.aicode.settings.AICodeSettingsState` | — | plugin.xml |
| — | `com.aicode.settings.UnitTestSettingsState` | — | plugin.xml |
| — | `com.aicode.settings.BatchUnitTestSettingsState` | — | plugin.xml |
| — | `com.aicode.apm.OpenTelemetryService` | — | plugin.xml |
| — | `com.aicode.settings.AICodeRequestSettings` | — | plugin.xml |
| — | `com.aicode.status.AICodeStatusService` | — | plugin.xml |
| — | `com.aicode.service.editor.DocumentActionTracker` | — | plugin.xml |
| `com.aicode.service.EditorManagerService` | `com.aicode.service.editor.EditorManagerServiceImpl` | `ai.code.plugin.editor.AICodeEditorTestManager` | plugin.xml |
| `com.aicode.service.RequestTipService` | `com.aicode.service.editor.RequestTipServiceImpl` | `ai.code.plugin.generate.AICodeCompletionTestService` | plugin.xml |
| `com.aicode.agent.service.PluginAgentProcessService` | `com.aicode.agent.service.RestartableAgentProcessService` | — | plugin.xml |
| — | `com.aicode.inline.InlineChatService` | — | plugin.xml |

### 3.2 Project Service

| 接口 | 实现类 | Test 实现 | 来源 |
|---|---|---|---|
| — | `com.aicode.action.batch.ResultTree` | — | plugin.xml |

---

## 4. Extension 注册

### 4.1 `com.intellij` 命名空间

#### postStartupActivity

| 实现类 | 来源 |
|---|---|
| `com.aicode.PluginStartupActivity` | plugin.xml |

#### applicationService

（见第 3 节 Service 注册表）

#### projectService

（见第 3 节 Service 注册表）

#### toolWindow

| ID | Anchor | Icon | Factory Class | 来源 |
|---|---|---|---|---|
| 星火飞码 iFlyCode | right | `com.aicode.icons.Icons.PluginIcon` | `com.aicode.toolwindow.ProjectToolWindowFactory` | plugin.xml |

#### jvm.exceptionFilter

| 实现类 | 来源 |
|---|---|
| `com.aicode.error.search.DebuggerFilter` | plugin.xml |

#### intentionAction

| Class Name | 来源 |
|---|---|
| `com.aicode.action.CodeProblemsIntentionAction` | plugin.xml |

#### statusBarWidgetFactory

| ID | 实现类 | 来源 |
|---|---|---|
| `ai.code.plugin` | `com.aicode.statusBar.StatusBarWidgetFactory` | plugin.xml |

#### typedHandler

| 实现类 | Order | 来源 |
|---|---|---|
| `com.aicode.service.editor.TipTypedHandlerDelegate` | first, before completionAutoPopup | plugin.xml |

#### actionPromoter

| 实现类 | Order | 来源 |
|---|---|---|
| `com.aicode.action.CodePromoterAction` | last | plugin.xml |
| `com.aicode.action.TipPromoterAction` | last | plugin.xml |

#### notificationGroup

| ID | Display Type | 来源 |
|---|---|---|
| `aicode.notice` | BALLOON | plugin.xml |

#### checkinHandlerFactory

| 实现类 | 来源 |
|---|---|
| `com.aicode.listener.CommitHandlerFactory` | plugin.xml |

#### codeInsight.inlayProvider（可选配置文件）

| Language | 实现类 | 来源 |
|---|---|---|
| JAVA | `com.aicode.toolwindow.PluginEditorInlayHintsProvider` | code-java.xml |
| Python | `com.aicode.toolwindow.PluginEditorInlayHintsProvider` | code-python.xml |
| JavaScript | `com.aicode.toolwindow.PluginEditorInlayHintsProvider` | code-javascript.xml |

### 4.2 `ai.code.plugin` 命名空间

#### languageIdSupport

| 实现类 | Order | 来源 |
|---|---|---|
| `com.aicode.language.AICodeExtendedLanguageSupport` | — | plugin.xml |
| `com.aicode.language.CodeLanguageInfoSupport` | last | plugin.xml |

---

## 5. Listener 注册

### 5.1 Application Listeners

| Topic | Class | activeInTestMode | activeInHeadlessMode | 来源 |
|---|---|---|---|---|
| `com.intellij.openapi.actionSystem.ex.AnActionListener` | `com.aicode.service.editor.DocumentActionTracker$ActionListener` | true | true | plugin.xml |
| `com.intellij.ide.plugins.DynamicPluginListener` | `com.aicode.listener.AICodeUnloadPluginListener` | — | — | plugin.xml |
| `com.intellij.ide.AppLifecycleListener` | `com.aicode.listener.ApplicationStartupListener` | — | — | plugin.xml |
| `com.intellij.openapi.project.ProjectManagerListener` | `com.aicode.listener.PluginManagerListener` | — | — | plugin.xml |

> 注：`com.aicode.complete.InlayListener` / `com.aicode.complete.InlayGotItListener` 已被注释掉，未注册。

### 5.2 Project Listeners

| Topic | Class | 来源 |
|---|---|---|
| `com.intellij.codeInsight.lookup.LookupManagerListener` | `com.aicode.listener.CodeLookupManagerListener` | plugin.xml |
| `com.intellij.openapi.command.CommandListener` | `com.aicode.listener.AutoCodeGenerateListener` | plugin.xml |
| `com.intellij.openapi.fileEditor.FileEditorManagerListener` | `com.aicode.listener.CodeFileEditorManagerListener` | plugin.xml |
| `com.aicode.agent.SocketMessageListener` | `com.aicode.agent.SocketMessageHandleListener` | plugin.xml |

---

## 6. ExtensionPoint 声明

| Qualified Name | Interface | Dynamic | 来源 |
|---|---|---|---|
| `ai.code.plugin.languageIdSupport` | `com.aicode.service.LanguageInfoSupport` | true | plugin.xml |
| `ai.code.plugin.editorSupport` | `com.aicode.service.EditorSupport` | true | plugin.xml |

---

## 7. Component 声明

### 7.1 Project Components

| 实现类 | 来源 |
|---|---|
| `com.aicode.listener.PluginDocumentListener` | plugin.xml |

### 7.2 Application Components

| 实现类 | 来源 |
|---|---|
| `com.aicode.listener.ThemeChangeListener` | plugin.xml |

---

## 8. 可选配置文件详情

### 8.1 code-java.xml

**触发条件**: 加载 `com.intellij.modules.java` 模块时生效
**额外依赖**: `JUnit`, `Coverage`, `com.intellij.modules.coverage`

| Extension Point | Language | 实现类 |
|---|---|---|
| `codeInsight.inlayProvider` | JAVA | `com.aicode.toolwindow.PluginEditorInlayHintsProvider` |

### 8.2 code-python.xml

**触发条件**: 加载 `com.intellij.modules.python` 模块时生效

| Extension Point | Language | 实现类 |
|---|---|---|
| `codeInsight.inlayProvider` | Python | `com.aicode.toolwindow.PluginEditorInlayHintsProvider` |

### 8.3 code-javascript.xml

**触发条件**: 加载 `com.intellij.modules.webstorm` 模块时生效

| Extension Point | Language | 实现类 |
|---|---|---|
| `codeInsight.inlayProvider` | JavaScript | `com.aicode.toolwindow.PluginEditorInlayHintsProvider` |

---

## 9. 注册统计汇总

| 类别 | 数量 |
|---|---|
| Action（独立） | 13 |
| Action（InlineChat 内） | 5 |
| Action Group | 5 |
| Application Service | 11 |
| Project Service | 1 |
| Extension (com.intellij) | 12 |
| Extension (ai.code.plugin) | 2 |
| Extension (可选配置文件) | 3 |
| Application Listener | 4 |
| Project Listener | 4 |
| ExtensionPoint | 2 |
| Project Component | 1 |
| Application Component | 1 |
| **总计注册项** | **53** |

---

## 10. 注释掉的注册项（未生效）

| 类型 | 内容 | 来源 |
|---|---|---|
| Listener | `com.aicode.complete.InlayListener` / `com.aicode.complete.InlayGotItListener` | plugin.xml (注释) |
| Extension | `codeInsight.inlayProvider` language=JAVA, `com.aicode.toolwindow.JavaPluginEditorInlayHintsProvider` | plugin.xml (注释) |

> 注：JAVA 的 inlayProvider 在 plugin.xml 中被注释，但通过 code-java.xml 可选配置文件以 `PluginEditorInlayHintsProvider` 类名重新注册。
