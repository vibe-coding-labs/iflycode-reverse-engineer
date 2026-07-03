# 08 用户认证流程

## 完整登录流程

```
┌─────────┐    ┌───────────┐    ┌───────────┐    ┌──────────┐
│ WebView  │    │ Plugin(J) │    │  Agent(本地)│    │  云端服务  │
└────┬─────┘    └─────┬─────┘    └─────┬──────┘    └────┬─────┘
     │                │                │                 │
     │  1. 页面加载完成 │                │                 │
     │◄───────────────┤                │                 │
     │                │  2. USER_LOGIN │                 │
     │                │───────────────►│  3. 请求登录URL  │
     │                │                │────────────────►│
     │                │                │  4. 返回登录URL  │
     │                │                │◄────────────────│
     │                │  5. LOGIN_INFO │                 │
     │                │◄───────────────│                 │
     │  6. login_receiver_login_iframe_src               │
     │◄───────────────┤                │                 │
     │                │                │                 │
     │  7. 用户扫码/登录                 │                 │
     │─────────────────────────────────────────────────►│
     │                │                │  8. 登录成功      │
     │                │                │◄────────────────│
     │                │  9. LOGIN_INFO (含 token, URLs, 企业信息)
     │                │◄───────────────│                 │
     │                │                │                 │
     │                │  10. USER_PERMISSION              │
     │                │───────────────►│────────────────►│
     │                │                │◄────────────────│
     │                │◄───────────────│                 │
     │  11. login_succeed               │                 │
     │◄───────────────┤                │                 │
     │  12. user_permission_list        │                 │
     │◄───────────────┤                │                 │
     │  13. 配置、模型列表、首页          │                 │
     │◄───────────────┤                │                 │
```

## 步骤详解

### 1. 页面加载与初始化

WebView 加载完成后 (`onLoadEnd`, status=200)：
- 注入 `window.myObject.sendMessage` JS Bridge
- 触发 `PluginWebsocketClient.sendWsMessage(CommandEnum.USER_LOGIN, project)`

### 2. USER_LOGIN 请求

```json
{
  "id": "uuid-xxx",
  "command": "user_login",
  "stream": true,
  "timeStamp": 1713744000000,
  "data": { "count": 1 }
}
```

### 3-4. Agent 请求登录 URL

Agent 向云端请求登录页面 URL。

### 5-6. 推送登录 iframe

```json
{
  "type": "login_receiver_login_iframe_src",
  "value": {
    "url": "https://portal.example.com/login?token=xxx&pluginVersion=3.4.2&ideType=IDEA&type=outer"
  }
}
```

### 7-8. 用户扫码登录

WebView 展示二维码或登录表单。用户完成登录后，云端通知 Agent。

### 9. LOGIN_INFO 响应 (Agent → Plugin)

Agent 推送完整登录信息：

```json
{
  "id": "uuid-xxx",
  "code": "0",
  "msg": "success",
  "command": "login_info",
  "data": {
    "clientId": "client-id-xxx",
    "user": "username",
    "token": "auth-token-xxx",
    "codeModelDtoList": [
      {
        "modelId": "model-1",
        "modelCode": "spark-v3.5",
        "modelName": "星火V3.5",
        "checked": true,
        "originalModelName": "Spark V3.5",
        "tokenExhausted": false
      }
    ],
    "enterpriseDto": {
      "enterpriseId": "ent-xxx",
      "enterpriseName": "企业名称",
      "userId": "user-xxx"
    },
    "tokenPath": "/path/to/token",
    "sysUrls": {
      "feedbackUrl": "https://...",
      "maintainRepoUrl": "https://...",
      "codeSearchServerUrl": "https://...",
      "officialWebsiteUrl": "https://portal.example.com/",
      "codeKnowledgeWebUrl": "https://...",
      "userCenterWebUrl": "https://..."
    },
    "packageCode": "pro",
    "packageName": "专业版",
    "reLogin": false
  }
}
```

### 10. Plugin 处理

收到 `LOGIN_INFO` 后 Plugin 执行：
1. 保存 `token` 到 `PluginStartupActivity.setApiKey()`
2. 保存用户信息到 `AICodeSettingsState`:
   - `userId`, `userName`
   - `enterpriseId`, `enterpriseName`
   - `loginUrl`, `feedbackUrl`, `maintainRepoUrl` 等 URL
   - `modelList` 模型列表
3. 发送 `USER_PERMISSION` 获取权限
4. 发送 `USER_MODEL_LIST` 获取完整模型信息

### 11. 推送登录成功到 WebView

```json
{
  "type": "login_succeed",
  "value": { }
}
```

### 12. 推送权限列表

```json
{
  "type": "user_permission_list",
  "value": [
    "code_optimization", "comments", "unit_testing",
    "doc_comments", "line_comments", "function_split",
    "inline_chat", "talk_intelligent", ...
  ]
}
```

### 13. 推送配置和首页

- 设置配置 (`SettingsDto`)
- 模型列表 (`FunctionModelInfo[]`)
- 首页推荐玩法
- 打开对话页 (`common_open_page` → `chat_view`)

## 登出流程

```
WebView ──login_logout──► UserService.handleAction()
    │
    ├─► PluginWebsocketClient.sendWsMessage(USER_LOGOUT)
    │
    ├─► AICodeSettingsState.clear()
    │   ├─ apiKey = ""
    │   ├─ userName = ""
    │   ├─ URLs = ""
    │   ├─ modelList.clear()
    │   └─ permissions.clear()
    │
    └─► 通知所有项目状态更新
```

## 多模型支持

登录后可获取和切换模型：

```
GET_MODEL_LIST ──► Agent ──► USER_MODEL_LIST
    │
    ├─► FunctionModelInfo[] (含 permissionCode, codeModelList)
    │
    └─► SET_MODEL ──► 保存 modelCode 到 AICodeSettingsState
```

## 会话场景

| 场景 | PluginSceneEnum | 说明 |
|------|----------------|------|
| SaaS | `PLUGIN_SAAS` | 公有云版本 |
| 私有化 | `PLUGIN_PRIVATE` | 私有部署版本 |
| 内部 | `PLUGIN_INNER` | 内部版本 |

不同场景下的功能开关和 UI 展示不同。
