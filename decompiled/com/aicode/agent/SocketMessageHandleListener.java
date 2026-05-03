/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.intellij.openapi.diagnostic.Logger
 *  com.intellij.openapi.project.Project
 *  com.intellij.openapi.ui.MessageType
 *  io.opentelemetry.api.trace.Span
 *  org.apache.commons.lang3.StringUtils
 */
package com.aicode.agent;

import com.aicode.action.CommitMessageSuggestionAction;
import com.aicode.action.RefreshAction;
import com.aicode.action.batch.BatchUnitTestTemplateService;
import com.aicode.agent.HeartBeatCheckRunner;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.SocketMessageListener;
import com.aicode.agent.dto.CodeTipRequestDto;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.ResponseDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.service.ChatService;
import com.aicode.agent.service.CodeCheckService;
import com.aicode.agent.service.CodeCompleteService;
import com.aicode.agent.service.CodeSearchService;
import com.aicode.agent.service.CommonService;
import com.aicode.agent.service.GitReviewService;
import com.aicode.agent.service.InlineChatCommandService;
import com.aicode.agent.service.SqlService;
import com.aicode.agent.service.UserService;
import com.aicode.apm.OpenTelemetryService;
import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.apm.enums.SpanAttrEnum;
import com.aicode.apm.enums.TracerEnum;
import com.aicode.enums.AICodeStatus;
import com.aicode.enums.WebViewDataTypeEnum;
import com.aicode.listener.GitBranchChangeListener;
import com.aicode.service.editor.RequestTipServiceImpl;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.status.AICodeStatusService;
import com.aicode.template.TemplateGenerator;
import com.aicode.template.generator.CreateTestMethodTask;
import com.aicode.template.request.TemplateRequestService;
import com.aicode.test.BatchUnitTestService;
import com.aicode.test.UnitTestService;
import com.aicode.updater.PluginUpdater;
import com.aicode.util.NewFileUtils;
import com.aicode.util.PositionUtil;
import com.aicode.view.WebViewWindowPanel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import io.opentelemetry.api.trace.Span;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class SocketMessageHandleListener
implements SocketMessageListener {
    private static final Logger enum = Logger.getInstance(SocketMessageHandleListener.class);

    /*
     * WARNING - void declaration
     */
    public static Boolean send2Web(Project project, Object object) {
        void a;
        Project project2 = project;
        if (a == null) {
            return false;
        }
        WebViewWindowPanel a2 = (WebViewWindowPanel)project2.getUserData(WebViewWindowPanel.WEB_VIEW_PANEL);
        if (Objects.isNull(a2) || !a2.isLoaded.get()) {
            return false;
        }
        a2.sendMessage2webView(a);
        return true;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void FE(Project project, CommandEnum commandEnum, JsonObject jsonObject, ResponseDto responseDto, MessageDto messageDto) {
        void a;
        void a2;
        SocketMessageHandleListener a3 = jsonObject;
        SocketMessageHandleListener a4 = this;
        void v0 = a2;
        Object object = v0.getData();
        String string = v0.getId();
        switch (a.getAgentModuleEnum()) {
            case INIT: {
                void a5;
                SocketMessageHandleListener.pe((Project)a5, (CommandEnum)a, (JsonObject)a3, (ResponseDto)a2);
                return;
            }
            case LOGIN: {
                void a5;
                UserService.handleAgentAction((CommandEnum)a, (JsonObject)a3, string, object, (Project)a5);
                return;
            }
            case CHAT: {
                void a6;
                void a5;
                ChatService.handleAgentAction((CommandEnum)a, (JsonObject)a3, string, (MessageDto)a6, (Project)a5);
                return;
            }
            case SQL_CHAT: {
                void a5;
                SqlService.handleAgentAction((CommandEnum)a, string, (JsonObject)a3, (Project)a5);
                return;
            }
            case CODE_SEARCH: {
                void a5;
                CodeSearchService.handleAgentAction((CommandEnum)a, (JsonObject)a3, string, (Project)a5);
                return;
            }
            case CODE_CHECK: {
                void a6;
                void a5;
                CodeCheckService.handleAgentAction((CommandEnum)a, (JsonObject)a3, (MessageDto)a6, string, (Project)a5);
                return;
            }
            case UNIT_TEST: {
                void a6;
                void a5;
                UnitTestService.handleAgentAction((CommandEnum)a, (JsonObject)a3, (MessageDto)a6, string, (Project)a5);
                return;
            }
            case BATCH_UNIT_TEST: {
                void a6;
                void a5;
                BatchUnitTestService.handleAgentAction((CommandEnum)a, (JsonObject)a3, (MessageDto)a6, string, (Project)a5);
                return;
            }
            case CODE_TEST_TEMPLATE: {
                void a6;
                void a5;
                TemplateRequestService.handleAgentAction((CommandEnum)a, (JsonObject)a3, (MessageDto)a6, string, (Project)a5);
                return;
            }
            case SERVER_RESOURCE: {
                BatchUnitTestTemplateService.changeServerStatus((JsonObject)a3);
                return;
            }
            case CODE_COMPLETE: {
                void a6;
                void a5;
                CodeCompleteService.handleAgentAction((CommandEnum)a, (JsonObject)a3, (MessageDto)a6, string, (Project)a5);
                return;
            }
            case GIT_REVIEW: {
                void a5;
                GitReviewService.handleAgentAction((CommandEnum)a, (JsonObject)a3, (Project)a5);
                return;
            }
            case COMMON: {
                PluginWebsocketClient.AGENT_REQUEST.remove(a2.getId());
                return;
            }
            case INLINE_CHAT: {
                void a6;
                void a5;
                InlineChatCommandService.handleAgentAction(string, (CommandEnum)a, (Project)a5, (MessageDto)a6, (JsonObject)a3);
                return;
            }
        }
    }

    private static /* synthetic */ boolean LF(JsonObject jsonObject, JsonObject jsonObject2) {
        JsonObject a = jsonObject2;
        JsonObject a2 = jsonObject;
        return a2.has(NewFileUtils.H("f1|;"));
    }

    /*
     * WARNING - void declaration
     */
    private static boolean Kd(String string, Project project, ResponseDto responseDto) {
        void a;
        String a2;
        String a3;
        String string2 = string;
        a3 = ((ResponseDto)((Object)a3)).getMsg();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(PositionUtil.H("c\u001eH-"), WebViewDataTypeEnum.COMMON_SHOW_MESSAGE_IN_WEB.getType());
        String string3 = a2 = new JsonObject();
        String string4 = a2;
        string4.addProperty(NewFileUtils.H("q.Z\u001d"), PositionUtil.H(";e\u0015W:"));
        string4.addProperty(NewFileUtils.H("\u0014Y\u001dv6M\u001d"), a3);
        string3.addProperty(PositionUtil.H("1Q&Y\u001d{\bK-"), Boolean.valueOf(true));
        string3.addProperty(NewFileUtils.H("O\fN\u000fq>E\u0016"), (Number)0);
        if (StringUtils.equals((CharSequence)PositionUtil.H("Q\ty"), (CharSequence)string2) && StringUtils.isNotBlank((CharSequence)a3)) {
            JsonObject jsonObject2 = jsonObject;
            a2.addProperty(NewFileUtils.H("O\fN\u000fq>E\u0016"), (Number)3000);
            jsonObject2.add(PositionUtil.H("(v\u000bM-"), (JsonElement)a2);
            SocketMessageHandleListener.send2Web((Project)a, jsonObject2);
            return true;
        }
        if (StringUtils.isNotBlank((CharSequence)a3)) {
            JsonObject jsonObject3 = jsonObject;
            jsonObject3.add(NewFileUtils.H("\u0018d;_\u001d"), (JsonElement)a2);
            SocketMessageHandleListener.send2Web((Project)a, jsonObject3);
            return true;
        }
        return false;
    }

    private static /* synthetic */ JsonArray nD(JsonObject jsonObject, JsonObject jsonObject2) {
        JsonObject a = jsonObject2;
        JsonObject a2 = jsonObject;
        return a2.getAsJsonArray(PositionUtil.H("V#l\t"));
    }

    public SocketMessageHandleListener() {
        SocketMessageHandleListener a;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void pe(Project project, CommandEnum commandEnum, JsonObject jsonObject, ResponseDto responseDto) {
        Object a2;
        Object a3 = jsonObject;
        Project a4 = project;
        switch (Ka.byte[((Enum)a2).ordinal()]) {
            case 1: {
                JsonElement a5;
                PluginWebsocketClient.AGENT_REQUEST.remove(a5.getId());
                return;
            }
            case 2: {
                JsonElement a5;
                PluginWebsocketClient.AGENT_REQUEST.remove(a5.getId());
                String string = a3.get(NewFileUtils.H("Z\r_\u0018")).getAsJsonObject().get(PositionUtil.H("Z%G;B(p-")).getAsString();
                PluginWebsocketClient.AGENT_CLIENT_ID.put(a4.getBasePath(), string);
                return;
            }
            case 3: {
                JsonElement a5;
                PluginWebsocketClient.AGENT_REQUEST.remove(a5.getId());
                PluginUpdater.checkUpdate(a4, (JsonObject)a3);
                return;
            }
            case 4: {
                JsonElement a5;
                PluginWebsocketClient.AGENT_REQUEST.remove(a5.getId());
                OpenTelemetryService.getInstance().handApmConfig((JsonObject)a3);
                return;
            }
            case 5: 
            case 6: 
            case 7: 
            case 8: {
                JsonElement a5;
                void v0 = a5;
                GitBranchChangeListener.handleGitRepoStatus(v0.getId(), (JsonObject)a3, a4);
                GitBranchChangeListener.handleGitResponse(v0.getId(), (JsonObject)a3, a4, (CommandEnum)((Object)a2));
                return;
            }
            case 9: {
                JsonElement a5;
                PluginWebsocketClient.AGENT_REQUEST.remove(a5.getId());
                a2 = Optional.ofNullable(a3).filter(arg_0 -> SocketMessageHandleListener.LF((JsonObject)a3, arg_0)).map(arg_0 -> SocketMessageHandleListener.nD((JsonObject)a3, arg_0)).orElseGet(JsonArray::new);
                JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty(NewFileUtils.H("J\u0015[\u001c"), WebViewDataTypeEnum.SETTING_RECEIVE_REPO_STATUS.getType());
                JsonObject jsonObject3 = new JsonObject();
                jsonObject3.add(PositionUtil.H("@'I&}*M(L:"), (JsonElement)a2);
                jsonObject2.add(NewFileUtils.H("\u0018_\u0000^\u001c"), (JsonElement)jsonObject3);
                SocketMessageHandleListener.send2Web(a4, jsonObject2);
                return;
            }
            case 10: {
                JsonElement a5;
                PluginWebsocketClient.AGENT_REQUEST.remove(a5.getId());
                a2 = Optional.ofNullable(a3).filter(a -> a.has(NewFileUtils.H("h?|;"))).map(a -> a.getAsJsonArray(PositionUtil.H("A4k\u000e"))).orElseGet(JsonArray::new);
                a3 = new ArrayList();
                a2 = a2.iterator();
                Iterator iterator = a2;
                while (true) {
                    if (!iterator.hasNext()) {
                        AICodeSettingsState.getInstance().languages = a3;
                        return;
                    }
                    a5 = (JsonElement)a2.next();
                    iterator = a2;
                    a3.add(a5.getAsString());
                }
            }
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void mf(String string, CommandEnum commandEnum, Project project, ResponseDto responseDto, MessageDto messageDto, JsonObject jsonObject) {
        JsonObject a;
        void a2;
        void a3;
        SocketMessageHandleListener socketMessageHandleListener = this;
        enum.info("agent error info: " + a3.getType() + " " + a2.getCode() + " " + a2.getMsg());
        String string2 = a2.getId();
        PluginWebsocketClient.AGENT_REQUEST.remove(string2);
        Object a4 = OpenTelemetryUtil.buildWithTracer(TracerEnum.RECORD_EXCEPTION, socketMessageHandleListener.getClass().getName());
        a4.setAttribute(SpanAttrEnum.COMMAND_ID.getText(), string2);
        a4.setAttribute(SpanAttrEnum.EXCEPTION_COMMAND.getText(), a3.getType());
        a4.setAttribute(SpanAttrEnum.EXCEPTION_CODE.getText(), (String)a);
        a4.setAttribute(SpanAttrEnum.EXCEPTION_MESSAGE.getText(), a2.getMsg());
        a4.end();
        switch (Ka.byte[a3.ordinal()]) {
            case 11: {
                void a5;
                SocketMessageHandleListener.Kd((String)a, (Project)a5, (ResponseDto)a2);
                a4 = UserService.getLoginUrl("");
                SocketMessageHandleListener.send2Web((Project)a5, a4);
                return;
            }
            case 12: {
                RefreshAction.REFRESH_MAP.remove(string2);
                return;
            }
            case 13: {
                void a6;
                void a5;
                ChatService.handleParseWebUrlErr((JsonObject)a6, (Project)a5, (String)a);
                return;
            }
            case 14: {
                void a5;
                CommitMessageSuggestionAction.COMMIT_MESSAGE_MAP.remove(string2);
                CommitMessageSuggestionAction.COMMIT_MESSAGE_BUTTON.set(false);
                CommonService.messageBus((Project)a5, a2.getMsg(), MessageType.INFO);
                return;
            }
            case 15: {
                Map<String, Long> map;
                void a5;
                a4 = RequestTipServiceImpl.CODE_TIP_MAP.get(string2);
                if (a4 != null) {
                    Span span = ((CodeTipRequestDto)a4).getParentSpan();
                    span.end();
                }
                if (!RequestTipServiceImpl.CODE_TIP_MAP.containsKey(string2)) break;
                RequestTipServiceImpl.CODE_TIP_MAP.remove(string2);
                if (!RequestTipServiceImpl.LAST_REQUEST.containsKey(a5) || !(map = RequestTipServiceImpl.LAST_REQUEST.get(a5)).containsKey(string2)) break;
                AICodeStatusService.notifyApplication(AICodeStatus.Ready, "");
                return;
            }
            case 16: {
                void a5;
                JsonObject jsonObject2 = CodeSearchService.getCodeSearchLanguage(null);
                SocketMessageHandleListener.send2Web((Project)a5, jsonObject2);
                return;
            }
            case 17: {
                void a5;
                a4 = CodeSearchService.getCodeSearchRepos(string2, null);
                SocketMessageHandleListener.send2Web((Project)a5, a4);
                return;
            }
            case 18: {
                void a5;
                a4 = CodeSearchService.getCodeSearchCode(string2, null);
                SocketMessageHandleListener.send2Web((Project)a5, a4);
                return;
            }
            case 19: {
                void a6;
                void a5;
                a4 = SqlService.getSourceList((JsonObject)a6);
                SocketMessageHandleListener.send2Web((Project)a5, a4);
                return;
            }
            case 20: {
                void a5;
                a4 = SqlService.getSourceType(null);
                SocketMessageHandleListener.send2Web((Project)a5, a4);
                return;
            }
            case 21: {
                void a6;
                void a5;
                a4 = SqlService.getTableList((JsonObject)a6);
                SocketMessageHandleListener.send2Web((Project)a5, a4);
                return;
            }
            case 22: 
            case 23: {
                void a5;
                void v0 = a5;
                SocketMessageHandleListener.send2Web((Project)v0, SqlService.getSqlChat((Project)v0, string2, null, a2.getMsg()));
                return;
            }
            case 24: {
                void a5;
                a4 = CodeCheckService.getErrorListResult((ResponseDto)a2);
                SocketMessageHandleListener.send2Web((Project)a5, a4);
                return;
            }
            case 25: {
                void a5;
                UnitTestService.testAnalysisErr((Project)a5, (ResponseDto)a2);
                return;
            }
            case 26: 
            case 27: 
            case 28: 
            case 29: {
                void a7;
                void a5;
                if (TemplateGenerator.INSTANCE.batchCreateTestFileTask != null && !TemplateGenerator.INSTANCE.batchCreateTestFileTask.isCanceled().booleanValue()) {
                    TemplateRequestService.handleRequestErrorTestCase((ResponseDto)a2, (CommandEnum)a3, (MessageDto)a7);
                    return;
                }
                JsonObject jsonObject3 = UnitTestService.requestTestCaseErr((ResponseDto)a2, (CommandEnum)a3, (MessageDto)a7);
                SocketMessageHandleListener.send2Web((Project)a5, jsonObject3);
                CreateTestMethodTask.isCanceled.set(true);
                return;
            }
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: {
                void a5;
                JsonObject jsonObject4 = BatchUnitTestService.batchUnitTestMessage(false, a2.getMsg());
                SocketMessageHandleListener.send2Web((Project)a5, jsonObject4);
                return;
            }
            case 35: {
                void a7;
                TemplateRequestService.handleRequestErrorTestCase((ResponseDto)a2, (CommandEnum)a3, (MessageDto)a7);
                return;
            }
            case 5: 
            case 6: 
            case 7: 
            case 8: {
                void a5;
                a4 = a2.getMsg();
                GitBranchChangeListener.handleGitException(string2, (String)a, (Project)a5, (CommandEnum)a3, (String)a4);
                if (!StringUtils.isNotBlank((CharSequence)a4) || a3 != CommandEnum.GIT_REPO_AUTHORIZE) break;
                CommonService.messageBus((Project)a5, (String)a4, MessageType.INFO);
                return;
            }
            case 36: {
                void a5;
                if (!StringUtils.equals((CharSequence)PositionUtil.H("w\u000b{"), (CharSequence)a)) break;
                SocketMessageHandleListener.Kd((String)a, (Project)a5, (ResponseDto)a2);
                return;
            }
            case 37: {
                void a5;
                if (!StringUtils.equals((CharSequence)NewFileUtils.H("G\u0018H"), (CharSequence)a)) break;
                CommonService.messageBus((Project)a5, a2.getMsg(), MessageType.INFO);
                return;
            }
            case 38: {
                void a6;
                void a5;
                a = ChatService.getGamePlay((JsonObject)a6);
                SocketMessageHandleListener.send2Web((Project)a5, a);
                return;
            }
            case 39: 
            case 40: 
            case 41: {
                void a7;
                void a5;
                InlineChatCommandService.handleAgentAction((Project)a5, (MessageDto)a7, a2.getMsg(), (CommandEnum)a3);
            }
            default: {
                enum.info("agent error info: " + a3.getType() + " " + a2.getCode() + " " + a2.getMsg());
            }
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void handleSocketMessage(String string, Project project) {
        SocketMessageHandleListener socketMessageHandleListener = this;
        try {
            void a;
            CommandEnum commandEnum;
            ResponseDto a2;
            SocketMessageHandleListener a3 = JsonParser.parseString((String)((Object)a2)).getAsJsonObject();
            String string2 = a3.get(PositionUtil.H("X$],")).getAsString();
            a2 = (ResponseDto)new Gson().fromJson((String)((Object)a2), ResponseDto.class);
            MessageDto messageDto = (MessageDto)PluginWebsocketClient.AGENT_REQUEST.get(a2.getId());
            String string3 = a2.getId();
            if (Objects.isNull((Object)(!Objects.isNull(messageDto) ? (commandEnum = CommandEnum.getByType(messageDto.getCommand())) : (commandEnum = CommandEnum.getByType(string3))))) {
                PluginWebsocketClient.AGENT_REQUEST.remove(string3);
                return;
            }
            if (CommandEnum.USER_VERSION == commandEnum) {
                HeartBeatCheckRunner.AGENT_CLIENT_MAP.clear();
                RefreshAction.REFRESH_MAP.remove(string3);
                PluginWebsocketClient.AGENT_REQUEST.remove(string3);
            }
            if (StringUtils.equals((CharSequence)NewFileUtils.H("I\u001bI"), (CharSequence)string2)) {
                socketMessageHandleListener.FE((Project)a, commandEnum, (JsonObject)a3, a2, messageDto);
                return;
            }
            enum.info("ws response except message ===========>" + (JsonObject)a3);
            ChatService.isChat(commandEnum, (JsonObject)a3, (Project)a, messageDto, a2);
            socketMessageHandleListener.mf(string2, commandEnum, (Project)a, a2, messageDto, (JsonObject)a3);
            if (StringUtils.equals((CharSequence)PositionUtil.H("\u007f\tx"), (CharSequence)string2)) {
                PluginWebsocketClient.WEB_REQUEST.clear();
                Thread.sleep(800L);
                PluginWebsocketClient.sendWsMessage(CommandEnum.USER_LOGIN, (Project)a);
                AICodeSettingsState.getInstance().clear();
            }
            return;
        }
        catch (Throwable a3) {
            enum.info(a3.getMessage(), a3);
            return;
        }
    }
}
