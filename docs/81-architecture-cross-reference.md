# iFlyCode 综合交叉引用与架构图谱

> 版本: iFlyCode 3.4.2-222 | 分析类文件数: 566 | 包数: 65 | 非内部类: 419

---

## 1. 包间依赖矩阵

### 1.1 依赖矩阵（行=依赖方，列=被依赖方，X=存在依赖）

```
                        u   a   e   m   d   a   s   i   c   a   i   s   l   v   t   t   t   t   g
                        t   g   n   e   i   p   e   n   o   p   n   e   i   i   e   e   m   e   e
                        i   e   u   s   f   m   t   l   m   m   l   r   s   e   s   s   p   n   n
                        l   n   m   s   f   .   t   i   p   .   i   v   t   w   t   t   l   e   e
                        .   s   .   .   .   s   .   n   .   e   n   i   e   .   .   .   .   r   r
                        .   e   .   .   .   r   .   e   .   n   e   c   r   .   .   .   .   .   a
                        .   r   .   .   .   v   .   .   .   u   .   e   .   .   .   .   .   .   t
                        .   v   .   .   .   i   .   .   .   m   .   .   .   .   .   .   .   .   o
                        .   i   .   .   .   c   .   .   .   .   .   .   .   .   .   .   .   .   r
                        .   c   .   .   .   e   .   .   .   .   .   .   .   .   .   .   .   .   .
─────────────────────────────────────────────────────────────────────────────────────────────────────
com.aicode              .   X   X   X   .   X   .   .   .   X   .   .   X   X   .   .   .   .   X
com.aicode.action       X   X   X   X   X   X   .   .   .   X   .   X   X   X   .   .   .   .   .
com.aicode.action.batch X   X   X   .   X   X   .   .   .   X   .   X   X   .   X   .   X   .   .
com.aicode.action.click X   X   X   X   .   X   .   X   .   X   .   X   X   .   .   .   .   .   .
com.aicode.agent        X   X   X   X   X   X   .   X   .   X   .   X   X   .   X   .   X   .   X
com.aicode.agent.dto    .   .   .   .   .   .   .   .   X   .   .   .   .   .   .   .   .   .   .
com.aicode.agent.enums  X   .   X   X   .   .   .   .   .   .   .   .   X   .   .   .   .   .   .
com.aicode.agent.service X  X   X   X   X   X   X   X   X   X   X   X   X   X   .   .   .   X   X
com.aicode.apm          .   X   .   X   .   .   .   .   .   .   X   .   X   .   .   .   .   .   .
com.aicode.complete     .   X   X   X   .   .   .   .   .   .   X   .   X   .   .   .   .   .   .
com.aicode.content.util .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .
com.aicode.diff         .   X   X   X   .   .   .   .   .   .   X   X   X   .   .   .   .   .   .
com.aicode.domain       .   .   .   .   .   .   X   .   .   .   X   .   .   .   .   .   .   .   .
com.aicode.enums        X   .   X   X   .   .   .   .   .   X   X   X   X   .   .   .   .   .   .
com.aicode.generate     .   .   X   .   X   .   .   .   X   X   .   .   .   .   .   .   .   .   .
com.aicode.inline       X   X   X   X   X   X   .   X   X   X   X   X   X   .   .   .   .   .   .
com.aicode.inline.ctrl  .   X   X   .   X   X   .   X   X   .   X   .   X   .   .   .   .   .   .
com.aicode.inline.ide   X   .   .   .   X   .   .   .   .   X   X   X   .   .   .   .   .   .   .
com.aicode.listener     X   X   X   X   X   X   .   X   X   X   .   X   X   X   .   .   .   .   .
com.aicode.service      X   .   X   .   .   .   .   X   .   X   X   X   .   .   .   .   .   .   .
com.aicode.service.edit X   X   X   X   X   X   X   X   X   X   X   X   X   .   .   .   .   X   .
com.aicode.settings     .   .   X   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .
com.aicode.status       .   .   X   X   .   .   .   .   .   .   .   .   X   X   .   .   .   .   .
com.aicode.template     X   X   .   X   .   X   .   .   .   .   .   .   X   .   X   X   X   X   .
com.aicode.test         X   X   X   X   X   X   .   .   .   X   .   X   X   X   X   .   X   .   .
com.aicode.view         X   X   X   X   X   X   .   .   .   .   X   X   X   .   .   .   .   .   .
```

### 1.2 完整包间依赖清单

