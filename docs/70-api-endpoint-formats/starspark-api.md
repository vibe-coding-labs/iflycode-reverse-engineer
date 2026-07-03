## 3. Starspark API 端点

### 3.1 认证与登录

#### POST /api/usercenter/v1/user/common/login

| 属性 | 值 |
|------|-----|
| 路由键 | `loginByAccount` |
| 方法 | POST |
| 超时 | 10000ms |
| 流式 | 否 |

**请求参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| user | string | 确认 | RSA加密的用户名 |
| pwCode | string | 确认 | RSA加密的密码 |

**Query参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| clientId | string | 确认 | 客户端标识 |

**响应格式:**

```json
&#123;
  "code": "0",
  "data": &#123;
    "token": "string",
    "user": "string",
    "clientId": "string",
    "codeModelDtoList": [&#123;"modelId": "", "modelCode": "", "modelName": "", "checked": false, "originalModelName": "", "tokenExhausted": false&#125;],
    "enterpriseDto": &#123;"enterpriseId": "", "enterpriseName": "", "userId": ""&#125;,
    "tokenPath": "string",
    "sysUrls": &#123;"feedbackUrl": "", "maintainRepoUrl": "", "codeSearchServerUrl": "", "officialWebsiteUrl": "", "codeKnowledgeWebUrl": "", "userCenterWebUrl": ""&#125;,
    "packageCode": "string",
    "packageName": "string",
    "reLogin": false
  &#125;
&#125;
```

> 响应来源: `UserInfoDto` 确认

---

#### POST /api/starspark/v1/chat/user/valid

| 属性 | 值 |
|------|-----|
| 路由键 | `validToken` |
| 方法 | POST |
| 超时 | 10000ms |

**请求参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| token | string | 确认 | 待验证的token |

**响应格式:** `BizResponse&lt;boolean&gt;`

---

#### GET /api/starspark/v1/user/authorizationQuery

| 属性 | 值 |
|------|-----|
| 路由键 | `loginStatus` |
| 方法 | GET (推断, 无method字段) |
| 超时 | 10000ms |

**Headers:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| clientId | string | 确认 | 客户端标识 |

**响应格式:** `BizResponse&lt;LoginInfo&gt;`

```json
&#123;
  "current": "string",
  "update": "string",
  "name": "string",
  "file": "string",
  "dir": "string",
  "md5": "string"
&#125;
```

> 响应来源: `LoginInfo` 确认

---

#### POST /api/starspark/v1/chat/user/logOut

| 属性 | 值 |
|------|-----|
| 路由键 | `exitLogin` |
| 方法 | POST |
| 超时 | 10000ms |

**请求参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| token | string | 确认 | 用户token |

---

#### POST /api/starspark/v1/user/packageQuery

| 属性 | 值 |
|------|-----|
| 路由键 | `getUserPackage` |
| 方法 | POST |
| 超时 | 10000ms |

**Headers:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| token | string | 确认 | 用户token |

---

### 3.2 配置与设置

#### GET /api/starspark/v1/agent/authSetting/query

| 属性 | 值 |
|------|-----|
| 路由键 | `getUrls` |
| 方法 | GET (推断) |
| 超时 | 10000ms |

**响应格式:** `BizResponse&lt;SysUrlDto&gt;`

```json
&#123;
  "feedbackUrl": "string",
  "maintainRepoUrl": "string",
  "codeSearchServerUrl": "string",
  "officialWebsiteUrl": "string",
  "codeKnowledgeWebUrl": "string",
  "userCenterWebUrl": "string"
&#125;
```

> 响应来源: `SysUrlDto` 确认

---

#### POST /api/starspark/v1/agent/authSetting/queryPluginLink

| 属性 | 值 |
|------|-----|
| 路由键 | `checkUpdate` |
| 方法 | POST |
| 超时 | 10000ms |

**Query参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| pluginType | string | 确认 | 插件类型 |

---

#### GET /api/starspark/v1/agent/wordWriter/config

| 属性 | 值 |
|------|-----|
| 路由键 | `getWordWriterConfig` |
| 方法 | GET (推断) |
| 超时 | 10000ms |

