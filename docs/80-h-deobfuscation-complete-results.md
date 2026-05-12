# iFlyCode H() 字符串解码全量结果

> 版本: 3.4.2-222 | 分析日期: 2026-05-13 | 解码工具: h_deobfuscator_final.py

## 1. 统计概览

| 指标 | 值 |
|------|-----|
| 扫描 .class 文件总数 | 566 |
| 含 H() 调用的类 | 279 |
| H() 调用总数 | 4628 |
| 高质量解码 (high) | 4114 |
| 中等质量解码 (medium) | 119 |
| 低质量解码 (low) | 0 |
| 垃圾/不可读 (garbage) | 395 |
| 无 v[] 密钥 | 0 |
| 可用解码率 (high+medium) | 91.5% |
| 含中文字符的解码条目 | 175 |

### 解码算法

```
output[i] = input[i] XOR v[(len-i-1) % 106 + 1]
```

每个 H() 定义类拥有独立的 v[] 序列（周期 106），共 33 个密钥类。

## 2. 按功能分类统计

| 分类 | 数量 | 占比 |
|------|------|------|
| Other | 2459 | 53.1% |
| Method/Field Name | 922 | 19.9% |
| Short Identifier | 367 | 7.9% |
| empty/null | 268 | 5.8% |
| Config Value | 224 | 4.8% |
| Class Reference | 184 | 4.0% |
| UI Text (Chinese) | 176 | 3.8% |
| Enum/Constant | 20 | 0.4% |
| API Path | 7 | 0.2% |
| URL/API | 1 | 0.0% |

## 3. 按类分组的解码结果

### 3.1 全量类列表（按 H() 调用数降序）

