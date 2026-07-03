# iFlyCode 代码补全 Inlay 渲染系统分析

> 版本: iFlyCode 3.4.2-222 | 分析日期: 2026-05-13

## 1. 系统概述

iFlyCode 的代码补全 Inlay 渲染系统负责将 AI 生成的代码建议以"幽灵文本"(Ghost Text)形式实时渲染到编辑器中，并提供完整的用户交互机制（采纳、逐词采纳、逐行采纳、拒绝、循环切换）。该系统构建在 IntelliJ Platform 的 Inlay API 之上，通过自定义 `EditorCustomElementRenderer` 实现渲染管线。

核心架构分为四层：
- **请求层** — 触发补全请求、管理请求生命周期
- **数据层** — 缓存、转换 Agent 响应为 Inlay 数据模型
- **渲染层** — 将代码建议绘制为编辑器内的半透明文本
- **交互层** — 处理用户键盘/鼠标操作

---

## 2. 渲染管线流程图

```
用户输入 / 手动触发 (Alt+\ / Alt+C)
        |
        v
[CodeTipTypedHandlerDelegate] ── checkAutoPopup() ── 拦截 IDE 自动补全弹窗
[TipTypedHandlerDelegate]      ── beforeCharTyped()  ── 追踪括号/引号闭合字符
        |
        v
[EditorManagerService.editorChanged(editor, requestType, force)]
        |
        v
[RequestTipServiceImpl.createRequest(editor, offset, tipType)]
  └─ 创建 EditorRequestService (文件上下文、偏移量、语言信息)
        |
        v
[RequestTipServiceImpl.fetchTips(request, subscriber, editor, prefix, requestType)]
  └─ 先查缓存: fetchCachedTips(request)
  └─ 缓存未命中 → 发送请求到 Agent
        |
        v
[CodeCompleteService.handleAgentAction()]  ← Agent WebSocket 响应
        |
        v
[RequestTipServiceImpl.dealStreamAgentTips() / dealAgentTips()]
  └─ 解析 ResponseStreamDto / JsonObject
  └─ 调用 CodeTipUtil.createEditorCodeTip() 转换为 CodeInlayList
        |
        v
[TipReceivedMessage.inlaysReceived()]  ← 消息总线通知
        |
        v
[EditorManagerService] 创建 Inlay
  └─ 为每个 CodeEditorInlay 创建 TipInlayRenderer
  └─ Editor.getInlayModel().addInlay(offset, renderer)
        |
        v
[TipInlayRenderer.paint()] ── 调用 InlayRendering.renderCodeBlock()
  └─ InlayRendering.PB()  ── 绘制圆角背景 (fillRoundRect)
  └─ InlayRendering.ic()  ── 绘制文本效果线 (下划线/波浪线等)
  └─ 逐行绘制代码文本
        |
        v
[InlayListener.inlaysUpdated()]  ← 通知 Inlay 更新
  └─ [InlayGotItListener] ── 首次显示 GotItTooltip 提示
        |
        v
用户交互:
  Tab         → [AcceptInlaysAction]     → EditorManagerService.acceptTip()
  Ctrl+Right  → [AcceptWordInlaysAction] → EditorManagerService.acceptWordTip()
  Ctrl+Down   → [AcceptLineCodeInlaysAction] → EditorManagerService.acceptTipForLine()
  Esc         → [DisposeInlaysAction]    → EditorManagerService.disposeTips()
  Alt+]       → [CycleNextEditorInlays]  → EditorManagerService.showNextInlaySet()
  Alt+[       → [CyclePreviousEditorInlays] → EditorManagerService.showPreviousInlaySet()
```

---

## 3. 核心类详解

### 3.1 渲染层

#### InlayRendering (com.aicode.service.editor.InlayRendering)

最终类，提供静态渲染方法，是整个 Inlay 视觉输出的核心。

| 方法 | 作用 |
|------|------|
| `renderCodeBlock(Editor, String, List&lt;String&gt;, Graphics, Rectangle2D, TextAttributes)` | 入口方法：渲染整个代码块，逐行绘制文本 |
| `IA(Editor, String, List&lt;String&gt;)` → `int` | 计算代码块最大宽度（取所有行中 `FontMetrics.stringWidth()` 的最大值） |
| `KC(Editor)` → `float` | 获取编辑器行高比例系数 |
| `PB(Graphics2D, TextAttributes, double, double, double, double)` | 绘制圆角背景矩形：使用 `TextAttributes.getBackgroundColor()` 填色，调用 `Graphics2D.fillRoundRect()` |
| `ic(Graphics2D, double, double, double, int, int, TextAttributes, Font)` | 绘制文本效果装饰线：根据 `EffectType` 选择对应的 `EffectPainter2D`（LINE_UNDERSCORE / BOLD_LINE_UNDERSCORE / STRIKE_THROUGH / WAVE_UNDERSCORE / BOLD_DOTTED_UNDERSCORE） |
| `Wc(Editor, String)` → `Font` | 获取编辑器字体 |
| `qc(Editor, Font)` → `FontMetrics` | 获取字体度量，使用 `Key<Map<Font, FontMetrics>>` 缓存 |

