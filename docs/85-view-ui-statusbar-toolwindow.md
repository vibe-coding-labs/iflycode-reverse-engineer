# iFlyCode Plugin: View, UI, StatusBar, ToolWindow Complete Analysis

## 1. Complete Class Inventory

### 1.1 com.aicode.view Package (WebView/JCEF Layer)

| Class | Type | Extends/Implements | Description |
|-------|------|-------------------|-------------|
| `WebViewWindowPanel` | class | `JPanel` | **God Object** - Main WebView panel hosting JCEF browser |
| `WebViewWindowPanel$c` | inner | `MouseAdapter` | Mouse click handler - opens external browser URL |
| `WebViewWindowPanel$D` | inner | `CefLifeSpanHandlerAdapter` | Browser lifecycle handler - registers custom scheme |
| `WebViewWindowPanel$K` | inner | synthetic | `$SwitchMap$com$aicode$agent$enums$ModuleEnum` - switch map for ModuleEnum |
| `WebViewWindowPanel$M` | inner | `CefLoadHandlerAdapter` | Load handler - injects JS bridge on page load |
| `PluginToolWindowPanel` | class | `SimpleToolWindowPanel` | ToolWindow panel wrapper containing WebViewWindowPanel |
| `CustomResourceHandler` | class | `CefResourceHandler` | Custom CEF resource handler for plugin resources |
| `CustomSchemeHandlerFactory` | class | `CefSchemeHandlerFactory` | Factory creating CustomResourceHandler instances |
| `OpenedConnection` | class | `ResourceHandlerState` | URL connection wrapper for CEF resource loading |
| `ResourceHandlerState` | interface | - | Interface for resource read/close/headers |

### 1.2 com.aicode.ui Package (UI Components)

| Class | Type | Extends/Implements | Description |
|-------|------|-------------------|-------------|
| `ActionButton` | class | `Object` | Factory for IntelliJ ActionButton with deobfuscation |
| `Font` | class (object) | `Object` | Font size constants (medium/small/large/xSmall/xLarge/xxLarge) |
| `FontKt` | class (Kotlin) | `Object` | Font utility extensions (bold/italic/plain/widthForFont) |
| `RoundLineBorder` | class | `LineBorder` | Rounded line border component |
| `SendStopActionButtonPanel` | class | `JPanel` | CardLayout panel switching between Send/Stop buttons |
| `Style` | class (object) | `Object` | Style constants container |
| `Style$Colors` | class (object) | `Object` | Color constants (BLUE, GREY, SEPARATOR_COLOR) |
| `Style$Colors$InlineChat` | class (object) | `Object` | Inline chat colors (background, border) |
| `Style$Borders` | class (object) | `Object` | Border constants (messageHeaderBorder, topMessageBorder) |

### 1.3 com.aicode.statusBar Package (Status Bar)

| Class | Type | Extends/Implements | Description |
|-------|------|-------------------|-------------|
| `StatusBarPopup` | class | `EditorBasedStatusBarPopup` | Status bar popup widget for AI Code status |
| `StatusBarWidgetFactory` | class | `StatusBarEditorBasedWidgetFactory` | Factory creating StatusBarPopup instances |

### 1.4 com.aicode.status Package (Status Service)

| Class | Type | Extends/Implements | Description |
|-------|------|-------------------|-------------|
| `AICodeStatusService` | class | `AICodeStatusListener, Disposable` | Application-level status management service |

### 1.5 com.aicode.toolwindow Package (Tool Window)

| Class | Type | Extends/Implements | Description |
|-------|------|-------------------|-------------|
| `ProjectToolWindowFactory` | class | `ToolWindowFactory, DumbAware` | Main tool window factory |
| `CheckGutterIconRenderer` | class | `GutterIconRenderer` | Gutter icon renderer for code check results |
| `CheckGutterIconRenderer$1` | inner | `ActionGroup` | Popup menu action group for gutter icon |
| `CheckGutterIconRenderer$1$1` | inner | `AnAction` | Individual action in gutter popup menu |
| `CheckGutterIconRenderer$2` | inner | synthetic | `$SwitchMap$com$aicode$agent$enums$CommandEnum` |
| `PluginEditorInlayHintsProvider` | class | `InlayHintsProvider` | Inlay hints provider for code actions |
| `PluginEditorInlayHintsProvider$1` | inner | `FactoryInlayHintsCollector` | Collector for inlay hints |
| `PluginEditorInlayHintsProvider$2` | inner | - | Settings panel provider |
| `PluginEditorInlayHintsProvider$3` | inner | - | Settings panel UI |
| `PluginEditorInlayHintsProvider$InlResult` | interface | - | Click callback interface |
| `PluginEditorInlayHintsProvider$InlCollectResult` | interface | - | Group click callback interface |
| `PluginHintSettings` | class | `Object` | Inlay hints settings (empty placeholder) |

### 1.6 com.aicode.diff Package (Diff Dialog)

| Class | Type | Extends/Implements | Description |
|-------|------|-------------------|-------------|
| `DiffDialog` | class | `DialogWrapper` | Diff comparison dialog with Accept/Reject actions |

### 1.7 com.aicode.test Package (Test Dialogs)

| Class | Type | Extends/Implements | Description |
|-------|------|-------------------|-------------|
| `UnitTestDialog` | class | `DialogWrapper` | Unit test generation configuration dialog |

### 1.8 com.aicode.action.batch Package (Batch Dialogs)

| Class | Type | Extends/Implements | Description |
|-------|------|-------------------|-------------|
| `BatchUnitTestDialog` | class | `DialogWrapper` | Batch unit test generation dialog |

### 1.9 com.aicode.listener Package (Theme)

| Class | Type | Extends/Implements | Description |
|-------|------|-------------------|-------------|
| `ThemeChangeListener` | class | `ApplicationComponent` | Theme change listener and WebView theme sync |

