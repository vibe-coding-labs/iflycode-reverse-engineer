# 24 Action 体系完整分析

> 基于 class 文件常量池静态分析 + 已反编译源码

## Action 体系架构

```
AnAction (IntelliJ 基类)
    │
    ├── PluginAnAction (com.aicode.action.click)
    │   ├── 基础 iFlyCode Action
    │   ├── 使用 PropertyUtils.H() 解码字符串
    │   └── 处理 RequestCancelException
    │
    ├── BaseAction (com.aicode.action.click)
    │   ├── 继承 PluginAnAction
    │   ├── 核心右键菜单操作基类
    │   ├── 引用: ChatService, CommonService, WebViewWindowPanel
    │   ├── 引用: CommandEnum, PermissionEnum, PageEnum
    │   ├── 引用: FirstChatMessage, AICodeSettingsState
    │   └── 使用 PropertyUtils.H() 解码字符串
    │
    └── 具体操作 (继承 BaseAction)
        ├── CodeOptimizeAction    — 代码优化 (权限: code_optimization)
        ├── ExplainCodeAction     — 代码解释 (权限: comments)
        ├── DocumentCommentAction — 文档注释 (权限: doc_comments)
        ├── InlineCommentAction   — 行间注释 (权限: line_comments)
        ├── FunctionSplitAction   — 函数拆分 (权限: function_split)
        ├── CodeCheckAction       — 代码检查
        ├── OpenInlayInlineChatAction — 内联聊天 (权限: inline_chat)
        ├── TerminalAction        — 终端操作
        └── UnitTestAction        — 单元测试 (权限: unit_testing)
```

## Action 分组

### 编辑器操作 Action (com.aicode.action)

| Action | 功能 | 触发方式 |
|--------|------|---------|
| AcceptInlaysAction | 接受补全 Inlay | 快捷键 |
| AcceptLineCodeInlaysAction | 接受行级补全 | 快捷键 |
| AcceptWordInlaysAction | 接受逐词补全 | 快捷键 |
| CycleNextEditorInlays | 切换下一个补全建议 | 快捷键 |
| CyclePreviousEditorInlays | 切换上一个补全建议 | 快捷键 |
| DisposeInlaysAction | 清除所有补全 Inlay | 快捷键/ESC |
| RequestCodeGenerateAction | 请求代码生成 | 菜单 |
| EnableAutoTriggerCodeGenerateAction | 自动触发开关 | 设置 |
| TipPromoterAction | 补全推荐 | 自动 |
| CodePromoterAction | 代码推荐 | 自动 |

### 窗口/导航 Action

| Action | 功能 |
|--------|------|
| OpenWindowAction | 打开 iFlyCode 窗口 |
| PluginSettingAction | 打开插件设置 |
| LogoutAction | 登出 |
| RefreshAction | 刷新 (Agent 重启) |
| UserInfoAction | 显示用户信息 |
| CommitMessageSuggestionAction | 生成提交信息建议 |
| CodeProblemsIntentionAction | 代码问题意图操作 |
| CodeProblemsTreePopupAction | 代码问题树弹出 |

### 批量单测 Action (com.aicode.action.batch)

| Action | 功能 |
|--------|------|
| BatchUTGeneratorAction | 触发批量单测生成 |
| BatchFunctionCommentAction | 批量函数注释 |

## ActionsUtil — Action 注册中心

`ActionsUtil` 负责将 iFlyCode Action 注册到 IntelliJ Action 系统：

```
引用的 Action:
  - CommitMessageSuggestionAction
  - OpenWindowAction
  - PrepushReviewAction
  - BatchUTGeneratorAction
  - BatchFunctionCommentAction
  - OpenInlayInlineChatAction
  - TerminalAction

引用的枚举:
  - CommandEnum (命令映射)
  - PermissionEnum (权限控制)

使用的 IntelliJ API:
  - ActionManager.registerAction()
  - DefaultActionGroup.addAction()
  - KeymapUtil (快捷键绑定)
```

## UnitTestAction — 单元测试入口

```
UnitTestAction 继承 BaseAction

引用的服务:
  - UnitTestService (Java 单测)
  - CppTestService (C++ 单测)
  - WebViewWindowPanel (UI)
  - PluginWebsocketClient (通信)

引用的配置:
  - GeneratorConfig (生成器配置)
  - AICodeSettingsState (设置)
  - LanguageEnum (语言判断)

引用的 UI:
  - BasicActionsBundle (消息)
  - AICodeLanguageInfo (语言信息)

流程:
  1. 获取当前 Editor 和 PsiFile
  2. 判断语言 (Java/Cpp/其他)
  3. 检查权限 (PermissionEnum.UNIT_TESTING)
  4. 调用对应 Service 处理
  5. 通过 WebView 展示结果
```

## AutoCodeGenerateListener — 自动补全触发

```
实现: CommandListener (IntelliJ)

引用的核心类:
  - RequestTipServiceImpl (补全请求)
  - EditorManagerService (编辑器管理)
  - DocumentActionTracker (文档追踪)
  - CommandCache (命令缓存)
  - CodeTipRequestDto (补全请求 DTO)

触发条件:
  - 用户输入字符 (Document 变更)
  - 满足自动触发条件 (AICodeSettingsState)
  - 非选择状态、非命令模式

流程:
  1. Document 变更事件触发
  2. 检查是否满足自动补全条件
  3. 构建 CodeTipRequestDto
  4. 调用 RequestTipServiceImpl 发送请求
  5. EditorManagerService 管理 Inlay 状态
```

## 权限控制映射

| PermissionEnum | 对应 Action | 对应 CommandEnum |
|---------------|------------|-----------------|
| CODE_OPTIMIZATION | CodeOptimizeAction | code_optimize |
| COMMENTS | ExplainCodeAction | code_explain |
| UNIT_TESTING | UnitTestAction | code_test |
| DOC_COMMENTS | DocumentCommentAction | code_comment |
| LINE_COMMENTS | InlineCommentAction | code_inline_comment |
| FUNCTION_SPLIT | FunctionSplitAction | code_split |
| INLINE_CHAT | OpenInlayInlineChatAction | dialog_edit |
