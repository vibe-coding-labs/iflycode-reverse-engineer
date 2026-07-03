## 7. Inline Chat UI Architecture

### 7.1 InlineChatPanel

```
Extends: JPanel, EditorCustomElementRenderer, KeyStrokeHandler, Disposable

Fields:
- byte: Editor (current editor)
- case: InlineChatInputPanel (input area)
- float: InlineChatTopPanel (top bar with close button)
- try: JPanel (content panel)
- final: JComponent (renderer component)
- enum: Inlay (editor inlay reference)
- if: Container (parent container)
```

### 7.2 InlineChatInputPanel

```
Extends: JPanel

Fields:
- super: InlineChatPanel (parent panel)
- final: Editor (current editor)
- long: InlineChatInputComponent (text area)
- try: SendStopActionButtonPanel (send/stop buttons)
- case: ChatInputController (input controller)
- float: EphemeralChatSessionController (session controller)
- byte: ComboBox (model selector)
- new, for, if, enum: GridBagConstraints (layout constraints)
```

### 7.3 InlineChatInputComponent

```
Extends: JBTextArea

Fields:
- try: AbstractAction (submit action)
- byte: AbstractAction (newline action)
- float: int (some counter/flag)
- enum: JLabel (character count or status label)

Key behavior:
- processKeyEvent() - Custom key handling for Ctrl+Arrow navigation
- Handles Enter for submit, Shift+Enter for newline
```

### 7.4 InlineChatTopPanel

```
Extends: JPanel

Layout: GridBagLayout
- Position (0,0): InlineChatInputPanel (weightx=1.0, fill=HORIZONTAL, anchor=EAST)
- Position (1,0): CloseInlineChatAction button (anchor=EAST, left inset=5)

Background: Style.Colors.InlineChat.background
Border: EmptyBorder(1,1,0,0)
FocusListener: InlineChatInputBorderFocusListener on input component
```

### 7.5 SendStopActionButtonPanel

```
Extends: JPanel
Layout: CardLayout

Cards:
- "stop" card: StopAction button (byte field)
- "send" card: SendMessageAction button (case field)

Methods:
- showStopButton() -> CardLayout.show("stop"), enable both
- showSendButton(Function0&lt;Boolean&gt; isEnabled) -> CardLayout.show("send"), set enabled state
```

---

## 8. Editor Integration: Gutter Icons and Inlay Hints

### 8.1 CheckGutterIconRenderer

```
Extends: GutterIconRenderer

Fields:
- presentationDataDto: PresentationDataDto (check result data)
- type: String (check type)
- highlighter: RangeHighlighter (editor highlighter)
- editor: Editor (current editor)
- lineNumber: int (line number)
- commandEnums: List&lt;CommandEnum&gt; (available actions)
- anActions: AnAction[] (cached action array)

Icon: toolWindow.svg / toolWindow_dark.svg based on theme
Alignment: LEFT
Tooltip: "" (empty)

Click Action:
1. Get CodeInfoDto from presentationDataDto
2. Get range (start/end line)
3. Jump to file via CommonService.jumpToFileByIndex()

Popup Menu Actions:
- Creates action group from commandEnums
- Supported commands: CODE_TEST, CODE_EXPLAIN, CODE_OPTIMIZE,
  CODE_SPLIT, CODE_COMMENT, CODE_INLINE_COMMENT
- Each creates CheckGutterIconRenderer$1$1 action

handleActionPerformed(project, commandEnum):
1. Get code content via PsiDocumentManager
2. Switch on commandEnum:
   - CODE_TEST: If Java -> handleUnitTest(); If C/C++/Python -> CppTestService
   - CODE_EXPLAIN/CODE_OPTIMIZE/CODE_SPLIT/CODE_COMMENT/CODE_INLINE_COMMENT:
     -> PluginEditorInlayHintsProvider.handleAction()
```

### 8.2 PluginEditorInlayHintsProvider

```
Implements: InlayHintsProvider

Settings: PluginHintSettings (empty placeholder)
Key: SettingsKey for persistence

Collector: PluginEditorInlayHintsProvider$1 (FactoryInlayHintsCollector)

collect() method logic:
1. If no API key -> return true (skip)
2. Get VirtualFile from editor
3. Check if element is PsiMethod/PyFunction/JSFunction/TSFunction
4. If PsiTypeParameter -> skip
5. If not PsiMethod and lineToolsType != LINE -> skip
6. If invalid Java method -> skip
7. Get editor actions from PermissionEnum.getEditorAction()
8. If no actions -> skip
9. Get anchor offset for inlay
10. If line count >= 20 and CODE_SPLIT in actions -> remove CODE_SPLIT
11. If PsiMethod and lineToolsType == LINE:
    -> addLineAction() (line-level action buttons)
12. If PsiMethod and lineToolsType == ICON:
    -> addGroupAction() (icon group action)
13. If not PsiMethod:
    -> addLineAction() (line-level for non-Java)
```

---

