## 4. Field Type Mapping Table

### 4.1 Primitive Type Usage

| Java Type | DTOs Using It | Fields |
|-----------|--------------|--------|
| boolean | MessageDto, CodeModel, SettingsDto (x13), FirstChatMessage$ValueDTO | stream, chatTest, checked, tokenExhausted, autoTriggerOnPause, lineToolsPermission*, open*, errorType, reLogin |
| int | MessageDto, PresentationDataDto, CommentInfo | currentLength, streamStep, inlineChatVersion, line, character, index |
| long | MessageDto, CodeTipRequestDto | timeStamp, firstAgentDuration |

### 4.2 Boxed Type Usage

| Java Type | DTOs Using It | Fields |
|-----------|--------------|--------|
| String | All DTOs except RangeDTO, PageInfo | Nearly all text fields |
| Integer | CodeInfoDto$RangeDTO, SettingsDto, CodeSearchDto, PageInfo | line, character, autoTriggerTimeDelay, isOpen, isPublic, startRow, endRow, codeLength, currentPage, pageSize, total, totalPage, count, docChangeCount |
| Long | DatabaseDto, CodeTipRequestDto, CodeSearchDto | createTime, updateTime, startTime |
| Boolean | CodeCheckListDto$ValueDTO, DatabaseDto, TipInfoDto | status, isShowOperateGuide |
| Double | CodeSearchDto | codeVector |
| BigDecimal | CodeSearchDto | score |

### 4.3 Collection Type Usage

| Java Type | DTOs Using It | Fields |
|-----------|--------------|--------|
| List\<CodeInfoDto$RangeDTO\> | MessageDto, CodeInfoDto, CodeCheckOriginDto$ErrListDTO | range, bodyRange |
| List\<CodeModel\> | UserInfoDto, FunctionModelInfo | codeModelDtoList, codeModelList |
| List\<CommentInfo\> | CommentContext | methods |
| List\<String\> | DatabaseDto, SqlInfoDto | databases, tables |
| List\<ReposInfoDto\> | CodeRepoInfoDto | content |
| List\<CodeSearchDto\> | CodeSearchInfoDto | content |
| List\<CodeCheckOriginDto$ErrListDTO\> | CodeCheckOriginDto | errList |
| String[] | SettingsDto | codeCompleteDisableLang |

### 4.4 Gson Type Usage

| Java Type | DTOs Using It | Fields |
|-----------|--------------|--------|
| JsonArray | MessageDto, CommentInfo, FirstChatMessage$ValueDTO | intelligent, relatedFiles, range, bodyRange, knowledge |
| JsonObject | FirstChatMessage$ValueDTO | data |

### 4.5 Special Type Usage

| Java Type | DTOs Using It | Fields |
|-----------|--------------|--------|
| Object | MessageDto, ResponseDto, CodeCheckListDto$ValueDTO | data, knowledge, otherObject |
| AtomicBoolean | MessageDto | isDisplay |
| StringBuffer | MessageDto | text |
| Flow.Subscriber | CodeTipRequestDto | codeSubScriber |
| Span (OpenTelemetry) | CodeTipRequestDto | parentSpan |
| Project (IntelliJ) | MessageDto | project |
| EditorRequestService | CodeTipRequestDto | request |
| RequestCaseCodeDto | MessageDto | requestCaseCodeDto |

---

## 5. API Endpoint Correspondence

### 5.1 WebSocket Commands and Their DTOs

