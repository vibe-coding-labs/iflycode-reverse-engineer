package com.aicode.service.editor;

import com.aicode.exception.RequestTimeoutException;
import com.aicode.inline.controller.ChatInputController;
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/* compiled from: pc */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/editor/TipTypedHandlerDelegate.class */
public class TipTypedHandlerDelegate extends TypedHandlerDelegate {

    /* renamed from: enum, reason: not valid java name */
    private static final Key<Long> f612enum = Key.create(RequestTimeoutException.H("\u00118\u0004\t\u00155~\u0005 \b5>$\u0016&&5\u0001=\u0001"));

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m297enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            default:
                H = ChatInputController.H("\"\u001d\u001f\u0001\b\fWAc)\u0011��G+\r \u001a,\u0012\u0007\u001e^\u0003\u001e-2\u001b\u001f\r\u0010\u0001_\\R\u0001YE\u00065\u007fW\rUR\u0001^\u001d\t\u0005\u000eC\u0001\u001d\nR\u001c\u001cU\f\u001b\u0016\u001a");
                i = a;
                break;
            case 5:
                do {
                } while (0 != 0);
                H = RequestTimeoutException.H("\u0005*\u000e4\u0002\u0018)\bp\u001c4\u0004\u001530U~\t\u007fU*X=\u00044\u0012Q>?\u0005y\n5\u0005'\u0001:U/\u0015<\u001d");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            default:
                i2 = 3;
                break;
            case 5:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            case 2:
            default:
                objArr[0] = ChatInputController.H("\u001c\u0011\u000b\u001a\u0015\u0004");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = RequestTimeoutException.H("\u0003&\u001a+\u00053\u0005");
                i3 = a;
                break;
            case 3:
                objArr[0] = ChatInputController.H("\u0004\u0007\u0016\u0013");
                i3 = a;
                break;
            case 4:
                objArr[0] = RequestTimeoutException.H("4\u001a8\u0010\u0015\u0019 \u0014");
                i3 = a;
                break;
            case 5:
                objArr[0] = ChatInputController.H("\b,\"A\u0003\u000e\b\u001d\u001a\u0016P,6\u0004\f\u0010\u0016\u0016P\u001e\u0013\u001b\n\n\u001b|\u000b\u001b\u000e/\u000e\u0002\u001b\u00144\u0017\u0014\u0007\u0003\u0017\f6\u001b\u0015\u0010\u0005\u000f\u000e\u0013");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            default:
                objArr[1] = RequestTimeoutException.H("\u0005tWN!5\u001e*��\u0004o?\b7\u00129\u00124_\u00188=\u00014\b~$0\b\u0004\b7\u0003\u0015\u00181\u001f=\u00145\u0003\u0016\u00168\u0010&\u0001$\u0014");
                i4 = a;
                break;
            case 5:
                do {
                } while (0 != 0);
                objArr[1] = ChatInputController.H("\u001e\u0013\u001c\f\u001d\u0017=\u001a\u001f\u000b!\u001b\u001e\u001f\u0012");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            default:
                objArr[2] = RequestTimeoutException.H("<\u001f% <\u00164\u0018)\u0001%) \u0014\u0016\u000e5\u0003\u0013\u001d0'$\u00135\u0005");
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                do {
                } while (0 != 0);
                objArr[2] = ChatInputController.H("\u001e\u0013\u001c\f\u001d\u0017=\u001a\u001f\u000b!\u001b\u001e\u001f\u0012");
                break;
            case 5:
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            default:
                throw new IllegalArgumentException(format);
            case 5:
                throw new IllegalStateException(format);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static boolean getPendingTypeOverAndReset(@NotNull Editor editor) {
        if (editor == null) {
            m297enum(0);
        }
        Long l = (Long) f612enum.get(editor);
        if (l != null) {
            f612enum.set(editor, (Object) null);
            return l.longValue() == editor.getDocument().getModificationStamp();
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @NotNull
    public TypedHandlerDelegate.Result beforeCharTyped(char c, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file, @NotNull FileType a) {
        if (project == null) {
            m297enum(1);
        }
        if (editor == null) {
            m297enum(2);
        }
        if (file == null) {
            m297enum(3);
        }
        if (a == null) {
            m297enum(4);
        }
        Project project2 = (c == ')' || c == ']' || c == '}' || c == '\"' || c == '\'' || c == '>' || c == ';') ? 1 : 0;
        if (project2 != null && CommandProcessor.getInstance().getCurrentCommand() != null) {
            f612enum.set(editor, Long.valueOf(editor.getDocument().getModificationStamp()));
        } else {
            f612enum.set(editor, (Object) null);
        }
        TypedHandlerDelegate.Result result = TypedHandlerDelegate.Result.CONTINUE;
        if (result == null) {
            m297enum(5);
        }
        return result;
    }
}
