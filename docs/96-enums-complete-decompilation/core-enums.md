## 1. com.aicode.enums Package (24 classes)

### 1.1 AICodeStatus

**Source file:** `sm` (obfuscated)
**Implements:** `com.intellij.util.ui.PresentableEnum`

| Ordinal | Enum Value | Icon | Presentable Text Key |
|---------|-----------|------|---------------------|
| 0 | `Ready` | `Icons.StatusBarIcon` | Decoded from `FontKt.H()` |
| 1 | `NotSignedIn` | `Icons.StatusBarIconNotSignedIn` | Decoded from `Maps.H()` |
| 2 | `CompletionInProgress` | `Icons.StatusBarCompletionInProgress` | Decoded from `FontKt.H()` |
| 3 | `AgentBroken` | `Icons.StatusBarIconError` | Decoded from `Maps.H()` |
| 4 | `IncompatibleClient` | `Icons.StatusBarIconError` | Decoded from `FontKt.H()` |
| 5 | `Unsupported` | `Icons.StatusBarIconError` | Decoded from `Maps.H()` |
| 6 | `UnknownError` | `Icons.StatusBarIconError` | Decoded from `FontKt.H()` |

**Key Methods:**
- `getIcon()` - Returns `javax.swing.Icon` based on status (switch on ordinal 1-7)
- `getPresentableText()` - Returns i18n text via `MessageBundle.get()`
- `isIconAlwaysShown()` - Returns `true` for all except Ready/NotSignedIn/CompletionInProgress
- `isDisablingClientRequests()` - Returns `true` for IncompatibleClient and AgentBroken

**Inner class:** `AICodeStatus$p` - Switch table mapping ordinals to int codes (1=NotSignedIn, 2=Ready, 3=CompletionInProgress, 4=AgentBroken, 5=IncompatibleClient, 6=Unsupported, 7=UnknownError)

**References:** `com.aicode.icons.Icons`, `com.aicode.util.MessageBundle`, `com.aicode.ui.FontKt`, `com.aicode.util.Maps`

---

### 1.2 AssistantTypeEnum

**Source file:** `lh` (obfuscated)

| Ordinal | Enum Value | Type (field) |
|---------|-----------|-------------|
| 0 | `IFLY_MATE` | Decoded from `LanguageFileExtensionDetails.H()` |
| 1 | `IFLY_DEV` | Decoded from `LanguageFileExtensionDetails.H()` |
| 2 | `IFLY_TEST` | Decoded from `LanguageFileExtensionDetails.H()` |
| 3 | `IFLY_OPS` | Decoded from `LanguageFileExtensionDetails.H()` |
| 4 | `IFLY_PM` | Decoded from `LanguageFileExtensionDetails.H()` |
| 5 | `IFLY_DBA` | Decoded from `LanguageFileExtensionDetails.H()` |

**Fields:**
- `private String enum` (obfuscated name for `type`)

**Key Methods:**
- `getType()` - Returns the type string

**References:** `com.aicode.exception.RequestTimeoutException.H()`, `com.aicode.content.util.file.LanguageFileExtensionDetails.H()`

---

### 1.3 BatchTestUnitLimt

**Source file:** `gg` (obfuscated)
**Note:** Class name has a typo ("Limt" instead of "Limit")

| Ordinal | Enum Value | Limit |
|---------|-----------|-------|
| 0 | `FIVE` | 5 |
| 1 | `TEN` | 10 |
| 2 | `TWENTY` | 20 |
| 3 | `FIFTY` | 50 |

**Fields:**
- `private Integer byte` (obfuscated name for `limit`)

**Key Methods:**
- `getLimit()` - Returns the Integer limit value
- `loadLimt(Integer)` - Finds enum by limit, defaults to FIVE

---

### 1.4 ChatOperationEnum

**Source file:** `km` (obfuscated)

| Ordinal | Enum Value | Name (field) | Desc (field) |
|---------|-----------|-------------|-------------|
| 0 | `ACTION_NEW` | Decoded | Decoded |
| 1 | `ACTION_DIFF` | Decoded | Decoded |
| 2 | `ACTION_INSERT` | Decoded | Decoded |
| 3 | `ACTION_COPY` | Decoded | Decoded |
| 4 | `ACTION_ACCEPT` | Decoded | Decoded |
| 5 | `ACTION_ACCEPT_INLINE_COMMENT` | Decoded | Decoded |

**Key Methods:**
- `getByName(String)` - Lookup by name(), returns null if blank

---

### 1.5 ClientTypeEnum

**Source file:** `of` (obfuscated)

