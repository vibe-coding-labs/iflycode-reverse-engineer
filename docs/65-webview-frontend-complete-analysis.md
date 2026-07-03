# iFlyCode WebView 前端完整分析 (Vue.js 2.7 + Pinia)

> 版本: 3.4.2-222 | 分析日期: 2026-05-11 | 文档编号: 65

## 1. 概述

本文档详细分析 iFlyCode WebView 前端代码，包括技术栈、路由结构、Pinia Stores、JS↔Java Bridge 通信协议、权限系统、UI 组件和中文界面字符串。

**重要修正**: 之前文档（doc 30）错误地记录为 Vue.js 3 + Pinia。实际分析发现 WebView 使用 **Vue.js 2.7.14**（兼容 Composition API），而非 Vue 3。

## 2. 技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| Vue.js | **2.7.14** | 前端框架（非 Vue 3） |
| Pinia | — | 状态管理（3 stores） |
| Vue Router | — | 路由管理（17 路径） |
| Element UI | — | UI 组件库（70+ 组件） |
| Mermaid | — | 图表渲染（20+ 图类型） |
| KaTeX | — | 数学公式渲染 |
| Cytoscape | — | 网络图可视化 |
| DOMPurify | — | HTML 安全过滤 |
| Vite | — | 构建工具 |

### 2.1 构建产物统计

| 类型 | 数量 | 总大小 |
|------|------|--------|
| JS 文件 | 84 | ~10.5 MB |
| CSS 文件 | 24 | ~460 KB |
| 图片/图标 | 8 | ~50 KB |
| SVG | 3 | ~5 KB |
| HTML | 1 | 491 B |

### 2.2 Top 10 JS 文件

| 文件 | 大小 | 内容 |
|------|------|------|
| index-f0296668.js | 3.6 MB | Vue 2.7 + Element UI + 核心业务逻辑 |
| index-9c2209c9.js | 1.5 MB | Element UI 组件 + Pinia chat store |
| sendMsgMode-8b767ec0.js | 280 KB | SQL Chat + 消息发送模式 |
| cytoscape.esm-23e802cd.js | 902 KB | Cytoscape 网络图库 |
| mermaid-parser.core-dc9f3dce.js | 611 KB | Mermaid 图表解析器 |
| katex-db156564.js | 478 KB | KaTeX 数学公式 |
| architectureDiagram-IEHRJDOE.js | 406 KB | 架构图渲染 |
| tips-f0105b0c.js | 80 KB | 代码补全提示 |
| index-1aaa16d8.js | 34 KB | CodeCheck store |
| chunk-A2AXSNBT-9b062cf2.js | 77 KB | 共享 chunk |

## 3. Vue Router 路径

### 3.1 路由表

| 路径 | 页面 | 说明 |
|------|------|------|
| `/` | 主页 | 默认首页 |
| `/login` | 登录页 | 用户登录 |
| `/chat-view` | 智能问答 | AI 聊天对话 |
| `/code-check` | 代码检查 | 代码质量检查 |
| `/code-review` | 代码评审 | Git 代码评审 |
| `/code-search` | 代码搜索 | 语义代码搜索 |
| `/unit-test` | 单元测试 | 单测生成 |
| `/unit-test-bank` | 单测题库 | 单测管理 |
| `/multi-test` | 批量单测 | 批量单测生成 |
| `/sql-page` | SQL 页面 | SQL 生成/优化 |
| `/setting-page` | 设置页 | 插件配置 |
| `/about-page` | 关于页 | 版本信息 |
| `/history-record` | 历史记录 | 对话历史 |
| `/tool-box` | 工具箱 | 功能入口 |
| `/function-guide` | 功能引导 | 新手引导 |
| `/web-view/:name` | WebView | 通用 WebView 页面 |

### 3.2 路由守卫

- `router.beforeEach` — 路由前置守卫（1 处）
- `router.afterEach` — 路由后置守卫（1 处）

