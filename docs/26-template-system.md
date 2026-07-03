# iFlyCode 模板系统分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-11

## 1. 概述

iFlyCode 使用 Apache Velocity 模板引擎生成 Java 单元测试代码。模板系统包含：

- **7 个测试模板** — 覆盖主流 Java 测试框架
- **2 个宏库** — 共享的代码生成宏
- **1 个 HTML 模板** — 默认展示页面

## 2. 模板文件清单

### 2.1 测试模板 (unitTests/)

| 模板文件 | 大小 | 框架组合 |
|---------|------|---------|
| `JUnit4.java.ft` | 16KB | JUnit 4 纯净版 |
| `JUnit4&Mockito.java.ft` | 27KB | JUnit 4 + Mockito |
| `JUnit4&Powermock.java.ft` | 28KB | JUnit 4 + PowerMock |
| `JUnit5.java.ft` | 18KB | JUnit 5 纯净版 |
| `JUnit5&Mockito.java.ft` | 28KB | JUnit 5 + Mockito |
| `SpringBootTest&Mockito.java.ft` | 28KB | Spring Boot + Mockito |
| `TestNG&Mockito.java.ft` | 15KB | TestNG + Mockito |

### 2.2 宏库 (unitIncludes/)

| 文件 | 大小 | 说明 |
|------|------|------|
| `IflyCode macros.java.ft` | 14KB | 主宏库 — 测试方法生成、Mock 设置、断言生成 |
| `IflyCode common macros.java.ft` | 1.2KB | 通用宏 — 方法名渲染、类型处理 |
| `default.html` | 4.8KB | 默认 HTML 展示页面 |

## 3. Velocity 上下文变量

模板接收以下上下文变量（从 VTL 注释提取）：

| 变量 | 类型 | 说明 |
|------|------|------|
| `$replacementTypes` | `Map<String,String>` | 自定义类型替换映射 |
| `$replacementTypesForReturn` | `Map<String,String>` | 返回类型替换映射 |
| `$mockBuilder` | `MockBuilder` | Mock 构建器实例 |

## 4. 核心宏定义

### 4.1 测试方法命名

```velocity
#macro(renderTestMethodName $methodName)
test$StringUtils.capitalizeFirstLetter($methodName)#testMethodSuffix($methodName,"")
#end
```

- 自动添加 `test` 前缀
- 首字母大写
- 处理重名方法（自动添加数字后缀）

### 4.2 Java 返回变量

```velocity
#macro(renderJavaReturnVar $type)
#if($type && $type.name !="void")$type.canonicalName result = #end
#end
```

### 4.3 默认类型值

模板中预定义了 80+ 种 Java 类型的默认值：

| 类型 | 默认值 |
|------|--------|
| `byte` | `(byte) 0` |
| `int` | `0` |
| `long` | `0L` |
| `boolean` | `true` |
| `java.lang.String` | `""` |
| `java.util.UUID` | `UUID.randomUUID()` |
| `java.lang.Runnable` | `()&#123;&#125;` |
| `java.io.InputStream` | `new ByteArrayInputStream(new byte[]&#123;0&#125;)` |
| `java.io.OutputStream` | `new ByteArrayOutputStream()` |
| `org.springframework.data.redis.core.RedisTemplate` | `new RedisTemplate<String,Object>()` |
| `java.util.concurrent.ThreadPoolExecutor` | `new ThreadPoolExecutor(5,10,...)` |

### 4.4 特殊类型处理

模板包含对以下框架的特殊支持：

- **Spring Framework**: `RedisTemplate`, `@Autowired`, `@Resource`
- **企业内部框架**: `com.bocom.jump.bp.core.*` (交通银行框架)
- **Java IO**: `InputStream`, `OutputStream`, `Reader`, `Writer` 全系列
- **Java 集合**: `Collection`, `Map`, `List`, `Set`

## 5. 模板生成流程

```
1. 用户选择方法/类 → 右键 → 单元测试
2. UnitTestService 收集方法签名、参数类型、返回类型
3. TestSubjectInspector 分析方法调用链、依赖关系
4. MockBuilder 生成 Mock 配置 (Mockito/PowerMock)
5. Velocity 引擎渲染模板 → 生成测试代码
6. CreateTestFileTask 写入测试文件
7. 可选：编译 + 执行 + 收集覆盖率
```

## 6. Mock 框架支持

| 框架 | 支持方式 | 模板 |
|------|---------|------|
| Mockito | `MockitoMockBuilder` | JUnit4&Mockito, JUnit5&Mockito, SpringBootTest&Mockito, TestNG&Mockito |
| PowerMock | `PowerMockBuilder extends MockitoMockBuilder` | JUnit4&Powermock |

### Mock 构建器继承关系

```
MockBuilder (接口)
└── MockitoMockBuilder (实现)
    └── PowerMockBuilder (扩展)
```

## 7. 代码生成策略

从 `BasicActionsBundle.properties` 提取：

| 策略 | Key | 说明 |
|------|-----|------|
| 快速生成 | `config.batch.unit.test.generate.by.template` | 调用规则能力快速生成单测基础代码 |
| 精准生成 | `config.batch.unit.test.generate.by.template.ai` | 结合 AI 模型精准识别代码分支，并生成单测代码 |

## 8. 生成流程选项

| 流程 | Key | 说明 |
|------|-----|------|
| 仅生成单测 | `生成单测` | 只生成选择文件的单测文件 |
| 生成+编译 | `生成单测 + 编译` | 生成单测文件并编译 |
| 生成+编译+执行 | `生成单测 + 编译 + 执行` | 生成、编译后执行单测，收集覆盖率信息 |

## 9. 关键发现

1. **企业框架支持**: 模板中包含 `com.bocom.jump.bp.core.*` (交通银行) 的类型默认值，说明 iFlyCode 针对金融企业客户做了定制。

2. **80+ 种类型默认值**: 模板预定义了大量 Java 类型的默认实例化代码，覆盖了 IO、集合、并发、Spring 等常见类型。

3. **双生成策略**: "快速生成"使用纯模板规则，"精准生成"结合 AI 模型识别代码分支后生成更精准的测试用例。

4. **7 种框架组合**: 支持 JUnit4/5、TestNG、Mockito、PowerMock、SpringBoot 的各种组合。

5. **Velocity 宏污染**: 模板大量使用 `#macro` 定义，宏之间有复杂的调用关系，特别是重名方法处理和类型替换逻辑。
