# 20 枚举值完整参考

## AgentModuleEnum — 命令路由模块 (15 个)

| 枚举 | 说明 |
|------|------|
| `LOG` | 日志 |
| `INIT` | 初始化 |
| `LOGIN` | 用户认证 |
| `COMMON` | 通用 |
| `CHAT` | 智能对话 |
| `SQL_CHAT` | SQL 对话 |
| `CODE_COMPLETE` | 代码补全 |
| `CODE_SEARCH` | 代码搜索 |
| `CODE_CHECK` | 代码检查 |
| `GIT_REVIEW` | Git 评审 |
| `UNIT_TEST` | 单元测试 |
| `BATCH_UNIT_TEST` | 批量单测 |
| `CODE_TEST_TEMPLATE` | 单测模板 |
| `SERVER_RESOURCE` | 服务器资源 |
| `INLINE_CHAT` | 内联聊天 |

## ModuleEnum — UI 路由模块 (12 个)

| 枚举 | 说明 |
|------|------|
| `LOG` | 日志 |
| `LOGIN` | 登录 |
| `COMMON` | 通用 |
| `SETTING` | 设置 |
| `CHAT` | 对话 |
| `SQL_CHAT` | SQL |
| `CODE_SEARCH` | 代码搜索 |
| `CODE_CHECK` | 代码检查 |
| `GIT_VIEW` | Git 视图 |
| `UNIT_TEST` | 单测 |
| `BATCH_UNIT_TEST` | 批量单测 |
| `UNIT_TESTING` | 测试中 |

## PermissionEnum — 功能权限 (23 个)

| 枚举 | 关联 Action |
|------|------------|
| `CODE_OPTIMIZATION` | CodeOptimizeAction |
| `COMMENTS` | ExplainCodeAction |
| `UNIT_TESTING` | UnitTestAction |
| `DOC_COMMENTS` | DocumentCommentAction |
| `LINE_COMMENTS` | InlineCommentAction |
| `FUNCTION_SPLIT` | FunctionSplitAction |
| `INLINE_CHAT` | OpenInlayInlineChatAction |
| `TALK_INTELLIGENT` | — |
| `CHAT_MODULE` | — |
| `CODE_DEBUG` | — |
| `REVIEW` | — |
| `GENERATE_COMMIT` | — |
| `BATCH_UNITTEST` | — |
| `CODE_KNOWLEDGE_BASE` | — |
| `SQL_GENERATION` | — |
| `SQL_OPTIMIZATION` | — |
| `DEMAND_TEST` | — |
| `GENERATE_TEST_CASE` | — |
| `CHAT_SQL_GENERATION` | — |
| `CHAT_SQL_OPTIMIZATION` | — |
| `DEMAND_ANALYSIS` | — |
| `DEMAND_SPLIT` | — |
| `FAILURE_ANALYSIS` | — |

## ChatOperationEnum — 对话操作 (6 个)

| 枚举 | 说明 |
|------|------|
| `ACTION_NEW` | 新建文件 |
| `ACTION_DIFF` | 差异对比 |
| `ACTION_INSERT` | 插入代码 |
| `ACTION_COPY` | 复制代码 |
| `ACTION_ACCEPT` | 接受注释 |
| `ACTION_ACCEPT_INLINE_COMMENT` | 接受行间注释 |

## CodeTipRequestType — 补全触发类型 (5 个)

| 枚举 | 说明 |
|------|------|
| `Automatic` | 自动触发 |
| `Interact` | 交互触发 |
| `Forced` | 强制触发 |
| `Manual` | 手动触发 |
| `InlineChat` | 内联聊天 |

## CodeTipType — 补全类型 (3 个)

| 枚举 | 说明 |
|------|------|
| `Inline` | 内联 |
| `AfterLineEnd` | 行尾 |
| `Block` | 块级 |

## TipTypeEnum — 补全模式 (2 个)

| 枚举 | 说明 |
|------|------|
| `SINGLE_LINE` | 单行模式 |
| `INTELLIGENT_MODE` | 智能模式 |

