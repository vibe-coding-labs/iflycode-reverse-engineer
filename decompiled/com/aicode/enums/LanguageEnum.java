package com.aicode.enums;

import com.aicode.util.HandleCacheUtil;
import com.aicode.util.JComponentKt;

/* compiled from: id */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/enums/LanguageEnum.class */
public enum LanguageEnum {
    C_LANGUAGE_01(JComponentKt.H("W"), HandleCacheUtil.H("\u0014"), JComponentKt.H("��4\u0011{Z")),
    C_LANGUAGE_02(JComponentKt.H("\\"), HandleCacheUtil.H("\u0014"), ""),
    CPP_LANGUAGE_01(HandleCacheUtil.H(",x'"), JComponentKt.H("=9\u001f"), ""),
    CPP_LANGUAGE_02(JComponentKt.H("\u0016bD"), HandleCacheUtil.H("\f#|"), ""),
    CPP_LANGUAGE_03(HandleCacheUtil.H("'p/"), JComponentKt.H("=9\u001f"), ""),
    CPP_LANGUAGE_04(JComponentKt.H("qW"), HandleCacheUtil.H("\f#|"), ""),
    CPP_LANGUAGE_05(HandleCacheUtil.H(",p/"), JComponentKt.H("=9\u001f"), ""),
    CPP_LANGUAGE_06(JComponentKt.H("\u001d9\u001f"), HandleCacheUtil.H("\f#|"), ""),
    CPP_LANGUAGE_07(HandleCacheUtil.H("\u0014"), JComponentKt.H("=9\u001f"), ""),
    CPP_LANGUAGE_08(JComponentKt.H("]"), HandleCacheUtil.H("\f#|"), ""),
    CPP_LANGUAGE_09(HandleCacheUtil.H("a>"), JComponentKt.H("=9\u001f"), ""),
    CPP_LANGUAGE_10(JComponentKt.H("Y"), HandleCacheUtil.H("\f#|"), ""),
    CPP_LANGUAGE_11(HandleCacheUtil.H("8"), JComponentKt.H("=9\u001f"), ""),
    CPP_LANGUAGE_126(JComponentKt.H("G"), HandleCacheUtil.H("\f#|"), ""),
    CPP_LANGUAGE_13(HandleCacheUtil.H("\u0004"), JComponentKt.H("=9\u001f"), ""),
    PYTHON_LANGUAGE_01(JComponentKt.H("bM"), HandleCacheUtil.H("[\rD\u0007G\u0019"), JComponentKt.H("\u0014<��0\u001f`Y")),
    PYTHON_LANGUAGE_02(JComponentKt.H("\u000ek\u0007"), HandleCacheUtil.H("[\rD\u0007G\u0019"), JComponentKt.H("\u0014<��0\u001f`Y")),
    PYTHON_LANGUAGE_03(JComponentKt.H("\u000ekW"), HandleCacheUtil.H("[\rD\u0007G\u0019"), JComponentKt.H("\u0014<��0\u001f`Y")),
    PYTHON_LANGUAGE_04(JComponentKt.H("\u000ek["), HandleCacheUtil.H("[\rD\u0007G\u0019"), JComponentKt.H("\u0014<��0\u001f`Y")),
    PYTHON_LANGUAGE_05(JComponentKt.H("\u000ekP"), HandleCacheUtil.H("[\rD\u0007G\u0019"), JComponentKt.H("\u0014<��0\u001f`Y")),
    PYTHON_LANGUAGE_06(JComponentKt.H("\u000ek]"), HandleCacheUtil.H("[\rD\u0007G\u0019"), JComponentKt.H("\u0014<��0\u001f`Y")),
    PYTHON_LANGUAGE_07(JComponentKt.H("\u000ekL"), HandleCacheUtil.H("[\rD\u0007G\u0019"), JComponentKt.H("\u0014<��0\u001f`Y")),
    PYTHON_LANGUAGE_08(JComponentKt.H("\u000ekN"), HandleCacheUtil.H("[\rD\u0007G\u0019"), JComponentKt.H("\u0014<��0\u001f`Y")),
    PYTHON_LANGUAGE_09(JComponentKt.H("(\u0007eN"), HandleCacheUtil.H("[\rD\u0007G\u0019"), JComponentKt.H("\u0014<��0\u001f`Y")),
    PYTHON_LANGUAGE_10(JComponentKt.H("\fbM"), HandleCacheUtil.H("[\rD\u0007G\u0019"), JComponentKt.H("\u0014<��0\u001f`Y")),
    PYTHON_LANGUAGE_11(JComponentKt.H("(\u0007vQ"), HandleCacheUtil.H("[\rD\u0007G\u0019"), JComponentKt.H("\u0014<��0\u001f`Y")),
    PYTHON_LANGUAGE_12(JComponentKt.H("\u000ekD"), HandleCacheUtil.H("[\rD\u0007G\u0019"), JComponentKt.H("\u0014<��0\u001f`Y")),
    PYTHON_LANGUAGE_13(JComponentKt.H("\u000ek@"), HandleCacheUtil.H("[\rD\u0007G\u0019"), JComponentKt.H("\u0014<��0\u001f`Y")),
    JAVA(JComponentKt.H("2\u001fdU"), HandleCacheUtil.H("Z\u000e^\u0016"), JComponentKt.H("\u000b\n1\u00064\u0012{~")),
    JS(JComponentKt.H("xG"), HandleCacheUtil.H("J\u001ev>X\u0017B\u0006X\u0003"), JComponentKt.H("\u0015\u0001'0,\u0011`Y")),
    TS(JComponentKt.H("fG"), HandleCacheUtil.H("T\u0006p:X\u0017B\u0006X\u0003"), JComponentKt.H("\u0015\u0001'0,\u0011`Y")),
    VUE(JComponentKt.H("\bgQ"), HandleCacheUtil.H("\u0019]\u0012"), JComponentKt.H("\u0015\u0001'0,\u0011`Y"));


    /* renamed from: try, reason: not valid java name */
    private final String f250try;

    /* renamed from: float, reason: not valid java name */
    private final String f251float;

    /* renamed from: enum, reason: not valid java name */
    private final String f253enum;

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static String getLanguage(String a) {
        LanguageEnum[] values = values();
        int length = values.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            LanguageEnum languageEnum = values[i2];
            if (languageEnum.getSuffix().equals(a)) {
                return languageEnum.getDescription();
            }
            i2++;
            i = i2;
        }
        return "";
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static boolean isVaildLanguage(String a) {
        LanguageEnum[] values = values();
        int length = values.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            if (!values[i2].getSuffix().equals(a)) {
                i2++;
                i = i2;
            } else {
                return true;
            }
        }
        return false;
    }

    LanguageEnum(String a, String a2, String a3) {
        this.f253enum = a;
        this.f251float = a2;
        this.f250try = a3;
    }

    public String getJetBrainPlatform() {
        return this.f250try;
    }

    public String getDescription() {
        return this.f251float;
    }

    public String getSuffix() {
        return this.f253enum;
    }
}
