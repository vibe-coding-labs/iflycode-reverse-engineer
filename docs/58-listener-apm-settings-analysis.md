# iFlyCode Listener/APM/Settings 包完整分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-11

## 1. 概述

本文档分析 `com/aicode/listener/`（21 个类）、`com/aicode/apm/`（7 个类含子包）和 `com/aicode/settings/`（6 个类）三个包。Listener 包实现 IDE 事件监听，APM 包实现 OpenTelemetry 链路追踪，Settings 包实现插件配置持久化。

## 2. Listener 包 — IDE 事件监听

### 2.1 AICodeUnloadPluginListener (82 strings)

**路径**: `com/aicode/listener/AICodeUnloadPluginListener`
**父类**: Object
**接口**: `DynamicPluginListener`
**职责**: 插件卸载监听器 — 处理插件动态卸载时的清理

**关键方法**:
- `beforePluginUnload(IdeaPluginDescriptor, boolean)` — 插件卸载前回调
  - 检查 `PluginInfoUtils.isAICodePlugin()` 确认是 iFlyCode 插件
  - 使用反射获取 `MessageBundle.INSTANCE` 的 `clear()` 方法（绕过访问限制）
  - 遍历所有有效项目 `ApplicationUtil.findValidProjects()`
  - 对每个项目的编辑器调用 `EditorManagerService.disposeTips(Editor, OperateActionEnum.UserOperate)`
  - 清理补全提示和编辑器状态

**关键依赖**:
- `PluginInfoUtils` — 插件识别
- `MessageBundle` — 消息包清理（反射调用）
- `ApplicationUtil` — 项目遍历
- `EditorManagerService` — 补全清理
- `OperateActionEnum.UserOperate` — 用户操作类型

**H() 混淆**: 使用 `MethodGeneratorConfig.H()` 解码 2 个混淆字符串

### 2.2 ApplicationStartupListener (32 strings)

**路径**: `com/aicode/listener/ApplicationStartupListener`
**父类**: Object
**接口**: `AppLifecycleListener`
**职责**: 应用生命周期监听器 — 处理应用关闭事件

**关键方法**:
- `appWillBeClosed(boolean)` — 应用即将关闭
  - 调用 `PluginStartupActivity.clear()` 清理启动活动
  - 日志记录关闭信息
- `appClosing()` — 应用关闭中

**H() 混淆**: 使用 `GeneratorConfig.H()` 和 `IndentLineUtil.H()` 解码混淆字符串

### 2.3 AutoCodeGenerateListener (262 strings) — 最大监听器

**路径**: `com/aicode/listener/AutoCodeGenerateListener`
**父类**: Object
**接口**: `CommandListener`
**职责**: 自动代码补全监听器 — 监听编辑器命令事件触发补全

**关键字段**:
- `commandNameTab` — `AtomicBoolean` — Tab 命令标记
- `commandNameCtrlZ` — `AtomicReference` — Ctrl+Z 命令标记
- `ignoreLookupApply` — 忽略 Lookup 应用
- `ignoreApply` — 忽略应用
- `isImitationDealFlag` — 仿制处理标记
- `inlineChatOperate` — 内联聊天操作
- `isImitationBuryingPoint` — 仿制埋点
- `atomicOperate` — `AtomicInteger` — 操作计数器

**内部类**:
- `$Q` — `OperateEditorState` — 操作编辑器状态（含 VisualPosition）
- `$T` — `UndoTransparentActionState` — 撤销透明操作状态（含 Editor + 时间戳）

**关键方法**:
- `commandStarted(CommandEvent)` — 命令开始
  - 检查命令名是否为 Tab/Ctrl+Z
  - 设置 `commandNameTab`/`commandNameCtrlZ` 标记
  - 记录 `UndoTransparentActionState`（编辑器 + 修改序列号）
- `commandFinished(CommandEvent)` — 命令完成
  - 递减 `atomicOperate` 计数器
  - 检查选区变化 → `CommandCache.isStartSelected()`
  - 触发补全请求 → `EditorManagerService.disposeTips()`
  - 检查 `CodeTipRequestType.Automatic` 自动补全触发
  - 检查 `CodeTipRequestType.Forced` 强制补全触发
