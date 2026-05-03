# 09 智能对话协议

## 对话消息发送

### WebView → Plugin → Agent

```
用户在 WebView 输入消息
    │
    ├─► chat_send_msg (WebViewDataTypeEnum)
    │   {
    │     "type": "chat_send_msg",
    │     "value": {
    │       "inputText": "解释这段代码",
    │       "id": "msg-uuid",
    │       "sessionId": "session-uuid",
    │       "type": "talk_intelligent",
    │       "codeInfo": { ... },
    │       "intelligent": [
    │         { "type": "assistantType", "value": "iFlyMate" },
    │         { "type": "command", "value": "code_explain" }
    │       ],
    │       "relatedFiles": [...],
    │       "knowledge": [...],
    │       "sqlInfo": null,
    │       "language": "java",
    │       "errorType": false,
    │       "errorMessage": ""
    │     }
    │   }
    │
    └─► ChatService.handleAction()
        │
        ├─► 构造 MessageDto
        │   {
        │     "id": "msg-uuid",
        │     "command": "talk_intelligent",
        │     "stream": true,
        │     "sessionId": "session-uuid",
        │     "path": "/path/to/file.java",
        │     "content": "// 选中的代码...",
        │     "range": [
        │       { "line": 10, "character": 0 },
        │       { "line": 20, "character": 15 }
        │     ],
        │     "modelCode": "spark-v3.5",
        │     "permissionCode": "talk_intelligent",
        │     "data": { "inputText": "解释这段代码" },
        │     "intelligent": [...],
        │     "relatedFiles": [...],
        │     "tipinfo": { "user": "...", "platform": "IU-241" }
        │   }
        │
        └─► PluginWebsocketClient.sendWsMessage()
```

### Agent 流式响应

```
Agent 返回多条 ResponseStreamDto:

{ "id": "msg-uuid", "code": "0", "data": { "ended": false, "text": "这段" } }
{ "id": "msg-uuid", "code": "0", "data": { "ended": false, "text": "代码" } }
{ "id": "msg-uuid", "code": "0", "data": { "ended": false, "text": "实现了..." } }
...
{ "id": "msg-uuid", "code": "0", "data": { "ended": true,  "text": "" } }
```

Plugin 收到后通过 `send2Web()` 逐条推送给 WebView:

```json
{
  "type": "chat_update_conversation_list",
  "value": {
    "sessionId": "session-uuid",
    "id": "msg-uuid",
    "content": "这段代码实现了...",
    "ended": false,
    "stream": true
  }
}
```

## 助手类型 (AssistantTypeEnum)

| 助手 | 枚举值 | 说明 |
|------|--------|------|
| 通用助手 | `iFlyMate` | 通用编程助手 |
| 开发助手 | `iFlyDev` | 结合本地工程的智能问答 (企业版) |
| 测试助手 | `iFlyTest` | 测试专用 |
| 运维助手 | `iFlyOps` | 智能问答、故障分析 (企业版) |
| 产品助手 | `iFlyPm` | 需求分析、需求拆分 (企业版) |
| DBA 助手 | `iFlyDBA` | SQL 生成和优化 (团队/企业版) |

## 智能模式 (intelligent 数组)

`intelligent` 字段是一个 JsonArray，包含助手类型和命令类型：

```json
[
  { "type": "assistantType", "value": "iFlyMate" },
  { "type": "command", "value": "code_explain" }
]
```

Plugin 根据 `type=command` 的 `value` 确定实际的 `CommandEnum` 和 `permissionCode`。

## 对话管理操作

### 获取历史列表

```
W→J: chat_get_history_list
  └─► CommandEnum.TALK_LIST
      └─► Agent 返回会话列表
          └─► J→W: chat_receiver_history_list
              {
                "type": "chat_receiver_history_list",
                "value": [
                  { "sessionId": "s1", "title": "对话1", "time": "..." },
                  ...
                ]
              }
```

### 获取对话历史

```
W→J: chat_get_conversation (sessionId)
  └─► CommandEnum.TALK_HISTORY
      {
        "command": "talk_history",
        "sessionId": "session-uuid"
      }
      └─► Agent 返回消息历史
          └─► J→W: chat_get_conversation_list
```

### 新建对话

```
W→J: chat_new_chat
  └─► 清空当前 sessionId
      └─► J→W: chat_update_conversation_list (空)
```

### 删除对话

```
W→J: chat_delete_history_item (sessionId)
  └─► CommandEnum.TALK_DELETE
      └─► Agent 返回确认
          └─► W→J: chat_get_history_list (刷新列表)
```

### 重发消息

```
W→J: chat_resend (messageId)
  └─► CommandEnum.TALK_RESEND
      {
        "command": "talk_resend",
        "requestion": "original-msg-uuid"
      }
```

## 代码操作对话

当对话涉及代码操作时，`codeInfo` 字段携带上下文：

```json
{
  "type": "chat_send_msg",
  "value": {
    "inputText": "优化这段代码",
    "type": "talk_intelligent",
    "intelligent": [
      { "type": "command", "value": "code_optimize" },
      { "type": "assistantType", "value": "iFlyMate" }
    ],
    "codeInfo": {
      "content": "public void process(String input) { ... }",
      "range": [
        { "line": 10, "character": 0 },
        { "line": 25, "character": 1 }
      ],
      "fileName": "Processor.java",
      "path": "/project/src/Processor.java",
      "language": "java",
      "allContent": "// 完整文件内容..."
    }
  }
}
```

## 对话结果操作 (ChatOperationEnum)

| 操作 | 说明 |
|------|------|
| `ACTION_NEW` | 在新文件中打开 |
| `ACTION_DIFF` | 显示差异对比 |
| `ACTION_INSERT` | 插入到当前编辑器 |
| `ACTION_COPY` | 复制到剪贴板 |
| `ACTION_ACCEPT` | 接受内联注释建议 |
| `ACTION_ACCEPT_INLINE_COMMENT` | 接受行间注释 |

操作通过 `common_code_click_action` 消息发送：

```json
{
  "type": "common_code_click_action",
  "data": {
    "action": "ACTION_COPY",
    "code": "优化后的代码...",
    "filePath": "/path/to/file.java",
    "range": [...]
  }
}
```

## 知识库对话

```
W→J: chat_send_msg + knowledge 字段
  └─► CommandEnum.TALK_KNOWLEDGE
      {
        "command": "talk_knowledge",
        "knowledge": [
          { "id": "kb-1", "name": "项目知识库" }
        ]
      }
```

## 预测补全

用户停止输入时，Plugin 主动预测并发送：

```
Plugin ──► CommandEnum.TALK_PREDICT
  {
    "command": "talk_predict",
    "sessionId": "current-session",
    "content": "用户当前输入..."
  }
  └─► Agent 返回预测文本
      └─► J→W: chat_predict
```
