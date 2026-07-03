# 92 - util Package Complete Decompilation

## Overview

Complete decompilation and analysis of all 37 classes in `com.aicode.util` package.
This package provides the foundational utility layer for the iFlyCode plugin, including
string manipulation, file operations, PSI tree navigation, editor caching, code highlighting,
UI component building, reflection, and the H() string deobfuscation mechanism.

**Source path**: `extracted/jar-contents/com/aicode/util/`
**Decompiler**: `javap -p -c` (OpenJDK 17.0.19)
**Total classes**: 37 (28 top-level + 9 inner)

---

## 1. Class Inventory

| # | Class | Source File | Type | H() Method | Key Role |
|---|-------|-----------|------|-----------|----------|
| 1 | AICodeStringUtil | ub | final | YES | String ops + H() decode entry |
| 2 | AICodeUtils | qb | public | YES | Editor/VirtualFile access, agent paths |
| 3 | Application | tb | final | YES | EDT/thread dispatch |
| 4 | ApplicationUtil | gb | final | NO | Project/language validation |
| 5 | ClassNameUtils | ab | public | NO | Class name extraction/resolution |
| 6 | CodeCheckUtil | kb | public | NO | Bracket matching, caret content |
| 7 | EditorCacheUtil | bb | public | NO | Editor UserData cache (Key-based) |
| 8 | EditorKt | vb | public | NO | Kotlin ext: inline chat cache, renderers |
| 9 | FileSizeUtil | pb | final | NO | File size check (1MB limit) |
| 10 | FileUtil | wb | public | NO | Source dir discovery, recent files |
| 11 | FileUtils | zb | public | NO | Plugin path, file copy, charset detect |
| 12 | HandleCacheUtil | nb | final | YES | Code completion cache handler |
| 13 | HighlighterUtil | jb | public | NO | Editor range highlighting |
| 14 | HighlighterUtil$01 | jb | inner | NO | Mouse click listener (multi-highlight) |
| 15 | HighlighterUtil$02 | jb | inner | NO | Mouse click listener (single highlight) |
| 16 | HighlighterUtil$EditorBranchRange | jb | inner | NO | Range data: start, end, result, out, root |
| 17 | IndentLineUtil | cb | public | YES | Smart indent calculation |
| 18 | JComponentKt | rb | final | YES | Kotlin ext: Swing component builders |
| 19 | JavaPsiUtils | JavaPsiUtils.java | public | NO | Java PSI: methods, enums, branches |
| 20 | LogUtil | ua | public | NO | Protocol logging (agent/web) |
| 21 | Maps | ha | final | YES | Map merge utility |
| 22 | MessageBundle | ja | final | NO | i18n message bundle (DynamicBundle) |
| 23 | NewFileUtils | pa | public | YES | File creation dialog handling |
| 24 | PluginComponentPanelBuilder | aa | public | NO | GridBag panel builder (settings UI) |
| 25 | PluginComponentPanelBuilder$CommentLabel | aa | inner | NO | Styled comment label |
| 26 | PluginComponentPanelBuilder$I$a | aa | inner | NO | Hyperlink comment label |
| 27 | PluginComponentPanelBuilder$i | aa | inner | NO | UI$Anchor ordinal switch map |
| 28 | PluginInfoUtils | ta | final | NO | Plugin ID, version, IDE detection |
| 29 | PositionUtil | da | public | YES | Line/column position calculation |
| 30 | PropertyUtils | wa | public | YES | Property accessor detection |
| 31 | PsiUtils | la | public | NO | Multi-language PSI operations |
| 32 | PsiUtils$J | la | inner | NO | Visibility predicate (public/private) |
| 33 | ReflectUtil | ya | public | NO | Reflection: fields, methods, class cache |
| 34 | StringUtils | oa | public | NO | String ops (extends hutool StrUtil) |
| 35 | TypeUtils | ra | public | NO | Type classification (collections, primitives) |
| 36 | UnitTestCollectUtil | va | public | NO | Test method discovery, diff analysis |
| 37 | VirtualFileUtils | za | public | NO | URI/path conversion (Windows compat) |

---

## 2. H() Deobfuscation Entry Analysis

### 2.1 Classes with H(Object) Methods

The H() method is the central string deobfuscation function. Each class that defines H()
contains its own copy of the decryption algorithm with a unique XOR key derived from
the caller's class+method name.

| Class | Key Derivation Pattern | Key Construction (bit ops) |
|-------|----------------------|--------------------------|
| AICodeStringUtil | methodName + className | `(1<<3)^3`, `(4<<3)^3`, `(4<<4)^(1<<4)` -> key from stack |
| AICodeUtils | className + methodName | `(3^5)<<4 ^ 1`, `(3^5)<<4 ^ (1<<1) ^ (3^5)<<4 ^ 3` |
| Application | methodName + className | `(3^5)<<4 ^ 3`, `(5<<4) ^ (3^5)<<4` |
| HandleCacheUtil | className + methodName | `(2^5)<<3 ^ (2 dup_x1)`, `(3^5)<<4 ^ 5` |
| IndentLineUtil | className + methodName | `(5<<4) ^ (2<<1)`, `(4<<5) ^ (3<<2)` |
| JComponentKt | methodName + className | Same pattern as AICodeStringUtil |
| Maps | methodName + className | `(2^5)<<3 ^ 5`, `(5<<3) ^ (3^5) ^ 1` |
| PositionUtil | methodName + className | `(5<<4) ^ (3<<2) ^ 1`, `(5<<3) ^ 4 ^ (5<<4) ^ (3^5)<<1` |
| PropertyUtils | methodName + className | `(2^5)<<4 ^ 5`, `(1<<3) ^ (3^5) ^ (4<<3) ^ 2` |

### 2.2 H() Algorithm (Common Pattern)

All H() implementations follow the same algorithm:

```
1. Get caller's class name and method name via LinkageError stack trace
2. Construct key string = methodName + className (or className + methodName)
3. Compute XOR shift constant from bit manipulation on key.length() - 1
4. For each position in the encoded string:
   result[i] = (char)(encoded.charAt(i) ^ key.charAt(keyPos) ^ xorConstant)
   keyPos decrements, wrapping to key.length() - 1 when reaching 0
5. Return new String(result)
```

### 2.3 Cross-Class H() Delegation

Classes in this package frequently delegate H() calls to other classes:

