# iFlyCode Agent 进程管理深度分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-11

## 1. 概述

iFlyCode 的 Agent 是一个独立的 Node.js 进程，通过 WebSocket 与 IntelliJ 插件通信。插件负责启动、监控和重启 Agent 进程。

## 2. 核心类

### 2.1 PluginAgentProcessHandler

**路径**: `com/aicode/agent/PluginAgentProcessHandler`
**父类**: `KillableProcessHandler` — IntelliJ 可杀进程处理器
**职责**: 管理 Agent 进程的 I/O 和生命周期

**关键发现**:
- 继承自 `KillableProcessHandler`，支持强制终止进程
- 通过 `ProcessListener` 监听进程输出和退出事件

### 2.2 PluginAgentCommandLine

**路径**: `com/aicode/agent/PluginAgentCommandLine`
**职责**: 构建 Agent 进程的启动命令行

**关键字符串常量**:
- `"node"` — Node.js 可执行文件名
- `"index.js"` — 主入口
- `"--max-old-space-size=4096"` — 内存限制 4GB
- `"--max-semi-space-size=64"` — V8 半空间 64MB
- `"--enable-source-maps"` — 启用 Source Maps
- `"--unhandled-rejections=strict"` — 严格 Promise 拒绝
- `"darwin"` / `"linux"` / `"win"` — 平台标识
- `"x64"` / `"arm64"` — 架构标识

### 2.3 RestartableAgentProcessService

**路径**: `com/aicode/agent/RestartableAgentProcessService`
**实现**: `PluginAgentProcessService`, `Disposable`
**职责**: 管理 Agent 进程的自动重启和生命周期

**关键功能**:
- 监控 Agent 进程健康状态
- 进程崩溃时自动重启
- 管理进程生命周期（启动、停止、重启）
- 实现 `Disposable` 接口，IDE 关闭时清理

### 2.4 AgentCheckTimer

**路径**: `com/aicode/agent/AgentCheckTimer`
**职责**: 定时检查 Agent 进程状态

**内部类**:
- `AgentCheckTimer$ba` extends `TimerTask` — 定时任务
- `AgentCheckTimer$ha` extends `TimerTask` — 定时任务

**功能**: 使用 `java.util.Timer` 定期检查 Agent 进程是否存活

### 2.5 HeartBeatCheckRunner

**路径**: `com/aicode/agent/HeartBeatCheckRunner`
**职责**: 心跳检测

**内部类**:
- `HeartBeatCheckRunner$Ga` extends `TimerTask` — 心跳发送任务
- `HeartBeatCheckRunner$ma` extends `TimerTask` — 心跳检测任务

**功能**: 定期向 Agent 发送心跳包，检测连接是否存活

## 3. Agent 进程架构

```
┌─────────────────────────────────────────────────────────────┐
│                    IntelliJ IDEA Plugin                       │
│  ┌─────────────────────────────────────────────────────────┐ │
│  │ RestartableAgentProcessService                          │ │
│  │   ├── PluginAgentCommandLine (构建启动命令)               │ │
│  │   ├── PluginAgentProcessHandler (管理进程 I/O)            │ │
│  │   ├── AgentCheckTimer (定时检查进程状态)                  │ │
│  │   └── HeartBeatCheckRunner (心跳检测)                    │ │
│  └───────────────────────┬─────────────────────────────────┘ │
│                          │ Process I/O                       │
│  ┌───────────────────────┴─────────────────────────────────┐ │
│  │ PluginWebsocketClient                                   │ │
│  │   └── WebSocket 通信 (ws://localhost:&#123;port&#125;)             │ │
│  └─────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
                           │
                    WebSocket (dynamic port)
                           │
┌─────────────────────────────────────────────────────────────┐
│                    Node.js Agent Process                      │
│                                                              │
│  node --max-old-space-size=4096                              │
│       --max-semi-space-size=64                               │
│       --enable-source-maps                                   │
│       --unhandled-rejections=strict                          │
│       index.js                                               │
│                                                              │
│  ┌────────────────────────────────────────────────────────┐  │
│  │ index.js → Express HTTP Server                         │  │
│  │   ├── WebSocket Server (命令分发)                        │  │
│  │   ├── HTTP Routes (API 端点)                            │  │
│  │   └── Worker Threads (并行处理)                         │  │
│  └────────────────────────────────────────────────────────┘  │
│                                                              │
│  ┌────────────────────────────────────────────────────────┐  │
│  │ Tree-sitter Parsers (9 WASM modules)                   │  │
│  │   C, C#, C++, Go, Java, JavaScript, Python, TSX, TS    │  │
│  └────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

## 4. 进程启动流程

```
1. PluginStartupActivity.run()
   └── RestartableAgentProcessService.initComponent()

