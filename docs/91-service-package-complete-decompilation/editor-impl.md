## 二、Editor 子包实现类完整反编译

### 19. EditorManagerServiceImpl (类, 源文件: ec)

```java
public class EditorManagerServiceImpl implements EditorManagerService &#123;
    // --- 静态字段 ---
    private static final Key&lt;Boolean&gt; new;           // 某个布尔标记
    private static final Logger long;                // SLF4J 日志器
    public static final Key&lt;RequestResultList&gt; CACHE_KEY_LAST_REQUEST;
    public static final Key&lt;RequestResultList&gt; KEY_LAST_REQUEST;
    private static final Key&lt;Boolean&gt; try;           // 另一个布尔标记
    private static final Set&lt;String&gt; super;           // 不支持的文件类型集合
    public static final Key&lt;Boolean&gt; enum;            // Inlay 显示标记

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

    private Inlay&lt;TipRenderer&gt; Za(EditorRequestService, Editor, CodeInlayList, TipInlayRenderer, int);
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

    boolean Xc(Editor, List&lt;CodeInlayList&gt;);
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
    List&lt;TipRenderer&gt; getInlays(Editor editor, int start, int end);
    int countTipInlays(Editor editor, TextRange range, boolean b1, boolean b2, boolean b3, boolean b4);
    boolean hasCacheData(Editor editor, char c);
&#125;
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
public class EditorManagerServiceImpl$B &#123;
    public static final int[] enum;  // CodeTipType.ordinal() -> switch 映射表

    static &#123;&#125;  // 初始化 switch 映射: Inline=1, AfterLineEnd=2, Block=3
&#125;
```

**关键分析**: 纯粹的 switch 表映射类，用于 `CodeTipType` 的 tableswitch 优化

---

### 21. EditorManagerServiceImpl$F (类, 源文件: ec)

```java
public class EditorManagerServiceImpl$F implements Flow.Subscriber<List&lt;CodeInlayList&gt;> &#123;
    public final EditorRequestService super;         // 关联的请求
    public final EditorManagerServiceImpl for;       // 外部类引用
    public static final boolean if;                  // assert 开关
    public final long case;                          // 请求时间戳
    public final Editor final;                       // 关联的编辑器
    private volatile Flow.Subscription try;          // Reactive 订阅
    public final Consumer float;                     // 完成回调
    private volatile boolean byte;                   // 是否已结束
    public final AtomicBoolean enum;                  // 原子标记（防重入）

    void onNext -> KB(List&lt;CodeInlayList&gt;);
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
&#125;
```

**关键分析**:
- Reactive Flow 订阅者，连接 RequestTipService 的流式输出与 EditorManagerServiceImpl 的渲染
- `AtomicBoolean enum` 防止多次 onNext 处理
- `Consumer float` 是完成回调，用于 Agent 模式下的延迟渲染
- 响应时间通过 `System.currentTimeMillis() - case` 计算

---

### 22. RequestTipServiceImpl (类, 源文件: zc)

```java
public class RequestTipServiceImpl implements RequestTipService, Disposable &#123;
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

    List&lt;CodeInlayList&gt; fetchCachedTips(EditorRequestService request);
    // 从缓存获取补全结果

    void fetchTips(EditorRequestService request, Flow.Subscriber<List&lt;CodeInlayList&gt;> subscriber,
                   Editor editor, String requestId, CodeTipRequestType type);
    // 流式获取补全:
    // 1. 检查缓存 isLatestPrefix
    // 2. 创建 SubmissionPublisher
    // 3. 调用 Agent 服务获取补全
    // 4. 处理结果并提交到 publisher

    private void ib(String requestId, List&lt;String&gt; lines, EditorRequestService request,
                    Flow.Subscriber<List&lt;CodeInlayList&gt;> subscriber, ResponseStreamDto.ResponseData data);
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
&#125;
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
public class RequestTipServiceImpl$j extends TypeToken<String[]> &#123;
    public final RequestTipServiceImpl enum;  // 外部类引用

    RequestTipServiceImpl$j(RequestTipServiceImpl outer);
&#125;
```

**关键分析**: Gson TypeToken 子类，用于 JSON 反序列化 `String[]` 类型

---

### 24. InlayRendering (类, 源文件: oc)

```java
public final class InlayRendering &#123;
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
&#125;
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
public class InlayRendering$G &#123;
    public static final int[] enum;  // EffectType.ordinal() -> switch 映射表

    static &#123;&#125;  // LINE_UNDERSCORE=1, BOLD_LINE_UNDERSCORE=2, STRIKEOUT=3, WAVE_UNDERSCORE=4, BOLD_DOTTED_LINE=5
&#125;
```

