# iFlyCode Agent Webpack Bundle 完整分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-11 | 文档编号: 66

## 1. 概述

本文档详细分析 iFlyCode Agent 的 webpack bundle（index.js，3.6 MB），包括加密体系、API 路由、数据库架构、Tree-sitter 解析器、WebSocket 通信和中文错误消息。

## 2. 基本信息

| 属性 | 值 |
|------|-----|
| 包名 | iflycode-agent |
| 版本 | 3.4.2 |
| Bundle 大小 | 3.6 MB (index.js) + 976 KB (worker.js) |
| Node 版本要求 | <=12 |
| 入口 | bin/index.js |
| 配置 | bin/config.json |

### 2.1 配置文件 (config.json)

```json
{
  "agent.version": "3.4.2",
  "agent.wasmCheck": 10,
  "agent.url": "https://iflycode-xfsaas.xfyun.cn",
  "agent.update": true
}
```

### 2.2 平台特定 Node 二进制

| 文件 | 大小 | 平台 |
|------|------|------|
| x86_64_darwin_node | 93 MB | macOS x86_64 |
| x86_64_darwin_arm_node | 89 MB | macOS ARM64 |
| x86_64_linux_node | 92 MB | Linux x86_64 |
| x86_64_windows_node.exe | 71 MB | Windows x86_64 |
| x86_64_windows7_node.exe | 30 MB | Windows 7 |

## 3. 加密体系完整分析

### 3.1 加密模块架构

```
encrypt(data, type, ...args)
    │
    ├── switch(type):
    │   ├── "SM2" → encryptSM2(data, key)
    │   ├── "SM4" → encryptSM4(data, key)
    │   ├── "RSA" → encryptRSA(data, key)
    │   ├── "AES" → encryptAES(data, key, iv)
    │   └── "MD5" → cryptoMd5(data)
    │
    └── decrypt(data, type, ...args)
        ├── "SM4" → decryptSM4(data, key)
        ├── "AES" → decryptAES(data, key, iv)
        └── default → return data
```

### 3.2 硬编码密钥（全部从源码提取）

| 密钥 | 值 | 用途 |
|------|-----|------|
| RSA_PUB_KEY | `-----BEGIN PUBLIC KEY-----\r\nMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCFMVCHyq4CNE0sHQj5O3o6SFxo5yKK6/tpOC/zbpcomixQ17X7BBccZPyDcruIUkfNhlAeQHxFDn2NCOn2zdm3+6kes6KqHyjziBpHzjz9cQtvvEb8oT6ZvB2Ffsqr3JygMwDyPDHt0BmMo5CsuCvQvpmu7o9Qf5mkSx2UFIxlGQIDAQAB\r\n-----END PUBLIC KEY-----` | RSA 加密（1024-bit） |
| SM2_PUB_KEY | `BCJQBO/6DHOQnoaIQJulGWmcnQosY/Ga82SVtALV8wRJW5IDl9Pohau/8WH7QaID3LslQmrcGDdLKkh5dSg0XtA=` | 国密 SM2 加密 |
| SM4_KEY | `GXjQSXlGw42RMR6av5Yzaw==` | 国密 SM4 加密 |
| AES_KEY | `YD3rEBXKcb4rc67whX13gR81LAc7YQjXLZgQowkU3/Q=` | AES-256-CTR 加密 |
| AES_IV | `c67whX13gR81LAc7YQjXLQ==` | AES-256-CTR IV |

### 3.3 各加密算法实现

#### encryptRSA

```javascript
function encryptRSA(data, key = RSA_PUB_KEY) {
  const buf = Buffer.from(data, "utf8");
  const chunkSize = 64;  // 64字节分块
  const chunks = [];
  const results = [];
  // 分块加密（RSA 只能加密有限长度数据）
  for (let i = 0; i < buf.length; i += chunkSize) {
    chunks.push(buf.slice(i, i + chunkSize));
  }
  chunks.forEach(chunk => {
    results.push(
      crypto.publicEncrypt(
        { key, padding: crypto.constants.RSA_PKCS1_PADDING },
        chunk
      ).toString("base64")
    );
  });
  return results;  // 返回数组（每块一个 base64 字符串）
}
```

**关键**: RSA 使用 PKCS1_PADDING，64 字节分块加密，返回 base64 数组。

#### encryptSM2

