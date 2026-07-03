# iFlyCode 性能分析（静态推断）

> 版本: 3.4.2-222 | 分析方法: 静态代码分析（反编译 + 配置文件提取 + Agent webpack bundle 分析）
> 标注: [确认] = 来自配置文件/常量定义 | [推断] = 来自代码逻辑推断

---

## 1. 代码补全超时和延迟配置

### 1.1 补全请求超时

| 配置项 | 值 | 来源 | 类型 |
|--------|-----|------|------|
| `aicode.complete.time.out` | 10000 ms (10s) | BasicActionsBundle.properties | [确认] |
| `triggerTime` (自动触发延迟) | 200 ms | AICodeSettingsState.triggerTime | [确认] |
| `autoTrigger` (自动触发开关) | true (默认开启) | AICodeSettingsState.autoTrigger | [确认] |

**影响分析**:
- 10 秒超时是代码补全请求的硬性上限。超过此时间将抛出 `RequestTimeoutException`，用户看到补全失败
- 200 ms 触发延迟是用户停止输入后到发起补全请求的等待时间，属于 debounce 机制，避免每次按键都触发请求
- 超时异常 `RequestTimeoutException extends RuntimeException` 为非受检异常，不会被编译期强制捕获，可能导致补全静默失败

**优化建议**:
- 10 秒超时对于代码补全场景偏长，建议降至 5 秒并增加渐进式提示
- 200 ms 触发延迟合理，但可考虑根据网络延迟动态调整（如 ping 值高时自动增加）

### 1.2 单元测试请求间隔

| 配置项 | 值 | 来源 | 类型 |
|--------|-----|------|------|
| `unitRequestInterval` | 8 (初始), 最小 5 | AICodeSettingsState | [确认] |

**影响分析**:
- `setUnitRequestInterval` 使用加权平均: `(min(5, new) + current) / 2`，确保值不低于 5
- 批量单元测试生成时，每个文件请求间隔至少 5 秒，防止服务端过载

---

## 2. WebSocket 心跳和重连机制

### 2.1 HeartBeatCheckRunner 心跳参数

| 参数 | 值 | 来源 | 类型 |
|------|-----|------|------|
| 心跳发送间隔 | 30,000 ms (30s) | `timer.scheduleAtFixedRate(..., 30000L, 30000L)` | [确认] |
| 超时检查间隔 | 1,000 ms (1s) | `timer2.scheduleAtFixedRate(..., 30000L, 1000L)` | [确认] |
| 初始延迟 | 30,000 ms (30s) | 两个定时器均为 30s 延迟 | [确认] |
| 单次超时阈值 | 10,000 ms (10s) | `System.currentTimeMillis() - l <= 10000L` | [确认] |
| 失败触发重启阈值 | 2 次超时 | `atomicInteger.get() >= 2` | [确认] |
| 心跳命令 | `USER_VERSION` | `CommandEnum.USER_VERSION.getType()` | [确认] |

**影响分析**:
- 心跳每 30 秒发送一次，超时检查每秒执行，意味着最坏情况下 30s（心跳间隔）+ 10s（超时阈值）= 40s 才能检测到 Agent 无响应
- 2 次超时即触发重启，即 2 个心跳周期无响应后重启，最坏情况约 80 秒
- 心跳使用 `ConcurrentSkipListMap` 存储，支持多项目并发访问，但每次心跳只检查第一个 WebSocket

### 2.2 AgentCheckTimer 健康检查参数

| 参数 | 值 | 来源 | 类型 |
|------|-----|------|------|
| 主检查间隔 | 2,000 ms (2s) | `timer.scheduleAtFixedRate(..., 30000L, 2000L)` | [确认] |
| 超时检查间隔 | 1,000 ms (1s) | `timeOutTimer.scheduleAtFixedRate(..., 30000L, 1000L)` | [确认] |
| 初始延迟 | 30,000 ms (30s) | 两个定时器均为 30s 延迟 | [确认] |
| 单次超时阈值 | 3,000 ms (3s) | `System.currentTimeMillis() - l <= 3000L` | [确认] |
| 失败触发刷新阈值 | 3 次超时 | `atomicInteger.get() >= 3` | [确认] |

