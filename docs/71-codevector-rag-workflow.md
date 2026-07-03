# iFlyCode codeVector/RAG 语义搜索工作流分析

> 版本: iFlyCode 3.4.2-222 | 分析日期: 2026-05-13
> 来源: Agent webpack bundle (index.js) + Java DTO 反编译 (CFR) + BasicActionsBundle.properties

## 统计概览

| 指标 | 数值 |
|------|------|
| RAG API 端点总数 | 16 |
| codeVector 相关端点 | 3 |
| 知识库管理端点 | 6 |
| 搜索端点 | 5 |
| Tree-sitter 语言适配器 | 10 |
| 确认率 | 82.5% |

---

## 1. 完整流程图

```
+=============================================================================+
|                    iFlyCode codeVector/RAG 语义搜索完整工作流                  |
+=============================================================================+

[IDE Plugin - Java Side]                    [Agent Process - Node.js Side]           [Cloud RAG Server]

+---------------------------+               +-------------------------------+        +------------------+
| 1. 用户交互触发            |               | 3. WebSocket 消息路由          |        | 5. RAG 后端服务   |
|                           |               |                               |        |                  |
| WebView UI Actions:       |   WS msg      | SocketMessageHandleListener   |  HTTP  | ragserver APIs:  |
| - CODE_SEARCH_REQUEST_    |-------------->| - handleAgentAction()         |------->| - /code/search   |
|   CODESEARCH_CODE_LIST   |               |   GIT_SEARCH -> gitCodeSearch |        | - /code/getUser  |
| - CODE_SEARCH_REQUEST_    |               |   GIT_USER_REPOS -> getGitRepos|       |   Repos          |
|   REPOSITORY_LIST        |               |   GIT_LANG_LIST -> gitLangList|        | - /code/getLangs |
| - CODE_SEARCH_REQUEST_    |               |                               |        |                  |
|   LANGUAGE_LIST          |               | 4. Chat 知识增强搜索           |        | restapi APIs:    |
| - GIT_CODE_KNOWLEDGE_     |               | KnowledgeExpress.create()     |------->| - /code/search   |
|   REPO_STATUS            |               |   -> _collectInfoKnowledgeCode|        |  InRepo          |
| - GIT_CODE_KNOWLEDGE_     |               |   -> _collectInfoKnowledgeDoc |        | - /doc/search    |
|   RE_INDEX               |               |   -> _collectInfoWebSearch    |        | - /doc/knowledge |
| - GIT_AUTHORIZE          |               |   -> _collectInfoDatabase     |        |  List            |
| - GIT_SAVE_TOKEN         |               |                               |        | - /code/online   |
| - GIT_RE_INDEX           |               |                               |        |  Search          |
+---------------------------+               +-------------------------------+        +------------------+

+---------------------------+               +-------------------------------+        +------------------+
| 2. 代码补全/审查触发       |               | 6. 本地文件索引 (codeVector)    |        | 7. 向量化服务     |
|                           |               |                               |        |                  |
| CodeSearchService:        |  WS/HTTP      | RetrievalAugmented:           |  HTTP  | /rag/incbatchload|
| - sendCodeSearchRequest() |-------------->| - analysisFile()              |------->|                  |
|   -> GIT_SEARCH           |               |   -> Tree-sitter AST 解析     |        | 向量嵌入 + 存储   |
| - sendCodeRepoRequest()   |               |   -> structure 字段构造        |        |                  |
|   -> GIT_USER_REPOS       |               | - analysisFileFinished()      |        |                  |
| - PluginWebsocketClient   |               |   -> chunk(fileResult, 50)    |        |                  |
|   .sendWsMessage()        |               |   -> ragBatchLoadApi()        |        |                  |
+---------------------------+               +-------------------------------+        +------------------+

+---------------------------+               +-------------------------------+
| 8. 代码补全上下文组装       |               | 9. 相似代码检索                |
|                           |               |                               |
| getStructure(file)        |               | SimilarCodeCache:             |
|   -> Tree-sitter AST      |               | - getSimilarCodes()           |
|   -> structure 字段        |               |   -> Jaccard similarity       |
| getImportStructures(file) |               |   -> LRU Cache (max=10, 30s) |
|   -> import 文件结构       |               | getSimilarQueryCode()         |
|                           |               |   -> 提取查询代码片段          |
| slidingCut() 分配:        |               |                               |
|   prefix: 38%             |               | 最终模板组装:                  |
|   suffix: 12%             |               | COMPLETE_CODE template:       |
|   structure: 18%          |               |   $[structure]$ - AST 结构    |
|   similar: 32%            |               |   $[similarStr]$ - 相似代码   |
+---------------------------+               +-------------------------------+
```

---

## 2. 阶段一: 代码仓库导入与授权

### 2.1 授权流程

**触发**: 用户在 WebView UI 点击 "授权" (GIT_AUTHORIZE)

**Java 端 (确认)**:
- `WebViewDataTypeEnum.GIT_AUTHORIZE` -> 发送 WebSocket 消息
- `CommandEnum.GIT_REPO_AUTHORIZE` (AgentModuleEnum.INIT)

