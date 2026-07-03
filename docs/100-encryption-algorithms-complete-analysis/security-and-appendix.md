## 9. 安全评估

### 9.1 密钥硬编码风险（严重）

**所有加密密钥均硬编码在客户端代码中**，这是最严重的安全问题：

| 密钥 | 硬编码位置 | 风险等级 |
|------|-----------|----------|
| RSA_PUB_KEY | 模块 42135 | 中（公钥可公开，但无法轮换） |
| SM2_PUB_KEY | 模块 42135 | 中（公钥可公开，但无法轮换） |
| SM4_KEY | 模块 42135 | **严重**（对称密钥泄露 = 加密失效） |
| AES_KEY | 模块 42135 | **严重**（对称密钥泄露 = 加密失效） |
| AES_IV | 模块 42135 | **严重**（固定 IV = CTR 模式完全失效） |

**具体影响**：
1. **SM4 密钥泄露**：任何人可解密权限缓存数据和代码监控上报数据
2. **AES 密钥+IV 泄露**：AES-256-CTR 使用固定 IV，密钥泄露后所有历史密文均可解密
3. **密钥无法轮换**：硬编码在客户端中，无法在不更新插件的情况下更换密钥

### 9.2 算法强度评估

| 算法 | 强度 | 评估 |
|------|------|------|
| RSA-1024 | 弱 | NIST 已不建议使用 1024-bit RSA，建议 >= 2048-bit |
| SM2-256 | 强 | 国密标准，等效于 ECC-256，安全强度足够 |
| SM4-128 | 强 | 国密标准，等效于 AES-128，安全强度足够 |
| AES-256-CTR | 强（算法）/ 弱（实现） | 算法本身安全，但固定 IV 导致 CTR 模式失效 |
| MD5 | 弱 | 已知碰撞攻击，不应用于安全场景 |

### 9.3 实现缺陷

#### 9.3.1 AES-256-CTR 固定 IV（严重）

```
AES_KEY: 603DEB1015CA71BE2B73AEF0857D77811F352C073B6108D72D9810A30914DFF6
AES_IV:                    73AEF0857D77811F352C073B6108D72D
```

CTR 模式下，相同的 Key+IV 组合会产生相同的密钥流。如果 IV 永远不变：
- 相同明文永远产生相同密文（确定性加密）
- 多条消息使用相同密钥流，已知明文攻击可恢复其他消息
- **违反 CTR 模式的基本安全前提**：IV 必须永不重复

此外，AES_IV 是 AES_KEY 的子串，进一步降低了密钥空间的独立性。

#### 9.3.2 SM4 使用 ECB 模式（中等）

ECB 模式对相同明文块产生相同密文块，存在以下风险：
- 相同的代码片段加密后产生相同密文，可被模式分析
- 无法检测密文重排或块替换攻击
- 建议使用 CBC 或 GCM 模式

#### 9.3.3 RSA 分块大小过于保守（低）

RSA-1024 使用 64 字节分块，而 PKCS#1 v1.5 填充后最大可加密 117 字节。这不影响安全性，但增加了密文体积和加密开销。

#### 9.3.4 MD5 用于缓存键（低）

MD5 用于缓存键生成和文件指纹，不涉及安全场景，碰撞风险可接受。但如果用于安全目的（如完整性校验），则不安全。

### 9.4 OWASP 相关性

| OWASP 类别 | 相关性 | 说明 |
|-------------|--------|------|
| A02:2021 - Cryptographic Failures | **高** | 硬编码密钥、弱算法(RSA-1024)、固定 IV |
| A05:2021 - Security Misconfiguration | **中** | ECB 模式、固定 IV |
| A07:2021 - Identification and Authentication Failures | **中** | RSA 加密仅保护传输，不提供端到端安全 |
| A08:2021 - Software and Data Integrity Failures | **低** | MD5 用于完整性标识 |

### 9.5 修复建议

