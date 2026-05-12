# iFlyCode Updater/Domain/FileLoader 深入分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-13

## 1. 概述

本文档分析 iFlyCode 插件的三个核心包：

- **`com/aicode/updater/`** — 插件自动更新系统，8 个类
- **`com/aicode/domain/`** — 领域模型层，9 个类（含内部类共 10 个）
- **`com/aicode/template/fileloader/`** — 文件模板加载器，11 个类

这三个包分别负责：插件自身的热更新机制、代码补全/提示的核心数据模型、以及单元测试模板的文件加载与注册管理。

---

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
private static final AtomicReference<String> enum  // 存储当前版本号
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
| `N(Collection<PluginDownloader>)` | 从更新列表中筛选 iFlyCode 插件的更新 |

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
if (baseline <= 211) {
    return UpdaterChecker2021_1.findAvailableUpdates(indicator)
} else {
    return UpdaterCheckerFrom2021_2.findAvailableUpdates(indicator)
}
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
   → StartupManager.runWhenProjectIsInitialized { R(downloader) }
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

**注意**：2021.1 及以下版本的 IntelliJ API 中 `UpdateChecker.getUpdatesForPlugins` 是静态方法，接受 `Collection<PluginId>` 和 `ProgressIndicator`。

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

## 3. Domain 包 — 领域模型层

### 3.1 包总览

| 类名 | 源文件名 | 大小 | 职责 |
|------|----------|------|------|
| `CommandCache` | CommandCache.java | 2.5K | 命令缓存 — 记录编辑器选区状态 |
| `GetTipsResult` | GetTipsResult.java | 3.0K | 代码提示结果容器 |
| `GetTipsResult$Tip` | GetTipsResult.java | 4.2K | 单条代码提示 |
| `LineInfo` | LineInfo.java | 5.5K | 行信息 — 光标所在行的完整信息 |
| `Position` | Position.java | 4.6K | 位置 — 行号+列号 |
| `Range` | Range.java | 3.6K | 范围 — 起始+结束位置 |
| `Suggestion` | Suggestion.java | 3.0K | 建议项 — 代码补全建议 |
| `VirtualFileUri` | VirtualFileUri.java | 5.8K | 虚拟文件 URI — 跨系统文件标识 |
| `VirtualFileUri$TypeAdapter` | VirtualFileUri.java | 1.3K | Gson 序列化适配器 |

### 3.2 CommandCache — 命令缓存

**路径**: `com/aicode/domain/CommandCache`

**字段**:
```
private boolean startSelected          // 起始位置是否有选区
private int startSelectedStartOffset  // 起始选区的起始偏移
private boolean endSelected           // 结束位置是否有选区
private int endSelectedStartOffset    // 结束选区的起始偏移
```

**用途**: 记录用户在编辑器中执行命令（如行内聊天）时的选区状态，用于在命令执行后恢复选区。

### 3.3 Position — 位置

**路径**: `com/aicode/domain/Position`

**字段**:
```
int line       // 行号（0-based）
int character   // 列号（0-based）
```

**方法**:
| 方法 | 用途 |
|------|------|
| `of(int, int)` | 静态工厂方法 |
| `Position(LineInfo)` | 从 LineInfo 构造（提取行号和列号） |
| `toOffset(String)` | 将 Position 转换为文本中的字符偏移量 |
| `getCursorPosition(Editor)` | 获取编辑器中光标的 Position |
| `lambda$getCursorPosition$0(Editor)` | lambda: 从 CaretModel 获取位置 |

**用途**: 表示文本中的位置，兼容 LSP 风格的 line/character 坐标。`toOffset()` 方法将行列转换为字符偏移，用于 IntelliJ Document API 交互。

### 3.4 Range — 范围

**路径**: `com/aicode/domain/Range`

**字段**:
```
Position start  // 起始位置
Position end    // 结束位置
```

**方法**:
| 方法 | 用途 |
|------|------|
| `of(Position, Position)` | 静态工厂方法 |

**用途**: 表示文本中的一个范围，用于代码补全的替换区域、行内聊天的编辑范围等。

### 3.5 LineInfo — 行信息

**路径**: `com/aicode/domain/LineInfo`

**字段**:
```
final int lineCount         // 文档总行数
final int lineNumber       // 当前行号（0-based）
final int lineStartOffset  // 当前行起始偏移
final int columnOffset     // 光标在行内的列偏移
final String line          // 当前行文本内容
final int nextLineIndent   // 下一行的缩进量
```

**方法**:
| 方法 | 用途 |
|------|------|
| `create(Document, int)` | 静态工厂：从 Document 和偏移量创建 LineInfo |
| `getLinePrefix()` | 获取光标前的行内容 |
| `getLineSuffix()` | 获取光标后的行内容 |
| `isBlankLine()` | 当前行是否为空行 |
| `getWhitespaceBeforeCursor()` | 获取光标前的空白字符 |
| `getLineEndOffset()` | 获取行尾偏移量 |
| `calculateNextLineIndent(Document, int)` | 计算下一行的缩进 |

**用途**: 封装光标所在行的完整信息，是代码补全请求的核心数据。服务端根据行内容、前缀、后缀、缩进等信息生成补全建议。

