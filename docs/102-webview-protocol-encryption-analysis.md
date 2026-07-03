# iFlyCode WebView 协议与加密交互完整分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-14 | 文档编号: 102

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
  │  构建 sendMsgData = {
  │    inputText: 用户输入,
  │    type: "TALK:INTELLIGENT" | command,
  │    intelligent: [{type:"command", value:"code_explain"}, ...],
  │    sessionId: 当前会话ID,
  │    knowledge: 知识库引用,
  │    relatedFiles: 相关文件,
  │    code: 选中代码
  │  }
  │
  ▼
sendMsgToIdea("CHAT:SEND_MSG", sendMsgData)
  │
  ▼
ideaUtil: window.myObject.sendMessage(JSON.stringify({type: "CHAT:SEND_MSG", value: sendMsgData}))
  │
  ▼
Java WebViewWindowPanel.handleRequest(json)
  │  解析 JSON → module="CHAT", command="SEND_MSG"
  │  路由到 ChatService.handleSendMsg()
  │
  ▼
Java ChatService
  │  构建 MessageDto:
  │  {
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
  │    intelligent: [{type:"command", value:"code_explain"}, ...],
  │    knowledge: 知识库引用,
  │    relatedFiles: 相关文件,
  │    md5: 文件MD5
  │  }
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
  │  Headers: { access-token: userToken }
  │  Body: { requestId, modelCode, message, sessionId, ... }
  │
  ▼
星火 API 返回 SSE 流
  │  data: {"text": "生成的文本片段1", "ended": false}
  │  data: {"text": "片段2", "ended": false}
  │  ...
  │  data: {"text": "最后片段", "ended": true}
  │
  ▼
Agent SSE Handler 处理每个 chunk
  │  → sendWSMessage(ws, responseData, requestId)
  │  → WebSocket 发送 ResponseStreamDto:
  │    {
  │      id: 原始请求ID,
  │      code: "0",
  │      data: { ended: false, text: "增量文本" }
  │    }
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
  │  → CefBrowser.executeJavaScript("window.receiveData({type, value})")
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
  │  → 构建 MessageDto { command: "code_check", ... }
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
  → sendMsgToIdea("CODE_CHECK:FIX", { issueId, fixCode })
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
  │  → 构建 MessageDto {
  │      command: "code_complete",
  │      path: 当前文件路径,
  │      lang: 文件语言,
  │      content: 文件完整内容,
  │      range: [光标位置],
  │      docChangeCount: 文档修改计数,
  │      md5: 文件内容MD5
  │    }
  │  → WebSocket 发送给 Agent
  │
  ▼
Agent CodeService.codeComplete()
  │  → POST /api/starspark/v1/platform/code/assist
  │  Body: { code, language, cursorPosition, ... }
  │
  ▼
星火 API 返回补全建议 (非流式)
  │  { suggestions: [{text: "补全代码", score: 0.95}, ...] }
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
  │  → 构建 MessageDto {
  │      command: "dialog_edit",
  │      content: 选中代码,
  │      range: 代码范围,
  │      path: 文件路径,
  │      data: { inputText: 用户指令, directName: 分类名 }
  │    }
  │  → WebSocket 发送给 Agent
  │
  ▼
Agent ChatService.chat()
  │  → POST /api/starspark/v1/agent/chat/inline/chat
  │  Body: { message, code, range, ... }
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
  │  构建 sendMsgData = {
  │    intelligent: [{type:"command", value:"sql_generate"}],
  │    params: { sqlInfo: { sourceId, database, tables, ... } }
  │  }
  │  sendMsgToIdea("SQL_CHAT:SEND_MSG", sendMsgData)
  │
  ▼
Java SqlService
  │  → 构建 MessageDto {
  │      command: "sql_generate_talk",
  │      sessionId: SQL会话ID,
  │      data: { sqlInfo: { sourceId, database, ... } }
  │    }
  │  → WebSocket 发送给 Agent
  │
  ▼
Agent SqlService + ChatService
  │  → POST /api/starspark/v1/agent/chat/generateSql (生成)
  │  → POST /api/starspark/v1/agent/chat/optimizeSql (优化)
  │  Headers: { access-token: userToken }
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
sendMsgToIdea("SQL_CHAT:SQL_LINK_TEST", {
  client: 数据库类型,
  host, port, user, password, database
})
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
  │  sendMsgToIdea("CODE_REVIEW:GET_CODEREVIEW_LIST", { path: diff目录 })
  │
  ▼
Java GitReviewService
  │  → 获取 Git Diff 内容
  │  → 构建 MessageDto {
  │      command: "git_review",
  │      content: diff内容,
  │      path: 文件路径
  │    }
  │  → WebSocket 发送给 Agent
  │
  ▼
Agent ChatService.chat()
  │  → POST /api/starspark/v1/agent/chat/review
  │  Body: { diff, path, ... }
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
  → sendMsgToIdea("CODE_REVIEW:GET_CHANGE_RESULT", { filePath, changeId })
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
  │  sendMsgToIdea("CODE_SEARCH:REQUEST_CODESEARCH_CODE_LIST", { query, repo, language })
  │
  ▼
Java CodeSearchService
  │  → 构建 MessageDto {
  │      command: "git_search",
  │      data: { query, repo, language }
  │    }
  │  → WebSocket 发送给 Agent
  │
  ▼
