# iFlyCode 盲点扫描报告 - 第 2 轮

时间: 2026-07-04 16:40 UTC+8

## 本轮发现

### 1. API 端点计数不一致：doc 22 vs doc 66

| 文档 | 端点计数 | 差异 |
|------|---------|------|
| `docs/22-agent-cloud-protocol/api-endpoints.md` | 46 个 | 不含 RAG 服务、telemetry 等 |
| `docs/66-agent-webpack-bundle-analysis.md` | 50 个 | 含 `/restapi/ragserver/` 等 |

**差异分析**：doc 22 按 service 分类列了 46 个，doc 66 从 webpack bundle 直接提取了 50 个路由。多出的 4 个端点可能来自 RAG 搜索和 telemetry 相关 API。两套清单不完全对齐。

**严重性**：中等。应统一到同一份 API 端点清单，标注来源。

### 2. 动作系统计数不一致：doc 24 vs doc 47 vs doc 84

| 文档 | Action 条目数 |
|------|-------------|
| `docs/24-action-system.md` | 24 个 action 条目 |
| `docs/47-action-system-complete.md` | 28 个 action 条目 |
| `docs/84-action-package-complete/` (全部子文件) | ~55 个 table 条目 |

**差异分析**：doc 24 是早期分析（仅 11 个核心 action），doc 47 增加到 28 个，doc 84 反编译后列出了完整清单（~55 个类）。这是正常的分析递进，但应注明 doc 24/47 已被 doc 84 取代。

**严重性**：低。属于分析的渐进式更新。

### 3. WebView 消息类型计数：doc 07 vs doc 65 vs doc 102

| 文档 | JS Bridge 消息数 |
|------|----------------|
| `docs/07-webview-bridge.md` | 124 种消息类型 |
| `docs/65-webview-frontend-complete-analysis.md` | 145 种 |
| `docs/102-webview-protocol-encryption-analysis/js-bridge.md` | 144 种 |

**差异分析**：doc 07 是较早文档，遗漏了部分消息类型。doc 65 和 doc 102 的计数接近（145 vs 144，差 1 条），基本一致。建议标注 doc 07 已被 doc 65/102 取代。

**严重性**：低。分析的渐进式更新。

### 4. 反编译代码清单：doc 36 vs doc 106

| 文档 | 统计范围 | 计数 |
|------|---------|------|
| `docs/36-complete-class-inventory.md` | Java 类 | 215+ 类（含全部子类） |
| `docs/106-agent-webpack-modules-and-full-class-inventory.md` | Webpack 模块+Java 类 | 142 类完整清单（不含内部类） |

**差异分析**：doc 36 包含嵌套类和内部类（计数更高），doc 106 只统计顶层类。doc 106 明确自称"完整类清单 v2，doc 36 的完整替换版"。应确认是否所有 doc 36 的类都包含在 doc 106 中。

**严重性**：中等。如果 doc 106 遗漏了 doc 36 中的某些类，则存在覆盖缺口。

### 5. H() 混淆分析状态需要统一汇总

| 相关文档 | 篇幅 | 状态 |
|---------|------|------|
| `docs/21-obfuscation.md` | 早期分析 | 总论 |
| `docs/29-obfuscated-strings.md` | 混淆字符串表 | 200+ 条推测性描述 |
| `docs/64-h-deobfuscation-analysis.md` | 算法分析 | 完整算法破解 |
| `docs/67-H-deobfuscation-solution.md` | 解决方案 | 解码器使用说明 |
| `docs/80-h-deobfuscation-complete-results/` | 完整结果(7 子文件) | 4,628 次调用结果 |

**差异分析**：4,628 次 H()调用分布在 7 个文件中（总计 522+101+12+20+15+165 = 835 条表格行），但"其他"类别占 53.1%（2,459 条），说明大量调用尚未归类。

**严重性**：低。核心算法已破解，未归类条目不影响主体分析。

## 已确认/已排除

- **纯分析递进非矛盾**：doc 24→doc 47→doc 84 是动作系统分析的三个版本，非矛盾
- **API 端点**：doc 22 按 service 分类，doc 66 从 webpack 直接提取，互补非冲突
- **类清单**：doc 36 含内部类（215+），doc 106 仅顶层类（142），范围不同
- **H() 算法**：完全破解，无需进一步分析

## 盲点跟踪表

| # | 盲点 | 状态 |
|---|------|------|
| 1 | debugCode=9527 后门实物验证 | 待验证（产品已下线，无法验证） |
| 2 | FeatureProbe SDK 用途 | 已确认为未打包的废弃依赖 ✅ |
| 3 | Agent 二进制 89MB 原因 | 推测已足够（调试符号） |
| 4 | API 端点清单统一 | ⚠️ 建议 doc 22 与 doc 66 合并 |
| 5 | OTel 开关验证 | 产品已下线，无法验证 |
| 6 | NeDB→SQLite 迁移触发条件 | 仍不明，但已无关 |
| 7 | 类清单 doc 106 是否完全覆盖 doc 36 | ⚠️ 建议交叉验证 |
| 8 | 文档换代标注 | doc 24/36/07 已分别被 doc 84/106/65 取代，需添加标注 |

## 状态总结

| 指标 | 数值 |
|------|------|
| 本轮发现真实盲点 | 3 个（端点和类清单统一、文档换代标注） |
| 非盲点（分析递进差异） | 5 个 |
| 本轮已解决 | 2 个（FeatureProbe、产品下线导致无法验证的点） |
| 待解决问题的趋势 | 持续下降中 |