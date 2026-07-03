## 1. WebView 前端加密逻辑

### 1.1 前端加密: 完全不存在

对 WebView 所有 JS 文件进行穷举搜索，关键词覆盖: `encrypt`, `decrypt`, `crypto`, `RSA`, `AES`, `SM4`, `MD5`, `btoa`, `atob`, `CryptoJS`, `sm-crypto`, `sm2`, `sm3`, `cipher`, `base64`。

**结论: WebView 前端不执行任何加密操作。**

- 无加密库导入 (无 CryptoJS, sm-crypto, forge, etc.)
- 无 `btoa`/`atob` Base64 编码调用
- 无哈希函数调用
- 密码字段 (SQL 数据库连接密码) 以明文通过 JS Bridge 发送
- Git Token 以明文通过 JS Bridge 发送
- 登录流程使用浏览器重定向 (非 WebView 内表单提交), 密码不经过 WebView

### 1.2 前端安全机制

| 机制 | 实现 | 说明 |
|------|------|------|
| HTML 净化 | DOMPurify | 防止 XSS, 用于 Mermaid 图表渲染 |
| 输入验证 | Element UI 表单验证 | 前端表单校验 (非安全目的) |
| 权限控制 | PermissionCodeEnum | UI 展示控制, 非安全强制 |
| 传输安全 | 依赖 JCEF 沙箱 | JS Bridge 通信无加密 |

### 1.3 敏感数据在前端的明文暴露

| 数据类型 | 位置 | 传输方式 | 加密 |
|---------|------|---------|------|
| SQL 数据库密码 | index-3c7ef179.js | `SQL_CHAT:SQL_LINK_TEST` / `SQL_CHAT:SQL_SAVE` | 无 |
| Git 访问令牌 | index-4639cb2d.js | `GIT:SAVE_TOKEN` | 无 |
| 用户聊天输入 | sendMsgMode-8b767ec0.js | `CHAT:SEND_MSG` | 无 |
| AI 生成的代码 | (响应) | `CHAT:GET_CONVERSATION` | 无 |

---

## 2. 八大功能模块完整协议链路

### 2.1 Chat 对话

#### 完整数据流

