## 3. CommonService — 通用服务

**类签名:** `public class com.aicode.agent.service.CommonService`
**源文件:** `fj` (混淆后)
**包路径:** `com.aicode.agent.service`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `private static final` | `org.slf4j.Logger` | `byte` | 日志器(混淆名) |
| `public static final` | `boolean` | `enum` | 混淆开关标志 |

### 方法

#### public 方法 (外部API) — 28个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static logOperate(String, String, Project)` | 记录操作日志 |
| `void` | `static refreshDocumentStruct(Project)` | 刷新文档结构 |
| `void` | `static handleChatFocusFileLine(Project, JsonObject)` | 处理聊天聚焦文件行 |
| `String` | `static addLineIndent(String, String)` | 添加行缩进 |
| `void` | `static copyCode(Project, String)` | 复制代码 |
| `void` | `static handleAction(WebViewDataTypeEnum, JsonObject, String, Project)` | 处理WebView动作 |
| `boolean` | `static isSupportJava(Editor)` | 判断是否支持Java |
| `void` | `static updateConfig(JsonObject, Project)` | 更新配置 |
| `void` | `static openFileDialog(Project, JsonObject)` | 打开文件对话框 |
| `synchronized void` | `static refreshFunctionAction(Project, MessageDto, JsonObject)` | 刷新功能动作(同步) |
| `void` | `static insertLineComment(Project, String, String, List<CodeInfoDto$RangeDTO>)` | 插入行注释 |
| `void` | `static insertCode(Project, String)` | 插入代码 |
| `void` | `static openPage(Project, PageEnum)` | 打开页面 |
| `void` | `static messageBus(Project, String, MessageType)` | 消息总线通知 |
| `void` | `static handleChatFocusFile(Project, JsonObject)` | 处理聊天聚焦文件 |
| `void` | `static diffCode(Project, RequestCaseCodeDto$ValueDTO)` | Diff代码 |
| `void` | `static jumpToFileByIndex(Project, String, Integer, Integer, boolean)` | 跳转到文件索引 |
| `void` | `static openFile(Project, String)` | 打开文件 |
| `void` | `static chatMessage2Web(Project, FirstChatMessage, Boolean)` | 聊天消息发送到Web |
| `void` | `static handleChatFeedback(String, Project)` | 处理聊天反馈 |
| `int[]` | `static getOffsets(Document, int, int, int, int)` | 获取偏移量 |
| `void` | `static popupKeymapSettings(Project)` | 弹出快捷键设置 |
| `void` | `static handleEval(JsonObject, Project)` | 处理评估 |
| `void` | `static genCodeFile(Project, String, String)` | 生成代码文件 |
| `void` | `static saveShowOperateGuidance(Project)` | 保存操作指引 |
| `JsonObject` | `static getConfig()` | 获取配置 |
| `void` | `static openUrl(String, Project)` | 打开URL |
| `void` | `static clearHighLight(MarkupModel, RangeHighlighter[])` | 清除高亮 |
| `void` | `static handleComment(Project, RequestCaseCodeDto$ValueDTO, ChatOperationEnum, String, String, RequestCaseCodeDto)` | 处理评论 |
| `void` | `static getPluginInfo(Project)` | 获取插件信息 |
| `void` | `static handleClick(Project, String)` | 处理点击 |

