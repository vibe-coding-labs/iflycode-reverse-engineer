# iFlyCode Complete 包与 Inlay 提示系统分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-11

## 1. 概述

`com/aicode/complete/` 包实现代码补全的 Inlay 提示系统，包括快捷键提示组件、GotIt 引导提示和 Inlay 更新监听。该包与 IntelliJ 的 Inlay Hint 框架深度集成。

## 2. 核心类

### 2.1 InlayCompletionHintFactory (129 strings)

**路径**: `com/aicode/complete/InlayCompletionHintFactory`
**职责**: Inlay 补全提示工厂 — 创建和显示补全快捷键提示

**关键方法**:
- `showHintAtPosition(Editor, JComponent, int, int)` — 在指定位置显示提示
- `showHintAtCaret(Editor, JComponent)` — 在光标位置显示提示
- `createAndShowHint(Editor, int)` — 创建并显示提示
- `createHintHint()` — 创建 HintHint 对象
- `createInformationComponent()` — 创建信息组件

**提示配置**:
- `info` — 提示类型
- `timeout` — 显示超时
- `reviveOnEditorChange` — 编辑器变更时恢复
- `setIconOnTheRight` — 图标在右侧

**内部类**: `InlineKeybindingHintComponent` — 快捷键提示 UI 组件

**关键依赖**:
- `GitReviewService` — Git 评审服务（H() 解码）
- `PropertyUtils` — 属性工具（H() 解码）

### 2.2 InlayCompletionHintFactory$InlineKeybindingHintComponent (60 strings)

**路径**: `com/aicode/complete/InlayCompletionHintFactory$InlineKeybindingHintComponent`
**职责**: 内联快捷键提示组件 — 显示补全快捷键的 UI 面板

**功能**:
- 继承 `JPanel` — Swing 面板
- 使用 `Style.Borders` 设置边框
- 设置背景色、不透明度
- 添加子组件到面板

**关键依赖**:
- `IdeAction` — IDE Action（H() 解码）
- `Maps` — Map 工具（H() 解码）

### 2.3 InlayGotItListener (134 strings)

**路径**: `com/aicode/complete/InlayGotItListener`
**职责**: Inlay GotIt 引导监听 — 首次使用补全时的引导提示

**关键方法**:
- `inlaysUpdated()` — Inlay 更新回调
- `isCancelled()` — 是否已取消

**GotIt 提示配置**:
- `.inlayGotIt` — GotIt 提示标识
- `ToolWindowIcon` — 使用工具窗口图标
- `withIcon` — 带图标
- `tooltip` — 提示文本
- `getContentComponent()` — 获取内容组件

**关键依赖**:
- `InlayListener` — Inlay 监听器
- `EditorRequestService` — 编辑器请求服务
- `BasicActionsBundle` — 消息包
- `CodeCompleteService` — 代码补全服务
- `Icons` — 图标
- `OperateActionEnum` — 操作动作枚举

### 2.4 InlayListener (23 strings)

**路径**: `com/aicode/complete/InlayListener`
**职责**: Inlay 监听器 — 补全 Inlay 更新事件监听

**关键方法**:
- `inlaysUpdated()` — Inlay 更新回调

**关键依赖**:
- `FileExtensionLanguageDetails` — 文件扩展语言映射（H() 解码）

## 3. Inlay 提示系统架构

```
┌──────────────────────────────────────────────────────────────┐
│                    IntelliJ Editor                            │
│                                                              │
│  代码补全触发 → Agent 返回补全文本                            │
│       │                                                      │
│       ▼                                                      │
│  InlayPresentationFactory                                    │
│    ├── 单行补全 → 灰色文字 Inlay                              │
│    └── 多行补全 → 代码块 Inlay                                │
│                                                              │
│  InlayCompletionHintFactory                                  │
│    └── 快捷键提示 (Tab 接受, Esc 拒绝)                       │
│        └── InlineKeybindingHintComponent (JPanel)            │
│                                                              │
│  InlayGotItListener                                          │
│    └── 首次使用引导 → GotItTooltip                           │
│        ├── .inlayGotIt 标识                                  │
│        ├── ToolWindowIcon                                    │
│        └── tooltip 文本                                      │
│                                                              │
│  InlayListener                                               │
│    └── inlaysUpdated() → 通知 UI 更新                        │
└──────────────────────────────────────────────────────────────┘
```

## 4. 提示显示流程

```
1. 补全结果到达 → InlayPresentationFactory 创建 Inlay
   └── 渲染到编辑器

2. 首次显示补全 → InlayGotItListener.inlaysUpdated()
   └── 检查是否首次使用
       └── 是 → 显示 GotItTooltip
           ├── 标识: .inlayGotIt
           ├── 图标: ToolWindowIcon
           └── 提示: "按 Tab 接受补全"

3. 快捷键提示 → InlayCompletionHintFactory.showHintAtCaret()
   └── 创建 InlineKeybindingHintComponent
       ├── 设置边框 (Style.Borders)
       ├── 设置背景色
       └── 显示在光标位置

4. 用户操作:
   ├── Tab → 接受补全 (CodeCompleteTabAction)
   ├── Esc → 拒绝补全
   └── 继续输入 → 取消补全 (ABORT)
```

## 5. 关键发现

1. **三层提示系统**: 补全 Inlay（灰色文字）+ 快捷键提示（HintFactory）+ 首次引导（GotIt），构成完整的用户引导体系。

2. **GotIt 机制**: 使用 IntelliJ 的 `GotItTooltip` API 实现首次使用引导，`.inlayGotIt` 标识确保只显示一次。

3. **HintHint 配置**: `InlayCompletionHintFactory` 使用 IntelliJ 的 `HintHint` 对象配置提示位置、超时和生命周期。

4. **H() 混淆**: 所有类都使用 H() 方法解码字符串，`InlayCompletionHintFactory` 依赖 `GitReviewService.H()` 和 `PropertyUtils.H()`，说明提示文本可能包含混淆的配置信息。

5. **轻量组件**: `InlineKeybindingHintComponent` 继承 JPanel，使用 `Style.Borders` 设置边框，与 `Style` 类（com/aicode/ui/Style）共享 UI 样式系统。

6. **InlayListener 最简**: 只有 23 个字符串，是最轻量的类，仅监听 Inlay 更新事件并通知 `FileExtensionLanguageDetails`。
