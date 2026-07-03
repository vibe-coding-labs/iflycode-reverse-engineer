# iFlyCode JSON 配置文件、资源文件与图标目录完整分析

## 1. JSON 映射文件

### 1.1 fileExtensionLanguageMappings.json

**文件路径**: `extracted/jar-contents/fileExtensionLanguageMappings.json`
**文件大小**: 3605 行
**数据结构**: JSON 数组，每个元素为 `{"extension": "ext", "value": "LanguageName"}`，扩展名无前导点

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
**数据结构**: JSON 数组，每个元素为 `{"name": "LanguageName", "type": "type", "extensions": [".ext1", ".ext2"]}`，扩展名有前导点

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

## 3. Velocity 模板文件

### 3.1 目录结构

```
fileTemplates/
  velocity.properties
  unitIncludes/
    default.html
    IflyCode common macros.java.ft
    IflyCode macros.java.ft
  unitTests/
    JUnit4&Mockito.java.ft
    JUnit4&Powermock.java.ft
    JUnit4.java.ft
    JUnit5&Mockito.java.ft
    JUnit5.java.ft
    SpringBootTest&Mockito.java.ft
    TestNG&Mockito.java.ft
    back
```

### 3.2 velocity.properties

```properties
file.resource.loader.path = ./unitIncludes/IflyCode macros.java.ft
```

配置 Velocity 模板引擎的资源加载路径，指向公共宏文件。

### 3.3 unitIncludes/ 模板

#### 3.3.1 default.html

模板变量说明文档，列出所有预定义变量：

| 变量名 | 类型 | 说明 |
|--------|------|------|
| `${CLASS_NAME}` | String | 被测试类名 |
| `${PACKAGE_NAME}` | String | 被测试类包名 |
| `${TESTED_CLASS_LANGUAGE}` | String | 被测试类语言 |
| `${MAX_RECURSION_DEPTH}` | int | 对象图内省最大递归深度 |
| `${TESTED_CLASS}` | `com.aicode.template.template.context.Type` | 被测试类实例 |
| `${MONTH_NAME_EN}` | String | 当前英文月份名 |
| `${DAY_NUMERIC}` | int | 当前日期数字 |
| `${HOUR_NUMERIC}` | int | 当前小时数字 |
| `${MINUTE_NUMERIC}` | int | 当前分钟数字 |
| `${SECOND_NUMERIC}` | int | 当前秒数字 |
| `${StringUtils}` | `com.aicode.template.template.context.StringUtils` | 字符串工具 |
| `${TestBuilder}` | `com.aicode.template.template.context.TestBuilder` | 测试构建器 |
| `${MockitoMockBuilder}` | `com.aicode.template.template.context.MockitoMockBuilder` | Mockito Mock 构建器 |
| `${TestSubjectUtils}` | `com.aicode.template.template.context.TestSubjectInspector` | 被测类检查工具 |

#### 3.3.2 IflyCode common macros.java.ft

公共宏定义文件，定义以下宏：

| 宏名 | 参数 | 功能 |
|------|------|------|
| `renderTestMethodName` | `$methodName` | 生成测试方法名：`test${CapitalizedName}` |
| `renderTestCaseMethodName` | `$caseMethodName, $methodName` | 生成测试用例方法名 |
| `renderTestMethodNameAsWords` | `$methodName` | 将方法名转为自然语言 |
| `testMethodSuffix` | `$methodName, $prefix` | 处理重名方法的后缀编号 |
| `renderJavaReturnVar` | `$type` | 渲染 Java 返回变量声明 |

关键全局变量：
- `$trackedTestMethodsCount` — 哈希表，跟踪每个方法名已生成的测试方法数量，用于处理重名

#### 3.3.3 IflyCode macros.java.ft

扩展宏定义文件，包含完整的测试代码生成宏。继承 `IflyCode common macros.java.ft`。

**VTL 变量声明**：

| 变量名 | 类型 | 说明 |
|--------|------|------|
| `$replacementTypes` | `Map<String,String>` | 自定义类型替换映射（参数生成） |
| `$replacementTypesForReturn` | `Map<String,String>` | 自定义类型替换映射（返回值生成） |
| `$defaultTypeValues` | `Map<String,String>` | 基本类型默认值映射 |
| `$mockBuilder` | `MockBuilder` | Mock 构建器（默认 `$MockitoMockBuilder`） |

**$defaultTypeValues 完整映射**：

