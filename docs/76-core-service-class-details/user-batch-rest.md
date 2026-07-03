## 12. UserService — 用户服务

**类签名:** `public class com.aicode.agent.service.UserService`
**源文件:** `zf` (混淆后)
**包路径:** `com.aicode.agent.service`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `private static final` | `com.intellij.openapi.diagnostic.Logger` | `byte` | 日志器(混淆名) |
| `private static` | `String` | `enum` | 登录URL(混淆名) |
| `public static` | `boolean` | `goTo` | 跳转标志 |

### 方法

#### public 方法 (外部API) — 17个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static showMessage(Project)` | 显示消息 |
| `void` | `static repaintModelComboBox(ComboBox)` | 重绘模型下拉框 |
| `void` | `static SetModel(JsonObject)` | 设置模型 |
| `void` | `static setGoTo(boolean)` | 设置跳转标志 |
| `boolean` | `static isGoTo()` | 获取跳转标志 |
| `void` | `static setItem(ComboBox, List&lt;CodeModel&gt;)` | 设置下拉框项 |
| `String` | `static getLoginUrl()` | 获取登录URL(无参) |
| `void` | `static send2WebShowOperateGuidance(JsonObject, Project)` | 发送操作指引到Web |
| `void` | `static logout(Project)` | 登出 |
| `JsonArray` | `static sortJsonArray(JsonArray, List&lt;String&gt;)` | 排序JSON数组 |
| `void` | `static handleAgentAction(CommandEnum, JsonObject, String, Object, Project)` | 处理Agent动作 |
| `void` | `static setLoginUrl(String)` | 设置登录URL |
| `JsonObject` | `static getLoginInfo(JsonObject, Project)` | 获取登录信息 |
| `JsonObject` | `static getLoginUrl(String)` | 获取登录URL(有参) |
| `void` | `static getUserPermissions(JsonObject, Project)` | 获取用户权限 |
| `void` | `static handleAction(WebViewDataTypeEnum, Project)` | 处理WebView动作 |
| `void` | `static clearIcon(Project)` | 清除图标 |
| `void` | `static sendWriterConfig(Project, JsonObject)` | 发送写入器配置 |
| `JsonObject` | `static getUserModelList(JsonObject, MessageDto)` | 获取用户模型列表 |

#### private 方法 (内部实现) — 5个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static lE(Project)` | 内部处理 |
| `void` | `static bd(Project)` | 内部处理 |
| `void` | `static Wf(MessageDto, Project)` | 内部处理 |
| `void` | `static uD()` | 内部处理 |
| `void` | `static ef(ItemEvent)` | 内部处理 |
| `void` | `static VC(MessageDto)` | 内部处理 |
| `void` | `static od(ComboBox)` | 内部处理 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public UserService()` | 公有构造器 |

### 内部类

#### UserService$Ja — 混淆开关表
```java
public class com.aicode.agent.service.UserService$Ja &#123;
  public static final int[] byte;
  public static final int[] enum;
&#125;
```

#### UserService$da — 通知动作
```java
public class com.aicode.agent.service.UserService$da extends NotificationAction &#123;
  public final Project enum;
  public void actionPerformed(AnActionEvent, Notification);
&#125;
```
IntelliJ通知动作，用于处理用户点击通知后的操作。

#### UserService$ea — TypeToken子类
```java
public class com.aicode.agent.service.UserService$ea
    extends TypeToken<List&lt;FunctionModelInfo&gt;> &#123;&#125;
```

#### UserService$la — TypeToken子类
```java
public class com.aicode.agent.service.UserService$la
    extends TypeToken<List&lt;String&gt;> &#123;&#125;
