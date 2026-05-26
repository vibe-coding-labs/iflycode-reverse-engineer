# iFlyCode Q 包与 Util 工具体系分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-11

## 1. 概述

`com/aicode/Q/` 包是 iFlyCode 的混淆包装层，使用单字母类名（Q、q 等）隐藏核心类型系统。`com/aicode/util/` 包提供字符串混淆（H() 方法）、通用工具和 PSI 工具。

## 2. Q 包 — 混淆类型系统

### 2.1 Q (CodeTipType 枚举包装)

**路径**: `com/aicode/Q/Q`
**职责**: 补全类型枚举 — 包装 `CodeTipType`

**枚举值**:
- `Inline` — 内联补全（单行灰色文字）
- `AfterLineEnd` — 行末补全（多行代码块）

**关键方法**:
- `values()` — 获取所有枚举值
- `valueOf(String)` — 按名称获取枚举值

**引用关系**: `CodeTipUtil` 直接引用 `Q/q` 类创建补全类型对象。

### 2.2 q (CodeTipType 实现)

**路径**: `com/aicode/Q/q`
**职责**: 补全类型实现 — `CodeTipType` 的具体实现

**字段**:
- `type` — Q — 补全类型枚举值

**关键方法**:
- `getType()` — 获取补全类型
- `isInline()` — 是否内联
- `isAfterLineEnd()` — 是否行末

### 2.3 Q 包其他类

| 类 | 推测职责 |
|----|---------|
| Q$1 | 匿名内部类 — 可能是 switch 映射 |
| Q$2 | 匿名内部类 — 可能是工厂方法 |

## 3. Util 包 — 工具体系

### 3.1 AICodeStringUtil (526 strings) — 最大工具类

**路径**: `com/aicode/util/AICodeStringUtil`
**职责**: AI 代码字符串工具 — 字符串混淆（H()）和代码文本处理

**H() 混淆方法**:
- `H(String)` — 核心混淆解码方法
  - 使用 `LinkageError.getStackTrace()` 获取调用者类名和方法名
  - 使用调用者信息生成 XOR 密钥
  - 对输入字符串进行 XOR 解码

**代码文本处理方法**:
- `getNextLines(String, int, int)` — 获取指定行之后的 N 行
- `findOverlappingLines(String, String)` — 检测重叠行
- `trailingWhitespace(String)` — 获取尾部空白
- `leadingWhitespace(String)` — 获取前导空白
- `stripLeading(String)` — 去除前导空白
- `getLinePrefix(String, int)` — 获取行前缀
- `getLineSuffix(String, int)` — 获取行后缀
- `isBlankLine(String)` — 是否空行
- `countLines(String)` — 计算行数
- `getLineContent(String, int)` — 获取指定行内容
- `getLineStartOffset(String, int)` — 获取行起始偏移
- `getLineEndOffset(String, int)` — 获取行结束偏移
- `whitespacePrefixLength(String)` — 前导空白长度

**关键依赖**:
- `LinkageError` — 用于获取调用栈（H() 混淆核心）
- `GenericUtils` — 通用工具（也使用栈追踪）

### 3.2 GenericUtils (312 strings)

**路径**: `com/aicode/util/GenericUtils`
**职责**: 通用工具 — H() 解码和通用操作

**关键方法**:
- `H(String)` — H() 混淆解码（与 AICodeStringUtil 相同机制）
- `getStackTrace()` — 获取调用栈

**关键依赖**:
- `LinkageError` — 栈追踪获取
- `OpenTelemetryUtil` — APM 工具

### 3.3 NewFileUtils (108 strings)

**路径**: `com/aicode/util/NewFileUtils`
**职责**: 新文件工具 — 文件创建和管理

**关键方法**:
- `H(String)` — H() 混淆解码
- `createFile()` — 创建文件
- `writeFile()` — 写入文件

### 3.4 PropertyUtils (64 strings)

**路径**: `com/aicode/util/PropertyUtils`
**职责**: 属性工具 — 读取配置属性

**关键方法**:
- `H(String)` — H() 混淆解码
- `getProperty()` — 获取属性

### 3.5 PsiUtils (57 strings)

**路径**: `com/aicode/util/PsiUtils`
**职责**: PSI 工具 — IntelliJ PSI 元素操作

**关键方法**:
- `findMethodAtOffset(PsiFile, int)` — 在偏移处查找方法
- `findClassAtOffset(PsiFile, int)` — 在偏移处查找类
- `getContainingMethod(PsiElement)` — 获取包含方法
- `getMethodSignature(PsiMethod)` — 获取方法签名

