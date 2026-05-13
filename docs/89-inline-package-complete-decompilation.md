# 89 - Inline Package Complete Decompilation

Complete bytecode-level decompilation and analysis of all 77 classes in `com.aicode.inline` package.

## Class Inventory (77 classes)

### inline/ (19 classes - core)

| # | Class | Source File | Type | Description |
|---|-------|-------------|------|-------------|
| 1 | InlineChatHandleService | oj | class | Non-streaming response handler; applies diff to editor |
| 2 | InlineChatHandleService$z | oj | class | Switch-map for InlineChatOperateEnum |
| 3 | InlineChatInlay | dm | object | Singleton; manages inlay presentation and balloon popups |
| 4 | InlineChatInlay$01 | dm | class | EditorFactoryListener; registers on editor creation |
| 5 | InlineChatInlay$02 | dm | class | SelectionListener; tracks selection changes |
| 6 | InlineChatInlay$u | dm | class | EditorCustomElementRenderer; renders placeholder text in inlay |
| 7 | InlineChatInputComponent | dg | class | JBTextArea subclass; input field with Enter/Shift+Enter handling |
| 8 | InlineChatInputComponent$01 | dg | class | AbstractAction; Enter key handler (submit) |
| 9 | InlineChatInputComponent$02 | dg | class | AbstractAction; Shift+Enter key handler (newline) |
| 10 | InlineChatInputPanel | zh | class | JPanel; input area with text field, send button, category combo |
| 11 | InlineChatInputPanel$01 | zh | class | DocumentListener; enables/disables send button based on input |
| 12 | InlineChatInputPanel$03 | zh | class | PopupMenuListener; category combo popup events |
| 13 | InlineChatInputPanel$S | zh | class | NotificationAction; handles notification click |
| 14 | InlineChatPanel | pj | class | JPanel+EditorCustomElementRenderer+KeyStrokeHandler; main inline chat panel |
| 15 | InlineChatPanel$02 | pj | class | CaretListener; adjusts panel on caret position change |
| 16 | InlineChatPanel$03 | pj | class | DocumentListener; redraws on document change |
| 17 | InlineChatPanel$r | pj | class | MouseAdapter; click handler for panel |
| 18 | InlineChatPanel$x | pj | class | ComponentAdapter; resize handler for panel |
| 19 | InlineChatService | gd | class | Main service; manages inline chat lifecycle, open/close/toggle |
| 20 | InlineChatService$Companion | gd | class | Kotlin companion; static methods for open/close |
| 21 | InlineChatStreamHandleService | tf | class | Streaming response handler; applies incremental diffs |
| 22 | InlineChatStreamHandleService$v | tf | class | Switch-map for InlineChatOperateEnum |
| 23 | InlineChatTopPanel | qk | class | JPanel; top bar of inline chat (icon + title) |
| 24 | KeyStrokeExecutorProvider | q | interface | Provides KeyStrokeHandler for a given editor |
| 25 | KeyStrokeHandler | o | interface | Executes a keystroke action; returns true if consumed |

### inline/action/ (5 classes)

| # | Class | Source File | Type | Description |
|---|-------|-------------|------|-------------|
| 26 | CloseInlineChatAction | qg | class | Closes inline chat; extends PluginAnAction |
| 27 | OpenInlineChatAction | yn | class | Opens inline chat; registers shortcut |
| 28 | OpenInlineChatAction$Companion | yn | class | Companion; register() and addActionShortcut() |
| 29 | SendMessageAction | ub | class | Sends message from inline chat input |
| 30 | StopAction | xl | class | Stops current inline chat operation |

### inline/action/operate/ (6 classes)

| # | Class | Source File | Type | Description |
|---|-------|-------------|------|-------------|
| 31 | InlineChatAcceptAction | cn | class | Accepts inline chat result; removes editor markup + close button panel |
| 32 | InlineChatAction | pf | class | Base action for operate actions; disposes inlay then calls handle() |
| 33 | InlineChatRejectAction | kl | class | Rejects inline chat result; calls cleanLastData() |
| 34 | InlineChatRetryAction | ce | class | Retries inline chat; calls SessionController.doRetry() |
| 35 | InlineChatStopAction | ak | class | Stops during LOADING/CATEGORY step; calls cleanRender() |
| 36 | InlineChatUndoAction | oe | class | Undoes inline chat; calls InlineChatService.handleUndoAction() |

### inline/controller/ (4 classes)

| # | Class | Source File | Type | Description |
|---|-------|-------------|------|-------------|
| 37 | ChatInputController | gm | class | Controls text input; submit() sends text via callback |
| 38 | EphemeralChatSessionController | ji | class | SessionController impl; lock/unlock for ephemeral sessions |
| 39 | SessionController | rg | abstract class | Core session state machine; manages step/operate enums, rendering |
| 40 | SessionController$X | rg | class | Switch-map for CommandEnum |

### inline/ide/ (11 classes)