内部类 `InlayRendering$G` 是 `EffectType` 的 switch 映射表，将 `EffectType.ordinal()` 映射到 1-5 的连续索引。

#### TipInlayRenderer (com.aicode.service.editor.TipInlayRenderer)

实现 `TipRenderer` 接口，是 IntelliJ Inlay 系统的渲染器实现。每个 Inlay 元素对应一个 `TipInlayRenderer` 实例。

| 字段 | 作用 |
|------|------|
| `if` (Inlay&lt;TipRenderer&gt;) | 关联的 Inlay 对象引用 |
| `case` (int) | 缓存宽度 |
| `try` (int) | 缓存高度 |
| `final` (String) | 完整文本内容 |
| `float` (CodeTipType) | 补全类型 (Inline / Block / AfterLineEnd) |
| `byte` (List&lt;String&gt;) | 逐行文本内容 |
| `enum` (TextAttributes) | 文本渲染属性（颜色、效果等） |

| 方法 | 作用 |
|------|------|
| `paint(Inlay, Graphics, Rectangle, TextAttributes)` | 渲染入口：委托 `InlayRendering.renderCodeBlock()` 绘制 |
| `calcWidthInPixels(Inlay)` | 计算宽度：委托 `InlayRendering.IA()` |
| `calcHeightInPixels(Inlay)` | 计算高度：行数 x 行高 |
| `setCachedWidth(int)` / `setCachedHeight(int)` | 缓存尺寸，避免重复计算 |
| `getContent()` | 返回完整文本 |
| `getLines()` / `getContentLines()` | 返回逐行文本 |
| `getType()` | 返回 CodeTipType |
| `getInlay()` / `setInlay(Inlay)` | 获取/设置关联的 Inlay 对象 |
| `getContextMenuGroupId(Inlay)` | 返回右键菜单组 ID |
| `replaceLeadingTabs(List, EditorRequestService)` | 静态方法：将 Tab 替换为空格（根据编辑器设置） |
| `AC(Editor)` | 静态方法：创建 TextAttributes，设置前景色为灰色（幽灵文本色） |

构造函数：`TipInlayRenderer(Editor, EditorRequestService, CodeTipType, List&lt;String&gt;)`
- 调用 `AC(editor)` 创建灰色 TextAttributes
- 调用 `replaceLeadingTabs()` 处理缩进
- 将行列表拼接为完整字符串

#### TipRenderer (com.aicode.service.TipRenderer)

接口，继承 `EditorCustomElementRenderer`，定义渲染器契约：

```java
public interface TipRenderer extends EditorCustomElementRenderer &#123;
    CodeTipType getType();
    List&lt;String&gt; getContentLines();
    Inlay&lt;TipRenderer&gt; getInlay();
&#125;
```

### 3.2 数据模型层

#### CodeTip (com.aicode.service.CodeTip)

接口，表示 AI 生成的代码建议：

```java
public interface CodeTip &#123;
    List&lt;String&gt; getTip();                    // 获取建议文本行
    CodeTip withCompletion(List&lt;String&gt;);     // 追加补全内容
    boolean isCached();                       // 是否来自缓存
    CodeTip asCached();                       // 标记为缓存
&#125;
```

#### AgentCodeTip (com.aicode.request.AgentCodeTip)

`CodeTip` 的主要实现，包装 Agent 响应中的 `GetTipsResult$Tip`：

| 字段 | 作用 |
|------|------|
| `enum` (GetTipsResult$Tip) | Agent 原始数据 |
| `try` (List&lt;String&gt;) | 建议文本行 |
| `final` (String) | 请求 ID |
| `float` (String) | 场景标识 |
| `byte` (String) | 语言标识 |

关键方法：
- `FromString(String)` — 从字符串解析创建实例
- `Ub()` — 创建缓存副本（`isCached = true`）

#### CodeEditorInlay (com.aicode.service.CodeEditorInlay)

接口，表示编辑器中一个 Inlay 元素的数据：

```java
public interface CodeEditorInlay &#123;
    List&lt;String&gt; getLines();
    int getEditorOffset();
    void setEditorOffset(int);
    CodeTipType getType();
    void setType(CodeTipType);
    boolean isEmptyTip();  // default: 判断 lines 是否为空
&#125;
```

#### CodeInlayList (com.aicode.service.CodeInlayList)

接口，表示一组相关的 Inlay 元素（一次补全建议）：

```java
public interface CodeInlayList extends Iterable&lt;CodeEditorInlay&gt; &#123;
    boolean isEmpty();
    List&lt;CodeEditorInlay&gt; getInlays();
    TextRange getReplacementRange();
    int getOffset();
    CodeTip getAICodeTip();
    String getReplacementText();
    ResponseStreamDto.ResponseData getData();
    boolean isRemoveBlank();
    // setter 方法...
&#125;
```

#### DefaultInlayList (com.aicode.generate.DefaultInlayList)

`CodeInlayList` 的基础实现：

