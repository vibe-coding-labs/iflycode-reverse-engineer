# iFlyCode-RE

讯飞 iFlyCode (星火飞码) JetBrains 插件通信协议逆向分析。

## 逆向对象

| 项目 | 值 |
|------|-----|
| 插件 | iFlyCode (星火飞码) |
| 版本 | 3.4.2-222 |
| 厂商 | 安徽卓见科技有限公司 |
| 底层模型 | 讯飞星火大模型 |
| 主包 | `com.aicode.*` |
| 类文件数 | 574 |

## 仓库结构

```
decompiled/           反编译 Java 源码 (104 个核心类)
docs/                 协议文档 (23 篇)
  ├── index.md          文档索引
  ├── reverse-engineering-report.md  完整逆向报告
  ├── 01-architecture.md            架构与三层通信模型
  ├── 02-agent-process.md           Agent 进程管理
  ├── 03-server-endpoints.md        服务端 API 端点
  ├── 04-websocket-protocol.md      WebSocket 协议
  ├── 05-message-formats.md         消息格式 (全部 DTO)
  ├── 06-command-reference.md       命令参考 (109 个命令)
  ├── 07-webview-bridge.md          WebView JS Bridge (124 种消息)
  ├── 08-auth-flow.md               认证流程
  ├── 09-chat-protocol.md           智能对话协议
  ├── 10-code-complete-protocol.md  代码补全协议
  ├── 11-inline-chat-protocol.md    内联聊天协议
  ├── 12-sql-protocol.md            SQL 协议
  ├── 13-unit-test-protocol.md      单元测试协议
  ├── 14-git-review-protocol.md     Git 评审协议
  ├── 15-code-search-protocol.md    代码搜索协议
  ├── 16-code-check-protocol.md     代码检查协议
  ├── 17-heartbeat-error.md         心跳与错误恢复
  ├── 18-telemetry.md               OpenTelemetry 遥测
  ├── 19-settings-protocol.md       设置同步协议
  ├── 20-enums-reference.md         枚举值参考
  ├── 21-obfuscation.md             混淆技术分析
  ├── 22-agent-cloud-protocol.md    Agent→Cloud HTTPS 协议 (64 端点)
  └── 23-agent-internals.md         Agent 内部架构与 Prompt 模板
data/imgs/           截图资源
```

## 架构概要

iFlyCode 采用三层通信架构：

```
JetBrains IDE
  ├── Plugin UI ←→ WebView (JCEF + JS Bridge, 124 种消息)
  ├── Java 层 → Agent 进程 (子进程 Node.js)
  └── Agent → Cloud (WebSocket + HTTPS, 64 个 API 端点)
```

**核心发现：**
- Agent 作为独立 Node.js 子进程运行，通过 WebSocket 与 IDE 通信
- 云端 API 路径：`/api/starspark/v1/agent/*`，基于 SSO Token 认证
- 支持 109 个命令，覆盖对话、补全、搜索、检查、测试等场景
- 底层模型为讯飞星火大模型，通过 SSE 流式返回

## 关联项目

- **[iflycode-proxy](https://github.com/vibe-coding-labs/iflycode-proxy)** — 基于本逆向分析构建的 OpenAI/Anthropic 兼容代理服务器，可将 iFlyCode 接入 Claude Code、Codex 等工具

## License

本项目仅供学习研究，逆向分析内容归原厂商所有。
