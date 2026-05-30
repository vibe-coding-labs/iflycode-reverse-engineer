package Q;

import com.aicode.action.CodeAction;
import com.aicode.action.click.PluginAnAction;
import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.aicode.language.AICodeLanguageInfo;
import com.aicode.service.EditorManagerService;
import com.aicode.util.MessageBundle;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

/* compiled from: bk */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:Q/Sa.class */
public abstract class Sa extends PluginAnAction implements CodeAction {
    public abstract boolean doCycleAction(@NotNull Editor editor);

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m0enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            default:
                H = AICodeLanguageInfo.H("\u000b4 \n\n��3\u0002g\u001b\u001e>lZ\u001dhn\ta[7U2\u001b4\u0002[$kAY:.\u000e:\f*U1\u001b+\u001a");
                i = a;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                do {
                } while (0 != 0);
                H = InlineChatStatusServiceKt.H("\u0007\u0011:\r(\u0005\u0017(&E\u0014,i,\u0003\u00072-(\u0014)@\t=tB\u0016;=\t?HaF._e\u000f\u001f|#PU{:L \u001d5\u0017}\u0016*\u0014Y>c\u0003\u0015+%��");
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
                objArr[0] = AICodeLanguageInfo.H("\r(\u001bT+mV\u0016,.U.\u001d0\u001c0��h7\u00199pG\u0018+?.&\u000e\u0005\u0016+\u0007(\u0018");
                i3 = a;
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                objArr[0] = InlineChatStatusServiceKt.H("\t");
                i3 = a;
                break;
            case 3:
                objArr[0] = AICodeLanguageInfo.H("!\u00116\u001a(\u0004");
                i3 = a;
                break;
            case 4:
                objArr[0] = InlineChatStatusServiceKt.H("\u00115hW/;1\u0018");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            default:
                objArr[1] = AICodeLanguageInfo.H(" \u0013\u000f\u001deG\u0017!%\u001d\u0007\u0017*\u0001\u000b\u000b?\u0002");
                i4 = a;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                do {
                } while (0 != 0);
                objArr[1] = InlineChatStatusServiceKt.H("=&\u0001b\t/��2\u001c O\u0018?rJ\u00140f-/\u001b2\u0011<\u001b14\u0010,G@\u000f7&\u0002");
                i4 = a;
                break;
        }
        switch (i4) {
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = AICodeLanguageInfo.H("1\u0005;\u000f3\u0013");
                break;
            case 2:
                objArr[2] = InlineChatStatusServiceKt.H("\t%\u00174\u0017+0\u001c.`L\t3,\b");
                break;
            case 3:
            case 4:
                objArr[2] = AICodeLanguageInfo.H("\b\"kB:1(\u0016*)%\u00071\u0007)\u0011");
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

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void update(@NotNull AnActionEvent a) {
        if (a == null) {
            m0enum(1);
        }
        Editor editor = (Editor) a.getData(CommonDataKeys.EDITOR);
        a.getPresentation().setEnabled(editor != null && EditorManagerService.getInstance().hasTipInlays(editor));
    }

    private static void Nd(@NotNull Editor editor, @NotNull String hintText) {
        if (editor == null) {
            m0enum(3);
        }
        if (hintText == null) {
            m0enum(4);
        }
        int offset = editor.getCaretModel().getOffset();
        HintManager.getInstance().showErrorHint(editor, hintText, offset, offset, (short) 4, 1033, 1500);
    }

    public void actionPerformed(@NotNull AnActionEvent a) {
        if (a == null) {
            m0enum(2);
        }
        Editor editor = (Editor) a.getData(CommonDataKeys.EDITOR);
        if (editor == null || doCycleAction(editor)) {
            return;
        }
        Nd(editor, getWarningHintText());
    }

    @NotNull
    public String getWarningHintText() {
        String str = MessageBundle.get(InlineChatStatusServiceKt.H(",\u00013\u001a8\u001cW2i\r\u000f79\u001f"));
        if (str == null) {
            m0enum(0);
        }
        return str;
    }
}
