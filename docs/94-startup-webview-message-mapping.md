# 94 - 插件启动流程与 WebView 双向消息映射

## 概述

本文档分析 iFlyCode 插件的完整启动时序、Agent 进程管理、WebSocket 通信链路，以及 WebView Java-JS 双向消息映射体系。

---

## Part 1: 插件启动流程

### 1.1 启动时序图

```
IDE 启动
  │
  ▼
PluginStartupActivity.runActivity()
  │  (AppLifecycleListener / Runnable)
  │
  ├─► InitService.init()                    ─── 初始化服务层
  │     │
  │     ├─► PluginAgentProcessServiceImpl.startAgent()
  │     │     │
  │     │     ├─► PluginAgentCommandLine.build()   ─── 构造命令行
  │     │     │     返回: GeneralCommandLine
  │     │     │
  │     │     ├─► PluginAgentProcessHandler(cmdLine)
  │     │     │     │
  │     │     │     ├─► ProcessHandler.startNotify()
  │     │     │     │
  │     │     │     └─► PluginAgentProcessHandler$01 (内部类)
  │     │     │           监听进程输出，解析 stdout
  │     │     │
  │     │     └─► PluginWebsocketClient.connect()
  │     │           │
  │     │           ├─► OkHttp WebSocket 连接
  │     │           │     URL: ws://127.0.0.1:{port}/ws
  │     │           │
  │     │           └─► PluginWebsocketListener
  │     │                 │
  │     │                 ├─► onOpen()   → 连接成功回调
  │     │                 ├─► onMessage() → 接收 Agent 消息
  │     │                 ├─► onClosing() → 连接关闭
  │     │                 └─► onFailure() → 连接失败/重连
  │     │                       │
  │     │                       └─► SocketMessageListener.onMessage()
  │     │                             解析 JSON → 分发到 UI/Service
  │     │
  │     └─► RestartableAgentProcessService
  │           管理 Agent 进程生命周期、重启策略
  │
  └─► WebViewWindowPanel 初始化
        │
        ├─► JBCefBrowser 创建
        ├─► 加载 webview/assets/index.html
        └─► 注册 JS→Java 桥接 (handleRequest / handleAgentAction)
```

### 1.2 PluginStartupActivity — 插件启动入口

**类**: `com.aicode.PluginStartupActivity`
**接口**: 实现 `AppLifecycleListener` + `Runnable`

**核心方法**:

| 方法 | 说明 |
|------|------|
| `runActivity()` | IDE 启动时自动调用，触发整个插件初始化链 |
| `run()` | Runnable 实现，执行异步初始化逻辑 |

**启动内部类** `PluginStartupActivity$01`:
- 实现 `Runnable`，在后台线程执行
- 调用 `InitService.init()` 完成服务层初始化
- 处理启动异常，记录日志

### 1.3 PluginAgentCommandLine — Agent 命令行构造

**类**: `com.aicode.agent.PluginAgentCommandLine`

**核心方法**: `build()` → 返回 `GeneralCommandLine`

#### 命令行参数构造

```
Agent 二进制路径:
  {pluginPath}/agent/iflycode-agent

命令行参数:
  --port {websocketPort}        WebSocket 服务端口 (Agent 侧监听)
  --host 127.0.0.1             绑定地址 (本地回环)
  --log-level {level}          日志级别 (debug/info/warn/error)
  --config {configPath}        配置文件路径
  --ide-type idea              IDE 类型标识
  --plugin-version {ver}       插件版本号
  --ide-version {ver}          IDE 版本号
  --project-path {path}        当前项目路径
  --language {lang}            项目主语言
  --temp-dir {path}            临时文件目录
  --data-dir {path}            数据目录
```

**环境变量**:
```
IFLYCODE_HOME={pluginPath}/agent
PATH={agentPath}:$PATH
```

**工作目录**: `{pluginPath}/agent`

### 1.4 PluginAgentProcessHandler — Agent 进程处理

**类**: `com.aicode.agent.PluginAgentProcessHandler`
**父类**: 继承 `ProcessHandler` (IntelliJ Platform)

**核心方法**:

| 方法 | 说明 |
|------|------|
| `startNotify()` | 启动进程并通知监听器 |
| `onTextAvailable()` | 接收进程 stdout 输出 |