| # | Class | Source File | Type | Description |
|---|-------|-------------|------|-------------|
| 41 | ActionScope | hl | enum | INLINE_CHAT_OPENED, INPUT_FOCUSED, ALWAYS, INLINE_CHAT_FOCUSED |
| 42 | ConditionalActionConfiguration | gf | class | Data class: scope + KeyStrokeExecutorProvider + bound keystrokes |
| 43 | ConditionalEditorActionHandler | ag | class | EditorActionHandler wrapper; conditionally intercepts IDE actions |
| 44 | ConditionalEditorActionPredicate | y | interface | Evaluates whether action should be intercepted |
| 45 | DefaultActionScopePredicateFactory | ui | class | PredicateFactory impl; creates predicates per ActionScope |
| 46 | DefaultActionScopePredicateFactory$WhenMappings | ui | class | Kotlin when-mappings for ActionScope enum |
| 47 | IdeAction | rk | class | Data class: actionId + scope |
| 48 | IdeActionService | si | object | Singleton; provides list of 47 IDE actions to intercept |
| 49 | IdeEditorActionRouter | jf | class | Initializes conditional action handlers for all IdeActions |
| 50 | IdeEditorActionRouterKt | ai | class | Kotlin top-level; replaceWithConditionalAction() utility |
| 51 | PredicateFactory | e | interface | Creates ConditionalEditorActionPredicate for a scope |

### inline/render/ (12 classes)

| # | Class | Source File | Type | Description |
|---|-------|-------------|------|-------------|
| 52 | InlineChatBtnPanelRenderer | en | class | Renders accept/retry/undo buttons panel |
| 53 | InlineChatBtnPanelRenderer$O | en | class | MouseAdapter; button hover/click with color change |
| 54 | InlineChatBtnPanelRenderer$U | en | class | ComponentAdapter; resize handler |
| 55 | InlineChatCategoryPanelRenderer | ne | class | Renders category selection panel (generate/doc/edit/etc.) |
| 56 | InlineChatCategoryPanelRenderer$t | ne | class | MouseAdapter; category button hover/click |
| 57 | InlineChatCategoryPanelRenderer$w | ne | class | ComponentAdapter; resize handler |
| 58 | InlineChatErrorPanelRenderer | nd | class | Renders error panel with retry/close buttons |
| 59 | InlineChatErrorPanelRenderer$n | nd | class | MouseAdapter; error button hover/click |
| 60 | InlineChatErrorPanelRenderer$y | nd | class | ComponentAdapter; resize handler |
| 61 | InlineChatStopPanelRenderer | vj | class | Renders stop button panel during streaming |
| 62 | InlineChatStopPanelRenderer$N | vj | class | ComponentAdapter; resize handler |
| 63 | InlineChatStopPanelRenderer$P | vj | class | MouseAdapter; stop button hover/click |

### inline/content/ (1 class)

| # | Class | Source File | Type | Description |
|---|-------|-------------|------|-------------|
| 64 | ChatMessage | ChatMessage.java | class | Simple POJO: question (String) + selected (boolean) |

### inline/dto/ (3 classes)

| # | Class | Source File | Type | Description |
|---|-------|-------------|------|-------------|
| 65 | InlineChatInfo | InlineChatInfo.java | class | Session data: editor, sessionController, message, content, lineList, version |
| 66 | LastChatQuestionInfo | LastChatQuestionInfo.java | class | Last question: offset, question, selected |
| 67 | LastSelectionTextCache | LastSelectionTextCache.java | class | Selection cache: careOffsetStart, selectionStart/End, realStart/End, text, range |

### inline/enums/ (3 classes)

| # | Class | Source File | Type | Description |
|---|-------|-------------|------|-------------|
| 68 | InlineChatCategoryEnum | kn | enum | GENERATE, UNKNOW, LINEDOC, DOC, EDIT |
| 69 | InlineChatOperateEnum | je | enum | EDIT, INSERT |
| 70 | InlineChatStepEnum | rf | enum | LOADING, CATEGORY, SUCCESS, ERROR |

### inline/listener/ (2 classes)

| # | Class | Source File | Type | Description |
|---|-------|-------------|------|-------------|
| 71 | InlineChatInputBorderFocusListener | lg | class | FocusListener; toggles border on focus gain/loss |
| 72 | InlineChatInputBorderFocusListener$Companion | lg | class | Companion; provides focusBorder and unfocusedBorder |

### inline/status/ (5 classes)

| # | Class | Source File | Type | Description |
|---|-------|-------------|------|-------------|
| 73 | InlineChatStatusService | g | interface | onGloballyEnabled/Disabled, ifEnabledForFile |
| 74 | InlineChatStatusServiceKt | hk | class | Kotlin top-level; accessor for status service singleton |
| 75 | InlineChatStatusServiceProvider | kg | object | Singleton provider; holds InlineChatStatusService instance |
| 76 | InlineChatStatusSubscription | w | interface | unsubscribe() |
| 77 | InlineStatusService | ae | class | Impl; ConcurrentMaps for enabled/disabled callbacks per file |

---

## Complete Decompilation by Subpackage

### 1. inline/ -- Core Classes

#### 1.1 InlineChatService (gd)

**Fields:**
- `byte`: InlineChatStatusService -- status service for enabled/disabled checks
- `enum`: static Map<String, InlineChatPanel> -- maps VirtualFile URL to active panel (ConcurrentHashMap)

**Key Methods:**