| 依赖包 | 被依赖包列表 |
|--------|-------------|
| `com.aicode` | action, agent, agent.service, apm, content.util.file, enums, inline.status, listener, message, ui, updater, util |
| `com.aicode.action` | (root), action.batch, action.batch.doc, agent, agent.dto, agent.enums, agent.service, apm, content.util, content.util.file, diff, enums, error.search, exception, icons, inline.ide, inline.status, language, message, service, service.editor, settings, statusBar, ui, util, view |
| `com.aicode.action.batch` | (root), action.batch.node, action.click, agent, agent.enums, agent.service, apm, content.util, diff, enums, icons, inline.ide, inline.status, message, service.editor, settings, template, template.context.domain, template.generator, template.request.dto, test.dto, ui, util |
| `com.aicode.action.click` | (root), action, action.batch, agent, agent.dto.chat, agent.enums, agent.service, apm, content.util, enums, error.search, exception, inline, inline.controller, language, message, service.editor, settings, test, util, view |
| `com.aicode.agent` | (root), action, action.batch, agent.dto, agent.enums, agent.service, apm, apm.enums, content.util, content.util.file, diff, enums, inline.controller, listener, message, service.editor, settings, status, template, template.generator, template.request, test, test.dto, updater, util, view |
| `com.aicode.agent.dto` | agent.dto.chat, agent.dto.search, agent.enums, service, settings, test.dto |
| `com.aicode.agent.enums` | action.batch, action.click, agent.service, content.util, inline.ide, message, settings, util |
| `com.aicode.agent.service` | (root), action, action.batch, action.batch.doc, action.click, agent, agent.dto, agent.dto.chat, agent.enums, apm, apm.enums, content.util, content.util.file, diff, enums, exception, inline, inline.controller, inline.dto, inline.enums, inline.ide, inline.status, listener, message, service, service.editor, settings, status, statusBar, test.dto, toolwindow, ui, updater, util, view |
| `com.aicode.apm` | agent.service, apm.enums, content.util.file, diff, inline.ide, message, settings, util |
| `com.aicode.complete` | agent.service, content.util.file, enums, icons, inline.ide, message, service, util |
| `com.aicode.content.util` | dto |
| `com.aicode.content.util.file` | diff, util |
| `com.aicode.diff` | agent.service, enums, exception, inline.ide, message, service.editor, util |
| `com.aicode.domain` | service, service.editor, util |
| `com.aicode.enums` | action.batch, agent.enums, agent.service, apm, content.util, content.util.file, diff, exception, icons, inline.controller, inline.ide, inline.status, language, message, service.editor, ui, util |
| `com.aicode.generate` | agent.dto, apm, diff, domain, enums, language, service, util |
| `com.aicode.inline` | (root), action.batch, agent, agent.dto, agent.dto.chat, agent.enums, agent.service, apm, content.util, diff, enums, exception, icons, inline.action, inline.content, inline.controller, inline.dto, inline.enums, inline.ide, inline.listener, inline.status, listener, message, service.editor, settings, ui, util |
| `com.aicode.inline.controller` | action.batch, agent, agent.dto, agent.dto.chat, agent.enums, agent.service, content.util.file, diff, enums, inline, inline.content, inline.dto, inline.enums, inline.render, listener, service, status, ui, util |
| `com.aicode.inline.dto` | agent.dto.chat, inline.controller |
| `com.aicode.inline.enums` | agent.service, apm, diff, ui, util |
| `com.aicode.inline.ide` | action.batch, apm, content.util.file, diff, exception, inline, inline.status, service.editor, ui, util |
| `com.aicode.inline.listener` | agent.service, content.util, diff, ui, util |
| `com.aicode.inline.render` | action.batch, agent.service, apm, content.util, diff, icons, inline.enums, inline.ide, language, message, ui, util |
| `com.aicode.inline.status` | exception, inline.controller, util |
| `com.aicode.language` | action.batch, apm, exception, inline.controller, service, service.editor, ui, util |
| `com.aicode.listener` | (root), action.batch, agent, agent.dto, agent.dto.chat, agent.enums, agent.service, apm.enums, content.util, content.util.file, diff, domain, enums, exception, icons, inline, inline.controller, inline.dto, inline.enums, inline.status, message, service, service.editor, settings, statusBar, test.dto, ui, util, view |
| `com.aicode.message` | diff, util |
| `com.aicode.request` | agent.service, domain, enums, inline.controller, language, service, service.editor, util |
| `com.aicode.service` | (root), agent.dto, apm, domain, enums, exception, inline.controller, inline.ide, inline.status, language, settings, util |
| `com.aicode.service.editor` | action.batch, agent, agent.dto, agent.dto.chat, agent.enums, agent.service, apm, apm.enums, content.util, content.util.file, diff, domain, enums, exception, generate, inline, inline.controller, inline.ide, inline.status, language, listener, message, request, service, settings, status, ui, util |
| `com.aicode.settings` | (root), agent.dto, enums |
| `com.aicode.status` | content.util.file, enums, exception, message, statusBar, util |
| `com.aicode.statusBar` | (root), enums, icons, inline.ide, language, message, settings, status, util |
| `com.aicode.template` | (root), action.batch, agent, agent.dto, agent.service, message, template.builder, template.context.domain, template.context.resolved, template.context.service, template.context.service.impl, template.fileloader, template.generator, template.request, template.request.dto, test, test.dto, util |
| `com.aicode.template.builder` | template, template.context.domain, template.context.resolved, template.fileloader, template.request, template.request.dto, util |
| `com.aicode.template.context.domain` | template, template.builder, template.context.domain.annotion, template.context.resolved, template.request, template.request.dto, util |
| `com.aicode.template.context.resolved` | template.context.domain, template.request.dto, util |
| `com.aicode.template.context.service` | template.context.domain, template.context.resolved, template.request.dto |
| `com.aicode.template.context.service.impl` | message, template, template.context.domain, template.context.resolved, template.context.service, template.request, template.request.dto, util |
| `com.aicode.template.fileloader` | template, util |
| `com.aicode.template.generator` | action.batch, agent, agent.dto, agent.service, enums, message, settings, template, template.builder, template.context.domain, template.fileloader, template.request, test, test.dto, util |
| `com.aicode.template.request` | agent, agent.dto, agent.enums, enums, template, template.builder, template.context.domain, template.context.resolved, template.context.service.impl, template.generator, template.request.dto, test.dto, util |
| `com.aicode.template.request.dto` | message, template.request, util |
| `com.aicode.test` | (root), action.batch, agent, agent.dto, agent.dto.chat, agent.enums, agent.service, content.util, content.util.file, diff, enums, message, settings, template, template.context.domain, template.fileloader, template.generator, template.request, template.request.dto, test.dto, util, view |
| `com.aicode.test.dto` | agent.dto.chat, template.request.dto |
| `com.aicode.toolwindow` | (root), action, action.click, agent, agent.dto.chat, agent.enums, agent.service, content.util.file, enums, icons, inline, inline.action, message, settings, test, util, view |
| `com.aicode.ui` | agent.service, content.util, content.util.file, diff, exception, inline.action, inline.controller, service.editor, util |
| `com.aicode.updater` | action.batch, agent.dto, agent.service, apm, content.util, enums, inline.ide, inline.status, message, settings, util |
| `com.aicode.util` | action.batch, agent, agent.dto.chat, agent.enums, agent.service, apm, content.util, content.util.file, diff, domain, enums, exception, generate, inline, inline.controller, inline.dto, inline.ide, inline.render, inline.status, language, message, service, service.editor, settings, template, template.context.domain, template.context.resolved, test, test.dto, ui |
| `com.aicode.view` | (root), action.batch, agent, agent.dto, agent.enums, agent.service, content.util, content.util.file, diff, enums, exception, icons, inline.controller, inline.ide, listener, message, test, ui, util |

---

## 2. 核心包排名（被依赖次数）

| 排名 | 包名 | 被依赖次数 | 类数 | 角色 |
|------|------|-----------|------|------|
| 1 | `com.aicode.util` | 49 | 37 | 基础设施 - 工具集 |
| 2 | `com.aicode.agent.service` | 29 | 32 | 服务层 - Agent 通信服务 |
| 3 | `com.aicode.enums` | 26 | 31 | 数据层 - 枚举定义 |
| 4 | `com.aicode.message` | 26 | 1 | 基础设施 - 消息资源 |
| 5 | `com.aicode.diff` | 24 | 6 | 服务层 - Diff 对比 |
| 6 | `com.aicode.action.batch` | 21 | 11 | UI层 - 批量操作 |
| 7 | `com.aicode.content.util.file` | 20 | 3 | 基础设施 - 文件工具 |
| 8 | `com.aicode.settings` | 20 | 6 | 基础设施 - 配置 |
| 9 | `com.aicode.exception` | 19 | 2 | 基础设施 - 异常 |
| 10 | `com.aicode.agent.enums` | 18 | 5 | 数据层 - Agent 枚举 |
| 11 | `com.aicode.agent` | 17 | 14 | 控制层 - Agent 核心 |
| 12 | `com.aicode.apm` | 17 | 5 | 基础设施 - 可观测性 |
| 13 | `com.aicode.ui` | 17 | 9 | UI层 - 通用组件 |
| 14 | `com.aicode.content.util` | 17 | 2 | 基础设施 - 编辑器工具 |
| 15 | `com.aicode.service.editor` | 17 | 16 | 服务层 - 编辑器服务 |
| 16 | `com.aicode` (root) | 17 | 2 | 入口 - 插件启动 |
| 17 | `com.aicode.inline.ide` | 17 | 11 | UI层 - IDE 集成 |
| 18 | `com.aicode.inline.controller` | 16 | 4 | 控制层 - 内联聊天控制 |
| 19 | `com.aicode.agent.dto` | 16 | 25 | 数据层 - Agent DTO |
| 20 | `com.aicode.inline.status` | 14 | 5 | 控制层 - 内联聊天状态 |

