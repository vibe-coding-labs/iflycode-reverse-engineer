## 2. Updater 包 — 插件自动更新系统

### 2.1 包总览

| 类名 | 源文件名 | 大小 | 职责 |
|------|----------|------|------|
| `PluginUpdater` | lb | 13K | 更新执行器：下载、安装、通知 |
| `PluginUpdater$E` | lb | 2.7K | 通知动作：重启 IDE |
| `PluginUpdater$m` | lb | 1.9K | 通知动作：关闭通知 |
| `PluginUpdaterCheckService` | hb | 9.2K | 更新检查调度服务 |
| `PluginUpdaterCheckService$CheckUpdatesTask` | hb | 5.2K | 后台检查更新任务 |
| `PluginUpdaterCheckService$k` | hb | 3.3K | 后台安装更新任务 |
| `UpdaterChecker2021_1` | db | 3.6K | 2021.1 及以下版本更新检查器 |
| `UpdaterCheckerFrom2021_2` | yb | 3.7K | 2021.2+ 版本更新检查器 |

**关键特征**：所有类名均经过混淆（源文件名为两字母代码），字符串常量通过 `OverlayUtils.H()`、`GitReviewService.H()`、`ConditionalActionConfiguration.H()` 等解密方法运行时解密。

### 2.2 PluginUpdater — 更新执行器

**路径**: `com/aicode/updater/PluginUpdater`

**字段**:
```
public static final Logger logger
private static final AtomicReference&lt;String&gt; enum  // 存储当前版本号
```

**方法签名与用途**:

| 方法 | 用途 |
|------|------|
| `checkUpdate(Project, JsonObject)` | 入口：解析登录响应中的更新信息，触发更新流程 |
| `notification(Project, String)` | 显示更新通知气泡，附带"重启"和"稍后"两个动作 |
| `doUpdate(Project, String, String, String, String)` | 执行更新：复制文件到临时目录、禁用插件、安装新版本 |
| `isUpdater(Project)` | 检查 `AICodeSettingsState.isUpdater` 标志，显示"待更新"通知 |
| `installAfterRestart(IdeaPluginDescriptor, Path, Path, boolean)` | 注册重启后安装脚本（Delete/Copy/Unzip 命令） |
| `disableOrEnablePlugin(String, PluginId)` | 通过反射调用 `PluginEnabler` 禁用/启用插件 |
| `isOccurred()` | 通过反射检查 `DynamicPluginEnabler` 是否正在执行更新 |

**checkUpdate 流程详解**（从字节码还原）:

```
1. 检查是否为 SaaS 场景 → 是则跳过更新
2. 从 JsonObject 中提取 "update" 字段（解密后为 "update"）
3. 将 JsonObject 反序列化为 LoginInfo 对象
4. 提取 LoginInfo 的五个字段：
   - current: 当前版本号
   - update:  新版本号
   - file:    下载文件路径
   - md5:     文件 MD5 校验值
   - name:    插件名称
5. 如果任一字段为空 → 返回
6. 如果 current == update → 版本一致，无需更新
7. 否则 → 在 Swing EDT 线程中调用 doUpdate
```

**doUpdate 流程详解**:

```
1. 检查 AtomicReference 中的版本号是否与 update 版本一致
   → 一致则只显示通知（已下载但未安装的情况）
2. 创建源文件 File 对象，检查文件是否存在
3. 构建临时路径：PathManager.getPluginTempPath() + separator + name
4. 如果临时文件不存在，或文件大小不一致 → 复制源文件到临时目录
5. 获取 AICODE_ID (PluginId)
6. 调用 disableOrEnablePlugin("disable", AICODE_ID) 禁用当前插件
7. 如果禁用成功：
   a. 查找已启用的插件描述符
   b. 获取插件安装路径
   c. 检查 isOccurred()（是否有其他动态更新正在进行）
   d. 调用 installAfterRestart(descriptor, tempPath, pluginPath, !isOccurred)
   e. 如果 isOccurred → 调用 InstalledPluginsState.onPluginInstall
   f. 否则 → 调用 InstalledPluginsState.addPreInstalledPlugin
   g. 重新启用插件：disableOrEnablePlugin("enable", AICODE_ID)
   h. 设置 AICodeSettingsState.isUpdater = true
   i. 显示更新通知
   j. 更新 AtomicReference 中的版本号
```

