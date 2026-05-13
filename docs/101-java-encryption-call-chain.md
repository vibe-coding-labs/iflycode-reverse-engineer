# iFlyCode Java 端加密调用链完整分析

> 文档编号: 101  
> 分析对象: iFlyCode 3.4.2-222 JetBrains Plugin (Java 端)  
> 分析日期: 2026-05-14  
> 交叉参考: docs/90 (agent/service), docs/91 (service), docs/92 (util), docs/94 (startup/webview), docs/97 (settings/updater/diff)

---

## 1. Java 端加密相关类清单

### 1.1 核心加密/认证相关类

| 类名 | 包路径 | 加密角色 |
|------|--------|----------|
| `PluginStartupActivity` | `com.aicode` | API Key 存储/获取 (通过 `Key<String>` UserData) |
| `AICodeSettingsState` | `com.aicode.settings` | 凭证/Token/URL 持久化存储 |
| `UserService` | `com.aicode.agent.service` | 登录流程处理, Token 接收 |
| `UserInfoDto` | `com.aicode.agent.dto` | 用户认证数据传输对象 (含 token 字段) |
| `LoginInfo` | `com.aicode.agent.dto` | Agent 版本更新信息 (含 md5 校验) |
| `MessageDto` | `com.aicode.agent.dto` | WebSocket 消息 DTO (含 md5, traceparent 字段) |
| `ConnectConfigDto` | `com.aicode.agent.dto` | 数据库连接配置 (含 user, password 明文字段) |
| `PluginWebsocketClient` | `com.aicode.agent` | WebSocket 通信, 消息序列化与发送 |
| `PluginWebsocketListener` | `com.aicode.agent` | WebSocket 消息接收与分发 |
| `SocketMessageHandleListener` | `com.aicode.agent` | 消息路由处理 |
| `OpenTelemetryConfig` | `com.aicode.apm` | SSL 证书验证禁用, TrustManager 实现 |
| `OpenTelemetryService` | `com.aicode.apm` | APM 连接管理, SSL 上下文配置 |
| `CommonService` | `com.aicode.agent.service` | URL Token 附加, apiKey 注入 |
| `SqlService` | `com.aicode.agent.service` | 数据库密码明文传输 |
| `PluginUpdater` | `com.aicode.updater` | 更新包 MD5 校验 |

### 1.2 关键发现: Java 端不执行任何加密操作

**Java 插件端不包含任何加密/解密代码。** 所有加密操作 (RSA, SM4, SM2, AES) 均在 Agent Node.js 进程中执行。Java 端仅负责:
- 存储/传递 Token (明文)
- 构造消息 DTO 并序列化为 JSON
- 通过 WebSocket 发送 JSON 字符串
- 禁用 SSL 证书验证

---

## 2. 登录流程加密链

### 2.1 完整登录流程

```
用户点击登录
    |
    v
Java: UserService.handleAction(LOGIN_INIT)
    |
    v
Java: PluginWebsocketClient.sendWsMessage(CommandEnum.USER_LOGIN, project)
    |  消息格式: { id: uuid, command: "user_login", data: { type: 1 } }
    |  注意: Java 不发送任何凭据, 仅发送登录请求
    |
    v
Agent (Node.js): 收到 USER_LOGIN 命令
    |
    v
Agent: LoginService.getLoginUrl() -> 获取二维码/登录页面 URL
    |
    v
Agent: 通过 WebSocket 返回 loginUrl 给 Java
    |
    v
Java: UserService.getLoginUrl() -> 接收 URL
    |
    v
Java: BrowserUtil.browse(loginUrl + "&pluginVersion=..." + "&ideType=IDEA&type=outer")
    |  用户在浏览器中完成认证 (扫码/账密)
    |
    v
Agent: 轮询 checkLogin() 等待用户完成认证
    |
    v
[账密登录路径 - 仅在 Agent 端执行]
Agent: LoginService.loginByAccount(user, password)
    |  const encryptedUser = encrypt(user, "RSA")[0]
    |  const encryptedPw = encrypt(password, "RSA")[0]
    |  -> 调用 /api/usercenter/v1/user/common/login
    |     body: { user: RSA(user), pwCode: RSA(password) }
    |
    v
[扫码登录路径]
Agent: 通过服务端回调获取 token
    |
    v
Agent: 返回 LOGIN_INFO 命令给 Java
    |  包含: { userInfo: { token, user, sysUrls, enterpriseDto, ... } }
    |
    v
Java: UserService.getLoginInfo(jsonObject, project)
    |  UserInfoDto userInfoDto = new Gson().fromJson(...)
    |  PluginStartupActivity.setApiKey(userInfoDto.getToken())
    |  AICodeSettingsState.userName = userInfoDto.getUser()
    |  -> Token 以明文存储在 Project UserData 中
```

