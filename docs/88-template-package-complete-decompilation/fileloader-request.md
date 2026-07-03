### 2.7 template/fileloader/ 子包

#### 2.7.1 FTManager（1656 行）

```
public class FTManager &#123;
  private static final String DEFAULT_TEMPLATE_EXTENSION;  // "ft"
  static final String TEMPLATE_EXTENSION_SUFFIX;           // ".ft"
  private static final String ENCODED_NAME_EXT_DELIMITER;  // 编码分隔符

  private final String myName;                    // 管理器名称
  private final boolean myInternal;               // 是否内部模板
  private final Path myTemplatesDir;              // 模板目录
  private final FTManager myOriginal;             // 原始管理器
  private final Map<String, FileTemplateBase> myTemplates;  // 模板映射
  private volatile List&lt;FileTemplateBase&gt; mySortedTemplates; // 排序模板
  private final List&lt;DefaultTemplate&gt; myDefaultTemplates;    // 默认模板
  private final TemplateRegistry templateRegistry;           // 模板注册表

  // 模板操作
  Collection&lt;FileTemplateBase&gt; getAllTemplates(boolean);     // 获取所有模板
  FileTemplateBase getTemplate(String);                      // 获取模板
  FileTemplateBase findTemplateByName(String);               // 按名查找
  FileTemplateBase addTemplate(String, String);              // 添加模板
  void updateTemplates(Collection<? extends FileTemplate>);  // 更新模板
  void loadCustomizedContent();                              // 加载自定义内容
  public void saveTemplates();                               // 保存模板

  // 文件名编码
  public static String encodeFileName(String, String);       // 编码文件名
  private static Pair<String, String> decodeFileName(String);// 解码文件名
&#125;
```

#### 2.7.2 FileTemplateContext

```
public class FileTemplateContext &#123;
  private FileTemplateDescriptor fileTemplateDescriptor;  // 模板描述符
  private final Project project;                          // 项目
  private final String targetClass;                       // 目标类名
  private final PsiPackage targetPackage;                 // 目标包
  private final Module srcModule;                         // 源模块
  private final Module testModule;                        // 测试模块
  private final PsiDirectory targetDirectory;             // 目标目录
  private final PsiClass srcClass;                        // 源类
  private final FileTemplateConfig fileTemplateConfig;    // 模板配置
  private final List&lt;String&gt; excludeMethodList;           // 排除方法列表
  private final Set&lt;PsiMethod&gt; selectedMethods;           // 选中的方法
  private final Boolean requestAi;                        // 是否请求 AI
  private String filePath;                                // 文件路径
&#125;
```

#### 2.7.3 FileTemplatesLoader

```
class FileTemplatesLoader &#123;
  static final String TEMPLATES_DIR;           // "fileTemplates"
  private static final String DEFAULT_TEMPLATES_ROOT;  // 默认模板根路径
  private final FTManager myTestTemplatesManager;       // 测试模板管理器
  private final FTManager myIncludesManager;            // 包含模板管理器
  private final FTManager[] myAllManagers;              // 所有管理器
  private static final String TESTS_DIR;                // "junit"
  public static final String INCLUDES_DIR;              // "includes"

  // 加载默认模板
  private static FileTemplateLoadResult loadDefaultTemplates(List&lt;String&gt;);
  private static void loadDefaultsFromRoot(URL, List&lt;String&gt;, FileTemplateLoadResult);
&#125;
```

#### 2.7.4 TemplateRegistry

```
public class TemplateRegistry &#123;
  // 7 种预定义模板
  public static final String JUNIT4_JAVA_TEMPLATE;              // "JUnit4 Java"
  public static final String JUNIT5_JAVA_TEMPLATE;              // "JUnit5 Java"
  public static final String JUNIT4_MOCKITO_JAVA_TEMPLATE;      // "JUnit4 Mockito Java"
  public static final String JUNIT4_POWERMOCK_JAVA_TEMPLATE;    // "JUnit4 PowerMock Java"
  public static final String JUNIT5_MOCKITO_JAVA_TEMPLATE;      // "JUnit5 Mockito Java"
  public static final String TESTNG_MOCKITO_JAVA_TEMPLATE;      // "TestNG Mockito Java"
  public static final String SPRINGBOOTTEST_MOCKITO_JAVA_TEMPLATE; // "SpringBootTest Mockito Java"

  public List&lt;TemplateDescriptor&gt; getTemplateDescriptors();
  public List&lt;TemplateDescriptor&gt; getEnabledTemplateDescriptors();
  public TemplateDescriptor getEnabledTemplateDescriptor(String, String);
&#125;
```