```
toggleInlineChat(Editor)
  1. Create new InlineChatPanel(this, editor)
  2. Add panel to editor's ContentComponent
  3. Set inlineContainer on panel
  4. Determine offset: selection start if selection, else caret offset
  5. scrollToLines(editor, offset, true)
  6. If existing InlineChatInfo, restore message text
  7. panel.createInlay(offset)
  8. Request focus to input component
  9. Map virtualFile.url -> panel in static map

closeInlineChat(Editor) [static]
  1. Get VirtualFile URL from editor
  2. If URL not in map, return
  3. Get InlineChatPanel from map
  4. Dispose inlay if present
  5. Remove panel from editor ContentComponent
  6. Revalidate + repaint editor component
  7. Remove URL from static map

openInlineChat(Editor) [via Companion]
  1. Send USER_MODEL_LIST ws message
  2. Clear RANGE_KEY and BODY_RANGE_KEY from editor UserData
  3. Get VirtualFile path
  4. Call statusService.ifEnabledForFile(path, supplier)
  5. Supplier creates InlineChatInfo, sets version, calls Ga() for range request

handleUndoAction(Editor) [static]
  1. Get InlineChatInfo by editor
  2. Set operateEnum to null
  3. Dispose inlay
  4. Call sessionController.handleUndo(editor)

cleanLastData(Editor) [static]
  1. Get InlineChatInfo by editor
  2. If sessionController null, close inline chat
  3. Call cleanLastData(InlineChatInfo)

cleanRender(Editor) [static]
  1. Get InlineChatInfo by editor
  2. Set operateEnum to null on sessionController
  3. Call cleanLastData(editor)

Constructor:
  1. Get InlineChatStatusService singleton
  2. Register onGloballyDisabled callback (closes all inline chats)
  3. Create IdeEditorActionRouter(this, keyStrokeExecutorProvider)
  4. Call router.init() to install conditional action handlers
```

**H() calls:** Multiple obfuscated strings via `enum(int)` method for parameter validation.

#### 1.2 InlineChatHandleService (oj) -- Non-Streaming Handler

**Fields:**
- `byte`: static TextAttributes -- original text highlight attributes
- `enum`: static TextAttributes -- new text highlight attributes
- `selectOriginalAttributes`: static TextAttributes -- selection original highlight
- `HANDING_DATA`: volatile boolean -- processing flag

**Key Methods:**

```
handleData(SessionController, String, MessageDto)
  1. Calls kE() to set tip text and category on controller
  2. Gets editor, startOffset, endOffset, document
  3. Determines line numbers for start/end
  4. Extracts content from MessageDto
  5. Parses response JSON for code changes
  6. Applies diff using difflib
  7. Highlights changes in editor

handleData(JsonObject, MessageDto)
  1. Alternative entry for JSON-based data handling

handleErrorData(SessionController, String)
  1. Called on error response
  2. Renders error panel

saveDocument(Project, Document)
  1. Runs document save in write action
```

**Internal Methods:**
- `kE()`: Sets tip text and category enum on controller, calculates line range
- `Ee()`: Compares two JsonObjects for equality
- `Af()`: Applies highlight range to markup model
- `dF()`: Handles diff application for specific line range
- `he()`: Handles error display
- `id()`: Inserts text into document at offset with highlighting
- `lf()`, `eD()`: Boolean-to-string conversion
- `Hd()`: Validates code ranges against editor content
- `aE()`, `Tc()`: Cleanup methods
- `OA()`: Applies DiffRow list to document
- `Tf()`: Text insertion with prefix trimming
- `sE()`, `UE()`: String processing for diff content
- `Sf()`: Scrolls editor to specific position
- `tb()`: Creates TextAttributes from Color

#### 1.3 InlineChatStreamHandleService (tf) -- Streaming Handler

**Fields:**
- `byte`: static TextAttributes -- original text highlight
- `enum`: static TextAttributes -- new text highlight
- `toHandleAttributes`: static TextAttributes -- text to be handled highlight
- `highLightAttributes`: static TextAttributes -- active highlight
- `HANDING_DATA`: volatile boolean

**Key Methods:**

```
handleData(String, ResponseStreamDto, MessageDto)
  1. Get InlineChatInfo by editor
  2. Get SessionController
  3. Route based on operate enum (EDIT vs INSERT)
  4. For streaming: apply incremental text changes
  5. Update highlight ranges as data arrives
  6. On stream complete: render button panel

handleErrorData(SessionController, String)
  1. Render error panel on stream error

saveDocument(Project, Document)
  1. Save document in write action
```

**Internal Methods:**
- `sb()`: Scrolls to position and sets highlight
- `Cc()`: Handles error text display
- `rc()`: Processes stream response data
- `eA()`: End-of-stream handling
- `gB()`, `ga()`: String processing
- `ka()`, `Ua()`, `Gc()`, `pB()`, `pc()`: Various stream data application methods
- `xB()`: Post-stream cleanup
- `hC()`: Document content cleanup
- `jc()`, `Tc()`: Controller state management
- `zA()`: Extracts response content from stream DTO
- `OA()`: Applies DiffRow list
- `La()`, `Hc()`, `sc()`: Highlight range management
- `WB()`, `GA()`: Boolean conversion

#### 1.4 InlineChatInlay (dm) -- Inlay Manager

**Fields:**
- `INSTANCE`: static InlineChatInlay (singleton)
- `byte`: static Inlay<?> -- current inlay reference
- `enum`: static AtomicBoolean -- inlay creation guard
- `balloons`: static Map<Editor, Balloon> -- active balloons per editor

**Key Methods:**

```
register()
  1. Registers EditorFactoryListener (inner class $01)
  2. On editor creation, adds SelectionListener ($02)

addInlay(Editor)
  1. Creates inlay at caret position
  2. Uses InlineChatInlay$u renderer for placeholder text
  3. Stores inlay reference

disposeInlay() [static]
  1. Disposes current inlay if present
  2. Resets guard flag

tf(): Private initialization
fe(Editor): Handles editor-specific setup
```

