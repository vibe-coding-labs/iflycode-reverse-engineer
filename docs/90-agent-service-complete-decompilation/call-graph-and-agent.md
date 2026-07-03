## 4. 与 Agent 进程的交互

### 4.1 WebSocket 通信模型

所有服务类通过 `PluginWebsocketClient` 与 Agent 进程通信：

```
IDE插件 (Service层)                    Agent进程
    |                                      |
    | --sendWsMessage(MessageDto)--------> |
    |                                      |
    | <--handleAgentAction(CommandEnum)--- |
    |                                      |
```

### 4.2 MessageDto 构造模式

```java
// 统一的消息构造模式
MessageDto msg = new MessageDto(
    IdUtil.fastSimpleUUID(),           // 唯一ID
    CommandEnum.XXX.getType()          // 命令类型字符串
);
msg.setPath(projectPath);              // 项目路径
msg.setData(data);                     // 业务数据
PluginWebsocketClient.sendWsMessage(msg, project);
```

### 4.3 AGENT_REQUEST 追踪

Agent 请求通过 `ConcurrentNavigableMap<String, String> AGENT_REQUEST` 追踪：
- 发送请求时: `AGENT_REQUEST.put(sessionId, commandType)`
- 收到响应时: `AGENT_REQUEST.remove(sessionId)`
- 用于检测超时请求和防止重复发送

### 4.4 Agent 进程生命周期

```
PluginStartupActivity
    |
    v
RestartableAgentProcessService (可重启包装器)
    |
    v
PluginAgentProcessServiceImpl (实际进程管理)
    |
    +-- unZipAgent()     解压Agent二进制
    +-- copySource()     复制WASM资源
    +-- launchAgent()    启动Agent进程
    +-- shutdown()       优雅关闭
    |
    v
PluginAgentProcessHandler (OS进程句柄)
    |
    +-- destroyProcess()  请求终止
    +-- killProcess()     强制终止
```

### 4.5 InitService 定时检查

InitService 在项目初始化时启动定时任务（500ms 间隔），定期检查：
1. RequestTipServiceImpl.LAST_REQUEST 中的过期请求
2. 超时阈值通过 H() 解密获取
3. 过期请求被清除并通知 UI

---

## 5. 内部类用途汇总

| 内部类 | 类型 | 用途 |
|--------|------|------|
| ChatService$Ia | SwitchMap | WebViewDataTypeEnum + CommandEnum 到 switch case 的映射数组 |
| CodeCheckService$Da | SwitchMap | CommandEnum + WebViewDataTypeEnum 映射 |
| CodeCompleteService$ja | SwitchMap | CommandEnum 映射 (CODE_COMPLETE, USER_CAN_CODE_ENHANCE, ACTION_SYNC_DOCUMENT_LIST) |
| CodeSearchService$Aa | SwitchMap | CommandEnum + WebViewDataTypeEnum 映射 |
| CodeSearchService$ga | TypeToken | Gson 反序列化 `List&lt;CodeRepoInfoDto&gt;` |
| CodeSearchService$ia | TypeToken | Gson 反序列化 `List&lt;CodeInfoDto&gt;` |
| CommonService$Fa | MouseMotionAdapter | Gutter 行号区域鼠标悬停效果（手型光标） |
| CommonService$Ha | SwitchMap | WebViewDataTypeEnum + ChatOperationEnum 映射 |
| CommonService$Ma | TypeToken | Gson 反序列化 `List<CodeInfoDto$RangeDTO>` |
| GitReviewService$Ca | SwitchMap | CommandEnum + WebViewDataTypeEnum 映射 |
| InlineChatCommandService$fa | TypeToken | Gson 反序列化 `List<CodeInfoDto$RangeDTO>` |
| InlineChatCommandService$ka | SwitchMap | CommandEnum + InlineChatCategoryEnum 映射 |
| SqlService$Ba | SwitchMap | CommandEnum + WebViewDataTypeEnum 映射 |
| UserService$Ja | SwitchMap | CommandEnum + WebViewDataTypeEnum 映射 |
| UserService$da | NotificationAction | IntelliJ 通知栏"去登录"按钮，点击打开浏览器 |
| UserService$ea | TypeToken | Gson 反序列化 `List&lt;FunctionModelInfo&gt;` |
| UserService$la | TypeToken | Gson 反序列化 `List&lt;String&gt;` |

