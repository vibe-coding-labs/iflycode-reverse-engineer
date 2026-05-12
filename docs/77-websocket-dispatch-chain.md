# iFlyCode WebSocket 消息分发链完整分析

> 版本: iFlyCode 3.4.2-222 | 分析日期: 2026-05-13

## 1. 架构概览

iFlyCode 的 WebSocket 消息系统是 IDE 插件与远端 Agent 之间的核心通信通道。消息从 WebSocket 连接接收，经过命令枚举路由，分发到对应的 Service 处理，处理结果通过流式响应回传到 WebView UI。

```
┌─────────────────────────────────────────────────────────────────────────┐
│                          iFlyCode Plugin (IDE)                          │
│                                                                         │
│  ┌──────────────┐    ┌───────────────────┐    ┌──────────────────────┐ │
│  │  WebView UI  │◄──►│  JS Bridge        │◄──►│  WebViewWindowPanel  │ │
│  │  (Chat/Code) │    │  (postMessage)    │    │  (send2Web/callJs)   │ │
│  └──────────────┘    └───────────────────┘    └──────────┬───────────┘ │
│                                                           │             │
│                     ┌─────────────────────────────────────┘             │
│                     │                                                   │
│                     ▼                                                   │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │              SocketMessageHandleListener                         │  │
│  │  ┌────────────────────────────────────────────────────────────┐  │  │
│  │  │  onMessage(MessageDto)                                     │  │  │
│  │  │    1. 解析 command → CommandEnum                           │  │  │
│  │  │    2. switch(command) 路由到对应 Service                   │  │  │
│  │  │    3. Service 处理 → 回传 WebView                          │  │  │
│  │  └────────────────────────────────────────────────────────────┘  │  │
│  └──────────┬───────────────────────────────────────────────────────┘  │
│             │                                                          │
│             ▼                                                          │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │              PluginWebsocketClient                               │  │
│  │  - connect(url, token)                                          │  │
│  │  - sendWsMessage(MessageDto)                                    │  │
│  │  - close()                                                      │  │
│  │  - reconnect()                                                  │  │
│  └──────────┬───────────────────────────────────────────────────────┘  │
│             │                                                          │
└─────────────┼──────────────────────────────────────────────────────────┘
              │ WebSocket (wss://)
              ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                        iFlyCode Agent Server                            │
│                                                                         │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐              │
│  │ Chat LLM │  │Code Gen  │  │UnitTest  │  │CodeCheck │  ...          │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘              │
└─────────────────────────────────────────────────────────────────────────┘
```

## 2. 完整分发流程图

```
Agent Server
     │
     │ WebSocket Frame (JSON)
     ▼
PluginWebsocketListener.onMessage(String rawJson)
     │
     │ JSON.parse → MessageDto
     ▼
SocketMessageHandleListener.onMessage(MessageDto msg)
     │
     │ 提取 msg.getCommand() → CommandEnum
     ▼
┌──── switch(command) ────────────────────────────────────────────────────┐
│                                                                         │
│  ┌─ CHAT_RELATED ──────────────────────────────────────────────────┐   │
│  │  CHAT_SEND         → ChatService.chatSend()                     │   │
│  │  CHAT_STOP         → ChatService.chatStop()                     │   │
│  │  CHAT_DELETE       → ChatService.chatDelete()                   │   │
│  │  CHAT_LIST         → ChatService.chatList()                     │   │
│  │  CHAT_DETAIL       → ChatService.chatDetail()                   │   │
│  │  CHAT_TITLE_UPDATE → ChatService.chatTitleUpdate()              │   │
│  │  CHAT_FEEDBACK     → ChatService.chatFeedback()                 │   │
│  └─────────────────────────────────────────────────────────────────┘   │
│                                                                         │
│  ┌─ INLINE_CHAT ───────────────────────────────────────────────────┐   │
│  │  INLINE_CHAT_SEND    → InlineChatCommandService.inlineSend()    │   │
│  │  INLINE_CHAT_STOP    → InlineChatCommandService.inlineStop()    │   │
│  │  INLINE_CHAT_ACCEPT  → InlineChatCommandService.inlineAccept()  │   │
│  │  INLINE_CHAT_REJECT  → InlineChatCommandService.inlineReject()  │   │
│  └─────────────────────────────────────────────────────────────────┘   │
│                                                                         │
│  ┌─ CODE_COMPLETE ─────────────────────────────────────────────────┐   │
│  │  CODE_COMPLETE_REQUEST → CodeCompleteService.complete()         │   │
│  │  CODE_COMPLETE_ACCEPT  → CodeCompleteService.accept()           │   │
│  │  CODE_COMPLETE_CANCEL  → CodeCompleteService.cancel()           │   │
│  └─────────────────────────────────────────────────────────────────┘   │
│                                                                         │
│  ┌─ UNIT_TEST ─────────────────────────────────────────────────────┐   │
│  │  UNIT_TEST_GENERATE    → UnitTestService.generate()             │   │
│  │  UNIT_TEST_BATCH       → BatchUnitTestService.batchGenerate()   │   │
│  │  UNIT_TEST_RUN         → UnitTestService.run()                  │   │
│  └─────────────────────────────────────────────────────────────────┘   │
│                                                                         │
│  ┌─ CODE_CHECK ────────────────────────────────────────────────────┐   │
│  │  CODE_CHECK_REQUEST  → CodeCheckService.check()                │   │
│  │  CODE_CHECK_RESULT   → CodeCheckService.handleResult()         │   │
│  │  CODE_CHECK_FIX      → CodeCheckService.fix()                  │   │
│  └─────────────────────────────────────────────────────────────────┘   │
│                                                                         │
│  ┌─ CODE_SEARCH ───────────────────────────────────────────────────┐   │
│  │  CODE_SEARCH_REQUEST → CodeSearchService.search()              │   │
│  │  CODE_SEARCH_RESULT  → CodeSearchService.handleResult()        │   │
│  └─────────────────────────────────────────────────────────────────┘   │
│                                                                         │
│  ┌─ GIT_REVIEW ────────────────────────────────────────────────────┐   │
│  │  GIT_REVIEW_START   → GitReviewService.startReview()           │   │
│  │  GIT_REVIEW_RESULT  → GitReviewService.handleResult()          │   │
│  └─────────────────────────────────────────────────────────────────┘   │
│                                                                         │
│  ┌─ SQL ───────────────────────────────────────────────────────────┐   │
│  │  SQL_GENERATE       → SqlService.generate()                    │   │
│  │  SQL_OPTIMIZE       → SqlService.optimize()                    │   │
│  │  SQL_EXPLAIN        → SqlService.explain()                     │   │
│  └─────────────────────────────────────────────────────────────────┘   │
│                                                                         │
│  ┌─ TEMPLATE ──────────────────────────────────────────────────────┐   │
│  │  TEMPLATE_REQUEST   → TemplateRequestService.request()         │   │
│  │  TEMPLATE_RESULT    → TemplateRequestService.handleResult()    │   │
│  └─────────────────────────────────────────────────────────────────┘   │
│                                                                         │
│  ┌─ USER ──────────────────────────────────────────────────────────┐   │
│  │  USER_LOGIN         → UserService.login()                      │   │
│  │  USER_LOGOUT        → UserService.logout()                     │   │
│  │  USER_INFO          → UserService.getUserInfo()                │   │
│  │  USER_QUOTA         → UserService.getQuota()                   │   │
│  └─────────────────────────────────────────────────────────────────┘   │
│                                                                         │
│  ┌─ SYSTEM ────────────────────────────────────────────────────────┐   │
│  │  HEARTBEAT          → (内部处理，更新连接状态)                   │   │
│  │  PING               → PluginWebsocketClient.sendPong()         │   │
│  │  ERROR              → (日志记录 + 错误提示)                     │   │
│  │  CONNECTION_LOST    → PluginWebsocketClient.reconnect()        │   │
│  └─────────────────────────────────────────────────────────────────┘   │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
     │
     │ Service 处理结果 → ResponseStreamDto / ResponseDto
     ▼
WebViewWindowPanel.send2Web(WebViewDataTypeEnum, Object data)
     │
     │ JSON 序列化 → JS Bridge postMessage
     ▼
WebView UI 更新
```

