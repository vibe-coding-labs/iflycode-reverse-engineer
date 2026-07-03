### 2.5 template/context/resolved/ 子包

```
// MethodCallArg - 已解析方法调用参数
public class MethodCallArg &#123;
  private final String text;
  private final String name;
  private final PsiType type;
&#125;

// ResolveComponents - 解析组件集合
public class ResolveComponents &#123;
  private List&lt;ResolveVarible&gt; variables;      // 变量列表
  private List&lt;ResolvedMethodCall&gt; methodCalls; // 方法调用列表
  private List&lt;ResolvedBranch&gt; branches;        // 分支列表
&#125;

// ResolveVarible - 已解析变量
public class ResolveVarible &#123;
  private String name;
  private Type type;
  private Method callMethod;  // 调用的方法
&#125;

// ResolvedBranch - 已解析分支
public class ResolvedBranch &#123;
  private String conditionText;     // 条件文本
  private Boolean result;           // 分支结果
  private Boolean isOut;            // 是否跳出
  private Integer startOffset;      // 起始偏移
  private Integer endOffset;        // 结束偏移
  private List&lt;ResolvedBranch&gt; children;  // 子分支
  private ResolvedBranch parent;    // 父分支
  private Boolean prev;             // 前置条件
  private Boolean after;            // 后置条件
&#125;

// ResolvedMethodCall - 已解析方法调用
public class ResolvedMethodCall &#123;
  private String methodName;
  private List&lt;MethodCallArg&gt; arguments;
&#125;

// ResolvedReference - 已解析引用
public class ResolvedReference &#123;
  private String name;
  private Type type;
&#125;
```

---

### 2.6 template/context/service/ 子包

#### 2.6.1 LangTestBuilder（接口）

```
public interface LangTestBuilder &#123;
  String PARAMS_SEPARATOR = ", ";
  String renderJavaCallParams(List&lt;Param&gt;, CaseResult);        // 渲染调用参数
  String renderJavaCallParam(Type, String, CaseResult);        // 渲染单个调用参数
  String renderJavaVariable(Type, String, CaseResult);         // 渲染变量
  String renderJavaMethodCaseBody(Type, Method);               // 渲染方法用例体
  String renderJavaMethodAssert(Method, CaseResult, String);   // 渲染断言
&#125;
```

#### 2.6.2 TestBuilder（接口）

```
public interface TestBuilder &#123;
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
&#125;
```

#### 2.6.3 JavaTestBuilderImpl（7056 行，最大类）

```
public class JavaTestBuilderImpl implements LangTestBuilder &#123;
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
  public String renderJavaCallParams(List&lt;Param&gt;, CaseResult);
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
  protected void buildCallParam(StringBuilder, Node&lt;Param&gt;, CaseParam, int);
    // 递归构建调用参数，处理嵌套类型

  protected void buildVaribleCallParam(StringBuilder, Node&lt;Param&gt;, CaseParam, int);
    // 构建变量调用参数

  void buildJavaParam(StringBuilder, Node&lt;Param&gt;, CaseParam, int);
    // 构建 Java 参数：处理构造函数、setter、getter

  private void resolveMethod(Method, Node&lt;Param&gt;, CaseParam, StringBuilder, Type, Type, String, Boolean, Map, int);
    // 解析方法调用链，生成 stub/verify 代码

  private void renderSetMethod(StringBuilder, Map, Type, Node, JSONObject, String, int);
    // 渲染 setter 方法调用

  protected void renderMapOrList(String, StringBuilder, Type, Node, CaseParam, List&lt;MethodCall&gt;, int);
    // 渲染 Map 或 List 初始化

  protected void renderEnumValue(StringBuilder, Type);
    // 渲染枚举值

  // 构造函数优化
  protected Method findValidConstructor(Type, boolean);
    // 查找有效构造函数

  boolean shouldOptimizeConstructorInitialization(int, int);
    // 是否应优化构造函数初始化

  private boolean isUnused(Type, Method, List&lt;Field&gt;);
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
&#125;
```

#### 2.6.4 LangTestBuilderFactory

```
public class LangTestBuilderFactory &#123;
  private final FileTemplateConfig fileTemplateConfig;
  private final Module srcModule;
  private final TypeDictionary typeDictionary;
  private final JavaVersion javaVersion;
  private Integer renderType;

  public LangTestBuilder createTestBuilder(Method, TestBuilder$ParamRole, Map, Map, Integer);
    // 创建 JavaTestBuilderImpl 实例
&#125;
```

#### 2.6.5 TestBuilderImpl

```
public class TestBuilderImpl implements TestBuilder &#123;
  private final LangTestBuilderFactory langTestBuilderFactory;

  // 委托实现：所有 TestBuilder 方法都委托给 LangTestBuilder
  // 根据 paramRole (Mock/Input/Output) 创建不同的 LangTestBuilder
&#125;
```

---
