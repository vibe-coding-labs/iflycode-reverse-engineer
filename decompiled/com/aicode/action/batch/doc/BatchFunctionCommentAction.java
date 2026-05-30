package com.aicode.action.batch.doc;

import cn.hutool.core.util.IdUtil;
import com.aicode.PluginStartupActivity;
import com.aicode.action.click.PluginAnAction;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.agent.dto.chat.FirstChatMessage;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.enums.PageEnum;
import com.aicode.agent.enums.PermissionEnum;
import com.aicode.agent.service.ChatService;
import com.aicode.agent.service.CommonService;
import com.aicode.agent.service.UserService;
import com.aicode.content.util.file.FileUtils;
import com.aicode.enums.AssistantTypeEnum;
import com.aicode.enums.WebViewDataTypeEnum;
import com.aicode.icons.Icons;
import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.util.ApplicationUtil;
import com.aicode.util.IndentLineUtil;
import com.aicode.util.PositionUtil;
import com.aicode.util.StringUtils;
import com.aicode.view.WebViewWindowPanel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.impl.VirtualFileImpl;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: pm */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/batch/doc/BatchFunctionCommentAction.class */
public class BatchFunctionCommentAction extends PluginAnAction {

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f107enum = LoggerFactory.getLogger(BatchFunctionCommentAction.class);

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m50enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 1:
            default:
                H = InlineChatStatusServiceKt.H("GQ+\u001c*\u0007%\u001a&E\u0005=i,\u0012\u0016? 5\t\u0002k-\u00194\u0002&\u000b(\u001c3D`G5DK!!B`\u0013kE>H+\u0016/\rI\"$\u001a|\u001b$D)\u0017*\u000f");
                i = a;
                break;
            case 2:
            case 3:
            case 4:
                do {
                } while (0 != 0);
                H = IndentLineUtil.H("\u001cM��D:^\u0015JN\\\u0016X\u001dE\u0010\u000b|u[\u000f\u0004\b\u001a]\fTTE\u0001E{v\u001cR\u001bC\u001d\f\u001b_\u0018G");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            default:
                i2 = 3;
                break;
            case 2:
            case 3:
            case 4:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            case 1:
            default:
                objArr[0] = InlineChatStatusServiceKt.H("\u0006");
                i3 = a;
                break;
            case 2:
            case 3:
            case 4:
                do {
                } while (0 != 0);
                objArr[0] = IndentLineUtil.H("W\u00045(\u001aM\r^\u001dC]L?w\u0006_\u001a\u0004\u001bG\u001aR\u001b\u0003\u0011E\u0017\u0004\u001bg\u0001I\u001fn\u0002F\u001cT\u001dD��r4i\u0014C��E2O\u0001C\u001bE");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            default:
                objArr[1] = InlineChatStatusServiceKt.H("eL\u0007`(\u0005?\u0016/\u000bo\u0004\r?4\u0017(L)\u000f(\u001a)K#\r%L)/3\u0001-&0\u000e.\u001c/\f2:\u0006!&\u000b2\r��\u00073\u000b)\r");
                i4 = a;
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[1] = IndentLineUtil.H("\tT/G\u0011G\u001a|\u0016_\u0006K\u0013N");
                i4 = a;
                break;
            case 3:
                objArr[1] = InlineChatStatusServiceKt.H("\u001b\u001c%'\n\u001f\u0016%\u0001\u000e\f \f");
                i4 = a;
                break;
            case 4:
                objArr[1] = IndentLineUtil.H("O\u0012\\>C��B\u0001_\u000et\u001dG\u001aT'D\u0007O\u0015O");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            default:
                objArr[2] = InlineChatStatusServiceKt.H("4\u0014#\u00032\u0006");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = IndentLineUtil.H("J\rE2k\u0017v\u000bC\u0015C\u0007G\u0011O");
                break;
            case 2:
            case 3:
            case 4:
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            default:
                throw new IllegalArgumentException(format);
            case 2:
            case 3:
            case 4:
                throw new IllegalStateException(format);
        }
    }

    @Override // com.aicode.action.click.PluginAnAction
    @NotNull
    public ActionUpdateThread getActionUpdateThread() {
        ActionUpdateThread actionUpdateThread = ActionUpdateThread.BGT;
        if (actionUpdateThread == null) {
            m50enum(4);
        }
        return actionUpdateThread;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private FirstChatMessage Xe(VirtualFile a, Project a2) {
        try {
            CodeInfoDto fileCodeInfo = getFileCodeInfo(a);
            JsonArray jsonArray = new JsonArray();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(IndentLineUtil.H("\u0001S\u0004N"), InlineChatStatusServiceKt.H("\u000f/\n(\u00173\u0003(\u0017"));
            jsonObject.addProperty(IndentLineUtil.H("Z\u0014F\u0001N"), AssistantTypeEnum.IFLY_MATE.getType());
            jsonArray.add(jsonObject);
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty(InlineChatStatusServiceKt.H("3\u001b6\u0006"), IndentLineUtil.H("R\u001cA\u0018K\u001aO"));
            jsonObject2.addProperty(InlineChatStatusServiceKt.H("\u0012&\u000e3\u0006"), CommandEnum.CODE_COMMENT.getType());
            jsonArray.add(jsonObject2);
            return He(fileCodeInfo, jsonArray, a2);
        } catch (Exception e) {
            f107enum.error(IndentLineUtil.H("菎台早仇亐砭侔恅奅贎"), e);
            return null;
        }
    }

    @NotNull
    public static CodeInfoDto buildCodeInfo(VirtualFile a, PsiFile a2, Project a3) {
        CodeInfoDto codeInfoDto = new CodeInfoDto();
        codeInfoDto.setContent(a2.getText());
        codeInfoDto.setAllContent(a2.getText());
        codeInfoDto.setLanguage(FileUtils.getFileExtension(a2.getName()));
        codeInfoDto.setFileName(a2.getName());
        codeInfoDto.setPath(a.getPath());
        CodeInfoDto.RangeDTO rangeDTO = new CodeInfoDto.RangeDTO();
        int[] startLineAndColumn = PositionUtil.getStartLineAndColumn(a3, a2);
        rangeDTO.setLine(Integer.valueOf(startLineAndColumn[0]));
        rangeDTO.setCharacter(Integer.valueOf(startLineAndColumn[1]));
        CodeInfoDto.RangeDTO rangeDTO2 = new CodeInfoDto.RangeDTO();
        int[] endLineAndColumn = PositionUtil.getEndLineAndColumn(a3, a2);
        rangeDTO2.setLine(Integer.valueOf(endLineAndColumn[0]));
        rangeDTO2.setCharacter(Integer.valueOf(endLineAndColumn[1]));
        ArrayList arrayList = new ArrayList();
        arrayList.add(rangeDTO);
        arrayList.add(rangeDTO2);
        codeInfoDto.setRange(arrayList);
        if (codeInfoDto == null) {
            m50enum(3);
        }
        return codeInfoDto;
    }

    public BatchFunctionCommentAction(@Nullable String text, @Nullable String a) {
        super(text, a, Icons.ToolWindowIcon);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void update(@NotNull AnActionEvent a) {
        if (a == null) {
            m50enum(0);
        }
        if (AICodeSettingsState.getInstance().permissions.contains(PermissionEnum.DOC_COMMENTS.getPermission())) {
            VirtualFile[] virtualFileArr = (VirtualFile[]) a.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY);
            a.getPresentation().setEnabledAndVisible((virtualFileArr == null || virtualFileArr.length != 1 || virtualFileArr[0].isDirectory()) ? false : true);
        } else {
            a.getPresentation().setEnabledAndVisible(false);
        }
    }

    private void Pd(Project a, FirstChatMessage a2) {
        PluginStartupActivity.handleExecutorService.execute(() -> {
            CommonService.openPage(a, PageEnum.CHAT_VIEW);
            if (!SocketMessageHandleListener.send2Web(a, a2).booleanValue()) {
                a.putUserData(WebViewWindowPanel.CODE_MESSAGE_DATA, a2);
            } else {
                CommonService.chatMessage2Web(a, a2, true);
            }
        });
    }

    public static CodeInfoDto getFileCodeInfo(VirtualFile a) {
        return (CodeInfoDto) ApplicationManager.getApplication().runReadAction(() -> {
            PsiFile findFile;
            Project findCurrentProject = ApplicationUtil.findCurrentProject();
            if (findCurrentProject != null && (a instanceof VirtualFileImpl) && null != (findFile = PsiManager.getInstance(findCurrentProject).findFile(a))) {
                return buildCodeInfo(a, findFile, findCurrentProject);
            }
            return null;
        });
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void actionPerformed(@NotNull AnActionEvent a) {
        VirtualFile virtualFile;
        FirstChatMessage Xe;
        if (a == null) {
            m50enum(1);
        }
        Project project = a.getProject();
        if (!StringUtils.isBlank(PluginStartupActivity.getApiKey())) {
            if (project == null || (virtualFile = (VirtualFile) a.getData(CommonDataKeys.VIRTUAL_FILE)) == null || (Xe = Xe(virtualFile, project)) == null) {
                return;
            }
            Pd(project, Xe);
            return;
        }
        UserService.showMessage(project);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NotNull
    private static FirstChatMessage He(CodeInfoDto a, JsonArray a2, Project a3) {
        String fastSimpleUUID = IdUtil.fastSimpleUUID();
        String fastSimpleUUID2 = StringUtils.isBlank((CharSequence) ChatService.SESSION_ID.get(a3.getBasePath())) ? IdUtil.fastSimpleUUID() : (String) ChatService.SESSION_ID.get(a3.getBasePath());
        ChatService.SESSION_ID.put(a3.getBasePath(), fastSimpleUUID2);
        FirstChatMessage firstChatMessage = new FirstChatMessage();
        firstChatMessage.setType(WebViewDataTypeEnum.CHAT_UPDATE_CONVERSATION_LIST.getType());
        FirstChatMessage.ValueDTO valueDTO = new FirstChatMessage.ValueDTO();
        valueDTO.setId(fastSimpleUUID);
        valueDTO.setSessionId(fastSimpleUUID2);
        valueDTO.setIntelligent(a2);
        valueDTO.setType(CommandEnum.TALK_INTELLIGENT.getType());
        if (a != null) {
            valueDTO.setCodeInfo(a);
        }
        firstChatMessage.setValue(valueDTO);
        if (firstChatMessage == null) {
            m50enum(2);
        }
        return firstChatMessage;
    }
}
