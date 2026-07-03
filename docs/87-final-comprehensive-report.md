# iFlyCode 3.4.2-222 逆向工程综合最终报告

> 版本: 3.4.2-222 | 报告日期: 2026-05-13 | 分析方法: 静态分析 (反编译 .class + Agent webpack bundle 逆向 + WebView 前端逆向)
> 分析范围: 566 个 .class 文件, 3.6 MB Agent bundle, 84 个 WebView JS 文件
> 来源文档: docs/01 ~ docs/81

---

## 1. 执行摘要

### 1.1 项目概述

iFlyCode 3.4.2-222 是科大讯飞推出的 AI 编程助手，以 IntelliJ IDEA 插件为主要载体，同时支持 VS Code 和 Eclipse。系统采用三层通信架构（IDE 插件 / 本地 Agent / 云端服务），通过 WebSocket 实现插件与 Agent 的双向通信，Agent 再通过 HTTPS 转发至讯飞星火大模型。插件端由 Java/Kotlin 编写（566 个 .class 文件，65 个包），Agent 端为 Node.js webpack bundle（3.6 MB），WebView 前端基于 Vue.js 2.7 + Pinia + Element UI。系统提供代码补全、智能问答、内联聊天、单元测试生成、SQL 生成/优化、代码检查/修复、Git 评审、语义代码搜索等 30 项功能，其中 IDEA 平台功能最完整（30/30），VS Code 次之（25/30），Eclipse 最受限（21/30）。

### 1.2 关键发现

| # | 发现 | 严重程度 | 来源 |
|---|------|---------|------|
| 1 | SSL 证书验证完全禁用 — APM 遥测通道使用 no-op X509TrustManager，允许中间人攻击 | 高 | doc 74 |
| 2 | debugCode=9527 硬编码后门 — 修改 config.json 即可启用开发模式，绕过安全检查 | 高 | doc 66, 74 |
| 3 | WebSocket 无认证 — 本机任意进程可连接 ws://127.0.0.1:{port}/ws/idea 伪造消息 | 高 | doc 04, 74 |
| 4 | RSA 1024-bit 公钥硬编码 — 已可被分解，直接影响登录密码安全 | 高 | doc 22, 74 |
| 5 | Agent 二进制无完整性校验 — 可被替换为恶意版本，Plugin 无验证地启动 | 高 | doc 74 |

### 1.3 风险评估

| 风险类别 | 高 | 中 | 低 | 合计 |
|---------|---|---|---|------|
| 权限控制失效 | 1 | 2 | 0 | 3 |
| 加密机制失效 | 2 | 2 | 1 | 5 |
| 注入攻击 | 0 | 4 | 0 | 4 |
| 不安全设计 | 1 | 1 | 0 | 2 |
| 安全配置错误 | 1 | 1 | 3 | 5 |
| 身份认证失败 | 0 | 3 | 0 | 3 |
| 软件完整性失败 | 2 | 0 | 0 | 2 |
| 日志监控不足 | 1 | 2 | 0 | 3 |
| 过时组件 | 1 | 1 | 0 | 2 |
| **合计** | **9** | **16** | **4** | **29** |

---

## 2. 系统架构

### 2.1 三层通信架构图