| Caller | Delegates H() To | Purpose |
|--------|------------------|---------|
| AICodeUtils | ActionButton.H, LanguageFileExtensionDetails.H, BasicActionsBundle.message | Agent paths, config keys |
| Application | CodeCompleteService.H, GenericUtils.H | Error messages |
| EditorCacheUtil | GitReviewService.H, EditorUtils.H | Cache key names |
| FileSizeUtil | JComponentKt.H | Key name |
| FileUtil | NewFileUtils.H, IdeAction.H | Directory names (".git", "src") |
| HandleCacheUtil | FileService.H, IdeAction.H | Error messages |
| IndentLineUtil | ActionButton.H, FileService.H | Error messages |
| LogUtil | ActionButton.H, GenericUtils.H | Log category strings |
| MessageBundle | FontKt.H, HandleCacheUtil.H | Bundle path, error messages |
| PluginInfoUtils | OverlayUtils.H, Maps.H | Property keys, plugin ID |
| PropertyUtils | FontKt.H, CodeCompleteService.H | Method prefixes ("set", "get", "is") |
| VirtualFileUtils | PropertyUtils.H, PositionUtil.H | Path prefixes ("file://", "/") |

---

## 3. Complete Class Decompilation Details

### 3.1 AICodeStringUtil (ub)

**Type**: `public final class`
**H() present**: YES (methodName + className order)

**Public Methods**:
- `H(Object)` -> String - Deobfuscation entry
- `isSpaceOrTab(char, boolean)` -> boolean - Checks if char is space/tab (or newline when flag set)
- `stripLeading(String)` -> String - Remove leading whitespace
- `trailingWhitespace(String)` -> String - Extract trailing whitespace
- `trailingWhitespaceLength(String)` -> int - Length of trailing whitespace
- `leadingWhitespaceLength(String)` -> int - Length of leading whitespace
- `leadingWhitespaceLengthWithTab(String, int)` -> int - Leading whitespace with tab size
- `leadingWhitespace(String)` -> String - Get leading whitespace substring
- `getNextLines(String, int, int)` -> List&lt;String&gt; - Get N lines starting from offset
- `notMatchSuffixIndex(String, String)` -> int - Find where suffixes diverge
- `splitLines(CharSequence)` -> String[] - Split text into lines
- `linesMatch(Iterable, Iterable, boolean)` -> boolean - Compare line sequences
- `createDiffInlays(String, String, boolean)` -> List<Pair<Integer,String>> - Create diff inlay markers
- `matchSuffixSection(String, String)` -> List<Pair<Integer,String>> - Match suffix sections
- `isSpacesOrTabs(CharSequence, boolean)` -> boolean - Check if all whitespace
- `findOverlappingLines(List, List)` -> int - Count overlapping lines
- `x(int[])` -> int[] - Process int array
- `Q(int[], int, int)` -> String - Build string from int array range

**Private Methods**: `V(int)`, `u(String,String)`, `U(TextRanges,int)`, `T(int[])`, `enum(int)`, `z(int[],int,char,char,TextRanges)`

**Key Logic**: Core string manipulation for diff computation and inlay generation. The `createDiffInlays` method is critical for inline chat diff rendering.

---

### 3.2 AICodeUtils (qb)

**Type**: `public class`
**H() present**: YES (className + methodName order)

**Public Methods**:
- `getVirtualFile(Project)` -> VirtualFile - Get selected editor's virtual file
- `getOpenFilePathList(Project)` -> List&lt;String&gt; - All open file paths
- `getPsiMethodContent(PsiFile, int, int)` -> PsiMethod - Find method at line range
- `hasInlineChat()` -> boolean - Check INLINE_CHAT permission
- `getAgentDirectoryPath()` -> String - Agent binary directory path
- `getWasmsDirectoryPath()` -> String - WASM directory path
- `getEditorFromAbsolutePath(Project, String)` -> Editor - Editor for file path
- `getEditor(FileEditorManager, VirtualFile)` -> Editor - Find TextEditor for virtual file

**H() Calls**: `ActionButton.H` (for "user.home"), `LanguageFileExtensionDetails.H` (for "aicode-agent"), `BasicActionsBundle.message`

**Key Logic**: `getAgentDirectoryPath()` constructs: `System.getProperty("user.home") + / + / + / + BasicActionsBundle.message(H-@!k...)`. The `getPsiMethodContent` uses `runReadAction` to find PsiMethods by line range.

---

### 3.3 Application (tb)

**Type**: `public final class`
**H() present**: YES (methodName + className order)

**Public Methods**:
- `runOnEdtJava(Runnable)` -> void - Run on EDT (invokeLater)
- `runOnEdt(Function0&lt;Unit&gt;)` -> void - Kotlin lambda on EDT
- `runOnPooledThread(Runnable)` -> Future<?> - Execute on pooled thread
- `H(Object)` -> String - Deobfuscation

**Key Logic**: Wraps IntelliJ's `ApplicationManager.getApplication()` for thread dispatch. The `runOnEdt` converts Kotlin `Function0` to `Runnable` via InvokeDynamic. Null checks throw via `enum(int)`.

---

### 3.4 ApplicationUtil (gb)

**Type**: `public final class`
**H() present**: NO

**Fields**:
- `supportLanguage: List&lt;String&gt;` - Initialized with 2 languages via H() calls to CancelRequestTip.H and AICodeLanguageInfo.H (likely "java" and "kotlin")

**Public Methods**:
- `findValidProjects()` -> Iterable&lt;Project&gt; - Non-null, non-disposed, non-default projects
- `findCurrentProject()` -> Project - From IdeFocusManager or first valid open project
- `isSupportLanguage(Editor)` -> Boolean - Always returns true (simplified)

---

### 3.5 ClassNameUtils (ab)

**Type**: `public class`
**H() present**: NO (uses H() from other classes)

