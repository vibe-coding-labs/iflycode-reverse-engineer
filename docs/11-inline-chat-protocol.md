# 11 内联聊天协议

## 概述

内联聊天 (Inline Chat) 允许用户直接在编辑器中与 AI 交互，实时看到代码变更的 diff 效果。

## 生命周期 (InlineChatStepEnum)

```
CATEGORY ──► LOADING ──► SUCCESS
    │            │
    │            └──► ERROR ──► (重试回到 LOADING)
    │
    └──► (用户取消)
```

| 阶段 | UI 表现 |
|------|---------|
| `CATEGORY` | 显示分类选择面板 (DOC/LINEDOC/EDIT/GENERATE) |
| `LOADING` | 等待响应，显示停止按钮 |
| `ERROR` | 显示重试和取消按钮 |
| `SUCCESS` | 显示接受/拒绝/Diff/重试按钮 |

## 操作枚举 (InlineChatOperateEnum)

| 操作 | 说明 |
|------|------|
| `INSERT` | 生成了新代码 |
| `EDIT` | 修改了现有代码 |

## 分类枚举 (InlineChatCategoryEnum)

| 分类 | 说明 |
|------|------|
| `DOC` | 文档生成 |
| `LINEDOC` | 行级文档 |
| `EDIT` | 编辑现有代码 |
| `GENERATE` | 生成新代码 |
| `UNKNOW` | 未知/回退 |

## 完整流程

### Step 1: 打开 Inline Chat

```
用户触发 (快捷键/右键/Gutter 图标)
    │
    └─► InlineChatService.openInlineChat()
        │
        ├─► 检查 apiKey、权限、设置
        │
        ├─► 发送 USER_MODEL_LIST 刷新模型
        │
        ├─► 获取选中代码范围:
        │   range = [
        │     { "line": startLine, "character": startChar },
        │     { "line": endLine, "character": endChar }
        │   ]
        │
        ├─► 创建 InlineChatInputPanel (输入面板)
        │
        └─► 创建 InlineChatInlay (Inlay 渲染器)
```

### Step 2: 获取函数范围

```
Plugin ──► CommandEnum.INLINECHAT_GET_FUNC_RANGE
  {
    "id": "uuid",
    "command": "inlinechat_get_func_range",
    "path": "/path/to/File.java",
    "content": "// 文件完整内容",
    "range": [
      { "line": 10, "character": 0 },
      { "line": 15, "character": 20 }
    ],
    "inlineChatVersion": 1
  }
  └─► Agent 返回扩展后的函数范围
```

### Step 3: 用户输入问题后发送

```
用户输入问题 → InlineChatInputPanel → SessionController
    │
    └─► CommandEnum.INLINECHAT_CATEGORY
      {
        "id": "uuid",
        "command": "inlinechat_category",
        "path": "/path/to/File.java",
        "content": "// 文件内容",
        "range": [...],
        "data": "用户的问题",
        "inlineChatVersion": 1,
        "otherObject": sessionController  // transient, 用于回调路由
      }
```

### Step 4: 接收流式响应

```
Agent 逐条返回 ResponseStreamDto:
  { "id": "uuid", "data": { "ended": false, "text": "代码片段..." } }

InlineChatStreamHandleService.handleData()
    │
    ├─► 匹配 requestId 和 inlineChatVersion
    │
    ├─► 解析 directName → InlineChatCategoryEnum
    │
    └─► 根据分类渲染:
        ├─► DOC/GENERATE: 在光标下方插入新行
        └─► EDIT/LINEDOC: 使用 DiffRowGenerator 计算 diff
            - 删除行: 红色高亮 RGB(240,20,20,20)
            - 插入行: 绿色高亮 RGB(120,254,200,50)
            - 当前行: 灰色高亮 RGB(120,120,120,100)
```

### Step 5: 用户最终操作

| 快捷键 | Action | 命令 |
|--------|--------|------|
| Alt+Y | AcceptAction | `dialog_accept` |
| Alt+X | RejectAction | `dialog_reject` |
| Alt+Z | StopAction | `dialog_abort` (停止) |
| Alt+D | RetryAction | 重试 (重新发送请求) |
| Ctrl+Z | UndoAction | 撤销 |

### Step 6: 清理

```
SessionController.handleOperation(ACCEPT/REJECT)
    │
    ├─► 移除所有 highlighter
    ├─► 移除 inlay
    └─► 清除 InlineChatService 中的 editor 关联
```

## Diff 算法

Inline Chat 使用 `DiffRowGenerator` (来自 java-diff-utils 库) 进行实时 diff：

```
原始代码:                    生成的代码:
  line 1                      line 1
  line 2 (修改)               line 2'  ← CHANGE (红色→绿色)
  line 3                      line 3
  (空)                        line 4   ← INSERT (绿色)
  line 5                      line 5
  line 6 (删除)               (空)     ← DELETE (红色)
```

## 版本控制

`inlineChatVersion` 计数器防止过期响应覆盖当前状态：
- 每次 openInlineChat 递增
- 响应中的 version 必须匹配当前版本才处理

## 直接模式 (INLINECHAT_DIRECT)

通过 `INLINECHAT_DIRECT` 命令，可以跳过分类选择，直接发送到特定功能：

```json
{
  "command": "inlinechat_direct",
  "directName": "DOC",
  "data": "为这个函数生成注释"
}
```

## Gutter 图标

通过 `PresentationDataDto` 在编辑器左侧 gutter 区域显示图标：

```json
{
  "line": 10,
  "character": 0,
  "type": "METHOD",    // METHOD 或 CLASS
  "codeInfoDto": {
    "fileName": "File.java",
    "path": "/path/to/File.java",
    "language": "java",
    "content": "...",
    "allContent": "..."
  }
}
```

用户点击图标可快速触发 Inline Chat。
