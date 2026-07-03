## 4. 功能子系统

### 4.1 代码补全 (13 步流程)

```
[1]  AutoCodeGenerateListener (Document 变更监听)
         │  条件: 自动补全启用 + 非选择/非命令模式 + InlineChat 不活跃
         v
[2]  DocumentActionTracker$ActionListener
         │  追踪: 字符输入/删除/粘贴
         │  防抖: 连续快速输入不重复触发
         v
[3]  RequestTipServiceImpl.requestTip(Editor, CodeTipRequestType)
         │  构建 CodeTipRequestDto:
         │    prefixCode, suffixCode, structure, imports,
         │    similarStr, language, filePath, cursorOffset
         v
[4]  构建 MessageDto (command = "code_complete")
         v
[5]  PluginWebsocketClient.send(MessageDto)
         v
[6]  Agent 转发到云端 (HTTPS POST -> 星火 API, SSE 流式)
         v
[7]  流式响应 -> ResponseStreamDto (text, ended)
         v
[8]  AgentCodeTipList 处理响应
         │  转换为 GetTipsResult.Tip / CodeInlayList
         v
[9]  EditorManagerServiceImpl.$F (Flow.Subscriber)
         │  onNext: 处理补全数据
         │  onComplete: 补全完成
         │  onError: RequestTimeoutException
         v
[10] RequestResultList 管理
         │  inlayLists: ObjectLinkedOpenHashSet&lt;CodeInlayList&gt;
         │  index: 当前显示索引
         v
[11] TipInlayRenderer 渲染
         │  灰色字体 + 斜体 + ActionButton
         v
[12] InlayRendering 样式
         │  TextAttributes: 前景色灰色, EffectType
         v
[13] 用户交互
         │  Tab    -> acceptInlay()  (替换文本)
         │  Esc    -> disposeTips()  (清除 Inlay)
         │  Alt+]  -> cycleNext()    (下一个建议)
         │  Alt+[  -> cyclePrevious() (上一个建议)
         │  Ctrl+-> -> acceptWord()  (接受一个词)
         │  Ctrl+|  -> acceptLine()  (接受一行)
```

> 来源: doc 32

### 4.2 智能聊天

| 维度 | 说明 | 来源 |
|------|------|------|
| WebView UI | Vue.js 2.7 + Pinia chat store + Element UI | doc 65 |
| 消息发送 | WebView -> JS Bridge -> ChatService -> WebSocket -> Agent | doc 09 |
| 上下文收集 | CommonService.collectContext(): prefix/suffix/structure/similar | doc 71 |
| 流式响应 | Agent 逐 chunk 推送 -> WebView 逐字渲染 | doc 09 |
| 历史管理 | NeDB 本地持久化, 自动压缩 | doc 66 |
| 多模型切换 | modelCode 字段, 支持 iFlyMate 等多种模型 | doc 09 |
| 知识增强 | KnowledgeExpress: 代码/文档/Web/数据库四路知识收集 | doc 71 |
| 企业助理 | assistantType 字段, iFlyDev 等企业定制模型 | doc 09 |

### 4.3 Inline Chat

| 组件 | 说明 | 来源 |
|------|------|------|
| SessionController | 会话生命周期管理 (455 strings, 最大类) | doc 57 |
| EphemeralChatSessionController | 临时会话管理 | doc 57 |
| InlineChatPanel / InputPanel / TopPanel | UI 组件 | doc 25, 57 |
| InlineChatInlay | 编辑器内嵌面板 | doc 57 |
| InlineChatCommandService | 命令发送/接收 | doc 11 |
| InlineChatStatusService | 状态订阅/通知 | doc 57 |
| IdeActionService / IdeEditorActionRouter | IDE 操作路由 | doc 27, 57 |
| 操作 | Accept/Reject/Undo/Retry/Stop | doc 57 |
| 快捷键 | Alt+Y(接受) / Alt+X(拒绝) / Alt+Z(撤销) / Alt+D(重试) | doc 57 |
| DiffService | 代码差异展示 | doc 42, 57 |
| 流式写入 | WriteCommandAction.runWriteCommandAction() 逐 chunk 写入编辑器 | doc 57 |

### 4.4 单元测试生成 (6 阶段时序)

```
阶段 1: 方法选择
  └── UnitTestAction.actionPerformed() -> UnitTestDialog.show()
       ├── 选择测试框架 (JUnit4/5)
       ├── 选择 Mock 框架 (Mockito/PowerMock/Disabled)
       ├── 模板生成开关 (GenaratebyTemplateSwitchEnum)
       └── 排除方法配置 (ExcludeMethodConfigurable)

阶段 2: 方法信息收集
  └── UnitTestService.testCollectionGenerate()
       ├── 收集方法签名、参数类型、返回类型
       ├── TestSubjectInspector 分析调用链
       └── MockBuilder 生成 Mock 配置

阶段 3a: 模板生成 (快速模式)
  └── VelocityInitializer.render(template, context)
       ├── 选择模板 (7 套: JUnit4/5, Mockito, PowerMock, TestNG, SpringBootTest)
       ├── 填充上下文变量 ($TESTED_CLASS, $MockitoMockBuilder, $replacementTypes)
       └── 生成测试代码

阶段 3b: AI 精准生成 (精准模式)
  └── CommonService.sendWsMessage(TEST_MAKE_CASE, data)
       ├── 发送方法信息到 Agent
       ├── Agent 调用 AI 模型分析代码分支
       └── 返回精准测试用例

阶段 4: 文件写入
  └── CreateTestFileTask.run()
       ├── 定位目标目录 (TargetDirectoryLocator)
       └── 写入测试代码

阶段 5: 编译+执行 (可选)
  └── 编译测试类 -> 执行 -> 收集结果

阶段 6: 覆盖率收集 (可选, 仅 IDEA+Java)
  └── 依赖 com.intellij.modules.coverage
```

