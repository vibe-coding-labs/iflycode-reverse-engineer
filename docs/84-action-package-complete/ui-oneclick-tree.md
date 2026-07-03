## 5. UI 操作组

### 5.1 OpenWindowAction

- **包**: `com.aicode.action`
- **源文件**: `yk` (混淆)
- **签名**: `public class OpenWindowAction extends PluginAnAction`

**字段**: 无实例字段

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `&lt;init&gt;()` | 构造器 | |
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
| `&lt;init&gt;()` | 构造器 | |
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
| `&lt;init&gt;()` | 构造器 | |
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
| `&lt;init&gt;()` | 构造器 | |
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
| `&lt;init&gt;()` | 构造器 | |
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
| `&lt;init&gt;()` | 构造器 | |
| `promote(List<? extends AnAction>, DataContext)` | `List&lt;AnAction&gt;` | 提升 Action 优先级 |

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
| `&lt;init&gt;()` | 构造器 | |
| `getFamilyName()` | `String` | 返回意图族名 |
| `getText()` | `String` | 返回显示文本 |
| `isAvailable(Project, Editor, PsiFile)` | `boolean` | 检查是否可用 |
| `invoke(Project, Editor, PsiFile)` | `void` | 执行一键修复 |
| `getIcon(int)` | `Icon` | 获取图标 |
| `getHighlights(Document, HighlightSeverity, Project)` | `static List&lt;HighlightInfo&gt;` | 获取高亮问题列表 |
| `fd(Project, PsiElement, Editor)` | `private boolean` | 内部检查 |
| `fD(Editor)` | `private TextRange` | 获取文本范围 |
| `GF(Project, Editor, TextRange)` | `private List&lt;String&gt;` | 获取问题代码行 |
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
| `&lt;init&gt;()` | 构造器 | |
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
| `&lt;init&gt;()` | 构造器 | |
| `promote(List<? extends AnAction>, DataContext)` | `List&lt;AnAction&gt;` | 提升 Action 优先级 |
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
| `TreeRootNode(String, List&lt;FileNode&gt;)` | 构造器 | 传入根名称和文件节点列表 |
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
| `createModel(String, List&lt;FileNode&gt;)` | `static DefaultTreeModel` | 创建树模型 |

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
