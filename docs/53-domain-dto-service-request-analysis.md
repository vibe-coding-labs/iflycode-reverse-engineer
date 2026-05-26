# iFlyCode Domain/DTO/Service/Request 层深度分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-11

## 1. 概述

本文档分析 iFlyCode 的 Domain 模型层、DTO 层、Service 接口层和 Request 层，这些层构成代码补全系统的核心数据流管道。

## 2. Domain 模型层 (com/aicode/domain)

### 2.1 CommandCache (49 strings)

**路径**: `com/aicode/domain/CommandCache`
**职责**: 命令缓存 — 缓存编辑器选区状态

**字段**:
- `startSelected` — boolean — 起始是否选中
- `startSelectedStartOffset` — int — 起始选中偏移
- `endSelected` — boolean — 结束是否选中
- `endSelectedStartOffset` — int — 结束选中偏移
- `result` — Object — 缓存结果

**Lombok**: 使用 `@Data` 注解（canEqual 方法存在）

### 2.2 GetTipsResult (72 strings) + GetTipsResult$Tip (79 strings)

**路径**: `com/aicode/domain/GetTipsResult`
**职责**: 补全结果 — Agent 返回的补全建议集合

**GetTipsResult 字段**:
- `tips` — `List<Tip>` — 补全提示列表

**GetTipsResult.Tip 字段**:
- `uuid` — String — 唯一标识
- `text` — String — 补全文本
- `range` — Range — 代码范围
- `displayText` — String — 显示文本
- `position` — Position — 光标位置

### 2.3 LineInfo (108 strings)

**路径**: `com/aicode/domain/LineInfo`
**职责**: 行信息 — 编辑器光标所在行的详细信息

**字段**:
- `lineCount` — int — 文件总行数
- `lineNumber` — int — 当前行号
- `lineStartOffset` — int — 当前行起始偏移
- `columnOffset` — int — 列偏移
- `line` — String — 当前行内容
- `nextLineIndent` — int — 下一行缩进

**关键方法**:
- `getLinePrefix()` — 获取光标前文本
- `getLineSuffix()` — 获取光标后文本
- `isBlankLine()` — 是否空行
- `getWhitespaceBeforeCursor()` — 获取光标前空白
- `getLineEndOffset()` — 获取行结束偏移
- `calculateNextLineIndent(Document, int)` — 计算下一行缩进

**关键依赖**:
- `AICodeStringUtil.trailingWhitespace()` — 尾部空白
- `EditorUtil.whitespacePrefixLength()` — 前导空白长度

### 2.4 Position (108 strings)

**路径**: `com/aicode/domain/Position`
**职责**: 位置 — LSP 风格的行列位置

**字段**:
- `line` — int — 行号
- `character` — int — 列号

**关键方法**:
- `toOffset(Editor)` — 转换为编辑器偏移
- `getLineNumber()` — 获取行号
- `getColumnOffset()` — 获取列偏移
- `lineColToOffset()` — 行列转偏移
- `getCursorPosition(Editor)` — 获取光标位置

**toString**: `Position(line=, character=)`

### 2.5 Range (71 strings)

**路径**: `com/aicode/domain/Range`
**职责**: 范围 — LSP 风格的代码范围

**字段**:
- `start` — Position — 起始位置
- `end` — Position — 结束位置

**toString**: `Range(start=, end=)`

### 2.6 Suggestion (63 strings)

**路径**: `com/aicode/domain/Suggestion`
**职责**: 补全建议 — 带评分的补全建议

**字段**:
- `score` — double — 评分
- `type` — String — 类型
- `hash` — String — 哈希
- `inlays` — List<CodeInlayList> — Inlay 列表

**toString**: `Suggestion{score=, type='', hash='', inlays=}`

### 2.7 VirtualFileUri (132 strings)

**路径**: `com/aicode/domain/VirtualFileUri`
**职责**: 虚拟文件 URI — IntelliJ VirtualFile 的 URI 表示

