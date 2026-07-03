## 1. 32 类完整清单与反编译结果

### 1.1 核心服务类 (10)

#### ChatService (源文件名: em)
```
public class com.aicode.agent.service.ChatService &#123;
  // 字段
  public static List&lt;String&gt; NEED_CODE_LIST;
  private static final Logger enum;  // 混淆: enum = logger
  public static ConcurrentNavigableMap<String, String> SESSION_ID;

  // 公开方法
  public static JsonObject getTalkList(JsonObject);
  public static void sendError2Web(JsonObject, Project, MessageDto);
  public static void handleParseWebUrlErr(JsonObject, Project, String);
  public static JsonObject getTalkPredictResult(JsonObject, MessageDto);
  public static JsonObject getAgentChatResponse(JsonObject, MessageDto);
  public static void handleAction(WebViewDataTypeEnum, JsonObject, String, Project);
  public static boolean isChat(CommandEnum, JsonObject, Project, MessageDto, ResponseDto);
  public static FirstChatMessage getRightChatMessage2Web(Project, String);
  public static void send2Agent(Project, FirstChatMessage);
  public static CodeInfoDto getSelectedCode(String);
  public static void handleChatStop(Project, JsonObject);
  public static void handleFeedbackCategory(JsonObject, Project);
  public static JsonObject getTalkHistory(Project, JsonObject);
  public static void handleCodeDebug(Project, String, String, boolean);
  public static void getTalkPredict(Project);
  public static void getRequestForTalkHistory(JsonObject, Project);
  public static void handleNewChat(Project);
  public static FirstChatMessage getFirstChatMessage2Web(Project, String);
  public static void handleCodeComment(Project, JsonObject, MessageDto);
  public static void handleChatDeleteMsg(String, Project);
  public static void handleCodeDebug(Project, String, Integer, String, String, boolean);
  public static boolean hasAnyDirectory(String);
  public static String getPath(Project);
  public static void deleteHistoryItem(JsonObject, Project);
  public static JsonObject getErrorChatResponse(FirstChatMessage$ValueDTO);
  public static boolean isCurrentBranchRemote(Project);
  public static JsonObject getKnowledgeChatResponse(JsonObject, MessageDto);
  public static FirstChatMessage getEditorChatMessage2Web(Project, String, CodeInfoDto);
  public static FirstChatMessage getFirstChatMessage(Project, String, CodeInfoDto, JsonArray);
  public static void handleCodeComment(Project, CommentInfo, MessageDto);
  public static void getHistoryList(Project);
  public static void handleChatMessage(Project, String);
  public static JsonObject getGamePlay(JsonObject);
  public static void refreshAgent(Project, boolean);
  public static void handleAgentAction(CommandEnum, JsonObject, String, MessageDto, Project);
  public static CodeInfoDto getCodeInfoDto(Editor, SelectionModel, int, int);
&#125;
```

**handleAction 分发表 (WebViewDataTypeEnum -> 方法)**:
| case | 枚举值 | 调用方法 |
|------|--------|----------|
| 1 | CHAT_CHOOSE_HISTORY_ITEM | getRequestForTalkHistory(json, project) |
| 2 | CHAT_GET_HISTORY_LIST | getHistoryList(project) |
| 3 | CHAT_DELETE_HISTORY_ITEM_ALL | deleteHistoryItem(json, project) |
| 4 | CHAT_DELETE_HISTORY_ITEM | deleteHistoryItem(json, project) |
| 5 | CHAT_SEND_MSG | handleChatMessage(project, string) |
| 6 | CHAT_REFRESH_MODEL | PluginWebsocketClient.sendWsMessage(USER_MODEL_LIST, project) |
| 7 | CHAT_GET_MODEL_LIST | PluginWebsocketClient.sendWsMessage(USER_MODEL_LIST, project) |
| 8 | CHAT_SET_MODEL | UserService.SetModel(json) |
| 9 | CHAT_DELETE_MSG | handleChatDeleteMsg(string, project) |
| 10 | CHAT_STOP_RESPONSE | handleChatStop(project, json) |
| 11 | CHAT_NEW_CHAT | handleNewChat(project) |
| 12 | CHAT_GET_IDE_FILE_STATE | Cd(project, json) -> 获取IDE文件状态 |
| 13 | CHAT_RECOMMEND_GAMEPLAY | Ad(project) -> 推荐玩法 |
| 14 | CHAT_GET_DOC_KNOWLEDGE_LIST | df(project) -> 获取文档知识列表 |
| 15 | CHAT_GET_CODE_KNOWLEDGE_LIST | VE(project) -> 获取代码知识列表 |
| 16 | CHAT_RESEND | dE(project, json) -> 重发消息 |
| 17 | CHAT_FEEDBACK_CATEGORY | PluginWebsocketClient.sendWsMessage(USER_FEEDBACK_CATEGORY, project) |
| 18 | CHAT_CHOOSE_FILE | Application.invokeLater + 选择文件逻辑 |
| 19 | COMMON_DOWNLOAD_TABLE | 下载表格到Markdown |
| 20 | CHAT_AGENT_REFRESH | refreshAgent(project, boolean) |
| 21 | CHAT_VALID_WEBSITE | 校验网站URL |
| 22 | GIT_CODE_KNOWLEDGE_REPO_STATUS | 代码知识库状态 |
| 23-26 | GIT_* | Git相关操作(授权/索引/Token/状态) |
| 27 | CHAT_GET_OPEN_DIR_LIST | 获取打开目录列表 |

