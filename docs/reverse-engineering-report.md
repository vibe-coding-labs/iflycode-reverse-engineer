# iFlyCode 3.4.2-222 逆向分析报告

> **🔔 重要通知：该产品（星火飞码）已停止运营，本报告仅供技术学习参考。**
>
> 免责声明：本报告所有内容仅供学习研究，版权归原厂商所有。如涉及侵权，请联系我们删除。

## 1. 基本信息

| 项目 | 值 |
|------|-----|
| 插件名称 | iFlyCode (星火飞码) |
| 版本 | 3.4.2-222 |
| 插件ID | `com.iflytek` |
| 厂商 | 安徽卓见科技有限公司 (Anhui Zhuojian Technology Co., Ltd) |
| 底层模型 | 讯飞星火大模型 |
| IDE 兼容性 | JetBrains IDE 2020.3+ / Android Studio |
| 主包 | `instrumented-iFlyCode-3.4.2-222.jar` (162MB) |
| 类文件数 | 574 个 |
| 包结构 | `com.aicode.*` |

## 2. 整体架构

```
┌──────────────────────────────────────────────────────────┐
│                    JetBrains IDE                          │
│                                                          │
│  ┌─────────────┐    JCEF WebView    ┌─────────────────┐  │
│  │  Plugin UI   │◄──────────────►  │  WebView Panel   │  │
│  │ (Editor/等)  │   JS Bridge      │  (JCEF Browser)  │  │
│  └──────┬───────┘                   └────────┬────────┘  │
│         │                                    │           │
│         │  Java 调用                         │ JS→Java    │
│         ▼                                    ▼           │
│  ┌──────────────────────────────────────────────────┐    │
│  │              Plugin Core (Java/Kotlin)            │    │
│  │  ┌─────────────────┐  ┌───────────────────────┐  │    │
│  │  │ Service 层       │  │ DTO/Model 层          │  │    │
│  │  │ - ChatService    │  │ - MessageDto          │  │    │
│  │  │ - UserService    │  │ - ResponseDto         │  │    │
│  │  │ - CodeComplete   │  │ - ResponseStreamDto   │  │    │
│  │  │ - SqlService     │  │ - CodeTipRequestDto   │  │    │
│  │  │ - GitReview      │  │ - LoginInfo           │  │    │
│  │  │ - CodeSearch     │  │ - SysUrlDto           │  │    │
│  │  └────────┬────────┘  └───────────────────────┘  │    │
│  │           │                                        │    │
│  │           ▼                                        │    │
│  │  ┌─────────────────────────────┐                  │    │
│  │  │  PluginWebsocketClient      │                  │    │
│  │  │  (OkHttp WebSocket)         │                  │    │
│  │  └────────────┬────────────────┘                  │    │
│  └───────────────┼───────────────────────────────────┘    │
│                  │ WebSocket                               │
│                  │ ws://127.0.0.1:&#123;动态端口&#125;/ws/idea        │
│                  ▼                                        │
│  ┌──────────────────────────────────────────────────┐    │
│  │        Local Agent (Node.js 进程)                 │    │
│  │  - 平台特定二进制 (Linux/Win/Mac Intel/ARM)        │    │
│  │  - 作为子进程启动                                   │    │
│  │  - 负责与远程服务器通信                              │    │
│  └───────────────┬──────────────────────────────────┘    │
│                  │ HTTPS (推测)                            │
│                  ▼                                        │
│        ┌─────────────────────┐                            │
│        │   iFlyCode 云端服务   │                            │
│        │  portal.example.com  │                            │
│        └─────────────────────┘                            │
└──────────────────────────────────────────────────────────┘
```

## 3. 通信机制详解

### 3.1 三层通信架构

iFlyCode 采用**三层通信架构**：

1. **IDE 插件 ↔ 本地 Agent**：WebSocket (`ws://127.0.0.1:&#123;port&#125;/ws/idea`)
2. **本地 Agent ↔ 远程服务器**：由 Node.js Agent 处理（协议细节在 Agent 二进制中，未包含在此 jar 中）
3. **IDE 插件内部**：JCEF WebView ↔ Java 层（JavaScript Bridge）

### 3.2 WebSocket 通信 (插件 → Agent)

#### 3.2.1 连接建立

**文件**: `PluginWebsocketClient.java:302-326`