```

---

## 13. TemplateRequestService — 模板请求服务

**类签名:** `public class com.aicode.template.request.TemplateRequestService`
**源文件:** `TemplateRequestService.java` (未混淆)
**包路径:** `com.aicode.template.request`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `private static final` | `com.intellij.openapi.diagnostic.Logger` | `LOG` | 日志器 |
| `public static final` | `Cache<String, FileRequestDto>` | `classModelRenders` | 类模型渲染缓存 |
| `public static final` | `int` | `MAX_TOKEN_CHAR_LENGTH` | 最大Token字符长度 |
| `public static final` | `int` | `MAX_REQUEST_LIMIT` | 最大请求限制 |
| `public static final` | `long` | `RETRY_WAIT_TIME` | 重试等待时间 |
| `private static final` | `int` | `CLASS_CACHE_LIMIT` | 类缓存限制 |

### 方法

#### public 方法 (外部API) — 28个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `synchronized void` | `static handleAgentAction(CommandEnum, JsonObject, MessageDto, String, Project)` | 处理Agent动作(同步) |
| `synchronized void` | `static handleRequestErrorTestCase(ResponseDto, CommandEnum, MessageDto)` | 处理请求错误测试用例(同步) |
| `void` | `static analysisString(String, Method)` | 分析字符串 |
| `void` | `static addCase(UnitTestDto$DataDTO$FunctionDataDTO, Method)` | 添加测试用例(2参数) |
| `void` | `static setParent(ResolvedBranch, Boolean, List&lt;CaseBranch&gt;)` | 设置父分支 |
| `void` | `static setPrev(ResolvedBranch, Boolean, List&lt;CaseBranch&gt;)` | 设置前驱分支 |
| `void` | `static setAfter(ResolvedBranch, Boolean, List&lt;CaseBranch&gt;)` | 设置后继分支 |
| `String` | `static extractTagValue(String, String)` | 提取标签值 |
| `int` | `static countMatches(String, String)` | 统计匹配次数 |
| `MethodRequestResult` | `static requestAI(PsiClass, Type, PsiMethod, TypeDictionary, GeneratorTemplateConfig, String, Project, List&lt;MessageDto&gt;, Set&lt;Method&gt;, FileRequestDto, Module, Map<String, String>)` | 请求AI(12参数) |
| `List&lt;MessageDto&gt;` | `static requestAI(String, PsiClass, Type, TypeDictionary, GeneratorTemplateConfig, String, Project, boolean, Module, Set&lt;Method&gt;)` | 请求AI(10参数) |
| `String` | `static convertKey(String, String)` | 转换Key |
| `Boolean` | `static containFile(String, String)` | 判断是否包含文件 |
| `boolean` | `static shouldBeTested(PsiMethod, PsiClass, GeneratorTemplateConfig)` | 判断方法是否应被测试 |
| `synchronized boolean` | `static isModelReturned(String, String)` | 判断模型是否已返回(同步) |
| `synchronized boolean` | `static isAllReturned(String)` | 判断是否全部返回(同步) |
| `synchronized FileRequestDto` | `static getReturnedFile(String)` | 获取已返回文件(同步) |
| `boolean` | `static remove(String, String)` | 移除(2参数) |
| `boolean` | `static remove(String, String, boolean)` | 移除(3参数) |
| `synchronized boolean` | `static isModelReturned(String, FileRequestDto)` | 判断模型是否已返回(同步,2参数) |
| `int` | `static calculateRequestAiInterval(int)` | 计算请求AI间隔 |
| `int` | `static calculateGeneratorTimes(int, int)` | 计算生成次数 |
| `void` | `static clearCache()` | 清除缓存 |

#### private 方法 (内部实现) — 17个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static addCase(String, Method, List&lt;CaseResult&gt;)` | 添加测试用例(3参数) |
| `String` | `static getMethodName(String, List&lt;CaseResult&gt;)` | 获取方法名 |
| `void` | `static convertException(String, CaseResult, Method)` | 转换异常 |
| `String` | `static resolveMessage(String)` | 解析消息 |
| `CaseParam` | `static convertOutput(String, Method)` | 转换输出 |
| `CaseParam` | `static convertJsonObject(String, String)` | 转换JSON对象 |
| `Map<String, CaseParam>` | `static convertInput(String, Method)` | 转换输入 |
| `void` | `static addMock(String, List&lt;ToMockMethod&gt;)` | 添加Mock |
| `void` | `static addBranches(String, Method, List&lt;CaseBranch&gt;)` | 添加分支 |
| `void` | `static recursionBranches(ResolvedBranch, List&lt;String&gt;, List&lt;CaseBranch&gt;)` | 递归分支 |
| `void` | `static resolveAllBranches(ResolvedBranch, List&lt;CaseBranch&gt;)` | 解析所有分支 |
| `void` | `static checkChildren(List&lt;ResolvedBranch&gt;, List&lt;String&gt;, List&lt;CaseBranch&gt;)` | 检查子节点 |
| `boolean` | `static matchIfBranch(String, String)` | 匹配if分支 |
| `boolean` | `static checkBranchInModelData(ResolvedBranch, List&lt;String&gt;, List&lt;CaseBranch&gt;)` | 检查分支在模型数据中 |
| `void` | `static resolveCaseBranch(ResolvedBranch, Boolean, List&lt;CaseBranch&gt;)` | 解析用例分支 |
| `String` | `static convertMethodName(String)` | 转换方法名 |
| `String` | `static convertBaseMethodName(String)` | 转换基础方法名 |
| `String` | `static caseHandle(String, String)` | 用例处理 |
| `String` | `static caseMocks(String, String)` | 用例Mock |
| `void` | `static appendTypeBody(Type, StringBuilder, PsiClass, boolean, Set&lt;String&gt;, Project, Module, TypeDictionary, Map<String, String>)` | 追加类型体 |
| `boolean` | `static calculateString2MaxToken(StringBuilder)` | 计算字符串是否超过最大Token |
| `void` | `static getBodyContent(StringBuilder, PsiMethod, PsiClass, List&lt;String&gt;, boolean, int)` | 获取方法体内容 |
| `void` | `static appendMethodText(StringBuilder, String, String)` | 追加方法文本 |