**handleAgentAction 分发表 (CommandEnum -> 方法)**:
| case | 枚举值 | 调用方法 |
|------|--------|----------|
| 1-7 | TALK_ASK/CODE_EXPLAIN/... | getKnowledgeChatResponse(json, msgDto) |
| 8 | TALK_KNOWLEDGE | getAgentChatResponse(json, msgDto) |
| 9 | TALK_RESEND | getKnowledgeChatResponse(json, msgDto) |
| 10 | TALK_INTELLIGENT | getKnowledgeChatResponse(json, msgDto) |
| 11 | GIT_DIFF | getAgentChatResponse(json, msgDto) |
| 12 | GIT_REVIEW | getAgentChatResponse(json, msgDto) |
| 13 | CODE_TEST | getKnowledgeChatResponse(json, msgDto) |
| 14 | SQL_SOURCE_EDIT | getAgentChatResponse(json, msgDto) |
| 15 | ERROR | getAgentChatResponse(json, msgDto) |
| 16 | GIT_COMMIT_MESSAGE | AGENT_REQUEST.remove + getTalkHistory + send2Web |
| 17 | USER_FEEDBACK_CATEGORY | AGENT_REQUEST.remove + getTalkList + send2Web |
| 18 | USER_KNOWLEDGE_LIST | AGENT_REQUEST.remove + getGamePlay + send2Web |
| 19 | TALK_HISTORY | AGENT_REQUEST.remove + getTalkPredictResult + send2Web |
| 20 | TALK_LIST | AGENT_REQUEST.remove + getTalkList + send2Web |
| 21 | TALK_RECOMMEND_GAMEPLAY | AGENT_REQUEST.remove + getGamePlay + send2Web |
| 22 | TALK_PREDICT | AGENT_REQUEST.remove + getTalkPredictResult + send2Web |
| 23 | CODE_COMMENT_RANGE | Cd(project, json) |
| 24 | ACTION_OPEN_DOCUMENT | 打开文档 |
| 25 | TALK_DOWNLOAD_MARKDOWN_TABLE | 下载Markdown表格 |
| 26 | FEEDBACK_CATEGORY_INFO | 反馈分类信息 |
| 27 | USER_PARSE_WEB_URL | 解析Web URL |

#### CodeCheckService (源文件名: ej)
```
public class com.aicode.agent.service.CodeCheckService &#123;
  public static final boolean enum;  // assertions flag

  public static void handleAction(WebViewWindowPanel, WebViewDataTypeEnum, JsonObject, Project);
  public static void sendCodeCheck(Project);
  public static JsonObject fixCodeCheck(JsonObject, Project);
&#125;
```

**handleAction 分发表**:
| case | 枚举值 | 调用方法 |
|------|--------|----------|
| 1 | CODE_CHECK_REQUEST_CODE_CHECK_LIST | sendCodeCheck(project) |
| 2 | CODE_CHECK_FIX | fixCodeCheck(json, project) -> 返回结果到WebView |

**sendCodeCheck**: 构造 MessageDto(UUID, CommandEnum.CODE_CHECK.getType())，设置 path 为项目路径，通过 PluginWebsocketClient.sendWsMessage 发送到 Agent。

**fixCodeCheck**: 从 JsonObject 提取修复数据，返回修复后的代码片段 JsonObject。

#### CodeCompleteService (源文件名: go)
```
public class com.aicode.agent.service.CodeCompleteService &#123;
  // 包含 H(Object) 混淆字符串解密方法（与 GitReviewService.H 相同算法）

  public static void handleAgentAction(CommandEnum, JsonObject, MessageDto, String, Project);
&#125;
```

**handleAgentAction 分发表**:
| case | 枚举值 | 调用方法 |
|------|--------|----------|
| 1 | CODE_COMPLETE | 处理代码补全结果：提取 %w3q 字段(PropertyUtils.H解密)，判断 stream/非stream，调用 RequestTipService.dealAgentTips 或 dealStreamAgentTips，结束时 AGENT_REQUEST.remove |
| 2 | USER_CAN_CODE_ENHANCE | 更新 enableCodeEnhance 设置，构造 JsonObject 发送 SETTING_GET_CAN_OPEN_CODE_ENHANCE 到 WebView |
| 3 | ACTION_SYNC_DOCUMENT_LIST | 同步文档列表（未实现具体逻辑，直接 return） |

关键：`%w3q` 经 PropertyUtils.H 解密后为 JSON 数据字段名。stream 模式下解析为 ResponseStreamDto，非 stream 模式直接传递 JsonObject。

