# 23 Agent 内部架构与 Prompt 模板

> 基于 `agent/bin/index.js` + `worker.js` 逆向分析

## Agent 内部架构

### Service 层级

```
ServiceBase (base class)
    ├─ ChatService      — 对话、评审、提交信息、内联聊天
    ├─ CodeService      — 代码补全
    ├─ CommonService     — 服务器资源、推荐
    ├─ GitService        — Git 仓库、语言列表、代码搜索
    ├─ LoginService      — 认证、SSO、Token 管理
    ├─ RagService        — RAG 搜索、仓库索引
    ├─ ReportService     — 数据收集上报
    ├─ SqlService        — SQL 生成/优化、达梦 DDL
    ├─ TestService       — 单元测试、批量单测
    └─ UserService       — 权限、设置、知识库、反馈
```

### Controller 路由层

```
BaseController (base class, handleMessageEvent)
    ├─ TalkController        — TALK:*, GIT:*
    ├─ CodeController        — CODE:*
    ├─ InlineChatController  — DIALOG:*
    ├─ TaskController        — TEST:*
    ├─ UserController        — USER:*
    ├─ SqlController         — SQL:*
    ├─ LogController         — LOG:*
    ├─ ActionController      — 操作上报
    ├─ DialogController      — 对话管理
    └─ AbortController       — 请求取消
```

### 进程间通信 (IPC)

多个 Agent 进程实例通过 Unix Socket 通信：

```
路径: ~/.iflycode/bin/unix.sock
     Windows: \\?\pipe\~\.iflycode\bin\unix.sock
```

IPC 消息类型：

| type | 方向 | 说明 |
|------|------|------|
| `login` | 主进程 → 其他进程 | 广播登录成功 (`loginId + "||" + clientId`) |
| `logout` | 主进程 → 其他进程 | 广播退出登录 (`loginId`) |

当 Agent A 完成登录后，通过 IPC 通知同一用户的其他 Agent 实例，使其也能获取 token。

### 对话持久化 (Dialog)

Agent 使用 nedb (`@seald-io/nedb`) 存储对话历史：

```
存储路径: ~/.iflycode/
上限: 1500 条记录
过期: 90 天
清理间隔: 1800 秒 (30分钟)
```

数据结构：

```json
{
  "id": "uuid",
  "startTime": 1713744000000,
  "endTime": 1713744010000,
  "platform": "idea",
  "type": "chat",
  "sessionId": "session-uuid",
  "title": "对话标题",
  "data": { "command": "TALK:ASK", ... },
  "user": { "role": "user", "content": "..." },
  "assistant": { "role": "assistant", "content": "...", "reasonContent": "..." }
}
```

### 数据库连接 (SQL 功能)

Agent 使用 knex.js 连接外部数据库（MySQL、PostgreSQL、达梦），支持以下操作：

| 操作 | 方法 |
|------|------|
| 测试连接 | `BaseKnex.test()` → `SELECT 1+1 as result` |
| 获取表列表 | 查询 `information_schema` |
| 获取表结构 | `SHOW CREATE TABLE` (MySQL) |
| 获取表大小 | `SELECT count(*)` |

支持的数据库类型：

| 客户端 | 数据库 |
|--------|--------|
| `mysql2` | MySQL |
| `pg` | PostgreSQL |
| `dmdb` | 达梦 (DM) |

### Tree-sitter 代码解析

Agent 使用 WebAssembly 版本的 tree-sitter 解析代码结构：

| 语言 | WASM 文件 |
|------|-----------|
| Java | `tree-sitter-java.wasm` |
| Python | `tree-sitter-python.wasm` |
| JavaScript | `tree-sitter-javascript.wasm` |
| TypeScript | `tree-sitter-typescript.wasm` |
| TSX | `tree-sitter-tsx.wasm` |
| C | `tree-sitter-c.wasm` |
| C++ | `tree-sitter-cpp.wasm` |
| C# | `tree-sitter-c_sharp.wasm` |
| Go | `tree-sitter-go.wasm` |
| Vue | `tree-sitter-vue.wasm` |

用于提取：
- 类/方法结构 (`structure`)
- import 依赖 (`imports`)
- 函数签名
- 代码上下文

## Prompt 模板体系

Agent 在发送请求到云端前，会构建包含上下文的 prompt。以下是所有模板。

### 代码补全模板

#### COMPLETE_CODE

