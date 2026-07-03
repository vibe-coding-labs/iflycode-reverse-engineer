# iFlyCode Enums Complete Decompilation Analysis

## Overview

This document provides a complete decompilation and analysis of all 31 enum classes found in the iFlyCode plugin, distributed across 4 packages:

| Package | Count | Classes |
|---------|-------|---------|
| `com.aicode.enums` | 24 | Core enums (status, language, UI, webview) |
| `com.aicode.agent.enums` | 5 | Agent communication enums |
| `com.aicode.inline.enums` | 3 | Inline Chat state machine enums |
| `com.aicode.apm.enums` | 2 | APM/telemetry enums |

All enum classes use string obfuscation via `H()` methods (e.g., `FontKt.H()`, `Maps.H()`, `AICodeStringUtil.H()`) to hide their internal string values. The obfuscated strings are decoded at runtime.

---

> **本文档已拆分为以下子页面：**

- [core enums包(24类)](core-enums.md)
- [Agent通信枚举](agent-enums.md)
- [Inline/Apm枚举与交叉引用](inline-apm-enums.md)
