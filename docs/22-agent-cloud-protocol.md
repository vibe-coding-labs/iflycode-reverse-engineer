# 22 Agent→Cloud HTTPS 通信协议

> 基于 `agent/bin/index.js` (3.8MB webpack bundle) 逆向分析

## 通信架构

```
IDE Plugin (Java)
    │ ws://127.0.0.1:{port}/ws/idea
    ▼
Agent (Node.js)
    │ HTTPS (node-fetch)
    ▼
Cloud Server
    ├─ https://iflycode-xfsaas.xfyun.cn      (SaaS)
    ├─ https://iflycode-api.iflytek.com       (Pro)
    └─ http://172.29.x.x:port                 (内网开发)
```

## 基础 HTTP 请求格式

### 请求构建 (ServiceBase._getInitOpt)

```http
POST /api/starspark/v1/agent/chat/async/ask HTTP/1.1
Content-Type: application/json
token: <用户token>
traceparent: <W3C Trace Context>

{
  "requestId": "uuid-xxx",
  "sessionId": "session-uuid",
  ...
}
```

### 通用 Headers

| Header | 值 | 说明 |
|--------|-----|------|
| `Content-Type` | `application/json` | 所有请求均为 JSON |
| `token` | 用户认证 token | 绝大多数 API 需要此 header |
| `traceparent` | W3C Trace Context | OpenTelemetry 注入的追踪标识 |

### 特殊 Headers

| Header | 使用场景 | 值 |
|--------|---------|-----|
| `access-token` | 批量单测相关 API | `client.user` (用户名) |
| `clientId` | 登录状态检查 | WebSocket 客户端 ID |

### 请求方法

所有 API 只使用 `GET` 和 `POST` 两种方法。绝大多数为 `POST`。

### 超时配置

| 场景 | 超时 |
|------|------|
| 默认 | 600,000 ms (10 分钟) |
| 快速接口 | 10,000 ms (10 秒) |
| 代码生成/单测 | 120,000 ms (2 分钟) |

### 通用错误码

| HTTP 状态码 | 业务码 | 含义 |
|------------|--------|------|
| 401 | — | 未授权，需重新登录 |
| 200 | `"0"` / `200` | 成功 |
| 200 | `"UNAUTHORIZED"` / `"5020"` / `"5001"` | 认证失效 |
| 200 | `"ENOTFOUND"` / `"ECONNRESET"` | 网络错误 |

响应体统一格式：

```json
{
  "code": "0",        // 或 "resCode": "0"
  "msg": "success",   // 或 "message"
  "obj": { ... },     // 成功时数据在 obj 或 data 字段
  "data": { ... }
}
```

Agent 提取逻辑：成功时取 `obj || data`，否则抛出 `iFlyCodeError(msg, code)`。

## API 端点完整列表

### 1. 认证与用户 (LoginService)

#### loginByAccount — 账号密码登录

```
POST /api/usercenter/v1/user/common/login?clientId=<clientId>
Content-Type: application/json

请求体:
{
  "user": "<RSA加密后的用户名>",
  "pwCode": "<RSA加密后的密码>"
}

响应体:
{
  "code": "0",
  "obj": {
    "token": "xxx-token-xxx",
    "clientId": "uuid",
    "user": "username",
    "enterpriseDto": {
      "userId": "uuid",
      "enterpriseId": "uuid",
      "enterpriseName": "企业名"
    }
  }
}
```

RSA 公钥:
```
-----BEGIN PUBLIC KEY-----
MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCFMVCHyq4CNE0sHQj5O3o6SFxo
5yKK6/tpOC/zbpcomixQ17X7BBccZPyDcruIUkfNhlAeQHxFDn2NCOn2zdm3+6kes
6KqHyjziBpHzjz9cQtvvEb8oT6ZvB2Ffsqr3JygMwDyPDHt0BmMo5CsuCvQvpmu7
o9Qf5mkSx2UFIxlGQIDAQAB
-----END PUBLIC KEY-----
```

加密方式: 1024-bit RSA，分块加密 (64 bytes/block)，PKCS#5 padding。

