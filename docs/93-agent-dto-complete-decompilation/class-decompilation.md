## 3. Complete Class-by-Class Decompilation

### 3.1 MessageDto (Core WebSocket Message Envelope)

**Source:** `MessageDto.java`
**Visibility:** public
**Significance:** The primary data carrier for all WebSocket communication between the IDE plugin and the agent process.

#### Fields

| Field | Type | Default | Transient | Purpose |
|-------|------|---------|-----------|---------|
| traceparent | String | null | no | OpenTelemetry trace propagation |
| id | String | null | no | Message unique identifier |
| stream | boolean | **true** | no | Whether response should stream |
| timeStamp | long | Instant.now().toEpochMilli() | no | Message creation timestamp |
| command | String | null | no | CommandEnum value (e.g. "TALK_INTELLIGENT") |
| path | String | null | no | File path context |
| lang | String | null | no | Programming language |
| content | String | null | no | Primary text content |
| sessionId | String | null | no | Chat session identifier |
| modelCode | String | null | no | Selected AI model code |
| permissionCode | String | null | no | PermissionEnum value |
| data | Object | null | no | Arbitrary payload (polymorphic) |
| docChangeCount | Integer | null | no | Document change counter |
| range | List\<CodeInfoDto$RangeDTO\> | null | no | Cursor/selection range |
| chatTest | boolean | false | **yes** | Test mode flag |
| pid | String | null | **yes** | Process ID |
| taskId | String | null | **yes** | Task tracking ID |
| requestCaseCodeDto | RequestCaseCodeDto | null | **yes** | Test case code DTO |
| project | Project | null | **yes** | IntelliJ project reference |
| knowledge | Object | null | no | Knowledge base results |
| text | StringBuffer | null | **yes** | Accumulated stream text |
| intelligent | JsonArray | null | no | Intelligent suggestion metadata |
| relatedFiles | JsonArray | null | no | Related files metadata |
| language | String | null | no | Programming language (alt field) |
| tipinfo | TipInfoDto | null | no | User tip information |
| requestion | String | null | no | Re-question text |
| md5 | String | null | no | Content MD5 hash |
| currentLength | int | **1** | **yes** | Current stream text length |
| streamStep | int | **1** | **yes** | Stream step counter |
| isDisplay | AtomicBoolean | **new AtomicBoolean(false)** | no | Display state flag |
| otherObject | Object | null | **yes** | Extension object |
| directName | String | null | no | Direct command name |
| inlineChatVersion | int | 0 | **yes** | Inline chat protocol version |

#### Constructors

- `MessageDto()`: Initializes `stream=true`, `timeStamp=now()`, `currentLength=1`, `streamStep=1`, `isDisplay=new AtomicBoolean(false)`
- `MessageDto(String id, String command)`: Same defaults + sets id and command

#### Key Methods

- `initModelInfo()`: Resolves `command` -> `CommandEnum` -> `PermissionEnum` -> sets `permissionCode` and `modelCode`. For `TALK_INTELLIGENT`, parses the `intelligent` JsonArray to find the actual command type, then reads model code from `AICodeSettingsState`. For `INLINE_CHAT` permission, reads `inlineChatModelCode` from settings.
- `setModelInfo(CommandEnum)`: Private helper. Maps CommandEnum -> PermissionEnum -> permissionCode. For `TALK_INTELLIGENT` permission, uses `AICodeSettingsState.modelCode`. For `INLINE_CHAT` permission, uses `AICodeSettingsState.inlineChatModelCode`.

---

### 3.2 ResponseDto (Generic HTTP Response)

**Source:** `ResponseDto.java`
**Visibility:** public
**Fields:** package-private (no `private` modifier)

| Field | Type | Purpose |
|-------|------|---------|
| id | String | Response correlation ID |
| code | String | Status code (e.g. "200", "500") |
| msg | String | Status message |
| data | Object | Polymorphic response payload |

No default values. Simple POJO with getters/setters.

---

### 3.3 ResponseStreamDto (Streaming Response Envelope)

**Source:** `ResponseStreamDto.java`
**Visibility:** public
**Fields:** package-private

| Field | Type | Purpose |
|-------|------|---------|
| id | String | Response correlation ID |
| code | String | Status code |
| msg | String | Status message |
| data | ResponseStreamDto$ResponseData | Typed stream payload |

---

### 3.4 ResponseStreamDto$ResponseData (Stream Chunk Payload)