```java
public void createWebSocketConnect(WebSocketListener listener, String port, Span span) throws Exception &#123;
    String url = "ws://127.0.0.1:" + port + "/ws/idea";
    // 可选: 添加 traceparent header 用于 OpenTelemetry 链路追踪
    this.request = new Request.Builder()
        .header("traceparent", traceparentValue)  // W3C Trace Context
        .url(url)
        .build();
    this.newWebSocket(listener);
&#125;
```

- **URL**: `ws://127.0.0.1:&#123;动态端口&#125;/ws/idea`
- **端口获取**: Agent 进程启动后通过 stdout 输出端口号，插件通过正则匹配解析
- **Header**: 支持 W3C `traceparent` header（OpenTelemetry 链路追踪）
- **Client**: OkHttp 4.12.0，超时时间均为 60 秒

#### 3.2.2 消息格式

**请求** — `MessageDto` (JSON):

```json
&#123;
  "id": "uuid-string",
  "command": "user_login",
  "stream": true,
  "timeStamp": 1713744000000,
  "traceparent": "00-xxx-xxx-01",
  "data": &#123; ... &#125;,
  "path": "/path/to/file.java",
  "lang": "java",
  "content": "...",
  "sessionId": "...",
  "modelCode": "...",
  "permissionCode": "...",
  "language": "java",
  "range": [...],
  "knowledge": &#123;...&#125;,
  "intelligent": [...],
  "relatedFiles": [...],
  "tipinfo": &#123;...&#125;,
  "requestion": "...",
  "md5": "...",
  "docChangeCount": 0
&#125;
```

**响应** — `ResponseDto`:

```json
&#123;
  "id": "uuid-string",
  "code": "0",
  "msg": "success",
  "command": "...",
  "data": &#123; ... &#125;
&#125;
```

**流式响应** — `ResponseStreamDto`:

```json
&#123;
  "id": "uuid-string",
  "code": "0",
  "msg": "success",
  "data": &#123;
    "ended": false,
    "text": "部分生成的代码或文本...",
    "showKeyMapTipFlag": false
  &#125;
&#125;
```

#### 3.2.3 命令体系

共 **109 个命令**，按模块 (`AgentModuleEnum`) 分组：

| 模块 | 命令示例 | 说明 |
|------|---------|------|
| **INIT** | `init`, `action_init`, `update`, `error`, `general_setting`, `repo_status` | 初始化与配置 |
| **LOGIN** | `user_login`, `user_logout`, `user_version`, `user_model_list`, `user_permission`, `login_info` | 用户认证 |
| **CODE_COMPLETE** | `code_complete`, `action_sync_document_list`, `log_accept`, `log_reject`, `log_display` | 代码补全 |
| **CHAT** | `talk_ask`, `talk_intelligent`, `talk_history`, `talk_list`, `talk_delete`, `talk_clear`, `talk_resend`, `talk_knowledge` | 智能对话 |
| **CHAT** | `code_explain`, `code_optimize`, `code_comment`, `code_inline_comment`, `code_split`, `code_debug`, `code_help` | 代码操作 |
| **CHAT** | `code_demand_analysis`, `code_demand_splitting`, `code_demand_test`, `code_generate_test_case`, `code_fault_analysis` | 需求/测试分析 |
| **SQL_CHAT** | `sql_generate`, `sql_optimize`, `sql_source_list`, `sql_source_types`, `sql_table_list`, `sql_test_connect` | SQL 功能 |
| **CODE_SEARCH** | `git_search`, `git_lang_list`, `git_user_repos` | 代码搜索 |
| **GIT_REVIEW** | `git_review`, `git_diff`, `git_commit_message` | Git 评审 |
| **CODE_CHECK** | `code_check`, `code_debug_duplicate` | 代码检查 |
| **UNIT_TEST** | `code_test`, `code_test_case`, `code_test_analysis`, `code_test_code`, `code_test_save`, `test_make_case`, `test_make_code` | 单元测试 |
| **BATCH_UNIT_TEST** | `code_batch_unit_test_create/list/download/cancel/delete` | 批量单测 |
| **INLINE_CHAT** | `dialog_edit`, `dialog_abort`, `dialog_reject`, `dialog_accept`, `dialog_diff`, `inlinechat_get_func_range`, `inlinechat_category`, `inlinechat_direct` | 内联聊天 |
| **COMMON** | `log_evaluation`, `log_feedback`, `log_operate`, `log_tip_setting`, `action_abort`, `log_accept_count` | 通用日志 |
| **SERVER_RESOURCE** | `server_resource` | 服务器资源状态 |

