# 01 整体架构

## 一句话理解

**iFlyCode 是一个 JetBrains IDE 插件，它在你电脑上启动一个本地 Node.js Agent 进程，这个 Agent 负责跟讯飞星火云端 API 通信，帮你完成代码补全、对话、SQL 生成等 AI 功能。**

```
你写代码 → IDE 插件 → Agent (本地 Node.js) → 讯飞星火云端
```

---

## 三层通信模型

系统分为三层，每层职责明确：

### 第 1 层：IDE 前端（Java/Kotlin 插件）

安装在 JetBrains IDE 中，负责所有 UI 交互和编辑器集成。它管理 WebView 面板（JCEF 浏览器）、拦截编辑器事件、处理用户操作。

**关键类：** `PluginStartupActivity`（启动入口）、`PluginWebsocketClient`（与 Agent 通信）

### 第 2 层：本地 Agent（Node.js 子进程）

插件启动时自动启动一个 Node.js 子进程（平台特定二进制），作为本地中转服务器。它接收 IDE 的请求，调用云端 API，返回结果。

**技术栈：** Node.js 18.18.0 + Express WebSocket + sqlite3 + tree-sitter WASM

### 第 3 层：讯飞星火云端

Agent 通过 HTTPS 将请求转发给讯飞星火大模型 API，获取 AI 响应。

---

## 通信链路

| 链路 | 协议 | 通信方式 |
|------|------|---------|
| IDE ↔ Agent | **WebSocket** (ws://127.0.0.1:动态端口) | JSON 消息，双向实时 |
| IDE ↔ WebView面板 | **JCEF JS Bridge** | JSON，IDE内部通信 |
| Agent ↔ 云端 | **HTTPS** | REST API + SSE 流式 |

所有 AI 能力（代码补全、对话、SQL 生成等）都走同一条链路：**IDE → WebSocket → Agent → HTTPS → 云端**，反向流式返回。

---

## 组件目录结构

### IDE 插件端

```
com/aicode/                    # 插件主包
├── PluginStartupActivity      # 启动入口
├── agent/                     # Agent 通信核心
│   ├── PluginWebsocketClient  # WebSocket 客户端
│   ├── service/               # 各功能服务（Chat、Code、SQL等）
│   └── dto/                   # 数据传输对象
├── view/                      # WebView UI
├── inline/                    # 内联聊天
├── action/                    # 用户操作
├── listener/                  # IDE 事件监听
├── template/                  # 单元测试模板
└── settings/                  # 持久化设置
```

### Agent 端

```
~/.iflycode/bin/agent/
├── bin/
│   ├── x86_64_linux_node      # Linux 二进制
│   ├── x86_64_darwin_node     # macOS Intel
│   ├── x86_64_darwin_arm_node # macOS ARM64
│   ├── x86_64_windows_node.exe # Windows
│   └── index.js               # Agent 主程序 (3.8MB webpack bundle)
├── wasms/                     # tree-sitter WASM 解析器
└── fileTemplates/             # 单测模板
```

### Agent 项目结构

```
agent/
├── ChatService                # 对话/评审/提交信息
├── CodeService                # 代码补全
├── CommonService              # 资源/推荐
├── GitService                 # Git操作
├── LoginService               # 认证
├── RagService                 # RAG搜索
├── TestService                # 单元测试
└── SqlService                 # SQL生成/优化
```

---

## 启动流程

```
IDE 启动 → PluginStartupActivity 执行
  → 启动 Agent 子进程（sockJS 检测端口）
  → WebSocket 连接握手（ACTION_INIT 初始化）
  → WebView 面板加载
  → 用户登录（SSO 扫码）
  → 功能可用
```

---

## 逆向覆盖情况

本仓库完整逆向分析覆盖以下内容：

| 组件 | 状态 | 文档数 |
|------|------|--------|
| Java 反编译源码 (413个.java) | ✅ 100% | 20+ 篇 |
| Agent Node.js (1,156 webpack模块) | ✅ 100% | 15+ 篇 |
| WebView 前端 (Vue 2.7) | ✅ 100% | 5+ 篇 |
| H() 混淆算法 | ✅ 完全破解 | 5 篇 |
| 加密系统 (RSA/SM2/SM4/AES/MD5) | ✅ 完整提取 | 3 篇 |
| Velocity 模板 (7个框架) | ✅ 完整提取 | 3 篇 |
| 64 个云端 API 端点 | ✅ 全部映射 | 2 篇 |
| 27 个 Prompt 模板 | ✅ 全部提取 | 1 篇 |
| 动态验证 | ✅ Agent运行确认 | 1 篇 |

> 详细内容见左侧侧边栏各分组。