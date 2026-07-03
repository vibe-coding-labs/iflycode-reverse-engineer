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
interface KeyStrokeHandler &#123;
    boolean execute(KeyStroke);
&#125;

interface KeyStrokeExecutorProvider &#123;
    KeyStrokeHandler keyStrokeExecutor(Editor);
&#125;
```

---
