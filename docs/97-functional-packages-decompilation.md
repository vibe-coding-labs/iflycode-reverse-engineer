# Doc 97: Functional Packages Complete Decompilation

## Overview

Complete bytecode-level decompilation and analysis of 8 functional packages containing ~58 classes total. All classes decompiled using `javap -p -c` with full bytecode disassembly.

**Total classes analyzed: 58** (9 domain + 6 settings + 8 updater + 6 diff + 5 generate + 5 content + 8 language + 7 apm + 4 subpackage)

---

## Package 1: com.aicode.domain (9 classes)

### 1.1 CommandCache

| Aspect | Detail |
|--------|--------|
| Type | Mutable data class (Lombok-style) |
| Fields | `boolean startSelected`, `int startSelectedStartOffset`, `boolean endSelected`, `int endSelectedStartOffset` |
| Methods | Getters/setters, `equals()`, `hashCode()`, `canEqual()`, `toString()` |
| Purpose | Caches command selection state (start/end offsets and selection flags) for inline chat or code complete |

**Key logic**: Standard Lombok-generated `equals`/`hashCode` with prime multiplier 59. The `canEqual()` method confirms Lombok `@EqualsAndHashCode`.

### 1.2 GetTipsResult

| Aspect | Detail |
|--------|--------|
| Fields | `List<GetTipsResult$Tip> tips` |
| Annotations | `@NotNull` on constructor and getter |
| Purpose | Wraps server response for code completion tips |

### 1.3 GetTipsResult$Tip (inner)

| Aspect | Detail |
|--------|--------|
| Modifier | `final` |
| Fields | `String uuid`, `String text`, `Range range`, `String displayText`, `Position position` |
| All fields | `@NotNull` enforced |
| Purpose | Single completion suggestion with UUID, text, range, display text, and cursor position |

### 1.4 LineInfo

| Aspect | Detail |
|--------|--------|
| Modifier | `final` |
| Fields | `int lineCount`, `int lineNumber`, `int lineStartOffset`, `int columnOffset`, `String line`, `int nextLineIndent` |
| Key methods | `create(Document, int)` - factory from IntelliJ Document; `getLinePrefix()`, `getLineSuffix()`, `isBlankLine()`, `getWhitespaceBeforeCursor()`, `getLineEndOffset()` |
| Static helper | `calculateNextLineIndent(Document, int)` - scans forward for next non-blank line's indent |

**Key logic**: `create()` runs in a read action, catches `Throwable` and returns null on failure. `calculateNextLineIndent()` iterates forward through lines until finding a non-blank one, returning its whitespace prefix length or -1 if none found.

### 1.5 Position

| Aspect | Detail |
|--------|--------|
| Fields | `int line`, `int character` |
| Key methods | `of(int, int)` - static factory; `Position(LineInfo)` - from LineInfo; `toOffset(String)` - converts to char offset; `getCursorPosition(Editor)` - gets cursor position in read action |
| Purpose | LSP-style Position (line, character) |

**Key logic**: `getCursorPosition()` uses `ApplicationManager.runReadAction()` to safely access editor state. `toOffset()` delegates to `StringUtil.lineColToOffset()`.

### 1.6 Range

| Aspect | Detail |
|--------|--------|
| Fields | `Position start`, `Position end` |
| Key methods | `of(Position, Position)` - static factory |
| Purpose | LSP-style Range with start/end positions |

### 1.7 Suggestion

| Aspect | Detail |
|--------|--------|
| Modifier | `final` |
| Fields | `int score`, `String type`, `String hash`, `CodeInlayList inlays` |
| Purpose | Scored suggestion with type classification, content hash, and inlay presentation |

### 1.8 VirtualFileUri

| Aspect | Detail |
|--------|--------|
| Modifier | `final` |
| Fields | `String uri` (private final) |
| Static | `Logger LOG` |
| Key methods | `from(VirtualFile)` - creates URI from IntelliJ VFS; `from(VirtualFileSystem, String)` - from filesystem + path |
| Private helpers | `processPath(String)` - Windows path normalization; `isNeedsPathPrefix(VirtualFileSystem)` - Windows local FS detection; `asPrefixedUri(String)` - Windows UNC path handling |

**Key logic**: Complex Windows path handling. On Windows, `isNeedsPathPrefix()` returns true for `LocalFileSystem` (but not `TempFileSystem`), adding a `/` prefix. `processPath()` converts `//` to `\\` and `$` to `%24`. `asPrefixedUri()` handles `file:////` and `file://` prefix formats.

### 1.9 VirtualFileUri$TypeAdapter

