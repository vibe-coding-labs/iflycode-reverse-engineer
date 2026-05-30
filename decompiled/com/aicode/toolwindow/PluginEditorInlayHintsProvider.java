package com.aicode.toolwindow;

import com.aicode.PluginStartupActivity;
import com.aicode.action.click.BaseAction;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.agent.dto.chat.FirstChatMessage;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.enums.PermissionEnum;
import com.aicode.agent.service.ChatService;
import com.aicode.content.util.file.FileUtils;
import com.aicode.enums.LanguageEnum;
import com.aicode.enums.LineToolsTypeEnum;
import com.aicode.icons.Icons;
import com.aicode.message.BasicActionsBundle;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.test.CppTestService;
import com.aicode.test.UnitTestService;
import com.aicode.util.JavaPsiUtils;
import com.aicode.util.PsiUtils;
import com.aicode.util.StringUtils;
import com.intellij.codeInsight.hints.FactoryInlayHintsCollector;
import com.intellij.codeInsight.hints.ImmediateConfigurable;
import com.intellij.codeInsight.hints.InlayHintsCollector;
import com.intellij.codeInsight.hints.InlayHintsProvider;
import com.intellij.codeInsight.hints.InlayHintsSink;
import com.intellij.codeInsight.hints.SettingsKey;
import com.intellij.codeInsight.hints.presentation.InlayPresentation;
import com.intellij.codeInsight.hints.presentation.PresentationFactory;
import com.intellij.codeInsight.hints.presentation.SequencePresentation;
import com.intellij.icons.AllIcons;
import com.intellij.lang.Language;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.util.SmartList;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JPanel;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/toolwindow/PluginEditorInlayHintsProvider.class */
public class PluginEditorInlayHintsProvider implements InlayHintsProvider<PluginHintSettings> {
    private static final Logger LOG = Logger.getInstance(PluginEditorInlayHintsProvider.class);
    private static final SettingsKey<PluginHintSettings> KEY = new SettingsKey<>(BasicActionsBundle.message("group.aicode.EditorActionGroup.text", new Object[0]));

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/toolwindow/PluginEditorInlayHintsProvider$InlCollectResult.class */
    public interface InlCollectResult {
        void onClick(@NotNull Editor editor, @NotNull PsiElement psiElement, @NotNull MouseEvent mouseEvent);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/toolwindow/PluginEditorInlayHintsProvider$InlResult.class */
    public interface InlResult {
        void onClick(@NotNull PsiElement psiElement, @NotNull Editor editor, @NotNull MouseEvent mouseEvent, CommandEnum commandEnum);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 18:
            case 20:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 15:
            case 16:
            case 17:
            case 19:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 18:
            case 20:
            default:
                i2 = 3;
                break;
            case 15:
            case 16:
            case 17:
            case 19:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 0:
            default:
                objArr[0] = "file";
                break;
            case 1:
            case 5:
            case 8:
            case 11:
            case 13:
                objArr[0] = "editor";
                break;
            case 2:
            case 18:
                objArr[0] = "settings";
                break;
            case 3:
                objArr[0] = "inlayHintsSink";
                break;
            case 4:
            case 7:
            case 10:
            case 12:
            case 14:
                objArr[0] = "element";
                break;
            case 6:
            case 9:
                objArr[0] = "sink";
                break;
            case 15:
            case 16:
            case 17:
            case 19:
                objArr[0] = "com/aicode/toolwindow/PluginEditorInlayHintsProvider";
                break;
            case 20:
                objArr[0] = "language";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 18:
            case 20:
            default:
                objArr[1] = "com/aicode/toolwindow/PluginEditorInlayHintsProvider";
                break;
            case 15:
                objArr[1] = "getName";
                break;
            case 16:
                objArr[1] = "getKey";
                break;
            case 17:
                objArr[1] = "getSettingsKey";
                break;
            case 19:
                objArr[1] = "createConfigurable";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            default:
                objArr[2] = "getCollectorFor";
                break;
            case 4:
            case 5:
            case 6:
                objArr[2] = "addGroupAction";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "addLineAction";
                break;
            case 10:
            case 11:
                objArr[2] = "handleCommand";
                break;
            case 12:
                objArr[2] = "handleUnitTest";
                break;
            case 13:
                objArr[2] = "findRealOffsetBySpace";
                break;
            case 14:
                objArr[2] = "getAnchorOffset";
                break;
            case 15:
            case 16:
            case 17:
            case 19:
                break;
            case 18:
                objArr[2] = "createConfigurable";
                break;
            case 20:
                objArr[2] = "isLanguageSupported";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 18:
            case 20:
            default:
                throw new IllegalArgumentException(format);
            case 15:
            case 16:
            case 17:
            case 19:
                throw new IllegalStateException(format);
        }
    }

