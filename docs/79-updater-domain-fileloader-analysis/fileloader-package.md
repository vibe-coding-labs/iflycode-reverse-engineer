## 4. Template/FileLoader 包 — 文件模板加载系统

### 4.1 包总览

| 类名 | 源文件名 | 大小 | 职责 |
|------|----------|------|------|
| `FileTemplatesLoader` | FileTemplatesLoader.java | 18K | 模板文件加载入口 |
| `FTManager` | FTManager.java | 24K | 模板文件管理器（核心） |
| `UnitTemplateManager` | UnitTemplateManager.java | 21K | 单测模板管理器（单例） |
| `UnitTemplateManager$1` | UnitTemplateManager.java | 3.1K | 项目级模板方案 |
| `FileTemplateContext` | FileTemplateContext.java | 6.2K | 模板上下文 |
| `FileTemplateLoadResult` | FileTemplateLoadResult.java | 2.1K | 加载结果容器 |
| `UnitFileTemplate` | UnitFileTemplate.java | 3.5K | 单测文件模板 |
| `TemplateDescriptor` | TemplateDescriptor.java | 3.5K | 模板描述符 |
| `TemplateRegistry` | TemplateRegistry.java | 5.4K | 模板注册表 |
| `TemplateResourceLoader` | TemplateResourceLoader.java | 4.4K | Velocity 资源加载器 |
| `TemplateRole` | TemplateRole.java | 1.1K | 模板角色枚举 |

### 4.2 TemplateRole — 模板角色枚举

**路径**: `com/aicode/template/fileloader/TemplateRole`

**枚举值**:
```
Tester    // 测试模板
Included  // 包含模板（可被其他模板 include）
```

### 4.3 TemplateDescriptor — 模板描述符

**路径**: `com/aicode/template/fileloader/TemplateDescriptor`

**字段**:
```
String htmlDisplayName     // HTML 显示名（用于 UI）
String displayName         // 纯文本显示名
String tokenizedName       // 分词名（含框架信息）
String filename            // 文件名
TemplateRole templateRole  // 模板角色
String framework           // 测试框架（如 JUnit4, JUnit5）
String mockFramework       // Mock 框架（如 Mockito, Powermock）
String LANGUAGE_JAVA = "java"  // 语言常量
```

**构造函数逻辑**:
```
TemplateDescriptor(displayName, tokenizedName, filename, role)
  → htmlDisplayName = displayName
  → this.displayName = displayName
  → this.tokenizedName = tokenizedName
  → this.filename = filename
  → this.templateRole = role
  → 解析 tokenizedName:
    → 按 "&" 分割
    → 如果有两部分: framework = parts[0], mockFramework = parts[1].toLowerCase() + "java"
    → 如果只有一部分: framework = parts[0]
```

### 4.4 TemplateRegistry — 模板注册表

**路径**: `com/aicode/template/fileloader/TemplateRegistry`

**字段**:
```
static Logger LOG
static String TEMPLATE_FILE_SUFFIX
static List&lt;TemplateDescriptor&gt; templateDescriptors

// 预定义模板常量
static final String JUNIT4_JAVA_TEMPLATE = "JUnit4.java"
static final String JUNIT5_JAVA_TEMPLATE = "JUnit5.java"
static final String JUNIT4_MOCKITO_JAVA_TEMPLATE = "JUnit4&Mockito.java"
static final String JUNIT4_POWERMOCK_JAVA_TEMPLATE = "JUnit4&Powermock.java"
static final String JUNIT5_MOCKITO_JAVA_TEMPLATE = "JUnit5&Mockito.java"
static final String TESTNG_MOCKITO_JAVA_TEMPLATE = "TestNG&Mockito.java"
static final String SPRINGBOOTTEST_MOCKITO_JAVA_TEMPLATE = "SpringBootTest&Mockito.java"
```

**静态初始化注册的模板**:

