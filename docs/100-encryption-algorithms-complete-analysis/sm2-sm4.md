## 3. SM2 加密

### 3.1 完整实现代码

#### 3.1.1 加密调度器接口

```javascript
// 模块 1618 - SM2 加密函数
function encryptSM2(d, E = S.SM2_PUB_KEY) &#123;
    const g = Buffer.from(E, "base64").toString("hex");  // Base64 公钥 → Hex
    const T = A.sm2.doEncrypt(d, g, 1);                   // C1C3C2 模式（mode=1）
    return Buffer.from("04" + T, "hex").toString("base64"); // 添加未压缩前缀 "04"，转 Base64
&#125;
```

#### 3.1.2 SM2 核心加密算法（模块 32214）

```javascript
const &#123; BigInteger: T &#125; = g(95947);
const &#123; encodeDer: A, decodeDer: S &#125; = g(36965);
const v = g(61431);  // EC 参数工具
const I = g(83623).sm3;  // SM3 哈希
const &#123; G: R, curve: N, n: O &#125; = v.generateEcparam();
const w = 0;  // C1C2C3 模式标志

function doEncrypt(d, E, g = 1) &#123;
    // d = 明文, E = 公钥(Hex), g = 模式(1=C1C3C2, 0=C1C2C3)

    // 1. 明文转字节数组
    d = typeof d === "string"
        ? v.hexToArray(v.utf8ToHex(d))
        : Array.prototype.slice.call(d);

    // 2. 解码公钥为椭圆曲线点
    E = v.getGlobalCurve().decodePointHex(E);

    // 3. 生成临时密钥对
    const A = v.generateKeyPairHex();
    const S = new T(A.privateKey, 16);
    let R = A.publicKey;
    if (R.length > 128) R = R.substr(R.length - 128);  // 取 x||y 部分

    // 4. 计算共享秘密点: (x2, y2) = E * S
    const N = E.multiply(S);
    const O = v.hexToArray(v.leftPad(N.getX().toBigInteger().toRadix(16), 64));
    const D = v.hexToArray(v.leftPad(N.getY().toBigInteger().toRadix(16), 64));

    // 5. 计算 C3 = SM3(x2 || M || y2)
    const L = v.arrayToHex(I([].concat(O, d, D)));

    // 6. KDF 密钥流生成: t = SM3(x2||y2 || counter)
    let P = 1;
    let B = 0;
    let x = [];
    const U = [].concat(O, D);
    const nextT = () => &#123;
        x = I([...U, P >> 24 & 255, P >> 16 & 255, P >> 8 & 255, P & 255]);
        P++;
        B = 0;
    &#125;;
    nextT();

    // 7. C2 = M XOR t（密钥流异或）
    for (let E = 0, g = d.length; E < g; E++) &#123;
        if (B === x.length) nextT();
        d[E] ^= x[B++] & 255;
    &#125;
    const F = v.arrayToHex(d);

    // 8. 根据 mode 返回 C1C3C2 或 C1C2C3
    return g === w ? R + F + L : R + L + F;
    // mode=1 (C1C3C2): R + L + F = C1 || C3 || C2
    // mode=0 (C1C2C3): R + F + L = C1 || C2 || C3
&#125;
```

#### 3.1.3 SM2 核心解密算法

```javascript
function doDecrypt(d, E, g = 1, &#123; output: A = "string" &#125; = &#123;&#125;) &#123;
    // d = 密文(Hex), E = 私钥(Hex), g = 模式

    E = new T(E, 16);

    // 1. 解析密文：提取 C3 和 C2
    let S = d.substr(128, 64);       // C3 (SM3 哈希, 64 hex = 32 bytes)
    let R = d.substr(128 + 64);      // C2 (加密数据)

    if (g === w) &#123;  // C1C2C3 模式
        S = d.substr(d.length - 64);
        R = d.substr(128, d.length - 128 - 64);
    &#125;

    const N = v.hexToArray(R);

    // 2. 解码 C1 为椭圆曲线点
    const O = v.getGlobalCurve().decodePointHex("04" + d.substr(0, 128));

    // 3. 计算共享秘密点: (x2, y2) = C1 * privateKey
    const D = O.multiply(E);
    const L = v.hexToArray(v.leftPad(D.getX().toBigInteger().toRadix(16), 64));
    const P = v.hexToArray(v.leftPad(D.getY().toBigInteger().toRadix(16), 64));

    // 4. KDF 密钥流生成（与加密相同）
    let B = 1;
    let x = 0;
    let U = [];
    const F = [].concat(L, P);
    const nextT = () => &#123;
        U = I([...F, B >> 24 & 255, B >> 16 & 255, B >> 8 & 255, B & 255]);
        B++;
        x = 0;
    &#125;;
    nextT();

    // 5. 解密: M = C2 XOR t
    for (let d = 0, E = N.length; d < E; d++) &#123;
        if (x === U.length) nextT();
        N[d] ^= U[x++] & 255;
    &#125;

    // 6. 验证: 计算 SM3(x2 || M || y2) 并与 C3 比较
    const G = v.arrayToHex(I([].concat(L, N, P)));
    if (G === S.toLowerCase()) &#123;
        return A === "array" ? N : v.arrayToUtf8(N);
    &#125; else &#123;
        return A === "array" ? [] : "";
    &#125;
&#125;
```

