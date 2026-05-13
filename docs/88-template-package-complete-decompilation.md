# 88 - Template 包完整反编译分析

## 概述

本文档对 `com.aicode.template` 包的 **90 个类** 进行完整反编译与分析。该包是 iFlyCode 插件单元测试生成的核心引擎，负责模板加载、上下文构建、AI 请求、代码生成和断言渲染的完整流程。

---

## 1. 类完整清单（按子包分组，含字节码行数统计）

### 1.1 template/ 根包（10 核心类）

| # | 类名 | 字节码行数 | 类型 | 职责 |
|---|------|-----------|------|------|
| 1 | AssertUtil | 1360 | 工具类 | 断言代码生成（JUnit4/5） |
| 2 | CodeRefactorUtil | 348 | 工具类 | 代码重构：取消注释 import 语句 |
| 3 | ExcludeMethodEnum | 168 | 枚举 | 排除方法类型：ABSTRACT/NATIVE/GETTER/SETTER/MAIN/EQUALS/TOSTRING/HASHCODE |
| 4 | FileTemplateConfig | 267 | 配置类 | 模板配置：递归深度、格式化、mock 策略等 |
| 5 | TemplateGenerator | 491 | 入口类 | 模板生成器单例入口，调度批量/单方法测试生成 |
| 6 | TestSubjectInspector | 469 | 检查器 | 被测类检查：可测方法、DI 注入、构造函数选择 |
| 7 | TestTemplateContextBuilder | 548 | 构建器 | 模板上下文构建，组装 Velocity 模板参数 |
| 8 | TestTemplateParams | 41 | 接口 | Velocity 模板参数名常量定义 |
| 9 | TypeDictionary | 1127 | 字典 | 类型字典：缓存类型解析、方法相关性判断 |
| 10 | VelocityInitializer | 47 | 初始化器 | Velocity 运行时初始化验证 |

**根包小计：10 类，4,866 行字节码**

### 1.2 template/builder/ 子包（6 类）

| # | 类名 | 字节码行数 | 类型 | 职责 |
|---|------|-----------|------|------|
| 11 | MethodFactory | 1844 | 工厂类 | 从 PsiMethod 创建 Method 域对象，解析方法调用链 |
| 12 | MethodReferencesBuilder | 882 | 构建器 | 方法引用解析：调用链、字段影响、变量解析 |
| 13 | MockBuilder | 22 | 接口 | Mock 构建器接口：isMockable/buildArgsTypes/mockStaticClass |
| 14 | MockBuilderFactory | 146 | 工厂类 | 创建 Mockito/PowerMock 构建器，检测 Mockito 版本 |
| 15 | MockitoMockBuilder | 1355 | 实现类 | Mockito mock 代码生成：匹配器、stub、verify |
| 16 | PowerMockBuilder | 69 | 实现类 | PowerMock 扩展：内部方法调用 mock |

**builder 子包小计：6 类，4,318 行字节码**

### 1.3 template/generator/ 子包（22 类）

| # | 类名 | 字节码行数 | 类型 | 职责 |
|---|------|-----------|------|------|
| 17 | CacheFileTemplate | 103 | 数据类 | 缓存文件模板：参数映射、上下文、目标目录 |
| 18 | ClassNameSelection | 30 | 数据类 | 类名选择结果 |
| 19 | ClassNameSelection$UserDecision | 70 | 枚举 | 用户决策：USE/USE_OTHER/SKIP |
| 20 | CreateTestFileTask | 2461 | 任务类 | 创建测试文件核心任务（Backgroundable Task） |
| 21 | CreateTestFileTask$1 | 80 | 匿名类 | ProgressIndicator 适配 |
| 22 | CreateTestFileTask$2 | 194 | 匿名类 | WriteAction 回调 |
| 23 | CreateTestFileTask$3 | 68 | 匿名类 | 通知回调 |
| 24 | CreateTestFileTask$4 | 75 | 匿名类 | 错误处理回调 |
| 25 | CreateTestMethodTask | 1114 | 任务类 | 创建单个测试方法任务 |
| 26 | CreateTestMethodTask$1 | 99 | 匿名类 | 方法处理回调 |
| 27 | GeneratedClassNameResolver | 493 | 解析器 | 解析生成的类名冲突 |
| 28 | GeneratedClassNameResolver$1 | 50 | 匿名类 | 类名比较器 |
| 29 | GeneratorFileConfig | 227 | 配置类 | 生成器文件配置：目标目录、包名、类名 |
| 30 | GeneratorProcess | 140 | 处理器 | 生成流程控制 |
| 31 | GeneratorTemplateConfig | 257 | 配置类 | 模板配置：测试框架、mock 框架选择 |
| 32 | ProcessErrorFileAnalyzer | 224 | 分析器 | 处理错误文件分析 |
| 33 | ProcessErrorFileAnalyzer$1 | 184 | 匿名类 | 错误分析回调 |
| 34 | TargetDirectoryLocator | 685 | 定位器 | 测试目录定位：源码到测试目录映射 |
| 35 | TargetDirectoryLocator$1 | 70 | 匿名类 | 目录搜索策略1 |
| 36 | TargetDirectoryLocator$2 | 147 | 匿名类 | 目录搜索策略2 |
| 37 | TargetDirectoryLocator$3 | 65 | 匿名类 | 目录搜索策略3 |
| 38 | TestFileTemplateUtil | 387 | 工具类 | 测试文件模板工具：Velocity 合并 |

**generator 子包小计：22 类，6,653 行字节码**

### 1.4 template/context/domain/ 子包（13 类）

| # | 类名 | 字节码行数 | 类型 | 职责 |
|---|------|-----------|------|------|
| 39 | Field | 580 | 域对象 | 字段模型：类型、DI 注入、setter/getter 属性 |
| 40 | Method | 721 | 域对象 | 方法模型：参数、返回值、调用链、分支、case 结果 |
| 41 | MethodCall | 146 | 域对象 | 方法调用：变量名、被调方法、参数 |
| 42 | MethodCallArgument | 63 | 域对象 | 方法调用参数文本 |
| 43 | Node | 91 | 泛型节点 | 递归树节点：深度、父节点、附加数据 |
| 44 | Param | 127 | 域对象 | 方法参数：类型、名称、赋值字段映射 |
| 45 | Reference | 123 | 域对象 | 引用：名称、类型、所有者类型 |
| 46 | StaticMethodCall | 111 | 域对象 | 静态方法调用：继承 MethodCall + ownerClass |
| 47 | SyntheticParam | 38 | 域对象 | 合成参数：继承 Param + UsageContext |
| 48 | SyntheticParam$UsageContext | 58 | 枚举 | 合成参数上下文：Property/Generic |
| 49 | Type | 1807 | 域对象 | 类型模型：完整类型信息、方法列表、字段列表、依赖解析 |
| 50 | DiClassAnnotationEnum | 153 | 枚举 | DI 类注解：Singleton/Service/Component/Repository/Controller/RestController/Configuration |
| 51 | DiFieldAnnotationEnum | 140 | 枚举 | DI 字段注解：Inject/Named/Qualifier/Autowired/Resource |
| 52 | SpringFieldAnnotationEnum | 75 | 枚举 | Spring 字段注解：Value |

**context/domain 子包小计：14 类（含 annotion 3 类），4,113 行字节码**

### 1.5 template/context/resolved/ 子包（6 类）

| # | 类名 | 字节码行数 | 类型 | 职责 |
|---|------|-----------|------|------|
| 53 | MethodCallArg | 85 | 值对象 | 已解析方法调用参数：text/name/type |
| 54 | ResolveComponents | 538 | 复合对象 | 解析组件集合：变量列表、方法调用列表、分支 |
| 55 | ResolveVarible | 143 | 值对象 | 已解析变量：名称、类型、调用方法 |
| 56 | ResolvedBranch | 327 | 值对象 | 已解析分支：条件、子分支、偏移量 |
| 57 | ResolvedMethodCall | 120 | 值对象 | 已解析方法调用：方法名、参数列表 |
| 58 | ResolvedReference | 41 | 值对象 | 已解析引用：名称、类型 |

**context/resolved 子包小计：6 类，1,254 行字节码**

### 1.6 template/context/service/ 子包（9 类）

| # | 类名 | 字节码行数 | 类型 | 职责 |
|---|------|-----------|------|------|
| 59 | LangTestBuilder | 14 | 接口 | 语言级测试构建器：参数渲染、断言渲染 |
| 60 | TestBuilder | 36 | 接口 | 测试构建器主接口：16 个渲染方法 |
| 61 | TestBuilder$ParamRole | 70 | 枚举 | 参数角色：Mock/Input/Output |
| 62 | JavaTestBuilderImpl | 7056 | 实现类 | Java 测试代码生成核心：参数构建、类型解析、构造函数优化 |
| 63 | JavaTestBuilderImpl$1 | 33 | 匿名类 | JSONObject 子类：key 映射 |
| 64 | JavaTestBuilderImpl$2 | 33 | 匿名类 | JSONObject 子类：变量映射 |
| 65 | JavaTestBuilderImpl$3 | 33 | 匿名类 | JSONObject 子类：输出映射 |
| 66 | LangTestBuilderFactory | 69 | 工厂类 | 创建 LangTestBuilder 实例 |
| 67 | TestBuilderImpl | 498 | 实现类 | TestBuilder 委托实现，调用 LangTestBuilder |