**Public Methods**:
- `extractTargetPropertyName(String, boolean, boolean)` -> String - Extract property from getter/setter prefix
- `removeFromCamelCaseName(String, String)` -> String - Remove prefix from camel case
- `resolveGenericTypeName(String)` -> String - Resolve generic type (e.g., List&lt;String&gt; -> String)
- `isExceptionClass(String)` -> String - Check if class is exception type
- `stripArrayVarargsDesignator(String)` / `stripArrayVarargsDesignator(String, boolean)` -> String
- `resolveCanonicalName(Object, Object)` -> String
- `stripGenerics(String)` -> final String - Remove generic parameters
- `extractPackageName(String)` / `getPackageName(String)` -> String
- `extractClassName(String)` -> String
- `arrayDimensions(String)` -> int
- `isArray(String)` / `isVarargs(String)` -> boolean
- `extractContainerType(String)` -> String - e.g., List from List&lt;String&gt;
- `extractClassNameFormMethodId(String)` -> String
- `extractGenerics(String)` -> final String
- `extractMethodName(String)` -> String

**H() Delegations**: NewFileUtils.H, InlineChatStatusServiceKt.H, ActionButton.H, CodeCompleteService.H

---

### 3.6 CodeCheckUtil (kb)

**Type**: `public class`
**H() present**: NO

**Obfuscated Fields**: `final`, `try`, `float`, `byte`, `enum` (Java keyword names used as obfuscated field names)

**Public Methods**:
- `dffs(String)` -> int - Deep-first-find-symbol: bracket depth analysis
- `matcher(String, String)` -> boolean - Pattern matching
- `isBracketMatched(String)` / `isBracketMatched2(String)` -> boolean - Bracket balance check
- `getDocument(Editor)` -> Document
- `getCurrentLineNumber(Editor)` -> int
- `getCaretPreffix(Editor)` / `getCaretSuffix(Editor)` / `getCaretContent(Editor)` -> String
- `getLineEndOffset(Editor)` / `getLineStartOffset(Editor)` / `getCaretOffset(Editor)` -> int
- `extractPrefix(String)` -> String
- `isValid(String)` -> boolean

**Key Logic**: `dffs` uses Stack-based bracket tracking. `isBracketMatched` counts `&#123;&#125;` `[]` `()` balance.

---

### 3.7 EditorCacheUtil (bb)

**Type**: `public class`
**H() present**: NO

**Cache Keys** (all created via H() in static init):
- `byte: Key&lt;Boolean&gt;` - Edit cache flag (decoded via GitReviewService.H + EditorUtils.H)
- `enum: Key&lt;LastChatQuestionInfo&gt;` - Chat question cache
- `LAST_SELECTION_TEXT_CACHE_KEY: Key&lt;LastSelectionTextCache&gt;` - Selection text cache
- `ORIGINAL_SELECTION_TEXT_CACHE_KEY: Key&lt;LastSelectionTextCache&gt;` - Original selection cache

**Public Methods**:
- `setEditCache(Editor, boolean)` / `getEditCache(Editor)` / `delEditCache(Editor)` - Boolean edit flag
- `setCache(Editor, int, String, boolean)` / `getCache(Editor)` / `clearEditCache(Editor)` - LastChatQuestionInfo

**Key Logic**: Uses IntelliJ's `Editor.putUserData(Key, value)` / `Editor.getUserData(Key)` for per-editor caching.

---

### 3.8 EditorKt (vb)

**Type**: `public class` (Kotlin object)
**H() present**: NO

**Static Fields** (all Maps, Kotlin companion-style):
- `FILE_LANG: Map<String,String>` - File extension to language mapping
- `categoryRendererCollection: Map<String, InlineChatCategoryPanelRenderer>` - Category panel renderers per editor
- `stopRendererCollection: Map<String, InlineChatStopPanelRenderer>` - Stop panel renderers
- `rendererCollection: Map<String, Object>` - General renderer collection
- `commentSymbols: Map<String,String>` - Language comment symbols
- `inlineChatCacheData: Map<String, InlineChatInfo>` - Per-file inline chat state
- `inlineChatBtnCache: Map<Editor, Inlay<?>>` - Per-editor inlay button
- `inlineChatVersion: AtomicInteger` - Version counter for inline chat
- `endSymbols: Map<String,String>` - Language block-end symbols

**Public Methods**:
- `addInfoByEditor(Editor, InlineChatInfo)` - Store inline chat info keyed by virtual file path
- `addCursorListener(Editor, CaretListener)` / `addSelectionListener(Editor, SelectionListener)` - Listener registration
- `getErrorRendererTip(Editor, EditorRequestService)` -> String
- `getFileType(Editor)` -> String
- `getHasSelection(Editor)` -> boolean
- `closeCategoryPanel(Editor)` / `closeStopPanel(Editor)` / `closeButtonPanel(Editor)` - Panel cleanup
- `getFile(Editor)` -> VirtualFile
- `getInfoByVirtualFile(VirtualFile)` / `getInfoByEditor(Editor)` -> InlineChatInfo
- `removeEditor(Editor)` - Remove all cached data for editor
- `containEditor(Editor)` -> boolean

**Key Logic**: Central registry for inline chat state. Uses `EditorImpl.getVirtualFile().getPath()` as cache key. All maps are static (singleton pattern from Kotlin object).

---

### 3.9 FileSizeUtil (pb)

**Type**: `public final class`
**H() present**: NO

**Fields**: `KEY_TOO_LARGE: Key&lt;Boolean&gt;` (created via JComponentKt.H)

**Public Methods**:
- `isSupported(VirtualFile)` -> boolean - Returns true if file <= 1MB and KEY_TOO_LARGE not set

---

### 3.10 FileUtil (wb)

**Type**: `public class`
**H() present**: NO

**Fields**:
- `byte: ConcurrentHashMap<String,Long>` - Timestamp cache
- `enum: ConcurrentHashMap<String,List&lt;String&gt;>` - Directory list cache

**Public Methods**:
- `findSourceCodeDirectories(String)` -> List&lt;String&gt; - Find .git/src directories recursively
- `openFileList(Project)` -> JsonArray - Recent files (max 10) as JSON
- `currentOpenFile(Project)` -> String - Current file path, tracks via RecentFilesManager
- `getSourceCodeDirectories(Project)` -> List&lt;String&gt; - Cached version (30-minute TTL)

**H() Delegations**: NewFileUtils.H (".git"), IdeAction.H ("src")

---

### 3.11 FileUtils (zb)

**Type**: `public class`
**H() present**: NO