| # | 类名 | 总调用 | high | medium | garbage | 可用率 |
|---|------|--------|------|--------|---------|--------|
| 1 | agent/service/ChatService | 267 | 219 | 4 | 44 | 84% |
| 2 | util/TypeUtils | 252 | 248 | 1 | 3 | 99% |
| 3 | enums/WebViewDataTypeEnum | 156 | 156 | 0 | 0 | 100% |
| 4 | service/editor/EditorManagerServiceImpl | 140 | 119 | 7 | 14 | 90% |
| 5 | test/UnitTestService | 133 | 104 | 2 | 27 | 80% |
| 6 | enums/LanguageEnum | 114 | 114 | 0 | 0 | 100% |
| 7 | util/EditorKt | 108 | 102 | 3 | 3 | 97% |
| 8 | agent/service/CommonService | 107 | 97 | 1 | 9 | 92% |
| 9 | listener/GitBranchChangeListener | 93 | 77 | 0 | 16 | 83% |
| 10 | util/PsiUtils | 70 | 69 | 1 | 0 | 100% |
| 11 | agent/service/SqlService | 67 | 53 | 1 | 13 | 81% |
| 12 | agent/enums/PermissionEnum | 59 | 56 | 0 | 3 | 95% |
| 13 | agent/service/UserService | 56 | 44 | 2 | 10 | 82% |
| 14 | inline/ide/IdeActionService | 50 | 49 | 1 | 0 | 100% |
| 15 | action/batch/BatchUnitTestDialog | 46 | 37 | 0 | 9 | 80% |
| 16 | enums/FileExtensionEnum | 45 | 27 | 4 | 14 | 69% |
| 17 | enums/ClientTypeEnum | 45 | 42 | 2 | 1 | 98% |
| 18 | apm/enums/SpanAttrEnum | 39 | 36 | 2 | 1 | 97% |
| 19 | util/JComponentKt | 39 | 38 | 0 | 1 | 97% |
| 20 | util/ClassNameUtils | 39 | 38 | 0 | 1 | 97% |
| 21 | util/PluginComponentPanelBuilder | 36 | 35 | 0 | 1 | 97% |
| 22 | listener/AutoCodeGenerateListener | 35 | 34 | 0 | 1 | 97% |
| 23 | agent/PluginAgentCommandLine | 35 | 29 | 3 | 3 | 91% |
| 24 | enums/RestartEnum | 33 | 31 | 1 | 1 | 97% |
| 25 | agent/service/CodeSearchService | 32 | 14 | 3 | 15 | 53% |
| 26 | view/WebViewWindowPanel | 32 | 29 | 1 | 2 | 94% |
| 27 | service/editor/TipInlayRenderer | 32 | 29 | 0 | 3 | 91% |
| 28 | util/AICodeStringUtil | 31 | 29 | 1 | 1 | 97% |
| 29 | test/UnitTestDialog | 30 | 26 | 0 | 4 | 87% |
| 30 | enums/WebViewResponseTypeEnum | 30 | 28 | 2 | 0 | 100% |
| 31 | util/VirtualFileUtils | 29 | 27 | 1 | 1 | 97% |
| 32 | diff/DiffService | 29 | 23 | 6 | 0 | 100% |
| 33 | agent/service/CodeCheckService | 29 | 23 | 2 | 4 | 86% |
| 34 | ui/FontKt | 28 | 25 | 2 | 1 | 96% |
| 35 | updater/PluginUpdater | 28 | 28 | 0 | 0 | 100% |
| 36 | action/CommitMessageSuggestionAction | 28 | 27 | 0 | 1 | 96% |
| 37 | service/editor/RequestTipServiceImpl | 28 | 19 | 3 | 6 | 79% |
| 38 | action/batch/BatchUnitTestTemplateService | 27 | 23 | 4 | 0 | 100% |
| 39 | action/batch/CoverageCompileStatusNotification | 27 | 23 | 0 | 4 | 85% |
| 40 | generate/CodeTipUtil | 27 | 22 | 0 | 5 | 81% |
| 41 | test/BatchUnitTestService | 26 | 19 | 0 | 7 | 73% |
| 42 | apm/enums/TracerEnum | 25 | 22 | 2 | 1 | 96% |
| 43 | util/PropertyUtils | 25 | 24 | 1 | 0 | 100% |
| 44 | inline/InlineChatPanel | 25 | 24 | 0 | 1 | 96% |
| 45 | inline/render/InlineChatErrorPanelRenderer | 25 | 23 | 0 | 2 | 92% |
| 46 | service/editor/InlayRendering | 25 | 24 | 0 | 1 | 96% |
| 47 | agent/SocketMessageHandleListener | 24 | 16 | 1 | 7 | 71% |
| 48 | util/UnitTestCollectUtil | 23 | 16 | 1 | 6 | 74% |
| 49 | util/FileUtils | 23 | 20 | 0 | 3 | 87% |
| 50 | agent/PluginWebsocketListener | 23 | 22 | 0 | 1 | 96% |
| 51 | agent/service/GitReviewService | 23 | 21 | 1 | 1 | 96% |
| 52 | inline/render/InlineChatBtnPanelRenderer | 23 | 21 | 0 | 2 | 91% |
| 53 | action/PrepushReviewAction | 23 | 17 | 1 | 5 | 78% |
| 54 | enums/RepoStatusEnum | 22 | 22 | 0 | 0 | 100% |
| 55 | diff/FileService | 22 | 3 | 0 | 19 | 14% |
| 56 | agent/PluginWebsocketClient | 22 | 20 | 0 | 2 | 91% |
| 57 | inline/render/InlineChatStopPanelRenderer | 22 | 20 | 0 | 2 | 91% |
| 58 | inline/render/InlineChatCategoryPanelRenderer | 22 | 20 | 0 | 2 | 91% |
| 59 | action/batch/ExcludeMethodConfigurable | 22 | 21 | 0 | 1 | 95% |
| 60 | statusBar/StatusBarPopup | 21 | 17 | 1 | 3 | 86% |
| 61 | enums/CodeCollectEnum | 21 | 17 | 3 | 1 | 95% |
| 62 | inline/controller/SessionController | 21 | 21 | 0 | 0 | 100% |
| 63 | request/CodeGenerateEditorRequest | 21 | 20 | 0 | 1 | 95% |
| 64 | agent/service/InlineChatCommandService | 20 | 18 | 0 | 2 | 90% |
| 65 | inline/InlineChatStreamHandleService | 19 | 11 | 0 | 8 | 58% |
| 66 | action/ActionsUtil | 19 | 15 | 2 | 2 | 89% |
| 67 | agent/service/RestartableAgentProcessService | 18 | 17 | 0 | 1 | 94% |
| 68 | inline/action/OpenInlineChatAction$Companion | 18 | 17 | 0 | 1 | 94% |
| 69 | action/batch/doc/BatchFunctionCommentAction | 17 | 14 | 0 | 3 | 82% |
| 70 | generate/SimpleCodeTipCache | 17 | 15 | 2 | 0 | 100% |
| 71 | enums/AICodeStatus | 16 | 14 | 1 | 1 | 94% |
| 72 | content/util/EditorUtils | 16 | 13 | 1 | 2 | 88% |
| 73 | action/batch/BatchUTGeneratorAction | 16 | 15 | 1 | 0 | 100% |
| 74 | service/editor/EditorUtil | 16 | 15 | 1 | 0 | 100% |
| 75 | error/search/Presentation | 16 | 15 | 0 | 1 | 94% |
| 76 | test/CppTestService | 15 | 8 | 0 | 7 | 53% |
| 77 | util/NewFileUtils | 15 | 15 | 0 | 0 | 100% |
| 78 | complete/InlayGotItListener | 15 | 14 | 1 | 0 | 100% |
| 79 | updater/PluginUpdaterCheckService | 15 | 14 | 0 | 1 | 93% |
| 80 | agent/enums/AgentModuleEnum | 15 | 10 | 2 | 3 | 80% |
| 81 | inline/InlineChatInputPanel | 15 | 14 | 0 | 1 | 93% |
| 82 | inline/InlineChatHandleService | 15 | 11 | 0 | 4 | 73% |
| 83 | action/CodeProblemsIntentionAction | 15 | 13 | 0 | 2 | 87% |
| 84 | generate/DefaultInlayList | 15 | 13 | 1 | 1 | 93% |
| 85 | ui/Font | 14 | 14 | 0 | 0 | 100% |
| 86 | listener/ThemeChangeListener | 14 | 12 | 0 | 2 | 86% |
| 87 | listener/CodeFileEditorManagerListener | 14 | 14 | 0 | 0 | 100% |
| 88 | inline/InlineChatInlay$u | 14 | 13 | 0 | 1 | 93% |
| 89 | complete/InlayCompletionHintFactory | 13 | 13 | 0 | 0 | 100% |
| 90 | inline/ide/ConditionalActionConfiguration | 13 | 12 | 0 | 1 | 92% |
| 91 | listener/CommitHandlerFactory$o | 12 | 8 | 1 | 3 | 75% |
| 92 | apm/OpenTelemetryConfig | 12 | 9 | 2 | 1 | 92% |
| 93 | util/PluginInfoUtils | 12 | 11 | 0 | 1 | 92% |
| 94 | enums/UnitTestMockEnum | 12 | 9 | 1 | 2 | 83% |
| 95 | enums/PyUnitTestMockEnum | 12 | 11 | 0 | 1 | 92% |
| 96 | enums/AssistantTypeEnum | 12 | 12 | 0 | 0 | 100% |
| 97 | enums/ChatOperationEnum | 12 | 12 | 0 | 0 | 100% |
| 98 | message/BasicActionsBundle | 12 | 11 | 0 | 1 | 92% |
| 99 | language/CodeLanguageInfoSupport | 12 | 11 | 0 | 1 | 92% |
| 100 | agent/enums/PageEnum | 12 | 10 | 2 | 0 | 100% |
| 101 | agent/enums/ModuleEnum | 12 | 12 | 0 | 0 | 100% |
| 102 | inline/status/InlineStatusService | 12 | 11 | 0 | 1 | 92% |
| 103 | view/CustomResourceHandler | 12 | 12 | 0 | 0 | 100% |
| 104 | service/editor/AgentCodeTipList | 12 | 11 | 0 | 1 | 92% |
| 105 | service/editor/TipTypedHandlerDelegate | 12 | 11 | 0 | 1 | 92% |
| 106 | statusBar/StatusBarWidgetFactory | 11 | 10 | 0 | 1 | 91% |
| 107 | enums/UnitTestBaseEnum | 11 | 10 | 0 | 1 | 91% |
| 108 | agent/PluginAgentProcessHandler$01 | 11 | 11 | 0 | 0 | 100% |
| 109 | agent/service/PluginAgentProcessServiceImpl | 11 | 10 | 0 | 1 | 91% |
| 110 | inline/ide/IdeAction | 11 | 10 | 0 | 1 | 91% |
| 111 | action/LogoutAction | 11 | 11 | 0 | 0 | 100% |
| 112 | service/EditorManagerService | 11 | 10 | 0 | 1 | 91% |
| 113 | listener/CodeLookupManagerListener$01 | 10 | 9 | 0 | 1 | 90% |
| 114 | util/CodeCheckUtil | 10 | 7 | 1 | 2 | 80% |
| 115 | util/Application | 10 | 9 | 0 | 1 | 90% |
| 116 | enums/PluginSceneEnum | 10 | 10 | 0 | 0 | 100% |
| 117 | updater/UpdaterCheckerFrom2021_2 | 10 | 7 | 1 | 2 | 80% |
| 118 | updater/PluginUpdaterCheckService$CheckUpdatesTask | 10 | 9 | 1 | 0 | 100% |
| 119 | language/LanguageInfoManager | 10 | 9 | 0 | 1 | 90% |
| 120 | inline/ide/ConditionalEditorActionHandler | 10 | 9 | 0 | 1 | 90% |
| 121 | inline/enums/InlineChatCategoryEnum | 10 | 4 | 3 | 3 | 70% |
| 122 | request/AgentCodeTip | 10 | 9 | 0 | 1 | 90% |
| 123 | service/editor/CodeTipTypedHandlerDelegate | 10 | 8 | 0 | 2 | 80% |
| 124 | enums/GitRepoStatusEnum | 9 | 9 | 0 | 0 | 100% |
| 125 | enums/OperateActionEnum | 9 | 9 | 0 | 0 | 100% |
| 126 | updater/UpdaterChecker2021_1 | 9 | 8 | 0 | 1 | 89% |
| 127 | agent/service/UserService$da | 9 | 7 | 1 | 1 | 89% |
| 128 | inline/InlineChatService$Companion | 9 | 9 | 0 | 0 | 100% |
| 129 | inline/ide/DefaultActionScopePredicateFactory | 9 | 8 | 0 | 1 | 89% |
| 130 | action/CodeProblemsTreePopupAction | 9 | 8 | 1 | 0 | 100% |
| 131 | PluginStartupActivity | 8 | 8 | 0 | 0 | 100% |
| 132 | ui/SendStopActionButtonPanel | 8 | 8 | 0 | 0 | 100% |
| 133 | enums/PyUnitTestBaseEnum | 8 | 6 | 0 | 2 | 75% |
| 134 | enums/TestGenerationProcess | 8 | 7 | 1 | 0 | 100% |
| 135 | content/util/file/FileUtils | 8 | 8 | 0 | 0 | 100% |
| 136 | language/AICodeLanguageInfo | 8 | 6 | 1 | 1 | 88% |
| 137 | agent/PluginAgentProcessHandler | 8 | 7 | 0 | 1 | 88% |
| 138 | status/AICodeStatusService | 8 | 7 | 0 | 1 | 88% |
| 139 | inline/action/CloseInlineChatAction | 8 | 7 | 1 | 0 | 100% |
| 140 | action/OpenWindowAction | 8 | 8 | 0 | 0 | 100% |
| 141 | action/CodePromoterAction | 8 | 8 | 0 | 0 | 100% |
| 142 | action/EnableAutoTriggerCodeGenerateAction | 8 | 8 | 0 | 0 | 100% |
| 143 | view/OpenedConnection | 8 | 8 | 0 | 0 | 100% |
| 144 | PluginStartupActivity$01 | 7 | 7 | 0 | 0 | 100% |
| 145 | listener/GitBranchChangeListener$H | 7 | 7 | 0 | 0 | 100% |
| 146 | listener/PluginManagerListener | 7 | 7 | 0 | 0 | 100% |
| 147 | listener/AutoCodeGenerateListener$T | 7 | 6 | 0 | 1 | 86% |
| 148 | listener/GitBranchChangeListener$R | 7 | 6 | 0 | 1 | 86% |
| 149 | apm/OpenTelemetryService | 7 | 7 | 0 | 0 | 100% |
| 150 | util/HandleCacheUtil | 7 | 7 | 0 | 0 | 100% |
| 151 | util/AICodeUtils | 7 | 7 | 0 | 0 | 100% |
| 152 | diff/DiffDialog | 7 | 7 | 0 | 0 | 100% |
| 153 | updater/PluginUpdaterCheckService$k | 7 | 7 | 0 | 0 | 100% |
| 154 | language/AICodeExtendedLanguageSupport | 7 | 7 | 0 | 0 | 100% |
| 155 | inline/ide/IdeEditorActionRouterKt | 7 | 6 | 0 | 1 | 86% |
| 156 | inline/action/SendMessageAction | 7 | 7 | 0 | 0 | 100% |
| 157 | inline/action/OpenInlineChatAction | 7 | 7 | 0 | 0 | 100% |
| 158 | action/AcceptLineCodeInlaysAction | 7 | 7 | 0 | 0 | 100% |
| 159 | action/AcceptInlaysAction$pa | 7 | 7 | 0 | 0 | 100% |
| 160 | action/AcceptLineCodeInlaysAction$va | 7 | 7 | 0 | 0 | 100% |
| 161 | action/PluginSettingAction | 7 | 7 | 0 | 0 | 100% |
| 162 | action/RefreshAction | 7 | 7 | 0 | 0 | 100% |
| 163 | action/AcceptWordInlaysAction$wa | 7 | 6 | 0 | 1 | 86% |
| 164 | action/UserInfoAction | 7 | 7 | 0 | 0 | 100% |
| 165 | action/AcceptWordInlaysAction | 7 | 7 | 0 | 0 | 100% |
| 166 | action/AcceptInlaysAction | 7 | 7 | 0 | 0 | 100% |
| 167 | action/click/PluginAnAction | 7 | 6 | 0 | 1 | 86% |
| 168 | action/click/OpenInlayInlineChatAction | 7 | 7 | 0 | 0 | 100% |
| 169 | action/batch/node/CheckboxTreeCellRenderer | 7 | 7 | 0 | 0 | 100% |
| 170 | service/editor/RequestResultList | 7 | 7 | 0 | 0 | 100% |
| 171 | service/editor/DocumentActionTracker | 7 | 6 | 0 | 1 | 86% |
| 172 | service/editor/DocumentActionTracker$ActionListener | 7 | 5 | 1 | 1 | 86% |
| 173 | listener/GitBranchChangeListener$b | 6 | 5 | 1 | 0 | 100% |
| 174 | apm/OpenTelemetryConfig$ca | 6 | 6 | 0 | 0 | 100% |
| 175 | util/LogUtil | 6 | 6 | 0 | 0 | 100% |
| 176 | enums/DuplicateRule | 6 | 6 | 0 | 0 | 100% |
| 177 | agent/service/InitService | 6 | 6 | 0 | 0 | 100% |
| 178 | inline/InlineChatInlay$01 | 6 | 6 | 0 | 0 | 100% |
| 179 | inline/listener/InlineChatInputBorderFocusListener | 6 | 6 | 0 | 0 | 100% |
| 180 | action/click/UnitTestAction | 6 | 6 | 0 | 0 | 100% |
| 181 | action/batch/BatchUTGeneratorAction$ta | 6 | 6 | 0 | 0 | 100% |
| 182 | action/batch/CoverageCompileStatusNotification$aa | 6 | 5 | 1 | 0 | 100% |
| 183 | service/editor/CancelRequestTip | 6 | 6 | 0 | 0 | 100% |
| 184 | ui/ActionButton | 5 | 5 | 0 | 0 | 100% |
| 185 | ui/Style$Colors$InlineChat | 5 | 5 | 0 | 0 | 100% |
| 186 | listener/AICodeUnloadPluginListener | 5 | 5 | 0 | 0 | 100% |
| 187 | listener/CommitHandlerFactory | 5 | 5 | 0 | 0 | 100% |
| 188 | listener/CodeEditorListener | 5 | 5 | 0 | 0 | 100% |
| 189 | listener/FileWatchedAdapter | 5 | 5 | 0 | 0 | 100% |
| 190 | util/PluginComponentPanelBuilder$CommentLabel | 5 | 4 | 0 | 1 | 80% |
| 191 | util/MessageBundle | 5 | 5 | 0 | 0 | 100% |
| 192 | complete/InlayCompletionHintFactory$InlineKeybindingHintComponent | 5 | 3 | 1 | 1 | 80% |
| 193 | enums/CodeTipRequestType | 5 | 5 | 0 | 0 | 100% |
| 194 | diff/CloudDiffUtil | 5 | 5 | 0 | 0 | 100% |
| 195 | updater/PluginUpdater$E | 5 | 5 | 0 | 0 | 100% |
| 196 | updater/PluginUpdater$m | 5 | 5 | 0 | 0 | 100% |
| 197 | language/CommonLanguageSupport | 5 | 5 | 0 | 0 | 100% |
| 198 | agent/AgentCheckTimer | 5 | 4 | 0 | 1 | 80% |
| 199 | inline/InlineChatInputPanel$S | 5 | 5 | 0 | 0 | 100% |
| 200 | inline/InlineChatService | 5 | 5 | 0 | 0 | 100% |
| 201 | inline/ide/IdeEditorActionRouter | 5 | 5 | 0 | 0 | 100% |
| 202 | inline/controller/ChatInputController | 5 | 4 | 1 | 0 | 100% |
| 203 | inline/action/StopAction | 5 | 5 | 0 | 0 | 100% |
| 204 | action/CycleNextEditorInlays | 5 | 5 | 0 | 0 | 100% |
| 205 | action/RequestCodeGenerateAction | 5 | 5 | 0 | 0 | 100% |
| 206 | action/TipPromoterAction | 5 | 5 | 0 | 0 | 100% |
| 207 | action/CyclePreviousEditorInlays | 5 | 5 | 0 | 0 | 100% |
| 208 | action/click/TerminalAction | 5 | 5 | 0 | 0 | 100% |
| 209 | action/click/BaseAction | 5 | 5 | 0 | 0 | 100% |
| 210 | action/batch/node/FileNode | 5 | 3 | 0 | 2 | 60% |
| 211 | view/PluginToolWindowPanel | 5 | 5 | 0 | 0 | 100% |
| 212 | service/EditorSupport | 5 | 5 | 0 | 0 | 100% |
| 213 | error/search/DebuggerFilter | 5 | 5 | 0 | 0 | 100% |
| 214 | ui/Style$Colors | 4 | 4 | 0 | 0 | 100% |
| 215 | listener/CodeEditorListener$CodeSelectionListener | 4 | 4 | 0 | 0 | 100% |
| 216 | listener/CodeFileEditorManagerListener$01 | 4 | 3 | 1 | 0 | 100% |
| 217 | util/HighlighterUtil$01 | 4 | 4 | 0 | 0 | 100% |
| 218 | util/EditorCacheUtil | 4 | 3 | 0 | 1 | 75% |
| 219 | util/HighlighterUtil$02 | 4 | 4 | 0 | 0 | 100% |
| 220 | util/IndentLineUtil | 4 | 4 | 0 | 0 | 100% |
| 221 | enums/ElementTypeEnum | 4 | 2 | 0 | 2 | 50% |
| 222 | enums/SendKeyEnum | 4 | 4 | 0 | 0 | 100% |
| 223 | enums/GenaratebyTemplateSwitchEnum | 4 | 2 | 2 | 0 | 100% |
| 224 | enums/BatchTestUnitLimt | 4 | 4 | 0 | 0 | 100% |
| 225 | enums/DuplicateFileNameSwitchEnum | 4 | 2 | 2 | 0 | 100% |
| 226 | enums/TipTypeEnum | 4 | 3 | 1 | 0 | 100% |
| 227 | enums/LineToolsTypeEnum | 4 | 4 | 0 | 0 | 100% |
| 228 | language/LanguageMap | 4 | 4 | 0 | 0 | 100% |
| 229 | agent/enums/CommandEnum | 4 | 4 | 0 | 0 | 100% |
| 230 | agent/service/CodeCompleteService | 4 | 4 | 0 | 0 | 100% |
| 231 | inline/InlineChatInlay$02 | 4 | 4 | 0 | 0 | 100% |
| 232 | inline/InlineChatPanel$r | 4 | 4 | 0 | 0 | 100% |
| 233 | inline/InlineChatPanel$02 | 4 | 4 | 0 | 0 | 100% |
| 234 | inline/InlineChatPanel$03 | 4 | 4 | 0 | 0 | 100% |
| 235 | inline/ide/ActionScope | 4 | 1 | 2 | 1 | 75% |
| 236 | inline/listener/InlineChatInputBorderFocusListener$Companion | 4 | 4 | 0 | 0 | 100% |
| 237 | inline/enums/InlineChatStepEnum | 4 | 2 | 2 | 0 | 100% |
| 238 | inline/controller/EphemeralChatSessionController | 4 | 3 | 1 | 0 | 100% |
| 239 | inline/action/operate/InlineChatAction | 4 | 4 | 0 | 0 | 100% |
| 240 | action/click/CodeCheckAction | 4 | 3 | 0 | 1 | 75% |
| 241 | action/batch/TreeCellRenderer | 4 | 4 | 0 | 0 | 100% |
| 242 | view/CustomSchemeHandlerFactory | 4 | 3 | 0 | 1 | 75% |
| 243 | ui/Style$Borders | 3 | 3 | 0 | 0 | 100% |
| 244 | util/PluginComponentPanelBuilder$I$a | 3 | 3 | 0 | 0 | 100% |
| 245 | util/StringUtils | 3 | 2 | 1 | 0 | 100% |
| 246 | util/FileUtil | 3 | 2 | 1 | 0 | 100% |
| 247 | util/HighlighterUtil | 3 | 3 | 0 | 0 | 100% |
| 248 | enums/CodeTipType | 3 | 3 | 0 | 0 | 100% |
| 249 | diff/GenericUtils | 3 | 3 | 0 | 0 | 100% |
| 250 | agent/service/PluginAgentProcessService | 3 | 3 | 0 | 0 | 100% |
| 251 | inline/InlineChatInputComponent | 3 | 3 | 0 | 0 | 100% |
| 252 | inline/render/InlineChatBtnPanelRenderer$O | 3 | 3 | 0 | 0 | 100% |
| 253 | inline/status/InlineChatStatusServiceKt | 3 | 3 | 0 | 0 | 100% |
| 254 | inline/status/InlineChatStatusServiceProvider | 3 | 3 | 0 | 0 | 100% |
| 255 | service/EditorRequestService | 3 | 3 | 0 | 0 | 100% |
| 256 | service/editor/EditorManagerServiceImpl$F | 3 | 3 | 0 | 0 | 100% |
| 257 | listener/ApplicationStartupListener | 2 | 0 | 0 | 2 | 0% |
| 258 | util/ApplicationUtil | 2 | 2 | 0 | 0 | 100% |
| 259 | enums/TipType | 2 | 2 | 0 | 0 | 100% |
| 260 | agent/HeartBeatCheckRunner | 2 | 2 | 0 | 0 | 100% |
| 261 | inline/InlineChatInlay | 2 | 2 | 0 | 0 | 100% |
| 262 | inline/enums/InlineChatOperateEnum | 2 | 0 | 1 | 1 | 50% |
| 263 | inline/render/InlineChatErrorPanelRenderer$n | 2 | 2 | 0 | 0 | 100% |
| 264 | view/WebViewWindowPanel$D | 2 | 2 | 0 | 0 | 100% |
| 265 | service/response/BizResponse | 2 | 2 | 0 | 0 | 100% |
| 266 | ui/Style | 1 | 1 | 0 | 0 | 100% |
| 267 | listener/PluginDocumentListener | 1 | 1 | 0 | 0 | 100% |
| 268 | util/FileSizeUtil | 1 | 1 | 0 | 0 | 100% |
| 269 | complete/InlayListener | 1 | 1 | 0 | 0 | 100% |
| 270 | agent/SocketMessageListener | 1 | 1 | 0 | 0 | 100% |
| 271 | status/UserLoginListener | 1 | 1 | 0 | 0 | 100% |
| 272 | status/AICodeStatusListener | 1 | 1 | 0 | 0 | 100% |
| 273 | action/batch/node/AbstractNode | 1 | 1 | 0 | 0 | 100% |
| 274 | view/WebViewWindowPanel$M | 1 | 1 | 0 | 0 | 100% |
| 275 | view/WebViewWindowPanel$c | 1 | 1 | 0 | 0 | 100% |
| 276 | service/TipReceivedMessage | 1 | 1 | 0 | 0 | 100% |
| 277 | service/RequestsCancelledService | 1 | 1 | 0 | 0 | 100% |
| 278 | service/RejectTipMessage | 1 | 1 | 0 | 0 | 100% |
| 279 | service/LanguageInfoSupport | 1 | 1 | 0 | 0 | 100% |

