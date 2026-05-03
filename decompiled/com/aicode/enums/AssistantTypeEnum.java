/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.enums;

import com.aicode.content.util.file.LanguageFileExtensionDetails;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class AssistantTypeEnum
extends Enum<AssistantTypeEnum> {
    private static final /* synthetic */ AssistantTypeEnum[] byte;
    public static final /* enum */ AssistantTypeEnum IFLY_MATE = new AssistantTypeEnum(LanguageFileExtensionDetails.H("L\u000eb\u001aX\u0019y\u0005"));
    private String enum;
    public static final /* enum */ AssistantTypeEnum IFLY_DEV = new AssistantTypeEnum(LanguageFileExtensionDetails.H("!H\u000fl<h\u0016"));
    public static final /* enum */ AssistantTypeEnum IFLY_TEST = new AssistantTypeEnum(LanguageFileExtensionDetails.H("L\u000eb\u001aA\u001d~\u0014"));
    public static final /* enum */ AssistantTypeEnum IFLY_DBA;
    public static final /* enum */ AssistantTypeEnum IFLY_OPS;
    public static final /* enum */ AssistantTypeEnum IFLY_PM;

    private AssistantTypeEnum(String string2) {
        Object a = string2;
        AssistantTypeEnum a2 = this;
        a2.enum = a;
    }

    public static AssistantTypeEnum[] values() {
        return (AssistantTypeEnum[])byte.clone();
    }

    public String getType() {
        AssistantTypeEnum a;
        return a.enum;
    }

    public static AssistantTypeEnum valueOf(String a) {
        return Enum.valueOf(AssistantTypeEnum.class, a);
    }

    static {
        IFLY_OPS = new AssistantTypeEnum(LanguageFileExtensionDetails.H("!H\u000fl7}\u0013"));
        IFLY_PM = new AssistantTypeEnum(LanguageFileExtensionDetails.H("g%y\u0001]\r"));
        IFLY_DBA = new AssistantTypeEnum(LanguageFileExtensionDetails.H("!H\u000fl<O!"));
        AssistantTypeEnum[] assistantTypeEnumArray = new AssistantTypeEnum[6];
        assistantTypeEnumArray[0] = IFLY_MATE;
        assistantTypeEnumArray[1] = IFLY_DEV;
        assistantTypeEnumArray[2] = IFLY_TEST;
        assistantTypeEnumArray[3] = IFLY_OPS;
        assistantTypeEnumArray[4] = IFLY_PM;
        assistantTypeEnumArray[5] = IFLY_DBA;
        byte = assistantTypeEnumArray;
    }
}