#### private 方法 (内部实现) — 27个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `boolean` | `static cD(Project, String)` | 内部判断 |
| `void` | `static ce(Application, String, String, Project, String)` | 内部处理 |
| `void` | `static xD(Project, String)` | 内部处理 |
| `SettingsDto` | `static ED(AICodeSettingsState)` | 转换设置DTO |
| `void` | `static uE(Editor, EditorGutterComponentEx, int)` | 内部处理 |
| `void` | `static kD(String, Project, Integer, Integer, boolean)` | 内部处理 |
| `String` | `static sd(AICodeSettingsState)` | 内部处理 |
| `void` | `static SE(PresentationDataDto, List&lt;CommandEnum&gt;)` | 内部处理 |
| `void` | `static re(Project, RequestCaseCodeDto$ValueDTO)` | 内部处理 |
| `boolean` | `static Le(Editor, int, int)` | 内部判断 |
| `void` | `static cf(String, Project, Integer)` | 内部处理 |
| `List&lt;PresentationDataDto&gt;` | `static ID(JsonObject, String, Editor)` | 内部处理 |
| `void` | `static je(Project, List, String, String)` | 内部处理 |
| `void` | `static RD(Application, Project, String)` | 内部处理 |
| `void` | `static xd(Application, Project, List, String, String)` | 内部处理 |
| `void` | `static enum(int)` | 混淆开关方法 |
| `void` | `static Gd(Project)` | 内部处理 |
| `void` | `static XC(Project, String)` | 内部处理 |
| `void` | `static IE(Editor)` | 内部处理 |
| `void` | `static lD(Application, Project, String)` | 内部处理 |
| `void` | `static cF(Document, String, List&lt;PresentationDataDto&gt;, Type, JsonElement, String, String)` | 内部处理 |
| `String` | `static Ge(String, int)` | 内部处理 |
| `void` | `static BE(Project, Boolean, Map)` | 内部处理 |
| `void` | `static GD(Project, String)` | 内部处理 |
| `void` | `static KF(Project)` | 内部处理 |
| `boolean` | `static WD(List&lt;RangeHighlighter&gt;, PresentationDataDto, Set&lt;String&gt;)` | 内部判断 |
| `void` | `static KE(Project, FirstChatMessage)` | 内部处理 |
| `void` | `static Qe(List, String, Project, String)` | 内部处理 |
| `void` | `static tC(String, String, Project, String)` | 内部处理 |
| `void` | `static aF(Project, JsonObject)` | 内部处理 |
| `void` | `static te(Project, String, JsonObject)` | 内部处理 |
| `void` | `static Qd(Project, String)` | 内部处理 |
| `String` | `static WC(AICodeSettingsState, String)` | 内部处理 |
| `void` | `static qf(String)` | 内部处理 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public CommonService()` | 公有构造器 |

### 内部类

#### CommonService$Fa — 鼠标移动监听器
```java
public class com.aicode.agent.service.CommonService$Fa extends java.awt.event.MouseMotionAdapter &#123;
  public final EditorGutterComponentEx float;
  public final Editor byte;
  public final int enum;
  public void mouseMoved(MouseEvent);
&#125;
```
编辑器行号区域的鼠标移动监听器，用于处理行号区域的悬停交互。

#### CommonService$Ha — 混淆开关表
```java
public class com.aicode.agent.service.CommonService$Ha &#123;
  public static final int[] byte;
  public static final int[] enum;
&#125;
```

#### CommonService$Ma — TypeToken子类
```java
public class com.aicode.agent.service.CommonService$Ma
    extends TypeToken<List<CodeInfoDto$RangeDTO>> &#123;&#125;
```
Gson反序列化用的类型标记子类。

---

## 4. InlineChatService — 内联聊天服务

**类签名:** `public class com.aicode.inline.InlineChatService implements com.intellij.openapi.Disposable`
**源文件:** `gd` (混淆后)
**包路径:** `com.aicode.inline`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `private final` | `InlineChatStatusService` | `byte` | 内联聊天状态服务(混淆名) |
| `private static final` | `Map<String, InlineChatPanel>` | `enum` | 编辑器到面板映射(混淆名) |

### 方法

#### public 方法 (外部API) — 10个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static closeInlineChat(Editor)` | 关闭内联聊天(静态) |
| `void` | `static handleUndoAction(Editor)` | 处理撤销动作 |
| `void` | `closeInlineChat(InlineChatPanel)` | 关闭内联聊天面板 |
| `void` | `static cleanRender(Editor)` | 清理渲染 |
| `InlineChatPanel` | `getInlineChat(Editor)` | 获取内联聊天面板 |
| `void` | `static cleanLastData(Editor)` | 清理上次数据(Editor版) |
| `VirtualFile` | `static getVirtualFile(Editor)` | 获取虚拟文件 |
| `void` | `toggleInlineChat(Editor)` | 切换内联聊天 |
| `void` | `dispose()` | 释放资源(Disposable接口) |
| `void` | `static scrollToLines(Editor, int, boolean)` | 滚动到指定行 |
| `void` | `static cleanLastData(InlineChatInfo)` | 清理上次数据(InlineChatInfo版) |

