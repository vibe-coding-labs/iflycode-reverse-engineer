### 2.1 AICodeUnloadPluginListener

```
public class com.aicode.listener.AICodeUnloadPluginListener
  implements com.intellij.ide.plugins.DynamicPluginListener

  // --- 字段 ---
  (无实例字段)

  // --- 方法 ---

  // 插件卸载前回调
  public void beforePluginUnload(IdeaPluginDescriptor, boolean):
    1. 检查插件描述符是否为 null (否则抛 IllegalArgumentException)
    2. 调用 PluginInfoUtils.isAICodePlugin(descriptor) 判断是否为 iFlyCode 插件
    3. 如果是:
       a. 调用 BC() 遍历所有有效项目
       b. 对每个项目的选中编辑器:
          - 获取 EditorManagerService.getInstance()
          - 调用 disposeTips(editor, OperateActionEnum.UserOperate) 关闭提示
       c. 通过反射调用 MessageBundle.INSTANCE 的 getDynamicBundle() 方法
          (使用解混淆字符串获取方法名)
    4. 异常处理: 捕获 Exception 后静默返回

  // 遍历所有有效项目，关闭编辑器提示
  private static void BC():
    1. ApplicationUtil.findValidProjects() 获取所有打开项目
    2. 遍历每个项目:
       a. FileEditorManager.getInstance(project).getSelectedTextEditor()
       b. 如果编辑器非 null 且未 disposed:
          - EditorManagerService.getInstance().disposeTips(editor, OperateActionEnum.UserOperate)
```

**交互关系**:
- `PluginInfoUtils.isAICodePlugin()` -- 判断插件标识
- `EditorManagerService.disposeTips()` -- 关闭代码提示
- `MessageBundle.INSTANCE` -- 反射获取动态 Bundle

---

### 2.2 ApplicationStartupListener

```
public class com.aicode.listener.ApplicationStartupListener
  implements com.intellij.ide.AppLifecycleListener

  // --- 字段 ---
  private static final Logger enum  // 日志记录器

  // --- 方法 ---

  // 应用即将关闭
  public void appWillBeClosed(boolean):
    1. Logger.info(解混淆: "应用即将关闭, 正在清理资源")
    2. PluginStartupActivity.clear()  // 清理启动活动资源

  // 应用正在关闭
  public void appClosing():
    1. Logger.info(解混淆: "应用正在关闭")
    2. PluginStartupActivity.clear()
```

**交互关系**:
- `PluginStartupActivity.clear()` -- 清理插件启动时创建的资源(WebSocket连接、定时器等)

---

### 2.3 AutoCodeGenerateListener (核心 -- 55KB)

