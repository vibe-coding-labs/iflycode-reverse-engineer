/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.map.MapUtil
 *  cn.hutool.core.util.IdUtil
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.intellij.openapi.project.Project
 *  com.intellij.openapi.project.ProjectManager
 *  okhttp3.WebSocket
 *  org.jetbrains.annotations.Nullable
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.aicode.agent;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.aicode.agent.HeartBeatCheckRunner;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.content.util.EditorUtils;
import com.aicode.content.util.file.FileExtensionLanguageDetails;
import com.aicode.enums.WebViewDataTypeEnum;
import com.aicode.util.ApplicationUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.WebSocket;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class AgentCheckTimer {
    public static Timer timeOutTimer;
    public static boolean isRunning;
    private static final long byte = 3000L;
    public static ConcurrentNavigableMap<String, HashMap<Long, Boolean>> AGENT_CHECK_MAP;
    private static final Logger enum;
    public static Timer timer;

    public AgentCheckTimer() {
        AgentCheckTimer a;
    }

    public static void stop() {
        if (!isRunning) {
            return;
        }
        timer.cancel();
        timer = null;
        timeOutTimer.cancel();
        timeOutTimer = null;
        isRunning = false;
        AGENT_CHECK_MAP.clear();
    }

    public static void run() {
        if (isRunning) {
            return;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            {
                ba a;
            }

            @Override
            public void run() {
                AgentCheckTimer.we();
            }
        }, 30000L, 2000L);
        timeOutTimer = new Timer();
        timeOutTimer.scheduleAtFixedRate(new TimerTask(){
            {
                ha a;
            }

            @Override
            public void run() {
                if (MapUtil.isEmpty(HeartBeatCheckRunner.AGENT_CLIENT_MAP)) {
                    return;
                }
                AgentCheckTimer.gd();
            }
        }, 30000L, 1000L);
        isRunning = true;
    }

    @Nullable
    private static WebSocket zE() {
        int n;
        if (PluginWebsocketClient.AGENT_WEBSOCKETS.isEmpty()) {
            return null;
        }
        Project[] projectArray = ProjectManager.getInstance().getOpenProjects();
        ArrayList<String> arrayList = new ArrayList<String>();
        Object object = projectArray;
        int n2 = projectArray.length;
        int n3 = n = 0;
        while (n3 < n2) {
            Project project = object[n];
            if (project != null && !project.isDisposed()) {
                arrayList.add(project.getBasePath());
            }
            n3 = ++n;
        }
        for (String string : PluginWebsocketClient.AGENT_WEBSOCKETS.keySet()) {
            if (arrayList.contains(string)) continue;
            PluginWebsocketClient.AGENT_WEBSOCKETS.remove(string);
        }
        object = PluginWebsocketClient.AGENT_WEBSOCKETS.values();
        if (object.isEmpty()) {
            return null;
        }
        WebSocket webSocket = (WebSocket)object.toArray()[0];
        if (webSocket == null) {
            return null;
        }
        return webSocket;
    }

    private static synchronized void gd() {
        AGENT_CHECK_MAP.entrySet().removeIf(a -> {
            if (!HeartBeatCheckRunner.AGENT_CLIENT_MAP.containsKey(a.getKey())) {
                return true;
            }
            return false;
        });
        AtomicInteger atomicInteger = new AtomicInteger(0);
        HeartBeatCheckRunner.AGENT_CLIENT_MAP.forEach((string, hashMap) -> {
            void a;
            void a2;
            AtomicInteger atomicInteger2 = atomicInteger;
            if (!AGENT_CHECK_MAP.containsKey(a2)) {
                return;
            }
            Iterator a3 = a.entrySet().iterator();
            block0: while (true) {
                Iterator iterator = a3;
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry)a3.next();
                    Long l = (Long)entry.getKey();
                    if (((Boolean)entry.getValue()).booleanValue()) {
                        iterator = a3;
                        atomicInteger2.getAndIncrement();
                        continue;
                    }
                    if (System.currentTimeMillis() - l <= 3000L) continue block0;
                    PluginWebsocketClient.AGENT_REQUEST.remove(a2);
                    a.put(l, true);
                    atomicInteger2.getAndIncrement();
                    continue block0;
                }
                break;
            }
        });
        if (atomicInteger.get() >= 3) {
            JsonObject jsonObject;
            enum.info(EditorUtils.H("/r-d9}dw(`ou%x%l|<aW\u0007`os5~#w(Y\u0004u`w#tkv&B\u0018:3{6b,s."));
            Project project = ApplicationUtil.findCurrentProject();
            JsonObject jsonObject2 = jsonObject = new JsonObject();
            jsonObject2.addProperty(FileExtensionLanguageDetails.H("qnbe"), WebViewDataTypeEnum.LOGIN_SHOW_FRESH.getType());
            jsonObject2.add(EditorUtils.H("3w!t?"), (JsonElement)new JsonObject());
            SocketMessageHandleListener.send2Web(project, jsonObject2);
            AgentCheckTimer.stop();
        }
    }

    private static void we() {
        String string = IdUtil.fastSimpleUUID();
        Object object = new HashMap<Long, Boolean>();
        ((HashMap)object).put(System.currentTimeMillis(), false);
        AGENT_CHECK_MAP.put(string, (HashMap<Long, Boolean>)object);
        HeartBeatCheckRunner.AGENT_CLIENT_MAP.put(string, (HashMap<Long, Boolean>)object);
        object = CommandEnum.USER_VERSION.getType();
        object = new MessageDto(string, (String)object);
        PluginWebsocketClient.AGENT_REQUEST.put(((MessageDto)object).getId(), (MessageDto)object);
        object = new Gson().toJson(object);
        WebSocket webSocket = AgentCheckTimer.zE();
        if (webSocket == null) {
            return;
        }
        enum.debug(FileExtensionLanguageDetails.H("\u53d4\u9016\u5fcd\u8def.t+-TVta%`}<ycPBi}`7>,6$\t\u001b4&0\"8)i}"), object);
        try {
            webSocket.send((String)object);
            return;
        }
        catch (Exception exception) {
            enum.info(EditorUtils.H("+8da\u0006w$s9=5b2TK~2d\"bk%~\u001aA'|#x+p<d"), (Throwable)exception);
            return;
        }
    }

    static {
        enum = LoggerFactory.getLogger(AgentCheckTimer.class);
        AGENT_CHECK_MAP = new ConcurrentSkipListMap<String, HashMap<Long, Boolean>>();
        isRunning = false;
    }
}
