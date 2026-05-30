# iFlyCode 3.4.2-222 逆向分析文档

> 讯飞星火飞码 JetBrains 插件通信协议逆向分析

## 基本信息

| 项目 | 值 |
|------|-----|
| 插件名称 | iFlyCode (星火飞码) |
| 版本 | 3.4.2-222 |
| 插件ID | `com.iflytek` |
| 厂商 | 安徽卓见科技有限公司 |
| 底层模型 | 讯飞星火大模型 |
| IDE 兼容性 | JetBrains 2020.3+ / Android Studio |
| 类文件数 | 574 个 |
| 主包 | `com.aicode.*` |

## 文档目录

### 架构与基础设施
- [01-architecture.md](01-architecture.md) — 整体架构与三层通信模型
- [02-agent-process.md](02-agent-process.md) — Agent 进程管理与生命周期
- [03-server-endpoints.md](03-server-endpoints.md) — Agent 服务端接口与端点分析

### 通信协议
- [04-websocket-protocol.md](04-websocket-protocol.md) — WebSocket 通信协议
- [05-message-formats.md](05-message-formats.md) — 消息格式定义 (全部 DTO)
- [06-command-reference.md](06-command-reference.md) — 命令体系完整参考 (109 个命令)

### UI 通信
- [07-webview-bridge.md](07-webview-bridge.md) — WebView JS Bridge 协议 (124 种消息类型)

### 功能流程
- [08-auth-flow.md](08-auth-flow.md) — 用户认证流程
- [09-chat-protocol.md](09-chat-protocol.md) — 智能对话协议
- [10-code-complete-protocol.md](10-code-complete-protocol.md) — 代码补全协议
- [11-inline-chat-protocol.md](11-inline-chat-protocol.md) — 内联聊天协议
- [12-sql-protocol.md](12-sql-protocol.md) — SQL 生成/优化协议
- [13-unit-test-protocol.md](13-unit-test-protocol.md) — 单元测试协议
- [14-git-review-protocol.md](14-git-review-protocol.md) — Git 评审协议
- [15-code-search-protocol.md](15-code-search-protocol.md) — 代码搜索协议
- [16-code-check-protocol.md](16-code-check-protocol.md) — 代码检查协议

### 运维与监控
- [17-heartbeat-error.md](17-heartbeat-error.md) — 心跳检测与错误恢复
- [18-telemetry.md](18-telemetry.md) — OpenTelemetry APM 遥测
- [19-settings-protocol.md](19-settings-protocol.md) — 设置同步协议

### 附录
- [20-enums-reference.md](20-enums-reference.md) — 枚举值完整参考
- [21-obfuscation.md](21-obfuscation.md) — 混淆技术分析
- [22-agent-cloud-protocol.md](22-agent-cloud-protocol.md) — Agent→Cloud HTTPS 通信协议 (64 个 API 端点)
- [23-agent-internals.md](23-agent-internals.md) — Agent 内部架构与 Prompt 模板

### 完整反编译补充分析
- [103-missing-classes-decompilation-analysis.md](103-missing-classes-decompilation-analysis.md) — 缺失类批量反编译报告 (413 个 .java 文件, 47 个新包, 9 大新发现)
- [104-final-blindspot-elimination.md](104-final-blindspot-elimination.md) — 最终盲区清零报告 (Worker.js/Agent二进制/WebView实物/配置映射表/跨文档交叉验证/覆盖矩阵)
- [105-velocity-templates-and-final-blindspots.md](105-velocity-templates-and-final-blindspots.md) — Velocity 模板系统 + 终极扫尾 (7 个单测模板/defaultTypeValues/SM2-AES验证/Q包真相/FeatureProbe/plugin.xml交叉/最终覆盖矩阵)
- [106-agent-webpack-modules-and-full-class-inventory.md](106-agent-webpack-modules-and-full-class-inventory.md) — Agent webpack 模块解构 (1156 模块精确映射) + 完整类清单 v2 (68 包/413 类) + WebSocket 端到端命令映射 (30 发送类) + 最终覆盖矩阵