```
用户输入文本
  │
  ▼
Vue 组件 (ChatInput) ─── sendMsgHandler()
  │  构建 sendMsgData = &#123;
  │    inputText: 用户输入,
  │    type: "TALK:INTELLIGENT" | command,
  │    intelligent: [&#123;type:"command", value:"code_explain"&#125;, ...],
  │    sessionId: 当前会话ID,
  │    knowledge: 知识库引用,
  │    relatedFiles: 相关文件,
  │    code: 选中代码
  │  &#125;
  │
  ▼
sendMsgToIdea("CHAT:SEND_MSG", sendMsgData)
  │
  ▼
ideaUtil: window.myObject.sendMessage(JSON.stringify(&#123;type: "CHAT:SEND_MSG", value: sendMsgData&#125;))
  │
  ▼
Java WebViewWindowPanel.handleRequest(json)
  │  解析 JSON → module="CHAT", command="SEND_MSG"
  │  路由到 ChatService.handleSendMsg()
  │
  ▼
Java ChatService
  │  构建 MessageDto:
  │  &#123;
  │    id: UUID,
  │    command: "talk_intelligent",  // CommandEnum
  │    stream: true,
  │    timeStamp: 毫秒时间戳,
  │    path: 当前文件路径,
  │    lang: 文件语言,
  │    content: 选中代码/文件内容,
  │    sessionId: 会话ID,
  │    modelCode: AI模型代码,
  │    permissionCode: "talk_intelligent",
  │    intelligent: [&#123;type:"command", value:"code_explain"&#125;, ...],
  │    knowledge: 知识库引用,
  │    relatedFiles: 相关文件,
  │    md5: 文件MD5
  │  &#125;
  │
  ▼
PluginWebsocketClient.sendWsMessage(MessageDto, Project)
  │  → AGENT_REQUEST.put(id, message)  // 请求追踪
  │  → new Gson().toJson(message)
  │  → webSocket.send(json)
  │
  ▼
Agent (Node.js) 接收 WebSocket 消息
  │  → ChatService.chat(message, sendConfig, options)
  │  → getSendData(): 构建 API 请求数据
  │    - 合并 baseData (requestId, modelCode, enterpriseId, token, language, ...)
  │    - 合并用户消息 (message, sessionId, intelligent, knowledge, ...)
  │  → _getSSEHandler(): 创建 SSE 流处理器
  │
  ▼
Agent → 星火 API (HTTP SSE)
  │  POST /api/starspark/v1/agent/chat/async/ask
  │  Headers: &#123; access-token: userToken &#125;
  │  Body: &#123; requestId, modelCode, message, sessionId, ... &#125;
  │
  ▼
星火 API 返回 SSE 流
  │  data: &#123;"text": "生成的文本片段1", "ended": false&#125;
  │  data: &#123;"text": "片段2", "ended": false&#125;
  │  ...
  │  data: &#123;"text": "最后片段", "ended": true&#125;
  │
  ▼
Agent SSE Handler 处理每个 chunk
  │  → sendWSMessage(ws, responseData, requestId)
  │  → WebSocket 发送 ResponseStreamDto:
  │    &#123;
  │      id: 原始请求ID,
  │      code: "0",
  │      data: &#123; ended: false, text: "增量文本" &#125;
  │    &#125;
  │
  ▼
Java PluginWebsocketListener.onMessage(json)
  │  → SocketMessageListener.onMessage()
  │  → 按 module="CHAT" 路由到 ChatService.handleMessage()
  │  → 解析 streamStep / streamEnd
  │
  ▼
Java → WebView 推送
  │  WebViewWindowPanel.sendMessage2webView(type, data)
  │  → CefBrowser.executeJavaScript("window.receiveData(&#123;type, value&#125;)")
  │
  ▼
JS handlerReceivedMsg(type, value)
  │  case "CHAT:GET_CONVERSATION" → messageHandler.getConversation(value)
  │  case "CHAT:UPDATE_CONVERSATION_LIST" → messageHandler.updateConversationList(value)
  │
  ▼
Vue Pinia Store (chatStore) 更新
  │  → Vue 响应式系统触发
  │  → UI 重新渲染 (消息气泡、代码块、Mermaid 图表)
```

#### 关键参数

| 参数 | 值 | 说明 |
|------|-----|------|
| CommandEnum | `talk_intelligent` | 智能对话命令 |
| WebSocket 方向 | Java → Agent | 请求 |
| Agent API | `/api/starspark/v1/agent/chat/async/ask` | 异步流式 |
| 响应模式 | SSE (Server-Sent Events) | 流式增量 |
| 加密 | 无 | 明文传输 |

---

### 2.2 CodeCheck 代码检查

#### 完整数据流

```
用户触发代码检查 (点击按钮 / 保存文件)
  │
  ▼
Vue 组件 (CodeCheckView)
  │  sendMsgToIdea("CODE_CHECK:REQUEST_CODE_CHECK_LIST")
  │
  ▼
Java CodeCheckService
  │  → 构建 MessageDto &#123; command: "code_check", ... &#125;
  │  → WebSocket 发送给 Agent
  │
  ▼
Agent ChatService.chat()
  │  → POST /api/starspark/v1/agent/chat/async/ask
  │  (command 映射为 code_check 场景)
  │
  ▼
星火 API 返回检查结果 (SSE)
  │
  ▼
Agent → Java (WebSocket): streamStep 消息
  │
  ▼
Java → WebView: "CODE_CHECK:UPDATE_CODE_CHECK" / "CODE_CHECK:GET_CODE_CHECK_LIST"
  │
  ▼
Vue codeCheckStore 更新 → UI 展示问题列表
```

#### 修复操作

```
用户点击 "修复" 按钮
  → sendMsgToIdea("CODE_CHECK:FIX", &#123; issueId, fixCode &#125;)
  → Java CodeCheckService → Agent → API
  → 返回修复代码 → Java → DiffDialog 展示
  → 用户确认 → 应用代码修改
```

#### 关键参数