- `undoTransparentActionFinished()` — 撤销透明操作完成
- `undoTransparentActionStarted()` — 撤销透明操作开始

**关键依赖**:
- `EditorManagerService` — 补全服务
- `DocumentActionTracker` — 文档操作追踪
- `CodeTipRequestType.Automatic/Forced` — 补全触发类型
- `CommandCache` — 命令缓存（选区状态）
- `OperateActionEnum.CaretChange/UserOperate` — 操作类型
- `RequestTipServiceImpl` — 补全请求服务
- `AICodeSettingsState.autoTrigger` — 自动触发设置
- `PluginStartupActivity.getApiKey()` — API 密钥检查
- `SpanAttrEnum.COMPLETE_RESULT` — APM 补全结果属性
- `OpenTelemetry Span` — 链路追踪

**H() 混淆**: 使用 `MethodGeneratorConfig.H()` 和 `HandleCacheUtil.H()` 解码约 15 个混淆字符串

### 2.4 CodeEditorListener (66 strings)

**路径**: `com/aicode/listener/CodeEditorListener`
**父类**: Object
**接口**: `EditorFactoryListener`
**职责**: 代码编辑器监听器 — 监听编辑器创建/销毁

**内部类**:
- `$CodeSelectionListener` — 选区变化监听器

**关键方法**:
- `editorCreated(EditorFactoryEvent)` — 编辑器创建
  - 获取编辑器和项目
  - 检查 `EditorManagerService.isAvailable()` 和语言支持
  - 创建 Disposable 并绑定到编辑器生命周期
  - 注册 `CodeSelectionListener` 到选区模型
- `editorReleased(EditorFactoryEvent)` — 编辑器释放

**关键依赖**:
- `EditorManagerService` — 补全服务
- `OverlayUtils` — 信息气球显示
- `ApplicationUtil.isSupportLanguage()` — 语言支持检查
- `EditorUtil.disposeWithEditor()` — 编辑器绑定

### 2.5 CodeEditorListener$CodeSelectionListener (63 strings)

**职责**: 选区变化监听器 — 处理编辑器选区变化事件

**关键方法**:
- `selectionChanged(SelectionEvent)` — 选区变化
  - 检查编辑器和项目有效性
  - 调用 `EditorUtil.isSelectedEditor()` 确认是当前编辑器
  - 调用 `InlineChatStatusServiceKt` 更新内联聊天状态
  - 记录选区变化信息

**关键依赖**:
- `InlineChatStatusServiceKt` — 内联聊天状态
- `EditorUtil` — 编辑器工具

### 2.6 CodeFileEditorManagerListener (220 strings)

**路径**: `com/aicode/listener/CodeFileEditorManagerListener`
**父类**: Object
**接口**: `FileEditorManagerListener`
**职责**: 文件编辑器管理监听器 — 监听文件打开/关闭/切换

**内部类**:
- `$01` — 匿名类

**关键方法**:
- `fileOpened(FileEditorManager, VirtualFile)` — 文件打开
  - 检查语言支持
  - 初始化编辑器补全服务
- `fileClosed(FileEditorManager, VirtualFile)` — 文件关闭
  - 清理编辑器补全状态
- `selectionChanged(FileEditorManager, FileEditor)` — 文件选择变化
  - 更新当前编辑器状态
  - 触发补全服务更新

### 2.7 CodeLookupManagerListener (87 strings)

**路径**: `com/aicode/listener/CodeLookupManagerListener`
**父类**: Object
**接口**: `LookupManagerListener`
**职责**: Lookup 管理监听器 — 监听代码补全弹出框

**内部类**:
- `$01` — 匿名类

**关键方法**:
- `activeLookupChanged(Lookup, Lookup)` — Lookup 弹出框变化
  - 当 Lookup 激活时，设置 `ignoreLookupApply` 标记
  - 当 Lookup 关闭时，清除标记

### 2.8 CommitHandlerFactory$o (286 strings) — 最大内部类

**路径**: `com/aicode/listener/CommitHandlerFactory$o`
**职责**: 提交处理器内部类 — 收集 Git 提交指标

