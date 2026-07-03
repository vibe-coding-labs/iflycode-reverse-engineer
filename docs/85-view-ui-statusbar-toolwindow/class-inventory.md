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