## 3. 核心类详解

### 3.1 PluginWebsocketListener — WebSocket 监听器接口

```
接口: com.aicode.agent.PluginWebsocketListener

方法:
  void onOpen()                    — 连接建立
  void onMessage(String message)   — 收到消息（原始 JSON 字符串）
  void onClose(int code, String reason)  — 连接关闭
  void onError(Exception ex)       — 连接异常
```

### 3.2 PluginWebsocketClient — WebSocket 客户端

```
类: com.aicode.agent.PluginWebsocketClient
父类: 无（独立实现）

字段:
  private PluginWebsocketListener listener       — 消息监听器
  private WebSocket webSocket                    — OkHttp WebSocket 实例
  private String serverUrl                       — 服务器地址
  private String token                           — 认证令牌
  private volatile boolean connected             — 连接状态标志
  private ScheduledExecutorService heartbeatExecutor — 心跳调度器
  private int reconnectAttempts                  — 重连次数
  private static final int MAX_RECONNECT = 5     — 最大重连次数
  private static final long RECONNECT_DELAY = 3000 — 重连延迟(ms)

方法:
  void connect(String url, String token)         — 建立 WebSocket 连接
  void sendWsMessage(MessageDto message)         — 发送消息到 Agent
  void close()                                   — 关闭连接
  void reconnect()                               — 重连逻辑（指数退避）
  boolean isConnected()                          — 检查连接状态
  void sendHeartbeat()                           — 发送心跳包
  void sendPong()                                — 响应 Ping

连接流程:
  1. 构建 OkHttp Request，添加 Authorization: Bearer {token}
  2. OkHttpClient.newWebSocket(request, listener)
  3. onOpen 回调 → 设置 connected=true → 通知 PluginWebsocketListener
  4. 启动心跳定时器（每30秒发送一次）

重连机制:
  - onClose/onError 触发重连
  - 指数退避: delay = RECONNECT_DELAY * 2^attempt
  - 超过 MAX_RECONNECT 后放弃重连
  - 重连成功后重置 reconnectAttempts
```

### 3.3 SocketMessageHandleListener — 消息分发核心

```
类: com.aicode.agent.SocketMessageHandleListener
实现: PluginWebsocketListener

字段:
  private ChatService chatService                           — 聊天服务
  private InlineChatCommandService inlineChatCommandService — 行内聊天服务
  private CodeCompleteService codeCompleteService           — 代码补全服务
  private UnitTestService unitTestService                   — 单测生成服务
  private BatchUnitTestService batchUnitTestService         — 批量单测服务
  private CodeCheckService codeCheckService                 — 代码检查服务
  private CodeSearchService codeSearchService               — 代码搜索服务
  private GitReviewService gitReviewService                 — Git 审查服务
  private SqlService sqlService                             — SQL 服务
  private TemplateRequestService templateRequestService     — 模板服务
  private UserService userService                           — 用户服务
  private WebViewWindowPanel webViewPanel                   — WebView 面板

核心方法:
  void onMessage(String rawJson)
    — 解析 JSON → MessageDto
    — 调用 onMessage(MessageDto)

  void onMessage(MessageDto msg)
    — 提取 command 字段
    — switch(command) 路由到对应 Service
    — 处理流式/非流式响应

  void onOpen()
    — 日志记录连接建立
    — 通知 WebView 连接就绪

  void onClose(int code, String reason)
    — 日志记录连接关闭
    — 通知 WebView 连接断开
    — 触发重连

  void onError(Exception ex)
    — 日志记录异常
    — 通知 WebView 错误状态
```

### 3.4 CommandEnum — 命令枚举