> 来源: doc 46, 75

**7 种框架组合:**

| 模板 | 测试框架 | Mock 框架 | 来源 |
|------|---------|----------|------|
| JUnit4.java.ft | JUnit 4 | 无 | doc 75 |
| JUnit5.java.ft | JUnit 5 | 无 | doc 75 |
| JUnit4&Mockito.java.ft | JUnit 4 | Mockito | doc 75 |
| JUnit5&Mockito.java.ft | JUnit 5 | Mockito | doc 75 |
| JUnit4&Powermock.java.ft | JUnit 4 | PowerMock | doc 75 |
| TestNG&Mockito.java.ft | TestNG | Mockito | doc 75 |
| SpringBootTest&Mockito.java.ft | SpringBootTest | Mockito+Spring | doc 75 |

### 4.5 SQL 生成/优化

| 功能 | CommandEnum | 说明 | 来源 |
|------|-------------|------|------|
| SQL 生成对话 | SQL_GENERATE_TALK | 自然语言 -> SQL | doc 12 |
| SQL 优化对话 | SQL_OPTIMIZE_TALK | SQL -> 优化后 SQL | doc 12 |
| 独立 SQL 生成 | SQL_GENERATE | 直接生成 SQL | doc 12 |
| 独立 SQL 优化 | SQL_OPTIMIZE | 直接优化 SQL | doc 12 |
| 数据源列表 | SQL_SOURCE_LIST | 获取已配置数据源 | doc 12 |
| 数据库类型 | SQL_SOURCE_TYPES | MySQL/PostgreSQL/Oracle/TxSQL | doc 12 |
| 连接测试 | SQL_TEST_CONNECT | 测试数据库连接 | doc 12 |
| 表列表 | SQL_TABLE_LIST | 获取数据库表名 | doc 12 |
| 保存数据源 | SQL_SOURCE_EDIT | 保存/编辑连接配置 | doc 12 |
| 删除数据源 | SQL_SOURCE_DELETE | 删除连接配置 | doc 12 |

### 4.6 代码检查/修复

| 功能 | 说明 | 来源 |
|------|------|------|
| 请求检查 | CommandEnum.CODE_CHECK, 发送文件路径+内容 | doc 16 |
| 检查结果 | CodeCheckDto: codeFragment, errorType, errorMessage, range | doc 16 |
| 一键修复 | CodeProblemsIntentionAction (仅 IDEA) | doc 16, 72 |
| 重复代码检测 | CommandEnum.CODE_DEBUG_DUPLICATE | doc 16 |
| Gutter 图标 | CheckGutterIconRenderer | doc 16 |
| Problems 面板 | ProblemsView.ToolWindow.TreePopup 集成 (仅 IDEA) | doc 16, 72 |

### 4.7 Git 评审

| 功能 | CommandEnum | 说明 | 来源 |
|------|-------------|------|------|
| 代码评审 | GIT_REVIEW | 流式返回评审意见 | doc 14 |
| Diff 获取 | GIT_DIFF | 获取文件差异 | doc 14 |
| Commit Message | GIT_COMMIT_MESSAGE | 生成提交信息 | doc 14 |
| 仓库状态 | GIT_REPOSITORY_STATUS | 检查 Git 仓库 | doc 14 |
| 仓库授权 | GIT_REPO_AUTHORIZE | Git 仓库授权 | doc 14 |
| Token 保存 | GIT_SAVE_TOKEN | 保存 Git Token | doc 14 |
| 知识库状态 | GIT_CODE_KNOWLEDGE_REPO_STATUS | 代码知识库索引状态 | doc 14 |
| 知识库索引 | GIT_CODE_KNOWLEDGE_RE_INDEX | 触发重新索引 | doc 14 |

### 4.8 codeVector/RAG 语义搜索

```
+=============================================================================+
|                    iFlyCode codeVector/RAG 完整工作流                        |
+=============================================================================+

[IDE Plugin - Java Side]              [Agent Process - Node.js Side]         [Cloud RAG Server]

1. 用户交互触发                        3. WebSocket 消息路由                   5. RAG 后端服务
   WebView UI Actions:                   SocketMessageHandleListener            ragserver APIs:
   - CODE_SEARCH_REQUEST                 - handleAgentAction()                  - /code/search
   - GIT_CODE_KNOWLEDGE_RE_INDEX         - GIT_SEARCH -> CodeSearchService      - /code/getUserRepos
   - GIT_AUTHORIZE                        - GIT_USER_REPOS -> getGitRepos        - /code/getLangs
                                          - GIT_LANG_LIST -> gitLangList

2. 代码补全/审查触发                    4. Chat 知识增强搜索                    6. 本地文件索引
   CodeSearchService:                     KnowledgeExpress.create()              RetrievalAugmented:
   - sendCodeSearchRequest()              -> _collectInfoKnowledgeCode           - analysisFile()
   - sendCodeRepoRequest()                -> _collectInfoKnowledgeDoc            - Tree-sitter AST 解析
   - sendCodeLangRequest()                -> _collectInfoWebSearch               - structure 字段构造
                                          -> _collectInfoDatabase                - chunk(fileResult, 50)
                                                                               -> ragBatchLoadApi()

8. 代码补全上下文组装                    9. 相似代码检索
   getStructure(file)                     SimilarCodeCache:
     -> Tree-sitter AST                     - getSimilarCodes()
     -> structure 字段                        -> Jaccard similarity
   getImportStructures(file)                 -> LRU Cache (max=10, 30s)
     -> import 文件结构
   slidingCut() 分配:
     prefix: 38%
     suffix: 12%
     structure: 18%
     similar: 32%
```

> 来源: doc 71

---