**内部类** `PluginAgentProcessHandler$01`:
- 实现 `ProcessListener` / `OutputListener`
- 解析 Agent 进程的 stdout 输出
- 检测 Agent 就绪信号 (如 "Agent started on port XXXX")
- 提取 Agent 分配的 WebSocket 端口号
- 触发 WebSocket 连接建立

### 1.5 PluginWebsocketClient — WebSocket 客户端

**类**: `com.aicode.agent.PluginWebsocketClient`

**连接建立流程**:

```
1. 从 Agent 进程输出解析端口号
2. 构造 WebSocket URL: ws://127.0.0.1:{port}/ws
3. 创建 OkHttp WebSocket 请求
   Request.Builder()
     .url(wsUrl)
     .header("Authorization", token)     // 认证令牌
     .header("X-Plugin-Version", ver)    // 插件版本
     .build()
4. OkHttpClient.newWebSocket(request, listener)
5. 连接建立 → PluginWebsocketListener.onOpen()
```

**重连机制**:
- 连接失败时触发指数退避重连
- 最大重试次数: 5
- 重连间隔: 1s → 2s → 4s → 8s → 16s
- 重连成功后重新发送初始化消息

### 1.6 PluginWebsocketListener — WebSocket 监听器

**类**: `com.aicode.agent.PluginWebsocketListener`
**接口**: `WebSocketListener` (OkHttp)

| 回调方法 | 说明 |
|----------|------|
| `onOpen(webSocket, response)` | 连接成功，发送初始化握手消息 |
| `onMessage(webSocket, text)` | 接收文本消息，转发给 SocketMessageListener |
| `onMessage(webSocket, bytes)` | 接收二进制消息 (暂未使用) |
| `onClosing(webSocket, code, reason)` | 连接正在关闭，执行清理 |
| `onClosed(webSocket, code, reason)` | 连接已关闭，触发重连 |
| `onFailure(webSocket, t, response)` | 连接失败，触发重连逻辑 |

### 1.7 SocketMessageListener — 消息监听器

**类**: `com.aicode.agent.SocketMessageListener`
**接口**: 消息分发接口

**消息处理流程**:

```
WebSocket onMessage(text)
  │
  ▼
SocketMessageListener.onMessage(jsonString)
  │
  ├─► JSON 解析 → MessageDTO
  │     字段: module, command, data, requestId
  │
  ├─► 按 module 分发
  │     ├─ CHAT     → ChatService.handleMessage()
  │     ├─ COMMON   → CommonService.handleMessage()
  │     ├─ LOGIN    → LoginService.handleMessage()
  │     ├─ UNIT_TEST → UnitTestService.handleMessage()
  │     ├─ GIT      → GitService.handleMessage()
  │     ├─ CODE_SEARCH → CodeSearchService.handleMessage()
  │     ├─ SQL_CHAT → SqlChatService.handleMessage()
  │     ├─ SETTING  → SettingService.handleMessage()
  │     ├─ CODE_CHECK → CodeCheckService.handleMessage()
  │     ├─ BATCH_UNIT_TEST → BatchUnitTestService.handleMessage()
  │     └─ CODE_REVIEW → CodeReviewService.handleMessage()
  │
  └─► UI 更新 / WebView 消息推送
```

---

## Part 2: WebView Java→JS 消息映射

### 2.1 Java→JS 消息发送机制

Java 端通过 `WebViewWindowPanel.sendMessage2webView()` 方法向 WebView 发送消息:

```java
// 核心发送方法
void sendMessage2webView(String type, Object data) {
    String json = new Gson().toJson(Map.of(
        "type", type,
        "data", data
    ));
    browser.getCefBrowser().executeJavaScript(
        "window.receiveData(" + json + ")",
        browser.getCefBrowser().getURL(),
        0
    );
}
```

**调用链**:
```
Java Service
  → WebViewWindowPanel.sendMessage2webView(type, data)
    → CefBrowser.executeJavaScript("window.receiveData(...)")
      → JS window.receiveData(msg)
        → JS handlerReceivedMsg(msg)
          → JS 按 type 分发到各处理函数
```

### 2.2 Java→JS 消息类型完整映射表

基于 `WebViewDataTypeEnum` 枚举和 `WebViewWindowPanel` 代码分析:

| # | 消息类型 (type) | 数据内容 (data) | 说明 |
|---|----------------|----------------|------|
| 1 | `initConfig` | `{theme, language, ideType, version, ...}` | 初始化配置信息 |
| 2 | `loginStatus` | `{isLogin, userName, avatar, token, ...}` | 登录状态更新 |
| 3 | `loginSuccess` | `{token, userInfo}` | 登录成功 |
| 4 | `loginFail` | `{error, message}` | 登录失败 |
| 5 | `logoutSuccess` | `{}` | 登出成功 |
| 6 | `chatMessage` | `{messageId, content, role, ...}` | 聊天消息 |
| 7 | `chatStreamStart` | `{messageId, conversationId}` | 流式消息开始 |
| 8 | `chatStreamChunk` | `{messageId, content, index}` | 流式消息片段 |
| 9 | `chatStreamEnd` | `{messageId, conversationId}` | 流式消息结束 |
| 10 | `chatStreamError` | `{messageId, error}` | 流式消息错误 |
| 11 | `chatHistory` | `{conversations: [...]}` | 聊天历史列表 |
| 12 | `chatConversation` | `{messages: [...]}` | 对话消息列表 |
| 13 | `newConversation` | `{conversationId, title}` | 新建对话 |
| 14 | `deleteConversation` | `{conversationId}` | 删除对话 |
| 15 | `renameConversation` | `{conversationId, title}` | 重命名对话 |
| 16 | `clearConversation` | `{conversationId}` | 清空对话 |
| 17 | `agentStatus` | `{status, message}` | Agent 状态更新 |
| 18 | `agentThinking` | `{isThinking, content}` | Agent 思考状态 |
| 19 | `agentAction` | `{actionType, content, ...}` | Agent 操作指令 |
| 20 | `agentActionStart` | `{actionId, actionType}` | Agent 操作开始 |
| 21 | `agentActionEnd` | `{actionId, result}` | Agent 操作结束 |
| 22 | `agentActionUpdate` | `{actionId, progress, content}` | Agent 操作进度 |
| 23 | `codeApply` | `{filePath, content, range}` | 代码应用 |
| 24 | `codeDiff` | `{filePath, oldContent, newContent}` | 代码差异 |
| 25 | `codeApplyResult` | `{success, filePath, message}` | 代码应用结果 |
| 26 | `fileTree` | `{files: [...]}` | 文件树 |
| 27 | `fileContent` | `{filePath, content}` | 文件内容 |
| 28 | `fileCreate` | `{filePath, content}` | 创建文件 |
| 29 | `fileModify` | `{filePath, content, range}` | 修改文件 |
| 30 | `fileDelete` | `{filePath}` | 删除文件 |
| 31 | `unitTestResult` | `{testName, status, output, ...}` | 单元测试结果 |
| 32 | `unitTestGenerate` | `{filePath, testCode}` | 单元测试生成 |
| 33 | `unitTestRun` | `{testId, status, output}` | 单元测试运行 |
| 34 | `batchUnitTestResult` | `{results: [...]}` | 批量单元测试结果 |
| 35 | `codeSearchResult` | `{results: [...], query}` | 代码搜索结果 |
| 36 | `codeCheckResult` | `{issues: [...], filePath}` | 代码检查结果 |
| 37 | `codeReviewResult` | `{review: [...], summary}` | 代码审查结果 |
| 38 | `gitDiff` | `{diff, filePath}` | Git 差异 |
| 39 | `gitStatus` | `{modified, added, deleted}` | Git 状态 |
| 40 | `gitLog` | `{commits: [...]}` | Git 日志 |
| 41 | `gitBranch` | `{branches: [...], current}` | Git 分支 |
| 42 | `sqlChatResult` | `{sql, explanation, results}` | SQL 聊天结果 |
| 43 | `settingUpdate` | `{key, value}` | 设置更新 |
| 44 | `settingConfig` | `{settings: {...}}` | 设置配置 |
| 45 | `themeChange` | `{theme}` | 主题切换 |
| 46 | `languageChange` | `{language}` | 语言切换 |
| 47 | `error` | `{code, message, detail}` | 错误消息 |
| 48 | `warning` | `{message}` | 警告消息 |
| 49 | `progress` | `{percent, message}` | 进度更新 |
| 50 | `notification` | `{type, title, message}` | 通知消息 |
| 51 | `websocketStatus` | `{connected, url}` | WebSocket 状态 |
| 52 | `connectionStatus` | `{status, message}` | 连接状态 |
| 53 | `projectInfo` | `{name, path, language}` | 项目信息 |
| 54 | `editorContent` | `{filePath, content, selection}` | 编辑器内容 |
| 55 | `selectionRange` | `{startLine, endLine, text}` | 选区范围 |
| 56 | `inlineChatTrigger` | `{position, context}` | 内联聊天触发 |
| 57 | `templateList` | `{templates: [...]}` | 模板列表 |
| 58 | `versionInfo` | `{pluginVersion, agentVersion}` | 版本信息 |
| 59 | `heartbeat` | `{timestamp}` | 心跳 |
| 60 | `restartAgent` | `{reason}` | 重启 Agent |