| 字段 | 作用 |
|------|------|
| `final` (List&lt;CodeEditorInlay&gt;) | Inlay 元素列表 |
| `try` (TextRange) | 替换范围 |
| `float` (CodeTip) | 关联的 AI 代码建议 |
| `byte` (String) | 替换文本 |
| `enum` (boolean) | 是否移除空行 |

#### AgentCodeTipList (com.aicode.service.editor.AgentCodeTipList)

`CodeInlayList` 的装饰器实现，包装另一个 `CodeInlayList` 并添加 Agent 特有信息（requestId、scene、language）。

### 3.3 枚举类型

#### CodeTipType (com.aicode.enums.CodeTipType)

定义 Inlay 的渲染位置：

| 值 | 含义 |
|----|------|
| `Inline` | 行内插入，紧跟光标位置 |
| `Block` | 块级插入，在当前行下方显示多行建议 |
| `AfterLineEnd` | 行尾追加 |

#### TipType (com.aicode.enums.TipType)

补全请求类型：

| 值 | 含义 |
|----|------|
| `GhostText` | 幽灵文本（代码补全） |
| `OpenAICode` | OpenAI 风格代码生成 |

#### TipTypeEnum (com.aicode.enums.TipTypeEnum)

补全模式：

| 值 | 含义 |
|----|------|
| `SINGLE_LINE` | 单行补全模式 |
| `INTELLIGENT_MODE` | 智能模式（多行） |

#### CodeTipRequestType (com.aicode.enums.CodeTipRequestType)

请求触发方式：

| 值 | 含义 | 关键方法 |
|----|------|----------|
| `Automatic` | 自动触发 | `isAutomaticOrForced()` |
| `Forced` | 强制触发 | `isForced()`, `isForcedOrManual()` |
| `Manual` | 手动触发 (Alt+\) | `isForcedOrManual()` |
| `Interact` | 交互触发 | `isUnforced()` |
| `InlineChat` | 行内聊天触发 | `isInlineChat()` |

#### OperateActionEnum (com.aicode.enums.OperateActionEnum)

用户操作类型，用于 Inlay 生命周期事件：

| 值 | 含义 |
|----|------|
| `Applied` | 采纳补全 |
| `Typing` | 用户继续输入 |
| `TypingAsSuggested` | 按建议输入 |
| `IdeCompletion` | IDE 自动补全 |
| `Cycling` | 循环切换 |
| `EscReject` | Esc 拒绝 |
| `UserOperate` | 用户操作 |
| `CaretChange` | 光标移动 |
| `SettingsChange` | 设置变更 |

`isUserAction()` — 判断是否为用户主动操作
`isResetLastRequest()` — 判断是否需要重置上次请求

### 3.4 请求层

#### RequestTipServiceImpl (com.aicode.service.editor.RequestTipServiceImpl)

实现 `RequestTipService` 接口，是补全请求的核心服务。

| 字段 | 作用 |
|------|------|
| `cache` (TipCache) | 代码建议缓存 |
| `CODE_TIP_MAP` (Map<String, CodeTipRequestDto>) | 活跃请求映射（按文件路径索引） |
| `LATEST_RESPONSE_DATA` (Map<Project, String>) | 项目级最新响应数据 |
| `LAST_REQUEST` (Map<Project, Map<String, Long>>) | 项目级最后请求时间戳 |
| `enum` (Language) | 当前语言 |

关键方法：
- `createRequest(Editor, offset, TipType)` — 创建补全请求上下文
- `createInlineChatRequest(Editor, offset, TipType)` — 创建行内聊天请求
- `fetchTips(request, subscriber, editor, prefix, requestType)` — 获取补全建议（先查缓存，再请求 Agent）
- `fetchInlineChatContent(...)` — 获取行内聊天内容
- `fetchCachedTips(request)` — 从缓存获取
- `dealStreamAgentTips(...)` — 处理流式 Agent 响应
- `dealAgentTips(...)` — 处理非流式 Agent 响应
- `isAvailable(Editor)` — 检查编辑器是否可用

#### CodeTipRequestDto (com.aicode.agent.dto.CodeTipRequestDto)

请求数据传输对象：

| 字段 | 作用 |
|------|------|
| `request` (EditorRequestService) | 编辑器请求上下文 |
| `codeSubScriber` (Flow.Subscriber<List&lt;CodeInlayList&gt;>) | 响应订阅者 |
| `parentSpan` (Span) | OpenTelemetry 追踪 Span |
| `startTime` (Long) | 请求开始时间 |
| `lastReplacementText` (String) | 上次替换文本（用于去重） |
| `firstAgentDuration` (long) | 首次 Agent 响应耗时 |

#### CancelRequestTip (com.aicode.service.editor.CancelRequestTip)

请求取消管理器，使用 `Alarm` 定时器：

- `cancelAllAndAddRequest(Runnable, int)` — 取消所有待处理请求并添加新请求
- `ab(int, TimeUnit)` — 等待请求完成
- `lA()` — 清除所有待处理请求

#### CodeTipUtil (com.aicode.generate.CodeTipUtil)

代码建议工具类，负责将 `CodeTip` 转换为 `CodeInlayList`：