**响应格式 (推断):**

```json
&#123;
  "enable": true,
  "interval": 20
&#125;
```

---

#### GET /api/starspark/v1/agent/pluginSetting/queryGlobalSetting

| 属性 | 值 |
|------|-----|
| 路由键 | `generalSetting` |
| 方法 | GET (推断) |

**响应格式:** `BizResponse&lt;SettingsDto&gt;`

```json
&#123;
  "autoTriggerOnPause": false,
  "autoTriggerTimeDelay": 0,
  "generateCodeMode": "string",
  "codeCompleteDisableLang": ["string"],
  "sendMessageType": "string",
  "javaTestFramework": "string",
  "javaMockFramework": "string",
  "lineToolsType": "string",
  "lineToolsPermissionDocComments": false,
  "lineToolsPermissionLineComments": false,
  "lineToolsPermissionComments": false,
  "lineToolsPermissionFunctionSplit": false,
  "lineToolsPermissionCodeOptimization": false,
  "lineToolsPermissionUnitTesting": false,
  "openFunctionSplit": false,
  "openCodeOptimization": false,
  "openIFlyTest": false,
  "openInlineChat": false,
  "openIFlyDBA": false,
  "openIFlyOps": false,
  "openIFlyPm": false,
  "openCodeEnhance": false,
  "inlineCompletionInputStyle": "string",
  "openAutoUpdate": false,
  "defaultLanguage": "string"
&#125;
```

> 响应来源: `SettingsDto` 确认

---

#### GET /api/starspark/v1/agent/pluginSetting/queryTokenSetting

| 属性 | 值 |
|------|-----|
| 路由键 | `tokenConfig` |
| 方法 | GET (推断) |

---

### 3.3 权限查询

#### POST /api/starspark/v1/agent/permission/queryUserFuncModelList

| 属性 | 值 |
|------|-----|
| 路由键 | `getFuncModelList` |
| 方法 | POST |
| 超时 | 10000ms |

**响应格式 (推断):**

```json
&#123;
  "data": [
    &#123;
      "permissionCode": "string",
      "permissionName": "string",
      "language": "string",
      "codeModelList": [&#123;"modelId": "", "modelCode": "", "modelName": "", "checked": false, "originalModelName": "", "tokenExhausted": false&#125;]
    &#125;
  ]
&#125;
```

> 响应来源: `FunctionModelInfo` 确认

---

#### POST /api/starspark/v1/agent/permission/queryUserPermissionPackageInfo

| 属性 | 值 |
|------|-----|
| 路由键 | `getPermission` |
| 方法 | POST |
| 超时 | 10000ms |

---

### 3.4 对话 (Chat) API

#### POST /api/starspark/v1/agent/chat/async/ask

| 属性 | 值 |
|------|-----|
| 路由键 | `talkAsk` |
| 方法 | POST |
| 流式 | 是 |
| 使用模型 | 是 |
| 场景 | TALK:ASK, TALK:QUESTION_ENHANCE |

**请求参数 (getBaseData + 以下):**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| sessionId | string | 确认 | 会话ID |
| scene | string | 确认 | 场景标识 |
| top_k | number | 确认 | 默认1, 重发时5 |
| temperature | number | 确认 | 默认0.5, 重发时0.5 |
| mup | null | 确认 | 默认null |
| messages | array | 确认 | 消息列表 |

**messages 元素格式 (推断):**

```json
&#123;
  "role": "user|assistant|system",
  "scene": "string",
  "talkCode": "string|null",
  "language": "string",
  "content": "string"
&#125;
```

**流式响应:** `ResponseStreamDto` (SSE)

---

#### POST /api/starspark/v1/agent/chat/sync/ask

| 属性 | 值 |
|------|-----|
| 路由键 | `talkAskSync` |
| 方法 | POST |
| 流式 | 否 |
| 使用模型 | 是 |
| 场景 | TALK:PREDICT |

**请求参数:** 同 async/ask，但 stream=false

**响应格式:** `ResponseDto` (非流式)

---

#### POST /api/starspark/v1/agent/chat/inline/chat