---

## 3. 循环依赖分析

### 3.1 双向依赖对（共 110 对，以下列出关键对）

**核心双向依赖（跨层）：**

```
agent.service <--> action.batch          (服务层 <-> UI层)
agent.service <--> action.click          (服务层 <-> UI层)
agent.service <--> inline.controller     (服务层 <-> 控制层)
agent.service <--> listener              (服务层 <-> 控制层)
agent.service <--> view                  (服务层 <-> UI层)
agent.service <--> ui                    (服务层 <-> UI层)
agent.service <--> toolwindow            (服务层 <-> UI层)
agent.service <--> diff                  (服务层 <-> 服务层)
agent.service <--> apm                   (服务层 <-> 基础设施)
agent.service <--> updat                 (服务层 <-> 基础设施)
```

**模板子系统内部双向依赖：**

```
template <--> template.builder
template <--> template.context.domain
template <--> template.context.service.impl
template <--> template.fileloader
template <--> template.generator
template <--> template.request
template <--> template.test
template.builder <--> template.context.domain
template.builder <--> template.request
template.context.domain <--> template.context.resolved
template.context.domain <--> template.request
template.generator <--> template.request
template.request <--> template.request.dto
```

**内联聊天子系统内部双向依赖：**

```
inline <--> inline.action
inline <--> inline.controller
inline <--> inline.ide
inline.controller <--> inline.dto
inline.controller <--> ui
```

### 3.2 循环依赖根因

1. **agent.service 是最大循环源**：它同时被 UI 层（action、view、toolwindow）和控制层（listener、inline.controller）依赖，又反向依赖这些包，形成网状循环
2. **模板子系统高度内聚**：template 及其子包之间形成密集双向依赖，但对外隔离较好
3. **util 包被几乎所有包反向依赖**：虽然 util 依赖了很多包的类型，但这些主要是常量引用而非逻辑依赖

---

## 4. 关键类调用关系图

### 4.1 Service 层调用关系

```
┌─────────────────────────────────────────────────────────────────────┐
│                     Agent Service Layer                             │
│                                                                     │
│  ┌──────────────────────┐     ┌──────────────────────────┐         │
│  │ RestartableAgent     │────>│ PluginAgentProcess       │         │
│  │ ProcessService       │     │ ServiceEx/Impl           │         │
│  └──────────┬───────────┘     └──────────────────────────┘         │
│             │                                                       │
│             │ starts/restarts                                       │
│             v                                                       │
│  ┌──────────────────────┐     ┌──────────────────────────┐         │
│  │ PluginWebsocket      │────>│ SocketMessageHandle      │         │
│  │ Client               │     │ Listener                 │         │
│  └──────────┬───────────┘     └──────┬───────────────────┘         │
│             │                        │ dispatches to                │
│             │                        v                              │
│  ┌──────────┴──────────────────────────────────────────────────┐   │
│  │                                                              │   │
│  │  ┌────────────┐ ┌────────────┐ ┌──────────────┐             │   │
│  │  │ Chat       │ │ CodeCheck  │ │ CodeComplete │             │   │
│  │  │ Service    │ │ Service    │ │ Service      │             │   │
│  │  └─────┬──────┘ └────────────┘ └──────┬───────┘             │   │
│  │        │                              │                      │   │
│  │  ┌─────┴──────┐ ┌────────────┐ ┌──────┴───────┐             │   │
│  │  │ Common     │ │ GitReview  │ │ InlineChat   │             │   │
│  │  │ Service    │ │ Service    │ │ CommandSvc   │             │   │
│  │  └─────┬──────┘ └──────┬─────┘ └──────┬───────┘             │   │
│  │        │               │               │                     │   │
│  │  ┌─────┴──────┐ ┌──────┴───────┐ ┌────┴────────┐           │   │
│  │  │ User       │ │ Sql          │ │ CodeSearch  │           │   │
│  │  │ Service    │ │ Service      │ │ Service     │           │   │
│  │  └────────────┘ └──────────────┘ └─────────────┘           │   │
│  │                                                              │   │
│  │  ┌────────────┐ ┌──────────────────┐                        │   │
│  │  │ Init       │ │ RecentFiles      │                        │   │
│  │  │ Service    │ │ Manager          │                        │   │
│  │  └────────────┘ └──────────────────┘                        │   │
│  └──────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────┘

Service 间调用关系:
  ChatService      --> CommonService, GitReviewService, SqlService, UserService
  CommonService    --> ChatService, DiffService
  InlineChatCmdSvc --> ChatService, CommonService, DiffService
  UserService      --> ChatService, CommonService, SqlService, GitReviewService
  CodeCompleteSvc  --> GitReviewService
  SqlService       --> CodeCompleteService
  InitService      --> RequestTipServiceImpl, CancelRequestTip
```

### 4.2 Service -> DTO 使用关系