**字段**:
- `uri` — String — URI 字符串
- `fileSystem` — VirtualFileSystem — 文件系统
- `path` — String — 路径
- `prefix` — String — URI 前缀

**关键方法**:
- `isNeedsPathPrefix(VirtualFileSystem)` — 是否需要路径前缀
- `asPrefixedUri(String)` — 添加前缀的 URI
- `constructUrl()` — 构造 URL
- `getUrl()` — 获取 URL

**URI 格式**:
- `file:///path` — 标准文件 URI
- `file://path` — 无斜杠前缀
- `file:////path` — TempFileSystem 特殊格式

**内部类**: `TypeAdapter` — Gson 序列化适配器

## 3. DTO 层 (com/aicode/dto)

### 3.1 FileIndexDto (43 strings)

**路径**: `com/aicode/dto/FileIndexDto`
**职责**: 文件索引 DTO — WebSocket 请求中的文件上下文

**字段**:
- `filePath` — String — 文件路径
- `fileName` — String — 文件名
- `selectStartLine` — int — 选区起始行
- `selectEndLine` — int — 选区结束行

### 3.2 GitResponseDTO (81 strings)

**路径**: `com/aicode/dto/GitResponseDTO`
**职责**: Git 响应 DTO — Git 操作响应

**字段**:
- `status` — Integer — 状态码
- `repoUrl` — String — 仓库 URL
- `repoId` — String — 仓库 ID
- `branch` — String — 分支
- `command` — String — 命令
- `repoName` — String — 仓库名称
- `code` — String — 代码

**toString**: `GitResponseDTO(status=, repoUrl=, repoId=, branch=, command=, repoName=, code=)`

## 4. Request 层 (com/aicode/request)

### 4.1 AgentCodeTip (110 strings)

**路径**: `com/aicode/request/AgentCodeTip`
**职责**: Agent 代码提示 — Agent 返回的补全结果封装

**字段**:
- `agentData` — GetTipsResult$Tip — Agent 原始数据
- `completion` — List<String> — 补全文本行列表
- `isCached` — boolean — 是否来自缓存
- `requestId` — String — 请求 ID
- `scene` — String — 场景
- `language` — String — 语言

**关键方法**:
- `getTip()` — 获取补全列表（实现 CodeTip 接口）
- `asCached()` — 标记为缓存
- `withCompletion(List)` — 创建带补全的副本
- `getDisplayText()` — 获取显示文本

**toString**: `AgentCodeTip{agentData=, completion=, requestId='', scene='', language=''}`

### 4.2 CodeGenerateEditorRequest (251 strings)

**路径**: `com/aicode/request/CodeGenerateEditorRequest`
**职责**: 代码生成编辑器请求 — 补全请求的完整上下文

**字段**:
- `completionType` — TipType — 补全类型
- `useTabIndents` — boolean — 是否使用 Tab 缩进
- `tabWidth` — int — Tab 宽度
- `requestId` — int — 请求 ID
- `fileLanguage` — AICodeLanguageInfo — 文件语言
- `uri` — VirtualFileUri — 文件 URI
- `documentContent` — String — 文档内容
- `offset` — int — 光标偏移
- `lineInfo` — LineInfo — 行信息
- `requestTimestamp` — long — 请求时间戳
- `documentModificationSequence` — long — 文档修改序列号
- `isCancelled` — boolean — 是否已取消

**关键方法**:
- `equalsRequest(CodeGenerateEditorRequest)` — 判断是否相同请求
- `getFileExtension()` — 获取文件扩展名
- `getFileName()` — 获取文件名
- `getFileNameSuffix()` — 获取文件名后缀
- `getSessionController()` — 获取会话控制器
- `getEditCache()` — 获取编辑缓存
- `findLanguageMapping()` — 查找语言映射

