package com.aicode.action;

import com.aicode.action.click.PluginAnAction;
import com.aicode.enums.CodeTipRequestType;
import com.aicode.language.AICodeLanguageInfo;
import com.aicode.service.EditorManagerService;
import com.aicode.service.editor.EditorUtil;
import com.aicode.util.ApplicationUtil;
import com.aicode.util.IndentLineUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: pl */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/RequestCodeGenerateAction.class */
public class RequestCodeGenerateAction extends PluginAnAction implements CodeAction {

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f9enum = LoggerFactory.getLogger(RequestCodeGenerateAction.class);

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m34enum(int a) {
        String H = IndentLineUtil.H("8T\tD\u001eI\u001b^TM[\u0019hW1O\u001fz\u001a\\\u0013��\u0019W\u001cP4c\u0001O\f\u0001X\u0005.%_O\u0012\u000bZSF\u0012\b\u0004\u0003D\fT{j\u0016RNS\u0016\f\u001b_\u0018G");
        Object[] objArr = new Object[3];
        objArr[0] = AICodeLanguageInfo.H("\u0013");
        objArr[1] = IndentLineUtil.H("S\u0010MFW\u0007R6b\u0010\u0005\u001fB\u000bI2lPr\u0011Z\nE\u001bC8K\nT8E5a\u000bG\u001aT2O\u0001C\u001bE");
        switch (a) {
            case 0:
            default:
                objArr[2] = AICodeLanguageInfo.H("1\u0005;\u000f3\u0013");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = IndentLineUtil.H("P\u001cT2k\u0017v\u000bC\u0015C\u0007G\u0011O");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void update(@NotNull AnActionEvent a) {
        if (a == null) {
            m34enum(0);
        }
        Editor editor = (Editor) a.getData(CommonDataKeys.EDITOR);
        a.getPresentation().setEnabled(Objects.nonNull(editor) && EditorUtil.isSelectedEditor(editor));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void actionPerformed(@NotNull AnActionEvent a) {
        if (a == null) {
            m34enum(1);
        }
        EditorManagerService editorManagerService = EditorManagerService.getInstance();
        Editor editor = (Editor) a.getData(CommonDataKeys.EDITOR);
        if (!Objects.nonNull(editor) || !EditorUtil.isSelectedEditor(editor) || !editorManagerService.isAvailable(editor) || !ApplicationUtil.isSupportLanguage(editor).booleanValue()) {
            return;
        }
        editorManagerService.editorChanged(editor, CodeTipRequestType.Manual, true);
    }
}
