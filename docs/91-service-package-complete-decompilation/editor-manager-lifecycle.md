## 三、EditorManagerService 实现分析

### 服务注册

```
EditorManagerService (接口)
    ↓ implements
EditorManagerServiceImpl (应用级服务)
    ↓ 注册方式
ApplicationManager.getApplication().getService(EditorManagerService.class)
```

### 核心方法调用链

```
用户输入 → DocumentActionTracker$ActionListener.afterActionPerformed()
    → EditorManagerService.editorChanged(editor, Forced, false)
        → EditorManagerServiceImpl.editorChanged(editor, offset, type, forced)
            → RequestTipService.createRequest(editor, offset, type)
            → RequestTipService.fetchCachedTips(request)  // 尝试缓存
            → RequestTipService.fetchTips(request, subscriber, ...)
                → Agent 服务请求
                → RequestTipServiceImpl.ib()  // 处理响应
                    → AgentCodeTip.FromString()
                    → CodeTipUtil.createEditorCodeTip()
                    → AgentCodeTipList 包装
                    → SubmissionPublisher.submit()
                        → EditorManagerServiceImpl$F.KB()  // onNext
                            → mc(request, editor) 验证
                            → Xc(editor, inlays) 检查重复
                            → TipReceivedMessage.TOPIC 发布
                            → NC() 渲染 Inlay
```

---

## 四、补全请求生命周期

### 阶段 1: 请求创建

```
EditorManagerServiceImpl.editorChanged(editor, offset, type, forced)
    ↓
RequestTipServiceImpl.createRequest(editor, offset, type)
    ↓ 创建 EditorRequestService 实例
    ↓ 设置 offset, completionType, documentContent, fileName 等
    ↓
EditorUtil.addEditorRequest(editor, request)  // 绑定到编辑器生命周期
```

### 阶段 2: 缓存检查

```
RequestTipServiceImpl.fetchCachedTips(request)
    ↓
TipCache.isLatestPrefix(prefix)  // 增量补全优化
    ↓ 如果命中
TipCache.getLatest(key)
    ↓ 返回缓存的 CodeInlayList
```

### 阶段 3: 网络请求

```
RequestTipServiceImpl.fetchTips(request, subscriber, ...)
    ↓ 创建 SubmissionPublisher<List&lt;CodeInlayList&gt;>
    ↓ subscriber.onSubscribe(subscription)
    ↓ 调用 Agent 服务 (CodeCompleteService / AgentService)
    ↓ 响应到达
    ↓
RequestTipServiceImpl.ib(requestId, lines, request, subscriber, data)
    ↓ 逐行处理:
    ↓   1. 替换 Tab -> 空格
    ↓   2. 去除前导空白（如果前缀末行非空）
    ↓   3. stripTrailing
    ↓   4. AgentCodeTip.FromString()
    ↓   5. replaceLeadingTabs()
    ↓   6. setRequestId, setScene, setLanguage
    ↓   7. CodeTipUtil.createEditorCodeTip()
    ↓   8. 包装为 AgentCodeTipList
    ↓ 提交到 publisher
    ↓ publisher.close()
```

### 阶段 4: 结果渲染

```
EditorManagerServiceImpl$F.KB(inlays)
    ↓ mc(request, editor) 验证请求有效性
    ↓ enum.compareAndSet(false, true) 防重入
    ↓ 重置 KEY_LAST_REQUEST 中的 RequestResultList
    ↓ Xc(editor, inlays) 检查是否已有同偏移 Inlay
    ↓ 如果没有: subscription.request(1)
    ↓ 发布 TipReceivedMessage.TOPIC
    ↓ 如果有 Consumer 回调且数据已结束:
    ↓   invokeLater -> callback.accept(inlayList)
    ↓
NC(inlayList, request, editor, forced, action)
    ↓ 如果 forced: disposeTips(editor, action)
    ↓ 获取 InlayModel
    ↓ 遍历 CodeEditorInlay:
    ↓   跳过 isEmptyTip()
    ↓   处理 replacementRange:
    ↓     matchSuffixSection 分割
    ↓     创建 TipInlayRenderer
    ↓   按 CodeTipType 分派:
    ↓     Inline: addInlineElement()
    ↓     AfterLineEnd: addAfterLineEndElement()
    ↓     Block: addBlockElement()
```

### 阶段 5: 补全接受

```
用户 Tab/Enter → EditorManagerServiceImpl.acceptTip(editor)
    ↓ 获取当前 Inlay
    ↓ 获取 TipRenderer
    ↓ 获取 contentLines
    ↓ 插入文本到文档
    ↓ disposeTips(editor, Accept)
```

---

## 五、Inlay 渲染管线

### 渲染架构

```
TipInlayRenderer (实现 TipRenderer + EditorCustomElementRenderer)
    ↓ calcWidthInPixels()
        → InlayRendering.getWidth(this, editor)
    ↓ paint()
        → InlayRendering.paint(g, this, r, editor)

InlayRendering (纯静态工具类)
    ↓ paint():
        1. 获取 TextAttributes
        2. PB() — 绘制圆角矩形背景
        3. 遍历 contentLines:
           a. drawString() — 绘制文本
           b. ic() — 绘制效果线
        4. Block 类型处理多行换行
```

### 三种 Inlay 类型

| CodeTipType | InlayModel 方法 | 用途 |
|---|---|---|
| Inline | `addInlineElement(offset, true, MAX_INT-priority, renderer)` |行内补全（灰色幽灵文本） |
| AfterLineEnd | `addAfterLineEndElement(offset, true, renderer)` | 行尾补全 |
| Block | `addBlockElement(offset, true, false, MAX_INT-priority, renderer)` | 块级补全（多行） |

### 文本效果绘制

| EffectType | EffectPainter2D 常量 | 视觉效果 |
|---|---|---|
| LINE_UNDERSCORE | LINE_UNDERSCORE | 细下划线 |
| BOLD_LINE_UNDERSCORE | BOLD_LINE_UNDERSCORE | 粗下划线 |
| STRIKEOUT | STRIKEOUT | 删除线 |
| WAVE_UNDERSCORE | WAVE_UNDERSCORE | 波浪线 |
| BOLD_DOTTED_LINE | BOLD_DOTTED_LINE | 粗点线 |

---