| Ordinal | Enum Value | Description | JetBrains Platform | Unix Exe | Windows Exe |
|---------|-----------|-------------|-------------------|----------|-------------|
| 0 | `IE` | Decoded | Decoded | Decoded | Decoded |
| 1 | `IC` | Decoded | Decoded | Decoded | Decoded |
| 2 | `IU` | Decoded | Decoded | Decoded | Decoded |
| 3 | `WS` | Decoded | Decoded | Decoded | Decoded |
| 4 | `PY` | Decoded | Decoded | Decoded | Decoded |
| 5 | `PC` | Decoded | Decoded | Decoded | Decoded |
| 6 | `CL` | Decoded | Decoded | Decoded | Decoded |
| 7 | `GO` | Decoded | Decoded | Decoded | Decoded |
| 8 | `AI` | Decoded | Decoded | Decoded | Decoded |

**Fields:**
- `private final String final` (obfuscated: unixExeFile)
- `private final String try` (obfuscated: description)
- `private final String byte` (obfuscated: jetBrainPlatform)
- `private final String float` (obfuscated: windowsExeFile)

**Key Methods:**
- `getDescription()` - Returns description string
- `getJetBrainPlatform()` - Returns JetBrains platform identifier
- `getUnixExeFile()` - Returns Unix executable filename
- `getWindowsExeFile()` - Returns Windows executable filename
- `getExeFileName(String)` - Finds ClientTypeEnum by name prefix, defaults to IC

**Platform Mapping (inferred):**
| Code | Platform |
|------|----------|
| IE | IntelliJ IDEA |
| IC | IntelliJ IDEA Community |
| IU | IntelliJ IDEA Ultimate |
| WS | WebStorm |
| PY | PyCharm |
| PC | PyCharm Community |
| CL | CLion |
| GO | GoLand |
| AI | AI (DataGrip/Rider) |

---

### 1.6 CodeCollectEnum

**Source file:** `ph` (obfuscated)

| Ordinal | Enum Value | Type | Name |
|---------|-----------|------|------|
| 0 | `GENERATE` | Decoded | Decoded |
| 1 | `INSERT` | Decoded | Decoded |
| 2 | `COPY` | Decoded | Decoded |
| 3 | `NEW` | Decoded | Decoded |
| 4 | `UNITTEST` | Decoded | Decoded |
| 5 | `COMPARE` | Decoded | Decoded |
| 6 | `OTHER` | Decoded | Decoded |

**Fields:**
- `private String byte` (obfuscated: name)
- `private String enum` (obfuscated: type)

**Key Methods:**
- `getType()` - Returns type string
- `getName()` - Returns name string

---

### 1.7 CodeTipRequestType

**Source file:** `lf` (obfuscated)

| Ordinal | Enum Value | Description |
|---------|-----------|-------------|
| 0 | `Automatic` | Auto-triggered completion |
| 1 | `Interact` | User interaction |
| 2 | `Forced` | Force-triggered completion |
| 3 | `Manual` | Manually triggered |
| 4 | `InlineChat` | Inline Chat triggered |

**Key Methods:**
- `isForcedOrManual()` - Returns true if Forced or Manual
- `isInlineChat()` - Returns true if InlineChat
- `isAutomaticOrForced()` - Returns true if Automatic or Forced
- `isUnforced()` - Returns true if Automatic
- `isForced()` - Returns true if Forced

---

### 1.8 CodeTipType

**Source file:** `ze` (obfuscated)

| Ordinal | Enum Value | Description |
|---------|-----------|-------------|
| 0 | `Inline` | Single-line inline completion |
| 1 | `AfterLineEnd` | Completion after line end |
| 2 | `Block` | Multi-line block completion |

**Key Methods:** Standard enum methods only

---

### 1.9 DuplicateFileNameSwitchEnum

**Source file:** `mf` (obfuscated)

| Ordinal | Enum Value | Type |
|---------|-----------|------|
| 0 | `ENABLED` | Decoded |
| 1 | `DISABLED` | Decoded |

**Fields:**
- `private final String byte` (obfuscated: type)

---

### 1.10 DuplicateRule

**Source file:** `zm` (obfuscated)

| Ordinal | Enum Value | Name |
|---------|-----------|------|
| 0 | `SKIP` | Decoded |
| 1 | `OVERWRITE` | Decoded |
| 2 | `COEXIST` | Decoded |

**Fields:**
- `private String byte` (obfuscated: name)

**Key Methods:**
- `getName()` - Returns name string

---

### 1.11 ElementTypeEnum

**Source file:** `hf` (obfuscated)

| Ordinal | Enum Value | Type |
|---------|-----------|------|
| 0 | `METHOD` | Decoded |
| 1 | `CLASS` | Decoded |

**Fields:**
- `private String byte` (obfuscated: type)

**Key Methods:**
- `getType()` - Returns type string
- `getByType(String)` - Returns `Optional&lt;ElementTypeEnum&gt;` by type, uses `Arrays.stream().filter().findFirst()`