| Command | Request DTO | Response DTO | Description |
|---------|------------|--------------|-------------|
| USER_LOGIN | (credentials in MessageDto.data) | ResponseDto (data=UserInfoDto) | User authentication |
| LOGIN_INFO | - | ResponseDto (data=LoginInfo) | Agent binary update check |
| INIT | MessageDto | ResponseDto | Initialize agent session |
| TALK_INTELLIGENT | WebRequestDto (value=FirstChatMessage) | ResponseStreamDto | AI chat conversation |
| INLINECHAT_CATEGORY | WebRequestDto (value=FirstChatMessage) | ResponseStreamDto | Inline chat |
| INLINECHAT_DIRECT | WebRequestDto (value=FirstChatMessage) | ResponseStreamDto | Direct inline chat |
| CODE_CHECK | MessageDto (data=CodeCheckDto) | ResponseDto (data=CodeCheckListDto) | Code quality check |
| CODE_COMMENT | MessageDto (data=CommentContext) | ResponseStreamDto | Generate code comments |
| CODE_INLINE_COMMENT | MessageDto (data=CommentContext) | ResponseStreamDto | Generate inline comments |
| CODE_OPTIMIZE | MessageDto | ResponseStreamDto | Code optimization |
| CODE_DEBUG | MessageDto | ResponseStreamDto | Code debug analysis |
| CODE_FAULT_ANALYSIS | MessageDto | ResponseStreamDto | Fault analysis |
| CODE_EXPLAIN | MessageDto | ResponseStreamDto | Code explanation |
| CODE_DEMAND_ANALYSIS | MessageDto | ResponseStreamDto | Demand analysis |
| CODE_DEMAND_TEST | MessageDto | ResponseStreamDto | Demand test |
| CODE_GENERATE_TEST_CASE | MessageDto | ResponseStreamDto | Generate test case |
| CODE_TEST | MessageDto | ResponseStreamDto | Run test |
| CODE_TEST_MAKE_CASE_JAVA | MessageDto | ResponseStreamDto | Make Java test case |
| CODE_BATCH_UNIT_TEST_CREATE | MessageDto | ResponseDto | Batch unit test create |
| CODE_BATCH_UNIT_TEST_DELETE | MessageDto | ResponseDto | Batch unit test delete |
| CODE_BATCH_UNIT_TEST_DOWNLOAD | MessageDto | ResponseDto | Batch unit test download |
| SQL_GENERATE_TALK | WebRequestDto (value=FirstChatMessage) | ResponseStreamDto | SQL generation chat |
| SQL_OPTIMIZE | MessageDto | ResponseStreamDto | SQL optimization |
| SQL_TEST_CONNECT | MessageDto (data=ConnectConfigDto) | ResponseDto | Test DB connection |
| SQL_SOURCE_LIST | MessageDto | ResponseDto (data=List\<DatabaseDto\>) | List database sources |
| SQL_SOURCE_EDIT | MessageDto (data=DatabaseDto) | ResponseDto | Edit database source |
| SQL_SOURCE_TYPES | MessageDto | ResponseDto | Get DB client types |
| GIT_SEARCH | MessageDto | ResponseDto (data=CodeSearchInfoDto) | Code search |
| GIT_USER_REPOS | MessageDto | ResponseDto (data=CodeRepoInfoDto) | List user repos |
| GIT_COMMIT_MESSAGE | MessageDto | ResponseStreamDto | Generate commit message |
| GIT_REVIEW | MessageDto | ResponseStreamDto | Code review |
| GIT_DIFF | MessageDto | ResponseDto | Git diff |
| GIT_LANG_LIST | MessageDto | ResponseDto | Language list |
| GIT_CODE_KNOWLEDGE_RE_INDEX | MessageDto | ResponseDto | Re-index knowledge base |
| GIT_REPOSITORY_STATUS | MessageDto | ResponseDto | Repository status |
| GENERAL_SETTING | MessageDto (data=SettingsDto) | ResponseDto | Sync settings |
| USER_PERMISSION | MessageDto | ResponseDto (data=List\<FunctionModelInfo\>) | Get user permissions |
| USER_LOGOUT | MessageDto | ResponseDto | User logout |
| USER_VERSION | MessageDto | ResponseDto | Version check |
| USER_FEEDBACK_CATEGORY | MessageDto | ResponseDto | Feedback categories |
| USER_PARSE_WEB_URL | MessageDto | ResponseDto | Parse web URL |
| USER_KNOWLEDGE_LIST | MessageDto | ResponseDto | Knowledge base list |
| MODEL_LIST_TIMER | MessageDto | ResponseDto (data=List\<CodeModel\>) | Refresh model list |
| RAG_LANGUAGES | MessageDto | ResponseDto | RAG supported languages |
| DIALOG_DIFF | MessageDto | ResponseDto | Dialog diff |
| DIALOG_EDIT | MessageDto | ResponseDto | Dialog edit |
| DIALOG_REJECT | MessageDto | ResponseDto | Dialog reject |
| TALK_HISTORY | MessageDto | ResponseDto | Chat history |
| TALK_CLEAR | MessageDto | ResponseDto | Clear chat |
| TALK_RESEND | MessageDto | ResponseDto | Resend message |
| TALK_RECOMMEND_GAMEPLAY | MessageDto | ResponseDto | Recommend gameplay |
| LOG_ACCEPT | MessageDto | ResponseDto | Log acceptance |
| LOG_REJECT | MessageDto | ResponseDto | Log rejection |
| LOG_ACCEPT_LINE | MessageDto | ResponseDto | Log line acceptance |
| LOG_ACCEPT_WORD | MessageDto | ResponseDto | Log word acceptance |
| LOG_ACCEPT_COUNT | MessageDto | ResponseDto | Log acceptance count |
| LOG_TIP_SETTING | MessageDto | ResponseDto | Log tip setting |
| LOG_FEEDBACK | MessageDto | ResponseDto | Log feedback |
| LOG_EVALUATION | MessageDto | ResponseDto | Log evaluation |
| LOG_OPERATE | MessageDto | ResponseDto | Log operation |
| LOG_TEST_COLLECTION_GENERATE | MessageDto | ResponseDto | Log test collection |
| LOG_IMITATIVE_WRITE | MessageDto | ResponseDto | Log imitative write |
| ACTION_ABORT | MessageDto | ResponseDto | Abort action |
| ACTION_OPEN_DOCUMENT | MessageDto | ResponseDto | Open document |
| ACTION_SYNC_DOCUMENT_LIST | MessageDto | ResponseDto | Sync document list |
| ACTION_INIT | MessageDto | ResponseDto | Init action |
| UPDATE | MessageDto | ResponseDto | Update |
| SERVER_RESOURCE | MessageDto | ResponseDto | Server resource |
| REPO_STATUS | MessageDto | ResponseDto | Repo status |