| 类型 | 默认值 |
|------|--------|
| byte | (byte) 0 |
| short | (short) 0 |
| int | 0 |
| long | 0L |
| float | 0f |
| double | 0d |
| char | 'a' |
| boolean | true |
| java.lang.Byte | Byte.valueOf("00110") |
| java.io.Serializable | Long.valueOf(1) |
| java.util.UUID | UUID.randomUUID() |
| java.lang.Runnable | ()->{} |
| java.lang.Short | Short.valueOf((short)0) |
| java.lang.Integer | Integer.valueOf(0) |
| java.lang.Long | Long.valueOf(1) |
| java.lang.Float | Float.valueOf(1.1f) |
| java.lang.Double | Double.valueOf(0) |
| java.lang.Character | Character.valueOf('a') |
| java.lang.Boolean | Boolean.TRUE |
| org.springframework.data.redis.core.RedisTemplate | new RedisTemplate<String,Object>() |
| java.util.concurrent.ThreadPoolExecutor | new ThreadPoolExecutor(5,10,10L,MINUTES,new LinkedBlockingQueue<>()) |
| java.io.InputStream | new ByteArrayInputStream(new byte[]{0}) |
| java.io.ByteArrayInputStream | new ByteArrayInputStream(new byte[]{0}) |
| java.io.DataInputStream | new DataInputStream(new ByteArrayInputStream(new byte[]{})) |
| java.io.PipedInputStream | new PipedInputStream(4) |
| java.io.FilterInputStream | new DataInputStream(new ByteArrayInputStream(new byte[]{})) |
| java.io.InputStreamReader | new InputStreamReader(new ByteArrayInputStream(new byte[]{})) |
| java.io.ObjectInputStream | new ObjectInputStream(new ByteArrayInputStream(new byte[]{})) |
| java.io.SequenceInputStream | new SequenceInputStream(...) |
| java.io.FileInputStream | new FileInputStream(getClass().getResource(...)) |
| java.util.zip.ZipOutputStream | new ZipOutputStream(new ByteArrayOutputStream()) |
| java.io.OutputStream | new ByteArrayOutputStream() |
| java.io.FileOutputStream | new FileOutputStream(getClass().getResource(...)) |
| java.io.DataOutputStream | new DataOutputStream(new ByteArrayOutputStream()) |
| java.io.ByteArrayOutputStream | new ByteArrayOutputStream() |
| java.io.PipedOutputStream | new PipedOutputStream() |
| java.io.OutputStreamWriter | new OutputStreamWriter(new ByteArrayOutputStream()) |
| java.io.FilterOutputStream | new FilterOutputStream(new ByteArrayOutputStream()) |
| java.io.ObjectOutputStream | new ObjectOutputStream(new ByteArrayOutputStream()) |
| java.io.Reader | Reader.nullReader() |
| java.io.FileReader | new FileReader(getClass().getResource(...)) |
| java.io.CharArrayReader | new CharArrayReader("string to read".toCharArray()) |
| java.io.StringReader | new StringReader("string to read") |
| java.io.Writer | Writer.nullWriter() |
| java.io.CharArrayWriter | new CharArrayWriter() |
| java.io.StringWriter | new StringWriter() |
| java.io.PrintWriter | new PrintWriter(new StringWriter()) |
| java.io.FileWriter | new FileWriter(getClass().getResource(...)) |
| java.io.BufferedReader | new BufferedReader(new StringReader("string to read")) |
| java.math.BigDecimal | new BigDecimal(0) |
| java.util.Date | new GregorianCalendar($YEAR, $MONTH, $DAY, $HOUR, $MINUTE).getTime() |
| java.time.LocalDate | LocalDate.of($YEAR, Month.$MONTH, $DAY) |
| java.time.LocalDateTime | LocalDateTime.of($YEAR, Month.$MONTH, $DAY, $HOUR, $MINUTE, $SECOND) |
| java.time.LocalTime | LocalTime.of($HOUR, $MINUTE, $SECOND) |
| java.time.Instant | LocalDateTime.of(...).toInstant(ZoneOffset.UTC) |
| java.io.File | new File(getClass().getResource(...)) |
| java.lang.Class | $TESTED_CLASS.canonicalName.class |

**核心宏定义**：

