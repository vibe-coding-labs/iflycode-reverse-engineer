# iFlyCode Enums Complete Decompilation Analysis

## Overview

This document provides a complete decompilation and analysis of all 31 enum classes found in the iFlyCode plugin, distributed across 4 packages:

| Package | Count | Classes |
|---------|-------|---------|
| `com.aicode.enums` | 24 | Core enums (status, language, UI, webview) |
| `com.aicode.agent.enums` | 5 | Agent communication enums |
| `com.aicode.inline.enums` | 3 | Inline Chat state machine enums |
| `com.aicode.apm.enums` | 2 | APM/telemetry enums |

All enum classes use string obfuscation via `H()` methods (e.g., `FontKt.H()`, `Maps.H()`, `AICodeStringUtil.H()`) to hide their internal string values. The obfuscated strings are decoded at runtime.

---

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
- `getByType(String)` - Returns `Optional<ElementTypeEnum>` by type, uses `Arrays.stream().filter().findFirst()`

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

## 2. com.aicode.agent.enums Package (5 classes)

### 2.1 CommandEnum (CRITICAL - 100+ values)

**Source file:** `cl` (obfuscated)
**The central command routing enum - drives ALL plugin functionality**

**Fields:**
- `private AgentModuleEnum case` (obfuscated: module reference)
- `private String final` (obfuscated: type string)
- `private String try` (obfuscated: description)
- `private String byte` (obfuscated: additional data)
- `private javax.swing.Icon enum` (obfuscated: icon)

**Constructors:** 4 overloads with varying parameters

**Complete Enum Values (by functional group):**

**LOG (Code Completion) Group:**
| Enum Value |
|-----------|
| `LOG_ACCEPT` |
| `LOG_ACCEPT_LINE` |
| `LOG_ACCEPT_WORD` |
| `LOG_ACCEPT_COUNT` |
| `LOG_REJECT` |
| `LOG_REJECT_ESC` |
| `LOG_DISPLAY` |
| `LOG_TIP_SETTING` |
| `LOG_OPERATE` |
| `LOG_EVALUATION` |
| `LOG_FEEDBACK` |
| `LOG_IMITATIVE_WRITE` |
| `LOG_TEST_COLLECTION_GENERATE` |
| `LOG_TEST_COLLECTION_COMMIT` |

**INIT Group:**
| Enum Value |
|-----------|
| `INIT` |
| `UPDATE` |
| `SERVER_RESOURCE` |

**LOGIN Group:**
| Enum Value |
|-----------|
| `USER_LOGIN` |
| `USER_LOGIN_CHECK` |
| `USER_LOGIN_ABORT` |
| `USER_LOGOUT` |
| `LOGIN_INFO` |
| `USER_PERMISSION` |
| `USER_VERSION` |
| `USER_MODEL_LIST` |
| `USER_PARSE_WEB_URL` |
| `USER_KNOWLEDGE_LIST` |
| `USER_FEEDBACK_CATEGORY` |
| `USER_CAN_CODE_ENHANCE` |

**CHAT Group:**
| Enum Value |
|-----------|
| `TALK_ASK` |
| `TALK_INTELLIGENT` |
| `TALK_PREDICT` |
| `TALK_RESEND` |
| `TALK_HISTORY` |
| `TALK_LIST` |
| `TALK_DELETE` |
| `TALK_CLEAR` |
| `TALK_RECOMMEND_GAMEPLAY` |
| `TALK_DOWNLOAD_MARKDOWN_TABLE` |
| `TALK_KNOWLEDGE` |

**SQL Chat Group:**
| Enum Value |
|-----------|
| `SQL_GENERATE` |
| `SQL_GENERATE_TALK` |
| `SQL_OPTIMIZE` |
| `SQL_OPTIMIZE_TALK` |
| `SQL_TABLE_LIST` |
| `SQL_SOURCE_LIST` |
| `SQL_SOURCE_TYPES` |
| `SQL_SOURCE_EDIT` |
| `SQL_SOURCE_DELETE` |
| `SQL_TEST_CONNECT` |

**CODE_COMPLETE Group:**
| Enum Value |
|-----------|
| `CODE_COMPLETE` |
| `RAG_LANGUAGES` |
| `MODEL_LIST_TIMER` |

**CODE_CHECK Group:**
| Enum Value |
|-----------|
| `CODE_CHECK` |
| `CODE_FAULT_ANALYSIS` |