---

### 26. TipInlayRenderer (类, 源文件: dc)

```java
public class TipInlayRenderer implements TipRenderer &#123;
    private Inlay&lt;TipRenderer&gt; if;          // 关联的 Inlay 对象
    private int case;                       // 缓存宽度
    private final String final;             // 行后缀文本（用于行内补全）
    private int try;                        // 行数
    private final CodeTipType float;        // 补全类型
    private final List&lt;String&gt; byte;        // 内容行
    private final TextAttributes enum;      // 文本属性

    // --- TipRenderer 接口实现 ---
    CodeTipType getType();
    List&lt;String&gt; getContentLines();
    Inlay&lt;TipRenderer&gt; getInlay();
    void setInlay(Inlay&lt;TipRenderer&gt; inlay);
    void setCachedWidth(int width);

    // --- EditorCustomElementRenderer 接口实现 ---
    int calcWidthInPixels(Inlay inlay);
    // -> InlayRendering.getWidth(this, editor)

    void paint(Graphics2D g, Rectangle r, Inlay inlay);
    // -> InlayRendering.paint(g, this, r, editor)

    // --- 静态工具方法 ---
    static List&lt;String&gt; replaceLeadingTabs(List&lt;String&gt; lines, EditorRequestService request);
    // 替换前导 Tab 为空格:
    // 1. 如果 request.isUseTabIndents()，直接返回
    // 2. 否则将每行前导 \t 替换为 request.getTabWidth() 个空格

    static int ya(CharSequence seq, int start, boolean b);
    // 计算前导空白长度（空格和Tab）
&#125;
```

**关键分析**:
- `TipInlayRenderer` 是 `TipRenderer` 的唯一实现
- 渲染委托给 `InlayRendering` 静态方法
- `replaceLeadingTabs()` 确保补全缩进与用户设置一致
- `ya()` 计算前导空白长度，用于重叠文本分割

---

### 27. DocumentActionTracker (类, 源文件: bc)

```java
public final class DocumentActionTracker &#123;
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
&#125;
```

**关键分析**:
- `AtomicInteger enum` 跟踪强制补全操作的嵌套层级
- `oB()` 判断 Delete/Backspace 操作，这些操作后需要触发强制补全
- H() 调用: `JComponentKt.H()`, `IdeAction.H()`, `FileInfo.H()`, `EditorUtils.H()`

---

### 28. DocumentActionTracker$ActionListener (类, 源文件: bc)

```java
public final class DocumentActionTracker$ActionListener implements AnActionListener &#123;
    private final AtomicReference&lt;Editor&gt; enum;  // 当前活跃编辑器引用

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
&#125;
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
public final class EditorUtil &#123;
    public static final Key<List&lt;EditorRequestService&gt;> enum;  // 编辑器请求列表 Key

    static long getDocumentModificationStamp(Document doc);
    // 优先使用 DocumentEx.getModificationSequence()
    // 否则使用 Document.getModificationStamp()

    static boolean isSelectedEditor(Editor editor);
    // 1. 获取 FileEditorManager
    // 2. 检查是否 Split Editor (通过反射 isSplit())
    // 3. 否则检查 getSelectedEditor() instanceof TextEditor
    //    -> textEditor.getEditor().equals(editor)

    static List&lt;EditorRequestService&gt; getEditorRequests(Editor editor);
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

    static &#123;&#125;  // enum = Key.create(H("..."))
&#125;
```

**关键分析**:
- `isSelectedEditor()` 使用反射处理 Split Editor 场景（IntelliJ 内部 API）
- `addEditorRequest()` 将请求与编辑器生命周期绑定
- `isFocusedEditor()` 对远程 IDE 和单元测试特殊处理
- H() 调用: `RequestCancelException.H()`, `JComponentKt.H()`

---

### 30. AgentCodeTipList (类, 源文件: tc)

```java
public class AgentCodeTipList implements CodeInlayList &#123;
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
    List&lt;CodeEditorInlay&gt; getInlays();  // -> delegate.getInlays() 或空列表
    int getOffset();            // -> request.getOffset()
    TextRange getReplacementRange();
    // 1. 如果 delegate 有 replacementRange，使用它
    // 2. 否则从 tip.getRange() 计算偏移

    String getReplacementText();
    // -> case (构造时计算的 replacementText)

    CodeTip getAICodeTip();     // -> try (agentCodeTip)
    Iterator&lt;CodeEditorInlay&gt; iterator();  // -> delegate.iterator() 或空迭代器
&#125;
```

