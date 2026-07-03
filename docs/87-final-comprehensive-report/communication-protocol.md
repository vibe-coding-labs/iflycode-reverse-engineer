## 3. 通信协议

### 3.1 WebSocket 协议

| 参数 | 值 | 来源 |
|------|-----|------|
| URL | `ws://127.0.0.1:&#123;动态端口&#125;/ws/idea` | doc 04 |
| Client | OkHttp 4.12.0 | doc 04 |
| connectTimeout | 60s | doc 04 |
| readTimeout | 60s | doc 04 |
| writeTimeout | 60s | doc 04 |
| 消息大小限制 | 512MB | doc 74 |
| 消息格式 | JSON | doc 04 |

**请求消息 (MessageDto) 核心字段:**

| 字段 | 类型 | 说明 |
|------|------|------|
| id | String | UUID 请求标识 |
| command | String | 命令类型 (CommandEnum) |
| stream | boolean | 是否流式 (默认 true) |
| timeStamp | long | 消息时间戳 (ms) |
| traceparent | String | W3C Trace Context |
| path | String | 当前文件路径 |
| lang | String | 文件语言 |
| content | String | 文件/选中代码内容 |
| sessionId | String | 会话 ID |
| modelCode | String | AI 模型代码 |
| permissionCode | String | 权限代码 |
| data | Object | 命令附加数据 |
| range | RangeDTO[] | 代码范围 |
| md5 | String | 文件 MD5 |

> 来源: doc 04, 05

**响应消息格式:**

| 类型 | 格式 | 用途 |
|------|------|------|
| ResponseDto | `&#123;id, code, msg, command, data&#125;` | 普通响应 |
| ResponseStreamDto | `&#123;id, code, msg, data: &#123;ended, text, showKeyMapTipFlag&#125;&#125;` | 流式响应 |
| BizResponse | `&#123;resCode, msg, obj&#125;` | 业务响应 |

> 来源: doc 04, 70

### 3.2 14 模块消息分发

`CommandEnum` 定义 109 个命令，按 15 个 `AgentModuleEnum` 分组。`SocketMessageHandleListener` 根据 command 值 switch 分发到对应 Service：

| 模块 | 命令数 | 目标 Service | 来源 |
|------|--------|-------------|------|
| INIT | 11 | InitService, UserService | doc 06, 77 |
| LOGIN | 9 | UserService | doc 06 |
| CODE_COMPLETE | 11 | CodeCompleteService | doc 06, 32 |
| CHAT | 18 | ChatService | doc 06, 09 |
| INLINE_CHAT | 4 | InlineChatCommandService | doc 06, 11 |
| UNIT_TEST | 4 | UnitTestService, BatchUnitTestService | doc 06, 46 |
| CODE_CHECK | 3 | CodeCheckService | doc 06, 16 |
| GIT | 8 | GitReviewService | doc 06, 14 |
| SQL | 8 | SqlService | doc 06, 12 |
| CODE_SEARCH | 3 | CodeSearchService | doc 06, 15 |
| ACTION | 2 | CommonService | doc 06 |
| SETTING | 2 | CommonService | doc 06 |
| RAG | 3 | CodeSearchService | doc 71 |
| GENERAL | 1 | CommonService | doc 06 |
| ERROR | 1 | CommonService | doc 06 |

> 来源: doc 06, 77

### 3.3 流式响应机制

```
Agent 推送流式数据
     │
     v
PluginWebsocketListener.onMessage(json)
     │
     v
SocketMessageHandleListener.onMessage(msg)
     │
     ├── 解析 ResponseStreamDto
     │     ├── data.ended == false → 追加 text 到当前内容
     │     └── data.ended == true  → 标记完成
     │
     ├── ChatService: 逐 chunk 推送到 WebView
     │     └── WebViewWindowPanel.callJS("receiveData", json)
     │
     ├── CodeCompleteService: 逐 chunk 更新 Inlay
     │     └── EditorManagerServiceImpl.$F.onNext(list)
     │
     └── InlineChatCommandService: 逐 chunk 写入编辑器
           └── WriteCommandAction.runWriteCommandAction()
```

> 来源: doc 04, 32, 77

### 3.4 JS Bridge 三平台实现

| 维度 | IDEA Bridge | VSCode Bridge | Eclipse Bridge |
|------|-------------|---------------|----------------|
| 源文件 | ideaUtil-11ab0730.js | vscodeUtil-49d49699.js | eclipseUtil-82d0751a.js |
| JS->IDE 发送 | `window.myObject.sendMessage(JSON)` | `vscode.postMessage(&#123;type, value&#125;)` | `window.sendMessage(JSON)` |
| IDE->JS 接收 | `window.receiveData = callback` | `window.addEventListener("message")` | `window.receiveData = callback` |
| 消息格式 | `&#123;type, value&#125;` (value 直接) | `&#123;type, value: JSON.stringify(value)&#125;` | `&#123;type, value: JSON.stringify(value)&#125;` |
| WebView 引擎 | JCEF (Chromium) | VSCode Webview (Chromium) | SWT Browser (WebKit/IE) |
| 平台检测 | 无 (默认) | `acquireVsCodeApi !== undefined` | 无 (宿主注入) |

> 来源: doc 07, 72

---
