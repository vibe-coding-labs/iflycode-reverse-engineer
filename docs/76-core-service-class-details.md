# iFlyCode 核心 Service 类字段级详细分析

> 基于 iFlyCode 3.4.2-222 插件 JAR 反编译结果
> 工具: javap -p (OpenJDK 11)
> 日期: 2026-05-13

## 概览

| # | 类名 | 包路径 | 字段数 | 方法数 | 内部类数 |
|---|------|--------|--------|--------|----------|
| 1 | ChatService | com.aicode.agent.service | 3 | 67 | 1 |
| 2 | CodeCompleteService | com.aicode.agent.service | 0 | 3 | 1 |
| 3 | CommonService | com.aicode.agent.service | 2 | 56 | 3 |
| 4 | InlineChatService | com.aicode.inline | 2 | 21 | 1 |
| 5 | InlineChatCommandService | com.aicode.agent.service | 3 | 24 | 2 |
| 6 | InlineChatHandleService | com.aicode.inline | 4 | 21 | 1 |
| 7 | InlineChatStreamHandleService | com.aicode.inline | 5 | 26 | 1 |
| 8 | GitReviewService | com.aicode.agent.service | 0 | 10 | 1 |
| 9 | SqlService | com.aicode.agent.service | 2 | 18 | 1 |
| 10 | CodeCheckService | com.aicode.agent.service | 1 | 11 | 1 |
| 11 | CodeSearchService | com.aicode.agent.service | 1 | 16 | 3 |
| 12 | UserService | com.aicode.agent.service | 3 | 23 | 4 |
| 13 | TemplateRequestService | com.aicode.template.request | 6 | 45 | 0 |
| 14 | BatchUnitTestService | com.aicode.test | 1 | 11 | 2 |
| 15 | PluginWebsocketClient | com.aicode.agent | 9 | 14 | 0 |
| 16 | RestartableAgentProcessService | com.aicode.agent.service | 6 | 13 | 0 |
| 17 | RequestTipServiceImpl | com.aicode.service.editor | 8 | 15 | 1 |
| 18 | EditorManagerServiceImpl | com.aicode.service.editor | 15 | 55 | 2 |
| 19 | OpenTelemetryService | com.aicode.apm | 1 | 4 | 0 |
| 20 | DiffService | com.aicode.diff | 6 | 11 | 0 |
| | **合计** | | **78** | **440** | **26** |

---

## 1. ChatService — 聊天服务核心

**类签名:** `public class com.aicode.agent.service.ChatService`
**源文件:** `em` (混淆后)
**包路径:** `com.aicode.agent.service`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `public static` | `List<String>` | `NEED_CODE_LIST` | 需要代码的命令列表 |
| `private static final` | `org.slf4j.Logger` | `enum` | 日志器(混淆名) |
| `public static` | `ConcurrentNavigableMap<String, String>` | `SESSION_ID` | 会话ID映射 |

### 方法

#### public 方法 (外部API) — 31个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `List<String>` | `static getTalkList(JsonObject)` | 获取对话列表 |
| `void` | `static sendError2Web(JsonObject, Project, MessageDto)` | 发送错误到Web端 |
| `void` | `static handleParseWebUrlErr(JsonObject, Project, String)` | 处理Web URL解析错误 |
| `JsonObject` | `static getTalkPredictResult(JsonObject, MessageDto)` | 获取对话预测结果 |
| `JsonObject` | `static getAgentChatResponse(JsonObject, MessageDto)` | 获取Agent聊天响应 |
| `void` | `static handleAction(WebViewDataTypeEnum, JsonObject, String, Project)` | 处理WebView动作 |
| `boolean` | `static isChat(CommandEnum, JsonObject, Project, MessageDto, ResponseDto)` | 判断是否为聊天命令 |
| `FirstChatMessage` | `static getRightChatMessage2Web(Project, String)` | 获取右侧聊天消息 |
| `void` | `static send2Agent(Project, FirstChatMessage)` | 发送消息到Agent |
| `CodeInfoDto` | `static getSelectedCode(String)` | 获取选中代码 |
| `void` | `static handleChatStop(Project, JsonObject)` | 处理聊天停止 |
| `void` | `static handleFeedbackCategory(JsonObject, Project)` | 处理反馈分类 |
| `JsonObject` | `static getTalkHistory(Project, JsonObject)` | 获取对话历史 |
| `void` | `static handleCodeDebug(Project, String, String, boolean)` | 处理代码调试(4参数) |
| `void` | `static getTalkPredict(Project)` | 获取对话预测 |
| `void` | `static getRequestForTalkHistory(JsonObject, Project)` | 请求对话历史 |
| `void` | `static handleNewChat(Project)` | 处理新建聊天 |
| `FirstChatMessage` | `static getFirstChatMessage2Web(Project, String)` | 获取首条聊天消息 |
| `void` | `static handleCodeComment(Project, JsonObject, MessageDto)` | 处理代码评论(JsonObject版) |
| `void` | `static handleChatDeleteMsg(String, Project)` | 处理删除聊天消息 |
| `void` | `static handleCodeDebug(Project, String, Integer, String, String, boolean)` | 处理代码调试(6参数) |
| `boolean` | `static hasAnyDirectory(String)` | 检查是否有目录 |
| `String` | `static getPath(Project)` | 获取项目路径 |
| `void` | `static deleteHistoryItem(JsonObject, Project)` | 删除历史项 |
| `JsonObject` | `static getErrorChatResponse(FirstChatMessage$ValueDTO)` | 获取错误聊天响应 |
| `boolean` | `static isCurrentBranchRemote(Project)` | 判断当前分支是否远程 |
| `JsonObject` | `static getKnowledgeChatResponse(JsonObject, MessageDto)` | 获取知识库聊天响应 |
| `FirstChatMessage` | `static getEditorChatMessage2Web(Project, String, CodeInfoDto)` | 获取编辑器聊天消息 |
| `FirstChatMessage` | `static getFirstChatMessage(Project, String, CodeInfoDto, JsonArray)` | 获取首条聊天消息(完整版) |
| `void` | `static handleCodeComment(Project, CommentInfo, MessageDto)` | 处理代码评论(CommentInfo版) |
| `void` | `static getHistoryList(Project)` | 获取历史列表 |
| `void` | `static handleChatMessage(Project, String)` | 处理聊天消息 |
| `JsonObject` | `static getGamePlay(JsonObject)` | 获取游戏玩法 |
| `void` | `static refreshAgent(Project, boolean)` | 刷新Agent |
| `void` | `static handleAgentAction(CommandEnum, JsonObject, String, MessageDto, Project)` | 处理Agent动作 |
| `CodeInfoDto` | `static getCodeInfoDto(Editor, SelectionModel, int, int)` | 获取代码信息DTO |

