## 3. Domain 包 — 领域模型层

### 3.1 包总览

| 类名 | 源文件名 | 大小 | 职责 |
|------|----------|------|------|
| `CommandCache` | CommandCache.java | 2.5K | 命令缓存 — 记录编辑器选区状态 |
| `GetTipsResult` | GetTipsResult.java | 3.0K | 代码提示结果容器 |
| `GetTipsResult$Tip` | GetTipsResult.java | 4.2K | 单条代码提示 |
| `LineInfo` | LineInfo.java | 5.5K | 行信息 — 光标所在行的完整信息 |
| `Position` | Position.java | 4.6K | 位置 — 行号+列号 |
| `Range` | Range.java | 3.6K | 范围 — 起始+结束位置 |
| `Suggestion` | Suggestion.java | 3.0K | 建议项 — 代码补全建议 |
| `VirtualFileUri` | VirtualFileUri.java | 5.8K | 虚拟文件 URI — 跨系统文件标识 |
| `VirtualFileUri$TypeAdapter` | VirtualFileUri.java | 1.3K | Gson 序列化适配器 |

### 3.2 CommandCache — 命令缓存

**路径**: `com/aicode/domain/CommandCache`

**字段**:
```
private boolean startSelected          // 起始位置是否有选区
private int startSelectedStartOffset  // 起始选区的起始偏移
private boolean endSelected           // 结束位置是否有选区
private int endSelectedStartOffset    // 结束选区的起始偏移
```

**用途**: 记录用户在编辑器中执行命令（如行内聊天）时的选区状态，用于在命令执行后恢复选区。

### 3.3 Position — 位置

**路径**: `com/aicode/domain/Position`

**字段**:
```
int line       // 行号（0-based）
int character   // 列号（0-based）
```

**方法**:
| 方法 | 用途 |
|------|------|
| `of(int, int)` | 静态工厂方法 |
| `Position(LineInfo)` | 从 LineInfo 构造（提取行号和列号） |
| `toOffset(String)` | 将 Position 转换为文本中的字符偏移量 |
| `getCursorPosition(Editor)` | 获取编辑器中光标的 Position |
| `lambda$getCursorPosition$0(Editor)` | lambda: 从 CaretModel 获取位置 |

**用途**: 表示文本中的位置，兼容 LSP 风格的 line/character 坐标。`toOffset()` 方法将行列转换为字符偏移，用于 IntelliJ Document API 交互。

### 3.4 Range — 范围

**路径**: `com/aicode/domain/Range`

**字段**:
```
Position start  // 起始位置
Position end    // 结束位置
```

**方法**:
| 方法 | 用途 |
|------|------|
| `of(Position, Position)` | 静态工厂方法 |

**用途**: 表示文本中的一个范围，用于代码补全的替换区域、行内聊天的编辑范围等。

### 3.5 LineInfo — 行信息

**路径**: `com/aicode/domain/LineInfo`

**字段**:
```
final int lineCount         // 文档总行数
final int lineNumber       // 当前行号（0-based）
final int lineStartOffset  // 当前行起始偏移
final int columnOffset     // 光标在行内的列偏移
final String line          // 当前行文本内容
final int nextLineIndent   // 下一行的缩进量
```

**方法**:
| 方法 | 用途 |
|------|------|
| `create(Document, int)` | 静态工厂：从 Document 和偏移量创建 LineInfo |
| `getLinePrefix()` | 获取光标前的行内容 |
| `getLineSuffix()` | 获取光标后的行内容 |
| `isBlankLine()` | 当前行是否为空行 |
| `getWhitespaceBeforeCursor()` | 获取光标前的空白字符 |
| `getLineEndOffset()` | 获取行尾偏移量 |
| `calculateNextLineIndent(Document, int)` | 计算下一行的缩进 |

**用途**: 封装光标所在行的完整信息，是代码补全请求的核心数据。服务端根据行内容、前缀、后缀、缩进等信息生成补全建议。

