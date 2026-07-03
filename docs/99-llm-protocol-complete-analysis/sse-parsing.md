## 4. SSE 流式响应解析

### 4.1 SSE 传输层

iFlyCode 使用标准 SSE (Server-Sent Events) 协议传输流式响应。实现基于 `eventsource-parser` 库的 `createParser` 函数。

#### SSE 解析器 (createParser)

```
状态机:
  reset() → E=true, T="", A=0, S=-1, v=undefined, I=undefined, R=""
  feed(data) → 追加到缓冲区，逐行解析

行解析 (parseEventStreamLine):
  空行 → 触发事件回调 &#123; type: "event", id, event, data &#125;
  "data: xxx" → 追加到 data 缓冲区（多行 data 用 \n 连接）
  "event: xxx" → 设置 event 类型
  "id: xxx" → 设置事件 ID
  "retry: xxx" → 设置重连间隔
```

#### SSE 消息处理 (_handleMessage)

```
1. 获取 response.body (Node.js Readable stream)
2. 创建 SSE parser
3. 监听 "readable" 事件:
   - 设置 60 秒超时（超时返回 504 错误）
   - 读取数据并 feed 到 parser
4. parser 回调:
   - data === "[DONE]" → 结束流
   - 其他 → 调用 onMessage 回调
5. 监听 "end" 事件 → 结束流
6. 监听 "error" 事件 → 结束流并记录错误
```

### 4.2 SSE 数据解析 (_getSSEData)

每条 SSE data 经过 JSON 解析后提取以下字段：

```javascript
function _getSSEData(data) &#123;
  if (data === "[DONE]") &#123;
    return &#123; ended: true &#125;;
  &#125;
  const parsed = JSON.parse(data);
  return &#123;
    text: parsed.choices?.[0]?.delta?.content || "",           // 主要回复内容
    reasonText: parsed.choices?.[0]?.delta?.reasoning_content || "",  // 推理过程（思维链）
    reason: parsed.choices?.[0]?.finish_reason,                // 结束原因
    error: parsed.error                                         // 错误信息
  &#125;;
&#125;
```

**响应格式** (兼容 OpenAI Chat Completions API):

```json
&#123;
  "choices": [
    &#123;
      "delta": &#123;
        "content": "代码片段...",
        "reasoning_content": "推理过程..."
      &#125;,
      "finish_reason": "stop"
    &#125;
  ],
  "error": &#123;
    "code": "5951",
    "message": "错误描述"
  &#125;
&#125;
```

### 4.3 SSE Handler 逻辑 (_getSSEHandler)

```
输入: command对象, &#123; dialog, isSSE, unstream, noEnded, isStreamResolve, resolve, reportDataReflow &#125;

流程:
  如果不是 SSE 模式 → 返回 null

  维护两个缓冲区:
    N[] = 文本内容缓冲区
    O[] = 推理内容缓冲区

  对每条 SSE 数据:
    1. 解析 _getSSEData(data)
    2. 如果 error.code === "5951" → 清空文本缓冲区，替换为错误消息
    3. 如果有 error → 记录到对话，发送 WebSocket 错误，resolve(&#123;&#125;)
    4. 如果 ended → 记录到对话（合并推理+内容），根据模式 resolve 结果
    5. 否则 → 追加到缓冲区，通过 WebSocket 实时推送

  结束时处理:
    - unstream=true → resolve 完整文本
    - noEnded=false → 发送 WebSocket 结束消息
    - isStreamResolve="with-merged-content" → resolve &#123; reasonContent, content &#125;
    - 其他 isStreamResolve → resolve &#123;&#125;
    - reportDataReflow=true → 记录并上报数据
```

### 4.4 特殊错误码

| 错误码 | 含义 | 处理方式 |
|--------|------|---------|
| 5951 | Token 耗尽 | 清空已生成内容，替换为错误消息 |
| 504 | 网络超时 | SSE 60 秒无数据时自动触发 |
| 401 | 未授权 | 通知客户端重新登录 |

### 4.5 非流式响应

当 API 不支持 stream 时：
- 响应作为普通 JSON 处理
- 通过 `_handleResult()` 解析
- 检查 `code` 或 `resCode` 字段，`200` 或 `"0"` 表示成功
- 错误码 `UNAUTHORIZED`/`5020`/`5001` 触发 401 重新登录

---
