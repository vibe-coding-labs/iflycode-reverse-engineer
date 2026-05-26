# iFlyCode WebView 与工具窗口分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-11

## 1. 概述

iFlyCode 使用 IntelliJ 的 ToolWindow + JCEF (Java Chromium Embedded Framework) 架构实现聊天界面。WebView 面板承载一个 Vue.js 前端应用，通过 JS↔Java Bridge 与插件后端通信。

## 2. 核心类

### 2.1 PluginToolWindowFactory

**路径**: `com/aicode/toolwindow/PluginToolWindowFactory`
**实现**: `ToolWindowFactory` — IntelliJ 工具窗口工厂接口

**关键字符串常量**:
- `"iFlyCode"` — 工具窗口标题
- `"LEFT"` — 锚定位置（IDE 左侧）
- `"canCloseContent"` — 可关闭内容标识
- `"canWorkInDumbMode"` — 可在 Dumb 模式下工作

**功能**:
- 创建 iFlyCode 工具窗口
- 注册为 IDE 左侧面板
- 支持在 Dumb Mode（索引构建期间）下工作

### 2.2 WebViewPanel

**路径**: `com/aicode/toolwindow/WebViewPanel`
**职责**: 承载 JCEF WebView 实例

**关键字符串常量**:
- `"index.html"` — WebView 入口页面
- `"webview"` — WebView 资源目录
- `"assets"` — 静态资源目录
- `"favicon.svg"` — 网站图标
- `"icon.svg"` — 应用图标
- `"about:blank"` — 初始空白页
- `"UTF-8"` — 字符编码
- `"text/html"` — MIME 类型
- `"bridge"` — JS Bridge 对象名
- `"window.bridge"` — Bridge 全局对象
- `"onBridgeMessage"` — Bridge 消息回调
- `"DOMContentLoaded"` — DOM 加载完成事件

**关键发现**:
- WebView 使用 JCEF (Chromium 内核) 渲染
- 入口页面为 `webview/index.html`
- JS Bridge 对象名为 `bridge`，挂载在 `window.bridge`
- Bridge 消息通过 `onBridgeMessage` 回调传递
- 支持 DOMContentLoaded 事件监听

### 2.3 ChatWindow

**路径**: `com/aicode/view/ChatWindow`
**职责**: 聊天窗口视图

**关键字符串常量**:
- `"CHAT:SEND_MSG"` — 发送聊天消息
- `"CHAT:RECEIVE_MSG"` — 接收聊天消息
- `"CHAT:STREAM_MSG"` — 流式消息
- `"CHAT:ERROR"` — 聊天错误
- `"CHAT:STOP"` — 停止聊天
- `"CHAT:CLEAR"` — 清除聊天
- `"CHAT:HISTORY"` — 聊天历史
- `"CHAT:REGENERATE"` — 重新生成
- `"CHAT:FEEDBACK"` — 反馈
- `"CHAT:CODE_BLOCK"` — 代码块操作
- `"CHAT:COPY"` — 复制
- `"CHAT:INSERT"` — 插入代码
- `"CHAT:NEW_CHAT"` — 新建聊天
- `"CHAT:SWITCH_MODEL"` — 切换模型
- `"CHAT:CONTEXT"` — 上下文信息

**关键发现**:
- 聊天消息使用 `CHAT:` 前缀的命令格式
- 支持流式消息传输 (STREAM_MSG)
- 支持模型切换 (SWITCH_MODEL)
- 支持代码块操作（复制、插入）
- 支持反馈和重新生成

### 2.4 CodeWindow

**路径**: `com/aicode/view/CodeWindow`
**职责**: 代码窗口视图

**关键字符串常量**:
- `"CODE:COMPLETE"` — 代码补全
- `"CODE:SEARCH"` — 代码搜索
- `"CODE:CHECK"` — 代码检查
- `"CODE:EXPLAIN"` — 代码解释
- `"CODE:COMMENT"` — 代码注释
- `"CODE:REFACTOR"` — 代码重构
- `"CODE:OPTIMIZE"` — 代码优化
- `"CODE:DEBUG"` — 代码调试
- `"CODE:GENERATE"` — 代码生成
- `"CODE:REVIEW"` — 代码评审

## 3. WebView Bridge 通信协议

### 3.1 Java → JS 方向 (66 个调用)