## 4. Pinia Stores

### 4.1 Store 定义

| Store | 文件 | 状态领域 |
|-------|------|---------|
| `chat` | index-f0296668.js | 聊天消息、对话列表、模型选择 |
| `codeCheck` | index-1aaa16d8.js | 代码检查结果、检查列表 |
| `sql` | sendMsgMode-8b767ec0.js | SQL 对话、数据源、表列表 |

**注意**: 之前文档（doc 30）记录了 7 个 Pinia Stores，实际只有 3 个。其余 4 个（Git Review、Code Search、Settings、Unit Test）可能是 Vue 2 Options API 组件的本地状态，而非独立 Pinia Store。

## 5. JS↔Java Bridge 通信协议

### 5.1 Bridge 架构

```
┌─────────────────────────────────────────────────┐
│                  WebView (JCEF)                   │
│                                                   │
│  ┌───────────┐  ┌───────────┐  ┌───────────┐    │
│  │ ideaUtil  │  │ vscodeUtil│  │ eclipseUtil│    │
│  │ (IDEA)    │  │ (VSCode)  │  │ (Eclipse)  │    │
│  └───────────┘  └───────────┘  └───────────┘    │
│        │              │              │            │
│        ▼              ▼              ▼            │
│  ┌─────────────────────────────────────────┐     │
│  │         handlerReceivedMsg(type, value) │     │
│  │         sendMsgToIdea(type, value)      │     │
│  └─────────────────────────────────────────┘     │
│                                                   │
└─────────────────────────────────────────────────┘
```

### 5.2 三种 IDE Bridge 实现

#### IDEA Bridge (ideaUtil-11ab0730.js)

```javascript
// JS→Java: 通过 window.myObject.sendMessage
function sendMsgToIdeaHandler(type, value = "") &#123;
  window.myObject.sendMessage(
    JSON.stringify(&#123; type, value &#125;)
  );
&#125;

// Java→JS: 通过 window.receiveData 回调
window.receiveData = function receiveData(data) &#123;
  let message = typeof data === "object" ? data : JSON.parse(data);
  handlerReceivedMsg(message.type, message.value);
&#125;;
```

#### VSCode Bridge (vscodeUtil-49d49699.js)

```javascript
// JS→Java: 通过 vscode.postMessage
function sendMsgToIdeaHandler(type, value) &#123;
  vscode.postMessage(&#123; type, value: JSON.stringify(value) &#125;);
&#125;

// Java→JS: 通过 window message event
window.addEventListener("message", (event) => &#123;
  handlerReceivedMsg(event.data.type, event.data.value);
&#125;);
```

#### Eclipse Bridge (eclipseUtil-82d0751a.js)

```javascript
// JS→Java: 通过 window.sendMessage
function sendMsgToIdeaHandler(type, value = "") &#123;
  window.sendMessage(
    JSON.stringify(&#123; type, value: JSON.stringify(value) &#125;)
  );
&#125;

// Java→JS: 通过 window.receiveData 回调
window.receiveData = function receiveData(data) &#123;
  handlerReceivedMsg(JSON.parse(data).type, JSON.parse(data).value);
&#125;;
```

### 5.3 Java→JS 消息类型 (handlerReceivedMsg switch/case)

完整从源码提取的 50+ 消息类型：