### 3.2 公钥

```
已脱敏
```

**Hex 解码**：
```
04225004EFF90C73909E8688409BA519699C9D0A2C63F19AF36495B402D5F304495B920397D3E885ABBFF161FB41A203DCBB25426ADC18374B2921E5D4A34
5ED0
```

- 前缀 `04`：未压缩点格式
- X 坐标：`225004EFF90C73909E8688409BA519699C9D0A2C63F19AF36495B402D5F30449`
- Y 坐标：`5B920397D3E885ABBFF161FB41A203DCBB25426ADC18374B2921E5D4A34`
- 注意：iFlyCode 的 `encryptSM2` 在调用 `doEncrypt` 前会去掉 `04` 前缀，加密后再添加回来

### 3.3 SM2 曲线参数（模块 61431）

```javascript
function generateEcparam() &#123;
    const d = new T("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF", 16);  // p
    const E = new T("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC", 16);  // a
    const g = new T("28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93", 16);  // b
    const A = new S(d, E, g);  // ECCurveFp

    // 基点 G
    const v = "32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7";  // Gx
    const I = "BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0";  // Gy
    const R = A.decodePointHex("04" + v + I);

    // 阶 n
    const N = new T("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123", 16);

    return &#123; curve: A, G: R, n: N &#125;;
&#125;
```

这些是 **SM2 国密标准曲线参数**（GM/T 0003-2012）。

### 3.4 加密参数

| 参数 | 值 | 说明 |
|------|------|------|
| 曲线 | SM2 推荐曲线 | GM/T 0003-2012 |
| 密文格式 | C1C3C2 | mode=1，符合国密标准 |
| KDF | SM3-based | 以 SM3 作为密钥派生函数 |
| 哈希 | SM3 | 用于计算 C3 和 KDF |
| 输出格式 | Base64 | 添加 "04" 前缀后转 Base64 |

### 3.5 调用点分析

**当前状态**：SM2 加密在 `encrypt` 调度器中注册，但 **未发现任何业务代码实际调用** `encrypt(data, "SM2")` 或 `encryptSM2`。SM2 为预留接口，可能用于未来版本或特定场景。

---

## 4. SM4 加密

### 4.1 完整实现代码

#### 4.1.1 加密调度器接口

```javascript
// 模块 1618 - SM4 加密函数
function encryptSM4(d, E = S.SM4_KEY) &#123;
    const g = Buffer.from(E, "base64").toString("hex");  // Base64 密钥 → Hex
    const T = A.sm4.encrypt(d, g, &#123; padding: "pkcs#5" &#125;); // SM4 加密，PKCS#5 填充
    return Buffer.from(T, "hex").toString("base64");       // Hex 密文 → Base64
&#125;

// 模块 1618 - SM4 解密函数
function decryptSM4(d, E = S.SM4_KEY) &#123;
    const g = Buffer.from(E, "base64").toString("hex");  // Base64 密钥 → Hex
    const T = Buffer.from(d, "base64").toString("hex");   // Base64 密文 → Hex
    return A.sm4.decrypt(T, g);                            // SM4 解密，返回 UTF-8 明文
&#125;
```

#### 4.1.2 SM4 核心算法（模块 22920）