| 宏名 | 参数 | 功能 |
|------|------|------|
| `renderTestSubjectInit` | `$testedClass, $hasTestableInstanceMethod, $hasMocks` | 渲染被测类初始化（@InjectMocks 或直接构造） |
| `renderMockedFields` | `$hasMocks, $testedClass` | 渲染 @Mock 字段 |
| `renderJavaReturnVar` | `$type` | 渲染返回变量声明 |
| `renderJUnitAssert` | `$method` | 渲染 JUnit 断言 |
| `renderTestNgAssert` | `$method` | 渲染 TestNG 断言 |
| `renderJunitAssertMethod` | `$type` | 选择 assertArrayEquals 或 assertEquals |
| `renderMethodCall` | `$method, $testedClassName, $caseResult` | 渲染方法调用 |
| `renderMockStubs` | `$method, $testedClass` | 渲染 Mockito when().thenReturn() 存根 |
| `renderMockStubWithData` | `$method, $testedClass, $caseResult` | 渲染带数据的 Mock 存根 |
| `renderMockVerifies` | `$method, $testedClass` | 渲染 Mockito verify() 验证 |
| `renderInternalMethodCallsStubs` | `$method, $testedClass` | 渲染 PowerMock 内部方法调用存根 |
| `renderInternalMethodCallsStubsWithData` | `$method, $testedClass, $caseResult` | 渲染带数据的 PowerMock 存根 |
| `renderTestMethodCase` | `$method, $testedClass` | 渲染方法分支内容（AI 精准生成） |
| `renderMethodCallWithSpy` | `$method, $testedClassName` | 渲染使用 spy 的方法调用 |
| `renderMethodCallWithSpyAndData` | `$method, $testedClassName, $caseResult` | 渲染带数据的 spy 方法调用 |

### 3.4 unitTests/ 模板

#### 模板概览

| 模板文件 | 行数 | 测试框架 | Mock 框架 | 特殊依赖 |
|----------|------|---------|----------|---------|
| JUnit4.java.ft | 271 | JUnit 4 | 无 | - |
| JUnit4&Mockito.java.ft | 451 | JUnit 4 | Mockito | - |
| JUnit4&Powermock.java.ft | 462 | JUnit 4 | PowerMock | JUnit, Coverage |
| JUnit5.java.ft | 308 | JUnit 5 | 无 | - |
| JUnit5&Mockito.java.ft | 479 | JUnit 5 | Mockito | - |
| SpringBootTest&Mockito.java.ft | 481 | Spring Boot | Mockito | - |
| TestNG&Mockito.java.ft | 257 | TestNG | Mockito | - |
| back | 22 | (片段) | - | PowerMock 方法模板片段 |

#### 模板共同结构

所有测试模板遵循相同的结构模式：

1. **VTL 变量声明** — 定义 `replacementTypes`、`replacementTypesForReturn`、`defaultTypeValues`、`mockBuilder`
2. **公共宏引入** — `#parse("IflyCode common macros.java")`
3. **包声明和导入** — 根据 framework 自动生成 import 语句
4. **测试类声明** — 使用 `@RunWith` / `@ExtendWith` / `@SpringBootTest` 等注解
5. **被测类初始化** — `@InjectMocks` 或直接构造
6. **Mock 字段声明** — `@Mock` 注解字段
7. **setUp 方法** — `@Before` / `@BeforeEach` / `@BeforeMethod` 初始化
8. **测试方法** — 遍历 `$TESTED_CLASS.methods`，使用 `#renderMethodWithData` 生成
9. **AI 分支测试** — 使用 `#renderTestMethodCase` 生成精准分支测试

#### 各模板差异

| 特性 | JUnit4 | JUnit4+Mockito | JUnit4+PowerMock | JUnit5 | JUnit5+Mockito | SpringBootTest+Mockito | TestNG+Mockito |
|------|--------|---------------|-----------------|--------|---------------|----------------------|---------------|
| @RunWith | JUnit4 | MockitoJUnitRunner | PowerMockRunner | - | MockitoExtension | SpringBootTest | - |
| @ExtendWith | - | - | - | - | MockitoExtension | - | - |
| @SpringBootTest | - | - | - | - | - | 是 | - |
| @Before | @Before | @Before | @Before | @BeforeEach | @BeforeEach | @BeforeEach | @BeforeMethod |
| @Test | @Test | @Test | @Test | @Test | @Test | @Test | @Test |
| Assert | Assert.assertEquals | Assert.assertEquals | Assert.assertEquals | Assertions.assertEquals | Assertions.assertEquals | Assertions.assertEquals | Assert.assertEquals |
| Mock 初始化 | - | MockitoAnnotations.openMocks | MockitoAnnotations.openMocks | - | MockitoAnnotations.openMocks | MockitoAnnotations.openMocks | MockitoAnnotations.openMocks |
| 异常测试 | @Test(expected) | @Test(expected) | @Test(expected) | Assertions.assertThrows | Assertions.assertThrows | Assertions.assertThrows | @Test(expectedExceptions) |
| Spy 支持 | - | - | PowerMockito.spy | - | - | - | - |
| 内部方法 Mock | - | - | doReturn...when(spy) | - | - | - | - |

