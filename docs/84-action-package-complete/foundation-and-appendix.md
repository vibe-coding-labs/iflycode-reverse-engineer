## 8. 基础与工具类

### 8.1 CodeAction (接口)

- **包**: `com.aicode.action`
- **源文件**: `ga` (混淆)
- **签名**: `public interface CodeAction`

**方法**: 无（标记接口）

**逻辑推断**: 标记接口，标识代码补全相关 Action。被 `AcceptInlaysAction`、`AcceptWordInlaysAction`、`AcceptLineCodeInlaysAction`、`DisposeInlaysAction`、`RequestCodeGenerateAction`、`EnableAutoTriggerCodeGenerateAction` 实现。`CodePromoterAction` 通过 `instanceof CodeAction` 识别这些 Action 以提升优先级。

---

### 8.2 PluginAnAction (抽象基类)

- **包**: `com.aicode.action.click`
- **源文件**: `th` (混淆)
- **签名**: `public abstract class PluginAnAction extends AnAction`

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `PluginAnAction()` | 构造器 | |
| `PluginAnAction(String, String, Icon)` | 构造器 | |
| `PluginAnAction(Supplier&lt;String&gt;, Icon)` | 构造器 | |
| `getActionUpdateThread()` | `ActionUpdateThread` | 返回 Action 更新线程 |

**关联 Service**: 无直接 Service 调用

**逻辑推断**: 所有插件 Action 的抽象基类，继承 IntelliJ `AnAction`。`getActionUpdateThread` 指定 Action 更新在哪个线程执行（BGT 或 EDT）。

---

### 8.3 ActionsUtil

- **包**: `com.aicode.action`
- **源文件**: `vg` (混淆)
- **签名**: `public class ActionsUtil`

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `enum` | `static AtomicBoolean` | 初始化标志 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `handRightChatAction(ActionManager)` | `static void` | 注册右键聊天 Action |
| `handBatchFunctionCommentAction(ActionManager)` | `static void` | 注册批量函数注释 Action |
| `handBatchTestAction(ActionManager)` | `static void` | 注册批量单测 Action |
| `handCommitAction(ActionManager)` | `static void` | 注册提交消息 Action |
| `handCodeReviewAction(ActionManager)` | `static void` | 注册代码审查 Action |
| `convertToId(String)` | `static String` | 转换为 Action ID |
| `refreshActions()` | `static synchronized void` | 刷新所有 Action |
| `registerOrReplaceAction(AnAction)` | `static void` | 注册或替换 Action |
| `Ke(ActionManager)` | `private static void` | 内部注册逻辑 |

**关联 Service**:
- `PermissionEnum.RIGHT_PERMISSION_ORDER_LIST` -> 右键权限排序列表
- `PermissionEnum.getAction()` -> 获取权限对应的 Action
- `CommandEnum` 枚举: `GIT_COMMIT_MESSAGE`, `CODE_DEBUG`, `GIT_REVIEW`
- `BasicActionsBundle.message(String, Object[])` -> 国际化消息
- `OpenTelemetryUtil.H()` / `FontKt.H()` (字符串解密)

**创建的 Action 实例**:
- `OpenWindowAction` -> 注册到 ActionManager
- `CommitMessageSuggestionAction` -> `GIT_COMMIT_MESSAGE` 命令
- `PrepushReviewAction` -> `GIT_REVIEW` 命令
- `TerminalAction` -> `CODE_DEBUG` 命令
- `BatchUTGeneratorAction` -> 批量单测
- `BatchFunctionCommentAction` -> 批量函数注释
- `OpenInlayInlineChatAction` -> 内联聊天

**逻辑推断**: Action 注册工具类。根据用户权限动态注册/替换 Action 到 IntelliJ ActionManager。`refreshActions` 在权限变更时重新注册所有 Action。`RIGHT_PERMISSION_ORDER_LIST` 定义了右键菜单 Action 的显示顺序。

---

## 9. Action -> Service 调用矩阵