### 3.6 GetTipsResult — 代码提示结果

**路径**: `com/aicode/domain/GetTipsResult`

**字段**:
```
List<GetTipsResult$Tip> tips  // 提示列表
```

### 3.7 GetTipsResult$Tip — 单条提示

**路径**: `com/aicode/domain/GetTipsResult$Tip`

**字段**:
```
final String uuid          // 提示唯一标识
final String text          // 补全文本
final Range range          // 替换范围
final String displayText   // 显示文本（灰色预览）
final Position position    // 插入位置
```

**用途**: 表示一条代码补全建议。`uuid` 用于追踪补全的接受/拒绝事件；`text` 是实际插入的代码；`displayText` 是编辑器中灰色预览文本；`range` 指定需要替换的文本范围。

### 3.8 Suggestion — 建议项

**路径**: `com/aicode/domain/Suggestion`

**字段**:
```
final int score             // 匹配分数
final String type            // 建议类型
final String hash            // 内容哈希
final CodeInlayList inlays   // 内嵌提示列表
```

**用途**: 代码补全的评分建议项，`score` 用于排序，`hash` 用于去重，`inlays` 关联 IntelliJ 的 InlayHint 显示。

### 3.9 VirtualFileUri — 虚拟文件 URI

**路径**: `com/aicode/domain/VirtualFileUri`

**字段**:
```
static Logger LOG
final String uri            // 文件 URI 字符串
```

**方法**:
| 方法 | 用途 |
|------|------|
| `from(VirtualFile)` | 从 VirtualFile 创建（处理路径前缀） |
| `from(VirtualFileSystem, String)` | 从文件系统和路径创建 |
| `processPath(String)` | 处理路径（标准化分隔符） |
| `isNeedsPathPrefix(VirtualFileSystem)` | 判断是否需要路径前缀 |
| `asPrefixedUri(String)` | 添加路径前缀 |
| `getUri()` | 获取 URI 字符串 |

**用途**: 将 IntelliJ 的 VirtualFile 转换为可序列化的 URI 标识，用于与服务端通信。处理了不同文件系统（如 jar://、temp://）的路径前缀需求。

### 3.10 VirtualFileUri$TypeAdapter — Gson 序列化适配器

**路径**: `com/aicode/domain/VirtualFileUri$TypeAdapter`
**实现**: `JsonSerializer<VirtualFileUri>`

**方法**:
| 方法 | 用途 |
|------|------|
| `serialize(VirtualFileUri, Type, JsonSerializationContext)` | 将 VirtualFileUri 序列化为 JSON 字符串 |

### 3.11 领域模型关系图

```
                    ┌──────────────────────────────────────────────────┐
                    │              代码补全数据流                       │
                    └──────────────────────────────────────────────────┘

    Editor (IntelliJ)                    Service (服务端)
         │                                   │
         v                                   │
    ┌──────────┐                              │
    │ LineInfo │ ←── Document + offset        │
    └────┬─────┘                              │
         │                                    │
         v                                    │
    ┌──────────┐                              │
    │ Position │ ←── LineInfo 构造            │
    └────┬─────┘                              │
         │                                    │
         v                                    v
    ┌──────────┐     HTTP/WS Request     ┌──────────────┐
    │  Range   │ ──────────────────────→ │  AI Backend   │
    └──────────┘                          └──────┬───────┘
         │                                       │
         │                              HTTP/WS Response
         │                                       │
         v                                       v
    ┌───────────────────┐                  ┌───────────────────┐
    │ VirtualFileUri    │                  │ GetTipsResult      │
    │ (文件标识)        │                  │ └── Tip            │
    └───────────────────┘                  │     ├── uuid       │
                                           │     ├── text       │
    ┌───────────────────┐                  │     ├── range ──────┼───→ Range
    │ CommandCache      │                  │     ├── displayText │
    │ (选区缓存)       │                  │     └── position ──┼───→ Position
    └───────────────────┘                  └───────────────────┘
                                                    │
                                                    v
                                           ┌───────────────────┐
                                           │ Suggestion         │
                                           │ ├── score          │
                                           │ ├── type           │
                                           │ ├── hash           │
                                           │ └── inlays         │
                                           └───────────────────┘

    ═══════════════════════════════════════════════════════════════

    Domain → Service 使用关系:
    ────────────────────────

    LineInfo ──→ RequestTipService (代码补全请求)
    Position ──→ InlineChatService (行内聊天定位)
    Range    ──→ InlineChatService (编辑范围)
    GetTipsResult$Tip ──→ PluginEditorInlayHintsProvider (渲染补全)
    Suggestion ──→ CodeInlayList (InlayHint 显示)
    VirtualFileUri ──→ 所有需要文件标识的请求
    CommandCache ──→ InlineChatHandleService (选区恢复)
```

### 3.12 扩展领域模型（跨包 DTO）

以下 DTO 类虽不在 `domain` 包中，但属于领域模型的一部分：

#### 3.12.1 agent/dto/chat 包 — 聊天领域 DTO

