# 91. Service 包完整反编译分析

## 概述

`com.aicode.service` 包是 iFlyCode 插件代码补全子系统的核心，包含 35 个类文件（34 个逻辑类 + 1 个匿名内部类），分布在三个子包中：

- `com.aicode.service` — 17 个顶层接口/类
- `com.aicode.service.editor` — 16 个实现类
- `com.aicode.service.response` — 1 个 DTO 类

源文件名均被混淆为单字母/双字母（如 `r`, `ec`, `zc`, `oc` 等）。

---

## 一、顶层接口/类完整反编译

### 1. EditorManagerService (接口, 源文件: r)

```java
public interface EditorManagerService extends Disposable {
    static final Logger LOG = Logger.getInstance(EditorManagerService.class);

    // --- 核心补全控制方法 ---
    boolean isAvailable(Editor editor);
    void showNextInlaySet(Editor editor);
    boolean hasPreviousInlaySet(Editor editor);
    boolean hasNextInlaySet(Editor editor);
    void editorChanged(Editor editor, int offset, CodeTipRequestType type, boolean forced);
    boolean acceptTip(Editor editor);
    void showPreviousInlaySet(Editor editor);
    List<TipRenderer> getInlays(Editor editor, int start, int end);
    void cancelTipRequests(Editor editor);
    boolean acceptTipForLine(Editor editor);
    boolean acceptWordTip(Editor editor);
    void disposeTips(Editor editor, OperateActionEnum action);
    void acceptTip(Project project, Editor editor, EditorRequestService request, CodeInlayList inlayList);
    int countTipInlays(Editor editor, TextRange range, boolean b1, boolean b2, boolean b3, boolean b4);
    boolean hasCacheData(Editor editor, char c);
    void acceptWordTip(Project project, Editor editor, EditorRequestService request, CodeInlayList inlayList);

    // --- 默认方法 ---
    default void editorChanged(Editor editor, CodeTipRequestType type, boolean forced) {
        // 1. 空检查 editor 和 type
        // 2. 检查 API Key 是否为空 (PluginStartupActivity.getApiKey())
        // 3. 检查 enableCodeComplete 设置
        // 4. 在 readAction 中执行: editorChanged(editor, editor.caretModel.offset, type, forced)
    }

    default boolean hasTipInlays(Editor editor) {
        // isAvailable(editor) && countTipInlays(editor, TextRange(0, doc.textLength), true, true, true, true) > 0
    }

    static EditorManagerService getInstance() {
        return ApplicationManager.getApplication().getService(EditorManagerService.class);
    }
}
```

**关键分析**:
- 继承 `Disposable`，作为 IntelliJ 应用级服务注册
- `editorChanged` 是补全请求的入口，支持 offset 和 type 两个维度
- `acceptTip` / `acceptWordTip` / `acceptTipForLine` 三种接受补全方式
- `showNextInlaySet` / `showPreviousInlaySet` 实现多候选补全循环
- H() 调用: `RequestTimeoutException.H()`, `OpenTelemetryUtil.H()` — 字符串解混淆

---

### 2. RequestTipService (接口, 源文件: c)

```java
public interface RequestTipService {
    EditorRequestService createRequest(Editor editor, int offset, TipType type);
    boolean isAvailable(Editor editor);
    List<CodeInlayList> fetchCachedTips(EditorRequestService request);
    void fetchTips(EditorRequestService request, Flow.Subscriber<List<CodeInlayList>> subscriber,
                   Editor editor, String requestId, CodeTipRequestType type);
    void fetchInlineChatContent(EditorRequestService request, Flow.Subscriber<List<CodeInlayList>> subscriber,
                                Editor editor, String requestId, CodeTipRequestType type);
    void dealStreamAgentTips(String requestId, ResponseStreamDto data, Project project, MessageDto message);
    void dealAgentTips(String requestId, JsonObject data, Project project);
    EditorRequestService createInlineChatRequest(Editor editor, int offset, TipType type);

    static RequestTipService getInstance() {
        return ApplicationManager.getApplication().getService(RequestTipService.class);
    }
}
```

**关键分析**:
- `createRequest` / `createInlineChatRequest` — 两种请求创建方式
- `fetchTips` — 使用 Reactive Flow (Flow.Subscriber) 流式获取补全
- `fetchInlineChatContent` — Inline Chat 专用内容获取
- `dealStreamAgentTips` / `dealAgentTips` — Agent 模式补全处理（流式/非流式）
- `fetchCachedTips` — 缓存补全结果获取

---

### 3. EditorRequestService (接口, 源文件: f)

```java
public interface EditorRequestService extends RequestCancellable {
    AICodeLanguageInfo getFileLanguage();
    LineInfo getLineInfo();
    long getDocumentModificationSequence();
    String getFileNameSuffix();
    boolean equalsRequest(EditorRequestService other);
    int getRequestId();
    Disposable getDisposable();
    void setSessionController(SessionController controller);
    SessionController getSessionController();
    TipType getCompletionType();
    void setOffset(int offset);
    Project getProject();
    int getOffset();
    boolean isUseTabIndents();
    long getRequestTimestamp();
    int getTabWidth();
    boolean isSelected();
    String getDocumentContent();
    String getFileName();

    default String getCurrentDocumentPrefix() {
        return getDocumentContent().substring(0, getOffset());
    }
}
```

**关键分析**:
- 继承 `RequestCancellable`，支持请求取消
- `getCurrentDocumentPrefix()` — 获取光标前文档前缀，用于补全上下文
- `getSessionController()` — Inline Chat 会话控制器
- `getCompletionType()` — 补全类型（行/多行/单词等）
- `equalsRequest()` — 请求去重比较
- H() 调用: `NewFileUtils.H()`, `InlineChatStatusServiceKt.H()`

---

### 4. EditorSupport (接口, 源文件: k)

```java
public interface EditorSupport {
    static final ExtensionPointName<EditorSupport> EP;

    boolean isCodeTipsEnabled(Editor editor);

    static boolean isEditorCodeTipsSupported(Editor editor) {
        if (editor == null) return true;  // 空编辑器视为支持（不阻止）
        if (!EP.hasAnyExtensions()) return true;
        if (EP.findFirstSafe(e -> e.isCodeTipsEnabled(editor)) != null) return false;
        return true;
    }

    static {}  // EP = ExtensionPointName.create(H("..."))
}
```

**关键分析**:
- IntelliJ Extension Point 机制，允许其他插件控制补全是否启用
- `isEditorCodeTipsSupported` — 全局检查：有扩展点且任一返回 false 则禁用
- H() 调用: `AICodeStringUtil.H()`, `ChatInputController.H()`

---

### 5. CodeEditorInlay (接口, 源文件: z)

```java
public interface CodeEditorInlay {
    List<String> getLines();
    int getEditorOffset();
    void setEditorOffset(int offset);
    void setType(CodeTipType type);
    CodeTipType getType();
    void setLines(List<String> lines);

    default boolean isEmptyTip() {
        return getLines().isEmpty() ||
               (getLines().size() == 1 && getLines().get(0).isEmpty());
    }
}
```