**注意**: 命令字符串经过混淆编码（通过 `AICodeStringUtil.H()`, `IdeAction.H()` 等方法），运行时解码为实际命令值。

### 3.3 Agent 进程管理

#### 3.3.1 Agent 启动

**文件**: `PluginAgentCommandLine.java`, `PluginAgentProcessServiceImpl.java`

1. **二进制文件查找**: 根据平台和架构选择对应的 Node.js 二进制文件
   - Linux: `x86_64_linux_node`
   - Windows: `x86_64_windows_node.exe` / `x86_64_windows7_node.exe`
   - macOS Intel: `x86_64_darwin_node`
   - macOS ARM: `x86_64_darwin_arm_node`

2. **启动命令**:
   ```
   &#123;agent_binary&#125; &#123;config_path&#125; &#123;node_exe_path&#125; &#123;os_arch&#125;
   ```
   环境变量设置: `ELECTRON_RUN_AS_NODE=""`

3. **端口发现**: Agent 进程启动后通过 stdout 输出端口，插件通过正则 `d+x+N&2` 解析

4. **重连机制** (`RestartableAgentProcessService`):
   - 最大重启次数: 3 次
   - 重连间隔: 3 秒
   - WebSocket 断开后自动重连

#### 3.3.2 心跳检测

**文件**: `HeartBeatCheckRunner.java`

- **心跳间隔**: 30 秒
- **心跳命令**: `USER_VERSION`
- **超时判定**: 10 秒未收到响应视为超时
- **连续超时阈值**: 2 次超时触发 Agent 重启
- **异常处理**: 清理所有挂起请求，重启 Agent 进程

### 3.4 WebView 通信

**文件**: `WebViewWindowPanel.java`

#### 3.4.1 WebView → Plugin

使用 JCEF 的 `JBCefJSQuery` 机制：

```javascript
// WebView 中的 JavaScript 调用
window.myObject.sendMessage(JSON.stringify(&#123;
  type: "xxx",
  ...
&#125;));
```

Plugin 端注册 handler 接收消息，根据 `WebViewDataTypeEnum` 分发到不同 Service 处理：
- `CHAT` → `ChatService`
- `LOGIN` → `UserService`
- `SQL_CHAT` → `SqlService`
- `CODE_SEARCH` → `CodeSearchService`
- `UNIT_TEST` → `UnitTestService`
- `CODE_CHECK` → `CodeCheckService`
- `GIT_VIEW` → `GitReviewService`
- `SETTING` / `COMMON` → `CommonService`

#### 3.4.2 Plugin → WebView

通过执行 JavaScript 函数 `receiveData()`:

```java
browser.getCefBrowser().executeJavaScript(
    "receiveData(" + jsonData + ");",
    browser.getCefBrowser().getURL(), 0
);
```

数据类型通过 `WebViewDataTypeEnum` 标识，包括:
- `LOGIN_SHOW_FRESH` — 登录刷新状态
- `COMMON_SHOW_MESSAGE_IN_WEB` — 通用消息
- `COMMON_OPEN_PAGE` — 页面导航
- `SETTING_RECEIVE_REPO_STATUS` — 仓库状态
- 等

#### 3.4.3 本地资源加载

通过 `CustomSchemeHandlerFactory` 注册自定义 Scheme:
- Scheme 名称: 混淆后的值
- Domain: 混淆后的值
- 使用 `CefApp.getInstance().registerSchemeHandlerFactory()` 注册

## 4. 用户认证流程

```
1. 插件启动 → 启动 Agent 进程 → 建立 WebSocket 连接
2. WebSocket 连接成功 (onOpen) → 发送 ACTION_INIT 命令
   - 携带: pluginVersion, clientName, apiVersion, projectPath
3. 发送 USER_LOGIN 命令 (data: &#123;count: 1&#125;)
4. Agent 返回登录信息 (SysUrlDto):
   - loginUrl        — 登录页面 URL
   - feedbackUrl     — 反馈 URL
   - maintainRepoUrl — 仓库管理 URL
   - codeSearchServerUrl — 代码搜索服务 URL
   - officialWebsiteUrl  — 官网 URL
   - codeKnowledgeWebUrl — 代码知识库 URL
   - userCenterWebUrl    — 用户中心 URL
5. WebView 加载登录页面
6. 用户扫码/登录成功 → Agent 推送登录成功消息
7. 插件保存用户信息 (userId, userName, enterpriseId 等)
```