| 方法 | 作用 |
|------|------|
| `createEditorCodeTip(EditorRequestService, CodeTip, boolean)` | 核心方法：创建编辑器代码建议，返回 `CodeInlayList` |
| `dropOverlappingTrailingLines(String, String, int)` | 去除与已有代码重叠的尾部行 |
| `TrimEndSpaceTab(String)` | 去除行尾空格/Tab |
| `trimStartSpaceTab(String)` | 去除行首空格/Tab |

### 3.5 缓存层

#### TipCache (com.aicode.service.TipCache)

缓存接口：

```java
public interface TipCache &#123;
    List&lt;CodeTip&gt; getLatest(String key);
    List&lt;CodeTip&gt; get(String key, boolean flag);
    void clear();
    void updateLatest(String key, String text, boolean flag);
    void add(String key, String text, boolean flag, CodeTip tip);
    boolean isLatestPrefix(String key);
&#125;
```

#### SimpleCodeTipCache (com.aicode.generate.SimpleCodeTipCache)

`TipCache` 的实现，使用 LRU 缓存策略：

| 字段 | 作用 |
|------|------|
| `byte` (LinkedHashMap<Z, List&lt;CodeTip&gt;>) | LRU 缓存映射 |
| `final` (ReadWriteLock) | 读写锁，保证线程安全 |
| `case` (String) | 最新键 |
| `enum` (String) | 最新文本 |
| `float` (boolean) | 最新标志 |

内部类 `SimpleCodeTipCache$Y` 继承 `LinkedHashMap`，重写 `removeEldestEntry()` 实现 LRU 淘汰。

内部类 `SimpleCodeTipCache$Z` 是缓存键，包含 `enum`（文本前缀）和 `byte`（布尔标志）。

缓存策略：
- 使用 `ReadWriteLock` 保证并发安全
- LRU 淘汰最老条目
- `isLatestPrefix()` 检查输入是否为最新缓存键的前缀（用于增量补全）
- `updateLatest()` 更新最新缓存条目
- `getLatest()` 获取最新缓存（无需精确匹配键）

### 3.6 交互层（动作）

#### AcceptInlaysAction (com.aicode.action.AcceptInlaysAction)

**快捷键**: `Tab`

采纳全部补全建议。内部处理器 `AcceptInlaysAction$pa` 继承 `EditorActionHandler`：

- `isEnabledForCaret()` — 委托 `AcceptInlaysAction.isSupported(editor)` 检查是否有活跃 Inlay
- `doExecute()` — 调用 `EditorManagerService.acceptTip(editor)` 采纳全部建议
- `executeInCommand()` — 返回 `false`（不在 Command 中执行）

#### AcceptWordInlaysAction (com.aicode.action.AcceptWordInlaysAction)

**快捷键**: `Ctrl+Right`

逐词采纳补全建议。内部处理器 `AcceptWordInlaysAction$wa`：

- `doExecute()` — 调用 `EditorManagerService.acceptWordTip(editor)` 采纳一个词

#### AcceptLineCodeInlaysAction (com.aicode.action.AcceptLineCodeInlaysAction)

**快捷键**: `Ctrl+Down`

逐行采纳补全建议。内部处理器 `AcceptLineCodeInlaysAction$va`：

- `doExecute()` — 调用 `EditorManagerService.acceptTipForLine(editor)` 采纳一行

#### DisposeInlaysAction (com.aicode.action.DisposeInlaysAction)

**快捷键**: `Esc`

取消/隐藏补全建议。使用 `Q.ua` 作为内部处理器（混淆后的 `EditorActionHandler` 子类）。

#### CycleNextEditorInlays (com.aicode.action.CycleNextEditorInlays)

**快捷键**: `Alt+]` (默认) / `Ctrl+.` (macOS)

循环显示下一组补全建议：

```java
boolean doCycleAction(Editor editor) &#123;
    EditorManagerService mgr = EditorManagerService.getInstance();
    if (mgr.hasNextInlaySet(editor)) &#123;
        mgr.showNextInlaySet(editor);
        return true;
    &#125;
    return false;
&#125;
```

#### CyclePreviousEditorInlays (com.aicode.action.CyclePreviousEditorInlays)

**快捷键**: `Alt+[` (默认) / `Ctrl+,` (macOS)

循环显示上一组补全建议：

```java
boolean doCycleAction(Editor editor) &#123;
    EditorManagerService mgr = EditorManagerService.getInstance();
    if (mgr.hasPreviousInlaySet(editor)) &#123;
        mgr.showPreviousInlaySet(editor);
        return true;
    &#125;
    return false;
&#125;
```

#### RequestCodeGenerateAction (com.aicode.action.RequestCodeGenerateAction)

**快捷键**: `Alt+\` / `Alt+C`

手动触发代码补全：

```java
void actionPerformed(AnActionEvent e) &#123;
    EditorManagerService mgr = EditorManagerService.getInstance();
    Editor editor = e.getData(CommonDataKeys.EDITOR);
    if (editor != null && EditorUtil.isSelectedEditor(editor)
        && mgr.isAvailable(editor)
        && ApplicationUtil.isSupportLanguage(editor)) &#123;
        mgr.editorChanged(editor, CodeTipRequestType.Manual, true);
    &#125;