1. **移除客户端硬编码密钥**：对称密钥应通过安全密钥交换协议获取
2. **升级 RSA 到 2048-bit+**：或使用 SM2 替代
3. **AES-CTR 必须使用随机 IV**：每次加密生成新的随机 IV，与密文一起传输
4. **SM4 使用 CBC 或 GCM 模式**：替代 ECB 模式
5. **实现密钥轮换机制**：支持在不更新客户端的情况下更换密钥
6. **MD5 替换为 SHA-256**：用于安全敏感场景的完整性校验

### 9.6 数据库加密子系统安全评估

数据库加密子系统（模块 63106/29006/65752）使用 DH 密钥交换 + 对称加密，设计相对合理：
- 使用 Diffie-Hellman 密钥交换生成会话密钥（非硬编码）
- 支持 DES3/AES-128/AES-192/AES-256/RC4 多种算法
- 包含 MD5 消息完整性校验
- RSA 公钥从文件读取（非硬编码）

**潜在问题**：
- DH 参数中 g=5（较小），p 为 512-bit（偏短）
- DEFAULT_IV 为硬编码的顺序字节序列 `[32,33,...,63,32]`
- RC4 已知不安全

---

## 附录 A：密钥常量模块完整代码（模块 42135）

```javascript
42135: (d, E) => &#123;
    "use strict";
    Object.defineProperty(E, "__esModule", &#123; value: true &#125;);

    // 加密密钥
    E.RSA_PUB_KEY = "已脱敏\r\n" +
        "已脱敏" +
已脱敏
        "s6KqHyjziBpHzjz9cQtvvEb8oT6ZvB2Ffsqr3JygMwDyPDHt0BmMo5CsuCvQvpmu" +
        "7o9Qf5mkSx2UFIxlGQIDAQAB\r\n" +
        "已脱敏";

    E.SM2_PUB_KEY = "已脱敏";

    E.SM4_KEY = "已脱敏";

    E.AES_KEY = "已脱敏";

    E.AES_IV = "已脱敏";

    // ... 其他常量（FILE_LANG, CODE_TEXT, PROJECT_FILE_TYPES 等）省略
&#125;;
```

## 附录 B：加密调度器完整代码（模块 1618）

