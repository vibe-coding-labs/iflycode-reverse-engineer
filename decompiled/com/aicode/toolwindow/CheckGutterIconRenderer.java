package com.aicode.toolwindow;

import com.aicode.PluginStartupActivity;
import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.agent.dto.chat.PresentationDataDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.service.CommonService;
import com.aicode.enums.LanguageEnum;
import com.aicode.icons.Icons;
import com.aicode.test.CppTestService;
import com.aicode.util.AICodeUtils;
import com.aicode.util.PsiUtils;
import com.aicode.util.StringUtils;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.Separator;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.Icon;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/toolwindow/CheckGutterIconRenderer.class */
public class CheckGutterIconRenderer extends GutterIconRenderer {
    private RangeHighlighter highlighter;
    private String type;
    private int lineNumber;
    private Editor editor;
    private PresentationDataDto presentationDataDto;
    private List<CommandEnum> commandEnums;
    private AnAction[] anActions;

    public CheckGutterIconRenderer(PresentationDataDto presentationDataDto, int lineNumber, String type, RangeHighlighter highlighter, Editor editor, List<CommandEnum> commandEnums) {
        this.presentationDataDto = presentationDataDto;
        this.type = type;
        this.highlighter = highlighter;
        this.editor = editor;
        this.lineNumber = lineNumber;
        this.commandEnums = commandEnums;
    }

    public Icon getIcon() {
        return Icons.isUnderDarcula() ? IconLoader.getIcon("/icons/toolWindow_dark.svg", Icons.class) : IconLoader.getIcon("/icons/toolWindow.svg", Icons.class);
    }

    public String getTooltipText() {
        return "";
    }

    public GutterIconRenderer.Alignment getAlignment() {
        return GutterIconRenderer.Alignment.LEFT;
    }