**Static Fields** (all platform-specific paths):
- `AGENT_DIR`, `BIN_DIR`, `WASM_DIR`, `FILE_TEMPLATES`, `CONFIG_JSON`
- `X86_64_DARWIN_NODE`, `X86_64_DARWIN_ARM_NODE`, `X86_64_LINUX_NODE`
- `X86_64_WINDOWS_NODE`, `X86_64_WINDOWS7_NODE`
- `NODE_EXE_FILENAME`, `AGENT_EXE_SUFFIX`

**Public Methods**:
- `getFileOfPluginPath(String)` -> File - Path in plugins directory
- `getPath(String)` -> String - Resource path via Class.getResource
- `getTypeName(String)` -> String - File type name
- `getLocalPath()` -> Path - Local plugin path
- `copyFile/copyDir/copyFileContent` - File copy operations
- `deleteFileOfPluginPath(String)` - Delete plugin file
- `getContent(String)` -> String - Read file content
- `getAndCreateFileOfPluginPath(String)` -> File - Create if not exists
- `getFileExtension(String)` -> String
- `detectCharset(File)` -> String - Charset detection

---

### 3.12 HandleCacheUtil (nb)

**Type**: `public final class`
**H() present**: YES (className + methodName order)

**Public Methods**:
- `handleCache(EditorRequestService, TipCache)` -> List&lt;CodeInlayList&gt; - Main cache handler
- `H(Object)` -> String - Deobfuscation

**Private Methods**:
- `D(LineInfo)` -> boolean - Check if line suffix is empty or valid middle position
- `a(EditorRequestService, boolean, CodeTip)` -> CodeInlayList - Create code tip via CodeTipUtil

**Key Logic**: `handleCache` checks `internalDisableHttpCache` setting, validates line info, gets latest cache from TipCache, filters by prefix match, and maps CodeTip to CodeInlayList. Returns null if cache miss or disabled.

---

### 3.13 HighlighterUtil (jb) + Inner Classes

**Type**: `public class`

**Fields**: `HIGH_LIGHTER: Key<List&lt;RangeHighlighter&gt;>` (created via ConditionalActionConfiguration.H)

**Public Methods**:
- `highlightText(Editor, List&lt;EditorBranchRange&gt;)` - Multi-range highlighting with color (169,76,0) for results, gray for non-results
- `highlightText(Editor, int, int, boolean)` - Single range highlighting with color (90,235,0) or (238,95,91)

**Private Methods**: `f`, `F`, `m`, `b`, `i`, `j` - Range comparison predicates

**HighlighterUtil$EditorBranchRange**:
- Fields: `enum:int` (startOffset), `float:int` (endOffset), `try:boolean` (isResult), `final:boolean` (isOut), `byte:boolean` (isRoot)
- Constructor: `(int startOffset, int endOffset, boolean isResult, boolean isOut, boolean isRoot)`
- Standard equals/hashCode/toString + getters/setters

**HighlighterUtil$01** (EditorMouseListener):
- Fields: `byte:List` (highlighters), `try:MarkupModel`, `float:Editor`, `enum:Disposable`
- `mouseClicked` -> Removes all highlighters, clears HIGH_LIGHTER key, disposes

**HighlighterUtil$02** (EditorMouseListener):
- Fields: `byte:MarkupModel`, `enum:RangeHighlighter`, `float:Disposable`
- `mouseClicked` -> Removes single highlighter, disposes

---

### 3.14 IndentLineUtil (cb)

**Type**: `public class`
**H() present**: YES (className + methodName order)

**Public Methods**:
- `indentLine(Project, Editor, int, int, int)` -> int - Smart indent (delegates with shouldUseSmartTabs)
- `indentLine(Project, Editor, int, int, int, boolean)` -> int - Full indent calculation
- `H(Object)` -> String - Deobfuscation

**Private Methods**:
- `k(CharSequence, int, int, int)` -> int - Count visual columns accounting for tabs

**Key Logic**: Calculates indent offset for code insertion. Handles tab/space mix, smart tabs, and column alignment. Returns the target offset for the new indented position.

---

### 3.15 JComponentKt (rb)

**Type**: `public final class` (Kotlin extension functions)
**H() present**: YES (methodName + className order)

**Public Methods** (all return JComponent for chaining):
- `font(JComponent, Font)` -> JComponent - Set font
- `findComponent(JComponent)` -> T - Find child by type (reified)
- `findComponent(KClass&lt;T&gt;, Component)` -> T - Find child by KClass
- `isChildFocused(JComponent)` -> boolean
- `minimumSize(JComponent, int, int)` / `preferredSize(JComponent, int, int)` / `maximumSize(JComponent, int, int)` -> JComponent
- `inAllChildren(JComponent, Function1)` -> void - Apply lambda to all children
- `removeInsets(JComponent)` -> JComponent - Remove insets
- `update(JComponent)` -> JComponent - Repaint + revalidate
- `opaque(JComponent, boolean)` -> JComponent
- `border(JComponent, Border)` -> JComponent
- `lockMouseInteractions(JComponent)` -> JComponent - Disable mouse events

**Key Logic**: Kotlin-style builder pattern for Swing components. All methods return the component for chaining.

---

### 3.16 JavaPsiUtils (JavaPsiUtils.java)

**Type**: `public class`
**H() present**: NO (unobfuscated source name suggests less obfuscation)

**Fields**:
- `INVALID_METHOD_NAMES: Set&lt;String&gt;` - Methods to skip
- `INVALID_JAVA_TOKENSET: TokenSet` - Tokens to skip
- `PATTERN: Pattern` - Regex for method signature parsing

**Public Methods** (22 total, large class):
- `isInvalidCodeElement(PsiElement)` -> boolean
- `getPsiMethodContent(Project, PsiFile, SelectionModel)` -> String
- `getPsiClassName(PsiFile, SelectionModel)` -> String
- `getJavaFileType()` -> FileType
- `getFollowingMethodSignatureFromComment(PsiComment)` -> String
- `isInvalidJavaMethod(PsiElement)` -> boolean
- `isMethodEmpty(PsiMethod)` / `isAbstractMethod(PsiMethod)` / `isDefaultMethod(PsiClass, PsiMethod)` -> boolean
- `resolveIfEnum(PsiClass)` -> boolean
- `resolveJavaEnumValues(PsiClass)` -> List&lt;String&gt;
- `findSpecialElementsInMethod(PsiMethod)` -> ResolvedBranch - Find if/try/return branches
- `findSpecialElementInPsiIfStatement(...)` / `findSpecialElementInPsiTryStatement(...)` -> void
- `checkHasReturn(PsiStatement, ResolvedBranch)` -> void
- `findResolvedMethodCalls(PsiMethod)` -> List&lt;ResolvedMethodCall&gt;
- `findMethodCalls(...)` -> void - Recursive method call discovery
- `findReferences(PsiMethod)` -> List&lt;ResolvedReference&gt;
- `findMethodReferences(PsiMethod)` -> List&lt;PsiMethod&gt;
- `getContainingClassFromMethodCall(PsiMethodCallExpression)` -> PsiClass