```javascript
1618: (d, E, g) => &#123;
    "use strict";
    Object.defineProperty(E, "__esModule", &#123; value: true &#125;);
    E.cryptoMd5 = E.decryptAES = E.encryptAES = E.encryptRSA =
        E.decryptSM4 = E.encryptSM4 = E.encryptSM2 = E.decrypt = E.encrypt = void 0;

    const T = g(76982);   // Node.js crypto
    const A = g(4707);    // sm-crypto (sm2, sm3, sm4)
    const S = g(42135);   // 硬编码密钥常量

    // 加密调度器
    function encrypt(d, E, ...g) &#123;
        switch (E) &#123;
            case "SM2": return encryptSM2(d, ...g);
            case "SM4": return encryptSM4(d, ...g);
            case "RSA": return encryptRSA(d, ...g);
            case "AES": return encryptAES(d, ...g);
            case "MD5": return cryptoMd5(d);
            default: return d;
        &#125;
    &#125;
    E.encrypt = encrypt;

    // 解密调度器
    function decrypt(d, E, ...g) &#123;
        switch (E) &#123;
            case "SM4": return decryptSM4(d, ...g);
            case "AES": return decryptAES(d, ...g);
            default: return d;
        &#125;
    &#125;
    E.decrypt = decrypt;

    // SM2 加密
    function encryptSM2(d, E = S.SM2_PUB_KEY) &#123;
        const g = Buffer.from(E, "base64").toString("hex");
        const T = A.sm2.doEncrypt(d, g, 1);
        return Buffer.from("04" + T, "hex").toString("base64");
    &#125;
    E.encryptSM2 = encryptSM2;

    // SM4 加密
    function encryptSM4(d, E = S.SM4_KEY) &#123;
        const g = Buffer.from(E, "base64").toString("hex");
        const T = A.sm4.encrypt(d, g, &#123; padding: "pkcs#5" &#125;);
        return Buffer.from(T, "hex").toString("base64");
    &#125;
    E.encryptSM4 = encryptSM4;

    // SM4 解密
    function decryptSM4(d, E = S.SM4_KEY) &#123;
        const g = Buffer.from(E, "base64").toString("hex");
        const T = Buffer.from(d, "base64").toString("hex");
        return A.sm4.decrypt(T, g);
    &#125;
    E.decryptSM4 = decryptSM4;

    // RSA 加密
    function encryptRSA(d, E = S.RSA_PUB_KEY) &#123;
        const g = Buffer.from(d, "utf8");
        const A = 64;
        const v = [];
        const I = [];
        for (let d = 0; d < g.length; d += A) &#123;
            v.push(g.slice(d, d + A));
        &#125;
        v.forEach((d) => &#123;
            I.push(T.publicEncrypt(&#123;
                key: E,
                padding: T.constants.RSA_PKCS1_PADDING
            &#125;, d).toString("base64"));
        &#125;);
        return I;
    &#125;
    E.encryptRSA = encryptRSA;

    // AES 加密
    function encryptAES(d, E = S.AES_KEY, g = S.AES_IV) &#123;
        const A = T.createCipheriv("aes-256-ctr",
            Buffer.from(E, "base64"), Buffer.from(g, "base64"));
        let v = A.update(d, "utf8", "base64");
        v += A.final("base64");
        return v;
    &#125;
    E.encryptAES = encryptAES;

    // AES 解密
    function decryptAES(d, E = S.AES_KEY, g = S.AES_IV) &#123;
        const A = T.createDecipheriv("aes-256-ctr",
            Buffer.from(E, "base64"), Buffer.from(g, "base64"));
        let v = A.update(d, "base64", "utf8");
        v += A.final("utf8");
        return v;
    &#125;
    E.decryptAES = decryptAES;

    // MD5 哈希
    function cryptoMd5(d) &#123;
        return T.createHash("md5").update(d).digest("hex");
    &#125;
    E.cryptoMd5 = cryptoMd5;
&#125;;
```

## 附录 C：SM3 哈希算法完整代码（模块 83623）