```
+=============================================================================+
|                          用户工作站 (localhost)                               |
|                                                                             |
|  +---------------------------------------------------------------------+   |
|  |                    JetBrains IDE (IntelliJ IDEA)                      |   |
|  |                                                                     |   |
|  |  +-------------------+  JCEF WebView   +-------------------------+  |   |
|  |  |  Editor 集成       |<=============>|  WebView Panel (UI)     |  |   |
|  |  |  Inlay/Action     |  JS Bridge     |  (JCEF Chromium)        |  |   |
|  |  +--------+----------+               +------------+------------+  |   |
|  |           |                                         |              |   |
|  |           | Java API                                | JS->Java     |   |
|  |           v                                         v              |   |
|  |  +-------------------------------------------------------------+  |   |
|  |  |              Plugin Core (Java/Kotlin)                       |  |   |
|  |  |                                                             |  |   |
|  |  |  +------------------------+  +---------------------------+  |  |   |
|  |  |  | Service 层             |  | DTO 层                    |  |  |   |
|  |  |  | ChatService            |  | MessageDto (请求)         |  |  |   |
|  |  |  | UserService            |  | ResponseDto (响应)        |  |  |   |
|  |  |  | CodeCompleteService    |  | ResponseStreamDto (流式)  |  |  |   |
|  |  |  | SqlService             |  | CodeInfoDto              |  |  |   |
|  |  |  | InlineChatCmdService   |  | FirstChatMessage         |  |  |   |
|  |  |  | GitReviewService       |  | ...                      |  |  |   |
|  |  |  +----------+-------------+  +---------------------------+  |  |   |
|  |  |             |                                              |  |   |
|  |  |             v                                              |  |   |
|  |  |  +-----------------------------+                          |  |   |
|  |  |  | PluginWebsocketClient       |                          |  |   |
|  |  |  | OkHttp 4.12.0 WebSocket     |                          |  |   |
|  |  |  | Timeout: connect/read/write |                          |  |   |
|  |  |  |         = 60s              |                          |  |   |
|  |  |  +-------------+--------------+                          |  |   |
|  |  +----------------+-----------------------------------------+  |   |
|  +------------------+--------------------------------------------+   |
|                     |                                                |
|                     | WebSocket (JSON)                               |
|                     | ws://127.0.0.1:{动态端口}/ws/idea              |
|                     v                                                |
|  +-----------------------------+----------------------------------+   |
|  |           Local Agent (Node.js 子进程)                            |   |
|  |                                                                 |   |
|  |  平台特定 Node.js 二进制 + index.js (3.6MB webpack bundle)       |   |
|  |  + worker.js (976 KB)                                           |   |
|  |  - sqlite3 本地存储 (NeDB)                                      |   |
|  |  - tree-sitter WASM 代码解析 (9 种语言)                          |   |
|  |  - snappy 压缩 (7 个平台原生模块)                                |   |
|  |  - sm-crypto 国密算法 (SM2/SM4)                                 |   |
|  |  - knex.js SQL 查询构建器                                        |   |
|  |                                                                 |   |
|  |  端口: portfinder 从 8000 开始动态分配                           |   |
|  +-----------------------------+----------------------------------+   |
|                                |                                     |
|                                | HTTPS                               |
|                                v                                     |
|                       +---------------------------+                   |
|                       |    iFlyCode 云端服务       |                   |
|                       |                           |                   |
|                       | SaaS: iflycode-xfsaas     |                   |
|                       |       .example.com         |                   |
|                       | API:  iflycode-api        |                   |
|                       |       .example.com        |                   |
|                       |                           |                   |
|                       | /api/starspark/v1/agent/* |                   |
|                       | /api/ragserver/v1/*       |                   |
|                       | /api/usercenter/v1/*      |                   |
|                       +---------------------------+                   |
+=============================================================================+
```

### 2.2 五层软件架构图

