# iFlyCode API 端点请求/响应格式分析

> 版本: iFlyCode 3.4.2-222 | 分析日期: 2026-05-13
> 来源: Agent webpack bundle (index.js) + Java DTO 反编译 (javap -p)

## 统计概览

| 指标 | 数值 |
|------|------|
| API 端点总数 | 57 |
| starspark 端点 | 38 |
| ragserver 端点 | 5 |
| restapi 端点 | 14 |
| 确认字段数 | 186 |
| 推断字段数 | 47 |
| 确认率 | 79.8% |

---

## 1. 通用响应格式

### 1.1 标准响应 ResponseDto (确认)

```json
{
  "id": "string",       // 请求ID
  "code": "string",     // 响应码 (0=成功, 其他=错误)
  "msg": "string",      // 响应消息
  "data": {}            // 响应数据 (Object)
}
```

> 来源: `com.aicode.agent.dto.ResponseDto` 字段: id, code, msg, data

### 1.2 流式响应 ResponseStreamDto (确认)

```json
{
  "id": "string",
  "code": "string",
  "msg": "string",
  "data": {
    "ended": false,           // 是否结束
    "text": "string",         // 增量文本内容
    "showKeyMapTipFlag": false // 是否显示快捷键提示
  }
}
```

> 来源: `com.aicode.agent.dto.ResponseStreamDto` + `ResponseStreamDto$ResponseData`

### 1.3 业务响应 BizResponse (确认)

```json
{
  "resCode": "string",  // 响应码 (RES_CODE_SUCCESS = "0")
  "msg": "string",      // 响应消息
  "obj": {}             // 响应对象 (泛型T)
}
```

> 来源: `com.aicode.service.response.BizResponse` (混淆字段: float=obj, byte=resCode, enum=msg)

### 1.4 错误码定义 (确认)

| 错误码 | 含义 |
|--------|------|
| 0 | 成功 |
| 400 | 参数错误 / 未知指令 / 不支持消息推送 |
| 401 | 未授权 / 用户未登录 |
| 404 | 指令不合法 / 找不到函数 |
| 408 | 任务处理超时 |
| 500 | 内部错误 |
| 501 | 登录地址未配置 / 登录失败 |
| 502 | 登录状态异常 |
| 600 | 参数不能为空 / 请选择数据表 / 帐号密码不能为空 |
| 601 | 文件读取失败 / 请选择文件 |
| 602 | 代码上下文超限 / 未选中有效方法 / 未查询到待评审内容 |
| 604 | 数据源不存在 / 提交信息无效 |
| 607 | 输入内容超长或选择表过多 |
| 608 | 暂无可评审内容 |
| 609 | 取消/指令已结束 (ACTION_REJECT/ACTION_ESC/debounce) |
| 610 | 指令操作已经结束 |
| 611 | 网络异常 |

---

## 2. 通用请求基础数据 getBaseData() (确认)

所有 Chat API 请求均包含以下基础字段:

```json
{
  "requestId": "string",           // 请求唯一ID
  "modelCode": "string",           // 模型代码
  "enterpriseId": "string",        // 企业ID
  "enableMultiModelSwitch": false, // 是否启用多模型切换
  "token": "string",               // 用户token
  "language": "string",            // 编程语言
  "timeStamp": 0,                  // 时间戳
  "fileName": "string",            // 文件名
  "fileNameSuffix": "string",      // 文件扩展名
  "projectName": "string",         // 项目名
  "agentVersion": "string",        // Agent版本号
  "commandType": "string",         // 命令类型 (如 "CODE:COMPLETE")
  "taskName": "string",            // 任务名 (scene)
  "scene": "string",               // 场景标识
  "knowledgeBase": "string",       // 知识库类型 ("codeKnowledgeBase" / "docKnowledgeBase")
  "userQuestionContent": "string", // 用户问题内容
  "clientName": "string",          // 客户端名称 (推断)
  "clientVersion": "string",       // 客户端版本 (推断)
  "pluginVersion": "string"        // 插件版本 (推断)
}
```

> 来源: webpack bundle `getBaseData()` 方法 + `clientInfo` 结构

