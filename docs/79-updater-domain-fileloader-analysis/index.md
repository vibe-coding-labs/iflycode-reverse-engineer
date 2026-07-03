# iFlyCode Updater/Domain/FileLoader 深入分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-13

## 1. 概述

本文档分析 iFlyCode 插件的三个核心包：

- **`com/aicode/updater/`** — 插件自动更新系统，8 个类
- **`com/aicode/domain/`** — 领域模型层，9 个类（含内部类共 10 个）
- **`com/aicode/template/fileloader/`** — 文件模板加载器，11 个类

这三个包分别负责：插件自身的热更新机制、代码补全/提示的核心数据模型、以及单元测试模板的文件加载与注册管理。

---

> **本文档已拆分为以下子页面：**

- [Updater包-插件自动更新系统](updater-package.md)
- [Domain领域模型层](domain-package.md)
- [FileLoader包分析](fileloader-package.md)