| Action | 主要 Service 调用 | CommandEnum | PageEnum | PermissionEnum |
|--------|-------------------|-------------|----------|----------------|
| **AcceptInlaysAction** | `AICodeRequestSettings`, `IndentLineUtil`, `CancelRequestTip` | - | - | - |
| **AcceptLineCodeInlaysAction** | `AICodeRequestSettings`, `IndentLineUtil`, `LanguageFileExtensionDetails` | - | - | - |
| **AcceptWordInlaysAction** | `AICodeRequestSettings`, `IndentLineUtil`, `ConditionalActionConfiguration` | - | - | - |
| **DisposeInlaysAction** | (父类 handler) | - | - | - |
| **CycleNextEditorInlays** | `OpenTelemetryUtil` | - | - | - |
| **CyclePreviousEditorInlays** | `PropertyUtils` | - | - | - |
| **RequestCodeGenerateAction** | `EditorUtil`, `ApplicationUtil`, `AICodeLanguageInfo` | - | - | - |
| **EnableAutoTriggerCodeGenerateAction** | `AICodeSettingsState`, `StatusBarPopup`, `WebViewWindowPanel`, `CommonService` | - | - | - |
| **ExplainCodeAction** | (via BaseAction) | `CODE_EXPLAIN` | `CHAT_VIEW` | `COMMENTS` |
| **CodeOptimizeAction** | (via BaseAction) | `CODE_OPTIMIZE` | `CHAT_VIEW` | `CODE_OPTIMIZATION` |
| **FunctionSplitAction** | (via BaseAction) | `CODE_SPLIT` | `CHAT_VIEW` | `FUNCTION_SPLIT` |
| **InlineCommentAction** | (via BaseAction) | `CODE_INLINE_COMMENT` | `CHAT_VIEW` | `LINE_COMMENTS` |
| **DocumentCommentAction** | (via BaseAction) | `CODE_COMMENT` | `CHAT_VIEW` | `DOC_COMMENTS` |
| **CodeCheckAction** | `CommonService.openPage` | - | `CODE_CHECK` | - |
| **PrepushReviewAction** | `GitReviewService`, `CommonService`, `SocketMessageHandleListener` | `GIT_REVIEW` | `CODE_REVIEW` | `REVIEW` |
| **CommitMessageSuggestionAction** | `PluginWebsocketClient`, `OverlayUtils` | `GIT_COMMIT_MESSAGE` | - | `GENERATE_COMMIT` |
| **UnitTestAction** | `UnitTestService`, `CppTestService` | - | - | `UNIT_TESTING` |
| **BatchUTGeneratorAction** | `BatchUnitTestTemplateService`, `UserService` | - | - | `BATCH_UNITTEST` |
| **BatchFunctionCommentAction** | `CommonService`, `ChatService`, `SocketMessageHandleListener`, `UserService` | `CODE_COMMENT`, `TALK_INTELLIGENT` | `CHAT_VIEW` | `DOC_COMMENTS` |
| **OpenWindowAction** | `CommonService.openPage` | - | `CHAT_VIEW` | - |
| **PluginSettingAction** | `CommonService.openPage` | - | `SETTING_PAGE` | - |
| **LogoutAction** | `UserService.logout`, `PluginWebsocketClient` | `USER_LOGIN` | - | - |
| **UserInfoAction** | `AICodeSettingsState` | - | - | - |
| **TerminalAction** | `Presentation.handleDebug` | `CODE_DEBUG` | - | `CODE_DEBUG` |
| **RefreshAction** | `ChatService.refreshAgent`, `RestartableAgentProcessService` | - | - | - |
| **TipPromoterAction** | (ActionPromoter 接口) | - | - | - |
| **CodePromoterAction** | (ActionPromoter 接口) | - | - | - |
| **CodeProblemsIntentionAction** | `Presentation.handleDebug`, `AICodeSettingsState` | - | - | - |
| **CodeProblemsTreePopupAction** | `Presentation.handleDebug`, `CommonService.messageBus` | - | - | - |
| **BaseAction** | `CommonService`, `ChatService`, `SocketMessageHandleListener` | 多种 | `CHAT_VIEW` | 多种 |
| **ActionsUtil** | `PermissionEnum.getAction`, `ActionManager` | 多种 | - | 多种 |

---

## 附录: 类继承关系图

