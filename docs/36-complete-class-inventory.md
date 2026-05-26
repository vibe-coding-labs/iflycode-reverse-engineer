# iFlyCode 完整类清单与继承关系

> 版本: 3.4.2-222 | 分析日期: 2026-05-11
> 来源: 常量池扫描 566 个 .class 文件

## 1. 概览

| 包 | 类数量 | 说明 |
|---|--------|------|
| com/aicode | 1 | 顶层入口 |
| com/aicode/action | 9 | 代码补全 Actions |
| com/aicode/action/batch | 12 | 批量操作 Actions |
| com/aicode/action/click | 1 | 点击 Action |
| com/aicode/agent | 3 | Agent 进程管理 |
| com/aicode/agent/dto | 7 | Agent 数据传输对象 |
| com/aicode/agent/enums | 2 | Agent 枚举 |
| com/aicode/agent/service | 7 | Agent 服务层 |
| com/aicode/apm | 2 | APM 遥测 |
| com/aicode/complete | 3 | 代码补全核心 |
| com/aicode/content | 1 | 内容处理 |
| com/aicode/content/util | 3 | 内容工具 |
| com/aicode/content/util/file | 2 | 文件工具 |
| com/aicode/diff | 5 | Diff 对比 |
| com/aicode/domain | 6 | 领域模型 |
| com/aicode/dto | 5 | 数据传输对象 |
| com/aicode/enums | 6 | 枚举定义 |
| com/aicode/error | 1 | 错误处理 |
| com/aicode/exception | 2 | 异常定义 |
| com/aicode/generate | 3 | 代码生成 |
| com/aicode/icons | 1 | 图标 |
| com/aicode/inline | 1 | 内联聊天 |
| com/aicode/inline/action | 5 | 内联聊天 Actions |
| com/aicode/inline/content | 1 | 内联聊天内容 |
| com/aicode/inline/controller | 2 | 内联聊天控制器 |
| com/aicode/inline/dto | 2 | 内联聊天 DTO |
| com/aicode/inline/enums | 1 | 内联聊天枚举 |
| com/aicode/inline/ide | 2 | 内联聊天 IDE 集成 |
| com/aicode/inline/listener | 1 | 内联聊天监听 |
| com/aicode/inline/render | 1 | 内联聊天渲染 |
| com/aicode/inline/status | 2 | 内联聊天状态 |
| com/aicode/language | 5 | 语言支持 |
| com/aicode/listener | 5 | 全局监听器 |
| com/aicode/message | 1 | 消息处理 |
| com/aicode/request | 4 | 请求管理 |
| com/aicode/service | 3 | 核心服务接口 |
| com/aicode/service/editor | 6 | 编辑器服务 |
| com/aicode/service/response | 1 | 响应处理 |
| com/aicode/settings | 4 | 设置管理 |
| com/aicode/status | 2 | 状态管理 |
| com/aicode/statusBar | 1 | 状态栏 |
| com/aicode/template | 1 | 模板 |
| com/aicode/template/impl | 3 | 模板实现 |
| com/aicode/test | 2 | 测试生成 |
| com/aicode/toolwindow | 2 | 工具窗口 |
| com/aicode/ui | 4 | UI 组件 |
| com/aicode/updater | 2 | 自动更新 |
| com/aicode/util | 15 | 工具类 |
| com/aicode/view | 3 | 视图组件 |

## 2. 完整类清单

### 2.1 com/aicode/ (1 class)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| PluginStartupActivity | Object | Runnable | 插件启动初始化 |

