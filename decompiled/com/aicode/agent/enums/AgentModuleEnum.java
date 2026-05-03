/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.enums;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class AgentModuleEnum
extends Enum<AgentModuleEnum> {
    public static final /* enum */ AgentModuleEnum CODE_CHECK;
    public static final /* enum */ AgentModuleEnum LOG;
    public static final /* enum */ AgentModuleEnum SERVER_RESOURCE;
    public static final /* enum */ AgentModuleEnum LOGIN;
    public static final /* enum */ AgentModuleEnum CODE_COMPLETE;
    private static final /* synthetic */ AgentModuleEnum[] enum;
    public static final /* enum */ AgentModuleEnum BATCH_UNIT_TEST;
    public static final /* enum */ AgentModuleEnum COMMON;
    public static final /* enum */ AgentModuleEnum INLINE_CHAT;
    public static final /* enum */ AgentModuleEnum GIT_REVIEW;
    public static final /* enum */ AgentModuleEnum CODE_SEARCH;
    public static final /* enum */ AgentModuleEnum SQL_CHAT;
    public static final /* enum */ AgentModuleEnum CHAT;
    public static final /* enum */ AgentModuleEnum UNIT_TEST;
    public static final /* enum */ AgentModuleEnum CODE_TEST_TEMPLATE;
    public static final /* enum */ AgentModuleEnum INIT;

    private AgentModuleEnum() {
        int a = n;
        AgentModuleEnum agentModuleEnum = this;
    }

    public static AgentModuleEnum[] values() {
        return (AgentModuleEnum[])enum.clone();
    }

    static {
        LOG = new AgentModuleEnum();
        INIT = new AgentModuleEnum();
        LOGIN = new AgentModuleEnum();
        COMMON = new AgentModuleEnum();
        CHAT = new AgentModuleEnum();
        SQL_CHAT = new AgentModuleEnum();
        CODE_COMPLETE = new AgentModuleEnum();
        CODE_SEARCH = new AgentModuleEnum();
        CODE_CHECK = new AgentModuleEnum();
        GIT_REVIEW = new AgentModuleEnum();
        UNIT_TEST = new AgentModuleEnum();
        BATCH_UNIT_TEST = new AgentModuleEnum();
        CODE_TEST_TEMPLATE = new AgentModuleEnum();
        SERVER_RESOURCE = new AgentModuleEnum();
        INLINE_CHAT = new AgentModuleEnum();
        AgentModuleEnum[] agentModuleEnumArray = new AgentModuleEnum[15];
        agentModuleEnumArray[0] = LOG;
        agentModuleEnumArray[1] = INIT;
        agentModuleEnumArray[2] = LOGIN;
        agentModuleEnumArray[3] = COMMON;
        agentModuleEnumArray[4] = CHAT;
        agentModuleEnumArray[5] = SQL_CHAT;
        agentModuleEnumArray[6] = CODE_COMPLETE;
        agentModuleEnumArray[7] = CODE_SEARCH;
        agentModuleEnumArray[8] = CODE_CHECK;
        agentModuleEnumArray[9] = GIT_REVIEW;
        agentModuleEnumArray[10] = UNIT_TEST;
        agentModuleEnumArray[11] = BATCH_UNIT_TEST;
        agentModuleEnumArray[12] = CODE_TEST_TEMPLATE;
        agentModuleEnumArray[13] = SERVER_RESOURCE;
        agentModuleEnumArray[14] = INLINE_CHAT;
        enum = agentModuleEnumArray;
    }

    public static AgentModuleEnum valueOf(String a) {
        return Enum.valueOf(AgentModuleEnum.class, a);
    }
}