```
枚举: com.aicode.agent.enums.CommandEnum
枚举值总数: 640+

注意: 枚举值的字符串名称经过混淆处理（H混淆器），实际运行时通过
AICodeStringUtil.h() 解码还原。以下列出的是解码后的语义名称。

分类:

  聊天类 (CHAT_*):
    CHAT_SEND, CHAT_STOP, CHAT_DELETE, CHAT_LIST,
    CHAT_DETAIL, CHAT_TITLE_UPDATE, CHAT_FEEDBACK,
    CHAT_HISTORY, CHAT_SHARE, CHAT_EXPORT

  行内聊天类 (INLINE_CHAT_*):
    INLINE_CHAT_SEND, INLINE_CHAT_STOP,
    INLINE_CHAT_ACCEPT, INLINE_CHAT_REJECT,
    INLINE_CHAT_DIFF, INLINE_CHAT_APPLY

  代码补全类 (CODE_COMPLETE_*):
    CODE_COMPLETE_REQUEST, CODE_COMPLETE_ACCEPT,
    CODE_COMPLETE_CANCEL, CODE_COMPLETE_RESULT

  单元测试类 (UNIT_TEST_*):
    UNIT_TEST_GENERATE, UNIT_TEST_BATCH,
    UNIT_TEST_RUN, UNIT_TEST_RESULT,
    UNIT_TEST_APPLY

  代码检查类 (CODE_CHECK_*):
    CODE_CHECK_REQUEST, CODE_CHECK_RESULT,
    CODE_CHECK_FIX, CODE_CHECK_IGNORE

  代码搜索类 (CODE_SEARCH_*):
    CODE_SEARCH_REQUEST, CODE_SEARCH_RESULT,
    CODE_SEARCH_INDEX

  Git 审查类 (GIT_REVIEW_*):
    GIT_REVIEW_START, GIT_REVIEW_RESULT,
    GIT_REVIEW_APPLY

  SQL 类 (SQL_*):
    SQL_GENERATE, SQL_OPTIMIZE, SQL_EXPLAIN,
    SQL_CONVERT, SQL_RESULT

  模板类 (TEMPLATE_*):
    TEMPLATE_REQUEST, TEMPLATE_RESULT,
    TEMPLATE_LIST, TEMPLATE_DETAIL

  用户类 (USER_*):
    USER_LOGIN, USER_LOGOUT, USER_INFO,
    USER_QUOTA, USER_SETTINGS, USER_UPDATE

  系统类 (SYSTEM_*):
    HEARTBEAT, PING, PONG, ERROR,
    CONNECTION_LOST, VERSION_CHECK,
    CONFIG_UPDATE, FEATURE_FLAG

  代码生成类 (CODE_GEN_*):
    CODE_GEN_REQUEST, CODE_GEN_RESULT,
    CODE_GEN_APPLY, CODE_GEN_CANCEL

  代码解释类 (CODE_EXPLAIN_*):
    CODE_EXPLAIN_REQUEST, CODE_EXPLAIN_RESULT

  代码注释类 (CODE_COMMENT_*):
    CODE_COMMENT_REQUEST, CODE_COMMENT_RESULT

  代码翻译类 (CODE_TRANSLATE_*):
    CODE_TRANSLATE_REQUEST, CODE_TRANSLATE_RESULT

  提交信息类 (COMMIT_MSG_*):
    COMMIT_MSG_GENERATE, COMMIT_MSG_RESULT

  Diff 审查类 (DIFF_REVIEW_*):
    DIFF_REVIEW_REQUEST, DIFF_REVIEW_RESULT

  对话上下文类 (CONTEXT_*):
    CONTEXT_FILES, CONTEXT_SYMBOLS,
    CONTEXT_DIAGNOSTICS, CONTEXT_SELECTION

  APM 类 (APM_*):
    APM_EVENT, APM_PERFORMANCE, APM_ERROR

  WebView 类 (WEBVIEW_*):
    WEBVIEW_READY, WEBVIEW_ACTION,
    WEBVIEW_DATA, WEBVIEW_STATE
```

### 3.5 CommandCache — 命令缓存

```
类: com.aicode.domain.CommandCache

字段:
  private String commandId                       — 命令唯一标识
  private CommandEnum command                    — 命令类型
  private long timestamp                         — 命令时间戳
  private MessageDto originalMessage             — 原始消息
  private CompletableFuture<ResponseDto> future  — 响应 Future
  private volatile boolean completed             — 是否已完成
  private volatile boolean cancelled             — 是否已取消

方法:
  static CommandCache create(MessageDto msg)     — 创建缓存条目
  void complete(ResponseDto response)            — 完成命令
  void cancel()                                  — 取消命令
  boolean isExpired(long timeoutMs)              — 检查是否超时
  void cleanup()                                 — 清理资源

用途:
  - 跟踪每个发出的命令，等待对应的响应
  - 支持超时检测和自动清理
  - 支持命令取消（如 CHAT_STOP）
  - 通过 commandId 关联请求与响应
```

### 3.6 MessageDto — 消息传输对象

```
类: com.aicode.agent.dto.MessageDto

字段:
  private String command                         — 命令类型（对应 CommandEnum）
  private String commandId                       — 命令唯一标识（UUID）
  private String data                            — 消息体（JSON 字符串）
  private String module                          — 模块标识（对应 AgentModuleEnum）
  private String sessionId                       — 会话标识
  private long timestamp                         — 时间戳
  private String version                         — 协议版本
  private Map<String, String> headers            — 扩展头信息

序列化格式:
{
  "command": "CHAT_SEND",
  "commandId": "uuid-xxxx-xxxx",
  "data": "{\"message\":\"hello\",\"chatId\":\"xxx\"}",
  "module": "CHAT",
  "sessionId": "session-xxx",
  "timestamp": 1715673600000,
  "version": "3.4.2",
  "headers": {}
}

方法:
  static MessageDto create(CommandEnum cmd)      — 创建消息（自动生成 commandId）
  static MessageDto create(CommandEnum cmd, Object data) — 创建带数据的消息
  String toJson()                                — 序列化为 JSON
  static MessageDto fromJson(String json)        — 从 JSON 反序列化
```

### 3.7 ResponseStreamDto — 流式响应 DTO

```
类: com.aicode.agent.dto.ResponseStreamDto

字段:
  private String commandId                       — 对应请求的 commandId
  private String command                         — 命令类型
  private String module                          — 模块标识
  private boolean ended                          — 流是否结束
  private String text                            — 本次增量文本
  private ResponseData data                      — 结构化响应数据
  private String error                           — 错误信息
  private int errorCode                          — 错误码

内部类 ResponseData:
  字段:
    private String type                          — 数据类型（对应 WebViewDataTypeEnum）
    private Object content                       — 数据内容
    private Map<String, Object> metadata         — 元数据

序列化格式:
{
  "commandId": "uuid-xxxx",
  "command": "CHAT_SEND",
  "module": "CHAT",
  "ended": false,
  "text": "这是一段增量文本",
  "data": {
    "type": "CHAT_MESSAGE",
    "content": { ... },
    "metadata": { "tokens": 42 }
  },
  "error": null,
  "errorCode": 0
}

流式消息生命周期:
  1. Agent 开始生成 → 发送 ended=false 的 ResponseStreamDto（含增量 text）
  2. 持续发送多个 ended=false 的增量包
  3. 生成完成 → 发送 ended=true 的最终包（含完整 data）
  4. 如出错 → 发送 ended=true + error 字段
```

### 3.8 ResponseDto — 非流式响应 DTO

```
类: com.aicode.agent.dto.ResponseDto

字段:
  private String commandId                       — 对应请求的 commandId
  private String command                         — 命令类型
  private boolean success                        — 是否成功
  private Object data                            — 响应数据
  private String error                           — 错误信息
  private int errorCode                          — 错误码
  private long timestamp                         — 响应时间戳

序列化格式:
{
  "commandId": "uuid-xxxx",
  "command": "USER_INFO",
  "success": true,
  "data": { ... },
  "error": null,
  "errorCode": 0,
  "timestamp": 1715673600000
}
```

