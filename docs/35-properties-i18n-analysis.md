# iFlyCode 消息属性与 i18n 分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-11

## 1. 概述

iFlyCode 插件包含两个属性文件用于 i18n 和 UI 文本：
- `aicode.properties` — 核心功能描述和更新日志
- `BasicActionsBundle.properties` — UI 操作标签

## 2. aicode.properties 分析

### 2.1 插件描述

插件描述列出了 **10 大核心功能**：

| # | 功能 | 描述 |
|---|------|------|
| 1 | 代码补全 | 行级/函数级代码补全，支持多种编程语言 |
| 2 | 单元测试 | 自动生成单元测试用例 |
| 3 | 代码解释 | 智能代码解释和注释 |
| 4 | 智能问答 | AI 驱动的编程问答 |
| 5 | SQL 生成/优化 | SQL 语句生成与性能优化 |
| 6 | 代码调试 | 智能代码调试辅助 |
| 7 | 文档注释 | 自动生成文档注释 |
| 8 | 代码预评审 | 代码提交前预审 |
| 9 | 批量单测 | 批量生成单元测试（企业版） |
| 10 | 通用助理 | 多角色 AI 助理 |

### 2.2 版本更新日志

从属性文件中提取的版本历史：

**v3.4.2:**
- Bug 修复和体验优化

**v3.4.1:**
- 多模型管理/切换
- SQL 支持 TxSQL
- Bug 修复

**v3.4.0:**
- 流程图生成
- 文件引用
- iFlyDev/iFlyOps/iFlyPm/iFlyDBA 助理
- inlinechat (私有化版)
- Bug 修复

**v3.3.1:**
- Go/C# 语言支持
- 批量函数注释
- 自动更新
- Bug 修复

**v3.3.0:**
- 实验功能设置
- Beta 标识
- 玩法推荐
- Bug 修复

**v3.2.6:**
- 批量单测（企业版）
- 代码预评审（个人/团队版）
- Bug 修复

**v3.2.4:**
- 通用助理
- 测试助理
- 代码优化
- 函数拆分
- Bug 修复

**v3.2.0:**
- C 语言支持
- JS 箭头函数
- APM 监控
- C/C++ 单测
- Bug 修复

**v3.0.0:**
- 产品架构和 UI 全新升级
- 星火大模型最新版本
- Bug 修复

**v2.2.0:**
- 历史会话
- SQL 多数据源
- Bug 修复

**v2.0.0:**
- SQL 生成/优化
- 代码调试
- 文档注释
- Bug 修复

**v1.0.0:**
- 行级/函数级补全
- 单元测试
- 代码解释
- 智能问答

### 2.3 功能角色体系

从版本历史中识别出的 AI 助理角色：

| 角色 ID | 名称 | 用途 |
|---------|------|------|
| iFlyDev | 开发助理 | 通用编程辅助 |
| iFlyOps | 运维助理 | 运维相关辅助 |
| iFlyPm | 产品助理 | 产品相关辅助 |
| iFlyDBA | 数据库助理 | 数据库相关辅助 |
| TestAssistant | 测试助理 | 测试相关辅助 |

## 3. BasicActionsBundle.properties 分析

### 3.1 Action 标签

| Key | 值 | 对应 Action |
|-----|-----|-----------|
| `action.AICode.applyInlays.text` | Accept Code Completion | 接受补全 |
| `action.AICode.applyWordInlays.text` | Accept Word Completion | 逐词采纳 |
| `action.AICode.applyLineCodeInlays.text` | Accept Line Completion | 逐行采纳 |
| `action.AICode.disposeInlays.text` | Dismiss Code Completion | 清除补全 |
| `action.AICode.cyclePrevInlays.text` | Previous Completion | 上一个补全 |
| `action.AICode.cycleNextInlays.text` | Next Completion | 下一个补全 |
| `action.AICode.requestCompletions.text` | Trigger Code Completion | 触发补全 |
| `action.AICode.openWindow.text` | Open iFlyCode | 打开窗口 |
| `action.AICode.InlineChat.InlineChatStopAction.text` | Stop Editing | 停止编辑 |
| `action.AICode.InlineChat.InlineChatUndoAction.text` | Undo | 撤销 |
| `action.AICode.InlineChat.AcceptAction.text` | Accept | 接受 |
| `action.AICode.InlineChat.RejectAction.text` | Reject | 拒绝 |
| `action.AICode.InlineChat.RetryAction.text` | Retry | 重试 |
| `action.AICode.userInfo.text` | User Info | 用户信息 |
| `action.AICode.enableAutoTrigger.text` | Auto Trigger | 自动触发 |
| `action.AICode.setting.text` | Settings | 设置 |
| `action.AICode.LogoutAction.text` | Logout | 登出 |
| `group.AICodeEditorPopup.text` | 星火飞码 iFlyCode | 编辑器右键菜单组名 |
| `action.TriggerCodeProblemsTreePopupAction.text` | One-Click Fix | 一键修复 |

### 3.2 UI 文本

| Key | 值 | 用途 |
|-----|-----|------|
| `editor.action.group.text` | 星火飞码 iFlyCode | 编辑器 Action 组名 |
| `status.bar.widget.name` | iFlyCode | 状态栏小部件名 |

## 4. 语言支持配置文件

### 4.1 code-java.xml

Java 语言扩展注册：

| 扩展点 | 实现类 | 说明 |
|--------|--------|------|
| `languageIdSupport` | `com.aicode.language.JavaLanguageInfoSupport` | Java 语言支持 |
| `editorSupport` | `com.aicode.language.JavaEditorSupport` | Java 编辑器支持 |

### 4.2 code-python.xml

Python 语言扩展注册：

| 扩展点 | 实现类 | 说明 |
|--------|--------|------|
| `languageIdSupport` | `com.aicode.language.PythonLanguageInfoSupport` | Python 语言支持 |
| `editorSupport` | `com.aicode.language.PythonEditorSupport` | Python 编辑器支持 |

### 4.3 code-javascript.xml

JavaScript 语言扩展注册：

| 扩展点 | 实现类 | 说明 |
|--------|--------|------|
| `languageIdSupport` | `com.aicode.language.JavaScriptLanguageInfoSupport` | JS 语言支持 |
| `editorSupport` | `com.aicode.language.JavaScriptEditorSupport` | JS 编辑器支持 |

## 5. 关键发现

1. **多语言扩展体系**：通过 `languageIdSupport` 和 `editorSupport` 两个自定义扩展点，iFlyCode 实现了可插拔的语言支持架构。每种语言通过独立的 XML 配置文件注册，支持 Java/Python/JavaScript 三种语言。

2. **企业版 vs 个人版功能差异**：从版本日志可见，批量单测是企业版专属功能，代码预评审在个人/团队版可用。

3. **私有化部署**：v3.4.0 引入了 inlinechat 的私有化版本，说明有 on-premise 部署模式。

4. **多模型管理**：v3.4.1 引入了多模型管理/切换功能，说明后端支持多个 AI 模型。

5. **APM 监控**：v3.2.0 引入了 APM 监控，对应代码中的 OpenTelemetry 集成。
