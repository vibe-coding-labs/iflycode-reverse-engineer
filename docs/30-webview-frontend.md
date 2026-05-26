# 30 WebView 前端架构分析

> 基于 webview/ 目录静态分析 — Vite 构建的 Vue 3 SPA

## 技术栈

```
框架: Vue 3 + Vite
状态管理: Pinia (7 stores)
UI 库: Element Plus (ElButton, ElInput, ElDialog, ElSelect, ...)
路由: Vue Router (14 routes)
国际化: vue-i18n (中文/英文)
图表: Mermaid (流程图/架构图渲染)
代码高亮: HighlightJS
数学公式: KaTeX
Markdown: marked (自定义渲染器)
构建工具: Vite (代码分割 + 懒加载)
```

## 文件结构

```
webview/
├── index.html              — SPA 入口 (491 bytes)
├── favicon.svg             — 网站图标
├── icon.svg                — 应用图标
└── assets/
    ├── index-f0296668.js   — 主应用代码 (3.76 MB, 96077 lines)
    ├── index-9c2209c9.js   — HighlightJS 核心 (1.52 MB)
    ├── sendMsgMode-8b767ec0.js — 聊天消息组件 (286 KB)
    ├── tips-f0105b0c.js    — Markdown/Tips 渲染 (81 KB)
    ├── layout-65cf677b.js  — DAG 布局算法 (59 KB)
    ├── form-c049a1e0.js    — Element Plus Form (36 KB)
    ├── index-07af936a.js   — 懒加载模块 (82 KB)
    ├── index-6b71b008.js   — 懒加载模块 (52 KB)
    ├── index-3c7ef179.js   — 懒加载模块 (42 KB)
    ├── index-4639cb2d.js   — 懒加载模块 (29 KB)
    ├── index-df569310.js   — 懒加载模块 (24 KB)
    ├── index-1aaa16d8.js   — 懒加载模块 (34 KB)
    ├── index-fe5ffc8f.js   — 懒加载模块 (12 KB)
    ├── ideaUtil-11ab0730.js — IDEA Bridge (535 bytes)
    ├── vscodeUtil-49d49699.js — VSCode Bridge (536 bytes)
    ├── eclipseUtil-82d0751a.js — Eclipse Bridge (476 bytes)
    ├── analysis-5564da2b.js — CodeOperateTypes 枚举 (501 bytes)
    ├── toolBox-ea934310.js — 工具箱 Store (3 KB)
    ├── response-feedback-4f74eae4.js — 反馈组件 (8 KB)
    ├── code-render-78c87ad5.js — 代码渲染 (7 KB)
    ├── logo-dde35d99.js    — Logo 资源 (5 KB)
    ├── getDocumentLanguage-4b7a17eb.js — 语言检测 (1 KB)
    ├── init-cc95ec8e.js    — D3 初始化 (258 bytes)
    ├── channel-cc5a1259.js — 颜色通道工具 (211 bytes)
    ├── [mermaid/katex/dag/cytoscape 等] — 图表渲染库
    ├── [CSS 文件 15+]      — 样式模块
    ├── [图片资源 20+]      — GIF/SVG/PNG 图标和背景
    └── [字体文件 4]        — Element/iconfont 字体
```

## Bridge 通信机制 — 核心

### 三平台适配

```
IDE 平台检测: IdeaEnum { IDEA, VSCODE, ECLIPSE }
运行时选择: switch ("IDEA") → 动态加载对应 util

IDEA Bridge (ideaUtil):
  JS→Java: window.myObject.sendMessage(JSON.stringify({type, value}))
  Java→JS: window.receiveData = function(data) { handlerReceivedMsg(msg.type, msg.value) }

VSCode Bridge (vscodeUtil):
  JS→Java: vscode.postMessage({type, value: JSON.stringify(value)})
  Java→JS: window.addEventListener("message", (event) => { handlerReceivedMsg(msg.type, msg.value) })

Eclipse Bridge (eclipseUtil):
  JS→Java: window.sendMessage(JSON.stringify({type, value: JSON.stringify(value)}))
  Java→JS: window.receiveData = function(data) { handlerReceivedMsg(msg.type, msg.value) }
```

### 统一接口

