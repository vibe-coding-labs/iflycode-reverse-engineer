## API 端点完整列表

### 1. 认证与用户 (LoginService)

#### loginByAccount — 账号密码登录

```
POST /api/usercenter/v1/user/common/login?clientId=&lt;clientId&gt;
Content-Type: application/json

请求体:
&#123;
  "user": "&lt;RSA加密后的用户名&gt;",
  "pwCode": "&lt;RSA加密后的密码&gt;"
&#125;

响应体:
&#123;
  "code": "0",
  "obj": &#123;
    "token": "xxx-token-xxx",
    "clientId": "uuid",
    "user": "username",
    "enterpriseDto": &#123;
      "userId": "uuid",
      "enterpriseId": "uuid",
      "enterpriseName": "企业名"
    &#125;
  &#125;
&#125;
```

RSA 公钥:
```
已脱敏 (1024-bit RSA 公钥, 已脱敏)
```

加密方式: 1024-bit RSA，分块加密 (64 bytes/block)，PKCS#5 padding。

#### validToken — Token 验证

```
POST /api/starspark/v1/chat/user/valid

&#123;
  "token": "&lt;token&gt;"
&#125;
```

#### loginStatus (checkLoginStatus) — 登录状态查询

```
GET /api/starspark/v1/user/authorizationQuery
clientId: &lt;clientId&gt;

响应: 包含 token 和用户信息
```

#### exitLogin — 退出登录

```
POST /api/starspark/v1/chat/user/logOut

&#123;
  "token": "&lt;token&gt;"
&#125;
```

#### getUrls (getSysUrl) — 获取系统 URL 配置

```
GET /api/starspark/v1/agent/authSetting/query
token: &lt;token&gt;

响应:
&#123;
  "loginUrl": "https://xxx/sso/login",
  "feedbackUrl": "https://...",
  ...
&#125;
```

#### getUserPackage — 查询用户套餐

```
POST /api/starspark/v1/user/packageQuery
token: &lt;token&gt;
```

#### checkUpdate — 插件更新检查

```
POST /api/starspark/v1/agent/authSetting/queryPluginLink?pluginType=&lt;type&gt;

响应: 包含最新版本下载链接
```

### 2. 对话 (ChatService)

#### talkAsk — 智能对话 (流式)

```
POST /api/starspark/v1/agent/chat/async/ask?token=&lt;token&gt;
Content-Type: application/json
token: &lt;token&gt;

请求体:
&#123;
  "sessionId": "uuid",
  "scene": "TALK_INTELLIGENT",
  "requestId": "uuid",
  "modelCode": "spark-v3.5",
  "enterpriseId": "ent-uuid",
  "enableMultiModelSwitch": false,
  "token": "xxx",
  "language": "java",
  "timeStamp": 1713744000000,
  "fileName": "Main.java",
  "fileNameSuffix": "java",
  "projectName": "my-project",
  "agentVersion": "3.4.2",
  "commandType": "TALK:ASK",
  "taskName": "TALK_INTELLIGENT",
  "scene": "TALK_INTELLIGENT",
  "knowledgeBase": "codeKnowledgeBase",
  "userQuestionContent": "问题内容",
  "top_k": 1,
  "temperature": 0.5,
  "mup": null,
  "messages": [
    &#123;"role": "system", "content": "系统提示..."&#125;,
    &#123;"role": "user", "content": "问题: xxx"&#125;,
    &#123;"role": "assistant", "content": "上一次回答..."&#125;,
    &#123;"role": "user", "content": "当前问题..."&#125;
  ]
&#125;

响应: SSE (Server-Sent Events) 流

data: &#123;"choices":[&#123;"delta":&#123;"content":"回答内容片段"&#125;&#125;]&#125;
data: &#123;"choices":[&#123;"delta":&#123;"content":"更多内容","reasoning_content":"思考过程"&#125;&#125;]&#125;
data: [DONE]
```

#### talkAskSync — 对话 (同步)

```
POST /api/starspark/v1/agent/chat/sync/ask
Content-Type: application/json
token: &lt;token&gt;

请求体: 同 talkAsk 但无 stream 参数
响应: JSON (非流式)
```

### 3. 代码补全 (CodeService)

#### codeGenerate — 代码补全

```
POST /api/starspark/v1/agent/code/codeComplete
Content-Type: application/json
token: &lt;token&gt;

请求体:
&#123;
  "requestId": "uuid",
  "modelCode": "spark-v3.5",
  ...
  "top_k": 1,
  "temperature": 1,
  "skipFilter": true,
  "stream": true,
  "fileName": "Main.java",
  "fileNameSuffix": "java",
  "content": "当前文件内容..."
&#125;

响应: SSE 流 (同对话格式)
```

### 4. 代码辅助 (CommonService)

#### codeAssist — 代码辅助 (补全/单测等)

```
POST /api/starspark/v1/platform/code/assist
Content-Type: application/json
token: &lt;token&gt;

&#123;
  "scene": "UNIT_TEST",
  ...
&#125;

响应: SSE 流, useModel=true
```

