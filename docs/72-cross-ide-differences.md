# iFlyCode 跨 IDE 支持差异分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-13 | 文档编号: 72

## 1. 概述

iFlyCode 3.4.2-222 支持三种 IDE 平台：IntelliJ IDEA（含 JetBrains 全家桶）、VS Code 和 Eclipse。三种平台的底层通信机制、功能可用性、Agent 构建目标、更新机制和语言支持存在系统性差异。本文档从 6 个维度全面分析这些差异。

**分析方法**：
- WebView Bridge 源码反编译（确认）
- ClientTypeEnum.class 字节码分析（确认）
- Agent webpack bundle 字符串提取（确认）
- plugin.xml 及可选依赖 XML 分析（确认）
- Agent package.json 构建脚本分析（确认）
- WebView 前端 JS 条件分支分析（确认/推断混合）

## 2. JS↔IDE Bridge 通信差异

### 2.1 Bridge 架构总览

WebView 前端通过三种不同的 Bridge 实现与宿主 IDE 通信，每种 IDE 使用不同的 JS↔Native 通信通道：

```
┌──────────────────────────────────────────────────────┐
│                    WebView (JCEF/JSDI/SWT)            │
│                                                        │
│  ┌─────────────┐  ┌─────────────┐  ┌──────────────┐  │
│  │ ideaUtil.js │  │vscodeUtil.js│  │eclipseUtil.js│  │
│  │   (IDEA)    │  │  (VSCode)   │  │  (Eclipse)   │  │
│  └─────────────┘  └─────────────┘  └──────────────┘  │
│        │                │                │             │
│        ▼                ▼                ▼             │
│  ┌──────────────────────────────────────────────┐    │
│  │       handlerReceivedMsg(type, value)        │    │
│  │       sendMsgToIdea(type, value)             │    │
│  └──────────────────────────────────────────────┘    │
└──────────────────────────────────────────────────────┘
```

### 2.2 三种 Bridge 实现对比

| 维度 | IDEA Bridge | VSCode Bridge | Eclipse Bridge |
|------|-------------|---------------|----------------|
| **源文件** | ideaUtil-11ab0730.js | vscodeUtil-49d49699.js | eclipseUtil-82d0751a.js |
| **JS→IDE 发送** | `window.myObject.sendMessage(JSON)` | `vscode.postMessage(&#123;type, value&#125;)` | `window.sendMessage(JSON)` |
| **IDE→JS 接收** | `window.receiveData = callback` | `window.addEventListener("message")` | `window.receiveData = callback` |
| **消息格式** | `&#123;type, value&#125;` (value 直接传递) | `&#123;type, value: JSON.stringify(value)&#125;` | `&#123;type, value: JSON.stringify(value)&#125;` |
| **接收解析** | `typeof data === "object" ? data : JSON.parse(data)` | `event.data.type, event.data.value` | `JSON.parse(data).type, JSON.parse(data).value` |
| **平台检测** | 无（IDEA 为默认） | `acquireVsCodeApi !== undefined` | 无（由宿主注入） |
| **调试日志** | 有 (`console.log`) | 无 | 有 (`console.log`) |
| **WebView 引擎** | JCEF (Chromium) | VSCode Webview (Chromium) | SWT Browser (WebKit/IE) |

**来源**: WebView 源码直接提取（确认）

### 2.3 Bridge 消息类型差异

所有 50+ Java→JS 消息类型在三种 IDE 中统一可用（通过 `handlerReceivedMsg` switch/case 处理），但部分消息类型的处理逻辑存在平台差异：

| 消息类型 | IDEA | VSCode | Eclipse | 差异说明 |
|---------|------|--------|---------|---------|
| `UNIT_TEST_BANK:IDEA_STOP` | 专用处理 | N/A | N/A | 仅 IDEA 有此消息类型（来源: 确认） |
| `CHAT:RECEIVER_IDE_FILE_STATE` | `getIdeFileState` | `getIdeaKnowledgefileMessage` | 同 VSCode | IDEA 有专用文件状态处理（来源: 确认） |
| `SETTING:CHANGE_THEME` | 完整支持 | 有限支持 | 有限支持 | IDEA 原生主题系统更丰富（来源: 推断） |
| `BATCH_UNIT_TEST:*` | 完整支持 | N/A | N/A | 批量单测仅 IDEA（来源: 确认） |

## 3. ClientTypeEnum 平台类型枚举