#### CodeSearchService (源文件名: rm)
```
public class com.aicode.agent.service.CodeSearchService &#123;
  private static final Logger enum;

  public static JsonObject getCodeSearchRepos(String, JsonObject);
  public static void handleAction(WebViewDataTypeEnum, JsonObject, Project);
  public static void handleAgentAction(CommandEnum, String, JsonObject, Project);
  public static JsonObject getSearchResult(JsonObject);
  public static JsonObject getLangList(JsonObject);
  public static JsonObject getReposList(JsonObject);
&#125;
```

**handleAction 分发表**:
| case | 枚举值 | 调用方法 |
|------|--------|----------|
| 1 | CODE_SEARCH_REQUEST_CODESEARCH_CODE_LIST | 发送 GIT_SEARCH 命令到 Agent |
| 2 | CODE_SEARCH_REQUEST_CODESEARCH_REPOSITORY_LIST | 发送 GIT_USER_REPOS 命令到 Agent |
| 3 | CODE_SEARCH_REQUEST_CODESEARCH_LANGUAGE_LIST | 发送 GIT_LANG_LIST 命令到 Agent |
| 4 | CODE_SEARCH_REQUEST_COPY_CODE | 复制代码到剪贴板 |
| 5 | CODE_SEARCH_REQUEST_INSERT_CODE | 插入代码到编辑器 |
| 6 | CODE_SEARCH_REQUEST_CODE_FILE | 打开代码文件 |
| 7 | CODE_SEARCH_REQUEST_OPEN_URL | 在浏览器中打开URL |

**handleAgentAction 分发表**:
| case | 枚举值 | 调用方法 |
|------|--------|----------|
| 1 | GIT_LANG_LIST | getLangList(json) + send2Web |
| 2 | GIT_USER_REPOS | getReposList(json) + send2Web |
| 3 | GIT_SEARCH | getSearchResult(json) + send2Web |

#### CommonService (源文件名: fj)
```
public class com.aicode.agent.service.CommonService &#123;
  private static final Logger byte;
  public static final boolean enum;

  // 核心方法
  public static void logOperate(String, String, Project);
  public static void refreshDocumentStruct(Project);
  public static void handleChatFocusFileLine(Project, JsonObject);
  public static void copyCode(Project, String);
  public static void handleAction(WebViewDataTypeEnum, JsonObject, String, Project);
  public static boolean isSupportJava(Editor);
  public static void updateConfig(JsonObject, Project);
  public static void openFileDialog(Project, JsonObject);
  public static synchronized void refreshFunctionAction(Project, MessageDto, JsonObject);
  public static void insertLineComment(Project, String, String, List&lt;RangeDTO&gt;);
  public static void insertCode(Project, String);
  public static void openPage(Project, PageEnum);
  public static void messageBus(Project, String, MessageType);
  public static void handleChatFocusFile(Project, JsonObject);
  public static void diffCode(Project, RequestCaseCodeDto$ValueDTO);
  public static void jumpToFileByIndex(Project, String, Integer, Integer, boolean);
  public static void openFile(Project, String);
  public static void chatMessage2Web(Project, FirstChatMessage, Boolean);
  public static void handleChatFeedback(String, Project);
  public static int[] getOffsets(Document, int, int, int, int);
  public static void popupKeymapSettings(Project);
  public static void handleEval(JsonObject, Project);
  public static void genCodeFile(Project, String, String);
  public static void saveShowOperateGuidance(Project);
  public static JsonObject getConfig();
  public static void openUrl(String, Project);
  public static void clearHighLight(MarkupModel, RangeHighlighter[]);
  public static void handleComment(Project, ValueDTO, ChatOperationEnum, String, String, RequestCaseCodeDto);
  public static void getPluginInfo(Project);
  public static void handleClick(Project, String);
&#125;
```

**handleAction 分发表**:
| case | 枚举值 | 调用方法 |
|------|--------|----------|
| 1 | COMMON_PAGE_READY | logger.info("page ready") |
| 2 | COMMON_OPEN_URL | openUrl(string, project) |
| 3 | COMMON_CODE_CLICK_ACTION | handleClick(project, string) |
| 4 | COMMON_FOCUS_FILE | handleChatFocusFile(project, json) |
| 5 | COMMON_FOCUS_FILE_LINE | handleChatFocusFileLine(project, json) |
| 6 | COMMON_FEEDBACK | handleChatFeedback(string, project) |
| 7 | COMMON_EVALUATION | handleEval(json, project) |
| 8 | SETTING_GET_CONFIG | getConfig() |
| 9 | SETTING_UPDATE_CONFIG | updateConfig(json, project) |
| 10 | SETTING_GET_CAN_OPEN_CODE_ENHANCE | PluginWebsocketClient.sendWsMessage(USER_CAN_CODE_ENHANCE, project) |
| 11 | COMMON_OPEN_FILE_DIALOG | openFileDialog(project, json) |
| 12 | SETTING_POPUP_KEYMAP_SETTINGS | popupKeymapSettings(project) |
| 13 | SAVE_SHOW_OPERATE_GUIDANCE | saveShowOperateGuidance(project) |

