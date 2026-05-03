# 19 设置同步协议

## 设置获取

```
W→J: setting_get_config
  └─► CommonService 读取 AICodeSettingsState
      └─► J→W: 推送 SettingsDto
          {
            "type": "setting_get_config",
            "value": {
              "autoTriggerOnPause": true,
              "autoTriggerTimeDelay": 200,
              "generateCodeMode": "INTELLIGENT_MODE",
              "sendMessageType": "ENTER_KEY",
              "codeCompleteDisableLang": ["txt", "md"],
              "javaTestFramework": "JUNIT_FOUR",
              "javaMockFramework": "POWER_MOCK",
              "lineToolsType": "ICON",
              "lineToolsPermissionDocComments": true,
              "lineToolsPermissionLineComments": true,
              "lineToolsPermissionComments": true,
              "lineToolsPermissionFunctionSplit": true,
              "lineToolsPermissionCodeOptimization": true,
              "lineToolsPermissionUnitTesting": true,
              "openFunctionSplit": true,
              "openCodeOptimization": true,
              "openIFlyTest": true,
              "openInlineChat": true,
              "openIFlyDBA": true,
              "openIFlyOps": true,
              "openIFlyPm": true,
              "openCodeEnhance": true,
              "inlineCompletionInputStyle": "DISPOSABLE",
              "openAutoUpdate": true,
              "defaultLanguage": "auto"
            }
          }
```

## 设置更新

```
W→J: setting_update_config
  {
    "value": {
      "autoTriggerOnPause": false,
      "autoTriggerTimeDelay": 500,
      "generateCodeMode": "SINGLE_LINE"
    }
  }
  └─► CommonService 写入 AICodeSettingsState
      ├─► 更新 autoTrigger
      ├─► 更新 triggerTime
      ├─► 更新 tipType
      ├─► 更新 testFramework
      ├─► 更新 mockFramework
      ├─► 更新 lineToolsType
      ├─► 更新各权限开关
      └─► 持久化到 AICodeSettingsPlugin.xml
```

## 插件基础信息

```
J→W: common_plugin_base_info
  {
    "type": "common_plugin_base_info",
    "value": {
      "version": "3.4.2",
      "ideType": "IntelliJ IDEA",
      "ideVersion": "2024.1",
      "platform": "macOS"
    }
  }
```

## 快捷键设置

```
W→J: setting_popup_keymap_settings
  └─► 打开 IntelliJ Keymap 设置页面
```

## 操作指南显示设置

```
W→J: setting_save_show_operate_guidance
  {
    "value": {
      "showGuide": false
    }
  }
  └─► 保存到 TipInfoDto.isShowOperateGuide
```

## 代码增强查询

```
W→J: setting_get_can_open_code_enhance
  └─► 查询用户权限中是否包含代码增强
      └─► 返回 boolean
```

## 持久化存储

设置保存在 IntelliJ 配置文件中：

```
位置: <IDE config dir>/options/AICodeSettingsPlugin.xml

内容示例:
<component name="com.aicode.settings.AICodeSettingsState">
  <autoTrigger>true</autoTrigger>
  <tipType>INTELLIGENT_MODE</tipType>
  <sendKey>ENTER_KEY</sendKey>
  <triggerTime>200</triggerTime>
  <userName>user@example.com</userName>
  <userId>user-uuid</userId>
  <enterpriseId>ent-uuid</enterpriseId>
  <modelCode>spark-v3.5</modelCode>
  <testFramework>JUNIT_FOUR</testFramework>
  <mockFramework>POWER_MOCK</mockFramework>
  <loginUrl>https://...</loginUrl>
  <feedbackUrl>https://...</feedbackUrl>
  ...
</component>
```

## Agent 端设置同步

部分设置由 Agent 端管理，通过 `GENERAL_SETTING` 命令同步：

```
Agent ──► GENERAL_SETTING
  {
    "data": {
      "settings": { ... }
    }
  }
  └─► Plugin 更新本地设置
```

## 插件场景 (PluginSceneEnum)

| 场景 | 说明 |
|------|------|
| `PLUGIN_SAAS` | 公有云 SaaS 版本 |
| `PLUGIN_PRIVATE` | 私有化部署版本 |
| `PLUGIN_INNER` | 内部版本 |

场景通过 `BasicActionsBundle.message("plugin.scene")` 配置，影响：
- UI 显示 (是否显示官网链接)
- 功能开关
- 登录方式
