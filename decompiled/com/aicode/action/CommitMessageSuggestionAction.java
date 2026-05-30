package com.aicode.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.aicode.PluginStartupActivity;
import com.aicode.action.click.PluginAnAction;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.enums.PermissionEnum;
import com.aicode.content.util.OverlayUtils;
import com.aicode.icons.Icons;
import com.aicode.service.editor.CancelRequestTip;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.util.StringUtils;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.CommitMessageI;
import com.intellij.openapi.vcs.FileStatus;
import com.intellij.openapi.vcs.VcsDataKeys;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.ContentRevision;
import com.intellij.openapi.vcs.changes.CurrentContentRevision;
import com.intellij.openapi.vcs.ui.CommitMessage;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.AnimatedIcon;
import com.intellij.ui.EditorTextField;
import com.intellij.vcs.commit.AbstractCommitWorkflowHandler;
import com.intellij.vcs.commit.CommitWorkflowUi;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/CommitMessageSuggestionAction.class */
public class CommitMessageSuggestionAction extends PluginAnAction {
    public static final AtomicBoolean COMMIT_MESSAGE_BUTTON = new AtomicBoolean(false);
    public static final Map<String, EditorTextField> COMMIT_MESSAGE_MAP = new ConcurrentHashMap();

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m19enum(int a) {
        String H = CancelRequestTip.H("(\u001b\u000e\u001c��\b\u001e\u0004i/\u000e\u0013W79\u0018\u0011+\u0016\u000f\rA'6\u0003\u0010\u000e\u0006\u0017\u0006\u0013APR\u0003WM\u0002\rKO\u0019k`\u0014G\u001d\u0005\u001e\u0019K\u0005\u0005\u001eA\u0003\u000fJ\u001f\u0004\u0005\u0005");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            case 2:
            default:
                objArr[0] = OverlayUtils.H("\t1\u000f(\u001f");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = CancelRequestTip.H("\f");
                break;
        }
        objArr[1] = OverlayUtils.H("\u0012\n%b\u00012\u00154\u0012,K.\u00019\t\u00148r3 \u000f\"\u000b9->\u0005/\u0010&\t\u0014\u001f!\f\f7?\u000f3\u001f��\u000f3\u0003)\u0005");
        switch (a) {
            case 0:
            default:
                objArr[2] = CancelRequestTip.H("\u0011\u000e\u0019\u0002\u0004\u0004:\u0004\u0013\f\u0005\u0003\u001c\f\r");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = OverlayUtils.H("4\u001c#\u000b2\u000e");
                break;
            case 2:
                objArr[2] = CancelRequestTip.H("\r\u000f\u0015\"\u0002\u000b\u001f\u0016\f\u001a");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void update(@NotNull AnActionEvent a) {
        if (a == null) {
            m19enum(1);
        }
        if (AICodeSettingsState.getInstance().permissions.contains(PermissionEnum.GENERATE_COMMIT.getPermission())) {
            ApplicationManager.getApplication().invokeLater(() -> {
                Presentation presentation = a.getPresentation();
                if (!StringUtils.isBlank(PluginStartupActivity.getApiKey())) {
                    if (((CommitMessageI) a.getData(VcsDataKeys.COMMIT_MESSAGE_CONTROL)) != null) {
                        if (COMMIT_MESSAGE_BUTTON.get()) {
                            presentation.setIcon(Icons.StatusBarCompletionInProgress);
                            return;
                        }
                        List<Change> changes = getChanges(a);
                        presentation.setIcon(Icons.getCurrentIcon());
                        presentation.setEnabled(!changes.isEmpty());
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

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public List<Change> getChanges(@NotNull AnActionEvent a) {
        if (a == null) {
            m19enum(2);
        }
        ArrayList arrayList = new ArrayList();
        AbstractCommitWorkflowHandler abstractCommitWorkflowHandler = (AbstractCommitWorkflowHandler) a.getData(VcsDataKeys.COMMIT_WORKFLOW_HANDLER);
        if (abstractCommitWorkflowHandler == null) {
            return arrayList;
        }
        CommitWorkflowUi ui = abstractCommitWorkflowHandler.getUi();
        List includedChanges = ui.getIncludedChanges();
        List list = (List) ui.getIncludedUnversionedFiles().stream().map(a2 -> {
            return new Change((ContentRevision) null, new CurrentContentRevision(a2));
        }).collect(Collectors.toList());
        arrayList.addAll(includedChanges);
        arrayList.addAll(list);
        return arrayList;
    }

    public void actionPerformed(@NotNull AnActionEvent a) {
        if (a == null) {
            m19enum(0);
        }
        ApplicationManager.getApplication().invokeLater(() -> {
            CommitMessage commitMessage;
            Project project = a.getProject();
            if (project != null && !(a.getPresentation().getIcon() instanceof AnimatedIcon) && (commitMessage = (CommitMessage) a.getData(VcsDataKeys.COMMIT_MESSAGE_CONTROL)) != null) {
                Point locationOnScreen = a.getInputEvent().getLocationOnScreen();
                locationOnScreen.y += 5;
                List<Change> changes = getChanges(a);
                if (CollUtil.isEmpty(changes)) {
                    OverlayUtils.showInfoBalloon(CancelRequestTip.H("讲劙遮厨暄盩于硪呥菝厼掭仙侃怍"), locationOnScreen);
                    return;
                }
                Map<String, LinkedHashSet<String>> SD = SD(changes);
                LinkedHashSet<String> linkedHashSet = SD.get(OverlayUtils.H("��5\u001d$<&\u001e.\u0018"));
                LinkedHashSet<String> linkedHashSet2 = SD.get(CancelRequestTip.H("\u000e\u0019\u0018\u0005\u000f0\u0011\u0005"));
                if (CollUtil.isEmpty(linkedHashSet)) {
                    OverlayUtils.showInfoBalloon(String.join(OverlayUtils.H("{\b4U"), linkedHashSet2), locationOnScreen);
                    return;
                }
                COMMIT_MESSAGE_BUTTON.set(true);
                EditorTextField editorField = commitMessage.getEditorField();
                editorField.setText("");
                String fastSimpleUUID = IdUtil.fastSimpleUUID();
                COMMIT_MESSAGE_MAP.put(fastSimpleUUID, editorField);
                MessageDto messageDto = new MessageDto(fastSimpleUUID, CommandEnum.GIT_COMMIT_MESSAGE.getType());
                messageDto.setData(linkedHashSet);
                PluginWebsocketClient.sendWsMessage(messageDto, project);
            }
        });
    }

    public CommitMessageSuggestionAction(@Nullable String text, @Nullable String a) {
        super(text, a, Icons.ToolWindowIcon);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private Map<String, LinkedHashSet<String>> SD(List<Change> list) {
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList(Arrays.asList(OverlayUtils.H("\u000b/"), CancelRequestTip.H("\u001d\u0004\u000f\u000e"), OverlayUtils.H(".\t("), CancelRequestTip.H("\u0013\u0018\u0003\u0018"), OverlayUtils.H("2\n8"), CancelRequestTip.H("\u000f\u001b\u0013\u0018"), OverlayUtils.H(":\u0016?"), CancelRequestTip.H("\u0007\u0007\u0014\u0018"), OverlayUtils.H(">\u001e?")));
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        Iterator<Change> it = list.iterator();
        while (it.hasNext()) {
            Change next = it.next();
            if (FileStatus.ADDED.equals(next.getFileStatus())) {
                it = it;
                linkedHashSet.add(CancelRequestTip.H("旀壳旪亝书畵扺掇仳価怯"));
            } else if (!next.isMoved()) {
                if (!next.isRenamed()) {
                    VirtualFile virtualFile = next.getVirtualFile();
                    if (virtualFile != null) {
                        String path = virtualFile.getPath();
                        if (!arrayList.contains(path.substring(path.lastIndexOf(CancelRequestTip.H("n")) + 1).toUpperCase())) {
                            linkedHashSet2.add(path);
                            it = it;
                        } else {
                            it = it;
                            linkedHashSet.add(OverlayUtils.H("旮杨簰旡亪乼畞扼掗从侧怄"));
                        }
                    } else {
                        it = it;
                        linkedHashSet.add(OverlayUtils.H("剤阯旡亪乼畞扼掗从侧怄"));
                    }
                } else {
                    it = it;
                    linkedHashSet.add(CancelRequestTip.H("醽名呠旪亝书畵扺掇仳価怯"));
                }
            } else {
                it = it;
                linkedHashSet.add(OverlayUtils.H("禿勣旡亪乼畞扼掗从侧怄"));
            }
        }
        hashMap.put(CancelRequestTip.H("\u000e\u0019\u0018\u0005%\u001a3'"), linkedHashSet);
        hashMap.put(OverlayUtils.H("��5\u001d$<&\u001e.\u0018"), linkedHashSet2);
        return hashMap;
    }
}