#### private 方法 (内部实现) — 10个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `Unit` | `E()` | Kotlin内部方法 |
| `void` | `ia(Editor, LastChatQuestionInfo, VirtualFile)` | 内部处理 |
| `void` | `Ec(Editor, LastChatQuestionInfo)` | 内部处理 |
| `KeyStrokeHandler` | `Wa(Editor)` | 获取按键处理器 |
| `Object` | `fA(Editor, LastChatQuestionInfo, VirtualFile)` | 内部处理 |
| `void` | `Ab()` | 内部处理 |
| `void` | `SA(LastChatQuestionInfo, Editor, VirtualFile)` | 内部处理 |
| `void` | `kb(Editor, VirtualFile, int, int, int, int, int)` | 内部处理 |
| `InlineChatService` | `static Sc(InlineChatService, Editor)` | 内部处理 |
| `void` | `static enum(int)` | 混淆开关方法 |
| `void` | `Ga(Editor, VirtualFile, int)` | 内部处理 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public InlineChatService()` | 公有构造器 |

### 内部类

#### InlineChatService$Companion — Kotlin伴生对象
```java
public final class com.aicode.inline.InlineChatService$Companion &#123;
  public static void closeInlineChat(InlineChatPanel);
  public static void removeFlag(Editor);
  public InlineChatPanel getInlineChat(Editor);
  public static void closeInlineChat(Editor);
  public static void openInlineChat(Editor, LastChatQuestionInfo);
  public static void openInlineChat(Editor);
&#125;
```
Kotlin伴生对象，提供静态方法入口：打开/关闭内联聊天、移除标记、获取面板实例。

---

## 5. InlineChatCommandService — 内联聊天命令

**类签名:** `public class com.aicode.agent.service.InlineChatCommandService`
**源文件:** `ll` (混淆后)
**包路径:** `com.aicode.agent.service`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `public static final` | `Key<List<CodeInfoDto$RangeDTO>>` | `RANGE_KEY` | 范围数据Key |
| `public static final` | `Key&lt;Integer&gt;` | `VERSION_KEY` | 版本Key |
| `public static final` | `Key<List<CodeInfoDto$RangeDTO>>` | `BODY_RANGE_KEY` | Body范围Key |

### 方法

#### public 方法 (外部API) — 3个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static handleAgentAction(String, CommandEnum, Project, MessageDto, JsonObject)` | 处理Agent动作(5参数) |
| `void` | `static handleAgentAction(Project, MessageDto, String, CommandEnum)` | 处理Agent动作(4参数) |
| `FirstChatMessage` | `static handleChatScene(MessageDto)` | 处理聊天场景 |

