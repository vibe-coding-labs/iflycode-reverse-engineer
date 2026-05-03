/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package com.aicode.agent.enums;

import com.aicode.agent.enums.AgentModuleEnum;
import com.aicode.inline.ide.IdeAction;
import com.aicode.message.BasicActionsBundle;
import com.aicode.util.AICodeStringUtil;
import javax.swing.Icon;
import org.apache.commons.lang3.StringUtils;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class CommandEnum
extends Enum<CommandEnum> {
    public static final /* enum */ CommandEnum CODE_TEST_MAKE_CASE_JAVA;
    public static final /* enum */ CommandEnum LOG_ACCEPT_LINE;
    public static final /* enum */ CommandEnum TALK_INTELLIGENT;
    public static final /* enum */ CommandEnum SQL_TEST_CONNECT;
    public static final /* enum */ CommandEnum GIT_SEARCH;
    public static final /* enum */ CommandEnum GENERAL_SETTING;
    public static final /* enum */ CommandEnum DIALOG_REJECT;
    public static final /* enum */ CommandEnum INLINECHAT_CATEGORY;
    private AgentModuleEnum case;
    public static final /* enum */ CommandEnum RAG_LANGUAGES;
    public static final /* enum */ CommandEnum ACTION_ABORT;
    public static final /* enum */ CommandEnum TALK_RESEND;
    public static final /* enum */ CommandEnum GIT_LANG_LIST;
    public static final /* enum */ CommandEnum TALK_HISTORY;
    public static final /* enum */ CommandEnum SQL_GENERATE_TALK;
    public static final /* enum */ CommandEnum LOG_TIP_SETTING;
    public static final /* enum */ CommandEnum LOG_ACCEPT;
    public static final /* enum */ CommandEnum CODE_BATCH_UNIT_TEST_CREATE;
    public static final /* enum */ CommandEnum GIT_CODE_KNOWLEDGE_RE_INDEX;
    public static final /* enum */ CommandEnum LOG_TEST_COLLECTION_GENERATE;
    public static final /* enum */ CommandEnum CODE_FAULT_ANALYSIS;
    public static final /* enum */ CommandEnum REPO_STATUS;
    public static final /* enum */ CommandEnum GIT_REPOSITORY_STATUS;
    public static final /* enum */ CommandEnum CODE_TEST_SAVE;
    public static final /* enum */ CommandEnum USER_PERMISSION;
    public static final /* enum */ CommandEnum LOG_IMITATIVE_WRITE;
    public static final /* enum */ CommandEnum CODE_COMMENT;
    public static final /* enum */ CommandEnum LOG_FEEDBACK;
    public static final /* enum */ CommandEnum USER_LOGOUT;
    public static final /* enum */ CommandEnum GIT_USER_REPOS;
    public static final /* enum */ CommandEnum CODE_DEBUG_DUPLICATE;
    public static final /* enum */ CommandEnum GIT_REVIEW;
    public static final /* enum */ CommandEnum SQL_SOURCE_TYPES;
    public static final /* enum */ CommandEnum LOG_REJECT;
    private String final;
    public static final /* enum */ CommandEnum CODE_EXPLAIN;
    public static final /* enum */ CommandEnum USER_PARSE_WEB_URL;
    public static final /* enum */ CommandEnum TALK_RECOMMEND_GAMEPLAY;
    public static final /* enum */ CommandEnum DIALOG_DIFF;
    public static final /* enum */ CommandEnum CODE_CHECK;
    public static final /* enum */ CommandEnum GIT_DIFF;
    public static final /* enum */ CommandEnum USER_KNOWLEDGE_LIST;
    public static final /* enum */ CommandEnum CODE_GENERATE_TEST_CASE;
    public static final /* enum */ CommandEnum GIT_COMMIT_MESSAGE;
    public static final /* enum */ CommandEnum CODE_OPTIMIZE;
    private final String try;
    public static final /* enum */ CommandEnum MODEL_LIST_TIMER;
    public static final /* enum */ CommandEnum TALK_CLEAR;
    public static final /* enum */ CommandEnum CODE_INLINE_COMMENT;
    public static final /* enum */ CommandEnum UPDATE;
    public static final /* enum */ CommandEnum ACTION_OPEN_DOCUMENT;
    public static final /* enum */ CommandEnum ACTION_SYNC_DOCUMENT_LIST;
    public static final /* enum */ CommandEnum DIALOG_EDIT;
    public static final /* enum */ CommandEnum CODE_TEST_ANALYSIS;
    public static final /* enum */ CommandEnum CODE_TEST_CODE;
    public static final /* enum */ CommandEnum LOG_ACCEPT_COUNT;
    public static final /* enum */ CommandEnum INIT;
    public static final /* enum */ CommandEnum USER_VERSION;
    public static final /* enum */ CommandEnum CODE_BATCH_UNIT_TEST_DELETE;
    public static final /* enum */ CommandEnum USER_LOGIN;
    public static final /* enum */ CommandEnum SQL_OPTIMIZE;
    public static final /* enum */ CommandEnum LOGIN_INFO;
    public static final /* enum */ CommandEnum LOG_EVALUATION;
    public static final /* enum */ CommandEnum USER_FEEDBACK_CATEGORY;
    public static final /* enum */ CommandEnum SERVER_RESOURCE;
    public static final /* enum */ CommandEnum CODE_TEST;
    public static final /* enum */ CommandEnum SQL_SOURCE_LIST;
    private static final /* synthetic */ CommandEnum[] float;
    public static final /* enum */ CommandEnum SQL_SOURCE_EDIT;
    public static final /* enum */ CommandEnum CODE_DEMAND_ANALYSIS;
    public static final /* enum */ CommandEnum CODE_BATCH_UNIT_TEST_DOWNLOAD;
    public static final /* enum */ CommandEnum CODE_DEBUG;
    public static final /* enum */ CommandEnum LOG_OPERATE;
    public static final /* enum */ CommandEnum CODE_HELP;
    public static final /* enum */ CommandEnum ACTION_INIT;
    public static final /* enum */ CommandEnum CODE_DEMAND_TEST;
    public static final /* enum */ CommandEnum LOG_ACCEPT_WORD;
    public static final /* enum */ CommandEnum INLINECHAT_DIRECT;
    public static final /* enum */ CommandEnum LOG_DISPLAY;
    public static final /* enum */ CommandEnum DIALOG_ABORT;
    public static final /* enum */ CommandEnum INLINECHAT_GET_FUNC_RANGE;
    public static final /* enum */ CommandEnum TALK_DOWNLOAD_MARKDOWN_TABLE;
    public static final /* enum */ CommandEnum SQL_TABLE_LIST;
    public static final /* enum */ CommandEnum CODE_DEMAND_SPLITTING;
    public static final /* enum */ CommandEnum SQL_SOURCE_DELETE;
    public static final /* enum */ CommandEnum TALK_ASK;
    public static final /* enum */ CommandEnum ERROR;
    private String byte;
    public static final /* enum */ CommandEnum TEST_MAKE_CODE;
    public static final /* enum */ CommandEnum CODE_COMPLETE;
    public static final /* enum */ CommandEnum TALK_PREDICT;
    public static final /* enum */ CommandEnum DIALOG_ACCEPT;
    public static final /* enum */ CommandEnum CODE_TEST_CASE;
    public static final /* enum */ CommandEnum CODE_BATCH_UNIT_TEST_LIST;
    public static final /* enum */ CommandEnum GIT_SAVE_TOKEN;
    public static final /* enum */ CommandEnum USER_MODEL_LIST;
    public static final /* enum */ CommandEnum GIT_CODE_KNOWLEDGE_REPO_STATUS;
    public static final /* enum */ CommandEnum TALK_DELETE;
    public static final /* enum */ CommandEnum CODE_BATCH_UNIT_TEST_CANCEL;
    public static final /* enum */ CommandEnum USER_LOGIN_CHECK;
    public static final /* enum */ CommandEnum FEEDBACK_CATEGORY_INFO;
    public static final /* enum */ CommandEnum CODE_SPLIT;
    public static final /* enum */ CommandEnum USER_LOGIN_ABORT;
    public static final /* enum */ CommandEnum SQL_OPTIMIZE_TALK;
    public static final /* enum */ CommandEnum TALK_LIST;
    public static final /* enum */ CommandEnum CODE_TEST_TEMPLATE;
    public static final /* enum */ CommandEnum SQL_GENERATE;
    public static final /* enum */ CommandEnum GIT_REPO_AUTHORIZE;
    public static final /* enum */ CommandEnum LOG_TEST_COLLECTION_COMMIT;
    private Icon enum;
    public static final /* enum */ CommandEnum CODE_COMMENT_RANGE;
    public static final /* enum */ CommandEnum TALK_KNOWLEDGE;
    public static final /* enum */ CommandEnum USER_CAN_CODE_ENHANCE;
    public static final /* enum */ CommandEnum LOG_REJECT_ESC;
    public static final /* enum */ CommandEnum TEST_MAKE_CASE;

    /*
     * WARNING - void declaration
     */
    private CommandEnum(String string2, AgentModuleEnum agentModuleEnum) {
        Enum a;
        void a2;
        CommandEnum a3;
        CommandEnum commandEnum = enum_;
        Enum enum_ = agentModuleEnum;
        CommandEnum commandEnum2 = a3 = commandEnum;
        commandEnum2.try = a2;
        commandEnum2.case = a;
    }

    static {
        INIT = new CommandEnum(AICodeStringUtil.H("TXLZ"), AgentModuleEnum.INIT);
        ACTION_INIT = new CommandEnum(AICodeStringUtil.H("losDIh\u0017txlz"), AgentModuleEnum.INIT);
        GIT_CODE_KNOWLEDGE_REPO_STATUS = new CommandEnum(AICodeStringUtil.H("z\u007fq\u0014x\u007f\"(TKicrb`jngy\u007fiwBYuy|bp}"), AgentModuleEnum.INIT);
        GIT_REPO_AUTHORIZE = new CommandEnum(AICodeStringUtil.H("bgq\u0014{gvbsfXRnbo\u007f\u007fk"), AgentModuleEnum.INIT);
        GIT_CODE_KNOWLEDGE_RE_INDEX = new CommandEnum(AICodeStringUtil.H("ird\\.DDbsn`jyegbjix_Cydsr`v"), AgentModuleEnum.INIT);
        GIT_SAVE_TOKEN = new CommandEnum(AICodeStringUtil.H("nkr\u0017\u007ff[Cyyr}``"), AgentModuleEnum.INIT);
        UPDATE = new CommandEnum(AICodeStringUtil.H("X\\ClrC\u0000TXCA"), AgentModuleEnum.INIT);
        ERROR = new CommandEnum(AICodeStringUtil.H("IU\u007fiT\u0000TXCA"), AgentModuleEnum.INIT);
        GENERAL_SETTING = new CommandEnum(AICodeStringUtil.H("ILLC_MK uCYI_KI"), AgentModuleEnum.INIT);
        REPO_STATUS = new CommandEnum(AICodeStringUtil.H("_IWb+UY\\BP]"), AgentModuleEnum.INIT);
        RAG_LANGUAGES = new CommandEnum(AICodeStringUtil.H("PGJ\u0001KlhAX\\Q@]"), AgentModuleEnum.INIT);
        USER_VERSION = new CommandEnum(AICodeStringUtil.H("s~iu7Pc\u007fn\u007fj`"), AgentModuleEnum.LOGIN);
        USER_LOGIN = new CommandEnum(AICodeStringUtil.H("ytHT\u001carql`"), AgentModuleEnum.LOGIN);
        USER_LOGOUT = new CommandEnum(AICodeStringUtil.H("x\u007fb_<jbzypz"), AgentModuleEnum.LOGIN);
        USER_LOGIN_ABORT = new CommandEnum(AICodeStringUtil.H("p}lp\u001cac`DHyl\u007fywz"), AgentModuleEnum.LOGIN);
        USER_LOGIN_CHECK = new CommandEnum(AICodeStringUtil.H("p}lp\u001cac`DHynusfe"), AgentModuleEnum.LOGIN);
        USER_MODEL_LIST = new CommandEnum(AICodeStringUtil.H("{zgt\u0017ahICjrq\u007fvz"), AgentModuleEnum.LOGIN);
        USER_PERMISSION = new CommandEnum(AICodeStringUtil.H("{zgt\u0017|b_Ko~n\u007fj`"), AgentModuleEnum.LOGIN);
        MODEL_LIST_TIMER = new CommandEnum(AICodeStringUtil.H("AHicJ\u0000Q_VZ"), AgentModuleEnum.LOGIN);
        LOGIN_INFO = new CommandEnum(AICodeStringUtil.H("@HjoH\u0000TXCA"), AgentModuleEnum.LOGIN);
        ACTION_SYNC_DOCUMENT_LIST = new CommandEnum(AICodeStringUtil.H("q%9BOi\u0016vwkmvfinyjHHrrq\u007fvz"), AgentModuleEnum.CODE_COMPLETE);
        CODE_COMPLETE = new CommandEnum(AICodeStringUtil.H("aiii\u001dNIk}qsqk"), IdeAction.H("\u4e9c\u7835\u8802\u5144"), AgentModuleEnum.CODE_COMPLETE, AICodeStringUtil.H("oOlrkBYCIK"));
        LOG_IMITATIVE_WRITE = new CommandEnum(AICodeStringUtil.H("`ji\u001fgdkrlxn[Cyzo\u007fqk"), IdeAction.H("\u4e9c\u7835\u4e98\u51b5"), AgentModuleEnum.CODE_COMPLETE);
        LOG_ACCEPT = new CommandEnum(IdeAction.H("\"j\b>%l<q7x"), AgentModuleEnum.CODE_COMPLETE);
        LOG_ACCEPT_WORD = new CommandEnum(IdeAction.H("`$g^n-f\nT0p({5h"), AgentModuleEnum.CODE_COMPLETE);
        LOG_ACCEPT_LINE = new CommandEnum(IdeAction.H("`$g^n-f\nT0p3})i"), AgentModuleEnum.CODE_COMPLETE);
        USER_CAN_CODE_ENHANCE = new CommandEnum(IdeAction.H("W6k5\u0016$m%\u007f'`*`\u0010A*g>z$i"), AgentModuleEnum.CODE_COMPLETE);
        LOG_REJECT = new CommandEnum(IdeAction.H("\"j\b>6j5q$x"), AgentModuleEnum.CODE_COMPLETE);
        LOG_REJECT_ESC = new CommandEnum(IdeAction.H("'o#\u0015<`\u0005A'{ q4o"), AgentModuleEnum.CODE_COMPLETE);
        LOG_DISPLAY = new CommandEnum(IdeAction.H("c!bu@-|/x&u"), AgentModuleEnum.CODE_COMPLETE);
        SQL_SOURCE_LIST = new CommandEnum(IdeAction.H("\u007f:l^|!p\u001dG!p3}4x"), AgentModuleEnum.SQL_CHAT);
        SQL_SOURCE_TYPES = new CommandEnum(IdeAction.H("4}'\u001a7`;w\fA;{&d\"\u007f"), AgentModuleEnum.SQL_CHAT);
        SQL_TEST_CONNECT = new CommandEnum(IdeAction.H("4}'\u001a0j=q\u0010G+a1q$x"), AgentModuleEnum.SQL_CHAT);
        SQL_SOURCE_EDIT = new CommandEnum(IdeAction.H("\u007f:l^|!p\u001dG!p:p.x"), AgentModuleEnum.SQL_CHAT);
        SQL_SOURCE_DELETE = new CommandEnum(IdeAction.H("\u007f6`Qs+z<f\n[ j3q3i"), AgentModuleEnum.SQL_CHAT);
        SQL_TABLE_LIST = new CommandEnum(IdeAction.H("8q(\u0015:d\rH!p3}4x"), AgentModuleEnum.SQL_CHAT);
        SQL_GENERATE = new CommandEnum(IdeAction.H("7~\"\u001f\bA*j-u3i"), AgentModuleEnum.SQL_CHAT, AICodeStringUtil.H("mMO]qwakBccTLI_J@"));
        SQL_OPTIMIZE = new CommandEnum(AICodeStringUtil.H("u|`\u001dBVrdp\u007f\u007fk"), AgentModuleEnum.SQL_CHAT, IdeAction.H("m\u000fM\u0013\u007f:l+_\u001aL\"m\u001eN\u000b]\bB"));
        TALK_HISTORY = new CommandEnum(IdeAction.H("0n\"nuL-|+{5u"), AgentModuleEnum.CHAT);
        TALK_LIST = new CommandEnum(IdeAction.H("q\u000eH/\u00153}4x"), AgentModuleEnum.CHAT);
        TALK_DELETE = new CommandEnum(IdeAction.H("{/i\u0004> j3q3i"), AgentModuleEnum.CHAT);
        TALK_CLEAR = new CommandEnum(IdeAction.H(":d\u0003O^l3q&~"), AgentModuleEnum.CHAT);
        TALK_ASK = new CommandEnum(IdeAction.H("\u001bE(dEu4g"), AgentModuleEnum.CHAT, AICodeStringUtil.H("zHNMdBShjJDZSKZ"));
        TALK_INTELLIGENT = new CommandEnum(AICodeStringUtil.H("qoei\u001cdbsHJjdzskz"), AgentModuleEnum.CHAT, IdeAction.H("x\nL\u000ff\u0000Q*h\bF\u0018Q\tX"));
        TALK_RESEND = new CommandEnum(IdeAction.H("{/i\u0004>6j,q)h"), AgentModuleEnum.CHAT);
        TALK_KNOWLEDGE = new CommandEnum(IdeAction.H("?a(dTn\u0001K3c:p i"), AICodeStringUtil.H("\u77c8\u8bfb\u5ea5\u95cb\u7b7a"), AgentModuleEnum.CHAT);
        CODE_DEMAND_TEST = new CommandEnum(AICodeStringUtil.H("famg\u001ciijLHbrisvz"), IdeAction.H("\u977f\u6c76\u6d2c\u8bf9"), AgentModuleEnum.CHAT, AICodeStringUtil.H("hB`gHIiSVZ"));
        CODE_GENERATE_TEST_CASE = new CommandEnum(AICodeStringUtil.H(".DDb\u0016bkkk{crhssHUrr~wvk"), IdeAction.H("\u757b\u623f\u6d34\u8be1\u754f\u4fa7"), AgentModuleEnum.CHAT, AICodeStringUtil.H("bKGGTLXBYcUY~WVK"));
        CODE_DEMAND_ANALYSIS = new CommandEnum(AICodeStringUtil.H("dcak\u001fjlogchxLHgadel}"), IdeAction.H("\u977f\u6c76\u5261\u67bc"), AgentModuleEnum.CHAT, AICodeStringUtil.H("mGKLBCLhGADEL]"));
        CODE_DEMAND_SPLITTING = new CommandEnum(AICodeStringUtil.H("Chh`\u0014akdchist]Joyi\u007fki"), IdeAction.H("\u977f\u6c76\u62a1\u522a"), AgentModuleEnum.CHAT, AICodeStringUtil.H("iIJlhB~MZLZ"));
        SQL_GENERATE_TALK = new CommandEnum(AICodeStringUtil.H("}tb\u0013ecciuLRcriwie"), IdeAction.H("E-L\u001dk,d\u52e6\u7402I|.x\u7578\u623c"), AgentModuleEnum.CHAT, AICodeStringUtil.H("mMO]qwakBccTLI_J@"));
        SQL_OPTIMIZE_TALK = new CommandEnum(AICodeStringUtil.H("}tb\u0013mvyejD\\criwie"), IdeAction.H("E-L\u001dk,d\u52e6\u7402I|.x\u4f7f\u533a"), AgentModuleEnum.CHAT, AICodeStringUtil.H("oMOQ}xni]XN`o\\LI_J@"));
        CODE_FAULT_ANALYSIS = new CommandEnum(AICodeStringUtil.H("ojj`\u0014ocsaxxLHgadel}"), IdeAction.H("\u653a\u96a8\u5261\u67bc"), AgentModuleEnum.CHAT, AICodeStringUtil.H("hHKJX^BLhGADEL]"));
        USER_KNOWLEDGE_LIST = new CommandEnum(AICodeStringUtil.H("yvkw\u0014bliz`bIAcrq\u007fvz"), IdeAction.H("\u83b3\u53b2\u77ca\u8bb9\u5ea7\u5270\u8844"), AgentModuleEnum.CHAT);
        USER_PARSE_WEB_URL = new CommandEnum(IdeAction.H("2\u007f\"~Qp%}=`\u0010S!m a5`"), AICodeStringUtil.H("\u83b1\u53f0\u77c8\u8bfb\u5ea5\u5232\u8846"), AgentModuleEnum.CHAT);
        CODE_EXPLAIN = new CommandEnum(AICodeStringUtil.H("ebhb7C~}qwl`"), IdeAction.H("\u4e9c\u7835\u8984\u91e6"), AgentModuleEnum.CHAT, AICodeStringUtil.H("NiK@XXQ]"));
        CODE_OPTIMIZE = new CommandEnum(AICodeStringUtil.H("aiii\u001dBVrdp\u007f\u007fk"), IdeAction.H("\u4e8d\u7824\u4f57\u5312Lm\u001a@\u0006\u0005"), AgentModuleEnum.CHAT, AICodeStringUtil.H("fAMGi]XN`o\\LI_J@"));
        CODE_TEST = new CommandEnum(AICodeStringUtil.H("xb^R\u001cbi~`|"), IdeAction.H("\u532a\u5177\u6d2c\u8bf9"), AgentModuleEnum.CHAT, AICodeStringUtil.H("xBNyRC^I_KI"));
        CODE_TEST_TEMPLATE = new CommandEnum(AICodeStringUtil.H("faak\u0013vc~xxYCk}qwqk"), IdeAction.H("\u6206\u91fb\u5332\u6d67"), AgentModuleEnum.CODE_TEST_TEMPLATE, AICodeStringUtil.H("`GYOOXhOYiSVZ"));
        CODE_COMMENT = new CommandEnum(AICodeStringUtil.H("ebhb7Ei`pskz"), IdeAction.H("\u5182\u6544\u6c8f\u91e6"), AgentModuleEnum.CHAT, AICodeStringUtil.H("iCDNiK@XXQ]"));
        CODE_INLINE_COMMENT = new CommandEnum(AICodeStringUtil.H("ojj`\u0014`ljdbbREi`pskz"), IdeAction.H("\u8833\u95c0\u6c8f\u91e6"), AgentModuleEnum.CHAT, AICodeStringUtil.H("jDBBNiK@XXQ]"));
        CODE_SPLIT = new CommandEnum(AICodeStringUtil.H("ohIC\u001c~mzlz"), IdeAction.H("\u5193\u6555\u6289\u5202Lm\u001a@\u0006\u0005"), AgentModuleEnum.CHAT, AICodeStringUtil.H("dSCOSdiH~MZLZ"));
        CODE_DEBUG = new CommandEnum(AICodeStringUtil.H("ohIC\u001cixtpi"), BasicActionsBundle.message(IdeAction.H("O\u000eO\bH\u000e\u000e\u0014C\u001bB&jJ[\u0016@\u000bI"), new Object[0]) + ": \u9519\u8bef\u5206\u6790\u4e0e\u4fee\u590d", AgentModuleEnum.CHAT, AICodeStringUtil.H("dbbCiXTPI"));
        CODE_HELP = new CommandEnum(AICodeStringUtil.H("dBBc\u0017usi~"), AgentModuleEnum.CHAT);
        GIT_REPOSITORY_STATUS = new CommandEnum(AICodeStringUtil.H("Gnx\u001f|`~fqoycuTYuy|bp}"), AgentModuleEnum.CHAT);
        TALK_DOWNLOAD_MARKDOWN_TABLE = new CommandEnum(AICodeStringUtil.H("qow{\\)DWi`joaqdctfhhZHyy|tik"), AgentModuleEnum.CHAT);
        TALK_RECOMMEND_GAMEPLAY = new CommandEnum(AICodeStringUtil.H("9JLl\u0016wkfadocchxJGkhmzdw"), AgentModuleEnum.CHAT);
        LOG_EVALUATION = new CommandEnum(AICodeStringUtil.H("ema\u0017iqLJsli\u007fj`"), AgentModuleEnum.COMMON);
        LOG_FEEDBACK = new CommandEnum(AICodeStringUtil.H("jbk\u001dKCci\u007fwfe"), AgentModuleEnum.COMMON);
        LOG_OPERATE = new CommandEnum(AICodeStringUtil.H("ac`7Ivhowqk"), AgentModuleEnum.COMMON);
        LOG_TIP_SETTING = new CommandEnum(AICodeStringUtil.H("bfe\u001cyewRUcyi\u007fki"), AgentModuleEnum.COMMON);
        ACTION_ABORT = new CommandEnum(AICodeStringUtil.H("gnxnBH\u001cl\u007fywz"), AgentModuleEnum.COMMON);
        LOG_ACCEPT_COUNT = new CommandEnum(AICodeStringUtil.H("ian\u0018gnob]Rynrckz"), AgentModuleEnum.COMMON);
        TALK_PREDICT = new CommandEnum(AICodeStringUtil.H("rl`l7Vthy\u007ffz"), AgentModuleEnum.CHAT);
        ACTION_OPEN_DOCUMENT = new CommandEnum(AICodeStringUtil.H("foqgj`\u0013mvhbxIIexpskz"), AgentModuleEnum.CHAT);
        CODE_COMMENT_RANGE = new CommandEnum(AICodeStringUtil.H("faak\u0013ai`abCRy\u007f|xbk"), AgentModuleEnum.CHAT);
        CODE_BATCH_UNIT_TEST_CREATE = new CommandEnum(AICodeStringUtil.H("mtt#WIAsomqp``vyyitYYe\u007fxwqk"), AgentModuleEnum.BATCH_UNIT_TEST);
        CODE_BATCH_UNIT_TEST_LIST = new CommandEnum(AICodeStringUtil.H("s))N:emqmmq|loyssHUrrq\u007fvz"), AgentModuleEnum.BATCH_UNIT_TEST);
        CODE_BATCH_UNIT_TEST_DOWNLOAD = new CommandEnum(AICodeStringUtil.H("ujj~\n$,_Cosp`lzvvc~xxIIqcqydj"), AgentModuleEnum.BATCH_UNIT_TEST);
        CODE_BATCH_UNIT_TEST_CANCEL = new CommandEnum(AICodeStringUtil.H("mtt#WIAsomqp``vyyitYYelsu`b"), AgentModuleEnum.BATCH_UNIT_TEST);
        CODE_BATCH_UNIT_TEST_DELETE = new CommandEnum(AICodeStringUtil.H("mtt#WIAsomqp``vyyitYYbhqsqk"), AgentModuleEnum.BATCH_UNIT_TEST);
        GIT_LANG_LIST = new CommandEnum(AICodeStringUtil.H("eoy\u0016kLHarq\u007fvz"), AgentModuleEnum.CODE_SEARCH);
        GIT_USER_REPOS = new CommandEnum(AICodeStringUtil.H("nkr\u0017ytHTy\u007fxfj}"), AgentModuleEnum.CODE_SEARCH);
        GIT_SEARCH = new CommandEnum(AICodeStringUtil.H("knY<uh|dff"), AgentModuleEnum.CODE_SEARCH);
        GIT_DIFF = new CommandEnum(AICodeStringUtil.H("JOr\u0017y\u007fch"), AgentModuleEnum.GIT_REVIEW);
        GIT_REVIEW = new CommandEnum(AICodeStringUtil.H("knY<thk\u007f`y"), IdeAction.H("\u4ecc\u787e\u98b0\u8ba3\u5b8d"), AgentModuleEnum.GIT_REVIEW, AICodeStringUtil.H("tHK_@Y"));
        GIT_COMMIT_MESSAGE = new CommandEnum(AICodeStringUtil.H("bgq\u0014jmk`esRKc~nwbk"), IdeAction.H("\u757b\u623f\u63af\u4e90\u4f86\u6043"), AgentModuleEnum.GIT_REVIEW, AICodeStringUtil.H("nGHH^FyceBP[LZ"));
        CODE_CHECK = new CommandEnum(AICodeStringUtil.H("ohIC\u001cnusfe"), IdeAction.H("\u4e9c\u7835\u68a7\u67c9"), AgentModuleEnum.CODE_CHECK);
        CODE_DEBUG_DUPLICATE = new CommandEnum(IdeAction.H("&a#i]h.b1h1a\u001aT(f<u3i"), AgentModuleEnum.CODE_CHECK, AICodeStringUtil.H("dbbCiXTPI"));
        CODE_TEST_ANALYSIS = new CommandEnum(AICodeStringUtil.H("vc~x\u001dLHgadel}"), AgentModuleEnum.UNIT_TEST);
        CODE_TEST_CASE = new CommandEnum(AICodeStringUtil.H("jmbh\u0016sHUrr~wvk"), AgentModuleEnum.UNIT_TEST, IdeAction.H("z\u0000L;P\u0001\\\u000b]\tK"));
        CODE_TEST_MAKE_CASE_JAVA = new CommandEnum(IdeAction.H("z\"\u007f3\u0016&a/j1f\u000eW!p5u1m"), AgentModuleEnum.UNIT_TEST, AICodeStringUtil.H("xBNyRC^I_KI"));
        TEST_MAKE_CASE = new CommandEnum(AICodeStringUtil.H("}guy\u0016jLMcr~wvk"), AgentModuleEnum.UNIT_TEST, IdeAction.H("z\u0000L;P\u0001\\\u000b]\tK"));
        TEST_MAKE_CODE = new CommandEnum(IdeAction.H("?e7{Th\u000eO!p<{#i"), AgentModuleEnum.UNIT_TEST, AICodeStringUtil.H("xBNyRC^I_KI"));
        CODE_TEST_CODE = new CommandEnum(AICodeStringUtil.H("jmbh\u0016sHUrr~yak"), AgentModuleEnum.UNIT_TEST);
        CODE_TEST_SAVE = new CommandEnum(AICodeStringUtil.H("jmbh\u0016sHUrrnwsk"), AgentModuleEnum.UNIT_TEST);
        LOG_TEST_COLLECTION_GENERATE = new CommandEnum(AICodeStringUtil.H("ia|\n2(XTxojbikjvobbxJChhowqk"), AgentModuleEnum.COMMON);
        LOG_TEST_COLLECTION_COMMIT = new CommandEnum(AICodeStringUtil.H("w\u007f!W_EtxzmjbegeyehCYebp{lz"), AgentModuleEnum.UNIT_TEST);
        USER_FEEDBACK_CATEGORY = new CommandEnum(AICodeStringUtil.H("^Sb~\u001fh`km`gngxNGrhzyww"), AgentModuleEnum.CHAT);
        FEEDBACK_CATEGORY_INFO = new CommandEnum(AICodeStringUtil.H("meBHGOFE\u0004AGYI@bt_\u0000TXCA"), AgentModuleEnum.CHAT);
        SERVER_RESOURCE = new CommandEnum(AICodeStringUtil.H("JCsej`\u001f}lpph~x_Cubhdfk"), AgentModuleEnum.SERVER_RESOURCE);
        DIALOG_EDIT = new CommandEnum(AICodeStringUtil.H("iefAIa\u0017xrlz"), IdeAction.H("\u7f71\u8fbd"), AgentModuleEnum.INLINE_CHAT);
        DIALOG_ABORT = new CommandEnum(IdeAction.H(" f/i\u0000C^n={5x"), AICodeStringUtil.H("\u7eed\u6b4c"), AgentModuleEnum.INLINE_CHAT);
        DIALOG_REJECT = new CommandEnum(AICodeStringUtil.H("fol`hJ<thwsfz"), IdeAction.H("\u62b5\u7ef1"), AgentModuleEnum.INLINE_CHAT);
        DIALOG_ACCEPT = new CommandEnum(IdeAction.H("d-n\"j\b>%l<q7x"), AICodeStringUtil.H("\u6380\u53f9"), AgentModuleEnum.INLINE_CHAT);
        DIALOG_DIFF = new CommandEnum(AICodeStringUtil.H("iefAIa\u0017y\u007fch"), IdeAction.H("\u5b9e\u6bf8"), AgentModuleEnum.INLINE_CHAT);
        INLINECHAT_GET_FUNC_RANGE = new CommandEnum(IdeAction.H("{j#\u0000L m/m3\u0016,e0p(p\u0001G;}>z i"), AgentModuleEnum.INLINE_CHAT, AICodeStringUtil.H("oC@NcceE\\Bs\u001c"));
        INLINECHAT_CATEGORY = new CommandEnum(AICodeStringUtil.H("ekbl`lanlx\u001dNGrhzyww"), AgentModuleEnum.INLINE_CHAT, IdeAction.H("-A\u0002L!a'G\u001e@1\u001e"));
        INLINECHAT_DIRECT = new CommandEnum(IdeAction.H("e)`\"n!l&d\u001b> f-q$x"), AgentModuleEnum.INLINE_CHAT, AICodeStringUtil.H("oC@NcceE\\Bs\u001c"));
        CommandEnum[] commandEnumArray = new CommandEnum[109];
        commandEnumArray[0] = INIT;
        commandEnumArray[1] = ACTION_INIT;
        commandEnumArray[2] = GIT_CODE_KNOWLEDGE_REPO_STATUS;
        commandEnumArray[3] = GIT_REPO_AUTHORIZE;
        commandEnumArray[4] = GIT_CODE_KNOWLEDGE_RE_INDEX;
        commandEnumArray[5] = GIT_SAVE_TOKEN;
        commandEnumArray[6] = UPDATE;
        commandEnumArray[7] = ERROR;
        commandEnumArray[8] = GENERAL_SETTING;
        commandEnumArray[9] = REPO_STATUS;
        commandEnumArray[10] = RAG_LANGUAGES;
        commandEnumArray[11] = USER_VERSION;
        commandEnumArray[12] = USER_LOGIN;
        commandEnumArray[13] = USER_LOGOUT;
        commandEnumArray[14] = USER_LOGIN_ABORT;
        commandEnumArray[15] = USER_LOGIN_CHECK;
        commandEnumArray[16] = USER_MODEL_LIST;
        commandEnumArray[17] = USER_PERMISSION;
        commandEnumArray[18] = MODEL_LIST_TIMER;
        commandEnumArray[19] = LOGIN_INFO;
        commandEnumArray[20] = ACTION_SYNC_DOCUMENT_LIST;
        commandEnumArray[21] = CODE_COMPLETE;
        commandEnumArray[22] = LOG_IMITATIVE_WRITE;
        commandEnumArray[23] = LOG_ACCEPT;
        commandEnumArray[24] = LOG_ACCEPT_WORD;
        commandEnumArray[25] = LOG_ACCEPT_LINE;
        commandEnumArray[26] = USER_CAN_CODE_ENHANCE;
        commandEnumArray[27] = LOG_REJECT;
        commandEnumArray[28] = LOG_REJECT_ESC;
        commandEnumArray[29] = LOG_DISPLAY;
        commandEnumArray[30] = SQL_SOURCE_LIST;
        commandEnumArray[31] = SQL_SOURCE_TYPES;
        commandEnumArray[32] = SQL_TEST_CONNECT;
        commandEnumArray[33] = SQL_SOURCE_EDIT;
        commandEnumArray[34] = SQL_SOURCE_DELETE;
        commandEnumArray[35] = SQL_TABLE_LIST;
        commandEnumArray[36] = SQL_GENERATE;
        commandEnumArray[37] = SQL_OPTIMIZE;
        commandEnumArray[38] = TALK_HISTORY;
        commandEnumArray[39] = TALK_LIST;
        commandEnumArray[40] = TALK_DELETE;
        commandEnumArray[41] = TALK_CLEAR;
        commandEnumArray[42] = TALK_ASK;
        commandEnumArray[43] = TALK_INTELLIGENT;
        commandEnumArray[44] = TALK_RESEND;
        commandEnumArray[45] = TALK_KNOWLEDGE;
        commandEnumArray[46] = CODE_DEMAND_TEST;
        commandEnumArray[47] = CODE_GENERATE_TEST_CASE;
        commandEnumArray[48] = CODE_DEMAND_ANALYSIS;
        commandEnumArray[49] = CODE_DEMAND_SPLITTING;
        commandEnumArray[50] = SQL_GENERATE_TALK;
        commandEnumArray[51] = SQL_OPTIMIZE_TALK;
        commandEnumArray[52] = CODE_FAULT_ANALYSIS;
        commandEnumArray[53] = USER_KNOWLEDGE_LIST;
        commandEnumArray[54] = USER_PARSE_WEB_URL;
        commandEnumArray[55] = CODE_EXPLAIN;
        commandEnumArray[56] = CODE_OPTIMIZE;
        commandEnumArray[57] = CODE_TEST;
        commandEnumArray[58] = CODE_TEST_TEMPLATE;
        commandEnumArray[59] = CODE_COMMENT;
        commandEnumArray[60] = CODE_INLINE_COMMENT;
        commandEnumArray[61] = CODE_SPLIT;
        commandEnumArray[62] = CODE_DEBUG;
        commandEnumArray[63] = CODE_HELP;
        commandEnumArray[64] = GIT_REPOSITORY_STATUS;
        commandEnumArray[65] = TALK_DOWNLOAD_MARKDOWN_TABLE;
        commandEnumArray[66] = TALK_RECOMMEND_GAMEPLAY;
        commandEnumArray[67] = LOG_EVALUATION;
        commandEnumArray[68] = LOG_FEEDBACK;
        commandEnumArray[69] = LOG_OPERATE;
        commandEnumArray[70] = LOG_TIP_SETTING;
        commandEnumArray[71] = ACTION_ABORT;
        commandEnumArray[72] = LOG_ACCEPT_COUNT;
        commandEnumArray[73] = TALK_PREDICT;
        commandEnumArray[74] = ACTION_OPEN_DOCUMENT;
        commandEnumArray[75] = CODE_COMMENT_RANGE;
        commandEnumArray[76] = CODE_BATCH_UNIT_TEST_CREATE;
        commandEnumArray[77] = CODE_BATCH_UNIT_TEST_LIST;
        commandEnumArray[78] = CODE_BATCH_UNIT_TEST_DOWNLOAD;
        commandEnumArray[79] = CODE_BATCH_UNIT_TEST_CANCEL;
        commandEnumArray[80] = CODE_BATCH_UNIT_TEST_DELETE;
        commandEnumArray[81] = GIT_LANG_LIST;
        commandEnumArray[82] = GIT_USER_REPOS;
        commandEnumArray[83] = GIT_SEARCH;
        commandEnumArray[84] = GIT_DIFF;
        commandEnumArray[85] = GIT_REVIEW;
        commandEnumArray[86] = GIT_COMMIT_MESSAGE;
        commandEnumArray[87] = CODE_CHECK;
        commandEnumArray[88] = CODE_DEBUG_DUPLICATE;
        commandEnumArray[89] = CODE_TEST_ANALYSIS;
        commandEnumArray[90] = CODE_TEST_CASE;
        commandEnumArray[91] = CODE_TEST_MAKE_CASE_JAVA;
        commandEnumArray[92] = TEST_MAKE_CASE;
        commandEnumArray[93] = TEST_MAKE_CODE;
        commandEnumArray[94] = CODE_TEST_CODE;
        commandEnumArray[95] = CODE_TEST_SAVE;
        commandEnumArray[96] = LOG_TEST_COLLECTION_GENERATE;
        commandEnumArray[97] = LOG_TEST_COLLECTION_COMMIT;
        commandEnumArray[98] = USER_FEEDBACK_CATEGORY;
        commandEnumArray[99] = FEEDBACK_CATEGORY_INFO;
        commandEnumArray[100] = SERVER_RESOURCE;
        commandEnumArray[101] = DIALOG_EDIT;
        commandEnumArray[102] = DIALOG_ABORT;
        commandEnumArray[103] = DIALOG_REJECT;
        commandEnumArray[104] = DIALOG_ACCEPT;
        commandEnumArray[105] = DIALOG_DIFF;
        commandEnumArray[106] = INLINECHAT_GET_FUNC_RANGE;
        commandEnumArray[107] = INLINECHAT_CATEGORY;
        commandEnumArray[108] = INLINECHAT_DIRECT;
        float = commandEnumArray;
    }

    public String getPermission() {
        CommandEnum a;
        return a.final;
    }

    public static CommandEnum[] values() {
        return (CommandEnum[])float.clone();
    }

    /*
     * WARNING - void declaration
     */
    private CommandEnum(String string2, String string3, AgentModuleEnum agentModuleEnum) {
        Enum a;
        void a2;
        void a3;
        CommandEnum a4;
        CommandEnum commandEnum = enum_;
        Enum enum_ = agentModuleEnum;
        CommandEnum commandEnum2 = a4 = commandEnum;
        a4.try = a3;
        commandEnum2.byte = a2;
        commandEnum2.case = a;
    }

    public AgentModuleEnum getAgentModuleEnum() {
        CommandEnum a;
        return a.case;
    }

    public String getType() {
        CommandEnum a;
        return a.try;
    }

    /*
     * WARNING - void declaration
     */
    private CommandEnum(String string2, AgentModuleEnum agentModuleEnum, String string3) {
        Object a;
        void a2;
        void a3;
        CommandEnum a4;
        CommandEnum commandEnum = object;
        Object object = string3;
        CommandEnum commandEnum2 = a4 = commandEnum;
        a4.try = a3;
        commandEnum2.case = a2;
        commandEnum2.final = a;
    }

    public static CommandEnum getByType(String string) {
        int a;
        String string2 = string;
        if (StringUtils.isBlank((CharSequence)string2)) {
            return null;
        }
        CommandEnum[] commandEnumArray = CommandEnum.values();
        int n = commandEnumArray.length;
        int n2 = a = 0;
        while (n2 < n) {
            CommandEnum commandEnum = commandEnumArray[a];
            if (commandEnum.try.equals(string2)) {
                return commandEnum;
            }
            n2 = ++a;
        }
        return null;
    }

    public String getDesc() {
        CommandEnum a;
        return a.byte;
    }

    /*
     * WARNING - void declaration
     */
    private CommandEnum(String string2, String string3, AgentModuleEnum agentModuleEnum, String string4) {
        Object a;
        void a2;
        void a3;
        void a4;
        CommandEnum a5;
        CommandEnum commandEnum = object;
        Object object = string4;
        CommandEnum commandEnum2 = a5 = commandEnum;
        CommandEnum commandEnum3 = a5;
        commandEnum3.try = a4;
        commandEnum3.byte = a3;
        commandEnum2.case = a2;
        commandEnum2.final = a;
    }

    public Icon getIcon() {
        CommandEnum a;
        return a.enum;
    }

    public static CommandEnum valueOf(String a) {
        return Enum.valueOf(CommandEnum.class, a);
    }
}