## 9. Dialog Analysis

### 9.1 DiffDialog

```
Extends: DialogWrapper

Fields:
- byte: Project
- enum: SimpleDiffRequest

Constructor(Project, SimpleDiffRequest):
- Set title from BasicActionsBundle
- Call init()

createCenterPanel():
- Create JPanel with BorderLayout
- Create DiffRequestPanel via DiffManager
- Set diff request
- Add BOTTOM_PANEL context hint

createActions():
- OK action: "Accept" (deobfuscated)
- Cancel action: "Reject" (deobfuscated)
- Order: [Reject, Accept]

doOKAction():
1. Get left/right VirtualFiles from request UserData
2. Get suggested code from DIFF_SUGGEST_CODE key
3. Read left file content as UTF-8
4. Remove CR characters
5. Write content to right file via WriteCommandAction
6. Track accept count via EditorManagerServiceImpl.acceptCount()
7. Close dialog
```

### 9.2 UnitTestDialog

```
Extends: DialogWrapper

Fields:
- char: JBCheckBox (generate by template checkbox)
- int: JRadioButton (template enabled radio)
- new: String (selected template)
- long: ComboBox (model selector)
- super: ComboBox (language selector)
- for: JRadioButton (template disabled radio)
- if: JLabel (info label)
- case: ExcludeMethodConfigurable (method exclusion config)
- try: String (source code)
- float: JPanel (content panel)
- byte: Project
- enum: JPanel (settings panel)

Key features:
- Model selection ComboBox
- Language selection ComboBox
- Template enable/disable radio buttons
- Method exclusion configuration
- Generate by template switch
```

### 9.3 BatchUnitTestDialog

```
Extends: DialogWrapper

Fields (28 total, heavily obfuscated):
- Multiple ComboBox controls (model, language, framework, etc.)
- JRadioButton controls (template on/off)
- JBCheckBox controls
- JBTextField (custom template path)
- TextFieldWithBrowseButton (file browser)
- ExcludeMethodConfigurable
- Multiple JPanel sections
- Project and Module references
```

---

## 10. Theme Change System

### 10.1 ThemeChangeListener

```
Implements: ApplicationComponent

Fields:
- float: int (previous font size)
- byte: String (previous LAF name)
- enum: Logger

initComponent():
- Subscribe to LafManagerListener.TOPIC (look and feel changes)
- Subscribe to EditorColorsManager.TOPIC (color scheme changes)

Theme Change Flow:
1. Detect LAF change via LafManager
2. Get new LookAndFeel name via reflection
3. If previous LAF is blank -> store current LAF
4. If font size not set -> store current console font size
5. If LAF name changed -> call changeTheme()
6. Update stored LAF name

changeTheme(themeName, fontSize):
1. Get tool window name from BasicActionsBundle
2. For each valid project:
   a. Create theme JSON object:
      - "theme": getTheme(themeName, toolWindow) -> "dark" or "light"
      - "fontSize": fontSize value
      - "type": SETTING_CHANGE_THEME
      - "data": theme object
   b. Send to WebView via SocketMessageHandleListener.send2Web()
   c. Update StatusBarPopup

getTheme(themeName, toolWindow):
- If themeName contains "dark" (deobfuscated):
  - Set StatusBarIcon to dark variant
  - Set ToolWindow icon to dark variant
  - Return "dark"
- Otherwise:
  - Set StatusBarIcon to light variant
  - Set ToolWindow icon to light variant
  - Return "light"

initTheme() (called on WebView load):
- Get current global scheme name and font size
- Call changeTheme() to sync WebView with IDE theme
```

---

## 11. Style System Constants

### 11.1 Style.Colors

| Property | Light RGB | Dark RGB | Description |
|----------|-----------|----------|-------------|
| BLUE | 5083390 (0x4D7F7E) | Same | Blue accent color |
| GREY | 13290708 (0xCAD7D4) | 5198166 (0x4F5E56) | Grey text color |
| SEPARATOR_COLOR | Gray.xCD/Gray.x4D | Named color | Separator line color |

### 11.2 Style.Colors.InlineChat

| Property | Description |
|----------|-------------|
| background | Light: 16382715 (0xFAEBEB), Dark: named color "Editor.SearchField" from Gray.x99/Gray.x78 |
| border | SEPARATOR_COLOR.darker() / SEPARATOR_COLOR |

### 11.3 Style.Borders

| Property | Insets (top, left, bottom, right) |
|----------|----------------------------------|
| messageHeaderBorder | (16, 1, 12, 12) |
| topMessageBorder | (1, 1, 16, 0) |

### 11.4 Font Sizes

| Property | Size Modifier |
|----------|--------------|
| medium | JBFont.label().plain() (base) |
| small | medium.lessOn(1.0) |
| large | medium.biggerOn(1.0) |
| xSmall | medium.lessOn(2.0) |
| xLarge | medium.biggerOn(3.0) |
| xxLarge | medium.biggerOn(5.0) |

---
