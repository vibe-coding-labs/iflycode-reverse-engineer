## 8. 混淆与反混淆

### 8.1 H() 算法完全破解

```
output[i] = input[i] XOR v[(len-i-1) % 106 + 1]
```

| 属性 | 说明 | 来源 |
|------|------|------|
| 算法 | 反向索引 XOR | doc 67 |
| 密钥 | 每类独立 v[] 序列, 周期 106 | doc 67 |
| 自逆性 | H(H(x)) = x | doc 67 |
| 位置无关 | 改变 input[i] 仅影响 output[i] | doc 67 |
| 内容无关 | XOR 密钥不依赖输入内容 | doc 67 |
| 密钥类数 | 33 个 H() 定义类 | doc 67, 80 |

### 8.2 解码统计

| 指标 | 值 | 来源 |
|------|-----|------|
| 扫描 .class 文件总数 | 566 | doc 80 |
| 含 H() 调用的类 | 279 | doc 80 |
| H() 调用总数 | 4628 | doc 80 |
| 高质量解码 (high) | 4114 | doc 80 |
| 中等质量解码 (medium) | 119 | doc 80 |
| 垃圾/不可读 (garbage) | 395 | doc 80 |
| 无 v[] 密钥 | 0 | doc 80 |
| 可用解码率 (high+medium) | 91.5% | doc 80 |
| 含中文字符的解码条目 | 175 | doc 80 |

### 8.3 自定义 ClassLoader

H() 的 .class 文件包含故意无效操作码：

| 无效操作码 | 偏移 | 说明 |
|-----------|------|------|
| `fconst_2` (0x0D) | 41 | 在整数上下文中使用浮点常量 |
| `fconst_1` (0x0C) | 42 | 在整数上下文中使用浮点常量 |
| `iinc 511` | -- | 操作数超范围 (合法: -128~127) |
| `iinc 255` | -- | 操作数超范围 |

运行时由自定义 ClassLoader (ByteBuddy transform) 将无效字节码替换为正确的 XOR 解码逻辑。

> 来源: doc 64, 67

### 8.4 混淆字段名重用 Java 关键字

H() 解码后发现的字段名使用 Java 关键字作为标识符（在混淆上下文中合法但反常规）：

| 混淆字段 | 上下文 | 来源 |
|---------|--------|------|
| `float` | BizResponse.obj | doc 53 |
| `byte` | BizResponse.resCode | doc 53 |
| `enum` | BizResponse.msg | doc 53 |

### 8.5 解码工具

**文件**: `tools/h_deobfuscator_final.py`

**用法**:
```bash
python3 tools/h_deobfuscator_final.py [base_dir] [output.json]
```

扫描所有 .class 文件中的 `ldc + invokestatic H` 模式，使用 33 组 v[] 密钥解码。

> 来源: doc 67

---

## 9. 数据模型

### 9.1 核心领域对象关系图

```
+=============================+
|       MessageDto (请求)      |
|  id, command, stream,       |
|  timeStamp, traceparent,    |
|  path, lang, content,      |
|  sessionId, modelCode,     |
|  permissionCode, data,     |
|  range, knowledge,         |
|  intelligent, relatedFiles,|
|  tipinfo, md5               |
+============+================+
             |
             | 1:1
             v
+=============================+     +=============================+
|     ResponseDto (响应)       |     |  ResponseStreamDto (流式)    |
|  id, code, msg, command,   |     |  id, code, msg,             |
|  data                       |     |  data: &#123;                    |
+=============================+     |    ended: boolean,          |
                                    |    text: string,            |
                                    |    showKeyMapTipFlag: bool  |
                                    |  &#125;                          |
                                    +=============================+

+=============================+     +=============================+
|     CodeInfoDto (聊天上下文) |     |  FirstChatMessage (首条消息) |
|  code, fileName, language,  |     |  sessionId, content,        |
|  lineInfo, path, range      |     |  codeInfo: CodeInfoDto      |
+=============================+     +=============================+

+=============================+     +=============================+
|   ConnectConfigDto (SQL连接) |     |   SettingsDto (设置)         |
|  host, port, user,          |     |  trigger_on_pause,          |
|  password, database, client |     |  trigger_time_delay,        |
+=============================+     |  code_mode, message_type,   |
                                    |  java_test, java_mock        |
                                    +=============================+
```