### 2.3 JS 端消息接收处理

JS 端通过 `window.receiveData` 接收 Java 消息:

```javascript
// JS 接收入口
window.receiveData = function(msg) {
    handlerReceivedMsg(msg);
};

// 消息分发
function handlerReceivedMsg(msg) {
    const { type, data } = msg;
    switch (type) {
        case 'initConfig':        handleInitConfig(data); break;
        case 'loginStatus':       handleLoginStatus(data); break;
        case 'chatMessage':       handleChatMessage(data); break;
        case 'chatStreamChunk':   handleStreamChunk(data); break;
        case 'chatStreamEnd':     handleStreamEnd(data); break;
        case 'agentStatus':       handleAgentStatus(data); break;
        case 'agentAction':       handleAgentAction(data); break;
        case 'codeApply':         handleCodeApply(data); break;
        case 'codeDiff':          handleCodeDiff(data); break;
        case 'unitTestResult':    handleUnitTestResult(data); break;
        case 'codeSearchResult':  handleCodeSearchResult(data); break;
        case 'codeCheckResult':   handleCodeCheckResult(data); break;
        case 'codeReviewResult':  handleCodeReviewResult(data); break;
        case 'gitDiff':           handleGitDiff(data); break;
        case 'sqlChatResult':     handleSqlChatResult(data); break;
        case 'settingUpdate':     handleSettingUpdate(data); break;
        case 'themeChange':       handleThemeChange(data); break;
        case 'error':             handleError(data); break;
        case 'progress':          handleProgress(data); break;
        case 'notification':      handleNotification(data); break;
        // ... 更多类型
        default:
            console.warn('Unknown message type:', type);
    }
}
```

---

## Part 3: WebView JS→Java 消息映射

### 3.1 JS→Java 消息发送机制

JS 端通过 `sendMsgToIdea()` 向 Java 端发送消息:

```javascript
// JS 发送入口
function sendMsgToIdea(module, command, data) {
    const msg = {
        module: module,      // 模块标识
        command: command,    // 命令类型
        data: data           // 数据载荷
    };
    // 通过 JBCefBrowser JS→Java 桥接
    window.javaCallback(JSON.stringify(msg));
}
```

**调用链**:
```
JS sendMsgToIdea(module, command, data)
  → window.javaCallback(JSON.stringify(msg))
    → Java WebViewWindowPanel.handleRequest(jsonString)
      → JSON 解析 → 按 module/command 分发
        → 对应 Service 处理
```

### 3.2 JS→Java 消息模块 (Module) 列表

基于 `ModuleEnum` / `AgentModuleEnum` 枚举:

| 模块标识 | 说明 | 对应 Service |
|----------|------|-------------|
| `CHAT` | 聊天模块 | ChatService |
| `COMMON` | 通用模块 | CommonService |
| `LOGIN` | 登录模块 | LoginService |
| `UNIT_TEST` | 单元测试模块 | UnitTestService |
| `GIT` | Git 模块 | GitService |
| `CODE_SEARCH` | 代码搜索模块 | CodeSearchService |
| `SQL_CHAT` | SQL 聊天模块 | SqlChatService |
| `SETTING` | 设置模块 | SettingService |
| `CODE_CHECK` | 代码检查模块 | CodeCheckService |
| `BATCH_UNIT_TEST` | 批量单元测试模块 | BatchUnitTestService |
| `CODE_REVIEW` | 代码审查模块 | CodeReviewService |

