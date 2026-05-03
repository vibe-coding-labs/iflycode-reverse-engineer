# 02 Agent 进程管理

## Agent 二进制文件

Agent 是一个 Node.js 应用程序，打包为平台特定的独立可执行文件：

| 文件名 | 平台 | 架构 | 大小 |
|--------|------|------|------|
| `x86_64_darwin_arm_node` | macOS | ARM64 (Apple Silicon) | 84 MB |
| `x86_64_darwin_node` | macOS | x86_64 (Intel) | 89 MB |
| `x86_64_linux_node` | Linux | x86_64 | 88 MB |
| `x86_64_windows_node.exe` | Windows | x86_64 | 68 MB |
| `x86_64_windows7_node.exe` | Windows 7 | x86_64 | 29 MB |

主程序入口: `agent/bin/index.js` (3.8 MB webpack bundle)

## Agent 启动流程

```
1. PluginStartupActivity.runActivity()
   └─► RestartableAgentProcessService.init()
       └─► createInitializedDelegate()
           └─► new PluginAgentProcessServiceImpl()
               ├─► unZipAgent()     — 首次运行解压 agent.zip
               ├─► copySource()     — 复制 WASM 文件
               └─► launchAgent()    — 启动 Agent 进程
                   └─► PluginAgentCommandLine.createAgentCommandLine()
                       └─► createAgentBinaryCommandline()
                           ├─► findAgentBinary()     — 查找平台二进制
                           ├─► chmod a+x (Unix)      — 设置可执行权限
                           └─► 构建启动命令
```

## 启动命令

```bash
# 命令格式
<binary_path> <index.js_path> <os_arch>

# 实际示例 (macOS ARM)
~/.iflycode/bin/agent/bin/x86_64_darwin_arm_node \
    ~/.iflycode/bin/agent/bin/index.js \
    aarch64

# 环境变量
ELECTRON_RUN_AS_NODE=""   # 禁止 Electron 行为
NODE_OPTIONS=""            # 清除用户 Node 配置
```

## 端口发现机制

Agent 进程启动后通过 stdout 输出监听端口号：

```java
// PluginAgentProcessHandler.onTextAvailable()
Pattern.compile("\\d+x+N&2");  // 混淆后的正则，匹配端口号
```

端口轮询获取：

```java
// RestartableAgentProcessService.JD()
// 最多重试 5 次，每次等待递增时间
Pair pair = agentService.getAgentPort(pid, timeout);
while (StringUtils.isBlank(port) && retryCount < 5) {
    pair = agentService.getAgentPort(pid, ++retryCount);
}
```

## WebSocket 连接建立

```
Agent 进程启动 ──stdout──► 输出端口号
                          │
插件解析端口 ◄─────────────┘
     │
     ▼
创建 WebSocket 连接: ws://127.0.0.1:{port}/ws/idea
     │
     ├─ 可选 Header: traceparent (W3C Trace Context)
     │
     ▼
连接成功 (onOpen):
     ├─► 发送 ACTION_INIT (pluginVersion, clientName, apiVersion, projectPath)
     ├─► 发送 USER_LOGIN (data: {count: 1})
     └─► 启动心跳检测
```

## Agent 重启机制

**触发条件**:

| 场景 | RestartEnum | Code |
|------|-------------|------|
| Agent 启动失败 | `START_AGENT` | 0 |
| WebSocket 连接被拒 | `CONNECT_REFUSED` | 1 |
| WebSocket 连接失败 | `CONNECT_FAILED` | 2 |
| WebSocket 连接错误 | `CONNECT_ERROR` | 3 |
| WebSocket 关闭异常 | `CLOSE_EXCEPTION` | 4 |
| WebSocket 关闭错误 | `CLOSE_ERROR` | 5 |
| 端口为空 | `BLANK_PORT` | 6 |
| 刷新重连 | `REFRESH` | 7 |
| 心跳超时 | `HEART_BEAT_ERROR` | 8 |
| 关闭后重连 | `CLOSE_RECONNECT` | 9 |
| 刷新后重连 | `REFRESH_RECONNECT` | 10 |

**重试策略**:
- 最大重启次数: 3 次
- 重试间隔: 3 秒
- 连接拒绝: 连续 3 次拒绝后重启
- 达到上限后: 停止重试，通知 WebView 显示刷新状态

## Agent 进程终止

```java
// 关闭所有 WebSocket
PluginWebsocketClient.closeWebsocket("plugin dispose");
// 关闭 Agent 进程
agentProcess.shutdown();
// 强制终止
agentProcess.killProcess();
// 清理残留进程 (按进程名匹配)
RestartableAgentProcessService.killAgent();
```

## 本地存储

Agent 使用 **sqlite3** 进行本地数据持久化（Native Addon）：

| 平台 | 文件 | 架构 |
|------|------|------|
| macOS ARM | `sqlite3-darwin-arm64.node` | arm64 |
| macOS Intel | `sqlite3-darwin-x64.node` | x86_64 |
| Linux | `sqlite3-linux-x64.node` | x86_64 |
| Windows 32 | `sqlite3-win32-ia32.node` | x86 |
| Windows 64 | `sqlite3-win32-x64.node` | x86_64 |

## WASM 代码解析

Agent 使用 tree-sitter 进行代码解析：

| WASM 文件 | 语言 | 大小 |
|-----------|------|------|
| `tree-sitter.wasm` | 基础运行时 | 189 KB |
| `tree-sitter-c.wasm` | C | 774 KB |
| `tree-sitter-java.wasm` | Java | 420 KB |
| `tree-sitter-javascript.wasm` | JavaScript | 628 KB |
| `tree-sitter-python.wasm` | Python | 463 KB |
| `tree-sitter-go.wasm` | Go | 202 KB |
| `tree-sitter-cpp.wasm` | C++ | 4.5 MB |
| `tree-sitter-c_sharp.wasm` | C# | 5.6 MB |
| `tree-sitter-typescript.wasm` | TypeScript | 2.2 MB |
| `tree-sitter-tsx.wasm` | TSX | 1.4 MB |
