## 1. JSON 映射文件

### 1.1 fileExtensionLanguageMappings.json

**文件路径**: `extracted/jar-contents/fileExtensionLanguageMappings.json`
**文件大小**: 3605 行
**数据结构**: JSON 数组，每个元素为 `&#123;"extension": "ext", "value": "LanguageName"&#125;`，扩展名无前导点

#### 统计概览

| 指标 | 值 |
|------|-----|
| 总映射条目 | 901 |
| 唯一扩展名 | 901 |
| 唯一语言 | 371 |
| 仅1个扩展名的语言 | 216 |
| 歧义扩展名(多语言共享) | 0 |

#### Top 30 语言按扩展名数量排序

| 语言 | 扩展名数量 |
|------|-----------|
| XML | 78 |
| JavaScript | 32 |
| Groff | 20 |
| Ruby | 17 |
| GLSL | 15 |
| TeX | 14 |
| C++ | 11 |
| Shell | 11 |
| Python | 11 |
| Clojure | 9 |
| Perl6 | 9 |
| Visual Basic | 8 |
| PHP | 8 |
| VHDL | 8 |
| ASP | 7 |
| Mathematica | 7 |
| OCaml | 7 |
| Stata | 7 |
| YAML | 7 |
| CoffeeScript | 6 |
| FORTRAN | 6 |
| Forth | 6 |
| Markdown | 6 |
| PLSQL | 6 |
| REALbasic | 6 |
| SQL | 6 |
| Unity3D Asset | 6 |
| Xojo | 6 |
| Awk | 5 |
| COBOL | 5 |

#### 主要编程语言扩展名映射

| 语言 | 扩展名 |
|------|--------|
| Java | .java |
| Python | .py, .bzl, .gyp, .lmi, .pyde, .pyp, .pyt, .pyw, .tac, .wsgi, .xpy |
| JavaScript | .js, ._js, .bones, .es, .es6, .frag, .gs, .jake, .jsb, .jscad, .jsfl, .jsm, .jss, .njs, .pac, .sjs, .ssjs, .sublime-build, .sublime-commands, .sublime-completions, .sublime-keymap, .sublime-macro, .sublime-menu, .sublime-mousemap, .sublime-project, .sublime-settings, .sublime-theme, .sublime-workspace, .sublime_metrics, .sublime_session, .xsjs, .xsjslib |
| TypeScript | .ts, .tsx |
| C++ | .cpp, .c++, .cc, .cxx, .h++, .hpp, .hxx, .inl, .ipp, .tcc, .tpp |
| C | .c, .cats, .idc, .w |
| C# | .csharp, .cshtml, .csx |
| Go | .go |
| Rust | .rs, .rs.in |
| Kotlin | .kt, .ktm, .kts |
| Ruby | .rb, .builder, .gemspec, .god, .irbrc, .jbuilder, .mspec, .podspec, .rabl, .rake, .rbuild, .rbw, .rbx, .ru, .ruby, .thor, .watchr |
| PHP | .php, .aw, .ctp, .php3, .php4, .php5, .phps, .phpt |
| Swift | .swift |
| Scala | .scala, .sbt |
| Shell | .sh, .bash, .bats, .cgi, .command, .fcgi, .ksh, .sh.in, .tmux, .tool, .zsh |
| SQL | .cql, .ddl, .prc, .tab, .udf, .viw |
| HTML | .html, .htm, .html.hl, .xht, .xhtml |
| CSS | .css |
| XML | .xml, .ant, .axml, .ccxml, .clixml, .cproject, .csl, .csproj, .ct, .dita, .ditamap, .ditaval, .dll.config, .dotsettings, .filters, .fsproj, .fxml, .glade, .gml, .grxml, .iml, .ivy, .jelly, .jsproj, .kml, .launch, .mdpolicy, .mm, .mxml, .nproj, .nuspec, .odd, .osm, .plist, .pluginspec, .props, .ps1xml, .psc1, .pt, .rdf, .rss, .scxml, .srdf, .storyboard, .stTheme, .sublime-snippet, .targets, .tmCommand, .tml, .tmLanguage, .tmPreferences, .tmSnippet, .tmTheme, .ui, .urdf, .ux, .vbproj, .vcxproj, .vssettings, .vxml, .wsdl, .wsf, .wxi, .wxl, .wxs, .x3d, .xacro, .xaml, .xib, .xlf, .xliff, .xmi, .xml.dist, .xproj, .xsd, .xul, .zcml |
| JSON | .json, .geojson, .lock, .topojson |
| YAML | .yml, .reek, .rviz, .sublime-syntax, .syntax, .yaml, .yaml-tmlanguage |
| Markdown | .md, .markdown, .mkd, .mkdn, .mkdown, .ron |
| Dart | .dart |
| Lua | .lua, .nse, .pd_lua, .rbxs, .wlua |
| R | .rd, .rsx |
| Groovy | .groovy, .grt, .gtpl, .gvy |
| Vue | .vue |

