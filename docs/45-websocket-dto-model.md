# iFlyCode WebSocket 通信与 DTO 数据模型分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-11

## 1. WebSocket 通信架构

### 1.1 核心类

#### PluginWebsocketClient (296 strings)

**路径**: `com/aicode/agent/PluginWebsocketClient`
**职责**: WebSocket 客户端 — 与 Agent 进程通信的核心

**关键数据结构**:
- `AGENT_CLIENT_ID` — 客户端 ID
- `AGENT_WEBSOCKETS` — `ConcurrentNavigableMap<String, WebSocket>` — 项目 → WebSocket 连接映射
- `URI_LINK_PREFIX` — URI 链接前缀
- `client` — `OkHttpClient` — OkHttp WebSocket 客户端

**关键方法**:
- `sendWsMessage(CommandEnum, Object, Project)` — 发送 WebSocket 消息
- `getClientName()` — 获取客户端名称（含 IDE 版本信息）
- `newWebSocket(WebSocketListener)` — 创建新的 WebSocket 连接

**OpenTelemetry 集成**:
- 发送消息时创建 APM Span
- 使用 `TextMapSetter` 传播 traceparent 到 WebSocket 消息
- 记录 `COMMAND_ID`、`SETTING_MESSAGE_TYPE`、`SETTING_TRIGGER_TIME_DELAY` 等属性

#### PluginWebsocketListener (185 strings)

**路径**: `com/aicode/agent/PluginWebsocketListener`
**父类**: `okhttp3.WebSocketListener`
**职责**: WebSocket 连接生命周期监听

**关键方法**:
- `onOpen(WebSocket, Response)` — 连接打开
  - 重置 `restartAttempts` 计数
  - 调用 `wsInit()` 初始化 WebSocket
  - 调用 `send2Web()` 推送状态到 WebView
- `onMessage(WebSocket, String)` — 收到消息
  - 调用 `SocketMessageHandleListener.handleSocketMessage()`
- `onFailure(WebSocket, Throwable, Response)` — 连接失败
  - 调用 `OpenTelemetryUtil` 记录错误 Span

#### SocketMessageHandleListener (341 strings)

**路径**: `com/aicode/agent/SocketMessageHandleListener`
**实现**: `SocketMessageListener`
**职责**: WebSocket 消息处理核心 — 分发所有 Agent 响应

**关键方法**:
- `handleSocketMessage(String, Project)` — 处理原始消息字符串
- `sendMessage2webView(Project, Object)` — 推送消息到 WebView
- `send2Web(Project, Object)` — 发送数据到 WebView
- `changeServerStatus()` — 变更服务状态

**消息分发逻辑**:
根据 `CommandEnum` 类型将消息分发到不同的服务:
- `CodeCheckService` — 代码检查
- `GitReviewService` — Git 评审
- `CodeCompleteService` — 代码补全
- `InlineChatCommandService` — 内联聊天
- `CommonService` — 通用消息

#### SocketMessageListener (21 strings)

**路径**: `com/aicode/agent/SocketMessageListener`
**实现**: IntelliJ `Topic` 消息总线接口
**职责**: 消息监听接口

**注册**: 通过 `com.intellij.util.messages.Topic` 注册到 IntelliJ 消息总线

#### AgentCheckTimer (183 strings)

**路径**: `com/aicode/agent/AgentCheckTimer`
**职责**: Agent 进程健康检查定时器

**关键数据结构**:
- `AGENT_CHECK_MAP` — 项目 → 检查状态映射
- `AGENT_CLIENT_MAP` — 项目 → WebSocket 客户端映射
- `AGENT_WEBSOCKETS` — 项目 → WebSocket 连接映射
- `timeOutTimer` — 超时定时器

**关键方法**:
- `scheduleAtFixedRate(TimerTask, delay, period)` — 定期检查
- `send2Web(Project, Object)` — 推送检查结果到 WebView

#### HeartBeatCheckRunner (223 strings)

**路径**: `com/aicode/agent/HeartBeatCheckRunner`
**职责**: 心跳检测运行器

**关键方法**:
- `checkAgent(Project)` — 检查 Agent 是否存活
- `onRestartException()` — 重启异常处理

**重启触发**:
- `RestartEnum.HEART_BEAT_ERROR` — 心跳检测失败触发重启

**关键字符串常量**:
- `[HeartBeatCheckRunner] end:` — 心跳检测结束日志

## 2. DTO 数据模型

### 2.1 MessageDto (205 strings) — 核心消息 DTO

**路径**: `com/aicode/agent/dto/MessageDto`
**职责**: WebSocket 消息数据传输对象

