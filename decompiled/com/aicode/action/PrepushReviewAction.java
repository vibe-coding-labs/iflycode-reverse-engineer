package com.aicode.action;

import com.aicode.PluginStartupActivity;
import com.aicode.action.click.PluginAnAction;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.enums.PageEnum;
import com.aicode.agent.enums.PermissionEnum;
import com.aicode.agent.service.CommonService;
import com.aicode.agent.service.GitReviewService;
import com.aicode.content.util.OverlayUtils;
import com.aicode.enums.WebViewResponseTypeEnum;
import com.aicode.icons.Icons;
import com.aicode.language.AICodeLanguageInfo;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.util.PropertyUtils;
import com.intellij.openapi.ListSelection;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.FileStatus;
import com.intellij.openapi.vcs.VcsDataKeys;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.AnimatedIcon;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: rh */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/PrepushReviewAction.class */
public class PrepushReviewAction extends PluginAnAction {
    public static String path;

    /* renamed from: enum, reason: not valid java name */
    private static final List<String> f8enum = new ArrayList(Arrays.asList(PropertyUtils.H("\u0001_"), AICodeLanguageInfo.H("\u0014<[k"), PropertyUtils.H("^\u0003X"), AICodeLanguageInfo.H("\u001a W}"), PropertyUtils.H("B��H"), AICodeLanguageInfo.H("\u0006#G}"), PropertyUtils.H("J\u001cO"), AICodeLanguageInfo.H("\u000e?@}"), PropertyUtils.H("N\u0014O")));
    public static AtomicBoolean PREPUSH_REVIEW_BUTTON = new AtomicBoolean(false);
    public static AtomicBoolean PAGE_READY = new AtomicBoolean(false);

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m29enum(int a) {
        String H = AICodeLanguageInfo.H("EG,\u000f$\u001d0\u001bc\u0014*\u0006d5JZ\u000e\u0005-\u0005#^*\n-\u000f4\r6\u0016\nih[/Jc\u001d)^x\u001fE\u007f:X3\u001a0\u0006e\u001a+\u0001o\u001c!U1\u001b+\u001a");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = PropertyUtils.H("y;\u007f\"o");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = AICodeLanguageInfo.H("\u0013");
                break;
        }
        objArr[1] = PropertyUtils.H("F?jhq;f8d4)+~\u0004N(~{S9y7e&j1Q7\u007f3v\n\u007f9s#u");
        switch (a) {
            case 0:
            default:
                objArr[2] = AICodeLanguageInfo.H("\u000e \u0006,\u001b*%*\f\"\u001a-\u0003\"\u0012");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = PropertyUtils.H(">l){8~");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public List<Change> getSelectedChange(AnActionEvent a) {
        ArrayList arrayList = new ArrayList();
        ListSelection listSelection = (ListSelection) a.getData(VcsDataKeys.CHANGES_SELECTION);
        if (listSelection == null) {
            return arrayList;
        }
        return listSelection.getList();
    }

    public PrepushReviewAction(@Nullable String text, @Nullable String a) {
        super(text, a, Icons.ToolWindowIcon);
    }

    public void actionPerformed(@NotNull AnActionEvent a) {
        if (a == null) {
            m29enum(0);
        }
        ApplicationManager.getApplication().invokeLater(() -> {
            Project project = a.getProject();
            if (project == null || (a.getPresentation().getIcon() instanceof AnimatedIcon)) {
                return;
            }
            Point locationOnScreen = a.getInputEvent().getLocationOnScreen();
            locationOnScreen.y += 5;
            List<Change> selectedChange = getSelectedChange(a);
            if (!selectedChange.isEmpty()) {
                Change change = selectedChange.get(0);
                if (!change.isMoved()) {
                    if (!change.isRenamed()) {
                        if (FileStatus.DELETED.equals(change.getFileStatus())) {
                            OverlayUtils.showInfoBalloon(PropertyUtils.H("剶陥盏斛亻丗讈宺"), locationOnScreen);
                            return;
                        }
                        VirtualFile virtualFile = change.getVirtualFile();
                        if (virtualFile == null) {
                            return;
                        }
                        if (f8enum.contains(virtualFile.getExtension().toUpperCase())) {
                            OverlayUtils.showInfoBalloon(AICodeLanguageInfo.H("曭乎敝捄旳杨旲亥盦讈寜"), locationOnScreen);
                            return;
                        }
                        path = virtualFile.getPath();
                        if (StringUtils.isBlank(path)) {
                            return;
                        }
                        FileDocumentManager fileDocumentManager = FileDocumentManager.getInstance();
                        Document document = fileDocumentManager.getDocument(virtualFile);
                        if (fileDocumentManager.isFileModified(virtualFile)) {
                            fileDocumentManager.saveDocument(document);
                        }
                        HashMap hashMap = new HashMap();
                        hashMap.put(PropertyUtils.H("9c<~"), WebViewResponseTypeEnum.CODE_REVIEW_RECEIVER_PAGE_INIT.getType());
                        hashMap.put(AICodeLanguageInfo.H("\u00032\u000e9\u0018"), null);
                        SocketMessageHandleListener.send2Web(project, hashMap);
                        CommonService.openPage(project, PageEnum.CODE_REVIEW);
                        if (!PAGE_READY.get()) {
                            return;
                        }
                        GitReviewService.sendGitDiffRequest(path, a.getProject());
                        return;
                    }
                    OverlayUtils.showInfoBalloon(AICodeLanguageInfo.H("醿吸呹盀旲亥乯讈寜"), locationOnScreen);
                    return;
                }
                OverlayUtils.showInfoBalloon(PropertyUtils.H("禭助盏斛亻丗讈宺"), locationOnScreen);
            }
        });
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void update(@NotNull AnActionEvent a) {
        if (a == null) {
            m29enum(1);
        }
        if (AICodeSettingsState.getInstance().permissions.contains(PermissionEnum.REVIEW.getPermission())) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Presentation presentation = a.getPresentation();
                if (!StringUtils.isBlank(PluginStartupActivity.getApiKey())) {
                    if (((Change[]) a.getData(VcsDataKeys.CHANGES)) != null) {
                        if (!PREPUSH_REVIEW_BUTTON.get()) {
                            presentation.setIcon(Icons.getCurrentIcon());
                            if (getSelectedChange(a).size() != 1) {
                                presentation.setEnabled(false);
                                presentation.setText(PropertyUtils.H("计光遂丱乍丰旋仭"));
                                return;
                            } else {
                                presentation.setText(AICodeLanguageInfo.H("亖硿飋變寞"));
                                presentation.setEnabled(true);
                                return;
                            }
                        }
                        presentation.setIcon(Icons.StatusBarCompletionInProgress);
                        return;
                    }
                    presentation.setEnabled(false);
                    return;
                }
                presentation.setEnabled(false);
            });
        } else {
            a.getPresentation().setEnabledAndVisible(false);
        }
    }
}