**影响分析**:
- AgentCheckTimer 是比 HeartBeatCheckRunner 更敏感的健康检查，2 秒检查一次，3 秒超时
- 3 次失败后发送 `login_show_fresh` 强制 WebView 刷新，并停止所有定时器
- 最快检测时间: 2s（检查间隔）+ 3s（超时）= 5s，比心跳检测快 8 倍

### 2.3 WebSocket 连接配置

| 参数 | 值 | 来源 | 类型 |
|------|-----|------|------|
| 读超时 | 60,000 ms (60s) | `readTimeout(60L, TimeUnit.SECONDS)` | [确认] |
| 写超时 | 60,000 ms (60s) | `writeTimeout(60L, TimeUnit.SECONDS)` | [确认] |
| 连接超时 | 60,000 ms (60s) | `connectTimeout(60L, TimeUnit.SECONDS)` | [确认] |
| WebSocket URL | `ws://127.0.0.1:&#123;port&#125;/ws/idea` | `createWebSocketConnect()` | [确认] |

**影响分析**:
- 60 秒的读写/连接超时对于本地 WebSocket 偏长，本地连接通常应在 5 秒内建立
- WebSocket 连接到本地 Agent（127.0.0.1），网络延迟可忽略，超时主要反映 Agent 进程是否存活

---

## 3. Agent 进程启动和端口分配

### 3.1 进程启动流程

| 阶段 | 耗时/配置 | 来源 | 类型 |
|------|-----------|------|------|
| Agent 解压 | 取决于 zip 大小 | `unZipAgent()` | [推断] |
| WASM 文件复制 | 取决于文件大小 | `copySource()` | [推断] |
| 进程启动 | 异步 | `launchAgent()` → `startNotify()` | [推断] |
| 端口获取重试 | 最多 5 次 | `JD()` 方法: `while (n >= 5)` | [确认] |
| 每次端口等待 | 递增秒数 (0,1,2,3,4) | `getAgentPort(pid, n)` → `awaitTermination(n, SECONDS)` | [确认] |
| WebSocket 连接建立 | 立即 | `checkAgent()` | [推断] |

**影响分析**:
- 端口获取最多等待 0+1+2+3+4 = 10 秒，加上 Agent 进程启动时间
- 首次启动总延迟估算: 解压(~2s) + 复制(~1s) + 进程启动(~3s) + 端口等待(~3s) + WS连接(~1s) = ~10s
- 后续启动跳过解压（检查 `~wgbj` 标记文件是否存在），延迟降至 ~5s

### 3.2 端口分配

| 参数 | 值 | 来源 | 类型 |
|------|-----|------|------|
| portfinder basePort | 8000 | Agent webpack bundle: `basePort=8e3` | [确认] |
| portfinder 版本 | ^1.0.32 | package.json | [确认] |

**影响分析**:
- Agent 进程使用 portfinder 从 8000 端口开始寻找可用端口
- 多实例场景下，端口从 8000 递增分配，避免冲突

### 3.3 重启策略

| 参数 | 值 | 来源 | 类型 |
|------|-----|------|------|
| 最大重启次数 | 3 次 | `RESTART_TIME = 3` | [确认] |
| 重启间隔 | 3,000 ms (3s) | `Thread.sleep(3000L)` | [确认] |
| 连接拒绝重试 | 3 次 | `connectAttempts < 3` | [确认] |
| 刷新后重连延迟 | 3,000 ms (3s) | `Thread.sleep(3000L)` in `de()` | [确认] |

**影响分析**:
- 3 次重启后停止尝试，总重启时间: 3 x 3s = 9s + 每次启动时间
- 第 3 次重启时通知 WebView 刷新（`pushAgentRefreshToWebView`），用户看到刷新提示
- 超过 3 次后 `Thread.sleep(3000L)` 然后直接返回，不再重试

