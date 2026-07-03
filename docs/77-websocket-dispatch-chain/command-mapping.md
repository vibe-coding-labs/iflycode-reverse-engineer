## 4. CommandEnum → Service 映射表

### 4.1 聊天模块 (CHAT)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| CHAT_SEND | ChatService | chatSend() | IDE→Agent | 是 |
| CHAT_STOP | ChatService | chatStop() | IDE→Agent | 否 |
| CHAT_DELETE | ChatService | chatDelete() | IDE→Agent | 否 |
| CHAT_LIST | ChatService | chatList() | IDE→Agent | 否 |
| CHAT_DETAIL | ChatService | chatDetail() | IDE→Agent | 否 |
| CHAT_TITLE_UPDATE | ChatService | chatTitleUpdate() | IDE→Agent | 否 |
| CHAT_FEEDBACK | ChatService | chatFeedback() | IDE→Agent | 否 |
| CHAT_HISTORY | ChatService | chatHistory() | IDE→Agent | 否 |
| CHAT_SHARE | ChatService | chatShare() | IDE→Agent | 否 |
| CHAT_EXPORT | ChatService | chatExport() | IDE→Agent | 否 |

### 4.2 行内聊天模块 (INLINE_CHAT)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| INLINE_CHAT_SEND | InlineChatCommandService | inlineSend() | IDE→Agent | 是 |
| INLINE_CHAT_STOP | InlineChatCommandService | inlineStop() | IDE→Agent | 否 |
| INLINE_CHAT_ACCEPT | InlineChatCommandService | inlineAccept() | IDE→Agent | 否 |
| INLINE_CHAT_REJECT | InlineChatCommandService | inlineReject() | IDE→Agent | 否 |
| INLINE_CHAT_DIFF | InlineChatCommandService | inlineDiff() | Agent→IDE | 否 |
| INLINE_CHAT_APPLY | InlineChatCommandService | inlineApply() | Agent→IDE | 否 |

### 4.3 代码补全模块 (CODE_COMPLETE)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| CODE_COMPLETE_REQUEST | CodeCompleteService | complete() | IDE→Agent | 是 |
| CODE_COMPLETE_ACCEPT | CodeCompleteService | accept() | IDE→Agent | 否 |
| CODE_COMPLETE_CANCEL | CodeCompleteService | cancel() | IDE→Agent | 否 |
| CODE_COMPLETE_RESULT | CodeCompleteService | handleResult() | Agent→IDE | 否 |

### 4.4 单元测试模块 (UNIT_TEST)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| UNIT_TEST_GENERATE | UnitTestService | generate() | IDE→Agent | 是 |
| UNIT_TEST_BATCH | BatchUnitTestService | batchGenerate() | IDE→Agent | 是 |
| UNIT_TEST_RUN | UnitTestService | run() | IDE→Agent | 否 |
| UNIT_TEST_RESULT | UnitTestService | handleResult() | Agent→IDE | 否 |
| UNIT_TEST_APPLY | UnitTestService | apply() | Agent→IDE | 否 |

### 4.5 代码检查模块 (CODE_CHECK)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| CODE_CHECK_REQUEST | CodeCheckService | check() | IDE→Agent | 是 |
| CODE_CHECK_RESULT | CodeCheckService | handleResult() | Agent→IDE | 否 |
| CODE_CHECK_FIX | CodeCheckService | fix() | IDE→Agent | 是 |
| CODE_CHECK_IGNORE | CodeCheckService | ignore() | IDE→Agent | 否 |

### 4.6 代码搜索模块 (CODE_SEARCH)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| CODE_SEARCH_REQUEST | CodeSearchService | search() | IDE→Agent | 否 |
| CODE_SEARCH_RESULT | CodeSearchService | handleResult() | Agent→IDE | 否 |
| CODE_SEARCH_INDEX | CodeSearchService | index() | IDE→Agent | 否 |

### 4.7 Git 审查模块 (GIT_REVIEW)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| GIT_REVIEW_START | GitReviewService | startReview() | IDE→Agent | 是 |
| GIT_REVIEW_RESULT | GitReviewService | handleResult() | Agent→IDE | 否 |
| GIT_REVIEW_APPLY | GitReviewService | apply() | Agent→IDE | 否 |

