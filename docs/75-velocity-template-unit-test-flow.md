# iFlyCode Velocity 模板体系与单测生成完整流程分析

> 版本: iFlyCode 3.4.2-222 | 分析日期: 2026-05-13

## 1. 模板文件体系总览

### 1.1 目录结构

```
fileTemplates/
  velocity.properties                        # Velocity 引擎配置
  unitTests/                                 # 单测模板（7 套）
    JUnit4.java.ft                           # JUnit4 纯净版
    JUnit5.java.ft                           # JUnit5 纯净版
    JUnit4&Mockito.java.ft                   # JUnit4 + Mockito
    JUnit5&Mockito.java.ft                   # JUnit5 + Mockito
    JUnit4&Powermock.java.ft                 # JUnit4 + PowerMock
    TestNG&Mockito.java.ft                   # TestNG + Mockito
    SpringBootTest&Mockito.java.ft           # SpringBootTest + Mockito
    back                                     # 备份目录（空）
  unitIncludes/                              # 公共宏模板
    IflyCode common macros.java.ft           # 基础宏定义
    IflyCode macros.java.ft                  # 扩展宏定义（含 PowerMock/TestNG）
    default.html                             # 模板变量说明 HTML
```

Agent 端存在完全相同的目录结构和模板文件（`agent_contents/agent/fileTemplates/`），两者内容逐字节一致。

### 1.2 Velocity 引擎配置

```properties
file.resource.loader.path = ./unitIncludes/IflyCode macros.java.ft
```

引擎启动时通过 `VelocityInitializer` 获取 `com.intellij.ide.fileTemplates.VelocityWrapper` 内部的 `RuntimeInstance`，注册自定义 `TemplateResourceLoader` 作为资源加载器。

---

## 2. 七套单测模板详解

### 2.1 模板特性矩阵

| 模板文件 | 测试框架 | Mock 框架 | 注解风格 | setUp 注解 | 类注解 | 断言 API | 异常断言 |
|---|---|---|---|---|---|---|---|
| JUnit4.java.ft | JUnit 4 | 无 | @Test | @Before | 无 | org.junit.Assert | @Test(expected=) |
| JUnit5.java.ft | JUnit 5 | 无 | @Test | @BeforeEach | 无 | Assertions | Assertions.assertThrows |
| JUnit4&Mockito.java.ft | JUnit 4 | Mockito | @Test | @Before | @RunWith(MockitoJUnitRunner) | Assert | @Test(expected=) |
| JUnit5&Mockito.java.ft | JUnit 5 | Mockito | @Test | @BeforeEach | 无 | Assertions | Assertions.assertThrows |
| JUnit4&Powermock.java.ft | JUnit 4 | PowerMock | @Test | @Before | @RunWith(PowerMockRunner) | Assert | @Test(expected=) |
| TestNG&Mockito.java.ft | TestNG | Mockito | @Test | @BeforeMethod | 无 | Assert.assertEquals | 无 |
| SpringBootTest&Mockito.java.ft | JUnit 5 | Mockito+Spring | @Test | @BeforeEach | @SpringBootTest | Assertions | Assertions.assertThrows |

### 2.2 模板结构分层

每个模板文件由以下层次组成：

```
[1] VTL 变量声明 (@vtlvariable)          -- IDE 类型提示
[2] 全局变量初始化 ($trackedTestMethodsCount, $replacementTypes, $mockBuilder, $defaultTypeValues)
[3] 宏定义区 (renderTestMethodName, renderMethodCall, renderMockStubs, ...)
[4] 模板主体 (package, import, class, fields, setUp, test methods)
```

### 2.3 模板注册表 (TemplateRegistry)

`TemplateRegistry` 在静态初始化块中注册 7 个 `TemplateDescriptor`：

