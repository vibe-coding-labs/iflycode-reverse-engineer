## 8. GitReviewService — Git 评审服务

**类签名:** `public class com.aicode.agent.service.GitReviewService`
**源文件:** `sk` (混淆后)
**包路径:** `com.aicode.agent.service`

### 字段

无实例或静态字段。

### 方法

#### public 方法 (外部API) — 8个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `String` | `static removeMarkdownCodeBlocks(String)` | 移除Markdown代码块 |
| `String` | `static H(Object)` | 辅助方法(混淆名) |
| `void` | `static sendGitDiffRequest(String, Project)` | 发送Git Diff请求 |
| `void` | `static getCommitMessage(Project, String, JsonObject)` | 获取提交消息 |
| `void` | `static handleAgentAction(CommandEnum, JsonObject, Project)` | 处理Agent动作 |
| `JsonObject` | `static getGiffReview(String, JsonObject)` | 获取Diff评审 |
| `JsonObject` | `static getGiffDiff(JsonObject)` | 获取Diff差异 |
| `void` | `static handleAction(WebViewDataTypeEnum, JsonObject, Project)` | 处理WebView动作 |
| `void` | `static sendCodeReviewRequest(JsonObject, Project)` | 发送代码评审请求 |

#### private 方法 (内部实现) — 2个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static ME(EditorTextField, String)` | 内部处理 |
| `void` | `static if(EditorTextField, String)` | 内部处理 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public GitReviewService()` | 公有构造器 |

### 内部类

#### GitReviewService$Ca — 混淆开关表
```java
public class com.aicode.agent.service.GitReviewService$Ca &#123;
  public static final int[] byte;
  public static final int[] enum;
&#125;
```

---

## 9. SqlService — SQL 服务

**类签名:** `public class com.aicode.agent.service.SqlService`
**源文件:** `ml` (混淆后)
**包路径:** `com.aicode.agent.service`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `public static` | `ConcurrentNavigableMap<String, String>` | `SQL_SESSION_ID` | SQL会话ID映射 |
| `private static final` | `org.slf4j.Logger` | `enum` | 日志器(混淆名) |

### 方法

#### public 方法 (外部API) — 14个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static handleSqlTest(JsonObject, Project)` | 处理SQL测试 |
| `JsonObject` | `static saveSource(JsonObject)` | 保存数据源 |
| `JsonObject` | `static getTableList(JsonObject)` | 获取表列表 |
| `JsonObject` | `static testConnect(JsonObject)` | 测试连接 |
| `JsonObject` | `static getSourceType(JsonObject)` | 获取数据源类型 |
| `void` | `static handleSqlSave(JsonObject, Project)` | 处理SQL保存 |
| `void` | `static handleSqlChatMessage(JsonObject, Project)` | 处理SQL聊天消息 |
| `void` | `static handleSqlDelete(JsonObject, Project)` | 处理SQL删除 |
| `void` | `static handleAction(WebViewDataTypeEnum, JsonObject, Project)` | 处理WebView动作 |
| `void` | `static handleAgentAction(CommandEnum, String, JsonObject, Project)` | 处理Agent动作 |
| `JsonObject` | `static getSqlChat(Project, String, JsonObject, String)` | 获取SQL聊天 |
| `void` | `static handleSqlTableList(JsonObject, Project)` | 处理SQL表列表 |
| `void` | `static handleSqlChatStop(Project, JsonObject)` | 处理SQL聊天停止 |
| `JsonObject` | `static getSourceList(JsonObject)` | 获取数据源列表 |

#### private 方法 (内部实现) — 2个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `ConnectConfigDto` | `static sf(JsonObject)` | 解析连接配置 |
| `FirstChatMessage` | `static kF(Project, JsonObject)` | 构建聊天消息 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public SqlService()` | 公有构造器 |

### 内部类

#### SqlService$Ba — 混淆开关表
```java
public class com.aicode.agent.service.SqlService$Ba &#123;
  public static final int[] byte;
  public static final int[] enum;