Agent RagService
  │  → POST /api/ragserver/v1/code/search (RAG 语义搜索)
  │  或 POST /restapi/ragserver/v1/code/searchInRepo (仓库内搜索)
  │  Headers: { access-token: userToken }
  │
  ▼
RAG 服务返回搜索结果
  │  { results: [{ filePath, line, content, score }, ...] }
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
  │  sendMsgToIdea("UNIT_TEST:FUNCTION_CASE", { filePath, className, methodName })
  │
  ▼
Java UnitTestService
  │  → 构建 MessageDto {
  │      command: "code_test",
  │      path: 文件路径,
  │      content: 文件内容,
  │      range: 方法范围
  │    }
  │  → WebSocket 发送给 Agent
  │
  ▼
Agent TestService
  │  → POST /api/starspark/v1/agent/code/generateUnitTest (生成测试代码)
  │  → POST /api/starspark/v1/agent/code/generateUnitTestCaseTemplate (生成测试模板)
  │  Headers: { access-token: userToken }
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
  → sendMsgToIdea("UNIT_TEST:SAVE_CODE", { filePath, testCode })
  → Java → 创建/写入测试文件到项目
```

#### 批量单元测试

```
用户触发批量测试
  → sendMsgToIdea("BATCH_UNIT_TEST:CREATE", { filePaths, options })
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

## 3. 端到端数据流图

### 3.1 完整协议栈

```
┌─────────────────────────────────────────────────────────────────────┐
│                        用户交互层                                    │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐           │
│  │ WebView  │  │ Editor   │  │ Inline   │  │ Inlay    │           │
│  │ (JCEF)  │  │ (IntelliJ)│  │ Chat    │  │ Hints   │           │
│  └────┬─────┘  └────┬─────┘  └────┬─────┘  └────┬─────┘           │
│       │              │              │              │                 │
│  ┌────▼──────────────▼──────────────▼──────────────▼────┐          │
│  │              JS Bridge / Java API                      │          │
│  │  ideaUtil: window.myObject.sendMessage()              │          │
│  │  Java: WebViewWindowPanel.handleRequest()             │          │
│  └────────────────────┬──────────────────────────────────┘          │
│                       │                                              │
│  ┌────────────────────▼──────────────────────────────────┐          │
│  │              Java Service Layer                        │          │
│  │  ChatService, CodeCheckService, SqlService,            │          │
│  │  GitReviewService, CodeSearchService, UnitTestService, │          │
│  │  CodeCompleteService, InlineChatCommandService         │          │
│  └────────────────────┬──────────────────────────────────┘          │
│                       │                                              │
│  ┌────────────────────▼──────────────────────────────────┐          │
│  │              WebSocket Client (OkHttp)                 │          │
│  │  ws://127.0.0.1:{port}/ws/idea                        │          │
│  │  MessageDto → JSON → webSocket.send()                 │          │
│  └────────────────────┬──────────────────────────────────┘          │
└───────────────────────┼──────────────────────────────────────────────┘
                        │ localhost WebSocket (无 TLS, 无认证)
┌───────────────────────▼──────────────────────────────────────────────┐
│                    Agent (Node.js)                                   │
│  ┌────────────────────▼──────────────────────────────────┐          │
│  │              WebSocket Server (ws)                     │          │
│  │  解析 JSON → 按 command 路由到 Service                │          │
│  └────────────────────┬──────────────────────────────────┘          │
│                       │                                              │
│  ┌────────────────────▼──────────────────────────────────┐          │
│  │              Agent Service Layer                       │          │
│  │  ChatService, SqlService, TestService,                 │          │
│  │  RagService, GitService, LoginService, CodeService     │          │
│  └────────────────────┬──────────────────────────────────┘          │
│                       │                                              │
│  ┌────────────────────▼──────────────────────────────────┐          │
│  │              HTTP Client (axios/fetch)                 │          │
│  │  星火 API, RAG 服务, 用户中心                          │          │
│  │  Headers: { access-token: userToken }                  │          │
│  └────────────────────┬──────────────────────────────────┘          │
└───────────────────────┼──────────────────────────────────────────────┘
                        │ HTTPS (TLS 1.2+)
┌───────────────────────▼──────────────────────────────────────────────┐
│                    云端服务                                          │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐           │
│  │ 星火 API │  │ RAG 服务 │  │ 用户中心 │  │ 单测服务 │           │
│  │ (SSE)   │  │ (REST)  │  │ (REST)  │  │ (REST)  │           │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘           │
└─────────────────────────────────────────────────────────────────────┘
```

### 3.2 加密在协议栈中的位置

```
层              │ 加密状态          │ 说明
────────────────┼───────────────────┼──────────────────────────────
WebView ↔ Java  │ 无加密 (明文)     │ JS Bridge: JSON.stringify
Java ↔ Agent    │ 无加密 (明文)     │ WebSocket ws:// (非 wss://)
Agent ↔ 云端    │ TLS 1.2+ (HTTPS) │ 星火 API, RAG, 用户中心
Agent 内部      │ SM2/SM4/RSA/AES  │ 仅用于登录密码加密和权限缓存
WebView 内部    │ 无加密            │ 前端不执行任何加密操作
```

### 3.3 Agent 端加密套件完整定义