| 常量名 | displayName | framework | mockFramework | filename | role |
|---|---|---|---|---|---|
| JUNIT4_JAVA_TEMPLATE | JUnit4 | JUnit4 | — | JUnit4.java | Tester |
| JUNIT5_JAVA_TEMPLATE | JUnit5 | JUnit5 | — | JUnit5.java | Tester |
| JUNIT4_MOCKITO_JAVA_TEMPLATE | JUnit4 & Mockito | JUnit4 | Mockito | JUnit4&Mockito.java | Tester |
| JUNIT4_POWERMOCK_JAVA_TEMPLATE | JUnit4 & Powermock | JUnit4 | Powermock | JUnit4&Powermock.java | Tester |
| JUNIT5_MOCKITO_JAVA_TEMPLATE | JUnit5 & Mockito | JUnit5 | Mockito | JUnit5&Mockito.java | Tester |
| TESTNG_MOCKITO_JAVA_TEMPLATE | TestNG & Mockito | TestNG | Mockito | TestNG&Mockito.java | Tester |
| SPRINGBOOTTEST_MOCKITO_JAVA_TEMPLATE | SpringBootTest & Mockito | SpringBootTest | Mockito | SpringBootTest&Mockito.java | Tester |

模板文件后缀统一为 `.ft`，语言标识为 `java`。

---

## 3. 模板变量映射表

### 3.1 TestTemplateParams 定义的 Velocity 变量

| 变量名 | Java 类型 | 来源/含义 |
|---|---|---|
| `$TESTED_CLASS` | com.aicode.template.context.domain.Type | 被测类的完整类型信息（方法、字段、父类等） |
| `$PACKAGE_NAME` | String | 测试类包名 |
| `$CLASS_NAME` | String | 测试类名 |
| `$TestBuilder` | TestBuilder | 测试代码生成器（渲染参数、断言、返回值等） |
| `$StringUtils` | StringUtils | 字符串工具（首字母大写/小写、驼峰转换等） |
| `$MockitoMockBuilder` | MockitoMockBuilder | Mockito Mock 构建器 |
| `$PowerMockBuilder` | PowerMockBuilder | PowerMock 构建器 |
| `$TestSubjectUtils` | TestSubjectInspector | 被测类检查工具（是否应测试、是否有 Mock 等） |
| `$JAVA_VERSION` | JavaVersion | 项目 Java 版本 |
| `$TestedClasspathJars` | `List&lt;String&gt;` | 被测类 classpath JAR 列表 |
| `$MAX_RECURSION_DEPTH` | int | 对象图递归深度上限 |
| `$MONTH_NAME_EN` | String | 当前月份英文名（大写用于 Calendar 常量） |
| `$DAY_NUMERIC` | int | 当前日 |
| `$HOUR_NUMERIC` | int | 当前小时 |
| `$MINUTE_NUMERIC` | int | 当前分钟 |
| `$SECOND_NUMERIC` | int | 当前秒 |
| `$TESTED_CLASS_LANGUAGE` | String | 被测类语言（固定 "java"） |
| `$HAS_TEST_METHODS` | Boolean | 是否已有测试方法 |

### 3.2 模板内部变量

| 变量名 | 类型 | 用途 |
|---|---|---|
| `$trackedTestMethodsCount` | Map<String,Integer> | 跟踪同名测试方法计数（用于后缀编号） |
| `$replacementTypes` | Map<String,String> | 自定义类型替换映射（入参） |
| `$replacementTypesForReturn` | Map<String,String> | 自定义类型替换映射（返回值） |
| `$defaultTypeValues` | Map<String,String> | 60+ 种 Java 类型的默认值初始化表达式 |
| `$mockBuilder` | MockBuilder | 当前 Mock 构建器实例（Mockito 或 PowerMock） |
| `$hasMocks` | Boolean | 被测类是否有可 Mock 的依赖 |
| `$hasTestableInstanceMethod` | Boolean | 被测类是否有可测试的实例方法 |

### 3.3 defaultTypeValues 完整映射

模板内置 60+ 种 Java 类型的默认初始化值，按类别分组：

**原始类型：**
`byte`->`(byte) 0`, `short`->`(short) 0`, `int`->`0`, `long`->`0L`, `float`->`0f`, `double`->`0d`, `char`->`'a'`, `boolean`->`true`

**包装类型：**
`Byte`->`Byte.valueOf("00110")`, `Short`->`Short.valueOf((short)0)`, `Integer`->`Integer.valueOf(0)`, `Long`->`Long.valueOf(1)`, `Float`->`Float.valueOf(1.1f)`, `Double`->`Double.valueOf(0)`, `Character`->`Character.valueOf('a')`, `Boolean`->`Boolean.TRUE`