**关键发现**: 286 个字符串，是 listener 包中最大的类。收集 Git 提交的代码变更指标，包括：
- 变更文件数、变更行数
- 代码复杂度指标
- 提交频率统计

### 2.9 GitBranchChangeListener (360 strings) — 第二大类

**路径**: `com/aicode/listener/GitBranchChangeListener`
**职责**: Git 分支变更监听器 — 监听 Git 分支切换

**内部类**:
- `$H` — Handler 内部类
- `$R` — Runner 内部类
- `$b` — 匿名类

**关键方法**:
- 监听 Git 分支变更事件
- 更新代码知识库状态
- 推送 `GIT_STATUS` 到 WebView

### 2.10 其他监听器

| 类 | 字符串数 | 职责 |
|----|---------|------|
| FileWatchedAdapter | 66 | 文件监听适配器 — 监听文件变更 |
| PluginDocumentListener | 64 | 插件文档监听器 — 监听文档修改 |
| PluginManagerListener | 101 | 插件管理监听器 — 监听插件状态 |
| ThemeChangeListener | 178 | 主题变更监听器 — 监听 IDE 主题切换 |

## 3. APM 包 — OpenTelemetry 链路追踪

### 3.1 OpenTelemetryConfig (186 strings)

**路径**: `com/aicode/apm/OpenTelemetryConfig`
**职责**: OpenTelemetry 配置初始化 — 设置 APM SDK

**关键方法**:
- `init()` — 初始化 OpenTelemetry SDK
  - 创建 `SdkTracerProvider`，采样率使用 `traceIdRatioBased`
  - 设置 W3C 传播器：`W3CTraceContextPropagator` + `W3CBaggagePropagator`
  - 创建 `BatchSpanProcessor` 配置：
    - `setExporterTimeout(MILLISECONDS)`
    - `setScheduleDelay` / `setMaxQueueSize` / `setMaxExportBatchSize`
  - 配置重试策略：`RetryPolicy`（maxAttempts, initialBackoff, maxBackoff, backoffMultiplier）
  - 创建 `OtlpHttpSpanExporter`：
    - `setEndpoint` — OTLP HTTP 端点（从 AICodeSettingsState.apmUrl 获取）
    - `setCompression` — 压缩配置
    - `setRetryPolicy` — 重试策略
    - `setProxy` — 代理配置（使用自定义 ProxySelector）
    - `setSslContext` — SSL 上下文（使用自定义 TrustManager）
  - 设置 Resource 属性：
    - `SpanAttrEnum.SYSTEM_USERNAME` — 系统用户名
    - `SpanAttrEnum.IDEA_VERSION` — IDEA 版本
    - `SpanAttrEnum.PLUGIN_VERSION` — 插件版本

**内部类**:
- `$La` — 自定义 `X509TrustManager`（信任所有证书 — 用于开发/内网环境）
  - `checkClientTrusted()` / `checkServerTrusted()` — 不验证
  - `getAcceptedIssuers()` — 返回空数组
- `$ca` — 自定义 `ProxySelector`（103 strings）
  - 使用反射获取代理配置
  - 支持 HTTP 代理和 SOCKS 代理
  - 代理失败时打印 "Connection to proxy failed for URI:"

**关键依赖**:
- OpenTelemetry SDK 1.36.0（`io/opentelemetry/sdk/*`）
- OTLP HTTP Exporter（`io/opentelemetry/exporter/otlp/http/trace/*`）
- W3C Trace Context + W3C Baggage 传播
- `AICodeSettingsState.apmUrl` — APM 端点 URL

### 3.2 OpenTelemetryService (131 strings)

**路径**: `com/aicode/apm/OpenTelemetryService`
**职责**: OpenTelemetry 服务 — APM 配置管理和初始化

**关键方法**:
- `handApmConfig()` — 处理 APM 配置
  - 从 `AICodeSettingsState` 获取 `apmUrl` 和 `apmEnable`
  - 解析 Agent 推送的配置 JSON（使用 Gson `JsonObject`）
  - 检查 `apmEnable` 开关
  - 日志："是否开启APM===>,agent push opentelemetry url is ..."
  - 调用 `OpenTelemetryConfig.init()` 初始化
  - 设置 `TracerEnum.IDEA_RUN` tracer