---

### 1.12 FileExtensionEnum

**Source file:** `un` (obfuscated)

| Ordinal | Enum Value | Description | JetBrains Platform | Suffix | Extra |
|---------|-----------|-------------|-------------------|--------|-------|
| 0 | `C_LANGUAGE_01` | Decoded | Decoded | Decoded | "" |
| 1 | `CPP_LANGUAGE_01` | Decoded | Decoded | Decoded | "" |
| 2 | `CSHARP` | Decoded | Decoded | Decoded | "" |
| 3 | `PYTHON_LANGUAGE_01` | Decoded | Decoded | Decoded | Decoded |
| 4 | `JAVA` | Decoded | Decoded | Decoded | "" |
| 5 | `KOTLIN` | Decoded | Decoded | "" | "" |
| 6 | `RUST` | Decoded | Decoded | "" | "" |
| 7 | `SWIFT` | Decoded | Decoded | "" | "" |
| 8 | `OBJECTIVE_C` | Decoded | Decoded | "" | "" |
| 9 | `GO` | Decoded | Decoded | Decoded | "" |
| 10 | `JS` | Decoded | Decoded | Decoded | Decoded |
| 11 | `TS` | Decoded | Decoded | Decoded | "" |
| 12 | `VUE` | Decoded | Decoded | "" | "" |

**Fields:**
- `private final String try` (obfuscated: jetBrainPlatform)
- `private final String byte` (obfuscated: description)
- `private final String float` (obfuscated: suffix)

**Key Methods:**
- `getDescription()` - Returns language description
- `getJetBrainPlatform()` - Returns JetBrains platform ID
- `getSuffix()` - Returns file suffix/extension
- `getLanguage(String)` - Maps description to suffix
- `getFileLanguage(String)` - Maps suffix to description

---

### 1.13 GenaratebyTemplateSwitchEnum

**Source file:** `pg` (obfuscated)
**Note:** Class name has a typo ("Genarateby" instead of "GenerateBy")

| Ordinal | Enum Value | Type |
|---------|-----------|------|
| 0 | `ENABLED` | Decoded |
| 1 | `DISABLED` | Decoded |

**Fields:**
- `private final String enum` (obfuscated: type)

---

### 1.14 GitRepoStatusEnum

**Source file:** `bn` (obfuscated)

| Ordinal | Enum Value | Code | NeedAuthorize | NeedSkipWeb | Message |
|---------|-----------|------|--------------|-------------|---------|
| 0 | `AUTHORIZED_EXPIRED` | -1 | true | true | i18n message |
| 1 | `SSH_PROTOCOL` | -2 | true | true | i18n message |
| 2 | `TOKEN_INVALID` | -3 | true | false | i18n message |
| 3 | `UNAUTHORIZED` | -4 | true | true | Concatenated i18n messages |

**Fields:**
- `private int final` (obfuscated: code)
- `private boolean try` (obfuscated: needAuthorize)
- `private boolean enum` (obfuscated: needSkipWeb)
- `private String byte` (obfuscated: message)

**Key Methods:**
- `getCode()` - Returns int code
- `isNeedSkipWeb()` - Returns boolean
- `isNeedAuthorize()` - Returns boolean
- `getMessage()` - Returns message string
- `getGitRepoStatusEnum(int)` - Lookup by code, returns null if not found

---

### 1.15 LanguageEnum

**Source file:** `id` (obfuscated)