**关键依赖**:
- `EditorRequestService` — 编辑器请求服务
- `CodeCompleteService` — 代码补全服务
- `VirtualFileUri` — 虚拟文件 URI
- `LineInfo` — 行信息
- `TipType` — 补全类型
- `LanguageInfoManager` — 语言信息管理
- `EditorCacheUtil` — 编辑器缓存
- `IndentLineUtil` — 缩进工具
- `RequestId` — 请求 ID 生成器
- `Guava Maps` — Google Guava Maps 工具

### 4.3 RequestId (17 strings)

**路径**: `com/aicode/request/RequestId`
**职责**: 请求 ID 生成器 — 生成递增的请求 ID

**字段**:
- `currentRequestId` — int — 当前请求 ID（原子递增）

## 5. Service 接口层 (com/aicode/service)

### 5.1 EditorManagerService (138 strings)

**路径**: `com/aicode/service/EditorManagerService`
**职责**: 编辑器管理服务 — 代码补全的核心控制器

**关键方法**:
- `editorChanged(Editor, int, CodeTipRequestType, boolean)` — 编辑器变更（触发补全）
- `showNextInlaySet(Editor)` — 显示下一组 Inlay
- `showPreviousInlaySet(Editor)` — 显示上一组 Inlay
- `hasNextInlaySet(Editor)` — 是否有下一组
- `hasPreviousInlaySet(Editor)` — 是否有上一组
- `acceptTip(Editor)` — 接受补全
- `acceptTipForLine(Editor)` — 接受行级补全
- `acceptWordTip(Editor)` — 接受逐词补全
- `cancelTipRequests(Editor)` — 取消补全请求
- `disposeTips(Editor)` — 清除补全
- `countTipInlays(Editor, TextRange, ...)` — 计算 Inlay 数量
- `hasCacheData(Editor, char)` — 是否有缓存数据
- `hasTipInlays(Editor)` — 是否有补全 Inlay
- `isAvailable(Editor)` — 编辑器是否可用
- `enableCodeComplete` — 启用代码补全

**关键依赖**:
- `RequestTimeoutException` — 超时异常
- `OpenTelemetryUtil` — APM 追踪
- `AICodeSettingsState` — 设置状态
- `CodeTipRequestType` — 补全请求类型

### 5.2 RequestTipService (40 strings)

**路径**: `com/aicode/service/RequestTipService`
**职责**: 补全请求服务接口 — 定义补全请求 API

**关键方法**:
- `createRequest(Editor, CodeTipRequestType)` — 创建补全请求
- `fetchTips(EditorRequestService)` — 获取补全
- `fetchCachedTips(EditorRequestService)` — 获取缓存补全
- `fetchInlineChatContent(...)` — 获取内联聊天内容
- `createInlineChatRequest(...)` — 创建内联聊天请求
- `dealStreamAgentTips(...)` — 处理流式 Agent 补全
- `dealAgentTips(...)` — 处理 Agent 补全
- `getService(Project)` — 获取服务实例

### 5.3 EditorRequestService (63 strings)

**路径**: `com/aicode/service/EditorRequestService`
**职责**: 编辑器请求服务 — 补全请求的编辑器上下文

**关键方法**:
- `getDocumentContent()` — 获取文档内容
- `getOffset()` — 获取光标偏移
- `getFileLanguage()` — 获取文件语言
- `getLineInfo()` — 获取行信息
- `getCompletionType()` — 获取补全类型
- `getRequestId()` — 获取请求 ID
- `equalsRequest(EditorRequestService)` — 判断是否相同请求
- `getFileName()` — 获取文件名
- `getTabWidth()` — 获取 Tab 宽度
- `getSessionController()` — 获取会话控制器
- `getDocumentModificationSequence()` — 获取文档修改序列号

### 5.4 其他 Service 接口/类