```
+=============================================================================+
|                        iFlyCode 五层架构                                     |
+=============================================================================+
|                                                                             |
|  +-- UI 层 --------------------------------------------------------------+  |
|  |                                                                       |  |
|  |  +----------------+  +----------------+  +-------------------------+ |  |
|  |  | Action         |  | WebView        |  | Inline Chat UI         | |  |
|  |  | action/        |  | view/          |  | inline/                 | |  |
|  |  | action.click/  |  | .WebViewWindow |  | .InlineChatPanel        | |  |
|  |  | action.batch/  |  |  Panel         |  | .InlineChatInputPanel   | |  |
|  |  | .CodeAction    |  | .PluginTool    |  | .InlineChatTopPanel     | |  |
|  |  | .BaseAction    |  |  WindowPanel   |  | .InlineChatInlay        | |  |
|  |  | .LogoutAction  |  | toolwindow/    |  | inline.render/          | |  |
|  |  +-------+--------+  +-------+--------+  | .BtnPanelRenderer       | |  |
|  |          |                    |           | .CategoryPanelRenderer  | |  |
|  |          |                    |           | .ErrorPanelRenderer     | |  |
|  |          |                    |           | .StopPanelRenderer      | |  |
|  |          |                    |           +------------+------------+ |  |
|  |  +-------+------+  +--------+-------+                |              |  |
|  |  | ui/          |  | complete/      |                |              |  |
|  |  | .ActionBtn   |  | .InlayCompl    |                |              |  |
|  |  | .Font/Style  |  |  etionHint    |                |              |  |
|  |  | .RoundBorder |  |  Factory      |                |              |  |
|  |  +--------------+  +----------------+                |              |  |
|  +----------------------------------------------------|--------------+  |
|                                                       |                 |
|  +-- 控制层 ------------------------------------------|--------------+  |
|  |                                                    |              |  |
|  |  +----------------+  +----------------+  +--------+----------+  |  |
|  |  | Listener       |  | Controller     |  | IDE Action Router  |  |  |
|  |  | listener/      |  | inline.ctrl/   |  | inline.ide/        |  |  |
|  |  | .AutoCodeGen  |  | .Session       |  | .IdeEditorAction   |  |  |
|  |  |  erateListener |  |  Controller    |  |  Router             |  |  |
|  |  | .CodeEditor   |  | .ChatInput     |  | .ConditionalEditor |  |  |
|  |  | .PluginDoc    |  |  Controller    |  |  ActionHandler      |  |  |
|  |  | .GitBranch    |  | .EphemeralChat|  | .IdeActionService  |  |  |
|  |  | .ThemeChange  |  |  SessionCtrl  |  | .ActionScope        |  |  |
|  |  +-------+--------+  +-------+--------+  +--------+----------+  |  |
|  |          |                    |                    |              |  |
|  |  +-------+------+  +--------+-------+  +---------+----------+  |  |
|  |  | Agent Core   |  | CommandEnum     |  | Inline Chat Status |  |  |
|  |  | agent/       |  | agent.enums/   |  | inline.status/     |  |  |
|  |  | .PluginWeb   |  | .CommandEnum   |  | .StatusService     |  |  |
|  |  |  socketClnt  |  | .ModuleEnum    |  | .Subscription      |  |  |
|  |  | .SocketMsg   |  | .PageEnum      |  | .ServiceProvider   |  |  |
|  |  |  HandleLstn  |  | .PermissionEnum|  |                    |  |  |
|  |  | .HeartBeat   |  |                |  |                    |  |  |
|  |  |  CheckRunner |  |                |  |                    |  |  |
|  |  +-------+------+  +----------------+  +--------------------+  |  |
|  +----------|----------------------------------------------|--------+  |
|             |                                              |           |
|  +-- 服务层 -|----------------------------------------------|--------+  |
|  |          |                                              |        |  |
|  |  +-------+-------------+  +-----------------------------+--+     |  |
|  |  | Agent Service       |  | Editor Service               |     |  |
|  |  | agent.service/      |  | service/                      |     |  |
|  |  | .ChatService        |  | .EditorManagerService         |     |  |
|  |  | .CommonService      |  | .EditorRequestService         |     |  |
|  |  | .CodeCompleteSvc    |  | .RequestTipService            |     |  |
|  |  | .CodeCheckService   |  | .TipRenderer / TipCache       |     |  |
|  |  | .GitReviewService   |  | service.editor/               |     |  |
|  |  | .InlineChatCmdSvc   |  | .EditorManagerServiceImpl     |     |  |
|  |  | .UserService        |  | .RequestTipServiceImpl        |     |  |
|  |  | .SqlService         |  | .CancelRequestTip             |     |  |
|  |  | .CodeSearchService  |  | .DocumentActionTracker        |     |  |
|  |  | .InitService        |  | .AgentCodeTipList             |     |  |
|  |  | .PluginAgentProcSvc|  | .TipInlayRenderer              |     |  |
|  |  +-------+-------------+  +---------------+----------------+     |  |
|  |          |                                |                     |  |
|  |  +-------+-------------+  +---------------+----------------+   |  |
|  |  | Diff Service        |  | Test/Template Service          |   |  |
|  |  | diff/                |  | test/                            |   |  |
|  |  | .DiffService         |  | .UnitTestService                 |   |  |
|  |  | .CloudDiffUtil       |  | .BatchUnitTestService            |   |  |
|  |  | .FileService         |  | .CppTestService                  |   |  |
|  |  +---------------------+  | template/                        |   |  |
|  |                           | .TemplateGenerator               |   |  |
|  |                           | template.generator/              |   |  |
|  |                           | .CreateTestFileTask               |   |  |
|  |                           | .CreateTestMethodTask             |   |  |
|  |                           +----------------------------------+   |  |
|  +------------------------------------------------------------------+  |
|                                                                          |
|  +-- 数据层 -----------------------------------------------------------+  |
|  |                                                                     |  |
|  |  +----------------+  +----------------+  +--------------------+    |  |
|  |  | Agent DTO      |  | Domain         |  | Enums             |    |  |
|  |  | agent.dto/      |  | domain/        |  | enums/             |    |  |
|  |  | .MessageDto     |  | .LineInfo      |  | .AICodeStatus      |    |  |
|  |  | .ResponseDto    |  | .Position      |  | .LanguageEnum      |    |  |
|  |  | .ResponseStream |  | .Range         |  | .CodeTipType       |    |  |
|  |  |  Dto            |  | .Suggestion    |  | .WebViewDataType   |    |  |
|  |  | .WebRequestDto  |  | .CommandCache  |  |  Enum              |    |  |
|  |  | .SettingsDto    |  | .VirtualFileUri|  | .ChatOperationEnum |    |  |
|  |  | .UserInfoDto    |  | .GetTipsResult  |  | .SendKeyEnum       |    |  |
|  |  | .CodeInfoDto    |  |                |  | .OperateActionEnum  |    |  |
|  |  | .ConnectConfig  |  |                |  | .PermissionEnum     |    |  |
|  |  | agent.dto.chat/ |  |                |  | .ClientTypeEnum     |    |  |
|  |  | agent.dto.search|  |                |  | .RestartEnum        |    |  |
|  |  +----------------+  +----------------+  +--------------------+    |  |
|  |                                                                     |  |
|  |  +----------------+  +----------------+  +--------------------+    |  |
|  |  | Inline DTO     |  | Test DTO       |  | Template DTO       |    |  |
|  |  | inline.dto/    |  | test.dto/       |  | template.request   |    |  |
|  |  | .InlineChatInfo|  | .UnitTestDto    |  |  .dto/             |    |  |
|  |  | .LastChatQInfo |  | .UnitTestAgent  |  | .CaseBranch        |    |  |
|  |  | .LastSelection |  |  Dto            |  | .CaseParam         |    |  |
|  |  |  TextCache     |  | .RequestCase    |  | .CaseResult        |    |  |
|  |  +----------------+  |  CodeDto        |  | .ToMockMethod      |    |  |
|  |                      | .BatchUnitTest  |  +--------------------+    |  |
|  |                      |  Dto            |                              |  |
|  |                      +----------------+                              |  |
|  +---------------------------------------------------------------------+  |
|                                                                          |
|  +-- 基础设施层 -------------------------------------------------------+  |
|  |                                                                     |  |
|  |  +----------------+  +----------------+  +--------------------+    |  |
|  |  | Utils          |  | Settings       |  | APM                |    |  |
|  |  | util/ (37 类)   |  | settings/ (6)  |  | apm/ (5)           |    |  |
|  |  | .AICodeUtils    |  | .AICode        |  | .OpenTelemetry      |    |  |
|  |  | .StringUtils    |  |  SettingsState |  |  Service            |    |  |
|  |  | .FileUtils      |  | .AICodeRequest |  | .OpenTelemetry      |    |  |
|  |  | .EditorKt       |  |  Settings      |  |  Util               |    |  |
|  |  | .PsiUtils       |  | .UnitTest      |  | .OpenTelemetry      |    |  |
|  |  | .LogUtil        |  |  SettingsState |  |  Config             |    |  |
|  |  | .Maps           |  | .BatchUnit     |  | apm.enums/          |    |  |
|  |  | .PropertyUtils  |  |  TestSettings  |  | .SpanAttrEnum       |    |  |
|  |  +----------------+  +----------------+  | .TracerEnum         |    |  |
|  |                                           +--------------------+    |  |
|  |  +----------------+  +----------------+  +--------------------+    |  |
|  |  | Message        |  | Exception      |  | Content/File       |    |  |
|  |  | message/ (1)    |  | exception/ (2) |  | content.util/ (2)  |    |  |
|  |  | .BasicActions   |  | .RequestCancel |  | .EditorUtils       |    |  |
|  |  |  Bundle         |  |  Exception     |  | .OverlayUtils       |    |  |
|  |  |                 |  | .RequestTimeout |  | content.util.file/  |    |  |
|  |  |                 |  |  Exception     |  | .FileUtils          |    |  |
|  |  +----------------+  +----------------+  +--------------------+    |  |
|  +---------------------------------------------------------------------+  |
+=============================================================================+
```