| Ordinal | Enum Value | Suffix | JetBrains Platform | Description |
|---------|-----------|--------|-------------------|-------------|
| 0 | `TS` | Decoded | Decoded | Decoded |
| 1 | `PYTHON_LANGUAGE_11` | Decoded | Decoded | Decoded |
| 2 | `C_LANGUAGE_02` | Decoded | Decoded | Decoded |
| 3 | `PYTHON_LANGUAGE_07` | Decoded | Decoded | Decoded |
| 4 | `PYTHON_LANGUAGE_04` | Decoded | Decoded | Decoded |
| 5 | `CPP_LANGUAGE_07` | Decoded | Decoded | Decoded |
| 6 | `CPP_LANGUAGE_08` | Decoded | Decoded | Decoded |
| 7 | `PYTHON_LANGUAGE_08` | Decoded | Decoded | Decoded |
| 8 | `PYTHON_LANGUAGE_13` | Decoded | Decoded | Decoded |
| 9 | `JS` | Decoded | Decoded | Decoded |
| 10 | `CPP_LANGUAGE_13` | Decoded | Decoded | Decoded |
| 11 | `PYTHON_LANGUAGE_09` | Decoded | Decoded | Decoded |
| 12 | `CPP_LANGUAGE_06` | Decoded | Decoded | Decoded |
| 13 | `C_LANGUAGE_01` | Decoded | Decoded | Decoded |
| 14 | `VUE` | Decoded | Decoded | Decoded |
| 15 | `CPP_LANGUAGE_01` | Decoded | Decoded | Decoded |
| 16 | `CPP_LANGUAGE_03` | Decoded | Decoded | Decoded |
| 17 | `JAVA` | Decoded | Decoded | Decoded |
| 18 | `CPP_LANGUAGE_09` | Decoded | Decoded | Decoded |
| 19 | `CPP_LANGUAGE_11` | Decoded | Decoded | Decoded |
| 20 | `CPP_LANGUAGE_10` | Decoded | Decoded | Decoded |
| 21 | `PYTHON_LANGUAGE_02` | Decoded | Decoded | Decoded |
| 22 | `PYTHON_LANGUAGE_10` | Decoded | Decoded | Decoded |
| 23 | `PYTHON_LANGUAGE_05` | Decoded | Decoded | Decoded |
| 24 | `PYTHON_LANGUAGE_06` | Decoded | Decoded | Decoded |
| 25 | `CPP_LANGUAGE_126` | Decoded | Decoded | Decoded |
| 26 | `PYTHON_LANGUAGE_03` | Decoded | Decoded | Decoded |
| 27 | `CPP_LANGUAGE_02` | Decoded | Decoded | Decoded |
| 28 | `PYTHON_LANGUAGE_12` | Decoded | Decoded | Decoded |
| 29 | `PYTHON_LANGUAGE_01` | Decoded | Decoded | Decoded |
| 30 | `CPP_LANGUAGE_04` | Decoded | Decoded | Decoded |
| 31 | `CPP_LANGUAGE_05` | Decoded | Decoded | Decoded |

**Fields:**
- `private final String try` (obfuscated: jetBrainPlatform)
- `private final String float` (obfuscated: suffix)
- `private final String enum` (obfuscated: description)

**Key Methods:**
- `getJetBrainPlatform()` - Returns JetBrains platform ID
- `getDescription()` - Returns language description
- `getSuffix()` - Returns file suffix
- `getLanguage(String)` - Maps description to suffix
- `isVaildLanguage(String)` - Validates language string (note: "Vaild" typo in original)

**Note:** The `PYTHON_LANGUAGE_XX` and `CPP_LANGUAGE_XX` naming with numeric suffixes suggests these map to internal language IDs. The `CPP_LANGUAGE_126` value is unusual and may represent a specific C++ dialect or standard.

---

### 1.16 LineToolsTypeEnum

**Source file:** `jk` (obfuscated)

| Ordinal | Enum Value | Code |
|---------|-----------|------|
| 0 | `LINE` | Decoded |
| 1 | `ICON` | Decoded |

**Fields:**
- `private String byte` (obfuscated: code)

**Key Methods:**
- `getCode()` - Returns code string

---

### 1.17 OperateActionEnum

**Source file:** `ci` (obfuscated)

| Ordinal | Enum Value | Description |
|---------|-----------|-------------|
| 0 | `UserOperate` | User-initiated operation |
| 1 | `IdeCompletion` | IDE auto-completion |
| 2 | `CaretChange` | Cursor position change |
| 3 | `SettingsChange` | Settings modification |
| 4 | `Cycling` | Cycling through suggestions |
| 5 | `TypingAsSuggested` | User typed as suggested |
| 6 | `Typing` | User typing (different from suggested) |
| 7 | `EscReject` | ESC key rejection |
| 8 | `Applied` | Suggestion applied |

**Key Methods:**
- `isUserAction()` - Returns true only for UserOperate
- `isResetLastRequest()` - Returns true for SettingsChange or Applied

---

### 1.18 PluginSceneEnum

**Source file:** `ei` (obfuscated)

| Ordinal | Enum Value | Scene | Description |
|---------|-----------|-------|-------------|
| 0 | `PLUGIN_SAAS` | Decoded | Decoded |
| 1 | `PLUGIN_PRIVATE` | Decoded | Decoded |
| 2 | `PLUGIN_INNER` | Decoded | Decoded |

**Fields:**
- `private final String float` (obfuscated: scene)
- `private final String enum` (obfuscated: description)

**Key Methods:**
- `getScene()` - Returns scene identifier
- `getDescription()` - Returns description
- `saasScene()` - Static method, checks if current scene equals SaaS scene via i18n message

---

### 1.19 PyUnitTestBaseEnum

**Source file:** `jn` (obfuscated)

| Ordinal | Enum Value | Name | Dependency |
|---------|-----------|------|------------|
| 0 | `AUTO` | Decoded | "" (empty) |
| 1 | `PYTEST` | Decoded | Decoded |
| 2 | `UNITTEST` | Decoded | Decoded |