```javascript
// 统一发送接口
async function sendMsgToIdea(type, value = "") {
  const result = await util; // util = ideaUtil/vscodeUtil/eclipseUtil
  result.sendMsgToIdeaHandler(type, value);
}

// 统一接收接口
function handlerReceivedMsg(type, value) {
  switch (type) {
    case "CHAT:GET_USER_INFO": messageHandler.receiveUserInfo(value); break;
    // ... 66 case statements
  }
}
```

### 消息格式

```
发送格式 (JS→Java):
  { type: "CATEGORY:ACTION", value: <any> }

接收格式 (Java→JS):
  { type: "CATEGORY:ACTION", value: <any> }

value 字段:
  - 字符串类型: 直接传递
  - 对象类型: IDEA 直接传递, VSCode/Eclipse JSON.stringify 后传递
```

## Bridge 消息类型完整映射

### JS→Java (WebView 发送到 IDE)

| 消息类型 | 功能 | value 数据 |
|---------|------|-----------|
| `CHAT:SEND_MSG` | 发送聊天消息 | `{ intelligent: [...], params: {...} }` |
| `CHAT:SET_MODEL` | 设置模型 | `command` (模型 ID) |
| `CHAT:REFRESH_MODEL` | 刷新模型列表 | 无 |
| `CHAT:NEW_CHAT` | 新建对话 | 无 |
| `CHAT:STOP_RESPONSE` | 停止响应 | 无 |
| `CHAT:DELETE_HISTORY_ITEM` | 删除历史条目 | 条目 ID |
| `CHAT:DELETE_HISTORY_ITEM_ALL` | 清空所有历史 | 无 |
| `CHAT:GET_HISTORY_LIST` | 获取历史列表 | 无 |
| `CHAT:CHOOSE_FILE` | 选择文件上传 | 无 |
| `CHAT:GET_IDE_FILE_STATE` | 获取 IDE 文件状态 | 无 |
| `CHAT:GET_CODE_KNOWLEDGE_LIST` | 获取代码知识库 | 无 |
| `CHAT:GET_DOC_KNOWLEDGE_LIST` | 获取文档知识库 | 无 |
| `CHAT:GET_OPEN_DIR_LIST` | 获取打开目录列表 | 无 |
| `CHAT:VALID_WEBSITE` | 验证网站 | `websiteForm.url` |
| `SQL_CHAT:SEND_MSG` | SQL 聊天消息 | `{ intelligent: [...], params: { sqlInfo } }` |
| `SQL_CHAT:SOURCE_LIST` | 获取数据源列表 | 无 |
| `SQL_CHAT:TABLE_LIST` | 获取数据表列表 | `requestParams` |
| `COMMON:PAGE_READY` | 页面就绪 | 无 |
| `COMMON:OPEN_URL` | 打开 URL | `{ type: "FEEDBACK_URL" }` |
| `GIT:GET_STATUS` | 获取 Git 状态 | 无 |
| `LOGIN:INIT` | 初始化登录 | 无 |
| `LOGIN:LOGOUT` | 登出 | 无 |
| `SETTING:SAVE_SHOW_OPERATE_GUIDANCE` | 保存操作引导设置 | `{ isShowOperateGuide: false }` |
| `JSLOGER` | 日志上报 | `{ permissionCodeList }` |

### Java→JS (IDE 发送到 WebView)

