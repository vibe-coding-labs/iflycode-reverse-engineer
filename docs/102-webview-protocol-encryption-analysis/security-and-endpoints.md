## 6. 协议安全评估

### 6.1 数据传输加密状态总览

| 链路 | 协议 | 加密 | 认证 | 风险 |
|------|------|------|------|------|
| WebView → Java | JS Bridge (JCEF) | 无 | 无 | 本地沙箱, 低风险 |
| Java → Agent | WebSocket ws:// | 无 | 无 | **高风险**: 本机进程可嗅探 |
| Agent → 云端 | HTTPS | TLS 1.2+ | access-token | 安全 |
| Agent 内部 | 函数调用 | SM2/SM4/RSA/AES | 硬编码密钥 | 密钥不可轮换 |

### 6.2 明文传输的敏感数据

| 数据 | 传输路径 | 风险等级 | 说明 |
|------|---------|---------|------|
| SQL 数据库密码 | WebView → Java → Agent | **高** | 明文经 JS Bridge 和 WebSocket |
| Git 访问令牌 | WebView → Java → Agent | **高** | 明文经 JS Bridge 和 WebSocket |
| 用户聊天内容 | WebView → Java → Agent → 云端 | **中** | 含代码、业务逻辑 |
| AI 生成代码 | 云端 → Agent → Java → WebView | **中** | 可能含敏感实现 |
| 文件完整内容 | Java → Agent (代码补全) | **中** | 每次补全发送完整文件 |
| 认证 Token | Agent → 云端 (HTTP header) | **低** | 仅在 HTTPS 通道 |
| 登录密码 | WebView → (浏览器) → 云端 | **低** | 浏览器重定向, 不经 WebView |

### 6.3 加密强度评估

| 算法 | 密钥强度 | 评估 | 问题 |
|------|---------|------|------|
| RSA-1024 | 1024 bit | **弱** | 2010年768-bit已分解, 1024-bit可分解 |
| SM2 | 256 bit (等效) | **中** | 算法安全, 但密钥硬编码 |
| SM4 | 128 bit | **中** | 算法安全, 但密钥硬编码 |
| AES-256-CTR | 256 bit | **弱** | CTR模式+固定IV=流密码重用 |
| MD5 | 128 bit | **弱** | 已知碰撞攻击 |

### 6.4 攻击面分析

#### 攻击面 1: WebSocket 嗅探 (高风险)

```
攻击条件: 本机恶意进程
攻击路径: 连接 ws://127.0.0.1:&#123;port&#125;/ws/idea
可获取数据: 所有用户输入、AI响应、SQL密码、Git Token
缓解措施: WebSocket 无认证, 端口可预测
```

#### 攻击面 2: JS Bridge 注入 (中风险)

```
攻击条件: WebView XSS 或 CEF 调试端口开放
攻击路径: 在 WebView 中执行任意 JS
可获取数据: 调用所有 JS→Java 功能
可执行操作: 发送消息、修改代码、访问数据库、操作 Git
缓解措施: JCEF 沙箱限制, 生产环境应禁用远程调试
```

#### 攻击面 3: 密钥提取 (中风险)

```
攻击条件: 访问 Agent 安装目录
攻击路径: 读取 agent/bin/index.js
可获取数据: 所有硬编码密钥 (RSA, SM2, SM4, AES)
影响: 可解密所有历史加密数据
缓解措施: webpack 混淆增加提取难度, 但非不可逆
```

#### 攻击面 4: 中间人攻击 (低风险)

```
攻击条件: 控制 Agent → 云端网络路径
攻击路径: 修改 DNS / 代理设置
可获取数据: 所有 API 请求/响应
缓解措施: HTTPS + 证书验证 (除 OpenTelemetry 通道)
注意: OpenTelemetry 通道禁用证书验证, 可被 MITM
```

### 6.5 安全建议优先级

| 优先级 | 建议 | 影响 |
|--------|------|------|
| P0 | WebSocket 添加认证 (共享密钥或 Token) | 防止本机进程嗅探 |
| P0 | SQL 密码传输前加密 (RSA/SM2) | 防止密码泄露 |
| P1 | Git Token 传输前加密 | 防止令牌泄露 |
| P1 | 升级 RSA 到 2048-bit+ | 防止密钥分解 |
| P1 | AES-CTR 改为 AES-GCM | 防止流密码重用攻击 |
| P2 | 密钥从服务端动态获取 | 支持密钥轮换 |
| P2 | OpenTelemetry 启用证书验证 | 防止 MITM |
| P3 | MD5 替换为 SHA-256 | 防止碰撞攻击 |
| P3 | JS Bridge 添加权限校验 | 防止未授权调用 |

