## 5. 完整请求/响应示例

### 5.1 Chat 对话 (TALK:ASK)

**请求**:

```json
POST https://saas.api.example.com/api/starspark/v1/agent/chat/async/ask?token=<auth-token>
Content-Type: application/json

&#123;
  "sessionId": "abc123",
  "scene": null,
  "requestId": "a1b2c3d4e5f6",
  "modelCode": "4.0Ultra",
  "token": "<auth-token>",
  "language": "java",
  "timeStamp": 1716000000000,
  "fileName": "UserService.java",
  "fileNameSuffix": "java",
  "projectName": "my-app",
  "agentVersion": "3.4.2",
  "commandType": "TALK:ASK",
  "taskName": null,
  "scene": null,
  "knowledgeBase": "codeKnowledgeBase",
  "userQuestionContent": "问题: 如何优化这个查询方法？",
  "enterpriseId": "ent001",
  "enableMultiModelSwitch": true,
  "top_k": 1,
  "temperature": 0.5,
  "mup": null,
  "messages": [
    &#123;
      "role": "system",
      "content": "你是一名人工智能编程助手，用户正在与你进行对话，请按照如下要求完成对话：\n1.你的名字是\"iFlyCode\"\n2.你的专业知识严格限于软件开发主题，对于与软件开发无关的问题，只需回答你是AI编程助手即可\n3.如果我的问题中含有\"引用代码\"，可以做为问题的关联上下文信息，如果\"引用代码\"和问题无关，请忽略\"引用代码\"，直接回复问题即可\n4.当前你的模式设定为 java模式，请用 java 为用户编写代码问题\n5.首先请一步步思考，给出针对问题的思考过程\n请首先总结我的要求"
    &#125;,
    &#123;
      "role": "user",
      "content": "[知识库上下文内容]\n\n问题: 如何优化这个查询方法？",
      "scene": null,
      "talkCode": "引用的代码:\nFrom the file: src/UserService.java\n```java\npublic List&lt;User&gt; findAll() &#123;\n    return userRepository.findAll();\n&#125;\n```",
      "language": "java"
    &#125;
  ]
&#125;
```

**SSE 响应**:

```
data: &#123;"choices":[&#123;"delta":&#123;"content":"根据","reasoning_content":"用户"&#125;&#125;]&#125;

data: &#123;"choices":[&#123;"delta":&#123;"content":"您的要求","reasoning_content":"想要优化"&#125;&#125;]&#125;

data: &#123;"choices":[&#123;"delta":&#123;"content":"，我建议...","reasoning_content":""&#125;,"finish_reason":"stop"&#125;]&#125;

data: [DONE]
```

### 5.2 代码补全 (CODE:COMPLETE)

**请求**:

```json
POST https://saas.api.example.com/api/starspark/v1/agent/code/codeComplete?token=<auth-token>
Content-Type: application/json

&#123;
  "sessionId": null,
  "scene": "COMPLETE_CODE_WITH_CONTEXT",
  "requestId": "f1e2d3c4b5a6",
  "modelCode": "4.0Ultra",
  "token": "<auth-token>",
  "language": "java",
  "timeStamp": 1716000000000,
  "fileName": "UserService.java",
  "fileNameSuffix": "java",
  "projectName": "my-app",
  "agentVersion": "3.4.2",
  "commandType": "CODE:COMPLETE",
  "taskName": "COMPLETE_CODE_WITH_CONTEXT",
  "scene": "COMPLETE_CODE_WITH_CONTEXT",
  "knowledgeBase": null,
  "userQuestionContent": null,
  "enterpriseId": "ent001",
  "enableMultiModelSwitch": true,
  "top_k": 1,
  "temperature": 1,
  "skipFilter": true,
  "stream": true,
  "messages": [
    &#123;
      "role": "user",
      "content": "&lt;文件依赖结构信息&gt;\n本文件类结构信息如下：\n包名：com.example\n路径：src/UserService.java\n文件结构：\nclass UserService &#123;\n  + findAll(): List&lt;User&gt;\n  + findById(id: Long): User\n&#125;\n\nimport文件1的类结构信息如下: \nclass UserRepository &#123;...&#125;\n&lt;/文件依赖结构信息&gt;\n\n&lt;相似代码段&gt;\n相似代码片段1如下：\npublic List&lt;User&gt; findAll() &#123; return repo.findAll(); &#125;\n&lt;/相似代码段&gt;\n\n&lt;光标下方的代码段&gt;\n    &#125;\n&#125;\n&lt;/光标下方的代码段&gt;\n\n&lt;光标上方的代码段&gt;\npublic class UserService &#123;\n    public List&lt;User&gt; find\n&lt;/光标上方的代码段&gt;"
    &#125;
  ]
