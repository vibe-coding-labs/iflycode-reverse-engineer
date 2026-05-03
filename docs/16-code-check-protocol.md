# 16 代码检查协议

## 功能概览

代码检查功能分析编辑器中的代码，识别拼写、语法和逻辑错误，并提供修复建议。

## 检查流程

### 请求检查列表

```
W→J: request_code_check_list
  {
    "value": {
      "path": "/path/to/File.java",
      "content": "// 文件完整内容"
    }
  }
  └─► CommandEnum.CODE_CHECK
      {
        "command": "code_check",
        "path": "/path/to/File.java",
        "content": "// 文件完整内容",
        "data": { }
      }
      └─► Agent 返回检查结果
```

### 检查结果

```
J→W: get_code_check_list
  {
    "type": "get_code_check_list",
    "value": {
      "status": true,
      "message": "",
      "data": {
        "path": "src/Main.java",
        "name": "Main.java",
        "errList": [
          {
            "codeFragment": "int x = 1 / 0;",
            "errorType": "POTENTIAL_ERROR",
            "errorMessage": "除零错误",
            "range": [
              { "line": 42, "character": 12 },
              { "line": 42, "character": 23 }
            ]
          },
          {
            "codeFragment": "if (str = null)",
            "errorType": "LOGIC_ERROR",
            "errorMessage": "应使用 == 而非 =",
            "range": [
              { "line": 55, "character": 8 },
              { "line": 55, "character": 20 }
            ]
          }
        ]
      }
    }
  }
```

### 修复代码

```
W→J: code_check_fix
  {
    "value": {
      "id": "error-1",
      "codeInfo": { ... },
      "errorType": "POTENTIAL_ERROR",
      "errorMessage": "除零错误"
    }
  }
  └─► Agent 返回修复建议
      └─► 用户接受后应用到编辑器
```

### 更新检查

```
W→J: update_code_check
  └─► 重新检查文件
```

## 重复代码检测

```
CommandEnum.CODE_DEBUG_DUPLICATE
  {
    "command": "code_debug_duplicate",
    "path": "/path/to/File.java",
    "content": "// 文件内容"
  }
  └─► Agent 返回重复代码位置
```

## Gutter 图标集成

代码检查结果通过 `CheckGutterIconRenderer` 在编辑器 gutter 区域显示图标。

## Problems 面板集成

通过 IntelliJ Problems View 集成：

```
ProblemsView.ToolWindow.TreePopup
  └─► TriggerCodeProblemsTreePopupAction (一键修复)
      └─► CodeProblemsIntentionAction
          └─► 触发代码检查修复流程
```

## 错误 DTO

### CodeCheckDto

```java
{
    "codeFragment": "int x = 1 / 0;",
    "errorType": "POTENTIAL_ERROR",
    "errorMessage": "除零错误",
    "codeInfo": { CodeInfoDto }
}
```

### CodeCheckOriginDto

```java
{
    "path": "src/Main.java",
    "name": "Main.java",
    "errList": [
        {
            "codeFragment": "...",
            "errorType": "...",
            "errorMessage": "...",
            "range": [ { "line": 42, "character": 12 }, ... ]
        }
    ]
}
```
