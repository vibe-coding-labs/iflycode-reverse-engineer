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
- `enum`: Set&lt;KeyStroke&gt; -- bound keystrokes

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

**Constructor:** `(InlineChatOperateEnum, int, Disposable, Editor, Function0&lt;Unit&gt; x4)`

Four Function0&lt;Unit&gt; callbacks:
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

**Constructor:** `(int, Disposable, Editor, Function0&lt;Unit&gt;)`

**Layout:**
- Category buttons: GENERATE, DOC, LINEDOC, EDIT
- Each button has hover effect (color change)
- Click triggers the Function0&lt;Unit&gt; callback

**Inner Classes:**
- `$t` (MouseAdapter): Category button hover/click
- `$w` (ComponentAdapter): Resize handler

#### 6.3 InlineChatErrorPanelRenderer (nd) -- Error Display

**Constructor:** `(int, Disposable, Editor, String, Function0&lt;Unit&gt; x2)`

**Layout:**
- Error message text
- Retry button (calls first callback)
- Close button (calls second callback)

**Inner Classes:**
- `$n` (MouseAdapter): Error button hover/click
- `$y` (ComponentAdapter): Resize handler

#### 6.4 InlineChatStopPanelRenderer (vj) -- Stop Button

**Constructor:** `(int, Disposable, Editor, Function0&lt;Unit&gt;)`

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
public class ChatMessage &#123;
    private String question;
    private boolean selected;
    // getters, setters, equals, hashCode, toString, canEqual
&#125;
```

---

### 8. inline/dto/ -- Data Transfer Objects

#### 8.1 InlineChatInfo

```java
public class InlineChatInfo &#123;
    private String message;
    private Editor editor;
    private SessionController sessionController;
    public int inlineChatVersion;
    private String requestId;
    private String content;
    private List&lt;String&gt; lineList;
    private boolean trimPrefix;
    private AtomicInteger handleLineIndex;
    // full getters/setters
&#125;
```

#### 8.2 LastChatQuestionInfo

```java
public class LastChatQuestionInfo &#123;
    private int offset;
    private String question;
    private boolean selected;
    // constructor, getters/setters
&#125;
```

#### 8.3 LastSelectionTextCache

```java
public class LastSelectionTextCache &#123;
    private int careOffsetStart;
    private int selectionStart;
    private int selectionEnd;
    private int realStartOffset;
    private int realEndOffset;
    private String text;
    private List<CodeInfoDto$RangeDTO> range;
    // constructor, getters/setters
&#125;
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
onGloballyEnabled(Function0&lt;Unit&gt;): InlineChatStatusSubscription
onGloballyDisabled(Function0&lt;Unit&gt;): InlineChatStatusSubscription
ifEnabledForFile(String, Supplier<?>): void
```

#### 11.2 InlineStatusService (ae) -- Implementation

**Fields:**
- `byte`: ConcurrentMap<String, Function0&lt;Unit&gt;> -- enabled callbacks per file
- `enum`: ConcurrentMap<String, Function0&lt;Unit&gt;> -- disabled callbacks per file

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
