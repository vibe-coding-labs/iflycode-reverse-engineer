/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.apm.enums;

import com.aicode.action.batch.MethodGeneratorConfig;
import com.aicode.diff.FileService;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class SpanAttrEnum
extends Enum<SpanAttrEnum> {
    public static final /* enum */ SpanAttrEnum PLUGIN_VERSION;
    public static final /* enum */ SpanAttrEnum AGENT_ERROR_REASON;
    public static final /* enum */ SpanAttrEnum COMPLETE_DURATION;
    public static final /* enum */ SpanAttrEnum SETTING_MESSAGE_TYPE;
    public static final /* enum */ SpanAttrEnum HTTP_SCHEME;
    public static final /* enum */ SpanAttrEnum SETTING_TRIGGER_ON_PAUSE;
    public static final /* enum */ SpanAttrEnum COMPLETE_IS_STREAM;
    private final String float;
    public static final /* enum */ SpanAttrEnum COMPLETE_FIRST_DURATION;
    public static final /* enum */ SpanAttrEnum DISABLE_GPU;
    public static final /* enum */ SpanAttrEnum EXCEPTION_COMMAND;
    public static final /* enum */ SpanAttrEnum AGENT_START_REASON;
    public static final /* enum */ SpanAttrEnum COMPLETE_ACCEPT;
    public static final /* enum */ SpanAttrEnum EXCEPTION_MESSAGE;
    public static final /* enum */ SpanAttrEnum AGENT_START_CODE;
    public static final /* enum */ SpanAttrEnum SETTING_CODE_MODE;
    public static final /* enum */ SpanAttrEnum AGENT_VERSION;
    public static final /* enum */ SpanAttrEnum SYSTEM_USERNAME;
    public static final /* enum */ SpanAttrEnum COMPLETE_FILE_LINE;
    public static final /* enum */ SpanAttrEnum COMPLETE_RESULT;
    private static final /* synthetic */ SpanAttrEnum[] byte;
    public static final /* enum */ SpanAttrEnum SETTING_JAVA_TEST;
    public static final /* enum */ SpanAttrEnum SETTING_TRIGGER_TIME_DELAY;
    public static final /* enum */ SpanAttrEnum COMPLETE_FORCE;
    public static final /* enum */ SpanAttrEnum COMPLETE_FILE_SIZE;
    public static final /* enum */ SpanAttrEnum COMPLETE_REJECT;
    public static final /* enum */ SpanAttrEnum EXCEPTION_CODE;
    public static final /* enum */ SpanAttrEnum COMMAND_ID;
    public static final /* enum */ SpanAttrEnum PLUGIN_UPDATE;
    public static final /* enum */ SpanAttrEnum USER_USERNAME;
    public static final /* enum */ SpanAttrEnum SETTING_JAVA_MOCK;
    public static final /* enum */ SpanAttrEnum IDEA_VERSION;
    private final String enum;

    public static SpanAttrEnum valueOf(String a) {
        return Enum.valueOf(SpanAttrEnum.class, a);
    }

    public String getText() {
        SpanAttrEnum a;
        return a.float;
    }

    static {
        SYSTEM_USERNAME = new SpanAttrEnum(FileService.H("\u0011<$12.\u007f\u0001\u0015:?*71+"), MethodGeneratorConfig.H("\u6499\u4f01\u7ca8\u7e9f\u7566\u623d\u5409"));
        IDEA_VERSION = new SpanAttrEnum(MethodGeneratorConfig.H("1282t\"8!3'ej"), FileService.H("6)!7\u7214\u6762"));
        AGENT_VERSION = new SpanAttrEnum(FileService.H("6\"2-%Z\u0010:?7?3 "), MethodGeneratorConfig.H("5:6.:\u7242\u6728"));
        PLUGIN_VERSION = new SpanAttrEnum(MethodGeneratorConfig.H("'5-14=t\"8!3'ej"), FileService.H("\u6396\u4ea0\u7214\u6762"));
        DISABLE_GPU = new SpanAttrEnum(FileService.H("3*\"\u0015\u00043(j1,;"), MethodGeneratorConfig.H("\u79d2\u7568\tZQ"));
        PLUGIN_UPDATE = new SpanAttrEnum(MethodGeneratorConfig.H("886>z(#$/~a"), FileService.H("\u6396\u4ea0\u66a8\u65fe"));
        USER_USERNAME = new SpanAttrEnum(FileService.H("\"621\u007f\u0001\u0015:?*71+"), MethodGeneratorConfig.H("\u6381\u4eb6\u7566\u623d\u5409"));
        HTTP_SCHEME = new SpanAttrEnum(MethodGeneratorConfig.H(".=4.<9?8'n;xh"), FileService.H("  50>\u0017\r:9d#.\""));
        AGENT_ERROR_REASON = new SpanAttrEnum(FileService.H(">*\u0015\f1y %1>\u0006H-(%%3 "), MethodGeneratorConfig.H("7:64 \u5472\u52fb\u5f42\u5e76\u5395\u56e4"));
        AGENT_START_REASON = new SpanAttrEnum(MethodGeneratorConfig.H("80mh#w+\"<!.z/6!=ej"), FileService.H("0\u0013\u000319\u546b\u52fe\u53c3\u56ae"));
        AGENT_START_CODE = new SpanAttrEnum(FileService.H("\u0011\u0005 91y0%\u0015\u0014+c'98+"), MethodGeneratorConfig.H("7:64 \u5472\u52fb#!na"));
        SETTING_TRIGGER_ON_PAUSE = new SpanAttrEnum(MethodGeneratorConfig.H("5-\u0005\u000b3:&a8\"|i\u0003+11:6(\u001b3\u0003!;ya"), FileService.H("\u546c\u52f9\u819e\u52ce\u89b9\u539c\u4ea7\u7857\u5ea6\u8be0"));
        SETTING_TRIGGER_TIME_DELAY = new SpanAttrEnum(FileService.H("15ky\u000b\u001e&}17+\"$\u0010,0\"21\u0005\u001d\u000b:\t!:=7"), MethodGeneratorConfig.H("\u5001\u982c\u89a6\u539f\u65fc\u95f0"));
        SETTING_CODE_MODE = new SpanAttrEnum(MethodGeneratorConfig.H("\u0002\u001a. (!>yoc9<*7)6\u0019;96\r!na"), FileService.H("\u4ebc\u784c\u5ebe\u8bf8\u9892\u6872"));
        SETTING_MESSAGE_TYPE = new SpanAttrEnum(FileService.H("\u0003$'$+1*^\u0011 9!\u001a&\"\u0007\u00078(\u0010/,+"), MethodGeneratorConfig.H("\u801c\u5974\u6815\u538b\u9055\u6dd5\u603c\u6349\u9560\u9147\u7f6a"));
        SETTING_JAVA_TEST = new SpanAttrEnum(MethodGeneratorConfig.H(";\u0014\u000b.=/(w=ip6\r=%)\u0015(5067!xo"), FileService.H(">'\t\f\u6d0f\u8b83\u681a\u67f8"));
        SETTING_JAVA_MOCK = new SpanAttrEnum(FileService.H("~\u0007\u00045:>%q'\u0011\u0014$\u001a*4(\u0017\u0006\u00072(39.%"), MethodGeneratorConfig.H("\u0017\u0012\f\u0015\u0010<#%\u684c\u67b2"));
        COMMAND_ID = new SpanAttrEnum(MethodGeneratorConfig.H("><79<=$`c`"), FileService.H("\u8bb4\u6c13\u0015\u0001:#0v5*"));
        EXCEPTION_COMMAND = new SpanAttrEnum(FileService.H("(\b\u0001 '1>,?Z\u00050 )72*"), MethodGeneratorConfig.H("\u8ba1\u6c1f2=13'\u5f42\u5e76\u630d\u4ee0"));
        EXCEPTION_CODE = new SpanAttrEnum(MethodGeneratorConfig.H("2!;3-'3;3}#!na"), FileService.H("\u5f4f\u5e7c\u72e0\u605d\u784f"));
        EXCEPTION_MESSAGE = new SpanAttrEnum(FileService.H("(\b\u0001 '1>,?Z\u000b:>77;+"), MethodGeneratorConfig.H("\u5f42\u5e76\u4feb\u606b"));
        COMPLETE_RESULT = new SpanAttrEnum(MethodGeneratorConfig.H("e84(:8'?z/63;fp"), FileService.H("\u6649\u5479\u6744\u8821\u513e\u652c\u6320"));
        COMPLETE_IS_STREAM = new SpanAttrEnum(FileService.H(".\u001f\u000f5; #&\u007f\u001d\u0015\f963=#"), MethodGeneratorConfig.H("\u666f\u5468\u6d4b\u5f0b"));
        COMPLETE_FIRST_DURATION = new SpanAttrEnum(MethodGeneratorConfig.H("-)%\u0001\u0013? $a?>zu#\u0018?33'\u001e!/24'ej"), FileService.H("\u0010\u0013\u000319\u99d2\u549b\u65aa\u95ba"));
        COMPLETE_FORCE = new SpanAttrEnum(FileService.H("\u0013\r(')274Z\u000f,\u000b+$?+"), MethodGeneratorConfig.H("\u8825\u5126\u7c71\u578f"));
        COMPLETE_FILE_SIZE = new SpanAttrEnum(MethodGeneratorConfig.H("4gk'5=\"8}<=16\u0013'pa"), FileService.H("\u8820\u513f\u65c4\u4ea7\u5953\u5c69w\u5318\u4f09=>g"));
        COMPLETE_FILE_LINE = new SpanAttrEnum(FileService.H(".\u001f\u000f5; #&\u007f\u0012\u000f3(\b?2+"), MethodGeneratorConfig.H("\u8831\u5135\u65d4\u4eb6\u6075\u8846\u6574"));
        COMPLETE_DURATION = new SpanAttrEnum(MethodGeneratorConfig.H("9;,?52|cy8?33'\u001e!/24'ej"), FileService.H("0\u0013\u000319\u8821\u513e\u804b\u65b8"));
        COMPLETE_REJECT = new SpanAttrEnum(FileService.H("#\u0011\f:.0x5*"), MethodGeneratorConfig.H("\u8838\u513b\u6292\u7e93c`"));
        COMPLETE_ACCEPT = new SpanAttrEnum(MethodGeneratorConfig.H("2978#4`c`"), FileService.H("\u883a\u5125\u63e1\u65605*"));
        SpanAttrEnum[] spanAttrEnumArray = new SpanAttrEnum[30];
        spanAttrEnumArray[0] = SYSTEM_USERNAME;
        spanAttrEnumArray[1] = IDEA_VERSION;
        spanAttrEnumArray[2] = AGENT_VERSION;
        spanAttrEnumArray[3] = PLUGIN_VERSION;
        spanAttrEnumArray[4] = DISABLE_GPU;
        spanAttrEnumArray[5] = PLUGIN_UPDATE;
        spanAttrEnumArray[6] = USER_USERNAME;
        spanAttrEnumArray[7] = HTTP_SCHEME;
        spanAttrEnumArray[8] = AGENT_ERROR_REASON;
        spanAttrEnumArray[9] = AGENT_START_REASON;
        spanAttrEnumArray[10] = AGENT_START_CODE;
        spanAttrEnumArray[11] = SETTING_TRIGGER_ON_PAUSE;
        spanAttrEnumArray[12] = SETTING_TRIGGER_TIME_DELAY;
        spanAttrEnumArray[13] = SETTING_CODE_MODE;
        spanAttrEnumArray[14] = SETTING_MESSAGE_TYPE;
        spanAttrEnumArray[15] = SETTING_JAVA_TEST;
        spanAttrEnumArray[16] = SETTING_JAVA_MOCK;
        spanAttrEnumArray[17] = COMMAND_ID;
        spanAttrEnumArray[18] = EXCEPTION_COMMAND;
        spanAttrEnumArray[19] = EXCEPTION_CODE;
        spanAttrEnumArray[20] = EXCEPTION_MESSAGE;
        spanAttrEnumArray[21] = COMPLETE_RESULT;
        spanAttrEnumArray[22] = COMPLETE_IS_STREAM;
        spanAttrEnumArray[23] = COMPLETE_FIRST_DURATION;
        spanAttrEnumArray[24] = COMPLETE_FORCE;
        spanAttrEnumArray[25] = COMPLETE_FILE_SIZE;
        spanAttrEnumArray[26] = COMPLETE_FILE_LINE;
        spanAttrEnumArray[27] = COMPLETE_DURATION;
        spanAttrEnumArray[28] = COMPLETE_REJECT;
        spanAttrEnumArray[29] = COMPLETE_ACCEPT;
        byte = spanAttrEnumArray;
    }

    public static SpanAttrEnum[] values() {
        return (SpanAttrEnum[])byte.clone();
    }

    /*
     * WARNING - void declaration
     */
    private SpanAttrEnum(String string2, String string3) {
        Object a;
        void a2;
        SpanAttrEnum a3;
        SpanAttrEnum spanAttrEnum = object;
        Object object = string3;
        SpanAttrEnum spanAttrEnum2 = a3 = spanAttrEnum;
        spanAttrEnum2.float = a2;
        spanAttrEnum2.enum = a;
    }

    public String getDesc() {
        SpanAttrEnum a;
        return a.enum;
    }
}