#### validToken — Token 验证

```
POST /api/starspark/v1/chat/user/valid

{
  "token": "<token>"
}
```

#### loginStatus (checkLoginStatus) — 登录状态查询

```
GET /api/starspark/v1/user/authorizationQuery
clientId: <clientId>

响应: 包含 token 和用户信息
```

#### exitLogin — 退出登录

```
POST /api/starspark/v1/chat/user/logOut

{
  "token": "<token>"
}
```

#### getUrls (getSysUrl) — 获取系统 URL 配置

```
GET /api/starspark/v1/agent/authSetting/query
token: <token>

响应:
{
  "loginUrl": "https://xxx/sso/login",
  "feedbackUrl": "https://...",
  ...
}
```

#### getUserPackage — 查询用户套餐

```
POST /api/starspark/v1/user/packageQuery
token: <token>
```

#### checkUpdate — 插件更新检查

```
POST /api/starspark/v1/agent/authSetting/queryPluginLink?pluginType=<type>

响应: 包含最新版本下载链接
```

### 2. 对话 (ChatService)

#### talkAsk — 智能对话 (流式)

```
POST /api/starspark/v1/agent/chat/async/ask?token=<token>
Content-Type: application/json
token: <token>

请求体:
{
  "sessionId": "uuid",
  "scene": "TALK_INTELLIGENT",
  "requestId": "uuid",
  "modelCode": "spark-v3.5",
  "enterpriseId": "ent-uuid",
  "enableMultiModelSwitch": false,
  "token": "xxx",
  "language": "java",
  "timeStamp": 1713744000000,
  "fileName": "Main.java",
  "fileNameSuffix": "java",
  "projectName": "my-project",
  "agentVersion": "3.4.2",
  "commandType": "TALK:ASK",
  "taskName": "TALK_INTELLIGENT",
  "scene": "TALK_INTELLIGENT",
  "knowledgeBase": "codeKnowledgeBase",
  "userQuestionContent": "问题内容",
  "top_k": 1,
  "temperature": 0.5,
  "mup": null,
  "messages": [
    {"role": "system", "content": "系统提示..."},
    {"role": "user", "content": "问题: xxx"},
    {"role": "assistant", "content": "上一次回答..."},
    {"role": "user", "content": "当前问题..."}
  ]
}

响应: SSE (Server-Sent Events) 流

data: {"choices":[{"delta":{"content":"回答内容片段"}}]}
data: {"choices":[{"delta":{"content":"更多内容","reasoning_content":"思考过程"}}]}
data: [DONE]
```

#### talkAskSync — 对话 (同步)

```
POST /api/starspark/v1/agent/chat/sync/ask
Content-Type: application/json
token: <token>

请求体: 同 talkAsk 但无 stream 参数
响应: JSON (非流式)
```

### 3. 代码补全 (CodeService)

#### codeGenerate — 代码补全

```
POST /api/starspark/v1/agent/code/codeComplete
Content-Type: application/json
token: <token>

请求体:
{
  "requestId": "uuid",
  "modelCode": "spark-v3.5",
  ...
  "top_k": 1,
  "temperature": 1,
  "skipFilter": true,
  "stream": true,
  "fileName": "Main.java",
  "fileNameSuffix": "java",
  "content": "当前文件内容..."
}

响应: SSE 流 (同对话格式)
```

### 4. 代码辅助 (CommonService)

#### codeAssist — 代码辅助 (补全/单测等)

```
POST /api/starspark/v1/platform/code/assist
Content-Type: application/json
token: <token>

{
  "scene": "UNIT_TEST",
  ...
}

响应: SSE 流, useModel=true
```

#### recommendations — 推荐建议

```
POST /api/starspark/v1/agent/chat/recommendations
Content-Type: application/json
token: <token>
```

#### serverResourceInfo — 服务器资源信息

```
POST /api/starspark/v1/agent/code/queryUnitTestQueueInfo
Content-Type: application/json
token: <token>
```

### 5. Git 相关 (GitService)

#### getGitRepos — 获取 Git 仓库列表