- APM 连接检查
  - 使用 OkHttp 发送请求到 APM 端点
  - 连接失败日志："APM连接失败"

**关键依赖**:
- `AICodeSettingsState` — 设置（apmUrl, apmEnable）
- `OpenTelemetryConfig` — 配置初始化
- `Gson JsonObject` — JSON 解析
- `OkHttp` — APM 连接检查
- `BasicActionsBundle` — 消息包
- `GenericUtils.H()` — 混淆解码

### 3.3 OpenTelemetryUtil (74 strings)

**路径**: `com/aicode/apm/OpenTelemetryUtil`
**职责**: OpenTelemetry 工具 — Span 创建辅助

**关键方法**:
- `buildWithParent(String, Span)` — 创建子 Span（INTERNAL 类型）
- `buildWithCommand(String)` — 创建命令 Span（CLIENT 类型）
- `buildWithTracer(String)` — 创建 Tracer Span
- 使用 `GlobalOpenTelemetry.getTracer()` 获取 Tracer
- 使用 `LinkageError.getStackTrace()` 获取调用者信息（与 H() 相同技巧）

**Span 类型**:
- `SpanKind.INTERNAL` — 内部操作
- `SpanKind.CLIENT` — 客户端请求

### 3.4 SpanAttrEnum (156 strings) — APM 属性枚举

**路径**: `com/aicode/apm/enums/SpanAttrEnum`
**职责**: Span 属性枚举 — 定义所有 APM 属性名

**枚举值** (30+):
| 枚举值 | 类别 | 说明 |
|--------|------|------|
| PLUGIN_VERSION | 插件 | 插件版本 |
| IDEA_VERSION | 插件 | IDEA 版本 |
| SYSTEM_USERNAME | 系统 | 系统用户名 |
| AGENT_VERSION | Agent | Agent 版本 |
| AGENT_START_REASON | Agent | 启动原因 |
| AGENT_START_CODE | Agent | 启动代码 |
| AGENT_ERROR_REASON | Agent | 错误原因 |
| COMMAND_ID | 命令 | 命令 ID |
| COMPLETE_DURATION | 补全 | 补全耗时 |
| COMPLETE_FIRST_DURATION | 补全 | 首次补全耗时 |
| COMPLETE_IS_STREAM | 补全 | 是否流式 |
| COMPLETE_ACCEPT | 补全 | 是否接受 |
| COMPLETE_REJECT | 补全 | 是否拒绝 |
| COMPLETE_FORCE | 补全 | 是否强制 |
| COMPLETE_RESULT | 补全 | 补全结果 |
| COMPLETE_FILE_LINE | 补全 | 文件行数 |
| COMPLETE_FILE_SIZE | 补全 | 文件大小 |
| SETTING_MESSAGE_TYPE | 设置 | 消息类型 |
| SETTING_TRIGGER_ON_PAUSE | 设置 | 暂停触发 |
| SETTING_TRIGGER_TIME_DELAY | 设置 | 触发延迟 |
| SETTING_CODE_MODE | 设置 | 代码模式 |
| SETTING_JAVA_TEST | 设置 | Java 测试 |
| SETTING_JAVA_MOCK | 设置 | Java Mock |
| EXCEPTION_COMMAND | 异常 | 异常命令 |
| EXCEPTION_MESSAGE | 异常 | 异常消息 |
| EXCEPTION_CODE | 异常 | 异常代码 |
| HTTP_SCHEME | HTTP | HTTP 协议 |
| DISABLE_GPU | 系统 | GPU 禁用 |
| USER_USERNAME | 用户 | 用户名 |
| PLUGIN_UPDATE | 插件 | 插件更新 |

**关键方法**:
- `getText()` — 获取属性文本值（H() 解码）
- `getDesc()` — 获取属性描述

### 3.5 TracerEnum (73 strings) — Tracer 枚举

**路径**: `com/aicode/apm/enums/TracerEnum`
**职责**: Tracer 枚举 — 定义 APM Tracer 名称