**Fields:**
- `private final String byte` (obfuscated: dependency)
- `private final String enum` (obfuscated: name)

**Key Methods:**
- `getDependency()` - Returns dependency string
- `getName()` - Returns name string
- `findByName(String)` - Lookup by name (case-insensitive), defaults to PYTEST

---

### 1.20 PyUnitTestMockEnum

**Source file:** `se` (obfuscated)

| Ordinal | Enum Value | Name | Dependency |
|---------|-----------|------|------------|
| 0 | `AUTO` | Decoded | Decoded |
| 1 | `OFF` | Decoded | Decoded |
| 2 | `UNITTESTMOCK` | Decoded | Decoded |
| 3 | `PYTESTMOCK` | Decoded | Decoded |

**Fields:**
- `private final String byte` (obfuscated: dependency)
- `private final String enum` (obfuscated: name)

**Key Methods:**
- `getDependency()` - Returns dependency string
- `getName()` - Returns name string
- `findByName(String)` - Lookup by name (case-insensitive), defaults to UNITTESTMOCK
- `findByDependency()` - Static, always returns OFF

---

### 1.21 RepoStatusEnum

**Source file:** `yi` (obfuscated)

| Ordinal | Enum Value | Code | Description |
|---------|-----------|------|-------------|
| 0 | `UNAUTHORIZED` | -4 | Decoded |
| 1 | `MISSING_TOKEN` | -3 | Decoded |
| 2 | `UNSUPPORTED_PROTOCOL` | -2 | Decoded |
| 3 | `UNINITIALIZED` | -1 | Decoded |
| 4 | `PENDING` | 0 | Decoded |
| 5 | `EXPIRED` | 6 | Decoded |
| 6 | `AUTHORIZED_1` | 1 | Decoded |
| 7 | `AUTHORIZED_2` | 2 | Decoded |
| 8 | `AUTHORIZED_3` | 3 | Decoded |
| 9 | `AUTHORIZED_4` | 4 | Decoded |
| 10 | `AUTHORIZED_5` | 5 | Decoded |

**Fields:**
- `private final String float` (obfuscated: description)
- `private final int enum` (obfuscated: code)

**Key Methods:**
- `getCode()` - Returns int code
- `getDescription()` - Returns description string
- `toString()` - Returns `code + description`

**Note:** The 5 AUTHORIZED levels (1-5) likely represent different authorization tiers or subscription levels.

---

### 1.22 RestartEnum

**Source file:** `ii` (obfuscated)

| Ordinal | Enum Value | Code | Text | Desc |
|---------|-----------|------|------|------|
| 0 | `START_AGENT` | 0 | Decoded | Decoded |
| 1 | `CONNECT_REFUSED` | 1 | Decoded | Decoded |
| 2 | `CONNECT_FAILED` | 2 | Decoded | Decoded |
| 3 | `CONNECT_ERROR` | 3 | Decoded | Decoded |
| 4 | `CLOSE_EXCEPTION` | 4 | Decoded | Decoded |
| 5 | `CLOSE_ERROR` | 5 | Decoded | Decoded |
| 6 | `BLANK_PORT` | 6 | Decoded | Decoded |
| 7 | `REFRESH` | 7 | Decoded | Decoded |
| 8 | `HEART_BEAT_ERROR` | 8 | Decoded | Decoded |
| 9 | `CLOSE_RECONNECT` | 9 | Decoded | Decoded |
| 10 | `REFRESH_RECONNECT` | 10 | Decoded | Decoded |

**Fields:**
- `private final String try` (obfuscated: text)
- `private final int float` (obfuscated: code)
- `private final String byte` (obfuscated: desc)

**Key Methods:**
- `getCode()` - Returns int code
- `getText()` - Returns text string
- `getDesc()` - Returns description string

---

### 1.23 SendKeyEnum

**Source file:** `ug` (obfuscated)

| Ordinal | Enum Value | Text |
|---------|-----------|------|
| 0 | `ENTER_KEY` | Decoded |
| 1 | `ENTER_SHIFT_KEY` | Decoded |

**Fields:**
- `private String enum` (obfuscated: text)

**Key Methods:**
- `getText()` - Returns text string
- `getByText(String)` - Lookup by text, defaults to ENTER_KEY

---

### 1.24 TestGenerationProcess

**Source file:** `bm` (obfuscated)

| Ordinal | Enum Value | Name | Description |
|---------|-----------|------|-------------|
| 0 | `GENERATION` | Decoded | "" (empty) |
| 1 | `GENERATION_BUILD` | Decoded | Decoded |
| 2 | `GENERATION_BUILD_EXECUTE` | Decoded | Decoded |

**Fields:**
- `private String float` (obfuscated: description)
- `private String byte` (obfuscated: name)

