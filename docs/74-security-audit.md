# iFlyCode 安全审计报告 (OWASP Top 10)

> 版本: 3.4.2-222 | 审计日期: 2026-05-13 | 方法: 静态分析 (反编译 .class + Agent bundle 逆向)

## 审计范围

- IntelliJ 插件端: 574 个 .class 文件 (com/aicode/*)
- Agent 端: index.js + worker.js (webpack bundle)
- WebView 前端: 84 个 JS 文件 (~10.5 MB)
- 配置文件: config.json, package.json, plugin.xml

## 严重程度评级标准

| 等级 | 定义 |
|------|------|
| **高** | 可直接被利用，无需特殊条件 |
| **中** | 需要特定条件才能利用 |
| **低** | 信息泄露，无法直接利用 |

---

## A01:2021 - 权限控制失效 (Broken Access Control)

### 发现 1.1: 权限列表由服务端下发，客户端无独立校验

| 项目 | 内容 |
|------|------|
| **风险描述** | PermissionEnum 定义了 23 种权限 (code_optimization, comments, unit_testing, doc_comments, line_comments, function_split, inline_chat, talk_intelligent, chat_module, code_debug, review, generate_commit, batch_unittest, code_knowledge_base, sql_generation, sql_optimization, demand_test, generate_test_case, chat_sql_generation, chat_sql_optimization, demand_analysis, demand_split, failure_analysis)，但权限列表完全由服务端通过 `user_permission_list` 消息下发，客户端仅存储在 `AICodeSettingsState.permissions` (LinkedHashSet) 中，UI 展示和功能开关依赖此集合，无客户端独立校验 |
| **影响范围** | 所有受权限控制的功能 (23 种) |
| **严重程度** | **中** |
| **证据来源** | `PermissionEnum.java` (23 个枚举值), `AICodeSettingsState.java` (permissions 字段), `WebViewWindowPanel.java` (handleRequest 路由无权限检查) |
| **修复建议** | 1. 在每个功能入口添加服务端权限校验 (非仅客户端); 2. 关键操作 (SQL 执行、代码执行) 需双重验证; 3. 权限变更需重新认证 |

### 发现 1.2: WebView JS Bridge 无权限隔离

| 项目 | 内容 |
|------|------|
| **风险描述** | WebViewWindowPanel.handleRequest() 接收 JS Bridge 消息后，根据 WebViewDataTypeEnum.module 直接路由到对应 Service，无权限检查。任何能在 WebView 中执行 JS 的攻击者可调用所有功能 (Chat, SQL, Git, CodeSearch, CodeCheck, UnitTest, Login, Setting 等) |
| **影响范围** | 所有通过 JS Bridge 暴露的功能 |
| **严重程度** | **高** |
| **证据来源** | `WebViewWindowPanel.java` 第 239-304 行: handleRequest() 方法直接 switch(module) 路由，无权限校验 |
| **修复建议** | 1. 在 handleRequest 入口添加权限校验; 2. 限制 JS Bridge 可调用的功能白名单; 3. 敏感操作 (SQL, Login) 需额外确认 |

### 发现 1.3: 数据库连接配置 (含密码) 通过 WebSocket 明文传输

| 项目 | 内容 |
|------|------|
| **风险描述** | ConnectConfigDto 包含 host, port, user, password, database 字段。SqlService.handleSqlTest() 和 handleSqlSave() 将整个 ConnectConfigDto (含明文密码) 通过 WebSocket 发送给 Agent。WebSocket 连接为 ws://127.0.0.1 (无 TLS)，本机其他进程可嗅探 |
| **影响范围** | SQL Chat 功能的所有数据库连接 |
| **严重程度** | **中** |
| **证据来源** | `ConnectConfigDto.java` (password 字段明文), `SqlService.java` (sf() 方法解析 password, handleSqlTest/handleSqlSave 直接发送) |
| **修复建议** | 1. 密码在传输前加密; 2. 使用 wss:// 替代 ws://; 3. Agent 端安全存储凭据，不回传 |

---

## A02:2021 - 加密机制失效 (Cryptographic Failures)

### 发现 2.1: RSA 1024-bit 公钥硬编码 (可被分解)

| 项目 | 内容 |
|------|------|
| **风险描述** | Agent bundle 中硬编码了 RSA 1024-bit 公钥: `MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCFMVCHyq4CNE0sHQj5O3o6SFxo5yKK6/tpOC/zbpcomixQ17X7BBccZPyDcruIUkfNhlAeQHxFDn2NCOn2zdm3+6kes6KqHyjziBpHzjz9cQtvvEb8oT6ZvB2Ffsqr3JygMwDyPDHt0BmMo5CsuCvQvpmu7o9Qf5mkSx2UFIxlGQIDAQAB`。1024-bit RSA 已被证明可在合理时间内分解 (2010 年 768-bit 已被分解，1024-bit 在 CADO-NFS 等工具下约需数月)。该密钥用于账号密码登录加密 |
| **影响范围** | 用户登录密码加密 |
| **严重程度** | **高** |
| **证据来源** | `agent/bin/index.js` 中 RSA 公钥 (MIGfMA0 开头, 1024-bit), `03-server-endpoints.md` 第 106 行: "账号密码登录使用 RSA 加密 (1024-bit, 分块 64 bytes)" |
| **修复建议** | 1. 升级到 RSA-2048 或 RSA-4096; 2. 密钥从服务端动态获取而非硬编码; 3. 考虑使用 SRP 或 PAKE 协议替代 RSA 加密传输 |

### 发现 2.2: SM2/SM4 国密算法密钥硬编码

| 项目 | 内容 |
|------|------|
| **风险描述** | Agent bundle 包含 sm-crypto 库 (SM2/SM4)，代码中发现 `sm2.doEncrypt(d,g,1)`, `sm4.encrypt(d,g,{padding:"pkcs#5"})`, `sm4.decrypt(T,g)` 调用。SM2 加密使用固定公钥 (04 前缀的 128 字节 hex)，SM4 使用固定密钥。密钥硬编码在 webpack bundle 中，无法轮换 |
| **影响范围** | 权限缓存加密 (SM4)、数据加密传输 (SM2) |
| **严重程度** | **高** |
| **证据来源** | `agent/bin/index.js`: sm2.doEncrypt, sm4.encrypt/decrypt 调用; `package.json`: "sm-crypto": "^0.3.13" 依赖 |
| **修复建议** | 1. 密钥从服务端动态获取; 2. 实现密钥轮换机制; 3. 使用密钥派生函数 (HKDF) 替代固定密钥 |

### 发现 2.3: AES-256-CTR 模式使用可能不当

| 项目 | 内容 |
|------|------|
| **风险描述** | Agent 使用 AES-256-CTR 加密模式。CTR 模式要求 nonce/IV 绝不重复，否则相同明文块会产生相同密文，泄露信息。若 IV 硬编码或使用可预测计数器，将导致流密码重用攻击 |
| **影响范围** | Agent 端数据加密 |
| **严重程度** | **中** |
| **证据来源** | 已知 AES-256-CTR 为五种加密算法之一 (SM2/SM4/AES-256-CTR/MD5/RSA) |
| **修复建议** | 1. 确认 IV 使用密码学安全随机数生成; 2. 考虑使用 AES-GCM 替代 CTR (提供认证加密); 3. 每次 encryption 使用唯一 IV |

### 发现 2.4: MD5 用于文件完整性校验

| 项目 | 内容 |
|------|------|
| **风险描述** | MessageDto 中包含 `md5` 字段用于文件完整性校验，Agent bundle 中发现 `crypto.createHash('md5')` 调用。MD5 已知存在碰撞攻击，不应用于安全目的 |
| **影响范围** | 代码补全提示的完整性校验 |
| **严重程度** | **低** |
| **证据来源** | `04-websocket-protocol.md` (md5 字段), `worker.js` 第 28055 行: `crypto.createHash('md5')` |
| **修复建议** | 替换为 SHA-256 或 SHA-3 |

### 发现 2.5: SSL 证书验证完全禁用

| 项目 | 内容 |
|------|------|
| **风险描述** | OpenTelemetryConfig.xE() 创建了一个空实现的 X509TrustManager: checkClientTrusted() 和 checkServerTrusted() 均为空方法体，getAcceptedIssuers() 返回空数组。该 TrustManager 用于 OtlpHttpSpanExporter 的 SSL Context，意味着 APM 遥测连接不验证服务端证书，允许中间人攻击 |
| **影响范围** | OpenTelemetry APM 遥测通道 |
| **严重程度** | **高** |
| **证据来源** | `OpenTelemetryConfig.java` 第 140-165 行: xE() 方法返回 no-op TrustManager; `18-telemetry.md` 第 33 行: "使用自定义 SSL Context，信任所有证书" |
| **修复建议** | 1. 使用系统默认 TrustManager; 2. 如需自定义 CA，仅添加特定根证书; 3. 启用证书主机名验证 |

---

## A03:2021 - 注入攻击 (Injection)

### 发现 3.1: 命令注入风险 - chmod 和 kill 命令拼接

| 项目 | 内容 |
|------|------|
| **风险描述** | PluginAgentCommandLine.findAgentBinary() 中使用字符串拼接构造 chmod 命令: `"chmod a+x " + path3`，然后通过 ProcessBuilder 执行。getKillCommandLine() 中使用 `"kill -9 " + pid` 和 `"taskkill /F /PID " + pid`。虽然 path3 来自插件安装目录 (非用户输入)，pid 来自系统进程列表，但如果路径包含特殊字符或进程信息被篡改，可能导致命令注入 |
| **影响范围** | Agent 进程管理 (启动、权限设置、终止) |
| **严重程度** | **中** |
| **证据来源** | `PluginAgentCommandLine.java` 第 62 行: `"taskkill /F /PID " + n`; 第 66 行: `"kill -9 " + n`; 第 319/334 行: `"chmod a+x " + path3` |
| **修复建议** | 1. 使用 ProcessBuilder 参数列表而非字符串拼接; 2. 对 pid 进行整数校验; 3. 对路径进行白名单校验 |

### 发现 3.2: SQL 注入风险 - knex 动态查询

| 项目 | 内容 |
|------|------|
| **风险描述** | Agent 使用 knex.js 2.5.1 作为 SQL 查询构建器，支持 mysql2, pg, dmdb, sqlite3 四种数据库。虽然 knex 默认使用参数化查询，但如果使用 .raw() 或 .whereRaw() 且拼接用户输入，则存在 SQL 注入风险。SQL Chat 功能允许用户输入自然语言生成 SQL，生成的 SQL 直接执行 |
| **影响范围** | SQL Chat 功能 (SQL 生成、SQL 优化、表查询) |
| **严重程度** | **中** |
| **证据来源** | `package.json`: "knex": "2.5.1", "mysql2": "3.2.0", "pg": "^8.11.3", "sqlite3": "^5.1.7", "dmdb": "^1.0.24984"; `23-agent-internals.md`: knex 数据库连接 |
| **修复建议** | 1. 审计所有 .raw()/.whereRaw() 调用确保参数化; 2. AI 生成的 SQL 在执行前进行安全审查; 3. 使用最小权限数据库账号; 4. 实现查询白名单或只读模式 |

### 发现 3.3: XSS 风险 - WebView JS Bridge 无输入净化

| 项目 | 内容 |
|------|------|
| **风险描述** | WebViewWindowPanel.sendMessage2webView() 使用 `executeJavaScript("receiveData(" + json + ");")` 将数据注入 WebView。如果 json 包含恶意 JavaScript (如 `</script><script>alert(1)</script>`)，可能突破 JSON 上下文执行任意 JS。ideaUtil.js 中 `window.myObject.sendMessage()` 将用户操作序列化为 JSON 发送给 Java 端，eclipseUtil.js 中 `window.sendMessage()` 同理。receiveData() 直接 JSON.parse 后调用 handlerReceivedMsg()，无输入验证 |
| **影响范围** | WebView 所有数据展示 (聊天、代码补全、SQL 结果) |
| **严重程度** | **中** |
| **证据来源** | `WebViewWindowPanel.java` 第 368 行: `executeJavaScript("receiveData(" + json + ");")`; `ideaUtil-11ab0730.js` 第 3-8 行; `eclipseUtil-82d0751a.js` 第 3-9 行 |
| **修复建议** | 1. 对注入 WebView 的 JSON 进行转义 (特别是 `</script>` 和 `]]>`); 2. 使用 CEF 的 PostMessage API 替代 executeJavaScript; 3. 在 receiveData 中添加 JSON Schema 验证; 4. 启用 WebView CSP |

### 发现 3.4: 自定义 ClassLoader 字节码变换 - 代码注入风险

| 项目 | 内容 |
|------|------|
| **风险描述** | iFlyCode 使用自定义 ClassLoader 在运行时变换含故意无效操作码 (fconst_2, fconst_1 在整数上下文; iinc 511/255 超出合法范围) 的 .class 文件字节码。该 ClassLoader 将无效字节码替换为正确的 XOR 解码逻辑。如果攻击者能替换 .class 文件或注入恶意字节码，ClassLoader 会无验证地加载和执行 |
| **影响范围** | 所有使用 H() 混淆的 312 个类 |
| **严重程度** | **中** |
| **证据来源** | `67-H-deobfuscation-solution.md` 第 29-34 行: "bytecode contains deliberately invalid opcodes... transformed by a custom ClassLoader"; `64-h-deobfuscation-analysis.md` 第 153 行: ByteBuddy transform |
| **修复建议** | 1. 对变换后的字节码进行签名验证; 2. 使用 IntelliJ 内置的 ClassLoader 而非自定义; 3. 对 .class 文件进行完整性校验 (SHA-256) |

---

## A04:2021 - 不安全设计 (Insecure Design)

### 发现 4.1: WebSocket 仅限 localhost 但无认证

| 项目 | 内容 |
|------|------|
| **风险描述** | Plugin 与 Agent 之间通过 `ws://127.0.0.1:{动态端口}/ws/idea` 通信。虽然绑定 localhost 限制了远程访问，但本机任何进程均可连接该 WebSocket 端口并发送伪造消息 (如 USER_LOGIN, SQL_SOURCE_EDIT 等)。WebSocket 连接无认证机制 (无 token、无 origin 检查) |
| **影响范围** | 所有 Plugin-Agent 通信 |
| **严重程度** | **高** |
| **证据来源** | `PluginWebsocketClient.java` 第 311 行: `"ws://127.0.0.1:" + port + "/ws/idea"`; `04-websocket-protocol.md` |
| **修复建议** | 1. WebSocket 握手时验证 token; 2. 使用随机生成的共享密钥 (仅 Plugin 和 Agent 知晓); 3. 验证 Origin header; 4. 使用 Unix Domain Socket 替代 TCP |

### 发现 4.2: 512MB 消息大小限制

| 项目 | 内容 |
|------|------|
| **风险描述** | WebSocket 消息大小限制为 512MB，远超正常需求。攻击者可发送超大消息导致内存耗尽 (OOM)，特别是在 IDE 进程中 |
| **影响范围** | Plugin 和 Agent 进程 |
| **严重程度** | **中** |
| **证据来源** | 已知配置: 512MB 消息大小限制 |
| **修复建议** | 1. 降低消息大小限制至合理值 (如 10MB); 2. 实现流式传输替代大消息; 3. 添加内存使用监控 |

---

## A05:2021 - 安全配置错误 (Security Misconfiguration)

### 发现 5.1: 开发环境内部 IP 泄露

| 项目 | 内容 |
|------|------|
| **风险描述** | Agent bundle 的 package.json 中包含多个开发环境内部 IP 地址，暴露了内网拓扑结构。这些 IP 在生产环境中不应存在 |
| **影响范围** | 内网安全态势 |
| **严重程度** | **低** |
| **证据来源** | `package.json` 第 14-22 行: 172.29.63.138, 172.30.14.79, 172.29.228.232, 172.29.228.181, 172.29.231.97; `index.js` 第 105 行: isIflyTekVersion() 中硬编码 IP 列表; WebView `sendMsgMode-8b767ec0.js`: 172.31.186.40, 172.30.13.11, 172.30.13.41, 172.31.64.29 |
| **修复建议** | 1. 构建时剥离开发配置; 2. 使用环境变量而非硬编码 IP; 3. 使用 .env 文件 (不打包进发布版) |

### 发现 5.2: config.json 明文配置

| 项目 | 内容 |
|------|------|
| **风险描述** | Agent 使用 `~/.iflycode/bin/config.json` 存储配置，包含 `agent.debugCode` 等敏感字段。debugCode=9527 可直接启用开发模式，绕过正常安全检查。配置文件无加密、无权限限制 |
| **影响范围** | Agent 运行时行为控制 |
| **严重程度** | **中** |
| **证据来源** | `agent/bin/config.json`: `{"agent.version": "3.4.2", "agent.wasmCheck": 10, "agent.url": "https://iflycode-xfsaas.xfyun.cn", "agent.update": true}`; `index.js` 第 41 行: `if(env_1.default.isDev || devCode === 9527) return true` |
| **修复建议** | 1. 移除 debugCode 后门; 2. 配置文件设置 600 权限; 3. 敏感配置加密存储 |

### 发现 5.3: 开发模式后门 (debugCode=9527)

| 项目 | 内容 |
|------|------|
| **风险描述** | Agent 的 getIsDevMode() 函数中，如果 `debugCode === 9527` 直接返回 true，启用开发模式。此外还有基于用户名的 hash 计算后门: `hashCode = (userName-version-002230).split('').reduce(sum, char.charCodeAt(0), 0) % 100`，如果 hashCode == debugCode 也启用开发模式。任何知道此逻辑的人可通过修改 config.json 启用开发模式 |
| **影响范围** | Agent 所有行为 (开发模式可能禁用更新检查、启用调试端口等) |
| **严重程度** | **高** |
| **证据来源** | `index.js` 第 41-48 行: `if(env_1.default.isDev || g===9527) return true; ... const v=\`${A}-${S}-002230\`.split("").reduce(((d,E)=>d+E.charCodeAt(0)),0)%100; return v==g` |
| **修复建议** | 1. 移除硬编码后门值 9527; 2. 移除基于用户名的 hash 后门; 3. 开发模式通过环境变量控制 (不持久化到配置文件) |

### 发现 5.4: Cody 源码路径泄露

| 项目 | 内容 |
|------|------|
| **风险描述** | WebView 主 bundle 中包含 `E:/github/cody/lib/shared/src/utils.ts` 路径字符串，说明开发环境参考了 Sourcegraph Cody 的代码。这可能涉及许可证合规问题 (Cody 为 AGPL-3.0) |
| **影响范围** | 开源合规 |
| **严重程度** | **低** |
| **证据来源** | `webview/assets/index-f0296668.js`: `E:/github/cody/lib/shared/src/utils.ts` |
| **修复建议** | 1. 审查 Cody 代码的使用是否符合其许可证; 2. 构建时剥离源码路径; 3. 使用 source-map 的 excludePath 配置 |

### 发现 5.5: Jenkins 构建路径泄露

| 项目 | 内容 |
|------|------|
| **风险描述** | Agent bundle 中包含 Jenkins 构建工作空间路径: `/home/jenkins/workspace/AGENTIDEA_ad622b01-5978-4025-9306-99e9f5da304c`，暴露了 CI/CD 基础设施信息 |
| **影响范围** | CI/CD 安全态势 |
| **严重程度** | **低** |
| **证据来源** | `agent/bin/index.js`: `/home/jenkins/workspace/AGENTIDEA_ad622b01-5978-4025-9306-99e9f5da304c` |
| **修复建议** | 构建时使用 `--build-arg` 或环境变量替代硬编码路径 |

---

## A07:2021 - 身份认证失败 (Identification and Authentication Failures)

### 发现 7.1: Token 存储无加密

| 项目 | 内容 |
|------|------|
| **风险描述** | 用户登录后，token 通过 `PluginStartupActivity.setApiKey()` 存储在 IntelliJ Project 的 UserData 中 (内存)，同时 Agent 端持久化到 nedb 数据库 (`~/.iflycode/`)。nedb 为明文 JSON 文件存储，token 可被同机其他用户读取 |
| **影响范围** | 用户认证凭据 |
| **严重程度** | **中** |
| **证据来源** | `PluginStartupActivity.java` 第 131-148 行: setApiKey() 存储到 Project UserData; `03-server-endpoints.md` 第 103 行: "Token 持久化到本地 nedb 数据库" |
| **修复建议** | 1. Token 加密后存储 (使用 OS keychain); 2. nedb 文件设置 600 权限; 3. Token 设置合理过期时间; 4. 支持 token 撤销 |

### 发现 7.2: Token 刷新机制依赖 WebSocket 重连

| 项目 | 内容 |
|------|------|
| **风险描述** | 当 WebSocket 连接失败或收到 401 错误时，SocketMessageHandleListener 自动触发 `USER_LOGIN` 重新登录。登录请求仅包含 `{count: 1}`，无 challenge-response 机制。如果 WebSocket 被劫持，攻击者可伪造 401 响应触发重登录，获取新 token |
| **影响范围** | 会话管理 |
| **严重程度** | **中** |
| **证据来源** | `SocketMessageHandleListener.java` 第 530-534 行: 收到特定错误码时自动 `sendWsMessage(USER_LOGIN)` 并 `clear()` 设置; `PluginWebsocketClient.java` 第 340-344 行: USER_LOGIN 附加 `{count: 1}` |
| **修复建议** | 1. 实现 OAuth2 refresh_token 机制; 2. 重登录需用户确认; 3. 限制自动重登次数; 4. 添加 rate limiting |

### 发现 7.3: 登录 URL 构造使用 clientId 而非 token

| 项目 | 内容 |
|------|------|
| **风险描述** | 登录 URL 构造为 `{loginUrl}?token=xxx&pluginVersion=3.4.2&ideType=IDEA&type=outer`，token 参数直接暴露在 URL 中。URL 可能被记录到浏览器历史、Referer header、代理日志中 |
| **影响范围** | 用户登录流程 |
| **严重程度** | **中** |
| **证据来源** | `08-auth-flow.md` 第 72 行: `"url": "https://iflycode.xfyun.cn/login?token=xxx&pluginVersion=3.4.2&ideType=IDEA&type=outer"` |
| **修复建议** | 1. 使用 POST 请求传递 token; 2. 使用短时效的一次性 token; 3. 登录完成后从 URL 中移除 token |

---

## A08:2021 - 软件和数据完整性失败 (Software and Data Integrity Failures)

### 发现 8.1: Agent 二进制文件无完整性校验

| 项目 | 内容 |
|------|------|
| **风险描述** | Agent 二进制文件 (x86_64_linux_node, x86_64_darwin_node 等) 从本地路径加载执行，无签名验证或 hash 校验。如果文件被替换为恶意版本，Plugin 会无验证地启动。LoginInfo 中包含 md5 字段但仅用于 Agent 更新包校验，非运行时完整性验证 |
| **影响范围** | Agent 进程执行 |
| **严重程度** | **高** |
| **证据来源** | `PluginAgentCommandLine.java` 第 104-146 行: createAgentBinaryCommandline() 无校验; `LoginInfo.java` (md5 字段仅用于更新) |
| **修复建议** | 1. 对 Agent 二进制进行代码签名; 2. 启动前验证签名或 SHA-256 hash; 3. 使用 HTTPS 下载更新包 |

### 发现 8.2: 自动更新无签名验证

| 项目 | 内容 |
|------|------|
| **风险描述** | PluginUpdater.checkUpdate() 检查并下载更新，但更新包的完整性仅依赖 MD5 校验 (LoginInfo.md5)。MD5 已知可碰撞，攻击者可构造恶意更新包通过 MD5 校验 |
| **影响范围** | 插件和 Agent 更新 |
| **严重程度** | **高** |
| **证据来源** | `PluginUpdater.java`, `LoginInfo.java` (md5 字段) |
| **修复建议** | 1. 使用 SHA-256 或 SHA-512 校验; 2. 对更新包进行代码签名; 3. 下载使用 HTTPS 并验证证书 |

---

## A09:2021 - 安全日志和监控不足 (Security Logging and Monitoring Failures)

### 发现 9.1: APM 采样率 100% 但 SSL 禁用

| 项目 | 内容 |
|------|------|
| **风险描述** | OpenTelemetry APM 配置采样率 100% (traceIdRatioBased(1.0))，所有请求均上报。但上报通道使用信任所有证书的 SSL Context (发现 2.5)，遥测数据 (含用户名、IDE 版本、操作详情) 可被中间人截获 |
| **影响范围** | 所有用户操作的遥测数据 |
| **严重程度** | **高** |
| **证据来源** | `OpenTelemetryConfig.java` 第 82 行: `Sampler.traceIdRatioBased(1.0)`; 第 140-165 行: no-op TrustManager |
| **修复建议** | 1. 修复 SSL 证书验证 (见发现 2.5); 2. 降低采样率至合理值 (如 10%); 3. 敏感属性 (用户名) 脱敏后上报 |

### 发现 9.2: 遥测数据包含敏感信息

| 项目 | 内容 |
|------|------|
| **风险描述** | OpenTelemetry Span 属性包含: system.username (系统用户名), user.username (iFlyCode 用户名), idea.version, plugin.version, 以及所有用户设置 (trigger_on_pause, trigger_time_delay, code_mode, message_type, java_test, java_mock)。这些数据上报到云端，可能违反隐私法规 |
| **影响范围** | 用户隐私 |
| **严重程度** | **中** |
| **证据来源** | `PluginWebsocketClient.java` 第 109-116 行: 设置 Span 属性 (userName, isUpdater, autoTrigger, triggerTime, tipType, sendKey, testFramework, mockFramework); `18-telemetry.md` |
| **修复建议** | 1. 对用户名进行 hash 脱敏; 2. 仅上报必要属性; 3. 提供用户数据删除机制 (GDPR); 4. 在隐私政策中披露遥测范围 |

### 发现 9.3: 日志记录可能泄露敏感数据

| 项目 | 内容 |
|------|------|
| **风险描述** | PluginWebsocketClient 中 `LogUtil.info()` 记录完整的 WebSocket 消息 JSON，可能包含代码内容、SQL 查询、token 等敏感信息。Agent 端日志存储在 `~/.iflycode/logs/` 目录，无加密 |
| **影响范围** | 用户代码和认证凭据 |
| **严重程度** | **中** |
| **证据来源** | `PluginWebsocketClient.java` 第 279 行: `LogUtil.info(...)` 记录消息 JSON; `23-agent-internals.md`: 日志路径 `~/.iflycode/logs/` |
| **修复建议** | 1. 日志中脱敏 token、password 等字段; 2. 代码内容仅记录 hash; 3. 日志文件设置 600 权限; 4. 实现日志自动清理 |

---

## A06:2021 - 易受攻击和过时的组件 (Vulnerable and Outdated Components)

### 发现 6.1: Node.js 引擎版本限制 <=12

| 项目 | 内容 |
|------|------|
| **风险描述** | package.json 中 `engines.node: "<=12"`，Node.js 12 已于 2022-04-30 EOL，不再接收安全更新。Agent 使用此版本运行，存在已知 CVE |
| **影响范围** | Agent 进程 |
| **严重程度** | **高** |
| **证据来源** | `package.json` 第 108 行: `"engines": {"node": "<=12"}` |
| **修复建议** | 升级到 Node.js 18 LTS 或 20 LTS |

### 发现 6.2: 依赖版本含已知漏洞

| 项目 | 内容 |
|------|------|
| **风险描述** | 多个依赖版本过旧: knex 2.5.1 (当前 3.x), marked 1.2.9 (当前 12.x, 1.x 有 XSS CVE-2022-4931 等), node-fetch 2.x (当前 3.x), lodash 4.16.6 (当前 4.17.21, 多个 CVE 已修复) |
| **影响范围** | Agent 进程 |
| **严重程度** | **中** |
| **证据来源** | `package.json` dependencies |
| **修复建议** | 1. 升级 lodash 至 4.17.21+; 2. 升级 marked 至 12.x+; 3. 定期执行 `npm audit`; 4. 使用 Dependabot 或 Snyk 自动监控 |

---

## 敏感数据泄露完整清单

### 硬编码密钥

| # | 类型 | 位置 | 值/描述 | 严重程度 |
|---|------|------|---------|----------|
| 1 | RSA-1024 公钥 | agent/bin/index.js | `MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCFMVCHyq4CNE0s...xlGQIDAQAB` | 高 |
| 2 | RSA-2048 公钥 | agent/bin/index.js | `MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuD8nrZ8V...` (多处) | 中 |
| 3 | SM2 公钥 | agent/bin/index.js | 04 前缀 128 字节 hex (sm2.doEncrypt 参数) | 高 |
| 4 | SM4 密钥 | agent/bin/index.js | sm4.encrypt/decrypt 第二参数 | 高 |
| 5 | AES-256 密钥 | agent/bin/index.js | AES-256-CTR 密钥 | 高 |
| 6 | H() XOR 密钥 | 33 个 .class 文件 | 33 组 v[] 序列 (周期 106) | 低 |

### 泄露的内部 IP 地址

| # | IP 地址 | 位置 | 用途 |
|---|---------|------|------|
| 1 | 172.29.63.138 | package.json, index.js | 开发后端服务器 |
| 2 | 172.30.14.79 | package.json, index.js | 备用开发服务器 |
| 3 | 172.29.228.232 | package.json | 开发服务器 |
| 4 | 172.29.228.181:8080 | package.json | SaaS 测试服务器 |
| 5 | 172.29.231.97:4318 | package.json | APM 追踪收集器 |
| 6 | 172.31.186.40 | webview sendMsgMode-8b767ec0.js | 数据库示例配置 |
| 7 | 172.30.13.11:2881 | webview sendMsgMode-8b767ec0.js | OceanBase 示例配置 |
| 8 | 172.30.13.41 | webview sendMsgMode-8b767ec0.js | 数据库示例配置 |
| 9 | 172.31.64.29 | webview sendMsgMode-8b767ec0.js | 数据库示例配置 |

### 源码路径泄露

| # | 路径 | 位置 | 说明 |
|---|------|------|------|
| 1 | E:/github/cody/lib/shared/src/utils.ts | webview index-f0296668.js | Cody (Sourcegraph) 源码路径 |
| 2 | /home/jenkins/workspace/AGENTIDEA_ad622b01-5978-4025-9306-99e9f5da304c | agent/bin/index.js | Jenkins CI 构建路径 |

### 其他敏感信息

| # | 信息 | 位置 | 说明 |
|---|------|------|------|
| 1 | debugCode=9527 后门 | index.js | 可直接启用开发模式 |
| 2 | 用户名 hash 后门 | index.js | `(userName-version-002230).reduce(sum, charCodeAt, 0) % 100` |
| 3 | WebSocket 无认证 | PluginWebsocketClient.java | ws://127.0.0.1:{port}/ws/idea |
| 4 | SSL 信任所有证书 | OpenTelemetryConfig.java | no-op X509TrustManager |
| 5 | 明文数据库密码传输 | SqlService.java, ConnectConfigDto.java | password 字段通过 WebSocket 明文发送 |
| 6 | Token 在 URL 中暴露 | 08-auth-flow.md | login?token=xxx |
| 7 | Token 明文存储 | nedb (~/.iflycode/) | 无加密持久化 |
| 8 | 采样率 100% | OpenTelemetryConfig.java | 所有操作均上报遥测 |

---

## 风险统计

| 严重程度 | 数量 | 发现编号 |
|----------|------|----------|
| **高** | 9 | 1.2, 2.1, 2.2, 2.5, 4.1, 5.3, 8.1, 8.2, 9.1 |
| **中** | 12 | 1.1, 1.3, 2.3, 3.1, 3.2, 3.3, 3.4, 4.2, 5.2, 7.1, 7.2, 7.3, 9.2, 9.3, 6.2 |
| **低** | 5 | 2.4, 5.1, 5.4, 5.5, 6(部分) |

## 优先修复建议 (Top 5)

1. **移除 SSL 证书验证禁用** (发现 2.5/9.1) - 影响所有 APM 遥测通信，可被中间人攻击
2. **升级 RSA 密钥至 2048-bit+** (发现 2.1) - 1024-bit RSA 可被分解，直接影响登录安全
3. **WebSocket 添加认证机制** (发现 4.1) - 本机任意进程可伪造消息
4. **移除 debugCode 后门** (发现 5.3) - 9527 硬编码后门可直接启用开发模式
5. **Agent 二进制签名验证** (发现 8.1/8.2) - 无完整性校验可被替换为恶意版本