---

## 4. 内存相关配置和缓存策略

### 4.1 插件端缓存

| 缓存 | 实现 | 容量 | 来源 | 类型 |
|------|------|------|------|------|
| SimpleCodeTipCache | LinkedHashMap + ReadWriteLock | 构造参数传入 | `SimpleCodeTipCache(int)` | [确认] |
| RecentFilesManager | ArrayDeque (per Project) | 20 条 | `private static final int enum = 20` | [确认] |
| RecentFileDirs | LinkedHashSet → 截取 | 5 条 | `if (n >= 5) break` | [确认] |
| EditorCacheUtil | IntelliJ UserData Key | 无容量限制 | `Key&lt;Boolean&gt;`, `Key&lt;LastSelectionTextCache&gt;` | [确认] |
| AGENT_REQUEST | ConcurrentSkipListMap | 无容量限制 | `PluginWebsocketClient.AGENT_REQUEST` | [确认] |
| AGENT_CLIENT_MAP | ConcurrentSkipListMap | 无容量限制 | `HeartBeatCheckRunner.AGENT_CLIENT_MAP` | [确认] |

**影响分析**:
- `SimpleCodeTipCache` 使用 SHA-256 哈希作为缓存键（`DigestUtil.sha256Hex`），读写锁保护并发访问
- `AGENT_REQUEST` 无容量限制，长时间运行可能积累大量未响应请求（依赖心跳超时清理）
- `RecentFilesManager` 限制 20 条最近文件，合理

### 4.2 Agent 端 LRU 缓存

| 缓存用途 | max | ttl (ms) | 来源 | 类型 |
|----------|-----|----------|------|------|
| 通用缓存 (1) | 100 | 10,000 (10s) | `LRUCache(&#123;max:100,ttl:1e4&#125;)` | [确认] |
| 文件缓存 (可配置) | this.maxFiles | this.ttl | `LRUCache(&#123;max:this.maxFiles,ttl:this.ttl,...&#125;)` | [确认] |
| 大容量缓存 | 1,000 | 300,000 (5min) | `LRUCache(&#123;max:1e3,ttl:3e5,...&#125;)` | [确认] |
| 小容量短 TTL | 10 | 30,000 (30s) | `LRUCache(&#123;max:10,ttl:3e4&#125;)` | [确认] |
| 中等缓存 (x2) | 20 | 10,000 (10s) | `LRUCache(&#123;max:20,ttl:1e4&#125;)` | [确认] |
| tree-sitter 解析 | 4 | 60,000 (1min) | `LRUCache(&#123;max:4,ttl:6e4,...&#125;)` | [确认] |
| 结构分析缓存 | 30 | 60,000 (1min) | `LRUCache(&#123;max:30,ttl:6e4,...&#125;)` | [确认] |

**影响分析**:
- tree-sitter 解析缓存仅 4 条/1 分钟，对大型项目可能频繁缓存未命中
- 大容量缓存 1000 条/5 分钟，是最主要的缓存层
- 所有 LRU 缓存均配置 `updateAgeOnGet: true, updateAgeOnHas: true`，访问时刷新 TTL

### 4.3 NeDB 对话历史存储

| 参数 | 值 | 来源 | 类型 |
|------|-----|------|------|
| NeDB 版本 | @seald-io/nedb ^4.0.4 | package.json | [确认] |
| 自动压缩 | 支持 | `setAutocompactionInterval` | [确认] |
| 持久化 | 支持 | `persistence` | [确认] |
| SQLite3 | ^5.1.7 | package.json (替代存储) | [确认] |

**影响分析**:
- NeDB 用于对话历史持久化存储，支持自动压缩防止文件膨胀
- 同时引入 SQLite3，可能作为 NeDB 的替代或补充存储引擎
- `sqlite2nedb` 迁移逻辑表明存在从 SQLite 到 NeDB 的数据迁移

### 4.4 线程池配置