#### recommendations — 推荐建议

```
POST /api/starspark/v1/agent/chat/recommendations
Content-Type: application/json
token: &lt;token&gt;
```

#### serverResourceInfo — 服务器资源信息

```
POST /api/starspark/v1/agent/code/queryUnitTestQueueInfo
Content-Type: application/json
token: &lt;token&gt;
```

### 5. Git 相关 (GitService)

#### getGitRepos — 获取 Git 仓库列表

```
POST /api/ragserver/v1/code/getUserRepos
Content-Type: application/json
token: &lt;token&gt;
```

#### gitLangList — 获取支持的语言列表

```
POST /api/ragserver/v1/code/getLanguages
Content-Type: application/json
token: &lt;token&gt;
```

#### gitCodeSearch — 代码搜索

```
POST /api/ragserver/v1/code/search
Content-Type: application/json
token: &lt;token&gt;
```

### 6. SQL 相关 (SqlService)

#### generateSql — SQL 生成 (流式)

```
POST /api/starspark/v1/agent/chat/generateSql
Content-Type: application/json
token: &lt;token&gt;

场景: GENERATE_SQL
响应: SSE 流
```

#### generateSqlDM — 达梦 SQL 生成 (同步)

```
POST /api/starspark/v1/agent/chat/sync/generateSql
Content-Type: application/json
token: &lt;token&gt;

响应: JSON
```

#### optimizeSql — SQL 优化 (流式)

```
POST /api/starspark/v1/agent/chat/optimizeSql
Content-Type: application/json
token: &lt;token&gt;

场景: OPTIMIZE_SQL
响应: SSE 流
```

#### transDaMengDDL — 达梦 DDL 转换

```
POST /api/starspark/v1/agent/chat/convertDmTableDDL
Content-Type: application/json
token: &lt;token&gt;

&#123;
  "dmTableDDL": "CREATE TABLE ..."
&#125;
```

### 7. 评审与提交 (ChatService)

#### review — 代码评审 (流式)

```
POST /api/starspark/v1/agent/chat/review
Content-Type: application/json
token: &lt;token&gt;

场景: REVIEW_CODE
响应: SSE 流
```

#### generateCommitMessage — 生成提交信息 (流式)

```
POST /api/starspark/v1/agent/chat/generateCommitMessage
Content-Type: application/json
token: &lt;token&gt;

场景: GENERATE_COMMIT_MESSAGE
响应: SSE 流
```

### 8. 单元测试 (TestService)

#### testCode — 生成单元测试代码

```
POST /api/starspark/v1/agent/code/generateUnitTest
Content-Type: application/json
token: &lt;token&gt;

超时: 120秒
```

#### testCase — 生成单测模板

```
POST /api/starspark/v1/agent/code/generateUnitTestCaseTemplate
Content-Type: application/json
token: &lt;token&gt;

超时: 120秒
```

### 9. 批量单测 (TestService, access-token 认证)

批量单测使用不同的认证方式——`access-token` header 而非 `token`。

#### batchUnitTestCreate — 创建批量单测任务

```
POST /restapi/unit/v1/createUnitTask
Content-Type: application/json
access-token: &lt;username&gt;

&#123;
  "taskName": "...",
  ...
&#125;
```

#### batchUnitTestList — 查询任务列表

```
POST /restapi/unit/v1/queryUnitTask
Content-Type: application/json
access-token: &lt;username&gt;

&#123;
  "taskStatus": 1
&#125;
```

#### batchUnitTestDownload — 下载任务结果

```
GET /restapi/unit/v1/exportByTaskId?taskId=&lt;id&gt;
access-token: &lt;username&gt;

响应: application/zip 文件流
```

#### batchUnitTestCancel — 取消任务

```
POST /restapi/unit/v1/cancelUnitTask
Content-Type: application/json
access-token: &lt;username&gt;

&#123;
  "taskId": "xxx"
&#125;
```

#### batchUnitTestDelete — 删除任务

```
POST /restapi/unit/v1/deleteUnitTask
Content-Type: application/json
access-token: &lt;username&gt;

&#123;
  "taskId": "xxx"
&#125;
```

#### batchUnitTestInProgress — 检查进行中任务

```
POST /restapi/unit/v1/isPendingTask
Content-Type: application/json
access-token: &lt;username&gt;

&#123;&#125;
```

### 10. 内联聊天 (ChatService)

#### inlineChat — 内联聊天 (流式)

```
POST /api/starspark/v1/agent/chat/inline/chat
Content-Type: application/json
token: &lt;token&gt;

场景: INLINE_CHAT_SELECTED 或 INLINE_CHAT
响应: SSE 流
```

### 11. RAG/代码搜索 (RagService)

#### ragBatchLoad — RAG 批量加载

```
POST /api/ragserver/v1/rag/incbatchload
Content-Type: application/json
token: &lt;token&gt;
```

