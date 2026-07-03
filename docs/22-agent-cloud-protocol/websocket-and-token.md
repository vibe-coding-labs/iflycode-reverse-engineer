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
&#123;
  "id": "原始消息ID",
  "code": 200,
  "data": &#123; ... &#125;,
  "msg": null
&#125;
```

错误响应:
```json
&#123;
  "id": "原始消息ID",
  "code": 400,
  "msg": "错误消息",
  "data": &#123; "code": "5001", "message": "..." &#125;
&#125;
```

## SSL/TLS 配置

Agent 默认忽略 HTTPS 证书验证（`agent.ignoreHttps` 配置项默认 `true`）:

```javascript
// ServiceBase._getInitOpt
if (this.ignoreHttps && url.startsWith("https://")) &#123;
  options.agent = new https.Agent(&#123; rejectUnauthorized: false &#125;);
&#125;
```

## Agent WebSocket 服务端

### 连接验证

```javascript
// Agent.onconnect
const url = request.url;
// 仅接受匹配 /ws/[^/]+$ 的路径
if (!/\/ws\/[^/]+$/.test(url)) &#123;
  ws.close(); // 关闭非法连接
&#125;
// 从 URL 提取 IDE 类型: /ws/idea → "idea"
const platform = url.split("/").pop();
```

### 初始化响应

新连接建立后，Agent 立即发送初始化消息:

```json
&#123;
  "id": "init",
  "code": 200,
  "data": &#123;
    "clientId": "uuid",
    "version": "3.4.2",
    "tipinfo": []
  &#125;
&#125;
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

baseURL 从 `config.json` 中的 `agent.url` 读取，默认 `https://saas.api.example.com`。可通过环境变量 `BASE_URL` 覆盖。

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