---

## 7. Agent API 端点完整映射

### 7.1 星火 API (starspark)

| API 路径 | 用途 | 模块 |
|---------|------|------|
| `/api/starspark/v1/agent/chat/async/ask` | 异步流式对话 | Chat |
| `/api/starspark/v1/agent/chat/sync/ask` | 同步对话 | Chat |
| `/api/starspark/v1/agent/chat/inline/chat` | 行内对话 | InlineChat |
| `/api/starspark/v1/agent/chat/review` | 代码评审 | GitReview |
| `/api/starspark/v1/agent/chat/generateSql` | SQL 生成 | SQL |
| `/api/starspark/v1/agent/chat/sync/generateSql` | SQL 生成 (同步) | SQL |
| `/api/starspark/v1/agent/chat/optimizeSql` | SQL 优化 | SQL |
| `/api/starspark/v1/agent/chat/sync/optimizeSql` | SQL 优化 (同步) | SQL |
| `/api/starspark/v1/agent/chat/optimizeCode` | 代码优化 | Chat |
| `/api/starspark/v1/agent/chat/splitFunction` | 函数拆分 | Chat |
| `/api/starspark/v1/agent/chat/generateCommitMessage` | Commit 消息生成 | Git |
| `/api/starspark/v1/agent/chat/convertDmTableDDL` | 达梦 DDL 转换 | SQL |
| `/api/starspark/v1/agent/chat/interLineCommentCode` | 行间注释 | InlineChat |
| `/api/starspark/v1/agent/chat/evaluate` | 评价 | Common |
| `/api/starspark/v1/agent/chat/feedback` | 反馈 | Common |
| `/api/starspark/v1/agent/chat/recommendations` | 推荐 | Chat |
| `/api/starspark/v1/agent/prompt/query` | Prompt 模板查询 | Chat |
| `/api/starspark/v1/agent/code/codeComplete` | 代码补全 | CodeComplete |
| `/api/starspark/v1/agent/code/generateUnitTest` | 单测生成 | UnitTest |
| `/api/starspark/v1/agent/code/generateUnitTestCaseTemplate` | 单测模板 | UnitTest |
| `/api/starspark/v1/platform/code/assist` | 代码辅助 (VSCode) | CodeComplete |
| `/api/starspark/v1/agent/permission/queryUserPermissionPackageInfo` | 权限查询 | Login |
| `/api/starspark/v1/agent/permission/queryUserFuncModelList` | 功能模型列表 | Login |
| `/api/starspark/v1/agent/authSetting/query` | 认证设置查询 | Login |
| `/api/starspark/v1/agent/authSetting/queryPluginLink` | 插件链接查询 | Login |
| `/api/starspark/v1/agent/authSetting/queryGlobalSetting` | 全局设置 | Setting |
| `/api/starspark/v1/agent/pluginSetting/queryTokenSetting` | Token 设置 | Setting |
| `/api/starspark/v1/agent/action/saveUserAction` | 用户行为记录 | Common |
| `/api/starspark/v1/agent/action/rejectCode` | 拒绝代码 | Common |
| `/api/starspark/v1/user/authorizationQuery` | 登录状态查询 | Login |
| `/api/starspark/v1/user/packageQuery` | 套餐查询 | Login |
| `/api/usercenter/v1/user/common/login` | 账号密码登录 | Login |
| `/api/starspark/v1/chat/user/logOut` | 登出 | Login |
| `/api/starspark/v1/chat/user/valid` | Token 验证 | Login |

### 7.2 RAG 服务 (ragserver)

| API 路径 | 用途 | 模块 |
|---------|------|------|
| `/api/ragserver/v1/code/search` | 代码搜索 | CodeSearch |
| `/api/ragserver/v1/code/onlineSearch` | 在线搜索 | CodeSearch |
| `/api/ragserver/v1/code/getLanguages` | 语言列表 | CodeSearch |
| `/api/ragserver/v1/code/getUserRepos` | 用户仓库 | CodeSearch |
| `/api/ragserver/v1/rag/incbatchload` | RAG 批量加载 | RAG |
| `/api/ragserver/v1/web/parseurl` | 网页解析 | RAG |
| `/restapi/ragserver/v1/doc/search` | 文档搜索 | RAG |
| `/restapi/ragserver/v1/doc/knowledgeList` | 文档知识列表 | RAG |
| `/restapi/ragserver/v1/rag/codeK/codeKnowledgeList` | 代码知识列表 | RAG |
| `/restapi/ragserver/v1/rag/codeK/personal/auth` | 个人知识库认证 | RAG |
| `/restapi/ragserver/v1/rag/codeK/personal/init/status` | 知识库初始化状态 | RAG |
| `/restapi/ragserver/v1/rag/codeK/updateGitToken` | 更新 Git Token | RAG |
| `/restapi/ragserver/v1/codeknowledge/reVectorized` | 重新向量化 | RAG |
| `/restapi/ragserver/v1/code/searchInRepo` | 仓库内搜索 | CodeSearch |
| `/restapi/ragserver/v1/rag/repoKeyEnable` | 仓库密钥启用 | RAG |
| `/restapi/ragserver/v1/rag/repoLangExtEnable` | 仓库语言扩展启用 | RAG |
| `/restapi/ragserver/v1/rag/repoKeyDialogEnable` | 仓库密钥对话框启用 | RAG |