---

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
{
  "code": "0",
  "data": {
    "token": "string",
    "user": "string",
    "clientId": "string",
    "codeModelDtoList": [{"modelId": "", "modelCode": "", "modelName": "", "checked": false, "originalModelName": "", "tokenExhausted": false}],
    "enterpriseDto": {"enterpriseId": "", "enterpriseName": "", "userId": ""},
    "tokenPath": "string",
    "sysUrls": {"feedbackUrl": "", "maintainRepoUrl": "", "codeSearchServerUrl": "", "officialWebsiteUrl": "", "codeKnowledgeWebUrl": "", "userCenterWebUrl": ""},
    "packageCode": "string",
    "packageName": "string",
    "reLogin": false
  }
}
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

**响应格式:** `BizResponse<boolean>`

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

**响应格式:** `BizResponse<LoginInfo>`

```json
{
  "current": "string",
  "update": "string",
  "name": "string",
  "file": "string",
  "dir": "string",
  "md5": "string"
}
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

**响应格式:** `BizResponse<SysUrlDto>`

```json
{
  "feedbackUrl": "string",
  "maintainRepoUrl": "string",
  "codeSearchServerUrl": "string",
  "officialWebsiteUrl": "string",
  "codeKnowledgeWebUrl": "string",
  "userCenterWebUrl": "string"
}
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
{
  "enable": true,
  "interval": 20
}
```

---

#### GET /api/starspark/v1/agent/pluginSetting/queryGlobalSetting

| 属性 | 值 |
|------|-----|
| 路由键 | `generalSetting` |
| 方法 | GET (推断) |

**响应格式:** `BizResponse<SettingsDto>`

```json
{
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
}
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
{
  "data": [
    {
      "permissionCode": "string",
      "permissionName": "string",
      "language": "string",
      "codeModelList": [{"modelId": "", "modelCode": "", "modelName": "", "checked": false, "originalModelName": "", "tokenExhausted": false}]
    }
  ]
}
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
{
  "role": "user|assistant|system",
  "scene": "string",
  "talkCode": "string|null",
  "language": "string",
  "content": "string"
}
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
{
  "completions": ["string"],  // 补全结果列表
  "requestId": "string",      // 请求ID
  "completeType": "string",   // 补全类型 (scene)
  "model": "string"           // 模型标识
}
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
{
  "requestId": "string",
  "endToEndRequestTime": 0
}
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

## 4. RAG Server API 端点

#### POST /api/ragserver/v1/code/getUserRepos

| 属性 | 值 |
|------|-----|
| 路由键 | `gitRepos` |
| 方法 | POST |

**响应格式 (确认):**

```json
{
  "currentPage": 0,
  "pageSize": 0,
  "total": 0,
  "totalPage": 0,
  "content": [
    {
      "id": "string",
      "repoUrl": "string",
      "repoName": "string",
      "branch": "string",
      "repoType": "string"
    }
  ]
}
```

> 响应来源: `CodeRepoInfoDto` extends `PageInfo`, content=`ReposInfoDto` 确认

---

#### GET /api/ragserver/v1/code/getLanguages

| 属性 | 值 |
|------|-----|
| 路由键 | `gitLangList` |
| 方法 | GET (推断, 无method字段) |

---

#### POST /api/ragserver/v1/code/search

| 属性 | 值 |
|------|-----|
| 路由键 | `gitCodeSearch` |
| 方法 | POST |

**请求参数 (确认):**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| clientName | string | 确认 | 客户端名称 |
| clientVersion | string | 确认 | 客户端版本 |
| pluginVersion | string | 确认 | 插件版本 |
| searchType | string | 确认 | 搜索类型 |
| content | string | 确认 | 搜索内容 |
| repoIds | array | 确认 | 仓库ID列表 |
| languages | array | 确认 | 语言列表 |
| currentPage | number | 确认 | 当前页 |
| pageSize | number | 确认 | 每页大小 |
| isSystemDefault | boolean | 确认 | 是否系统默认 |
| requestId | string | 确认 | 请求ID |
| userId | string | 确认 | 用户ID |
| enterpriseId | string | 确认 | 企业ID |

**响应格式 (确认):**