### 3.6 JavaPsiUtils (92 strings)

**路径**: `com/aicode/util/JavaPsiUtils`
**职责**: Java PSI 工具 — Java 特定的 PSI 操作

**关键方法**:
- `findJavaMethod(PsiFile, int)` — 查找 Java 方法
- `getMethodParameters(PsiMethod)` — 获取方法参数
- `getReturnType(PsiMethod)` — 获取返回类型
- `isTestMethod(PsiMethod)` — 是否测试方法
- `isPrivateMethod(PsiMethod)` — 是否私有方法
- `getSuperMethods(PsiMethod)` — 获取父类方法

### 3.7 HandleCacheUtil (38 strings)

**路径**: `com/aicode/util/HandleCacheUtil`
**职责**: 缓存处理工具 — 管理补全缓存

### 3.8 EditorCacheUtil (48 strings)

**路径**: `com/aicode/util/EditorCacheUtil`
**职责**: 编辑器缓存工具 — 缓存编辑器状态

### 3.9 IndentLineUtil (34 strings)

**路径**: `com/aicode/util/IndentLineUtil`
**职责**: 缩进行工具 — 处理代码缩进

### 3.10 CodeCheckUtil (52 strings)

**路径**: `com/aicode/util/CodeCheckUtil`
**职责**: 代码检查工具 — 模式匹配

**关键方法**:
- `matcher(String)` — 模式匹配

### 3.11 PluginInfoUtils (42 strings)

**路径**: `com/aicode/util/PluginInfoUtils`
**职责**: 插件信息工具 — 获取插件版本和 ID

**关键方法**:
- `getPluginId()` — 获取插件 ID
- `getPluginVersion()` — 获取插件版本

## 4. H() 混淆体系总结

### 4.1 H() 定义点 (27 个)

| 类 | 字符串数 | 职责 |
|----|---------|------|
| AICodeStringUtil | 526 | 核心混淆 + 代码文本处理 |
| GenericUtils | 312 | 通用混淆 |
| NewFileUtils | 108 | 文件操作混淆 |
| PropertyUtils | 64 | 属性读取混淆 |
| 其他 23 个类 | ~200 | 各自领域的混淆 |

### 4.2 H() 调用链

```
调用者类.方法()
    │
    ▼
H(混淆字符串)
    │
    ├── LinkageError.getStackTrace()
    │   └── 获取调用者类名 + 方法名
    │
    ├── 生成 XOR 密钥
    │   └── callerClassName + methodName → 密钥
    │
    └── XOR 解码
        └── 混淆字符串 ⊕ 密钥 → 明文字符串
```

### 4.3 H() 安全性分析

| 维度 | 评估 |
|------|------|
| **混淆强度** | 中等 — XOR 解码可逆 |
| **密钥来源** | 调用者类名+方法名 — 可从 .class 文件推导 |
| **破解难度** | 低 — 静态分析可完全还原 |
| **运行时 Hook** | 可在 H() 方法入口 Hook 获取明文 |
| **静态解码** | 可编写 Python 脚本批量解码 |

## 5. 关键发现

1. **Q 包极简**: 只有 Q（枚举）和 q（实现）两个类，是 `CodeTipType` 的混淆包装，将 Inline/AfterLineEnd 两种补全类型隐藏在单字母类名中。

2. **AICodeStringUtil 双重职责**: 既是 H() 混淆的核心实现，也是代码文本处理（行分割、空白检测、重叠检测）的工具类。526 个字符串使其成为最大的工具类。

3. **4 个 H() 工具类**: AICodeStringUtil、GenericUtils、NewFileUtils、PropertyUtils 都定义了 H() 方法，说明混淆字符串分布在多个领域。

4. **PSI 双工具**: PsiUtils（通用 PSI）和 JavaPsiUtils（Java 特定 PSI）提供两层 PSI 操作，支持 Java 和其他语言的代码结构分析。

5. **LinkageError 技巧**: 所有 H() 方法都使用 `LinkageError.getStackTrace()` 而非 `Thread.currentThread().getStackTrace()`，可能是因为 LinkageError 是 Error 而非 Exception，在某些安全检查中不会被拦截。

6. **缓存三层**: HandleCacheUtil（处理缓存）+ EditorCacheUtil（编辑器缓存）+ SimpleCodeTipCache（补全缓存），构成三层缓存体系。

7. **CodeCheckUtil.matcher()**: 使用模式匹配检测代码模式，可能用于判断补全建议是否应该被过滤或修改。