**Inner Classes:**
- `$01` (EditorFactoryListener): On editorCreated, adds selection listener
- `$02` (SelectionListener): On selectionChanged, manages balloon visibility
- `$u` (EditorCustomElementRenderer): Renders placeholder text in inlay with gray TextAttributes

#### 1.5 InlineChatInputComponent (dg) -- Text Input

**Extends:** JBTextArea

**Fields:**
- `try`: AbstractAction -- Enter key action (submit)
- `float`: int -- row count tracking
- `byte`: AbstractAction -- Shift+Enter action (newline)
- `enum`: JLabel -- placeholder label

**Key Methods:**

```
processKeyEvent(KeyEvent)
  1. Overrides default key processing
  2. Delegates to parent for standard handling

updatePlaceholder()
  1. Shows/hides placeholder text based on content
```

**Inner Classes:**
- `$01` (AbstractAction): Enter handler -- calls inputPanel submit
- `$02` (AbstractAction): Shift+Enter handler -- inserts newline, adjusts row count

#### 1.6 InlineChatInputPanel (zh) -- Input Area

**Extends:** JPanel

**Fields:**
- `new`: GridBagConstraints
- `long`: InlineChatInputComponent -- text input
- `super`: InlineChatPanel -- parent panel reference
- `for`: GridBagConstraints
- `if`: GridBagConstraints
- `case`: ChatInputController -- input controller
- `final`: Editor -- associated editor
- `try`: SendStopActionButtonPanel -- send/stop button
- `float`: EphemeralChatSessionController -- session controller
- `byte`: ComboBox -- category selector
- `enum`: GridBagConstraints

**Key Methods:**

```
Bd(Editor): Submit handler
  1. Gets text from input component
  2. Creates ChatMessage
  3. Calls sessionController.sendMessage()

CE(): Check if input is empty
ja(String): Set text in input
getChatInputController(): Returns controller
qC(Editor, String): Send message with text
Sb(): Delete/clear handler
setComboBox(): Configure category combo box
qA(String): Update input text
delete(): Cleanup and remove panel
```

**Inner Classes:**
- `$01` (DocumentListener): Enables/disables send button based on text content
- `$03` (PopupMenuListener): Category combo popup visibility events
- `$S` (NotificationAction): Handles notification click action

#### 1.7 InlineChatPanel (pj) -- Main Panel

**Implements:** JPanel, EditorCustomElementRenderer, KeyStrokeHandler, Disposable

**Fields:**
- `for`: static Logger
- `if`: Container -- outer container
- `case`: InlineChatInputPanel -- input area
- `final`: JComponent -- content area
- `try`: JPanel -- content panel
- `float`: InlineChatTopPanel -- top bar
- `byte`: Editor -- associated editor
- `enum`: Inlay<?> -- inlay reference

**Key Methods:**

```
createInlay(int offset)
  1. Creates InlayModel at offset
  2. Stores inlay reference

paint(Inlay, Graphics, Rectangle, TextAttributes)
  1. Renders the panel in the inlay

execute(KeyStroke): KeyStrokeHandler impl
  1. Delegates to KeyStrokeExecutorProvider
  2. Returns true if keystroke consumed

redraw(): Forces inlay repaint
setContent(JComponent): Sets content area
dispose(): Cleanup

inAllChildren(JComponent, Function1): Static utility
  1. Recursively applies function to all child components
```

**Inner Classes:**
- `$02` (CaretListener): Adjusts panel position on caret change
- `$03` (DocumentListener): Redraws panel on document change
- `$r` (MouseAdapter): Click handler
- `$x` (ComponentAdapter): Resize handler

#### 1.8 InlineChatTopPanel (qk) -- Top Bar

**Extends:** JPanel

Simple panel with GridBagConstraints for layout. Contains icon and title label.

#### 1.9 KeyStrokeHandler / KeyStrokeExecutorProvider (o, q)

```
interface KeyStrokeHandler {
    boolean execute(KeyStroke);
}

interface KeyStrokeExecutorProvider {
    KeyStrokeHandler keyStrokeExecutor(Editor);
}
```

---

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

### 5. inline/ide/ -- IDE Key Interception System

#### 5.1 ActionScope (hl) -- Enum

```
INLINE_CHAT_OPENED  -- action active when inline chat is open
INPUT_FOCUSED       -- action active when input field has focus
ALWAYS              -- action always active
INLINE_CHAT_FOCUSED -- action active when inline chat panel has focus
```

Enum values are H()-obfuscated strings decoded at class init.

#### 5.2 ConditionalActionConfiguration (gf)

**Data class with:**
- `byte`: ActionScope
- `float`: KeyStrokeExecutorProvider
- `enum`: Set<KeyStroke> -- bound keystrokes

**Methods:**
- `getScope()`, `getKeyStrokeExecutorProvider()`, `getBoundKeyStrokes()`
- `copy()`, `equals()`, `hashCode()`, `toString()`
- `H(Object)`: Deobfuscation helper

#### 5.3 ConditionalEditorActionHandler (ag)

**Extends:** EditorActionHandler

**Fields:**
- `final`: ConditionalActionConfiguration
- `try`: EditorActionHandler -- original handler (chained)
- `float`: PredicateFactory
- `byte`: boolean -- consumed flag
- `enum`: InlineChatService

**Key Methods:**