#### private 方法 (内部实现) — 35个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static iE(Project, FirstChatMessage, MessageDto)` | 内部处理 |
| `void` | `static Ye(Project, MessageDto, RequestCaseCodeDto)` | 内部处理 |
| `boolean` | `static ye(JsonObject, JsonObject)` | 内部判断 |
| `void` | `static ZC(Project, String, int, AtomicInteger, int, String)` | 内部处理 |
| `void` | `static EE(Project)` | 内部处理 |
| `boolean` | `static QD(JsonObject, JsonObject)` | 内部判断 |
| `void` | `static bF(JsonObject)` | 内部处理 |
| `void` | `static Ad(Project)` | 内部处理 |
| `void` | `static vD(Project, JsonObject, MessageDto)` | 内部处理 |
| `int` | `static Zf(CommentInfo, CommentInfo)` | 内部比较 |
| `void` | `static GE(File)` | 内部处理 |
| `void` | `static TE(Project, PluginToolWindowPanel)` | 内部处理 |
| `void` | `static Mf(Project, String, int, AtomicInteger, int, String)` | 内部处理 |
| `void` | `static of(String, Project, int, AtomicInteger, int, String)` | 内部处理 |
| `JsonArray` | `static CD(JsonObject, JsonObject)` | 内部处理 |
| `JsonObject` | `static Lf(JsonObject, JsonObject)` | 内部处理 |
| `void` | `static Oe(JsonObject, Project, MessageDto)` | 内部处理 |
| `void` | `static dE(Project, JsonObject)` | 内部处理 |
| `FirstChatMessage` | `static oE(String, Project, boolean, String)` | 内部处理 |
| `void` | `static jF(Project, JsonObject)` | 内部处理 |
| `void` | `static hE(Project, String)` | 内部处理 |
| `void` | `static cd(JsonObject, MessageDto, Project)` | 内部处理 |
| `void` | `static rE(String, Project, int, AtomicInteger, int, String)` | 内部处理 |
| `void` | `static VE(Project)` | 内部处理 |
| `JsonObject` | `static qD(JsonObject, JsonObject)` | 内部处理 |
| `void` | `static df(Project)` | 内部处理 |
| `void` | `static ed(Project, JsonObject, WebViewDataTypeEnum)` | 内部处理 |
| `String` | `static hF(Project)` | 内部处理 |
| `CodeInfoDto` | `static qE(String)` | 内部处理 |
| `boolean` | `static mD(JsonObject, JsonObject)` | 内部判断 |
| `void` | `static enum(int)` | 混淆开关方法 |
| `void` | `static zd(Application, Project, JsonObject)` | 内部处理 |
| `void` | `static Id(Application, Project, String, int, AtomicInteger, int, String)` | 内部处理 |
| `void` | `static Vf(Project)` | 内部处理 |
| `void` | `static Dd(Project, FirstChatMessage)` | 内部处理 |
| `void` | `static zD(AtomicReference, Project)` | 内部处理 |
| `void` | `static ae(Project, MessageDto, JsonObject)` | 内部处理 |
| `boolean` | `static Kf(JsonObject, JsonObject)` | 内部判断 |
| `MessageDto` | `static Uf(String, String, String, Integer, String, String)` | 内部处理 |
| `void` | `static Me(Project)` | 内部处理 |
| `void` | `static Cd(Project, JsonObject)` | 内部处理 |
| `void` | `static qd(JsonObject, Project)` | 内部处理 |
| `JsonObject` | `static DE(JsonObject, JsonObject)` | 内部处理 |
| `void` | `static gF(MessageDto, Project)` | 内部处理 |
| `void` | `static dD(Application, Project, String, int, AtomicInteger, int, String)` | 内部处理 |
| `void` | `static QE(Project, Application, JsonObject)` | 内部处理 |
| `void` | `static Ed(Project, JsonObject)` | 内部处理 |
| `void` | `static BD(Project, JsonArray)` | 内部处理 |
| `void` | `static ZE(PluginToolWindowPanel, MessageDto, Project)` | 内部处理 |
| `JsonArray` | `static UD(String)` | 内部处理 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `private ChatService()` | 私有构造器(工具类模式) |

### 内部类

#### ChatService$Ia
```java
public class com.aicode.agent.service.ChatService$Ia {
  public static final int[] byte;
  public static final int[] enum;
}
```
混淆开关表内部类，包含 `byte[]` 和 `enum[]` 两个 int 数组，用于控制混淆方法名的映射。

---

## 2. CodeCompleteService — 代码补全服务

**类签名:** `public class com.aicode.agent.service.CodeCompleteService`
**源文件:** `go` (混淆后)
**包路径:** `com.aicode.agent.service`

### 字段

无实例或静态字段。

### 方法

#### public 方法 (外部API) — 2个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `String` | `static H(Object)` | 辅助方法(混淆名) |
| `void` | `static handleAgentAction(CommandEnum, JsonObject, MessageDto, String, Project)` | 处理Agent动作 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public CodeCompleteService()` | 公有构造器 |

### 内部类

#### CodeCompleteService$ja
```java
public class com.aicode.agent.service.CodeCompleteService$ja {
  public static final int[] enum;
}
```
混淆开关表内部类。

---

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
| `void` | `static SE(PresentationDataDto, List<CommandEnum>)` | 内部处理 |
| `void` | `static re(Project, RequestCaseCodeDto$ValueDTO)` | 内部处理 |
| `boolean` | `static Le(Editor, int, int)` | 内部判断 |
| `void` | `static cf(String, Project, Integer)` | 内部处理 |
| `List<PresentationDataDto>` | `static ID(JsonObject, String, Editor)` | 内部处理 |
| `void` | `static je(Project, List, String, String)` | 内部处理 |
| `void` | `static RD(Application, Project, String)` | 内部处理 |
| `void` | `static xd(Application, Project, List, String, String)` | 内部处理 |
| `void` | `static enum(int)` | 混淆开关方法 |
| `void` | `static Gd(Project)` | 内部处理 |
| `void` | `static XC(Project, String)` | 内部处理 |
| `void` | `static IE(Editor)` | 内部处理 |
| `void` | `static lD(Application, Project, String)` | 内部处理 |
| `void` | `static cF(Document, String, List<PresentationDataDto>, Type, JsonElement, String, String)` | 内部处理 |
| `String` | `static Ge(String, int)` | 内部处理 |
| `void` | `static BE(Project, Boolean, Map)` | 内部处理 |
| `void` | `static GD(Project, String)` | 内部处理 |
| `void` | `static KF(Project)` | 内部处理 |
| `boolean` | `static WD(List<RangeHighlighter>, PresentationDataDto, Set<String>)` | 内部判断 |
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
public class com.aicode.agent.service.CommonService$Fa extends java.awt.event.MouseMotionAdapter {
  public final EditorGutterComponentEx float;
  public final Editor byte;
  public final int enum;
  public void mouseMoved(MouseEvent);
}
```
编辑器行号区域的鼠标移动监听器，用于处理行号区域的悬停交互。

#### CommonService$Ha — 混淆开关表
```java
public class com.aicode.agent.service.CommonService$Ha {
  public static final int[] byte;
  public static final int[] enum;
}
```

#### CommonService$Ma — TypeToken子类
```java
public class com.aicode.agent.service.CommonService$Ma
    extends TypeToken<List<CodeInfoDto$RangeDTO>> {}
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
public final class com.aicode.inline.InlineChatService$Companion {
  public static void closeInlineChat(InlineChatPanel);
  public static void removeFlag(Editor);
  public InlineChatPanel getInlineChat(Editor);
  public static void closeInlineChat(Editor);
  public static void openInlineChat(Editor, LastChatQuestionInfo);
  public static void openInlineChat(Editor);
}
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
| `public static final` | `Key<Integer>` | `VERSION_KEY` | 版本Key |
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
    extends TypeToken<List<CodeInfoDto$RangeDTO>> {}
```

#### InlineChatCommandService$ka — 混淆开关表
```java
public class com.aicode.agent.service.InlineChatCommandService$ka {
  public static final int[] byte;
  public static final int[] enum;
}
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
| `void` | `static dF(SessionController, String, Editor, int, Document, int, List<String>)` | 内部处理 |
| `void` | `static he(SessionController, String)` | 内部处理 |
| `void` | `static id(Document, int, String, int, List, MarkupModel, Editor, boolean)` | 内部处理 |
| `String` | `static lf(Boolean)` | 内部处理 |
| `void` | `static Sf(Editor, int)` | 内部处理 |
| `TextAttributes` | `static tb(Color)` | 创建文本属性 |
| `boolean` | `static Hd(Editor, String, List<CodeInfoDto$RangeDTO>, Document, List<String>, SessionController)` | 内部判断 |
| `void` | `static aE(SessionController)` | 内部处理 |
| `void` | `static Tc(SessionController)` | 内部处理 |
| `void` | `static OA(Editor, List<DiffRow>, Document, int, SessionController)` | Diff处理 |
| `void` | `static Tf(Editor, String, int, Document, int, List<String>, boolean)` | 内部处理 |
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
public class com.aicode.inline.InlineChatHandleService$z {
  public static final int[] byte;
  public static final int[] enum;
}
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
| `void` | `static OA(Editor, List<DiffRow>, Document, int, SessionController)` | Diff处理 |
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
public class com.aicode.inline.InlineChatStreamHandleService$v {
  public static final int[] byte;
  public static final int[] enum;
}
```