### 内部类分类统计

- **SwitchMap (9个)**: 编译器生成的枚举 switch 优化，将 ordinal 映射到连续 case 编号
- **TypeToken (5个)**: Gson 泛型反序列化支持，保留完整泛型类型信息
- **UI组件 (2个)**: MouseMotionAdapter 和 NotificationAction，处理用户交互
- **无业务逻辑内部类**: 所有内部类均为编译器生成或框架辅助类

---

## 6. H() 混淆字符串调用分析

### 6.1 解密方法分布

| 服务类 | H() 方法来源 | 用途 |
|--------|-------------|------|
| ChatService | RequestCancelException.H(), NewFileUtils.H() | JSON 字段名解密 |
| CodeCompleteService | PropertyUtils.H(), GitReviewService.H() | 字段名解密 |
| CodeSearchService | LanguageFileExtensionDetails.H() | 语言文件扩展名 |
| CommonService | NewFileUtils.H(), MethodGeneratorConfig.H() | 配置键名解密 |
| GitReviewService | CancelRequestTip.H(), NewFileUtils.H(), IndentLineUtil.H() | 字段名/消息解密 |
| SqlService | RequestCancelException.H(), CodeCompleteService.H() | 数据库配置字段名 |
| UserService | PositionUtil.H(), GenericUtils.H(), IndentLineUtil.H() | URL/消息解密 |
| InitService | CancelRequestTip.H(), ActionButton.H() | 提示文本解密 |

### 6.2 H() 解密算法

CodeCompleteService.H() 和 GitReviewService.H() 包含内联的字符串解密算法：
1. 获取调用者类名和方法名（通过 LinkageError.getStackTrace()）
2. 基于类名+方法名计算 XOR 密钥（位移+异或运算）
3. 对输入字符串逐字符 XOR 解密
4. 返回解密后的明文字符串

其他 H() 方法调用分散在各工具类中（PropertyUtils, NewFileUtils, CancelRequestTip 等），使用类似的 XOR 解密机制。

---

## 7. 关键发现

### 7.1 统一分发模式
所有 7 个核心服务类（ChatService, CodeCheckService, CodeCompleteService, CodeSearchService, CommonService, GitReviewService, SqlService, UserService）严格遵循 `handleAction` + `handleAgentAction` 双通道分发模式。WebView 前端请求走 `handleAction`，Agent 进程响应走 `handleAgentAction`。

### 7.2 Agent 通信协议
所有服务通过 `PluginWebsocketClient.sendWsMessage(MessageDto, Project)` 发送请求，通过 `SocketMessageHandleListener.send2Web(Project, Object)` 发送响应到 WebView。请求追踪使用 `AGENT_REQUEST` ConcurrentNavigableMap。

### 7.3 配置管理集中
CommonService.updateConfig() 是唯一的配置写入入口，覆盖 AICodeSettingsState 的 20+ 个字段，包括功能开关、权限控制、框架选择等。

### 7.4 进程管理分层
Agent 进程管理采用三层架构：PluginAgentProcessService(接口) -> PluginAgentProcessServiceEx(扩展接口) -> PluginAgentProcessServiceImpl(实现) + RestartableAgentProcessService(可重启包装器)。

### 7.5 行内聊天独立子系统
InlineChatCommandService 不遵循标准 handleAction 模式，仅实现 handleAgentAction，通过 SessionController 和 Editor UserData 管理会话状态，是相对独立的子系统。