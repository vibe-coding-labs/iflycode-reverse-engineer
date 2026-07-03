## 5. 安全分析

### 5.1 九个高风险发现详情

| # | OWASP | 发现 | 严重程度 | 证据 | 来源 |
|---|-------|------|---------|------|------|
| 1.2 | A01 | WebView JS Bridge 无权限隔离 | 高 | WebViewWindowPanel.handleRequest() 第 239-304 行: switch(module) 无权限校验 | doc 74 |
| 2.1 | A02 | RSA 1024-bit 公钥硬编码 | 高 | Agent index.js: 已脱敏 | doc 22, 74 |
| 2.2 | A02 | SM2/SM4 国密算法密钥硬编码 | 高 | Agent index.js: sm2.doEncrypt, sm4.encrypt/decrypt 调用 | doc 66, 74 |
| 2.5 | A02 | SSL 证书验证完全禁用 | 高 | OpenTelemetryConfig.java 第 140-165 行: no-op X509TrustManager | doc 74 |
| 4.1 | A04 | WebSocket 仅限 localhost 但无认证 | 高 | PluginWebsocketClient.java 第 311 行 | doc 04, 74 |
| 5.3 | A05 | debugCode=9527 后门 | 高 | index.js 第 41 行: `if(env_1.default.isDev || g===9527) return true` | doc 66, 74 |
| 8.1 | A08 | Agent 二进制文件无完整性校验 | 高 | PluginAgentCommandLine.java 第 104-146 行 | doc 74 |
| 8.2 | A08 | 自动更新无签名验证 (仅 MD5) | 高 | PluginUpdater.java, LoginInfo.java | doc 74 |
| 9.1 | A09 | APM 采样率 100% 但 SSL 禁用 | 高 | OpenTelemetryConfig.java 第 82 行 + 第 140-165 行 | doc 74 |

### 5.2 加密体系评估

| 算法 | 密钥长度 | 密钥来源 | 评估 | 来源 |
|------|---------|---------|------|------|
| RSA | 1024-bit | 硬编码于 Agent bundle | 不安全: 1024-bit 可分解 | doc 22, 66 |
| SM2 | 128 字节 hex (04 前缀) | 硬编码于 Agent bundle | 算法安全, 但密钥不可轮换 | doc 66 |
| SM4 | Base64 编码 | 硬编码于 Agent bundle | 算法安全, 但密钥不可轮换 | doc 66 |
| AES-256-CTR | 256-bit + IV | 硬编码于 Agent bundle | CTR 模式需确保 IV 唯一; 密钥不可轮换 | doc 66 |
| MD5 | 128-bit | 运行时计算 | 不安全: 存在碰撞攻击 | doc 74 |

> 来源: doc 66, 74

### 5.3 debugCode=9527 后门

```javascript
// Agent index.js 第 41-48 行
function getIsDevMode(g, A, S) &#123;
    if (env_1.default.isDev || g === 9527) return true;
    const v = `$&#123;A&#125;-$&#123;S&#125;-002230`
        .split("")
        .reduce(((d, E) => d + E.charCodeAt(0)), 0) % 100;
    return v == g;
&#125;
```

- `g` = config.json 中的 `agent.debugCode` 值
- `A` = 用户名 (userName)
- `S` = Agent 版本号
- 设置 `debugCode=9527` 直接启用开发模式
- 基于用户名 hash 的第二后门: `(userName-version-002230).reduce(sum, charCodeAt, 0) % 100 == debugCode`

> 来源: doc 66, 74

### 5.4 SSL 证书验证禁用

```java
// OpenTelemetryConfig.java 第 140-165 行
private X509TrustManager xE() &#123;
    return new X509TrustManager() &#123;
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) &#123;
            // 空实现 — 不验证客户端证书
        &#125;
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) &#123;
            // 空实现 — 不验证服务端证书
        &#125;
        @Override
        public X509Certificate[] getAcceptedIssuers() &#123;
            return new X509Certificate[0]; // 返回空数组
        &#125;
    &#125;;