**特殊接口：**
`Serializable`->`Long.valueOf(1)`, `Comparable`->`Integer.valueOf(1)`, `Number`->`Integer.valueOf(0)`, `Runnable`->`()->&#123;&#125;`, `UUID`->`UUID.randomUUID()`, `Class`->`$TESTED_CLASS.canonicalName.class`

**I/O 类型（18 种）：** InputStream/OutputStream 全家族、Reader/Writer 全家族，均使用内存流实现

**时间类型：**
`Date`->`GregorianCalendar($YEAR,...)`, `LocalDate`->`LocalDate.of(...)`, `LocalDateTime`->`LocalDateTime.of(...)`, `LocalTime`->`LocalTime.of(...)`, `Instant`->`LocalDateTime.of(...).toInstant(UTC)`

**业务特定类型：**
`com.bocom.jump.bp.core.*` 系列（交银康联业务框架）、`RedisTemplate`、`ThreadPoolExecutor`、`BigDecimal`

---

## 4. 宏定义体系

### 4.1 IflyCode common macros.java.ft（基础宏）

| 宏名 | 参数 | 功能 |
|---|---|---|
| `renderTestMethodName` | $methodName | 生成测试方法名：`test` + 首字母大写 + 后缀 |
| `renderTestCaseMethodName` | $caseMethodName, $methodName | 生成用例方法名（支持自定义用例名） |
| `renderTestMethodNameAsWords` | $methodName | 生成词分隔的测试方法名 |
| `testMethodSuffix` | $methodName, $prefix | 重名方法后缀编号（_2, _3...） |
| `renderJavaReturnVar` | $type | 渲染返回值变量声明 |

### 4.2 IflyCode macros.java.ft（扩展宏）

在基础宏之上增加：

| 宏名 | 参数 | 功能 |
|---|---|---|
| `renderTestSubjectInit` | $testedClass, $hasTestableInstanceMethod, $hasMocks | 被测对象初始化（@InjectMocks / new） |
| `renderMockedFields` | $hasMocks, $testedClass | 渲染 @Mock 字段 |
| `renderMethodCall` | $method, $testedClassName | 渲染方法调用（含反射调用私有方法） |
| `renderMethodCallThird` | $method, $caseResult, $testedClassName | 渲染带用例数据的方法调用 |
| `renderMethodCallWithSpy` | $method, $testedClassName | 渲染 Spy 方式的方法调用 |
| `renderMockStubs` | $method, $testedClass | 渲染 when().thenReturn() Mock 桩 |
| `renderMockStubWithData` | $method, $testedClass, $caseResult | 渲染带数据的 Mock 桩 |
| `renderMockVerifies` | $method, $testedClass | 渲染 verify() 验证 |
| `renderMockitoStaticMockStubs` | $method, $testedClass, $caseResult | 渲染 Mockito.mockStatic() 静态 Mock |
| `renderInternalMethodCallsStubs` | $method, $testedClass | 渲染内部方法调用的 PowerMock Spy 桩 |
| `renderFieldValueMockito` | $field, $testedClassName | 渲染 Spring @Value 字段注入 |
| `renderMockVariables` | $method, $resolveVariables, $caseResult | 渲染解析变量数据 |
| `resetRenderVariable` | $method, $caseResult | 重置变量状态 |
| `renderCaseBranches` | $method, $caseResult | 渲染分支条件代码 |
| `renderJUnitAssert` / `renderJUnitAssertMockito` | $method, $caseResult | 渲染断言语句 |
| `renderExceptionMessage` | $exceptionMessage | 渲染异常消息断言（JUnit5） |
| `setClassWithNoMock` | $method | 无 Mock 时直接 new 被测类 |

---

## 5. 双模式单测生成

### 5.1 GenaratebyTemplateSwitchEnum

该枚举控制模板模式与 AI 模式的切换。从常量池分析，枚举值名称被混淆保护，但通过 `getType()` 方法获取类型标识，结合 `GenericUtils` 和 `FileService` 的引用推断存在至少两个枚举值：

- **TEMPLATE** — 模板模式（快速生成，基于 Velocity 模板）
- **AI** — AI 模式（精准生成，调用大模型）

### 5.2 GeneratorProcess 枚举（生成流程状态）