### 1.10 com.aicode.inline Package (Inline Chat UI)

| Class | Type | Extends/Implements | Description |
|-------|------|-------------------|-------------|
| `InlineChatPanel` | class | `JPanel, EditorCustomElementRenderer, KeyStrokeHandler, Disposable` | Main inline chat panel |
| `InlineChatInputPanel` | class | `JPanel` | Input panel with text area and send/stop buttons |
| `InlineChatInputComponent` | class | `JBTextArea` | Text input area with custom key handling |
| `InlineChatInlay` | class (object) | `Object` | Inlay model for inline chat positioning |
| `InlineChatTopPanel` | class | `JPanel` | Top bar with close button for inline chat |

---

## 2. UI Component Hierarchy

```
IntelliJ Platform
+-- ToolWindow (created by ProjectToolWindowFactory)
    +-- PluginToolWindowPanel (SimpleToolWindowPanel)
        +-- WebViewWindowPanel (JPanel)  <-- GOD OBJECT
            +-- JBCefBrowser (JCEF Chromium browser)
            +-- JBCefJSQuery (Java<->JS bridge)
            +-- WebViewWindowPanel$D (CefLifeSpanHandlerAdapter)
            +-- WebViewWindowPanel$M (CefLoadHandlerAdapter)
            +-- WebViewWindowPanel$c (MouseAdapter)
            +-- WebViewWindowPanel$K (ModuleEnum switchmap)

StatusBar
+-- StatusBarWidgetFactory
    +-- StatusBarPopup (EditorBasedStatusBarPopup)
        +-- AICodeStatusService (status management)
        +-- Icons.StatusBarIcon* (status icons)

Editor
+-- CheckGutterIconRenderer (GutterIconRenderer)
    +-- CheckGutterIconRenderer$1 (ActionGroup popup)
        +-- CheckGutterIconRenderer$1$1 (AnAction items)
+-- PluginEditorInlayHintsProvider (InlayHintsProvider)
    +-- PluginEditorInlayHintsProvider$1 (FactoryInlayHintsCollector)
+-- InlineChatInlay (Inlay model)
    +-- InlineChatPanel (JPanel)
        +-- InlineChatTopPanel (JPanel)
            +-- CloseInlineChatAction button
        +-- InlineChatInputPanel (JPanel)
            +-- InlineChatInputComponent (JBTextArea)
            +-- SendStopActionButtonPanel (JPanel)
                +-- SendMessageAction button
                +-- StopAction button

Dialogs
+-- DiffDialog (DialogWrapper)
    +-- DiffRequestPanel (IntelliJ diff viewer)
+-- UnitTestDialog (DialogWrapper)
    +-- JBCheckBox, JRadioButton, ComboBox controls
+-- BatchUnitTestDialog (DialogWrapper)
    +-- JBCheckBox, JRadioButton, ComboBox, TextFieldWithBrowseButton
```

---

## 3. WebViewWindowPanel - God Object Deep Analysis

### 3.1 Class Signature
```
public synchronized class com.aicode.view.WebViewWindowPanel extends javax.swing.JPanel
Version: 55.0 (Java 11)
```

### 3.2 Fields (Deobfuscated Names)

| Obfuscated Name | Actual Type | Deobfuscated Purpose |
|----------------|------------|---------------------|
| `try` | `JBCefJSQuery` | JS query handler for Java<->JS communication |
| `float` | `Project` | IntelliJ project reference |
| `byte` | `JBCefBrowser` | JCEF browser instance |
| `enum` | `String` | Static URL/path string |
| `isLoaded` | `AtomicBoolean` | Whether WebView page has finished loading |
| `final` (static) | `Logger` | SLF4J logger |

### 3.3 Static DataKey Fields (Public)

| Field | Type | Purpose |
|-------|------|---------|
| `UNIT_TEST_METHOD_DATA` | `Key` | Data key for unit test method data |
| `CODE_DEBUG_AGENT_DATA` | `Key` | Data key for code debug agent data |
| `UNIT_TEST_MESSAGE_DATA` | `Key` | Data key for unit test message data |
| `CODE_DEBUG_MESSAGE_DATA` | `Key` | Data key for code debug message data |
| `OPEN_PAGE_DATA` | `Key` | Data key for page open data |
| `CODE_MESSAGE_DATA` | `Key` | Data key for code message data |
| `WEB_VIEW_PANEL` | `Key` | Data key for WebView panel reference |

### 3.4 Key Methods (Deobfuscated)

| Obfuscated Name | Signature | Deobfuscated Purpose |
|----------------|-----------|---------------------|
| `true()` | `JBCefBrowser` | Creates/configures JCEF browser instance |
| `else(String)` | `void` | JS callback handler - delegates to `handleRequest()` |
| `handleRequest(String)` | `void` | Processes incoming JS messages |
| `goto()` | `void` | Navigation/page initialization |
| `for()` | `ResourceHandlerState` | Gets current resource handler state |
| `super(ResourceHandlerState)` | `void` | Sets resource handler state |

### 3.5 Browser Creation Flow (true() method)

```
1. Check IDE baseline version >= 211
2. If >= 211: Use JBCefBrowser via reflection (deobfuscated class name)
   - Create JBCefBrowser instance
   - Call setOffScreenRendering(false) via reflection
3. If < 211: Fallback browser creation
4. Create JBCefJSQuery for Java<->JS bridge
5. Register CefLifeSpanHandler (WebViewWindowPanel$D)
6. Register CefLoadHandler (WebViewWindowPanel$M)
7. Add MouseAdapter (WebViewWindowPanel$c)
8. Return browser instance
```

### 3.6 Services Directly Operating on WebViewWindowPanel

Based on bytecode analysis, the following services interact with WebViewWindowPanel:

