/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.util.IdUtil
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.intellij.openapi.project.Project
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.aicode.agent.service;

import cn.hutool.core.util.IdUtil;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.dto.ConnectConfigDto;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.chat.FirstChatMessage;
import com.aicode.agent.dto.chat.SqlInfoDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.service.CodeCompleteService;
import com.aicode.enums.WebViewDataTypeEnum;
import com.aicode.enums.WebViewResponseTypeEnum;
import com.aicode.exception.RequestCancelException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.intellij.openapi.project.Project;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class SqlService {
    public static ConcurrentNavigableMap<String, String> SQL_SESSION_ID = new ConcurrentSkipListMap<String, String>();
    private static final Logger enum = LoggerFactory.getLogger(SqlService.class);

    private static ConnectConfigDto sf(JsonObject jsonObject) {
        JsonObject jsonObject2 = jsonObject;
        if (!jsonObject2.has(RequestCancelException.H("%C)B5"))) {
            return new ConnectConfigDto();
        }
        JsonObject jsonObject3 = jsonObject2.get(CodeCompleteService.H("z^v_j")).getAsJsonObject();
        Object object = jsonObject3.get(RequestCancelException.H("W?K Y$")).getAsString();
        Object a = jsonObject3.get(CodeCompleteService.H("WuY{")).getAsString();
        String string = jsonObject3.get(RequestCancelException.H("R*E$")).getAsString();
        object = new ConnectConfigDto((String)object, (String)a, string);
        if (jsonObject3.has(CodeCompleteService.H("Ck"))) {
            a = jsonObject3.get(RequestCancelException.H("^4")).getAsString();
            ((ConnectConfigDto)object).setId((String)a);
        }
        if (jsonObject3.has(CodeCompleteService.H("JiO}"))) {
            a = jsonObject3.get(RequestCancelException.H("W6R\"")).getAsString();
            ((ConnectConfigDto)object).setUser((String)a);
        }
        if (jsonObject3.has(CodeCompleteService.H("_kZ\u007fHuXk"))) {
            a = jsonObject3.get(RequestCancelException.H("B4G U*E4")).getAsString();
            ((ConnectConfigDto)object).setPassword((String)a);
        }
        if (jsonObject3.has(CodeCompleteService.H("Kk]m]{Yj"))) {
            a = jsonObject3.get(RequestCancelException.H("V4@2@$D5")).getAsString();
            ((ConnectConfigDto)object).setDatabase((String)a);
        }
        return object;
    }

    /*
     * WARNING - void declaration
     */
    public static void handleSqlTest(JsonObject jsonObject, Project project) {
        void a;
        MessageDto messageDto;
        JsonObject jsonObject2 = jsonObject;
        ConnectConfigDto a2 = SqlService.sf(jsonObject2);
        MessageDto messageDto2 = messageDto = new MessageDto(a2.getId(), CommandEnum.SQL_TEST_CONNECT.getType());
        messageDto2.setData(a2);
        PluginWebsocketClient.sendWsMessage(messageDto2, (Project)a);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static FirstChatMessage kF(Project project, JsonObject jsonObject) {
        Project project2 = project;
        try {
            FirstChatMessage.ValueDTO valueDTO;
            SqlInfoDto sqlInfoDto;
            Object object;
            String string;
            JsonArray jsonArray;
            SqlInfoDto a;
            block7: {
                jsonArray = a.getAsJsonArray(RequestCancelException.H(")V+W9X:]8y\u0004"));
                string = null;
                if (jsonArray != null) {
                    int a22;
                    int n = a22 = 0;
                    while (n < jsonArray.size()) {
                        object = jsonArray.get((int)a22).getAsJsonObject();
                        if (object.has(CodeCompleteService.H("S{zJ")) && RequestCancelException.H("6[>W<y\u0014").equals(object.get(CodeCompleteService.H("S{zJ")).getAsString())) {
                            string = object.get(RequestCancelException.H("%[1b\u0015")).getAsString();
                            sqlInfoDto = a;
                            break block7;
                        }
                        n = ++a22;
                    }
                }
                sqlInfoDto = a;
            }
            JsonObject a22 = sqlInfoDto.get(CodeCompleteService.H("YmUcg\\")).getAsJsonObject().get(RequestCancelException.H("&E?s3q\u001f")).getAsJsonObject();
            object = new Gson();
            a = (SqlInfoDto)object.fromJson((JsonElement)a22, SqlInfoDto.class);
            String a22 = StringUtils.isBlank((CharSequence)((CharSequence)SQL_SESSION_ID.get(project2.getBasePath()))) ? IdUtil.fastSimpleUUID() : (String)SQL_SESSION_ID.get(project2.getBasePath());
            SQL_SESSION_ID.put(project2.getBasePath(), a22);
            object = new FirstChatMessage();
            FirstChatMessage.ValueDTO valueDTO2 = valueDTO = new FirstChatMessage.ValueDTO();
            FirstChatMessage.ValueDTO valueDTO3 = valueDTO;
            valueDTO.setId(IdUtil.fastSimpleUUID());
            valueDTO3.setSessionId(a22);
            valueDTO3.setType(string);
            valueDTO2.setSqlInfo(a);
            valueDTO2.setIntelligent(jsonArray);
            Object object2 = object;
            ((FirstChatMessage)object2).setType(WebViewDataTypeEnum.SQL_CHAT_UPDATE_CONVERSATION_LIST.getType());
            ((FirstChatMessage)object2).setValue(valueDTO2);
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static JsonObject saveSource(JsonObject jsonObject) {
        JsonObject a;
        JsonObject jsonObject2 = jsonObject;
        JsonObject jsonObject3 = a = new JsonObject();
        jsonObject3.addProperty(CodeCompleteService.H("[sYi"), WebViewResponseTypeEnum.SQL_CHAT_RECEIVE_SAVE.getType());
        jsonObject3.add(RequestCancelException.H("2S9A6"), (JsonElement)jsonObject2);
        return jsonObject3;
    }

    public static JsonObject getTableList(JsonObject jsonObject) {
        JsonObject a;
        JsonObject jsonObject2 = jsonObject;
        JsonObject jsonObject3 = a = new JsonObject();
        jsonObject3.addProperty(CodeCompleteService.H("KcHx"), WebViewResponseTypeEnum.SQL_CHAT_RECEIVE_TABLE_LIST.getType());
        jsonObject3.add(RequestCancelException.H(")C)P'"), (JsonElement)jsonObject2);
        return jsonObject3;
    }

    public static JsonObject testConnect(JsonObject jsonObject) {
        JsonObject a;
        JsonObject jsonObject2 = jsonObject;
        JsonObject jsonObject3 = a = new JsonObject();
        jsonObject3.addProperty(CodeCompleteService.H("[sHx"), WebViewResponseTypeEnum.SQL_CHAT_RECEIVE_LINK_TEST.getType());
        jsonObject3.add(RequestCancelException.H("%S9P'"), (JsonElement)jsonObject2);
        return jsonObject3;
    }

    public static JsonObject getSourceType(JsonObject jsonObject) {
        JsonObject a2;
        JsonObject jsonObject2 = jsonObject;
        JsonArray jsonArray = new JsonArray();
        if (jsonObject2 != null) {
            try {
                jsonArray = jsonObject2.get(RequestCancelException.H("E'@2")).getAsJsonArray();
            }
            catch (Exception a2) {
                enum.info("getSourceType Exception" + a2.getMessage());
            }
        }
        JsonObject jsonObject3 = a2 = new JsonObject();
        jsonObject3.addProperty(CodeCompleteService.H("H`Yi"), WebViewResponseTypeEnum.SQL_CHAT_RECEIVE_SOURCE_TYPES.getType());
        jsonObject3.add(RequestCancelException.H("9@*A6"), (JsonElement)jsonArray);
        return jsonObject3;
    }

    /*
     * WARNING - void declaration
     */
    public static void handleSqlSave(JsonObject jsonObject, Project project) {
        void a;
        JsonObject jsonObject2 = jsonObject;
        ConnectConfigDto connectConfigDto = SqlService.sf(jsonObject2);
        Object a2 = connectConfigDto.getId();
        if (StringUtils.isBlank((CharSequence)a2)) {
            a2 = IdUtil.fastSimpleUUID();
        }
        Object object = a2 = new MessageDto((String)a2, CommandEnum.SQL_SOURCE_EDIT.getType());
        ((MessageDto)object).setData(connectConfigDto);
        PluginWebsocketClient.sendWsMessage((MessageDto)object, (Project)a);
    }

    /*
     * WARNING - void declaration
     */
    public static void handleSqlChatMessage(JsonObject jsonObject, Project project) {
        void a;
        JsonObject jsonObject2 = jsonObject;
        if (!jsonObject2.has(RequestCancelException.H("!W=A6"))) {
            return;
        }
        Object a2 = jsonObject2.getAsJsonObject(CodeCompleteService.H("~Jb\\i"));
        if ((a2 = SqlService.kF((Project)a, (JsonObject)a2)) == null) {
            return;
        }
        SocketMessageHandleListener.send2Web((Project)a, a2);
        if (((FirstChatMessage)a2).getValue() == null) {
            return;
        }
        FirstChatMessage.ValueDTO valueDTO = ((FirstChatMessage)a2).getValue();
        a2 = valueDTO.getId();
        String string = valueDTO.getType();
        SqlInfoDto sqlInfoDto = valueDTO.getSqlInfo();
        a2 = new MessageDto((String)a2, string);
        ((MessageDto)a2).setData(sqlInfoDto);
        ((MessageDto)a2).setSessionId((String)SQL_SESSION_ID.get(a.getBasePath()));
        PluginWebsocketClient.sendWsMessage((MessageDto)a2, (Project)a);
    }

    /*
     * WARNING - void declaration
     */
    public static void handleSqlDelete(JsonObject jsonObject, Project project) {
        void a;
        MessageDto messageDto;
        JsonObject jsonObject2 = jsonObject;
        if (!jsonObject2.has(RequestCancelException.H("%D.A6"))) {
            return;
        }
        Object a2 = jsonObject2.get(CodeCompleteService.H("zYq\\i")).getAsString();
        MessageDto messageDto2 = messageDto = new MessageDto((String)a2, CommandEnum.SQL_SOURCE_DELETE.getType());
        messageDto2.setData(a2);
        PluginWebsocketClient.sendWsMessage(messageDto2, (Project)a);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static void handleAction(WebViewDataTypeEnum webViewDataTypeEnum, JsonObject jsonObject, Project project) {
        WebViewDataTypeEnum a = project;
        WebViewDataTypeEnum a2 = webViewDataTypeEnum;
        switch (a2) {
            case SQL_CHAT_GET_MODEL_LIST: {
                PluginWebsocketClient.sendWsMessage(CommandEnum.USER_MODEL_LIST, (Project)a);
                return;
            }
            case SQL_CHAT_REQUEST_SOURCE_TYPES: {
                PluginWebsocketClient.sendWsMessage(CommandEnum.SQL_SOURCE_TYPES, (Project)a);
                return;
            }
            case SQL_CHAT_SQL_LINK_TEST: {
                void a3;
                SqlService.handleSqlTest((JsonObject)a3, (Project)a);
                return;
            }
            case SQL_CHAT_SOURCE_LIST: {
                void a3;
                if (a3.has(CodeCompleteService.H("vBjWb")) && a3.get(RequestCancelException.H(")_5J=")).isJsonObject()) {
                    MessageDto messageDto;
                    JsonObject jsonObject2 = a3.get(CodeCompleteService.H("vBjWb")).getAsJsonObject();
                    MessageDto messageDto2 = messageDto = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.SQL_SOURCE_LIST.getType());
                    messageDto2.setData(jsonObject2);
                    PluginWebsocketClient.sendWsMessage(messageDto2, (Project)a);
                    return;
                }
                PluginWebsocketClient.sendWsMessage(CommandEnum.SQL_SOURCE_LIST, (Project)a);
                return;
            }
            case SQL_CHAT_SQL_SAVE: {
                void a3;
                SqlService.handleSqlSave((JsonObject)a3, (Project)a);
                return;
            }
            case SQL_CHAT_SOURCE_DELETE: {
                void a3;
                SqlService.handleSqlDelete((JsonObject)a3, (Project)a);
                return;
            }
            case SQL_CHAT_TABLE_LIST: {
                void a3;
                SqlService.handleSqlTableList((JsonObject)a3, (Project)a);
                return;
            }
            case SQL_CHAT_SEND_MSG: {
                void a3;
                SqlService.handleSqlChatMessage((JsonObject)a3, (Project)a);
                return;
            }
            case SQL_CHAT_NEW_CHAT: {
                SQL_SESSION_ID.put(a.getBasePath(), IdUtil.fastSimpleUUID());
                return;
            }
            case SQL_CHAT_STOP_RESPONSE: {
                void a3;
                SqlService.handleSqlChatStop((Project)a, (JsonObject)a3);
                return;
            }
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static void handleAgentAction(CommandEnum commandEnum, String string, JsonObject jsonObject, Project project) {
        CommandEnum commandEnum2 = commandEnum;
        switch (commandEnum2) {
            case SQL_SOURCE_LIST: {
                void a;
                void a2;
                void a3;
                PluginWebsocketClient.AGENT_REQUEST.remove(a3);
                CommandEnum a4 = SqlService.getSourceList((JsonObject)a2);
                SocketMessageHandleListener.send2Web((Project)a, (Object)a4);
                return;
            }
            case SQL_SOURCE_TYPES: {
                void a;
                void a2;
                void a3;
                PluginWebsocketClient.AGENT_REQUEST.remove(a3);
                CommandEnum a4 = SqlService.getSourceType((JsonObject)a2);
                SocketMessageHandleListener.send2Web((Project)a, (Object)a4);
                return;
            }
            case SQL_TEST_CONNECT: {
                void a;
                void a2;
                void a3;
                PluginWebsocketClient.AGENT_REQUEST.remove(a3);
                CommandEnum a4 = SqlService.testConnect((JsonObject)a2);
                SocketMessageHandleListener.send2Web((Project)a, (Object)a4);
                return;
            }
            case SQL_SOURCE_EDIT: {
                void a;
                void a2;
                void a3;
                PluginWebsocketClient.AGENT_REQUEST.remove(a3);
                CommandEnum a4 = SqlService.saveSource((JsonObject)a2);
                SocketMessageHandleListener.send2Web((Project)a, (Object)a4);
                return;
            }
            case SQL_SOURCE_DELETE: {
                void a3;
                PluginWebsocketClient.AGENT_REQUEST.remove(a3);
                return;
            }
            case SQL_TABLE_LIST: {
                void a;
                void a2;
                void a3;
                PluginWebsocketClient.AGENT_REQUEST.remove(a3);
                CommandEnum a4 = SqlService.getTableList((JsonObject)a2);
                SocketMessageHandleListener.send2Web((Project)a, (Object)a4);
                return;
            }
            case SQL_GENERATE: 
            case SQL_OPTIMIZE: {
                void a;
                void a2;
                void a3;
                void v0 = a;
                SocketMessageHandleListener.send2Web((Project)v0, SqlService.getSqlChat((Project)v0, (String)a3, (JsonObject)a2, ""));
                return;
            }
        }
    }

    public SqlService() {
        SqlService a;
    }

    /*
     * WARNING - void declaration
     */
    public static JsonObject getSqlChat(Project project, String string, JsonObject jsonObject, String string2) {
        Project project2;
        JsonObject jsonObject2;
        String string3;
        Project a;
        block6: {
            block5: {
                void a2;
                void a3;
                Project project3 = project;
                Project project4 = a = new JsonObject();
                project4.addProperty(CodeCompleteService.H("Qy"), (String)a3);
                project4.addProperty(RequestCancelException.H(")w\u0006J7_9l&"), (String)SQL_SESSION_ID.get(project3.getBasePath()));
                string3 = "";
                if (StringUtils.isBlank((CharSequence)a2)) {
                    Project project5 = a;
                    project5.addProperty(CodeCompleteService.H("O}Su`DWdyqHx"), RequestCancelException.H("D2]6"));
                    project5.addProperty(CodeCompleteService.H("Tkik]OWqBfKx"), Boolean.valueOf(true));
                    try {
                        void a4;
                        jsonObject2 = a4.get(RequestCancelException.H("T6Q#")).getAsJsonObject();
                        if (jsonObject2.has(CodeCompleteService.H("Ym@i"))) {
                            string3 = jsonObject2.get(RequestCancelException.H("D2]6")).getAsString();
                        }
                        if (jsonObject2.has(CodeCompleteService.H("dCl]y")) && jsonObject2.get(RequestCancelException.H(";^3@&")).getAsBoolean()) {
                            a.addProperty(CodeCompleteService.H("Tkik]OWqBfKx"), Boolean.valueOf(false));
                            PluginWebsocketClient.AGENT_REQUEST.remove(a3);
                        }
                        break block5;
                    }
                    catch (Exception exception) {
                        enum.info("getSqlChat Exception" + exception.getMessage());
                        project2 = a;
                        break block6;
                    }
                }
                a.addProperty(RequestCancelException.H("R\"N*}\u001bJ;d.U'"), CodeCompleteService.H("d_zWo"));
                a.addProperty(RequestCancelException.H("I4t4@\u0010J._9V'"), Boolean.valueOf(false));
                string3 = a2;
            }
            project2 = a;
        }
        project2.addProperty(CodeCompleteService.H("}OWqBfKx"), string3);
        JsonObject jsonObject3 = jsonObject2 = new JsonObject();
        jsonObject3.addProperty(RequestCancelException.H("D.U'"), WebViewResponseTypeEnum.SQL_CHAT_UPDATE_CONVERSATION_LIST.getType());
        jsonObject3.add(CodeCompleteService.H("wLdMx"), (JsonElement)a);
        return jsonObject3;
    }

    /*
     * WARNING - void declaration
     */
    public static void handleSqlTableList(JsonObject jsonObject, Project project) {
        void a;
        Object a2;
        JsonObject jsonObject2 = jsonObject;
        if (!jsonObject2.has(RequestCancelException.H(")C)P'"))) {
            return;
        }
        JsonObject jsonObject3 = jsonObject2.get(CodeCompleteService.H("v^vMx")).getAsJsonObject();
        Object object = a2 = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.SQL_TABLE_LIST.getType());
        ((MessageDto)object).setData(jsonObject3);
        PluginWebsocketClient.sendWsMessage((MessageDto)object, (Project)a);
    }

    public static void handleSqlChatStop(Project project, JsonObject jsonObject) {
        JsonObject jsonObject2;
        MessageDto messageDto;
        Project a;
        Object a22;
        block4: {
            a22 = jsonObject;
            a = project;
            if (!a22.has(CodeCompleteService.H("kBjI|"))) {
                return;
            }
            try {
                a22 = a22.get(RequestCancelException.H("4_5T#")).getAsString();
                if (!StringUtils.isBlank((CharSequence)a22)) break block4;
                return;
            }
            catch (Exception a22) {
                enum.error(a22.getMessage(), (Throwable)a22);
                return;
            }
        }
        MessageDto messageDto2 = messageDto = new MessageDto((String)a22, CommandEnum.ACTION_ABORT.getType());
        messageDto2.setData(a22);
        PluginWebsocketClient.sendWsMessage(messageDto2, a);
        MessageDto messageDto3 = messageDto = new JsonObject();
        messageDto3.addProperty(CodeCompleteService.H("U}"), (String)a22);
        messageDto3.addProperty(RequestCancelException.H("1g\u0016V+Q7h\""), (String)SQL_SESSION_ID.get(a.getBasePath()));
        MessageDto messageDto4 = messageDto;
        messageDto.addProperty(CodeCompleteService.H("m_KmLhO|"), "");
        messageDto4.addProperty(RequestCancelException.H("B2V2m\u000bV'j Q#"), CodeCompleteService.H("WcDm"));
        messageDto4.addProperty(RequestCancelException.H("Y$l,P\u0000V2Q7R#"), false);
        JsonObject jsonObject3 = jsonObject2 = new JsonObject();
        jsonObject3.addProperty(CodeCompleteService.H("W\u007fL|"), WebViewDataTypeEnum.SQL_CHAT_UPDATE_CONVERSATION_LIST.getType());
        jsonObject3.add(RequestCancelException.H("4_5T#"), (JsonElement)messageDto);
        SocketMessageHandleListener.send2Web(a, jsonObject3);
        PluginWebsocketClient.AGENT_REQUEST.remove(a22);
    }

    public static JsonObject getSourceList(JsonObject jsonObject) {
        JsonObject a;
        JsonObject jsonObject2 = jsonObject;
        JsonObject jsonObject3 = a = new JsonObject();
        jsonObject3.addProperty(RequestCancelException.H("V<U'"), WebViewResponseTypeEnum.SQL_CHAT_RECEIVE_SOURCE_LIST.getType());
        jsonObject3.add(CodeCompleteService.H("v^vMx"), (JsonElement)jsonObject2);
        return jsonObject3;
    }
}