### 4.8 SQL 模块 (SQL)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| SQL_GENERATE | SqlService | generate() | IDE→Agent | 是 |
| SQL_OPTIMIZE | SqlService | optimize() | IDE→Agent | 是 |
| SQL_EXPLAIN | SqlService | explain() | IDE→Agent | 是 |
| SQL_CONVERT | SqlService | convert() | IDE→Agent | 是 |
| SQL_RESULT | SqlService | handleResult() | Agent→IDE | 否 |

### 4.9 模板模块 (TEMPLATE)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| TEMPLATE_REQUEST | TemplateRequestService | request() | IDE→Agent | 是 |
| TEMPLATE_RESULT | TemplateRequestService | handleResult() | Agent→IDE | 否 |
| TEMPLATE_LIST | TemplateRequestService | list() | IDE→Agent | 否 |
| TEMPLATE_DETAIL | TemplateRequestService | detail() | IDE→Agent | 否 |

### 4.10 用户模块 (USER)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| USER_LOGIN | UserService | login() | IDE→Agent | 否 |
| USER_LOGOUT | UserService | logout() | IDE→Agent | 否 |
| USER_INFO | UserService | getUserInfo() | IDE→Agent | 否 |
| USER_QUOTA | UserService | getQuota() | IDE→Agent | 否 |
| USER_SETTINGS | UserService | getSettings() | IDE→Agent | 否 |
| USER_UPDATE | UserService | updateSettings() | IDE→Agent | 否 |

### 4.11 系统模块 (SYSTEM)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| HEARTBEAT | (内部) | sendHeartbeat() | IDE→Agent | 否 |
| PING | PluginWebsocketClient | sendPong() | Agent→IDE | 否 |
| PONG | (内部) | 更新心跳时间 | IDE→Agent | 否 |
| ERROR | (内部) | 日志+通知 | Agent→IDE | 否 |
| CONNECTION_LOST | PluginWebsocketClient | reconnect() | Agent→IDE | 否 |
| VERSION_CHECK | (内部) | 版本校验 | 双向 | 否 |
| CONFIG_UPDATE | (内部) | 更新配置 | Agent→IDE | 否 |
| FEATURE_FLAG | (内部) | 功能开关 | Agent→IDE | 否 |

### 4.12 代码生成/解释/注释/翻译模块

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| CODE_GEN_REQUEST | ChatService | codeGen() | IDE→Agent | 是 |
| CODE_GEN_RESULT | ChatService | handleCodeGenResult() | Agent→IDE | 否 |
| CODE_GEN_APPLY | ChatService | applyCodeGen() | Agent→IDE | 否 |
| CODE_GEN_CANCEL | ChatService | cancelCodeGen() | IDE→Agent | 否 |
| CODE_EXPLAIN_REQUEST | ChatService | codeExplain() | IDE→Agent | 是 |
| CODE_EXPLAIN_RESULT | ChatService | handleExplainResult() | Agent→IDE | 否 |
| CODE_COMMENT_REQUEST | ChatService | codeComment() | IDE→Agent | 是 |
| CODE_COMMENT_RESULT | ChatService | handleCommentResult() | Agent→IDE | 否 |
| CODE_TRANSLATE_REQUEST | ChatService | codeTranslate() | IDE→Agent | 是 |
| CODE_TRANSLATE_RESULT | ChatService | handleTranslateResult() | Agent→IDE | 否 |

### 4.13 提交信息与 Diff 审查模块

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| COMMIT_MSG_GENERATE | GitReviewService | generateCommitMsg() | IDE→Agent | 是 |
| COMMIT_MSG_RESULT | GitReviewService | handleCommitMsgResult() | Agent→IDE | 否 |
| DIFF_REVIEW_REQUEST | GitReviewService | diffReview() | IDE→Agent | 是 |
| DIFF_REVIEW_RESULT | GitReviewService | handleDiffResult() | Agent→IDE | 否 |

### 4.14 上下文模块 (CONTEXT)

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| CONTEXT_FILES | ChatService | sendContextFiles() | IDE→Agent | 否 |
| CONTEXT_SYMBOLS | ChatService | sendContextSymbols() | IDE→Agent | 否 |
| CONTEXT_DIAGNOSTICS | ChatService | sendDiagnostics() | IDE→Agent | 否 |
| CONTEXT_SELECTION | ChatService | sendSelection() | IDE→Agent | 否 |

### 4.15 APM 模块

