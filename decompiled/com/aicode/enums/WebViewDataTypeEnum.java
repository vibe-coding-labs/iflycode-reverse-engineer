/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package com.aicode.enums;

import com.aicode.agent.enums.ModuleEnum;
import com.aicode.util.AICodeUtils;
import org.apache.commons.lang3.StringUtils;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class WebViewDataTypeEnum
extends Enum<WebViewDataTypeEnum> {
    public static final /* enum */ WebViewDataTypeEnum BATCH_UNIT_TEST_MESSAGE;
    public static final /* enum */ WebViewDataTypeEnum CHAT_GET_HISTORY_LIST;
    public static final /* enum */ WebViewDataTypeEnum COMMON_OPEN_FILE_DIALOG;
    public static final /* enum */ WebViewDataTypeEnum BATCH_UNIT_TEST_GET_TASK_LIST;
    public static final /* enum */ WebViewDataTypeEnum CODE_REVIEW_GET_CHANGE_RESULT_END;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TESTING_RECEIVE_FUNCTION;
    public static final /* enum */ WebViewDataTypeEnum CHAT_GET_CONVERSATION;
    public static final /* enum */ WebViewDataTypeEnum GIT_RE_INDEX;
    public static final /* enum */ WebViewDataTypeEnum CHAT_SEND_VALID_WEBSITE_RESULT;
    public static final /* enum */ WebViewDataTypeEnum CHAT_CHOOSE_HISTORY_ITEM;
    public static final /* enum */ WebViewDataTypeEnum COMMON_EVALUATION;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TESTING_WEB_STOP;
    public static final /* enum */ WebViewDataTypeEnum CHAT_DELETE_MSG;
    public static final /* enum */ WebViewDataTypeEnum CODE_REVIEW_GET_CHANGE_RESULT;
    public static final /* enum */ WebViewDataTypeEnum CHAT_GET_OPEN_DIR_LIST;
    public static final /* enum */ WebViewDataTypeEnum SQL_CHAT_UPDATE_CONVERSATION_LIST;
    public static final /* enum */ WebViewDataTypeEnum CHAT_VALID_WEBSITE;
    public static final /* enum */ WebViewDataTypeEnum CODE_SEARCH_REQUEST_CODESEARCH_CODE_LIST;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TEST_GET_CASE_CODE;
    public static final /* enum */ WebViewDataTypeEnum CODE_CHECK_FIX;
    public static final /* enum */ WebViewDataTypeEnum SQL_CHAT_SOURCE_LIST;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TEST_REQUEST_UT_INFO;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TEST_FUNCTION_LIST;
    public static final /* enum */ WebViewDataTypeEnum CODE_CHECK_UPDATE_CODE_CHECK;
    private String float;
    public static final /* enum */ WebViewDataTypeEnum CHAT_DELETE_HISTORY_ITEM;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TEST_RECEIVE_FUNCTION_CASE_CODE;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TESTING_SAVE;
    public static final /* enum */ WebViewDataTypeEnum GIT_GET_STATUS;
    public static final /* enum */ WebViewDataTypeEnum CHAT_GET_USER_INFO;
    public static final /* enum */ WebViewDataTypeEnum COMMON_FEEDBACK;
    public static final /* enum */ WebViewDataTypeEnum SQL_CHAT_TABLE_LIST;
    public static final /* enum */ WebViewDataTypeEnum CHAT_STOP_RESPONSE;
    private ModuleEnum byte;
    public static final /* enum */ WebViewDataTypeEnum CHAT_RESEND;
    public static final /* enum */ WebViewDataTypeEnum GIT_CODE_KNOWLEDGE_REPO_STATUS;
    public static final /* enum */ WebViewDataTypeEnum BATCH_UNIT_TEST_GET_LIST;
    public static final /* enum */ WebViewDataTypeEnum COMMON_FOCUS_FILE;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TEST_REQUEST_ALL_CODE_FILE;
    public static final /* enum */ WebViewDataTypeEnum SETTING_GET_CAN_OPEN_CODE_ENHANCE;
    public static final /* enum */ WebViewDataTypeEnum CHAT_GET_FEEDBACK_LIST;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TESTING_PAGE_READY;
    public static final /* enum */ WebViewDataTypeEnum CHAT_AGENT_REFRESH;
    public static final /* enum */ WebViewDataTypeEnum CHAT_FEEDBACK_CATEGORY;
    public static final /* enum */ WebViewDataTypeEnum BATCH_UNIT_TEST_REFRESH_TASK_DOWNLOAD_STATUS;
    public static final /* enum */ WebViewDataTypeEnum CHAT_GET_MODEL_LIST;
    public static final /* enum */ WebViewDataTypeEnum CHAT_CHOOSE_FILE;
    public static final /* enum */ WebViewDataTypeEnum LOGIN_SHOW_FRESH;
    public static final /* enum */ WebViewDataTypeEnum COMMON_OPEN_PAGE;
    public static final /* enum */ WebViewDataTypeEnum SQL_CHAT_SQL_SAVE;
    public static final /* enum */ WebViewDataTypeEnum CHAT_DELETE_HISTORY_ITEM_ALL;
    public static final /* enum */ WebViewDataTypeEnum CHAT_RECEIVER_HISTORY_LIST;
    public static final /* enum */ WebViewDataTypeEnum LOGIN_GO_LOGIN;
    public static final /* enum */ WebViewDataTypeEnum LOG;
    public static final /* enum */ WebViewDataTypeEnum BATCH_UNIT_TEST_DOWNLOAD;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TEST_SAVE_CODE;
    public static final /* enum */ WebViewDataTypeEnum CHAT_SEND_MSG;
    public static final /* enum */ WebViewDataTypeEnum CHAT_GET_IDE_FILE_STATE;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TESTING_MAPPING_FILE;
    public static final /* enum */ WebViewDataTypeEnum LOGIN_LOGIN_SUCCEED;
    public static final /* enum */ WebViewDataTypeEnum SETTING_RECEIVE_REPO_STATUS;
    public static final /* enum */ WebViewDataTypeEnum SQL_CHAT_SEND_MSG;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TESTING_IDEA_STOP;
    public static final /* enum */ WebViewDataTypeEnum CODE_REVIEW_PAGE_READY;
    public static final /* enum */ WebViewDataTypeEnum CODE_SEARCH_REQUEST_OPEN_URL;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TEST_RECEIVE_FUNCTION_CASE;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TEST_REQUEST_CASE_CODE;
    public static final /* enum */ WebViewDataTypeEnum SETTING_GET_CONFIG;
    public static final /* enum */ WebViewDataTypeEnum CODE_CHECK_REQUEST_CODE_CHECK_LIST;
    public static final /* enum */ WebViewDataTypeEnum GIT_SAVE_TOKEN;
    private static final /* synthetic */ WebViewDataTypeEnum[] enum;
    public static final /* enum */ WebViewDataTypeEnum CHAT_GET_DOC_KNOWLEDGE_LIST;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TEST_COPY_CASE_CODE;
    public static final /* enum */ WebViewDataTypeEnum COMMON_FOCUS_FILE_LINE;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TEST_REQUEST_METHOD_CASE;
    public static final /* enum */ WebViewDataTypeEnum SETTING_POPUP_KEYMAP_SETTINGS;
    public static final /* enum */ WebViewDataTypeEnum CHAT_UPDATE_CONVERSATION_LIST;
    public static final /* enum */ WebViewDataTypeEnum CHAT_RECOMMEND_GAMEPLAY;
    public static final /* enum */ WebViewDataTypeEnum CODE_SEARCH_REQUEST_CODESEARCH_REPOSITORY_LIST;
    public static final /* enum */ WebViewDataTypeEnum CODE_SEARCH_REQUEST_INSERT_CODE;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TEST_FUNCTION_CASE;
    public static final /* enum */ WebViewDataTypeEnum CHAT_RECEIVER_CODE_KNOWLEDGE_LIST;
    public static final /* enum */ WebViewDataTypeEnum SQL_CHAT_SQL_LINK_TEST;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TEST_GET_ALL_CODE_FILE;
    public static final /* enum */ WebViewDataTypeEnum LOGIN_INIT;
    public static final /* enum */ WebViewDataTypeEnum SQL_CHAT_NEW_CHAT;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TEST_FUNCTION_CASE_CODE;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TEST_GET_METHOD_CASE;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TESTING_RESPONSE_SAVE;
    public static final /* enum */ WebViewDataTypeEnum COMMON_OPEN_URL;
    public static final /* enum */ WebViewDataTypeEnum CHAT_RECEIVER_DOC_KNOWLEDGE_LIST;
    public static final /* enum */ WebViewDataTypeEnum SQL_CHAT_SOURCE_DELETE;
    public static final /* enum */ WebViewDataTypeEnum BATCH_UNIT_TEST_DELETE;
    public static final /* enum */ WebViewDataTypeEnum CHAT_GET_CODE_KNOWLEDGE_LIST;
    public static final /* enum */ WebViewDataTypeEnum COMMON_CODE_CLICK_ACTION;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TESTING_RECEIVE_DATA;
    public static final /* enum */ WebViewDataTypeEnum CHAT_SEND_OPEN_DIR_LIST;
    public static final /* enum */ WebViewDataTypeEnum SQL_CHAT_REQUEST_SOURCE_TYPES;
    public static final /* enum */ WebViewDataTypeEnum LOGIN_CLOSE_QR_CODE;
    public static final /* enum */ WebViewDataTypeEnum COMMON_PAGE_READY;
    public static final /* enum */ WebViewDataTypeEnum CHAT_NEW_CHAT;
    public static final /* enum */ WebViewDataTypeEnum LOGIN_LOGIN_CHECK;
    public static final /* enum */ WebViewDataTypeEnum CHAT_RECEIVER_RECOMMEND_GAMEPLAY;
    public static final /* enum */ WebViewDataTypeEnum CHAT_REFRESH_MODEL;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TEST_REGENERATE;
    public static final /* enum */ WebViewDataTypeEnum COMMON_PLUGIN_BASE_INFO;
    public static final /* enum */ WebViewDataTypeEnum CHAT_SET_MODEL;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TEST_PAGE_READY;
    public static final /* enum */ WebViewDataTypeEnum CODE_CHECK_GET_CODE_CHECK_LIST;
    public static final /* enum */ WebViewDataTypeEnum SQL_CHAT_GET_MODEL_LIST;
    public static final /* enum */ WebViewDataTypeEnum CHAT_RECEIVER_IDE_FILE_STATE;
    public static final /* enum */ WebViewDataTypeEnum LOGIN_RECEIVER_LOGIN_IFRAME_SRC;
    public static final /* enum */ WebViewDataTypeEnum CODE_SEARCH_REQUEST_CODE_FILE;
    public static final /* enum */ WebViewDataTypeEnum SETTING_UPDATE_CONFIG;
    public static final /* enum */ WebViewDataTypeEnum SAVE_SHOW_OPERATE_GUIDANCE;
    public static final /* enum */ WebViewDataTypeEnum UNIT_TEST_GET_UT_INFO;
    public static final /* enum */ WebViewDataTypeEnum LOGIN_LOGIN_ABORT;
    public static final /* enum */ WebViewDataTypeEnum SQL_CHAT_STOP_RESPONSE;
    public static final /* enum */ WebViewDataTypeEnum BATCH_UNIT_TEST_CREATE;
    public static final /* enum */ WebViewDataTypeEnum GIT_AUTHORIZE;
    public static final /* enum */ WebViewDataTypeEnum CHAT_PREDICT;
    public static final /* enum */ WebViewDataTypeEnum COMMON_SHOW_MESSAGE_IN_WEB;
    public static final /* enum */ WebViewDataTypeEnum CODE_REVIEW_GET_CODEREVIEW_LIST;
    public static final /* enum */ WebViewDataTypeEnum LOGIN_LOGOUT;
    public static final /* enum */ WebViewDataTypeEnum CODE_SEARCH_REQUEST_COPY_CODE;
    public static final /* enum */ WebViewDataTypeEnum CODE_SEARCH_REQUEST_CODESEARCH_LANGUAGE_LIST;
    public static final /* enum */ WebViewDataTypeEnum COMMON_DOWNLOAD_TABLE;

    /*
     * WARNING - void declaration
     */
    private WebViewDataTypeEnum(String string2, ModuleEnum moduleEnum) {
        Enum a;
        void a2;
        WebViewDataTypeEnum a3;
        WebViewDataTypeEnum webViewDataTypeEnum = enum_;
        Enum enum_ = moduleEnum;
        WebViewDataTypeEnum webViewDataTypeEnum2 = a3 = webViewDataTypeEnum;
        webViewDataTypeEnum2.float = a2;
        webViewDataTypeEnum2.byte = a;
    }

    public String getType() {
        WebViewDataTypeEnum a;
        return a.float;
    }

    public static WebViewDataTypeEnum valueOf(String a) {
        return Enum.valueOf(WebViewDataTypeEnum.class, a);
    }

    public static WebViewDataTypeEnum getByType(String string) {
        int a;
        String string2 = string;
        if (StringUtils.isBlank((CharSequence)string2)) {
            return null;
        }
        WebViewDataTypeEnum[] webViewDataTypeEnumArray = WebViewDataTypeEnum.values();
        int n = webViewDataTypeEnumArray.length;
        int n2 = a = 0;
        while (n2 < n) {
            WebViewDataTypeEnum webViewDataTypeEnum = webViewDataTypeEnumArray[a];
            if (webViewDataTypeEnum.getType().equals(string2)) {
                return webViewDataTypeEnum;
            }
            n2 = ++a;
        }
        return null;
    }

    static {
        LOG = new WebViewDataTypeEnum(AICodeUtils.H("xmk"), ModuleEnum.LOG);
        UNIT_TEST_GET_UT_INFO = new WebViewDataTypeEnum(AICodeUtils.H("UU\\Z_opEL%VOPuqUPSZDC"), ModuleEnum.UNIT_TEST);
        UNIT_TEST_GET_METHOD_CASE = new WebViewDataTypeEnum(AICodeUtils.H("CelZ_OP]T\u0001rSL@\\OPbkEPYUQI"), ModuleEnum.UNIT_TEST);
        UNIT_TEST_GET_CASE_CODE = new WebViewDataTypeEnum(AICodeUtils.H("p@IOJZEha,_ZEUGkwDPY[FI"), ModuleEnum.UNIT_TEST);
        UNIT_TEST_GET_ALL_CODE_FILE = new WebViewDataTypeEnum(AICodeUtils.H("QV_\u007fzZEHA4G~aIYS]UGe`DP\\]NI"), ModuleEnum.UNIT_TEST);
        UNIT_TEST_REQUEST_UT_INFO = new WebViewDataTypeEnum(AICodeUtils.H("CelZ_OP]T\u0001gSIJTYPuqUPSZDC"), ModuleEnum.UNIT_TEST);
        UNIT_TEST_REQUEST_CASE_CODE = new WebViewDataTypeEnum(AICodeUtils.H("QV_\u007fzZEHA4R~dC]LEUGkwDPY[FI"), ModuleEnum.UNIT_TEST);
        UNIT_TEST_REQUEST_ALL_CODE_FILE = new WebViewDataTypeEnum(AICodeUtils.H("bHA^[LSxq4R^D[EhaIYS]UGe`DP\\]NI"), ModuleEnum.UNIT_TEST);
        UNIT_TEST_REQUEST_METHOD_CASE = new WebViewDataTypeEnum(AICodeUtils.H("]DMLI\u007f`]T!GKQnpEL@\\OPbkEPYUQI"), ModuleEnum.UNIT_TEST);
        UNIT_TEST_COPY_CASE_CODE = new WebViewDataTypeEnum(AICodeUtils.H("~kGTDAKSo\u000fUWOHUGkwDPY[FI"), ModuleEnum.UNIT_TEST);
        UNIT_TEST_PAGE_READY = new WebViewDataTypeEnum(AICodeUtils.H("N[GTdaSKK+ZEma^]_UFU"), ModuleEnum.UNIT_TEST);
        UNIT_TEST_FUNCTION_LIST = new WebViewDataTypeEnum(AICodeUtils.H("p@IOJZEha,^J_IPckOPV]QX"), ModuleEnum.UNIT_TEST);
        UNIT_TEST_FUNCTION_CASE = new WebViewDataTypeEnum(AICodeUtils.H("p@IOJZEha,^J_IPckOPYUQI"), ModuleEnum.UNIT_TEST);
        UNIT_TEST_RECEIVE_FUNCTION_CASE = new WebViewDataTypeEnum(AICodeUtils.H("bHA^[LSxq4R^VKImpI^J_IPckOPYUQI"), ModuleEnum.UNIT_TEST);
        UNIT_TEST_FUNCTION_CASE_CODE = new WebViewDataTypeEnum(AICodeUtils.H("_JQBtqKSO/HUuvBQP_UGkwDPY[FI"), ModuleEnum.UNIT_TEST);
        UNIT_TEST_RECEIVE_FUNCTION_CASE_CODE = new WebViewDataTypeEnum(AICodeUtils.H("_JDWfcC[^>JSh`GV^JHUuvBQP_UGkwDPY[FI"), ModuleEnum.UNIT_TEST);
        UNIT_TEST_SAVE_CODE = new WebViewDataTypeEnum(AICodeUtils.H("@@IojB]LE0WkrDPY[FI"), ModuleEnum.UNIT_TEST);
        UNIT_TEST_REGENERATE = new WebViewDataTypeEnum(AICodeUtils.H("N[GTdaSKK+XAmaOJHUVI"), ModuleEnum.UNIT_TEST);
        UNIT_TESTING_RECEIVE_FUNCTION = new WebViewDataTypeEnum(AICodeUtils.H("]DMLI\u007f`]TR[I:ipU]VGO[lqOLN]MB"), ModuleEnum.UNIT_TESTING);
        UNIT_TESTING_RECEIVE_DATA = new WebViewDataTypeEnum(AICodeUtils.H("]DMLI\u007f`]TR[I:ipU]VGO[~aR[^UVM"), ModuleEnum.UNIT_TESTING);
        UNIT_TESTING_PAGE_READY = new WebViewDataTypeEnum(AICodeUtils.H("p@IOJZEha_VX+ZEma^]_UFU"), ModuleEnum.UNIT_TESTING);
        UNIT_TESTING_SAVE = new WebViewDataTypeEnum(AICodeUtils.H("Uu|BGKTYPcjF5IUTI"), ModuleEnum.UNIT_TESTING);
        UNIT_TESTING_RESPONSE_SAVE = new WebViewDataTypeEnum(AICodeUtils.H("MXbqQT^FZIur,JZBZKdwDPIUTI"), ModuleEnum.UNIT_TESTING);
        UNIT_TESTING_MAPPING_FILE = new WebViewDataTypeEnum(AICodeUtils.H("CelZ_OP]Tr{Q\"RPZTcjFP\\]NI"), ModuleEnum.UNIT_TESTING);
        UNIT_TESTING_IDEA_STOP = new WebViewDataTypeEnum(AICodeUtils.H("[NRAQT~fBQQV0Mna@PI@M\\"), ModuleEnum.UNIT_TESTING);
        UNIT_TESTING_WEB_STOP = new WebViewDataTypeEnum(AICodeUtils.H("UU\\Z_opELV_M>}aCPI@M\\"), ModuleEnum.UNIT_TESTING);
        BATCH_UNIT_TEST_CREATE = new WebViewDataTypeEnum(AICodeUtils.H("LAOVF_n{_L@EOW~\u001eB]_UVI"), ModuleEnum.BATCH_UNIT_TEST);
        BATCH_UNIT_TEST_GET_LIST = new WebViewDataTypeEnum(AICodeUtils.H("idZCSJ[NraILZB^>maUPV]QX"), ModuleEnum.BATCH_UNIT_TEST);
        BATCH_UNIT_TEST_DOWNLOAD = new WebViewDataTypeEnum(AICodeUtils.H("idZCSJ[NraILZB^>nkVAV[CH"), ModuleEnum.BATCH_UNIT_TEST);
        BATCH_UNIT_TEST_DELETE = new WebViewDataTypeEnum(AICodeUtils.H("LAOVF_n{_L@EOW~\u001eEJVQVI"), ModuleEnum.BATCH_UNIT_TEST);
        BATCH_UNIT_TEST_MESSAGE = new WebViewDataTypeEnum(AICodeUtils.H("gOTX]QUu|BGKTYP\u0010iD\\IUEI"), ModuleEnum.BATCH_UNIT_TEST);
        BATCH_UNIT_TEST_REFRESH_TASK_DOWNLOAD_STATUS = new WebViewDataTypeEnum(AICodeUtils.H("@MHQ\t\u0010mxCPRW|dR2XA^DnvF_OT]KdqYOQ]EEn{R[[@W_"), ModuleEnum.BATCH_UNIT_TEST);
        BATCH_UNIT_TEST_GET_TASK_LIST = new WebViewDataTypeEnum(AICodeUtils.H("JKP[^tp@IOJZEha,_ZEUPkwJPV]QX"), ModuleEnum.BATCH_UNIT_TEST);
        CODE_SEARCH_REQUEST_CODESEARCH_CODE_LIST = new WebViewDataTypeEnum(AICodeUtils.H("\u0002\u0000|sUWHBktN2XAICnvZ_XZJEhpWJ\\YUGe`DPV]QX"), ModuleEnum.CODE_SEARCH);
        CODE_SEARCH_REQUEST_CODESEARCH_REPOSITORY_LIST = new WebViewDataTypeEnum(AICodeUtils.H("Y[FICA\u0004\u000ejuB>_FhbC[^[[Yo`]EZGMHdgSHPBCPevXPV]QX"), ModuleEnum.CODE_SEARCH);
        CODE_SEARCH_REQUEST_CODESEARCH_LANGUAGE_LIST = new WebViewDataTypeEnum(AICodeUtils.H("ACXW\u001e\u001c}wXGE9krW]OWLIhjJEHPORx}IT^_MQkcDPV]QX"), ModuleEnum.CODE_SEARCH);
        CODE_SEARCH_REQUEST_COPY_CODE = new WebViewDataTypeEnum(AICodeUtils.H("KE@]Ix`ORX]4R~dC]LEUGetXPY[FI"), ModuleEnum.CODE_SEARCH);
        CODE_SEARCH_REQUEST_INSERT_CODE = new WebViewDataTypeEnum(AICodeUtils.H("tILO[KSjwMH!GKQnpEL@XDWovUPY[FI"), ModuleEnum.CODE_SEARCH);
        CODE_SEARCH_REQUEST_CODE_FILE = new WebViewDataTypeEnum(AICodeUtils.H("KE@]Ix`ORX]4R~dC]LEUGe`DP\\]NI"), ModuleEnum.CODE_SEARCH);
        CODE_SEARCH_REQUEST_OPEN_URL = new WebViewDataTypeEnum(AICodeUtils.H("IK\\StvKAIVF:ipGMZB^[etDAEAP@"), ModuleEnum.CODE_SEARCH);
        CODE_REVIEW_PAGE_READY = new WebViewDataTypeEnum(AICodeUtils.H("MO_PQR~c_]H+ZEma^]_UFU"), ModuleEnum.GIT_VIEW);
        CODE_REVIEW_GET_CHANGE_RESULT = new WebViewDataTypeEnum(AICodeUtils.H("KE@]Iy`XI^B4G~aI[WPDCo{SJIANX"), ModuleEnum.GIT_VIEW);
        CODE_REVIEW_GET_CODEREVIEW_LIST = new WebViewDataTypeEnum(AICodeUtils.H("tILO[JS}lKW!RKTdvY\\ZCORcaVPV]QX"), ModuleEnum.GIT_VIEW);
        CODE_REVIEW_GET_CHANGE_RESULT_END = new WebViewDataTypeEnum(AICodeUtils.H("@vsCWXAN_nr4G^AQCstX_ZNXAyqM[EQLH"), ModuleEnum.GIT_VIEW);
        CHAT_CHOOSE_HISTORY_ITEM = new WebViewDataTypeEnum(AICodeUtils.H("hmOT!VFOtfSGWXYPevXPS@GA"), ModuleEnum.CHAT);
        CHAT_GET_HISTORY_LIST = new WebViewDataTypeEnum(AICodeUtils.H("CSTZ:|pBGWXYPevXPV]QX"), ModuleEnum.CHAT);
        CHAT_DELETE_HISTORY_ITEM = new WebViewDataTypeEnum(AICodeUtils.H("hmOT!QKL~aSGWXYPevXPS@GA"), ModuleEnum.CHAT);
        CHAT_DELETE_HISTORY_ITEM_ALL = new WebViewDataTypeEnum(AICodeUtils.H("ILYB\u0011aKL^AK_s|ELPCS[cpDBEUN@"), ModuleEnum.CHAT);
        CHAT_REFRESH_MODEL = new WebViewDataTypeEnum(AICodeUtils.H("MHza,JZWXAyl^BUPG@"), ModuleEnum.CHAT);
        CHAT_GET_MODEL_LIST = new WebViewDataTypeEnum(AICodeUtils.H("VFAo\u000fQ]KNGKnaMPV]QX"), ModuleEnum.CHAT);
        CHAT_SET_MODEL = new WebViewDataTypeEnum(AICodeUtils.H("UP^E0Wop^BUPG@"), ModuleEnum.CHAT);
        CHAT_GET_CONVERSATION = new WebViewDataTypeEnum(AICodeUtils.H("CSTZ:|pBG\\^DRovRNN]MB"), ModuleEnum.CHAT);
        CHAT_SEND_MSG = new WebViewDataTypeEnum(AICodeUtils.H("[WP^>yaOKEYQK"), ModuleEnum.CHAT);
        CHAT_RESEND = new WebViewDataTypeEnum(AICodeUtils.H("RBE~\u001eSJIQLH"), ModuleEnum.CHAT);
        CHAT_CHOOSE_FILE = new WebViewDataTypeEnum(AICodeUtils.H("x}WL%RBKewDP\\]NI"), ModuleEnum.CHAT);
        CHAT_SEND_OPEN_DIR_LIST = new WebViewDataTypeEnum(AICodeUtils.H("fFAO/]EuqIWOTD[nmSPV]QX"), ModuleEnum.CHAT);
        CHAT_UPDATE_CONVERSATION_LIST = new WebViewDataTypeEnum(AICodeUtils.H("KBEL,~uJAOPQCt{@]MBKPckOPV]QX"), ModuleEnum.CHAT);
        SQL_CHAT_UPDATE_CONVERSATION_LIST = new WebViewDataTypeEnum(AICodeUtils.H("Ph{YKBEL,~uJAOPQCt{@]MBKPckOPV]QX"), ModuleEnum.SQL_CHAT);
        CHAT_RECEIVER_HISTORY_LIST = new WebViewDataTypeEnum(AICodeUtils.H("[^jq4R^VKImpDGWXYPevXPV]QX"), ModuleEnum.CHAT);
        CHAT_DELETE_MSG = new WebViewDataTypeEnum(AICodeUtils.H("v^YK+NAfaUJEYQK"), ModuleEnum.CHAT);
        CHAT_PREDICT = new WebViewDataTypeEnum(AICodeUtils.H("\\YKP\u0010tSJ^]AX"), ModuleEnum.CHAT);
        CHAT_STOP_RESPONSE = new WebViewDataTypeEnum(AICodeUtils.H("MHza,KK^Z[xaR_UZQI"), ModuleEnum.CHAT);
        CHAT_NEW_CHAT = new WebViewDataTypeEnum(AICodeUtils.H("[WP^>daVPY\\CX"), ModuleEnum.CHAT);
        CHAT_GET_USER_INFO = new WebViewDataTypeEnum(AICodeUtils.H("MHza,_ZEUQyaSPSZDC"), ModuleEnum.CHAT);
        CHAT_GET_IDE_FILE_STATE = new WebViewDataTypeEnum(AICodeUtils.H("fFAO/IEoj_\\ZNLMfa^\\NUVI"), ModuleEnum.CHAT);
        CHAT_RECOMMEND_GAMEPLAY = new WebViewDataTypeEnum(AICodeUtils.H("ILYBtqOLP/\\Exz[UZ_N[meLJJXCU"), ModuleEnum.CHAT);
        CHAT_RECEIVER_RECOMMEND_GAMEPLAY = new WebViewDataTypeEnum(AICodeUtils.H("uBEY\\mvJC0V]UnlXEIJ\\Exz[UZ_N[meLJJXCU"), ModuleEnum.CHAT);
        CHAT_GET_DOC_KNOWLEDGE_LIST = new WebViewDataTypeEnum(AICodeUtils.H("GPW\u007f\u001fIEOJJOxj]VPFFAncDPV]QX"), ModuleEnum.CHAT);
        CHAT_FEEDBACK_CATEGORY = new WebViewDataTypeEnum(AICodeUtils.H("MHZA4F~pRZ^RA[ieUJ][PU"), ModuleEnum.CHAT);
        CHAT_GET_FEEDBACK_LIST = new WebViewDataTypeEnum(AICodeUtils.H("MHZA4G~aI^ZTNFkgJPV]QX"), ModuleEnum.CHAT);
        CHAT_RECEIVER_IDE_FILE_STATE = new WebViewDataTypeEnum(AICodeUtils.H("ILYB\u0011wKC^\\XEij_\\ZNLMfa^\\NUVI"), ModuleEnum.CHAT);
        CHAT_RECEIVER_DOC_KNOWLEDGE_LIST = new WebViewDataTypeEnum(AICodeUtils.H("z\u007fG\\0V]UnlXEIJJOxj]VPFFAncDPV]QX"), ModuleEnum.CHAT);
        CHAT_GET_CODE_KNOWLEDGE_LIST = new WebViewDataTypeEnum(AICodeUtils.H("ILYB\u0011bKTDVAD~j]VPFFAncDPV]QX"), ModuleEnum.CHAT);
        CHAT_RECEIVER_CODE_KNOWLEDGE_LIST = new WebViewDataTypeEnum(AICodeUtils.H("@qvR2XA[SbsKRDVAD~j]VPFFAncDPV]QX"), ModuleEnum.CHAT);
        CHAT_VALID_WEBSITE = new WebViewDataTypeEnum(AICodeUtils.H("MHza,N^]C@usDMI]VI"), ModuleEnum.CHAT);
        CHAT_SEND_VALID_WEBSITE_RESULT = new WebViewDataTypeEnum(AICodeUtils.H("E@KP\"EnkJ_MTBI\u007fjA]]BCPo{SJIANX"), ModuleEnum.CHAT);
        GIT_CODE_KNOWLEDGE_REPO_STATUS = new WebViewDataTypeEnum(AICodeUtils.H("AA^>[Yo`QKUZYL~qQ]@COTe{R[[@W_"), ModuleEnum.CHAT);
        GIT_AUTHORIZE = new WebViewDataTypeEnum(AICodeUtils.H("_VE0E\u007fpI@H]XI"), ModuleEnum.CHAT);
        GIT_RE_INDEX = new WebViewDataTypeEnum(AICodeUtils.H("XX^>xa^FTPGT"), ModuleEnum.CHAT);
        GIT_SAVE_TOKEN = new WebViewDataTypeEnum(AICodeUtils.H("QQK+YE|a^[U_GB"), ModuleEnum.CHAT);
        GIT_GET_STATUS = new WebViewDataTypeEnum(AICodeUtils.H("QQK+MA~{R[[@W_"), ModuleEnum.CHAT);
        COMMON_DOWNLOAD_TABLE = new WebViewDataTypeEnum(AICodeUtils.H("CTXCOu\u000fRWH_FKk`^[[VNI"), ModuleEnum.CHAT);
        CHAT_GET_OPEN_DIR_LIST = new WebViewDataTypeEnum(AICodeUtils.H("MHZA4G~aIWOTD[nmSPV]QX"), ModuleEnum.CHAT);
        COMMON_PAGE_READY = new WebViewDataTypeEnum(AICodeUtils.H("Ctx[WQ+ZEma^]_UFU"), ModuleEnum.COMMON);
        COMMON_OPEN_URL = new WebViewDataTypeEnum(AICodeUtils.H("vYUR^D>etDAEAP@"), ModuleEnum.COMMON);
        COMMON_OPEN_PAGE = new WebViewDataTypeEnum(AICodeUtils.H("xz[UP_0KzaOPJUEI"), ModuleEnum.COMMON);
        COMMON_OPEN_FILE_DIALOG = new WebViewDataTypeEnum(AICodeUtils.H("fAMVZ@:teSV@WCHo{EF[XMK"), ModuleEnum.COMMON);
        COMMON_CODE_CLICK_ACTION = new WebViewDataTypeEnum(AICodeUtils.H("hjCMT[4CtqSG\\]CGa{@LN]MB"), ModuleEnum.COMMON);
        COMMON_FOCUS_FILE = new WebViewDataTypeEnum(AICodeUtils.H("Ctx[WQ+LKiqRP\\]NI"), ModuleEnum.COMMON);
        COMMON_FOCUS_FILE_LINE = new WebViewDataTypeEnum(AICodeUtils.H("MOVXAN\u0001sY[JBUBchDPV]LI"), ModuleEnum.COMMON);
        COMMON_EVALUATION = new WebViewDataTypeEnum(AICodeUtils.H("Ctx[WQ+ORkhTNN]MB"), ModuleEnum.COMMON);
        COMMON_FEEDBACK = new WebViewDataTypeEnum(AICodeUtils.H("vYUR^D>laDKXUAG"), ModuleEnum.COMMON);
        SETTING_GET_CONFIG = new WebViewDataTypeEnum(AICodeUtils.H("]Eoa_VX+MA~{B@TRKK"), ModuleEnum.SETTING);
        SETTING_UPDATE_CONFIG = new WebViewDataTypeEnum(AICodeUtils.H("S^AZIur,MOUKPo{B@TRKK"), ModuleEnum.SETTING);
        SETTING_GET_CAN_OPEN_CODE_ENHANCE = new WebViewDataTypeEnum(AICodeUtils.H("P|cRADC\"QnqQCZ[QOkpXG\\^NAuaOG[ZAI"), ModuleEnum.SETTING);
        SETTING_POPUP_KEYMAP_SETTINGS = new WebViewDataTypeEnum(AICodeUtils.H("[OPL_eb4PTE[Pd~SARPZ[yaU[SZE_"), ModuleEnum.SETTING);
        SAVE_SHOW_OPERATE_GUIDANCE = new WebViewDataTypeEnum(AICodeUtils.H("^FmcOFM>KW}`QSSZY_teSJ^EO[mqHK[ZAI"), ModuleEnum.SETTING);
        SETTING_RECEIVE_REPO_STATUS = new WebViewDataTypeEnum(AICodeUtils.H("W]B\u007fl@G!GKC~|@]@COTe{R[[@W_"), ModuleEnum.SETTING);
        COMMON_PLUGIN_BASE_INFO = new WebViewDataTypeEnum(AICodeUtils.H("fAMVZ@:kyC_V_UFkwDPSZDC"), ModuleEnum.SETTING);
        LOGIN_INIT = new WebViewDataTypeEnum(AICodeUtils.H("FKmmO5SZKX"), ModuleEnum.LOGIN);
        LOGIN_LOGOUT = new WebViewDataTypeEnum(AICodeUtils.H("S^MMd\u001eM@][WX"), ModuleEnum.LOGIN);
        LOGIN_RECEIVER_LOGIN_IFRAME_SRC = new WebViewDataTypeEnum(AICodeUtils.H("{IOCJ\"DnfKIMP\\_wzQQQNCBxeLJEGPO"), ModuleEnum.LOGIN);
        LOGIN_LOGIN_SUCCEED = new WebViewDataTypeEnum(AICodeUtils.H("YAGr{,TPVCJuwTLYQGH"), ModuleEnum.LOGIN);
        LOGIN_GO_LOGIN = new WebViewDataTypeEnum(AICodeUtils.H("ZWXXD>mk^CUSKB"), ModuleEnum.LOGIN);
        LOGIN_SHOW_FRESH = new WebViewDataTypeEnum(AICodeUtils.H("x}WL%PMAdp^JHFM^"), ModuleEnum.CHAT);
        CHAT_AGENT_REFRESH = new WebViewDataTypeEnum(AICodeUtils.H("MHza,YXTDPuvDIHQQD"), ModuleEnum.CHAT);
        COMMON_SHOW_MESSAGE_IN_WEB = new WebViewDataTypeEnum(AICodeUtils.H("[YfhAN!FFOlj[]LBKCo{HAECGN"), ModuleEnum.LOGIN);
        LOGIN_CLOSE_QR_CODE = new WebViewDataTypeEnum(AICodeUtils.H("YAGr{,[S^YAuuSPY[FI"), ModuleEnum.LOGIN);
        SQL_CHAT_SOURCE_LIST = new WebViewDataTypeEnum(AICodeUtils.H("HDB_x}WL%BEQxgDPV]QX"), ModuleEnum.SQL_CHAT);
        SQL_CHAT_REQUEST_SOURCE_TYPES = new WebViewDataTypeEnum(AICodeUtils.H("[[HGUcdZ:IP_U~fBGL^_Via^[CDG_"), ModuleEnum.SQL_CHAT);
        SQL_CHAT_SQL_LINK_TEST = new WebViewDataTypeEnum(AICodeUtils.H("]QWJMHza,KN]UHcjJPNQQX"), ModuleEnum.SQL_CHAT);
        SQL_CHAT_SQL_SAVE = new WebViewDataTypeEnum(AICodeUtils.H("SjyI[WP^>yuMPIUTI"), ModuleEnum.SQL_CHAT);
        SQL_CHAT_SOURCE_DELETE = new WebViewDataTypeEnum(AICodeUtils.H("]QWJMHza,KPDXGo{EJVQVI"), ModuleEnum.SQL_CHAT);
        SQL_CHAT_TABLE_LIST = new WebViewDataTypeEnum(AICodeUtils.H("F_Ldv^YK+^EhhDPV]QX"), ModuleEnum.SQL_CHAT);
        SQL_CHAT_SEND_MSG = new WebViewDataTypeEnum(AICodeUtils.H("SjyI[WP^>yaOKEYQK"), ModuleEnum.SQL_CHAT);
        SQL_CHAT_GET_MODEL_LIST = new WebViewDataTypeEnum(AICodeUtils.H("v_LDVFAo\u000fQ]KNGKnaMPV]QX"), ModuleEnum.SQL_CHAT);
        SQL_CHAT_NEW_CHAT = new WebViewDataTypeEnum(AICodeUtils.H("SjyI[WP^>daVPY\\CX"), ModuleEnum.SQL_CHAT);
        SQL_CHAT_STOP_RESPONSE = new WebViewDataTypeEnum(AICodeUtils.H("]QWJMHza,KK^Z[xaR_UZQI"), ModuleEnum.SQL_CHAT);
        CODE_CHECK_REQUEST_CODE_CHECK_LIST = new WebViewDataTypeEnum(AICodeUtils.H("NL}rYKBA[]\u0011wKQNP]TdvY\\ZNILogJPV]QX"), ModuleEnum.CODE_CHECK);
        CODE_CHECK_GET_CODE_CHECK_LIST = new WebViewDataTypeEnum(AICodeUtils.H("EGNAGUc`MK!RKTdvY\\ZNILogJPV]QX"), ModuleEnum.CODE_CHECK);
        CODE_CHECK_FIX = new WebViewDataTypeEnum(AICodeUtils.H("UW[TUGbaBD RKT"), ModuleEnum.CODE_CHECK);
        CODE_CHECK_UPDATE_CODE_CHECK = new WebViewDataTypeEnum(AICodeUtils.H("IK\\StfFEX^4UkqWLZNIKna^LRQAG"), ModuleEnum.CODE_CHECK);
        LOGIN_LOGIN_ABORT = new WebViewDataTypeEnum(AICodeUtils.H("Ltr_V%]ECcj^NX[PX"), ModuleEnum.LOGIN);
        LOGIN_LOGIN_CHECK = new WebViewDataTypeEnum(AICodeUtils.H("Ltr_V%]ECcj^LRQAG"), ModuleEnum.LOGIN);
        WebViewDataTypeEnum[] webViewDataTypeEnumArray = new WebViewDataTypeEnum[124];
        webViewDataTypeEnumArray[0] = LOG;
        webViewDataTypeEnumArray[1] = UNIT_TEST_GET_UT_INFO;
        webViewDataTypeEnumArray[2] = UNIT_TEST_GET_METHOD_CASE;
        webViewDataTypeEnumArray[3] = UNIT_TEST_GET_CASE_CODE;
        webViewDataTypeEnumArray[4] = UNIT_TEST_GET_ALL_CODE_FILE;
        webViewDataTypeEnumArray[5] = UNIT_TEST_REQUEST_UT_INFO;
        webViewDataTypeEnumArray[6] = UNIT_TEST_REQUEST_CASE_CODE;
        webViewDataTypeEnumArray[7] = UNIT_TEST_REQUEST_ALL_CODE_FILE;
        webViewDataTypeEnumArray[8] = UNIT_TEST_REQUEST_METHOD_CASE;
        webViewDataTypeEnumArray[9] = UNIT_TEST_COPY_CASE_CODE;
        webViewDataTypeEnumArray[10] = UNIT_TEST_PAGE_READY;
        webViewDataTypeEnumArray[11] = UNIT_TEST_FUNCTION_LIST;
        webViewDataTypeEnumArray[12] = UNIT_TEST_FUNCTION_CASE;
        webViewDataTypeEnumArray[13] = UNIT_TEST_RECEIVE_FUNCTION_CASE;
        webViewDataTypeEnumArray[14] = UNIT_TEST_FUNCTION_CASE_CODE;
        webViewDataTypeEnumArray[15] = UNIT_TEST_RECEIVE_FUNCTION_CASE_CODE;
        webViewDataTypeEnumArray[16] = UNIT_TEST_SAVE_CODE;
        webViewDataTypeEnumArray[17] = UNIT_TEST_REGENERATE;
        webViewDataTypeEnumArray[18] = UNIT_TESTING_RECEIVE_FUNCTION;
        webViewDataTypeEnumArray[19] = UNIT_TESTING_RECEIVE_DATA;
        webViewDataTypeEnumArray[20] = UNIT_TESTING_PAGE_READY;
        webViewDataTypeEnumArray[21] = UNIT_TESTING_SAVE;
        webViewDataTypeEnumArray[22] = UNIT_TESTING_RESPONSE_SAVE;
        webViewDataTypeEnumArray[23] = UNIT_TESTING_MAPPING_FILE;
        webViewDataTypeEnumArray[24] = UNIT_TESTING_IDEA_STOP;
        webViewDataTypeEnumArray[25] = UNIT_TESTING_WEB_STOP;
        webViewDataTypeEnumArray[26] = BATCH_UNIT_TEST_CREATE;
        webViewDataTypeEnumArray[27] = BATCH_UNIT_TEST_GET_LIST;
        webViewDataTypeEnumArray[28] = BATCH_UNIT_TEST_DOWNLOAD;
        webViewDataTypeEnumArray[29] = BATCH_UNIT_TEST_DELETE;
        webViewDataTypeEnumArray[30] = BATCH_UNIT_TEST_MESSAGE;
        webViewDataTypeEnumArray[31] = BATCH_UNIT_TEST_REFRESH_TASK_DOWNLOAD_STATUS;
        webViewDataTypeEnumArray[32] = BATCH_UNIT_TEST_GET_TASK_LIST;
        webViewDataTypeEnumArray[33] = CODE_SEARCH_REQUEST_CODESEARCH_CODE_LIST;
        webViewDataTypeEnumArray[34] = CODE_SEARCH_REQUEST_CODESEARCH_REPOSITORY_LIST;
        webViewDataTypeEnumArray[35] = CODE_SEARCH_REQUEST_CODESEARCH_LANGUAGE_LIST;
        webViewDataTypeEnumArray[36] = CODE_SEARCH_REQUEST_COPY_CODE;
        webViewDataTypeEnumArray[37] = CODE_SEARCH_REQUEST_INSERT_CODE;
        webViewDataTypeEnumArray[38] = CODE_SEARCH_REQUEST_CODE_FILE;
        webViewDataTypeEnumArray[39] = CODE_SEARCH_REQUEST_OPEN_URL;
        webViewDataTypeEnumArray[40] = CODE_REVIEW_PAGE_READY;
        webViewDataTypeEnumArray[41] = CODE_REVIEW_GET_CHANGE_RESULT;
        webViewDataTypeEnumArray[42] = CODE_REVIEW_GET_CODEREVIEW_LIST;
        webViewDataTypeEnumArray[43] = CODE_REVIEW_GET_CHANGE_RESULT_END;
        webViewDataTypeEnumArray[44] = CHAT_CHOOSE_HISTORY_ITEM;
        webViewDataTypeEnumArray[45] = CHAT_GET_HISTORY_LIST;
        webViewDataTypeEnumArray[46] = CHAT_DELETE_HISTORY_ITEM;
        webViewDataTypeEnumArray[47] = CHAT_DELETE_HISTORY_ITEM_ALL;
        webViewDataTypeEnumArray[48] = CHAT_REFRESH_MODEL;
        webViewDataTypeEnumArray[49] = CHAT_GET_MODEL_LIST;
        webViewDataTypeEnumArray[50] = CHAT_SET_MODEL;
        webViewDataTypeEnumArray[51] = CHAT_GET_CONVERSATION;
        webViewDataTypeEnumArray[52] = CHAT_SEND_MSG;
        webViewDataTypeEnumArray[53] = CHAT_RESEND;
        webViewDataTypeEnumArray[54] = CHAT_CHOOSE_FILE;
        webViewDataTypeEnumArray[55] = CHAT_SEND_OPEN_DIR_LIST;
        webViewDataTypeEnumArray[56] = CHAT_UPDATE_CONVERSATION_LIST;
        webViewDataTypeEnumArray[57] = SQL_CHAT_UPDATE_CONVERSATION_LIST;
        webViewDataTypeEnumArray[58] = CHAT_RECEIVER_HISTORY_LIST;
        webViewDataTypeEnumArray[59] = CHAT_DELETE_MSG;
        webViewDataTypeEnumArray[60] = CHAT_PREDICT;
        webViewDataTypeEnumArray[61] = CHAT_STOP_RESPONSE;
        webViewDataTypeEnumArray[62] = CHAT_NEW_CHAT;
        webViewDataTypeEnumArray[63] = CHAT_GET_USER_INFO;
        webViewDataTypeEnumArray[64] = CHAT_GET_IDE_FILE_STATE;
        webViewDataTypeEnumArray[65] = CHAT_RECOMMEND_GAMEPLAY;
        webViewDataTypeEnumArray[66] = CHAT_RECEIVER_RECOMMEND_GAMEPLAY;
        webViewDataTypeEnumArray[67] = CHAT_GET_DOC_KNOWLEDGE_LIST;
        webViewDataTypeEnumArray[68] = CHAT_FEEDBACK_CATEGORY;
        webViewDataTypeEnumArray[69] = CHAT_GET_FEEDBACK_LIST;
        webViewDataTypeEnumArray[70] = CHAT_RECEIVER_IDE_FILE_STATE;
        webViewDataTypeEnumArray[71] = CHAT_RECEIVER_DOC_KNOWLEDGE_LIST;
        webViewDataTypeEnumArray[72] = CHAT_GET_CODE_KNOWLEDGE_LIST;
        webViewDataTypeEnumArray[73] = CHAT_RECEIVER_CODE_KNOWLEDGE_LIST;
        webViewDataTypeEnumArray[74] = CHAT_VALID_WEBSITE;
        webViewDataTypeEnumArray[75] = CHAT_SEND_VALID_WEBSITE_RESULT;
        webViewDataTypeEnumArray[76] = GIT_CODE_KNOWLEDGE_REPO_STATUS;
        webViewDataTypeEnumArray[77] = GIT_AUTHORIZE;
        webViewDataTypeEnumArray[78] = GIT_RE_INDEX;
        webViewDataTypeEnumArray[79] = GIT_SAVE_TOKEN;
        webViewDataTypeEnumArray[80] = GIT_GET_STATUS;
        webViewDataTypeEnumArray[81] = COMMON_DOWNLOAD_TABLE;
        webViewDataTypeEnumArray[82] = CHAT_GET_OPEN_DIR_LIST;
        webViewDataTypeEnumArray[83] = COMMON_PAGE_READY;
        webViewDataTypeEnumArray[84] = COMMON_OPEN_URL;
        webViewDataTypeEnumArray[85] = COMMON_OPEN_PAGE;
        webViewDataTypeEnumArray[86] = COMMON_OPEN_FILE_DIALOG;
        webViewDataTypeEnumArray[87] = COMMON_CODE_CLICK_ACTION;
        webViewDataTypeEnumArray[88] = COMMON_FOCUS_FILE;
        webViewDataTypeEnumArray[89] = COMMON_FOCUS_FILE_LINE;
        webViewDataTypeEnumArray[90] = COMMON_EVALUATION;
        webViewDataTypeEnumArray[91] = COMMON_FEEDBACK;
        webViewDataTypeEnumArray[92] = SETTING_GET_CONFIG;
        webViewDataTypeEnumArray[93] = SETTING_UPDATE_CONFIG;
        webViewDataTypeEnumArray[94] = SETTING_GET_CAN_OPEN_CODE_ENHANCE;
        webViewDataTypeEnumArray[95] = SETTING_POPUP_KEYMAP_SETTINGS;
        webViewDataTypeEnumArray[96] = SAVE_SHOW_OPERATE_GUIDANCE;
        webViewDataTypeEnumArray[97] = SETTING_RECEIVE_REPO_STATUS;
        webViewDataTypeEnumArray[98] = COMMON_PLUGIN_BASE_INFO;
        webViewDataTypeEnumArray[99] = LOGIN_INIT;
        webViewDataTypeEnumArray[100] = LOGIN_LOGOUT;
        webViewDataTypeEnumArray[101] = LOGIN_RECEIVER_LOGIN_IFRAME_SRC;
        webViewDataTypeEnumArray[102] = LOGIN_LOGIN_SUCCEED;
        webViewDataTypeEnumArray[103] = LOGIN_GO_LOGIN;
        webViewDataTypeEnumArray[104] = LOGIN_SHOW_FRESH;
        webViewDataTypeEnumArray[105] = CHAT_AGENT_REFRESH;
        webViewDataTypeEnumArray[106] = COMMON_SHOW_MESSAGE_IN_WEB;
        webViewDataTypeEnumArray[107] = LOGIN_CLOSE_QR_CODE;
        webViewDataTypeEnumArray[108] = SQL_CHAT_SOURCE_LIST;
        webViewDataTypeEnumArray[109] = SQL_CHAT_REQUEST_SOURCE_TYPES;
        webViewDataTypeEnumArray[110] = SQL_CHAT_SQL_LINK_TEST;
        webViewDataTypeEnumArray[111] = SQL_CHAT_SQL_SAVE;
        webViewDataTypeEnumArray[112] = SQL_CHAT_SOURCE_DELETE;
        webViewDataTypeEnumArray[113] = SQL_CHAT_TABLE_LIST;
        webViewDataTypeEnumArray[114] = SQL_CHAT_SEND_MSG;
        webViewDataTypeEnumArray[115] = SQL_CHAT_GET_MODEL_LIST;
        webViewDataTypeEnumArray[116] = SQL_CHAT_NEW_CHAT;
        webViewDataTypeEnumArray[117] = SQL_CHAT_STOP_RESPONSE;
        webViewDataTypeEnumArray[118] = CODE_CHECK_REQUEST_CODE_CHECK_LIST;
        webViewDataTypeEnumArray[119] = CODE_CHECK_GET_CODE_CHECK_LIST;
        webViewDataTypeEnumArray[120] = CODE_CHECK_FIX;
        webViewDataTypeEnumArray[121] = CODE_CHECK_UPDATE_CODE_CHECK;
        webViewDataTypeEnumArray[122] = LOGIN_LOGIN_ABORT;
        webViewDataTypeEnumArray[123] = LOGIN_LOGIN_CHECK;
        enum = webViewDataTypeEnumArray;
    }

    public static WebViewDataTypeEnum[] values() {
        return (WebViewDataTypeEnum[])enum.clone();
    }

    public ModuleEnum getModule() {
        WebViewDataTypeEnum a;
        return a.byte;
    }
}