---

## 8. GitReviewService — Git 评审服务

**类签名:** `public class com.aicode.agent.service.GitReviewService`
**源文件:** `sk` (混淆后)
**包路径:** `com.aicode.agent.service`

### 字段

无实例或静态字段。

### 方法

#### public 方法 (外部API) — 8个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `String` | `static removeMarkdownCodeBlocks(String)` | 移除Markdown代码块 |
| `String` | `static H(Object)` | 辅助方法(混淆名) |
| `void` | `static sendGitDiffRequest(String, Project)` | 发送Git Diff请求 |
| `void` | `static getCommitMessage(Project, String, JsonObject)` | 获取提交消息 |
| `void` | `static handleAgentAction(CommandEnum, JsonObject, Project)` | 处理Agent动作 |
| `JsonObject` | `static getGiffReview(String, JsonObject)` | 获取Diff评审 |
| `JsonObject` | `static getGiffDiff(JsonObject)` | 获取Diff差异 |
| `void` | `static handleAction(WebViewDataTypeEnum, JsonObject, Project)` | 处理WebView动作 |
| `void` | `static sendCodeReviewRequest(JsonObject, Project)` | 发送代码评审请求 |

#### private 方法 (内部实现) — 2个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static ME(EditorTextField, String)` | 内部处理 |
| `void` | `static if(EditorTextField, String)` | 内部处理 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public GitReviewService()` | 公有构造器 |

### 内部类

#### GitReviewService$Ca — 混淆开关表
```java
public class com.aicode.agent.service.GitReviewService$Ca {
  public static final int[] byte;
  public static final int[] enum;
}
```

---

## 9. SqlService — SQL 服务

**类签名:** `public class com.aicode.agent.service.SqlService`
**源文件:** `ml` (混淆后)
**包路径:** `com.aicode.agent.service`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `public static` | `ConcurrentNavigableMap<String, String>` | `SQL_SESSION_ID` | SQL会话ID映射 |
| `private static final` | `org.slf4j.Logger` | `enum` | 日志器(混淆名) |

### 方法

#### public 方法 (外部API) — 14个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static handleSqlTest(JsonObject, Project)` | 处理SQL测试 |
| `JsonObject` | `static saveSource(JsonObject)` | 保存数据源 |
| `JsonObject` | `static getTableList(JsonObject)` | 获取表列表 |
| `JsonObject` | `static testConnect(JsonObject)` | 测试连接 |
| `JsonObject` | `static getSourceType(JsonObject)` | 获取数据源类型 |
| `void` | `static handleSqlSave(JsonObject, Project)` | 处理SQL保存 |
| `void` | `static handleSqlChatMessage(JsonObject, Project)` | 处理SQL聊天消息 |
| `void` | `static handleSqlDelete(JsonObject, Project)` | 处理SQL删除 |
| `void` | `static handleAction(WebViewDataTypeEnum, JsonObject, Project)` | 处理WebView动作 |
| `void` | `static handleAgentAction(CommandEnum, String, JsonObject, Project)` | 处理Agent动作 |
| `JsonObject` | `static getSqlChat(Project, String, JsonObject, String)` | 获取SQL聊天 |
| `void` | `static handleSqlTableList(JsonObject, Project)` | 处理SQL表列表 |
| `void` | `static handleSqlChatStop(Project, JsonObject)` | 处理SQL聊天停止 |
| `JsonObject` | `static getSourceList(JsonObject)` | 获取数据源列表 |

#### private 方法 (内部实现) — 2个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `ConnectConfigDto` | `static sf(JsonObject)` | 解析连接配置 |
| `FirstChatMessage` | `static kF(Project, JsonObject)` | 构建聊天消息 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public SqlService()` | 公有构造器 |

### 内部类

#### SqlService$Ba — 混淆开关表
```java
public class com.aicode.agent.service.SqlService$Ba {
  public static final int[] byte;
  public static final int[] enum;
}
```

---

## 10. CodeCheckService — 代码检查服务

**类签名:** `public class com.aicode.agent.service.CodeCheckService`
**源文件:** `ej` (混淆后)
**包路径:** `com.aicode.agent.service`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `public static final` | `boolean` | `enum` | 混淆开关标志 |

### 方法

#### public 方法 (外部API) — 9个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static handleAction(WebViewWindowPanel, WebViewDataTypeEnum, JsonObject, Project)` | 处理WebView动作 |
| `void` | `static sendCodeCheck(Project)` | 发送代码检查 |
| `CodeCheckListDto` | `static getErrorList(String)` | 获取错误列表 |
| `JsonObject` | `static getErrorResponse(String, String)` | 获取错误响应 |
| `CodeCheckListDto` | `static getErrorListResult(ResponseDto)` | 获取错误列表结果 |
| `JsonObject` | `static fixCodeCheck(JsonObject, Project)` | 修复代码检查 |
| `CodeCheckListDto` | `static getCheckData(JsonObject)` | 获取检查数据 |
| `JsonObject` | `static getAgentChatResponse(JsonObject, MessageDto)` | 获取Agent聊天响应 |
| `void` | `static handleAgentAction(CommandEnum, JsonObject, MessageDto, String, Project)` | 处理Agent动作 |
| `CodeCheckListDto` | `static getList(List<CodeCheckDto>)` | 获取检查列表 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public CodeCheckService()` | 公有构造器 |

### 内部类

#### CodeCheckService$Da — 混淆开关表
```java
public class com.aicode.agent.service.CodeCheckService$Da {
  public static final int[] byte;
  public static final int[] enum;
}
```

---

## 11. CodeSearchService — 代码搜索服务

**类签名:** `public class com.aicode.agent.service.CodeSearchService`
**源文件:** `rm` (混淆后)
**包路径:** `com.aicode.agent.service`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `private static final` | `com.intellij.openapi.diagnostic.Logger` | `enum` | 日志器(混淆名) |

### 方法

#### public 方法 (外部API) — 12个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `JsonObject` | `static getCodeSearchRepos(String, JsonObject)` | 获取代码搜索仓库 |
| `void` | `static handleAgentAction(CommandEnum, JsonObject, String, Project)` | 处理Agent动作 |
| `JsonObject` | `static getCodeSearchLanguage(JsonObject)` | 获取代码搜索语言 |
| `JsonObject` | `static getCodeSearchCode(String, JsonObject)` | 获取代码搜索代码 |
| `JsonObject` | `static requestCopyCode(String)` | 请求复制代码 |
| `void` | `static requestOpenUrl(String)` | 请求打开URL |
| `void` | `static sendCodeRepoRequest(JsonObject, Project)` | 发送代码仓库请求 |
| `void` | `static handleAction(WebViewWindowPanel, WebViewDataTypeEnum, JsonObject, String, Project)` | 处理WebView动作 |
| `void` | `static requestInsertCode(Project, String)` | 请求插入代码 |
| `void` | `static sendCodeSearchRequest(JsonObject, Project)` | 发送代码搜索请求 |
| `void` | `static requestCodeFile(Project, String)` | 请求代码文件 |

#### private 方法 (内部实现) — 4个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static Ld(Application, Project, RequestCaseCodeDto$ValueDTO)` | 内部处理 |
| `void` | `static Te(Application, String, Project, RequestCaseCodeDto$ValueDTO)` | 内部处理 |
| `void` | `static tE(Project, RequestCaseCodeDto$ValueDTO)` | 内部处理 |
| `void` | `static XE(Project, RequestCaseCodeDto$ValueDTO)` | 内部处理 |
| `void` | `static eE(String, Project, RequestCaseCodeDto$ValueDTO)` | 内部处理 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public CodeSearchService()` | 公有构造器 |

### 内部类

#### CodeSearchService$Aa — 混淆开关表
```java
public class com.aicode.agent.service.CodeSearchService$Aa {
  public static final int[] byte;
  public static final int[] enum;
}
```

#### CodeSearchService$ga — TypeToken子类
```java
public class com.aicode.agent.service.CodeSearchService$ga
    extends TypeToken<List<CodeRepoInfoDto>> {}