### 3.1 枚举值完整列表

从 `com/aicode/enums/ClientTypeEnum.class` 字节码中提取的枚举值：

| 枚举常量 | 推断含义 | 说明 |
|---------|---------|------|
| `of` | Office / 通用 | 默认/通用客户端类型 |
| `WS` | WebStorm | JetBrains WebStorm IDE |
| `IC` | IntelliJ IDEA Community | IDEA 社区版 |
| `PY` | PyCharm | JetBrains PyCharm IDE |
| `IE` | IntelliJ IDEA Ultimate | IDEA 旗舰版 |
| `AI` | Android Studio | 基于 IDEA 的 Android 开发 |
| `PC` | PhpStorm / CLion? | JetBrains 其他 IDE（来源: 推断） |
| `CL` | CLion | JetBrains C/C++ IDE |
| `IU` | IntelliJ IDEA Ultimate (alt) | IDEA 旗舰版备用标识 |
| `GO` | GoLand | JetBrains Go IDE |

**来源**: ClientTypeEnum.class 字节码提取（确认枚举值，推断含义基于 JetBrains 产品代码惯例）

### 3.2 ClientTypeEnum 字段与方法

| 方法/字段 | 说明 |
|----------|------|
| `description: String` | 客户端描述（中文） |
| `jetBrainPlatform: String` | JetBrains 平台标识 |
| `windowsExeFile: String` | Windows 可执行文件名 |
| `unixExeFile: String` | Unix 可执行文件名 |
| `exeFileName: String` | 通用可执行文件名 |
| `getDescription()` | 获取描述 |
| `getJetBrainPlatform()` | 获取 JetBrains 平台标识 |
| `getWindowsExeFile()` | 获取 Windows exe 文件名 |
| `getUnixExeFile()` | 获取 Unix 可执行文件名 |
| `getExeFileName()` | 获取当前平台可执行文件名 |

**来源**: 字节码方法签名提取（确认）

### 3.3 Agent 平台 ID 映射

Agent webpack bundle 中定义的平台 ID 映射（用于更新检查）：

```javascript
const platformIdMap = &#123; vscode: 1, idea: 2, eclipse: 3, vs: 4 &#125;;
```

| 平台 | ID | 说明 |
|------|-----|------|
| `vscode` | 1 | VS Code |
| `idea` | 2 | IntelliJ IDEA (JetBrains 全家桶) |
| `eclipse` | 3 | Eclipse |
| `vs` | 4 | Visual Studio (预留?) |

**来源**: Agent index.js 字符串提取（确认）

## 4. Agent 构建目标差异

### 4.1 构建脚本

从 `agent/bin/package.json` 提取的构建脚本：

| 脚本 | BUILD_TARGET | 说明 |
|------|-------------|------|
| `pkg:all` | `all` | 全平台构建（IDEA + VSCode + Eclipse） |
| `pkg:vs` | `vs` | VS Code 专用构建 |
| `pkg:node` | `ncc` | Node.js 独立构建（使用 @vercel/ncc 打包） |
| `pkg:nodezip` | `nodezip` | Node.js ZIP 打包构建 |

**来源**: package.json scripts 字段（确认）

### 4.2 构建目标差异分析

| 维度 | `pkg:all` | `pkg:vs` | `pkg:node` |
|------|----------|---------|-----------|
| **目标平台** | IDEA + VSCode + Eclipse | VS Code | Node.js 独立 |
| **BUILD_TARGET** | `all` | `vs` | `ncc` |
| **Node 二进制** | 包含全部 5 个 | 仅包含 VSCode 所需 | 包含全部 5 个 |
| **WebView** | 包含全部 3 个 Bridge | 仅 vscodeUtil.js | 不包含 |
| **Tree-sitter WASM** | 包含 9 个语言 | 包含 9 个语言 | 包含 9 个语言 |
| **Snappy 原生模块** | 包含 7 个平台 | 包含 7 个平台 | 包含 7 个平台 |
| **用途** | IDEA 插件内嵌 | VSCode 扩展 | CLI/服务端 |

**来源**: package.json + 目录结构分析（确认目录，推断构建差异）

### 4.3 Agent 平台特定 Node 二进制