---

### 3.17 LogUtil (ua)

**Type**: `public class`
**H() present**: NO

**Fields**:
- `WEB_RECEIVE: String` - Decoded via GenericUtils.H (likely "web_receive")
- `AGENT_SEND: String` - Decoded via ActionButton.H (likely "agent_send")
- `enum: Logger` - IntelliJ diagnostic logger
- `PRINT_STACK_TRACE: AtomicBoolean` - Toggle for logging

**Public Methods**:
- `info(String, String)` -> void - Conditional logging by category

**Key Logic**: Only logs when `PRINT_STACK_TRACE` is true. For WEB_RECEIVE, logs with prefix. For AGENT_SEND, parses JSON to extract command type and data, then logs selectively (skips USER_VERSION, includes LOG_TEST_COLLECTION_*).

---

### 3.18 Maps (ha)

**Type**: `public final class`
**H() present**: YES (methodName + className order)

**Public Methods**:
- `merge(Map<K,? extends V>...)` -> Map<K,V> - Merge multiple maps into unmodifiable map
- `H(Object)` -> String - Deobfuscation

---

### 3.19 MessageBundle (ja)

**Type**: `public final class extends DynamicBundle`
**H() present**: NO

**Fields**: `INSTANCE: MessageBundle` (singleton)

**Public Methods**:
- `get(String)` -> String - Get message with no args
- `get(String, Object...)` -> String - Get message with args

**Key Logic**: Extends IntelliJ's `DynamicBundle` for i18n. Bundle path decoded via HandleCacheUtil.H. The `enum(int)` method validates non-null keys.

---

### 3.20 NewFileUtils (pa)

**Type**: `public class`
**H() present**: YES

**Public Methods**:
- `creatFile(Project, String, String, String, CodeCollectEnum)` -> void - Create file with content
- `handleCreateFile(Project, UnitTestDto$DataDTO, String, String, String, CodeCollectEnum)` -> void
- `handleCreateFile(Project, String, String, String, String, CodeCollectEnum)` -> void
- `showDialog(Project, TextFieldWithBrowseButton, JBTextField, JBCheckBox, String, String)` -> DialogBuilder
- `getChooseFile(Project)` -> String - File chooser dialog
- `H(Object)` -> String - Deobfuscation

---

### 3.21 PluginComponentPanelBuilder (aa) + Inner Classes

**Type**: `public class implements GridBagPanelBuilder`

**Obfuscated Fields** (all Java keywords): `true:Runnable`, `this:boolean`, `else:boolean`, `char:String`, `int:String`, `new:JComponent`, `long:String`, `super:boolean`, `for:boolean`, `if:JComponent`, `case:HyperlinkListener`, `final:boolean`, `try:UI$Anchor`, `float:String`, `byte:boolean`, `enum:Icon`

**Public Methods**:
- `createPanel()` -> JPanel - Build the settings panel
- `withTopRightComponent(JComponent)` -> PluginComponentPanelBuilder
- `setCommentText(JLabel, String, boolean, int)` -> void - Static comment setter
- `createCommentComponent(String, boolean)` -> JLabel
- `computeCommentInsets(JComponent, boolean)` -> Insets
- `resizeY(boolean)` / `resizeX(boolean)` / `moveCommentRight()` -> PluginComponentPanelBuilder

**PluginComponentPanelBuilder$CommentLabel** (extends JBLabel):
- Custom foreground color via `JBColor.namedColor` (Gray.x78 / Gray.x8C)
- `setUI` overrides to apply comment font

**PluginComponentPanelBuilder$I$a** (extends CommentLabel):
- Hyperlink-enabled comment label
- `createHyperlinkListener()` -> HyperlinkListener

**PluginComponentPanelBuilder$i**:
- Static `enum: int[]` - UI$Anchor ordinal mapping (Top=1, Center=2, Bottom=3)

---

### 3.22 PluginInfoUtils (ta)

**Type**: `public final class`
**H() present**: NO

**Fields**:
- `AICODE_ID: PluginId` - Plugin ID (decoded via Maps.H + BasicActionsBundle.message)
- `enum: boolean` - Assertion status

**Public Methods**:
- `getPluginBasePath()` -> Path - Plugin directory path
- `isRemoteIDE()` -> boolean - Checks system property (decoded via OverlayUtils.H + Maps.H)
- `isSupportedIDE(Project)` -> boolean - Not remote IDE and not LightEdit
- `getVersion()` -> String - Plugin version from descriptor
- `isAICodePlugin(PluginDescriptor)` -> boolean
- `getJetBrainsIDEVersion(String)` -> String - IDE version (major or full)

---

### 3.23 PositionUtil (da)

**Type**: `public class`
**H() present**: YES (methodName + className order)

**Public Methods**:
- `getStartLineAndColumn(Project, PsiFile)` -> int[2] - [lineNumber, column] at start
- `getEndLineAndColumn(Project, PsiFile)` -> int[2] - [lineNumber, column] at end
- `H(Object)` -> String - Deobfuscation

---

### 3.24 PropertyUtils (wa)

**Type**: `public class`
**H() present**: YES (methodName + className order)

**Public Methods**:
- `isPropertySetter(PsiMethod)` / `isPropertySetter(PsiMethod, String)` -> boolean
- `isPropertyGetter(PsiMethod)` -> boolean
- `hasSetter(PsiMethod, String)` / `hasGetter(PsiMethod, String)` -> boolean
- `isSampleSetMethod(String, Method)` / `isSampleGetMethod(String, Method)` -> boolean
- `inferFieldNameFromAccessor(String)` -> String - "setFoo" -> "foo", "isBar" -> "bar"
- `isMainMethod(PsiMethod)` -> boolean - Check if method is `public static void main(String[])`
- `H(Object)` -> String - Deobfuscation

