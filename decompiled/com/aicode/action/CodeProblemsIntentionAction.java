package com.aicode.action;

import com.aicode.PluginStartupActivity;
import com.aicode.error.search.Presentation;
import com.aicode.icons.Icons;
import com.aicode.message.BasicActionsBundle;
import com.aicode.service.editor.RequestResultList;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.util.Application;
import com.intellij.codeInsight.daemon.impl.DaemonCodeAnalyzerEx;
import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Iconable;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.Processors;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.Icon;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: cj */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/CodeProblemsIntentionAction.class */
public class CodeProblemsIntentionAction extends BaseIntentionAction implements Iconable {

    /* renamed from: enum, reason: not valid java name */
    private static Logger f6enum = Logger.getInstance(CodeProblemsIntentionAction.class);

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m15enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            default:
                H = RequestResultList.H("[gyPV_}O9FbAU`~\b%A?\u0006i\bmGn[;Gu\\\u0015urQu@s\u000fu\\vD");
                i = a;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                do {
                } while (0 != 0);
                H = Application.H("Hxhych 9\u0003F`~$GKiD}g}c,r`~nhcyka0\u000e\u000f}*4xc&+~:2z*by}y\u0001Llt4ul*ayba");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            default:
                i2 = 2;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                i2 = 3;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = RequestResultList.H("uiY4H\u007fGwNt\fxHs\\Ra5koVtshGb^xBh`t\\PicLo\\\\Lo@uF");
                i3 = a;
                break;
            case 1:
            case 2:
            case 4:
                do {
                } while (0 != 0);
                objArr[0] = Application.H("g{eeimy");
                i3 = a;
                break;
            case 3:
                objArr[0] = RequestResultList.H("d]~ZvLt\\");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            default:
                objArr[1] = Application.H("pl~[ivy");
                i4 = a;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                do {
                } while (0 != 0);
                objArr[1] = RequestResultList.H("uiY4H\u007fGwNt\fxHs\\Ra5koVtshGb^xBh`t\\PicLo\\\\Lo@uF");
                i4 = a;
                break;
        }
        switch (i4) {
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = Application.H("KpAbv`fnnbh");
                break;
            case 2:
                objArr[2] = RequestResultList.H("tAmFqM");
                break;
            case 3:
            case 4:
                objArr[2] = Application.H("jDVKis\u007fechdz~");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            default:
                throw new IllegalStateException(format);
            case 1:
            case 2:
            case 3:
            case 4:
                throw new IllegalArgumentException(format);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private boolean fd(Project a, PsiElement psiElement, Editor a2) {
        TextRange fD = fD(a2);
        List<HighlightInfo> highlights = getHighlights(a2.getDocument(), HighlightSeverity.WEAK_WARNING, a);
        if (!CollectionUtils.isEmpty(highlights)) {
            for (HighlightInfo highlightInfo : highlights) {
                if (fD.intersectsStrict(highlightInfo.getStartOffset(), highlightInfo.getEndOffset())) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile a) {
        if (project == null) {
            m15enum(1);
        }
        if (!StringUtils.isBlank(PluginStartupActivity.getApiKey()) && AICodeSettingsState.getInstance().enableCodeDebug) {
            return fd(project, a.findElementAt(editor.getCaretModel().getOffset()), editor);
        }
        return false;
    }

    @NotNull
    public String getText() {
        String str = BasicActionsBundle.message(Application.H("a\u007faf`\u0007Kgn{s`(Ki}cfxNozdNLDr{by${ivy"), new Object[0]).trim() + BasicActionsBundle.message(RequestResultList.H("E{^xLw\u0005DZYjJZoP}Fw[T@xJKFj]EFtQi]s\u0001oLb\\"), new Object[0]);
        if (str == null) {
            m15enum(0);
        }
        return str;
    }

    @NotNull
    @IntentionFamilyName
    public String getFamilyName() {
        return Application.H("N{s`V|bv{lg|E`yDLwi{yHi{eac");
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void invoke(@NotNull Project project, Editor editor, PsiFile a) throws IncorrectOperationException {
        if (project == null) {
            m15enum(2);
        }
        if (project != null) {
            TextRange fD = fD(editor);
            List<String> GF = GF(project, editor, fD);
            String text = editor.getDocument().getText(fD);
            int lineNumber = editor.getDocument().getLineNumber(fD.getStartOffset());
            if (!CollectionUtils.isEmpty(GF)) {
                Presentation.handleDebug(project, a.getVirtualFile().getPath(), text, "\n\n" + ((String) IntStream.range(0, GF.size()).mapToObj(a2 -> {
                    return String.format(Application.H(",+~"), GF.get(a2));
                }).collect(Collectors.joining(RequestResultList.H("\t\u0010\"")))), lineNumber + 1);
            }
        }
    }

    public Icon getIcon(int i) {
        return Icons.StatusBarIcon;
    }

    public List<HighlightInfo> getHighlights(@NotNull Document document, @Nullable HighlightSeverity minSeverity, @NotNull Project a) {
        if (document == null) {
            m15enum(3);
        }
        if (a == null) {
            m15enum(4);
        }
        ArrayList arrayList = new ArrayList();
        DaemonCodeAnalyzerEx.processHighlights(document, a, minSeverity, 0, document.getTextLength(), Processors.cancelableCollectProcessor(arrayList));
        return arrayList;
    }

    private TextRange fD(Editor a) {
        SelectionModel selectionModel = a.getSelectionModel();
        int selectionStart = selectionModel.getSelectionStart();
        int selectionEnd = selectionModel.getSelectionEnd();
        if (selectionStart == selectionEnd) {
            LogicalPosition logicalPosition = a.getCaretModel().getLogicalPosition();
            selectionStart = a.getDocument().getLineStartOffset(logicalPosition.line);
            selectionEnd = a.getDocument().getLineEndOffset(logicalPosition.line);
        }
        return new TextRange(selectionStart, selectionEnd);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private List<String> GF(Project a, Editor a2, TextRange a3) {
        List<HighlightInfo> highlights = getHighlights(a2.getDocument(), HighlightSeverity.WEAK_WARNING, a);
        ArrayList arrayList = new ArrayList();
        for (HighlightInfo highlightInfo : highlights) {
            if (a3.intersectsStrict(highlightInfo.getStartOffset(), highlightInfo.getEndOffset()) && StringUtils.isNotBlank(highlightInfo.getDescription())) {
                arrayList.add(highlightInfo.getDescription());
            }
        }
        return arrayList;
    }
}