&#125;
```

#### TipPromoterAction (com.aicode.action.TipPromoterAction)

实现 `ActionPromoter`，提升 Inlay 相关动作的优先级。当动作列表中包含 `Q.Sa`（Cycle 动作基类）时，将其提升到最高优先级，确保 Tab/Esc 等快捷键优先被 Inlay 系统处理。

### 3.7 输入处理层

#### CodeTipTypedHandlerDelegate (com.aicode.service.editor.CodeTipTypedHandlerDelegate)

继承 `TypedHandlerDelegate`，拦截 IDE 自动补全弹窗：

```java
Result checkAutoPopup(char c, Project project, Editor editor, PsiFile file) &#123;
    if (!AICodeRequestSettings.settings().isShowIdeCodeTips()
        && EditorManagerService.getInstance().hasCacheData(editor, c)) &#123;
        // 当 IDE 补全提示被禁用且有缓存数据时，阻止 IDE 弹窗
        return Result.STOP;
    &#125;
    return super.checkAutoPopup(c, project, editor, file);
&#125;
```

#### TipTypedHandlerDelegate (com.aicode.service.editor.TipTypedHandlerDelegate)

继承 `TypedHandlerDelegate`，追踪括号/引号闭合字符的输入：

- 在 `beforeCharTyped()` 中检测 `)`, `]`, `&#125;`, `"`, `'`, `>`, `;` 等闭合字符
- 如果当前有活跃的 Command 且输入了闭合字符，记录 `Document.modificationStamp` 到 Editor 的 UserData
- `getPendingTypeOverAndReset(Editor)` — 检查是否有待处理的类型覆盖，并重置状态

此机制用于处理"用户输入了 Inlay 建议中的下一个字符"的情况——如果用户输入的字符与建议一致，则自动采纳该字符位置的建议。

### 3.8 提示与通知层

#### InlayListener (com.aicode.complete.InlayListener)

消息总线监听器接口：

```java
public interface InlayListener &#123;
    Topic&lt;InlayListener&gt; TOPIC = ...;
    void inlaysUpdated(EditorRequestService, OperateActionEnum, Editor, List<Inlay&lt;TipRenderer&gt;>);
&#125;
```

#### InlayGotItListener (com.aicode.complete.InlayGotItListener)

实现 `InlayListener`，在首次显示 Inlay 时弹出 GotItTooltip 引导提示：

- 当 `inlaysUpdated()` 被调用且 Inlay 列表非空、请求未被取消时
- 创建 `GotItTooltip`，设置标题、正文、图标（ToolWindowIcon）、位置（atLeft）
- 获取第一个 Inlay 的边界位置，在该位置显示提示气泡
- 使用 `BasicActionsBundle.message()` 获取本地化文本

#### InlayCompletionHintFactory (com.aicode.complete.InlayCompletionHintFactory)

创建补全提示的工厂类：

| 方法 | 作用 |
|------|------|
| `showHintAtCaret(Editor)` | 在光标位置显示补全提示 |
| `showHintAtPosition(Editor, Point)` | 在指定位置显示补全提示 |
| `showEditorHint(LightweightHint, Editor, short, int, int, boolean)` | 使用 `HintManagerImpl` 显示编辑器提示 |

内部创建 `LightweightHint`，包含 `SimpleColoredComponent` 和 `InlineKeybindingHintComponent`，显示快捷键提示信息。

#### InlayCompletionHintFactory$InlineKeybindingHintComponent

继承 `JPanel`，封装快捷键提示的 UI 组件。

### 3.9 编辑器集成层

#### PluginEditorInlayHintsProvider (com.aicode.toolwindow.PluginEditorInlayHintsProvider)

实现 IntelliJ `InlayHintsProvider&lt;PluginHintSettings&gt;`，提供编辑器内的操作提示 Inlay（如"生成单元测试"、"代码优化"等快捷操作入口）：

| 方法 | 作用 |
|------|------|
| `getCollectorFor(PsiFile, Editor, PluginHintSettings, InlayHintsSink)` | 返回 `FactoryInlayHintsCollector`，收集 PSI 元素上的提示 |
| `getInlCollectResult(List&lt;CommandEnum&gt;)` | 获取可用的命令集合 |
| `addGroupAction(...)` | 添加分组操作 Inlay |
| `addLineAction(...)` | 添加行级操作 Inlay |
| `handleCommand(PsiElement, Editor, CommandEnum)` | 处理命令执行 |
| `handleUnitTest(PsiElement, EditorImpl, Project)` | 处理单元测试生成 |
| `handleAction(CommandEnum, Project, CodeInfoDto)` | 处理动作执行 |
| `isLanguageSupported(Language)` | 语言支持检查 |
| `createConfigurable(PluginHintSettings)` | 创建设置面板 |

内部类：
- `$1` — `FactoryInlayHintsCollector` 实现，遍历 PSI 树收集提示
- `$2` — `BaseListPopupStep&lt;CommandEnum&gt;`，弹出命令选择菜单
- `$3` — `CommandEnum` switch 映射表
- `InlCollectResult` — 点击回调接口
- `InlResult` — 行级点击回调接口