| 消息类型 | 处理方法 | 说明 |
|---------|---------|------|
| `CHAT:AGENT_ERROR` | hanldeAgentError | Agent 错误 |
| `CHAT:GET_USER_INFO` | receiveUserInfo | 用户信息 |
| `CHAT:GET_CONVERSATION` | getConversation | 获取对话 |
| `CHAT:UPDATE_CONVERSATION_LIST` | updateConversationList | 更新对话列表 |
| `CHAT:UPDATE_SELECT_CODE` | updateSelectCode | 更新选中代码 |
| `CHAT:SET_SEND_MESSAGE_TYPE` | setSendMessageType | 设置发送模式 |
| `CHAT:GET_MODEL_LIST` | getModelList | 获取模型列表 |
| `CHAT:PREDICT` | updatePredictList | 更新预测列表 |
| `CHAT:RECEIVER_IDE_FILE_STATE` | getIdeFileState / getIdeaKnowledgefileMessage | IDE 文件状态 |
| `CHAT:RECEIVER_DOC_KNOWLEDGE_LIST` | getDocKnowledgeInfo | 文档知识库 |
| `CHAT:RECEIVER_CODE_KNOWLEDGE_LIST` | getCodeKnowledgeInfo | 代码知识库 |
| `CHAT:RECEIVER_HISTORY_LIST` | — | 历史记录 |
| `CHAT:SEND_OPEN_DIR_LIST` | getOpenDirListInfo | 打开目录列表 |
| `CHAT:SEND_VALID_WEBSITE_RESULT` | — | 网站验证结果 |
| `CHAT:CHOOSE_FILE` | — | 选择文件 |
| `CHAT:GET_FEEDBACK_LIST` | — | 反馈列表 |
| `CHAT_TALK:RECEIVER_RECOMMEND_GAMEPLAY` | — | 推荐玩法 |
| `CODE_CHECK:GET_CODE_CHECK_LIST` | getCodeSearchCodeList | 代码检查列表 |
| `CODE_CHECK:UPDATE_CODE_CHECK` | — | 更新代码检查 |
| `CODE_REVIEW:RECEIVER_CODE_REVIEW` | — | 代码评审结果 |
| `CODE_REVIEW:RECEIVER_CHANGE_RESULT` | — | 变更结果 |
| `CODE_REVIEW:RECEIVER_PAGE_INIT` | — | 页面初始化 |
| `CODE_SEARCH:GET_CODESEARCH_CODE_LIST` | getCodeSearchCodeList | 代码搜索结果 |
| `CODE_SEARCH:GET_CODESEARCH_REPOSITORY_LIST` | getCodeSearchRepositoryList | 仓库列表 |
| `CODE_SEARCH:GET_CODESEARCH_LANGUAGE_LIST` | getCodeSearchLanguageList | 语言列表 |
| `COMMON:OPEN_PAGE` | — | 打开页面 |
| `COMMON:PLUGIN_BASE_INFO` | — | 插件基本信息 |
| `COMMON:SHOW_MESSAGE_IN_WEB` | — | 显示消息 |
| `GIT:CODE_KNOWLEDGE_REPO_STATUS` | — | 知识库状态 |
| `GIT:CODE_KNOWLEDGE_RE_INDEX` | — | 知识库重建索引 |
| `GIT:REPO_AUTHORIZE` | — | 仓库授权 |
| `GIT:STATUS` | — | Git 状态 |
| `LOGIN:GO_LOGIN` | — | 前往登录 |
| `LOGIN:LOGIN_SUCCEED` | receiveLoginSuccess | 登录成功 |
| `LOGIN:RECEIVER_LOGIN_IFRAME_SRC` | receiveLoginIframeSrc | 登录 iframe |
| `SETTING:GET_CONFIG` | getSettingInfo | 获取配置 |
| `SETTING:CHANGE_THEME` | changeTheme | 切换主题 |
| `SETTING:GET_CAN_OPEN_CODE_ENHANCE` | — | 代码增强开关 |
| `SETTING:RECEIVE_REPO_STATUS` | — | 仓库状态 |
| `SETTING:SEND_SHOW_OPERATE_GUIDANCE` | receiveOperateGuideData | 操作引导 |
| `SQL_CHAT:GET_CONVERSATION` | — | SQL 对话 |
| `SQL_CHAT:UPDATE_CONVERSATION_LIST` | — | SQL 对话列表 |
| `SQL_CHAT:RECEIVE_LINK_TEST` | — | 连接测试 |
| `SQL_CHAT:RECEIVE_SAVE` | — | 保存 |
| `SQL_CHAT:RECEIVE_SOURCE_LIST` | sqlReceiveSourceList | 数据源列表 |
| `SQL_CHAT:RECEIVE_SOURCE_TYPES` | — | 数据源类型 |
| `SQL_CHAT:RECEIVE_TABLE_LIST` | — | 表列表 |
| `SQL_CHAT:SOURCE_REFRESH_SAVE` | — | 刷新保存 |
| `UNIT_TEST:FUNCTION_LIST` | — | 函数列表 |
| `UNIT_TEST:GET_ALL_CODE_FILE` | receiveSaveMessage | 获取代码文件 |
| `UNIT_TEST:GET_UT_INFO` | addUTContent | 单测信息 |
| `UNIT_TEST:GET_METHOD_CASE` | receiveClassCaseData | 方法用例 |
| `UNIT_TEST:GET_CASE_CODE` | receiveCaseCode | 用例代码 |
| `BATCH_UNIT_TEST:GET_TASK_LIST` | — | 批量单测任务列表 |
| `BATCH_UNIT_TEST:MESSAGE` | — | 批量单测消息 |
| `BATCH_UNIT_TEST:REFRESH_TASK_DOWNLOAD_STATUS` | — | 批量单测下载状态 |