## 4. CommandEnum → Service 映射表

### 4.1 聊天模块 (CHAT)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| CHAT_SEND | ChatService | chatSend() | IDE→Agent | 是 |
| CHAT_STOP | ChatService | chatStop() | IDE→Agent | 否 |
| CHAT_DELETE | ChatService | chatDelete() | IDE→Agent | 否 |
| CHAT_LIST | ChatService | chatList() | IDE→Agent | 否 |
| CHAT_DETAIL | ChatService | chatDetail() | IDE→Agent | 否 |
| CHAT_TITLE_UPDATE | ChatService | chatTitleUpdate() | IDE→Agent | 否 |
| CHAT_FEEDBACK | ChatService | chatFeedback() | IDE→Agent | 否 |
| CHAT_HISTORY | ChatService | chatHistory() | IDE→Agent | 否 |
| CHAT_SHARE | ChatService | chatShare() | IDE→Agent | 否 |
| CHAT_EXPORT | ChatService | chatExport() | IDE→Agent | 否 |

### 4.2 行内聊天模块 (INLINE_CHAT)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| INLINE_CHAT_SEND | InlineChatCommandService | inlineSend() | IDE→Agent | 是 |
| INLINE_CHAT_STOP | InlineChatCommandService | inlineStop() | IDE→Agent | 否 |
| INLINE_CHAT_ACCEPT | InlineChatCommandService | inlineAccept() | IDE→Agent | 否 |
| INLINE_CHAT_REJECT | InlineChatCommandService | inlineReject() | IDE→Agent | 否 |
| INLINE_CHAT_DIFF | InlineChatCommandService | inlineDiff() | Agent→IDE | 否 |
| INLINE_CHAT_APPLY | InlineChatCommandService | inlineApply() | Agent→IDE | 否 |

### 4.3 代码补全模块 (CODE_COMPLETE)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| CODE_COMPLETE_REQUEST | CodeCompleteService | complete() | IDE→Agent | 是 |
| CODE_COMPLETE_ACCEPT | CodeCompleteService | accept() | IDE→Agent | 否 |
| CODE_COMPLETE_CANCEL | CodeCompleteService | cancel() | IDE→Agent | 否 |
| CODE_COMPLETE_RESULT | CodeCompleteService | handleResult() | Agent→IDE | 否 |

### 4.4 单元测试模块 (UNIT_TEST)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| UNIT_TEST_GENERATE | UnitTestService | generate() | IDE→Agent | 是 |
| UNIT_TEST_BATCH | BatchUnitTestService | batchGenerate() | IDE→Agent | 是 |
| UNIT_TEST_RUN | UnitTestService | run() | IDE→Agent | 否 |
| UNIT_TEST_RESULT | UnitTestService | handleResult() | Agent→IDE | 否 |
| UNIT_TEST_APPLY | UnitTestService | apply() | Agent→IDE | 否 |

### 4.5 代码检查模块 (CODE_CHECK)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| CODE_CHECK_REQUEST | CodeCheckService | check() | IDE→Agent | 是 |
| CODE_CHECK_RESULT | CodeCheckService | handleResult() | Agent→IDE | 否 |
| CODE_CHECK_FIX | CodeCheckService | fix() | IDE→Agent | 是 |
| CODE_CHECK_IGNORE | CodeCheckService | ignore() | IDE→Agent | 否 |

### 4.6 代码搜索模块 (CODE_SEARCH)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| CODE_SEARCH_REQUEST | CodeSearchService | search() | IDE→Agent | 否 |
| CODE_SEARCH_RESULT | CodeSearchService | handleResult() | Agent→IDE | 否 |
| CODE_SEARCH_INDEX | CodeSearchService | index() | IDE→Agent | 否 |

### 4.7 Git 审查模块 (GIT_REVIEW)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| GIT_REVIEW_START | GitReviewService | startReview() | IDE→Agent | 是 |
| GIT_REVIEW_RESULT | GitReviewService | handleResult() | Agent→IDE | 否 |
| GIT_REVIEW_APPLY | GitReviewService | apply() | Agent→IDE | 否 |

### 4.8 SQL 模块 (SQL)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| SQL_GENERATE | SqlService | generate() | IDE→Agent | 是 |
| SQL_OPTIMIZE | SqlService | optimize() | IDE→Agent | 是 |
| SQL_EXPLAIN | SqlService | explain() | IDE→Agent | 是 |
| SQL_CONVERT | SqlService | convert() | IDE→Agent | 是 |
| SQL_RESULT | SqlService | handleResult() | Agent→IDE | 否 |

### 4.9 模板模块 (TEMPLATE)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| TEMPLATE_REQUEST | TemplateRequestService | request() | IDE→Agent | 是 |
| TEMPLATE_RESULT | TemplateRequestService | handleResult() | Agent→IDE | 否 |
| TEMPLATE_LIST | TemplateRequestService | list() | IDE→Agent | 否 |
| TEMPLATE_DETAIL | TemplateRequestService | detail() | IDE→Agent | 否 |

### 4.10 用户模块 (USER)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| USER_LOGIN | UserService | login() | IDE→Agent | 否 |
| USER_LOGOUT | UserService | logout() | IDE→Agent | 否 |
| USER_INFO | UserService | getUserInfo() | IDE→Agent | 否 |
| USER_QUOTA | UserService | getQuota() | IDE→Agent | 否 |
| USER_SETTINGS | UserService | getSettings() | IDE→Agent | 否 |
| USER_UPDATE | UserService | updateSettings() | IDE→Agent | 否 |

### 4.11 系统模块 (SYSTEM)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| HEARTBEAT | (内部) | sendHeartbeat() | IDE→Agent | 否 |
| PING | PluginWebsocketClient | sendPong() | Agent→IDE | 否 |
| PONG | (内部) | 更新心跳时间 | IDE→Agent | 否 |
| ERROR | (内部) | 日志+通知 | Agent→IDE | 否 |
| CONNECTION_LOST | PluginWebsocketClient | reconnect() | Agent→IDE | 否 |
| VERSION_CHECK | (内部) | 版本校验 | 双向 | 否 |
| CONFIG_UPDATE | (内部) | 更新配置 | Agent→IDE | 否 |
| FEATURE_FLAG | (内部) | 功能开关 | Agent→IDE | 否 |

