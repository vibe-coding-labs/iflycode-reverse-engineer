package com.aicode.content.util;

import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.awt.RelativePoint;
import java.awt.Point;
import javax.swing.event.HyperlinkListener;

/* compiled from: hh */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/content/util/OverlayUtils.class */
public class OverlayUtils {
    public static String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getClassName()).insert(0, stackTraceElement.getMethodName()).toString();
        int length = stringBuffer.length() - 1;
        int i = (4 << 4) ^ ((2 << 2) ^ 1);
        int i2 = 5 << 3;
        String str = (String) a;
        int length2 = str.length();
        char[] cArr = new char[length2];
        int i3 = length2 - 1;
        int i4 = i3;
        int i5 = length;
        while (i3 >= 0) {
            int i6 = i4;
            int i7 = i4 - 1;
            cArr[i6] = (char) (5 ^ (str.charAt(i6) ^ stringBuffer.charAt(i5)));
            if (i7 < 0) {
                break;
            }
            char charAt = (char) (i2 ^ (str.charAt(i7) ^ stringBuffer.charAt(i5)));
            i4 = i7 - 1;
            i5--;
            cArr[i7] = charAt;
            if (i5 < 0) {
                i5 = length;
            }
        }
        return new String(cArr);
    }

    public static void showWarningBalloon(String a, Point a2) {
        jf(a, MessageType.WARNING, a2);
    }

    public static void showInfoBalloon(String a, Point a2) {
        jf(a, MessageType.INFO, a2);
    }

    private static void jf(String a, MessageType a2, Point a3) {
        JBPopupFactory.getInstance().createHtmlTextBalloonBuilder(a, a2, (HyperlinkListener) null).setFadeoutTime(2500L).createBalloon().show(RelativePoint.fromScreen(a3), Balloon.Position.below);
    }
}