| 枚举值 | 中文描述 | 含义 |
|---|---|---|
| INIT | 初始化完成 | 加载配置、解析被测类 |
| ANALYSIS | 解析完成 | AST 分析、分支解析 |
| REQUEST_AI | 请求AI模型 | 调用 AI 服务生成用例数据 |
| GENERATING | 生成单测中... | Velocity 模板渲染 |
| GENERATED | 生成完成 | 文件写入完成 |
| OVER | 生成结束 | 流程结束 |

### 5.3 双模式流程对比

```
┌─────────────────────────────────────────────────────────────────┐
│                    用户触发单测生成                               │
│              (右键菜单 / 快捷键 / 批量操作)                        │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────────────────────────┐
│  GeneratorProcess.INIT                                          │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │ 1. 解析 PsiClass → Type (被测类元模型)                     │   │
│  │ 2. 构建 FileTemplateContext                              │   │
│  │    - project, srcClass, targetDirectory                  │   │
│  │    - fileTemplateConfig (递归深度、格式化等)                │   │
│  │    - excludeMethodList (排除方法)                         │   │
│  │    - requestAi (是否请求AI)                               │   │
│  │ 3. 选择 TemplateDescriptor (framework + mockFramework)     │   │
│  └──────────────────────────────────────────────────────────┘   │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────────────────────────┐
│  GeneratorProcess.ANALYSIS                                      │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │ TestSubjectInspector 分析:                               │   │
│  │   - 遍历 $TESTED_CLASS.methods                          │   │
│  │   - shouldBeTested() 过滤 (排除 abstract/native/getter等) │   │
│  │   - 解析方法分支 (if/else/switch → CaseResult)            │   │
│  │   - 识别 Mock 依赖 (MockitoMockBuilder.hasMocks)         │   │
│  │   - 识别静态方法调用 (mockStaticClass)                    │   │
│  │   - 识别内部方法调用 (directMethodCalls)                  │   │
│  └──────────────────────────────────────────────────────────┘   │
└──────────────────────┬──────────────────────────────────────────┘
                       │
              ┌────────┴────────┐
              │                 │
     requestAi=false    requestAi=true
     (模板模式)          (AI 模式)
              │                 │
              ▼                 ▼
┌──────────────────┐ ┌──────────────────────────────────────────┐
│ 模板模式(快速)    │ │ AI 模式(精准)                             │
│                  │ │                                          │
│ 直接使用模板     │ │ GeneratorProcess.REQUEST_AI              │
│ 内置的           │ │ ┌────────────────────────────────────┐   │
│ defaultTypeValues│ │ │ TemplateRequestService              │   │
│ 生成默认值       │ │ │   → 构建 TemplateTestPromptDto      │   │
│                  │ │ │     - testFrame: "JUnit5"          │   │
│ CaseResult       │ │ │     - mockFrame: "Mockito"         │   │
│ 仅含空默认用例   │ │ │     - testContent: 被测方法源码     │   │
│                  │ │ │     - testCaseNumber: 期望用例数    │   │
│                  │ │ │     - branchList: 分支条件列表      │   │
│                  │ │ │   → 调用 AI 服务                    │   │
│                  │ │ │   → 返回 CaseResult[]               │   │
│                  │ │ │     - 每个用例含: 入参数据、         │   │
│                  │ │ │       Mock数据、期望输出、异常        │   │
│                  │ │ └────────────────────────────────────┘   │
│                  │ │                                          │
│                  │ │ JavaTestBuilderImpl 转换:                │
│                  │ │   AI JSON → Velocity CaseResult 对象     │
│                  │ │   - renderJavaCallParams()               │
│                  │ │   - renderSetMethod()                    │
│                  │ │   - 智能推断常用字段名                   │
│                  │ │     (count→int, status→boolean, etc.)   │
│                  │ └──────────────────────────────────────────┘
              │                 │
              └────────┬────────┘
                       │
                       ▼
┌──────────────────────────────────────────────────────────────────┐
│  GeneratorProcess.GENERATING                                    │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │ Velocity 模板渲染:                                       │   │
│  │                                                          │   │
│  │ 1. VelocityInitializer.getRuntimeInstance()               │   │
│  │    → 获取 Velocity RuntimeInstance                       │   │
│  │                                                          │   │
│  │ 2. TemplateResourceLoader 加载模板                       │   │
│  │    → UnitTemplateManager.getAllPatterns()                │   │
│  │    → 按 name+extension 匹配 FileTemplate                │   │
│  │    → 返回模板文本 (getText)                              │   │
│  │                                                          │   │
│  │ 3. 填充 Velocity 上下文 (TestTemplateContextBuilder):     │   │
│  │    - $TESTED_CLASS → Type 对象                           │   │
│  │    - $TestBuilder → TestBuilderImpl 实例                 │   │
│  │    - $MockitoMockBuilder → MockitoMockBuilder 实例       │   │
│  │    - $TestSubjectUtils → TestSubjectInspector 实例       │   │
│  │    - $StringUtils → StringUtils 实例                     │   │
│  │    - 时间变量 ($YEAR, $MONTH_NAME_EN, ...)               │   │
│  │    - $JAVA_VERSION, $MAX_RECURSION_DEPTH                 │   │
│  │                                                          │   │
│  │ 4. Velocity.evaluate(context, template)                  │   │
│  │    → 生成 Java 测试代码字符串                            │   │
│  └──────────────────────────────────────────────────────────┘   │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────────────────────────┐
│  GeneratorProcess.GENERATED                                     │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │ CreateTestFileTask / CreateTestMethodTask:               │   │
│  │                                                          │   │
│  │ 1. 确定目标目录 (TargetDirectoryLocator)                  │   │
│  │ 2. 解析类名冲突 (GeneratedClassNameResolver)              │   │
│  │ 3. 写入测试文件 (writeTestClass / writeTestFile)          │   │
│  │    → ApplicationManager.getApplication()                 │   │
│  │      .invokeLater(Computable&lt;PsiElement&gt;)                │   │
│  │ 4. 后处理:                                               │   │
│  │    - 代码格式化 (isReformatCode)                         │   │
│  │    - 优化 import (isOptimizeImports)                     │   │
│  │    - 替换 FQN (isReplaceFqn)                             │   │
│  │    - 编译错误修复 (ProcessErrorFileAnalyzer)              │   │
│  └──────────────────────────────────────────────────────────┘   │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────────────────────────┐
│  GeneratorProcess.OVER                                         │
│  通知 UI 更新，刷新项目视图                                     │
└──────────────────────────────────────────────────────────────────┘
```

