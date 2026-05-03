/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.enums;

import com.aicode.inline.status.InlineChatStatusServiceKt;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class SendKeyEnum
extends Enum<SendKeyEnum> {
    private static final /* synthetic */ SendKeyEnum[] byte;
    public static final /* enum */ SendKeyEnum ENTER_KEY = new SendKeyEnum(InlineChatStatusServiceKt.H("&\u0013,\u00002"));
    private String enum;
    public static final /* enum */ SendKeyEnum ENTER_SHIFT_KEY = new SendKeyEnum(InlineChatStatusServiceKt.H(";\u0019=+\u001c\u0019&\u0013,\u00002"));

    public static SendKeyEnum[] values() {
        return (SendKeyEnum[])byte.clone();
    }

    private SendKeyEnum(String string2) {
        Object a = string2;
        SendKeyEnum a2 = this;
        a2.enum = a;
    }

    static {
        SendKeyEnum[] sendKeyEnumArray = new SendKeyEnum[2];
        sendKeyEnumArray[0] = ENTER_KEY;
        sendKeyEnumArray[1] = ENTER_SHIFT_KEY;
        byte = sendKeyEnumArray;
    }

    public static SendKeyEnum getByText(String string) {
        int a;
        String string2 = string;
        SendKeyEnum[] sendKeyEnumArray = SendKeyEnum.values();
        int n = sendKeyEnumArray.length;
        int n2 = a = 0;
        while (n2 < n) {
            SendKeyEnum sendKeyEnum = sendKeyEnumArray[a];
            if (sendKeyEnum.enum.equals(string2)) {
                return sendKeyEnum;
            }
            n2 = ++a;
        }
        return ENTER_KEY;
    }

    public String getText() {
        SendKeyEnum a;
        return a.enum;
    }

    public static SendKeyEnum valueOf(String a) {
        return Enum.valueOf(SendKeyEnum.class, a);
    }
}
