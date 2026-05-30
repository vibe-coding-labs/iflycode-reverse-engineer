package com.aicode.exception;

/* compiled from: vf */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/exception/RequestCancelException.class */
public class RequestCancelException extends RuntimeException {
    public static String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getMethodName()).insert(0, stackTraceElement.getClassName()).toString();
        int length = stringBuffer.length() - 1;
        int i = ((2 ^ 5) << 4) ^ ((2 << 2) ^ 3);
        int i2 = ((3 ^ 5) << 3) ^ (3 ^ 5);
        int i3 = (5 << 4) ^ 1;
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

    public RequestCancelException(String a) {
        super(a);
    }
}