```
┌─────────────────────────────────────────────────────────────────┐
│  Agent DTO 层                                                    │
│                                                                  │
│  ┌─────────────┐  ┌──────────────┐  ┌──────────────────────┐   │
│  │ MessageDto  │  │ ResponseDto  │  │ ResponseStreamDto    │   │
│  └──────┬──────┘  └──────┬───────┘  └──────────┬───────────┘   │
│         │                │                      │               │
│  被所有Service使用     ChatService           CodeComplete      │
│                      CodeCheckService        InlineChatCmd     │
│                                                                  │
│  ┌─────────────┐  ┌──────────────┐  ┌──────────────────────┐   │
│  │ WebRequest  │  │ CodeInfoDto  │  │ FirstChatMessage     │   │
│  │ Dto         │  │ (chat)       │  │ (chat)               │   │
│  └──────┬──────┘  └──────┬───────┘  └──────────┬───────────┘   │
│         │                │                      │               │
│  ChatService         多个Service使用        ChatService         │
│  CommonService                            InlineChatCmd         │
│                                           UserService           │
│                                                                  │
│  ┌─────────────┐  ┌──────────────┐  ┌──────────────────────┐   │
│  │ CodeCheckDto│  │ SettingsDto  │  │ UserInfoDto          │   │
│  │ CodeCheckFix│  │ TipInfoDto   │  │ FunctionModelInfo    │   │
│  │ CodeCheckList│ │ ConnectConfig│  │ EnterpriseDto        │   │
│  └──────┬──────┘  └──────┬───────┘  └──────────┬───────────┘   │
│         │                │                      │               │
│  CodeCheckService   CommonService          UserService          │
│                     InitService                                  │
│                                                                  │
│  ┌─────────────┐  ┌──────────────┐                              │
│  │ CodeTip     │  │ CodeSearch   │                              │
│  │ RequestDto  │  │ InfoDto      │                              │
│  └──────┬──────┘  └──────┬───────┘                              │
│         │                │                                      │
│  EditorManagerSvc    CodeSearchService                          │
│  RequestTipService                                              │
└─────────────────────────────────────────────────────────────────┘
```

### 4.3 Listener -> Service 触发关系

```
┌──────────────────────────┐     ┌───────────────────────────────┐
│ Listener                 │     │ Target Service                │
├──────────────────────────┤     ├───────────────────────────────┤
│ AICodeUnloadPlugin       │────>│ CodeCompleteService           │
│                          │     │ EditorManagerService          │
├──────────────────────────┤     ├───────────────────────────────┤
│ AutoCodeGenerate         │────>│ EditorManagerService          │
│                          │     │ DocumentActionTracker         │
│                          │     │ RequestTipServiceImpl         │
├──────────────────────────┤     ├───────────────────────────────┤
│ CodeEditor               │────>│ EditorManagerService          │
├──────────────────────────┤     ├───────────────────────────────┤
│ CodeFileEditorManager    │────>│ RecentFilesManager            │
├──────────────────────────┤     ├───────────────────────────────┤
│ CodeLookupManager        │────>│ EditorManagerService          │
│                          │     │ EditorUtil                    │
├──────────────────────────┤     ├───────────────────────────────┤
│ CommitHandlerFactory     │────>│ CancelRequestTip              │
├──────────────────────────┤     ├───────────────────────────────┤
│ FileWatchedAdapter       │────>│ GitReviewService              │
├──────────────────────────┤     ├───────────────────────────────┤
│ GitBranchChange          │────>│ ChatService                   │
│                          │     │ InlineChatStatusServiceKt     │
├──────────────────────────┤     ├───────────────────────────────┤
│ PluginManager            │────>│ ChatService                   │
│                          │     │ SqlService                    │
│                          │     │ CancelRequestTip              │
├──────────────────────────┤     ├───────────────────────────────┤
│ ThemeChange              │────>│ CancelRequestTip              │
└──────────────────────────┘     └───────────────────────────────┘
```

### 4.4 Action -> Service 调用关系

```
┌──────────────────────────────────┐     ┌─────────────────────────┐
│ Action                           │     │ Service                 │
├──────────────────────────────────┤     ├─────────────────────────┤
│ AcceptInlaysAction               │────>│ EditorManagerService    │
│ AcceptLineCodeInlaysAction       │────>│ EditorManagerService    │
│ AcceptWordInlaysAction           │────>│ EditorManagerService    │
│ CodePromoterAction               │────>│ EditorManagerService    │
│ CycleNextEditorInlays            │────>│ EditorManagerService    │
│ CyclePreviousEditorInlays        │────>│ EditorManagerService    │
│ RequestCodeGenerateAction        │────>│ EditorManagerService    │
├──────────────────────────────────┤     ├─────────────────────────┤
│ BaseAction (click)               │────>│ ChatService             │
│                                  │     │ CommonService            │
├──────────────────────────────────┤     ├─────────────────────────┤
│ CodeCheckAction (click)          │────>│ CommonService            │
├──────────────────────────────────┤     ├─────────────────────────┤
│ LogoutAction                     │────>│ UserService             │
├──────────────────────────────────┤     ├─────────────────────────┤
│ OpenWindowAction                 │────>│ CommonService            │
│                                  │     │ InlineChatStatusSvcKt   │
├──────────────────────────────────┤     ├─────────────────────────┤
│ PluginSettingAction              │────>│ CommonService            │
├──────────────────────────────────┤     ├─────────────────────────┤
│ PrepushReviewAction              │────>│ CommonService            │
│                                  │     │ GitReviewService        │
├──────────────────────────────────┤     ├─────────────────────────┤
│ RefreshAction                    │────>│ ChatService             │
│                                  │     │ RestartableAgentProcSvc │
├──────────────────────────────────┤     ├─────────────────────────┤
│ CommitMessageSuggestionAction    │────>│ CancelRequestTip        │
├──────────────────────────────────┤     ├─────────────────────────┤
│ BatchUTGeneratorAction           │────>│ UserService             │
│                                  │     │ CancelRequestTip        │
├──────────────────────────────────┤     ├─────────────────────────┤
│ BatchFunctionCommentAction       │────>│ ChatService             │
│                                  │     │ CommonService            │
│                                  │     │ UserService             │
├──────────────────────────────────┤     ├─────────────────────────┤
│ UnitTestAction (click)           │────>│ UnitTestService         │
│                                  │     │ CppTestService          │
├──────────────────────────────────┤     ├─────────────────────────┤
│ OpenInlayInlineChatAction        │────>│ InlineChatService       │
│                                  │     │ CancelRequestTip        │
└──────────────────────────────────┘     └─────────────────────────┘
```

---

## 5. 五层架构图