**Source:** `ResponseStreamDto.java` (inner class)
**Visibility:** public

| Field | Type | Default | Purpose |
|-------|------|---------|---------|
| ended | boolean | false | Whether stream is complete |
| text | String | **""** (empty string) | Incremental text content |
| showKeyMapTipFlag | boolean | **false** | Show keyboard shortcut tip |

Contains `this$0` reference to enclosing `ResponseStreamDto`.

---

### 3.5 WebRequestDto\<T\> (Generic WebSocket Request)

**Source:** `WebRequestDto.java`
**Visibility:** public, generic `T`

| Field | Type | Purpose |
|-------|------|---------|
| type | String | Command/action type discriminator |
| value | T | Typed payload |

This is the standard envelope for WebSocket messages sent to the agent. The `type` field maps to `CommandEnum` values. The `value` field carries the command-specific DTO.

---

### 3.6 LoginInfo (Agent Binary Update Info)

**Source:** `LoginInfo.java`
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| current | String | Current agent version |
| update | String | Available update version |
| name | String | Agent binary filename |
| file | String | Download URL/path |
| dir | String | Installation directory |
| md5 | String | Binary MD5 checksum |

Used by the `LOGIN_INFO` command to deliver agent binary update information.

---

### 3.7 UserInfoDto (Authenticated User Session)

**Source:** `UserInfoDto.java`
**Visibility:** public
**Fields:** package-private

| Field | Type | Purpose |
|-------|------|---------|
| clientId | String | Client identifier |
| user | String | Username/email |
| token | String | Authentication token |
| codeModelDtoList | List\<CodeModel\> | Available AI models |
| enterpriseDto | EnterpriseDto | Enterprise/tenant info |
| tokenPath | String | Token storage path |
| sysUrls | SysUrlDto | System URLs |
| packageCode | String | Subscription package code |
| packageName | String | Subscription package name |
| reLogin | boolean | Force re-login flag |

Returned by the `USER_LOGIN` command response.

---

### 3.8 SettingsDto (Plugin Configuration State)

**Source:** `SettingsDto.java`
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| autoTriggerOnPause | boolean | Auto-trigger code completion on pause |
| autoTriggerTimeDelay | Integer | Delay in ms before auto-trigger |
| generateCodeMode | String | Code generation mode |
| codeCompleteDisableLang | String[] | Languages with code completion disabled |
| sendMessageType | String | WebSocket vs HTTP message type |
| javaTestFramework | String | Java test framework (JUnit/TestNG) |
| javaMockFramework | String | Java mock framework (Mockito/etc) |
| lineToolsType | String | Line tools mode |
| lineToolsPermissionDocComments | boolean | Doc comment generation permission |
| lineToolsPermissionLineComments | boolean | Line comment generation permission |
| lineToolsPermissionComments | boolean | Comment generation permission |
| lineToolsPermissionFunctionSplit | boolean | Function split permission |
| lineToolsPermissionCodeOptimization | boolean | Code optimization permission |
| lineToolsPermissionUnitTesting | boolean | Unit testing permission |
| openFunctionSplit | boolean | Function split feature toggle |
| openCodeOptimization | boolean | Code optimization feature toggle |
| openIFlyTest | boolean | iFlyTest feature toggle |
| openInlineChat | boolean | Inline chat feature toggle |
| openIFlyDBA | boolean | iFlyDBA feature toggle |
| openIFlyOps | boolean | iFlyOps feature toggle |
| openIFlyPm | boolean | iFlyPm feature toggle |
| openCodeEnhance | boolean | Code enhance feature toggle |
| inlineCompletionInputStyle | String | Inline completion input style |
| openAutoUpdate | boolean | Auto-update feature toggle |
| defaultLanguage | String | Default programming language |

Used by the `GENERAL_SETTING` command to sync plugin settings.

---

### 3.9 ConnectConfigDto (Database Connection Config)

**Source:** `ConnectConfigDto.java`
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| id | String | Connection identifier |
| client | String | Database client type (MySQL/PostgreSQL/etc) |
| host | String | Database host |
| port | String | Database port |
| user | String | Database username |
| password | String | Database password |
| database | String | Database name |

Has a convenience constructor `ConnectConfigDto(String client, String host, String port)`.

---

### 3.10 CodeCheckDto (Code Check Request)

**Source:** `CodeCheckDto.java`
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| codeFragment | String | Code snippet to check |
| errorType | String | Error category |
| errorMessage | String | Error description |
| codeInfo | CodeInfoDto | Source file context |