**context/service 子包小计：9 类，7,842 行字节码**

### 1.7 template/fileloader/ 子包（11 类）

| # | 类名 | 字节码行数 | 类型 | 职责 |
|---|------|-----------|------|------|
| 68 | FTManager | 1656 | 管理器 | 文件模板管理：加载、保存、排序模板 |
| 69 | FileTemplateContext | 211 | 上下文 | 文件模板上下文：项目、类、包、模块信息 |
| 70 | FileTemplateLoadResult | 77 | 结果类 | 模板加载结果：MultiMap + 描述 URL |
| 71 | FileTemplatesLoader | 1142 | 加载器 | 从 classpath 和配置目录加载模板 |
| 72 | TemplateDescriptor | 207 | 描述符 | 模板描述：名称、角色、框架、mock 框架 |
| 73 | TemplateRegistry | 222 | 注册表 | 模板注册：7 种预定义模板（JUnit4/5 + Mockito/PowerMock/TestNG/SpringBoot） |
| 74 | TemplateResourceLoader | 84 | 资源加载器 | Velocity 资源加载器：从 FileTemplate 读取 |
| 75 | TemplateRole | 58 | 枚举 | 模板角色：Tester/Included |
| 76 | UnitFileTemplate | 248 | 模板类 | 单元测试文件模板：继承 FileTemplateBase |
| 77 | UnitTemplateManager | 1494 | 管理器 | 单元测试模板管理器：继承 FileTemplateManager |
| 78 | UnitTemplateManager$1 | 103 | 匿名类 | FileTemplatesScheme 实现 |

**fileloader 子包小计：11 类，5,502 行字节码**

### 1.8 template/request/ 子包（12 类）

| # | 类名 | 字节码行数 | 类型 | 职责 |
|---|------|-----------|------|------|
| 79 | DataUtils | 431 | 工具类 | 数据类型判断与转换：数字/布尔/日期/空值 |
| 80 | DataUtils$1 | 70 | 匿名类 | TypeEnum switch 映射 |
| 81 | FileRequestDto | 89 | DTO | 文件请求：requestId、filePath、方法结果列表 |
| 82 | MethodRequestResult | 140 | DTO | 方法请求结果：requestId、methodId、耗时 |
| 83 | TemplateRequestService | 3348 | 服务类 | AI 请求核心：构建 prompt、发送请求、解析响应 |
| 84 | TemplateTestDto | 83 | DTO | 测试 DTO：测试框架、mock 框架、测试内容 |
| 85 | TemplateTestPromptDto | 53 | DTO | AI 提示 DTO：stream、content、unitTest |
| 86 | CaseBranch | 263 | DTO | 用例分支：方法名、条件文本、结果、偏移量 |
| 87 | CaseParam | 99 | DTO | 用例参数：名称、类型、规范名、数据 |
| 88 | CaseResult | 484 | DTO | 用例结果：输入/输出/mock/分支/异常 |
| 89 | ToMockMethod | 53 | DTO | Mock 方法：类名、方法名、返回值 |
| 90 | TypeEnum | 170 | 枚举 | 类型枚举：BOOLEAN/STRING/NUMBER/ARRAY/LIST/HASHMAP/CLASS/STREAM/DATE |

**request 子包小计：12 类，5,433 行字节码**

### 总计

| 子包 | 类数 | 字节码行数 |
|------|------|-----------|
| template/ (根) | 10 | 4,866 |
| template/builder/ | 6 | 4,318 |
| template/generator/ | 22 | 6,653 |
| template/context/domain/ | 14 | 4,113 |
| template/context/resolved/ | 6 | 1,254 |
| template/context/service/ | 9 | 7,842 |
| template/fileloader/ | 11 | 5,502 |
| template/request/ | 12 | 5,433 |
| **合计** | **90** | **39,981** |

---

## 2. 每个类的完整反编译结果

### 2.1 template/ 根包

#### 2.1.1 AssertUtil

```
public class com.aicode.template.AssertUtil {
  // 常量
  public static final String J4_TEMPLATE;      // JUnit4 模板标识
  public static final String J4_ASSERT;         // "org.junit.Assert"
  public static final String J5_ASSERTIONS;     // "org.junit.jupiter.api.Assertions"
  public static boolean J5;                     // 是否 JUnit5
  public static final String KONG_WORDS;        // 空/null 关键字
  public static final String KONG_LIST_WORDS;   // 空集合关键字
  public static final String[] NULL_KEY_WORDS;  // null 关键字数组
  public static final List<String> BASIC_TYPES; // 基本类型列表

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
}
```

**关键逻辑**: 断言代码生成根据返回类型分7种策略，支持 JUnit4 (`assertEquals`) 和 JUnit5 (`Assertions.assertEquals`) 两种断言框架。中文常量："期望返回值是空，不进行断言"、"未知的类型"。

#### 2.1.2 CodeRefactorUtil

```
public class com.aicode.template.CodeRefactorUtil {
  public static final String COMMENTED_IMPORT_TOKEN;  // 注释掉的 import 标记

  public void uncommentImports(PsiFile, Project);
    // 取消注释 import 语句：找到被注释的 import，替换为正常 import

  private PsiElement extractImportStatement(PsiFile, Project, String);
    // 提取 import 语句

  private PsiElement createGroovyImport(Project, String);
    // 创建 Groovy import 语句

  private PsiElement createImportStatementOnDemand(Project, String, boolean);
    // 创建 on-demand import 语句
}
```

#### 2.1.3 ExcludeMethodEnum

```
public final class ExcludeMethodEnum extends Enum<ExcludeMethodEnum> {
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
}
```

#### 2.1.4 FileTemplateConfig

```
public class FileTemplateConfig {
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
}
```

#### 2.1.5 TemplateGenerator（核心入口）

```
public class TemplateGenerator {
  private static final Logger LOG;
  public static final TemplateGenerator INSTANCE;          // 单例
  public CreateTestFileTask batchCreateTestFileTask;       // 批量测试任务
  private CreateTestMethodTask createTestMethodTaskTask;   // 单方法测试任务

  // 批量测试入口
  public List<String> batchTestClass(Project, List<VirtualFile>, PsiManager, GeneratorConfig);
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
}
```

**关键交互**: 调用 `PluginStartupActivity.handleExecutorService`（异步执行器）、`ProgressManager`（进度管理）、`SocketMessageHandleListener.send2Web()`（WebSocket 通信）。

#### 2.1.6 TestSubjectInspector

```
public class TestSubjectInspector {
  private static final Set<String> SCALA_FUTURE_TYPES;  // Scala Future 类型
  private final boolean generateTestsForInternalMethods;
  private final Set<Method> selectedMethods;

  public boolean hasTestableInstanceMethod(List<Method>);   // 是否有可测实例方法
  public boolean hasSetFields(List<Field>);                 // 是否有 setter 字段
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
}
```

#### 2.1.7 TestTemplateContextBuilder

```
public class TestTemplateContextBuilder {
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

  private List<String> resolveClasspathJars(FileTemplateContext);
    // 解析 classpath JAR 列表

  private JavaVersion getJavaVersion(Module);
    // 获取 Java 版本
}
```

#### 2.1.8 TestTemplateParams

```
public interface TestTemplateParams {
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
}
```

#### 2.1.9 TypeDictionary

```
public class TypeDictionary {
  private static final int MAX_RELEVANT_METHOD_IDS_CACHE;  // 最大缓存方法数
  private final long startTimestamp;
  private final Set<String> testSubjectTypesNames;          // 被测类型名
  private final Set<String> testSubjectMethodNames;         // 被测方法名
  private final Cache<String, Boolean> relevantMethodIdsCache;  // 方法相关性缓存
  private final Cache<String, Type> typeDictionary;         // 类型字典缓存
  private final PsiClass testSubjectClass;                  // 被测类
  private final PsiPackage targetPackage;                   // 目标包
  private final Set<PsiMethod> testedMethods;               // 被测方法集合
  private final Set<String> methodCallsFromTestSubject;     // 被测类方法调用
  private final List<String> testSubjectMethodParamsType;   // 被测方法参数类型
  private AtomicInteger newTypeCounter;                     // 新类型计数
  private AtomicInteger existingTypeHitsCounter;            // 缓存命中计数
  private boolean throwSpecificExceptionTypes;

  public static TypeDictionary create(PsiClass, PsiPackage, Cache, boolean, Set<String>, Set<PsiMethod>);
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
}
```