---

## 6. 核心 Java 类分析

### 6.1 VelocityInitializer

- **功能**: 桥接 IntelliJ 的 `VelocityWrapper`，获取其内部的 `RuntimeInstance`
- **关键方法**:
  - `verifyRuntimeSetup()` — 验证 Velocity 运行时
  - `getRuntimeInstance()` — 通过反射获取 `com.intellij.ide.fileTemplates.VelocityWrapper` 的 `ri` 字段
- **反射路径**: `Class.forName("com.intellij.ide.fileTemplates.VelocityWrapper")` → `getDeclaredField("ri")` → `ReflectUtil.getField()`

### 6.2 TemplateResourceLoader

- **继承**: `org.apache.velocity.runtime.resource.loader.ResourceLoader`
- **功能**: 自定义 Velocity 资源加载器，从 `UnitTemplateManager` 获取模板文本
- **关键方法**:
  - `init(ExtendedProperties)` — 初始化（空实现）
  - `getResourceStream(source)` — 按 source 名匹配 `FileTemplate`，返回 `ByteArrayInputStream(template.getText().getBytes())`
  - `getResourceReader(source, encoding)` — 同上，返回 `StringReader`
  - `isSourceModified(resource)` — 始终返回 true
  - `getLastModified(resource)` — 始终返回 0

### 6.3 UnitTemplateManager

- **功能**: 管理所有单元测试 `FileTemplate` 实例
- **关键方法**:
  - `getDefaultInstance()` — 获取单例
  - `getAllPatterns()` — 返回所有 `FileTemplate[]`

### 6.4 FileTemplateContext