&#125;
```

### 5.3 Inline Chat - 编辑 (DIRECT:EDIT)

**请求**:

```json
POST https://saas.api.example.com/api/starspark/v1/agent/chat/inline/chat?token=<auth-token>
Content-Type: application/json

&#123;
  "sessionId": null,
  "scene": "INLINE_CHAT",
  "requestId": "a1b2c3d4",
  "modelCode": "4.0Ultra",
  "token": "<auth-token>",
  "language": "java",
  "timeStamp": 1716000000000,
  "fileName": "UserService.java",
  "fileNameSuffix": "java",
  "projectName": "my-app",
  "agentVersion": "3.4.2",
  "commandType": "INLINECHAT:DIRECT",
  "taskName": "INLINE_CHAT",
  "scene": "INLINE_CHAT",
  "messages": [
    &#123;
      "role": "user",
      "content": "你是一位精通java编程的专家...\n\n【代码】：\npublic class UserService &#123;\n    private UserRepository repo;\n\n&lt;modified_code&gt;\n    public List&lt;User&gt; findAll() &#123;\n        return repo.findAll();\n    &#125;\n&lt;/modified_code&gt;\n&#125;\n\n【用户指令】：\n&#123;command：用户输入的指令&#125;\n\n【返回格式】：只返回修改后的&lt;modified_code&gt;区间的代码...",
      "language": "java"
    &#125;
  ]
&#125;
```

### 5.4 SQL 生成 (SQL:GENERATE)

**请求**:

```json
POST https://saas.api.example.com/api/starspark/v1/agent/chat/generateSql?token=<auth-token>
Content-Type: application/json

&#123;
  "sessionId": null,
  "scene": "GENERATE_SQL",
  "requestId": "sql-gen-001",
  "modelCode": "4.0Ultra",
  "token": "<auth-token>",
  "language": "sql",
  "timeStamp": 1716000000000,
  "fileName": "",
  "fileNameSuffix": "",
  "projectName": "",
  "agentVersion": "3.4.2",
  "commandType": "SQL:GENERATE",
  "taskName": "GENERATE_SQL",
  "scene": "GENERATE_SQL",
  "messages": [
    &#123;
      "role": "user",
      "content": "你是一位MySql数据库专家，根据下面【要求】和【用户输入】生成SQL语句。\n\n【表结构】：users 该表的数据量：1000；\nid INT, name VARCHAR(100), email VARCHAR(100)\n\n【用户输入】：查询所有邮箱包含gmail的用户\n\n【要求】：\n1. 精确理解用户用自然语言描述的SQL需求...\n4. 使用markdown格式返回SQL语句。\n5. 返回结果包含推理过程和说明。",
      "language": "sql",
      "latest": true,
      "latestAgent": true,
      "tableKey": "users"
    &#125;
  ]
&#125;
```

### 5.5 Git Commit Message (GIT:COMMIT_MESSAGE)

**请求**:

```json
POST https://saas.api.example.com/api/starspark/v1/agent/chat/generateCommitMessage?token=<auth-token>
Content-Type: application/json

