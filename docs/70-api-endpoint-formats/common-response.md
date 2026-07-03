## 1. 通用响应格式

### 1.1 标准响应 ResponseDto (确认)

```json
&#123;
  "id": "string",       // 请求ID
  "code": "string",     // 响应码 (0=成功, 其他=错误)
  "msg": "string",      // 响应消息
  "data": &#123;&#125;            // 响应数据 (Object)
&#125;
```

> 来源: `com.aicode.agent.dto.ResponseDto` 字段: id, code, msg, data

### 1.2 流式响应 ResponseStreamDto (确认)

```json
&#123;
  "id": "string",
  "code": "string",
  "msg": "string",
  "data": &#123;
    "ended": false,           // 是否结束
    "text": "string",         // 增量文本内容
    "showKeyMapTipFlag": false // 是否显示快捷键提示
  &#125;
&#125;
```

> 来源: `com.aicode.agent.dto.ResponseStreamDto` + `ResponseStreamDto$ResponseData`

### 1.3 业务响应 BizResponse (确认)

```json
&#123;
  "resCode": "string",  // 响应码 (RES_CODE_SUCCESS = "0")
  "msg": "string",      // 响应消息
  "obj": &#123;&#125;             // 响应对象 (泛型T)
&#125;
```

> 来源: `com.aicode.service.response.BizResponse` (混淆字段: float=obj, byte=resCode, enum=msg)

### 1.4 错误码定义 (确认)

| 错误码 | 含义 |
|--------|------|
| 0 | 成功 |
| 400 | 参数错误 / 未知指令 / 不支持消息推送 |
| 401 | 未授权 / 用户未登录 |
| 404 | 指令不合法 / 找不到函数 |
| 408 | 任务处理超时 |
| 500 | 内部错误 |
| 501 | 登录地址未配置 / 登录失败 |
| 502 | 登录状态异常 |
| 600 | 参数不能为空 / 请选择数据表 / 帐号密码不能为空 |
| 601 | 文件读取失败 / 请选择文件 |
| 602 | 代码上下文超限 / 未选中有效方法 / 未查询到待评审内容 |
| 604 | 数据源不存在 / 提交信息无效 |
| 607 | 输入内容超长或选择表过多 |
| 608 | 暂无可评审内容 |
| 609 | 取消/指令已结束 (ACTION_REJECT/ACTION_ESC/debounce) |
| 610 | 指令操作已经结束 |
| 611 | 网络异常 |

---

## 2. 通用请求基础数据 getBaseData() (确认)

所有 Chat API 请求均包含以下基础字段:

```json
&#123;
  "requestId": "string",           // 请求唯一ID
  "modelCode": "string",           // 模型代码
  "enterpriseId": "string",        // 企业ID
  "enableMultiModelSwitch": false, // 是否启用多模型切换
  "token": "string",               // 用户token
  "language": "string",            // 编程语言
  "timeStamp": 0,                  // 时间戳
  "fileName": "string",            // 文件名
  "fileNameSuffix": "string",      // 文件扩展名
  "projectName": "string",         // 项目名
  "agentVersion": "string",        // Agent版本号
  "commandType": "string",         // 命令类型 (如 "CODE:COMPLETE")
  "taskName": "string",            // 任务名 (scene)
  "scene": "string",               // 场景标识
  "knowledgeBase": "string",       // 知识库类型 ("codeKnowledgeBase" / "docKnowledgeBase")
  "userQuestionContent": "string", // 用户问题内容
  "clientName": "string",          // 客户端名称 (推断)
  "clientVersion": "string",       // 客户端版本 (推断)
  "pluginVersion": "string"        // 插件版本 (推断)
&#125;
```

> 来源: webpack bundle `getBaseData()` 方法 + `clientInfo` 结构

---
