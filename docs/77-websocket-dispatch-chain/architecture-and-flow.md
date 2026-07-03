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