### 2.3 110 个双向依赖对

系统存在 110 个包间双向依赖对，其中最关键的循环依赖源如下：

| 依赖对 | 层级跨越 | 影响 |
|--------|---------|------|
| agent.service <--> action.batch | 服务层 <-> UI层 | Service 直接操作 UI 批量操作 |
| agent.service <--> action.click | 服务层 <-> UI层 | Service 直接操作 UI 点击 |
| agent.service <--> inline.controller | 服务层 <-> 控制层 | Service 直接操作内联聊天控制 |
| agent.service <--> listener | 服务层 <-> 控制层 | Service 直接操作事件监听 |
| agent.service <--> view | 服务层 <-> UI层 | Service 直接操作 WebView |
| agent.service <--> ui | 服务层 <-> UI层 | Service 直接操作 UI 组件 |
| agent.service <--> toolwindow | 服务层 <-> UI层 | Service 直接操作工具窗口 |
| agent.service <--> diff | 服务层 <-> 服务层 | 代码补全与 Diff 循环依赖 |
| agent.service <--> apm | 服务层 <-> 基础设施 | 业务逻辑与遥测循环依赖 |
| template <--> template.builder | 模板子系统内部 | 内聚但双向 |
| template <--> template.context.domain | 模板子系统内部 | 内聚但双向 |
| inline <--> inline.controller | 内联聊天子系统内部 | 内聚但双向 |

