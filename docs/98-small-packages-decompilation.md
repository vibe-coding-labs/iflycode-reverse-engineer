# Small Packages Complete Decompilation Analysis

## Overview

This document provides complete decompilation and analysis of 9 small packages in the iFlyCode plugin, covering 22 classes total (including inner classes). These packages form the foundational infrastructure: request construction, exception handling, error/debug filtering, status management, icons, i18n, DTOs, and inlay completion hints.

### Package Inventory

| Package | Classes | Purpose |
|---------|---------|---------|
| `com.aicode.request` | 3 | Code completion request construction |
| `com.aicode.exception` | 2 | Request lifecycle exceptions |
| `com.aicode.error.search` | 3 | Debug filter for JVM exceptions |
| `com.aicode.status` | 3 | AI Code status management |
| `com.aicode.icons` | 1 | Icon constants registry |
| `com.aicode.message` | 1 | I18N message bundle |
| `com.aicode.dto` | 2 | Data transfer objects |
| `com.aicode.complete` | 4 | Inlay completion hint system |
| `com.aicode.enums` (ref) | 2 | AICodeStatus enum + switch table |

**Note**: The `com.aicode.search` package does not exist. The error/debug classes are in `com.aicode.error.search`. The message bundle is `BasicActionsBundle`, not `MessageBundle`. The DTO classes are `FileIndexDto` and `GitResponseDTO`, not `BizResponse`.

---

## 1. Package: com.aicode.request (3 classes)

### 1.1 AgentCodeTip

**File**: `com/aicode/request/AgentCodeTip.class` (source: "lc")  
**Type**: `public final class`  
**Implements**: `com.aicode.service.CodeTip`

This class wraps a `GetTipsResult$Tip` domain object into the `CodeTip` interface, providing line-split tip text and mutable metadata fields (requestId, scene, language).

#### Fields (obfuscated -> deobfuscated)

| Obfuscated Name | Type | Access | Deobfuscated Name |
|-----------------|------|--------|-------------------|
| `final` | `String` | private | language |
| `try` | `List<String>` | private final | tipLines |
| `float` | `String` | private | requestId |
| `byte` | `String` | private | scene |
| `enum` | `GetTipsResult$Tip` | private final | agentData |

#### Constructors

1. **`AgentCodeTip(GetTipsResult$Tip tip)`** - Primary constructor:
   - Stores the tip domain object
   - Splits `tip.getDisplayText()` into lines via `AICodeStringUtil.splitLines()`
   - Converts to immutable `List.of()`

2. **`AgentCodeTip(GetTipsResult$Tip tip, List<String> completion, boolean cached)`** - Copy constructor:
   - Stores tip and completion lines directly
   - The `cached` flag is consumed at construction but not stored as a field (used for validation only)

#### Key Methods

| Method | Signature | Description |
|--------|-----------|-------------|
| `getAgentData()` | `GetTipsResult$Tip` | Returns the underlying domain tip |
| `getTip()` | `List<String>` | Returns tip text lines (non-null asserted) |
| `getScene()` / `setScene()` | `String` | Scene context (e.g., "inline", "chat") |
| `getRequestId()` / `setRequestId()` | `String` | Request identifier |
| `getLanguage()` / `setLanguage()` | `String` | Programming language |
| `withCompletion(List<String>)` | `AgentCodeTip` | Immutable copy with new completion lines |
| `asCached()` | `CodeTip` | Creates a cached copy (sets cached=true) |
| `isCached()` | `boolean` | Always returns `false` in this implementation |
| `FromString(String)` | `AgentCodeTip` | Static factory: creates tip from raw string |

#### FromString Factory Method

Creates an `AgentCodeTip` from a plain string:
1. Deobfuscates constant "G7R/" via `PositionUtil.H()` to get a file path prefix
2. Creates `Position.of(0,0)` for both start and end positions
3. Creates `Range.of(startPos, endPos)` as empty range
4. Constructs `GetTipsResult$Tip` with the deobfuscated path, empty range, input text, and `Position.of(0,0)`
5. Wraps in `AgentCodeTip`

#### Private Ub() Method (asCached helper)

Creates a new `AgentCodeTip` with the same `enum` (agentData) and `try` (tipLines), then copies over `requestId`, `scene`, and `language` from the current instance.

---

### 1.2 CodeGenerateEditorRequest

**File**: `com/aicode/request/CodeGenerateEditorRequest.class` (source: "kc")  
**Type**: `public class`  
**Implements**: `com.aicode.service.EditorRequestService, com.intellij.openapi.Disposable`

This is the primary request object for code generation/completion. It captures the full editor context needed to make a completion request to the AI backend.

#### Fields (obfuscated -> deobfuscated)

