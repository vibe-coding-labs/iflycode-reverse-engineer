package com.aicode.action;

import Q.Sa;
import com.aicode.content.util.file.FileExtensionLanguageDetails;
import com.aicode.util.IndentLineUtil;
import com.intellij.openapi.actionSystem.ActionPromoter;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DataContext;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/TipPromoterAction.class */
public class TipPromoterAction implements ActionPromoter {
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m35enum(int a) {
        String H = FileExtensionLanguageDetails.H("@annntai$pjen\u001cO|w_aje;\u007f||}#9@C{;7'C\u00052oi=(l!8g&hbat\u0001]le4dl;ahbp");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = IndentLineUtil.H("P\u0010X\u001cE\u001aX");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = FileExtensionLanguageDetails.H("efu{xvh");
                break;
        }
        objArr[1] = IndentLineUtil.H("\b!|\\M\u0003V%q\r\u0018\u0014I\u0003A\u001aDAe\u0016P8E4i\u0016R\u000bC2O\u0001C\u001bE");
        objArr[2] = FileExtensionLanguageDetails.H("v{tbrzy");
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Nullable
    public List<AnAction> promote(@NotNull List<? extends AnAction> list, @NotNull DataContext a) {
        if (list == null) {
            m35enum(0);
        }
        if (a == null) {
            m35enum(1);
        }
        for (AnAction anAction : list) {
            if (anAction instanceof Sa) {
                return List.of(anAction);
            }
        }
        return null;
    }
}
