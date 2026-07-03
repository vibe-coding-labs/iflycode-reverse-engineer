# iFlyCode i18n 完整字符串表分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-13

## 概述

本文档对 iFlyCode 3.4.2-222 插件的所有国际化（i18n）字符串进行了完整提取与分类。字符串来源于四个渠道：

| 来源 | 文件路径 | 字符串数量 |
|------|----------|-----------|
| properties 资源文件 | `messages/BasicActionsBundle.properties` | 115 |
| properties 资源文件 | `messages/aicode.properties` | 14 |
| Agent webpack bundle | `agent/bin/index.js` | 685 |
| WebView 前端 JS | `webview/assets/index-*.js` | 365 |
| **合计** | | **1179** |

> 注：H() 混淆字符串因 XOR 解码后中文恢复率较低（约 8.9% 可辨识中文），仅少量可辨识条目列入各模块。H() 解码总计 4628 调用，其中高质量 4114、中等 119、垃圾 395。

---

## 1. 认证与登录

| Key / 标识 | 中文值 | 来源 |
|------------|--------|------|
| token.auth.empty | 您尚未登录%s插件，请登录后继续使用。 | properties |
| aicode.not.signed | 未登录 | properties |
| aicode.StatusBarPopup.setting | 插件配置 | properties |
| action.logout | 退出 | properties |
| showMessage() | 厷登录 | H() |
| 用户未登录 | 用户未登录 | Agent |
| 超时未登录 | 超时未登录 | Agent |
| 未授权，请重新登录 | 未授权，请重新登录 | Agent |
| 登录地址未配置，请联系管理员 | 登录地址未配置，请联系管理员 | Agent |
| 开始登录轮询 | 开始登录轮询 | Agent |
| 终止登录轮询 | 终止登录轮询 | Agent |
| 使用浏览器登录 | 使用浏览器登录 | WebView |
| 取消登录 | 取消登录 | WebView |
| 已完成登录？ | 已完成登录？ | WebView |
| 请在浏览器完成登录操作 | 请在浏览器完成登录操作 | WebView |
| 请点击这里 | 请点击这里 | WebView |
| 免密登录 | 免密登录 | WebView |
| 无权限！请登录后重试 | 无权限！请登录后重试 | WebView |

---

## 2. 通信与网络

| Key / 标识 | 中文值 | 来源 |
|------------|--------|------|
| aicode.network.error | 连接网络失败，请检查网络 | properties |
| aicode.parse.web.url.error.text | 请求过于频繁，服务器暂时拒绝服务，请稍后再试 | properties |
| aicode.requesting | 请求中 | properties |
| 网络异常，请检查网络 | 网络异常，请检查网络 | Agent |
| 网络异常，请重试 | 网络异常，请重试 | Agent |
| 网络通信异常 | 网络通信异常 | Agent |
| 网络通讯超时 | 网络通讯超时 | Agent |
| 请求超时 | 请求超时 | Agent |
| 请求出错 | 请求出错 | Agent |
| 请求已取消 | 请求已取消 | Agent |
| 请求地址： | 请求地址： | Agent |
| 请求配置： | 请求配置： | Agent |
| 请求错误 | 请求错误 | Agent |
| 请求不支持消息推送 | 请求不支持消息推送 | Agent |
| SSE推送失败 | SSE推送失败 | Agent |
| 连接已断开 | 连接已断开 | Agent |
| 连接已经关闭 | 连接已经关闭 | Agent |
| 连接已重置, 重置原因： | 连接已重置, 重置原因： | Agent |
| 连接重置失败, 重置原因： | 连接重置失败, 重置原因： | Agent |
| 连接超时 | 连接超时 | Agent |
| 非法连接： | 非法连接： | Agent |
| 连接池别名已存在 | 连接池别名已存在 | Agent |
| 连接池已经关闭 | 连接池已经关闭 | Agent |
| 连接池已达到最大连接数，无法创建更多连接 | 连接池已达到最大连接数，无法创建更多连接 | Agent |
| 连接池正在关闭 | 连接池正在关闭 | Agent |
| 连接池等待队列已满 | 连接池等待队列已满 | Agent |
| 连接池连接有效性检查失败:没有结果集. | 连接池连接有效性检查失败:没有结果集. | Agent |
| 获取连接请求等待超时 | 获取连接请求等待超时 | Agent |
| 无效的连接串或连接池别名或连接属性 | 无效的连接串或连接池别名或连接属性 | Agent |
| 进程通信主通信建立完成 | 进程通信主通信建立完成 | Agent |
| 消息加密失败 | 消息加密失败 | Agent |
| 消息解密失败 | 消息解密失败 | Agent |
| 消息校验异常 | 消息校验异常 | Agent |
| 消息长度超出限制512M | 消息长度超出限制512M | Agent |
| 初始化SSL环境失败 | 初始化SSL环境失败 | Agent |
| net server on回调 close | net server on回调 close | Agent |
| net初始化createServer on回调 error | net初始化createServer on回调 error | Agent |
| net链接connect on回调 data | net链接connect on回调 data | Agent |
| net链接connect失败 尝试删除.sock文件 | net链接connect失败 尝试删除.sock文件 | Agent |
| net链接connect失败 尝试删除.sock文件 失败 | net链接connect失败 尝试删除.sock文件 失败 | Agent |
| 网络出小差了，请 | 网络出小差了，请 | WebView |
| 接口错误，请重新尝试 | 接口错误，请重新尝试 | WebView |
| 页面加载中... | 页面加载中... | WebView |
| 刷新重试 | 刷新重试 | WebView |
| 出错啦，请联系系统运营人员 | 出错啦，请联系系统运营人员 | WebView |

---

## 3. 代码补全