```javascript
function encryptSM2(data, key = SM2_PUB_KEY) {
  const hexKey = Buffer.from(key, "base64").toString("hex");
  const encrypted = smCrypto.sm2.doEncrypt(data, hexKey, 1);  // mode=1 (C1C3C2)
  return Buffer.from("04" + encrypted, "hex").toString("base64");
}
```

**关键**: SM2 使用 `sm-crypto` 库，mode=1（C1C3C2 模式），结果前缀 "04"。

#### encryptSM4

```javascript
function encryptSM4(data, key = SM4_KEY) {
  const hexKey = Buffer.from(key, "base64").toString("hex");
  const encrypted = smCrypto.sm4.encrypt(data, hexKey, { padding: "pkcs#5" });
  return Buffer.from(encrypted, "hex").toString("base64");
}
```

**关键**: SM4 使用 PKCS#5 填充。

#### encryptAES

```javascript
function encryptAES(data, key = AES_KEY, iv = AES_IV) {
  const cipher = crypto.createCipheriv(
    "aes-256-ctr",
    Buffer.from(key, "base64"),
    Buffer.from(iv, "base64")
  );
  let result = cipher.update(data, "utf8", "base64");
  result += cipher.final("base64");
  return result;
}
```

**关键**: AES-256-CTR 模式，密钥和 IV 都是 base64 编码的硬编码值。

#### decryptSM4

```javascript
function decryptSM4(data, key = SM4_KEY) {
  const hexKey = Buffer.from(key, "base64").toString("hex");
  const hexData = Buffer.from(data, "base64").toString("hex");
  return smCrypto.sm4.decrypt(hexData, hexKey);
}
```

#### decryptAES

```javascript
function decryptAES(data, key = AES_KEY, iv = AES_IV) {
  const decipher = crypto.createDecipheriv(
    "aes-256-ctr",
    Buffer.from(key, "base64"),
    Buffer.from(iv, "base64")
  );
  let result = decipher.update(data, "base64", "utf8");
  result += decipher.final("utf8");
  return result;
}
```

### 3.4 加密使用场景

| 场景 | 加密类型 | 说明 |
|------|---------|------|
| 登录（用户名+密码） | RSA | `encrypt(username, "RSA")[0]` + `encrypt(password, "RSA")[0]` |
| 权限数据缓存 | SM4 | `encryptSM4(JSON.stringify(permissions))` |
| 代码补全数据上报 | SM4 | `encrypt(prefixCode, "SM4")` + `encrypt(completeCode, "SM4")` |
| 消息加密 | AES | `encryptAES(message, AES_KEY, AES_IV)` |

### 3.5 安全性评估

| 维度 | 评估 | 说明 |
|------|------|------|
| **密钥硬编码** | 严重 | RSA、SM2、SM4、AES 密钥全部硬编码在 bundle 中 |
| **RSA 1024-bit** | 弱 | 1024-bit RSA 已被认为不安全，现代标准要求 2048-bit+ |
| **AES-256-CTR** | 中等 | 算法本身安全，但 IV 硬编码导致相同密钥+IV 加密不同数据 |
| **SM2/SM4** | 中等 | 国密算法本身安全，但密钥硬编码 |
| **PKCS#5 padding** | 标准 | SM4 使用 PKCS#5 填充，无安全问题 |
| **分块加密** | 注意 | RSA 64字节分块加密，返回数组而非单值 |

## 4. API 路由完整列表

### 4.1 Starspark API (43 路由)

#### Chat 相关 (16)

| 路径 | 方法 | 说明 |
|------|------|------|
| `/api/starspark/v1/agent/chat/async/ask` | POST | 异步 AI 对话 |
| `/api/starspark/v1/agent/chat/sync/ask` | POST | 同步 AI 对话 |
| `/api/starspark/v1/agent/chat/inline/chat` | POST | 内联聊天 |
| `/api/starspark/v1/agent/chat/review` | POST | 代码评审 |
| `/api/starspark/v1/agent/chat/optimizeCode` | POST | 代码优化 |
| `/api/starspark/v1/agent/chat/splitFunction` | POST | 函数拆分 |
| `/api/starspark/v1/agent/chat/optimizeSql` | POST | SQL 优化 |
| `/api/starspark/v1/agent/chat/generateSql` | POST | SQL 生成 |
| `/api/starspark/v1/agent/chat/sync/generateSql` | POST | 同步 SQL 生成 |
| `/api/starspark/v1/agent/chat/sync/optimizeSql` | POST | 同步 SQL 优化 |
| `/api/starspark/v1/agent/chat/generateCommitMessage` | POST | 生成 commit message |
| `/api/starspark/v1/agent/chat/interLineCommentCode` | POST | 行注释 |
| `/api/starspark/v1/agent/chat/feedback` | POST | 反馈 |
| `/api/starspark/v1/agent/chat/evaluate` | POST | 评估 |
| `/api/starspark/v1/agent/chat/convertDmTableDDL` | POST | DM 表 DDL 转换 |
| `/api/starspark/v1/agent/chat/recommendations` | POST | 推荐 |

