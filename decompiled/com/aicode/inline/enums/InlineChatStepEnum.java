/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.inline.enums;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class InlineChatStepEnum
extends Enum<InlineChatStepEnum> {
    public static final /* enum */ InlineChatStepEnum ERROR;
    public static final /* enum */ InlineChatStepEnum SUCCESS;
    private static final /* synthetic */ InlineChatStepEnum[] enum;
    public static final /* enum */ InlineChatStepEnum LOADING;
    public static final /* enum */ InlineChatStepEnum CATEGORY;

    private InlineChatStepEnum() {
        int a = n;
        InlineChatStepEnum inlineChatStepEnum = this;
    }

    public static InlineChatStepEnum valueOf(String a) {
        return Enum.valueOf(InlineChatStepEnum.class, a);
    }

    public static InlineChatStepEnum[] values() {
        return (InlineChatStepEnum[])enum.clone();
    }

    static {
        CATEGORY = new InlineChatStepEnum();
        LOADING = new InlineChatStepEnum();
        ERROR = new InlineChatStepEnum();
        SUCCESS = new InlineChatStepEnum();
        InlineChatStepEnum[] inlineChatStepEnumArray = new InlineChatStepEnum[4];
        inlineChatStepEnumArray[0] = CATEGORY;
        inlineChatStepEnumArray[1] = LOADING;
        inlineChatStepEnumArray[2] = ERROR;
        inlineChatStepEnumArray[3] = SUCCESS;
        enum = inlineChatStepEnumArray;
    }
}
