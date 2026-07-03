## 9. Reference: AICodeStatus Enum (from com.aicode.enums)

### 9.1 AICodeStatus

**File**: `com/aicode/enums/AICodeStatus.class` (source: "sm")  
**Type**: `public final enum`  
**Implements**: `PresentableEnum`

#### Enum Constants (7 values)

| Constant | Ordinal | Deobfuscated Text | Icon |
|----------|---------|-------------------|------|
| `Ready` | 0 | "Ready" (via FontKt.H) | `Icons.StatusBarIcon` |
| `NotSignedIn` | 1 | "Not Signed In" (via Maps.H) | `Icons.StatusBarIconNotSignedIn` |
| `CompletionInProgress` | 2 | "Completion In Progress" (via FontKt.H) | `Icons.StatusBarCompletionInProgress` |
| `AgentBroken` | 3 | "Agent Broken" (via Maps.H) | `Icons.StatusBarIconError` |
| `IncompatibleClient` | 4 | "Incompatible Client" (via FontKt.H) | `Icons.StatusBarIconError` |
| `Unsupported` | 5 | "Unsupported" (via Maps.H) | `Icons.StatusBarIconError` |
| `UnknownError` | 6 | "Unknown Error" (via FontKt.H) | `Icons.StatusBarIconError` |

#### Key Methods

| Method | Return | Description |
|--------|--------|-------------|
| `getIcon()` | `Icon` | Returns status-specific icon (see table above) |
| `getPresentableText()` | `String` | Returns human-readable status text |
| `isIconAlwaysShown()` | `boolean` | `true` for AgentBroken, IncompatibleClient, Unsupported, UnknownError; `false` for Ready, NotSignedIn, CompletionInProgress |
| `isDisablingClientRequests()` | `boolean` | `true` only for IncompatibleClient and AgentBroken |

#### getIcon() Switch Logic

Uses `$enum` switch table (AICodeStatus$p inner class):
- Case 1 (NotSignedIn): `Icons.StatusBarIconNotSignedIn`
- Case 2 (Ready): `Icons.StatusBarIcon`
- Case 3 (CompletionInProgress): `Icons.StatusBarCompletionInProgress`
- Default (4-7): `Icons.StatusBarIconError`

#### getPresentableText() Switch Logic

- Ready: `MessageBundle.get(deobfuscatedKey) + " iFlyCode"` (e.g., "Ready iFlyCode")
- NotSignedIn: empty string
- CompletionInProgress: `MessageBundle.get(deobfuscatedKey)` (e.g., "Thinking...")
- AgentBroken: deobfuscated via FontKt.H (e.g., "Agent Broken")
- IncompatibleClient: deobfuscated via Maps.H (e.g., "Incompatible Client")
- Unsupported: deobfuscated via FontKt.H (e.g., "Unsupported")
- UnknownError: deobfuscated via Maps.H (e.g., "Unknown Error")

### 9.2 AICodeStatus$p (Switch Table)

Inner class that pre-computes an int[] mapping from enum ordinals to switch case numbers:

| Enum | Ordinal | Switch Case |
|------|---------|-------------|
| NotSignedIn | 1 | 1 |
| Ready | 0 | 2 |
| CompletionInProgress | 2 | 3 |
| AgentBroken | 3 | 4 |
| IncompatibleClient | 4 | 5 |
| Unsupported | 5 | 6 |
| UnknownError | 6 | 7 |

---

## 10. Cross-Package Analysis

### 10.1 Exception System Architecture

```
RuntimeException
  +-- RequestCancelException    (user cancellation)
  +-- RequestTimeoutException   (request timeout)

Both contain H() deobfuscation method (XOR-based string decryption)
Both are unchecked - no mandatory catch blocks
Used in code completion request lifecycle
```

### 10.2 Status Management Flow

```
AICodeStatusService (application-level singleton)
    |
    +-- Holds: AICodeStatus + String message (thread-safe via mutex)
    +-- Initial state: Ready
    |
    +-- notifyApplication(status, message)
    |       |
    |       +-- Application.getMessageBus().syncPublisher(TOPIC)
    |       +-- AICodeStatusListener.onAICodeStatus(status, message)
    |
    +-- onAICodeStatus() [implements listener]
    |       |
    |       +-- Skip if current.isDisablingClientRequests()
    |       +-- Skip if same status + same message
    |       +-- Update state + trigger UI refresh
    |
    +-- bB() -> bC()
            |
            +-- For each open project:
                StatusBarPopup.update(project, message)

AICodeStatus Enum:
    Ready -> NotSignedIn -> CompletionInProgress -> AgentBroken -> IncompatibleClient -> Unsupported -> UnknownError
    
    Disabling states: AgentBroken, IncompatibleClient
    (These block new client requests)
```

### 10.3 Inlay Completion Hint System

