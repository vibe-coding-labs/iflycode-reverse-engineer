/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.enums;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class ModuleEnum
extends Enum<ModuleEnum> {
    public static final /* enum */ ModuleEnum UNIT_TESTING;
    public static final /* enum */ ModuleEnum GIT_VIEW;
    public static final /* enum */ ModuleEnum COMMON;
    public static final /* enum */ ModuleEnum UNIT_TEST;
    public static final /* enum */ ModuleEnum CODE_SEARCH;
    public static final /* enum */ ModuleEnum SQL_CHAT;
    public static final /* enum */ ModuleEnum SETTING;
    public static final /* enum */ ModuleEnum LOGIN;
    public static final /* enum */ ModuleEnum CODE_CHECK;
    public static final /* enum */ ModuleEnum CHAT;
    public static final /* enum */ ModuleEnum LOG;
    private static final /* synthetic */ ModuleEnum[] enum;
    public static final /* enum */ ModuleEnum BATCH_UNIT_TEST;

    static {
        LOG = new ModuleEnum();
        LOGIN = new ModuleEnum();
        COMMON = new ModuleEnum();
        SETTING = new ModuleEnum();
        CHAT = new ModuleEnum();
        SQL_CHAT = new ModuleEnum();
        CODE_SEARCH = new ModuleEnum();
        CODE_CHECK = new ModuleEnum();
        GIT_VIEW = new ModuleEnum();
        UNIT_TEST = new ModuleEnum();
        BATCH_UNIT_TEST = new ModuleEnum();
        UNIT_TESTING = new ModuleEnum();
        ModuleEnum[] moduleEnumArray = new ModuleEnum[12];
        moduleEnumArray[0] = LOG;
        moduleEnumArray[1] = LOGIN;
        moduleEnumArray[2] = COMMON;
        moduleEnumArray[3] = SETTING;
        moduleEnumArray[4] = CHAT;
        moduleEnumArray[5] = SQL_CHAT;
        moduleEnumArray[6] = CODE_SEARCH;
        moduleEnumArray[7] = CODE_CHECK;
        moduleEnumArray[8] = GIT_VIEW;
        moduleEnumArray[9] = UNIT_TEST;
        moduleEnumArray[10] = BATCH_UNIT_TEST;
        moduleEnumArray[11] = UNIT_TESTING;
        enum = moduleEnumArray;
    }

    private ModuleEnum() {
        int a = n;
        ModuleEnum moduleEnum = this;
    }

    public static ModuleEnum valueOf(String a) {
        return Enum.valueOf(ModuleEnum.class, a);
    }

    public static ModuleEnum[] values() {
        return (ModuleEnum[])enum.clone();
    }
}