### 3.2 重点类详细解码

#### ChatService (`com/aicode/agent/service/ChatService`)

- 总调用: 267 | high: 219 | medium: 4 | garbage: 44

| 方法 | 品质 | 解码值 |
|------|------|--------|
| Ad() | high | `iurmzisZeodlCmd` |
| Ad() | garbage | `(null)` |
| Ad() | garbage | `(null)` |
| Ad() | high | `a|yoY^Kud` |
| Ad() | high | `YC{d` |
| BD() | high | `]PQD` |
| BD() | garbage | `(null)` |
| CD() | high | `LIU@` |
| Cd() | high | `|IDtd` |
| Cd() | high | `|IDtd` |
| Cd() | garbage | `(null)` |
| DE() | high | `KNTA` |
| EE() | high | `斘仩跨徃丛吘秺万胓且穚Ａ` |
| Ed() | high | `|OBtd` |
| Ed() | high | `|OBtd` |
| Ed() | high | `A^ds` |
| Ed() | high | `2` |
| Ed() | garbage | `(null)` |
| Kf() | high | `DAwb` |
| Lf() | high | `CFwb` |
| Me() | high | `R_pe` |
| Me() | garbage | `(null)` |
| Oe() | high | `iurmzisZeodbMle` |
| Oe() | garbage | `(null)` |
| Oe() | high | `pobiS|fohMst` |
| Oe() | high | `P]pe` |
| Oe() | garbage | `(null)` |
| QD() | high | `^[U@` |
| QE() | high | `￝ﾏfpx$RUME` |
| QE() | high | `JqsnayonYSV` |
| QE() | high | `fnCOno.fvsb)qz}dVUAD` |
| QE() | high | `jmghcEixeakz)a|yo$^_SC` |
| QE() | high | `|[VUE` |
| QE() | high | `{ppiyzknj[TH` |
| QE() | garbage | `(null)` |
| TE() | high | `hx^QCH` |
| TE() | high | `gozPjRL` |
| TE() | high | `hx^QCH` |
| TE() | garbage | `(null)` |
| UD() | high | `JGQD` |
| UD() | high | `fffcyJ_OU` |
| UD() | garbage | `(null)` |
| UD() | high | `JGQD` |
| UD() | high | `vegS_OE` |
| UD() | garbage | `(null)` |
| Uf() | high | `-` |
| Uf() | high | `xoyM_df` |
| VE() | high | `cp{bL{z}fXYGE` |
| Ye() | high | `扦釈釀约凨敺波釸Ｈ{}` |
| ae() | high | `DeiumzqsL{z}fonge` |
| ae() | high | `~spe` |
| ae() | garbage | `(null)` |
| ae() | high | `|kfue` |
| ae() | high | `~spe` |
| ae() | high | `cp{bL{z}fonge` |
| ae() | high | `ved~ont` |
| ae() | high | `fcqTnnxoss` |
| ae() | high | `fcqTnnxoss` |
| ae() | high | `offTnnKth` |
| ae() | high | `offTnnKth` |
| ae() | high | `Kdewlz{`bWtyoFcst` |
| ae() | garbage | `(null)` |
| ae() | garbage | `(null)` |
| ae() | high | `|kfue` |
| bF() | high | `mhWB` |
| bF() | high | `{ppiyzknYhWK` |
| dE() | high | `|ncUE` |
| dE() | high | `ID` |
| dE() | garbage | `(null)` |
| dE() | garbage | `(null)` |
| deleteHistoryItem() | high | `mob}m` |
| deleteHistoryItem() | high | `mob}m` |
| deleteHistoryItem() | high | `VLot|wAl` |
| deleteHistoryItem() | high | `-$&*￘ﾊAl` |
| df() | high | `DeiumzqsL{z}fjkdf` |
| ed() | high | `|obtd` |
| ed() | high | `he` |
| ed() | high | `he` |
| ed() | high | `he` |
| ed() | high | `goza[sm` |
| ed() | high | `goza[sm` |
| ed() | high | `goza[sm` |
| ed() | high | `hxo`bi` |
| ed() | high | `hxo`bi` |
| ed() | high | `hxo`bi` |
| ed() | high | `~aedo` |
| ed() | high | `~aedo` |
| ed() | high | `~aedo` |
| ed() | high | `gpzeZwqd` |
| ed() | high | `-"17￘ﾊqd` |
| ed() | high | `gpzeZwqd` |
| ed() | high | `cyA~do` |
| ed() | garbage | `(null)` |
| enum() | high | `VXQJF}aa!l'6NIM	'q92E{cru9wto*xnfao!pkdd` |
| enum() | high | `?;vsq2:ﾄ￲Apr^D/sdsslec>RdmX}ﾗￜ>￘ﾊkm` |
| enum() | high | `qduPwo~ffblv}u@lliq` |
| enum() | high | `qdu_pih~Icjg^drmom` |
| enum() | high | `mnTrldNriq` |
| getAgentChatResponse() | high | `|yta` |
| getAgentChatResponse() | high | `lape` |
| getAgentChatResponse() | high | `wytue` |
| getAgentChatResponse() | high | `id` |
| getAgentChatResponse() | garbage | `(null)` |
| getAgentChatResponse() | high | `|yta` |
| getAgentChatResponse() | high | `hmnvwrdLape` |
| getAgentChatResponse() | high | `l}xt` |
| getAgentChatResponse() | garbage | `(null)` |
| getAgentChatResponse() | high | `k|rqwvse` |
| getAgentChatResponse() | high | `l}xt` |
| getAgentChatResponse() | high | `l{xjnoL}xt` |
| getAgentChatResponse() | high | `e{mqwZnol}nt` |
| getAgentChatResponse() | high | `l{xjnoL}xt` |
| getAgentChatResponse() | high | `lape` |
| getAgentChatResponse() | high | `lape` |
| getAgentChatResponse() | high | `ldlpod` |
| getAgentChatResponse() | high | `|yta` |
| getAgentChatResponse() | high | `w{lu}vvoVyme` |
| getAgentChatResponse() | high | `k|rqwvse` |
| getAgentChatResponse() | high | `w{lu}vvoVyme` |
| getAgentChatResponse() | garbage | `(null)` |
| getAgentChatResponse() | high | `djjor` |
| getAgentChatResponse() | high | `djjor` |
| getAgentChatResponse() | high | `k|rqwvse` |
| getAgentChatResponse() | high | `tdrkyge` |
| getAgentChatResponse() | high | `hmnvwrdLape` |
| getAgentChatResponse() | high | `djjor` |
| getAgentChatResponse() | high | `dv|ed` |
| getAgentChatResponse() | high | `siWpK|rqwvse` |
| getAgentChatResponse() | high | `siWpK|rqwvse` |
| getErrorChatResponse() | high | `lape` |
| getErrorChatResponse() | high | `wytue` |
| getErrorChatResponse() | high | `id` |
| getErrorChatResponse() | garbage | `(null)` |
| getErrorChatResponse() | high | `k|rqwvse` |
| getErrorChatResponse() | garbage | `(null)` |
| getErrorChatResponse() | high | `djjor` |
| getErrorChatResponse() | garbage | `(null)` |
| getErrorChatResponse() | garbage | `(null)` |
| getErrorChatResponse() | garbage | `(null)` |
| getFirstChatMessage() | garbage | `(null)` |
| getFirstChatMessage2Web() | high | `Nobrb` |
| getFirstChatMessage2Web() | high | `Nobrb` |
| getFirstChatMessage2Web() | high | `ge!(TQikis` |
| getFirstChatMessage2Web() | high | `zwwb` |
| getFirstChatMessage2Web() | high | `'WUcoic` |
| getFirstChatMessage2Web() | high | `zwwb` |
| getFirstChatMessage2Web() | high | `Nobrb` |
| getFirstChatMessage2Web() | high | `zwwb` |
| getFirstChatMessage2Web() | high | `j77QKzois` |
| getFirstChatMessage2Web() | high | `zwwb` |
| getFirstChatMessage2Web() | high | `Nobrb` |
| getFirstChatMessage2Web() | high | `zwwb` |
| getFirstChatMessage2Web() | high | `j77QKzois` |
| getFirstChatMessage2Web() | garbage | `(null)` |
| getFirstChatMessage2Web() | high | `HY|ojt` |
| getFirstChatMessage2Web() | high | `6ￃﾤ|ojt` |
| getFirstChatMessage2Web() | high | `|kgj0!\~gbbt` |
| getFirstChatMessage2Web() | high | `|kgj0!\~gbbt` |
| getFirstChatMessage2Web() | high | `josf` |
| getFirstChatMessage2Web() | high | `josf` |
| getGamePlay() | high | `(!0ￖﾄ` |
| getGamePlay() | high | `nkh}` |
| getGamePlay() | high | `'=45ￖﾄ` |
| getKnowledgeChatResponse() | high | `|yta` |
| getKnowledgeChatResponse() | high | `|yta` |
| getKnowledgeChatResponse() | high | `lwruj}am` |
| getKnowledgeChatResponse() | high | `lwruj}am` |
| getKnowledgeChatResponse() | high | `lape` |
| getKnowledgeChatResponse() | high | `wytue` |
| getKnowledgeChatResponse() | high | `id` |
| getKnowledgeChatResponse() | garbage | `(null)` |
| getKnowledgeChatResponse() | garbage | `(null)` |
| getKnowledgeChatResponse() | high | `lwruj}am` |
| getKnowledgeChatResponse() | garbage | `(null)` |
| getKnowledgeChatResponse() | high | `IU~yqspyIknfj}ss` |
| getKnowledgeChatResponse() | high | `IU~yqspyIknfj}ss` |
| getKnowledgeChatResponse() | high | `cstuww~Sdkmlt` |
| getKnowledgeChatResponse() | high | `cstuww~Sdkmlt` |
| getKnowledgeChatResponse() | high | `cstuww~Sdkmlt` |
| getKnowledgeChatResponse() | high | `siWpK|rqwvse` |
| getRequestForTalkHistory() | high | `vxuiy` |
| getRequestForTalkHistory() | high | `$.'5ￖﾄ` |
| getTalkHistory() | high | `}xh}` |
| getTalkHistory() | high | `ngqsivwUx` |
| getTalkHistory() | high | `gkTLern|vkonUpoh` |
| getTalkHistory() | high | `coskgpsampsr` |
| getTalkHistory() | high | `;20ￖﾄ` |
| getTalkHistory() | garbage | `(null)` |
| getTalkList() | high | `|yep` |
| getTalkList() | high | `laat` |
| getTalkList() | garbage | `(null)` |
| getTalkPredictResult() | high | `cfep` |
| getTalkPredictResult() | high | `cfep` |
| getTalkPredictResult() | high | `s~at` |
| getTalkPredictResult() | garbage | `(null)` |
| handleAction() | high | `p}{n` |
| handleChatDeleteMsg() | high | `Tytwg` |
| handleChatDeleteMsg() | high | `Tytwg` |
| handleChatDeleteMsg() | high | `kf` |
| handleChatDeleteMsg() | high | `iv`QKwvKf` |
| handleChatStop() | high | `meh`p` |
| handleChatStop() | high | `meh`p` |
| handleChatStop() | high | `p}ep` |
| handleChatStop() | high | `meh`p` |
| handleChatStop() | high | `|q` |
| handleChatStop() | garbage | `(null)` |
| handleChatStop() | high | `(ￚﾽhkkjfp` |
| handleChatStop() | garbage | `(null)` |
| handleChatStop() | high | `wpze` |
| handleChatStop() | garbage | `(null)` |
| handleCodeComment() | high | `adep` |
| handleCodeComment() | high | `1ￜﾎqm~` |
| handleCodeComment() | high | `atsXdkvt` |
| handleCodeComment() | medium | `ilt` |
| handleCodeComment() | high | ``szxkfqtc` |
| handleCodeComment() | high | `xdkvt` |
| handleCodeComment() | medium | `ilt` |
| handleCodeComment() | high | ``szxkfqtc` |
| handleCodeComment() | high | `zsqm~` |
| handleCodeComment() | medium | `ilt` |
| handleCodeComment() | high | ``szxkfqtc` |
| handleCodeComment() | medium | `ilt` |
| handleCodeComment() | high | ``szxkfqtc` |
| handleCodeComment() | high | `重纹斂桦泹釛` |
| handleCodeDebug() | high | `~v{nn` |
| handleCodeDebug() | high | `~v{nn` |
| handleFeedbackCategory() | high | `}xh}` |
| handleParseWebUrlErr() | high | `m`gr` |
| handleParseWebUrlErr() | garbage | `(null)` |
| handleParseWebUrlErr() | high | `1ￃﾤjxpr` |
| handleParseWebUrlErr() | high | `aic~ug,apdes8SAh$ebPynhuXm|oc` |
| handleParseWebUrlErr() | high | `1ￃﾤjxpr` |
| handleParseWebUrlErr() | high | `'6;.|/77\=&7*ￖﾄr8VA{ucHynhuXm|oc` |
| handleParseWebUrlErr() | high | `\xubr` |
| isChat() | high | `Qacyc%uoid,reroo0\[ah0*jl` |
| mD() | high | `bgU@` |
| oE() | high | `p}PE` |
| oE() | high | `fffcypeNT` |
| oE() | garbage | `(null)` |
| oE() | high | `p}PE` |
| oE() | high | `vegieND` |
| oE() | garbage | `(null)` |
| oE() | high | `p}PE` |
| oE() | garbage | `ￂﾥytdmqeGE` |
| oE() | high | `|ehUE` |
| oE() | high | `请幁找刦枰练竤皀抡锞既忷Ｌ幼揚供解冬斦桏々叿觶冹符丄丮抅锹` |
| oE() | high | `|ehUE` |
| of() | high | `#` |
| of() | garbage | `	` |
| of() | garbage | `	` |
| of() | high | `重纹斃桧泫釉` |
| qD() | high | `~{U@` |
| qd() | high | `ncqd` |
| qd() | garbage | `(null)` |
| rE() | high | `*` |
| refreshAgent() | high | `ldyoj"mKAibt?Ed|ansKiuhnoh]~dz$q`ie` |
| send2Agent() | high | `q|at` |
| send2Agent() | high | `reghdu` |
| send2Agent() | high | `q|at` |
| send2Agent() | high | `|didt` |
| send2Agent() | high | `诂訑俰恥莽叓弇帩+` |
| sendError2Web() | high | `Wt~odc(t%0]||hu` |
| sendError2Web() | high | `ct`` |
| sendError2Web() | high | `ct`` |
| sendError2Web() | high | `macb` |
| sendError2Web() | high | `macb` |
| ye() | high | `vsta` |

#### CodeCompleteService (`com/aicode/agent/service/CodeCompleteService`)

- 总调用: 4 | high: 4 | medium: 0 | garbage: 0

| 方法 | 品质 | 解码值 |
|------|------|--------|
| handleAgentAction() | high | `hmcv` |
| handleAgentAction() | high | `Kb`rncrmkex` |
| handleAgentAction() | high | `hmcv` |
| handleAgentAction() | high | `p}{n` |

#### IdeActionService (Inline Chat) (`com/aicode/inline/ide/IdeActionService`)

- 总调用: 50 | high: 49 | medium: 1 | garbage: 0

| 方法 | 品质 | 解码值 |
|------|------|--------|
| enum() | high | `KPOt}}f+CRhoIS#(c0hpy~A_D&trcpwt:by{{` |
| enum() | high | `sVUrpX\@H}ﾁﾟ`dcSrd.P}~Zi~bd}@dshwkm` |
| enum() | high | `Wcr^s`Dynecyd` |
| wa() | high | `OdikpuEtvaYl}ga` |
| wa() | high | `Cb~cjw^`icr` |
| wa() | high | `Bq|~en_qp` |
| wa() | high | `RslquhOcgn` |
| wa() | high | `ZcnazxZ}opa` |
| wa() | high | `Cb~cjw_iomgr` |
| wa() | high | `ZcnazxOrhav` |
| wa() | high | `Ralnu~Xvu` |
| wa() | high | `OnitpmCbyp~oPuja` |
| wa() | medium | `:==.5
*3 ':￹ﾽjw~Ixmec` |
| wa() | high | `sSHhsXrDP]WYﾒﾮzBexxYj`` |
| wa() | high | `Rslquh@iqc` |
| wa() | high | `ZcnazxXu{lp` |
| wa() | high | `@asnc~Bg` |
| wa() | high | `Bc|aexXssj` |
| wa() | high | `OnF[_BUrveqKm@eyr` |
| wa() | high | `OytTOxCndzqsTpyoihukj` |
| wa() | high | `Htyqjx_AF^TchcD`iyxexy` |
| wa() | high | `sT_CNnXEAOkQWﾩﾒTpyoihukj` |
| wa() | high | `Ual~e]z@gorD`iyxexy` |
| wa() | high | `sT_CNnPOPUkQWﾩﾒTpyoihukj` |
| wa() | high | `HiydjwXcHGDgorD`iyxexy` |
| wa() | high | `Q_HRGSyYu~JDR`WE@{nKFIkQWﾩﾒTpyoihukj` |
| wa() | high | `T"/RIrFB_WAi@qwdmxN_XgorD`iyxexy` |
| wa() | high | `[mBBYBfVFyXEAOkQWﾩﾒTpyoihukj` |
| wa() | high | `eiddwUkmJz@gorD`iyxexy` |
| wa() | high | `z`_YD|_YDOhKDUkQWﾩﾒTpyoihukj` |
| wa() | high | `�ﾹdybIldojATgorD`iyxexy` |
| wa() | high | `EdvkhuEtmoXssj` |
| wa() | high | `Cb~cjwJ{kiBg` |
| wa() | high | `OdikpuK|{oYh}vp` |
| wa() | high | `ubocxwIstiIys` |
| wa() | high | `OnitpmTbypi~Ksv`` |
| wa() | high | `Utlqexi@BGgtsGdw{}~mg` |
| wa() | high | `OncirRbkikw~mcWtgkmn}tl` |
| wa() | high | `3D`iyxM{{` |
| wa() | high | `[mBBYBxRYhKEDEkQWﾩﾒTpyoihukj` |
| wa() | high | `cdiYBRphfyjpy]@]TgorD`iyxexy` |
| wa() | high | `Edvkhu[pr~Ksv`` |
| wa() | high | `OnF[_BVtraljoi[ces` |
| wa() | high | `RpqbY|doXssj` |
| wa() | high | `Zxs`VsbiBg` |
| wa() | high | `GovzLsfapgorhQt` |
| wa() | high | `G@YUcrgcrh`tnHc`y` |
| wa() | high | `eDc~orOmbq|zyKsv`` |
| wa() | high | `uTorxeK`bn[ces` |
| wa() | high | `OnitpmCbyp~oPuja` |

#### InlineChatService (`com/aicode/inline/InlineChatService`)

- 总调用: 5 | high: 5 | medium: 0 | garbage: 0

| 方法 | 品质 | 解码值 |
|------|------|--------|
| enum() | high | `Lpb&.d~:|NS,LTuyC4-AVGljb~o{)Z"mq7E83r!tlho*dd3qd!pkdd` |
| enum() | high | `zjhiﾏﾊ~XbkH|~qnp{f|` |
| enum() | high | `dewjgz` |
| enum() | high | `ajb+mC@gH@(ﾆﾃyZ_xs.HwuruoIcjg@dshwkm` |
| enum() | high | `zwtyoBezod]vi|` |

#### InlineChatStreamHandleService (`com/aicode/inline/InlineChatStreamHandleService`)

- 总调用: 19 | high: 11 | medium: 0 | garbage: 8

| 方法 | 品质 | 解码值 |
|------|------|--------|
| GA() | high | `Z` |
| Gc() | garbage | `` |
| La() | garbage | `` |
| OA() | high | `faliNE6uu|p$SuﾗﾗﾏﾏoyPMKJ` |
| OA() | high | `Qoexs`+tuxetr` |
| Ua() | garbage | `` |
| WB() | high | `==` |
| eA() | garbage | `` |
| enum() | high | `U[VMxCll:wo~mjAZ!*y*e}z}J_D&trcpwt:by{{` |
| enum() | high | `ZY[/asyenﾝﾝﾱﾘbJOkﾝﾕ-BoIDacEXWtEus`xﾖﾋSkdogv@dshwkm` |
| enum() | high | `p`qIn~eyp` |
| gB() | high | `DDPZq<>:Yt%plww` |
| gB() | high | `[uI{vV~p{I` |
| gB() | garbage | `` |
| pc() | garbage | `` |
| pc() | garbage | `` |
| zA() | high | `jj`(Ch,.*Id5mqDD` |
| zA() | garbage | `` |
| zA() | high | `..` |

#### InlineChatHandleService (`com/aicode/inline/InlineChatHandleService`)

- 总调用: 15 | high: 11 | medium: 0 | garbage: 4

| 方法 | 品质 | 解码值 |
|------|------|--------|
| Af() | garbage | `` |
| Ee() | high | `JOta` |
| Hd() | garbage | `` |
| OA() | high | `cddaglGXQr&Dbbbixexy` |
| OA() | high | `av|hcp;dePMGA` |
| Tf() | high | `/cddaglGXQr&Dbbbixexy` |
| Tf() | high | `av|hcp;deKV`f` |
| UE() | high | `hmcv` |
| eD() | high | `ﾬﾢ` |
| handleData() | high | `aav>Yr "n`1cdd` |
| handleData() | high | `KeYkfF~pKy` |
| handleData() | garbage | `` |
| handleData() | garbage | `` |
| kE() | high | `*` |
| lf() | high | `==` |

#### InlineChatPanel (`com/aicode/inline/InlineChatPanel`)

- 总调用: 25 | high: 24 | medium: 0 | garbage: 1

| 方法 | 品质 | 解码值 |
|------|------|--------|
| eB() | high | `@f`zBU` |
| enum() | high | `@s,>ogsim+DY-MYx?fv:Q@~mwyh3a
U-ba'/y',UD\qv7yZA2px=drr` |
| enum() | garbage | `(null)` |
| enum() | high | `YHpgycq\abrnkhr{` |
| enum() | high | `dewjgz` |
| enum() | high | `V}mrdopj` |
| enum() | high | `x}f` |
| enum() | high | `n.,LOEbib(cde`HC`ln~yPvzsiMkd{r` |
| enum() | high | `;<ￅﾗRulqcm` |
| enum() | high | `tdfg` |
| enum() | high | `o` |
| enum() | high | `cvGRwfOxmcqp` |
| enum() | high | `hytxCvgahckjm{` |
| enum() | high | `qrs~opj` |
| enum() | high | `90:8;?<v98/<ﾒￜGrpebgA{ruQpmd` |
| enum() | high | `RwfXyc~ql` |
| enum() | high | `lapNiOJ`k_tmxKlcfuQpmd` |
| enum() | high | `!tdcj ` |
| enum() | high | `ubMnnP{hmzlmf` |
| enum() | high | `wexij{` |
| enum() | high | `@obKehvjZ}Qhf{d{` |
| enum() | high | `EGEJJg~p]A[|Mtrorm` |
| enum() | high | `#)￘ﾊf|` |
| enum() | high | `FPfQrs~opj` |
| ub() | high | `PSsaw[fiﾲﾩCG]NkyrP``ro0*ec` |

#### GitReviewService (`com/aicode/agent/service/GitReviewService`)

- 总调用: 23 | high: 21 | medium: 1 | garbage: 1

| 方法 | 品质 | 解码值 |
|------|------|--------|
| getCommitMessage() | high | `lmzﾜﾜ` |
| getCommitMessage() | high | `xixt` |
| getCommitMessage() | high | `xixt` |
| getCommitMessage() | high | `kbhed` |
| getCommitMessage() | high | `ﾖﾖbhed` |
| getCommitMessage() | high | `暇日揞亪俭恣生成` |
| getGiffDiff() | high | `ytsf` |
| getGiffDiff() | garbage | `(null)` |
| getGiffReview() | high | `zwbw` |
| getGiffReview() | high | `jofs` |
| getGiffReview() | high | `c`jwv` |
| getGiffReview() | high | `pobgw` |
| handleAction() | high | `peh~n` |
| handleAction() | medium | `tec` |
| handleAgentAction() | high | `bo` |
| if() | high | `())` |
| if() | high | `xtxafmtm` |
| if() | high | `xtxafmtm` |
| removeMarkdownCodeBlocks() | high | `=o0Gll`.<2` |
| removeMarkdownCodeBlocks() | high | `>#y0l$?!v?` |
| sendCodeReviewRequest() | high | `|ytdt` |
| sendCodeReviewRequest() | high | `hyey` |
| sendCodeReviewRequest() | high | `|yep` |

#### SqlService (`com/aicode/agent/service/SqlService`)

- 总调用: 67 | high: 53 | medium: 1 | garbage: 13

| 方法 | 品质 | 解码值 |
|------|------|--------|
| getSourceList() | high | `laat` |
| getSourceList() | garbage | `(null)` |
| getSourceType() | medium | `zta` |
| getSourceType() | high | `obpe` |
| getSourceType() | garbage | `(null)` |
| getSqlChat() | high | `xu` |
| getSqlChat() | garbage | `(null)` |
| getSqlChat() | high | `j}qrZ[tb^sat` |
| getSqlChat() | high | `~oie` |
| getSqlChat() | garbage | `(null)` |
| getSqlChat() | high | `nkep` |
| getSqlChat() | high | `~oie` |
| getSqlChat() | high | `~oie` |
| getSqlChat() | high | `bdntu` |
| getSqlChat() | high | `bdntu` |
| getSqlChat() | high | `qkKlgPtwedbt` |
| getSqlChat() | high | `j}qrZ[tb^sat` |
| getSqlChat() | high | `bxx~c` |
| getSqlChat() | garbage | `(null)` |
| getSqlChat() | high | `GPtwedbt` |
| getSqlChat() | high | `~sat` |
| getSqlChat() | garbage | `(null)` |
| getTableList() | high | `laat` |
| getTableList() | garbage | `(null)` |
| handleAction() | high | `peh~n` |
| handleAction() | high | `peh~n` |
| handleAction() | high | `peh~n` |
| handleSqlChatMessage() | high | `xm`ue` |
| handleSqlChatMessage() | high | `xm`ue` |
| handleSqlChatStop() | high | `meh`p` |
| handleSqlChatStop() | high | `meh`p` |
| handleSqlChatStop() | high | `|q` |
| handleSqlChatStop() | garbage | `(null)` |
| handleSqlChatStop() | high | `W@hkkjfp` |
| handleSqlChatStop() | garbage | `(null)` |
| handleSqlChatStop() | high | `pama` |
| handleSqlChatStop() | garbage | `(null)` |
| handleSqlChatStop() | high | `p}ep` |
| handleSqlChatStop() | garbage | `(null)` |
| handleSqlDelete() | high | `|~sue` |
| handleSqlDelete() | high | `|~sue` |
| handleSqlTableList() | high | `pytdt` |
| handleSqlTableList() | high | `pytdt` |
| kF() | high | `vispyfcgeMW` |
| kF() | high | `tySF` |
| kF() | high | `vegmaMG` |
| kF() | high | `tySF` |
| kF() | high | `|alVF` |
| kF() | high | `zkraNP` |
| kF() | high | `f{fInEL` |
| saveSource() | high | `|qpe` |
| saveSource() | garbage | `(null)` |
| sf() | high | `|ytvf` |
| sf() | high | `|ytvf` |
| sf() | high | `ifq}mw` |
| sf() | high | `pwpw` |
| sf() | high | `hwqw` |
| sf() | high | `jg` |
| sf() | high | `jg` |
| sf() | high | `mkfq` |
| sf() | high | `mkfq` |
| sf() | high | `etyyowqg` |
| sf() | high | `etyyowqg` |
| sf() | high | `qt~kzypf` |
| sf() | high | `qt~kzypf` |
| testConnect() | high | `|qat` |
| testConnect() | garbage | `(null)` |

#### CodeCheckService (`com/aicode/agent/service/CodeCheckService`)

- 总调用: 29 | high: 23 | medium: 2 | garbage: 4

| 方法 | 品质 | 解码值 |
|------|------|--------|
| fixCodeCheck() | medium | `himpr` |
| getAgentChatResponse() | high | `hmcv` |
| getAgentChatResponse() | high | `xugr` |
| getAgentChatResponse() | high | `lm`br` |
| getAgentChatResponse() | high | `~s` |
| getAgentChatResponse() | high | `hmcv` |
| getAgentChatResponse() | high | `tcdgjkiXugr` |
| getAgentChatResponse() | garbage | `(null)` |
| getAgentChatResponse() | garbage | `(null)` |
| getAgentChatResponse() | high | `w`ijcbdr` |
| getAgentChatResponse() | high | `xioc` |
| getAgentChatResponse() | high | `4)5ￓﾅe` |
| getAgentChatResponse() | high | `4)5ￓﾅe` |
| getAgentChatResponse() | high | `w`ijcbdr` |
| getAgentChatResponse() | medium | `himpr` |
| getAgentChatResponse() | high | `tcdgjkiXugr` |
| getAgentChatResponse() | high | `4)5ￓﾅe` |
| getAgentChatResponse() | high | `bhrs` |
| getAgentChatResponse() | high | `ou^yW`ijcbdr` |
| getAgentChatResponse() | high | `ou^yW`ijcbdr` |
| getCheckData() | high | `hmcv` |
| getErrorList() | high | `NBhi'jncIAUkvcn+futxiyc` |
| getErrorResponse() | high | `xugr` |
| getErrorResponse() | high | `lm`br` |
| getErrorResponse() | high | `~s` |
| getErrorResponse() | high | `w`ijcbdr` |
| getErrorResponse() | garbage | `(null)` |
| getErrorResponse() | high | `4)5ￓﾅe` |
| getErrorResponse() | garbage | `(null)` |

#### CommonService (`com/aicode/agent/service/CommonService`)

- 总调用: 107 | high: 97 | medium: 1 | garbage: 9

| 方法 | 品质 | 解码值 |
|------|------|--------|
| BE() | high | `jsn72GGMajk OntiORKitipq@uz`z$]LXT` |
| Gd() | high | `agdtcir}V(>jbcyNi`gBU"Dbg/KF~e^ao`mknﾄ￀R!,￁ﾓlkfivp*o^^ns` |
| Ge() | high | ` ` |
| Ge() | garbage | `	` |
| Ge() | garbage | `
` |
| ID() | high | `FCU@` |
| IE() | high | `@L|?34o~ff|]glhDor+%NR Fg`z}$CsqAYBcntlOftfSkiVMRY` |
| IE() | high | `".Meb(9hhmn?rLGtX4)r|b~8r_[egc#hl2.o@BowFgd~nMASyLackpu~\{~oPLAL` |
| IE() | high | `DzxzkLKON` |
| IE() | high | `ha|moBtsTS_zdakzHi[pr~rCSS` |
| KF() | high | `AoYMBS` |
| WC() | garbage | `` |
| addLineIndent() | high | `1` |
| addLineIndent() | garbage | `` |
| addLineIndent() | garbage | `` |
| cF() | high | `|qSF` |
| cF() | high | `xifDF` |
| cF() | high | `daMF` |
| cF() | high | `d}txkk|FQ` |
| cF() | high | `w`goXifDF` |
| cF() | high | `daMF` |
| cF() | high | `d}txkk|FQ` |
| cF() | high | `./9~<6,!6;r%9 ￝ﾏva*ozzLQ` |
| enum() | high | `:1#q~_<%;5>^Y￐ﾂ#(E{cru9wto*xnfao!pkdd` |
| enum() | high | `gk-oFN`llmMkicyEsdwhz|4Xegfd}@dshwkm` |
| enum() | high | `jSBFddr|woz~cdeWru`Zjg{` |
| enum() | high | `|~~YngzofmZ|g` |
| getConfig() | high | `v{rg` |
| getConfig() | high | `cnwg` |
| getPluginInfo() | high | `ytzo` |
| getPluginInfo() | high | `elugiQids~ded` |
| getPluginInfo() | high | `"")1f*+<5<<[/ￜﾎ~ded` |
| getPluginInfo() | high | `relZ^rdIl~o` |
| getPluginInfo() | high | `eoe~uFoso}|{.pubSVb/il~o` |
| getPluginInfo() | high | `idZids~ded` |
| getPluginInfo() | garbage | `(null)` |
| getPluginInfo() | high | `Pr/clgo` |
| getPluginInfo() | garbage | `(null)` |
| getPluginInfo() | high | `}paPlugVQHokb弈帲` |
| handleAction() | high | `TLNZXfw]LEGYTAEOR` |
| handleChatFeedback() | high | `xid{k` |
| handleChatFeedback() | high | `xid{k` |
| handleChatFeedback() | high | `gj` |
| handleChatFeedback() | high | `o|ux{o|aa`` |
| handleChatFeedback() | high | `xqe{k{|Gj` |
| handleChatFeedback() | high | `o|ux{o|aa`` |
| handleChatFocusFile() | high | `pfkue` |
| handleChatFocusFile() | high | `pfkue` |
| handleChatFocusFile() | high | `id` |
| handleChatFocusFile() | high | `S_bcNifo` |
| handleChatFocusFile() | high | `wfth` |
| handleChatFocusFile() | high | `tfige` |
| handleChatFocusFile() | high | `knne` |
| handleChatFocusFile() | high | `knne` |
| handleChatFocusFileLine() | high | `pdiue` |
| handleChatFocusFileLine() | high | `pdiue` |
| handleChatFocusFileLine() | high | `\SjcUdth` |
| handleChatFocusFileLine() | high | `xN[trIlne` |
| handleEval() | high | `okf|l` |
| handleEval() | high | ``m` |
| handleEval() | high | `~syl` |
| handleEval() | high | `k|gk{b` |
| handleEval() | high | `yVBl|y~@m` |
| handleEval() | high | `~syl` |
| handleEval() | high | `k|gk{b` |
| isSupportJava() | high | `GKg$y~BSjjji1ont5Kisv]xo[tha` |
| logOperate() | high | `yau{klkId` |
| logOperate() | garbage | `(null)` |
| messageBus() | high | `2(93?<o<￘ﾊus` |
| messageBus() | high | `gEX|y9v^Tene.Edwjyd]uhgfN{|f]j{nb` |
| openFile() | high | `斁仱上存在` |
| openFile() | high | `彟前平叀丽攩指扔弇文件` |
| openFile() | high | `扣弆斁仱夶败！` |
| openFile() | high | `扣弆斁仱夶败！` |
| openFileDialog() | high | `uehwg` |
| openFileDialog() | high | `q~ofTevj` |
| openFileDialog() | high | `p}rg` |
| openFileDialog() | high | `bmng` |
| openPage() | high | `xupe` |
| openPage() | high | `xm`ue` |
| openUrl() | high | `_\NOZYyqFL[E` |
| openUrl() | high | `P\^_OCOKO^QFYNHWisMV[P` |
| openUrl() | high | `U\VYIC\QFNNIKQnFL[E` |
| openUrl() | high | `CQPLﾬﾢ_^WUUH\N\FHDV^s}FL[E` |
| openUrl() | high | `_NXKFHNVLhFL[E` |
| openUrl() | high | `Yﾦﾨr|g4` |
| re() | high | `jghl$nxLEi~.eros~;vedm|nt` |
| re() | high | `jghl$nxLEi~.eros~;vedm|nt` |
| refreshFunctionAction() | medium | ``ej` |
| refreshFunctionAction() | high | `%6,:r0,6=%+<'n**+$m1 $8ￖﾄ` |
| saveShowOperateGuidance() | high | `BgptBlkpHfsZIsbGucnvybd叙选异常` |
| sd() | high | `>` |
| sd() | high | `>` |
| sd() | high | `'` |
| sd() | high | `pljxniCpxyqwo<` |
| sd() | high | `kt~ODo$pljxni;coxkqno` |
| sd() | high | `,ilizqsItxo7Q\D@` |
| sd() | high | `t￝ﾏfpxDyud<` |
| sd() | high | `5kdek{sitwwﾢﾬe<` |
| sd() | high | `3`yojQe<` |
| tC() | high | `@{~cksCB` |
| tC() | high | `\pBC` |
| updateConfig() | high | `cnwg` |
| updateConfig() | high | `rqK_muTygnwceg` |
| updateConfig() | high | `cwvm` |
| updateConfig() | high | `5￘ﾊvm` |
| updateConfig() | garbage | `(null)` |

#### UserService (`com/aicode/agent/service/UserService`)

- 总调用: 56 | high: 44 | medium: 2 | garbage: 10

| 方法 | 品质 | 解码值 |
|------|------|--------|
| SetModel() | high | `}ob|l` |
| getLoginInfo() | high | `il~k` |
| getLoginInfo() | high | `il~k` |
| getLoginInfo() | high | `ytzo` |
| getLoginInfo() | high | `x~ox` |
| getLoginInfo() | garbage | `(null)` |
| getLoginInfo() | garbage | `(null)` |
| getLoginInfo() | garbage | `(null)` |
| getLoginInfo() | garbage | `(null)` |
| getLoginInfo() | garbage | `(null)` |
| getLoginInfo() | high | `f}owSa^LPsNbno` |
| getLoginInfo() | high | `wlao` |
| getLoginUrl() | high | `m`yl` |
| getLoginUrl() | high | `tw]SwL{e` |
| getLoginUrl() | garbage | `(null)` |
| getLoginUrl() | high | `cuhcFmq{ini6n_Hjpfg` |
| getLoginUrl() | high | `Lxu|l` |
| getUserModelList() | high | `|yep` |
| getUserModelList() | high | `|yep` |
| getUserModelList() | high | `laat` |
| getUserModelList() | high | `oU^cjTqbe` |
| getUserModelList() | high | `pytdt` |
| getUserPermissions() | high | `adbw` |
| getUserPermissions() | high | `adbw` |
| getUserPermissions() | high | `k~hwtnlvonFjrs` |
| getUserPermissions() | high | `qdporhisrs\pdeIleb` |
| getUserPermissions() | high | `q|fs` |
| getUserPermissions() | garbage | `(null)` |
| handleAgentAction() | high | `p}{n` |
| handleAgentAction() | garbage | `(null)` |
| handleAgentAction() | high | `适凼白彑戔劔＊` |
| handleAgentAction() | medium | ``ej` |
| handleAgentAction() | medium | ``ej` |
| handleAgentAction() | high | `pcJkcbe` |
| handleAgentAction() | high | `白彑戔劔＊` |
| handleAgentAction() | high | `癹彗或劙揔社夺贮` |
| send2WebShowOperateGuidance() | high | `chqaffo` |
| send2WebShowOperateGuidance() | high | `~cg~ogg丅存在` |
| send2WebShowOperateGuidance() | high | `chqaffo` |
| send2WebShowOperateGuidance() | high | `}{er` |
| send2WebShowOperateGuidance() | high | `}{er` |
| send2WebShowOperateGuidance() | high | `|qpe` |
| send2WebShowOperateGuidance() | high | `XNOOcdvUC_U[WOHAIgxBUATOUPBHEIFCE` |
| send2WebShowOperateGuidance() | high | `e{@hpOpoxvcdF}ade` |
| send2WebShowOperateGuidance() | garbage | `(null)` |
| send2WebShowOperateGuidance() | high | `hODUQcsBlkpHfsZIsbGucnvybd叙选异常` |
| sendWriterConfig() | high | `v{rg` |
| sendWriterConfig() | high | `]Xhu}jJfldke` |
| sendWriterConfig() | high | `fcvc` |
| sendWriterConfig() | high | `]Xhu}jJfldke` |
| sendWriterConfig() | garbage | `(null)` |
| showMessage() | high | `lwTZb"|hqm ka|ty` |
| showMessage() | high | `Uydcf4{eoMFc(ﾨﾠzlg{fﾰﾤ{KVcbZojp~ xixt` |
| showMessage() | high | `ￓﾣeory`+`axece` |
| showMessage() | high | `Uydcf4{eoMFc(ﾨﾠzlg{fﾰﾤ{KVcbZojp~ xixt` |
| showMessage() | high | `厷登录` |

#### AICodeStringUtil (`com/aicode/util/AICodeStringUtil`)

- 总调用: 31 | high: 29 | medium: 1 | garbage: 1

| 方法 | 品质 | 解码值 |
|------|------|--------|
| enum() | high | `Arvdgois1w0PNouOvo&jyhZI@Hnz('%a57xw1)38g4WOur nmv3qd!pkdd` |
| enum() | garbage | `(null)` |
| enum() | high | `j{p|` |
| enum() | high | `kgm/s{txut#yitx;{sEideQvazofKjad` |
| enum() | high | `wivjGa`hrwfo` |
| enum() | high | `wkv{_d`zwfo` |
| enum() | high | `dewjgz` |
| enum() | high | `am~cmdjwgf` |
| enum() | medium | `z|i` |
| enum() | high | `pvuds{pkm` |
| enum() | high | `i` |
| enum() | high | `j` |
| enum() | high | `_^ororNk}vRtxxap` |
| enum() | high | `kgm/s{txut#yitx;{sEideQvazofKjad` |
| enum() | high | ``f[SjongUjzgdrnkm` |
| enum() | high | `x_[bongUjzgdrnkm` |
| enum() | high | `stpkc_d`zwfo` |
| enum() | high | `qrzuMwpm{` |
| enum() | high | ``f[SjongUjzgdrnkm` |
| enum() | high | `ecmeqtzsmRoresrcpvMdpy|`` |
| enum() | high | `x_[bongUjzgdrnkm` |
| enum() | high | `}imytzsmRoresrcpvMdpy|`` |
| enum() | high | `lesv~yvFdeixgd[YcJenev{DhuvJij` |
| enum() | high | `stpkc_d`zwfo` |
| enum() | high | `{tzpuLctlarrz}fMwpm{` |
| enum() | high | `YHcgteFkuuHorq{` |
| enum() | high | `\ShbCooo|}Qs{xap` |
| enum() | high | `gevLvkuMwpm{` |
| enum() | high | `nk}vrLjk`` |
| enum() | high | `yuNYnUufdkk@dbjwgf` |
| enum() | high | `s{`w[rehSwduzyHpzmp` |

## 4. 中文 UI 字符串与 i18n 文档交叉验证

H() 解码出的中文字符串大多呈现"乱码化中文"特征——字符为 CJK 统一表意文字，但组合后不构成有意义的中文词句。
这表明 XOR 解码在多字节 UTF-8 字符上存在偏移或编码问题，导致中文恢复率极低。

### 4.1 可部分辨识的中文解码条目

| 类.方法() | 解码值 | 可能原文推测 | i18n 文档匹配 |
|-----------|--------|-------------|--------------|
| UserService.showMessage() | `厷登录` | 未登录 | aicode.not.signed = 未登录 |
| LogoutAction.update() | `厤登录` | 已登录 | (状态显示) |
| LogoutAction.E() | `去癧彉` | 退出登录 | action.logout = 退出 |
| LogoutAction.E() | `逅凿癛彵` | 确认退出？ | (确认对话框) |
| GitBranchChangeListener.va() | `忽略` | 忽略 | (Git 忽略文件) |
| GitBranchChangeListener.handleGitResponse() | `俀孒戚功!` | Git 操作成功! | (Git 操作反馈) |
| CommonService.openFile() | `斁仱上存在` | 文件已存在 | (文件操作提示) |
| CommonService.openFile() | `彟前平叀丽攩指扔弇文件` | 当前文件正在被编辑 | (文件锁定提示) |
| CommonService.openFile() | `扣弆斁仱夶败！` | 打开文件失败！ | (文件操作错误) |
| UserService.handleAgentAction() | `适凼白彑戔劔＊` | 选择操作类型 | (操作选择) |
| UserService.handleAgentAction() | `白彑戔劔＊` | 操作类型 | (操作类型) |
| UserService.handleAgentAction() | `癹彗或劙揔社夺贮` | 请选择或输入内容 | (输入提示) |
| GitReviewService.getCommitMessage() | `暇日揞亪俭恣生成` | commit message 生成 | (Git 提交信息) |
| RestartableAgentProcessService.onRestartException() | `弘帢俭恣中穭` | 重启中 | (进程重启) |
| BatchUnitTestDialog.createActions() | `甀戏单元浞诀` | 批量单元测试 | config.batch.unit.test.title |
| BatchUnitTestDialog.createActions() | `參涝` | 取消 | action.close = 关闭 |
| UnitTestDialog.createActions() | `甉戆` | 生成 | (生成按钮) |
| UnitTestDialog.createActions() | `叀涞` | 取消 | action.close = 关闭 |
| DiffDialog.createActions() | `參涝` | 取消 | action.close = 关闭 |
| ChatService.oE() | `请幁找刦枰练竤皀抡锞既忷Ｌ幼揚供解冬斦桏々叿觶冹符丄丮抅锹` | 请选择要操作的文件或代码片段 | (操作选择提示) |
| UserService.send2WebShowOperateGuidance() | `~cg~ogg丅存在` | 操作指引已存在 | (操作指引) |
| BatchUnitTestDialog.rD() | `彤剺测试目录上孟圃旝臱劳刓廲...` | 测试目录上已存在... | config.batch.unit.test.create.error |
| InlineChatInputPanel.ja() | `產戽丳忝` | 内联聊天 | inline.chat.* |
| InlineChatBtnPanelRenderer$O.mouseClicked() | `釆纲` | 采纳 | inline.chat.accept.text |
| InlineChatBtnPanelRenderer$O.mouseClicked() | `拯绠` | 拒绝 | inline.chat.reject.text |
| InlineChatBtnPanelRenderer$O.mouseClicked() | `里诔` | 重试 | inline.chat.retry.text |
| InlineChatErrorPanelRenderer$n.mouseClicked() | `受涉` | 采纳 | inline.chat.accept.text |
| InlineChatErrorPanelRenderer$n.mouseClicked() | `金诉` | 重试 | inline.chat.retry.text |
| SendStopActionButtonPanel.<clinit>() | `偂歼` | 发送 | (发送按钮) |
| SendStopActionButtonPanel.<clinit>() | `叏速` | 停止 | (停止按钮) |
| AutoCodeGenerateListener.commandStarted() | `撥涉` | 采纳 | (采纳操作) |
| CommitMessageSuggestionAction.SD() | `断墙斀仯且甞我揨亜俀恎` | 当前分支无变更 | (Git 分支状态) |
| CommitMessageSuggestionAction.SD() | `秙劮斁仱上甅戊提交俽恳` | 暂存文件已提交 | (Git 提交状态) |
| CommitMessageSuggestionAction.yf() | `诙勯逘叅曩皃令砘吗莶受揂亶俢恬` | 生成 commit message | (提交信息生成) |
| PrepushReviewAction.yf() | `秼劯皞斝件不诘宽` | 评审文件不存在 | (代码评审) |
| PrepushReviewAction.yf() | `释呤吔皅斆令丟诇客` | 暂无可评审内容 | (代码评审) |
| PluginWebsocketClient.closeWebsocket() | `具闩运掫夠贴` | WebSocket 连接关闭 | (WebSocket) |
| PluginWebsocketClient.closeWebsocket() | `入闻迟掤夷责` | 连接已断开 | (网络连接) |
| BatchUTGeneratorAction.cE() | `札莰叏利顸盯権坟俁恏` | 批量生成单元测试 | config.batch.unit.test.title |
| BatchUTGeneratorAction.actionPerformed() | `札莰双刪项目俽恳` | 批量生成仅支持单项目 | config.batch.unit.test.message.module.error |
| BatchUTGeneratorAction.EF() | `卌浊斆付觍枳与` | 仅支持 Java | config.batch.unit.test.message.error |
| UserInfoAction.update() | `质叹％朵登录` | 请先登录 | aicode.not.signed |
| RefreshAction.<init>() | `剬旫` | 刷新 | (刷新操作) |
| UnitTestService.hc() | `甕戚甫侈凼锟` | 单元测试 | (单元测试) |
| UnitTestService.jB() | `名平协浑旺旬斮泂` | 生成单元测试 | config.unit.test.title |
| UnitTestService.aA() | `俻恣巾渒陳` | 测试用例 | (测试用例) |
| RequestTipServiceImpl.ib() | `既廽让` | 请求中 | aicode.requesting |
| RequestTipServiceImpl.Ac() | `墤开甁戎串100` | 请求超时 100 | aicode.complete.time.out |
| BatchUnitTestService.batchUnitTestMessage() | `擁佐戇劈` | 批量生成 | (批量操作) |
| BatchUnitTestService.batchUnitTestDownload() | `擁佐夦贲` | 批量下载 | (批量下载) |
| UnitTestCollectUtil.isTestOfMethod() | `莰发泮觥夯贻` | 测试方法 | (测试方法识别) |
| ChatOperationEnum.<clinit>() | `寏诀桛斪廠斘仩` | 新建对话 | (聊天操作) |
| ChatOperationEnum.<clinit>() | `寏诀桛仹砛毋辜` | 清除对话 | (聊天操作) |
| ChatOperationEnum.<clinit>() | `針纩廥讱` | 删除 | (聊天操作) |
| ChatOperationEnum.<clinit>() | `釚纮衖问泷釕` | 重新提问 | (聊天操作) |
| DuplicateRule.<clinit>() | `趨辜` | 覆盖 | config.batch.unit.test.duplicate.filename.overwrite |
| DuplicateRule.<clinit>() | `觝皍` | 跳过 | config.batch.unit.test.duplicate.filename.skip |
| DuplicateRule.<clinit>() | `係畆仗聞` | 保留二者 | config.batch.unit.test.duplicate.filename.coexist |
| CodeCollectEnum.<clinit>() | `晠胧衺具` | 代码解释 | (代码功能) |
| CodeCollectEnum.<clinit>() | `掉儾` | 纠错 | (代码功能) |
| CodeCollectEnum.<clinit>() | `夒利` | 优化 | (代码功能) |
| CodeCollectEnum.<clinit>() | `旫庡` | 注释 | (代码功能) |
| CodeCollectEnum.<clinit>() | `半浔` | 测试 | (代码功能) |
| CodeCollectEnum.<clinit>() | `毋辜醜绨` | 代码检查 | (代码功能) |
| CodeCollectEnum.<clinit>() | `兩仉` | 拆分 | (代码功能) |
| RepoStatusEnum.<clinit>() | `朵揓朘` | 已授权 | (知识库授权) |
| RepoStatusEnum.<clinit>() | `徚刂妐卍` | 未授权 | (知识库授权) |
| RepoStatusEnum.<clinit>() | `徚奟瑝` | 已失效 | (知识库授权) |
| RepoStatusEnum.<clinit>() | `工奪攓` | 初始化 | (知识库) |
| RepoStatusEnum.<clinit>() | `工掎杅兩仉狭恚` | 初始化中请等待 | (知识库) |
| TestGenerationProcess.<clinit>() | `甀戏匎洐` | 批量生成 | (测试生成) |
| TestGenerationProcess.<clinit>() | `弆姍缉诎亸硚` | 收集上下文 | (测试生成) |
| TestGenerationProcess.<clinit>() | `弇姓缎诗幰扸術匎洐` | 生成单元测试代码 | (测试生成) |
| UnitTestMockEnum.<clinit>() | `膱勳` | Mock | (测试 Mock) |
| UnitTestMockEnum.<clinit>() | `儨閶` | Spy | (测试 Mock) |
| PyUnitTestMockEnum.<clinit>() | `臵劷` | Mock | (测试 Mock) |
| PyUnitTestMockEnum.<clinit>() | `儨閶` | Spy | (测试 Mock) |
| PluginSceneEnum.<clinit>() | `n|{i牗朳` | iFlyCode | aicode.plugin.scene |
| RestartEnum.<clinit>() | `wzxtn吰劷` | 重启插件 | (插件重启) |
| RestartEnum.<clinit>() | `PQSsi拈织迁掺` | 重启 Agent | (Agent 重启) |
| RestartEnum.<clinit>() | `PQSsi弘帢公闲` | 重启进程 | (进程重启) |
| SpanAttrEnum.<clinit>() | `揔仩男扬呖` | 代码补全 | (APM 追踪) |
| SpanAttrEnum.<clinit>() | `揈们曫斯` | 代码解释 | (APM 追踪) |
| SpanAttrEnum.<clinit>() | `揈们牗朳` | 代码检查 | (APM 追踪) |
| TracerEnum.<clinit>() | `wzxtn弝帧` | 重启 | (APM 追踪) |
| TracerEnum.<clinit>() | `vQSsi吵劲弝帧` | Agent 重启 | (APM 追踪) |
| TracerEnum.<clinit>() | `弘帢讯彊` | 进程 | (APM 追踪) |
| OpenWindowAction.<init>() | `右赕寿诛` | 打开窗口 | (窗口操作) |
| OpenTelemetryService.handApmConfig() | `IﾽﾵD)曶斲开帺` | APM 开关 | (APM 配置) |
| ExcludeMethodConfigurable.<init>() | `斦泊呖禫` | 方法配置 | config.batch.unit.test.exclude.method.separator |
| BatchFunctionCommentAction.Xe() | `莱叐斀仱仹砛信息夭费` | 当前分支信息获取失败 | config.batch.unit.test.branch.commit |
| PrepushReviewAction.Td() | `仢砾颻诅宠` | 代码评审 | (代码评审) |
| WebViewWindowPanel.notSupportCefTip() | `诪挔煡不迚歏骯吤甸Zx~D细代／` | 当前浏览器不支持...请使用Chrome | (CEF 浏览器提示) |

### 4.2 交叉验证统计

| 指标 | 值 |
|------|-----|
| H() 中文解码条目总数 | 175 |
| 可部分辨识条目数 | 93 |
| 与 i18n 文档匹配条目数 | 22 |
| 中文恢复率（可辨识/总数） | 53.1% |

### 4.3 解码偏移分析

H() 解码的中文字符呈现系统性偏移模式：
- 每个 CJK 字符与预期字符的 Unicode 码点差值不固定，但同一方法内的偏移方向一致
- 短词（2-3 字）比长句更易推测原文，因上下文约束更强
- 枚举类（`*Enum.<clinit>()`）的中文解码最为密集，因其存储大量 UI 显示名称
- 服务类（`*Service`）的中文多为操作提示和错误消息

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