| 显示名 | 文件名 | 框架 | Mock 框架 |
|--------|--------|------|-----------|
| JUnit4 | JUnit4.java | JUnit4 | (无) |
| JUnit5 | JUnit5.java | JUnit5 | (无) |
| JUnit4 & Mockito | JUnit4&Mockito.java | JUnit4 | Mockito |
| JUnit4 & Powermock | JUnit4&Powermock.java | JUnit4 | Powermock |
| JUnit5 & Mockito | JUnit5&Mockito.java | JUnit5 | Mockito |
| TestNG & Mockito | TestNG&Mockito.java | TestNG | Mockito |
| SpringBootTest & Mockito | SpringBootTest&Mockito.java | SpringBootTest | Mockito |

**方法**:
| 方法 | 用途 |
|------|------|
| `getTemplateDescriptors()` | 返回所有模板描述符 |
| `getEnabledTemplateDescriptors()` | 返回所有启用的模板 |
| `getEnabledTemplateDescriptor(String, String)` | 按框架+Mock框架查找模板 |

**getEnabledTemplateDescriptor 查找逻辑**:
```
1. 遍历所有启用的模板
2. 优先匹配: framework 匹配 AND mockFramework 匹配
3. 降级匹配: framework 匹配 OR mockFramework 匹配（仅返回第一个降级匹配）
4. isMatch 逻辑: 取框架名中 "." 前的部分进行忽略大小写比较
```

### 4.5 UnitFileTemplate — 单测文件模板

**路径**: `com/aicode/template/fileloader/UnitFileTemplate`
**继承**: `FileTemplateBase`（IntelliJ 内部类）

**字段**:
```
String name            // 模板名
String displayName     // 显示名
boolean isDefault      // 是否默认模板
String description     // 描述
String extension       // 扩展名
```

### 4.6 FileTemplateContext — 模板上下文

**路径**: `com/aicode/template/fileloader/FileTemplateContext`

**字段**:
```
FileTemplateDescriptor fileTemplateDescriptor  // 模板描述
final Project project                          // 项目
final String targetClass                       // 目标类名
final PsiPackage targetPackage                 // 目标包
final Module srcModule                         // 源码模块
final Module testModule                        // 测试模块
final PsiDirectory targetDirectory             // 目标目录
final PsiClass srcClass                        // 源码类
final FileTemplateConfig fileTemplateConfig    // 模板配置
final List&lt;String&gt; excludeMethodList           // 排除方法列表
final Set&lt;PsiMethod&gt; selectedMethods          // 选中的方法
final Boolean requestAi                        // 是否请求 AI
String filePath                                // 文件路径
```

**第二个构造函数额外参数**: `List&lt;PsiMethod&gt;` — 直接传入方法列表（会转换为 selectedMethods Set）

### 4.7 FileTemplateLoadResult — 加载结果

**路径**: `com/aicode/template/fileloader/FileTemplateLoadResult`

**字段**:
```
MultiMap<String, DefaultTemplate> result  // 按类别分组的默认模板
URL defaultTemplateDescription            // 模板描述文档 URL
URL defaultIncludeDescription             // 包含模板描述文档 URL
```

### 4.8 FileTemplatesLoader — 模板文件加载器

**路径**: `com/aicode/template/fileloader/FileTemplatesLoader`（包私有类）

**字段**:
```
static final Logger LOG
static final String TEMPLATES_DIR
private static final String DEFAULT_TEMPLATES_ROOT
private static final String DESCRIPTION_FILE_EXTENSION
private static final String DESCRIPTION_EXTENSION_SUFFIX
private static final String DEFAULT_TEMPLATE_DESCRIPTION_FILENAME
final FTManager myTestTemplatesManager     // 测试模板管理器
final FTManager myIncludesManager          // 包含模板管理器
final FTManager[] myAllManagers            // 所有管理器数组
private static final String TESTS_DIR
public static final String INCLUDES_DIR
private final URL myDefaultTemplateDescription
private final URL myDefaultIncludeDescription
```

