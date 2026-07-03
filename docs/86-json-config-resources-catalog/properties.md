## 4. 属性文件

### 4.1 messages/aicode.properties

**文件路径**: `extracted/jar-contents/messages/aicode.properties`
**条目数**: 14

| Key | 原始值(Unicode) | 解码值 |
|-----|----------------|--------|
| aicode.no.tips | 没有其他建议 | 没有其他建议 |
| aicode.enableAICode.auto.trigger | √ 自动触发代码补全 | √ 自动触发代码补全 |
| aicode.disableAICode.auto.trigger | 自动触发代码补全 | 自动触发代码补全 |
| aicode.StatusBarPopup.setting | 插件配置 | 插件配置 |
| aicode.not.signed | 未登录 | 未登录 |
| aicode.requesting | 请求中 | 请求中 |
| aicode.plugin.download.success.msg | 新版本%s已经下载完成，重启生效 | 新版本%s已经下载完成，重启生效 |
| aicode.plugin.download.success.option1 | 立刻重启 | 立刻重启 |
| aicode.plugin.download.success.option2 | 忽略 | 忽略 |
| aicode.plugin.update.success | 已经安装完成，请愉快的使用吧~ | 已经安装完成，请愉快的使用吧~ |
| aicode.plugin.update.success.msg | 检测到%s有新版本，是否立即更新？ | 检测到%s有新版本，是否立即更新？ |
| aicode.plugin.update.option1 | 更新 | 更新 |
| aicode.plugin.update.option2 | 忽略 | 忽略 |
| aicode.update.installing.title | 插件正在下载中 | 插件正在下载中 |

### 4.2 messages/BasicActionsBundle.properties

**文件路径**: `extracted/jar-contents/messages/BasicActionsBundle.properties`
**条目数**: 115

#### 插件元信息

| Key | 解码值 |
|-----|--------|
| group.aicode.EditorActionGroup.text | 星火飞码 iFlyCode |
| aicode.plugin.title | iFlyCode |
| aicode.plugin.id | com.iflytek |
| aicode.plugin.version | 3.4.2-222 |
| aicode.agent.version | 3.4.2-222 |
| aicode.plugin.scene | iFlyCode |
| aicode.plugin.public.date | 2025-04-22 |
| aicode.faq.web.url | https://portal.example.com/document?flagName=常见问题 |

#### 基础操作

| Key | 解码值 |
|-----|--------|
| aicode.action.createFile | 新建 |
| aicode.action.get | 采纳 |
| aicode.action.diff | 比较 |
| aicode.action.diff.replace | 采纳 |
| aicode.action.new | 新建指令 |
| action.settings | 设置 |
| action.logout | 退出 |
| action.help | 帮助 |
| action.close | 关闭 |
| custom.component.me | 我 |

#### 错误信息

| Key | 解码值 |
|-----|--------|
| aicode.chat.error | 回复异常，请重试！ |
| aicode.network.error | 连接网络失败，请检查网络 |
| aicode.parse.web.url.error.text | 请求过于频繁，服务器暂时拒绝服务，请稍后再试 |
| aicode.component.test.message.error | 非常抱歉，您提问的内容不符合单测要求。 |
| aicode.no.select.error | 请先选择代码片段 |
| token.auth.empty | 您尚未登录%s插件，请登录后继续使用。 |
| code.check.empty.content | 未检测到问题 |
| diff.select.empty.content | 未选中代码块 |
| aicode.cycleNextInlays.noMoreAvailableError | 暂无更多候选结果 |
| inline.chat.error | 无结果，请重试 |

#### 补全模式

| Key | 解码值 |
|-----|--------|
| aicode.single.line.model | 单行模式 |
| aicode.start.model | 智能模式 |

#### 新建文件

| Key | 解码值 |
|-----|--------|
| aicode.create.file.name | 文件名称： |
| aicode.create.file.desc | 文件路径： |

#### 批量单元测试配置