### 5.4 JS→Java 消息发送

JS→Java 通过 `sendMsgToIdea(type, value)` 发送，type 为字符串常量。从源码分析发现，消息类型与 Java→JS 的类型命名规则一致（`MODULE:ACTION` 格式），但具体的 JS→Java 发送类型在 minified 代码中较难提取，因为函数名被压缩为变量引用。

## 6. 权限系统 (PermissionCodeEnum)

### 6.1 权限代码完整列表

| 权限代码 | 频次 | 说明 |
|---------|------|------|
| `unitTest` | 4 | 单元测试 |
| `functionSplit` | 4 | 函数拆分 |
| `codeOptimization` | 4 | 代码优化 |
| `iFlyTest` | 4 | iFlyTest 助理 |
| `iFlyDev` | 4 | iFlyDev 助理 |
| `codeKnowledgeBase` | 3 | 代码知识库 |
| `iFlyDBA` | 3 | iFlyDBA 助理 |
| `iFlyPm` | 3 | iFlyPM 助理 |
| `iFlyOps` | 3 | iFlyOps 助理 |
| `docComment` | 2 | 文档注释 |
| `inlineComment` | 2 | 行注释 |
| `codeExplain` | 2 | 代码解释 |
| `openInlineChat` | 2 | 内联聊天 |
| `sqlGenerate` | 2 | SQL 生成 |
| `sqlOptimize` | 2 | SQL 优化 |
| `iFlyMate` | 2 | iFlyMate 助理 |
| `codeSearch` | 1 | 代码搜索 |
| `codeCheck` | 1 | 代码检查 |
| `batchUnitTest` | 1 | 批量单测 |
| `codeReview` | 1 | 代码评审 |
| `demandTest` | 1 | 需求测试 |
| `generateTestCase` | 1 | 测试用例生成 |
| `demandAnalysis` | 1 | 需求分析 |
| `demandSplit` | 1 | 需求拆分 |
| `chatSQLGeneration` | 1 | SQL 对话生成 |
| `chatSQLOptimization` | 1 | SQL 对话优化 |
| `failureAnalysis` | 1 | 故障分析 |
| `anomalyDetection` | 1 | 异常检测 |
| `focus` | 1 | 关注 |
| `knowledge` | 1 | 知识 |
| `currentOpenFile` | 1 | 当前打开文件 |
| `openFileList` | 1 | 打开文件列表 |
| `localRepo` | 1 | 本地仓库 |
| `docKnowledge` | 1 | 文档知识 |
| `codeKnowledge` | 1 | 代码知识 |
| `webSearch` | 1 | 网络搜索 |
| `webDoc` | 1 | 网络文档 |
| `database` | 1 | 数据库 |
| `directory` | 1 | 目录 |

