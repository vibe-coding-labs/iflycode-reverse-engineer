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
│          │  &#123;ended:false, text:"你"&#125;│          │
│          │                          │          │
│          │  ResponseStreamDto #2    │          │
│          │◄─────────────────────────│          │
│          │  &#123;ended:false, text:"好"&#125;│          │
│          │                          │          │
│          │  ResponseStreamDto #3    │          │
│          │◄─────────────────────────│          │
│          │  &#123;ended:false, text:"！"&#125;│          │
│          │                          │          │
│          │  ResponseStreamDto #N    │          │
│          │◄─────────────────────────│          │
│          │  &#123;ended:true,            │          │
│          │   data:&#123;type:"CHAT_MSG", │          │
│          │   content:&#123;...&#125;&#125;&#125;        │          │
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
     │     &#123;commandId, text, ended: false&#125;
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
           │     &#123;commandId, error, errorCode&#125;
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
                 &#123;commandId, data, ended: true&#125;
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
     │ 1. 构建传输对象: &#123;type, data, timestamp&#125;
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
WebView JS: window.iFlyCodeBridge.postMessage(&#123;type: "STOP", commandId: "xxx"&#125;)
     │
     ▼
WebViewWindowPanel 收到 JS 消息
     │
     ▼
ChatService.chatStop() / InlineChatCommandService.inlineStop()
     │
     ▼
PluginWebsocketClient.sendWsMessage(
  MessageDto.create(CommandEnum.CHAT_STOP, &#123;chatId, commandId&#125;)
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
| CHAT_MESSAGE | 聊天消息 | `&#123;chatId, messageId, role, content, timestamp&#125;` |
| STREAM_TEXT | 流式增量文本 | `&#123;commandId, text, ended&#125;` |
| INLINE_DIFF | 行内 Diff 展示 | `&#123;editorId, hunks[], original, modified&#125;` |
| CODE_COMPLETE | 代码补全建议 | `&#123;requestId, completions: [&#123;text, range&#125;]&#125;` |
| UNIT_TEST_RESULT | 单测生成结果 | `&#123;file, code, passed, failures&#125;` |
| CODE_CHECK_RESULT | 代码检查结果 | `&#123;file, issues: [&#123;severity, message, range, fix?&#125;]&#125;` |
| CODE_SEARCH_RESULT | 代码搜索结果 | `&#123;results: [&#123;file, line, snippet, score&#125;]&#125;` |
| GIT_REVIEW_RESULT | Git 审查结果 | `&#123;comments, suggestions&#125;` |
| SQL_RESULT | SQL 结果 | `&#123;sql, explanation&#125;` |
| TEMPLATE_RESULT | 模板结果 | `&#123;templateId, content&#125;` |
| USER_INFO | 用户信息 | `&#123;id, name, avatar, quota&#125;` |
| ERROR | 错误信息 | `&#123;code, message, details&#125;` |
| CONNECTION_STATUS | 连接状态 | `&#123;connected, reconnecting&#125;` |
| FEATURE_FLAGS | 功能开关 | `&#123;flags&#125;` |
| CONFIG | 配置信息 | `&#123;config&#125;` |
| CHAT_LIST | 对话列表 | `&#123;chats: [&#123;id, title, timestamp&#125;]&#125;` |
| CHAT_DETAIL | 对话详情 | `&#123;chatId, messages[]&#125;` |
| QUOTA_INFO | 配额信息 | `&#123;used, total, expires&#125;` |
| LOADING | 加载状态 | `&#123;commandId, loading&#125;` |
| PROGRESS | 进度信息 | `&#123;commandId, progress, total, message&#125;` |

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
  browser.callJs("window.iFlyCodeBridge.onMessage('" + JSON.stringify(&#123;
    type: "CHAT_MESSAGE",     // WebViewDataTypeEnum
    data: &#123; ... &#125;,            // 具体数据
    timestamp: 1715673600000  // 时间戳
  &#125;) + "')")

WebView → IDE (postMessage):
  window.iFlyCodeBridge.postMessage(JSON.stringify(&#123;
    type: "CHAT_SEND",        // WebViewResponseTypeEnum
    data: &#123; ... &#125;,            // 请求数据
    requestId: "uuid-xxxx"    // 请求标识
  &#125;))

WebView → IDE (响应回调):
  window.iFlyCodeBridge.onResponse(&#123;
    requestId: "uuid-xxxx",   // 对应请求的 requestId
    success: true,
    data: &#123; ... &#125;
  &#125;)
```