**关键分析**:
- 表示单个 Inlay 元素，包含行内容、偏移量和类型
- `isEmptyTip()` — 空补全判断：无行或仅一行空字符串

---

### 6. CodeInlayList (接口, 源文件: p)

```java
public interface CodeInlayList extends Iterable<CodeEditorInlay> {
    boolean isEmpty();
    void setData(ResponseStreamDto.ResponseData data);
    void setRemoveBlank(boolean removeBlank);
    List<CodeEditorInlay> getInlays();
    TextRange getReplacementRange();
    int getOffset();
    boolean isRemoveBlank();
    void setReplacementText(String text);
    CodeTip getAICodeTip();
    ResponseStreamDto.ResponseData getData();
    String getReplacementText();
    void setReplacementRange(TextRange range);
}
```

**关键分析**:
- 补全结果集合，可迭代 `CodeEditorInlay`
- 关联 `ResponseStreamDto.ResponseData` — 流式响应数据
- `getReplacementRange()` / `getReplacementText()` — 文本替换范围和内容
- `isRemoveBlank()` — 是否移除空白行

---

### 7. CodeTip (接口, 源文件: n)

```java
public interface CodeTip {
    List<String> getTip();
    CodeTip withCompletion(List<String> lines);
    boolean isCached();
    CodeTip asCached();
}
```

**关键分析**:
- 补全内容抽象，支持不可变转换 (`withCompletion`)
- 缓存标记 (`isCached` / `asCached`)

---

### 8. ProjectService (接口, 源文件: s)

```java
public interface ProjectService {
    void flushLib(Project project);
}
```

**关键分析**:
- 项目级服务，仅一个方法 `flushLib` — 刷新项目库索引

---

### 9. TipCache (接口, 源文件: t)

```java
public interface TipCache {
    List<CodeTip> getLatest(String key);
    List<CodeTip> get(String key, boolean b);
    void clear();
    void updateLatest(String key, String value, boolean b);
    void add(String key, String value, boolean b, CodeTip tip);
    boolean isLatestPrefix(String key);
}
```

**关键分析**:
- 补全缓存接口，支持最新/历史缓存
- `isLatestPrefix()` — 判断输入是否为最新缓存的前缀（增量补全优化）
- `updateLatest()` — 更新最新缓存

---

### 10. TipRenderer (接口, 源文件: m)

```java
public interface TipRenderer extends EditorCustomElementRenderer {
    CodeTipType getType();
    List<String> getContentLines();
    Inlay<TipRenderer> getInlay();
}
```

**关键分析**:
- IntelliJ Inlay 渲染器接口
- 关联 `Inlay<TipRenderer>` — IDE 内嵌元素
- `getContentLines()` — 获取渲染内容行

---

### 11. TipReceivedMessage (接口, 源文件: x)

```java
public interface TipReceivedMessage {
    static final Topic<TipReceivedMessage> TOPIC = Topic.create(H("..."), TipReceivedMessage.class);

    void inlaysReceived(EditorRequestService request, List<CodeInlayList> inlays);
}
```

**关键分析**:
- IntelliJ MessageBus 消息主题
- 补全结果接收通知，发布到 `TipReceivedMessage.TOPIC`
- H() 调用: `ConditionalActionConfiguration.H()`

---

### 12. RequestsCancelledService (接口, 源文件: l)

```java
public interface RequestsCancelledService {
    static final Topic<RequestsCancelledService> TOPIC = Topic.create(H("..."), RequestsCancelledService.class);

    void requestsCancelled(int requestId);
}
```

**关键分析**:
- 请求取消通知主题
- 通过 requestId 标识被取消的请求
- H() 调用: `Maps.H()`

---

### 13. RequestCancellable (接口, 源文件: v)

```java
public interface RequestCancellable {
    static final RequestCancellable enum = new RequestCancellable$f();  // 空实现单例

    void cancel();
    boolean isCancelled();
}
```

**关键分析**:
- 请求可取消接口
- 静态 `enum` 字段是一个空实现（no-op），`isCancelled()` 永远返回 false，`cancel()` 为空操作

---

### 14. RequestCancellable$f (类, 源文件: v)

```java
public class RequestCancellable$f implements RequestCancellable {
    public boolean isCancelled() { return false; }
    public void cancel() { /* no-op */ }
}
```

---

### 15. RejectTipMessage (接口, 源文件: a)

```java
public interface RejectTipMessage {
    static final Topic<RejectTipMessage> TOPIC = Topic.create(H("..."), RejectTipMessage.class);

    void automaticCodeTipsRejected(EditorRequestService request);
}
```

**关键分析**:
- 补全拒绝通知主题
- 当自动补全被拒绝时发布
- H() 调用: `Maps.H()`

---

### 16. LanguageInfoSupport (接口, 源文件: d)

```java
public interface LanguageInfoSupport {
    static final ExtensionPointName<LanguageInfoSupport> EP = new ExtensionPointName<>(H("..."));

    AICodeLanguageInfo findVSCodeLanguageMapping(PsiFile file);
}
```

**关键分析**:
- VS Code 语言映射扩展点
- 将 IntelliJ PsiFile 映射到 AICodeLanguageInfo
- H() 调用: `OpenTelemetryUtil.H()`

---

### 17. ProcessStatusListener (接口, 源文件: b)

```java
public interface ProcessStatusListener {
    static final Topic<ProcessStatusListener> TOPIC = new Topic<>(ProcessStatusListener.class);

    void onAgentProcessRestart();
}
```

**关键分析**:
- Agent 进程重启监听
- 使用 Topic 但未指定显示名（直接传 Class）

---

### 18. BizResponse<T> (类, 源文件: xc, 子包: response)

```java
public class BizResponse<T> {
    private T float;           // obj (泛型数据)
    private String byte;       // resCode (响应码)
    private String enum;       // msg (消息)
    static final String RES_CODE_SUCCESS = H("...");  // 解混淆后为 "0"

    T getObj();
    void setObj(T obj);
    String getResCode();
    void setResCode(String code);
    String getMsg();
    void setMsg(String msg);
    boolean isSuccess() { return StrUtil.equals(H("..."), byte); }  // resCode == "0"
    boolean isFail() { return !isSuccess(); }
}
```

**关键分析**:
- 通用业务响应 DTO
- 成功码通过 H() 解混淆，实际值为 "0"
- 使用 Hutool 的 `StrUtil.equals()` 比较

---

## 二、Editor 子包实现类完整反编译

### 19. EditorManagerServiceImpl (类, 源文件: ec)