| Key | 解码值 |
|-----|--------|
| config.batch.unit.test.title | 批量生成单元测试 |
| config.batch.unit.test.framework.title | 测试框架： |
| config.batch.unit.test.mock.framework.title | Mock框架： |
| config.batch.unit.test.separator | 单测配置 |
| config.batch.unit.test.exclude.method.separator | 方法配置 |
| config.batch.unit.test.file.separator | 文件配置 |
| config.batch.unit.test.duplicate.filename.title | 文件重名规则： |
| config.batch.unit.test.duplicate.filename.overwrite | 覆盖 |
| config.batch.unit.test.duplicate.filename.skip | 跳过 |
| config.batch.unit.test.duplicate.filename.coexist | 保留二者 |
| config.batch.unit.test.private.method.title | 私有方法： |
| config.batch.unit.test.private.method.content | 私有方法生成单元测试 |
| config.batch.unit.test.exclude.title | 以下方法不生成单测： |
| config.batch.unit.test.exclude.empty.text | 以下方法不生成单测 |
| config.batch.unit.test.select.empty.title | 未选择类 |
| config.batch.unit.test.select.one.class | (已选择1个类) |
| config.batch.unit.test.select.class.number.title.prefix | 已选择 |
| config.batch.unit.test.select.class.number.title.suffix | 个Java类 |
| config.batch.unit.test.select.class.number.prefix | (已选择 |
| config.batch.unit.test.select.class.number.suffix | 个类) |
| config.batch.unit.test.notice | 单测生成完毕&#123;0&#125;，成功&#123;1&#125;个文件，跳过&#123;2&#125;个文件，失败&#123;3&#125;个文件 |
| config.batch.unit.test.test.module.directory.title | 单元测试代码目录： |
| config.batch.unit.test.task.error | 上一个单元测试任务还未完成！请稍后 |
| config.batch.unit.test.create.error | 测试代码目录创建失败！ |
| config.batch.unit.test.create.single.error | 单元测试代码生成失败！&#123;0&#125; |
| config.batch.unit.test.create.single.error.ignore | 所选代码无需生成单元测试 |
| config.batch.unit.test.create.single.repeat.error | 单元测试代码生成失败！所选文件正在生成单元测试代码中，稍后再试 |
| config.batch.unit.test.cancel.message | 单元测试代码生成中，退出将中断生成，确认要退出吗？ |
| config.batch.unit.test.cancel.title | 确认退出 |
| config.batch.unit.test.message.error | 非常抱歉，目前只支持JAVA语言的批量单测。 |
| config.batch.unit.test.message.ide.error | 非常抱歉，批量单测只支持IntelliJ IDEA编译器。 |
| config.batch.unit.test.message.module.error | 非常抱歉，批量单测只支持选择单个模块下代码。 |

#### 生成策略

| Key | 解码值 |
|-----|--------|
| config.batch.unit.test.generate.by.template.title | 生成策略： |
| config.batch.unit.test.generate.by.template | 快速生成 |
| config.batch.unit.test.generate.by.template.ai | 精准生成 |
| config.batch.unit.test.generate.by.template.help.text | 调用规则能力快速生成单元测试基础代码 |
| config.batch.unit.test.generate.by.template.ai.help.text | 结合AI模型精准识别代码分支，并生成单元测试代码 |

#### 生成流程

| Key | 解码值 |
|-----|--------|
| config.batch.unit.test.generation.process.title | 生成流程： |
| config.batch.unit.test.generation.process.help.text | 生成流程：批量单测文件生成过程；生成单测：只生成选择文件的单测文件；生成单测+编译：生成选择文件的单测文件并编译文件；生成单测+编译+执行：生成选择文件的单测文件，编译文件后执行单测，收集单测覆盖度信息 |
| config.batch.unit.test.generation.limit.title | 文件数量限制： |
| config.batch.unit.test.generation.limit.help.text | 1、文件数量会限制选择生成单测的代码文件数量。若选择的文件数量超出限制，则只会生成先选择的文件。2、请勿将文件数量设置得过大，否则可能会导致生成的时间过长，或者出现卡顿、超时等异常情况。 |

#### 文件重名规则帮助文本

| Key | 解码值 |
|-----|--------|
| config.batch.unit.test.duplicate.skip.help.text | 遇到已生成重名单测文件，会覆盖该单测代码文件 |
| config.batch.unit.test.duplicate.overwrite.help.text | 遇到已生成重名单测文件，会跳过不生成该文件 |
| config.batch.unit.test.duplicate.coexist.help.text | 遇到已生成重名单测文件，会自动添加序号，如QuickSortTest.java QuickSortTest1.java |

#### 模型服务状态

| Key | 解码值 |
|-----|--------|
| config.batch.unit.test.servers.status.title | (模型服务： |
| config.batch.unit.test.servers.status.idle | 空闲) |
| config.batch.unit.test.servers.status.busy | 繁忙) |
| config.batch.unit.test.servers.status.saturate | 饱和) |
| config.batch.unit.test.servers.status.help.text | 模型服务状态：三种服务器AI资源状态（空闲：资源充足、繁忙：使用人数较多、资源少、拥堵：资源很少，AI不能提供有效支持） |
| config.batch.unit.test.branch.commit | 当前分支信息 |
| config.batch.unit.test.generate.wait.message | &#123;0&#125;正在等待模型生成，预计还需&#123;1&#125;s，已等待&#123;2&#125;s |
| config.batch.unit.test.save.path.content | 记录单元测试代码目录路径，下次默认保存到此目录 |