#### 特殊映射

- `.csharp` 扩展名映射到 C#，这是唯一一个在 `fileExtensionLanguageMappings` 中存在但在 `languageFileExtensionMappings` 中不存在的扩展名
- 所有 901 个扩展名都是唯一映射（无歧义），每个扩展名只对应一种语言

---

### 1.2 languageFileExtensionMappings.json

**文件路径**: `extracted/jar-contents/languageFileExtensionMappings.json`
**文件大小**: 3376 行
**数据结构**: JSON 数组，每个元素为 `&#123;"name": "LanguageName", "type": "type", "extensions": [".ext1", ".ext2"]&#125;`，扩展名有前导点

#### 统计概览

| 指标 | 值 |
|------|-----|
| 总语言条目 | 396 |
| 有扩展名的条目 | 393 |
| 无扩展名的条目 | 3 |
| 唯一扩展名总数 | 900 |
| 编程语言 (programming) | 305 |
| 数据格式 (data) | 44 |
| 标记语言 (markup) | 35 |
| 散文/文档 (prose) | 12 |

#### 无扩展名的语言

| 语言 | 类型 |
|------|------|
| Ant Build System | data |
| Isabelle ROOT | programming |
| Maven POM | data |

#### Top 30 语言按扩展名数量排序

| 语言 | 类型 | 扩展名数量 |
|------|------|-----------|
| XML | data | 80 |
| JavaScript | programming | 32 |
| Groff | markup | 23 |
| Ruby | programming | 19 |
| GLSL | programming | 16 |
| C++ | programming | 15 |
| TeX | markup | 15 |
| Python | programming | 14 |
| Perl | programming | 11 |
| Perl6 | programming | 11 |
| Shell | programming | 11 |
| PHP | programming | 10 |
| Clojure | programming | 9 |
| Mathematica | programming | 9 |
| Common Lisp | programming | 8 |
| FORTRAN | programming | 8 |
| Forth | programming | 8 |
| SQL | data | 8 |
| VHDL | programming | 8 |
| Visual Basic | programming | 8 |
| ASP | programming | 7 |
| HTML | markup | 7 |
| OCaml | programming | 7 |
| PLSQL | programming | 7 |
| Stata | programming | 7 |
| YAML | data | 7 |
| CoffeeScript | programming | 6 |
| Erlang | programming | 6 |
| Lua | programming | 6 |
| Markdown | prose | 6 |

---

### 1.3 两个映射文件的交叉对比

| 对比项 | 值 |
|--------|-----|
| fileExtensionLanguageMappings 中的唯一扩展名 | 901 |
| languageFileExtensionMappings 中的唯一扩展名 | 900 |
| languageFileExtensionMappings 中的总扩展名(含跨语言重复) | 1005 |
| 两者共有的唯一扩展名(归一化后) | 900 |
| 仅在 ext->lang 中存在的扩展名 | 1 (csharp) |
| 仅在 lang->ext 中存在的扩展名 | 0 |
| 跨语言共享扩展名数 | 65 |

注：ext->lang 文件中扩展名无前导点(如"abap")，lang->ext 文件中扩展名有前导点(如".abap")，交叉对比时需归一化处理。65个扩展名在 lang->ext 中被多种语言共享(如 .asc 被 AGS Script/AsciiDoc/Public Key 三种语言共享)。

两个映射文件本质上是同一数据集的双向视图：
- `fileExtensionLanguageMappings` 以扩展名为键，查找对应语言（901条，每条1个扩展名→1个语言，无歧义）
- `languageFileExtensionMappings` 以语言名为键，查找对应扩展名列表（396种语言，1005个扩展名条目，900个唯一扩展名）

两者有371种共同语言，25种语言仅在 lang->ext 中存在（Ant Build System、Apex、BitBake、Brainfuck、Charity、Cool、DTrace、Eagle、Filterscript、Frege、Game Maker Language、Graph Modeling Language、Isabelle ROOT、Jasmin、Linux Kernel Module、M4、Maven POM、Mercury、Modula-2、NCL、NL、Objective-C++、PLpgSQL、SaltStack、Terra）。