**GIT_REVIEW Group:**
| Enum Value |
|-----------|
| `GIT_REVIEW` |
| `GIT_DIFF` |
| `GIT_SEARCH` |
| `GIT_USER_REPOS` |
| `GIT_LANG_LIST` |
| `GIT_COMMIT_MESSAGE` |
| `GIT_SAVE_TOKEN` |
| `GIT_REPO_AUTHORIZE` |
| `GIT_REPOSITORY_STATUS` |
| `GIT_CODE_KNOWLEDGE_RE_INDEX` |
| `GIT_CODE_KNOWLEDGE_REPO_STATUS` |

**UNIT_TEST Group:**
| Enum Value |
|-----------|
| `CODE_TEST` |
| `CODE_TEST_CODE` |
| `CODE_TEST_CASE` |
| `CODE_TEST_SAVE` |
| `CODE_TEST_ANALYSIS` |
| `CODE_GENERATE_TEST_CASE` |
| `CODE_TEST_TEMPLATE` |
| `CODE_TEST_MAKE_CASE_JAVA` |
| `TEST_MAKE_CODE` |
| `TEST_MAKE_CASE` |

**BATCH_UNIT_TEST Group:**
| Enum Value |
|-----------|
| `CODE_BATCH_UNIT_TEST_LIST` |
| `CODE_BATCH_UNIT_TEST_CREATE` |
| `CODE_BATCH_UNIT_TEST_DELETE` |
| `CODE_BATCH_UNIT_TEST_DOWNLOAD` |
| `CODE_BATCH_UNIT_TEST_CANCEL` |

**CODE_SEARCH Group:**
| Enum Value |
|-----------|
| `CODE_SEARCH` (implied by module) |

**INLINE_CHAT Group:**
| Enum Value |
|-----------|
| `INLINECHAT_CATEGORY` |
| `INLINECHAT_DIRECT` |
| `INLINECHAT_GET_FUNC_RANGE` |

**COMMON Group:**
| Enum Value |
|-----------|
| `ACTION_INIT` |
| `ACTION_ABORT` |
| `ACTION_OPEN_DOCUMENT` |
| `ACTION_SYNC_DOCUMENT_LIST` |
| `DIALOG_ACCEPT` |
| `DIALOG_REJECT` |
| `DIALOG_ABORT` |
| `DIALOG_EDIT` |
| `DIALOG_DIFF` |
| `GENERAL_SETTING` |
| `REPO_STATUS` |
| `ERROR` |

**Code Action Group:**
| Enum Value |
|-----------|
| `CODE_EXPLAIN` |
| `CODE_COMMENT` |
| `CODE_COMMENT_RANGE` |
| `CODE_INLINE_COMMENT` |
| `CODE_OPTIMIZE` |
| `CODE_DEBUG` |
| `CODE_DEBUG_DUPLICATE` |
| `CODE_SPLIT` |
| `CODE_HELP` |
| `CODE_DEMAND_ANALYSIS` |
| `CODE_DEMAND_SPLITTING` |
| `CODE_DEMAND_TEST` |

---

### 2.2 AgentModuleEnum

**Source file:** `ho` (obfuscated)
**14 module routing values**

| Ordinal | Enum Value | Decoded Name |
|---------|-----------|-------------|
| 0 | `LOG` | Decoded |
| 1 | `INIT` | Decoded |
| 2 | `LOGIN` | Decoded |
| 3 | `COMMON` | Decoded |
| 4 | `CHAT` | Decoded |
| 5 | `SQL_CHAT` | Decoded |
| 6 | `CODE_COMPLETE` | Decoded |
| 7 | `CODE_SEARCH` | Decoded |
| 8 | `CODE_CHECK` | Decoded |
| 9 | `GIT_REVIEW` | Decoded |
| 10 | `UNIT_TEST` | Decoded |
| 11 | `BATCH_UNIT_TEST` | Decoded |
| 12 | `CODE_TEST_TEMPLATE` | Decoded |
| 13 | `SERVER_RESOURCE` | Decoded |
| 14 | `INLINE_CHAT` | Decoded |

**Key Methods:** Standard enum methods only

---

### 2.3 ModuleEnum

**Source file:** `ch` (obfuscated)
**12 module values for WebView routing**

| Ordinal | Enum Value |
|---------|-----------|
| 0 | `LOG` |
| 1 | `LOGIN` |
| 2 | `COMMON` |
| 3 | `SETTING` |
| 4 | `CHAT` |
| 5 | `SQL_CHAT` |
| 6 | `CODE_SEARCH` |
| 7 | `CODE_CHECK` |
| 8 | `GIT_VIEW` |
| 9 | `UNIT_TEST` |
| 10 | `BATCH_UNIT_TEST` |
| 11 | `UNIT_TESTING` |