#### 2.1.10 VelocityInitializer

```
public class VelocityInitializer {
  public static void verifyRuntimeSetup();
    // 验证 Velocity 运行时设置

  private static RuntimeInstance getRuntimeInstance();
    // 获取 Velocity RuntimeInstance 单例
}
```

---

### 2.2 template/builder/ 子包

#### 2.2.1 MethodFactory

```
public class MethodFactory {
  public static Cache<String, Method> methodIdCaches;  // 方法 ID 缓存
  private static String jsonDirPath;                    // JSON 数据目录路径

  // 核心方法
  private static List<CaseResult> getLocalData(PsiClass, String, PsiType, String);
    // 从本地 JSON 文件获取测试用例数据

  public static Method createMethod(PsiMethod, PsiClass, String, int, TypeDictionary, PsiType, Type);
    // 从 PsiMethod 创建 Method 域对象：
    // 1. 提取方法名、返回类型、参数
    // 2. 解析方法调用链 (resolveCalledMethods)
    // 3. 解析内部引用 (resolveInternalReferences)
    // 4. 解析方法引用 (resolveMethodReferences)
    // 5. 加载本地测试数据 (getLocalData)

  public static Method createMethod(ResolvedMethodCall, int, TypeDictionary, PsiType);
    // 从已解析方法调用创建 Method

  public static void resolveInternalReferences(TypeDictionary, PsiMethod, Method, PsiClass, Set<String>, int);
    // 解析内部引用：字段赋值、方法调用关系

  public static boolean hasInternalMethodCall(Method, Type);
    // 是否有内部方法调用

  public static List<ResolvedMethodCall> resolvedMethodCalls(PsiMethod);
    // 解析方法中的所有方法调用

  private static Set<MethodCall> resolveCalledMethods(PsiMethod, PsiClass, Method, TypeDictionary, ...);
    // 解析被调用的方法：遍历 PsiMethod 中的方法调用表达式

  public static boolean isTestable(PsiMethod, PsiClass);
    // 方法是否可测试：非抽象、非native、可见

  public static boolean isInherited(PsiMethod, PsiClass);
    // 方法是否继承自父类

  private static Optional<PsiSubstitutor> findMethodSubstitutor(PsiMethod, PsiClass, PsiType);
    // 查找方法泛型替换器
}
```

#### 2.2.2 MethodReferencesBuilder

```
public class MethodReferencesBuilder {
  public void resolveMethodReferences(int, List<Method>);
    // 解析方法间引用关系

  public void resolveMethodCallFields(int, List<Method>, List<Field>);
    // 解析方法调用影响的字段

  public void resolveMethodVariables(Type);
    // 解析方法中的变量

  public void resolveMethodCallByCaseResult(Type);
    // 按用例结果解析方法调用

  public boolean isMethodCalled(Method, Method);
    // 判断方法是否被另一个方法调用

  private void resolveFieldsAffectedByCtor(Type, int);
    // 解析构造函数影响的字段

  private void resolveMethodCalls(List<Method>, Method);
    // 解析方法调用链
}
```

#### 2.2.3 MockBuilder（接口）

```
public interface MockBuilder {
  boolean isMockable(Field, Type);           // 字段是否可 mock
  boolean isMockableType(Type, Type);        // 类型是否可 mock
  String getImmockabiliyReason(String, Field);  // 不可 mock 原因
  String buildArgsTypes(List<Param>);        // 构建参数类型字符串
  String buildStaticTypeNames(Type);         // 构建静态类型名
  String buildMockArgsMatchers(List<Param>); // 构建 mock 参数匹配器
  Set<String> mockStaticClass(Method);       // 获取需要 mock 的静态类
  Boolean isMockStatic(Method);              // 是否需要 mock 静态方法
  String resolveExceptions(Method);          // 解析方法异常
  boolean isMockExpected(Field);             // 字段是否期望 mock
}
```

#### 2.2.4 MockBuilderFactory

```
public class MockBuilderFactory {
  private static final String MOCKITO_CORE_JAR_NAME_PREFIX;  // "mockito-core"
  private static final Pattern MOCKITO_CORE_VERSION_REGEX;   // 版本号正则

  public MockitoMockBuilder createMockitoMockBuilder(FileTemplateContext, TestSubjectInspector, List<String>);
    // 创建 MockitoMockBuilder

  public PowerMockBuilder createPowerMockBuilder(FileTemplateContext, TestSubjectInspector, List<String>);
    // 创建 PowerMockBuilder

  public static boolean isMockInline(FileTemplateContext);
    // 检测是否使用 mockito-inline（mock maker）

  String resolveMockitoVersion(List<String>);
    // 从 classpath JAR 列表解析 Mockito 版本号
}
```

#### 2.2.5 MockitoMockBuilder

```
public class MockitoMockBuilder implements MockBuilder {
  private static final Pattern SEMVER_PATTERN;       // 语义版本正则
  public static final Pattern LOGGER_PATTERN;         // Logger 模式
  public static final LinkedHashMap<String, String> DEFAULT_TYPE_TO_MATCHERS;  // 类型->匹配器映射
  public static final Map<String, String> DEFAULT_TYPE_TO_BOCOM;  // 类型->Bocom匹配器映射

  private final boolean isMockitoMockMakerInlineOn;   // mockito-inline 是否启用
  private final boolean stubMockMethodCallsReturnValues;
  protected final TestSubjectInspector testSubjectInspector;
  private final String mockitoCoreVersion;
  private final Integer mockitoCoreMajorVersion;
  private final Integer mockitoCoreMinorVersion;

  // Mock 判断
  public boolean isMockable(Field);                    // 字段是否可 mock
  public boolean isMockableType(Type, Type);           // 类型是否可 mock
  protected boolean isMockableByMockFramework(Field);  // mock 框架是否支持
  protected boolean isMockableCommonChecks(Field, Type);  // 通用 mock 检查

  // Mock 代码生成
  public String buildMockArgsMatchers(List<Param>);    // 构建 any()/eq() 匹配器
  public String buildArgsTypes(List<Param>);           // 构建参数类型
  public String buildStaticTypeNames(Type);            // 构建静态类型名

  // Mock 策略
  public boolean shouldStub(Method, List<Field>);      // 是否需要 stub
  public boolean shouldVerify(Method, List<Field>);    // 是否需要 verify
  public boolean isMockExpected(Field);                // 是否期望 mock

  // 异常处理
  public String resolveExceptions(Method);             // 解析方法异常
  public void appendMethodExceptionTypes(List<String>, List<String>);

  // 版本相关
  public String getInitMocksMethod();                  // 获取 initMocks 方法名（版本相关）
  public String getMockitoCoreVersion();
}
```

#### 2.2.6 PowerMockBuilder

```
public class PowerMockBuilder extends MockitoMockBuilder {
  private final boolean renderInternalMethodCallStubs;

  public boolean hasInternalMethodCall(Method, Type);
    // 检查是否有内部方法调用（需要 PowerMock mock）

  protected boolean isMockableByMockFramework(Field);
    // PowerMock 支持更多类型的 mock（final 类等）
}
```

---

### 2.3 template/generator/ 子包

#### 2.3.1 CacheFileTemplate

```
public class CacheFileTemplate {
  private Map<String, Object> paramMaps;                  // 模板参数
  private FileTemplateContext context;                     // 文件模板上下文
  private PsiDirectory targetDirectory;                   // 目标目录
  private GeneratorFileConfig generatorFileConfig;        // 生成器文件配置
  private MethodGeneratorConfig methodGeneratorConfig;    // 方法生成器配置
  private List<MessageDto> messageDtos;                   // AI 消息列表
  // getter/setter
}
```

#### 2.3.2 ClassNameSelection + UserDecision

```
public class ClassNameSelection {
  private final String className;
  private final UserDecision userDecision;
}

public enum ClassNameSelection$UserDecision {
  USE,        // 使用现有类
  USE_OTHER,  // 使用其他名称
  SKIP        // 跳过
}
```

#### 2.3.3 CreateTestFileTask（核心任务，2461 行）

```
public class CreateTestFileTask extends Task.Backgroundable {
  // 核心流程（run 方法）：
  // 1. 遍历 GeneratorConfig 中的文件列表
  // 2. 对每个文件：
  //    a. 解析 PsiClass
  //    b. 创建 TypeDictionary
  //    c. 创建 TestTemplateContextBuilder
  //    d. 构建 Velocity 模板上下文
  //    e. 调用 TemplateRequestService.requestAI() 请求 AI
  //    f. 合并 Velocity 模板生成测试代码
  //    g. 写入测试文件
  //    h. 调用 CodeRefactorUtil.uncommentImports() 取消注释 import

  // 内部类：
  // $1 - ProgressIndicator 适配器
  // $2 - WriteAction 回调：写入测试文件
  // $3 - 通知回调：显示生成结果
  // $4 - 错误处理回调
}
```

