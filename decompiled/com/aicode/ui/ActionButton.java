package com.aicode.ui;

import com.aicode.inline.controller.ChatInputController;
import com.aicode.util.AICodeUtils;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.AnAction;
import org.jetbrains.annotations.NotNull;

/* compiled from: mb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/ui/ActionButton.class */
public class ActionButton {
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m366enum(int a) {
        throw new IllegalArgumentException(String.format(ChatInputController.H(";\u0004\u0013\r\u0015\u0011\u0014\u0002\u0019S\u0019\b^2:\u0017\f:\u0006\u0013\u001e^IT\u0010\u000f\u0013\u0017MP$zS]\u0010H^\u001d\u001eT\\\u0006{|\u0011N\u000e\u001a\u0010\u001bX\u001a\u0016\u0001R\u001c\u001cU\f\u001b\u0016\u001a"), AICodeUtils.H("zvtgna"), ChatInputController.H("\u001d\u001d\u0015[\u0018\u001c66\u0006\u000bL\u001a\n@9\u0017\r\u001c\u001d\u0010;��\u0016\u001a\u0015\u0018"), AICodeUtils.H("y`tzna")));
    }

    public static String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getClassName()).insert(0, stackTraceElement.getMethodName()).toString();
        int length = stringBuffer.length() - 1;
        int i = ((3 ^ 5) << 3) ^ 3;
        int i2 = (3 << 3) ^ 3;
        String str = (String) a;
        int length2 = str.length();
        char[] cArr = new char[length2];
        int i3 = length2 - 1;
        int i4 = i3;
        int i5 = length;
        while (i3 >= 0) {
            int i6 = i4;
            int i7 = i4 - 1;
            cArr[i6] = (char) (i2 ^ (str.charAt(i6) ^ stringBuffer.charAt(i5)));
            if (i7 < 0) {
                break;
            }
            char charAt = (char) (1 ^ (str.charAt(i7) ^ stringBuffer.charAt(i5)));
            i4 = i7 - 1;
            i5--;
            cArr[i7] = charAt;
            if (i5 < 0) {
                i5 = length;
            }
        }
        return new String(cArr);
    }

    public static com.intellij.openapi.actionSystem.impl.ActionButton button(@NotNull AnAction action) {
        if (action == null) {
            m366enum(0);
        }
        return new com.intellij.openapi.actionSystem.impl.ActionButton(action, action.getTemplatePresentation().clone(), AICodeUtils.H("`u~nava"), ActionToolbar.DEFAULT_MINIMUM_BUTTON_SIZE);
    }
}