| Aspect | Detail |
|--------|--------|
| Implements | `Gson JsonSerializer<VirtualFileUri>` |
| Method | `serialize()` - outputs `uri` field as `JsonPrimitive` string |
| Purpose | Gson serialization adapter for VirtualFileUri |

---

## Package 2: com.aicode.settings (6 classes)

### 2.1 AICodeSettingsState -- **PersistentStateComponent**

| Aspect | Detail |
|--------|--------|
| Implements | `PersistentStateComponent<AICodeSettingsState>` (self-referencing) |
| Scope | Application-level service |
| Total fields | **45+ public fields** |
| Persistence | `getState()` returns `this`; `loadState()` uses `XmlSerializerUtil.copyBean()` |

**Complete field inventory:**

| Category | Fields |
|----------|--------|
| Core | `autoTrigger` (true), `tipType` (INTELLIGENT_MODE), `sendKey` (ENTER_KEY), `modelCode` (""), `inlineChatModelCode` (""), `triggerTime` (200ms) |
| URLs | `loginUrl`, `feedbackUrl`, `maintainRepoUrl`, `codeSearchServerUrl`, `officialWebsiteUrl`, `codeKnowledgeWebUrl`, `userCenterWebUrl` |
| Identity | `enterpriseId`, `enterpriseName`, `userId`, `userName` |
| Update | `isUpdater` (false), `openAutoUpdate` (true) |
| Java Test | `testFramework` (JUNIT_FOUR), `mockFramework` (POWER_MOCK), `modifyTestFrame` (false), `modifyTestFramenNum` (0) |
| Python Test | `pyTestFramework` (UNITTEST), `pyMockFramework` (UNITTESTMOCK), `pyModifyTestFrame` (false), `pyModifyTestFramenNum` (0) |
| Models | `modelList` (HashMap), `modelInfoList`, `inlineCompletionInputStyle` (DISPOSABLE), `defaultLanguage` (auto), `languages` (ArrayList) |
| Completion | `codeCompleteDisableLang` (["txt","md"]), `enableCodeComplete` (false), `enableCodeDebug` (true), `enableCodeEnhance` (false), `openCodeEnhance` (true), `streamOutputConfig` (false) |
| Line Tools | `lineToolsType` (ICON), `lineToolsPermissionDocComments` (true), `lineToolsPermissionLineComments` (true), `lineToolsPermissionComments` (true), `lineToolsPermissionFunctionSplit` (true), `lineToolsPermissionCodeOptimization` (true), `lineToolsPermissionUnitTesting` (true) |
| Feature Flags | `openFunctionSplit` (true), `openCodeOptimization` (true), `openIFlyTest` (true), `openInlineChat` (true), `openIFlyDBA` (true), `openIFlyOps` (true), `openIFlyPm` (true) |
| APM | `apmEnable` (null/Boolean), `apmUrl` |
| Other | `permissions` (LinkedHashSet), `generateUnitTestFile`, `unitRequestInterval` (8), `showSaasQrCode` (false), `ignoreGitAuth` (false), `ignoreVersion` |

**Key methods:**
- `clear()` - resets user identity, URLs, model list, permissions; calls `PluginStartupActivity.setApiKey("")`
- `setUnitRequestInterval(int)` - **adaptive**: `newInterval = Math.min(5, input) + current) / 2` (moving average with floor of 5)
- `getUnitRequestInterval()` - enforces minimum of 5

### 2.2 AICodeRequestSettings -- **PersistentStateComponent**

| Aspect | Detail |
|--------|--------|
| Implements | `PersistentStateComponent<CodeGenerateRequestState>` |
| Scope | Application-level service |
| Key methods | `settings()` - static accessor; `getState()` / `loadState()` / `noStateLoaded()` (synchronized) |
| State class | `CodeGenerateRequestState` |

### 2.3 CodeGenerateRequestState

| Aspect | Detail |
|--------|--------|
| Fields | `Color inlayTextColor` (null), `boolean showIdeCodeTips` (false), `transient boolean internalDisableHttpCache` (false), `boolean requestLimitNotificationShown` (false) |
| Color | Serialized via `ColorConverter` |
| Purpose | UI/visual settings for code generation requests |

### 2.4 BatchUnitTestSettingsState -- **PersistentStateComponent**

| Aspect | Detail |
|--------|--------|
| Implements | `PersistentStateComponent<BatchUnitTestSettingsState>` (self-referencing) |
| Fields | `testFramework` (JUNIT_FOUR), `mockFramework` (POWER_MOCK), `testGenerationProcess` (GENERATION), `enabledGenerateByTemplate` (DISABLED), `testPrivate` (false), `duplicateRule` (COEXIST), `testModuleDirectory` (null), `savePath` (true), `batchTestUnitLimt` (FIVE) |
| Purpose | Batch unit test generation configuration |

