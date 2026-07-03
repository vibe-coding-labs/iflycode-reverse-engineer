## 2. 每个类的完整反编译结果

### 2.1 template/ 根包

#### 2.1.1 AssertUtil

```
public class com.aicode.template.AssertUtil &#123;
  // 常量
  public static final String J4_TEMPLATE;      // JUnit4 模板标识
  public static final String J4_ASSERT;         // "org.junit.Assert"
  public static final String J5_ASSERTIONS;     // "org.junit.jupiter.api.Assertions"
  public static boolean J5;                     // 是否 JUnit5
  public static final String KONG_WORDS;        // 空/null 关键字
  public static final String KONG_LIST_WORDS;   // 空集合关键字
  public static final String[] NULL_KEY_WORDS;  // null 关键字数组
  public static final List&lt;String&gt; BASIC_TYPES; // 基本类型列表

  // 核心方法
  public static String assertResult(Type, String, String, String, boolean, String);
    // 根据返回类型分发断言生成：
    // - 空返回值 -> 跳过断言（"期望返回值是空，不进行断言"）
    // - 数组 -> arrayAssert()
    // - Map -> mapAssert()
    // - JSONObject -> jsonAssert()
    // - Collection -> collectionAssert()
    // - 基本类型 -> basicAssert()
    // - 实体类 -> entityAssert()
    // - 未知类型 -> "未知的类型"

  public static String assertParams(Type, String, String);
    // 参数断言生成，逻辑类似 assertResult

  public static String getName(String);
    // 从断言模板中提取变量名
&#125;
```

**关键逻辑**: 断言代码生成根据返回类型分7种策略，支持 JUnit4 (`assertEquals`) 和 JUnit5 (`Assertions.assertEquals`) 两种断言框架。中文常量："期望返回值是空，不进行断言"、"未知的类型"。

#### 2.1.2 CodeRefactorUtil

```
public class com.aicode.template.CodeRefactorUtil &#123;
  public static final String COMMENTED_IMPORT_TOKEN;  // 注释掉的 import 标记

  public void uncommentImports(PsiFile, Project);
    // 取消注释 import 语句：找到被注释的 import，替换为正常 import

  private PsiElement extractImportStatement(PsiFile, Project, String);
    // 提取 import 语句

  private PsiElement createGroovyImport(Project, String);
    // 创建 Groovy import 语句

  private PsiElement createImportStatementOnDemand(Project, String, boolean);
    // 创建 on-demand import 语句
&#125;
```

#### 2.1.3 ExcludeMethodEnum

```
public final class ExcludeMethodEnum extends Enum&lt;ExcludeMethodEnum&gt; &#123;
  ABSTRACT("abstract", false),   // 抽象方法，不可写
  NATIVE("native", false),       // native 方法，不可写
  GETTER("getter", true),        // getter，可写但通常跳过
  SETTER("setter", true),        // setter，可写但通常跳过
  MAIN("main", true),            // main 方法
  EQUALS("equals", true),        // equals 方法
  TOSTRING("toString", true),    // toString 方法
  HASHCODE("hashCode", true);    // hashCode 方法

  private String name;
  private boolean canWrite;      // 是否可写测试
&#125;
```

#### 2.1.4 FileTemplateConfig

```
public class FileTemplateConfig &#123;
  public static final int DEFAULT_MAX_RECURSION_DEPTH;  // 默认最大递归深度

  // 配置项
  private int maxRecursionDepth;                    // 类型解析递归深度
  private boolean reformatCode;                     // 是否重新格式化代码
  private boolean replaceFqn;                       // 是否替换全限定名
  private boolean optimizeImports;                  // 是否优化 import
  private boolean stubMockMethodCallsReturnValues;  // 是否 stub mock 方法返回值
  private boolean ignoreUnusedProperties;           // 忽略未使用属性
  private boolean replaceInterfaceParamsWithConcreteTypes;  // 接口参数替换为具体类型
  private int maxNumOfConcreteCandidatesToReplaceInterfaceParam;
  private int minPercentOfExcessiveSettersToPreferMapCtor;  // setter 过多时偏好 Map 构造器
  private int minPercentOfInteractionWithPropertiesToTriggerConstructorOptimization;
  private boolean generateTestsForInternalMethods;  // 为内部方法生成测试
  private boolean renderInternalMethodCallStubs;    // 渲染内部方法调用 stub
  private boolean throwSpecificExceptionTypes;      // 抛出特定异常类型
&#125;
```