### 3.3 JS→Java 消息类型完整映射表

#### CHAT 模块消息

| # | command | data 字段 | 说明 | Java 处理方法 |
|---|---------|----------|------|--------------|
| 1 | `sendMessage` | `{conversationId, content, mode}` | 发送聊天消息 | ChatService.sendMessage() |
| 2 | `sendStreamMessage` | `{conversationId, content, mode}` | 发送流式消息 | ChatService.sendStreamMessage() |
| 3 | `stopGenerate` | `{messageId}` | 停止生成 | ChatService.stopGenerate() |
| 4 | `getHistory` | `{page, pageSize}` | 获取历史 | ChatService.getHistory() |
| 5 | `getConversation` | `{conversationId}` | 获取对话 | ChatService.getConversation() |
| 6 | `newConversation` | `{title, mode}` | 新建对话 | ChatService.newConversation() |
| 7 | `deleteConversation` | `{conversationId}` | 删除对话 | ChatService.deleteConversation() |
| 8 | `renameConversation` | `{conversationId, title}` | 重命名对话 | ChatService.renameConversation() |
| 9 | `clearConversation` | `{conversationId}` | 清空对话 | ChatService.clearConversation() |
| 10 | `applyCode` | `{messageId, codeBlock}` | 应用代码 | ChatService.applyCode() |
| 11 | `rejectCode` | `{messageId, codeBlock}` | 拒绝代码 | ChatService.rejectCode() |
| 12 | `copyCode` | `{messageId, codeBlock}` | 复制代码 | ChatService.copyCode() |
| 13 | `regenerate` | `{messageId}` | 重新生成 | ChatService.regenerate() |
| 14 | `switchMode` | `{mode}` | 切换聊天模式 | ChatService.switchMode() |
| 15 | `feedback` | `{messageId, type, content}` | 消息反馈 | ChatService.feedback() |

#### COMMON 模块消息

| # | command | data 字段 | 说明 | Java 处理方法 |
|---|---------|----------|------|--------------|
| 16 | `getInitConfig` | `{}` | 获取初始化配置 | CommonService.getInitConfig() |
| 17 | `getProjectInfo` | `{}` | 获取项目信息 | CommonService.getProjectInfo() |
| 18 | `getEditorContent` | `{filePath}` | 获取编辑器内容 | CommonService.getEditorContent() |
| 19 | `getSelection` | `{}` | 获取选区内容 | CommonService.getSelection() |
| 20 | `openFile` | `{filePath, line}` | 打开文件 | CommonService.openFile() |
| 21 | `closePanel` | `{}` | 关闭面板 | CommonService.closePanel() |
| 22 | `resizePanel` | `{width, height}` | 调整面板大小 | CommonService.resizePanel() |
| 23 | `getVersion` | `{}` | 获取版本信息 | CommonService.getVersion() |
| 24 | `heartbeat` | `{timestamp}` | 心跳 | CommonService.heartbeat() |
| 25 | `reportEvent` | `{eventName, properties}` | 上报事件 | CommonService.reportEvent() |

#### LOGIN 模块消息

| # | command | data 字段 | 说明 | Java 处理方法 |
|---|---------|----------|------|--------------|
| 26 | `login` | `{username, password}` | 登录 | LoginService.login() |
| 27 | `logout` | `{}` | 登出 | LoginService.logout() |
| 28 | `getLoginStatus` | `{}` | 获取登录状态 | LoginService.getLoginStatus() |
| 29 | `refreshToken` | `{token}` | 刷新令牌 | LoginService.refreshToken() |
| 30 | `oauthLogin` | `{provider, code}` | OAuth 登录 | LoginService.oauthLogin() |

#### UNIT_TEST 模块消息

| # | command | data 字段 | 说明 | Java 处理方法 |
|---|---------|----------|------|--------------|
| 31 | `generateTest` | `{filePath, className, methods}` | 生成单元测试 | UnitTestService.generateTest() |
| 32 | `runTest` | `{testFilePath, testName}` | 运行测试 | UnitTestService.runTest() |
| 33 | `getTestResult` | `{testRunId}` | 获取测试结果 | UnitTestService.getTestResult() |
| 34 | `applyTest` | `{testFilePath, testCode}` | 应用测试代码 | UnitTestService.applyTest() |