#### back 文件

`back` 是一个模板片段文件（22行），包含 PowerMock 模式的测试方法体模板：

```velocity
@Test
public void #renderTestMethodName($method.name)()
    #if($PowerMockBuilder.hasInternalMethodCall($method, $TESTED_CLASS)||$method.private)
        throws Exception
    #else
        $mockBuilder.resolveExceptions($method)
    #end {
    #if($hasMocks)
        #renderStaticMockStubs($method,$caseResult)
    #end
    #if($hasMocks && $PowerMockBuilder.shouldStub($method, $TESTED_CLASS))
        #renderMockStubs($method, $TESTED_CLASS)
    #end
    #if($PowerMockBuilder.hasInternalMethodCall($method, $TESTED_CLASS) && $PowerMockBuilder.shouldStub($method, $TESTED_CLASS))
        #renderInternalMethodCallsStubs($method, $TESTED_CLASS)
        #renderMethodCallWithSpy($method,$TESTED_CLASS.name)
    #else
        #renderMethodCall($method,$TESTED_CLASS.name)
    #end
    #if($hasMocks && $PowerMockBuilder.shouldVerify($method,$TESTED_CLASS))
        #renderMockVerifies($method,$TESTED_CLASS)
    #end
    #if($method.hasReturn())
        Assert.#renderJUnitAssert($method,$method.caseResult)
    #end
}
```

### 3.5 模板在代码中的引用

| 类 | 用途 |
|----|------|
| `com.aicode.template.VelocityInitializer` | Velocity 引擎初始化 |
| `com.aicode.template.TemplateGenerator` | 模板生成器入口 |
| `com.aicode.template.TestTemplateParams` | 模板参数封装 |
| `com.aicode.template.TestTemplateContextBuilder` | 模板上下文构建 |
| `com.aicode.template.fileloader.TemplateResourceLoader` | 模板资源加载器 |
| `com.aicode.template.fileloader.FileTemplatesLoader` | 文件模板加载器 |
| `com.aicode.template.fileloader.FileTemplateContext` | 文件模板上下文 |
| `com.aicode.template.fileloader.FileTemplateLoadResult` | 模板加载结果 |
| `com.aicode.template.fileloader.UnitFileTemplate` | 单元测试文件模板 |
| `com.aicode.template.fileloader.UnitTemplateManager` | 单元测试模板管理器 |
| `com.aicode.template.fileloader.FTManager` | 文件模板管理器 |
| `com.aicode.template.fileloader.TemplateRegistry` | 模板注册表（注册 JUnit4/5 等模板） |
| `com.aicode.template.generator.TestFileTemplateUtil` | 测试文件模板工具 |
| `com.aicode.template.generator.CreateTestMethodTask` | 创建测试方法任务 |
| `com.aicode.template.generator.CreateTestFileTask` | 创建测试文件任务 |
| `com.aicode.template.builder.MockitoMockBuilder` | Mockito Mock 构建器 |
| `com.aicode.template.builder.PowerMockBuilder` | PowerMock 构建器 |
| `com.aicode.template.builder.MockBuilderFactory` | Mock 构建器工厂 |
| `com.aicode.template.context.service.TestBuilder` | 测试构建器接口 |
| `com.aicode.template.context.service.impl.JavaTestBuilderImpl` | Java 测试构建器实现 |
| `com.aicode.template.context.service.impl.TestBuilderImpl` | 测试构建器实现 |

---

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
| config.batch.unit.test.notice | 单测生成完毕{0}，成功{1}个文件，跳过{2}个文件，失败{3}个文件 |
| config.batch.unit.test.test.module.directory.title | 单元测试代码目录： |
| config.batch.unit.test.task.error | 上一个单元测试任务还未完成！请稍后 |
| config.batch.unit.test.create.error | 测试代码目录创建失败！ |
| config.batch.unit.test.create.single.error | 单元测试代码生成失败！{0} |
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
| config.batch.unit.test.generate.wait.message | {0}正在等待模型生成，预计还需{1}s，已等待{2}s |
| config.batch.unit.test.save.path.content | 记录单元测试代码目录路径，下次默认保存到此目录 |