| 消息类型 | 功能 | Handler 方法 |
|---------|------|-------------|
| `CHAT:GET_USER_INFO` | 用户信息 | `receiveUserInfo` |
| `CHAT:GET_CONVERSATION` | 会话数据 | `getConversation` |
| `CHAT:UPDATE_CONVERSATION_LIST` | 更新会话列表 | `updateConversationList` |
| `CHAT:UPDATE_SELECT_CODE` | 更新选中代码 | `updateSelectCode` |
| `CHAT:SET_SEND_MESSAGE_TYPE` | 设置发送类型 | `setSendMessageType` |
| `CHAT:GET_MODEL_LIST` | 模型列表 | `getModelList` |
| `CHAT:PREDICT` | 预测建议 | `updatePredictList` |
| `CHAT:RECEIVER_IDE_FILE_STATE` | IDE 文件状态 | `getIdeFileState` / `getIdeaKnowledgefileMessage` |
| `CHAT:RECEIVER_DOC_KNOWLEDGE_LIST` | 文档知识库 | `getDocKnowledgeInfo` |
| `CHAT:RECEIVER_CODE_KNOWLEDGE_LIST` | 代码知识库 | `getCodeKnowledgeInfo` |
| `CHAT:SEND_OPEN_DIR_LIST` | 打开目录列表 | `getOpenDirListInfo` |
| `CHAT:RECEIVER_HISTORY_LIST` | 历史列表 | `receiveHistoryList` |
| `CHAT:GET_FEEDBACK_LIST` | 反馈列表 | `receiveFeedBackCheckList` |
| `CHAT:CHOOSE_FILE` | 文件选择结果 | `receiveUploadFile` |
| `CHAT:SEND_VALID_WEBSITE_RESULT` | 网站验证结果 | `getValidWebsiteResult` |
| `CHAT:AGENT_ERROR` | Agent 错误 | `hanldeAgentError` |
| `CHAT_TALK:RECEIVER_RECOMMEND_GAMEPLAY` | 推荐玩法 | `receiveRecommendList` |
| `LOGIN:RECEIVER_LOGIN_IFRAME_SRC` | 登录 iframe | `receiveLoginIframeSrc` |
| `LOGIN:LOGIN_SUCCEED` | 登录成功 | `receiveLoginSuccess` |
| `LOGIN:GO_LOGIN` | 跳转登录 | `goLoginClickPage` |
| `SETTING:GET_CONFIG` | 配置信息 | `getSettingInfo` |
| `SETTING:CHANGE_THEME` | 主题变更 | `changeTheme` |
| `SETTING:GET_CAN_OPEN_CODE_ENHANCE` | 代码增强开关 | `changeCodeEnhanceEnabled` |
| `SETTING:RECEIVE_REPO_STATUS` | 仓库状态 | `receiveRepoStatus` |
| `SETTING:SEND_SHOW_OPERATE_GUIDANCE` | 操作引导数据 | `receiveOperateGuideData` |
| `COMMON:OPEN_PAGE` | 打开页面 | `openPage` / `UtTestOpenPage` / `UtTestBankOpenPage` |
| `COMMON:SHOW_MESSAGE_IN_WEB` | 显示消息 | `showMessageInWeb` |
| `COMMON:PLUGIN_BASE_INFO` | 插件基础信息 | `getPluginBaseInfo` |
| `GIT:STATUS` | Git 状态 | `updateGitStatusList` |
| `GIT:REPO_AUTHORIZE` | 仓库授权 | (via handlerReceivedMsg) |
| `GIT:CODE_KNOWLEDGE_REPO_STATUS` | 知识库仓库状态 | (via handlerReceivedMsg) |
| `GIT:CODE_KNOWLEDGE_RE_INDEX` | 知识库重新索引 | (via handlerReceivedMsg) |
| `CODE_REVIEW:RECEIVER_PAGE_INIT` | 代码评审初始化 | `receiveCodeReviewInit` |
| `CODE_REVIEW:RECEIVER_CODE_REVIEW` | 代码评审结果 | `receiveCodeReview` |
| `CODE_REVIEW:RECEIVER_CHANGE_RESULT` | 变更结果 | `receiveChangeResult` |
| `CODE_CHECK:GET_CODE_CHECK_LIST` | 代码检查列表 | `getCodeCheckList` |
| `CODE_CHECK:UPDATE_CODE_CHECK` | 更新代码检查 | `updateCodeCheckList` |
| `CODE_SEARCH:GET_CODESEARCH_CODE_LIST` | 代码搜索结果 | `getCodeSearchCodeList` |
| `CODE_SEARCH:GET_CODESEARCH_REPOSITORY_LIST` | 仓库搜索结果 | `getCodeSearchRepositoryList` |
| `CODE_SEARCH:GET_CODESEARCH_LANGUAGE_LIST` | 语言搜索结果 | `getCodeSearchLanguageList` |
| `SQL_CHAT:RECEIVE_SOURCE_TYPES` | 数据源类型 | `sqlReceiveSourceTypes` |
| `SQL_CHAT:RECEIVE_LINK_TEST` | 连接测试 | `sqlReceiveLinkTest` |
| `SQL_CHAT:RECEIVE_SAVE` | 保存结果 | `sqlReceiveSave` |
| `SQL_CHAT:SOURCE_REFRESH_SAVE` | 数据源刷新保存 | `sqlSourceRefreshReceiveSave` |
| `SQL_CHAT:RECEIVE_SOURCE_LIST` | 数据源列表 | `sqlReceiveSourceList` / `talkSqlReceiveSourceList` |
| `SQL_CHAT:RECEIVE_TABLE_LIST` | 数据表列表 | `sqlReceiveTableList` / `talkSqlReceiveTableList` |
| `SQL_CHAT:GET_CONVERSATION` | SQL 会话 | `sqlGetConversation` |
| `SQL_CHAT:UPDATE_CONVERSATION_LIST` | SQL 会话列表更新 | `sqlUpdateConversationList` |
| `UNIT_TEST:GET_UT_INFO` | 单测信息 | `addUTContent` |
| `UNIT_TEST:GET_METHOD_CASE` | 方法用例 | `receiveClassCaseData` |
| `UNIT_TEST:GET_CASE_CODE` | 用例代码 | `receiveCaseCode` |
| `UNIT_TEST:GET_ALL_CODE_FILE` | 所有代码文件 | `receiveSaveMessage` |
| `UNIT_TEST:FUNCTION_LIST` | 函数列表 | `unitTestFunctionList` |
| `UNIT_TEST:RECEIVE_FUNCTION_CASE` | 函数用例 | `unitTestFunctionCase` / `unitTestScrollTo` |
| `UNIT_TEST:RECEIVE_FUNCTION_CASE_CODE` | 函数用例代码 | `unitTestFunctionCode` / `unitTestScrollTo` |
| `BATCH_UNIT_TEST:GET_TASK_LIST` | 批量单测任务 | `getMultiTestTaskList` |
| `BATCH_UNIT_TEST:MESSAGE` | 批量单测消息 | `multiTestMessage` |
| `BATCH_UNIT_TEST:REFRESH_TASK_DOWNLOAD_STATUS` | 刷新下载状态 | `multiTestRefreshTaskDownloadStatus` |
| `UNIT_TEST_BANK:RECEIVE_FUNCTION` | 单测库函数 | `receiveTestBankFunctionData` |
| `UNIT_TEST_BANK:RECEIVE_DATA` | 单测库数据 | `receiveTestBankData` |
| `UNIT_TEST_BANK:RESPONSE_SAVE` | 单测库保存 | `receiveTestBankSave` |
| `UNIT_TEST_BANK:IDEA_STOP` | 单测库停止 | `stopUnitTestBank` |
| `USER:PERMISSION_LIST` | 权限列表 | `getPermissionCodeList` |