### 2.5 UnitTestSettingsState -- **PersistentStateComponent**

| Aspect | Detail |
|--------|--------|
| Implements | `PersistentStateComponent<UnitTestSettingsState>` (self-referencing) |
| Fields | `testFramework` (JUNIT_FOUR), `mockFramework` (POWER_MOCK), `enabledGenerateByTemplate` (DISABLED), `testPrivate` (false), `testClasPath` (""), `savePath` (false) |
| Purpose | Single-file unit test generation configuration |

### 2.6 ColorConverter

| Aspect | Detail |
|--------|--------|
| Extends | `Converter<Color>` (IntelliJ XML serializer) |
| Methods | `fromString(String)` - hex to Color via `ColorUtil.fromHex()`; `toString(Color)` - Color to HTML hex via `ColorUtil.toHtmlColor()` |
| Error handling | `fromString()` catches Exception and returns null |

---

## Package 3: com.aicode.updater (8 classes)

### 3.1 PluginUpdater

| Aspect | Detail |
|--------|--------|
| Source file | `lb` (obfuscated) |
| Static fields | `Logger logger`, `AtomicReference<String> enum` (tracks last update version) |
| Key methods | `checkUpdate(Project, JsonObject)` - **synchronized**; `notification(Project, String)`; `doUpdate(Project, String, String, String, String)` |

**checkUpdate() flow:**
1. Skip if SaaS scene
2. Parse JsonObject for `updateInfo` -> `LoginInfo` DTO
3. Extract `current`, `update`, `file`, `md5`, `name` from LoginInfo
4. If any blank, return
5. If `current != update`, invoke `doUpdate()` on EDT

**doUpdate() flow:**
1. Check `AtomicReference` to avoid duplicate notifications for same version
2. Copy plugin JAR from source file to `PathManager.getPluginTempPath()` using `cn.hutool.core.io.FileUtil.copy()`
3. Call `disableOrEnablePlugin()` to disable current plugin
4. Find enabled plugin descriptor
5. Call `installAfterRestart()` with plugin descriptor, new path, old path
6. If `isOccurred()` (first install), update `InstalledPluginsState`

**CRITICAL SECURITY ISSUE: MD5 not verified!**
The `md5` field is extracted from `LoginInfo` but **never used** in `doUpdate()`. The downloaded plugin JAR is copied and installed without any integrity verification. This allows:
- Man-in-the-middle attacks to inject malicious plugin code
- Corrupted downloads to be installed
- Supply chain attacks if the update server is compromised

### 3.2 PluginUpdater$E (Restart Action)

| Aspect | Detail |
|--------|--------|
| Extends | `NotificationAction` |
| Action | On `actionPerformed()`, calls `Application.restart()` via `invokeLater()` |
| Purpose | "Restart" button in update notification |

### 3.3 PluginUpdater$m (Dismiss Action)

| Aspect | Detail |
|--------|--------|
| Extends | `NotificationAction` |
| Action | On `actionPerformed()`, calls `notification.hideBalloon()` |
| Purpose | "Dismiss" button in update notification |

### 3.4 PluginUpdaterCheckService

| Aspect | Detail |
|--------|--------|
| Source file | `hb` (obfuscated) |
| Static fields | `volatile boolean final` (checking flag), `volatile boolean try` (installing flag), `Object float` (lock), `String byte` (last version), `Logger enum` |
| Key methods | `scheduleRepeatedUpdateCheck(Project)` - schedules every **24 hours**; `queueUpdateCheck(Project)` - synchronized check; `O(ProgressIndicator)` - find updates; `N(Collection)` - filter AICode plugin; `t(Project, PluginDownloader, ProgressIndicator)` - install update |

**Update check flow:**
1. `scheduleRepeatedUpdateCheck()` uses `AppExecutorUtil` with 24-hour fixed delay
2. `queueUpdateCheck()` synchronized on lock object, creates `CheckUpdatesTask` if not already checking
3. `O()` dispatches to `UpdaterChecker2021_1` or `UpdaterCheckerFrom2021_2` based on IDE build baseline version (<=211 vs >211)
4. `N()` filters for AICode plugin by ID
5. `t()` uses **reflection** to call `updateAndInstall()` on `PluginDownloader` (accessing internal IntelliJ API)

### 3.5 PluginUpdaterCheckService$CheckUpdatesTask

