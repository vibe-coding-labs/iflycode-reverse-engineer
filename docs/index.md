---
# https://vitepress.dev/reference/default-theme-home-page
layout: home

hero:
  name: "iFlyCode 逆向分析"
  text: "讯飞星火飞码 JetBrains 插件通信协议逆向分析"
  tagline: "版本 3.4.2-222 | 108 篇文档 | 全量反编译完成"
  actions:
    - theme: brand
      text: 开始阅读
      link: /01-architecture
    - theme: alt
      text: GitHub
      link: https://github.com/vibe-coding-labs/iflycode-reverse-engineer

features:
  - title: Java 插件源码
    details: 413 个 .java 文件，68 个包，100% 完整反编译 (jadx 1.5.0)
  - title: Agent Node.js
    details: 1,156 webpack 模块，5 种加密算法，27+ 个 Prompt 模板
  - title: WebView 前端
    details: 84 个 JS 文件，55 种 JS→Java 消息类型，Vue 2.7.14
  - title: 加密系统
    details: RSA/SM2/SM4/AES/MD5 完整实现提取
  - title: H() 混淆破解
    details: XOR + 周期 106 密钥算法完全破解，4,628 次调用
  - title: Agent 动态验证
    details: Agent 二进制运行确认，WebSocket 连接握手，SSL 禁用确认
---

## 项目信息

| 项目 | 值 |
|------|-----|
| 插件名称 | iFlyCode (星火飞码) |
| 版本 | 3.4.2-222 |
| 厂商 | 安徽卓见科技有限公司 |
| 底层模型 | 讯飞星火大模型 |
| IDE 兼容性 | JetBrains 2020.3+ / Android Studio |

## 文档导航

> 左侧**侧边栏**按类别整理了全部文档：
> - **架构与基础设施** — 三层通信模型、Agent 进程、消息格式
> - **功能流程** — 认证、对话、代码补全、SQL、Git 评审等协议
> - **运维与监控** — 心跳、遥测、设置同步
> - **逆向分析核心** — 混淆分析、类清单、命令体系
> - **综合报告** — 安全审计、性能分析、反混淆方案
> - **全量反编译分析** — 按包分类的完整反编译报告
> - **高层综合** — LLM 协议、加密算法最终分析

## 关联项目

- [iflycode-proxy](https://github.com/vibe-coding-labs/iflycode-proxy) — 基于本逆向分析构建的 OpenAI/Anthropic 兼容代理服务器