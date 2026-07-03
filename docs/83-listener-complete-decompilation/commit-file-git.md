### 2.13 CommitHandlerFactory$o (提交处理实现 -- 37KB)

```
public class com.aicode.listener.CommitHandlerFactory$o
  extends com.intellij.openapi.vcs.checkin.CheckinHandler

  // --- 字段 ---
  private final Project final                    // 关联项目
  private static final Logger try                // SLF4J 日志
  private static final Map<Project, String> float // 项目->提交ID映射
  private final CheckinProjectPanel byte         // 提交面板
  private final Map<VirtualFile, List&lt;Change&gt;> enum // VCS根->变更列表映射

  // --- 关键方法 ---

  // 提交前处理
  public ReturnResult beforeCheckin():
    1. 如果 apiKey 非空:
       a. panel.getSelectedChanges() 获取选中变更
       b. Application.executeOnPooledThread 异步执行变更分析
    2. 返回 COMMIT (允许提交)

  // 提交成功处理
  public void checkinSuccessful():
    1. 如果 apiKey 为空 -> 返回
    2. 记录开始时间
    3. 遍历 enum Map(VCS根->变更列表):
       a. 对每个变更列表:
          - 创建 3 个 ArrayList: 方法列表、新增行数列表、总行数列表
          - 遍历每个 Change:
            * Fc(change, addedLinesList, totalLinesList) -- 分析变更方法
            * 合并到 methodList
          - 如果 methodList 非空:
            * GC(methodList, addedLinesList, totalLinesList, vcsRoot) -- 发送收集数据
    4. 记录结束时间和耗时

  // 分析变更方法
  private List&lt;UnitTestMethodDto&gt; Fc(Change, List&lt;Integer&gt;, List&lt;Integer&gt;):
    1. change.getVirtualFile() 获取文件
    2. 如果文件为 null -> 返回空列表
    3. 检查文件类型是否为 Java (equalsIgnoreCase)
    4. 检查文件状态(ADDED 或 MODIFIED)
    5. UnitTestCollectUtil.diffContent(changes, project) 获取 diff 内容
    6. 如果 diff 为空 -> 返回空列表
    7. Application.runReadAction 获取 Document
    8. 如果 Document 为 null -> 返回空列表
    9. 获取文档行数
    10. UnitTestCollectUtil.getChangeByDiff(diff, lineCount) 获取变更行
    11. 如果变更行为空 -> 返回空列表
    12. 记录行数到列表
    13. 如果是新增文件: 记录总行数
    14. 如果是修改文件: 统计新增方法数(通过 diff 分析)
    15. UnitTestCollectUtil.getAllMethods(project, document) 获取所有方法
    16. UnitTestCollectUtil.getChangeMethods(allMethods, changeLines, isAdded) 获取变更方法

  // 发送单元测试收集数据
  private void GC(List&lt;UnitTestMethodDto&gt;, List&lt;Integer&gt;, List&lt;Integer&gt;, VirtualFile):
    1. 计算新增行数总和
    2. 如果新增行数为 0 -> 返回
    3. 计算总行数总和
    4. 遍历方法列表:
       a. 如果 isUnitTestMethod 为 true:
          - 累加 methodLine 到 totalMethodLines
          - 累加 increment 到 totalIncrement
          - 如果 methodId 非空且 totalAccumulatedLines <= totalMethodLines:
            * 创建 CommitChangeDto(methodId, methodLine)
    5. 创建 UnitTestCollectDto:
       - repositoryName
       - addedLines
       - totalLines
       - totalMethodLines
       - totalIncrement
       - commitMessage (解混淆)
       - commitChangeList
    6. 设置 clientName (ApplicationInfo.getVersionName())
    7. 设置 clientVersion (ApplicationInfo.getApiVersion())
    8. 设置 pluginVersion (BasicActionsBundle.message)
    9. 创建 MessageDto:
       - command: LOG_TEST_COLLECTION_COMMIT
       - id: UUID
       - path: vcsRoot.getPath()
       - data: unitTestCollectDto
    10. PluginWebsocketClient.sendWsMessage(messageDto, project)

  // 获取 VCS 仓库当前修订版本
  private String Da(VirtualFile) throws Exception:
    1. VcsRepositoryManager.getInstance(project).getRepositoryForRoot(vcsRoot)
    2. 如果仓库为 null -> 返回 UUID
    3. repository.getCurrentRevision()
    4. 如果修订为空 -> 返回 UUID
    5. 检查缓存 Map 中是否已有该修订
    6. 如果有且包含当前修订 -> 返回 修订+时间戳
    7. 更新缓存 Map

  // 检查是否为 Git 提交哈希
  private static boolean vA(String):
    - 以 "0" + 小写字母开头 或 以 "`" 开头

  // 组织变更到 VCS 根映射
  private void Pa(Collection&lt;Change&gt;):
    1. 遍历变更集合
    2. 对每个变更:
       a. 获取虚拟文件
       b. ProjectLevelVcsManager.getInstance(project).getVcsRootObjectFor(file)
       c. 如果 VCS 根有效: 添加到 enum Map