### 5.2 HTTP API Endpoints (Inferred from DTOs)

Based on `ConnectConfigDto` and `DatabaseDto` patterns, the following REST endpoints are likely:

| Endpoint | Method | Request DTO | Response DTO |
|----------|--------|-------------|--------------|
| /api/sql/source/list | GET | - | List\<DatabaseDto\> |
| /api/sql/source/edit | POST | DatabaseDto | ResponseDto |
| /api/sql/test/connect | POST | ConnectConfigDto | ResponseDto |
| /api/sql/source/types | GET | - | ResponseDto |
| /api/git/search | POST | CodeSearchInfoDto | CodeSearchInfoDto |
| /api/git/repos | GET | - | CodeRepoInfoDto |

---

## 6. WebSocket Message Correspondence

### 6.1 Message Flow Architecture

```
IDE Plugin                              Agent Process
    |                                       |
    |  WebRequestDto&lt;FirstChatMessage&gt;       |
    |  &#123;type: "TALK_INTELLIGENT",           |
    |   value: &#123;inputText, codeInfo, ...&#125;&#125;  |
    |-------------------------------------->|
    |                                       |
    |  ResponseStreamDto                    |
    |  &#123;id, code, msg,                      |
    |   data: &#123;ended:false, text:"..."&#125;&#125;    |
    |<--------------------------------------|
    |                                       |
    |  ResponseStreamDto                    |
    |  &#123;id, code, msg,                      |
    |   data: &#123;ended:false, text:"..."&#125;&#125;    |
    |<--------------------------------------|
    |                                       |
    |  ResponseStreamDto                    |
    |  &#123;id, code, msg,                      |
    |   data: &#123;ended:true, text:""&#125;&#125;        |
    |<--------------------------------------|
```

### 6.2 Non-Streaming Message Flow

```
IDE Plugin                              Agent Process
    |                                       |
    |  MessageDto                           |
    |  &#123;command: "SQL_SOURCE_LIST",         |
    |   sessionId: "...", ...&#125;              |
    |-------------------------------------->|
    |                                       |
    |  ResponseDto                          |
    |  &#123;id, code: "200", msg: "ok",         |
    |   data: [DatabaseDto, ...]&#125;           |
    |<--------------------------------------|
```

### 6.3 Code Completion (Special Flow)

```
IDE Plugin                              Agent Process
    |                                       |
    |  CodeTipRequestDto (internal only)    |
    |  &#123;request: EditorRequestService,      |
    |   codeSubScriber: Flow.Subscriber,    |
    |   parentSpan: Span, ...&#125;              |
    |------> MessageDto ----->              |
    |  &#123;command: "CODE_TIP",               |
    |   content: "...",                     |
    |   path: "file.java", ...&#125;            |
    |-------------------------------------->|
    |                                       |
    |  ResponseStreamDto                    |
    |  &#123;data: &#123;text: "completion..."&#125;&#125;      |
    |<--------------------------------------|
    |                                       |
    |  CodeInlayList (via Subscriber)       |
    |<--------------------------------------|
```

### 6.4 MessageDto Serialization Behavior

Fields marked `transient` are excluded from Gson serialization:
- `chatTest` -- test-only flag
- `pid` -- process ID (client-side)
- `taskId` -- task ID (client-side)
- `requestCaseCodeDto` -- test case DTO (client-side)
- `project` -- IntelliJ Project (non-serializable)
- `text` -- accumulated stream text (client-side buffer)
- `currentLength` -- stream length counter (client-side)
- `streamStep` -- stream step counter (client-side)
- `otherObject` -- extension object (client-side)
- `inlineChatVersion` -- inline chat version (client-side)