```javascript
// 统一加密接口
function encrypt(data, algorithm, ...args) {
  switch (algorithm) {
    case "SM2": return encryptSM2(data, ...args);  // 国密 SM2 非对称加密
    case "SM4": return encryptSM4(data, ...args);  // 国密 SM4 对称加密
    case "RSA": return encryptRSA(data, ...args);  // RSA 非对称加密
    case "AES": return encryptAES(data, ...args);  // AES-256-CTR 对称加密
    case "MD5": return cryptoMd5(data);            // MD5 哈希
    default:   return data;
  }
}

function decrypt(data, algorithm, ...args) {
  switch (algorithm) {
    case "SM4": return decryptSM4(data, ...args);
    case "AES": return decryptAES(data, ...args);
    default:   return data;
  }
}
```

#### 各算法实现细节

| 算法 | 用途 | 密钥 | 模式 | 密钥来源 |
|------|------|------|------|---------|
| **SM2** | 数据加密传输 | `SM2_PUB_KEY = "已脱敏"` | C1C3C2, mode=1 | 硬编码 |
| **SM4** | 权限缓存加密 | `SM4_KEY = "已脱敏"` | PKCS#5 padding | 硬编码 |
| **RSA** | 登录密码加密 | `RSA_PUB_KEY = "已脱敏"` (1024-bit) | PKCS1 padding, 64-byte 分块 | 硬编码 |
| **AES** | 通用数据加密 | `AES_KEY = "已脱敏"` | AES-256-CTR | 硬编码 |
| **AES IV** | CTR 初始向量 | `AES_IV = "已脱敏"` | 固定 IV | 硬编码 |
| **MD5** | 文件完整性校验 | N/A | crypto.createHash("md5") | N/A |

#### 加密使用场景

| 场景 | 算法 | 调用位置 | 数据 |
|------|------|---------|------|
| 账号密码登录 | RSA | LoginService.loginByForm() | 用户密码 |
| 权限缓存 | SM4 | 权限数据存储/读取 | 权限列表 |
| 数据传输加密 | SM2 | 部分数据发送 | 敏感数据 |
| 通用加密 | AES | 内部数据加密 | 通用数据 |
| 文件完整性 | MD5 | MessageDto.md5 | 文件内容哈希 |

---

## 4. WebView→Java JS Bridge 消息格式

### 4.1 消息发送机制 (三种 IDE)

#### IDEA (JCEF)

```javascript
// JS → Java
window.myObject.sendMessage(JSON.stringify({ type, value }))

// Java → JS
CefBrowser.executeJavaScript("window.receiveData(" + json + ")")
```

#### VSCode

```javascript
// JS → Java
vscode.postMessage({ type, value: JSON.stringify(value) })

// Java → JS
window.addEventListener("message", (event) => {
  handlerReceivedMsg(event.data.type, event.data.value)
})
```

#### Eclipse

```javascript
// JS → Java
window.sendMessage(JSON.stringify({ type, value: JSON.stringify(value) }))

// Java → JS
window.receiveData = function(data) {
  handlerReceivedMsg(JSON.parse(data).type, JSON.parse(data).value)
}
```

### 4.2 JS→Java 消息完整映射表

从 WebView 源码提取的所有 JS→Java 消息类型:

#### CHAT 模块 (14 条)

| 消息类型 | value 格式 | 敏感数据 | 文件来源 |
|---------|-----------|---------|---------|
| `CHAT:SEND_MSG` | `{inputText, type, intelligent, sessionId, knowledge, relatedFiles, code}` | 用户输入, 代码 | sendMsgMode |
| `CHAT:RESEND` | `{id, sessionId, type}` | 无 | sendMsgMode |
| `CHAT:STOP_RESPONSE` | `{sessionId}` | 无 | sendMsgMode |
| `CHAT:SET_MODEL` | `modelCode` | 无 | sendMsgMode |
| `CHAT:REFRESH_MODEL` | 无 | 无 | sendMsgMode |
| `CHAT:DELETE_MSG` | `{messageId}` | 无 | sendMsgMode |
| `CHAT:DELETE_HISTORY_ITEM` | `{sessionId}` | 无 | index-f0296668 |
| `CHAT:DELETE_HISTORY_ITEM_ALL` | `{}` | 无 | index-f0296668 |
| `CHAT:GET_HISTORY_LIST` | 无 | 无 | index-f0296668 |
| `CHAT:CHOOSE_HISTORY_ITEM` | `sessionId` | 无 | index-df569310 |
| `CHAT:NEW_CHAT` | 无 | 无 | index-f0296668 |
| `CHAT:GET_IDE_FILE_STATE` | `{isRecommend?, isGetData?}` | 无 | sendMsgMode |
| `CHAT:GET_CODE_KNOWLEDGE_LIST` | 无 | 无 | sendMsgMode |
| `CHAT:GET_DOC_KNOWLEDGE_LIST` | 无 | 无 | sendMsgMode |
| `CHAT:CHOOSE_FILE` | 无 | 无 | sendMsgMode |
| `CHAT:GET_OPEN_DIR_LIST` | 无 | 无 | sendMsgMode |
| `CHAT:VALID_WEBSITE` | `url` | URL | sendMsgMode |
| `CHAT:AGENT_REFRESH` | 无 | 无 | index-df569310 |

#### SQL_CHAT 模块 (8 条)