**方法**:
| 方法 | 用途 |
|------|------|
| `getAllManagers()` | 返回所有 FTManager |
| `getInternalTestTemplatesManager()` | 获取内置测试模板管理器 |
| `getCustomTestTemplatesManager()` | 获取自定义测试模板管理器 |
| `getPatternsManager()` | 获取模式管理器 |
| `loadDefaultTemplates(List&lt;String&gt;)` | 从 classpath 加载默认模板 |
| `loadDefaultsFromRoot(URL, List&lt;String&gt;, FileTemplateLoadResult)` | 从指定根 URL 加载 |

### 4.9 FTManager — 模板文件管理器

**路径**: `com/aicode/template/fileloader/FTManager`

**字段**:
```
static final Logger LOG
static final String DEFAULT_TEMPLATE_EXTENSION
static final String TEMPLATE_EXTENSION_SUFFIX
static final String ENCODED_NAME_EXT_DELIMITER
final String myName                        // 管理器名称
final boolean myInternal                   // 是否内置
final Path myTemplatesDir                  // 模板目录路径
final FTManager myOriginal                // 原始管理器（用于自定义模板）
final Map<String, FileTemplateBase> myTemplates  // 模板映射
volatile List&lt;FileTemplateBase&gt; mySortedTemplates // 排序后的模板列表
final List&lt;DefaultTemplate&gt; myDefaultTemplates   // 默认模板列表
final TemplateRegistry templateRegistry    // 模板注册表
```

**方法**:
| 方法 | 用途 |
|------|------|
| `getName()` | 获取管理器名称 |
| `getAllTemplates(boolean)` | 获取所有模板（可包含默认） |
| `getTemplate(String)` | 按名获取模板 |
| `findTemplateByName(String)` | 按名查找模板 |
| `addTemplate(String, String)` | 添加模板 |
| `updateTemplates(Collection)` | 更新模板集合 |
| `setDefaultTemplates(Collection)` | 设置默认模板 |
| `loadCustomizedContent()` | 加载自定义模板内容 |
| `saveTemplates()` | 保存模板到磁盘 |
| `encodeFileName(String, String)` | 编码文件名 |
| `decodeFileName(String)` | 解码文件名 |

### 4.10 UnitTemplateManager — 单测模板管理器

**路径**: `com/aicode/template/fileloader/UnitTemplateManager`
**继承**: `FileTemplateManager`（IntelliJ 平台类）

**字段**:
```
static final Logger LOG
static final String TEST_TEMPLATES_CATEGORY = "tests"
final FileTemplatesLoader myFileTemplatesLoader  // 加载器
static volatile UnitTemplateManager instance      // 单例
final Project myProject                           // 项目
final FileTemplatesScheme myProjectScheme          // 项目方案
FileTemplatesScheme myScheme                       // 当前方案
boolean myInitialized                             // 是否已初始化
final TemplateRegistry templateRegistry            // 模板注册表
Date myTestDate                                    // 测试日期
```

**方法**:
| 方法 | 用途 |
|------|------|
| `getInstance(Project)` | 获取项目级单例 |
| `getDefaultInstance()` | 获取默认单例 |
| `getSettings()` | 获取 FileTemplatesLoader（懒加载） |
| `getCurrentScheme()` | 获取当前模板方案 |
| `checkInitialized()` | 确保已初始化 |
| `getTemplates(String)` | 按类别获取模板 |
| `getAllTemplates()` | 获取所有模板 |
| `getTemplate(String)` | 按名获取模板 |
| `addTemplate(String, String)` | 添加模板 |
| `removeTemplate(FileTemplate)` | 删除模板 |
| `getDefaultProperties()` | 获取默认属性（含日期格式化） |
| `getRecentNames()` | 获取最近使用的模板名 |
| `getInternalTemplates()` | 获取内置模板 |
| `getTestTemplates()` | 获取测试模板描述符列表 |
| `getInternalTemplate(String)` | 获取内置模板 |
| `findInternalTemplate(String)` | 查找内置模板 |
| `findCustomTestTemplate(String)` | 查找自定义测试模板 |
| `getCodeTemplate(String)` | 获取代码模板 |
| `getJ2eeTemplate(String)` | 获取 J2EE 模板 |
| `getDefaultTemplate(String)` | 获取默认模板 |
| `setTemplates(String, Collection)` | 设置模板 |
| `saveAllTemplates()` | 保存所有模板 |
| `getDefaultTemplateDescription()` | 获取默认模板描述 |
| `getDefaultIncludeDescription()` | 获取默认包含描述 |