#### PluginHintSettings (com.aicode.toolwindow.PluginHintSettings)

Inlay 提示设置类，当前为空实现（无自定义配置项）。

---

## 4. 用户交互流程

### 4.1 键盘快捷键映射

| 快捷键 | 动作类 | 动作 ID | 行为 |
|--------|--------|---------|------|
| `Tab` | `AcceptInlaysAction` | `AICode.applyInlays` | 采纳全部补全 |
| `Esc` | `DisposeInlaysAction` | `AICode.disposeInlays` | 拒绝/隐藏补全 |
| `Ctrl+Right` | `AcceptWordInlaysAction` | `AICode.applyWordInlays` | 逐词采纳 |
| `Ctrl+Down` | `AcceptLineCodeInlaysAction` | `AICode.applyLineCodeInlays` | 逐行采纳 |
| `Alt+]` / `Ctrl+.` | `CycleNextEditorInlays` | `AICode.cycleNextInlays` | 切换到下一组建议 |
| `Alt+[` / `Ctrl+,` | `CyclePreviousEditorInlays` | `AICode.cyclePrevInlays` | 切换到上一组建议 |
| `Alt+\` / `Alt+C` | `RequestCodeGenerateAction` | `AICode.requestCompletions` | 手动触发补全 |

### 4.2 交互时序

```
1. 触发阶段
   用户输入 → CodeTipTypedHandlerDelegate.checkAutoPopup()
            → EditorManagerService.editorChanged(editor, Automatic, false)
            → RequestTipServiceImpl.fetchTips()

2. 渲染阶段
   Agent 响应 → dealStreamAgentTips() / dealAgentTips()
             → CodeTipUtil.createEditorCodeTip() → CodeInlayList
             → TipReceivedMessage.inlaysReceived()
             → 创建 TipInlayRenderer → Editor.getInlayModel().addInlay()
             → TipInlayRenderer.paint() → InlayRendering.renderCodeBlock()
             → InlayGotItListener.inlaysUpdated() → GotItTooltip (首次)

3. 交互阶段
   ┌─ Tab ──────────→ acceptTip() ────→ 替换文档文本 + 移除 Inlay
   ├─ Ctrl+Right ───→ acceptWordTip() → 替换一个词 + 更新 Inlay
   ├─ Ctrl+Down ────→ acceptTipForLine() → 替换一行 + 更新 Inlay
   ├─ Esc ──────────→ disposeTips() ──→ 移除 Inlay + 记录拒绝
   ├─ Alt+] ────────→ showNextInlaySet() → 切换显示下一组缓存建议
   ├─ Alt+[ ────────→ showPreviousInlaySet() → 切换显示上一组缓存建议
   └─ 继续输入 ─────→ TipTypedHandlerDelegate.beforeCharTyped()
                    → 匹配建议字符 → 自动采纳
                    → 不匹配 → 取消 Inlay + 发起新请求
```

### 4.3 类型覆盖 (Type-over) 机制

当用户输入的字符与 Inlay 建议中的下一个字符一致时，系统自动采纳该字符：

1. `TipTypedHandlerDelegate.beforeCharTyped()` 检测闭合字符 `)`, `]`, `&#125;`, `"`, `'`, `>`, `;`
2. 如果当前有活跃 Command，记录 `Document.modificationStamp` 到 Editor UserData
3. 后续通过 `getPendingTypeOverAndReset()` 检查：如果 stamp 匹配（文档未被其他修改），则确认类型覆盖

---

## 5. 缓存策略

### 5.1 缓存架构

```
SimpleCodeTipCache
  ├── LinkedHashMap<CacheKey, List&lt;CodeTip&gt;>  (LRU 缓存)
  ├── ReadWriteLock                           (并发控制)
  ├── latestKey / latestText / latestFlag     (最新条目追踪)
  └── CacheKey = (prefix: String, flag: boolean)
```

### 5.2 缓存键设计

缓存键 `SimpleCodeTipCache$Z` 由两部分组成：
- **prefix** — 光标前的文本前缀（即已输入的代码上下文）
- **flag** — 布尔标志（区分不同类型的请求）

### 5.3 缓存操作

| 操作 | 方法 | 说明 |
|------|------|------|
| 添加 | `add(key, text, flag, tip)` | 添加新的缓存条目 |
| 精确查询 | `get(key, flag)` | 按精确键查询 |
| 最新查询 | `getLatest(key)` | 获取最新添加的缓存（不要求键精确匹配） |
| 前缀检查 | `isLatestPrefix(key)` | 检查输入是否为最新缓存键的前缀 |
| 更新最新 | `updateLatest(key, text, flag)` | 更新最新缓存条目 |
| 清除 | `clear()` | 清空所有缓存 |

### 5.4 LRU 淘汰

`SimpleCodeTipCache$Y` 继承 `LinkedHashMap`，重写 `removeEldestEntry()`：
- 构造时指定最大容量
- 当条目数超过容量时，自动淘汰最久未访问的条目