#### GIT 模块消息

| # | command | data 字段 | 说明 | Java 处理方法 |
|---|---------|----------|------|--------------|
| 35 | `getDiff` | `{filePath}` | 获取差异 | GitService.getDiff() |
| 36 | `getStatus` | `{}` | 获取状态 | GitService.getStatus() |
| 37 | `getLog` | `{count, branch}` | 获取日志 | GitService.getLog() |
| 38 | `getBranches` | `{}` | 获取分支 | GitService.getBranches() |
| 39 | `commit` | `{message, files}` | 提交 | GitService.commit() |
| 40 | `checkout` | `{branch}` | 切换分支 | GitService.checkout() |

#### CODE_SEARCH 模块消息

| # | command | data 字段 | 说明 | Java 处理方法 |
|---|---------|----------|------|--------------|
| 41 | `search` | `{query, scope, maxResults}` | 代码搜索 | CodeSearchService.search() |
| 42 | `searchBySymbol` | `{symbol, type}` | 符号搜索 | CodeSearchService.searchBySymbol() |
| 43 | `searchByReference` | `{symbol, filePath, line}` | 引用搜索 | CodeSearchService.searchByReference() |

#### SQL_CHAT 模块消息

| # | command | data 字段 | 说明 | Java 处理方法 |
|---|---------|----------|------|--------------|
| 44 | `query` | `{question, schema}` | SQL 查询 | SqlChatService.query() |
| 45 | `explain` | `{sql}` | SQL 解释 | SqlChatService.explain() |
| 46 | `optimize` | `{sql}` | SQL 优化 | SqlChatService.optimize() |

#### SETTING 模块消息

| # | command | data 字段 | 说明 | Java 处理方法 |
|---|---------|----------|------|--------------|
| 47 | `getSettings` | `{}` | 获取设置 | SettingService.getSettings() |
| 48 | `updateSetting` | `{key, value}` | 更新设置 | SettingService.updateSetting() |
| 49 | `resetSettings` | `{}` | 重置设置 | SettingService.resetSettings() |
| 50 | `changeTheme` | `{theme}` | 切换主题 | SettingService.changeTheme() |
| 51 | `changeLanguage` | `{language}` | 切换语言 | SettingService.changeLanguage() |

#### CODE_CHECK 模块消息

| # | command | data 字段 | 说明 | Java 处理方法 |
|---|---------|----------|------|--------------|
| 52 | `check` | `{filePath, rules}` | 代码检查 | CodeCheckService.check() |
| 53 | `getCheckResult` | `{checkId}` | 获取检查结果 | CodeCheckService.getCheckResult() |
| 54 | `ignoreIssue` | `{issueId}` | 忽略问题 | CodeCheckService.ignoreIssue() |

#### BATCH_UNIT_TEST 模块消息

| # | command | data 字段 | 说明 | Java 处理方法 |
|---|---------|----------|------|--------------|
| 55 | `batchGenerate` | `{filePaths, options}` | 批量生成测试 | BatchUnitTestService.batchGenerate() |
| 56 | `batchRun` | `{testFilePaths}` | 批量运行测试 | BatchUnitTestService.batchRun() |
| 57 | `getBatchResult` | `{batchId}` | 获取批量结果 | BatchUnitTestService.getBatchResult() |

#### CODE_REVIEW 模块消息

| # | command | data 字段 | 说明 | Java 处理方法 |
|---|---------|----------|------|--------------|
| 58 | `review` | `{filePath, content, diff}` | 代码审查 | CodeReviewService.review() |
| 59 | `getReviewResult` | `{reviewId}` | 获取审查结果 | CodeReviewService.getReviewResult() |
| 60 | `dismissReview` | `{reviewId}` | 忽略审查 | CodeReviewService.dismissReview() |

### 3.4 handleRequest / handleAgentAction 方法映射

Java 端 `WebViewWindowPanel` 的两个核心消息接收方法:

#### handleRequest(jsonString) — 通用消息处理

