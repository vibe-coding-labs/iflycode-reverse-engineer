/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.intellij.openapi.application.ApplicationManager
 *  com.intellij.openapi.project.Project
 *  io.opentelemetry.api.trace.Span
 *  okhttp3.Response
 *  okhttp3.WebSocket
 *  okhttp3.WebSocketListener
 *  org.apache.commons.lang3.StringUtils
 *  org.jetbrains.annotations.NotNull
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.aicode.agent;

import com.aicode.PluginStartupActivity;
import com.aicode.agent.HeartBeatCheckRunner;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.service.CodeCompleteService;
import com.aicode.agent.service.PluginAgentProcessService;
import com.aicode.agent.service.RestartableAgentProcessService;
import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.apm.enums.TracerEnum;
import com.aicode.enums.RestartEnum;
import com.aicode.enums.WebViewDataTypeEnum;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import io.opentelemetry.api.trace.Span;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class PluginWebsocketListener
extends WebSocketListener {
    private final Project float;
    private boolean byte;
    private static final Logger enum = LoggerFactory.getLogger(PluginWebsocketListener.class);

    private static /* synthetic */ void enum(int a) {
        Object[] objectArray;
        Object[] objectArray2;
        String string = CodeCompleteService.H("h~ErUx\f3<_Ow\u0019\\eaQNWkw\u001eYm\\jR\u007fWc]*\u0000'Z+\u0018rf\u0005\u0000s\u0011?K=DyQs\tbQo\tnG'WiMh");
        Object[] objectArray3 = new Object[3];
        switch (a) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = CodeCompleteService.H("lLnqhZwDp");
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                while (false) {
                }
                objectArray3[0] = CodeCompleteService.H("[iQwVrRa");
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = CodeCompleteService.H("MyYp");
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = CodeCompleteService.H("PbXoNj");
                break;
            }
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = CodeCompleteService.H("p");
                break;
            }
        }
        objectArray2[1] = CodeCompleteService.H("JcC$^s@iKo\bcNiVi/uIuXsVJLnQhJg[oeeQs\\rDv");
        switch (a) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = CodeCompleteService.H("MivlDj");
                break;
            }
            case 2: 
            case 3: {
                objectArray = objectArray2;
                while (false) {
                }
                objectArray2[2] = CodeCompleteService.H("tGAGtJ}Fa");
                break;
            }
            case 4: 
            case 5: {
                objectArray = objectArray2;
                objectArray2[2] = CodeCompleteService.H("tGONhJuOc");
                break;
            }
            case 6: 
            case 7: {
                objectArray = objectArray2;
                objectArray2[2] = CodeCompleteService.H("tGJCnUiSa");
                break;
            }
        }
        throw new IllegalArgumentException(String.format(string, objectArray));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String string) {
        void a2;
        PluginWebsocketListener pluginWebsocketListener = pluginWebsocketListener2;
        PluginWebsocketListener pluginWebsocketListener2 = webSocket;
        PluginWebsocketListener webSocket2 = pluginWebsocketListener;
        if (pluginWebsocketListener2 == null) {
            PluginWebsocketListener.enum(2);
        }
        if (a2 == null) {
            PluginWebsocketListener.enum(3);
        }
        pluginWebsocketListener2 = webSocket2;
        synchronized (pluginWebsocketListener2) {
            block9: {
                PluginWebsocketListener pluginWebsocketListener3;
                try {
                    if (webSocket2.float.isDisposed()) {
                        enum.info(CodeCompleteService.H("kQiHbbP\teL:{s^xD}Lh"));
                        return;
                    }
                }
                catch (Throwable a2) {
                    enum.info("send error: " + a2);
                    pluginWebsocketListener3 = pluginWebsocketListener2;
                    break block9;
                }
                {
                    new SocketMessageHandleListener().handleSocketMessage((String)a2, webSocket2.float);
                    pluginWebsocketListener3 = pluginWebsocketListener2;
                }
            }
            // ** MonitorExit[v1] (shouldn't be in output)
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        void a;
        void webSocket2;
        PluginWebsocketListener pluginWebsocketListener = pluginWebsocketListener2;
        if (webSocket2 == null) {
            PluginWebsocketListener.enum(0);
        }
        if (a == null) {
            PluginWebsocketListener.enum(1);
        }
        RestartableAgentProcessService.restartAttempts.set(0);
        RestartableAgentProcessService.refreshTimes.set(1);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CodeCompleteService.H("]uRb"), WebViewDataTypeEnum.LOGIN_SHOW_FRESH.getType());
        PluginWebsocketListener pluginWebsocketListener2 = new JsonObject();
        pluginWebsocketListener2.addProperty(CodeCompleteService.H("Hk[uWCPulTrlDnGu"), 0);
        jsonObject.add(CodeCompleteService.H("oH`Wb"), (JsonElement)pluginWebsocketListener2);
        SocketMessageHandleListener.send2Web(pluginWebsocketListener.float, jsonObject);
        super.onOpen((WebSocket)webSocket2, (Response)a);
        PluginWebsocketClient.AGENT_WEBSOCKETS.put(pluginWebsocketListener.float.getBasePath(), (WebSocket)webSocket2);
        PluginWebsocketClient.wsInit(pluginWebsocketListener.float);
    }

    /*
     * WARNING - void declaration
     */
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable throwable, Response response) {
        RestartableAgentProcessService restartableAgentProcessService;
        void t;
        PluginWebsocketListener pluginWebsocketListener = pluginWebsocketListener2;
        PluginWebsocketListener pluginWebsocketListener2 = webSocket;
        PluginWebsocketListener webSocket2 = pluginWebsocketListener;
        if (pluginWebsocketListener2 == null) {
            PluginWebsocketListener.enum(6);
        }
        if (t == null) {
            PluginWebsocketListener.enum(7);
        }
        if (PluginStartupActivity.UNINSTALL.get()) {
            enum.info("[PluginWebsocketListener] onFailure: " + PluginStartupActivity.UNINSTALL.get());
            return;
        }
        if (ApplicationManager.getApplication().isDisposed()) {
            return;
        }
        enum.info(CodeCompleteService.H("LFdQhiDH|\u0005eXf\\lJrFb"), (Throwable)t);
        pluginWebsocketListener2 = OpenTelemetryUtil.buildWithTracer(TracerEnum.AGENT_FAILURE, ((Object)((Object)webSocket2)).getClass().getName());
        pluginWebsocketListener2.recordException((Throwable)t);
        HeartBeatCheckRunner.handRequestOnAgentFail();
        RestartableAgentProcessService a = (RestartableAgentProcessService)PluginAgentProcessService.getInstance();
        if (!webSocket2.byte) {
            enum.warn(CodeCompleteService.H("ZDcLs*LBfKeCq\u0019yLiF~"));
            if (StringUtils.contains((CharSequence)t.getMessage(), (CharSequence)CodeCompleteService.H("w\\zKhLh"))) {
                RestartableAgentProcessService restartableAgentProcessService2 = a;
                restartableAgentProcessService = restartableAgentProcessService2;
                restartableAgentProcessService2.onRestartException(RestartEnum.CONNECT_REFUSED.getText(), RestartEnum.CONNECT_REFUSED.getCode(), (Span)pluginWebsocketListener2);
            } else {
                RestartableAgentProcessService restartableAgentProcessService3 = a;
                if (StringUtils.contains((CharSequence)t.getMessage(), (CharSequence)CodeCompleteService.H("\u007f}WwLh"))) {
                    restartableAgentProcessService3.onRestartException(RestartEnum.CONNECT_FAILED.getText(), RestartEnum.CONNECT_FAILED.getCode(), (Span)pluginWebsocketListener2);
                    restartableAgentProcessService = a;
                } else {
                    restartableAgentProcessService3.onRestartException(t.getMessage(), RestartEnum.CONNECT_ERROR.getCode(), (Span)pluginWebsocketListener2);
                    restartableAgentProcessService = a;
                }
            }
        } else {
            enum.warn(CodeCompleteService.H("kIoS`\u0019yLiF~"));
            RestartableAgentProcessService restartableAgentProcessService4 = a;
            restartableAgentProcessService = restartableAgentProcessService4;
            restartableAgentProcessService4.onRestartException(RestartEnum.CLOSE_ERROR.getText(), RestartEnum.CLOSE_ERROR.getCode(), (Span)pluginWebsocketListener2);
        }
        restartableAgentProcessService.checkAgent(webSocket2.float);
        pluginWebsocketListener2.end();
    }

    /*
     * WARNING - void declaration
     */
    public void onClosing(@NotNull WebSocket webSocket, int n, @NotNull String string) {
        void code;
        void a;
        Span webSocket2;
        PluginWebsocketListener pluginWebsocketListener = restartableAgentProcessService;
        if (webSocket2 == null) {
            PluginWebsocketListener.enum(4);
        }
        if (a == null) {
            PluginWebsocketListener.enum(5);
        }
        pluginWebsocketListener.byte = true;
        if (!(StringUtils.contains((CharSequence)a, (CharSequence)CodeCompleteService.H("o[\u007f\u0005cNhXk")) || StringUtils.equals((CharSequence)a, (CharSequence)CodeCompleteService.H("V~BtVEjKTFKc\\qflMtNj")) || StringUtils.equals((CharSequence)a, (CharSequence)CodeCompleteService.H("ZRjIc\\nflMtNj")))) {
            block6: {
                webSocket2 = OpenTelemetryUtil.buildWithTracer(TracerEnum.AGENT_FAILURE, ((Object)((Object)pluginWebsocketListener)).getClass().getName());
                RestartableAgentProcessService restartableAgentProcessService = (RestartableAgentProcessService)PluginAgentProcessService.getInstance();
                if (!restartableAgentProcessService.isRunning()) break block6;
                restartableAgentProcessService.onReconnectException(RestartEnum.CLOSE_RECONNECT.getText(), RestartEnum.CLOSE_RECONNECT.getCode(), pluginWebsocketListener.float);
            }
            try {
                restartableAgentProcessService.onRestartException(RestartEnum.CLOSE_EXCEPTION.getText(), RestartEnum.CLOSE_EXCEPTION.getCode(), webSocket2);
                restartableAgentProcessService.checkAgent(pluginWebsocketListener.float);
            }
            catch (Exception exception) {
                enum.error(exception.getMessage(), (Throwable)exception);
                webSocket2.recordException((Throwable)exception);
            }
            webSocket2.end();
        }
        enum.info("CLOSE: " + (int)code + " " + (String)a);
    }

    public PluginWebsocketListener(Project project) {
        PluginWebsocketListener a;
        PluginWebsocketListener a2 = project;
        PluginWebsocketListener pluginWebsocketListener = a = this;
        pluginWebsocketListener.byte = false;
        pluginWebsocketListener.float = a2;
    }
}
