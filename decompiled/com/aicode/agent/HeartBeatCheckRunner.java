/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.map.MapUtil
 *  cn.hutool.core.util.IdUtil
 *  com.google.gson.Gson
 *  com.google.gson.JsonObject
 *  com.intellij.openapi.project.Project
 *  com.intellij.openapi.project.ProjectManager
 *  okhttp3.WebSocket
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.aicode.agent;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.aicode.PluginStartupActivity;
import com.aicode.action.CommitMessageSuggestionAction;
import com.aicode.action.PrepushReviewAction;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.service.ChatService;
import com.aicode.agent.service.PluginAgentProcessService;
import com.aicode.agent.service.RestartableAgentProcessService;
import com.aicode.content.util.OverlayUtils;
import com.aicode.enums.AICodeStatus;
import com.aicode.enums.RestartEnum;
import com.aicode.status.AICodeStatusService;
import com.aicode.util.ApplicationUtil;
import com.aicode.util.NewFileUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import okhttp3.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class HeartBeatCheckRunner {
    public static ConcurrentNavigableMap<String, HashMap<Long, Boolean>> AGENT_CLIENT_MAP;
    private static final Logger byte;
    private static final long enum = 10000L;

    static {
        byte = LoggerFactory.getLogger(HeartBeatCheckRunner.class);
        AGENT_CLIENT_MAP = new ConcurrentSkipListMap<String, HashMap<Long, Boolean>>();
    }

    public static void run() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            {
                Ga a;
            }

            @Override
            public void run() {
                HeartBeatCheckRunner.we();
            }
        }, 30000L, 30000L);
        Timer timer2 = new Timer();
        timer2.scheduleAtFixedRate(new TimerTask(){

            @Override
            public void run() {
                if (MapUtil.isEmpty(AGENT_CLIENT_MAP)) {
                    return;
                }
                HeartBeatCheckRunner.gd();
            }
            {
                ma a;
            }
        }, 30000L, 1000L);
    }

    public HeartBeatCheckRunner() {
        HeartBeatCheckRunner a;
    }

    private static synchronized void gd() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        AGENT_CLIENT_MAP.forEach((string, hashMap) -> {
            void a;
            AtomicInteger atomicInteger2 = atomicInteger;
            Iterator a2 = a.entrySet().iterator();
            block0: while (true) {
                Iterator iterator = a2;
                while (iterator.hasNext()) {
                    void a3;
                    Map.Entry entry = (Map.Entry)a2.next();
                    Long l = (Long)entry.getKey();
                    if (((Boolean)entry.getValue()).booleanValue()) {
                        iterator = a2;
                        atomicInteger2.getAndIncrement();
                        continue;
                    }
                    if (System.currentTimeMillis() - l <= 10000L) continue block0;
                    PluginWebsocketClient.AGENT_REQUEST.remove(a3);
                    a.put(l, true);
                    atomicInteger2.getAndIncrement();
                    continue block0;
                }
                break;
            }
        });
        if (atomicInteger.get() >= 2) {
            byte.info(OverlayUtils.H("\n(\u00014\u001f|\u0013cJ\u0014m.\b,\u00071W=\u0016\u000f)9@,\u001c?\u0014\u001b2)\u0002#@*\b'N\u001428\u0014f\u0019#\u00189\u0001(\u0003"));
            try {
                RestartableAgentProcessService restartableAgentProcessService;
                HeartBeatCheckRunner.handRequestOnAgentFail();
                Project project = ApplicationUtil.findCurrentProject();
                if (PluginStartupActivity.UNINSTALL.get()) {
                    byte.info("[HeartBeatCheckRunner] end: " + PluginStartupActivity.UNINSTALL.get());
                    return;
                }
                RestartableAgentProcessService restartableAgentProcessService2 = restartableAgentProcessService = (RestartableAgentProcessService)PluginAgentProcessService.getInstance();
                restartableAgentProcessService2.onRestartException(RestartEnum.HEART_BEAT_ERROR.getText(), RestartEnum.HEART_BEAT_ERROR.getCode());
                restartableAgentProcessService2.checkAgent(project);
                return;
            }
            finally {
                AGENT_CLIENT_MAP.clear();
            }
        }
    }

    public static void handRequestOnAgentFail() {
        CommitMessageSuggestionAction.COMMIT_MESSAGE_BUTTON.set(false);
        PrepushReviewAction.PREPUSH_REVIEW_BUTTON.set(false);
        AICodeStatusService.notifyApplication(AICodeStatus.Ready, "");
        Collection collection = PluginWebsocketClient.AGENT_REQUEST.values();
        if (collection.isEmpty()) {
            return;
        }
        for (Map.Entry<Project, List<MessageDto>> entry : collection.stream().filter(a -> {
            if (a.getProject() != null) {
                return true;
            }
            return false;
        }).collect(Collectors.groupingBy(MessageDto::getProject)).entrySet()) {
            Project project = entry.getKey();
            for (MessageDto messageDto : entry.getValue()) {
                String string = messageDto.getCommand();
                if (!CommandEnum.TALK_INTELLIGENT.getType().equals((Object)CommandEnum.getByType(string))) continue;
                ChatService.sendError2Web(new JsonObject(), project, messageDto);
            }
        }
        PluginWebsocketClient.AGENT_WEBSOCKETS.clear();
        PluginWebsocketClient.WEB_REQUEST.clear();
        PluginWebsocketClient.AGENT_REQUEST.clear();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void we() {
        if (PluginWebsocketClient.AGENT_WEBSOCKETS.isEmpty()) {
            return;
        }
        try {
            String string2;
            Object object;
            Project[] projectArray = ProjectManager.getInstance().getOpenProjects();
            if (projectArray != null) {
                int n;
                object = new ArrayList();
                int n2 = n = 0;
                while (n2 < projectArray.length) {
                    if (projectArray[n] != null && !projectArray[n].isDisposed()) {
                        object.add((String)projectArray[n].getBasePath());
                    }
                    n2 = ++n;
                }
                for (String string2 : PluginWebsocketClient.AGENT_WEBSOCKETS.keySet()) {
                    if (object.contains(string2)) continue;
                    PluginWebsocketClient.closeWebsocket(string2, NewFileUtils.H("E\u001aD\u0005h\f^\bT1H\u0017H\u0012\u007f\u0002V\u0018N\u001d"));
                    PluginWebsocketClient.AGENT_WEBSOCKETS.remove(string2);
                }
            }
            if ((object = PluginWebsocketClient.AGENT_WEBSOCKETS.values()) == null) return;
            if (object.size() == 0) {
                return;
            }
            WebSocket webSocket = (WebSocket)object.toArray()[0];
            if (webSocket == null) {
                return;
            }
            string2 = IdUtil.fastSimpleUUID();
            object = new HashMap<Long, Boolean>();
            ((HashMap)object).put(System.currentTimeMillis(), false);
            AGENT_CLIENT_MAP.put(string2, (HashMap<Long, Boolean>)object);
            object = CommandEnum.USER_VERSION.getType();
            Object object2 = new MessageDto(string2, (String)object);
            PluginWebsocketClient.AGENT_REQUEST.put(((MessageDto)object2).getId(), (MessageDto)object2);
            object2 = new Gson().toJson(object2);
            if (webSocket == null) return;
            byte.debug("request ws message ===========>\n" + (String)object);
            byte.debug("\u53d1\u9001\u5fc3\u8df3:{}" + string2);
            try {
                webSocket.send((String)object2);
                return;
            }
            catch (Exception exception) {
                byte.info("webSocket send error ===========>\n" + exception);
                return;
            }
        }
        catch (Throwable throwable) {
            byte.info("heart beat send error ===========>\n" + throwable);
        }
    }
}
