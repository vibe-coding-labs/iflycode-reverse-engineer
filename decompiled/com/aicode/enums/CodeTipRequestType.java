/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.enums;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class CodeTipRequestType
extends Enum<CodeTipRequestType> {
    private static final /* synthetic */ CodeTipRequestType[] enum;
    public static final /* enum */ CodeTipRequestType Forced;
    public static final /* enum */ CodeTipRequestType Interact;
    public static final /* enum */ CodeTipRequestType InlineChat;
    public static final /* enum */ CodeTipRequestType Manual;
    public static final /* enum */ CodeTipRequestType Automatic;

    public static CodeTipRequestType valueOf(String a) {
        return Enum.valueOf(CodeTipRequestType.class, a);
    }

    public boolean isForcedOrManual() {
        CodeTipRequestType a;
        if (a == Forced || a == Manual) {
            return true;
        }
        return false;
    }

    public boolean isInlineChat() {
        CodeTipRequestType a;
        if (a == InlineChat) {
            return true;
        }
        return false;
    }

    public static CodeTipRequestType[] values() {
        return (CodeTipRequestType[])enum.clone();
    }

    public boolean isAutomaticOrForced() {
        CodeTipRequestType a;
        if (a == Automatic || a == Forced) {
            return true;
        }
        return false;
    }

    private CodeTipRequestType() {
        int a = n;
        CodeTipRequestType codeTipRequestType = this;
    }

    public boolean isUnforced() {
        CodeTipRequestType a;
        if (a == Automatic) {
            return true;
        }
        return false;
    }

    public boolean isForced() {
        CodeTipRequestType a;
        if (a == Forced) {
            return true;
        }
        return false;
    }

    static {
        Automatic = new CodeTipRequestType();
        Interact = new CodeTipRequestType();
        Forced = new CodeTipRequestType();
        Manual = new CodeTipRequestType();
        InlineChat = new CodeTipRequestType();
        CodeTipRequestType[] codeTipRequestTypeArray = new CodeTipRequestType[5];
        codeTipRequestTypeArray[0] = Automatic;
        codeTipRequestTypeArray[1] = Interact;
        codeTipRequestTypeArray[2] = Forced;
        codeTipRequestTypeArray[3] = Manual;
        codeTipRequestTypeArray[4] = InlineChat;
        enum = codeTipRequestTypeArray;
    }
}