```
AnAction (IntelliJ Platform)
  +-- PluginAnAction (abstract)
  |     +-- BaseAction (abstract)
  |     |     +-- ExplainCodeAction
  |     |     +-- CodeOptimizeAction
  |     |     +-- FunctionSplitAction
  |     |     +-- InlineCommentAction
  |     |     +-- DocumentCommentAction
  |     +-- CodeCheckAction
  |     +-- UnitTestAction
  |     +-- OpenInlayInlineChatAction
  |     +-- TerminalAction
  |     +-- OpenWindowAction
  |     +-- PluginSettingAction
  |     +-- LogoutAction
  |     +-- UserInfoAction
  |     +-- RefreshAction
  |     +-- CodeProblemsTreePopupAction
  |     +-- CommitMessageSuggestionAction
  |     +-- PrepushReviewAction
  |     +-- BatchUTGeneratorAction
  |     +-- EnableAutoTriggerCodeGenerateAction
  |     +-- RequestCodeGenerateAction
  |     +-- Q.sa (abstract)
  |           +-- CycleNextEditorInlays
  |           +-- CyclePreviousEditorInlays
  +-- (不继承 PluginAnAction)
        +-- CodePromoterAction (implements ActionPromoter)

EditorAction (IntelliJ Platform)
  +-- AcceptInlaysAction (implements DumbAware, CodeAction)
  +-- AcceptLineCodeInlaysAction (implements DumbAware, CodeAction)
  +-- AcceptWordInlaysAction (implements DumbAware, CodeAction)
  +-- DisposeInlaysAction (implements DumbAware, CodeAction)

BaseIntentionAction (IntelliJ Platform)
  +-- CodeProblemsIntentionAction (implements Iconable)

ActionPromoter (IntelliJ Platform)
  +-- CodePromoterAction
  +-- TipPromoterAction

DialogWrapper (IntelliJ Platform)
  +-- BatchUnitTestDialog

DefaultMutableTreeNode (Swing)
  +-- AbstractNode (abstract)
        +-- TreeRootNode
        +-- FileNode

Tree (IntelliJ Platform)
  +-- ResultTree

ColoredTreeCellRenderer (IntelliJ Platform)
  +-- TreeCellRenderer

TreeCellRenderer (Swing Interface)
  +-- CheckboxTreeCellRenderer

CompileStatusNotification + CompilationStatusListener (IntelliJ Platform)
  +-- CoverageCompileStatusNotification

JPanel (Swing)
  +-- ExcludeMethodConfigurable (implements ConfigurableUi, Disposable)

(纯数据类)
  +-- GeneratorConfig
  +-- MethodGeneratorConfig

(标记接口)
  +-- CodeAction

(工具类)
  +-- ActionsUtil
  +-- BatchUnitTestTemplateService
```

---

## 附录: 混淆字符串解密方法

所有混淆字符串通过以下方法解密:
- `com.aicode.util.Application.H(Object)` -> 通用解密
- `com.aicode.util.PropertyUtils.H(Object)` -> 属性解密
- `com.aicode.service.editor.CancelRequestTip.H(Object)` -> 请求提示解密
- `com.aicode.util.IndentLineUtil.H(Object)` -> 缩进工具解密
- `com.aicode.inline.status.InlineChatStatusServiceKt.H(Object)` -> 内联聊天状态解密
- `com.aicode.diff.GenericUtils.H(Object)` -> 通用工具解密
- `com.aicode.action.batch.GeneratorConfig.H(Object)` -> 生成器配置解密
- `com.aicode.apm.OpenTelemetryUtil.H(Object)` -> APM 解密
- `com.aicode.exception.RequestTimeoutException.H(Object)` -> 异常解密
- `com.aicode.exception.RequestCancelException.H(Object)` -> 取消异常解密
- `com.aicode.content.util.file.FileExtensionLanguageDetails.H(Object)` -> 文件扩展名解密
- `com.aicode.content.util.file.LanguageFileExtensionDetails.H(Object)` -> 语言扩展名解密
- `com.aicode.content.util.OverlayUtils.H(Object)` -> 覆盖层工具解密
- `com.aicode.content.util.EditorUtils.H(Object)` -> 编辑器工具解密
- `com.aicode.inline.ide.ConditionalActionConfiguration.H(Object)` -> 条件配置解密
- `com.aicode.inline.controller.ChatInputController.H(Object)` -> 聊天输入解密
- `com.aicode.ui.FontKt.H(Object)` -> 字体解密
- `com.aicode.util.JComponentKt.H(Object)` -> 组件解密
- `com.aicode.util.AICodeStringUtil.H(Object)` -> 字符串工具解密
- `com.aicode.util.AICodeUtils.H(Object)` -> 通用工具解密
- `com.aicode.util.NewFileUtils.H(Object)` -> 新文件工具解密
- `com.aicode.util.PositionUtil.H(Object)` -> 位置工具解密
- `com.aicode.util.Maps.H(Object)` -> Map 工具解密
- `com.aicode.language.AICodeLanguageInfo.H(Object)` -> 语言信息解密
- `com.aicode.action.batch.MethodGeneratorConfig.H(Object)` -> 方法配置解密

每个类中还包含 `private static void enum(int)` 方法，这是混淆器的字符串解密初始化方法，在类加载时调用。