## AssistantTypeEnum — 助手类型 (6 个)

| 枚举 | 说明 |
|------|------|
| `IFLY_MATE` | 通用助手 |
| `IFLY_DEV` | 开发助手 |
| `IFLY_TEST` | 测试助手 |
| `IFLY_OPS` | 运维助手 |
| `IFLY_PM` | 产品助手 |
| `IFLY_DBA` | DBA 助手 |

## ClientTypeEnum — IDE 类型 (9 个)

| 枚举 | IDE |
|------|-----|
| `IE` | IntelliJ IDEA Educational |
| `IC` | IntelliJ IDEA Community |
| `IU` | IntelliJ IDEA Ultimate |
| `WS` | WebStorm |
| `PY` | PyCharm |
| `PC` | PyCharm Community |
| `CL` | CLion |
| `GO` | GoLand |
| `AI` | Android Studio |

## RestartEnum — 重启原因 (11 个)

| 枚举 | Code | 说明 |
|------|------|------|
| `START_AGENT` | 0 | Agent 启动失败 |
| `CONNECT_REFUSED` | 1 | 连接被拒 |
| `CONNECT_FAILED` | 2 | 连接失败 |
| `CONNECT_ERROR` | 3 | 连接错误 |
| `CLOSE_EXCEPTION` | 4 | 关闭异常 |
| `CLOSE_ERROR` | 5 | 关闭错误 |
| `BLANK_PORT` | 6 | 端口为空 |
| `REFRESH` | 7 | 刷新 |
| `HEART_BEAT_ERROR` | 8 | 心跳错误 |
| `CLOSE_RECONNECT` | 9 | 关闭后重连 |
| `REFRESH_RECONNECT` | 10 | 刷新后重连 |

## SendKeyEnum — 发送键 (2 个)

| 枚举 | 说明 |
|------|------|
| `ENTER_KEY` | Enter 发送 |
| `ENTER_SHIFT_KEY` | Shift+Enter 换行 |

## PluginSceneEnum — 场景 (3 个)

| 枚举 | 说明 |
|------|------|
| `PLUGIN_SAAS` | 公有云 |
| `PLUGIN_PRIVATE` | 私有化 |
| `PLUGIN_INNER` | 内部 |

## ElementTypeEnum — 元素类型 (2 个)

| 枚举 | 说明 |
|------|------|
| `METHOD` | 方法 |
| `CLASS` | 类 |

## CodeCollectEnum — 收集类型 (7 个)

| 枚举 | 说明 |
|------|------|
| `GENERATE` | 生成 |
| `INSERT` | 插入 |
| `COPY` | 复制 |
| `NEW` | 新建 |
| `UNITTEST` | 单测 |
| `COMPARE` | 对比 |
| `OTHER` | 其他 |

## InlineChatCategoryEnum — 内联聊天分类 (5 个)

| 枚举 | 说明 |
|------|------|
| `DOC` | 文档生成 |
| `LINEDOC` | 行级文档 |
| `EDIT` | 编辑代码 |
| `GENERATE` | 生成代码 |
| `UNKNOW` | 未知 |

## InlineChatOperateEnum — 内联聊天操作 (2 个)

| 枚举 | 说明 |
|------|------|
| `INSERT` | 新代码插入 |
| `EDIT` | 现有代码修改 |

## InlineChatStepEnum — 内联聊天阶段 (4 个)

| 枚举 | 说明 |
|------|------|
| `CATEGORY` | 分类选择 |
| `LOADING` | 等待响应 |
| `ERROR` | 错误 |
| `SUCCESS` | 成功 |

## PageEnum — 页面 (6 个)

| 枚举 | 说明 |
|------|------|
| `CHAT_VIEW` | 对话页 |
| `SETTING_PAGE` | 设置页 |
| `CODE_CHECK` | 代码检查页 |
| `CODE_REVIEW` | 代码评审页 |
| `UNIT_TEST` | 单测页 |
| `UNIT_TESTING` | 测试中页 |
