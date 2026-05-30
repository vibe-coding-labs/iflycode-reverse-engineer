package Q;

import com.aicode.action.click.PluginAnAction;
import com.aicode.exception.RequestCancelException;
import com.aicode.message.BasicActionsBundle;
import com.aicode.service.EditorManagerService;
import com.aicode.util.Application;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

/* compiled from: dh */
/* renamed from: Q.sa, reason: case insensitive filesystem */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:Q/sa.class */
public abstract class AbstractC0001sa extends PluginAnAction {
    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m2enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            default:
                H = Application.H("M@^F��8\u007f|!o`rfbq6(}\u001f\u0017=m~erv%hay5dhzD@ m}emn");
                i = a;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                do {
                } while (0 != 0);
                H = RequestCancelException.H(">jE0]2Z'\u001f>K1\u001c\u001bN\b\u000bVW)\\wD2M9I&H>rGX=Qb\u00108Rs\u001a+\nfO{m\u0012\fl\u0002+_#\u00141ZxJ6P7");
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
                objArr[0] = Application.H("m^_a,zsnf`)ona\u007fb`\u001es,>gb`aqEaipUtm]W\u000f.gynl");
                i3 = a;
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                objArr[0] = RequestCancelException.H(">");
                i3 = a;
                break;
            case 3:
                objArr[0] = Application.H("+)zdnp");
                i3 = a;
                break;
            case 4:
                objArr[0] = RequestCancelException.H("\\:Q,p&D/");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            default:
                objArr[1] = Application.H("fgqQo\u007f{\u007fciy[ 9Guyv");
                i4 = a;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                do {
                } while (0 != 0);
                objArr[1] = RequestCancelException.H("4[>\u00109M S?eH\u001e{V,_9\u001b\u0012]+P1]8t$\u0010|G\u0006I4X6~;P*S5");
                i4 = a;
                break;
        }
        switch (i4) {
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = Application.H(";=wqug");
                break;
            case 2:
                objArr[2] = RequestCancelException.H("\u0006\u001clK*^\u0007Q!Y7V.Y?");
                break;
            case 3:
            case 4:
                objArr[2] = Application.H("vnazVonbTe/?}yoe");
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

    public abstract boolean doCycleAction(@NotNull Editor editor);

    public void actionPerformed(@NotNull AnActionEvent a) {
        if (a == null) {
            m2enum(2);
        }
        if (a == null) {
            throw new RuntimeException();
        }
        Editor editor = (Editor) a.getData(CommonDataKeys.EDITOR);
        if (editor != null && !doCycleAction(editor)) {
            Nd(editor, getWarningHintText());
        }
    }

    private static void Nd(@NotNull Editor editor, @NotNull String hintText) {
        if (editor == null) {
            m2enum(3);
        }
        if (hintText == null) {
            m2enum(4);
        }
        int offset = editor.getCaretModel().getOffset();
        HintManager.getInstance().showErrorHint(editor, hintText, offset, offset, (short) 4, 1033, 1500);
    }

    @NotNull
    public String getWarningHintText() {
        String message = BasicActionsBundle.message(RequestCancelException.H("\u0006\u0016{M!UyU(W?@\fc\u0019D\u001eM(^!KqQ7{>k\u001by)^1I#g\u000eQ\u0016[<J0"), new Object[0]);
        if (message == null) {
            throw new RuntimeException();
        }
        if (message == null) {
            m2enum(0);
        }
        return message;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void update(@NotNull AnActionEvent a) {
        if (a == null) {
            m2enum(1);
        }
        if (a != null) {
            Editor editor = (Editor) a.getData(CommonDataKeys.EDITOR);
            a.getPresentation().setEnabled(editor != null && EditorManagerService.getInstance().hasTipInlays(editor));
            return;
        }
        throw new RuntimeException();
    }
}
