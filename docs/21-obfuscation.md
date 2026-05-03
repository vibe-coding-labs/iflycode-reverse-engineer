# 21 混淆技术分析

## 混淆方法概览

iFlyCode 使用了多种混淆技术保护代码：

### 1. 字符串 XOR 编码

所有字符串常量经过 XOR 编码，运行时解码。存在多个不同的编码器：

| 编码器方法 | 所在类 | 用途 |
|-----------|--------|------|
| `H(String)` | `NewFileUtils` | 通用字符串 |
| `H(String)` | `RequestResultList` | WebSocket 相关 |
| `H(String)` | `AICodeStringUtil` | 命令类型 |
| `H(String)` | `IdeAction` | IDE Action |
| `H(String)` | `FileService` | 文件操作 |
| `H(String)` | `GitReviewService` | Git 操作 |
| `H(String)` | `EditorUtils` | 编辑器操作 |
| `H(String)` | `Maps` | 配置键 |
| `H(String)` | `GeneratorConfig` | 生成器配置 |
| `H(String)` | `PositionUtil` | 位置相关 |
| `H(String)` | `ChatInputController` | 聊天输入 |
| `H(String)` | `JComponentKt` | UI 组件 |
| `H(String)` | `CodeCompleteService` | 补全相关 |
| `H(String)` | `FileExtensionLanguageDetails` | 文件扩展名 |
| `H(String)` | `RequestCancelException` | 异常 |
| `H(String)` | `ActionButton` | UI 操作 |
| `H(String)` | `OverlayUtils` | 覆盖层 |
| `H(String)` | `Maps` | 映射 |

编码原理: 使用调用者的类名/方法名作为 XOR 密钥。

### 2. 字段名混淆

使用 Java 关键字和保留字作为字段名：

| 字段 | 实际类型 | 实际用途 |
|------|---------|---------|
| `byte` | Logger | 日志记录器 |
| `enum` | Logger | 日志记录器 |
| `final` | String | 端口号 |
| `float` | Object | 同步锁 |
| `try` | PluginAgentProcessHandler | 进程处理器 |
| `case` | AgentModuleEnum | 模块枚举 |

### 3. 控制流混淆

- 大量不可读的变量赋值
- 交织的 try-catch 块
- 反编译后出现 `void` 声明和 `WARNING` 注释
- CFR 反编译器部分方法反编译失败（如 `RestartableAgentProcessService.onReconnectException`）

### 4. 内部类命名

非标准的内部类命名模式：

```
PluginStartupActivity$01
AcceptInlaysAction$pa
AcceptLineCodeInlaysAction$va
AcceptWordInlaysAction$wa
SocketMessageHandleListener$Ka
HeartBeatCheckRunner$Ga, $ma
AutoCodeGenerateListener$Q, $T
GitBranchChangeListener$H, $R, $b
PluginAgentProcessHandler$01
```

### 5. 顶层包混淆

`Q/` 包下的类使用极短名称：

```
Q.q    — 用途未知
Q.sa   — 用途未知
Q.ua   — 用途未知
```

### 6. 反编译对抗

- 部分方法包含反编译陷阱（不可达代码、循环引用）
- CFR 报告 `ConfusedCFRException` 和 `Started 2 blocks at once` 错误
- 字符串编码使得静态分析无法直接搜索关键字

## 对逆向分析的影响

| 混淆技术 | 影响 | 应对策略 |
|---------|------|---------|
| 字符串 XOR | 无法直接搜索关键字 | 运行时 hook 或逐个 H() 方法分析 |
| 字段名混淆 | 降低可读性 | 通过类型推断和上下文分析 |
| 控制流混淆 | 反编译结果不完整 | 手动分析字节码或使用多种反编译器 |
| 内部类命名 | 增加理解难度 | 通过外部类引用推断用途 |

## 编码器示例

```java
// 典型的 XOR 编码器 (推测原理)
public static String H(String encoded) {
    String callerClassName = getCallerClassName(); // 通过堆栈获取
    char[] key = callerClassName.toCharArray();
    char[] chars = encoded.toCharArray();
    for (int i = 0; i < chars.length; i++) {
        chars[i] ^= key[i % key.length];
    }
    return new String(chars);
}
```

这种混淆不算特别强（可以通过运行时 Hook 完全绕过），但足以阻止简单的静态分析。