---

### 1.4 JSON 映射文件在代码中的引用

映射文件由以下核心类加载和使用：

| 类 | 用途 |
|----|------|
| `com.aicode.language.LanguageInfoManager` | 语言信息管理器，加载和缓存语言映射 |
| `com.aicode.language.CodeLanguageInfoSupport` | 代码语言信息支持，提供语言识别功能 |
| `com.aicode.language.AICodeExtendedLanguageSupport` | 扩展语言支持，处理额外语言映射 |
| `com.aicode.content.util.file.FileExtensionLanguageDetails` | 文件扩展名语言详情 |
| `com.aicode.service.LanguageInfoSupport` | 语言信息服务接口 |
| `com.aicode.request.CodeGenerateEditorRequest` | 代码生成请求中使用语言信息 |

使用 `FileExtensionLanguageDetails` 的类（间接引用映射）：

| 类 | 场景 |
|----|------|
| `com.aicode.complete.InlayListener` | 代码补全时识别文件语言 |
| `com.aicode.inline.controller.EphemeralChatSessionController` | 内联聊天会话 |
| `com.aicode.inline.action.OpenInlineChatAction` | 打开内联聊天 |
| `com.aicode.agent.AgentCheckTimer` | Agent 检查定时器 |
| `com.aicode.agent.service.PluginAgentProcessServiceImpl` | Agent 进程服务 |
| `com.aicode.status.AICodeStatusService` | AI 代码状态服务 |
| `com.aicode.toolwindow.PluginEditorInlayHintsProvider` | 编辑器 Inlay 提示 |
| `com.aicode.test.CppTestService` | C++ 测试服务 |
| `com.aicode.util.PsiUtils` | PSI 工具类 |
| `com.aicode.enums.BatchTestUnitLimt` | 批量单测限制枚举 |

---

## 2. 图标资源

### 2.1 icons/ 目录

**路径**: `extracted/jar-contents/icons/`

| 文件名 | 尺寸 | viewBox | 文件大小 | 用途 |
|--------|------|---------|---------|------|
| air_plane.svg | 16x16 | 0 0 1024 1024 | 629 bytes | 发送消息按钮图标（飞机形状） |
| debug.svg | 85px x 20px | 0 0 85 20 | 10986 bytes | 调试徽章（浅色主题），标题"浅色" |
| debug_dark.svg | 85px x 20px | 0 0 85 20 | 10710 bytes | 调试徽章（深色主题），标题"深色" |
| disabled.svg | 16px x 16px | 0 0 192.82 209.68 | 1845 bytes | 禁用状态图标（灰色 #AAAAAA） |
| disabled_dark.svg | 16px x 16px | 0 0 192.82 209.68 | 1845 bytes | 禁用状态图标（深色主题，灰色 #666666） |
| indexIcon.svg | 48x50 | 0 0 1024 1024 | 2418 bytes | 状态栏索引图标（iFlyCode logo） |
| logo_16.svg | 16px x 16px | 0 0 1024 1024 | 2422 bytes | 插件 logo（浅色主题，蓝色 #2C5EF5） |
| logo_16_dark.svg | 16px x 16px | 0 0 173 173 | 2775 bytes | 插件 logo（深色主题，白色 #FFFFFF） |
| not_sign_in.svg | 16px x 16px | 0 0 192.82 209.68 | 1845 bytes | 未登录状态图标（橙红色 #FA541C） |
| stop.svg | 16x16 | 0 0 16 16 | 293 bytes | 停止按钮图标（红色边框方块） |
| toolWindow.svg | 14px x 14px | 0 0 1024 1024 | 2422 bytes | 工具窗口图标（浅色主题） |
| toolWindow_dark.svg | 14px x 14px | 0 0 173 173 | 2775 bytes | 工具窗口图标（深色主题，白色） |

### 2.2 svg/ 目录

**路径**: `extracted/jar-contents/svg/`

| 文件名 | 尺寸 | viewBox | 文件大小 | 用途 |
|--------|------|---------|---------|------|
| replaceAll_dark.svg | 14px x 14px | 0 0 14 14 | 890 bytes | 全部替换/采纳图标（深色主题，对勾形状，标题"对勾_check-correct"） |

### 2.3 META-INF/pluginIcon.svg

**路径**: `extracted/jar-contents/META-INF/pluginIcon.svg`