#### Code 相关 (4)

| 路径 | 方法 | 说明 |
|------|------|------|
| `/api/starspark/v1/agent/code/codeComplete` | POST | 代码补全 |
| `/api/starspark/v1/agent/code/generateUnitTest` | POST | 单元测试生成 |
| `/api/starspark/v1/agent/code/generateUnitTestCaseTemplate` | POST | 单测模板 |
| `/api/starspark/v1/agent/code/queryUnitTestQueueInfo` | POST | 单测队列查询 |

#### Collect 相关 (6)

| 路径 | 方法 | 说明 |
|------|------|------|
| `/api/starspark/v1/agent/collect/chatDataContent` | POST | 聊天数据收集 |
| `/api/starspark/v1/agent/collect/codeAccept` | POST | 代码接受收集 |
| `/api/starspark/v1/agent/collect/commitCodeData` | POST | 提交代码数据 |
| `/api/starspark/v1/agent/collect/commitUnitTestData` | POST | 提交单测数据 |
| `/api/starspark/v1/agent/collect/generateUnitTestData` | POST | 生成单测数据 |
| `/api/starspark/v1/agent/collect/unitTestStatistics` | POST | 单测统计 |
| `/api/starspark/v1/agent/collect/uploadRequestTime` | POST | 上报请求时间 |

#### Permission & Settings (8)

| 路径 | 方法 | 说明 |
|------|------|------|
| `/api/starspark/v1/agent/permission/queryUserFuncModelList` | GET | 查询用户功能模型 |
| `/api/starspark/v1/agent/permission/queryUserPermissionPackageInfo` | GET | 查询权限包 |
| `/api/starspark/v1/agent/authSetting/query` | GET | 查询认证设置 |
| `/api/starspark/v1/agent/authSetting/queryPluginLink` | GET | 查询插件链接 |
| `/api/starspark/v1/agent/pluginSetting/queryGlobalSetting` | GET | 查询全局设置 |
| `/api/starspark/v1/agent/pluginSetting/queryTokenSetting` | GET | 查询 Token 设置 |
| `/api/starspark/v1/agent/prompt/query` | GET | 查询 Prompt |
| `/api/starspark/v1/agent/wordWriter/config` | GET | 写作配置 |

#### Action & Feedback (3)

| 路径 | 方法 | 说明 |
|------|------|------|
| `/api/starspark/v1/agent/action/rejectCode` | POST | 拒绝代码 |
| `/api/starspark/v1/agent/action/saveUserAction` | POST | 保存用户操作 |
| `/api/starspark/v1/agent/feedback/queryCategory` | GET | 查询反馈分类 |

#### User & Auth (5)

| 路径 | 方法 | 说明 |
|------|------|------|
| `/api/starspark/v1/chat/user/logOut` | POST | 登出 |
| `/api/starspark/v1/chat/user/valid` | GET | 验证用户 |
| `/api/starspark/v1/user/authorizationQuery` | GET | 授权查询 |
| `/api/starspark/v1/user/packageQuery` | GET | 套餐查询 |
| `/api/starspark/v1/platform/code/assist` | POST | 代码辅助 |

#### Login (1)

| 路径 | 方法 | 说明 |
|------|------|------|
| `/api/usercenter/v1/user/common/login` | POST | 统一登录 |

### 4.2 RAG Server API (14 路由)

#### Code Search (4)