```javascript
83623: d => &#123;
    const E = new Uint32Array(68);  // 消息扩展 W
    const g = new Uint32Array(64);  // 消息扩展 W'

    function rotl(d, E) &#123;
        const g = E & 31;
        return d << g | d >>> 32 - g;
    &#125;

    function xor(d, E) &#123;
        const g = [];
        for (let T = d.length - 1; T >= 0; T--)
            g[T] = (d[T] ^ E[T]) & 255;
        return g;
    &#125;

    // 置换函数 P0
    function P0(d) &#123;
        return d ^ rotl(d, 9) ^ rotl(d, 17);
    &#125;

    // 置换函数 P1
    function P1(d) &#123;
        return d ^ rotl(d, 15) ^ rotl(d, 23);
    &#125;

    // SM3 哈希主函数
    function sm3(d) &#123;
        let T = d.length * 8;           // 消息比特长度
        let A = T % 512;                // 填充计算
        A = A >= 448 ? 512 - A % 448 - 1 : 448 - A - 1;

        const S = new Array((A - 7) / 8);  // 填充 0
        const v = new Array(8);              // 长度编码
        for (let d = 0, E = S.length; d < E; d++) S[d] = 0;
        for (let d = 0, E = v.length; d < E; d++) v[d] = 0;

        // 长度编码为 64-bit big-endian
        T = T.toString(2);
        for (let d = 7; d >= 0; d--) &#123;
            if (T.length > 8) &#123;
                const E = T.length - 8;
                v[d] = parseInt(T.substr(E), 2);
                T = T.substr(0, E);
            &#125; else if (T.length > 0) &#123;
                v[d] = parseInt(T, 2);
                T = "";
            &#125;
        &#125;

        // 填充: message || 1bit || 0bits || length
        const I = new Uint8Array([...d, 128, ...S, ...v]);
        const R = new DataView(I.buffer, 0);
        const N = I.length / 64;  // 分组数

        // 初始值 IV
        const O = new Uint32Array([
            1937774191, 1226093241, 388252375, 3666478592,
            2842636476, 372324522, 3817729613, 2969243214
        ]);

        // 逐块压缩
        for (let d = 0; d < N; d++) &#123;
            E.fill(0);
            g.fill(0);

            const T = 16 * d;
            for (let d = 0; d < 16; d++) &#123;
                E[d] = R.getUint32((T + d) * 4, false);
            &#125;

            // 消息扩展 W[16..67]
            for (let d = 16; d < 68; d++) &#123;
                E[d] = P1(E[d - 16] ^ E[d - 9] ^ rotl(E[d - 3], 15))
                     ^ rotl(E[d - 13], 7) ^ E[d - 6];
            &#125;

            // W'[0..63] = W[i] ^ W[i+4]
            for (let d = 0; d < 64; d++) &#123;
                g[d] = E[d] ^ E[d + 4];
            &#125;

            // 常量
            const A = 2043430169;   // T_j (0 <= j <= 15)
            const S = 2055708042;   // T_j (16 <= j <= 63)

            // 压缩函数
            let v = O[0], I = O[1], N = O[2], w = O[3];
            let D = O[4], L = O[5], P = O[6], B = O[7];
            let x, U, F, G, V;

            for (let d = 0; d < 64; d++) &#123;
                V = d >= 0 && d <= 15 ? A : S;
                x = rotl(rotl(v, 12) + D + rotl(V, d), 7);
                U = x ^ rotl(v, 12);
                F = (d >= 0 && d <= 15 ? v ^ I ^ N : v & I | v & N | I & N)
                    + w + U + g[d];
                G = (d >= 0 && d <= 15 ? D ^ L ^ P : D & L | ~D & P)
                    + B + x + E[d];
                w = N; N = rotl(I, 9); I = v; v = F;
                B = P; P = rotl(L, 19); L = D; D = P0(G);
            &#125;

            O[0] ^= v; O[1] ^= I; O[2] ^= N; O[3] ^= w;
            O[4] ^= D; O[5] ^= L; O[6] ^= P; O[7] ^= B;
        &#125;

        // 输出
        const w = [];
        for (let d = 0, E = O.length; d < E; d++) &#123;
            const E = O[d];
            w.push(
                (E & 4278190080) >>> 24,
                (E & 16711680) >>> 16,
                (E & 65280) >>> 8,
                E & 255
            );
        &#125;
        return w;
    &#125;

    // HMAC-SM3
    const T = 64;
    const A = new Uint8Array(T);
    const S = new Uint8Array(T);
    for (let d = 0; d < T; d++) &#123; A[d] = 54; S[d] = 92; &#125;

    function hmac(d, E) &#123;
        if (E.length > T) E = sm3(E);
        while (E.length < T) E.push(0);
        const g = xor(E, A);
        const v = xor(E, S);
        const I = sm3([...g, ...d]);
        return sm3([...v, ...I]);
    &#125;

    d.exports = &#123; sm3: sm3, hmac: hmac &#125;;
&#125;;
```

## 附录 D：加密算法使用统计

| 算法 | 业务调用次数 | 调用场景 | 状态 |
|------|-------------|----------|------|
| RSA | 2 | 登录加密（用户名+密码） | 活跃 |
| SM2 | 0 | 无 | 预留 |
| SM4 加密 | 3 | 权限缓存(1) + 代码监控(2) | 活跃 |
| SM4 解密 | 1 | 权限缓存读取 | 活跃 |
| AES 加密 | 0 | 无 | 预留 |
| AES 解密 | 0 | 无 | 预留 |
| MD5 | 2 | 补全缓存键(1) + 文件哈希(1) | 活跃 |
| 文件 MD5 | 1 | 文件指纹 | 活跃（独立实现） |
