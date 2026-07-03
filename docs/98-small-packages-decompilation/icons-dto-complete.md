## 4. Package: com.aicode.status (3 classes)

### 4.1 AICodeStatusListener

**File**: `com/aicode/status/AICodeStatusListener.class` (source: "j")  
**Type**: `public interface`

IntelliJ message bus listener for AI Code status changes.

#### Fields

| Field | Type | Description |
|-------|------|-------------|
| `TOPIC` | `Topic&lt;AICodeStatusListener&gt;` | static final - message bus topic |

The TOPIC is created with a deobfuscated name string via `RequestCancelException.H()`.

#### Method

```java
void onAICodeStatus(AICodeStatus status, String message)
```

Called when the AI Code status changes. Implementations update UI accordingly.

### 4.2 AICodeStatusService

**File**: `com/aicode/status/AICodeStatusService.class` (source: "wc")  
**Type**: `public class`  
**Implements**: `AICodeStatusListener, Disposable`

Application-level service that manages the current AI Code status and broadcasts changes.

#### Fields (obfuscated -> deobfuscated)

| Obfuscated | Type | Deobfuscated |
|------------|------|--------------|
| `float` | `AICodeStatus` | currentStatus |
| `byte` | `Object` | lock (mutex) |
| `enum` | `String` | statusMessage |

#### Constructor

1. Creates mutex object
2. Sets `currentStatus = AICodeStatus.Ready` (initial state)
3. Connects to `Application.getMessageBus()` and subscribes to `TOPIC` with `this`

#### Key Methods

| Method | Signature | Description |
|--------|-----------|-------------|
| `getCurrentStatus()` | `static Pair<AICodeStatus, String>` | Gets service instance, returns status+message |
| `notifyApplication(AICodeStatus)` | `static void` | Notifies with null message |
| `notifyApplication(AICodeStatus, String)` | `static void` | Broadcasts status change via message bus |
| `onAICodeStatus(AICodeStatus, String)` | `void` | Updates internal state, triggers UI refresh |
| `dispose()` | `void` | No-op |

#### onAICodeStatus() Logic (Thread-safe)

1. Synchronized on `lock`:
   - If current status `isDisablingClientRequests()`, skip update
   - If status unchanged AND message equals current, skip update
   - Otherwise: update `currentStatus` and `statusMessage`, set `changed = true`
2. If `changed`: call `bB()` to update UI

#### notifyApplication() Logic

1. Gets `ApplicationManager.getApplication()`
2. If application is disposed, return
3. Gets `messageBus.syncPublisher(TOPIC)`
4. Calls `listener.onAICodeStatus(status, message)`

#### bB() - UI Update Trigger

1. Creates a Runnable that calls `bC()`
2. If already on dispatch thread: runs immediately
3. Otherwise: `Application.invokeLater(runnable)`

#### bC() - Status Bar Update

Iterates over all open projects and calls `StatusBarPopup.update(project, statusMessage)` for each non-disposed project.

### 4.3 UserLoginListener

**File**: `com/aicode/status/UserLoginListener.class` (source: "i")  
**Type**: `public interface`

IntelliJ message bus listener for user login events.

#### Fields

| Field | Type | Description |
|-------|------|-------------|
| `USER_LOGIN` | `Topic&lt;UserLoginListener&gt;` | static final - message bus topic |

The topic name is created by deobfuscating a string via `FileExtensionLanguageDetails.H()`, then appending `BasicActionsBundle.message(deobfuscatedKey)`.

#### Method

```java
void login(Project project, boolean isLoggedIn)
```

Called when user login state changes.

---

## 5. Package: com.aicode.icons (1 class)

### 5.1 Icons

**File**: `com/aicode/icons/Icons.class` (source: "Icons.java")  
**Type**: `public class`

Central icon registry for the entire plugin. All icons are loaded via `IconLoader.getIcon()` from SVG resources.

#### Static Icon Constants

