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