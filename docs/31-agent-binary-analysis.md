# 31 Agent 二进制深度分析

> 基于 agent.zip 解压 + webpack bundle 静态字符串提取

## Agent 概览

```
名称: iFlyCode Agent (讯飞飞码本地代理)
版本: 3.4.2-222
运行时: Node.js (内嵌二进制)
入口: agent/bin/index.js (3.97MB webpack bundle)
Worker: agent/bin/worker.js (999KB)
端口: 6832 (默认)
协议: WebSocket (ws://127.0.0.1:6832/ws/idea)
```

## 文件结构

```
agent/
├── bin/
│   ├── index.js              — 主进程 (3.97MB webpack bundle)
│   ├── worker.js             — Worker 线程 (999KB)
│   ├── x86_64_darwin_arm_node  — macOS ARM64 Node 二进制 (88MB)
│   ├── x86_64_darwin_node     — macOS x86_64 Node 二进制 (93MB)
│   ├── x86_64_linux_node      — Linux x86_64 Node 二进制 (87MB)
│   └── x86_64_windows_node.exe — Windows x86_64 Node 二进制 (88MB)
├── wasms/                     — tree-sitter WASM 解析器
│   ├── tree-sitter-c.wasm
│   ├── tree-sitter-cpp.wasm
│   ├── tree-sitter-css.wasm
│   ├── tree-sitter-go.wasm
│   ├── tree-sitter-java.wasm
│   ├── tree-sitter-javascript.wasm
│   ├── tree-sitter-json.wasm
│   ├── tree-sitter-python.wasm
│   ├── tree-sitter-rust.wasm
│   └── tree-sitter-typescript.wasm
├── fileTemplates/             — Java 单测模板
│   ├── Junit3Template.java
│   ├── Junit4Template.java
│   ├── Junit5Template.java
│   ├── TestNGTemplate.java
│   ├── Junit3ParamTemplate.java
│   ├── Junit4ParamTemplate.java
│   ├── Junit5ParamTemplate.java
│   └── TestNGParamTemplate.java
└── [其他资源文件]
```

## Node.js 内嵌二进制

```
平台支持:
  macOS ARM64    — 88MB (x86_64_darwin_arm_node)
  macOS x86_64   — 93MB (x86_64_darwin_node)
  Linux x86_64   — 87MB (x86_64_linux_node)
  Windows x86_64 — 88MB (x86_64_windows_node.exe)

用途: Agent 作为独立进程运行，内嵌 Node.js 运行时
启动: IDE 启动 Agent 子进程 → Agent 绑定端口 6832 → WebSocket 监听
```

## tree-sitter 集成

```
WASM 解析器 (10 个语言):
  c, cpp, css, go, java, javascript, json, python, rust, typescript

引用统计:
  TreeSitter: 24 次
  .wasm: 6 次

用途:
  - 代码结构解析 (AST)
  - 代码补全上下文提取 (structure 字段)
  - 相似代码检索 (similarStr 字段)
  - 代码检查/评审辅助
```

## 云端 API 端点 (92 个路由)

### 星火 Agent API (/api/starspark/v1/agent/)

```
POST /api/starspark/v1/agent/chat/completions        — 聊天补全
POST /api/starspark/v1/agent/chat/completions/stream  — 流式聊天补全
POST /api/starspark/v1/agent/code/complete            — 代码补全
POST /api/starspark/v1/agent/code/complete/stream     — 流式代码补全
POST /api/starspark/v1/agent/code/generate            — 代码生成
POST /api/starspark/v1/agent/code/generate/stream     — 流式代码生成
POST /api/starspark/v1/agent/code/explain             — 代码解释
POST /api/starspark/v1/agent/code/optimize            — 代码优化
POST /api/starspark/v1/agent/code/review              — 代码评审
POST /api/starspark/v1/agent/code/comment             — 代码注释
POST /api/starspark/v1/agent/code/test                — 代码测试
POST /api/starspark/v1/agent/code/debug               — 代码调试
POST /api/starspark/v1/agent/code/split               — 函数拆分
POST /api/starspark/v1/agent/code/search              — 代码搜索
POST /api/starspark/v1/agent/code/check               — 代码检查
POST /api/starspark/v1/agent/sql/generate             — SQL 生成
POST /api/starspark/v1/agent/sql/optimize             — SQL 优化
POST /api/starspark/v1/agent/sql/explain              — SQL 解释
POST /api/starspark/v1/agent/unit/test                — 单元测试
POST /api/starspark/v1/agent/unit/test/batch          — 批量单测
POST /api/starspark/v1/agent/git/review               — Git 评审
POST /api/starspark/v1/agent/demand/analysis          — 需求分析
POST /api/starspark/v1/agent/demand/splitting         — 需求拆分
POST /api/starspark/v1/agent/demand/test              — 需求测试
```