```java
public class EditorManagerServiceImpl implements EditorManagerService {
    // --- 静态字段 ---
    private static final Key<Boolean> new;           // 某个布尔标记
    private static final Logger long;                // SLF4J 日志器
    public static final Key<RequestResultList> CACHE_KEY_LAST_REQUEST;
    public static final Key<RequestResultList> KEY_LAST_REQUEST;
    private static final Key<Boolean> try;           // 另一个布尔标记
    private static final Set<String> super;           // 不支持的文件类型集合
    public static final Key<Boolean> enum;            // Inlay 显示标记

    // --- 实例字段 ---
    public static final AtomicInteger docChangeCount; // 文档变更计数器
    public static Boolean for;                        // 某个全局布尔
    public static final boolean if;                   // assert 开关
    public static final String ACCEPT_CODE_FOR_LINE;  // 行级接受操作 ID
    public static final String ACCEPT_CODE_FOR_WORD;  // 单词级接受操作 ID
    public static final int DELAY_MILLIS;             // 延迟毫秒数
    public static String byte;                        // 全局字符串状态
    private Integer case;                             // 当前索引
    private Integer float;                            // 最大显示索引
    public static OperateActionEnum final;            // 当前操作枚举
    public static final Map<String, String> keyMap;   // 键映射表
    public final CancelRequestTip requestAlarm;       // 请求取消定时器

    // --- 核心方法 ---
    void showPreviousInlaySet(Editor editor);
    // -> KEY_LAST_REQUEST.get(editor).getPrevCodeTip() -> fC(inlayList, request, editor, true, Cycling)

    void showNextInlaySet(Editor editor);
    // -> KEY_LAST_REQUEST.get(editor).getNextCodeTip() -> fC(inlayList, request, editor, true, Cycling)

    private boolean fC(CodeInlayList, EditorRequestService, Editor, boolean forced, OperateActionEnum);
    // 1. mc(request, editor) — 验证请求有效性
    // 2. mB(inlayList, request, editor, forced, action) — 执行显示

    private void NC(CodeInlayList, EditorRequestService, Editor, boolean, OperateActionEnum);
    // 核心渲染逻辑:
    // 1. 如果 forced=true，先 disposeTips
    // 2. 获取 InlayModel
    // 3. 遍历 CodeInlayList 中的每个 CodeEditorInlay
    // 4. 跳过 isEmptyTip() 的元素
    // 5. 处理 replacementRange:
    //    - 如果有替换范围，使用 matchSuffixSection 分割
    //    - 创建 TipInlayRenderer
    // 6. 根据 CodeTipType 分派渲染:
    //    case Inline:    addInlineElement(offset, relatesToPrecedingText, priority, renderer)
    //    case AfterLineEnd: addAfterLineEndElement(offset, relatesToPrecedingText, renderer)
    //    case Block:     addBlockElement(offset, showAbove, showIfCollapsed, priority, renderer)

    private static void HC(Editor, EditorRequestService, CodeEditorInlay, String, InlayModel, int);
    // 创建 TipInlayRenderer 并添加为 inline element

    private Inlay<TipRenderer> Za(EditorRequestService, Editor, CodeInlayList, TipInlayRenderer, int);
    // 高级 Inlay 创建:
    // 1. 获取当前行后缀文本
    // 2. 使用 CodeCheckUtil.matcher 检查括号匹配
    // 3. 使用 matchSuffixSection 分割重叠部分
    // 4. 创建多个 TipInlayRenderer 对应不同偏移

    private boolean mc(EditorRequestService, Editor);
    // 请求有效性验证:
    // 1. editor.isDisposed() 检查
    // 2. request.getDocumentModificationSequence() == doc 序列号
    // 3. EditorUtil.isSelectedEditor(editor) 检查

    private boolean mB(CodeInlayList, EditorRequestService, Editor, boolean, OperateActionEnum);
    // 显示补全:
    // 1. mc() 验证
    // 2. editor.isDisposed() 检查
    // 3. NC() 执行渲染

    boolean Xc(Editor, List<CodeInlayList>);
    // 检查是否已有相同偏移的 Inlay 显示中

    // --- 其他方法 ---
    boolean isAvailable(Editor editor);
    // 检查编辑器是否支持补全（非二进制文件、文件大小等）

    void editorChanged(Editor editor, int offset, CodeTipRequestType type, boolean forced);
    // 核心入口: 创建请求 -> fetchTips/fetchCachedTips -> 渲染

    boolean acceptTip(Editor editor);
    boolean acceptTipForLine(Editor editor);
    boolean acceptWordTip(Editor editor);
    void acceptTip(Project, Editor, EditorRequestService, CodeInlayList);
    void acceptWordTip(Project, Editor, EditorRequestService, CodeInlayList);
    void cancelTipRequests(Editor editor);
    void disposeTips(Editor editor, OperateActionEnum action);
    List<TipRenderer> getInlays(Editor editor, int start, int end);
    int countTipInlays(Editor editor, TextRange range, boolean b1, boolean b2, boolean b3, boolean b4);
    boolean hasCacheData(Editor editor, char c);
}
```

**关键分析**:
- `NC()` 是 Inlay 渲染的核心方法，按 `CodeTipType` 分三种渲染模式
- `mc()` 是请求有效性验证，检查文档修改序列号防止过期请求
- `fC()` / `mB()` 是显示/切换补全候选的入口
- `Za()` 处理行后缀与补全文本的重叠分割
- H() 调用: `EditorUtils.H()`, `Content.util.EditorUtils.H()`

---

### 20. EditorManagerServiceImpl$B (类, 源文件: ec)

```java
public class EditorManagerServiceImpl$B {
    public static final int[] enum;  // CodeTipType.ordinal() -> switch 映射表

    static {}  // 初始化 switch 映射: Inline=1, AfterLineEnd=2, Block=3
}
```

**关键分析**: 纯粹的 switch 表映射类，用于 `CodeTipType` 的 tableswitch 优化

---

### 21. EditorManagerServiceImpl$F (类, 源文件: ec)

```java
public class EditorManagerServiceImpl$F implements Flow.Subscriber<List<CodeInlayList>> {
    public final EditorRequestService super;         // 关联的请求
    public final EditorManagerServiceImpl for;       // 外部类引用
    public static final boolean if;                  // assert 开关
    public final long case;                          // 请求时间戳
    public final Editor final;                       // 关联的编辑器
    private volatile Flow.Subscription try;          // Reactive 订阅
    public final Consumer float;                     // 完成回调
    private volatile boolean byte;                   // 是否已结束
    public final AtomicBoolean enum;                  // 原子标记（防重入）

    void onNext -> KB(List<CodeInlayList>);
    // 1. 日志记录
    // 2. mc(request, editor) 验证请求有效性
    // 3. enum.compareAndSet(false, true) — 防重入
    // 4. 重置 KEY_LAST_REQUEST 中的 RequestResultList
    // 5. Xc(editor, inlays) 检查是否已有显示
    // 6. 如果没有，请求下一个: try.request(1)
    // 7. 发布 TipReceivedMessage.TOPIC 消息
    // 8. 如果有 Consumer 回调且数据已结束:
    //    -> invokeLater 执行回调，记录响应时间

    void onSubscribe(Flow.Subscription s);
    // 1. 保存订阅
    // 2. request(1) 请求第一个元素
    // 3. 注册 Disposer: 当 request.getDisposable() 销毁时取消订阅

    void onComplete();  // 空
    void onError(Throwable t);  // 静默

    private static void cc(Consumer, CodeInlayList, long, Editor);
    // 回调执行 + 响应时间日志
}
```

**关键分析**:
- Reactive Flow 订阅者，连接 RequestTipService 的流式输出与 EditorManagerServiceImpl 的渲染
- `AtomicBoolean enum` 防止多次 onNext 处理
- `Consumer float` 是完成回调，用于 Agent 模式下的延迟渲染
- 响应时间通过 `System.currentTimeMillis() - case` 计算