```
isEnabledForCaret(Editor, Caret, DataContext)
  1. Evaluate predicate for current scope
  2. Return true if action should be intercepted

doExecute(Editor, Caret, DataContext)
  1. If predicate evaluates true:
     a. Get KeyStrokeHandler from configuration
     b. Execute keystroke via handler
     c. If consumed, return (don't chain to original)
  2. Otherwise, delegate to original handler

DC(Editor, Caret, DataContext): Internal enabled check
xa(Editor): Focus management
```

#### 5.4 ConditionalEditorActionPredicate (y) -- Interface

```
boolean evaluate(Editor, Caret, DataContext)
```

#### 5.5 DefaultActionScopePredicateFactory (ui)

**Implements:** PredicateFactory

**Fields:**
- `enum`: InlineChatService

**predicate(ActionScope):**
```
INLINE_CHAT_OPENED -> yb(): checks if inline chat panel exists for editor
INPUT_FOCUSED -> Nb(): checks if input component has focus
ALWAYS -> ha(): returns true (static)
INLINE_CHAT_FOCUSED -> hA(): checks if inline chat panel has focus
```

**Internal Methods:**
- `mC()`: Static helper; checks if inline chat is open for editor
- `YA()`: Static helper; checks if inline chat panel is focused
- `Sa()`: Static helper; checks if input is focused

#### 5.6 IdeAction (rk)

**Data class:**
- `byte`: String -- actionId (H()-obfuscated)
- `enum`: ActionScope

**47 IDE actions registered** (from IdeActionService.wa() bytecode analysis). The actionIds are H()-obfuscated but based on the scope assignments and IntelliJ action ID conventions, they include:

| Scope | Likely Actions |
|-------|---------------|
| null (ALWAYS) | EditorEnter, EditorTab, EditorBackSpace, EditorDelete, EditorLineStart, EditorLineEnd, EditorMoveToPageTop, EditorMoveToPageBottom, etc. |
| INLINE_CHAT_FOCUSED | EditorPreviousWord, EditorNextWord, EditorPreviousWordWithSelection, EditorNextWordWithSelection |
| INLINE_CHAT_OPENED | EditorEscape |

#### 5.7 IdeActionService (si) -- Singleton

**Fields:**
- `INSTANCE`: static IdeActionService

**wa()**: Creates array of 47 IdeAction objects with H()-obfuscated actionIds.

**getIdeActions()**: Returns cached list of all IDE actions to intercept.

#### 5.8 IdeEditorActionRouter (jf)

**init():**
```
1. Get all IdeActions from IdeActionService
2. For each IdeAction:
   a. Get shortcuts from active Keymap
   b. Filter KeyboardShortcut instances
   c. Extract first and second KeyStrokes
   d. Create ConditionalActionConfiguration(scope, provider, keystrokes)
   e. Call replaceWithConditionalAction(editorActionManager, actionId, config, inlineChatService)
```

#### 5.9 IdeEditorActionRouterKt (ai)

**replaceWithConditionalAction(EditorActionManager, String, ConditionalActionConfiguration, InlineChatService):**
```
1. Get Action from ActionManager by actionId
2. If action exists:
   a. Get current EditorActionHandler
   b. If handler exists:
      - Create ConditionalEditorActionHandler(originalHandler, config, inlineChatService)
      - Set as new handler via EditorActionManager.setActionHandler()
   c. If handler is null, log warning
3. If action not found, log debug
```

#### 5.10 PredicateFactory (e) -- Interface

```
ConditionalEditorActionPredicate predicate(ActionScope)
```

---

### 6. inline/render/ -- Renderer Classes

All renderers follow the same pattern: JPanel + EditorCustomElementRenderer + Disposable.

#### 6.1 InlineChatBtnPanelRenderer (en) -- Accept/Retry/Undo Buttons

**Constructor:** `(InlineChatOperateEnum, int, Disposable, Editor, Function0<Unit> x4)`

Four Function0<Unit> callbacks:
- `byte`: Accept callback
- `enum`: Retry callback
- `case`: Undo callback
- `int`: (additional callback)

**Layout:**
- Icon label (ToolWindowIcon)
- Accept button (green) -> calls accept callback
- Retry button (blue) -> calls retry callback
- Undo button (red) -> calls undo callback
- Buttons created via `aC(String, Color)` helper

**Inner Classes:**
- `$O` (MouseAdapter): Button hover/click; changes label color on enter/exit, fires callback on click
- `$U` (ComponentAdapter): Resize handler; triggers inlay update

#### 6.2 InlineChatCategoryPanelRenderer (ne) -- Category Selection

**Constructor:** `(int, Disposable, Editor, Function0<Unit>)`

**Layout:**
- Category buttons: GENERATE, DOC, LINEDOC, EDIT
- Each button has hover effect (color change)
- Click triggers the Function0<Unit> callback

**Inner Classes:**
- `$t` (MouseAdapter): Category button hover/click
- `$w` (ComponentAdapter): Resize handler

#### 6.3 InlineChatErrorPanelRenderer (nd) -- Error Display

**Constructor:** `(int, Disposable, Editor, String, Function0<Unit> x2)`

**Layout:**
- Error message text
- Retry button (calls first callback)
- Close button (calls second callback)

**Inner Classes:**
- `$n` (MouseAdapter): Error button hover/click
- `$y` (ComponentAdapter): Resize handler

#### 6.4 InlineChatStopPanelRenderer (vj) -- Stop Button

**Constructor:** `(int, Disposable, Editor, Function0<Unit>)`