**installAfterRestart 流程详解**:

```
构建 StartupActionScriptManager 命令列表：
1. 如果旧插件路径存在 → 添加 DeleteCommand(旧路径)
2. 添加 DeleteCommand(当前插件 lib 路径)
3. 获取 PluginsPath
4. 如果新文件以 .jar 结尾：
   → 添加 CopyCommand(新文件, pluginsPath/fileName)
5. 否则（.zip 格式）：
   → 添加 DeleteCommand(pluginsPath/rootEntryName)
   → 添加 UnzipCommand(新文件, pluginsPath)
6. 如果 isUpdate（非首次安装）→ 添加 DeleteCommand(新文件)
7. 调用 StartupActionScriptManager.addActionCommands(commands)
```

### 2.3 PluginUpdater$E — 重启动作

**路径**: `com/aicode/updater/PluginUpdater$E`
**继承**: `NotificationAction`

**方法**:
| 方法 | 用途 |
|------|------|
| `actionPerformed(AnActionEvent, Notification)` | 调用 `Application.restart()` 重启 IDE |
| `v()` | 静态方法，调用 `ApplicationManager.getApplication().restart()` |
| `PluginUpdater$E(String)` | 构造函数，传入动作显示名 |

### 2.4 PluginUpdater$m — 关闭通知动作

**路径**: `com/aicode/updater/PluginUpdater$m`
**继承**: `NotificationAction`

**方法**:
| 方法 | 用途 |
|------|------|
| `actionPerformed(AnActionEvent, Notification)` | 调用 `notification.hideBalloon()` 关闭通知气泡 |

### 2.5 PluginUpdaterCheckService — 更新检查调度

**路径**: `com/aicode/updater/PluginUpdaterCheckService`

**字段**:
```
private static volatile boolean final     // 检查进行中标志
private static volatile boolean try       // 安装进行中标志
private static final Object float         // 同步锁
private static volatile String byte        // 上次检查到的版本号
private static final Logger enum           // 日志
```

**方法签名与用途**:

| 方法 | 用途 |
|------|------|
| `scheduleRepeatedUpdateCheck(Project)` | 注册定时检查：每 24 小时执行一次 |
| `queueUpdateCheck(Project)` | 入队一次更新检查（加锁，防止并发） |
| `s(PluginDownloader)` | 检查下载器是否为 iFlyCode 插件 |
| `q(Project)` | 检查设置中是否开启自动更新，是则入队检查 |
| `X(Project, PluginDownloader)` | 创建安装任务 $k 并入队执行 |
| `t(Project, PluginDownloader, ProgressIndicator)` | 通过反射调用 `PluginInstaller.prepareToInstall` |
| `O(ProgressIndicator)` | 版本分发：baseline <= 211 用 2021_1 检查器，> 211 用 2021_2+ 检查器 |
| `N(Collection&lt;PluginDownloader&gt;)` | 从更新列表中筛选 iFlyCode 插件的更新 |

**scheduleRepeatedUpdateCheck 流程**:

```
AppExecutorUtil.getAppScheduledExecutorService()
  .scheduleWithFixedDelay(
    runnable,     // 调用 q(project)
    0,            // 初始延迟 0
    24,           // 间隔 24 小时
    HOURS         // 时间单位
  )
```

**queueUpdateCheck 流程**:

```
1. 加锁 (float 对象)
2. 检查 final 和 try 标志
3. 如果都不为 true → 设置 final = true，创建 CheckUpdatesTask 并入队
4. 释放锁
```

**O() 版本分发逻辑**:

```
int baseline = ApplicationInfo.getInstance().getBuild().getBaselineVersion()
if (baseline <= 211) &#123;
    return UpdaterChecker2021_1.findAvailableUpdates(indicator)
&#125; else &#123;
    return UpdaterCheckerFrom2021_2.findAvailableUpdates(indicator)
&#125;
```

### 2.6 PluginUpdaterCheckService$CheckUpdatesTask — 检查任务