| Aspect | Detail |
|--------|--------|
| Extends | `Task.Backgroundable` |
| Run logic | Calls `O()` to find updates, `N()` to filter, then `t()` to install if new version found |
| Error handling | Sets `final` flag to false on cancel/error |

### 3.6 PluginUpdaterCheckService$k

| Aspect | Detail |
|--------|--------|
| Extends | `Task.Backgroundable` |
| Fields | `Project byte`, `PluginDownloader enum` |
| Purpose | Background task for downloading and installing a specific plugin update |

### 3.7 UpdaterChecker2021_1

| Aspect | Detail |
|--------|--------|
| Purpose | Update checker for IntelliJ 2021.1 and earlier |
| Method | `findAvailableUpdates()` uses **reflection** to call `UpdateChecker.checkForUpdates()` then `getAvailableUpdates()` |
| Error handling | Catches `NoSuchMethodException`, `InvocationTargetException`, `IllegalAccessException`; returns empty list on failure |

### 3.8 UpdaterCheckerFrom2021_2

| Aspect | Detail |
|--------|--------|
| Purpose | Update checker for IntelliJ 2021.2+ |
| Method | `findAvailableUpdates()` uses **reflection** to call `UpdateChecker.checkForUpdatesAndLoadPluginDescriptors()` then `getAvailableUpdates()` with different method chain |
| Error handling | Same as UpdaterChecker2021_1 |

**Both updaters use reflection** because `UpdateChecker` is an internal IntelliJ API that changed between versions. The obfuscated string constants are method names decoded at runtime.

---

## Package 4: com.aicode.diff (6 classes)

### 4.1 CloudDiffUtil

| Aspect | Detail |
|--------|--------|
| Static fields | 5 `Key<String>` constants: `DIFF_FILENAME`, `DIFF_FILEPATH_LEFT`, `DIFF_FILEPATH_RIGHT`, `DIFF_FILE_UNIQUE_ID`, `DIFF_SUGGEST_CODE` |
| Purpose | UserData keys for passing diff information through IntelliJ's diff framework |
| Obfuscation | Key names are H()-deobfuscated at class initialization |

### 4.2 DiffDialog

| Aspect | Detail |
|--------|--------|
| Extends | `DialogWrapper` |
| Fields | `Project byte`, `SimpleDiffRequest enum` (obfuscated names) |
| Key methods | `createCenterPanel()` - creates diff panel; `doOKAction()` - applies right-side content to left file; `createActions()` - OK (Apply) and Cancel buttons |
| Apply logic | Reads left VirtualFile content, removes CR (char 13), writes to right file via `WriteCommandAction` |
| Tracking | Calls `EditorManagerServiceImpl.acceptCount()` with `CodeCollectEnum.COMPARE` |

### 4.3 DiffService

| Aspect | Detail |
|--------|--------|
| Static fields | `Key<VirtualFile> DIFF_FILEPATH_LEFT/RIGHT`, `String tempDirectoryName`, `Logger` |
| Key methods | `replaceTextInVirtualFile()` - write command to replace text range; `closeDiffViewIfAlreadyOpened()` - iterates editors to find and close existing diff; `openDiff()` - creates and shows diff dialog |
| Purpose | Core diff service managing file comparison and content replacement |

### 4.4 FileInfo

| Aspect | Detail |
|--------|--------|
| Fields | `boolean byte` (couldFile), `VirtualFile enum` (vf) |
| Static method | `H(Object)` - **H-function deobfuscation** (XOR-based string decoder using caller class+method as key) |
| Purpose | File info wrapper with cloud file flag |

**The `H()` method is a critical deobfuscation function** used throughout the codebase. It:
1. Gets caller class name + method name via `LinkageError().getStackTrace()[1]`
2. XORs input string characters with key derived from the caller name
3. Returns decoded string

### 4.5 FileService

| Aspect | Detail |
|--------|--------|
| Key methods | `replaceContentInFile(String, String, String, String, int, int)` - line-based file content replacement; `replaceContentInFile(String, String)` - full file replacement; `deleteFile(File)`; `addWriteSpace()` - indentation adjustment |
| Line ending | Handles `\r\n`, `\n`, `\r` line separators with H()-decoded patterns |
| Purpose | Low-level file I/O for diff operations |

### 4.6 GenericUtils

| Aspect | Detail |
|--------|--------|
| Static methods | `H(Object)` - H-function deobfuscation; `generateRandomInt(int, int)`; `isCloudFile(VirtualFile)`; `getVersionedFileName(String, String)`; `getClodFileMeta(String)` |
| Cloud file | `isCloudFile()` checks if path metadata has 6 segments (split by `O`-decoded separator) |
| Purpose | Utility class with deobfuscation and cloud file detection |