#### 2.7.5 TemplateDescriptor

```
public class TemplateDescriptor &#123;
  private String htmlDisplayName;       // HTML 显示名
  private String displayName;           // 显示名
  private String tokenizedName;         // 标记化名称
  private String filename;              // 文件名
  private TemplateRole templateRole;    // 角色：Tester/Included
  private String framework;             // 框架
  private String mockFramework;         // Mock 框架
  public static final String LANGUAGE_JAVA = "java";
&#125;
```

#### 2.7.6 TemplateResourceLoader

```
public class TemplateResourceLoader extends ResourceLoader &#123;
  // Velocity 资源加载器：
  // 从 IntelliJ FileTemplate 系统加载模板资源

  public void init(ExtendedProperties);
  public Reader getResourceReader(String, String);
  public InputStream getResourceStream(String);
  public boolean isSourceModified(Resource);
  public long getLastModified(Resource);
&#125;
```

#### 2.7.7 UnitFileTemplate

```
public class UnitFileTemplate extends FileTemplateBase &#123;
  private String name;
  private String displayName;
  private boolean isDefault;
  private String description;
  private String extension;
&#125;
```

#### 2.7.8 UnitTemplateManager

```
public class UnitTemplateManager extends FileTemplateManager &#123;
  public static final String TEST_TEMPLATES_CATEGORY;  // "Tests"
  private static volatile UnitTemplateManager instance; // 单例
  private final FileTemplatesLoader myFileTemplatesLoader;
  private final Project myProject;
  private final TemplateRegistry templateRegistry;
  private Date myTestDate;  // 测试日期

  public static UnitTemplateManager getInstance(Project);
  public FileTemplate[] getTemplates(String);
  public List&lt;TemplateDescriptor&gt; getTestTemplates();
  public FileTemplate getInternalTemplate(String);
  public FileTemplate findCustomTestTemplate(String);
  public void setTestDate(Date);
  public Properties getDefaultProperties();
  public void saveAllTemplates();
&#125;
```

---

### 2.8 template/request/ 子包

#### 2.8.1 DataUtils

```
public class DataUtils &#123;
  private static Set&lt;String&gt; NUMBER_TYPE;   // 数字类型集合
  private static Set&lt;String&gt; BOOLEAN_TYPE;  // 布尔类型集合
  private static Set&lt;String&gt; DATE_TYPE;     // 日期类型集合
  public static final List&lt;String&gt; noSupportValues;  // 不支持的值
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
  public static List&lt;CaseResult&gt; parseToCaseResult(JSONArray);  // 从 JSON 数组解析用例
  public static boolean isEmptyData(CaseResult);  // 是否空数据
&#125;
```

#### 2.8.2 TemplateRequestService（3348 行，第二大类）

