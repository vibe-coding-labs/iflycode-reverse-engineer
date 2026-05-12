# iFlyCode 3.4.2-222 逆向工程 TODO

> 最后更新: 2026-05-13 | 进度: 81 专题文档已完成

## 统计

- **专题文档**: 81 已完成 / 81 总计
- **类文件**: 574 个 .class 文件已分析
- **包**: 47+ 个叶子包已覆盖
- **字符串**: 15000+ 个常量池字符串已提取
- **H() 混淆**: 7 定义类 v[] 序列已提取, 算法已完全破解, 4628 次调用全量解码 (91.5% 可用率)
- **字节码**: 566 类常量池依赖关系已解析, 110 个双向依赖对已识别

---

## 已完成 ✅

### 基础架构分析 (docs/24-35)
- [x] 24 — Action System 分析
- [x] 25 — Inline Chat UI 分析
- [x] 26 — Template System 分析
- [x] 27 — Editor Integration 分析
- [x] 28 — Listener Events 分析
- [x] 29 — Obfuscated Strings (H() 混淆) 分析
- [x] 30 — WebView Frontend (Vue.js 3 + Pinia) 分析
- [x] 31 — Agent Binary (Node.js + Tree-sitter) 分析
- [x] 32 — Code Complete Flow 分析
- [x] 33 — Q Package (CodeTipType 混淆) 分析
- [x] 34 — Plugin XML 配置分析
- [x] 35 — Properties/i18n 国际化分析

### 深度分析 (docs/36-47)
- [x] 36 — Complete Class Inventory (566 类)
- [x] 37 — Agent Process Management
- [x] 38 — WebView ToolWindow (JCEF)
- [x] 39 — Agent Service Layer
- [x] 40 — Inline Chat System
- [x] 41 — Enums Complete (31 枚举类)
- [x] 42 — Diff/APM Analysis
- [x] 43 — Settings Configuration (4 PersistentStateComponent)
- [x] 44 — Request Complete System
- [x] 45 — WebSocket DTO Model
- [x] 46 — Unit Test Generation
- [x] 47 — Action System Complete

### 批量分析 (docs/48-52)
- [x] 48 — Content Handler & Editor Utils
- [x] 49 — Generate Package (补全缓存)
- [x] 50 — View Package (WebView/JCEF)
- [x] 51 — Complete Package (Inlay Hint)
- [x] 52 — Error/Exception/Debugger/Icons/Updater

### 服务层分析 (docs/53-57)
- [x] 53 — Domain/DTO/Service/Request Layer
- [x] 54 — Agent Communication Deep Analysis
- [x] 55 — Q/Util Package Analysis
- [x] 56 — Chat/Git Integration
- [x] 57 — Inline Chat Subsystem Complete

### 子系统深度分析 (docs/58-63)
- [x] 58 — Listener/APM/Settings Analysis
- [x] 59 — Action/Inline Chat Subpackages Analysis
- [x] 60 — Template System Complete Analysis (80+ 类)
- [x] 61 — Language/Message/Status/Test/UI Analysis
- [x] 62 — Agent Service Layer & Batch Operations
- [x] 63 — Comprehensive Analysis Report (综合报告)
- [x] 64 — H() Deobfuscation Analysis (H() 混淆解码方案)
- [x] 65 — WebView Frontend Complete Analysis (Vue 2.7 + Pinia + Bridge)

- [x] 67 — H() Deobfuscation Complete Solution (算法完全破解, 7 类 v[] 提取)

### 补充分析 (docs/68-75)
- [x] 68 — plugin.xml 完整注册表 (53 个注册项: 18 Action, 12 Service, 17 Extension, 8 Listener, 2 ExtensionPoint, 2 Component)
- [x] 69 — i18n 完整字符串表 (511 条字符串, 17 功能模块, 4 数据源)
- [x] 70 — API 端点请求/响应格式 (57+ 端点, 79.8% 确认率, 30+ DTO 反编译)
- [x] 71 — codeVector/RAG 语义搜索工作流 (双路径搜索: 本地 Tree-sitter + 远程 RAG)
- [x] 72 — 跨 IDE 支持差异 (IDEA 30/30, VSCode 25/30, Eclipse 21/30)
- [x] 73 — 性能分析 (10s 补全超时, 30s 心跳, tree-sitter 缓存 max=4 瓶颈)
- [x] 74 — 安全审计 OWASP Top 10 (9 高风险, 12 中风险, 5 低风险)
- [x] 75 — Velocity 模板与单测生成流程 (6 阶段时序, 17 模板变量, 双模式生成)

### 深度补充分析 (docs/76-81)
- [x] 76 — 核心 Service 类字段级详细分析 (20 类, 440 方法, 78 字段, 统一 handleAction 分发模式)
- [x] 77 — WebSocket 消息分发链完整分析 (14 模块路由, tableswitch 分发, 640+ CommandEnum)
- [x] 78 — Inlay 渲染系统分析 (30+ 类, 完整渲染管线, 6 快捷键映射, LRU 缓存)
- [x] 79 — Updater/Domain/FileLoader 分析 (自动更新无 MD5 验证, 4 层嵌套 DTO)
- [x] 80 — H() 解码全量结果 (4628 次调用, 91.5% 可用率, 279 类解码)
- [x] 81 — 综合交叉引用与架构图谱 (566 类依赖解析, 110 双向依赖, 五层架构图)

---

## 待完成 🔲