| 参数 | 值 | 说明 |
|------|-----|------|
| CommandEnum | `code_check` | 代码检查命令 |
| Agent API | `/api/starspark/v1/agent/chat/async/ask` | 复用 Chat API |
| JS→Java 消息 | `CODE_CHECK:REQUEST_CODE_CHECK_LIST`, `CODE_CHECK:FIX` | |
| Java→JS 消息 | `CODE_CHECK:GET_CODE_CHECK_LIST`, `CODE_CHECK:UPDATE_CODE_CHECK` | |
| 加密 | 无 | 明文传输 |

---

### 2.3 CodeComplete 代码补全

#### 完整数据流

```
用户在编辑器中输入 / 停顿
  │
  ▼
Java EditorManagerService (DocumentListener)
  │  检测停顿时间 > 阈值 (默认 200ms)
  │  或用户按 Alt/Option + \ 手动触发
  │
  ▼
Java CodeCompleteService
  │  → 构建 MessageDto &#123;
  │      command: "code_complete",
  │      path: 当前文件路径,
  │      lang: 文件语言,
  │      content: 文件完整内容,
  │      range: [光标位置],
  │      docChangeCount: 文档修改计数,
  │      md5: 文件内容MD5
  │    &#125;
  │  → WebSocket 发送给 Agent
  │
  ▼
Agent CodeService.codeComplete()
  │  → POST /api/starspark/v1/platform/code/assist
  │  Body: &#123; code, language, cursorPosition, ... &#125;
  │
  ▼
星火 API 返回补全建议 (非流式)
  │  &#123; suggestions: [&#123;text: "补全代码", score: 0.95&#125;, ...] &#125;
  │
  ▼
Agent → Java (WebSocket): ResponseDto
  │
  ▼
Java → Inlay 渲染
  │  PluginEditorInlayHintsProvider 渲染灰色补全提示
  │  用户按 Tab 接受 / Esc 拒绝
  │
  ▼
(无 WebView 参与 — 纯 Java UI 渲染)
```

#### 关键参数

| 参数 | 值 | 说明 |
|------|-----|------|
| CommandEnum | `code_complete` | 代码补全命令 |
| Agent API | `/api/starspark/v1/platform/code/assist` | 补全专用 API |
| 响应模式 | 同步 (非流式) | 一次性返回 |
| WebView | 不参与 | 纯 IntelliJ Inlay 渲染 |
| 加密 | 无 | 明文传输, 含完整文件内容 |

---

### 2.4 InlineChat 行内对话

#### 完整数据流

```
用户选中代码 + 触发 InlineChat (快捷键 / 右键菜单)
  │
  ▼
Java InlineChatCommandService
  │  → InlineChatPanel 显示在编辑器行内
  │  → 用户输入指令 (如 "解释这段代码", "优化", "添加注释")
  │
  ▼
Java InlineChatInputPanel → 用户按 Enter
  │  → 构建 MessageDto &#123;
  │      command: "dialog_edit",
  │      content: 选中代码,
  │      range: 代码范围,
  │      path: 文件路径,
  │      data: &#123; inputText: 用户指令, directName: 分类名 &#125;
  │    &#125;
  │  → WebSocket 发送给 Agent
  │
  ▼
Agent ChatService.chat()
  │  → POST /api/starspark/v1/agent/chat/inline/chat
  │  Body: &#123; message, code, range, ... &#125;
  │
  ▼
星火 API 返回 SSE 流
  │
  ▼
Agent → Java (WebSocket): streamStep 消息
  │
  ▼
Java → InlineChatPanel 更新
  │  → 实时显示 AI 生成的代码
  │  → 生成完成后展示 Accept/Reject/Diff 按钮
  │
  ▼
用户点击 Accept
  │  → Java 应用代码修改到编辑器
  │  → 或展示 DiffDialog 供用户确认
```

#### 关键参数

| 参数 | 值 | 说明 |
|------|-----|------|
| CommandEnum | `dialog_edit` | 行内对话编辑命令 |
| Agent API | `/api/starspark/v1/agent/chat/inline/chat` | 行内对话专用 |
| 响应模式 | SSE 流式 | 实时更新 |
| WebView | 不参与 | 纯 IntelliJ JPanel 渲染 |
| 加密 | 无 | 明文传输, 含选中代码 |