| 消息类型 | value 格式 | 敏感数据 | 文件来源 |
|---------|-----------|---------|---------|
| `SQL_CHAT:SEND_MSG` | `{intelligent, params: {sqlInfo}}` | SQL 查询 | sendMsgMode |
| `SQL_CHAT:SQL_LINK_TEST` | `{client, host, port, user, password, database}` | **数据库密码** | index-3c7ef179 |
| `SQL_CHAT:SQL_SAVE` | `{sourceId, client, host, port, user, password, database}` | **数据库密码** | index-3c7ef179 |
| `SQL_CHAT:SOURCE_LIST` | `{sourceId?, refreshFlag?}` | 无 | index-3c7ef179 |
| `SQL_CHAT:TABLE_LIST` | `{sourceId, database}` | 无 | index-3c7ef179 |
| `SQL_CHAT:REQUEST_SOURCE_TYPES` | 无 | 无 | index-3c7ef179 |
| `SQL_CHAT:SOURCE_DELETE` | `sourceId` | 无 | index-3c7ef179 |
| `SQL_CHAT:NEW_CHAT` | 无 | 无 | index-3c7ef179 |
| `SQL_CHAT:STOP_RESPONSE` | 无 | 无 | sendMsgMode |

#### CODE_CHECK 模块 (2 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `CODE_CHECK:REQUEST_CODE_CHECK_LIST` | 无 | 无 |
| `CODE_CHECK:FIX` | `{issueId, fixCode}` | 修复代码 |

#### CODE_REVIEW 模块 (4 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `CODE_REVIEW:PAGE_READY` | 无 | 无 |
| `CODE_REVIEW:GET_CODEREVIEW_LIST` | `{path}` | 无 |
| `CODE_REVIEW:GET_CHANGE_RESULT` | `{filePath, changeId}` | 无 |
| `CODE_REVIEW:GET_CHANGE_RESULT_END` | `true` | 无 |

#### CODE_SEARCH 模块 (3 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `CODE_SEARCH:REQUEST_CODESEARCH_CODE_LIST` | `{query, repo, language}` | 无 |
| `CODE_SEARCH:REQUEST_CODESEARCH_REPOSITORY_LIST` | 无 | 无 |
| `CODE_SEARCH:REQUEST_CODESEARCH_LANGUAGE_LIST` | 无 | 无 |

#### UNIT_TEST 模块 (7 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `UNIT_TEST:PAGE_READY` | 无 | 无 |
| `UNIT_TEST:FUNCTION_CASE` | `{filePath, className, methodName}` | 无 |
| `UNIT_TEST:FUNCTION_CASE_CODE` | `{caseId}` | 无 |
| `UNIT_TEST:SAVE_CODE` | `{filePath, testCode}` | 测试代码 |
| `UNIT_TEST:REGENERATE` | `{params}` | 无 |
| `UNIT_TESTING:MAPPING_FILE` | `{params}` | 无 |
| `UNIT_TEST:SAVE` | `{params}` | 无 |

#### BATCH_UNIT_TEST 模块 (4 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `BATCH_UNIT_TEST:CREATE` | `{filePaths, options}` | 无 |
| `BATCH_UNIT_TEST:GET_LIST` | 无 | 无 |
| `BATCH_UNIT_TEST:DOWNLOAD` | `taskId` | 无 |
| `BATCH_UNIT_TEST:DELETE` | `taskId` | 无 |

#### UNIT_TEST_BANK 模块 (2 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `UNIT_TEST_BANK:PAGE_READY` | 无 | 无 |
| `UNIT_TEST_BANK:SAVE` | `{params}` | 无 |

#### GIT 模块 (4 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `GIT:GET_STATUS` | `{}` | 无 |
| `GIT:SAVE_TOKEN` | `{token, repoType}` | **Git Token** |
| `GIT:AUTHORIZE` | `{token, repoType}` | **Git Token** |
| `GIT:RE_INDEX` | `{params}` | 无 |

#### LOGIN 模块 (4 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `LOGIN:INIT` | `{showInfo: true}` | 无 |
| `LOGIN:LOGIN_ABORT` | 无 | 无 |
| `LOGIN:LOGIN_CHECK` | 无 | 无 |
| `LOGIN:LOGOUT` | 无 | 无 |
| `LOGIN:CLOSE_QR_CODE` | 无 | 无 |

#### SETTING 模块 (4 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `SETTING:UPDATE_CONFIG` | `{key, value}` | 可能含配置 |
| `SETTING:GET_CAN_OPEN_CODE_ENHANCE` | 无 | 无 |
| `SETTING:SAVE_SHOW_OPERATE_GUIDANCE` | `{isShowOperateGuide}` | 无 |
| `SETTING:POPUP_KEYMAP_SETTINGS` | 无 | 无 |

#### COMMON 模块 (8 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `COMMON:PAGE_READY` | 无 | 无 |
| `COMMON:OPEN_URL` | `{url}` | URL |
| `COMMON:FOCUS_FILE` | `{filePath, line}` | 无 |
| `COMMON:FOCUS_FILE_LINE` | `{filePath, line}` | 无 |
| `COMMON:CODE_CLICK_ACTION` | `{type, content}` | 代码内容 |
| `COMMON:OPEN_FILE_DIALOG` | `{params}` | 无 |
| `COMMON:EVALUATION` | `{messageId, type}` | 无 |
| `COMMON:FEEDBACK` | `{messageId, type, content}` | 无 |
| `COMMON:DOWNLOAD_TABLE` | `{params}` | 无 |

---

## 5. Java→WebView 回调格式

### 5.1 回调发送机制