### RAG 服务 API (/api/ragserver/v1/)

```
POST /api/ragserver/v1/knowledge/base/create         — 创建知识库
POST /api/ragserver/v1/knowledge/base/delete         — 删除知识库
POST /api/ragserver/v1/knowledge/base/list           — 知识库列表
POST /api/ragserver/v1/knowledge/base/update         — 更新知识库
POST /api/ragserver/v1/knowledge/doc/upload          — 文档上传
POST /api/ragserver/v1/knowledge/doc/delete          — 文档删除
POST /api/ragserver/v1/knowledge/doc/list            — 文档列表
POST /api/ragserver/v1/knowledge/search              — 知识搜索
POST /api/ragserver/v1/knowledge/embedding           — 向量嵌入
```

### REST API RAG (/restapi/ragserver/v1/)

```
POST /restapi/ragserver/v1/knowledge/base/create     — 创建知识库 (REST)
POST /restapi/ragserver/v1/knowledge/base/delete     — 删除知识库 (REST)
POST /restapi/ragserver/v1/knowledge/base/list       — 知识库列表 (REST)
POST /restapi/ragserver/v1/knowledge/search          — 知识搜索 (REST)
```

### 单元测试 API (/restapi/unit/v1/)

```
POST /restapi/unit/v1/test/generate                  — 生成测试
POST /restapi/unit/v1/test/generate/batch            — 批量生成
POST /restapi/unit/v1/test/template/list             — 模板列表
POST /restapi/unit/v1/test/template/get              — 获取模板
POST /restapi/unit/v1/test/result                    — 测试结果
POST /restapi/unit/v1/test/case/list                 — 用例列表
POST /restapi/unit/v1/test/case/code                 — 用例代码
```

### 其他 API 路由

```
POST /api/starspark/v1/agent/login                   — 登录
POST /api/starspark/v1/agent/logout                  — 登出
POST /api/starspark/v1/agent/token/refresh           — Token 刷新
POST /api/starspark/v1/agent/user/info               — 用户信息
POST /api/starspark/v1/agent/model/list              — 模型列表
POST /api/starspark/v1/agent/feedback                — 反馈上报
POST /api/starspark/v1/agent/log                     — 日志上报
POST /api/starspark/v1/agent/config                  — 配置获取
POST /api/starspark/v1/agent/permission              — 权限查询
```

## RSA 加密

### 公钥

```
公钥 1 (1024-bit, 已脱敏):
  已脱敏

公钥 2 (2048-bit, MIIBIjAN 前缀):
  MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuD8nrZ8V...
```

### 加密引用

```
publicEncrypt: 4 次 — RSA 公钥加密
createCipher:  2 次 — 对称加密创建
createDecipher: 2 次 — 对称解密创建
createHash:    14 次 — 哈希计算

用途推断:
  - 登录密码 RSA 加密 (publicEncrypt)
  - Token/会话 AES 加密 (createCipher/createDecipher)
  - 请求签名/校验 (createHash)
  - WebSocket 消息加密
```

### 加密相关中文错误消息

```
"消息加密失败"           — 消息加密失败
"未授权，请重新登录"      — Token 过期/无效
"网络通信异常"           — 网络错误
"请求超时"               — 请求超时
"服务端错误"             — 服务器错误
"参数校验失败"           — 参数验证失败
```

## 数据库集成

### 引用统计

```
knex:    143 次 — SQL 查询构建器 (主要数据库接口)
sqlite:   32 次 — SQLite 数据库 (本地存储)
mysql:    58 次 — MySQL 数据库 (SQL 功能)
nedb:      7 次 — NeDB 嵌入式数据库 (对话历史)
```

### 用途分析

```
NeDB (7 次):
  - 对话历史存储 (conversationList)
  - 会话管理 (sessionId)
  - 本地缓存

Knex + SQLite (143 + 32 次):
  - 代码知识库索引
  - 文件索引/搜索
  - 本地配置存储
  - 缓存管理

Knex + MySQL (143 + 58 次):
  - SQL 生成/优化功能
  - 数据库连接管理
  - 表结构查询
  - SQL 执行验证
```