> 来源: doc 81, 1.2 节

### 2.4 核心组件清单

| 排名 | 包名 | 被依赖次数 | 类数 | 角色 |
|------|------|-----------|------|------|
| 1 | com.aicode.util | 49 | 37 | 基础设施 - 工具集 |
| 2 | com.aicode.agent.service | 29 | 32 | 服务层 - Agent 通信服务 |
| 3 | com.aicode.enums | 26 | 31 | 数据层 - 枚举定义 |
| 4 | com.aicode.message | 26 | 1 | 基础设施 - 消息资源 |
| 5 | com.aicode.diff | 24 | 6 | 服务层 - Diff 对比 |
| 6 | com.aicode.action.batch | 21 | 11 | UI层 - 批量操作 |
| 7 | com.aicode.content.util.file | 20 | 3 | 基础设施 - 文件工具 |
| 8 | com.aicode.settings | 20 | 6 | 基础设施 - 配置 |
| 9 | com.aicode.exception | 19 | 2 | 基础设施 - 异常 |
| 10 | com.aicode.agent.enums | 18 | 5 | 数据层 - Agent 枚举 |

> 来源: doc 81, 第 2 节

---

## 3. 通信协议

### 3.1 WebSocket 协议

| 参数 | 值 | 来源 |
|------|-----|------|
| URL | `ws://127.0.0.1:{动态端口}/ws/idea` | doc 04 |
| Client | OkHttp 4.12.0 | doc 04 |
| connectTimeout | 60s | doc 04 |
| readTimeout | 60s | doc 04 |
| writeTimeout | 60s | doc 04 |
| 消息大小限制 | 512MB | doc 74 |
| 消息格式 | JSON | doc 04 |

**请求消息 (MessageDto) 核心字段:**

| 字段 | 类型 | 说明 |
|------|------|------|
| id | String | UUID 请求标识 |
| command | String | 命令类型 (CommandEnum) |
| stream | boolean | 是否流式 (默认 true) |
| timeStamp | long | 消息时间戳 (ms) |
| traceparent | String | W3C Trace Context |
| path | String | 当前文件路径 |
| lang | String | 文件语言 |
| content | String | 文件/选中代码内容 |
| sessionId | String | 会话 ID |
| modelCode | String | AI 模型代码 |
| permissionCode | String | 权限代码 |
| data | Object | 命令附加数据 |
| range | RangeDTO[] | 代码范围 |
| md5 | String | 文件 MD5 |

> 来源: doc 04, 05

**响应消息格式:**

| 类型 | 格式 | 用途 |
|------|------|------|
| ResponseDto | `{id, code, msg, command, data}` | 普通响应 |
| ResponseStreamDto | `{id, code, msg, data: {ended, text, showKeyMapTipFlag}}` | 流式响应 |
| BizResponse | `{resCode, msg, obj}` | 业务响应 |

> 来源: doc 04, 70

### 3.2 14 模块消息分发

`CommandEnum` 定义 109 个命令，按 15 个 `AgentModuleEnum` 分组。`SocketMessageHandleListener` 根据 command 值 switch 分发到对应 Service：

| 模块 | 命令数 | 目标 Service | 来源 |
|------|--------|-------------|------|
| INIT | 11 | InitService, UserService | doc 06, 77 |
| LOGIN | 9 | UserService | doc 06 |
| CODE_COMPLETE | 11 | CodeCompleteService | doc 06, 32 |
| CHAT | 18 | ChatService | doc 06, 09 |
| INLINE_CHAT | 4 | InlineChatCommandService | doc 06, 11 |
| UNIT_TEST | 4 | UnitTestService, BatchUnitTestService | doc 06, 46 |
| CODE_CHECK | 3 | CodeCheckService | doc 06, 16 |
| GIT | 8 | GitReviewService | doc 06, 14 |
| SQL | 8 | SqlService | doc 06, 12 |
| CODE_SEARCH | 3 | CodeSearchService | doc 06, 15 |
| ACTION | 2 | CommonService | doc 06 |
| SETTING | 2 | CommonService | doc 06 |
| RAG | 3 | CodeSearchService | doc 71 |
| GENERAL | 1 | CommonService | doc 06 |
| ERROR | 1 | CommonService | doc 06 |

> 来源: doc 06, 77

### 3.3 流式响应机制