### 5.5 缓存命中流程

```
用户输入 → editorChanged()
  → fetchCachedTips(request)
    → cache.getLatest(prefix)
      ├─ 命中 → 直接创建 Inlay（无需请求 Agent）
      └─ 未命中 → fetchTips()
                   → 发送请求到 Agent
                   → 响应存入缓存: cache.add()
```

### 5.6 增量补全

`isLatestPrefix()` 方法支持增量补全场景：
- 用户输入 "syst" → 缓存命中，显示 "System.out.println()"
- 用户继续输入 "em" → "system" 是 "syst" 的前缀扩展
- 缓存检查通过，直接使用已有建议（可能截取匹配部分）

---

## 6. 设置配置

### 6.1 AICodeSettingsState (com.aicode.settings.AICodeSettingsState)

全局设置，持久化存储：

| 字段 | 类型 | 作用 |
|------|------|------|
| `autoTrigger` | boolean | 是否自动触发补全 |
| `tipType` | String | 补全类型（SINGLE_LINE / INTELLIGENT_MODE） |
| `enableCodeComplete` | boolean | 是否启用代码补全 |
| `triggerTime` | Integer | 自动触发延迟时间（毫秒） |
| `codeCompleteDisableLang` | String[] | 禁用补全的语言列表 |
| `streamOutputConfig` | boolean | 是否启用流式输出 |
| `inlineCompletionInputStyle` | String | 行内补全输入风格 |
| `modelCode` | String | 使用的模型代码 |
| `sendKey` | String | 发送快捷键配置 |

### 6.2 CodeGenerateRequestState (com.aicode.settings.CodeGenerateRequestState)

请求相关设置：

| 字段 | 类型 | 作用 |
|------|------|------|
| `inlayTextColor` | Color | Inlay 文本颜色 |
| `showIdeCodeTips` | boolean | 是否显示 IDE 原生补全提示 |
| `internalDisableHttpCache` | boolean | 内部禁用 HTTP 缓存标志 |
| `requestLimitNotificationShown` | boolean | 请求限制通知是否已显示 |

### 6.3 配置交互

- `showIdeCodeTips = false` 时，`CodeTipTypedHandlerDelegate` 会拦截 IDE 自动补全弹窗（返回 `Result.STOP`），让 iFlyCode 的 Inlay 补全独占显示
- `inlayTextColor` 控制 `TipInlayRenderer.AC()` 创建的 TextAttributes 前景色
- `enableCodeComplete = false` 时，整个补全系统不触发

---

## 7. EditorManagerService 接口

`EditorManagerService` 是 Inlay 生命周期的核心管理接口：

| 方法 | 作用 |
|------|------|
| `isAvailable(Editor)` | 检查编辑器是否可用 |
| `editorChanged(Editor, CodeTipRequestType, boolean)` | 编辑器变更触发补全 |
| `acceptTip(Editor)` | 采纳全部补全 |
| `acceptWordTip(Editor)` | 逐词采纳 |
| `acceptTipForLine(Editor)` | 逐行采纳 |
| `disposeTips(Editor, OperateActionEnum)` | 丢弃补全（带操作原因） |
| `cancelTipRequests(Editor)` | 取消进行中的请求 |
| `hasNextInlaySet(Editor)` | 是否有下一组建议 |
| `showNextInlaySet(Editor)` | 显示下一组建议 |
| `hasPreviousInlaySet(Editor)` | 是否有上一组建议 |
| `showPreviousInlaySet(Editor)` | 显示上一组建议 |
| `getInlays(Editor, int, int)` | 获取指定范围的 Inlay |
| `countTipInlays(Editor, TextRange, ...)` | 统计 Inlay 数量 |
| `hasTipInlays(Editor)` | 是否有活跃 Inlay |
| `hasCacheData(Editor, char)` | 是否有匹配的缓存数据 |

---

## 8. 渲染细节

### 8.1 颜色方案

- **幽灵文本色**: 通过 `TipInlayRenderer.AC(editor)` 创建，使用灰色调前景色
- **背景色**: 通过 `TextAttributes.getBackgroundColor()` 获取，`InlayRendering.PB()` 使用 `fillRoundRect()` 绘制圆角背景
- **效果线**: 支持 5 种 `EffectPainter2D` 效果：LINE_UNDERSCORE、BOLD_LINE_UNDERSCORE、STRIKE_THROUGH、WAVE_UNDERSCORE、BOLD_DOTTED_UNDERSCORE

### 8.2 尺寸计算

- **宽度**: `InlayRendering.IA()` 遍历所有行，取 `FontMetrics.stringWidth()` 最大值
- **高度**: 行数 x 编辑器行高（通过 `InlayRendering.KC(editor)` 获取行高比例）
- **缓存**: `TipInlayRenderer` 缓存计算结果（`case` 字段缓存宽度，`try` 字段缓存高度）

### 8.3 Tab/空格处理

`TipInlayRenderer.replaceLeadingTabs()` 根据编辑器设置将 Tab 替换为对应数量的空格，确保 Inlay 文本与编辑器缩进一致。