### 6.2 实验开关 (ExperimentEnum)

| 开关 | 说明 |
|------|------|
| `openFunctionSplit` | 函数拆分功能 |
| `openCodeOptimization` | 代码优化功能 |
| `openInlineChat` | 内联聊天功能 |
| `openIFlyTest` | iFlyTest 助理 |
| `openIFlyDBA` | iFlyDBA 助理 |
| `openIFlyDev` | iFlyDev 助理 |
| `openIFlyPm` | iFlyPM 助理 |
| `openIFlyOps` | iFlyOps 助理 |

### 6.3 聊天类型 (ChatTypeEnum)

| 类型 | 说明 |
|------|------|
| `codeExplain` | 代码解释 |
| `docComment` | 文档注释 |
| `inlineComment` | 行注释 |
| `functionSplit` | 函数拆分 |
| `codeOptimization` | 代码优化 |
| `unitTest` | 单元测试 |
| `quickFix` | 快速修复 |
| `demandTest` | 需求测试 |
| `generateTestCase` | 测试用例生成 |
| `demandAnalysis` | 需求分析 |
| `demandSplit` | 需求拆分 |
| `chatSQLGeneration` | SQL 生成 |
| `chatSQLOptimization` | SQL 优化 |
| `helpDoc` | 帮助文档 |
| `anomalyDetection` | 异常检测 |
| `failureAnalysis` | 故障分析 |
| `focus` | 关注 |

### 6.4 聊天视图类型 (ChatViewTypeEnum)

| 类型 | 说明 |
|---------|------|
| `chat` | 智能问答 |
| `sqlChat` | SQL 对话 |

### 6.5 SQL 聊天类型 (SqlChatTypeEnum)

| 类型 | 说明 |
|---------|------|
| `sqlGenerate` | SQL 生成 |
| `sqlOptimize` | SQL 优化 |

### 6.6 IDE 类型 (IdeaEnum)

| 类型 | 说明 |
|---------|------|
| `IDEA` | IntelliJ IDEA |
| `VSCODE` | VS Code |
| `ECLIPSE` | Eclipse |

### 6.7 插件版本 (PluginSenseEnum)

| 类型 | 说明 |
|---------|------|
| `consumer` | 消费者版 |
| `insider` | 内部版 |

### 6.8 下拉菜单 (DropDownEnum)

| 类型 | 说明 |
|---------|------|
| `ASSISTANT` | 助理选择 |

### 6.9 底部栏 (BarEnum)

| 类型 | 说明 |
|---------|------|
| `ToolBox` | 工具箱 |
| `More` | 更多 |
| `FeedBack` | 反馈 |
| `NewChat` | 新对话 |
| `Record` | 历史 |

## 7. Element UI 组件使用

### 7.1 使用的 Element UI 组件 (70+)

| 类别 | 组件 |
|------|------|
| **基础** | ElButton, ElButtonGroup, ElLink, ElIcon, ElTag |
| **表单** | ElForm, ElFormItem, ElInput, ElInputNumber, ElSelect, ElOption, ElOptionGroup, ElRadio, ElRadioButton, ElRadioGroup, ElCheckbox, ElCheckboxButton, ElCheckboxGroup, ElSwitch, ElSlider, ElTimePicker, ElTimeSelect, ElDatePicker, ElUpload, ElUploadDrag, ElUploadList, ElCascader, ElCascaderPanel, ElCascaderMenu, ElColorPicker, ElTransfer, ElTransferPanel |
| **数据** | ElTable, ElTableColumn, ElTableBody, ElTableHeader, ElTableFooter, ElTableRow, ElTableFilterPanel, ElPagination, ElPager, ElTree, ElTreeNode, ElStatistic, ElDescriptions, ElDescriptionsItem, ElDescriptionsRow |
| **导航** | ElMenu, ElMenuItem, ElMenuItemGroup, ElSubmenu, ElTabs, ElTabPane, ElTabBar, ElTabNav, ElBreadcrumb, ElBreadcrumbItem, ElPageHeader, ElSteps, ElStep |
| **通知** | ElAlert, ElDialog, ElDrawer, ElPopover, ElPopconfirm, ElTooltip, ElMessage |
| **布局** | ElContainer, ElHeader, ElMain, ElAside, ElFooter, ElRow, ElCol, ElDivider |
| **其他** | ElCard, ElCarousel, ElCarouselItem, ElCollapse, ElCollapseItem, ElTimeline, ElTimelineItem, ElProgress, ElSpinner, ElSkeleton, ElSkeletonItem, ElAvatar, ElBadge, ElBacktop, ElEmpty, ElResult, ElScrollbar, ElCalendar, ElImage, ElDropdown, ElDropdownMenu, ElDropdownItem, ElAutocomplete, ElRate |