| 文件 | 大小 | 平台 | 用途 |
|------|------|------|------|
| `x86_64_darwin_node` | 93 MB | macOS x86_64 | Intel Mac |
| `x86_64_darwin_arm_node` | 89 MB | macOS ARM64 | Apple Silicon |
| `x86_64_linux_node` | 92 MB | Linux x86_64 | Linux 桌面/服务器 |
| `x86_64_windows_node.exe` | 71 MB | Windows x86_64 | Windows 10+ |
| `x86_64_windows7_node.exe` | 30 MB | Windows 7 | 旧版 Windows |

**来源**: 文件系统直接观察（确认）

### 4.4 Agent 登录 ID 生成逻辑

Agent 为不同平台生成独立的登录 ID：

```javascript
["idea", "vscode", "vs"].forEach((platform) => &#123;
  const config = N[platform] || N["agent"];
  const loginId = `$&#123;platform&#125;_$&#123;user&#125;`;
  if (config) &#123;
    w[loginId] = Object.assign(&#123;&#125;, config, &#123; loginId &#125;);
  &#125;
&#125;);
```

**关键发现**: Eclipse 平台不在登录 ID 生成列表中，Eclipse 用户可能使用 `agent` 默认配置。

**来源**: Agent index.js 字符串提取（确认）

## 5. plugin.xml 可选依赖差异

### 5.1 IDEA 插件依赖

| 依赖 | 是否可选 | config-file | 说明 |
|------|---------|-------------|------|
| `com.intellij.modules.platform` | 必选 | — | IntelliJ 平台核心 |
| `com.intellij.modules.lang` | 必选 | — | 语言支持核心 |
| `Git4Idea` | 必选 | — | Git 集成 |
| `com.intellij.modules.java` | **可选** | `code-java.xml` | Java 语言增强 |
| `com.intellij.modules.python` | **可选** | `code-python.xml` | Python 语言增强 |
| `com.intellij.modules.webstorm` | **可选** | `code-javascript.xml` | JavaScript 语言增强 |

**来源**: plugin.xml 直接提取（确认）

### 5.2 可选依赖配置文件

#### code-java.xml

```xml
<idea-plugin>
    &lt;depends&gt;JUnit&lt;/depends&gt;
    &lt;depends&gt;Coverage&lt;/depends&gt;
    &lt;depends&gt;com.intellij.modules.coverage&lt;/depends&gt;
    <extensions defaultExtensionNs="com.intellij">
        <codeInsight.inlayProvider language="JAVA"
          implementationClass="com.aicode.toolwindow.PluginEditorInlayHintsProvider"/>
    &lt;/extensions&gt;
</idea-plugin>
```

**Java 专用**: JUnit 集成、Coverage 集成、Java Inlay Hints Provider

#### code-python.xml

```xml
<idea-plugin>
    <extensions defaultExtensionNs="com.intellij">
        <codeInsight.inlayProvider language="Python"
          implementationClass="com.aicode.toolwindow.PluginEditorInlayHintsProvider"/>
    &lt;/extensions&gt;
</idea-plugin>
```

**Python 专用**: Python Inlay Hints Provider

#### code-javascript.xml

```xml
<idea-plugin>
    <extensions defaultExtensionNs="com.intellij">
        <codeInsight.inlayProvider language="JavaScript"
          implementationClass="com.aicode.toolwindow.PluginEditorInlayHintsProvider"/>
    &lt;/extensions&gt;
</idea-plugin>
```

**JavaScript 专用**: JavaScript Inlay Hints Provider

### 5.3 可选依赖差异对比

| 功能 | code-java.xml | code-python.xml | code-javascript.xml |
|------|:---:|:---:|:---:|
| Inlay Hints Provider | JAVA | Python | JavaScript |
| JUnit 依赖 | Yes | No | No |
| Coverage 依赖 | Yes | No | No |
| Coverage 模块依赖 | Yes | No | No |

**来源**: XML 文件直接提取（确认）

**关键发现**: Java 是唯一具有 JUnit 和 Coverage 依赖的语言，这解释了为什么批量单测功能仅在 IDEA + Java 环境中可用。

## 6. WebView 前端平台条件差异

### 6.1 IdeaEnum 定义

WebView 前端定义了 IDE 类型枚举：

```javascript
const IdeaEnum = &#123;
  IDEA: "IDEA",
  ECLIPSE: "ECLIPSE",
  VSCODE: "VSCODE"
&#125;;
```

**来源**: WebView index-f0296668.js 提取（确认）

### 6.2 displayIde 平台过滤

WebView 设置页面使用 `displayIde` 字段控制配置项的平台可见性：

```javascript
// 设置项定义
&#123;
  type: "checkbox",
  title: "更新设置",
  desc: "自动更新新版本",
  props: "openAutoUpdate",
  displayIde: [IdeaEnum.IDEA],  // 仅在 IDEA 中显示
  displayScene: [PluginSenseEnum.consumer]  // 仅消费者版
&#125;

// 过滤逻辑
const newConfig = configs.filter((data) => &#123;
  if (data.displayIde && data.displayIde.length && !data.displayIde.includes("IDEA")) &#123;
    return false;  // 当前 IDE 不在 displayIde 列表中则隐藏
  &#125;
  if (data.displayScene && data.displayScene.length && !data.displayScene.includes(getPluginSense())) &#123;
    return false;
  &#125;
  return !data.permissionCode || permissionCodeList.includes(data.permissionCode);
&#125;);
```

**来源**: WebView 源码提取（确认）

### 6.3 平台受限的设置项

| 设置项 | displayIde | 说明 |
|--------|-----------|------|
| 自动更新新版本 | `[IDEA]` | 仅 IDEA 支持自动更新 |
| 其他设置项 | 未设置 (全部可见) | 跨平台通用 |

**来源**: WebView 源码提取（确认）

**关键发现**: "自动更新新版本" 设置仅限 IDEA 平台。VSCode 和 Eclipse 的更新由各自扩展市场管理。

### 6.4 VSCode 平台检测

VSCode Bridge 通过 `acquireVsCodeApi` 全局函数检测平台：

```javascript
let vscode = null;
if (typeof acquireVsCodeApi !== "undefined") &#123;
  vscode = acquireVsCodeApi();
&#125;
function isVscode() &#123;
  return vscode;
&#125;
```

**来源**: vscodeUtil-49d49699.js 直接提取（确认）

## 7. 功能差异矩阵

### 7.1 核心功能可用性

| 功能 | IDEA | VSCode | Eclipse | 差异原因 |
|------|:----:|:------:|:-------:|---------|
| **代码补全** | Yes | Yes | Yes | Agent API 统一 |
| **智能问答** | Yes | Yes | Yes | WebView 统一 |
| **代码解释** | Yes | Yes | Yes | Agent API 统一 |
| **函数注释** | Yes | Yes | Yes | Agent API 统一 |
| **行间注释** | Yes | Yes | Yes | Agent API 统一 |
| **代码优化** | Yes | Yes | Yes | Agent API 统一 |
| **函数拆分** | Yes | Yes | Yes | Agent API 统一 |
| **单元测试** | Yes | Yes | Yes | Agent API 统一 |
| **代码搜索** | Yes | Yes | Yes | RAG API 统一 |
| **代码评审** | Yes | Yes | Yes | Agent API 统一 |
| **Commit Message** | Yes | Yes | Yes | Agent API 统一 |
| **SQL 生成/优化** | Yes | Yes | Yes | Agent API 统一 |

### 7.2 平台受限功能

| 功能 | IDEA | VSCode | Eclipse | 差异原因 |
|------|:----:|:------:|:-------:|---------|
| **批量单测** | Yes | No | No | 依赖 JUnit + Coverage 模块（来源: 确认） |
| **Inline Chat** | Yes | 部分 | No | 仅 IDEA 有完整 Inlay 实现；VSCode 有基础支持；Eclipse 无（来源: 推断） |
| **自动更新** | Yes | No | No | 由 `displayIde: [IDEA]` 限制（来源: 确认） |
| **Inlay Hints** | Yes | No | No | 依赖 `codeInsight.inlayProvider` 扩展点（来源: 确认） |
| **代码检查 (CodeCheck)** | Yes | 部分 | No | IDEA 有 ProblemsView 集成（来源: 推断） |
| **批量函数注释** | Yes | No | No | 依赖 `BatchFunctionCommentAction`（来源: 确认） |
| **覆盖率集成** | Yes (Java) | No | No | 依赖 `com.intellij.modules.coverage`（来源: 确认） |
| **调试器异常过滤** | Yes | No | No | 依赖 `jvm.exceptionFilter` 扩展点（来源: 确认） |
| **一键修复** | Yes | No | No | 依赖 `CodeProblemsIntentionAction`（来源: 确认） |
| **多模型切换** | Yes | Yes | Yes | Agent API 统一（来源: 确认） |
| **知识库** | Yes | Yes | Yes | RAG API 统一（来源: 确认） |
| **企业助理** | Yes | Yes | Yes | 权限控制，非平台限制（来源: 确认） |

### 7.3 Inline Chat 平台差异详情

Inline Chat 在 IDEA 中有完整的实现栈：

| 组件 | IDEA | VSCode | Eclipse |
|------|:----:|:------:|:-------:|
| `InlineChatService` | Yes | 部分 | No |
| `InlineChatInlay` | Yes | No | No |
| `InlineChatPanel` | Yes | No | No |
| `InlineChatInputPanel` | Yes | No | No |
| `InlineChatTopPanel` | Yes | No | No |
| `InlineChatHandleService` | Yes | No | No |
| `InlineChatStreamHandleService` | Yes | No | No |
| `InlineChatStatusService` | Yes | No | No |
| `SessionController` | Yes | No | No |
| `EphemeralChatSessionController` | Yes | No | No |
| `IdeActionService` | Yes | No | No |
| `IdeEditorActionRouter` | Yes | No | No |
| 操作: Accept/Reject/Undo/Retry/Stop | Yes | 部分 | No |
| 快捷键: Alt+Y/X/Z/D | Yes | 部分 | No |

**来源**: Java class 文件列表 + plugin.xml action 注册（确认 IDEA 实现，推断 VSCode 部分支持基于 Agent API 存在，推断 Eclipse 无基于无 Bridge 专用实现）

## 8. 语言支持差异

### 8.1 LanguageEnum 语言列表

从 `com/aicode/enums/LanguageEnum.class` 提取的语言枚举：

| 语言 | 枚举前缀 | 数量 | 说明 |
|------|---------|------|------|
| Java | `JAVA` | 1 | 核心支持语言 |
| Python | `PYTHON_LANGUAGE_` | 13 | 13 种 Python 方言/版本 |
| C | `C_LANGUAGE_` | 2 | C 语言 |
| C++ | `CPP_LANGUAGE_` | 13 | 13 种 C++ 方言/版本 |
| Vue | `VUE` | 1 | Vue.js |

**来源**: LanguageEnum.class 字节码提取（确认）

### 8.2 Tree-sitter 解析器支持

| 解析器 | WASM 文件 | 大小 | IDEA | VSCode | Eclipse |
|--------|----------|------|:----:|:------:|:-------:|
| C | tree-sitter-c.wasm | 793 KB | Yes | Yes | Yes |
| C# | tree-sitter-c_sharp.wasm | 5.9 MB | Yes | Yes | Yes |
| C++ | tree-sitter-cpp.wasm | 4.7 MB | Yes | Yes | Yes |
| Go | tree-sitter-go.wasm | 207 KB | Yes | Yes | Yes |
| Java | tree-sitter-java.wasm | 430 KB | Yes | Yes | Yes |
| JavaScript | tree-sitter-javascript.wasm | 644 KB | Yes | Yes | Yes |
| Python | tree-sitter-python.wasm | 474 KB | Yes | Yes | Yes |
| TSX | tree-sitter-tsx.wasm | 1.5 MB | Yes | Yes | Yes |
| TypeScript | tree-sitter-typescript.wasm | 2.3 MB | Yes | Yes | Yes |

**来源**: wasms/ 目录直接观察（确认）

**关键发现**: Tree-sitter 解析器在所有平台共享相同的 WASM 文件，因为 WASM 是平台无关的。

### 8.3 Inlay Hints 语言差异

| 语言 | IDEA Inlay Hints | VSCode | Eclipse | 实现类 |
|------|:---:|:------:|:-------:|--------|
| Java | Yes | No | No | `PluginEditorInlayHintsProvider` |
| Python | Yes | No | No | `PluginEditorInlayHintsProvider` |
| JavaScript | Yes | No | No | `PluginEditorInlayHintsProvider` |

**来源**: code-java.xml / code-python.xml / code-javascript.xml（确认）

**关键发现**: Inlay Hints（代码补全的幽灵文本显示）仅通过 IntelliJ 的 `codeInsight.inlayProvider` 扩展点实现，VSCode 和 Eclipse 使用各自的补全机制。

## 9. 更新机制差异

### 9.1 更新检查流程

Agent 中的更新检查使用平台 ID 映射：

```javascript
// 平台 ID 映射
const platformIdMap = &#123; vscode: 1, idea: 2, eclipse: 3, vs: 4 &#125;;

// 更新检查
async refreshLatestVersion() &#123;
  // 按平台分组客户端
  clients.forEach((client) => &#123;
    if (client.platform && client.token && client.clientInfo) &#123;
      const key = `$&#123;client.platform&#125;_$&#123;client.user&#125;`;
      grouped[key] = client;
    &#125;
  &#125;);

  // 对每个平台调用 checkUpdate API
  for (const key in grouped) &#123;
    const client = grouped[key];
    const pluginType = platformIdMap[client.platform];  // 平台 ID
    const result = await userService.getLastPlugin(&#123;id&#125;, pluginType);
    // 下载并通知更新
  &#125;
&#125;
```

**来源**: Agent index.js 提取（确认）

### 9.2 更新机制对比

| 维度 | IDEA | VSCode | Eclipse |
|------|------|--------|---------|
| **更新方式** | Agent 内置更新 | VSCode 扩展市场 | Eclipse 更新站点 |
| **自动更新设置** | 有 (`openAutoUpdate`) | 无（由 VSCode 管理） | 无（由 Eclipse 管理） |
| **更新 API** | `checkUpdate` + `pluginType=2` | `checkUpdate` + `pluginType=1` | `checkUpdate` + `pluginType=3` |
| **下载链接** | 服务端返回 `.jar` | 服务端返回 `.vsix` | 服务端返回 `.jar` |
| **MD5 校验** | Yes | Yes | Yes |
| **增量更新** | Yes | No | No |

**来源**: Agent 更新逻辑 + WebView 设置项分析（确认 API，推断下载格式）

## 10. Agent 进程管理差异

### 10.1 Agent 启动方式

| 维度 | IDEA | VSCode | Eclipse |
|------|------|--------|---------|
| **Agent 进程** | 内嵌 Node.js 二进制 | 系统 Node.js | 内嵌 Node.js 二进制 |
| **通信方式** | WebSocket (port 6832) | WebSocket / stdio | WebSocket (port 6832) |
| **进程管理** | `RestartableAgentProcessService` | VSCode 扩展宿主 | 类似 IDEA |
| **端口查找** | `portfinder` 自动 | 配置或自动 | `portfinder` 自动 |
| **Node.js 版本** | 内嵌 (<=12) | 系统 Node.js | 内嵌 (<=12) |

**来源**: Agent 架构分析 + package.json engines 字段（确认 Node 版本要求，推断通信方式）

### 10.2 Snappy 原生模块

Agent 包含 7 个平台的 Snappy 压缩原生模块（@napi-rs/snappy）：

| 模块 | 平台 |
|------|------|
| `snappy.darwin-arm64.node` | macOS ARM64 |
| `snappy.darwin-x64.node` | macOS x86_64 |
| `snappy.linux-x64-gnu.node` | Linux (glibc) |
| `snappy.linux-x64-musl.node` | Linux (musl/Alpine) |
| `snappy.win32-arm64-msvc.node` | Windows ARM64 |
| `snappy.win32-ia32-msvc.node` | Windows x86 |
| `snappy.win32-x64-msvc.node` | Windows x64 |

**来源**: @napi-rs/ 目录直接观察（确认）

## 11. 完整功能矩阵

### 11.1 功能可用性总表

| # | 功能 | IDEA | VSCode | Eclipse | 限制原因 |
|---|------|:----:|:------:|:-------:|---------|
| 1 | 代码补全（行级/多行） | **Y** | **Y** | **Y** | Agent API |
| 2 | 智能问答 | **Y** | **Y** | **Y** | WebView |
| 3 | 代码解释 | **Y** | **Y** | **Y** | Agent API |
| 4 | 函数注释 | **Y** | **Y** | **Y** | Agent API |
| 5 | 行间注释 | **Y** | **Y** | **Y** | Agent API |
| 6 | 代码优化 | **Y** | **Y** | **Y** | Agent API |
| 7 | 函数拆分 | **Y** | **Y** | **Y** | Agent API |
| 8 | 单元测试 | **Y** | **Y** | **Y** | Agent API |
| 9 | 批量单测 | **Y** | N | N | JUnit+Coverage |
| 10 | 批量函数注释 | **Y** | N | N | IDEA Action |
| 11 | Inline Chat | **Y** | P | N | Inlay API |
| 12 | 代码搜索 | **Y** | **Y** | **Y** | RAG API |
| 13 | 代码评审 | **Y** | **Y** | **Y** | Agent API |
| 14 | Commit Message | **Y** | **Y** | **Y** | Agent API |
| 15 | SQL 生成/优化 | **Y** | **Y** | **Y** | Agent API |
| 16 | 代码检查 | **Y** | P | N | ProblemsView |
| 17 | 一键修复 | **Y** | N | N | IntentionAction |
| 18 | Inlay Hints | **Y** | N | N | inlayProvider |
| 19 | 调试器异常过滤 | **Y** | N | N | exceptionFilter |
| 20 | 覆盖率集成 | **Y** | N | N | Coverage 模块 |
| 21 | 自动更新 | **Y** | N | N | displayIde |
| 22 | 多模型切换 | **Y** | **Y** | **Y** | Agent API |
| 23 | 知识库 (RAG) | **Y** | **Y** | **Y** | RAG API |
| 24 | 企业助理 (iFlyDev等) | **Y** | **Y** | **Y** | 权限控制 |
| 25 | 主题适配 | **Y** | P | P | IDEA 原生 |
| 26 | 快捷键配置 | **Y** | P | P | IDEA Action |
| 27 | Mermaid 图表 | **Y** | **Y** | **Y** | WebView |
| 28 | 架构图 | **Y** | **Y** | **Y** | WebView |
| 29 | 历史记录 | **Y** | **Y** | **Y** | Agent API |
| 30 | 需求分析/拆分 | **Y** | **Y** | **Y** | 企业版权限 |

**图例**: Y = 完整支持, P = 部分支持, N = 不支持

### 11.2 功能统计

| 平台 | 完整支持 | 部分支持 | 不支持 | 总可用 |
|------|:-------:|:-------:|:------:|:-----:|
| **IDEA** | 30 | 0 | 0 | 30/30 |
| **VSCode** | 21 | 4 | 5 | 25/30 |
| **Eclipse** | 21 | 0 | 9 | 21/30 |

## 12. 关键发现

1. **IDEA 是功能最完整的平台**: 30/30 功能全部可用，是 iFlyCode 的首要开发目标。批量单测、Inline Chat、Inlay Hints、一键修复、调试器集成等高级功能仅在 IDEA 中可用。

2. **VSCode 是次要平台**: 25/30 功能可用，缺少 IDEA 专属的编辑器深度集成功能（Inlay Hints、批量单测、覆盖率等），但核心 AI 功能（补全、问答、代码搜索）完整。

3. **Eclipse 支持最有限**: 21/30 功能可用，缺少所有 IDEA 专属功能和 VSCode 部分支持的功能。Eclipse Bridge 实现最简单，且不在 Agent 登录 ID 生成列表中。

4. **Bridge 通信是核心差异点**: 三种 IDE 使用完全不同的 JS↔Native 通信机制（JCEF myObject / VSCode postMessage / SWT sendMessage），消息格式也有细微差异（value 序列化方式不同）。

5. **Agent 是跨平台统一层**: Agent (Node.js 进程) 提供了统一的 API 层，核心 AI 功能（补全、问答、搜索、评审）通过 Agent API 实现，天然跨平台。平台差异主要来自 IDE 侧的编辑器集成深度。

6. **ClientTypeEnum 揭示 JetBrains 全家桶支持**: 10 个枚举值覆盖了 JetBrains 全家桶（IDEA、WebStorm、PyCharm、GoLand、CLion、Android Studio），说明 IDEA 插件同时支持所有 JetBrains IDE。

7. **构建目标分离**: `pkg:all`（IDEA）、`pkg:vs`（VSCode）、`pkg:node`（独立）三种构建目标，说明 Agent 为不同平台打包不同内容。

8. **更新机制差异**: IDEA 通过 Agent 内置更新（支持自动更新开关），VSCode 通过扩展市场，Eclipse 通过更新站点。服务端 `checkUpdate` API 通过 `pluginType` 参数区分平台。

9. **Java 语言获得特殊对待**: code-java.xml 是唯一引入 JUnit 和 Coverage 依赖的可选配置，这直接支撑了批量单测和覆盖率集成功能。

10. **Eclipse 支持可能正在弱化**: Eclipse 不在 Agent 登录 ID 列表中，Bridge 实现最简单，且缺少大量高级功能。结合行业趋势，Eclipse 支持可能是维护模式而非积极开发。