```
POST /api/ragserver/v1/code/getUserRepos
Content-Type: application/json
token: <token>
```

#### gitLangList — 获取支持的语言列表

```
POST /api/ragserver/v1/code/getLanguages
Content-Type: application/json
token: <token>
```

#### gitCodeSearch — 代码搜索

```
POST /api/ragserver/v1/code/search
Content-Type: application/json
token: <token>
```

### 6. SQL 相关 (SqlService)

#### generateSql — SQL 生成 (流式)

```
POST /api/starspark/v1/agent/chat/generateSql
Content-Type: application/json
token: <token>

场景: GENERATE_SQL
响应: SSE 流
```

#### generateSqlDM — 达梦 SQL 生成 (同步)

```
POST /api/starspark/v1/agent/chat/sync/generateSql
Content-Type: application/json
token: <token>

响应: JSON
```

#### optimizeSql — SQL 优化 (流式)

```
POST /api/starspark/v1/agent/chat/optimizeSql
Content-Type: application/json
token: <token>

场景: OPTIMIZE_SQL
响应: SSE 流
```

#### transDaMengDDL — 达梦 DDL 转换

```
POST /api/starspark/v1/agent/chat/convertDmTableDDL
Content-Type: application/json
token: <token>

{
  "dmTableDDL": "CREATE TABLE ..."
}
```

### 7. 评审与提交 (ChatService)

#### review — 代码评审 (流式)

```
POST /api/starspark/v1/agent/chat/review
Content-Type: application/json
token: <token>

场景: REVIEW_CODE
响应: SSE 流
```

#### generateCommitMessage — 生成提交信息 (流式)

```
POST /api/starspark/v1/agent/chat/generateCommitMessage
Content-Type: application/json
token: <token>

场景: GENERATE_COMMIT_MESSAGE
响应: SSE 流
```

### 8. 单元测试 (TestService)

#### testCode — 生成单元测试代码

```
POST /api/starspark/v1/agent/code/generateUnitTest
Content-Type: application/json
token: <token>

超时: 120秒
```

#### testCase — 生成单测模板

```
POST /api/starspark/v1/agent/code/generateUnitTestCaseTemplate
Content-Type: application/json
token: <token>

超时: 120秒
```

### 9. 批量单测 (TestService, access-token 认证)

批量单测使用不同的认证方式——`access-token` header 而非 `token`。

#### batchUnitTestCreate — 创建批量单测任务

```
POST /restapi/unit/v1/createUnitTask
Content-Type: application/json
access-token: <username>

{
  "taskName": "...",
  ...
}
```

#### batchUnitTestList — 查询任务列表

```
POST /restapi/unit/v1/queryUnitTask
Content-Type: application/json
access-token: <username>

{
  "taskStatus": 1
}
```

#### batchUnitTestDownload — 下载任务结果

```
GET /restapi/unit/v1/exportByTaskId?taskId=<id>
access-token: <username>

响应: application/zip 文件流
```

#### batchUnitTestCancel — 取消任务

```
POST /restapi/unit/v1/cancelUnitTask
Content-Type: application/json
access-token: <username>

{
  "taskId": "xxx"
}
```

#### batchUnitTestDelete — 删除任务

```
POST /restapi/unit/v1/deleteUnitTask
Content-Type: application/json
access-token: <username>

{
  "taskId": "xxx"
}
```

#### batchUnitTestInProgress — 检查进行中任务

```
POST /restapi/unit/v1/isPendingTask
Content-Type: application/json
access-token: <username>

{}
```

### 10. 内联聊天 (ChatService)

#### inlineChat — 内联聊天 (流式)

```
POST /api/starspark/v1/agent/chat/inline/chat
Content-Type: application/json
token: <token>

场景: INLINE_CHAT_SELECTED 或 INLINE_CHAT
响应: SSE 流
```

### 11. RAG/代码搜索 (RagService)

#### ragBatchLoad — RAG 批量加载

```
POST /api/ragserver/v1/rag/incbatchload
Content-Type: application/json
token: <token>
```

#### searchInRepo — 仓库内搜索

