# 83. Listener 完整反编译分析

## 1. Listener 类完整清单

| # | 类名 | 实现接口 | 行数 | 角色 |
|---|------|---------|------|------|
| 1 | `AICodeUnloadPluginListener` | `DynamicPluginListener` | 6.1k | 插件卸载处理 |
| 2 | `ApplicationStartupListener` | `AppLifecycleListener` | 2.3k | 应用生命周期 |
| 3 | `AutoCodeGenerateListener` | `CommandListener` | 55k | 代码补全命令监听核心 |
| 4 | `AutoCodeGenerateListener$Q` | (data class) | 727b | 编辑器位置+修改序列快照 |
| 5 | `AutoCodeGenerateListener$T` | (data class) | 6.0k | 编辑器+文档修改序列快照 |
| 6 | `CodeEditorListener` | `EditorFactoryListener` | 5.3k | 编辑器工厂事件 |
| 7 | `CodeEditorListener$CodeSelectionListener` | `SelectionListener` | 4.7k | 文本选择变更 |
| 8 | `CodeFileEditorManagerListener` | `FileEditorManagerListener` | 30k | 文件编辑器事件核心 |
| 9 | `CodeFileEditorManagerListener$01` | `DocumentListener` | 13k | 文档变更监听 |
| 10 | `CodeLookupManagerListener` | `LookupManagerListener` | 8.1k | 代码补全弹窗事件 |
| 11 | `CodeLookupManagerListener$01` | `LookupListener` | 14k | 补全项选择事件 |
| 12 | `CommitHandlerFactory` | `CheckinHandlerFactory` | 3.9k | VCS 提交处理工厂 |
| 13 | `CommitHandlerFactory$o` | `CheckinHandler` | 37k | 提交处理实现(单元测试收集) |
| 14 | `FileWatchedAdapter` | `FileDocumentManagerListener` | 5.6k | 文件保存监听 |
| 15 | `GitBranchChangeListener` | (plain class + MessageBus) | 79k | Git 分支变更核心 |
| 16 | `GitBranchChangeListener$H` | `NotificationAction` | 5.8k | "忽略授权"通知动作 |
| 17 | `GitBranchChangeListener$R` | `NotificationAction` | 7.0k | "打开知识库"通知动作 |
| 18 | `GitBranchChangeListener$b` | `NotificationAction` | 5.8k | "授权仓库"通知动作 |
| 19 | `PluginDocumentListener` | `ProjectComponent` | 4.9k | 项目文档生命周期 |
| 20 | `PluginManagerListener` | `ProjectManagerListener` | 10k | 项目关闭处理 |
| 21 | `ThemeChangeListener` | `ApplicationComponent` | 18k | IDE 主题变更 |

**总计**: 21 个类, 约 6278 行字节码

---

> **本文档已拆分为以下子页面：**

- [Listener清单与应用级监听器](listener-inventory-and-decomp.md)
- [事件订阅关系与调用链](event-subscriptions.md)
- [Service交互与关键发现](service-interaction.md)
