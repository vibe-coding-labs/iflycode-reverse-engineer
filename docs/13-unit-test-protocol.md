# 13 单元测试协议

## 单元测试模式

iFlyCode 支持两种单元测试模式：
1. **模板模式** (Java): 使用 Velocity 模板 + 模型生成
2. **流式模式** (其他语言): 使用 AI 流式生成

## 测试框架配置

### Java

| 框架 (UnitTestBaseEnum) | 说明 |
|------------------------|------|
| `JUNIT_FOUR` | JUnit 4 |
| `JUNIT_FIVE` | JUnit 5 |
| `TEST_NG` | TestNG |

| Mock (UnitTestMockEnum) | 说明 |
|------------------------|------|
| `POWER_MOCK` | PowerMock |
| `MOCKITO` | Mockito |
| `EASY_MOCK` | EasyMock |

### Python

| 框架 (PyUnitTestBaseEnum) | 说明 |
|--------------------------|------|
| `UNITTEST` | unittest |
| `PYTEST` | pytest |

| Mock (PyUnitTestMockEnum) | 说明 |
|--------------------------|------|
| `UNITTESTMOCK` | unittest.mock |
| `PYTESTMOCK` | pytest-mock |

## 单测流程

### Step 1: 获取单测信息

```
W→J: ut_get_ut_info
  └─► 收集当前文件的函数/方法信息
      └─► J→W: ut_page_ready
          {
            "fileName": "Processor.java",
            "functions": [
              { "name": "process", "range": [...] },
              { "name": "validate", "range": [...] }
            ]
          }
```

### Step 2: 获取方法用例

```
W→J: ut_get_method_case
  {
    "methodName": "process",
    "filePath": "/path/to/Processor.java"
  }
  └─► CommandEnum.CODE_TEST_CASE
      {
        "command": "code_test_case",
        "path": "/path/to/Processor.java",
        "content": "// 文件内容",
        "data": { "methodName": "process" }
      }
      └─► Agent 返回测试用例
```

### Step 3: 获取测试代码

```
W→J: ut_get_case_code
  └─► CommandEnum.TEST_MAKE_CODE
      └─► Agent 返回完整测试代码
```

### Step 4: 保存测试

```
W→J: ut_save_code
  └─► 写入测试文件到项目
```

### Step 5: 重新生成

```
W→J: ut_regenerate
  └─► 重新请求 Agent 生成
```

## 模板模式 (Java)

### 分析请求

```
CommandEnum.CODE_TEST_ANALYSIS
  {
    "command": "code_test_analysis",
    "path": "/path/to/Processor.java",
    "lang": "java"
  }
  └─► Agent 返回方法分析结果
      └─► TemplateRequestService 生成模板上下文
```

### 生成测试用例

```
CommandEnum.CODE_TEST_MAKE_CASE_JAVA
  {
    "command": "code_test_make_case_java",
    "path": "/path/to/Processor.java",
    "content": "// 文件内容",
    "data": { ... }
  }
  └─► Agent 返回测试用例
      └─► 使用 Velocity 模板渲染
```

## 批量单测 (BATCH_UNIT_TEST)

### 创建批量单测

```
W→J: batch_ut_create
  {
    "files": ["File1.java", "File2.java"],
    "config": { ... }
  }
  └─► CommandEnum.CODE_BATCH_UNIT_TEST_CREATE
      └─► Agent 逐文件生成测试
          └─► J→W: batch_ut_message (进度推送)
              {
                "type": "batch_ut_message",
                "value": {
                  "status": "processing",
                  "file": "File1.java",
                  "progress": 50
                }
              }
```

### 获取任务列表

```
W→J: batch_ut_get_task_list
  └─► CommandEnum.CODE_BATCH_UNIT_TEST_LIST
      └─► Agent 返回所有批量单测任务
```

### 下载

```
W→J: batch_ut_download
  └─► CommandEnum.CODE_BATCH_UNIT_TEST_DOWNLOAD
```

### 取消

```
W→J: batch_ut_download
  └─► CommandEnum.CODE_BATCH_UNIT_TEST_CANCEL
```

### 删除

```
W→J: batch_ut_delete
  └─► CommandEnum.CODE_BATCH_UNIT_TEST_DELETE
```

## 单元测试 (UNIT_TESTING 模块)

这是一个不同的 UI 流程，用于更交互式的测试生成：

```
W→J: unitesting_page_ready
  └─► 初始化测试页面

J→W: unitesting_receive_function
  └─► 推送方法列表到 WebView

W→J: unitesting_save
  └─► 保存生成的测试代码

J→W: unitesting_response_save
  └─► 保存结果

W→J: unitesting_mapping_file
  └─► 映射源文件和测试文件

W→J: unitesting_idea_stop / unitesting_web_stop
  └─► 停止测试生成
```

## 请求间隔控制

```java
// AICodeSettingsState
Integer unitRequestInterval = 8;  // 最小值: 5
void setUnitRequestInterval(int newInterval) {
    this.unitRequestInterval = (Math.min(5, newInterval) + this.unitRequestInterval) / 2;
}
```

批量单测时控制请求频率，避免过快请求。