1. **ChatService** - Sends chat messages to WebView
2. **CodeCheckService** - Sends code check results to WebView
3. **CodeCompleteService** - Sends completion data to WebView
4. **CodeSearchService** - Sends search results to WebView
5. **CommonService** - Sends common/plugin info to WebView
6. **GitReviewService** - Sends git review data to WebView
7. **InlineChatCommandService** - Sends inline chat commands
8. **RestartableAgentProcessService** - Pushes agent refresh to WebView
9. **SqlService** - Sends SQL chat data to WebView
10. **UserService** - Sends user login/status to WebView
11. **SocketMessageHandleListener** - Routes WebSocket messages to WebView
12. **ThemeChangeListener** - Sends theme changes to WebView

---

## 4. WebView Interaction Flow

### 4.1 Java -> JS (Push to WebView)

```
Service Layer
    |
    v
SocketMessageHandleListener.send2Web(Project, Object)
    |
    v
WebViewWindowPanel.handleRequest(String jsonData)
    |
    v
JBCefBrowser.executeJavaScript(jsCode, url, line)
    |
    v
JavaScript in WebView (window.receiveMessage or similar)
```

The `handleRequest()` method serializes data as JSON and calls JavaScript
in the browser to update the UI. The ModuleEnum switchmap ($K) maps:
- CODE_SEARCH -> 1
- UNIT_TEST -> 2
- BATCH_UNIT_TEST -> 3
- UNIT_TESTING -> 4
- LOG -> 5
- CHAT -> 6
- LOGIN -> 7
- COMMON -> 8
- SETTING -> 9
- SQL_CHAT -> 10
- CODE_CHECK -> 11
- GIT_VIEW -> 12

### 4.2 JS -> Java (Pull from WebView)

```
JavaScript in WebView
    |
    v
JBCefJSQuery.invoke("callbackId", "jsonData")
    |
    v
WebViewWindowPanel.else(String)  [synthetic bridge]
    |
    v
WebViewWindowPanel.handleRequest(String)
    |
    v
Service Layer (routes to appropriate service)
```

### 4.3 JS Bridge Injection Flow (WebViewWindowPanel$M.onLoadEnd)

```
1. Check HTTP status code == 200
2. Inject JBCefJSQuery handler into browser:
   - jsQuery.inject("window.javaBridge") -> JavaScript wrapper
   - browser.executeJavaScript(injectedScript, url, 0)
3. If page not yet loaded AND user not in "goTo" state:
   - Send USER_LOGIN command via WebSocket
4. Set isLoaded = true
5. Initialize theme via ThemeChangeListener.initTheme()
6. If agent refresh pending:
   - Push agent refresh to WebView
   - Clear refresh flag
7. Call goto() for navigation
8. Send plugin info via CommonService.getPluginInfo()
```

### 4.4 Custom Scheme Handler Flow

```
Browser requests resource
    |
    v
CustomSchemeHandlerFactory.create(browser, frame, scheme, request)
    |
    v
CustomResourceHandler(project)
    |
    v
CustomResourceHandler.processRequest(request, callback)
    |
    v
1. Parse URL: remove scheme prefix, split by separator
2. Resolve resource via ClassLoader.getResource()
3. Handle special file types (set MIME type):
   - .js files -> "application/javascript"
   - .css files -> "text/css"
   - .html files -> "text/html"
   - .svg files -> "image/svg+xml"
4. Create OpenedConnection from URL
5. Set ResourceHandlerState
6. callback.Continue()
    |
    v
OpenedConnection.readResponse() -> streams data to browser
```

---

## 5. StatusBar State Management Flow

### 5.1 AICodeStatusService

```
Application-level singleton service (via ApplicationManager.getService())

Fields:
- float: AICodeStatus (current status enum)
- byte: Object (synchronization monitor)
- enum: String (status detail text)

Key Methods:
- getCurrentStatus() -> Pair<AICodeStatus, String>
- notifyApplication(AICodeStatus) / notifyApplication(AICodeStatus, String)
- onAICodeStatus(AICodeStatus, String) - listener callback
- dispose()
```

### 5.2 Status Update Flow

```
Service detects status change
    |
    v
AICodeStatusService.notifyApplication(AICodeStatus, detailText)
    |
    v
Application.getMessageBus().syncPublisher(TOPIC)
    .onAICodeStatus(status, detailText)
    |
    v
AICodeStatusService.onAICodeStatus() [self-subscribed]
    |
    v
1. Synchronized: Update internal status + detail text
2. If status changed: call bB() -> update UI
    |
    v
bB() -> bC() on EDT:
    |
    v
For each open project:
    StatusBarPopup.update(project, detailText)
    |
    v
StatusBarPopup.getWidgetState(virtualFile)
    |
    v
Returns WidgetState based on:
- If apiKey is blank -> NotSignedIn state with StatusBarIconNotSignedIn
- If status.isIconAlwaysShown() -> show with status icon
- If no virtualFile -> HIDDEN
- If autoTrigger enabled -> show with status icon
- If CompletionInProgress -> show with spinning icon
- Otherwise -> show with disabled icon
```

### 5.3 StatusBarPopup Widget States

| Condition | Widget State | Icon |
|-----------|-------------|------|
| No API key | NotSignedIn, clickable | StatusBarIconNotSignedIn |
| Status icon always shown | Shown, clickable | Status.getIcon() |
| No file open | HIDDEN | - |
| Auto-trigger enabled | Shown, clickable | Status.getIcon() |
| Completion in progress | Shown, clickable | StatusBarCompletionInProgress |
| Auto-trigger disabled | Shown, clickable | StatusBarIconDisabled |

### 5.4 StatusBarPopup Popup Menu

```
StatusBarPopup.createPopup(dataContext)
    |
    v
Oa(dataContext, isPinned=false)
    |
    v
1. Get current status from AICodeStatusService
2. If Unsupported -> return null (no popup)
3. Get action ID from pC(status) -> deobfuscated action group ID
4. Look up ActionGroup from ActionManager
5. Create JBPopupFactory.createActionGroupPopup()
```