| 参数 | 值 | 来源 | 类型 |
|------|-----|------|------|
| 核心线程数 | 10 | `ThreadPoolExecutor(10, ...)` | [确认] |
| 最大线程数 | 200 | `ThreadPoolExecutor(..., 200, ...)` | [确认] |
| 队列容量 | 1,024 | `LinkedBlockingQueue(1024)` | [确认] |
| 空闲存活时间 | 0 ms | `0L, TimeUnit.MILLISECONDS` | [确认] |
| 拒绝策略 | AbortPolicy | `ThreadPoolExecutor.AbortPolicy()` | [确认] |

**影响分析**:
- 线程池核心 10 / 最大 200，弹性扩展，但空闲线程立即回收
- 1024 队列容量 + AbortPolicy 意味着队列满时直接抛出 RejectedExecutionException
- 对于 IDE 插件场景，200 最大线程数偏高，可能影响 IDE 性能

---

## 5. WebSocket 消息频率和流式响应

### 5.1 流式响应机制

| 参数 | 值 | 来源 | 类型 |
|------|-----|------|------|
| 流式输出默认开启 | `stream = true` | `MessageDto.stream` | [确认] |
| 流式步进初始值 | 1 | `streamStep = 1` | [确认] |
| 当前长度初始值 | 1 | `currentLength = 1` | [确认] |
| 流式结束标记 | `ended` boolean | `ResponseStreamDto.ResponseData.ended` | [确认] |
| 流式文本增量 | `text` 字段 | `ResponseStreamDto.ResponseData.text` | [确认] |

**影响分析**:
- 所有请求默认启用流式输出（`stream = true`），可通过 `streamOutputConfig` 设置关闭
- 流式响应通过 `ended` 字段判断是否完成，每步增量文本通过 `text` 字段传递
- `streamStep` 和 `currentLength` 用于跟踪流式进度，但初始值均为 1（可能表示从第 1 步/第 1 个字符开始）

### 5.2 消息处理频率特征

| 场景 | 频率 | 来源 | 类型 |
|------|------|------|------|
| 心跳发送 | 每 30s | HeartBeatCheckRunner | [确认] |
| AgentCheckTimer 检查 | 每 2s | AgentCheckTimer | [确认] |
| 超时检查 | 每 1s | 两个检查器 | [确认] |
| 代码补全请求 | 用户触发 + 200ms debounce | triggerTime | [推断] |
| 流式响应接收 | 由 Agent 决定 | WebSocket onMessage | [推断] |
| Inline Chat 流式 | 逐行渲染 | InlineChatStreamHandleService | [推断] |

**影响分析**:
- WebSocket 消息处理在 `synchronized` 块中执行（`PluginWebsocketListener.onMessage`），单线程串行处理
- 流式响应期间，每收到一个 chunk 就触发 `WriteCommandAction.runWriteCommandAction` 修改文档
- Inline Chat 的 `HANDING_DATA` volatile 标志用于防止流式处理期间的其他编辑干扰

### 5.3 错误消息显示时长

| 场景 | 时长 | 来源 | 类型 |
|------|------|------|------|
| 请求频繁错误 | 3,000 ms (3s) | `addProperty("duration", 3000)` | [确认] |
| 普通错误 | 0 ms (不自动消失) | `addProperty("duration", 0)` | [确认] |

---

## 6. 大文件和大型项目处理

### 6.1 内容截断策略

| 参数 | 值 | 来源 | 类型 |
|------|-----|------|------|
| 上下文截断 (chat) | 2,000 字符 | `truncateDocumentString(A.content, d.range, 2e3)` | [确认] |
| prefix 占比 | 38% | `percent:38` | [确认] |
| suffix 占比 | 12% | `percent:12` | [确认] |
| structure 占比 | 18% | `percent:18` | [确认] |
| similar 占比 | 32% | `percent:32` | [确认] |
| 代码补全 prefix 占比 | 38% | `percent:38` | [确认] |
| 代码补全 suffix 占比 | 12% | `percent:12` | [确认] |

