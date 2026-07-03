## 6. CommandEnum 与 API 路由映射

### 6.1 CommandEnum 枚举值 (确认)

> 来源: `com.aicode.agent.enums.CommandEnum` 反编译
> 注: 枚举值的 type/desc 字段经过混淆 (H() 解密), 以下映射来自 webpack CHAT_APIS

| CommandEnum | AgentModuleEnum | API 路由键 | 场景 |
|-------------|----------------|-----------|------|
| CODE_COMPLETE | CODE_COMPLETE | codeGenerate | COMPLETE_CODE_WITH_CONTEXT |
| TALK_ASK | CHAT | talkAsk | - |
| TALK_PREDICT | CHAT | talkAskSync | - |
| TALK_RESEND | CHAT | talkAsk | - |
| SQL_GENERATE | SQL_CHAT | generateSql | GENERATE_SQL |
| SQL_GENERATE_TALK | SQL_CHAT | generateSql | GENERATE_SQL |
| SQL_OPTIMIZE | SQL_CHAT | optimizeSql | OPTIMIZE_SQL |
| SQL_OPTIMIZE_TALK | SQL_CHAT | optimizeSql | OPTIMIZE_SQL |
| GIT_COMMIT_MESSAGE | GIT_REVIEW | generateCommitMessage | GENERATE_COMMIT_MESSAGE |
| GIT_REVIEW | GIT_REVIEW | review | REVIEW_CODE |
| CODE_OPTIMIZE | CHAT | codeOptimize | - |
| CODE_SPLIT | CHAT | codeSplit | - |
| CODE_COMMENT | CHAT | talkAsk | - |
| CODE_INLINE_COMMENT | CHAT | inlineComment | - |
| CODE_EXPLAIN | CHAT | talkAsk | - |
| CODE_DEBUG | CHAT | talkAsk | - |
| CODE_CHECK | CODE_CHECK | talkAsk | - |
| CODE_TEST | UNIT_TEST | talkAsk | - |
| CODE_TEST_CASE | CODE_TEST_TEMPLATE | testCase | - |
| CODE_TEST_CODE | UNIT_TEST | testCode | - |
| CODE_GENERATE_TEST_CASE | UNIT_TEST | testCode | - |
| CODE_DEMAND_ANALYSIS | CHAT | talkAsk | - |
| CODE_DEMAND_SPLITTING | CHAT | talkAsk | - |
| CODE_DEMAND_TEST | CHAT | talkAsk | - |
| USER_LOGIN | LOGIN | loginByAccount | - |
| USER_LOGOUT | LOGIN | exitLogin | - |
| USER_LOGIN_CHECK | LOGIN | validToken | - |
| USER_PERMISSION | LOGIN | getFuncModelList | - |
| USER_MODEL_LIST | LOGIN | getFuncModelList | - |
| USER_FEEDBACK_CATEGORY | COMMON | queryCategory | - |
| USER_KNOWLEDGE_LIST | CODE_SEARCH | knowledgeList | - |
| RAG_LANGUAGES | CODE_SEARCH | gitLangList | - |
| GIT_USER_REPOS | CODE_SEARCH | gitRepos | - |
| GIT_SEARCH | CODE_SEARCH | gitCodeSearch | - |
| GIT_DIFF | GIT_REVIEW | review | - |
| GIT_SAVE_TOKEN | CODE_SEARCH | codeKnowledgeUpdateGitToken | - |
| GIT_REPO_AUTHORIZE | CODE_SEARCH | authPersonalCodeKnowledge | - |
| GIT_CODE_KNOWLEDGE_RE_INDEX | CODE_SEARCH | codeKnowledgeReVectorized | - |
| GIT_CODE_KNOWLEDGE_REPO_STATUS | CODE_SEARCH | codeKnowledgeStatus | - |
| REPO_STATUS | CODE_SEARCH | repoKeyEnable | - |
| GENERAL_SETTING | COMMON | generalSetting | - |
| INIT | INIT | - | - |
| ACTION_INIT | INIT | - | - |
| ERROR | COMMON | - | - |
| UPDATE | COMMON | checkUpdate | - |
| DIALOG_ACCEPT | INLINE_CHAT | - | - |
| DIALOG_REJECT | INLINE_CHAT | - | - |
| DIALOG_DIFF | INLINE_CHAT | - | - |
| DIALOG_EDIT | INLINE_CHAT | - | - |
| DIALOG_ABORT | INLINE_CHAT | - | - |
| INLINECHAT_CATEGORY | INLINE_CHAT | inlineChat | INLINE_CHAT_SELECTED |
| INLINECHAT_DIRECT | INLINE_CHAT | inlineChat | INLINE_CHAT_DIRECT |
| INLINECHAT_GET_FUNC_RANGE | INLINE_CHAT | - | - |
| LOG_ACCEPT | LOG | userAction | - |
| LOG_REJECT | LOG | codeReject | - |
| LOG_REJECT_ESC | LOG | codeReject | - |
| LOG_ACCEPT_LINE | LOG | userAction | - |
| LOG_ACCEPT_WORD | LOG | userAction | - |
| LOG_ACCEPT_COUNT | LOG | userAction | - |
| LOG_EVALUATION | LOG | chatEvaluation | - |
| LOG_FEEDBACK | LOG | chatFeedback | - |
| LOG_TIP_SETTING | LOG | - | - |
| LOG_OPERATE | LOG | userAction | - |
| LOG_IMITATIVE_WRITE | LOG | userAction | - |
| LOG_TEST_COLLECTION_GENERATE | LOG | unitTestCollectionGenerate | - |
| LOG_TEST_COLLECTION_COMMIT | LOG | unitTestCollectionCommit | - |
| LOG_DISPLAY | LOG | - | - |
| SERVER_RESOURCE | COMMON | serverResourceInfo | - |
| LOGIN_INFO | LOGIN | loginStatus | - |
| CODE_BATCH_UNIT_TEST_CREATE | BATCH_UNIT_TEST | batchUnitTestCreate | - |
| CODE_BATCH_UNIT_TEST_LIST | BATCH_UNIT_TEST | batchUnitTestList | - |
| CODE_BATCH_UNIT_TEST_DELETE | BATCH_UNIT_TEST | batchUnitTestDelete | - |
| CODE_BATCH_UNIT_TEST_DOWNLOAD | BATCH_UNIT_TEST | batchUnitTestDownload | - |
| CODE_BATCH_UNIT_TEST_CANCEL | BATCH_UNIT_TEST | batchUnitTestCancel | - |
| CODE_FAULT_ANALYSIS | CHAT | talkAsk | - |
| CODE_DEBUG_DUPLICATE | CHAT | talkAsk | - |
| SQL_SOURCE_LIST | SQL_CHAT | - | - |
| SQL_SOURCE_EDIT | SQL_CHAT | - | - |
| SQL_SOURCE_DELETE | SQL_CHAT | - | - |
| SQL_SOURCE_TYPES | SQL_CHAT | - | - |
| SQL_TABLE_LIST | SQL_CHAT | - | - |
| SQL_TEST_CONNECT | SQL_CHAT | - | - |
| TALK_INTELLIGENT | CHAT | talkAsk | - |
| TALK_HISTORY | CHAT | - | - |
| TALK_LIST | CHAT | - | - |
| TALK_DELETE | CHAT | - | - |
| TALK_CLEAR | CHAT | - | - |
| TALK_RECOMMEND_GAMEPLAY | CHAT | recommendations | - |
| TALK_DOWNLOAD_MARKDOWN_TABLE | CHAT | - | - |
| TALK_KNOWLEDGE | CHAT | - | - |
| USER_PARSE_WEB_URL | CODE_SEARCH | parseWebDocument | - |
| USER_CAN_CODE_ENHANCE | CODE_SEARCH | - | - |
| USER_VERSION | LOGIN | checkUpdate | - |
| ACTION_ABORT | COMMON | - | - |
| ACTION_OPEN_DOCUMENT | COMMON | - | - |
| ACTION_SYNC_DOCUMENT_LIST | COMMON | - | - |
| FEEDBACK_CATEGORY_INFO | COMMON | queryCategory | - |
| MODEL_LIST_TIMER | LOGIN | getFuncModelList | - |
| TEST_MAKE_CODE | UNIT_TEST | testCode | - |
| TEST_MAKE_CASE | CODE_TEST_TEMPLATE | testCase | - |
| TEST_MAKE_CASE_JAVA | CODE_TEST_TEMPLATE | testCase | - |

