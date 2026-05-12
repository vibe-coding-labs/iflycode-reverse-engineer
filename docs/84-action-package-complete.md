# iFlyCode Action 包完整分析

> 版本: iFlyCode 3.4.2-222
> 包路径: `com.aicode.action` (含子包 `click`, `batch`, `batch.doc`, `batch.node`)
> 外部类总数: 42

---

## 目录

1. [代码补全操作组](#1-代码补全操作组)
2. [代码功能操作组](#2-代码功能操作组)
3. [Git 操作组](#3-git-操作组)
4. [单测操作组](#4-单测操作组)
5. [UI 操作组](#5-ui-操作组)
6. [一键修复组](#6-一键修复组)
7. [树形组件组](#7-树形组件组)
8. [基础与工具类](#8-基础与工具类)
9. [Action -> Service 调用矩阵](#9-action--service-调用矩阵)

---

## 1. 代码补全操作组

### 1.1 AcceptInlaysAction

- **包**: `com.aicode.action`
- **源文件**: `xd` (混淆)
- **签名**: `public class AcceptInlaysAction extends EditorAction implements DumbAware, CodeAction`
- **内部类**: `AcceptInlaysAction$pa extends EditorActionHandler`

**字段**: 无实例字段

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `<init>()` | 构造器 | 创建内部 `pa` handler, 设置 injectedContext |
| `isSupported(Editor)` | `static boolean` | 检查编辑器是否支持 inlay 接受 |
| `update(AnActionEvent)` | `void` | 更新 action 可见性/可用性 |
| `Rf(AnActionEvent)` | `private boolean` | 内部可用性判断 |
| `Re(Document, int, int)` | `static boolean` | 文档范围有效性检查 |
| `enum(int)` | `private static void` | 混淆字符串解密 |

**内部类 pa 方法**:
| 方法 | 说明 |
|------|------|
| `isEnabledForCaret(Editor, Caret, DataContext)` | 判断 caret 位置是否启用 |
| `executeInCommand(Editor, DataContext)` | 是否在 command 中执行 |
| `doExecute(Editor, Caret, DataContext)` | 执行接受 inlay 代码 |

**关联 Service**:
- `AICodeRequestSettings.settings()` -> `CodeGenerateRequestState.isShowIdeCodeTips()`
- `CancelRequestTip.H()` (字符串解密)
- `IndentLineUtil.indentLine(Project, Editor, int, int, int)`
- `AICodeStringUtil.isSpacesOrTabs(CharSequence, boolean)`
- `GeneratorConfig.H()` (字符串解密)

**逻辑推断**: 接受编辑器中的 inline code completion inlay 提示，将建议代码插入文档。`isSupported` 检查 `isShowIdeCodeTips` 设置和编辑器状态。`doExecute` 通过 `IndentLineUtil.indentLine` 处理缩进后插入代码。

---

### 1.2 AcceptLineCodeInlaysAction

- **包**: `com.aicode.action`
- **源文件**: `dk` (混淆)
- **签名**: `public class AcceptLineCodeInlaysAction extends EditorAction implements DumbAware, CodeAction`
- **内部类**: `AcceptLineCodeInlaysAction$va extends EditorActionHandler`

**字段**: 无实例字段

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `<init>()` | 构造器 | 创建内部 `va` handler |
| `isSupported(Editor)` | `static boolean` | 检查编辑器是否支持行级 inlay 接受 |
| `update(AnActionEvent)` | `void` | 更新 action 可用性 |
| `Rf(AnActionEvent)` | `private boolean` | 内部可用性判断 |
| `Re(Document, int, int)` | `static boolean` | 文档范围有效性检查 |

**内部类 va 方法**:
| 方法 | 说明 |
|------|------|
| `isEnabledForCaret(Editor, Caret, DataContext)` | 判断 caret 位置是否启用 |
| `executeInCommand(Editor, DataContext)` | 是否在 command 中执行 |
| `doExecute(Editor, Caret, DataContext)` | 执行接受行级 inlay 代码 |

**关联 Service**:
- `AICodeRequestSettings.settings()` -> `CodeGenerateRequestState.isShowIdeCodeTips()`
- `LanguageFileExtensionDetails.H()` (字符串解密)
- `NewFileUtils.H()` (字符串解密)
- `IndentLineUtil.indentLine(Project, Editor, int, int, int)`
- `AICodeStringUtil.isSpacesOrTabs(CharSequence, boolean)`

**逻辑推断**: 仅接受当前行的代码补全 inlay，与 `AcceptInlaysAction` 类似但粒度为单行。使用 `LanguageFileExtensionDetails` 做语言扩展名相关处理。

---

### 1.3 AcceptWordInlaysAction

- **包**: `com.aicode.action`
- **源文件**: `li` (混淆)
- **签名**: `public class AcceptWordInlaysAction extends EditorAction implements DumbAware, CodeAction`
- **内部类**: `AcceptWordInlaysAction$wa extends EditorActionHandler`

**字段**: 无实例字段

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `<init>()` | 构造器 | 创建内部 `wa` handler |
| `isSupported(Editor)` | `static boolean` | 检查编辑器是否支持单词级 inlay 接受 |
| `update(AnActionEvent)` | `void` | 更新 action 可用性 |
| `Rf(AnActionEvent)` | `private boolean` | 内部可用性判断 |
| `Re(Document, int, int)` | `static boolean` | 文档范围有效性检查 |

**内部类 wa 方法**:
| 方法 | 说明 |
|------|------|
| `isEnabledForCaret(Editor, Caret, DataContext)` | 判断 caret 位置是否启用 |
| `executeInCommand(Editor, DataContext)` | 是否在 command 中执行 |
| `doExecute(Editor, Caret, DataContext)` | 执行接受单词级 inlay 代码 |

**关联 Service**:
- `AICodeRequestSettings.settings()` -> `CodeGenerateRequestState.isShowIdeCodeTips()`
- `ConditionalActionConfiguration.H()` (字符串解密)
- `PropertyUtils.H()` (字符串解密)
- `IndentLineUtil.indentLine(Project, Editor, int, int, int)`
- `AICodeStringUtil.isSpacesOrTabs(CharSequence, boolean)`

**逻辑推断**: 仅接受当前单词的代码补全 inlay，最细粒度的接受操作。使用 `ConditionalActionConfiguration` 做条件配置检查。

---

### 1.4 DisposeInlaysAction

- **包**: `com.aicode.action`
- **源文件**: `ki` (混淆)
- **签名**: `public class DisposeInlaysAction extends EditorAction implements DumbAware, CodeAction`

**字段**: 无实例字段

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `<init>()` | 构造器 | 创建默认 handler |

**关联 Service**: 无直接 Service 调用（逻辑在父类 EditorAction 的 handler 中）

**逻辑推断**: 关闭/清除编辑器中显示的所有 inline code completion inlay 提示。

---

### 1.5 CycleNextEditorInlays

- **包**: `com.aicode.action`
- **源文件**: `sd` (混淆)
- **签名**: `public class CycleNextEditorInlays extends Q.sa`
- **父类 Q.sa**: `abstract class Q.sa extends PluginAnAction` (抽象基类，提供 `doCycleAction` 抽象方法)

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `ID` | `static final String` | Action ID 常量 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `<init>()` | 构造器 | |
| `doCycleAction(Editor)` | `boolean` | 切换到下一个 inlay 提示 |
| `actionPerformed(AnActionEvent)` | `void` | 继承自 Q.sa |
| `update(AnActionEvent)` | `void` | 继承自 Q.sa |

**关联 Service**:
- `Application.H()` (字符串解密)
- `OpenTelemetryUtil.H()` (字符串解密/APM)

**逻辑推断**: 在多个 inlay 提示之间循环切换，聚焦下一个建议。Q.sa 基类提供 `getWarningHintText()` 和 `Nd(Editor, String)` 辅助方法。

---

### 1.6 CyclePreviousEditorInlays

- **包**: `com.aicode.action`
- **源文件**: `hi` (混淆)
- **签名**: `public class CyclePreviousEditorInlays extends Q.sa`

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `ID` | `static final String` | Action ID 常量 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `<init>()` | 构造器 | |
| `doCycleAction(Editor)` | `boolean` | 切换到上一个 inlay 提示 |
| `actionPerformed(AnActionEvent)` | `void` | 继承自 Q.sa |
| `update(AnActionEvent)` | `void` | 继承自 Q.sa |

**关联 Service**:
- `PropertyUtils.H()` (字符串解密)

**逻辑推断**: 在多个 inlay 提示之间反向循环切换，聚焦上一个建议。

---

### 1.7 RequestCodeGenerateAction

- **包**: `com.aicode.action`
- **源文件**: `pl` (混淆)
- **签名**: `public class RequestCodeGenerateAction extends PluginAnAction implements CodeAction`

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `enum` | `static final Logger` | 日志器 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `<init>()` | 构造器 | |
| `actionPerformed(AnActionEvent)` | `void` | 触发代码生成请求 |
| `update(AnActionEvent)` | `void` | 更新可用性 |

**关联 Service**:
- `EditorUtil.isSelectedEditor(Editor)` -> 检查编辑器是否选中
- `ApplicationUtil.isSupportLanguage(Editor)` -> 检查语言支持
- `AICodeLanguageInfo.H()` (字符串解密)
- `IndentLineUtil.H()` (字符串解密)
- `CodeTipRequestType.Manual` -> 手动触发类型枚举

**逻辑推断**: 手动触发代码补全请求（非自动触发）。检查编辑器状态和语言支持后，以 `CodeTipRequestType.Manual` 类型发起补全请求。

---

### 1.8 EnableAutoTriggerCodeGenerateAction

- **包**: `com.aicode.action`
- **源文件**: `nh` (混淆)
- **签名**: `public class EnableAutoTriggerCodeGenerateAction extends PluginAnAction implements CodeAction`

**字段**: 无实例字段

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `<init>()` | 构造器 | |
| `actionPerformed(AnActionEvent)` | `void` | 切换自动触发开关 |
| `update(AnActionEvent)` | `void` | 更新选中状态 |
| `isDumbAware()` | `boolean` | 返回 true |

**关联 Service**:
- `AICodeSettingsState.getInstance()` -> `autoTrigger` 字段读写
- `PluginStartupActivity.getApiKey()` -> 检查登录状态
- `StatusBarPopup.update(Project)` -> 更新状态栏图标
- `WebViewWindowPanel.WEB_VIEW_PANEL` -> 获取 WebView 面板
- `CommonService.getConfig()` -> 获取配置
- `WebViewWindowPanel.sendMessage2webView(Object)` -> 通知 WebView
- `MessageBundle.get(String)` -> 国际化消息
- `RequestTimeoutException.H()` (字符串解密)
- `FileExtensionLanguageDetails.H()` (字符串解密)

**逻辑推断**: 切换自动触发代码补全的开关。修改 `AICodeSettingsState.autoTrigger` 后，同步更新状态栏图标和 WebView 前端。

---

## 2. 代码功能操作组

### 2.1 BaseAction (抽象基类)

- **包**: `com.aicode.action.click`
- **源文件**: `qf` (混淆)
- **签名**: `public abstract class BaseAction extends PluginAnAction`

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `taskName` | `String` | 任务名称 |
| `type` | `String` | 命令类型 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `BaseAction(String, String)` | 构造器 | 传入 taskName, type |
| `BaseAction(String, String, Icon)` | 构造器 | 含图标 |
| `BaseAction()` | 构造器 | 默认 |
| `actionPerformed(AnActionEvent)` | `void` | 委托 `handle(event, type)` |
| `handle(AnActionEvent, String)` | `static void` | 核心处理逻辑 |
| `handleRight(Project, FirstChatMessage)` | `static void` | 右键菜单处理 |
| `jD(Project, FirstChatMessage)` | `private static void` | 内部消息处理 |
| `update(AnActionEvent)` | `void` | 权限检查更新 |

**关联 Service**:
- `PluginStartupActivity.handleExecutorService` -> 异步执行器
- `PluginStartupActivity.getApiKey()` -> 登录检查
- `AICodeSettingsState.getInstance()` -> `permissions` 权限集合
- `CommandEnum` 枚举: `CODE_COMMENT`, `CODE_INLINE_COMMENT`, `CODE_SPLIT`, `CODE_EXPLAIN`, `CODE_OPTIMIZE`
- `PermissionEnum` 枚举: `DOC_COMMENTS`, `LINE_COMMENTS`, `FUNCTION_SPLIT`, `COMMENTS`, `CODE_OPTIMIZATION`
- `PageEnum.CHAT_VIEW` -> 聊天页面
- `CommonService.openPage(Project, PageEnum)` -> 打开页面
- `ChatService.getRightChatMessage2Web(Project, String)` -> 获取右键聊天消息
- `CommonService.chatMessage2Web(Project, FirstChatMessage, Boolean)` -> 发送聊天消息
- `SocketMessageHandleListener.send2Web(Project, Object)` -> WebSocket 发送
- `ActionsUtil.registerOrReplaceAction(AnAction)` -> 注册 Action
- `OpenTelemetryUtil.H()` -> APM 追踪
- `WebViewWindowPanel.CODE_MESSAGE_DATA` -> WebView 数据键

**逻辑推断**: 所有代码功能 Action 的抽象基类。`actionPerformed` 将 `type` (CommandEnum 类型字符串) 传入 `handle` 静态方法，该方法检查权限、构建 `FirstChatMessage`、打开聊天页面并发送消息到 WebSocket。`update` 根据 `CommandEnum` 类型检查对应 `PermissionEnum` 权限。

---

### 2.2 ExplainCodeAction

- **包**: `com.aicode.action.click`
- **源文件**: `lo` (混淆)
- **签名**: `public class ExplainCodeAction extends BaseAction`

**字段**: 继承自 BaseAction

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `ExplainCodeAction(String, String)` | 构造器 | 委托 `BaseAction(taskName, type)` |

**关联 Service**: 通过 BaseAction 间接调用
- `CommandEnum.CODE_EXPLAIN` / `PermissionEnum.COMMENTS`

**逻辑推断**: 代码解释功能，选中代码后发送到 AI 聊天进行解释。

---

### 2.3 CodeOptimizeAction

- **包**: `com.aicode.action.click`
- **源文件**: `te` (混淆)
- **签名**: `public class CodeOptimizeAction extends BaseAction`

**字段**: 继承自 BaseAction

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `CodeOptimizeAction(String, String)` | 构造器 | 委托 `BaseAction(taskName, type)` |

**关联 Service**: 通过 BaseAction 间接调用
- `CommandEnum.CODE_OPTIMIZE` / `PermissionEnum.CODE_OPTIMIZATION`

**逻辑推断**: 代码优化功能，选中代码后发送到 AI 进行优化建议。

---

### 2.4 FunctionSplitAction

- **包**: `com.aicode.action.click`
- **源文件**: `jg` (混淆)
- **签名**: `public class FunctionSplitAction extends BaseAction`

**字段**: 继承自 BaseAction

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `FunctionSplitAction(String, String)` | 构造器 | 委托 `BaseAction(taskName, type)` |

**关联 Service**: 通过 BaseAction 间接调用
- `CommandEnum.CODE_SPLIT` / `PermissionEnum.FUNCTION_SPLIT`

**逻辑推断**: 函数拆分功能，将长函数拆分为多个小函数。

---

### 2.5 InlineCommentAction

- **包**: `com.aicode.action.click`
- **源文件**: `lm` (混淆)
- **签名**: `public class InlineCommentAction extends BaseAction`

**字段**: 继承自 BaseAction

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `InlineCommentAction(String, String)` | 构造器 | 委托 `BaseAction(taskName, type)` |

**关联 Service**: 通过 BaseAction 间接调用
- `CommandEnum.CODE_INLINE_COMMENT` / `PermissionEnum.LINE_COMMENTS`

**逻辑推断**: 行内注释功能，为选中代码添加行内注释。

---

### 2.6 DocumentCommentAction

- **包**: `com.aicode.action.click`
- **源文件**: `hn` (混淆)
- **签名**: `public class DocumentCommentAction extends BaseAction`

**字段**: 继承自 BaseAction

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `DocumentCommentAction(String, String)` | 构造器 | 委托 `BaseAction(taskName, type)` |

**关联 Service**: 通过 BaseAction 间接调用
- `CommandEnum.CODE_COMMENT` / `PermissionEnum.DOC_COMMENTS`

**逻辑推断**: 文档注释功能，为函数/类生成 Javadoc/KDoc 风格文档注释。

---

### 2.7 CodeCheckAction

- **包**: `com.aicode.action.click`
- **源文件**: `qm` (混淆)
- **签名**: `public class CodeCheckAction extends PluginAnAction`

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `byte` | `String` | 混淆字段 - 可能是文件路径 |
| `content` | `static String` | 代码内容 |
| `path` | `static String` | 文件路径 |
| `enum` | `static final boolean` | 混淆常量 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `CodeCheckAction(String)` | 构造器 | |
| `CodeCheckAction(String, String, Icon)` | 构造器 | |
| `actionPerformed(AnActionEvent)` | `void` | 触发代码检查 |
| `update(AnActionEvent)` | `void` | 更新可用性 |
| `handle(Project, VirtualFile, Document)` | `static void` | 静态处理入口 |

**关联 Service**:
- `PluginStartupActivity.getApiKey()` -> 登录检查
- `ActionsUtil.registerOrReplaceAction(AnAction)` -> 注册 Action
- `CommonService.openPage(Project, PageEnum.CODE_CHECK)` -> 打开代码检查页面
- `RequestResultList.H()` (字符串解密)
- `PositionUtil.H()` (字符串解密)

**逻辑推断**: 代码检查功能，打开 `CODE_CHECK` 页面进行代码质量分析。`handle` 静态方法可被其他组件直接调用。

---

## 3. Git 操作组

### 3.1 PrepushReviewAction

- **包**: `com.aicode.action`
- **源文件**: `rh` (混淆)
- **签名**: `public class PrepushReviewAction extends PluginAnAction`

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `PREPUSH_REVIEW_BUTTON` | `static AtomicBoolean` | 按钮状态标志 |
| `path` | `static String` | 审查路径 |
| `PAGE_READY` | `static AtomicBoolean` | 页面就绪标志 |
| `enum` | `static final List<String>` | 支持的文件扩展名列表 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `PrepushReviewAction(String, String)` | 构造器 | |
| `actionPerformed(AnActionEvent)` | `void` | 触发预推送审查 |
| `update(AnActionEvent)` | `void` | 更新可用性 |
| `getSelectedChange(AnActionEvent)` | `List<Change>` | 获取选中的 VCS 变更 |
| `yf(AnActionEvent)` | `private void` | 内部处理 |
| `Td(AnActionEvent)` | `private void` | 内部处理 |

**关联 Service**:
- `AICodeSettingsState.getInstance()` -> `permissions` 权限集合
- `PermissionEnum.REVIEW` -> 审查权限
- `PluginStartupActivity.getApiKey()` -> 登录检查
- `OverlayUtils.showInfoBalloon(String, Point)` -> 显示提示气泡
- `WebViewResponseTypeEnum.CODE_REVIEW_RECEIVER_PAGE_INIT` -> 审查页面初始化类型
- `SocketMessageHandleListener.send2Web(Project, Object)` -> WebSocket 发送
- `CommonService.openPage(Project, PageEnum.CODE_REVIEW)` -> 打开代码审查页面
- `GitReviewService.sendGitDiffRequest(String, Project)` -> 发送 Git Diff 请求
- `AICodeLanguageInfo.H()` / `PropertyUtils.H()` (字符串解密)

**逻辑推断**: 推送前代码审查。获取 VCS 变更列表，发送 Git Diff 到后端进行 AI 审查，在 `CODE_REVIEW` 页面展示结果。

---

### 3.2 CommitMessageSuggestionAction

- **包**: `com.aicode.action`
- **源文件**: `mi` (混淆)
- **签名**: `public class CommitMessageSuggestionAction extends PluginAnAction`

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `COMMIT_MESSAGE_BUTTON` | `static AtomicBoolean` | 按钮状态标志 |
| `COMMIT_MESSAGE_MAP` | `static Map<String, EditorTextField>` | 提交消息编辑器映射 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `CommitMessageSuggestionAction(String, String)` | 构造器 | |
| `actionPerformed(AnActionEvent)` | `void` | 触发提交消息生成 |
| `update(AnActionEvent)` | `void` | 更新可用性 |
| `getChanges(AnActionEvent)` | `List<Change>` | 获取 VCS 变更列表 |
| `SD(List<Change>)` | `private Map<String, LinkedHashSet<String>>` | 按文件分组变更 |
| `le(FilePath)` | `static Change` | 查找文件对应的 Change |
| `yf(AnActionEvent)` | `private void` | 内部处理 |
| `Td(AnActionEvent)` | `private void` | 内部处理 |

**关联 Service**:
- `AICodeSettingsState.getInstance()` -> `permissions` 权限集合
- `PermissionEnum.GENERATE_COMMIT` -> 生成提交消息权限
- `PluginStartupActivity.getApiKey()` -> 登录检查
- `OverlayUtils.showInfoBalloon(String, Point)` -> 显示提示气泡
- `MessageDto` -> 构建 WebSocket 消息
- `CommandEnum.GIT_COMMIT_MESSAGE` -> 提交消息命令
- `PluginWebsocketClient.sendWsMessage(MessageDto, Project)` -> 发送 WebSocket 消息
- `Icons.StatusBarCompletionInProgress` / `Icons.getCurrentIcon()` -> 状态图标
- `CancelRequestTip.H()` / `OverlayUtils.H()` (字符串解密)

**逻辑推断**: AI 生成 Git 提交消息。收集 VCS 变更，通过 WebSocket 发送 `GIT_COMMIT_MESSAGE` 命令，AI 返回建议的提交消息填入编辑器。

---

## 4. 单测操作组

### 4.1 UnitTestAction

- **包**: `com.aicode.action.click`
- **源文件**: `mm` (混淆)
- **签名**: `public class UnitTestAction extends PluginAnAction`

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `taskName` | `String` | 任务名称 |
| `type` | `String` | 命令类型 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `UnitTestAction(String, String)` | 构造器 | |
| `actionPerformed(AnActionEvent)` | `void` | 触发单测生成 |
| `handle(AnActionEvent, Project, Editor, String)` | `void` | 处理单测生成 |
| `update(AnActionEvent)` | `void` | 更新可用性 |

**关联 Service**:
- `AICodeSettingsState.getInstance()` -> `permissions` 权限集合
- `PermissionEnum.UNIT_TESTING` -> 单测权限
- `PluginStartupActivity.getApiKey()` -> 登录检查
- `WebViewWindowPanel.UNIT_TEST_MESSAGE_DATA` -> 单测数据键
- `LanguageEnum` 枚举: `JAVA`, `CPP_LANGUAGE_01`, `C_LANGUAGE_01`, `PYTHON_LANGUAGE_01`
- `UnitTestService.handleJavaUnitTest(Project, Editor)` -> Java 单测处理
- `CppTestService.resolveCppTest(Project, Editor, String, PsiElement)` -> C/C++ 单测处理
- `UnitTestService.sendUnitTestErrInfo(Project, WebViewDataTypeEnum, String, String)` -> 发送错误信息
- `BaseAction.handle(AnActionEvent, String)` -> 回退到 BaseAction 处理
- `FileUtils.getFileExtension(String)` -> 获取文件扩展名
- `LanguageEnum.getLanguage(String)` -> 获取语言枚举
- `WebViewDataTypeEnum.UNIT_TEST_RECEIVE_FUNCTION_CASE` -> 单测数据类型

**逻辑推断**: 单文件单测生成。根据语言类型分发: Java 走 `UnitTestService.handleJavaUnitTest`，C/C++ 走 `CppTestService.resolveCppTest`，Python 等其他语言回退到 `BaseAction.handle`。不支持的语言通过 `UnitTestService.sendUnitTestErrInfo` 报错。

---

### 4.2 BatchUTGeneratorAction

- **包**: `com.aicode.action.batch`
- **源文件**: `zi` (混淆)
- **签名**: `public class BatchUTGeneratorAction extends PluginAnAction`
- **内部类**: `BatchUTGeneratorAction$ta extends Task.Backgroundable`

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `enum` | `static final Logger` | 日志器 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `BatchUTGeneratorAction(String, String)` | 构造器 | |
| `actionPerformed(AnActionEvent)` | `void` | 触发批量单测生成 |
| `update(AnActionEvent)` | `void` | 更新可用性 |
| `cE(Project, VirtualFile[], List<FileNode>)` | `private Module` | 查找对应模块 |
| `Hf(Project, String)` | `private void` | 内部处理 |
| `EF(Project, VirtualFile[], Module, List<FileNode>)` | `private void` | 执行批量生成 |

**内部类 ta (后台任务)**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `super` | `final String` | 任务标识 |
| `for` | `final List` | 文件节点列表 |
| `if` | `final BatchUTGeneratorAction` | 外部类引用 |
| `case` | `final List` | 排除方法列表 |
| `final` | `final StringBuilder` | 输出缓冲 |
| `try` | `final Module` | 模块 |
| `float` | `final Project` | 项目 |
| `byte` | `final VirtualFile[]` | 虚拟文件数组 |
| `enum` | `final GeneratorConfig` | 生成器配置 |

| 方法 | 说明 |
|------|------|
| `run(ProgressIndicator)` | 后台执行批量单测生成 |
| `onFinished()` | 完成回调 |
| `onThrowable(Throwable)` | 错误回调 |
| `PE(Project, GeneratorConfig)` | 编译后处理 |
| `LE(Project, VirtualFile[], List, List, GeneratorConfig, String, Module, StringBuilder)` | 执行生成逻辑 |

**关联 Service**:
- `AICodeSettingsState.getInstance()` -> `permissions` 权限集合
- `PermissionEnum.BATCH_UNITTEST` -> 批量单测权限
- `PluginStartupActivity.getApiKey()` -> 登录检查
- `UserService.showMessage(Project)` -> 用户消息
- `BatchUnitTestTemplateService.getTestPath(Project, VirtualFile)` -> 获取测试路径
- `GeneratorConfig` -> 生成器配置对象
- `BasicActionsBundle.message(String, Object[])` -> 国际化消息
- `PsiUtils.instanceOf(Object, String[])` -> 类型检查
- `Icons.getCurrentIcon()` / `Icons.ToolWindowIcon` -> 图标
- `InlineChatStatusServiceKt.H()` / `CancelRequestTip.H()` (字符串解密)

**逻辑推断**: 批量单测生成入口。检查权限后，弹出 `BatchUnitTestDialog` 配置对话框，用户确认后创建 `ta` 后台任务执行编译和生成。

---

### 4.3 BatchUnitTestDialog

- **包**: `com.aicode.action.batch`
- **源文件**: `pi` (混淆)
- **签名**: `public class BatchUnitTestDialog extends DialogWrapper`

**字段** (混淆严重，关键字作字段名):
| 字段 | 类型 | 说明 |
|------|------|------|
| `const` | `final Project` | 项目 |
| `void` | `final Module` | 模块 |
| `true` | `final String` | 源文件路径 |
| `long` | `final String` | 测试路径 |
| `try` | `final String` | 文件名 |
| `final` | `List<FileNode>` | 文件节点列表 |
| `throw` | `List<String>` | 排除方法列表 |
| `native` | `JPanel` | 主面板 |
| `while` | `ComboBox` | 测试框架选择 |
| `null` | `ComboBox` | Mock 框架选择 |
| `if` | `ComboBox` | 模板选择 |
| `catch` | `ComboBox` | 其他选择 |
| `short` | `JBTextField` | 文本输入 |
| `this` | `TextFieldWithBrowseButton` | 路径浏览 |
| `goto` | `ExcludeMethodConfigurable` | 排除方法配置 |
| `false` | `JRadioButton` | 单选按钮 |
| `do` | `JRadioButton` | 单选按钮 |
| `else` | `JRadioButton` | 单选按钮 |
| `new` | `JRadioButton` | 单选按钮 |
| `float` | `JRadioButton` | 单选按钮 |
| `case` | `JBCheckBox` | 复选框 |
| `byte` | `JBCheckBox` | 复选框 |
| `enum` | `JLabel` | 标签 |
| `assert` | `String` | 断言 |
| `super` | `String` | 父类名 |
| `for` | `String` | 其他字符串 |
| `break` | `JPanel` | 面板 |
| `class` | `JPanel` | 面板 |
| `char` | `JPanel` | 面板 |
| `int` | `static final Logger` | 日志器 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `BatchUnitTestDialog(Project, Module, String, String, String, List<FileNode>, List<String>, String)` | 构造器 | |
| `createCenterPanel()` | `JComponent` | 创建对话框中心面板 |
| `doOKAction()` | `void` | 确认按钮回调 |
| `getSelectedValue(GeneratorConfig)` | `GeneratorConfig` | 获取用户选择的配置 |
| `setDuplicateFileNameSwitch(DuplicateRule)` | `void` | 设置重复文件名规则 |
| `setGenerateByTemplateSwitch(GenaratebyTemplateSwitchEnum)` | `void` | 设置模板开关 |
| `changeGenerateByTemplateComponent(String)` | `void` | 切换模板组件 |
| `createUnitTestTreeView(List<FileNode>)` | `JPanel` | 创建文件树视图 |
| `setDuplicateFileNameSwitchComponent()` | `JPanel` | 重复文件名开关组件 |
| `createActions()` | `Action[]` | 创建对话框按钮 |
| `hB()` | `private GenaratebyTemplateSwitchEnum` | 获取模板开关枚举 |
| `getDuplicateFileNameSwitchEnum()` | `DuplicateRule` | 获取重复规则枚举 |
| `iF()` | `private JPanel` | 创建面板 |
| `iD(JBTextField)` | `private JPanel` | 创建面板 |
| `pD(ComboBox, ComboBox)` | `private JComponent` | 创建组件 |
| `Dc(ComboBox, ComboBox)` | `private JComponent` | 创建组件 |
| `RA(JBCheckBox)` | `private JComponent` | 创建组件 |
| `rD(TextFieldWithBrowseButton, JBCheckBox)` | `private JComponent` | 创建组件 |
| `za()` | `private JPanel` | 创建面板 |
| `If(Tree, TreePath, boolean)` | `private void` | 树节点选择 |
| `Nf(Icon, String)` | `private void` | 通知 |
| `AF(BatchUnitTestDialog)` | `static void` | 静态辅助 |

**关联 Service**: 无直接 Service 调用，纯 UI 对话框

**逻辑推断**: 批量单测配置对话框。提供测试框架选择 (JUnit/TestNG等)、Mock 框架选择 (Mockito等)、模板选择、排除方法配置、文件树视图等。`doOKAction` 收集配置到 `GeneratorConfig` 并触发后续编译和生成流程。

---

### 4.4 BatchUnitTestTemplateService

- **包**: `com.aicode.action.batch`
- **源文件**: `ol` (混淆)
- **签名**: `public class BatchUnitTestTemplateService`

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `final` | `static final Logger` | 日志器 |
| `try` | `static AtomicInteger` | 计数器 |
| `float` | `static final String` | 常量字符串 |
| `byte` | `static AtomicReference<BatchUnitTestDialog>` | 对话框引用 |
| `enum` | `static String` | 状态字符串 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `createUnitTestDialog(Project, Module, String, List<FileNode>, List<String>, StringBuilder)` | `static BatchUnitTestDialog` | 创建单测对话框 |
| `recursion(VirtualFile, Project, Set<VirtualFile>)` | `static void` | 递归查找文件 |
| `queryServerResource(Project)` | `static void` | 查询服务端资源 |
| `getVirtualFile(Project, List<VirtualFile>)` | `static List<VirtualFile>` | 获取虚拟文件列表 |
| `doCompile(Project, boolean, GeneratorConfig)` | `static void` | 执行编译 |
| `handleClassFiles(Project, List<VirtualFile>, List<FileNode>, List<String>, GeneratorConfig, String, Module, StringBuilder)` | `static void` | 处理 class 文件 |
| `getTestPath(Project, VirtualFile)` | `static String` | 获取测试路径 |
| `changeServerStatus(JsonObject)` | `static void` | 改变服务端状态 |
| `Md()` | `private static void` | 内部初始化 |
| `Ud()` | `private static void` | 内部处理 |
| `HE(Project, CompilationStatusListener)` | `private static void` | 注册编译监听 |
| `TC(Project)` | `private static CompileScope` | 创建编译范围 |
| `ad(Project, CompilerManager, CompileScope, boolean, GeneratorConfig)` | `private static void` | 执行编译 |

**关联 Service**:
- `PluginWebsocketClient.sendWsMessage(CommandEnum, Project)` -> 发送 `SERVER_RESOURCE` 命令
- `CommandEnum.SERVER_RESOURCE` -> 服务端资源命令
- `OverlayUtils.H()` (字符串解密)
- `NewFileUtils.H()` (字符串解密)
- `PsiUtils.instanceOf(Object, String[])` -> 类型检查
- `GeneratorConfig` -> 配置对象操作
- `FileNode` -> 文件节点操作
- `CoverageCompileStatusNotification` -> 编译状态通知

**逻辑推断**: 批量单测模板服务。负责创建对话框、递归查找源文件、查询服务端模板资源、执行编译、处理编译后的 class 文件。`handleClassFiles` 是核心方法，编译成功后调用 `CoverageCompileStatusNotification` 进行后续处理。

---

### 4.5 BatchFunctionCommentAction

- **包**: `com.aicode.action.batch.doc`
- **源文件**: `pm` (混淆)
- **签名**: `public class BatchFunctionCommentAction extends PluginAnAction`

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `enum` | `static final Logger` | 日志器 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `BatchFunctionCommentAction(String, String)` | 构造器 | |
| `actionPerformed(AnActionEvent)` | `void` | 触发批量函数注释 |
| `update(AnActionEvent)` | `void` | 更新可用性 |
| `getActionUpdateThread()` | `ActionUpdateThread` | 指定更新线程 |
| `buildCodeInfo(VirtualFile, PsiFile, Project)` | `static CodeInfoDto` | 构建代码信息 |
| `getFileCodeInfo(VirtualFile)` | `static CodeInfoDto` | 获取文件代码信息 |
| `Xd(VirtualFile)` | `private static CodeInfoDto` | 构建代码信息 |
| `Xe(VirtualFile, Project)` | `private FirstChatMessage` | 构建聊天消息 |
| `He(CodeInfoDto, JsonArray, Project)` | `private static FirstChatMessage` | 构建聊天消息 |
| `Pd(Project, FirstChatMessage)` | `private void` | 发送消息 |
| `yd(Project, FirstChatMessage)` | `private static void` | 发送消息 |

**关联 Service**:
- `AICodeSettingsState.getInstance()` -> `permissions` 权限集合
- `PermissionEnum.DOC_COMMENTS` -> 文档注释权限
- `PluginStartupActivity.getApiKey()` -> 登录检查
- `PluginStartupActivity.handleExecutorService` -> 异步执行器
- `UserService.showMessage(Project)` -> 用户消息
- `CommonService.openPage(Project, PageEnum.CHAT_VIEW)` -> 打开聊天页面
- `CommonService.chatMessage2Web(Project, FirstChatMessage, Boolean)` -> 发送聊天消息
- `SocketMessageHandleListener.send2Web(Project, Object)` -> WebSocket 发送
- `ChatService.SESSION_ID` -> 会话 ID 映射
- `CodeInfoDto` / `CodeInfoDto$RangeDTO` -> 代码信息 DTO
- `FirstChatMessage` / `FirstChatMessage$ValueDTO` -> 聊天消息 DTO
- `CommandEnum.CODE_COMMENT` / `CommandEnum.TALK_INTELLIGENT` -> 命令枚举
- `AssistantTypeEnum.IFLY_MATE` -> 助手类型
- `WebViewDataTypeEnum.CHAT_UPDATE_CONVERSATION_LIST` -> 数据类型
- `PositionUtil.getStartLineAndColumn()` / `getEndLineAndColumn()` -> 位置工具
- `FileUtils.getFileExtension(String)` -> 文件扩展名
- `IndentLineUtil.H()` / `InlineChatStatusServiceKt.H()` (字符串解密)

**逻辑推断**: 批量函数注释生成。对选中文件/目录中的所有函数，构建 `CodeInfoDto`（含代码内容、语言、范围等），通过 `FirstChatMessage` 发送到 AI 进行文档注释生成。

---

### 4.6 GeneratorConfig

- **包**: `com.aicode.action.batch`
- **源文件**: `sh` (混淆)
- **签名**: `public class GeneratorConfig`

**字段** (混淆严重):
| 字段 | 类型 | 说明 |
|------|------|------|
| `break` | `Boolean` | 启用模板生成 |
| `class` | `boolean` | 单文件模式 |
| `true` | `List<String>` | 生成文件路径列表 |
| `testFileName` | `String` | 测试文件名 |
| `this` | `List<String>` | 文件绝对路径列表 |
| `else` | `List<String>` | 排除方法列表 |
| `char` | `String` | 执行路径 |
| `int` | `boolean` | 测试私有方法 |
| `new` | `DuplicateRule` | 重复文件名规则 |
| `long` | `Integer` | 测试单元限制 |
| `super` | `Boolean` | 请求 AI |
| `for` | `UnitTestBaseEnum` | 测试框架 |
| `if` | `String` | Action ID |
| `case` | `boolean` | 覆盖模式 |
| `final` | `Module` | 源模块 |
| `try` | `Module` | 测试模块 |
| `float` | `UnitTestMockEnum` | Mock 框架 |
| `byte` | `String` | 测试模块目录 |
| `enum` | `TestGenerationProcess` | 生成过程状态 |

**方法**: 完整的 getter/setter 对（省略，共 30+ 方法）

**关联 Service**: 无直接 Service 调用，纯数据对象

**逻辑推断**: 单测生成器配置 DTO，存储所有单测生成相关配置参数。

---

### 4.7 MethodGeneratorConfig

- **包**: `com.aicode.action.batch`
- **源文件**: `pk` (混淆)
- **签名**: `public class MethodGeneratorConfig`

**字段** (混淆严重):
| 字段 | 类型 | 说明 |
|------|------|------|
| `true` | `UnitTestDto$DataDTO$FunctionDataDTO` | 函数数据 |
| `this` | `boolean` | 启用模板生成 |
| `else` | `String` | 路径 |
| `char` | `boolean` | 测试私有方法 |
| `int` | `List<Method>` | 模板方法列表 |
| `new` | `List<String>` | 排除方法列表 |
| `long` | `PsiFile` | PSI 文件 |
| `super` | `UnitTestBaseEnum` | 测试框架 |
| `for` | `boolean` | 方法级单测 |
| `if` | `String` | 模块路径 |
| `case` | `UnitTestMockEnum` | Mock 框架 |
| `final` | `List<CaseResult>` | 测试用例结果 |
| `try` | `UnitTestDto$DataDTO` | 单测 DTO |
| `float` | `PsiClass` | PSI 类 |
| `byte` | `List<PsiMethod>` | PSI 方法列表 |
| `enum` | `String` | 测试目录路径 |

**方法**: 完整的 getter/setter 对（省略，共 25+ 方法）

**关联 Service**: 无直接 Service 调用，纯数据对象

**逻辑推断**: 方法级单测生成器配置 DTO，比 `GeneratorConfig` 更细粒度，包含 PSI 级别的方法信息和模板数据。

---

### 4.8 CoverageCompileStatusNotification

- **包**: `com.aicode.action.batch`
- **源文件**: `qe` (混淆)
- **签名**: `public class CoverageCompileStatusNotification implements CompileStatusNotification, CompilationStatusListener`
- **内部类**: `CoverageCompileStatusNotification$aa extends ProcessAdapter`

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `char` | `final GeneratorConfig` | 生成器配置 |
| `int` | `final Project` | 项目 |
| `new` | `final Boolean` | 布尔标志 |
| `long` | `final ToolWindow` | 工具窗口 |
| `for` | `final Boolean` | 布尔标志 |
| `if` | `final CompilerManager` | 编译管理器 |
| `final` | `final Integer` | 整数参数 |
| `float` | `final Boolean` | 布尔标志 |
| `byte` | `final List<VirtualFile>` | 虚拟文件列表 |
| `enum` | `static final Logger` | 日志器 |
| `else` | `static final String` | 常量 |
| `super` | `static final String` | 常量 |
| `case` | `static final String` | 常量 |
| `try` | `static final String` | 常量 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `CoverageCompileStatusNotification(ToolWindow, CompilerManager, Boolean, Project, GeneratorConfig, Integer)` | 构造器 | |
| `finished(boolean, int, int, CompileContext)` | `void` | 编译完成回调 |
| `compilationFinished(boolean, int, int, CompileContext)` | `void` | 编译完成回调 (接口方法) |
| `openWindow(Project, String, boolean)` | `static final void` | 打开窗口 |
| `setActivateViewOnRun(Project, boolean)` | `static void` | 设置运行时激活视图 |
| `hf(CompilerMessage[], CompileContext)` | `private void` | 处理编译消息 |
| `Be()` | `private void` | 内部处理 |
| `ud()` | `private void` | 内部处理 |
| `jd()` | `private void` | 内部处理 |
| `hD()` | `private void` | 内部处理 |
| `fF(Application)` | `private void` | 内部处理 |
| `wC(Project, String, boolean, Module)` | `private static void` | 创建运行配置 |
| `LD(JUnitConfiguration, String, Module)` | `private static void` | 配置 JUnit |
| `AD(JUnitConfiguration, String, Module)` | `private static void` | 配置 JUnit |
| `HD(Project, RunContentDescriptor)` | `private static void` | 处理运行描述符 |
| `Qf(Project, String, boolean)` | `private static void` | 执行测试 |
| `Xf(Project, String, boolean)` | `private static void` | 执行测试 |

**内部类 aa**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `enum` | `final Project` | 项目 |

| 方法 | 说明 |
|------|------|
| `processTerminated(ProcessEvent)` | 进程终止回调 |

**关联 Service**:
- `TemplateGenerator.batchTestClass(Project, GeneratorConfig)` -> 批量生成测试类
- `ProcessErrorFileAnalyzer` -> 错误文件分析器
- `TestGenerationProcess.GENERATION_BUILD_EXECUTE` -> 生成过程枚举
- `GeneratorConfig` -> 配置对象操作
- `GenericUtils.H()` (字符串解密)

**逻辑推断**: 编译状态监听器。编译完成后，根据 `GeneratorConfig` 中的 `TestGenerationProcess` 状态决定下一步: 如果是 `GENERATION_BUILD_EXECUTE`，则调用 `TemplateGenerator.batchTestClass` 生成测试代码；否则执行测试运行。

---

### 4.9 ExcludeMethodConfigurable

- **包**: `com.aicode.action.batch`
- **源文件**: `gi` (混淆)
- **签名**: `public final class ExcludeMethodConfigurable extends JPanel implements ConfigurableUi<NodeRendererSettings>, Disposable`

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `float` | `final JBTable` | 方法表格 |
| `byte` | `final ElementsChooser<NodeRenderer>` | 元素选择器 |
| `enum` | `NodeRenderer` | 当前选中渲染器 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `getComponent()` | `JComponent` | 获取 UI 组件 |
| `reset(NodeRendererSettings)` | `void` | 重置配置 |
| `apply(NodeRendererSettings)` | `void` | 应用配置 |
| `isModified(NodeRendererSettings)` | `boolean` | 是否修改 |
| `getBody()` | `ArrayList<String>` | 获取排除方法列表 |
| `dispose()` | `void` | 释放资源 |
| `toArray(Map<?, ?>)` | `static Object[][]` | Map 转数组 |
| `vf(NodeRenderer)` | `private void` | 内部处理 |
| `bE(ArrayList, NodeRenderer)` | `private boolean` | 内部检查 |
| `Ef(RendererConfiguration)` | `private void` | 内部处理 |
| `Bf(int)` | `static Object[][]` | 创建数组 |
| `wD(ListSelectionEvent)` | `private void` | 选择事件 |
| `Ae(List<NodeRenderer>)` | `private void` | 内部处理 |
| `wE(JBTable)` | `private ArrayList<String>` | 获取表格数据 |
| `ff()` | `private void` | 内部处理 |
| `DF(AnActionButton)` | `private void` | 添加按钮处理 |
| `wd(AnActionButton)` | `private void` | 删除按钮处理 |
| `WE(Map$Entry)` | `static Object[]` | Map 条目转数组 |

**关联 Service**: 无直接 Service 调用，纯 UI 组件

**逻辑推断**: 排除方法配置面板。在批量单测对话框中，允许用户选择要排除的方法（如 getter/setter/toString 等）。

---

## 5. UI 操作组

### 5.1 OpenWindowAction

- **包**: `com.aicode.action`
- **源文件**: `yk` (混淆)
- **签名**: `public class OpenWindowAction extends PluginAnAction`

**字段**: 无实例字段

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `<init>()` | 构造器 | |
| `actionPerformed(AnActionEvent)` | `void` | 打开主窗口 |
| `update(AnActionEvent)` | `void` | 更新可用性 |
| `isDumbAware()` | `boolean` | 返回 true |
| `yf(AnActionEvent)` | `private static void` | 内部处理 |

**关联 Service**:
- `CommonService.openPage(Project, PageEnum.CHAT_VIEW)` -> 打开聊天页面
- `AICodeStringUtil.H()` / `InlineChatStatusServiceKt.H()` (字符串解密)
- `BasicActionsBundle.message(String, Object[])` -> 国际化消息

**逻辑推断**: 打开 iFlyCode 主聊天窗口，导航到 `CHAT_VIEW` 页面。

---

### 5.2 PluginSettingAction

- **包**: `com.aicode.action`
- **源文件**: `jh` (混淆)
- **签名**: `public class PluginSettingAction extends PluginAnAction`

**字段**: 无实例字段

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `<init>()` | 构造器 | |
| `actionPerformed(AnActionEvent)` | `void` | 打开设置页面 |
| `update(AnActionEvent)` | `void` | 更新可用性 |

**关联 Service**:
- `PluginStartupActivity.getApiKey()` -> 登录检查
- `CommonService.openPage(Project, PageEnum.SETTING_PAGE)` -> 打开设置页面
- `FontKt.H()` / `JComponentKt.H()` (字符串解密)
- `MessageBundle.get(String)` -> 国际化消息

**逻辑推断**: 打开插件设置页面，导航到 `SETTING_PAGE`。

---

### 5.3 LogoutAction

- **包**: `com.aicode.action`
- **源文件**: `vh` (混淆)
- **签名**: `public class LogoutAction extends PluginAnAction`

**字段**: 无实例字段

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `<init>()` | 构造器 | |
| `actionPerformed(AnActionEvent)` | `void` | 执行登出 |
| `update(AnActionEvent)` | `void` | 更新可用性 |
| `E()` | `private static String` | 获取标识 |
| `Gf(AnActionEvent)` | `private static void` | 内部处理 |

**关联 Service**:
- `PluginStartupActivity.getApiKey()` -> 登录状态检查
- `UserService.logout(Project)` -> 执行登出
- `PluginWebsocketClient.sendWsMessage(CommandEnum, Project)` -> 发送 `USER_LOGIN` 命令
- `CommandEnum.USER_LOGIN` -> 登录命令枚举
- `StringUtils.isBlank(CharSequence)` -> 字符串检查
- `BasicActionsBundle.message(String, Object[])` -> 国际化消息
- `OpenTelemetryUtil.H()` / `RequestCancelException.H()` (字符串解密)

**逻辑推断**: 用户登出。发送 `USER_LOGIN` WebSocket 消息通知后端，调用 `UserService.logout` 清理本地状态。

---

### 5.4 UserInfoAction

- **包**: `com.aicode.action`
- **源文件**: `am` (混淆)
- **签名**: `public class UserInfoAction extends PluginAnAction`

**字段**: 无实例字段

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `<init>()` | 构造器 | |
| `actionPerformed(AnActionEvent)` | `void` | 显示用户信息 |
| `update(AnActionEvent)` | `void` | 更新可用性 |

**关联 Service**:
- `PluginStartupActivity.getApiKey()` -> 登录状态检查
- `AICodeSettingsState.getInstance()` -> `userName` 用户名
- `Maps.H()` / `PositionUtil.H()` (字符串解密)

**逻辑推断**: 显示当前用户信息，读取 `AICodeSettingsState.userName`。

---

### 5.5 TerminalAction

- **包**: `com.aicode.action.click`
- **源文件**: `uj` (混淆)
- **签名**: `public class TerminalAction extends PluginAnAction`

**字段**: 无实例字段

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `TerminalAction(String, String, Icon)` | 构造器 | |
| `actionPerformed(AnActionEvent)` | `void` | 触发终端调试 |
| `update(AnActionEvent)` | `void` | 更新可用性 |

**关联 Service**:
- `AICodeSettingsState.getInstance()` -> `permissions` 权限集合
- `PermissionEnum.CODE_DEBUG` -> 代码调试权限
- `Presentation.handleDebug(String, String, boolean, boolean)` -> 处理调试
- `ChatInputController.H()` (字符串解密)
- `MethodGeneratorConfig.H()` (字符串解密)
- `StringUtils.isBlank(CharSequence)` -> 字符串检查

**逻辑推断**: 终端/调试功能入口。检查 `CODE_DEBUG` 权限后，调用 `Presentation.handleDebug` 启动调试流程。

---

### 5.6 RefreshAction

- **包**: `com.aicode.action`
- **源文件**: `tg` (混淆)
- **签名**: `public class RefreshAction extends PluginAnAction`

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `REFRESH_MAP` | `static ConcurrentNavigableMap<String, Boolean>` | 刷新状态映射 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `<init>()` | 构造器 | |
| `actionPerformed(AnActionEvent)` | `void` | 执行刷新 |
| `update(AnActionEvent)` | `void` | 更新可用性 |

**关联 Service**:
- `RestartableAgentProcessService.refreshTimes` -> 刷新次数计数器
- `ChatService.refreshAgent(Project, boolean)` -> 刷新 Agent 进程
- `AICodeUtils.H()` / `GenericUtils.H()` (字符串解密)

**逻辑推断**: 刷新 AI Agent 进程。调用 `ChatService.refreshAgent` 重启 Agent，通过 `REFRESH_MAP` 跟踪刷新状态。

---

### 5.7 TipPromoterAction

- **包**: `com.aicode.action`
- **源文件**: `mo` (混淆)
- **签名**: `public class TipPromoterAction implements ActionPromoter`

**字段**: 无实例字段

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `<init>()` | 构造器 | |
| `promote(List<? extends AnAction>, DataContext)` | `List<AnAction>` | 提升 Action 优先级 |

**关联 Service**:
- `FileExtensionLanguageDetails.H()` (字符串解密)
- `IndentLineUtil.H()` (字符串解密)

**逻辑推断**: Action 优先级提升器。实现 IntelliJ `ActionPromoter` 接口，调整代码补全相关 Action 的执行优先级，确保 iFlyCode 的补全 Action 优先于其他插件。

---

## 6. 一键修复组

### 6.1 CodeProblemsIntentionAction

- **包**: `com.aicode.action`
- **源文件**: `cj` (混淆)
- **签名**: `public class CodeProblemsIntentionAction extends BaseIntentionAction implements Iconable`

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `enum` | `static Logger` | 日志器 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `<init>()` | 构造器 | |
| `getFamilyName()` | `String` | 返回意图族名 |
| `getText()` | `String` | 返回显示文本 |
| `isAvailable(Project, Editor, PsiFile)` | `boolean` | 检查是否可用 |
| `invoke(Project, Editor, PsiFile)` | `void` | 执行一键修复 |
| `getIcon(int)` | `Icon` | 获取图标 |
| `getHighlights(Document, HighlightSeverity, Project)` | `static List<HighlightInfo>` | 获取高亮问题列表 |
| `fd(Project, PsiElement, Editor)` | `private boolean` | 内部检查 |
| `fD(Editor)` | `private TextRange` | 获取文本范围 |
| `GF(Project, Editor, TextRange)` | `private List<String>` | 获取问题代码行 |
| `CF(List, int)` | `private static String` | 格式化代码行 |

**关联 Service**:
- `PluginStartupActivity.getApiKey()` -> 登录检查
- `AICodeSettingsState.getInstance()` -> `enableCodeDebug` 调试开关
- `Presentation.handleDebug(Project, String, String, String, int)` -> 处理调试/修复
- `RequestResultList.H()` (字符串解密)
- `Application.H()` (字符串解密)
- `BasicActionsBundle.message(String, Object[])` -> 国际化消息
- `Icons.StatusBarIcon` -> 状态栏图标

**逻辑推断**: 代码问题一键修复意图 Action。在编辑器中出现代码问题时，Alt+Enter 弹出意图菜单中显示。`isAvailable` 检查 `enableCodeDebug` 设置和当前行是否有问题。`invoke` 收集问题代码，调用 `Presentation.handleDebug` 发送到 AI 修复。

---

### 6.2 CodeProblemsTreePopupAction

- **包**: `com.aicode.action`
- **源文件**: `ik` (混淆)
- **签名**: `public class CodeProblemsTreePopupAction extends PluginAnAction`

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `enum` | `static Logger` | 日志器 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `<init>()` | 构造器 | |
| `actionPerformed(AnActionEvent)` | `void` | 触发问题树弹窗 |
| `update(AnActionEvent)` | `void` | 更新可用性 |
| `xf(AnActionEvent, Object)` | `private void` | 内部处理 |
| `nE(Object)` | `private String` | 获取名称 |

**关联 Service**:
- `Presentation.handleDebug(Project, String, String, String, int)` -> 处理调试/修复
- `CommonService.messageBus(Project, String, MessageType)` -> 显示消息通知
- `BasicActionsBundle.message(String, Object[])` -> 国际化消息
- `IndentLineUtil.H()` / `GenericUtils.H()` (字符串解密)
- `ReflectUtil.getObjField(Object, String)` -> 反射获取字段

**逻辑推断**: 代码问题树弹窗 Action。在问题树中选择问题后，触发一键修复。使用反射获取问题详情，调用 `Presentation.handleDebug` 发送修复请求。

---

### 6.3 CodePromoterAction

- **包**: `com.aicode.action`
- **源文件**: `tk` (混淆)
- **签名**: `public class CodePromoterAction implements ActionPromoter`

**字段**: 无实例字段

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `<init>()` | 构造器 | |
| `promote(List<? extends AnAction>, DataContext)` | `List<AnAction>` | 提升 Action 优先级 |
| `isIdeaVimAction(AnAction)` | `static boolean` | 检查是否 IdeaVim Action |
| `hd(Editor)` | `private boolean` | 内部检查 |
| `ke(AnAction, AnAction)` | `private static int` | Action 比较 |
| `ee(AnAction, AnAction)` | `private static int` | Action 比较 |
| `kf(AnAction)` | `private static boolean` | 内部检查 |

**关联 Service**:
- `AcceptInlaysAction.isSupported(Editor)` -> 检查 inlay 支持
- `AcceptWordInlaysAction.isSupported(Editor)` -> 检查单词级 inlay 支持
- `RequestResultList.H()` (字符串解密)
- `GenericUtils.H()` (字符串解密)
- `CodeAction` 接口 -> instanceof 检查

**逻辑推断**: 代码补全 Action 优先级提升器。当编辑器有 inlay 提示时，提升 `AcceptInlaysAction` 和 `AcceptWordInlaysAction` 的优先级，确保 Tab 键优先触发代码补全接受而非其他 Action。特别处理 IdeaVim 插件冲突。

---

## 7. 树形组件组

### 7.1 AbstractNode

- **包**: `com.aicode.action.batch.node`
- **源文件**: `wf` (混淆)
- **签名**: `public abstract class AbstractNode extends DefaultMutableTreeNode`

**字段**: 无实例字段

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `AbstractNode()` | 构造器 | |
| `render(TreeCellRenderer)` | `abstract void` | 渲染节点（抽象方法） |
| `getAllFileNodeChildCount()` | `int` | 获取所有子文件节点数 |
| `spaceAndThinSpace()` | `static String` | 返回空格和细空格字符 |

**关联 Service**: 无

**逻辑推断**: 树节点抽象基类，定义 `render` 抽象方法供子类实现不同的渲染逻辑。

---

### 7.2 TreeRootNode

- **包**: `com.aicode.action.batch.node`
- **源文件**: `kj` (混淆)
- **签名**: `public class TreeRootNode extends AbstractNode`

**字段**: 无实例字段

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `TreeRootNode(String, List<FileNode>)` | 构造器 | 传入根名称和文件节点列表 |
| `render(TreeCellRenderer)` | `void` | 渲染根节点 |

**关联 Service**: 无

**逻辑推断**: 树根节点，包含项目名称和下属 `FileNode` 列表。

---

### 7.3 FileNode

- **包**: `com.aicode.action.batch.node`
- **源文件**: `ad` (混淆)
- **签名**: `public class FileNode extends AbstractNode`

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `byte` | `final String` | 文件名 |
| `enum` | `static final Map<String, FileNode>` | 文件名到节点映射缓存 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `FileNode(String)` | 构造器 | |
| `getFileName()` | `String` | 获取文件名 |
| `getFileNode(String)` | `static FileNode` | 从缓存获取节点 |
| `setFileNode(FileNode)` | `void` | 设置节点 |
| `clear()` | `static void` | 清除缓存 |
| `render(TreeCellRenderer)` | `void` | 渲染文件节点 |

**关联 Service**: 无

**逻辑推断**: 文件节点，表示一个待生成单测的源文件。使用静态 Map 缓存避免重复创建。

---

### 7.4 CheckboxTreeCellRenderer

- **包**: `com.aicode.action.batch.node`
- **源文件**: `fd` (混淆)
- **签名**: `public class CheckboxTreeCellRenderer implements TreeCellRenderer`
- **内部类**:
  - `CheckboxTreeCellRenderer$CheckedNode` - 带复选框的节点数据
  - `CheckboxTreeCellRenderer$Ea extends MouseAdapter` - 鼠标点击监听器

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `try` | `JLabel` | 标签 |
| `float` | `JCheckBox` | 复选框 |
| `byte` | `JLabel` | 图标标签 |
| `enum` | `JPanel` | 面板 |

**内部类 CheckedNode**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `float` | `String` | 文本 |
| `byte` | `boolean` | 是否选中 |
| `enum` | `Icon` | 图标 |

**内部类 Ea**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `enum` | `final JTree` | 树组件 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `CheckboxTreeCellRenderer()` | 构造器 | |
| `getTreeCellRendererComponent(JTree, Object, boolean, boolean, boolean, int, boolean)` | `Component` | 渲染树单元格 |
| `createTreeNodes()` | `static DefaultMutableTreeNode` | 创建示例树节点 |
| `ge(DefaultMutableTreeNode)` | `private boolean` | 内部检查 |
| `main(String[])` | `static void` | 测试入口 |

**关联 Service**: 无

**逻辑推断**: 带复选框的树单元格渲染器，用于批量单测对话框中的文件选择树。`Ea` 内部类处理鼠标点击切换复选框状态。

---

### 7.5 ResultTree

- **包**: `com.aicode.action.batch`
- **源文件**: `gh` (混淆)
- **签名**: `public class ResultTree extends Tree`

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `DEFAULT_ROW_HEIGHT` | `static final int` | 默认行高 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `ResultTree()` | 构造器 | |
| `ResultTree(TreeNode)` | 构造器 | |
| `createModel(String, List<FileNode>)` | `static DefaultTreeModel` | 创建树模型 |

**关联 Service**: 无

**逻辑推断**: 结果展示树组件，继承 IntelliJ `Tree`，用于展示批量单测生成结果。

---

### 7.6 TreeCellRenderer

- **包**: `com.aicode.action.batch`
- **源文件**: `af` (混淆)
- **签名**: `public class TreeCellRenderer extends ColoredTreeCellRenderer`

**字段**: 无实例字段

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `TreeCellRenderer()` | 构造器 | |
| `customizeCellRenderer(JTree, Object, boolean, boolean, boolean, int, boolean)` | `void` | 自定义渲染 |

**关联 Service**: 无

**逻辑推断**: 彩色树单元格渲染器，用于结果树中的节点渲染，被 `AbstractNode.render()` 调用。

---

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
| `PluginAnAction(Supplier<String>, Icon)` | 构造器 | |
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
