package com.aicode.action.click;

import com.aicode.PluginStartupActivity;
import com.aicode.action.ActionsUtil;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.dto.chat.FirstChatMessage;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.enums.PageEnum;
import com.aicode.agent.enums.PermissionEnum;
import com.aicode.agent.service.ChatService;
import com.aicode.agent.service.CommonService;
import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.util.PropertyUtils;
import com.aicode.view.WebViewWindowPanel;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsActions;
import javax.swing.Icon;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: qf */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/click/BaseAction.class */
public abstract class BaseAction extends PluginAnAction {
    public String taskName;
    public String type;

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m52enum(int a) {
        String H = PropertyUtils.H("M)$a&y/bm|)cgPB47Z4z:!;}?{!~x>36i<8;ay/>)(N\u001204<s4dCZ.bvc.<#o w");
        Object[] objArr = new Object[3];
        objArr[0] = OpenTelemetryUtil.H(")x/a?");
        objArr[1] = PropertyUtils.H("o4,9/p(s%sf\u007fo/\tX-;2j.s\b\u001b\u0003w%d\n\u007f9s#u");
        switch (a) {
            case 0:
            default:
                objArr[2] = OpenTelemetryUtil.H("7g4I\u000bl\u0016p#n#|'j/");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = PropertyUtils.H("#}#~ ~");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    public BaseAction(@NlsActions.ActionText @Nullable String text, @NlsActions.ActionDescription @Nullable String a) {
        this(text, a, null);
        this.taskName = text;
        this.type = a;
    }

    public BaseAction(@NlsActions.ActionText @Nullable String text, @NlsActions.ActionDescription @Nullable String description, @Nullable Icon a) {
        super(text, description, a);
        ActionsUtil.registerOrReplaceAction(this);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void handle(@NotNull AnActionEvent event, String type) {
        FirstChatMessage rightChatMessage2Web;
        if (event == null) {
            m52enum(1);
        }
        Project project = event.getProject();
        if (((Editor) event.getData(PlatformDataKeys.EDITOR)) == null || project == null || (rightChatMessage2Web = ChatService.getRightChatMessage2Web(project, type)) == null) {
            return;
        }
        handleRight(project, rightChatMessage2Web);
    }

    public void actionPerformed(@NotNull AnActionEvent a) {
        if (a == null) {
            m52enum(0);
        }
        handle(a, this.type);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void update(AnActionEvent a) {
        if (CommandEnum.CODE_COMMENT.getType().equals(this.type)) {
            if (!AICodeSettingsState.getInstance().permissions.contains(PermissionEnum.DOC_COMMENTS.getPermission())) {
                a.getPresentation().setEnabledAndVisible(false);
                return;
            }
        } else if (CommandEnum.CODE_INLINE_COMMENT.getType().equals(this.type)) {
            if (!AICodeSettingsState.getInstance().permissions.contains(PermissionEnum.LINE_COMMENTS.getPermission())) {
                a.getPresentation().setEnabledAndVisible(false);
                return;
            }
        } else if (!CommandEnum.CODE_SPLIT.getType().equals(this.type)) {
            if (CommandEnum.CODE_EXPLAIN.getType().equals(this.type)) {
                if (!AICodeSettingsState.getInstance().permissions.contains(PermissionEnum.COMMENTS.getPermission())) {
                    a.getPresentation().setEnabledAndVisible(false);
                    return;
                }
            } else if (CommandEnum.CODE_OPTIMIZE.getType().equals(this.type) && !AICodeSettingsState.getInstance().permissions.contains(PermissionEnum.CODE_OPTIMIZATION.getPermission())) {
                a.getPresentation().setEnabledAndVisible(false);
                return;
            }
        } else if (!AICodeSettingsState.getInstance().permissions.contains(PermissionEnum.FUNCTION_SPLIT.getPermission())) {
            a.getPresentation().setEnabledAndVisible(false);
            return;
        }
        Project project = a.getProject();
        Editor editor = (Editor) a.getData(PlatformDataKeys.EDITOR);
        if (!StringUtils.isBlank(PluginStartupActivity.getApiKey())) {
            if (CommandEnum.CODE_EXPLAIN.getType().equals(this.type) || CommandEnum.CODE_OPTIMIZE.getType().equals(this.type) || CommandEnum.CODE_COMMENT.getType().equals(this.type)) {
                a.getPresentation().setEnabled(true);
                return;
            }
            boolean z = false;
            if (editor != null && project != null) {
                z = editor.getSelectionModel().getSelectedText() != null;
            }
            a.getPresentation().setEnabled(z);
            return;
        }
        a.getPresentation().setEnabled(false);
    }

    public static void handleRight(Project a, FirstChatMessage a2) {
        PluginStartupActivity.handleExecutorService.execute(() -> {
            CommonService.openPage(a, PageEnum.CHAT_VIEW);
            if (SocketMessageHandleListener.send2Web(a, a2).booleanValue()) {
                CommonService.chatMessage2Web(a, a2, true);
            } else {
                a.putUserData(WebViewWindowPanel.CODE_MESSAGE_DATA, a2);
            }
        });
    }

    public BaseAction() {
    }
}