```java
// Java 端
void sendMessage2webView(String type, Object data) {
    String json = new Gson().toJson(Map.of("type", type, "data", data));
    browser.getCefBrowser().executeJavaScript(
        "window.receiveData(" + json + ")",
        browser.getCefBrowser().getURL(),
        0
    );
}
```

### 5.2 Java→JS 消息完整映射表

从 `handlerReceivedMsg()` switch/case 提取的所有 Java→JS 消息类型:

| 消息类型 | 处理方法 | 数据内容 | 含加密数据 |
|---------|---------|---------|-----------|
| `CHAT:AGENT_ERROR` | hanldeAgentError | `{error, message}` | 无 |
| `CHAT:GET_USER_INFO` | receiveUserInfo | `{userName, avatar, ...}` | 无 |
| `CHAT:GET_CONVERSATION` | getConversation | `{messages: [...]}` | 无 |
| `CHAT:UPDATE_CONVERSATION_LIST` | updateConversationList | `{conversation}` | 无 |
| `CHAT:UPDATE_SELECT_CODE` | updateSelectCode | `{code, range}` | 无 |
| `CHAT:SET_SEND_MESSAGE_TYPE` | setSendMessageType | `{type}` | 无 |
| `CHAT:GET_MODEL_LIST` | getModelList | `{models: [...]}` | 无 |
| `CHAT:PREDICT` | updatePredictList | `{predictions}` | 无 |
| `CHAT:RECEIVER_IDE_FILE_STATE` | getIdeFileState | `{files, isRecommend}` | 无 |
| `CHAT:RECEIVER_DOC_KNOWLEDGE_LIST` | getDocKnowledgeInfo | `{documents}` | 无 |
| `CHAT:RECEIVER_CODE_KNOWLEDGE_LIST` | getCodeKnowledgeInfo | `{repositories}` | 无 |
| `CHAT:SEND_OPEN_DIR_LIST` | getOpenDirListInfo | `{dirs}` | 无 |
| `CHAT:RECEIVER_HISTORY_LIST` | receiveHistoryList | `{conversations}` | 无 |
| `CHAT:GET_FEEDBACK_LIST` | receiveFeedBackCheckList | `{categories}` | 无 |
| `CHAT:CHOOSE_FILE` | receiveUploadFile | `{filePath}` | 无 |
| `CHAT:SEND_VALID_WEBSITE_RESULT` | getValidWebsiteResult | `{isValid, url}` | 无 |
| `CHAT_TALK:RECEIVER_RECOMMEND_GAMEPLAY` | receiveRecommendList | `{recommendations}` | 无 |
| `LOGIN:RECEIVER_LOGIN_IFRAME_SRC` | receiveLoginIframeSrc | `{url}` | 无 |
| `LOGIN:LOGIN_SUCCEED` | receiveLoginSuccess | `{token?, userInfo?}` | **可能含 Token** |
| `LOGIN:GO_LOGIN` | goLoginClickPage | 无 | 无 |
| `SETTING:GET_CONFIG` | getSettingInfo | `{settings}` | 无 |
| `SETTING:CHANGE_THEME` | changeTheme | `{theme}` | 无 |
| `SETTING:GET_CAN_OPEN_CODE_ENHANCE` | changeCodeEnhanceEnabled | `{enabled}` | 无 |
| `SETTING:RECEIVE_REPO_STATUS` | receiveRepoStatus | `{status}` | 无 |
| `SETTING:SEND_SHOW_OPERATE_GUIDANCE` | receiveOperateGuideData | `{isShow}` | 无 |
| `COMMON:OPEN_PAGE` | openPage | `{page}` | 无 |
| `COMMON:SHOW_MESSAGE_IN_WEB` | showMessageInWeb | `{message}` | 无 |
| `COMMON:PLUGIN_BASE_INFO` | getPluginBaseInfo | `{version, ...}` | 无 |
| `CODE_CHECK:GET_CODE_CHECK_LIST` | getCodeCheckList | `{issues}` | 无 |
| `CODE_CHECK:UPDATE_CODE_CHECK` | updateCodeCheckList | `{issue}` | 无 |
| `CODE_REVIEW:RECEIVER_PAGE_INIT` | receiveCodeReviewInit | `{initData}` | 无 |
| `CODE_REVIEW:RECEIVER_CODE_REVIEW` | receiveCodeReview | `{review}` | 无 |
| `CODE_REVIEW:RECEIVER_CHANGE_RESULT` | receiveChangeResult | `{change}` | 无 |
| `CODE_SEARCH:GET_CODESEARCH_CODE_LIST` | getCodeSearchCodeList | `{results}` | 无 |
| `CODE_SEARCH:GET_CODESEARCH_REPOSITORY_LIST` | getCodeSearchRepositoryList | `{repos}` | 无 |
| `CODE_SEARCH:GET_CODESEARCH_LANGUAGE_LIST` | getCodeSearchLanguageList | `{languages}` | 无 |
| `SQL_CHAT:RECEIVE_SOURCE_TYPES` | sqlReceiveSourceTypes | `{types}` | 无 |
| `SQL_CHAT:RECEIVE_LINK_TEST` | sqlReceiveLinkTest | `{success, message}` | 无 |
| `SQL_CHAT:RECEIVE_SAVE` | sqlReceiveSave | `{success}` | 无 |
| `SQL_CHAT:SOURCE_REFRESH_SAVE` | sqlSourceRefreshReceiveSave | `{success}` | 无 |
| `SQL_CHAT:RECEIVE_SOURCE_LIST` | sqlReceiveSourceList | `{sources}` | **含连接信息** |
| `SQL_CHAT:RECEIVE_TABLE_LIST` | sqlReceiveTableList | `{tables}` | 无 |
| `SQL_CHAT:GET_CONVERSATION` | sqlGetConversation | `{messages}` | 无 |
| `SQL_CHAT:UPDATE_CONVERSATION_LIST` | sqlUpdateConversationList | `{conversation}` | 无 |
| `UNIT_TEST:GET_UT_INFO` | addUTContent | `{testInfo}` | 无 |
| `UNIT_TEST:GET_METHOD_CASE` | receiveClassCaseData | `{cases}` | 无 |
| `UNIT_TEST:GET_CASE_CODE` | receiveCaseCode | `{code}` | 无 |
| `UNIT_TEST:GET_ALL_CODE_FILE` | receiveSaveMessage | `{files}` | 无 |
| `UNIT_TEST:FUNCTION_LIST` | unitTestFunctionList | `{functions}` | 无 |
| `UNIT_TEST:RECEIVE_FUNCTION_CASE` | unitTestFunctionCase | `{case}` | 无 |
| `UNIT_TEST:RECEIVE_FUNCTION_CASE_CODE` | unitTestFunctionCode | `{code}` | 无 |
| `BATCH_UNIT_TEST:GET_TASK_LIST` | getMultiTestTaskList | `{tasks}` | 无 |
| `BATCH_UNIT_TEST:MESSAGE` | multiTestMessage | `{message}` | 无 |
| `BATCH_UNIT_TEST:REFRESH_TASK_DOWNLOAD_STATUS` | multiTestRefreshTaskDownloadStatus | `{status}` | 无 |
| `UNIT_TEST_BANK:RECEIVE_FUNCTION` | receiveTestBankFunctionData | `{functions}` | 无 |
| `UNIT_TEST_BANK:RECEIVE_DATA` | receiveTestBankData | `{data}` | 无 |
| `UNIT_TEST_BANK:RESPONSE_SAVE` | receiveTestBankSave | `{success}` | 无 |
| `UNIT_TEST_BANK:IDEA_STOP` | stopUnitTestBank | 无 | 无 |
| `USER:PERMISSION_LIST` | getPermissionCodeList | `{permissions}` | 无 |
| `GIT:STATUS` | updateGitStatusList | `{status}` | 无 |