&#125;
```

> 来源: doc 74

### 5.5 WebSocket 无认证

- 连接 URL: `ws://127.0.0.1:&#123;动态端口&#125;/ws/idea`
- 握手无 token 验证
- 无 Origin 检查
- 本机任意进程可连接并发送伪造消息
- 可伪造 USER_LOGIN, SQL_SOURCE_EDIT 等命令

> 来源: doc 04, 74

---

## 6. 性能特征

### 6.1 补全延迟和超时

| 配置项 | 值 | 来源 |
|--------|-----|------|
| 补全请求超时 | 10,000 ms (10s) | doc 73 |
| 自动触发延迟 (debounce) | 200 ms | doc 73 |
| 自动触发开关 | 默认开启 | doc 73 |
| 单测请求间隔 | 5~8 秒 (加权平均) | doc 73 |
| 上下文截断 (chat) | 2,000 字符 | doc 73 |
| 上下文分配 prefix | 38% | doc 73 |
| 上下文分配 suffix | 12% | doc 73 |
| 上下文分配 structure | 18% | doc 73 |
| 上下文分配 similar | 32% | doc 73 |
| 代码补全禁用语言 | txt, md | doc 73 |

### 6.2 心跳和重连机制

| 参数 | 值 | 来源 |
|------|-----|------|
| 心跳发送间隔 | 30,000 ms (30s) | doc 73 |
| 心跳超时阈值 | 10,000 ms (10s) | doc 73 |
| 心跳失败重启阈值 | 2 次超时 | doc 73 |
| AgentCheck 检查间隔 | 2,000 ms (2s) | doc 73 |
| AgentCheck 超时 | 3,000 ms (3s) | doc 73 |
| AgentCheck 失败阈值 | 3 次超时 | doc 73 |
| WebSocket 读/写/连接超时 | 60,000 ms (60s) | doc 73 |
| 最大重启次数 | 3 次 | doc 73 |
| 重启间隔 | 3,000 ms (3s) | doc 73 |
| 连接拒绝重试 | 3 次 | doc 73 |
| 端口获取重试 | 5 次 (递增等待 0+1+2+3+4=10s) | doc 73 |

### 6.3 缓存策略

| 缓存 | 实现 | 容量/过期 | 来源 |
|------|------|----------|------|
| SimpleCodeTipCache | LinkedHashMap + ReadWriteLock | SHA-256 键 | doc 73 |
| RecentFilesManager | ArrayDeque | 20 条 | doc 73 |
| RecentFileDirs | LinkedHashSet | 5 条 | doc 73 |
| AGENT_REQUEST | ConcurrentSkipListMap | 无容量限制 | doc 73 |
| Agent 通用缓存 | LRU | max=100, ttl=10s | doc 73 |
| Agent 大容量缓存 | LRU | max=1000, ttl=5min | doc 73 |
| Agent tree-sitter 解析 | LRU | max=4, ttl=60s | doc 73 |
| Agent 结构分析 | LRU | max=30, ttl=60s | doc 73 |
| SimilarCodeCache | LRU | max=10, ttl=30s | doc 71 |

### 6.4 tree-sitter 缓存瓶颈

tree-sitter 解析缓存仅 max=4 条 / ttl=60s，对大型项目是主要性能瓶颈。频繁切换文件时缓存命中率低，每次切换需重新解析 AST。建议提升至 20-50 条。

> 来源: doc 73

### 6.5 线程池配置

| 参数 | 值 | 来源 |
|------|-----|------|
| 核心线程 | 10 | doc 73 |
| 最大线程 | 200 | doc 73 |
| 队列容量 | 1,024 | doc 73 |
| 空闲存活 | 0 ms (立即回收) | doc 73 |
| 拒绝策略 | AbortPolicy (抛异常) | doc 73 |

### 6.6 WASM 内存配置

| 参数 | 值 | 来源 |
|------|-----|------|
| 初始内存 | 33,554,432 bytes (32MB) | doc 73 |
| 最大内存页 | 32,768 (2GB) | doc 73 |
| web-tree-sitter 版本 | 0.22.2 | doc 73 |
| WASM 检查间隔 | 10 (config.json: agent.wasmCheck) | doc 73 |

---

## 7. 跨平台差异