---

## 6. ToolWindow Lifecycle

### 6.1 ProjectToolWindowFactory

```
Implements: ToolWindowFactory, DumbAware

Constructor:
1. InlineChatInlay.INSTANCE.register() - Register inline chat inlay
2. OpenInlineChatAction.Companion.register() - Register inline chat action

createToolWindowContent(Project, ToolWindow):
1. If ACTIVITY_STARTED -> toolWindow.show(), else toolWindow.hide()
2. Set title actions: [RefreshAction]
3. Create PluginToolWindowPanel(project, toolWindow.disposable)
4. Get ContentManager and ContentFactory
5. Create content from panel's content component
6. Add content to ContentManager
```

### 6.2 PluginToolWindowPanel

```
Extends: SimpleToolWindowPanel

Fields:
- byte: Disposable (parent disposable)
- enum: Project (project reference)

Constructor(Project, Disposable):
1. super(true) - vertical layout
2. Store project and disposable references
3. Create WebViewWindowPanel(project)
4. setContent(webViewWindowPanel) - set as content component
```

### 6.3 ToolWindow Lifecycle Diagram

```
IDE Startup
    |
    v
ProjectToolWindowFactory.<init>()
    |-- InlineChatInlay.register()
    |-- OpenInlineChatAction.register()
    |
    v
Project Opened
    |
    v
createToolWindowContent(project, toolWindow)
    |
    v
PluginToolWindowPanel(project, disposable)
    |
    v
WebViewWindowPanel(project)
    |-- Create JBCefBrowser
    |-- Create JBCefJSQuery
    |-- Register CefLifeSpanHandler ($D)
    |   |-- onAfterCreated: Register CustomSchemeHandlerFactory
    |-- Register CefLoadHandler ($M)
    |   |-- onLoadEnd: Inject JS bridge, trigger login, init theme
    |-- Register MouseAdapter ($c)
    |
    v
Browser Loads Page
    |
    v
CustomResourceHandler serves resources
    |
    v
JS Bridge Established
    |
    v
User Interacts with WebView
    |
    v
Project Closed
    |
    v
Disposable.dispose() chain
```

---

## 7. Inline Chat UI Architecture

### 7.1 InlineChatPanel

```
Extends: JPanel, EditorCustomElementRenderer, KeyStrokeHandler, Disposable

Fields:
- byte: Editor (current editor)
- case: InlineChatInputPanel (input area)
- float: InlineChatTopPanel (top bar with close button)
- try: JPanel (content panel)
- final: JComponent (renderer component)
- enum: Inlay (editor inlay reference)
- if: Container (parent container)
```

### 7.2 InlineChatInputPanel

```
Extends: JPanel

Fields:
- super: InlineChatPanel (parent panel)
- final: Editor (current editor)
- long: InlineChatInputComponent (text area)
- try: SendStopActionButtonPanel (send/stop buttons)
- case: ChatInputController (input controller)
- float: EphemeralChatSessionController (session controller)
- byte: ComboBox (model selector)
- new, for, if, enum: GridBagConstraints (layout constraints)
```

### 7.3 InlineChatInputComponent

```
Extends: JBTextArea

Fields:
- try: AbstractAction (submit action)
- byte: AbstractAction (newline action)
- float: int (some counter/flag)
- enum: JLabel (character count or status label)

Key behavior:
- processKeyEvent() - Custom key handling for Ctrl+Arrow navigation
- Handles Enter for submit, Shift+Enter for newline
```

### 7.4 InlineChatTopPanel

```
Extends: JPanel

Layout: GridBagLayout
- Position (0,0): InlineChatInputPanel (weightx=1.0, fill=HORIZONTAL, anchor=EAST)
- Position (1,0): CloseInlineChatAction button (anchor=EAST, left inset=5)

Background: Style.Colors.InlineChat.background
Border: EmptyBorder(1,1,0,0)
FocusListener: InlineChatInputBorderFocusListener on input component
```

### 7.5 SendStopActionButtonPanel

```
Extends: JPanel
Layout: CardLayout

Cards:
- "stop" card: StopAction button (byte field)
- "send" card: SendMessageAction button (case field)

Methods:
- showStopButton() -> CardLayout.show("stop"), enable both
- showSendButton(Function0<Boolean> isEnabled) -> CardLayout.show("send"), set enabled state
```

---

## 8. Editor Integration: Gutter Icons and Inlay Hints

### 8.1 CheckGutterIconRenderer

```
Extends: GutterIconRenderer

Fields:
- presentationDataDto: PresentationDataDto (check result data)
- type: String (check type)
- highlighter: RangeHighlighter (editor highlighter)
- editor: Editor (current editor)
- lineNumber: int (line number)
- commandEnums: List<CommandEnum> (available actions)
- anActions: AnAction[] (cached action array)

Icon: toolWindow.svg / toolWindow_dark.svg based on theme
Alignment: LEFT
Tooltip: "" (empty)

Click Action:
1. Get CodeInfoDto from presentationDataDto
2. Get range (start/end line)
3. Jump to file via CommonService.jumpToFileByIndex()

Popup Menu Actions:
- Creates action group from commandEnums
- Supported commands: CODE_TEST, CODE_EXPLAIN, CODE_OPTIMIZE,
  CODE_SPLIT, CODE_COMMENT, CODE_INLINE_COMMENT
- Each creates CheckGutterIconRenderer$1$1 action

handleActionPerformed(project, commandEnum):
1. Get code content via PsiDocumentManager
2. Switch on commandEnum:
   - CODE_TEST: If Java -> handleUnitTest(); If C/C++/Python -> CppTestService
   - CODE_EXPLAIN/CODE_OPTIMIZE/CODE_SPLIT/CODE_COMMENT/CODE_INLINE_COMMENT:
     -> PluginEditorInlayHintsProvider.handleAction()
```

