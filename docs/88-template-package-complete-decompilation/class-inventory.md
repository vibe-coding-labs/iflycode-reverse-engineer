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