### 2.2 关键代码分析

**Java 端登录请求 (PluginWebsocketClient.wsInit):**
```java
// 发送 USER_LOGIN 命令, 不含任何凭据
INITID = IdUtil.fastSimpleUUID();
MessageDto msg = new MessageDto(INITID, CommandEnum.USER_LOGIN.getType());
JsonObject data = new JsonObject();
data.addProperty("type", 1);  // 仅指定登录类型
msg.setData(data);
PluginWebsocketClient.sendWsMessage(msg, project);
```

**Java 端 Token 存储 (UserService.getLoginInfo):**
```java
// 从 Agent 响应中提取 Token, 明文存储
UserInfoDto userInfoDto = new Gson().fromJson(jsonObject.get("userInfo"), UserInfoDto.class);
PluginStartupActivity.setApiKey(userInfoDto.getToken());  // 明文存入 Project UserData
AICodeSettingsState.userName = userInfoDto.getUser();     // 明文存入设置
```

**Agent 端 RSA 加密 (index.js webpack module 1618):**
```javascript
// Agent 端执行 RSA 加密, Java 端不参与
async loginByAccount(d, E, g, T, A) {
    const S = encrypt(T, "RSA")[0];  // RSA 加密用户名
    const v = encrypt(A, "RSA")[0];  // RSA 加密密码
    const I = await this.loginByForm(d, g, { user: S, pwCode: v });
    // POST /api/usercenter/v1/user/common/login
}
```

### 2.3 Token 存储机制

| 存储位置 | 类型 | 加密状态 | 生命周期 |
|----------|------|----------|----------|
| `Project.userData(API_KEY)` | `Key<String>` | 明文 | 项目会话 |
| `AICodeSettingsState.userName` | XML 持久化 | 明文 | 跨会话 |
| `AICodeSettingsState.loginUrl` | XML 持久化 | 明文 | 跨会话 |
| `AICodeSettingsState.feedbackUrl` | XML 持久化 | 明文 | 跨会话 |
| `AICodeSettingsState.enterpriseId` | XML 持久化 | 明文 | 跨会话 |

**AICodeSettingsState 持久化文件:** `AICodeSettingsPlugin.xml` (在 IDE config 目录下)

---

## 3. WebSocket 消息加密

### 3.1 消息格式

**Java -> Agent (请求):**
```json
{
  "id": "uuid-string",
  "command": "command_type_string",
  "stream": true,
  "timeStamp": 1715678901234,
  "traceparent": "00-traceid-spanid-01",
  "path": "/path/to/file",
  "lang": "java",
  "content": "source code content",
  "sessionId": "session-uuid",
  "modelCode": "model-code",
  "permissionCode": "permission-code",
  "data": { ... },
  "md5": null,
  "range": [{ "line": 1, "character": 0 }, { "line": 10, "character": 5 }]
}
```

**Agent -> Java (响应):**
```json
{
  "id": "same-uuid-as-request",
  "code": "status_code",
  "msg": "message",
  "type": "response_type",
  "data": { ... }
}
```

### 3.2 消息加密分析

**结论: WebSocket 消息不加密。**

- Java 端 `PluginWebsocketClient.Fd()` 方法直接将 `MessageDto` 序列化为 JSON 并通过 `webSocket.send(json)` 发送
- 没有消息级别的加密层
- 通信依赖传输层安全 (但见第5节 SSL 问题)

**关键代码 (PluginWebsocketClient.Fd):**
```java
private static Boolean Fd(Project project, MessageDto messageDto) {
    messageDto.initModelInfo();  // 填充 modelCode, permissionCode
    WebSocket webSocket = AGENT_WEBSOCKETS.get(project.getBasePath());
    AGENT_REQUEST.put(messageDto.getId(), messageDto);
    String json = new Gson().toJson(messageDto);  // 明文 JSON
    LogUtil.info("...", json);
    if (webSocket != null) {
        return webSocket.send(json);  // 明文发送
    }
    return false;
}
```

