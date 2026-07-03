## 5. 未解码/解码失败条目

共 395 个 H() 调用解码结果被判定为 garbage（可打印字符比例 < 30% 或控制字符比例 > 30%）。

### 5.1 按类分布（garbage 数量降序）

| # | 类名 | garbage 数 | 占该类比例 |
|---|------|-----------|-----------|
| 1 | agent/service/ChatService | 44 | 16% |
| 2 | test/UnitTestService | 27 | 20% |
| 3 | diff/FileService | 19 | 86% |
| 4 | listener/GitBranchChangeListener | 16 | 17% |
| 5 | agent/service/CodeSearchService | 15 | 47% |
| 6 | service/editor/EditorManagerServiceImpl | 14 | 10% |
| 7 | enums/FileExtensionEnum | 14 | 31% |
| 8 | agent/service/SqlService | 13 | 19% |
| 9 | agent/service/UserService | 10 | 18% |
| 10 | agent/service/CommonService | 9 | 8% |
| 11 | action/batch/BatchUnitTestDialog | 9 | 20% |
| 12 | inline/InlineChatStreamHandleService | 8 | 42% |
| 13 | test/BatchUnitTestService | 7 | 27% |
| 14 | agent/SocketMessageHandleListener | 7 | 29% |
| 15 | test/CppTestService | 7 | 47% |
| 16 | service/editor/RequestTipServiceImpl | 6 | 21% |
| 17 | util/UnitTestCollectUtil | 6 | 26% |
| 18 | generate/CodeTipUtil | 5 | 19% |
| 19 | action/PrepushReviewAction | 5 | 22% |
| 20 | test/UnitTestDialog | 4 | 13% |
| 21 | agent/service/CodeCheckService | 4 | 14% |
| 22 | action/batch/CoverageCompileStatusNotification | 4 | 15% |
| 23 | inline/InlineChatHandleService | 4 | 27% |
| 24 | util/TypeUtils | 3 | 1% |
| 25 | util/EditorKt | 3 | 3% |
| 26 | agent/enums/PermissionEnum | 3 | 5% |
| 27 | agent/PluginAgentCommandLine | 3 | 9% |
| 28 | service/editor/TipInlayRenderer | 3 | 9% |
| 29 | util/FileUtils | 3 | 13% |
| 30 | statusBar/StatusBarPopup | 3 | 14% |
| 31 | action/batch/doc/BatchFunctionCommentAction | 3 | 18% |
| 32 | agent/enums/AgentModuleEnum | 3 | 20% |
| 33 | listener/CommitHandlerFactory$o | 3 | 25% |
| 34 | inline/enums/InlineChatCategoryEnum | 3 | 30% |
| 35 | view/WebViewWindowPanel | 2 | 6% |
| 36 | inline/render/InlineChatErrorPanelRenderer | 2 | 8% |
| 37 | inline/render/InlineChatBtnPanelRenderer | 2 | 9% |
| 38 | agent/PluginWebsocketClient | 2 | 9% |
| 39 | inline/render/InlineChatStopPanelRenderer | 2 | 9% |
| 40 | inline/render/InlineChatCategoryPanelRenderer | 2 | 9% |
| 41 | agent/service/InlineChatCommandService | 2 | 10% |
| 42 | action/ActionsUtil | 2 | 11% |
| 43 | content/util/EditorUtils | 2 | 12% |
| 44 | action/CodeProblemsIntentionAction | 2 | 13% |
| 45 | listener/ThemeChangeListener | 2 | 14% |
| 46 | enums/UnitTestMockEnum | 2 | 17% |
| 47 | util/CodeCheckUtil | 2 | 20% |
| 48 | updater/UpdaterCheckerFrom2021_2 | 2 | 20% |
| 49 | service/editor/CodeTipTypedHandlerDelegate | 2 | 20% |
| 50 | enums/PyUnitTestBaseEnum | 2 | 25% |
| 51 | action/batch/node/FileNode | 2 | 40% |
| 52 | enums/ElementTypeEnum | 2 | 50% |
| 53 | listener/ApplicationStartupListener | 2 | 100% |
| 54 | enums/ClientTypeEnum | 1 | 2% |
| 55 | apm/enums/SpanAttrEnum | 1 | 3% |
| 56 | util/JComponentKt | 1 | 3% |
| 57 | util/ClassNameUtils | 1 | 3% |
| 58 | util/PluginComponentPanelBuilder | 1 | 3% |
| 59 | listener/AutoCodeGenerateListener | 1 | 3% |
| 60 | enums/RestartEnum | 1 | 3% |
| 61 | util/AICodeStringUtil | 1 | 3% |
| 62 | util/VirtualFileUtils | 1 | 3% |
| 63 | ui/FontKt | 1 | 4% |
| 64 | action/CommitMessageSuggestionAction | 1 | 4% |
| 65 | apm/enums/TracerEnum | 1 | 4% |
| 66 | inline/InlineChatPanel | 1 | 4% |
| 67 | service/editor/InlayRendering | 1 | 4% |
| 68 | agent/PluginWebsocketListener | 1 | 4% |
| 69 | agent/service/GitReviewService | 1 | 4% |
| 70 | action/batch/ExcludeMethodConfigurable | 1 | 5% |
| 71 | enums/CodeCollectEnum | 1 | 5% |
| 72 | request/CodeGenerateEditorRequest | 1 | 5% |
| 73 | agent/service/RestartableAgentProcessService | 1 | 6% |
| 74 | inline/action/OpenInlineChatAction$Companion | 1 | 6% |
| 75 | enums/AICodeStatus | 1 | 6% |
| 76 | error/search/Presentation | 1 | 6% |
| 77 | updater/PluginUpdaterCheckService | 1 | 7% |
| 78 | inline/InlineChatInputPanel | 1 | 7% |
| 79 | generate/DefaultInlayList | 1 | 7% |
| 80 | inline/InlineChatInlay$u | 1 | 7% |
| 81 | inline/ide/ConditionalActionConfiguration | 1 | 8% |
| 82 | apm/OpenTelemetryConfig | 1 | 8% |
| 83 | util/PluginInfoUtils | 1 | 8% |
| 84 | enums/PyUnitTestMockEnum | 1 | 8% |
| 85 | message/BasicActionsBundle | 1 | 8% |
| 86 | language/CodeLanguageInfoSupport | 1 | 8% |
| 87 | inline/status/InlineStatusService | 1 | 8% |
| 88 | service/editor/AgentCodeTipList | 1 | 8% |
| 89 | service/editor/TipTypedHandlerDelegate | 1 | 8% |
| 90 | statusBar/StatusBarWidgetFactory | 1 | 9% |
| 91 | enums/UnitTestBaseEnum | 1 | 9% |
| 92 | agent/service/PluginAgentProcessServiceImpl | 1 | 9% |
| 93 | inline/ide/IdeAction | 1 | 9% |
| 94 | service/EditorManagerService | 1 | 9% |
| 95 | listener/CodeLookupManagerListener$01 | 1 | 10% |
| 96 | util/Application | 1 | 10% |
| 97 | language/LanguageInfoManager | 1 | 10% |
| 98 | inline/ide/ConditionalEditorActionHandler | 1 | 10% |
| 99 | request/AgentCodeTip | 1 | 10% |
| 100 | updater/UpdaterChecker2021_1 | 1 | 11% |
| 101 | agent/service/UserService$da | 1 | 11% |
| 102 | inline/ide/DefaultActionScopePredicateFactory | 1 | 11% |
| 103 | language/AICodeLanguageInfo | 1 | 12% |
| 104 | agent/PluginAgentProcessHandler | 1 | 12% |
| 105 | status/AICodeStatusService | 1 | 12% |
| 106 | listener/AutoCodeGenerateListener$T | 1 | 14% |
| 107 | listener/GitBranchChangeListener$R | 1 | 14% |
| 108 | inline/ide/IdeEditorActionRouterKt | 1 | 14% |
| 109 | action/AcceptWordInlaysAction$wa | 1 | 14% |
| 110 | action/click/PluginAnAction | 1 | 14% |
| 111 | service/editor/DocumentActionTracker | 1 | 14% |
| 112 | service/editor/DocumentActionTracker$ActionListener | 1 | 14% |
| 113 | util/PluginComponentPanelBuilder$CommentLabel | 1 | 20% |
| 114 | complete/InlayCompletionHintFactory$InlineKeybindingHintComponent | 1 | 20% |
| 115 | agent/AgentCheckTimer | 1 | 20% |
| 116 | util/EditorCacheUtil | 1 | 25% |
| 117 | inline/ide/ActionScope | 1 | 25% |
| 118 | action/click/CodeCheckAction | 1 | 25% |
| 119 | view/CustomSchemeHandlerFactory | 1 | 25% |
| 120 | inline/enums/InlineChatOperateEnum | 1 | 50% |

