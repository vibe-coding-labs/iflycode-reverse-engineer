# iFlyCode Plugin.xml 与插件配置分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-11

## 1. 插件基本信息

| 属性 | 值 |
|------|-----|
| Plugin ID | `com.iflytek` |
| 名称 | iFlyCode |
| 版本 | 3.4.2-222 |
| 最低 IDE 版本 | 222.3345.118 (IntelliJ 2022.2+) |
| 厂商 | Anhui Zhuojian Technology Co., Ltd (安徽卓健科技有限公司) |
| 官网 | https://portal.example.com/ |

## 2. 依赖声明

| 依赖 | 必需 | 配置文件 | 说明 |
|------|------|---------|------|
| `com.intellij.modules.platform` | 是 | - | IntelliJ 平台核心 |
| `com.intellij.modules.lang` | 是 | - | 语言支持模块 |
| `Git4Idea` | 是 | - | Git 集成 |
| `com.intellij.modules.java` | 否 | `code-java.xml` | Java 语言支持 |
| `com.intellij.modules.python` | 否 | `code-python.xml` | Python 语言支持 |
| `com.intellij.modules.webstorm` | 否 | `code-javascript.xml` | JavaScript 语言支持 |

## 3. 扩展点声明

### 3.1 自定义扩展点

| 扩展点 | 接口 | 动态 | 用途 |
|--------|------|------|------|
| `ai.code.plugin.languageIdSupport` | `com.aicode.service.LanguageInfoSupport` | 是 | 语言 ID 支持 |
| `ai.code.plugin.editorSupport` | `com.aicode.service.EditorSupport` | 是 | 编辑器支持 |

### 3.2 IntelliJ 扩展点注册

#### Application Services

| 服务接口 | 实现类 | 说明 |
|---------|--------|------|
| - | `com.aicode.settings.AICodeSettingsState` | AI 代码设置状态 |
| - | `com.aicode.settings.UnitTestSettingsState` | 单测设置状态 |
| - | `com.aicode.settings.BatchUnitTestSettingsState` | 批量单测设置状态 |
| - | `com.aicode.apm.OpenTelemetryService` | OpenTelemetry 遥测服务 |
| - | `com.aicode.settings.AICodeRequestSettings` | 请求设置 |
| - | `com.aicode.status.AICodeStatusService` | 状态服务 |
| - | `com.aicode.service.editor.DocumentActionTracker` | 文档操作追踪 |
| `EditorManagerService` | `EditorManagerServiceImpl` | 编辑器管理服务 |
| `RequestTipService` | `RequestTipServiceImpl` | 补全请求服务 |
| `PluginAgentProcessService` | `RestartableAgentProcessService` | Agent 进程服务 |
| - | `com.aicode.inline.InlineChatService` | 内联聊天服务 |

#### Project Services

| 服务接口 | 实现类 | 说明 |
|---------|--------|------|
| - | `com.aicode.action.batch.ResultTree` | 批量单测结果树 |

#### Project Components

| 实现类 | 说明 |
|--------|------|
| `com.aicode.listener.PluginDocumentListener` | 插件文档监听器 |

#### Application Components

| 实现类 | 说明 |
|--------|------|
| `com.aicode.listener.ThemeChangeListener` | 主题变更监听 |

## 4. Action 注册

### 4.1 代码补全 Actions

| Action ID | 类 | 快捷键 | 说明 |
|-----------|-----|--------|------|
| `AICode.applyInlays` | `AcceptInlaysAction` | Tab | 接受补全 |
| `AICode.applyWordInlays` | `AcceptWordInlaysAction` | Ctrl+Right | 逐词采纳 |
| `AICode.applyLineCodeInlays` | `AcceptLineCodeInlaysAction` | Ctrl+Down | 逐行采纳 |
| `AICode.disposeInlays` | `DisposeInlaysAction` | Esc | 清除补全 |
| `AICode.cyclePrevInlays` | `CyclePreviousEditorInlays` | Alt+[ / Ctrl+, | 上一个补全 |
| `AICode.cycleNextInlays` | `CycleNextEditorInlays` | Alt+] / Ctrl+. | 下一个补全 |
| `AICode.requestCompletions` | `RequestCodeGenerateAction` | Alt+\ / Alt+C | 触发补全 |

### 4.2 状态栏 Actions

| Action ID | 类 | 说明 |
|-----------|-----|------|
| `AICode.userInfo` | `UserInfoAction` | 用户信息 |
| `AICode.enableAutoTrigger` | `EnableAutoTriggerCodeGenerateAction` | 自动触发开关 |
| `AICode.setting` | `PluginSettingAction` | 插件设置 |
| `AICode.LogoutAction` | `LogoutAction` | 登出 |

### 4.3 Inline Chat Actions