| CommandEnum | Service | Service 方法 | 方向 | 流式 |
|---|---|---|---|---|
| APM_EVENT | (内部) | reportEvent() | IDE→Agent | 否 |
| APM_PERFORMANCE | (内部) | reportPerf() | IDE→Agent | 否 |
| APM_ERROR | (内部) | reportError() | IDE→Agent | 否 |

## 5. 双向消息类型完整列表

### 5.1 IDE → Agent 消息（请求类）

| 消息类型 | 用途 | data 字段结构 |
|---|---|---|
| CHAT_SEND | 发送聊天消息 | `&#123;chatId, message, context?, files?&#125;` |
| CHAT_STOP | 停止生成 | `&#123;chatId&#125;` |
| CHAT_DELETE | 删除对话 | `&#123;chatId&#125;` |
| CHAT_LIST | 获取对话列表 | `&#123;page, size&#125;` |
| CHAT_DETAIL | 获取对话详情 | `&#123;chatId&#125;` |
| CHAT_TITLE_UPDATE | 更新对话标题 | `&#123;chatId, title&#125;` |
| CHAT_FEEDBACK | 提交反馈 | `&#123;chatId, messageId, rating, comment?&#125;` |
| INLINE_CHAT_SEND | 行内聊天请求 | `&#123;editorId, file, offset, selection?, instruction&#125;` |
| INLINE_CHAT_STOP | 停止行内聊天 | `&#123;editorId&#125;` |
| INLINE_CHAT_ACCEPT | 接受行内修改 | `&#123;editorId&#125;` |
| INLINE_CHAT_REJECT | 拒绝行内修改 | `&#123;editorId&#125;` |
| CODE_COMPLETE_REQUEST | 请求代码补全 | `&#123;file, offset, prefix, suffix, language&#125;` |
| CODE_COMPLETE_ACCEPT | 接受补全建议 | `&#123;requestId&#125;` |
| CODE_COMPLETE_CANCEL | 取消补全请求 | `&#123;requestId&#125;` |
| UNIT_TEST_GENERATE | 生成单测 | `&#123;file, class, method?, framework&#125;` |
| UNIT_TEST_BATCH | 批量生成单测 | `&#123;files[], framework&#125;` |
| UNIT_TEST_RUN | 运行单测 | `&#123;testFile, testClass?&#125;` |
| CODE_CHECK_REQUEST | 请求代码检查 | `&#123;file, content, language&#125;` |
| CODE_CHECK_FIX | 请求代码修复 | `&#123;file, issues[]&#125;` |
| CODE_CHECK_IGNORE | 忽略检查项 | `&#123;file, issueIds[]&#125;` |
| CODE_SEARCH_REQUEST | 代码搜索 | `&#123;query, type, maxResults&#125;` |
| CODE_SEARCH_INDEX | 索引代码 | `&#123;projectPath, files[]&#125;` |
| GIT_REVIEW_START | 开始 Git 审查 | `&#123;diff, baseBranch?, targetBranch?&#125;` |
| SQL_GENERATE | 生成 SQL | `&#123;description, dialect?, schema?&#125;` |
| SQL_OPTIMIZE | 优化 SQL | `&#123;sql, dialect?&#125;` |
| SQL_EXPLAIN | 解释 SQL | `&#123;sql, dialect?&#125;` |
| SQL_CONVERT | 转换 SQL | `&#123;sql, fromDialect, toDialect&#125;` |
| TEMPLATE_REQUEST | 请求模板 | `&#123;templateId, params?&#125;` |
| TEMPLATE_LIST | 获取模板列表 | `&#123;category?&#125;` |
| USER_LOGIN | 用户登录 | `&#123;token, refreshToken?&#125;` |
| USER_LOGOUT | 用户登出 | `&#123;&#125;` |
| USER_INFO | 获取用户信息 | `&#123;&#125;` |
| USER_QUOTA | 获取配额 | `&#123;&#125;` |
| USER_SETTINGS | 获取设置 | `&#123;&#125;` |
| USER_UPDATE | 更新设置 | `&#123;settings&#125;` |
| HEARTBEAT | 心跳 | `&#123;timestamp&#125;` |
| PONG | 心跳响应 | `&#123;timestamp&#125;` |
| CODE_GEN_REQUEST | 代码生成 | `&#123;instruction, file?, language?&#125;` |
| CODE_EXPLAIN_REQUEST | 代码解释 | `&#123;file, selection?, content&#125;` |
| CODE_COMMENT_REQUEST | 代码注释 | `&#123;file, selection?, content&#125;` |
| CODE_TRANSLATE_REQUEST | 代码翻译 | `&#123;content, fromLang, toLang&#125;` |
| COMMIT_MSG_GENERATE | 生成提交信息 | `&#123;diff, convention?&#125;` |
| DIFF_REVIEW_REQUEST | Diff 审查 | `&#123;diff, file?&#125;` |
| CONTEXT_FILES | 发送文件上下文 | `&#123;files[]&#125;` |
| CONTEXT_SYMBOLS | 发送符号上下文 | `&#123;symbols[]&#125;` |
| CONTEXT_DIAGNOSTICS | 发送诊断信息 | `&#123;diagnostics[]&#125;` |
| CONTEXT_SELECTION | 发送选区 | `&#123;file, startOffset, endOffset, text&#125;` |
| APM_EVENT | 上报事件 | `&#123;event, properties?&#125;` |
| APM_PERFORMANCE | 上报性能 | `&#123;metric, value, unit&#125;` |
| APM_ERROR | 上报错误 | `&#123;error, stack?, context?&#125;` |

