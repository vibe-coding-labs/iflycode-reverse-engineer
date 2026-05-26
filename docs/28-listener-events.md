# 28 事件监听体系

> 基于 class 文件常量池静态分析

## 监听器总览

| 监听器 | IntelliJ 接口 | 触发条件 | 核心功能 |
|--------|-------------|---------|---------|
| AutoCodeGenerateListener | CommandListener | 文档变更 | 自动代码补全触发 |
| CodeEditorListener | — | 编辑器事件 | 代码选择/覆盖层管理 |
| CodeFileEditorManagerListener | FileEditorManagerListener | 文件打开/关闭 | 同步文档列表到 Agent |
| CodeLookupManagerListener | LookupManagerListener | 代码补全弹出 | 补全交互控制 |
| CommitHandlerFactory | CheckinHandlerFactory | VCS 提交 | 提交前取消补全请求 |
| FileWatchedAdapter | — | 文件系统变更 | Git 仓库文件监控 |
| GitBranchChangeListener | GitRepositoryChangeListener | Git 分支切换 | 仓库状态同步 |
| PluginDocumentListener | DocumentListener | 文档内容变更 | Diff 检测 |
| PluginManagerListener | — | 插件状态变更 | 内联聊天状态管理 |
| ThemeChangeListener | LafManagerListener + EditorColorsListener | 主题/颜色变更 | UI 主题同步 |
| AICodeUnloadPluginListener | — | 插件卸载 | 清理补全状态 |

## AutoCodeGenerateListener — 自动补全触发器

```
实现: CommandListener (IntelliJ)

核心依赖:
  RequestTipServiceImpl  — 发送补全请求
  EditorManagerService   — 管理编辑器 Inlay 状态
  DocumentActionTracker  — 追踪文档操作
  CommandCache           — 命令缓存
  CodeTipRequestDto      — 补全请求 DTO

内部类:
  $Q — 编辑器状态快照 (用于去重判断)
  $T — 补全触发条件判断

流程:
  1. Document 变更 → CommandEvent 触发
  2. 获取当前 Editor、Document、CaretModel
  3. 检查 AICodeSettingsState 是否启用自动触发
  4. 判断 CodeTipRequestType (Automatic/Interact/Forced/Manual)
  5. 构建 CodeTipRequestDto
  6. 调用 RequestTipServiceImpl 发送请求
  7. EditorManagerService 管理 Inlay 生命周期
```

## CodeFileEditorManagerListener — 文件切换同步

```
实现: FileEditorManagerListener

核心依赖:
  PluginWebsocketClient  — WebSocket 通信
  RecentFilesManager     — 最近文件管理
  MessageDto             — 消息 DTO
  CodeInfoDto            — 代码信息 DTO
  CommandEnum            — 命令枚举

流程:
  1. 文件打开/关闭事件触发
  2. 构建 MessageDto (command = action_sync_document_list)
  3. 填充 CodeInfoDto (文件路径、语言、范围)
  4. 通过 WebSocket 发送到 Agent
  5. Agent 更新文档列表缓存
```

## CodeLookupManagerListener — 补全弹出控制

```
实现: LookupManagerListener

核心依赖:
  EditorManagerService   — 编辑器管理
  EditorUtil             — 编辑器工具
  CodeGenerateRequestState — 代码生成请求状态
  AICodeRequestSettings  — 请求设置

流程:
  1. IntelliJ 代码补全弹出时触发
  2. 检查是否有 iFlyCode Inlay 存在
  3. 如果有，调用 disposeTips(editor, OperateActionEnum)
  4. 防止 IntelliJ 补全和 iFlyCode 补全同时显示
```

## GitBranchChangeListener — Git 状态同步

```
实现: GitRepositoryChangeListener

核心依赖:
  PluginWebsocketClient     — WebSocket 通信
  SocketMessageHandleListener — 消息处理
  ChatService              — 对话服务
  CommandEnum              — 命令枚举
  PermissionEnum           — 权限枚举
  GitRepoStatusEnum        — Git 仓库状态
  WebViewDataTypeEnum      — WebView 消息类型
  InlineChatStatusServiceKt — 内联聊天状态
  AICodeSettingsState      — 设置

内部类:
  $H — Git 状态处理器
  $R — 仓库状态回调
  $b — 分支变更回调

流程:
  1. Git 分支切换/仓库状态变更触发
  2. 获取 GitRepoStatusEnum (当前仓库状态)
  3. 构建 WebRequestDto 发送到 Agent
  4. 通过 ChatService 发送 git_repo_status 命令
  5. 通过 WebView 推送仓库状态到 UI
  6. InlineChatStatusService 更新内联聊天可用性
```

## PluginManagerListener — 插件状态管理

```
核心依赖:
  PluginWebsocketClient  — WebSocket 通信
  ChatService            — 对话服务
  SqlService             — SQL 服务
  SessionController      — 内联聊天会话控制
  ChatInputController    — 聊天输入控制
  InlineChatInfo         — 内联聊天信息
  InlineChatStepEnum     — 内联聊天步骤
  CancelRequestTip       — 取消补全请求

流程:
  1. 插件状态变更触发
  2. 检查 InlineChatStepEnum 状态
  3. 如果有活跃的内联聊天，发送取消命令
  4. 通过 SessionController 管理会话生命周期
```

## ThemeChangeListener — 主题同步

```
实现: LafManagerListener + EditorColorsListener

核心依赖:
  SocketMessageHandleListener — 消息处理
  WebViewResponseTypeEnum    — 响应类型
  Icons                      — 图标
  StatusBarPopup             — 状态栏
  FileExtensionLanguageDetails — 文件扩展名
  CancelRequestTip           — 取消补全

流程:
  1. IDE 主题/颜色方案变更触发
  2. 更新 Icons (亮/暗主题图标)
  3. 更新 StatusBarPopup 样式
  4. 通过 WebView 推送主题变更通知 (setting_change_theme)
  5. 取消当前补全请求 (避免样式不一致)
```

## AICodeUnloadPluginListener — 插件卸载清理

```
核心依赖:
  EditorManagerService   — 编辑器管理
  CodeCompleteService    — 代码补全服务
  MethodGeneratorConfig  — 方法生成器配置
  OperateActionEnum      — 操作枚举
  PluginInfoUtils        — 插件信息
  MessageBundle          — 消息包

流程:
  1. 插件卸载/禁用时触发
  2. 调用 EditorManagerService.disposeTips() 清除所有 Inlay
  3. 停止 CodeCompleteService
  4. 清理 MethodGeneratorConfig 缓存
```

## 事件流总结

```
用户输入字符
    → AutoCodeGenerateListener (自动补全)
    → RequestTipServiceImpl → WebSocket → Agent → 云端

用户切换文件
    → CodeFileEditorManagerListener (文档同步)
    → WebSocket → Agent (action_sync_document_list)

用户切换 Git 分支
    → GitBranchChangeListener (仓库状态)
    → ChatService → WebSocket → Agent (git_repo_status)

用户弹出 IntelliJ 补全
    → CodeLookupManagerListener (补全冲突)
    → EditorManagerService.disposeTips() (清除 iFlyCode Inlay)

IDE 主题变更
    → ThemeChangeListener (UI 同步)
    → WebView (setting_change_theme)

插件卸载
    → AICodeUnloadPluginListener (清理)
    → EditorManagerService.disposeTips() + CodeCompleteService.stop()
```