#### 单元测试生成结果

| Key | 解码值 |
|-----|--------|
| unit.test.generate.success | 单元测试代码已生成，点击查看 |
| unit.test.method.generate.success | 单元测试代码生成完毕 |
| unit.test.method.generate.skip.message | {0}:下列方法无需生成单元测试{1} |
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
| aicode.update.installing.title | 正在下载{0}插件 |

#### 内联聊天

| Key | 解码值 |
|-----|--------|
| inline.chat.error | 无结果，请重试 |
| inline.chat.accept.text | 采纳({0}) |
| inline.chat.reject.text | 拒绝({0}) |
| inline.chat.retry.text | 重试({0}) |
| inline.chat.diff.text | 查看diff |
| inline.chat.cancel.text | 取消({0}) |

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

## 5. WebView 前端资源

### 5.1 目录结构

```
webview/
  index.html          (491B)  — SPA 入口页面
  favicon.svg         (2.5K)  — 网页图标
  icon.svg            (1.8K)  — 应用图标
  assets/             (30MB)  — 143 个静态资源文件
    84 JS 文件
    24 CSS 文件
    14 SVG 文件
    11 PNG 文件
    5 GIF 文件
    2 WOFF 文件
    2 TTF 文件
    1 WOFF2 文件
```

### 5.2 index.html

```html
<!DOCTYPE html>
<html lang="en" class="iflycode">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="icon" href="./favicon.svg" type="image/svg+xml" />
    <title>iFlyCode</title>
    <script type="module" crossorigin src="./assets/index-f0296668.js"></script>
    <link rel="stylesheet" href="./assets/index-1edf4661.css">
  </head>
  <body>
    <div id="app"></div>
  </body>
</html>
```

关键特征：
- `<html>` 元素的 `class="iflycode"` 用于 CSS 主题选择（勿修改）
- 入口 JS: `index-f0296668.js` (3.6MB, Vue 3 + Vite SPA 主 bundle)
- 入口 CSS: `index-1edf4661.css` (152K, 主样式表)
- `<script type="module">` 表明使用 ES Module 规范
- `crossorigin` 属性用于 JCEF 跨域安全策略

### 5.3 技术栈推断

| 特征 | 推断 |
|------|------|
| `<div id="app">` | Vue 3 SPA |
| `type="module"` | Vite 构建 |
| 文件名 hash (如 `f0296668`) | Vite 内容 hash |
| chunk 分割策略 | Vite code splitting |
| Element Plus 图标字体 | Element Plus UI 框架 |
| Mermaid 图表 JS | Mermaid.js 图表渲染 |
| KaTeX 数学公式 | KaTeX 数学渲染 |
| Cytoscape 图论可视化 | Cytoscape.js 网络图 |
| Dagre 布局 | Dagre 有向图布局 |
| iconfont 三套字体 | 自定义 iconfont 图标 |

### 5.4 核心 JS Bundle

| 文件 | 大小 | 功能 |
|------|------|------|
| index-f0296668.js | 3.6M | Vue 3 + Vite 主 bundle（含 Vue runtime、组件、路由） |
| index-9c2209c9.js | 1.5M | 第二主 bundle（含 Markdown 渲染、代码高亮等） |
| cytoscape.esm-23e802cd.js | 902K | Cytoscape.js 图论可视化库 |
| mermaid-parser.core-dc9f3dce.js | 611K | Mermaid.js 图表解析器核心 |
| katex-db156564.js | 478K | KaTeX 数学公式渲染 |
| architectureDiagram-IEHRJDOE-906938ae.js | 406K | 架构图渲染 |
| sendMsgMode-8b767ec0.js | 280K | 消息发送模式组件 |
| mindmap-definition-ALO5MXBD-e4546bb9.js | 243K | 思维导图定义与渲染 |
| index-1edf4661.css | 152K | 主样式表 |
| index-226a90e1.css | 64K | 第二样式表 |
| sendMsgMode-eb863a7a.css | 64K | 消息发送模式样式 |

### 5.5 图表/可视化模块

WebView 嵌入了完整的 Mermaid.js 图表系统，支持以下图表类型：