#### searchInRepo — 仓库内搜索

```
POST /restapi/ragserver/v1/code/searchInRepo
Content-Type: application/json
token: &lt;token&gt;
```

#### searchInDoc — 文档搜索

```
POST /restapi/ragserver/v1/doc/search
Content-Type: application/json
token: &lt;token&gt;
```

#### searchInWebSearch — Web 搜索

```
POST /api/ragserver/v1/code/onlineSearch
Content-Type: application/json
token: &lt;token&gt;
```

#### parseWebDocument — 解析 Web 文档

```
POST /api/ragserver/v1/web/parseurl
Content-Type: application/json
token: &lt;token&gt;
```

### 12. 用户设置与权限 (UserService)

#### getPermission — 查询权限

```
POST /api/starspark/v1/agent/permission/queryUserPermissionPackageInfo
Content-Type: application/json
token: &lt;token&gt;
```

#### getFuncModelList — 查询功能模型列表

```
POST /api/starspark/v1/agent/permission/queryUserFuncModelList
Content-Type: application/json
token: &lt;token&gt;
```

#### generalSetting — 全局设置

```
GET /api/starspark/v1/agent/pluginSetting/queryGlobalSetting
token: &lt;token&gt;
```

#### tokenConfig — Token 配置

```
GET /api/starspark/v1/agent/pluginSetting/queryTokenSetting
token: &lt;token&gt;
```

#### getChatPromptTemplate — 获取对话提示模板

```
POST /api/starspark/v1/agent/prompt/query
Content-Type: application/json
token: &lt;token&gt;

&#123;
  "role": "system",
  "scene": "TALK_INTELLIGENT",
  "language": "java"
&#125;
```

### 13. 数据收集 (ReportService)

#### chatDataReport — 对话数据上报

```
POST /api/starspark/v1/agent/collect/chatDataContent
Content-Type: application/json
token: &lt;token&gt;
```

#### codeReport — 代码接受上报

```
POST /api/starspark/v1/agent/collect/codeAccept
Content-Type: application/json
token: &lt;token&gt;
```

#### codeReject — 代码拒绝上报

```
POST /api/starspark/v1/agent/action/rejectCode
Content-Type: application/json
token: &lt;token&gt;
```

#### recordCommitInfo — 提交信息记录

```
POST /api/starspark/v1/agent/collect/commitCodeData
Content-Type: application/json
token: &lt;token&gt;
```

#### unitTestCollection — 单测统计数据

```
POST /api/starspark/v1/agent/collect/unitTestStatistics
Content-Type: application/json
token: &lt;token&gt;
```

#### unitTestCollectionGenerate — 单测生成数据

```
POST /api/starspark/v1/agent/collect/generateUnitTestData
Content-Type: application/json
token: &lt;token&gt;
```

#### unitTestCollectionCommit — 单测提交数据

```
POST /api/starspark/v1/agent/collect/commitUnitTestData
Content-Type: application/json
token: &lt;token&gt;
```

#### requestTimeAnalysis — 请求时间分析上报

```
POST /api/starspark/v1/agent/collect/uploadRequestTime
Content-Type: application/json
token: &lt;token&gt;
```

#### userAction — 用户操作记录

```
POST /api/starspark/v1/agent/action/saveUserAction
Content-Type: application/json
token: &lt;token&gt;
```

### 14. 知识库 (UserService)

#### knowledgeList — 文档知识库列表

```
POST /restapi/ragserver/v1/doc/knowledgeList
Content-Type: application/json
token: &lt;token&gt;
```

#### codeKnowledgeStatus — 代码知识库状态

```
POST /restapi/ragserver/v1/rag/codeK/personal/init/status
Content-Type: application/json
token: &lt;token&gt;
```

#### authPersonalCodeKnowledge — 授权个人代码知识库

```
POST /restapi/ragserver/v1/rag/codeK/personal/auth
Content-Type: application/json
token: &lt;token&gt;
```

#### codeKnowledgeReVectorized — 知识库重新向量化

```
POST /restapi/ragserver/v1/codeknowledge/reVectorized
Content-Type: application/json
token: &lt;token&gt;
```

#### updateGitToken — 更新 Git Token

```
POST /restapi/ragserver/v1/rag/codeK/updateGitToken
Content-Type: application/json
token: &lt;token&gt;
```

#### queryCategory — 反馈分类查询

```
POST /api/starspark/v1/agent/feedback/queryCategory
Content-Type: application/json
token: &lt;token&gt;
```

### 15. 代码增强 (UserService)

#### chatEvaluation — 对话评价

```
POST /api/starspark/v1/agent/chat/evaluate
Content-Type: application/json
token: &lt;token&gt;
```

#### chatFeedback — 对话反馈

```
POST /api/starspark/v1/agent/chat/feedback
Content-Type: application/json
token: &lt;token&gt;
```

#### getWordWriterConfig — Word 写作配置

```
GET /api/starspark/v1/agent/wordWriter/config
token: &lt;token&gt;
```