---

### 22. RequestTipServiceImpl (类, 源文件: zc)

```java
public class RequestTipServiceImpl implements RequestTipService, Disposable {
    public final TipCache cache;                    // SimpleCodeTipCache(32)
    private static final Logger final;              // SLF4J
    public String try;                              // 当前文件路径
    public static final Map<String, CodeTipRequestDto> CODE_TIP_MAP;  // ConcurrentHashMap
    public static final boolean float;              // assert 开关
    public static final Map<Project, String> LATEST_RESPONSE_DATA;    // ConcurrentHashMap
    public String byte;                              // 当前语言 ID
    public static final Object object;              // 同步锁
    public static final Map<Project, Map<String, Long>> LAST_REQUEST; // ConcurrentHashMap
    public Language enum;                            // 当前语言

    // --- 核心方法 ---
    EditorRequestService createRequest(Editor editor, int offset, TipType type);
    // 创建补全请求对象

    boolean isAvailable(Editor editor);
    // 1. 获取 PsiFile
    // 2. 检查是否 PsiBinaryFile
    // 3. 检查 FileType.isBinary()
    // 4. FileSizeUtil.isSupported(virtualFile)

    private void jA(Editor editor, Span span);
    // 初始化语言信息:
    // 1. PsiDocumentManager.getPsiFile()
    // 2. 获取 Language, language.getID().toLowerCase()
    // 3. 获取 VirtualFile, 记录文件大小到 span
    // 4. 设置 try (文件路径)

    List<CodeInlayList> fetchCachedTips(EditorRequestService request);
    // 从缓存获取补全结果

    void fetchTips(EditorRequestService request, Flow.Subscriber<List<CodeInlayList>> subscriber,
                   Editor editor, String requestId, CodeTipRequestType type);
    // 流式获取补全:
    // 1. 检查缓存 isLatestPrefix
    // 2. 创建 SubmissionPublisher
    // 3. 调用 Agent 服务获取补全
    // 4. 处理结果并提交到 publisher

    private void ib(String requestId, List<String> lines, EditorRequestService request,
                    Flow.Subscriber<List<CodeInlayList>> subscriber, ResponseStreamDto.ResponseData data);
    // 核心补全处理管线:
    // 1. 创建 SubmissionPublisher
    // 2. 分割文档前缀 (按换行符)
    // 3. 遍历补全行:
    //    a. 替换制表符
    //    b. 去除前导空白（如果前缀最后一行非空）
    //    c. stripTrailing
    //    d. AgentCodeTip.FromString()
    //    e. replaceLeadingTabs
    //    f. setRequestId, setScene, setLanguage
    //    g. CodeTipUtil.createEditorCodeTip()
    //    h. 包装为 AgentCodeTipList
    // 4. 提交到 publisher
    // 5. 更新 AICodeStatus

    void dealStreamAgentTips(String requestId, ResponseStreamDto data, Project project, MessageDto message);
    // 流式 Agent 补全处理

    void dealAgentTips(String requestId, JsonObject data, Project project);
    // 非流式 Agent 补全处理

    private String Rb(ResponseStreamDto.ResponseData data, CodeTipRequestDto request);
    // 从响应数据提取替换文本:
    // 1. 如果 isEnded，取 lastReplacementText
    // 2. 否则取 data.getText()
    // 3. 处理特殊后缀 (如 "```" 结尾)

    EditorRequestService createInlineChatRequest(Editor editor, int offset, TipType type);
    // 创建 Inline Chat 请求（委托给 createRequest）

    void fetchInlineChatContent(...);
    // 获取 Inline Chat 内容
}
```

**关键分析**:
- `ib()` 是补全处理管线的核心，将原始文本行转换为 `CodeInlayList`
- `AgentCodeTip.FromString()` 将文本转为补全提示对象
- `CodeTipUtil.createEditorCodeTip()` 创建编辑器补全结构
- `AgentCodeTipList` 包装结果，关联 `ResponseStreamDto.ResponseData`
- H() 调用: `RequestCancelException.H()`, `CodeCompleteService.H()`, `GitReviewService.H()`

---

### 23. RequestTipServiceImpl$j (类, 源文件: zc)

```java
public class RequestTipServiceImpl$j extends TypeToken<String[]> {
    public final RequestTipServiceImpl enum;  // 外部类引用

    RequestTipServiceImpl$j(RequestTipServiceImpl outer);
}
```

**关键分析**: Gson TypeToken 子类，用于 JSON 反序列化 `String[]` 类型

---

### 24. InlayRendering (类, 源文件: oc)

```java
public final class InlayRendering {
    private static final Key<Map<Font, FontMetrics>> byte;  // 字体度量缓存
    private static final Method enum;                       // 反射方法缓存

    // --- 渲染方法 ---
    private static void PB(Graphics2D g, TextAttributes attr, double x, double y, double w, double h);
    // 绘制圆角矩形背景:
    // 1. 获取 backgroundColor
    // 2. setColor
    // 3. fillRoundRect(x, y, w, h, 1, 1)

    private static void ic(Graphics2D g, double x, double y, double h, int start, int end,
                          TextAttributes attr, Font font);
    // 绘制文本效果（下划线/删除线/波浪线）:
    // 根据 EffectType 分派:
    //   LINE_UNDERSCORE -> EffectPainter2D.LINE_UNDERSCORE
    //   BOLD_LINE_UNDERSCORE -> EffectPainter2D.BOLD_LINE_UNDERSCORE
    //   STRIKEOUT -> EffectPainter2D.STRIKEOUT
    //   WAVE_UNDERSCORE -> EffectPainter2D.WAVE_UNDERSCORE
    //   BOLD_DOTTED_LINE -> EffectPainter2D.BOLD_DOTTED_LINE

    static void paint(Graphics2D g, TipRenderer renderer, Rectangle r, Editor editor);
    // 主渲染入口:
    // 1. 获取 TextAttributes
    // 2. 绘制背景 (PB)
    // 3. 遍历内容行:
    //    a. 绘制文本
    //    b. 绘制效果 (ic)
    // 4. 处理多行 Block 类型

    static int getWidth(TipRenderer renderer, Editor editor);
    // 计算渲染宽度

    // --- 辅助方法 ---
    private static FontMetrics getFontMetrics(Graphics2D g, Font font);
    // 获取/缓存字体度量
}
```

**关键分析**:
- `InlayRendering` 是纯渲染工具类，处理 Graphics2D 绘制
- `PB()` 绘制圆角矩形背景（radius=1）
- `ic()` 根据 `EffectType` 分派 5 种效果绘制
- `paint()` 是主渲染入口，处理背景、文本、效果三层绘制
- 使用 `Key<Map<Font, FontMetrics>>` 缓存字体度量避免重复计算

---

### 25. InlayRendering$G (类, 源文件: oc)

```java
public class InlayRendering$G {
    public static final int[] enum;  // EffectType.ordinal() -> switch 映射表

