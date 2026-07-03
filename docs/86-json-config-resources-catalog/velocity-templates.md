## 3. Velocity 模板文件

### 3.1 目录结构

```
fileTemplates/
  velocity.properties
  unitIncludes/
    default.html
    IflyCode common macros.java.ft
    IflyCode macros.java.ft
  unitTests/
    JUnit4&Mockito.java.ft
    JUnit4&Powermock.java.ft
    JUnit4.java.ft
    JUnit5&Mockito.java.ft
    JUnit5.java.ft
    SpringBootTest&Mockito.java.ft
    TestNG&Mockito.java.ft
    back
```

### 3.2 velocity.properties

```properties
file.resource.loader.path = ./unitIncludes/IflyCode macros.java.ft
```

配置 Velocity 模板引擎的资源加载路径，指向公共宏文件。

### 3.3 unitIncludes/ 模板

#### 3.3.1 default.html

模板变量说明文档，列出所有预定义变量：

| 变量名 | 类型 | 说明 |
|--------|------|------|
| `$&#123;CLASS_NAME&#125;` | String | 被测试类名 |
| `$&#123;PACKAGE_NAME&#125;` | String | 被测试类包名 |
| `$&#123;TESTED_CLASS_LANGUAGE&#125;` | String | 被测试类语言 |
| `$&#123;MAX_RECURSION_DEPTH&#125;` | int | 对象图内省最大递归深度 |
| `$&#123;TESTED_CLASS&#125;` | `com.aicode.template.template.context.Type` | 被测试类实例 |
| `$&#123;MONTH_NAME_EN&#125;` | String | 当前英文月份名 |
| `$&#123;DAY_NUMERIC&#125;` | int | 当前日期数字 |
| `$&#123;HOUR_NUMERIC&#125;` | int | 当前小时数字 |
| `$&#123;MINUTE_NUMERIC&#125;` | int | 当前分钟数字 |
| `$&#123;SECOND_NUMERIC&#125;` | int | 当前秒数字 |
| `$&#123;StringUtils&#125;` | `com.aicode.template.template.context.StringUtils` | 字符串工具 |
| `$&#123;TestBuilder&#125;` | `com.aicode.template.template.context.TestBuilder` | 测试构建器 |
| `$&#123;MockitoMockBuilder&#125;` | `com.aicode.template.template.context.MockitoMockBuilder` | Mockito Mock 构建器 |
| `$&#123;TestSubjectUtils&#125;` | `com.aicode.template.template.context.TestSubjectInspector` | 被测类检查工具 |

#### 3.3.2 IflyCode common macros.java.ft

公共宏定义文件，定义以下宏：

| 宏名 | 参数 | 功能 |
|------|------|------|
| `renderTestMethodName` | `$methodName` | 生成测试方法名：`test$&#123;CapitalizedName&#125;` |
| `renderTestCaseMethodName` | `$caseMethodName, $methodName` | 生成测试用例方法名 |
| `renderTestMethodNameAsWords` | `$methodName` | 将方法名转为自然语言 |
| `testMethodSuffix` | `$methodName, $prefix` | 处理重名方法的后缀编号 |
| `renderJavaReturnVar` | `$type` | 渲染 Java 返回变量声明 |

关键全局变量：
- `$trackedTestMethodsCount` — 哈希表，跟踪每个方法名已生成的测试方法数量，用于处理重名

#### 3.3.3 IflyCode macros.java.ft

扩展宏定义文件，包含完整的测试代码生成宏。继承 `IflyCode common macros.java.ft`。

**VTL 变量声明**：

| 变量名 | 类型 | 说明 |
|--------|------|------|
| `$replacementTypes` | `Map<String,String>` | 自定义类型替换映射（参数生成） |
| `$replacementTypesForReturn` | `Map<String,String>` | 自定义类型替换映射（返回值生成） |
| `$defaultTypeValues` | `Map<String,String>` | 基本类型默认值映射 |
| `$mockBuilder` | `MockBuilder` | Mock 构建器（默认 `$MockitoMockBuilder`） |

**$defaultTypeValues 完整映射**：