### 4.12 代码生成/解释/注释/翻译模块

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| CODE_GEN_REQUEST | ChatService | codeGen() | IDE→Agent | 是 |
| CODE_GEN_RESULT | ChatService | handleCodeGenResult() | Agent→IDE | 否 |
| CODE_GEN_APPLY | ChatService | applyCodeGen() | Agent→IDE | 否 |
| CODE_GEN_CANCEL | ChatService | cancelCodeGen() | IDE→Agent | 否 |
| CODE_EXPLAIN_REQUEST | ChatService | codeExplain() | IDE→Agent | 是 |
| CODE_EXPLAIN_RESULT | ChatService | handleExplainResult() | Agent→IDE | 否 |
| CODE_COMMENT_REQUEST | ChatService | codeComment() | IDE→Agent | 是 |
| CODE_COMMENT_RESULT | ChatService | handleCommentResult() | Agent→IDE | 否 |
| CODE_TRANSLATE_REQUEST | ChatService | codeTranslate() | IDE→Agent | 是 |
| CODE_TRANSLATE_RESULT | ChatService | handleTranslateResult() | Agent→IDE | 否 |

### 4.13 提交信息与 Diff 审查模块

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| COMMIT_MSG_GENERATE | GitReviewService | generateCommitMsg() | IDE→Agent | 是 |
| COMMIT_MSG_RESULT | GitReviewService | handleCommitMsgResult() | Agent→IDE | 否 |
| DIFF_REVIEW_REQUEST | GitReviewService | diffReview() | IDE→Agent | 是 |
| DIFF_REVIEW_RESULT | GitReviewService | handleDiffResult() | Agent→IDE | 否 |

### 4.14 上下文模块 (CONTEXT)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| CONTEXT_FILES | ChatService | sendContextFiles() | IDE→Agent | 否 |
| CONTEXT_SYMBOLS | ChatService | sendContextSymbols() | IDE→Agent | 否 |
| CONTEXT_DIAGNOSTICS | ChatService | sendDiagnostics() | IDE→Agent | 否 |
| CONTEXT_SELECTION | ChatService | sendSelection() | IDE→Agent | 否 |

### 4.15 APM 模块

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| APM_EVENT | (内部) | reportEvent() | IDE→Agent | 否 |
| APM_PERFORMANCE | (内部) | reportPerf() | IDE→Agent | 否 |
| APM_ERROR | (内部) | reportError() | IDE→Agent | 否 |

## 5. 双向消息类型完整列表

### 5.1 IDE → Agent 消息（请求类）

| 消息类型 | 用途 | data 字段结构 |
|---|---|---|
| CHAT_SEND | 发送聊天消息 | `{chatId, message, context?, files?}` |
| CHAT_STOP | 停止生成 | `{chatId}` |
| CHAT_DELETE | 删除对话 | `{chatId}` |
| CHAT_LIST | 获取对话列表 | `{page, size}` |
| CHAT_DETAIL | 获取对话详情 | `{chatId}` |
| CHAT_TITLE_UPDATE | 更新对话标题 | `{chatId, title}` |
| CHAT_FEEDBACK | 提交反馈 | `{chatId, messageId, rating, comment?}` |
| INLINE_CHAT_SEND | 行内聊天请求 | `{editorId, file, offset, selection?, instruction}` |
| INLINE_CHAT_STOP | 停止行内聊天 | `{editorId}` |
| INLINE_CHAT_ACCEPT | 接受行内修改 | `{editorId}` |
| INLINE_CHAT_REJECT | 拒绝行内修改 | `{editorId}` |
| CODE_COMPLETE_REQUEST | 请求代码补全 | `{file, offset, prefix, suffix, language}` |
| CODE_COMPLETE_ACCEPT | 接受补全建议 | `{requestId}` |
| CODE_COMPLETE_CANCEL | 取消补全请求 | `{requestId}` |
| UNIT_TEST_GENERATE | 生成单测 | `{file, class, method?, framework}` |
| UNIT_TEST_BATCH | 批量生成单测 | `{files[], framework}` |
| UNIT_TEST_RUN | 运行单测 | `{testFile, testClass?}` |
| CODE_CHECK_REQUEST | 请求代码检查 | `{file, content, language}` |
| CODE_CHECK_FIX | 请求代码修复 | `{file, issues[]}` |
| CODE_CHECK_IGNORE | 忽略检查项 | `{file, issueIds[]}` |
| CODE_SEARCH_REQUEST | 代码搜索 | `{query, type, maxResults}` |
| CODE_SEARCH_INDEX | 索引代码 | `{projectPath, files[]}` |
| GIT_REVIEW_START | 开始 Git 审查 | `{diff, baseBranch?, targetBranch?}` |
| SQL_GENERATE | 生成 SQL | `{description, dialect?, schema?}` |
| SQL_OPTIMIZE | 优化 SQL | `{sql, dialect?}` |
| SQL_EXPLAIN | 解释 SQL | `{sql, dialect?}` |
| SQL_CONVERT | 转换 SQL | `{sql, fromDialect, toDialect}` |
| TEMPLATE_REQUEST | 请求模板 | `{templateId, params?}` |
| TEMPLATE_LIST | 获取模板列表 | `{category?}` |
| USER_LOGIN | 用户登录 | `{token, refreshToken?}` |
| USER_LOGOUT | 用户登出 | `{}` |
| USER_INFO | 获取用户信息 | `{}` |
| USER_QUOTA | 获取配额 | `{}` |
| USER_SETTINGS | 获取设置 | `{}` |
| USER_UPDATE | 更新设置 | `{settings}` |
| HEARTBEAT | 心跳 | `{timestamp}` |
| PONG | 心跳响应 | `{timestamp}` |
| CODE_GEN_REQUEST | 代码生成 | `{instruction, file?, language?}` |
| CODE_EXPLAIN_REQUEST | 代码解释 | `{file, selection?, content}` |
| CODE_COMMENT_REQUEST | 代码注释 | `{file, selection?, content}` |
| CODE_TRANSLATE_REQUEST | 代码翻译 | `{content, fromLang, toLang}` |
| COMMIT_MSG_GENERATE | 生成提交信息 | `{diff, convention?}` |
| DIFF_REVIEW_REQUEST | Diff 审查 | `{diff, file?}` |
| CONTEXT_FILES | 发送文件上下文 | `{files[]}` |
| CONTEXT_SYMBOLS | 发送符号上下文 | `{symbols[]}` |
| CONTEXT_DIAGNOSTICS | 发送诊断信息 | `{diagnostics[]}` |
| CONTEXT_SELECTION | 发送选区 | `{file, startOffset, endOffset, text}` |
| APM_EVENT | 上报事件 | `{event, properties?}` |
| APM_PERFORMANCE | 上报性能 | `{metric, value, unit}` |
| APM_ERROR | 上报错误 | `{error, stack?, context?}` |