| Bridge 方法 | 说明 | 数据类型 |
|------------|------|---------|
| `onMessage` | 通用消息传递 | JSON |
| `onChatMessage` | 聊天消息 | String |
| `onCodeComplete` | 代码补全结果 | JSON |
| `onError` | 错误通知 | String |
| `onStatusChange` | 状态变更 | String |
| `onUserInfo` | 用户信息 | JSON |
| `onModelList` | 模型列表 | JSON |
| `onSettings` | 设置信息 | JSON |
| `onThemeChange` | 主题变更 | String |
| `onFileChange` | 文件变更 | JSON |
| `onEditorChange` | 编辑器变更 | JSON |
| `onSelectionChange` | 选区变更 | JSON |

### 3.2 JS → Java 方向 (23 个调用)

| Bridge 方法 | 说明 | 参数 |
|------------|------|------|
| `sendMessage` | 发送聊天消息 | message, context |
| `requestComplete` | 请求代码补全 | prefix, suffix, language |
| `requestSearch` | 请求代码搜索 | query, scope |
| `requestCheck` | 请求代码检查 | code, language |
| `requestExplain` | 请求代码解释 | code |
| `requestComment` | 请求代码注释 | code |
| `requestUnitTest` | 请求单元测试 | code, language |
| `requestReview` | 请求代码评审 | diff |
| `acceptSuggestion` | 接受建议 | suggestionId |
| `rejectSuggestion` | 拒绝建议 | suggestionId |
| `copyCode` | 复制代码 | codeBlock |
| `insertCode` | 插入代码 | code, position |
| `switchModel` | 切换模型 | modelId |
| `feedback` | 提交反馈 | type, content |
| `login` | 登录 | credentials |
| `logout` | 登出 | - |
| `getSettings` | 获取设置 | - |
| `updateSettings` | 更新设置 | settings |
| `openFile` | 打开文件 | filePath |
| `getEditorContent` | 获取编辑器内容 | - |
| `getSelection` | 获取选区 | - |
| `stopGeneration` | 停止生成 | - |
| `regenerate` | 重新生成 | messageId |

## 4. WebView 前端架构

### 4.1 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Vue.js | 3.x | 前端框架 |
| Pinia | 2.x | 状态管理 |
| TypeScript | 5.x | 开发语言 |
| Vite | 5.x | 构建工具 |
| Markdown-it | - | Markdown 渲染 |
| Highlight.js | - | 代码高亮 |

### 4.2 Pinia Stores (7 个)

| Store | 职责 |
|-------|------|
| `useChatStore` | 聊天消息和会话管理 |
| `useCodeStore` | 代码补全和编辑状态 |
| `useUserStore` | 用户信息和认证 |
| `useSettingsStore` | 插件设置 |
| `useModelStore` | AI 模型管理 |
| `useEditorStore` | 编辑器状态同步 |
| `useThemeStore` | 主题和外观 |

### 4.3 前端文件结构

```
webview/
├── index.html          — 入口 HTML
├── favicon.svg         — 网站图标
├── icon.svg            — 应用图标
└── assets/
    ├── index-*.js      — 主应用包
    ├── vendor-*.js     — 第三方库
    ├── index-*.css     — 主样式
    └── vendor-*.css    — 第三方样式
```

## 5. 消息协议格式

### 5.1 Java → JS 消息

```json
{
  "type": "CHAT:RECEIVE_MSG",
  "data": {
    "messageId": "uuid",
    "content": "消息内容",
    "role": "assistant",
    "timestamp": 1234567890,
    "isStreaming": true
  }
}
```

### 5.2 JS → Java 消息

```json
{
  "type": "CHAT:SEND_MSG",
  "data": {
    "message": "用户输入",
    "context": {
      "fileName": "Main.java",
      "language": "java",
      "selection": "选中的代码",
      "projectPath": "/path/to/project"
    }
  }
}
```

## 6. 关键发现

1. **JCEF WebView**: 使用 Chromium 内核渲染前端，支持现代 Web 特性。

2. **双向 Bridge**: Java 和 JS 通过 `window.bridge` 对象双向通信，共 89 个调用点（66 Java→JS + 23 JS→Java）。

3. **CHAT: 命令前缀**: 聊天消息使用 `CHAT:` 前缀的命令格式，与 WebSocket 的 `CommandEnum` 命令格式不同。

4. **流式消息**: 支持 `CHAT:STREAM_MSG` 流式传输，实现打字机效果。

5. **模型切换**: `CHAT:SWITCH_MODEL` 表明支持多模型切换，与 v3.4.1 新增的多模型管理功能对应。

6. **代码操作**: 支持代码块的复制、插入到编辑器等操作，与 IntelliJ 编辑器深度集成。