#### 2.3.4 CreateTestMethodTask

```
public class CreateTestMethodTask {
  // 单方法测试生成任务：
  // 1. 解析目标方法
  // 2. 构建 TypeDictionary
  // 3. 请求 AI 生成用例
  // 4. 合并模板生成测试方法
  // 5. 插入到已有测试类中

  // $1 - 方法处理回调
}
```

#### 2.3.5 GeneratedClassNameResolver

```
public class GeneratedClassNameResolver {
  // 解析生成的类名冲突：
  // 1. 检查目标目录是否已有同名类
  // 2. 如果冲突，弹出对话框让用户选择
  // 3. 支持自动追加数字后缀

  // $1 - 类名比较器
}
```

#### 2.3.6 GeneratorFileConfig

```
public class GeneratorFileConfig {
  private String targetDirectory;     // 目标目录
  private String packageName;         // 包名
  private String className;           // 类名
  private String srcClassName;        // 源类名
  private boolean customClassName;    // 是否自定义类名
  // getter/setter
}
```

#### 2.3.7 GeneratorProcess

```
public class GeneratorProcess {
  // 生成流程控制：协调各组件完成生成
}
```

#### 2.3.8 GeneratorTemplateConfig

```
public class GeneratorTemplateConfig {
  private String testFramework;       // 测试框架：JUnit4/JUnit5/TestNG
  private String mockFramework;       // Mock 框架：Mockito/PowerMock
  private boolean generateTestsForInternalMethods;
  private boolean requestAi;          // 是否请求 AI
  private FileTemplateConfig fileTemplateConfig;
  // getter/setter
}
```

#### 2.3.9 ProcessErrorFileAnalyzer

```
public class ProcessErrorFileAnalyzer {
  // 分析生成过程中的错误文件：
  // 1. 检测编译错误
  // 2. 分析 import 缺失
  // 3. 尝试自动修复

  // $1 - 错误分析回调
}
```

#### 2.3.10 TargetDirectoryLocator

```
public class TargetDirectoryLocator {
  // 测试目录定位策略：
  // 1. 查找 src/test/java 对应目录
  // 2. 查找同模块测试源根
  // 3. 查找跨模块测试源根

  // $1 - 同模块目录搜索
  // $2 - 跨模块目录搜索
  // $3 - 默认目录搜索
}
```

#### 2.3.11 TestFileTemplateUtil

```
public class TestFileTemplateUtil {
  // 测试文件模板工具：
  // 1. 合并 Velocity 模板
  // 2. 处理模板中的占位符
  // 3. 生成最终测试代码
}
```

---

### 2.4 template/context/domain/ 子包

#### 2.4.1 Type（核心域对象，1807 行）

```
public class Type {
  private final String canonicalName;        // 全限定名
  private final String name;                 // 简名
  private final boolean isPrimitive;         // 基本类型
  private final String packageName;          // 包名
  private final List<Type> composedTypes;    // 组合类型（泛型参数）
  private final boolean array;               // 数组
  private final int arrayDimensions;         // 数组维度
  private final boolean varargs;             // 可变参数
  private final boolean isEnum;              // 枚举
  private final List<String> enumValues;     // 枚举值
  private final boolean isInterface;         // 接口
  private final boolean isAbstract;          // 抽象
  private final boolean isStatic;            // 静态
  private final boolean isFinal;             // final
  private final Type parentContainerClass;   // 父容器类
  private final String superClass;           // 父类
  private boolean dependenciesResolved;      // 依赖是否已解析
  private boolean hasDefaultConstructor;     // 有默认构造函数
  private final List<Method> methods;        // 方法列表
  private final Set<String> staticClassNames;// 静态类名
  private final Set<Field> fields;           // 字段列表
  private final List<Type> implementedInterfaces;  // 实现的接口
  private final boolean isAnnotatedByDI;     // DI 注解标记
  private boolean resolved;                  // 是否已解析

  // 构造函数
  public Type(PsiType, Object, TypeDictionary, int, boolean);
  public Type(PsiClass, TypeDictionary, int, boolean);

  // 依赖解析
  public void resolveDependencies(TypeDictionary, int, PsiType, boolean);
    // 解析类型的所有依赖：方法、字段、接口

  private void resolveFields(PsiClass, TypeDictionary, int);
    // 解析字段：过滤测试注解字段

  private void resolveImplementedInterfaces(PsiClass, TypeDictionary, boolean, int);
    // 解析实现的接口

  public List<Method> findConstructors();
    // 查找构造函数，按参数数量排序

  private boolean buildAnnotatedByDi(PsiClass, TypeDictionary);
    // 检查 DI 注解（@Service/@Component/@Repository 等）

  private boolean buildAnnotatedBySpringConfig(PsiClass, TypeDictionary);
    // 检查 Spring 配置注解

  // 工具方法
  public boolean isArray(), isCollection(), isEnum(), isInterface();
  public String renderArray();  // 渲染数组声明
}
```

#### 2.4.2 Method

```
public class Method {
  private final String methodId;               // 方法唯一标识
  private final String name;                   // 方法名
  private final Type returnType;               // 返回类型
  private final String ownerClassCanonicalType;// 所属类全限定名
  private final List<Param> methodParams;      // 参数列表
  private final List<String> callParams;       // 调用参数
  private Set<String> exceptions;              // 异常列表
  private String methodExceptionTypes;         // 异常类型字符串
  // 访问修饰符
  private final boolean isPrivate, isProtected, isDefault, isPublic;
  private final boolean isAbstract, isNative, isStatic;
  private final boolean isSetter, isGetter, constructor;
  private final boolean overridden, inherited, isInInterface, isSynthetic;
  private final String propertyName;           // 属性名（getter/setter）
  private final boolean accessible, primaryConstructor, testable;
  private final Integer startOffset, endOffset; // 源码偏移量
  // 调用关系
  private final Set<MethodCall> directMethodCalls;     // 直接方法调用
  private final Set<MethodCall> methodCalls;           // 所有方法调用
  private final ResolveComponents resolveComponents;   // 解析组件
  private final Set<StaticMethodCall> staticMethodCalls; // 静态方法调用
  private final Set<Method> methodReferences;          // 方法引用
  private final Set<MethodCall> calledFamilyMembers;   // 家族方法调用
  private ResolvedBranch caseBranchSet;                // 用例分支集
  private final Set<Reference> internalReferences;     // 内部引用
  private final Set<Field> indirectlyAffectedFields;   // 间接影响字段
  private final List<CaseResult> caseResults;          // AI 生成的用例结果
  private final CaseResult caseResult;                 // 单个用例结果
  private final Set<String> reflectionMethods;         // 反射方法

  public boolean hasReturn();   // 是否有返回值
  public boolean hasParams();   // 是否有参数
  public void resolveExceptions();  // 从方法调用链解析异常
}
```

#### 2.4.3 Field

```
public class Field {
  private final Type type;                       // 字段类型
  private final boolean overridden;              // 是否被覆盖
  private final boolean isFinal, isStatic;       // 修饰符
  private final String ownerClassCanonicalName;  // 所属类
  private final boolean isAnnotatedByDI;         // DI 注解
  private final boolean hasSetter;               // 有 setter
  private final boolean getProperty, setProperty;// Groovy 属性
  private final boolean isInitializedInline;     // 内联初始化
  private final boolean isAnnotatedBySpringValue;// @Value 注解
  private String name;                           // 字段名
  private boolean isNotInBuilder;                // 不在 builder 中

  // 构造时自动检测
  private boolean buildAnnotatedByDI(PsiField, PsiClass, TypeDictionary);
    // 检查 @Inject/@Autowired/@Resource 等 DI 注解
  private boolean buildAnnotatedBySpringConfig(PsiField, PsiClass, TypeDictionary);
    // 检查 @Value 注解
  private boolean buildHasSetter(PsiClass, String, TypeDictionary);
    // 检查是否有 setter 方法
}
```

#### 2.4.4 Param, MethodCall, MethodCallArgument, StaticMethodCall, Reference, Node, SyntheticParam

```
// Param - 方法参数
public class Param {
  final Type type;
  private String name;
  private final ArrayList<Field> assignedToFields;  // 赋值给的字段
}

// MethodCall - 方法调用
public class MethodCall {
  private final String variableName;        // 调用变量名
  private final Method method;              // 被调方法
  private final List<MethodCallArgument> methodCallArguments;  // 调用参数
}

// MethodCallArgument - 方法调用参数
public class MethodCallArgument {
  private final String text;  // 参数文本
}

// StaticMethodCall - 静态方法调用（继承 MethodCall）
public class StaticMethodCall extends MethodCall {
  private String ownerClass;  // 静态方法所属类
}

// Reference - 引用
public class Reference {
  private final String referenceName;
  private final Type referenceType;
  private final Type ownerType;
  private final String referenceId;
}

// Node<T> - 递归树节点
public class Node<T> {
  private final T data;
  private final Node<T> parent;
  private final int depth;
  private Object needData;
  public boolean hasSameAncestor();  // 是否有相同祖先
}

// SyntheticParam - 合成参数（继承 Param）
public class SyntheticParam extends Param {
  private final UsageContext usageContext;  // Property/Generic
}
```

