# iFlyCode 字符串混淆机制深度分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-11

## 1. 概述

iFlyCode 插件使用了一种**基于调用栈的 XOR 字符串混淆系统**，覆盖了整个代码库。该系统的核心是一个名为 `H()` 的静态方法，分布在 **27 个不同的类**中，被 **312 个类**调用。每个类中的 `H()` 方法使用不同的 XOR 密钥（基于调用者的类名+方法名），使得混淆字符串无法通过简单的全局密钥一次性解码。

## 2. H() 方法机制详解

### 2.1 核心算法

`H()` 方法的签名统一为：
```java
public static String H(Object obj) → String
```

从 `AICodeStringUtil.H()` 的字节码反汇编，算法流程如下：

```
1. 获取调用栈信息
   new LinkageError().getStackTrace()  → StackTraceElement[]
   取 stackTrace[1]（调用者）

2. 构造 XOR 密钥
   key = stackTrace[1].getMethodName() + stackTrace[1].getClassName()
   → 密钥 = "方法名类名" 的拼接字符串

3. 计算密钥长度调整值
   key_len = key.length() - 1
   计算一组基于位运算的偏移值：
   offset1 = (1 << 3) ^ 3 ^ (4 << 3) ^ 3 ^ (4 << 4) ^ (1 << 4)
   → offset1 = 8 ^ 3 ^ 32 ^ 3 ^ 64 ^ 16 = 82

4. 将输入转为 char 数组
   char[] input = ((String) obj).toCharArray()
   创建结果数组 char[] result = new char[input.length]

5. XOR 解码循环
   对每个字符：
   result[i] = (char)(input[i] ^ key[(i + offset) % key.length] ^ offset2)
   其中 offset 和 offset2 由密钥长度和位运算值决定

6. 返回解码后的字符串
   return new String(result)
```

### 2.2 关键发现

**密钥来源是调用栈**：每个 `H()` 方法从 `LinkageError.getStackTrace()` 获取调用者的类名和方法名，拼接后作为 XOR 密钥。这意味着：

- 同一个混淆字符串在不同调用位置会产生不同的密钥
- 解码需要知道**确切的调用位置**（类名+方法名）
- 无法用单一密钥解码所有字符串

**27 个 H() 定义点**：每个定义点的 `H()` 方法内部逻辑相同，但它们各自包含一组**预编码的混淆字符串**作为常量池中的 String 常量。这些字符串在编译时已经用对应的密钥进行了 XOR 加密。

### 2.3 H() 方法分布

| 定义类 | 被调用次数 | 所属包 |
|--------|-----------|--------|
| com/aicode/util/Maps | 27 | util |
| com/aicode/util/JComponentKt | 24 | util |
| com/aicode/agent/service/CodeCompleteService | 23 | agent/service |
| com/aicode/diff/GenericUtils | 23 | diff |
| com/aicode/content/util/EditorUtils | 20 | content/util |
| com/aicode/content/util/file/FileExtensionLanguageDetails | 20 | content/util/file |
| com/aicode/service/editor/CancelRequestTip | 19 | service/editor |
| com/aicode/util/NewFileUtils | 21 | util |
| com/aicode/apm/OpenTelemetryUtil | 21 | apm |
| com/aicode/inline/ide/ConditionalActionConfiguration | 16 | inline/ide |
| com/aicode/exception/RequestCancelException | 16 | exception |
| com/aicode/inline/controller/ChatInputController | 14 | inline/controller |
| com/aicode/util/HandleCacheUtil | 14 | util |
| com/aicode/util/IndentLineUtil | 17 | util |
| com/aicode/inline/status/InlineChatStatusServiceKt | 17 | inline/status |
| com/aicode/action/batch/MethodGeneratorConfig | 16 | action/batch |
| com/aicode/agent/service/GitReviewService | 15 | agent/service |
| com/aicode/util/Application | 15 | util |
| com/aicode/language/AICodeLanguageInfo | 13 | language |
| com/aicode/util/AICodeStringUtil | 12 | util |
| com/aicode/action/batch/GeneratorConfig | 12 | action/batch |
| com/aicode/ui/ActionButton | 11 | ui |
| com/aicode/util/AICodeUtils | 11 | util |
| com/aicode/ui/FontKt | 13 | ui |
| com/aicode/content/util/OverlayUtils | 9 | content/util |
| com/aicode/diff/FileInfo | 9 | diff |
| com/aicode/service/editor/RequestResultList | 15 | service/editor |
| com/aicode/exception/RequestTimeoutException | 13 | exception |
| com/aicode/content/util/file/LanguageFileExtensionDetails | 13 | content/util/file |
| com/aicode/inline/ide/IdeAction | 15 | inline/ide |

## 3. 混淆字符串示例

### 3.1 AICodeStringUtil 中的混淆字符串

常量池中发现的 30+ 个混淆字符串（String 常量）：