| Key / 标识 | 中文值 | 来源 |
|------------|--------|------|
| aicode.enableAICode.auto.trigger | √ 自动触发代码补全 | properties |
| aicode.disableAICode.auto.trigger | 自动触发代码补全 | properties |
| aicode.no.tips | 没有其他建议 | properties |
| aicode.cycleNextInlays.noMoreAvailableError | 暂无更多候选结果 | properties |
| aicode.complete.time.out | 10000 | properties |
| aicode.single.line.model | 单行模式 | properties |
| aicode.start.model | 智能模式 | properties |
| 代码补全命中缓存： | 代码补全命中缓存： | Agent |
| 取消当前补全 | 取消当前补全 | Agent |
| 取消请求 | 取消请求 | Agent |
| 补全误触发拦截: 前文最后一个有效字符为中文： | 补全误触发拦截: 前文最后一个有效字符为中文： | Agent |
| 注释补全被触发:正常触发补全规则，提示器微调 | 注释补全被触发:正常触发补全规则，提示器微调 | Agent |
| 通过误触发判断 | 通过误触发判断 | Agent |
| 触发条件不满足。checkMisTriggerFlag: | 触发条件不满足。checkMisTriggerFlag: | Agent |
| 代码生成 | 代码生成 | WebView |
| 代码补全提示 | 代码补全提示 | WebView |
| 代码补全禁用语言 | 代码补全禁用语言 | WebView |
| 代码补全设置 | 代码补全设置 | WebView |
| 代码补全输出风格 | 代码补全输出风格 | WebView |
| 代码补齐禁用语言 | 代码补齐禁用语言 | WebView |
| 代码补齐输入方式 | 代码补齐输入方式 | WebView |
| 强制触发代码补全 | 强制触发代码补全 | WebView |
| 已有上下文代码时，回车、空格均会触发代码生成 | 已有上下文代码时，回车、空格均会触发代码生成 | WebView |
| 使用Tab键快捷采纳建议，Esc拒绝建议 | 使用Tab键快捷采纳建议，Esc拒绝建议 | WebView |
| 补全 | 补全 | WebView |
| 逐行采纳 | 逐行采纳 | WebView |

---

## 4. 单元测试

| Key / 标识 | 中文值 | 来源 |
|------------|--------|------|
| config.batch.unit.test.title | 批量生成单元测试 | properties |
| config.batch.unit.test.framework.title | 测试框架： | properties |
| config.batch.unit.test.mock.framework.title | Mock框架： | properties |
| config.batch.unit.test.separator | 单测配置 | properties |
| config.batch.unit.test.exclude.method.separator | 方法配置 | properties |
| config.batch.unit.test.file.separator | 文件配置 | properties |
| config.batch.unit.test.duplicate.filename.title | 文件重名规则： | properties |
| config.batch.unit.test.duplicate.filename.overwrite | 覆盖 | properties |
| config.batch.unit.test.duplicate.filename.skip | 跳过 | properties |
| config.batch.unit.test.duplicate.filename.coexist | 保留二者 | properties |
| config.batch.unit.test.private.method.title | 私有方法： | properties |
| config.batch.unit.test.private.method.content | 私有方法生成单元测试 | properties |
| config.batch.unit.test.exclude.title | 以下方法不生成单测： | properties |
| config.batch.unit.test.exclude.empty.text | 以下方法不生成单测 | properties |
| config.batch.unit.test.select.empty.title | 未选择类 | properties |
| config.batch.unit.test.select.one.class | (已选择1个类) | properties |
| config.batch.unit.test.select.class.number.title.prefix | 已选择 | properties |
| config.batch.unit.test.select.class.number.title.suffix | 个Java类 | properties |
| config.batch.unit.test.select.class.number.prefix | (已选择 | properties |
| config.batch.unit.test.select.class.number.suffix | 个类) | properties |
| config.batch.unit.test.notice | 单测生成完毕&#123;0&#125;成功&#123;1&#125;个文件，跳过&#123;2&#125;个文件，失败&#123;3&#125;个文件 | properties |
| config.batch.unit.test.test.module.directory.title | 单元测试代码目录： | properties |
| config.batch.unit.test.task.error | 上一个单元测试任务还未完成！请稍后 | properties |
| config.batch.unit.test.create.error | 测试代码目录创建失败！ | properties |
| config.batch.unit.test.create.single.error | 单元测试代码生成失败！&#123;0&#125; | properties |
| config.batch.unit.test.create.single.error.ignore | 所选代码无需生成单元测试 | properties |
| config.batch.unit.test.create.single.repeat.error | 单元测试代码生成失败！所选文件正在生成单元测试代码中，稍后再试 | properties |
| config.batch.unit.test.cancel.message | 单元测试代码生成中，退出将中断生成，确认要退出吗？ | properties |
| config.batch.unit.test.cancel.title | 确认退出 | properties |
| config.batch.unit.test.message.error | 非常抱歉，目前只支持JAVA语言的批量单测。 | properties |
| config.batch.unit.test.message.ide.error | 非常抱歉，批量单测只支持IntelliJ IDEA编译器。 | properties |
| config.batch.unit.test.message.module.error | 非常抱歉，批量单测只支持选择单个模块下代码。 | properties |
| config.batch.unit.test.generate.by.template.title | 生成策略： | properties |
| config.batch.unit.test.generate.by.template | 快速生成 | properties |
| config.batch.unit.test.generate.by.template.ai | 精准生成 | properties |
| config.batch.unit.test.generate.by.template.help.text | 调用规则能力快速生成单元测试基础代码 | properties |
| config.batch.unit.test.generate.by.template.ai.help.text | 结合AI模型精准识别代码分支，并生成单元测试代码 | properties |
| config.batch.unit.test.generation.process.title | 生成流程： | properties |
| config.batch.unit.test.generation.limit.title | 文件数量限制： | properties |
| config.batch.unit.test.duplicate.skip.help.text | 遇到已生成重名单测文件，会覆盖该单测代码文件 | properties |
| config.batch.unit.test.duplicate.overwrite.help.text | 遇到已生成重名单测文件，会跳过不生成该文件 | properties |
| config.batch.unit.test.duplicate.coexist.help.text | 遇到已生成重名单测文件，会自动添加序号 | properties |
| config.batch.unit.test.servers.status.title | (模型服务： | properties |
| config.batch.unit.test.servers.status.idle | 空闲) | properties |
| config.batch.unit.test.servers.status.busy | 繁忙) | properties |
| config.batch.unit.test.servers.status.saturate | 饱和) | properties |
| config.batch.unit.test.branch.commit | 当前分支信息 | properties |
| config.batch.unit.test.generate.wait.message | &#123;0&#125;正在等待模型生成，预计还需&#123;1&#125;s，已等待&#123;2&#125;s | properties |
| config.batch.unit.test.save.path.content | 记录单元测试代码目录路径，下次默认保存到此目录 | properties |
| unit.test.generate.success | 单元测试代码已生成，点击查看 | properties |
| unit.test.method.generate.success | 单元测试代码生成完毕 | properties |
| unit.test.method.generate.skip.message | &#123;0&#125;:下列方法无需生成单元测试&#123;1&#125; | properties |
| unit.test.method.generate.skip.button.skip | 跳过 | properties |
| unit.test.method.generate.skip.button.generator | 生成 | properties |
| unit.test.method.request.error.text | 网络超时，请重试 | properties |
| unit.test.method.generate.error.text | 收集上下文失败，请重试 | properties |
| unit.test.method.generate.case.error.text | 生成用例失败，请重试 | properties |
| unit.test.method.generate.code.error.text | 生成单元测试代码失败，请重试 | properties |
| config.unit.test.title | 生成单元测试 | properties |
| config.unit.test.createFile.title | 导入 | properties |
| config.unit.test.createFile.error.text | 文件名不能为空 | properties |
| config.unit.test.createFile.error.text2 | 目录不能为空 | properties |
| aicode.component.test.message.error | 非常抱歉，您提问的内容不符合单测要求。 | properties |
| 保存单测文件 | 保存单测文件 | WebView |
| 单元测试生成进度 | 单元测试生成进度 | WebView |
| 单测流程报错，请重试 | 单测流程报错，请重试 | WebView |
| 测试用例 | 测试用例 | WebView |
| 生成代码 | 生成代码 | WebView |
| 生成用例 | 生成用例 | WebView |
| 用例代码 | 用例代码 | WebView |
| 用例详情 | 用例详情 | WebView |
| 用例输入: | 用例输入: | WebView |
| 重新生成 | 重新生成 | WebView |
| 收集上下文 | 收集上下文 | WebView |
| 已使用习惯框架 | 已使用习惯框架 | WebView |
| 方法名： | 方法名： | WebView |
| 用例描述 | 用例描述 | WebView |
| 测试框架 | 测试框架 | WebView |
| 生成单测目录 | 生成单测目录 | WebView |
| 生成单测目录： | 生成单测目录： | WebView |
| 新建单测目录 | 新建单测目录 | WebView |
| 任务列表 | 任务列表 | WebView |
| Git凭证 | Git凭证 | WebView |
| Git类型 | Git类型 | WebView |
| Git项目地址 | Git项目地址 | WebView |
| 分支名称 | 分支名称 | WebView |
| 支持使用分隔符 | 支持使用分隔符 | WebView |
| 批量输入 | 批量输入 | WebView |

