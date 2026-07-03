# 03 Agent 服务端接口

## 生产环境

| 服务 | URL |
|------|-----|
| SaaS 后端 | `https://saas.api.example.com` |
| API 服务 | `https://pro.api.example.com` |
| OTLP 追踪 | `https://saas.api.example.com/v1/traces` |

## API 端点

### AI 功能 (`/api/starspark/v1/agent/`)

| 端点 | 功能 |
|------|------|
| `chat/*` | 智能对话、内联聊天、评审、优化、SQL 生成 |
| `code/*` | 代码补全、单测生成 |
| `collect/*` | 遥测数据收集 |
| `permission/*` | 用户权限 |
| `pluginSetting/*` | 插件设置 |
| `prompt/query` | Prompt 管理 |
| `authSetting/*` | 认证设置 |

### 用户中心 (`/api/`)

| 端点 | 功能 |
|------|------|
| `chat/user/*` | 登录/登出 |
| `usercenter/v1/user/common/login` | 用户统一登录 |
| `starspark/v1/user/*` | 授权/套餐查询 |

### RAG 搜索 (`/api/ragserver/v1/`)

| 端点 | 功能 |
|------|------|
| `code/*` | 代码搜索、在线搜索 |
| `rag/*` | RAG 索引构建 |
| `web/*` | URL 解析 |

### 平台服务

| 端点 | 功能 |
|------|------|
| `starspark/v1/platform/code/assist` | 平台级代码辅助 |

## Agent 版本信息

```json
// agent/bin/package.json
{
  "name": "iflycode-agent",
  "version": "3.4.2"
}
```

## 内部开发环境 (从 agent bundle 中提取)

| 环境 | URL |
|------|-----|
| 开发后端 | `http://10.0.0.1` |
| 备用开发后端 | `http://10.0.0.2` |
| 开发后端 | `http://10.0.0.3` |
| SaaS 测试 | `http://10.0.0.4:8080` |
| 内部追踪收集器 | `http://10.0.0.5:4318/v1/traces` |

## 完整请求流程

```
IDE Plugin                        Local Agent                    Cloud Server
─────────                        ───────────                    ────────────
                                    │
  WebSocket connect ───────────────►│
  ws://127.0.0.1:{port}/ws/idea    │
                                    │
  MessageDto (JSON) ───────────────►│
  {                                 │   HTTPS POST ──────────────►│
    "id": "uuid",                   │   /api/starspark/v1/...     │
    "command": "talk_intelligent",  │   Authorization: Bearer ... │
    "data": {...}                   │                             │
  }                                 │   ◄─────────────────────────│
                                    │   JSON Response
  ◄────────────────────────────────│
  ResponseDto / ResponseStreamDto   │
  {                                 │
    "id": "uuid",                   │
    "code": "0",                    │
    "data": { "text": "...",        │
              "ended": false }      │
  }                                 │
```

## 认证方式

Agent 与云端通信使用 Token 认证（注意: 不是 Bearer Token）：

| 认证方式 | Header 格式 | 使用场景 |
|---------|------------|---------|
| Token | `token: <value>` | 绝大多数 API |
| Access Token | `access-token: <username>` | 批量单测 API |
| Client ID | `clientId: <value>` | 登录状态检查 |

- Token 通过用户登录获取，持久化到本地 nedb 数据库 (`~/.iflycode/`)
- Agent 进程持有 token，插件不直接与云端通信
- 登录 URL 构造: `{loginUrl}?clientId={clientId}` (注意是 clientId 而非 token)
- 账号密码登录使用 RSA 加密 (1024-bit, 分块 64 bytes)

详细 API 端点列表和请求/响应格式请参阅 [22-agent-cloud-protocol.md](22-agent-cloud-protocol.md)。
