package com.aicode.enums;

import com.aicode.language.AICodeLanguageInfo;
import com.aicode.util.JComponentKt;

/* compiled from: un */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/enums/FileExtensionEnum.class */
public enum FileExtensionEnum {
    C_LANGUAGE_01(AICodeLanguageInfo.H("F"), JComponentKt.H("w"), AICodeLanguageInfo.H("\u00112��}K")),
    CPP_LANGUAGE_01(AICodeLanguageInfo.H("\fdU"), JComponentKt.H("=9\u001f"), ""),
    CSHARP(JComponentKt.H("qG"), AICodeLanguageInfo.H("��!\u0016.Fu"), ""),
    PYTHON_LANGUAGE_01(AICodeLanguageInfo.H("d\\"), JComponentKt.H("\u0015:\f6]z"), AICodeLanguageInfo.H("\u0005:\u00116\u000efH")),
    JAVA(AICodeLanguageInfo.H("4\u000ebD"), JComponentKt.H("\u0012?Du"), AICodeLanguageInfo.H("\r\u001b7\u00172\u0003}o")),
    KOTLIN(AICodeLanguageInfo.H("\u007fQ"), JComponentKt.H("\u000e,\f2[z"), ""),
    RUST(JComponentKt.H("`G"), AICodeLanguageInfo.H("\f:Gq"), ""),
    SWIFT(AICodeLanguageInfo.H("\u0001)\u0006rQ"), JComponentKt.H("0\u000f7T`"), ""),
    OBJECTIVE_C(JComponentKt.H("Y"), AICodeLanguageInfo.H("8\u00018\u00016\u0017;\b*Kf"), ""),
    GO(AICodeLanguageInfo.H("sJ"), JComponentKt.H("\u0002,\u0014?\\s"), ""),
    JS(JComponentKt.H("xG"), AICodeLanguageInfo.H("\t3\u00124\u00101\f&Dq"), JComponentKt.H("\u0015\u0001'0,\u0011`Y")),
    TS(JComponentKt.H("fG"), AICodeLanguageInfo.H("\u0017+\u00140\u00101\f&Dq"), JComponentKt.H("\u0015\u0001'0,\u0011`Y")),
    VUE(JComponentKt.H("\bgQ"), AICodeLanguageInfo.H("9A`"), JComponentKt.H("\u0015\u0001'0,\u0011`Y"));


    /* renamed from: try, reason: not valid java name */
    private final String f239try;

    /* renamed from: float, reason: not valid java name */
    private final String f240float;

    /* renamed from: byte, reason: not valid java name */
    private final String f241byte;

    public String getSuffix() {
        return this.f240float;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static String getLanguage(String a) {
        FileExtensionEnum[] values = values();
        int length = values.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            FileExtensionEnum fileExtensionEnum = values[i2];
            if (!fileExtensionEnum.getDescription().equalsIgnoreCase(a)) {
                i2++;
                i = i2;
            } else {
                return fileExtensionEnum.getSuffix();
            }
        }
        return "";
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static String getFileLanguage(String a) {
        FileExtensionEnum[] values = values();
        int length = values.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            FileExtensionEnum fileExtensionEnum = values[i2];
            if (!fileExtensionEnum.getSuffix().equalsIgnoreCase(a)) {
                i2++;
                i = i2;
            } else {
                return fileExtensionEnum.getDescription();
            }
        }
        return "";
    }

    public String getJetBrainPlatform() {
        return this.f239try;
    }

    FileExtensionEnum(String a, String a2, String a3) {
        this.f240float = a;
        this.f241byte = a2;
        this.f239try = a3;
    }

    public String getDescription() {
        return this.f241byte;
    }
}