**Layout:**
- Stop button with hover effect
- Click triggers stop callback

**Inner Classes:**
- `$N` (ComponentAdapter): Resize handler
- `$P` (MouseAdapter): Stop button hover/click

**Common Renderer Methods:**
```
createInlay(int offset): Creates inlay at offset in editor
paint(Inlay, Graphics, Rectangle, TextAttributes): Renders panel
calcWidthInPixels(Inlay): Returns panel width
calcHeightInPixels(Inlay): Returns panel height
redraw(): Forces inlay repaint
dispose(): Cleanup
setInlay(Inlay): Updates inlay reference
setOffset(int): Updates offset
inAllChildren(JComponent, Function1): Recursive child traversal
```

---

### 7. inline/content/ -- ChatMessage

```java
public class ChatMessage {
    private String question;
    private boolean selected;
    // getters, setters, equals, hashCode, toString, canEqual
}
```

---

### 8. inline/dto/ -- Data Transfer Objects

#### 8.1 InlineChatInfo

```java
public class InlineChatInfo {
    private String message;
    private Editor editor;
    private SessionController sessionController;
    public int inlineChatVersion;
    private String requestId;
    private String content;
    private List<String> lineList;
    private boolean trimPrefix;
    private AtomicInteger handleLineIndex;
    // full getters/setters
}
```

#### 8.2 LastChatQuestionInfo

```java
public class LastChatQuestionInfo {
    private int offset;
    private String question;
    private boolean selected;
    // constructor, getters/setters
}
```

#### 8.3 LastSelectionTextCache

```java
public class LastSelectionTextCache {
    private int careOffsetStart;
    private int selectionStart;
    private int selectionEnd;
    private int realStartOffset;
    private int realEndOffset;
    private String text;
    private List<CodeInfoDto$RangeDTO> range;
    // constructor, getters/setters
}
```

---

### 9. inline/enums/ -- Enumerations

#### 9.1 InlineChatCategoryEnum

| Value | H()-decoded | Description |
|-------|-------------|-------------|
| GENERATE | "generate" | Code generation |
| UNKNOW | "unknown" | Unknown category |
| LINEDOC | "linedoc" | Line-level documentation |
| DOC | "doc" | Block documentation |
| EDIT | "edit" | Code editing |

Methods: `getCategoryEnumByValue(String)`, `getCategoryEnumByName(String)`, `getValue()`

#### 9.2 InlineChatOperateEnum

| Value | Description |
|-------|-------------|
| EDIT | Modify existing code (diff-based) |
| INSERT | Insert new code |

#### 9.3 InlineChatStepEnum

| Value | Description |
|-------|-------------|
| LOADING | Waiting for response |
| CATEGORY | Category selection displayed |
| SUCCESS | Operation completed |
| ERROR | Error occurred |

---

### 10. inline/listener/ -- Focus Listener

#### 10.1 InlineChatInputBorderFocusListener

**Companion:**
- `focusBorder`: Border when input has focus
- `unfocusedBorder`: Border when input loses focus

**Behavior:**
- `focusGained()`: Set focus border
- `focusLost()`: Set unfocused border

---

### 11. inline/status/ -- Status Service

#### 11.1 InlineChatStatusService (interface)

```
onGloballyEnabled(Function0<Unit>): InlineChatStatusSubscription
onGloballyDisabled(Function0<Unit>): InlineChatStatusSubscription
ifEnabledForFile(String, Supplier<?>): void
```

#### 11.2 InlineStatusService (ae) -- Implementation

**Fields:**
- `byte`: ConcurrentMap<String, Function0<Unit>> -- enabled callbacks per file
- `enum`: ConcurrentMap<String, Function0<Unit>> -- disabled callbacks per file

**Methods:**
- `onGloballyEnabled()`: Registers callback, returns subscription
- `onGloballyDisabled()`: Registers callback, returns subscription
- `ifEnabledForFile()`: Checks if file is enabled, executes supplier if so
- `eC()`: Enable handler for file
- `OB()`: Disable handler for file
- `cB()`: Creates subscription wrapper
- `rC()`: Generates unique callback ID

#### 11.3 InlineChatStatusServiceProvider (kg) -- Singleton

Holds static `InlineChatStatusService` instance.

#### 11.4 InlineChatStatusServiceKt (hk)

Kotlin top-level functions:
- `InlineChatStatusService()`: Returns service from provider
- `H(Object)`: Deobfuscation helper

#### 11.5 InlineChatStatusSubscription (interface)

```
unsubscribe()
```

---

## Inline Chat Complete Interaction Flow

