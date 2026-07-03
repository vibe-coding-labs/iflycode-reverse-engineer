## 2. 模型映射表

### 2.1 模型选择机制

iFlyCode 采用**服务端模型列表 + 客户端选择**的架构：

1. **模型列表获取**: 客户端通过 `/api/starspark/v1/agent/permission/queryUserFuncModelList` 获取用户可用的模型列表
2. **模型列表结构**: 返回按 `permissionCode` 分组的模型列表，每个分组包含 `codeModelList` 数组
3. **模型选择逻辑** (`getRealModel` 方法):

```
输入: modelCode (可选), permissionCode, _language
流程:
  1. 从缓存中获取 modelList (key: token + ":modelList")
  2. 按 permissionCode + language 匹配找到对应的模型分组
  3. 过滤掉 tokenExhausted=true 的模型
  4. 如果指定了 modelCode，优先选择匹配的模型
  5. 否则选择分组中的第一个可用模型
输出: &#123; modelCode, modelName, isSelfModel &#125;
```

4. **isSelfModel 判断**: 当模型的 `modelSource === "STAR_SPARK"` 时，`isSelfModel = true`

### 2.2 模型来源分类

| modelSource | isSelfModel | 说明 |
|-------------|-------------|------|
| `STAR_SPARK` | true | 星火自有模型（默认） |
| 其他值 | false | 第三方模型（如 GPT、Claude 等） |

### 2.3 isSelfModel 的影响

当 `isSelfModel = true` 时：
- `_mergeUserMessages()` 会将 `role: "system"` 的消息拆分为 `user + assistant` 对，使用 `ASSISTANT_ANSWER` 模板作为 assistant 回复
- 这是因为星火模型不支持 system 角色，需要用 user/assistant 对模拟

当 `isSelfModel = false` 时：
- system 角色消息保持不变，直接发送给第三方模型

### 2.4 多模型切换

- `enableMultiModelSwitch` 字段：当请求中包含 `modelCode` 时为 `true`
- 用户可以在 UI 中选择不同的模型进行对话
- 模型列表通过 WebSocket 消息 `"model-list"` 推送到客户端

---

## 3. 请求构造流程

### 3.1 API 端点定义

所有 API 端点定义在 module 35606 的 `APIS` 对象中：

| API 名称 | URL | Method | Stream | Timeout | useModel |
|----------|-----|--------|--------|---------|----------|
| codeAssist | /api/starspark/v1/platform/code/assist | POST | true | - | true |
| codeGenerate | /api/starspark/v1/agent/code/codeComplete | POST | - | 120s | true |
| generateSql | /api/starspark/v1/agent/chat/generateSql | POST | true | - | true |
| optimizeSql | /api/starspark/v1/agent/chat/optimizeSql | POST | true | - | true |
| generateSqlDM | /api/starspark/v1/agent/chat/sync/generateSql | POST | - | - | true |
| optimizeSqlDM | /api/starspark/v1/agent/chat/sync/optimizeSql | POST | - | - | true |
| generateCommitMessage | /api/starspark/v1/agent/chat/generateCommitMessage | POST | true | - | true |
| review | /api/starspark/v1/agent/chat/review | POST | true | - | true |
| testCase | /api/starspark/v1/agent/code/generateUnitTestCaseTemplate | POST | - | 120s | true |
| testCode | /api/starspark/v1/agent/code/generateUnitTest | POST | - | 120s | true |
| talkAsk | /api/starspark/v1/agent/chat/async/ask | POST | true | - | true |
| talkAskSync | /api/starspark/v1/agent/chat/sync/ask | POST | - | - | true |
| inlineChat | /api/starspark/v1/agent/chat/inline/chat | POST | true | - | true |
| inlineComment | /api/starspark/v1/agent/chat/interLineCommentCode | POST | true | - | true |
| codeSplit | /api/starspark/v1/agent/chat/splitFunction | POST | true | - | true |
| codeOptimize | /api/starspark/v1/agent/chat/optimizeCode | POST | true | - | true |
| getChatPromptTemplate | /api/starspark/v1/agent/prompt/query | POST | - | - | - |
| getFuncModelList | /api/starspark/v1/agent/permission/queryUserFuncModelList | POST | - | 10s | - |
| validToken | /api/starspark/v1/chat/user/valid | POST | - | 10s | - |
| loginByAccount | /api/usercenter/v1/user/common/login | POST | - | 10s | - |
| getPermission | /api/starspark/v1/agent/permission/queryUserPermissionPackageInfo | POST | - | 10s | - |

### 3.2 命令到 API 映射 (CHAT_APIS)

| Command | API | scene | 默认参数 |
|---------|-----|-------|---------|
| CODE:COMPLETE | codeGenerate | COMPLETE_CODE_WITH_CONTEXT | top_k=1, temperature=1, skipFilter=true, stream=true |
| TALK:ASK | talkAsk | - | top_k=1, temperature=0.5, mup=null |
| TALK:QUESTION_ENHANCE | talkAsk | - | top_k=1, temperature=0.5, mup=null |
| TALK:PREDICT | talkAskSync | - | top_k=1, temperature=0.5, mup=null |
| SQL:GENERATE | generateSql | GENERATE_SQL | - |
| SQL:OPTIMIZE | optimizeSql | OPTIMIZE_SQL | - |
| SQL:GENERATE_DM | generateSqlDM | GENERATE_SQL | - |
| SQL:OPTIMIZE_DM | optimizeSqlDM | OPTIMIZE_SQL | - |
| GIT:COMMIT_MESSAGE | generateCommitMessage | GENERATE_COMMIT_MESSAGE | - |
| GIT:REVIEW | review | REVIEW_CODE | - |
| TEST:MAKE_CASE | testCase | - | - |
| TEST:MAKE_CASE_JAVA | testCase | - | - |
| TEST:MAKE_CODE | testCode | - | - |
| TEST:OTHER | codeAssist | UNIT_TEST | - |
| DIALOG:REQUEST | inlineChat | INLINE_CHAT_SELECTED | - |