```

#### CodeSearchService$ia — TypeToken子类
```java
public class com.aicode.agent.service.CodeSearchService$ia
    extends TypeToken<List<CodeInfoDto>> {}
```

---

## 12. UserService — 用户服务

**类签名:** `public class com.aicode.agent.service.UserService`
**源文件:** `zf` (混淆后)
**包路径:** `com.aicode.agent.service`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `private static final` | `com.intellij.openapi.diagnostic.Logger` | `byte` | 日志器(混淆名) |
| `private static` | `String` | `enum` | 登录URL(混淆名) |
| `public static` | `boolean` | `goTo` | 跳转标志 |

### 方法

#### public 方法 (外部API) — 17个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static showMessage(Project)` | 显示消息 |
| `void` | `static repaintModelComboBox(ComboBox)` | 重绘模型下拉框 |
| `void` | `static SetModel(JsonObject)` | 设置模型 |
| `void` | `static setGoTo(boolean)` | 设置跳转标志 |
| `boolean` | `static isGoTo()` | 获取跳转标志 |
| `void` | `static setItem(ComboBox, List<CodeModel>)` | 设置下拉框项 |
| `String` | `static getLoginUrl()` | 获取登录URL(无参) |
| `void` | `static send2WebShowOperateGuidance(JsonObject, Project)` | 发送操作指引到Web |
| `void` | `static logout(Project)` | 登出 |
| `JsonArray` | `static sortJsonArray(JsonArray, List<String>)` | 排序JSON数组 |
| `void` | `static handleAgentAction(CommandEnum, JsonObject, String, Object, Project)` | 处理Agent动作 |
| `void` | `static setLoginUrl(String)` | 设置登录URL |
| `JsonObject` | `static getLoginInfo(JsonObject, Project)` | 获取登录信息 |
| `JsonObject` | `static getLoginUrl(String)` | 获取登录URL(有参) |
| `void` | `static getUserPermissions(JsonObject, Project)` | 获取用户权限 |
| `void` | `static handleAction(WebViewDataTypeEnum, Project)` | 处理WebView动作 |
| `void` | `static clearIcon(Project)` | 清除图标 |
| `void` | `static sendWriterConfig(Project, JsonObject)` | 发送写入器配置 |
| `JsonObject` | `static getUserModelList(JsonObject, MessageDto)` | 获取用户模型列表 |

#### private 方法 (内部实现) — 5个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static lE(Project)` | 内部处理 |
| `void` | `static bd(Project)` | 内部处理 |
| `void` | `static Wf(MessageDto, Project)` | 内部处理 |
| `void` | `static uD()` | 内部处理 |
| `void` | `static ef(ItemEvent)` | 内部处理 |
| `void` | `static VC(MessageDto)` | 内部处理 |
| `void` | `static od(ComboBox)` | 内部处理 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public UserService()` | 公有构造器 |

### 内部类

#### UserService$Ja — 混淆开关表
```java
public class com.aicode.agent.service.UserService$Ja {
  public static final int[] byte;
  public static final int[] enum;
}
```

#### UserService$da — 通知动作
```java
public class com.aicode.agent.service.UserService$da extends NotificationAction {
  public final Project enum;
  public void actionPerformed(AnActionEvent, Notification);
}
```
IntelliJ通知动作，用于处理用户点击通知后的操作。

#### UserService$ea — TypeToken子类
```java
public class com.aicode.agent.service.UserService$ea
    extends TypeToken<List<FunctionModelInfo>> {}
```

#### UserService$la — TypeToken子类
```java
public class com.aicode.agent.service.UserService$la
    extends TypeToken<List<String>> {}
```

---

## 13. TemplateRequestService — 模板请求服务

**类签名:** `public class com.aicode.template.request.TemplateRequestService`
**源文件:** `TemplateRequestService.java` (未混淆)
**包路径:** `com.aicode.template.request`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `private static final` | `com.intellij.openapi.diagnostic.Logger` | `LOG` | 日志器 |
| `public static final` | `Cache<String, FileRequestDto>` | `classModelRenders` | 类模型渲染缓存 |
| `public static final` | `int` | `MAX_TOKEN_CHAR_LENGTH` | 最大Token字符长度 |
| `public static final` | `int` | `MAX_REQUEST_LIMIT` | 最大请求限制 |
| `public static final` | `long` | `RETRY_WAIT_TIME` | 重试等待时间 |
| `private static final` | `int` | `CLASS_CACHE_LIMIT` | 类缓存限制 |

### 方法

#### public 方法 (外部API) — 28个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `synchronized void` | `static handleAgentAction(CommandEnum, JsonObject, MessageDto, String, Project)` | 处理Agent动作(同步) |
| `synchronized void` | `static handleRequestErrorTestCase(ResponseDto, CommandEnum, MessageDto)` | 处理请求错误测试用例(同步) |
| `void` | `static analysisString(String, Method)` | 分析字符串 |
| `void` | `static addCase(UnitTestDto$DataDTO$FunctionDataDTO, Method)` | 添加测试用例(2参数) |
| `void` | `static setParent(ResolvedBranch, Boolean, List<CaseBranch>)` | 设置父分支 |
| `void` | `static setPrev(ResolvedBranch, Boolean, List<CaseBranch>)` | 设置前驱分支 |
| `void` | `static setAfter(ResolvedBranch, Boolean, List<CaseBranch>)` | 设置后继分支 |
| `String` | `static extractTagValue(String, String)` | 提取标签值 |
| `int` | `static countMatches(String, String)` | 统计匹配次数 |
| `MethodRequestResult` | `static requestAI(PsiClass, Type, PsiMethod, TypeDictionary, GeneratorTemplateConfig, String, Project, List<MessageDto>, Set<Method>, FileRequestDto, Module, Map<String, String>)` | 请求AI(12参数) |
| `List<MessageDto>` | `static requestAI(String, PsiClass, Type, TypeDictionary, GeneratorTemplateConfig, String, Project, boolean, Module, Set<Method>)` | 请求AI(10参数) |
| `String` | `static convertKey(String, String)` | 转换Key |
| `Boolean` | `static containFile(String, String)` | 判断是否包含文件 |
| `boolean` | `static shouldBeTested(PsiMethod, PsiClass, GeneratorTemplateConfig)` | 判断方法是否应被测试 |
| `synchronized boolean` | `static isModelReturned(String, String)` | 判断模型是否已返回(同步) |
| `synchronized boolean` | `static isAllReturned(String)` | 判断是否全部返回(同步) |
| `synchronized FileRequestDto` | `static getReturnedFile(String)` | 获取已返回文件(同步) |
| `boolean` | `static remove(String, String)` | 移除(2参数) |
| `boolean` | `static remove(String, String, boolean)` | 移除(3参数) |
| `synchronized boolean` | `static isModelReturned(String, FileRequestDto)` | 判断模型是否已返回(同步,2参数) |
| `int` | `static calculateRequestAiInterval(int)` | 计算请求AI间隔 |
| `int` | `static calculateGeneratorTimes(int, int)` | 计算生成次数 |
| `void` | `static clearCache()` | 清除缓存 |

#### private 方法 (内部实现) — 17个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static addCase(String, Method, List<CaseResult>)` | 添加测试用例(3参数) |
| `String` | `static getMethodName(String, List<CaseResult>)` | 获取方法名 |
| `void` | `static convertException(String, CaseResult, Method)` | 转换异常 |
| `String` | `static resolveMessage(String)` | 解析消息 |
| `CaseParam` | `static convertOutput(String, Method)` | 转换输出 |
| `CaseParam` | `static convertJsonObject(String, String)` | 转换JSON对象 |
| `Map<String, CaseParam>` | `static convertInput(String, Method)` | 转换输入 |
| `void` | `static addMock(String, List<ToMockMethod>)` | 添加Mock |
| `void` | `static addBranches(String, Method, List<CaseBranch>)` | 添加分支 |
| `void` | `static recursionBranches(ResolvedBranch, List<String>, List<CaseBranch>)` | 递归分支 |
| `void` | `static resolveAllBranches(ResolvedBranch, List<CaseBranch>)` | 解析所有分支 |
| `void` | `static checkChildren(List<ResolvedBranch>, List<String>, List<CaseBranch>)` | 检查子节点 |
| `boolean` | `static matchIfBranch(String, String)` | 匹配if分支 |
| `boolean` | `static checkBranchInModelData(ResolvedBranch, List<String>, List<CaseBranch>)` | 检查分支在模型数据中 |
| `void` | `static resolveCaseBranch(ResolvedBranch, Boolean, List<CaseBranch>)` | 解析用例分支 |
| `String` | `static convertMethodName(String)` | 转换方法名 |
| `String` | `static convertBaseMethodName(String)` | 转换基础方法名 |
| `String` | `static caseHandle(String, String)` | 用例处理 |
| `String` | `static caseMocks(String, String)` | 用例Mock |
| `void` | `static appendTypeBody(Type, StringBuilder, PsiClass, boolean, Set<String>, Project, Module, TypeDictionary, Map<String, String>)` | 追加类型体 |
| `boolean` | `static calculateString2MaxToken(StringBuilder)` | 计算字符串是否超过最大Token |
| `void` | `static getBodyContent(StringBuilder, PsiMethod, PsiClass, List<String>, boolean, int)` | 获取方法体内容 |
| `void` | `static appendMethodText(StringBuilder, String, String)` | 追加方法文本 |