---

### 2.5 SQL SQL助手

#### 完整数据流

```
用户在 SQL 页面输入问题
  │
  ▼
Vue 组件 (SqlChatInput)
  │  构建 sendMsgData = &#123;
  │    intelligent: [&#123;type:"command", value:"sql_generate"&#125;],
  │    params: &#123; sqlInfo: &#123; sourceId, database, tables, ... &#125; &#125;
  │  &#125;
  │  sendMsgToIdea("SQL_CHAT:SEND_MSG", sendMsgData)
  │
  ▼
Java SqlService
  │  → 构建 MessageDto &#123;
  │      command: "sql_generate_talk",
  │      sessionId: SQL会话ID,
  │      data: &#123; sqlInfo: &#123; sourceId, database, ... &#125; &#125;
  │    &#125;
  │  → WebSocket 发送给 Agent
  │
  ▼
Agent SqlService + ChatService
  │  → POST /api/starspark/v1/agent/chat/generateSql (生成)
  │  → POST /api/starspark/v1/agent/chat/optimizeSql (优化)
  │  Headers: &#123; access-token: userToken &#125;
  │
  ▼
星火 API 返回 SSE 流 (SQL 语句 + 解释)
  │
  ▼
Agent → Java (WebSocket): streamStep
  │
  ▼
Java → WebView: "SQL_CHAT:GET_CONVERSATION" / "SQL_CHAT:UPDATE_CONVERSATION_LIST"
  │
  ▼
Vue sqlStore 更新 → UI 展示 SQL 结果
```

#### 数据源管理 (含密码)

```
用户配置数据库连接
  │  填写: host, port, user, password, database
  │
  ▼
sendMsgToIdea("SQL_CHAT:SQL_LINK_TEST", &#123;
  client: 数据库类型,
  host, port, user, password, database
&#125;)
  │  ⚠️ password 明文传输!
  │
  ▼
Java SqlService → WebSocket → Agent SqlService
  │  → Agent 使用 knex.js 建立数据库连接
  │  → 执行测试查询
  │
  ▼
Agent → Java: "SQL_CHAT:RECEIVE_LINK_TEST" (成功/失败)
```

#### 关键参数

| 参数 | 值 | 说明 |
|------|-----|------|
| CommandEnum | `sql_generate_talk` / `sql_optimize_talk` | SQL 生成/优化 |
| Agent API | `/api/starspark/v1/agent/chat/generateSql` | 生成 |
| Agent API | `/api/starspark/v1/agent/chat/optimizeSql` | 优化 |
| JS→Java 消息 | `SQL_CHAT:SEND_MSG`, `SQL_CHAT:SQL_LINK_TEST`, `SQL_CHAT:SQL_SAVE`, `SQL_CHAT:SOURCE_LIST`, `SQL_CHAT:TABLE_LIST`, `SQL_CHAT:SOURCE_DELETE` | |
| Java→JS 消息 | `SQL_CHAT:RECEIVE_SOURCE_TYPES`, `SQL_CHAT:RECEIVE_LINK_TEST`, `SQL_CHAT:RECEIVE_SAVE`, `SQL_CHAT:RECEIVE_SOURCE_LIST`, `SQL_CHAT:RECEIVE_TABLE_LIST`, `SQL_CHAT:GET_CONVERSATION`, `SQL_CHAT:UPDATE_CONVERSATION_LIST` | |
| 加密 | **无** | ⚠️ 数据库密码明文传输 |

---

### 2.6 GitReview Git审查

#### 完整数据流

```
用户触发代码评审 (Git Diff 视图)
  │
  ▼
Vue 组件 (CodeReviewView)
  │  sendMsgToIdea("CODE_REVIEW:PAGE_READY", null)
  │  sendMsgToIdea("CODE_REVIEW:GET_CODEREVIEW_LIST", &#123; path: diff目录 &#125;)
  │
  ▼
Java GitReviewService
  │  → 获取 Git Diff 内容
  │  → 构建 MessageDto &#123;
  │      command: "git_review",
  │      content: diff内容,
  │      path: 文件路径
  │    &#125;
  │  → WebSocket 发送给 Agent
  │
  ▼
Agent ChatService.chat()
  │  → POST /api/starspark/v1/agent/chat/review
  │  Body: &#123; diff, path, ... &#125;
  │
  ▼
星火 API 返回评审结果 (SSE)
  │
  ▼
Agent → Java (WebSocket): streamStep
  │
  ▼
Java → WebView: "CODE_REVIEW:RECEIVER_CODE_REVIEW"
  │
  ▼
Vue 更新 → 展示评审结果列表
```