**枚举值**:
| 枚举值 | 说明 |
|--------|------|
| IDEA_RUN | IDEA 运行 |
| CODE_COMPLETE | 代码补全 |
| CODE_COMPLETE_PARENT | 补全父 Span |
| CODE_COMPLETE_INLINE_CHAT_PARENT | 内联聊天补全父 Span |
| AGENT_RUN | Agent 运行 |
| AGENT_FAILURE | Agent 失败 |
| AGENT_ERROR | Agent 错误 |
| AGENT_RESTART | Agent 重启 |
| RECORD_EXCEPTION | 异常记录 |

**关键方法**:
- `getText()` — 获取 Tracer 文本（H() 解码）
- `getDesc()` — 获取描述
- `getType()` — 获取类型（关联 CommandEnum）

## 4. Settings 包 — 配置持久化

### 4.1 AICodeSettingsState (173 strings) — 核心设置

**路径**: `com/aicode/settings/AICodeSettingsState`
**父类**: Object
**接口**: `PersistentStateComponent<AICodeSettingsState>`
**存储**: `AICodeSettingsPlugin.xml`
**职责**: 插件核心设置 — 所有用户配置的持久化存储

**完整字段列表**:

| 字段 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| autoTrigger | String | "auto" | 自动触发模式 |
| tipType | String | INTELLIGENT_MODE | 补全类型 |
| sendKey | String | ENTER_KEY | 发送键 |
| modelCode | String | — | AI 模型代码 |
| modelInfoList | List<FunctionModelInfo> | — | 模型信息列表 |
| inlineChatModelCode | String | — | 内联聊天模型 |
| triggerTime | Integer | — | 触发延迟时间 |
| loginUrl | String | — | 登录 URL |
| feedbackUrl | String | — | 反馈 URL |
| maintainRepoUrl | String | — | 维护仓库 URL |
| codeSearchServerUrl | String | — | 代码搜索 URL |
| officialWebsiteUrl | String | — | 官网 URL |
| codeKnowledgeWebUrl | String | — | 代码知识库 URL |
| userCenterWebUrl | String | — | 用户中心 URL |
| enterpriseId | String | — | 企业 ID |
| enterpriseName | String | — | 企业名称 |
| userId | String | — | 用户 ID |
| userName | String | — | 用户名 |
| isUpdater | Boolean | — | 是否更新器 |
| testFramework | String | JUNIT_FOUR | Java 测试框架 |
| mockFramework | String | POWER_MOCK | Java Mock 框架 |
| modifyTestFrame | — | — | 修改测试框架 |
| modifyTestFramenNum | — | — | 修改测试框架数量 |
| pyTestFramework | String | UNITTEST | Python 测试框架 |
| pyMockFramework | String | UNITTESTMOCK | Python Mock 框架 |
| pyModifyTestFrame | — | — | Python 修改测试框架 |
| pyModifyTestFramenNum | — | — | Python 修改测试框架数量 |
| modelList | Map<String, String> | HashMap | 模型列表映射 |
| codeCompleteDisableLang | String[] | — | 禁用补全的语言 |
| generateUnitTestFile | — | — | 生成单测文件 |
| unitRequestInterval | Integer | — | 单测请求间隔 |
| lineToolsType | String | ICON | 行工具类型 |
| lineToolsPermissionDocComments | — | — | 文档注释权限 |
| lineToolsPermissionLineComments | — | — | 行注释权限 |
| lineToolsPermissionComments | — | — | 注释权限 |
| lineToolsPermissionFunctionSplit | — | — | 函数拆分权限 |
| lineToolsPermissionCodeOptimization | — | — | 代码优化权限 |
| lineToolsPermissionUnitTesting | — | — | 单测权限 |
| openFunctionSplit | — | — | 开启函数拆分 |
| openCodeOptimization | — | — | 开启代码优化 |
| openIFlyTest | — | — | 开启 iFlyTest |
| openInlineChat | — | — | 开启内联聊天 |
| openIFlyDBA | — | — | 开启 iFlyDBA |
| openIFlyOps | — | — | 开启 iFlyOps |
| openIFlyPm | — | — | 开启 iFlyPm |
| permissions | LinkedHashSet<String> | — | 权限集合 |
| enableCodeDebug | Boolean | — | 开启代码调试 |
| enableCodeComplete | Boolean | — | 开启代码补全 |
| openAutoUpdate | Boolean | — | 开启自动更新 |
| apmEnable | Boolean | — | 开启 APM |
| apmUrl | String | — | APM URL |
| streamOutputConfig | — | — | 流式输出配置 |
| openCodeEnhance | — | — | 开启代码增强 |
| enableCodeEnhance | — | — | 启用代码增强 |
| inlineCompletionInputStyle | — | — | 内联补全输入样式 |
| defaultLanguage | — | — | 默认语言 |
| languages | List<String> | ArrayList | 支持的语言列表 |
| showSaasQrCode | — | — | 显示 SaaS 二维码 |
| ignoreGitAuth | — | — | 忽略 Git 认证 |
| ignoreVersion | — | — | 忽略版本 |