### 8.2 PluginEditorInlayHintsProvider

```
Implements: InlayHintsProvider

Settings: PluginHintSettings (empty placeholder)
Key: SettingsKey for persistence

Collector: PluginEditorInlayHintsProvider$1 (FactoryInlayHintsCollector)

collect() method logic:
1. If no API key -> return true (skip)
2. Get VirtualFile from editor
3. Check if element is PsiMethod/PyFunction/JSFunction/TSFunction
4. If PsiTypeParameter -> skip
5. If not PsiMethod and lineToolsType != LINE -> skip
6. If invalid Java method -> skip
7. Get editor actions from PermissionEnum.getEditorAction()
8. If no actions -> skip
9. Get anchor offset for inlay
10. If line count >= 20 and CODE_SPLIT in actions -> remove CODE_SPLIT
11. If PsiMethod and lineToolsType == LINE:
    -> addLineAction() (line-level action buttons)
12. If PsiMethod and lineToolsType == ICON:
    -> addGroupAction() (icon group action)
13. If not PsiMethod:
    -> addLineAction() (line-level for non-Java)
```

---

## 9. Dialog Analysis

### 9.1 DiffDialog

```
Extends: DialogWrapper

Fields:
- byte: Project
- enum: SimpleDiffRequest

Constructor(Project, SimpleDiffRequest):
- Set title from BasicActionsBundle
- Call init()

createCenterPanel():
- Create JPanel with BorderLayout
- Create DiffRequestPanel via DiffManager
- Set diff request
- Add BOTTOM_PANEL context hint

createActions():
- OK action: "Accept" (deobfuscated)
- Cancel action: "Reject" (deobfuscated)
- Order: [Reject, Accept]

doOKAction():
1. Get left/right VirtualFiles from request UserData
2. Get suggested code from DIFF_SUGGEST_CODE key
3. Read left file content as UTF-8
4. Remove CR characters
5. Write content to right file via WriteCommandAction
6. Track accept count via EditorManagerServiceImpl.acceptCount()
7. Close dialog
```

### 9.2 UnitTestDialog

```
Extends: DialogWrapper

Fields:
- char: JBCheckBox (generate by template checkbox)
- int: JRadioButton (template enabled radio)
- new: String (selected template)
- long: ComboBox (model selector)
- super: ComboBox (language selector)
- for: JRadioButton (template disabled radio)
- if: JLabel (info label)
- case: ExcludeMethodConfigurable (method exclusion config)
- try: String (source code)
- float: JPanel (content panel)
- byte: Project
- enum: JPanel (settings panel)

Key features:
- Model selection ComboBox
- Language selection ComboBox
- Template enable/disable radio buttons
- Method exclusion configuration
- Generate by template switch
```

### 9.3 BatchUnitTestDialog

```
Extends: DialogWrapper

Fields (28 total, heavily obfuscated):
- Multiple ComboBox controls (model, language, framework, etc.)
- JRadioButton controls (template on/off)
- JBCheckBox controls
- JBTextField (custom template path)
- TextFieldWithBrowseButton (file browser)
- ExcludeMethodConfigurable
- Multiple JPanel sections
- Project and Module references
```

---

## 10. Theme Change System

### 10.1 ThemeChangeListener

```
Implements: ApplicationComponent

Fields:
- float: int (previous font size)
- byte: String (previous LAF name)
- enum: Logger

initComponent():
- Subscribe to LafManagerListener.TOPIC (look and feel changes)
- Subscribe to EditorColorsManager.TOPIC (color scheme changes)

Theme Change Flow:
1. Detect LAF change via LafManager
2. Get new LookAndFeel name via reflection
3. If previous LAF is blank -> store current LAF
4. If font size not set -> store current console font size
5. If LAF name changed -> call changeTheme()
6. Update stored LAF name

changeTheme(themeName, fontSize):
1. Get tool window name from BasicActionsBundle
2. For each valid project:
   a. Create theme JSON object:
      - "theme": getTheme(themeName, toolWindow) -> "dark" or "light"
      - "fontSize": fontSize value
      - "type": SETTING_CHANGE_THEME
      - "data": theme object
   b. Send to WebView via SocketMessageHandleListener.send2Web()
   c. Update StatusBarPopup

getTheme(themeName, toolWindow):
- If themeName contains "dark" (deobfuscated):
  - Set StatusBarIcon to dark variant
  - Set ToolWindow icon to dark variant
  - Return "dark"
- Otherwise:
  - Set StatusBarIcon to light variant
  - Set ToolWindow icon to light variant
  - Return "light"

initTheme() (called on WebView load):
- Get current global scheme name and font size
- Call changeTheme() to sync WebView with IDE theme
```

---

## 11. Style System Constants

### 11.1 Style.Colors

| Property | Light RGB | Dark RGB | Description |
|----------|-----------|----------|-------------|
| BLUE | 5083390 (0x4D7F7E) | Same | Blue accent color |
| GREY | 13290708 (0xCAD7D4) | 5198166 (0x4F5E56) | Grey text color |
| SEPARATOR_COLOR | Gray.xCD/Gray.x4D | Named color | Separator line color |

### 11.2 Style.Colors.InlineChat

| Property | Description |
|----------|-------------|
| background | Light: 16382715 (0xFAEBEB), Dark: named color "Editor.SearchField" from Gray.x99/Gray.x78 |
| border | SEPARATOR_COLOR.darker() / SEPARATOR_COLOR |

### 11.3 Style.Borders

| Property | Insets (top, left, bottom, right) |
|----------|----------------------------------|
| messageHeaderBorder | (16, 1, 12, 12) |
| topMessageBorder | (1, 1, 16, 0) |

### 11.4 Font Sizes