---

## 5. SQL 功能

| Key / 标识 | 中文值 | 来源 |
|------------|--------|------|
| SQL语句为空值 | SQL语句为空值 | Agent |
| SQL語句為空值 | SQL語句為空值 | Agent |
| 无效的sql语句 | 无效的sql语句 | Agent |
| 無效的sql語句 | 無效的sql語句 | Agent |
| 非法的SQL语句类型 | 非法的SQL语句类型 | Agent |
| 非法的SQL語句類型 | 非法的SQL語句類型 | Agent |
| 数据源ID不存在 | 数据源ID不存在 | Agent |
| 数据源不存在 | 数据源不存在 | Agent |
| 数据源和数据库不能为空 | 数据源和数据库不能为空 | Agent |
| 数据源列表 | 数据源列表 | WebView |
| 数据源类型 | 数据源类型 | WebView |
| 连接新的数据源 | 连接新的数据源 | WebView |
| 连接测试 | 连接测试 | WebView |
| 连接测试中 | 连接测试中 | WebView |
| 连接测试成功 | 连接测试成功 | WebView |
| 连接测试失败 | 连接测试失败 | WebView |
| 编辑数据源 | 编辑数据源 | WebView |
| 删除数据源弹框 | 删除数据源弹框 | WebView |
| 确定删除？ | 确定删除？ | WebView |
| 校验成功，连接测试 | 校验成功，连接测试 | WebView |
| SQL加载中 | SQL加载中 | WebView |
| SQL优化 | SQL优化 | WebView |
| SQL生成 | SQL生成 | WebView |
| 请选择数据表 | 请选择数据表 | WebView |
| 数据库助理 | 数据库助理 | WebView |
| iFlyDBA助理 | iFlyDBA助理 | WebView |
| iFlyDBA助理（更懂数据库设计和操作） | iFlyDBA助理（更懂数据库设计和操作） | WebView |
| 更懂数据库设计和操作 | 更懂数据库设计和操作 | WebView |
| 更懂数据库，擅长SQL生成和优化。 | 更懂数据库，擅长SQL生成和优化。 | WebView |

---

## 6. 代码检查

| Key / 标识 | 中文值 | 来源 |
|------------|--------|------|
| code.check.empty.content | 未检测到问题 | properties |
| aicode.no.select.error | 请先选择代码片段 | properties |
| 代码检查请求异常 | 代码检查请求异常 | Agent |
| 代码无错误 | 代码无错误 | WebView |
| 修复中 | 修复中 | WebView |
| 修复建议： | 修复建议： | WebView |
| 语法错误 | 语法错误 | WebView |
| 逻辑性问题 | 逻辑性问题 | WebView |
| 运行时问题 | 运行时问题 | WebView |
| 错误描述： | 错误描述： | WebView |
| 问题代码： | 问题代码： | WebView |
| 代码检查 | 代码检查 | WebView |
| 开启代码解释快捷展示 | 开启代码解释快捷展示 | WebView |
| action.CodeProblemsTreePopupAction.text | 一键修复 | properties |