---

## Package 5: com.aicode.generate (5 classes)

### 5.1 SimpleCodeTipCache

| Aspect | Detail |
|--------|--------|
| Implements | `TipCache` |
| Fields | `String case` (latest prefix), `ReadWriteLock final`, `Logger try`, `boolean float` (enabled), `LinkedHashMap<Z, List<CodeTip>> byte` (cache map), `String enum` (current file) |
| Key methods | `isLatestPrefix(String)` - checks if prefix matches latest; `mF(String)` - SHA-256 hash of prefix via `DigestUtil.sha256Hex()` |
| Cache key | `SimpleCodeTipCache$Z` - contains `boolean byte` (isPartial) + `String enum` (hashed prefix) |
| Cache map | `SimpleCodeTipCache$Y` extends `LinkedHashMap` with LRU eviction (`removeEldestEntry`) |
| Thread safety | Uses `ReadWriteLock` for concurrent access |

**Cache strategy:**
- Key = (isPartial, SHA-256(prefix))
- LRU eviction when map size exceeds configured capacity
- SHA-256 hashing prevents cache key collision on long prefixes
- ReadWriteLock allows concurrent reads, exclusive writes

### 5.2 SimpleCodeTipCache$Z (Cache Key)

| Aspect | Detail |
|--------|--------|
| Modifier | `final` |
| Fields | `boolean byte` (isPartial), `String enum` (hashed prefix) |
| Custom `equals()` | Compares both isPartial flag and prefix string |

### 5.3 SimpleCodeTipCache$Y (Cache Map)

| Aspect | Detail |
|--------|--------|
| Extends | `LinkedHashMap<Z, List<CodeTip>>` |
| Field | `int byte` (max capacity) |
| `removeEldestEntry()` | Returns true when `size() > capacity` - implements LRU eviction |

### 5.4 DefaultInlayList

| Aspect | Detail |
|--------|--------|
| Implements | `CodeInlayList` |
| Fields | `List<CodeEditorInlay> final`, `TextRange try`, `CodeTip float`, `String byte` (replacementText), `boolean enum` (removeBlank) |
| Purpose | Default implementation of inlay presentation list for code tips |

### 5.5 CodeTipUtil

| Aspect | Detail |
|--------|--------|
| Static field | `Logger enum` |
| Purpose | Utility for code tip operations (heavily obfuscated) |

---

## Package 6: com.aicode.content (5 classes in util/ and util/file/)

### 6.1 EditorUtils (content/util)

| Aspect | Detail |
|--------|--------|
| Key method | `createEditor(Project, String, String)` - creates read-only editor with `LightVirtualFile`, disables highlighting |
| Purpose | Creates temporary editor instances for preview/display |

### 6.2 OverlayUtils (content/util)

| Aspect | Detail |
|--------|--------|
| Key methods | `showInfoBalloon(String, Point)` - shows info notification; `H(Object)` - H-function deobfuscation |
| Purpose | UI overlay utilities for balloon notifications |

### 6.3 FileUtils (content/util/file)

| Aspect | Detail |
|--------|--------|
| Key methods | `getEditorFile(Editor)` - gets VirtualFile from editor; `createFile(String, String, String)` - creates file with content; `tryCreateDirectory(String)` - mkdirs |
| Purpose | File I/O utilities for content operations |

### 6.4 FileExtensionLanguageDetails (content/util/file)

| Aspect | Detail |
|--------|--------|
| Fields | `String byte` (extension), `String enum` (language) |
| Static | `H(Object)` - H-function deobfuscation |
| Purpose | Maps file extensions to language identifiers |

### 6.5 LanguageFileExtensionDetails (content/util/file)

| Aspect | Detail |
|--------|--------|
| Fields | `String float` (type), `List<String> byte` (extensions), `String enum` (name) |
| Purpose | Maps language names to supported file extensions |

---

## Package 7: com.aicode.language (8 classes)

### 7.1 LanguageMap

| Aspect | Detail |
|--------|--------|
| Modifier | `final` |
| Static field | `Map<String, String> enum` (unmodifiable, backed by `ConcurrentHashMap`) |
| Key method | `getId(Language)` - maps IntelliJ Language ID to VS Code language ID |
| Purpose | IntelliJ-to-VSCode language identifier mapping |

### 7.2 LanguageMap$s

| Aspect | Detail |
|--------|--------|
| Extends | `ConcurrentHashMap<String, String>` |
| Purpose | Thread-safe language mapping storage |