**Key Methods:**
- `getName()` - Returns name string
- `getDescription()` - Returns description (prefixed with "- " if non-empty)
- `loadByName(String)` - Lookup by name, defaults to GENERATION

---

### 1.25 TipType

**Source file:** `eh` (obfuscated)

| Ordinal | Enum Value | Description |
|---------|-----------|-------------|
| 0 | `GhostText` | Ghost text completion |
| 1 | `OpenAICode` | OpenAI-style code completion |

---

### 1.26 TipTypeEnum

**Source file:** `pn` (obfuscated)

| Ordinal | Enum Value | Display Name |
|---------|-----------|-------------|
| 0 | `SINGLE_LINE` | Decoded (i18n) |
| 1 | `INTELLIGENT_MODE` | Decoded (i18n) |

**Fields:**
- `private String enum` (obfuscated: displayName)

**Key Methods:**
- `getByName(String)` - Lookup by name(), defaults to INTELLIGENT_MODE

---

### 1.27 UnitTestBaseEnum

**Source file:** `de` (obfuscated)

| Ordinal | Enum Value | Name | Dependency |
|---------|-----------|------|------------|
| 0 | `AUTO` | Decoded | "" (empty) |
| 1 | `JUNIT_FOUR` | Decoded | Decoded |
| 2 | `JUNIT_FIVE` | Decoded | Decoded |
| 3 | `SPRINGBOOTTEST` | Decoded | Decoded |

**Fields:**
- `private final String byte` (obfuscated: dependency)
- `private final String enum` (obfuscated: name)

**Key Methods:**
- `getDependency()` - Returns dependency string
- `getName()` - Returns name string
- `findByName(String)` - Lookup by name (case-insensitive), defaults to JUNIT_FOUR

---

### 1.28 UnitTestMockEnum

**Source file:** `wi` (obfuscated)

| Ordinal | Enum Value | Name | Dependency |
|---------|-----------|------|------------|
| 0 | `AUTO` | Decoded | Decoded |
| 1 | `OFF` | Decoded | Decoded |
| 2 | `POWER_MOCK` | Decoded | Decoded |
| 3 | `MOCKITO` | Decoded | Decoded |

**Fields:**
- `private final String float` (obfuscated: name)
- `private final String byte` (obfuscated: dependency)

**Key Methods:**
- `getName()` - Returns name string
- `getDependency()` - Returns dependency string
- `findByName(String)` - Lookup by name (case-insensitive), defaults to OFF
- `findByDependency()` - Static, always returns OFF

---

### 1.29 WebViewDataTypeEnum

**Source file:** `lk` (obfuscated)
**Size:** 20KB - the largest enum in the `com.aicode.enums` package

This enum defines all data types exchanged between the IDE plugin and the WebView frontend. Values are organized by functional area:

**CHAT Operations:**
| Ordinal | Enum Value |
|---------|-----------|
| - | `CHAT_SEND_MSG` |
| - | `CHAT_RESEND` |
| - | `CHAT_STOP_RESPONSE` |
| - | `CHAT_GET_HISTORY_LIST` |
| - | `CHAT_RECEIVER_HISTORY_LIST` |
| - | `CHAT_CHOOSE_HISTORY_ITEM` |
| - | `CHAT_DELETE_HISTORY_ITEM` |
| - | `CHAT_DELETE_HISTORY_ITEM_ALL` |
| - | `CHAT_DELETE_MSG` |
| - | `CHAT_GET_CONVERSATION` |
| - | `CHAT_UPDATE_CONVERSATION_LIST` |
| - | `CHAT_GET_USER_INFO` |
| - | `CHAT_GET_IDE_FILE_STATE` |
| - | `CHAT_RECEIVER_IDE_FILE_STATE` |
| - | `CHAT_GET_MODEL_LIST` |
| - | `CHAT_SET_MODEL` |
| - | `CHAT_REFRESH_MODEL` |
| - | `CHAT_PREDICT` |
| - | `CHAT_CHOOSE_FILE` |
| - | `CHAT_SEND_OPEN_DIR_LIST` |
| - | `CHAT_GET_OPEN_DIR_LIST` |
| - | `CHAT_VALID_WEBSITE` |
| - | `CHAT_SEND_VALID_WEBSITE_RESULT` |
| - | `CHAT_GET_FEEDBACK_LIST` |
| - | `CHAT_FEEDBACK_CATEGORY` |
| - | `CHAT_AGENT_REFRESH` |
| - | `CHAT_RECOMMEND_GAMEPLAY` |
| - | `CHAT_RECEIVER_RECOMMEND_GAMEPLAY` |
| - | `CHAT_GET_DOC_KNOWLEDGE_LIST` |
| - | `CHAT_RECEIVER_DOC_KNOWLEDGE_LIST` |
| - | `CHAT_GET_CODE_KNOWLEDGE_LIST` |
| - | `CHAT_RECEIVER_CODE_KNOWLEDGE_LIST` |
| - | `CHAT_NEW_CHAT` |
| - | `TALK_KNOWLEDGE` |