### 3.3 traceparent 传播

Java 端通过 OpenTelemetry W3C Trace Context 传播注入 `traceparent` 头:

```java
// PluginWebsocketClient.FF() - 构建 traceparent
Span span = ...;
span.setAttribute(SpanAttrEnum.USER_USERNAME.getText(), settings.userName);
span.setAttribute(SpanAttrEnum.PLUGIN_UPDATE.getText(), settings.isUpdater);
// ... 更多属性

Map<String, String> map = new HashMap<>();
GlobalOpenTelemetry.getPropagators().getTextMapPropagator()
    .inject(Context.current(), map, (m, k, v) -> m.put(k, v));
String traceparent = map.get("traceparent");
```

`traceparent` 作为消息字段传递, 不是加密机制, 而是分布式追踪传播。

---

## 4. HTTP 请求加密

### 4.1 OkHttp 客户端配置

**PluginWebsocketClient 构造函数:**
```java
client = new OkHttpClient().newBuilder()
    .readTimeout(60L, TimeUnit.SECONDS)
    .writeTimeout(60L, TimeUnit.SECONDS)
    .connectTimeout(60L, TimeUnit.SECONDS)
    .build();
```

**没有配置任何拦截器 (Interceptor)。** OkHttp 客户端仅用于 WebSocket 连接, 不用于 HTTP API 调用。所有 API 调用由 Agent Node.js 进程执行。

### 4.2 Token 附加机制

Java 端在两种场景下将 API Key 附加到 URL:

**场景1: WebView URL 加载 (CommonService.openUrl):**
```java
if (requestCaseCodeDto.isNeedToken() && url.endsWith(".html")) {
    url = url + PluginStartupActivity.getApiKey();  // 明文附加到 URL
}
```

**场景2: Code Knowledge URL (CommonService.WC):**
```java
if (codeKnowledgeWebUrl.endsWith("/D/")) {
    url = codeKnowledgeWebUrl + PluginStartupActivity.getApiKey();
} else if (!codeKnowledgeWebUrl.endsWith("/")) {
    url = codeKnowledgeWebUrl + "?token=" + PluginStartupActivity.getApiKey();
}
```

**安全风险:** Token 以明文附加到 URL, 可能被日志/浏览器历史记录泄露。

### 4.3 数据库密码传输

**SqlService.handleSqlTest:**
```java
ConnectConfigDto configDto = SqlService.sf(jsonObject);  // 从 WebView 解析
MessageDto messageDto = new MessageDto(configDto.getId(), CommandEnum.SQL_TEST_CONNECT.getType());
messageDto.setData(configDto);  // 包含明文 user, password
PluginWebsocketClient.sendWsMessage(messageDto, project);
```

**ConnectConfigDto 字段:**
- `id`: 连接标识
- `client`: 客户端类型
- `host`: 数据库主机
- `port`: 数据库端口
- `user`: 数据库用户名 (明文)
- `password`: 数据库密码 (明文)
- `database`: 数据库名

**安全风险:** 数据库凭据以明文通过 WebSocket 传输给 Agent 进程。

---

## 5. SSL/TLS 处理

### 5.1 证书验证禁用

**OpenTelemetryConfig.xE() - 创建全信任 TrustManager:**
```java
private static TrustManager[] xE() {
    TrustManager[] trustManagerArray = new TrustManager[1];
    trustManagerArray[0] = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
            // 空实现 - 接受所有客户端证书
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
            // 空实现 - 接受所有服务端证书
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];  // 返回空数组
        }
    };
    return trustManagerArray;
}
```

**OpenTelemetryConfig.IF() - 应用禁用验证的 SSL 上下文:**
```java
private static SSLContext IF(TrustManager[] trustManagerArray) {
    SSLContext sslContext = SSLContext.getInstance("TLS");  // 混淆后为 "0K9"
    sslContext.init(null, trustManagerArray, new SecureRandom());
    SSLContext.setDefault(sslContext);  // 设为全局默认!
    return sslContext;
}
```

**OpenTelemetryConfig.jE() - 构建 OTLP Exporter:**
```java
private static OtlpHttpSpanExporter jE(String endpoint) {
    TrustManager[] trustManagers = OpenTelemetryConfig.xE();  // 获取全信任 TrustManager

    OtlpHttpSpanExporter exporter = OtlpHttpSpanExporter.builder()
        .setEndpoint(endpoint)
        .setCompression("gzip")
        .setRetryPolicy(OpenTelemetryConfig.VD())
        .setProxy(proxyOptions)
        .setSslContext(
            OpenTelemetryConfig.IF(trustManagers),      // 禁用验证的 SSLContext
            (X509TrustManager)trustManagers[0]           // 全信任 TrustManager
        )
        .build();
    return exporter;
}
```