### 6.2 AgentModuleEnum 模块定义 (确认)

| 枚举值 | 说明 |
|--------|------|
| INIT | 初始化 |
| LOGIN | 登录认证 |
| CHAT | 对话 |
| SQL_CHAT | SQL对话 |
| CODE_COMPLETE | 代码补全 |
| CODE_CHECK | 代码检查 |
| UNIT_TEST | 单元测试 |
| CODE_TEST_TEMPLATE | 测试模板 |
| BATCH_UNIT_TEST | 批量单元测试 |
| GIT_REVIEW | Git评审 |
| CODE_SEARCH | 代码搜索 |
| LOG | 日志埋点 |
| COMMON | 通用 |
| INLINE_CHAT | 内联对话 |
| SERVER_RESOURCE | 服务器资源 |

---

## 7. Token 限制与折扣配置 (确认)

| 命令 | Token限制 | 折扣(%) |
|------|-----------|---------|
| CODE:COMPLETE | 8000 | 67 |
| CODE:OPTIMIZE | 8000 | 67 |
| CODE:SPLIT | 8000 | 67 |
| CODE:INLINE_COMMENT | 8000 | 62.5 |
| CODE:COMMENT | 8000 | 100 |
| CODE:CHECK | 8000 | 100 |
| CODE:DEBUG | 8000 | 80 |
| CODE:DEBUG_DUPLICATE | 8000 | 80 |
| CODE:FIX | 8000 | 70 |
| CODE:TEST | 8000 | 80 |
| CODE:TEST_CASE | 8000 | 100 |
| CODE:TEST_CODE | 8000 | 100 |
| GIT:COMMIT_MESSAGE | 8000 | 100 |
| GIT:REVIEW | 8000 | 100 |
| DIALOG:TALK_ROUND | 8000 | 80 |
| DIALOG:TALK_TOTAL | 8000 | 80 |