| Field | Type | Resource Path | Notes |
|-------|------|---------------|-------|
| `PluginIconLogo` | `Icon` | `/icons/indexIcon.svg` | Plugin logo |
| `LOGO` | `Icon` | `/icons/toolWindow.svg` | Tool window logo (light) |
| `ToolWindowIcon` | `Icon` | `/icons/toolWindow_dark.svg` or `/icons/toolWindow.svg` | Theme-aware |
| `PluginIcon` | `Icon` | `/icons/toolWindow_dark.svg` or `/icons/disabled_dark.svg` | Theme-aware (different logic) |
| `ReplaceAll` | `Icon` | `/svg/replaceAll_dark.svg` | Replace all action |
| `DebugIcon` | `Icon` | `/icons/debug.svg` | Debug icon (light) |
| `DebugDarkIcon` | `Icon` | `/icons/debug_dark.svg` | Debug icon (dark) |
| `StatusBarIcon` | `Icon` | `/icons/logo_16_dark.svg` or `/icons/logo_16.svg` | Status bar (theme-aware) |
| `StatusBarIconDisabled` | `Icon` | `/icons/disabled_dark.svg` or `/icons/disabled.svg` | Disabled state (theme-aware) |
| `StatusBarIconNotSignedIn` | `Icon` | `/icons/not_sign_in.svg` | Not signed in state |
| `StatusBarIconError` | `Icon` | Same as `StatusBarIconDisabled` | Error state |
| `StatusBarCompletionInProgress` | `Icon` | `AnimatedIcon.Default` | Animated spinner |
| `I_FLY_CODE` | `Icon` | `/icons/logo_16.svg` | **final** - iFlyCode logo |
| `AirPlane` | `Icon` | `/icons/air_plane.svg` | Airplane icon |
| `STOP` | `Icon` | `/icons/stop.svg` | Stop/cancel icon |

#### Theme-Aware Initialization Logic

Several icons use `isUnderDarcula()` to select dark/light variants:

- **ToolWindowIcon**: dark=`/icons/toolWindow_dark.svg`, light=`/icons/toolWindow.svg`
- **PluginIcon**: dark=`/icons/toolWindow_dark.svg`, light=`/icons/disabled_dark.svg` (note: light uses "disabled_dark" which may be a bug)
- **StatusBarIcon**: dark=`/icons/logo_16_dark.svg`, light=`/icons/logo_16.svg`
- **StatusBarIconDisabled**: dark=`/icons/disabled_dark.svg`, light=`/icons/disabled.svg`

#### Helper Methods

| Method | Signature | Description |
|--------|-----------|-------------|
| `getIcon(String)` | `static Icon` | Loads icon via `IconLoader.getIcon(path, Icons.class)` |
| `isUnderDarcula()` | `static boolean` | Reflectively calls `StartupUiUtil.isUnderDarcula()` |
| `getCurrentIcon()` | `static Icon` | Returns theme-aware tool window icon |

#### isUnderDarcula() Implementation

Uses reflection to call `com.intellij.util.ui.StartupUiUtil.isUnderDarcula()`:
1. `Class.forName("com.intellij.util.ui.StartupUiUtil")`
2. `getDeclaredMethod("isUnderDarcula")`
3. `invoke()` and cast to Boolean
4. Returns `false` on any Exception

---

## 6. Package: com.aicode.message (1 class)

### 6.1 BasicActionsBundle

**File**: `com/aicode/message/BasicActionsBundle.class` (source: "fc")  
**Type**: `public class`  
**Extends**: `com.intellij.DynamicBundle`

I18N message bundle for basic action labels and UI strings.

#### Fields (obfuscated -> deobfuscated)

| Obfuscated | Type | Deobfuscated |
|------------|------|--------------|
| `byte` | `String` | static final - bundle path (deobfuscated) |
| `enum` | `BasicActionsBundle` | static final - singleton instance |

#### Static Initialization

1. Deobfuscates bundle path via `AICodeUtils.H()` and stores in `byte` (bundlePath)
2. Creates singleton instance: `new BasicActionsBundle()`

#### Constructor

Calls `super(deobfuscatedBundlePath)` where the bundle path is deobfuscated via `FileInfo.H()`.

#### Key Methods