- **字段**:
  - `fileTemplateDescriptor` — IntelliJ 文件模板描述符
  - `project` — 当前项目
  - `targetClass` — 目标类名
  - `targetPackage` — 目标包
  - `srcModule` / `testModule` — 源/测试模块
  - `targetDirectory` — 目标目录
  - `srcClass` — 源 PsiClass
  - `fileTemplateConfig` — 模板配置
  - `excludeMethodList` — 排除方法列表
  - `selectedMethods` — 选中的方法集合
  - `requestAi` — 是否请求 AI

### 6.5 FileTemplateConfig

- **配置项**:
  - `maxRecursionDepth` (默认 3) — 对象图递归深度
  - `reformatCode` — 是否格式化代码
  - `replaceFqn` — 是否替换全限定名
  - `optimizeImports` — 是否优化 import
  - `stubMockMethodCallsReturnValues` — 是否生成 Mock 返回值桩
  - `ignoreUnusedProperties` — 是否忽略未使用属性
  - `replaceInterfaceParamsWithConcreteTypes` — 是否用具体类型替换接口参数
  - `maxNumOfConcreteCandidatesToReplaceInterfaceParam` — 替换候选数上限
  - `minPercentOfExcessiveSettersToPreferMapCtor` — Map 构造器偏好阈值
  - `minPercentOfInteractionWithPropertiesToTriggerConstructorOptimization` — 构造器优化阈值
  - `generateTestsForInternalMethods` — 是否为内部方法生成测试
  - `renderInternalMethodCallStubs` — 是否渲染内部方法调用桩
  - `throwSpecificExceptionTypes` — 是否抛出特定异常类型

### 6.6 JavaTestBuilderImpl

- **继承**: `LangTestBuilder`（抽象基类）
- **功能**: Java 语言测试代码生成器，负责将 AI 返回的 JSON 用例数据转换为 Velocity 可消费的 CaseResult 对象
- **关键能力**:
  - `renderJavaCallParams()` — 渲染方法调用参数
  - `renderSetMethod()` — 渲染 setter 方法调用
  - 智能字段名推断：`count/delete/save/status/num/update` → `Integer`，`score/duration` → `Double`，`pageSize/limit/page` → `Integer`，`deleted/isdeleted` → `Boolean`
  - 时间类型特殊处理：`java.util.Date` → `SimpleDateFormat("yyyy-MM-dd")`，`java.time.*` → 对应的 `.of()` 方法
  - JSON 数据解析：使用 `cn.hutool.json.JSONObject/JSONArray`

### 6.7 TemplateRequestService

- **功能**: AI 辅助单测请求服务
- **输入**: `TemplateTestPromptDto`（包含 `stream`、`content`、`unitTest`）
- **`TemplateTestDto` 字段**:
  - `testFrame` — 测试框架名
  - `mockFrame` — Mock 框架名
  - `testContent` — 被测方法源码
  - `testCaseNumber` — 期望用例数
  - `branchList` — 分支条件列表

### 6.8 CreateTestFileTask / CreateTestMethodTask

- **CreateTestFileTask**: 创建整个测试文件（WriteAction 中执行）
- **CreateTestMethodTask**: 创建单个测试方法
- **关键流程**:
  1. 确定文件路径（`targetDirectory.getVirtualFile().getCanonicalPath()` + separator + filename）
  2. 检查文件是否已存在，若存在则删除
  3. 调用 `writeTestFile(fileTemplateManager, templateName, context, paramMaps, targetDirectory)`
  4. 在 `ApplicationManager.getApplication().invokeLater()` 中执行 PsiElement 写入

### 6.9 GeneratorTemplateConfig

- **字段**:
  - `psiPackage`, `srcClass`, `testModule`, `srcModule` — PSI 元素
  - `testFramework` (UnitTestBaseEnum) — 测试框架枚举
  - `mockFramework` (UnitTestMockEnum) — Mock 框架枚举
  - `testPrivate` — 是否测试私有方法
  - `requestAi` — 是否请求 AI
  - `duplicateRule` (DuplicateRule) — 重名规则
  - `psiFile` — 源文件
  - `testMethods` — 选中的测试方法集合
  - `targetDirectory` — 目标目录路径
  - `unitTestDto` (UnitTestDto.DataDTO) — 单测 DTO
  - `testClassAbsolutePath` — 测试类绝对路径
  - `methodUt` — 是否方法级单测

---

## 7. Agent 端模板对比

### 7.1 文件级对比