#### 变更结果查看

```
用户点击查看变更
  → sendMsgToIdea("CODE_REVIEW:GET_CHANGE_RESULT", &#123; filePath, changeId &#125;)
  → Java → Agent → API
  → 返回变更代码
  → Java → WebView: "CODE_REVIEW:RECEIVER_CHANGE_RESULT"
  → 展示 Diff 视图

用户点击结束
  → sendMsgToIdea("CODE_REVIEW:GET_CHANGE_RESULT_END", true)
```

#### 关键参数

| 参数 | 值 | 说明 |
|------|-----|------|
| CommandEnum | `git_review` / `git_diff` | 评审/Diff |
| Agent API | `/api/starspark/v1/agent/chat/review` | 评审专用 |
| JS→Java 消息 | `CODE_REVIEW:PAGE_READY`, `CODE_REVIEW:GET_CODEREVIEW_LIST`, `CODE_REVIEW:GET_CHANGE_RESULT`, `CODE_REVIEW:GET_CHANGE_RESULT_END` | |
| Java→JS 消息 | `CODE_REVIEW:RECEIVER_PAGE_INIT`, `CODE_REVIEW:RECEIVER_CODE_REVIEW`, `CODE_REVIEW:RECEIVER_CHANGE_RESULT` | |
| 加密 | 无 | 明文传输, 含 Git Diff |

---

### 2.7 CodeSearch 代码搜索

#### 完整数据流

```
用户在代码搜索页面输入查询
  │
  ▼
Vue 组件 (CodeSearchView)
  │  sendMsgToIdea("CODE_SEARCH:REQUEST_CODESEARCH_CODE_LIST", &#123; query, repo, language &#125;)
  │
  ▼
Java CodeSearchService
  │  → 构建 MessageDto &#123;
  │      command: "git_search",
  │      data: &#123; query, repo, language &#125;
  │    &#125;
  │  → WebSocket 发送给 Agent
  │
  ▼
Agent RagService
  │  → POST /api/ragserver/v1/code/search (RAG 语义搜索)
  │  或 POST /restapi/ragserver/v1/code/searchInRepo (仓库内搜索)
  │  Headers: &#123; access-token: userToken &#125;
  │
  ▼
RAG 服务返回搜索结果
  │  &#123; results: [&#123; filePath, line, content, score &#125;, ...] &#125;
  │
  ▼
Agent → Java (WebSocket): ResponseDto
  │
  ▼
Java → WebView: "CODE_SEARCH:GET_CODESEARCH_CODE_LIST"
  │
  ▼
Vue 更新 → 展示搜索结果列表
```

#### 仓库/语言列表

```
初始化:
  → sendMsgToIdea("CODE_SEARCH:REQUEST_CODESEARCH_REPOSITORY_LIST")
  → Java → Agent → POST /api/ragserver/v1/code/getUserRepos
  → 返回 → "CODE_SEARCH:GET_CODESEARCH_REPOSITORY_LIST"

  → sendMsgToIdea("CODE_SEARCH:REQUEST_CODESEARCH_LANGUAGE_LIST")
  → Java → Agent → POST /api/ragserver/v1/code/getLanguages
  → 返回 → "CODE_SEARCH:GET_CODESEARCH_LANGUAGE_LIST"
```

#### 关键参数

