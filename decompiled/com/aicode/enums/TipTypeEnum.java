/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.enums;

import com.aicode.message.BasicActionsBundle;
import com.aicode.util.NewFileUtils;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class TipTypeEnum
extends Enum<TipTypeEnum> {
    public static final /* enum */ TipTypeEnum SINGLE_LINE = new TipTypeEnum(BasicActionsBundle.message(NewFileUtils.H("j0C\u001d_\f\r\u0002\u001bNJ\u0013G^K\u001cN\u0017\t\u0018U\f\u0015N"), new Object[0]));
    private static final /* synthetic */ TipTypeEnum[] byte;
    public static final /* enum */ TipTypeEnum INTELLIGENT_MODE = new TipTypeEnum(BasicActionsBundle.message(NewFileUtils.H("B\u0018\u0011OI\u001a\f\u0003S\u0014R\u0006\t\u0018U\f\u0015N"), new Object[0]));
    private String enum;

    private TipTypeEnum(String string2) {
        Object a = string2;
        TipTypeEnum a2 = this;
        a2.enum = a;
    }

    public static TipTypeEnum valueOf(String a) {
        return Enum.valueOf(TipTypeEnum.class, a);
    }

    public static TipTypeEnum getByName(String string) {
        int a;
        String string2 = string;
        TipTypeEnum[] tipTypeEnumArray = TipTypeEnum.values();
        int n = tipTypeEnumArray.length;
        int n2 = a = 0;
        while (n2 < n) {
            TipTypeEnum tipTypeEnum = tipTypeEnumArray[a];
            if (tipTypeEnum.name().equals(string2)) {
                return tipTypeEnum;
            }
            n2 = ++a;
        }
        return INTELLIGENT_MODE;
    }

    static {
        TipTypeEnum[] tipTypeEnumArray = new TipTypeEnum[2];
        tipTypeEnumArray[0] = SINGLE_LINE;
        tipTypeEnumArray[1] = INTELLIGENT_MODE;
        byte = tipTypeEnumArray;
    }

    public static TipTypeEnum[] values() {
        return (TipTypeEnum[])byte.clone();
    }
}