#### 单元测试生成结果

| Key | 解码值 |
|-----|--------|
| unit.test.generate.success | 单元测试代码已生成，点击查看 |
| unit.test.method.generate.success | 单元测试代码生成完毕 |
| unit.test.method.generate.skip.message | &#123;0&#125;:下列方法无需生成单元测试&#123;1&#125; |
| unit.test.method.generate.skip.button.skip | 跳过 |
| unit.test.method.generate.skip.button.generator | 生成 |
| unit.test.method.request.error.text | 网络超时，请重试 |
| unit.test.method.generate.error.text | 收集上下文失败，请重试 |
| unit.test.method.generate.case.error.text | 生成用例失败，请重试 |
| unit.test.method.generate.code.error.text | 生成单元测试代码失败，请重试 |

#### 单个单元测试配置

| Key | 解码值 |
|-----|--------|
| config.unit.test.title | 生成单元测试 |
| config.unit.test.createFile.title | 导入 |
| config.unit.test.createFile.comment | iFlyCodeTestGenerate# |
| config.unit.test.createFile.error.text | 文件名不能为空 |
| config.unit.test.createFile.error.text2 | 目录不能为空 |

#### 配置与端点

| Key | 解码值 |
|-----|--------|
| aicode.otel.switch | false |
| aicode.otel.endpoint | https://saas.api.example.com/v1/traces |
| aicode.complete.time.out | 10000 |

#### 知识库

| Key | 解码值 |
|-----|--------|
| aicode.knowledge.tip | 申请获取您当前代码库建立索引，此索引结果仅用于提升生成代码的质量，您可以在知识管理平台随时移除。 |
| aicode.knowledge.protocol.tip | 仅支持HTTP/HTTPS协议代码仓库地址的初始化，请在管理平台修改代码仓库的访问方式。 |
| aicode.knowledge.token.invalid.tip | 无有效令牌，请在知识管理平台进行管理。 |
| aicode.knowledge.authorize.expired.tip | 当前代码分支已失效，请重新授权。 |
| aicode.knowledge.management | 跳转管理平台 |
| aicode.knowledge.authorization | 授权 |
| aicode.file.download | 导出 |

#### 更新

| Key | 解码值 |
|-----|--------|
| aicode.update.installing.title | 正在下载&#123;0&#125;插件 |

#### 内联聊天

| Key | 解码值 |
|-----|--------|
| inline.chat.error | 无结果，请重试 |
| inline.chat.accept.text | 采纳(&#123;0&#125;) |
| inline.chat.reject.text | 拒绝(&#123;0&#125;) |
| inline.chat.retry.text | 重试(&#123;0&#125;) |
| inline.chat.diff.text | 查看diff |
| inline.chat.cancel.text | 取消(&#123;0&#125;) |

#### 一键修复

| Key | 解码值 |
|-----|--------|
| action.CodeProblemsTreePopupAction.text | 一键修复 |

### 4.3 属性文件在代码中的引用

`BasicActionsBundle` 被 60+ 个类引用，主要使用类：

| 类 | 引用场景 |
|----|---------|
| `com.aicode.message.BasicActionsBundle` | 消息包入口类 |
| `com.aicode.PluginStartupActivity` | 插件启动活动 |
| `com.aicode.statusBar.StatusBarPopup` | 状态栏弹窗 |
| `com.aicode.statusBar.StatusBarWidgetFactory` | 状态栏小部件 |
| `com.aicode.test.UnitTestService` | 单元测试服务 |
| `com.aicode.test.UnitTestDialog` | 单元测试对话框 |
| `com.aicode.diff.DiffService` | Diff 服务 |
| `com.aicode.diff.DiffDialog` | Diff 对话框 |
| `com.aicode.updater.PluginUpdater` | 插件更新器 |
| `com.aicode.agent.service.ChatService` | 聊天服务 |
| `com.aicode.agent.service.UserService` | 用户服务 |
| `com.aicode.agent.service.InitService` | 初始化服务 |
| `com.aicode.agent.service.CodeCheckService` | 代码检查服务 |
| `com.aicode.inline.InlineChatInputPanel` | 内联聊天输入面板 |
| `com.aicode.inline.render.*` | 内联聊天渲染器 |
| `com.aicode.action.*` | 各种操作类 |
| `com.aicode.view.WebViewWindowPanel` | WebView 窗口面板 |
| `com.aicode.template.TemplateGenerator` | 模板生成器 |
| `com.aicode.apm.OpenTelemetryConfig` | OpenTelemetry 配置 |
| `com.aicode.listener.*` | 各种监听器 |

---