#### lambda 方法 — 16个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `boolean` | `static lambda$requestAI$15(String, Method)` | lambda |
| `void` | `static lambda$requestAI$14(FileRequestDto, MethodRequestResult)` | lambda |
| `boolean` | `static lambda$requestAI$13(Project, PsiClass, PsiMethod, String, UnitTestDto$DataDTO$FunctionDataDTO)` | lambda |
| `String` | `static lambda$requestAI$12(MethodCall)` | lambda |
| `boolean` | `static lambda$requestAI$11(Type, MethodCall)` | lambda |
| `boolean` | `static lambda$requestAI$10(String, Method)` | lambda |
| `boolean` | `static lambda$resolveCaseBranch$9(ResolvedBranch, CaseBranch)` | lambda |
| `boolean` | `static lambda$checkBranchInModelData$8(Optional, String)` | lambda |
| `boolean` | `static lambda$checkBranchInModelData$7(ResolvedBranch, String)` | lambda |
| `boolean` | `static lambda$resolveAllBranches$6(ResolvedBranch, CaseBranch)` | lambda |
| `void` | `static lambda$convertInput$5(Map, CaseParam, Param)` | lambda |
| `boolean` | `static lambda$getMethodName$4(String, CaseResult)` | lambda |
| `void` | `static lambda$handleRequestErrorTestCase$3(MessageDto, MethodRequestResult)` | lambda |
| `void` | `static lambda$handleRequestErrorTestCase$2(MessageDto, MethodRequestResult)` | lambda |
| `boolean` | `static lambda$handleRequestErrorTestCase$1(MessageDto, MethodRequestResult)` | lambda |
| `boolean` | `static lambda$handleAgentAction$0(MessageDto, MethodRequestResult)` | lambda |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public TemplateRequestService()` | 公有构造器 |

**注:** 此类未混淆，保留原始方法名，是最完整的可读服务类。

---

## 14. BatchUnitTestService — 批量单测服务

**类签名:** `public final class com.aicode.test.BatchUnitTestService`
**源文件:** `gc` (混淆后)
**包路径:** `com.aicode.test`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `private static final` | `org.slf4j.Logger` | `enum` | 日志器(混淆名) |

### 方法

#### public 方法 (外部API) — 10个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static batchUnitTestCreate(String, Project)` | 批量单测创建 |
| `void` | `static batchUnitTestDelete(String, Project)` | 批量单测删除 |
| `JsonObject` | `static codeBatchUnitTestList(JsonObject)` | 代码批量单测列表 |
| `void` | `static handleAction(WebViewDataTypeEnum, String, Project)` | 处理WebView动作 |
| `JsonObject` | `static batchUnitTestMessage(boolean, String)` | 批量单测消息 |
| `void` | `static batchUnitTestDownload(String, Project)` | 批量单测下载 |
| `void` | `static batchUnitTestList(Project)` | 批量单测列表 |
| `void` | `static handleAgentAction(CommandEnum, JsonObject, MessageDto, String, Project)` | 处理Agent动作 |
| `JsonObject` | `static batchUnitTestDownload(JsonObject, MessageDto)` | 批量单测下载(2参数) |

#### private 方法 (内部实现) — 1个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static oA(File)` | 内部处理 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public BatchUnitTestService()` | 公有构造器 |

### 内部类

#### BatchUnitTestService$g — TypeToken子类
```java
public class com.aicode.test.BatchUnitTestService$g
    extends TypeToken<List<BatchUnitTestDto>> {}
```

#### BatchUnitTestService$l — 混淆开关表
```java
public class com.aicode.test.BatchUnitTestService$l {
  public static final int[] byte;
  public static final int[] enum;
}
```

---

## 15. PluginWebsocketClient — WebSocket 客户端

**类签名:** `public class com.aicode.agent.PluginWebsocketClient`
**源文件:** `ve` (混淆后)
**包路径:** `com.aicode.agent`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `public static` | `ConcurrentNavigableMap<String, MessageDto>` | `AGENT_REQUEST` | Agent请求映射 |
| `public static` | `ConcurrentNavigableMap<String, WebRequestDto>` | `WEB_REQUEST` | Web请求映射 |
| `public static` | `ConcurrentNavigableMap<String, RequestCaseCodeDto>` | `WEB_REQUEST_DATA` | Web请求数据映射 |
| `public` | `okhttp3.Request` | `request` | OkHttp请求对象 |
| `private static final` | `org.slf4j.Logger` | `byte` | 日志器(混淆名) |
| `public static` | `ConcurrentNavigableMap<String, String>` | `AGENT_CLIENT_ID` | Agent客户端ID映射 |
| `public static` | `String` | `INITID` | 初始化ID |
| `public static` | `ConcurrentNavigableMap<String, WebSocket>` | `AGENT_WEBSOCKETS` | Agent WebSocket映射 |
| `public static final` | `boolean` | `enum` | 混淆开关标志 |
| `public static final` | `String` | `URI_LINK_PREFIX` | URI链接前缀 |
| `public static` | `okhttp3.OkHttpClient` | `client` | OkHttp客户端 |

### 方法

#### public 方法 (外部API) — 12个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `String` | `static getClientName()` | 获取客户端名称 |
| `WebSocket` | `newWebSocket(WebSocketListener)` | 创建WebSocket连接 |
| `void` | `static closeWebsocket(String, String)` | 关闭WebSocket(2参数) |
| `Boolean` | `static sendWsMessageForCode(Span, MessageDto, Project)` | 发送代码补全WS消息 |
| `void` | `static sendWsMessage(CommandEnum, Object, Project)` | 发送WS消息(3参数) |
| `void` | `static sendWsMessageForGitKnowledge(CommandEnum, Object, Project, WebViewDataTypeEnum)` | 发送Git知识WS消息 |
| `void` | `static wsInit(Project)` | WebSocket初始化 |
| `void` | `static sendWsMessageWithOutApm(MessageDto, Project)` | 发送WS消息(无APM) |
| `void` | `createWebSocketConnect(WebSocketListener, String, Span)` | 创建WebSocket连接 |
| `void` | `static sendWsMessage(MessageDto, Project)` | 发送WS消息(MessageDto版) |
| `void` | `static closeWebsocket(String)` | 关闭WebSocket(1参数) |
| `void` | `static sendWsMessage(CommandEnum, Project)` | 发送WS消息(2参数) |