**H() Delegations**: FontKt.H ("set", "get", "is", "main"), CodeCompleteService.H ("public", "void")

---

### 3.25 PsiUtils (la) + PsiUtils$J

**Type**: `public class`

**Fields**:
- `try: Logger`, `float: List&lt;String&gt;`, `byte: String[]`, `enum: TokenSet`
- `DEFAULT_CLASS_NAME: String`, `IDENTIFIER: String`

**Public Methods** (large class, 20+ methods):
- `getPsiClassName(PsiFile, SelectionModel)` -> String - Delegates to JavaPsiUtils for Java
- `isCommentElement(PsiElement, Editor)` -> boolean
- `getContainingClass(PsiElement)` -> PsiClass
- `getPsiMethodContent(Project, PsiFile, SelectionModel)` -> String
- `isPythonIdentifier(String)` -> boolean
- `getLineTextAtCaret(Editor)` -> String
- `generateVisibilityPredicator(PsiClass, PsiClass)` -> Predicate&lt;PsiModifierListOwner&gt;
- `formatMethodId(PsiClass, String, PsiType[])` -> String
- `isProtectedModifier(PsiModifierListOwner)` / `isPublicModifier` / `isPrivateModifier` -> boolean
- `isInvalidCodeElement(PsiElement)` -> boolean
- `getMethodsFromPsiClass(PsiClass, boolean, String, Predicate)` -> PsiMethod[]
- `findNonWhitespaceAtOffset(PsiFile, int)` -> PsiElement
- `checkCaretAround(Editor)` -> boolean
- `getPackageName(PsiClass)` -> String
- `getLanguageByCurrentFile(Editor)` -> String
- `getField(String, String)` -> Object - Static field access via reflection

**PsiUtils$J** (Predicate&lt;PsiModifierListOwner&gt;):
- Fields: `float:boolean` (includePrivate), `byte:boolean` (includeProtected), `enum:boolean` (includePackagePrivate)
- `default(PsiModifierListOwner)` -> boolean - Visibility filter logic

---

### 3.26 ReflectUtil (ya)

**Type**: `public class`
**H() present**: NO

**Fields** (all ConcurrentHashMap caches):
- `final: Map<Class, Method[]>` - Declared methods cache
- `try: Map<Class, Field[]>` - Declared fields cache
- `enum: Map<String, Class<?>>` - Class.forName cache
- `float: Method[]`, `byte: Field[]` - Empty arrays

**Public Methods**:
- `getAllFields(Class<?>)` -> List&lt;Field&gt; - All fields including superclass
- `getAllMethod(Class<?>)` -> List&lt;Method&gt; - All methods including superclass
- `getMethod(Class<?>, String, Class<?>...)` -> Method - Find method in class hierarchy
- `replaceField(Field, Object)` -> void - Set field accessible + set value
- `classForName(String)` -> Class<?> - Cached Class.forName
- `getObjField(Object, String)` -> Object - Get field value by name
- `getField(Field, Object)` -> Object - Get field value
- `getStaticField(Class<?>, String)` -> T - Get static field value

---

### 3.27 StringUtils (oa)

**Type**: `public class extends cn.hutool.core.util.StrUtil`
**H() present**: NO

**Public Methods**:
- `hasLine(String, String)` -> boolean - Check if any line matches regex
- `removeSuffix(String, String)` -> String
- `extractClassName(String)` -> String - Delegate to ClassNameUtils + deCapitalize
- `deCapitalizeFirstLetter(String)` -> String - "Foo" -> "foo"
- `capitalizeFirstLetter(String)` -> String - "foo" -> "Foo"
- `splitBySize(String, int)` -> List&lt;String&gt; - Fixed-length split (Guava Splitter)
- `remove(String, char)` -> String - Remove all occurrences of char
- `camelCaseToWords(String)` -> String - "fooBar" -> "foo bar"

---

### 3.28 TypeUtils (ra)

**Type**: `public class`
**H() present**: NO

**Obfuscated Fields** (18 static fields, all Java keywords): `break`, `class`, `true`, `this`, `else`, `char`, `int`, `new`, `long`, `super`, `for`, `if`, `case`, `final`, `try`, `float`, `byte`, `enum`

**Public Fields**:
- `JAVA_FUTURE_TYPES: Set&lt;String&gt;` - Future/CompletableFuture types
- `WRAPPER_TYPES: Set&lt;String&gt;` - Primitive wrapper types
- `TYPE_TO_ARG_MATCHERS: Map<String,String>` - Type to Mockito matcher mapping

**Public Methods**:
- `isIgnore(String)` -> boolean - Check if type should be ignored
- `isMap(String)` -> Boolean - Check if type is Map
- `isNoMockStaticType(String, Type)` -> boolean
- `isInArray(Type)` -> boolean
- `isDateType(Type)` -> boolean
- `getLegacyJavaReplacementTypes()` -> Map<String,String>
- `hasValidEmptyConstructor(Type)` -> boolean

---

### 3.29 UnitTestCollectUtil (va)

**Type**: `public class`
**H() present**: NO

**Fields**:
- `UNIT_TEST_METHOD_FLAG: String`
- `float: Logger`, `byte: Pattern`, `enum: Pattern`

**Public Methods**:
- `isTestOfMethod(PsiMethod)` -> boolean - Check if method has test annotations
- `getAllMethods(Project, Document)` -> List&lt;UnitTestMethodDto&gt; - All test methods in document
- `getChangeMethods(List, List, boolean)` -> List&lt;UnitTestMethodDto&gt; - Filter by changes
- `getChangeByDiff(String, int)` -> List&lt;ChangeInfoDto&gt; - Parse unified diff
- `diffContent(List&lt;Change&gt;, Project)` -> String - Generate diff content
- `getTestMethodId(PsiMethod)` -> String

**Key Logic**: Uses `runReadAction` + `executeOnPooledThread` for concurrent annotation scanning. Checks for JUnit 5 (@Test), JUnit 4, and TestNG annotations.

---

### 3.30 VirtualFileUtils (za)

**Type**: `public class`
**H() present**: NO

**Public Methods**:
- `from(VirtualFile)` -> String - Convert to URI string (handles Windows paths)
- `getUri(VirtualFile)` -> String - Delegate to from()
- `null(String)` -> String - Normalize URI (Windows UNC path handling)

