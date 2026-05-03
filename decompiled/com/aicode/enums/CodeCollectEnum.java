/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.enums;

import com.aicode.util.HandleCacheUtil;
import com.aicode.util.Maps;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class CodeCollectEnum
extends Enum<CodeCollectEnum> {
    public static final /* enum */ CodeCollectEnum COMPARE;
    public static final /* enum */ CodeCollectEnum GENERATE;
    public static final /* enum */ CodeCollectEnum UNITTEST;
    private static final /* synthetic */ CodeCollectEnum[] float;
    private String byte;
    public static final /* enum */ CodeCollectEnum NEW;
    public static final /* enum */ CodeCollectEnum INSERT;
    public static final /* enum */ CodeCollectEnum OTHER;
    public static final /* enum */ CodeCollectEnum COPY;
    private String enum;

    static {
        GENERATE = new CodeCollectEnum(Maps.H("\b6\u00061\u0007(Kf"), HandleCacheUtil.H("\u666a\u80b2\u886d\u513f"));
        INSERT = new CodeCollectEnum(HandleCacheUtil.H("b:c*z#"), Maps.H("\u63ed\u5166"));
        COPY = new CodeCollectEnum(Maps.H("\u0016&Oz"), HandleCacheUtil.H("\u5905\u5261"));
        NEW = new CodeCollectEnum(HandleCacheUtil.H("!m "), Maps.H("\u658f\u5ef9"));
        UNITTEST = new CodeCollectEnum(Maps.H("\u001a=\u0001 \u0001,Lw"), HandleCacheUtil.H("\u535d\u6d1c"));
        COMPARE = new CodeCollectEnum(HandleCacheUtil.H("\u001cd9`.z2"), Maps.H("\u6ba1\u8fca\u91f8\u7eb0"));
        OTHER = new CodeCollectEnum(Maps.H(";\u0001!Zq"), HandleCacheUtil.H("\u517e\u4e81"));
        CodeCollectEnum[] codeCollectEnumArray = new CodeCollectEnum[7];
        codeCollectEnumArray[0] = GENERATE;
        codeCollectEnumArray[1] = INSERT;
        codeCollectEnumArray[2] = COPY;
        codeCollectEnumArray[3] = NEW;
        codeCollectEnumArray[4] = UNITTEST;
        codeCollectEnumArray[5] = COMPARE;
        codeCollectEnumArray[6] = OTHER;
        float = codeCollectEnumArray;
    }

    public String getType() {
        CodeCollectEnum a;
        return a.enum;
    }

    public static CodeCollectEnum[] values() {
        return (CodeCollectEnum[])float.clone();
    }

    public static CodeCollectEnum valueOf(String a) {
        return Enum.valueOf(CodeCollectEnum.class, a);
    }

    public String getName() {
        CodeCollectEnum a;
        return a.byte;
    }

    /*
     * WARNING - void declaration
     */
    private CodeCollectEnum(String string2, String string3) {
        Object a;
        void a2;
        CodeCollectEnum a3;
        CodeCollectEnum codeCollectEnum = object;
        Object object = string3;
        CodeCollectEnum codeCollectEnum2 = a3 = codeCollectEnum;
        codeCollectEnum2.enum = a2;
        codeCollectEnum2.byte = a;
    }
}