### 3.3 请求体构造 (getSendData)

#### 基础数据 (getBaseData)

```json
&#123;
  "requestId": "<random-uuid>",
  "modelCode": "<selected-model-code>",
  "enterpriseId": "<enterprise-id>",
  "enableMultiModelSwitch": true,
  "token": "<auth-token>",
  "language": "<programming-language>",
  "timeStamp": 1716000000000,
  "fileName": "Example.java",
  "fileNameSuffix": "java",
  "projectName": "my-project",
  "agentVersion": "3.4.2",
  "commandType": "TALK:ASK",
  "taskName": "&lt;scene&gt;",
  "scene": "&lt;scene&gt;",
  "knowledgeBase": "docKnowledgeBase|codeKnowledgeBase",
  "userQuestionContent": "<user-input>"
&#125;
```

`knowledgeBase` 字段根据知识库类型自动判断：
- `docKnowledgeBase`: 仅文档知识库有内容
- `codeKnowledgeBase`: 代码知识库或混合知识库有内容

#### 完整请求体 (getSendData 合并后)

```json
&#123;
  "sessionId": "<session-id>",
  "scene": "<scene-from-CHAT_APIS>",
  "requestId": "...",
  "modelCode": "...",
  "token": "...",
  "language": "...",
  "timeStamp": ...,
  "fileName": "...",
  "fileNameSuffix": "...",
  "projectName": "...",
  "agentVersion": "3.4.2",
  "commandType": "...",
  "taskName": "...",
  "knowledgeBase": "...",
  "userQuestionContent": "...",
  "enterpriseId": "...",
  "enableMultiModelSwitch": true,
  "top_k": 1,
  "temperature": 0.5,
  "mup": null,
  "messages": [
    &#123; "role": "system", "content": "..." &#125;,
    &#123; "role": "user", "content": "...", "scene": "...", "talkCode": "...", "language": "..." &#125;,
    &#123; "role": "assistant", "content": "..." &#125;
  ]
&#125;
```

#### 消息组装逻辑

1. **助手前置消息** (`_assistantMessages`): 由 `getAssistantMessages()` 生成，包含 system prompt 和知识库上下文，插入到消息列表最前面
2. **用户消息**: 包含 `talkCode`（引用代码）和 `scene` 字段
3. **历史对话**: 当 `sessionId` 存在且 `_sendDialog=true` 时，从本地数据库加载历史消息
4. **消息合并** (`_mergeUserMessages`):
   - 连续的 user 消息合并为一条（用换行符连接）
   - system 消息在 `isSelfModel=true` 时转为 user+assistant 对
5. **SQL 消息处理** (`_handleSqlMessages`): 特殊处理表结构信息，只在最后一条包含 tableList 的消息中注入表结构
6. **重发处理**: 当 `_isResend=true` 时，覆盖参数为 `top_k=5, temperature=0.5`

### 3.4 URL 构造

```
baseURL = "https://saas.api.example.com"  (从 config.json 读取)
完整 URL = baseURL + api.url + "?token=" + authToken
```

`_getUrl()` 方法使用 `URL` 对象拼接 query 参数。

### 3.5 请求头

```javascript
&#123;
  "Content-Type": "application/json",
  "token": "<auth-token>"
&#125;
```

此外，OpenTelemetry 追踪信息通过 `B.default.inject(requestId, headers)` 注入（W3C Trace Context 格式）。

### 3.6 Token 预算配置 (DEFAULT_TOKEN_CONFIG)

| Command | token | discount | 有效 token 数 | 约等于字符数 |
|---------|-------|----------|--------------|-------------|
| default | 8000 | 80% | 6400 | ~19200 |
| CODE:COMPLETE | 8000 | 67% | 5360 | ~16080 |
| CODE:FIX | 8000 | 70% | 5600 | ~16800 |
| CODE:COMMENT | 8000 | 100% | 8000 | ~24000 |
| CODE:TEST_CASE | 8000 | 100% | 8000 | ~24000 |
| CODE:TEST_CODE | 8000 | 100% | 8000 | ~24000 |
| CODE:TEST | 8000 | 80% | 6400 | ~19200 |
| CODE:CHECK | 8000 | 100% | 8000 | ~24000 |
| CODE:DEBUG | 8000 | 80% | 6400 | ~19200 |
| CODE:DEBUG_DUPLICATE | 8000 | 80% | 6400 | ~19200 |
| CODE:INLINE_COMMENT | 8000 | 62.5% | 5000 | ~15000 |
| CODE:OPTIMIZE | 8000 | 67% | 5360 | ~16080 |
| GIT:COMMIT_MESSAGE | 8000 | 100% | 8000 | ~24000 |
| GIT:REVIEW | 8000 | 100% | 8000 | ~24000 |
| DIALOG:TALK_ROUND | 8000 | 80% | 6400 | ~19200 |
| DIALOG:TALK_TOTAL | 8000 | 80% | 6400 | ~19200 |

计算公式: `effectiveToken = floor(token * discount / 100)`, `maxChars = floor(effectiveToken * 4 * 0.75)`

### 3.7 测试框架库映射

| 语言 | 框架 | include 语句 |
|------|------|-------------|
| C++ | gtest | `#include "gtest/gtest.h"` |
| C++ | gmock | `#include "gmock/gmock.h"` |
| Python | unittest | `import unittest` |
| Python | unittest.mock | `from unittest.mock import patch` |
| Python | pytest | `import pytest` |
| Python | pytest-mock | (空字符串) |

---