**Private Methods**:
- `void(String)` -> String - Internal path normalization
- `const(VirtualFileSystem)` -> boolean - Check if local FS (excluding WSL on Windows)

**Key Logic**: Complex Windows path handling. Converts `/C:/` to `C:/`, handles UNC paths (`//server/share`), and WSL path detection. Uses `PsiUtils.instanceOf` for WSL filesystem check.

---

## 4. H() Call Frequency Statistics

### 4.1 H() Methods Defined in This Package

| Class | Has Own H() | Key Order |
|-------|------------|-----------|
| AICodeStringUtil | YES | methodName + className |
| AICodeUtils | YES | className + methodName |
| Application | YES | methodName + className |
| HandleCacheUtil | YES | className + methodName |
| IndentLineUtil | YES | className + methodName |
| JComponentKt | YES | methodName + className |
| Maps | YES | methodName + className |
| PositionUtil | YES | methodName + className |
| PropertyUtils | YES | methodName + className |

**Total**: 9 classes define their own H() method

### 4.2 External H() Delegation Targets

| Target Class | Called By (in this package) | Call Count |
|-------------|---------------------------|------------|
| ActionButton.H | AICodeUtils, Application, IndentLineUtil, LogUtil | 4 |
| CodeCompleteService.H | Application, PropertyUtils | 2 |
| GenericUtils.H | Application, LogUtil | 2 |
| GitReviewService.H | EditorCacheUtil | 2 |
| EditorUtils.H | EditorCacheUtil, HighlighterUtil | 2 |
| LanguageFileExtensionDetails.H | AICodeUtils, ClassNameUtils | 2 |
| NewFileUtils.H | ClassNameUtils, FileUtil | 2 |
| IdeAction.H | FileUtil, HandleCacheUtil | 2 |
| FileService.H | HandleCacheUtil, IndentLineUtil | 2 |
| FontKt.H | MessageBundle, PropertyUtils | 2 |
| HandleCacheUtil.H | MessageBundle | 1 |
| CancelRequestTip.H | ApplicationUtil | 1 |
| AICodeLanguageInfo.H | ApplicationUtil, HighlighterUtil$01 | 2 |
| ConditionalActionConfiguration.H | HighlighterUtil | 1 |
| InlineChatStatusServiceKt.H | ClassNameUtils, HighlighterUtil | 2 |
| RequestResultList.H | HighlighterUtil$01, PluginComponentPanelBuilder$I$a | 2 |
| OverlayUtils.H | PluginInfoUtils | 2 |
| Maps.H | PluginInfoUtils | 2 |
| FileInfo.H | StringUtils | 2 |
| FileExtensionLanguageDetails.H | PsiUtils, StringUtils | 2 |
| JComponentKt.H | FileSizeUtil, PluginComponentPanelBuilder$CommentLabel | 2 |
| PropertyUtils.H | VirtualFileUtils | 3 |
| PositionUtil.H | VirtualFileUtils | 3 |
| PsiUtils (instanceOf) | VirtualFileUtils | 1 |
| RequestTimeoutException.H | PluginComponentPanelBuilder$CommentLabel | 2 |
| IndentLineUtil.H | PluginComponentPanelBuilder$I$a | 1 |
| ChatInputController.H | HighlighterUtil$02 | 1 |
| OpenTelemetryUtil.H | PluginComponentPanelBuilder | 1 |

### 4.3 Internal H() Cross-Calls Within Package

| Caller | Callee (in same package) |
|--------|------------------------|
| MessageBundle | HandleCacheUtil.H |
| FileSizeUtil | JComponentKt.H |
| PluginComponentPanelBuilder$CommentLabel | JComponentKt.H |
| VirtualFileUtils | PropertyUtils.H, PositionUtil.H |

---

## 5. Service Layer Interactions

### 5.1 Services Called From util Package

| util Class | Service Class | Method | Purpose |
|-----------|--------------|--------|---------|
| AICodeUtils | AICodeSettingsState | getInstance() | Check INLINE_CHAT permission |
| AICodeUtils | RecentFilesManager | fileOpened(), getRecentFiles() | Track open files |
| HandleCacheUtil | AICodeRequestSettings | settings() | Check internalDisableHttpCache |
| HandleCacheUtil | CodeTipUtil | createEditorCodeTip(), TrimEndSpaceTab() | Generate code tips |
| HandleCacheUtil | CommonLanguageSupport | isValidMiddleOfTheLinePosition() | Validate cursor position |
| LogUtil | AICodeSettingsState | (via CommandEnum) | Log category routing |
| EditorKt | InlineChatCategoryPanelRenderer | (constructor) | Panel renderer creation |
| EditorKt | InlineChatStopPanelRenderer | (constructor) | Stop panel renderer |
| NewFileUtils | content.util.file.FileUtils | createFile() | File creation |
| FileUtil | RecentFilesManager | fileOpened(), getRecentFiles() | File tracking |
| UnitTestCollectUtil | (various VCS services) | Change, ContentRevision | Diff analysis |

### 5.2 Services That Call Into util Package

| Service Class | util Target | Purpose |
|--------------|------------|---------|
| CodeCompleteService | AICodeStringUtil, HandleCacheUtil | String processing, cache |
| InlineChatService | EditorKt, EditorCacheUtil, HighlighterUtil | Chat state, highlighting |
| EditorRequestService | AICodeUtils, PositionUtil, IndentLineUtil | Editor queries |
| GitReviewService | AICodeUtils, FileUtil | File/project access |
| TemplateEngine | PsiUtils, JavaPsiUtils, PropertyUtils, TypeUtils | Code generation |
| UnitTestService | UnitTestCollectUtil, NewFileUtils | Test generation |

---

## 6. Kotlin Extension Functions

### 6.1 EditorKt (from vb)

All functions are static methods taking `Editor` as first parameter (Kotlin extension style):

