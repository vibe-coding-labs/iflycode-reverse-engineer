/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.intellij.openapi.project.Project
 */
package com.aicode.agent.service;

import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.ResponseStreamDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.service.GitReviewService;
import com.aicode.enums.WebViewDataTypeEnum;
import com.aicode.service.RequestTipService;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.util.PropertyUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.intellij.openapi.project.Project;

public class CodeCompleteService {
    public static String H(Object object) {
        int a;
        Object object2 = object;
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String string = new StringBuffer(stackTraceElement.getClassName()).append(stackTraceElement.getMethodName()).toString();
        object2 = (String)object2;
        int n = ((String)object2).length();
        int n2 = n - 1;
        char[] cArray = new char[n];
        int n3 = 4 << 4 ^ (3 ^ 5) << 1;
        int cfr_ignored_0 = 2 << 3 ^ 5;
        int n4 = (3 ^ 5) << 4 ^ (2 << 2 ^ 1);
        int n5 = a = string.length() - 1;
        int n6 = n2;
        String string2 = string;
        while (n6 >= 0) {
            int n7 = n2--;
            cArray[n7] = (char)(n4 ^ (((String)object2).charAt(n7) ^ string2.charAt(a)));
            if (n2 < 0) break;
            int n8 = n2--;
            char c = cArray[n8] = (char)(n3 ^ (((String)object2).charAt(n8) ^ string2.charAt(a)));
            if (--a < 0) {
                a = n5;
            }
            n6 = n2;
        }
        return new String(cArray);
    }

    public CodeCompleteService() {
        CodeCompleteService a;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static void handleAgentAction(CommandEnum commandEnum, JsonObject jsonObject, MessageDto messageDto, String string, Project project) {
        CommandEnum a = jsonObject;
        CommandEnum a2 = commandEnum;
        switch (a2) {
            case CODE_COMPLETE: {
                void a3;
                void a4;
                void a5;
                Boolean bl = true;
                RequestTipService requestTipService = RequestTipService.getInstance();
                JsonObject jsonObject2 = a.get(PropertyUtils.H("%w3q")).getAsJsonObject();
                if (!a5.isStream() || jsonObject2.has(GitReviewService.H("=(\u0011 \u0007(\u0002\"\u001f$\u0002"))) {
                    requestTipService.dealAgentTips((String)a4, (JsonObject)a, (Project)a3);
                    return;
                }
                if (a5.isStream()) {
                    ResponseStreamDto responseStreamDto;
                    ResponseStreamDto responseStreamDto2 = responseStreamDto = (ResponseStreamDto)new Gson().fromJson((JsonElement)a, ResponseStreamDto.class);
                    requestTipService.dealStreamAgentTips((String)a4, responseStreamDto2, (Project)a3, (MessageDto)a5);
                    bl = responseStreamDto2.getData().isEnded();
                }
                if (!bl.booleanValue()) return;
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                return;
            }
            case USER_CAN_CODE_ENHANCE: {
                boolean bl;
                void a3;
                void a4;
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                AICodeSettingsState.getInstance().enableCodeEnhance = bl = a.get(PropertyUtils.H("%w3q")).getAsBoolean();
                CommandEnum commandEnum2 = a = new JsonObject();
                commandEnum2.addProperty(GitReviewService.H("?\t:\u0014"), WebViewDataTypeEnum.SETTING_GET_CAN_OPEN_CODE_ENHANCE.getType());
                commandEnum2.addProperty(PropertyUtils.H("j z2u"), bl);
                SocketMessageHandleListener.send2Web((Project)a3, (Object)commandEnum2);
                return;
            }
            case ACTION_SYNC_DOCUMENT_LIST: {
                void a4;
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                return;
            }
        }
    }
}