**字段**:

| 字段 | 类型 | 说明 |
|------|------|------|
| id | String | 消息唯一 ID |
| stream | boolean | 是否流式消息 |
| command | CommandEnum | 命令类型 |
| sessionId | String | 会话 ID |
| modelCode | String | 模型代码 |
| permissionCode | String | 权限代码 |
| data | String | 消息数据（JSON 字符串） |
| range | List&lt;RangeDTO&gt; | 代码范围列表 |
| chatTest | String | 聊天测试标识 |
| pid | String | 进程 ID |
| taskId | String | 任务 ID |
| requestCaseCodeDto | RequestCaseCodeDto | 请求用例代码 DTO |
| text | String | 文本内容 |
| tipinfo | TipInfoDto | 提示信息 |
| requestion | String | 请求问题 |
| streamStep | String | 流式步骤 |
| inlineChatVersion | String | 内联聊天版本 |

### 2.2 ResponseDto (29 strings) — 响应 DTO

**路径**: `com/aicode/agent/dto/ResponseDto`
**字段**: id, code, data

### 2.3 ResponseStreamDto (33 strings) — 流式响应 DTO

**路径**: `com/aicode/agent/dto/ResponseStreamDto`
**字段**: id, code, data (ResponseData)

**ResponseData** 内部类:
- 包含流式响应的具体数据
- `ended` 标志标识流式传输结束

### 2.4 UserInfoDto (61 strings) — 用户信息 DTO

**路径**: `com/aicode/agent/dto/UserInfoDto`
**字段**:

| 字段 | 类型 | 说明 |
|------|------|------|
| clientId | String | 客户端 ID |
| user | String | 用户名 |
| token | String | 认证 Token |
| codeModelDtoList | List&lt;CodeModel&gt; | 可用模型列表 |
| enterpriseDto | EnterpriseDto | 企业信息 |
| tokenPath | String | Token 路径 |
| sysUrls | SysUrlDto | 系统 URLs |
| packageCode | String | 包代码 |
| reLogin | boolean | 是否需要重新登录 |

### 2.5 SettingsDto (98 strings) — 设置 DTO

**路径**: `com/aicode/agent/dto/SettingsDto`
**职责**: 从 `AICodeSettingsState` 转换的设置 DTO，发送到 Agent

**字段**:

| 字段 | 类型 | 说明 |
|------|------|------|
| generateCodeMode | String | 代码生成模式 |
| codeCompleteDisableLang | String | 禁用补全的语言 |
| sendMessageType | String | 发送消息类型 |
| lineToolsType | String | 行工具类型 |
| lineToolsPermissionDocComments | boolean | 文档注释权限 |
| lineToolsPermissionLineComments | boolean | 行注释权限 |
| lineToolsPermissionComments | boolean | 注释权限 |
| lineToolsPermissionFunctionSplit | boolean | 函数拆分权限 |
| lineToolsPermissionCodeOptimization | boolean | 代码优化权限 |
| lineToolsPermissionUnitTesting | boolean | 单测权限 |
| openFunctionSplit | boolean | 开启函数拆分 |
| openCodeOptimization | boolean | 开启代码优化 |
| openInlineChat | boolean | 开启内联聊天 |
| openCodeEnhance | boolean | 开启代码增强 |
| inlineCompletionInputStyle | String | 内联补全输入风格 |

### 2.6 CodeModel (39 strings) — 模型 DTO

**路径**: `com/aicode/agent/dto/CodeModel`
**字段**: modelId, value, modelCode, modelName, checked, originalModelName, tokenExhausted

**关键发现**: `tokenExhausted` 字段标识模型 Token 是否耗尽，用于模型切换提示。

### 2.7 FunctionModelInfo (37 strings) — 功能模型信息

**路径**: `com/aicode/agent/dto/FunctionModelInfo`
**字段**: permissionCode, value, codeModelList (List&lt;CodeModel&gt;)

**职责**: 每个功能（代码补全、聊天等）有独立的权限代码和可用模型列表。

### 2.8 CodeTipRequestDto (60 strings) — 补全请求 DTO

**路径**: `com/aicode/agent/dto/CodeTipRequestDto`
**字段**:

| 字段 | 类型 | 说明 |
|------|------|------|
| request | EditorRequestService | 编辑器请求服务 |
| codeSubScriber | Flow.Subscriber<List&lt;CodeInlayList&gt;> | 代码补全订阅者 |
| lastReplacementText | String | 最后替换文本 |
| firstAgentDuration | Long | 首字耗时 |

**关键发现**: 使用 Java 9 的 `Flow.Subscriber` API（Reactive Streams），实现补全结果的异步订阅。