### 7.3 AICodeExtendedLanguageSupport

| Aspect | Detail |
|--------|--------|
| Implements | `LanguageInfoSupport` (extension point) |
| Static field | `Map<W, String> enum` - single mapping entry (obfuscated keys) |
| Key method | `findVSCodeLanguageMapping(PsiFile)` - looks up language+extension in map |
| Purpose | Extended language support beyond built-in mappings |

### 7.4 AICodeExtendedLanguageSupport$W

| Aspect | Detail |
|--------|--------|
| Modifier | `final` |
| Fields | `String byte` (language ID), `String enum` (extension) |
| Purpose | Composite key for language+extension lookup |

### 7.5 AICodeLanguageInfo

| Aspect | Detail |
|--------|--------|
| Modifier | `final` |
| Fields | `String byte` (VS Code ID), `Language enum` (IntelliJ Language) |
| Purpose | Language info pairing IntelliJ Language with VS Code identifier |

### 7.6 CodeLanguageInfoSupport

| Aspect | Detail |
|--------|--------|
| Implements | `LanguageInfoSupport` |
| Purpose | Built-in language support implementation (heavily obfuscated) |

### 7.7 CommonLanguageSupport

| Aspect | Detail |
|--------|--------|
| Modifier | `final` |
| Static field | `Pattern enum` - compiled regex for line position validation |
| Key method | `isValidMiddleOfTheLinePosition(String)` - checks if cursor is at valid mid-line position |
| Purpose | Common language-agnostic position validation |

### 7.8 LanguageInfoManager

| Aspect | Detail |
|--------|--------|
| Modifier | `final` |
| Static field | `Key<AICodeLanguageInfo> enum` - PsiFile user data cache key |
| Key methods | `findLanguageMapping(PsiFile)` - finds language mapping with caching; `findFallback(VirtualFile)` - fallback when no mapping found |
| Caching | Results cached in PsiFile's UserData |
| Extension point | Uses `LanguageInfoSupport.EP` extension point |

---

## Package 8: com.aicode.apm (7 classes)

### 8.1 OpenTelemetryConfig

| Aspect | Detail |
|--------|--------|
| Static fields | `long final` (export timeout), `int try` (schedule delay), `Logger float`, `int byte` (max queue), `int enum` (max batch) |
| Key methods | `init(String)` - initializes OpenTelemetry SDK; `AE()` - builds Resource; `td(String)` - creates BatchSpanProcessor; `jE(String)` - creates OTLP HTTP exporter; `IF(TrustManager[])` - creates SSLContext; `xE()` - creates TrustManager array; `VD()` - creates RetryPolicy |

**init() flow:**
1. Build `SdkTracerProvider` with `traceIdRatioBased(1.0)` sampler (100% sampling)
2. Add `BatchSpanProcessor` with OTLP HTTP exporter
3. Set Resource with service name, system username, IDEA version, plugin version
4. Build `OpenTelemetrySdk` with W3C trace context + W3C baggage propagators
5. On exception, log and return null

**BatchSpanProcessor configuration:**
- Export timeout: 30000ms
- Schedule delay: 5000ms
- Max queue size: 2048
- Max export batch size: 512

**RetryPolicy:**
- Max attempts: 2
- Initial backoff: 1s
- Max backoff: 5s
- Backoff multiplier: 1.5

### 8.2 OpenTelemetryConfig$La -- **SECURITY: Trust-all X509TrustManager**

| Aspect | Detail |
|--------|--------|
| Implements | `X509TrustManager` |
| `checkClientTrusted()` | **EMPTY - accepts all certificates** |
| `checkServerTrusted()` | **EMPTY - accepts all certificates** |
| `getAcceptedIssuers()` | Returns empty `X509Certificate[]` |

**CRITICAL SECURITY ISSUE**: This TrustManager completely disables SSL certificate verification. Any HTTPS connection made through this SSL context will accept:
- Self-signed certificates
- Expired certificates
- Certificates for wrong hostnames
- Man-in-the-middle intercepted connections

This affects all OpenTelemetry APM trace exports, meaning an attacker could:
1. Intercept and read all APM telemetry data
2. Inject fake telemetry data
3. Perform MITM attacks on the APM endpoint

### 8.3 OpenTelemetryConfig$ca -- Custom ProxySelector

| Aspect | Detail |
|--------|--------|
| Extends | `ProxySelector` |
| `select(URI)` | Uses reflection to access IntelliJ's `ProxySettings` internal fields |
| Logic | If IntelliJ HTTP proxy is enabled and URI scheme is "https", creates `Proxy(HTTP, InetSocketAddress(host, port))` |
| Reflection | Accesses `isHttpProxyEnabled`, `httpProxyHost`, `httpProxyPort` fields via `setAccessible(true)` |