**关键分析**:
- `AgentCodeTipList` 是 `CodeInlayList` 的装饰器，包装 Agent 补全结果
- `dropOverlappingTrailingLines()` 去除与文档已有内容重叠的尾部行
- `getReplacementRange()` 从 Agent 返回的 Range 对象计算 IntelliJ TextRange
- H() 调用: `RequestCancelException.H()`, `CodeCompleteService.H()`

---

### 31. CancelRequestTip (类, 源文件: yc)

```java
public class CancelRequestTip &#123;
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
&#125;
```

**关键分析**:
- `CancelRequestTip` 是防抖定时器，用于延迟触发补全请求
- `cancelAllAndAddRequest()` — 取消旧请求，添加新延迟请求
- `H()` 是一个独立的字符串解混淆方法，使用调用栈信息作为 XOR 密钥
- H() 调用: `GitReviewService.H()`, `FileExtensionLanguageDetails.H()`

---

### 32. CodeTipTypedHandlerDelegate (类, 源文件: sc)

```java
public class CodeTipTypedHandlerDelegate extends TypedHandlerDelegate &#123;
    private static final Logger enum;

    Result checkAutoPopup(char c, Project project, Editor editor, PsiFile file);
    // 1. 检查 AICodeRequestSettings.isShowIdeCodeTips()
    // 2. 如果不显示 IDE 补全，但有缓存数据: hasCacheData(editor, c)
    //    -> 返回 STOP（阻止 IDE 自动补全弹窗）
    // 3. 否则委托给父类
&#125;
```

**关键分析**:
- 当 AI 补全有缓存数据时，阻止 IDE 原生自动补全弹窗
- 这是 AI 补全与 IDE 补全的冲突解决策略
- H() 调用: `PropertyUtils.H()`, `Maps.H()`

---

### 33. TipTypedHandlerDelegate (类, 源文件: pc)

```java
public class TipTypedHandlerDelegate extends TypedHandlerDelegate &#123;
    private static final Key&lt;Long&gt; enum;  // pendingTypeOver Key

    static boolean getPendingTypeOverAndReset(Editor editor);
    // 1. 获取 enum Key 中的 Long 值
    // 2. 清除 Key (set null)
    // 3. 比较 Long 值与 document.modificationStamp
    //    -> 相等返回 true（表示是补全接受后的 type-over）

    Result beforeCharTyped(char c, Project project, Editor editor, PsiFile file, FileType fileType);
    // 1. 检查是否为括号/引号/分号闭合字符: ) ] &#125; " ' > ;
    // 2. 如果是，且在 CommandProcessor 中:
    //    -> 保存 document.modificationStamp 到 enum Key
    // 3. 返回 CONTINUE
&#125;
```

**关键分析**:
- `TipTypedHandlerDelegate` 处理 "type-over" 场景：用户输入闭合字符时，如果补全已包含该字符，则跳过
- `getPendingTypeOverAndReset()` 在 `acceptTip` 时被调用，判断是否应跳过补全中的闭合字符
- H() 调用: `RequestTimeoutException.H()`, `ChatInputController.H()`

---

### 34. RequestResultList (类, 源文件: uc)

```java
public class RequestResultList &#123;
    public final Object case;                                      // inlayLock
    public final EditorRequestService final;                       // request
    public boolean try;                                            // hasOnDemandCodeTips
    public int float;                                              // index
    public int enum;                                               // maxShownIndex
    public final ObjectLinkedOpenHashSet&lt;CodeInlayList&gt; byte;      // inlayLists

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

    private static CodeInlayList TB(ObjectSortedSet&lt;CodeInlayList&gt; set, int index);
    // -> set.stream().skip(index).findFirst().orElse(null)

    String toString();
    // -> "RequestResultList(request=..., inlayLock=..., inlayLists=..., index=..., maxShownIndex=..., hasOnDemandCodeTips=...)"
&#125;
```

**关键分析**:
- `RequestResultList` 管理单个补全请求的所有候选结果
- 使用 `ObjectLinkedOpenHashSet` 保持插入顺序且去重
- `index` 实现循环切换（wrap around），`maxShownIndex` 记录最大展示索引
- 所有操作在 `synchronized(case)` 下进行，线程安全
- H() 调用: `GeneratorConfig.H()`, `ConditionalActionConfiguration.H()`

---
