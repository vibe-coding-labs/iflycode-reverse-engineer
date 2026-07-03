## 3. 端到端数据流图

### 3.1 完整协议栈

```
┌─────────────────────────────────────────────────────────────────────┐
│                        用户交互层                                    │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐           │
│  │ WebView  │  │ Editor   │  │ Inline   │  │ Inlay    │           │
│  │ (JCEF)  │  │ (IntelliJ)│  │ Chat    │  │ Hints   │           │
│  └────┬─────┘  └────┬─────┘  └────┬─────┘  └────┬─────┘           │
│       │              │              │              │                 │
│  ┌────▼──────────────▼──────────────▼──────────────▼────┐          │
│  │              JS Bridge / Java API                      │          │
│  │  ideaUtil: window.myObject.sendMessage()              │          │
│  │  Java: WebViewWindowPanel.handleRequest()             │          │
│  └────────────────────┬──────────────────────────────────┘          │
│                       │                                              │
│  ┌────────────────────▼──────────────────────────────────┐          │
│  │              Java Service Layer                        │          │
│  │  ChatService, CodeCheckService, SqlService,            │          │
│  │  GitReviewService, CodeSearchService, UnitTestService, │          │
│  │  CodeCompleteService, InlineChatCommandService         │          │
│  └────────────────────┬──────────────────────────────────┘          │
│                       │                                              │
│  ┌────────────────────▼──────────────────────────────────┐          │
│  │              WebSocket Client (OkHttp)                 │          │
│  │  ws://127.0.0.1:&#123;port&#125;/ws/idea                        │          │
│  │  MessageDto → JSON → webSocket.send()                 │          │
│  └────────────────────┬──────────────────────────────────┘          │
└───────────────────────┼──────────────────────────────────────────────┘
                        │ localhost WebSocket (无 TLS, 无认证)
┌───────────────────────▼──────────────────────────────────────────────┐
│                    Agent (Node.js)                                   │
│  ┌────────────────────▼──────────────────────────────────┐          │
│  │              WebSocket Server (ws)                     │          │
│  │  解析 JSON → 按 command 路由到 Service                │          │
│  └────────────────────┬──────────────────────────────────┘          │
│                       │                                              │
│  ┌────────────────────▼──────────────────────────────────┐          │
│  │              Agent Service Layer                       │          │
│  │  ChatService, SqlService, TestService,                 │          │
│  │  RagService, GitService, LoginService, CodeService     │          │
│  └────────────────────┬──────────────────────────────────┘          │
│                       │                                              │
│  ┌────────────────────▼──────────────────────────────────┐          │
│  │              HTTP Client (axios/fetch)                 │          │
│  │  星火 API, RAG 服务, 用户中心                          │          │
│  │  Headers: &#123; access-token: userToken &#125;                  │          │
│  └────────────────────┬──────────────────────────────────┘          │
└───────────────────────┼──────────────────────────────────────────────┘
                        │ HTTPS (TLS 1.2+)
┌───────────────────────▼──────────────────────────────────────────────┐
│                    云端服务                                          │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐           │
│  │ 星火 API │  │ RAG 服务 │  │ 用户中心 │  │ 单测服务 │           │
│  │ (SSE)   │  │ (REST)  │  │ (REST)  │  │ (REST)  │           │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘           │
└─────────────────────────────────────────────────────────────────────┘
```

### 3.2 加密在协议栈中的位置

```
层              │ 加密状态          │ 说明
────────────────┼───────────────────┼──────────────────────────────
WebView ↔ Java  │ 无加密 (明文)     │ JS Bridge: JSON.stringify
Java ↔ Agent    │ 无加密 (明文)     │ WebSocket ws:// (非 wss://)
Agent ↔ 云端    │ TLS 1.2+ (HTTPS) │ 星火 API, RAG, 用户中心
Agent 内部      │ SM2/SM4/RSA/AES  │ 仅用于登录密码加密和权限缓存
WebView 内部    │ 无加密            │ 前端不执行任何加密操作
```

### 3.3 Agent 端加密套件完整定义

```javascript
// 统一加密接口
function encrypt(data, algorithm, ...args) &#123;
  switch (algorithm) &#123;
    case "SM2": return encryptSM2(data, ...args);  // 国密 SM2 非对称加密
    case "SM4": return encryptSM4(data, ...args);  // 国密 SM4 对称加密
    case "RSA": return encryptRSA(data, ...args);  // RSA 非对称加密
    case "AES": return encryptAES(data, ...args);  // AES-256-CTR 对称加密
    case "MD5": return cryptoMd5(data);            // MD5 哈希
    default:   return data;
  &#125;
&#125;

function decrypt(data, algorithm, ...args) &#123;
  switch (algorithm) &#123;
    case "SM4": return decryptSM4(data, ...args);
    case "AES": return decryptAES(data, ...args);
    default:   return data;
  &#125;
&#125;
```

#### 各算法实现细节

| 算法 | 用途 | 密钥 | 模式 | 密钥来源 |
|------|------|------|------|---------|
| **SM2** | 数据加密传输 | `SM2_PUB_KEY = "已脱敏"` | C1C3C2, mode=1 | 硬编码 |
| **SM4** | 权限缓存加密 | `SM4_KEY = "已脱敏"` | PKCS#5 padding | 硬编码 |
| **RSA** | 登录密码加密 | `RSA_PUB_KEY = "已脱敏"` (1024-bit) | PKCS1 padding, 64-byte 分块 | 硬编码 |
| **AES** | 通用数据加密 | `AES_KEY = "已脱敏"` | AES-256-CTR | 硬编码 |
| **AES IV** | CTR 初始向量 | `AES_IV = "已脱敏"` | 固定 IV | 硬编码 |
| **MD5** | 文件完整性校验 | N/A | crypto.createHash("md5") | N/A |

#### 加密使用场景

| 场景 | 算法 | 调用位置 | 数据 |
|------|------|---------|------|
| 账号密码登录 | RSA | LoginService.loginByForm() | 用户密码 |
| 权限缓存 | SM4 | 权限数据存储/读取 | 权限列表 |
| 数据传输加密 | SM2 | 部分数据发送 | 敏感数据 |
| 通用加密 | AES | 内部数据加密 | 通用数据 |
| 文件完整性 | MD5 | MessageDto.md5 | 文件内容哈希 |

---