```
public class TemplateRequestService &#123;
  public static final Cache<String, FileRequestDto> classModelRenders;  // 类模型渲染缓存
  public static final int MAX_TOKEN_CHAR_LENGTH;   // 最大 token 字符长度
  public static final int MAX_REQUEST_LIMIT;       // 最大请求限制
  public static final long RETRY_WAIT_TIME;        // 重试等待时间

  // AI 请求核心方法
  public static MethodRequestResult requestAI(PsiClass, Type, PsiMethod, TypeDictionary,
      GeneratorTemplateConfig, String, Project, List&lt;MessageDto&gt;, Set&lt;Method&gt;,
      FileRequestDto, Module, Map<String, String>);
    // 1. 构建方法上下文信息（类结构、方法签名、调用链）
    // 2. 构建 AI prompt（包含被测代码、mock 信息、分支信息）
    // 3. 通过 PluginWebsocketClient 发送请求
    // 4. 等待 AI 响应
    // 5. 解析响应为 CaseResult

  public static List&lt;MessageDto&gt; requestAI(String, PsiClass, Type, TypeDictionary,
      GeneratorTemplateConfig, String, Project, boolean, Module, Set&lt;Method&gt;);
    // 批量请求 AI：遍历所有可测方法

  // AI 响应处理
  public static synchronized void handleAgentAction(CommandEnum, JsonObject, MessageDto, String, Project);
    // 处理 AI 返回的测试用例数据：
    // 1. 解析 XML 标签：<test-case>、<case-mock>、&lt;branch&gt;
    // 2. 转换为 CaseResult/CaseParam/ToMockMethod/CaseBranch
    // 3. 存入 FileRequestDto

  public static synchronized void handleRequestErrorTestCase(ResponseDto, CommandEnum, MessageDto);
    // 处理 AI 请求错误

  // 辅助方法
  private static void appendTypeBody(Type, StringBuilder, PsiClass, boolean, Set&lt;String&gt;, Project, Module, TypeDictionary, Map);
    // 构建类型体信息（用于 AI prompt）

  private static void getBodyContent(StringBuilder, PsiMethod, PsiClass, List&lt;String&gt;, boolean, int);
    // 获取方法体内容

  public static boolean shouldBeTested(PsiMethod, PsiClass, GeneratorTemplateConfig);
    // 方法是否应被测试

  public static synchronized boolean isModelReturned(String, String);
    // 检查 AI 模型是否已返回

  public static synchronized boolean isAllReturned(String);
    // 检查所有方法是否已返回

  // 用例解析
  private static void addCase(String, Method, List&lt;CaseResult&gt;);
  private static void convertException(String, CaseResult, Method);
  private static CaseParam convertOutput(String, Method);
  private static Map<String, CaseParam> convertInput(String, Method);
  private static void addMock(String, List&lt;ToMockMethod&gt;);
  private static void addBranches(String, Method, List&lt;CaseBranch&gt;);
  private static void resolveAllBranches(ResolvedBranch, List&lt;CaseBranch&gt;);
  private static void resolveCaseBranch(ResolvedBranch, Boolean, List&lt;CaseBranch&gt;);

  // XML 解析
  private static String caseHandle(String, String);   // 解析 <test-case> 标签
  private static String caseMocks(String, String);    // 解析 <case-mock> 标签
  public static String extractTagValue(String, String);  // 提取标签值
&#125;
```

#### 2.8.3 DTO 类

```
// FileRequestDto - 文件请求
public class FileRequestDto &#123;
  private String requestId;
  private String filePath;
  private List&lt;MethodRequestResult&gt; methodRequestResults;
  public int getDiff(int);  // 计算耗时差值
&#125;

// MethodRequestResult - 方法请求结果
public class MethodRequestResult &#123;
  private Integer requestCount;
  private String requestId;
  private String methodId;
  private Method method;
  private boolean isReturn;
  private Date beginTime;
  private Date endTime;
  public Long getDiff();  // 计算耗时
&#125;

// TemplateTestDto - 测试 DTO
public class TemplateTestDto &#123;
  private String testFrame;       // 测试框架
  private String mockFrame;       // Mock 框架
  private String testContent;     // 测试内容
  private Integer testCaseNumber; // 用例数量
  private List&lt;String&gt; branchList;// 分支列表
&#125;

// TemplateTestPromptDto - AI 提示 DTO
public class TemplateTestPromptDto &#123;
  private boolean stream;         // 是否流式
  private String content;         // 提示内容
  private TemplateTestDto unitTest; // 单元测试信息
&#125;

// CaseBranch - 用例分支
public class CaseBranch &#123;
  private String methodName;
  private String conditionText;
  private Boolean result;
  private Boolean isOut;
  private Integer startOffset;
  private Integer endOffset;
  public String toCommitText();
  public String toCommitText(boolean);
&#125;

// CaseParam - 用例参数
public class CaseParam &#123;
  private String name;
  private String type;
  private String canonicalName;
  private Object data;
  public TypeEnum getResolveType();  // 解析类型枚举
&#125;

// CaseResult - 用例结果
public class CaseResult &#123;
  private String caseMethodName;
  private String type;
  private Map<String, CaseParam> input;
  private List&lt;CaseBranch&gt; branches;
  private List&lt;ToMockMethod&gt; mockMethods;
  private CaseParam output;
  private String message;
  private String exception;
  private String exceptionMessage;
  private String methodCommentId;
  public String toCommitBranchText();
  public String toComment();
  public String toEndComment();
&#125;

// ToMockMethod - Mock 方法
public class ToMockMethod &#123;
  private String className;
  private String methodName;
  private CaseParam returnValue;
&#125;

// TypeEnum - 类型枚举
public enum TypeEnum &#123;
  BOOLEAN, STRING, NUMBER, ARRAY, LIST, HASHMAP, CLASS, STREAM, DATE;
  public static TypeEnum parse(String);  // 从字符串解析
&#125;
```

---