| 属性 | 值 |
|------|-----|
| 尺寸 | 64x64 |
| viewBox | 0 0 1024 1024 |
| 文件大小 | ~1.5KB |
| 主色 | #2C5EF5 (蓝色) |
| 用途 | JetBrains 插件市场展示图标 |

### 2.4 Icons 类字段映射

**类**: `com.aicode.icons.Icons`
**加载方式**: `com.intellij.openapi.util.IconLoader.findIcon(path, Icons.class)`

| 字段名 | 图标路径 | 主题适配 | 用途 |
|--------|---------|---------|------|
| `PluginIcon` | /icons/logo_16.svg (浅色) / /icons/logo_16_dark.svg (深色) | 是 | 插件主 logo，用于 toolWindow |
| `PluginIconLogo` | /icons/logo_16.svg (浅色) / /icons/logo_16_dark.svg (深色) | 是 | 插件 logo 大图 |
| `ToolWindowIcon` | /icons/toolWindow.svg (浅色) / /icons/toolWindow_dark.svg (深色) | 是 | 工具窗口图标 |
| `ReplaceAll` | /svg/replaceAll_dark.svg | 否 | 全部替换/采纳按钮 |
| `DebugIcon` | /icons/debug.svg | 否 | 调试徽章（浅色） |
| `DebugDarkIcon` | /icons/debug_dark.svg | 否 | 调试徽章（深色） |
| `StatusBarIcon` | /icons/indexIcon.svg | 否 | 状态栏图标 |
| `StatusBarIconDisabled` | /icons/disabled.svg (浅色) / /icons/disabled_dark.svg (深色) | 是 | 状态栏禁用图标 |
| `StatusBarIconNotSignedIn` | /icons/not_sign_in.svg | 否 | 状态栏未登录图标 |
| `StatusBarIconError` | (动态生成) | - | 状态栏错误图标 |
| `StatusBarCompletionInProgress` | AnimatedIcon.Default | - | 状态栏补全进行中动画 |
| `AirPlane` | /icons/air_plane.svg | 否 | 发送消息按钮 |
| `STOP` | /icons/stop.svg | 否 | 停止按钮 |

### 2.5 图标在代码中的引用

| 字段 | 引用类 | 场景 |
|------|--------|------|
| `PluginIcon` | `WebViewWindowPanel` | 工具窗口标题图标 |
| `PluginIconLogo` | `WebViewWindowPanel` | WebView 面板 logo |
| `ToolWindowIcon` | `CommitMessageSuggestionAction`, `PrepushReviewAction`, `BatchUTGeneratorAction`, `BatchFunctionCommentAction`, `InlayGotItListener`, `InlineChatBtnPanelRenderer`, `PluginEditorInlayHintsProvider` | 各操作按钮图标 |
| `ReplaceAll` | (内联聊天采纳) | 全部采纳按钮 |
| `DebugIcon` / `DebugDarkIcon` | `error.search.Presentation` | 错误搜索展示 |
| `StatusBarIcon` | `CodeProblemsIntentionAction`, `AICodeStatus`, `ThemeChangeListener`, `StatusBarPopup` | 状态栏正常状态 |
| `StatusBarIconDisabled` | `StatusBarPopup` | 状态栏禁用状态 |
| `StatusBarIconNotSignedIn` | `AICodeStatus`, `StatusBarPopup` | 状态栏未登录 |
| `StatusBarIconError` | `AICodeStatus` | 状态栏错误状态 |
| `StatusBarCompletionInProgress` | `CommitMessageSuggestionAction`, `PrepushReviewAction`, `AICodeStatus`, `StatusBarPopup` | 补全进行中动画 |
| `AirPlane` | `SendMessageAction` | 内联聊天发送按钮 |
| `STOP` | `ChatService`, `SqlService`, `WebViewDataTypeEnum`, `CodeTipTypedHandlerDelegate` | 停止生成按钮 |

### 2.6 图标在 plugin.xml 中的引用

```xml
<!-- 工具窗口图标 -->
<toolWindow id="星火飞码 iFlyCode" icon="com.aicode.icons.Icons.PluginIcon" .../>

<!-- 一键修复操作图标 -->
<action id="TriggerCodeProblemsTreePopupAction" icon="com.aicode.icons.Icons.ToolWindowIcon" .../>

<!-- 编辑器操作组图标 -->
<group id="aicode.EditorActionGroup" icon="com.aicode.icons.Icons.ToolWindowIcon" .../>
```

---
