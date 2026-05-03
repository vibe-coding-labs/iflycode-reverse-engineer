/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.enums;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class CodeTipType
extends Enum<CodeTipType> {
    private static final /* synthetic */ CodeTipType[] enum;
    public static final /* enum */ CodeTipType AfterLineEnd;
    public static final /* enum */ CodeTipType Block;
    public static final /* enum */ CodeTipType Inline;

    public static CodeTipType[] values() {
        return (CodeTipType[])enum.clone();
    }

    static {
        Inline = new CodeTipType();
        AfterLineEnd = new CodeTipType();
        Block = new CodeTipType();
        CodeTipType[] codeTipTypeArray = new CodeTipType[3];
        codeTipTypeArray[0] = Inline;
        codeTipTypeArray[1] = AfterLineEnd;
        codeTipTypeArray[2] = Block;
        enum = codeTipTypeArray;
    }

    public static CodeTipType valueOf(String a) {
        return Enum.valueOf(CodeTipType.class, a);
    }

    private CodeTipType() {
        int a = n;
        CodeTipType codeTipType = this;
    }
}