| Property | Size Modifier |
|----------|--------------|
| medium | JBFont.label().plain() (base) |
| small | medium.lessOn(1.0) |
| large | medium.biggerOn(1.0) |
| xSmall | medium.lessOn(2.0) |
| xLarge | medium.biggerOn(3.0) |
| xxLarge | medium.biggerOn(5.0) |

---

## 12. Complete Bytecode Disassembly

### 12.1 WebViewWindowPanel (Main Class)

```
public synchronized class com.aicode.view.WebViewWindowPanel extends javax.swing.JPanel
Version: 55.0

Fields:
  private static final Lorg/slf4j/Logger; final
  public static final Lcom/intellij/openapi/util/Key; UNIT_TEST_METHOD_DATA
  private Lcom/intellij/ui/jcef/JBCefJSQuery; try          // jsQuery
  private final Lcom/intellij/openapi/project/Project; float // project
  public static final Lcom/intellij/openapi/util/Key; CODE_DEBUG_AGENT_DATA
  private Lcom/intellij/ui/jcef/JBCefBrowser; byte          // browser
  public static final Lcom/intellij/openapi/util/Key; UNIT_TEST_MESSAGE_DATA
  public static final Lcom/intellij/openapi/util/Key; CODE_DEBUG_MESSAGE_DATA
  public static final Lcom/intellij/openapi/util/Key; OPEN_PAGE_DATA
  public final Ljava/util/concurrent/atomic/AtomicBoolean; isLoaded
  private static Ljava/lang/String; enum                    // url/path
  public static final Lcom/intellij/openapi/util/Key; CODE_MESSAGE_DATA
  public static final Lcom/intellij/openapi/util/Key; WEB_VIEW_PANEL

Methods:
  private synthetic void else(java.lang.String)
    // JS callback bridge -> handleRequest(String)

  private com.intellij.ui.jcef.JBCefBrowser true()
    // Browser creation with version check (>=211 uses JBCefBrowser)
    // Creates JBCefJSQuery, registers handlers

  // ... (full bytecode in saved tool results)
```

### 12.2 WebViewWindowPanel$c (MouseAdapter)

```
public synchronized class com.aicode.view.WebViewWindowPanel$c extends java.awt.event.MouseAdapter

Fields:
  public final synthetic Lcom/aicode/view/WebViewWindowPanel; enum  // outer this

Methods:
  public void mouseClicked(java.awt.event.MouseEvent)
    // Opens external browser URL via BrowserUtil.browse()
    // URL from BasicActionsBundle.message() (deobfuscated)
```

### 12.3 WebViewWindowPanel$D (CefLifeSpanHandlerAdapter)

```
public synchronized class com.aicode.view.WebViewWindowPanel$D extends org.cef.handler.CefLifeSpanHandlerAdapter

Fields:
  public final synthetic Lcom/aicode/view/WebViewWindowPanel; enum  // outer this

Methods:
  public void onAfterCreated(org.cef.browser.CefBrowser)
    // Registers CustomSchemeHandlerFactory with CefApp
    // Scheme: deobfuscated via IdeAction.H() and FontKt.H()
    // Factory: CustomSchemeHandlerFactory(project)
```

### 12.4 WebViewWindowPanel$M (CefLoadHandlerAdapter)

```
public synchronized class com.aicode.view.WebViewWindowPanel$M extends org.cef.handler.CefLoadHandlerAdapter

Fields:
  public final synthetic Lcom/aicode/view/WebViewWindowPanel; enum  // outer this

Methods:
  public void onLoadEnd(org.cef.browser.CefBrowser, org.cef.browser.CefFrame, int)
    // On HTTP 200:
    // 1. Inject JBCefJSQuery into browser as JS bridge
    // 2. If not loaded and not goTo: send USER_LOGIN via WebSocket
    // 3. Set isLoaded = true
    // 4. Init theme via ThemeChangeListener.initTheme()
    // 5. If agent refresh pending: push refresh, clear flag
    // 6. Call goto() for navigation
    // 7. Send plugin info via CommonService.getPluginInfo()
```

### 12.5 WebViewWindowPanel$K (ModuleEnum SwitchMap)

```
public synchronized synthetic class com.aicode.view.WebViewWindowPanel$K extends java.lang.Object

Fields:
  public static final synthetic [I enum  // $SwitchMap$ModuleEnum

Static init maps:
  CODE_SEARCH -> 1, UNIT_TEST -> 2, BATCH_UNIT_TEST -> 3,
  UNIT_TESTING -> 4, LOG -> 5, CHAT -> 6, LOGIN -> 7,
  COMMON -> 8, SETTING -> 9, SQL_CHAT -> 10,
  CODE_CHECK -> 11, GIT_VIEW -> 12
```

### 12.6 PluginToolWindowPanel

```
public synchronized class com.aicode.view.PluginToolWindowPanel extends com.intellij.openapi.ui.SimpleToolWindowPanel

Fields:
  private final Lcom/intellij/openapi/Disposable; byte    // disposable
  private final Lcom/intellij/openapi/project/Project; enum // project

Constructor(Project, Disposable):
  1. super(true) - vertical simple tool window panel
  2. Store project and disposable
  3. Create WebViewWindowPanel(project)
  4. setContent(webViewWindowPanel)
```

### 12.7 CustomResourceHandler

```
public synchronized class com.aicode.view.CustomResourceHandler extends java.lang.Object
implements org.cef.handler.CefResourceHandler

Fields:
  private Lcom/aicode/view/ResourceHandlerState; float  // current state
  private Lcom/intellij/openapi/project/Project; byte   // project
  private final Lcom/intellij/openapi/diagnostic/Logger; enum // logger

Methods:
  processRequest(CefRequest, CefCallback):
    1. Parse URL, remove scheme, split by separator
    2. Resolve resource via ClassLoader.getResource()
    3. Handle special file types (.js/.css/.html/.svg)
    4. Create OpenedConnection
    5. Set state, callback.Continue()

  readResponse(byte[], int, IntRef, CefCallback):
    Delegates to ResourceHandlerState.readResponse()

  getResponseHeaders(CefResponse, IntRef, StringRef):
    Delegates to ResourceHandlerState.getResponseHeaders()

  cancel():
    Close and null the ResourceHandlerState
```