**SQL Chat Operations:**
| Ordinal | Enum Value |
|---------|-----------|
| - | `SQL_CHAT_SEND_MSG` |
| - | `SQL_CHAT_TABLE_LIST` |
| - | `SQL_CHAT_SOURCE_LIST` |
| - | `SQL_CHAT_SQL_SAVE` |
| - | `SQL_CHAT_UPDATE_CONVERSATION_LIST` |
| - | `SQL_CHAT_SOURCE_DELETE` |
| - | `SQL_CHAT_SOURCE_EDIT` |
| - | `SQL_CHAT_REQUEST_SOURCE_TYPES` |
| - | `SQL_CHAT_SQL_LINK_TEST` |
| - | `SQL_CHAT_STOP_RESPONSE` |
| - | `SQL_CHAT_GET_MODEL_LIST` |
| - | `SQL_CHAT_NEW_CHAT` |

**Code Check/Review Operations:**
| Ordinal | Enum Value |
|---------|-----------|
| - | `CODE_CHECK_REQUEST_CODE_CHECK_LIST` |
| - | `CODE_CHECK_UPDATE_CODE_CHECK` |
| - | `CODE_CHECK_GET_CODE_CHECK_LIST` |
| - | `CODE_CHECK_FIX` |
| - | `CODE_REVIEW_PAGE_READY` |
| - | `CODE_REVIEW_GET_CODEREVIEW_LIST` |
| - | `CODE_REVIEW_GET_CHANGE_RESULT` |
| - | `CODE_REVIEW_GET_CHANGE_RESULT_END` |

**Unit Test Operations:**
| Ordinal | Enum Value |
|---------|-----------|
| - | `UNIT_TEST_FUNCTION_LIST` |
| - | `UNIT_TEST_REQUEST_UT_INFO` |
| - | `UNIT_TEST_GET_UT_INFO` |
| - | `UNIT_TEST_REQUEST_ALL_CODE_FILE` |
| - | `UNIT_TEST_GET_ALL_CODE_FILE` |
| - | `UNIT_TEST_REQUEST_METHOD_CASE` |
| - | `UNIT_TEST_GET_METHOD_CASE` |
| - | `UNIT_TEST_REQUEST_CASE_CODE` |
| - | `UNIT_TEST_GET_CASE_CODE` |
| - | `UNIT_TEST_COPY_CASE_CODE` |
| - | `UNIT_TEST_SAVE_CODE` |
| - | `UNIT_TEST_REGENERATE` |
| - | `UNIT_TEST_PAGE_READY` |
| - | `UNIT_TESTING_RECEIVE_FUNCTION` |
| - | `UNIT_TESTING_RECEIVE_DATA` |
| - | `UNIT_TESTING_MAPPING_FILE` |
| - | `UNIT_TESTING_SAVE` |
| - | `UNIT_TESTING_RESPONSE_SAVE` |
| - | `UNIT_TESTING_WEB_STOP` |
| - | `UNIT_TESTING_IDEA_STOP` |
| - | `UNIT_TESTING_PAGE_READY` |
| - | `UNIT_TEST_RECEIVE_FUNCTION_CASE` |
| - | `UNIT_TEST_RECEIVE_FUNCTION_CASE_CODE` |
| - | `UNIT_TEST_FUNCTION_CASE` |
| - | `UNIT_TEST_FUNCTION_CASE_CODE` |

**Batch Unit Test Operations:**
| Ordinal | Enum Value |
|---------|-----------|
| - | `BATCH_UNIT_TEST_GET_LIST` |
| - | `BATCH_UNIT_TEST_GET_TASK_LIST` |
| - | `BATCH_UNIT_TEST_CREATE` |
| - | `BATCH_UNIT_TEST_DELETE` |
| - | `BATCH_UNIT_TEST_DOWNLOAD` |
| - | `BATCH_UNIT_TEST_MESSAGE` |
| - | `BATCH_UNIT_TEST_CANCEL` |
| - | `BATCH_UNIT_TEST_REFRESH_TASK_DOWNLOAD_STATUS` |

**Git Operations:**
| Ordinal | Enum Value |
|---------|-----------|
| - | `GIT_GET_STATUS` |
| - | `GIT_SAVE_TOKEN` |
| - | `GIT_RE_INDEX` |
| - | `GIT_CODE_KNOWLEDGE_REPO_STATUS` |
| - | `GIT_AUTHORIZE` |