**设置持久化**: 保存在 `AICodeSettingsPlugin.xml` 文件中，包含:
- 用户信息 (userId, userName)
- 企业信息 (enterpriseId, enterpriseName)
- 服务器 URL (loginUrl, feedbackUrl 等)
- 功能配置 (autoTrigger, tipType, testFramework 等)
- 模型列表 (modelList, modelCode)

## 5. 核心功能通信流

### 5.1 代码补全

```
Editor 事件触发 → RequestTipServiceImpl 构建请求
  → PluginWebsocketClient.sendWsMessageForCode(MessageDto)
  → WebSocket 发送 code_complete 命令
  → Agent 返回 ResponseStreamDto (流式)
  → CodeCompleteService 处理流式响应
  → 渲染到 Editor Inlay
```

### 5.2 智能对话

```
WebView 用户输入 → JS Bridge → ChatService
  → 构造 MessageDto (talk_ask / talk_intelligent)
  → WebSocket 发送
  → Agent 返回流式文本
  → send2Web() 推送到 WebView
```

### 5.3 单元测试

```
用户触发 → UnitTestService.handleAction()
  → 发送 code_test / code_test_case 等命令
  → 模板模式下走 TemplateRequestService
  → 响应中包含测试代码
  → CreateTestFileTask 写入文件
```

## 6. 代码混淆

插件使用了多种混淆技术:

1. **字符串编码**: 所有字符串常量通过工具方法编码
   - `RequestResultList.H("...")`
   - `NewFileUtils.H("...")`
   - `AICodeStringUtil.H("...")`
   - `IdeAction.H("...")`
   - `FileService.H("...")`
   - `GitReviewService.H("...")`
   - 等多个不同的编码器

2. **字段名混淆**: 使用 Java 关键字作为字段名
   - `byte` → Logger 实例
   - `enum` → Logger 实例
   - `float` → 同步对象
   - `final` → 端口号
   - `try` → Handler 实例
   - `case` → ModuleEnum

3. **控制流混淆**: 反编译后出现大量不可读的变量赋值和跳转

4. **内部类命名**: 使用 `$01`, `$pa`, `$va` 等非标准内部类名

5. **包名混淆**: `Q/` 包下的类 (`Q.q`, `Q.sa`, `Q.ua`)

## 7. 依赖库

| 库 | 版本 | 用途 |
|----|------|------|
| OkHttp | 4.12.0 | HTTP/WebSocket 客户端 |
| OkHttp SSE | 4.10.0 | Server-Sent Events |
| Okio | 3.6.0 (JVM) | IO 库 |
| Gson | (内置) | JSON 序列化 |
| Hutool | 5.8.12 | 工具类 (IdUtil, MapUtil) |
| Apache Commons Lang | 3.12.0 | 字符串/对象工具 |
| Apache Commons Text | 1.10.0 | 文本处理 |
| Java Diff Utils | 4.12 | 代码差异对比 |
| OpenTelemetry | 1.36.0 | APM 链路追踪 |
| SLF4J | 2.0.7 | 日志框架 |
| Kotlin Stdlib | 1.9.10 | Kotlin 运行时 |
| Velocity | (内置部分) | 模板引擎 (单元测试) |
| JSON.org | 20080701 / 20230618 | JSON 处理 |

## 8. 关键发现与安全观察

### 8.1 通信安全

- **WebSocket 无加密**: 插件与本地 Agent 的 WebSocket 连接使用 `ws://`（明文），非 `wss://`
- **仅本地通信**: WebSocket 绑定 `127.0.0.1`，不暴露到网络
- **Agent 到云端**: 通信细节在 Node.js Agent 二进制中（不在此 jar 中），推测使用 HTTPS

### 8.2 Agent 进程

- Node.js Agent 二进制被打包在插件中
- 首次运行时从 zip 解压到本地目录
- 运行时以子进程方式启动
- 支持跨平台 (Linux/Windows/macOS, x64/ARM64)
- 具备自动重启机制（最多 3 次）

### 8.3 数据收集