```javascript
const E = 0;    // 解密模式标志
const g = 32;   // SM4 轮数
const T = 16;   // 分组大小（字节）

// SM4 S-Box（256 字节置换表）
const A = [
    214,144,233,254,204,225,61,183,22,182,20,194,40,251,44,5,
    43,103,154,118,42,190,4,195,170,68,19,38,73,134,6,153,
    156,66,80,244,145,239,152,122,51,84,11,67,237,207,172,98,
    228,179,28,169,201,8,232,149,128,223,148,250,117,143,63,166,
    71,7,167,252,243,115,23,186,131,89,60,25,230,133,79,168,
    104,107,129,178,113,100,218,139,248,235,15,75,112,86,157,53,
    30,36,14,94,99,88,209,162,37,34,124,59,1,33,120,135,
    212,0,70,87,159,211,39,82,76,54,2,231,160,196,200,158,
    234,191,138,210,64,199,56,181,163,247,242,206,249,97,21,161,
    224,174,93,164,155,52,26,85,173,147,50,48,245,140,177,227,
    29,246,226,46,130,102,202,96,192,41,35,171,13,83,78,111,
    213,219,55,69,222,253,142,47,3,255,106,114,109,108,91,81,
    141,27,175,146,187,221,188,127,17,217,92,65,31,16,90,216,
    10,193,49,136,165,205,123,189,45,116,208,18,184,229,180,176,
    137,105,151,74,12,150,119,126,101,185,241,9,197,110,198,132,
    24,240,125,236,58,220,77,32,121,238,95,62,215,203,57,72
];

// 系统参数 FK
const S = [
    462357, 472066609, 943670861, 1415275113,
    1886879365, 2358483617, 2830087869, 3301692121,
    3773296373, 4228057617, 404694573, 876298825,
    1347903077, 1819507329, 2291111581, 2762715833,
    3234320085, 3705924337, 4177462797, 337322537,
    808926789, 1280531041, 1752135293, 2223739545,
    2695343797, 3166948049, 3638552301, 4110090761,
    269950501, 741554753, 1213159005, 1684763257
];

// 循环左移
function rotl(d, E) &#123;
    const g = E & 31;
    return d << g | d >>> 32 - g;
&#125;

// S-Box 字节替换（32-bit 字）
function byteSub(d) &#123;
    return (A[d >>> 24 & 255] & 255) << 24 |
           (A[d >>> 16 & 255] & 255) << 16 |
           (A[d >>> 8 & 255] & 255) << 8 |
           (A[d & 255] & 255);
&#125;

// 线性变换 L（用于加密轮函数）
function l1(d) &#123;
    return d ^ rotl(d, 2) ^ rotl(d, 10) ^ rotl(d, 18) ^ rotl(d, 24);
&#125;

// 线性变换 L'（用于密钥扩展）
function l2(d) &#123;
    return d ^ rotl(d, 13) ^ rotl(d, 23);
&#125;

// SM4 单块加密/解密
function sms4Crypt(d, E, g) &#123;
    // d = 输入(16字节), E = 输出(16字节), g = 轮密钥(32个32-bit字)
    const T = new Array(4);
    const A = new Array(4);

    // 字节转 32-bit 字
    for (let E = 0; E < 4; E++) &#123;
        A[0] = d[4 * E] & 255;
        A[1] = d[4 * E + 1] & 255;
        A[2] = d[4 * E + 2] & 255;
        A[3] = d[4 * E + 3] & 255;
        T[E] = A[0] << 24 | A[1] << 16 | A[2] << 8 | A[3];
    &#125;

    // 32 轮 Feistel 迭代
    for (let d = 0, E; d < 32; d += 4) &#123;
        E = T[1] ^ T[2] ^ T[3] ^ g[d + 0];
        T[0] ^= l1(byteSub(E));
        E = T[2] ^ T[3] ^ T[0] ^ g[d + 1];
        T[1] ^= l1(byteSub(E));
        E = T[3] ^ T[0] ^ T[1] ^ g[d + 2];
        T[2] ^= l1(byteSub(E));
        E = T[0] ^ T[1] ^ T[2] ^ g[d + 3];
        T[3] ^= l1(byteSub(E));
    &#125;

    // 反序输出
    for (let d = 0; d < 16; d += 4) &#123;
        E[d]     = T[3 - d / 4] >>> 24 & 255;
        E[d + 1] = T[3 - d / 4] >>> 16 & 255;
        E[d + 2] = T[3 - d / 4] >>> 8 & 255;
        E[d + 3] = T[3 - d / 4] & 255;
    &#125;
&#125;

// SM4 密钥扩展
function sms4KeyExt(d, g, T) &#123;
    // d = 密钥(16字节), g = 轮密钥输出(32个32-bit字), T = 模式(1=加密, 0=解密)
    const A = new Array(4);
    const v = new Array(4);

    // 密钥字节转 32-bit 字
    for (let E = 0; E < 4; E++) &#123;
        v[0] = d[0 + 4 * E] & 255;
        v[1] = d[1 + 4 * E] & 255;
        v[2] = d[2 + 4 * E] & 255;
        v[3] = d[3 + 4 * E] & 255;
        A[E] = v[0] << 24 | v[1] << 16 | v[2] << 8 | v[3];
    &#125;

    // 与系统参数 FK 异或
    A[0] ^= 2746333894;   // 0xA3B1BAC6
    A[1] ^= 1453994832;   // 0x56AA3350
    A[2] ^= 1736282519;   // 0x677D9197
    A[3] ^= 2993693404;   // 0xB27022DC

    // 32 轮密钥扩展
    for (let d = 0, E; d < 32; d += 4) &#123;
        E = A[1] ^ A[2] ^ A[3] ^ S[d + 0];  // CK 常量
        g[d + 0] = A[0] ^= l2(byteSub(E));
        E = A[2] ^ A[3] ^ A[0] ^ S[d + 1];
        g[d + 1] = A[1] ^= l2(byteSub(E));
        E = A[3] ^ A[0] ^ A[1] ^ S[d + 2];
        g[d + 2] = A[2] ^= l2(byteSub(E));
        E = A[0] ^ A[1] ^ A[2] ^ S[d + 3];
        g[d + 3] = A[3] ^= l2(byteSub(E));
    &#125;

    // 解密模式：反转轮密钥顺序
    if (T === E) &#123;
        for (let d = 0, E; d < 16; d++) &#123;
            E = g[d];
            g[d] = g[31 - d];
            g[31 - d] = E;
        &#125;
    &#125;
&#125;

// SM4 主函数
function sm4(d, A, S, &#123; padding: v = "pkcs#7", mode: I, iv: R = [], output: N = "string" &#125; = &#123;&#125;) &#123;
    // d = 数据, A = 密钥, S = 方向(1=加密, 0=解密)

    // CBC 模式 IV 校验
    if (I === "cbc") &#123;
        if (typeof R === "string") R = hexToArray(R);
        if (R.length !== 128 / 8) throw new Error("iv is invalid");
    &#125;

    // 密钥校验与转换
    if (typeof A === "string") A = hexToArray(A);
    if (A.length !== 128 / 8) throw new Error("key is invalid");

    // 数据转换
    if (typeof d === "string") &#123;
        if (S !== E) &#123; d = utf8ToArray(d); &#125;   // 加密：UTF-8 → 字节数组
        else &#123; d = hexToArray(d); &#125;              // 解密：Hex → 字节数组
    &#125; else &#123; d = [...d]; &#125;

    // PKCS#5/PKCS#7 填充（仅加密时）
    if ((v === "pkcs#5" || v === "pkcs#7") && S !== E) &#123;
        const E = T - d.length % T;
        for (let g = 0; g < E; g++) d.push(E);
    &#125;

    // 密钥扩展
    const O = new Array(g);
    sms4KeyExt(A, O, S);

    // 分组加密/解密
    const w = [];
    let D = R;  // CBC 前一组密文
    let L = d.length;
    let P = 0;

    while (L >= T) &#123;
        const g = d.slice(P, P + 16);
        const A = new Array(16);

        // CBC 模式：加密前 XOR，解密后 XOR
        if (I === "cbc") &#123;
            for (let d = 0; d < T; d++) &#123;
                if (S !== E) &#123; g[d] ^= D[d]; &#125;  // 加密：明文 XOR 前密文
            &#125;
        &#125;

        sms4Crypt(g, A, O);

        if (I === "cbc") &#123;
            for (let d = 0; d < T; d++) &#123;
                if (S === E) &#123; A[d] ^= D[d]; &#125;  // 解密：密文 XOR 前密文
            &#125;
        &#125;

        for (let d = 0; d < T; d++) &#123; w[P + d] = A[d]; &#125;

        if (I === "cbc") &#123;
            if (S !== E) &#123; D = A; &#125;    // 加密：更新 CBC 链为当前密文
            else &#123; D = g; &#125;            // 解密：更新 CBC 链为当前密文输入
        &#125;

        L -= T;
        P += T;
    &#125;

    // PKCS#5/PKCS#7 去填充（仅解密时）
    if ((v === "pkcs#5" || v === "pkcs#7") && S === E) &#123;
        const d = w.length;
        const E = w[d - 1];
        for (let g = 1; g <= E; g++) &#123;
            if (w[d - g] !== E) throw new Error("padding is invalid");
        &#125;
        w.splice(d - E, E);
    &#125;

    // 输出格式转换
    if (N !== "array") &#123;
        if (S !== E) &#123; return ArrayToHex(w); &#125;     // 加密输出：Hex
        else &#123; return arrayToUtf8(w); &#125;              // 解密输出：UTF-8 字符串
    &#125; else &#123; return w; &#125;
&#125;

// 导出接口
d.exports = &#123;
    encrypt(d, E, g) &#123; return sm4(d, E, 1, g); &#125;,  // 加密
    decrypt(d, E, g) &#123; return sm4(d, E, 0, g); &#125;   // 解密
&#125;;
```

