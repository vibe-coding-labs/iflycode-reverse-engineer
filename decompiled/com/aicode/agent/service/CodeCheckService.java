/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.bean.BeanUtil
 *  cn.hutool.core.util.IdUtil
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.intellij.openapi.project.Project
 *  org.apache.commons.collections.CollectionUtils
 */
package com.aicode.agent.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.aicode.action.click.CodeCheckAction;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.dto.CodeCheckDto;
import com.aicode.agent.dto.CodeCheckFixDto;
import com.aicode.agent.dto.CodeCheckListDto;
import com.aicode.agent.dto.CodeCheckOriginDto;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.ResponseDto;
import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.enums.WebViewDataTypeEnum;
import com.aicode.inline.ide.IdeAction;
import com.aicode.message.BasicActionsBundle;
import com.aicode.util.StringUtils;
import com.aicode.view.WebViewWindowPanel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.intellij.openapi.project.Project;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class CodeCheckService {
    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static void handleAction(WebViewWindowPanel webViewWindowPanel, WebViewDataTypeEnum webViewDataTypeEnum, JsonObject jsonObject, Project project) {
        Object a = webViewDataTypeEnum;
        WebViewWindowPanel a2 = webViewWindowPanel;
        switch (Da.byte[((Enum)a).ordinal()]) {
            case 1: {
                void a3;
                CodeCheckService.sendCodeCheck((Project)a3);
                return;
            }
            case 2: {
                void a4;
                void a3;
                a = CodeCheckService.fixCodeCheck((JsonObject)a4, (Project)a3);
                if (a == null) return;
                a2.sendMessage2webView(a);
                return;
            }
        }
    }

    public static void sendCodeCheck(Project project) {
        Object a;
        Project project2 = project;
        if (StringUtils.isBlank((CharSequence)CodeCheckAction.path)) {
            return;
        }
        if (StringUtils.isBlank((CharSequence)CodeCheckAction.content)) {
            return;
        }
        Object object = a = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.CODE_CHECK.getType());
        ((MessageDto)object).setPath(CodeCheckAction.path);
        ((MessageDto)object).setContent(CodeCheckAction.content);
        PluginWebsocketClient.sendWsMessage((MessageDto)object, project2);
    }

    public static CodeCheckListDto getErrorList(String string) {
        String string2 = string;
        CodeCheckListDto codeCheckListDto = new CodeCheckListDto();
        codeCheckListDto.setType(WebViewDataTypeEnum.CODE_CHECK_GET_CODE_CHECK_LIST.getType());
        CodeCheckListDto.ValueDTO a = new CodeCheckListDto.ValueDTO();
        a.setStatus(false);
        string2 = StringUtils.isBlank((CharSequence)string2) ? BasicActionsBundle.message(IdeAction.H("*m\u0006LAG\u0001G\u0002Aww\u0002T\fJRT\fF\u001dG\u0001P"), new Object[0]) : string2;
        CodeCheckListDto codeCheckListDto2 = codeCheckListDto;
        a.setMessage(string2);
        codeCheckListDto2.setValue(a);
        return codeCheckListDto2;
    }

    public static JsonObject getErrorResponse(String string, String string2) {
        String a;
        String string3 = string;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(IdeAction.H("\u001d[\u001fA"), WebViewDataTypeEnum.CODE_CHECK_UPDATE_CODE_CHECK.getType());
        String a2 = new JsonObject();
        jsonObject.add(IdeAction.H("^\bN\u001aA"), (JsonElement)a2);
        a2.addProperty(IdeAction.H("\u0006@"), string3);
        a = StringUtils.isBlank((CharSequence)a) ? "" : a;
        String string4 = a2;
        a2.addProperty(IdeAction.H("\u000eR\u0010X\u0006L\u001cA"), a);
        string4.addProperty(IdeAction.H("\u001dA\u000bC\u0013Y\u0010M=[\u001fA"), IdeAction.H("M\u001bP\u0000V"));
        string4.addProperty(IdeAction.H("\u0006W1].R\u0010X\u0006L\u001cA"), Boolean.valueOf(false));
        return jsonObject;
    }

    public static CodeCheckListDto getErrorListResult(ResponseDto a) {
        try {
            return CodeCheckService.getErrorList(a.getMsg());
        }
        catch (Exception exception) {
            return CodeCheckService.getErrorList(null);
        }
    }

    /*
     * WARNING - void declaration
     */
    public static JsonObject fixCodeCheck(JsonObject jsonObject, Project project) {
        void a;
        Object a2;
        block7: {
            CodeCheckFixDto codeCheckFixDto;
            block6: {
                JsonObject jsonObject2 = jsonObject;
                try {
                    codeCheckFixDto = (CodeCheckFixDto)new Gson().fromJson((JsonElement)jsonObject2, CodeCheckFixDto.class);
                    if (codeCheckFixDto != null) break block6;
                    return null;
                }
                catch (Exception exception) {
                    return null;
                }
            }
            a2 = codeCheckFixDto.getValue();
            if (a2 != null) break block7;
            return null;
        }
        String string = ((CodeCheckFixDto.ValueDTO)a2).getId();
        if (StringUtils.isBlank((CharSequence)string)) {
            return null;
        }
        Object object = a2;
        a2 = ((CodeCheckFixDto.ValueDTO)object).getErrorMessage();
        CodeInfoDto codeInfoDto = ((CodeCheckFixDto.ValueDTO)object).getCodeInfo();
        Object object2 = codeInfoDto.getPath();
        if (codeInfoDto == null || CollectionUtils.isEmpty(codeInfoDto.getRange()) || StringUtils.isBlank((CharSequence)object2) || StringUtils.isBlank((CharSequence)a2)) {
            return CodeCheckService.getErrorResponse(string, "");
        }
        Object object3 = object2 = new MessageDto();
        Object object4 = object2;
        ((MessageDto)object4).setId(string);
        ((MessageDto)object4).setCommand(CommandEnum.CODE_DEBUG_DUPLICATE.getType());
        ((MessageDto)object3).setRange(codeInfoDto.getRange());
        ((MessageDto)object2).setPath(codeInfoDto.getPath());
        JsonObject jsonObject3 = new JsonObject();
        jsonObject3.addProperty(IdeAction.H("Z\u0006[\u001aC\bA"), (String)a2);
        ((MessageDto)object3).setData(jsonObject3);
        PluginWebsocketClient.sendWsMessage((MessageDto)object2, (Project)a);
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static CodeCheckListDto getCheckData(JsonObject jsonObject) {
        JsonObject jsonObject2 = jsonObject;
        try {
            CodeCheckOriginDto codeCheckOriginDto = (CodeCheckOriginDto)new Gson().fromJson((JsonElement)jsonObject2.getAsJsonObject(IdeAction.H("\rC\u001bE")), CodeCheckOriginDto.class);
            String string = codeCheckOriginDto.getPath();
            String string2 = codeCheckOriginDto.getName();
            assert (string != null);
            Object a = codeCheckOriginDto.getErrList();
            ArrayList<CodeCheckDto> arrayList = new ArrayList<CodeCheckDto>();
            if (CollectionUtils.isEmpty((Collection)a)) {
                return CodeCheckService.getList(arrayList);
            }
            Object object = a = a.iterator();
            while (object.hasNext()) {
                CodeCheckOriginDto.ErrListDTO errListDTO = (CodeCheckOriginDto.ErrListDTO)a.next();
                CodeCheckDto codeCheckDto = new CodeCheckDto();
                BeanUtil.copyProperties((Object)errListDTO, (Object)codeCheckDto, (String[])new String[0]);
                List<CodeInfoDto.RangeDTO> list = errListDTO.getRange();
                if (CollectionUtils.isEmpty(list)) {
                    object = a;
                    continue;
                }
                CodeInfoDto codeInfoDto = new CodeInfoDto();
                object = a;
                CodeCheckDto codeCheckDto2 = codeCheckDto;
                CodeInfoDto codeInfoDto2 = codeInfoDto;
                CodeInfoDto codeInfoDto3 = codeInfoDto;
                codeInfoDto3.setPath(string);
                codeInfoDto3.setFileName(string2);
                codeInfoDto2.setContent(errListDTO.getCodeFragment());
                codeInfoDto2.setRange(list);
                codeCheckDto2.setCodeInfo(codeInfoDto2);
                arrayList.add(codeCheckDto2);
            }
            if (CollectionUtils.isEmpty(arrayList)) {
                return CodeCheckService.getErrorList(null);
            }
            return CodeCheckService.getList(arrayList);
        }
        catch (Error error) {
            return CodeCheckService.getErrorList(null);
        }
    }

    /*
     * Unable to fully structure code
     */
    public static JsonObject getAgentChatResponse(JsonObject var0, MessageDto var1_1) {
        var3_2 = var0;
        if (!var3_2.has(IdeAction.H("\rC\u001bE"))) {
            return null;
        }
        var4_3 = new JsonObject();
        var4_3.addProperty(IdeAction.H("\u001d[\u001fA"), WebViewDataTypeEnum.CODE_CHECK_UPDATE_CODE_CHECK.getType());
        a = new JsonObject();
        var4_3.add(IdeAction.H("^\bN\u001aA"), (JsonElement)a);
        a.addProperty(IdeAction.H("\u0006@"), a.getId());
        var2_4 = var3_2.get(IdeAction.H("\rC\u001bE")).getAsJsonObject();
        a.addProperty(IdeAction.H("\u001dA\u000bC\u0013Y\u0010M=[\u001fA"), IdeAction.H("\u001dG\u0017P"));
        if (var2_4.has(IdeAction.H("\u001dG\u0017P"))) {
            a.addProperty(IdeAction.H("\u000eR\u0010X\u0006L\u001cA"), var2_4.get(IdeAction.H("\u001dG\u0017P")).getAsString());
        }
        if (!var2_4.has(IdeAction.H("M\u001bP\u0000V"))) ** GOTO lbl26
        try {
            var5_6 = var2_4.get(IdeAction.H("M\u001bP\u0000V")).getAsJsonObject();
            a.addProperty(IdeAction.H("\u000eR\u0010X\u0006L\u001cA"), var5_6.get(IdeAction.H("Z\u0006[\u001aC\bA")).getAsString());
            v0 = a;
            ** GOTO lbl23
        }
        catch (Exception var5_7) {
            try {
                v0 = a;
lbl23:
                // 2 sources

                v0.addProperty(IdeAction.H("\u001dA\u000bC\u0013Y\u0010M=[\u001fA"), IdeAction.H("M\u001bP\u0000V"));
                PluginWebsocketClient.AGENT_REQUEST.remove(a.getId());
lbl26:
                // 2 sources

                v1 = a;
                if (var2_4.has(IdeAction.H("M\u0007F\n@"))) {
                    v1.addProperty(IdeAction.H("\u0006W1].R\u0010X\u0006L\u001cA"), Boolean.valueOf(false));
                    PluginWebsocketClient.AGENT_REQUEST.remove(a.getId());
                    return var4_3;
                }
                v1.addProperty(IdeAction.H("\u0006W1].R\u0010X\u0006L\u001cA"), Boolean.valueOf(true));
                return var4_3;
            }
            catch (Exception var2_5) {
                return var4_3;
            }
        }
    }

    public CodeCheckService() {
        CodeCheckService a;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static void handleAgentAction(CommandEnum commandEnum, JsonObject jsonObject, MessageDto messageDto, String string, Project project) {
        CommandEnum a = jsonObject;
        CommandEnum a2 = commandEnum;
        switch (a2) {
            case CODE_CHECK: {
                void a3;
                CodeCheckListDto a4;
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                a4 = CodeCheckService.getCheckData((JsonObject)a);
                SocketMessageHandleListener.send2Web((Project)a3, a4);
                return;
            }
            case CODE_DEBUG_DUPLICATE: {
                void a5;
                void a3;
                a = CodeCheckService.getAgentChatResponse((JsonObject)a, (MessageDto)a5);
                SocketMessageHandleListener.send2Web((Project)a3, (Object)a);
                return;
            }
        }
    }

    public static CodeCheckListDto getList(List<CodeCheckDto> list) {
        Object a;
        List<CodeCheckDto> list2 = list;
        CodeCheckListDto codeCheckListDto = new CodeCheckListDto();
        codeCheckListDto.setType(WebViewDataTypeEnum.CODE_CHECK_GET_CODE_CHECK_LIST.getType());
        Object object = a = new CodeCheckListDto.ValueDTO();
        ((CodeCheckListDto.ValueDTO)object).setStatus(true);
        ((CodeCheckListDto.ValueDTO)object).setData(list2);
        codeCheckListDto.setValue((CodeCheckListDto.ValueDTO)object);
        return codeCheckListDto;
    }
}
