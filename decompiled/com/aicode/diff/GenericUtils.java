package com.aicode.diff;

import com.aicode.util.Application;
import com.aicode.util.Maps;
import com.intellij.openapi.vfs.VirtualFile;
import java.io.File;

/* compiled from: qd */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/diff/GenericUtils.class */
public class GenericUtils {
    public static String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getClassName()).append(stackTraceElement.getMethodName()).toString();
        int length = stringBuffer.length() - 1;
        int i = (1 << 3) ^ 4;
        int i2 = ((2 ^ 5) << 3) ^ (3 ^ 5);
        int i3 = ((3 ^ 5) << 3) ^ (3 ^ 5);
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

    public static int generateRandomInt(int a, int a2) {
        return Double.valueOf((Math.random() * ((a - a2) + 1)) + a2).intValue();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static String[] getClodFileMeta(String a) {
        File file = new File(a);
        if (file.exists()) {
            return file.getParentFile().getName().split(Application.H("O"));
        }
        return new String[]{Maps.H("\u0012>\n\u0019\b=%(\u00144")};
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static String getVersionedFileName(String a, String a2) {
        if (a2 == null) {
            return a;
        }
        int lastIndexOf = a.lastIndexOf(Application.H(">"));
        if (lastIndexOf == -1) {
            return a + "_" + a2;
        }
        String substring = a.substring(0, lastIndexOf);
        return (substring + "_" + a2) + "." + a.substring(lastIndexOf + 1);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static boolean isCloudFile(VirtualFile a) {
        return getClodFileMeta(a.getPath()).length == 6;
    }
}