```
╔══════════════════════════════════════════════════════════════════════════════╗
║                        iFlyCode 五层架构                                    ║
╠══════════════════════════════════════════════════════════════════════════════╣
║                                                                            ║
║  ┌─ UI 层 ──────────────────────────────────────────────────────────────┐  ║
║  │                                                                      │  ║
║  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────────────────┐  │  ║
║  │  │ Action       │  │ WebView      │  │ Inline Chat UI           │  │  ║
║  │  │              │  │              │  │                          │  │  ║
║  │  │ action/      │  │ view/        │  │ inline/                  │  │  ║
║  │  │ action.click/│  │ .WebViewWin  │  │ .InlineChatPanel         │  │  ║
║  │  │ action.batch/│  │  dowPanel    │  │ .InlineChatInputPanel    │  │  ║
║  │  │ .CodeAction  │  │ .PluginTool  │  │ .InlineChatTopPanel      │  │  ║
║  │  │ .BaseAction  │  │  WindowPanel │  │ .InlineChatInlay         │  │  ║
║  │  │ .LogoutAction│  │              │  │ inline.render/           │  │  ║
║  │  │ .RefreshAct  │  │ toolwindow/  │  │ .InlineChatBtnPanelRend  │  │  ║
║  │  └──────┬───────┘  └──────┬───────┘  │ .InlineChatCategoryRend  │  │  ║
║  │         │                 │          │ .InlineChatErrorRend      │  │  ║
║  │         │                 │          │ .InlineChatStopRend       │  │  ║
║  │         │                 │          └────────────┬─────────────┘  │  ║
║  │  ┌──────┴─────┐  ┌───────┴──────┐                │              │  ║
║  │  │ ui/        │  │ complete/    │                │              │  ║
║  │  │ .ActionBtn │  │ .InlayCompl  │                │              │  ║
║  │  │ .Font      │  │  etionHint   │                │              │  ║
║  │  │ .Style     │  │  Factory     │                │              │  ║
║  │  │ .RoundLine │  │              │                │              │  ║
║  │  │  Border    │  │              │                │              │  ║
║  │  └────────────┘  └──────────────┘                │              │  ║
║  └──────────────────────────────────────────────────┼──────────────┘  ║
║                                                     │                  ║
║  ┌─ 控制层 ─────────────────────────────────────────┼──────────────┐  ║
║  │                                                  │              │  ║
║  │  ┌──────────────┐  ┌──────────────┐  ┌──────────┴───────────┐ │  ║
║  │  │ Listener     │  │ Controller   │  │ IDE Action Router    │ │  ║
║  │  │              │  │              │  │                      │ │  ║
║  │  │ listener/    │  │ inline.ctrl/ │  │ inline.ide/          │ │  ║
║  │  │ .AutoCode    │  │ .Session     │  │ .IdeEditorAction     │ │  ║
║  │  │  Generate    │  │  Controller  │  │  Router               │ │  ║
║  │  │ .CodeEditor  │  │ .ChatInput   │  │ .ConditionalEditor   │ │  ║
║  │  │ .PluginDoc   │  │  Controller  │  │  ActionHandler        │ │  ║
║  │  │ .GitBranch   │  │ .Ephemeral   │  │ .IdeActionService    │ │  ║
║  │  │ .ThemeChange │  │  ChatSession │  │ .ActionScope          │ │  ║
║  │  │ .FileWatched │  │  Controller  │  │ .IdeAction            │ │  ║
║  │  └──────┬───────┘  └──────┬───────┘  └──────────┬───────────┘ │  ║
║  │         │                 │                      │             │  ║
║  │  ┌──────┴──────┐  ┌──────┴───────┐  ┌──────────┴───────────┐ │  ║
║  │  │ Agent Core  │  │ CommandEnum  │  │ Inline Chat Status   │ │  ║
║  │  │             │  │              │  │                      │ │  ║
║  │  │ agent/      │  │ agent.enums/ │  │ inline.status/       │ │  ║
║  │  │ .PluginWeb  │  │ .CommandEnum │  │ .InlineChatStatus    │ │  ║
║  │  │  socketClnt │  │ .ModuleEnum  │  │  Service             │ │  ║
║  │  │ .SocketMsg  │  │ .PageEnum    │  │ .InlineChatStatus    │ │  ║
║  │  │  HandleLstn │  │ .AgentModule │  │  Subscription        │ │  ║
║  │  │ .PluginWeb  │  │ .Permission  │  │ .InlineStatusService │ │  ║
║  │  │  socketLstn │  │  Enum        │  │ .InlineChatStatus    │ │  ║
║  │  │ .AgentCheck │  │              │  │  ServiceProvider     │ │  ║
║  │  │  Timer      │  │              │  │                      │ │  ║
║  │  │ .HeartBeat  │  │              │  │                      │ │  ║
║  │  │  CheckRunner│  │              │  │                      │ │  ║
║  │  └──────┬──────┘  └──────────────┘  └──────────┬───────────┘ │  ║
║  └─────────┼──────────────────────────────────────┼─────────────┘  ║
║            │                                      │                 ║
║  ┌─ 服务层 ─┼──────────────────────────────────────┼─────────────┐  ║
║  │          │                                      │             │  ║
║  │  ┌───────┴──────────┐  ┌───────────────────────┴──────────┐ │  ║
║  │  │ Agent Service    │  │ Editor Service                   │ │  ║
║  │  │                  │  │                                  │ │  ║
║  │  │ agent.service/   │  │ service/                         │ │  ║
║  │  │ .ChatService     │  │ .EditorManagerService            │ │  ║
║  │  │ .CommonService   │  │ .EditorRequestService            │ │  ║
║  │  │ .CodeComplete    │  │ .RequestTipService               │ │  ║
║  │  │  Service         │  │ .TipRenderer                     │ │  ║
║  │  │ .CodeCheck       │  │ .TipCache                        │ │  ║
║  │  │  Service         │  │ service.editor/                  │ │  ║
║  │  │ .GitReview       │  │ .EditorManagerServiceImpl        │ │  ║
║  │  │  Service         │  │ .RequestTipServiceImpl           │ │  ║
║  │  │ .InlineChat      │  │ .CancelRequestTip                │ │  ║
║  │  │  CommandService  │  │ .AgentCodeTipList                │ │  ║
║  │  │ .UserService     │  │ .DocumentActionTracker           │ │  ║
║  │  │ .SqlService      │  │ .TipInlayRenderer                │ │  ║
║  │  │ .CodeSearch      │  │ .InlayRendering                  │ │  ║
║  │  │  Service         │  │ .RequestResultList               │ │  ║
║  │  │ .InitService     │  │                                  │ │  ║
║  │  │ .PluginAgent     │  │                                  │ │  ║
║  │  │  ProcessService  │  │                                  │ │  ║
║  │  │ .Restartable     │  │                                  │ │  ║
║  │  │  AgentProcessSvc │  │                                  │ │  ║
║  │  └──────┬───────────┘  └────────────┬─────────────────────┘ │  ║
║  │         │                           │                       │  ║
║  │  ┌──────┴──────────┐  ┌─────────────┴────────────────────┐ │  ║
║  │  │ Diff Service    │  │ Test/Template Service            │ │  ║
║  │  │                 │  │                                  │ │  ║
║  │  │ diff/           │  │ test/                            │ │  ║
║  │  │ .DiffService    │  │ .UnitTestService                 │ │  ║
║  │  │ .CloudDiffUtil  │  │ .BatchUnitTestService            │ │  ║
║  │  │ .FileService    │  │ .CppTestService                  │ │  ║
║  │  │                 │  │ template/                        │ │  ║
║  │  │                 │  │ .TemplateGenerator               │ │  ║
║  │  │                 │  │ template.generator/              │ │  ║
║  │  │                 │  │ .CreateTestFileTask              │ │  ║
║  │  │                 │  │ .CreateTestMethodTask            │ │  ║
║  │  │                 │  │ template.request/                │ │  ║
║  │  │                 │  │ .TemplateRequestService          │ │  ║
║  │  └─────────────────┘  └──────────────────────────────────┘ │  ║
║  └─────────────────────────────────────────────────────────────┘  ║
║                                                                   ║
║  ┌─ 数据层 ─────────────────────────────────────────────────────┐  ║
║  │                                                              │  ║
║  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐  │  ║
║  │  │ Agent DTO    │  │ Domain       │  │ Enums            │  │  ║
║  │  │              │  │              │  │                  │  │  ║
║  │  │ agent.dto/   │  │ domain/      │  │ enums/           │  │  ║
║  │  │ .MessageDto  │  │ .LineInfo    │  │ .AICodeStatus    │  │  ║
║  │  │ .ResponseDto │  │ .Position    │  │ .LanguageEnum    │  │  ║
║  │  │ .Response    │  │ .Range       │  │ .CodeTipType     │  │  ║
║  │  │  StreamDto   │  │ .Suggestion  │  │ .WebViewDataType │  │  ║
║  │  │ .WebRequest  │  │ .CommandCache│  │ .WebViewResponse │  │  ║
║  │  │  Dto         │  │ .Virtual     │  │  TypeEnum        │  │  ║
║  │  │ .SettingsDto │  │  FileUri     │  │ .ChatOperation   │  │  ║
║  │  │ .UserInfoDto │  │ .GetTips     │  │  Enum            │  │  ║
║  │  │ agent.dto    │  │  Result      │  │ .SendKeyEnum     │  │  ║
║  │  │  .chat/      │  │              │  │ .OperateAction   │  │  ║
║  │  │  .search/    │  │              │  │  Enum            │  │  ║
║  │  └──────────────┘  └──────────────┘  └──────────────────┘  │  ║
║  │                                                              │  ║
║  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐  │  ║
║  │  │ Inline DTO   │  │ Test DTO     │  │ Template DTO     │  │  ║
║  │  │              │  │              │  │                  │  │  ║
║  │  │ inline.dto/  │  │ test.dto/    │  │ template.request │  │  ║
║  │  │ .InlineChat  │  │ .UnitTestDto │  │  .dto/           │  │  ║
║  │  │  Info        │  │ .UnitTest    │  │ .CaseBranch      │  │  ║
║  │  │ .LastChat    │  │  AgentDto    │  │ .CaseParam       │  │  ║
║  │  │  QuestionInfo│  │ .RequestCase │  │ .CaseResult      │  │  ║
║  │  │ .LastSelection│ │  CodeDto     │  │ .ToMockMethod    │  │  ║
║  │  │  TextCache   │  │ .BatchUnit   │  │                  │  │  ║
║  │  └──────────────┘  │  TestDto     │  └──────────────────┘  │  ║
║  │                    └──────────────┘                         │  ║
║  └─────────────────────────────────────────────────────────────┘  ║
║                                                                   ║
║  ┌─ 基础设施层 ─────────────────────────────────────────────────┐  ║
║  │                                                              │  ║
║  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐  │  ║
║  │  │ Utils        │  │ Settings     │  │ APM              │  │  ║
║  │  │              │  │              │  │                  │  │  ║
║  │  │ util/        │  │ settings/    │  │ apm/             │  │  ║
║  │  │ .AICodeUtils │  │ .AICode      │  │ .OpenTelemetry   │  │  ║
║  │  │ .StringUtils │  │  SettingsState│ │  Service         │  │  ║
║  │  │ .FileUtils   │  │ .AICode      │  │ .OpenTelemetry   │  │  ║
║  │  │ .EditorKt    │  │  Request     │  │  Util            │  │  ║
║  │  │ .PsiUtils    │  │  Settings    │  │ .OpenTelemetry   │  │  ║
║  │  │ .LogUtil     │  │ .UnitTest    │  │  Config          │  │  ║
║  │  │ .Maps        │  │  SettingsState│ │ apm.enums/       │  │  ║
║  │  │ .Property    │  │ .BatchUnit   │  │ .SpanAttrEnum    │  │  ║
║  │  │  Utils       │  │  TestSettings│  │ .TracerEnum      │  │  ║
║  │  └──────────────┘  └──────────────┘  └──────────────────┘  │  ║
║  │                                                              │  ║
║  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐  │  ║
║  │  │ Message      │  │ Exception    │  │ Content/File     │  │  ║
║  │  │              │  │              │  │                  │  │  ║
║  │  │ message/     │  │ exception/   │  │ content.util/    │  │  ║
║  │  │ .BasicActions│  │ .Request     │  │ .EditorUtils     │  │  ║
║  │  │  Bundle      │  │  Cancel      │  │ .OverlayUtils    │  │  ║
║  │  │              │  │  Exception   │  │ content.util     │  │  ║
║  │  │              │  │ .Request     │  │  .file/          │  │  ║
║  │  │              │  │  Timeout     │  │ .FileUtils       │  │  ║
║  │  │              │  │  Exception   │  │ .FileExtension   │  │  ║
║  │  │              │  │              │  │  LanguageDetails  │  │  ║
║  │  └──────────────┘  └──────────────┘  └──────────────────┘  │  ║
║  └─────────────────────────────────────────────────────────────┘  ║
╚════════════════════════════════════════════════════════════════════╝
```

