# 10 代码补全协议

## 触发方式

| 触发类型 (CodeTipRequestType) | 说明 |
|------|------|
| `Automatic` | 自动触发 (暂停/打字后) |
| `Interact` | 交互触发 |
| `Forced` | 强制触发 |
| `Manual` | 手动触发 (快捷键 Alt+\ 或 Alt+C) |
| `InlineChat` | 内联聊天触发 |

## 补全模式

| 模式 (TipTypeEnum) | 说明 |
|------|------|
| `SINGLE_LINE` | 单行模式：仅生成一行代码，更快 |
| `INTELLIGENT_MODE` | 智能模式：根据上下文生成单行或多行 |

## 补全类型

| 类型 (CodeTipType) | 说明 |
|------|------|
| `Inline` | 内联补全 (光标位置) |
| `AfterLineEnd` | 行尾补全 |
| `Block` | 块级补全 |

## 补全请求流程

```
Editor 事件触发 (打字/暂停/手动)
    │
    ├─► AutoCodeGenerateListener / TipTypedHandlerDelegate
    │
    ├─► RequestTipServiceImpl.requestTip()
    │   │
    │   ├─► 构造 CodeGenerateEditorRequest:
    │   │   &#123;
    │   │     "completionType": "Inline",
    │   │     "fileLanguage": &#123; "language": "java" &#125;,
    │   │     "uri": "file:///path/to/File.java",
    │   │     "documentContent": "// 完整文件内容",
    │   │     "offset": 256,
    │   │     "lineInfo": &#123;
    │   │       "lineBeforeCaret": "    public void process(",
    │   │       "lineAfterCaret": ") &#123;",
    │   │       "currentLine": "    public void process("
    │   │     &#125;,
    │   │     "requestTimestamp": 1713744000000,
    │   │     "documentModificationSequence": 42,
    │   │     "fileName": "File.java",
    │   │     "fileNameSuffix": "java",
    │   │     "isSelected": false,
    │   │     "useTabIndents": true,
    │   │     "tabWidth": 4
    │   │   &#125;
    │   │
    │   └─► 构造 CodeTipRequestDto:
    │       &#123;
    │         "request": editorRequest,
    │         "parentSpan": span,
    │         "startTime": timestamp,
    │         "lastReplacementText": "",
    │         "firstAgentDuration": 0
    │       &#125;
    │
    ├─► PluginWebsocketClient.sendWsMessageForCode()
    │   │
    │   └─► MessageDto:
    │       &#123;
    │         "id": "uuid",
    │         "command": "code_complete",
    │         "stream": true,
    │         "path": "/path/to/File.java",
    │         "lang": "java",
    │         "content": "// 文件内容",
    │         "data": &#123; "offset": 256, ... &#125;,
    │         "tipinfo": &#123;
    │           "user": "username",
    │           "platform": "IU-241",
    │           "isShowOperateGuide": false
    │         &#125;,
    │         "docChangeCount": 3
    │       &#125;
    │
    └─► WebSocket 发送 JSON
```

## Agent 响应

### 非流式响应

```json
&#123;
  "id": "uuid",
  "code": "0",
  "command": "code_complete",
  "data": &#123;
    "text": "String input) &#123;\n    return input.toUpperCase();\n&#125;"
  &#125;
&#125;
```

### 流式响应

```json
&#123; "id": "uuid", "code": "0", "data": &#123; "ended": false, "text": "String input) &#123;" &#125; &#125;
&#123; "id": "uuid", "code": "0", "data": &#123; "ended": false, "text": "\n    return input.toUpperCase();" &#125; &#125;
&#123; "id": "uuid", "code": "0", "data": &#123; "ended": true, "text": "\n&#125;" &#125; &#125;
```

## 补全结果处理

```
Agent 响应
    │
    ├─► CodeCompleteService.handleAgentAction()
    │
    ├─► 从 AGENT_REQUEST 取回原始 MessageDto
    │
    ├─► RequestTipService.dealAgentTips() 或 dealStreamAgentTips()
    │   │
    │   ├─► 非流式: 一次性渲染到 Editor Inlay
    │   └─► 流式: 逐步更新 Inlay 内容
    │
    └─► 渲染为 Ghost Text (Inlay)
        ├─► 用户按 Tab → AcceptInlaysAction → LOG_ACCEPT
        ├─► 用户按 Ctrl+Right → AcceptWordInlaysAction → LOG_ACCEPT_WORD
        ├─► 用户按 Ctrl+Down → AcceptLineCodeInlaysAction → LOG_ACCEPT_LINE
        ├─► 用户按 Esc → DisposeInlaysAction → LOG_REJECT / LOG_REJECT_ESC
        └─► 用户继续打字 → 自动拒绝 → LOG_REJECT
```

## 用户操作导航

```
Alt+[  → CyclePreviousEditorInlays (上一个建议)
Alt+]  → CycleNextEditorInlays (下一个建议)
Tab    → AcceptInlaysAction (接受)
Ctrl+→ → AcceptWordInlaysAction (逐词)
Ctrl+↓ → AcceptLineCodeInlaysAction (逐行)
Esc    → DisposeInlaysAction (拒绝)
Alt+\ 或 Alt+C → RequestCodeGenerateAction (手动触发)
```

## 日志上报

补全后自动上报：

```json
&#123;
  "command": "log_accept",
  "data": &#123;
    "tipType": "Inline",
    "requestType": "Automatic",
    "acceptedText": "String input) &#123;",
    "fileLanguage": "java",
    "duration": 1200
  &#125;
&#125;
```

## 代码增强

通过 `USER_CAN_CODE_ENHANCE` 命令查询是否启用代码增强功能。启用后补全质量更高。

## 文件大小限制

补全有文件大小限制，超大文件不会触发自动补全。