- **OpenTelemetry APM**: 完整的链路追踪集成
- **遥测数据**: 用户名、插件版本、设置项、命令 ID 等通过 Span Attribute 上报
- **日志上报**: 代码补全的接受/拒绝、操作行为等通过 `LOG_*` 命令上报

### 8.4 用户数据

- 用户凭证信息存储在 IntelliJ 持久化配置 (`AICodeSettingsPlugin.xml`)
- 包含: userId, userName, enterpriseId, modelCode 等
- 代码文件路径和内容通过 WebSocket 传输给 Agent

## 9. 文件结构概览

```
com/aicode/
├── PluginStartupActivity.class          # 插件启动入口
├── agent/                               # 核心 Agent 通信层
│   ├── PluginWebsocketClient.class      # WebSocket 客户端
│   ├── PluginWebsocketListener.class    # WebSocket 监听器
│   ├── SocketMessageHandleListener.class # 消息分发处理
│   ├── SocketMessageListener.class      # 消息监听接口
│   ├── HeartBeatCheckRunner.class       # 心跳检测
│   ├── AgentCheckTimer.class            # Agent 检查定时器
│   ├── PluginAgentCommandLine.class     # Agent 命令行构建
│   ├── PluginAgentProcessHandler.class  # Agent 进程管理
│   ├── dto/                             # 数据传输对象
│   │   ├── MessageDto.class             # 消息主体
│   │   ├── ResponseDto.class            # 响应主体
│   │   ├── ResponseStreamDto.class      # 流式响应
│   │   ├── LoginInfo.class              # 登录信息
│   │   ├── SysUrlDto.class              # 系统 URL 配置
│   │   ├── ConnectConfigDto.class       # 连接配置
│   │   ├── UserInfoDto.class            # 用户信息
│   │   ├── SettingsDto.class            # 设置
│   │   ├── EnterpriseDto.class          # 企业信息
│   │   ├── CodeModel.class              # 代码模型
│   │   └── ...                          # 其他 DTO
│   ├── enums/                           # 枚举定义
│   │   ├── CommandEnum.class            # 109 个命令枚举
│   │   ├── AgentModuleEnum.class        # 模块枚举
│   │   ├── ModuleEnum.class             # 功能模块
│   │   ├── PageEnum.class               # 页面枚举
│   │   └── PermissionEnum.class         # 权限枚举
│   └── service/                         # Agent 服务层
│       ├── ChatService.class            # 对话服务
│       ├── UserService.class            # 用户服务
│       ├── CodeCompleteService.class    # 代码补全
│       ├── SqlService.class             # SQL 服务
│       ├── CodeSearchService.class      # 代码搜索
│       ├── GitReviewService.class       # Git 评审
│       ├── CodeCheckService.class       # 代码检查
│       ├── InlineChatCommandService.class # 内联聊天
│       ├── CommonService.class          # 通用服务
│       └── ...                          # 其他服务
├── view/                                # UI 层
│   ├── WebViewWindowPanel.class         # WebView 面板
│   ├── PluginToolWindowPanel.class      # 工具窗口
│   ├── CustomResourceHandler.class      # 资源处理
│   └── CustomSchemeHandlerFactory.class # Scheme 工厂
├── settings/                            # 设置管理
│   ├── AICodeSettingsState.class        # 核心设置
│   └── AICodeRequestSettings.class      # 请求设置
├── listener/                            # 事件监听器
├── action/                              # 用户操作
├── inline/                              # 内联聊天
├── template/                            # 单测模板
├── test/                                # 单元测试
├── apm/                                 # APM 监控
├── service/                             # 编辑器服务
└── util/                                # 工具类
```

## 10. 总结

iFlyCode 采用**本地 Agent 代理架构**：

1. **JetBrains 插件** 作为 UI 层，负责编辑器集成和用户交互
2. **本地 Node.js Agent** 作为通信代理，运行在 `127.0.0.1` 上
3. **远程云端服务** 提供实际的 AI 推理能力

插件与 Agent 之间通过 **WebSocket** 通信，使用 JSON 格式的 `MessageDto` / `ResponseDto` 进行数据交换。所有 109 个命令按功能模块分组，涵盖代码补全、智能对话、代码检查、单元测试、SQL 生成、Git 评审等全部功能。

这种架构的一个关键特点是：**敏感的远程通信逻辑被封装在 Node.js Agent 二进制中**，而非 Java 插件中。要完整理解 Agent 与云端的通信协议，还需要对 Agent 二进制进行逆向分析。
