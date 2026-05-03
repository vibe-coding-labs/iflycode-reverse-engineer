/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.actionSystem.AnAction
 *  org.apache.commons.lang3.StringUtils
 */
package com.aicode.agent.enums;

import com.aicode.action.batch.GeneratorConfig;
import com.aicode.action.click.CodeOptimizeAction;
import com.aicode.action.click.DocumentCommentAction;
import com.aicode.action.click.ExplainCodeAction;
import com.aicode.action.click.FunctionSplitAction;
import com.aicode.action.click.InlineCommentAction;
import com.aicode.action.click.OpenInlayInlineChatAction;
import com.aicode.action.click.UnitTestAction;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.content.util.EditorUtils;
import com.aicode.settings.AICodeSettingsState;
import com.intellij.openapi.actionSystem.AnAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class PermissionEnum
extends Enum<PermissionEnum> {
    public static final /* enum */ PermissionEnum DEMAND_TEST;
    public static final /* enum */ PermissionEnum SQL_OPTIMIZATION;
    public static final /* enum */ PermissionEnum CHAT_SQL_GENERATION;
    public static final /* enum */ PermissionEnum INLINE_CHAT;
    private static final /* synthetic */ PermissionEnum[] float;
    public static final /* enum */ PermissionEnum DEMAND_SPLIT;
    private final AnAction byte;
    public static final List<String> PERMISSION_ORDER_LIST;
    public static final /* enum */ PermissionEnum FUNCTION_SPLIT;
    public static final /* enum */ PermissionEnum TALK_INTELLIGENT;
    public static final /* enum */ PermissionEnum FAILURE_ANALYSIS;
    public static final /* enum */ PermissionEnum DEMAND_ANALYSIS;
    public static final /* enum */ PermissionEnum CODE_OPTIMIZATION;
    public static final /* enum */ PermissionEnum REVIEW;
    private final String enum;
    public static final /* enum */ PermissionEnum GENERATE_TEST_CASE;
    public static final /* enum */ PermissionEnum COMMENTS;
    public static final /* enum */ PermissionEnum CODE_KNOWLEDGE_BASE;
    public static final /* enum */ PermissionEnum CHAT_MODULE;
    public static final /* enum */ PermissionEnum CHAT_SQL_OPTIMIZATION;
    public static final /* enum */ PermissionEnum LINE_COMMENTS;
    public static final /* enum */ PermissionEnum UNIT_TESTING;
    public static final /* enum */ PermissionEnum CODE_DEBUG;
    public static final /* enum */ PermissionEnum GENERATE_COMMIT;
    public static final /* enum */ PermissionEnum DOC_COMMENTS;
    public static final /* enum */ PermissionEnum SQL_GENERATION;
    public static final List<PermissionEnum> RIGHT_PERMISSION_ORDER_LIST;
    public static final /* enum */ PermissionEnum BATCH_UNITTEST;

    public AnAction getAction() {
        PermissionEnum a;
        return a.byte;
    }

    /*
     * WARNING - void declaration
     */
    private PermissionEnum(String string2, AnAction anAction) {
        PermissionEnum a;
        void a2;
        PermissionEnum a3;
        PermissionEnum permissionEnum = permissionEnum2;
        PermissionEnum permissionEnum2 = anAction;
        PermissionEnum permissionEnum3 = a3 = permissionEnum;
        permissionEnum3.enum = a2;
        permissionEnum3.byte = a;
    }

    public String getPermission() {
        PermissionEnum a;
        return a.enum;
    }

    public static List<CommandEnum> getEditorAction() {
        ArrayList<CommandEnum> arrayList = new ArrayList<CommandEnum>();
        Iterator iterator = AICodeSettingsState.getInstance().permissions.iterator();
        block0: while (true) {
            Iterator iterator2 = iterator;
            while (iterator2.hasNext()) {
                String string = (String)iterator.next();
                if (AICodeSettingsState.getInstance().lineToolsPermissionDocComments && DOC_COMMENTS.getPermission().equals(string)) {
                    iterator2 = iterator;
                    arrayList.add(CommandEnum.CODE_COMMENT);
                    continue;
                }
                if (AICodeSettingsState.getInstance().lineToolsPermissionLineComments && LINE_COMMENTS.getPermission().equals(string)) {
                    iterator2 = iterator;
                    arrayList.add(CommandEnum.CODE_INLINE_COMMENT);
                    continue;
                }
                if (AICodeSettingsState.getInstance().lineToolsPermissionFunctionSplit && FUNCTION_SPLIT.getPermission().equals(string)) {
                    iterator2 = iterator;
                    arrayList.add(CommandEnum.CODE_SPLIT);
                    continue;
                }
                if (AICodeSettingsState.getInstance().lineToolsPermissionComments && COMMENTS.getPermission().equals(string)) {
                    iterator2 = iterator;
                    arrayList.add(CommandEnum.CODE_EXPLAIN);
                    continue;
                }
                if (AICodeSettingsState.getInstance().lineToolsPermissionCodeOptimization && CODE_OPTIMIZATION.getPermission().equals(string)) {
                    iterator2 = iterator;
                    arrayList.add(CommandEnum.CODE_OPTIMIZE);
                    continue;
                }
                if (!AICodeSettingsState.getInstance().lineToolsPermissionUnitTesting || !UNIT_TESTING.getPermission().equals(string)) continue block0;
                arrayList.add(CommandEnum.CODE_TEST);
                continue block0;
            }
            break;
        }
        return arrayList;
    }

    public static PermissionEnum[] values() {
        return (PermissionEnum[])float.clone();
    }

    public static List<AnAction> getRightAction() {
        int n;
        ArrayList<AnAction> arrayList = new ArrayList<AnAction>();
        HashMap<String, AnAction> hashMap = new HashMap<String, AnAction>();
        PermissionEnum[] permissionEnumArray = PermissionEnum.values();
        int n2 = permissionEnumArray.length;
        int n3 = n = 0;
        while (n3 < n2) {
            PermissionEnum permissionEnum = permissionEnumArray[n];
            String string = permissionEnum.getPermission();
            AnAction anAction = permissionEnum.getAction();
            if (anAction != null) {
                hashMap.put(string, anAction);
            }
            n3 = ++n;
        }
        for (String string : PERMISSION_ORDER_LIST) {
            if (!hashMap.containsKey(string)) continue;
            AnAction anAction = (AnAction)hashMap.get(string);
            arrayList.add(anAction);
        }
        return arrayList;
    }

    public static PermissionEnum getPermissionEnum(String string) {
        int a;
        String string2 = string;
        if (StringUtils.isBlank((CharSequence)string2)) {
            return null;
        }
        PermissionEnum[] permissionEnumArray = PermissionEnum.values();
        int n = permissionEnumArray.length;
        int n2 = a = 0;
        while (n2 < n) {
            PermissionEnum permissionEnum = permissionEnumArray[a];
            if (string2.equals(permissionEnum.getPermission())) {
                return permissionEnum;
            }
            n2 = ++a;
        }
        return null;
    }

    static {
        CODE_OPTIMIZATION = new PermissionEnum(GeneratorConfig.H(";\u0001\u0006\u0011+\u0002\u0011\u001a#1\u001f\u0012\n\u0001\t\u001e"), new CodeOptimizeAction(CommandEnum.CODE_OPTIMIZE.getDesc(), CommandEnum.CODE_OPTIMIZE.getType()));
        COMMENTS = new PermissionEnum(GeneratorConfig.H("\r7\b\u001e\u001b\u0006\u0012\u0003"), new ExplainCodeAction(CommandEnum.CODE_EXPLAIN.getDesc(), CommandEnum.CODE_EXPLAIN.getType()));
        UNIT_TESTING = new PermissionEnum(GeneratorConfig.H("'\u000b\u001a:\f\u0000\u0000\n\u0001\b\u0017"), new UnitTestAction(CommandEnum.CODE_TEST.getDesc(), CommandEnum.CODE_TEST.getType()));
        DOC_COMMENTS = new PermissionEnum(GeneratorConfig.H("6\n\u0010\r7\b\u001e\u001b\u0006\u0012\u0003"), new DocumentCommentAction(CommandEnum.CODE_COMMENT.getDesc(), CommandEnum.CODE_COMMENT.getType()));
        LINE_COMMENTS = new PermissionEnum(GeneratorConfig.H("(\u001b\u000b\u0016\r7\b\u001e\u001b\u0006\u0012\u0003"), new InlineCommentAction(CommandEnum.CODE_INLINE_COMMENT.getDesc(), CommandEnum.CODE_INLINE_COMMENT.getType()));
        FUNCTION_SPLIT = new PermissionEnum(GeneratorConfig.H("2\u0011\u001c\u0006\u0007'7\u000b \u000e\u0004\u000f\u0004"), new FunctionSplitAction(CommandEnum.CODE_SPLIT.getDesc(), CommandEnum.CODE_SPLIT.getType()));
        INLINE_CHAT = new PermissionEnum(GeneratorConfig.H("-\u001c\t\u001a =&\u001b\u001f\u001c0B"), new OpenInlayInlineChatAction(EditorUtils.H("t(Z\u0004s#E5\u007f1"), GeneratorConfig.H("\f\u001d\"1\u000b\u0016=\u0000\u0007\u0004")));
        TALK_INTELLIGENT = new PermissionEnum(GeneratorConfig.H(":\u0003\u0018\u000f;\u000b\u0007+4\t\u001a\u0019\r\b\u0004"), null);
        CHAT_MODULE = new PermissionEnum(GeneratorConfig.H("&\u001b/,(\u001c\u001a\u001d\n\u0015"), null);
        CODE_DEBUG = new PermissionEnum(GeneratorConfig.H("0!<\u00007\u001b\n\u0013\u0017"), null);
        REVIEW = new PermissionEnum(GeneratorConfig.H("7\u0016\b\u0001\u0003\u0007"), null);
        GENERATE_COMMIT = new PermissionEnum(GeneratorConfig.H("%\u0011\n\u0017\u0017\u0012:=&\u001c\u0013\u0005\u000f\u0004"), null);
        BATCH_UNITTEST = new PermissionEnum(GeneratorConfig.H("6\u0005\u0006\u0006\u001b\u001b6\f\u0007*\r\u0015\u0004"), null);
        CODE_KNOWLEDGE_BASE = new PermissionEnum(GeneratorConfig.H("-\u0017\n\u0007?\n\u001d\u0012\u001f+<\u0002\u0016<\t\u0015\u0015"), null);
        SQL_GENERATION = new PermissionEnum(GeneratorConfig.H("'5>\"\u0016 =\u0017\u0012\n\u0001\t\u001e"), null);
        SQL_OPTIMIZATION = new PermissionEnum(GeneratorConfig.H("=38+\u0002\u0011\u001a#1\u001f\u0012\n\u0001\t\u001e"), null);
        DEMAND_TEST = new PermissionEnum(GeneratorConfig.H("!\u0016#9\u000b\u0017*\r\u0015\u0004"), null);
        GENERATE_TEST_CASE = new PermissionEnum(GeneratorConfig.H("?\u000b\f\u0011\u0016\u0013\u0011\u0016\u001a=\u0016\u0007=\t\u0015\u0015"), null);
        CHAT_SQL_GENERATION = new PermissionEnum(GeneratorConfig.H("-\u0010\u000f\u0016'5>\"\u0016 =\u0017\u0012\n\u0001\t\u001e"), null);
        CHAT_SQL_OPTIMIZATION = new PermissionEnum(GeneratorConfig.H("7\u0010\u000f\f=38+\u0002\u0011\u001a#1\u001f\u0012\n\u0001\t\u001e"), null);
        DEMAND_ANALYSIS = new PermissionEnum(GeneratorConfig.H("&\u0011\t\u0013\u000b\u0017\u000f6\u0004\u001f\u0007\u001b\u000f\u0003"), null);
        DEMAND_SPLIT = new PermissionEnum(GeneratorConfig.H("6\u0000\u001e/6\u0001 \u000e\u0004\u000f\u0004"), null);
        FAILURE_ANALYSIS = new PermissionEnum(GeneratorConfig.H("(\u0003\u001d\b\u0007\u0017\u0016\u000f6\u0004\u001f\u0007\u001b\u000f\u0003"), null);
        PermissionEnum[] permissionEnumArray = new PermissionEnum[23];
        permissionEnumArray[0] = CODE_OPTIMIZATION;
        permissionEnumArray[1] = COMMENTS;
        permissionEnumArray[2] = UNIT_TESTING;
        permissionEnumArray[3] = DOC_COMMENTS;
        permissionEnumArray[4] = LINE_COMMENTS;
        permissionEnumArray[5] = FUNCTION_SPLIT;
        permissionEnumArray[6] = INLINE_CHAT;
        permissionEnumArray[7] = TALK_INTELLIGENT;
        permissionEnumArray[8] = CHAT_MODULE;
        permissionEnumArray[9] = CODE_DEBUG;
        permissionEnumArray[10] = REVIEW;
        permissionEnumArray[11] = GENERATE_COMMIT;
        permissionEnumArray[12] = BATCH_UNITTEST;
        permissionEnumArray[13] = CODE_KNOWLEDGE_BASE;
        permissionEnumArray[14] = SQL_GENERATION;
        permissionEnumArray[15] = SQL_OPTIMIZATION;
        permissionEnumArray[16] = DEMAND_TEST;
        permissionEnumArray[17] = GENERATE_TEST_CASE;
        permissionEnumArray[18] = CHAT_SQL_GENERATION;
        permissionEnumArray[19] = CHAT_SQL_OPTIMIZATION;
        permissionEnumArray[20] = DEMAND_ANALYSIS;
        permissionEnumArray[21] = DEMAND_SPLIT;
        permissionEnumArray[22] = FAILURE_ANALYSIS;
        float = permissionEnumArray;
        String[] stringArray = new String[11];
        stringArray[0] = EditorUtils.H("U)q/X\b^.g)Hw");
        stringArray[1] = GeneratorConfig.H("6\n\u0010\r7\b\u001e\u001b\u0006\u0012\u0003");
        stringArray[2] = EditorUtils.H("P.s#u\u0002p+c3j6");
        stringArray[3] = GeneratorConfig.H("2\u0011\u001c\u0006\u0007'7\u000b \u000e\u0004\u000f\u0004");
        stringArray[4] = EditorUtils.H("u\u0002p+c3j6");
        stringArray[5] = GeneratorConfig.H(";\u0001\u0006\u0011+\u0002\u0011\u001a#1\u001f\u0012\n\u0001\t\u001e");
        stringArray[6] = EditorUtils.H("\u0012s/B9x5r4p\"");
        stringArray[7] = GeneratorConfig.H("0!<\u00007\u001b\n\u0013\u0017");
        stringArray[8] = EditorUtils.H("O#p4{2");
        stringArray[9] = GeneratorConfig.H("&\u001b/,(\u001c\u001a\u001d\n\u0015");
        stringArray[10] = EditorUtils.H("\u0003}3~.c\u0003t2R8m1");
        PERMISSION_ORDER_LIST = Arrays.asList(stringArray);
        PermissionEnum[] permissionEnumArray2 = new PermissionEnum[6];
        permissionEnumArray2[0] = DOC_COMMENTS;
        permissionEnumArray2[1] = LINE_COMMENTS;
        permissionEnumArray2[2] = FUNCTION_SPLIT;
        permissionEnumArray2[3] = COMMENTS;
        permissionEnumArray2[4] = CODE_OPTIMIZATION;
        permissionEnumArray2[5] = UNIT_TESTING;
        RIGHT_PERMISSION_ORDER_LIST = Arrays.asList(permissionEnumArray2);
    }

    public static PermissionEnum valueOf(String a) {
        return Enum.valueOf(PermissionEnum.class, a);
    }
}