    public boolean equals(Object obj) {
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String getType() {
        return this.type;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public PresentationDataDto getPresentationDataDto() {
        return this.presentationDataDto;
    }

    @Nullable
    public AnAction getClickAction() {
        Integer startRangeLine = null;
        Integer endRangeLine = null;
        String path = null;
        try {
            CodeInfoDto codeInfoDto = this.presentationDataDto.getCodeInfoDto();
            List<CodeInfoDto.RangeDTO> range = codeInfoDto.getRange();
            CodeInfoDto.RangeDTO startRange = range.get(0);
            CodeInfoDto.RangeDTO endRange = range.get(1);
            startRangeLine = startRange.getLine();
            endRangeLine = endRange.getLine();
            path = codeInfoDto.getPath();
        } catch (Exception e) {
        }
        if (startRangeLine != null && endRangeLine != null && StringUtils.isNotBlank(path)) {
            CommonService.jumpToFileByIndex(this.editor.getProject(), path, startRangeLine, endRangeLine, false);
        }
        return super.getClickAction();
    }

    @Nullable
    public ActionGroup getPopupMenuActions() {
        ActionGroup popupMenuActions = new ActionGroup() { // from class: com.aicode.toolwindow.CheckGutterIconRenderer.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "com/aicode/toolwindow/CheckGutterIconRenderer$1", "getChildren"));
            }

            public AnAction[] getChildren(@Nullable final AnActionEvent e) {
                if (CollectionUtils.isEmpty(CheckGutterIconRenderer.this.commandEnums)) {
                    return new AnAction[0];
                }
                int size = CheckGutterIconRenderer.this.commandEnums.size();
                CheckGutterIconRenderer.this.anActions = new AnAction[(size + size) - 1];
                for (int i = 0; i < size; i++) {
                    final CommandEnum commandEnum = CheckGutterIconRenderer.this.commandEnums.get(i);
                    CheckGutterIconRenderer.this.anActions[i * 2] = new AnAction(commandEnum.getDesc(), commandEnum.getType(), null) { // from class: com.aicode.toolwindow.CheckGutterIconRenderer.1.1
                        private static /* synthetic */ void $$$reportNull$$$0(int i2) {
                            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "anActionEvent", "com/aicode/toolwindow/CheckGutterIconRenderer$1$1", "actionPerformed"));
                        }

                        public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
                            if (anActionEvent == null) {
                                $$$reportNull$$$0(0);
                            }
                            Editor editor = (Editor) e.getData(CommonDataKeys.EDITOR);
                            if (editor != null) {
                                SelectionModel selectionModel = editor.getSelectionModel();
                                selectionModel.removeSelection();
                            }
                            if (StringUtils.isBlank(PluginStartupActivity.getApiKey())) {
                                return;
                            }
                            CheckGutterIconRenderer.this.handleActionPerformed(anActionEvent.getProject(), commandEnum);
                        }
                    };
                    if (i < size - 1) {
                        CheckGutterIconRenderer.this.anActions[(i * 2) + 1] = Separator.create();
                    }
                }
                AnAction[] anActionArr = CheckGutterIconRenderer.this.anActions;
                if (anActionArr == null) {
                    $$$reportNull$$$0(0);
                }
                return anActionArr;
            }
        };
        return popupMenuActions;
    }

    public AnAction[] getAnActions() {
        return this.anActions;
    }

    public void handleActionPerformed(Project project, CommandEnum commandEnum) {
        CodeInfoDto codeInfoDto = this.presentationDataDto.getCodeInfoDto();
        AtomicInteger startOffset = new AtomicInteger();
        AtomicInteger endOffset = new AtomicInteger();
        ApplicationManager.getApplication().invokeAndWait(() -> {
            try {
                Document document = this.editor.getDocument();
                List<CodeInfoDto.RangeDTO> range = codeInfoDto.getRange();
                CodeInfoDto.RangeDTO startRangeDTO = range.get(0);
                CodeInfoDto.RangeDTO endRangeDTO = range.get(1);
                Integer startLine = startRangeDTO.getLine();
                Integer endLine = endRangeDTO.getLine();
                startOffset.set(document.getLineStartOffset(startLine.intValue()));
                endOffset.set(document.getLineEndOffset(endLine.intValue()));
                PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(this.editor.getDocument());
                if (psiFile == null) {
                    throw new RuntimeException();
                }
                codeInfoDto.setContent(AICodeUtils.getPsiMethodContent(psiFile, startOffset.get(), endOffset.get()).getText());
                codeInfoDto.setAllContent(psiFile.getText());
            } catch (Throwable th) {
            }
        });
        switch (commandEnum) {
            case CODE_TEST:
                if (LanguageEnum.JAVA.getSuffix().equalsIgnoreCase(codeInfoDto.getLanguage())) {
                    ApplicationManager.getApplication().invokeLater(() -> {
                        try {
                            PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(this.editor.getDocument());
                            if (psiFile == null) {
                                throw new RuntimeException();
                            }
                            PsiMethod psiMethodContent = AICodeUtils.getPsiMethodContent(psiFile, startOffset.get(), endOffset.get());
                            if (psiMethodContent == null || !PsiUtils.instanceOf(psiMethodContent, "com.intellij.psi.PsiMethod")) {
                                throw new RuntimeException();
                            }
                            PluginEditorInlayHintsProvider.handleUnitTest(psiMethodContent, this.editor, project);
                        } catch (Throwable th) {
                            PluginEditorInlayHintsProvider.handleAction(commandEnum, project, codeInfoDto);
                        }
                    });
                    return;
                } else if (LanguageEnum.CPP_LANGUAGE_01.getDescription().equalsIgnoreCase(codeInfoDto.getLanguage()) || LanguageEnum.C_LANGUAGE_01.getDescription().equalsIgnoreCase(codeInfoDto.getLanguage()) || StringUtils.equalsIgnoreCase(LanguageEnum.PYTHON_LANGUAGE_01.getDescription(), codeInfoDto.getLanguage())) {
                    CppTestService.resolveCppTest(project, this.editor, codeInfoDto.getLanguage(), null);
                    return;
                } else {
                    PluginEditorInlayHintsProvider.handleAction(commandEnum, project, codeInfoDto);
                    return;
                }
            case CODE_EXPLAIN:
            case CODE_OPTIMIZE:
            case CODE_SPLIT:
            case CODE_COMMENT:
            case CODE_INLINE_COMMENT:
                PluginEditorInlayHintsProvider.handleAction(commandEnum, project, codeInfoDto);
                return;
            default:
                return;
        }
    }
}