```
Agent 推送流式数据
     │
     v
PluginWebsocketListener.onMessage(json)
     │
     v
SocketMessageHandleListener.onMessage(msg)
     │
     ├── 解析 ResponseStreamDto
     │     ├── data.ended == false → 追加 text 到当前内容
     │     └── data.ended == true  → 标记完成
     │
     ├── ChatService: 逐 chunk 推送到 WebView
     │     └── WebViewWindowPanel.callJS("receiveData", json)
     │
     ├── CodeCompleteService: 逐 chunk 更新 Inlay
     │     └── EditorManagerServiceImpl.$F.onNext(list)
     │
     └── InlineChatCommandService: 逐 chunk 写入编辑器
           └── WriteCommandAction.runWriteCommandAction()
```

> 来源: doc 04, 32, 77

### 3.4 JS Bridge 三平台实现

| 维度 | IDEA Bridge | VSCode Bridge | Eclipse Bridge |
|------|-------------|---------------|----------------|
| 源文件 | ideaUtil-11ab0730.js | vscodeUtil-49d49699.js | eclipseUtil-82d0751a.js |
| JS->IDE 发送 | `window.myObject.sendMessage(JSON)` | `vscode.postMessage({type, value})` | `window.sendMessage(JSON)` |
| IDE->JS 接收 | `window.receiveData = callback` | `window.addEventListener("message")` | `window.receiveData = callback` |
| 消息格式 | `{type, value}` (value 直接) | `{type, value: JSON.stringify(value)}` | `{type, value: JSON.stringify(value)}` |
| WebView 引擎 | JCEF (Chromium) | VSCode Webview (Chromium) | SWT Browser (WebKit/IE) |
| 平台检测 | 无 (默认) | `acquireVsCodeApi !== undefined` | 无 (宿主注入) |

> 来源: doc 07, 72

---

## 4. 功能子系统

### 4.1 代码补全 (13 步流程)

```
[1]  AutoCodeGenerateListener (Document 变更监听)
         │  条件: 自动补全启用 + 非选择/非命令模式 + InlineChat 不活跃
         v
[2]  DocumentActionTracker$ActionListener
         │  追踪: 字符输入/删除/粘贴
         │  防抖: 连续快速输入不重复触发
         v
[3]  RequestTipServiceImpl.requestTip(Editor, CodeTipRequestType)
         │  构建 CodeTipRequestDto:
         │    prefixCode, suffixCode, structure, imports,
         │    similarStr, language, filePath, cursorOffset
         v
[4]  构建 MessageDto (command = "code_complete")
         v
[5]  PluginWebsocketClient.send(MessageDto)
         v
[6]  Agent 转发到云端 (HTTPS POST -> 星火 API, SSE 流式)
         v
[7]  流式响应 -> ResponseStreamDto (text, ended)
         v
[8]  AgentCodeTipList 处理响应
         │  转换为 GetTipsResult.Tip / CodeInlayList
         v
[9]  EditorManagerServiceImpl.$F (Flow.Subscriber)
         │  onNext: 处理补全数据
         │  onComplete: 补全完成
         │  onError: RequestTimeoutException
         v
[10] RequestResultList 管理
         │  inlayLists: ObjectLinkedOpenHashSet<CodeInlayList>
         │  index: 当前显示索引
         v
[11] TipInlayRenderer 渲染
         │  灰色字体 + 斜体 + ActionButton
         v
[12] InlayRendering 样式
         │  TextAttributes: 前景色灰色, EffectType
         v
[13] 用户交互
         │  Tab    -> acceptInlay()  (替换文本)
         │  Esc    -> disposeTips()  (清除 Inlay)
         │  Alt+]  -> cycleNext()    (下一个建议)
         │  Alt+[  -> cyclePrevious() (上一个建议)
         │  Ctrl+-> -> acceptWord()  (接受一个词)
         │  Ctrl+|  -> acceptLine()  (接受一行)
```

> 来源: doc 32

### 4.2 智能聊天

| 维度 | 说明 | 来源 |
|------|------|------|
| WebView UI | Vue.js 2.7 + Pinia chat store + Element UI | doc 65 |
| 消息发送 | WebView -> JS Bridge -> ChatService -> WebSocket -> Agent | doc 09 |
| 上下文收集 | CommonService.collectContext(): prefix/suffix/structure/similar | doc 71 |
| 流式响应 | Agent 逐 chunk 推送 -> WebView 逐字渲染 | doc 09 |
| 历史管理 | NeDB 本地持久化, 自动压缩 | doc 66 |
| 多模型切换 | modelCode 字段, 支持 iFlyMate 等多种模型 | doc 09 |
| 知识增强 | KnowledgeExpress: 代码/文档/Web/数据库四路知识收集 | doc 71 |
| 企业助理 | assistantType 字段, iFlyDev 等企业定制模型 | doc 09 |

### 4.3 Inline Chat