#### private 方法 (内部实现) — 3个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `String` | `static FF(Span, MessageDto, boolean)` | 内部处理 |
| `Boolean` | `static Ce(Project, MessageDto)` | 内部处理 |
| `void` | `static yD(Map, String, String)` | 内部处理 |
| `Boolean` | `static Fd(Project, MessageDto)` | 内部处理 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public PluginWebsocketClient()` | 公有构造器 |

---

## 16. RestartableAgentProcessService — Agent 进程管理

**类签名:** `public class com.aicode.agent.service.RestartableAgentProcessService implements PluginAgentProcessService, com.intellij.openapi.Disposable`
**源文件:** `xj` (混淆后)
**包路径:** `com.aicode.agent.service`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `private final` | `Object` | `float` | 锁对象(混淆名) |
| `public static final` | `AtomicBoolean` | `pushAgentRefresh` | 推送Agent刷新标志 |
| `public final` | `AtomicInteger` | `connectAttempts` | 连接尝试次数 |
| `public static final` | `AtomicInteger` | `refreshTimes` | 刷新次数 |
| `private` | `PluginAgentProcessServiceImpl` | `byte` | 代理进程实现(混淆名) |
| `public static final` | `AtomicInteger` | `restartAttempts` | 重启尝试次数 |
| `private static final` | `com.intellij.openapi.diagnostic.Logger` | `enum` | 日志器(混淆名) |
| `public static final` | `int` | `RESTART_TIME` | 重启时间间隔 |

### 方法

#### public 方法 (外部API) — 10个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static pushAgentRefreshToWebView()` | 推送Agent刷新到WebView |
| `void` | `forceRestart()` | 强制重启 |
| `void` | `onRestartException(String, Integer)` | 重启异常回调(2参数) |
| `boolean` | `isRunning()` | 判断是否运行中 |
| `void` | `onReconnectException(String, Integer, Project)` | 重连异常回调 |
| `PluginAgentProcessServiceEx` | `getDelegate()` | 获取代理委托 |
| `void` | `checkAgent(Project)` | 检查Agent状态 |
| `void` | `dispose()` | 释放资源(Disposable接口) |
| `PluginAgentProcessServiceImpl` | `createInitializedDelegate()` | 创建初始化委托 |
| `void` | `onRestartException(String, Integer, Span)` | 重启异常回调(3参数) |
| `static void` | `killAgent()` | 终止Agent进程 |
| `void` | `refreshAgent(Project)` | 刷新Agent |
| `void` | `init()` | 初始化 |

#### private 方法 (内部实现) — 2个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `pE()` | 内部处理 |
| `String` | `JD()` | 内部处理(可能等待) |
| `void` | `de(String, Project)` | 内部处理 |
| `void` | `static enum(int)` | 混淆开关方法 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public RestartableAgentProcessService()` | 公有构造器 |

---

## 17. RequestTipServiceImpl — 请求提示服务

**类签名:** `public class com.aicode.service.editor.RequestTipServiceImpl implements RequestTipService, com.intellij.openapi.Disposable`
**源文件:** `zc` (混淆后)
**包路径:** `com.aicode.service.editor`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `public final` | `TipCache` | `cache` | 提示缓存 |
| `private static final` | `org.slf4j.Logger` | `final` | 日志器(混淆名) |
| `public` | `String` | `try` | 字段(混淆名) |
| `public static final` | `Map<String, CodeTipRequestDto>` | `CODE_TIP_MAP` | 代码提示请求映射 |
| `public static final` | `boolean` | `float` | 混淆开关标志 |
| `public static final` | `Map<Project, String>` | `LATEST_RESPONSE_DATA` | 最新响应数据 |
| `public` | `String` | `byte` | 字段(混淆名) |
| `public static final` | `Object` | `object` | 同步锁对象 |
| `public static final` | `Map<Project, Map<String, Long>>` | `LAST_REQUEST` | 最后请求时间映射 |
| `public` | `Language` | `enum` | 语言类型(混淆名) |

### 方法

#### public 方法 (外部API) — 10个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `boolean` | `isAvailable(Editor)` | 判断编辑器是否可用(接口方法) |
| `EditorRequestService` | `createInlineChatRequest(Editor, int, TipType)` | 创建内联聊天请求 |
| `EditorRequestService` | `createRequest(Editor, int, TipType)` | 创建请求 |
| `void` | `fetchInlineChatContent(EditorRequestService, Flow$Subscriber, Editor, String, CodeTipRequestType)` | 获取内联聊天内容 |
| `void` | `dealStreamAgentTips(String, ResponseStreamDto, Project, MessageDto)` | 处理流式Agent提示 |
| `void` | `dispose()` | 释放资源(Disposable接口) |
| `List<CodeInlayList>` | `fetchCachedTips(EditorRequestService)` | 获取缓存提示 |
| `String` | `getFileExtensionFromEditor(Editor)` | 获取编辑器文件扩展名 |
| `void` | `dealAgentTips(String, JsonObject, Project)` | 处理Agent提示 |
| `void` | `fetchTips(EditorRequestService, Flow$Subscriber, Editor, String, CodeTipRequestType)` | 获取提示 |

#### private 方法 (内部实现) — 5个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `jA(Editor, Span)` | 内部处理 |
| `void` | `ib(String, List<String>, EditorRequestService, Flow$Subscriber, ResponseStreamDto$ResponseData)` | 内部处理 |
| `String` | `Rb(ResponseStreamDto$ResponseData, CodeTipRequestDto)` | 内部处理 |
| `void` | `Xb(Editor, Span)` | 内部处理 |
| `void` | `static enum(int)` | 混淆开关方法 |
| `void` | `static iC(Editor, MessageDto)` | 内部处理 |
| `void` | `Ac(String, String, AICodeSettingsState, Editor, CodeTipRequestType, Span, String, boolean)` | 内部处理 |
| `void` | `static Fa(Editor, List<CodeInfoDto$RangeDTO>)` | 内部处理 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public RequestTipServiceImpl()` | 公有构造器 |

### 内部类

#### RequestTipServiceImpl$j — TypeToken子类
```java
public class com.aicode.service.editor.RequestTipServiceImpl$j extends TypeToken<String[]> {
  public final RequestTipServiceImpl enum;
}
```

---

## 18. EditorManagerServiceImpl — 编辑器管理

**类签名:** `public class com.aicode.service.editor.EditorManagerServiceImpl implements EditorManagerService`
**源文件:** `ec` (混淆后)
**包路径:** `com.aicode.service.editor`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `private static final` | `Key<Boolean>` | `new` | Key(混淆名) |
| `private static final` | `org.slf4j.Logger` | `long` | 日志器(混淆名) |
| `public static final` | `Key<RequestResultList>` | `CACHE_KEY_LAST_REQUEST` | 缓存Key |
| `private static final` | `Set<String>` | `super` | 集合(混淆名) |
| `public static final` | `AtomicInteger` | `docChangeCount` | 文档变更计数 |
| `public static` | `Boolean` | `for` | 标志(混淆名) |
| `public static final` | `boolean` | `if` | 混淆开关标志 |
| `public static final` | `String` | `ACCEPT_CODE_FOR_LINE` | 行级接受代码Key |
| `private` | `Integer` | `case` | 字段(混淆名) |
| `public static` | `OperateActionEnum` | `final` | 操作枚举(混淆名) |
| `private static final` | `Key<Boolean>` | `try` | Key(混淆名) |
| `public static final` | `Map<String, String>` | `keyMap` | 键映射 |
| `public final` | `CancelRequestTip` | `requestAlarm` | 请求取消闹钟 |
| `public static final` | `Key<RequestResultList>` | `KEY_LAST_REQUEST` | 最后请求Key |
| `private` | `Integer` | `float` | 字段(混淆名) |
| `public static final` | `int` | `DELAY_MILLIS` | 延迟毫秒数 |
| `public static` | `String` | `byte` | 字段(混淆名) |
| `public static final` | `String` | `ACCEPT_CODE_FOR_WORD` | 词级接受代码Key |
| `public static final` | `Key<Boolean>` | `enum` | Key(混淆名) |

### 方法

