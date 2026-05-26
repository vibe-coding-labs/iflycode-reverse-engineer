# iFlyCode 请求管理与代码补全系统分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-11

## 1. 请求管理系统

### 1.1 概述

iFlyCode 的请求管理负责代码补全请求的调度、取消、超时和重试。核心类位于 `com/aicode/request/` 包。

### 1.2 核心类

#### RequestResultList

**路径**: `com/aicode/request/RequestResultList`
**职责**: 请求结果列表 — 管理所有活跃的补全请求

**关键方法**:
- `addRequestResult(RequestResult)` — 添加请求结果
- `removeRequestResult(RequestResult)` — 移除请求结果
- `getRequestResult(String requestId)` — 获取指定请求结果
- `clearAll()` — 清除所有请求结果

**功能**: 维护一个请求 ID → 请求结果的映射表，用于追踪所有活跃的补全请求。

#### RequestResult

**路径**: `com/aicode/request/RequestResult`
**职责**: 单个补全请求的结果

**字段**:
- `requestId` — 请求唯一标识
- `result` — 补全结果文本
- `isCompleted` — 是否已完成
- `isCancelled` — 是否已取消

#### RequestCancelException

**路径**: `com/aicode/request/RequestCancelException`
**父类**: `RuntimeException`
**职责**: 请求取消异常

#### RequestTimeoutException

**路径**: `com/aicode/request/RequestTimeoutException`
**父类**: `RuntimeException`
**职责**: 请求超时异常

### 1.3 请求生命周期

```
1. 用户输入 → 触发代码补全
   └── RequestTipService.createRequest()
        ├── 生成 requestId
        ├── 创建 RequestResult
        └── RequestResultList.addRequestResult()

2. 请求发送到 Agent
   └── CommonService.sendWsMessage(CODE_COMPLETE, data)
        └── PluginWebsocketClient.send()

3a. 正常响应
   └── SocketMessageHandleListener.onMessage()
        ├── RequestResult.setResult(text)
        ├── RequestResult.setCompleted(true)
        └── 渲染补全提示到编辑器

3b. 用户继续输入 → 取消前一个请求
   └── RequestTipService.cancelPreviousRequest()
        ├── RequestResult.setCancelled(true)
        └── RequestResultList.removeRequestResult()

3c. 请求超时
   └── RequestTimeoutException
        └── 清理补全提示
```

## 2. 代码补全系统

### 2.1 概述

iFlyCode 的代码补全系统基于 IntelliJ 的 Inlay Presentation API 实现，支持单行和多行补全。补全请求通过 WebSocket 发送到 Agent 进程，Agent 返回补全建议后渲染为 Inlay Hint。

### 2.2 核心类

#### RequestTipService (接口)

**路径**: `com/aicode/complete/RequestTipService`
**职责**: 代码补全请求服务接口

**关键方法**:
- `requestTip(Editor, int offset)` — 请求代码补全
- `cancelRequestTip()` — 取消当前补全请求
- `isRequesting()` — 是否正在请求中

#### RequestTipServiceImpl

**路径**: `com/aicode/complete/RequestTipServiceImpl`
**实现**: `RequestTipService`
**职责**: 代码补全请求服务实现

**关键依赖**:
- `PluginWebsocketClient` — WebSocket 客户端
- `CodeCompleteService` — 代码补全服务
- `RequestResultList` — 请求结果列表
- `AICodeSettingsState` — 设置状态
- `OpenTelemetryUtil` — APM 追踪

**关键方法**:
- `requestTip(Editor, int offset)` — 请求补全
  1. 检查是否启用自动补全
  2. 检查当前文件语言是否支持
  3. 收集代码上下文（prefix, suffix, 文件路径, 语言）
  4. 发送 `CODE_COMPLETE` 命令到 Agent
  5. 启动 APM Span 追踪
- `cancelRequestTip()` — 取消补全
  1. 发送 `ABORT` 命令到 Agent
  2. 清理 Inlay Hint
  3. 关闭 APM Span

#### CancelRequestTip

**路径**: `com/aicode/complete/CancelRequestTip`
**职责**: 取消补全请求

**功能**: 封装补全请求的取消逻辑，包括:
- 发送 ABORT 命令
- 清理编辑器中的 Inlay Hint
- 重置补全状态

#### InlayPresentationFactory

**路径**: `com/aicode/complete/InlayPresentationFactory`
**职责**: Inlay 展示工厂 — 创建补全提示的视觉元素

**关键方法**:
- `createInlayPresentation(String text, Editor)` — 创建 Inlay 展示
- 创建单行补全展示
- 创建多行补全展示

