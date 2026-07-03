## 一、顶层接口/类完整反编译

### 1. EditorManagerService (接口, 源文件: r)

```java
public interface EditorManagerService extends Disposable &#123;
    static final Logger LOG = Logger.getInstance(EditorManagerService.class);

    // --- 核心补全控制方法 ---
    boolean isAvailable(Editor editor);
    void showNextInlaySet(Editor editor);
    boolean hasPreviousInlaySet(Editor editor);
    boolean hasNextInlaySet(Editor editor);
    void editorChanged(Editor editor, int offset, CodeTipRequestType type, boolean forced);
    boolean acceptTip(Editor editor);
    void showPreviousInlaySet(Editor editor);
    List&lt;TipRenderer&gt; getInlays(Editor editor, int start, int end);
    void cancelTipRequests(Editor editor);
    boolean acceptTipForLine(Editor editor);
    boolean acceptWordTip(Editor editor);
    void disposeTips(Editor editor, OperateActionEnum action);
    void acceptTip(Project project, Editor editor, EditorRequestService request, CodeInlayList inlayList);
    int countTipInlays(Editor editor, TextRange range, boolean b1, boolean b2, boolean b3, boolean b4);
    boolean hasCacheData(Editor editor, char c);
    void acceptWordTip(Project project, Editor editor, EditorRequestService request, CodeInlayList inlayList);

    // --- 默认方法 ---
    default void editorChanged(Editor editor, CodeTipRequestType type, boolean forced) &#123;
        // 1. 空检查 editor 和 type
        // 2. 检查 API Key 是否为空 (PluginStartupActivity.getApiKey())
        // 3. 检查 enableCodeComplete 设置
        // 4. 在 readAction 中执行: editorChanged(editor, editor.caretModel.offset, type, forced)
    &#125;

    default boolean hasTipInlays(Editor editor) &#123;
        // isAvailable(editor) && countTipInlays(editor, TextRange(0, doc.textLength), true, true, true, true) > 0
    &#125;

    static EditorManagerService getInstance() &#123;
        return ApplicationManager.getApplication().getService(EditorManagerService.class);
    &#125;
&#125;
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
public interface RequestTipService &#123;
    EditorRequestService createRequest(Editor editor, int offset, TipType type);
    boolean isAvailable(Editor editor);
    List&lt;CodeInlayList&gt; fetchCachedTips(EditorRequestService request);
    void fetchTips(EditorRequestService request, Flow.Subscriber<List&lt;CodeInlayList&gt;> subscriber,
                   Editor editor, String requestId, CodeTipRequestType type);
    void fetchInlineChatContent(EditorRequestService request, Flow.Subscriber<List&lt;CodeInlayList&gt;> subscriber,
                                Editor editor, String requestId, CodeTipRequestType type);
    void dealStreamAgentTips(String requestId, ResponseStreamDto data, Project project, MessageDto message);
    void dealAgentTips(String requestId, JsonObject data, Project project);
    EditorRequestService createInlineChatRequest(Editor editor, int offset, TipType type);

    static RequestTipService getInstance() &#123;
        return ApplicationManager.getApplication().getService(RequestTipService.class);
    &#125;
&#125;
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
public interface EditorRequestService extends RequestCancellable &#123;
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

    default String getCurrentDocumentPrefix() &#123;
        return getDocumentContent().substring(0, getOffset());
    &#125;
&#125;
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
public interface EditorSupport &#123;
    static final ExtensionPointName&lt;EditorSupport&gt; EP;

    boolean isCodeTipsEnabled(Editor editor);

    static boolean isEditorCodeTipsSupported(Editor editor) &#123;
        if (editor == null) return true;  // 空编辑器视为支持（不阻止）
        if (!EP.hasAnyExtensions()) return true;
        if (EP.findFirstSafe(e -> e.isCodeTipsEnabled(editor)) != null) return false;
        return true;
    &#125;

    static &#123;&#125;  // EP = ExtensionPointName.create(H("..."))
&#125;
```

**关键分析**:
- IntelliJ Extension Point 机制，允许其他插件控制补全是否启用
- `isEditorCodeTipsSupported` — 全局检查：有扩展点且任一返回 false 则禁用
- H() 调用: `AICodeStringUtil.H()`, `ChatInputController.H()`

---

### 5. CodeEditorInlay (接口, 源文件: z)

```java
public interface CodeEditorInlay &#123;
    List&lt;String&gt; getLines();
    int getEditorOffset();
    void setEditorOffset(int offset);
    void setType(CodeTipType type);
    CodeTipType getType();
    void setLines(List&lt;String&gt; lines);

    default boolean isEmptyTip() &#123;
        return getLines().isEmpty() ||
               (getLines().size() == 1 && getLines().get(0).isEmpty());
    &#125;
&#125;
```

**关键分析**:
- 表示单个 Inlay 元素，包含行内容、偏移量和类型
- `isEmptyTip()` — 空补全判断：无行或仅一行空字符串

---

### 6. CodeInlayList (接口, 源文件: p)

```java
public interface CodeInlayList extends Iterable&lt;CodeEditorInlay&gt; &#123;
    boolean isEmpty();
    void setData(ResponseStreamDto.ResponseData data);
    void setRemoveBlank(boolean removeBlank);
    List&lt;CodeEditorInlay&gt; getInlays();
    TextRange getReplacementRange();
    int getOffset();
    boolean isRemoveBlank();
    void setReplacementText(String text);
    CodeTip getAICodeTip();
    ResponseStreamDto.ResponseData getData();
    String getReplacementText();
    void setReplacementRange(TextRange range);
&#125;
```

