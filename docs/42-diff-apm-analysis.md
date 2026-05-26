# iFlyCode Diff 与 APM 系统分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-11

## 1. Diff 系统概述

iFlyCode 的 Diff 系统用于展示 AI 生成代码与原始代码的差异对比，基于 IntelliJ 内置的 Diff 框架实现。支持云端文件和本地文件的 Diff 对比。

## 2. Diff 核心类

### 2.1 CloudDiffUtil

**路径**: `com/aicode/diff/CloudDiffUtil`
**职责**: 云端 Diff 工具

**Key 常量**:
- `DIFF_FILE_UNIQUE_ID` — Diff 文件唯一标识 Key
- `DIFF_FILEPATH_LEFT` — 左侧文件路径 Key（原始文件）
- `DIFF_FILEPATH_RIGHT` — 右侧文件路径 Key（AI 建议文件）
- `DIFF_SUGGEST_CODE` — 建议代码 Key
- `DIFF_FILENAME` — 文件名 Key

**功能**: 使用 IntelliJ `Key<T>` 机制在 Diff 组件间传递数据。

### 2.2 DiffDialog

**路径**: `com/aicode/diff/DiffDialog`
**父类**: `DialogWrapper` — IntelliJ 对话框基类
**职责**: Diff 对比对话框

**关键方法**:
- `createActions()` — 创建对话框操作按钮
- `getOKAction()` — 获取确认操作（接受 AI 建议）
- `getCancelAction()` — 获取取消操作（拒绝 AI 建议）
- `doOKAction()` — 执行确认操作

**Diff 渲染流程**:
```
DiffManager.getInstance()
  → createRequestPanel(project, disposable, window)
  → DiffRequestPanel.setRequest(SimpleDiffRequest)
  → DiffUserDataKeysEx 设置额外数据
```

### 2.3 DiffService

**路径**: `com/aicode/diff/DiffService`
**职责**: Diff 服务 — 代码替换和视图管理

**关键方法**:
- `replaceTextInVirtualFile(Project, VirtualFile, int start, int end, String text)` — 替换文件中的文本
- `closeDiffViewIfAlreadyOpened()` — 关闭已打开的 Diff 视图

**文本替换流程**:
```
FileDocumentManager.getInstance().getDocument(virtualFile)
  → Document.replaceString(start, end, text)
  → WriteCommandAction.runWriteCommandAction(project, runnable)
  → FileDocumentManager.getInstance().saveDocument(document)
```

**关键发现**:
- 使用 `WriteCommandAction` 确保 IDE 写操作安全
- 替换后自动保存文档
- 支持关闭已打开的 Diff 视图避免重复

### 2.4 FileInfo

**路径**: `com/aicode/diff/FileInfo`
**职责**: Diff 文件信息

**字段**:
- `VirtualFile` — 虚拟文件引用
- `couldFile` (boolean) — 是否为云端文件

### 2.5 FileService

**路径**: `com/aicode/diff/FileService`
**职责**: 文件操作服务

**关键方法**:
- `replaceContentInFile()` — 替换文件内容
- `deleteFile(File)` — 删除文件
- `replaceAll()` — 全局替换

**实现细节**:
- 使用 `FileReader` / `FileWriter` 进行文件读写
- 使用 `Files.deleteIfExists()` 删除文件
- 支持 `lineSeparator` 行分隔符处理

### 2.6 GenericUtils

**路径**: `com/aicode/diff/GenericUtils`
**职责**: Diff 通用工具

**关键方法**:
- `isCloudFile(VirtualFile)` — 判断是否为云端文件
- `getClodFileMeta(VirtualFile)` — 获取云端文件元数据
- `getVersionedFileName(File)` — 获取版本化文件名

**关键发现**:
- 使用 `LinkageError.getStackTrace()` — 与 H() 混淆使用相同的栈追踪技术
- 云端文件有独立的元数据管理

## 3. Diff 交互流程

```
1. AI 生成代码建议
   └── ChatService / InlineChatCommandService
        └── 返回 AI 建议代码

2. 用户点击 "Diff" 操作
   └── ChatOperationEnum.ACTION_DIFF
        └── DiffDialog 创建
             ├── 左侧: 原始文件 (DIFF_FILEPATH_LEFT)
             └── 右侧: AI 建议 (DIFF_FILEPATH_RIGHT + DIFF_SUGGEST_CODE)

3. Diff 视图展示
   └── DiffManager → DiffRequestPanel → SimpleDiffRequest

4. 用户接受修改
   └── DiffDialog.doOKAction()
        └── DiffService.replaceTextInVirtualFile()
             → WriteCommandAction → Document.replaceString → saveDocument

5. 用户拒绝修改
   └── DiffDialog.getCancelAction()
        └── 关闭 Diff 视图，不应用修改
```

## 4. APM 系统概述

iFlyCode 集成了 OpenTelemetry SDK 进行应用性能监控 (APM)，追踪代码补全、Agent 生命周期等关键操作的性能指标。

## 5. APM 核心类

### 5.1 OpenTelemetryConfig

**路径**: `com/aicode/apm/OpenTelemetryConfig`
**职责**: OpenTelemetry SDK 配置

**配置流程**:
```
1. SdkTracerProvider.builder()
     .setSampler(Sampler.traceIdRatioBased(ratio))  — 采样率控制
     .addSpanProcessor(spanProcessor)                — 添加 Span 处理器
     .setResource(resource)                          — 设置资源标签
     .build()

2. TextMapPropagator 配置:
     W3CTraceContextPropagator  — W3C Trace Context 传播
     W3CBaggagePropagator       — W3C Baggage 传播

3. OtlpHttpSpanExporter — OTLP HTTP 协议导出
     RetryPolicy.builder()     — 重试策略
```