### 12.8 CustomSchemeHandlerFactory

```
public synchronized class com.aicode.view.CustomSchemeHandlerFactory extends java.lang.Object
implements org.cef.callback.CefSchemeHandlerFactory

Fields:
  private Lcom/intellij/openapi/project/Project; enum  // project

Methods:
  create(CefBrowser, CefFrame, String, CefRequest):
    -> new CustomResourceHandler(project)
```

### 12.9 OpenedConnection

```
public synchronized class com.aicode.view.OpenedConnection extends java.lang.Object
implements com.aicode.view.ResourceHandlerState

Fields:
  private final Ljava/net/URLConnection; float  // connection
  private Z byte                                // inputStreamOpened flag
  private Ljava/io/InputStream; enum            // inputStream

Methods:
  getResponseHeaders():
    - Detect MIME type by URL extension (.js/.css/.html/.svg)
    - Set response status 200
    - On error: set status 404, ERR_FILE_NOT_FOUND

  readResponse():
    - Read from input stream into buffer
    - Return Boolean(true) if data available, Boolean(false) when done

  close():
    - Close input stream
```

### 12.10 ResourceHandlerState

```
public interface abstract class com.aicode.view.ResourceHandlerState

Methods:
  abstract Boolean readResponse(byte[], int, IntRef, CefCallback) throws IOException
  abstract void close() throws IOException
  abstract void getResponseHeaders(CefResponse, IntRef, StringRef)
```

### 12.11 StatusBarPopup

```
public synchronized class com.aicode.statusBar.StatusBarPopup extends com.intellij.openapi.wm.impl.status.EditorBasedStatusBarPopup

Fields:
  public static Ljava/lang/String; byte           // detail text
  private static final Ljava/lang/String; enum    // widget ID

Key Methods:
  ID() -> deobfuscated widget ID string
  getWidgetState(VirtualFile) -> WidgetState based on status
  createPopup(DataContext) -> action group popup
  createInstance(Project) -> new StatusBarPopup(project)
  update(Project) -> static update helper
  update(Project, String) -> update with detail text
```

### 12.12 StatusBarWidgetFactory

```
public synchronized class com.aicode.statusBar.StatusBarWidgetFactory extends com.intellij.openapi.wm.impl.status.widget.StatusBarEditorBasedWidgetFactory

Methods:
  getDisplayName() -> deobfuscated display name from BasicActionsBundle
  getId() -> deobfuscated widget ID
  createWidget(Project) -> new StatusBarPopup(project)
  disposeWidget(StatusBarWidget) -> no-op
```

### 12.13 AICodeStatusService

```
public synchronized class com.aicode.status.AICodeStatusService extends java.lang.Object
implements com.aicode.status.AICodeStatusListener, com.intellij.openapi.Disposable

Fields:
  private Lcom/aicode/enums/AICodeStatus; float  // current status
  private final Ljava/lang/Object; byte           // sync monitor
  private Ljava/lang/String; enum                 // detail text

Key Methods:
  static getCurrentStatus() -> Pair<AICodeStatus, String>
  static notifyApplication(AICodeStatus) / notifyApplication(AICodeStatus, String)
  onAICodeStatus(AICodeStatus, String) - updates state, triggers UI update
  dispose() - no-op

Constructor:
  1. Initialize status = Ready
  2. Create sync monitor
  3. Subscribe to own TOPIC via MessageBus
```

### 12.14 ProjectToolWindowFactory

```
public synchronized class com.aicode.toolwindow.ProjectToolWindowFactory extends java.lang.Object
implements com.intellij.openapi.wm.ToolWindowFactory, com.intellij.openapi.project.DumbAware

Fields:
  public static final Ljava/lang/String; UNIT_TEST_CONTENT_NAME
  private static final Lorg/slf4j/Logger; LOG

Constructor:
  1. InlineChatInlay.INSTANCE.register()
  2. OpenInlineChatAction.Companion.register()

createToolWindowContent(Project, ToolWindow):
  1. Show/hide based on ACTIVITY_STARTED flag
  2. Set title actions: [RefreshAction]
  3. Create PluginToolWindowPanel(project, toolWindow.disposable)
  4. Add as content to ContentManager
```

### 12.15 CheckGutterIconRenderer

```
public synchronized class com.aicode.toolwindow.CheckGutterIconRenderer extends com.intellij.openapi.editor.markup.GutterIconRenderer

Fields:
  private Lcom/intellij/openapi/editor/markup/RangeHighlighter; highlighter
  private Ljava/lang/String; type
  private I lineNumber
  private Lcom/intellij/openapi/editor/Editor; editor
  private Lcom/aicode/agent/dto/chat/PresentationDataDto; presentationDataDto
  private Ljava/util/List; commandEnums
  private [Lcom/intellij/openapi/actionSystem/AnAction; anActions

Key Methods:
  getIcon() -> toolWindow.svg (dark/light)
  getAlignment() -> LEFT
  getClickAction() -> jump to file/line
  getPopupMenuActions() -> action group from commandEnums
  handleActionPerformed(Project, CommandEnum) -> route to service
```

### 12.16 PluginEditorInlayHintsProvider