```

**交互关系**:
- `UnitTestCollectUtil` -- 单元测试收集工具
  - `diffContent()` -- 获取 diff 内容
  - `getChangeByDiff()` -- 获取变更行
  - `getAllMethods()` -- 获取所有方法
  - `getChangeMethods()` -- 获取变更方法
- `PluginWebsocketClient.sendWsMessage()` -- 发送 WebSocket 消息
- `UnitTestMethodDto` -- 单元测试方法 DTO
- `UnitTestCollectDto` -- 单元测试收集 DTO
- `CommitChangeDto` -- 提交变更 DTO

---

### 2.14 FileWatchedAdapter

```
public class com.aicode.listener.FileWatchedAdapter
  implements com.intellij.openapi.fileEditor.FileDocumentManagerListener

  // --- 字段 ---
  private static final Logger enum  // SLF4J 日志

  // --- 方法 ---

  // 文档保存前
  public void beforeDocumentSaving(Document):
    1. FileDocumentManager.getInstance().getFile(document) 获取虚拟文件
    2. 如果文件非 null:
       a. 获取文件名
       b. mb(fileName) 获取文件扩展名
       c. LanguageEnum.isVaildLanguage(extension) 检查语言有效性
       d. 如果语言无效 -> 返回
    3. ProjectUtil.guessProjectForFile(file) 获取项目
    4. 异常处理: Logger.info(e.getMessage())

  // 获取文件扩展名
  private String mb(String fileName):
    - fileName.split(解混淆: ".") 最后一段
    - 如果无分割 -> 返回 null

  // --- 静态初始化 ---
  static:
    - LoggerFactory.getLogger(FileWatchedAdapter.class)