### 2.9 其他 DTO

| DTO | 字段 | 说明 |
|-----|------|------|
| ConnectConfigDto | id, user, database | SQL 连接配置 |
| DatabaseDto | id, formData, databases, errMsg | 数据库信息 |
| EnterpriseDto | enterpriseId, enterpriseName, userId | 企业信息 |
| SysUrlDto | feedbackUrl, maintainRepoUrl, codeSearchServerUrl, officialWebsiteUrl, codeKnowledgeWebUrl, userCenterWebUrl | 系统 URLs |
| TipInfoDto | user, isShowOperateGuide | 提示信息 |
| LoginInfo | (空) | 登录信息（字段被混淆） |
| WebRequestDto&lt;T&gt; | type, value | 通用 Web 请求 DTO（泛型） |
| CodeCheckDto | codeFragment, errorType, errorMessage, codeInfo | 代码检查数据 |
| CodeCheckFixDto | type, value (ValueDTO) | 代码检查修复 |
| CodeCheckListDto | type, value (ValueDTO) | 代码检查列表 |
| CodeCheckOriginDto | errList (List&lt;ErrListDTO&gt;) | 代码检查原始数据 |
| CodeRepoInfoDto | content, currentPage, pageSize, totalPage | 代码仓库信息（含分页） |
| CodeSearchInfoDto | content, type, count, currentPage, pageSize, totalPage | 代码搜索结果（含分页） |

## 3. WebSocket 通信流程

```
┌──────────────────────────────────────────────────────────────┐
│                    IntelliJ Plugin                            │
│                                                              │
│  PluginWebsocketClient                                       │
│    ├── AGENT_WEBSOCKETS: ConcurrentNavigableMap              │
│    │     (project → WebSocket)                               │
│    ├── OkHttpClient → WebSocket 连接                         │
│    └── sendWsMessage() → 发送 MessageDto                     │
│                                                              │
│  PluginWebsocketListener (extends WebSocketListener)          │
│    ├── onOpen() → 初始化 + 推送状态到 WebView                 │
│    ├── onMessage() → SocketMessageHandleListener              │
│    │     └── 按 CommandEnum 分发到各 Service                  │
│    └── onFailure() → APM 记录 + 重连                         │
│                                                              │
│  AgentCheckTimer                                             │
│    ├── 定期检查 WebSocket 连接存活                             │
│    └── 检查所有打开项目的 Agent 状态                           │
│                                                              │
│  HeartBeatCheckRunner                                        │
│    ├── 定期发送心跳包                                         │
│    └── 失败时触发 RestartEnum.HEART_BEAT_ERROR 重启           │
└──────────────────────────────────────────────────────────────┘
                           │
                    WebSocket (OkHttp)
                           │
┌──────────────────────────────────────────────────────────────┐
│                    Node.js Agent                              │
│  Express HTTP Server + WebSocket Server                      │
│    ├── 接收 MessageDto (command + data)                      │
│    ├── 处理请求 → AI 模型调用                                 │
│    └── 返回 ResponseDto / ResponseStreamDto                  │
└──────────────────────────────────────────────────────────────┘
```

## 4. 关键发现

1. **OkHttp WebSocket**: 使用 OkHttp 的 WebSocket 客户端与 Agent 通信，而非 Java-WebSocket 库。

2. **ConcurrentNavigableMap**: WebSocket 连接使用并发安全映射存储，支持多项目并行。

3. **消息总线**: `SocketMessageListener` 通过 IntelliJ 的 `Topic` 消息总线注册，允许多个组件订阅 WebSocket 消息。

4. **APM 传播**: WebSocket 消息携带 OpenTelemetry traceparent，实现跨进程链路追踪。

5. **流式响应**: `ResponseStreamDto` 支持流式传输，`ended` 标志标识传输结束。

6. **权限控制**: `FunctionModelInfo.permissionCode` 控制每个功能的可用模型，`CodeModel.tokenExhausted` 标识 Token 耗尽。

7. **Reactive Streams**: `CodeTipRequestDto` 使用 Java 9 `Flow.Subscriber` API 实现补全结果的异步订阅。

8. **分页支持**: `CodeRepoInfoDto` 和 `CodeSearchInfoDto` 都包含分页信息（currentPage, pageSize, totalPage）。

9. **设置推送**: `SettingsDto` 从 `AICodeSettingsState` 转换后推送到 Agent，包含 7 个行工具权限开关。

10. **企业信息**: `EnterpriseDto` 和 `UserInfoDto.enterpriseDto` 传递企业 ID/名称，用于私有化部署场景。