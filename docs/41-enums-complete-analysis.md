# iFlyCode 枚举体系完整分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-11

## 1. 概述

iFlyCode 包含 31 个枚举类，分布在 `com/aicode/enums/`、`com/aicode/agent/enums/`、`com/aicode/apm/enums/`、`com/aicode/inline/enums/` 四个包中。枚举定义了插件的所有状态、命令、类型和配置选项。

## 2. 插件枚举 (com/aicode/enums)

### 2.1 AICodeStatus

**实现**: `PresentableEnum` — 可展示枚举
**职责**: 插件运行状态

**推断枚举值**:
- `NOT_LOGIN` — 未登录
- `LOGINED` — 已登录
- `AGENT_STARTING` — Agent 启动中
- `AGENT_RUNNING` — Agent 运行中
- `AGENT_ERROR` — Agent 错误
- `AGENT_STOPPED` — Agent 已停止

### 2.2 AssistantTypeEnum

**枚举值**: `IFLY_MATE`, `IFLY_DEV`, `IFLY_TEST`, `IFLY_DBA`, `IFLY_OPS`, `IFLY_PM`

**对应角色**:
| 枚举值 | 角色 | 说明 |
|--------|------|------|
| IFLY_MATE | AI 助手 | 通用编程助手 |
| IFLY_DEV | 开发助手 | 代码开发辅助 |
| IFLY_TEST | 测试助手 | 单元测试生成 |
| IFLY_DBA | DBA 助手 | SQL 生成和优化 |
| IFLY_OPS | 运维助手 | 运维相关辅助 |
| IFLY_PM | PM 助手 | 产品经理辅助 |

### 2.3 ChatOperationEnum

**枚举值**: `ACTION_INSERT`, `ACTION_COPY`, `ACTION_NEW`, `ACTION_DIFF`, `ACTION_ACCEPT`, `ACTION_ACCEPT_INLINE_COMMENT`

**职责**: 聊天消息中的代码操作类型

| 枚举值 | 说明 |
|--------|------|
| ACTION_INSERT | 插入代码到编辑器 |
| ACTION_COPY | 复制代码 |
| ACTION_NEW | 新建文件 |
| ACTION_DIFF | 显示 Diff 对比 |
| ACTION_ACCEPT | 接受建议 |
| ACTION_ACCEPT_INLINE_COMMENT | 接受内联注释 |

### 2.4 PluginSceneEnum

**枚举值**: `PLUGIN_SAAS`, `PLUGIN_PRIVATE`, `PLUGIN_INNER`

**职责**: 插件部署场景

| 枚举值 | 说明 |
|--------|------|
| PLUGIN_SAAS | SaaS 公有云版本 |
| PLUGIN_PRIVATE | 私有化部署版本 |
| PLUGIN_INNER | 内部版本 |

### 2.5 RestartEnum

**枚举值**: `HEART_BEAT_ERROR`, `CONNECT_REFUSED`, `CLOSE_EXCEPTION`, `CLOSE_RECONNECT`, `START_AGENT`, `REFRESH_RECONNECT`, `CONNECT_ERROR`, `BLANK_PORT`, `CONNECT_FAILED`, `CLOSE_ERROR`

**职责**: Agent 进程重启原因

| 枚举值 | 说明 |
|--------|------|
| HEART_BEAT_ERROR | 心跳检测失败 |
| CONNECT_REFUSED | 连接被拒绝 |
| CLOSE_EXCEPTION | 关闭异常 |
| CLOSE_RECONNECT | 关闭后重连 |
| START_AGENT | 启动 Agent |
| REFRESH_RECONNECT | 刷新重连 |
| CONNECT_ERROR | 连接错误 |
| BLANK_PORT | 端口为空 |
| CONNECT_FAILED | 连接失败 |
| CLOSE_ERROR | 关闭错误 |

### 2.6 GitRepoStatusEnum

**枚举值**: `TOKEN_INVALID`, `SSH_PROTOCOL`, `AUTHORIZED_EXPIRED`

**职责**: Git 仓库认证状态

### 2.7 RepoStatusEnum

**枚举值**: `MISSING_TOKEN`, `AUTHORIZED_1`, `AUTHORIZED_2`, `AUTHORIZED_3`, `AUTHORIZED_4`, `AUTHORIZED_5`, `UNSUPPORTED_PROTOCOL`

**职责**: 仓库状态（含多级授权和协议支持）

### 2.8 LanguageEnum

**枚举值** (部分): `C_LANGUAGE_01`, `CPP_LANGUAGE_01`~`CPP_LANGUAGE_13`, `PYTHON_LANGUAGE_01`~`PYTHON_LANGUAGE_13`