```
InlayListener (message bus topic)
    |
    +-- inlaysUpdated(request, action, editor, inlays)
    |
    +-- InlayGotItListener (implementation)
        |
        +-- Shows GotItTooltip on first inlay appearance
        +-- Uses Icons.ToolWindowIcon
        +-- Position: Balloon.Position.atLeft

InlayCompletionHintFactory
    |
    +-- showHintAtCaret(editor)
    |       Creates LightweightHint at caret position
    |       Flags: HINT_BY_MOUSE CaretPosition, timeout=0
    |
    +-- showHintAtPosition(editor, point)
    |       Creates LightweightHint at specific point
    |       Flags: 42, timeout=0, byMouse=false
    |
    +-- Df() -> Creates hint JComponent
    |       SimpleColoredComponent + InlineKeybindingHintComponent
    |       Text: "Press &#123;keybinding&#125; to accept iFlyCode suggestion"
    |
    +-- ve() -> Generates hint text
        Deobfuscates keybinding, separator, format template
        Returns formatted string

DebuggerFilter (error.search)
    |
    +-- applyFilter() -> DebuggerFilter$V
    |       Adds clickable debug icon next to exceptions
    |
    +-- DebuggerFilter$V.createInlayRenderer()
    |       Checks: API key present + enableCodeDebug setting
    |       Returns: Presentation or null
    |
    +-- Presentation
        +-- paint(): Renders debug icon (light/dark theme aware)
        +-- mouseClicked(): Triggers ChatService.handleCodeDebug()
        +-- mouseMoved/mouseExited: Hand cursor management
```

### 10.4 Icon Constants Complete Registry

| Icon | Resource | Usage |
|------|----------|-------|
| `PluginIconLogo` | `/icons/indexIcon.svg` | Plugin logo in marketplace |
| `LOGO` | `/icons/toolWindow.svg` | Tool window tab icon (light) |
| `ToolWindowIcon` | `/icons/toolWindow[_dark].svg` | Tool window icon (theme-aware) |
| `PluginIcon` | `/icons/toolWindow_dark.svg` or `/icons/disabled_dark.svg` | Plugin icon (theme-aware) |
| `ReplaceAll` | `/svg/replaceAll_dark.svg` | Replace all action button |
| `DebugIcon` | `/icons/debug.svg` | Debug inlay icon (light) |
| `DebugDarkIcon` | `/icons/debug_dark.svg` | Debug inlay icon (dark) |
| `StatusBarIcon` | `/icons/logo_16[_dark].svg` | Status bar normal state |
| `StatusBarIconDisabled` | `/icons/disabled[_dark].svg` | Status bar disabled state |
| `StatusBarIconNotSignedIn` | `/icons/not_sign_in.svg` | Status bar not signed in |
| `StatusBarIconError` | Same as Disabled | Status bar error state |
| `StatusBarCompletionInProgress` | `AnimatedIcon.Default` | Status bar loading spinner |
| `I_FLY_CODE` | `/icons/logo_16.svg` | iFlyCode brand icon (final) |
| `AirPlane` | `/icons/air_plane.svg` | Airplane decoration |
| `STOP` | `/icons/stop.svg` | Stop/cancel button |

### 10.5 Request Lifecycle

```
1. CodeGenerateEditorRequest.create(editor, offset, tipType)
   |
   +-- RequestId.incrementAndGet() -> unique ID
   +-- Captures: project, document, psiFile, language, uri, content, offset
   +-- Reads: tab settings, selection state, file extension
   +-- Returns: new CodeGenerateEditorRequest (or null on error)

2. Request execution
   |
   +-- If cancelled: RequestCancelException
   +-- If timeout: RequestTimeoutException
   +-- On success: AgentCodeTip wraps GetTipsResult$Tip

3. AgentCodeTip lifecycle
   |
   +-- Created from GetTipsResult$Tip (splits display text into lines)
   +-- withCompletion() -> immutable copy with new lines
   +-- asCached() -> cached copy (for reuse)
   +-- FromString() -> factory from raw text
```

### 10.6 Message Bus Topics

| Topic | Interface | Package | Description |
|-------|-----------|---------|-------------|
| `AICodeStatusListener.TOPIC` | `AICodeStatusListener` | `status` | AI Code status changes |
| `UserLoginListener.USER_LOGIN` | `UserLoginListener` | `status` | User login state changes |
| `InlayListener.TOPIC` | `InlayListener` | `complete` | Inlay hint updates |

### 10.7 Obfuscation Patterns

All classes in these packages use the standard H-string deobfuscation pattern:
- **Primary method**: `SomeClass.H(Object)` - XOR-based character decryption
- **Validation method**: `enum(int)` - Null-check assertions with obfuscated error messages
- **Deobfuscation delegates**: Various classes serve as H-method hosts:
  - `RequestCancelException.H()` / `RequestTimeoutException.H()`
  - `GitReviewService.H()`
  - `PositionUtil.H()`
  - `PropertyUtils.H()`
  - `Maps.H()`
  - `IndentLineUtil.H()`
  - `HandleCacheUtil.H()`
  - `FileExtensionLanguageDetails.H()` / `LanguageFileExtensionDetails.H()`
  - `AICodeUtils.H()` / `FileInfo.H()`
  - `FontKt.H()`
  - `JComponentKt.H()`
  - `IdeAction.H()`
  - `CancelRequestTip.H()`
  - `CodeCompleteService.H()`

---

## 11. Summary Statistics

| Metric | Value |
|--------|-------|
| Total packages analyzed | 9 |
| Total classes decompiled | 22 |
| Total enum constants | 7 (AICodeStatus) |
| Total icon constants | 15 |
| Total message bus topics | 3 |
| Total exception types | 2 |
| Total DTO fields | 12 (5 + 7) |
| Total request fields | 18 |
| Total obfuscated field names | ~30 |
| SVG icon resources | 12 unique paths |