| 类型 | 默认值 |
|------|--------|
| byte | (byte) 0 |
| short | (short) 0 |
| int | 0 |
| long | 0L |
| float | 0f |
| double | 0d |
| char | 'a' |
| boolean | true |
| java.lang.Byte | Byte.valueOf("00110") |
| java.io.Serializable | Long.valueOf(1) |
| java.util.UUID | UUID.randomUUID() |
| java.lang.Runnable | ()->&#123;&#125; |
| java.lang.Short | Short.valueOf((short)0) |
| java.lang.Integer | Integer.valueOf(0) |
| java.lang.Long | Long.valueOf(1) |
| java.lang.Float | Float.valueOf(1.1f) |
| java.lang.Double | Double.valueOf(0) |
| java.lang.Character | Character.valueOf('a') |
| java.lang.Boolean | Boolean.TRUE |
| org.springframework.data.redis.core.RedisTemplate | new RedisTemplate<String,Object>() |
| java.util.concurrent.ThreadPoolExecutor | new ThreadPoolExecutor(5,10,10L,MINUTES,new LinkedBlockingQueue<>()) |
| java.io.InputStream | new ByteArrayInputStream(new byte[]&#123;0&#125;) |
| java.io.ByteArrayInputStream | new ByteArrayInputStream(new byte[]&#123;0&#125;) |
| java.io.DataInputStream | new DataInputStream(new ByteArrayInputStream(new byte[]&#123;&#125;)) |
| java.io.PipedInputStream | new PipedInputStream(4) |
| java.io.FilterInputStream | new DataInputStream(new ByteArrayInputStream(new byte[]&#123;&#125;)) |
| java.io.InputStreamReader | new InputStreamReader(new ByteArrayInputStream(new byte[]&#123;&#125;)) |
| java.io.ObjectInputStream | new ObjectInputStream(new ByteArrayInputStream(new byte[]&#123;&#125;)) |
| java.io.SequenceInputStream | new SequenceInputStream(...) |
| java.io.FileInputStream | new FileInputStream(getClass().getResource(...)) |
| java.util.zip.ZipOutputStream | new ZipOutputStream(new ByteArrayOutputStream()) |
| java.io.OutputStream | new ByteArrayOutputStream() |
| java.io.FileOutputStream | new FileOutputStream(getClass().getResource(...)) |
| java.io.DataOutputStream | new DataOutputStream(new ByteArrayOutputStream()) |
| java.io.ByteArrayOutputStream | new ByteArrayOutputStream() |
| java.io.PipedOutputStream | new PipedOutputStream() |
| java.io.OutputStreamWriter | new OutputStreamWriter(new ByteArrayOutputStream()) |
| java.io.FilterOutputStream | new FilterOutputStream(new ByteArrayOutputStream()) |
| java.io.ObjectOutputStream | new ObjectOutputStream(new ByteArrayOutputStream()) |
| java.io.Reader | Reader.nullReader() |
| java.io.FileReader | new FileReader(getClass().getResource(...)) |
| java.io.CharArrayReader | new CharArrayReader("string to read".toCharArray()) |
| java.io.StringReader | new StringReader("string to read") |
| java.io.Writer | Writer.nullWriter() |
| java.io.CharArrayWriter | new CharArrayWriter() |
| java.io.StringWriter | new StringWriter() |
| java.io.PrintWriter | new PrintWriter(new StringWriter()) |
| java.io.FileWriter | new FileWriter(getClass().getResource(...)) |
| java.io.BufferedReader | new BufferedReader(new StringReader("string to read")) |
| java.math.BigDecimal | new BigDecimal(0) |
| java.util.Date | new GregorianCalendar($YEAR, $MONTH, $DAY, $HOUR, $MINUTE).getTime() |
| java.time.LocalDate | LocalDate.of($YEAR, Month.$MONTH, $DAY) |
| java.time.LocalDateTime | LocalDateTime.of($YEAR, Month.$MONTH, $DAY, $HOUR, $MINUTE, $SECOND) |
| java.time.LocalTime | LocalTime.of($HOUR, $MINUTE, $SECOND) |
| java.time.Instant | LocalDateTime.of(...).toInstant(ZoneOffset.UTC) |
| java.io.File | new File(getClass().getResource(...)) |
| java.lang.Class | $TESTED_CLASS.canonicalName.class |

**核心宏定义**：

| 宏名 | 参数 | 功能 |
|------|------|------|
| `renderTestSubjectInit` | `$testedClass, $hasTestableInstanceMethod, $hasMocks` | 渲染被测类初始化（@InjectMocks 或直接构造） |
| `renderMockedFields` | `$hasMocks, $testedClass` | 渲染 @Mock 字段 |
| `renderJavaReturnVar` | `$type` | 渲染返回变量声明 |
| `renderJUnitAssert` | `$method` | 渲染 JUnit 断言 |
| `renderTestNgAssert` | `$method` | 渲染 TestNG 断言 |
| `renderJunitAssertMethod` | `$type` | 选择 assertArrayEquals 或 assertEquals |
| `renderMethodCall` | `$method, $testedClassName, $caseResult` | 渲染方法调用 |
| `renderMockStubs` | `$method, $testedClass` | 渲染 Mockito when().thenReturn() 存根 |
| `renderMockStubWithData` | `$method, $testedClass, $caseResult` | 渲染带数据的 Mock 存根 |
| `renderMockVerifies` | `$method, $testedClass` | 渲染 Mockito verify() 验证 |
| `renderInternalMethodCallsStubs` | `$method, $testedClass` | 渲染 PowerMock 内部方法调用存根 |
| `renderInternalMethodCallsStubsWithData` | `$method, $testedClass, $caseResult` | 渲染带数据的 PowerMock 存根 |
| `renderTestMethodCase` | `$method, $testedClass` | 渲染方法分支内容（AI 精准生成） |
| `renderMethodCallWithSpy` | `$method, $testedClassName` | 渲染使用 spy 的方法调用 |
| `renderMethodCallWithSpyAndData` | `$method, $testedClassName, $caseResult` | 渲染带数据的 spy 方法调用 |

### 3.4 unitTests/ 模板

#### 模板概览

| 模板文件 | 行数 | 测试框架 | Mock 框架 | 特殊依赖 |
|----------|------|---------|----------|---------|
| JUnit4.java.ft | 271 | JUnit 4 | 无 | - |
| JUnit4&Mockito.java.ft | 451 | JUnit 4 | Mockito | - |
| JUnit4&Powermock.java.ft | 462 | JUnit 4 | PowerMock | JUnit, Coverage |
| JUnit5.java.ft | 308 | JUnit 5 | 无 | - |
| JUnit5&Mockito.java.ft | 479 | JUnit 5 | Mockito | - |
| SpringBootTest&Mockito.java.ft | 481 | Spring Boot | Mockito | - |
| TestNG&Mockito.java.ft | 257 | TestNG | Mockito | - |
| back | 22 | (片段) | - | PowerMock 方法模板片段 |

#### 模板共同结构

所有测试模板遵循相同的结构模式：

1. **VTL 变量声明** — 定义 `replacementTypes`、`replacementTypesForReturn`、`defaultTypeValues`、`mockBuilder`
2. **公共宏引入** — `#parse("IflyCode common macros.java")`
3. **包声明和导入** — 根据 framework 自动生成 import 语句
4. **测试类声明** — 使用 `@RunWith` / `@ExtendWith` / `@SpringBootTest` 等注解
5. **被测类初始化** — `@InjectMocks` 或直接构造
6. **Mock 字段声明** — `@Mock` 注解字段
7. **setUp 方法** — `@Before` / `@BeforeEach` / `@BeforeMethod` 初始化
8. **测试方法** — 遍历 `$TESTED_CLASS.methods`，使用 `#renderMethodWithData` 生成
9. **AI 分支测试** — 使用 `#renderTestMethodCase` 生成精准分支测试

#### 各模板差异

| 特性 | JUnit4 | JUnit4+Mockito | JUnit4+PowerMock | JUnit5 | JUnit5+Mockito | SpringBootTest+Mockito | TestNG+Mockito |
|------|--------|---------------|-----------------|--------|---------------|----------------------|---------------|
| @RunWith | JUnit4 | MockitoJUnitRunner | PowerMockRunner | - | MockitoExtension | SpringBootTest | - |
| @ExtendWith | - | - | - | - | MockitoExtension | - | - |
| @SpringBootTest | - | - | - | - | - | 是 | - |
| @Before | @Before | @Before | @Before | @BeforeEach | @BeforeEach | @BeforeEach | @BeforeMethod |
| @Test | @Test | @Test | @Test | @Test | @Test | @Test | @Test |
| Assert | Assert.assertEquals | Assert.assertEquals | Assert.assertEquals | Assertions.assertEquals | Assertions.assertEquals | Assertions.assertEquals | Assert.assertEquals |
| Mock 初始化 | - | MockitoAnnotations.openMocks | MockitoAnnotations.openMocks | - | MockitoAnnotations.openMocks | MockitoAnnotations.openMocks | MockitoAnnotations.openMocks |
| 异常测试 | @Test(expected) | @Test(expected) | @Test(expected) | Assertions.assertThrows | Assertions.assertThrows | Assertions.assertThrows | @Test(expectedExceptions) |
| Spy 支持 | - | - | PowerMockito.spy | - | - | - | - |
| 内部方法 Mock | - | - | doReturn...when(spy) | - | - | - | - |

#### back 文件

`back` 是一个模板片段文件（22行），包含 PowerMock 模式的测试方法体模板：

```velocity
@Test
public void #renderTestMethodName($method.name)()
    #if($PowerMockBuilder.hasInternalMethodCall($method, $TESTED_CLASS)||$method.private)
        throws Exception
    #else
        $mockBuilder.resolveExceptions($method)
    #end &#123;
    #if($hasMocks)
        #renderStaticMockStubs($method,$caseResult)
    #end
    #if($hasMocks && $PowerMockBuilder.shouldStub($method, $TESTED_CLASS))
        #renderMockStubs($method, $TESTED_CLASS)
    #end
    #if($PowerMockBuilder.hasInternalMethodCall($method, $TESTED_CLASS) && $PowerMockBuilder.shouldStub($method, $TESTED_CLASS))
        #renderInternalMethodCallsStubs($method, $TESTED_CLASS)
        #renderMethodCallWithSpy($method,$TESTED_CLASS.name)
    #else
        #renderMethodCall($method,$TESTED_CLASS.name)
    #end
    #if($hasMocks && $PowerMockBuilder.shouldVerify($method,$TESTED_CLASS))
        #renderMockVerifies($method,$TESTED_CLASS)
    #end
    #if($method.hasReturn())
        Assert.#renderJUnitAssert($method,$method.caseResult)
    #end
&#125;
```

### 3.5 模板在代码中的引用

| 类 | 用途 |
|----|------|
| `com.aicode.template.VelocityInitializer` | Velocity 引擎初始化 |
| `com.aicode.template.TemplateGenerator` | 模板生成器入口 |
| `com.aicode.template.TestTemplateParams` | 模板参数封装 |
| `com.aicode.template.TestTemplateContextBuilder` | 模板上下文构建 |
| `com.aicode.template.fileloader.TemplateResourceLoader` | 模板资源加载器 |
| `com.aicode.template.fileloader.FileTemplatesLoader` | 文件模板加载器 |
| `com.aicode.template.fileloader.FileTemplateContext` | 文件模板上下文 |
| `com.aicode.template.fileloader.FileTemplateLoadResult` | 模板加载结果 |
| `com.aicode.template.fileloader.UnitFileTemplate` | 单元测试文件模板 |
| `com.aicode.template.fileloader.UnitTemplateManager` | 单元测试模板管理器 |
| `com.aicode.template.fileloader.FTManager` | 文件模板管理器 |
| `com.aicode.template.fileloader.TemplateRegistry` | 模板注册表（注册 JUnit4/5 等模板） |
| `com.aicode.template.generator.TestFileTemplateUtil` | 测试文件模板工具 |
| `com.aicode.template.generator.CreateTestMethodTask` | 创建测试方法任务 |
| `com.aicode.template.generator.CreateTestFileTask` | 创建测试文件任务 |
| `com.aicode.template.builder.MockitoMockBuilder` | Mockito Mock 构建器 |
| `com.aicode.template.builder.PowerMockBuilder` | PowerMock 构建器 |
| `com.aicode.template.builder.MockBuilderFactory` | Mock 构建器工厂 |
| `com.aicode.template.context.service.TestBuilder` | 测试构建器接口 |
| `com.aicode.template.context.service.impl.JavaTestBuilderImpl` | Java 测试构建器实现 |
| `com.aicode.template.context.service.impl.TestBuilderImpl` | 测试构建器实现 |

---