**CodeInfoDto** (`com/aicode/agent/dto/chat/CodeInfoDto`):
```
String content              // 代码片段内容
List<RangeDTO> range        // 代码范围
List<RangeDTO> bodyRange     // 方法体范围 (transient)
String fileName             // 文件名
String path                 // 文件路径
String language             // 编程语言
String allContent           // 文件完整内容
```

**CodeInfoDto$RangeDTO**:
```
Integer line                // 行号
Integer character           // 列号
```

**CommentContext** (`com/aicode/agent/dto/chat/CommentContext`):
```
String md5                              // 文件 MD5
List<CommentInfo> methods               // 方法注释列表
```

**CommentInfo** (`com/aicode/agent/dto/chat/CommentInfo`):
```
String name                  // 方法名
String textContext           // 注释文本
int index                    // 方法索引
JsonArray range              // 方法范围
JsonArray bodyRange          // 方法体范围
```

**PresentationDataDto** (`com/aicode/agent/dto/chat/PresentationDataDto`):
```
int line                     // 行号
int character                // 列号
String type                  // 展示类型
CodeInfoDto codeInfoDto      // 关联代码信息
```

**SqlInfoDto** (`com/aicode/agent/dto/chat/SqlInfoDto`):
```
String database              // 数据库名
String inputText             // 输入 SQL
String sourceId               // 数据源 ID
List<String> tables          // 涉及的表
```

#### 3.12.2 agent/dto 包 — 功能权限 DTO

**FunctionModelInfo** (`com/aicode/agent/dto/FunctionModelInfo`):
```
String permissionCode        // 权限代码
String permissionName        // 权限名称
String language              // 编程语言
List<CodeModel> codeModelList  // 代码模型列表
```

**CodeModel** (`com/aicode/agent/dto/CodeModel`):
```
String modelId               // 模型 ID
String modelCode             // 模型代码
String modelName             // 模型名称
boolean checked              // 是否选中
String originalModelName     // 原始模型名
boolean tokenExhausted       // Token 是否耗尽
```

#### 3.12.3 test/dto 包 — 单测领域 DTO

**FunctionDataDto** (`com/aicode/test/dto/FunctionDataDto`):
```
String functionName         // 函数名
String id                    // 标识
String xmlCase               // XML 用例
String methodContent         // 方法内容
String testContent           // 测试内容
String caseContent           // 用例内容
String caseInput             // 用例输入
String caseResult            // 用例结果
String unitTest              // 单元测试代码
String unitMock              // Mock 代码
```

**ChangeInfoDto** (`com/aicode/test/dto/ChangeInfoDto`):
```
Integer changeLine           // 变更行号
String content               // 变更内容
```

**UnitTestDto** (`com/aicode/test/dto/UnitTestDto`):
```
String tabName               // 标签名
String type                   // 类型
String language               // 语言
String level                  // 级别
String id                     // 标识
String packagePath            // 包路径
String absolutePath           // 绝对路径
String errMessage             // 错误信息
List<DataDTO> data            // 数据列表
```

**UnitTestDto$DataDTO**:
```
String className              // 类名
String operationTime          // 操作时间
String id                     // 标识
String language               // 语言
String path                   // 路径
String testClassAbsolutePath  // 测试类绝对路径
String testClasPath           // 测试类路径
String testClassName          // 测试类名
String structure              // 结构
String testFrame              // 测试框架
String mockFrame              // Mock 框架
boolean modifyTestFrame       // 是否修改测试框架
boolean testFrameAlert        // 测试框架告警
List<FunctionDataDTO> functionData  // 函数数据列表
String testTemplate           // 测试模板
String reason                 // 原因
String message                // 消息
```

**UnitTestDto$DataDTO$FunctionDataDTO$TemplateAttr**:
```
boolean staticMethod          // 是否静态方法
String className              // 类名
String methodName             // 方法名
String classPackage           // 类包名
TreeMap<String,String> prepareForTestImport  // 测试准备导入
Map<String,String> fieldClass  // 字段类映射
Set<String> methodImportClass  // 方法导入类集合
String template               // 模板名
```

### 3.13 跨包 DTO 关系图

