/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.util.IdUtil
 *  com.google.gson.Gson
 *  com.google.gson.JsonObject
 *  com.intellij.openapi.application.ApplicationInfo
 *  com.intellij.openapi.project.Project
 *  io.opentelemetry.api.GlobalOpenTelemetry
 *  io.opentelemetry.api.trace.Span
 *  io.opentelemetry.context.Context
 *  io.opentelemetry.context.Scope
 *  io.opentelemetry.context.propagation.TextMapSetter
 *  okhttp3.OkHttpClient
 *  okhttp3.Request
 *  okhttp3.Request$Builder
 *  okhttp3.WebSocket
 *  okhttp3.WebSocketListener
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.aicode.agent;

import cn.hutool.core.util.IdUtil;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.WebRequestDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.apm.enums.SpanAttrEnum;
import com.aicode.enums.WebViewDataTypeEnum;
import com.aicode.message.BasicActionsBundle;
import com.aicode.service.editor.RequestResultList;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.test.dto.RequestCaseCodeDto;
import com.aicode.util.LogUtil;
import com.aicode.util.NewFileUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.project.Project;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.TextMapSetter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class PluginWebsocketClient {
    public static ConcurrentNavigableMap<String, MessageDto> AGENT_REQUEST;
    public static ConcurrentNavigableMap<String, WebRequestDto> WEB_REQUEST;
    public static ConcurrentNavigableMap<String, RequestCaseCodeDto> WEB_REQUEST_DATA;
    public Request request;
    private static final Logger byte;
    public static ConcurrentNavigableMap<String, String> AGENT_CLIENT_ID;
    public static String INITID;
    public static ConcurrentNavigableMap<String, WebSocket> AGENT_WEBSOCKETS;
    public static final String URI_LINK_PREFIX;
    public static OkHttpClient client;

    public static String getClientName() {
        String string = ApplicationInfo.getInstance().getVersionName().toUpperCase();
        if (string.contains(RequestResultList.H("SlEs"))) {
            string = NewFileUtils.H("j5n8");
            return string;
        }
        string = string.replaceAll(RequestResultList.H("\u0012"), NewFileUtils.H("T"));
        return string;
    }

    public WebSocket newWebSocket(WebSocketListener webSocketListener) {
        PluginWebsocketClient a = webSocketListener;
        PluginWebsocketClient a2 = this;
        return client.newWebSocket(a2.request, (WebSocketListener)a);
    }

    /*
     * WARNING - void declaration
     */
    private static String FF(Span span, MessageDto messageDto, boolean bl) {
        String a4;
        block7: {
            Span span2 = span;
            Scope scope = span2.makeCurrent();
            try {
                Object a2;
                TextMapSetter a3;
                if (a3 != null) {
                    void a4;
                    a2 = AICodeSettingsState.getInstance();
                    span2.setAttribute(SpanAttrEnum.USER_USERNAME.getText(), ((AICodeSettingsState)a2).userName);
                    span2.setAttribute(SpanAttrEnum.PLUGIN_UPDATE.getText(), ((AICodeSettingsState)a2).isUpdater);
                    span2.setAttribute(SpanAttrEnum.SETTING_TRIGGER_ON_PAUSE.getText(), ((AICodeSettingsState)a2).autoTrigger);
                    span2.setAttribute(SpanAttrEnum.SETTING_TRIGGER_TIME_DELAY.getText(), (long)((AICodeSettingsState)a2).triggerTime.intValue());
                    span2.setAttribute(SpanAttrEnum.SETTING_CODE_MODE.getText(), ((AICodeSettingsState)a2).tipType);
                    span2.setAttribute(SpanAttrEnum.SETTING_MESSAGE_TYPE.getText(), ((AICodeSettingsState)a2).sendKey);
                    span2.setAttribute(SpanAttrEnum.SETTING_JAVA_TEST.getText(), ((AICodeSettingsState)a2).testFramework);
                    span2.setAttribute(SpanAttrEnum.SETTING_JAVA_MOCK.getText(), ((AICodeSettingsState)a2).mockFramework);
                    if (a4 != false) {
                        span2.setAttribute(SpanAttrEnum.COMMAND_ID.getText(), a3.getId());
                    }
                }
                a2 = new HashMap();
                a3 = (map, string, string2) -> {
                    void a;
                    String a2 = string2;
                    Map a3 = map;
                    assert (a3 != null);
                    a3.put(a, a2);
                };
                GlobalOpenTelemetry.getPropagators().getTextMapPropagator().inject(Context.current(), a2, a3);
                a4 = (String)a2.get(RequestResultList.H("qjK~JaBhMnF"));
                if (scope == null) break block7;
            }
            catch (Throwable a2) {
                Throwable throwable;
                block8: {
                    if (scope != null) {
                        try {
                            scope.close();
                            throwable = a2;
                            break block8;
                        }
                        catch (Throwable a3) {
                            a2.addSuppressed(a3);
                        }
                    }
                    throwable = a2;
                }
                throw throwable;
            }
            scope.close();
        }
        return a4;
    }

    static {
        URI_LINK_PREFIX = NewFileUtils.H("W^E\r_\u0016G\u0017\\\u0017[\nFA\u0018");
        AGENT_WEBSOCKETS = new ConcurrentSkipListMap<String, WebSocket>();
        AGENT_CLIENT_ID = new ConcurrentSkipListMap<String, String>();
        WEB_REQUEST = new ConcurrentSkipListMap<String, WebRequestDto>();
        WEB_REQUEST_DATA = new ConcurrentSkipListMap<String, RequestCaseCodeDto>();
        AGENT_REQUEST = new ConcurrentSkipListMap<String, MessageDto>();
        byte = LoggerFactory.getLogger(PluginWebsocketClient.class);
    }

    /*
     * WARNING - void declaration
     */
    public static void closeWebsocket(String string, String string2) {
        String string3 = string;
        String a = (WebSocket)AGENT_WEBSOCKETS.get(string3);
        if (a != null) {
            void a2;
            if (!a.close(1000, (String)a2)) {
                byte.info(NewFileUtils.H("\u5156\u959a\u8ff5\u63dc\u590b\u8d4d"));
                return;
            }
            AGENT_WEBSOCKETS.remove(string3);
        }
    }

    /*
     * WARNING - void declaration
     */
    public static Boolean sendWsMessageForCode(Span span, MessageDto messageDto, Project project) {
        void a;
        MessageDto a2 = messageDto;
        Span a3 = span;
        if (PluginWebsocketClient.Ce((Project)a, a2).booleanValue()) {
            String string = PluginWebsocketClient.FF(a3, a2, false);
            if (StringUtils.isNotBlank((CharSequence)string)) {
                a2.setTraceparent(string);
            }
            Boolean bl = PluginWebsocketClient.Fd((Project)a, a2);
            a3.end();
            return bl;
        }
        a3.end();
        return false;
    }

    private static Boolean Ce(Project project, MessageDto messageDto) {
        MessageDto a = messageDto;
        Project a2 = project;
        if (Objects.isNull(a2)) {
            return false;
        }
        if (Objects.isNull(a)) {
            return false;
        }
        CharSequence[] charSequenceArray = new CharSequence[2];
        charSequenceArray[0] = a.getId();
        charSequenceArray[1] = a.getCommand();
        return !StringUtils.isAllBlank((CharSequence[])charSequenceArray);
    }

    /*
     * WARNING - void declaration
     */
    public static void sendWsMessage(CommandEnum commandEnum, Object object, Project project) {
        void a;
        void a2;
        CommandEnum commandEnum2 = commandEnum;
        Object a3 = IdUtil.fastSimpleUUID();
        String string = commandEnum2.getType();
        a3 = new MessageDto((String)a3, string);
        if (Objects.nonNull(a2)) {
            ((MessageDto)a3).setData(a2);
        }
        PluginWebsocketClient.sendWsMessage((MessageDto)a3, (Project)a);
    }

    /*
     * WARNING - void declaration
     */
    public static void sendWsMessageForGitKnowledge(CommandEnum commandEnum, Object object, Project project, WebViewDataTypeEnum webViewDataTypeEnum) {
        void a;
        void a2;
        WebRequestDto a3;
        CommandEnum commandEnum2 = commandEnum;
        String string = IdUtil.fastSimpleUUID();
        Object a4 = commandEnum2.getType();
        a4 = new MessageDto(string, (String)a4);
        if (Objects.nonNull(a3)) {
            ((MessageDto)a4).setData(a3);
        }
        a3 = new WebRequestDto();
        a3.setType(a2.getType());
        WEB_REQUEST.put(string, a3);
        PluginWebsocketClient.sendWsMessage((MessageDto)a4, (Project)a);
    }

    public static void wsInit(Project project) {
        Project project2 = project;
        Object a = new JsonObject();
        a.addProperty(RequestResultList.H("B[p\u007fCsytQiAo\\"), BasicActionsBundle.message(NewFileUtils.H("\u0011N\u0016D\u001dE\\J\u0004L\fT\u0001)#E\u0000T\u001cU\u0006"), new Object[0]));
        byte.info(BasicActionsBundle.message(RequestResultList.H("Fo@oX\rpx@y@b\u001b^M~Qp_P@t[Xk_XrZa\rnMxF"), new Object[0]) + " plugin version is " + BasicActionsBundle.message(NewFileUtils.H("\u0011N\u0016D\u001dE\\J\u0004L\fT\u0001)#E\u0000T\u001cU\u0006"), new Object[0]));
        Project project3 = a;
        a.addProperty(RequestResultList.H("{FtJ\u007fWTImW"), PluginWebsocketClient.getClientName());
        project3.addProperty(NewFileUtils.H("\u000bU\u0002X\u0001s\u0003E\u0000T\u001cU\u0006"), ApplicationInfo.getInstance().getApiVersion());
        project3.addProperty(RequestResultList.H("ujEwJrW^ArA"), project2.getBasePath());
        PluginWebsocketClient.sendWsMessage(CommandEnum.ACTION_INIT, a, project2);
        INITID = IdUtil.fastSimpleUUID();
        a = new MessageDto(INITID, CommandEnum.USER_LOGIN.getType());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(NewFileUtils.H("\u001bh>E\u001cs\fJ\r"), (Number)1);
        Object object = a;
        ((MessageDto)object).setData(jsonObject);
        PluginWebsocketClient.sendWsMessage((MessageDto)object, project2);
    }

    private static Boolean Fd(Project project, MessageDto messageDto) {
        Object a = messageDto;
        Project a2 = project;
        ((MessageDto)a).initModelInfo();
        boolean bl = false;
        WebSocket webSocket = (WebSocket)AGENT_WEBSOCKETS.get(a2.getBasePath());
        AGENT_REQUEST.put(((MessageDto)a).getId(), (MessageDto)a);
        a = new Gson().toJson(a);
        LogUtil.info(NewFileUtils.H("{?D\u001c"), (String)a);
        if (webSocket != null) {
            bl = webSocket.send((String)a);
        }
        return bl;
    }

    public static void sendWsMessageWithOutApm(MessageDto messageDto, Project project) {
        MessageDto a = project;
        MessageDto a2 = messageDto;
        if (PluginWebsocketClient.Ce((Project)a, a2).booleanValue()) {
            PluginWebsocketClient.Fd((Project)a, a2);
        }
    }

    public PluginWebsocketClient() {
        PluginWebsocketClient a;
        client = new OkHttpClient().newBuilder().readTimeout(60L, TimeUnit.SECONDS).writeTimeout(60L, TimeUnit.SECONDS).connectTimeout(60L, TimeUnit.SECONDS).build();
    }

    /*
     * WARNING - void declaration
     */
    public void createWebSocketConnect(WebSocketListener webSocketListener, String string, Span span) throws Exception {
        void a;
        void a2;
        void a3;
        PluginWebsocketClient pluginWebsocketClient = this;
        if (StringUtils.isBlank((CharSequence)a3)) {
            byte.warn(NewFileUtils.H("]0S\u0007\u4e1a\u7a08\fR\u7ac4\u539a\u839a\u53a9\u590b\u8d4d"));
            throw new Exception(RequestResultList.H("P]Eq\u4e22\u7a501\u000f\u7afe\u53c0\u83ad\u53fe\u5931\u8d17"));
        }
        String a4 = "ws://127.0.0.1:" + (String)a3 + "/ws/idea";
        String string2 = PluginWebsocketClient.FF((Span)a2, null, false);
        if (StringUtils.isNotBlank((CharSequence)string2)) {
            PluginWebsocketClient pluginWebsocketClient2 = pluginWebsocketClient;
            pluginWebsocketClient2.request = new Request.Builder().header(NewFileUtils.H("\u0007R\u0013C\u0017[\u0018_\u001aT\u001c"), string2).url(a4).build();
        } else {
            pluginWebsocketClient.request = new Request.Builder().url(a4).build();
        }
        byte.info("\u521b\u5efawebsocket\u8fde\u63a5: " + (String)a3 + " " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        void v1 = a2;
        pluginWebsocketClient.newWebSocket((WebSocketListener)a);
        v1.setAttribute(SpanAttrEnum.HTTP_SCHEME.getText(), a4);
        v1.setAttribute(SpanAttrEnum.AGENT_VERSION.getText(), BasicActionsBundle.message(RequestResultList.H("vL|BuF.SP`v^3YtQiAo\\"), new Object[0]));
        a2.setAttribute(SpanAttrEnum.HTTP_SCHEME.getText(), a4);
        a2.setAttribute(SpanAttrEnum.AGENT_VERSION.getText(), BasicActionsBundle.message(NewFileUtils.H("D\u001eH\u0016^\r#>F\u0016N\u0006\u000e\u0004N\u000b^\u0016U\u0006"), new Object[0]));
    }

    /*
     * WARNING - void declaration
     */
    public static void sendWsMessage(MessageDto messageDto, Project project) {
        void a;
        MessageDto messageDto2 = messageDto;
        if (PluginWebsocketClient.Ce((Project)a, messageDto2).booleanValue()) {
            Span span = OpenTelemetryUtil.buildWithCommand(messageDto2.getCommand(), PluginWebsocketClient.class.getName());
            Object a2 = PluginWebsocketClient.FF(span, messageDto2, true);
            if (StringUtils.isNotBlank((CharSequence)a2)) {
                messageDto2.setTraceparent((String)a2);
            }
            if (CommandEnum.USER_LOGIN.getType().equals(messageDto2.getCommand())) {
                Object object = a2 = new JsonObject();
                object.addProperty(RequestResultList.H("^rDtMNQpW"), (Number)1);
                messageDto2.setData(object);
            }
            PluginWebsocketClient.Fd((Project)a, messageDto2);
            span.end();
        }
    }

    public static void closeWebsocket(String string) {
        Object a;
        String string2 = string;
        Object object = a = AGENT_WEBSOCKETS.keySet().iterator();
        while (object.hasNext()) {
            String string3 = (String)a.next();
            if (!((WebSocket)AGENT_WEBSOCKETS.get(string3)).close(1000, string2)) {
                byte.info(RequestResultList.H("\u5162\u95ce\u8fc4\u638d\u5931\u8d17"));
            }
            client.connectionPool().evictAll();
            object = a;
        }
    }

    public static void sendWsMessage(CommandEnum commandEnum, Project project) {
        CommandEnum a = project;
        CommandEnum a2 = commandEnum;
        PluginWebsocketClient.sendWsMessage(a2, null, (Project)a);
    }
}
