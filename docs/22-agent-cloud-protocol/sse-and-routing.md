## SSE (Server-Sent Events) 流式协议

### SSE 响应格式

Agent 使用 `eventsource-parser` 库解析 SSE 流。响应格式遵循 OpenAI 兼容格式：

```
data: &#123;"choices":[&#123;"delta":&#123;"content":"回答内容片段","reasoning_content":"推理过程"&#125;&#125;]&#125;
data: &#123;"choices":[&#123;"delta":&#123;"content":"更多内容"&#125;&#125;]&#125;
data: &#123;"choices":[&#123;"delta":&#123;"content":"结束"&#125;,"finish_reason":"stop"&#125;]&#125;
data: [DONE]
```

### SSE 解析逻辑 (_getSSEData)

```javascript
// 每个 SSE data 行解析为:
&#123;
  text: choices[0].delta.content,           // 正式回答内容
  reasonText: choices[0].delta.reasoning_content,  // 推理/思考内容
  reason: choices[0].finish_reason,         // 结束原因
  error: error,                             // 错误信息
  ended: true                               // [DONE] 标记
&#125;
```

### SSE 超时处理

- 流数据到达间隔超时: 60 秒
- 超时后发送错误消息: `&#123;"error": &#123;"code": 504, "message": "网络异常，请检查网络"&#125;&#125;`

### SSE 错误码

| 码 | 含义 |
|----|------|
| 5951 | 模型负载保护触发 |
| 504 | 流超时 |

## 命令路由映射 (CHAT_APIS)

Agent 内部将 WebSocket 命令映射到不同的 API 端点和场景：

| WebSocket 命令 | API 端点 | 场景 (scene) | 流式 |
|---------------|----------|-------------|------|
| `CODE:COMPLETE` | `codeGenerate` | `COMPLETE_CODE_WITH_CONTEXT` | Yes |
| `TALK:ASK` | `talkAsk` | (动态) | Yes |
| `TALK:QUESTION_ENHANCE` | `talkAsk` | (动态) | Yes |
| `TALK:PREDICT` | `talkAskSync` | (动态) | No |
| `TALK:INTELLIGENT` | `talkAsk` | (动态) | Yes |
| `SQL:GENERATE` | `generateSql` | `GENERATE_SQL` | Yes |
| `SQL:OPTIMIZE` | `optimizeSql` | `OPTIMIZE_SQL` | Yes |
| `SQL:GENERATE_DM` | `generateSqlDM` | `GENERATE_SQL` | No |
| `SQL:OPTIMIZE_DM` | `optimizeSqlDM` | `OPTIMIZE_SQL` | No |
| `GIT:COMMIT_MESSAGE` | `generateCommitMessage` | `GENERATE_COMMIT_MESSAGE` | Yes |
| `GIT:REVIEW` | `review` | `REVIEW_CODE` | Yes |
| `TEST:MAKE_CASE` | `testCase` | — | No |
| `TEST:MAKE_CASE_JAVA` | `testCase` | — | No |
| `TEST:MAKE_CODE` | `testCode` | — | No |
| `TEST:OTHER` | `codeAssist` | `UNIT_TEST` | Yes |
| `DIALOG:REQUEST` | `inlineChat` | `INLINE_CHAT_SELECTED` | Yes |
