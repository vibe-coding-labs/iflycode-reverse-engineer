### 2.19 PluginDocumentListener

```
public class com.aicode.listener.PluginDocumentListener
  implements com.intellij.openapi.components.ProjectComponent

  // --- 静态字段 ---
  private static final Logger enum
  public static Map<Project, List&lt;Object&gt;> projectListConcurrentHashMap  // 项目->资源列表映射

  // --- 方法 ---

  // 项目打开
  public void projectOpened():
    1. ApplicationUtil.findCurrentProject() 获取项目
    2. 创建锁对象
    3. 创建 Alarm (POOLED_THREAD):
       a. 如果项目非 null: new Alarm(POOLED_THREAD, project)
       b. 如果项目为 null: new Alarm(POOLED_THREAD)
    4. 将 [Alarm, 锁对象] 存入 projectListConcurrentHashMap

  // 项目关闭
  public void projectClosed():
    1. 遍历 projectListConcurrentHashMap 的 keySet:
       a. 如果项目已 disposed: 从 Map 中移除

  // 获取组件名
  public String getComponentName():
    - 返回解混淆字符串 (项目文档监听器名称)
```

**交互关系**:
- `projectListConcurrentHashMap` -- 全局共享的项目资源映射，被 `CodeFileEditorManagerListener$01` 引用
- `Alarm` -- IntelliJ 定时器，用于延迟执行代码补全请求

---

### 2.20 PluginManagerListener

```
public class com.aicode.listener.PluginManagerListener
  implements com.intellij.openapi.project.ProjectManagerListener

  // --- 方法 ---

  // 项目关闭
  public void projectClosed(Project):
    1. project.getBasePath() 获取项目路径
    2. PluginWebsocketClient.closeWebsocket(basePath, 解混淆: "项目关闭")
    3. ChatService.SESSION_ID.remove(basePath)  // 清除 Chat 会话
    4. SqlService.SQL_SESSION_ID.remove(basePath) // 清除 SQL 会话

  // 项目关闭前保存
  public void projectClosingBeforeSave(Project):
    1. 调用 kc(project) -- 处理 Inline Chat 会话

  // 处理 Inline Chat 会话关闭
  private static void kc(Project):
    1. 创建 ArrayList 收集需要处理的 InlineChatInfo
    2. 遍历 EditorKt.inlineChatCacheData:
       a. 如果 info.getEditor().getProject() == 当前项目:
          - 添加到列表
    3. 遍历列表:
       a. 如果 sessionController.getInlineChatStepEnum() == SUCCESS:
          - sessionController.handleOperation(editor, DIALOG_ACCEPT)  // 自动接受
    4. 异常处理: 捕获 Throwable
```

**交互关系**:
- `PluginWebsocketClient.closeWebsocket()` -- 关闭 WebSocket 连接
- `ChatService.SESSION_ID` -- Chat 会话 ID 缓存
- `SqlService.SQL_SESSION_ID` -- SQL 会话 ID 缓存
- `EditorKt.inlineChatCacheData` -- Inline Chat 缓存数据
- `SessionController.handleOperation()` -- Inline Chat 操作处理

---

### 2.21 ThemeChangeListener

```
public class com.aicode.listener.ThemeChangeListener
  implements com.intellij.openapi.components.ApplicationComponent

  // --- 字段 ---
  private int float           // 字体大小缓存
  private String byte         // 主题名缓存
  private static final Logger enum

  // --- 方法 ---

  // 初始化组件
  public void initComponent():
    1. 创建 LafManagerListener (lookAndFeelChanged)
    2. 创建 EditorColorsListener (globalSchemeChange)
    3. Application.getMessageBus().connect()
    4. 订阅 LafManagerListener.TOPIC (lookAndFeelChanged)
    5. 订阅 EditorColorsManager.TOPIC (globalSchemeChange)

  // LookAndFeel 变更
  private void ac(LafManager):
    1. 通过反射获取 LafManager 的当前 LookAndFeelInfo
    2. 如果 byte(主题名) 为空: 设置为当前 LAF 名
    3. 如果 float(字体大小) 为 0: 从全局 Scheme 获取 consoleFontSize
    4. 如果新主题名与缓存不同:
       - changeTheme(themeName, fontSize)
    5. 更新 byte = 新主题名

  // EditorColorsScheme 变更
  private void yA(EditorColorsScheme):
    1. 获取当前全局 Scheme 的 consoleFontSize
    2. 如果字体大小与缓存不同:
       - changeTheme(cachedThemeName, newFontSize)
    3. 更新 float = 新字体大小

  // 初始化主题
  public static void initTheme():
    1. EditorColorsManager.getInstance().getGlobalScheme()
    2. 获取 scheme 名和 consoleFontSize
    3. changeTheme(schemeName, fontSize)

  // 获取主题(含图标切换)
  public static String getTheme(String themeName, ToolWindow toolWindow):
    1. 如果 themeName 非空且包含 "dark"(解混淆):
       a. 设置暗色图标: Icons.StatusBarIcon = dark 图标路径
       b. 设置 ToolWindow 图标为暗色
       c. 返回 "dark"
    2. 否则:
       a. 设置亮色图标: Icons.StatusBarIcon = light 图标路径
       b. 设置 ToolWindow 图标为亮色
       c. 返回 "light"

  // 切换主题(异步)
  public static void changeTheme(String themeName, int fontSize):
    1. Application.invokeLater:
       a. ea(themeName, fontSize) -- 实际执行

  // 实际主题切换
  private static void ea(String themeName, int fontSize):
    1. 获取插件版本号
    2. 遍历所有有效项目:
       a. 获取 ToolWindow
       b. getTheme(themeName, toolWindow) -- 获取主题+切换图标
       c. 构建 JsonObject:
          - "theme": 主题名
          - "fontSize": 字体大小
          - "type": SETTING_CHANGE_THEME
       d. SocketMessageHandleListener.send2Web(project, data) -- 发送给 WebView
       e. StatusBarPopup.update(project) -- 更新状态栏

  // 释放组件
  public void disposeComponent():
    - 空实现

  // 获取组件名
  public String getComponentName():
    - 返回解混淆字符串
```

**交互关系**:
- `SocketMessageHandleListener.send2Web()` -- 发送消息给 WebView
- `StatusBarPopup.update()` -- 更新状态栏图标
- `Icons.StatusBarIcon` -- 状态栏图标
- `ToolWindow.setIcon()` -- 工具窗口图标
- `WebViewResponseTypeEnum.SETTING_CHANGE_THEME` -- 主题变更响应类型
- `BasicActionsBundle.message()` -- 获取插件版本号

---
