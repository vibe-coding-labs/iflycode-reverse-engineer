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
| `enum` | `static final List&lt;String&gt;` | 支持的文件扩展名列表 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `PrepushReviewAction(String, String)` | 构造器 | |
| `actionPerformed(AnActionEvent)` | `void` | 触发预推送审查 |
| `update(AnActionEvent)` | `void` | 更新可用性 |
| `getSelectedChange(AnActionEvent)` | `List&lt;Change&gt;` | 获取选中的 VCS 变更 |
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
| `getChanges(AnActionEvent)` | `List&lt;Change&gt;` | 获取 VCS 变更列表 |
| `SD(List&lt;Change&gt;)` | `private Map<String, LinkedHashSet&lt;String&gt;>` | 按文件分组变更 |
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
| `cE(Project, VirtualFile[], List&lt;FileNode&gt;)` | `private Module` | 查找对应模块 |
| `Hf(Project, String)` | `private void` | 内部处理 |
| `EF(Project, VirtualFile[], Module, List&lt;FileNode&gt;)` | `private void` | 执行批量生成 |

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
| `final` | `List&lt;FileNode&gt;` | 文件节点列表 |
| `throw` | `List&lt;String&gt;` | 排除方法列表 |
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
| `BatchUnitTestDialog(Project, Module, String, String, String, List&lt;FileNode&gt;, List&lt;String&gt;, String)` | 构造器 | |
| `createCenterPanel()` | `JComponent` | 创建对话框中心面板 |
| `doOKAction()` | `void` | 确认按钮回调 |
| `getSelectedValue(GeneratorConfig)` | `GeneratorConfig` | 获取用户选择的配置 |
| `setDuplicateFileNameSwitch(DuplicateRule)` | `void` | 设置重复文件名规则 |
| `setGenerateByTemplateSwitch(GenaratebyTemplateSwitchEnum)` | `void` | 设置模板开关 |
| `changeGenerateByTemplateComponent(String)` | `void` | 切换模板组件 |
| `createUnitTestTreeView(List&lt;FileNode&gt;)` | `JPanel` | 创建文件树视图 |
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
| `byte` | `static AtomicReference&lt;BatchUnitTestDialog&gt;` | 对话框引用 |
| `enum` | `static String` | 状态字符串 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `createUnitTestDialog(Project, Module, String, List&lt;FileNode&gt;, List&lt;String&gt;, StringBuilder)` | `static BatchUnitTestDialog` | 创建单测对话框 |
| `recursion(VirtualFile, Project, Set&lt;VirtualFile&gt;)` | `static void` | 递归查找文件 |
| `queryServerResource(Project)` | `static void` | 查询服务端资源 |
| `getVirtualFile(Project, List&lt;VirtualFile&gt;)` | `static List&lt;VirtualFile&gt;` | 获取虚拟文件列表 |
| `doCompile(Project, boolean, GeneratorConfig)` | `static void` | 执行编译 |
| `handleClassFiles(Project, List&lt;VirtualFile&gt;, List&lt;FileNode&gt;, List&lt;String&gt;, GeneratorConfig, String, Module, StringBuilder)` | `static void` | 处理 class 文件 |
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
| `true` | `List&lt;String&gt;` | 生成文件路径列表 |
| `testFileName` | `String` | 测试文件名 |
| `this` | `List&lt;String&gt;` | 文件绝对路径列表 |
| `else` | `List&lt;String&gt;` | 排除方法列表 |
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
| `int` | `List&lt;Method&gt;` | 模板方法列表 |
| `new` | `List&lt;String&gt;` | 排除方法列表 |
| `long` | `PsiFile` | PSI 文件 |
| `super` | `UnitTestBaseEnum` | 测试框架 |
| `for` | `boolean` | 方法级单测 |
| `if` | `String` | 模块路径 |
| `case` | `UnitTestMockEnum` | Mock 框架 |
| `final` | `List&lt;CaseResult&gt;` | 测试用例结果 |
| `try` | `UnitTestDto$DataDTO` | 单测 DTO |
| `float` | `PsiClass` | PSI 类 |
| `byte` | `List&lt;PsiMethod&gt;` | PSI 方法列表 |
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
| `byte` | `final List&lt;VirtualFile&gt;` | 虚拟文件列表 |
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
- **签名**: `public final class ExcludeMethodConfigurable extends JPanel implements ConfigurableUi&lt;NodeRendererSettings&gt;, Disposable`

**字段**:
| 字段 | 类型 | 说明 |
|------|------|------|
| `float` | `final JBTable` | 方法表格 |
| `byte` | `final ElementsChooser&lt;NodeRenderer&gt;` | 元素选择器 |
| `enum` | `NodeRenderer` | 当前选中渲染器 |

**方法**:
| 方法 | 签名 | 说明 |
|------|------|------|
| `getComponent()` | `JComponent` | 获取 UI 组件 |
| `reset(NodeRendererSettings)` | `void` | 重置配置 |
| `apply(NodeRendererSettings)` | `void` | 应用配置 |
| `isModified(NodeRendererSettings)` | `boolean` | 是否修改 |
| `getBody()` | `ArrayList&lt;String&gt;` | 获取排除方法列表 |
| `dispose()` | `void` | 释放资源 |
| `toArray(Map<?, ?>)` | `static Object[][]` | Map 转数组 |
| `vf(NodeRenderer)` | `private void` | 内部处理 |
| `bE(ArrayList, NodeRenderer)` | `private boolean` | 内部检查 |
| `Ef(RendererConfiguration)` | `private void` | 内部处理 |
| `Bf(int)` | `static Object[][]` | 创建数组 |
| `wD(ListSelectionEvent)` | `private void` | 选择事件 |
| `Ae(List&lt;NodeRenderer&gt;)` | `private void` | 内部处理 |
| `wE(JBTable)` | `private ArrayList&lt;String&gt;` | 获取表格数据 |
| `ff()` | `private void` | 内部处理 |
| `DF(AnActionButton)` | `private void` | 添加按钮处理 |
| `wd(AnActionButton)` | `private void` | 删除按钮处理 |
| `WE(Map$Entry)` | `static Object[]` | Map 条目转数组 |

**关联 Service**: 无直接 Service 调用，纯 UI 组件

**逻辑推断**: 排除方法配置面板。在批量单测对话框中，允许用户选择要排除的方法（如 getter/setter/toString 等）。

---