---

## 6. 三大数据流图

### 6.1 代码补全数据流

```
用户输入字符
     │
     v
┌──────────────────┐    ┌───────────────────────┐
│ AutoCodeGenerate │───>│ EditorManagerService   │
│ Listener         │    │ .documentChanged()     │
│ (监听文档变更)    │    └───────────┬───────────┘
└──────────────────┘                │
                                    v
                    ┌───────────────────────────┐
                    │ RequestTipServiceImpl      │
                    │ .requestTip()             │
                    │ (构建 CodeTipRequestDto)   │
                    └───────────┬───────────────┘
                                │
                    ┌───────────┴───────────┐
                    │ PluginWebsocketClient  │
                    │ .send()               │
                    │ (通过 WebSocket 发送   │
                    │  MessageDto)           │
                    └───────────┬───────────┘
                                │
                    ═══════════╪═══════════
                    网络传输 (WebSocket)
                    ═══════════╪═══════════
                                │
                                v
                    ┌───────────────────────────┐
                    │ Cloud (iFlytek AI Engine)  │
                    │ (代码补全推理)             │
                    └───────────┬───────────────┘
                                │
                    ═══════════╪═══════════
                    网络传输 (WebSocket)
                    ═══════════╪═══════════
                                │
                                v
                    ┌───────────────────────────┐
                    │ SocketMessageHandle        │
                    │ Listener                   │
                    │ .onMessage()               │
                    │ (根据 CommandEnum 分发)     │
                    └───────────┬───────────────┘
                                │
                                v
                    ┌───────────────────────────┐
                    │ CodeCompleteService        │
                    │ .handleCodeComplete()      │
                    │ (解析 ResponseStreamDto)    │
                    └───────────┬───────────────┘
                                │
                                v
                    ┌───────────────────────────┐
                    │ AgentCodeTipList           │
                    │ (构建补全建议列表)          │
                    └───────────┬───────────────┘
                                │
                                v
                    ┌───────────────────────────┐
                    │ TipInlayRenderer           │
                    │ (渲染 Inlay 提示)          │
                    └───────────┬───────────────┘
                                │
                                v
                    ┌───────────────────────────┐
                    │ Editor (Inlay Hint)        │
                    │ (用户看到灰色补全文本)      │
                    └───────────────────────────┘
                                │
                    用户按 Tab/Enter 接受
                                │
                                v
                    ┌───────────────────────────┐
                    │ AcceptInlaysAction         │
                    │ AcceptLineCodeInlaysAction │
                    │ AcceptWordInlaysAction     │
                    │ (将补全文本插入编辑器)      │
                    └───────────────────────────┘
```

