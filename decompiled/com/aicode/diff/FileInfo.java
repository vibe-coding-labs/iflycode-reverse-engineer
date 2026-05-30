package com.aicode.diff;

import com.intellij.openapi.vfs.VirtualFile;

/* compiled from: fi */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/diff/FileInfo.class */
public class FileInfo {

    /* renamed from: byte, reason: not valid java name */
    public boolean f214byte;

    /* renamed from: enum, reason: not valid java name */
    public VirtualFile f215enum;

    public static String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getMethodName()).append(stackTraceElement.getClassName()).toString();
        int length = stringBuffer.length() - 1;
        int i = ((2 ^ 5) << 4) ^ (1 << 1);
        int i2 = ((2 ^ 5) << 3) ^ 5;
        int i3 = (3 << 3) ^ 3;
        String str = (String) a;
        int length2 = str.length();
        char[] cArr = new char[length2];
        int i4 = length2 - 1;
        int i5 = i4;
        int i6 = length;
        while (i4 >= 0) {
            int i7 = i5;
            int i8 = i5 - 1;
            cArr[i7] = (char) (i ^ (str.charAt(i7) ^ stringBuffer.charAt(i6)));
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

    public FileInfo(VirtualFile a, boolean z) {
        this.f214byte = false;
        this.f215enum = a;
        this.f214byte = z;
    }

    public void setVf(VirtualFile a) {
        this.f215enum = a;
    }

    public boolean isCouldFile() {
        return this.f214byte;
    }

    public void setCouldFile(boolean z) {
        this.f214byte = z;
    }

    public VirtualFile getVf() {
        return this.f215enum;
    }
}