| Obfuscated | Type | Access | Deobfuscated |
|------------|------|--------|--------------|
| `break` | `TipType` | private final | completionType |
| `class` | `boolean` | private | selected |
| `true` | `int` | private | offset |
| `this` | `String` | private final | documentContent |
| `else` | `long` | private final | requestTimestamp |
| `char` | `Logger` | private static final | LOG |
| `int` | `boolean` | private final | useTabIndents |
| `new` | `String` | private final | fileNameSuffix |
| `long` | `int` | private final | requestId |
| `super` | `boolean` | private volatile | cancelled |
| `for` | `Project` | private final | project |
| `if` | `String` | private final | fileName |
| `case` | `LineInfo` | private final | lineInfo |
| `final` | `AICodeLanguageInfo` | private final | fileLanguage |
| `try` | `VirtualFileUri` | private final | uri |
| `float` | `SessionController` | private | sessionController |
| `byte` | `int` | private final | tabWidth |
| `enum` | `long` | private final | documentModificationSequence |

#### Constructor

```java
CodeGenerateEditorRequest(
    Project project,           // IntelliJ project
    TipType completionType,    // LINE or MULTILINE
    boolean useTabIndents,     // Tab vs space indentation
    int tabWidth,              // Tab width in spaces
    int requestId,             // Unique request ID
    AICodeLanguageInfo lang,   // Language mapping info
    VirtualFileUri uri,        // File URI
    String documentContent,    // Full document text
    int offset,                // Cursor offset
    LineInfo lineInfo,         // Current line info
    long modificationSeq,      // Document modification stamp
    String fileName,           // File name
    String fileNameSuffix,     // File extension
    boolean selected           // Whether text is selected
)
```

Timestamp is set to `System.currentTimeMillis()` at construction time.

#### Key Methods

| Method | Return | Description |
|--------|--------|-------------|
| `getCompletionType()` | `TipType` | LINE or MULTILINE |
| `getOffset()` / `setOffset()` | `int` | Cursor position in document |
| `getDocumentContent()` | `String` | Full document text |
| `getUri()` | `VirtualFileUri` | File URI (non-null asserted) |
| `getLineInfo()` | `LineInfo` | Current line context |
| `getFileLanguage()` | `AICodeLanguageInfo` | Language mapping |
| `isUseTabIndents()` | `boolean` | Tab indentation flag |
| `getTabWidth()` | `int` | Tab width |
| `getRequestId()` | `int` | Unique request ID |
| `getRequestTimestamp()` | `long` | Creation timestamp |
| `getDocumentModificationSequence()` | `long` | Document version stamp |
| `isCancelled()` / `setCancelled()` | `boolean` | Cancellation flag |
| `isSelected()` / `setSelected()` | `boolean` | Text selection flag |
| `getFileName()` | `String` | File name |
| `getFileNameSuffix()` | `String` | File extension |
| `getProject()` | `Project` | IntelliJ project |
| `getSessionController()` / `setSessionController()` | `SessionController` | Inline session controller |
| `cancel()` | `void` | Sets cancelled=true and disposes |
| `dispose()` | `void` | Logs debug, sets cancelled=true |
| `equalsRequest(EditorRequestService)` | `boolean` | Compares by requestId |
| `getDisposable()` | `Disposable` | Returns `this` |

#### Static Factory: create(Editor, int, TipType)

The main entry point for creating requests:
1. Gets `Project` from editor; returns null if null
2. Gets `Document` and `PsiFile` from editor; returns null if null
3. Reads `isUseTabCharacter(project)` and `getTabSize(project)` from editor settings
4. Creates `LineInfo.create(document, offset)` for cursor context
5. Creates `VirtualFileUri.from(psiFile.getVirtualFile())`
6. Calls `getFileExtension(project)` to extract file name and extension
7. Checks `EditorCacheUtil.getEditCache(editor)` for cached selection state
8. Creates new `CodeGenerateEditorRequest` with `RequestId.incrementAndGet()` and `LanguageInfoManager.findLanguageMapping(psiFile)`
9. Returns null on any exception

#### Static: getFileExtension(Project)

Extracts file name and extension from the currently selected editor:
1. Gets `FileEditorManager.getSelectedEditor()`
2. Gets `VirtualFile.getName()` from selected file
3. Splits at last "." to separate name and extension
4. Returns `Map<"fileName" -> name, "fileExtension" -> extension>`
5. Returns `Collections.emptyMap()` if no editor or file

---

### 1.3 RequestId

**File**: `com/aicode/request/RequestId.class` (source: "cc")  
**Type**: `public final class`

Thread-safe atomic request ID generator.

#### Fields

| Field | Type | Description |
|-------|------|-------------|
| `enum` | `AtomicInteger` | private static final - atomic counter |

