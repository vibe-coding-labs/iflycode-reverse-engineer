# 01 整体架构

## 三层通信模型

iFlyCode 采用三层通信架构，将 IDE 前端、本地代理和云端服务解耦：

```
┌─────────────────────────────────────────────────────────────────┐
│                       JetBrains IDE                              │
│                                                                  │
│  ┌──────────────┐   JCEF WebView    ┌──────────────────────┐    │
│  │  Editor 集成   │◄──────────────► │  WebView Panel (UI)   │    │
│  │  Inlay/Action │   JS Bridge      │  (JCEF Browser)       │    │
│  └──────┬────────┘                  └──────────┬───────────┘    │
│         │                                      │                │
│         │ Java API                             │ JS→Java        │
│         ▼                                      ▼                │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │                Plugin Core (Java/Kotlin)                  │   │
│  │                                                          │   │
│  │  ┌────────────────────┐  ┌────────────────────────────┐  │   │
│  │  │ Service 层          │  │ DTO 层                     │  │   │
│  │  │ ChatService         │  │ MessageDto (请求)           │  │   │
│  │  │ UserService         │  │ ResponseDto (响应)          │  │   │
│  │  │ CodeCompleteService │  │ ResponseStreamDto (流式)    │  │   │
│  │  │ SqlService          │  │ FirstChatMessage           │  │   │
│  │  │ InlineChatCmdService│  │ CodeInfoDto                │  │   │
│  │  │ GitReviewService    │  │ ...                        │  │   │
│  │  └─────────┬──────────┘  └────────────────────────────┘  │   │
│  │            │                                              │   │
│  │            ▼                                              │   │
│  │  ┌──────────────────────────────────┐                    │   │
│  │  │ PluginWebsocketClient            │                    │   │
│  │  │ OkHttp 4.12.0 WebSocket          │                    │   │
│  │  │ 超时: connect/read/write = 60s   │                    │   │
│  │  └──────────────┬───────────────────┘                    │   │
│  └─────────────────┼────────────────────────────────────────┘   │
│                    │ WebSocket (JSON)                            │
│                    │ ws://127.0.0.1:&#123;动态端口&#125;/ws/idea            │
│                    ▼                                             │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │           Local Agent (Node.js 子进程)                     │   │
│  │                                                          │   │
│  │  平台特定 Node.js 二进制 + index.js (3.8MB webpack bundle)  │   │
│  │  - sqlite3 本地存储                                        │   │
│  │  - tree-sitter WASM 代码解析                               │   │
│  │  - snappy 压缩                                             │   │
│  │                                                          │   │
│  │  端口: 动态分配, 通过 stdout 输出                            │   │
│  └──────────────┬───────────────────────────────────────────┘   │
│                 │ HTTPS                                          │
│                 ▼                                                │
│        ┌─────────────────────────────┐                           │
│        │     iFlyCode 云端服务        │                           │
│        │                             │                           │
│        │ SaaS: saas.api.example.com                        │
│        │ API:  iflycode-api.example.com                        │
│        │                             │                           │
│        │ /api/starspark/v1/agent/*  — AI 功能                   │
│        │ /api/ragserver/v1/*        — RAG 搜索                  │
│        │ /api/usercenter/v1/*       — 用户中心                  │
│        └─────────────────────────────┘                           │
└─────────────────────────────────────────────────────────────────┘
```

## 通信链路总结

| 链路 | 协议 | 方向 | 数据格式 |
|------|------|------|---------|
| IDE ↔ Agent | WebSocket | 双向 | JSON (MessageDto/ResponseDto) |
| IDE 内部 (Java ↔ WebView) | JCEF JS Bridge | 双向 | JSON (WebViewDataTypeEnum) |
| Agent ↔ 云端 | HTTPS | 双向 | JSON (推测) |

## 插件目录结构

```
com/aicode/
├── PluginStartupActivity.class        # 启动入口
├── agent/                             # Agent 通信核心
│   ├── PluginWebsocketClient.class    # WebSocket 客户端 (OkHttp)
│   ├── PluginWebsocketListener.class  # WebSocket 事件监听
│   ├── SocketMessageHandleListener.class # 消息路由分发
│   ├── HeartBeatCheckRunner.class     # 心跳 (30s)
│   ├── AgentCheckTimer.class          # Agent 健康检查
│   ├── PluginAgentCommandLine.class   # Agent 启动命令
│   ├── PluginAgentProcessHandler.class # Agent 进程管理
│   ├── dto/                           # 数据传输对象
│   ├── enums/                         # 命令/模块枚举
│   └── service/                       # 功能服务层
├── view/                              # UI (JCEF WebView)
├── settings/                          # 持久化设置
├── inline/                            # 内联聊天
├── listener/                          # IDE 事件监听
├── action/                            # 用户操作
├── template/                          # 单测模板
├── test/                              # 单元测试
├── apm/                               # OpenTelemetry APM
└── util/                              # 工具类
```

## 运行时目录 (`~/.iflycode/`)

```
~/.iflycode/
├── bin/
│   └── agent/
│       ├── bin/
│       │   ├── x86_64_darwin_arm_node    # macOS ARM64
│       │   ├── x86_64_darwin_node        # macOS Intel
│       │   ├── x86_64_linux_node         # Linux x64
│       │   ├── x86_64_windows_node.exe   # Windows x64
│       │   ├── index.js                  # Agent 主程序 (3.8MB)
│       │   ├── config.json               # Agent 配置
│       │   ├── build/Release/            # sqlite3 native
│       │   └── @napi-rs/                 # snappy native
│       ├── wasms/                        # tree-sitter WASM
│       └── fileTemplates/               # 模板文件
```
