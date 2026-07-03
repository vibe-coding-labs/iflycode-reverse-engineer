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
  &lt;head&gt;
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="icon" href="./favicon.svg" type="image/svg+xml" />
    &lt;title&gt;iFlyCode&lt;/title&gt;
    <script type="module" crossorigin src="./assets/index-f0296668.js">&lt;/script&gt;
    <link rel="stylesheet" href="./assets/index-1edf4661.css">
  &lt;/head&gt;
  &lt;body&gt;
    <div id="app">&lt;/div&gt;
  &lt;/body&gt;
&lt;/html&gt;
```

关键特征：
- `&lt;html&gt;` 元素的 `class="iflycode"` 用于 CSS 主题选择（勿修改）
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
    &lt;depends&gt;JUnit&lt;/depends&gt;
    &lt;depends&gt;Coverage&lt;/depends&gt;
    &lt;depends&gt;com.intellij.modules.coverage&lt;/depends&gt;
    <extensions defaultExtensionNs="com.intellij">
        <codeInsight.inlayProvider language="JAVA"
            implementationClass="com.aicode.toolwindow.PluginEditorInlayHintsProvider"/>
    &lt;/extensions&gt;
</idea-plugin>
```

Java 语言支持：依赖 JUnit 和 Coverage 模块，注册 Inlay 提示提供者。

#### code-javascript.xml

```xml
<idea-plugin>
    <extensions defaultExtensionNs="com.intellij">
        <codeInsight.inlayProvider language="JavaScript"
            implementationClass="com.aicode.toolwindow.PluginEditorInlayHintsProvider"/>
    &lt;/extensions&gt;
</idea-plugin>
```

JavaScript 语言支持：注册 Inlay 提示提供者。

#### code-python.xml

```xml
<idea-plugin>
    <extensions defaultExtensionNs="com.intellij">
        <codeInsight.inlayProvider language="Python"
            implementationClass="com.aicode.toolwindow.PluginEditorInlayHintsProvider"/>
    &lt;/extensions&gt;
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