    static {}  // LINE_UNDERSCORE=1, BOLD_LINE_UNDERSCORE=2, STRIKEOUT=3, WAVE_UNDERSCORE=4, BOLD_DOTTED_LINE=5
}
```

---

### 26. TipInlayRenderer (类, 源文件: dc)

```java
public class TipInlayRenderer implements TipRenderer {
    private Inlay<TipRenderer> if;          // 关联的 Inlay 对象
    private int case;                       // 缓存宽度
    private final String final;             // 行后缀文本（用于行内补全）
    private int try;                        // 行数
    private final CodeTipType float;        // 补全类型
    private final List<String> byte;        // 内容行
    private final TextAttributes enum;      // 文本属性

    // --- TipRenderer 接口实现 ---
    CodeTipType getType();
    List<String> getContentLines();
    Inlay<TipRenderer> getInlay();
    void setInlay(Inlay<TipRenderer> inlay);
    void setCachedWidth(int width);

    // --- EditorCustomElementRenderer 接口实现 ---
    int calcWidthInPixels(Inlay inlay);
    // -> InlayRendering.getWidth(this, editor)

    void paint(Graphics2D g, Rectangle r, Inlay inlay);
    // -> InlayRendering.paint(g, this, r, editor)

    // --- 静态工具方法 ---
    static List<String> replaceLeadingTabs(List<String> lines, EditorRequestService request);
    // 替换前导 Tab 为空格:
    // 1. 如果 request.isUseTabIndents()，直接返回
    // 2. 否则将每行前导 \t 替换为 request.getTabWidth() 个空格

    static int ya(CharSequence seq, int start, boolean b);
    // 计算前导空白长度（空格和Tab）
}
```

**关键分析**:
- `TipInlayRenderer` 是 `TipRenderer` 的唯一实现
- 渲染委托给 `InlayRendering` 静态方法
- `replaceLeadingTabs()` 确保补全缩进与用户设置一致
- `ya()` 计算前导空白长度，用于重叠文本分割

---

### 27. DocumentActionTracker (类, 源文件: bc)

```java
public final class DocumentActionTracker {
    public static final boolean float;           // assert 开关
    private static final Logger byte;             // 诊断日志
    private final AtomicInteger enum;             // 强制补全计数器

    boolean getExecutingForcedCodeGenerateAction();
    // -> enum.get() > 0

    void exitForcedCodeGenerateAction();
    // -> enum.decrementAndGet()
    // assert: 结果 >= 0

    private void Hb();
    // -> enum.incrementAndGet()

    static DocumentActionTracker getInstance();
    // -> ApplicationManager.getApplication().getService(DocumentActionTracker.class)

    private static boolean oB(AnAction action);
    // 检查是否为删除/退格操作:
    // action instanceof DeleteAction || action instanceof BackspaceAction
}
```

**关键分析**:
- `AtomicInteger enum` 跟踪强制补全操作的嵌套层级
- `oB()` 判断 Delete/Backspace 操作，这些操作后需要触发强制补全
- H() 调用: `JComponentKt.H()`, `IdeAction.H()`, `FileInfo.H()`, `EditorUtils.H()`

---

### 28. DocumentActionTracker$ActionListener (类, 源文件: bc)

```java
public final class DocumentActionTracker$ActionListener implements AnActionListener {
    private final AtomicReference<Editor> enum;  // 当前活跃编辑器引用

    void beforeActionPerformed(AnAction action, AnActionEvent event);
    // 1. 获取当前编辑器
    // 2. 检查 containEditor(editor)
    // 3. 如果是 SaveAllAction: 移除编辑器 + invokeLater 关闭按钮面板
    // 4. 如果是 UndoAction: handleUndoAction(editor)
    // 5. 设置 enum: 如果 EditorManagerService.isAvailable(editor) 则保存，否则 null
    // 6. 如果 oB(action) (Delete/Backspace): Hb() 增加强制补全计数

    void afterActionPerformed(AnAction action, AnActionEvent event);
    // 1. 如果 oB(action): exitForcedCodeGenerateAction()
    // 2. 如果不再 executingForcedCodeGenerateAction:
    //    获取保存的编辑器引用
    //    检查 editor/project/disposed 状态
    //    检查 isSelectedEditor
    //    检查 EditorManagerService.isAvailable
    //    -> editorChanged(editor, Forced, false) 触发强制补全

    private static void Ya(Editor editor);
    // closeButtonPanel(editor) — 关闭 Inline Chat 按钮面板
}
```

**关键分析**:
- `ActionListener` 是 IntelliJ AnActionListener 扩展点实现
- **Delete/Backspace 触发强制补全**: `beforeActionPerformed` 增加 `Hb()`，`afterActionPerformed` 减少 `exitForcedCodeGenerateAction()`
- **SaveAllAction**: 关闭 Inline Chat 按钮面板
- **UndoAction**: 委托给 `InlineChatService.handleUndoAction()`
- `afterActionPerformed` 中，如果不再处于强制补全状态，触发 `editorChanged(Forced, false)`

---

### 29. EditorUtil (类, 源文件: ac)

```java
public final class EditorUtil {
    public static final Key<List<EditorRequestService>> enum;  // 编辑器请求列表 Key

    static long getDocumentModificationStamp(Document doc);
    // 优先使用 DocumentEx.getModificationSequence()
    // 否则使用 Document.getModificationStamp()

    static boolean isSelectedEditor(Editor editor);
    // 1. 获取 FileEditorManager
    // 2. 检查是否 Split Editor (通过反射 isSplit())
    // 3. 否则检查 getSelectedEditor() instanceof TextEditor
    //    -> textEditor.getEditor().equals(editor)

    static List<EditorRequestService> getEditorRequests(Editor editor);
    // -> enum Key.get(editor, List.of())

    static boolean isFocusedEditor(Editor editor);
    // 1. 单元测试模式: 始终返回 true
    // 2. 远程 IDE: 始终返回 true
    // 3. 否则: editor.contentComponent.isFocusOwner()

    static void addEditorRequest(Editor editor, EditorRequestService request);
    // 1. disposeWithEditor(editor, request.getDisposable())
    // 2. 如果 Key 不存在，创建 CopyOnWriteList
    // 3. 添加到列表

    static int whitespacePrefixLength(String text);
    // 计算前导空白长度（空格和Tab）

    static {}  // enum = Key.create(H("..."))
}
```

**关键分析**:
- `isSelectedEditor()` 使用反射处理 Split Editor 场景（IntelliJ 内部 API）
- `addEditorRequest()` 将请求与编辑器生命周期绑定
- `isFocusedEditor()` 对远程 IDE 和单元测试特殊处理
- H() 调用: `RequestCancelException.H()`, `JComponentKt.H()`

---

### 30. AgentCodeTipList (类, 源文件: tc)

```java
public class AgentCodeTipList implements CodeInlayList {
    private ResponseStreamDto.ResponseData long;    // data
    private String super;                            // scene
    public static final boolean for;                // assert 开关
    private final EditorRequestService if;           // request
    private String case;                             // replacementText
    private boolean final;                           // removeBlank
    private final AgentCodeTip try;                  // agentCodeTip
    private String float;                            // language
    private String byte;                              // requestId
    private final CodeInlayList enum;               // delegate

