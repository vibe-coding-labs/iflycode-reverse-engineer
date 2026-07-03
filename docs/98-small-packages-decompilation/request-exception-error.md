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
| `try` | `List&lt;String&gt;` | private final | tipLines |
| `float` | `String` | private | requestId |
| `byte` | `String` | private | scene |
| `enum` | `GetTipsResult$Tip` | private final | agentData |

#### Constructors

1. **`AgentCodeTip(GetTipsResult$Tip tip)`** - Primary constructor:
   - Stores the tip domain object
   - Splits `tip.getDisplayText()` into lines via `AICodeStringUtil.splitLines()`
   - Converts to immutable `List.of()`

2. **`AgentCodeTip(GetTipsResult$Tip tip, List&lt;String&gt; completion, boolean cached)`** - Copy constructor:
   - Stores tip and completion lines directly
   - The `cached` flag is consumed at construction but not stored as a field (used for validation only)

#### Key Methods

| Method | Signature | Description |
|--------|-----------|-------------|
| `getAgentData()` | `GetTipsResult$Tip` | Returns the underlying domain tip |
| `getTip()` | `List&lt;String&gt;` | Returns tip text lines (non-null asserted) |
| `getScene()` / `setScene()` | `String` | Scene context (e.g., "inline", "chat") |
| `getRequestId()` / `setRequestId()` | `String` | Request identifier |
| `getLanguage()` / `setLanguage()` | `String` | Programming language |
| `withCompletion(List&lt;String&gt;)` | `AgentCodeTip` | Immutable copy with new completion lines |
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
| `applyFilter()` | `Filter$ResultItem applyFilter(String, List&lt;PsiClass&gt;, int)` | Creates a `DebuggerFilter$V` result item |

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
| `suffixDir` | `List&lt;String&gt;` | public static - source directory suffixes |

#### Static Initialization: suffixDir

Initialized with 4 directory suffix patterns (using `File.separator`):
- `&#123;sep&#125;src&#123;sep&#125;main&#123;sep&#125;java` (Maven/Gradle Java source)
- `&#123;sep&#125;src&#123;sep&#125;main&#123;sep&#125;kotlin` (Maven/Gradle Kotlin source)
- `&#123;sep&#125;src&#123;sep&#125;test&#123;sep&#125;java` (Maven/Gradle Java test)
- `&#123;sep&#125;src&#123;sep&#125;test&#123;sep&#125;kotlin` (Maven/Gradle Kotlin test)

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