### 5.2 Agent → IDE 消息（响应类）

| 消息类型 | 用途 | 流式 | data 字段结构 |
|---|---|---|---|
| CHAT_SEND (response) | 聊天流式响应 | 是 | ResponseStreamDto: `{ended, text, data}` |
| INLINE_CHAT_DIFF | 行内聊天 Diff | 否 | `{editorId, diff: {hunks[]}}` |
| INLINE_CHAT_APPLY | 行内聊天应用结果 | 否 | `{editorId, success}` |
| CODE_COMPLETE_RESULT | 补全结果 | 否 | `{requestId, completions[]}` |
| UNIT_TEST_RESULT | 单测结果 | 否 | `{file, testCode, passed?, failures[]}` |
| UNIT_TEST_APPLY | 单测应用结果 | 否 | `{file, success}` |
| CODE_CHECK_RESULT | 检查结果 | 否 | `{file, issues[]}` |
| CODE_SEARCH_RESULT | 搜索结果 | 否 | `{results[]}` |
| GIT_REVIEW_RESULT | 审查结果 | 否 | `{review: {comments[], suggestions[]}}` |
| SQL_RESULT | SQL 结果 | 否 | `{sql, explanation?}` |
| TEMPLATE_RESULT | 模板结果 | 否 | `{templateId, content}` |
| USER_INFO (response) | 用户信息 | 否 | `{user: {id, name, avatar, quota}}` |
| USER_QUOTA (response) | 配额信息 | 否 | `{quota: {used, total, expires}}` |
| PING | 心跳请求 | 否 | `{timestamp}` |
| ERROR | 错误通知 | 否 | `{code, message, details?}` |
| CONNECTION_LOST | 连接丢失 | 否 | `{reason}` |
| CONFIG_UPDATE | 配置更新 | 否 | `{config}` |
| FEATURE_FLAG | 功能开关 | 否 | `{flags: {key: value}}` |
| CODE_GEN_RESULT | 代码生成结果 | 否 | `{code, language, file?}` |
| CODE_GEN_APPLY | 代码生成应用 | 否 | `{file, success}` |
| CODE_EXPLAIN_RESULT | 解释结果 | 否 | `{explanation}` |
| CODE_COMMENT_RESULT | 注释结果 | 否 | `{commentedCode}` |
| CODE_TRANSLATE_RESULT | 翻译结果 | 否 | `{translatedCode, language}` |
| COMMIT_MSG_RESULT | 提交信息结果 | 否 | `{message}` |
| DIFF_REVIEW_RESULT | Diff 审查结果 | 否 | `{review: {issues[], suggestions[]}}` |

## 6. 流式响应机制详解

### 6.1 流式消息协议

```
┌──────────┐                          ┌──────────┐
│   IDE    │                          │  Agent   │
│          │  CHAT_SEND (request)     │          │
│          │─────────────────────────►│          │
│          │                          │  开始生成 │
│          │  ResponseStreamDto #1    │          │
│          │◄─────────────────────────│          │
│          │  {ended:false, text:"你"}│          │
│          │                          │          │
│          │  ResponseStreamDto #2    │          │
│          │◄─────────────────────────│          │
│          │  {ended:false, text:"好"}│          │
│          │                          │          │
│          │  ResponseStreamDto #3    │          │
│          │◄─────────────────────────│          │
│          │  {ended:false, text:"！"}│          │
│          │                          │          │
│          │  ResponseStreamDto #N    │          │
│          │◄─────────────────────────│          │
│          │  {ended:true,            │          │
│          │   data:{type:"CHAT_MSG", │          │
│          │   content:{...}}}        │          │
│          │                          │          │
└──────────┘                          └──────────┘
```

### 6.2 流式处理流程

```
Agent 发送 ResponseStreamDto
     │
     ▼
SocketMessageHandleListener.onMessage()
     │
     │ 解析 ResponseStreamDto
     ▼
判断 ended 字段
     │
     ├── ended = false (增量包)
     │     │
     │     ▼
     │   提取 text 字段（增量文本）
     │     │
     │     ▼
     │   WebViewWindowPanel.send2Web(
     │     WebViewDataTypeEnum.STREAM_TEXT,
     │     {commandId, text, ended: false}
     │   )
     │     │
     │     ▼
     │   WebView JS: 追加增量文本到当前消息
     │
     └── ended = true (结束包)
           │
           ▼
         检查 error 字段
           │
           ├── error != null (错误)
           │     │
           │     ▼
           │   WebViewWindowPanel.send2Web(
           │     WebViewDataTypeEnum.ERROR,
           │     {commandId, error, errorCode}
           │   )
           │     │
           │     ▼
           │   WebView JS: 显示错误信息
           │
           └── error == null (成功)
                 │
                 ▼
               提取 data 字段（完整结构化数据）
                 │
                 ▼
               WebViewWindowPanel.send2Web(
                 WebViewDataTypeEnum.CHAT_MESSAGE,
                 {commandId, data, ended: true}
               )
                 │
                 ▼
               WebView JS: 完成消息渲染，更新 UI 状态
```

### 6.3 流式响应的 WebView 回传机制

```
Service 处理流式包
     │
     ▼
WebViewWindowPanel.send2Web(WebViewDataTypeEnum type, Object data)
     │
     │ 1. 构建传输对象: {type, data, timestamp}
     │ 2. JSON 序列化
     ▼
browser.callJs("window.iFlyCodeBridge.onMessage('" + json + "')")
     │
     ▼
WebView JS Bridge (window.iFlyCodeBridge.onMessage)
     │
     │ 解析 JSON → 根据 type 分发
     ▼
对应 JS Handler 处理
```

### 6.4 流式消息的取消机制

```
用户点击"停止"按钮
     │
     ▼
WebView JS: window.iFlyCodeBridge.postMessage({type: "STOP", commandId: "xxx"})
     │
     ▼
WebViewWindowPanel 收到 JS 消息
     │
     ▼
ChatService.chatStop() / InlineChatCommandService.inlineStop()
     │
     ▼
PluginWebsocketClient.sendWsMessage(
  MessageDto.create(CommandEnum.CHAT_STOP, {chatId, commandId})
)
     │
     ▼
Agent 停止生成 → 发送 ended=true 的最终包
     │
     ▼
IDE 收到最终包 → 更新 UI 为"已停止"状态
```