| 组件 | 说明 | 来源 |
|------|------|------|
| SessionController | 会话生命周期管理 (455 strings, 最大类) | doc 57 |
| EphemeralChatSessionController | 临时会话管理 | doc 57 |
| InlineChatPanel / InputPanel / TopPanel | UI 组件 | doc 25, 57 |
| InlineChatInlay | 编辑器内嵌面板 | doc 57 |
| InlineChatCommandService | 命令发送/接收 | doc 11 |
| InlineChatStatusService | 状态订阅/通知 | doc 57 |
| IdeActionService / IdeEditorActionRouter | IDE 操作路由 | doc 27, 57 |
| 操作 | Accept/Reject/Undo/Retry/Stop | doc 57 |
| 快捷键 | Alt+Y(接受) / Alt+X(拒绝) / Alt+Z(撤销) / Alt+D(重试) | doc 57 |
| DiffService | 代码差异展示 | doc 42, 57 |
| 流式写入 | WriteCommandAction.runWriteCommandAction() 逐 chunk 写入编辑器 | doc 57 |

### 4.4 单元测试生成 (6 阶段时序)

```
阶段 1: 方法选择
  └── UnitTestAction.actionPerformed() -> UnitTestDialog.show()
       ├── 选择测试框架 (JUnit4/5)
       ├── 选择 Mock 框架 (Mockito/PowerMock/Disabled)
       ├── 模板生成开关 (GenaratebyTemplateSwitchEnum)
       └── 排除方法配置 (ExcludeMethodConfigurable)

阶段 2: 方法信息收集
  └── UnitTestService.testCollectionGenerate()
       ├── 收集方法签名、参数类型、返回类型
       ├── TestSubjectInspector 分析调用链
       └── MockBuilder 生成 Mock 配置

阶段 3a: 模板生成 (快速模式)
  └── VelocityInitializer.render(template, context)
       ├── 选择模板 (7 套: JUnit4/5, Mockito, PowerMock, TestNG, SpringBootTest)
       ├── 填充上下文变量 ($TESTED_CLASS, $MockitoMockBuilder, $replacementTypes)
       └── 生成测试代码

阶段 3b: AI 精准生成 (精准模式)
  └── CommonService.sendWsMessage(TEST_MAKE_CASE, data)
       ├── 发送方法信息到 Agent
       ├── Agent 调用 AI 模型分析代码分支
       └── 返回精准测试用例

阶段 4: 文件写入
  └── CreateTestFileTask.run()
       ├── 定位目标目录 (TargetDirectoryLocator)
       └── 写入测试代码

阶段 5: 编译+执行 (可选)
  └── 编译测试类 -> 执行 -> 收集结果

阶段 6: 覆盖率收集 (可选, 仅 IDEA+Java)
  └── 依赖 com.intellij.modules.coverage
```

> 来源: doc 46, 75

**7 种框架组合:**

| 模板 | 测试框架 | Mock 框架 | 来源 |
|------|---------|----------|------|
| JUnit4.java.ft | JUnit 4 | 无 | doc 75 |
| JUnit5.java.ft | JUnit 5 | 无 | doc 75 |
| JUnit4&Mockito.java.ft | JUnit 4 | Mockito | doc 75 |
| JUnit5&Mockito.java.ft | JUnit 5 | Mockito | doc 75 |
| JUnit4&Powermock.java.ft | JUnit 4 | PowerMock | doc 75 |
| TestNG&Mockito.java.ft | TestNG | Mockito | doc 75 |
| SpringBootTest&Mockito.java.ft | SpringBootTest | Mockito+Spring | doc 75 |

### 4.5 SQL 生成/优化

| 功能 | CommandEnum | 说明 | 来源 |
|------|-------------|------|------|
| SQL 生成对话 | SQL_GENERATE_TALK | 自然语言 -> SQL | doc 12 |
| SQL 优化对话 | SQL_OPTIMIZE_TALK | SQL -> 优化后 SQL | doc 12 |
| 独立 SQL 生成 | SQL_GENERATE | 直接生成 SQL | doc 12 |
| 独立 SQL 优化 | SQL_OPTIMIZE | 直接优化 SQL | doc 12 |
| 数据源列表 | SQL_SOURCE_LIST | 获取已配置数据源 | doc 12 |
| 数据库类型 | SQL_SOURCE_TYPES | MySQL/PostgreSQL/Oracle/TxSQL | doc 12 |
| 连接测试 | SQL_TEST_CONNECT | 测试数据库连接 | doc 12 |
| 表列表 | SQL_TABLE_LIST | 获取数据库表名 | doc 12 |
| 保存数据源 | SQL_SOURCE_EDIT | 保存/编辑连接配置 | doc 12 |
| 删除数据源 | SQL_SOURCE_DELETE | 删除连接配置 | doc 12 |

### 4.6 代码检查/修复