#### 2.4.5 annotion 枚举

```
// DiClassAnnotationEnum - DI 类注解
SINGLETON("javax.inject.Singleton")
SERVICE("org.springframework.stereotype.Service")
COMPONENT("org.springframework.stereotype.Component")
REPOSITORY("org.springframework.stereotype.Repository")
CONTROLLER("org.springframework.stereotype.Controller")
REST_CONTROLLER("org.springframework.web.bind.annotation.RestController")
CONFIGURATION("org.springframework.context.annotation.Configuration")

// DiFieldAnnotationEnum - DI 字段注解
INJECT("javax.inject.Inject")
NAMED("javax.inject.Named")
QUALIFIER("javax.inject.Qualifier")
QUALIFIER_SPRING("org.springframework.beans.factory.annotation.Qualifier")
AUTOWIRED("org.springframework.beans.factory.annotation.Autowired")
RESOURCE("javax.annotation.Resource")

// SpringFieldAnnotationEnum - Spring 字段注解
VALUE("org.springframework.beans.factory.annotation.Value")
```

---

### 2.5 template/context/resolved/ 子包

```
// MethodCallArg - 已解析方法调用参数
public class MethodCallArg {
  private final String text;
  private final String name;
  private final PsiType type;
}

// ResolveComponents - 解析组件集合
public class ResolveComponents {
  private List<ResolveVarible> variables;      // 变量列表
  private List<ResolvedMethodCall> methodCalls; // 方法调用列表
  private List<ResolvedBranch> branches;        // 分支列表
}

// ResolveVarible - 已解析变量
public class ResolveVarible {
  private String name;
  private Type type;
  private Method callMethod;  // 调用的方法
}

// ResolvedBranch - 已解析分支
public class ResolvedBranch {
  private String conditionText;     // 条件文本
  private Boolean result;           // 分支结果
  private Boolean isOut;            // 是否跳出
  private Integer startOffset;      // 起始偏移
  private Integer endOffset;        // 结束偏移
  private List<ResolvedBranch> children;  // 子分支
  private ResolvedBranch parent;    // 父分支
  private Boolean prev;             // 前置条件
  private Boolean after;            // 后置条件
}

// ResolvedMethodCall - 已解析方法调用
public class ResolvedMethodCall {
  private String methodName;
  private List<MethodCallArg> arguments;
}

// ResolvedReference - 已解析引用
public class ResolvedReference {
  private String name;
  private Type type;
}
```

---

### 2.6 template/context/service/ 子包

#### 2.6.1 LangTestBuilder（接口）

```
public interface LangTestBuilder {
  String PARAMS_SEPARATOR = ", ";
  String renderJavaCallParams(List<Param>, CaseResult);        // 渲染调用参数
  String renderJavaCallParam(Type, String, CaseResult);        // 渲染单个调用参数
  String renderJavaVariable(Type, String, CaseResult);         // 渲染变量
  String renderJavaMethodCaseBody(Type, Method);               // 渲染方法用例体
  String renderJavaMethodAssert(Method, CaseResult, String);   // 渲染断言
}
```

#### 2.6.2 TestBuilder（接口）

```
public interface TestBuilder {
  String RESULT_VARIABLE_NAME = "result";
  String renderMethodParams(Method, Map, Map);                           // 渲染方法参数
  String renderMethodParamsWithCase(Method, Map, Map, CaseResult);       // 带用例渲染参数
  String buildPrameterizedTestComponentsString(Method, Map, Map, Map);   // 参数化测试
  String renderReturnParam(Method, Method, String, Map, Map);            // 渲染返回参数
  String renderReturnParamAndMockito(Method, Method, Type, String, Map, Map);  // 返回参数+Mockito
  String renderReturnParamToMock(Method, Type, String, Map, Map);        // mock 返回参数
  String renderReturnParamWithData(Method, Type, String, Map, Map, CaseResult);  // 带数据返回参数
  String renderVariableWithData(Method, ResolveVarible, Map, Map, CaseResult);   // 带数据变量
  String resetVariable(Method, CaseResult);                              // 重置变量
  String renderCaseBranches(Method, CaseResult);                         // 渲染分支
  String renderMockReturnParamWithData(Method, Type, String, Map, Map, CaseResult); // mock 返回
  String renderInitType(Type, String, Map, Map);                         // 初始化类型
  String renderInitTypeValue(Type, String, Map, Map);                    // 初始化类型值
  String renderMethodCaseBody(Method, Type, Map, Map);                   // 方法用例体
  String renderJavaMethodAssert(Method, CaseResult, Map, Map, String);   // 断言
  String renderDocComment();                                              // 文档注释
}
```

#### 2.6.3 JavaTestBuilderImpl（7056 行，最大类）

```
public class JavaTestBuilderImpl implements LangTestBuilder {
  // 静态默认类型
  private static final Type DEFAULT_STRING_TYPE;
  private static final Type DEFAULT_Object_TYPE;
  private static final Type DEFAULT_Map_TYPE;
  private static final Type DEFAULT_List_TYPE;
  private static final String[] SPEC_ARRAY_VALUE;  // 特殊数组值
  public static final LinkedHashMap<String, String> DEFAULT_TYPE_TO_MATCHERS;  // 类型->匹配器
  public static final Map<String, String> DEFAULT_TYPE_TO_BOCOM;              // 类型->Bocom匹配器
  private static final int JAVA_9_VERSION = 53;

  // 实例字段
  private final TestBuilder$ParamRole paramRole;     // 参数角色：Mock/Input/Output
  private final Method testedMethod;                  // 被测方法
  protected final String NEW_INITIALIZER;             // "new " 初始化关键字
  private final Module srcModule;                     // 源模块
  private final TypeDictionary typeDictionary;        // 类型字典
  protected FileTemplateConfig fileTemplateConfig;    // 模板配置
  private final JavaVersion javaVersion;              // Java 版本
  private final Map<String, String> defaultTypeValues;  // 默认类型值
  private final Map<String, String> typesOverrides;     // 类型覆盖
  private final Integer renderType;                     // 渲染类型

  // 核心渲染方法
  public String renderJavaCallParams(List<Param>, CaseResult);
    // 渲染方法调用参数：遍历参数列表，对每个参数调用 renderJavaCallParam

  public String renderJavaCallParam(Type, String, CaseResult);
    // 渲染单个调用参数：根据类型（基本类型/集合/Map/实体）生成不同的初始化代码

  public String renderJavaVariable(Type, String, CaseResult);
    // 渲染变量声明

  public String renderJavaMethodCaseBody(Type, Method);
    // 渲染方法用例体

  public String renderJavaMethodAssert(Method, CaseResult, String);
    // 渲染断言代码

  // 内部构建方法
  protected void buildCallParam(StringBuilder, Node<Param>, CaseParam, int);
    // 递归构建调用参数，处理嵌套类型

  protected void buildVaribleCallParam(StringBuilder, Node<Param>, CaseParam, int);
    // 构建变量调用参数

  void buildJavaParam(StringBuilder, Node<Param>, CaseParam, int);
    // 构建 Java 参数：处理构造函数、setter、getter

  private void resolveMethod(Method, Node<Param>, CaseParam, StringBuilder, Type, Type, String, Boolean, Map, int);
    // 解析方法调用链，生成 stub/verify 代码

  private void renderSetMethod(StringBuilder, Map, Type, Node, JSONObject, String, int);
    // 渲染 setter 方法调用

  protected void renderMapOrList(String, StringBuilder, Type, Node, CaseParam, List<MethodCall>, int);
    // 渲染 Map 或 List 初始化

  protected void renderEnumValue(StringBuilder, Type);
    // 渲染枚举值

  // 构造函数优化
  protected Method findValidConstructor(Type, boolean);
    // 查找有效构造函数

  boolean shouldOptimizeConstructorInitialization(int, int);
    // 是否应优化构造函数初始化

  private boolean isUnused(Type, Method, List<Field>);
    // 字段是否未使用

  private boolean isPropertyUsed(Method, Param, Type);
    // 属性是否被使用

  // 类型解析
  String resolveTypeName(Type);
    // 解析类型名：处理泛型、内部类

  private String resolveConcreteType(String);
    // 解析具体类型：接口->实现类替换

  // 匿名内部类
  // $1 - JSONObject 子类：key 映射
  // $2 - JSONObject 子类：变量映射
  // $3 - JSONObject 子类：输出映射
}
```

#### 2.6.4 LangTestBuilderFactory