---

## 8. 关键 DTO 结构参考

### 8.1 MessageDto (确认)

> 核心请求消息结构, 用于 WebSocket 和 HTTP 请求

```json
&#123;
  "traceparent": "string",
  "id": "string",
  "stream": false,
  "timeStamp": 0,
  "command": "string",
  "path": "string",
  "lang": "string",
  "content": "string",
  "sessionId": "string",
  "modelCode": "string",
  "permissionCode": "string",
  "data": &#123;&#125;,
  "docChangeCount": 0,
  "range": [&#123;"line": 0, "character": 0&#125;],
  "knowledge": &#123;&#125;,
  "intelligent": [],
  "relatedFiles": [],
  "language": "string",
  "tipinfo": &#123;"user": "", "platform": "", "isShowOperateGuide": false&#125;,
  "requestion": "string",
  "md5": "string",
  "directName": "string",
  "inlineChatVersion": 0
&#125;
```

> 来源: `com.aicode.agent.dto.MessageDto` 确认

### 8.2 CodeInfoDto (确认)

```json
&#123;
  "content": "string",
  "range": [&#123;"line": 0, "character": 0&#125;],
  "fileName": "string",
  "path": "string",
  "language": "string",
  "allContent": "string"
&#125;
```

> 来源: `com.aicode.agent.dto.chat.CodeInfoDto` 确认

### 8.3 SqlInfoDto (确认)

```json
&#123;
  "database": "string",
  "inputText": "string",
  "sourceId": "string",
  "tables": ["string"]
&#125;
```

> 来源: `com.aicode.agent.dto.chat.SqlInfoDto` 确认

### 8.4 FirstChatMessage (确认)

```json
&#123;
  "type": "string",
  "value": &#123;
    "inputText": "string",
    "id": "string",
    "sessionId": "string",
    "type": "string",
    "codeInfo": &#123;"content": "", "range": [], "fileName": "", "path": "", "language": "", "allContent": ""&#125;,
    "sqlInfo": &#123;"database": "", "inputText": "", "sourceId": "", "tables": []&#125;,
    "knowledge": [],
    "errorType": false,
    "errorMessage": "string",
    "intelligent": [],
    "relatedFiles": [],
    "data": &#123;&#125;,
    "language": "string",
    "code": "string"
  &#125;
&#125;
```

> 来源: `FirstChatMessage` + `FirstChatMessage$ValueDTO` 确认

### 8.5 WebRequestDto (确认)

```json
&#123;
  "type": "string",
  "value": &#123;&#125;
&#125;
```

> 来源: `com.aicode.agent.dto.WebRequestDto` 确认

### 8.6 ConnectConfigDto (确认)

```json
&#123;
  "id": "string",
  "client": "string",
  "host": "string",
  "port": "string",
  "user": "string",
  "password": "string",
  "database": "string"
&#125;
```

> 来源: `com.aicode.agent.dto.ConnectConfigDto` 确认

---

## 9. 来源标注说明

| 标注 | 含义 |
|------|------|
| 确认 | 从 Java DTO 字段 (javap -p) 或 webpack bundle 显式代码提取 |
| 推断 | 从上下文逻辑、参数传递链路推断，未找到直接定义 |