**Key Methods:**
- `getType()` - Returns type string
- `getByType(String)` - Lookup by type, returns null if not found

---

### 2.4 PageEnum

**Source file:** `ri` (obfuscated)
**6 page values for WebView navigation**

| Ordinal | Enum Value | Type |
|---------|-----------|------|
| 0 | `CHAT_VIEW` | Decoded |
| 1 | `SETTING_PAGE` | Decoded |
| 2 | `CODE_CHECK` | Decoded |
| 3 | `CODE_REVIEW` | Decoded |
| 4 | `UNIT_TEST` | Decoded |
| 5 | `UNIT_TESTING` | Decoded |

**Key Methods:**
- `getType()` - Returns type string
- `getByType(String)` - Lookup by type, returns null if not found

---

### 2.5 PermissionEnum

**Source file:** `ud` (obfuscated)
**24 permission values controlling feature access**

| Ordinal | Enum Value |
|---------|-----------|
| 0 | `DEMAND_TEST` |
| 1 | `SQL_OPTIMIZATION` |
| 2 | `CHAT_SQL_GENERATION` |
| 3 | `INLINE_CHAT` |
| 4 | `DEMAND_SPLIT` |
| 5 | `FUNCTION_SPLIT` |
| 6 | `TALK_INTELLIGENT` |
| 7 | `FAILURE_ANALYSIS` |
| 8 | `DEMAND_ANALYSIS` |
| 9 | `CODE_OPTIMIZATION` |
| 10 | `REVIEW` |
| 11 | `GENERATE_TEST_CASE` |
| 12 | `COMMENTS` |
| 13 | `CODE_KNOWLEDGE_BASE` |
| 14 | `CHAT_MODULE` |
| 15 | `CHAT_SQL_OPTIMIZATION` |
| 16 | `LINE_COMMENTS` |
| 17 | `UNIT_TESTING` |
| 18 | `CODE_DEBUG` |
| 19 | `GENERATE_COMMIT` |
| 20 | `DOC_COMMENTS` |
| 21 | `SQL_GENERATION` |
| 22 | `BATCH_UNITTEST` |

**Fields:**
- `private final String enum` (obfuscated: permission code)
- `private final AnAction byte` (obfuscated: associated IntelliJ action)
- `public static final List<String> PERMISSION_ORDER_LIST` - Ordered list of permission string codes
- `public static final List<PermissionEnum> RIGHT_PERMISSION_ORDER_LIST` - Ordered list for right panel

**Key Methods:**
- `getPermissionCode()` - Returns permission code string
- `getAction()` - Returns associated `AnAction`

---

## 3. com.aicode.inline.enums Package (3 classes)

### 3.1 InlineChatCategoryEnum

**Source file:** `kn` (obfuscated)
**Inline Chat category types - determines the chat mode**

| Ordinal | Enum Value | Value (field) |
|---------|-----------|--------------|
| 0 | `DOC` | Decoded |
| 1 | `LINEDOC` | Decoded |
| 2 | `EDIT` | Decoded |
| 3 | `GENERATE` | Decoded |
| 4 | `UNKNOW` | Decoded |

**Note:** "UNKNOW" is a typo for "UNKNOWN" in the original code.

**Fields:**
- `private final String byte` (obfuscated: value string)

**Key Methods:**
- `getValue()` - Returns value string
- `getCategoryEnumByValue(String)` - Lookup by value, defaults to UNKNOW
- `getCategoryEnumByName(String)` - Lookup by name (case-insensitive), defaults to UNKNOW

---

### 3.2 InlineChatOperateEnum

**Source file:** `je` (obfuscated)
**Inline Chat operation types**

| Ordinal | Enum Value | Description |
|---------|-----------|-------------|
| 0 | `INSERT` | Insert new code |
| 1 | `EDIT` | Edit existing code |

---

### 3.3 InlineChatStepEnum

**Source file:** `rf` (obfuscated)
**Inline Chat state machine steps**

| Ordinal | Enum Value | Description |
|---------|-----------|-------------|
| 0 | `CATEGORY` | Category selection phase |
| 1 | `LOADING` | Loading/AI processing phase |
| 2 | `ERROR` | Error state |
| 3 | `SUCCESS` | Successful completion |

**State Machine Flow:**
```
CATEGORY -> LOADING -> SUCCESS
                   -> ERROR
```

---

## 4. com.aicode.apm.enums Package (2 classes)

### 4.1 SpanAttrEnum

**Source file:** `oi` (obfuscated)
**30 OpenTelemetry span attribute keys**