| 路径 | 方法 | 说明 |
|------|------|------|
| `/api/ragserver/v1/code/search` | POST | 代码搜索 |
| `/api/ragserver/v1/code/getLanguages` | GET | 获取语言列表 |
| `/api/ragserver/v1/code/getUserRepos` | GET | 获取用户仓库 |
| `/api/ragserver/v1/code/onlineSearch` | POST | 在线搜索 |

#### RAG (2)

| 路径 | 方法 | 说明 |
|------|------|------|
| `/api/ragserver/v1/rag/incbatchload` | POST | 批量加载 |
| `/api/ragserver/v1/web/parseurl` | POST | URL 解析 |

#### RestAPI RAG (8)

| 路径 | 方法 | 说明 |
|------|------|------|
| `/restapi/ragserver/v1/code/searchInRepo` | POST | 仓库内代码搜索 |
| `/restapi/ragserver/v1/codeknowledge/reVectorized` | POST | 重新向量化 |
| `/restapi/ragserver/v1/doc/knowledgeList` | GET | 文档知识列表 |
| `/restapi/ragserver/v1/doc/search` | POST | 文档搜索 |
| `/restapi/ragserver/v1/rag/codeK/codeKnowledgeList` | GET | 代码知识列表 |
| `/restapi/ragserver/v1/rag/codeK/personal/auth` | POST | 个人知识库认证 |
| `/restapi/ragserver/v1/rag/codeK/personal/init/status` | GET | 初始化状态 |
| `/restapi/ragserver/v1/rag/codeK/updateGitToken` | POST | 更新 Git Token |

#### RAG Enable (2)

| 路径 | 方法 | 说明 |
|------|------|------|
| `/restapi/ragserver/v1/rag/repoKeyDialogEnable` | GET | 仓库密钥对话框开关 |
| `/restapi/ragserver/v1/rag/repoKeyEnable` | GET | 仓库密钥开关 |
| `/restapi/ragserver/v1/rag/repoLangExtEnable` | GET | 语言扩展开关 |

### 4.3 Unit Test API (6 路由)

| 路径 | 方法 | 说明 |
|------|------|------|
| `/restapi/unit/v1/createUnitTask` | POST | 创建单测任务 |
| `/restapi/unit/v1/cancelUnitTask` | POST | 取消单测任务 |
| `/restapi/unit/v1/queryUnitTask` | GET | 查询单测任务 |
| `/restapi/unit/v1/deleteUnitTask` | DELETE | 删除单测任务 |
| `/restapi/unit/v1/exportByTaskId` | GET | 导出单测结果 |
| `/restapi/unit/v1/isPendingTask` | GET | 查询待处理任务 |

### 4.4 其他路由

| 路径 | 方法 | 说明 |
|------|------|------|
| `/ws/onMessage` | WebSocket | WebSocket 消息 |
| `/sso/login` | POST | SSO 登录 |
| `/commitCollect` | POST | 提交收集 |
| `/index` | GET | 首页 |

### 4.5 API 总计

| 服务 | 路由数 |
|------|--------|
| Starspark | 43 |
| RAG Server | 14 |
| Unit Test | 6 |
| 其他 | 4 |
| **总计** | **67** |

**注意**: 之前文档（doc 31）记录了 92 条 API 路由，实际从 bundle 中提取了 67 条。其余路由可能在 webpack bundle 的动态路由注册中，或通过 WebSocket 命令间接调用。

## 5. 数据库架构

### 5.1 数据库引用统计

| 数据库 | 引用次数 | 说明 |
|--------|---------|------|
| Knex | 184 | SQL 查询构建器（主数据库接口） |
| MySQL/MySQL2 | 112 | 远程 MySQL 数据库 |
| SQLite3 | 68 | 本地 SQLite 数据库 |
| DMDB | 87 | 达梦数据库（国产） |
| PostgreSQL | 62 | PostgreSQL 数据库 |
| NeDB | 16 | 本地嵌入式数据库 |

### 5.2 数据库连接配置

从源码提取的数据库配置模式：

```javascript
// MySQL/MySQL2 连接
{
  host: conn_prop_host,
  port: conn_prop_port || 1433,
  user: connectionSettings.user,
  password: d.password,
  database: d.database
}

// DMDB (达梦) 连接
{
  connectString: `${host}:${port}`,
  loginEncrypt: false,
  user: username,
  password: password
}
```

### 5.3 数据库用途

