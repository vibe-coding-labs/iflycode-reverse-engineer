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