| 属性 | 值 |
|------|-----|
| 路由键 | `inlineChat` |
| 方法 | POST |
| 流式 | 是 |
| 使用模型 | 是 |
| 场景 | INLINE_CHAT_SELECTED |

**请求参数 (getBaseData + 以下):**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| sessionId | string | 确认 | 会话ID |
| scene | string | 确认 | "INLINE_CHAT_SELECTED" |
| directName | string | 推断 | 直接指令名称 |
| inlineChatVersion | number | 推断 | 内联聊天版本号 |
| messages | array | 确认 | 消息列表 |

---

#### POST /api/starspark/v1/agent/chat/evaluate

| 属性 | 值 |
|------|-----|
| 路由键 | `chatEvaluation` |
| 方法 | POST |

**请求参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| requestId | string | 确认 | 请求ID |
| evaluation | string | 确认 | 评价内容 |

---

#### POST /api/starspark/v1/agent/chat/feedback

| 属性 | 值 |
|------|-----|
| 路由键 | `chatFeedback` |
| 方法 | POST |

**请求参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| requestId | string | 确认 | 请求ID |
| apprasial | string | 确认 | 评价类型 |
| evalDetail | string | 确认 | 评价详情 |

---

#### POST /api/starspark/v1/agent/chat/recommendations

| 属性 | 值 |
|------|-----|
| 路由键 | `recommendations` |
| 方法 | POST |

**请求参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| userFileStatusCode | string | 确认 | 用户文件状态码 |

---

#### POST /api/starspark/v1/agent/chat/generateCommitMessage

| 属性 | 值 |
|------|-----|
| 路由键 | `generateCommitMessage` |
| 方法 | POST |
| 流式 | 是 |
| 使用模型 | 是 |
| 场景 | GIT:COMMIT_MESSAGE |
| Token限制 | 8000, 折扣100% |

**请求参数:** getBaseData + messages

---

#### POST /api/starspark/v1/agent/chat/review

| 属性 | 值 |
|------|-----|
| 路由键 | `review` |
| 方法 | POST |
| 流式 | 是 |
| 使用模型 | 是 |
| 场景 | GIT:REVIEW |
| Token限制 | 8000, 折扣100% |

**请求参数:** getBaseData + messages

---

#### POST /api/starspark/v1/agent/chat/optimizeCode

| 属性 | 值 |
|------|-----|
| 路由键 | `codeOptimize` |
| 方法 | POST |
| 流式 | 是 |
| 使用模型 | 是 |
| Token限制 | 8000, 折扣67% |

**请求参数:** getBaseData + messages

---

#### POST /api/starspark/v1/agent/chat/splitFunction

| 属性 | 值 |
|------|-----|
| 路由键 | `codeSplit` |
| 方法 | POST |
| 流式 | 是 |
| 使用模型 | 是 |
| Token限制 | 8000, 折扣67% |

**请求参数:** getBaseData + messages

---

#### POST /api/starspark/v1/agent/chat/interLineCommentCode

| 属性 | 值 |
|------|-----|
| 路由键 | `inlineComment` |
| 方法 | POST |
| 流式 | 是 |
| 使用模型 | 是 |
| Token限制 | 8000, 折扣62.5% |

**请求参数:** getBaseData + messages

---

### 3.5 SQL 相关 API

#### POST /api/starspark/v1/agent/chat/generateSql

| 属性 | 值 |
|------|-----|
| 路由键 | `generateSql` |
| 方法 | POST |
| 流式 | 是 |
| 使用模型 | 是 |
| 场景 | SQL:GENERATE |

**请求参数 (getBaseData + 以下):**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| messages | array | 确认 | 包含SQL结构信息 |
| messages[].tableKey | string | 确认 | 表名排序拼接 |
| messages[].tableList | array | 确认 | 表信息列表 |
| messages[].content | string | 确认 | 模板渲染后的SQL提示 |
| messages[].originalInput | string | 确认 | 原始用户输入 |
| messages[].databaseType | string | 确认 | 数据库类型 |

---

#### POST /api/starspark/v1/agent/chat/sync/generateSql

| 属性 | 值 |
|------|-----|
| 路由键 | `generateSqlDM` |
| 方法 | POST |
| 使用模型 | 是 |
| 场景 | SQL:GENERATE_DM (达梦数据库) |

