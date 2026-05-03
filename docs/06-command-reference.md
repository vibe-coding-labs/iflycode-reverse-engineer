# 06 命令体系完整参考

## 命令总览

共 **109 个命令**，按 15 个功能模块 (`AgentModuleEnum`) 分组。

## INIT 模块 — 初始化与配置

| 命令 | 说明 |
|------|------|
| `init` | 初始化 |
| `action_init` | 插件初始化 (携带 pluginVersion, clientName, apiVersion, projectPath) |
| `git_code_knowledge_repo_status` | 代码知识库仓库状态 |
| `git_repo_authorize` | Git 仓库授权 |
| `git_code_knowledge_re_index` | 代码知识库重新索引 |
| `git_save_token` | 保存 Git Token |
| `update` | 更新检查 |
| `error` | 错误通知 |
| `general_setting` | 通用设置 |
| `repo_status` | 仓库状态查询 |
| `rag_languages` | RAG 支持语言列表 |

## LOGIN 模块 — 用户认证

| 命令 | 说明 |
|------|------|
| `user_version` | 用户版本检查 (也用于心跳) |
| `user_login` | 用户登录 |
| `user_logout` | 用户登出 |
| `user_login_abort` | 中止登录 |
| `user_login_check` | 检查登录状态 |
| `user_model_list` | 获取可用模型列表 |
| `user_permission` | 获取用户权限 |
| `model_list_timer` | 定时刷新模型列表 |
| `login_info` | 登录信息 (含 Token, URLs, 企业信息) |

## CODE_COMPLETE 模块 — 代码补全

| 命令 | 说明 |
|------|------|
| `code_complete` | 请求代码补全 |
| `action_sync_document_list` | 同步文档列表 |
| `log_imitative_write` | 仿写日志 |
| `log_accept` | 接受补全日志 |
| `log_accept_word` | 逐词接受日志 |
| `log_accept_line` | 逐行接受日志 |
| `log_reject` | 拒绝补全日志 |
| `log_reject_esc` | ESC 拒绝日志 |
| `log_display` | 补全展示日志 |
| `user_can_code_enhance` | 代码增强可用性 |

## CHAT 模块 — 智能对话

### 对话管理

| 命令 | 说明 |
|------|------|
| `talk_history` | 获取对话历史 (需 sessionId) |
| `talk_list` | 获取会话列表 |
| `talk_delete` | 删除消息 |
| `talk_clear` | 清空历史 (需 sessionId) |
| `talk_resend` | 重发消息 |

### AI 对话

| 命令 | 说明 | 权限代码 |
|------|------|---------|
| `talk_ask` | 发送对话消息 | `talk_intelligent` |
| `talk_intelligent` | 智能对话 (含 assistant 选择) | `talk_intelligent` |
| `talk_predict` | 预测响应 | — |
| `talk_knowledge` | 知识库问答 | — |

### 代码操作

| 命令 | 说明 | 权限代码 |
|------|------|---------|
| `code_explain` | 代码解释 | `code_explain` |
| `code_optimize` | 代码优化 | `code_optimization` |
| `code_comment` | 函数注释 | `doc_comments` |
| `code_inline_comment` | 行间注释 | `line_comments` |
| `code_split` | 函数拆分 | `function_split` |
| `code_debug` | 错误分析与修复 | `code_debug` |
| `code_help` | 代码帮助 | — |

### 需求与测试

| 命令 | 说明 | 权限代码 |
|------|------|---------|
| `code_demand_test` | 需求测试 | `demand_test` |
| `code_generate_test_case` | 生成测试用例 | `generate_test_case` |
| `code_demand_analysis` | 需求分析 | `demand_analysis` |
| `code_demand_splitting` | 需求拆分 | `demand_split` |
| `code_fault_analysis` | 故障分析 | `failure_analysis` |

### SQL 对话 (走 CHAT 模块)

| 命令 | 说明 | 权限代码 |
|------|------|---------|
| `sql_generate_talk` | SQL 生成对话 | `chat_sql_generation` |
| `sql_optimize_talk` | SQL 优化对话 | `chat_sql_optimization` |

### 其他

| 命令 | 说明 |
|------|------|
| `user_knowledge_list` | 知识库列表 |
| `user_parse_web_url` | 解析网页 URL |
| `git_repository_status` | Git 仓库状态 |
| `talk_download_markdown_table` | 下载 Markdown 表格 |
| `talk_recommend_gameplay` | 推荐玩法 |
| `user_feedback_category` | 反馈分类 |
| `feedback_category_info` | 反馈分类详情 |
| `action_open_document` | 打开文档 |
| `code_comment_range` | 批量注释指定范围 |

## SQL_CHAT 模块 — SQL 管理