    // --- 构造器 ---
    AgentCodeTipList(CodeInlayList delegate, AgentCodeTip tip, EditorRequestService request);
    // 1. 保存 delegate, tip, request
    // 2. removeBlank = true
    // 3. replacementText = CodeTipUtil.dropOverlappingTrailingLines(tip.getText(), request.getDocumentContent(), request.getOffset())

    // --- CodeInlayList 接口实现 ---
    boolean isEmpty();          // -> delegate.isEmpty()
    List<CodeEditorInlay> getInlays();  // -> delegate.getInlays() 或空列表
    int getOffset();            // -> request.getOffset()
    TextRange getReplacementRange();
    // 1. 如果 delegate 有 replacementRange，使用它
    // 2. 否则从 tip.getRange() 计算偏移

    String getReplacementText();
    // -> case (构造时计算的 replacementText)

    CodeTip getAICodeTip();     // -> try (agentCodeTip)
    Iterator<CodeEditorInlay> iterator();  // -> delegate.iterator() 或空迭代器
}
```

**关键分析**:
- `AgentCodeTipList` 是 `CodeInlayList` 的装饰器，包装 Agent 补全结果
- `dropOverlappingTrailingLines()` 去除与文档已有内容重叠的尾部行
- `getReplacementRange()` 从 Agent 返回的 Range 对象计算 IntelliJ TextRange
- H() 调用: `RequestCancelException.H()`, `CodeCompleteService.H()`

---

### 31. CancelRequestTip (类, 源文件: yc)

```java
public class CancelRequestTip {
    private final Object byte;              // 同步锁
    private final Alarm enum;               // IntelliJ Alarm 定时器

    CancelRequestTip(Disposable parent);
    // 初始化: byte = new Object(), enum = new Alarm(POOLED_THREAD, parent)

    void cancelAllAndAddRequest(Runnable task, int delayMs);
    // synchronized(byte):
    //   enum.cancelAllRequests()
    //   enum.addRequest(task, delayMs)

    void lA();
    // synchronized(byte):
    //   enum.cancelAllRequests()

    void ab(int timeout, TimeUnit unit);
    // synchronized(byte):
    //   enum.waitForAllExecuted(timeout, unit)

    static String H(Object input);
    // 字符串解混淆方法:
    // 1. 获取调用者类名+方法名构建 key
    // 2. 使用 XOR 解码: result[i] = input[i] ^ key[j] ^ key[k]
    // 3. 双向遍历解码
}
```

**关键分析**:
- `CancelRequestTip` 是防抖定时器，用于延迟触发补全请求
- `cancelAllAndAddRequest()` — 取消旧请求，添加新延迟请求
- `H()` 是一个独立的字符串解混淆方法，使用调用栈信息作为 XOR 密钥
- H() 调用: `GitReviewService.H()`, `FileExtensionLanguageDetails.H()`

---

### 32. CodeTipTypedHandlerDelegate (类, 源文件: sc)

```java
public class CodeTipTypedHandlerDelegate extends TypedHandlerDelegate {
    private static final Logger enum;

    Result checkAutoPopup(char c, Project project, Editor editor, PsiFile file);
    // 1. 检查 AICodeRequestSettings.isShowIdeCodeTips()
    // 2. 如果不显示 IDE 补全，但有缓存数据: hasCacheData(editor, c)
    //    -> 返回 STOP（阻止 IDE 自动补全弹窗）
    // 3. 否则委托给父类
}
```

**关键分析**:
- 当 AI 补全有缓存数据时，阻止 IDE 原生自动补全弹窗
- 这是 AI 补全与 IDE 补全的冲突解决策略
- H() 调用: `PropertyUtils.H()`, `Maps.H()`

---

### 33. TipTypedHandlerDelegate (类, 源文件: pc)

```java
public class TipTypedHandlerDelegate extends TypedHandlerDelegate {
    private static final Key<Long> enum;  // pendingTypeOver Key

    static boolean getPendingTypeOverAndReset(Editor editor);
    // 1. 获取 enum Key 中的 Long 值
    // 2. 清除 Key (set null)
    // 3. 比较 Long 值与 document.modificationStamp
    //    -> 相等返回 true（表示是补全接受后的 type-over）

    Result beforeCharTyped(char c, Project project, Editor editor, PsiFile file, FileType fileType);
    // 1. 检查是否为括号/引号/分号闭合字符: ) ] } " ' > ;
    // 2. 如果是，且在 CommandProcessor 中:
    //    -> 保存 document.modificationStamp 到 enum Key
    // 3. 返回 CONTINUE
}
```

**关键分析**:
- `TipTypedHandlerDelegate` 处理 "type-over" 场景：用户输入闭合字符时，如果补全已包含该字符，则跳过
- `getPendingTypeOverAndReset()` 在 `acceptTip` 时被调用，判断是否应跳过补全中的闭合字符
- H() 调用: `RequestTimeoutException.H()`, `ChatInputController.H()`

---

### 34. RequestResultList (类, 源文件: uc)

```java
public class RequestResultList {
    public final Object case;                                      // inlayLock
    public final EditorRequestService final;                       // request
    public boolean try;                                            // hasOnDemandCodeTips
    public int float;                                              // index
    public int enum;                                               // maxShownIndex
    public final ObjectLinkedOpenHashSet<CodeInlayList> byte;      // inlayLists

    // --- 方法 ---
    Object getInlayLock();                // -> case
    EditorRequestService getRequest();    // -> final
    int getIndex();                       // -> float
    void setIndex(int);                   // -> float = value
    int getMaxShownIndex();               // -> enum
    void setMaxShownIndex(int);           // -> enum = value
    boolean isHasOnDemandCodeTips();      // -> try
    void setHasOnDemandCodeTips(boolean); // -> try = value
    void setHasOnDemandCodeTips();        // synchronized(case): try = true

    boolean hasOnDemandCodeTips();
    // synchronized(case): try || byte.size() > 1

    boolean hasPrev();   // synchronized(case): byte.size() > 1
    boolean hasNext();   // synchronized(case): byte.size() > 1

    CodeInlayList getPrevCodeTip();
    // synchronized(case):
    //   if size <= 1: return null, index = 0
    //   index-- (wrap around)
    //   return TB(byte, index)

    CodeInlayList getNextCodeTip();
    // synchronized(case):
    //   if size <= 1: return null, index = 0
    //   index++ (wrap around)
    //   maxShownIndex = max(maxShownIndex, index)
    //   return TB(byte, index)

    CodeInlayList getCurrentCodeTip();
    // synchronized(case): TB(byte, index)

    void addInlays(CodeInlayList inlayList);
    // synchronized(case):
    //   byte.add(inlayList)
    //   maxShownIndex = max(maxShownIndex, byte.size())

    void resetInlays();
    // synchronized(case): byte.clear()

    static String H(Object input);
    // 字符串解混淆方法（同 CancelRequestTip.H 模式）

    private static CodeInlayList TB(ObjectSortedSet<CodeInlayList> set, int index);
    // -> set.stream().skip(index).findFirst().orElse(null)