| Ordinal | Enum Value | Attribute Key |
|---------|-----------|-------------|
| 0 | `PLUGIN_VERSION` | Decoded |
| 1 | `AGENT_ERROR_REASON` | Decoded |
| 2 | `COMPLETE_DURATION` | Decoded |
| 3 | `SETTING_MESSAGE_TYPE` | Decoded |
| 4 | `HTTP_SCHEME` | Decoded |
| 5 | `SETTING_TRIGGER_ON_PAUSE` | Decoded |
| 6 | `COMPLETE_IS_STREAM` | Decoded |
| 7 | `COMPLETE_FIRST_DURATION` | Decoded |
| 8 | `DISABLE_GPU` | Decoded |
| 9 | `EXCEPTION_COMMAND` | Decoded |
| 10 | `AGENT_START_REASON` | Decoded |
| 11 | `COMPLETE_ACCEPT` | Decoded |
| 12 | `EXCEPTION_MESSAGE` | Decoded |
| 13 | `AGENT_START_CODE` | Decoded |
| 14 | `SETTING_CODE_MODE` | Decoded |
| 15 | `AGENT_VERSION` | Decoded |
| 16 | `SYSTEM_USERNAME` | Decoded |
| 17 | `COMPLETE_FILE_LINE` | Decoded |
| 18 | `COMPLETE_RESULT` | Decoded |
| 19 | `SETTING_JAVA_TEST` | Decoded |
| 20 | `SETTING_TRIGGER_TIME_DELAY` | Decoded |
| 21 | `COMPLETE_FORCE` | Decoded |
| 22 | `COMPLETE_FILE_SIZE` | Decoded |
| 23 | `COMPLETE_REJECT` | Decoded |
| 24 | `EXCEPTION_CODE` | Decoded |
| 25 | `COMMAND_ID` | Decoded |
| 26 | `PLUGIN_UPDATE` | Decoded |
| 27 | `USER_USERNAME` | Decoded |
| 28 | `SETTING_JAVA_MOCK` | Decoded |
| 29 | `IDEA_VERSION` | Decoded |

**Fields:**
- `private final String float` (obfuscated: attribute key string)

**Key Methods:**
- `getKey()` - Returns attribute key string

---

### 4.2 TracerEnum

**Source file:** `jj` (obfuscated)
**9 OpenTelemetry tracer definitions**

| Ordinal | Enum Value | Name | Description |
|---------|-----------|------|-------------|
| 0 | `IDEA_RUN` | Decoded | Decoded |
| 1 | `AGENT_RUN` | Decoded | Decoded |
| 2 | `AGENT_FAILURE` | Decoded | Decoded |
| 3 | `AGENT_RESTART` | Decoded | Decoded |
| 4 | `AGENT_ERROR` | Decoded | Decoded |
| 5 | `CODE_COMPLETE_PARENT` | Decoded | Decoded |
| 6 | `CODE_COMPLETE_INLINE_CHAT_PARENT` | Decoded | Decoded |
| 7 | `CODE_COMPLETE` | `CommandEnum.CODE_COMPLETE.getType()` | `CommandEnum.CODE_COMPLETE.getDesc()` |
| 8 | `RECORD_EXCEPTION` | Decoded | Decoded |

**Fields:**
- `private final String byte` (obfuscated: description)
- `private final String enum` (obfuscated: name)

**Key Methods:**
- `getDesc()` - Returns description string
- `getText()` - Returns name string

**Cross-Reference:** `CODE_COMPLETE` tracer references `CommandEnum.CODE_COMPLETE` for its name and description, establishing the link between APM tracing and command routing.

---

## 5. Enum Cross-Reference Map

### 5.1 CommandEnum -> AgentModuleEnum Mapping

Each `CommandEnum` value has an `AgentModuleEnum` reference that routes it to the correct module handler:

```
AgentModuleEnum.LOG         <- LOG_ACCEPT, LOG_ACCEPT_LINE, LOG_ACCEPT_WORD, LOG_ACCEPT_COUNT, LOG_REJECT, LOG_REJECT_ESC, LOG_DISPLAY, LOG_TIP_SETTING, LOG_OPERATE, LOG_EVALUATION, LOG_FEEDBACK, LOG_IMITATIVE_WRITE, LOG_TEST_COLLECTION_GENERATE, LOG_TEST_COLLECTION_COMMIT
AgentModuleEnum.INIT        <- INIT, UPDATE, SERVER_RESOURCE
AgentModuleEnum.LOGIN       <- USER_LOGIN, USER_LOGIN_CHECK, USER_LOGIN_ABORT, USER_LOGOUT, LOGIN_INFO, USER_PERMISSION, USER_VERSION, USER_MODEL_LIST, USER_PARSE_WEB_URL, USER_KNOWLEDGE_LIST, USER_FEEDBACK_CATEGORY, USER_CAN_CODE_ENHANCE
AgentModuleEnum.COMMON      <- ACTION_INIT, ACTION_ABORT, ACTION_OPEN_DOCUMENT, ACTION_SYNC_DOCUMENT_LIST, DIALOG_ACCEPT, DIALOG_REJECT, DIALOG_ABORT, DIALOG_EDIT, DIALOG_DIFF, GENERAL_SETTING, REPO_STATUS, ERROR
AgentModuleEnum.CHAT        <- TALK_ASK, TALK_INTELLIGENT, TALK_PREDICT, TALK_RESEND, TALK_HISTORY, TALK_LIST, TALK_DELETE, TALK_CLEAR, TALK_RECOMMEND_GAMEPLAY, TALK_DOWNLOAD_MARKDOWN_TABLE, TALK_KNOWLEDGE
AgentModuleEnum.SQL_CHAT    <- SQL_GENERATE, SQL_GENERATE_TALK, SQL_OPTIMIZE, SQL_OPTIMIZE_TALK, SQL_TABLE_LIST, SQL_SOURCE_LIST, SQL_SOURCE_TYPES, SQL_SOURCE_EDIT, SQL_SOURCE_DELETE, SQL_TEST_CONNECT
AgentModuleEnum.CODE_COMPLETE <- CODE_COMPLETE, RAG_LANGUAGES, MODEL_LIST_TIMER
AgentModuleEnum.CODE_SEARCH <- (code search commands)
AgentModuleEnum.CODE_CHECK  <- CODE_CHECK, CODE_FAULT_ANALYSIS
AgentModuleEnum.GIT_REVIEW  <- GIT_REVIEW, GIT_DIFF, GIT_SEARCH, GIT_USER_REPOS, GIT_LANG_LIST, GIT_COMMIT_MESSAGE, GIT_SAVE_TOKEN, GIT_REPO_AUTHORIZE, GIT_REPOSITORY_STATUS, GIT_CODE_KNOWLEDGE_RE_INDEX, GIT_CODE_KNOWLEDGE_REPO_STATUS
AgentModuleEnum.UNIT_TEST   <- CODE_TEST, CODE_TEST_CODE, CODE_TEST_CASE, CODE_TEST_SAVE, CODE_TEST_ANALYSIS, CODE_GENERATE_TEST_CASE, CODE_TEST_TEMPLATE, CODE_TEST_MAKE_CASE_JAVA, TEST_MAKE_CODE, TEST_MAKE_CASE
AgentModuleEnum.BATCH_UNIT_TEST <- CODE_BATCH_UNIT_TEST_LIST, CODE_BATCH_UNIT_TEST_CREATE, CODE_BATCH_UNIT_TEST_DELETE, CODE_BATCH_UNIT_TEST_DOWNLOAD, CODE_BATCH_UNIT_TEST_CANCEL
AgentModuleEnum.INLINE_CHAT <- INLINECHAT_CATEGORY, INLINECHAT_DIRECT, INLINECHAT_GET_FUNC_RANGE
```

### 5.2 WebViewDataTypeEnum -> ModuleEnum Mapping

`WebViewDataTypeEnum` values each reference a `ModuleEnum` for WebView routing:

```
ModuleEnum.LOG          <- LOG
ModuleEnum.LOGIN        <- LOGIN_GO_LOGIN, LOGIN_LOGIN_SUCCEED, LOGIN_SHOW_FRESH, LOGIN_INIT, LOGIN_LOGIN_CHECK, LOGIN_LOGIN_ABORT, LOGIN_LOGOUT, LOGIN_CLOSE_QR_CODE, LOGIN_RECEIVER_LOGIN_IFRAME_SRC
ModuleEnum.COMMON       <- COMMON_OPEN_PAGE, COMMON_FOCUS_FILE, COMMON_FOCUS_FILE_LINE, COMMON_OPEN_FILE_DIALOG, COMMON_OPEN_URL, COMMON_FEEDBACK, COMMON_EVALUATION, COMMON_PAGE_READY, COMMON_PLUGIN_BASE_INFO, COMMON_CODE_CLICK_ACTION, COMMON_SHOW_MESSAGE_IN_WEB, COMMON_DOWNLOAD_TABLE
ModuleEnum.SETTING      <- SETTING_GET_CONFIG, SETTING_UPDATE_CONFIG, SETTING_RECEIVE_REPO_STATUS, SETTING_GET_CAN_OPEN_CODE_ENHANCE, SETTING_POPUP_KEYMAP_SETTINGS
ModuleEnum.CHAT         <- All CHAT_* values
ModuleEnum.SQL_CHAT     <- All SQL_CHAT_* values
ModuleEnum.CODE_SEARCH  <- All CODE_SEARCH_* values
ModuleEnum.CODE_CHECK   <- All CODE_CHECK_* values
ModuleEnum.GIT_VIEW     <- All GIT_* values
ModuleEnum.UNIT_TEST    <- All UNIT_TEST_* values
ModuleEnum.BATCH_UNIT_TEST <- All BATCH_UNIT_TEST_* values
ModuleEnum.UNIT_TESTING <- All UNIT_TESTING_* values
```