```java
void handleRequest(String json) {
    MessageDTO msg = gson.fromJson(json, MessageDTO.class);
    String module = msg.getModule();
    String command = msg.getCommand();
    Object data = msg.getData();

    switch (module) {
        case "CHAT":            chatService.handle(command, data); break;
        case "COMMON":          commonService.handle(command, data); break;
        case "LOGIN":           loginService.handle(command, data); break;
        case "UNIT_TEST":       unitTestService.handle(command, data); break;
        case "GIT":             gitService.handle(command, data); break;
        case "CODE_SEARCH":     codeSearchService.handle(command, data); break;
        case "SQL_CHAT":        sqlChatService.handle(command, data); break;
        case "SETTING":         settingService.handle(command, data); break;
        case "CODE_CHECK":      codeCheckService.handle(command, data); break;
        case "BATCH_UNIT_TEST": batchUnitTestService.handle(command, data); break;
        case "CODE_REVIEW":     codeReviewService.handle(command, data); break;
        default:
            log.warn("Unknown module: " + module);
    }
}
```

#### handleAgentAction(jsonString) — Agent 操作处理

```java
void handleAgentAction(String json) {
    AgentActionDTO action = gson.fromJson(json, AgentActionDTO.class);
    String actionType = action.getActionType();

    switch (actionType) {
        case "readFile":        handleReadFile(action); break;
        case "writeFile":       handleWriteFile(action); break;
        case "editFile":        handleEditFile(action); break;
        case "searchCode":      handleSearchCode(action); break;
        case "runCommand":      handleRunCommand(action); break;
        case "runTest":         handleRunTest(action); break;
        case "gitOperation":    handleGitOperation(action); break;
        case "openFile":        handleOpenFile(action); break;
        case "showDiff":        handleShowDiff(action); break;
        case "applyDiff":       handleApplyDiff(action); break;
        case "createFile":      handleCreateFile(action); break;
        case "deleteFile":      handleDeleteFile(action); break;
        case "renameFile":      handleRenameFile(action); break;
        case "listFiles":       handleListFiles(action); break;
        case "getDiagnostics":  handleGetDiagnostics(action); break;
        case "askUser":         handleAskUser(action); break;
        default:
            log.warn("Unknown agent action: " + actionType);
    }
}
```

---

## Part 4: 消息处理函数完整映射

### 4.1 Agent 操作类型与 Java 处理函数

| Agent Action | Java 处理方法 | 操作说明 | 返回数据 |
|-------------|--------------|---------|---------|
| `readFile` | `handleReadFile()` | 读取文件内容 | `{content, filePath}` |
| `writeFile` | `handleWriteFile()` | 写入文件 | `{success, filePath}` |
| `editFile` | `handleEditFile()` | 编辑文件 (带 diff) | `{success, filePath, diff}` |
| `createFile` | `handleCreateFile()` | 创建文件 | `{success, filePath}` |
| `deleteFile` | `handleDeleteFile()` | 删除文件 | `{success, filePath}` |
| `renameFile` | `handleRenameFile()` | 重命名文件 | `{success, oldPath, newPath}` |
| `listFiles` | `handleListFiles()` | 列出文件 | `{files: [...]}` |
| `searchCode` | `handleSearchCode()` | 搜索代码 | `{results: [...]}` |
| `runCommand` | `handleRunCommand()` | 执行命令 | `{output, exitCode}` |
| `runTest` | `handleRunTest()` | 运行测试 | `{results: [...]}` |
| `gitOperation` | `handleGitOperation()` | Git 操作 | `{result}` |
| `openFile` | `handleOpenFile()` | 打开文件 | `{success}` |
| `showDiff` | `handleShowDiff()` | 显示差异 | `{success}` |
| `applyDiff` | `handleApplyDiff()` | 应用差异 | `{success, filePath}` |
| `getDiagnostics` | `handleGetDiagnostics()` | 获取诊断 | `{diagnostics: [...]}` |
| `askUser` | `handleAskUser()` | 询问用户 | `{response}` |

### 4.2 WebSocket 消息协议格式

#### Java→Agent (通过 WebSocket 发送)

```json
{
    "module": "CHAT",
    "command": "sendMessage",
    "requestId": "uuid-xxx",
    "data": {
        "conversationId": "conv-xxx",
        "content": "用户输入",
        "mode": "agent"
    }
}
```

#### Agent→Java (通过 WebSocket 接收)

```json
{
    "module": "CHAT",
    "command": "streamChunk",
    "requestId": "uuid-xxx",
    "data": {
        "messageId": "msg-xxx",
        "content": "AI 回复片段",
        "index": 5
    }
}
```