## 8. 中文 UI 字符串

### 8.1 导航与页面名称

| 字符串 | 说明 |
|--------|------|
| 主页 | 首页 |
| 智能问答 | 聊天页面 |
| 历史记录 | 对话历史 |
| 代码搜索 | 语义搜索 |
| 工具箱 | 功能入口 |
| 代码检查 | 代码质量 |
| 批量单测 | 批量单元测试 |
| 代码评审 | Git 评审 |
| 登录 | 登录页 |
| 设置 | 配置页 |
| 关于 | 版本信息 |
| 操作引导 | 新手引导 |

### 8.2 助理名称

| 字符串 | 说明 |
|--------|------|
| 通用助理 | 默认助理 |
| iFlyDev助理 — 更懂工程和业务【企业版】 | 开发助理 |
| iFlyTest助理 — 更懂测试【企业版】 | 测试助理 |
| iFlyPM助理 — 更懂产品和需求【企业版】 | 产品助理 |
| iFlyOps助理 — 更懂运维【企业版】 | 运维助理 |

### 8.3 设置项

| 字符串 | 说明 |
|--------|------|
| 开启代码生成增强 | 代码增强开关 |
| 开启代码生成增强，会提高代码生成的准确性，但是可能会拉长生成时长 | 增强说明 |
| 暂无可用代码知识库，请去知识管理平台添加 | 知识库提示 |
| 开启代码补全提示 | 补全开关 |
| 启用后，回车、停顿等操作均会触发代码补全；若关闭，可通过"Alt/Option + \"手动触发代码补全 | 补全说明 |
| 自定义停顿触发时间（单位：毫秒） | 停顿时间 |
| 聊天框发送消息按键配置 | 发送按键 |
| 智能问答回复快捷键设置 | 回复快捷键 |
| 行间快捷工具类型配置 | 行间工具 |
| 编辑区沉浸式的功能入口样式设置 | 沉浸式入口 |
| 开启函数注释快捷展示 | 函数注释 |
| 开启行间注释快捷展示 | 行注释 |
| 开启代码解释快捷展示 | 代码解释 |
| 开启代码优化(Beta)快捷展示 | 代码优化 |
| 开启单元测试快捷展示 | 单元测试 |
| 开启函数拆分功能 | 函数拆分 |
| 开启代码优化功能 | 代码优化 |
| 开启inlineChat功能 | 内联聊天 |
| 更新设置 | 更新 |
| 自动更新新版本 | 自动更新 |
| 知识库配置 | 知识库 |
| commit message配置 | Git 提交信息 |

### 8.4 通用 UI

| 字符串 | 说明 |
|--------|------|
| 确定 | 确认按钮 |
| 取消 | 取消按钮 |
| 清空 | 清空 |
| 加载中 | 加载状态 |
| 加载失败 | 错误状态 |
| 暂无数据 | 空状态 |
| 无匹配数据 | 搜索无结果 |
| 请选择 | 选择提示 |
| 提示 | 提示标题 |
| 无权限！请登录后重试 | 权限错误 |
| 新建对话 | 新对话 |
| 更多 | 更多选项 |
| 反馈 | 反馈 |
| SQL生成 | SQL 生成 |
| SQL优化 | SQL 优化 |
| 单元测试 | 单元测试 |
| 星火13B | 模型名称 |

