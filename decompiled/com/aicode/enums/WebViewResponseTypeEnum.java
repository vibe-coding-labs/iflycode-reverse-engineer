/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.enums;

import com.aicode.agent.service.GitReviewService;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class WebViewResponseTypeEnum
extends Enum<WebViewResponseTypeEnum> {
    public static final /* enum */ WebViewResponseTypeEnum CODE_SEARCH_GET_CODESEARCH_REPOSITORY_LIST;
    public static final /* enum */ WebViewResponseTypeEnum SQL_CHAT_RECEIVE_TABLE_LIST;
    public static final /* enum */ WebViewResponseTypeEnum CODE_SEARCH_GET_CODE_COPY_SUCCESS;
    public static final /* enum */ WebViewResponseTypeEnum CODE_REVIEW_RECEIVER_PAGE_INIT;
    public static final /* enum */ WebViewResponseTypeEnum SQL_CHAT_UPDATE_CONVERSATION_LIST;
    public static final /* enum */ WebViewResponseTypeEnum CODE_REVIEW_RECEIVER_CHANGE_RESULT;
    public static final /* enum */ WebViewResponseTypeEnum SQL_CHAT_RECEIVE_SAVE;
    public static final /* enum */ WebViewResponseTypeEnum CODE_SEARCH_GET_CODESEARCH_LANGUAGE_LIST;
    public static final /* enum */ WebViewResponseTypeEnum SQL_CHAT_RECEIVE_SOURCE_LIST;
    public static final /* enum */ WebViewResponseTypeEnum SQL_CHAT_RECEIVE_SOURCE_TYPES;
    private static final /* synthetic */ WebViewResponseTypeEnum[] byte;
    private final String enum;
    public static final /* enum */ WebViewResponseTypeEnum SQL_CHAT_RECEIVE_LINK_TEST;
    public static final /* enum */ WebViewResponseTypeEnum USER_PERMISSION_LIST;
    public static final /* enum */ WebViewResponseTypeEnum CODE_SEARCH_GET_CODESEARCH_CODE_LIST;
    public static final /* enum */ WebViewResponseTypeEnum CODE_REVIEW_RECEIVER_CODE_REVIEW;
    public static final /* enum */ WebViewResponseTypeEnum SETTING_CHANGE_THEME;

    public static WebViewResponseTypeEnum valueOf(String a) {
        return Enum.valueOf(WebViewResponseTypeEnum.class, a);
    }

    public String getType() {
        WebViewResponseTypeEnum a;
        return a.enum;
    }

    public static WebViewResponseTypeEnum[] values() {
        return (WebViewResponseTypeEnum[])byte.clone();
    }

    private WebViewResponseTypeEnum(String string2) {
        Object a = string2;
        WebViewResponseTypeEnum a2 = this;
        a2.enum = a;
    }

    static {
        USER_PERMISSION_LIST = new WebViewResponseTypeEnum(GitReviewService.H("\u00049\f \"s\u0002.\u0005:\u001e%\u0003>\u0003)\u001c\"Iu"));
        SETTING_CHANGE_THEME = new WebViewResponseTypeEnum(GitReviewService.H("\u0002/\u001d&Qm\u0000F\u000b;\f8\r4\u0012\"\u0018.Wd"));
        CODE_SEARCH_GET_CODESEARCH_CODE_LIST = new WebViewResponseTypeEnum(GitReviewService.H("\u0014#\u0005?/\u0018\u0018'\u0006,\t@&\u001f\u001e.\u0012%\r7Kf\u0006.\u000b;\u00125\u00055\b)\u001c\"Iu"));
        CODE_SEARCH_GET_CODESEARCH_REPOSITORY_LIST = new WebViewResponseTypeEnum(GitReviewService.H("\u0017 \u000f5\u0015\"\u0012-\u001398q\u001a#\u00000\u00025%\u001f\u00194\u00108\n:Gq\u0002,\u0007 \u0004\"\u0005#\u0014)\u001c\"Iu"));
        CODE_SEARCH_GET_CODESEARCH_LANGUAGE_LIST = new WebViewResponseTypeEnum(GitReviewService.H("\b?\u000e4\b?\u0004;\"\b\u0015\\\u0013*\u0015%\"\u0015\u000e4\u0002/\b [k\u00180\t=\n#\u000b6\b)\u001c\"Iu"));
        CODE_SEARCH_GET_CODE_COPY_SUCCESS = new WebViewResponseTypeEnum(GitReviewService.H("9?\u000f\u00189\u0007*\u0000(\"\u0012p6\u0014>\u00161Wg\u0002#\u000b<\u001d/\u0015\"\u00185\u0013.Ir"));
        CODE_REVIEW_RECEIVER_PAGE_INIT = new WebViewResponseTypeEnum(GitReviewService.H("\u001e)\u0010*\u001e($\f\u00034\u0006P\u001b7[f\u000e*\r!\u0012&\u000b6\b)\u0019%Su"));
        CODE_REVIEW_RECEIVER_CODE_REVIEW = new WebViewResponseTypeEnum(GitReviewService.H("3\u0004\u0019#\u000b=\u0004,(\u001f\u001dK\u0003/\n7Qu\u0002.\u00170\u00022\u000f.\u001f3\u0006\"_v"));
        CODE_REVIEW_RECEIVER_CHANGE_RESULT = new WebViewResponseTypeEnum(GitReviewService.H("\u000254\u000e\u00024\u00119\b?6`\u00184\u0012/\u0000$]q\u0018?\u00002\u00031\u000f.\u001f3\u0003>Vu"));
        SQL_CHAT_RECEIVE_SOURCE_LIST = new WebViewResponseTypeEnum(GitReviewService.H("\u0007>\r%\"\u0012\u000b%k8\f1]j\u00119\u0017 \u0002#\u00182\b)\u001c\"Iu"));
        SQL_CHAT_RECEIVE_SOURCE_TYPES = new WebViewResponseTypeEnum(GitReviewService.H("5\u0005#\u001e9)\u001b\u001eK\u0003/\n7Qu\u0002#\u001b<\u0018$\t4\u0012\"\t;_r"));
        SQL_CHAT_RECEIVE_LINK_TEST = new WebViewResponseTypeEnum(GitReviewService.H("\u0012+-\u0005\t9\u0010>s ]`\u00025\u001e6\u0012:\u0003?\u0006)\u0004.Iu"));
        SQL_CHAT_RECEIVE_SAVE = new WebViewResponseTypeEnum(GitReviewService.H("\"\u0000&\u00161Pb\u0013F\u001a6\u000e3\u0003'\b)\u0003*Ld"));
        SQL_CHAT_RECEIVE_TABLE_LIST = new WebViewResponseTypeEnum(GitReviewService.H("<\u00106>\u0019\u00020\u0005P\u001b7[f\u000e*\r,\u00197\b=\b)\u001c\"Iu"));
        SQL_CHAT_UPDATE_CONVERSATION_LIST = new WebViewResponseTypeEnum(GitReviewService.H(")!\u0007\u0002%\u001c.\u0015@4\n\u000e0\u0005/\u00161Wm\u00119\u001a \f\"\u0003>\u0003)\u001c\"Iu"));
        WebViewResponseTypeEnum[] webViewResponseTypeEnumArray = new WebViewResponseTypeEnum[15];
        webViewResponseTypeEnumArray[0] = USER_PERMISSION_LIST;
        webViewResponseTypeEnumArray[1] = SETTING_CHANGE_THEME;
        webViewResponseTypeEnumArray[2] = CODE_SEARCH_GET_CODESEARCH_CODE_LIST;
        webViewResponseTypeEnumArray[3] = CODE_SEARCH_GET_CODESEARCH_REPOSITORY_LIST;
        webViewResponseTypeEnumArray[4] = CODE_SEARCH_GET_CODESEARCH_LANGUAGE_LIST;
        webViewResponseTypeEnumArray[5] = CODE_SEARCH_GET_CODE_COPY_SUCCESS;
        webViewResponseTypeEnumArray[6] = CODE_REVIEW_RECEIVER_PAGE_INIT;
        webViewResponseTypeEnumArray[7] = CODE_REVIEW_RECEIVER_CODE_REVIEW;
        webViewResponseTypeEnumArray[8] = CODE_REVIEW_RECEIVER_CHANGE_RESULT;
        webViewResponseTypeEnumArray[9] = SQL_CHAT_RECEIVE_SOURCE_LIST;
        webViewResponseTypeEnumArray[10] = SQL_CHAT_RECEIVE_SOURCE_TYPES;
        webViewResponseTypeEnumArray[11] = SQL_CHAT_RECEIVE_LINK_TEST;
        webViewResponseTypeEnumArray[12] = SQL_CHAT_RECEIVE_SAVE;
        webViewResponseTypeEnumArray[13] = SQL_CHAT_RECEIVE_TABLE_LIST;
        webViewResponseTypeEnumArray[14] = SQL_CHAT_UPDATE_CONVERSATION_LIST;
        byte = webViewResponseTypeEnumArray;
    }
}
