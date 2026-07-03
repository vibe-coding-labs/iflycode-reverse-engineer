# 14 Git 评审协议

## 功能概览

Git 评审包括：
- 代码预评审 (Code Review)
- Commit Message 生成
- Git Diff 分析

## 代码评审

### 页面初始化

```
W→J: code_review_page_ready
  └─► 收集 Git 变更列表
      └─► J→W: code_review_receiver_page_init
          &#123;
            "changes": [
              &#123; "file": "src/Main.java", "status": "MODIFIED" &#125;,
              &#123; "file": "src/Utils.java", "status": "ADDED" &#125;
            ]
          &#125;
```

### 获取 Diff

```
CommandEnum.GIT_DIFF
  &#123;
    "command": "git_diff",
    "data": &#123;
      "files": ["src/Main.java", "src/Utils.java"]
    &#125;
  &#125;
  └─► Agent 返回 Diff 内容
```

### 执行评审

```
CommandEnum.GIT_REVIEW
  &#123;
    "command": "git_review",
    "path": "/project/path",
    "content": "// Diff 内容...",
    "data": &#123;
      "diffContent": "...",
      "files": ["src/Main.java"]
    &#125;
  &#125;
  └─► Agent 流式返回评审意见
      └─► J→W: code_review_receiver_code_review
          &#123;
            "type": "code_review_receiver_code_review",
            "value": &#123;
              "comments": [
                &#123;
                  "file": "src/Main.java",
                  "line": 42,
                  "comment": "建议使用 try-with-resources",
                  "severity": "SUGGESTION"
                &#125;
              ]
            &#125;
          &#125;
```

### 获取变更结果

```
W→J: code_review_get_change_result
  └─► CommandEnum.GIT_DIFF
      └─► 获取最新 diff 结果

J→W: code_review_receiver_change_result
  └─► 推送变更结果

J→W: code_review_get_change_result_end
  └─► 标记结果传输完成
```

## Commit Message 生成

### 触发

用户在 Commit 对话框中右键选择生成 Commit Message：

```
CommitHandlerFactory.createHandler()
    │
    └─► CommitMessageSuggestionAction
        │
        ├─► 获取已暂存文件列表
        │
        ├─► CommandEnum.GIT_COMMIT_MESSAGE
        │   &#123;
        │     "id": "uuid",
        │     "command": "git_commit_message",
        │     "path": "/project/path",
        │     "data": &#123;
        │       "stagedFiles": ["src/Main.java", "src/Utils.java"],
        │       "diffContent": "// Diff 内容..."
        │     &#125;
        │   &#125;
        │
        └─► Agent 返回 Commit Message
            └─► 填充到 Commit 输入框
```

### 状态追踪

```java
// CommitMessageSuggestionAction
static ConcurrentHashMap<String, Boolean> COMMIT_MESSAGE_MAP;
static AtomicBoolean COMMIT_MESSAGE_BUTTON;
```

## Git 仓库管理

### 仓库状态

```
CommandEnum.GIT_REPOSITORY_STATUS
  └─► 检查项目是否为 Git 仓库
      └─► 返回状态信息
```

### 仓库授权

```
CommandEnum.GIT_REPO_AUTHORIZE
  &#123;
    "command": "git_repo_authorize",
    "data": &#123;
      "repoUrl": "https://github.com/...",
      "token": "git-token-xxx"
    &#125;
  &#125;
```

### 保存 Token

```
CommandEnum.GIT_SAVE_TOKEN
  &#123;
    "command": "git_save_token",
    "data": &#123;
      "platform": "github",
      "token": "xxx"
    &#125;
  &#125;
```

## 代码知识库

### 仓库状态检查

```
CommandEnum.GIT_CODE_KNOWLEDGE_REPO_STATUS
  └─► 检查代码知识库索引状态
```

### 重新索引

```
CommandEnum.GIT_CODE_KNOWLEDGE_RE_INDEX
  &#123;
    "command": "git_code_knowledge_re_index",
    "data": &#123;
      "repoUrl": "...",
      "branch": "main"
    &#125;
  &#125;
  └─► 触发知识库重新构建
```

## 错误处理

Git 操作失败时：

```
Agent 返回错误 → SocketMessageHandleListener.mf()
    │
    ├─► GitBranchChangeListener.handleGitException()
    │
    └─► CommonService.messageBus() 显示通知
```