### 6.2 聊天数据流

```
用户在 WebView 中输入消息
     │
     v
┌──────────────────┐    ┌───────────────────────┐
│ WebViewWindow    │───>│ ChatService             │
│ Panel            │    │ .sendChatMessage()      │
│ (JS -> Java桥接) │    │ (构建 FirstChatMessage  │
└──────────────────┘    │  + CodeInfoDto)         │
                        └───────────┬────────────┘
                                    │
                        ┌───────────┴───────────┐
                        │ CommonService           │
                        │ .collectContext()       │
                        │ (收集代码上下文、文件信息)│
                        └───────────┬───────────┘
                                    │
                        ┌───────────┴───────────┐
                        │ PluginWebsocketClient   │
                        │ .send()                │
                        │ (发送 MessageDto)       │
                        └───────────┬───────────┘
                                    │
                        ═══════════╪═══════════
                        网络传输 (WebSocket)
                        ═══════════╪═══════════
                                    │
                                    v
                        ┌───────────────────────────┐
                        │ Cloud (iFlytek AI Engine)  │
                        │ (对话推理，流式返回)        │
                        └───────────┬───────────────┘
                                    │
                        ═══════════╪═══════════
                        网络传输 (WebSocket)
                        ═══════════╪═══════════
                                    │
                                    v
                        ┌───────────────────────────┐
                        │ SocketMessageHandle        │
                        │ Listener                   │
                        │ .onMessage()               │
                        └───────────┬───────────────┘
                                    │
                                    v
                        ┌───────────────────────────┐
                        │ ChatService                │
                        │ .handleChatResponse()      │
                        │ (解析 ResponseDto/Stream)   │
                        └───────────┬───────────────┘
                                    │
                        ┌───────────┴───────────┐
                        │ WebViewWindowPanel      │
                        │ .callJS()               │
                        │ (Java -> JS桥接,        │
                        │  更新聊天界面)           │
                        └────────────────────────┘

  ── 内联聊天变体 ──────────────────────────────────────

  用户选中代码 + 右键/快捷键
       │
       v
  ┌──────────────────┐    ┌───────────────────────┐
  │ OpenInlayInline  │───>│ InlineChatService      │
  │ ChatAction       │    │ .openInlineChat()      │
  └──────────────────┘    └───────────┬───────────┘
                                      │
                          ┌───────────┴───────────┐
                          │ SessionController       │
                          │ .startSession()         │
                          │ (管理会话状态)           │
                          └───────────┬───────────┘
                                      │
                          ┌───────────┴───────────┐
                          │ InlineChatCommand       │
                          │ Service                 │
                          │ .sendInlineChat()       │
                          │ (发送到 Agent)           │
                          └───────────┬───────────┘
                                      │
                          ┌───────────┴───────────┐
                          │ DiffService             │
                          │ (展示代码差异)           │
                          └────────────────────────┘
```

### 6.3 单测生成数据流

```
用户右键 -> Generate Unit Test
     │
     v
┌──────────────────┐    ┌───────────────────────┐
│ UnitTestAction   │───>│ UnitTestService         │
│ (click)          │    │ .generateUnitTest()     │
└──────────────────┘    └───────────┬───────────┘
                                    │
              ┌─────────────────────┼──────────────────────┐
              │                     │                      │
              v                     v                      v
  ┌───────────────────┐ ┌──────────────────┐ ┌──────────────────┐
  │ TemplateGenerator  │ │ Template         │ │ CommonService    │
  │ .generate()       │ │ RequestService   │ │ (获取上下文)      │
  │ (模板渲染)        │ │ .request()       │ │                  │
  └────────┬──────────┘ └────────┬─────────┘ └──────────────────┘
           │                     │
           v                     v
  ┌───────────────────┐ ┌──────────────────┐
  │ FileTemplate      │ │ PluginWebsocket  │
  │ Context           │ │ Client           │
  │ (Velocity 模板)   │ │ (发送请求到云端)  │
  └────────┬──────────┘ └────────┬─────────┘
           │                     │
           v                     ══════════════════
  ┌───────────────────┐          网络传输
  │ TestBuilderImpl   │          ══════════════════
  │ /JavaTestBuilder  │                 │
  │ Impl              │                 v
  │ (构建测试代码)     │     ┌──────────────────────┐
  └────────┬──────────┘     │ Cloud (AI Engine)     │
           │                 │ (生成测试用例)         │
           v                 └──────────┬───────────┘
  ┌───────────────────┐                 │
  │ CreateTestFile    │                 ══════════════════
  │ Task / Create     │                 网络传输
  │ TestMethodTask    │                 ══════════════════
  │ (写入测试文件)     │                 │
  └────────┬──────────┘                 v
           │                 ┌──────────────────────┐
           v                 │ SocketMessageHandle   │
  ┌───────────────────┐     │ Listener              │
  │ Editor            │     │ .onMessage()          │
  │ (显示生成的测试)   │     └──────────┬───────────┘
  └───────────────────┘                 │
                                        v
                            ┌──────────────────────┐
                            │ UnitTestService       │
                            │ .handleResponse()     │
                            │ (解析测试结果)         │
                            └──────────┬───────────┘
                                       │
                           ┌───────────┴───────────┐
                           │ WebViewWindowPanel     │
                           │ (在聊天窗口展示结果)    │
                           └───────────────────────┘

  ── 批量单测变体 ──────────────────────────────────────

  用户选择多个文件/方法
       │
       v
  ┌──────────────────┐    ┌───────────────────────┐
  │ BatchUTGenerator │───>│ BatchUnitTest          │
  │ Action           │    │ TemplateService        │
  └──────────────────┘    │ (批量调度单测生成)      │
                          └────────────────────────┘
```