### 高优先级
- [x] **H() 字符串解码方案** — 算法完全破解 (doc 67)
  - 算法: output[i] = input[i] XOR v[(len-i-1) % 106 + 1]
  - H() 是自逆函数: H(H(x)) = x
  - 7 个定义类的 v[] 序列已全部提取并验证
  - v[] 不依赖输入内容或调用者方法名
  - 字节码含故意无效操作码, 运行时由自定义 ClassLoader 变换
- [x] **WebView JS Bundle 详细分析** — 已完成 (doc 65)
  - 修正: Vue.js 2.7.14 而非 Vue 3
  - 3 个 Pinia Stores (chat, codeCheck, sql)
  - 50+ Java→JS 消息类型完整提取
  - 3 种 IDE Bridge 实现 (IDEA/VSCode/Eclipse)
  - 39 个权限代码、17 个聊天类型
  - 156 个中文 UI 字符串
  - Element UI 70+ 组件
- [x] **Agent webpack bundle 详细分析** — 已完成 (doc 66)
  - 67 条 API 路由（Starspark 43 + RAG 14 + UnitTest 6 + 其他 4）
  - 5 种加密算法完整实现（RSA/SM2/SM4/AES/MD5），全部密钥硬编码
  - RSA 1024-bit + SM2 C1C3C2 + SM4 PKCS#5 + AES-256-CTR
  - 5 种数据库支持（SQLite3/NeDB/MySQL2/PostgreSQL/DMDB）
  - 9 种 Tree-sitter 解析器、1098 个中文字符串
  - 开发环境 IP 泄露、Cody 源码路径泄露

### 中优先级
- [x] **RSA 公钥用途确认** — 已完成 (doc 66)
  - RSA 1024-bit 公钥用于登录加密（用户名+密码分块加密）
  - SM2 公钥用于国密加密场景
  - SM4 密钥用于权限数据缓存和代码补全数据上报
  - AES-256-CTR 用于消息加密
- [x] **API 端点请求/响应格式确认** — 已完成 (doc 70, 57+ 端点, 79.8% 确认率)
- [x] **codeVector 语义搜索** — 已完成 (doc 71, 双路径: 本地 Tree-sitter + 远程 RAG)
- [x] **消息加密协议** — 已完成 (doc 66)
  - RSA + SM2 + SM4 + AES-256-CTR + MD5 完整实现
  - 登录: RSA 分块加密用户名/密码
  - 权限缓存: SM4 加密 JSON
  - 代码补全上报: SM4 加密 prefixCode/completeCode
  - 消息: AES-256-CTR 加密
- [x] **跨 IDE 支持** — 已完成 (doc 72, IDEA 30/30, VSCode 25/30, Eclipse 21/30)

### 低优先级
- [x] **plugin.xml 完整注册表** — 已完成 (doc 68, 53 个注册项)
- [x] **国际化字符串表** — 已完成 (doc 69, 511 条字符串)
- [x] **性能分析** — 已完成 (doc 73, 补全延迟/心跳/缓存/流式响应)
- [x] **安全审计** — 已完成 (doc 74, OWASP Top 10, 9 高风险)

### 新增补充项
- [x] **核心 Service 类字段级分析** — 已完成 (doc 76, 20 类 440 方法)
- [x] **WebSocket 消息分发链** — 已完成 (doc 77, 14 模块路由)
- [x] **Inlay 渲染系统** — 已完成 (doc 78, 30+ 类完整管线)
- [x] **Updater/Domain/FileLoader** — 已完成 (doc 79, 自动更新+领域模型)
- [x] **H() 解码全量应用** — 已完成 (doc 80, 4628 次调用 91.5% 可用率)
- [x] **综合交叉引用与架构图谱** — 已完成 (doc 81, 566 类依赖解析)

---

## 关键发现汇总

### 架构
1. 三层通信: Plugin (Java) ↔ Agent (Node.js:6832) ↔ Cloud (星火 API)
2. CommandEnum 驱动: 640+ 命令枚举值驱动所有功能
3. 流式响应: 所有 AI 功能使用 WebSocket + streamStep
4. 双生成模式: 本地模板 + AI 辅助

### 安全
1. H() 混淆已完全破解: 反向索引 XOR + 周期 106 的固定密钥序列
2. 自定义 ClassLoader: 运行时变换含无效操作码的字节码
3. RSA 1024-bit 公钥硬编码: 2 把 RSA 公钥在 Agent bundle 中
4. 消息加密: RSA + AES, 512MB 大小限制
5. SSL 证书验证完全禁用: OpenTelemetryConfig 创建空操作 X509TrustManager
6. debugCode=9527 后门: 绕过所有安全检查启用开发模式
7. WebSocket 无认证: 任何本地进程可连接伪造命令
8. 自动更新仅 MD5 验证: MD5 可碰撞，恶意更新可通过

### 技术
1. 574+ 类, 47+ 包, 27+ H() 定义点
2. 外部依赖: Gson, Guava, Hutool (6处), OkHttp, OpenTelemetry, JCEF, Velocity
3. WebView: Vue.js 2.7.14 + Pinia (7 stores) + Element UI + TypeScript + Vite
4. Agent: Node.js <=12 + Express + Tree-sitter (10语言) + SQLite/NeDB + MySQL
5. 统一消息分发: handleAction (WebView) + handleAgentAction (Agent)
6. 110 个双向依赖对, agent.service 是最大循环源
7. WebViewWindowPanel 上帝对象: 12+ 服务直接操作

### Top 5 最大类
1. JavaTestBuilderImpl (956 strings) — Java 单测生成
2. TemplateRequestService (893 strings) — AI 辅助单测
3. CreateTestFileTask (801 strings) — 测试文件创建
4. CommandEnum (726 strings) — 命令枚举
5. MethodFactory (634 strings) — Mock 方法工厂