```
    ┌──────────────────────────────────────────────────────────────────┐
    │                    单测生成数据流                                  │
    └──────────────────────────────────────────────────────────────────┘

    ┌──────────────┐        ┌──────────────────────┐
    │ FunctionModel│        │  UnitTestDto          │
    │ Info         │        │  ├── tabName          │
    │ ├──permission│        │  ├── type             │
    │ │  Code      │        │  └── data[]           │
    │ ├──permission│        │      └── DataDTO      │
    │ │  Name      │        │          ├──className │
    │ ├──language  │        │          ├──testFrame │
    │ └──codeModel│        │          ├──mockFrame │
    │    List[]    │        │          └──function  │
    │    └──Code  │        │             Data[]     │
    │      Model   │        │             └──Func   │
    │      ├──model│        │               DataDTO  │
    │      │  Id   │        │               ├──unit  │
    │      ├──model│        │               │  Test  │
    │      │  Code │        │               ├──unit  │
    │      └──model│        │               │  Mock  │
    │         Name │        │               ├──range │──→ CodeInfoDto
    └──────────────┘        │               │        │    $RangeDTO
                            │               └──templ │
    ┌──────────────┐        │                  ateAttr│
    │ CodeInfoDto  │        │                  ├──static│
    │ ├──content   │        │                  │Method │
    │ ├──range[]   │        │                  ├──class│
    │ │  └──Range  │        │                  │Name   │
    │ │    DTO     │        │                  ├──field│
    │ ├──fileName  │        │                  │Class  │
    │ ├──path      │        │                  └──templ│
    │ ├──language  │        │                     ate  │
    │ └──allContent│        └──────────────────────┘
    └──────────────┘
         │
         │ 关联
         v
    ┌──────────────┐        ┌──────────────────────┐
    │ Presentation │        │  CommentContext       │
    │ DataDto      │        │  ├── md5              │
    │ ├──line      │        │  └── methods[]        │
    │ ├──character │        │      └──CommentInfo   │
    │ ├──type      │        │         ├──name       │
    │ └──codeInfo  │        │         ├──textContext│
    │    Dto       │        │         ├──index      │
    └──────────────┘        │         ├──range      │
                            │         └──bodyRange  │
    ┌──────────────┐        └──────────────────────┘
    │ SqlInfoDto   │
    │ ├──database  │        ┌──────────────────────┐
    │ ├──inputText │        │  ChangeInfoDto        │
    │ ├──sourceId  │        │  ├── changeLine       │
    │ └──tables[]  │        │  └── content          │
    └──────────────┘        └──────────────────────┘
```

---

## 4. Template/FileLoader 包 — 文件模板加载系统

### 4.1 包总览

| 类名 | 源文件名 | 大小 | 职责 |
|------|----------|------|------|
| `FileTemplatesLoader` | FileTemplatesLoader.java | 18K | 模板文件加载入口 |
| `FTManager` | FTManager.java | 24K | 模板文件管理器（核心） |
| `UnitTemplateManager` | UnitTemplateManager.java | 21K | 单测模板管理器（单例） |
| `UnitTemplateManager$1` | UnitTemplateManager.java | 3.1K | 项目级模板方案 |
| `FileTemplateContext` | FileTemplateContext.java | 6.2K | 模板上下文 |
| `FileTemplateLoadResult` | FileTemplateLoadResult.java | 2.1K | 加载结果容器 |
| `UnitFileTemplate` | UnitFileTemplate.java | 3.5K | 单测文件模板 |
| `TemplateDescriptor` | TemplateDescriptor.java | 3.5K | 模板描述符 |
| `TemplateRegistry` | TemplateRegistry.java | 5.4K | 模板注册表 |
| `TemplateResourceLoader` | TemplateResourceLoader.java | 4.4K | Velocity 资源加载器 |
| `TemplateRole` | TemplateRole.java | 1.1K | 模板角色枚举 |

### 4.2 TemplateRole — 模板角色枚举

**路径**: `com/aicode/template/fileloader/TemplateRole`

**枚举值**:
```
Tester    // 测试模板
Included  // 包含模板（可被其他模板 include）
```

### 4.3 TemplateDescriptor — 模板描述符

**路径**: `com/aicode/template/fileloader/TemplateDescriptor`

**字段**:
```
String htmlDisplayName     // HTML 显示名（用于 UI）
String displayName         // 纯文本显示名
String tokenizedName       // 分词名（含框架信息）
String filename            // 文件名
TemplateRole templateRole  // 模板角色
String framework           // 测试框架（如 JUnit4, JUnit5）
String mockFramework       // Mock 框架（如 Mockito, Powermock）
String LANGUAGE_JAVA = "java"  // 语言常量
```

**构造函数逻辑**:
```
TemplateDescriptor(displayName, tokenizedName, filename, role)
  → htmlDisplayName = displayName
  → this.displayName = displayName
  → this.tokenizedName = tokenizedName
  → this.filename = filename
  → this.templateRole = role
  → 解析 tokenizedName:
    → 按 "&" 分割
    → 如果有两部分: framework = parts[0], mockFramework = parts[1].toLowerCase() + "java"
    → 如果只有一部分: framework = parts[0]
```

### 4.4 TemplateRegistry — 模板注册表

**路径**: `com/aicode/template/fileloader/TemplateRegistry`

**字段**:
```
static Logger LOG
static String TEMPLATE_FILE_SUFFIX
static List<TemplateDescriptor> templateDescriptors

// 预定义模板常量
static final String JUNIT4_JAVA_TEMPLATE = "JUnit4.java"
static final String JUNIT5_JAVA_TEMPLATE = "JUnit5.java"
static final String JUNIT4_MOCKITO_JAVA_TEMPLATE = "JUnit4&Mockito.java"
static final String JUNIT4_POWERMOCK_JAVA_TEMPLATE = "JUnit4&Powermock.java"
static final String JUNIT5_MOCKITO_JAVA_TEMPLATE = "JUnit5&Mockito.java"
static final String TESTNG_MOCKITO_JAVA_TEMPLATE = "TestNG&Mockito.java"
static final String SPRINGBOOTTEST_MOCKITO_JAVA_TEMPLATE = "SpringBootTest&Mockito.java"
```