| 数据库 | 用途 |
|--------|------|
| SQLite3 | 本地缓存、知识库索引 |
| NeDB | 本地嵌入式存储 |
| MySQL | 远程持久化 |
| PostgreSQL | 远程持久化（备选） |
| DMDB | 达梦数据库（国产替代） |

## 6. Tree-sitter 解析器

### 6.1 WASM 文件

| 文件 | 说明 |
|------|------|
| tree-sitter.wasm | Tree-sitter 核心 |
| tree-sitter-c.wasm | C 语言 |
| tree-sitter-c_sharp.wasm | C# 语言 |
| tree-sitter-cpp.wasm | C++ 语言 |
| tree-sitter-go.wasm | Go 语言 |
| tree-sitter-java.wasm | Java 语言 |
| tree-sitter-javascript.wasm | JavaScript |
| tree-sitter-python.wasm | Python |
| tree-sitter-tsx.wasm | TSX |
| tree-sitter-typescript.wasm | TypeScript |

### 6.2 Tree-sitter 引用统计

| 关键词 | 引用次数 |
|--------|---------|
| wasm | 534 |
| TreeSitter | 24 |
| tree-sitter | 7 |
| tree_sitter | 7 |
| treeSitter | 6 |

## 7. WebSocket 通信

### 7.1 WebSocket Server

- `WebSocketServer`: 4 处引用
- `wss.`: 1 处引用
- 路径: `/ws/onMessage`

### 7.2 Express Server

- `app.listen`: 1 处（监听端口）
- 默认端口: 6832

## 8. 外部依赖

### 8.1 运行时依赖 (package.json)

| 依赖 | 版本 | 用途 |
|------|------|------|
| @opentelemetry/exporter-trace-otlp-http | 0.49.1 | APM 链路追踪 |
| @opentelemetry/instrumentation | 0.49.1 | APM 自动埋点 |
| @opentelemetry/sdk-trace-base | 1.24.1 | APM SDK |
| @seald-io/nedb | ^4.0.4 | 本地嵌入式数据库 |
| adm-zip | ^0.5.12 | ZIP 文件处理 |
| cheerio | 1.0.0-rc.12 | HTML 解析 |
| dmdb | ^1.0.24984 | 达梦数据库驱动 |
| eventsource-parser | ^1.1.2 | SSE 解析 |
| fast-glob | ^3.3.2 | 文件搜索 |
| fast-xml-parser | ^4.3.5 | XML 解析 |
| featureprobe-server-sdk-node | ^2.2.0 | 功能开关 |
| fs-extra | ^10.1.0 | 文件操作增强 |
| iconv-lite | ^0.6.3 | 编码转换 |
| jschardet | ^3.1.4 | 编码检测 |
| knex | 2.5.1 | SQL 查询构建器 |
| lodash | ^4.16.6 | 通用工具 |
| log4js | ^6.9.1 | 日志 |
| lru-cache | 7.18.3 | LRU 缓存 |
| marked | 1.2.9 | Markdown 渲染 |
| mysql2 | 3.2.0 | MySQL 驱动 |
| node-abort-controller | ^3.1.1 | AbortController |
| node-fetch | ^2.7.0 | HTTP 客户端 |
| pg | ^8.11.3 | PostgreSQL 驱动 |
| portfinder | ^1.0.32 | 端口查找 |
| sm-crypto | ^0.3.13 | 国密 SM2/SM4 加密 |
| sqlite3 | ^5.1.7 | SQLite 驱动 |
| toml | ^3.0.0 | TOML 解析 |
| uuid | ^9.0.1 | UUID 生成 |
| web-tree-sitter | 0.22.2 | Tree-sitter WASM |
| word-extractor | ^1.0.4 | Word 文档解析 |
| ws | ^8.18.0 | WebSocket |

### 8.2 Node.js 内置模块引用

| 模块 | 用途 |
|------|------|
| crypto | 加密/解密 |
| child_process | 子进程管理 |
| fs | 文件系统 |
| http/https | HTTP 服务 |
| net | 网络操作 |
| path | 路径处理 |
| stream | 流处理 |
| worker_threads | Worker 线程 |
| cluster | 集群 |
| dns | DNS 解析 |
| os | 操作系统信息 |
| url | URL 解析 |

## 9. 中文错误消息

### 9.1 错误类 (top 20)