**结论: 所有 Java→JS 回调均不包含加密数据。** 数据以明文 JSON 推送到 WebView。

---

## 6. 协议安全评估

### 6.1 数据传输加密状态总览

| 链路 | 协议 | 加密 | 认证 | 风险 |
|------|------|------|------|------|
| WebView → Java | JS Bridge (JCEF) | 无 | 无 | 本地沙箱, 低风险 |
| Java → Agent | WebSocket ws:// | 无 | 无 | **高风险**: 本机进程可嗅探 |
| Agent → 云端 | HTTPS | TLS 1.2+ | access-token | 安全 |
| Agent 内部 | 函数调用 | SM2/SM4/RSA/AES | 硬编码密钥 | 密钥不可轮换 |

### 6.2 明文传输的敏感数据

| 数据 | 传输路径 | 风险等级 | 说明 |
|------|---------|---------|------|
| SQL 数据库密码 | WebView → Java → Agent | **高** | 明文经 JS Bridge 和 WebSocket |
| Git 访问令牌 | WebView → Java → Agent | **高** | 明文经 JS Bridge 和 WebSocket |
| 用户聊天内容 | WebView → Java → Agent → 云端 | **中** | 含代码、业务逻辑 |
| AI 生成代码 | 云端 → Agent → Java → WebView | **中** | 可能含敏感实现 |
| 文件完整内容 | Java → Agent (代码补全) | **中** | 每次补全发送完整文件 |
| 认证 Token | Agent → 云端 (HTTP header) | **低** | 仅在 HTTPS 通道 |
| 登录密码 | WebView → (浏览器) → 云端 | **低** | 浏览器重定向, 不经 WebView |

### 6.3 加密强度评估

| 算法 | 密钥强度 | 评估 | 问题 |
|------|---------|------|------|
| RSA-1024 | 1024 bit | **弱** | 2010年768-bit已分解, 1024-bit可分解 |
| SM2 | 256 bit (等效) | **中** | 算法安全, 但密钥硬编码 |
| SM4 | 128 bit | **中** | 算法安全, 但密钥硬编码 |
| AES-256-CTR | 256 bit | **弱** | CTR模式+固定IV=流密码重用 |
| MD5 | 128 bit | **弱** | 已知碰撞攻击 |

### 6.4 攻击面分析

#### 攻击面 1: WebSocket 嗅探 (高风险)

```
攻击条件: 本机恶意进程
攻击路径: 连接 ws://127.0.0.1:{port}/ws/idea
可获取数据: 所有用户输入、AI响应、SQL密码、Git Token
缓解措施: WebSocket 无认证, 端口可预测
```

#### 攻击面 2: JS Bridge 注入 (中风险)

```
攻击条件: WebView XSS 或 CEF 调试端口开放
攻击路径: 在 WebView 中执行任意 JS
可获取数据: 调用所有 JS→Java 功能
可执行操作: 发送消息、修改代码、访问数据库、操作 Git
缓解措施: JCEF 沙箱限制, 生产环境应禁用远程调试
```

