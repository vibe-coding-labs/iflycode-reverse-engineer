## 2. handleAction 分发模式分析

### 2.1 双通道分发架构

所有服务类实现两个分发入口：

```
WebView前端 -> handleAction(WebViewDataTypeEnum, JsonObject, String, Project)
Agent进程   -> handleAgentAction(CommandEnum, JsonObject, String, MessageDto, Project)
```

### 2.2 分发机制

1. **SwitchMap 模式**: 每个服务类都有一个内部类（如 ChatService$Ia）包含两个 `int[]` 数组，将枚举的 ordinal 值映射到 switch case 编号
2. **tableswitch/lookupswitch**: handleAction 方法使用 JVM 的 tableswitch 指令进行 O(1) 分发
3. **参数传递**: WebViewDataTypeEnum 决定路由，JsonObject 携带数据，String 为辅助参数（如 sessionId），Project 为 IDE 上下文

### 2.3 分发流程图

```
WebView前端操作
    |
    v
SocketMessageHandleListener.onMessage()
    |
    v
解析 WebViewDataTypeEnum
    |
    +-- CHAT_* ----------> ChatService.handleAction()
    +-- CODE_CHECK_* ---> CodeCheckService.handleAction()
    +-- CODE_SEARCH_* --> CodeSearchService.handleAction()
    +-- COMMON_* -------> CommonService.handleAction()
    +-- CODE_REVIEW_* --> GitReviewService.handleAction()
    +-- SQL_CHAT_* -----> SqlService.handleAction()
    +-- LOGIN_* --------> UserService.handleAction()

Agent进程响应
    |
    v
PluginWebsocketClient.onMessage()
    |
    v
解析 CommandEnum
    |
    +-- TALK_*/CODE_* --> ChatService.handleAgentAction()
    +-- CODE_COMPLETE --> CodeCompleteService.handleAgentAction()
    +-- GIT_* ----------> CodeSearchService.handleAgentAction()
    +-- GIT_DIFF/REVIEW > GitReviewService.handleAgentAction()
    +-- SQL_* ----------> SqlService.handleAgentAction()
    +-- USER_* --------> UserService.handleAgentAction()
    +-- INLINECHAT_* --> InlineChatCommandService.handleAgentAction()
```

### 2.4 handleAction 参数差异

| 服务类 | handleAction 签名 |
|--------|-------------------|
| ChatService | (WebViewDataTypeEnum, JsonObject, String, Project) |
| CodeCheckService | (WebViewWindowPanel, WebViewDataTypeEnum, JsonObject, Project) |
| CodeSearchService | (WebViewDataTypeEnum, JsonObject, Project) |
| CommonService | (WebViewDataTypeEnum, JsonObject, String, Project) |
| GitReviewService | (WebViewDataTypeEnum, JsonObject, Project) |
| SqlService | (WebViewDataTypeEnum, JsonObject, Project) |
| UserService | (WebViewDataTypeEnum, Project) |

注意 CodeCheckService 额外接收 WebViewWindowPanel 参数，UserService 不接收 JsonObject。

---

## 3. 服务间调用关系图

```
                    +------------------+
                    |  ChatService     |
                    |  (核心调度中心)   |
                    +--------+---------+
                             |
            +----------------+----------------+
            |                |                |
            v                v                v
    +-------+------+  +-----+------+  +------+-------+
    | UserService  |  | CommonSvc  |  | GitReviewSvc |
    | (登录/模型) |  | (通用操作) |  | (代码审查)   |
    +-------+------+  +-----+------+  +------+-------+
            |                |                |
            |                v                |
            |        +-------+-------+        |
            |        | CodeCheckSvc |        |
            |        | (代码检查)    |        |
            |        +-------+-------+        |
            |                |                |
            v                v                v
    +-------+------+  +-----+------+  +------+-------+
    | CodeComplete |  | CodeSearch |  | SqlService   |
    | Service      |  | Service    |  | (SQL助手)    |
    +-------+------+  +-----+------+  +------+-------+
            |                |                |
            v                v                v
    +-------+------+  +-----+------+  +------+-------+
    | RequestTip   |  | InlineChat |  | RecentFiles |
    | Service      |  | CmdService |  | Manager     |
    +--------------+  +-----+------+  +-------------+
                            |
                    +-------+-------+
                    | Restartable   |
                    | AgentProcess  |
                    | Service       |
                    +-------+-------+
                            |
                    +-------+-------+
                    | PluginAgent  |
                    | ProcessSvc   |
                    | Impl         |
                    +-------+-------+
                            |
                    +-------+-------+
                    | PluginAgent   |
                    | ProcessHandler|
                    +--------------+

跨服务调用关系:
- ChatService -> UserService.SetModel() (设置模型)
- ChatService -> CommonService.openPage() (打开页面)
- ChatService -> CommonService.chatMessage2Web() (发送消息到WebView)
- ChatService -> PluginWebsocketClient.sendWsMessage() (发送到Agent)
- CodeCheckService -> PluginWebsocketClient.sendWsMessage()
- CodeSearchService -> PluginWebsocketClient.sendWsMessage()
- SqlService -> PluginWebsocketClient.sendWsMessage()
- GitReviewService -> PluginWebsocketClient.sendWsMessage()
- UserService -> PluginWebsocketClient.sendWsMessage()
- CommonService -> AICodeSettingsState (读写配置)
- CommonService -> SocketMessageHandleListener.send2Web() (发送到WebView)
- CodeCompleteService -> RequestTipService (代码补全提示)
- InlineChatCommandService -> SessionController (行内聊天会话)
- InitService -> RequestTipServiceImpl (检查过期请求)
- RestartableAgentProcessService -> PluginAgentProcessServiceImpl (代理)
- UserService$da -> BrowserUtil.browse() (打开浏览器)
```

---