| Method | Signature | Description |
|--------|-----------|-------------|
| `message(String, Object...)` | `static String` | Resolves i18n message (non-null asserted) |
| `messagePointer(String, Object...)` | `static Supplier&lt;String&gt;` | Returns lazy message supplier |

Both methods delegate to the singleton instance's `getMessage()` and `getLazyMessage()` respectively, with null-check assertions via the `enum()` validation method.

---

## 7. Package: com.aicode.dto (2 classes)

### 7.1 FileIndexDto

**File**: `com/aicode/dto/FileIndexDto.class` (source: "FileIndexDto.java")  
**Type**: `public class`

Simple POJO for file index information, used in code search/indexing.

#### Fields

| Field | Type | Getter | Setter |
|-------|------|--------|--------|
| `title` | `String` | `getTitle()` | `setTitle()` |
| `filePath` | `String` | `getFilePath()` | `setFilePath()` |
| `fileName` | `String` | `getFileName()` | `setFileName()` |
| `selectStartLine` | `int` | `getSelectStartLine()` | `setSelectStartLine()` |
| `selectEndLine` | `int` | `getSelectEndLine()` | `setSelectEndLine()` |

#### Computed Method

```java
String getFileIndexName()
```

Returns `fileName + selectStartLine + selectEndLine` via `makeConcatWithConstants`. This creates a composite key like "MyFile.java1030" for indexing purposes.

### 7.2 GitResponseDTO

**File**: `com/aicode/dto/GitResponseDTO.class` (source: "GitResponseDTO.java")  
**Type**: `public class`

Lombok-style DTO for git operation responses. Includes full `equals()`, `hashCode()`, `toString()`, and `canEqual()` methods (Lombok @Data annotation pattern).

#### Fields

| Field | Type | Description |
|-------|------|-------------|
| `status` | `Integer` | Operation status code |
| `repoUrl` | `String` | Repository URL |
| `repoId` | `String` | Repository identifier |
| `branch` | `String` | Git branch name |
| `command` | `String` | Git command executed |
| `repoName` | `String` | Repository name |
| `code` | `String` | Response code/data |

All fields have standard getters and setters.

#### equals() Implementation

Standard Lombok-generated equals: compares all 7 fields with null-safe checks. Uses `canEqual()` for subclass safety.

#### hashCode() Implementation

Standard Lombok-generated hashCode: uses prime 59, iterates through all fields with `result = result * 59 + (field == null ? 43 : field.hashCode())`.

#### toString() Implementation

Returns `GitResponseDTO(status=X, repoUrl=X, repoId=X, branch=X, command=X, repoName=X, code=X)` via `makeConcatWithConstants`.

---

## 8. Package: com.aicode.complete (4 classes)

### 8.1 InlayCompletionHintFactory

**File**: `com/aicode/complete/InlayCompletionHintFactory.class` (source: "ie")  
**Type**: `public class`

Factory for creating and showing inlay completion hints (the "Press Tab to accept" tooltip that appears after AI suggestions).

#### Fields

| Field | Type | Description |
|-------|------|-------------|
| `enum` | `Logger` | private static final - logger |

#### Key Methods

| Method | Signature | Description |
|--------|-----------|-------------|
| `showHintAtPosition(Editor, Point)` | `static void` | Shows hint at specific screen position |
| `showHintAtCaret(Editor)` | `static void` | Shows hint at current caret position |
| `showEditorHint(...)` | `static void` | Low-level hint display method |

#### showHintAtPosition(Editor, Point) Logic

1. Validates editor and point are non-null
2. Logs the point position
3. Creates `LightweightHint` from `Df()` component
4. Calls `HintManagerImpl.showEditorHint(hint, editor, point, flags=42, timeout=0, byMouse=false)`
5. On Throwable: logs warning with deobfuscated message

#### showHintAtCaret(Editor) Logic

1. Validates editor is non-null
2. Logs deobfuscated "showing hint at caret" message
3. Creates `LightweightHint` from `Df()` component
4. Calls `showEditorHint(hint, editor, flags=1, hintFlags=42, byMouse=false, timeout=0)`
5. On Throwable: logs warning

#### Df() - Create Hint Component

