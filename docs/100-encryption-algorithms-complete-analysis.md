# iFlyCode 加密算法完整逆向分析

> **目标版本**: iFlyCode 3.4.2-222 JetBrains Plugin
> **分析文件**: `agent/bin/index.js` (3.97MB webpack bundle)
> **分析日期**: 2026-05-14

---

## 1. 加密算法概览

iFlyCode Agent 共使用 **5 种加密算法**，分布在两个独立的加密子系统中：

### 1.1 业务加密子系统（模块 1618）

| 算法 | 类型 | 密钥长度 | 用途 | 实现模块 |
|------|------|----------|------|----------|
| RSA | 非对称加密 | 1024-bit | 登录凭据加密 | Node.js `crypto` (模块 76982) |
| SM2 | 非对称加密（国密） | 256-bit SM2 曲线 | 预留接口（当前无业务调用） | sm-crypto (模块 32214) |
| SM4 | 对称加密（国密） | 128-bit | 代码监控上报、权限缓存 | sm-crypto (模块 22920) |
| AES-256-CTR | 对称加密 | 256-bit + 128-bit IV | 消息加密（预留接口） | Node.js `crypto` (模块 76982) |
| MD5 | 哈希 | N/A | 缓存键生成、文件指纹 | Node.js `crypto` (模块 76982) |

### 1.2 数据库加密子系统（模块 63106）

| 算法 | 类型 | 用途 | 实现方式 |
|------|------|------|----------|
| DES3/AES/RC4 | 对称加密 | 数据库连接加密 | DH 密钥交换 + 对称加密 |
| MD5 | 哈希 | 消息完整性校验 | Node.js `crypto` |

### 1.3 模块依赖关系

```
模块 42135 (常量/密钥)
    ├── RSA_PUB_KEY
    ├── SM2_PUB_KEY
    ├── SM4_KEY
    ├── AES_KEY
    └── AES_IV
         ↓
模块 1618 (加密调度器)
    ├── g(76982) → Node.js crypto
    ├── g(4707)  → sm-crypto 入口
    │    ├── g(32214) → SM2 (doEncrypt/doDecrypt)
    │    │    ├── g(95947) → BigInteger
    │    │    ├── g(36965) → DER 编解码
    │    │    ├── g(61431) → EC 参数 / 工具函数
    │    │    └── g(83623) → SM3 哈希
    │    ├── g(97309) → SM3 (含 HMAC)
    │    │    └── g(83623) → SM3 核心
    │    └── g(22920) → SM4 (sms4Crypt/sms4KeyExt)
    └── g(42135) → 硬编码密钥常量
```

---

## 2. RSA 加密

### 2.1 完整实现代码

```javascript
// 模块 1618 - RSA 加密函数
function encryptRSA(d, E = S.RSA_PUB_KEY) {
    const g = Buffer.from(d, "utf8");
    const A = 64;  // 分块大小：64 字节
    const v = [];   // 明文分块数组
    const I = [];   // 密文分块数组

    // 将明文按 64 字节分块
    for (let d = 0; d < g.length; d += A) {
        v.push(g.slice(d, d + A));
    }

    // 对每个分块使用 RSA 公钥加密
    v.forEach((d) => {
        I.push(
            T.publicEncrypt(
                {
                    key: E,                                    // RSA 公钥 PEM
                    padding: T.constants.RSA_PKCS1_PADDING     // PKCS#1 v1.5 填充
                },
                d
            ).toString("base64")
        );
    });

    return I;  // 返回 Base64 编码的密文数组
}
```

### 2.2 公钥

```
已脱敏
已脱敏
已脱敏
s6KqHyjziBpHzjz9cQtvvEb8oT6ZvB2Ffsqr3JygMwDyPDHt0BmMo5CsuCvQvpmu
7o9Qf5mkSx2UFIxlGQIDAQAB
已脱敏
```

**公钥参数解析**：
- 算法：RSA
- 密钥长度：1024-bit（已脱敏 为 1024-bit RSA 公钥的 ASN.1 头）
已脱敏
- 公钥指数 (e)：65537 (0x10001，标准值)

### 2.3 加密参数