| Function | Signature | Purpose |
|----------|-----------|---------|
| addInfoByEditor | (Editor, InlineChatInfo) -> void | Cache inline chat info |
| addCursorListener | (Editor, CaretListener) -> void | Register caret listener |
| addSelectionListener | (Editor, SelectionListener) -> void | Register selection listener |
| getErrorRendererTip | (Editor, EditorRequestService) -> String | Error tooltip |
| getFileType | (Editor) -> String | Language type |
| getHasSelection | (Editor) -> boolean | Has text selection |
| closeCategoryPanel | (Editor) -> void | Close category panel |
| closeStopPanel | (Editor) -> void | Close stop panel |
| closeButtonPanel | (Editor) -> void | Close button panel |
| getFile | (Editor) -> VirtualFile | Get virtual file |
| getInfoByVirtualFile | (VirtualFile) -> InlineChatInfo | Get cached info |
| getInfoByEditor | (Editor) -> InlineChatInfo | Get cached info |
| removeEditor | (Editor) -> void | Remove all editor state |
| containEditor | (Editor) -> boolean | Check if editor tracked |

### 6.2 JComponentKt (from rb)

All functions return JComponent for builder-style chaining:

| Function | Signature | Purpose |
|----------|-----------|---------|
| font | (JComponent, Font) -> JComponent | Set font |
| findComponent | (JComponent) -> T | Find child by reified type |
| findComponent | (KClass&lt;T&gt;, Component) -> T | Find child by KClass |
| isChildFocused | (JComponent) -> boolean | Check focus |
| minimumSize | (JComponent, int, int) -> JComponent | Set min size |
| preferredSize | (JComponent, int, int) -> JComponent | Set preferred size |
| maximumSize | (JComponent, int, int) -> JComponent | Set max size |
| inAllChildren | (JComponent, Function1) -> void | Iterate children |
| removeInsets | (JComponent) -> JComponent | Remove insets |
| update | (JComponent) -> JComponent | Repaint + revalidate |
| opaque | (JComponent, boolean) -> JComponent | Set opacity |
| border | (JComponent, Border) -> JComponent | Set border |
| lockMouseInteractions | (JComponent) -> JComponent | Disable mouse |

---

## 7. Obfuscation Patterns

### 7.1 Field Name Obfuscation

The most heavily obfuscated classes use Java reserved keywords as field names:

| Class | Obfuscated Fields |
|-------|------------------|
| CodeCheckUtil | `final`, `try`, `float`, `byte`, `enum` |
| TypeUtils | `break`, `class`, `true`, `this`, `else`, `char`, `int`, `new`, `long`, `super`, `for`, `if`, `case`, `final`, `try`, `float`, `byte`, `enum` |
| PluginComponentPanelBuilder | `true`, `this`, `else`, `char`, `int`, `new`, `long`, `super`, `for`, `if`, `case`, `final`, `try`, `float`, `byte`, `enum` |
| HighlighterUtil$EditorBranchRange | `final`, `try`, `float`, `byte`, `enum` |
| UnitTestCollectUtil | `float`, `byte`, `enum` |

### 7.2 Method Name Obfuscation

Private methods use single-letter or keyword names:

| Class | Obfuscated Methods |
|-------|-------------------|
| AICodeStringUtil | `V`, `u`, `U`, `T`, `z` |
| AICodeUtils | `p`, `w`, `W`, `y` |
| ClassNameUtils | `Y` |
| CodeCheckUtil | `o` |
| FileUtil | `r`, `M` |
| HandleCacheUtil | `D`, `a` |
| HighlighterUtil | `f`, `F`, `m`, `b`, `i`, `j` |
| IndentLineUtil | `k` |
| JavaPsiUtils | (mostly unobfuscated) |
| PsiUtils | `h`, `g`, plus keyword names: `transient`, `throws`, `continue`, `protected`, `native` |
| ReflectUtil | `implements`, `public` |
| UnitTestCollectUtil | `throw`, `boolean`, `while`, `abstract`, `package`, `interface`, `double`, `extends`, `private`, `import`, `volatile`, `return`, `do`, `this` |
| VirtualFileUtils | `void`, `null`, `const` |

### 7.3 Source File Obfuscation

Most classes have 2-letter obfuscated source file names (e.g., "ub", "qb", "tb"). Exceptions:
- `JavaPsiUtils` -> "JavaPsiUtils.java" (unobfuscated, likely auto-generated or less protected)

### 7.4 enum(int) Pattern

Every class contains a private `enum(int)` method that serves as a null-check assertion handler.
When a null value is detected, `enum(int)` is called with an error code, which:
1. Decodes an error message template via H()
2. Formats it with context-specific arguments
3. Throws `IllegalArgumentException` or `IllegalStateException`

---

## 8. Architecture Summary

```
com.aicode.util
|
|-- String Layer
|   |-- AICodeStringUtil    (H() entry, diff, whitespace)
|   |-- StringUtils         (extends hutool, camelCase, split)
|   |-- ClassNameUtils      (class name parsing, generics)
|   |-- Maps                (map merge)
|   |-- MessageBundle       (i18n, DynamicBundle)
|
|-- Editor Layer
|   |-- AICodeUtils         (editor/file access, agent paths)
|   |-- EditorCacheUtil     (UserData cache keys)
|   |-- EditorKt            (inline chat state, renderers)
|   |-- CodeCheckUtil       (bracket matching, caret)
|   |-- IndentLineUtil      (smart indent)
|   |-- HighlighterUtil     (range highlighting + 3 inner classes)
|   |-- PositionUtil        (line/column calculation)
|
|-- PSI Layer
|   |-- PsiUtils            (multi-language PSI, visibility)
|   |-- PsiUtils$J          (visibility predicate)
|   |-- JavaPsiUtils        (Java-specific PSI, branches, enums)
|   |-- PropertyUtils       (getter/setter detection)
|
|-- File Layer
|   |-- FileUtil            (source dirs, recent files, cache)
|   |-- FileUtils           (plugin paths, copy, charset)
|   |-- FileSizeUtil        (1MB limit check)
|   |-- NewFileUtils        (file creation dialogs)
|   |-- VirtualFileUtils    (URI/path conversion)
|
|-- UI Layer
|   |-- JComponentKt        (Kotlin Swing builders)
|   |-- PluginComponentPanelBuilder (+ 3 inner classes)
|
|-- Infrastructure
|   |-- Application         (EDT/thread dispatch)
|   |-- ApplicationUtil     (project validation)
|   |-- HandleCacheUtil     (completion cache)
|   |-- LogUtil             (protocol logging)
|   |-- PluginInfoUtils     (plugin ID/version)
|   |-- ReflectUtil         (reflection cache)
|   |-- TypeUtils           (type classification)
|   |-- UnitTestCollectUtil (test discovery, diff)
```