#### Methods

| Method | Return | Description |
|--------|--------|-------------|
| `currentRequestId()` | `int` | static - returns current value |
| `incrementAndGet()` | `int` | static - atomically increments and returns |

Initialized to 0 (default AtomicInteger). Used by `CodeGenerateEditorRequest.create()` to assign unique IDs.

---

## 2. Package: com.aicode.exception (2 classes)

### 2.1 RequestCancelException

**File**: `com/aicode/exception/RequestCancelException.class` (source: "vf")  
**Type**: `public class`  
**Extends**: `RuntimeException`

#### Constructor

```java
RequestCancelException(String message)
```

Simple RuntimeException wrapper for cancelled requests.

#### Static H(Object) Method

This class contains the standard H-string deobfuscation method (same pattern as other classes in the codebase). It uses `LinkageError.getStackTrace()[1]` to get caller class+method name, then performs XOR-based character decryption on the input string. This is the same obfuscation pattern found throughout the codebase.

### 2.2 RequestTimeoutException

**File**: `com/aicode/exception/RequestTimeoutException.class` (source: "rd")  
**Type**: `public class`  
**Extends**: `RuntimeException`

#### Constructor

```java
RequestTimeoutException(String message)
```

Simple RuntimeException wrapper for timed-out requests.

#### Static H(Object) Method

Identical deobfuscation pattern to `RequestCancelException.H()`.

### Exception Hierarchy Analysis

```
RuntimeException
  +-- RequestCancelException    (request was explicitly cancelled by user)
  +-- RequestTimeoutException   (request exceeded time limit)
```

Both exceptions:
- Extend `RuntimeException` (unchecked) - no mandatory catch
- Contain the `H(Object)` static deobfuscation method
- Are used in the code completion request lifecycle
- Have simple String-message constructors
- Are likely thrown in `CodeGenerateEditorRequest.cancel()` and timeout handlers

---

## 3. Package: com.aicode.error.search (3 classes)

### 3.1 DebuggerFilter

**File**: `com/aicode/error/search/DebuggerFilter.class` (source: "ah")  
**Type**: `public class`  
**Implements**: `com.intellij.execution.filter.JvmExceptionOccurrenceFilter`

This filter intercepts JVM exception occurrences in the console/run output and adds clickable inlay hints for AI-powered debugging.

#### Methods

| Method | Signature | Description |
|--------|-----------|-------------|
| `applyFilter()` | `Filter$ResultItem applyFilter(String, List<PsiClass>, int)` | Creates a `DebuggerFilter$V` result item |

#### applyFilter Logic

1. Validates input: `text` and `classes` must be non-null (throws obfuscated error otherwise)
2. Gets the first `PsiClass` from the list
3. Gets the `Project` from that PsiClass
4. Creates `new DebuggerFilter$V(text.length() + offset, offset, project)`
5. Returns the ResultItem which provides an inlay renderer

The filter adds a debug icon next to exception stack traces in the console, allowing users to click and trigger AI-powered code debugging.

### 3.2 DebuggerFilter$V (Inner Class)

**File**: `com/aicode/error/search/DebuggerFilter$V.class`  
**Type**: `public class`  
**Extends**: `com.intellij.execution.filters.Filter$ResultItem`  
**Implements**: `com.intellij.execution.impl.InlayProvider`

#### Fields

| Field | Type | Description |
|-------|------|-------------|
| `byte` | `int` | Offset position |
| `enum` | `Project` | IntelliJ project |

#### Constructor

```java
DebuggerFilter$V(int startOffset, int endOffset, Project project)
```

Calls `super(startOffset, endOffset, null)` (no HyperlinkInfo), stores project and offset.

#### createInlayRenderer(Editor)

1. Checks `PluginStartupActivity.getApiKey()` - returns null if no API key (no inlay)
2. Checks `AICodeSettingsState.getInstance().enableCodeDebug` - returns null if debug disabled
3. If enabled: creates `new Presentation(editor, project, offset)` as the renderer
4. Returns null if either condition fails

### 3.3 Presentation

**File**: `com/aicode/error/search/Presentation.class` (source: "dj")  
**Type**: `public class`  
**Implements**: `EditorCustomElementRenderer, InputHandler`

This is the custom inlay renderer that paints the debug icon and handles click/mouse interactions for AI debugging.

#### Fields

| Field | Type | Description |
|-------|------|-------------|
| `float` | `Project` | private final - IntelliJ project |
| `byte` | `Editor` | private final - editor instance |
| `enum` | `int` | private final - offset position |
| `suffixDir` | `List<String>` | public static - source directory suffixes |

#### Static Initialization: suffixDir

