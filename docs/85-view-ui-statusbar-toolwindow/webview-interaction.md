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
ProjectToolWindowFactory.&lt;init&gt;()
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