```
POST /restapi/ragserver/v1/code/searchInRepo
Content-Type: application/json
token: <token>
```

#### searchInDoc — 文档搜索

```
POST /restapi/ragserver/v1/doc/search
Content-Type: application/json
token: <token>
```

#### searchInWebSearch — Web 搜索

```
POST /api/ragserver/v1/code/onlineSearch
Content-Type: application/json
token: <token>
```

#### parseWebDocument — 解析 Web 文档

```
POST /api/ragserver/v1/web/parseurl
Content-Type: application/json
token: <token>
```

### 12. 用户设置与权限 (UserService)

#### getPermission — 查询权限

```
POST /api/starspark/v1/agent/permission/queryUserPermissionPackageInfo
Content-Type: application/json
token: <token>
```

#### getFuncModelList — 查询功能模型列表

```
POST /api/starspark/v1/agent/permission/queryUserFuncModelList
Content-Type: application/json
token: <token>
```

#### generalSetting — 全局设置

```
GET /api/starspark/v1/agent/pluginSetting/queryGlobalSetting
token: <token>
```

#### tokenConfig — Token 配置

```
GET /api/starspark/v1/agent/pluginSetting/queryTokenSetting
token: <token>
```

#### getChatPromptTemplate — 获取对话提示模板

```
POST /api/starspark/v1/agent/prompt/query
Content-Type: application/json
token: <token>

{
  "role": "system",
  "scene": "TALK_INTELLIGENT",
  "language": "java"
}
```

### 13. 数据收集 (ReportService)

#### chatDataReport — 对话数据上报

```
POST /api/starspark/v1/agent/collect/chatDataContent
Content-Type: application/json
token: <token>
```

#### codeReport — 代码接受上报

```
POST /api/starspark/v1/agent/collect/codeAccept
Content-Type: application/json
token: <token>
```

#### codeReject — 代码拒绝上报

```
POST /api/starspark/v1/agent/action/rejectCode
Content-Type: application/json
token: <token>
```

#### recordCommitInfo — 提交信息记录

```
POST /api/starspark/v1/agent/collect/commitCodeData
Content-Type: application/json
token: <token>
```

#### unitTestCollection — 单测统计数据

```
POST /api/starspark/v1/agent/collect/unitTestStatistics
Content-Type: application/json
token: <token>
```

#### unitTestCollectionGenerate — 单测生成数据

```
POST /api/starspark/v1/agent/collect/generateUnitTestData
Content-Type: application/json
token: <token>
```

#### unitTestCollectionCommit — 单测提交数据

```
POST /api/starspark/v1/agent/collect/commitUnitTestData
Content-Type: application/json
token: <token>
```

#### requestTimeAnalysis — 请求时间分析上报

```
POST /api/starspark/v1/agent/collect/uploadRequestTime
Content-Type: application/json
token: <token>
```

#### userAction — 用户操作记录

```
POST /api/starspark/v1/agent/action/saveUserAction
Content-Type: application/json
token: <token>
```

### 14. 知识库 (UserService)

#### knowledgeList — 文档知识库列表

```
POST /restapi/ragserver/v1/doc/knowledgeList
Content-Type: application/json
token: <token>
```

#### codeKnowledgeStatus — 代码知识库状态

```
POST /restapi/ragserver/v1/rag/codeK/personal/init/status
Content-Type: application/json
token: <token>
```

#### authPersonalCodeKnowledge — 授权个人代码知识库

```
POST /restapi/ragserver/v1/rag/codeK/personal/auth
Content-Type: application/json
token: <token>
```

#### codeKnowledgeReVectorized — 知识库重新向量化

```
POST /restapi/ragserver/v1/codeknowledge/reVectorized
Content-Type: application/json
token: <token>
```

#### updateGitToken — 更新 Git Token

```
POST /restapi/ragserver/v1/rag/codeK/updateGitToken
Content-Type: application/json
token: <token>
```

#### queryCategory — 反馈分类查询

```
POST /api/starspark/v1/agent/feedback/queryCategory
Content-Type: application/json
token: <token>
```

