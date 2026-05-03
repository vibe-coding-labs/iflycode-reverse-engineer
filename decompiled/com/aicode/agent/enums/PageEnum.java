/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package com.aicode.agent.enums;

import com.aicode.util.Application;
import org.apache.commons.lang3.StringUtils;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class PageEnum
extends Enum<PageEnum> {
    public static final /* enum */ PageEnum UNIT_TEST;
    private static final /* synthetic */ PageEnum[] byte;
    public static final /* enum */ PageEnum CODE_CHECK;
    public static final /* enum */ PageEnum SETTING_PAGE;
    public static final /* enum */ PageEnum CODE_REVIEW;
    private final String enum;
    public static final /* enum */ PageEnum CHAT_VIEW;
    public static final /* enum */ PageEnum UNIT_TESTING;

    public static PageEnum[] values() {
        return (PageEnum[])byte.clone();
    }

    public String getType() {
        PageEnum a;
        return a.enum;
    }

    public static PageEnum valueOf(String a) {
        return Enum.valueOf(PageEnum.class, a);
    }

    static {
        CHAT_VIEW = new PageEnum(Application.H("*eMGz c\u007fhy"));
        SETTING_PAGE = new PageEnum(Application.H("-taqrLHi ewjk"));
        CODE_CHECK = new PageEnum(Application.H("+fiAC#n}sne"));
        CODE_REVIEW = new PageEnum(Application.H("(gjb@\u000b|hc\u007fhy"));
        UNIT_TEST = new PageEnum(Application.H("*sKOz as~z"));
        UNIT_TESTING = new PageEnum(Application.H("-rjlr\bRk~a\u007fci"));
        PageEnum[] pageEnumArray = new PageEnum[6];
        pageEnumArray[0] = CHAT_VIEW;
        pageEnumArray[1] = SETTING_PAGE;
        pageEnumArray[2] = CODE_CHECK;
        pageEnumArray[3] = CODE_REVIEW;
        pageEnumArray[4] = UNIT_TEST;
        pageEnumArray[5] = UNIT_TESTING;
        byte = pageEnumArray;
    }

    private PageEnum(String string2) {
        Object a = string2;
        PageEnum a2 = this;
        a2.enum = a;
    }

    public static PageEnum getByType(String string) {
        int a;
        String string2 = string;
        if (StringUtils.isBlank((CharSequence)string2)) {
            return null;
        }
        PageEnum[] pageEnumArray = PageEnum.values();
        int n = pageEnumArray.length;
        int n2 = a = 0;
        while (n2 < n) {
            PageEnum pageEnum = pageEnumArray[a];
            if (pageEnum.enum.equals(string2)) {
                return pageEnum;
            }
            n2 = ++a;
        }
        return null;
    }
}