**请求参数:** 同 generateSql，databaseType="dm"

---

#### POST /api/starspark/v1/agent/chat/optimizeSql

| 属性 | 值 |
|------|-----|
| 路由键 | `optimizeSql` |
| 方法 | POST |
| 流式 | 是 |
| 使用模型 | 是 |
| 场景 | SQL:OPTIMIZE |

**请求参数:** 同 generateSql

---

#### POST /api/starspark/v1/agent/chat/sync/optimizeSql

| 属性 | 值 |
|------|-----|
| 路由键 | `optimizeSqlDM` |
| 方法 | POST |
| 使用模型 | 是 |
| 场景 | SQL:OPTIMIZE_DM (达梦数据库) |

---

#### POST /api/starspark/v1/agent/chat/convertDmTableDDL

| 属性 | 值 |
|------|-----|
| 路由键 | `transDaMengDDL` |
| 方法 | POST |

**请求参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| dmTableDDL | string | 确认 | 达梦数据库DDL语句 |

---

### 3.6 代码补全与单元测试

#### POST /api/starspark/v1/agent/code/codeComplete

| 属性 | 值 |
|------|-----|
| 路由键 | `codeGenerate` |
| 方法 | POST |
| 流式 | 是 (默认) |
| 使用模型 | 是 |
| 超时 | 120000ms |
| 场景 | CODE:COMPLETE |
| Token限制 | 8000, 折扣67% |

**请求参数 (getBaseData + 以下):**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| prefix | string | 确认 | 光标上方代码 |
| suffix | string | 确认 | 光标下方代码 |
| selected | string | 确认 | 选中的代码 (推断) |
| lang | string | 确认 | 编程语言 |
| top_k | number | 确认 | 默认1 |
| temperature | number | 确认 | 默认1 |
| skipFilter | boolean | 确认 | 默认true |
| stream | boolean | 确认 | 默认true |
| user | string | 确认 | 用户标识 |
| projectName | string | 确认 | 项目名 |
| commentComplete | boolean | 确认 | 注释补全标志 |
| filePath | string | 确认 | 文件相对路径 |
| docChangeCount | number | 确认 | 文档变更计数 |
| forcedTrigger | boolean | 确认 | 是否强制触发 |
| codeGenerateMode | string | 确认 | 补全模式 ("SINGLE_LINE" 等) |
| latestAgent | boolean | 确认 | 是否最新Agent |
| codeBlockContent | string | 确认 | 代码块内容 |
| promptScene | string | 确认 | 提示场景 |

**响应格式 (确认):**

```json
&#123;
  "completions": ["string"],  // 补全结果列表
  "requestId": "string",      // 请求ID
  "completeType": "string",   // 补全类型 (scene)
  "model": "string"           // 模型标识
&#125;
```

---

#### POST /api/starspark/v1/agent/code/generateUnitTestCaseTemplate

| 属性 | 值 |
|------|-----|
| 路由键 | `testCase` |
| 方法 | POST |
| 使用模型 | 是 |
| 超时 | 120000ms |
| 场景 | TEST:MAKE_CASE, TEST:MAKE_CASE_JAVA |
| Token限制 | 8000, 折扣100% |

**请求参数:** getBaseData + messages

---

#### POST /api/starspark/v1/agent/code/generateUnitTest

| 属性 | 值 |
|------|-----|
| 路由键 | `testCode` |
| 方法 | POST |
| 使用模型 | 是 |
| 超时 | 120000ms |
| 场景 | TEST:MAKE_CODE |
| Token限制 | 8000, 折扣100% |

**请求参数:** getBaseData + messages

---

#### POST /api/starspark/v1/agent/code/queryUnitTestQueueInfo

| 属性 | 值 |
|------|-----|
| 路由键 | `serverResourceInfo` |
| 方法 | POST |

---

### 3.7 代码辅助

#### POST /api/starspark/v1/platform/code/assist

| 属性 | 值 |
|------|-----|
| 路由键 | `codeAssist` |
| 方法 | POST |
| 流式 | 是 |
| 使用模型 | 是 |
| 场景 | TEST:OTHER |

