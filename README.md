# iFlyCode 逆向分析

> **🔔 重要通知：iFlyCode（星火飞码）产品已停止运营。官网 `iflycode.xfyun.cn` 已无法访问，API 后端已停服，插件已从 JetBrains 商店下架。**

---

## 📖 文档站

**所有分析文档都在这里 👇**

### https://vibe-coding-labs.github.io/iflycode-reverse-engineer/

---

## 项目说明

本项目对 iFlyCode 3.4.2-222（讯飞星火飞码 JetBrains 插件）进行通信协议逆向分析，仅供**学习研究**用途。

| 逆向成果 | 内容 |
|----------|------|
| Java 反编译 | 413 个 .java 文件，68 个包 |
| Agent Node.js | 1,156 webpack 模块，5 种加密算法 |
| WebView 前端 | 84 JS 文件，55 种 JS→Java 消息类型 |
| Velocity 模板 | 7 个测试框架模板 |
| H() 混淆破解 | XOR 周期密钥算法完全破解 |

## 免责声明

> **本仓库所有内容仅供技术学习和研究参考。**
>
> 1. 逆向分析内容版权归原厂商（安徽卓见科技有限公司/讯飞星火）所有
> 2. 如涉及侵权，请联系我们删除相关内容
> 3. 请勿将分析结果用于任何商业用途或非法目的
> 4. 使用者应遵守所在国家/地区的法律法规
>
> 如有任何问题或侵权投诉，请提交 [GitHub Issue](https://github.com/vibe-coding-labs/iflycode-reverse-engineer/issues)

## 关联项目

- [iflycode-proxy](https://github.com/vibe-coding-labs/iflycode-proxy) — 基于本逆向分析的 OpenAI/Anthropic 兼容代理