    String toString();
    // -> "RequestResultList(request=..., inlayLock=..., inlayLists=..., index=..., maxShownIndex=..., hasOnDemandCodeTips=...)"
}
```

**关键分析**:
- `RequestResultList` 管理单个补全请求的所有候选结果
- 使用 `ObjectLinkedOpenHashSet` 保持插入顺序且去重
- `index` 实现循环切换（wrap around），`maxShownIndex` 记录最大展示索引
- 所有操作在 `synchronized(case)` 下进行，线程安全
- H() 调用: `GeneratorConfig.H()`, `ConditionalActionConfiguration.H()`

---

## 三、EditorManagerService 实现分析

### 服务注册

```
EditorManagerService (接口)
    ↓ implements
EditorManagerServiceImpl (应用级服务)
    ↓ 注册方式
ApplicationManager.getApplication().getService(EditorManagerService.class)
```

### 核心方法调用链

```
用户输入 → DocumentActionTracker$ActionListener.afterActionPerformed()
    → EditorManagerService.editorChanged(editor, Forced, false)
        → EditorManagerServiceImpl.editorChanged(editor, offset, type, forced)
            → RequestTipService.createRequest(editor, offset, type)
            → RequestTipService.fetchCachedTips(request)  // 尝试缓存
            → RequestTipService.fetchTips(request, subscriber, ...)
                → Agent 服务请求
                → RequestTipServiceImpl.ib()  // 处理响应
                    → AgentCodeTip.FromString()
                    → CodeTipUtil.createEditorCodeTip()
                    → AgentCodeTipList 包装
                    → SubmissionPublisher.submit()
                        → EditorManagerServiceImpl$F.KB()  // onNext
                            → mc(request, editor) 验证
                            → Xc(editor, inlays) 检查重复
                            → TipReceivedMessage.TOPIC 发布
                            → NC() 渲染 Inlay
```

---

## 四、补全请求生命周期

### 阶段 1: 请求创建

```
EditorManagerServiceImpl.editorChanged(editor, offset, type, forced)
    ↓
RequestTipServiceImpl.createRequest(editor, offset, type)
    ↓ 创建 EditorRequestService 实例
    ↓ 设置 offset, completionType, documentContent, fileName 等
    ↓
EditorUtil.addEditorRequest(editor, request)  // 绑定到编辑器生命周期
```

### 阶段 2: 缓存检查

```
RequestTipServiceImpl.fetchCachedTips(request)
    ↓
TipCache.isLatestPrefix(prefix)  // 增量补全优化
    ↓ 如果命中
TipCache.getLatest(key)
    ↓ 返回缓存的 CodeInlayList
```

### 阶段 3: 网络请求

```
RequestTipServiceImpl.fetchTips(request, subscriber, ...)
    ↓ 创建 SubmissionPublisher<List<CodeInlayList>>
    ↓ subscriber.onSubscribe(subscription)
    ↓ 调用 Agent 服务 (CodeCompleteService / AgentService)
    ↓ 响应到达
    ↓
RequestTipServiceImpl.ib(requestId, lines, request, subscriber, data)
    ↓ 逐行处理:
    ↓   1. 替换 Tab -> 空格
    ↓   2. 去除前导空白（如果前缀末行非空）
    ↓   3. stripTrailing
    ↓   4. AgentCodeTip.FromString()
    ↓   5. replaceLeadingTabs()
    ↓   6. setRequestId, setScene, setLanguage
    ↓   7. CodeTipUtil.createEditorCodeTip()
    ↓   8. 包装为 AgentCodeTipList
    ↓ 提交到 publisher
    ↓ publisher.close()
```

### 阶段 4: 结果渲染

```
EditorManagerServiceImpl$F.KB(inlays)
    ↓ mc(request, editor) 验证请求有效性
    ↓ enum.compareAndSet(false, true) 防重入
    ↓ 重置 KEY_LAST_REQUEST 中的 RequestResultList
    ↓ Xc(editor, inlays) 检查是否已有同偏移 Inlay
    ↓ 如果没有: subscription.request(1)
    ↓ 发布 TipReceivedMessage.TOPIC
    ↓ 如果有 Consumer 回调且数据已结束:
    ↓   invokeLater -> callback.accept(inlayList)
    ↓
NC(inlayList, request, editor, forced, action)
    ↓ 如果 forced: disposeTips(editor, action)
    ↓ 获取 InlayModel
    ↓ 遍历 CodeEditorInlay:
    ↓   跳过 isEmptyTip()
    ↓   处理 replacementRange:
    ↓     matchSuffixSection 分割
    ↓     创建 TipInlayRenderer
    ↓   按 CodeTipType 分派:
    ↓     Inline: addInlineElement()
    ↓     AfterLineEnd: addAfterLineEndElement()
    ↓     Block: addBlockElement()
```

### 阶段 5: 补全接受

```
用户 Tab/Enter → EditorManagerServiceImpl.acceptTip(editor)
    ↓ 获取当前 Inlay
    ↓ 获取 TipRenderer
    ↓ 获取 contentLines
    ↓ 插入文本到文档
    ↓ disposeTips(editor, Accept)
```

---

## 五、Inlay 渲染管线

### 渲染架构

```
TipInlayRenderer (实现 TipRenderer + EditorCustomElementRenderer)
    ↓ calcWidthInPixels()
        → InlayRendering.getWidth(this, editor)
    ↓ paint()
        → InlayRendering.paint(g, this, r, editor)

InlayRendering (纯静态工具类)
    ↓ paint():
        1. 获取 TextAttributes
        2. PB() — 绘制圆角矩形背景
        3. 遍历 contentLines:
           a. drawString() — 绘制文本
           b. ic() — 绘制效果线
        4. Block 类型处理多行换行
```

### 三种 Inlay 类型

| CodeTipType | InlayModel 方法 | 用途 |
|---|---|---|
| Inline | `addInlineElement(offset, true, MAX_INT-priority, renderer)` |行内补全（灰色幽灵文本） |
| AfterLineEnd | `addAfterLineEndElement(offset, true, renderer)` | 行尾补全 |
| Block | `addBlockElement(offset, true, false, MAX_INT-priority, renderer)` | 块级补全（多行） |

### 文本效果绘制

| EffectType | EffectPainter2D 常量 | 视觉效果 |
|---|---|---|
| LINE_UNDERSCORE | LINE_UNDERSCORE | 细下划线 |
| BOLD_LINE_UNDERSCORE | BOLD_LINE_UNDERSCORE | 粗下划线 |
| STRIKEOUT | STRIKEOUT | 删除线 |
| WAVE_UNDERSCORE | WAVE_UNDERSCORE | 波浪线 |
| BOLD_DOTTED_LINE | BOLD_DOTTED_LINE | 粗点线 |

---

## 六、与 Agent/Service 层的交互

### 服务依赖图

```
EditorManagerServiceImpl
    ├── RequestTipService.getInstance()
    │   ├── createRequest() → EditorRequestService
    │   ├── fetchCachedTips() → TipCache
    │   ├── fetchTips() → Agent 服务
    │   └── dealStreamAgentTips() → 流式 Agent
    ├── CancelRequestTip.requestAlarm
    │   └── cancelAllAndAddRequest() → 防抖延迟
    ├── DocumentActionTracker.getInstance()
    │   └── ActionListener → AnActionListener
    ├── EditorUtil
    │   ├── isSelectedEditor()
    │   ├── isFocusedEditor()
    │   ├── addEditorRequest()
    │   └── getDocumentModificationStamp()
    ├── TipReceivedMessage.TOPIC → MessageBus
    ├── RequestsCancelledService.TOPIC → MessageBus
    ├── RejectTipMessage.TOPIC → MessageBus
    ├── AICodeSettingsState.enableCodeComplete
    ├── PluginStartupActivity.getApiKey()
    └── AICodeRequestSettings.isShowIdeCodeTips()