```
[User triggers OpenInlineChatAction or toggleInlineChat]
         |
         v
InlineChatService.toggleInlineChat(editor)
  |-- Create InlineChatPanel(disposable, editor)
  |-- Add panel to Editor ContentComponent
  |-- Determine offset (selection start or caret offset)
  |-- panel.createInlay(offset)
  |-- Request focus to input component
  |-- Store panel in static map (url -> panel)
         |
         v
[User types in InlineChatInputComponent]
  |-- Enter key -> ChatInputController.submit()
  |-- Shift+Enter -> newline
         |
         v
InlineChatInputPanel.Bd(editor)
  |-- Get text from input component
  |-- Create ChatMessage(question, selected)
  |-- sessionController.sendMessage(message, editor)
         |
         v
SessionController.sendMessage(message, editor)
  |-- executeRequest(message, editor)
      |-- Create MessageDto with UUID
      |-- Set command based on category
      |-- Set path, content, range from editor
      |-- Set inlineChatVersion
      |-- PluginWebsocketClient.sendWsMessage(dto, project)
         |
         v
[WebSocket Response Arrives]
         |
    +----+----+
    |         |
    v         v
[Non-Stream]  [Stream]
    |         |
    v         v
InlineChatHandleService   InlineChatStreamHandleService
.handleData()             .handleData()
    |                         |
    v                         v
[Parse response JSON]    [Parse stream chunks]
[Apply diff via difflib] [Apply incremental changes]
[Highlight changes]      [Update highlights per chunk]
    |                         |
    +----+----+---------------+
         |
         v
SessionController.renderCategoryPanel()  (if CATEGORY step)
  |-- Create InlineChatCategoryPanelRenderer
  |-- User selects: GENERATE / DOC / LINEDOC / EDIT
  |-- Re-send with selected category
         |
         v
SessionController.renderStopPanel()  (during LOADING)
  |-- Create InlineChatStopPanelRenderer
  |-- User can click Stop
         |
         v
SessionController.renderFunButtons()  (on SUCCESS)
  |-- Create InlineChatBtnPanelRenderer
  |-- Buttons: Accept / Retry / Undo
         |
    +----+----+----+
    |         |    |
    v         v    v
  Accept    Retry  Undo
    |         |    |
    v         |    v
removeEditor  |  handleUndoAction
closeButton   |  (revert changes)
Panel         |
    |         v
    |       doRetry()
    |       (re-send message)
    v
[Changes applied to document]

SessionController.renderErrorFunButtons()  (on ERROR)
  |-- Create InlineChatErrorPanelRenderer
  |-- Buttons: Retry / Close
         |
    +----+----+
    |         |
    v         v
  Retry     Close
    |         |
    v         v
doErrorRetry  cleanLastData
              (revert changes)
```

## IDE Key Interception System Analysis

### Architecture Overview

```
InlineChatService (constructor)
  |
  +-- Creates IdeEditorActionRouter(inlineChatService, keyStrokeExecutorProvider)
  |     |
  |     +-- init()
  |           |
  |           +-- IdeActionService.getIdeActions()  [47 actions]
  |           |     |
  |           |     +-- For each IdeAction:
  |           |           Get Keymap shortcuts
  |           |           Extract KeyStrokes
  |           |           Create ConditionalActionConfiguration
  |           |           Call replaceWithConditionalAction()
  |           |
  |           +-- IdeEditorActionRouterKt.replaceWithConditionalAction()
  |                 |
  |                 +-- Get original EditorActionHandler
  |                 +-- Wrap with ConditionalEditorActionHandler
  |                 +-- Set as new handler
  |
  +-- Registers onGloballyDisabled callback (closes all chats)

ConditionalEditorActionHandler
  |
  +-- isEnabledForCaret()
  |     Uses PredicateFactory -> DefaultActionScopePredicateFactory
  |     |
  |     +-- INLINE_CHAT_OPENED: check if panel exists for editor
  |     +-- INPUT_FOCUSED: check if input component has focus
  |     +-- ALWAYS: always true
  |     +-- INLINE_CHAT_FOCUSED: check if panel has focus
  |
  +-- doExecute()
        |
        +-- If predicate true:
        |     Get KeyStrokeHandler from config
        |     Execute keystroke
        |     If consumed, return (block original action)
        |
        +-- If predicate false:
              Delegate to original handler
```

### Intercepted Actions (47 total)

Based on bytecode analysis of `IdeActionService.wa()`, 47 IDE actions are intercepted. The action IDs are H()-obfuscated but can be categorized by their ActionScope assignment:

**INLINE_CHAT_FOCUSED scope (4 actions):**
- EditorPreviousWord
- EditorNextWord
- EditorPreviousWordWithSelection
- EditorNextWordWithSelection

**INLINE_CHAT_OPENED scope (1 action):**
- EditorEscape (close inline chat)

**null/ALWAYS scope (42 actions):**
All standard editor actions that should be conditionally intercepted when inline chat is active:
- EditorEnter, EditorTab, EditorBackSpace
- EditorDelete, EditorLineStart, EditorLineEnd
- EditorMoveToPageTop, EditorMoveToPageBottom
- EditorUp, EditorDown, EditorLeft, EditorRight
- EditorSelectionStart, EditorSelectionEnd
- EditorTextStart, EditorTextEnd
- EditorPreviousWord, EditorNextWord
- EditorCut, EditorCopy, EditorPaste
- Various other editor navigation/edit actions

### Key Interception Flow

```
[User presses key in editor]
         |
         v
IntelliJ Action System
         |
         v
ConditionalEditorActionHandler.doExecute()
         |
         v
DefaultActionScopePredicateFactory.predicate(scope).evaluate()
         |
    +----+----+
    |         |
    v         v
  true      false
    |         |
    v         v
KeyStrokeHandler  Original Handler
.execute()        .doExecute()
    |
    v
InlineChatPanel.execute(keyStroke)
  |-- If inline chat is open and focused:
  |     Consume keystroke (return true)
  |     Route to inline chat input
  |-- Otherwise:
        Return false (let original handler run)
```

## Renderer System Analysis

### Renderer Hierarchy