**静态初始化注册的模板**:

| 显示名 | 文件名 | 框架 | Mock 框架 |
|--------|--------|------|-----------|
| JUnit4 | JUnit4.java | JUnit4 | (无) |
| JUnit5 | JUnit5.java | JUnit5 | (无) |
| JUnit4 & Mockito | JUnit4&Mockito.java | JUnit4 | Mockito |
| JUnit4 & Powermock | JUnit4&Powermock.java | JUnit4 | Powermock |
| JUnit5 & Mockito | JUnit5&Mockito.java | JUnit5 | Mockito |
| TestNG & Mockito | TestNG&Mockito.java | TestNG | Mockito |
| SpringBootTest & Mockito | SpringBootTest&Mockito.java | SpringBootTest | Mockito |

**方法**:
| 方法 | 用途 |
|------|------|
| `getTemplateDescriptors()` | 返回所有模板描述符 |
| `getEnabledTemplateDescriptors()` | 返回所有启用的模板 |
| `getEnabledTemplateDescriptor(String, String)` | 按框架+Mock框架查找模板 |

**getEnabledTemplateDescriptor 查找逻辑**:
```
1. 遍历所有启用的模板
2. 优先匹配: framework 匹配 AND mockFramework 匹配
3. 降级匹配: framework 匹配 OR mockFramework 匹配（仅返回第一个降级匹配）
4. isMatch 逻辑: 取框架名中 "." 前的部分进行忽略大小写比较
```

### 4.5 UnitFileTemplate — 单测文件模板

**路径**: `com/aicode/template/fileloader/UnitFileTemplate`
**继承**: `FileTemplateBase`（IntelliJ 内部类）

**字段**:
```
String name            // 模板名
String displayName     // 显示名
boolean isDefault      // 是否默认模板
String description     // 描述
String extension       // 扩展名
```

### 4.6 FileTemplateContext — 模板上下文

**路径**: `com/aicode/template/fileloader/FileTemplateContext`

**字段**:
```
FileTemplateDescriptor fileTemplateDescriptor  // 模板描述
final Project project                          // 项目
final String targetClass                       // 目标类名
final PsiPackage targetPackage                 // 目标包
final Module srcModule                         // 源码模块
final Module testModule                        // 测试模块
final PsiDirectory targetDirectory             // 目标目录
final PsiClass srcClass                        // 源码类
final FileTemplateConfig fileTemplateConfig    // 模板配置
final List<String> excludeMethodList           // 排除方法列表
final Set<PsiMethod> selectedMethods          // 选中的方法
final Boolean requestAi                        // 是否请求 AI
String filePath                                // 文件路径
```

**第二个构造函数额外参数**: `List<PsiMethod>` — 直接传入方法列表（会转换为 selectedMethods Set）

### 4.7 FileTemplateLoadResult — 加载结果

**路径**: `com/aicode/template/fileloader/FileTemplateLoadResult`

**字段**:
```
MultiMap<String, DefaultTemplate> result  // 按类别分组的默认模板
URL defaultTemplateDescription            // 模板描述文档 URL
URL defaultIncludeDescription             // 包含模板描述文档 URL
```

### 4.8 FileTemplatesLoader — 模板文件加载器

**路径**: `com/aicode/template/fileloader/FileTemplatesLoader`（包私有类）

**字段**:
```
static final Logger LOG
static final String TEMPLATES_DIR
private static final String DEFAULT_TEMPLATES_ROOT
private static final String DESCRIPTION_FILE_EXTENSION
private static final String DESCRIPTION_EXTENSION_SUFFIX
private static final String DEFAULT_TEMPLATE_DESCRIPTION_FILENAME
final FTManager myTestTemplatesManager     // 测试模板管理器
final FTManager myIncludesManager          // 包含模板管理器
final FTManager[] myAllManagers            // 所有管理器数组
private static final String TESTS_DIR
public static final String INCLUDES_DIR
private final URL myDefaultTemplateDescription
private final URL myDefaultIncludeDescription
```

**方法**:
| 方法 | 用途 |
|------|------|
| `getAllManagers()` | 返回所有 FTManager |
| `getInternalTestTemplatesManager()` | 获取内置测试模板管理器 |
| `getCustomTestTemplatesManager()` | 获取自定义测试模板管理器 |
| `getPatternsManager()` | 获取模式管理器 |
| `loadDefaultTemplates(List<String>)` | 从 classpath 加载默认模板 |
| `loadDefaultsFromRoot(URL, List<String>, FileTemplateLoadResult)` | 从指定根 URL 加载 |

### 4.9 FTManager — 模板文件管理器

**路径**: `com/aicode/template/fileloader/FTManager`

**字段**:
```
static final Logger LOG
static final String DEFAULT_TEMPLATE_EXTENSION
static final String TEMPLATE_EXTENSION_SUFFIX
static final String ENCODED_NAME_EXT_DELIMITER
final String myName                        // 管理器名称
final boolean myInternal                   // 是否内置
final Path myTemplatesDir                  // 模板目录路径
final FTManager myOriginal                // 原始管理器（用于自定义模板）
final Map<String, FileTemplateBase> myTemplates  // 模板映射
volatile List<FileTemplateBase> mySortedTemplates // 排序后的模板列表
final List<DefaultTemplate> myDefaultTemplates   // 默认模板列表
final TemplateRegistry templateRegistry    // 模板注册表
```

