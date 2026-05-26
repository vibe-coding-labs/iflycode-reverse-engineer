# 25 内联聊天 UI 架构

> 基于 class 文件常量池静态分析

## 内联聊天组件层级

```
InlineChatTopPanel (顶部面板)
    ├── InlineChatInputPanel (输入面板)
    │   ├── InlineChatInputComponent (输入组件)
    │   │   ├── 文本输入框
    │   │   ├── 发送按钮 (SendMessageAction)
    │   │   └── 停止按钮 (StopAction)
    │   ├── SendStopActionButtonPanel (发送/停止按钮组)
    │   ├── ChatInputController (输入控制器)
    │   ├── AICodeSettingsState (设置)
    │   ├── UserService (用户服务)
    │   └── CodeModel/FunctionModelInfo (模型选择)
    ├── CloseInlineChatAction (关闭按钮)
    └── InlineChatInputBorderFocusListener (焦点监听)

InlineChatPanel (主面板)
    ├── InlineChatInputPanel (输入面板)
    ├── InlineChatInputComponent (输入组件)
    ├── FileService (文件服务 — Diff 展示)
    └── 内部类:
        ├── $02 — 响应处理器
        ├── $03 — 错误处理器
        ├── $r — 渲染回调
        └── $x — 状态更新回调

InlineChatInlay (Inlay 渲染)
    ├── GenericUtils (Diff 工具)
    ├── RequestTimeoutException (超时处理)
    └── 内部类:
        ├── $01 — 创建处理器
        ├── $02 — 更新处理器
        └── $u — UI 回调
```

## 操作 Action 层级

```
PluginAnAction (基类)
    └── InlineChatAction (内联聊天操作基类)
        ├── InlineChatAcceptAction  — 接受修改
        │   └── EditorKt (编辑器操作)
        ├── InlineChatRejectAction  — 拒绝修改
        │   └── InlineChatService (服务)
        ├── InlineChatRetryAction   — 重试
        │   ├── SessionController (会话控制)
        │   ├── InlineChatInfo (聊天信息)
        │   └── EditorKt (编辑器操作)
        ├── InlineChatStopAction    — 停止生成
        │   ├── SessionController (会话控制)
        │   ├── InlineChatInfo (聊天信息)
        │   ├── InlineChatStepEnum (步骤状态)
        │   └── EditorKt (编辑器操作)
        └── InlineChatUndoAction   — 撤销修改
            └── InlineChatService (服务)

独立 Action:
    CloseInlineChatAction  — 关闭内联聊天
        ├── InlineChatService (服务)
        ├── InlineChatStatusServiceKt (状态服务)
        └── EditorKt (编辑器操作)

    OpenInlineChatAction  — 打开内联聊天
        ├── InlineChatService (服务)
        ├── HandleCacheUtil (缓存)
        └── Maps (映射)

    SendMessageAction  — 发送消息
        ├── FileService (文件服务)
        ├── Icons (图标)
        └── InlineChatStatusServiceKt (状态)

    StopAction  — 停止
        ├── PropertyUtils.H() (字符串解码)
        └── Maps (映射)
```

## 渲染器体系

```
InlayRendering (渲染管理)
    ├── InlineChatBtnPanelRenderer — 操作按钮面板
    │   ├── EditorUtils (编辑器工具)
    │   ├── Icons (图标)
    │   ├── InlineChatOperateEnum (操作类型: INSERT/EDIT)
    │   ├── BasicActionsBundle (消息)
    │   ├── Maps (映射)
    │   └── 内部类: $O (按钮回调), $U (样式回调)
    │
    ├── InlineChatCategoryPanelRenderer — 分类选择面板
    │   ├── GitReviewService (引用)
    │   ├── FileService (文件服务)
    │   ├── BasicActionsBundle (消息)
    │   └── 内部类: $t (分类回调), $w (选择回调)
    │
    ├── InlineChatErrorPanelRenderer — 错误面板
    │   ├── IdeAction (IDE 操作)
    │   ├── AICodeLanguageInfo (语言信息)
    │   ├── BasicActionsBundle (消息)
    │   └── StringUtils (字符串工具)
    │   └── 内部类: $n (错误回调), $y (重试回调)
    │
    └── InlineChatStopPanelRenderer — 停止面板
    │   ├── BasicActionsBundle (消息)
    │   ├── Maps (映射)
    │   └── 内部类: $N (停止回调), $P (样式回调)
```

## InlineChatHandleService — 内联聊天处理核心

```
核心依赖:
  InlineChatCommandService — 命令服务
  MessageDto              — 消息 DTO
  CodeInfoDto             — 代码信息 DTO
  SessionController       — 会话控制
  InlineChatCategoryEnum  — 分类枚举
  InlineChatOperateEnum   — 操作枚举
  FileExtensionEnum       — 文件扩展名
  GeneratorConfig         — 生成器配置
  MethodGeneratorConfig   — 方法生成器配置

关键方法:
  handleChat(SessionController, String, InlineChatCategoryEnum)
    — 处理内联聊天请求

  handleResponse(SessionController, String, Editor, int, Document, int, List)
    — 处理 Agent 响应

  handleOperate(InlineChatOperateEnum)
    — 处理用户操作 (接受/拒绝/重试)

  canHandle(Editor, List, Document, List, SessionController): boolean
    — 判断是否可以处理请求
```

## 状态管理

```
InlineChatStatusService — 状态订阅管理
  subscribe(Function0<Unit>): InlineChatStatusSubscription
    — 注册状态变更监听器

InlineStatusService — 状态服务
  引用: ChatInputController, InlineChatStatusService, Maps
  功能: 管理内联聊天全局状态 (活跃/空闲/错误)

InlineChatStepEnum — 步骤状态
  CATEGORY → 分类选择阶段
  LOADING  → 等待响应阶段
  ERROR    → 错误阶段
  SUCCESS  → 成功阶段
```

## 内联聊天完整流程

```
1. 用户触发 OpenInlineChatAction
2. InlineChatService 创建 InlineChatInlay
3. InlineChatTopPanel 显示输入面板
4. InlineChatCategoryPanelRenderer 显示分类选择
   (DOC/LINEDOC/EDIT/GENERATE)
5. 用户选择分类 → InlineChatHandleService.handleChat()
6. 构建 MessageDto (command = dialog_edit)
7. WebSocket 发送到 Agent
8. Agent 转发到云端
9. 流式响应 → InlineChatStreamHandleService 处理
10. InlineChatInlay 更新渲染
11. InlineChatBtnPanelRenderer 显示操作按钮

用户操作:
  Accept → InlineChatAcceptAction → 应用修改 → log_accept
  Reject → InlineChatRejectAction → 撤销修改 → log_reject
  Retry  → InlineChatRetryAction → 重试请求
  Stop   → InlineChatStopAction → 中止生成
  Undo   → InlineChatUndoAction → 撤销已应用的修改
  Close  → CloseInlineChatAction → 关闭面板
```

## KeyStrokeHandler — 按键处理

```
KeyStrokeExecutorProvider — 按键执行器工厂
  provide(Editor): KeyStrokeHandler

KeyStrokeHandler — 按键处理
  处理内联聊天中的键盘快捷键:
  Enter    → 发送消息
  Shift+Enter → 换行
  Esc      → 关闭内联聊天
  Tab      → 接受建议
```