> 来源: doc 45, 53

### 9.2 DTO 层次结构

```
MessageDto (顶层请求)
  ├── data: Object (按 command 类型不同)
  │     ├── CodeTipRequestDto (code_complete)
  │     ├── SqlInfoDto (sql_generate/sql_optimize)
  │     ├── CodeCheckDto (code_check)
  │     ├── GitDiffDto (git_review)
  │     └── WebRequestDto (chat)
  ├── range: RangeDTO[]
  │     └── &#123;line, character&#125;
  ├── tipinfo: TipInfoDto
  │     └── &#123;user, platform, isShowOperateGuide&#125;
  └── intelligent: JsonArray
        └── [&#123;type, value&#125;]

ResponseDto (顶层响应)
  └── data: Object (按 command 类型不同)
        ├── ResponseStreamDto.ResponseData (流式)
        ├── CodeCheckOriginDto (code_check)
        └── GitReviewResultDto (git_review)
```

> 来源: doc 45, 53

### 9.3 四层嵌套 UnitTestDto

```
UnitTestDto
  └── DataDTO
        └── FunctionDataDTO
              ├── Data — 方法数据
              │     ├── path — 文件路径
              │     ├── language — 编程语言
              │     └── text — 源代码文本
              ├── CodeList — 代码列表
              └── RangeDTO — 代码范围
                    ├── startLine
                    ├── startCharacter
                    ├── endLine
                    └── endCharacter
```

> 来源: doc 46

---

## 10. 技术栈汇总

### 10.1 Plugin 端 (IDE 内)

| 技术 | 版本 | 用途 | 来源 |
|------|------|------|------|
| Java | 17+ | 插件核心语言 | doc 01 |
| Kotlin | — | 部分工具类 (EditorKt, JComponentKt) | doc 01 |
| IntelliJ SDK | 2021.1+ | 插件框架 | doc 34 |
| OkHttp | 4.12.0 | WebSocket 客户端 | doc 04 |
| Gson | — | JSON 序列化 | doc 04 |
| ByteBuddy | — | 自定义 ClassLoader (H() 解码) | doc 64 |
| OpenTelemetry | — | APM 遥测 | doc 18 |
| fastutil | — | 高性能集合 (ObjectLinkedOpenHashSet) | doc 53 |
| Lombok | — | DTO 注解 (@Data) | doc 53 |

### 10.2 WebView 前端

| 技术 | 版本 | 用途 | 来源 |
|------|------|------|------|
| Vue.js | **2.7.14** | 前端框架 (非 Vue 3) | doc 65 |
| Pinia | — | 状态管理 (3 stores) | doc 65 |
| Vue Router | — | 路由管理 (17 路径) | doc 65 |
| Element UI | — | UI 组件库 (70+ 组件) | doc 65 |
| Mermaid | — | 图表渲染 (20+ 图类型) | doc 65 |
| KaTeX | — | 数学公式渲染 | doc 65 |
| Cytoscape | — | 网络图可视化 | doc 65 |
| DOMPurify | — | HTML 安全过滤 | doc 65 |
| Vite | — | 构建工具 | doc 65 |

### 10.3 Agent 端 (Node.js 子进程)

| 技术 | 版本 | 用途 | 来源 |
|------|------|------|------|
| Node.js | **<=12** (EOL) | 运行时 | doc 66 |
| Express | — | HTTP 服务 | doc 66 |
| ws | — | WebSocket 服务端 | doc 66 |
| knex | 2.5.1 | SQL 查询构建器 | doc 66 |
| mysql2 | 3.2.0 | MySQL 驱动 | doc 66 |
| pg | ^8.11.3 | PostgreSQL 驱动 | doc 66 |
| sqlite3 | ^5.1.7 | SQLite 驱动 | doc 66 |
| @seald-io/nedb | ^4.0.4 | 本地文档存储 | doc 66 |
| sm-crypto | ^0.3.13 | 国密 SM2/SM4 | doc 66 |
| web-tree-sitter | 0.22.2 | AST 解析 (WASM) | doc 66 |
| @napi-rs/snappy | — | Snappy 压缩 (7 平台) | doc 66 |
| node-fetch | 2.x | HTTP 客户端 | doc 66 |
| lodash | 4.16.6 | 工具库 (有已知 CVE) | doc 66 |
| marked | 1.2.9 | Markdown 渲染 (有 XSS CVE) | doc 66 |
| portfinder | ^1.0.32 | 端口发现 | doc 66 |

