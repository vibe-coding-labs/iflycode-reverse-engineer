/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package com.aicode.enums;

import com.aicode.content.util.EditorUtils;
import com.aicode.diff.FileService;
import org.apache.commons.lang3.StringUtils;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class ClientTypeEnum
extends Enum<ClientTypeEnum> {
    public static final /* enum */ ClientTypeEnum WS;
    public static final /* enum */ ClientTypeEnum IC;
    public static final /* enum */ ClientTypeEnum PY;
    public static final /* enum */ ClientTypeEnum IE;
    private final String final;
    public static final /* enum */ ClientTypeEnum AI;
    public static final /* enum */ ClientTypeEnum PC;
    private final String try;
    public static final /* enum */ ClientTypeEnum CL;
    private final String float;
    public static final /* enum */ ClientTypeEnum IU;
    public static final /* enum */ ClientTypeEnum GO;
    private final String byte;
    private static final /* synthetic */ ClientTypeEnum[] enum;

    public static ClientTypeEnum valueOf(String a) {
        return Enum.valueOf(ClientTypeEnum.class, a);
    }

    /*
     * WARNING - void declaration
     */
    private ClientTypeEnum(String string2, String string3, String string4, String string5) {
        Object a;
        void a2;
        void a3;
        void a4;
        ClientTypeEnum a5;
        ClientTypeEnum clientTypeEnum = object;
        Object object = string5;
        ClientTypeEnum clientTypeEnum2 = a5 = clientTypeEnum;
        ClientTypeEnum clientTypeEnum3 = a5;
        clientTypeEnum3.try = a4;
        clientTypeEnum3.byte = a3;
        clientTypeEnum2.float = a2;
        clientTypeEnum2.final = a;
    }

    public String getWindowsExeFile() {
        ClientTypeEnum a;
        return a.float;
    }

    public String getDescription() {
        ClientTypeEnum a;
        return a.try;
    }

    public String getJetBrainPlatform() {
        ClientTypeEnum a;
        return a.byte;
    }

    public String getUnixExeFile() {
        ClientTypeEnum a;
        return a.final;
    }

    public static ClientTypeEnum[] values() {
        return (ClientTypeEnum[])enum.clone();
    }

    public static ClientTypeEnum getExeFileName(String string) {
        int a;
        String string2 = string;
        if (StringUtils.isBlank((CharSequence)string2)) {
            return IC;
        }
        ClientTypeEnum[] clientTypeEnumArray = ClientTypeEnum.values();
        int n = clientTypeEnumArray.length;
        int n2 = a = 0;
        while (n2 < n) {
            ClientTypeEnum clientTypeEnum = clientTypeEnumArray[a];
            if (string2.startsWith(clientTypeEnum.name())) {
                return clientTypeEnum;
            }
            n2 = ++a;
        }
        return IC;
    }

    static {
        IE = new ClientTypeEnum(FileService.H("/\u001b\b\u0005\u650f\u80ee\u7206"), EditorUtils.H("O\u0019[\u0004"), FileService.H("=\"\u0011\u0007iyj3$+"), EditorUtils.H("o9{$"));
        IC = new ClientTypeEnum(EditorUtils.H("$Y\u0003G\u7963\u5324\u720d"), FileService.H("\r\u0012\u0019\u000f"), EditorUtils.H("\u007f)S\f+r(8f "), FileService.H("-29/"));
        IU = new ClientTypeEnum(FileService.H("/\u001b\b\u0005\u4e45\u4e46\u7206"), EditorUtils.H("O\u0019[\u0004"), FileService.H("=\"\u0011\u0007iyj3$+"), EditorUtils.H("o9{$"));
        WS = new ClientTypeEnum(EditorUtils.H("a\b\u007f\u0015r2l("), FileService.H("#\u0003=\u001e09.#"), EditorUtils.H("}4a+b\"D\u0000+r(8f "), FileService.H("\u0003\u0003=>09.#"));
        PY = new ClientTypeEnum(FileService.H("\u0004?7\u000e>?)\u4e45\u4e46\u7206"), EditorUtils.H("=d\u0005n<l("), FileService.H("*80<'\u0006\u000biyj3$+"), EditorUtils.H("\u001dd%n<l("));
        PC = new ClientTypeEnum(EditorUtils.H("F4u\u0005|4k\u7963\u5324\u720d"), FileService.H("6&\u000e,7.#"), EditorUtils.H("!z;~,D\u0000+r(8f "), FileService.H("\u0016&.,7.#"));
        CL = new ClientTypeEnum(FileService.H("\u000e\b?3 "), EditorUtils.H("\u0005J4q+"), FileService.H("08/\u001b\biyj3$+"), EditorUtils.H("%j4q+"));
        GO = new ClientTypeEnum(EditorUtils.H("Z)J<p!"), FileService.H("\u0018\"\b72*"), EditorUtils.H("q\"Z\fs\"(8f "), FileService.H("8\"(72*"));
        AI = new ClientTypeEnum(FileService.H("\t4%!;/\u0010F\f9125!"), EditorUtils.H("K?g*y$RMN2s9w*"), FileService.H("2'!\"\u001d\tiyj3$+"), EditorUtils.H("n2s9w*"));
        ClientTypeEnum[] clientTypeEnumArray = new ClientTypeEnum[9];
        clientTypeEnumArray[0] = IE;
        clientTypeEnumArray[1] = IC;
        clientTypeEnumArray[2] = IU;
        clientTypeEnumArray[3] = WS;
        clientTypeEnumArray[4] = PY;
        clientTypeEnumArray[5] = PC;
        clientTypeEnumArray[6] = CL;
        clientTypeEnumArray[7] = GO;
        clientTypeEnumArray[8] = AI;
        enum = clientTypeEnumArray;
    }
}