```
<文件依赖结构信息>
本文件类结构信息如下：
$[structure]$

$[imports]$
</文件依赖结构信息>

<相似代码段>
$[similarStr]$
</相似代码段>

<光标下方的代码段>
$[suffixCode]$
</光标下方的代码段>

<光标上方的代码段>
$[prefixCode]$
</光标上方的代码段>
```

#### COMPLETE_CODE_VUE (Vue 文件专用)

```
<文件依赖结构信息>
$[imports]$
</文件依赖结构信息>

<相似代码段>
$[similarStr]$
</相似代码段>

<光标下方的代码段>
$[suffixCode]$
</光标下方的代码段>

<光标上方的代码段>
$[prefixCode]$
</光标上方的代码段>
```

### 对话模板

#### GENERAL_ASSISTANT (通用助手)

```
你是一名人工智能编程助手，用户正在与你进行对话，请按照如下要求完成对话：
1.你的名字是"iFlyCode"
2.你的专业知识严格限于软件开发主题，对于与软件开发无关的问题，只需回答你是AI编程助手即可
3.如果我的问题中含有"引用代码"，可以做为问题的关联上下文信息，如果"引用代码"和问题无关，请忽略"引用代码"，直接回复问题即可
$IF:withLang[4.当前你的模式设定为 $[language]$模式，请用 $[language]$ 为用户编写代码问题]$
5.首先请一步步思考，给出针对问题的思考过程
请首先总结我的要求
```

#### MATE_ASSISTANT (iFlyMate 助手)

```
你是一名人工智能编程助手。
当询问你的姓名时，你必须回答"iFlyCode"。
你的专业知识严格限于软件开发主题。
对于与软件开发无关的问题，只需回答你是AI编程助手即可。
请你遵循以下要求来回答用户的问题：
1.首先请一步步思考 - 用伪代码详细描述要构建的内容的计划。
2.然后在单个代码块中输出代码。
3.在答案中使用 Markdown 格式。
4.确保在 Markdown 代码块的开头包含编程语言名称。
5.如果我的问题中含有"引用代码"，可以做为问题的关联上下文信息。如果"引用代码"和问题无关，请忽略"引用代码"，直接回复问题即可。
6.如果我没有特别指定语言，那默认使用中文回复。
7.请不用担心你的回复会被打断，尽量输出你的推理过程。
```

#### TALK_REFERENCE_CODE (引用代码模板)

```
引用的代码:
From the file: $[path]$
```$[lang]$
$[code]$
```
```

#### ASSISTANT_ANSWER (系统消息)

```
您要求我作为一个名为"iFlyCode"的人工智能助手，专注于软件开发主题。我的能力仅限于提供与软件开发相关的回答和建议。
$IF:withLang[当您提出问题时，我会尽力以$[language]$语言的视角来解答，确保解决方案或解释适用于$[language]$环境。]$
```

### SQL 模板

#### SQL_GENERATE_PROMPT

```
你是一位$[type]$数据库专家，根据下面【要求】和【用户输入】生成SQL语句。

【表结构】：$[structure]$

【用户输入】：$[inputText]$

【要求】：
1. 精确理解用户用自然语言描述的SQL需求，包括数据表、字段、条件等关键信息。
2. 分析数据表结构和字段关系，确定查询的逻辑和语法结构。
3. 编写SQL语句，确保语法正确且逻辑清晰，能够准确反映用户的需求。
4. 使用markdown格式返回SQL语句。
5. 返回结果包含推理过程和说明。
```

#### SQL_OPTIMIZE_PROMPT

```
你是一位$[type]$数据库专家，根据下面【要求】和【用户输入】优化用户输入的SQL语句。

【表结构】：$[structure]$

【用户输入】：$[inputText]$

【要求】：
1. 分析用户提供的原始SQL语句，理解其查询逻辑和目标。
2. 检查数据库的索引设计、表结构及表数据量，找出可能的性能瓶颈。
3. 重写SQL语句，优化查询逻辑，添加或调整索引，以提高查询效率。
4. 如果sql没有问题就无需改写。
5. 请把优化后的sql语句及索引创建语句放在一个用markdown语法的sql代码块中。
5. 返回结果包含推理过程和说明。
```

### Git 模板

#### GIT_COMMIT_MESSAGE

```
$[changeInfo]$

【说明】：
1.变更的代码中行首带有减号（-）和加号（+）的代码行是变更的代码块，-表示该行代码被删除，+表示该行代码是新增的。请仔细分析新增和删除的代码逻辑。
2.生成的提交信息，需要包含删除的代码文件、变更的代码文件、新增的代码文件的提交信息，文件名称不需要包含路径
...
```