| 消息 | 频次 | 说明 |
|------|------|------|
| 用户未登录 | 3 | 未登录错误 |
| 提交的信息或参数无效 | 3 | 参数错误 |
| 网络异常，请检查网络 | 3 | 网络错误 |
| 未授权，请重新登录 | 2 | 授权过期 |
| 数据源不存在 | 2 | 数据源错误 |
| 连接已断开 | 2 | WebSocket 断开 |
| 连接池连接有效性检查失败:没有结果集. | 2 | 数据库连接错误 |
| 警告:批量执行部分行产生错误 | 2 | 批量执行错误 |
| 升级文件下载失败 | 2 | 更新失败 |
| 文件读取失败 | 1 | 文件错误 |
| 任务处理超时 | 1 | 超时错误 |
| 请选择代码片段 | 1 | 用户操作提示 |
| 指令操作已经结束 | 1 | 操作完成 |
| 数据迁移出错 | 3 | 数据迁移错误 |
| 解析文本 | 2 | 文本解析 |

### 9.2 图表类型

| 消息 | 频次 | 说明 |
|------|------|------|
| 流程图 | 2 | Flowchart |
| 序列图 | 2 | Sequence diagram |
| 甘特图 | 2 | Gantt chart |
| 类图 | 2 | Class diagram |
| 状态图 | 2 | State diagram |
| 实体关系图 | 2 | ER diagram |
| 用户旅程图 | 2 | User journey |
| 派生图 | 2 | Pie chart |
| 时序图 | 1 | Timeline |

### 9.3 总计

- **1098 个独特中文字符串**（包含日文汉字排序表等非业务字符串）
- **业务相关中文字符串约 200+ 个**

## 10. HTTPS URL 端点

| URL | 说明 |
|-----|------|
| https://iflycode-api.iflytek.com | 生产 API |
| https://iflycode-xfsaas.xfyun.cn | 星火 SaaS API |
| https://iflycode-xfsaas.xfyun.cn/v1/traces | APM 链路追踪 |

## 11. 开发环境泄露

从 package.json 的 scripts 中发现多个内部开发服务器地址：

| 地址 | 说明 |
|------|------|
| http://172.29.63.138 | 开发服务器 |
| http://172.30.14.79 | 开发服务器79 |
| http://172.29.228.232 | 开发服务器232 |
| http://172.29.228.181:8080 | 开发服务器181 |
| http://172.29.231.97:4318 | APM 服务器 |

## 12. 关键发现

1. **5 种加密算法**: RSA、SM2（国密）、SM4（国密）、AES-256-CTR、MD5，全部密钥硬编码。

2. **RSA 1024-bit**: 使用 1024-bit RSA 公钥，已被认为不安全。64 字节分块加密，返回 base64 数组。

3. **国密 SM2/SM4**: 使用 `sm-crypto` 库实现国密加密，SM2 使用 C1C3C2 模式，SM4 使用 PKCS#5 填充。

4. **AES-256-CTR**: 密钥和 IV 硬编码，CTR 模式不提供认证加密，存在安全风险。

5. **67 条 API 路由**: Starspark 43 条 + RAG Server 14 条 + Unit Test 6 条 + 其他 4 条。

6. **5 种数据库支持**: SQLite3（本地）、NeDB（本地）、MySQL2（远程）、PostgreSQL（远程）、DMDB（达梦，国产）。

7. **9 种 Tree-sitter 解析器**: C、C#、C++、Go、Java、JavaScript、Python、TSX、TypeType。

8. **1098 个中文字符串**: 包含错误消息、图表类型、AI 提示词等。

9. **featureprobe 功能开关**: 使用 FeatureProbe SDK 实现灰度发布。

10. **开发环境泄露**: package.json 中包含 4 个内部开发服务器 IP 地址和 APM 服务器地址。

11. **Node <=12 版本要求**: Agent 要求 Node.js <=12，说明可能使用了较老的 Node.js API。

12. **worker.js**: 976 KB 的 worker 文件，可能用于 Tree-sitter 解析的 Worker 线程。

13. **DMDB (达梦数据库)**: 支持国产达梦数据库，说明产品面向国内政企市场。

14. **word-extractor**: 支持 Word 文档解析，可能用于需求文档导入。

15. **登录加密**: 用户名和密码使用 RSA 加密后传输，但 RSA 1024-bit 安全性不足。