**getTestTemplates() 逻辑**:
```
1. 获取所有模板
2. 过滤非默认模板
3. 对每个模板，从 TemplateRegistry 查找对应的 TemplateDescriptor
4. 返回描述符列表
```

### 4.11 UnitTemplateManager$1 — 项目级模板方案

**路径**: `com/aicode/template/fileloader/UnitTemplateManager$1`
**继承**: `FileTemplatesScheme`

**方法**:
| 方法 | 用途 |
|------|------|
| `getTemplatesDir()` | 返回项目级模板目录 |
| `getProject()` | 返回关联项目 |

### 4.12 TemplateResourceLoader — Velocity 资源加载器

**路径**: `com/aicode/template/fileloader/TemplateResourceLoader`
**继承**: `org.apache.velocity.runtime.resource.loader.ResourceLoader`

**方法**:
| 方法 | 用途 |
|------|------|
| `init(ExtendedProperties)` | 初始化（兼容旧版 Velocity） |
| `init(ExtProperties)` | 初始化（新版 Velocity） |
| `getResourceReader(String, String)` | 获取模板 Reader |
| `getResourceStream(String)` | 获取模板 InputStream |
| `isSourceModified(Resource)` | 检查模板是否修改 |
| `getLastModified(Resource)` | 获取最后修改时间 |

**资源加载逻辑**:
```
1. 从 UnitTemplateManager 获取模板名对应的 FileTemplate
2. 如果找到 → 返回模板内容的 InputStream
3. 如果未找到 → 抛出 ResourceNotFoundException
```

### 4.13 FileTemplateConfig — 模板配置

**路径**: `com/aicode/template/FileTemplateConfig`

**字段**:
```
static final int DEFAULT_MAX_RECURSION_DEPTH
int maxRecursionDepth                      // 最大递归深度
boolean reformatCode                       // 是否重新格式化代码
boolean replaceFqn                          // 是否替换全限定名
boolean optimizeImports                    // 是否优化导入
boolean stubMockMethodCallsReturnValues    // 是否 stub Mock 方法返回值
boolean ignoreUnusedProperties            // 是否忽略未使用属性
boolean replaceInterfaceParamsWithConcreteTypes  // 是否用具体类型替换接口参数
int maxNumOfConcreteCandidatesToReplaceInterfaceParam  // 替换接口参数的最大候选数
int minPercentOfExcessiveSettersToPreferMapCtor  // 偏好 Map 构造器的最小 setter 百分比
int minPercentOfInteractionWithPropertiesToTriggerConstructorOptimization  // 触发构造器优化的最小交互百分比
boolean generateTestsForInternalMethods    // 是否为内部方法生成测试
boolean renderInternalMethodCallStubs      // 是否渲染内部方法调用 stub
boolean throwSpecificExceptionTypes        // 是否抛出特定异常类型
```

**构造函数**:
```
FileTemplateConfig(boolean reformatCode, boolean optimizeImports, boolean replaceFqn)
  → 创建默认配置（maxRecursionDepth=3, 其余按参数）
```

### 4.14 CacheFileTemplate — 缓存文件模板

**路径**: `com/aicode/template/generator/CacheFileTemplate`

**字段**:
```
Map<String, Object> paramMaps                  // 模板参数映射
FileTemplateContext context                    // 模板上下文
PsiDirectory targetDirectory                  // 目标目录
GeneratorFileConfig generatorFileConfig       // 生成器文件配置
MethodGeneratorConfig methodGeneratorConfig    // 方法生成器配置
List&lt;MessageDto&gt; messageDtos                   // 消息列表
```

### 4.15 文件模板加载流程图

