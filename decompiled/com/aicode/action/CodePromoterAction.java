package com.aicode.action;

import Q.ua;
import com.aicode.diff.GenericUtils;
import com.aicode.service.EditorManagerService;
import com.aicode.service.editor.RequestResultList;
import com.intellij.openapi.actionSystem.ActionPromoter;
import com.intellij.openapi.actionSystem.ActionWithDelegate;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* compiled from: tk */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/CodePromoterAction.class */
public class CodePromoterAction implements ActionPromoter {
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m18enum(int a) {
        String H = GenericUtils.H("\u0012)\u007fe::;)b,0%y\u0011\u0016?l^��\u00115q\";!:\u000b\u000b0)+q|v*vb%5{a?YZ&}/?,#y?7$s9=p->7?");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = RequestResultList.H("S~[rFt[");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = GenericUtils.H("87>7.#'");
                break;
            case 2:
                objArr[0] = RequestResultList.H("|Lo@uF");
                break;
        }
        objArr[1] = GenericUtils.H("\u0005\u0001)c888<=4m+0/-#\u0019P\u00162&/\u000f%6<7$6)\u001937\"4=");
        switch (a) {
            case 0:
            case 1:
            default:
                objArr[2] = RequestResultList.H("Bo@vFnM");
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[2] = GenericUtils.H("#,\u001e=49\u0006:6\u001937\"4=");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static boolean isIdeaVimAction(@NotNull AnAction action) {
        if (action == null) {
            m18enum(2);
        }
        String H = RequestResultList.H("zDv\u0007mSuG\u007f\\Zjr\u000biVxN5_sE");
        if (!action.getClass().getName().startsWith(H)) {
            if ((action instanceof ActionWithDelegate) && ((ActionWithDelegate) action).getDelegate().getClass().getName().startsWith(H)) {
                return false;
            }
            return true;
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public List<AnAction> promote(@NotNull List<? extends AnAction> list, @NotNull DataContext a) {
        if (list == null) {
            m18enum(0);
        }
        if (a == null) {
            m18enum(1);
        }
        if (CommonDataKeys.EDITOR.getData(a) != null && EditorManagerService.getInstance().isAvailable((Editor) CommonDataKeys.EDITOR.getData(a)) && AcceptWordInlaysAction.isSupported((Editor) CommonDataKeys.EDITOR.getData(a))) {
            ArrayList arrayList = new ArrayList(list);
            arrayList.sort((a2, a3) -> {
                boolean z = (a2 instanceof CodeAction) && (a2 instanceof EditorAction);
                boolean z2 = (a3 instanceof CodeAction) && (a3 instanceof EditorAction);
                if (!z || !z2 || (!(a2 instanceof AcceptWordInlaysAction) && !(a3 instanceof AcceptWordInlaysAction))) {
                    if (!isIdeaVimAction(a2) || !isIdeaVimAction(a3)) {
                        return 0;
                    }
                    if (z) {
                        return -1;
                    }
                    return z2 ? 1 : 0;
                }
                return -1;
            });
            return arrayList;
        }
        if (hd((Editor) CommonDataKeys.EDITOR.getData(a)) || list.stream().noneMatch(a4 -> {
            return (a4 instanceof CodeAction) && (a4 instanceof EditorAction);
        })) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList(list);
        arrayList2.sort((a5, a6) -> {
            boolean z = (a5 instanceof CodeAction) && (a5 instanceof EditorAction);
            boolean z2 = (a6 instanceof CodeAction) && (a6 instanceof EditorAction);
            if (!z || !z2 || (!(a5 instanceof AcceptInlaysAction) && !(a6 instanceof AcceptInlaysAction))) {
                if (!isIdeaVimAction(a5) || !isIdeaVimAction(a6)) {
                    return 0;
                }
                if (z) {
                    return -1;
                }
                return z2 ? 1 : 0;
            }
            return -1;
        });
        return arrayList2;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private boolean hd(Editor a) {
        return (a != null && EditorManagerService.getInstance().isAvailable(a) && (AcceptInlaysAction.isSupported(a) || ua.Yf(a))) ? false : true;
    }
}