### 确认来源统计

| 来源类型 | 字段数 |
|----------|--------|
| Java DTO 反编译 | 142 |
| Webpack bundle 显式代码 | 44 |
| 合计确认 | 186 |

### 推断来源统计

| 来源类型 | 字段数 |
|----------|--------|
| 上下文逻辑推断 | 32 |
| 参数传递链路推断 | 15 |
| 合计推断 | 47 |

---

## 10. API 路由完整清单

### Starspark 路由 (38个)

| # | 路由键 | 路径 | 方法 | 流式 | 超时(ms) |
|---|--------|------|------|------|----------|
| 1 | getWordWriterConfig | /api/starspark/v1/agent/wordWriter/config | GET | - | 10000 |
| 2 | getUrls | /api/starspark/v1/agent/authSetting/query | GET | - | 10000 |
| 3 | checkUpdate | /api/starspark/v1/agent/authSetting/queryPluginLink | POST | - | 10000 |
| 4 | codeAssist | /api/starspark/v1/platform/code/assist | POST | Y | - |
| 5 | generateSql | /api/starspark/v1/agent/chat/generateSql | POST | Y | - |
| 6 | optimizeSql | /api/starspark/v1/agent/chat/optimizeSql | POST | Y | - |
| 7 | generateSqlDM | /api/starspark/v1/agent/chat/sync/generateSql | POST | - | - |
| 8 | optimizeSqlDM | /api/starspark/v1/agent/chat/sync/optimizeSql | POST | - | - |
| 9 | generateCommitMessage | /api/starspark/v1/agent/chat/generateCommitMessage | POST | Y | - |
| 10 | review | /api/starspark/v1/agent/chat/review | POST | Y | - |
| 11 | codeGenerate | /api/starspark/v1/agent/code/codeComplete | POST | Y | 120000 |
| 12 | testCase | /api/starspark/v1/agent/code/generateUnitTestCaseTemplate | POST | - | 120000 |
| 13 | testCode | /api/starspark/v1/agent/code/generateUnitTest | POST | - | 120000 |
| 14 | serverResourceInfo | /api/starspark/v1/agent/code/queryUnitTestQueueInfo | POST | - | - |
| 15 | talkAsk | /api/starspark/v1/agent/chat/async/ask | POST | Y | - |
| 16 | talkAskSync | /api/starspark/v1/agent/chat/sync/ask | POST | - | - |
| 17 | loginByAccount | /api/usercenter/v1/user/common/login | POST | - | 10000 |
| 18 | getFuncModelList | /api/starspark/v1/agent/permission/queryUserFuncModelList | POST | - | 10000 |
| 19 | validToken | /api/starspark/v1/chat/user/valid | POST | - | 10000 |
| 20 | loginStatus | /api/starspark/v1/user/authorizationQuery | GET | - | 10000 |
| 21 | exitLogin | /api/starspark/v1/chat/user/logOut | POST | - | 10000 |
| 22 | getPermission | /api/starspark/v1/agent/permission/queryUserPermissionPackageInfo | POST | - | 10000 |
| 23 | userAction | /api/starspark/v1/agent/action/saveUserAction | POST | - | - |
| 24 | codeReport | /api/starspark/v1/agent/collect/codeAccept | POST | - | - |
| 25 | codeReject | /api/starspark/v1/agent/action/rejectCode | POST | - | - |
| 26 | chatEvaluation | /api/starspark/v1/agent/chat/evaluate | POST | - | - |
| 27 | chatFeedback | /api/starspark/v1/agent/chat/feedback | POST | - | - |
| 28 | gitRepos | /api/ragserver/v1/code/getUserRepos | POST | - | - |
| 29 | gitLangList | /api/ragserver/v1/code/getLanguages | GET | - | - |
| 30 | gitCodeSearch | /api/ragserver/v1/code/search | POST | - | - |
| 31 | requestTimeAnalysis | /api/starspark/v1/agent/collect/uploadRequestTime | POST | - | - |
| 32 | tokenConfig | /api/starspark/v1/agent/pluginSetting/queryTokenSetting | GET | - | - |
| 33 | transDaMengDDL | /api/starspark/v1/agent/chat/convertDmTableDDL | POST | - | - |
| 34 | ragBatchLoad | /api/ragserver/v1/rag/incbatchload | POST | - | - |
| 35 | inlineComment | /api/starspark/v1/agent/chat/interLineCommentCode | POST | Y | - |
| 36 | knowledgeList | /restapi/ragserver/v1/doc/knowledgeList | POST | - | - |
| 37 | searchInRepo | /restapi/ragserver/v1/code/searchInRepo | POST | - | - |
| 38 | searchInDoc | /restapi/ragserver/v1/doc/search | POST | - | - |