#### private 方法 (内部实现) — 20个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static OD(Project, MessageDto, InlineChatCategoryEnum, SessionController)` | 内部处理 |
| `String` | `static nD(JsonObject, JsonObject)` | 内部处理 |
| `boolean` | `static LF(JsonObject, JsonObject)` | 内部判断 |
| `void` | `static Se(Project, MessageDto)` | 内部处理 |
| `void` | `static dd(MessageDto, CommandEnum, Project, String)` | 内部处理 |
| `void` | `static mE(Editor, Document, Type, JsonObject, List<CodeInfoDto$RangeDTO>, boolean)` | 内部处理 |
| `void` | `static XD(JsonObject, Editor, Document, boolean)` | 内部处理 |
| `void` | `static me(InlineChatInfo, String)` | 内部处理 |
| `void` | `static bf(InlineChatInfo, Project, MessageDto)` | 内部处理 |
| `boolean` | `static zf(MessageDto)` | 内部判断 |
| `boolean` | `static Sd(List<CodeInfoDto$RangeDTO>, Editor, Document)` | 内部判断 |
| `void` | `static Ve(Project, MessageDto, JsonObject)` | 内部处理 |
| `void` | `static lF(MessageDto, JsonObject, String)` | 内部处理 |
| `void` | `static oD(SessionController, Project, MessageDto, InlineChatCategoryEnum)` | 内部处理 |
| `void` | `static UC(MessageDto, Project, JsonObject)` | 内部处理 |
| `void` | `static HF(SessionController)` | 内部处理 |
| `void` | `static YE(Project, FirstChatMessage)` | 内部处理 |
| `void` | `static Ue(MessageDto, Project)` | 内部处理 |
| `void` | `static Ze(SessionController, Editor, InlineChatCategoryEnum)` | 内部处理 |
| `void` | `static enum(int)` | 混淆开关方法 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public InlineChatCommandService()` | 公有构造器 |

### 内部类

#### InlineChatCommandService$fa — TypeToken子类
```java
public class com.aicode.agent.service.InlineChatCommandService$fa
    extends TypeToken<List<CodeInfoDto$RangeDTO>> &#123;&#125;
```

#### InlineChatCommandService$ka — 混淆开关表
```java
public class com.aicode.agent.service.InlineChatCommandService$ka &#123;
  public static final int[] byte;
  public static final int[] enum;
&#125;
```

---

## 6. InlineChatHandleService — 内联聊天处理

**类签名:** `public class com.aicode.inline.InlineChatHandleService`
**源文件:** `oj` (混淆后)
**包路径:** `com.aicode.inline`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `private static` | `TextAttributes` | `byte` | 文本属性(混淆名) |
| `public static volatile` | `boolean` | `HANDING_DATA` | 正在处理数据标志 |
| `private static` | `TextAttributes` | `enum` | 文本属性(混淆名) |
| `public static` | `TextAttributes` | `selectOriginalAttributes` | 选中原始文本属性 |

### 方法

#### public 方法 (外部API) — 5个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static handleErrorData(SessionController, String)` | 处理错误数据 |
| `void` | `static handleData(SessionController, String, MessageDto)` | 处理数据(3参数) |
| `void` | `static handleData(JsonObject, MessageDto)` | 处理数据(2参数) |
| `void` | `static saveDocument(Project, Document)` | 保存文档 |

#### private 方法 (内部实现) — 15个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static kE(SessionController, String, InlineChatCategoryEnum)` | 内部处理 |
| `boolean` | `static Ee(JsonObject, JsonObject)` | 内部判断 |
| `void` | `static Af(int, List, Document, MarkupModel, SessionController, Editor)` | 内部处理 |
| `void` | `static dF(SessionController, String, Editor, int, Document, int, List&lt;String&gt;)` | 内部处理 |
| `void` | `static he(SessionController, String)` | 内部处理 |
| `void` | `static id(Document, int, String, int, List, MarkupModel, Editor, boolean)` | 内部处理 |
| `String` | `static lf(Boolean)` | 内部处理 |
| `void` | `static Sf(Editor, int)` | 内部处理 |
| `TextAttributes` | `static tb(Color)` | 创建文本属性 |
| `boolean` | `static Hd(Editor, String, List<CodeInfoDto$RangeDTO>, Document, List&lt;String&gt;, SessionController)` | 内部判断 |
| `void` | `static aE(SessionController)` | 内部处理 |
| `void` | `static Tc(SessionController)` | 内部处理 |
| `void` | `static OA(Editor, List&lt;DiffRow&gt;, Document, int, SessionController)` | Diff处理 |
| `void` | `static Tf(Editor, String, int, Document, int, List&lt;String&gt;, boolean)` | 内部处理 |
| `String` | `static sE(String)` | 内部处理 |
| `String` | `static UE(JsonObject, JsonObject)` | 内部处理 |
| `String` | `static eD(Boolean)` | 内部处理 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public InlineChatHandleService()` | 公有构造器 |

### 内部类

#### InlineChatHandleService$z — 混淆开关表
```java
public class com.aicode.inline.InlineChatHandleService$z &#123;
  public static final int[] byte;
  public static final int[] enum;