### 8.5 SQL Chat 专用

| 字符串 | 说明 |
|--------|------|
| 切换数据库将清空已选数据表 | 数据库切换提示 |
| 切换数据源将清空已选数据表 | 数据源切换提示 |
| 当前代码库未建立索引 | 知识库未索引 |
| 思考中 | AI 思考状态 |
| 思考过程 | 思考过程展示 |
| 停止生成 | 停止按钮 |
| 出错了 | 错误提示 |
| 参考资料 | 参考资料标题 |
| 向上滚动 | 滚动按钮 |
| 向下滚动 | 滚动按钮 |
| 展开 | 展开 |
| 删除 | 删除 |
| 刷新 | 刷新 |
| 使用 | 使用 |
| 已使用 | 已使用 |
| 已选择 | 已选择 |
| 引用 | 引用 |
| 打开 | 打开 |
| 信息收集 | 信息收集 |

## 9. 关键发现

1. **Vue.js 2.7.14 而非 Vue 3**: WebView 使用 Vue 2.7.14（兼容 Composition API），而非之前文档记录的 Vue 3。这是一个重要修正。

2. **3 个 Pinia Stores 而非 7 个**: 实际只有 `chat`、`codeCheck`、`sql` 三个 Pinia Store。Git Review、Code Search、Settings、Unit Test 可能使用 Vue 2 Options API 的本地状态。

3. **三 IDE Bridge**: 支持 IDEA、VSCode、Eclipse 三种 IDE 的 JS↔Java Bridge，每种 IDE 有不同的通信机制：
   - IDEA: `window.myObject.sendMessage()` + `window.receiveData()`
   - VSCode: `vscode.postMessage()` + `window.addEventListener("message")`
   - Eclipse: `window.sendMessage()` + `window.receiveData()`

4. **50+ Java→JS 消息类型**: 完整提取了 `handlerReceivedMsg` switch/case 中的所有消息类型，覆盖 CHAT、CODE_CHECK、CODE_REVIEW、CODE_SEARCH、COMMON、GIT、LOGIN、SETTING、SQL_CHAT、UNIT_TEST、BATCH_UNIT_TEST 共 11 个模块。

5. **39 个权限代码**: PermissionCodeEnum 包含 39 个权限值，覆盖所有功能模块和知识来源。

6. **8 个实验开关**: ExperimentEnum 控制 8 个功能的开关状态，说明部分功能是灰度发布。

7. **17 个聊天类型**: ChatTypeEnum 包含 17 种聊天类型，对应不同的 AI 功能。

8. **Element UI 70+ 组件**: 使用了 Element UI 的 70+ 组件，覆盖表单、数据、导航、通知、布局等所有类别。

9. **Mermaid 20+ 图类型**: 支持 architectureDiagram、blockDiagram、c4Diagram、flowDiagram、ganttDiagram、gitGraphDiagram、journeyDiagram、kanban、mindmap、pieDiagram、quadrantDiagram、requirementDiagram、sankeyDiagram、sequenceDiagram、stateDiagram、timeline、xychartDiagram 等 20+ 种图表。

10. **156 个中文 UI 字符串**: 提取了 156 个独特的中文 UI 字符串，覆盖导航、助理名称、设置项、通用 UI、SQL Chat 等领域。

11. **5 个助理角色**: 通用助理 + iFlyDev + iFlyTest + iFlyPM + iFlyOps，其中 4 个标注【企业版】。

12. **模型名称 "星火13B"**: 确认了默认使用的 AI 模型是星火13B。

13. **Cody 源码泄露**: 主 bundle 中包含 已脱敏 路径字符串，说明开发环境参考了 Sourcegraph Cody 的代码。