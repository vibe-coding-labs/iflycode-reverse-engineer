import { defineConfig } from 'vitepress'

export default defineConfig({
  base: '/iflycode-reverse-engineer/',
  title: 'iFlyCode 逆向分析文档',
  description: '讯飞星火飞码 JetBrains 插件通信协议逆向分析',
  lang: 'zh-CN',
  lastUpdated: true,
  ignoreDeadLinks: true,
  markdown: {
    lineNumbers: true,
  },
  themeConfig: {
    search: {
      provider: 'local',
    },
    nav: [
      { text: '🏠 首页', link: '/' },
      { text: '📚 全部文档', items: [
        { text: '🏗️ 架构与基础设施', link: '/01-architecture' },
        { text: '🔄 功能流程', link: '/08-auth-flow' },
        { text: '🤖 Agent 系统', link: '/23-agent-internals' },
        { text: '☕ Java 插件', link: '/47-action-system-complete' },
        { text: '🎨 前端与通信', link: '/30-webview-frontend' },
        { text: '🔐 加密与混淆', link: '/100-encryption-algorithms-complete-analysis/' },
        { text: '📊 运维与监控', link: '/17-heartbeat-error' },
        { text: '📋 综合报告', link: '/87-final-comprehensive-report' },
        { text: '📦 全量反编译', link: '/88-template-package-complete-decompilation/' },
      ]},
      { text: 'GitHub', link: 'https://github.com/vibe-coding-labs/iflycode-reverse-engineer' },
    ],
    sidebar: [
      // === 架构与基础设施 ===
      {
        text: '🏗️ 架构与基础设施',
        collapsed: false,
        items: [
          { text: '整体架构（先看这个）', link: '/01-architecture' },
          { text: 'Agent 进程管理', link: '/02-agent-process' },
          { text: 'WebSocket 通信协议', link: '/04-websocket-protocol' },
          { text: '消息格式定义', link: '/05-message-formats' },
          { text: '命令体系参考', link: '/06-command-reference' },
          { text: 'WebView JS Bridge', link: '/07-webview-bridge' },
          { text: '服务端端点', link: '/03-server-endpoints' },
        ],
      },
      // === 功能流程 ===
      {
        text: '🔄 功能流程',
        collapsed: false,
        items: [
          { text: '用户认证流程', link: '/08-auth-flow' },
          { text: '智能对话协议', link: '/09-chat-protocol' },
          { text: '代码补全协议', link: '/10-code-complete-protocol' },
          { text: '内联聊天协议', link: '/11-inline-chat-protocol' },
          { text: 'SQL 生成/优化', link: '/12-sql-protocol' },
          { text: '单元测试协议', link: '/13-unit-test-protocol' },
          { text: 'Git 评审协议', link: '/14-git-review-protocol' },
          { text: '代码搜索协议', link: '/15-code-search-protocol' },
          { text: '代码检查协议', link: '/16-code-check-protocol' },
        ],
      },
      // === Agent 系统 ===
      {
        text: '🤖 Agent 系统',
        collapsed: false,
        items: [
          { text: 'Agent 内部架构', link: '/23-agent-internals' },
          { text: 'Agent 二进制分析', link: '/31-agent-binary-analysis' },
          { text: 'Agent Cloud 协议（64个API端点）', link: '/22-agent-cloud-protocol/' },
          { text: 'Agent 服务层', link: '/62-agent-service-layer' },
          { text: 'Agent 通信深度分析', link: '/54-agent-communication-deep-analysis' },
          { text: 'CodeVector RAG', link: '/71-codevector-rag-workflow' },
          { text: 'WebSocket 分发链', link: '/77-websocket-dispatch-chain' },
          { text: 'WebSocket DTO', link: '/45-websocket-dto-model' },
          { text: '请求系统', link: '/44-request-complete-system' },
          { text: '内容处理器', link: '/48-content-handler-analysis' },
          { text: '代码补全完整流程', link: '/32-code-complete-flow' },
          { text: 'Agent Webpack 分析', link: '/66-agent-webpack-bundle-analysis' },
          { text: 'Agent 模块全面分析', link: '/107-agent-webpack-modules-and-full-analysis' },
          { text: 'Agent Webpack 清单', link: '/106-agent-webpack-modules-and-full-class-inventory' },
          { text: 'Agent 动态验证', link: '/108-agent-dynamic-verification' },
        ],
      },
      // === Java 插件 ===
      {
        text: '☕ Java 插件',
        collapsed: false,
        items: [
          { text: '动作系统完整分析', link: '/47-action-system-complete' },
          { text: 'Action 包完整反编译', link: '/84-action-package-complete/' },
          { text: '内联聊天系统', link: '/57-inline-chat-subsystem-complete' },
          { text: '内联聊天 UI', link: '/25-inline-chat-ui' },
          { text: '编辑器集成', link: '/27-editor-integration' },
          { text: 'Inlay 渲染系统', link: '/78-inlay-render-system' },
          { text: '监听器事件', link: '/28-listener-events' },
          { text: 'Listener 完整反编译', link: '/83-listener-complete-decompilation/' },
          { text: '模板系统', link: '/60-template-system-complete-analysis' },
          { text: '单元测试生成', link: '/46-unit-test-generation' },
          { text: 'Core Service 类', link: '/76-core-service-class-details/' },
          { text: 'Service 包完整反编译', link: '/91-service-package-complete-decompilation/' },
          { text: 'Q 包分析', link: '/33-Q-package-analysis' },
          { text: 'Plugin XML 分析', link: '/34-plugin-xml-analysis' },
          { text: 'Chat Git 集成', link: '/56-chat-git-integration-analysis' },
          { text: 'Settings 配置', link: '/43-settings-configuration' },
          { text: '设置同步协议', link: '/19-settings-protocol' },
          { text: 'Diff/APM 分析', link: '/42-diff-apm-analysis' },
          { text: '错误/异常分析', link: '/52-error-exception-analysis' },
          { text: 'Updater/自动更新', link: '/79-updater-domain-fileloader-analysis' },
        ],
      },
      // === 前端与通信 ===
      {
        text: '🎨 前端与通信',
        collapsed: false,
        items: [
          { text: 'WebView 前端架构', link: '/30-webview-frontend' },
          { text: 'WebView 前端完整分析', link: '/65-webview-frontend-complete-analysis' },
          { text: 'WebView ToolWindow', link: '/38-webview-toolwindow-analysis' },
          { text: 'WebView 协议加密', link: '/102-webview-protocol-encryption-analysis/' },
          { text: 'View/UI/StatusBar', link: '/85-view-ui-statusbar-toolwindow' },
          { text: '跨 IDE 差异分析', link: '/72-cross-ide-differences' },
          { text: 'Properties i18n', link: '/35-properties-i18n-analysis' },
          { text: 'I18n 完整字符串表', link: '/69-i18n-complete-string-table' },
        ],
      },
      // === 加密与混淆 ===
      {
        text: '🔐 加密与混淆',
        collapsed: false,
        items: [
          { text: '混淆技术总览', link: '/21-obfuscation' },
          { text: 'H() 混淆算法分析', link: '/64-h-deobfuscation-analysis' },
          { text: 'H() 混淆字符串表', link: '/29-obfuscated-strings' },
          { text: 'H() 反混淆方案', link: '/67-H-deobfuscation-solution' },
          { text: 'H() 反混淆完整结果', link: '/80-h-deobfuscation-complete-results/' },
          { text: '加密算法完整分析', link: '/100-encryption-algorithms-complete-analysis/' },
          { text: 'Java 加密调用链', link: '/101-java-encryption-call-chain' },
        ],
      },
      // === 运维与监控 ===
      {
        text: '📊 运维与监控',
        collapsed: false,
        items: [
          { text: '心跳与错误恢复', link: '/17-heartbeat-error' },
          { text: 'Telemetry 遥测（APM）', link: '/18-telemetry' },
          { text: '性能分析', link: '/73-performance-analysis' },
          { text: '安全审计', link: '/74-security-audit' },
        ],
      },
      // === 综合报告 ===
      {
        text: '📋 综合报告',
        collapsed: false,
        items: [
          { text: '综合分析报告', link: '/63-comprehensive-analysis-report' },
          { text: '最终综合报告', link: '/87-final-comprehensive-report' },
          { text: 'LLM 协议完整分析', link: '/99-llm-protocol-complete-analysis/' },
          { text: 'API 端点格式大全', link: '/70-api-endpoint-formats/' },
          { text: 'Plugin XML 完整注册表', link: '/68-plugin-xml-complete-registry' },
          { text: 'Velocity 单测模板分析', link: '/75-velocity-template-unit-test-flow' },
          { text: '架构交叉引用', link: '/81-architecture-cross-reference' },
          { text: 'Q/Velocity/Kotlin 扩展', link: '/82-q-velocity-kotlin-extensions' },
          { text: 'JSON 配置资源目录', link: '/86-json-config-resources-catalog' },
        ],
      },
      // === 全量反编译分析 ===
      {
        text: '📦 全量反编译分析',
        collapsed: true,
        items: [
          { text: 'Template 包', link: '/88-template-package-complete-decompilation/' },
          { text: 'Inline 包', link: '/89-inline-package-complete-decompilation' },
          { text: 'Agent Service 包', link: '/90-agent-service-complete-decompilation' },
          { text: 'Service 包', link: '/91-service-package-complete-decompilation/' },
          { text: 'Util 包', link: '/92-util-package-complete-decompilation' },
          { text: 'Agent DTO 包', link: '/93-agent-dto-complete-decompilation' },
          { text: 'Startup WebView 映射', link: '/94-startup-webview-message-mapping' },
          { text: 'Test 包', link: '/95-test-package-complete-decompilation' },
          { text: 'Enums 包', link: '/96-enums-complete-decompilation/' },
          { text: '功能包（domain/settings/updater等）', link: '/97-functional-packages-decompilation' },
          { text: '小包分析', link: '/98-small-packages-decompilation' },
        ],
      },
      // === 扫尾与附录 ===
      {
        text: '📎 扫尾与附录',
        collapsed: true,
        items: [
          { text: '缺失类反编译', link: '/103-missing-classes-decompilation-analysis' },
          { text: '最终盲点消除', link: '/104-final-blindspot-elimination' },
          { text: 'Velocity 终极扫尾', link: '/105-velocity-templates-and-final-blindspots' },
          { text: '枚举值参考', link: '/20-enums-reference' },
          { text: '完整类清单', link: '/36-complete-class-inventory' },
          { text: '逆向工程报告', link: '/reverse-engineering-report' },
        ],
      },
    ],
    socialLinks: [
      { icon: 'github', link: 'https://github.com/vibe-coding-labs/iflycode-reverse-engineer' },
    ],
    footer: {
      message: '本项目仅供学习研究，逆向分析内容归原厂商所有。',
      copyright: 'Copyright © 2026 Vibe Coding Labs',
    },
  },
})