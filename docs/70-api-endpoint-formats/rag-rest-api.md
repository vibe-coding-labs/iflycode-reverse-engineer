## 4. RAG Server API 端点

#### POST /api/ragserver/v1/code/getUserRepos

| 属性 | 值 |
|------|-----|
| 路由键 | `gitRepos` |
| 方法 | POST |

**响应格式 (确认):**

```json
&#123;
  "currentPage": 0,
  "pageSize": 0,
  "total": 0,
  "totalPage": 0,
  "content": [
    &#123;
      "id": "string",
      "repoUrl": "string",
      "repoName": "string",
      "branch": "string",
      "repoType": "string"
    &#125;
  ]
&#125;
```

> 响应来源: `CodeRepoInfoDto` extends `PageInfo`, content=`ReposInfoDto` 确认

---

#### GET /api/ragserver/v1/code/getLanguages

| 属性 | 值 |
|------|-----|
| 路由键 | `gitLangList` |
| 方法 | GET (推断, 无method字段) |

---

#### POST /api/ragserver/v1/code/search

| 属性 | 值 |
|------|-----|
| 路由键 | `gitCodeSearch` |
| 方法 | POST |

**请求参数 (确认):**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| clientName | string | 确认 | 客户端名称 |
| clientVersion | string | 确认 | 客户端版本 |
| pluginVersion | string | 确认 | 插件版本 |
| searchType | string | 确认 | 搜索类型 |
| content | string | 确认 | 搜索内容 |
| repoIds | array | 确认 | 仓库ID列表 |
| languages | array | 确认 | 语言列表 |
| currentPage | number | 确认 | 当前页 |
| pageSize | number | 确认 | 每页大小 |
| isSystemDefault | boolean | 确认 | 是否系统默认 |
| requestId | string | 确认 | 请求ID |
| userId | string | 确认 | 用户ID |
| enterpriseId | string | 确认 | 企业ID |

**响应格式 (确认):**

```json
&#123;
  "currentPage": 0,
  "pageSize": 0,
  "total": 0,
  "totalPage": 0,
  "type": "string",
  "count": 0,
  "content": [
    &#123;
      "id": "string",
      "repoUrl": "string",
      "repoName": "string",
      "repoType": "string",
      "branch": "string",
      "filePath": "string",
      "fileName": "string",
      "language": "string",
      "isOpen": 0,
      "isPublic": 0,
      "startRow": 0,
      "endRow": 0,
      "score": 0,
      "code": "string",
      "codeLength": 0,
      "codeVector": 0.0,
      "createTime": 0
    &#125;
  ]
&#125;
```

> 响应来源: `CodeSearchInfoDto` extends `PageInfo`, content=`CodeSearchDto` 确认

---

#### POST /api/ragserver/v1/code/onlineSearch

| 属性 | 值 |
|------|-----|
| 路由键 | `searchInWebSearch` |
| 方法 | POST |

---

#### POST /api/ragserver/v1/rag/incbatchload

| 属性 | 值 |
|------|-----|
| 路由键 | `ragBatchLoad` |
| 方法 | POST |

---

#### POST /api/ragserver/v1/web/parseurl

| 属性 | 值 |
|------|-----|
| 路由键 | `parseWebDocument` |
| 方法 | POST |

---

## 5. REST API 端点

### 5.1 代码搜索 (REST)

#### POST /restapi/ragserver/v1/code/searchInRepo

| 属性 | 值 |
|------|-----|
| 路由键 | `searchInRepo` |
| 方法 | POST |

---

#### POST /restapi/ragserver/v1/doc/search

| 属性 | 值 |
|------|-----|
| 路由键 | `searchInDoc` |
| 方法 | POST |

---

#### POST /restapi/ragserver/v1/doc/knowledgeList

| 属性 | 值 |
|------|-----|
| 路由键 | `knowledgeList` |
| 方法 | POST |

---

### 5.2 代码知识库 (REST)

#### POST /restapi/ragserver/v1/rag/codeK/codeKnowledgeList

| 属性 | 值 |
|------|-----|
| 路由键 | `codeKnowledgeList` |
| 方法 | POST |

---

#### POST /restapi/ragserver/v1/rag/codeK/personal/init/status

| 属性 | 值 |
|------|-----|
| 路由键 | `codeKnowledgeStatus` |
| 方法 | POST |

---