| 模块文件 | 图表类型 |
|----------|---------|
| flowDiagram-4HSFHLVR.js | 流程图 |
| sequenceDiagram-X6HHIX6F.js | 时序图 |
| classDiagram-GIVACNV2.js | 类图 |
| classDiagram-v2-COTLJTTW.js | 类图 v2 |
| stateDiagram-DGXRK772.js | 状态图 |
| stateDiagram-v2-YXO3MK2T.js | 状态图 v2 |
| erDiagram-Q7BY3M3F.js | ER 图 |
| ganttDiagram-APWFNJXF.js | 甘特图 |
| pieDiagram-IB7DONF6.js | 饼图 |
| requirementDiagram-KVF5MWMF.js | 需求图 |
| c4Diagram-VJAJSXHY.js | C4 架构图 |
| gitGraphDiagram-7IBYFJ6S.js | Git 图 |
| journeyDiagram-U35MCT3I.js | 用户旅程图 |
| mindmap-definition-ALO5MXBD.js | 思维导图 |
| timeline-definition-BDJGKUSR.js | 时间线 |
| sankeyDiagram-QLVOVGJD.js | 桑基图 |
| blockDiagram-JOT3LUYC.js | 方块图 |
| quadrantDiagram-7GDLP6J5.js | 四象限图 |
| xychartDiagram-VJFVF3MP.js | XY 图表 |
| architectureDiagram-IEHRJDOE.js | 架构图 |
| kanban-definition-NDS4AKOZ.js | 看板图 |

### 5.6 图片资源

| 文件 | 大小 | 用途推断 |
|------|------|---------|
| acceptImg-861626e8.gif | 4.1M | 采纳/接受操作引导动画 |
| acceptLineImg-e4e5b3f5.gif | 6.2M | 采纳行操作引导动画 |
| focusWebviewIdeaImg-f4d37783.gif | 3.4M | IDEA WebView 聚焦引导动画 |
| focusWebviewImg-b27fde3b.gif | 3.3M | WebView 聚焦引导动画 |
| forceSuggestImg-012f86b4.gif | 2.6M | 强制建议引导动画 |
| login-bg-dark-cf18bcfe.png | 167K | 登录页面背景（深色主题） |
| login-bg-light-b9dac078.png | 196K | 登录页面背景（浅色主题） |
| inlinechat-idea-6efad0a1.png | 89K | 内联聊天 IDEA 示意图 |
| inlinechat-vscode-c464105e.png | 159K | 内联聊天 VSCode 示意图 |
| init-error-dark-655af060.png | 17K | 初始化错误页面（深色） |
| init-error-light-8fe08347.png | 16K | 初始化错误页面（浅色） |
| init-page-logo-dark-999519b0.png | 14K | 初始化页面 logo（深色） |
| init-page-logo-light-3c17efdc.png | 12K | 初始化页面 logo（浅色） |
| interface-error-46484725.png | 5K | 接口错误图（深色） |
| interface-error-light-0cce1215.png | 4.6K | 接口错误图（浅色） |
| wechat-7fd7c344.png | 4.9K | 微信图标 |

### 5.7 SVG 图标资源

| 文件 | 大小 | 用途推断 |
|------|------|---------|
| asserts-0b649a72.svg | 3.2K | 断言图标 |
| backTop-5a9f82af.svg | 1.5K | 返回顶部图标 |
| beta-f5ff9cdc.svg | 2.7K | Beta 标记图标 |
| branch-2225d873.svg | 3.1K | 分支图标 |
| dependencies-15cca920.svg | 2.9K | 依赖图标 |
| error-4fda799d.svg | 1.5K | 错误图标 |
| iconC-d8f82dc5.svg | 4K | C 语言图标 |
| iconP-90b6c891.svg | 4.3K | P 语言图标 |
| iconT-bad1e0ae.svg | 4.3K | T 语言图标 |
| info-d3843cec.svg | 2.3K | 信息图标 |
| input-581ec246.svg | 2.3K | 输入图标 |
| star-a050b290.svg | 889B | 星标图标 |
| success-d268d9f2.svg | 1K | 成功图标 |
| waring-64256091.svg | 882B | 警告图标 |

### 5.8 字体资源

| 文件 | 大小 | 用途 |
|------|------|------|
| element-icons-a30f5b3b.ttf | 56K | Element Plus 图标字体 (TTF) |
| element-icons-ab40a589.woff | 28K | Element Plus 图标字体 (WOFF) |
| iconfont-38e74bcd.ttf | 45K | 自定义 iconfont 图标 (TTF) |
| iconfont-5b60d1cb.woff2 | 23K | 自定义 iconfont 图标 (WOFF2) |
| iconfont-b15805b6.woff | 27K | 自定义 iconfont 图标 (WOFF) |