### 2.2 com/aicode/action/ (9 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| AcceptInlaysAction | AnAction | - | 接受代码补全 (Tab) |
| AcceptLineCodeInlaysAction | AnAction | - | 逐行采纳 (Ctrl+Down) |
| AcceptWordInlaysAction | AnAction | - | 逐词采纳 (Ctrl+Right) |
| CycleNextEditorInlays | AnAction | - | 下一个补全 (Alt+]) |
| CyclePreviousEditorInlays | AnAction | - | 上一个补全 (Alt+[) |
| DisposeInlaysAction | AnAction | - | 清除补全 (Esc) |
| EnableAutoTriggerCodeGenerateAction | ToggleAction | - | 自动触发开关 |
| LogoutAction | AnAction | - | 登出 |
| RequestCodeGenerateAction | AnAction | - | 触发补全 (Alt+\) |

### 2.3 com/aicode/action/batch/ (12 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| BatchUnitTestAction | AnAction | - | 批量单测 Action |
| CodeProblemsIntentionAction | Object | IntentionAction | 代码问题意图 |
| CodeProblemsTreePopupAction | AnAction | - | 一键修复弹出 |
| GeneratorConfig | Object | - | 生成器配置 (含 H() 混淆) |
| MethodGeneratorConfig | Object | - | 方法级生成器配置 (含 H() 混淆) |
| OpenWindowAction | AnAction | - | 打开窗口 (Ctrl+Q) |
| PluginSettingAction | AnAction | - | 插件设置 |
| ResultTree | Object | - | 批量单测结果树 |
| UnitTestAction | AnAction | - | 单元测试 Action |
| UnitTestByFileAction | AnAction | - | 按文件生成单测 |
| UnitTestByMethodAction | AnAction | - | 按方法生成单测 |
| UserInfoAction | AnAction | - | 用户信息 |

### 2.4 com/aicode/action/click/ (1 class)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| EditorActionGroup | DefaultActionGroup | - | 编辑器右键菜单组 |

### 2.5 com/aicode/agent/ (3 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| AgentProcess | Object | - | Agent 进程管理 |
| AgentProcessBuilder | Object | - | Agent 进程构建器 |
| RestartableAgentProcessService | Object | PersistentStateComponent | Agent 进程服务 |

### 2.6 com/aicode/agent/dto/ (7 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| AgentRequest | Object | - | Agent 请求 |
| ChatRequest | Object | - | 聊天请求 |
| CodeCompleteRequest | Object | - | 代码补全请求 |
| CodeSearchRequest | Object | - | 代码搜索请求 |
| GitReviewRequest | Object | - | Git 评审请求 |
| InlineChatRequest | Object | - | 内联聊天请求 |
| UnitTestRequest | Object | - | 单测请求 |

### 2.7 com/aicode/agent/enums/ (2 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| CommandEnum | Enum | - | WebSocket 命令枚举 (109+ 值) |
| RequestTypeEnum | Enum | - | 请求类型枚举 |

### 2.8 com/aicode/agent/service/ (7 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| ChatService | Object | - | 聊天服务 |
| CodeCompleteService | Object | - | 代码补全服务 (含 H() 混淆) |
| CodeSearchService | Object | - | 代码搜索服务 |
| GitReviewService | Object | - | Git 评审服务 (含 H() 混淆) |
| InlineChatService | Object | - | 内联聊天服务 |
| UnitTestService | Object | - | 单测服务 |
| CodeCheckService | Object | - | 代码检查服务 |

### 2.9 com/aicode/apm/ (2 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| OpenTelemetryService | Object | PersistentStateComponent | OTEL 服务 |
| OpenTelemetryUtil | Object | - | OTEL 工具 (含 H() 混淆) |

### 2.10 com/aicode/complete/ (3 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| CodeCompleteHandler | Object | - | 补全处理器 |
| InlayPresentationFactory | Object | - | 补全渲染工厂 |
| InlayPresentationUtils | Object | - | 补全渲染工具 |

### 2.11 com/aicode/content/ (1 class)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| ContentHelper | Object | - | 内容辅助 |

### 2.12 com/aicode/content/util/ (3 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| EditorUtils | Object | - | 编辑器工具 (含 H() 混淆) |
| OverlayUtils | Object | - | 覆盖层工具 (含 H() 混淆) |
| PsiUtils | Object | - | PSI 工具 |

### 2.13 com/aicode/content/util/file/ (2 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| FileExtensionLanguageDetails | Object | - | 文件扩展名语言映射 (含 H() 混淆) |
| LanguageFileExtensionDetails | Object | - | 语言文件扩展名映射 (含 H() 混淆) |

### 2.14 com/aicode/diff/ (5 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| DiffContent | Object | - | Diff 内容 |
| DiffWindow | Object | - | Diff 窗口 |
| FileInfo | Object | - | 文件信息 (含 H() 混淆) |
| GenericUtils | Object | - | 通用工具 (含 H() 混淆) |
| ShowDiffAction | AnAction | - | 显示 Diff |

### 2.15 com/aicode/domain/ (6 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| CodeCompleteResult | Object | - | 代码补全结果 |
| CodeCompleteResultItem | Object | - | 补全结果项 |
| CodeSearchResult | Object | - | 代码搜索结果 |
| GitReviewResult | Object | - | Git 评审结果 |
| InlineChatResult | Object | - | 内联聊天结果 |
| UnitTestResult | Object | - | 单测结果 |

### 2.16 com/aicode/dto/ (5 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| ChatDTO | Object | - | 聊天 DTO |
| CodeCompleteDTO | Object | - | 补全 DTO |
| InlineChatDTO | Object | - | 内联聊天 DTO |
| SocketMessage | Object | - | WebSocket 消息 |
| WebViewMessage | Object | - | WebView 消息 |

### 2.17 com/aicode/enums/ (6 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| CodeOperateTypes | Enum | - | 代码操作类型 |
| WebViewDataTypeEnum | Enum | - | WebView 数据类型 |
| MessageType | Enum | - | 消息类型 |
| RequestStatus | Enum | - | 请求状态 |
| SessionStatus | Enum | - | 会话状态 |
| TriggerType | Enum | - | 触发类型 |

### 2.18 com/aicode/error/ (1 class)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| ErrorHandler | Object | - | 全局错误处理 |

### 2.19 com/aicode/exception/ (2 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| RequestCancelException | RuntimeException | - | 请求取消异常 (含 H() 混淆) |
| RequestTimeoutException | RuntimeException | - | 请求超时异常 (含 H() 混淆) |

### 2.20 com/aicode/generate/ (3 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| CodeGenerator | Object | - | 代码生成器 |
| CommentGenerator | Object | - | 注释生成器 |
| TestGenerator | Object | - | 测试生成器 |

### 2.21 com/aicode/icons/ (1 class)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| AICodeIcons | Object | - | 图标常量 |

### 2.22 com/aicode/inline/ (1 class)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| InlineChatService | Object | PersistentStateComponent | 内联聊天服务 |

### 2.23 com/aicode/inline/action/ (5 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| InlineChatAcceptAction | AnAction | - | 接受内联聊天 |
| InlineChatRejectAction | AnAction | - | 拒绝内联聊天 |
| InlineChatRetryAction | AnAction | - | 重试内联聊天 |
| InlineChatStopAction | AnAction | - | 停止内联聊天 |
| InlineChatUndoAction | AnAction | - | 撤销内联聊天 |

### 2.24 com/aicode/inline/content/ (1 class)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| InlineChatContent | Object | - | 内联聊天内容 |

### 2.25 com/aicode/inline/controller/ (2 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| ChatInputController | Object | - | 聊天输入控制器 (含 H() 混淆) |
| InlineChatController | Object | - | 内联聊天控制器 |

### 2.26 com/aicode/inline/dto/ (2 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| InlineChatRequest | Object | - | 内联聊天请求 |
| InlineChatResponse | Object | - | 内联聊天响应 |

### 2.27 com/aicode/inline/enums/ (1 class)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| InlineChatStatus | Enum | - | 内联聊天状态枚举 |

### 2.28 com/aicode/inline/ide/ (2 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| ConditionalActionConfiguration | Object | - | 条件 Action 配置 (含 H() 混淆) |
| IdeAction | Object | - | IDE Action (含 H() 混淆) |

### 2.29 com/aicode/inline/listener/ (1 class)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| InlineChatListener | Object | - | 内联聊天监听 |

### 2.30 com/aicode/inline/render/ (1 class)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| InlineChatRenderer | Object | - | 内联聊天渲染器 |

### 2.31 com/aicode/inline/status/ (2 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| InlineChatStatusServiceKt | Object | - | 内联聊天状态服务 (含 H() 混淆) |
| InlineChatStatusEnum | Enum | - | 内联聊天状态枚举 |

### 2.32 com/aicode/language/ (5 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| AICodeExtendedLanguageSupport | Object | LanguageInfoSupport | 扩展语言支持 |
| AICodeLanguageInfo | Object | - | 语言信息 (含 H() 混淆) |
| CodeLanguageInfoSupport | Object | LanguageInfoSupport | 代码语言支持 |
| JavaLanguageInfoSupport | Object | LanguageInfoSupport, EditorSupport | Java 语言支持 |
| PythonLanguageInfoSupport | Object | LanguageInfoSupport, EditorSupport | Python 语言支持 |
| JavaScriptLanguageInfoSupport | Object | LanguageInfoSupport, EditorSupport | JS 语言支持 |

### 2.33 com/aicode/listener/ (5 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| AICodeUnloadPluginListener | Object | DynamicPluginListener | 插件卸载监听 |
| ApplicationStartupListener | Object | AppLifecycleListener | 应用启动监听 |
| AutoCodeGenerateListener | Object | CommandListener | 自动补全触发 |
| CodeFileEditorManagerListener | Object | FileEditorManagerListener | 文件编辑器监听 |
| PluginDocumentListener | Object | - | 文档监听 |
| PluginManagerListener | Object | ProjectManagerListener | 项目管理监听 |

### 2.34 com/aicode/message/ (1 class)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| SocketMessageHandleListener | Object | - | WebSocket 消息处理 |

### 2.35 com/aicode/request/ (4 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| RequestManager | Object | - | 请求管理器 |
| RequestResultList | Object | - | 请求结果列表 |
| SocketRequest | Object | - | WebSocket 请求 |
| HttpRequest | Object | - | HTTP 请求 |

### 2.36 com/aicode/service/ (3 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| EditorManagerService | Object | - | 编辑器管理服务接口 |
| EditorManagerServiceImpl | Object | EditorManagerService | 编辑器管理实现 |
| LanguageInfoSupport | Object | - | 语言信息支持接口 |

### 2.37 com/aicode/service/editor/ (6 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| CancelRequestTip | Object | - | 取消请求提示 (含 H() 混淆) |
| DocumentActionTracker | Object | PersistentStateComponent | 文档操作追踪 |
| RequestResultList | Object | - | 请求结果列表 (含 H() 混淆) |
| RequestTipService | Object | - | 请求提示服务接口 |
| RequestTipServiceImpl | Object | RequestTipService | 请求提示实现 |
| EditorSupport | Object | - | 编辑器支持接口 |

### 2.38 com/aicode/service/response/ (1 class)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| CodeCompleteResponse | Object | - | 代码补全响应 |

### 2.39 com/aicode/settings/ (4 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| AICodeRequestSettings | Object | PersistentStateComponent | 请求设置 |
| AICodeSettingsState | Object | PersistentStateComponent | 主设置状态 |
| BatchUnitTestSettingsState | Object | PersistentStateComponent | 批量单测设置 |
| UnitTestSettingsState | Object | PersistentStateComponent | 单测设置 |

### 2.40 com/aicode/status/ (2 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| AICodeStatusService | Object | PersistentStateComponent | 状态服务 |
| StatusBarWidgetFactory | Object | StatusBarWidgetFactory | 状态栏小部件 |

### 2.41 com/aicode/statusBar/ (1 class)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| StatusBarWidget | Object | StatusBarWidget | 状态栏小部件实现 |

### 2.42 com/aicode/template/ (1 class)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| TemplateEngine | Object | - | 模板引擎 |

### 2.43 com/aicode/template/impl/ (3 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| JavaTemplateImpl | TemplateEngine | - | Java 模板 |
| PythonTemplateImpl | TemplateEngine | - | Python 模板 |
| JavaScriptTemplateImpl | TemplateEngine | - | JavaScript 模板 |

### 2.44 com/aicode/test/ (2 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| UnitTestGenerator | Object | - | 单测生成器 |
| UnitTestRunner | Object | - | 单测运行器 |

### 2.45 com/aicode/toolwindow/ (2 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| PluginToolWindowFactory | Object | ToolWindowFactory | 工具窗口工厂 |
| WebViewPanel | Object | - | WebView 面板 |

### 2.46 com/aicode/ui/ (4 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| ActionButton | Object | - | Action 按钮 (含 H() 混淆) |
| FontKt | Object | - | 字体工具 (含 H() 混淆) |
| LoginPanel | Object | - | 登录面板 |
| SettingsPanel | Object | - | 设置面板 |

### 2.47 com/aicode/updater/ (2 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| PluginUpdater | Object | - | 插件更新器 |
| UpdateChecker | Object | - | 更新检查器 |

### 2.48 com/aicode/util/ (15 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| AICodeStringUtil | Object | - | 字符串混淆核心 (含 H() 混淆) |
| AICodeUtils | Object | - | 通用工具 (含 H() 混淆) |
| Application | Object | - | 应用工具 (含 H() 混淆) |
| HandleCacheUtil | Object | - | 缓存工具 (含 H() 混淆) |
| IndentLineUtil | Object | - | 缩进工具 (含 H() 混淆) |
| JComponentKt | Object | - | Swing 组件扩展 (含 H() 混淆) |
| Maps | Object | - | Map 工具 (含 H() 混淆) |
| NewFileUtils | Object | - | 文件创建工具 (含 H() 混淆) |
| PositionUtil | Object | - | 位置工具 (含 H() 混淆) |
| TipTypedHandlerDelegate | TypedHandlerDelegate | - | 输入处理委托 |
| CodePromoterAction | ActionPromoter | - | Action 推广 |
| TipPromoterAction | ActionPromoter | - | 补全推广 |
| DebuggerFilter | Object | ExceptionFilter | JVM 异常过滤 |
| CommitHandlerFactory | CheckinHandlerFactory | - | 提交处理器 |
| CodeLookupManagerListener | Object | LookupManagerListener | 代码查找监听 |

### 2.49 com/aicode/view/ (3 classes)

| 类 | 父类 | 接口 | 说明 |
|----|------|------|------|
| ChatWindow | Object | - | 聊天窗口 |
| CodeWindow | Object | - | 代码窗口 |
| DiffWindow | Object | - | Diff 窗口 |

## 3. 关键继承关系

### 3.1 Action 继承树

```
AnAction (IntelliJ Platform)
├── AcceptInlaysAction          — 接受补全
├── AcceptLineCodeInlaysAction  — 逐行采纳
├── AcceptWordInlaysAction      — 逐词采纳
├── CycleNextEditorInlays       — 下一个补全
├── CyclePreviousEditorInlays   — 上一个补全
├── DisposeInlaysAction         — 清除补全
├── RequestCodeGenerateAction   — 触发补全
├── EnableAutoTriggerCodeGenerateAction (ToggleAction) — 自动触发
├── LogoutAction                — 登出
├── BatchUnitTestAction         — 批量单测
├── CodeProblemsTreePopupAction — 一键修复
├── OpenWindowAction            — 打开窗口
├── PluginSettingAction         — 设置
├── UnitTestAction              — 单测
├── UnitTestByFileAction        — 按文件单测
├── UnitTestByMethodAction      — 按方法单测
├── UserInfoAction              — 用户信息
├── InlineChatAcceptAction      — 接受内联
├── InlineChatRejectAction      — 拒绝内联
├── InlineChatRetryAction       — 重试内联
├── InlineChatStopAction        — 停止内联
├── InlineChatUndoAction        — 撤销内联
└── ShowDiffAction              — 显示 Diff
```

### 3.2 服务继承树

```
PersistentStateComponent (IntelliJ Platform)
├── RestartableAgentProcessService  — Agent 进程服务
├── OpenTelemetryService            — OTEL 服务
├── InlineChatService               — 内联聊天服务
├── DocumentActionTracker           — 文档追踪
├── AICodeRequestSettings           — 请求设置
├── AICodeSettingsState             — 主设置
├── BatchUnitTestSettingsState      — 批量单测设置
├── UnitTestSettingsState           — 单测设置
└── AICodeStatusService             — 状态服务
```

### 3.3 异常继承树

```
RuntimeException
├── RequestCancelException   — 请求取消 (含 H() 混淆)
└── RequestTimeoutException  — 请求超时 (含 H() 混淆)
```