### 3.6 GetTipsResult — 代码提示结果

**路径**: `com/aicode/domain/GetTipsResult`

**字段**:
```
List<GetTipsResult$Tip> tips  // 提示列表
```

### 3.7 GetTipsResult$Tip — 单条提示

**路径**: `com/aicode/domain/GetTipsResult$Tip`

**字段**:
```
final String uuid          // 提示唯一标识
final String text          // 补全文本
final Range range          // 替换范围
final String displayText   // 显示文本（灰色预览）
final Position position    // 插入位置
```

**用途**: 表示一条代码补全建议。`uuid` 用于追踪补全的接受/拒绝事件；`text` 是实际插入的代码；`displayText` 是编辑器中灰色预览文本；`range` 指定需要替换的文本范围。

### 3.8 Suggestion — 建议项

**路径**: `com/aicode/domain/Suggestion`

**字段**:
```
final int score             // 匹配分数
final String type            // 建议类型
final String hash            // 内容哈希
final CodeInlayList inlays   // 内嵌提示列表
```

**用途**: 代码补全的评分建议项，`score` 用于排序，`hash` 用于去重，`inlays` 关联 IntelliJ 的 InlayHint 显示。

### 3.9 VirtualFileUri — 虚拟文件 URI

**路径**: `com/aicode/domain/VirtualFileUri`

**字段**:
```
static Logger LOG
final String uri            // 文件 URI 字符串
```

**方法**:
| 方法 | 用途 |
|------|------|
| `from(VirtualFile)` | 从 VirtualFile 创建（处理路径前缀） |
| `from(VirtualFileSystem, String)` | 从文件系统和路径创建 |
| `processPath(String)` | 处理路径（标准化分隔符） |
| `isNeedsPathPrefix(VirtualFileSystem)` | 判断是否需要路径前缀 |
| `asPrefixedUri(String)` | 添加路径前缀 |
| `getUri()` | 获取 URI 字符串 |

**用途**: 将 IntelliJ 的 VirtualFile 转换为可序列化的 URI 标识，用于与服务端通信。处理了不同文件系统（如 jar://、temp://）的路径前缀需求。

### 3.10 VirtualFileUri$TypeAdapter — Gson 序列化适配器

**路径**: `com/aicode/domain/VirtualFileUri$TypeAdapter`
**实现**: `JsonSerializer&lt;VirtualFileUri&gt;`

**方法**:
| 方法 | 用途 |
|------|------|
| `serialize(VirtualFileUri, Type, JsonSerializationContext)` | 将 VirtualFileUri 序列化为 JSON 字符串 |

### 3.11 领域模型关系图

```
                    ┌──────────────────────────────────────────────────┐
                    │              代码补全数据流                       │
                    └──────────────────────────────────────────────────┘

    Editor (IntelliJ)                    Service (服务端)
         │                                   │
         v                                   │
    ┌──────────┐                              │
    │ LineInfo │ ←── Document + offset        │
    └────┬─────┘                              │
         │                                    │
         v                                    │
    ┌──────────┐                              │
    │ Position │ ←── LineInfo 构造            │
    └────┬─────┘                              │
         │                                    │
         v                                    v
    ┌──────────┐     HTTP/WS Request     ┌──────────────┐
    │  Range   │ ──────────────────────→ │  AI Backend   │
    └──────────┘                          └──────┬───────┘
         │                                       │
         │                              HTTP/WS Response
         │                                       │
         v                                       v
    ┌───────────────────┐                  ┌───────────────────┐
    │ VirtualFileUri    │                  │ GetTipsResult      │
    │ (文件标识)        │                  │ └── Tip            │
    └───────────────────┘                  │     ├── uuid       │
                                           │     ├── text       │
    ┌───────────────────┐                  │     ├── range ──────┼───→ Range
    │ CommandCache      │                  │     ├── displayText │
    │ (选区缓存)       │                  │     └── position ──┼───→ Position
    └───────────────────┘                  └───────────────────┘
                                                    │
                                                    v
                                           ┌───────────────────┐
                                           │ Suggestion         │
                                           │ ├── score          │
                                           │ ├── type           │
                                           │ ├── hash           │
                                           │ └── inlays         │
                                           └───────────────────┘

    ═══════════════════════════════════════════════════════════════

    Domain → Service 使用关系:
    ────────────────────────

    LineInfo ──→ RequestTipService (代码补全请求)
    Position ──→ InlineChatService (行内聊天定位)
    Range    ──→ InlineChatService (编辑范围)
    GetTipsResult$Tip ──→ PluginEditorInlayHintsProvider (渲染补全)
    Suggestion ──→ CodeInlayList (InlayHint 显示)
    VirtualFileUri ──→ 所有需要文件标识的请求
    CommandCache ──→ InlineChatHandleService (选区恢复)
```