### 7.1 功能可用性矩阵

| # | 功能 | IDEA | VSCode | Eclipse | 限制原因 | 来源 |
|---|------|:----:|:------:|:-------:|---------|------|
| 1 | 代码补全 | Y | Y | Y | Agent API | doc 72 |
| 2 | 智能问答 | Y | Y | Y | WebView | doc 72 |
| 3 | 代码解释 | Y | Y | Y | Agent API | doc 72 |
| 4 | 函数注释 | Y | Y | Y | Agent API | doc 72 |
| 5 | 行间注释 | Y | Y | Y | Agent API | doc 72 |
| 6 | 代码优化 | Y | Y | Y | Agent API | doc 72 |
| 7 | 函数拆分 | Y | Y | Y | Agent API | doc 72 |
| 8 | 单元测试 | Y | Y | Y | Agent API | doc 72 |
| 9 | 批量单测 | Y | N | N | JUnit+Coverage | doc 72 |
| 10 | 批量函数注释 | Y | N | N | IDEA Action | doc 72 |
| 11 | Inline Chat | Y | P | N | Inlay API | doc 72 |
| 12 | 代码搜索 | Y | Y | Y | RAG API | doc 72 |
| 13 | 代码评审 | Y | Y | Y | Agent API | doc 72 |
| 14 | Commit Message | Y | Y | Y | Agent API | doc 72 |
| 15 | SQL 生成/优化 | Y | Y | Y | Agent API | doc 72 |
| 16 | 代码检查 | Y | P | N | ProblemsView | doc 72 |
| 17 | 一键修复 | Y | N | N | IntentionAction | doc 72 |
| 18 | Inlay Hints | Y | N | N | inlayProvider | doc 72 |
| 19 | 调试器异常过滤 | Y | N | N | exceptionFilter | doc 72 |
| 20 | 覆盖率集成 | Y | N | N | Coverage 模块 | doc 72 |
| 21 | 自动更新 | Y | N | N | displayIde | doc 72 |
| 22 | 多模型切换 | Y | Y | Y | Agent API | doc 72 |
| 23 | 知识库 (RAG) | Y | Y | Y | RAG API | doc 72 |
| 24 | 企业助理 | Y | Y | Y | 权限控制 | doc 72 |
| 25 | 主题适配 | Y | P | P | IDEA 原生 | doc 72 |
| 26 | 快捷键配置 | Y | P | P | IDEA Action | doc 72 |
| 27 | Mermaid 图表 | Y | Y | Y | WebView | doc 72 |
| 28 | 架构图 | Y | Y | Y | WebView | doc 72 |
| 29 | 历史记录 | Y | Y | Y | Agent API | doc 72 |
| 30 | 需求分析/拆分 | Y | Y | Y | 企业版权限 | doc 72 |

> 图例: Y = 完整支持, P = 部分支持, N = 不支持

### 7.2 功能统计

| 平台 | 完整支持 | 部分支持 | 不支持 | 总可用 |
|------|:-------:|:-------:|:------:|:-----:|
| **IDEA** | 30 | 0 | 0 | 30/30 |
| **VSCode** | 21 | 4 | 5 | 25/30 |
| **Eclipse** | 21 | 0 | 9 | 21/30 |

> 来源: doc 72

### 7.3 平台差异根因

| 差异根因 | 影响 | 来源 |
|---------|------|------|
| IntelliJ `codeInsight.inlayProvider` 扩展点 | Inlay Hints 仅 IDEA | doc 72 |
| IntelliJ `jvm.exceptionFilter` 扩展点 | 调试器异常过滤仅 IDEA | doc 72 |
| IntelliJ `IntentionAction` API | 一键修复仅 IDEA | doc 72 |
| IntelliJ `ProblemsView` 集成 | 代码检查面板仅 IDEA | doc 72 |
| JUnit + Coverage 模块依赖 | 批量单测+覆盖率仅 IDEA+Java | doc 72 |
| JS Bridge 通信差异 | 三种完全不同的 JS<->Native 通道 | doc 72 |
| Eclipse 不在 Agent 登录 ID 列表 | Eclipse 可能使用默认配置 | doc 72 |

---
