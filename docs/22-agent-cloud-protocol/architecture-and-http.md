## 通信架构

```
IDE Plugin (Java)
    │ ws://127.0.0.1:&#123;port&#125;/ws/idea
    ▼
Agent (Node.js)
    │ HTTPS (node-fetch)
    ▼
Cloud Server
    ├─ https://saas.api.example.com      (SaaS)
    ├─ https://pro.api.example.com       (Pro)
    └─ http://10.0.x.x:port                 (内网开发)
```

## 基础 HTTP 请求格式

### 请求构建 (ServiceBase._getInitOpt)

```http
POST /api/starspark/v1/agent/chat/async/ask HTTP/1.1
Content-Type: application/json
token: &lt;用户token&gt;
traceparent: <W3C Trace Context>

&#123;
  "requestId": "uuid-xxx",
  "sessionId": "session-uuid",
  ...
&#125;
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
&#123;
  "code": "0",        // 或 "resCode": "0"
  "msg": "success",   // 或 "message"
  "obj": &#123; ... &#125;,     // 成功时数据在 obj 或 data 字段
  "data": &#123; ... &#125;
&#125;
```

Agent 提取逻辑：成功时取 `obj || data`，否则抛出 `iFlyCodeError(msg, code)`。