**Inlay 展示类型**:
- **单行补全**: 灰色文字显示在光标后
- **多行补全**: 代码块展示在当前行下方

#### DeclarativeInlayHintsProvider

**路径**: `com/aicode/complete/DeclarativeInlayHintsProvider`
**职责**: 声明式 Inlay Hint 提供者

**实现**: IntelliJ 的 `InlayHintsProvider` 接口

**功能**: 注册到 IntelliJ 的 Inlay Hint 系统，使 iFlyCode 的补全提示与 IDE 原生 Inlay Hint 框架集成。

### 2.3 补全触发机制

```
触发方式:
1. 自动触发 (autoTrigger = true)
   └── DocumentListener.onDocumentChange()
        ├── 防抖处理 (triggerTime 延迟)
        └── RequestTipService.requestTip()

2. 手动触发
   └── CodeCompleteAction.actionPerformed()
        └── RequestTipService.requestTip()

3. Tab 键触发
   └── CodeCompleteTabAction.actionPerformed()
        └── 接受当前补全建议
```

### 2.4 补全数据流

```
┌──────────────────────────────────────────────────────────────┐
│                    IntelliJ Editor                            │
│  DocumentListener                                             │
│    └── 防抖 (triggerTime ms)                                  │
│        └── RequestTipServiceImpl.requestTip()                 │
│              ├── 收集上下文:                                    │
│              │   ├── prefix (光标前文本)                       │
│              │   ├── suffix (光标后文本)                       │
│              │   ├── filePath (文件路径)                       │
│              │   ├── language (编程语言)                       │
│              │   └── lineCount (文件行数)                      │
│              └── APM Span: CODE_COMPLETE                      │
└──────────────────────────────────────────────────────────────┘
                           │
                    WebSocket: CODE_COMPLETE
                           │
┌──────────────────────────────────────────────────────────────┐
│                    Node.js Agent                              │
│  Code Complete Handler                                        │
│    ├── Tree-sitter 解析代码结构                                │
│    ├── 调用 AI 模型生成补全                                    │
│    └── 流式返回补全结果                                        │
└──────────────────────────────────────────────────────────────┘
                           │
                    WebSocket: Response (stream)
                           │
┌──────────────────────────────────────────────────────────────┐
│                    IntelliJ Editor                            │
│  CodeCompleteService.dealStreamAgentTips()                    │
│    ├── 解析流式响应                                            │
│    ├── InlayPresentationFactory 创建展示                       │
│    ├── 渲染到编辑器 (灰色文字/代码块)                           │
│    └── APM: COMPLETE_DURATION, COMPLETE_FIRST_DURATION        │
│                                                              │
│  用户操作:                                                    │
│    ├── Tab → 接受补全 (插入文本)                               │
│    ├── Esc → 拒绝补全 (移除 Inlay)                            │
│    └── 继续输入 → 取消补全 (ABORT + 清理)                      │
└──────────────────────────────────────────────────────────────┘
```

### 2.5 代码增强模式

`enableCodeEnhance` 标志控制代码增强模式:
- **关闭**: 仅返回 AI 生成的补全文本
- **开启**: Agent 可能返回额外的代码改进建议（如优化、重构建议）

### 2.6 语言支持与禁用

`codeCompleteDisableLang` 字段允许用户禁用特定语言的代码补全:
- 格式: 逗号分隔的语言 ID 列表
- 通过 `LanguageFileExtensionDetails` 映射语言到文件扩展名

## 3. 关键发现

1. **请求取消机制**: 用户继续输入时自动取消前一个补全请求（发送 ABORT 命令），避免过时的补全结果显示。

2. **双触发模式**: 支持自动触发（防抖延迟）和手动触发（快捷键），自动触发延迟时间可配置。

3. **流式补全**: Agent 通过 WebSocket 流式返回补全结果，`dealStreamAgentTips()` 逐步渲染，实现打字机效果。

4. **APM 全链路追踪**: 代码补全从请求到渲染全链路追踪，包含首字耗时 (TTFT) 和总耗时两个关键指标。

5. **Inlay Presentation API**: 使用 IntelliJ 的 Inlay Presentation API 渲染补全提示，而非自绘 UI，与 IDE 原生体验一致。

6. **请求结果管理**: `RequestResultList` 维护所有活跃请求的映射表，支持按 ID 查询和批量清理。

7. **异常体系**: `RequestCancelException` 和 `RequestTimeoutException` 分别处理取消和超时场景，继承自 `RuntimeException`。
