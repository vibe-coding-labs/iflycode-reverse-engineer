/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package com.aicode.enums;

import com.aicode.service.editor.RequestResultList;
import org.apache.commons.lang3.StringUtils;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class ChatOperationEnum
extends Enum<ChatOperationEnum> {
    public static final /* enum */ ChatOperationEnum ACTION_INSERT;
    public static final /* enum */ ChatOperationEnum ACTION_COPY;
    public static final /* enum */ ChatOperationEnum ACTION_NEW;
    public static final /* enum */ ChatOperationEnum ACTION_DIFF;
    public static final /* enum */ ChatOperationEnum ACTION_ACCEPT;
    public static final /* enum */ ChatOperationEnum ACTION_ACCEPT_INLINE_COMMENT;
    private static final /* synthetic */ ChatOperationEnum[] enum;

    static {
        ACTION_NEW = new ChatOperationEnum(RequestResultList.H("\u5bfa\u8bc7\u686e\u65b1\u5ec9\u659e\u4edd"));
        ACTION_DIFF = new ChatOperationEnum(RequestResultList.H("\u5bfa\u8bc7\u686e\u4ee2\u7832\u6bcd\u8fa8"));
        ACTION_INSERT = new ChatOperationEnum(RequestResultList.H("\u5bfa\u8bc7\u686e\u63d3\u5156\u4efa\u782a"));
        ACTION_COPY = new ChatOperationEnum(RequestResultList.H("\u5bfa\u8bc7\u686e\u590c\u5205\u4efa\u782a"));
        ACTION_ACCEPT = new ChatOperationEnum(RequestResultList.H("\u91c6\u7e80\u5ee3\u8b85"));
        ACTION_ACCEPT_INLINE_COMMENT = new ChatOperationEnum(RequestResultList.H("\u91dd\u7e9b\u884d\u95c7\u6cf1\u91e1"));
        ChatOperationEnum[] chatOperationEnumArray = new ChatOperationEnum[6];
        chatOperationEnumArray[0] = ACTION_NEW;
        chatOperationEnumArray[1] = ACTION_DIFF;
        chatOperationEnumArray[2] = ACTION_INSERT;
        chatOperationEnumArray[3] = ACTION_COPY;
        chatOperationEnumArray[4] = ACTION_ACCEPT;
        chatOperationEnumArray[5] = ACTION_ACCEPT_INLINE_COMMENT;
        enum = chatOperationEnumArray;
    }

    public static ChatOperationEnum[] values() {
        return (ChatOperationEnum[])enum.clone();
    }

    private ChatOperationEnum(String string2) {
        int a = n;
        ChatOperationEnum chatOperationEnum = this;
    }

    public static ChatOperationEnum getByName(String string) {
        int a;
        String string2 = string;
        if (StringUtils.isBlank((CharSequence)string2)) {
            return null;
        }
        ChatOperationEnum[] chatOperationEnumArray = ChatOperationEnum.values();
        int n = chatOperationEnumArray.length;
        int n2 = a = 0;
        while (n2 < n) {
            ChatOperationEnum chatOperationEnum = chatOperationEnumArray[a];
            if (chatOperationEnum.name().equals(string2)) {
                return chatOperationEnum;
            }
            n2 = ++a;
        }
        return null;
    }

    public static ChatOperationEnum valueOf(String a) {
        return Enum.valueOf(ChatOperationEnum.class, a);
    }
}