### 3.12 扩展领域模型（跨包 DTO）

以下 DTO 类虽不在 `domain` 包中，但属于领域模型的一部分：

#### 3.12.1 agent/dto/chat 包 — 聊天领域 DTO

**CodeInfoDto** (`com/aicode/agent/dto/chat/CodeInfoDto`):
```
String content              // 代码片段内容
List&lt;RangeDTO&gt; range        // 代码范围
List&lt;RangeDTO&gt; bodyRange     // 方法体范围 (transient)
String fileName             // 文件名
String path                 // 文件路径
String language             // 编程语言
String allContent           // 文件完整内容
```

**CodeInfoDto$RangeDTO**:
```
Integer line                // 行号
Integer character           // 列号
```

**CommentContext** (`com/aicode/agent/dto/chat/CommentContext`):
```
String md5                              // 文件 MD5
List&lt;CommentInfo&gt; methods               // 方法注释列表
```

**CommentInfo** (`com/aicode/agent/dto/chat/CommentInfo`):
```
String name                  // 方法名
String textContext           // 注释文本
int index                    // 方法索引
JsonArray range              // 方法范围
JsonArray bodyRange          // 方法体范围
```

**PresentationDataDto** (`com/aicode/agent/dto/chat/PresentationDataDto`):
```
int line                     // 行号
int character                // 列号
String type                  // 展示类型
CodeInfoDto codeInfoDto      // 关联代码信息
```

**SqlInfoDto** (`com/aicode/agent/dto/chat/SqlInfoDto`):
```
String database              // 数据库名
String inputText             // 输入 SQL
String sourceId               // 数据源 ID
List&lt;String&gt; tables          // 涉及的表
```

#### 3.12.2 agent/dto 包 — 功能权限 DTO

**FunctionModelInfo** (`com/aicode/agent/dto/FunctionModelInfo`):
```
String permissionCode        // 权限代码
String permissionName        // 权限名称
String language              // 编程语言
List&lt;CodeModel&gt; codeModelList  // 代码模型列表
```

**CodeModel** (`com/aicode/agent/dto/CodeModel`):
```
String modelId               // 模型 ID
String modelCode             // 模型代码
String modelName             // 模型名称
boolean checked              // 是否选中
String originalModelName     // 原始模型名
boolean tokenExhausted       // Token 是否耗尽
```

#### 3.12.3 test/dto 包 — 单测领域 DTO

**FunctionDataDto** (`com/aicode/test/dto/FunctionDataDto`):
```
String functionName         // 函数名
String id                    // 标识
String xmlCase               // XML 用例
String methodContent         // 方法内容
String testContent           // 测试内容
String caseContent           // 用例内容
String caseInput             // 用例输入
String caseResult            // 用例结果
String unitTest              // 单元测试代码
String unitMock              // Mock 代码
```

**ChangeInfoDto** (`com/aicode/test/dto/ChangeInfoDto`):
```
Integer changeLine           // 变更行号
String content               // 变更内容
```