**updateConfig**: 从 JsonObject 解析配置字段，更新 AICodeSettingsState 的所有属性：
- autoTriggerOnPause, autoTriggerTimeDelay, generateCodeMode (TipTypeEnum)
- codeCompleteDisableLang, sendMessageType, lineToolsType
- javaTestFramework, javaMockFramework, defaultLanguage
- openCodeEnhance, openAutoUpdate (仅SaaS场景)
- lineToolsPermission 系列: DocComments, LineComments, Comments, FunctionSplit, CodeOptimization, UnitTesting
- inlineCompletionInputStyle, openFunctionSplit, openCodeOptimization
- openIFlyTest, openInlineChat, openIFlyDBA, openIFlyOps, openIFlyPm

#### GitReviewService (源文件名: sk)
```
public class com.aicode.agent.service.GitReviewService &#123;
  // 包含 H(Object) 混淆字符串解密方法
  // 包含 removeMarkdownCodeBlocks(String) 方法

  public static void sendGitDiffRequest(String, Project);
  public static void getCommitMessage(Project, String, JsonObject);
  public static void handleAgentAction(CommandEnum, JsonObject, Project);
  public static JsonObject getGiffReview(String, JsonObject);
  public static JsonObject getGiffDiff(JsonObject);
  public static void handleAction(WebViewDataTypeEnum, JsonObject, Project);
  public static void sendCodeReviewRequest(JsonObject, Project);
&#125;
```

**handleAction 分发表**:
| case | 枚举值 | 调用方法 |
|------|--------|----------|
| 1 | CODE_REVIEW_PAGE_READY | 检查 PrepushReviewAction.path 非空则 sendGitDiffRequest |
| 2 | CODE_REVIEW_GET_CHANGE_RESULT | sendCodeReviewRequest(json, project) |
| 3 | CODE_REVIEW_GET_CODEREVIEW_LIST | 从json提取path，sendGitDiffRequest(path, project) |
| 4 | CODE_REVIEW_GET_CHANGE_RESULT_END | PREPUSH_REVIEW_BUTTON.set(false) |

**handleAgentAction 分发表**:
| case | 枚举值 | 调用方法 |
|------|--------|----------|
| 1 | GIT_DIFF | AGENT_REQUEST.remove + getGiffDiff(json) + send2Web |
| 2 | GIT_REVIEW | getGiffReview(sessionId, json) + send2Web |
| 3 | GIT_COMMIT_MESSAGE | getCommitMessage(project, sessionId, json) |

**sendGitDiffRequest**: 设置 PREPUSH_REVIEW_BUTTON=true，构造 MessageDto(UUID, GIT_DIFF)，设置 path，通过 PluginWebsocketClient.sendWsMessage 发送。

**sendCodeReviewRequest**: 从json提取 reviewData(含diffVersion) 和 reviewVersion，构造 MessageDto(UUID, GIT_REVIEW)，设置 path 和 data，发送到 Agent。

**getCommitMessage**: 从json提取commit消息文本，检查是否为空（空则显示提示"请输入提交信息"），非空则通过 Application.invokeLater 设置到 EditorTextField。

**removeMarkdownCodeBlocks**: 移除 Markdown 代码块标记（```），用于清理 Agent 返回的代码。

#### InitService (源文件名: uf)
```
public final class com.aicode.agent.service.InitService &#123;
  private static final Logger byte;
  private final ScheduledExecutorService enum;  // 混淆: enum = scheduler

  public void initProject(Project);
  private void md(Project);  // 启动定时任务
  private static void xC();  // 通知 Ready 状态
  private static void ld(Project, String);  // 检查过期请求
&#125;
```

**initProject**: 校验 project 非空，调用 md()。

**md**: 使用 BasicActionsBundle.message 获取提示文本，创建定时任务 scheduleAtFixedRate(delay=0, period=500ms)，通过 ScheduledExecutorService 执行。

**ld**: 遍历 RequestTipServiceImpl.LAST_REQUEST 映射，检查每个请求的时间戳，如果当前时间 - 请求时间 > 超时阈值，则记录日志、清除过期请求映射、通过 Application.invokeLater 通知。

#### InlineChatCommandService (源文件名: ll)
```
public class com.aicode.agent.service.InlineChatCommandService &#123;
  public static final Key<List<CodeInfoDto$RangeDTO>> RANGE_KEY;
  public static final Key&lt;Integer&gt; VERSION_KEY;
  public static final Key<List<CodeInfoDto$RangeDTO>> BODY_RANGE_KEY;

  public static void handleAgentAction(String, CommandEnum, Project, MessageDto, JsonObject);
  public static void handleAgentAction(Project, MessageDto, String, CommandEnum);
  public static FirstChatMessage handleChatScene(MessageDto);
