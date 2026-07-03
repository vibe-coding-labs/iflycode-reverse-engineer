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
