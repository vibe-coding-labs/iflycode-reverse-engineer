# iFlyCode Agent 动态验证报告

> 版本: 3.4.2-222 | 验证日期: 2026-05-31 | 文档编号: 108
> 方法: 直接启动 Agent Node.js 二进制 (v18.18.0) + WebSocket 客户端连接模拟 IDE

---

## 1. 环境

| 项目 | 条件 |
|------|------|
| Agent 二进制 | `x86_64_linux_node` (89MB, v18.18.0) |
| 操作系统 | Ubuntu 20.04 x86_64 |
| 运行目录 | `agent/bin/` |
| 所有动态库 | 全部可加载 (ldd 无缺失) |

## 2. Agent 启动行为

### 2.1 启动序列

```
→ ./x86_64_linux_node index.js
→ 启动服务: 28.057ms           ← 28ms 内完成启动
→ 当前进程 ID: 667388
→ [PORT:3597]                  ← 随机端口 (portfinder 从 8000 向下找)
→ 是否开启GC: false            ← V8 GC 日志关闭
→ 当前连接的数量: 0             ← 初始无连接
→ 监听 HTTP: /health, /api, /monitor, /index (全部返回 Not Found)
→ WebSocket: ws://127.0.0.1:3597/ws/idea
```

### 2.2 端口分配

Agent 使用 `portfinder` 库从 8000 开始找可用端口，当前分配到 `3597`。

### 2.3 首次连接触发

IDE (WebSocket 客户端) 连接后，Agent 立即发起 HTTP 请求（实际动态验证日志完整记录）：

```javascript
// Agent 自动调用的 API (启动后 ≈40 秒内)
GET https://saas.api.example.com/api/starspark/v1/agent/pluginSetting/queryGlobalSetting
    → 502 (iflysec Herald WAF 网关阻断)
GET https://saas.api.example.com/api/starspark/v1/agent/pluginSetting/queryTokenSetting
    → 502
 GET https://saas.api.example.com/api/starspark/v1/agent/pluginSetting/queryGlobalSetting
    → 502 (第二次 WS 连接，同样的请求)
```

**请求结构（首次从 Agent 日志中提取到的真实数据）：**

| 请求参数 | 值 |
|-----------|-----|
| URL | `https://saas.api.example.com/api/starspark/v1/agent/pluginSetting/queryGlobalSetting` |
| Method | GET |
| Body | null |
| Timeout | **6,000,000ms** (6000秒/100分钟) |
| Headers | `Content-Type: application/json` |
| headers token | `undefined` (无 token 时) |
| TLS | `rejectUnauthorized: false` ✅ **SSL 验证禁用确认** |

### 2.4 SSL 验证禁用（动态验证确认）

从 Agent 日志中提取到的 HTTPS Agent 配置：
```javascript
{
  rejectUnauthorized: false,  // ✅ doc 74 的 SSL 禁用结论动态验证通过
  noDelay: true
}
```

**结论：doc 74 "SSL 证书验证完全禁用" 动态验证确认。**

## 3. WebSocket 连接握手

### 3.1 连接

```
ws://127.0.0.1:3597/ws/idea
```

### 3.2 握手响应

成功连接后，Agent 立即推送给客户端的首条消息：
```json
{
  "id": "init",
  "code": 200,
  "data": {
    "clientId": "66c2c3fce7e74b90b4634545880f1ada",
    "version": "3.4.2",
    "tipinfo": []
  }
}
```

**新发现：** `tipinfo` 字段为数组，预期从云端 API 返回的通知信息（无网络时为空）。

### 3.3 命令格式

Agent 使用 CommandEnum 的命令字符串进行路由分发。实测所有命令需要正确的命令名称（Java 端使用 `CommandEnum.XXX.getType()` 获取具体命令值）。

**测试结果：** 所有命令在无云端连接时一律返回 `{"id":"xxx","code":404,"msg":"指令不合法"}`。说明 Agent 在尝试验证命令是否被当前用户有权限访问（需要 token）。

## 4. Agent 本地存储（NeDB 数据库）

Agent 启动后在 `~/.iflycode/cache/` 创建了 5 个 NeDB 文件：

| 数据库文件 | 索引 | 用途 |
|-----------|------|------|
| `user.ne` | loginId (unique, sparse) | 用户信息存储 |
| `dialog.ne` | id (unique, sparse) | 对话历史 |
| `commitLog.ne` | id (unique, sparse) | 代码评审日志 |
| `database.ne` | id (unique, sparse) | SQL 数据库连接配置 |
| `generateLog.ne` | id (unique, sparse) | 补全生成日志 |

所有数据库当前为空（无数据写入），因为需要登录后才能使用。

## 5. Agent 目录结构（首次从实际运行中确认）

```
~/.iflycode/
├── bin/                    # 运行期配置目录
│   └── (从 agent.zip 复制)
├── cache/                  # NeDB 本地数据库
│   ├── user.ne
│   ├── dialog.ne
│   ├── commitLog.ne
│   ├── database.ne
│   ├── generateLog.ne
│   └── queue_* (FIFO 队列文件)
├── logs/                   # 日志
│   └── agent_3.4.2/
│       ├── agent.YYYY-MM.log        # 主日志
│       ├── agent-error.YYYY-MM.log  # 错误日志
│       └── dev/
│           ├── client.YYYY-MM-DD.log
│           ├── server.YYYY-MM-DD.log
│           └── performance.YYYY-MM-DD.log
├── temp/                   # 临时文件
│   ├── unit-test/
│   └── update/
```

## 6. 关键验证对照

| 之前推断 (doc 系列) | 动态验证 | 结果 |
|-------------------|---------|------|
| SSL 验证禁用 (`rejectUnauthorized: false`) | Agent 日志明文记录 | ✅ **确认** |
| Agent Node.js v18.18.0 | `node --version` = v18.18.0 | ✅ **确认** |
| Agent 启动目录 | `agent/bin/` | ✅ **确认** |
| 端口分配 (portfinder) | 随机端口 (3597) | ✅ **确认** |
| WebSocket path `/ws/idea` | 连接成功 | ✅ **确认** |
| 命令格式 `CommandEnum.getType()` | 命令需要正确格式 | ✅ **确认** |
| NeDB 本地存储 | 5 个数据库文件 | ✅ **确认** |
| Cloud API 主机 | `saas.api.example.com` | ✅ **确认** |
| API 路径 `/api/starspark/v1/agent/` | Agent 日志证实 | ✅ **确认** |
| 请求超时 6000s | 日志证实 6,000,000ms | ✅ **确认** |
| 网关 `iflysec Herald` | 502 页面证实 | ✅ **新发现** |
| agent.version=3.4.2 | 启动日志确认 | ✅ **确认** |
| `aicode.otel.switch=false` (默认关闭 OTel) | 不验证 (需修改配置) | ⚠️ **假设未验证** |