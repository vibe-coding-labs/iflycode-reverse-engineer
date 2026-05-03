/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.enums;

import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.util.Application;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class RestartEnum
extends Enum<RestartEnum> {
    public static final /* enum */ RestartEnum REFRESH;
    private final String try;
    private final int float;
    public static final /* enum */ RestartEnum HEART_BEAT_ERROR;
    public static final /* enum */ RestartEnum CONNECT_REFUSED;
    private final String byte;
    public static final /* enum */ RestartEnum CLOSE_EXCEPTION;
    public static final /* enum */ RestartEnum CLOSE_RECONNECT;
    public static final /* enum */ RestartEnum START_AGENT;
    public static final /* enum */ RestartEnum REFRESH_RECONNECT;
    private static final /* synthetic */ RestartEnum[] enum;
    public static final /* enum */ RestartEnum CONNECT_ERROR;
    public static final /* enum */ RestartEnum BLANK_PORT;
    public static final /* enum */ RestartEnum CONNECT_FAILED;
    public static final /* enum */ RestartEnum CLOSE_ERROR;

    public int getCode() {
        RestartEnum a;
        return a.float;
    }

    public String getText() {
        RestartEnum a;
        return a.try;
    }

    static {
        START_AGENT = new RestartEnum(0, OpenTelemetryUtil.H("\u0016r4J\u0014/8`1~<"), Application.H("gih{b\u5422\u52a6"));
        CONNECT_REFUSED = new RestartEnum(1, Application.H("pbc}d4tnl|twc\u0005Tkk`ehj"), OpenTelemetryUtil.H("\u0010C\u0005a?\u62c6\u7e8d\u8fd2\u63ed"));
        CONNECT_FAILED = new RestartEnum(2, OpenTelemetryUtil.H("E'w8aqc+}9p2P@i*}<i,"), Application.H("VBC`y\u8fcb\u63b3\u593c\u8d2b"));
        CONNECT_ERROR = new RestartEnum(3, Application.H("Gtuzc!a}\u007fzrFR.hgdb|"), OpenTelemetryUtil.H("\u0010C\u0005a?\u8fca\u63f5\u9515\u8ba7"));
        CLOSE_EXCEPTION = new RestartEnum(4, OpenTelemetryUtil.H("\u0017r4n034y>W\u0005/.f\"c:"), Application.H("VBC`y\u5f17\u5e2e\u517e\u95e3"));
        CLOSE_ERROR = new RestartEnum(5, Application.H("Qsrov2rxxVC.hgdb|"), OpenTelemetryUtil.H("\u0010C\u0005a?\u950d\u8bbf\u517f\u95a5"));
        BLANK_PORT = new RestartEnum(6, OpenTelemetryUtil.H("T6e*gwe>V\u0014/)x1b#"), Application.H("VBC`y\u7afa\u53f5\u4e37\u7a74"));
        REFRESH = new RestartEnum(7, Application.H("Cutzc\u0005Tkkgs~f"), OpenTelemetryUtil.H("!h.z$\u523b\u65f8"));
        HEART_BEAT_ERROR = new RestartEnum(8, OpenTelemetryUtil.H("r\u0010a.fv}4a6gww4E\u0014/.f\"c:"), Application.H("Cutzc\u5fe6\u8dd5\u68ce\u6d46\u5924\u8d33\u91c0\u5421"));
        CLOSE_RECONNECT = new RestartEnum(9, Application.H("pbc}d4tmmat4e@Eac{snz"), OpenTelemetryUtil.H("T6A\u000e{\u5138\u95f9\u65a6\u91c1\u8f96"));
        REFRESH_RECONNECT = new RestartEnum(10, OpenTelemetryUtil.H("JT\u0012j42$p7r!`?5#A\u0003`%z5o<"), Application.H("Up@Hz\u523a\u65a5\u65e0\u91c0\u8fd0"));
        RestartEnum[] restartEnumArray = new RestartEnum[11];
        restartEnumArray[0] = START_AGENT;
        restartEnumArray[1] = CONNECT_REFUSED;
        restartEnumArray[2] = CONNECT_FAILED;
        restartEnumArray[3] = CONNECT_ERROR;
        restartEnumArray[4] = CLOSE_EXCEPTION;
        restartEnumArray[5] = CLOSE_ERROR;
        restartEnumArray[6] = BLANK_PORT;
        restartEnumArray[7] = REFRESH;
        restartEnumArray[8] = HEART_BEAT_ERROR;
        restartEnumArray[9] = CLOSE_RECONNECT;
        restartEnumArray[10] = REFRESH_RECONNECT;
        enum = restartEnumArray;
    }

    public String getDesc() {
        RestartEnum a;
        return a.byte;
    }

    /*
     * WARNING - void declaration
     */
    private RestartEnum(int n2, String string2, String string3) {
        Object a;
        void a2;
        void a3;
        RestartEnum a4;
        RestartEnum restartEnum = object;
        Object object = string3;
        RestartEnum restartEnum2 = a4 = restartEnum;
        a4.float = a3;
        restartEnum2.try = a2;
        restartEnum2.byte = a;
    }

    public static RestartEnum valueOf(String a) {
        return Enum.valueOf(RestartEnum.class, a);
    }

    public static RestartEnum[] values() {
        return (RestartEnum[])enum.clone();
    }
}