**Security concern**: Uses reflection with `setAccessible(true)` to bypass access controls on IntelliJ's internal proxy settings class.

### 8.4 OpenTelemetryService

| Aspect | Detail |
|--------|--------|
| Static field | `Logger enum` |
| Instance field | `Span parentSpan` |
| Key methods | `getInstance()` - application service; `handApmConfig(JsonObject)` - processes APM config from server; `kd(String)` - health check via OkHttp |

**handApmConfig() flow:**
1. Get current `apmUrl` and `apmEnable` from `AICodeSettingsState`
2. If JsonObject provided, extract new `apmUrl` and `apmEnable` from it
3. If URL is blank or doesn't start with "https", call `GlobalOpenTelemetry.resetForTest()` (disable APM)
4. If `apmEnable` is true and URL is valid, call `init()` to initialize OpenTelemetry
5. If `apmEnable` is false, reset APM

**kd() method**: Makes HTTP GET request to APM URL using OkHttp to verify connectivity. Returns true if response has non-blank message.

### 8.5 OpenTelemetryUtil

| Aspect | Detail |
|--------|--------|
| Key methods | `buildWithParent(Span, TracerEnum, String)` - creates child span; `buildWithCommand(String, String)` - creates CLIENT span; `buildWithTracer(TracerEnum, String)` - creates span from tracer; `eF()` - internal span builder; `H(Object)` - H-function deobfuscation |

**Span creation:**
- Uses `GlobalOpenTelemetry.getTracer()` with tracer name from `TracerEnum`
- Supports parent context propagation via `Context.current().with(parentSpan)`
- Default `SpanKind.INTERNAL` for parent spans, `SpanKind.CLIENT` for command/tracer spans

### 8.6 SpanAttrEnum (apm/enums)

| Aspect | Detail |
|--------|--------|
| Constants | `SYSTEM_USERNAME`, `IDEA_VERSION`, `PLUGIN_VERSION` (observed in Resource builder) |
| Purpose | Enum for OpenTelemetry span attribute keys |

### 8.7 TracerEnum (apm/enums)

| Aspect | Detail |
|--------|--------|
| Purpose | Enum for OpenTelemetry tracer names |
| Usage | Passed to `OpenTelemetryUtil.buildWithParent()` and `buildWithTracer()` |

---

## Analysis Section

### A. Configuration Persistence Mechanism

**4 PersistentStateComponent implementations:**

| Component | State Type | Scope | Storage |
|-----------|-----------|-------|---------|
| `AICodeSettingsState` | Self (AICodeSettingsState) | Application | XML via `XmlSerializerUtil.copyBean()` |
| `AICodeRequestSettings` | CodeGenerateRequestState | Application | XML (synchronized access) |
| `BatchUnitTestSettingsState` | Self | Application | XML via `XmlSerializerUtil.copyBean()` |
| `UnitTestSettingsState` | Self | Application | XML via `XmlSerializerUtil.copyBean()` |

**Persistence pattern:**
- All use `XmlSerializerUtil.copyBean()` in `loadState()` for XML deserialization
- `AICodeRequestSettings` additionally implements `noStateLoaded()` to create default state
- `AICodeRequestSettings.getState()` is `synchronized` for thread safety
- `AICodeSettingsState.getState()` returns `this` (self-persisting pattern)
- All accessed via `ApplicationManager.getApplication().getService()` (application-level services)

**AICodeSettingsState is the central configuration hub** with 45+ fields covering all plugin functionality.

### B. Auto-Update Mechanism

**Update flow:**
```
PluginUpdaterCheckService.scheduleRepeatedUpdateCheck()
  -> Every 24 hours via AppExecutorUtil
  -> queueUpdateCheck() [synchronized]
    -> CheckUpdatesTask (Backgroundable)
      -> O() findAvailableUpdates()
        -> UpdaterChecker2021_1 or UpdaterCheckerFrom2021_2 [reflection-based]
      -> N() filter for AICode plugin
      -> t() install update [reflection to call updateAndInstall()]
```

**Server-initiated update:**
```
PluginUpdater.checkUpdate(Project, JsonObject)
  -> Parse LoginInfo (current, update, file, md5, name)
  -> If current != update, doUpdate() on EDT
    -> Copy JAR to plugin temp path
    -> disableOrEnablePlugin() to disable current
    -> installAfterRestart() with new path
```