```json
{
  "currentPage": 0,
  "pageSize": 0,
  "total": 0,
  "totalPage": 0,
  "type": "string",
  "count": 0,
  "content": [
    {
      "id": "string",
      "repoUrl": "string",
      "repoName": "string",
      "repoType": "string",
      "branch": "string",
      "filePath": "string",
      "fileName": "string",
      "language": "string",
      "isOpen": 0,
      "isPublic": 0,
      "startRow": 0,
      "endRow": 0,
      "score": 0,
      "code": "string",
      "codeLength": 0,
      "codeVector": 0.0,
      "createTime": 0
    }
  ]
}
```

> 响应来源: `CodeSearchInfoDto` extends `PageInfo`, content=`CodeSearchDto` 确认

---

#### POST /api/ragserver/v1/code/onlineSearch

| 属性 | 值 |
|------|-----|
| 路由键 | `searchInWebSearch` |
| 方法 | POST |

---

#### POST /api/ragserver/v1/rag/incbatchload

| 属性 | 值 |
|------|-----|
| 路由键 | `ragBatchLoad` |
| 方法 | POST |

---

#### POST /api/ragserver/v1/web/parseurl

| 属性 | 值 |
|------|-----|
| 路由键 | `parseWebDocument` |
| 方法 | POST |

---

## 5. REST API 端点

### 5.1 代码搜索 (REST)

#### POST /restapi/ragserver/v1/code/searchInRepo

| 属性 | 值 |
|------|-----|
| 路由键 | `searchInRepo` |
| 方法 | POST |

---

#### POST /restapi/ragserver/v1/doc/search

| 属性 | 值 |
|------|-----|
| 路由键 | `searchInDoc` |
| 方法 | POST |

---

#### POST /restapi/ragserver/v1/doc/knowledgeList

| 属性 | 值 |
|------|-----|
| 路由键 | `knowledgeList` |
| 方法 | POST |

---

### 5.2 代码知识库 (REST)

#### POST /restapi/ragserver/v1/rag/codeK/codeKnowledgeList

| 属性 | 值 |
|------|-----|
| 路由键 | `codeKnowledgeList` |
| 方法 | POST |

---

#### POST /restapi/ragserver/v1/rag/codeK/personal/init/status

| 属性 | 值 |
|------|-----|
| 路由键 | `codeKnowledgeStatus` |
| 方法 | POST |

---

#### POST /restapi/ragserver/v1/rag/codeK/personal/auth

| 属性 | 值 |
|------|-----|
| 路由键 | `authPersonalCodeKnowledge` |
| 方法 | POST |

**请求参数 (推断):**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| repoUrl | string | 推断 | 仓库URL |

---

#### POST /restapi/ragserver/v1/rag/codeK/updateGitToken

| 属性 | 值 |
|------|-----|
| 路由键 | `codeKnowledgeUpdateGitToken` |
| 方法 | POST |

---

#### POST /restapi/ragserver/v1/codeknowledge/reVectorized

| 属性 | 值 |
|------|-----|
| 路由键 | `codeKnowledgeReVectorized` |
| 方法 | POST |

**请求参数 (确认):**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| id | string | 确认 | 知识库ID |
| isOpen | number | 确认 | 是否开放 (默认2) |
| isPublic | number | 确认 | 是否公开 (默认0) |
| enterpriseId | string | 确认 | 企业ID |
| createUser | string | 确认 | 创建用户 |

---

### 5.3 RAG 仓库配置 (REST)

#### POST /restapi/ragserver/v1/rag/repoKeyDialogEnable

| 属性 | 值 |
|------|-----|
| 路由键 | `repoSearchReady` |
| 方法 | POST |

**请求参数 (确认):**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| repoKey | string | 确认 | 仓库Key |

---

#### POST /restapi/ragserver/v1/rag/repoKeyEnable

| 属性 | 值 |
|------|-----|
| 路由键 | `repoKeyEnable` |
| 方法 | POST |

**请求参数 (确认):**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| repoKey | string | 确认 | 仓库Key |
| force | boolean | 确认 | 是否强制 |

---

#### GET /restapi/ragserver/v1/rag/repoLangExtEnable

| 属性 | 值 |
|------|-----|
| 路由键 | `repoLangExtEnable` |
| 方法 | GET |

**Query参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| force | boolean | 确认 | 是否强制 |

---

### 5.4 批量单元测试 (REST)

#### POST /restapi/unit/v1/createUnitTask

| 属性 | 值 |
|------|-----|
| 路由键 | `batchUnitTestCreate` |
| 方法 | POST |