### REST API 路由 (14个)

| # | 路由键 | 路径 | 方法 |
|---|--------|------|------|
| 39 | searchInWebSearch | /api/ragserver/v1/code/onlineSearch | POST |
| 40 | repoSearchReady | /restapi/ragserver/v1/rag/repoKeyDialogEnable | POST |
| 41 | repoKeyEnable | /restapi/ragserver/v1/rag/repoKeyEnable | POST |
| 42 | repoLangExtEnable | /restapi/ragserver/v1/rag/repoLangExtEnable | GET |
| 43 | generalSetting | /api/starspark/v1/agent/pluginSetting/queryGlobalSetting | GET |
| 44 | inlineChat | /api/starspark/v1/agent/chat/inline/chat | POST |
| 45 | codeSplit | /api/starspark/v1/agent/chat/splitFunction | POST |
| 46 | codeOptimize | /api/starspark/v1/agent/chat/optimizeCode | POST |
| 47 | queryCategory | /api/starspark/v1/agent/feedback/queryCategory | POST |
| 48 | getChatPromptTemplate | /api/starspark/v1/agent/prompt/query | POST |
| 49 | codeKnowledgeList | /restapi/ragserver/v1/rag/codeK/codeKnowledgeList | POST |
| 50 | codeKnowledgeStatus | /restapi/ragserver/v1/rag/codeK/personal/init/status | POST |
| 51 | authPersonalCodeKnowledge | /restapi/ragserver/v1/rag/codeK/personal/auth | POST |
| 52 | codeKnowledgeReVectorized | /restapi/ragserver/v1/codeknowledge/reVectorized | POST |
| 53 | codeKnowledgeUpdateGitToken | /restapi/ragserver/v1/rag/codeK/updateGitToken | POST |
| 54 | recordCommitInfo | /api/starspark/v1/agent/collect/commitCodeData | POST |
| 55 | recommendations | /api/starspark/v1/agent/chat/recommendations | POST |
| 56 | getUserPackage | /api/starspark/v1/user/packageQuery | POST |
| 57 | parseWebDocument | /api/ragserver/v1/web/parseurl | POST |

### Batch Unit Test 路由 (6个, 含在REST中)

| # | 路由键 | 路径 | 方法 |
|---|--------|------|------|
| - | batchUnitTestCreate | /restapi/unit/v1/createUnitTask | POST |
| - | batchUnitTestList | /restapi/unit/v1/queryUnitTask | POST |
| - | batchUnitTestDownload | /restapi/unit/v1/exportByTaskId | GET |
| - | batchUnitTestCancel | /restapi/unit/v1/cancelUnitTask | POST |
| - | batchUnitTestDelete | /restapi/unit/v1/deleteUnitTask | POST |
| - | batchUnitTestInProgress | /restapi/unit/v1/isPendingTask | POST |

---

## 11. Chat APIS 场景映射 (确认)

| CHAT_APIS 键 | API 路由键 | 场景 | 默认参数 |
|-------------|-----------|------|----------|
| CODE:COMPLETE | codeGenerate | COMPLETE_CODE_WITH_CONTEXT | top_k=1, temperature=1, skipFilter=true, stream=true |
| TALK:ASK | talkAsk | - | top_k=1, temperature=0.5, mup=null |
| TALK:QUESTION_ENHANCE | talkAsk | - | top_k=1, temperature=0.5, mup=null |
| TALK:PREDICT | talkAskSync | - | top_k=1, temperature=0.5, mup=null |
| SQL:GENERATE | generateSql | GENERATE_SQL | - |
| SQL:OPTIMIZE | optimizeSql | OPTIMIZE_SQL | - |
| SQL:GENERATE_DM | generateSqlDM | GENERATE_SQL | - |
| SQL:OPTIMIZE_DM | optimizeSqlDM | OPTIMIZE_SQL | - |
| GIT:COMMIT_MESSAGE | generateCommitMessage | GENERATE_COMMIT_MESSAGE | - |
| GIT:REVIEW | review | REVIEW_CODE | - |
| TEST:MAKE_CASE | testCase | - | - |
| TEST:MAKE_CASE_JAVA | testCase | - | - |
| TEST:MAKE_CODE | testCode | - | - |
| TEST:OTHER | codeAssist | UNIT_TEST | - |
| DIALOG:REQUEST | inlineChat | INLINE_CHAT_SELECTED | - |