### 5.2 安全影响

| 影响 | 详情 |
|------|------|
| **中间人攻击** | APM OTLP 连接可被 MITM 攻击 |
| **全局影响** | `SSLContext.setDefault()` 影响所有后续 SSL 连接 |
| **数据泄露** | 遥测数据 (含用户名, 插件版本, IDE 版本) 可被截获 |
| **证书固定缺失** | 没有证书固定 (Certificate Pinning) 机制 |

### 5.3 WebSocket 连接安全

**WebSocket 连接使用 `ws://` (非 `wss://`):**
```java
String url = "ws://127.0.0.1:" + port + "/ws/idea";
```

由于连接目标是本地 Agent 进程 (127.0.0.1), 使用明文 WebSocket 是合理的。但如果 Agent 端口被外部访问, 则存在风险。

---

## 6. debugCode=9527 后门

### 6.1 后门位置

**后门在 Agent Node.js 端实现, 不在 Java 端。**

Agent `index.js` 中的 `getIsDevMode()` 函数:

```javascript
function getIsDevMode() {
    const g = getConfig("agent.debugCode");

    // 后门路径1: 硬编码值 9527 直接返回 true
    if (isDev || g === 9527) return true;

    // 未设置则返回 false
    if (!g) return false;

    // 后门路径2: 哈希验证
    const A = (userInfo().username || "unknown").toLowerCase();
    const S = getAgentVersion();
    const v = `${A}-${S}-002230`
        .split("")
        .reduce((d, E) => d + E.charCodeAt(0), 0) % 100;
    return v == g;  // 宽松比较 (== 而非 ===)
}
```

### 6.2 后门触发条件

| 条件 | 效果 |
|------|------|
| `isDev === true` | 开发模式, 直接启用 |
| `debugCode === 9527` | **硬编码后门值**, 直接启用开发者模式 |
| `debugCode == hash(username, version)` | 哈希匹配, 启用开发者模式 |
| `debugCode` 未设置 | 正常模式 |

### 6.3 哈希计算公式

```
hash = (charCodeSum(username.toLowerCase() + "-" + agentVersion + "-002230")) % 100
```

示例计算:
- `username = "testuser"`, `version = "3.4.2"`
- 输入字符串: `"testuser-3.4.2-002230"`
- 逐字符 charCode 求和: `116+101+115+116+117+115+101+114+45+51+46+52+46+50+45+48+48+50+50+51+48 = ...`
- 对 100 取模得到 `debugCode` 值

### 6.4 配置来源

```javascript
function getConfig(d, E) {
    const g = path.resolve(__dirname, "./config.json");   // 内置配置
    const T = getHomeDir(`/bin/config.json`);              // 用户配置
    const v = {};
    try { Object.assign(v, fs.readJsonSync(g)); } catch {}
    try {
        const d = fs.readJsonSync(T);
        d["agent.debugCode"] && Object.assign(v, d);  // 仅当 debugCode 存在时合并
        v["agent.tipinfo"] = d?.["agent.tipinfo"];
    } catch {}
    return d ? (v.hasOwnProperty(d) ? v[d] : E) : v;
}
```

**配置文件路径:** `~/.iflycode/bin/config.json`

**设置 debugCode 的方式:**
1. 编辑 `~/.iflycode/bin/config.json`, 添加 `"agent.debugCode": 9527`
2. 或计算特定用户名+版本的哈希值并设置

### 6.5 Java 端与 debugCode 的关系

**Java 端不检查 debugCode。** Java 端所有功能不区分开发者/生产模式。debugCode 仅影响 Agent Node.js 进程的行为, 包括:
- 日志详细程度
- 调试端口开放
- 特定功能的启用/禁用
- 错误处理策略

---

## 7. Java <-> Agent 加密数据流图

### 7.1 整体架构