&#125;
```

**handleAgentAction (5参数版)**: 根据 CommandEnum 分发：
- INLINECHAT_GET_FUNC_RANGE: 处理行内聊天功能范围获取
- INLINECHAT_CATEGORY: 处理行内聊天分类
- INLINECHAT_DIRECT: 处理行内聊天直接指令

**handleAgentAction (4参数版)**: 根据 InlineChatCategoryEnum 分发：
- DOC/LINEDOC: 文档注释生成
- EDIT: 代码编辑
- GENERATE: 代码生成
- UNKNOW: 未知类型

关键逻辑：OD() 方法检查 SessionController 的编辑器状态，根据是否有选区决定使用 DOC 还是 LINEDOC 模式。行内聊天通过 Editor.getUserData(RANGE_KEY) 获取范围信息。

#### SqlService (源文件名: ml)
```
public class com.aicode.agent.service.SqlService &#123;
  public static ConcurrentNavigableMap<String, String> SQL_SESSION_ID;
  private static final Logger enum;

  private static ConnectConfigDto sf(JsonObject);
  public static void handleSqlTest(JsonObject, Project);
  private static FirstChatMessage kF(Project, JsonObject);
  public static JsonObject saveSource(JsonObject);
  public static JsonObject getTableList(JsonObject);
  public static JsonObject testConnect(JsonObject);
  public static JsonObject getSourceType(JsonObject);
  public static void handleSqlSave(JsonObject, Project);
  public static void handleSqlChatMessage(JsonObject, Project);
  public static void handleSqlDelete(JsonObject, Project);
  public static void handleAction(WebViewDataTypeEnum, JsonObject, Project);
  public static void handleAgentAction(CommandEnum, String, JsonObject, Project);
  public static JsonObject getSqlChat(Project, String, JsonObject, String);
  public static void handleSqlTableList(JsonObject, Project);
&#125;
```

**handleAction 分发表**:
| case | 枚举值 | 调用方法 |
|------|--------|----------|
| 1 | SQL_CHAT_GET_MODEL_LIST | PluginWebsocketClient.sendWsMessage(USER_MODEL_LIST, project) |
| 2 | SQL_CHAT_REQUEST_SOURCE_TYPES | PluginWebsocketClient.sendWsMessage(SQL_SOURCE_TYPES, project) |
| 3 | SQL_CHAT_SQL_LINK_TEST | handleSqlTest(json, project) |
| 4 | SQL_CHAT_SOURCE_LIST | PluginWebsocketClient.sendWsMessage(SQL_SOURCE_LIST, project) |
| 5 | SQL_CHAT_SQL_SAVE | handleSqlSave(json, project) |
| 6 | SQL_CHAT_SOURCE_DELETE | handleSqlDelete(json, project) |
| 7 | SQL_CHAT_TABLE_LIST | handleSqlTableList(json, project) |
| 8 | SQL_CHAT_SEND_MSG | handleSqlChatMessage(json, project) |
| 9 | SQL_CHAT_NEW_CHAT | SQL_SESSION_ID.remove + 新会话 |
| 10 | SQL_CHAT_STOP_RESPONSE | handleSqlChatStop(project, json) |

**handleAgentAction 分发表**:
| case | 枚举值 | 调用方法 |
|------|--------|----------|
| 1 | SQL_SOURCE_LIST | getSourceList(json) + send2Web |
| 2 | SQL_SOURCE_TYPES | getSourceType(json) + send2Web |
| 3 | SQL_TEST_CONNECT | testConnect(json) + send2Web |
| 4 | SQL_SOURCE_EDIT | saveSource(json) + send2Web |
| 5 | SQL_SOURCE_DELETE | 删除数据源 + send2Web |
| 6 | SQL_TABLE_LIST | getTableList(json) + send2Web |
| 7 | SQL_GENERATE | getSqlChat + send2Web |
| 8 | SQL_OPTIMIZE | getSqlChat + send2Web |

**sf()**: 从 JsonObject 解析 ConnectConfigDto，提取 host、port、username、password、database、type 字段（均通过 H() 解密）。

**handleSqlTest**: 构造 MessageDto(UUID, SQL_TEST_CONNECT)，设置 ConnectConfigDto 为 data，发送到 Agent。

**handleSqlChatMessage**: 构造 MessageDto(UUID, SQL_GENERATE 或 SQL_OPTIMIZE)，设置 sql 和 sessionId，发送到 Agent。

#### UserService (源文件名: zf)
```
public class com.aicode.agent.service.UserService &#123;
  private static final Logger byte;
  private static String enum;  // loginUrl
  public static boolean goTo;

  public static void showMessage(Project);
  public static void repaintModelComboBox(ComboBox);
  public static void SetModel(JsonObject);
  public static void setGoTo(boolean);
  public static boolean isGoTo();
  public static void setItem(ComboBox, List&lt;CodeModel&gt;);
  public static String getLoginUrl();
  public static void send2WebShowOperateGuidance(JsonObject, Project);
  public static void logout(Project);
  public static JsonArray sortJsonArray(JsonArray, List&lt;String&gt;);
  public static void handleAgentAction(CommandEnum, JsonObject, String, Object, Project);
  public static void setLoginUrl(String);
  public static JsonObject getLoginInfo(JsonObject, Project);
  public static JsonObject getLoginUrl(String);
  public static void getUserPermissions(JsonObject, Project);
  public static void handleAction(WebViewDataTypeEnum, Project);
  public static void clearIcon(Project);
  public static void sendWriterConfig(Project, JsonObject);
  public static JsonObject getUserModelList(JsonObject, MessageDto);