#### POST /restapi/ragserver/v1/rag/codeK/personal/auth

| 属性 | 值 |
|------|-----|
| 路由键 | `authPersonalCodeKnowledge` |
| 方法 | POST |

**请求参数 (推断):**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| repoUrl | string | 推断 | 仓库URL |

---

#### POST /restapi/ragserver/v1/rag/codeK/updateGitToken

| 属性 | 值 |
|------|-----|
| 路由键 | `codeKnowledgeUpdateGitToken` |
| 方法 | POST |

---

#### POST /restapi/ragserver/v1/codeknowledge/reVectorized

| 属性 | 值 |
|------|-----|
| 路由键 | `codeKnowledgeReVectorized` |
| 方法 | POST |

**请求参数 (确认):**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| id | string | 确认 | 知识库ID |
| isOpen | number | 确认 | 是否开放 (默认2) |
| isPublic | number | 确认 | 是否公开 (默认0) |
| enterpriseId | string | 确认 | 企业ID |
| createUser | string | 确认 | 创建用户 |

---

### 5.3 RAG 仓库配置 (REST)

#### POST /restapi/ragserver/v1/rag/repoKeyDialogEnable

| 属性 | 值 |
|------|-----|
| 路由键 | `repoSearchReady` |
| 方法 | POST |

**请求参数 (确认):**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| repoKey | string | 确认 | 仓库Key |

---

#### POST /restapi/ragserver/v1/rag/repoKeyEnable

| 属性 | 值 |
|------|-----|
| 路由键 | `repoKeyEnable` |
| 方法 | POST |

**请求参数 (确认):**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| repoKey | string | 确认 | 仓库Key |
| force | boolean | 确认 | 是否强制 |

---

#### GET /restapi/ragserver/v1/rag/repoLangExtEnable

| 属性 | 值 |
|------|-----|
| 路由键 | `repoLangExtEnable` |
| 方法 | GET |

**Query参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| force | boolean | 确认 | 是否强制 |

---

### 5.4 批量单元测试 (REST)

#### POST /restapi/unit/v1/createUnitTask

| 属性 | 值 |
|------|-----|
| 路由键 | `batchUnitTestCreate` |
| 方法 | POST |

**Headers:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| access-token | string | 确认 | 用户token |

**请求参数:** `BatchUnitTestDto` (确认)

```json
&#123;
  "taskId": "string",
  "gitUrl": "string",
  "gitBranch": "string",
  "gitType": "string",
  "gitToken": "string",
  "unitTestDirectory": "string",
  "testFramework": "string",
  "unitTestLanguage": "string",
  "taskStatus": "string",
  "description": "string",
  "completion": "string",
  "total": "string",
  "modifyTime": "string",
  "remark": "string"
&#125;
```

> 请求来源: `BatchUnitTestDto` 确认

---

#### POST /restapi/unit/v1/queryUnitTask

| 属性 | 值 |
|------|-----|
| 路由键 | `batchUnitTestList` |
| 方法 | POST |

**Headers:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| access-token | string | 确认 | 用户token |

**请求参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| taskStatus | string | 确认 | 任务状态 |

---

#### GET /restapi/unit/v1/exportByTaskId

| 属性 | 值 |
|------|-----|
| 路由键 | `batchUnitTestDownload` |
| 方法 | GET |

**Headers:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| access-token | string | 确认 | 用户token |

**Query参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| taskId | string | 确认 | 任务ID |

---

#### POST /restapi/unit/v1/cancelUnitTask

| 属性 | 值 |
|------|-----|
| 路由键 | `batchUnitTestCancel` |
| 方法 | POST |

**Headers:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| access-token | string | 确认 | 用户token |

**请求参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| taskId | string | 确认 | 任务ID |

---

#### POST /restapi/unit/v1/deleteUnitTask

| 属性 | 值 |
|------|-----|
| 路由键 | `batchUnitTestDelete` |
| 方法 | POST |

**Headers:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| access-token | string | 确认 | 用户token |

**请求参数:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| taskId | string | 确认 | 任务ID |

---

#### POST /restapi/unit/v1/isPendingTask

| 属性 | 值 |
|------|-----|
| 路由键 | `batchUnitTestInProgress` |
| 方法 | POST |

**Headers:**

| 字段 | 类型 | 来源 | 说明 |
|------|------|------|------|
| access-token | string | 确认 | 用户token |

---
