## 1. 执行摘要

### 1.1 项目概述

iFlyCode 3.4.2-222 是科大讯飞推出的 AI 编程助手，以 IntelliJ IDEA 插件为主要载体，同时支持 VS Code 和 Eclipse。系统采用三层通信架构（IDE 插件 / 本地 Agent / 云端服务），通过 WebSocket 实现插件与 Agent 的双向通信，Agent 再通过 HTTPS 转发至讯飞星火大模型。插件端由 Java/Kotlin 编写（566 个 .class 文件，65 个包），Agent 端为 Node.js webpack bundle（3.6 MB），WebView 前端基于 Vue.js 2.7 + Pinia + Element UI。系统提供代码补全、智能问答、内联聊天、单元测试生成、SQL 生成/优化、代码检查/修复、Git 评审、语义代码搜索等 30 项功能，其中 IDEA 平台功能最完整（30/30），VS Code 次之（25/30），Eclipse 最受限（21/30）。

### 1.2 关键发现

| # | 发现 | 严重程度 | 来源 |
|---|------|---------|------|
| 1 | SSL 证书验证完全禁用 — APM 遥测通道使用 no-op X509TrustManager，允许中间人攻击 | 高 | doc 74 |
| 2 | debugCode=9527 硬编码后门 — 修改 config.json 即可启用开发模式，绕过安全检查 | 高 | doc 66, 74 |
| 3 | WebSocket 无认证 — 本机任意进程可连接 ws://127.0.0.1:&#123;port&#125;/ws/idea 伪造消息 | 高 | doc 04, 74 |
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
|                     | ws://127.0.0.1:&#123;动态端口&#125;/ws/idea              |
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