```

**注意**: 此监听器的 `beforeDocumentSaving` 方法获取了项目和文件信息，但字节码中未发现后续服务调用。可能是占位实现或逻辑被混淆。

---

### 2.15 GitBranchChangeListener (核心 -- 79KB)

```
public class com.aicode.listener.GitBranchChangeListener

  // --- 静态字段 ---
  public static final Map<String, String> CURRENT_REPO          // 当前仓库信息
  public static final Key&lt;Boolean&gt; NOTICE_CODE_KNOWLEDGE_REPO_STATUS  // 通知状态 Key
  public static final Key GIT_CODE_KNOWLEDGE_REPO_STATUS        // 代码知识库状态 Key
  private static final Map<String, String> enum                 // 仓库状态缓存
  public static final Key GIT_STATUS                            // Git 状态 Key

  // --- 实例字段 ---
  private final Project float                   // 关联项目
  private MessageBusConnection byte             // 消息总线连接

  // --- 关键方法 ---

  // 构造函数
  GitBranchChangeListener(Project):
    1. 验证项目非空
    2. 存储项目引用
    3. 调用 jC() 注册消息总线

  // 注册 Git 仓库变更监听
  private void jC():
    1. project.getMessageBus().connect() 获取 MessageBusConnection
    2. 订阅 GitRepository.GIT_REPO_CHANGE topic
    3. 使用 invokedynamic 创建 repositoryChanged 回调
    4. 回调中调用 kA(repository)

  // 仓库变更处理
  private void kA(GitRepository):
    1. 如果权限包含 CODE_KNOWLEDGE_BASE:
       a. Zb(project, repository) -- 检查仓库授权状态

  // 检查仓库授权状态
  private static void Zb(Project, GitRepository):
    1. 获取当前分支名
    2. 获取远程 URL
    3. 如果远程为空 -> 返回
    4. 检查远程分支是否包含当前分支
    5. 如果不包含: sendNoAuthStatusToWeb(project)
    6. 如果包含:
       a. 检查缓存 Map 中是否已有相同远程+分支
       b. 如果有且未变 -> 返回
       c. 更新缓存 Map
       d. 发送 GIT_CODE_KNOWLEDGE_REPO_STATUS 消息给 Agent

  // 发送无授权状态给 Web
  public static void sendNoAuthStatusToWeb(Project):
    1. getCurrentGitInfo(project) 获取当前 Git 信息
    2. 构建 JsonObject:
       - type: COMMON_SHOW_MESSAGE_IN_WEB
       - data: 包含 repositoryName, remoteUrl, branchName
       - status: -5 (未授权)
    3. dc(project, jsonObject) 发送给 Web

  // 发送消息给 Web (UI 线程)
  private static void dc(Project, JsonObject):
    1. Application.invokeLater 异步执行:
       a. 获取 WebViewWindowPanel
       b. 如果已加载: SocketMessageHandleListener.send2Web(project, data)
       c. 否则: project.putUserData(GIT_STATUS, data) 缓存

  // 代码知识库通知
  public static void codeKnowledgeNotification(Project):
    1. 检查 CODE_KNOWLEDGE_BASE 权限
    2. 如果已通知(NOTICE_CODE_KNOWLEDGE_REPO_STATUS) -> 返回
    3. 设置已通知标记
    4. 获取 GitRepositoryManager
    5. 遍历仓库: Zb(project, repository)

  // 获取当前 Git 信息
  public static void getCurrentGitInfo(Project):
    1. GitRepositoryManager.getInstance(project).getRepositories()
    2. 遍历仓库:
       a. 获取当前分支名
       b. 获取远程 URL
       c. 更新 CURRENT_REPO Map:
          - "remoteUrl" -> remoteUrl
          - "branchName" -> branchName
          - "currentBranchName" -> branchName
          - "currentRemoteUrl" -> remoteUrl

  // 处理 Git 仓库状态响应
  public static void handleGitRepoStatus(String, JsonObject, Project):
    1. 解析响应中的 data.status (int)
    2. 获取 AGENT_REQUEST 中的 MessageDto
    3. 提取 remoteUrl 和 branchName
    4. 调用 va(requestId, project, statusCode, remoteUrl, branchName)
    5. 清除 AGENT_REQUEST 缓存

  // 处理 Git 异常
  public static void handleGitException(String, String, Project, CommandEnum, String):
    1. 如果 command == GIT_SAVE_TOKEN:
       a. 构建 COMMON_SHOW_MESSAGE_IN_WEB 消息
       b. 包含错误信息和 2000ms 超时
       c. send2Web 发送给 Web
    2. 否则:
       a. 构建 GIT_CODE_KNOWLEDGE_REPO_STATUS 消息
       b. 包含 remoteUrl, branchName, repositoryName, command
       c. dc 发送给 Web
       d. 清除 AGENT_REQUEST 缓存

  // 处理 Git 响应
  public static void handleGitResponse(String, JsonObject, Project, CommandEnum):
    1. 如果 command == GIT_SAVE_TOKEN:
       a. 构建 COMMON_SHOW_MESSAGE_IN_WEB 成功消息
       b. 包含 2000ms 超时, autoClose=true
       c. send2Web 发送给 Web
    2. 否则:
       a. getCurrentGitInfo(project) 刷新 Git 信息
       b. 解析响应中的 data
       c. 构建状态消息:
          - type: COMMON_SHOW_MESSAGE_IN_WEB
          - status: 解析值
          - command: 当前命令类型
          - repositoryName: 从 remoteUrl 提取
       d. dc 发送给 Web
       e. 如果是 GIT_CODE_KNOWLEDGE_REPO_STATUS 且分支不在远程:
          sendNoAuthStatusToWeb(project)

  // 处理仓库状态(含通知)
  private static void va(String, Project, int, String, String):
    1. 如果 ignoreGitAuth 设置开启:
       a. 检查版本号是否匹配
       b. 如果匹配 -> 返回
       c. 重置 ignoreGitAuth = false
    2. 如果是 GIT_GET_STATUS 请求 -> 清除并返回
    3. 获取 GitRepoStatusEnum
    4. 如果状态为 UNAUTHORIZED:
       a. 从 remoteUrl 提取仓库平台名
       b. 格式化通知消息
    5. 创建 Notification:
       - group: 解混淆通知组
       - title: 解混淆标题
       - type: INFO
    6. 如果不需要跳过 Web:
       a. 添加 "打开知识库" 通知动作 ($R)
    7. 添加 "忽略" 通知动作 ($H)
    8. 通知显示在项目中

  // 从 URL 提取仓库名
  public static String getRepositoryNameFromUrl(String url):
    1. 如果 url 为空 -> 返回 null
    2. 如果以 ".git" 结尾 -> 截取掉
    3. lastIndexOf('/') + 1 截取
    4. 如果无 '/' -> 返回 null

  // 释放资源
  public void dispose():
    1. 如果 byte(MessageBusConnection) 非 null:
       - byte.disconnect()