Initialized with 4 directory suffix patterns (using `File.separator`):
- `{sep}src{sep}main{sep}java` (Maven/Gradle Java source)
- `{sep}src{sep}main{sep}kotlin` (Maven/Gradle Kotlin source)
- `{sep}src{sep}test{sep}java` (Maven/Gradle Java test)
- `{sep}src{sep}test{sep}kotlin` (Maven/Gradle Kotlin test)

#### Key Methods

| Method | Signature | Description |
|--------|-----------|-------------|
| `paint()` | `void paint(Inlay, Graphics, Rectangle, TextAttributes)` | Renders debug icon centered in the inlay area |
| `calcWidthInPixels()` | `int calcWidthInPixels(Inlay)` | Returns `Icons.DebugIcon.getIconWidth()` |
| `mouseClicked()` | `void mouseClicked(MouseEvent, Point)` | Triggers AI debug on click |
| `mouseMoved()` | `void mouseMoved(MouseEvent, Point)` | Sets hand cursor on hover |
| `mouseExited()` | `void mouseExited()` | Restores default cursor |
| `handleDebug()` | `static void handleDebug(...)` | Multiple overloads for debug triggering |

#### paint() Logic

1. Checks if dark theme via `EditorColorsManager.getGlobalScheme().getColor(READONLY_FRAGMENT_BACKGROUND_COLOR)`
2. If null (dark theme): uses `Icons.DebugDarkIcon`
3. If non-null (light theme): uses `Icons.DebugIcon`
4. Centers the icon within the inlay rectangle

#### mouseClicked() Logic

1. Gets the clicked line number from the document
2. Calculates a range: from clicked line to min(clickedLine + 20, totalLines)
3. Gets the code text for that range
4. Gets the text from the offset to the end of the clicked line
5. If the line text is blank, returns
6. Calls `handleDebug(rangeText, lineText, true, true)` to trigger AI debugging

#### handleDebug() - Main Debug Entry Point

Two overloads:
1. **`handleDebug(String rangeText, String lineText, boolean isFullStack, boolean isSelected)`**:
   - Finds current project via `ApplicationUtil.findCurrentProject()`
   - If `lineText` is blank: calls `ChatService.handleCodeDebug(project, "", lineText, isSelected)` (simple mode)
   - Otherwise: parses the exception text using `Yd()` to extract file paths and line numbers
   - If parsed maps are empty: falls back to simple debug
   - If parsed: finds matching source file, calls `ChatService.handleCodeDebug(project, filePath, lineNumber, "", lineText, isSelected)`

2. **`handleDebug(Project, String, String, String, int)`**: Convenience overload

#### Yd() - Exception Text Parser

Private static method that parses exception stack trace text:
1. Compiles two regex patterns (deobfuscated):
   - Pattern 1: Matches Java stack trace lines like `at package.Class.method(File.java:123)` - extracts class path and line number
   - Pattern 2: Matches a simpler pattern for file references
2. Splits text by newline
3. For each line:
   - If blank, skip
   - Try Pattern 1 (full stack trace): if matches, put group(1) -> group(4) in first map
   - If not full stack and Pattern 2 matches: put group(1) -> group(2) in second map
4. Returns both maps via parameters

#### We() - Source File Resolver

Private static method that resolves a class path to an actual source file:
1. Iterates over source code directories from `FileUtil.getSourceCodeDirectories()`
2. For each directory, tries each suffix from `suffixDir`
3. Constructs path: `sourceDir + suffix + classPath`
4. If file exists, returns the path
5. Returns null if no match found

---

## 4. Package: com.aicode.status (3 classes)

### 4.1 AICodeStatusListener

**File**: `com/aicode/status/AICodeStatusListener.class` (source: "j")  
**Type**: `public interface`

IntelliJ message bus listener for AI Code status changes.

#### Fields

| Field | Type | Description |
|-------|------|-------------|
| `TOPIC` | `Topic<AICodeStatusListener>` | static final - message bus topic |

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
| `USER_LOGIN` | `Topic<UserLoginListener>` | static final - message bus topic |

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
| `messagePointer(String, Object...)` | `static Supplier<String>` | Returns lazy message supplier |

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
4. Result is something like: "Press {keybinding} to accept iFlyCode suggestion"

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
| `TOPIC` | `Topic<InlayListener>` | static final - message bus topic |

Topic name is deobfuscated via `FileExtensionLanguageDetails.H()`.

#### Method

```java
void inlaysUpdated(EditorRequestService request, OperateActionEnum action, Editor editor, List<Inlay<TipRenderer>> inlays)
```

Called when inlay hints are updated in the editor.

---

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
    |       Text: "Press {keybinding} to accept iFlyCode suggestion"
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