```
+-------------------+     ws://127.0.0.1:PORT/ws/idea     +-------------------+
|   Java Plugin     | <===============================> |   Agent (Node.js)  |
|   (IDEA 进程)     |         明文 WebSocket JSON         |   (子进程)         |
+-------------------+                                     +-------------------+
        |                                                        |
        | 不执行加密                                              | 执行所有加密
        |                                                        |
        v                                                        v
+-------------------+                                     +-------------------+
| Project UserData  |                                     | 远程 API 服务      |
| API_KEY = token   |                                     | /api/usercenter/  |
| (明文存储)         |                                     | /api/starspark/   |
+-------------------+                                     +-------------------+
                                                                  ^
                                                                  | HTTPS
                                                                  |
                                                          +-------------------+
                                                          | API 请求加密:      |
                                                          | - RSA (登录凭据)   |
                                                          | - SM4 (权限缓存)   |
                                                          | - SM4 (代码收集)   |
                                                          | - AES (内部通信)   |
                                                          +-------------------+
```

### 7.2 各通道加密状态

| 通道 | 方向 | 加密 | 详情 |
|------|------|------|------|
| Java -> Agent (WebSocket) | 请求 | 无 | 明文 JSON |
| Agent -> Java (WebSocket) | 响应 | 无 | 明文 JSON |
| Agent -> API (登录) | 请求 | RSA | 用户名/密码 RSA 加密 |
| Agent -> API (代码收集) | 请求 | SM4 | 代码前缀/补全结果 SM4 加密 |
| Agent -> API (权限查询) | 缓存 | SM4 | 权限信息 SM4 加密后本地缓存 |
| Agent -> API (APM 遥测) | 请求 | TLS (禁用验证) | OTLP HTTP + 全信任 TrustManager |
| Java -> WebView (CEF) | 内部 | 无 | 本地资源加载 |
| WebView -> 外部 (URL) | 请求 | 无 | Token 附加到 URL 明文参数 |

### 7.3 Agent 端加密密钥 (硬编码)

从 Agent `index.js` webpack module 42135 提取:

```javascript
// RSA 公钥 (1024-bit)
RSA_PUB_KEY = "-----BEGIN PUBLIC KEY-----\r\n" +
    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCFMVCHyq4CNE0sHQj5O3o6SFxo" +
    "5yKK6/tpOC/zbpcomixQ17X7BBccZPyDcruIUkfNhlAeQHxFDn2NCOn2zdm3+6ke" +
    "s6KqHyjziBpHzjz9cQt..." +
    "-----END PUBLIC KEY-----";

// SM2 公钥 (Base64)
SM2_PUB_KEY = "BCJQBO/6DHOQnoaIQJulGWmcnQosY/Ga82SVtALV8wRJW5IDl9Pohau/8WH7QaID3LslQmrcGDdLKkh5dSg0XtA=";

// SM4 密钥 (Base64)
SM4_KEY = "GXjQSXlGw42RMR6av5Yzaw==";

// AES-256-CTR 密钥 (Base64)
AES_KEY = "YD3rEBXKcb4rc67whX13gR81LAc7YQjXLZgQowkU3/Q=";

// AES-256-CTR IV (Base64)
AES_IV = "c...";  // (截断, 需要完整提取)
```

---

## 8. Java 端与 Agent 端加密对比

### 8.1 职责分工

| 安全功能 | Java 端 | Agent 端 |
|----------|---------|----------|
| RSA 加密 | 不执行 | 登录凭据加密 |
| SM4 加密/解密 | 不执行 | 权限缓存, 代码收集 |
| SM2 加密 | 不执行 | (可用, 具体用途待确认) |
| AES 加密/解密 | 不执行 | 内部通信加密 |
| MD5 哈希 | 不执行 | 更新包校验, 消息完整性 |
| Token 存储 | 明文存入 UserData | 从 API 获取后传递给 Java |
| Token 附加 | 附加到 URL 明文参数 | 附加到 HTTP 请求头 |
| SSL 验证 | 禁用 (APM 连接) | 遵循 Node.js 默认 |
| 凭据传输 | 明文通过 WebSocket | RSA 加密后发送到 API |
| debugCode 检查 | 不检查 | 检查并启用开发者模式 |

### 8.2 安全风险汇总

