### 2.2 template/builder/ 子包

#### 2.2.1 MethodFactory

```
public class MethodFactory &#123;
  public static Cache<String, Method> methodIdCaches;  // 方法 ID 缓存
  private static String jsonDirPath;                    // JSON 数据目录路径

  // 核心方法
  private static List&lt;CaseResult&gt; getLocalData(PsiClass, String, PsiType, String);
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

  public static void resolveInternalReferences(TypeDictionary, PsiMethod, Method, PsiClass, Set&lt;String&gt;, int);
    // 解析内部引用：字段赋值、方法调用关系

  public static boolean hasInternalMethodCall(Method, Type);
    // 是否有内部方法调用

  public static List&lt;ResolvedMethodCall&gt; resolvedMethodCalls(PsiMethod);
    // 解析方法中的所有方法调用

  private static Set&lt;MethodCall&gt; resolveCalledMethods(PsiMethod, PsiClass, Method, TypeDictionary, ...);
    // 解析被调用的方法：遍历 PsiMethod 中的方法调用表达式

  public static boolean isTestable(PsiMethod, PsiClass);
    // 方法是否可测试：非抽象、非native、可见

  public static boolean isInherited(PsiMethod, PsiClass);
    // 方法是否继承自父类

  private static Optional&lt;PsiSubstitutor&gt; findMethodSubstitutor(PsiMethod, PsiClass, PsiType);
    // 查找方法泛型替换器
&#125;
```

#### 2.2.2 MethodReferencesBuilder

```
public class MethodReferencesBuilder &#123;
  public void resolveMethodReferences(int, List&lt;Method&gt;);
    // 解析方法间引用关系

  public void resolveMethodCallFields(int, List&lt;Method&gt;, List&lt;Field&gt;);
    // 解析方法调用影响的字段

  public void resolveMethodVariables(Type);
    // 解析方法中的变量

  public void resolveMethodCallByCaseResult(Type);
    // 按用例结果解析方法调用

  public boolean isMethodCalled(Method, Method);
    // 判断方法是否被另一个方法调用

  private void resolveFieldsAffectedByCtor(Type, int);
    // 解析构造函数影响的字段

  private void resolveMethodCalls(List&lt;Method&gt;, Method);
    // 解析方法调用链
&#125;
```

#### 2.2.3 MockBuilder（接口）

```
public interface MockBuilder &#123;
  boolean isMockable(Field, Type);           // 字段是否可 mock
  boolean isMockableType(Type, Type);        // 类型是否可 mock
  String getImmockabiliyReason(String, Field);  // 不可 mock 原因
  String buildArgsTypes(List&lt;Param&gt;);        // 构建参数类型字符串
  String buildStaticTypeNames(Type);         // 构建静态类型名
  String buildMockArgsMatchers(List&lt;Param&gt;); // 构建 mock 参数匹配器
  Set&lt;String&gt; mockStaticClass(Method);       // 获取需要 mock 的静态类
  Boolean isMockStatic(Method);              // 是否需要 mock 静态方法
  String resolveExceptions(Method);          // 解析方法异常
  boolean isMockExpected(Field);             // 字段是否期望 mock
&#125;
```

#### 2.2.4 MockBuilderFactory

```
public class MockBuilderFactory &#123;
  private static final String MOCKITO_CORE_JAR_NAME_PREFIX;  // "mockito-core"
  private static final Pattern MOCKITO_CORE_VERSION_REGEX;   // 版本号正则

  public MockitoMockBuilder createMockitoMockBuilder(FileTemplateContext, TestSubjectInspector, List&lt;String&gt;);
    // 创建 MockitoMockBuilder

  public PowerMockBuilder createPowerMockBuilder(FileTemplateContext, TestSubjectInspector, List&lt;String&gt;);
    // 创建 PowerMockBuilder

  public static boolean isMockInline(FileTemplateContext);
    // 检测是否使用 mockito-inline（mock maker）

  String resolveMockitoVersion(List&lt;String&gt;);
    // 从 classpath JAR 列表解析 Mockito 版本号
&#125;
```

#### 2.2.5 MockitoMockBuilder

```
public class MockitoMockBuilder implements MockBuilder &#123;
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
  public String buildMockArgsMatchers(List&lt;Param&gt;);    // 构建 any()/eq() 匹配器
  public String buildArgsTypes(List&lt;Param&gt;);           // 构建参数类型
  public String buildStaticTypeNames(Type);            // 构建静态类型名

  // Mock 策略
  public boolean shouldStub(Method, List&lt;Field&gt;);      // 是否需要 stub
  public boolean shouldVerify(Method, List&lt;Field&gt;);    // 是否需要 verify
  public boolean isMockExpected(Field);                // 是否期望 mock

  // 异常处理
  public String resolveExceptions(Method);             // 解析方法异常
  public void appendMethodExceptionTypes(List&lt;String&gt;, List&lt;String&gt;);

  // 版本相关
  public String getInitMocksMethod();                  // 获取 initMocks 方法名（版本相关）
  public String getMockitoCoreVersion();
&#125;
```

#### 2.2.6 PowerMockBuilder

```
public class PowerMockBuilder extends MockitoMockBuilder &#123;
  private final boolean renderInternalMethodCallStubs;

  public boolean hasInternalMethodCall(Method, Type);
    // 检查是否有内部方法调用（需要 PowerMock mock）

  protected boolean isMockableByMockFramework(Field);
    // PowerMock 支持更多类型的 mock（final 类等）
&#125;
```

---