**Key observations:**
1. Two update paths: scheduled (every 24h) and server-push (via login response)
2. Both use IntelliJ's internal `PluginDownloader` API via reflection
3. IDE version branching: `UpdaterChecker2021_1` for <=2021.1, `UpdaterCheckerFrom2021_2` for >=2021.2
4. `AtomicReference<String>` prevents duplicate notifications
5. Update notification has "Restart" and "Dismiss" actions

### C. APM/OpenTelemetry Integration

**Architecture:**
```
OpenTelemetryService (singleton, application service)
  -> handApmConfig(JsonObject) - receives config from server
  -> OpenTelemetryConfig.init(apmUrl)
    -> SdkTracerProvider with 100% sampling
    -> BatchSpanProcessor (5s delay, 2048 queue, 512 batch)
    -> OtlpHttpSpanExporter (gzip, retry policy)
    -> SSLContext with Trust-all TrustManager [INSECURE]
    -> ProxySelector from IntelliJ proxy settings [reflection]
    -> Resource with service/IDE/plugin metadata
  -> OpenTelemetryUtil - span creation utilities
    -> buildWithParent(), buildWithCommand(), buildWithTracer()
    -> W3C Trace Context + W3C Baggage propagation
```

**Configuration flow:**
1. Server sends APM config (URL + enable flag) in login response
2. `OpenTelemetryService.handApmConfig()` processes config
3. If enabled and URL starts with "https", initializes SDK
4. If disabled or invalid URL, resets via `GlobalOpenTelemetry.resetForTest()`
5. Health check via `kd()` method using OkHttp GET

### D. Security Issues Summary

| # | Severity | Component | Issue | Impact |
|---|----------|-----------|-------|--------|
| 1 | **CRITICAL** | `OpenTelemetryConfig$La` | Trust-all X509TrustManager | All SSL certificate verification disabled for APM connections; enables MITM attacks |
| 2 | **HIGH** | `PluginUpdater.doUpdate()` | MD5 not verified on downloaded plugin | Downloaded plugin JAR installed without integrity check; enables supply chain attacks |
| 3 | **MEDIUM** | `OpenTelemetryConfig$ca` | Reflection bypass of access controls | `setAccessible(true)` on IntelliJ internal proxy fields; could break with IDE updates |
| 4 | **MEDIUM** | `UpdaterChecker2021_1/From2021_2` | Reflection to access internal UpdateChecker API | Fragile dependency on IntelliJ internals; different method chains per IDE version |
| 5 | **LOW** | `AICodeSettingsState.clear()` | Calls `PluginStartupActivity.setApiKey("")` | API key clearing coupled with settings reset; potential side effects |
| 6 | **LOW** | `OpenTelemetryConfig.init()` | 100% trace sampling (`traceIdRatioBased(1.0)`) | All requests traced; potential performance overhead and data volume |
| 7 | **INFO** | `SimpleCodeTipCache` | SHA-256 cache key hashing | Cache keys are SHA-256 hashes of prefixes; prevents collision but adds overhead |

### E. String Obfuscation Pattern

Multiple classes implement the `H(Object)` static method for string deobfuscation:

| Class | Pattern |
|-------|---------|
| `FileInfo.H()` | XOR-based with caller class+method name as key |
| `GenericUtils.H()` | Same XOR pattern |
| `OverlayUtils.H()` | Same XOR pattern |
| `FileExtensionLanguageDetails.H()` | Same XOR pattern |
| `LanguageFileExtensionDetails.H()` | Same XOR pattern |
| `FileService.H()` | Same XOR pattern |
| `OpenTelemetryUtil.H()` | Same XOR pattern |

**Common pattern:**
1. Get caller class name + method name via `new LinkageError().getStackTrace()[1]`
2. Concatenate to form XOR key
3. Iterate input string, XOR each character with corresponding key character
4. Return decoded string

This obfuscation is applied to all string constants in the updater, diff, content, language, and APM packages.

### F. Class Count Summary

| Package | Classes | Inner Classes | Total |
|---------|---------|---------------|-------|
| domain | 7 | 2 (Tip, TypeAdapter) | 9 |
| settings | 6 | 0 | 6 |
| updater | 4 | 4 (E, m, CheckUpdatesTask, k) | 8 |
| diff | 6 | 0 | 6 |
| generate | 3 | 2 (Y, Z) | 5 |
| content/util | 2 | 0 | 2 |
| content/util/file | 3 | 0 | 3 |
| language | 6 | 2 (W, s) | 8 |
| apm | 3 | 2 (La, ca) | 5 |
| apm/enums | 2 | 0 | 2 |
| **Total** | **44** | **14** | **58** |