## 6. 按包维度的统计摘要

| 包路径 | 类数 | H() 调用总数 | high | medium | garbage |
|--------|------|-------------|------|--------|---------|
| util | 30 | 783 | 745 | 12 | 26 |
| agent/service | 14 | 652 | 536 | 15 | 101 |
| enums | 30 | 629 | 583 | 20 | 26 |
| service/editor | 13 | 305 | 263 | 12 | 30 |
| listener | 19 | 243 | 213 | 3 | 27 |
| action | 22 | 212 | 197 | 4 | 11 |
| test | 4 | 204 | 157 | 2 | 45 |
| action/batch | 8 | 154 | 134 | 6 | 14 |
| inline | 15 | 134 | 119 | 0 | 15 |
| agent | 9 | 131 | 112 | 4 | 15 |
| inline/ide | 8 | 109 | 100 | 3 | 6 |
| agent/enums | 5 | 102 | 92 | 4 | 6 |
| inline/render | 6 | 97 | 89 | 0 | 8 |
| updater | 8 | 89 | 83 | 2 | 4 |
| ui | 8 | 68 | 65 | 2 | 1 |
| diff | 5 | 66 | 41 | 6 | 19 |
| view | 8 | 65 | 61 | 1 | 3 |
| apm/enums | 2 | 64 | 58 | 4 | 2 |
| generate | 3 | 59 | 50 | 3 | 6 |
| language | 6 | 46 | 42 | 1 | 3 |
| inline/action | 5 | 45 | 43 | 1 | 1 |
| complete | 4 | 34 | 31 | 2 | 1 |
| action/click | 6 | 34 | 32 | 0 | 2 |
| statusBar | 2 | 32 | 27 | 1 | 4 |
| request | 2 | 31 | 29 | 0 | 2 |
| inline/controller | 3 | 30 | 28 | 2 | 0 |
| apm | 3 | 25 | 22 | 2 | 1 |
| service | 7 | 23 | 22 | 0 | 1 |
| error/search | 2 | 21 | 20 | 0 | 1 |
| inline/status | 3 | 18 | 17 | 0 | 1 |
| action/batch/doc | 1 | 17 | 14 | 0 | 3 |
| content/util | 1 | 16 | 13 | 1 | 2 |
| inline/enums | 3 | 16 | 6 | 6 | 4 |
| com/aicode | 2 | 15 | 15 | 0 | 0 |
| action/batch/node | 3 | 13 | 11 | 0 | 2 |
| message | 1 | 12 | 11 | 0 | 1 |
| status | 3 | 10 | 9 | 0 | 1 |
| inline/listener | 2 | 10 | 10 | 0 | 0 |
| content/util/file | 1 | 8 | 8 | 0 | 0 |
| inline/action/operate | 1 | 4 | 4 | 0 | 0 |
| service/response | 1 | 2 | 2 | 0 | 0 |