&#125;
```

**handleAction 分发表**:
| case | 枚举值 | 调用方法 |
|------|--------|----------|
| 1 | LOGIN_INIT | 初始化登录流程 |
| 2 | LOGIN_LOGOUT | logout(project) |
| 3 | LOGIN_LOGIN_ABORT | 中止登录 |
| 4 | LOGIN_LOGIN_CHECK | 检查登录状态 |
| 5 | LOGIN_CLOSE_QR_CODE | 关闭二维码 |

**handleAgentAction 分发表**:
| case | 枚举值 | 调用方法 |
|------|--------|----------|
| 1 | USER_LOGIN | 处理登录响应，更新UI |
| 2 | USER_LOGOUT | 处理登出响应 |
| 3 | USER_VERSION | 版本检查 |
| 4 | USER_MODEL_LIST | getUserModelList(json, msgDto) |
| 5 | MODEL_LIST_TIMER | 定时刷新模型列表 |
| 6 | LOGIN_INFO | getLoginInfo(json, project) |
| 7 | USER_PERMISSION | getUserPermissions(json, project) |

**showMessage**: 构造通知消息，包含"去登录"按钮（UserService$da），点击后打开浏览器到登录URL并显示 WebView 面板。

**logout**: 构造 MessageDto(UUID, USER_LOGOUT)，发送到 Agent，清除本地状态。

**getUserModelList**: 从 JsonObject 解析 FunctionModelInfo 列表（通过 UserService$ea TypeToken），排序后更新 ComboBox。

---

### 1.2 进程管理类 (5)

#### PluginAgentProcessService (源文件名: qa) - 接口
```
public interface com.aicode.agent.service.PluginAgentProcessService &#123;
  public abstract boolean isRunning();
  public static PluginAgentProcessService getInstance();
&#125;
```
IntelliJ Application Service 接口，通过 ApplicationManager.getApplication().getService() 获取单例。

#### PluginAgentProcessServiceEx (源文件名: ka) - 扩展接口
```
public interface com.aicode.agent.service.PluginAgentProcessServiceEx
    extends PluginAgentProcessService &#123;
  public abstract void startNotify();
  public abstract Pair getAgentPort(Long, int) throws InterruptedException, IOException;
  public abstract boolean isShutdown();
  public abstract void shutdown();
  public abstract void copySource() throws IOException;
&#125;
```
扩展接口，增加进程生命周期管理方法。

#### PluginAgentProcessServiceImpl (源文件名: fg) - 实现
```
public class com.aicode.agent.service.PluginAgentProcessServiceImpl
    implements PluginAgentProcessServiceEx &#123;
  private String final;  // 混淆: final = port
  private final PluginAgentProcessHandler try;  // 混淆: try = processHandler
  private final AtomicBoolean float;  // 混淆: float = shutdown flag
  private static final Logger byte;
  private final ExecutorService enum;  // 混淆: enum = executor

  // 构造器: 解压Agent -> 复制源码 -> 启动Agent进程
  public PluginAgentProcessServiceImpl() throws IOException, ExecutionException;

  public void shutdown();       // 优雅关闭: destroyProcess + shutdown executor
  public String getPort();
  public boolean isRunning();   // !isProcessTerminated && !isProcessTerminating
  public void startNotify();
  public void copySource();     // 复制WASM文件到Agent目录
  public Pair getAgentPort(Long, int);
  public void unZipAgent();     // 解压Agent zip包
  public PluginAgentProcessHandler launchAgent();
  public void setPort(String);
  public Long getAgentPid();
