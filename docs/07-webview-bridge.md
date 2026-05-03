# 07 WebView JS Bridge 协议

## 通信机制

WebView 使用 JCEF (Java Chromium Embedded Framework) 内嵌浏览器，通过 `JBCefJSQuery` 实现双向通信。

## Java → WebView

通过执行 JavaScript 调用：

```java
browser.getCefBrowser().executeJavaScript(
    "receiveData(" + gson.toJson(data) + ");",
    browser.getCefBrowser().getURL(), 0
);
```

**消息格式**：
```json
{
  "type": "<WebViewDataTypeEnum 值>",
  "value": { ... },
  "data": { ... }
}
```

## WebView → Java

通过注入的 JavaScript 桥接对象：

```javascript
// 在 WebView 页面加载完成后注入
window.myObject = {
    sendMessage: function(data) {
        // JBCefJSQuery 回调
    }
};
```

**消息格式**：
```json
{
  "type": "<WebViewDataTypeEnum 值>",
  "data": { ... },
  "value": { ... }
}
```

## 页面加载

- 使用自定义 Scheme 加载本地资源（从 JAR classpath）
- 加载成功 (HTTP 200) 时注入 JS Bridge 并触发 `USER_LOGIN`
- URL 格式: `{custom_scheme}://{custom_host}/index.html`

## 消息类型完整列表 (WebViewDataTypeEnum)

共 **124 种**消息类型，按 12 个 UI 模块 (`ModuleEnum`) 路由。

### LOGIN 模块 (8 种)

| 类型 | 方向 | 说明 |
|------|------|------|
| `login_init` | W→J | 初始化登录 |
| `login_logout` | W→J | 登出 |
| `login_receiver_login_iframe_src` | J→W | 推送登录 iframe URL |
| `login_succeed` | J→W | 登录成功 |
| `login_go_login` | W→J | 跳转登录 |
| `login_close_qr_code` | W→J | 关闭二维码 |
| `login_abort` | W→J | 中止登录 |
| `login_check` | W→J | 检查登录状态 |

### CHAT 模块 (40+ 种)

#### 对话管理

| 类型 | 方向 | 说明 |
|------|------|------|
| `chat_choose_history_item` | W→J | 选择历史对话 |
| `chat_get_history_list` | W→J | 获取历史列表 |
| `chat_delete_history_item` | W→J | 删除对话 |
| `chat_delete_history_all` | W→J | 删除所有对话 |
| `chat_refresh_model` | W→J | 刷新模型 |
| `chat_get_model_list` | W→J | 获取模型列表 |
| `chat_set_model` | W→J | 设置当前模型 |
| `chat_get_conversation` | W→J | 获取对话详情 |
| `chat_send_msg` | W→J | 发送消息 |
| `chat_resend` | W→J | 重发消息 |
| `chat_choose_file` | W→J | 选择文件引用 |
| `chat_send_open_dir_list` | W→J | 打开目录列表 |
| `chat_update_conversation_list` | J→W | 更新对话列表 (流式) |
| `chat_receiver_history_list` | J→W | 历史列表响应 |
| `chat_get_conversation_list` | J→W | 对话列表响应 |
| `chat_delete_msg` | W→J | 删除消息 |
| `chat_predict` | J→W | 预测响应 |
| `chat_stop_response` | W→J | 停止响应 |
| `chat_new_chat` | W→J | 新建对话 |
| `chat_get_user_info` | W→J | 获取用户信息 |

#### 高级功能

| 类型 | 方向 | 说明 |
|------|------|------|
| `chat_get_ide_file_state` | W→J | 获取 IDE 文件状态 |
| `chat_recommend_gameplay` | J→W | 推荐玩法 |
| `chat_get_doc_knowledge_list` | W→J | 获取知识库列表 |
| `chat_feedback_category` | W→J | 反馈分类 |
| `chat_get_feedback_list` | W→J | 反馈列表 |
| `chat_receiver_*` | J→W | 各种推送通知 |
| `chat_valid_website` | W→J | 验证网站 URL |
| `chat_agent_refresh` | J→W | Agent 刷新状态 |
| `chat_get_open_dir_list` | W→J | 获取目录列表 |
| `chat_show_fresh` | J→W | 显示刷新 |
| `chat_get_doc_knowledge_list` | W→J | 获取知识库文档列表 |
| `chat_get_open_dir_list` | W→J | 获取打开目录列表 |