**关键分析**:
- 补全结果集合，可迭代 `CodeEditorInlay`
- 关联 `ResponseStreamDto.ResponseData` — 流式响应数据
- `getReplacementRange()` / `getReplacementText()` — 文本替换范围和内容
- `isRemoveBlank()` — 是否移除空白行

---

### 7. CodeTip (接口, 源文件: n)

```java
public interface CodeTip &#123;
    List&lt;String&gt; getTip();
    CodeTip withCompletion(List&lt;String&gt; lines);
    boolean isCached();
    CodeTip asCached();
&#125;
```

**关键分析**:
- 补全内容抽象，支持不可变转换 (`withCompletion`)
- 缓存标记 (`isCached` / `asCached`)

---

### 8. ProjectService (接口, 源文件: s)

```java
public interface ProjectService &#123;
    void flushLib(Project project);
&#125;
```

**关键分析**:
- 项目级服务，仅一个方法 `flushLib` — 刷新项目库索引

---

### 9. TipCache (接口, 源文件: t)

```java
public interface TipCache &#123;
    List&lt;CodeTip&gt; getLatest(String key);
    List&lt;CodeTip&gt; get(String key, boolean b);
    void clear();
    void updateLatest(String key, String value, boolean b);
    void add(String key, String value, boolean b, CodeTip tip);
    boolean isLatestPrefix(String key);
&#125;
```

**关键分析**:
- 补全缓存接口，支持最新/历史缓存
- `isLatestPrefix()` — 判断输入是否为最新缓存的前缀（增量补全优化）
- `updateLatest()` — 更新最新缓存

---

### 10. TipRenderer (接口, 源文件: m)

```java
public interface TipRenderer extends EditorCustomElementRenderer &#123;
    CodeTipType getType();
    List&lt;String&gt; getContentLines();
    Inlay&lt;TipRenderer&gt; getInlay();
&#125;
```

**关键分析**:
- IntelliJ Inlay 渲染器接口
- 关联 `Inlay&lt;TipRenderer&gt;` — IDE 内嵌元素
- `getContentLines()` — 获取渲染内容行

---

### 11. TipReceivedMessage (接口, 源文件: x)

```java
public interface TipReceivedMessage &#123;
    static final Topic&lt;TipReceivedMessage&gt; TOPIC = Topic.create(H("..."), TipReceivedMessage.class);

    void inlaysReceived(EditorRequestService request, List&lt;CodeInlayList&gt; inlays);
&#125;
```

**关键分析**:
- IntelliJ MessageBus 消息主题
- 补全结果接收通知，发布到 `TipReceivedMessage.TOPIC`
- H() 调用: `ConditionalActionConfiguration.H()`

---

### 12. RequestsCancelledService (接口, 源文件: l)

```java
public interface RequestsCancelledService &#123;
    static final Topic&lt;RequestsCancelledService&gt; TOPIC = Topic.create(H("..."), RequestsCancelledService.class);

    void requestsCancelled(int requestId);
&#125;
```

**关键分析**:
- 请求取消通知主题
- 通过 requestId 标识被取消的请求
- H() 调用: `Maps.H()`

---

### 13. RequestCancellable (接口, 源文件: v)

```java
public interface RequestCancellable &#123;
    static final RequestCancellable enum = new RequestCancellable$f();  // 空实现单例

    void cancel();
    boolean isCancelled();
&#125;
```

**关键分析**:
- 请求可取消接口
- 静态 `enum` 字段是一个空实现（no-op），`isCancelled()` 永远返回 false，`cancel()` 为空操作

---

### 14. RequestCancellable$f (类, 源文件: v)

```java
public class RequestCancellable$f implements RequestCancellable &#123;
    public boolean isCancelled() &#123; return false; &#125;
    public void cancel() &#123; /* no-op */ &#125;
&#125;
```

---

### 15. RejectTipMessage (接口, 源文件: a)

```java
public interface RejectTipMessage &#123;
    static final Topic&lt;RejectTipMessage&gt; TOPIC = Topic.create(H("..."), RejectTipMessage.class);

    void automaticCodeTipsRejected(EditorRequestService request);
&#125;
```

**关键分析**:
- 补全拒绝通知主题
- 当自动补全被拒绝时发布
- H() 调用: `Maps.H()`

---

### 16. LanguageInfoSupport (接口, 源文件: d)

```java
public interface LanguageInfoSupport &#123;
    static final ExtensionPointName&lt;LanguageInfoSupport&gt; EP = new ExtensionPointName<>(H("..."));

    AICodeLanguageInfo findVSCodeLanguageMapping(PsiFile file);
&#125;
```

**关键分析**:
- VS Code 语言映射扩展点
- 将 IntelliJ PsiFile 映射到 AICodeLanguageInfo
- H() 调用: `OpenTelemetryUtil.H()`

---

### 17. ProcessStatusListener (接口, 源文件: b)

```java
public interface ProcessStatusListener &#123;
    static final Topic&lt;ProcessStatusListener&gt; TOPIC = new Topic<>(ProcessStatusListener.class);

    void onAgentProcessRestart();
&#125;
```

**关键分析**:
- Agent 进程重启监听
- 使用 Topic 但未指定显示名（直接传 Class）

---

### 18. BizResponse&lt;T&gt; (类, 源文件: xc, 子包: response)

```java
public class BizResponse&lt;T&gt; &#123;
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
    boolean isSuccess() &#123; return StrUtil.equals(H("..."), byte); &#125;  // resCode == "0"
    boolean isFail() &#123; return !isSuccess(); &#125;
&#125;
```

**关键分析**:
- 通用业务响应 DTO
- 成功码通过 H() 解混淆，实际值为 "0"
- 使用 Hutool 的 `StrUtil.equals()` 比较

---
