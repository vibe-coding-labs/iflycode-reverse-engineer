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
IDE ──────► Agent:  HEARTBEAT &#123;timestamp&#125;  (每30秒)
Agent ─────► IDE:   PONG &#123;timestamp&#125;       (响应)

如果 90 秒内未收到 PONG:
  → 标记连接为不可用
  → 触发重连流程
  → 通知 WebView CONNECTION_STATUS &#123;connected: false&#125;
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
