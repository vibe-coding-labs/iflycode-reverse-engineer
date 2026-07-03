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
function encryptRSA(d, E = S.RSA_PUB_KEY) &#123;
    const g = Buffer.from(d, "utf8");
    const A = 64;  // 分块大小：64 字节
    const v = [];   // 明文分块数组
    const I = [];   // 密文分块数组

    // 将明文按 64 字节分块
    for (let d = 0; d < g.length; d += A) &#123;
        v.push(g.slice(d, d + A));
    &#125;

    // 对每个分块使用 RSA 公钥加密
    v.forEach((d) => &#123;
        I.push(
            T.publicEncrypt(
                &#123;
                    key: E,                                    // RSA 公钥 PEM
                    padding: T.constants.RSA_PKCS1_PADDING     // PKCS#1 v1.5 填充
                &#125;,
                d
            ).toString("base64")
        );
    &#125;);

    return I;  // 返回 Base64 编码的密文数组
&#125;
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
async loginByAccount(d, E, g, T, A) &#123;
    // T = 用户名（明文）
    // A = 密码（明文）
    const S = (0, V.encrypt)(T, "RSA")[0];  // 加密用户名，取第一个分块
    const v = (0, V.encrypt)(A, "RSA")[0];  // 加密密码，取第一个分块
    const I = await this.loginByForm(d, g, &#123;
        user: S,      // RSA 加密后的用户名
        pwCode: v     // RSA 加密后的密码
    &#125;);
    // ...
&#125;
```

**数据流**：
```
用户输入用户名/密码
    → encrypt(plaintext, "RSA")
    → encryptRSA(plaintext, RSA_PUB_KEY)
    → Buffer.from(plaintext, "utf8")
    → 按 64 字节分块
    → crypto.publicEncrypt(&#123;key, padding: PKCS1&#125;, block)
    → Base64 编码
    → 取 [0]（第一个分块）
    → POST /api/usercenter/v1/user/common/login
        body: &#123; user: encryptedUsername, pwCode: encryptedPassword &#125;
        query: &#123; clientId: randomId &#125;
```

**API 端点**：`POST /api/usercenter/v1/user/common/login`

### 2.5 第二套 RSA 实现（数据库加密子系统）

```javascript
// 模块 29006 - RSACipher 类（用于数据库连接加密）
var v = function () &#123;
    function r(d) &#123;
        try &#123;
            this.pubKey = A.readFileSync(d, "utf8");  // 从文件读取公钥
        &#125; catch (d) &#123;
            throw new Error("Init public cipher error: " + d);
        &#125;
    &#125;
    return r.prototype.encrypt = function (d) &#123;
        try &#123;
            return T.publicEncrypt(&#123;
                key: this.pubKey,
                padding: S.RSA_PKCS1_PADDING
            &#125;, d);
        &#125; catch (d) &#123;
            throw new Error("Cert encrypt error: " + d);
        &#125;
    &#125;, r;
&#125;();
E.RSACipher = v;
```

**区别**：此实现从文件系统读取公钥（非硬编码），用于数据库连接的加密通信。

---