### 4.2 密钥

```
已脱敏
```

**Hex 解码**：`1978D0497946C38D91311E9ABF96336B`

- 密钥长度：128-bit（16 字节），符合 SM4 标准要求
- 编码方式：Base64

### 4.3 加密参数

| 参数 | 值 | 说明 |
|------|------|------|
| 密钥长度 | 128-bit | SM4 标准唯一密钥长度 |
| 分组大小 | 128-bit (16 字节) | SM4 标准分组 |
| 填充模式 | PKCS#5 | 实际等同于 PKCS#7（分组=16字节） |
| 工作模式 | ECB | 默认模式（未指定 mode 参数） |
| 输出格式 | Base64 | 加密后 Hex → Base64 |

### 4.4 调用点分析

#### 调用点 1：权限数据本地缓存（加密 + 解密）

```javascript
// 模块位置：UserController 类
async permission(d) &#123;
    const &#123; enterpriseId: g &#125; = this.client || &#123;&#125;;
    const T = new B.default("permission", "user");  // IndexedDB 存储

    try &#123;
        // 从服务器获取权限数据
        const A = await this.userService.getUserPermission(d, g);

        // SM4 加密后存入本地 IndexedDB
        await T.upsert(
            &#123; user: E, info: (0, x.encryptSM4)(JSON.stringify(A)) &#125;,
            &#123; user: E &#125;
        );

        return A;
    &#125; catch (d) &#123;
        // 网络失败时，从本地缓存 SM4 解密读取
        const g = await T.get(&#123; user: E &#125;);
        return (g?.info) ? JSON.parse((0, x.decryptSM4)(g.info)) : [];
    &#125;
&#125;
```

