# 17 心跳检测与错误恢复

## 心跳机制

### HeartBeatCheckRunner

两个定时器：

| 定时器 | 初始延迟 | 间隔 | 作用 |
|--------|---------|------|------|
| 主定时器 | 30 秒 | 30 秒 | 发送心跳 |
| 超时检查 | 30 秒 | 1 秒 | 检查响应超时 |

### 心跳发送流程

```
每 30 秒执行一次:

1. 检查 AGENT_WEBSOCKETS 是否为空
2. 清理已关闭项目的 WebSocket
3. 选择第一个活跃 WebSocket
4. 生成 UUID 作为心跳 ID
5. 发送 USER_VERSION 命令:
   {
     "id": "heartbeat-uuid",
     "command": "user_version",
     "stream": true,
     "timeStamp": 1713744000000
   }
6. 记录到 AGENT_CLIENT_MAP:
   { "heartbeat-uuid": { timestamp: now, responded: false } }
```

### 超时检测

```
每 1 秒执行一次:

1. 遍历 AGENT_CLIENT_MAP
2. 对每条记录:
   - 已响应 → 跳过
   - 未响应 且 超过 10 秒 → 标记为超时，移除请求
3. 如果累计超时 >= 2 次:
   → 触发 handRequestOnAgentFail()
   → 重启 Agent
   → 清空 AGENT_CLIENT_MAP
```

### AgentCheckTimer

独立于心跳的健康检查机制：

| 参数 | 值 |
|------|-----|
| 初始延迟 | 30 秒 |
| 主检查间隔 | 2 秒 |
| 超时检查间隔 | 1 秒 |
| 单次超时阈值 | 3000 ms |
| 失败阈值 | 3 次 |

连续 3 次失败后：
- 发送 `login_show_fresh` 到 WebView（强制刷新）
- 停止所有检查定时器

## 错误恢复

### WebSocket 错误处理

```
PluginWebsocketListener.onFailure()
    │
    ├─► 检查是否正在卸载插件 → 忽略
    ├─► 检查 Application 是否已销毁 → 忽略
    │
    ├─► 记录 OpenTelemetry Span (TracerEnum.AGENT_FAILURE)
    │
    ├─► handRequestOnAgentFail() — 清理所有挂起请求
    │
    ├─► 判断错误类型:
    │   ├─ 包含 "Connection refused" → CONNECT_REFUSED
    │   ├─ 包含 "connect timed out" → CONNECT_FAILED
    │   └─ 其他 → CONNECT_ERROR
    │
    └─► RestartableAgentProcessService.onRestartException()
        ├─► 重试计数 +1
        ├─► 计数 <= 3 → forceRestart()
        ├─► 计数 == 3 → pushAgentRefreshToWebView()
        └─► 计数 > 3 → 等待 3 秒后停止
```

### WebSocket 关闭处理

```
PluginWebsocketListener.onClosing()
    │
    ├─► 标记 isClosed = true
    │
    ├─► 忽略特定关闭码:
    │   - "Going Away"
    │   - "Normal Closure"
    │   - 另一个正常关闭码
    │
    └─► 其他关闭码:
        ├─► 记录 OpenTelemetry Span
        ├─► onReconnectException() (如果 Agent 还在运行)
        ├─► onRestartException(CLOSE_EXCEPTION)
        └─► checkAgent() (重新建立连接)
```

### handRequestOnAgentFail()

```
1. 重置 Commit Message 按钮状态
2. 重置 Prepush Review 按钮状态
3. 设置 IDE 状态为 Ready
4. 清理所有挂起请求:
   - 遍历 AGENT_REQUEST
   - 对 TALK_INTELLIGENT 类型的请求发送错误到 WebView
5. 清空全局状态:
   - AGENT_WEBSOCKETS.clear()
   - WEB_REQUEST.clear()
   - AGENT_REQUEST.clear()
```

## 重启策略总结

```
                ┌──────────┐
                │  正常运行  │
                └────┬─────┘
                     │
              心跳超时/连接错误
                     │
                ┌────▼─────┐
                │ 尝试重启  │ restartAttempts++
                └────┬─────┘
                     │
            ┌────────┼────────┐
            │        │        │
        count≤3   count==3  count>3
            │        │        │
       forceRestart  通知     等待3秒
            │       WebView   不重试
            │       刷新
            ▼
     ┌──────────┐
     │ 成功?     │
     └──┬───┬───┘
       Yes   No
        │    │
   ┌────▼┐  └──► 再次重启
   │ 正常 │      (如果 count ≤ 3)
   └─────┘
```