### SQL_CHAT 模块 (10 种)

| 类型 | 方向 | 说明 |
|------|------|------|
| `sql_chat_source_list` | W→J | 获取数据源列表 |
| `sql_chat_request_source_types` | W→J | 获取数据库类型 |
| `sql_chat_sql_link_test` | W→J | 测试数据库连接 |
| `sql_chat_sql_save` | W→J | 保存数据源 |
| `sql_chat_source_delete` | W→J | 删除数据源 |
| `sql_chat_table_list` | W→J | 获取表列表 |
| `sql_chat_send_msg` | W→J | 发送 SQL 对话 |
| `sql_chat_get_model_list` | W→J | 获取模型列表 |
| `sql_chat_new_chat` | W→J | 新建 SQL 对话 |
| `sql_chat_stop_response` | W→J | 停止响应 |

### UNIT_TEST 模块 (18 种)

| 类型 | 方向 | 说明 |
|------|------|------|
| `ut_get_ut_info` | W→J | 获取单测信息 |
| `ut_get_method_case` | W→J | 获取方法用例 |
| `ut_get_case_code` | W→J | 获取用例代码 |
| `ut_get_all_code_file` | W→J | 获取所有代码文件 |
| `ut_request_*` | W→J | 各种请求 |
| `ut_copy_case_code` | W→J | 复制用例代码 |
| `ut_page_ready` | W→J | 页面就绪 |
| `ut_function_list` | J→W | 函数列表 |
| `ut_function_case` | J→W | 函数用例 |
| `ut_receive_*` | J→W | 各种接收 |
| `ut_save_code` | W→J | 保存代码 |
| `ut_regenerate` | W→J | 重新生成 |

### UNIT_TESTING 模块 (8 种)

| 类型 | 方向 | 说明 |
|------|------|------|
| `unitesting_receive_function` | J→W | 接收函数 |
| `unitesting_receive_data` | J→W | 接收数据 |
| `unitesting_page_ready` | W→J | 页面就绪 |
| `unitesting_save` | W→J | 保存 |
| `unitesting_response_save` | J→W | 保存响应 |
| `unitesting_mapping_file` | W→J | 映射文件 |
| `unitesting_idea_stop` | W→J | IDE 停止 |
| `unitesting_web_stop` | W→J | WebView 停止 |

### BATCH_UNIT_TEST 模块 (7 种)

| 类型 | 方向 | 说明 |
|------|------|------|
| `batch_ut_create` | W→J | 创建批量单测 |
| `batch_ut_get_list` | W→J | 获取列表 |
| `batch_ut_download` | W→J | 下载 |
| `batch_ut_delete` | W→J | 删除 |
| `batch_ut_message` | J→W | 消息推送 |
| `batch_ut_refresh_task_download_status` | W→J | 刷新下载状态 |
| `batch_ut_get_task_list` | W→J | 获取任务列表 |

### CODE_SEARCH 模块 (7 种)

| 类型 | 方向 | 说明 |
|------|------|------|
| `request_codesearch_code_list` | W→J | 搜索代码 |
| `request_codesearch_repository_list` | W→J | 仓库列表 |
| `request_codesearch_language_list` | W→J | 语言列表 |
| `request_copy_code` | W→J | 复制代码 |
| `request_insert_code` | W→J | 插入代码 |
| `request_code_file` | W→J | 代码文件 |
| `request_open_url` | W→J | 打开 URL |

### GIT_VIEW 模块 (4 种)

| 类型 | 方向 | 说明 |
|------|------|------|
| `code_review_page_ready` | W→J | 评审页就绪 |
| `code_review_get_change_result` | W→J | 获取变更结果 |
| `code_review_get_codereview_list` | W→J | 获取评审列表 |
| `code_review_get_change_result_end` | J→W | 变更结果结束 |

### CODE_CHECK 模块 (4 种)

| 类型 | 方向 | 说明 |
|------|------|------|
| `request_code_check_list` | W→J | 请求检查列表 |
| `get_code_check_list` | J→W | 获取检查列表 |
| `code_check_fix` | W→J | 修复代码 |
| `update_code_check` | W→J | 更新检查 |