```
public class LangTestBuilderFactory {
  private final FileTemplateConfig fileTemplateConfig;
  private final Module srcModule;
  private final TypeDictionary typeDictionary;
  private final JavaVersion javaVersion;
  private Integer renderType;

  public LangTestBuilder createTestBuilder(Method, TestBuilder$ParamRole, Map, Map, Integer);
    // 创建 JavaTestBuilderImpl 实例
}
```

#### 2.6.5 TestBuilderImpl

```
public class TestBuilderImpl implements TestBuilder {
  private final LangTestBuilderFactory langTestBuilderFactory;

  // 委托实现：所有 TestBuilder 方法都委托给 LangTestBuilder
  // 根据 paramRole (Mock/Input/Output) 创建不同的 LangTestBuilder
}
```

---

### 2.7 template/fileloader/ 子包

#### 2.7.1 FTManager（1656 行）

```
public class FTManager {
  private static final String DEFAULT_TEMPLATE_EXTENSION;  // "ft"
  static final String TEMPLATE_EXTENSION_SUFFIX;           // ".ft"
  private static final String ENCODED_NAME_EXT_DELIMITER;  // 编码分隔符

  private final String myName;                    // 管理器名称
  private final boolean myInternal;               // 是否内部模板
  private final Path myTemplatesDir;              // 模板目录
  private final FTManager myOriginal;             // 原始管理器
  private final Map<String, FileTemplateBase> myTemplates;  // 模板映射
  private volatile List<FileTemplateBase> mySortedTemplates; // 排序模板
  private final List<DefaultTemplate> myDefaultTemplates;    // 默认模板
  private final TemplateRegistry templateRegistry;           // 模板注册表

  // 模板操作
  Collection<FileTemplateBase> getAllTemplates(boolean);     // 获取所有模板
  FileTemplateBase getTemplate(String);                      // 获取模板
  FileTemplateBase findTemplateByName(String);               // 按名查找
  FileTemplateBase addTemplate(String, String);              // 添加模板
  void updateTemplates(Collection<? extends FileTemplate>);  // 更新模板
  void loadCustomizedContent();                              // 加载自定义内容
  public void saveTemplates();                               // 保存模板

  // 文件名编码
  public static String encodeFileName(String, String);       // 编码文件名
  private static Pair<String, String> decodeFileName(String);// 解码文件名
}
```

#### 2.7.2 FileTemplateContext

```
public class FileTemplateContext {
  private FileTemplateDescriptor fileTemplateDescriptor;  // 模板描述符
  private final Project project;                          // 项目
  private final String targetClass;                       // 目标类名
  private final PsiPackage targetPackage;                 // 目标包
  private final Module srcModule;                         // 源模块
  private final Module testModule;                        // 测试模块
  private final PsiDirectory targetDirectory;             // 目标目录
  private final PsiClass srcClass;                        // 源类
  private final FileTemplateConfig fileTemplateConfig;    // 模板配置
  private final List<String> excludeMethodList;           // 排除方法列表
  private final Set<PsiMethod> selectedMethods;           // 选中的方法
  private final Boolean requestAi;                        // 是否请求 AI
  private String filePath;                                // 文件路径
}
```

#### 2.7.3 FileTemplatesLoader

```
class FileTemplatesLoader {
  static final String TEMPLATES_DIR;           // "fileTemplates"
  private static final String DEFAULT_TEMPLATES_ROOT;  // 默认模板根路径
  private final FTManager myTestTemplatesManager;       // 测试模板管理器
  private final FTManager myIncludesManager;            // 包含模板管理器
  private final FTManager[] myAllManagers;              // 所有管理器
  private static final String TESTS_DIR;                // "junit"
  public static final String INCLUDES_DIR;              // "includes"

  // 加载默认模板
  private static FileTemplateLoadResult loadDefaultTemplates(List<String>);
  private static void loadDefaultsFromRoot(URL, List<String>, FileTemplateLoadResult);
}
```

#### 2.7.4 TemplateRegistry

```
public class TemplateRegistry {
  // 7 种预定义模板
  public static final String JUNIT4_JAVA_TEMPLATE;              // "JUnit4 Java"
  public static final String JUNIT5_JAVA_TEMPLATE;              // "JUnit5 Java"
  public static final String JUNIT4_MOCKITO_JAVA_TEMPLATE;      // "JUnit4 Mockito Java"
  public static final String JUNIT4_POWERMOCK_JAVA_TEMPLATE;    // "JUnit4 PowerMock Java"
  public static final String JUNIT5_MOCKITO_JAVA_TEMPLATE;      // "JUnit5 Mockito Java"
  public static final String TESTNG_MOCKITO_JAVA_TEMPLATE;      // "TestNG Mockito Java"
  public static final String SPRINGBOOTTEST_MOCKITO_JAVA_TEMPLATE; // "SpringBootTest Mockito Java"

  public List<TemplateDescriptor> getTemplateDescriptors();
  public List<TemplateDescriptor> getEnabledTemplateDescriptors();
  public TemplateDescriptor getEnabledTemplateDescriptor(String, String);
}
```

#### 2.7.5 TemplateDescriptor

```
public class TemplateDescriptor {
  private String htmlDisplayName;       // HTML 显示名
  private String displayName;           // 显示名
  private String tokenizedName;         // 标记化名称
  private String filename;              // 文件名
  private TemplateRole templateRole;    // 角色：Tester/Included
  private String framework;             // 框架
  private String mockFramework;         // Mock 框架
  public static final String LANGUAGE_JAVA = "java";
}
```

#### 2.7.6 TemplateResourceLoader

```
public class TemplateResourceLoader extends ResourceLoader {
  // Velocity 资源加载器：
  // 从 IntelliJ FileTemplate 系统加载模板资源

  public void init(ExtendedProperties);
  public Reader getResourceReader(String, String);
  public InputStream getResourceStream(String);
  public boolean isSourceModified(Resource);
  public long getLastModified(Resource);
}
```

#### 2.7.7 UnitFileTemplate

```
public class UnitFileTemplate extends FileTemplateBase {
  private String name;
  private String displayName;
  private boolean isDefault;
  private String description;
  private String extension;
}
```

#### 2.7.8 UnitTemplateManager

```
public class UnitTemplateManager extends FileTemplateManager {
  public static final String TEST_TEMPLATES_CATEGORY;  // "Tests"
  private static volatile UnitTemplateManager instance; // 单例
  private final FileTemplatesLoader myFileTemplatesLoader;
  private final Project myProject;
  private final TemplateRegistry templateRegistry;
  private Date myTestDate;  // 测试日期

  public static UnitTemplateManager getInstance(Project);
  public FileTemplate[] getTemplates(String);
  public List<TemplateDescriptor> getTestTemplates();
  public FileTemplate getInternalTemplate(String);
  public FileTemplate findCustomTestTemplate(String);
  public void setTestDate(Date);
  public Properties getDefaultProperties();
  public void saveAllTemplates();
}
```

---

### 2.8 template/request/ 子包

#### 2.8.1 DataUtils

```
public class DataUtils {
  private static Set<String> NUMBER_TYPE;   // 数字类型集合
  private static Set<String> BOOLEAN_TYPE;  // 布尔类型集合
  private static Set<String> DATE_TYPE;     // 日期类型集合
  public static final List<String> noSupportValues;  // 不支持的值
  public static final CaseResult Empty;     // 空用例结果

  public static boolean isNumberType(Type);
  public static boolean isBooleanType(String);
  public static boolean isDateType(String);
  public static boolean canSetNullValue(String);
  public static String convertToBoolean(String, String, String);
  public static Object convertData(Object, TypeEnum);  // 按类型枚举转换数据
  public static boolean isNull(Object);
  public static CaseParam tryConvertCaseParam(String, String);  // 尝试转换用例参数
  public static boolean checkNumberData(String);
  public static List<CaseResult> parseToCaseResult(JSONArray);  // 从 JSON 数组解析用例
  public static boolean isEmptyData(CaseResult);  // 是否空数据
}
```

#### 2.8.2 TemplateRequestService（3348 行，第二大类）