    @Nullable
    public InlayHintsCollector getCollectorFor(@NotNull PsiFile file, @NotNull Editor editor, @NotNull PluginHintSettings settings, @NotNull InlayHintsSink inlayHintsSink) {
        if (file == null) {
            $$$reportNull$$$0(0);
        }
        if (editor == null) {
            $$$reportNull$$$0(1);
        }
        if (settings == null) {
            $$$reportNull$$$0(2);
        }
        if (inlayHintsSink == null) {
            $$$reportNull$$$0(3);
        }
        return new FactoryInlayHintsCollector(editor) { // from class: com.aicode.toolwindow.PluginEditorInlayHintsProvider.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                switch (i) {
                    case 0:
                    default:
                        objArr[0] = "element";
                        break;
                    case 1:
                        objArr[0] = "editor";
                        break;
                    case 2:
                        objArr[0] = "sink";
                        break;
                }
                objArr[1] = "com/aicode/toolwindow/PluginEditorInlayHintsProvider$1";
                objArr[2] = "collect";
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            public boolean collect(@NotNull PsiElement element, @NotNull Editor editor2, @NotNull InlayHintsSink sink) {
                if (element == null) {
                    $$$reportNull$$$0(0);
                }
                if (editor2 == null) {
                    $$$reportNull$$$0(1);
                }
                if (sink == null) {
                    $$$reportNull$$$0(2);
                }
                if (StringUtils.isBlank(PluginStartupActivity.getApiKey())) {
                    return true;
                }
                VirtualFile file2 = FileDocumentManager.getInstance().getFile(editor2.getDocument());
                if (Objects.isNull(file2) || !PsiUtils.instanceOf(element, "com.intellij.psi.PsiMethod", "com.jetbrains.python.psi.PyFunction", "com.intellij.lang.javascript.psi.impl.JSFunctionImpl", "com.intellij.lang.javascript.psi.ecma6.impl.TypeScriptFunctionImpl") || PsiUtils.instanceOf(element, "com.intellij.psi.PsiTypeParameter")) {
                    return true;
                }
                boolean isPsiMethod = PsiUtils.instanceOf(element, "com.intellij.psi.PsiMethod");
                if (!isPsiMethod && !LineToolsTypeEnum.LINE.getCode().equals(AICodeSettingsState.getInstance().lineToolsType)) {
                    return true;
                }
                if (isPsiMethod && JavaPsiUtils.isInvalidJavaMethod(element)) {
                    return true;
                }
                List<CommandEnum> commandEnums = PermissionEnum.getEditorAction();
                if (CollectionUtils.isEmpty(commandEnums)) {
                    return true;
                }
                try {
                    int offset = PluginEditorInlayHintsProvider.getAnchorOffset(element);
                    if (PsiUtils.getLineCount(offset, element) < 20 && commandEnums.contains(CommandEnum.CODE_SPLIT)) {
                        commandEnums.remove(CommandEnum.CODE_SPLIT);
                    }
                    if (CollectionUtils.isEmpty(commandEnums)) {
                        return true;
                    }
                    PresentationFactory factory = getFactory();
                    Document document = editor2.getDocument();
                    try {
                        int line = document.getLineNumber(offset);
                        int startOffset = document.getLineStartOffset(line);
                        String linePrefix = (String) ApplicationManager.getApplication().runReadAction(() -> {
                            return document.getText(new TextRange(startOffset, offset));
                        });
                        int column = (offset + PluginEditorInlayHintsProvider.this.findRealOffsetBySpace(editor2, linePrefix)) - startOffset;
                        if (isPsiMethod) {
                            if (LineToolsTypeEnum.LINE.getCode().equals(AICodeSettingsState.getInstance().lineToolsType)) {
                                InlResult inlResult = (element2, editor1, event, commandEnum) -> {
                                    PluginEditorInlayHintsProvider.handleCommand(element, editor1, commandEnum);
                                };
                                PluginEditorInlayHintsProvider.addLineAction(element, editor2, sink, commandEnums, factory, inlResult, startOffset, column);
                            } else if (LineToolsTypeEnum.ICON.getCode().equals(AICodeSettingsState.getInstance().lineToolsType)) {
                                InlCollectResult inlCollectResult = PluginEditorInlayHintsProvider.this.getInlCollectResult(commandEnums);
                                PluginEditorInlayHintsProvider.addGroupAction(element, editor2, sink, factory, inlCollectResult, startOffset, column);
                            }
                        } else {
                            InlResult inlResult2 = (element22, editor12, event2, commandEnum2) -> {
                                PluginEditorInlayHintsProvider.handleCommand(element, editor12, commandEnum2);
                            };
                            PluginEditorInlayHintsProvider.addLineAction(element, editor2, sink, commandEnums, factory, inlResult2, startOffset, column);
                        }
                        return true;
                    } catch (Throwable e) {
                        PluginEditorInlayHintsProvider.LOG.info("InterlineTools: " + e.getMessage(), e);
                        return true;
                    }
                } catch (Throwable th) {
                    return true;
                }
            }
        };
    }

    private InlCollectResult getInlCollectResult(List<CommandEnum> commandEnums) {
        InlCollectResult inlResult = (editor, element, event) -> {
            if (editor.getProject() == null) {
                return;
            }
            editor.getSelectionModel().setSelection(element.getTextRange().getStartOffset(), element.getTextRange().getEndOffset());
            ListPopup listPopup = JBPopupFactory.getInstance().createListPopup(new BaseListPopupStep<CommandEnum>("", commandEnums) { // from class: com.aicode.toolwindow.PluginEditorInlayHintsProvider.2
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "com/aicode/toolwindow/PluginEditorInlayHintsProvider$2", "getTextFor"));
                }

                @NotNull
                public String getTextFor(CommandEnum value) {
                    String desc = value.getDesc();
                    if (desc == null) {
                        $$$reportNull$$$0(0);
                    }
                    return desc;
                }

                @Nullable
                public PopupStep<?> onChosen(CommandEnum commandEnum, boolean finalChoice) {
                    editor.getSelectionModel().removeSelection();
                    PluginEditorInlayHintsProvider.handleCommand(element, editor, commandEnum);
                    return FINAL_CHOICE;
                }
            });
            listPopup.showInScreenCoordinates(editor.getComponent(), event.getLocationOnScreen());
        };
        return inlResult;
    }

    private static void addGroupAction(@NotNull PsiElement element, @NotNull Editor editor, @NotNull InlayHintsSink sink, PresentationFactory factory, InlCollectResult inlResult, int startOffset, int column) {
        if (element == null) {
            $$$reportNull$$$0(4);
        }
        if (editor == null) {
            $$$reportNull$$$0(5);
        }
        if (sink == null) {
            $$$reportNull$$$0(6);
        }
        SmartList<InlayPresentation> smartList = new SmartList<>();
        smartList.add(factory.textSpacePlaceholder(column, true));
        smartList.add(factory.smallScaledIcon(Icons.ToolWindowIcon));
        smartList.add(factory.smallScaledIcon(AllIcons.Actions.FindAndShowNextMatchesSmall));
        smartList.add(factory.textSpacePlaceholder(1, true));
        SequencePresentation shiftedPresentation = new SequencePresentation(smartList);
        InlayPresentation finalPresentation = factory.referenceOnHover(shiftedPresentation, (event, translated) -> {
            inlResult.onClick(editor, element, event);
        });
        sink.addBlockElement(startOffset, true, true, 300, finalPresentation);
    }

    private static void addLineAction(@NotNull PsiElement element, @NotNull Editor editor, @NotNull InlayHintsSink sink, List<CommandEnum> commandEnums, PresentationFactory factory, InlResult inlResult, int startOffset, int column) {
        if (element == null) {
            $$$reportNull$$$0(7);
        }
        if (editor == null) {
            $$$reportNull$$$0(8);
        }
        if (sink == null) {
            $$$reportNull$$$0(9);
        }
        List<InlayPresentation> inlayPresentations = new ArrayList<>();
        inlayPresentations.add(factory.textSpacePlaceholder(column, true));
        inlayPresentations.add(factory.text(BasicActionsBundle.message("aicode.plugin.title", new Object[0]) + ": "));
        for (int i = 0; i < commandEnums.size(); i++) {
            InlayPresentation presentationExplain = factory.text(commandEnums.get(i).getDesc());
            int finalI = i;
            InlayPresentation finalPresentationExplain = factory.referenceOnHover(presentationExplain, (event, translated) -> {
                inlResult.onClick(element, editor, event, (CommandEnum) commandEnums.get(finalI));
            });
            inlayPresentations.add(finalPresentationExplain);
            if (i < commandEnums.size() - 1) {
                inlayPresentations.add(factory.text(" | "));
            }
        }
        SequencePresentation shiftedPresentation = new SequencePresentation(inlayPresentations);
        sink.addBlockElement(startOffset, true, true, 300, shiftedPresentation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleCommand(@NotNull PsiElement element, @NotNull Editor editor, CommandEnum commandEnum) {
        if (element == null) {
            $$$reportNull$$$0(10);
        }
        if (editor == null) {
            $$$reportNull$$$0(11);
        }
        if (StringUtils.isBlank(PluginStartupActivity.getApiKey())) {
            return;
        }
        CodeInfoDto codeInfoDto = PsiUtils.getCodeInfo(editor, element);
        switch (commandEnum) {
            case CODE_TEST:
                Project project = editor.getProject();
                String name = ((EditorImpl) editor).getVirtualFile().getName();
                String fileExtension = FileUtils.getFileExtension(name);
                String languageName = element.getLanguage().getDisplayName();
                String language = LanguageEnum.getLanguage(fileExtension);
                if (StringUtils.equalsIgnoreCase(PluginWebsocketClient.getClientName(), "IDEA") && (StringUtils.equalsIgnoreCase(LanguageEnum.JAVA.getDescription(), languageName) || StringUtils.equalsIgnoreCase(LanguageEnum.JAVA.getDescription(), fileExtension))) {
                    handleUnitTest(element, (EditorImpl) editor, project);
                    return;
                }
                if (StringUtils.equals(LanguageEnum.CPP_LANGUAGE_01.getDescription(), languageName) || StringUtils.equals(LanguageEnum.CPP_LANGUAGE_01.getDescription(), language) || StringUtils.equals(LanguageEnum.C_LANGUAGE_01.getDescription(), languageName) || StringUtils.equals(LanguageEnum.C_LANGUAGE_01.getDescription(), language) || StringUtils.equalsIgnoreCase(LanguageEnum.PYTHON_LANGUAGE_01.getDescription(), languageName) || StringUtils.equalsIgnoreCase(LanguageEnum.PYTHON_LANGUAGE_01.getDescription(), language)) {
                    CppTestService.resolveCppTest(project, editor, language, element);
                    return;
                } else {
                    handleAction(commandEnum, editor.getProject(), codeInfoDto);
                    return;
                }
            case CODE_EXPLAIN:
            case CODE_OPTIMIZE:
            case CODE_SPLIT:
            case CODE_COMMENT:
            case CODE_INLINE_COMMENT:
                handleAction(commandEnum, editor.getProject(), codeInfoDto);
                return;
            default:
                return;
        }
    }

    public static void handleUnitTest(@NotNull PsiElement element, EditorImpl editor, Project project) {
        if (element == null) {
            $$$reportNull$$$0(12);
        }
        UnitTestService.handleJavaUnitTestByElement(project, editor, element);
    }

    public static void handleAction(CommandEnum commandEnum, Project project, CodeInfoDto codeInfoDto) {
        FirstChatMessage rightChatMessage2Web = ChatService.getEditorChatMessage2Web(project, commandEnum.getType(), codeInfoDto);
        BaseAction.handleRight(project, rightChatMessage2Web);
    }

    private int findRealOffsetBySpace(@NotNull Editor editor, String linePrefix) {
        if (editor == null) {
            $$$reportNull$$$0(13);
        }
        int tabWidth = editor.getSettings().getTabSize(editor.getProject());
        int totalOffset = 0;
        for (int i = 0; i < linePrefix.length(); i++) {
            if (linePrefix.charAt(i) == '\t') {
                totalOffset += tabWidth;
            }
        }
        return totalOffset;
    }

    public static int getAnchorOffset(@NotNull PsiElement element) {
        if (element == null) {
            $$$reportNull$$$0(14);
        }
        if (PsiUtils.instanceOf(element, "com.jetbrains.python.psi.PyFunction")) {
            return element.getTextRange().getStartOffset();
        }
        for (PsiElement child : element.getChildren()) {
            if (!(child instanceof PsiComment) && !(child instanceof PsiWhiteSpace)) {
                return child.getTextRange().getStartOffset();
            }
        }
        return element.getTextRange().getStartOffset();
    }

    @NotNull
    /* renamed from: createSettings, reason: merged with bridge method [inline-methods] */
    public PluginHintSettings m363createSettings() {
        return new PluginHintSettings();
    }

    @NotNull
    public String getName() {
        String message = BasicActionsBundle.message("group.aicode.EditorActionGroup.text", new Object[0]);
        if (message == null) {
            $$$reportNull$$$0(15);
        }
        return message;
    }

    @NotNull
    public SettingsKey<PluginHintSettings> getKey() {
        SettingsKey<PluginHintSettings> settingsKey = KEY;
        if (settingsKey == null) {
            $$$reportNull$$$0(16);
        }
        return settingsKey;
    }

    @NotNull
    public static SettingsKey<PluginHintSettings> getSettingsKey() {
        SettingsKey<PluginHintSettings> settingsKey = KEY;
        if (settingsKey == null) {
            $$$reportNull$$$0(17);
        }
        return settingsKey;
    }

    public String getPreviewText() {
        return null;
    }

    @NotNull
    public ImmediateConfigurable createConfigurable(@NotNull PluginHintSettings settings) {
        if (settings == null) {
            $$$reportNull$$$0(18);
        }
        ImmediateConfigurable immediateConfigurable = listener -> {
            JPanel panel = new JPanel();
            panel.setVisible(false);
            return panel;
        };
        if (immediateConfigurable == null) {
            $$$reportNull$$$0(19);
        }
        return immediateConfigurable;
    }

    public boolean isLanguageSupported(@NotNull Language language) {
        if (language == null) {
            $$$reportNull$$$0(20);
            return true;
        }
        return true;
    }

    public boolean isVisibleInSettings() {
        return true;
    }
}