### 15. 代码增强 (UserService)

#### chatEvaluation — 对话评价

```
POST /api/starspark/v1/agent/chat/evaluate
Content-Type: application/json
token: <token>
```

#### chatFeedback — 对话反馈

```
POST /api/starspark/v1/agent/chat/feedback
Content-Type: application/json
token: <token>
```

#### getWordWriterConfig — Word 写作配置

```
GET /api/starspark/v1/agent/wordWriter/config
token: <token>
```

## SSE (Server-Sent Events) 流式协议

### SSE 响应格式

Agent 使用 `eventsource-parser` 库解析 SSE 流。响应格式遵循 OpenAI 兼容格式：

```
data: {"choices":[{"delta":{"content":"回答内容片段","reasoning_content":"推理过程"}}]}
data: {"choices":[{"delta":{"content":"更多内容"}}]}
data: {"choices":[{"delta":{"content":"结束"},"finish_reason":"stop"}]}
data: [DONE]
```

### SSE 解析逻辑 (_getSSEData)

```javascript
// 每个 SSE data 行解析为:
{
  text: choices[0].delta.content,           // 正式回答内容
  reasonText: choices[0].delta.reasoning_content,  // 推理/思考内容
  reason: choices[0].finish_reason,         // 结束原因
  error: error,                             // 错误信息
  ended: true                               // [DONE] 标记
}
```

### SSE 超时处理

- 流数据到达间隔超时: 60 秒
- 超时后发送错误消息: `{"error": {"code": 504, "message": "网络异常，请检查网络"}}`

### SSE 错误码

| 码 | 含义 |
|----|------|
| 5951 | 模型负载保护触发 |
| 504 | 流超时 |

## 命令路由映射 (CHAT_APIS)

Agent 内部将 WebSocket 命令映射到不同的 API 端点和场景：

| WebSocket 命令 | API 端点 | 场景 (scene) | 流式 |
|---------------|----------|-------------|------|
| `CODE:COMPLETE` | `codeGenerate` | `COMPLETE_CODE_WITH_CONTEXT` | Yes |
| `TALK:ASK` | `talkAsk` | (动态) | Yes |
| `TALK:QUESTION_ENHANCE` | `talkAsk` | (动态) | Yes |
| `TALK:PREDICT` | `talkAskSync` | (动态) | No |
| `TALK:INTELLIGENT` | `talkAsk` | (动态) | Yes |
| `SQL:GENERATE` | `generateSql` | `GENERATE_SQL` | Yes |
| `SQL:OPTIMIZE` | `optimizeSql` | `OPTIMIZE_SQL` | Yes |
| `SQL:GENERATE_DM` | `generateSqlDM` | `GENERATE_SQL` | No |
| `SQL:OPTIMIZE_DM` | `optimizeSqlDM` | `OPTIMIZE_SQL` | No |
| `GIT:COMMIT_MESSAGE` | `generateCommitMessage` | `GENERATE_COMMIT_MESSAGE` | Yes |
| `GIT:REVIEW` | `review` | `REVIEW_CODE` | Yes |
| `TEST:MAKE_CASE` | `testCase` | — | No |
| `TEST:MAKE_CASE_JAVA` | `testCase` | — | No |
| `TEST:MAKE_CODE` | `testCode` | — | No |
| `TEST:OTHER` | `codeAssist` | `UNIT_TEST` | Yes |
| `DIALOG:REQUEST` | `inlineChat` | `INLINE_CHAT_SELECTED` | Yes |

## WebSocket 消息转发机制

### Agent 收到 WebSocket 消息后的处理流程

