# 15 代码搜索协议

## 概述

代码搜索功能允许用户通过自然语言或代码语义搜索企业内部代码仓库。基于 RAG (检索增强生成) 架构。

## 搜索流程

### 获取支持语言

```
W→J: request_codesearch_language_list
  └─► CommandEnum.GIT_LANG_LIST
      &#123;
        "command": "git_lang_list"
      &#125;
      └─► Agent 返回语言列表
          └─► J→W: code_search_get_codesearch_language_list
              &#123;
                "type": "code_search_get_codesearch_language_list",
                "value": ["Java", "Python", "JavaScript", "Go", "C++"]
              &#125;
```

### 获取仓库列表

```
W→J: request_codesearch_repository_list
  └─► CommandEnum.GIT_USER_REPOS
      &#123;
        "command": "git_user_repos"
      &#125;
      └─► Agent 返回仓库列表 (分页)
          └─► J→W: code_search_get_codesearch_repository_list
              &#123;
                "type": "code_search_get_codesearch_repository_list",
                "value": &#123;
                  "content": [
                    &#123;
                      "id": "repo-1",
                      "repoUrl": "https://github.com/org/repo1",
                      "repoName": "repo1",
                      "branch": "main",
                      "repoType": "github"
                    &#125;
                  ],
                  "currentPage": 1,
                  "pageSize": 10,
                  "total": 25,
                  "totalPage": 3
                &#125;
              &#125;
```

### 执行代码搜索

```
W→J: request_codesearch_code_list
  &#123;
    "type": "request_codesearch_code_list",
    "value": &#123;
      "query": "用户认证逻辑",
      "language": "Java",
      "repoUrl": "https://github.com/org/repo1",
      "page": 1,
      "pageSize": 10
    &#125;
  &#125;
  └─► CommandEnum.GIT_SEARCH
      &#123;
        "command": "git_search",
        "data": &#123;
          "query": "用户认证逻辑",
          "language": "Java",
          "repoUrl": "..."
        &#125;
      &#125;
      └─► Agent 返回搜索结果 (分页)
          └─► J→W: code_search_get_codesearch_code_list
              &#123;
                "value": &#123;
                  "content": [
                    &#123;
                      "id": "result-1",
                      "repoUrl": "https://github.com/org/repo1",
                      "repoName": "repo1",
                      "repoType": "github",
                      "branch": "main",
                      "filePath": "src/auth/AuthService.java",
                      "fileName": "AuthService.java",
                      "language": "java",
                      "isOpen": 1,
                      "isPublic": 0,
                      "startRow": 45,
                      "endRow": 68,
                      "score": 0.95,
                      "code": "public boolean authenticate(...) &#123; ... &#125;",
                      "codeLength": 512,
                      "createTime": 1713744000000
                    &#125;
                  ],
                  "currentPage": 1,
                  "pageSize": 10,
                  "total": 15
                &#125;
              &#125;
```

### 复制代码

```
W→J: request_copy_code
  &#123;
    "value": &#123;
      "code": "public boolean authenticate(...) &#123; ... &#125;"
    &#125;
  &#125;
  └─► CommonService 复制到剪贴板
      └─► J→W: code_search_get_code_copy_success
```

### 插入代码

```
W→J: request_insert_code
  &#123;
    "value": &#123;
      "code": "public boolean authenticate(...) &#123; ... &#125;",
      "filePath": "/path/to/file.java"
    &#125;
  &#125;
  └─► 插入到当前编辑器光标位置
```

### 查看文件

```
W→J: request_code_file
  &#123;
    "value": &#123;
      "filePath": "src/auth/AuthService.java",
      "repoUrl": "https://github.com/org/repo1"
    &#125;
  &#125;
  └─► 打开完整文件内容
```

### 打开 URL

```
W→J: request_open_url
  &#123;
    "value": &#123;
      "url": "https://github.com/org/repo1/blob/main/src/auth/AuthService.java"
    &#125;
  &#125;
  └─► 在浏览器中打开
```

## RAG 服务端点

Agent 与云端 RAG 服务通信：

```
/api/ragserver/v1/code/*   — 代码搜索、在线搜索
/api/ragserver/v1/rag/*    — RAG 索引构建
/api/ragserver/v1/web/*    — URL 解析
```

## 搜索结果数据结构

### CodeSearchDto

```java
&#123;
    "id": "result-1",
    "repoUrl": "https://...",
    "repoName": "repo1",
    "repoType": "github",
    "branch": "main",
    "filePath": "src/auth/AuthService.java",
    "fileName": "AuthService.java",
    "language": "java",
    "isOpen": 1,          // 是否公开
    "isPublic": 0,        // 是否公共
    "startRow": 45,       // 代码起始行
    "endRow": 68,         // 代码结束行
    "score": 0.95,        // 相关度分数
    "code": "...",        // 代码片段
    "codeLength": 512,    // 代码长度
    "codeVector": 0.123,  // 向量化表示
    "createTime": 1713744000000
&#125;
```

### ReposInfoDto

```java
&#123;
    "id": "repo-1",
    "repoUrl": "https://github.com/org/repo1",
    "repoName": "repo1",
    "branch": "main",
    "repoType": "github"
&#125;
```