#### lambda 方法 — 16个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `boolean` | `static lambda$requestAI$15(String, Method)` | lambda |
| `void` | `static lambda$requestAI$14(FileRequestDto, MethodRequestResult)` | lambda |
| `boolean` | `static lambda$requestAI$13(Project, PsiClass, PsiMethod, String, UnitTestDto$DataDTO$FunctionDataDTO)` | lambda |
| `String` | `static lambda$requestAI$12(MethodCall)` | lambda |
| `boolean` | `static lambda$requestAI$11(Type, MethodCall)` | lambda |
| `boolean` | `static lambda$requestAI$10(String, Method)` | lambda |
| `boolean` | `static lambda$resolveCaseBranch$9(ResolvedBranch, CaseBranch)` | lambda |
| `boolean` | `static lambda$checkBranchInModelData$8(Optional, String)` | lambda |
| `boolean` | `static lambda$checkBranchInModelData$7(ResolvedBranch, String)` | lambda |
| `boolean` | `static lambda$resolveAllBranches$6(ResolvedBranch, CaseBranch)` | lambda |
| `void` | `static lambda$convertInput$5(Map, CaseParam, Param)` | lambda |
| `boolean` | `static lambda$getMethodName$4(String, CaseResult)` | lambda |
| `void` | `static lambda$handleRequestErrorTestCase$3(MessageDto, MethodRequestResult)` | lambda |
| `void` | `static lambda$handleRequestErrorTestCase$2(MessageDto, MethodRequestResult)` | lambda |
| `boolean` | `static lambda$handleRequestErrorTestCase$1(MessageDto, MethodRequestResult)` | lambda |
| `boolean` | `static lambda$handleAgentAction$0(MessageDto, MethodRequestResult)` | lambda |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public TemplateRequestService()` | 公有构造器 |

**注:** 此类未混淆，保留原始方法名，是最完整的可读服务类。

---

## 14. BatchUnitTestService — 批量单测服务

**类签名:** `public final class com.aicode.test.BatchUnitTestService`
**源文件:** `gc` (混淆后)
**包路径:** `com.aicode.test`

### 字段

| 访问修饰 | 类型 | 名称 | 说明 |
|----------|------|------|------|
| `private static final` | `org.slf4j.Logger` | `enum` | 日志器(混淆名) |

### 方法

#### public 方法 (外部API) — 10个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static batchUnitTestCreate(String, Project)` | 批量单测创建 |
| `void` | `static batchUnitTestDelete(String, Project)` | 批量单测删除 |
| `JsonObject` | `static codeBatchUnitTestList(JsonObject)` | 代码批量单测列表 |
| `void` | `static handleAction(WebViewDataTypeEnum, String, Project)` | 处理WebView动作 |
| `JsonObject` | `static batchUnitTestMessage(boolean, String)` | 批量单测消息 |
| `void` | `static batchUnitTestDownload(String, Project)` | 批量单测下载 |
| `void` | `static batchUnitTestList(Project)` | 批量单测列表 |
| `void` | `static handleAgentAction(CommandEnum, JsonObject, MessageDto, String, Project)` | 处理Agent动作 |
| `JsonObject` | `static batchUnitTestDownload(JsonObject, MessageDto)` | 批量单测下载(2参数) |

#### private 方法 (内部实现) — 1个

| 返回类型 | 方法签名 | 说明 |
|----------|----------|------|
| `void` | `static oA(File)` | 内部处理 |

#### 构造器

| 签名 | 说明 |
|------|------|
| `public BatchUnitTestService()` | 公有构造器 |

### 内部类

#### BatchUnitTestService$g — TypeToken子类
```java
public class com.aicode.test.BatchUnitTestService$g
    extends TypeToken<List&lt;BatchUnitTestDto&gt;> &#123;&#125;
```

#### BatchUnitTestService$l — 混淆开关表
```java
public class com.aicode.test.BatchUnitTestService$l &#123;
  public static final int[] byte;
  public static final int[] enum;
&#125;
```

---
