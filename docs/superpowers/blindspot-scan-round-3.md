# iFlyCode 盲点扫描报告 - 第 3 轮

时间: 2026-07-04 16:50 UTC+8

## 本轮发现

### 🔴 1. API 端点清单存在巨大差异：doc 31 vs doc 22 vs doc 66

| 来源 | 端点数量 | 覆盖范围 |
|------|---------|---------|
| `docs/31-agent-binary-analysis.md` | **92 条** | 最完整（含 Cloud API + RAG + Knowledge） |
| `docs/22-agent-cloud-protocol/api-endpoints.md` | **46 条** | 少了很多端点 |
| `docs/66-agent-webpack-bundle-analysis.md` | **50 条** | 中等 |

**doc 31 独有的端点（doc 22/66 都遗漏的）：**

**知识库 CRUD（8 个端点，完全缺失）：**
- `POST /api/ragserver/v1/knowledge/base/create`
- `POST /api/ragserver/v1/knowledge/base/delete`
- `POST /api/ragserver/v1/knowledge/base/list`
- `POST /api/ragserver/v1/knowledge/base/update`
- `POST /api/ragserver/v1/knowledge/doc/delete`
- `POST /api/ragserver/v1/knowledge/doc/list`
- `POST /api/ragserver/v1/knowledge/doc/upload`
- `POST /api/ragserver/v1/knowledge/embedding`
- `POST /api/ragserver/v1/knowledge/search`

**Chat Completions 端点（2 个，doc 22 缺失）：**
- `POST /api/starspark/v1/agent/chat/completions`
- `POST /api/starspark/v1/agent/chat/completions/stream`

**需求分析系列（3 个，doc 22 缺失）：**
- `POST /api/starspark/v1/agent/demand/test`
- `POST /api/starspark/v1/agent/demand/analysis`
- `POST /api/starspark/v1/agent/demand/splitting`

**代码功能端点（多个，doc 22 改用 agent/code 前缀）：**
- `code/complete/stream`, `code/generate`, `code/generate/stream`, `code/check`, `code/comment`, `code/debug`, `code/explain`, `code/optimize`, `code/review`, `code/search`, `code/split`, `code/test`
- `agent/config`, `agent/git/review`, `agent/feedback`

**严重性：高。92 条 vs 46 条，翻倍差距。doc 31 提供了最完整的清单，doc 22 需要合并更新。**

### 🟡 2. 需求测试（Demand Test）无专用协议文档

虽然 `code_demand_test`, `code_demand_analysis`, `code_demand_split` 出现在命令参考（doc 06）和权限枚举中，但**没有任何一篇文档深入分析需求测试的协议格式、请求/响应结构、流程细节**。

相比之下，代码补全（doc 10）、SQL（doc 12）、单元测试（doc 13）、Git 评审（doc 14）等都有专用协议文档。

**严重性：中等。这是一个功能系统，但其协议层未被单独分析。**

### 🟡 3. 流程图生成（Mermaid/Flowchart）无协议级分析

Mermaid 流程图生成是在 v3.4.0 版本中加入的功能，webpack 中包含了巨大的 mermaid 解析模块（625KB+），但**没有任何文档分析流程图生成的协议流程**。

相关记录：
- docs/34-plugin-xml-analysis.md: v3.4.0 版本说明包含"流程图生成"
- docs/104-final-blindspot-elimination.md: `getChartTypeFromContent()` 函数
- docs/107-agent-webpack-modules-and-full-analysis.md: 5 个 mermaid 相关模块（总共 ~220KB）

但**请求/响应格式、触发流程、使用场景都没有分析文档**。

**严重性：中等。Mermaid 解析器已识别但协议层未覆盖。**

### 🟢 4. 知识库管理 CRUD 无分析文档

doc 31 揭示了 8 个 `ragserver/v1/knowledge/*` 端点，用于知识库的完整 CRUD 操作（创建、删除、列表、更新、文档上传、文档删除、文档列表、Embedding、搜索）。这些端点**完全不包含在 doc 22 或任何其他文档中**。

**严重性：中等。这是一个完整的子系统。**

### 🟢 5. 100 个"其他"H() 混淆字符串未归类

H() 解码结果中，"Other"类别占 53.1%（2,459/4,628 条）。虽然不影响核心功能理解，但意味着大量代码的语义仍未知。

**严重性：低。**

## 已确认/已排除

- 需求测试/分析/拆分已被确认是通过 `chat/completions` 或类似端点处理，而非独立端点协议——可能可通过命令系统推断
- 流程图功能可能通过 chat completions 端点 + 返回 mermaid 代码实现，协议层与聊天一致
- 知识库 CRUD 遵循 RESTful 模式，结构与已有的 RAG 端点类似
- 平台差异分析（doc 72）已覆盖完整

## 盲点跟踪表更新

| # | 盲点 | 状态 | 优先级 |
|---|------|------|--------|
| 1 | API 端点清单三套不统一 | 🔴 高 | **应合并** |
| 2 | 需求测试协议分析缺失 | 🟡 中 | 可补 |
| 3 | 流程图生成协议分析缺失 | 🟡 中 | 可补 |
| 4 | 知识库 CRUD 端点分析缺失 | 🟡 中 | 可补 |
| 5 | debugCode=9527 后门验证 | ✅ 产品下线，关闭 | — |
| 6 | 文档换代标注 | ✅ 开始轮中已标注 | — |
| 7 | 类清单覆盖范围 | 🟢 低 | 可标注意图 |

## 状态总结

| 指标 | 数值 |
|------|------|
| 本轮发现新盲点 | 4 个（1 高优先级，3 中优先级） |
| 本轮已解决/确认 | 2 个 |
| 累积待解决 | 3 个（均因产品下线无法进一步实物验证） |
| 文档补齐建议 | 合并 API 端点清单 + 补 Demand/Flowchart 协议分析 |