package com.aicode.service.editor;

import com.aicode.service.EditorManagerService;
import com.aicode.settings.AICodeRequestSettings;
import com.aicode.util.Maps;
import com.aicode.util.PropertyUtils;
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/* compiled from: sc */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/editor/CodeTipTypedHandlerDelegate.class */
public class CodeTipTypedHandlerDelegate extends TypedHandlerDelegate {

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f563enum = Logger.getInstance(CodeTipTypedHandlerDelegate.class);

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m279enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 1:
            case 2:
            default:
                H = PropertyUtils.H("\u0017s*o=bb/AP\"hfQ\t\u007f\u0002o>p>%\u0006@)m?`3u41M\u001803lt 1kji5#'\u000bD4dnw(dep&48t+|");
                i = a;
                break;
            case 3:
                do {
                } while (0 != 0);
                H = Maps.H("$\u0016\n-\u0007��\f0O>��-\u0005>��xV<k\\\u0017x��$\u0017,F4\u000f(U;\u0001,\u0011*\u0001s\u001a=��<");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            default:
                i2 = 3;
                break;
            case 3:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = PropertyUtils.H("b1{<d$d");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = Maps.H("\n7\u001d<\u0003\"");
                i3 = a;
                break;
            case 2:
                objArr[0] = PropertyUtils.H("0h+u");
                i3 = a;
                break;
            case 3:
                objArr[0] = Maps.H("\n\u00079^,<\n\u0017 \u0014b\u0017=\u0017/ \u0016\u0005s\n7\f-\u0002#K\u001b\u001c+ -\r(9(\u0014=\u0002\u0012\u00012\u0011%\u0001* =\u00036\u0013)\u00185");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            case 2:
            default:
                objArr[1] = PropertyUtils.H("U\"wip.s\u0019E.3!`\u0004W2o7*\"t/e\u0005OlW#\u007f#E'i\u0013i b\u0002y&~*u\"b\u0001w/q1`3u");
                i4 = a;
                break;
            case 3:
                do {
                } while (0 != 0);
                objArr[1] = Maps.H("\u00034\u0010*\u000f\u0019\u0011,��\u0003\u001b8\u0019 ");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            case 1:
            case 2:
            default:
                objArr[2] = PropertyUtils.H("$x+z,Q0f,D9q2`");
                break;
            case 3:
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            case 2:
            default:
                throw new IllegalArgumentException(format);
            case 3:
                throw new IllegalStateException(format);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NotNull
    public TypedHandlerDelegate.Result checkAutoPopup(char c, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile a) {
        TypedHandlerDelegate.Result checkAutoPopup;
        if (project == null) {
            m279enum(0);
        }
        if (editor == null) {
            m279enum(1);
        }
        if (a == null) {
            m279enum(2);
        }
        if (AICodeRequestSettings.settings().isShowIdeCodeTips() || !EditorManagerService.getInstance().hasCacheData(editor, c)) {
            checkAutoPopup = super.checkAutoPopup(c, project, editor, a);
        } else {
            f563enum.debug(Maps.H("$;\u0001\u0011&\u00189\r6\u0002y��1%|,<\u0001<98\u0014x\u0003 5\f\u0014x\u000f4\u00079\u0013)\u0005|\u00010\u00141\f9D4\u0017u\u0011+\r1%\u000f��-\u0011i\u0007!q\f\u00183\u0018!\u0015*\u001d("));
            checkAutoPopup = TypedHandlerDelegate.Result.STOP;
        }
        if (checkAutoPopup == null) {
            m279enum(3);
        }
        return checkAutoPopup;
    }
}