## 路由系统

```
Vue Router 配置:
  /login          → loginPage       — 登录页
  /chat-view      → chatView        — 智能问答主页
  /code-review    → codeReview      — 代码评审
  /code-check     → codeCheck       — 代码检查
  /code-search    → codeSearch      — 代码搜索
  /unit-test      → unitTest        — 单元测试
  /unit-test-bank → unitTestBank    — 单测题库
  /multi-test     → multiTest       — 批量单测
  /sql-page       → sqlPage         — SQL 生成/优化
  /setting-page   → settingPage     — 设置页
  /about-page     → aboutPage       — 关于页
  /history-record → historyRecord   — 历史记录
  /function-guide → functionGuide   — 功能引导
  /tool-box       → toolBox         — 工具箱
  /web-view/:name → (动态路由)      — WebView 子页面
```

## Pinia Store 体系

```
7 个 Pinia Store:

1. chatStore ("chat")
   状态:
     conversationId: string       — 当前会话 ID
     conversation: {              — 会话数据
       sessionId: string
       conversationList: array    — 对话消息列表
     }
     command: string              — 当前命令
     intelligent: array           — 智能模式数据
     endTime: number              — 结束时间
     modelId: string              — 模型 ID
     modelName: string            — 模型名称 (星火13B/38B/65B/70B/7B)
     response: string             — AI 响应文本
     startTime: number            — 开始时间
   Actions:
     sendMsg, setModel, stopResponse, etc.

2. loginStore ("login")
   — 登录状态管理

3. settingStore ("setting")
   — 设置配置管理
   配置项:
     代码建议风格 (单行模式/流式输出/一次性输出)
     代码输出方式 (文字展示/图标展示)
     代码补全禁用语言
     代码生成设置
     聊天框发送消息按键配置 (Enter/Shift+Enter)
     自动更新新版本
     inlineChat 功能开关
     代码优化功能开关
     代码生成增强开关
     代码补全提示开关
     代码解释快捷展示开关
     函数拆分功能开关
     函数注释快捷展示开关
     单元测试快捷展示开关
     行间注释快捷展示开关
     行间快捷工具类型配置
     智能问答回复快捷键设置
     智能问答默认语言 (中文/英文/自动)
     自定义停顿触发时间 (毫秒)
     编辑区沉浸式功能入口样式设置

4. permissionStore ("permission")
   — 权限管理
   权限码: PermissionCodeEnum

5. layoutStore ("layout")
   — 布局状态管理

6. functionStore ("function")
   — 功能状态管理

7. toolBoxStore ("toolBox")
   — 工具箱列表
   工具项: SQL生成/优化 (/sql-page)
```