| 常量池索引 | 混淆值 | 用途推测 |
|-----------|--------|---------|
| #307 | `%2(44<.s=wbc%+/>t0 -4 zt~Jb%"lz$vu"y1>95&s9=p->7?` | 可能是 API 端点或错误消息 |
| #313 | `2Lr[s.]<Q&k_+...&#123;Fe_7H?)G,7U4L;\bG,]-` | 可能是配置键名 |
| #318 | `7.#'` | 短字符串，可能是简单标识符 |
| #320 | `['TfnK7J;0F+WdH0k7Q c4K \%|-X-` | 可能是日志消息 |
| #322 | `(>.:)99/"54` | 可能是数字或ID |
| #324 | `2Y4QW#M0_&` | 可能是枚举值 |
| #326 | `=4*?4!` | 可能是短标识符 |
| #328 | `S/T9^']0^/` | 可能是路径或命令 |
| #330 | `'*/2` | 可能是操作符 |
| #332 | `2\/W0L7R$` | 可能是格式字符串 |
| #334 | `2` | 单字符，可能是数字 |
| #336 | `#` | 单字符 |
| #338 | `+>0%;=>%%-2+` | 可能是条件表达式 |
| #340 | `%+.#10::/=#3*86` | 可能是 URL 或路径 |
| #342 | `'lL1["g(P=W1Y8R$` | 可能是方法名 |
| #344 | `,#(;#=1'"54` | 可能是配置值 |
| #346 | `C0U F@7T2` | 可能是类名片段 |
| #348 | `Z,T,^+U,^A,P6@!Z,~'G>E)` | 可能是长标识符 |
| #350 | `5&#10::/=#3*86` | 可能是 URL |
| #352 | `2P$V+U,^A,P6@!Z,~'G>E)` | 可能是标识符 |
| #354 | `?>+$#7>,5")':9=&;1$+:1` | 可能是错误消息 |
| #356 | `F1B)IW#M0_&` | 可能是枚举值 |
| #358 | `>9?=,'836*":5?*%> ` | 可能是状态码 |
| #360 | `jM9A t)_/&#123;,E8H2` | 可能是方法名 |
| #362 | `,.87?<5"&-2+` | 可能是配置键 |
| #364 | `R D\1F@7T2` | 可能是类名 |
| #366 | `6;=>+"?8;` | 可能是条件 |
| #368 | `V*&#125;@@#V)AW!]0^/` | 可能是路径 |
| #370 | `>>-.\n6)7/452 -/>+` | 可能是消息模板 |

### 3.2 NewFileUtils 中的混淆字符串

| 常量池索引 | 混淆值 | 上下文线索 |
|-----------|--------|-----------|
| #45 | `I@PlDkx9ZQsA TmZ:\kH~M,[aJiNI FlEi` | 在 creatFile 方法附近 → 可能是文件创建错误消息 |
| #66 | `R!y?m8l`i1vmWisz7v:TOa=eso?z,` | 在 PsiManager.findFile 附近 → 可能是文件查找错误 |
| #109 | `dVyP~A/@t^|HfDdE+MgFk` | 在 showDialog 附近 → 可能是对话框标题 |
| #205 | `b1n9"s>-p:o;'#%&#125;_Zt:a9m%lxp>D?-c=&#125;3x=` | 在 UnitTestSettingsState 附近 → 可能是设置键名 |
| #229 | `yKoKaE)]cIqxjYJ5J~HiLxoefJtIqEi` | 在 JBCheckBox 附近 → 可能是复选框标签 |
| #231 | `(&#123;j=e4(;k!b6.*%&#125;dae+''".g_Zm0v8O"7`$e<D...c7"'`"z,` | 在 showDialog 参数附近 → 可能是对话框标签 |
| #343 | `cjMi` | 短字符串 → 可能是文件扩展名 |
| #354 | `Jc0v(o'`9nqSt/x6O?z,` | 在 getBasePath 附近 → 可能是路径相关 |
| #356 | `d@o`N[5J~HiLxjcC@.D`Zo` | 在 getText 附近 → 可能是文本标签 |
| #367 | `Kc&#123;K` | 在 WebViewDataTypeEnum 附近 → 可能是枚举值 |
| #399 | `c0%ry(?;b:&#125;x?qX...<`(Bi`:eqUc!~&#125;&#125;3n=` | 在 setErrorText 附近 → 可能是错误文本 |
| #405 | `o)_u_3PnIqL3gT]"XloNQeflEi7YJoJrqZbq` | 在 getTextField 附近 → 可能是标签 |

## 4. 解码方案

### 4.1 方案一：运行时 Hook（推荐）

通过 Java Agent 注入，在 H() 方法入口处拦截，记录所有调用和返回值：

```java
// Java Agent premain 方法
public static void premain(String args, Instrumentation inst) &#123;
    new AgentBuilder.Default()
        .type(ElementMatchers.named("com.aicode.util.AICodeStringUtil"))
        .transform((builder, typeDescription, classLoader, module) ->
            builder.method(ElementMatchers.named("H"))
                   .intercept(Advice.to(HInterceptor.class))
        ).installOn(inst);
&#125;

// 拦截器
class HInterceptor &#123;
    @Advice.OnMethodEnter
    static void onEnter(@Advice.Argument(0) Object arg) &#123;
        System.out.println("H() input: " + arg);
    &#125;
    @Advice.OnMethodExit
    static void onExit(@Advice.Return String result) &#123;
        System.out.println("H() decoded: " + result);
    &#125;
&#125;
```

