/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.reflect.TypeToken
 *  com.intellij.ide.BrowserUtil
 *  com.intellij.openapi.application.Application
 *  com.intellij.openapi.application.ApplicationManager
 *  com.intellij.openapi.command.WriteCommandAction
 *  com.intellij.openapi.diagnostic.Logger
 *  com.intellij.openapi.project.Project
 *  org.apache.commons.lang3.StringUtils
 */
package com.aicode.agent.service;

import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.dto.CodeRepoInfoDto;
import com.aicode.agent.dto.CodeSearchInfoDto;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.content.util.EditorUtils;
import com.aicode.content.util.file.LanguageFileExtensionDetails;
import com.aicode.enums.CodeCollectEnum;
import com.aicode.enums.FileExtensionEnum;
import com.aicode.enums.WebViewDataTypeEnum;
import com.aicode.enums.WebViewResponseTypeEnum;
import com.aicode.test.dto.RequestCaseCodeDto;
import com.aicode.util.AICodeUtils;
import com.aicode.util.NewFileUtils;
import com.aicode.view.WebViewWindowPanel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class CodeSearchService {
    private static final Logger enum = Logger.getInstance(CodeSearchService.class);

    public static JsonObject getCodeSearchRepos(String string, JsonObject jsonObject) {
        Object object;
        Type type;
        String a = jsonObject;
        String a2 = string;
        Gson gson = new Gson();
        Integer n = 1;
        Integer n2 = 10;
        Integer n3 = 0;
        Integer n4 = 0;
        List<Object> list = new ArrayList();
        if (a != null && Objects.nonNull(type = a.get(LanguageFileExtensionDetails.H("g\u000fq\t")).getAsJsonObject())) {
            object = (CodeRepoInfoDto)gson.fromJson((JsonElement)type, CodeRepoInfoDto.class);
            n = object.getCurrentPage();
            n2 = object.getPageSize();
            n3 = object.getTotal();
            n4 = object.getTotalPage();
            list = object.getContent();
        }
        type = new TypeToken<List<CodeRepoInfoDto>>(){
            {
                ga a;
            }
        }.getType();
        object = gson.toJsonTree(list, type);
        String string2 = a = new JsonObject();
        String string3 = a;
        String string4 = a;
        string4.addProperty(AICodeUtils.H("ghakrh|\\cma"), (Number)n);
        string4.addProperty(LanguageFileExtensionDetails.H("f\u001an\u0001P\u0007\u007f\r"), (Number)n2);
        string3.addProperty(AICodeUtils.H("|cvkh"), (Number)n3);
        string3.addProperty(LanguageFileExtensionDetails.H("\u000by\u000fh\bS\u000fb\r"), (Number)n4);
        string2.addProperty(AICodeUtils.H("a|fsm\u007fvC`"), a2);
        string2.add(LanguageFileExtensionDetails.H("\u0018f\nw\u000bk\u001c"), object);
        Gson gson2 = gson = new JsonObject();
        gson2.addProperty(AICodeUtils.H("x{za"), WebViewResponseTypeEnum.CODE_SEARCH_GET_CODESEARCH_REPOSITORY_LIST.getType());
        gson2.add(LanguageFileExtensionDetails.H("\u0012b\u0002p\r"), (JsonElement)a);
        return gson2;
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void Ld(Application application, Project project, RequestCaseCodeDto.ValueDTO valueDTO) {
        void a;
        Object a2 = valueDTO;
        Application a3 = application;
        a3.runWriteAction(() -> CodeSearchService.tE((Project)a, (RequestCaseCodeDto.ValueDTO)a2));
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void Te(Application application, String string, Project project, RequestCaseCodeDto.ValueDTO valueDTO) {
        void a;
        void a2;
        Object a3 = valueDTO;
        Application a4 = application;
        a4.runReadAction(() -> CodeSearchService.eE((String)a2, (Project)a, (RequestCaseCodeDto.ValueDTO)a3));
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static void handleAgentAction(CommandEnum commandEnum, JsonObject jsonObject, String string, Project project) {
        void var2_3;
        void a = var2_3;
        CommandEnum a2 = commandEnum;
        switch (a2) {
            case GIT_LANG_LIST: {
                void a3;
                JsonObject a4;
                PluginWebsocketClient.AGENT_REQUEST.remove(a);
                JsonObject jsonObject2 = CodeSearchService.getCodeSearchLanguage(a4);
                SocketMessageHandleListener.send2Web((Project)a3, jsonObject2);
                return;
            }
            case GIT_USER_REPOS: {
                void a3;
                JsonObject a4;
                PluginWebsocketClient.AGENT_REQUEST.remove(a);
                JsonObject jsonObject3 = CodeSearchService.getCodeSearchRepos((String)a, a4);
                SocketMessageHandleListener.send2Web((Project)a3, jsonObject3);
                return;
            }
            case GIT_SEARCH: {
                void a3;
                JsonObject a4;
                PluginWebsocketClient.AGENT_REQUEST.remove(a);
                a4 = CodeSearchService.getCodeSearchCode((String)a, a4);
                SocketMessageHandleListener.send2Web((Project)a3, a4);
                return;
            }
        }
    }

    public static JsonObject getCodeSearchLanguage(JsonObject jsonObject) {
        JsonObject a;
        JsonObject jsonObject2 = jsonObject;
        JsonObject jsonObject3 = new JsonObject();
        if (jsonObject2 != null) {
            JsonObject jsonObject4;
            a = new JsonArray();
            try {
                a = jsonObject2.get(AICodeUtils.H("hc~e")).getAsJsonArray();
                jsonObject4 = jsonObject3;
            }
            catch (Exception exception) {
                enum.info("getCodeSearchLanguage Exception" + exception.getMessage());
                jsonObject4 = jsonObject3;
            }
            jsonObject4.add(LanguageFileExtensionDetails.H("\u0018f\nw\u000bk\u001c"), (JsonElement)a);
        }
        JsonObject jsonObject5 = a = new JsonObject();
        jsonObject5.addProperty(AICodeUtils.H("x{za"), WebViewResponseTypeEnum.CODE_SEARCH_GET_CODESEARCH_LANGUAGE_LIST.getType());
        jsonObject5.add(LanguageFileExtensionDetails.H("\u0012b\u0002p\r"), (JsonElement)jsonObject3);
        return jsonObject5;
    }

    public static JsonObject getCodeSearchCode(String string, JsonObject jsonObject) {
        Object object;
        Type type;
        String a = jsonObject;
        String a2 = string;
        Gson gson = new Gson();
        Integer n = 1;
        Integer n2 = 10;
        Integer n3 = 0;
        Integer n4 = 0;
        String string2 = "";
        List<Object> list = new ArrayList();
        if (a != null && Objects.nonNull(type = a.get(LanguageFileExtensionDetails.H("g\u000fq\t")).getAsJsonObject())) {
            object = (CodeSearchInfoDto)gson.fromJson((JsonElement)type, CodeSearchInfoDto.class);
            n = object.getCurrentPage();
            n2 = object.getPageSize();
            n3 = object.getTotal();
            n4 = object.getTotalPage();
            string2 = object.getType();
            list = object.getContent();
        }
        type = new TypeToken<List<CodeInfoDto>>(){
            {
                ia a;
            }
        }.getType();
        object = gson.toJsonTree(list, type);
        String string3 = a = new JsonObject();
        String string4 = a;
        String string5 = a;
        a.addProperty(AICodeUtils.H("a|fsm\u007fvC`"), a2);
        string5.addProperty(LanguageFileExtensionDetails.H("\u000bg\rd\u001eg\u0010S\u000fb\r"), (Number)n);
        string5.addProperty(AICodeUtils.H("ivam_kpa"), (Number)n2);
        string4.addProperty(LanguageFileExtensionDetails.H("\u0010l\u001ad\u0004"), (Number)n3);
        string4.addProperty(AICodeUtils.H("gvcgd\\cma"), (Number)n4);
        string3.addProperty(LanguageFileExtensionDetails.H("w\u0017u\r"), string2);
        string3.add(AICodeUtils.H("ikyv]vri@kyp"), object);
        Gson gson2 = gson = new JsonObject();
        gson2.addProperty(LanguageFileExtensionDetails.H("w\u0017u\r"), WebViewResponseTypeEnum.CODE_SEARCH_GET_CODESEARCH_CODE_LIST.getType());
        gson2.add(AICodeUtils.H("~mn\u007fa"), (JsonElement)a);
        return gson2;
    }

    public static JsonObject requestCopyCode(String string) {
        String string2 = string;
        Object a = (RequestCaseCodeDto)new Gson().fromJson(string2, RequestCaseCodeDto.class);
        if (Objects.isNull(a)) {
            return null;
        }
        if (Objects.isNull(((RequestCaseCodeDto)a).getValue())) {
            return null;
        }
        a = ((RequestCaseCodeDto)a).getValue();
        StringSelection stringSelection = new StringSelection(((RequestCaseCodeDto.ValueDTO)a).getCode());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        stringSelection = new JsonObject();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(AICodeUtils.H("c`"), ((RequestCaseCodeDto.ValueDTO)a).getId());
        StringSelection stringSelection2 = stringSelection;
        stringSelection2.addProperty(LanguageFileExtensionDetails.H("w\u0017u\r"), WebViewResponseTypeEnum.CODE_SEARCH_GET_CODE_COPY_SUCCESS.getType());
        stringSelection2.add(AICodeUtils.H("~mn\u007fa"), (JsonElement)jsonObject);
        return stringSelection2;
    }

    public static void requestOpenUrl(String string) {
        String string2 = string;
        Object a = (RequestCaseCodeDto)new Gson().fromJson(string2, RequestCaseCodeDto.class);
        if (Objects.isNull(a)) {
            return;
        }
        if (Objects.isNull(((RequestCaseCodeDto)a).getValue())) {
            return;
        }
        a = ((RequestCaseCodeDto)a).getValue();
        String string3 = ((RequestCaseCodeDto.ValueDTO)a).getUrl();
        if (StringUtils.equals((CharSequence)LanguageFileExtensionDetails.H("~\u0001a;w\u0004"), (CharSequence)((RequestCaseCodeDto.ValueDTO)a).getType())) {
            // empty if block
        }
        if (StringUtils.isBlank((CharSequence)string3)) {
            return;
        }
        BrowserUtil.browse((String)string3);
    }

    /*
     * WARNING - void declaration
     */
    public static void sendCodeRepoRequest(JsonObject jsonObject, Project project) {
        void a;
        JsonObject jsonObject2 = jsonObject;
        JsonObject jsonObject3 = (JsonObject)jsonObject2.get(LanguageFileExtensionDetails.H("\u0012b\u0002p\r"));
        Object a2 = jsonObject3.get(AICodeUtils.H("a|fsm\u007fvC`")).getAsString();
        Object object = a2 = new MessageDto((String)a2, CommandEnum.GIT_USER_REPOS.getType());
        ((MessageDto)object).setData(jsonObject3);
        PluginWebsocketClient.sendWsMessage((MessageDto)object, (Project)a);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static void handleAction(WebViewWindowPanel webViewWindowPanel, WebViewDataTypeEnum webViewDataTypeEnum, JsonObject jsonObject, String string, Project project) {
        JsonObject a;
        WebViewWindowPanel webViewWindowPanel2 = webViewWindowPanel;
        webViewWindowPanel = project;
        WebViewWindowPanel a2 = webViewWindowPanel2;
        switch (Aa.byte[a.ordinal()]) {
            case 1: {
                WebViewWindowPanel a3;
                void a4;
                CodeSearchService.sendCodeSearchRequest((JsonObject)a4, (Project)a3);
                return;
            }
            case 2: {
                WebViewWindowPanel a3;
                void a4;
                CodeSearchService.sendCodeRepoRequest((JsonObject)a4, (Project)a3);
                return;
            }
            case 3: {
                WebViewWindowPanel a3;
                PluginWebsocketClient.sendWsMessage(CommandEnum.GIT_LANG_LIST, (Project)a3);
                return;
            }
            case 4: {
                void a5;
                a = CodeSearchService.requestCopyCode((String)a5);
                a2.sendMessage2webView(a);
                return;
            }
            case 5: {
                void a5;
                WebViewWindowPanel a3;
                CodeSearchService.requestInsertCode((Project)a3, (String)a5);
                return;
            }
            case 6: {
                void a5;
                WebViewWindowPanel a3;
                CodeSearchService.requestCodeFile((Project)a3, (String)a5);
                return;
            }
            case 7: {
                void a5;
                CodeSearchService.requestOpenUrl((String)a5);
                return;
            }
        }
    }

    public CodeSearchService() {
        CodeSearchService a;
    }

    public static void requestInsertCode(Project project, String string) {
        Object a = string;
        Project a2 = project;
        if (Objects.isNull(a = (RequestCaseCodeDto)new Gson().fromJson((String)a, RequestCaseCodeDto.class))) {
            return;
        }
        if (Objects.isNull(((RequestCaseCodeDto)a).getValue())) {
            return;
        }
        a = ((RequestCaseCodeDto)a).getValue();
        Application application = ApplicationManager.getApplication();
        application.invokeLater(() -> CodeSearchService.Ld(application, a2, (RequestCaseCodeDto.ValueDTO)a));
    }

    /*
     * WARNING - void declaration
     */
    public static void sendCodeSearchRequest(JsonObject jsonObject, Project project) {
        void a;
        JsonObject jsonObject2 = jsonObject;
        JsonObject jsonObject3 = (JsonObject)jsonObject2.get(LanguageFileExtensionDetails.H("\u0012b\u0002p\r"));
        Object a2 = jsonObject3.get(AICodeUtils.H("a|fsm\u007fvC`")).getAsString();
        a2 = new MessageDto((String)a2, CommandEnum.GIT_SEARCH.getType());
        ((MessageDto)a2).setData(jsonObject3);
        jsonObject3 = AICodeUtils.getVirtualFile((Project)a);
        if (jsonObject3 != null) {
            ((MessageDto)a2).setPath(jsonObject3.getPath());
        }
        PluginWebsocketClient.sendWsMessage((MessageDto)a2, (Project)a);
    }

    private static /* synthetic */ void tE(Project project, RequestCaseCodeDto.ValueDTO valueDTO) {
        Project a;
        Object a2 = valueDTO;
        Project project2 = a = project;
        WriteCommandAction.runWriteCommandAction((Project)project2, () -> CodeSearchService.XE(project2, (RequestCaseCodeDto.ValueDTO)a2));
    }

    public static void requestCodeFile(Project project, String string) {
        Object a = string;
        Project a2 = project;
        if (Objects.isNull(a = (RequestCaseCodeDto)new Gson().fromJson((String)a, RequestCaseCodeDto.class))) {
            return;
        }
        if (Objects.isNull(((RequestCaseCodeDto)a).getValue())) {
            return;
        }
        a = ((RequestCaseCodeDto)a).getValue();
        String string2 = FileExtensionEnum.getLanguage(((RequestCaseCodeDto.ValueDTO)a).getLanguage());
        Application application = ApplicationManager.getApplication();
        application.invokeLater(() -> CodeSearchService.Te(application, string2, a2, (RequestCaseCodeDto.ValueDTO)a));
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void XE(Project project, RequestCaseCodeDto.ValueDTO valueDTO) {
        Project project2 = project;
        Project a = EditorUtils.getSelectedEditor(project2);
        if (a != null) {
            void a2;
            Project project3 = a;
            a = project3.getSelectionModel();
            project3.getDocument().replaceString(a.getSelectionStart(), a.getSelectionEnd(), (CharSequence)a2.getCode());
            project3.getContentComponent().requestFocus();
            a.removeSelection();
        }
    }

    /*
     * WARNING - void declaration
     */
    private static /* synthetic */ void eE(String string, Project project, RequestCaseCodeDto.ValueDTO valueDTO) {
        void a;
        void a2;
        String string2 = string;
        String a3 = "Untitled." + string2;
        NewFileUtils.handleCreateFile((Project)a2, a.getCode(), a3, "", AICodeUtils.H("Omna"), CodeCollectEnum.NEW);
    }
}