| 类 | 职责 |
|----|------|
| CodeTip | 补全提示接口 — getTip(), isCached(), asCached() |
| CodeInlayList | Inlay 列表 — getInlays(), getReplacementRange(), getReplacementText() |
| CodeEditorInlay | 代码编辑器 Inlay — getLines(), getEditorOffset(), getType() |
| TipCache | 补全缓存接口 — getLatest(), updateLatest(), isLatestPrefix() |
| TipRenderer | 补全渲染接口 — getType(), getContentLines(), getInlay() |
| EditorSupport | 编辑器支持 — isEditorCodeTipsSupported(), isCodeTipsEnabled() |
| LanguageInfoSupport | 语言信息支持 — findVSCodeLanguageMapping() |
| ProcessStatusListener | 进程状态监听 — onAgentProcessRestart() |
| ProjectService | 项目服务 — 空类 |
| RejectTipMessage | 拒绝补全消息 — automaticCodeTipsRejected |
| TipReceivedMessage | 补全接收消息 — inlaysReceived |
| RequestCancellable | 可取消请求 — cancel(), isCancelled() |
| RequestsCancelledService | 取消服务 — requestsCancelled() |

## 6. Service/editor 实现层

### 6.1 EditorManagerServiceImpl (核心实现)

**内部类**:
- `$B` — CodeTipType switch 映射 (Inline, AfterLineEnd)
- `$F` — 流式响应处理器（最关键）

**$F 关键方法**:
- `onComplete()` — 流式完成回调
- `onError()` — 错误回调
- `onNext(ResponseData)` — 流式数据回调
- `cancel()` — 取消
- `accept()` — 接受

**关键日志**: `【code complete inlay finished】，代码补全总耗时【】毫秒`

**关键依赖**:
- `ResponseStreamDto$ResponseData` — Agent 流式响应
- `RequestResultList` — 请求结果列表
- `TipReceivedMessage` — 补全接收消息
- `RequestTipServiceImpl` — 补全请求实现

### 6.2 AgentCodeTipList (141 strings)

**路径**: `com/aicode/service/editor/AgentCodeTipList`
**职责**: Agent 补全列表 — 将 Agent 响应转换为 CodeInlayList

**关键方法**:
- `getAICodeTip()` — 获取 AI 代码提示
- `getInlays()` — 获取 Inlay 列表
- `getReplacementRange()` — 获取替换范围
- `getReplacementText()` — 获取替换文本
- `dropOverlappingTrailingLines()` — 去除重叠尾部行

### 6.3 CancelRequestTip (84 strings)

**路径**: `com/aicode/service/editor/CancelRequestTip`
**职责**: 取消补全请求 — 管理请求取消和替换

**关键方法**:
- `cancelAllRequests()` — 取消所有请求
- `cancelAllAndAddRequest()` — 取消所有并添加新请求
- `addRequest()` — 添加请求

### 6.4 DocumentActionTracker (77 + 119 strings)

**路径**: `com/aicode/service/editor/DocumentActionTracker`
**职责**: 文档操作追踪 — 监听编辑器 Action 事件

**内部类**: `ActionListener` — Action 事件监听器

**关键方法**:
- `beforeActionPerformed()` — Action 执行前
- `afterActionPerformed()` — Action 执行后
- `handleUndoAction()` — 处理撤销
- `exitForcedCodeGenerateAction()` — 退出强制补全
- `getExecutingForcedCodeGenerateAction()` — 是否正在执行强制补全
- `editorChanged()` — 编辑器变更

### 6.5 CodeTipTypedHandlerDelegate (81 strings)

**路径**: `com/aicode/service/editor/CodeTipTypedHandlerDelegate`
**职责**: 补全输入处理 — 监听字符输入触发自动补全

**关键方法**:
- `charTyped(char, Project, Editor)` — 字符输入回调
- `checkAutoPopup()` — 检查是否自动弹出

## 7. 数据流总图