| 风险 | 严重程度 | 位置 | 详情 |
|------|----------|------|------|
| SSL 证书验证禁用 | **高** | `OpenTelemetryConfig.xE()` | 全信任 TrustManager, 全局 SSLContext |
| Token 明文 URL 附加 | **中** | `CommonService.openUrl()` | Token 出现在 URL 中, 可被日志记录 |
| 数据库密码明文传输 | **中** | `SqlService.handleSqlTest()` | 通过 WebSocket 明文传输 DB 凭据 |
| API Key 明文存储 | **中** | `PluginStartupActivity.setApiKey()` | 存入 Project UserData, 无加密 |
| debugCode=9527 后门 | **中** | Agent `getIsDevMode()` | 硬编码后门值启用开发者模式 |
| WebSocket 明文通信 | **低** | `PluginWebsocketClient` | 仅本地通信, 风险较低 |
| 加密密钥硬编码 | **中** | Agent `index.js` | RSA/SM4/AES 密钥嵌入在 webpack bundle 中 |

### 8.3 Agent 端加密实现详情 (webpack module 1618)

```javascript
// 统一加密入口
function encrypt(data, mode, ...args) {
    switch (mode) {
        case "SM2": return encryptSM2(data, ...args);
        case "SM4": return encryptSM4(data, ...args);
        case "RSA": return encryptRSA(data, ...args);
        case "AES": return encryptAES(data, ...args);
        case "MD5": return cryptoMd5(data);
        default: return data;
    }
}

// 统一解密入口
function decrypt(data, mode, ...args) {
    switch (mode) {
        case "SM4": return decryptSM4(data, ...args);
        case "AES": return decryptAES(data, ...args);
        default: return data;
    }
}

// RSA 加密 - 分块 (64字节), PKCS1 填充
function encryptRSA(data, key = RSA_PUB_KEY) {
    const buf = Buffer.from(data, "utf8");
    const CHUNK = 64;
    const chunks = [];
    const results = [];
    for (let i = 0; i < buf.length; i += CHUNK) {
        chunks.push(buf.slice(i, i + CHUNK));
    }
    chunks.forEach(chunk => {
        results.push(crypto.publicEncrypt(
            { key, padding: crypto.constants.RSA_PKCS1_PADDING },
            chunk
        ).toString("base64"));
    });
    return results;  // 返回 Base64 编码的加密块数组
}

// SM4 加密 - PKCS#5 填充, Base64 输出
function encryptSM4(data, key = SM4_KEY) {
    const hexKey = Buffer.from(key, "base64").toString("hex");
    const encrypted = sm4.encrypt(data, hexKey, { padding: "pkcs#5" });
    return Buffer.from(encrypted, "hex").toString("base64");
}

// SM4 解密
function decryptSM4(data, key = SM4_KEY) {
    const hexKey = Buffer.from(key, "base64").toString("hex");
    const hexData = Buffer.from(data, "base64").toString("hex");
    return sm4.decrypt(hexData, hexKey);
}

// SM2 加密 - C1C3C2 模式, Base64 输出
function encryptSM2(data, key = SM2_PUB_KEY) {
    const hexKey = Buffer.from(key, "base64").toString("hex");
    const encrypted = sm2.doEncrypt(data, hexKey, 1);  // mode 1 = C1C3C2
    return Buffer.from("04" + encrypted, "hex").toString("base64");
}

// AES-256-CTR 加密
function encryptAES(data, key = AES_KEY, iv = AES_IV) {
    const cipher = crypto.createCipheriv(
        "aes-256-ctr",
        Buffer.from(key, "base64"),
        Buffer.from(iv, "base64")
    );
    let result = cipher.update(data, "utf8", "base64");
    result += cipher.final("base64");
    return result;
}

// AES-256-CTR 解密
function decryptAES(data, key = AES_KEY, iv = AES_IV) {
    const decipher = crypto.createDecipheriv(
        "aes-256-ctr",
        Buffer.from(key, "base64"),
        Buffer.from(iv, "base64")
    );
    let result = decipher.update(data, "base64", "utf8");
    result += decipher.final("utf8");
    return result;
}

// MD5 哈希
function cryptoMd5(data) {
    return crypto.createHash("md5").update(data).digest("hex");
}
```

### 8.4 加密使用场景

| 场景 | 加密方式 | 数据 | 代码位置 |
|------|----------|------|----------|
| 账密登录 | RSA | 用户名 + 密码 | `LoginService.loginByAccount()` |
| 权限缓存 | SM4 | `JSON.stringify(permissions)` | `permission()` 方法 |
| 代码收集上报 | SM4 | prefixCode + completeCode | `codeMonitorReport()` |
| 更新包校验 | MD5 | 更新文件内容 | `LoginInfo.md5` 字段 |
| 消息完整性 | MD5 | 代码上下文 | `MessageDto.md5` 字段 |

