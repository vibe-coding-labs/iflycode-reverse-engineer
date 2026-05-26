# 33 Q/ 包功能分析

> 基于 class 文件常量池静态分析

## 核心发现

Q/ 包的三个类 **不是** 加密/签名/认证相关代码，而是 **内部类的混淆包装**。
它们是其他类的匿名内部类，被混淆器重命名到 Q/ 包下以增加逆向难度。

## 类映射

| 混淆名 | 实际所属类 | 类型 | 功能 |
|--------|-----------|------|------|
| `Q.q` | `com.aicode.enums.CodeTipType` | 内部类 | `CodeEditorInlay` 的默认实现，包含 type/editorOffset/completionLines 字段 |
| `Q.sa` | `com.aicode.action.click.PluginAnAction` | 内部类 | 编辑器 Action 处理器，执行 `doCycleAction()` 和错误提示 |
| `Q.ua` | `com.aicode.agent.service.GitReviewService` | 内部类 | `EditorActionHandler` 子类，处理 ESC 拒绝操作 (`OperateActionEnum.EscReject`) |

## Q.q — CodeEditorInlay 默认实现

```
字段:
  type: CodeTipType          — 补全类型 (Inline/AfterLineEnd/Block)
  editorOffset: int          — 编辑器偏移量
  completionLines: List<String> — 补全行列表

方法:
  setLines(List<String>)     — 设置补全行
  getLines(): List<String>   — 获取补全行
  getEditorOffset(): int     — 获取偏移量
  setEditorOffset(int)       — 设置偏移量
  getType(): CodeTipType     — 获取类型
  setType(CodeTipType)       — 设置类型
  toString(): String         — "DefaultAICodeEditorInlay(type=X, editorOffset=Y, completionLines=Z)"

混淆字符串引用:
  H() 调用来自: ConditionalActionConfiguration, ChatInputController
  包含 6+ 个 XOR 编码字符串
```

## Q.sa — PluginAnAction 内部类

```
继承: com.intellij.openapi.actionSystem.AnAction

方法:
  actionPerformed(AnActionEvent) — 处理 Action 事件
    1. 获取 Editor
    2. 调用 doCycleAction(Editor)
    3. 失败时通过 HintManager.showErrorHint() 显示错误

  update(AnActionEvent) — 更新 Action 状态
    1. 获取 EditorManagerService
    2. 检查 hasTipInlays()
    3. 设置 Presentation.setEnabled()

混淆字符串引用:
  H() 调用来自: RequestCancelException, Application
  getWarningHintText() 返回一个 XOR 编码字符串
  包含 8+ 个 XOR 编码字符串
```

## Q.ua — GitReviewService 内部类

```
继承: com.intellij.openapi.editor.actionSystem.EditorActionHandler

方法:
  isEnabledForCaret(Editor, Caret, DataContext): boolean
    — 检查 Editor 非空且 EditorManagerService.hasTipInlays()

  doExecute(Editor, Caret, DataContext): void
    — 调用 EditorManagerService.disposeTips(editor, OperateActionEnum.EscReject)
    — ESC 键拒绝当前补全

  executeInCommand(Editor, DataContext): boolean
  isAvailable(): boolean

混淆字符串引用:
  H() 调用来自: PropertyUtils, GitReviewService
  包含 6+ 个 XOR 编码字符串
```

## 对逆向分析的影响

1. **Q/ 包不再是 P0 优先级** — 它们是简单的内部类，不涉及加密/签名
2. **混淆字符串解码仍然是 P0** — 所有三个类都大量使用 H() 方法
3. **Q.q 揭示了 CodeEditorInlay 的数据结构** — 这是代码补全渲染的核心数据类型
4. **Q.ua 揭示了 ESC 拒绝机制** — `OperateActionEnum.EscReject` → `disposeTips()`

## 字段名混淆映射

| 混淆字段 | 实际类型 | 实际用途 |
|---------|---------|---------|
| `Q.q.cg` | ? | CodeEditorInlay 内部字段 |
| `Q.sa.dh` | ? | PluginAnAction 内部字段 |
| `Q.sa.Nd` | method | (Editor, String) → void |
| `Q.ua.yl` | ? | EditorActionHandler 内部字段 |
| `Q.ua.Yf` | method | (Editor) → boolean |