| Action ID | 类 | 快捷键 | 说明 |
|-----------|-----|--------|------|
| `AICode.InlineChat.InlineChatStopAction` | `InlineChatStopAction` | Alt+Z | 停止编辑 |
| `AICode.InlineChat.InlineChatUndoAction` | `InlineChatUndoAction` | Ctrl+Z / Cmd+Z | 撤销 |
| `AICode.InlineChat.AcceptAction` | `InlineChatAcceptAction` | Alt+Y | 接受 |
| `AICode.InlineChat.RejectAction` | `InlineChatRejectAction` | Alt+X | 拒绝 |
| `AICode.InlineChat.RetryAction` | `InlineChatRetryAction` | Alt+D | 重试 |

### 4.4 其他 Actions

| Action ID | 类 | 说明 |
|-----------|-----|------|
| `AICode.openWindow` | `OpenWindowAction` | Ctrl+Q 打开窗口 |
| `TriggerCodeProblemsTreePopupAction` | `CodeProblemsTreePopupAction` | 一键修复 |

### 4.5 Action Groups

| Group ID | 包含 | 说明 |
|----------|------|------|
| `AICode.inlayContextMenu` | applyInlays, applyWordInlays | 补全右键菜单 |
| `AICode.statusBarPopup` | userInfo, enableAutoTrigger, setting, LogoutAction | 状态栏弹出菜单 |
| `AICode.InlineChat` | Stop, Undo, Accept, Reject, Retry | 内联聊天操作 |
| `AICodeEditorPopup` | `aicode.EditorActionGroup` | 编辑器右键菜单 "星火飞码 iFlyCode" |

## 5. Listener 注册

### 5.1 Application Listeners

| Topic | Listener | 说明 |
|-------|----------|------|
| `AnActionListener` | `DocumentActionTracker$ActionListener` | 全局 Action 追踪 |
| `DynamicPluginListener` | `AICodeUnloadPluginListener` | 插件卸载监听 |
| `AppLifecycleListener` | `ApplicationStartupListener` | 应用启动监听 |
| `ProjectManagerListener` | `PluginManagerListener` | 项目管理监听 |

### 5.2 Project Listeners

| Topic | Listener | 说明 |
|-------|----------|------|
| `LookupManagerListener` | `CodeLookupManagerListener` | 代码查找监听 |
| `CommandListener` | `AutoCodeGenerateListener` | 自动补全触发 |
| `FileEditorManagerListener` | `CodeFileEditorManagerListener` | 文件编辑器监听 |
| `SocketMessageListener` | `SocketMessageHandleListener` | WebSocket 消息监听 |

## 6. 其他扩展

| 扩展点 | 实现类 | 说明 |
|--------|--------|------|
| `postStartupActivity` | `PluginStartupActivity` | 启动后初始化 |
| `jvm.exceptionFilter` | `DebuggerFilter` | JVM 异常过滤 |
| `intentionAction` | `CodeProblemsIntentionAction` | 代码问题意图 |
| `statusBarWidgetFactory` | `StatusBarWidgetFactory` | 状态栏小部件 |
| `typedHandler` | `TipTypedHandlerDelegate` | 输入处理 (order: first, before completionAutoPopup) |
| `actionPromoter` | `CodePromoterAction` | Action 推广 |
| `actionPromoter` | `TipPromoterAction` | 补全推广 |
| `notificationGroup` | `aicode.notice` (BALLOON) | 通知组 |
| `checkinHandlerFactory` | `CommitHandlerFactory` | 提交处理器 |
| `languageIdSupport` | `AICodeExtendedLanguageSupport` | 扩展语言支持 |
| `languageIdSupport` | `CodeLanguageInfoSupport` (last) | 代码语言支持 |

## 7. 资源包

- **消息包**: `messages.BasicActionsBundle` — UI 文本和标签
- **描述**: 包含完整的中英双语功能描述，涵盖 10 大功能

## 8. 版本历史要点

从 `change-notes` 提取的关键版本信息：

| 版本 | 关键特性 |
|------|---------|
| 3.4.1 | 多模型管理/切换，SQL 支持 TxSQL |
| 3.4.0 | 流程图生成，文件引用，iFlyDev/iFlyOps/iFlyPm/iFlyDBA 助理，inlinechat (私有化版) |
| 3.3.1 | Go/C# 支持，批量函数注释，自动更新 |
| 3.3.0 | 实验功能设置，Beta 标识，玩法推荐 |
| 3.2.6 | 批量单测(企业版)，代码预评审(个人/团队版) |
| 3.2.4 | 通用助理、测试助理、代码优化、函数拆分 |
| 3.2.0 | C 语言支持，JS 箭头函数，APM 监控，C/C++ 单测 |
| 3.0.0 | 产品架构和 UI 全新升级，星火大模型最新版本 |
| 2.2.0 | 历史会话，SQL 多数据源 |
| 2.0.0 | SQL 生成/优化，代码调试，文档注释 |
| 1.0.0 | 行级/函数级补全，单元测试，代码解释，智能问答 |