#### GIT_REVIEW_CNT (代码评审)

```
$IF:JAVA[当前类上下文结构信息：
$[structure]$

当前类import包信息：
$[imports]$

]需要进行评审的代码：
$[code]$
```

### 单元测试模板

#### UNIT_TEST_PROMPT_CODE (通用)

```
【代码上下文】
当前被测函数所在的文件结构信息如下：
$[structure]$

【被测代码】
$[code]$

【单测用例列表】
$[cases]$

【单元测试代码】
$[includes]$

<todo>
```

#### UNIT_TEST_PROMPT_CASE (用例生成)

```
【被测代码】
$[code]$
【结构信息】
$[structure]$
```

### 其他模板

#### QUESTION_REPHRASED (问题重写)

```
根据对话上下文对用户问题进行指代消除和扩展，返回3个优化后的问题，按照<question></question>格式返回。
用户问题：$[question]$
```

#### INLINE_CHAT

```
$[prefixCode]$

$[suffixCode]$
```

### 模板语法

| 语法 | 说明 |
|------|------|
| `$[variable]$` | 变量替换 |
| `$IF:condition[...]$` | 条件渲染 |

## Agent 配置常量

| 常量 | 值 | 说明 |
|------|-----|------|
| `NAME` | `iflycode-agent` | Agent 名称 |
| `ASSISTANT_NAME` | `iFlyCode` | 助手显示名 |
| `VERSION` | `3.4.2` | Agent 版本 |
| `INSTALL_DIR` | `.iflycode` | 安装目录 |
| `DEFAULT_API_URL` | `https://saas.api.example.com` | 默认 API 地址 |
| `DEFAULT_TRACE_API_URL` | `https://saas.api.example.com/v1/traces` | 默认 APM 地址 |
| `autoDestroyTimeLimit` | `300000` (5分钟) | 自动销毁超时 |
| `workspaceAliveTime` | `600000` (10分钟) | 工作区存活时间 |
| `MAX_CONTENT_SIZE` | `100000` | 最大内容长度 |
| `MAX_SIMILAR_ANALYSIS_SIZE` | `30000` | 最大相似分析长度 |
| `MAX_COMPARE_LINES` | `60` | 最大对比行数 |
| `MAX_COMPARE_STEP` | `30` | 最大对比步数 |
| `SQL_TABLE_CACHE_TIME` | `600000` (10分钟) | SQL 表结构缓存时间 |
| `SQL_STRUCTURE_MAX_LENGTH` | `20000` | SQL 结构最大长度 |
| `SKIP_LOGIN` | `false` | 跳过登录 |
| `wasmCheck` | `10` | WASM 检查次数 |

## 单元测试文件模板

Agent 内置了以下 Java 单测框架模板：

| 文件 | 框架组合 |
|------|---------|
| `JUnit4.java.ft` | JUnit 4 |
| `JUnit4&Mockito.java.ft` | JUnit 4 + Mockito |
| `JUnit4&Powermock.java.ft` | JUnit 4 + PowerMock |
| `JUnit5.java.ft` | JUnit 5 |
| `JUnit5&Mockito.java.ft` | JUnit 5 + Mockito |
| `SpringBootTest&Mockito.java.ft` | SpringBootTest + Mockito |
| `TestNG&Mockito.java.ft` | TestNG + Mockito |

模板使用 FreeMarker/Velocity 风格的变量替换，由 Agent 在本地生成测试代码骨架后发送到云端补全。

## 消息处理生命周期

### 请求去重

```
Agent 使用 handlingCommands Map 跟踪进行中的请求:
key: `${command}_${path}`
value: Promise resolve 函数

同一命令+路径的重复请求会排队等待前一个完成。
```

### 请求取消

```
1. Plugin 发送 ABORT 命令
2. Agent 调用 ServiceBase.abort(requestId)
3. 通过 AbortController 取消 HTTP fetch
4. 清理 handlingCommands 中的记录
5. 返回取消确认
```

### 文件信息收集

```
getFileInfo(message):
  1. 读取文件内容
  2. 使用 tree-sitter 解析代码结构
  3. 提取选中代码范围
  4. 获取 import 列表
  5. 获取类/方法结构
  6. 返回 { fileInfo, selected }
```

## Agent 启动参数

```
<node_binary> <index.js> <os_arch>

示例:
./x86_64_darwin_node index.js darwin-arm64
./x86_64_linux_node index.js linux-x64
x86_64_windows_node.exe index.js windows-x64
```

`os_arch` 参数用于选择正确的 sqlite3 native addon。
