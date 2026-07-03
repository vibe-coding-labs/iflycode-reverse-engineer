## 1. Package Structure Overview

```
com.aicode.agent.dto/
├── (root)                          25 classes
│   ├── MessageDto                  Core WebSocket message envelope
│   ├── ResponseDto                 Generic HTTP response wrapper
│   ├── ResponseStreamDto           Streaming response wrapper
│   │   └── $ResponseData           Inner: stream chunk payload
│   ├── WebRequestDto&lt;T&gt;            Generic WebSocket request wrapper
│   ├── LoginInfo                   Agent binary update info
│   ├── UserInfoDto                 Authenticated user session
│   ├── SettingsDto                 Plugin configuration state
│   ├── ConnectConfigDto            Database connection config
│   ├── CodeCheckDto                Code check request
│   ├── CodeCheckFixDto             Code check fix result
│   │   └── $ValueDTO               Inner: fix detail payload
│   ├── CodeCheckListDto            Code check list result
│   │   └── $ValueDTO               Inner: list detail payload
│   ├── CodeCheckOriginDto          Code check origin file
│   │   └── $ErrListDTO             Inner: error entry detail
│   ├── CodeModel                   AI model descriptor
│   ├── CodeRepoInfoDto             Paged repository info (extends PageInfo)
│   ├── CodeSearchInfoDto           Paged code search result (extends PageInfo)
│   ├── CodeTipRequestDto           Code completion request context
│   ├── DatabaseDto                 Database source descriptor
│   ├── EnterpriseDto               Enterprise/tenant info
│   ├── FunctionModelInfo           Permission-to-model mapping
│   ├── SysUrlDto                   System URL configuration
│   └── TipInfoDto                  User tip/guide metadata
├── chat/                           8 classes
│   ├── CodeInfoDto                 Source code context for chat
│   │   └── $RangeDTO               Inner: cursor position
│   ├── CommentContext              Comment generation context (Lombok @EqualsAndHashCode)
│   ├── CommentInfo                 Single comment entry (Lombok @EqualsAndHashCode)
│   ├── FirstChatMessage            Initial chat message envelope
│   │   └── $ValueDTO               Inner: chat message payload
│   ├── PresentationDataDto         Inline chat presentation data
│   └── SqlInfoDto                  SQL chat context
└── search/                         3 classes
    ├── CodeSearchDto               Code search result entry
    ├── PageInfo                    Pagination base class
    └── ReposInfoDto                Repository metadata entry
```

---

## 2. DTO Hierarchy Diagram (ASCII Art)

```
                              Object
                                |
        +-----------+-----------+-----------+-----------+-----------+
        |           |           |           |           |           |
    MessageDto  ResponseDto  ResponseStreamDto  WebRequestDto&lt;T&gt;  LoginInfo
        |           |           |               |                 |
        |           |       +---+---+           |             UserInfoDto
        |           |       |       |           |                 |
        |        (id,code,  id   ResponseData  (type,value)   SettingsDto
        |         msg,data)       |                             |
        |                    (ended,text,                  ConnectConfigDto
        |                   showKeyMapTipFlag)                   |
        |                                                  CodeCheckDto
        |                                                      |
        |                                          CodeCheckFixDto
        |                                                |
        |                                          CodeCheckListDto
        |                                                |
        |                                        CodeCheckOriginDto
        |                                                |
        |                                           CodeModel
        |                                                |
        |                                       FunctionModelInfo
        |                                                |
        |                                          DatabaseDto
        |                                                |
        |                                        EnterpriseDto
        |                                                |
        |                                          SysUrlDto
        |                                                |
        |                                         TipInfoDto
        |                                                |
        |                                     CodeTipRequestDto
        |                                                |
        +------------------+-----------------+-----------+-----------+
                           |                 |                       |
                    PageInfo          CodeRepoInfoDto         CodeSearchInfoDto
                       |                (extends)                  (extends)
                       |                    |                          |
                (currentPage,       List&lt;ReposInfoDto&gt;     List&lt;CodeSearchDto&gt;
                 pageSize,                                   + type, count
                 total,
                 totalPage)

    chat/ subpackage:
    ================
    Object
      |
      +-- CodeInfoDto
      |     +-- $RangeDTO (line, character)
      |
      +-- CommentContext  [@EqualsAndHashCode]
      |     - md5: String
      |     - methods: List&lt;CommentInfo&gt;
      |
      +-- CommentInfo  [@EqualsAndHashCode]
      |     - name, textContext, index, range (JsonArray), bodyRange (JsonArray)
      |
      +-- FirstChatMessage
      |     +-- $ValueDTO
      |
      +-- PresentationDataDto
      |     - line, character, type, codeInfoDto
      |
      +-- SqlInfoDto
            - database, inputText, sourceId, tables

    Cross-references:
    ================
    MessageDto ──────────> CodeInfoDto$RangeDTO  (range field)
    MessageDto ──────────> TipInfoDto            (tipinfo field)
    CodeCheckDto ────────> CodeInfoDto           (codeInfo field)
    CodeCheckFixDto$ValueDTO ──> CodeInfoDto     (codeInfo field)
    CodeCheckOriginDto$ErrListDTO ──> CodeInfoDto$RangeDTO (range field)
    UserInfoDto ─────────> CodeModel             (codeModelDtoList)
    UserInfoDto ─────────> EnterpriseDto         (enterpriseDto)
    UserInfoDto ─────────> SysUrlDto             (sysUrls)
    FunctionModelInfo ───> CodeModel             (codeModelList)
    DatabaseDto ─────────> ConnectConfigDto      (formData)
    CodeRepoInfoDto ─────> PageInfo              (extends)
                          ──> ReposInfoDto        (content)
    CodeSearchInfoDto ───> PageInfo              (extends)
                          ──> CodeSearchDto       (content)
    FirstChatMessage$ValueDTO ──> CodeInfoDto    (codeInfo)
                                 SqlInfoDto       (sqlInfo)
    PresentationDataDto ──> CodeInfoDto          (codeInfoDto)
```

---