---

## 7. CommandEnum 分发路由

`CommandEnum` 是 iFlyCode 的核心路由枚举，定义了所有 WebSocket 命令类型。以下列出引用 `CommandEnum` 的关键类及其角色：

```
CommandEnum (agent.enums)
     │
     ├── 发送端 (构造命令)
     │   ├── ChatService              ── 聊天命令
     │   ├── CommonService            ── 通用操作命令
     │   ├── CodeCompleteService      ── 代码补全命令
     │   ├── CodeCheckService         ── 代码检查命令
     │   ├── CodeSearchService        ── 代码搜索命令
     │   ├── GitReviewService         ── Git 审查命令
     │   ├── InlineChatCommandService ── 内联聊天命令
     │   ├── SqlService               ── SQL 命令
     │   ├── UserService              ── 用户操作命令
     │   ├── EditorManagerServiceImpl ── 编辑器请求命令
     │   ├── RequestTipServiceImpl    ── 代码提示命令
     │   └── PluginWebsocketClient    ── WebSocket 发送
     │
     └── 接收端 (分发命令)
         ├── SocketMessageHandleListener ── 核心分发器
         ├── PluginWebsocketListener     ── 连接状态处理
         └── HeartBeatCheckRunner        ── 心跳检查
```

---

## 8. WebViewWindowPanel 集成点

`WebViewWindowPanel` 是聊天 UI 的核心面板，被以下服务类直接操作：

```
WebViewWindowPanel (view)
     │
     ├── 数据写入 (Java -> JS)
     │   ├── ChatService              ── 聊天响应
     │   ├── CodeCheckService         ── 代码检查结果
     │   ├── CodeSearchService        ── 搜索结果
     │   ├── CommonService            ── 通用响应
     │   ├── GitReviewService         ── Git 审查结果
     │   ├── InlineChatCommandService ── 内联聊天响应
     │   ├── SqlService               ── SQL 结果
     │   ├── UserService              ── 用户信息
     │   ├── RestartableAgentProcSvc  ── 重连状态
     │   ├── UnitTestService          ── 单测结果
     │   ├── CppTestService           ── C++ 测试结果
     │   ├── BatchUnitTestService     ── 批量单测结果
     │   └── GitBranchChangeListener  ── Git 状态变更
     │
     └── 事件读取 (JS -> Java)
         ├── BaseAction               ── 通用操作触发
         ├── EnableAutoTriggerAction  ── 自动触发开关
         ├── BatchFunctionCommentAct  ── 批量注释
         └── UnitTestAction           ── 单测触发
```

---

## 9. AICodeSettingsState 配置传播

`AICodeSettingsState` 是全局配置中心，被 40+ 类引用：

```
AICodeSettingsState (settings)
     │
     ├── UI 层读取
     │   ├── action.CodeProblemsIntentionAction
     │   ├── action.CommitMessageSuggestionAction
     │   ├── action.EnableAutoTriggerCodeGenerateAction
     │   ├── action.PrepushReviewAction
     │   ├── action.UserInfoAction
     │   ├── action.click.BaseAction / TerminalAction / UnitTestAction
     │   ├── action.click.OpenInlayInlineChatAction
     │   ├── inline.InlineChatInputPanel
     │   └── statusBar.StatusBarPopup
     │
     ├── 服务层读取
     │   ├── agent.service.ChatService / CommonService / UserService
     │   ├── agent.service.CodeCompleteService
     │   ├── service.EditorManagerService
     │   ├── service.editor.EditorManagerServiceImpl
     │   └── service.editor.RequestTipServiceImpl
     │
     ├── 基础设施层读取
     │   ├── agent.PluginWebsocketClient
     │   ├── agent.SocketMessageHandleListener
     │   ├── apm.OpenTelemetryService
     │   ├── listener.AutoCodeGenerateListener
     │   ├── listener.GitBranchChangeListener
     │   └── util.AICodeUtils
     │
     └── 模板/测试层读取
         ├── template.generator.CreateTestFileTask
         ├── template.generator.CreateTestMethodTask
         ├── test.CppTestService
         ├── test.UnitTestService
         └── updater.PluginUpdater / PluginUpdaterCheckService
```

---

## 10. 架构特征总结

### 10.1 依赖方向统计

| 方向 | 数量 | 说明 |
|------|------|------|
| UI -> 服务层 | 28 | Action/View 调用 Agent Service |
| 控制层 -> 服务层 | 15 | Listener/Controller 触发 Service |
| 服务层 -> 数据层 | 42 | Service 使用 DTO/Domain/Enum |
| 服务层 -> 基础设施 | 38 | Service 使用 Util/Settings/APM |
| UI -> 基础设施 | 22 | Action 直接使用 Util/Settings |
| 双向依赖 | 110 | 循环依赖对数 |

### 10.2 关键架构问题

1. **agent.service 过度耦合**：`agent.service` 包被 29 个包依赖，同时反向依赖 35 个包，是最大的双向依赖源。它同时承担了业务逻辑和 UI 回调的职责。

2. **util 包职责膨胀**：`util` 包含 37 个类，被 49 个包依赖，同时反向依赖 30 个包。部分工具类（如 `AICodeUtils`、`HandleCacheUtil`）包含了业务逻辑而非纯工具方法。

3. **WebViewWindowPanel 上帝对象**：被 12+ 个 Service 直接操作，承担了过多 UI 更新职责。

4. **模板子系统内聚但庞大**：template 及其 9 个子包形成紧密内聚的子系统，内部双向依赖密集，但对外仅通过 `TemplateGenerator` 和 `TemplateRequestService` 暴露接口。

5. **Listener 直接操作 Service**：11 个 Listener 直接调用 15 个不同的 Service，缺少中间事件总线层，导致 Listener 与 Service 紧耦合。

### 10.3 类规模分布

| 包 | 类数 | 说明 |
|----|------|------|
| util | 37 | 最大包，工具集 |
| agent.service | 32 | 第二大，Agent 通信服务 |
| enums | 31 | 枚举定义 |
| agent.dto | 25 | Agent 数据传输对象 |
| inline | 25 | 内联聊天（含子包） |
| action | 24 | 动作定义 |
| template.generator | 22 | 模板生成器 |
| listener | 21 | 事件监听器 |
| test.dto | 19 | 测试 DTO |
| service | 17 | 编辑器服务接口 |
| service.editor | 16 | 编辑器服务实现 |
