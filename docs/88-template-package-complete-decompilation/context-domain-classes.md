### 2.4 template/context/domain/ 子包

#### 2.4.1 Type（核心域对象，1807 行）

```
public class Type &#123;
  private final String canonicalName;        // 全限定名
  private final String name;                 // 简名
  private final boolean isPrimitive;         // 基本类型
  private final String packageName;          // 包名
  private final List&lt;Type&gt; composedTypes;    // 组合类型（泛型参数）
  private final boolean array;               // 数组
  private final int arrayDimensions;         // 数组维度
  private final boolean varargs;             // 可变参数
  private final boolean isEnum;              // 枚举
  private final List&lt;String&gt; enumValues;     // 枚举值
  private final boolean isInterface;         // 接口
  private final boolean isAbstract;          // 抽象
  private final boolean isStatic;            // 静态
  private final boolean isFinal;             // final
  private final Type parentContainerClass;   // 父容器类
  private final String superClass;           // 父类
  private boolean dependenciesResolved;      // 依赖是否已解析
  private boolean hasDefaultConstructor;     // 有默认构造函数
  private final List&lt;Method&gt; methods;        // 方法列表
  private final Set&lt;String&gt; staticClassNames;// 静态类名
  private final Set&lt;Field&gt; fields;           // 字段列表
  private final List&lt;Type&gt; implementedInterfaces;  // 实现的接口
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

  public List&lt;Method&gt; findConstructors();
    // 查找构造函数，按参数数量排序

  private boolean buildAnnotatedByDi(PsiClass, TypeDictionary);
    // 检查 DI 注解（@Service/@Component/@Repository 等）

  private boolean buildAnnotatedBySpringConfig(PsiClass, TypeDictionary);
    // 检查 Spring 配置注解

  // 工具方法
  public boolean isArray(), isCollection(), isEnum(), isInterface();
  public String renderArray();  // 渲染数组声明
&#125;
```

#### 2.4.2 Method

```
public class Method &#123;
  private final String methodId;               // 方法唯一标识
  private final String name;                   // 方法名
  private final Type returnType;               // 返回类型
  private final String ownerClassCanonicalType;// 所属类全限定名
  private final List&lt;Param&gt; methodParams;      // 参数列表
  private final List&lt;String&gt; callParams;       // 调用参数
  private Set&lt;String&gt; exceptions;              // 异常列表
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
  private final Set&lt;MethodCall&gt; directMethodCalls;     // 直接方法调用
  private final Set&lt;MethodCall&gt; methodCalls;           // 所有方法调用
  private final ResolveComponents resolveComponents;   // 解析组件
  private final Set&lt;StaticMethodCall&gt; staticMethodCalls; // 静态方法调用
  private final Set&lt;Method&gt; methodReferences;          // 方法引用
  private final Set&lt;MethodCall&gt; calledFamilyMembers;   // 家族方法调用
  private ResolvedBranch caseBranchSet;                // 用例分支集
  private final Set&lt;Reference&gt; internalReferences;     // 内部引用
  private final Set&lt;Field&gt; indirectlyAffectedFields;   // 间接影响字段
  private final List&lt;CaseResult&gt; caseResults;          // AI 生成的用例结果
  private final CaseResult caseResult;                 // 单个用例结果
  private final Set&lt;String&gt; reflectionMethods;         // 反射方法

  public boolean hasReturn();   // 是否有返回值
  public boolean hasParams();   // 是否有参数
  public void resolveExceptions();  // 从方法调用链解析异常
&#125;
```

#### 2.4.3 Field

```
public class Field &#123;
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
&#125;
```

#### 2.4.4 Param, MethodCall, MethodCallArgument, StaticMethodCall, Reference, Node, SyntheticParam

```
// Param - 方法参数
public class Param &#123;
  final Type type;
  private String name;
  private final ArrayList&lt;Field&gt; assignedToFields;  // 赋值给的字段
&#125;

// MethodCall - 方法调用
public class MethodCall &#123;
  private final String variableName;        // 调用变量名
  private final Method method;              // 被调方法
  private final List&lt;MethodCallArgument&gt; methodCallArguments;  // 调用参数
&#125;

// MethodCallArgument - 方法调用参数
public class MethodCallArgument &#123;
  private final String text;  // 参数文本
&#125;

// StaticMethodCall - 静态方法调用（继承 MethodCall）
public class StaticMethodCall extends MethodCall &#123;
  private String ownerClass;  // 静态方法所属类
&#125;

// Reference - 引用
public class Reference &#123;
  private final String referenceName;
  private final Type referenceType;
  private final Type ownerType;
  private final String referenceId;
&#125;

// Node&lt;T&gt; - 递归树节点
public class Node&lt;T&gt; &#123;
  private final T data;
  private final Node&lt;T&gt; parent;
  private final int depth;
  private Object needData;
  public boolean hasSameAncestor();  // 是否有相同祖先
&#125;

// SyntheticParam - 合成参数（继承 Param）
public class SyntheticParam extends Param &#123;
  private final UsageContext usageContext;  // Property/Generic
&#125;
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