#### public 方法 (外部API) — 18个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `showPreviousInlaySet(Editor)` | 显示上一组Inlay |
| `boolean` | `acceptTipForLine(Editor)` | 行级接受提示 |
| `static int` | `countLeadingSpaces(String)` | 计算前导空格 |
| `static void` | `acceptCount(Project, int, int, String, Document, CodeCollectEnum)` | 接受计数(6参数) |
| `void` | `disposeTips(Editor, OperateActionEnum)` | 释放提示 |
| `boolean` | `hasNextInlaySet(Editor)` | 是否有下一组Inlay |
| `int` | `countTrailingSpaces(String)` | 计算尾部空格 |
| `List<TipRenderer>` | `getInlays(Editor, int, int)` | 获取Inlay列表 |
| `boolean` | `acceptTip(Editor)` | 接受提示 |
| `static List<String>` | `findCommonContinuousSubstrings(String, String)` | 查找公共连续子串 |
| `boolean` | `hasCacheData(Editor, char)` | 是否有缓存数据 |
| `boolean` | `isAvailable(Editor)` | 编辑器是否可用 |
| `static void` | `acceptCount(Project, String, String, CodeCollectEnum)` | 接受计数(4参数) |
| `boolean` | `hasPreviousInlaySet(Editor)` | 是否有上一组Inlay |
| `static void` | `acceptCount(Editor, int, int, CodeCollectEnum)` | 接受计数(4参数,Editor版) |
| `static Stack<Integer>` | `findMatchingRightParentheses(String)` | 查找匹配右括号 |
| `boolean` | `acceptWordTip(Editor)` | 词级接受提示 |
| `void` | `acceptTip(Project, Editor, EditorRequestService, CodeInlayList)` | 接受提示(完整版) |
| `void` | `dispose()` | 释放资源 |
| `void` | `editorChanged(Editor, int, CodeTipRequestType, boolean)` | 编辑器变更回调 |
| `void` | `cancelTipRequests(Editor)` | 取消提示请求 |
| `void` | `showNextInlaySet(Editor)` | 显示下一组Inlay |
| `void` | `acceptWordTip(Project, Editor, EditorRequestService, CodeInlayList)` | 词级接受提示(完整版) |
| `int` | `countTipInlays(Editor, TextRange, boolean, boolean, boolean, boolean)` | 计算提示Inlay数量 |

#### private 方法 (内部实现) — 36个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `boolean` | `fC(CodeInlayList, EditorRequestService, Editor, boolean, OperateActionEnum)` | 内部判断 |
| `void` | `static HC(Editor, EditorRequestService, CodeEditorInlay, String, InlayModel, int)` | 内部处理 |
| `boolean` | `static GB(Inlay)` | 内部判断 |
| `void` | `NC(CodeInlayList, EditorRequestService, Editor, boolean, OperateActionEnum)` | 内部处理 |
| `Inlay<TipRenderer>` | `Za(EditorRequestService, Editor, CodeInlayList, TipInlayRenderer, int)` | 内部处理 |
| `boolean` | `zB(Document, int, CodeTipRequestType, RequestResultList)` | 内部判断 |
| `void` | `iB(long)` | 内部处理 |
| `void` | `static Oc(Document, int)` | 内部处理 |
| `boolean` | `static LC(String, String, int)` | 内部判断 |
| `boolean` | `mc(EditorRequestService, Editor)` | 内部判断 |
| `boolean` | `static hb(String)` | 内部判断 |
| `void` | `mA(Editor)` | 内部处理 |
| `void` | `Aa(CodeInlayList, EditorRequestService, Editor)` | 内部处理 |
| `void` | `static gc(char[], int, String, Map, Editor, EditorRequestService, InlayModel, CodeEditorInlay, int, Map)` | 内部处理 |
| `String` | `zc()` | 内部处理 |
| `void` | `lc(CodeInlayList, EditorRequestService, Editor, boolean, OperateActionEnum)` | 内部处理 |
| `void` | `jb(Project, Editor, EditorRequestService, CodeInlayList, String, String)` | 内部处理 |
| `void` | `vc(Project, Editor, String, String, boolean)` | 内部处理 |
| `void` | `qB(EditorRequestService, Editor, int, CodeEditorInlay, List<String>)` | 内部处理 |
| `void` | `static enum(int)` | 混淆开关方法 |
| `boolean` | `static ta(Inlay)` | 内部判断 |
| `boolean` | `PA(Editor, RequestResultList, Document)` | 内部判断 |
| `void` | `MA(EditorRequestService, Editor, int, CodeEditorInlay, String)` | 内部处理 |
| `boolean` | `qa(Editor, RequestResultList, Document, boolean)` | 内部判断 |
| `String` | `static JA(int)` | 内部处理 |
| `String` | `la()` | 内部处理 |
| `boolean` | `QB(Editor)` | 内部判断 |
| `void` | `ma(boolean, Editor, EditorRequestService, CodeTipRequestType, Consumer<CodeInlayList>)` | 内部处理 |
| `void` | `XA(EditorRequestService, Editor, CodeTipRequestType, Consumer)` | 内部处理 |
| `boolean` | `SC()` | 内部判断 |
| `boolean` | `OC(Editor, int)` | 内部判断 |
| `String` | `HA(Editor)` | 内部处理 |
| `void` | `oc(Editor, RequestResultList)` | 内部处理 |
| `void` | `Mc(Project, Editor, EditorRequestService, CodeInlayList, String, String, Span, MessageDto)` | 内部处理 |
| `String` | `TA(CodeInlayList)` | 内部处理 |
| `void` | `bA(CodeInlayList, EditorRequestService, Editor, boolean, OperateActionEnum)` | 内部处理 |
| `String` | `Na(String)` | 内部处理 |
| `void` | `dA(EditorRequestService, Editor, CodeTipRequestType, Consumer)` | 内部处理 |
| `void` | `Ra(EditorRequestService, Editor, RequestResultList, CodeInlayList)` | 内部处理 |
| `void` | `ZB(List<TipRenderer>)` | 内部处理 |
| `boolean` | `Cb(Editor)` | 内部判断 |
| `Flow$Subscriber` | `da(Editor, EditorRequestService, Consumer<CodeInlayList>, long)` | 内部处理 |
| `void` | `uA(RequestResultList, EditorRequestService, Editor, CodeInlayList)` | 内部处理 |
| `void` | `Fb(Editor, Document, String, boolean, String, String, Project)` | 内部处理 |
| `ResponseStreamDto$ResponseData` | `pb(CodeInlayList)` | 内部处理 |
| `boolean` | `mB(CodeInlayList, EditorRequestService, Editor, boolean, OperateActionEnum)` | 内部判断 |
| `void` | `Pb(Editor, Runnable)` | 内部处理 |
| `void` | `Db(CodeInlayList, EditorRequestService, Editor, boolean, OperateActionEnum)` | 内部处理 |
| `boolean` | `fb(Document)` | 内部判断 |
| `boolean` | `Nc(Editor, RequestResultList, Document)` | 内部判断 |
| `String` | `static aB(int)` | 内部处理 |
| `String` | `MC(Editor)` | 内部处理 |
| `void` | `Ka(Editor, EditorRequestService, CodeTipRequestType, Consumer<CodeInlayList>)` | 内部处理 |
| `boolean` | `static nC(boolean, TextRange, Inlay)` | 内部判断 |
| `boolean` | `Xc(Editor, List<CodeInlayList>)` | 内部判断 |
| `boolean` | `lb(Editor, int)` | 内部判断 |
| `List<String>` | `Ba(String, String)` | 内部处理 |
| `boolean` | `static Yc(String, String)` | 内部判断 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public EditorManagerServiceImpl()` | 公有构造器 |

### 内部类

#### EditorManagerServiceImpl$B — 混淆开关表
```java
public class com.aicode.service.editor.EditorManagerServiceImpl$B {
  public static final int[] enum;
}
```

#### EditorManagerServiceImpl$F — Flow订阅者
```java
public class com.aicode.service.editor.EditorManagerServiceImpl$F
    implements Flow$Subscriber<List<CodeInlayList>> {
  public final EditorRequestService super;
  public final EditorManagerServiceImpl for;
  public static final boolean if;
  public final long case;
  public final Editor final;
  private volatile Flow$Subscription try;
  public final Consumer float;
  private volatile boolean byte;
  public final AtomicBoolean enum;

  public void onComplete();
  public void onError(Throwable);
  public void onNext(Object);
  public void KB(List<CodeInlayList>);
  public void onSubscribe(Flow$Subscription);
}
```
响应式流订阅者，用于处理代码补全提示的异步数据流。实现 `Flow.Subscriber` 接口，支持背压控制。

---

## 19. OpenTelemetryService — 遥测服务

**类签名:** `public class com.aicode.apm.OpenTelemetryService`
**源文件:** `rn` (混淆后)
**包路径:** `com.aicode.apm`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `private static final` | `org.slf4j.Logger` | `enum` | 日志器(混淆名) |
| `public` | `io.opentelemetry.api.trace.Span` | `parentSpan` | OpenTelemetry父Span |

### 方法

#### public 方法 (外部API) — 3个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `static OpenTelemetryService` | `getInstance()` | 获取单例实例 |
| `synchronized void` | `handApmConfig(JsonObject)` | 处理APM配置(同步) |

#### private 方法 (内部实现) — 1个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `boolean` | `kd(String)` | 内部判断 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public OpenTelemetryService()` | 公有构造器 |