**UnitTestDto** (`com/aicode/test/dto/UnitTestDto`):
```
String tabName               // 标签名
String type                   // 类型
String language               // 语言
String level                  // 级别
String id                     // 标识
String packagePath            // 包路径
String absolutePath           // 绝对路径
String errMessage             // 错误信息
List&lt;DataDTO&gt; data            // 数据列表
```

**UnitTestDto$DataDTO**:
```
String className              // 类名
String operationTime          // 操作时间
String id                     // 标识
String language               // 语言
String path                   // 路径
String testClassAbsolutePath  // 测试类绝对路径
String testClasPath           // 测试类路径
String testClassName          // 测试类名
String structure              // 结构
String testFrame              // 测试框架
String mockFrame              // Mock 框架
boolean modifyTestFrame       // 是否修改测试框架
boolean testFrameAlert        // 测试框架告警
List&lt;FunctionDataDTO&gt; functionData  // 函数数据列表
String testTemplate           // 测试模板
String reason                 // 原因
String message                // 消息
```

**UnitTestDto$DataDTO$FunctionDataDTO$TemplateAttr**:
```
boolean staticMethod          // 是否静态方法
String className              // 类名
String methodName             // 方法名
String classPackage           // 类包名
TreeMap<String,String> prepareForTestImport  // 测试准备导入
Map<String,String> fieldClass  // 字段类映射
Set&lt;String&gt; methodImportClass  // 方法导入类集合
String template               // 模板名
```

### 3.13 跨包 DTO 关系图

```
    ┌──────────────────────────────────────────────────────────────────┐
    │                    单测生成数据流                                  │
    └──────────────────────────────────────────────────────────────────┘

    ┌──────────────┐        ┌──────────────────────┐
    │ FunctionModel│        │  UnitTestDto          │
    │ Info         │        │  ├── tabName          │
    │ ├──permission│        │  ├── type             │
    │ │  Code      │        │  └── data[]           │
    │ ├──permission│        │      └── DataDTO      │
    │ │  Name      │        │          ├──className │
    │ ├──language  │        │          ├──testFrame │
    │ └──codeModel│        │          ├──mockFrame │
    │    List[]    │        │          └──function  │
    │    └──Code  │        │             Data[]     │
    │      Model   │        │             └──Func   │
    │      ├──model│        │               DataDTO  │
    │      │  Id   │        │               ├──unit  │
    │      ├──model│        │               │  Test  │
    │      │  Code │        │               ├──unit  │
    │      └──model│        │               │  Mock  │
    │         Name │        │               ├──range │──→ CodeInfoDto
    └──────────────┘        │               │        │    $RangeDTO
                            │               └──templ │
    ┌──────────────┐        │                  ateAttr│
    │ CodeInfoDto  │        │                  ├──static│
    │ ├──content   │        │                  │Method │
    │ ├──range[]   │        │                  ├──class│
    │ │  └──Range  │        │                  │Name   │
    │ │    DTO     │        │                  ├──field│
    │ ├──fileName  │        │                  │Class  │
    │ ├──path      │        │                  └──templ│
    │ ├──language  │        │                     ate  │
    │ └──allContent│        └──────────────────────┘
    └──────────────┘
         │
         │ 关联
         v
    ┌──────────────┐        ┌──────────────────────┐
    │ Presentation │        │  CommentContext       │
    │ DataDto      │        │  ├── md5              │
    │ ├──line      │        │  └── methods[]        │
    │ ├──character │        │      └──CommentInfo   │
    │ ├──type      │        │         ├──name       │
    │ └──codeInfo  │        │         ├──textContext│
    │    Dto       │        │         ├──index      │
    └──────────────┘        │         ├──range      │
                            │         └──bodyRange  │
    ┌──────────────┐        └──────────────────────┘
    │ SqlInfoDto   │
    │ ├──database  │        ┌──────────────────────┐
    │ ├──inputText │        │  ChangeInfoDto        │
    │ ├──sourceId  │        │  ├── changeLine       │
    │ └──tables[]  │        │  └── content          │
    └──────────────┘        └──────────────────────┘
```

---
