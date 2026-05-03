/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.util.IdUtil
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.reflect.TypeToken
 *  com.intellij.ide.BrowserUtil
 *  com.intellij.notification.Notification
 *  com.intellij.notification.NotificationAction
 *  com.intellij.notification.NotificationGroupManager
 *  com.intellij.openapi.actionSystem.AnAction
 *  com.intellij.openapi.actionSystem.AnActionEvent
 *  com.intellij.openapi.application.ApplicationInfo
 *  com.intellij.openapi.application.ApplicationManager
 *  com.intellij.openapi.diagnostic.Logger
 *  com.intellij.openapi.editor.markup.RangeHighlighter
 *  com.intellij.openapi.project.Project
 *  com.intellij.openapi.project.ProjectManager
 *  com.intellij.openapi.ui.ComboBox
 *  com.intellij.openapi.ui.MessageType
 *  com.intellij.openapi.wm.ToolWindowManager
 *  org.apache.commons.collections.CollectionUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.jetbrains.annotations.NotNull
 */
package com.aicode.agent.service;

import cn.hutool.core.util.IdUtil;
import com.aicode.PluginStartupActivity;
import com.aicode.action.RefreshAction;
import com.aicode.agent.HeartBeatCheckRunner;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.dto.CodeModel;
import com.aicode.agent.dto.EnterpriseDto;
import com.aicode.agent.dto.FunctionModelInfo;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.TipInfoDto;
import com.aicode.agent.dto.UserInfoDto;
import com.aicode.agent.dto.chat.FirstChatMessage;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.enums.PermissionEnum;
import com.aicode.agent.service.ChatService;
import com.aicode.agent.service.CommonService;
import com.aicode.agent.service.GitReviewService;
import com.aicode.agent.service.SqlService;
import com.aicode.content.util.EditorUtils;
import com.aicode.diff.GenericUtils;
import com.aicode.enums.WebViewDataTypeEnum;
import com.aicode.enums.WebViewResponseTypeEnum;
import com.aicode.listener.GitBranchChangeListener;
import com.aicode.message.BasicActionsBundle;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.statusBar.StatusBarPopup;
import com.aicode.util.ApplicationUtil;
import com.aicode.util.IndentLineUtil;
import com.aicode.util.PositionUtil;
import com.aicode.view.WebViewWindowPanel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.intellij.ide.BrowserUtil;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationAction;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.wm.ToolWindowManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.invoke.LambdaMetafactory;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.swing.SwingUtilities;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class UserService {
    private static final Logger byte = Logger.getInstance(UserService.class);
    private static String enum;
    public static boolean goTo;

    public static void showMessage(Project project) {
        Project project2 = project;
        Object[] objectArray = new Object[1];
        objectArray[0] = BasicActionsBundle.message(GenericUtils.H("\n!41<n)64:96u\u0000)7\"6#\u0000*\u000f\u001a<5\u0002?*8'q%<+/"), new Object[0]);
        Object a = String.format(BasicActionsBundle.message(PositionUtil.H("_4z\u0004WgN*[7\u0013(V;M0"), new Object[0]), objectArray);
        a = NotificationGroupManager.getInstance().getNotificationGroup(PositionUtil.H("\u0000P*@;JqS\"O\"Z,")).createNotification((String)a, MessageType.INFO).setTitle(BasicActionsBundle.message(GenericUtils.H("\n!41<n)64:96u\u0000)7\"6#\u0000*\u000f\u001a<5\u0002?*8'q%<+/"), new Object[0]));
        a.addAction((AnAction)new NotificationAction(PositionUtil.H("\u53f0\u7642\u5f1c"), project2){
            public final /* synthetic */ Project enum;

            /*
             * WARNING - void declaration
             */
            private static /* synthetic */ void Fe(Project project, JsonObject jsonObject, Notification notification) {
                Project project2 = project;
                try {
                    void a;
                    Object a2;
                    while (Objects.isNull(a2 = (WebViewWindowPanel)project2.getUserData(WebViewWindowPanel.WEB_VIEW_PANEL)) || !a2.isLoaded.get()) {
                    }
                    if (null != UserService.getLoginUrl() && !UserService.getLoginUrl().isEmpty()) {
                        void a3;
                        SocketMessageHandleListener.send2Web(project2, a3);
                        BrowserUtil.browse((String)(UserService.getLoginUrl() + "&pluginVersion=" + BasicActionsBundle.message(IndentLineUtil.H("V u\u0010D\r\u0019\u001c_\u0006K\u0010HQV[\u0013\rH\u0014J"), new Object[0]) + "&ideType=IDEA&type=outer"));
                        UserService.setGoTo(false);
                    }
                    a.notify(project2);
                    return;
                }
                catch (Exception a2) {
                    return;
                }
            }

            /*
             * WARNING - void declaration
             */
            public void actionPerformed(@NotNull AnActionEvent anActionEvent, @NotNull Notification notification) {
                void a;
                da da2 = da3;
                da da3 = anActionEvent;
                da e = da2;
                if (da3 == null) {
                    da.enum(0);
                }
                if (a == null) {
                    da.enum(1);
                }
                if (StringUtils.isNotBlank((CharSequence)PluginStartupActivity.getApiKey())) {
                    return;
                }
                UserService.setGoTo(true);
                da3 = ToolWindowManager.getInstance((Project)da3.getProject()).getToolWindow(BasicActionsBundle.message(GitReviewService.H("\\2\u00140\u000ek\u001f.\u001f?\u000f(X\u000e\u0014#\u0005\u001b=\u0000\u0019\"\u0004-\u0017\f\u00029\u00189\\5\u001f8\u000f"), new Object[0]));
                if (da3 != null) {
                    da3.show();
                    a.expire();
                    da da4 = da3 = new JsonObject();
                    da4.addProperty(IndentLineUtil.H("\nX\u000bA"), WebViewDataTypeEnum.LOGIN_GO_LOGIN.getType());
                    da4.add(GitReviewService.H("\u0004 \u00165\u001e"), (JsonElement)new JsonObject());
                    ApplicationManager.getApplication().executeOnPooledThread(() -> da.Fe(e.enum, (JsonObject)da3, (Notification)a));
                }
            }
            {
                void a;
                da a2 = object;
                object = this;
                object.enum = a2;
                super((String)a);
            }

            private static /* synthetic */ void enum(int a) {
                Object[] objectArray;
                String string = GitReviewService.H("\u0002\n&\u000f'\u0014>\u001f*W8\u001ea:\u0018\u0002&'8\u001a+\\1\u001bxP\u001c/#\t3ZqH\u0004ka\u00150Mw\u001acS4\\,\u000fsO`\u0015*\na\u0018/Q?\u001f%\u001e");
                Object[] objectArray2 = new Object[3];
                switch (a) {
                    default: {
                        objectArray = objectArray2;
                        objectArray2[0] = IndentLineUtil.H("A");
                        break;
                    }
                    case 1: {
                        objectArray = objectArray2;
                        while (false) {
                        }
                        objectArray2[0] = GitReviewService.H(".\u00141\u0017'\u0013)\u0010%\u0003&\u001c");
                        break;
                    }
                }
                objectArray[1] = IndentLineUtil.H("\u000f\\\u001e\u0003\u0018O\u001cOP\u000e`q\u000eS\u0011TGD,d\tI\u000bRCf\u0000I\u000bu\u001aRH\b\u001dD_\u0017");
                objectArray[2] = GitReviewService.H("\u001bcO)\u0014+.$\b,\u001e#\u0007,\u0016");
                throw new IllegalArgumentException(String.format(string, objectArray));
            }
        });
        a.notify(project2);
    }

    private static void lE(Project project) {
        Project project2 = project;
        Object a = project2.getUserData(WebViewWindowPanel.OPEN_PAGE_DATA);
        if (a != null) {
            SocketMessageHandleListener.send2Web(project2, a);
            project2.putUserData(WebViewWindowPanel.OPEN_PAGE_DATA, null);
        }
        if ((a = (FirstChatMessage)project2.getUserData(WebViewWindowPanel.CODE_MESSAGE_DATA)) != null) {
            Project project3 = project2;
            CommonService.chatMessage2Web(project3, (FirstChatMessage)a, false);
            project3.putUserData(WebViewWindowPanel.CODE_MESSAGE_DATA, null);
        }
        if ((a = (FirstChatMessage)project2.getUserData(WebViewWindowPanel.CODE_DEBUG_MESSAGE_DATA)) != null) {
            MessageDto messageDto = (MessageDto)project2.getUserData(WebViewWindowPanel.CODE_DEBUG_AGENT_DATA);
            if (messageDto != null) {
                CommonService.chatMessage2Web(project2, (FirstChatMessage)a, false);
                PluginStartupActivity.handleExecutorService.execute(() -> {
                    MessageDto a = project2;
                    MessageDto a2 = messageDto;
                    PluginWebsocketClient.sendWsMessage(a2, (Project)a);
                });
            }
            Project project4 = project2;
            project4.putUserData(WebViewWindowPanel.CODE_DEBUG_MESSAGE_DATA, null);
            project4.putUserData(WebViewWindowPanel.CODE_DEBUG_AGENT_DATA, null);
        }
    }

    public UserService() {
        UserService a;
    }

    public static void repaintModelComboBox(ComboBox a) {
        if (a != null) {
            ComboBox comboBox = a;
            comboBox.revalidate();
            comboBox.repaint();
        }
    }

    public static void SetModel(JsonObject jsonObject) {
        JsonObject jsonObject2 = jsonObject;
        CodeModel a = (CodeModel)new Gson().fromJson(jsonObject2.get(PositionUtil.H(">X%E%")), CodeModel.class);
        AICodeSettingsState.getInstance().modelCode = a.getModelCode();
    }

    public static void setGoTo(boolean a) {
        goTo = a;
    }

    public static boolean isGoTo() {
        return goTo;
    }

    /*
     * WARNING - void declaration
     */
    public static void setItem(ComboBox comboBox, List<CodeModel> list) {
        boolean bl;
        void a;
        ComboBox comboBox2 = comboBox;
        boolean bl2 = false;
        for (Object a2 : a) {
            ((CodeModel)a2).setOriginalModelName(((CodeModel)a2).getModelName());
            if (((CodeModel)a2).isTokenExhausted()) {
                Object object = a2;
                ((CodeModel)object).setModelName(((CodeModel)object).getModelName() + " (\u672c\u6708\u6b21\u6570\u5df2\u7528\u5c3d)");
            }
            comboBox2.addItem(a2);
            if (!StringUtils.equals((CharSequence)((CodeModel)a2).getModelCode(), (CharSequence)AICodeSettingsState.getInstance().inlineChatModelCode) || ((CodeModel)a2).isTokenExhausted()) continue;
            bl2 = true;
            comboBox2.setSelectedItem(a2);
        }
        if (!bl2) {
            for (Object a2 : a) {
                if (((CodeModel)a2).isTokenExhausted()) continue;
                bl = bl2 = true;
                comboBox2.setSelectedItem(a2);
                AICodeSettingsState.getInstance().inlineChatModelCode = ((CodeModel)a2).getModelCode();
                break;
            }
        } else {
            bl = bl2;
        }
        if (!bl) {
            comboBox2.setSelectedItem(a.get(0));
            AICodeSettingsState.getInstance().inlineChatModelCode = ((CodeModel)a.get(0)).getModelCode();
        }
    }

    public static String getLoginUrl() {
        return enum;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void send2WebShowOperateGuidance(JsonObject jsonObject, Project project) {
        JsonObject jsonObject2 = jsonObject;
        try {
            void a;
            String string;
            if (jsonObject2.get(PositionUtil.H("9[2V!_&")) == null) {
                byte.info(GenericUtils.H("&3'666:\u4e50\u5b0b\u5773"));
                return;
            }
            JsonArray jsonArray = jsonObject2.get(PositionUtil.H("9[2V!_&")).getAsJsonArray();
            boolean bl = jsonArray == null || jsonArray.size() == 0;
            if (!bl) {
                Iterator iterator;
                string = ApplicationInfo.getInstance().getVersionName();
                String a2 = jsonObject2.get(GenericUtils.H(" .6)")) != null ? jsonObject2.get(PositionUtil.H("J<\\;")).getAsString() : AICodeSettingsState.getInstance().userName;
                Iterator iterator2 = iterator = jsonArray.iterator();
                while (iterator2.hasNext()) {
                    Object object = (JsonElement)iterator.next();
                    object = (TipInfoDto)new Gson().fromJson((JsonElement)object, TipInfoDto.class);
                    String string2 = ((TipInfoDto)object).getUser();
                    String string3 = ((TipInfoDto)object).getPlatform();
                    if (StringUtils.equals((CharSequence)string2, (CharSequence)a2) && string.contains(string3)) {
                        bl = ((TipInfoDto)object).getShowOperateGuide();
                        break;
                    }
                    bl = true;
                    iterator2 = iterator;
                }
            }
            string = new JsonObject();
            string.addProperty(GenericUtils.H("!$#>"), PositionUtil.H("`\u0006\u007f\u000fZ-kfj\f`\u001ab\u001e`\u0017n\u0016T;l\u000bt\u0011}\u0017z\u0018{\u0006~\u0001z\f"));
            JsonObject a2 = new JsonObject();
            a2.addProperty(GenericUtils.H(":(\"\u0011,<\u0010'7(6+=\u0017 47>"), Boolean.valueOf(bl));
            string.add(PositionUtil.H("4^#L,"), (JsonElement)a2);
            SocketMessageHandleListener.send2Web((Project)a, string);
            return;
        }
        catch (Exception exception) {
            String[] stringArray = new String[1];
            stringArray[0] = exception.getMessage();
            byte.error(GenericUtils.H(":\u001c\u001f\"|\u0004>&\u001f?05\u0005#>\u0003\u00187.\u0018\";>61;5\u5384\u905c\u5f51\u5e63"), stringArray);
            return;
        }
    }

    public static void logout(Project a) {
        AICodeSettingsState.getInstance().clear();
        PluginWebsocketClient.sendWsMessage(CommandEnum.USER_LOGOUT, a);
    }

    public static JsonArray sortJsonArray(JsonArray jsonArray, List<String> list) {
        String a;
        Object a22;
        JsonArray jsonArray2 = jsonArray;
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        for (Object a22 : jsonArray2) {
            if (!a22.isJsonPrimitive() || !a22.getAsJsonPrimitive().isString()) continue;
            hashMap.put(a22.getAsString(), a22);
        }
        Iterator iterator = new JsonArray();
        a22 = a.iterator();
        while (a22.hasNext()) {
            a = (String)a22.next();
            if (!hashMap.containsKey(a)) continue;
            iterator.add((JsonElement)hashMap.get(a));
        }
        return iterator;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void handleAgentAction(CommandEnum commandEnum, JsonObject jsonObject, String string, Object object, Project project) {
        CommandEnum commandEnum2 = commandEnum;
        commandEnum = project;
        CommandEnum a = commandEnum2;
        switch (a) {
            case USER_LOGIN: {
                CommandEnum a2;
                JsonObject a32;
                void a4;
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                JsonObject jsonObject2 = UserService.getLoginUrl((String)a32);
                if (StringUtils.equals((CharSequence)PluginWebsocketClient.INITID, (CharSequence)a4) && StringUtils.isNotBlank((CharSequence)((String)a32))) {
                    UserService.showMessage((Project)a2);
                }
                if (!StringUtils.isNotBlank((CharSequence)((String)a32))) break;
                UserService.setLoginUrl((String)a32);
                SocketMessageHandleListener.send2Web((Project)a2, jsonObject2);
                return;
            }
            case USER_LOGOUT: {
                Iterator<Project> iterator;
                CommandEnum a2;
                JsonObject a32;
                void a4;
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                JsonObject jsonObject3 = UserService.getLoginUrl((String)a32);
                Iterator<Project> iterator2 = iterator = ApplicationUtil.findValidProjects().iterator();
                while (true) {
                    if (!iterator2.hasNext()) {
                        UserService.uD();
                        return;
                    }
                    Project project2 = iterator.next();
                    if (project2 == null) {
                        iterator2 = iterator;
                        continue;
                    }
                    if (StringUtils.isNotBlank((CharSequence)((String)a32))) {
                        SocketMessageHandleListener.send2Web(project2, jsonObject3);
                    }
                    JsonObject jsonObject4 = new JsonObject();
                    iterator2 = iterator;
                    JsonObject jsonObject5 = jsonObject4;
                    jsonObject5.addProperty(GenericUtils.H("-((5"), WebViewDataTypeEnum.LOGIN_LOGIN_SUCCEED.getType());
                    jsonObject5.addProperty(PositionUtil.H("3R/G'"), Boolean.valueOf(false));
                    SocketMessageHandleListener.send2Web(project2, jsonObject5);
                    UserService.clearIcon(project2);
                    ChatService.SESSION_ID.put(a2.getBasePath(), IdUtil.fastSimpleUUID());
                    SqlService.SQL_SESSION_ID.put(a2.getBasePath(), IdUtil.fastSimpleUUID());
                    CommonService.messageBus(project2, GenericUtils.H("\u904a\u51a5\u762c\u5f0c\u6241\u52c7\uff51"), MessageType.INFO);
                }
            }
            case USER_VERSION: {
                JsonObject a32;
                void a4;
                if (a32 != null) {
                    HeartBeatCheckRunner.AGENT_CLIENT_MAP.clear();
                }
                RefreshAction.REFRESH_MAP.remove(a4);
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                return;
            }
            case USER_MODEL_LIST: 
            case MODEL_LIST_TIMER: {
                void a5;
                CommandEnum a2;
                void a4;
                MessageDto messageDto = (MessageDto)PluginWebsocketClient.AGENT_REQUEST.get(a4);
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                JsonObject jsonObject6 = UserService.getUserModelList((JsonObject)a5, messageDto);
                SocketMessageHandleListener.send2Web((Project)a2, jsonObject6);
                return;
            }
            case LOGIN_INFO: {
                void a5;
                CommandEnum a2;
                void a4;
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                JsonObject jsonObject7 = UserService.getLoginInfo((JsonObject)a5, (Project)a2);
                SocketMessageHandleListener.send2Web((Project)a2, jsonObject7);
                UserService.send2WebShowOperateGuidance(a5.get(PositionUtil.H("W\"F#")).getAsJsonObject(), (Project)a2);
                JsonObject a32 = CommonService.getConfig();
                SocketMessageHandleListener.send2Web((Project)a2, a32);
                CommandEnum commandEnum3 = a2;
                CommonService.refreshDocumentStruct((Project)commandEnum3);
                UserService.sendWriterConfig((Project)commandEnum3, (JsonObject)a5);
                PluginWebsocketClient.sendWsMessage(CommandEnum.USER_CAN_CODE_ENHANCE, (Project)a2);
                try {
                    boolean a32 = a5.get(GenericUtils.H("=0,1")).getAsJsonObject().get(PositionUtil.H("*P\t\\$[,")).getAsBoolean();
                    if (a32) {
                        CommonService.messageBus((Project)a2, GenericUtils.H("\u762c\u5f0c\u6241\u52c7\uff51"), MessageType.INFO);
                    }
                }
                catch (Throwable a32) {
                    byte.info(PositionUtil.H("\u7653\u5f0d\u6225\u52da\u63e3\u7979\u5903\u8d67"), a32);
                }
                CommonService.getPluginInfo((Project)a2);
                UserService.uD();
                return;
            }
            case USER_PERMISSION: {
                void a5;
                CommandEnum a2;
                void a4;
                PluginWebsocketClient.AGENT_REQUEST.remove(a4);
                CommandEnum commandEnum4 = a2;
                UserService.getUserPermissions((JsonObject)a5, (Project)commandEnum4);
                UserService.lE((Project)commandEnum4);
                GitBranchChangeListener.codeKnowledgeNotification((Project)commandEnum4);
                return;
            }
        }
    }

    public static void setLoginUrl(String a) {
        enum = a;
    }

    private static void uD() {
        int n;
        Project[] projectArray = ProjectManager.getInstance().getOpenProjects();
        int n2 = projectArray.length;
        int n3 = n = 0;
        while (n3 < n2) {
            Project project = projectArray[n];
            if (!project.isDisposed()) {
                StatusBarPopup.update(project);
            }
            n3 = ++n;
        }
    }

    public static JsonObject getLoginInfo(JsonObject jsonObject, Project project) {
        JsonElement a;
        JsonObject jsonObject2 = jsonObject;
        JsonObject jsonObject3 = null;
        if (Objects.isNull(jsonObject2.get(PositionUtil.H("^+G\"")))) {
            return null;
        }
        UserInfoDto a2 = (UserInfoDto)new Gson().fromJson(jsonObject2.get(GenericUtils.H("49-0")), UserInfoDto.class);
        AICodeSettingsState aICodeSettingsState = AICodeSettingsState.getInstance();
        UserInfoDto userInfoDto = a2;
        PluginStartupActivity.setApiKey(userInfoDto.getToken());
        AICodeSettingsState aICodeSettingsState2 = aICodeSettingsState;
        Object object = a2;
        AICodeSettingsState aICodeSettingsState3 = aICodeSettingsState;
        Object object2 = a2;
        aICodeSettingsState.feedbackUrl = ((UserInfoDto)object2).getSysUrls().getFeedbackUrl();
        aICodeSettingsState3.maintainRepoUrl = ((UserInfoDto)object2).getSysUrls().getMaintainRepoUrl();
        aICodeSettingsState3.codeSearchServerUrl = a2.getSysUrls().getCodeSearchServerUrl();
        aICodeSettingsState.officialWebsiteUrl = ((UserInfoDto)object).getSysUrls().getOfficialWebsiteUrl();
        aICodeSettingsState2.codeKnowledgeWebUrl = ((UserInfoDto)object).getSysUrls().getCodeKnowledgeWebUrl();
        aICodeSettingsState2.userCenterWebUrl = a2.getSysUrls().getUserCenterWebUrl();
        AICodeSettingsState.getInstance().userName = userInfoDto.getUser();
        EnterpriseDto enterpriseDto = userInfoDto.getEnterpriseDto();
        if (enterpriseDto != null) {
            AICodeSettingsState aICodeSettingsState4 = aICodeSettingsState;
            EnterpriseDto enterpriseDto2 = enterpriseDto;
            aICodeSettingsState.userId = enterpriseDto2.getUserId();
            aICodeSettingsState4.enterpriseName = enterpriseDto2.getEnterpriseName();
            aICodeSettingsState4.enterpriseId = enterpriseDto.getEnterpriseId();
        }
        PluginWebsocketClient.sendWsMessage(CommandEnum.USER_MODEL_LIST, (Project)a);
        PluginWebsocketClient.sendWsMessage(CommandEnum.USER_PERMISSION, (Project)a);
        jsonObject3 = new JsonObject();
        a = new Gson().toJsonTree((Object)enterpriseDto);
        aICodeSettingsState = new Gson().toJsonTree((Object)a2.getSysUrls());
        jsonObject3.addProperty(PositionUtil.H("N3C&"), WebViewDataTypeEnum.LOGIN_LOGIN_SUCCEED.getType());
        EnterpriseDto enterpriseDto3 = enterpriseDto = new JsonObject();
        EnterpriseDto enterpriseDto4 = enterpriseDto;
        enterpriseDto.addProperty(GenericUtils.H("%+<#"), a2.getUser());
        enterpriseDto4.add(PositionUtil.H("\u0016K1o8_0"), (JsonElement)aICodeSettingsState);
        enterpriseDto4.add(GenericUtils.H("<1#=\"\u000f\u00051#5\u001c->"), a);
        enterpriseDto3.addProperty(PositionUtil.H("5S!~\u0004U'y%W&"), a2.getPackageCode());
        enterpriseDto3.addProperty(GenericUtils.H("'93\u0014\u0016?5\u001e944"), a2.getPackageName());
        UserInfoDto userInfoDto2 = a2;
        enterpriseDto3.addProperty(PositionUtil.H("\u0017W\u000eU-Z-"), userInfoDto2.isReLogin());
        if (userInfoDto2.isReLogin()) {
            AICodeSettingsState.getInstance().showSaasQrCode = true;
        }
        enterpriseDto.addProperty(GenericUtils.H("\"10 \u000b1\u001e\u0004\t\"\u00137=4"), AICodeSettingsState.getInstance().showSaasQrCode);
        JsonObject jsonObject4 = jsonObject3;
        jsonObject4.add(PositionUtil.H("4[&F&"), (JsonElement)enterpriseDto);
        return jsonObject4;
    }

    public static JsonObject getLoginUrl(String string) {
        String a;
        String string2 = string;
        JsonObject jsonObject = null;
        jsonObject = new JsonObject();
        jsonObject.addProperty(GenericUtils.H("05*7"), WebViewDataTypeEnum.LOGIN_RECEIVER_LOGIN_IFRAME_SRC.getType());
        String string3 = a = new JsonObject();
        string3.addProperty(PositionUtil.H("^-n\u0010@\u000bB,"), string2);
        string3.addProperty(GenericUtils.H("!=,8>6\u0006\u0006\u00197%5<"), BasicActionsBundle.message(PositionUtil.H("*P*G<uNC/N,\\+\u001c4l\u000b]7_."), new Object[0]));
        JsonObject jsonObject2 = jsonObject;
        jsonObject2.add(GenericUtils.H("\u001d% /7"), (JsonElement)a);
        return jsonObject2;
    }

    private static /* synthetic */ void ef(ItemEvent itemEvent) {
        ItemEvent itemEvent2 = itemEvent;
        CodeModel a = (CodeModel)itemEvent2.getItem();
        AICodeSettingsState.getInstance().inlineChatModelCode = a.getModelCode();
    }

    /*
     * WARNING - void declaration
     */
    public static void getUserPermissions(JsonObject jsonObject, Project project) {
        void a;
        String string;
        Object object;
        JsonElement jsonElement;
        JsonObject jsonObject2 = jsonObject;
        JsonArray jsonArray = new JsonArray();
        Object a2 = new JsonArray();
        if (jsonObject2.has(GenericUtils.H("<11,"))) {
            a2 = jsonObject2.getAsJsonArray(PositionUtil.H("V#[>"));
        }
        AICodeSettingsState aICodeSettingsState = AICodeSettingsState.getInstance();
        a2 = a2.iterator();
        Iterator iterator = a2;
        while (iterator.hasNext()) {
            jsonElement = (JsonElement)a2.next();
            object = jsonElement.getAsJsonObject();
            string = object.get(GenericUtils.H("/27 ,>,>6?\u001b?!(")).getAsString();
            iterator = a2;
            jsonArray.add(string);
        }
        a2 = UserService.sortJsonArray(jsonArray, PermissionEnum.PERMISSION_ORDER_LIST);
        jsonElement = new Gson();
        object = new TypeToken<List<String>>(){
            {
                la a;
            }
        }.getType();
        aICodeSettingsState.permissions.addAll((Collection)jsonElement.fromJson((JsonElement)a2, (Type)object));
        aICodeSettingsState.permissions.addAll((Collection)jsonElement.fromJson((JsonElement)jsonArray, (Type)object));
        AICodeSettingsState aICodeSettingsState2 = aICodeSettingsState;
        aICodeSettingsState2.enableCodeDebug = aICodeSettingsState2.permissions.contains(PermissionEnum.CODE_DEBUG.getPermission());
        aICodeSettingsState2.enableCodeComplete = aICodeSettingsState2.permissions.contains(PermissionEnum.CHAT_MODULE.getPermission());
        string = new JsonObject();
        string.add(PositionUtil.H("^;C,\\6\\6@1v*W&~+\\+"), (JsonElement)jsonArray);
        JsonArray jsonArray2 = jsonArray = new JsonObject();
        jsonArray2.addProperty(GenericUtils.H(",)5("), WebViewResponseTypeEnum.USER_PERMISSION_LIST.getType());
        jsonArray2.add(PositionUtil.H("5S.Z:"), (JsonElement)string);
        SocketMessageHandleListener.send2Web((Project)a, jsonArray2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void handleAction(WebViewDataTypeEnum webViewDataTypeEnum, Project project) {
        WebViewDataTypeEnum a = project;
        WebViewDataTypeEnum a2 = webViewDataTypeEnum;
        switch (a2) {
            case LOGIN_INIT: {
                PluginWebsocketClient.sendWsMessage(CommandEnum.USER_LOGIN, (Project)a);
                return;
            }
            case LOGIN_LOGOUT: {
                UserService.logout((Project)a);
                return;
            }
            case LOGIN_LOGIN_ABORT: {
                PluginWebsocketClient.sendWsMessage(CommandEnum.USER_LOGIN_ABORT, (Project)a);
                return;
            }
            case LOGIN_LOGIN_CHECK: {
                PluginWebsocketClient.sendWsMessage(CommandEnum.USER_LOGIN_CHECK, (Project)a);
                return;
            }
            case LOGIN_CLOSE_QR_CODE: {
                AICodeSettingsState.getInstance().showSaasQrCode = false;
                return;
            }
        }
    }

    public static void clearIcon(Project a) {
        ApplicationManager.getApplication().invokeLater(() -> {
            Project project2 = a;
            if (project2.isDisposed()) {
                return;
            }
            Project a = EditorUtils.getSelectedEditor(project2);
            if (a == null) {
                return;
            }
            if ((a = a.getMarkupModel()) == null) {
                return;
            }
            if (a.getAllHighlighters() != null) {
                int n;
                RangeHighlighter[] rangeHighlighterArray = a.getAllHighlighters();
                int n2 = rangeHighlighterArray.length;
                int n3 = n = 0;
                while (n3 < n2) {
                    RangeHighlighter rangeHighlighter = rangeHighlighterArray[n];
                    a.removeHighlighter(rangeHighlighter);
                    n3 = ++n;
                }
            }
        });
    }

    private static /* synthetic */ void VC(MessageDto a) {
        UserService.od((ComboBox)a.getOtherObject());
    }

    /*
     * WARNING - void declaration
     */
    public static void sendWriterConfig(Project project, JsonObject jsonObject) {
        void a;
        Project project2 = project;
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty(GenericUtils.H("+.!<"), WebViewDataTypeEnum.CHAT_GET_USER_INFO.getType());
        Project a2 = new JsonObject();
        a2.add(PositionUtil.H("h\u001dZ7W0y%[#R,"), a.get(GenericUtils.H(";6%8")).getAsJsonObject().get(PositionUtil.H("h\u001dZ7W0y%[#R,")));
        jsonObject2.add(GenericUtils.H(".>;$<"), (JsonElement)a2);
        SocketMessageHandleListener.send2Web(project2, jsonObject2);
    }

    public static JsonObject getUserModelList(JsonObject jsonObject, MessageDto messageDto) {
        boolean bl;
        boolean bl2;
        CodeModel codeModel2;
        Object object;
        List<CodeModel> list;
        AICodeSettingsState aICodeSettingsState;
        Object a;
        block11: {
            a = messageDto;
            JsonObject a2 = jsonObject;
            aICodeSettingsState = AICodeSettingsState.getInstance();
            if (Objects.isNull(a2.get(GenericUtils.H("!,6+")))) {
                aICodeSettingsState.modelCode = null;
                return null;
            }
            list = new TypeToken<List<FunctionModelInfo>>(){
                {
                    ea a;
                }
            }.getType();
            AICodeSettingsState.getInstance().modelInfoList = list = (List)new Gson().fromJson((JsonElement)a2.get(PositionUtil.H("K>\\9")).getAsJsonArray(), (Type)((Object)list));
            if (a != null && ((MessageDto)a).getOtherObject() instanceof ComboBox) {
                SwingUtilities.invokeLater(() -> UserService.VC((MessageDto)a));
            }
            a = null;
            for (FunctionModelInfo functionModelInfo : list) {
                if (!PermissionEnum.TALK_INTELLIGENT.getPermission().equalsIgnoreCase(functionModelInfo.getPermissionCode())) continue;
                object = a = functionModelInfo;
                break block11;
            }
            object = a;
        }
        if (object == null) {
            return null;
        }
        if (((FunctionModelInfo)a).getCodeModelList() == null) {
            return null;
        }
        list = ((FunctionModelInfo)a).getCodeModelList();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        boolean bl22 = false;
        for (CodeModel codeModel2 : list) {
            if (!StringUtils.equals((CharSequence)codeModel2.getModelCode(), (CharSequence)aICodeSettingsState.modelCode) || codeModel2.isTokenExhausted()) continue;
            bl2 = true;
            codeModel2.setChecked(true);
        }
        if (!bl2) {
            for (CodeModel codeModel2 : list) {
                boolean bl3;
                if (codeModel2.isTokenExhausted()) continue;
                bl = bl3 = true;
                CodeModel codeModel3 = codeModel2;
                codeModel3.setChecked(true);
                aICodeSettingsState.modelCode = codeModel3.getModelCode();
                break;
            }
        } else {
            bl = bl2;
        }
        if (!bl) {
            ((CodeModel)list.get(0)).setChecked(true);
            aICodeSettingsState.modelCode = ((CodeModel)list.get(0)).getModelCode();
        }
        a = new JsonObject();
        a.addProperty(GenericUtils.H("142/"), WebViewDataTypeEnum.CHAT_GET_MODEL_LIST.getType());
        codeModel2 = new Gson().toJsonTree(list != null ? list : new ArrayList());
        aICodeSettingsState = new JsonObject();
        Object object2 = a;
        aICodeSettingsState.add(PositionUtil.H("-\u007f\u0004P)c6[,"), (JsonElement)codeModel2);
        object2.add(GenericUtils.H("!$!7/"), (JsonElement)aICodeSettingsState);
        return object2;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private static void od(ComboBox var0) {
        block11: {
            block10: {
                var2_3 = var0;
                if (var2_3 != null) break block10;
                return;
            }
            if (var2_3.isValid() && var2_3.isEnabled() && var2_3.isVisible()) break block11;
            return;
        }
        try {
            v0 = var2_3;
            v0.removeAllItems();
            var1_4 /* !! */  = v0.getItemListeners();
            a = var1_4 /* !! */ .length;
            v1 = var3_6 = 0;
            while (v1 < a) {
                var4_8 = var1_4 /* !! */ [var3_6];
                var2_3.removeItemListener(var4_8);
                v1 = ++var3_6;
            }
            if (AICodeSettingsState.getInstance().modelInfoList == null) {
                v2 = var2_3;
                v2.setVisible(false);
                UserService.repaintModelComboBox(v2);
                return;
            }
            var1_4 /* !! */  = null;
            for (FunctionModelInfo var3_7 : AICodeSettingsState.getInstance().modelInfoList) {
                if (!PermissionEnum.INLINE_CHAT.getPermission().equalsIgnoreCase(var3_7.getPermissionCode())) continue;
                v3 /* !! */  = var1_4 /* !! */  = var3_7;
                ** GOTO lbl39
            }
        }
        catch (Throwable var1_5) {
            return;
        }
        v3 /* !! */  = var1_4 /* !! */ ;
lbl39:
        // 2 sources

        if (v3 /* !! */  == null || CollectionUtils.isEmpty(var1_4 /* !! */ .getCodeModelList())) {
            v4 = var2_3;
            v4.setVisible(false);
            UserService.repaintModelComboBox(v4);
            return;
        }
        a = var1_4 /* !! */ .getCodeModelList();
        if (a.size() == 1) {
            AICodeSettingsState.getInstance().inlineChatModelCode = ((CodeModel)a.get(0)).getModelCode();
            v5 = var2_3;
            v5.setVisible(false);
            UserService.repaintModelComboBox(v5);
            return;
        }
        v6 = var2_3;
        UserService.setItem(v6, a);
        v6.addItemListener((ItemListener)LambdaMetafactory.metafactory(null, null, null, (Ljava/awt/event/ItemEvent;)V, ef(java.awt.event.ItemEvent ), (Ljava/awt/event/ItemEvent;)V)());
        v7 = var2_3;
        v7.setVisible(true);
        UserService.repaintModelComboBox(v7);
    }
}