**关键方法**:
- `getInstance()` — 获取设置实例（Application service）
- `getState()` / `loadState()` — 持久化状态读写
- `clear()` — 清空设置
- `setUnitRequestInterval()` — 设置单测请求间隔（使用 `Math.min` 限制）

**关键依赖**:
- `PersistentStateComponent` — IntelliJ 持久化组件
- `XmlSerializerUtil.copyBean()` — XML 序列化
- `PluginStartupActivity.setApiKey()` — API 密钥设置
- `TipTypeEnum.INTELLIGENT_MODE` — 补全类型默认值
- `SendKeyEnum.ENTER_KEY` — 发送键默认值
- `UnitTestBaseEnum/UnitTestMockEnum` — 测试框架默认值

### 4.2 AICodeRequestSettings (64 strings)

**路径**: `com/aicode/settings/AICodeRequestSettings`
**父类**: Object
**接口**: `PersistentStateComponent<CodeGenerateRequestState>`
**存储**: `AICodeRequestSettings.xml`
**职责**: 补全请求设置 — 代码补全请求的配置持久化

**关键方法**:
- `getState()` — 返回 `CodeGenerateRequestState`
- `loadState(CodeGenerateRequestState)` — 加载状态
- `noStateLoaded()` — 无状态加载回调

### 4.3 CodeGenerateRequestState (27 strings)

**路径**: `com/aicode/settings/CodeGenerateRequestState`
**职责**: 代码生成请求状态 — 补全请求的具体配置

**字段**:
- `inlayTextColor` — `Color` — Inlay 文本颜色（使用 ColorConverter）
- `showIdeCodeTips` — boolean — 显示 IDE 代码提示
- `showIdeCompletions` — boolean — 显示 IDE 补全
- `internalDisableHttpCache` — boolean — 内部禁用 HTTP 缓存
- `disableHttpCache` — boolean — 禁用 HTTP 缓存
- `requestLimitNotificationShown` — boolean — 请求限制通知已显示

**注解**:
- `@OptionTag(converter=ColorConverter)` — 颜色字段使用自定义转换器
- `@Nullable` — inlayTextColor 可为空

### 4.4 ColorConverter (45 strings)

**路径**: `com/aicode/settings/ColorConverter`
**父类**: `Converter<Color>`
**职责**: 颜色转换器 — AWT Color ↔ XML 字符串转换

**关键方法**:
- `fromString(String)` — 从十六进制字符串创建 Color（使用 `ColorUtil.fromHex()`）
- `toString(Color)` — 将 Color 转换为 HTML 颜色字符串（使用 `ColorUtil.toHtmlColor()`）

### 4.5 BatchUnitTestSettingsState (84 strings)

**路径**: `com/aicode/settings/BatchUnitTestSettingsState`
**父类**: Object
**接口**: `PersistentStateComponent<BatchUnitTestSettingsState>`
**存储**: `BatchUnitTestSettingsPlugin.xml`
**职责**: 批量单测设置 — 批量单元测试的配置持久化