&#123;
  "sessionId": null,
  "scene": "GENERATE_COMMIT_MESSAGE",
  "requestId": "git-commit-001",
  "modelCode": "4.0Ultra",
  "token": "<auth-token>",
  "language": "java",
  "timeStamp": 1716000000000,
  "fileName": "UserService.java",
  "fileNameSuffix": "java",
  "projectName": "my-app",
  "agentVersion": "3.4.2",
  "commandType": "GIT:COMMIT_MESSAGE",
  "taskName": "GENERATE_COMMIT_MESSAGE",
  "scene": "GENERATE_COMMIT_MESSAGE",
  "messages": [
    &#123;
      "role": "user",
      "content": "\n【变更的代码文件如下】：\n```\n文件：src/UserService.java\n@@ -10,3 +10,5 @@\n+    public User findById(Long id) &#123;\n+        return repo.findById(id);\n+    &#125;\n\n```\n\n【说明】：\n1.变更的代码中行首带有减号（-）和加号（+）的代码行是变更的代码块...\n\n返回结果参考示例：\n```\n1. 修改 [文件名2] 以修复 [问题描述] 并改进 [改进点]。\n```\n\n【要求】：\n1.按照删除、新增和修改进行分类描述；\n2.一个文件只生成一条描述信息；\n3.尽可能在描述中带上文件名...\n4.使用中文返回，返回内容包裹在一个markdown的文本块中，不要包含其他解释信息。\n"
    &#125;
  ]
&#125;
```

### 5.6 单元测试 (TEST:MAKE_CASE + TEST:MAKE_CODE)

**第一步 - 生成测试用例模板 (testCase)**:

```json
POST https://saas.api.example.com/api/starspark/v1/agent/code/generateUnitTestCaseTemplate?token=<auth-token>
Content-Type: application/json

&#123;
  "requestId": "test-case-001",
  "modelCode": "4.0Ultra",
  "token": "<auth-token>",
  "language": "java",
  "commandType": "TEST:MAKE_CASE",
  "scene": null,
  "messages": [
    &#123;
      "role": "user",
      "content": "【被测代码】\npublic int add(int a, int b) &#123; return a + b; &#125;\n【结构信息】\nclass Calculator &#123; +add(int, int): int &#125;",
      "unitTest": &#123;
        "testCaseNumber": 5,
        "testFrame": "junit5"
      &#125;
    &#125;
  ]
&#125;
```

**第二步 - 生成测试代码 (testCode)**:

```json
POST https://saas.api.example.com/api/starspark/v1/agent/code/generateUnitTest?token=<auth-token>
Content-Type: application/json

&#123;
  "requestId": "test-code-001",
  "modelCode": "4.0Ultra",
  "token": "<auth-token>",
  "language": "java",
  "commandType": "TEST:MAKE_CODE",
  "messages": [
    &#123;
      "role": "user",
      "content": "【代码上下文】\n当前被测函数所在的文件结构信息如下：\nclass Calculator &#123; +add(int, int): int &#125;\n\n【被测代码】\npublic int add(int a, int b) &#123; return a + b; &#125;\n\n【单测用例列表】\n1. testAddPositiveNumbers\n2. testAddNegativeNumbers\n3. testAddZero\n4. testAddOverflow\n5. testAddMixedSigns\n\n【单元测试代码】\nimport org.junit.jupiter.api.Test;\nimport static org.junit.jupiter.api.Assertions.*;\n\n&lt;todo&gt;"
    &#125;
  ]
&#125;
```

---

## 6. 协议安全评估

### 6.1 传输层安全

| 项目 | 状态 | 说明 |
|------|------|------|
| HTTPS | 启用 | baseURL 使用 `https://saas.api.example.com` |
| 证书验证 | 可绕过 | `ignoreHttps` 选项可禁用证书验证（`rejectUnauthorized: false`） |
| WebSocket | 本地 | Agent 与 IDE 通过 `ws://127.0.0.1:&lt;port&gt;/ws` 通信（无加密） |