### 10.4 加密体系

| 算法 | 用途 | 密钥来源 | 来源 |
|------|------|---------|------|
| RSA 1024-bit | 登录密码加密 | 硬编码 | doc 22, 66 |
| RSA 2048-bit | 其他数据加密 | 硬编码 | doc 66 |
| SM2 | 国密数据加密 | 硬编码公钥 | doc 66 |
| SM4 | 权限缓存加密 | 硬编码密钥 | doc 66 |
| AES-256-CTR | Agent 端数据加密 | 硬编码密钥+IV | doc 66 |
| MD5 | 文件完整性校验 | 运行时计算 | doc 04, 74 |

---

## 11. 文档索引

| # | 文档编号 | 标题 | 一句话摘要 |
|---|---------|------|-----------|
| 1 | 01 | 整体架构 | 三层通信模型 (IDE/Agent/Cloud) 与目录结构 |
| 2 | 02 | Agent 进程管理 | Agent 启动/停止/重启流程与端口分配 |
| 3 | 03 | Agent 服务端接口 | Agent 暴露的 HTTP/WS 端点列表 |
| 4 | 04 | WebSocket 通信协议 | WebSocket 连接参数、消息格式、生命周期 |
| 5 | 05 | 消息格式定义 (DTO) | MessageDto/ResponseDto/ResponseStreamDto 字段说明 |
| 6 | 06 | 命令体系完整参考 | 109 个 CommandEnum 按 15 模块分组 |
| 7 | 07 | WebView JS Bridge 协议 | JCEF 双向通信机制与 124 种消息类型 |
| 8 | 08 | 用户认证流程 | OAuth/QR/密码登录与 Token 管理 |
| 9 | 09 | 智能对话协议 | Chat 消息发送/接收/流式/历史管理 |
| 10 | 10 | 代码补全协议 | code_complete 请求/响应/Inlay 渲染 |
| 11 | 11 | 内联聊天协议 | Inline Chat 发送/停止/接受/拒绝 |
| 12 | 12 | SQL 生成/优化协议 | 数据源管理、SQL 对话、连接测试 |
| 13 | 13 | 单元测试协议 | 单测生成请求/响应/模板选择 |
| 14 | 14 | Git 评审协议 | Code Review/Commit Message/Diff 分析 |
| 15 | 15 | 代码搜索协议 | 语义搜索与 RAG 知识库管理 |
| 16 | 16 | 代码检查协议 | 代码检查/修复/重复代码检测 |
| 17 | 17 | 心跳检测与错误恢复 | 30s 心跳与 2 次超时重启机制 |
| 18 | 18 | OpenTelemetry APM 遥测 | 100% 采样率与 W3C Trace Context 传播 |
| 19 | 19 | 设置同步协议 | 设置读写与 WebView 同步 |
| 20 | 20 | 枚举值完整参考 | 所有枚举类型与值定义 |
| 21 | 21 | 混淆技术分析 | H() 字符串混淆与自定义 ClassLoader |
| 22 | 22 | Agent->Cloud HTTPS 通信协议 | 57 个 API 端点与请求/响应格式 |
| 23 | 23 | Agent 内部架构与 Prompt 模板 | Agent 模块架构与 AI Prompt 模板 |
| 24 | 24 | Action 体系完整分析 | IntelliJ Action 注册与功能映射 |
| 25 | 25 | 内联聊天 UI 架构 | Inline Chat 面板组件与渲染流程 |
| 26 | 26 | 模板系统分析 | Velocity 模板引擎与单测模板 |
| 27 | 27 | 编辑器集成架构 | Inlay/Action/TypedHandler 集成 |
| 28 | 28 | 事件监听体系 | 11 个 Listener 与 15 个 Service 的触发关系 |
| 29 | 29 | 字符串混淆机制深度分析 | H() 算法原理与无效操作码分析 |
| 30 | 30 | WebView 前端架构分析 | Vue.js + Pinia + Element UI 技术栈 |
| 31 | 31 | Agent 二进制深度分析 | Node.js 二进制与 webpack bundle 结构 |
| 32 | 32 | 代码补全完整流程分析 | 13 步补全流程与核心类详解 |
| 33 | 33 | Q/ 包功能分析 | util 子包工具类功能 |
| 34 | 34 | Plugin.xml 与插件配置分析 | 插件元数据、扩展点与依赖 |
| 35 | 35 | 消息属性与 i18n 分析 | BasicActionsBundle 多语言支持 |
| 36 | 36 | 完整类清单与继承关系 | 566 个 .class 文件清单 |
| 37 | 37 | Agent 进程管理深度分析 | 进程启动/重启/端口查找详细流程 |
| 38 | 38 | WebView 与工具窗口分析 | PluginToolWindowPanel 与 WebView 集成 |
| 39 | 39 | Agent 服务层深度分析 | 12 个 Service 类功能与依赖 |
| 40 | 40 | 内联聊天系统分析 | Inline Chat 完整子系统架构 |
| 41 | 41 | 枚举体系完整分析 | 所有枚举类型值与用途 |
| 42 | 42 | Diff 与 APM 系统分析 | DiffService 与 OpenTelemetry 集成 |
| 43 | 43 | 设置与配置系统分析 | AICodeSettingsState 全局配置中心 |
| 44 | 44 | 请求管理与代码补全系统分析 | 请求取消/超时/防抖机制 |
| 45 | 45 | WebSocket 通信与 DTO 数据模型分析 | 核心通信类与数据结构 |
| 46 | 46 | 单元测试生成系统分析 | 双生成策略与 7 种框架组合 |
| 47 | 47 | Action 体系完整分析 | Action 注册与功能映射 |
| 48 | 48 | 内容处理器与编辑器工具分析 | EditorUtils/OverlayUtils 工具 |
| 49 | 49 | 代码生成包分析 | Generate 包功能与依赖 |
| 50 | 50 | View 包与 WebView 窗口分析 | WebViewWindowPanel 集成点 |
| 51 | 51 | Complete 包与 Inlay 提示系统分析 | InlayCompletionHintFactory 渲染 |
| 52 | 52 | 错误/异常体系与辅助系统分析 | 异常层次结构与错误处理 |
| 53 | 53 | Domain/DTO/Service/Request 层深度分析 | 核心数据流管道 |
| 54 | 54 | Agent 包与通信层深度分析 | WebSocket 客户端/监听器/分发器 |
| 55 | 55 | Q 包与 Util 工具体系分析 | 37 个工具类功能 |
| 56 | 56 | Chat 与 Git 集成系统分析 | Chat/GitReview/CommitMessage 交互 |
| 57 | 57 | Inline Chat 子系统完整分析 | 8 个子包与 SessionController |
| 58 | 58 | Listener/APM/Settings 包完整分析 | 事件监听/APM/配置传播 |
| 59 | 59 | Action 体系与 Inline Chat 子包分析 | Action 子包与 Inline Chat 子包 |
| 60 | 60 | Template 单测模板系统完整分析 | 7 套模板与 Velocity 变量映射 |
| 61 | 61 | Language/Message/Status/StatusBar/Test/UI 包分析 | 语言/消息/状态栏/测试/UI 包 |
| 62 | 62 | Agent Service 层与 Action Batch 批量系统分析 | Service 层与批量操作系统 |
| 63 | 63 | 逆向工程综合分析报告 | 早期综合分析报告 |
| 64 | 64 | H() 字符串混淆体系与解码方案分析 | H() 算法逆向与 v[] 提取方法 |
| 65 | 65 | WebView 前端完整分析 | Vue.js 2.7 + Pinia + Element UI |
| 66 | 66 | Agent Webpack Bundle 完整分析 | 加密体系/57 API/数据库/Tree-sitter |
| 67 | 67 | H() String Deobfuscation Complete Solution | 解码算法/33 密钥/工具使用 |
| 68 | 68 | plugin.xml 完整注册表分析 | 扩展点/Action/Service 注册 |
| 69 | 69 | i18n 完整字符串表分析 | 多语言字符串完整提取 |
| 70 | 70 | API 端点请求/响应格式分析 | 57 API 请求/响应字段级分析 |
| 71 | 71 | codeVector/RAG 语义搜索工作流分析 | RAG 授权/索引/检索完整流程 |
| 72 | 72 | 跨 IDE 支持差异分析 | IDEA 30/30, VSCode 25/30, Eclipse 21/30 |
| 73 | 73 | 性能分析 | 超时/心跳/缓存/线程池/WASM 配置 |
| 74 | 74 | 安全审计报告 (OWASP Top 10) | 9 高/16 中/4 低风险发现 |
| 75 | 75 | Velocity 模板体系与单测生成完整流程 | 模板变量映射与生成策略 |
| 76 | 76 | 核心 Service 类字段级详细分析 | 12 个 Service 类字段级文档 |
| 77 | 77 | WebSocket 消息分发链完整分析 | 109 命令分发路由详解 |
| 78 | 78 | 代码补全 Inlay 渲染系统分析 | TipInlayRenderer/InlayRendering 样式 |
| 79 | 79 | Updater/Domain/FileLoader 深入分析 | 更新/领域模型/文件加载器 |
| 80 | 80 | H() 字符串解码全量结果 | 4628 次调用 91.5% 可用率 |
| 81 | 81 | 综合交叉引用与架构图谱 | 110 双向依赖对/五层架构/三大数据流 |