**字段**:
- `testFramework` — String — 测试框架（默认 JUNIT_FOUR）
- `mockFramework` — String — Mock 框架（默认 POWER_MOCK）
- `testGenerationProcess` — `TestGenerationProcess` — 测试生成过程（默认 GENERATION）
- `enabledGenerateByTemplate` — `GenaratebyTemplateSwitchEnum` — 模板生成开关（默认 DISABLED）
- `testPrivate` — boolean — 测试私有方法
- `duplicateRule` — `DuplicateRule` — 重复规则（默认 COEXIST）
- `testModuleDirectory` — String — 测试模块目录
- `savePath` — String — 保存路径
- `batchTestUnitLimt` — `BatchTestUnitLimt` — 批量单测限制（默认 FIVE）

### 4.6 UnitTestSettingsState (72 strings)

**路径**: `com/aicode/settings/UnitTestSettingsState`
**父类**: Object
**接口**: `PersistentStateComponent<UnitTestSettingsState>`
**存储**: `UnitTestSettingsPlugin.xml`
**职责**: 单测设置 — 单元测试的配置持久化

**字段**:
- `testFramework` — String — 测试框架（默认 JUNIT_FOUR）
- `mockFramework` — String — Mock 框架（默认 POWER_MOCK）
- `enabledGenerateByTemplate` — `GenaratebyTemplateSwitchEnum` — 模板生成（默认 DISABLED）
- `testPrivate` — boolean — 测试私有方法
- `testClasPath` — String — 测试类路径
- `savePath` — String — 保存路径

## 5. 关键发现

1. **AutoCodeGenerateListener 是补全触发核心**: 262 个字符串，监听 CommandListener 事件，管理 Tab/Ctrl+Z 命令标记，协调自动补全和强制补全的触发逻辑。内部类 `$Q`（OperateEditorState）和 `$T`（UndoTransparentActionState）管理编辑器操作状态。

2. **GitBranchChangeListener 第二大**: 360 个字符串，3 个内部类（$H, $R, $b），监听 Git 分支变更并更新代码知识库状态。

3. **CommitHandlerFactory$o 收集 Git 指标**: 286 个字符串，是 listener 包中最大的内部类，收集 Git 提交的代码变更指标用于埋点分析。

4. **OpenTelemetryConfig 全配置**: 186 个字符串，完整配置 OTLP HTTP Exporter，包括采样率、批量处理、重试策略、代理和 SSL。自定义 TrustManager（$La）信任所有证书，自定义 ProxySelector（$ca）支持代理。

5. **30+ APM 属性**: SpanAttrEnum 定义 30+ 个属性，覆盖插件/Agent/补全/设置/异常/HTTP 六大类，每个属性通过 H() 解码获取文本值。

6. **9 个 Tracer**: TracerEnum 定义 9 个 Tracer，包括 IDEA 运行、代码补全（含父 Span 和内联聊天父 Span）、Agent 运行/失败/错误/重启、异常记录。

7. **AICodeSettingsState 50+ 字段**: 核心设置类包含 50+ 个字段，覆盖 AI 模型、测试框架、权限控制、APM、代码补全、内联聊天等所有配置。使用 `PersistentStateComponent` 持久化到 `AICodeSettingsPlugin.xml`。

8. **4 个 PersistentStateComponent**: AICodeSettingsState、AICodeRequestSettings、BatchUnitTestSettingsState、UnitTestSettingsState 分别持久化到 4 个 XML 文件。

9. **OpenTelemetryUtil 使用 LinkageError**: 与 H() 混淆相同的技巧，使用 `LinkageError.getStackTrace()` 获取调用者信息，用于 Span 名称生成。

10. **6 个系统 URL**: AICodeSettingsState 包含 6 个系统 URL（loginUrl, feedbackUrl, maintainRepoUrl, codeSearchServerUrl, officialWebsiteUrl, codeKnowledgeWebUrl, userCenterWebUrl），与 SysUrlDto 的 6 个 URL 对应。

11. **反射绕过访问限制**: AICodeUnloadPluginListener 使用反射获取 `MessageBundle.INSTANCE.clear()` 方法，说明插件卸载时需要绕过 Kotlin 编译器的访问限制。

12. **APM 连接检查**: OpenTelemetryService 使用 OkHttp 发送请求到 APM 端点验证连接，失败时记录 "APM连接失败"。