**Agent 端 (确认)**:
```
Route: "CODE_KNOWLEDGE_REPO_STATUS"
Method: getGitReposInPlatform()
```

**API 调用序列**:

1. **获取代码知识库状态**
   ```
   POST /restapi/ragserver/v1/rag/codeK/personal/init/status
   Request: &#123;
     repoUrl: "https://<normalized-git-url>.git",  // 确认: matchRepoUrlProtocal + normalizeGitUrl
     branch: "string",                              // 确认
     createUser: "userId",                          // 确认
     enterpriseId: "enterpriseId"                   // 确认
   &#125;
   ```

2. **授权个人代码知识库**
   ```
   POST /restapi/ragserver/v1/rag/codeK/personal/auth
   Request: &#123;
     repoUrl: "https://<normalized-git-url>.git",  // 确认
     branch: "string",                              // 确认
     enterpriseId: "enterpriseId",                  // 确认
     createUser: "userId",                          // 确认
     createUserName: "string",                      // 确认
     accessToken: "string",                         // 确认: Git Token
     repoType: 3                                    // 确认: 默认值 3 (推断: 1=public, 2=private, 3=personal)
   &#125;
   ```

3. **更新 Git Token**
   ```
   POST /restapi/ragserver/v1/rag/codeK/updateGitToken
   Request: &#123;
     username: "string",                            // 确认
     selfGitToken: "string",                        // 确认: 推断字段名基于 v 变量
     enterpriseId: "enterpriseId"                   // 确认
   &#125;
   ```

### 2.2 权限相关字符串 (确认 - BasicActionsBundle.properties)

| Key | 值 (Unicode 解码) | 用途 |
|-----|-------------------|------|
| `aicode.knowledge.tip` | 申请获取您当前代码库建立索引，此索引结果仅用于提升生成代码的质量，您可以在知识管理平台随时移除。 | 索引授权提示 |
| `aicode.knowledge.protocol.tip` | 仅支持HTTP/HTTPS协议代码仓地址的初始化，请在管理平台修改代码仓库的访问方式。 | 协议限制提示 |
| `aicode.knowledge.token.invalid.tip` | 无有效令牌，请在知识管理平台进行管理。 | Token 无效提示 |
| `aicode.knowledge.authorize.expired.tip` | 当前代码分支已失效，请重新授权。 | 授权过期提示 |
| `aicode.knowledge.management` | 跳转管理平台 | 管理平台入口 |
| `aicode.knowledge.authorization` | 授权 | 授权按钮 |

---

## 3. 阶段二: 文件索引与 Tree-sitter AST 解析

### 3.1 Tree-sitter 语言适配器 (确认 - Agent bundle)

| 类名 | 语言标识 | 模块 ID |
|------|----------|---------|
| `TreeSitterJava` | `java` | 5706 |
| `TreeSitterJs` | `javascript` | 36177 |
| `TreeSitterTs` | `typescript` | 947 |
| `TreeSitterTSX` | `tsx` | 92379 |
| `TreeSitterVue` | `typescript` (extract script) | 73404 |
| `TreeSitterPy` | `python` | 53065 |
| `TreeSitterC` | `c` | (inline) |
| `TreeSitterCpp` | `cpp` | 64713 |
| `TreeSitterCSharp` | `c_sharp` | 15298 |
| `TreeSitterGo` | `go` | 82340 |

**基类**: `TreeSitter` (模块 34049)
- 构造函数: `constructor(language)` - 接受语言标识
- 核心方法: `_findAllNodes(tree)` - 遍历 AST 节点
- 输出: `&#123; name, type, value, text &#125;` 结构

### 3.2 文件分析流程 (确认 - Agent bundle)

```
ACTION_OPEN_DOCUMENT 触发
    |
    v
openDocument(&#123;filepath, range, content, docChanges&#125;)
    |
    v
parseFileInfo() -> TreeSitterAST
    |
    +-- [Worker 模式]: TreeSitterWorker.analyzeFileInfo(file)
    |                   (异步, 独立 Worker 线程)
    |
    +-- [主线程模式]: analyzeFileInfo(file)
                     (同步, 直接调用 Tree-sitter)
    |
    v
返回文件信息: &#123;
    structure: "string",    // AST 结构化文本 (确认)
    methods: [],            // 方法列表 (确认)
    imports: [],            // import 列表 (确认)
    classes: [],            // 类列表 (确认)
    namespace: "string",    // 包名/命名空间 (确认)
    content: "string",      // 文件内容 (确认)
    path: "string",         // 文件路径 (确认)
    lang: "string"          // 语言 (确认)
&#125;
```

### 3.3 structure 字段构造 (确认)

**getStructureText(file, isParent)**:
```javascript
getStructureText(file, isParent) &#123;
    if (!file?.structure) return "";

    return (isParent
        ? `包名：$&#123;file.namespace || ""&#125;（父类结构）\n`
        : `包名：$&#123;file.namespace || ""&#125;\n`
    ) + `路径：$&#123;getRelativePath(wsClientId, file.path)&#125;\n`
      + `文件结构：\n$&#123;file.structure&#125;`;