---

## 12. 遗留问题

### 12.1 仍需动态验证的项目

| # | 项目 | 原因 | 建议验证方法 |
|---|------|------|-------------|
| 1 | AES-256-CTR IV 生成方式 | 静态分析无法确认 IV 是否使用密码学安全随机数 | 运行时 hook crypto.createCipheriv |
| 2 | WebSocket 消息处理串行化影响 | synchronized 块在流式响应期间是否阻塞心跳检测 | 多线程压力测试 |
| 3 | Agent 进程首次启动延迟估算 | 解压/复制时间依赖硬件 | 多平台实测 |
| 4 | tree-sitter 缓存命中率 | max=4 在不同项目规模下的实际表现 | 大型项目 (1000+ 文件) 实测 |
| 5 | NeDB 自动压缩间隔 | 静态分析仅确认支持，未提取具体间隔值 | 运行时监控 ~/.iflycode/ 目录 |
| 6 | 用户名 hash 后门实际可利用性 | 需要知道特定用户名+版本号组合 | 暴力枚举 100 以内 debugCode |
| 7 | SQL Chat 生成的 SQL 是否直接执行 | 静态分析发现 knex 查询能力，但未确认执行路径 | SQL Chat 交互测试 |
| 8 | Cody 源码路径引用的实际影响 | 路径泄露不等于代码复制，需确认许可证合规 | 代码相似度比对 |
| 9 | Eclipse 平台的实际功能可用性 | 多项功能标记为"推断"，需实际安装测试 | Eclipse 插件安装实测 |
| 10 | AGENT 进程 Node.js <=12 的实际 CVE 影响 | 需确认内嵌 Node 版本具体小版本号 | 运行 `node --version` |