2. PluginAgentCommandLine.build()
   ├── 检测平台 (darwin/linux/win) 和架构 (x64/arm64)
   ├── 定位 Node.js 二进制: agent/bin/&#123;platform&#125;-&#123;arch&#125;/node
   ├── 构建启动命令:
   │   node --max-old-space-size=4096
   │        --max-semi-space-size=64
   │        --enable-source-maps
   │        --unhandled-rejections=strict
   │        index.js
   └── 设置环境变量和工作目录

3. PluginAgentProcessHandler.start()
   ├── Runtime.exec(command)
   ├── 启动 stdout/stderr 消费线程
   ├── 等待 Agent 就绪 (读取端口输出)
   └── 建立 WebSocket 连接

4. WebSocket 连接建立
   └── ws://localhost:&#123;port&#125;
       ├── 发送初始化消息
       ├── 注册消息处理器
       └── 启动心跳检测
```

## 5. Agent 文件结构

```
agent/
├── bin/
│   ├── index.js          — 主入口 (webpack bundle)
│   ├── worker.js         — Worker 线程入口
│   ├── config.json       — Agent 配置
│   ├── package.json      — 包描述
│   ├── darwin-x64/       — macOS Intel Node.js 二进制
│   ├── darwin-arm64/     — macOS Apple Silicon Node.js 二进制
│   ├── linux-x64/        — Linux Intel Node.js 二进制
│   ├── linux-arm64/      — Linux ARM Node.js 二进制
│   ├── win-x64/          — Windows Intel Node.js 二进制
│   └── win-arm64/        — Windows ARM Node.js 二进制
├── wasms/
│   ├── tree-sitter-c.wasm
│   ├── tree-sitter-c_sharp.wasm
│   ├── tree-sitter-cpp.wasm
│   ├── tree-sitter-go.wasm
│   ├── tree-sitter-java.wasm
│   ├── tree-sitter-javascript.wasm
│   ├── tree-sitter-python.wasm
│   ├── tree-sitter-tsx.wasm
│   └── tree-sitter-typescript.wasm
└── fileTemplates/
    └── velocity.properties
```

## 6. 关键发现

1. **自包含 Node.js**: Agent 捆绑了平台特定的 Node.js 二进制，不依赖用户系统安装的 Node.js。

2. **4GB 内存限制**: `--max-old-space-size=4096` 限制了 V8 堆内存上限。

3. **9 种语言解析器**: 通过 Tree-sitter WASM 模块支持 C, C#, C++, Go, Java, JavaScript, Python, TSX, TypeScript。

4. **Worker 线程**: `worker.js` 表明 Agent 使用 Node.js Worker Threads 进行并行处理。

5. **Source Maps**: 启用 Source Maps 说明 Agent 代码经过 webpack 打包但保留了调试信息。

6. **跨平台支持**: 支持 macOS/Linux/Windows × x64/arm64 = 6 种组合。

7. **心跳机制**: `HeartBeatCheckRunner` 使用 `TimerTask` 定期发送心跳包，检测连接存活。

8. **进程健康检查**: `AgentCheckTimer` 定期检查 Agent 进程是否存活，崩溃时自动重启。