```
public class TemplateRequestService {
  public static final Cache<String, FileRequestDto> classModelRenders;  // 类模型渲染缓存
  public static final int MAX_TOKEN_CHAR_LENGTH;   // 最大 token 字符长度
  public static final int MAX_REQUEST_LIMIT;       // 最大请求限制
  public static final long RETRY_WAIT_TIME;        // 重试等待时间

  // AI 请求核心方法
  public static MethodRequestResult requestAI(PsiClass, Type, PsiMethod, TypeDictionary,
      GeneratorTemplateConfig, String, Project, List<MessageDto>, Set<Method>,
      FileRequestDto, Module, Map<String, String>);
    // 1. 构建方法上下文信息（类结构、方法签名、调用链）
    // 2. 构建 AI prompt（包含被测代码、mock 信息、分支信息）
    // 3. 通过 PluginWebsocketClient 发送请求
    // 4. 等待 AI 响应
    // 5. 解析响应为 CaseResult

  public static List<MessageDto> requestAI(String, PsiClass, Type, TypeDictionary,
      GeneratorTemplateConfig, String, Project, boolean, Module, Set<Method>);
    // 批量请求 AI：遍历所有可测方法

  // AI 响应处理
  public static synchronized void handleAgentAction(CommandEnum, JsonObject, MessageDto, String, Project);
    // 处理 AI 返回的测试用例数据：
    // 1. 解析 XML 标签：<test-case>、<case-mock>、<branch>
    // 2. 转换为 CaseResult/CaseParam/ToMockMethod/CaseBranch
    // 3. 存入 FileRequestDto

  public static synchronized void handleRequestErrorTestCase(ResponseDto, CommandEnum, MessageDto);
    // 处理 AI 请求错误

  // 辅助方法
  private static void appendTypeBody(Type, StringBuilder, PsiClass, boolean, Set<String>, Project, Module, TypeDictionary, Map);
    // 构建类型体信息（用于 AI prompt）

  private static void getBodyContent(StringBuilder, PsiMethod, PsiClass, List<String>, boolean, int);
    // 获取方法体内容

  public static boolean shouldBeTested(PsiMethod, PsiClass, GeneratorTemplateConfig);
    // 方法是否应被测试

  public static synchronized boolean isModelReturned(String, String);
    // 检查 AI 模型是否已返回

  public static synchronized boolean isAllReturned(String);
    // 检查所有方法是否已返回

  // 用例解析
  private static void addCase(String, Method, List<CaseResult>);
  private static void convertException(String, CaseResult, Method);
  private static CaseParam convertOutput(String, Method);
  private static Map<String, CaseParam> convertInput(String, Method);
  private static void addMock(String, List<ToMockMethod>);
  private static void addBranches(String, Method, List<CaseBranch>);
  private static void resolveAllBranches(ResolvedBranch, List<CaseBranch>);
  private static void resolveCaseBranch(ResolvedBranch, Boolean, List<CaseBranch>);

  // XML 解析
  private static String caseHandle(String, String);   // 解析 <test-case> 标签
  private static String caseMocks(String, String);    // 解析 <case-mock> 标签
  public static String extractTagValue(String, String);  // 提取标签值
}
```

#### 2.8.3 DTO 类

```
// FileRequestDto - 文件请求
public class FileRequestDto {
  private String requestId;
  private String filePath;
  private List<MethodRequestResult> methodRequestResults;
  public int getDiff(int);  // 计算耗时差值
}

// MethodRequestResult - 方法请求结果
public class MethodRequestResult {
  private Integer requestCount;
  private String requestId;
  private String methodId;
  private Method method;
  private boolean isReturn;
  private Date beginTime;
  private Date endTime;
  public Long getDiff();  // 计算耗时
}

// TemplateTestDto - 测试 DTO
public class TemplateTestDto {
  private String testFrame;       // 测试框架
  private String mockFrame;       // Mock 框架
  private String testContent;     // 测试内容
  private Integer testCaseNumber; // 用例数量
  private List<String> branchList;// 分支列表
}

// TemplateTestPromptDto - AI 提示 DTO
public class TemplateTestPromptDto {
  private boolean stream;         // 是否流式
  private String content;         // 提示内容
  private TemplateTestDto unitTest; // 单元测试信息
}

// CaseBranch - 用例分支
public class CaseBranch {
  private String methodName;
  private String conditionText;
  private Boolean result;
  private Boolean isOut;
  private Integer startOffset;
  private Integer endOffset;
  public String toCommitText();
  public String toCommitText(boolean);
}

// CaseParam - 用例参数
public class CaseParam {
  private String name;
  private String type;
  private String canonicalName;
  private Object data;
  public TypeEnum getResolveType();  // 解析类型枚举
}

// CaseResult - 用例结果
public class CaseResult {
  private String caseMethodName;
  private String type;
  private Map<String, CaseParam> input;
  private List<CaseBranch> branches;
  private List<ToMockMethod> mockMethods;
  private CaseParam output;
  private String message;
  private String exception;
  private String exceptionMessage;
  private String methodCommentId;
  public String toCommitBranchText();
  public String toComment();
  public String toEndComment();
}

// ToMockMethod - Mock 方法
public class ToMockMethod {
  private String className;
  private String methodName;
  private CaseParam returnValue;
}

// TypeEnum - 类型枚举
public enum TypeEnum {
  BOOLEAN, STRING, NUMBER, ARRAY, LIST, HASHMAP, CLASS, STREAM, DATE;
  public static TypeEnum parse(String);  // 从字符串解析
}
```

---

## 3. 子包间交互关系

```
                    +-------------------+
                    |  TemplateGenerator|  (入口单例)
                    +--------+----------+
                             |
              +--------------+---------------+
              |                              |
    +---------v----------+       +-----------v-----------+
    | CreateTestFileTask |       | CreateTestMethodTask  |
    | (批量测试生成)      |       | (单方法测试生成)       |
    +----+------+--------+       +-----------+-----------+
         |      |                            |
         |      +-------+                    |
         v              v                    v
  +------+------+  +----+-------+    +-------+--------+
  | fileloader  |  | request    |    | generator      |
  | 子包        |  | 子包       |    | 子包(配置/定位) |
  +------+------+  +----+-------+    +-------+--------+
         |              |                     |
         v              v                     v
  +------+------+  +----+-------------------------+
  | TestTemplate|  | TemplateRequestService       |
  | ContextBuilder  | (AI 请求/响应处理)            |
  +----+------+--+  +----+------+---------+-------+
       |      |          |         |         |
       v      v          v         v         v
  +----+---+ +--+---+  +-+----+ +-+-----+ +-+------+
  |builder | |context| |agent | |test   | |settings|
  |子包    | |子包   | |包    | |包     | |包      |
  +--------+ +--+---+ +------+ +-------+ +--------+
                |
       +--------+--------+
       |        |        |
       v        v        v
  +----+--+ +---+---+ +--+----+
  |domain | |resolved| |service|
  |子包   | |子包    | |子包   |
  +-------+ +--------+ +-------+
```

### 交互关系详述

1. **TemplateGenerator -> generator**: 入口类创建 CreateTestFileTask/CreateTestMethodTask
2. **generator -> fileloader**: 加载 Velocity 模板，获取 FileTemplateContext
3. **generator -> request**: 通过 TemplateRequestService 请求 AI 生成用例
4. **generator -> builder**: 使用 MockBuilderFactory 创建 mock 构建器
5. **TestTemplateContextBuilder -> builder**: 调用 MockBuilderFactory 和 MethodReferencesBuilder
6. **TestTemplateContextBuilder -> context/domain**: 构建 Type、Method、Field 域对象
7. **context/service -> context/domain**: JavaTestBuilderImpl 使用 Type、Method、Param 生成代码
8. **context/service -> request/dto**: 使用 CaseResult、CaseParam 渲染 AI 生成的用例
9. **request -> agent**: TemplateRequestService 通过 PluginWebsocketClient 发送 AI 请求
10. **builder -> context/domain**: MockitoMockBuilder 检查 Field、Type 的 mockability

---

## 4. 与 agent/service 的调用链

### 4.1 AI 请求调用链

```
TemplateGenerator.createTestClass()
  -> CreateTestMethodTask.run()
    -> TemplateRequestService.requestAI()
      -> appendTypeBody()              // 构建类结构信息
      -> getBodyContent()              // 获取方法体
      -> PluginWebsocketClient         // 发送 WebSocket 请求
      -> 等待 AI 响应
    -> TemplateRequestService.handleAgentAction()
      -> caseHandle()                  // 解析 <test-case> 标签
      -> caseMocks()                   // 解析 <case-mock> 标签
      -> convertInput()                // 转换输入参数
      -> convertOutput()               // 转换输出参数
      -> addMock()                     // 添加 mock 方法
      -> addBranches()                 // 添加分支信息
    -> TestTemplateContextBuilder.build()
      -> 创建 Type/Method/Field 域对象
      -> resolveMethodReferences()
      -> resolveMethodCallFields()
    -> Velocity 模板合并
    -> CodeRefactorUtil.uncommentImports()
    -> SocketMessageHandleListener.send2Web()  // 发送结果到 WebView
```

### 4.2 外部包依赖