&#125;
```

**构造器流程**:
1. 初始化 AtomicBoolean(false) 作为 shutdown 标志
2. 解密字符串获取 executor 名称，创建 SingleThreadExecutor
3. 初始化 port 为 null
4. 调用 unZipAgent() 解压 Agent 二进制包
5. 调用 copySource() 复制 WASM 文件
6. 调用 launchAgent() 启动 Agent 进程，获取 PluginAgentProcessHandler

**unZipAgent**: 检查本地路径(localPath)，若不存在则使用 AICodeUtils.getAgentDirectoryPath()。检查 agent 目录下是否已存在（通过 H() 解密文件名），存在则跳过。否则从插件路径获取 zip 文件(FileUtils.getFileOfPluginPath)，复制到 agent 目录，解压后删除 zip。

**copySource**: 将 WASM 文件从插件资源复制到 Agent 的 wasms 目录。

**launchAgent**: 调用 PluginAgentCommandLine.createAgentCommandLine() 创建命令行，构造 PluginAgentProcessHandler。

**shutdown**: compareAndSet(false, true)，若成功则：
- 若 Application 未 disposed 且 processHandler 非空，destroyProcess
- shutdown executor，awaitTermination(1s)
- 若进程未终止且可 kill，killProcess
- 异常处理中多次尝试 kill

#### RestartableAgentProcessService (源文件名: xj)
```
public class com.aicode.agent.service.RestartableAgentProcessService
    implements PluginAgentProcessService, Disposable &#123;
  private final Object float;  // 混淆: float = lock
  public static final AtomicBoolean pushAgentRefresh;
  public final AtomicInteger connectAttempts;
  public static final AtomicInteger refreshTimes;
  private PluginAgentProcessServiceImpl byte;  // 混淆: byte = delegate
  public static final AtomicInteger restartAttempts;
  private static final Logger enum;
  public static final int RESTART_TIME = 10;

  public RestartableAgentProcessService();
  public boolean isRunning();
  public void restart();       // 重新创建 PluginAgentProcessServiceImpl
  public void dispose();       // 清理资源
  public void checkAndRestart();  // 检查并重启
&#125;
```

**restart**: synchronized(float) 块中：
1. 若 delegate 非空，调用 shutdown
2. 创建新的 PluginAgentProcessServiceImpl
3. 重置 connectAttempts 和 restartAttempts

**checkAndRestart**: 检查连接尝试次数，若超过阈值则自动重启 Agent 进程。

**dispose**: 关闭 delegate，清理资源。

#### RecentFilesManager (源文件名: yj)
```
public class com.aicode.agent.service.RecentFilesManager &#123;
  public static final Map<Project, Deque&lt;String&gt;> recentFilesMap;
  private static final int enum = 20;  // 最大保留文件数

  public static void fileOpened(Project, String);
  public static JsonArray getRecentFileDirs(Project);
  public static Deque&lt;String&gt; getRecentFiles(Project);
&#125;
```

**fileOpened**: 将文件路径添加到项目对应的 Deque 头部，移除重复项，超过 20 个则移除尾部。

**getRecentFileDirs**: 从最近文件列表中提取不重复的父目录路径，最多返回 5 个目录。

---

### 1.3 内部类 (17)

#### ChatService$Ia - SwitchMap (WebViewDataTypeEnum + CommandEnum)
```
public class ChatService$Ia &#123;
  public static final int[] byte;  // WebViewDataTypeEnum.ordinal -> switch case
  public static final int[] enum;  // CommandEnum.ordinal -> switch case
&#125;
```
WebViewDataTypeEnum 映射: 27 个枚举值 (CHAT_CHOOSE_HISTORY_ITEM=1 到 CHAT_GET_OPEN_DIR_LIST=27)
CommandEnum 映射: 27 个枚举值 (TALK_ASK=1 到 USER_PARSE_WEB_URL=27)

#### CodeCheckService$Da - SwitchMap (CommandEnum + WebViewDataTypeEnum)
```
public class CodeCheckService$Da &#123;
  public static final int[] byte;  // WebViewDataTypeEnum.ordinal
  public static final int[] enum;  // CommandEnum.ordinal
&#125;
```
CommandEnum: CODE_CHECK=1, CODE_DEBUG_DUPLICATE=2
WebViewDataTypeEnum: CODE_CHECK_REQUEST_CODE_CHECK_LIST=1, CODE_CHECK_FIX=2

#### CodeCompleteService$ja - SwitchMap (CommandEnum)
```
public class CodeCompleteService$ja &#123;
  public static final int[] enum;  // CommandEnum.ordinal
&#125;
```
CommandEnum: CODE_COMPLETE=1, USER_CAN_CODE_ENHANCE=2, ACTION_SYNC_DOCUMENT_LIST=3

#### CodeSearchService$Aa - SwitchMap (CommandEnum + WebViewDataTypeEnum)
```
public class CodeSearchService$Aa &#123;
  public static final int[] byte;  // WebViewDataTypeEnum.ordinal
  public static final int[] enum;  // CommandEnum.ordinal
&#125;
```
CommandEnum: GIT_LANG_LIST=1, GIT_USER_REPOS=2, GIT_SEARCH=3
WebViewDataTypeEnum: 7个值 (CODE_SEARCH_REQUEST_CODESEARCH_CODE_LIST=1 到 CODE_SEARCH_REQUEST_OPEN_URL=7)

#### CodeSearchService$ga - TypeToken (List&lt;CodeRepoInfoDto&gt;)
```
public class CodeSearchService$ga extends TypeToken<List&lt;CodeRepoInfoDto&gt;> &#123;&#125;
```
用于 Gson 反序列化代码仓库信息列表。

#### CodeSearchService$ia - TypeToken (List&lt;CodeInfoDto&gt;)
```
public class CodeSearchService$ia extends TypeToken<List&lt;CodeInfoDto&gt;> &#123;&#125;
```
用于 Gson 反序列化代码信息列表。

#### CommonService$Fa - MouseMotionAdapter
```
public class CommonService$Fa extends MouseMotionAdapter &#123;
  public final EditorGutterComponentEx float;  // gutter
  public final Editor byte;                     // editor
  public final int enum;                        // line number

  public void mouseMoved(MouseEvent);