**关键组件**:
- `SdkTracerProvider` — Tracer 提供者
- `Sampler.traceIdRatioBased` — 基于比例的采样
- `W3CTraceContextPropagator` — W3C 标准追踪上下文传播
- `W3CBaggagePropagator` — W3C 标准行李传播
- `OtlpHttpSpanExporter` — OTLP HTTP Span 导出器

### 5.2 OpenTelemetryService

**路径**: `com/aicode/apm/OpenTelemetryService`
**职责**: APM 服务 — 配置管理和 Span 上报

**关键方法**:
- `handApmConfig()` — 处理 APM 配置

**关键字符串常量**:
- `是否开启APM===>` — APM 开关日志
- `agent push opentelemetry url is ` — APM URL 日志

**配置来源**:
- `AICodeSettingsState.apmUrl` — APM 上报 URL

**HTTP 上报**:
- 使用 `OkHttpClient` 发送 Span 数据到 APM 后端
- 读取 `parentSpan` 关联父 Span

### 5.3 OpenTelemetryUtil

**路径**: `com/aicode/apm/OpenTelemetryUtil`
**职责**: OpenTelemetry 工具类 — Span 构建辅助

**关键方法**:
- `buildWithParent(Span, TracerEnum, String)` — 构建带父 Span 的子 Span
- `buildWithTracer(TracerEnum, String)` — 构建 Tracer 级别的 Span
- `buildWithCommand(String, String)` — 构建命令级别的 Span

**Span 构建流程**:
```
GlobalOpenTelemetry.getTracer(tracerName)
  → Tracer.spanBuilder(spanName)
  → SpanBuilder.setSpanKind(spanKind)
  → SpanBuilder.setParent(Context.current())
  → SpanBuilder.startSpan()
```

**关键发现**:
- 使用 `LinkageError.getStackTrace()` 获取调用栈 — 与 H() 混淆相同的栈追踪技术

## 6. APM 追踪架构

```
┌───────────────────────────────────────────────────────────┐
│                    iFlyCode Plugin                          │
│                                                            │
│  OpenTelemetryUtil                                         │
│    ├── buildWithParent() — 子 Span（补全请求）              │
│    ├── buildWithTracer()  — Tracer Span                    │
│    └── buildWithCommand() — 命令 Span                     │
│                                                            │
│  TracerEnum                                                │
│    ├── CODE_COMPLETE           — 代码补全                  │
│    ├── CODE_COMPLETE_PARENT    — 补全父 Span               │
│    ├── CODE_COMPLETE_INLINE_CHAT_PARENT — 内联聊天补全      │
│    ├── AGENT_RUN              — Agent 运行                 │
│    ├── AGENT_RESTART          — Agent 重启                 │
│    ├── AGENT_ERROR            — Agent 错误                 │
│    └── AGENT_FAILURE          — Agent 故障                 │
│                                                            │
│  SpanAttrEnum                                              │
│    ├── COMPLETE_DURATION      — 补全总耗时                 │
│    ├── COMPLETE_FIRST_DURATION — 首字耗时 (TTFT)          │
│    ├── COMPLETE_ACCEPT/REJECT — 补全接受/拒绝              │
│    ├── COMPLETE_FILE_LINE/SIZE — 文件行数/大小             │
│    ├── AGENT_VERSION          — Agent 版本                 │
│    ├── AGENT_START_REASON/CODE — 启动原因/代码             │
│    └── EXCEPTION_*           — 异常属性                    │
└───────────────────────────────────────────────────────────┘
                           │
                    OTLP HTTP Export
                           │
┌───────────────────────────────────────────────────────────┐
│                    APM Backend                              │
│  (URL from AICodeSettingsState.apmUrl)                     │
└───────────────────────────────────────────────────────────┘
```

## 7. 关键发现

1. **IntelliJ Diff 框架集成**: Diff 系统完全基于 IntelliJ 内置的 Diff 框架（`DiffManager`, `SimpleDiffRequest`, `DiffRequestPanel`），而非自研。

2. **云端文件支持**: `CloudDiffUtil` 和 `GenericUtils.isCloudFile()` 表明 iFlyCode 支持云端文件的 Diff 对比，可能用于云端代码评审场景。

3. **W3C 标准追踪**: APM 使用 W3C Trace Context 和 W3C Baggage 标准传播协议，确保与标准 APM 后端兼容。

4. **OTLP HTTP 导出**: 使用 `OtlpHttpSpanExporter` 通过 HTTP 协议导出 Span 数据，而非 gRPC。

5. **采样率控制**: 使用 `traceIdRatioBased` 采样器，可配置采样比例，避免全量上报影响性能。

6. **首字耗时 (TTFT)**: `COMPLETE_FIRST_DURATION` 追踪代码补全的首字耗时，这是 AI 代码补全的关键性能指标。

7. **栈追踪复用**: `OpenTelemetryUtil` 和 `GenericUtils` 都使用 `LinkageError.getStackTrace()` 获取调用栈信息，与 H() 混淆使用相同的技术。

8. **APM URL 动态配置**: APM 上报 URL 来自 `AICodeSettingsState.apmUrl`，支持运行时动态切换 APM 后端。
