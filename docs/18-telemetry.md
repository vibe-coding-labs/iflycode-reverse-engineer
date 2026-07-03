# 18 OpenTelemetry APM 遥测

## 配置

| 参数 | 值 |
|------|-----|
| Exporter | OTLP HTTP (`OtlpHttpSpanExporter`) |
| Endpoint | `https://saas.api.example.com/v1/traces` |
| Compression | gzip |
| 采样率 | 100% (`traceIdRatioBased(1.0)`) |
| 传播格式 | W3C Trace Context + W3C Baggage |

### 批处理配置

| 参数 | 值 |
|------|-----|
| Export Timeout | 30 秒 |
| Schedule Delay | 5 秒 |
| Max Queue Size | 2048 |
| Max Batch Size | 512 |

### 重试策略

| 参数 | 值 |
|------|-----|
| Max Attempts | 2 |
| Initial Backoff | 1 秒 |
| Max Backoff | 5 秒 |
| Backoff Multiplier | 1.5x |

### SSL

- 使用自定义 SSL Context，信任所有证书 (X509TrustManager no-op)
- 支持系统代理 (通过反射读取代理设置)

## Resource 属性

| 属性 | 值 |
|------|-----|
| `service.name` | 服务名称 |
| `system.username` | 系统用户名 |
| `idea.version` | IDE 版本 |
| `plugin.version` | 插件版本 |

## Tracer 枚举 (TracerEnum)

| Tracer | 说明 |
|--------|------|
| `IDEA_RUN` | 插件/IDE 启动 |
| `AGENT_RUN` | Agent 进程执行 |
| `AGENT_FAILURE` | Agent 失败事件 |
| `AGENT_RESTART` | Agent 重启事件 |
| `AGENT_ERROR` | Agent 错误事件 |
| `CODE_COMPLETE_PARENT` | 代码补全父 Span |
| `CODE_COMPLETE_INLINE_CHAT_PARENT` | 内联聊天父 Span |
| `CODE_COMPLETE` | 单次代码补全 |
| `RECORD_EXCEPTION` | 异常记录 |

## Span 属性 (SpanAttrEnum)

### 系统属性

| 属性 | 说明 |
|------|------|
| `SYSTEM_USERNAME` | 系统用户名 |
| `IDEA_VERSION` | IDE 版本 |
| `PLUGIN_VERSION` | 插件版本 |
| `DISABLE_GPU` | GPU 禁用状态 |

### Agent 属性

| 属性 | 说明 |
|------|------|
| `AGENT_VERSION` | Agent 版本 |
| `AGENT_START_REASON` | 启动原因 |
| `AGENT_START_CODE` | 启动退出码 |
| `AGENT_ERROR_REASON` | 错误原因 |

### 设置属性

| 属性 | 说明 |
|------|------|
| `SETTING_TRIGGER_ON_PAUSE` | 暂停触发开关 |
| `SETTING_TRIGGER_TIME_DELAY` | 触发延迟时间 |
| `SETTING_CODE_MODE` | 代码模式 (SINGLE_LINE/INTELLIGENT) |
| `SETTING_MESSAGE_TYPE` | 发送键类型 |
| `SETTING_JAVA_TEST` | Java 测试框架 |
| `SETTING_JAVA_MOCK` | Java Mock 框架 |

### 补全属性

| 属性 | 说明 |
|------|------|
| `COMPLETE_RESULT` | 补全结果 |
| `COMPLETE_IS_STREAM` | 是否流式 |
| `COMPLETE_FIRST_DURATION` | 首次响应耗时 |
| `COMPLETE_DURATION` | 总耗时 |
| `COMPLETE_FORCE` | 是否强制触发 |
| `COMPLETE_FILE_SIZE` | 文件大小 |
| `COMPLETE_FILE_LINE` | 文件行数 |
| `COMPLETE_ACCEPT` | 接受操作 |
| `COMPLETE_REJECT` | 拒绝操作 |

### 错误属性

| 属性 | 说明 |
|------|------|
| `EXCEPTION_COMMAND` | 异常关联的命令 |
| `EXCEPTION_CODE` | 异常码 |
| `EXCEPTION_MESSAGE` | 异常消息 |

### 其他属性

| 属性 | 说明 |
|------|------|
| `USER_USERNAME` | 用户名 |
| `HTTP_SCHEME` | HTTP URL |
| `COMMAND_ID` | 命令 ID |
| `PLUGIN_UPDATE` | 插件更新状态 |

## Span 注入流程

```
sendWsMessage()
    │
    ├─► OpenTelemetryUtil.buildWithCommand() — 创建 Span
    │
    ├─► FF() — 注入属性:
    │   ├─► SpanAttr.USER_USERNAME
    │   ├─► SpanAttr.PLUGIN_UPDATE
    │   ├─► SpanAttr.SETTING_TRIGGER_ON_PAUSE
    │   ├─► SpanAttr.SETTING_TRIGGER_TIME_DELAY
    │   ├─► SpanAttr.SETTING_CODE_MODE
    │   ├─► SpanAttr.SETTING_MESSAGE_TYPE
    │   ├─► SpanAttr.SETTING_JAVA_TEST
    │   ├─► SpanAttr.SETTING_JAVA_MOCK
    │   └─► SpanAttr.COMMAND_ID (如果是非 USER_LOGIN)
    │
    ├─► W3C Trace Context 传播:
    │   GlobalOpenTelemetry.getPropagators()
    │     .getTextMapPropagator()
    │     .inject(Context.current(), headersMap, setter)
    │   → 提取 "traceparent" header
    │
    ├─► 设置 MessageDto.traceparent
    │
    └─► span.end()
```

## 用户控制

用户可通过设置启用/禁用 APM：
```java
// AICodeSettingsState
Boolean apmEnable;  // null = 默认, true/false = 手动控制
String apmUrl;      // 自定义 OTLP 端点
```
