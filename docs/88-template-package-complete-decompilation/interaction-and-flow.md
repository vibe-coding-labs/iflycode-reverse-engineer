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
    -> TemplateTestPromptDto: &#123; stream, content, unitTest &#125;

// 响应方向：Agent Server -> IDE
TemplateRequestService.handleAgentAction()
  <- CommandEnum: UNIT_TEST_CASE / UNIT_TEST_ERROR
  <- JsonObject: &#123; caseMethodName, input, output, mockMethods, branches, exception &#125;
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
- `&lt;branch&gt;...&lt;/branch&gt;` - 分支信息

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