**Login Operations:**
| Ordinal | Enum Value |
|---------|-----------|
| - | `LOGIN_GO_LOGIN` |
| - | `LOGIN_LOGIN_SUCCEED` |
| - | `LOGIN_SHOW_FRESH` |
| - | `LOGIN_INIT` |
| - | `LOGIN_LOGIN_CHECK` |
| - | `LOGIN_LOGIN_ABORT` |
| - | `LOGIN_LOGOUT` |
| - | `LOGIN_CLOSE_QR_CODE` |
| - | `LOGIN_RECEIVER_LOGIN_IFRAME_SRC` |

**Common Operations:**
| Ordinal | Enum Value |
|---------|-----------|
| - | `COMMON_OPEN_PAGE` |
| - | `COMMON_FOCUS_FILE` |
| - | `COMMON_FOCUS_FILE_LINE` |
| - | `COMMON_OPEN_FILE_DIALOG` |
| - | `COMMON_OPEN_URL` |
| - | `COMMON_FEEDBACK` |
| - | `COMMON_EVALUATION` |
| - | `COMMON_PAGE_READY` |
| - | `COMMON_PLUGIN_BASE_INFO` |
| - | `COMMON_CODE_CLICK_ACTION` |
| - | `COMMON_SHOW_MESSAGE_IN_WEB` |
| - | `COMMON_DOWNLOAD_TABLE` |

**Setting Operations:**
| Ordinal | Enum Value |
|---------|-----------|
| - | `SETTING_GET_CONFIG` |
| - | `SETTING_UPDATE_CONFIG` |
| - | `SETTING_RECEIVE_REPO_STATUS` |
| - | `SETTING_GET_CAN_OPEN_CODE_ENHANCE` |
| - | `SETTING_POPUP_KEYMAP_SETTINGS` |

**Code Search Operations:**
| Ordinal | Enum Value |
|---------|-----------|
| - | `CODE_SEARCH_REQUEST_CODESEARCH_REPOSITORY_LIST` |
| - | `CODE_SEARCH_REQUEST_CODESEARCH_LANGUAGE_LIST` |
| - | `CODE_SEARCH_REQUEST_CODESEARCH_CODE_LIST` |
| - | `CODE_SEARCH_REQUEST_OPEN_URL` |
| - | `CODE_SEARCH_REQUEST_COPY_CODE` |
| - | `CODE_SEARCH_REQUEST_INSERT_CODE` |
| - | `CODE_SEARCH_REQUEST_CODE_FILE` |
| - | `CODE_SEARCH_GET_CODESEARCH_REPOSITORY_LIST` |
| - | `CODE_SEARCH_GET_CODESEARCH_LANGUAGE_LIST` |
| - | `CODE_SEARCH_GET_CODESEARCH_CODE_LIST` |
| - | `CODE_SEARCH_GET_CODE_COPY_SUCCESS` |

**Other:**
| Ordinal | Enum Value |
|---------|-----------|
| - | `LOG` |
| - | `SAVE_SHOW_OPERATE_GUIDANCE` |

**Fields:**
- `private String float` (obfuscated: type string)
- `private ModuleEnum byte` (obfuscated: module reference)

**Key Methods:**
- `getType()` - Returns type string
- `getModule()` - Returns associated ModuleEnum

---

### 1.30 WebViewResponseTypeEnum

**Source file:** `kk` (obfuscated)

| Ordinal | Enum Value |
|---------|-----------|
| - | `CODE_SEARCH_GET_CODESEARCH_REPOSITORY_LIST` |
| - | `CODE_SEARCH_GET_CODESEARCH_LANGUAGE_LIST` |
| - | `CODE_SEARCH_GET_CODESEARCH_CODE_LIST` |
| - | `CODE_SEARCH_GET_CODE_COPY_SUCCESS` |
| - | `CODE_REVIEW_RECEIVER_PAGE_INIT` |
| - | `CODE_REVIEW_RECEIVER_CODE_REVIEW` |
| - | `CODE_REVIEW_RECEIVER_CHANGE_RESULT` |
| - | `SQL_CHAT_RECEIVE_TABLE_LIST` |
| - | `SQL_CHAT_RECEIVE_SOURCE_LIST` |
| - | `SQL_CHAT_RECEIVE_SOURCE_TYPES` |
| - | `SQL_CHAT_RECEIVE_SAVE` |
| - | `SQL_CHAT_RECEIVE_LINK_TEST` |
| - | `SQL_CHAT_UPDATE_CONVERSATION_LIST` |
| - | `USER_PERMISSION_LIST` |
| - | `SETTING_CHANGE_THEME` |

**Fields:**
- `private final String enum` (obfuscated: type string)

**Key Methods:**
- `getType()` - Returns type string

---