```

**交互关系**:
- `PluginWebsocketClient` -- WebSocket 通信
  - `AGENT_REQUEST` -- Agent 请求缓存
  - `WEB_REQUEST` -- Web 请求缓存
  - `sendWsMessage()` -- 发送消息
- `SocketMessageHandleListener.send2Web()` -- 发送消息给 WebView
- `GitRepositoryManager` -- Git 仓库管理
- `AICodeSettingsState` -- 设置状态
  - `permissions` -- 权限集合
  - `ignoreGitAuth` -- 忽略 Git 授权
  - `ignoreVersion` -- 忽略版本
  - `codeKnowledgeWebUrl` -- 代码知识库 URL
- `ChatService.isCurrentBranchRemote()` -- 检查分支是否在远程
- `CommonService.messageBus()` -- 消息总线通知
- `WebViewWindowPanel` -- WebView 面板

---

### 2.16 GitBranchChangeListener$H ("忽略"通知动作)

```
public class com.aicode.listener.GitBranchChangeListener$H
  extends com.intellij.notification.NotificationAction

  // --- 字段 ---
  public final Project enum  // 关联项目

  // --- 方法 ---

  // 动作执行
  public void actionPerformed(AnActionEvent, Notification):
    1. notification.hideBalloon()  // 关闭通知
    2. CommonService.messageBus(project, 解混淆: "已忽略授权提醒", INFO)
    3. AICodeSettingsState.getInstance().ignoreGitAuth = true
    4. AICodeSettingsState.getInstance().ignoreVersion = BasicActionsBundle.message(解混淆)
```

---

### 2.17 GitBranchChangeListener$R ("打开知识库"通知动作)

```
public class com.aicode.listener.GitBranchChangeListener$R
  extends com.intellij.notification.NotificationAction

  // --- 方法 ---

  // 动作执行
  public void actionPerformed(AnActionEvent, Notification):
    1. notification.hideBalloon()
    2. 构建知识库 URL:
       a. 如果 codeKnowledgeWebUrl 以 "/#" 结尾: url + apiKey
       b. 如果以 "/" 结尾(非"/#"): url + "?token=" + apiKey
       c. 否则: 直接使用 codeKnowledgeWebUrl
    3. BrowserUtil.browse(url)  // 在浏览器中打开
```

---

### 2.18 GitBranchChangeListener$b ("授权仓库"通知动作)

```
public class com.aicode.listener.GitBranchChangeListener$b
  extends com.intellij.notification.NotificationAction

  // --- 字段 ---
  public final String float   // remoteUrl
  public final String byte    // branchName
  public final Project enum   // 关联项目

  // --- 方法 ---

  // 动作执行
  public void actionPerformed(AnActionEvent, Notification):
    1. notification.hideBalloon()
    2. 构建 JsonObject:
       - "remoteUrl" -> remoteUrl
       - "branchName" -> branchName
    3. PluginWebsocketClient.sendWsMessage(GIT_REPO_AUTHORIZE, data, project)
```

---