### 5.2 Agent → IDE 消息（响应类）

| 消息类型 | 用途 | 流式 | data 字段结构 |
|---|---|---|---|
| CHAT_SEND (response) | 聊天流式响应 | 是 | ResponseStreamDto: `&#123;ended, text, data&#125;` |
| INLINE_CHAT_DIFF | 行内聊天 Diff | 否 | `&#123;editorId, diff: &#123;hunks[]&#125;&#125;` |
| INLINE_CHAT_APPLY | 行内聊天应用结果 | 否 | `&#123;editorId, success&#125;` |
| CODE_COMPLETE_RESULT | 补全结果 | 否 | `&#123;requestId, completions[]&#125;` |
| UNIT_TEST_RESULT | 单测结果 | 否 | `&#123;file, testCode, passed?, failures[]&#125;` |
| UNIT_TEST_APPLY | 单测应用结果 | 否 | `&#123;file, success&#125;` |
| CODE_CHECK_RESULT | 检查结果 | 否 | `&#123;file, issues[]&#125;` |
| CODE_SEARCH_RESULT | 搜索结果 | 否 | `&#123;results[]&#125;` |
| GIT_REVIEW_RESULT | 审查结果 | 否 | `&#123;review: &#123;comments[], suggestions[]&#125;&#125;` |
| SQL_RESULT | SQL 结果 | 否 | `&#123;sql, explanation?&#125;` |
| TEMPLATE_RESULT | 模板结果 | 否 | `&#123;templateId, content&#125;` |
| USER_INFO (response) | 用户信息 | 否 | `&#123;user: &#123;id, name, avatar, quota&#125;&#125;` |
| USER_QUOTA (response) | 配额信息 | 否 | `&#123;quota: &#123;used, total, expires&#125;&#125;` |
| PING | 心跳请求 | 否 | `&#123;timestamp&#125;` |
| ERROR | 错误通知 | 否 | `&#123;code, message, details?&#125;` |
| CONNECTION_LOST | 连接丢失 | 否 | `&#123;reason&#125;` |
| CONFIG_UPDATE | 配置更新 | 否 | `&#123;config&#125;` |
| FEATURE_FLAG | 功能开关 | 否 | `&#123;flags: &#123;key: value&#125;&#125;` |
| CODE_GEN_RESULT | 代码生成结果 | 否 | `&#123;code, language, file?&#125;` |
| CODE_GEN_APPLY | 代码生成应用 | 否 | `&#123;file, success&#125;` |
| CODE_EXPLAIN_RESULT | 解释结果 | 否 | `&#123;explanation&#125;` |
| CODE_COMMENT_RESULT | 注释结果 | 否 | `&#123;commentedCode&#125;` |
| CODE_TRANSLATE_RESULT | 翻译结果 | 否 | `&#123;translatedCode, language&#125;` |
| COMMIT_MSG_RESULT | 提交信息结果 | 否 | `&#123;message&#125;` |
| DIFF_REVIEW_RESULT | Diff 审查结果 | 否 | `&#123;review: &#123;issues[], suggestions[]&#125;&#125;` |
