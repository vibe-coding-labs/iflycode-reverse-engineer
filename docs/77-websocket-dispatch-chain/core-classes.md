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
  1. 构建 OkHttp Request，添加 Authorization: Bearer &#123;token&#125;
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
  private CompletableFuture&lt;ResponseDto&gt; future  — 响应 Future
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
&#123;
  "command": "CHAT_SEND",
  "commandId": "uuid-xxxx-xxxx",
  "data": "&#123;\"message\":\"hello\",\"chatId\":\"xxx\"&#125;",
  "module": "CHAT",
  "sessionId": "session-xxx",
  "timestamp": 1715673600000,
  "version": "3.4.2",
  "headers": &#123;&#125;
&#125;

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
&#123;
  "commandId": "uuid-xxxx",
  "command": "CHAT_SEND",
  "module": "CHAT",
  "ended": false,
  "text": "这是一段增量文本",
  "data": &#123;
    "type": "CHAT_MESSAGE",
    "content": &#123; ... &#125;,
    "metadata": &#123; "tokens": 42 &#125;
  &#125;,
  "error": null,
  "errorCode": 0
&#125;

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
&#123;
  "commandId": "uuid-xxxx",
  "command": "USER_INFO",
  "success": true,
  "data": &#123; ... &#125;,
  "error": null,
  "errorCode": 0,
  "timestamp": 1715673600000
&#125;
```