```
Plugin → Agent (WebSocket JSON)
    │
    ▼
getWSMessage(ws_data) → JSON.parse(data.toString())
    │
    ├─ 记录 OpenTelemetry Span (message.id, message.command, message.sessionId)
    │
    ├─ 根据 command 前缀路由到 Controller:
    │   ├─ "CODE:"    → CodeController
    │   ├─ "TALK:"    → TalkController
    │   ├─ "SQL:"     → SqlController
    │   ├─ "TEST:"    → TaskController
    │   ├─ "GIT:"     → TalkController (复用 ChatService)
    │   ├─ "DIALOG:"  → InlineChatController
    │   ├─ "USER:"    → UserController
    │   └─ "LOG:"     → LogController
    │
    ├─ Controller.handleMessageEvent(msg)
    │   ├─ 检查路由配置 (routes[command])
    │   ├─ 检查 needLogin → 未登录发送 401 错误
    │   ├─ 注入 _scene, _recordDialog, _sendDialog 等上下文
    │   └─ 调用对应的 routeFunc
    │
    ├─ Service 调用 HTTP API
    │   ├─ 构建请求: getBaseData() + 业务数据
    │   ├─ 注入 headers: Content-Type, token, traceparent
    │   ├─ 注入 abort signal
    │   └─ 发送 HTTPS 请求
    │
    └─ 响应回传:
        ├─ 非流式: await response.json() → sendWSMessage(ws, data, id)
        └─ 流式: SSE 解析 → 逐段 sendWSMessage(ws, chunk, id)
```

### Agent → Plugin 响应格式

```json
{
  "id": "原始消息ID",
  "code": 200,
  "data": { ... },
  "msg": null
}
```

错误响应:
```json
{
  "id": "原始消息ID",
  "code": 400,
  "msg": "错误消息",
  "data": { "code": "5001", "message": "..." }
}
```

## SSL/TLS 配置

Agent 默认忽略 HTTPS 证书验证（`agent.ignoreHttps` 配置项默认 `true`）:

```javascript
// ServiceBase._getInitOpt
if (this.ignoreHttps && url.startsWith("https://")) {
  options.agent = new https.Agent({ rejectUnauthorized: false });
}
```

## Agent WebSocket 服务端

### 连接验证

```javascript
// Agent.onconnect
const url = request.url;
// 仅接受匹配 /ws/[^/]+$ 的路径
if (!/\/ws\/[^/]+$/.test(url)) {
  ws.close(); // 关闭非法连接
}
// 从 URL 提取 IDE 类型: /ws/idea → "idea"
const platform = url.split("/").pop();
```

### 初始化响应

新连接建立后，Agent 立即发送初始化消息:

```json
{
  "id": "init",
  "code": 200,
  "data": {
    "clientId": "uuid",
    "version": "3.4.2",
    "tipinfo": []
  }
}
```

### 控制器注册

Agent 为每个连接实例化以下控制器:

| Controller | 职责 |
|-----------|------|
| TalkController | 对话、代码操作 |
| CodeController | 代码补全 |
| InlineChatController | 内联聊天 |
| TaskController | 单元测试 |
| UserController | 用户管理 |
| SqlController | SQL 操作 |
| LogController | 日志 |
| ActionController | 操作上报 |
| DialogController | 对话管理 |
| AbortController | 请求取消 |

## Token 管理流程

```
1. Agent 启动 → 无 token
2. Plugin 发送 LOGIN/LOGIN_CHECK 命令
3. LoginService.toLogin():
   a. 尝试从本地缓存读取 token (nedb 数据库)
   b. 如果有缓存 token → validToken() 验证
   c. 如果验证成功 → loginSuccess()
   d. 如果验证失败 → 获取登录 URL (SSO)
4. SSO 登录流程:
   a. getSysUrl() → 获取 loginUrl
   b. 构建登录 URL: loginUrl + ?clientId=xxx
   c. 返回 URL 给 Plugin → WebView 打开浏览器
   d. 轮询 checkLoginStatus(clientId) → 等待登录完成
5. loginSuccess():
   a. 保存 token 到 ServiceBase.token
   b. 保存到本地 nedb 数据库 (持久化)
   c. 设置 OpenTelemetry 用户属性
   d. 广播 login 事件到其他进程
   e. 通过 WebSocket 发送 "login-info" 给 Plugin
6. 后续所有 HTTP 请求使用 token header
7. Token 失效 → 401 → 发送 401 错误到 Plugin → 重新登录
```

## URL 路径规则

