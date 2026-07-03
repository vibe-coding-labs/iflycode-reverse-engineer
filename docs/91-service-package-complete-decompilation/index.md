# 91. Service 包完整反编译分析

## 概述

`com.aicode.service` 包是 iFlyCode 插件代码补全子系统的核心，包含 35 个类文件（34 个逻辑类 + 1 个匿名内部类），分布在三个子包中：

- `com.aicode.service` — 17 个顶层接口/类
- `com.aicode.service.editor` — 16 个实现类
- `com.aicode.service.response` — 1 个 DTO 类

源文件名均被混淆为单字母/双字母（如 `r`, `ec`, `zc`, `oc` 等）。

---

> **本文档已拆分为以下子页面：**

- [顶层接口/类完整反编译](top-level-interfaces.md)
- [Editor子包实现类](editor-impl.md)
- [EditorManager与补全生命周期](editor-manager-lifecycle.md)
- [Inlay渲染与Service层交互](inlay-and-interaction.md)
