## 六、与 Agent/Service 层的交互

### 服务依赖图

```
EditorManagerServiceImpl
    ├── RequestTipService.getInstance()
    │   ├── createRequest() → EditorRequestService
    │   ├── fetchCachedTips() → TipCache
    │   ├── fetchTips() → Agent 服务
    │   └── dealStreamAgentTips() → 流式 Agent
    ├── CancelRequestTip.requestAlarm
    │   └── cancelAllAndAddRequest() → 防抖延迟
    ├── DocumentActionTracker.getInstance()
    │   └── ActionListener → AnActionListener
    ├── EditorUtil
    │   ├── isSelectedEditor()
    │   ├── isFocusedEditor()
    │   ├── addEditorRequest()
    │   └── getDocumentModificationStamp()
    ├── TipReceivedMessage.TOPIC → MessageBus
    ├── RequestsCancelledService.TOPIC → MessageBus
    ├── RejectTipMessage.TOPIC → MessageBus
    ├── AICodeSettingsState.enableCodeComplete
    ├── PluginStartupActivity.getApiKey()
    └── AICodeRequestSettings.isShowIdeCodeTips()
```

### MessageBus 通信

| 主题 | 发布者 | 订阅者 | 事件 |
|---|---|---|---|
| TipReceivedMessage.TOPIC | EditorManagerServiceImpl$F | 补全结果消费者 | 补全结果到达 |
| RequestsCancelledService.TOPIC | EditorManagerServiceImpl | 请求取消监听 | 请求被取消 |
| RejectTipMessage.TOPIC | EditorManagerServiceImpl | 补全拒绝监听 | 自动补全被拒绝 |
| ProcessStatusListener.TOPIC | Agent 进程管理 | Agent 重启监听 | Agent 进程重启 |

### H() 解混淆调用汇总

| 类 | H() 解码器 | 用途 |
|---|---|---|
| EditorManagerService | RequestTimeoutException.H, OpenTelemetryUtil.H | 字符串常量解混淆 |
| EditorSupport | AICodeStringUtil.H, ChatInputController.H | 扩展点名称、方法名 |
| EditorRequestService | NewFileUtils.H, InlineChatStatusServiceKt.H | 空检查错误消息 |
| TipReceivedMessage | ConditionalActionConfiguration.H | Topic 显示名 |
| RequestsCancelledService | Maps.H | Topic 显示名 |
| RejectTipMessage | Maps.H | Topic 显示名 |
| LanguageInfoSupport | OpenTelemetryUtil.H | 扩展点名称 |
| EditorManagerServiceImpl | EditorUtils.H, Content.util.EditorUtils.H | 日志消息、操作 ID |
| RequestTipServiceImpl | RequestCancelException.H, CodeCompleteService.H | 错误消息、状态通知 |
| CancelRequestTip | GitReviewService.H, FileExtensionLanguageDetails.H | 错误消息 |
| DocumentActionTracker | JComponentKt.H, IdeAction.H | 操作类型判断 |
| DocumentActionTracker$ActionListener | FileInfo.H, EditorUtils.H | 日志消息 |
| EditorUtil | RequestCancelException.H, JComponentKt.H | Key 名称 |
| AgentCodeTipList | RequestCancelException.H, CodeCompleteService.H | 空检查错误消息 |
| CodeTipTypedHandlerDelegate | PropertyUtils.H, Maps.H | 设置检查消息 |
| TipTypedHandlerDelegate | RequestTimeoutException.H, ChatInputController.H | 括号字符判断 |
| RequestResultList | GeneratorConfig.H, ConditionalActionConfiguration.H | 错误消息 |
| BizResponse | ConditionalActionConfiguration.H, NewFileUtils.H | 成功码 "0" |

---

## 七、类关系总图

