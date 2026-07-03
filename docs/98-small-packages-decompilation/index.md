# Small Packages Complete Decompilation Analysis

## Overview

This document provides complete decompilation and analysis of 9 small packages in the iFlyCode plugin, covering 22 classes total (including inner classes). These packages form the foundational infrastructure: request construction, exception handling, error/debug filtering, status management, icons, i18n, DTOs, and inlay completion hints.

### Package Inventory

| Package | Classes | Purpose |
|---------|---------|---------|
| `com.aicode.request` | 3 | Code completion request construction |
| `com.aicode.exception` | 2 | Request lifecycle exceptions |
| `com.aicode.error.search` | 3 | Debug filter for JVM exceptions |
| `com.aicode.status` | 3 | AI Code status management |
| `com.aicode.icons` | 1 | Icon constants registry |
| `com.aicode.message` | 1 | I18N message bundle |
| `com.aicode.dto` | 2 | Data transfer objects |
| `com.aicode.complete` | 4 | Inlay completion hint system |
| `com.aicode.enums` (ref) | 2 | AICodeStatus enum + switch table |

**Note**: The `com.aicode.search` package does not exist. The error/debug classes are in `com.aicode.error.search`. The message bundle is `BasicActionsBundle`, not `MessageBundle`. The DTO classes are `FileIndexDto` and `GitResponseDTO`, not `BizResponse`.

---

> **本文档已拆分为以下子页面：**

- [request/exception/error包](request-exception-error.md)
- [图标/DTO/Complete包](icons-dto-complete.md)
- [交叉分析与统计](cross-and-summary.md)