**方法**:
| 方法 | 用途 |
|------|------|
| `getName()` | 获取管理器名称 |
| `getAllTemplates(boolean)` | 获取所有模板（可包含默认） |
| `getTemplate(String)` | 按名获取模板 |
| `findTemplateByName(String)` | 按名查找模板 |
| `addTemplate(String, String)` | 添加模板 |
| `updateTemplates(Collection)` | 更新模板集合 |
| `setDefaultTemplates(Collection)` | 设置默认模板 |
| `loadCustomizedContent()` | 加载自定义模板内容 |
| `saveTemplates()` | 保存模板到磁盘 |
| `encodeFileName(String, String)` | 编码文件名 |
| `decodeFileName(String)` | 解码文件名 |

### 4.10 UnitTemplateManager — 单测模板管理器

**路径**: `com/aicode/template/fileloader/UnitTemplateManager`
**继承**: `FileTemplateManager`（IntelliJ 平台类）

**字段**:
```
static final Logger LOG
static final String TEST_TEMPLATES_CATEGORY = "tests"
final FileTemplatesLoader myFileTemplatesLoader  // 加载器
static volatile UnitTemplateManager instance      // 单例
final Project myProject                           // 项目
final FileTemplatesScheme myProjectScheme          // 项目方案
FileTemplatesScheme myScheme                       // 当前方案
boolean myInitialized                             // 是否已初始化
final TemplateRegistry templateRegistry            // 模板注册表
Date myTestDate                                    // 测试日期
```

**方法**:
| 方法 | 用途 |
|------|------|
| `getInstance(Project)` | 获取项目级单例 |
| `getDefaultInstance()` | 获取默认单例 |
| `getSettings()` | 获取 FileTemplatesLoader（懒加载） |
| `getCurrentScheme()` | 获取当前模板方案 |
| `checkInitialized()` | 确保已初始化 |
| `getTemplates(String)` | 按类别获取模板 |
| `getAllTemplates()` | 获取所有模板 |
| `getTemplate(String)` | 按名获取模板 |
| `addTemplate(String, String)` | 添加模板 |
| `removeTemplate(FileTemplate)` | 删除模板 |
| `getDefaultProperties()` | 获取默认属性（含日期格式化） |
| `getRecentNames()` | 获取最近使用的模板名 |
| `getInternalTemplates()` | 获取内置模板 |
| `getTestTemplates()` | 获取测试模板描述符列表 |
| `getInternalTemplate(String)` | 获取内置模板 |
| `findInternalTemplate(String)` | 查找内置模板 |
| `findCustomTestTemplate(String)` | 查找自定义测试模板 |
| `getCodeTemplate(String)` | 获取代码模板 |
| `getJ2eeTemplate(String)` | 获取 J2EE 模板 |
| `getDefaultTemplate(String)` | 获取默认模板 |
| `setTemplates(String, Collection)` | 设置模板 |
| `saveAllTemplates()` | 保存所有模板 |
| `getDefaultTemplateDescription()` | 获取默认模板描述 |
| `getDefaultIncludeDescription()` | 获取默认包含描述 |

**getTestTemplates() 逻辑**:
```
1. 获取所有模板
2. 过滤非默认模板
3. 对每个模板，从 TemplateRegistry 查找对应的 TemplateDescriptor
4. 返回描述符列表
```

### 4.11 UnitTemplateManager$1 — 项目级模板方案

**路径**: `com/aicode/template/fileloader/UnitTemplateManager$1`
**继承**: `FileTemplatesScheme`

**方法**:
| 方法 | 用途 |
|------|------|
| `getTemplatesDir()` | 返回项目级模板目录 |
| `getProject()` | 返回关联项目 |

### 4.12 TemplateResourceLoader — Velocity 资源加载器

**路径**: `com/aicode/template/fileloader/TemplateResourceLoader`
**继承**: `org.apache.velocity.runtime.resource.loader.ResourceLoader`

**方法**:
| 方法 | 用途 |
|------|------|
| `init(ExtendedProperties)` | 初始化（兼容旧版 Velocity） |
| `init(ExtProperties)` | 初始化（新版 Velocity） |
| `getResourceReader(String, String)` | 获取模板 Reader |
| `getResourceStream(String)` | 获取模板 InputStream |
| `isSourceModified(Resource)` | 检查模板是否修改 |
| `getLastModified(Resource)` | 获取最后修改时间 |

**资源加载逻辑**:
```
1. 从 UnitTemplateManager 获取模板名对应的 FileTemplate
2. 如果找到 → 返回模板内容的 InputStream
3. 如果未找到 → 抛出 ResourceNotFoundException
```

### 4.13 FileTemplateConfig — 模板配置

**路径**: `com/aicode/template/FileTemplateConfig`

