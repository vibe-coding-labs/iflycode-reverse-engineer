# iFlyCode Action 体系完整分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-11

## 1. 概述

iFlyCode 注册了 23+ 个 IntelliJ Action，分布在 `com/aicode/action/` 包中。Action 是 IntelliJ 插件与用户交互的核心入口，通过菜单、快捷键、右键菜单等方式触发。

## 2. Action 类清单

### 2.1 代码补全类

| Action | 快捷键 | 说明 |
|--------|--------|------|
| CodeCompleteAction | - | 手动触发代码补全 |
| CodeCompleteTabAction | Tab | Tab 键接受补全建议 |
| CodeCompleteCtrlRightAction | Ctrl+Right | Ctrl+Right 接受补全到下一个词 |

### 2.2 内联聊天类

| Action | 快捷键 | 说明 |
|--------|--------|------|
| InlineChatAction | Alt+\ | 打开内联聊天 |
| InlineChatStopAction | Alt+Z | 停止内联聊天生成 |
| InlineChatAcceptAction | Alt+Y | 接受内联聊天建议 |
| InlineChatRejectAction | Alt+X | 拒绝内联聊天建议 |
| InlineChatRetryAction | Alt+D | 重试内联聊天 |

### 2.3 Git 评审类

| Action | 快捷键 | 说明 |
|--------|--------|------|
| PrepushReviewAction | - | 预推送代码评审 |
| CommitMessageSuggestionAction | - | AI 生成提交信息 |

### 2.4 单元测试类

| Action | 快捷键 | 说明 |
|--------|--------|------|
| UnitTestAction | - | 单元测试生成 |
| BatchUTGeneratorAction | - | 批量单测生成 |

### 2.5 代码检查类

| Action | 快捷键 | 说明 |
|--------|--------|------|
| CodeCheckAction | - | 代码检查 |

### 2.6 代码搜索类

| Action | 快捷键 | 说明 |
|--------|--------|------|
| CodeSearchAction | - | 代码搜索 |

### 2.7 SQL 助手类

| Action | 快捷键 | 说明 |
|--------|--------|------|
| SqlChatAction | - | SQL 对话 |

### 2.8 其他

| Action | 快捷键 | 说明 |
|--------|--------|------|
| LoginAction | - | 登录 |
| LogoutAction | - | 登出 |
| SettingsAction | - | 打开设置 |
| AboutAction | - | 关于 |
| FeedbackAction | - | 反馈 |

## 3. Action 继承体系

```
AnAction (IntelliJ 基类)
├── CodeCompleteAction
│   └── CodeCompleteTabAction (扩展 Tab 键接受)
│   └── CodeCompleteCtrlRightAction (扩展 Ctrl+Right 接受)
├── InlineChatAction
├── InlineChatStopAction
├── InlineChatAcceptAction
├── InlineChatRejectAction
├── InlineChatRetryAction
├── PrepushReviewAction
├── CommitMessageSuggestionAction
├── UnitTestAction
├── BatchUTGeneratorAction
├── CodeCheckAction
├── CodeSearchAction
├── SqlChatAction
├── LoginAction
├── LogoutAction
└── ...
```

## 4. Action 注册 (plugin.xml)

### 4.1 主菜单组

```xml
<group id="AICode.MainMenu" text="iFlyCode" popup="true">
  <action id="AICode.Login" class="LoginAction"/>
  <action id="AICode.Logout" class="LogoutAction"/>
  <separator/>
  <action id="AICode.Settings" class="SettingsAction"/>
  <action id="AICode.About" class="AboutAction"/>
  <action id="AICode.Feedback" class="FeedbackAction"/>
</group>
```

### 4.2 编辑器右键菜单

```xml
<group id="AICode.EditorPopupMenu" text="iFlyCode">
  <action id="AICode.InlineChat" class="InlineChatAction"/>
  <action id="AICode.CodeCheck" class="CodeCheckAction"/>
  <action id="AICode.UnitTest" class="UnitTestAction"/>
  <separator/>
  <action id="AICode.CodeSearch" class="CodeSearchAction"/>
  <action id="AICode.SqlChat" class="SqlChatAction"/>
</group>
```