Agent 端模板文件与 IDE 端**完全一致**（逐字节相同），包括：

- `velocity.properties` — 相同
- 7 个 `unitTests/*.java.ft` — 相同
- `unitIncludes/IflyCode common macros.java.ft` — 相同
- `unitIncludes/IflyCode macros.java.ft` — 相同

### 7.2 Agent 端模板加载机制差异

| 维度 | IDE 端 | Agent 端 |
|---|---|---|
| 加载器 | `TemplateResourceLoader` → `UnitTemplateManager.getDefaultInstance().getAllPatterns()` | Agent 进程内独立加载，路径 `agent/fileTemplates/` |
| Velocity 初始化 | `VelocityInitializer` 反射获取 `VelocityWrapper.ri` | Agent 端直接初始化 `RuntimeInstance` |
| 上下文构建 | `TestTemplateContextBuilder` + PSI 元素 | Agent 端使用纯文本解析（无 PSI） |
| Mock 构建 | `MockitoMockBuilder` / `PowerMockBuilder` 基于 PSI 类型系统 | Agent 端基于反射/类路径扫描 |
| 文件写入 | `CreateTestFileTask` → IntelliJ WriteAction | Agent 端直接文件 I/O |

### 7.3 Agent 端特有组件

- `agent.zip` — Agent 独立打包
- Agent 进程通过 WebSocket 与 IDE 通信
- `CodeCompleteService` — Agent 端代码补全服务（被 `TestGenerationProcess` 引用）

---

## 8. 完整单测生成流程（从用户触发到文件创建）

### 8.1 触发入口

1. **右键菜单**: `BatchUnitTestTemplateService` → `createUnitTestDialog()`
2. **快捷键**: 通过 `AnAction` 子类触发
3. **批量模式**: `BatchUnitTestTemplateService.recursion()` 递归扫描文件树

### 8.2 完整时序

```
[用户操作]
    │
    ▼
[1] Action 触发 → 获取当前 PsiClass / PsiFile
    │
    ▼
[2] 弹出 BatchUnitTestDialog
    │  - 选择测试框架 (UnitTestBaseEnum: JUnit4/JUnit5/TestNG/SpringBootTest)
    │  - 选择 Mock 框架 (UnitTestMockEnum: Mockito/PowerMock)
    │  - 选择被测方法 (selectedMethods)
    │  - 选择生成模式 (GenaratebyTemplateSwitchEnum: TEMPLATE/AI)
    │  - 配置 FileTemplateConfig
    │
    ▼
[3] 构建 GeneratorTemplateConfig
    │  - psiPackage, srcClass, testModule, srcModule
    │  - testFramework, mockFramework
    │  - testPrivate, requestAi, duplicateRule
    │  - testMethods, targetDirectory
    │
    ▼
[4] INIT 阶段
    │  - 解析 PsiClass → Type 元模型
    │  - 构建 FileTemplateContext
    │  - TemplateRegistry.getEnabledTemplateDescriptor(framework, mockFramework)
    │    → 匹配 TemplateDescriptor (filename + ".ft")
    │
    ▼
[5] ANALYSIS 阶段
    │  - TestSubjectInspector 分析被测类
    │  - ExcludeMethodEnum 过滤: ABSTRACT, NATIVE, GETTER, SETTER, MAIN, EQUALS, TOSTRING, HASHCODE
    │  - 解析方法分支 → ResolvedBranch → CaseResult
    │  - 识别 Mock 依赖 → hasMocks
    │  - 识别静态方法 → staticMethodCalls
    │  - 识别内部方法调用 → directMethodCalls
    │
    ▼
[6] 分支: requestAi?
    │
    ├─ [TEMPLATE 模式]
    │   │  CaseResult 使用默认值 (defaultTypeValues)
    │   │  无 AI 请求，直接进入渲染
    │   │
    │   ▼
    │
    └─ [AI 模式]
        │  REQUEST_AI 阶段
        │  - 构建 TemplateTestPromptDto
        │    &#123; stream: true,
        │      content: "...",
        │      unitTest: &#123; testFrame, mockFrame, testContent, testCaseNumber, branchList &#125;
        │    &#125;
        │  - TemplateRequestService 发送请求
        │  - AI 返回 JSON 用例数据
        │  - JavaTestBuilderImpl 转换:
        │    JSONObject → CaseParam(name, type, canonicalName, data)
        │    → CaseResult(exception, exceptionMessage, caseMethodName, resolveComponents)
        │
        ▼
[7] GENERATING 阶段
    │  - VelocityInitializer.getRuntimeInstance()
    │  - 构建 VelocityContext:
    │    put("TESTED_CLASS", type)
    │    put("PACKAGE_NAME", packageName)
    │    put("CLASS_NAME", className)
    │    put("TestBuilder", testBuilderImpl)
    │    put("StringUtils", stringUtils)
    │    put("MockitoMockBuilder", mockitoMockBuilder)
    │    put("PowerMockBuilder", powerMockBuilder)
    │    put("TestSubjectUtils", testSubjectInspector)
    │    put("JAVA_VERSION", javaVersion)
    │    put("MAX_RECURSION_DEPTH", maxRecursionDepth)
    │    put("MONTH_NAME_EN", monthNameEn)
    │    put("DAY_NUMERIC", dayNumeric)
    │    ... (时间变量)
    │  - TemplateResourceLoader 加载模板文本
    │  - Velocity.evaluate(context, templateName, templateText)
    │  → 生成 Java 测试代码字符串
    │
    ▼
[8] GENERATED 阶段
    │  - CreateTestFileTask 执行:
    │    a. TargetDirectoryLocator 定位测试目录
    │    b. GeneratedClassNameResolver 解决类名冲突
    │    c. WriteAction:
    │       - 确定文件路径
    │       - 若文件已存在 → 根据 duplicateRule 处理
    │       - writeTestFile() 写入 PsiElement
    │    d. 后处理:
    │       - reformatCode → CodeStyleManager.reformat()
    │       - optimizeImports → JavaCodeStyleManager.optimizeImports()
    │       - replaceFqn → 替换全限定名为简单名 + import
    │       - ProcessErrorFileAnalyzer 修复编译错误
    │         (移除无法解析的 import、修复类型引用)
    │
    ▼
[9] OVER 阶段
    │  - 通知 UI 更新
    │  - 刷新项目视图
    │  - 记录生成统计 (MethodRequestResult: requestCount, beginTime, endTime)
    │
    ▼
[完成] 测试文件已创建，用户可编辑/运行
```