**请求参数:** getBaseData + messages

---

### 3.8 数据收集与埋点

#### POST /api/starspark/v1/agent/collect/codeAccept

| 属性 | 值 |
|------|-----|
| 路由键 | `codeReport` |
| 方法 | POST |

**请求参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| encryptMode | string | 确认 | "SM4" |
| codeCollectDtoList | array | 确认 | 代码收集DTO列表 |

---

#### POST /api/starspark/v1/agent/action/saveUserAction

| 属性 | 值 |
|------|-----|
| 路由键 | `userAction` |
| 方法 | POST |

**请求参数 (推断):**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| requestId | string | 确认 | 请求ID |
| action | string | 确认 | 动作类型 (ACTION_TAB/ACTION_WORD/ACTION_LINE/ACTION_GRAY_SHOW/ACTION_IMITATIVE_WRITE) |
| docChangeCount | number | 确认 | 文档变更计数 |
| displayCodeTime | number | 推断 | 代码显示时间 |
| rejectCodeTime | number | 推断 | 代码拒绝时间 |

---

#### POST /api/starspark/v1/agent/action/rejectCode

| 属性 | 值 |
|------|-----|
| 路由键 | `codeReject` |
| 方法 | POST |

**请求参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| requestList | array | 确认 | 请求列表 |
| rejectType | string | 确认 | 拒绝类型 |

---

#### POST /api/starspark/v1/agent/collect/commitCodeData

| 属性 | 值 |
|------|-----|
| 路由键 | `recordCommitInfo` |
| 方法 | POST |

**请求参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| id | string | 确认 | commitId |

---

#### POST /api/starspark/v1/agent/collect/uploadRequestTime

| 属性 | 值 |
|------|-----|
| 路由键 | `requestTimeAnalysis` |
| 方法 | POST |

**请求参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| endToEndRequestTimeDtoList | array | 确认 | 端到端请求时间DTO列表 |

**endToEndRequestTimeDtoList 元素 (推断):**

```json
&#123;
  "requestId": "string",
  "endToEndRequestTime": 0
&#125;
```

---

#### POST /api/starspark/v1/agent/collect/chatDataContent

| 属性 | 值 |
|------|-----|
| 路由键 | `chatDataReport` |
| 方法 | POST |

**请求参数 (推断):**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| requestId | string | 确认 | 请求ID |
| assistRoleType | string | 确认 | 助手角色类型 |
| chatDataContent | object | 确认 | 聊天数据内容 |

---

#### POST /api/starspark/v1/agent/collect/unitTestStatistics

| 属性 | 值 |
|------|-----|
| 路由键 | `unitTestCollection` |
| 方法 | POST |

**请求参数 (推断):**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| projectName | string | 确认 | 项目名 |
| subSystemName | string | 确认 | 子系统名 |
| clientName | string | 确认 | 客户端名 |
| clientVersion | string | 确认 | 客户端版本 |
| pluginVersion | string | 确认 | 插件版本 |

---

#### POST /api/starspark/v1/agent/collect/generateUnitTestData

| 属性 | 值 |
|------|-----|
| 路由键 | `unitTestCollectionGenerate` |
| 方法 | POST |

**请求参数:** 同 unitTestStatistics

---

#### POST /api/starspark/v1/agent/collect/commitUnitTestData

| 属性 | 值 |
|------|-----|
| 路由键 | `unitTestCollectionCommit` |
| 方法 | POST |

**请求参数:** 同 unitTestStatistics

---

### 3.9 反馈与提示

#### POST /api/starspark/v1/agent/feedback/queryCategory

| 属性 | 值 |
|------|-----|
| 路由键 | `queryCategory` |
| 方法 | POST |

---

#### POST /api/starspark/v1/agent/prompt/query

| 属性 | 值 |
|------|-----|
| 路由键 | `getChatPromptTemplate` |
| 方法 | POST |

**请求参数 (推断):**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| scene | string | 确认 | 场景标识 |
| role | string | 确认 | 角色 ("user"/"system"/"assistant") |
| language | string | 确认 | 编程语言 |
| + getBaseData字段 | | | |

---
