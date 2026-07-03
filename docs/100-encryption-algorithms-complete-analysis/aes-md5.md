## 5. AES-256-CTR 加密

### 5.1 完整实现代码

```javascript
// 模块 1618 - AES 加密函数
function encryptAES(d, E = S.AES_KEY, g = S.AES_IV) &#123;
    const A = T.createCipheriv(
        "aes-256-ctr",                    // 算法：AES-256 CTR 模式
        Buffer.from(E, "base64"),          // 密钥：Base64 → Buffer (32 字节)
        Buffer.from(g, "base64")           // IV：Base64 → Buffer (16 字节)
    );
    let v = A.update(d, "utf8", "base64"); // UTF-8 明文 → Base64 密文
    v += A.final("base64");                // 完成加密
    return v;                              // 返回 Base64 密文
&#125;

// 模块 1618 - AES 解密函数
function decryptAES(d, E = S.AES_KEY, g = S.AES_IV) &#123;
    const A = T.createDecipheriv(
        "aes-256-ctr",                     // 算法：AES-256 CTR 模式
        Buffer.from(E, "base64"),          // 密钥：Base64 → Buffer (32 字节)
        Buffer.from(g, "base64")           // IV：Base64 → Buffer (16 字节)
    );
    let v = A.update(d, "base64", "utf8"); // Base64 密文 → UTF-8 明文
    v += A.final("utf8");                  // 完成解密
    return v;                              // 返回 UTF-8 明文
&#125;
```

### 5.2 密钥与 IV

```
AES_KEY: 已脱敏
AES_IV:  已脱敏
```

**Hex 解码**：
- AES_KEY：`603DEB1015CA71BE2B73AEF0857D77811F352C073B6108D72D9810A30914DFF6`（32 字节 = 256-bit）
- AES_IV：`73AEF0857D77811F352C073B6108D72D`（16 字节 = 128-bit）

**注意**：AES_IV 是 AES_KEY 的子串！
```
AES_KEY: 603DEB1015CA71BE2B73AEF0857D77811F352C073B6108D72D9810A30914DFF6
AES_IV:                    73AEF0857D77811F352C073B6108D72D
                          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                          AES_KEY[12:28] 的 Hex 值与 AES_IV 完全一致
```

### 5.3 加密参数

| 参数 | 值 | 说明 |
|------|------|------|
| 算法 | AES-256-CTR | 256-bit 密钥，CTR 模式 |
| 密钥长度 | 256-bit (32 字节) | Base64 编码 |
| IV 长度 | 128-bit (16 字节) | Base64 编码 |
| 填充 | 无 | CTR 模式不需要填充 |
| 输入编码 | UTF-8 | 明文编码 |
| 输出编码 | Base64 | 密文编码 |

### 5.4 调用点分析

**当前状态**：AES-256-CTR 在 `encrypt`/`decrypt` 调度器中注册，但 **未发现任何业务代码实际调用** `encrypt(data, "AES")`、`decrypt(data, "AES")`、`encryptAES` 或 `decryptAES`。AES 为预留接口，可能用于 WebSocket 消息加密或未来版本。

---

## 6. MD5 哈希

### 6.1 完整实现代码

```javascript
// 模块 1618 - MD5 哈希函数
function cryptoMd5(d) &#123;
    return T.createHash("md5").update(d).digest("hex");
    // 输入：任意字符串
    // 输出：32 字符小写 Hex 字符串
&#125;
```

### 6.2 调用点分析

#### 调用点 1：代码补全缓存键生成

```javascript
// 模块位置：codeGenerateMediator 函数
function codeGenerateMediator(d, E, g, N, O) &#123;
    A.default.add(E.requestId, E.token);
    A.default.setCompleteStatus(E.requestId, 0);

    const &#123; requestId: B, scene: x, model: U &#125; = E;

    try &#123;
        // 构造缓存键：prefix + suffix + language
        const T = &#123; prefix: g, suffix: N, lang: E.language &#125;;

        // MD5 哈希作为缓存键
        const D = (0, I.cryptoMd5)((0, R.json2str)(T));

        // 检查缓存
        const F = S.default.get(D);
        if (F && E?.forcedTrigger === false) &#123;
            v.default.attr(d.id, &#123; "complete.cache": `代码补全命中缓存：$&#123;B&#125;` &#125;);
            if (E.stream) &#123;
                return w(Object.assign(&#123;&#125;, F, &#123; ended: true, fromCache: true &#125;));
            &#125;
            w(F);
        &#125; else &#123;
            // 缓存未命中，发起代码补全请求
            // ...
            S.default.set(D, N, 60 * 60 * 1000);  // 缓存 1 小时
        &#125;
    &#125;
&#125;
```

**数据流**：
```
代码补全请求
    → 构造缓存键对象: &#123; prefix, suffix, lang &#125;
    → JSON.stringify()
    → cryptoMd5(jsonString)
    → MD5 哈希 (32 字符 Hex)
    → 查询内存缓存 (Map)
        → 命中：返回缓存结果
        → 未命中：发起补全请求，结果缓存 1 小时
```

#### 调用点 2：方法内容哈希（代码注释场景）

```javascript
// 模块位置：代码注释处理
const S = (0, H.cryptoMd5)(E.content);  // E.content = 文件内容
const I = A.length;  // 方法数量

for (let g = 0; g < I; g++) &#123;
    const T = A[g];
    if (d.command !== "CODE:COMMENT_RANGE") &#123;
        const E = (T.params || []).map((d => d.text)).join(", ");
        this.sendData(d.id, &#123;
            type: "method",
            data: Object.assign(&#123;&#125;, T, &#123;
                index: g + 1,
                total: I,
                md5: S,           // 文件内容的 MD5 哈希
                markdownName: (0, W.escapeHtml)(`$&#123;g > 0 ? "\n" : ""&#125;函数名称：$&#123;T.name&#125;($&#123;E&#125;)\n`)
            &#125;)
        &#125;);
    &#125;
&#125;
```

**数据流**：
```
文件内容 (E.content)
    → cryptoMd5(content)
    → MD5 哈希
    → 作为 md5 字段发送给 WebView
    → 用于标识文件版本/内容唯一性
```

#### 调用点 3：文件 MD5 计算（独立实现）

```javascript
// 模块位置：文件工具函数
function getFileMd5(d) &#123;
    return new Promise((E) => &#123;
        const g = T.existsSync(d);
        if (g) &#123;
            const g = T.createReadStream(d);
            const A = S.createHash("md5");
            g.on("data", (d) => A.update(d, "utf8"));
            g.on("end", () => &#123;
                const d = A.digest("hex");
                E(d);
            &#125;);
        &#125; else &#123;
            E("");
        &#125;
    &#125;);
&#125;
```

**用途**：计算文件 MD5 指纹，用于文件变更检测或去重。

---