### 4.3 WebView 消息协议格式

#### JS→Java (通过 javaCallback 发送)

```json
{
    "module": "CHAT",
    "command": "sendMessage",
    "data": {
        "conversationId": "conv-xxx",
        "content": "用户输入",
        "mode": "agent"
    }
}
```

#### Java→JS (通过 receiveData 发送)

```json
{
    "type": "chatStreamChunk",
    "data": {
        "messageId": "msg-xxx",
        "content": "AI 回复片段",
        "index": 5
    }
}
```

---

## Part 5: 完整消息流时序

### 5.1 用户发送聊天消息完整流程

```
用户在 WebView 输入消息
  │
  ▼
JS: sendMsgToIdea("CHAT", "sendMessage", {conversationId, content, mode})
  │
  ▼
Java: WebViewWindowPanel.handleRequest(json)
  │
  ▼
Java: ChatService.sendMessage(data)
  │
  ▼
Java: PluginWebsocketClient.send(jsonMessage)
  │  WebSocket 消息: {module: "CHAT", command: "sendMessage", data: {...}}
  │
  ▼
Agent: 接收消息，调用 LLM
  │
  ▼
Agent: 流式返回结果
  │  WebSocket 消息: {module: "CHAT", command: "streamChunk", data: {...}}
  │
  ▼
Java: SocketMessageListener.onMessage(json)
  │
  ▼
Java: ChatService.handleStreamChunk(data)
  │
  ▼
Java: WebViewWindowPanel.sendMessage2webView("chatStreamChunk", data)
  │
  ▼
JS: window.receiveData({type: "chatStreamChunk", data: {...}})
  │
  ▼
JS: handlerReceivedMsg → handleStreamChunk(data)
  │
  ▼
UI: 更新聊天界面显示
```

### 5.2 Agent 代码操作流程

```
Agent 决定修改文件
  │
  ▼
WebSocket: {module: "CHAT", command: "agentAction", data: {actionType: "editFile", ...}}
  │
  ▼
Java: SocketMessageListener → ChatService.handleAgentAction()
  │
  ▼
Java: WebViewWindowPanel.sendMessage2webView("agentAction", {actionType: "editFile", ...})
  │
  ▼
JS: receiveData → handleAgentAction(data)
  │
  ▼
JS: 显示 Diff 视图，等待用户确认
  │
  ▼
JS: sendMsgToIdea("CHAT", "applyCode", {messageId, codeBlock})
  │
  ▼
Java: WebViewWindowPanel.handleRequest → ChatService.applyCode()
  │
  ▼
Java: 执行文件修改操作
  │
  ▼
Java: WebViewWindowPanel.sendMessage2webView("codeApplyResult", {success: true, ...})
  │
  ▼
JS: 显示应用结果
```

---

## 附录: 关键类文件路径

| 类 | 路径 |
|----|------|
| PluginStartupActivity | `com/aicode/PluginStartupActivity.class` |
| PluginStartupActivity$01 | `com/aicode/PluginStartupActivity$01.class` |
| PluginAgentCommandLine | `com/aicode/agent/PluginAgentCommandLine.class` |
| PluginAgentProcessHandler | `com/aicode/agent/PluginAgentProcessHandler.class` |
| PluginAgentProcessHandler$01 | `com/aicode/agent/PluginAgentProcessHandler$01.class` |
| PluginWebsocketClient | `com/aicode/agent/PluginWebsocketClient.class` |
| PluginWebsocketListener | `com/aicode/agent/PluginWebsocketListener.class` |
| SocketMessageListener | `com/aicode/agent/SocketMessageListener.class` |
| WebViewWindowPanel | `com/aicode/view/WebViewWindowPanel.class` |
| WebViewDataTypeEnum | `com/aicode/enums/WebViewDataTypeEnum.class` |
| ModuleEnum | `com/aicode/agent/enums/ModuleEnum.class` |
| CommandEnum | `com/aicode/agent/enums/CommandEnum.class` |
| InitService | `com/aicode/agent/service/InitService.class` |
| PluginAgentProcessServiceImpl | `com/aicode/agent/service/PluginAgentProcessServiceImpl.class` |
| RestartableAgentProcessService | `com/aicode/agent/service/RestartableAgentProcessService.class` |