#### 2.1.5 TemplateGenerator（核心入口）

```
public class TemplateGenerator &#123;
  private static final Logger LOG;
  public static final TemplateGenerator INSTANCE;          // 单例
  public CreateTestFileTask batchCreateTestFileTask;       // 批量测试任务
  private CreateTestMethodTask createTestMethodTaskTask;   // 单方法测试任务

  // 批量测试入口
  public List&lt;String&gt; batchTestClass(Project, List&lt;VirtualFile&gt;, PsiManager, GeneratorConfig);
    // 提交到 PluginStartupActivity.handleExecutorService 异步执行

  public static void batchTestClass(Project, GeneratorConfig);
    // 检查是否有未完成任务（"上一个单元测试任务还未完成"）
    // 创建 CreateTestFileTask 并通过 ProgressManager.run() 执行

  // 单方法测试入口
  public static void createTestClass(Project, MethodGeneratorConfig);
    // 1. 检查是否有未完成任务
    // 2. 收集 PsiMethod 集合（runReadAction）
    // 3. 创建 CreateTestMethodTask（runWriteAction later）
    // 4. 调用 SocketMessageHandleListener.send2Web() 发送结果
&#125;
```

**关键交互**: 调用 `PluginStartupActivity.handleExecutorService`（异步执行器）、`ProgressManager`（进度管理）、`SocketMessageHandleListener.send2Web()`（WebSocket 通信）。

#### 2.1.6 TestSubjectInspector

```
public class TestSubjectInspector &#123;
  private static final Set&lt;String&gt; SCALA_FUTURE_TYPES;  // Scala Future 类型
  private final boolean generateTestsForInternalMethods;
  private final Set&lt;Method&gt; selectedMethods;

  public boolean hasTestableInstanceMethod(List&lt;Method&gt;);   // 是否有可测实例方法
  public boolean hasSetFields(List&lt;Field&gt;);                 // 是否有 setter 字段
  public boolean shouldBeTested(Method);                    // 方法是否应被测试
  public boolean isMethodCalled(Method, Method, CaseResult);// 方法是否被调用
  public boolean isJavaFuture(Type);                        // 是否 Java Future
  public Method findOptimalConstructor(Type);               // 查找最优构造函数
  public boolean isMethodOwnedByClass(Method, Type);        // 方法是否属于类
  public boolean isNotInjectedInDiClass(Field, Type);       // DI 类中是否未注入
  public boolean hasAccessibleCtor(Type);                   // 是否有可访问构造函数

  private static Method findBiggestValidConstructor(Type);  // 查找最大有效构造函数
  private static boolean isImplements(Type, String);        // 是否实现接口
  private static boolean isSameGenericType(Type, String);   // 泛型类型匹配
&#125;
```

#### 2.1.7 TestTemplateContextBuilder

```
public class TestTemplateContextBuilder &#123;
  private final MockBuilderFactory mockBuilderFactory;
  private final MethodReferencesBuilder methodReferencesBuilder;
  private final Cache<String, Type> typeCache;                    // 类型缓存
  private final Cache<String, TypeDictionary> typeDictionaryCache;// 类型字典缓存
  private final GeneratorTemplateConfig templateConfig;

  public Map<String, Object> build(String, FileTemplateContext, Properties);
    // 构建模板上下文：
    // 1. initTemplateContext() - 初始化基础参数
    // 2. 解析 PsiClass -> Type
    // 3. resolveMethodReferences() - 解析方法引用
    // 4. resolveMethodCallFields() - 解析方法调用字段
    // 5. resolveMethodVariables() - 解析方法变量
    // 6. resolveMethodCallByCaseResult() - 按用例结果解析
    // 7. populateDateFields() - 填充日期字段
    // 8. 组装所有模板参数

  private List&lt;String&gt; resolveClasspathJars(FileTemplateContext);
    // 解析 classpath JAR 列表

  private JavaVersion getJavaVersion(Module);
    // 获取 Java 版本
&#125;
```