### 5.3 PermissionEnum -> CommandEnum Mapping

Permissions gate access to specific commands:

```
INLINE_CHAT        -> INLINECHAT_CATEGORY, INLINECHAT_DIRECT
TALK_INTELLIGENT   -> TALK_INTELLIGENT
COMMENTS           -> CODE_COMMENT
LINE_COMMENTS      -> CODE_INLINE_COMMENT
DOC_COMMENTS       -> CODE_COMMENT_RANGE
CODE_OPTIMIZATION  -> CODE_OPTIMIZE
CODE_DEBUG         -> CODE_DEBUG
REVIEW             -> GIT_REVIEW
GENERATE_TEST_CASE -> CODE_GENERATE_TEST_CASE
UNIT_TESTING       -> CODE_TEST, CODE_TEST_CODE, CODE_TEST_CASE, CODE_TEST_SAVE
FAILURE_ANALYSIS   -> CODE_FAULT_ANALYSIS
DEMAND_ANALYSIS    -> CODE_DEMAND_ANALYSIS
DEMAND_SPLIT       -> CODE_DEMAND_SPLITTING
DEMAND_TEST        -> CODE_DEMAND_TEST
GENERATE_COMMIT    -> GIT_COMMIT_MESSAGE
CODE_KNOWLEDGE_BASE -> GIT_CODE_KNOWLEDGE_RE_INDEX, GIT_CODE_KNOWLEDGE_REPO_STATUS
SQL_GENERATION     -> SQL_GENERATE, SQL_GENERATE_TALK
SQL_OPTIMIZATION   -> SQL_OPTIMIZE, SQL_OPTIMIZE_TALK
CHAT_SQL_GENERATION -> SQL_CHAT_SEND_MSG
CHAT_SQL_OPTIMIZATION -> SQL_CHAT_SEND_MSG
CHAT_MODULE        -> CHAT_SEND_MSG
BATCH_UNITTEST     -> CODE_BATCH_UNIT_TEST_*
```

### 5.4 Inline Chat State Machine

```
InlineChatStepEnum:
  CATEGORY (0) -> User selects category from InlineChatCategoryEnum
      |
      v
  LOADING (1) -> AI processing, shows loading indicator
      |
      +-------> ERROR (2) -> Error occurred, can retry
      |
      +-------> SUCCESS (3) -> Result ready, user can accept/reject

InlineChatCategoryEnum:
  DOC (0)       -> Generate doc comment
  LINEDOC (1)   -> Generate line-level doc
  EDIT (2)      -> Edit selected code
  GENERATE (3)  -> Generate new code
  UNKNOW (4)    -> Unknown/fallback

InlineChatOperateEnum:
  INSERT (0)    -> Insert new code at position
  EDIT (1)      -> Edit existing code in-place
```

---

## 6. API Endpoint Mapping

Based on the command enum values and their module routing, the following API endpoint structure can be inferred:

### 6.1 Code Completion API
```
CODE_COMPLETE     -> /api/code/complete (streaming)
RAG_LANGUAGES     -> /api/code/rag/languages
MODEL_LIST_TIMER  -> /api/model/list (periodic)
```

### 6.2 Chat API
```
TALK_ASK          -> /api/chat/ask (streaming)
TALK_INTELLIGENT  -> /api/chat/intelligent (streaming)
TALK_PREDICT      -> /api/chat/predict
TALK_RESEND       -> /api/chat/resend (streaming)
TALK_HISTORY      -> /api/chat/history
TALK_LIST         -> /api/chat/list
TALK_DELETE       -> /api/chat/delete
TALK_CLEAR        -> /api/chat/clear
```