| 命令 | 说明 |
|------|------|
| `sql_source_list` | 数据源列表 |
| `sql_source_types` | 数据库类型列表 |
| `sql_test_connect` | 测试连接 |
| `sql_source_edit` | 编辑数据源 |
| `sql_source_delete` | 删除数据源 |
| `sql_table_list` | 表列表 |
| `sql_generate` | SQL 生成 |
| `sql_optimize` | SQL 优化 |

## CODE_SEARCH 模块 — 代码搜索

| 命令 | 说明 |
|------|------|
| `git_lang_list` | 支持语言列表 |
| `git_user_repos` | 用户仓库列表 |
| `git_search` | 代码搜索 |

## GIT_REVIEW 模块 — Git 评审

| 命令 | 说明 | 权限代码 |
|------|------|---------|
| `git_diff` | 获取 Diff | — |
| `git_review` | 代码评审 | `review` |
| `git_commit_message` | 生成 Commit Message | `generate_commit` |

## CODE_CHECK 模块 — 代码检查

| 命令 | 说明 |
|------|------|
| `code_check` | 代码检查 |
| `code_debug_duplicate` | 重复代码检测 |

## UNIT_TEST 模块 — 单元测试

| 命令 | 说明 | 权限代码 |
|------|------|---------|
| `code_test` | 单元测试 | `unit_testing` |
| `code_test_template` | 模板化单测 | — |
| `code_test_analysis` | 测试分析 | — |
| `code_test_case` | 测试用例生成 | — |
| `code_test_make_case_java` | Java 测试用例 | — |
| `test_make_case` | 通用测试用例 | — |
| `test_make_code` | 测试代码生成 | — |
| `code_test_code` | 获取测试代码 | — |
| `code_test_save` | 保存测试 | — |

## BATCH_UNIT_TEST 模块 — 批量单元测试

| 命令 | 说明 |
|------|------|
| `code_batch_unit_test_create` | 创建批量单测 |
| `code_batch_unit_test_list` | 获取批量单测列表 |
| `code_batch_unit_test_download` | 下载批量单测 |
| `code_batch_unit_test_cancel` | 取消批量单测 |
| `code_batch_unit_test_delete` | 删除批量单测 |

## INLINE_CHAT 模块 — 内联聊天

| 命令 | 说明 | 权限代码 |
|------|------|---------|
| `dialog_edit` | 编辑对话 | `inline_chat` |
| `dialog_abort` | 中止对话 | — |
| `dialog_reject` | 拒绝对话 | — |
| `dialog_accept` | 接受对话 | — |
| `dialog_diff` | 查看 Diff | — |
| `inlinechat_get_func_range` | 获取函数范围 | — |
| `inlinechat_category` | Inline Chat 分类 | — |
| `inlinechat_direct` | Inline Chat 直达 | — |

## COMMON 模块 — 通用/日志

| 命令 | 说明 |
|------|------|
| `log_evaluation` | 评价日志 |
| `log_feedback` | 反馈日志 |
| `log_operate` | 操作日志 |
| `log_tip_setting` | 补全设置日志 |
| `action_abort` | 中止操作 |
| `log_accept_count` | 接受计数日志 |
| `log_test_collection_generate` | 测试收集生成日志 |
| `log_test_collection_commit` | 测试收集提交日志 |

## SERVER_RESOURCE 模块

| 命令 | 说明 |
|------|------|
| `server_resource` | 服务器资源状态 |

## 命令与权限映射

23 种权限 (`PermissionEnum`)：

| 权限 | 关联 IntelliJ Action | 关联命令 |
|------|---------------------|---------|
| `code_optimization` | CodeOptimizeAction | code_optimize |
| `comments` | ExplainCodeAction | code_explain |
| `unit_testing` | UnitTestAction | code_test |
| `doc_comments` | DocumentCommentAction | code_comment |
| `line_comments` | InlineCommentAction | code_inline_comment |
| `function_split` | FunctionSplitAction | code_split |
| `inline_chat` | OpenInlayInlineChatAction | dialog_edit, inlinechat_* |
| `talk_intelligent` | — | talk_intelligent |
| `chat_module` | — | talk_ask |
| `code_debug` | — | code_debug |
| `review` | — | git_review |
| `generate_commit` | — | git_commit_message |
| `batch_unittest` | — | code_batch_unit_test_* |
| `code_knowledge_base` | — | git_code_knowledge_* |
| `sql_generation` | — | sql_generate |
| `sql_optimization` | — | sql_optimize |
| `demand_test` | — | code_demand_test |
| `generate_test_case` | — | code_generate_test_case |
| `chat_sql_generation` | — | sql_generate_talk |
| `chat_sql_optimization` | — | sql_optimize_talk |
| `demand_analysis` | — | code_demand_analysis |
| `demand_split` | — | code_demand_splitting |
| `failure_analysis` | — | code_fault_analysis |