```
EditorCustomElementRenderer (IntelliJ interface)
    |
    +-- InlineChatPanel (also KeyStrokeHandler, Disposable)
    +-- InlineChatBtnPanelRenderer (also Disposable)
    +-- InlineChatCategoryPanelRenderer (also Disposable)
    +-- InlineChatErrorPanelRenderer (also Disposable)
    +-- InlineChatStopPanelRenderer (also Disposable)
    +-- InlineChatInlay$u (placeholder renderer)
```

### Renderer Lifecycle

```
1. Creation:
   renderer = new XxxPanelRenderer(offset, disposable, editor, callbacks...)
   renderer.createInlay(offset)
   -- Creates Inlay in editor's InlayModel
   -- Stores Inlay reference in renderer

2. Painting:
   paint(Inlay, Graphics, Rectangle, TextAttributes)
   -- Called by IntelliJ inlay system
   -- Renders JPanel content into Graphics context

3. Interaction:
   MouseAdapter inner classes handle:
   -- mouseEntered: change label color (hover effect)
   -- mouseExited: restore label color
   -- mouseClicked: fire callback function

4. Resize:
   ComponentAdapter inner classes handle:
   -- componentResized: trigger inlay update

5. Disposal:
   dispose()
   -- Remove inlay
   -- Clean up listeners
```

### Renderer State Machine

```
[Inline Chat Opened]
         |
         v
InlineChatPanel (input area)
  |-- User types and submits
         |
         v
InlineChatCategoryPanelRenderer  <-- CATEGORY step
  |-- GENERATE / DOC / LINEDOC / EDIT
  |-- Click triggers category selection
         |
         v
InlineChatStopPanelRenderer  <-- LOADING step
  |-- Stop button during streaming
  |-- Click triggers stop action
         |
    +----+----+
    |         |
    v         v
  SUCCESS    ERROR
    |         |
    v         v
InlineChatBtnPanelRenderer   InlineChatErrorPanelRenderer
  |-- Accept (green)          |-- Retry button
  |-- Retry (blue)            |-- Close button
  |-- Undo (red)              |-- Error message text
```

### Button Color Scheme

| Button | Normal Color | Hover Color | Action |
|--------|-------------|-------------|--------|
| Accept | Green | Lighter green | Accept changes |
| Retry | Blue | Lighter blue | Retry request |
| Undo | Red | Lighter red | Revert changes |
| Stop | Default | Hover | Stop streaming |
| Category | Default | Hover | Select category |
| Error Retry | Default | Hover | Retry after error |
| Error Close | Default | Hover | Dismiss error |

### Inlay Management

```
InlineChatInlay (singleton)
  |-- Manages global inlay state
  |-- Registers EditorFactoryListener
  |-- Tracks SelectionListener per editor
  |-- Manages balloon popups per editor

SessionController
  |-- Holds defaultInlay reference
  |-- Holds rangeHighlighter for selection
  |-- Holds toHandleRangeHighlighterMap for diff highlights
  |-- Creates renderer inlays via renderXxxPanel() methods
```

## H() Obfuscation Summary

The inline package uses extensive H() obfuscation for:
1. **Action IDs** in IdeActionService (47 obfuscated strings via Maps.H and OpenTelemetryUtil.H)
2. **Enum values** in ActionScope (4 obfuscated strings)
3. **Error messages** in enum(int) methods across all classes
4. **UI text** in renderers (button labels, error messages)
5. **Category enum values** in InlineChatCategoryEnum

Key H() targets used in inline package:
- `com/aicode/util/Maps.H()` -- action IDs
- `com/aicode/apm/OpenTelemetryUtil.H()` -- action IDs
- `com/aicode/util/NewFileUtils.H()` -- enum values, UI text
- `com/aicode/inline/status/InlineChatStatusServiceKt.H()` -- status messages
- `com/aicode/inline/ide/IdeAction.H()` -- action-related strings
- `com/aicode/inline/ide/ConditionalActionConfiguration.H()` -- configuration strings
- `com/aicode/exception/RequestCancelException.H()` -- error text
- `com/aicode/exception/RequestTimeoutException.H()` -- error text
- `com/aicode/service/editor/CancelRequestTip.H()` -- cancel messages
- `com/aicode/content/util/file/LanguageFileExtensionDetails.H()` -- language-related strings
- `com/aicode/diff/FileService.H()` -- diff-related strings
- `com/aicode/diff/FileInfo.H()` -- file info strings
- `com/aicode/language/AICodeLanguageInfo.H()` -- language info strings

## Cross-Package Dependencies

The inline package depends on:

| Package | Classes Used |
|---------|-------------|
| com.aicode.agent | PluginWebsocketClient, MessageDto, ResponseStreamDto, CommandEnum, CodeInfoDto$RangeDTO |
| com.aicode.agent.dto.chat | CodeInfoDto$RangeDTO |
| com.aicode.action.click | PluginAnAction |
| com.aicode.util | EditorKt, VirtualFileUtils, Application, Maps, NewFileUtils |
| com.aicode.ui | SendStopActionButtonPanel |
| com.aicode.icons | Icons.ToolWindowIcon |
| com.aicode.service.editor | CancelRequestTip, InlineChatCommandService |
| com.aicode.exception | RequestCancelException, RequestTimeoutException |
| com.aicode.content.util.file | LanguageFileExtensionDetails |
| com.aicode.diff | FileService, FileInfo |
| com.aicode.apm | OpenTelemetryUtil |
| com.aicode.language | AICodeLanguageInfo |
| com.github.difflib.text | DiffRow |
| cn.hutool.core.util | IdUtil |