## 7. WebView ↔ IDE JS Bridge 消息

### 7.1 WebViewDataTypeEnum — WebView 数据类型

Agent → WebView 的数据类型枚举，用于 `send2Web()` 调用：

| 枚举值 | 用途 | data 结构 |
|---|---|---|
| CHAT_MESSAGE | 聊天消息 | `{chatId, messageId, role, content, timestamp}` |
| STREAM_TEXT | 流式增量文本 | `{commandId, text, ended}` |
| INLINE_DIFF | 行内 Diff 展示 | `{editorId, hunks[], original, modified}` |
| CODE_COMPLETE | 代码补全建议 | `{requestId, completions: [{text, range}]}` |
| UNIT_TEST_RESULT | 单测生成结果 | `{file, code, passed, failures}` |
| CODE_CHECK_RESULT | 代码检查结果 | `{file, issues: [{severity, message, range, fix?}]}` |
| CODE_SEARCH_RESULT | 代码搜索结果 | `{results: [{file, line, snippet, score}]}` |
| GIT_REVIEW_RESULT | Git 审查结果 | `{comments, suggestions}` |
| SQL_RESULT | SQL 结果 | `{sql, explanation}` |
| TEMPLATE_RESULT | 模板结果 | `{templateId, content}` |
| USER_INFO | 用户信息 | `{id, name, avatar, quota}` |
| ERROR | 错误信息 | `{code, message, details}` |
| CONNECTION_STATUS | 连接状态 | `{connected, reconnecting}` |
| FEATURE_FLAGS | 功能开关 | `{flags}` |
| CONFIG | 配置信息 | `{config}` |
| CHAT_LIST | 对话列表 | `{chats: [{id, title, timestamp}]}` |
| CHAT_DETAIL | 对话详情 | `{chatId, messages[]}` |
| QUOTA_INFO | 配额信息 | `{used, total, expires}` |
| LOADING | 加载状态 | `{commandId, loading}` |
| PROGRESS | 进度信息 | `{commandId, progress, total, message}` |

### 7.2 WebViewResponseTypeEnum — WebView 响应类型

WebView → IDE 的请求类型枚举，用于 JS `postMessage` 调用：

| 枚举值 | 用途 | 触发场景 | IDE 处理 |
|---|---|---|---|
| CHAT_SEND | 发送聊天消息 | 用户输入并发送 | ChatService.chatSend() |
| CHAT_STOP | 停止生成 | 用户点击停止 | ChatService.chatStop() |
| CHAT_DELETE | 删除对话 | 用户删除对话 | ChatService.chatDelete() |
| CHAT_LIST | 获取对话列表 | 打开聊天面板 | ChatService.chatList() |
| CHAT_FEEDBACK | 提交反馈 | 用户评价回复 | ChatService.chatFeedback() |
| INLINE_ACCEPT | 接受行内修改 | 用户点击接受 | InlineChatCommandService.inlineAccept() |
| INLINE_REJECT | 拒绝行内修改 | 用户点击拒绝 | InlineChatCommandService.inlineReject() |
| CODE_COMPLETE_ACCEPT | 接受补全 | 用户选择补全项 | CodeCompleteService.accept() |
| CODE_COMPLETE_CANCEL | 取消补全 | 用户忽略补全 | CodeCompleteService.cancel() |
| UNIT_TEST_APPLY | 应用单测 | 用户点击应用 | UnitTestService.apply() |
| CODE_CHECK_FIX | 修复代码 | 用户点击修复 | CodeCheckService.fix() |
| CODE_CHECK_IGNORE | 忽略检查项 | 用户点击忽略 | CodeCheckService.ignore() |
| GIT_REVIEW_APPLY | 应用审查建议 | 用户点击应用 | GitReviewService.apply() |
| SQL_COPY | 复制 SQL | 用户点击复制 | (本地剪贴板) |
| TEMPLATE_USE | 使用模板 | 用户选择模板 | TemplateRequestService.request() |
| USER_LOGIN | 登录 | 用户点击登录 | UserService.login() |
| USER_LOGOUT | 登出 | 用户点击登出 | UserService.logout() |
| SETTINGS_OPEN | 打开设置 | 用户点击设置 | (打开 IDE 设置) |
| READY | WebView 就绪 | 页面加载完成 | 发送初始数据 |
| RESIZE | 窗口大小变化 | 面板大小变化 | (调整布局) |

### 7.3 JS Bridge 通信协议

```
IDE → WebView (send2Web):
  browser.callJs("window.iFlyCodeBridge.onMessage('" + JSON.stringify({
    type: "CHAT_MESSAGE",     // WebViewDataTypeEnum
    data: { ... },            // 具体数据
    timestamp: 1715673600000  // 时间戳
  }) + "')")

WebView → IDE (postMessage):
  window.iFlyCodeBridge.postMessage(JSON.stringify({
    type: "CHAT_SEND",        // WebViewResponseTypeEnum
    data: { ... },            // 请求数据
    requestId: "uuid-xxxx"    // 请求标识
  }))

WebView → IDE (响应回调):
  window.iFlyCodeBridge.onResponse({
    requestId: "uuid-xxxx",   // 对应请求的 requestId
    success: true,
    data: { ... }
  })
```

## 8. 消息分发时序图

### 8.1 聊天消息完整时序