```
    ┌─────────────────────────────────────────────────────────────────┐
    │                    模板加载与注册流程                              │
    └─────────────────────────────────────────────────────────────────┘

    ┌───────────────────────┐
    │  UnitTemplateManager  │ ← 单例 (getInstance)
    │  .getInstance(project)│
    └───────────┬───────────┘
                │
                v
    ┌───────────────────────┐        ┌──────────────────────────────┐
    │  FileTemplatesLoader │        │     TemplateRegistry          │
    │  (包私有, 构造时加载) │        │  (静态初始化, 7 个预定义模板)  │
    └───────────┬───────────┘        └──────────┬───────────────────┘
                │                               │
                v                               │
    ┌───────────────────────┐                   │
    │  loadDefaultTemplates │                   │
    │  (从 classpath 加载)   │                   │
    └───────────┬───────────┘                   │
                │                               │
    ┌───────────┴───────────┐                   │
    │                       │                   │
    v                       v                   │
    ┌──────────────┐ ┌──────────────┐            │
    │  FTManager   │ │  FTManager   │            │
    │  (tests)     │ │  (includes)  │            │
    └──────┬───────┘ └──────────────┘            │
           │                                    │
           v                                    │
    ┌──────────────────────┐                    │
    │  createAndStore      │                    │
    │  BundledTemplate     │                    │
    │  (每个 DefaultTemplate│                    │
    │   创建 UnitFileTemplate)                   │
    └──────────┬───────────┘                    │
               │                                │
               v                                v
    ┌──────────────────────────────────────────────────────┐
    │              UnitFileTemplate                         │
    │  ├── name = "JUnit4&Mockito"                         │
    │  ├── displayName = "JUnit4 & Mockito"               │
    │  ├── isDefault = true                                │
    │  ├── extension = "java"                               │
    │  └── text = (从 .ft 文件加载)                          │
    └──────────────────────────────────────────────────────┘

    ══════════════════════════════════════════════════════════

    模板渲染流程:
    ─────────────

    ┌───────────────────┐
    │ FileTemplateContext│ ← 包含 project, srcClass, methods, config
    └────────┬──────────┘
             │
             v
    ┌───────────────────┐
    │  CacheFileTemplate│ ← 包含 paramMaps, context, config
    └────────┬──────────┘
             │
             v
    ┌───────────────────────────────────────────────┐
    │  VelocityInitializer + TemplateResourceLoader │
    │  → Velocity 引擎渲染模板                       │
    │  → 从 UnitTemplateManager 获取模板内容          │
    └────────┬──────────────────────────────────────┘
             │
             v
    ┌───────────────────┐
    │  生成的测试代码    │
    └───────────────────┘
```

### 4.16 模板注册表完整模板列表

| # | 显示名 | 文件名 | 框架 | Mock 框架 | 角色 |
|---|--------|--------|------|-----------|------|
| 1 | JUnit4 | JUnit4.java | JUnit4 | (无) | Tester |
| 2 | JUnit5 | JUnit5.java | JUnit5 | (无) | Tester |
| 3 | JUnit4 & Mockito | JUnit4&Mockito.java | JUnit4 | Mockito | Tester |
| 4 | JUnit4 & Powermock | JUnit4&Powermock.java | JUnit4 | Powermock | Tester |
| 5 | JUnit5 & Mockito | JUnit5&Mockito.java | JUnit5 | Mockito | Tester |
| 6 | TestNG & Mockito | TestNG&Mockito.java | TestNG | Mockito | Tester |
| 7 | SpringBootTest & Mockito | SpringBootTest&Mockito.java | SpringBootTest | Mockito | Tester |

---

## 5. 三个包的协作关系