**Headers:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| access-token | string | 确认 | 用户token |

**请求参数:** `BatchUnitTestDto` (确认)

```json
{
  "taskId": "string",
  "gitUrl": "string",
  "gitBranch": "string",
  "gitType": "string",
  "gitToken": "string",
  "unitTestDirectory": "string",
  "testFramework": "string",
  "unitTestLanguage": "string",
  "taskStatus": "string",
  "description": "string",
  "completion": "string",
  "total": "string",
  "modifyTime": "string",
  "remark": "string"
}
```

> 请求来源: `BatchUnitTestDto` 确认

---

#### POST /restapi/unit/v1/queryUnitTask

| 属性 | 值 |
|------|-----|
| 路由键 | `batchUnitTestList` |
| 方法 | POST |

**Headers:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| access-token | string | 确认 | 用户token |

**请求参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| taskStatus | string | 确认 | 任务状态 |

---

#### GET /restapi/unit/v1/exportByTaskId

| 属性 | 值 |
|------|-----|
| 路由键 | `batchUnitTestDownload` |
| 方法 | GET |

**Headers:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| access-token | string | 确认 | 用户token |

**Query参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| taskId | string | 确认 | 任务ID |

---

#### POST /restapi/unit/v1/cancelUnitTask

| 属性 | 值 |
|------|-----|
| 路由键 | `batchUnitTestCancel` |
| 方法 | POST |

**Headers:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| access-token | string | 确认 | 用户token |

**请求参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| taskId | string | 确认 | 任务ID |

---

#### POST /restapi/unit/v1/deleteUnitTask

| 属性 | 值 |
|------|-----|
| 路由键 | `batchUnitTestDelete` |
| 方法 | POST |

**Headers:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| access-token | string | 确认 | 用户token |

**请求参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| taskId | string | 确认 | 任务ID |

---

#### POST /restapi/unit/v1/isPendingTask

| 属性 | 值 |
|------|-----|
| 路由键 | `batchUnitTestInProgress` |
| 方法 | POST |

**Headers:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| access-token | string | 确认 | 用户token |

---

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
{
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
  "data": {},
  "docChangeCount": 0,
  "range": [{"line": 0, "character": 0}],
  "knowledge": {},
  "intelligent": [],
  "relatedFiles": [],
  "language": "string",
  "tipinfo": {"user": "", "platform": "", "isShowOperateGuide": false},
  "requestion": "string",
  "md5": "string",
  "directName": "string",
  "inlineChatVersion": 0
}
```

> 来源: `com.aicode.agent.dto.MessageDto` 确认

### 8.2 CodeInfoDto (确认)

```json
{
  "content": "string",
  "range": [{"line": 0, "character": 0}],
  "fileName": "string",
  "path": "string",
  "language": "string",
  "allContent": "string"
}
```

> 来源: `com.aicode.agent.dto.chat.CodeInfoDto` 确认

### 8.3 SqlInfoDto (确认)

```json
{
  "database": "string",
  "inputText": "string",
  "sourceId": "string",
  "tables": ["string"]
}
```

> 来源: `com.aicode.agent.dto.chat.SqlInfoDto` 确认

### 8.4 FirstChatMessage (确认)

```json
{
  "type": "string",
  "value": {
    "inputText": "string",
    "id": "string",
    "sessionId": "string",
    "type": "string",
    "codeInfo": {"content": "", "range": [], "fileName": "", "path": "", "language": "", "allContent": ""},
    "sqlInfo": {"database": "", "inputText": "", "sourceId": "", "tables": []},
    "knowledge": [],
    "errorType": false,
    "errorMessage": "string",
    "intelligent": [],
    "relatedFiles": [],
    "data": {},
    "language": "string",
    "code": "string"
  }
}
```

> 来源: `FirstChatMessage` + `FirstChatMessage$ValueDTO` 确认

### 8.5 WebRequestDto (确认)

```json
{
  "type": "string",
  "value": {}
}
```

> 来源: `com.aicode.agent.dto.WebRequestDto` 确认

### 8.6 ConnectConfigDto (确认)

```json
{
  "id": "string",
  "client": "string",
  "host": "string",
  "port": "string",
  "user": "string",
  "password": "string",
  "database": "string"
}
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
