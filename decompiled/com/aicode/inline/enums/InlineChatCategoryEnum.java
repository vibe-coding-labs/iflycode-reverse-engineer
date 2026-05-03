/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package com.aicode.inline.enums;

import com.aicode.util.IndentLineUtil;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class InlineChatCategoryEnum
extends Enum<InlineChatCategoryEnum> {
    private final String byte;
    private static final /* synthetic */ InlineChatCategoryEnum[] enum;
    public static final /* enum */ InlineChatCategoryEnum GENERATE;
    public static final /* enum */ InlineChatCategoryEnum UNKNOW;
    public static final /* enum */ InlineChatCategoryEnum LINEDOC;
    public static final /* enum */ InlineChatCategoryEnum DOC;
    public static final /* enum */ InlineChatCategoryEnum EDIT;

    static {
        DOC = new InlineChatCategoryEnum(IndentLineUtil.H("T\u0018K"));
        LINEDOC = new InlineChatCategoryEnum(IndentLineUtil.H("l\u001dE\nt\u0018K"));
        EDIT = new InlineChatCategoryEnum(IndentLineUtil.H("\nT\u001e\\"));
        GENERATE = new InlineChatCategoryEnum(IndentLineUtil.H("8e\u001aN\u001dQ\u0003M"));
        UNKNOW = new InlineChatCategoryEnum(IndentLineUtil.H("\u0001E\u0004^\u0018_"));
        InlineChatCategoryEnum[] inlineChatCategoryEnumArray = new InlineChatCategoryEnum[5];
        inlineChatCategoryEnumArray[0] = DOC;
        inlineChatCategoryEnumArray[1] = LINEDOC;
        inlineChatCategoryEnumArray[2] = EDIT;
        inlineChatCategoryEnumArray[3] = GENERATE;
        inlineChatCategoryEnumArray[4] = UNKNOW;
        enum = inlineChatCategoryEnumArray;
    }

    public static InlineChatCategoryEnum getCategoryEnumByValue(String a) {
        if (StringUtils.isBlank((CharSequence)a)) {
            return UNKNOW;
        }
        return Arrays.stream(InlineChatCategoryEnum.values()).filter(inlineChatCategoryEnum -> {
            Object a = inlineChatCategoryEnum;
            String a2 = a;
            return ((InlineChatCategoryEnum)((Object)((Object)a))).byte.equalsIgnoreCase(a2);
        }).findFirst().orElse(UNKNOW);
    }

    public String getValue() {
        InlineChatCategoryEnum a;
        return a.byte;
    }

    private InlineChatCategoryEnum(String string2) {
        Object a = string2;
        InlineChatCategoryEnum a2 = this;
        a2.byte = a;
    }

    public static InlineChatCategoryEnum valueOf(String a) {
        return Enum.valueOf(InlineChatCategoryEnum.class, a);
    }

    public static InlineChatCategoryEnum getCategoryEnumByName(String a) {
        if (StringUtils.isBlank((CharSequence)a)) {
            return UNKNOW;
        }
        return Arrays.stream(InlineChatCategoryEnum.values()).filter(inlineChatCategoryEnum -> {
            InlineChatCategoryEnum a = inlineChatCategoryEnum;
            String a2 = a;
            return a.name().equalsIgnoreCase(a2);
        }).findFirst().orElse(UNKNOW);
    }

    public static InlineChatCategoryEnum[] values() {
        return (InlineChatCategoryEnum[])enum.clone();
    }
}