Private method that builds the hint UI:
1. Creates `HintUtil.createInformationComponent()` (SimpleColoredComponent)
2. Sets `setIconOnTheRight(true)`
3. Creates `SimpleColoredText` with the hint text from `ve()` and `REGULAR_ATTRIBUTES`
4. Appends text to the component
5. Wraps in `InlineKeybindingHintComponent` (inner class JPanel)

#### ve() - Generate Hint Text

Private method that generates the display text:
1. Deobfuscates three strings via `PropertyUtils.H()` and `GitReviewService.H()`
2. The strings represent: keybinding text, separator, and hint format
3. Formats via `String.format(template, keybinding, separator, hintText)`
4. Result is something like: "Press &#123;keybinding&#125; to accept iFlyCode suggestion"

#### showEditorHint() - Low-Level Display

```java
static void showEditorHint(LightweightHint hint, Editor editor, short flags, int hintFlags, int timeout, boolean byMouse)
```

1. Gets `editor.getCaretModel().getLogicalPosition()`
2. Calculates hint position via `HintManagerImpl.getHintPosition(hint, editor, logicalPosition, flags)`
3. Creates `HintHint` via `HintManagerImpl.createHintHint(editor, point, hint, flags)`
4. Shows via `HintManagerImpl.showEditorHint(hint, editor, point, hintFlags, timeout, byMouse, hintHint)`

### 8.2 InlayCompletionHintFactory$InlineKeybindingHintComponent

**File**: `com/aicode/complete/InlayCompletionHintFactory$InlineKeybindingHintComponent.class`  
**Type**: `public class`  
**Extends**: `javax.swing.JPanel`

Custom JPanel that wraps the hint text component with proper layout and styling.

#### Constructor(SimpleColoredComponent)

1. Validates component is non-null
2. Sets `BorderLayout` as layout manager
3. Adds the SimpleColoredComponent at `BorderLayout.CENTER` (position deobfuscated via `Maps.H()`)
4. Sets `setOpaque(true)`
5. Copies background color from the SimpleColoredComponent
6. Calls `revalidate()` and `repaint()`

### 8.3 InlayGotItListener

**File**: `com/aicode/complete/InlayGotItListener.class` (source: "og")  
**Type**: `public class`  
**Implements**: `InlayListener`

Shows a "Got It" tooltip when inlay completions are first displayed, introducing the feature to new users.

#### inlaysUpdated() Logic

1. Validates all 4 parameters are non-null
2. If inlay list is empty or request is cancelled, return
3. Gets header text from `BasicActionsBundle.message(deobfuscatedKey)`
4. Gets body text from deobfuscated strings
5. Creates `GotItTooltip` with:
   - ID from `BasicActionsBundle.message()`
   - Body text (deobfuscated)
   - Disposable from `request.getDisposable()`
6. Configures tooltip:
   - `.withHeader(headerText)` - adds header
   - `.withPosition(Balloon.Position.atLeft)` - positions at left
   - `.withIcon(Icons.ToolWindowIcon)` - uses tool window icon
   - `.andShowCloseShortcut()` - adds close shortcut
7. Gets bounds of first inlay: `inlays.get(0).getBounds()`
8. If bounds non-null and `tooltip.canShow()`:
   - Calls `wf(contentComponent, tooltip, bounds.getLocation())`
9. On Exception: logs error

#### wf() - Show Tooltip

Private method that validates all 3 parameters are non-null (via `enum()` assertions). The actual showing is handled by the GotItTooltip framework.

### 8.4 InlayListener

**File**: `com/aicode/complete/InlayListener.class` (source: "sa")  
**Type**: `public interface`

IntelliJ message bus listener for inlay completion events.

#### Fields

| Field | Type | Description |
|-------|------|-------------|
| `TOPIC` | `Topic&lt;InlayListener&gt;` | static final - message bus topic |

Topic name is deobfuscated via `FileExtensionLanguageDetails.H()`.

#### Method

```java
void inlaysUpdated(EditorRequestService request, OperateActionEnum action, Editor editor, List<Inlay&lt;TipRenderer&gt;> inlays)
```

Called when inlay hints are updated in the editor.

---
