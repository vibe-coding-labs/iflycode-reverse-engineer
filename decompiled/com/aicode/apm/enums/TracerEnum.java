/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.apm.enums;

import com.aicode.agent.enums.CommandEnum;
import com.aicode.diff.GenericUtils;
import com.aicode.util.IndentLineUtil;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class TracerEnum
extends Enum<TracerEnum> {
    public static final /* enum */ TracerEnum CODE_COMPLETE;
    public static final /* enum */ TracerEnum AGENT_FAILURE;
    private static final /* synthetic */ TracerEnum[] float;
    public static final /* enum */ TracerEnum RECORD_EXCEPTION;
    public static final /* enum */ TracerEnum AGENT_RESTART;
    public static final /* enum */ TracerEnum CODE_COMPLETE_PARENT;
    public static final /* enum */ TracerEnum CODE_COMPLETE_INLINE_CHAT_PARENT;
    private final String byte;
    public static final /* enum */ TracerEnum AGENT_ERROR;
    private final String enum;
    public static final /* enum */ TracerEnum AGENT_RUN;
    public static final /* enum */ TracerEnum IDEA_RUN;

    public static TracerEnum[] values() {
        return (TracerEnum[])float.clone();
    }

    public String getDesc() {
        TracerEnum a;
        return a.byte;
    }

    public static TracerEnum valueOf(String a) {
        return Enum.valueOf(TracerEnum.class, a);
    }

    /*
     * WARNING - void declaration
     */
    private TracerEnum(String string2, String string3) {
        Object a;
        void a2;
        TracerEnum a3;
        TracerEnum tracerEnum = object;
        Object object = string3;
        TracerEnum tracerEnum2 = a3 = tracerEnum;
        tracerEnum2.enum = a2;
        tracerEnum2.byte = a;
    }

    static {
        IDEA_RUN = new TracerEnum(GenericUtils.H("\u001e<5>mx\u0018}n"), IndentLineUtil.H("\u001dO\nQ\u5458\u5280"));
        AGENT_RUN = new TracerEnum(IndentLineUtil.H("8A\u001aN\u001c\reC\u001bE\u0001U\u0014\\"), GenericUtils.H("\u8f8e\u63fa6%/ft"));
        AGENT_FAILURE = new TracerEnum(GenericUtils.H("\u0014:?<+mb\u0016>>.?ze"), IndentLineUtil.H("a\u0013N\u0001D\u5f75\u5e10"));
        AGENT_RESTART = new TracerEnum(IndentLineUtil.H("8A\u001aN\u001c\reR\u0011X\u001bQ\u0005\\"), GenericUtils.H("182,>\u91c5\u542f"));
        AGENT_ERROR = new TracerEnum(GenericUtils.H("\u001b5:9,je\u001208gr"), IndentLineUtil.H("V8e\u001a_\u5440\u5298\u5f75\u5e10"));
        CODE_COMPLETE_PARENT = new TracerEnum(IndentLineUtil.H(":I\u001bER\r\u001co\u0019[\u0003U\u0003M"), GenericUtils.H("\u4eb4\u7859\u8835\u5137\u72611:in"));
        CODE_COMPLETE_INLINE_CHAT_PARENT = new TracerEnum(GenericUtils.H("\u0011\u0014\u001b\u001e\u0010\u0010b\u0002\u001a\u0006\u0017\u000f[T"), IndentLineUtil.H("m\u0017J\u0016N\rt7a\u0000\u721d\u001c@\u0016F"));
        CODE_COMPLETE = new TracerEnum(CommandEnum.CODE_COMPLETE.getType(), CommandEnum.CODE_COMPLETE.getDesc());
        RECORD_EXCEPTION = new TracerEnum(GenericUtils.H("\u0001oa:/>he\u0012 3:'6#gn"), IndentLineUtil.H("\u5f6d\u5e08\u8bc7\u5f7d"));
        TracerEnum[] tracerEnumArray = new TracerEnum[9];
        tracerEnumArray[0] = IDEA_RUN;
        tracerEnumArray[1] = AGENT_RUN;
        tracerEnumArray[2] = AGENT_FAILURE;
        tracerEnumArray[3] = AGENT_RESTART;
        tracerEnumArray[4] = AGENT_ERROR;
        tracerEnumArray[5] = CODE_COMPLETE_PARENT;
        tracerEnumArray[6] = CODE_COMPLETE_INLINE_CHAT_PARENT;
        tracerEnumArray[7] = CODE_COMPLETE;
        tracerEnumArray[8] = RECORD_EXCEPTION;
        float = tracerEnumArray;
    }

    public String getText() {
        TracerEnum a;
        return a.enum;
    }
}