## 模型列表

```
星火模型选项 (从 case 语句提取):
  星火7B   — SparkDesk Lite
  星火13B  — SparkDesk V2
  星火38B  — SparkDesk Pro
  星火65B  — SparkDesk Max
  星火70B  — SparkDesk Ultra
```

## 助手类型 (Assistant)

```
从 UI 字符串提取:

通用助理   — 计算机领域通用助理
研发助理   — 更懂工程和业务
产品助理   — 更懂产品和需求
测试助理   — 更懂测试
运维助理   — 更懂运维
数据库助理 — 更懂数据库设计和操作

企业版标注:
  研发助理【企业版】
  产品助理【企业版】
  测试助理【企业版】
  运维助理【企业版】
  数据库助理【团队及企业版】
```

## 命令映射 (CODE:xxx)

```
从 sendMsgMode 提取的 CODE 命令:

CODE:EXPLAIN          — 代码解释
CODE:COMMENT          — 函数注释
CODE:INLINE_COMMENT   — 行间注释
CODE:OPTIMIZE         — 代码优化
CODE:SPLIT            — 函数拆分
CODE:REVIEW           — 代码评审
CODE:DEBUG            — 故障分析
CODE:GENERATE_TEST_CASE — 生成测试用例
CODE:DEMAND_ANALYSIS  — 需求分析
CODE:DEMAND_SPLITTING — 需求拆分
CODE:DEMAND_TEST      — 需求测试
CODE:FAULT_ANALYSIS   — 错误分析
CODE:anomalyDetection — 异常检测
CODE:focus            — 代码聚焦
CODE:HELP             — 帮助

SQL 命令:
  SQL:GENERATE       — SQL 生成
  SQL:OPTIMIZE       — SQL 优化
  SQL:GENERATE_TALK  — SQL 生成(对话)
  SQL:OPTIMIZE_TALK  — SQL 优化(对话)

TALK 命令:
  TALK:ASK           — 智能问答
  TALK:INTELLIGENT   — 智能模式
  TALK:KNOWLEDGE     — 知识库问答

TEST 命令:
  TEST:UNITTEST      — 单元测试
  TEST:OTHER         — 其他测试
```

## 知识库类型

```
KnowledgeEnum:
  Chat_CurrentOpenFile — 当前打开文件
  Chat_Directory       — 目录
  Chat_Database        — 数据库
  Chat_LocalRepo       — 本地代码库
  Chat_CodeKnowledge   — 代码知识库
  Chat_DocKnowledge    — 文档知识库
  Chat_OpenFileList    — 打开文件列表
  Chat_WebDoc          — 网页文档
  Chat_WebSearch       — 联网搜索
```

## 代码操作类型 (CodeOperateTypes)

```
CodeOperateTypes 枚举:
  ACTION_NEW              — 新建文件
  ACTION_DIFF             — Diff 对比
  ACTION_INSERT           — 插入代码
  ACTION_COPY             — 复制代码
  ACTION_ACCEPT           — 接受修改
  ACTION_ACCEPT_INLINE_COMMENT — 接受行间注释
```

## 支持的语言 (代码补全)

```
从 case 语句提取的编程语言:
  c, c++, cpp, csharp, css, cuda-cpp, go, html, java,
  javascript, javascriptreact, less, objective-c, objective-cpp,
  php, python, r, rust, sass, scss, shell, shellscript,
  sql, tex, typescript, typescriptreact, vue
```

## Chat 消息数据结构

```javascript
// 对话消息结构 (从 chatStore 注释提取)
{
  command: 'TALK:INTELLIGENT',    // 命令类型
  endTime: 1730191488722,         // 结束时间
  id: '43d8fe71...',              // 消息 ID
  intelligent: [                  // 智能模式数据
    { type: 'assistant', value: 'iFlyTest' },
    { type: 'natural_language', value: 'hi' },
  ],
  modelId: '3',                   // 模型 ID
  modelName: '星火13B',           // 模型名称
  response: 'Hello!...',          // AI 响应
  sessionId: '2f836398...',       // 会话 ID
  startTime: 1730191487344,       // 开始时间
  codeInfo: {                     // 代码信息 (可选)
    content: 'export function...', // 代码内容
  }
}
```