### 6.2 认证与授权

| 项目 | 状态 | 说明 |
|------|------|------|
| Token 认证 | 明文 | Token 通过 HTTP header 和 URL query 参数传递 |
| Token 存储 | 本地缓存 | Token 存储在内存中的 ClientManager 对象中 |
| 权限控制 | 服务端 | `permissionCode` 和模型列表由服务端控制 |
| Token 耗尽检测 | 客户端 | `tokenExhausted` 字段标记模型是否可用 |

### 6.3 数据加密

| 项目 | 算法 | 用途 |
|------|------|------|
| 代码上报 | SM4 | `codeReport` API 使用 `encryptMode: "SM4"` 加密代码收集数据 |
| 权限信息 | SM4 | 用户权限信息本地缓存使用 SM4 加密存储 |
| 登录凭据 | RSA/SM2 | 登录时密码使用 RSA 或 SM2 公钥加密传输 |

### 6.4 Prompt 注入风险

| 风险点 | 评估 | 说明 |
|--------|------|------|
| 用户输入直接嵌入 | 中 | 用户输入通过 `$[userInput]$` 直接插入 prompt，但 system prompt 限制了回复范围 |
| 文件内容注入 | 低 | 文件内容作为上下文注入，但被明确标记为"引用代码" |
| SQL 表结构注入 | 低 | 表结构信息仅用于 SQL 生成，有长度限制 (SQL_STRUCTURE_MAX_LENGTH=20000) |
| 知识库内容注入 | 中 | 外部知识库内容直接注入 prompt，可能包含恶意指令 |

### 6.5 敏感信息暴露

| 项目 | 风险 | 说明 |
|------|------|------|
| 硬编码密钥 | 高 | RSA/SM2/SM4/AES 密钥全部硬编码在 bundle 中 |
| Token 明文传输 | 中 | Token 在 URL query 参数和 HTTP header 中明文传输 |
| 代码内容上传 | 中 | 用户代码内容作为 prompt 的一部分发送到服务端 |
| 错误消息泄露 | 低 | 错误消息使用中文通用描述，未泄露内部信息 |

### 6.6 请求超时与重试

| 场景 | 超时时间 | 处理方式 |
|------|---------|---------|
| 默认请求 | 600 秒 (6e6 ms) | 抛出 iFlyCodeError |
| 代码补全 | 120 秒 | 抛出 iFlyCodeError |
| SSE 流式 | 60 秒无数据 | 返回 504 错误 |
| 登录 | 60 秒 | 抛出 iFlyCodeError |
| 一般查询 | 10 秒 | 抛出 iFlyCodeError |

### 6.7 服务端 Prompt 模板

除了客户端硬编码的 Prompt 模板外，iFlyCode 还支持从服务端获取 Prompt 模板：

- API: `/api/starspark/v1/agent/prompt/query`
- 缓存 key: `&#123;role&#125;_&#123;scene&#125;_&#123;language&#125;`
- 缓存时间: 43200 秒 (12 小时)
- 模板语法: `$&#123;varName&#125;` (使用 `fillServerTemplate` 函数替换)

服务端模板优先级高于客户端模板，允许运营人员在不更新客户端的情况下调整 Prompt。

---

## 附录 A: Controller-Route 映射表