## WebSocket 通信

### 连接协议

```
IDE → Agent:
  ws://127.0.0.1:6832/ws/idea

消息格式:
  { command: "xxx", data: {...} }

流式响应:
  ResponseStreamDto: { requestId, text, ended, data }
```

### 命令分发

```
Agent 接收 IDE 命令后:
  1. 解析 command 字段
  2. 路由到对应处理器
  3. 构建云端 API 请求
  4. HTTPS POST 到星火 API
  5. 接收 SSE 流式响应
  6. 转换为 ResponseStreamDto
  7. 通过 WebSocket 回传 IDE
```

## 关键中文错误消息 (227 条提取)

### 认证相关

```
"未授权，请重新登录"
"登录已过期"
"Token刷新失败"
"用户信息获取失败"
"权限校验失败"
```

### 通信相关

```
"消息加密失败"
"网络通信异常"
"请求超时"
"服务端错误"
"WebSocket连接失败"
"WebSocket连接断开"
```

### 功能相关

```
"代码补全失败"
"代码生成失败"
"代码解释失败"
"代码优化失败"
"代码评审失败"
"单元测试生成失败"
"SQL生成失败"
"SQL优化失败"
"知识库检索失败"
"文件索引失败"
"模板加载失败"
```

### 数据相关

```
"数据库连接失败"
"数据源配置错误"
"表结构查询失败"
"SQL执行失败"
"数据验证失败"
"参数校验失败"
```

## 进程架构

```
IDE (IntelliJ)
  │
  ├── 启动 Agent 子进程
  │   Agent (Node.js)
  │   ├── index.js — 主进程
  │   │   ├── WebSocket Server (端口 6832)
  │   │   ├── HTTPS Client → 星火云 API
  │   │   ├── tree-sitter WASM 加载
  │   │   ├── NeDB 对话存储
  │   │   ├── Knex 数据库连接
  │   │   └── RSA/AES 加密
  │   │
  │   └── worker.js — Worker 线程
  │       ├── CPU 密集任务 (代码解析)
  │       └── tree-sitter AST 构建
  │
  └── WebView (JCEF)
      └── Vue 3 SPA
```

## webpack Bundle 分析限制

```
index.js 特征:
  - 3.97MB 单文件 webpack bundle
  - 高度压缩/混淆 (所有代码在约 100 个超长行上)
  - 变量名混淆 (单字母/短变量名)
  - 模块 ID 数字化 (webpack module ID)

可提取信息:
  ✅ 字符串常量 (API 路由、错误消息、配置值)
  ✅ import/require 引用 (库名、模块路径)
  ✅ 正则表达式模式
  ✅ JSON 配置片段

不可提取信息 (因混淆):
  ❌ 具体函数实现逻辑
  ❌ 变量赋值关系
  ❌ 控制流/数据流
  ❌ 模块间调用关系图
  ❌ 加密算法具体实现

建议:
  - 使用 source-map 支持还原 (如有 .map 文件)
  - 运行时 Hook/调试获取实际行为
  - 反编译 webpack 模块获取模块级代码
```

## 与 docs/22 交叉验证

```
docs/22 中记录的端点 vs Agent 实际端点:

匹配:
  ✅ /api/starspark/v1/agent/chat/completions
  ✅ /api/starspark/v1/agent/code/complete
  ✅ /api/starspark/v1/agent/code/generate
  ✅ /api/ragserver/v1/knowledge/*

新增 (Agent 中发现但 docs/22 未记录):
  + /api/starspark/v1/agent/code/search
  + /api/starspark/v1/agent/code/check
  + /api/starspark/v1/agent/demand/*
  + /restapi/unit/v1/test/*
  + /api/starspark/v1/agent/login
  + /api/starspark/v1/agent/token/refresh
  + /api/starspark/v1/agent/permission
```

## 单测模板文件

```
Junit3Template.java    — JUnit 3 风格模板
Junit4Template.java    — JUnit 4 风格模板
Junit5Template.java    — JUnit 5 风格模板
TestNGTemplate.java    — TestNG 风格模板
Junit3ParamTemplate.java  — JUnit 3 参数化模板
Junit4ParamTemplate.java  — JUnit 4 参数化模板
Junit5ParamTemplate.java  — JUnit 5 参数化模板
TestNGParamTemplate.java  — TestNG 参数化模板

用途:
  Agent 接收单测请求 → 选择对应模板 → 填充类/方法信息 → 发送到云端生成
```