**优点**：一次性获取所有解码结果，无需逐个计算密钥
**缺点**：需要运行环境，需要安装 Java Agent

### 4.2 方案二：静态解码（Python 实现）

基于反汇编的 H() 算法，用 Python 重现解码逻辑：

```python
def decode_h_string(obfuscated: str, caller_class: str, caller_method: str) -> str:
    """
    模拟 AICodeStringUtil.H() 的 XOR 解码算法
    
    参数:
        obfuscated: 混淆后的字符串
        caller_class: 调用者的完整类名 (如 "com/aicode/action/AcceptInlaysAction")
        caller_method: 调用者的方法名 (如 "actionPerformed")
    """
    # 构造密钥: 方法名 + 类名
    key = caller_method + caller_class
    
    # 计算偏移值 (从字节码提取)
    # offset1 = (1 << 3) ^ 3 ^ (4 << 3) ^ 3 ^ (4 << 4) ^ (1 << 4)
    # = 8 ^ 3 ^ 32 ^ 3 ^ 64 ^ 16 = 82
    
    result = []
    key_len = len(key)
    
    for i, ch in enumerate(obfuscated):
        # XOR with key character (cycling through key)
        key_char = key[(i + some_offset) % key_len]
        decoded = chr(ord(ch) ^ ord(key_char) ^ some_offset2)
        result.append(decoded)
    
    return ''.join(result)
```

**问题**：偏移值 `some_offset` 和 `some_offset2` 的精确计算需要完整反汇编每个 H() 方法。不同定义点的 H() 方法可能有不同的偏移计算逻辑。

### 4.3 方案三：枚举所有可能的调用点

由于密钥 = 方法名 + 类名，且类名和方法名可以从常量池提取，可以：

1. 提取所有 312 个调用类的类名和方法名
2. 对每个混淆字符串，枚举所有可能的密钥组合
3. 检查解码结果是否为合理的 ASCII 文本

**优点**：纯静态分析，无需运行环境
**缺点**：计算量大，可能产生误判

## 5. enum() 方法 — 预编码字符串表

`AICodeStringUtil` 还包含一个 `enum(int)` 方法（1203 字节字节码），这是一个**静态初始化方法**，包含大量预编码的混淆字符串。该方法在类加载时执行，将混淆字符串解码后存储到内部数组中。

从常量池分析，`enum()` 方法引用了以下 H() 定义点来解码其内部的混淆字符串：
- `com/aicode/diff/GenericUtils.H()`
- `com/aicode/util/PositionUtil.H()`

这表明 `enum()` 方法内部使用其他类的 H() 作为解码器，形成了**跨类解码链**。

## 6. 混淆体系架构图

```
┌─────────────────────────────────────────────────────────────┐
│                    iFlyCode 字符串混淆体系                      │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  编译时 (Build Time):                                        │
│  ┌──────────┐    ┌──────────┐    ┌──────────┐              │
│  │ 源代码    │ →  │ H()编码器 │ →  │ 混淆字符串 │              │
│  │ 明文字符串 │    │ XOR加密   │    │ 常量池存储 │              │
│  └──────────┘    └──────────┘    └──────────┘              │
│       │               │               │                     │
│       │   密钥 = 调用者方法名+类名      │                     │
│       │               │               │                     │
│  运行时 (Runtime):                                          │
│  ┌──────────┐    ┌──────────┐    ┌──────────┐              │
│  │ 调用代码   │ →  │ H()解码器 │ →  │ 明文字符串 │              │
│  │ 312个类   │    │ 27个定义点 │    │ 返回结果   │              │
│  └──────────┘    └──────────┘    └──────────┘              │
│       │               │               │                     │
│       │   密钥 = stackTrace[1]        │                     │
│       │   .getMethodName()            │                     │
│       │   + .getClassName()           │                     │
│       └───────────────────────────────┘                     │
│                                                             │
│  跨类解码链:                                                 │
│  AICodeStringUtil.enum() → GenericUtils.H() → 解码结果       │
│  AICodeStringUtil.enum() → PositionUtil.H() → 解码结果       │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

## 7. 影响范围

- **312 个类**使用了 H() 混淆解码
- **27 个类**定义了 H() 方法
- 每个定义类包含 **5-30+ 个混淆字符串**
- 总计约 **500-800 个混淆字符串**需要解码
- 混淆覆盖了：API 端点、错误消息、UI 标签、配置键名、枚举值、日志消息等

## 8. 下一步行动

1. **安装 Java 运行时** — 使 javap 可用，完整反编译所有 H() 方法
2. **运行时 Hook** — 使用 ByteBuddy Agent 拦截所有 H() 调用，一次性获取所有明文
3. **静态解码器** — 完善 Python 解码器，逐个方法计算偏移值
4. **跨类解码链分析** — 追踪 enum() 方法中的跨类调用关系
5. **明文字符串映射表** — 创建 docs/29-obfuscated-strings.md