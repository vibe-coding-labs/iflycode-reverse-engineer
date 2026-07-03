# iFlyCode H() 字符串解码全量结果

> 版本: 3.4.2-222 | 分析日期: 2026-05-13 | 解码工具: h_deobfuscator_final.py

## 1. 统计概览

| 指标 | 值 |
|------|-----|
| 扫描 .class 文件总数 | 566 |
| 含 H() 调用的类 | 279 |
| H() 调用总数 | 4628 |
| 高质量解码 (high) | 4114 |
| 中等质量解码 (medium) | 119 |
| 低质量解码 (low) | 0 |
| 垃圾/不可读 (garbage) | 395 |
| 无 v[] 密钥 | 0 |
| 可用解码率 (high+medium) | 91.5% |
| 含中文字符的解码条目 | 175 |

### 解码算法

```
output[i] = input[i] XOR v[(len-i-1) % 106 + 1]
```

每个 H() 定义类拥有独立的 v[] 序列（周期 106），共 33 个密钥类。

> **本文档已拆分为以下子页面：**

- [统计概览与解码结果](statistics-and-classes.md)
- [中文UI字符串与i18n交叉验证](chinese-strings.md)
- [未解码条目与关键发现](undecoded-and-summary.md)
