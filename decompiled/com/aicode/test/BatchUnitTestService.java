package com.aicode.test;

import cn.hutool.core.util.IdUtil;
import com.aicode.action.batch.GeneratorConfig;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.content.util.EditorUtils;
import com.aicode.enums.WebViewDataTypeEnum;
import com.aicode.test.dto.BatchUnitTestDto;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.intellij.ide.actions.RevealFileAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: gc */
@Service
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/BatchUnitTestService.class */
public final class BatchUnitTestService {

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f621enum = LoggerFactory.getLogger(BatchUnitTestService.class);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: gc */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/BatchUnitTestService$g.class */
    public class g extends TypeToken<List<BatchUnitTestDto>> {
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static JsonObject batchUnitTestDownload(JsonObject a, MessageDto a2) {
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        JsonElement jsonElement = a.get(GeneratorConfig.H("\f\u001f\u001a\u0019"));
        if (!StringUtils.equals(GeneratorConfig.H("H"), jsonElement.getAsJsonObject().get(EditorUtils.H(",\u007f2S$r(")).getAsString())) {
            jsonObject = batchUnitTestMessage(false, EditorUtils.H("擝众大赨"));
        } else {
            jsonObject.addProperty(EditorUtils.H("d2f("), WebViewDataTypeEnum.BATCH_UNIT_TEST_REFRESH_TASK_DOWNLOAD_STATUS.getType());
            jsonObject2.addProperty(GeneratorConfig.H("\u0016\u0015\u001b\u0015'\u001c"), a2.getTaskId());
            jsonObject.add(EditorUtils.H("7q'c("), jsonObject2);
            Path path = Paths.get(jsonElement.getAsJsonObject().get(GeneratorConfig.H("\u0011\f\u0012")).getAsString(), new String[0]);
            if (Files.exists(path, new LinkOption[0])) {
                File file = new File(path.getParent().toString());
                ApplicationManager.getApplication().invokeLater(() -> {
                    RevealFileAction.openDirectory(file);
                });
            }
        }
        return jsonObject;
    }

    public static JsonObject codeBatchUnitTestList(JsonObject a) {
        List list = (List) new Gson().fromJson(a.get(GeneratorConfig.H("\f\u001f\u001a\u0019")), new g().getType());
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        jsonObject.addProperty(EditorUtils.H("d2f("), WebViewDataTypeEnum.BATCH_UNIT_TEST_GET_TASK_LIST.getType());
        jsonObject2.add(GeneratorConfig.H("\t\n\u0011\u001f$\u0017\u001d\f"), new Gson().toJsonTree(list));
        jsonObject.add(EditorUtils.H("7q'c("), jsonObject2);
        return jsonObject;
    }

    public static void batchUnitTestList(Project a) {
        PluginWebsocketClient.sendWsMessage(CommandEnum.CODE_BATCH_UNIT_TEST_LIST, a);
    }

    public static void batchUnitTestDownload(String a, Project a2) {
        JsonElement jsonElement = ((JsonObject) new Gson().fromJson(a, JsonObject.class)).get(GeneratorConfig.H("\u0002\t\u0012\u001b\u001d"));
        MessageDto messageDto = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.CODE_BATCH_UNIT_TEST_DOWNLOAD.getType());
        messageDto.setData(jsonElement.getAsString());
        messageDto.setTaskId(jsonElement.getAsString());
        PluginWebsocketClient.sendWsMessage(messageDto, a2);
    }

    public static void batchUnitTestCreate(String a, Project a2) {
        BatchUnitTestDto batchUnitTestDto = (BatchUnitTestDto) new Gson().fromJson(((JsonObject) new Gson().fromJson(a, JsonObject.class)).get(GeneratorConfig.H("\u0002\t\u0012\u001b\u001d")), BatchUnitTestDto.class);
        MessageDto messageDto = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.CODE_BATCH_UNIT_TEST_CREATE.getType());
        messageDto.setData(batchUnitTestDto);
        PluginWebsocketClient.sendWsMessage(messageDto, a2);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static JsonObject batchUnitTestMessage(boolean z, String a) {
        JsonObject jsonObject;
        JsonObject jsonObject2 = new JsonObject();
        JsonObject jsonObject3 = new JsonObject();
        jsonObject2.addProperty(EditorUtils.H("d2f("), WebViewDataTypeEnum.BATCH_UNIT_TEST_MESSAGE.getType());
        if (z) {
            jsonObject = jsonObject2;
            jsonObject3.addProperty(GeneratorConfig.H("\u001c\u0007\u001e\u001d"), EditorUtils.H("-o\"s.e>"));
            jsonObject3.addProperty(GeneratorConfig.H("\u0006\u0007\u0007\u001b\u001f\t\u001d"), EditorUtils.H("擝众戆勒"));
            jsonObject3.addProperty(GeneratorConfig.H("\u0019\u0007\u0012\u001a\u001b\u001d\u0010"), true);
        } else {
            jsonObject3.addProperty(EditorUtils.H("d2f("), GeneratorConfig.H("\u0011\u001a\f\u0001\n"));
            jsonObject3.addProperty(EditorUtils.H("3\u007f2c*q("), a);
            jsonObject3.addProperty(GeneratorConfig.H("\u0019\u0007\u0012\u001a\u001b\u001d\u0010"), true);
            jsonObject = jsonObject2;
        }
        jsonObject.add(EditorUtils.H("7q'c("), jsonObject3);
        return jsonObject2;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void handleAction(WebViewDataTypeEnum a, String a2, Project a3) {
        switch (a) {
            case BATCH_UNIT_TEST_CREATE:
                do {
                } while (0 != 0);
                batchUnitTestCreate(a2, a3);
                return;
            case BATCH_UNIT_TEST_GET_LIST:
                batchUnitTestList(a3);
                return;
            case BATCH_UNIT_TEST_DOWNLOAD:
                batchUnitTestDownload(a2, a3);
                return;
            case BATCH_UNIT_TEST_DELETE:
                batchUnitTestDelete(a2, a3);
                return;
            default:
                return;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void handleAgentAction(CommandEnum a, JsonObject a2, MessageDto a3, String a4, Project a5) {
        switch (a) {
            case CODE_BATCH_UNIT_TEST_CREATE:
                ConcurrentNavigableMap<String, MessageDto> concurrentNavigableMap = PluginWebsocketClient.AGENT_REQUEST;
                do {
                } while (0 != 0);
                concurrentNavigableMap.remove(a4);
                SocketMessageHandleListener.send2Web(a5, batchUnitTestMessage(true, null));
                return;
            case CODE_BATCH_UNIT_TEST_LIST:
                SocketMessageHandleListener.send2Web(a5, codeBatchUnitTestList(a2));
                return;
            case CODE_BATCH_UNIT_TEST_DOWNLOAD:
                SocketMessageHandleListener.send2Web(a5, batchUnitTestDownload(a2, a3));
                return;
            case CODE_BATCH_UNIT_TEST_CANCEL:
            case CODE_BATCH_UNIT_TEST_DELETE:
                SocketMessageHandleListener.send2Web(a5, batchUnitTestMessage(true, null));
                return;
            default:
                return;
        }
    }

    public static void batchUnitTestDelete(String a, Project a2) {
        JsonElement jsonElement = ((JsonObject) new Gson().fromJson(a, JsonObject.class)).get(EditorUtils.H("7q'c("));
        MessageDto messageDto = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.CODE_BATCH_UNIT_TEST_DELETE.getType());
        messageDto.setData(jsonElement.getAsString());
        PluginWebsocketClient.sendWsMessage(messageDto, a2);
    }
}