| 外部包 | 被调用类 | 调用场景 |
|--------|---------|---------|
| com.aicode.agent | PluginWebsocketClient, SocketMessageHandleListener, MessageDto, CommandEnum | AI 请求发送与响应接收 |
| com.aicode.action.batch | GeneratorConfig, MethodGeneratorConfig, BatchUnitTestTemplateService | 批量测试配置 |
| com.aicode.test | UnitTestService, UnitTestDto, MethodUnitTestData | 测试结果回传 |
| com.aicode.settings | AICodeSettingsState, BatchUnitTestSettingsState | 读取用户配置 |
| com.aicode.enums | DuplicateRule, PluginSceneEnum, TestGenerationProcess, UnitTestBaseEnum, UnitTestMockEnum, WebViewDataTypeEnum | 枚举引用 |
| com.aicode.util | StringUtils, TypeUtils, ClassNameUtils, FileUtils, JavaPsiUtils, PropertyUtils, PsiUtils, ReflectUtil, AICodeUtils, UnitTestCollectUtil | 工具方法 |
| com.aicode.message | BasicActionsBundle | 国际化消息 |

### 4.3 WebSocket 通信协议

```
// 请求方向：IDE -> Agent Server
TemplateRequestService.requestAI()
  -> PluginWebsocketClient.send()
    -> CommandEnum: REQUEST_COMPLETE / GENERATE_UNIT_TEST
    -> TemplateTestPromptDto: { stream, content, unitTest }

// 响应方向：Agent Server -> IDE
TemplateRequestService.handleAgentAction()
  <- CommandEnum: UNIT_TEST_CASE / UNIT_TEST_ERROR
  <- JsonObject: { caseMethodName, input, output, mockMethods, branches, exception }
```

---

## 5. 模板生成完整流程图

```
用户触发测试生成
       |
       v
[TemplateGenerator]  (单例入口)
       |
       +-- 批量模式 --> [batchTestClass()]
       |                    |
       |                    v
       |              [CreateTestFileTask] (Backgroundable)
       |                    |
       +-- 单方法模式 -> [createTestClass()]
                            |
                            v
                      [CreateTestMethodTask]
                            |
       +-------+-------+---+---+-------+-------+
       |       |       |       |       |       |
       v       v       v       v       v       v
    [解析    [创建    [构建    [请求    [合并    [写入
     PSI]    TypeDict] 上下文]  AI]    模板]   文件]
       |       |       |       |       |       |
       v       v       v       v       v       v
    PsiClass  Type    Test    Template  Velocity  PsiDirectory
    PsiMethod Dictionary Template Request  合并    写入
    PsiField         Context Builder  Service  结果
                            |           |
                   +--------+--+     +--+--------+
                   |           |     |           |
                   v           v     v           v
              [MockBuilder [Method [构建      [解析
               Factory]    Ref    Prompt]    响应]
                           Builder]
                                        |
                                        v
                              +---------+---------+
                              |                   |
                              v                   v
                         [CaseResult]       [CaseBranch]
                         [CaseParam]        [ToMockMethod]
                              |                   |
                              +---------+---------+
                                        |
                                        v
                              [JavaTestBuilderImpl]
                              (渲染测试代码)
                                        |
                              +---------+---------+
                              |                   |
                              v                   v
                         [参数渲染]          [断言渲染]
                         renderJavaCallParams  renderJavaMethodAssert
                         renderReturnParam     AssertUtil.assertResult
                         renderInitType
                              |
                              v
                         [Velocity 模板合并]
                              |
                              v
                         [CodeRefactorUtil.uncommentImports()]
                              |
                              v
                         [SocketMessageHandleListener.send2Web()]
                              |
                              v
                         WebView 前端展示
```

### 流程步骤详解

1. **用户触发**: 通过 Action 或 WebView 触发测试生成
2. **任务检查**: 检查是否有未完成的测试生成任务（"上一个单元测试任务还未完成"）
3. **PSI 解析**: 在 ReadAction 中解析 PsiClass、PsiMethod、PsiField
4. **类型字典**: 创建 TypeDictionary，缓存类型解析结果
5. **上下文构建**: TestTemplateContextBuilder 组装所有模板参数
6. **AI 请求**: TemplateRequestService 构建并发送 AI prompt
7. **响应解析**: 解析 AI 返回的 XML 格式用例数据
8. **代码渲染**: JavaTestBuilderImpl 根据 CaseResult 渲染测试代码
9. **模板合并**: Velocity 引擎合并模板和参数
10. **文件写入**: 在 WriteAction 中写入测试文件
11. **后处理**: 取消注释 import、格式化代码
12. **结果回传**: 通过 WebSocket 发送结果到 WebView

---

## 6. 关键发现

### 6.1 无 H() 混淆调用

template 包中 **没有发现** `com.aicode.util.H` 的调用。所有字符串常量均为明文，包括中文提示信息。

### 6.2 AI 通信协议

AI 请求使用 XML 标签格式：
- `<test-case>...</test-case>` - 测试用例
- `<case-mock>...</case-mock>` - mock 信息
- `<branch>...</branch>` - 分支信息

### 6.3 模板框架支持

支持 7 种测试模板组合：
1. JUnit4 + 无 Mock
2. JUnit5 + 无 Mock
3. JUnit4 + Mockito
4. JUnit4 + PowerMock
5. JUnit5 + Mockito
6. TestNG + Mockito
7. SpringBootTest + Mockito

### 6.4 核心复杂度分布

| 类 | 行数 | 复杂度来源 |
|----|------|-----------|
| JavaTestBuilderImpl | 7056 | 参数渲染、类型解析、构造函数优化、递归构建 |
| TemplateRequestService | 3348 | AI prompt 构建、XML 解析、用例转换 |
| CreateTestFileTask | 2461 | 批量文件生成流程、错误处理、进度管理 |
| MethodFactory | 1844 | PSI 方法解析、调用链构建、泛型替换 |
| Type | 1807 | 类型依赖解析、字段/方法/接口解析 |
| FTManager | 1656 | 模板文件管理、编码/解码、排序 |
| MockitoMockBuilder | 1355 | Mock 代码生成、版本适配、匹配器选择 |

### 6.5 缓存策略

- **TypeDictionary**: 使用 hutool Cache 缓存类型解析结果
- **MethodFactory**: methodIdCaches 缓存方法 ID -> Method 映射
- **TestTemplateContextBuilder**: typeCache 和 typeDictionaryCache 双层缓存
- **TemplateRequestService**: classModelRenders 缓存文件级 AI 请求结果

### 6.6 中文常量清单

| 常量 | 所在类 | 用途 |
|------|--------|------|
| 期望返回值是空，不进行断言 | AssertUtil | 断言跳过日志 |
| 未知的类型 | AssertUtil | 类型判断失败 |
| 上一个单元测试任务还未完成 | TemplateGenerator | 任务冲突警告 |
| 生成单测中... | CreateTestFileTask | 进度提示 |
| 生成完成 | CreateTestFileTask | 完成提示 |
| 生成结束 | CreateTestFileTask | 结束提示 |
| 生成测试文件 | CreateTestFileTask | 文件生成提示 |
| 生成单测文件名称异常 | CreateTestFileTask | 异常提示 |
| 生成文件失败 | CreateTestFileTask | 失败提示 |
| 生成上下文异常 | CreateTestFileTask | 上下文异常 |
| 生成模板上下文信息失败 | CreateTestFileTask | 模板异常 |
| 模板地址为空 | CreateTestFileTask | 模板缺失 |
| 模板生成失败 | CreateTestFileTask | 生成失败 |
| 收集上下文 | CreateTestFileTask | 收集阶段 |
| 请求AI模型 | CreateTestFileTask | AI 请求阶段 |
| 解析完成 | CreateTestFileTask | 解析完成 |
| 手动取消PSI解析 | CreateTestFileTask | 用户取消 |
| 初始化完成 | CreateTestMethodTask | 初始化完成 |
| 单测实现失败 | CreateTestMethodTask | 实现失败 |
| 信息已清除 | TemplateRequestService | 缓存清除 |
| 处理异常信息已清除 | TemplateRequestService | 异常清除 |
| 读取模型数据异常 | TemplateRequestService | AI 数据异常 |
| 读取用例Mock信息异常 | TemplateRequestService | Mock 数据异常 |
| 读取用例输入异常 | TemplateRequestService | 输入数据异常 |
| 补充用例分支信息异常 | TemplateRequestService | 分支数据异常 |
| 转换用例数据失败 | TemplateRequestService | 数据转换失败 |
| 转换异常 | TemplateRequestService | 转换异常 |
| 转换时间异常 | TemplateRequestService | 时间转换异常 |
| 断言生成异常 | AssertUtil | 断言异常 |
| 注释异常方法失败 | CreateTestMethodTask | 注释失败 |
| 更新测试目录异常 | TargetDirectoryLocator | 目录异常 |
| 清理缓存报错 | TemplateRequestService | 缓存清理错误 |
| 当前加载groovy plugin插件失败 | CodeRefactorUtil | Groovy 加载失败 |
| import文件%d信息 | CodeRefactorUtil | import 信息 |
| 请填写需要使用的测试类名 | GeneratedClassNameResolver | 类名输入 |
| 使用其他名称 | ClassNameSelection | 用户选择 |
| &取消 / &打开已有文件 | ClassNameSelection | 按钮文本 |