**职责**: 编程语言枚举，每种语言有多个子类型编号（可能对应不同版本或框架）

### 2.9 FileExtensionEnum

**枚举值**: `OBJECTIVE_C`, `CPP_LANGUAGE_01`, `C_LANGUAGE_01`, `PYTHON_LANGUAGE_01`

**职责**: 文件扩展名映射

### 2.10 TestGenerationProcess

**枚举值**: `GENERATION_BUILD`, `GENERATION_BUILD_EXECUTE`

**职责**: 测试生成流程阶段

| 枚举值 | 说明 |
|--------|------|
| GENERATION_BUILD | 仅生成+编译 |
| GENERATION_BUILD_EXECUTE | 生成+编译+执行 |

### 2.11 UnitTestBaseEnum

**枚举值**: `JUNIT_FIVE`, `JUNIT_FOUR`

**职责**: 单元测试基类框架选择

### 2.12 UnitTestMockEnum

**枚举值**: `POWER_MOCK`

**职责**: 单元测试 Mock 框架选择

### 2.13 TipTypeEnum

**枚举值**: `SINGLE_LINE`, `INTELLIGENT_MODE`

**职责**: 代码补全提示类型

| 枚举值 | 说明 |
|--------|------|
| SINGLE_LINE | 单行补全 |
| INTELLIGENT_MODE | 智能模式补全 |

### 2.14 SendKeyEnum

**枚举值**: `ENTER_KEY`, `ENTER_SHIFT_KEY`

**职责**: 发送消息的快捷键

### 2.15 WebViewDataTypeEnum

**枚举值** (部分):
- `BATCH_UNIT_TEST_MESSAGE` — 批量单测消息
- `CHAT_GET_HISTORY_LIST` — 获取聊天历史
- `CHAT_GET_CONVERSATION` — 获取对话
- `CHAT_SEND_VALID_WEBSITE_RESULT` — 发送有效网站结果
- `CHAT_CHOOSE_HISTORY_ITEM` — 选择历史项
- `CHAT_DELETE_MSG` — 删除消息
- `CHAT_GET_OPEN_DIR_LIST` — 获取打开目录列表
- `CHAT_VALID_WEBSITE` — 验证网站
- `CODE_REVIEW_GET_CHANGE_RESULT` — 获取代码评审结果
- `CODE_REVIEW_GET_CHANGE_RESULT_END` — 代码评审结果结束
- `CODE_SEARCH_REQUEST_CODESEARCH_CODE_LIST` — 请求代码搜索
- `CODE_CHECK_FIX` — 代码检查修复
- `COMMON_EVALUATION` — 通用评价
- `COMMON_OPEN_FILE_DIALOG` — 打开文件对话框
- `GIT_RE_INDEX` — Git 重新索引
- `SQL_CHAT_UPDATE_CONVERSATION_LIST` — SQL 对话列表更新
- `UNIT_TESTING_RECEIVE_FUNCTION` — 接收测试函数
- `UNIT_TESTING_WEB_STOP` — Web 端停止测试
- `UNIT_TEST_GET_CASE_CODE` — 获取用例代码
- `BATCH_UNIT_TEST_GET_TASK_LIST` — 获取批量单测任务列表

### 2.16 WebViewResponseTypeEnum

**枚举值** (部分):
- `CODE_SEARCH_GET_CODESEARCH_REPOSITORY_LIST` — 代码搜索仓库列表
- `CODE_SEARCH_GET_CODESEARCH_LANGUAGE_LIST` — 代码搜索语言列表
- `CODE_SEARCH_GET_CODE_COPY_SUCCESS` — 代码复制成功
- `CODE_REVIEW_RECEIVER_PAGE_INIT` — 代码评审页面初始化
- `CODE_REVIEW_RECEIVER_CHANGE_RESULT` — 代码评审变更结果
- `CODE_REVIEW_RECEIVER_CODE_REVIEW` — 代码评审结果
- `SQL_CHAT_RECEIVE_TABLE_LIST` — SQL 表列表
- `SQL_CHAT_RECEIVE_SAVE` — SQL 保存
- `SQL_CHAT_RECEIVE_SOURCE_LIST` — SQL 数据源列表
- `SQL_CHAT_RECEIVE_SOURCE_TYPES` — SQL 数据源类型
- `SQL_CHAT_RECEIVE_LINK_TEST` — SQL 连接测试
- `SQL_CHAT_UPDATE_CONVERSATION_LIST` — SQL 对话列表更新
- `USER_PERMISSION_LIST` — 用户权限列表
- `SETTING_CHANGE_THEME` — 主题变更

