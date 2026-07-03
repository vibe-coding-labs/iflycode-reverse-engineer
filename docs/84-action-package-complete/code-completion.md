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
| `&lt;init&gt;()` | 构造器 | 创建内部 `pa` handler, 设置 injectedContext |
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
| `&lt;init&gt;()` | 构造器 | 创建内部 `va` handler |
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
| `&lt;init&gt;()` | 构造器 | 创建内部 `wa` handler |
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
| `&lt;init&gt;()` | 构造器 | 创建默认 handler |

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
| `&lt;init&gt;()` | 构造器 | |
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
| `&lt;init&gt;()` | 构造器 | |
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
| `&lt;init&gt;()` | 构造器 | |
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
| `&lt;init&gt;()` | 构造器 | |
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
