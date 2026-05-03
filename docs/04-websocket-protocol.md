# 04 WebSocket 通信协议

## 连接参数

| 参数 | 值 |
|------|-----|
| URL | `ws://127.0.0.1:{动态端口}/ws/idea` |
| Client | OkHttp 4.12.0 |
| connectTimeout | 60 秒 |
| readTimeout | 60 秒 |
| writeTimeout | 60 秒 |

## 请求头

| Header | 说明 |
|--------|------|
| `traceparent` | W3C Trace Context (可选，用于 OpenTelemetry 链路追踪) |

## 请求消息格式 (MessageDto)

```json
{
  "id": "a1b2c3d4e5f6",
  "command": "talk_intelligent",
  "stream": true,
  "timeStamp": 1713744000000,
  "traceparent": "00-4bf92f3577b34da6a3ce929d0e0e4736-00f067aa0ba902b7-01",
  "path": "/path/to/project/src/Main.java",
  "lang": "java",
  "content": "// 文件完整内容或选中的代码",
  "sessionId": "session-uuid-xxx",
  "modelCode": "model-code-xxx",
  "permissionCode": "talk_intelligent",
  "language": "java",
  "data": { },
  "range": [
    { "line": 10, "character": 0 },
    { "line": 20, "character": 15 }
  ],
  "knowledge": null,
  "intelligent": [
    { "type": "command", "value": "code_explain" },
    { "type": "assistantType", "value": "iFlyMate" }
  ],
  "relatedFiles": [],
  "tipinfo": {
    "user": "username",
    "platform": "IU-241.1",
    "isShowOperateGuide": false
  },
  "docChangeCount": 3,
  "md5": "d41d8cd98f00b204e9800998ecf8427e"
}
```

### 字段说明

| 字段 | 类型 | 说明 |
|------|------|------|
| `id` | String | UUID，请求唯一标识 |
| `command` | String | 命令类型 (对应 CommandEnum) |
| `stream` | boolean | 是否流式响应 (默认 true) |
| `timeStamp` | long | 消息时间戳 (毫秒) |
| `traceparent` | String | W3C Trace Context 标识 |
| `path` | String | 当前文件路径 |
| `lang` | String | 文件语言 |
| `content` | String | 文件/选中代码内容 |
| `sessionId` | String | 会话 ID (对话类功能) |
| `modelCode` | String | AI 模型代码 |
| `permissionCode` | String | 权限代码 |
| `data` | Object | 命令附加数据 |
| `range` | RangeDTO[] | 代码范围 (2 元素: 起止位置) |
| `knowledge` | Object | 知识库引用 |
| `intelligent` | JsonArray | 智能模式选项 |
| `relatedFiles` | JsonArray | 相关文件列表 |
| `tipinfo` | TipInfoDto | 代码补全提示信息 |
| `docChangeCount` | Integer | 文档修改计数 |
| `md5` | String | 文件 MD5 |
| `requestion` | String | 重发关联的原始请求 ID |
| `directName` | String | Inline Chat 分类名 |
| `language` | String | 默认语言设置 |

### RangeDTO

```json
{
  "line": 10,
  "character": 5
}
```
0-based 行号和字符偏移。

## 响应消息格式

### 普通响应 (ResponseDto)

```json
{
  "id": "a1b2c3d4e5f6",
  "code": "0",
  "msg": "success",
  "command": "talk_intelligent",
  "data": { }
}
```

| 字段 | 类型 | 说明 |
|------|------|------|
| `id` | String | 对应请求 ID |
| `code` | String | 状态码 ("0" = 成功) |
| `msg` | String | 状态消息 |
| `command` | String | 响应命令类型 |
| `data` | Object | 响应数据 |

### 流式响应 (ResponseStreamDto)

```json
{
  "id": "a1b2c3d4e5f6",
  "code": "0",
  "msg": "success",
  "data": {
    "ended": false,
    "text": "生成的代码或文本片段...",
    "showKeyMapTipFlag": false
  }
}
```

| 字段 | 类型 | 说明 |
|------|------|------|
| `data.ended` | boolean | 是否结束 |
| `data.text` | String | 本段文本内容 (增量) |
| `data.showKeyMapTipFlag` | boolean | 是否显示快捷键提示 |

### 错误响应

```json
{
  "id": "uuid",
  "code": "401",
  "msg": "token expired",
  "command": "user_login"
}
```

错误时 `code` 非零，`msg` 包含错误描述。

## 消息发送流程

```
sendWsMessage(MessageDto, Project)
    │
    ├─► Ce() — 校验 project、message 不为空，id 和 command 非空
    │
    ├─► OpenTelemetryUtil.buildWithCommand() — 创建 Span
    │
    ├─► FF() — 注入 traceparent header
    │   ├─► 设置 Span 属性 (username, pluginVersion, settings)
    │   └─► W3C Trace Context 传播
    │
    ├─► 若为 USER_LOGIN 命令，附加 {count: 1}
    │
    └─► Fd() — 实际发送
        ├─► message.initModelInfo() — 根据命令设置 modelCode/permissionCode
        ├─► AGENT_REQUEST.put(id, message) — 存入请求追踪表
        ├─► new Gson().toJson(message) — 序列化为 JSON
        └─► webSocket.send(json) — 发送
```

## 连接生命周期

```
                    ┌──────────┐
                    │  创建连接  │
                    └─────┬────┘
                          │
                    ┌─────▼────┐
         ┌────────►│  已连接    │◄─────────────┐
         │         └─────┬────┘                │
         │               │                     │
         │         ┌─────▼────┐         ┌──────┴─────┐
         │         │  通信中   │         │  连接关闭    │
         │         └─────┬────┘         └──────┬─────┘
         │               │                     │
         │         ┌─────▼────┐                │
         │         │ onClosing │───────────────┤
         │         └─────┬────┘                │
         │               │                     │
         │         ┌─────▼────┐         ┌──────┴─────┐
         │         │ onFailure │────────►│  重启Agent  │
         │         └──────────┘         └──────┬─────┘
         │                                     │
         └─────────────────────────────────────┘
                     (重新建立连接)
```

## 请求追踪

```java
// 全局请求追踪表
AGENT_REQUEST:  ConcurrentSkipListMap<requestId, MessageDto>
AGENT_CLIENT_ID: ConcurrentSkipListMap<projectPath, clientId>
AGENT_WEBSOCKETS: ConcurrentSkipListMap<projectPath, WebSocket>
WEB_REQUEST:    ConcurrentSkipListMap<requestId, WebRequestDto>
WEB_REQUEST_DATA: ConcurrentSkipListMap<requestId, RequestCaseCodeDto>
```