```
用户输入字符
    │
    ▼
CodeTipTypedHandlerDelegate.charTyped()
    │
    ├── hasCacheData() → 命中缓存 → 直接显示
    └── 未命中 → 触发补全请求
        │
        ▼
DocumentActionTracker.ActionListener.beforeActionPerformed()
    │
    ▼
EditorManagerService.editorChanged(editor, offset, requestType, forced)
    │
    ▼
RequestTipService.createRequest(editor, requestType)
    │
    ├── 构建 CodeGenerateEditorRequest
    │   ├── completionType (TipType)
    │   ├── fileLanguage (AICodeLanguageInfo)
    │   ├── uri (VirtualFileUri)
    │   ├── documentContent (String)
    │   ├── offset (int)
    │   ├── lineInfo (LineInfo)
    │   └── requestTimestamp (long)
    │
    ▼
CancelRequestTip.cancelAllAndAddRequest()
    │
    ▼
PluginWebsocketClient.sendWsMessage(CODE_COMPLETE, data)
    │
    ▼
Agent → AI 模型 → 流式响应
    │
    ▼
EditorManagerServiceImpl$F.onNext(ResponseData)
    │
    ├── AgentCodeTipList 构建
    │   ├── GetTipsResult$Tip 解析
    │   ├── Range/Position 转换
    │   └── dropOverlappingTrailingLines()
    │
    ├── AgentCodeTip 封装
    │   ├── completion (List<String>)
    │   ├── requestId, scene, language
    │   └── isCached = false
    │
    ├── SimpleCodeTipCache 缓存
    │   └── CacheKey(promptHash, isMultiline)
    │
    ▼
InlayPresentationFactory 渲染
    │
    ▼
编辑器 Inlay Hint 显示
    │
    ▼
用户操作:
    ├── Tab → EditorManagerService.acceptTip()
    ├── Ctrl+Right → EditorManagerService.acceptWordTip()
    ├── Alt+/ → 切换下一组 showNextInlaySet()
    └── 继续输入 → CancelRequestTip.cancelAllRequests()
```

## 8. 关键发现

1. **LSP 风格**: Position(line, character) 和 Range(start, end) 采用 LSP (Language Server Protocol) 风格，与 Agent 端 (VS Code 风格) 保持一致。

2. **VirtualFileUri 智能前缀**: 根据文件系统类型（TempFileSystem vs 标准）自动添加 `file:///` 前缀，确保 URI 格式正确。

3. **请求去重**: `CodeGenerateEditorRequest.equalsRequest()` 通过比较文档内容、偏移、修改序列号等判断是否为相同请求，避免重复补全。

4. **Guava 依赖**: `CodeGenerateEditorRequest` 使用 Google Guava 的 `Maps` 工具类，这是除 Gson 和 Hutool 外的第三个外部依赖。

5. **流式完成日志**: `【code complete inlay finished】，代码补全总耗时【】毫秒` — 中文日志，说明面向国内用户。

6. **CodeTip 接口体系**: `CodeTip` (接口) → `AgentCodeTip` (实现)，支持 `getTip()`, `isCached()`, `asCached()`, `withCompletion()` 操作，构成补全结果的不可变数据流。

7. **三层取消机制**: `RequestCancellable` (可取消接口) → `CancelRequestTip` (取消管理) → `RequestsCancelledService` (全局取消通知)，确保补全请求在任何层级都能被正确取消。

8. **DocumentActionTracker**: 追踪所有编辑器 Action 事件（包括撤销），在 `beforeActionPerformed` 和 `afterActionPerformed` 中触发补全逻辑，是编辑器事件到补全触发的桥梁。

9. **LineInfo 丰富**: 提供光标前文本 (prefix)、光标后文本 (suffix)、空白行检测、前导空白、下一行缩进等详细信息，为 Agent 提供精确的代码上下文。

10. **Suggestion 评分**: `Suggestion` 包含 `score` 字段，说明补全建议有评分机制，可能用于多候选补全的排序。