## 消息发送模式配置

```
sendMessageTypeOptions:
  (从 UI 字符串推断)
  - Enter 发送 / Shift+Enter 换行
  - Ctrl+Enter 发送 / Enter 换行

commitMessageTypeOptions:
  (Git 提交消息类型配置)
```

## 初始化流程

```
1. index.html 加载 → index-f0296668.js
2. Vue 3 应用创建 → Pinia stores 初始化
3. IdeaEnum 检测 → 加载 ideaUtil/vscodeUtil/eclipseUtil
4. 路由初始化 → 默认跳转到 /login 或 /chat-view
5. sendMsgToIdea("COMMON:PAGE_READY") → 通知 IDE 页面就绪
6. IDE 回复 COMMON:PLUGIN_BASE_INFO → 获取插件信息
7. IDE 回复 CHAT:GET_USER_INFO → 获取用户信息
8. IDE 回复 SETTING:GET_CONFIG → 获取配置
9. IDE 回复 USER:PERMISSION_LIST → 获取权限列表
10. 页面完全初始化 → 用户可交互
```

## 主题系统

```
支持亮色/暗色主题:
  - 登录背景: login-bg-light / login-bg-dark
  - 初始化错误: init-error-light / init-error-dark
  - 初始化 Logo: init-page-logo-light / init-page-logo-dark

主题切换:
  IDE → JS: SETTING:CHANGE_THEME → messageHandler.changeTheme(value)
  CSS 变量驱动: var(--chat-view-sql-choose-secondary-color) 等
```

## UI 组件层级

```
App (根组件)
├── HeaderTabs (顶部标签导航)
├── RouterView
│   ├── LoginPage (/login)
│   │   └── iframe (登录 iframe src 由 LOGIN:RECEIVER_LOGIN_IFRAME_SRC 提供)
│   ├── ChatView (/chat-view)
│   │   ├── SendMsgMode (消息发送/接收组件 — 286KB 核心)
│   │   │   ├── 输入框 (textarea + 快捷键)
│   │   │   ├── 助手选择下拉
│   │   │   ├── 命令选择下拉
│   │   │   ├── 知识库标签 (#tag)
│   │   │   ├── 文件上传
│   │   │   ├── 网站验证
│   │   │   ├── SQL 数据源/表选择
│   │   │   ├── ResponseFeedback (反馈组件)
│   │   │   ├── MarkdownRender (Markdown 渲染)
│   │   │   ├── CodeRender (代码渲染)
│   │   │   └── Mermaid 渲染 (流程图/架构图)
│   │   └── HistoryRecord (历史对话)
│   ├── CodeReview (/code-review)
│   ├── CodeCheck (/code-check)
│   ├── CodeSearch (/code-search)
│   ├── UnitTest (/unit-test)
│   ├── UnitTestBank (/unit-test-bank)
│   ├── MultiTest (/multi-test)
│   ├── SqlPage (/sql-page)
│   ├── SettingPage (/setting-page)
│   ├── AboutPage (/about-page)
│   ├── FunctionGuide (/function-guide)
│   └── ToolBox (/tool-box)
│       └── SQL生成/优化 工具
└── StatusBar (状态栏弹出)
```

## 与 Java 层对应关系

```
WebView Bridge ↔ Java 层映射:

window.myObject.sendMessage() ↔ WebViewWindowPanel.myObject (JSBridgeCallback)
  → SocketMessageHandleListener.handleWebViewMessage()
  → 根据 type 字段分发到对应 Service

window.receiveData() ↔ WebViewWindowPanel.callJS()
  → JSBridgeCallback.post() → WebView 执行 JavaScript

handlerReceivedMsg ↔ SocketMessageHandleListener
  → Java 层通过 WebViewDataTypeEnum 构造消息
  → WebView 通过 handlerReceivedMsg 接收并分发

sendMsgToIdea ↔ ChatService/CommonService
  → WebView 发送消息 → Java 层接收
  → Java 层通过 WebSocket 转发到 Agent
  → Agent 转发到云端
```