```
                    ┌──────────────────────┐
                    │  EditorManagerService │ (接口)
                    │  extends Disposable   │
                    └──────────┬───────────┘
                               │ implements
                    ┌──────────▼───────────┐
                    │EditorManagerServiceImpl│
                    │  - requestAlarm       │
                    │  - docChangeCount      │
                    │  - keyMap             │
                    └──┬──────┬──────────┬──┘
                       │      │          │
            ┌──────────┘      │          │
            │                  │          │
   ┌────────▼──────┐  ┌───────▼──────┐  ┌▼──────────────────┐
   │RequestTipService│  │TipRenderer   │  │DocumentActionTracker│
   │  (接口)        │  │  (接口)      │  │  + ActionListener   │
   └───────┬──────┘  └───────┬──────┘  └────────────────────┘
           │implements        │implements
   ┌───────▼──────┐  ┌───────▼──────────┐
   │RequestTipServiceImpl│  │TipInlayRenderer  │
   │  - cache           │  │  - contentLines  │
   │  - CODE_TIP_MAP    │  │  - type          │
   └───────┬──────┘  └───────┬──────────┘
           │                 │ delegates to
           │         ┌───────▼──────────┐
           │         │ InlayRendering    │
           │         │  (静态工具类)     │
           │         └──────────────────┘
           │
    ┌──────▼──────────┐
    │EditorRequestService│ (接口)
    │ extends RequestCancellable │
    └──────┬──────────┘
           │ used by
    ┌──────▼──────────┐     ┌──────────────────┐
    │RequestResultList │────▶│CodeInlayList      │
    │  - inlayLists    │     │  (接口)           │
    │  - index         │     └───────┬──────────┘
    │  - inlayLock     │             │ implements
    └─────────────────┘     ┌───────▼──────────┐
                            │AgentCodeTipList  │
                            │  - delegate      │
                            │  - agentCodeTip  │
                            └──────────────────┘

    ┌──────────────────┐     ┌──────────────────┐
    │CancelRequestTip  │     │EditorUtil         │
    │  - byte (lock)   │     │  - enum (Key)     │
    │  - enum (Alarm)  │     │  + isSelectedEditor│
    └──────────────────┘     └──────────────────┘

    ┌──────────────────┐     ┌──────────────────┐
    │CodeTipTypedHandler│     │TipTypedHandler    │
    │ Delegate         │     │Delegate           │
    │  checkAutoPopup  │     │  beforeCharTyped   │
    └──────────────────┘     └──────────────────┘

    ┌──────────────────┐     ┌──────────────────┐
    │BizResponse&lt;T&gt;    │     │ProjectService     │
    │  - float (obj)   │     │  flushLib()       │
    │  - byte (resCode)│     └──────────────────┘
    │  - enum (msg)    │
    └──────────────────┘
```

---

## 八、关键发现

1. **Reactive Flow 架构**: 补全请求使用 `Flow.Subscriber` / `SubmissionPublisher` 实现流式响应，支持增量渲染
2. **防抖机制**: `CancelRequestTip` 使用 IntelliJ `Alarm` 实现请求防抖，避免频繁触发
3. **文档序列号验证**: `mc()` 方法通过 `DocumentEx.getModificationSequence()` 确保请求不过期
4. **三种 Inlay 渲染模式**: Inline（行内灰色文本）、AfterLineEnd（行尾）、Block（块级多行）
5. **Type-over 优化**: `TipTypedHandlerDelegate` 处理闭合字符输入时的补全接受
6. **IDE 补全冲突解决**: `CodeTipTypedHandlerDelegate` 在有 AI 缓存时阻止 IDE 原生自动补全
7. **Delete/Backspace 触发强制补全**: `DocumentActionTracker` 监听删除操作后自动触发 `Forced` 类型补全
8. **Undo 特殊处理**: Undo 操作委托给 `InlineChatService.handleUndoAction()`
9. **多候选循环**: `RequestResultList` 支持 wrap-around 索引实现 Alt+/ 循环切换
10. **字符串解混淆**: 所有类使用不同的 H() 解码器，`CancelRequestTip.H()` 和 `RequestResultList.H()` 使用调用栈信息作为 XOR 密钥