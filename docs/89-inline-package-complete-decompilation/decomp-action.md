### 2. inline/action/ -- Action Classes

#### 2.1 CloseInlineChatAction (qg)

**Extends:** PluginAnAction

**Fields:**
- `enum`: Editor -- target editor

**actionPerformed():**
1. Calls `InlineChatService.closeInlineChat(editor)`

#### 2.2 OpenInlineChatAction (yn)

**Extends:** PluginAnAction

**Companion Methods:**
- `register()`: Registers the action with ActionManager
- `addActionShortcut(String, String, Map)`: Adds keyboard shortcut
- `getRegisteredAction()`: Returns the registered AnAction

**actionPerformed():**
1. Gets editor from event
2. Calls `InlineChatService.openInlineChat(editor)`

#### 2.3 SendMessageAction (ub)

**Extends:** PluginAnAction

**actionPerformed():**
1. Gets InlineChatInputPanel from editor
2. Calls inputPanel's submit method

#### 2.4 StopAction (xl)

**Extends:** PluginAnAction

**actionPerformed():**
1. Gets InlineChatInfo by editor
2. Calls `InlineChatService.cleanRender(editor)`

---

### 3. inline/action/operate/ -- Operation Actions

#### 3.1 InlineChatAction (pf) -- Base Class

**Extends:** PluginAnAction, implements DumbAware

```
handle(Editor): Abstract -- overridden by subclasses
actionPerformed(AnActionEvent):
  1. InlineChatInlay.disposeInlay()
  2. Get editor from event data
  3. Call handle(editor)
  4. Catch Throwable silently
```

#### 3.2 InlineChatAcceptAction (cn)

```
handle(Editor):
  1. EditorKt.removeEditor(editor) -- removes markup highlights
  2. EditorKt.closeButtonPanel(editor) -- removes button panel inlay
```

#### 3.3 InlineChatRejectAction (kl)

```
handle(Editor):
  1. InlineChatService.cleanLastData(editor) -- reverts all changes
```

#### 3.4 InlineChatRetryAction (ce)

```
handle(Editor):
  1. Get InlineChatInfo by editor
  2. If sessionController and editor exist:
     sessionController.doRetry(editor)
```

#### 3.5 InlineChatStopAction (ak)

```
handle(Editor):
  1. Get InlineChatInfo by editor
  2. If step is LOADING or CATEGORY, proceed
  3. Otherwise return (cannot stop in other states)
  4. InlineChatService.cleanRender(editor)
```

#### 3.6 InlineChatUndoAction (oe)

```
handle(Editor):
  1. InlineChatService.handleUndoAction(editor)
```

---

### 4. inline/controller/ -- Session Controllers

#### 4.1 SessionController (rg) -- Abstract Base

**Extends:** Disposable

**Fields (obfuscated):**
- `catch`: int -- start offset
- `const`: boolean -- has selection
- `false`: volatile InlineChatStepEnum -- current step
- `do`: Editor -- associated editor
- `break`: int -- end offset
- `class`: String -- tip text
- `true`: String -- original select text
- `this`: int -- care offset
- `else`: static Logger
- `char`: int -- insert start offset
- `int`: RangeHighlighter -- range highlighter
- `new`: Inlay<?> -- default inlay
- `long`: InlineChatOperateEnum -- operate mode
- `super`: int -- change length
- `for`: int -- handle offset
- `stop`: volatile boolean -- stop flag
- `if`: String -- line indent
- `case`: int -- default offset
- `final`: Map<Integer, RangeHighlighter> -- to-handle range highlighters
- `try`: InlineChatCategoryEnum -- category
- `float`: static boolean -- accept flag
- `byte`: int -- end line number
- `enum`: int -- line bre block

**Key Methods:**

```
sendMessage(ChatMessage, Editor) [final]
  1. Calls executeRequest(message, editor)

executeRequest(ChatMessage, Editor) [final]
  1. Creates MessageDto with UUID
  2. Sets command based on category (INLINECHAT for generate, etc.)
  3. Sets path, content, range from editor
  4. Sets inlineChatVersion
  5. Sends via PluginWebsocketClient.sendWsMessage()

doAccept(Editor)
  1. Removes range highlighters
  2. Clears toHandle range highlighter map
  3. Sets step to SUCCESS

doReject(Editor)
  1. Calls clear(editor) to revert changes

doRetry(Editor)
  1. Resets step
  2. Re-sends the last message

doStop(Editor)
  1. Sets stop flag to true
  2. Cleans up rendering

doCancel(Editor)
  1. If step is CATEGORY, cancel category selection
  2. Otherwise cancel current operation

doCancelCategory(Editor)
  1. Runs in write action
  2. Removes category panel inlay
  3. Resets state

doErrorRetry(Editor)
  1. Retry after error

errorStop(Editor)
  1. Stop on error

handleUndo(Editor)
  1. Revert document changes
  2. Remove highlights

handleOperation(Editor, CommandEnum)
  1. Routes to appropriate handler based on CommandEnum
  2. ACCEPT, REJECT, RETRY, STOP, CANCEL, CANCEL_CATEGORY

renderCategoryPanel()
  1. Creates InlineChatCategoryPanelRenderer
  2. Creates inlay for category selection

renderStopPanel()
  1. Creates InlineChatStopPanelRenderer
  2. Creates inlay for stop button

renderFunButtons(int, Editor)
  1. Creates InlineChatBtnPanelRenderer
  2. Creates inlay for accept/retry/undo buttons

renderErrorFunButtons(int, Editor, String)
  1. Creates InlineChatErrorPanelRenderer
  2. Creates inlay for error + retry buttons
```

**Internal Methods:**
- `kB()`: Gets code range DTOs from editor
- `YB()`, `ua()`, `rA()`, `VA()`, `JB()`, `MB()`, `NA()`, `nA()`, `BA()`, `Ea()`, `Zc()`, `Qa()`, `nc()`: Various state management
- `fB()`: Sets operate enum and updates highlights
- `ba()`: Applies range highlight with operate-specific coloring
- `vB()`: Synchronized cleanup
- `lB()`, `CA()`, `gC()`: Static editor utilities
- `cC()`, `zb()`, `Xa()`, `tB()`, `Bc()`, `sC()`, `KA()`, `Vb()`: Kotlin Unit-returning helpers

#### 4.2 EphemeralChatSessionController (ji)

**Extends:** SessionController

**Fields:**
- `byte`: AtomicBoolean -- session lock flag
- `enum`: InlineChatInputPanel -- parent input panel

**Key Methods:**

```
lockSession(): Sets lock flag to true
unlockSession(): Sets lock flag to false
Qb(): Returns session locked state (Boolean)
dispose(): Cleanup
```

#### 4.3 ChatInputController (gm)

**Fields:**
- `float`: JBTextArea -- text area reference
- `byte`: Function1<String, Unit> -- submit callback
- `enum`: String -- current input text

**Key Methods:**

```
submit(): Calls callback with current text
stop(): No-op (placeholder)
updateInput(String): Sets text in text area
H(Object): Deobfuscation helper (static)
```

---