---

## 9. MD5 校验链

### 9.1 更新包 MD5 校验

**LoginInfo DTO:**
```java
public class LoginInfo {
    private String current;   // 当前版本
    private String update;    // 更新版本
    private String name;      // 更新名称
    private String file;      // 更新文件路径
    private String dir;       // 更新目录
    private String md5;       // MD5 校验和
}
```

**PluginUpdater.checkUpdate:**
```java
LoginInfo loginInfo = new Gson().fromJson(jsonObject.get("loginInfo"), LoginInfo.class);
String current = loginInfo.getCurrent();
String update = loginInfo.getUpdate();
String file = loginInfo.getFile();
String md5 = loginInfo.getMd5();    // 接收 MD5
String name = loginInfo.getName();
// 注意: Java 端接收 MD5 但未在代码中验证
// MD5 验证可能在 Agent 端或下载后验证
```

### 9.2 消息 MD5 字段

**MessageDto.md5:**
```java
public class MessageDto {
    private String md5;  // 用于代码上下文完整性校验
    // ...
}
```

**CommonService.handleComment:**
```java
messageDto2.setMd5(
    null != requestCaseCodeDto.getContext()
        ? requestCaseCodeDto.getContext().getMd5()
        : null
);
```

MD5 用于代码上下文的完整性校验, 防止传输过程中数据损坏。

---

## 10. 代理 (Proxy) 配置

### 10.1 APM 代理配置

**OpenTelemetryConfig.jE() 中的代理选择器:**
```java
ProxyOptions.create(new ProxySelector() {
    @Override
    public List<Proxy> select(URI uri) {
        ArrayList<Proxy> list = new ArrayList<>();
        // 通过反射获取 IDE 代理设置
        Class<?> clazz = Class.forName("com.intellij.util.net.HttpConfigurable");
        Object configurable = clazz.getDeclaredMethod("getInstance").invoke(null);
        Field useProxy = clazz.getDeclaredField("USE_PROXY");
        useProxy.setAccessible(true);

        if ((Boolean)useProxy.get(configurable) && "https".equalsIgnoreCase(uri.getScheme())) {
            Field host = clazz.getDeclaredField("PROXY_HOST");
            host.setAccessible(true);
            String proxyHost = (String)host.get(configurable);
            Field port = clazz.getDeclaredField("PROXY_PORT");
            port.setAccessible(true);
            int proxyPort = (Integer)port.get(configurable);
            list.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)));
        }
        list.add(Proxy.NO_PROXY);
        return list;
    }
});
```

代理配置通过反射读取 IntelliJ IDEA 的 HTTP 代理设置, 并应用到 OTLP Exporter。

---

## 11. 总结

### 11.1 核心发现

1. **Java 端零加密**: Java 插件端不执行任何加密/解密操作, 所有加密由 Agent Node.js 进程完成
2. **明文 Token 存储**: API Token 以明文存储在 Project UserData 和 XML 配置文件中
3. **明文 WebSocket 通信**: Java 与 Agent 之间的所有通信均为明文 JSON
4. **SSL 验证禁用**: APM 连接使用全信任 TrustManager, 且设为全局默认 SSLContext
5. **数据库密码明文传输**: DB 连接凭据通过 WebSocket 明文传递
6. **Token URL 泄露**: API Key 以明文附加到 URL 参数中
7. **debugCode 后门**: Agent 端硬编码 9527 作为开发者模式后门
8. **密钥硬编码**: RSA/SM4/SM2/AES 密钥均硬编码在 Agent webpack bundle 中

### 11.2 架构安全模型

```
[用户浏览器] ---(HTTPS)---> [iFlyCode API 服务]
                                ^
                                | (RSA 加密凭据, SM4 加密数据)
                                |
[IDEA Plugin] ---(ws://明文)---> [Agent Node.js] ---(HTTPS)---> [API 服务]
     |                              |
     | 存储 Token (明文)             | 执行所有加密
     | 禁用 SSL 验证                 | 硬编码密钥
     | 附加 Token 到 URL             | debugCode 后门
     v                              v
  本地存储                        本地缓存 (SM4 加密)
```

Java 插件作为"瘦客户端", 将所有安全敏感操作委托给 Agent 进程, 但自身在 Token 管理和 SSL 验证方面存在多个安全缺陷。