---

## 9. 消息总线事件

| Topic | 接口 | 事件 |
|-------|------|------|
| `InlayListener.TOPIC` | `InlayListener` | `inlaysUpdated(requestService, action, editor, inlays)` |
| `TipReceivedMessage.TOPIC` | `TipReceivedMessage` | `inlaysReceived(requestService, inlayLists)` |
| `RejectTipMessage.TOPIC` | `RejectTipMessage` | `automaticCodeTipsRejected(requestService)` |

---

## 10. 类关系图

```
EditorCustomElementRenderer (IntelliJ Platform)
  └── TipRenderer (interface)
        └── TipInlayRenderer
              ├── uses → InlayRendering (static rendering)
              ├── has → CodeTipType (Inline/Block/AfterLineEnd)
              ├── has → TextAttributes (rendering attributes)
              └── has → Inlay&lt;TipRenderer&gt; (platform inlay reference)

CodeTip (interface)
  └── AgentCodeTip
        ├── has → GetTipsResult$Tip (agent data)
        └── withCompletion() → creates new instance

CodeEditorInlay (interface)
  └── [implementation in DefaultInlayList]

CodeInlayList (interface) extends Iterable&lt;CodeEditorInlay&gt;
  ├── DefaultInlayList
  └── AgentCodeTipList (decorator)

TipCache (interface)
  └── SimpleCodeTipCache
        ├── SimpleCodeTipCache$Y (LRU LinkedHashMap)
        └── SimpleCodeTipCache$Z (cache key)

RequestTipService (interface)
  └── RequestTipServiceImpl
        ├── has → TipCache
        ├── has → CODE_TIP_MAP (active requests)
        └── has → CancelRequestTip

EditorManagerService (interface) extends Disposable
  └── [implementation manages inlay lifecycle]

InlayHintsProvider&lt;PluginHintSettings&gt; (IntelliJ Platform)
  └── PluginEditorInlayHintsProvider
        ├── PluginEditorInlayHintsProvider$1 (collector)
        ├── PluginEditorInlayHintsProvider$2 (popup step)
        └── PluginEditorInlayHintsProvider$3 (switch map)
```

---

## 11. 混淆映射

由于代码经过混淆处理，以下是已识别的混淆名映射：

| 混淆名 | 原始文件 | 推测原名 |
|--------|----------|----------|
| `oc` | InlayRendering | InlayRendering |
| `dc` | TipInlayRenderer | TipInlayRenderer |
| `m` | TipRenderer | TipRenderer |
| `t` | TipCache | TipCache |
| `eh` | TipType | TipType |
| `pn` | TipTypeEnum | TipTypeEnum |
| `ze` | CodeTipType | CodeTipType |
| `lf` | CodeTipRequestType | CodeTipRequestType |
| `ci` | OperateActionEnum | OperateActionEnum |
| `n` | CodeTip | CodeTip |
| `z` | CodeEditorInlay | CodeEditorInlay |
| `p` | CodeInlayList | CodeInlayList |
| `el` | DefaultInlayList | DefaultInlayList |
| `lc` | AgentCodeTip | AgentCodeTip |
| `tc` | AgentCodeTipList | AgentCodeTipList |
| `sj` | SimpleCodeTipCache | SimpleCodeTipCache |
| `zc` | RequestTipServiceImpl | RequestTipServiceImpl |
| `yc` | CancelRequestTip | CancelRequestTip |
| `go` | CodeCompleteService | CodeCompleteService |
| `oh` | CodeTipUtil | CodeTipUtil |
| `sc` | CodeTipTypedHandlerDelegate | CodeTipTypedHandlerDelegate |
| `pc` | TipTypedHandlerDelegate | TipTypedHandlerDelegate |
| `sa` | InlayListener | InlayListener |
| `og` | InlayGotItListener | InlayGotItListener |
| `ie` | InlayCompletionHintFactory | InlayCompletionHintFactory |
| `xd` | AcceptInlaysAction | AcceptInlaysAction |
| `li` | AcceptWordInlaysAction | AcceptWordInlaysAction |
| `dk` | AcceptLineCodeInlaysAction | AcceptLineCodeInlaysAction |
| `ki` | DisposeInlaysAction | DisposeInlaysAction |
| `sd` | CycleNextEditorInlays | CycleNextEditorInlays |
| `hi` | CyclePreviousEditorInlays | CyclePreviousEditorInlays |
| `pl` | RequestCodeGenerateAction | RequestCodeGenerateAction |
| `mo` | TipPromoterAction | TipPromoterAction |
| `r` | EditorManagerService | EditorManagerService |
| `f` | EditorRequestService | EditorRequestService |
| `Q.sa` | Cycle action base class | BaseCycleInlaysAction |
| `Q.ua` | Dispose action handler | DisposeInlaysHandler |

TipInlayRenderer 字段混淆映射：

| 混淆字段 | 推测原名 |
|----------|----------|
| `if` | inlay |
| `case` | cachedWidth |
| `try` | cachedHeight |
| `final` | content |
| `float` | type |
| `byte` | lines |
| `enum` | textAttributes |