## 3. Agent 枚举 (com/aicode/agent/enums)

### 3.1 CommandEnum

**职责**: WebSocket 命令枚举 — 109+ 个命令值
**已识别命令**:
- `CODE_CHECK` — 代码检查
- `GIT_DIFF` — Git Diff 评审
- `INLINECHAT_DIRECT` — 内联聊天直连
- `SQL_TEST_CONNECT` — SQL 测试连接
- `ABORT` — 取消请求

### 3.2 AgentModuleEnum

**职责**: Agent 模块枚举

### 3.3 ModuleEnum

**职责**: 功能模块枚举

### 3.4 PageEnum

**职责**: 页面枚举
**已知值**: `CHAT_VIEW` — 聊天视图页面

### 3.5 PermissionEnum

**职责**: 权限枚举

## 4. APM 枚举 (com/aicode/apm/enums)

### 4.1 TracerEnum

**枚举值**: `CODE_COMPLETE`, `CODE_COMPLETE_PARENT`, `CODE_COMPLETE_INLINE_CHAT_PARENT`, `AGENT_FAILURE`, `AGENT_ERROR`, `AGENT_RESTART`, `AGENT_RUN`

**职责**: OpenTelemetry Tracer 类型

| 枚举值 | 说明 |
|--------|------|
| CODE_COMPLETE | 代码补全 |
| CODE_COMPLETE_PARENT | 代码补全父 Span |
| CODE_COMPLETE_INLINE_CHAT_PARENT | 内联聊天补全父 Span |
| AGENT_FAILURE | Agent 故障 |
| AGENT_ERROR | Agent 错误 |
| AGENT_RESTART | Agent 重启 |
| AGENT_RUN | Agent 运行 |

### 4.2 SpanAttrEnum

**枚举值** (部分):
- `AGENT_ERROR_REASON` — Agent 错误原因
- `AGENT_START_REASON` / `AGENT_START_CODE` — Agent 启动原因/代码
- `AGENT_VERSION` — Agent 版本
- `COMPLETE_DURATION` / `COMPLETE_FIRST_DURATION` — 补全耗时/首字耗时
- `COMPLETE_IS_STREAM` — 是否流式补全
- `COMPLETE_ACCEPT` / `COMPLETE_REJECT` — 补全接受/拒绝
- `COMPLETE_FILE_LINE` / `COMPLETE_FILE_SIZE` — 补全文件行数/大小
- `COMPLETE_RESULT` / `COMPLETE_FORCE` — 补全结果/强制补全
- `COMMAND_ID` — 命令 ID
- `EXCEPTION_COMMAND` / `EXCEPTION_MESSAGE` / `EXCEPTION_CODE` — 异常命令/消息/代码
- `SETTING_MESSAGE_TYPE` / `SETTING_CODE_MODE` / `SETTING_JAVA_TEST` / `SETTING_JAVA_MOCK` — 设置相关属性

**职责**: OpenTelemetry Span 属性键

## 5. 枚举分类统计

| 包 | 枚举数 | 说明 |
|----|--------|------|
| com/aicode/enums | 17 | 插件核心枚举 |
| com/aicode/agent/enums | 5 | Agent 通信枚举 |
| com/aicode/apm/enums | 2 | APM 追踪枚举 |
| com/aicode/inline/enums | ~3 | 内联聊天枚举 |
| **总计** | **~27** | |

## 6. 关键发现

1. **6 种 AI 角色**: `AssistantTypeEnum` 定义了 6 种 AI 助手角色，覆盖开发、测试、DBA、运维、PM 等场景。

2. **10 种重启原因**: `RestartEnum` 详细分类了 Agent 进程的 10 种重启触发原因，说明重启逻辑非常健壮。

3. **3 种部署场景**: `PluginSceneEnum` 区分 SaaS、私有化和内部版本，说明 iFlyCode 有明确的企业私有化部署策略。

4. **WebView 双向枚举**: `WebViewDataTypeEnum`（请求类型）和 `WebViewResponseTypeEnum`（响应类型）构成 WebView 通信的完整协议。

5. **多级授权**: `RepoStatusEnum` 有 5 级授权状态（AUTHORIZED_1~5），可能对应不同的权限级别。

6. **APM 全链路追踪**: `TracerEnum` 和 `SpanAttrEnum` 覆盖了代码补全、Agent 生命周期、异常处理的全链路追踪属性。

7. **补全性能指标**: `SpanAttrEnum` 中的 `COMPLETE_DURATION` 和 `COMPLETE_FIRST_DURATION` 分别追踪总耗时和首字耗时（TTFT），是关键的性能指标。