Fields serialized to JSON (sent to agent):
- `traceparent`, `id`, `stream`, `timeStamp`, `command`, `path`, `lang`, `content`, `sessionId`, `modelCode`, `permissionCode`, `data`, `docChangeCount`, `range`, `knowledge`, `intelligent`, `relatedFiles`, `language`, `tipinfo`, `requestion`, `md5`, `isDisplay`, `directName`

### 6.5 WebRequestDto Type+Value Pattern

Several DTOs follow a `type + value` pattern matching `WebRequestDto&lt;T&gt;`:

| Outer DTO | Type Values | Inner Value DTO |
|-----------|-------------|-----------------|
| WebRequestDto\<FirstChatMessage\> | "TALK_INTELLIGENT", "INLINECHAT_CATEGORY", "INLINECHAT_DIRECT" | FirstChatMessage |
| CodeCheckFixDto | (check fix type) | CodeCheckFixDto$ValueDTO |
| CodeCheckListDto | (check list type) | CodeCheckListDto$ValueDTO |
| FirstChatMessage | (chat type) | FirstChatMessage$ValueDTO |

---

## 7. Design Patterns and Observations

### 7.1 Type+Value Discriminator Pattern

The `WebRequestDto&lt;T&gt;`, `CodeCheckFixDto`, `CodeCheckListDto`, and `FirstChatMessage` all use a `type` + `value` pattern. This is a polymorphic serialization strategy where:
- `type` is a string discriminator that identifies the payload type
- `value` is the typed payload

This pattern allows the agent process to route messages without deserializing the full payload.

### 7.2 Inheritance for Pagination

`CodeRepoInfoDto` and `CodeSearchInfoDto` extend `PageInfo`, which provides pagination fields with built-in validation (page clamping, default page size). This is a clean DDD approach where pagination is a cross-cutting concern.

### 7.3 Transient Fields for Client-Side State

`MessageDto` has 9 transient fields that exist only on the IDE side. These hold IntelliJ-specific references (`Project`), reactive streams state (`text`, `currentLength`, `streamStep`), and test infrastructure (`chatTest`, `requestCaseCodeDto`). This separation ensures the serialized form is clean and agent-process-compatible.

### 7.4 Lombok Usage

`CommentContext` and `CommentInfo` use Lombok's `@EqualsAndHashCode` annotation, evidenced by the generated `equals()`, `hashCode()`, and `canEqual()` methods in bytecode. Other DTOs use hand-written getters/setters, suggesting Lombok was adopted later in development.

### 7.5 OpenTelemetry Integration

`MessageDto.traceparent` and `CodeTipRequestDto.parentSpan` indicate distributed tracing support. The `traceparent` field follows the W3C Trace Context specification for cross-process trace propagation.

### 7.6 Gson as Serialization Framework

The use of `JsonArray` and `JsonObject` in `MessageDto`, `CommentInfo`, and `FirstChatMessage$ValueDTO` shows that some fields are intentionally left as raw JSON to avoid creating intermediate DTOs for dynamic or evolving server responses.

### 7.7 Field Visibility Inconsistency

Some DTOs use `private` fields (MessageDto, SettingsDto, CodeModel, etc.) while others use package-private (ResponseDto, ResponseStreamDto, UserInfoDto, EnterpriseDto). This suggests different developers or different development phases.

### 7.8 First-Write-Wins in CodeTipRequestDto

The `setFirstAgentDuration()` method only sets the value if the current value is 0, implementing a first-write-wins pattern. This prevents later measurements from overwriting the initial agent response time.

---

## 8. Summary Statistics

| Metric | Count |
|--------|-------|
| Total classes | 36 |
| Top-level classes | 24 |
| Inner classes ($-prefixed) | 6 |
| chat/ subpackage classes | 8 |
| search/ subpackage classes | 3 |
| Classes with default values | 4 (MessageDto, ResponseData, CodeTipRequestDto, FirstChatMessage$ValueDTO) |
| Classes extending base | 2 (CodeRepoInfoDto, CodeSearchInfoDto extend PageInfo) |
| Generic classes | 1 (WebRequestDto&lt;T&gt;) |
| Lombok-annotated classes | 2 (CommentContext, CommentInfo) |
| Total unique fields across all DTOs | ~130 |
| Transient fields | 9 (all in MessageDto) |
| Gson-specific fields (JsonArray/JsonObject) | 8 |
| OpenTelemetry fields | 2 |
| IntelliJ-specific fields | 2 (Project, EditorRequestService) |