| 功能 | 说明 | 来源 |
|------|------|------|
| 请求检查 | CommandEnum.CODE_CHECK, 发送文件路径+内容 | doc 16 |
| 检查结果 | CodeCheckDto: codeFragment, errorType, errorMessage, range | doc 16 |
| 一键修复 | CodeProblemsIntentionAction (仅 IDEA) | doc 16, 72 |
| 重复代码检测 | CommandEnum.CODE_DEBUG_DUPLICATE | doc 16 |
| Gutter 图标 | CheckGutterIconRenderer | doc 16 |
| Problems 面板 | ProblemsView.ToolWindow.TreePopup 集成 (仅 IDEA) | doc 16, 72 |

### 4.7 Git 评审

| 功能 | CommandEnum | 说明 | 来源 |
|------|-------------|------|------|
| 代码评审 | GIT_REVIEW | 流式返回评审意见 | doc 14 |
| Diff 获取 | GIT_DIFF | 获取文件差异 | doc 14 |
| Commit Message | GIT_COMMIT_MESSAGE | 生成提交信息 | doc 14 |
| 仓库状态 | GIT_REPOSITORY_STATUS | 检查 Git 仓库 | doc 14 |
| 仓库授权 | GIT_REPO_AUTHORIZE | Git 仓库授权 | doc 14 |
| Token 保存 | GIT_SAVE_TOKEN | 保存 Git Token | doc 14 |
| 知识库状态 | GIT_CODE_KNOWLEDGE_REPO_STATUS | 代码知识库索引状态 | doc 14 |
| 知识库索引 | GIT_CODE_KNOWLEDGE_RE_INDEX | 触发重新索引 | doc 14 |

### 4.8 codeVector/RAG 语义搜索

```
+=============================================================================+
|                    iFlyCode codeVector/RAG 完整工作流                        |
+=============================================================================+

[IDE Plugin - Java Side]              [Agent Process - Node.js Side]         [Cloud RAG Server]

1. 用户交互触发                        3. WebSocket 消息路由                   5. RAG 后端服务
   WebView UI Actions:                   SocketMessageHandleListener            ragserver APIs:
   - CODE_SEARCH_REQUEST                 - handleAgentAction()                  - /code/search
   - GIT_CODE_KNOWLEDGE_RE_INDEX         - GIT_SEARCH -> CodeSearchService      - /code/getUserRepos
   - GIT_AUTHORIZE                        - GIT_USER_REPOS -> getGitRepos        - /code/getLangs
                                          - GIT_LANG_LIST -> gitLangList

2. 代码补全/审查触发                    4. Chat 知识增强搜索                    6. 本地文件索引
   CodeSearchService:                     KnowledgeExpress.create()              RetrievalAugmented:
   - sendCodeSearchRequest()              -> _collectInfoKnowledgeCode           - analysisFile()
   - sendCodeRepoRequest()                -> _collectInfoKnowledgeDoc            - Tree-sitter AST 解析
   - sendCodeLangRequest()                -> _collectInfoWebSearch               - structure 字段构造
                                          -> _collectInfoDatabase                - chunk(fileResult, 50)
                                                                               -> ragBatchLoadApi()

8. 代码补全上下文组装                    9. 相似代码检索
   getStructure(file)                     SimilarCodeCache:
     -> Tree-sitter AST                     - getSimilarCodes()
     -> structure 字段                        -> Jaccard similarity
   getImportStructures(file)                 -> LRU Cache (max=10, 30s)
     -> import 文件结构
   slidingCut() 分配:
     prefix: 38%
     suffix: 12%
     structure: 18%
     similar: 32%
```

> 来源: doc 71

---

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
function getIsDevMode(g, A, S) {
    if (env_1.default.isDev || g === 9527) return true;
    const v = `${A}-${S}-002230`
        .split("")
        .reduce(((d, E) => d + E.charCodeAt(0)), 0) % 100;
    return v == g;
}
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
private X509TrustManager xE() {
    return new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
            // 空实现 — 不验证客户端证书
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
            // 空实现 — 不验证服务端证书
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0]; // 返回空数组
        }
    };
}
```

> 来源: doc 74

### 5.5 WebSocket 无认证

- 连接 URL: `ws://127.0.0.1:{动态端口}/ws/idea`
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
|  data                       |     |  data: {                    |
+=============================+     |    ended: boolean,          |
                                    |    text: string,            |
                                    |    showKeyMapTipFlag: bool  |
                                    |  }                          |
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
  │     └── {line, character}
  ├── tipinfo: TipInfoDto
  │     └── {user, platform, isShowOperateGuide}
  └── intelligent: JsonArray
        └── [{type, value}]

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