**影响分析**:
- 代码补全请求中，上下文按比例分配: prefix 38% + suffix 12% + structure 18% + similar 32%
- Chat 场景使用固定 2000 字符截断（`2e3`），代码补全使用 `maxCharSize` 动态计算
- `maxCharSize` 根据命令类型（`command`）动态决定最大上下文窗口大小

### 6.2 tree-sitter 解析性能

| 参数 | 值 | 来源 | 类型 |
|------|-----|------|------|
| tree-sitter 版本 | web-tree-sitter 0.22.2 | package.json | [确认] |
| WASM 初始内存 | 33,554,432 (32MB) | `INITIAL_MEMORY=33554432` | [确认] |
| WASM 最大内存页 | 32,768 (2GB) | `maximum:32768` | [确认] |
| 解析缓存 | max=4, ttl=60s | LRU 缓存 | [确认] |
| 结构分析缓存 | max=30, ttl=60s | LRU 缓存 | [确认] |
| WASM 检查间隔 | 10 | `agent.wasmCheck: 10` (config.json) | [确认] |

**影响分析**:
- tree-sitter WASM 初始分配 32MB 内存，最大可扩展至 2GB
- 解析缓存仅 4 条，对多文件项目意味着频繁重新解析
- `wasmCheck: 10` 可能表示每 10 次操作检查一次 WASM 状态

### 6.3 文件大小限制

| 参数 | 值 | 来源 | 类型 |
|------|-----|------|------|
| MySQL 错误消息包大小 | 512M | Agent bundle: `512M` | [确认] |
| 文件缓存默认 maxSize | 0 (无限制) | `maxSize:0` | [确认] |
| 代码补全禁用语言 | txt, md | `codeCompleteDisableLang = new String[]&#123;"txt", "md"&#125;` | [确认] |

**影响分析**:
- 512M 是 MySQL 驱动的包大小限制，不是 iFlyCode 自身的消息限制
- 代码补全对 txt 和 md 文件默认禁用，避免对非代码文件发起补全请求
- `maxSize:0` 表示文件缓存无大小限制，可能对大文件造成内存压力

---

## 7. OpenTelemetry APM 性能配置

### 7.1 遥测导出配置

| 参数 | 值 | 来源 | 类型 |
|------|-----|------|------|
| 采样率 | 100% (1.0) | `Sampler.traceIdRatioBased(1.0)` | [确认] |
| 导出超时 | 30,000 ms (30s) | `setExporterTimeout(30000L, MILLISECONDS)` | [确认] |
| 调度延迟 | 5,000 ms (5s) | `setScheduleDelay(5000L, MILLISECONDS)` | [确认] |
| 最大队列大小 | 2,048 | `setMaxQueueSize(2048)` | [确认] |
| 最大导出批次 | 512 | `setMaxExportBatchSize(512)` | [确认] |
| 重试最大次数 | 2 | `setMaxAttempts(2)` | [确认] |
| 重试初始退避 | 1,000 ms (1s) | `setInitialBackoff(Duration.ofSeconds(1L))` | [确认] |
| 重试最大退避 | 5,000 ms (5s) | `setMaxBackoff(Duration.ofSeconds(5L))` | [确认] |
| 退避乘数 | 1.5 | `setBackoffMultiplier(1.5)` | [确认] |
| APM 开关 | false (默认) | `aicode.otel.switch=false` | [确认] |
| APM 端点 | https://saas.api.example.com/v1/traces | `aicode.otel.endpoint` | [确认] |

**影响分析**:
- 100% 采样率在生产环境偏高，会产生大量遥测数据
- 2048 队列 + 512 批次，每 5 秒导出一次，理论上每秒可处理 ~100 个 Span
- APM 默认关闭（`otel.switch=false`），需 Agent 推送配置后才会启用

---

## 8. 插件更新检查

| 参数 | 值 | 来源 | 类型 |
|------|-----|------|------|
| 更新检查调度 | 定时重复 | `PluginUpdaterCheckService.scheduleRepeatedUpdateCheck` | [推断] |
| 自动更新开关 | true (默认开启) | `openAutoUpdate = true` | [确认] |

