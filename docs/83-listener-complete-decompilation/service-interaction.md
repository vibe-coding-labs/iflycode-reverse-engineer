## 5. 与 Service 层的交互关系

### 5.1 EditorManagerService (核心交互)

| Listener | 调用方法 | 用途 |
|----------|---------|------|
| AutoCodeGenerateListener | `editorChanged(editor, requestType, isCommandName)` | 触发代码补全请求 |
| AutoCodeGenerateListener | `disposeTips(editor, actionEnum)` | 关闭代码提示 |
| AutoCodeGenerateListener | `isAvailable(editor)` | 检查编辑器可用性 |
| AutoCodeGenerateListener | `hasTipInlays(editor)` | 检查 AI 提示 |
| AutoCodeGenerateListener | `cancelTipRequests(editor)` | 取消补全请求 |
| CodeEditorListener | `isAvailable(editor)` | 检查编辑器可用性 |
| CodeLookupManagerListener | `editorChanged(editor, Forced, false)` | 强制触发补全 |
| CodeLookupManagerListener | `cancelTipRequests(editor)` | 取消补全请求 |
| CodeLookupManagerListener | `disposeTips(editor, IdeCompletion)` | 关闭 IDE 补全提示 |
| CodeLookupManagerListener$01 | `acceptTip(editor)` | 接受 AI 补全 |
| CodeLookupManagerListener$01 | `hasTipInlays(editor)` | 检查 AI 提示 |
| AICodeUnloadPluginListener | `disposeTips(editor, UserOperate)` | 用户操作关闭提示 |

### 5.2 PluginWebsocketClient (WebSocket 通信)

| Listener | 调用方法 | 用途 |
|----------|---------|------|
| CodeFileEditorManagerListener | `sendWsMessageWithOutApm(msg, project)` | 发送文档打开/同步消息 |
| CommitHandlerFactory$o | `sendWsMessage(msg, project)` | 发送单元测试收集数据 |
| GitBranchChangeListener | `sendWsMessage(CommandEnum, data, project)` | 发送 Git 状态消息 |
| PluginManagerListener | `closeWebsocket(basePath, reason)` | 关闭 WebSocket 连接 |

### 5.3 SocketMessageHandleListener (WebView 通信)

| Listener | 调用方法 | 用途 |
|----------|---------|------|
| GitBranchChangeListener | `send2Web(project, data)` | 发送 Git 状态给 WebView |
| ThemeChangeListener | `send2Web(project, data)` | 发送主题变更给 WebView |

### 5.4 其他服务交互

| Listener | 服务 | 用途 |
|----------|------|------|
| AutoCodeGenerateListener | `DocumentActionTracker` | 追踪强制代码生成动作 |
| AutoCodeGenerateListener | `RequestTipServiceImpl` | APM 追踪(撤销时结束 Span) |
| CodeFileEditorManagerListener | `RecentFilesManager` | 记录最近打开文件 |
| CodeFileEditorManagerListener$01 | `InlineChatService` | 清理 Inline Chat 数据 |
| CodeFileEditorManagerListener$01 | `CommonService` | Java 语言支持检查 |
| CommitHandlerFactory$o | `UnitTestCollectUtil` | 分析变更方法和单元测试 |
| PluginManagerListener | `ChatService` | 清除 Chat 会话 ID |
| PluginManagerListener | `SqlService` | 清除 SQL 会话 ID |
| GitBranchChangeListener | `AICodeSettingsState` | 读取权限/忽略设置 |
| ThemeChangeListener | `StatusBarPopup` | 更新状态栏图标 |

---

## 6. 混淆字符串解密方法汇总

所有 Listener 中大量使用了字符串混淆，解密通过以下静态方法:

| 解密方法 | 来源包 | 用途 |
|---------|--------|------|
| `MethodGeneratorConfig.H(Object)` | `com.aicode.action.batch` | 通用解密 |
| `CodeCompleteService.H(Object)` | `com.aicode.agent.service` | 代码补全相关 |
| `GeneratorConfig.H(Object)` | `com.aicode.action.batch` | 代码生成相关 |
| `HandleCacheUtil.H(Object)` | `com.aicode.util` | 缓存相关 |
| `IndentLineUtil.H(Object)` | `com.aicode.util` | 缩进相关 |
| `PropertyUtils.H(Object)` | `com.aicode.util` | 属性相关 |
| `OverlayUtils.H(Object)` | `com.aicode.content.util` | 覆盖层相关 |
| `FileExtensionLanguageDetails.H(Object)` | `com.aicode.content.util.file` | 文件扩展名相关 |
| `InlineChatStatusServiceKt.H(Object)` | `com.aicode.inline.status` | Inline Chat 状态 |
| `CancelRequestTip.H(Object)` | `com.aicode.service.editor` | 取消请求提示 |
| `JComponentKt.H(Object)` | `com.aicode.util` | UI 组件相关 |
| `AICodeStringUtil.H(Object)` | `com.aicode.util` | 字符串工具 |
| `GenericUtils.H(Object)` | `com.aicode.diff` | 通用差异工具 |
| `FileService.H(Object)` | `com.aicode.diff` | 文件服务 |
| `RequestCancelException.H(Object)` | `com.aicode.exception` | 请求取消异常 |
| `FontKt.H(Object)` | `com.aicode.ui` | 字体相关 |
| `Maps.H(Object)` | `com.aicode.util` | Map 工具 |
| `NewFileUtils.H(Object)` | `com.aicode.util` | 新文件工具 |

---

## 7. 关键发现

### 7.1 代码补全触发机制

AutoCodeGenerateListener 是代码补全的核心触发器:
- 监听 IntelliJ 的 CommandListener，在 `commandFinished` 时检测编辑器位置变更
- 如果光标位置改变(VisualPosition 不同)，触发 `editorChanged(Automatic)` -- 自动补全
- 如果仅文档内容改变(modificationSequence 不同)，触发 `disposeTips(CaretChange)` -- 关闭提示
- Tab 键选择 IDE 补全时，如果存在 AI 提示，会拦截 IDE 补全，改为接受 AI 提示

### 7.2 文档变更延迟策略

CodeFileEditorManagerListener$01 对不同语言采用不同延迟:
- Java 文件: 3000ms 延迟后触发补全
- 非 Java 文件: 50ms 延迟后触发补全

这表明 Java 代码补全需要更多上下文分析时间。

### 7.3 Git 授权流程

GitBranchChangeListener 实现了完整的 Git 仓库授权流程:
1. 检测分支是否在远程仓库
2. 如果不在远程，标记为未授权(status=-5)
3. 通过 WebSocket 通知 Agent 和 WebView
4. 提供三种用户操作: 打开知识库、忽略授权、授权仓库
5. 授权通过 Agent WebSocket 发送 GIT_REPO_AUTHORIZE 命令

### 7.4 项目关闭清理

PluginManagerListener 在项目关闭时:
1. 先自动接受所有处于 SUCCESS 状态的 Inline Chat 会话
2. 关闭 WebSocket 连接
3. 清除 Chat 和 SQL 会话缓存

### 7.5 VCS 提交数据收集

CommitHandlerFactory$o 在代码提交成功后:
1. 分析所有 Java 变更文件
2. 提取变更方法信息
3. 收集单元测试相关数据
4. 通过 WebSocket 发送 LOG_TEST_COLLECTION_COMMIT 消息给 Agent

### 7.6 主题变更广播

ThemeChangeListener 在 IDE 主题或字体大小变更时:
1. 切换状态栏和工具窗口图标(暗色/亮色)
2. 通过 WebView 通信通道通知前端主题变更
3. 更新状态栏弹出组件