### 4.3 快捷键绑定

```xml
<action id="AICode.CodeCompleteTab" class="CodeCompleteTabAction">
  <keyboard-shortcut keymap="$default" first-keystroke="TAB"/>
</action>
<action id="AICode.InlineChat" class="InlineChatAction">
  <keyboard-shortcut keymap="$default" first-keystroke="alt BACK_SLASH"/>
</action>
<action id="AICode.InlineChatStop" class="InlineChatStopAction">
  <keyboard-shortcut keymap="$default" first-keystroke="alt Z"/>
</action>
<action id="AICode.InlineChatAccept" class="InlineChatAcceptAction">
  <keyboard-shortcut keymap="$default" first-keystroke="alt Y"/>
</action>
<action id="AICode.InlineChatReject" class="InlineChatRejectAction">
  <keyboard-shortcut keymap="$default" first-keystroke="alt X"/>
</action>
<action id="AICode.InlineChatRetry" class="InlineChatRetryAction">
  <keyboard-shortcut keymap="$default" first-keystroke="alt D"/>
</action>
```

## 5. Action 触发流程

### 5.1 代码补全流程

```
用户输入 → DocumentListener.onDocumentChange()
  └── 防抖 (triggerTime ms)
      └── RequestTipService.requestTip(editor, offset)
          └── WebSocket: CODE_COMPLETE → Agent

用户按 Tab → CodeCompleteTabAction.actionPerformed()
  └── 接受补全建议 → Document.insertString()

用户按 Ctrl+Right → CodeCompleteCtrlRightAction.actionPerformed()
  └── 接受补全到下一个词 → 部分插入
```

### 5.2 内联聊天流程

```
用户选中文本 → Alt+\ → InlineChatAction.actionPerformed()
  └── InlineChatController.startSession()
      └── InlineChatCommandService → WebSocket: INLINECHAT_DIRECT

用户按 Alt+Y → InlineChatAcceptAction
  └── 应用代码修改

用户按 Alt+X → InlineChatRejectAction
  └── 撤销代码修改

用户按 Alt+Z → InlineChatStopAction
  └── WebSocket: ABORT

用户按 Alt+D → InlineChatRetryAction
  └── 重新发送请求
```

### 5.3 Git 评审流程

```
用户执行 Git Push → PrepushReviewAction
  └── GitReviewService.sendGitDiffRequest()
      └── WebSocket: GIT_DIFF → Agent

用户点击 Commit → CommitMessageSuggestionAction
  └── GitReviewService.getCommitMessage()
      └── WebSocket: GIT_DIFF → Agent (请求提交信息)
```

## 6. 关键发现

1. **Tab 键冲突**: `CodeCompleteTabAction` 拦截 Tab 键，可能与 IDE 原生的 Tab 缩进功能冲突。iFlyCode 通过检查是否有活跃的补全建议来决定是否拦截。

2. **5 个内联聊天快捷键**: Alt+\（打开）、Alt+Z（停止）、Alt+Y（接受）、Alt+X（拒绝）、Alt+D（重试），构成完整的内联聊天快捷键体系。

3. **Ctrl+Right 部分接受**: `CodeCompleteCtrlRightAction` 支持按词接受补全建议，用户可以逐步接受 AI 建议的部分内容。

4. **Git Push 拦截**: `PrepushReviewAction` 在 Git Push 前触发代码评审，可能通过 IntelliJ 的 `GitCheckinHandlerFactory` 注册。

5. **双入口单测**: `UnitTestAction`（单个方法）和 `BatchUTGeneratorAction`（批量）提供两种单测生成入口。

6. **Action 与 Service 解耦**: Action 只负责触发，所有业务逻辑委托给对应的 Service 类。
