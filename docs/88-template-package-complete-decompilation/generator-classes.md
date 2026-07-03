### 2.3 template/generator/ 子包

#### 2.3.1 CacheFileTemplate

```
public class CacheFileTemplate &#123;
  private Map<String, Object> paramMaps;                  // 模板参数
  private FileTemplateContext context;                     // 文件模板上下文
  private PsiDirectory targetDirectory;                   // 目标目录
  private GeneratorFileConfig generatorFileConfig;        // 生成器文件配置
  private MethodGeneratorConfig methodGeneratorConfig;    // 方法生成器配置
  private List&lt;MessageDto&gt; messageDtos;                   // AI 消息列表
  // getter/setter
&#125;
```

#### 2.3.2 ClassNameSelection + UserDecision

```
public class ClassNameSelection &#123;
  private final String className;
  private final UserDecision userDecision;
&#125;

public enum ClassNameSelection$UserDecision &#123;
  USE,        // 使用现有类
  USE_OTHER,  // 使用其他名称
  SKIP        // 跳过
&#125;
```

#### 2.3.3 CreateTestFileTask（核心任务，2461 行）

```
public class CreateTestFileTask extends Task.Backgroundable &#123;
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
&#125;
```

#### 2.3.4 CreateTestMethodTask

```
public class CreateTestMethodTask &#123;
  // 单方法测试生成任务：
  // 1. 解析目标方法
  // 2. 构建 TypeDictionary
  // 3. 请求 AI 生成用例
  // 4. 合并模板生成测试方法
  // 5. 插入到已有测试类中

  // $1 - 方法处理回调
&#125;
```

#### 2.3.5 GeneratedClassNameResolver

```
public class GeneratedClassNameResolver &#123;
  // 解析生成的类名冲突：
  // 1. 检查目标目录是否已有同名类
  // 2. 如果冲突，弹出对话框让用户选择
  // 3. 支持自动追加数字后缀

  // $1 - 类名比较器
&#125;
```

#### 2.3.6 GeneratorFileConfig

```
public class GeneratorFileConfig &#123;
  private String targetDirectory;     // 目标目录
  private String packageName;         // 包名
  private String className;           // 类名
  private String srcClassName;        // 源类名
  private boolean customClassName;    // 是否自定义类名
  // getter/setter
&#125;
```

#### 2.3.7 GeneratorProcess

```
public class GeneratorProcess &#123;
  // 生成流程控制：协调各组件完成生成
&#125;
```

#### 2.3.8 GeneratorTemplateConfig

```
public class GeneratorTemplateConfig &#123;
  private String testFramework;       // 测试框架：JUnit4/JUnit5/TestNG
  private String mockFramework;       // Mock 框架：Mockito/PowerMock
  private boolean generateTestsForInternalMethods;
  private boolean requestAi;          // 是否请求 AI
  private FileTemplateConfig fileTemplateConfig;
  // getter/setter
&#125;
```

#### 2.3.9 ProcessErrorFileAnalyzer

```
public class ProcessErrorFileAnalyzer &#123;
  // 分析生成过程中的错误文件：
  // 1. 检测编译错误
  // 2. 分析 import 缺失
  // 3. 尝试自动修复

  // $1 - 错误分析回调
&#125;
```

#### 2.3.10 TargetDirectoryLocator

```
public class TargetDirectoryLocator &#123;
  // 测试目录定位策略：
  // 1. 查找 src/test/java 对应目录
  // 2. 查找同模块测试源根
  // 3. 查找跨模块测试源根

  // $1 - 同模块目录搜索
  // $2 - 跨模块目录搜索
  // $3 - 默认目录搜索
&#125;
```

#### 2.3.11 TestFileTemplateUtil

```
public class TestFileTemplateUtil &#123;
  // 测试文件模板工具：
  // 1. 合并 Velocity 模板
  // 2. 处理模板中的占位符
  // 3. 生成最终测试代码
&#125;
```

---
