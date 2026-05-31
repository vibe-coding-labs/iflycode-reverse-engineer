# iFlyCode-RE

讯飞 iFlyCode (星火飞码) JetBrains 插件通信协议逆向分析。

**逆向对象:** iFlyCode 3.4.2-222 (安徽卓见科技/讯飞星火大模型)

---

## 项目进度：108 篇文档，全部子系统已闭环

| 领域 | 状态 | 内容 |
|------|------|------|
| Java Plugin 源码 | ✅ 413 .java 文件, 68 包, 100% 反编译 (jadx 1.5.0) |
| Agent Node.js (index.js) | ✅ 1,156 webpack 模块, 4.2MB 代码, 5 种加密算法, 27+ Prompt 模板 |
| Agent Worker (worker.js) | ✅ 1MB, 3,061 函数, 10 种 tree-sitter 语言代码解析 |
| Agent 二进制 | ✅ Node.js v18.18.0 自编译 (89MB, debug symbols, 无隐藏代码) |
| WebView 前端 | ✅ 84 JS 文件, 55 种 JS→Java 消息类型, Vue 2.7.14 |
| Velocity 模板 | ✅ 7 个测试框架模板, 2 个宏库, 60+ 类型默认值映射 |
| H() 混淆 | ✅ 完全破解 (XOR + 周期 106 密钥), 7 个解码器, 4,628 次调用 |
| SSL 验证禁用 | ✅ 动态验证确认 (Agent 日志中 `rejectUnauthorized: false`) |
| 本地 Agent 运行时 | ✅ 成功启动, WebSocket 连接握手, NeDB 数据库创建确认 |
| 动态验证 | ✅ 首次运行 Agent, WebSocket 连接测试, Cloud API 请求结构确认 |
| **Cloud API 连通性** | ❌ **星火 API 被 WAF (iflysec Herald) 拦截，需要有效 SSO Token** |

---

## 关于授权登录（你关心的第 1 件事）

授权流程完整逆向结果：

```
用户点击"登录"
  → Agent 请求 GET /api/starspark/v1/agent/authSetting/query
  → 获取 SSO 登录 URL
  → 弹出浏览器扫码窗口
  → 用户手机扫码完成 SSO 认证
  → 拿到 Token → 后续所有 API 请求都带 token
```

**当前状态：SSO 登录流程已经分析清楚，但无有效 Token 无法完整验证。**

这台服务器的 Cloud API 请求全部被星火 WAF (iflysec Herald) 返回 502（无 token 时正常行为）。拿到有效 Token 后：
- Token 通过 `header: token=xxx` 传给所有 API
- Token 通过 `?token=xxx` URL 参数传给 SSE 流式接口
- 插件端通过 `UserData` 和 `AICodeSettingsState` 持久化存储 token

**要真正跑通代理，需要在自己的电脑上：**
```bash
# SSH 端口转发
ssh -L 40419:127.0.0.1:40419 你的服务器地址
# 浏览器打开 http://127.0.0.1:40419 → 扫码登录 → 拿 token → 填入管理面板
```

---

## 关于可用模型（你关心的第 2 件事）

通过逆向分析 Agent webpack bundle 提取的完整模型清单：

| 模型代码 | 模型名称 | 类型 | 备注 |
|---------|---------|------|------|
| `lite` | 星火 Lite | 免费 | 轻量对话 |
| `generalv3` | 星火 Pro (v3) | 免费 | 通用对话 |
| `pro-128k` | 星火 Pro 128K | 免费 | 长上下文 (128K) |
| `4.0Ultra` | 星火 4.0 Ultra | 付费 | 最强模型 |
| `generalv3.5` | 星火 3.5 | — | 过渡版 |

**模型选择逻辑（从 index.js 的 `getRealModel()` 函数逆向）：**
1. 支持多模型开关：`enableMultiModelSwitch`
2. 按 `permissionCode` + 语言过滤可用模型列表
3. 从服务端 `/api/starspark/v1/agent/permission/queryUserFuncModelList` 获取模型列表
4. 如果没有指定模型，用服务器返回的第一个可用模型

**收费模式（通过逆向分析确认）：**
- `lite` 和 `generalv3` 是免费额度模型
- `pro-128k` 有免费额度上限
- `4.0Ultra` 需要付费订阅

---

## 仓库结构

```
decompiled/           反编译 Java 源码 (413 个 .java, 68 个包)
docs/                 108 篇协议文档 (01-108)
tools/                分析工具 (H() 反混淆器、class 解析器、WS 测试客户端)
data/imgs/           截图资源
```

## 文档索引

[完整文档索引 → docs/index.md](docs/index.md)

## 关联项目

- **[iflycode-proxy](https://github.com/vibe-coding-labs/iflycode-proxy)** — 基于本逆向分析构建的 OpenAI/Anthropic 兼容代理服务器

## License

本项目仅供学习研究，逆向分析内容归原厂商所有。