| 前缀 | 用途 | 代理规则 |
|------|------|---------|
| `/api/starspark/v1/agent/*` | AI 服务 | 直连 |
| `/api/starspark/v1/platform/*` | 平台服务 | 直连 |
| `/api/starspark/v1/chat/user/*` | 用户认证 | 直连 |
| `/api/starspark/v1/user/*` | 用户信息 | 直连 |
| `/api/usercenter/v1/*` | 用户中心 | 直连 |
| `/api/ragserver/v1/*` | RAG 搜索 | 直连 |
| `/restapi/ragserver/v1/*` | RAG 内部 | 直连 |
| `/restapi/unit/v1/*` | 批量单测 | 直连 |

baseURL 从 `config.json` 中的 `agent.url` 读取，默认 `https://iflycode-xfsaas.xfyun.cn`。可通过环境变量 `BASE_URL` 覆盖。

## 完整 API 汇总表

| # | APIS Key | URL | Method | Stream | Timeout |
|---|----------|-----|--------|--------|---------|
| 1 | loginByAccount | /api/usercenter/v1/user/common/login | POST | No | 10s |
| 2 | validToken | /api/starspark/v1/chat/user/valid | POST | No | 10s |
| 3 | loginStatus | /api/starspark/v1/user/authorizationQuery | GET | No | 10s |
| 4 | exitLogin | /api/starspark/v1/chat/user/logOut | POST | No | 10s |
| 5 | getUrls | /api/starspark/v1/agent/authSetting/query | GET | No | 10s |
| 6 | checkUpdate | /api/starspark/v1/agent/authSetting/queryPluginLink | POST | No | 10s |
| 7 | getPermission | /api/starspark/v1/agent/permission/queryUserPermissionPackageInfo | POST | No | 10s |
| 8 | getFuncModelList | /api/starspark/v1/agent/permission/queryUserFuncModelList | POST | No | 10s |
| 9 | generalSetting | /api/starspark/v1/agent/pluginSetting/queryGlobalSetting | GET | No | - |
| 10 | tokenConfig | /api/starspark/v1/agent/pluginSetting/queryTokenSetting | GET | No | - |
| 11 | getChatPromptTemplate | /api/starspark/v1/agent/prompt/query | POST | No | - |
| 12 | getWordWriterConfig | /api/starspark/v1/agent/wordWriter/config | GET | No | 10s |
| 13 | getUserPackage | /api/starspark/v1/user/packageQuery | POST | No | 10s |
| 14 | talkAsk | /api/starspark/v1/agent/chat/async/ask | POST | Yes | - |
| 15 | talkAskSync | /api/starspark/v1/agent/chat/sync/ask | POST | No | - |
| 16 | codeGenerate | /api/starspark/v1/agent/code/codeComplete | POST | No | 120s |
| 17 | codeAssist | /api/starspark/v1/platform/code/assist | POST | Yes | - |
| 18 | serverResourceInfo | /api/starspark/v1/agent/code/queryUnitTestQueueInfo | POST | No | - |
| 19 | recommendations | /api/starspark/v1/agent/chat/recommendations | POST | No | - |
| 20 | review | /api/starspark/v1/agent/chat/review | POST | Yes | - |
| 21 | generateCommitMessage | /api/starspark/v1/agent/chat/generateCommitMessage | POST | Yes | - |
| 22 | generateSql | /api/starspark/v1/agent/chat/generateSql | POST | Yes | - |
| 23 | generateSqlDM | /api/starspark/v1/agent/chat/sync/generateSql | POST | No | - |
| 24 | optimizeSql | /api/starspark/v1/agent/chat/optimizeSql | POST | Yes | - |
| 25 | optimizeSqlDM | /api/starspark/v1/agent/chat/sync/optimizeSql | POST | No | - |
| 26 | transDaMengDDL | /api/starspark/v1/agent/chat/convertDmTableDDL | POST | No | - |
| 27 | inlineChat | /api/starspark/v1/agent/chat/inline/chat | POST | Yes | - |
| 28 | testCase | /api/starspark/v1/agent/code/generateUnitTestCaseTemplate | POST | No | 120s |
| 29 | testCode | /api/starspark/v1/agent/code/generateUnitTest | POST | No | 120s |
| 30 | batchUnitTestCreate | /restapi/unit/v1/createUnitTask | POST | No | - |
| 31 | batchUnitTestList | /restapi/unit/v1/queryUnitTask | POST | No | - |
| 32 | batchUnitTestDownload | /restapi/unit/v1/exportByTaskId | GET | No | - |
| 33 | batchUnitTestCancel | /restapi/unit/v1/cancelUnitTask | POST | No | - |
| 34 | batchUnitTestDelete | /restapi/unit/v1/deleteUnitTask | POST | No | - |
| 35 | batchUnitTestInProgress | /restapi/unit/v1/isPendingTask | POST | No | - |
| 36 | chatEvaluation | /api/starspark/v1/agent/chat/evaluate | POST | No | - |
| 37 | chatFeedback | /api/starspark/v1/agent/chat/feedback | POST | No | - |
| 38 | chatDataReport | /api/starspark/v1/agent/collect/chatDataContent | POST | No | - |
| 39 | codeReport | /api/starspark/v1/agent/collect/codeAccept | POST | No | - |
| 40 | codeReject | /api/starspark/v1/agent/action/rejectCode | POST | No | - |
| 41 | recordCommitInfo | /api/starspark/v1/agent/collect/commitCodeData | POST | No | - |
| 42 | unitTestCollection | /api/starspark/v1/agent/collect/unitTestStatistics | POST | No | - |
| 43 | unitTestCollectionGenerate | /api/starspark/v1/agent/collect/generateUnitTestData | POST | No | - |
| 44 | unitTestCollectionCommit | /api/starspark/v1/agent/collect/commitUnitTestData | POST | No | - |
| 45 | requestTimeAnalysis | /api/starspark/v1/agent/collect/uploadRequestTime | POST | No | - |
| 46 | userAction | /api/starspark/v1/agent/action/saveUserAction | POST | No | - |
| 47 | queryCategory | /api/starspark/v1/agent/feedback/queryCategory | POST | No | - |
| 48 | gitRepos | /api/ragserver/v1/code/getUserRepos | POST | No | - |
| 49 | gitLangList | /api/ragserver/v1/code/getLanguages | POST | No | - |
| 50 | gitCodeSearch | /api/ragserver/v1/code/search | POST | No | - |
| 51 | ragBatchLoad | /api/ragserver/v1/rag/incbatchload | POST | No | - |
| 52 | searchInRepo | /restapi/ragserver/v1/code/searchInRepo | POST | No | - |
| 53 | searchInDoc | /restapi/ragserver/v1/doc/search | POST | No | - |
| 54 | searchInWebSearch | /api/ragserver/v1/code/onlineSearch | POST | No | - |
| 55 | parseWebDocument | /api/ragserver/v1/web/parseurl | POST | No | - |
| 56 | knowledgeList | /restapi/ragserver/v1/doc/knowledgeList | POST | No | - |
| 57 | codeKnowledgeStatus | /restapi/ragserver/v1/rag/codeK/personal/init/status | POST | No | - |
| 58 | authPersonalCodeKnowledge | /restapi/ragserver/v1/rag/codeK/personal/auth | POST | No | - |
| 59 | codeKnowledgeReVectorized | /restapi/ragserver/v1/codeknowledge/reVectorized | POST | No | - |
| 60 | codeKnowledgeUpdateGitToken | /restapi/ragserver/v1/rag/codeK/updateGitToken | POST | No | - |
| 61 | codeKnowledgeList | /restapi/ragserver/v1/rag/codeK/codeKnowledgeList | POST | No | - |
| 62 | repoKeyEnable | /restapi/ragserver/v1/rag/repoKeyEnable | POST | No | - |
| 63 | repoLangExtEnable | /restapi/ragserver/v1/rag/repoLangExtEnable | GET | No | - |
| 64 | repoSearchReady | /restapi/ragserver/v1/rag/repoKeyDialogEnable | POST | No | - |

共 **64 个 API 端点**。