---

## 20. DiffService — Diff 服务

**类签名:** `public class com.aicode.diff.DiffService`
**源文件:** `xk` (混淆后)
**包路径:** `com.aicode.diff`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `private static final` | `String` | `try` | 字段(混淆名) |
| `public static` | `Key<VirtualFile>` | `DIFF_FILEPATH_LEFT` | Diff左侧文件Key |
| `private static final` | `String` | `float` | 字段(混淆名) |
| `private static final` | `String` | `byte` | 字段(混淆名) |
| `public static` | `Key<VirtualFile>` | `DIFF_FILEPATH_RIGHT` | Diff右侧文件Key |
| `private static final` | `com.intellij.openapi.diagnostic.Logger` | `enum` | 日志器(混淆名) |
| `public static final` | `String` | `tempDirectoryName` | 临时目录名 |

### 方法

#### public 方法 (外部API) — 7个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static replaceTextInVirtualFile(Project, VirtualFile, int, int, String)` | 替换虚拟文件中的文本 |
| `void` | `closeDiffViewIfAlreadyOpened(Project)` | 关闭已打开的Diff视图 |
| `void` | `static openDiff(Project, String, Document, int, int)` | 打开Diff视图 |
| `void` | `openInlineChatDiff(Editor, String, Document, int, int)` | 打开内联聊天Diff |
| `void` | `static copyFile(String, String, String, String)` | 复制文件 |
| `void` | `static replaceTextInFile(VirtualFile, int, int, String)` | 替换文件中的文本 |
| `static Document` | `getDocument(Editor)` | 获取文档 |
| `void` | `openDiffViewForAICode(Project, String, Editor)` | 打开AI代码Diff视图 |

#### private 方法 (内部实现) — 3个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static YC(Document, VirtualFile)` | 内部处理 |
| `void` | `static Vd(Document, int, int, String)` | 内部处理 |
| `void` | `static enum(int)` | 混淆开关方法 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public DiffService()` | 公有构造器 |

---

## 附录A: 混淆模式分析

### 混淆开关方法模式

几乎所有混淆类都包含一个 `private static void enum(int)` 方法，这是混淆器的开关方法。对应的内部类（如 `ChatService$Ia`、`CommonService$Ha` 等）包含两个 `static final int[]` 数组（`byte` 和 `enum`），用于在运行时解析混淆后的方法名。

### 字段命名混淆

混淆后的字段名使用 Java 关键字和常见词作为名称，包括:
- `byte`, `enum`, `float`, `long`, `try`, `for`, `if`, `case`, `super`, `new`, `final`

这是典型的 ProGuard/R8 混淆策略，利用 Java class 文件允许关键字作为字段名的特性来增加逆向难度。

### 混淆方法名模式

私有方法名使用2个字母的随机组合（如 `iE`, `Ye`, `ZC`, `bF`, `Ad` 等），而公共API方法名保留语义化名称（如 `handleAction`, `send2Agent`, `getTalkHistory`）。

### 未混淆的类

`TemplateRequestService` 是唯一未混淆的核心服务类，保留了完整的原始方法名和字段名，源文件名为 `TemplateRequestService.java`。这可能是由于该类逻辑过于复杂，混淆后难以保证正确性，或者是遗漏。

---

## 附录B: 服务间依赖关系

### Agent通信层

```
PluginWebsocketClient (WebSocket通信)
    |
    +-- RestartableAgentProcessService (进程管理)
    |       implements PluginAgentProcessService, Disposable
    |
    +-- ChatService (聊天)
    +-- CodeCompleteService (代码补全)
    +-- InlineChatCommandService (内联聊天命令)
    +-- GitReviewService (Git评审)
    +-- SqlService (SQL)
    +-- CodeCheckService (代码检查)
    +-- CodeSearchService (代码搜索)
    +-- UserService (用户)
    +-- BatchUnitTestService (批量单测)
    +-- CommonService (通用)
```

### 编辑器集成层

```
RequestTipServiceImpl (请求提示)
    implements RequestTipService, Disposable
    |
    +-- EditorManagerServiceImpl (编辑器管理)
    |       implements EditorManagerService
    |       |
    |       +-- EditorManagerServiceImpl$F (Flow订阅者)
    |
    +-- InlineChatService (内联聊天面板)
    |       implements Disposable
    |       |
    |       +-- InlineChatService$Companion (Kotlin伴生对象)
    |
    +-- InlineChatHandleService (内联聊天处理)
    +-- InlineChatStreamHandleService (内联聊天流式处理)
```

### 辅助服务层

```
DiffService (Diff对比)
OpenTelemetryService (APM遥测)
TemplateRequestService (模板请求/单测生成)
```

---

## 附录C: handleAction/handleAgentAction 方法统一入口

所有核心服务类都实现了 `handleAction` 和/或 `handleAgentAction` 方法，这是 iFlyCode 插件的消息分发机制:

| 类 | handleAction | handleAgentAction |
|----|-------------|-------------------|
| ChatService | `handleAction(WebViewDataTypeEnum, JsonObject, String, Project)` | `handleAgentAction(CommandEnum, JsonObject, String, MessageDto, Project)` |
| CodeCompleteService | - | `handleAgentAction(CommandEnum, JsonObject, MessageDto, String, Project)` |
| CommonService | `handleAction(WebViewDataTypeEnum, JsonObject, String, Project)` | - |
| InlineChatCommandService | - | `handleAgentAction(String, CommandEnum, Project, MessageDto, JsonObject)` + `handleAgentAction(Project, MessageDto, String, CommandEnum)` |
| GitReviewService | `handleAction(WebViewDataTypeEnum, JsonObject, Project)` | `handleAgentAction(CommandEnum, JsonObject, Project)` |
| SqlService | `handleAction(WebViewDataTypeEnum, JsonObject, Project)` | `handleAgentAction(CommandEnum, String, JsonObject, Project)` |
| CodeCheckService | `handleAction(WebViewWindowPanel, WebViewDataTypeEnum, JsonObject, Project)` | `handleAgentAction(CommandEnum, JsonObject, MessageDto, String, Project)` |
| CodeSearchService | `handleAction(WebViewWindowPanel, WebViewDataTypeEnum, JsonObject, String, Project)` | `handleAgentAction(CommandEnum, JsonObject, String, Project)` |
| UserService | `handleAction(WebViewDataTypeEnum, Project)` | `handleAgentAction(CommandEnum, JsonObject, String, Object, Project)` |
| BatchUnitTestService | `handleAction(WebViewDataTypeEnum, String, Project)` | `handleAgentAction(CommandEnum, JsonObject, MessageDto, String, Project)` |
| TemplateRequestService | - | `handleAgentAction(CommandEnum, JsonObject, MessageDto, String, Project)` (synchronized) |

- `handleAction`: 从 WebView 前端触发的动作入口
- `handleAgentAction`: 从 Agent 后端响应触发的动作入口