**字段**:
```
static final int DEFAULT_MAX_RECURSION_DEPTH
int maxRecursionDepth                      // 最大递归深度
boolean reformatCode                       // 是否重新格式化代码
boolean replaceFqn                          // 是否替换全限定名
boolean optimizeImports                    // 是否优化导入
boolean stubMockMethodCallsReturnValues    // 是否 stub Mock 方法返回值
boolean ignoreUnusedProperties            // 是否忽略未使用属性
boolean replaceInterfaceParamsWithConcreteTypes  // 是否用具体类型替换接口参数
int maxNumOfConcreteCandidatesToReplaceInterfaceParam  // 替换接口参数的最大候选数
int minPercentOfExcessiveSettersToPreferMapCtor  // 偏好 Map 构造器的最小 setter 百分比
int minPercentOfInteractionWithPropertiesToTriggerConstructorOptimization  // 触发构造器优化的最小交互百分比
boolean generateTestsForInternalMethods    // 是否为内部方法生成测试
boolean renderInternalMethodCallStubs      // 是否渲染内部方法调用 stub
boolean throwSpecificExceptionTypes        // 是否抛出特定异常类型
```

**构造函数**:
```
FileTemplateConfig(boolean reformatCode, boolean optimizeImports, boolean replaceFqn)
  → 创建默认配置（maxRecursionDepth=3, 其余按参数）
```

### 4.14 CacheFileTemplate — 缓存文件模板

**路径**: `com/aicode/template/generator/CacheFileTemplate`

**字段**:
```
Map<String, Object> paramMaps                  // 模板参数映射
FileTemplateContext context                    // 模板上下文
PsiDirectory targetDirectory                  // 目标目录
GeneratorFileConfig generatorFileConfig       // 生成器文件配置
MethodGeneratorConfig methodGeneratorConfig    // 方法生成器配置
List<MessageDto> messageDtos                   // 消息列表
```

### 4.15 文件模板加载流程图

```
    ┌─────────────────────────────────────────────────────────────────┐
    │                    模板加载与注册流程                              │
    └─────────────────────────────────────────────────────────────────┘

    ┌───────────────────────┐
    │  UnitTemplateManager  │ ← 单例 (getInstance)
    │  .getInstance(project)│
    └───────────┬───────────┘
                │
                v
    ┌───────────────────────┐        ┌──────────────────────────────┐
    │  FileTemplatesLoader │        │     TemplateRegistry          │
    │  (包私有, 构造时加载) │        │  (静态初始化, 7 个预定义模板)  │
    └───────────┬───────────┘        └──────────┬───────────────────┘
                │                               │
                v                               │
    ┌───────────────────────┐                   │
    │  loadDefaultTemplates │                   │
    │  (从 classpath 加载)   │                   │
    └───────────┬───────────┘                   │
                │                               │
    ┌───────────┴───────────┐                   │
    │                       │                   │
    v                       v                   │
    ┌──────────────┐ ┌──────────────┐            │
    │  FTManager   │ │  FTManager   │            │
    │  (tests)     │ │  (includes)  │            │
    └──────┬───────┘ └──────────────┘            │
           │                                    │
           v                                    │
    ┌──────────────────────┐                    │
    │  createAndStore      │                    │
    │  BundledTemplate     │                    │
    │  (每个 DefaultTemplate│                    │
    │   创建 UnitFileTemplate)                   │
    └──────────┬───────────┘                    │
               │                                │
               v                                v
    ┌──────────────────────────────────────────────────────┐
    │              UnitFileTemplate                         │
    │  ├── name = "JUnit4&Mockito"                         │
    │  ├── displayName = "JUnit4 & Mockito"               │
    │  ├── isDefault = true                                │
    │  ├── extension = "java"                               │
    │  └── text = (从 .ft 文件加载)                          │
    └──────────────────────────────────────────────────────┘

    ══════════════════════════════════════════════════════════

    模板渲染流程:
    ─────────────

    ┌───────────────────┐
    │ FileTemplateContext│ ← 包含 project, srcClass, methods, config
    └────────┬──────────┘
             │
             v
    ┌───────────────────┐
    │  CacheFileTemplate│ ← 包含 paramMaps, context, config
    └────────┬──────────┘
             │
             v
    ┌───────────────────────────────────────────────┐
    │  VelocityInitializer + TemplateResourceLoader │
    │  → Velocity 引擎渲染模板                       │
    │  → 从 UnitTemplateManager 获取模板内容          │
    └────────┬──────────────────────────────────────┘
             │
             v
    ┌───────────────────┐
    │  生成的测试代码    │
    └───────────────────┘
```

### 4.16 模板注册表完整模板列表

| # | 显示名 | 文件名 | 框架 | Mock 框架 | 角色 |
|---|--------|--------|------|-----------|------|
| 1 | JUnit4 | JUnit4.java | JUnit4 | (无) | Tester |
| 2 | JUnit5 | JUnit5.java | JUnit5 | (无) | Tester |
| 3 | JUnit4 & Mockito | JUnit4&Mockito.java | JUnit4 | Mockito | Tester |
| 4 | JUnit4 & Powermock | JUnit4&Powermock.java | JUnit4 | Powermock | Tester |
| 5 | JUnit5 & Mockito | JUnit5&Mockito.java | JUnit5 | Mockito | Tester |
| 6 | TestNG & Mockito | TestNG&Mockito.java | TestNG | Mockito | Tester |
| 7 | SpringBootTest & Mockito | SpringBootTest&Mockito.java | SpringBootTest | Mockito | Tester |