Used by the `CODE_CHECK` command.

---

### 3.11 CodeCheckFixDto (Code Check Fix Result)

**Source:** `CodeCheckFixDto.java`
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| type | String | Result type discriminator |
| value | CodeCheckFixDto$ValueDTO | Fix detail payload |

Follows the `WebRequestDto`-style `type+value` pattern.

---

### 3.12 CodeCheckFixDto$ValueDTO (Fix Detail)

**Source:** `CodeCheckFixDto.java` (inner class)
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| id | String | Fix identifier |
| codeInfo | CodeInfoDto | Source file context with fix |
| errorType | String | Error category |
| errorMessage | String | Error description |

---

### 3.13 CodeCheckListDto (Code Check List Result)

**Source:** `CodeCheckListDto.java`
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| type | String | Result type discriminator |
| value | CodeCheckListDto$ValueDTO | List detail payload |

---

### 3.14 CodeCheckListDto$ValueDTO (List Detail)

**Source:** `CodeCheckListDto.java` (inner class)
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| status | Boolean | Check status (success/failure) |
| message | String | Status message |
| data | Object | Polymorphic check result data |

---

### 3.15 CodeCheckOriginDto (Code Check Origin File)

**Source:** `CodeCheckOriginDto.java`
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| path | String | File path |
| name | String | File name |
| errList | List\<CodeCheckOriginDto$ErrListDTO\> | List of errors in file |

---

### 3.16 CodeCheckOriginDto$ErrListDTO (Error Entry)

**Source:** `CodeCheckOriginDto.java` (inner class)
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| codeFragment | String | Error code snippet |
| errorType | String | Error category |
| errorMessage | String | Error description |
| range | List\<CodeInfoDto$RangeDTO\> | Error location range |

---

### 3.17 CodeModel (AI Model Descriptor)

**Source:** `CodeModel.java`
**Visibility:** public

| Field | Type | Default | Purpose |
|-------|------|---------|---------|
| modelId | String | null | Model unique identifier |
| modelCode | String | null | Model code (used in MessageDto.modelCode) |
| modelName | String | null | Display name |
| checked | boolean | false | Whether currently selected |
| originalModelName | String | null | Original model name |
| tokenExhausted | boolean | false | Token quota exhausted flag |

`toString()` returns `modelName`.

---

### 3.18 CodeRepoInfoDto (Paged Repository Info)

**Source:** `CodeRepoInfoDto.java`
**Visibility:** public
**Extends:** `PageInfo`

| Field | Type | Purpose |
|-------|------|---------|
| (inherited) | (PageInfo fields) | currentPage, pageSize, total, totalPage |
| content | List\<ReposInfoDto\> | Repository entries |

Constructors:
- `CodeRepoInfoDto()`
- `CodeRepoInfoDto(List&lt;ReposInfoDto&gt; content)`
- `CodeRepoInfoDto(Integer currentPage, Integer pageSize, Integer total, Integer totalPage, List&lt;ReposInfoDto&gt; content)`

Used by the `GIT_USER_REPOS` command.

---

### 3.19 CodeSearchInfoDto (Paged Code Search Result)

**Source:** `CodeSearchInfoDto.java`
**Visibility:** public
**Extends:** `PageInfo`

| Field | Type | Purpose |
|-------|------|---------|
| (inherited) | (PageInfo fields) | currentPage, pageSize, total, totalPage |
| content | List\<CodeSearchDto\> | Search result entries |
| type | String | Search type |
| count | Integer | Total match count |

Used by the `GIT_SEARCH` command.

---

### 3.20 CodeTipRequestDto (Code Completion Request Context)

**Source:** `CodeTipRequestDto.java`
**Visibility:** public

| Field | Type | Default | Purpose |
|-------|------|---------|---------|
| request | EditorRequestService | null | Editor request service reference |
| codeSubScriber | Flow.Subscriber\<List\<CodeInlayList\>\> | null | Reactive code subscriber |
| parentSpan | Span (OpenTelemetry) | null | Tracing parent span |
| startTime | Long | null | Request start timestamp |
| lastReplacementText | String | **""** | Previous completion text |
| firstAgentDuration | long | **0** | First agent response duration (ms) |

`setFirstAgentDuration(long)`: Only sets the value if current value is 0 (first-write-wins semantics).

---

### 3.21 DatabaseDto (Database Source Descriptor)