&#125;
```

---

## 7. InlineChatStreamHandleService — 内联聊天流式处理

**类签名:** `public class com.aicode.inline.InlineChatStreamHandleService`
**源文件:** `tf` (混淆后)
**包路径:** `com.aicode.inline`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `private static` | `TextAttributes` | `byte` | 文本属性(混淆名) |
| `public static volatile` | `boolean` | `HANDING_DATA` | 正在处理数据标志 |
| `private static` | `TextAttributes` | `enum` | 文本属性(混淆名) |
| `public static` | `TextAttributes` | `toHandleAttributes` | 待处理文本属性 |
| `public static` | `TextAttributes` | `highLightAttributes` | 高亮文本属性 |

### 方法

#### public 方法 (外部API) — 4个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static handleErrorData(SessionController, String)` | 处理错误数据 |
| `void` | `static saveDocument(Project, Document)` | 保存文档 |
| `void` | `static handleData(String, ResponseStreamDto, MessageDto)` | 处理流式数据 |

#### private 方法 (内部实现) — 21个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static sb(SessionController, Editor, int)` | 内部处理 |
| `void` | `static enum(int)` | 混淆开关方法 |
| `void` | `static Cc(SessionController, String)` | 内部处理 |
| `void` | `static rc(ResponseStreamDto, SessionController, String, Editor)` | 内部处理 |
| `void` | `static eA(SessionController, InlineChatInfo, Editor, Document)` | 内部处理 |
| `String` | `static gB(String)` | 内部处理 |
| `void` | `static ka(SessionController, InlineChatInfo, Editor, Document, CaretModel, int, boolean)` | 内部处理 |
| `void` | `static Ua(SessionController, Document, int, CaretModel, Editor, InlineChatInfo, boolean)` | 内部处理 |
| `void` | `static OA(Editor, List&lt;DiffRow&gt;, Document, int, SessionController)` | Diff处理 |
| `void` | `static hC(Document, SessionController, Editor)` | 内部处理 |
| `void` | `static xB(SessionController, Editor)` | 内部处理 |
| `void` | `static Gc(SessionController, Document, int, Editor, InlineChatInfo, boolean, CaretModel)` | 内部处理 |
| `void` | `static pB(SessionController, InlineChatInfo, Editor, Document, CaretModel, int, boolean)` | 内部处理 |
| `String` | `static GA(Boolean)` | 内部处理 |
| `void` | `static pc(SessionController, InlineChatInfo, Editor, Document, int)` | 内部处理 |
| `void` | `static sc(Document, SessionController, int)` | 内部处理 |
| `String` | `static WB(Boolean)` | 内部处理 |
| `TextAttributes` | `static tb(Color)` | 创建文本属性 |
| `void` | `static Hc(Document, SessionController, int)` | 内部处理 |
| `String` | `static ga(String)` | 内部处理 |
| `void` | `static La(int, List, Document, MarkupModel, SessionController, Editor)` | 内部处理 |
| `void` | `static Tc(SessionController)` | 内部处理 |
| `void` | `static jc(SessionController)` | 内部处理 |
| `String` | `static zA(ResponseStreamDto$ResponseData, InlineChatInfo)` | 内部处理 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public InlineChatStreamHandleService()` | 公有构造器 |

### 内部类

#### InlineChatStreamHandleService$v — 混淆开关表
```java
public class com.aicode.inline.InlineChatStreamHandleService$v &#123;
  public static final int[] byte;
  public static final int[] enum;
&#125;
```

---
