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