**Source:** `DatabaseDto.java`
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| id | String | Database source identifier |
| formData | ConnectConfigDto | Connection configuration |
| databases | List\<String\> | List of database names |
| status | Boolean | Connection status |
| errMsg | String | Error message |
| createTime | Long | Creation timestamp |
| updateTime | Long | Last update timestamp |

Used by `SQL_SOURCE_LIST` and `SQL_SOURCE_EDIT` commands.

---

### 3.22 EnterpriseDto (Enterprise/Tenant Info)

**Source:** `EnterpriseDto.java`
**Visibility:** public
**Fields:** package-private

| Field | Type | Purpose |
|-------|------|---------|
| enterpriseId | String | Enterprise identifier |
| enterpriseName | String | Enterprise display name |
| userId | String | User ID within enterprise |

---

### 3.23 FunctionModelInfo (Permission-to-Model Mapping)

**Source:** `FunctionModelInfo.java`
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| permissionCode | String | PermissionEnum value |
| permissionName | String | Permission display name |
| language | String | Programming language filter |
| codeModelList | List\<CodeModel\> | Available models for this permission |

Maps a permission (e.g. "TALK_INTELLIGENT") to the AI models that support it.

---

### 3.24 SysUrlDto (System URL Configuration)

**Source:** `SysUrlDto.java`
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| feedbackUrl | String | User feedback URL |
| maintainRepoUrl | String | Repository maintenance URL |
| codeSearchServerUrl | String | Code search server URL |
| officialWebsiteUrl | String | Official website URL |
| codeKnowledgeWebUrl | String | Code knowledge base URL |
| userCenterWebUrl | String | User center URL |

---

### 3.25 TipInfoDto (User Tip/Guide Metadata)

**Source:** `TipInfoDto.java`
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| user | String | User identifier |
| platform | String | Platform identifier |
| isShowOperateGuide | Boolean | Whether to show operation guide |

Note: Field name is `isShowOperateGuide` but getter is `getShowOperateGuide()` (Gson serialization name = `isShowOperateGuide`).

---

### 3.26 CodeInfoDto (Source Code Context for Chat)

**Source:** `CodeInfoDto.java` (chat/ subpackage)
**Visibility:** public

| Field | Type | Transient | Purpose |
|-------|------|-----------|---------|
| content | String | no | Selected code content |
| range | List\<CodeInfoDto$RangeDTO\> | no | Selection range |
| bodyRange | List\<CodeInfoDto$RangeDTO\> | **yes** | Method body range |
| fileName | String | no | Source file name |
| path | String | no | Source file path |
| language | String | no | Programming language |
| allContent | String | no | Full file content |

Central DTO used across chat, code check, and inline chat features. The `bodyRange` is transient (not serialized to JSON) -- it is a client-side only field for tracking method body positions.

---

### 3.27 CodeInfoDto$RangeDTO (Cursor Position)

**Source:** `CodeInfoDto.java` (inner class)
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| line | Integer | Line number (0-based) |
| character | Integer | Character offset in line |

Constructor: `RangeDTO(Integer line, Integer character)`.

Used throughout the codebase to represent cursor positions and selection ranges. A selection is typically represented as a `List&lt;RangeDTO&gt;` with two entries: [start, end].

---

### 3.28 CommentContext (Comment Generation Context)

**Source:** `CommentContext.java` (chat/ subpackage)
**Visibility:** public
**Lombok:** `@EqualsAndHashCode` (generates `equals()`, `hashCode()`, `canEqual()`)

| Field | Type | Purpose |
|-------|------|---------|
| md5 | String | File content hash (cache key) |
| methods | List\<CommentInfo\> | Methods to generate comments for |

The Lombok-generated `equals()` and `hashCode()` use `md5` and `methods` fields. The `canEqual()` method ensures only `CommentContext` instances can be equal.

---

### 3.29 CommentInfo (Single Comment Entry)

**Source:** `CommentInfo.java` (chat/ subpackage)
**Visibility:** public
**Lombok:** `@EqualsAndHashCode`

| Field | Type | Purpose |
|-------|------|---------|
| name | String | Method/function name |
| textContext | String | Method source text |
| index | int | Method order index |
| range | JsonArray | Selection range (as raw JSON) |
| bodyRange | JsonArray | Method body range (as raw JSON) |

Unlike `CodeInfoDto` which uses typed `List&lt;RangeDTO&gt;`, `CommentInfo` stores ranges as `JsonArray` -- this is because the range data comes directly from the agent process in JSON form and is not deserialized into typed objects.