```
    ┌──────────────────────────────────────────────────────────────────────┐
    │                        iFlyCode 插件架构                              │
    └──────────────────────────────────────────────────────────────────────┘

    ┌─────────────────┐     ┌──────────────────────┐     ┌──────────────────┐
    │   Domain 包     │     │   Updater 包          │     │  FileLoader 包   │
    │                 │     │                      │     │                  │
    │ Position ───────┼────→│ PluginUpdater        │     │ UnitTemplate     │
    │ Range    ───────┼────→│ (使用 Position/Range │     │ Manager          │
    │ LineInfo ───────┼─┐   │  定位更新通知位置)    │     │   ↓              │
    │ VirtualFileUri  │ │   │                      │     │ FTManager        │
    │   ↓            │ │   │ CheckService          │     │   ↓              │
    │ GetTipsResult  │ │   │ (定时检查更新)        │     │ TemplateRegistry │
    │   ↓            │ │   │                      │     │   ↓              │
    │ Suggestion     │ │   │ UpdaterChecker        │     │ FileTemplate     │
    │   ↓            │ │   │ (版本分发)            │     │ Context          │
    │ CommandCache   │ │   │                      │     │   ↓              │
    │                 │ │   └──────────────────────┘     │ CacheFile        │
    └─────────────────┘ │                                │ Template         │
                        │                                └──────────────────┘
                        │
                        v
    ┌─────────────────────────────────────────────────────────────────────┐
    │                     跨包 DTO 协作                                    │
    │                                                                     │
    │  CodeInfoDto ←── PresentationDataDto (行内聊天代码定位)              │
    │  CommentContext ←── CommentInfo (函数注释生成)                      │
    │  FunctionModelInfo ←── CodeModel (功能权限+模型选择)                │
    │  UnitTestDto ←── FunctionDataDto ←── TemplateAttr (单测生成)        │
    │  SqlInfoDto (SQL 智能提示)                                          │
    │  ChangeInfoDto (代码变更追踪)                                        │
    └─────────────────────────────────────────────────────────────────────┘
```

---

## 6. 关键发现与安全分析

### 6.1 更新系统安全特征

1. **反射绕过 API 兼容性**: 两个 `UpdaterChecker` 均通过反射调用 IntelliJ 内部 API，规避了版本兼容性限制，但也绕过了 IntelliJ 的插件 API 稳定性检查。

2. **动态插件禁用/启用**: `disableOrEnablePlugin` 通过反射调用 `PluginEnabler`（内部类），实现了不重启 IDE 即可禁用/启用插件的能力。

3. **重启安装脚本**: `installAfterRestart` 使用 `StartupActionScriptManager` 注册 Delete/Copy/Unzip 命令，在 IDE 重启时执行文件操作，这是 IntelliJ 官方的插件安装机制。

4. **无 MD5 客户端校验**: 虽然登录响应包含 `md5` 字段，但 `doUpdate` 方法中未发现客户端 MD5 校验逻辑，仅通过文件大小比较判断是否需要重新复制。

5. **SaaS 场景跳过更新**: `checkUpdate` 方法在 `PluginSceneEnum.saasScene()` 为 true 时直接返回，说明 SaaS 部署模式有独立的更新机制。

### 6.2 领域模型设计特征

1. **LSP 风格坐标系统**: `Position` 和 `Range` 采用 LSP (Language Server Protocol) 风格的 line/character 坐标，便于与服务端 AI 引擎交互。

2. **不可变值对象**: `LineInfo`、`Suggestion`、`GetTipsResult$Tip` 均为不可变类（final 字段），符合函数式编程范式。

3. **Gson 序列化集成**: `VirtualFileUri$TypeAdapter` 实现了自定义 Gson 序列化，确保文件 URI 在 JSON 通信中的正确表示。

4. **深层嵌套 DTO**: `UnitTestDto` 包含 4 层嵌套（Dto → DataDTO → FunctionDataDTO → TemplateAttr/Data/CodeList），反映了单测生成流程的复杂性。

### 6.3 模板系统设计特征

1. **双管理器架构**: `FTManager` 负责文件系统层面的模板 CRUD，`UnitTemplateManager` 负责业务层面的模板查询和渲染。

2. **7 种预定义模板**: 覆盖了 Java 生态主流测试框架组合（JUnit4/5 + Mockito/Powermock，TestNG + Mockito，SpringBootTest + Mockito）。

3. **Velocity 引擎集成**: `TemplateResourceLoader` 将 IntelliJ 的 `FileTemplate` 适配为 Velocity 的 `ResourceLoader`，实现了模板引擎的无缝集成。

4. **项目级模板方案**: `UnitTemplateManager$1` 实现了项目级模板目录，允许不同项目使用不同的自定义模板。