**数据流**：
```
服务器权限数据 (JSON)
    → JSON.stringify()
    → encryptSM4(jsonString, SM4_KEY)
    → Buffer.from(SM4_KEY, "base64").toString("hex")
    → sm4.encrypt(jsonString, hexKey, &#123; padding: "pkcs#5" &#125;)
    → sms4KeyExt() → 32 轮密钥
    → PKCS#5 填充
    → sms4Crypt() × N 块 (ECB 模式)
    → Hex 密文
    → Buffer.from(hex, "hex").toString("base64")
    → 存入 IndexedDB

IndexedDB 读取:
    → Base64 密文
    → Buffer.from(base64, "base64").toString("hex")
    → sm4.decrypt(hexCipher, hexKey)
    → sms4KeyExt(解密模式，反转轮密钥)
    → sms4Crypt() × N 块
    → PKCS#5 去填充
    → UTF-8 明文
    → JSON.parse()
    → 权限对象
```

**API 端点**：`POST /api/starspark/v1/agent/permission/queryUserPermissionPackageInfo`

#### 调用点 2：代码补全监控上报（加密）

```javascript
// 模块位置：CodeMonitor 类
codeReportMediator(d, E) &#123;
    const g = S.default.get(d.wsClientId);
    if (!g) return;

    const &#123; user: T, token: A &#125; = S.default.get(d.wsClientId);
    d.collectFrequency++;

    const N = &#123;
        user: T,
        requestId: d.id,
        prefixCodeList: [(0, R.encrypt)(d.prefixCode, "SM4")],   // SM4 加密前缀代码
        completeCodeList: [(0, R.encrypt)(E, "SM4")],            // SM4 加密补全代码
        collectFrequency: d.collectFrequency
    &#125;;

    new I.default(&#123; token: A &#125;).codeMonitorReport(&#123; id: d.id &#125;, [N])
        .then(() => &#123; v.default.info(`codeMonitorReport success`); &#125;)
        .catch((d) => &#123; v.default.error(d); &#125;);
&#125;
```

**数据流**：
```
代码补全事件触发
    → CodeMonitor.codeReport()
    → 读取文件内容
    → codeReportMediator(completion, code)
    → encrypt(prefixCode, "SM4")  → SM4 加密前缀代码
    → encrypt(completeCode, "SM4") → SM4 加密补全代码
    → 构造 codeCollectDtoList
    → LogService.codeMonitorReport()
        → POST /api/starspark/v1/agent/collect/codeAccept
            body: &#123; encryptMode: "SM4", codeCollectDtoList: [...] &#125;
```

**API 端点**：`POST /api/starspark/v1/agent/collect/codeAccept`
**请求标记**：`encryptMode: "SM4"` — 告知服务端数据使用 SM4 加密

---
