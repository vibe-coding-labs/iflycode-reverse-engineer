package com.aicode.util;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;

/* compiled from: da */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/PositionUtil.class */
public class PositionUtil {
    public static /* synthetic */ String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getMethodName()).insert(0, stackTraceElement.getClassName()).toString();
        int length = stringBuffer.length() - 1;
        int i = (5 << 4) ^ ((3 << 2) ^ 1);
        int i2 = (5 << 3) ^ 4;
        int i3 = (5 << 4) ^ ((3 ^ 5) << 1);
        String str = (String) a;
        int length2 = str.length();
        char[] cArr = new char[length2];
        int i4 = length2 - 1;
        int i5 = i4;
        int i6 = length;
        while (i4 >= 0) {
            int i7 = i5;
            int i8 = i5 - 1;
            cArr[i7] = (char) (i2 ^ (str.charAt(i7) ^ stringBuffer.charAt(i6)));
            if (i8 < 0) {
                break;
            }
            char charAt = (char) (i3 ^ (str.charAt(i8) ^ stringBuffer.charAt(i6)));
            i5 = i8 - 1;
            i6--;
            cArr[i8] = charAt;
            if (i6 < 0) {
                i6 = length;
            }
        }
        return new String(cArr);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ int[] getEndLineAndColumn(Project a, PsiFile a2) {
        Document document;
        if (a2 != null && (document = PsiDocumentManager.getInstance(a).getDocument(a2)) != null) {
            int textLength = document.getTextLength();
            int lineNumber = document.getLineNumber(textLength);
            return new int[]{lineNumber, textLength - document.getLineStartOffset(lineNumber)};
        }
        return new int[]{-1, -1};
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ int[] getStartLineAndColumn(Project a, PsiFile a2) {
        Document document;
        if (a2 != null && (document = PsiDocumentManager.getInstance(a).getDocument(a2)) != null) {
            int lineNumber = document.getLineNumber(0);
            return new int[]{lineNumber, 0 - document.getLineStartOffset(lineNumber)};
        }
        return new int[]{-1, -1};
    }
}