| Controller | Route | Command | 场景 |
|------------|-------|---------|------|
| ACTION | INIT | ACTION:INIT | 初始化 |
| ACTION | OPEN_DOCUMENT | ACTION:OPEN_DOCUMENT | 文档打开 |
| ACTION | ABORT | ACTION:ABORT | 取消请求 |
| CODE | COMPLETE | CODE:COMPLETE | 代码补全 |
| CODE | COMMENT/COMMENT_RANGE | CODE:COMMENT | 代码注释 |
| CODE | FIX | CODE:FIX | 代码修复 |
| CODE | EXPLAIN | CODE:EXPLAIN | 代码解释 |
| CODE | OPTIMIZE | CODE:OPTIMIZE | 代码优化 |
| CODE | CHECK | CODE:CHECK | 代码检查 |
| CODE | DEBUG/DEBUG_DUPLICATE | CODE:DEBUG | 代码调试 |
| CODE | INLINE_COMMENT | CODE:INLINE_COMMENT | 行内注释 |
| CODE | SPLIT | CODE:SPLIT | 函数拆分 |
| DIALOG | REQUEST | DIALOG:REQUEST | 行内对话 |
| DIALOG | ACCEPT | DIALOG:ACCEPT | 接受建议 |
| DIALOG | REJECT | DIALOG:REJECT | 拒绝建议 |
| DIALOG | ABORT | DIALOG:ABORT | 取消对话 |
| GIT | COMMIT_MESSAGE | GIT:COMMIT_MESSAGE | 生成提交信息 |
| GIT | REVIEW | GIT:REVIEW | 代码评审 |
| INLINECHAT | CATEGORY | INLINECHAT:CATEGORY | 意图分类 |
| INLINECHAT | DIRECT | INLINECHAT:DIRECT | 直接操作 |
| INLINECHAT | GET_FUNC_RANGE | INLINECHAT:GET_FUNC_RANGE | 获取函数范围 |
| SQL | GENERATE/GENERATE_TALK | SQL:GENERATE | SQL 生成 |
| SQL | OPTIMIZE/OPTIMIZE_TALK | SQL:OPTIMIZE | SQL 优化 |
| TALK | ASK/INTELLIGENT | TALK:ASK | 对话问答 |
| TALK | PREDICT | TALK:PREDICT | 问题预测 |
| TALK | QUESTION_ENHANCE | TALK:QUESTION_ENHANCE | 问题增强 |
| TEST | MAKE_CASE | TEST:MAKE_CASE | 生成测试用例 |
| TEST | MAKE_CASE_JAVA | TEST:MAKE_CASE_JAVA | Java 测试用例 |
| TEST | MAKE_CODE | TEST:MAKE_CODE | 生成测试代码 |
| TEST | OTHER | TEST:OTHER | 其他测试 |
| USER | LOGIN | USER:LOGIN | 用户登录 |
| USER | LOGOUT | USER:LOGOUT | 用户登出 |
| USER | MODEL_LIST | USER:MODEL_LIST | 获取模型列表 |
| USER | PERMISSION | USER:PERMISSION | 获取权限 |

## 附录 B: 智能助手类型

| 类型 | 枚举值 | System Prompt | 特殊行为 |
|------|--------|--------------|---------|
| 编程助手 | iFlyMate | GENERAL_ASSISTANT | 注入知识库上下文 |
| 开发助手 | iFlyDev | GENERAL_ASSISTANT | 两步 RAG：先找文件再读内容 |
| 测试助手 | iFlyTest | GENERAL_ASSISTANT | 注入知识库上下文 |
| 运维助手 | iFlyOps | GENERAL_ASSISTANT | 注入知识库上下文 |
| 产品助手 | iFlyPm | GENERAL_ASSISTANT | 注入知识库上下文 |
| 数据库助手 | iFlyDBA | GENERAL_ASSISTANT | 特殊 SQL 处理逻辑 |

## 附录 C: 数据源类型 (SourceType)

| 枚举值 | 显示名 |
|--------|--------|
| MySql | MySQL |
| PostgreSQL | PostgreSQL |
| OceanBase | OceanBase |
| TXSQL | TXSQL |
| DaMeng | 达梦 |

达梦数据库使用独立的 API 端点（`sync/generateSql`、`sync/optimizeSql`），非流式同步请求。