| 参数 | 值 | 说明 |
|------|------|------|
| 分块大小 | 64 字节 | RSA 1024-bit 最大明文长度 = 128 - 11(PKCS#1填充) = 117 字节；此处使用更保守的 64 字节 |
| 填充模式 | PKCS#1 v1.5 | `RSA_PKCS1_PADDING` |
| 输出格式 | Base64 字符串数组 | 每个分块独立编码 |
| 返回类型 | `string[]` | 数组，每元素为一个分块的 Base64 密文 |

### 2.4 调用点分析

#### 调用点 1：用户登录 - 账号密码加密

```javascript
// 模块位置：LoginService 类
async loginByAccount(d, E, g, T, A) {
    // T = 用户名（明文）
    // A = 密码（明文）
    const S = (0, V.encrypt)(T, "RSA")[0];  // 加密用户名，取第一个分块
    const v = (0, V.encrypt)(A, "RSA")[0];  // 加密密码，取第一个分块
    const I = await this.loginByForm(d, g, {
        user: S,      // RSA 加密后的用户名
        pwCode: v     // RSA 加密后的密码
    });
    // ...
}
```

**数据流**：
```
用户输入用户名/密码
    → encrypt(plaintext, "RSA")
    → encryptRSA(plaintext, RSA_PUB_KEY)
    → Buffer.from(plaintext, "utf8")
    → 按 64 字节分块
    → crypto.publicEncrypt({key, padding: PKCS1}, block)
    → Base64 编码
    → 取 [0]（第一个分块）
    → POST /api/usercenter/v1/user/common/login
        body: { user: encryptedUsername, pwCode: encryptedPassword }
        query: { clientId: randomId }
```

**API 端点**：`POST /api/usercenter/v1/user/common/login`

### 2.5 第二套 RSA 实现（数据库加密子系统）

```javascript
// 模块 29006 - RSACipher 类（用于数据库连接加密）
var v = function () {
    function r(d) {
        try {
            this.pubKey = A.readFileSync(d, "utf8");  // 从文件读取公钥
        } catch (d) {
            throw new Error("Init public cipher error: " + d);
        }
    }
    return r.prototype.encrypt = function (d) {
        try {
            return T.publicEncrypt({
                key: this.pubKey,
                padding: S.RSA_PKCS1_PADDING
            }, d);
        } catch (d) {
            throw new Error("Cert encrypt error: " + d);
        }
    }, r;
}();
E.RSACipher = v;
```

**区别**：此实现从文件系统读取公钥（非硬编码），用于数据库连接的加密通信。

---

## 3. SM2 加密

### 3.1 完整实现代码

#### 3.1.1 加密调度器接口

```javascript
// 模块 1618 - SM2 加密函数
function encryptSM2(d, E = S.SM2_PUB_KEY) {
    const g = Buffer.from(E, "base64").toString("hex");  // Base64 公钥 → Hex
    const T = A.sm2.doEncrypt(d, g, 1);                   // C1C3C2 模式（mode=1）
    return Buffer.from("04" + T, "hex").toString("base64"); // 添加未压缩前缀 "04"，转 Base64
}
```

#### 3.1.2 SM2 核心加密算法（模块 32214）

```javascript
const { BigInteger: T } = g(95947);
const { encodeDer: A, decodeDer: S } = g(36965);
const v = g(61431);  // EC 参数工具
const I = g(83623).sm3;  // SM3 哈希
const { G: R, curve: N, n: O } = v.generateEcparam();
const w = 0;  // C1C2C3 模式标志

function doEncrypt(d, E, g = 1) {
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
    const nextT = () => {
        x = I([...U, P >> 24 & 255, P >> 16 & 255, P >> 8 & 255, P & 255]);
        P++;
        B = 0;
    };
    nextT();

    // 7. C2 = M XOR t（密钥流异或）
    for (let E = 0, g = d.length; E < g; E++) {
        if (B === x.length) nextT();
        d[E] ^= x[B++] & 255;
    }
    const F = v.arrayToHex(d);

    // 8. 根据 mode 返回 C1C3C2 或 C1C2C3
    return g === w ? R + F + L : R + L + F;
    // mode=1 (C1C3C2): R + L + F = C1 || C3 || C2
    // mode=0 (C1C2C3): R + F + L = C1 || C2 || C3
}
```

#### 3.1.3 SM2 核心解密算法

```javascript
function doDecrypt(d, E, g = 1, { output: A = "string" } = {}) {
    // d = 密文(Hex), E = 私钥(Hex), g = 模式

    E = new T(E, 16);

    // 1. 解析密文：提取 C3 和 C2
    let S = d.substr(128, 64);       // C3 (SM3 哈希, 64 hex = 32 bytes)
    let R = d.substr(128 + 64);      // C2 (加密数据)

    if (g === w) {  // C1C2C3 模式
        S = d.substr(d.length - 64);
        R = d.substr(128, d.length - 128 - 64);
    }

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
    const nextT = () => {
        U = I([...F, B >> 24 & 255, B >> 16 & 255, B >> 8 & 255, B & 255]);
        B++;
        x = 0;
    };
    nextT();

    // 5. 解密: M = C2 XOR t
    for (let d = 0, E = N.length; d < E; d++) {
        if (x === U.length) nextT();
        N[d] ^= U[x++] & 255;
    }

    // 6. 验证: 计算 SM3(x2 || M || y2) 并与 C3 比较
    const G = v.arrayToHex(I([].concat(L, N, P)));
    if (G === S.toLowerCase()) {
        return A === "array" ? N : v.arrayToUtf8(N);
    } else {
        return A === "array" ? [] : "";
    }
}
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
function generateEcparam() {
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

    return { curve: A, G: R, n: N };
}
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
function encryptSM4(d, E = S.SM4_KEY) {
    const g = Buffer.from(E, "base64").toString("hex");  // Base64 密钥 → Hex
    const T = A.sm4.encrypt(d, g, { padding: "pkcs#5" }); // SM4 加密，PKCS#5 填充
    return Buffer.from(T, "hex").toString("base64");       // Hex 密文 → Base64
}

// 模块 1618 - SM4 解密函数
function decryptSM4(d, E = S.SM4_KEY) {
    const g = Buffer.from(E, "base64").toString("hex");  // Base64 密钥 → Hex
    const T = Buffer.from(d, "base64").toString("hex");   // Base64 密文 → Hex
    return A.sm4.decrypt(T, g);                            // SM4 解密，返回 UTF-8 明文
}
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
function rotl(d, E) {
    const g = E & 31;
    return d << g | d >>> 32 - g;
}

// S-Box 字节替换（32-bit 字）
function byteSub(d) {
    return (A[d >>> 24 & 255] & 255) << 24 |
           (A[d >>> 16 & 255] & 255) << 16 |
           (A[d >>> 8 & 255] & 255) << 8 |
           (A[d & 255] & 255);
}

// 线性变换 L（用于加密轮函数）
function l1(d) {
    return d ^ rotl(d, 2) ^ rotl(d, 10) ^ rotl(d, 18) ^ rotl(d, 24);
}

// 线性变换 L'（用于密钥扩展）
function l2(d) {
    return d ^ rotl(d, 13) ^ rotl(d, 23);
}

// SM4 单块加密/解密
function sms4Crypt(d, E, g) {
    // d = 输入(16字节), E = 输出(16字节), g = 轮密钥(32个32-bit字)
    const T = new Array(4);
    const A = new Array(4);

    // 字节转 32-bit 字
    for (let E = 0; E < 4; E++) {
        A[0] = d[4 * E] & 255;
        A[1] = d[4 * E + 1] & 255;
        A[2] = d[4 * E + 2] & 255;
        A[3] = d[4 * E + 3] & 255;
        T[E] = A[0] << 24 | A[1] << 16 | A[2] << 8 | A[3];
    }

    // 32 轮 Feistel 迭代
    for (let d = 0, E; d < 32; d += 4) {
        E = T[1] ^ T[2] ^ T[3] ^ g[d + 0];
        T[0] ^= l1(byteSub(E));
        E = T[2] ^ T[3] ^ T[0] ^ g[d + 1];
        T[1] ^= l1(byteSub(E));
        E = T[3] ^ T[0] ^ T[1] ^ g[d + 2];
        T[2] ^= l1(byteSub(E));
        E = T[0] ^ T[1] ^ T[2] ^ g[d + 3];
        T[3] ^= l1(byteSub(E));
    }

    // 反序输出
    for (let d = 0; d < 16; d += 4) {
        E[d]     = T[3 - d / 4] >>> 24 & 255;
        E[d + 1] = T[3 - d / 4] >>> 16 & 255;
        E[d + 2] = T[3 - d / 4] >>> 8 & 255;
        E[d + 3] = T[3 - d / 4] & 255;
    }
}

// SM4 密钥扩展
function sms4KeyExt(d, g, T) {
    // d = 密钥(16字节), g = 轮密钥输出(32个32-bit字), T = 模式(1=加密, 0=解密)
    const A = new Array(4);
    const v = new Array(4);

    // 密钥字节转 32-bit 字
    for (let E = 0; E < 4; E++) {
        v[0] = d[0 + 4 * E] & 255;
        v[1] = d[1 + 4 * E] & 255;
        v[2] = d[2 + 4 * E] & 255;
        v[3] = d[3 + 4 * E] & 255;
        A[E] = v[0] << 24 | v[1] << 16 | v[2] << 8 | v[3];
    }

    // 与系统参数 FK 异或
    A[0] ^= 2746333894;   // 0xA3B1BAC6
    A[1] ^= 1453994832;   // 0x56AA3350
    A[2] ^= 1736282519;   // 0x677D9197
    A[3] ^= 2993693404;   // 0xB27022DC

    // 32 轮密钥扩展
    for (let d = 0, E; d < 32; d += 4) {
        E = A[1] ^ A[2] ^ A[3] ^ S[d + 0];  // CK 常量
        g[d + 0] = A[0] ^= l2(byteSub(E));
        E = A[2] ^ A[3] ^ A[0] ^ S[d + 1];
        g[d + 1] = A[1] ^= l2(byteSub(E));
        E = A[3] ^ A[0] ^ A[1] ^ S[d + 2];
        g[d + 2] = A[2] ^= l2(byteSub(E));
        E = A[0] ^ A[1] ^ A[2] ^ S[d + 3];
        g[d + 3] = A[3] ^= l2(byteSub(E));
    }

    // 解密模式：反转轮密钥顺序
    if (T === E) {
        for (let d = 0, E; d < 16; d++) {
            E = g[d];
            g[d] = g[31 - d];
            g[31 - d] = E;
        }
    }
}

// SM4 主函数
function sm4(d, A, S, { padding: v = "pkcs#7", mode: I, iv: R = [], output: N = "string" } = {}) {
    // d = 数据, A = 密钥, S = 方向(1=加密, 0=解密)

    // CBC 模式 IV 校验
    if (I === "cbc") {
        if (typeof R === "string") R = hexToArray(R);
        if (R.length !== 128 / 8) throw new Error("iv is invalid");
    }

    // 密钥校验与转换
    if (typeof A === "string") A = hexToArray(A);
    if (A.length !== 128 / 8) throw new Error("key is invalid");

    // 数据转换
    if (typeof d === "string") {
        if (S !== E) { d = utf8ToArray(d); }   // 加密：UTF-8 → 字节数组
        else { d = hexToArray(d); }              // 解密：Hex → 字节数组
    } else { d = [...d]; }

    // PKCS#5/PKCS#7 填充（仅加密时）
    if ((v === "pkcs#5" || v === "pkcs#7") && S !== E) {
        const E = T - d.length % T;
        for (let g = 0; g < E; g++) d.push(E);
    }

    // 密钥扩展
    const O = new Array(g);
    sms4KeyExt(A, O, S);

    // 分组加密/解密
    const w = [];
    let D = R;  // CBC 前一组密文
    let L = d.length;
    let P = 0;

    while (L >= T) {
        const g = d.slice(P, P + 16);
        const A = new Array(16);

        // CBC 模式：加密前 XOR，解密后 XOR
        if (I === "cbc") {
            for (let d = 0; d < T; d++) {
                if (S !== E) { g[d] ^= D[d]; }  // 加密：明文 XOR 前密文
            }
        }

        sms4Crypt(g, A, O);

        if (I === "cbc") {
            for (let d = 0; d < T; d++) {
                if (S === E) { A[d] ^= D[d]; }  // 解密：密文 XOR 前密文
            }
        }

        for (let d = 0; d < T; d++) { w[P + d] = A[d]; }

        if (I === "cbc") {
            if (S !== E) { D = A; }    // 加密：更新 CBC 链为当前密文
            else { D = g; }            // 解密：更新 CBC 链为当前密文输入
        }

        L -= T;
        P += T;
    }

    // PKCS#5/PKCS#7 去填充（仅解密时）
    if ((v === "pkcs#5" || v === "pkcs#7") && S === E) {
        const d = w.length;
        const E = w[d - 1];
        for (let g = 1; g <= E; g++) {
            if (w[d - g] !== E) throw new Error("padding is invalid");
        }
        w.splice(d - E, E);
    }

    // 输出格式转换
    if (N !== "array") {
        if (S !== E) { return ArrayToHex(w); }     // 加密输出：Hex
        else { return arrayToUtf8(w); }              // 解密输出：UTF-8 字符串
    } else { return w; }
}

// 导出接口
d.exports = {
    encrypt(d, E, g) { return sm4(d, E, 1, g); },  // 加密
    decrypt(d, E, g) { return sm4(d, E, 0, g); }   // 解密
};
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
async permission(d) {
    const { enterpriseId: g } = this.client || {};
    const T = new B.default("permission", "user");  // IndexedDB 存储

    try {
        // 从服务器获取权限数据
        const A = await this.userService.getUserPermission(d, g);

        // SM4 加密后存入本地 IndexedDB
        await T.upsert(
            { user: E, info: (0, x.encryptSM4)(JSON.stringify(A)) },
            { user: E }
        );

        return A;
    } catch (d) {
        // 网络失败时，从本地缓存 SM4 解密读取
        const g = await T.get({ user: E });
        return (g?.info) ? JSON.parse((0, x.decryptSM4)(g.info)) : [];
    }
}
```

**数据流**：
```
服务器权限数据 (JSON)
    → JSON.stringify()
    → encryptSM4(jsonString, SM4_KEY)
    → Buffer.from(SM4_KEY, "base64").toString("hex")
    → sm4.encrypt(jsonString, hexKey, { padding: "pkcs#5" })
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
codeReportMediator(d, E) {
    const g = S.default.get(d.wsClientId);
    if (!g) return;

    const { user: T, token: A } = S.default.get(d.wsClientId);
    d.collectFrequency++;

    const N = {
        user: T,
        requestId: d.id,
        prefixCodeList: [(0, R.encrypt)(d.prefixCode, "SM4")],   // SM4 加密前缀代码
        completeCodeList: [(0, R.encrypt)(E, "SM4")],            // SM4 加密补全代码
        collectFrequency: d.collectFrequency
    };

    new I.default({ token: A }).codeMonitorReport({ id: d.id }, [N])
        .then(() => { v.default.info(`codeMonitorReport success`); })
        .catch((d) => { v.default.error(d); });
}
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
            body: { encryptMode: "SM4", codeCollectDtoList: [...] }
```

**API 端点**：`POST /api/starspark/v1/agent/collect/codeAccept`
**请求标记**：`encryptMode: "SM4"` — 告知服务端数据使用 SM4 加密

---

## 5. AES-256-CTR 加密

### 5.1 完整实现代码

```javascript
// 模块 1618 - AES 加密函数
function encryptAES(d, E = S.AES_KEY, g = S.AES_IV) {
    const A = T.createCipheriv(
        "aes-256-ctr",                    // 算法：AES-256 CTR 模式
        Buffer.from(E, "base64"),          // 密钥：Base64 → Buffer (32 字节)
        Buffer.from(g, "base64")           // IV：Base64 → Buffer (16 字节)
    );
    let v = A.update(d, "utf8", "base64"); // UTF-8 明文 → Base64 密文
    v += A.final("base64");                // 完成加密
    return v;                              // 返回 Base64 密文
}

// 模块 1618 - AES 解密函数
function decryptAES(d, E = S.AES_KEY, g = S.AES_IV) {
    const A = T.createDecipheriv(
        "aes-256-ctr",                     // 算法：AES-256 CTR 模式
        Buffer.from(E, "base64"),          // 密钥：Base64 → Buffer (32 字节)
        Buffer.from(g, "base64")           // IV：Base64 → Buffer (16 字节)
    );
    let v = A.update(d, "base64", "utf8"); // Base64 密文 → UTF-8 明文
    v += A.final("utf8");                  // 完成解密
    return v;                              // 返回 UTF-8 明文
}
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
function cryptoMd5(d) {
    return T.createHash("md5").update(d).digest("hex");
    // 输入：任意字符串
    // 输出：32 字符小写 Hex 字符串
}
```

### 6.2 调用点分析

#### 调用点 1：代码补全缓存键生成

```javascript
// 模块位置：codeGenerateMediator 函数
function codeGenerateMediator(d, E, g, N, O) {
    A.default.add(E.requestId, E.token);
    A.default.setCompleteStatus(E.requestId, 0);

    const { requestId: B, scene: x, model: U } = E;

    try {
        // 构造缓存键：prefix + suffix + language
        const T = { prefix: g, suffix: N, lang: E.language };

        // MD5 哈希作为缓存键
        const D = (0, I.cryptoMd5)((0, R.json2str)(T));

        // 检查缓存
        const F = S.default.get(D);
        if (F && E?.forcedTrigger === false) {
            v.default.attr(d.id, { "complete.cache": `代码补全命中缓存：${B}` });
            if (E.stream) {
                return w(Object.assign({}, F, { ended: true, fromCache: true }));
            }
            w(F);
        } else {
            // 缓存未命中，发起代码补全请求
            // ...
            S.default.set(D, N, 60 * 60 * 1000);  // 缓存 1 小时
        }
    }
}
```

**数据流**：
```
代码补全请求
    → 构造缓存键对象: { prefix, suffix, lang }
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

for (let g = 0; g < I; g++) {
    const T = A[g];
    if (d.command !== "CODE:COMMENT_RANGE") {
        const E = (T.params || []).map((d => d.text)).join(", ");
        this.sendData(d.id, {
            type: "method",
            data: Object.assign({}, T, {
                index: g + 1,
                total: I,
                md5: S,           // 文件内容的 MD5 哈希
                markdownName: (0, W.escapeHtml)(`${g > 0 ? "\n" : ""}函数名称：${T.name}(${E})\n`)
            })
        });
    }
}
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
function getFileMd5(d) {
    return new Promise((E) => {
        const g = T.existsSync(d);
        if (g) {
            const g = T.createReadStream(d);
            const A = S.createHash("md5");
            g.on("data", (d) => A.update(d, "utf8"));
            g.on("end", () => {
                const d = A.digest("hex");
                E(d);
            });
        } else {
            E("");
        }
    });
}
```

**用途**：计算文件 MD5 指纹，用于文件变更检测或去重。

---

## 7. 加密调用链追踪图

```
┌─────────────────────────────────────────────────────────────────────┐
│                        iFlyCode 加密调用链                           │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  ┌──────────────┐     encrypt(T,"RSA")[0]     ┌──────────────────┐ │
│  │  用户登录     │ ──────────────────────────→ │  RSA-1024        │ │
│  │  LoginService │    用户名 + 密码             │  PKCS#1 v1.5     │ │
│  └──────────────┘                              │  64-byte 分块    │ │
│        │                                       └────────┬─────────┘ │
│        │ loginByForm()                                  │           │
│        ▼                                                ▼           │
│  ┌──────────────────────────────────────────────────────────────┐  │
│  │  POST /api/usercenter/v1/user/common/login                  │  │
│  │  body: { user: RSA(username), pwCode: RSA(password) }       │  │
│  │  query: { clientId: randomId }                              │  │
│  └──────────────────────────────────────────────────────────────┘  │
│                                                                     │
│  ┌──────────────┐     encryptSM4(JSON)       ┌──────────────────┐ │
│  │  权限缓存     │ ─────────────────────────→ │  SM4-128         │ │
│  │  UserController│   权限 JSON → 加密存储     │  ECB 模式        │ │
│  └──────────────┘                              │  PKCS#5 填充     │ │
│        │                                       └────────┬─────────┘ │
│        │ IndexedDB upsert/get                            │           │
│        ▼                                                ▼           │
│  ┌──────────────────────────────────────────────────────────────┐  │
│  │  IndexedDB "permission"/"user"                              │  │
│  │  { user: username, info: SM4_Base64(permissions_json) }     │  │
│  └──────────────────────────────────────────────────────────────┘  │
│                                                                     │
│  ┌──────────────┐     encrypt(code,"SM4")   ┌──────────────────┐ │
│  │  代码监控     │ ─────────────────────────→ │  SM4-128         │ │
│  │  CodeMonitor  │   前缀代码 + 补全代码       │  ECB 模式        │ │
│  └──────────────┘                              │  PKCS#5 填充     │ │
│        │                                       └────────┬─────────┘ │
│        │ codeMonitorReport()                            │           │
│        ▼                                                ▼           │
│  ┌──────────────────────────────────────────────────────────────┐  │
│  │  POST /api/starspark/v1/agent/collect/codeAccept            │  │
│  │  { encryptMode: "SM4", codeCollectDtoList: [{               │  │
│  │      prefixCodeList: [SM4(prefix)],                         │  │
│  │      completeCodeList: [SM4(completion)]                    │  │
│  │  }] }                                                       │  │
│  └──────────────────────────────────────────────────────────────┘  │
│                                                                     │
│  ┌──────────────┐     cryptoMd5(json)           ┌──────────────────┐ │
│  │  代码补全缓存 │ ──────────────────────────→  │  MD5             │ │
│  │  GenerateMediator│  {prefix,suffix,lang}     │  128-bit         │ │
│  └──────────────┘                              │  Hex 输出        │ │
│                                                └────────┬─────────┘ │
│  ┌──────────────┐     cryptoMd5(content)                 │         │
│  │  代码注释     │ ──────────────────────────→            │         │
│  │  CommentHandler│  文件内容 MD5                        │         │
│  └──────────────┘                                        │         │
│                                                          ▼         │
│  ┌──────────────────────────────────────────────────────────────┐  │
│  │  内存缓存 Map / WebView 数据传输                             │  │
│  │  key: MD5(prefix+suffix+lang) → value: completion result   │  │
│  │  md5: MD5(file_content) → 文件版本标识                       │  │
│  └──────────────────────────────────────────────────────────────┘  │
│                                                                     │
│  ┌──────────────┐     [预留] encrypt(d,"SM2")  ┌──────────────────┐ │
│  │  SM2 接口     │ ──────────────────────────→ │  SM2-256         │ │
│  │  (未使用)     │                              │  C1C3C2 格式     │ │
│  └──────────────┘                              └──────────────────┘ │
│                                                                     │
│  ┌──────────────┐     [预留] encrypt(d,"AES")   ┌──────────────────┐ │
│  │  AES 接口     │ ──────────────────────────→ │  AES-256-CTR     │ │
│  │  (未使用)     │                              │  固定 IV         │ │
│  └──────────────┘                              └──────────────────┘ │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
```

---

## 8. 加密数据流图

### 8.1 RSA 登录加密数据流

```
┌──────────┐    ┌──────────────┐    ┌──────────────┐    ┌──────────────┐    ┌──────────┐
│ 用户输入  │    │ UTF-8 编码   │    │ 64B 分块     │    │ RSA PKCS#1   │    │ Base64   │
│ username │───→│ Buffer.from  │───→│ slice(0,64)  │───→│ publicEncrypt│───→│ encode   │──→ [0]
│ password │    │ ("utf8")     │    │ slice(64,128)│    │ (PUB_KEY,    │    │ per block│
└──────────┘    └──────────────┘    │ ...          │    │  PKCS1_PAD)  │    └──────────┘
                                    └──────────────┘    └──────────────┘
                                                                │
                                                                ▼
                                                    POST /api/usercenter/v1/user/common/login
                                                    { user: base64_rsa_username,
                                                      pwCode: base64_rsa_password }
                                                    ?clientId=randomId
```

### 8.2 SM4 权限缓存数据流

```
┌──────────────────┐    ┌──────────────┐    ┌──────────────┐    ┌──────────────┐    ┌──────────┐
│ 服务器权限数据    │    │ JSON 序列化  │    │ SM4 ECB      │    │ Hex → Base64 │    │IndexedDB │
│ getUserPermission│───→│ JSON.stringify│───→│ encrypt      │───→│ Buffer.from  │───→│ upsert   │
│ (API 响应)       │    │              │    │ (SM4_KEY,    │    │ (hex,"hex")  │    │          │
└──────────────────┘    └──────────────┘    │  pkcs#5)     │    │ .toString    │    └──────────┘
                                            └──────────────┘    │ ("base64")   │
                                                                └──────────────┘

┌──────────┐    ┌──────────────┐    ┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│IndexedDB │    │ Base64 → Hex │    │ SM4 ECB      │    │ PKCS#5 去填充│    │ JSON 反序列化│
│ get      │───→│ Buffer.from  │───→│ decrypt      │───→│ (自动)       │───→│ JSON.parse   │──→ 权限对象
│          │    │ (b64,"base64")│    │ (SM4_KEY)    │    │              │    │              │
└──────────┘    └──────────────┘    └──────────────┘    └──────────────┘    └──────────────┘
```

### 8.3 SM4 代码监控上报数据流

```
┌──────────────────┐    ┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│ 代码补全事件      │    │ 提取代码文本  │    │ SM4 ECB 加密 │    │ 构造上报数据  │
│ CodeMonitor      │───→│ prefixCode   │───→│ encrypt      │───→│ {            │
│ .codeReport()    │    │ completeCode │    │ (SM4_KEY,    │    │   encryptMode│
└──────────────────┘    └──────────────┘    │  pkcs#5)     │    │   :"SM4",    │
                                            └──────────────┘    │   prefixCode │
                                                                │   List:[SM4],│
                                                                │   completeCo │
                                                                │   deList:[SM4]│
                                                                │ }            │
                                                                └──────┬───────┘
                                                                       │
                                                                       ▼
                                                    POST /api/starspark/v1/agent/collect/codeAccept
```

### 8.4 MD5 缓存键数据流

```
┌──────────────────┐    ┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│ 代码补全请求      │    │ 构造键对象    │    │ JSON 序列化  │    │ MD5 哈希     │
│ codeGenerate     │───→│ {prefix,     │───→│ json2str()   │───→│ createHash   │──→ 32-char Hex
│ Mediator         │    │  suffix,     │    │              │    │ ("md5")      │    缓存键
└──────────────────┘    │  lang}       │    └──────────────┘    └──────────────┘
                        └──────────────┘
```

---

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
42135: (d, E) => {
    "use strict";
    Object.defineProperty(E, "__esModule", { value: true });

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
};
```

## 附录 B：加密调度器完整代码（模块 1618）

```javascript
1618: (d, E, g) => {
    "use strict";
    Object.defineProperty(E, "__esModule", { value: true });
    E.cryptoMd5 = E.decryptAES = E.encryptAES = E.encryptRSA =
        E.decryptSM4 = E.encryptSM4 = E.encryptSM2 = E.decrypt = E.encrypt = void 0;

    const T = g(76982);   // Node.js crypto
    const A = g(4707);    // sm-crypto (sm2, sm3, sm4)
    const S = g(42135);   // 硬编码密钥常量

    // 加密调度器
    function encrypt(d, E, ...g) {
        switch (E) {
            case "SM2": return encryptSM2(d, ...g);
            case "SM4": return encryptSM4(d, ...g);
            case "RSA": return encryptRSA(d, ...g);
            case "AES": return encryptAES(d, ...g);
            case "MD5": return cryptoMd5(d);
            default: return d;
        }
    }
    E.encrypt = encrypt;

    // 解密调度器
    function decrypt(d, E, ...g) {
        switch (E) {
            case "SM4": return decryptSM4(d, ...g);
            case "AES": return decryptAES(d, ...g);
            default: return d;
        }
    }
    E.decrypt = decrypt;

    // SM2 加密
    function encryptSM2(d, E = S.SM2_PUB_KEY) {
        const g = Buffer.from(E, "base64").toString("hex");
        const T = A.sm2.doEncrypt(d, g, 1);
        return Buffer.from("04" + T, "hex").toString("base64");
    }
    E.encryptSM2 = encryptSM2;

    // SM4 加密
    function encryptSM4(d, E = S.SM4_KEY) {
        const g = Buffer.from(E, "base64").toString("hex");
        const T = A.sm4.encrypt(d, g, { padding: "pkcs#5" });
        return Buffer.from(T, "hex").toString("base64");
    }
    E.encryptSM4 = encryptSM4;

    // SM4 解密
    function decryptSM4(d, E = S.SM4_KEY) {
        const g = Buffer.from(E, "base64").toString("hex");
        const T = Buffer.from(d, "base64").toString("hex");
        return A.sm4.decrypt(T, g);
    }
    E.decryptSM4 = decryptSM4;

    // RSA 加密
    function encryptRSA(d, E = S.RSA_PUB_KEY) {
        const g = Buffer.from(d, "utf8");
        const A = 64;
        const v = [];
        const I = [];
        for (let d = 0; d < g.length; d += A) {
            v.push(g.slice(d, d + A));
        }
        v.forEach((d) => {
            I.push(T.publicEncrypt({
                key: E,
                padding: T.constants.RSA_PKCS1_PADDING
            }, d).toString("base64"));
        });
        return I;
    }
    E.encryptRSA = encryptRSA;

    // AES 加密
    function encryptAES(d, E = S.AES_KEY, g = S.AES_IV) {
        const A = T.createCipheriv("aes-256-ctr",
            Buffer.from(E, "base64"), Buffer.from(g, "base64"));
        let v = A.update(d, "utf8", "base64");
        v += A.final("base64");
        return v;
    }
    E.encryptAES = encryptAES;

    // AES 解密
    function decryptAES(d, E = S.AES_KEY, g = S.AES_IV) {
        const A = T.createDecipheriv("aes-256-ctr",
            Buffer.from(E, "base64"), Buffer.from(g, "base64"));
        let v = A.update(d, "base64", "utf8");
        v += A.final("utf8");
        return v;
    }
    E.decryptAES = decryptAES;

    // MD5 哈希
    function cryptoMd5(d) {
        return T.createHash("md5").update(d).digest("hex");
    }
    E.cryptoMd5 = cryptoMd5;
};
```

## 附录 C：SM3 哈希算法完整代码（模块 83623）

```javascript
83623: d => {
    const E = new Uint32Array(68);  // 消息扩展 W
    const g = new Uint32Array(64);  // 消息扩展 W'

    function rotl(d, E) {
        const g = E & 31;
        return d << g | d >>> 32 - g;
    }

    function xor(d, E) {
        const g = [];
        for (let T = d.length - 1; T >= 0; T--)
            g[T] = (d[T] ^ E[T]) & 255;
        return g;
    }

    // 置换函数 P0
    function P0(d) {
        return d ^ rotl(d, 9) ^ rotl(d, 17);
    }

    // 置换函数 P1
    function P1(d) {
        return d ^ rotl(d, 15) ^ rotl(d, 23);
    }

    // SM3 哈希主函数
    function sm3(d) {
        let T = d.length * 8;           // 消息比特长度
        let A = T % 512;                // 填充计算
        A = A >= 448 ? 512 - A % 448 - 1 : 448 - A - 1;

        const S = new Array((A - 7) / 8);  // 填充 0
        const v = new Array(8);              // 长度编码
        for (let d = 0, E = S.length; d < E; d++) S[d] = 0;
        for (let d = 0, E = v.length; d < E; d++) v[d] = 0;

        // 长度编码为 64-bit big-endian
        T = T.toString(2);
        for (let d = 7; d >= 0; d--) {
            if (T.length > 8) {
                const E = T.length - 8;
                v[d] = parseInt(T.substr(E), 2);
                T = T.substr(0, E);
            } else if (T.length > 0) {
                v[d] = parseInt(T, 2);
                T = "";
            }
        }

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
        for (let d = 0; d < N; d++) {
            E.fill(0);
            g.fill(0);

            const T = 16 * d;
            for (let d = 0; d < 16; d++) {
                E[d] = R.getUint32((T + d) * 4, false);
            }

            // 消息扩展 W[16..67]
            for (let d = 16; d < 68; d++) {
                E[d] = P1(E[d - 16] ^ E[d - 9] ^ rotl(E[d - 3], 15))
                     ^ rotl(E[d - 13], 7) ^ E[d - 6];
            }

            // W'[0..63] = W[i] ^ W[i+4]
            for (let d = 0; d < 64; d++) {
                g[d] = E[d] ^ E[d + 4];
            }

            // 常量
            const A = 2043430169;   // T_j (0 <= j <= 15)
            const S = 2055708042;   // T_j (16 <= j <= 63)

            // 压缩函数
            let v = O[0], I = O[1], N = O[2], w = O[3];
            let D = O[4], L = O[5], P = O[6], B = O[7];
            let x, U, F, G, V;

            for (let d = 0; d < 64; d++) {
                V = d >= 0 && d <= 15 ? A : S;
                x = rotl(rotl(v, 12) + D + rotl(V, d), 7);
                U = x ^ rotl(v, 12);
                F = (d >= 0 && d <= 15 ? v ^ I ^ N : v & I | v & N | I & N)
                    + w + U + g[d];
                G = (d >= 0 && d <= 15 ? D ^ L ^ P : D & L | ~D & P)
                    + B + x + E[d];
                w = N; N = rotl(I, 9); I = v; v = F;
                B = P; P = rotl(L, 19); L = D; D = P0(G);
            }

            O[0] ^= v; O[1] ^= I; O[2] ^= N; O[3] ^= w;
            O[4] ^= D; O[5] ^= L; O[6] ^= P; O[7] ^= B;
        }

        // 输出
        const w = [];
        for (let d = 0, E = O.length; d < E; d++) {
            const E = O[d];
            w.push(
                (E & 4278190080) >>> 24,
                (E & 16711680) >>> 16,
                (E & 65280) >>> 8,
                E & 255
            );
        }
        return w;
    }

    // HMAC-SM3
    const T = 64;
    const A = new Uint8Array(T);
    const S = new Uint8Array(T);
    for (let d = 0; d < T; d++) { A[d] = 54; S[d] = 92; }

    function hmac(d, E) {
        if (E.length > T) E = sm3(E);
        while (E.length < T) E.push(0);
        const g = xor(E, A);
        const v = xor(E, S);
        const I = sm3([...g, ...d]);
        return sm3([...v, ...I]);
    }

    d.exports = { sm3: sm3, hmac: hmac };
};
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
