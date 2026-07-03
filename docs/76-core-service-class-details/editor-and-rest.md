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
| `List&lt;CodeInlayList&gt;` | `fetchCachedTips(EditorRequestService)` | 获取缓存提示 |
| `String` | `getFileExtensionFromEditor(Editor)` | 获取编辑器文件扩展名 |
| `void` | `dealAgentTips(String, JsonObject, Project)` | 处理Agent提示 |
| `void` | `fetchTips(EditorRequestService, Flow$Subscriber, Editor, String, CodeTipRequestType)` | 获取提示 |

#### private 方法 (内部实现) — 5个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `jA(Editor, Span)` | 内部处理 |
| `void` | `ib(String, List&lt;String&gt;, EditorRequestService, Flow$Subscriber, ResponseStreamDto$ResponseData)` | 内部处理 |
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
public class com.aicode.service.editor.RequestTipServiceImpl$j extends TypeToken<String[]> &#123;
  public final RequestTipServiceImpl enum;
&#125;
```

---

## 18. EditorManagerServiceImpl — 编辑器管理

**类签名:** `public class com.aicode.service.editor.EditorManagerServiceImpl implements EditorManagerService`
**源文件:** `ec` (混淆后)
**包路径:** `com.aicode.service.editor`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `private static final` | `Key&lt;Boolean&gt;` | `new` | Key(混淆名) |
| `private static final` | `org.slf4j.Logger` | `long` | 日志器(混淆名) |
| `public static final` | `Key&lt;RequestResultList&gt;` | `CACHE_KEY_LAST_REQUEST` | 缓存Key |
| `private static final` | `Set&lt;String&gt;` | `super` | 集合(混淆名) |
| `public static final` | `AtomicInteger` | `docChangeCount` | 文档变更计数 |
| `public static` | `Boolean` | `for` | 标志(混淆名) |
| `public static final` | `boolean` | `if` | 混淆开关标志 |
| `public static final` | `String` | `ACCEPT_CODE_FOR_LINE` | 行级接受代码Key |
| `private` | `Integer` | `case` | 字段(混淆名) |
| `public static` | `OperateActionEnum` | `final` | 操作枚举(混淆名) |
| `private static final` | `Key&lt;Boolean&gt;` | `try` | Key(混淆名) |
| `public static final` | `Map<String, String>` | `keyMap` | 键映射 |
| `public final` | `CancelRequestTip` | `requestAlarm` | 请求取消闹钟 |
| `public static final` | `Key&lt;RequestResultList&gt;` | `KEY_LAST_REQUEST` | 最后请求Key |
| `private` | `Integer` | `float` | 字段(混淆名) |
| `public static final` | `int` | `DELAY_MILLIS` | 延迟毫秒数 |
| `public static` | `String` | `byte` | 字段(混淆名) |
| `public static final` | `String` | `ACCEPT_CODE_FOR_WORD` | 词级接受代码Key |
| `public static final` | `Key&lt;Boolean&gt;` | `enum` | Key(混淆名) |

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
| `List&lt;TipRenderer&gt;` | `getInlays(Editor, int, int)` | 获取Inlay列表 |
| `boolean` | `acceptTip(Editor)` | 接受提示 |
| `static List&lt;String&gt;` | `findCommonContinuousSubstrings(String, String)` | 查找公共连续子串 |
| `boolean` | `hasCacheData(Editor, char)` | 是否有缓存数据 |
| `boolean` | `isAvailable(Editor)` | 编辑器是否可用 |
| `static void` | `acceptCount(Project, String, String, CodeCollectEnum)` | 接受计数(4参数) |
| `boolean` | `hasPreviousInlaySet(Editor)` | 是否有上一组Inlay |
| `static void` | `acceptCount(Editor, int, int, CodeCollectEnum)` | 接受计数(4参数,Editor版) |
| `static Stack&lt;Integer&gt;` | `findMatchingRightParentheses(String)` | 查找匹配右括号 |
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
| `Inlay&lt;TipRenderer&gt;` | `Za(EditorRequestService, Editor, CodeInlayList, TipInlayRenderer, int)` | 内部处理 |
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
| `void` | `qB(EditorRequestService, Editor, int, CodeEditorInlay, List&lt;String&gt;)` | 内部处理 |
| `void` | `static enum(int)` | 混淆开关方法 |
| `boolean` | `static ta(Inlay)` | 内部判断 |
| `boolean` | `PA(Editor, RequestResultList, Document)` | 内部判断 |
| `void` | `MA(EditorRequestService, Editor, int, CodeEditorInlay, String)` | 内部处理 |
| `boolean` | `qa(Editor, RequestResultList, Document, boolean)` | 内部判断 |
| `String` | `static JA(int)` | 内部处理 |
| `String` | `la()` | 内部处理 |
| `boolean` | `QB(Editor)` | 内部判断 |
| `void` | `ma(boolean, Editor, EditorRequestService, CodeTipRequestType, Consumer&lt;CodeInlayList&gt;)` | 内部处理 |
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
| `void` | `ZB(List&lt;TipRenderer&gt;)` | 内部处理 |
| `boolean` | `Cb(Editor)` | 内部判断 |
| `Flow$Subscriber` | `da(Editor, EditorRequestService, Consumer&lt;CodeInlayList&gt;, long)` | 内部处理 |
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
| `void` | `Ka(Editor, EditorRequestService, CodeTipRequestType, Consumer&lt;CodeInlayList&gt;)` | 内部处理 |
| `boolean` | `static nC(boolean, TextRange, Inlay)` | 内部判断 |
| `boolean` | `Xc(Editor, List&lt;CodeInlayList&gt;)` | 内部判断 |
| `boolean` | `lb(Editor, int)` | 内部判断 |
| `List&lt;String&gt;` | `Ba(String, String)` | 内部处理 |
| `boolean` | `static Yc(String, String)` | 内部判断 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public EditorManagerServiceImpl()` | 公有构造器 |

### 内部类

#### EditorManagerServiceImpl$B — 混淆开关表
```java
public class com.aicode.service.editor.EditorManagerServiceImpl$B &#123;
  public static final int[] enum;
&#125;
```

#### EditorManagerServiceImpl$F — Flow订阅者
```java
public class com.aicode.service.editor.EditorManagerServiceImpl$F
    implements Flow$Subscriber<List&lt;CodeInlayList&gt;> &#123;
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
  public void KB(List&lt;CodeInlayList&gt;);
  public void onSubscribe(Flow$Subscription);
&#125;
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
| `public static` | `Key&lt;VirtualFile&gt;` | `DIFF_FILEPATH_LEFT` | Diff左侧文件Key |
| `private static final` | `String` | `float` | 字段(混淆名) |
| `private static final` | `String` | `byte` | 字段(混淆名) |
| `public static` | `Key&lt;VirtualFile&gt;` | `DIFF_FILEPATH_RIGHT` | Diff右侧文件Key |
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