---

## 5. 三个包的协作关系

```
    ┌──────────────────────────────────────────────────────────────────────┐
    │                        iFlyCode 插件架构                              │
    └──────────────────────────────────────────────────────────────────────┘

    ┌─────────────────┐     ┌──────────────────────┐     ┌──────────────────┐
    │   Domain 包     │     │   Updater 包          │     │  FileLoader 包   │
    │                 │     │                      │     │                  │
    │ Position ───────┼────→│ PluginUpdater        │     │ UnitTemplate     │
    │ Range    ───────┼────→│ (使用 Position/Range │     │ Manager          │
    │ LineInfo ───────┼─┐   │  定位更新通知位置)    │     │   ↓              │
    │ VirtualFileUri  │ │   │                      │     │ FTManager        │
    │   ↓            │ │   │ CheckService          │     │   ↓              │
    │ GetTipsResult  │ │   │ (定时检查更新)        │     │ TemplateRegistry │
    │   ↓            │ │   │                      │     │   ↓              │
    │ Suggestion     │ │   │ UpdaterChecker        │     │ FileTemplate     │
    │   ↓            │ │   │ (版本分发)            │     │ Context          │
    │ CommandCache   │ │   │                      │     │   ↓              │
    │                 │ │   └──────────────────────┘     │ CacheFile        │
    └─────────────────┘ │                                │ Template         │
                        │                                └──────────────────┘
                        │
                        v
    ┌─────────────────────────────────────────────────────────────────────┐
    │                     跨包 DTO 协作                                    │
    │                                                                     │
    │  CodeInfoDto ←── PresentationDataDto (行内聊天代码定位)              │
    │  CommentContext ←── CommentInfo (函数注释生成)                      │
    │  FunctionModelInfo ←── CodeModel (功能权限+模型选择)                │
    │  UnitTestDto ←── FunctionDataDto ←── TemplateAttr (单测生成)        │
    │  SqlInfoDto (SQL 智能提示)                                          │
    │  ChangeInfoDto (代码变更追踪)                                        │
    └─────────────────────────────────────────────────────────────────────┘
```

---

## 6. 关键发现与安全分析

### 6.1 更新系统安全特征

1. **反射绕过 API 兼容性**: 两个 `UpdaterChecker` 均通过反射调用 IntelliJ 内部 API，规避了版本兼容性限制，但也绕过了 IntelliJ 的插件 API 稳定性检查。

2. **动态插件禁用/启用**: `disableOrEnablePlugin` 通过反射调用 `PluginEnabler`（内部类），实现了不重启 IDE 即可禁用/启用插件的能力。

3. **重启安装脚本**: `installAfterRestart` 使用 `StartupActionScriptManager` 注册 Delete/Copy/Unzip 命令，在 IDE 重启时执行文件操作，这是 IntelliJ 官方的插件安装机制。

4. **无 MD5 客户端校验**: 虽然登录响应包含 `md5` 字段，但 `doUpdate` 方法中未发现客户端 MD5 校验逻辑，仅通过文件大小比较判断是否需要重新复制。

5. **SaaS 场景跳过更新**: `checkUpdate` 方法在 `PluginSceneEnum.saasScene()` 为 true 时直接返回，说明 SaaS 部署模式有独立的更新机制。

### 6.2 领域模型设计特征

1. **LSP 风格坐标系统**: `Position` 和 `Range` 采用 LSP (Language Server Protocol) 风格的 line/character 坐标，便于与服务端 AI 引擎交互。

2. **不可变值对象**: `LineInfo`、`Suggestion`、`GetTipsResult$Tip` 均为不可变类（final 字段），符合函数式编程范式。

3. **Gson 序列化集成**: `VirtualFileUri$TypeAdapter` 实现了自定义 Gson 序列化，确保文件 URI 在 JSON 通信中的正确表示。

4. **深层嵌套 DTO**: `UnitTestDto` 包含 4 层嵌套（Dto → DataDTO → FunctionDataDTO → TemplateAttr/Data/CodeList），反映了单测生成流程的复杂性。

### 6.3 模板系统设计特征

1. **双管理器架构**: `FTManager` 负责文件系统层面的模板 CRUD，`UnitTemplateManager` 负责业务层面的模板查询和渲染。

2. **7 种预定义模板**: 覆盖了 Java 生态主流测试框架组合（JUnit4/5 + Mockito/Powermock，TestNG + Mockito，SpringBootTest + Mockito）。

3. **Velocity 引擎集成**: `TemplateResourceLoader` 将 IntelliJ 的 `FileTemplate` 适配为 Velocity 的 `ResourceLoader`，实现了模板引擎的无缝集成。

4. **项目级模板方案**: `UnitTemplateManager$1` 实现了项目级模板目录，允许不同项目使用不同的自定义模板。