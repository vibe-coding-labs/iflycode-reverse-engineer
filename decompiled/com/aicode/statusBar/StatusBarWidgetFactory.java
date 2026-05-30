package com.aicode.statusBar;

import com.aicode.inline.ide.IdeAction;
import com.aicode.message.BasicActionsBundle;
import com.aicode.util.PropertyUtils;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.impl.status.widget.StatusBarEditorBasedWidgetFactory;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/* compiled from: rc */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/statusBar/StatusBarWidgetFactory.class */
public class StatusBarWidgetFactory extends StatusBarEditorBasedWidgetFactory {
    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m310enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            default:
                H = PropertyUtils.H("\u0016O,`\u0018t;lqk\u0005C+{4'PQe951(g4dvo\u000bGcf$b#s#:>r7`");
                i = a;
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                H = IdeAction.H("?G\fU\u0013P\u0011@YT'qK`6\\P!,~\u0012\u0015\u001bA\fT\u0012Q\rW:#L\u0005\u000b\u0014}y\u0005\bKVC\u0003\u001c\u0004\u0013@?sKN\u0006V^W��\u000e\u0016F\u001fT");
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
                i2 = 3;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = PropertyUtils.H("E9llu?b8d4)\u0013C\"`%t7C93\u0015e$f2c\u0014`\u0016d*p&s\"G,y$h)u");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = IdeAction.H("E\u0017A\u0012V\u0010L");
                i3 = a;
                break;
            case 2:
                objArr[0] = PropertyUtils.H(":s4`>x");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            default:
                objArr[1] = IdeAction.H("+b\u001fd��Q\u000eY\u0004W6R\u001e]");
                i4 = a;
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                objArr[1] = PropertyUtils.H("E9llu?b8d4)\u0013C\"`%t7C93\u0015e$f2c\u0014`\u0016d*p&s\"G,y$h)u");
                i4 = a;
                break;
        }
        switch (i4) {
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = IdeAction.H("\bR\fC\nP2G\u001cT\u0016L");
                break;
            case 2:
                objArr[2] = PropertyUtils.H("W*g1y%d\u001as4`>x");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            default:
                throw new IllegalStateException(format);
            case 1:
            case 2:
                throw new IllegalArgumentException(format);
        }
    }

    @NonNls
    @NotNull
    public String getId() {
        return PropertyUtils.H("\u0005Zmw.r3/=v%`2b");
    }

    @NotNull
    public StatusBarWidget createWidget(@NotNull Project a) {
        if (a == null) {
            m310enum(1);
        }
        return new StatusBarPopup(a);
    }

    @Nls
    @NotNull
    public String getDisplayName() {
        String message = BasicActionsBundle.message(IdeAction.H("G\u0006G\u0011Q))\u001bL\u001cE\u0017[KZ\u0011G\u001f]"), new Object[0]);
        if (message == null) {
            m310enum(0);
        }
        return message;
    }

    public void disposeWidget(@NotNull StatusBarWidget a) {
        if (a == null) {
            m310enum(2);
        }
    }
}
