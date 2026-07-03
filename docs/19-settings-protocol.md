# 19 设置同步协议

## 设置获取

```
W→J: setting_get_config
  └─► CommonService 读取 AICodeSettingsState
      └─► J→W: 推送 SettingsDto
          &#123;
            "type": "setting_get_config",
            "value": &#123;
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
            &#125;
          &#125;
```

## 设置更新

```
W→J: setting_update_config
  &#123;
    "value": &#123;
      "autoTriggerOnPause": false,
      "autoTriggerTimeDelay": 500,
      "generateCodeMode": "SINGLE_LINE"
    &#125;
  &#125;
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
  &#123;
    "type": "common_plugin_base_info",
    "value": &#123;
      "version": "3.4.2",
      "ideType": "IntelliJ IDEA",
      "ideVersion": "2024.1",
      "platform": "macOS"
    &#125;
  &#125;
```

## 快捷键设置

```
W→J: setting_popup_keymap_settings
  └─► 打开 IntelliJ Keymap 设置页面
```

## 操作指南显示设置

```
W→J: setting_save_show_operate_guidance
  &#123;
    "value": &#123;
      "showGuide": false
    &#125;
  &#125;
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
  &lt;autoTrigger&gt;true&lt;/autoTrigger&gt;
  &lt;tipType&gt;INTELLIGENT_MODE&lt;/tipType&gt;
  &lt;sendKey&gt;ENTER_KEY&lt;/sendKey&gt;
  &lt;triggerTime&gt;200&lt;/triggerTime&gt;
  &lt;userName&gt;user@example.com&lt;/userName&gt;
  &lt;userId&gt;user-uuid&lt;/userId&gt;
  &lt;enterpriseId&gt;ent-uuid&lt;/enterpriseId&gt;
  &lt;modelCode&gt;spark-v3.5&lt;/modelCode&gt;
  &lt;testFramework&gt;JUNIT_FOUR&lt;/testFramework&gt;
  &lt;mockFramework&gt;POWER_MOCK&lt;/mockFramework&gt;
  &lt;loginUrl&gt;https://...&lt;/loginUrl&gt;
  &lt;feedbackUrl&gt;https://...&lt;/feedbackUrl&gt;
  ...
&lt;/component&gt;
```

## Agent 端设置同步

部分设置由 Agent 端管理，通过 `GENERAL_SETTING` 命令同步：

```
Agent ──► GENERAL_SETTING
  &#123;
    "data": &#123;
      "settings": &#123; ... &#125;
    &#125;
  &#125;
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
