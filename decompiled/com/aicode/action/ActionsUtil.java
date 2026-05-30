package com.aicode.action;

import cn.hutool.core.util.StrUtil;
import com.aicode.action.batch.BatchUTGeneratorAction;
import com.aicode.action.batch.doc.BatchFunctionCommentAction;
import com.aicode.action.click.OpenInlayInlineChatAction;
import com.aicode.action.click.TerminalAction;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.enums.PermissionEnum;
import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.icons.Icons;
import com.aicode.message.BasicActionsBundle;
import com.aicode.ui.FontKt;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.Anchor;
import com.intellij.openapi.actionSystem.Constraints;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.keymap.KeymapUtil;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.text.CaseUtils;

/* compiled from: vg */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/ActionsUtil.class */
public class ActionsUtil {

    /* renamed from: enum, reason: not valid java name */
    private static AtomicBoolean f5enum = new AtomicBoolean(false);

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static synchronized void refreshActions() {
        if (f5enum.get()) {
            return;
        }
        f5enum.set(true);
        ActionManager actionManager = ActionManager.getInstance();
        handRightChatAction(actionManager);
        handBatchTestAction(actionManager);
        handCodeReviewAction(actionManager);
        handCommitAction(actionManager);
        Ke(actionManager);
        handBatchFunctionCommentAction(actionManager);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void registerOrReplaceAction(AnAction a) {
        ActionManager actionManager = ActionManager.getInstance();
        String convertToId = convertToId(a.getTemplateText());
        if (actionManager.getAction(convertToId) != null) {
            actionManager.replaceAction(convertToId, a);
        } else {
            actionManager.registerAction(convertToId, a, PluginId.getId(BasicActionsBundle.message(OpenTelemetryUtil.H("i%m%k.<&X\u0005r8fbd-"), new Object[0])));
        }
    }

    public static void handCommitAction(ActionManager a) {
        a.getAction(OpenTelemetryUtil.H("v\u0007qhX4{?o-j\nq\"]\u001f{\u0016z#x9")).add(new CommitMessageSuggestionAction(CommandEnum.GIT_COMMIT_MESSAGE.getDesc(), CommandEnum.GIT_COMMIT_MESSAGE.getType()), new Constraints(Anchor.FIRST, FontKt.H("\u000e')h\u0017#-1\"8\u0013+7<*00\u0005*+,5")));
    }

    private static void Ke(ActionManager a) {
        a.getAction(FontKt.H("��0017(<�� 1\u0016\u00111\u000f12.71\u0005<;")).add(new TerminalAction(CommandEnum.CODE_DEBUG.getDesc(), CommandEnum.CODE_DEBUG.getType(), Icons.LOGO), Constraints.FIRST);
    }

    public static void handCodeReviewAction(ActionManager a) {
        DefaultActionGroup action = a.getAction(FontKt.H("\u001d*3 '9\u0005<=-4\u000b1-4&87"));
        DefaultActionGroup action2 = a.getAction(OpenTelemetryUtil.H("A.t?o)}\u001cf.e\u0006[��`!E)c<"));
        PrepushReviewAction prepushReviewAction = new PrepushReviewAction(CommandEnum.GIT_REVIEW.getDesc(), CommandEnum.GIT_REVIEW.getType());
        Constraints constraints = new Constraints(Anchor.LAST, "");
        Constraints constraints2 = new Constraints(Anchor.AFTER, FontKt.H("\u001d*3 '9\u0005<=-4q\f'.!+1"));
        action.add(prepushReviewAction, constraints);
        action2.add(prepushReviewAction, constraints2);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void handRightChatAction(ActionManager a) {
        DefaultActionGroup action = a.getAction(OpenTelemetryUtil.H(".bC\u000bf#;\u0014l%z%}\nq\"]\u001f{\u0016z#x9"));
        action.removeAll();
        action.add(new OpenWindowAction());
        String blankToDefault = StrUtil.blankToDefault(KeymapUtil.getFirstKeyboardShortcutText(FontKt.H(";\u0018:-\u001f$/6\u0018\u000f\u0017 \"+\u001f!,-6+")), KeymapUtil.getFirstMouseShortcutText(OpenTelemetryUtil.H("M6p?A\"b#a.Q>U\u0004T2|%b'")));
        action.add(new OpenInlayInlineChatAction("内联聊天(Beta)  " + blankToDefault, "内联聊天(Beta)  " + blankToDefault));
        action.addSeparator();
        Iterator<PermissionEnum> it = PermissionEnum.RIGHT_PERMISSION_ORDER_LIST.iterator();
        while (it.hasNext()) {
            AnAction action2 = it.next().getAction();
            it = it;
            action.add(action2);
        }
    }

    public static String convertToId(String a) {
        return BasicActionsBundle.message(FontKt.H("#+*1(L\u001f*<1&>i\u0011,1067��>&'*7\u0004-\f\n(j\n\u0007+;"), new Object[0]) + "." + CaseUtils.toCamelCase(a, true, new char[0]);
    }

    public static void handBatchFunctionCommentAction(ActionManager a) {
        a.getAction(OpenTelemetryUtil.H("R4z;m/z\u001cf.e\u0006[��`!E)c<")).add(new BatchFunctionCommentAction(BasicActionsBundle.message(FontKt.H("/10+2v%0&\u001b\f=j\u001f\"32=<\u0018&76\u0019\u0004\u0013:,*.l,!!1"), new Object[0]) + ": 批量函数注释", OpenTelemetryUtil.H("\u0013i8m\"H.!nR\u0005v)gqn9`){\"}8\u0014\u0013z<e)c=")), new Constraints(Anchor.BEFORE, FontKt.H("\t7\u001b\u0002='.\u00103#%\u001e656/;\u000f:=\u0011*0\u0005*+,5")));
    }

    public static void handBatchTestAction(ActionManager a) {
        a.getAction(FontKt.H("3\r=$!;7\t\u001f\u000f#\u0018,/+2\u0015!70")).add(new BatchUTGeneratorAction(BasicActionsBundle.message(OpenTelemetryUtil.H("#p)`!&-g)`/*%e��k2z#I/z#`%U$[\u0005e\u007f|)u="), new Object[0]) + ": 批量单元测试", FontKt.H("\n>7��\u0017\u0015+*=1>\u0002\u0005&h\u0016176\f!*1")), new Constraints(Anchor.BEFORE, OpenTelemetryUtil.H("^8`!*hT2k#b\u0001g<{:B.|#f\u0005{\u0016z#x9")));
    }
}
