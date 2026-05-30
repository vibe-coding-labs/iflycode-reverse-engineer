package com.aicode.test;

import cn.hutool.core.util.IdUtil;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.enums.PageEnum;
import com.aicode.agent.service.CodeCompleteService;
import com.aicode.agent.service.CommonService;
import com.aicode.content.util.file.FileExtensionLanguageDetails;
import com.aicode.enums.LanguageEnum;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.test.dto.UnitTestAgentDto;
import com.aicode.test.dto.UnitTestDto;
import com.aicode.view.WebViewWindowPanel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;

/* compiled from: qc */
@Service
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/CppTestService.class */
public final class CppTestService {

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f624enum = Logger.getInstance(UnitTestService.class);

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void resolveCppTest(Project a, Editor a2, String a3, PsiElement a4) {
        int selectionStart;
        int selectionEnd;
        Project project;
        MessageDto messageDto;
        if (a4 != null) {
            TextRange textRange = a4.getTextRange();
            project = a;
            selectionStart = textRange.getStartOffset();
            selectionEnd = textRange.getEndOffset();
        } else {
            selectionStart = a2.getSelectionModel().getSelectionStart();
            selectionEnd = a2.getSelectionModel().getSelectionEnd();
            project = a;
        }
        if (PsiDocumentManager.getInstance(project).getPsiFile(a2.getDocument()) == null) {
            UnitTestService.notice(a);
            return;
        }
        VirtualFile virtualFile = ((EditorImpl) a2).getVirtualFile();
        String path = virtualFile.getPath();
        if (ModuleUtilCore.findModuleForFile(virtualFile, a) == null) {
            return;
        }
        Document document = a2.getDocument();
        int lineNumber = document.getLineNumber(selectionStart);
        int lineStartOffset = selectionStart - document.getLineStartOffset(lineNumber);
        int lineNumber2 = document.getLineNumber(selectionEnd);
        int lineStartOffset2 = selectionEnd - document.getLineStartOffset(lineNumber2);
        CodeInfoDto.RangeDTO rangeDTO = new CodeInfoDto.RangeDTO();
        rangeDTO.setLine(Integer.valueOf(lineNumber));
        rangeDTO.setCharacter(Integer.valueOf(lineStartOffset));
        CodeInfoDto.RangeDTO rangeDTO2 = new CodeInfoDto.RangeDTO();
        rangeDTO2.setLine(Integer.valueOf(lineNumber2));
        rangeDTO2.setCharacter(Integer.valueOf(lineStartOffset2));
        ArrayList arrayList = new ArrayList();
        arrayList.add(rangeDTO);
        arrayList.add(rangeDTO2);
        MessageDto messageDto2 = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.CODE_TEST_ANALYSIS.getType());
        messageDto2.setPath(path);
        messageDto2.setLang(a3);
        messageDto2.setRange(arrayList);
        JsonObject jsonObject = new JsonObject();
        AICodeSettingsState aICodeSettingsState = AICodeSettingsState.getInstance();
        if (StringUtils.equalsIgnoreCase(LanguageEnum.PYTHON_LANGUAGE_01.getDescription(), a3) && aICodeSettingsState.pyModifyTestFrame) {
            messageDto = messageDto2;
            jsonObject.addProperty(CodeCompleteService.H("m}N]JM{Ux"), aICodeSettingsState.pyTestFramework);
            jsonObject.addProperty(FileExtensionLanguageDetails.H("mygb]qphr"), aICodeSettingsState.pyMockFramework);
        } else {
            jsonObject.addProperty(CodeCompleteService.H("m}N]JM{Ux"), FileExtensionLanguageDetails.H("BDQX"));
            jsonObject.addProperty(CodeCompleteService.H("tw^BJM{Ux"), FileExtensionLanguageDetails.H("BDQX"));
            messageDto = messageDto2;
        }
        messageDto.setPid(CodeCompleteService.H("NiaONxH|MS@xk\u007fKi"));
        messageDto2.setData(jsonObject);
        a.putUserData(WebViewWindowPanel.UNIT_TEST_MESSAGE_DATA, messageDto2);
        CommonService.openPage(a, PageEnum.UNIT_TEST);
    }

    public static void resolveFunctionCase(Project a, UnitTestDto.DataDTO.FunctionDataDTO a2) {
        MessageDto messageDto = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.TEST_MAKE_CASE.getType());
        messageDto.setId(a2.getId());
        messageDto.setPath(a2.getPath());
        messageDto.setLang(a2.getLanguage());
        messageDto.setText(new StringBuffer());
        JsonObject jsonObject = new JsonObject();
        UnitTestDto.DataDTO.FunctionDataDTO.Data data = a2.getData();
        if (StringUtils.equalsIgnoreCase(LanguageEnum.PYTHON_LANGUAGE_01.getDescription(), a2.getLanguage()) && data.isModifyTestFrame()) {
            AICodeSettingsState.getInstance().pyTestFramework = data.getTestFrame();
            AICodeSettingsState.getInstance().pyMockFramework = data.getMockFrame();
            AICodeSettingsState.getInstance().pyModifyTestFrame = data.isModifyTestFrame();
        }
        jsonObject.addProperty(FileExtensionLanguageDetails.H("zoqtdz"), Boolean.valueOf(data.isStream()));
        jsonObject.addProperty(CodeCompleteService.H("QePraf|Oc}RxL~"), 6);
        jsonObject.addProperty(FileExtensionLanguageDetails.H("`~ar"), data.getCode());
        jsonObject.addProperty(CodeCompleteService.H("sjYYNM{Di"), data.getTestFrame());
        jsonObject.addProperty(FileExtensionLanguageDetails.H("mygb]qphr"), data.getMockFrame());
        jsonObject.addProperty(CodeCompleteService.H("t{XXkKo[i"), data.getStructure());
        messageDto.setData(jsonObject);
        PluginWebsocketClient.sendWsMessage(messageDto, a);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void cppTestAnalysis(JsonObject a, MessageDto a2, Project a3) {
        UnitTestAgentDto unitTestAgentDto = (UnitTestAgentDto) new Gson().fromJson(a.get(FileExtensionLanguageDetails.H("gpqv")), UnitTestAgentDto.class);
        UnitTestDto.DataDTO dataDTO = new UnitTestDto.DataDTO();
        dataDTO.setLanguage(a2.getLang());
        dataDTO.setPath(a2.getPath());
        dataDTO.setClassName(unitTestAgentDto.getName());
        dataDTO.setStructure(unitTestAgentDto.getStructure());
        AICodeSettingsState aICodeSettingsState = AICodeSettingsState.getInstance();
        if (aICodeSettingsState.pyModifyTestFrame && StringUtils.equalsIgnoreCase(LanguageEnum.PYTHON_LANGUAGE_01.getDescription(), a2.getLang()) && StringUtils.equals(CodeCompleteService.H("jMLb_iT``~VnqeLn"), a2.getPid()) && aICodeSettingsState.pyModifyTestFramenNum.intValue() == 0) {
            dataDTO.setTestFrameAlert(true);
            aICodeSettingsState.pyModifyTestFramenNum = Integer.valueOf(aICodeSettingsState.pyModifyTestFramenNum.intValue() + 1);
        }
        dataDTO.setTestFrame(unitTestAgentDto.getTestFrame());
        dataDTO.setMockFrame(unitTestAgentDto.getMockFrame());
        ArrayList arrayList = new ArrayList();
        Iterator<UnitTestAgentDto.method> it = unitTestAgentDto.getMethods().iterator();
        while (it.hasNext()) {
            UnitTestAgentDto.method next = it.next();
            UnitTestDto.DataDTO.FunctionDataDTO functionDataDTO = new UnitTestDto.DataDTO.FunctionDataDTO();
            it = it;
            functionDataDTO.setId(IdUtil.fastSimpleUUID());
            functionDataDTO.setFunctionName(next.getName());
            functionDataDTO.setCode(next.getCode());
            functionDataDTO.setRange(next.getRange());
            arrayList.add(functionDataDTO);
        }
        dataDTO.setFunctionData(arrayList);
        JsonObject a4 = UnitTestService.receiveFunction(dataDTO);
        SocketMessageHandleListener.send2Web(a3, a4);
    }

    public static void getTestCode(Project a, UnitTestDto.DataDTO.FunctionDataDTO a2) {
        MessageDto messageDto = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.TEST_MAKE_CODE.getType());
        messageDto.setId(IdUtil.fastSimpleUUID());
        messageDto.setPid(a2.getId());
        messageDto.setPath(a2.getPath());
        messageDto.setLang(a2.getLanguage());
        messageDto.setText(new StringBuffer());
        messageDto.setData(a2.getData());
        PluginWebsocketClient.sendWsMessage(messageDto, a);
    }
}