#### 攻击面 3: 密钥提取 (中风险)

```
攻击条件: 访问 Agent 安装目录
攻击路径: 读取 agent/bin/index.js
可获取数据: 所有硬编码密钥 (RSA, SM2, SM4, AES)
影响: 可解密所有历史加密数据
缓解措施: webpack 混淆增加提取难度, 但非不可逆
```

#### 攻击面 4: 中间人攻击 (低风险)

```
攻击条件: 控制 Agent → 云端网络路径
攻击路径: 修改 DNS / 代理设置
可获取数据: 所有 API 请求/响应
缓解措施: HTTPS + 证书验证 (除 OpenTelemetry 通道)
注意: OpenTelemetry 通道禁用证书验证, 可被 MITM
```

### 6.5 安全建议优先级

| 优先级 | 建议 | 影响 |
|--------|------|------|
| P0 | WebSocket 添加认证 (共享密钥或 Token) | 防止本机进程嗅探 |
| P0 | SQL 密码传输前加密 (RSA/SM2) | 防止密码泄露 |
| P1 | Git Token 传输前加密 | 防止令牌泄露 |
| P1 | 升级 RSA 到 2048-bit+ | 防止密钥分解 |
| P1 | AES-CTR 改为 AES-GCM | 防止流密码重用攻击 |
| P2 | 密钥从服务端动态获取 | 支持密钥轮换 |
| P2 | OpenTelemetry 启用证书验证 | 防止 MITM |
| P3 | MD5 替换为 SHA-256 | 防止碰撞攻击 |
| P3 | JS Bridge 添加权限校验 | 防止未授权调用 |

---

## 7. Agent API 端点完整映射

### 7.1 星火 API (starspark)

| API 路径 | 用途 | 模块 |
|---------|------|------|
| `/api/starspark/v1/agent/chat/async/ask` | 异步流式对话 | Chat |
| `/api/starspark/v1/agent/chat/sync/ask` | 同步对话 | Chat |
| `/api/starspark/v1/agent/chat/inline/chat` | 行内对话 | InlineChat |
| `/api/starspark/v1/agent/chat/review` | 代码评审 | GitReview |
| `/api/starspark/v1/agent/chat/generateSql` | SQL 生成 | SQL |
| `/api/starspark/v1/agent/chat/sync/generateSql` | SQL 生成 (同步) | SQL |
| `/api/starspark/v1/agent/chat/optimizeSql` | SQL 优化 | SQL |
| `/api/starspark/v1/agent/chat/sync/optimizeSql` | SQL 优化 (同步) | SQL |
| `/api/starspark/v1/agent/chat/optimizeCode` | 代码优化 | Chat |
| `/api/starspark/v1/agent/chat/splitFunction` | 函数拆分 | Chat |
| `/api/starspark/v1/agent/chat/generateCommitMessage` | Commit 消息生成 | Git |
| `/api/starspark/v1/agent/chat/convertDmTableDDL` | 达梦 DDL 转换 | SQL |
| `/api/starspark/v1/agent/chat/interLineCommentCode` | 行间注释 | InlineChat |
| `/api/starspark/v1/agent/chat/evaluate` | 评价 | Common |
| `/api/starspark/v1/agent/chat/feedback` | 反馈 | Common |
| `/api/starspark/v1/agent/chat/recommendations` | 推荐 | Chat |
| `/api/starspark/v1/agent/prompt/query` | Prompt 模板查询 | Chat |
| `/api/starspark/v1/agent/code/codeComplete` | 代码补全 | CodeComplete |
| `/api/starspark/v1/agent/code/generateUnitTest` | 单测生成 | UnitTest |
| `/api/starspark/v1/agent/code/generateUnitTestCaseTemplate` | 单测模板 | UnitTest |
| `/api/starspark/v1/platform/code/assist` | 代码辅助 (VSCode) | CodeComplete |
| `/api/starspark/v1/agent/permission/queryUserPermissionPackageInfo` | 权限查询 | Login |
| `/api/starspark/v1/agent/permission/queryUserFuncModelList` | 功能模型列表 | Login |
| `/api/starspark/v1/agent/authSetting/query` | 认证设置查询 | Login |
| `/api/starspark/v1/agent/authSetting/queryPluginLink` | 插件链接查询 | Login |
| `/api/starspark/v1/agent/authSetting/queryGlobalSetting` | 全局设置 | Setting |
| `/api/starspark/v1/agent/pluginSetting/queryTokenSetting` | Token 设置 | Setting |
| `/api/starspark/v1/agent/action/saveUserAction` | 用户行为记录 | Common |
| `/api/starspark/v1/agent/action/rejectCode` | 拒绝代码 | Common |
| `/api/starspark/v1/user/authorizationQuery` | 登录状态查询 | Login |
| `/api/starspark/v1/user/packageQuery` | 套餐查询 | Login |
| `/api/usercenter/v1/user/common/login` | 账号密码登录 | Login |
| `/api/starspark/v1/chat/user/logOut` | 登出 | Login |
| `/api/starspark/v1/chat/user/valid` | Token 验证 | Login |

### 7.2 RAG 服务 (ragserver)