&#125;
```

---

## 10. CodeCheckService — 代码检查服务

**类签名:** `public class com.aicode.agent.service.CodeCheckService`
**源文件:** `ej` (混淆后)
**包路径:** `com.aicode.agent.service`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `public static final` | `boolean` | `enum` | 混淆开关标志 |

### 方法

#### public 方法 (外部API) — 9个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static handleAction(WebViewWindowPanel, WebViewDataTypeEnum, JsonObject, Project)` | 处理WebView动作 |
| `void` | `static sendCodeCheck(Project)` | 发送代码检查 |
| `CodeCheckListDto` | `static getErrorList(String)` | 获取错误列表 |
| `JsonObject` | `static getErrorResponse(String, String)` | 获取错误响应 |
| `CodeCheckListDto` | `static getErrorListResult(ResponseDto)` | 获取错误列表结果 |
| `JsonObject` | `static fixCodeCheck(JsonObject, Project)` | 修复代码检查 |
| `CodeCheckListDto` | `static getCheckData(JsonObject)` | 获取检查数据 |
| `JsonObject` | `static getAgentChatResponse(JsonObject, MessageDto)` | 获取Agent聊天响应 |
| `void` | `static handleAgentAction(CommandEnum, JsonObject, MessageDto, String, Project)` | 处理Agent动作 |
| `CodeCheckListDto` | `static getList(List&lt;CodeCheckDto&gt;)` | 获取检查列表 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public CodeCheckService()` | 公有构造器 |

### 内部类

#### CodeCheckService$Da — 混淆开关表
```java
public class com.aicode.agent.service.CodeCheckService$Da &#123;
  public static final int[] byte;
  public static final int[] enum;
&#125;
```

---

## 11. CodeSearchService — 代码搜索服务

**类签名:** `public class com.aicode.agent.service.CodeSearchService`
**源文件:** `rm` (混淆后)
**包路径:** `com.aicode.agent.service`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `private static final` | `com.intellij.openapi.diagnostic.Logger` | `enum` | 日志器(混淆名) |

### 方法

#### public 方法 (外部API) — 12个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `JsonObject` | `static getCodeSearchRepos(String, JsonObject)` | 获取代码搜索仓库 |
| `void` | `static handleAgentAction(CommandEnum, JsonObject, String, Project)` | 处理Agent动作 |
| `JsonObject` | `static getCodeSearchLanguage(JsonObject)` | 获取代码搜索语言 |
| `JsonObject` | `static getCodeSearchCode(String, JsonObject)` | 获取代码搜索代码 |
| `JsonObject` | `static requestCopyCode(String)` | 请求复制代码 |
| `void` | `static requestOpenUrl(String)` | 请求打开URL |
| `void` | `static sendCodeRepoRequest(JsonObject, Project)` | 发送代码仓库请求 |
| `void` | `static handleAction(WebViewWindowPanel, WebViewDataTypeEnum, JsonObject, String, Project)` | 处理WebView动作 |
| `void` | `static requestInsertCode(Project, String)` | 请求插入代码 |
| `void` | `static sendCodeSearchRequest(JsonObject, Project)` | 发送代码搜索请求 |
| `void` | `static requestCodeFile(Project, String)` | 请求代码文件 |

#### private 方法 (内部实现) — 4个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static Ld(Application, Project, RequestCaseCodeDto$ValueDTO)` | 内部处理 |
| `void` | `static Te(Application, String, Project, RequestCaseCodeDto$ValueDTO)` | 内部处理 |
| `void` | `static tE(Project, RequestCaseCodeDto$ValueDTO)` | 内部处理 |
| `void` | `static XE(Project, RequestCaseCodeDto$ValueDTO)` | 内部处理 |
| `void` | `static eE(String, Project, RequestCaseCodeDto$ValueDTO)` | 内部处理 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public CodeSearchService()` | 公有构造器 |

### 内部类

#### CodeSearchService$Aa — 混淆开关表
```java
public class com.aicode.agent.service.CodeSearchService$Aa &#123;
  public static final int[] byte;
  public static final int[] enum;
&#125;
```

#### CodeSearchService$ga — TypeToken子类
```java
public class com.aicode.agent.service.CodeSearchService$ga
    extends TypeToken<List&lt;CodeRepoInfoDto&gt;> &#123;&#125;
```

#### CodeSearchService$ia — TypeToken子类
```java
public class com.aicode.agent.service.CodeSearchService$ia
    extends TypeToken<List&lt;CodeInfoDto&gt;> &#123;&#125;
```

---
