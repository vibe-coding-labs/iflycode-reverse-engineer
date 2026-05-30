package com.aicode.content.util.file;

import java.util.List;

/* compiled from: ko */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/content/util/file/LanguageFileExtensionDetails.class */
public class LanguageFileExtensionDetails {

    /* renamed from: float, reason: not valid java name */
    private String f205float;

    /* renamed from: byte, reason: not valid java name */
    private List<String> f206byte;

    /* renamed from: enum, reason: not valid java name */
    private String f207enum;

    public static String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getClassName()).insert(0, stackTraceElement.getMethodName()).toString();
        int length = stringBuffer.length() - 1;
        int i = (1 << 3) ^ 5;
        int i2 = (3 ^ 5) << 4;
        String str = (String) a;
        int length2 = str.length();
        char[] cArr = new char[length2];
        int i3 = length2 - 1;
        int i4 = i3;
        int i5 = length;
        while (i3 >= 0) {
            int i6 = i4;
            int i7 = i4 - 1;
            cArr[i6] = (char) (i ^ (str.charAt(i6) ^ stringBuffer.charAt(i5)));
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

    public String getType() {
        return this.f205float;
    }

    public void setType(String a) {
        this.f205float = a;
    }

    public void setExtensions(List<String> list) {
        this.f206byte = list;
    }

    public String getName() {
        return this.f207enum;
    }

    public void setName(String a) {
        this.f207enum = a;
    }

    public List<String> getExtensions() {
        return this.f206byte;
    }
}