### 6.3 SQL Chat API
```
SQL_GENERATE      -> /api/sql/generate (streaming)
SQL_OPTIMIZE      -> /api/sql/optimize (streaming)
SQL_TABLE_LIST    -> /api/sql/tables
SQL_SOURCE_LIST   -> /api/sql/sources
SQL_SOURCE_TYPES  -> /api/sql/source/types
SQL_TEST_CONNECT  -> /api/sql/test/connect
```

### 6.4 Code Analysis API
```
CODE_CHECK        -> /api/code/check
CODE_EXPLAIN      -> /api/code/explain (streaming)
CODE_COMMENT      -> /api/code/comment (streaming)
CODE_OPTIMIZE     -> /api/code/optimize (streaming)
CODE_DEBUG        -> /api/code/debug (streaming)
CODE_SPLIT        -> /api/code/split
```

### 6.5 Unit Test API
```
CODE_TEST         -> /api/test/generate (streaming)
CODE_TEST_SAVE    -> /api/test/save
CODE_TEST_ANALYSIS-> /api/test/analysis
CODE_TEST_TEMPLATE-> /api/test/template
```

### 6.6 Git API
```
GIT_REVIEW        -> /api/git/review (streaming)
GIT_DIFF          -> /api/git/diff
GIT_COMMIT_MESSAGE-> /api/git/commit/message (streaming)
GIT_SEARCH        -> /api/git/search
GIT_USER_REPOS    -> /api/git/user/repos
```

### 6.7 Auth API
```
USER_LOGIN        -> /api/user/login
USER_LOGIN_CHECK  -> /api/user/login/check
USER_LOGOUT       -> /api/user/logout
USER_PERMISSION   -> /api/user/permission
USER_MODEL_LIST   -> /api/user/model/list
```

---

## 7. Obfuscation Analysis

### 7.1 String Obfuscation Pattern

All enum string values are obfuscated using the `H()` static method pattern. The obfuscation methods are distributed across multiple utility classes:

| Obfuscation Class | Used By |
|------------------|---------|
| `com.aicode.ui.FontKt.H()` | AICodeStatus, ChatOperationEnum, CodeTipType, CodeTipRequestType |
| `com.aicode.util.Maps.H()` | AICodeStatus, BatchTestUnitLimt, CodeCollectEnum, RepoStatusEnum, GitRepoStatusEnum |
| `com.aicode.util.AICodeStringUtil.H()` | BatchTestUnitLimt, ModuleEnum |
| `com.aicode.util.AICodeUtils.H()` | CodeTipRequestType, PyUnitTestMockEnum |
| `com.aicode.exception.RequestTimeoutException.H()` | AssistantTypeEnum |
| `com.aicode.content.util.file.LanguageFileExtensionDetails.H()` | AssistantTypeEnum, CodeTipType, ElementTypeEnum |
| `com.aicode.content.util.file.FileExtensionLanguageDetails.H()` | BatchTestUnitLimt |
| `com.aicode.content.util.EditorUtils.H()` | ClientTypeEnum |
| `com.aicode.diff.FileService.H()` | ClientTypeEnum, CodeTipType, DuplicateRule, DuplicateFileNameSwitchEnum |
| `com.aicode.diff.GenericUtils.H()` | GenaratebyTemplateSwitchEnum |
| `com.aicode.util.IndentLineUtil.H()` | LineToolsTypeEnum, RepoStatusEnum, TracerEnum |
| `com.aicode.util.JComponentKt.H()` | TipTypeEnum, ElementTypeEnum |
| `com.aicode.util.PropertyUtils.H()` | DuplicateFileNameSwitchEnum, ModuleEnum |
| `com.aicode.util.NewFileUtils.H()` | PluginSceneEnum, TipTypeEnum |
| `com.aicode.util.HandleCacheUtil.H()` | CodeCollectEnum |
| `com.aicode.util.Application.H()` | PyUnitTestBaseEnum, UnitTestBaseEnum, AgentModuleEnum |
| `com.aicode.agent.service.CodeCompleteService.H()` | TestGenerationProcess, CommandEnum |
| `com.aicode.inline.ide.IdeAction.H()` | CodeTipRequestType, LineToolsTypeEnum, AgentModuleEnum |
| `com.aicode.inline.ide.ConditionalActionConfiguration.H()` | OperateActionEnum, DuplicateRule, PyUnitTestBaseEnum, UnitTestMockEnum |
| `com.aicode.inline.status.InlineChatStatusServiceKt.H()` | SendKeyEnum, ClientTypeEnum |
| `com.aicode.inline.controller.ChatInputController.H()` | PyUnitTestMockEnum, UnitTestMockEnum |
| `com.aicode.apm.OpenTelemetryUtil.H()` | RestartEnum, InlineChatStepEnum |
| `com.aicode.action.batch.MethodGeneratorConfig.H()` | TipType |
| `com.aicode.service.editor.RequestResultList.H()` | ChatOperationEnum, GitRepoStatusEnum |