```
public class com.aicode.listener.AutoCodeGenerateListener
  implements com.intellij.openapi.command.CommandListener

  // --- 静态字段 ---
  public static final AtomicBoolean commandNameTab       // Tab 键触发标记
  public static final AtomicBoolean commandNameCtrlZ     // Ctrl+Z 撤销标记
  public static final AtomicBoolean ignoreLookupApply    // 忽略补全应用标记
  public static final AtomicBoolean ignoreApply          // 忽略应用标记
  public static final AtomicBoolean commandName          // 命令名匹配标记
  public static final AtomicBoolean isImitationDealFlag  // 仿写处理标记
  public static final AtomicBoolean inlineChatOperate    // Inline Chat 操作标记
  public static final AtomicBoolean isImitationBuryingPoint  // 仿写埋点标记

  // --- 实例字段 ---
  private final AtomicReference<AutoCodeGenerateListener$T> case  // 撤销快照
  private final Project final           // 关联项目
  private final AtomicBoolean try       // 命令进行中标记
  public final AtomicInteger atomicOperate  // 操作计数器

  // --- 私有静态字段 ---
  private static final Key&lt;CommandCache&gt; float   // 命令缓存 Key
  private static final Logger byte               // SLF4J 日志
  private static final Key<AutoCodeGenerateListener$Q> enum  // 位置快照 Key

  // --- 关键方法 ---

  // 获取文档修改序列号
  private static long wb(Document):
    - 如果 Document instanceof DocumentEx: 返回 getModificationSequence()
    - 否则: 返回 getModificationStamp()

  // 创建编辑器位置快照
  private static AutoCodeGenerateListener$Q Bb(Editor):
    - new Q(wb(editor.getDocument()), editor.getCaretModel().getVisualPosition())

  // 命令开始
  public void commandStarted(CommandEvent):
    1. atomicOperate.getAndIncrement()
    2. 如果 atomicOperate > 0: 记录日志并返回(嵌套命令)
    3. 如果 ignoreApply 为 true: 记录日志并返回
    4. 获取当前选中编辑器:
       a. 如果有文本选择:
          - 创建 CommandCache, 设置 startSelected=true, 记录 selectionStart
          - 存入编辑器 UserData (Key=float)
       b. 如果无文本选择:
          - 清除编辑器 UserData (Key=float)
    5. 检查是否应跳过(BB方法):
       - apiKey 为空 -> 跳过
       - autoTrigger 设置关闭 -> 跳过
    6. 设置 try = true (命令进行中)
    7. 存储编辑器位置快照到 UserData (Key=enum)
    8. 检测命令名:
       - commandName = "completion" (equalsIgnoreCase 解混淆)
       - isImitationDealFlag = "imitation" (equalsIgnoreCase 解混淆)
       - commandNameCtrlZ = 以 "Undo" 或 "撤销" 开头
       - commandNameTab = "Tab" (equalsIgnoreCase 解混淆)
    9. 调用 bb(editor) 处理撤销相关逻辑

  // 命令完成
  public void commandFinished(CommandEvent):
    1. atomicOperate.decrementAndGet()
    2. 如果 atomicOperate > 0: 记录日志并返回
    3. 如果 ignoreLookupApply 为 true:
       - 重置 ignoreLookupApply = false
       - 重置 ignoreApply = false
       - 返回
    4. 如果 ignoreApply 为 true:
       - 重置 ignoreApply = false
       - 返回
    5. 如果 inlineChatOperate 为 true:
       - 重置 inlineChatOperate = false
       - 返回
    6. 获取当前编辑器，检查:
       a. 无文本选择
       b. 有 CommandCache 且 startSelected=true
       c. 光标位置 > startSelectedStartOffset
       d. 选中文本为换行符 -> 清除缓存并返回
    7. 获取 EditorManagerService 实例
    8. 检查命令名标记(commandName)
    9. 检查是否应跳过(BB方法)
    10. 如果正在执行强制代码生成 -> disposeTips + exitForcedCodeGenerateAction
    11. 如果 commandNameTab 为 true -> 重置并返回
    12. 如果 try 为 true 且编辑器非 null:
        a. 检查语言支持
        b. 检查编辑器可用性
        c. 获取位置快照，比较新旧快照:
           - 如果位置改变(xA) -> 设置 isImitationBuryingPoint=true, editorChanged(Automatic)
           - 如果命令为 EmptyRunnable -> editorChanged(Automatic, false)
           - 如果仅修改序列改变(Va) -> disposeTips(CaretChange)

  // 撤销透明操作开始
  public void undoTransparentActionStarted():
    1. 获取当前编辑器
    2. 创建编辑器+文档快照 (Rc方法)
    3. 存入 case 字段 (AtomicReference)

  // 撤销透明操作完成
  public void undoTransparentActionFinished():
    1. 获取 case 中的快照
    2. 清除 case (设为 null)
    3. 获取当前编辑器
    4. 如果编辑器匹配快照中的编辑器且文档修改序列不同:
       - EditorManagerService.isAvailable(editor)
       - hasTipInlays(editor)
       - editorChanged(Forced, false)

  // 检测是否应跳过命令
  private boolean BB(CommandEvent):
    - apiKey 为空 -> 跳过
    - autoTrigger 设置关闭 -> 跳过
    - 否则不跳过

  // 处理撤销时的 APM 跨度
  private void bb(Editor):
    1. 如果 commandNameCtrlZ 为 true:
       a. 获取 RequestTipServiceImpl.LATEST_RESPONSE_DATA
       b. 获取 CODE_TIP_MAP 中的请求
       c. 如果存在: 设置 Span 属性 COMPLETE_RESULT, 结束 Span
       d. 从 CODE_TIP_MAP 和 LAST_REQUEST 中移除
    2. 异常处理: Logger.error

  // 位置快照比较
  private static boolean Va(Q old, Q new):
    - 如果 new.visualPosition != old.visualPosition -> 返回 true (位置改变)
    - 否则返回 false

  // 修改序列比较
  private static boolean xA(Q old, Q new):
    - 如果 new.modificationSequence != old.modificationSequence -> 返回 true
    - 否则返回 false
```

**交互关系**:
- `EditorManagerService` -- 代码补全核心服务
  - `editorChanged(editor, requestType, isCommandName)` -- 触发代码补全请求
  - `disposeTips(editor, actionEnum)` -- 关闭代码提示
  - `isAvailable(editor)` -- 检查编辑器可用性
  - `hasTipInlays(editor)` -- 检查是否有提示 Inlay
- `DocumentActionTracker` -- 文档动作追踪
  - `getExecutingForcedCodeGenerateAction()` -- 是否正在执行强制代码生成
  - `exitForcedCodeGenerateAction()` -- 退出强制代码生成
- `RequestTipServiceImpl` -- 请求提示服务
  - `LATEST_RESPONSE_DATA` -- 最新响应数据 Map
  - `CODE_TIP_MAP` -- 代码提示请求 Map
  - `LAST_REQUEST` -- 最后请求 Map
- `CommandCache` -- 命令缓存(域对象)
- `PluginStartupActivity.getApiKey()` -- 获取 API Key
- `AICodeSettingsState.autoTrigger` -- 自动触发设置

---

### 2.4 AutoCodeGenerateListener$Q (位置快照)

```
public final class com.aicode.listener.AutoCodeGenerateListener$Q
  // --- 字段 ---
  private final VisualPosition byte  // 光标可视位置
  private final long enum           // 文档修改序列号

  // --- 构造 ---
  Q(long modificationSequence, VisualPosition visualPosition)
```

---

### 2.5 AutoCodeGenerateListener$T (撤销快照)

```
public final class com.aicode.listener.AutoCodeGenerateListener$T
  // --- 字段 ---
  private final Editor byte  // 编辑器引用
  private final long enum    // 文档修改序列号

  // --- 方法 ---
  public Editor WA()  // 获取编辑器(含 null 检查)

  // --- 构造 ---
  T(Editor editor, long modificationSequence)
```

---