---

### 3.30 FirstChatMessage (Initial Chat Message Envelope)

**Source:** `FirstChatMessage.java` (chat/ subpackage)
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| type | String | Command type discriminator |
| value | FirstChatMessage$ValueDTO | Message payload |

Follows the `WebRequestDto`-style `type+value` pattern. Used as the first message in a chat session.

---

### 3.31 FirstChatMessage$ValueDTO (Chat Message Payload)

**Source:** `FirstChatMessage.java` (inner class)
**Visibility:** public

| Field | Type | Default | Purpose |
|-------|------|---------|---------|
| inputText | String | null | User input text |
| id | String | null | Message identifier |
| sessionId | String | null | Chat session ID |
| type | String | null | Chat type |
| codeInfo | CodeInfoDto | null | Source code context |
| sqlInfo | SqlInfoDto | null | SQL context (for SQL chat) |
| knowledge | JsonArray | null | Knowledge base results |
| errorType | boolean | **false** | Error flag (misleading name) |
| errorMessage | String | null | Error message |
| intelligent | JsonArray | null | Intelligent suggestion metadata |
| relatedFiles | JsonArray | null | Related files metadata |
| data | JsonObject | null | Additional data |
| language | String | null | Programming language |
| code | String | null | Code content |

This is the richest DTO in the chat subsystem. It carries all context needed for the first message in a chat session, including code context, SQL context, knowledge base results, and intelligent suggestions.

---

### 3.32 PresentationDataDto (Inline Chat Presentation)

**Source:** `PresentationDataDto.java` (chat/ subpackage)
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| line | int | Cursor line |
| character | int | Cursor character |
| type | String | Presentation type |
| codeInfoDto | CodeInfoDto | Source code context |

Used for inline chat positioning and context.

---

### 3.33 SqlInfoDto (SQL Chat Context)

**Source:** `SqlInfoDto.java` (chat/ subpackage)
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| database | String | Database name |
| inputText | String | User SQL query text |
| sourceId | String | Database source identifier |
| tables | List\<String\> | Selected table names |

Constructor: `SqlInfoDto(String database, String inputText, String sourceId, List&lt;String&gt; tables)`.

Used by SQL-related chat commands (`SQL_GENERATE_TALK`, `SQL_OPTIMIZE`).

---

### 3.34 CodeSearchDto (Code Search Result Entry)

**Source:** `CodeSearchDto.java` (search/ subpackage)
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| id | String | Result identifier |
| repoUrl | String | Repository URL |
| repoName | String | Repository name |
| repoType | String | Repository type (git/svn) |
| branch | String | Branch name |
| filePath | String | File path within repo |
| fileName | String | File name |
| language | String | Programming language |
| isOpen | Integer | Open source flag (0/1) |
| isPublic | Integer | Public repo flag (0/1) |
| startRow | Integer | Match start line |
| endRow | Integer | Match end line |
| score | BigDecimal | Relevance score |
| code | String | Matched code snippet |
| codeLength | Integer | Code length |
| codeVector | Double | Code vector (embedding) |
| createTime | Long | Index timestamp |

Has a full-args constructor with all 17 fields. `toString()` includes all fields.

---

### 3.35 PageInfo (Pagination Base Class)

**Source:** `PageInfo.java` (search/ subpackage)
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| currentPage | Integer | Current page number |
| pageSize | Integer | Items per page |
| total | Integer | Total item count |
| totalPage | Integer | Total page count |

**Business logic in setters:**
- `setCurrentPage(Integer)`: Clamps to minimum 1 and maximum of `totalPage`
- `setPageSize(Integer)`: Defaults to 10 if null

Constructor: `PageInfo(Integer currentPage, Integer pageSize, Integer total, Integer totalPage)`.

Extended by `CodeRepoInfoDto` and `CodeSearchInfoDto`.

---

### 3.36 ReposInfoDto (Repository Metadata Entry)

**Source:** `ReposInfoDto.java` (search/ subpackage)
**Visibility:** public

| Field | Type | Purpose |
|-------|------|---------|
| id | String | Repository identifier |
| repoUrl | String | Repository URL |
| repoName | String | Repository name |
| branch | String | Default branch |
| repoType | String | Repository type |

Constructor: `ReposInfoDto(String id, String repoUrl, String repoName, String branch, String repoType)`.

---