```
public synchronized class com.aicode.toolwindow.PluginEditorInlayHintsProvider extends java.lang.Object
implements com.intellij.codeInsight.hints.InlayHintsProvider

Fields:
  private static final Lcom/intellij/openapi/diagnostic/Logger; LOG
  private static final Lcom/intellij/codeInsight/hints/SettingsKey; KEY

Key Methods:
  getCollectorFor(PsiFile, Editor, PluginHintSettings, InlayHintsSink)
    -> new PluginEditorInlayHintsProvider$1(this, editor)

  Static methods:
  - handleAction(CommandEnum, Project, CodeInfoDto)
  - handleUnitTest(PsiElement, EditorImpl, Project)
  - handleCommand(PsiElement, Editor, CommandEnum)
  - addLineAction(...) / addGroupAction(...)
  - findRealOffsetBySpace(Editor, String) -> int
  - getAnchorOffset(PsiElement) -> int
```

### 12.17 PluginHintSettings

```
public synchronized class com.aicode.toolwindow.PluginHintSettings extends java.lang.Object
// Empty placeholder class for InlayHintsProvider settings
```

### 12.18 DiffDialog

```
public synchronized class com.aicode.diff.DiffDialog extends com.intellij.openapi.ui.DialogWrapper

Fields:
  private final Lcom/intellij/openapi/project/Project; byte    // project
  private final Lcom/intellij/diff/requests/SimpleDiffRequest; enum // diff request

Key Methods:
  createCenterPanel() -> DiffRequestPanel with BorderLayout
  createActions() -> [Reject, Accept]
  doOKAction() -> write left content to right file, track accept
```

### 12.19 ThemeChangeListener

```
public synchronized class com.aicode.listener.ThemeChangeListener extends java.lang.Object
implements com.intellij.openapi.components.ApplicationComponent

Fields:
  private I float                    // previous font size
  private Ljava/lang/String; byte    // previous LAF name
  private static final Lcom/intellij/openapi/diagnostic/Logger; enum

Key Methods:
  initComponent() -> subscribe to LAF and color scheme changes
  static initTheme() -> sync current theme to WebView
  static changeTheme(String, int) -> push theme to WebView via WebSocket
  static getTheme(String, ToolWindow) -> "dark" or "light" + update icons
```

---

## 13. Deobfuscation Notes

All string literals in the bytecode are obfuscated using a custom XOR-based
cipher. The deobfuscation is performed by static `H()` methods scattered
across the codebase (each class has its own `H()` method with different
XOR keys). Key deobfuscation methods used in this package:

| Class | H() Method | Used In |
|-------|-----------|---------|
| `RequestCancelException.H()` | WebViewWindowPanel browser creation |
| `IdeAction.H()` | CustomSchemeHandlerFactory, StatusBarWidgetFactory |
| `FontKt.H()` | CustomSchemeHandlerFactory scheme name |
| `FileService.H()` | WebViewWindowPanel$M JS bridge name |
| `GenericUtils.H()` | PluginToolWindowPanel error messages |
| `ChatInputController.H()` | PluginToolWindowPanel, ActionButton |
| `AICodeUtils.H()` | ActionButton place ID |
| `Maps.H()` | StatusBarPopup widget/action IDs |
| `AICodeLanguageInfo.H()` | StatusBarPopup widget/popup IDs |
| `BasicActionsBundle.message()` | All user-visible strings |
| `FileExtensionLanguageDetails.H()` | OpenedConnection MIME type detection |
| `CodeCompleteService.H()` | OpenedConnection MIME types, DiffDialog |
| `EditorUtils.H()` | CustomResourceHandler URL parsing |
| `GeneratorConfig.H()` | CustomResourceHandler URL parsing |
| `PropertyUtils.H()` | StatusBarWidgetFactory IDs |
| `AICodeStringUtil.H()` | DiffDialog title |
| `CancelRequestTip.H()` | ThemeChangeListener component name |
| `JComponentKt.H()` | Style assertion, ActionButton |
| `Application.H()` | Font, FontKt |
| `RequestResultList.H()` | Font, Style$Borders |
| `IndentLineUtil.H()` | SendStopActionButtonPanel card names |
| `OverlayUtils.H()` | Style$Borders |
| `AICodeStringUtil.H()` | DiffDialog |
| `LanguageFileExtensionDetails.H()` | CustomSchemeHandlerFactory, ThemeChangeListener |
| `FileInfo.H()` | CustomSchemeHandlerFactory |

---

## 14. Key Architectural Insights

1. **WebViewWindowPanel is the central UI hub**: All service communication flows
   through this single JPanel. 12+ services directly push data to it via
   `handleRequest()` which serializes to JSON and calls JavaScript.

2. **JCEF is the rendering engine**: The entire chat/AI interface is a web
   application rendered in Chromium via JCEF. Java communicates with JS through
   `JBCefJSQuery` (the bridge) and `executeJavaScript()` (push).

3. **Custom CEF scheme**: Resources are loaded via a custom scheme handler
   (`CustomSchemeHandlerFactory`/`CustomResourceHandler`) that resolves
   classpath resources and serves them with correct MIME types.

4. **StatusBar is status-driven**: The `AICodeStatusService` is an
   application-level singleton that broadcasts status changes via IntelliJ's
   `MessageBus`. The `StatusBarPopup` subscribes and updates its icon/text
   based on the current `AICodeStatus` enum value.

5. **Inline Chat is editor-native**: Unlike the main chat (WebView), inline
   chat uses Swing components (`JPanel`, `JBTextArea`) rendered as editor
   inlays. This provides a native IDE feel for inline code modifications.

6. **Gutter icons bridge to services**: `CheckGutterIconRenderer` provides
   context menus for code actions (test, explain, optimize, etc.) that route
   to `PluginEditorInlayHintsProvider.handleAction()` or
   `CppTestService.resolveCppTest()`.

7. **Theme sync is bidirectional**: `ThemeChangeListener` pushes IDE theme
   changes to the WebView via WebSocket, and on WebView load, `initTheme()`
   syncs the current theme. The WebView sends theme data as JSON with type
   `SETTING_CHANGE_THEME`.

8. **All strings are obfuscated**: Every string literal uses a per-class XOR
   cipher (`H()` method), making static analysis difficult without runtime
   deobfuscation.