**路径**: `com/aicode/updater/PluginUpdaterCheckService$CheckUpdatesTask`
**继承**: `Task.Backgroundable`

**方法**:
| 方法 | 用途 |
|------|------|
| `run(ProgressIndicator)` | 执行更新检查，找到更新后安排安装 |
| `onThrowable(Throwable)` | 设置 final = false（检查完成） |
| `onCancel()` | 设置 final = false |
| `R(PluginDownloader)` | 调用 PluginUpdaterCheckService.X 安排安装 |

**run() 流程**:

```
1. 检查项目是否已销毁
2. 调用 O(indicator) 获取可用更新列表
3. 调用 N(updates) 筛选 iFlyCode 插件更新
4. 如果找到更新且版本号与上次不同：
   → StartupManager.runWhenProjectIsInitialized &#123; R(downloader) &#125;
5. 设置 final = false
```

### 2.7 PluginUpdaterCheckService$k — 安装任务

**路径**: `com/aicode/updater/PluginUpdaterCheckService$k`
**继承**: `Task.Backgroundable`

**字段**:
```
public final Project byte           // 项目引用
public final PluginDownloader enum  // 下载器引用
```

**方法**:
| 方法 | 用途 |
|------|------|
| `run(ProgressIndicator)` | 调用 `PluginUpdaterCheckService.t()` 执行安装准备 |
| `onThrowable(Throwable)` | 设置 try = false |
| `onCancel()` | 设置 try = false |

### 2.8 UpdaterChecker2021_1 — 旧版检查器

**路径**: `com/aicode/updater/UpdaterChecker2021_1`

**方法**:
| 方法 | 用途 |
|------|------|
| `findAvailableUpdates(ProgressIndicator)` | 通过反射调用 `UpdateChecker.getUpdatesForPlugins` |

**反射调用链**（从字节码还原）:

```
1. Class.forName("com.intellij.openapi.updateSettings.impl.UpdateChecker")
2. getMethod("getUpdatesForPlugins", Collection.class, ProgressIndicator.class)
3. invoke(null, singletonList(null), indicator)  // 静态方法，第一个参数传 null
4. 对返回值调用 getDeclaredMethod("getPluginUpdates")
5. invoke(result) → 获取更新列表
6. 如果结果为 null → 返回空列表
```

**注意**：2021.1 及以下版本的 IntelliJ API 中 `UpdateChecker.getUpdatesForPlugins` 是静态方法，接受 `Collection&lt;PluginId&gt;` 和 `ProgressIndicator`。

### 2.9 UpdaterCheckerFrom2021_2 — 新版检查器

**路径**: `com/aicode/updater/UpdaterCheckerFrom2021_2`

**方法**:
| 方法 | 用途 |
|------|------|
| `findAvailableUpdates(ProgressIndicator)` | 通过反射调用新版 `UpdateChecker` API |

**反射调用链**（从字节码还原）:

```
1. Class.forName("com.intellij.openapi.updateSettings.impl.UpdateChecker")
2. getMethod("getUpdatesForPlugins", BuildNumber.class, ProgressIndicator.class)
3. invoke(null, null, indicator)  // BuildNumber 传 null（使用当前 IDE 版本）
4. 对返回值调用 getMethod("getPluginUpdates")
5. invoke(result) → 获取更新列表
6. 对结果调用 getMethod("getAll")
7. invoke(result) → 获取所有插件更新
8. 如果结果为 null → 返回空列表
```

**注意**：2021.2+ 版本的 IntelliJ API 变更了 `getUpdatesForPlugins` 的签名，第一个参数从 `Collection` 变为 `BuildNumber`。

### 2.10 更新流程总图