### COMMON 模块 (11 种)

| 类型 | 方向 | 说明 |
|------|------|------|
| `common_page_ready` | W→J | 页面就绪 |
| `common_open_url` | W→J | 打开 URL |
| `common_open_page` | J→W | 打开页面 |
| `common_open_file_dialog` | W→J | 打开文件对话框 |
| `common_code_click_action` | W→J | 代码点击操作 (copy/insert/new/diff) |
| `common_focus_file` | W→J | 聚焦文件 |
| `common_focus_file_line` | W→J | 聚焦文件行 |
| `common_evaluation` | W→J | 评价 |
| `common_feedback` | W→J | 反馈 |
| `common_download_table` | W→J | 下载表格 |
| `common_show_message_in_web` | J→W | 显示消息 |
| `common_plugin_base_info` | J→W | 插件基础信息 |

### SETTING 模块 (6 种)

| 类型 | 方向 | 说明 |
|------|------|------|
| `setting_get_config` | W→J | 获取配置 |
| `setting_update_config` | W→J | 更新配置 |
| `setting_get_can_open_code_enhance` | W→J | 代码增强可用性 |
| `setting_popup_keymap_settings` | W→J | 弹出快捷键设置 |
| `setting_save_show_operate_guidance` | W→J | 保存操作指南显示 |
| `setting_receive_repo_status` | J→W | 接收仓库状态 |

### LOGIN 模块特殊消息

| 类型 | 方向 | 说明 |
|------|------|------|
| `login_show_fresh` | J→W | 显示刷新按钮 (Agent 重启时) |

## 响应类型 (WebViewResponseTypeEnum)

Plugin → WebView 的标准响应类型：

| 类型 | 说明 |
|------|------|
| `user_permission_list` | 用户权限列表 |
| `setting_change_theme` | 主题变更通知 |
| `code_search_get_codesearch_code_list` | 代码搜索结果 |
| `code_search_get_codesearch_repository_list` | 仓库搜索结果 |
| `code_search_get_codesearch_language_list` | 语言列表 |
| `code_search_get_code_copy_success` | 复制成功 |
| `code_review_receiver_page_init` | 评审页初始化 |
| `code_review_receiver_code_review` | 代码评审数据 |
| `code_review_receiver_change_result` | 变更结果 |
| `sql_chat_receive_source_list` | 数据源列表 |
| `sql_chat_receive_source_types` | 数据库类型 |
| `sql_chat_receive_link_test` | 连接测试结果 |
| `sql_chat_receive_save` | 保存确认 |
| `sql_chat_receive_table_list` | 表列表 |
| `sql_chat_update_conversation_list` | SQL 对话更新 |

## 消息路由

```
WebView ──sendMessage()──► WebViewWindowPanel.handleRequest()
    │
    ├─► 解析 JSON，提取 "type" 字段
    │
    ├─► 匹配 WebViewDataTypeEnum
    │
    └─► 按 getModule() 分发到 Service:
        ├─ CHAT        → ChatService.handleAction()
        ├─ LOGIN       → UserService.handleAction()
        ├─ SQL_CHAT    → SqlService.handleAction()
        ├─ CODE_SEARCH → CodeSearchService.handleAction()
        ├─ UNIT_TEST   → UnitTestService.handleAction()
        ├─ BATCH_UNIT_TEST → BatchUnitTestService.handleAction()
        ├─ CODE_CHECK  → CodeCheckService.handleAction()
        ├─ GIT_VIEW    → GitReviewService.handleAction()
        ├─ COMMON      → CommonService.handleAction()
        └─ SETTING     → CommonService.handleAction()

Service 处理:
    │
    ├─► 构造 MessageDto
    ├─► PluginWebsocketClient.sendWsMessage()
    └─► Agent 响应后通过 send2Web() 推送回 WebView
```

## 页面导航 (PageEnum)

| 页面 | 说明 |
|------|------|
| `chat_view` | 主对话页面 |
| `setting_page` | 设置页面 |
| `code_check` | 代码检查页面 |
| `code_review` | 代码评审页面 |
| `unit_test` | 单元测试页面 |
| `unit_testing` | 单元测试中页面 |