### 12.2 无法从静态分析确认的行为

| # | 行为 | 不确定性 | 来源 |
|---|------|---------|------|
| 1 | 云端 API 的限流策略 | 仅能从客户端超时推断，无法确认服务端行为 | doc 22 |
| 2 | AI 模型的 Prompt 模板完整内容 | Agent bundle 中模板被混淆/压缩，仅部分可读 | doc 23 |
| 3 | 企业版私有部署的配置差异 | 仅能从代码中的 isIflyTekVersion() 推断 | doc 66 |
| 4 | Token 刷新的实际安全策略 | 仅确认自动重登机制，未确认 refresh_token 机制 | doc 08 |
| 5 | WebSocket 消息大小 512MB 限制的实际执行 | 仅从配置发现，未确认是否有服务端限制 | doc 74 |
| 6 | APM 采样率 100% 是否为默认值 | 配置中发现 1.0，但 APM 默认关闭 (otel.switch=false) | doc 73 |
| 7 | 代码补全的 `maxCharSize` 动态计算逻辑 | 依赖 command 类型，具体值需运行时确认 | doc 71 |
| 8 | NeDB -> SQLite 迁移逻辑的触发条件 | 发现 sqlite2nedb 迁移代码，但触发条件不明 | doc 66 |

---

*报告结束。本文档基于 81 份逆向工程文档的综合分析生成，所有结论均标注来源文档编号。*