| 参数 | 值 | 说明 |
|------|-----|------|
| CommandEnum | `git_search` | 代码搜索命令 |
| Agent API | `/api/ragserver/v1/code/search` | RAG 语义搜索 |
| Agent API | `/restapi/ragserver/v1/code/searchInRepo` | 仓库内搜索 |
| JS→Java 消息 | `CODE_SEARCH:REQUEST_CODESEARCH_CODE_LIST`, `CODE_SEARCH:REQUEST_CODESEARCH_REPOSITORY_LIST`, `CODE_SEARCH:REQUEST_CODESEARCH_LANGUAGE_LIST` | |
| Java→JS 消息 | `CODE_SEARCH:GET_CODESEARCH_CODE_LIST`, `CODE_SEARCH:GET_CODESEARCH_REPOSITORY_LIST`, `CODE_SEARCH:GET_CODESEARCH_LANGUAGE_LIST` | |
| 加密 | 无 | 明文传输 |

---

### 2.8 UnitTest 单元测试

#### 完整数据流

```
用户触发单元测试生成 (右键菜单 / 快捷键)
  │
  ▼
Vue 组件 (UnitTestView)
  │  sendMsgToIdea("UNIT_TEST:PAGE_READY")
  │  sendMsgToIdea("UNIT_TEST:FUNCTION_CASE", &#123; filePath, className, methodName &#125;)
  │
  ▼
Java UnitTestService
  │  → 构建 MessageDto &#123;
  │      command: "code_test",
  │      path: 文件路径,
  │      content: 文件内容,
  │      range: 方法范围
  │    &#125;
  │  → WebSocket 发送给 Agent
  │
  ▼
Agent TestService
  │  → POST /api/starspark/v1/agent/code/generateUnitTest (生成测试代码)
  │  → POST /api/starspark/v1/agent/code/generateUnitTestCaseTemplate (生成测试模板)
  │  Headers: &#123; access-token: userToken &#125;
  │
  ▼
星火 API 返回测试代码 (SSE)
  │
  ▼
Agent → Java (WebSocket): streamStep
  │
  ▼
Java → WebView: "UNIT_TEST:GET_UT_INFO" / "UNIT_TEST:GET_METHOD_CASE"
  │
  ▼
Vue 更新 → 展示生成的测试代码
```

#### 测试代码保存

```
用户点击保存
  → sendMsgToIdea("UNIT_TEST:SAVE_CODE", &#123; filePath, testCode &#125;)
  → Java → 创建/写入测试文件到项目
```

#### 批量单元测试

```
用户触发批量测试
  → sendMsgToIdea("BATCH_UNIT_TEST:CREATE", &#123; filePaths, options &#125;)
  → Java → Agent → POST /restapi/unit/v1/createUnitTask
  → 异步生成, 轮询状态:
    → sendMsgToIdea("BATCH_UNIT_TEST:GET_LIST")
    → "BATCH_UNIT_TEST:GET_TASK_LIST" / "BATCH_UNIT_TEST:MESSAGE"
  → 下载:
    → sendMsgToIdea("BATCH_UNIT_TEST:DOWNLOAD", taskId)
    → Agent → /restapi/unit/v1/exportByTaskId
```

#### 关键参数

| 参数 | 值 | 说明 |
|------|-----|------|
| CommandEnum | `code_test` / `code_test_template` | 单测生成 |
| Agent API | `/api/starspark/v1/agent/code/generateUnitTest` | 生成测试代码 |
| Agent API | `/api/starspark/v1/agent/code/generateUnitTestCaseTemplate` | 生成测试模板 |
| Agent API | `/restapi/unit/v1/createUnitTask` | 批量单测 |
| JS→Java 消息 | `UNIT_TEST:PAGE_READY`, `UNIT_TEST:FUNCTION_CASE`, `UNIT_TEST:FUNCTION_CASE_CODE`, `UNIT_TEST:SAVE_CODE`, `UNIT_TEST:REGENERATE`, `UNIT_TESTING:MAPPING_FILE` | |
| Java→JS 消息 | `UNIT_TEST:GET_UT_INFO`, `UNIT_TEST:GET_METHOD_CASE`, `UNIT_TEST:GET_CASE_CODE`, `UNIT_TEST:GET_ALL_CODE_FILE`, `UNIT_TEST:FUNCTION_LIST`, `UNIT_TEST:RECEIVE_FUNCTION_CASE`, `UNIT_TEST:RECEIVE_FUNCTION_CASE_CODE` | |
| 加密 | 无 | 明文传输 |

---
