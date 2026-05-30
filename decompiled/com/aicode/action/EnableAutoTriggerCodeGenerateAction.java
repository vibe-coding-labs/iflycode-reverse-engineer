package com.aicode.action;

import com.aicode.PluginStartupActivity;
import com.aicode.action.click.PluginAnAction;
import com.aicode.agent.service.CommonService;
import com.aicode.content.util.file.FileExtensionLanguageDetails;
import com.aicode.exception.RequestTimeoutException;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.statusBar.StatusBarPopup;
import com.aicode.util.MessageBundle;
import com.aicode.view.WebViewWindowPanel;
import com.google.gson.JsonObject;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: nh */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/EnableAutoTriggerCodeGenerateAction.class */
public class EnableAutoTriggerCodeGenerateAction extends PluginAnAction implements CodeAction {
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m25enum(int a) {
        String H = RequestTimeoutException.H("\u0018\n7\u0004\u00190.\u0015a\u00065\tA��\t\t(3'\u001f>S \u00105\u0007\u001b2.\u001e#PwT\u0001tp\u001e=Zu\u0002iC'U,\u0015#\u0005T;9\u0003a\u00029]4\u000e7\u0016");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = FileExtensionLanguageDetails.H("~yx`h");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = RequestTimeoutException.H("\u001f");
                break;
        }
        objArr[1] = FileExtensionLanguageDetails.H("e`p\u001bG{cf\u007fb:fvq~}n\fta|fz`VRAjC|ubpwrB|pcBrOVqp`cHx{tar");
        switch (a) {
            case 0:
            default:
                objArr[2] = RequestTimeoutException.H(")\r>\u001a/\u001f");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = FileExtensionLanguageDetails.H("gfcH\\mAqtot}pkx");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    public boolean isDumbAware() {
        return true;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void actionPerformed(@NotNull AnActionEvent a) {
        if (a == null) {
            m25enum(1);
        }
        AICodeSettingsState.getInstance().autoTrigger = !AICodeSettingsState.getInstance().autoTrigger;
        Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
        int length = openProjects.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            Project project = openProjects[i2];
            if (!project.isDisposed()) {
                StatusBarPopup.update(project);
                WebViewWindowPanel webViewWindowPanel = (WebViewWindowPanel) project.getUserData(WebViewWindowPanel.WEB_VIEW_PANEL);
                JsonObject config = CommonService.getConfig();
                if (webViewWindowPanel != null) {
                    webViewWindowPanel.sendMessage2webView(config);
                }
            }
            i2++;
            i = i2;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void update(@NotNull AnActionEvent a) {
        if (a == null) {
            m25enum(0);
        }
        if (StringUtils.isNotBlank(PluginStartupActivity.getApiKey())) {
            a.getPresentation().setVisible(true);
            a.getPresentation().setEnabled(true);
        } else {
            a.getPresentation().setVisible(false);
            a.getPresentation().setEnabled(false);
        }
        a.getPresentation().setText(!AICodeSettingsState.getInstance().autoTrigger ? MessageBundle.get(RequestTimeoutException.H("\u001a8\u0013?\u0015\u0017}4\u0018(\u001b2\u001d\"'\u001d6.\u00045_\u0015 \"\u0018o\u0014.\u0014=\u001c>\b")) : MessageBundle.get(FileExtensionLanguageDetails.H("e\u007ffxCP+r`}g{wAHP{b`9@Fw~:r{rhzkn")));
    }
}