### 7.2 Field Name Obfuscation

Enum field names are obfuscated using Java reserved keywords:

| Obfuscated Name | Actual Meaning | Used In |
|----------------|---------------|---------|
| `byte` | type/code/name | 20+ enums |
| `enum` | type/description/name | 15+ enums |
| `final` | type/code | 5 enums |
| `try` | description/name | 5 enums |
| `float` | suffix/limit/array | 8 enums |
| `case` | module reference | CommandEnum |

### 7.3 Source File Obfuscation

All source filenames are obfuscated to 2-letter codes:
- `sm` = AICodeStatus
- `lh` = AssistantTypeEnum
- `gg` = BatchTestUnitLimt
- `km` = ChatOperationEnum
- `of` = ClientTypeEnum
- `ph` = CodeCollectEnum
- `lf` = CodeTipRequestType
- `ze` = CodeTipType
- `mf` = DuplicateFileNameSwitchEnum
- `zm` = DuplicateRule
- `hf` = ElementTypeEnum
- `un` = FileExtensionEnum
- `pg` = GenaratebyTemplateSwitchEnum
- `bn` = GitRepoStatusEnum
- `id` = LanguageEnum
- `jk` = LineToolsTypeEnum
- `ci` = OperateActionEnum
- `ei` = PluginSceneEnum
- `jn` = PyUnitTestBaseEnum
- `se` = PyUnitTestMockEnum
- `yi` = RepoStatusEnum
- `ii` = RestartEnum
- `ug` = SendKeyEnum
- `bm` = TestGenerationProcess
- `eh` = TipType
- `pn` = TipTypeEnum
- `de` = UnitTestBaseEnum
- `wi` = UnitTestMockEnum
- `lk` = WebViewDataTypeEnum
- `kk` = WebViewResponseTypeEnum
- `cl` = CommandEnum
- `ho` = AgentModuleEnum
- `ud` = PermissionEnum
- `ch` = ModuleEnum
- `ri` = PageEnum
- `kn` = InlineChatCategoryEnum
- `je` = InlineChatOperateEnum
- `rf` = InlineChatStepEnum
- `oi` = SpanAttrEnum
- `jj` = TracerEnum

---

## 8. Summary Statistics

| Metric | Value |
|--------|-------|
| Total enum classes | 31 (30 enums + 1 inner class) |
| Total enum values | ~400+ |
| CommandEnum values | 100+ |
| WebViewDataTypeEnum values | 100+ |
| PermissionEnum values | 24 |
| AgentModuleEnum values | 15 |
| LanguageEnum values | 32 |
| SpanAttrEnum values | 30 |
| Obfuscation utility classes | 20+ |
| Cross-reference links | 50+ |

### Key Architectural Insights

1. **CommandEnum is the central routing hub** - Every user action and system event flows through CommandEnum, which routes to the appropriate AgentModuleEnum handler.

2. **WebViewDataTypeEnum mirrors CommandEnum** - The WebView data types largely parallel the CommandEnum values, serving as the bridge between the WebView frontend and the Java backend.

3. **PermissionEnum gates CommandEnum** - Before a command is executed, the permission system checks if the user has access to the corresponding feature.

4. **Inline Chat has its own state machine** - The InlineChatStepEnum (CATEGORY -> LOADING -> SUCCESS/ERROR) combined with InlineChatCategoryEnum (DOC/LINEDOC/EDIT/GENERATE) and InlineChatOperateEnum (INSERT/EDIT) forms a complete state machine for inline chat interactions.

5. **Two parallel module systems** - AgentModuleEnum (15 values) handles agent communication routing, while ModuleEnum (12 values) handles WebView UI routing. They overlap significantly but are not identical (e.g., AgentModuleEnum has SERVER_RESOURCE and CODE_TEST_TEMPLATE, while ModuleEnum has SETTING and GIT_VIEW).

6. **Language support is extensive** - LanguageEnum (32 values) and FileExtensionEnum (13 values) together define support for C, C++, C#, Python, Java, Kotlin, Rust, Swift, Objective-C, Go, JavaScript, TypeScript, and Vue, with multiple sub-variants per language.

7. **Authorization has 5 tiers** - The AUTHORIZED_1 through AUTHORIZED_5 values in RepoStatusEnum suggest a multi-level authorization system, likely corresponding to subscription tiers (free, basic, pro, enterprise, etc.).