---

## 9. 关键设计模式总结

### 9.1 策略模式 — Mock 框架切换

`$mockBuilder` 变量在模板中统一使用，但实际实例根据模板类型不同：
- JUnit4&Mockito / JUnit5&Mockito / SpringBootTest&Mockito → `$MockitoMockBuilder`
- JUnit4&Powermock → `$PowerMockBuilder`（`#set($mockBuilder = $PowerMockBuilder)`）

### 9.2 模板方法模式 — 测试方法生成

每个模板的测试方法生成遵循统一骨架：
```
@Test
public void testXxx() throws Exception &#123;
    // 1. resetRenderVariable — 重置变量状态
    // 2. renderCaseBranches — 渲染分支条件
    // 3. setClassWithNoMock — 无Mock时初始化被测对象
    // 4. renderMockVariables(mockData) — Mock数据
    // 5. renderMockitoStaticMockStubs — 静态Mock
    // 6. renderMockStubs / renderMockStubWithData — Mock桩
    // 7. renderMockVariables(input) — 入参数据
    // 8. renderMethodCall / renderMethodCallThird — 方法调用
    // 9. renderMockVerifies — Mock验证
    // 10. renderMockVariables(output) — 断言数据
    // 11. renderJUnitAssert / renderJUnitAssertMockito — 断言
&#125;
```

### 9.3 双重分发 — 用例数据注入

- **无 AI 时**: `renderMethodParams()` 使用 `defaultTypeValues` 生成默认值
- **有 AI 时**: `renderMethodParamsWithCase()` 使用 `CaseResult` 中的具体数据

### 9.4 桥接模式 — IntelliJ PSI 与 Velocity 模板

`Type` / `Method` / `Field` / `Param` 等领域对象桥接了 IntelliJ PSI 元素与 Velocity 模板变量，使模板可以访问 `$method.name`、`$field.type.canonicalName` 等属性而无需直接操作 PSI。