#### 2.1.8 TestTemplateParams

```
public interface TestTemplateParams &#123;
  TESTED_CLASS           // 被测类
  MESSAGES_KEY           // 消息键
  PACKAGE_NAME           // 包名
  CLASS_NAME             // 类名
  TestBuilder            // TestBuilder 实例
  StringUtils            // StringUtils 实例
  MockitoMockBuilder     // MockitoMockBuilder 实例
  PowerMockBuilder       // PowerMockBuilder 实例
  TestSubjectUtils       // TestSubjectInspector 实例
  JAVA_VERSION           // Java 版本
  TestedClasspathJars    // 被测类 classpath JAR
  MAX_RECURSION_DEPTH    // 最大递归深度
  MONTH_NAME_EN          // 英文月份名
  DAY_NUMERIC            // 数字日
  HOUR_NUMERIC           // 数字时
  MINUTE_NUMERIC         // 数字分
  SECOND_NUMERIC         // 数字秒
  TESTED_CLASS_LANGUAGE  // 被测类语言
  HAS_TEST_METHODS       // 是否有测试方法
&#125;
```

#### 2.1.9 TypeDictionary

```
public class TypeDictionary &#123;
  private static final int MAX_RELEVANT_METHOD_IDS_CACHE;  // 最大缓存方法数
  private final long startTimestamp;
  private final Set&lt;String&gt; testSubjectTypesNames;          // 被测类型名
  private final Set&lt;String&gt; testSubjectMethodNames;         // 被测方法名
  private final Cache<String, Boolean> relevantMethodIdsCache;  // 方法相关性缓存
  private final Cache<String, Type> typeDictionary;         // 类型字典缓存
  private final PsiClass testSubjectClass;                  // 被测类
  private final PsiPackage targetPackage;                   // 目标包
  private final Set&lt;PsiMethod&gt; testedMethods;               // 被测方法集合
  private final Set&lt;String&gt; methodCallsFromTestSubject;     // 被测类方法调用
  private final List&lt;String&gt; testSubjectMethodParamsType;   // 被测方法参数类型
  private AtomicInteger newTypeCounter;                     // 新类型计数
  private AtomicInteger existingTypeHitsCounter;            // 缓存命中计数
  private boolean throwSpecificExceptionTypes;

  public static TypeDictionary create(PsiClass, PsiPackage, Cache, boolean, Set&lt;String&gt;, Set&lt;PsiMethod&gt;);
    // 工厂方法：创建 TypeDictionary，解析类型名和方法调用

  public boolean isRelevant(PsiMethod, PsiClass);           // 方法是否相关
  public boolean shouldCheckMethodCall(PsiMethod);          // 是否应检查方法调用
  public boolean isUsedType(PsiMethod);                     // 是否使用的类型
  public boolean isTestSubject(PsiClass);                   // 是否被测类
  public Type getType(PsiType, int, boolean);               // 获取/创建类型
  public Type getType(PsiClass, int, boolean);              // 获取/创建类型
  public boolean contain(PsiClass);                         // 是否包含类
  public boolean isAccessible(PsiMethod);                   // 方法是否可访问
  public void resolveMethodReturnTypeAndParam(PsiMethod, int);  // 解析方法返回类型和参数
  public void logStatistics();                              // 输出统计信息
&#125;
```

#### 2.1.10 VelocityInitializer

```
public class VelocityInitializer &#123;
  public static void verifyRuntimeSetup();
    // 验证 Velocity 运行时设置

  private static RuntimeInstance getRuntimeInstance();
    // 获取 Velocity RuntimeInstance 单例
&#125;
```

---