## 7. 关键发现

1. **解码覆盖率**: 91.5% 的 H() 调用可成功解码为可读字符串（high+medium），仅 8.5% 为不可读 garbage
2. **中文恢复困难**: 虽然可解码为 CJK 字符，但 88.8% 的中文条目无法还原为有意义的中文词句，XOR 解码在多字节字符上存在系统性偏移
3. **枚举类最密集**: `WebViewDataTypeEnum`（156）、`LanguageEnum`（114）、`PermissionEnum`（59）等枚举类是 H() 使用最密集的类，用于存储 UI 显示名称和类型标识
4. **服务类最复杂**: `ChatService`（267 次调用）是 H() 使用最多的类，涵盖聊天请求、响应处理、错误处理等全流程
5. **33 个密钥类**: H() 的 XOR 密钥分布在 33 个定义类中，每个类拥有独立的 v[107] 序列（v[0] 固定为 0）
6. **无遗漏密钥**: 所有 4628 次 H() 调用均能匹配到对应密钥类，无因缺少密钥而无法解码的情况
7. **内联聊天系统**: Inline Chat 子系统（IdeActionService、InlineChatPanel、InlineChatHandleService 等）共约 150 次 H() 调用，用于操作名称、面板渲染、错误处理
8. **Git 集成**: GitReviewService 和 GitBranchChangeListener 合计 116 次 H() 调用，涵盖代码评审、分支变更、WebSocket 通信
