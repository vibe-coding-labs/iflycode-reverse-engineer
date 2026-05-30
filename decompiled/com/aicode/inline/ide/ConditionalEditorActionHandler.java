package com.aicode.inline.ide;

import com.aicode.exception.RequestCancelException;
import com.aicode.inline.InlineChatService;
import com.aicode.inline.KeyStrokeHandler;
import com.aicode.util.PositionUtil;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import java.util.Iterator;
import java.util.Set;
import javax.swing.KeyStroke;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ag */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/ide/ConditionalEditorActionHandler.class */
public final class ConditionalEditorActionHandler extends EditorActionHandler {

    /* renamed from: final, reason: not valid java name */
    @NotNull
    private final ConditionalActionConfiguration f407final;

    /* renamed from: try, reason: not valid java name */
    @NotNull
    private final EditorActionHandler f408try;

    /* renamed from: float, reason: not valid java name */
    @NotNull
    private final PredicateFactory f409float;

    /* renamed from: byte, reason: not valid java name */
    private boolean f410byte;

    /* renamed from: enum, reason: not valid java name */
    @NotNull
    private final InlineChatService f411enum;

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public boolean isEnabledForCaret(@NotNull Editor editor, @NotNull Caret caret, @Nullable DataContext a) {
        if (editor == null) {
            m200enum(3);
        }
        if (caret == null) {
            m200enum(4);
        }
        return DC(editor, caret, a) || this.f408try.isEnabled(editor, caret, a);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m200enum(int a) {
        String H = PositionUtil.H("y:R0E=[1\u0013%]0\u001d\r~/m'M$YeX9A\"C;i\bMo\u000f}Fb\u0013,Tb1\u0017\u0013hAbU=C4\u0019'A*\u0019+WbG,]-");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = RequestCancelException.H("=O3S:M%X\u001b^6@/Y)");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = PositionUtil.H("K7[!Z7[-z\u0005Q\fQ6Q'^\u0003V'H7^<@#]0^/");
                break;
            case 2:
                objArr[0] = RequestCancelException.H("1[>T4Q\u0010K%@��Z*R*_>");
                break;
            case 3:
            case 5:
                objArr[0] = PositionUtil.H("W&@-^3");
                break;
            case 4:
                objArr[0] = RequestCancelException.H(";E1Y/");
                break;
        }
        objArr[1] = PositionUtil.H("Q-PbQ)z\u0006\\-\u001a,F4Z-Kqt\tZ`k7[!Z7[-z\u0005Q\bV+L'B\u0001Z=G1W\u0001S,M5T3");
        switch (a) {
            case 0:
            case 1:
            case 2:
            default:
                objArr[2] = RequestCancelException.H("\u00031J*He");
                break;
            case 3:
            case 4:
                do {
                } while (0 != 0);
                objArr[2] = PositionUtil.H("+K\r^![%K:\u007f&@\u0001H+T5");
                break;
            case 5:
                objArr[2] = RequestCancelException.H(" [\u0016G=G6H>");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    public void doExecute(@NotNull Editor editor, @Nullable Caret caret, @Nullable DataContext a) {
        ConditionalEditorActionHandler conditionalEditorActionHandler;
        if (editor == null) {
            m200enum(5);
        }
        if (!this.f410byte) {
            try {
                this.f410byte = true;
                if (DC(editor, caret, a)) {
                    conditionalEditorActionHandler = this;
                    conditionalEditorActionHandler.xa(editor);
                } else {
                    this.f408try.execute(editor, caret, a);
                    conditionalEditorActionHandler = this;
                }
                conditionalEditorActionHandler.f410byte = false;
            } catch (Throwable th) {
                this.f410byte = false;
                throw th;
            }
        }
    }

    private boolean DC(Editor a, Caret a2, DataContext a3) {
        return this.f409float.predicate(this.f407final.getScope()).evaluate(a, a2, a3);
    }

    public ConditionalEditorActionHandler(@NotNull EditorActionHandler originalHandler, @NotNull ConditionalActionConfiguration conditionalActionConfiguration, @NotNull InlineChatService a) {
        if (originalHandler == null) {
            m200enum(0);
        }
        if (conditionalActionConfiguration == null) {
            m200enum(1);
        }
        if (a == null) {
            m200enum(2);
        }
        this.f408try = originalHandler;
        this.f407final = conditionalActionConfiguration;
        this.f411enum = a;
        this.f409float = new DefaultActionScopePredicateFactory(this.f411enum);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private void xa(Editor a) {
        KeyStrokeHandler keyStrokeExecutor = this.f407final.getKeyStrokeExecutorProvider().keyStrokeExecutor(a);
        if (keyStrokeExecutor == null) {
            return;
        }
        Set<KeyStroke> boundKeyStrokes = this.f407final.getBoundKeyStrokes();
        if (boundKeyStrokes.isEmpty()) {
            return;
        }
        Iterator<T> it = boundKeyStrokes.iterator();
        while (it.hasNext() && !keyStrokeExecutor.execute((KeyStroke) it.next())) {
        }
    }
}