```
User    WebView    WebViewPanel    SocketMsgHandler    ChatService    WSClient    Agent
  │        │            │                │                 │             │         │
  │ 输入   │            │                │                 │             │         │
  │───────►│            │                │                 │             │         │
  │        │ postMessage │                │                 │             │         │
  │        │(CHAT_SEND)  │                │                 │             │         │
  │        │────────────►│                │                 │             │         │
  │        │            │ chatSend()      │                 │             │         │
  │        │            │────────────────►│                 │             │         │
  │        │            │                │ create MessageDto│             │         │
  │        │            │                │─────────────────►│             │         │
  │        │            │                │                 │ sendWsMsg() │         │
  │        │            │                │                 │────────────►│         │
  │        │            │                │                 │             │────────►│
  │        │            │                │                 │             │         │ 处理
  │        │            │                │                 │             │         │ 生成
  │        │            │                │                 │             │◄────────│
  │        │            │                │                 │             │ stream#1│
  │        │            │                │  onMessage()    │             │         │
  │        │            │                │◄────────────────│◄────────────│         │
  │        │            │ send2Web()     │                 │             │         │
  │        │            │(STREAM_TEXT)   │                 │             │         │
  │        │            │◄───────────────│                 │             │         │
  │        │ onMessage() │               │                 │             │         │
  │        │◄────────────│               │                 │             │         │
  │ 渲染   │            │                │                 │             │         │
  │◄───────│            │                │                 │             │         │
  │        │            │                │                 │             │◄────────│
  │        │            │                │  onMessage()    │             │ stream#2│
  │        │            │                │◄────────────────│◄────────────│         │
  │        │            │ send2Web()     │                 │             │         │
  │        │            │(STREAM_TEXT)   │                 │             │         │
  │        │            │◄───────────────│                 │             │         │
  │        │ onMessage() │               │                 │             │         │
  │        │◄────────────│               │                 │             │         │
  │ 追加   │            │                │                 │             │         │
  │◄───────│            │                │                 │             │         │
  │        │            │                │                 │             │  ...    │
  │        │            │                │                 │             │◄────────│
  │        │            │                │  onMessage()    │             │ ended   │
  │        │            │                │◄────────────────│◄────────────│         │
  │        │            │ send2Web()     │                 │             │         │
  │        │            │(CHAT_MESSAGE)  │                 │             │         │
  │        │            │◄───────────────│                 │             │         │
  │        │ onMessage() │               │                 │             │         │
  │        │◄────────────│               │                 │             │         │
  │ 完成   │            │                │                 │             │         │
  │◄───────│            │                │                 │             │         │
```

### 8.2 代码补全时序

```
Editor   CodeCompleteService    WSClient    Agent    SocketMsgHandler    WebViewPanel
  │            │                    │         │             │                 │
  │ 类型事件   │                    │         │             │                 │
  │───────────►│                    │         │             │                 │
  │            │ sendWsMsg()        │         │             │                 │
  │            │(CODE_COMPLETE_     │         │             │                 │
  │            │ REQUEST)           │         │             │                 │
  │            │───────────────────►│         │             │                 │
  │            │                    │────────►│             │                 │
  │            │                    │         │ 推理        │                 │
  │            │                    │◄────────│             │                 │
  │            │  onMessage()       │         │             │                 │
  │            │◄───────────────────│         │             │                 │
  │            │                    │         │             │                 │
  │ 显示补全   │                    │         │             │                 │
  │ 列表       │                    │         │             │                 │
  │◄───────────│                    │         │             │                 │
  │            │                    │         │             │                 │
  │ 用户选择   │                    │         │             │                 │
  │───────────►│                    │         │             │                 │
  │            │ sendWsMsg()        │         │             │                 │
  │            │(CODE_COMPLETE_     │         │             │                 │
  │            │ ACCEPT)            │         │             │                 │
  │            │───────────────────►│────────►│             │                 │
  │ 插入代码   │                    │         │             │                 │
  │◄───────────│                    │         │             │                 │
```

## 9. AgentModuleEnum — Agent 模块枚举

```
枚举: com.aicode.agent.enums.AgentModuleEnum

模块分类:
  CHAT          — 聊天模块
  INLINE_CHAT   — 行内聊天模块
  CODE_COMPLETE — 代码补全模块
  CODE_GEN      — 代码生成模块
  CODE_EXPLAIN  — 代码解释模块
  CODE_COMMENT  — 代码注释模块
  CODE_TRANSLATE — 代码翻译模块
  CODE_CHECK    — 代码检查模块
  CODE_SEARCH   — 代码搜索模块
  UNIT_TEST     — 单元测试模块
  GIT_REVIEW    — Git 审查模块
  SQL           — SQL 模块
  TEMPLATE      — 模板模块
  USER          — 用户模块
  SYSTEM        — 系统模块
  APM           — APM 监控模块
  CONTEXT       — 上下文模块
```

## 10. 连接管理与容错

### 10.1 连接生命周期

```
PluginWebsocketClient 状态机:

  DISCONNECTED ──────► CONNECTING ──────► CONNECTED
       ▲                   │                   │
       │                   │ (失败)            │ (onClose/onError)
       │                   ▼                   │
       │              RECONNECTING ◄───────────┘
       │                   │
       │  (超过最大重连)    │ (指数退避重连)
       │                   ▼
       └────────────── CONNECTING ──────► CONNECTED
```

### 10.2 心跳机制

```
IDE ──────► Agent:  HEARTBEAT {timestamp}  (每30秒)
Agent ─────► IDE:   PONG {timestamp}       (响应)

如果 90 秒内未收到 PONG:
  → 标记连接为不可用
  → 触发重连流程
  → 通知 WebView CONNECTION_STATUS {connected: false}
```

### 10.3 消息可靠性

```
- 每条消息携带 commandId (UUID)
- CommandCache 缓存所有发出的请求
- 超时检测: 默认 30 秒未收到响应 → 超时回调
- 取消机制: CHAT_STOP / INLINE_CHAT_STOP / CODE_COMPLETE_CANCEL
- 幂等设计: 重复 commandId 的响应会被 CommandCache 忽略
```

## 11. 混淆与安全

### 11.1 字符串混淆

CommandEnum 的枚举值名称经过 H 混淆器处理:
- 编译后枚举名如 `a`, `b`, `c` ... `ka`, `kb` ...
- 运行时通过 `AICodeStringUtil.h()` 解码还原为语义名称
- 混淆映射存储在 `H.class` 的静态初始化块中

### 11.2 传输安全

- WebSocket 使用 WSS (TLS) 加密传输
- 认证通过 JWT Token (Authorization: Bearer header)
- Token 在连接建立时传入，过期后需重新登录
- 消息体中的敏感数据（如代码内容）不做额外加密，依赖 TLS

## 12. 总结

iFlyCode 的 WebSocket 消息分发链采用经典的 **观察者模式 + 命令路由** 架构:

1. **PluginWebsocketClient** 负责底层 WebSocket 连接管理（连接、重连、心跳）
2. **SocketMessageHandleListener** 作为核心分发器，通过 CommandEnum switch 路由到对应 Service
3. **各 Service** 处理业务逻辑，通过 WebViewWindowPanel 回传结果到 WebView
4. **流式响应** 通过 ResponseStreamDto 的 ended/text/data 三字段实现增量推送
5. **JS Bridge** 通过 WebViewDataTypeEnum/WebViewResponseTypeEnum 实现双向通信
6. **CommandCache** 提供请求-响应关联、超时检测和取消机制

整个链路的关键设计特点:
- 单一 WebSocket 连接复用所有业务（通过 command 字段区分）
- 流式与非流式消息统一协议（ResponseStreamDto 兼容两种模式）
- 指数退避重连保证连接可靠性
- commandId 实现请求-响应关联和幂等处理
