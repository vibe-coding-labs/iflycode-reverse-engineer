# Doc 95: test Package Complete Decompilation & Analysis

## Overview

The `com.aicode.test` package implements the **unit test generation system** for iFlyCode. It contains 28 classes organized into 3 layers:

- **Service Layer** (4 classes): UnitTestService, BatchUnitTestService, CppTestService + inner classes
- **DTO Layer** (19 classes): Nested data transfer objects for test generation requests/responses
- **UI Layer** (1 class): UnitTestDialog for user configuration

**Total bytecode size**: ~120KB (UnitTestService alone is 66KB)

---

> **本文档已拆分为以下子页面：**

- [类清单与DTO结构](class-inventory.md)
- [Service类与单测生成流程](service-and-flow.md)
- [对话框与混淆分析](dialog-and-obfuscation.md)
- [Template交互与I/O操作](template-and-io.md)
