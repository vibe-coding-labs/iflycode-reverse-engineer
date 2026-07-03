# 90 - Agent Service 包完整反编译分析

## 概述

`com.aicode.agent.service` 包含 iFlyCode 插件的核心服务层，共 32 个类，负责所有业务逻辑的统一分发、Agent 进程管理和 WebSocket 通信。所有服务类遵循统一的 `handleAction` / `handleAgentAction` 双通道分发模式。

---

> **本文档已拆分为以下子页面：**

- [32类完整清单与反编译结果](32-classes.md)
- [handleAction分发模式](handle-action.md)
- [调用关系与Agent交互](call-graph-and-agent.md)
