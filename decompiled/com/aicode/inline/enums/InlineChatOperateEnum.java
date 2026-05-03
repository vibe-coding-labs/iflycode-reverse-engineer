/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.inline.enums;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class InlineChatOperateEnum
extends Enum<InlineChatOperateEnum> {
    public static final /* enum */ InlineChatOperateEnum EDIT;
    private static final /* synthetic */ InlineChatOperateEnum[] enum;
    public static final /* enum */ InlineChatOperateEnum INSERT;

    static {
        INSERT = new InlineChatOperateEnum();
        EDIT = new InlineChatOperateEnum();
        InlineChatOperateEnum[] inlineChatOperateEnumArray = new InlineChatOperateEnum[2];
        inlineChatOperateEnumArray[0] = INSERT;
        inlineChatOperateEnumArray[1] = EDIT;
        enum = inlineChatOperateEnumArray;
    }

    private InlineChatOperateEnum() {
        int a = n;
        InlineChatOperateEnum inlineChatOperateEnum = this;
    }

    public static InlineChatOperateEnum[] values() {
        return (InlineChatOperateEnum[])enum.clone();
    }

    public static InlineChatOperateEnum valueOf(String a) {
        return Enum.valueOf(InlineChatOperateEnum.class, a);
    }
}