### 5.9 IDE/VSCode 适配模块

| 文件 | 大小 | 功能 |
|------|------|------|
| ideaUtil-11ab0730.js | 535B | IntelliJ IDEA 适配工具 |
| vscodeUtil-49d49699.js | 536B | VSCode 适配工具 |
| eclipseUtil-82d0751a.js | 476B | Eclipse 适配工具 |
| getDocumentLanguage-4b7a17eb.js | 1.4K | 获取文档语言类型 |
| channel-cc5a1259.js | 211B | IDE 通信通道 |
| clone-985a3830.js | 184B | 克隆工具 |
| init-cc95ec8e.js | 258B | 初始化模块 |

### 5.10 WebView 在代码中的引用

| 类 | 用途 |
|----|------|
| `com.aicode.view.WebViewWindowPanel` | WebView 窗口面板，加载 index.html |
| `com.aicode.toolwindow.PluginToolWindowFactory` | 工具窗口工厂，创建 WebView 面板 |
| `com.aicode.view.WebViewDataTypeEnum` | WebView 数据类型枚举 |
| `com.aicode.view.WebViewChatService` | WebView 聊天服务（JS↔Java 通信） |

---

## 6. META-INF 配置

### 6.1 MANIFEST.MF

```
Manifest-Version: 1.0
Created-By: Gradle 7.6.1
Build-JVM: 11.0.5 (Oracle Corporation 11.0.5+10-LTS)
Version: 3.4.2-222
Build-Plugin: Gradle IntelliJ Plugin
Build-Plugin-Version: 1.13.3
Build-OS: Linux 3.10.0-1127.el7.x86_64 amd64
Build-SDK: IC-2022.2
```

### 6.2 语言扩展配置 (code-*.xml)

#### code-java.xml

```xml
<idea-plugin>
    <depends>JUnit</depends>
    <depends>Coverage</depends>
    <depends>com.intellij.modules.coverage</depends>
    <extensions defaultExtensionNs="com.intellij">
        <codeInsight.inlayProvider language="JAVA"
            implementationClass="com.aicode.toolwindow.PluginEditorInlayHintsProvider"/>
    </extensions>
</idea-plugin>
```

Java 语言支持：依赖 JUnit 和 Coverage 模块，注册 Inlay 提示提供者。

#### code-javascript.xml

```xml
<idea-plugin>
    <extensions defaultExtensionNs="com.intellij">
        <codeInsight.inlayProvider language="JavaScript"
            implementationClass="com.aicode.toolwindow.PluginEditorInlayHintsProvider"/>
    </extensions>
</idea-plugin>
```

JavaScript 语言支持：注册 Inlay 提示提供者。

#### code-python.xml

```xml
<idea-plugin>
    <extensions defaultExtensionNs="com.intellij">
        <codeInsight.inlayProvider language="Python"
            implementationClass="com.aicode.toolwindow.PluginEditorInlayHintsProvider"/>
    </extensions>
</idea-plugin>
```

Python 语言支持：注册 Inlay 提示提供者。

### 6.3 pluginIcon.svg

JetBrains 插件市场展示图标（64x64），与 `logo_16.svg` 使用相同的 iFlyCode logo 设计，主色 #2C5EF5。

---

## 7. 资源文件引用关系总图