### 7.3 单元测试服务 (unit)

| API 路径 | 用途 | 模块 |
|---------|------|------|
| `/restapi/unit/v1/createUnitTask` | 创建批量单测任务 | BatchUnitTest |
| `/restapi/unit/v1/queryUnitTask` | 查询单测任务 | BatchUnitTest |
| `/restapi/unit/v1/exportByTaskId` | 下载单测结果 | BatchUnitTest |
| `/restapi/unit/v1/cancelUnitTask` | 取消单测任务 | BatchUnitTest |
| `/restapi/unit/v1/deleteUnitTask` | 删除单测任务 | BatchUnitTest |
| `/restapi/unit/v1/isPendingTask` | 检查待处理任务 | BatchUnitTest |

### 7.4 数据收集 API

| API 路径 | 用途 |
|---------|------|
| `/api/starspark/v1/agent/collect/chatDataContent` | 聊天数据收集 |
| `/api/starspark/v1/agent/collect/codeAccept` | 代码接受收集 |
| `/api/starspark/v1/agent/collect/commitCodeData` | 提交代码数据收集 |
| `/api/starspark/v1/agent/collect/uploadRequestTime` | 请求时间分析 |
| `/api/starspark/v1/agent/collect/unitTestStatistics` | 单测统计收集 |
| `/api/starspark/v1/agent/collect/commitUnitTestData` | 单测数据提交 |
| `/api/starspark/v1/agent/collect/generateUnitTestData` | 单测生成数据 |
| `/api/starspark/v1/agent/code/queryUnitTestQueueInfo` | 单测队列信息 |

---

## 8. 消息统计

### 8.1 JS→Java 消息统计

| 模块 | 消息数 | 含敏感数据 |
|------|--------|-----------|
| CHAT | 18 | 用户输入, 代码 |
| SQL_CHAT | 9 | **数据库密码** |
| CODE_CHECK | 2 | 修复代码 |
| CODE_REVIEW | 4 | 无 |
| CODE_SEARCH | 3 | 无 |
| UNIT_TEST | 7 | 测试代码 |
| BATCH_UNIT_TEST | 4 | 无 |
| UNIT_TEST_BANK | 2 | 无 |
| GIT | 4 | **Git Token** |
| LOGIN | 5 | 无 |
| SETTING | 4 | 配置数据 |
| COMMON | 9 | 代码内容, URL |
| **总计** | **71** | **2 含密码/令牌** |

### 8.2 Java→JS 消息统计

| 模块 | 消息数 | 含敏感数据 |
|------|--------|-----------|
| CHAT | 16 | 无 |
| SQL_CHAT | 9 | 连接信息 |
| CODE_CHECK | 2 | 无 |
| CODE_REVIEW | 3 | 无 |
| CODE_SEARCH | 3 | 无 |
| UNIT_TEST | 7 | 无 |
| BATCH_UNIT_TEST | 3 | 无 |
| UNIT_TEST_BANK | 4 | 无 |
| LOGIN | 3 | 可能含 Token |
| SETTING | 5 | 无 |
| COMMON | 3 | 无 |
| GIT | 1 | 无 |
| USER | 1 | 无 |
| **总计** | **60** | **0 含加密数据** |

---

## 9. 跨文档引用

| 文档 | 内容 | 关联 |
|------|------|------|
| doc 04 | WebSocket 协议格式 | MessageDto/ResponseDto 定义 |
| doc 06 | 命令体系参考 | 109 个 CommandEnum 值 |
| doc 08 | 认证流程 | 登录链路, Token 管理 |
| doc 65 | WebView 前端完整分析 | JS Bridge, 路由, Stores |
| doc 74 | 安全审计报告 | OWASP Top 10 发现 |
| doc 85 | View/UI/StatusBar 分析 | WebViewWindowPanel God Object |
| doc 94 | 启动与消息映射 | 双向消息映射体系 |