```

### MessageBus 通信

| 主题 | 发布者 | 订阅者 | 事件 |
|---|---|---|---|
| TipReceivedMessage.TOPIC | EditorManagerServiceImpl$F | 补全结果消费者 | 补全结果到达 |
| RequestsCancelledService.TOPIC | EditorManagerServiceImpl | 请求取消监听 | 请求被取消 |
| RejectTipMessage.TOPIC | EditorManagerServiceImpl | 补全拒绝监听 | 自动补全被拒绝 |
| ProcessStatusListener.TOPIC | Agent 进程管理 | Agent 重启监听 | Agent 进程重启 |

### H() 解混淆调用汇总

| 类 | H() 解码器 | 用途 |
|---|---|---|
| EditorManagerService | RequestTimeoutException.H, OpenTelemetryUtil.H | 字符串常量解混淆 |
| EditorSupport | AICodeStringUtil.H, ChatInputController.H | 扩展点名称、方法名 |
| EditorRequestService | NewFileUtils.H, InlineChatStatusServiceKt.H | 空检查错误消息 |
| TipReceivedMessage | ConditionalActionConfiguration.H | Topic 显示名 |
| RequestsCancelledService | Maps.H | Topic 显示名 |
| RejectTipMessage | Maps.H | Topic 显示名 |
| LanguageInfoSupport | OpenTelemetryUtil.H | 扩展点名称 |
| EditorManagerServiceImpl | EditorUtils.H, Content.util.EditorUtils.H | 日志消息、操作 ID |
| RequestTipServiceImpl | RequestCancelException.H, CodeCompleteService.H | 错误消息、状态通知 |
| CancelRequestTip | GitReviewService.H, FileExtensionLanguageDetails.H | 错误消息 |
| DocumentActionTracker | JComponentKt.H, IdeAction.H | 操作类型判断 |
| DocumentActionTracker$ActionListener | FileInfo.H, EditorUtils.H | 日志消息 |
| EditorUtil | RequestCancelException.H, JComponentKt.H | Key 名称 |
| AgentCodeTipList | RequestCancelException.H, CodeCompleteService.H | 空检查错误消息 |
| CodeTipTypedHandlerDelegate | PropertyUtils.H, Maps.H | 设置检查消息 |
| TipTypedHandlerDelegate | RequestTimeoutException.H, ChatInputController.H | 括号字符判断 |
| RequestResultList | GeneratorConfig.H, ConditionalActionConfiguration.H | 错误消息 |
| BizResponse | ConditionalActionConfiguration.H, NewFileUtils.H | 成功码 "0" |

---

## 七、类关系总图

```
                    ┌──────────────────────┐
                    │  EditorManagerService │ (接口)
                    │  extends Disposable   │
                    └──────────┬───────────┘
                               │ implements
                    ┌──────────▼───────────┐
                    │EditorManagerServiceImpl│
                    │  - requestAlarm       │
                    │  - docChangeCount      │
                    │  - keyMap             │
                    └──┬──────┬──────────┬──┘
                       │      │          │
            ┌──────────┘      │          │
            │                  │          │
   ┌────────▼──────┐  ┌───────▼──────┐  ┌▼──────────────────┐
   │RequestTipService│  │TipRenderer   │  │DocumentActionTracker│
   │  (接口)        │  │  (接口)      │  │  + ActionListener   │
   └───────┬──────┘  └───────┬──────┘  └────────────────────┘
           │implements        │implements
   ┌───────▼──────┐  ┌───────▼──────────┐
   │RequestTipServiceImpl│  │TipInlayRenderer  │
   │  - cache           │  │  - contentLines  │
   │  - CODE_TIP_MAP    │  │  - type          │
   └───────┬──────┘  └───────┬──────────┘
           │                 │ delegates to
           │         ┌───────▼──────────┐
           │         │ InlayRendering    │
           │         │  (静态工具类)     │
           │         └──────────────────┘
           │
    ┌──────▼──────────┐
    │EditorRequestService│ (接口)
    │ extends RequestCancellable │
    └──────┬──────────┘
           │ used by
    ┌──────▼──────────┐     ┌──────────────────┐
    │RequestResultList │────▶│CodeInlayList      │
    │  - inlayLists    │     │  (接口)           │
    │  - index         │     └───────┬──────────┘
    │  - inlayLock     │             │ implements
    └─────────────────┘     ┌───────▼──────────┐
                            │AgentCodeTipList  │
                            │  - delegate      │
                            │  - agentCodeTip  │
                            └──────────────────┘

    ┌──────────────────┐     ┌──────────────────┐
    │CancelRequestTip  │     │EditorUtil         │
    │  - byte (lock)   │     │  - enum (Key)     │
    │  - enum (Alarm)  │     │  + isSelectedEditor│
    └──────────────────┘     └──────────────────┘

    ┌──────────────────┐     ┌──────────────────┐
    │CodeTipTypedHandler│     │TipTypedHandler    │
    │ Delegate         │     │Delegate           │
    │  checkAutoPopup  │     │  beforeCharTyped   │
    └──────────────────┘     └──────────────────┘

    ┌──────────────────┐     ┌──────────────────┐
    │BizResponse<T>    │     │ProjectService     │
    │  - float (obj)   │     │  flushLib()       │
    │  - byte (resCode)│     └──────────────────┘
    │  - enum (msg)    │
    └──────────────────┘
```

---

## 八、关键发现

1. **Reactive Flow 架构**: 补全请求使用 `Flow.Subscriber` / `SubmissionPublisher` 实现流式响应，支持增量渲染
2. **防抖机制**: `CancelRequestTip` 使用 IntelliJ `Alarm` 实现请求防抖，避免频繁触发
3. **文档序列号验证**: `mc()` 方法通过 `DocumentEx.getModificationSequence()` 确保请求不过期
4. **三种 Inlay 渲染模式**: Inline（行内灰色文本）、AfterLineEnd（行尾）、Block（块级多行）
5. **Type-over 优化**: `TipTypedHandlerDelegate` 处理闭合字符输入时的补全接受
6. **IDE 补全冲突解决**: `CodeTipTypedHandlerDelegate` 在有 AI 缓存时阻止 IDE 原生自动补全
7. **Delete/Backspace 触发强制补全**: `DocumentActionTracker` 监听删除操作后自动触发 `Forced` 类型补全
8. **Undo 特殊处理**: Undo 操作委托给 `InlineChatService.handleUndoAction()`
9. **多候选循环**: `RequestResultList` 支持 wrap-around 索引实现 Alt+/ 循环切换
10. **字符串解混淆**: 所有类使用不同的 H() 解码器，`CancelRequestTip.H()` 和 `RequestResultList.H()` 使用调用栈信息作为 XOR 密钥