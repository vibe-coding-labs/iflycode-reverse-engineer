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
| `public static` | `List&lt;String&gt;` | `NEED_CODE_LIST` | 需要代码的命令列表 |
| `private static final` | `org.slf4j.Logger` | `enum` | 日志器(混淆名) |
| `public static` | `ConcurrentNavigableMap<String, String>` | `SESSION_ID` | 会话ID映射 |

### 方法

#### public 方法 (外部API) — 31个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `List&lt;String&gt;` | `static getTalkList(JsonObject)` | 获取对话列表 |
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
public class com.aicode.agent.service.ChatService$Ia &#123;
  public static final int[] byte;
  public static final int[] enum;
&#125;
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
public class com.aicode.agent.service.CodeCompleteService$ja &#123;
  public static final int[] enum;
&#125;
```
混淆开关表内部类。

---