```
plugin.xml
  |-- com.aicode.icons.Icons.PluginIcon  --> /icons/logo_16.svg (浅色) / logo_16_dark.svg (深色)
  |-- com.aicode.icons.Icons.ToolWindowIcon --> /icons/toolWindow.svg (浅色) / toolWindow_dark.svg (深色)
  |
  |-- code-java.xml --> PluginEditorInlayHintsProvider (JAVA Inlay)
  |-- code-javascript.xml --> PluginEditorInlayHintsProvider (JavaScript Inlay)
  |-- code-python.xml --> PluginEditorInlayHintsProvider (Python Inlay)

Icons 类 (com.aicode.icons.Icons)
  |-- PluginIcon / PluginIconLogo --> logo_16.svg / logo_16_dark.svg
  |-- ToolWindowIcon --> toolWindow.svg / toolWindow_dark.svg
  |-- StatusBarIcon --> indexIcon.svg
  |-- StatusBarIconDisabled --> disabled.svg / disabled_dark.svg
  |-- StatusBarIconNotSignedIn --> not_sign_in.svg
  |-- StatusBarCompletionInProgress --> AnimatedIcon.Default
  |-- DebugIcon / DebugDarkIcon --> debug.svg / debug_dark.svg
  |-- ReplaceAll --> replaceAll_dark.svg
  |-- AirPlane --> air_plane.svg
  |-- STOP --> stop.svg

LanguageInfoManager / CodeLanguageInfoSupport
  |-- fileExtensionLanguageMappings.json (扩展名 -> 语言, 901条)
  |-- languageFileExtensionMappings.json (语言 -> 扩展名, 396种语言, 1005个扩展名条目)
  |-- FileExtensionLanguageDetails (被 20+ 类引用)

WebViewWindowPanel / PluginToolWindowFactory
  |-- webview/index.html (SPA 入口, Vue 3 + Vite)
  |-- webview/favicon.svg / icon.svg
  |-- webview/assets/ (143 文件, 30MB)
      |-- index-f0296668.js (3.6M, 主 bundle)
      |-- index-1edf4661.css (152K, 主样式)
      |-- mermaid-parser.core (611K, 图表引擎)
      |-- cytoscape.esm (902K, 图论可视化)
      |-- 21 种 Mermaid 图表模块
      |-- 5 个 GIF 引导动画 (19.5MB)
      |-- Element Plus + 自定义 iconfont 字体
      |-- ideaUtil / vscodeUtil / eclipseUtil (IDE 适配)

TemplateGenerator / UnitTemplateManager
  |-- velocity.properties (引擎配置)
  |-- unitIncludes/IflyCode common macros.java.ft (公共宏)
  |-- unitIncludes/IflyCode macros.java.ft (扩展宏)
  |-- unitIncludes/default.html (变量文档)
  |-- unitTests/JUnit4.java.ft
  |-- unitTests/JUnit4&Mockito.java.ft
  |-- unitTests/JUnit4&Powermock.java.ft
  |-- unitTests/JUnit5.java.ft
  |-- unitTests/JUnit5&Mockito.java.ft
  |-- unitTests/SpringBootTest&Mockito.java.ft
  |-- unitTests/TestNG&Mockito.java.ft
  |-- unitTests/back (PowerMock 片段)

BasicActionsBundle (com.aicode.message.BasicActionsBundle)
  |-- messages/BasicActionsBundle.properties (115 条, UI 文本)
  |-- messages/aicode.properties (14 条, 状态/更新文本)
  |-- 被 60+ 类引用
```

---

## 8. 关键发现

1. **语言映射覆盖广泛**：支持 371 种语言、901 个文件扩展名，覆盖主流编程语言和大量小众语言。映射数据来源于 GitHub Linguist 规范。25 种语言仅在 lang->ext 中存在（无文件扩展名或扩展名不在 ext->lang 映射中）。

2. **图标系统支持深色主题**：所有关键图标（logo、toolWindow、disabled）都有浅色/深色两个版本，通过 `Icons.getIcon()` 方法根据 `isUnderDarcula()` 动态选择。

3. **模板系统高度模块化**：Velocity 模板分为公共宏（common macros）和框架特定模板两层，支持 7 种测试框架组合，包含 AI 精准生成模式。

4. **属性文件集中管理 UI 文本**：所有中文 UI 文本通过 Unicode 转义存储在 properties 文件中，`BasicActionsBundle` 是主要的消息包（115条），覆盖批量单测、内联聊天、知识库等全部功能模块。`aicode.properties`（14条）管理状态和更新消息。

5. **WebView 前端为 Vue 3 + Vite SPA**：30MB 资源包含 143 个文件，嵌入 Mermaid.js（21种图表类型）、KaTeX、Cytoscape.js 等可视化库。5个 GIF 动画（总计 19.5MB）用于功能引导。支持 IntelliJ IDEA / VSCode / Eclipse 三种 IDE 适配。

6. **配置端点暴露**：`aicode.otel.endpoint` 暴露了 OpenTelemetry 追踪端点 `https://saas.api.example.com/v1/traces`，`aicode.complete.time.out` 设置补全超时为 10 秒。

7. **构建信息**：插件版本 3.4.2-222，基于 IntelliJ IC-2022.2 SDK 构建，使用 Gradle 7.6.1 + IntelliJ Plugin 1.13.3，发布日期 2025-04-22。

8. **跨语言扩展名共享**：65 个文件扩展名在 lang->ext 映射中被多种语言共享（如 .asc 被 AGS Script/AsciiDoc/Public Key 共享），但 ext->lang 映射中每个扩展名只对应一种语言（无歧义），说明 ext->lang 采用了优先级选择策略。