```
                        ┌─────────────────────────────────┐
                        │    scheduleRepeatedUpdateCheck   │
                        │   (每24小时, ScheduledExecutor)   │
                        └────────────┬────────────────────┘
                                     │
                                     v
                        ┌─────────────────────────────────┐
                        │       queueUpdateCheck           │
                        │  (synchronized, 防止并发检查)     │
                        └────────────┬────────────────────┘
                                     │
                                     v
                        ┌─────────────────────────────────┐
                        │     CheckUpdatesTask.run()       │
                        │   (Backgroundable 后台任务)       │
                        └────────────┬────────────────────┘
                                     │
                          ┌──────────┴──────────┐
                          │  baseline <= 211?    │
                          └──┬───────────────┬──┘
                             │               │
                    ┌────────v───┐   ┌───────v──────────┐
                    │ 2021_1     │   │ From2021_2       │
                    │ 反射调用    │   │ 反射调用          │
                    │ getUpdates │   │ getUpdates        │
                    │ ForPlugins │   │ ForPlugins        │
                    │ (Collection│   │ (BuildNumber,     │
                    │  Progress) │   │  Progress)        │
                    └─────┬──────┘   └───────┬──────────┘
                          │                  │
                          └──────┬───────────┘
                                 │
                                 v
                    ┌─────────────────────────────────┐
                    │  N() 筛选 iFlyCode 插件更新        │
                    │  (PluginId == AICODE_ID)          │
                    └────────────┬────────────────────┘
                                 │
                    ┌────────────v────────────────────┐
                    │  版本号与上次不同?                  │
                    │  (byte != downloader.getVersion) │
                    └────┬──────────────────────┬─────┘
                         │ 是                    │ 否
                         v                       v
            ┌──────────────────────┐     ┌──────────────┐
            │  创建 $k 安装任务     │     │   跳过       │
            │  runWhenProjectIs    │     └──────────────┘
            │  Initialized         │
            └──────────┬───────────┘
                       │
                       v
            ┌─────────────────────────────────┐
            │   $k.run()                      │
            │   t(project, downloader, ind)   │
            │   → 反射调用                     │
            │   PluginInstaller               │
            │   .prepareToInstall             │
            └──────────┬──────────────────────┘
                       │
                       v
            ┌─────────────────────────────────┐
            │   PluginUpdater.notification()  │
            │   显示更新通知气泡                 │
            │   [重启] → $E → restart()       │
            │   [稍后] → $m → hideBalloon()   │
            └─────────────────────────────────┘

    ══════════════════════════════════════════════════════════

    另一条路径：登录响应触发更新
    ─────────────────────────────

    checkUpdate(Project, JsonObject)
        │
        ├── 解析 LoginInfo: current, update, file, md5, name
        │
        ├── current == update? → 跳过
        │
        └── doUpdate(Project, update, file, md5, name)
            │
            ├── 复制文件到临时目录
            ├── disableOrEnablePlugin("disable", AICODE_ID)
            ├── installAfterRestart(descriptor, temp, pluginPath, !isOccurred)
            │   ├── DeleteCommand(旧路径)
            │   ├── DeleteCommand(lib路径)
            │   ├── CopyCommand 或 UnzipCommand
            │   └── addActionCommands(commands)
            ├── disableOrEnablePlugin("enable", AICODE_ID)
            ├── AICodeSettingsState.isUpdater = true
            └── notification(Project, update)
```

### 2.11 MD5 校验机制

从 `checkUpdate` 方法中可以看到，`LoginInfo` 对象包含 `md5` 字段。然而在 `doUpdate` 的字节码中，MD5 校验逻辑并未直接出现。可能的情况：

1. MD5 校验在服务端完成（下载 URL 中已包含校验信息）
2. MD5 校验在 `FileUtil.copy` 或下载过程中由 hutool 库内部完成
3. MD5 字段仅用于日志记录或上报

从字节码分析，`doUpdate` 方法通过文件大小比较（`File.length()`）来判断文件是否需要重新复制，而非 MD5。

### 2.12 版本兼容性检查

更新检查器根据 IDE 的 `BuildNumber.getBaselineVersion()` 进行分发：

| Baseline | IDE 版本范围 | 检查器 |
|----------|-------------|--------|
| <= 211 | IntelliJ 2021.1 及更早 | `UpdaterChecker2021_1` |
| > 211 | IntelliJ 2021.2+ | `UpdaterCheckerFrom2021_2` |

两个检查器都通过反射调用 IntelliJ 内部 API，原因是：
- IntelliJ 的 `UpdateChecker` API 在不同版本间不兼容
- 反射调用可以避免编译时依赖特定版本
- 捕获 `NoSuchMethodException`、`IllegalAccessException`、`InvocationTargetException` 以优雅降级

---