| API 路径 | 用途 | 模块 |
|---------|------|------|
| `/api/ragserver/v1/code/search` | 代码搜索 | CodeSearch |
| `/api/ragserver/v1/code/onlineSearch` | 在线搜索 | CodeSearch |
| `/api/ragserver/v1/code/getLanguages` | 语言列表 | CodeSearch |
| `/api/ragserver/v1/code/getUserRepos` | 用户仓库 | CodeSearch |
| `/api/ragserver/v1/rag/incbatchload` | RAG 批量加载 | RAG |
| `/api/ragserver/v1/web/parseurl` | 网页解析 | RAG |
| `/restapi/ragserver/v1/doc/search` | 文档搜索 | RAG |
| `/restapi/ragserver/v1/doc/knowledgeList` | 文档知识列表 | RAG |
| `/restapi/ragserver/v1/rag/codeK/codeKnowledgeList` | 代码知识列表 | RAG |
| `/restapi/ragserver/v1/rag/codeK/personal/auth` | 个人知识库认证 | RAG |
| `/restapi/ragserver/v1/rag/codeK/personal/init/status` | 知识库初始化状态 | RAG |
| `/restapi/ragserver/v1/rag/codeK/updateGitToken` | 更新 Git Token | RAG |
| `/restapi/ragserver/v1/codeknowledge/reVectorized` | 重新向量化 | RAG |
| `/restapi/ragserver/v1/code/searchInRepo` | 仓库内搜索 | CodeSearch |
| `/restapi/ragserver/v1/rag/repoKeyEnable` | 仓库密钥启用 | RAG |
| `/restapi/ragserver/v1/rag/repoLangExtEnable` | 仓库语言扩展启用 | RAG |
| `/restapi/ragserver/v1/rag/repoKeyDialogEnable` | 仓库密钥对话框启用 | RAG |

### 7.3 单元测试服务 (unit)

| API 路径 | 用途 | 模块 |
|---------|------|------|
| `/restapi/unit/v1/createUnitTask` | 创建批量单测任务 | BatchUnitTest |
| `/restapi/unit/v1/queryUnitTask` | 查询单测任务 | BatchUnitTest |
| `/restapi/unit/v1/exportByTaskId` | 下载单测结果 | BatchUnitTest |
| `/restapi/unit/v1/cancelUnitTask` | 取消单测任务 | BatchUnitTest |
| `/restapi/unit/v1/deleteUnitTask` | 删除单测任务 | BatchUnitTest |
| `/restapi/unit/v1/isPendingTask` | 检查待处理任务 | BatchUnitTest |

### 7.4 数据收集 API

| API 路径 | 用途 |
|---------|------|
| `/api/starspark/v1/agent/collect/chatDataContent` | 聊天数据收集 |
| `/api/starspark/v1/agent/collect/codeAccept` | 代码接受收集 |
| `/api/starspark/v1/agent/collect/commitCodeData` | 提交代码数据收集 |
| `/api/starspark/v1/agent/collect/uploadRequestTime` | 请求时间分析 |
| `/api/starspark/v1/agent/collect/unitTestStatistics` | 单测统计收集 |
| `/api/starspark/v1/agent/collect/commitUnitTestData` | 单测数据提交 |
| `/api/starspark/v1/agent/collect/generateUnitTestData` | 单测生成数据 |
| `/api/starspark/v1/agent/code/queryUnitTestQueueInfo` | 单测队列信息 |

---

## 8. 消息统计

### 8.1 JS→Java 消息统计

| 模块 | 消息数 | 含敏感数据 |
|------|--------|-----------|
| CHAT | 18 | 用户输入, 代码 |
| SQL_CHAT | 9 | **数据库密码** |
| CODE_CHECK | 2 | 修复代码 |
| CODE_REVIEW | 4 | 无 |
| CODE_SEARCH | 3 | 无 |
| UNIT_TEST | 7 | 测试代码 |
| BATCH_UNIT_TEST | 4 | 无 |
| UNIT_TEST_BANK | 2 | 无 |
| GIT | 4 | **Git Token** |
| LOGIN | 5 | 无 |
| SETTING | 4 | 配置数据 |
| COMMON | 9 | 代码内容, URL |
| **总计** | **71** | **2 含密码/令牌** |

### 8.2 Java→JS 消息统计

| 模块 | 消息数 | 含敏感数据 |
|------|--------|-----------|
| CHAT | 16 | 无 |
| SQL_CHAT | 9 | 连接信息 |
| CODE_CHECK | 2 | 无 |
| CODE_REVIEW | 3 | 无 |
| CODE_SEARCH | 3 | 无 |
| UNIT_TEST | 7 | 无 |
| BATCH_UNIT_TEST | 3 | 无 |
| UNIT_TEST_BANK | 4 | 无 |
| LOGIN | 3 | 可能含 Token |
| SETTING | 5 | 无 |
| COMMON | 3 | 无 |
| GIT | 1 | 无 |
| USER | 1 | 无 |
| **总计** | **60** | **0 含加密数据** |

---

## 9. 跨文档引用

| 文档 | 内容 | 关联 |
|------|------|------|
| doc 04 | WebSocket 协议格式 | MessageDto/ResponseDto 定义 |
| doc 06 | 命令体系参考 | 109 个 CommandEnum 值 |
| doc 08 | 认证流程 | 登录链路, Token 管理 |
| doc 65 | WebView 前端完整分析 | JS Bridge, 路由, Stores |
| doc 74 | 安全审计报告 | OWASP Top 10 发现 |
| doc 85 | View/UI/StatusBar 分析 | WebViewWindowPanel God Object |
| doc 94 | 启动与消息映射 | 双向消息映射体系 |
