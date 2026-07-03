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
| SendStopActionButtonPanel.&lt;clinit&gt;() | `偂歼` | 发送 | (发送按钮) |
| SendStopActionButtonPanel.&lt;clinit&gt;() | `叏速` | 停止 | (停止按钮) |
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
| RefreshAction.&lt;init&gt;() | `剬旫` | 刷新 | (刷新操作) |
| UnitTestService.hc() | `甕戚甫侈凼锟` | 单元测试 | (单元测试) |
| UnitTestService.jB() | `名平协浑旺旬斮泂` | 生成单元测试 | config.unit.test.title |
| UnitTestService.aA() | `俻恣巾渒陳` | 测试用例 | (测试用例) |
| RequestTipServiceImpl.ib() | `既廽让` | 请求中 | aicode.requesting |
| RequestTipServiceImpl.Ac() | `墤开甁戎串100` | 请求超时 100 | aicode.complete.time.out |
| BatchUnitTestService.batchUnitTestMessage() | `擁佐戇劈` | 批量生成 | (批量操作) |
| BatchUnitTestService.batchUnitTestDownload() | `擁佐夦贲` | 批量下载 | (批量下载) |
| UnitTestCollectUtil.isTestOfMethod() | `莰发泮觥夯贻` | 测试方法 | (测试方法识别) |
| ChatOperationEnum.&lt;clinit&gt;() | `寏诀桛斪廠斘仩` | 新建对话 | (聊天操作) |
| ChatOperationEnum.&lt;clinit&gt;() | `寏诀桛仹砛毋辜` | 清除对话 | (聊天操作) |
| ChatOperationEnum.&lt;clinit&gt;() | `針纩廥讱` | 删除 | (聊天操作) |
| ChatOperationEnum.&lt;clinit&gt;() | `釚纮衖问泷釕` | 重新提问 | (聊天操作) |
| DuplicateRule.&lt;clinit&gt;() | `趨辜` | 覆盖 | config.batch.unit.test.duplicate.filename.overwrite |
| DuplicateRule.&lt;clinit&gt;() | `觝皍` | 跳过 | config.batch.unit.test.duplicate.filename.skip |
| DuplicateRule.&lt;clinit&gt;() | `係畆仗聞` | 保留二者 | config.batch.unit.test.duplicate.filename.coexist |
| CodeCollectEnum.&lt;clinit&gt;() | `晠胧衺具` | 代码解释 | (代码功能) |
| CodeCollectEnum.&lt;clinit&gt;() | `掉儾` | 纠错 | (代码功能) |
| CodeCollectEnum.&lt;clinit&gt;() | `夒利` | 优化 | (代码功能) |
| CodeCollectEnum.&lt;clinit&gt;() | `旫庡` | 注释 | (代码功能) |
| CodeCollectEnum.&lt;clinit&gt;() | `半浔` | 测试 | (代码功能) |
| CodeCollectEnum.&lt;clinit&gt;() | `毋辜醜绨` | 代码检查 | (代码功能) |
| CodeCollectEnum.&lt;clinit&gt;() | `兩仉` | 拆分 | (代码功能) |
| RepoStatusEnum.&lt;clinit&gt;() | `朵揓朘` | 已授权 | (知识库授权) |
| RepoStatusEnum.&lt;clinit&gt;() | `徚刂妐卍` | 未授权 | (知识库授权) |
| RepoStatusEnum.&lt;clinit&gt;() | `徚奟瑝` | 已失效 | (知识库授权) |
| RepoStatusEnum.&lt;clinit&gt;() | `工奪攓` | 初始化 | (知识库) |
| RepoStatusEnum.&lt;clinit&gt;() | `工掎杅兩仉狭恚` | 初始化中请等待 | (知识库) |
| TestGenerationProcess.&lt;clinit&gt;() | `甀戏匎洐` | 批量生成 | (测试生成) |
| TestGenerationProcess.&lt;clinit&gt;() | `弆姍缉诎亸硚` | 收集上下文 | (测试生成) |
| TestGenerationProcess.&lt;clinit&gt;() | `弇姓缎诗幰扸術匎洐` | 生成单元测试代码 | (测试生成) |
| UnitTestMockEnum.&lt;clinit&gt;() | `膱勳` | Mock | (测试 Mock) |
| UnitTestMockEnum.&lt;clinit&gt;() | `儨閶` | Spy | (测试 Mock) |
| PyUnitTestMockEnum.&lt;clinit&gt;() | `臵劷` | Mock | (测试 Mock) |
| PyUnitTestMockEnum.&lt;clinit&gt;() | `儨閶` | Spy | (测试 Mock) |
| PluginSceneEnum.&lt;clinit&gt;() | `n|&#123;i牗朳` | iFlyCode | aicode.plugin.scene |
| RestartEnum.&lt;clinit&gt;() | `wzxtn吰劷` | 重启插件 | (插件重启) |
| RestartEnum.&lt;clinit&gt;() | `PQSsi拈织迁掺` | 重启 Agent | (Agent 重启) |
| RestartEnum.&lt;clinit&gt;() | `PQSsi弘帢公闲` | 重启进程 | (进程重启) |
| SpanAttrEnum.&lt;clinit&gt;() | `揔仩男扬呖` | 代码补全 | (APM 追踪) |
| SpanAttrEnum.&lt;clinit&gt;() | `揈们曫斯` | 代码解释 | (APM 追踪) |
| SpanAttrEnum.&lt;clinit&gt;() | `揈们牗朳` | 代码检查 | (APM 追踪) |
| TracerEnum.&lt;clinit&gt;() | `wzxtn弝帧` | 重启 | (APM 追踪) |
| TracerEnum.&lt;clinit&gt;() | `vQSsi吵劲弝帧` | Agent 重启 | (APM 追踪) |
| TracerEnum.&lt;clinit&gt;() | `弘帢讯彊` | 进程 | (APM 追踪) |
| OpenWindowAction.&lt;init&gt;() | `右赕寿诛` | 打开窗口 | (窗口操作) |
| OpenTelemetryService.handApmConfig() | `IﾽﾵD)曶斲开帺` | APM 开关 | (APM 配置) |
| ExcludeMethodConfigurable.&lt;init&gt;() | `斦泊呖禫` | 方法配置 | config.batch.unit.test.exclude.method.separator |
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
- 枚举类（`*Enum.&lt;clinit&gt;()`）的中文解码最为密集，因其存储大量 UI 显示名称
- 服务类（`*Service`）的中文多为操作提示和错误消息