&#125;
```
鼠标移动监听器：当鼠标在 Gutter 上移动到有效行时，将光标变为手型指针。用于行内操作按钮的悬停效果。

#### CommonService$Ha - SwitchMap (WebViewDataTypeEnum + ChatOperationEnum)
```
public class CommonService$Ha &#123;
  public static final int[] byte;  // ChatOperationEnum.ordinal
  public static final int[] enum;  // WebViewDataTypeEnum.ordinal
&#125;
```
WebViewDataTypeEnum: 13个值 (COMMON_PAGE_READY=1 到 SAVE_SHOW_OPERATE_GUIDANCE=13)
ChatOperationEnum: 6个值 (ACTION_COPY=1 到 ACTION_ACCEPT_INLINE_COMMENT=6)

#### CommonService$Ma - TypeToken (List<CodeInfoDto$RangeDTO>)
```
public class CommonService$Ma extends TypeToken<List<CodeInfoDto$RangeDTO>> &#123;&#125;
```
用于 Gson 反序列化代码范围信息。

#### GitReviewService$Ca - SwitchMap (CommandEnum + WebViewDataTypeEnum)
```
public class GitReviewService$Ca &#123;
  public static final int[] byte;  // WebViewDataTypeEnum.ordinal
  public static final int[] enum;  // CommandEnum.ordinal
&#125;
```
CommandEnum: GIT_DIFF=1, GIT_REVIEW=2, GIT_COMMIT_MESSAGE=3
WebViewDataTypeEnum: 4个值 (CODE_REVIEW_PAGE_READY=1 到 CODE_REVIEW_GET_CHANGE_RESULT_END=4)

#### InlineChatCommandService$fa - TypeToken (List<CodeInfoDto$RangeDTO>)
```
public class InlineChatCommandService$fa extends TypeToken<List<CodeInfoDto$RangeDTO>> &#123;&#125;
```
用于 Gson 反序列化行内聊天的代码范围信息。

#### InlineChatCommandService$ka - SwitchMap (CommandEnum + InlineChatCategoryEnum)
```
public class InlineChatCommandService$ka &#123;
  public static final int[] byte;  // CommandEnum.ordinal
  public static final int[] enum;  // InlineChatCategoryEnum.ordinal
&#125;
```
CommandEnum: INLINECHAT_GET_FUNC_RANGE=1, INLINECHAT_CATEGORY=2, INLINECHAT_DIRECT=3
InlineChatCategoryEnum: DOC=1, LINEDOC=2, EDIT=3, GENERATE=4, UNKNOW=5

#### SqlService$Ba - SwitchMap (CommandEnum + WebViewDataTypeEnum)
```
public class SqlService$Ba &#123;
  public static final int[] byte;  // CommandEnum.ordinal
  public static final int[] enum;  // WebViewDataTypeEnum.ordinal
&#125;
```
CommandEnum: 8个值 (SQL_SOURCE_LIST=1 到 SQL_OPTIMIZE=8)
WebViewDataTypeEnum: 10个值 (SQL_CHAT_GET_MODEL_LIST=1 到 SQL_CHAT_STOP_RESPONSE=10)

#### UserService$Ja - SwitchMap (CommandEnum + WebViewDataTypeEnum)
```
public class UserService$Ja &#123;
  public static final int[] byte;  // WebViewDataTypeEnum.ordinal
  public static final int[] enum;  // CommandEnum.ordinal
&#125;
```
CommandEnum: 7个值 (USER_LOGIN=1 到 USER_PERMISSION=7)
WebViewDataTypeEnum: 5个值 (LOGIN_INIT=1 到 LOGIN_CLOSE_QR_CODE=5)

#### UserService$da - NotificationAction (去登录按钮)
```
public class UserService$da extends NotificationAction &#123;
  public final Project enum;  // 混淆: enum = project

  public void actionPerformed(AnActionEvent, Notification);
&#125;
```
点击"去登录"按钮时：
1. 检查 apiKey 是否已设置（已登录则直接返回）
2. 设置 goTo=true
3. 获取 ToolWindow 并显示
4. 构造 LOGIN_GO_LOGIN 消息发送到 WebView
5. 在后台线程执行：获取 WebView 面板 -> 发送登录URL到浏览器

#### UserService$ea - TypeToken (List&lt;FunctionModelInfo&gt;)
```
public class UserService$ea extends TypeToken<List&lt;FunctionModelInfo&gt;> &#123;&#125;
```
用于 Gson 反序列化模型列表。

#### UserService$la - TypeToken (List&lt;String&gt;)
```
public class UserService$la extends TypeToken<List&lt;String&gt;> &#123;&#125;
```
用于 Gson 反序列化字符串列表（模型名称等）。

---