---

## 性能配置汇总表

| 维度 | 配置项 | 值 | 单位 | 类型 |
|------|--------|-----|------|------|
| **代码补全** | 补全超时 | 10,000 | ms | [确认] |
| | 触发延迟 | 200 | ms | [确认] |
| | 单测请求间隔 | 5~8 | s | [确认] |
| | 上下文截断(chat) | 2,000 | chars | [确认] |
| | prefix 占比 | 38 | % | [确认] |
| | suffix 占比 | 12 | % | [确认] |
| | structure 占比 | 18 | % | [确认] |
| | similar 占比 | 32 | % | [确认] |
| **心跳** | 心跳间隔 | 30,000 | ms | [确认] |
| | 心跳超时阈值 | 10,000 | ms | [确认] |
| | 心跳失败重启阈值 | 2 | 次 | [确认] |
| | AgentCheck 间隔 | 2,000 | ms | [确认] |
| | AgentCheck 超时 | 3,000 | ms | [确认] |
| | AgentCheck 失败阈值 | 3 | 次 | [确认] |
| **WebSocket** | 读超时 | 60,000 | ms | [确认] |
| | 写超时 | 60,000 | ms | [确认] |
| | 连接超时 | 60,000 | ms | [确认] |
| **Agent 进程** | 端口基址 | 8,000 | - | [确认] |
| | 端口获取重试 | 5 | 次 | [确认] |
| | 最大重启次数 | 3 | 次 | [确认] |
| | 重启间隔 | 3,000 | ms | [确认] |
| | 连接拒绝重试 | 3 | 次 | [确认] |
| **缓存** | tree-sitter 解析缓存 | 4/60,000 | 条/ms | [确认] |
| | 结构分析缓存 | 30/60,000 | 条/ms | [确认] |
| | 大容量缓存 | 1,000/300,000 | 条/ms | [确认] |
| | 最近文件数 | 20 | 条 | [确认] |
| **线程池** | 核心线程 | 10 | - | [确认] |
| | 最大线程 | 200 | - | [确认] |
| | 队列容量 | 1,024 | - | [确认] |
| **WASM** | 初始内存 | 33,554,432 | bytes (32MB) | [确认] |
| | 最大内存 | 2,147,483,648 | bytes (2GB) | [确认] |
| **APM** | 采样率 | 100 | % | [确认] |
| | 导出超时 | 30,000 | ms | [确认] |
| | 调度延迟 | 5,000 | ms | [确认] |
| | 队列大小 | 2,048 | - | [确认] |
| | 批次大小 | 512 | - | [确认] |

---

## 性能瓶颈推断

### 高优先级瓶颈

1. **tree-sitter 解析缓存过小** (max=4): 大型项目中频繁切换文件时，缓存命中率低，每次切换需重新解析 AST。建议提升至 20-50。

2. **WebSocket 消息串行处理**: `onMessage` 使用 `synchronized` 同步块，所有 WebSocket 消息串行处理。流式响应期间可能阻塞心跳响应检测。

3. **AGENT_REQUEST 无容量限制**: 长时间运行或网络异常时，未响应请求可能无限积累，导致内存泄漏。

### 中优先级瓶颈

4. **线程池最大 200 线程**: IDE 插件场景下 200 线程可能抢占 IDE 主线程资源，建议降至 50-100。

5. **60 秒 WebSocket 超时**: 本地连接不需要如此长的超时，建议降至 10-15 秒。

6. **心跳检测延迟**: 最坏情况 40 秒才能检测到 Agent 无响应，对用户体验影响较大。

### 低优先级瓶颈

7. **APM 100% 采样率**: 生产环境应降至 10-20%，减少遥测数据量和网络开销。

8. **INITIAL_MEMORY 32MB**: 对小文件项目偏大，但 WASM 不支持动态缩减，属于合理权衡。