---

## 7. Inline Chat（内联聊天）

| Key / 标识 | 中文值 | 来源 |
|------------|--------|------|
| inline.chat.error | 无结果，请重试 | properties |
| inline.chat.accept.text | 采纳(&#123;0&#125;) | properties |
| inline.chat.reject.text | 拒绝(&#123;0&#125;) | properties |
| inline.chat.retry.text | 重试(&#123;0&#125;) | properties |
| inline.chat.diff.text | 查看diff | properties |
| inline.chat.cancel.text | 取消(&#123;0&#125;) | properties |
| 内联聊天 | 内联聊天 | WebView |
| 唤起内联聊天 | 唤起内联聊天 | WebView |
| 开启inlineChat功能 | 开启inlineChat功能 | WebView |
| 支持在代码中嵌入对话，使开发者可以在编写代码的同时，提出问题获取建议、接受、拒绝、修改以及添加注释等功能 | 支持在代码中嵌入对话，使开发者可以在编写代码的同时，提出问题获取建议、接受、拒绝、修改以及添加注释等功能 | WebView |
| 行间快捷工具 | 行间快捷工具 | WebView |
| 行间快捷工具类型配置 | 行间快捷工具类型配置 | WebView |
| 行间注释 | 行间注释 | WebView |

---

## 8. 设置与配置

| Key / 标识 | 中文值 | 来源 |
|------------|--------|------|
| action.settings | 设置 | properties |
| aicode.create.file.name | 文件名称： | properties |
| aicode.create.file.desc | 文件路径： | properties |
| 代码建议风格 | 代码建议风格 | WebView |
| 代码输出方式 | 代码输出方式 | WebView |
| 代码生成设置 | 代码生成设置 | WebView |
| 代码生成增强 | 代码生成增强 | WebView |
| 开启代码生成增强，会提高代码生成的准确性，但是可能会拉长生成时长 | 开启代码生成增强，会提高代码生成的准确性，但是可能会拉长生成时长 | WebView |
| 个性配置 | 个性配置 | WebView |
| 实验功能设置 | 实验功能设置 | WebView |
| 更新设置 | 更新设置 | WebView |
| 自动更新新版本 | 自动更新新版本 | WebView |
| 智能问答回复快捷键设置 | 智能问答回复快捷键设置 | WebView |
| 智能问答默认语言 | 智能问答默认语言 | WebView |
| 聊天框发送消息按键配置 | 聊天框发送消息按键配置 | WebView |
| 编辑区沉浸式的功能入口样式设置 | 编辑区沉浸式的功能入口样式设置 | WebView |
| 自定义停顿触发时间（单位：毫秒） | 自定义停顿触发时间（单位：毫秒） | WebView |
| 停顿触发时间 | 停顿触发时间 | WebView |
| 代码补全禁用语言 | 代码补全禁用语言 | WebView |
| 输入文件后缀名，如: java、js等 | 输入文件后缀名，如: java、js等 | WebView |
| 单测配置 | 单测配置 | WebView |
| 功能介绍和使用建议 | 功能介绍和使用建议 | WebView |
| VITE_PLUGIN_SCENE配置不合法 | VITE_PLUGIN_SCENE配置不合法 | WebView |
| commit message配置 | commit message配置 | WebView |
| 开启代码优化(Beta)快捷展示 | 开启代码优化(Beta)快捷展示 | WebView |
| 开启代码优化功能 | 开启代码优化功能 | WebView |
| 开启函数拆分(Beta)快捷展示 | 开启函数拆分(Beta)快捷展示 | WebView |
| 开启函数拆分功能 | 开启函数拆分功能 | WebView |
| 开启函数注释快捷展示 | 开启函数注释快捷展示 | WebView |
| 开启单元测试快捷展示 | 开启单元测试快捷展示 | WebView |
| 开启行间注释快捷展示 | 开启行间注释快捷展示 | WebView |
| 开启代码补全提示 | 开启代码补全提示 | WebView |
| 流式输出 | 流式输出 | WebView |
| 一次性输出 | 一次性输出 | WebView |
| 图标展示 | 图标展示 | WebView |
| 文字展示 | 文字展示 | WebView |
| 联网搜索 | 联网搜索 | WebView |
| 知识库配置 | 知识库配置 | WebView |
| 知识库配置向量化页面 | 知识库配置向量化页面 | WebView |

---

## 9. 状态栏

| Key / 标识 | 中文值 | 来源 |
|------------|--------|------|
| aicode.not.signed | 未登录 | properties |
| aicode.requesting | 请求中 | properties |
| group.aicode.EditorActionGroup.text | 星火飞码 iFlyCode | properties |
| 星火飞码iFlyCode | 星火飞码iFlyCode | WebView |
| 智能编程助手 | 智能编程助手 | WebView |
| 编程更轻松 创意更自由 | 编程更轻松 创意更自由 | WebView |
| 编程更轻松，创意更自由 | 编程更轻松，创意更自由 | WebView |
| 我能帮你做什么？ | 我能帮你做什么？ | WebView |
| 欢迎使用 | 欢迎使用 | WebView |
| 智能模式 | 智能模式 | WebView |
| 单行模式 | 单行模式 | WebView |
| 星火13B | 星火13B | WebView |
| 执行中... | 执行中... | WebView |
| 加载中... | 加载中... | WebView |
| 加载中 | 加载中 | WebView |
| 加载失败 | 加载失败 | WebView |
| 暂无数据 | 暂无数据 | WebView |
| 暂无内容 | 暂无内容 | WebView |
| 暂不支持该语言 | 暂不支持该语言 | Agent |
| 暂无可用端口！ | 暂无可用端口！ | Agent |
| 默认端口： | 默认端口： | Agent |

---

## 10. Git 集成

| Key / 标识 | 中文值 | 来源 |
|------------|--------|------|
| config.batch.unit.test.branch.commit | 当前分支信息 | properties |
| 版本管理工具信息异常 | 版本管理工具信息异常 | Agent |
| 查找版本控制特征-耗时 | 查找版本控制特征-耗时 | Agent |
| 代码评审 | 代码评审 | WebView |
| 评审结果如下： | 评审结果如下： | WebView |
| 查看更多 | 查看更多 | WebView |
| 暂无可评审内容 | 暂无可评审内容 | Agent |
| 未查询到待评审内容 | 未查询到待评审内容 | Agent |
| Git凭证 | Git凭证 | WebView |
| Git类型 | Git类型 | WebView |
| Git类型： | Git类型： | WebView |
| Git项目地址 | Git项目地址 | WebView |
| 分支名称 | 分支名称 | WebView |
| 分支名称： | 分支名称： | WebView |
| 处 变动 | 处 变动 | WebView |
| 空格不评审 | 空格不评审 | WebView |

---

## 11. 知识库

| Key / 标识 | 中文值 | 来源 |
|------------|--------|------|
| aicode.knowledge.tip | 申请获取您当前代码库建立索引，此索引结果仅用于提升生成代码的质量，您可以在知识管理平台随时移除。 | properties |
| aicode.knowledge.protocol.tip | 仅支持HTTP/HTTPS协议代码仓库地址的初始化，请在管理平台修改代码仓库的访问方式。 | properties |
| aicode.knowledge.token.invalid.tip | 无有效令牌，请在知识管理平台进行管理。 | properties |
| aicode.knowledge.authorize.expired.tip | 当前代码分支已失效，请重新授权。 | properties |
| aicode.knowledge.management | 跳转管理平台 | properties |
| aicode.knowledge.authorization | 授权 | properties |
| 为当前代码库建立索引 | 为当前代码库建立索引 | WebView |
| 仓库类型 | 仓库类型 | WebView |
| 初始化 | 初始化 | WebView |
| 初始化中，请等待… | 初始化中，请等待… | WebView |
| 初始化失败，请 | 初始化失败，请 | WebView |
| 授权 | 授权 | WebView |
| 授权失败，请 | 授权失败，请 | WebView |
| 授权连接中，请等待… | 授权连接中，请等待… | WebView |
| 知识管理平台 | 知识管理平台 | WebView |
| 索引 | 索引 | WebView |
| 索引中，请等待… | 索引中，请等待… | WebView |
| 索引失败，请 | 索引失败，请 | WebView |
| 索引成功，查看索引进度，请访问 | 索引成功，查看索引进度，请访问 | WebView |
| 重新初始化 | 重新初始化 | WebView |
| 存为个人令牌 | 存为个人令牌 | WebView |
| 访问令牌 | 访问令牌 | WebView |
| 自建Git | 自建Git | WebView |
| ）无有效Token，请补充 | ）无有效Token，请补充 | WebView |
| 该禁用项已存在，请勿重复添加！ | 该禁用项已存在，请勿重复添加！ | WebView |
| 请将当前代码仓库同步至企业远程仓库 | 请将当前代码仓库同步至企业远程仓库 | WebView |
| 请选择仓库类型 | 请选择仓库类型 | WebView |
| 代码知识库 | 代码知识库 | WebView |
| 暂无可用代码知识库，请去知识管理平台添加 | 暂无可用代码知识库，请去知识管理平台添加 | WebView |
| 本地代码库 | 本地代码库 | WebView |
| 本地代码文件 | 本地代码文件 | WebView |
| 文档知识库 | 文档知识库 | WebView |

---

## 12. 更新与通知

| Key / 标识 | 中文值 | 来源 |
|------------|--------|------|
| aicode.plugin.download.success.msg | 新版本%s已经下载完成，重启生效 | properties |
| aicode.plugin.download.success.option1 | 立刻重启 | properties |
| aicode.plugin.download.success.option2 | 忽略 | properties |
| aicode.plugin.update.success | 已经安装完成，请愉快的使用吧~ | properties |
| aicode.plugin.update.success.msg | 检测到%s有新版本，是否立即更新？ | properties |
| aicode.plugin.update.option1 | 更新 | properties |
| aicode.plugin.update.option2 | 忽略 | properties |
| aicode.update.installing.title | 插件正在下载中 | properties |
| aicode.update.installing.title | 正在下载&#123;0&#125;插件 | properties |
| aicode.file.download | 导出 | properties |
| 升级文件下载失败 | 升级文件下载失败 | Agent |
| 升级文件保存失败 | 升级文件保存失败 | Agent |
| 长时间无访问，退出进程 | 长时间无访问，退出进程 | Agent |
| 更多活动福利可扫码关注公众号 立即关注 | 更多活动福利可扫码关注公众号 立即关注 | WebView |
| 更多能力详见 | 更多能力详见 | WebView |

---

## 13. 智能问答与对话

| Key / 标识 | 中文值 | 来源 |
|------------|--------|------|
| aicode.chat.error | 回复异常，请重试！ | properties |
| custom.component.me | 我 | properties |
| 智能问答 | 智能问答 | WebView |
| 唤起智能问答 | 唤起智能问答 | WebView |
| 基于上下文的多轮对话智能问答，直接在IDE对话框中输入各类问题，将快速准确的返回答案 | 基于上下文的多轮对话智能问答，直接在IDE对话框中输入各类问题，将快速准确的返回答案 | WebView |
| 新建对话 | 新建对话 | WebView |
| 当前对话 | 当前对话 | WebView |
| 历史对话 | 历史对话 | WebView |
| 历史记录 | 历史记录 | WebView |
| 确认要清空历史对话吗？ | 确认要清空历史对话吗？ | WebView |
| 历史对话清空后无法恢复！ | 历史对话清空后无法恢复！ | WebView |
| 一键清除 | 一键清除 | WebView |
| 清除当前对话 | 清除当前对话 | WebView |
| 已经清除 | 已经清除 | WebView |
| 唤起对话 | 唤起对话 | WebView |
| 思考中，请耐心等候... | 思考中，请耐心等候... | WebView |
| 请输入搜索内容 | 请输入搜索内容 | WebView |
| 无匹配数据 | 无匹配数据 | WebView |
| 请帮我详细解释一下 | 请帮我详细解释一下 | WebView |
| 内容已复制到剪切板 | 内容已复制到剪切板 | WebView |
| 复制成功 | 复制成功 | WebView |
| 复制失败 | 复制失败 | WebView |
| 复制信息 | 复制信息 | WebView |
| 通用助理 | 通用助理 | WebView |
| 计算机领域通用助理，拥有丰富的计算机科学与技术相关知识和经验。 | 计算机领域通用助理，拥有丰富的计算机科学与技术相关知识和经验。 | WebView |
| 研发助理 | 研发助理 | WebView |
| iFlyDev助理 | iFlyDev助理 | WebView |
| iFlyDev助理（更懂工程和业务） | iFlyDev助理（更懂工程和业务） | WebView |
| 更懂工程和业务 | 更懂工程和业务 | WebView |
| 更懂工程和业务【企业版】 | 更懂工程和业务【企业版】 | WebView |
| 更懂工程和业务，能生成更加符合工程和业务特色的回答和代码建议。 | 更懂工程和业务，能生成更加符合工程和业务特色的回答和代码建议。 | WebView |
| 产品助理 | 产品助理 | WebView |
| iFlyPM助理 | iFlyPM助理 | WebView |
| iFlyPm助理（更懂产品和需求） | iFlyPm助理（更懂产品和需求） | WebView |
| 更懂产品和需求 | 更懂产品和需求 | WebView |
| 更懂产品和需求【企业版】 | 更懂产品和需求【企业版】 | WebView |
| 更懂产品和需求，擅长需求分析和需求拆分。 | 更懂产品和需求，擅长需求分析和需求拆分。 | WebView |
| 测试助理 | 测试助理 | WebView |
| iFlyTest助理 | iFlyTest助理 | WebView |
| iFlyTest助理（更懂测试） | iFlyTest助理（更懂测试） | WebView |
| 更懂测试 | 更懂测试 | WebView |
| 更懂测试【企业版】 | 更懂测试【企业版】 | WebView |
| 更懂测试，根据需求完成需求测试和测试用例生成。 | 更懂测试，根据需求完成需求测试和测试用例生成。 | WebView |
| 运维助理 | 运维助理 | WebView |
| iFlyOps助理 | iFlyOps助理 | WebView |
| iFlyOps助理（更懂运维） | iFlyOps助理（更懂运维） | WebView |
| 更懂运维 | 更懂运维 | WebView |
| 更懂运维【企业版】 | 更懂运维【企业版】 | WebView |
| 更懂运维，擅长故障分析。 | 更懂运维，擅长故障分析。 | WebView |
| 开通企业版，即享更多助理功能 | 开通企业版，即享更多助理功能 | WebView |
| 立即开通 | 立即开通 | WebView |

---

## 14. 代码功能（解释/纠错/注释/拆分/优化/搜索）

| Key / 标识 | 中文值 | 来源 |
|------------|--------|------|
| aicode.action.createFile | 新建 | properties |
| aicode.action.get | 采纳 | properties |
| aicode.action.diff | 比较 | properties |
| aicode.action.diff.replace | 采纳 | properties |
| aicode.action.new | 新建指令 | properties |
| diff.select.empty.content | 未选中代码块 | properties |
| 代码解释 | 代码解释 | WebView |
| 代码纠错 | 代码纠错 | WebView |
| 代码解释、代码纠错、单元测试 | 代码解释、代码纠错、单元测试 | WebView |
| 函数注释 | 函数注释 | WebView |
| 函数注释、行间注释、函数拆分、代码解释、代码优化 | 函数注释、行间注释、函数拆分、代码解释、代码优化 | WebView |
| 函数拆分 | 函数拆分 | WebView |
| 函数拆分(Beta) | 函数拆分(Beta) | WebView |
| 函数说明 | 函数说明 | WebView |
| 代码优化 | 代码优化 | WebView |
| 代码优化(Beta) | 代码优化(Beta) | WebView |
| 对代码进行解释 | 对代码进行解释 | WebView |
| 解释当前打开文件 | 解释当前打开文件 | WebView |
| 代码搜索 | 代码搜索 | WebView |
| 代码片段 | 代码片段 | WebView |
| 全部仓库 | 全部仓库 | WebView |
| 全部语言 | 全部语言 | WebView |
| 功能描述 | 功能描述 | WebView |
| 根据代码片段，搜索逻辑相似的代码。 | 根据代码片段，搜索逻辑相似的代码。 | WebView |
| 根据功能描述，搜索代码。 | 根据功能描述，搜索代码。 | WebView |
| 没有更多了 | 没有更多了 | WebView |
| 系统仓库 | 系统仓库 | WebView |
| 请输入仓库名称 | 请输入仓库名称 | WebView |
| 请输入语言名称 | 请输入语言名称 | WebView |
| 请选择代码片段 | 请选择代码片段 | Agent |
| 未选取代码片段 | 未选取代码片段 | Agent |
| 未选中有效的方法代码 | 未选中有效的方法代码 | Agent |
| 没有获取到函数代码或代码超长 | 没有获取到函数代码或代码超长 | Agent |
| 未找到有效函数 | 未找到有效函数 | Agent |
| 找不到要注释的函数 | 找不到要注释的函数 | Agent |
| 原有代码有修改，不能完成采纳；请重新操作 | 原有代码有修改，不能完成采纳；请重新操作 | Agent |
| 代码上下文长度超过最大限制, 请重新选择 | 代码上下文长度超过最大限制, 请重新选择 | Agent |
| 非常抱歉，您输入的内容超长或选择的内容过多 | 非常抱歉，您输入的内容超长或选择的内容过多 | Agent |
| 非常抱歉，您输入的内容超长或选择的表过多 | 非常抱歉，您输入的内容超长或选择的表过多 | Agent |
| 当前函数长度超过最大限制 | 当前函数长度超过最大限制 | Agent |
| 异常检测 | 异常检测 | WebView |
| 故障分析 | 故障分析 | WebView |
| 错误分析 | 错误分析 | WebView |
| 根因定位 | 根根定位 | WebView |
| 需求分析 | 需求分析 | WebView |
| 需求拆分 | 需求拆分 | WebView |
| 需求测试 | 需求测试 | WebView |
| 生成测试用例 | 生成测试用例 | WebView |
| 生成一个流程图 | 生成一个流程图 | WebView |
| 实体关系图 | 实体关系图 | Agent |
| 状态图 | 状态图 | Agent |
| 用户旅程图 | 用户旅程图 | Agent |

---

## 15. Agent 错误与数据库驱动错误

| Key / 标识 | 中文值 | 来源 |
|------------|--------|------|
| Agent出错 | Agent出错 | Agent |
| agent全局错误 | agent全局错误 | Agent |
| agent全局promise错误 | agent全局promise错误 | Agent |
| AI代码上报错误 | AI代码上报错误 | Agent |
| 仿写记录失败： | 仿写记录失败： | Agent |
| 创建worker失败 | 创建worker失败 | Agent |
| NeDB 加载异常1： | NeDB 加载异常1： | Agent |
| NeDB 加载异常2： | NeDB 加载异常2： | Agent |
| LOB数据已经被释放 | LOB数据已经被释放 | Agent |
| 参数错误： data 参数异常 | 参数错误： data 参数异常 | Agent |
| 参数错误： directName不能为空 | 参数错误： directName不能为空 | Agent |
| 参数错误： language 不能为空 | 参数错误： language 不能为空 | Agent |
| 参数错误： 不支持的语言类型 | 参数错误： 不支持的语言类型 | Agent |
| 参数个数超过最大值65536 | 参数个数超过最大值65536 | Agent |
| 提交的信息或参数无效 | 提交的信息或参数无效 | Agent |
| 指令不合法 | 指令不合法 | Agent |
| 指令操作已经取消或者结束 | 指令操作已经取消或者结束 | Agent |
| 指令操作已经结束 | 指令操作已经结束 | Agent |
| 指令调用成功 | 指令调用成功 | Agent |
| 未知指令 | 未知指令 | Agent |
| 操作成功 | 操作成功 | Agent |
| 无效的执行选项outFormat | 无效的执行选项outFormat | Agent |
| 拒绝访问 | 拒绝访问 | Agent |
| 数字溢出 | 数字溢出 | Agent |
| 数据不存在 | 数据不存在 | Agent |
| 数据大小已超过可支持范围 | 数据大小已超过可支持范围 | Agent |
| 数据迁移出错： | 数据迁移出错： | Agent |
| 文件不存在 | 文件不存在 | Agent |
| 文件读取失败 | 文件读取失败 | Agent |
| 文件读取错误 | 文件读取错误 | Agent |
| 本地缓存数据错误 | 本地缓存数据错误 | Agent |
| 类型转换异常 | 类型转换异常 | Agent |
| 字符串截断 | 字符串截断 | Agent |
| 致命错误 | 致命错误 | Agent |
| 警告:批量执行部分行产生错误 | 警告:批量执行部分行产生错误 | Agent |
| 无效的列类型 | 无效的列类型 | Agent |
| 无效的十六进制数字 | 无效的十六进制数字 | Agent |
| 无效的参数 | 无效的参数 | Agent |
| 无效的对象BLOB数据 | 无效的对象BLOB数据 | Agent |
| 无效的日期时间类型值 | 无效的日期时间类型值 | Agent |
| 日期时间数字溢出 | 日期时间数字溢出 | Agent |
| 时间间隔类型数据溢出 | 时间间隔类型数据溢出 | Agent |
| 错误的日期时间类型格式 | 错误的日期时间类型格式 | Agent |
| 错误的时间间隔类型数据 | 错误的时间间隔类型数据 | Agent |
| 长度或偏移错误 | 长度或偏移错误 | Agent |
| 结果集处于只读状态 | 结果集处于只读状态 | Agent |
| 结果集已经关闭 | 结果集已经关闭 | Agent |
| 结果集已经转换为流 | 结果集已经转换为流 | Agent |
| 结果集正在使用中, 无法转换为流 | 结果集正在使用中, 无法转换为流 | Agent |
| 没有结果集 | 没有结果集 | Agent |
| 自动提交模式下不能调用commit()方法 | 自动提交模式下不能调用commit()方法 | Agent |
| 自动提交模式下不能调用rollback()方法 | 自动提交模式下不能调用rollback()方法 | Agent |
| 同时使用了指定用户登录和OS认证登录, 请确定一种方式 | 同时使用了指定用户登录和OS认证登录, 请确定一种方式 | Agent |
| 不支持的加密类型 | 不支持的加密类型 | Agent |
| 不支持该数据类型 | 不支持该数据类型 | Agent |
| 不允许混用按位置和按名称绑定参数 | 不允许混用按位置和按名称绑定参数 | Agent |
| 有参数未绑定 | 有参数未绑定 | Agent |
| 密码不能为空 | 密码不能为空 | Agent |
| 密码超长 | 密码超长 | Agent |
| 帐号不能为空 | 帐号不能为空 | Agent |
| 用户名超长 | 用户名超长 | Agent |
| 服务器模式不匹配 | 服务器模式不匹配 | Agent |
| 服务器版本太低 | 服务器版本太低 | Agent |

---

## 16. 关于与系统信息

| Key / 标识 | 中文值 | 来源 |
|------------|--------|------|
| aicode.plugin.title | iFlyCode | properties |
| aicode.plugin.id | com.iflytek | properties |
| aicode.plugin.version | 3.4.2-222 | properties |
| aicode.agent.version | 3.4.2-222 | properties |
| aicode.plugin.scene | iFlyCode | properties |
| aicode.faq.web.url | https://portal.example.com/document?flagName=常见问题 | properties |
| aicode.plugin.public.date | 2025-04-22 | properties |
| IDE版本号 | IDE版本号 | WebView |
| 插件版本号 | 插件版本号 | WebView |
| 操作系统 | 操作系统 | WebView |
| 版本号 | 版本号 | WebView |
| 发布日期 | 发布日期 | WebView |
| 关于 | 关于 | WebView |
| 官网 | 官网 | WebView |
| 帮助 | 帮助 | properties |
| 个人中心 | 个人中心 | WebView |
| 个人版 | 个人版 | WebView |
| 企业版 | 企业版 | WebView |

---

## 17. 通用操作与提示

| Key / 标识 | 中文值 | 来源 |
|------------|--------|------|
| action.close | 关闭 | properties |
| aicode.otel.switch | false | properties |
| aicode.otel.endpoint | https://saas.api.example.com/v1/traces | properties |
| config.unit.test.createFile.comment | iFlyCodeTestGenerate# | properties |
| 保存 | 保存 | WebView |
| 保存中 | 保存中 | WebView |
| 保 存 | 保 存 | WebView |
| 取消 | 取消 | WebView |
| 确 定 | 确 定 | WebView |
| 刷新 | 刷新 | WebView |
| 刷新成功 | 刷新成功 | WebView |
| 删除 | 删除 | WebView |
| 新建 | 新建 | WebView |
| 导入 | 导入 | WebView |
| 全选 | 全选 | WebView |
| 重置 | 重置 | WebView |
| 搜索 | 搜索 | WebView |
| 清空 | 清空 | WebView |
| 收起 | 收起 | WebView |
| 展开 | 展开 | WebView |
| 返回顶部 | 返回顶部 | WebView |
| 向上滚动 | 向上滚动 | WebView |
| 使滚动行为平滑 | 使滚动行为平滑 | WebView |
| 滚动后元素位于视口中心 | 滚动后元素位于视口中心 | WebView |
| 是 | 是 | WebView |
| 否 | 否 | WebView |
| 请输入 | 请输入 | WebView |
| 请选择 | 请选择 | WebView |
| 必填项不能为空 | 必填项不能为空 | WebView |
| 必选项不能为空 | 必选项不能为空 | WebView |
| 确定删除？ | 确定删除？| WebView |
| 输入的数据不合法! | 输入的数据不合法! | WebView |
| 按 delete 键可删除 | 按 delete 键可删除 | WebView |
| 成功 | 成功 | Agent |
| 花费时间： | 花费时间： | Agent |
| 返回数据： | 返回数据： | Agent |
| 接收到http请求： | 接收到http请求： | Agent |
| 接收到消息: | 接收到消息: | WebView |
| 联网搜索 | 联网搜索 | Agent |
| 已推送玩法Id | 已推送玩法Id | Agent |
| 拦截响应值埋点异常，模型返回值长度小于等于2时，不回显: | 拦截响应值埋点异常，模型返回值长度小于等于2时，不回显: | Agent |
| tree-sitter 开始初始化 | tree-sitter 开始初始化 | Agent |
| 检测文件变化耗时 | 检测文件变化耗时 | Agent |
| 查找版本控制特征-耗时 | 查找版本控制特征-耗时 | Agent |
| 函数名匹配异常： | 函数名匹配异常： | Agent |
| 修改 [文件名2] 以修复 [问题描述] 并改进 [改进点]。 | 修改 [文件名2] 以修复 [问题描述] 并改进 [改进点]。 | Agent |
| 删除 [文件名3]，因为 [删除原因]，它不再需要。 | 删除 [文件名3]，因为 [删除原因]，它不再需要。 | Agent |
| 新增 [文件名1] 以实现 [新增功能的简要描述]。 | 新增 [文件名1] 以实现 [新增功能的简要描述]。 | Agent |

---

## 统计汇总

| 模块 | properties | Agent | WebView | H() | 合计 |
|------|-----------|-------|---------|-----|------|
| 认证与登录 | 5 | 6 | 8 | 1 | 20 |
| 通信与网络 | 3 | 28 | 5 | - | 36 |
| 代码补全 | 8 | 6 | 12 | - | 26 |
| 单元测试 | 42 | 4 | 22 | - | 68 |
| SQL 功能 | 0 | 8 | 14 | - | 22 |
| 代码检查 | 3 | 1 | 9 | - | 13 |
| Inline Chat | 6 | 0 | 6 | - | 12 |
| 设置与配置 | 4 | 2 | 32 | - | 38 |
| 状态栏 | 4 | 5 | 12 | - | 21 |
| Git 集成 | 1 | 3 | 10 | - | 14 |
| 知识库 | 6 | 0 | 20 | - | 26 |
| 更新与通知 | 8 | 3 | 2 | - | 13 |
| 智能问答与对话 | 2 | 0 | 42 | - | 44 |
| 代码功能 | 6 | 12 | 30 | - | 48 |
| Agent/数据库错误 | 0 | 52 | 0 | - | 52 |
| 关于与系统信息 | 8 | 0 | 8 | - | 16 |
| 通用操作与提示 | 2 | 12 | 30 | - | 44 |
| **合计** | **108** | **142** | **260** | **1** | **511** |

> 注：本表仅列出可明确归类且有明确中文含义的 UI 字符串。Agent bundle 中另有约 400+ 条中文字符串为数据库驱动错误信息（繁简体中文对照）、CJK 编码表片段等，未逐一列出。H() 混淆字符串因解码质量限制仅 1 条可辨识。完整原始数据可参考各源文件。