&#125;
```

**structure 在代码补全中的使用**:
- `getStructure(file)` - 获取当前文件结构
- `getImportStructures(file, true, filter)` - 获取 import 文件的结构
- `resortImportsByCursorPosition4Java()` - Java 专用 import 重排序

### 3.4 similarStr 字段构造 (确认)

**相似代码检索流程**:
```
1. getSimilarQueryCode(fileInfo, cursorPosition)
   -> 提取光标位置附近的代码片段作为查询
   -> 限制: maxLines=30, maxChars=20 (确认)

2. SimilarCodeCache.getSimilarCodes(path, queryCode, fileInfo, wsClientId)
   -> LRU Cache: max=10 entries, ttl=30000ms (确认)
   -> Jaccard 相似度检查:
      - tokenize(code) -> Set&lt;word&gt;
      - jaccardSimilarity(new, cached) = |intersection| / |union|
      - 如果相似度 >= 0.8, 复用缓存结果 (确认)
      - 否则重新调用远程搜索

3. 结果注入 COMPLETE_CODE 模板的 $[similarStr]$ 变量
```

---

## 4. 阶段三: 本地仓库索引 (RetrievalAugmented)

### 4.1 RetrievalAugmented 类 (确认 - Agent bundle)

```javascript
class RetrievalAugmented &#123;
    constructor() &#123;
        this.fileTaskPool = new TaskController(1, 200, () => this.analysisFileFinished());
        this.fileResult = [];
    &#125;

    // 分析项目中的所有文件
    async analysis(dir, repoKey, root, workSpaceId) &#123;
        // 遍历目录变更 (sign: 'A'=新增, 'M'=修改)
        dirChanges.forEach(change => &#123;
            if ("AM".includes(change.sign)) &#123;
                this.fileTaskPool.addTask(new Task(change.dir, async () => &#123;
                    await this.analysisFile(change.dir, repoKey, root, workSpaceId);
                &#125;));
            &#125;
        &#125;);
    &#125;

    // 分析单个文件
    async analysisFile(dir, repoKey, root, workSpaceId) &#123;
        // 1. 读取文件内容
        // 2. Tree-sitter AST 解析
        // 3. 提取 structure, methods, imports, classes
        // 4. 结果追加到 this.fileResult
        this.fileResult = [...this.fileResult, ...parsedResults];
    &#125;

    // 所有文件分析完成后批量上传
    async analysisFileFinished() &#123;
        const chunks = chunk(this.fileResult, 50);  // 每批 50 个文件
        this.fileResult = [];

        chunks.forEach(chunk => &#123;
            this.requestPool.addTask(new Task(randomId(), async () => &#123;
                await ragService.ragBatchLoadApi(&#123;id&#125;, chunk);
            &#125;));
        &#125;);
    &#125;
&#125;
```

**关键参数**:
- `TaskController(concurrency=1, delay=200ms)` - 单线程, 200ms 间隔 (确认)
- `chunk(fileResult, 50)` - 每批 50 个文件 (确认)
- 仅处理 `sign='A'`(新增) 或 `sign='M'`(修改) 的文件 (确认)

### 4.2 批量加载 API (确认)

```
POST /api/ragserver/v1/rag/incbatchload
Content-Type: application/json

Request Body: [   // 数组, 每批最多 50 个文件
    &#123;
        path: "string",           // 文件相对路径 (推断)
        content: "string",        // 文件内容 (推断)
        structure: "string",      // Tree-sitter AST 结构 (推断)
        language: "string",       // 语言标识 (推断)
        methods: [],              // 方法列表 (推断)
        imports: [],              // import 列表 (推断)
        classes: [],              // 类列表 (推断)
        namespace: "string"       // 命名空间 (推断)
    &#125;,
    ...
]
```

### 4.3 仓库搜索就绪检查 (确认)

```
POST /restapi/ragserver/v1/rag/repoKeyDialogEnable
Request: &#123; repoKey: "string" &#125;
Response: boolean  // true=搜索就绪, false=索引未完成
```

```
POST /restapi/ragserver/v1/rag/repoKeyEnable
Request: &#123; repoKey: "string", force: boolean &#125;
Response: boolean  // 是否需要分析
```

### 4.4 语言扩展支持检查 (确认)

```
GET /restapi/ragserver/v1/rag/repoLangExtEnable?force=true
Response: string[]  // 支持的语言扩展列表
```

---

## 5. 阶段四: 语义搜索

### 5.1 codeVector 全局代码搜索 (确认)

**Java 端触发**:
- `WebViewDataTypeEnum.CODE_SEARCH_REQUEST_CODESEARCH_CODE_LIST`
- `CodeSearchService.sendCodeSearchRequest()` -> `CommandEnum.GIT_SEARCH`
- 通过 `PluginWebsocketClient.sendWsMessage()` 发送 WebSocket 消息

**Agent 端路由**:
```
POST /api/ragserver/v1/code/search
```

**请求参数 (确认 - Agent bundle)**:
```json
&#123;
    "clientName": "string",          // 客户端名称 (确认)
    "clientVersion": "string",       // 客户端版本 (确认)
    "pluginVersion": "string",       // 插件版本 (确认)
    "searchType": "string",          // 搜索类型 (确认)
    "content": "string",             // 搜索内容/查询 (确认)
    "repoIds": [],                   // 仓库 ID 列表 (确认)
    "languages": [],                 // 语言过滤 (确认)
    "currentPage": 1,                // 当前页码 (确认, 默认 1)
    "pageSize": 10,                  // 每页大小 (确认, 默认 10)
    "isSystemDefault": boolean,      // 是否系统默认 (确认)
    "requestId": "string",           // 请求 ID (确认)
    "userId": "string",              // 用户 ID (确认)
    "enterpriseId": "string"         // 企业 ID (确认)
&#125;
```

**响应 DTO (确认 - CodeSearchInfoDto + CodeSearchDto)**:
```json
&#123;
    "type": "CODE_SEARCH_GET_CODESEARCH_CODE_LIST",
    "data": &#123;
        "requestId": "string",
        "currentPage": 1,
        "pageSize": 10,
        "total": 0,
        "totalPage": 0,
        "type": "string",
        "content": [
            &#123;
                "id": "string",              // 结果 ID (确认)
                "repoUrl": "string",         // 仓库 URL (确认)
                "repoName": "string",        // 仓库名称 (确认)
                "repoType": "string",        // 仓库类型 (确认)
                "branch": "string",          // 分支 (确认)
                "filePath": "string",        // 文件路径 (确认)
                "fileName": "string",        // 文件名 (确认)
                "language": "string",        // 语言 (确认)
                "isOpen": 0,                 // 是否公开 (确认, Integer)
                "isPublic": 0,               // 是否公开 (确认, Integer)
                "startRow": 0,               // 起始行 (确认)
                "endRow": 0,                 // 结束行 (确认)
                "score": 0.0,                // 相似度分数 (确认, BigDecimal)
                "code": "string",            // 代码内容 (确认)
                "codeLength": 0,             // 代码长度 (确认)
                "codeVector": 0.0,           // 代码向量 (确认, Double - 推断: 向量相似度)
                "createTime": 0              // 创建时间 (确认, Long)
            &#125;
        ]
    &#125;
&#125;
```

### 5.2 仓库内代码搜索 (Chat 知识增强) (确认)

```
POST /restapi/ragserver/v1/code/searchInRepo
Request: &#123;
    searchContent: "string",         // 搜索内容 (确认)
    repoKey: "string",               // 仓库 Key (确认)
    userId: "string",                // 用户 ID (确认)
    enterpriseId: "string",          // 企业 ID (确认)
    searchType: 0,                   // 搜索类型 (确认: 0=localRepo, 1=codeKnowledge)
    knowledgeList: []                // 知识库列表 (确认)
&#125;
```

### 5.3 文档知识搜索 (确认)

```
POST /restapi/ragserver/v1/doc/search
Request: &#123;
    searchContent: "string",         // 搜索内容 (确认)
    knowledgeList: [],               // 知识库列表 (确认)
    userId: "string",                // 用户 ID (确认)
    enterpriseId: "string"           // 企业 ID (确认)
&#125;
```

### 5.4 在线搜索 (确认)

```
POST /api/ragserver/v1/code/onlineSearch
Request: &#123;
    searchType: "NATURE_LANGUAGE_TO_CODE",  // 搜索类型 (确认)
    content: "string"                        // 搜索内容 (确认)
&#125;
```

### 5.5 Web 文档解析 (确认)

```
POST /api/ragserver/v1/web/parseurl
Request: &#123;
    url: "string",          // URL (确认)
    oper: 1                 // 操作类型 (确认, 默认 1)
&#125;
```

---

## 6. 阶段五: 知识库管理

### 6.1 知识库列表获取 (确认)

**Java 端触发**:
- `WebViewDataTypeEnum.CHAT_GET_DOC_KNOWLEDGE_LIST` (文档知识)
- `WebViewDataTypeEnum.CHAT_GET_CODE_KNOWLEDGE_LIST` (代码知识)
- `WebViewDataTypeEnum.CHAT_RECEIVER_DOC_KNOWLEDGE_LIST` (接收文档知识)
- `WebViewDataTypeEnum.CHAT_RECEIVER_CODE_KNOWLEDGE_LIST` (接收代码知识)

**Agent 端**:
```javascript
async knowledgeList(command) &#123;
    let &#123; data &#125; = command;
    if (!data) &#123;
        data = &#123; documentKnowledge: true, codeKnowledge: true &#125;;
    &#125;

    const result = &#123; documentKnowledge: [], codeKnowledge: [] &#125;;

    if (data.documentKnowledge) &#123;
        result.documentKnowledge = await this.userService.getKnowledgeList(command, params);
    &#125;
    if (data.codeKnowledge) &#123;
        result.codeKnowledge = await this.gitService.getGitRepos(command, params);
    &#125;
    return result;
&#125;
```

**API 调用**:

1. **文档知识库列表**
   ```
   POST /restapi/ragserver/v1/doc/knowledgeList
   Request: &#123;
       userName: "string",          // 确认
       enterpriseId: "string",     // 确认
       userId: "string",           // 确认
       currentPage: 1,             // 确认
       pageSize: 100               // 确认
   &#125;
   ```

2. **代码知识库列表**
   ```
   POST /api/ragserver/v1/code/getUserRepos
   Request: &#123;
       repoName: "string",         // 确认
       currentPage: 1,             // 确认, 默认 1
       pageSize: 10,               // 确认, 默认 10
       requestId: "string",        // 确认
       userId: "string",           // 确认
       enterpriseId: "string"      // 确认
   &#125;
   ```

3. **代码知识库列表 (v2)**
   ```
   POST /restapi/ragserver/v1/rag/codeK/codeKnowledgeList
   ```

### 6.2 代码知识库重新向量化 (确认)

**Java 端触发**:
- `WebViewDataTypeEnum.GIT_RE_INDEX` -> `CommandEnum.GIT_CODE_KNOWLEDGE_RE_INDEX`

**Agent 端**:
```javascript
async reIndexCodeKnowledge(command) &#123;
    const &#123; id, isOpen, isPublic &#125; = command.data || &#123;&#125;;
    const &#123; enterpriseDto &#125; = this.client;

    await this.userService.codeKnowledgeReVectorized(command, &#123;
        id: id,
        isOpen: isOpen ?? 2,       // 默认 2 (推断: 1=open, 2=private)
        isPublic: isPublic ?? 0,    // 默认 0 (推断: 0=private, 1=public)
        enterpriseId: enterpriseDto.enterpriseId,
        createUser: enterpriseDto.userId
    &#125;);
&#125;
```

**API**:
```
POST /restapi/ragserver/v1/codeknowledge/reVectorized
Request: &#123;
    id: "string",               // 知识库 ID (确认)
    isOpen: 2,                  // 开放状态 (确认, 默认 2)
    isPublic: 0,                // 公开状态 (确认, 默认 0)
    enterpriseId: "string",     // 企业 ID (确认)
    createUser: "string"        // 创建用户 (确认)
&#125;
```

### 6.3 语言列表获取 (确认)

**Java 端触发**:
- `WebViewDataTypeEnum.CODE_SEARCH_REQUEST_CODESEARCH_LANGUAGE_LIST`
- `CommandEnum.GIT_LANG_LIST`

**API**:
```
GET /api/ragserver/v1/code/getLanguages
Response: &#123;
    type: "CODE_SEARCH_GET_CODESEARCH_LANGUAGE_LIST",
    data: &#123;
        list: ["Java", "Python", "JavaScript", ...]   // 确认
    &#125;
&#125;
```

---

## 7. 阶段六: Chat 知识增强 (KnowledgeExpress)

### 7.1 知识类型与收集 (确认)

| 知识类型 | 收集方法 | 搜索 API | 适用助手 |
|----------|----------|----------|----------|
| `localRepo` | `_collectInfoKnowledgeCode(cmd, data, enhance, 0)` | `searchInRepoApi` | iFlyDev |
| `codeKnowledge` | `_collectInfoKnowledgeCode(cmd, data, enhance, 1)` | `searchInRepoApi` | iFlyDev, iFlyOps |
| `docKnowledge` | `_collectInfoKnowledgeDoc(cmd, data, enhance)` | `searchInDocApi` | iFlyDev, iFlyOps |
| `webSearch` | `_collectInfoWebSearch(cmd, input)` | `searchInWebSearchApi` | iFlyOps |
| `database` | `_collectInfoDatabase(cmd, dbList)` | (本地 SQL 结构) | iFlyDBA |
| `currentOpenFile` | (当前打开文件) | (本地) | iFlyDev |
| `openFileList` | (打开文件列表) | (本地) | iFlyDev |

### 7.2 知识收集流程 (确认)

```
KnowledgeExpress.create(controller, dialog, knowledges)
    |
    v
[1] 分析知识类型
    - iFlyDev: 自动添加 localRepo (如果 ragSearchReady)
    - iFlyOps: 自动添加 webSearch
    |
    v
[2] 并行收集知识
    - localRepo -> _collectInfoKnowledgeCode(data, analyzeData, questionEnhanceArr, 0)
    - codeKnowledge -> _collectInfoKnowledgeCode(data, analyzeData, questionEnhanceArr, 1)
    - docKnowledge -> _collectInfoKnowledgeDoc(data, analyzeData, questionEnhanceArr)
    - webSearch -> _collectInfoWebSearch(data, naturalInput)
    - database -> _collectInfoDatabase(data, analyzeData.database)
    |
    v
[3] 组装知识上下文
    - dirFileList -> KNOWLEDGE_FILE_INFO 模板
    - knowledgeDocument -> KNOWLEDGE_FILE_INFO 模板
    - knowledgeCode -> KNOWLEDGE_FILE_INFO 模板
    - webSearch -> 直接内容
    - database -> KNOWLEDGE_DATABASE_INFO 模板
    - webDoc -> KNOWLEDGE_WEBDOC 模板
    |
    v
[4] 限制知识长度
    - knowledgeInfoLimit(knowledge, 15000)  // Chat 场景 (确认)
    - knowledgeInfoLimit(knowledge, 5000)   // iFlyDBA 场景 (确认)
```

### 7.3 知识模板 (确认)

```
KNOWLEDGE_FILE_INFO:
    $[path]$
    ```$[language]$
    $[content]$
    ```

KNOWLEDGE_DATABASE_INFO:
    【数据表结构】：
    ```sql
    $[structure]$
    ```

KNOWLEDGE_WEBDOC:
    $[title]$
    $[content]$

KNOWLEDGE_BASE:
    $[content]$
    请仔细分析上面提供的内容是否包含问题所需的信息，如果包含，请参考提供的信息回答用户问题。如果不包含，请独立分析用户问题并回答。
```

---

## 8. 阶段七: 代码补全上下文组装

### 8.1 上下文滑动窗口 (确认 - slidingCut)

代码补全请求的上下文分配比例:

| 组成部分 | 比例 | 说明 |
|----------|------|------|
| `prefix` | 38% | 光标上方代码 (reverse sliding) |
| `suffix` | 12% | 光标下方代码 |
| `structure` | 18% | 当前文件 AST 结构 |
| `imports` | (在 18% 内) | import 文件结构信息 |
| `similar` | 32% | 相似代码片段 |

### 8.2 COMPLETE_CODE 模板 (确认)

```
&lt;文件依赖结构信息&gt;
本文件类结构信息如下：
$[structure]$

$[imports]$
&lt;/文件依赖结构信息&gt;

&lt;相似代码段&gt;
$[similarStr]$
&lt;/相似代码段&gt;

&lt;光标下方的代码段&gt;
$[suffixCode]$
&lt;/光标下方的代码段&gt;

&lt;光标上方的代码段&gt;
$[prefixCode]$
&lt;/光标上方的代码段&gt;
```

### 8.3 COMPLETE_CODE_VUE 模板 (确认)

```
&lt;文件依赖结构信息&gt;
$[imports]$
&lt;/文件依赖结构信息&gt;

&lt;相似代码段&gt;
$[similarStr]$
&lt;/相似代码段&gt;

&lt;光标下方的代码段&gt;
$[suffixCode]$
&lt;/光标下方的代码段&gt;

&lt;光标上方的代码段&gt;
$[prefixCode]$
&lt;/光标上方的代码段&gt;
```

> Vue 模板不包含 `$[structure]$` 字段, 仅使用 imports

---

## 9. 完整 API 端点清单

### 9.1 ragserver 端点 (/api/ragserver/v1/*)

| 端点 | 方法 | Agent 字段 | 用途 |
|------|------|------------|------|
| `/api/ragserver/v1/code/search` | POST | `gitCodeSearch` | 全局代码语义搜索 |
| `/api/ragserver/v1/code/getUserRepos` | POST | `gitRepos` | 获取用户代码仓库 |
| `/api/ragserver/v1/code/getLanguages` | GET | `gitLangList` | 获取支持语言列表 |
| `/api/ragserver/v1/code/onlineSearch` | POST | `searchInWebSearch` | 在线代码搜索 |
| `/api/ragserver/v1/rag/incbatchload` | POST | `ragBatchLoad` | 批量文件索引上传 |
| `/api/ragserver/v1/web/parseurl` | POST | `parseWebDocument` | Web 文档解析 |

### 9.2 restapi 端点 (/restapi/ragserver/v1/*)

| 端点 | 方法 | Agent 字段 | 用途 |
|------|------|------------|------|
| `/restapi/ragserver/v1/code/searchInRepo` | POST | `searchInRepo` | 仓库内代码搜索 |
| `/restapi/ragserver/v1/doc/search` | POST | `searchInDoc` | 文档知识搜索 |
| `/restapi/ragserver/v1/doc/knowledgeList` | POST | `knowledgeList` | 文档知识库列表 |
| `/restapi/ragserver/v1/rag/repoKeyDialogEnable` | POST | `repoSearchReady` | 仓库搜索就绪检查 |
| `/restapi/ragserver/v1/rag/repoKeyEnable` | POST | `repoKeyEnable` | 仓库索引启用检查 |
| `/restapi/ragserver/v1/rag/repoLangExtEnable` | GET | `repoLangExtEnable` | 语言扩展支持检查 |
| `/restapi/ragserver/v1/rag/codeK/codeKnowledgeList` | POST | `codeKnowledgeList` | 代码知识库列表 |
| `/restapi/ragserver/v1/rag/codeK/personal/init/status` | POST | `codeKnowledgeStatus` | 个人代码知识库状态 |
| `/restapi/ragserver/v1/rag/codeK/personal/auth` | POST | `authPersonalCodeKnowledge` | 授权个人代码知识库 |
| `/restapi/ragserver/v1/codeknowledge/reVectorized` | POST | `codeKnowledgeReVectorized` | 重新向量化 |
| `/restapi/ragserver/v1/rag/codeK/updateGitToken` | POST | `codeKnowledgeUpdateGitToken` | 更新 Git Token |

---

## 10. Java 端 DTO 数据结构

### 10.1 CodeSearchDto (确认 - 反编译)

```java
public class CodeSearchDto &#123;
    private String id;              // 结果 ID
    private String repoUrl;         // 仓库 URL
    private String repoName;        // 仓库名称
    private String repoType;        // 仓库类型
    private String branch;          // 分支
    private String filePath;        // 文件路径
    private String fileName;        // 文件名
    private String language;        // 语言
    private Integer isOpen;         // 是否开放
    private Integer isPublic;       // 是否公开
    private Integer startRow;       // 起始行
    private Integer endRow;         // 结束行
    private BigDecimal score;       // 相似度分数
    private String code;            // 代码内容
    private Integer codeLength;     // 代码长度
    private Double codeVector;      // 代码向量 (向量相似度)
    private Long createTime;        // 创建时间
&#125;
```

> `codeVector` (Double) 字段是 codeVector 系统的核心标识, 存储向量相似度分数

### 10.2 CodeSearchInfoDto (确认 - 反编译)

```java
public class CodeSearchInfoDto extends PageInfo &#123;
    private List&lt;CodeSearchDto&gt; content;   // 搜索结果列表
    private String type;                   // 搜索类型
    private Integer count;                 // 结果总数
&#125;
```

### 10.3 CodeRepoInfoDto (确认 - 反编译)

```java
public class CodeRepoInfoDto extends PageInfo &#123;
    private List&lt;ReposInfoDto&gt; content;    // 仓库列表
&#125;
```

### 10.4 CodeInfoDto (确认 - 反编译)

```java
public class CodeInfoDto &#123;
    private String content;                // 代码内容
    private List&lt;RangeDTO&gt; range;          // 代码范围
    private transient List&lt;RangeDTO&gt; bodyRange;  // 方法体范围
    private String fileName;               // 文件名
    private String path;                   // 文件路径
    private String language;               // 语言
    private String allContent;             // 全部内容
&#125;

public static class RangeDTO &#123;
    private Integer line;                  // 行号
    private Integer character;             // 列号
&#125;
```

### 10.5 PageInfo (确认 - 推断自 CodeSearchInfoDto/CodeRepoInfoDto 继承)

```java
public class PageInfo &#123;
    private Integer currentPage;           // 当前页码
    private Integer pageSize;              // 每页大小
    private Integer total;                 // 总数
    private Integer totalPage;             // 总页数
&#125;
```

---

## 11. CodeSearchService 方法映射 (确认 - 反编译)

| Java 方法 | WebViewDataTypeEnum | CommandEnum | Agent 路由 | 说明 |
|-----------|---------------------|-------------|------------|------|
| `sendCodeSearchRequest()` | `CODE_SEARCH_REQUEST_CODESEARCH_CODE_LIST` | `GIT_SEARCH` | `gitCodeSearch` | 代码搜索 |
| `sendCodeRepoRequest()` | `CODE_SEARCH_REQUEST_CODESEARCH_REPOSITORY_LIST` | `GIT_USER_REPOS` | `getGitRepos` | 仓库列表 |
| (语言列表) | `CODE_SEARCH_REQUEST_CODESEARCH_LANGUAGE_LIST` | `GIT_LANG_LIST` | `gitLangList` | 语言列表 |
| `requestCopyCode()` | `CODE_SEARCH_REQUEST_COPY_CODE` | - | - | 复制代码 |
| `requestInsertCode()` | `CODE_SEARCH_REQUEST_INSERT_CODE` | - | - | 插入代码 |
| `requestCodeFile()` | `CODE_SEARCH_REQUEST_CODE_FILE` | - | - | 创建代码文件 |
| `requestOpenUrl()` | `CODE_SEARCH_REQUEST_OPEN_URL` | - | - | 打开 URL |

---

## 12. APM 监控指标 (确认)

| 指标名 | 采集方式 | 说明 |
|--------|----------|------|
| `treeSitter_analyzeFileInfo` | `Date.now() - startTime` | Tree-sitter 解析耗时 |
| `similarCode_Calc` | `Date.now() - startTime` | 相似代码计算耗时 |
| `command_ACTION:OPEN_DOCUMENT` | 计数 | 文档打开事件 |
| `command_CODE:COMPLETE` | 计数 | 代码补全事件 |

**限制数组** (确认):
```javascript
const limitArray = &#123;
    treeSitter_analyzeFileInfo: 1,        // 保留最近 1 次
    "command_ACTION:OPEN_DOCUMENT": 1,    // 保留最近 1 次
    "command_CODE:COMPLETE": 1            // 保留最近 1 次
&#125;;
```

---

## 13. 知识库完整生命周期

```
+=====================================================================+
|                    知识库生命周期                                      |
+=====================================================================+

[1] 创建/授权
    |
    |  authPersonalCodeKnowledge() -> /rag/codeK/personal/auth
    |  参数: repoUrl, branch, accessToken, repoType
    |
    v
[2] 状态检查
    |
    |  getCodeKnowledgeStatus() -> /rag/codeK/personal/init/status
    |  返回: 初始化状态 (是否已索引)
    |
    v
[3] 文件索引 (如果未索引)
    |
    |  RetrievalAugmented.analysis()
    |    -> 遍历项目文件 (仅 A/M 变更)
    |    -> Tree-sitter AST 解析每个文件
    |    -> 提取 structure, methods, imports, classes
    |    -> chunk(results, 50)
    |    -> ragBatchLoadApi() -> /rag/incbatchload
    |
    v
[4] 索引就绪检查
    |
    |  repoSearchReadyApi() -> /rag/repoKeyDialogEnable
    |  返回: boolean (是否可以搜索)
    |
    v
[5] 搜索查询
    |
    |  [全局搜索]  gitCodeSearch() -> /code/search
    |  [仓库搜索]  searchInRepoApi() -> /code/searchInRepo
    |  [文档搜索]  searchInDocApi() -> /doc/search
    |  [在线搜索]  searchInWebSearchApi() -> /code/onlineSearch
    |
    v
[6] 结果返回
    |
    |  CodeSearchDto: &#123; id, repoUrl, repoName, filePath, code, score, codeVector, ... &#125;
    |
    v
[7] 重新索引 (可选)
    |
    |  codeKnowledgeReVectorized() -> /codeknowledge/reVectorized
    |  参数: id, isOpen, isPublic
    |
    v
[8] Token 更新 (可选)
    |
    |  updateGitToken() -> /rag/codeK/updateGitToken
    |  参数: username, selfGitToken, enterpriseId
```

---

## 14. codeVector 语义搜索完整流程

```
+=====================================================================+
|              codeVector 语义搜索完整流程                               |
+=====================================================================+

[IDE Plugin]                    [Agent Process]                  [RAG Server]
    |                               |                               |
    | 1. 用户输入搜索查询             |                               |
    |------------------------------>|                               |
    |  WebViewDataType:             |                               |
    |  CODE_SEARCH_REQUEST_         |                               |
    |  CODESEARCH_CODE_LIST         |                               |
    |                               |                               |
    |                               | 2. 构造搜索请求                |
    |                               | searchType, content,          |
    |                               | repoIds, languages,           |
    |                               | currentPage, pageSize         |
    |                               |                               |
    |                               | 3. HTTP POST                  |
    |                               |------------------------------>|
    |                               | /api/ragserver/v1/code/search |
    |                               |                               |
    |                               |                               | 4. 向量相似度搜索
    |                               |                               |    query embedding
    |                               |                               |    -> cosine similarity
    |                               |                               |    -> top-K results
    |                               |                               |
    |                               | 5. 返回搜索结果                |
    |                               |<------------------------------|
    |                               | CodeSearchDto[]:              |
    |                               |   score (相似度分数)           |
    |                               |   codeVector (向量分数)        |
    |                               |   code (代码片段)              |
    |                               |                               |
    | 6. 返回 WebView 响应           |                               |
    |<------------------------------|                               |
    |  WebViewResponseType:         |                               |
    |  CODE_SEARCH_GET_             |                               |
    |  CODESEARCH_CODE_LIST         |                               |
    |                               |                               |
    | 7. 用户操作:                   |                               |
    | - 复制代码 (requestCopyCode)   |                               |
    | - 插入代码 (requestInsertCode) |                               |
    | - 创建文件 (requestCodeFile)   |                               |
    | - 打开链接 (requestOpenUrl)    |                               |
```

---

## 15. 来源标注说明

| 标注 | 含义 |
|------|------|
| (确认) | 从反编译源码或 Agent bundle 中直接确认 |
| (推断) | 基于确认信息合理推断, 未找到直接证据 |
| (确认 - Agent bundle) | 从 agent/bin/index.js webpack bundle 中提取 |
| (确认 - 反编译) | 从 CFR 反编译的 .java 文件中确认 |
| (确认 - BasicActionsBundle.properties) | 从属性文件中确认 |

---

## 16. 关键发现

1. **codeVector 是 RAG 服务的向量搜索能力**: `CodeSearchDto.codeVector` (Double) 字段存储向量相似度分数, RAG 服务器执行 embedding + cosine similarity 搜索

2. **Tree-sitter 是本地 AST 解析引擎**: 10 种语言适配器 (Java/JS/TS/TSX/Vue/Python/C/C++/C#/Go), 解析结果用于本地代码补全的 `structure` 字段和远程索引的 `ragBatchLoad`

3. **双路径搜索架构**:
   - **本地路径**: Tree-sitter AST -> structure + similarStr -> 代码补全 (无需网络)
   - **远程路径**: RAG Server embedding -> 向量搜索 -> 语义搜索结果 (需要网络)

4. **知识增强是多源融合**: Chat 对话中同时支持 localRepo + codeKnowledge + docKnowledge + webSearch + database 五种知识源

5. **批量索引是增量式的**: 仅处理 A(新增)/M(修改) 变更, 每批 50 个文件, 单线程 200ms 间隔上传

6. **相似代码缓存使用 Jaccard 相似度**: 阈值 0.8, LRU 缓存 10 条/30 秒 TTL, 避免重复远程搜索
