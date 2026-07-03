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
