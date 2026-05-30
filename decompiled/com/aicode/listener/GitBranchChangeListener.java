package com.aicode.listener;

import com.aicode.PluginStartupActivity;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.WebRequestDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.enums.PermissionEnum;
import com.aicode.agent.service.ChatService;
import com.aicode.agent.service.CommonService;
import com.aicode.diff.FileService;
import com.aicode.diff.GenericUtils;
import com.aicode.enums.GitRepoStatusEnum;
import com.aicode.enums.WebViewDataTypeEnum;
import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.aicode.message.BasicActionsBundle;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.util.AICodeStringUtil;
import com.aicode.util.HandleCacheUtil;
import com.aicode.util.IndentLineUtil;
import com.aicode.util.PropertyUtils;
import com.aicode.view.WebViewWindowPanel;
import com.google.gson.JsonObject;
import com.intellij.ide.BrowserUtil;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationAction;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.util.Key;
import com.intellij.util.messages.MessageBusConnection;
import git4idea.GitRemoteBranch;
import git4idea.repo.GitRemote;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: vc */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/listener/GitBranchChangeListener.class */
public class GitBranchChangeListener {

    /* renamed from: float, reason: not valid java name */
    private final Project f513float;

    /* renamed from: byte, reason: not valid java name */
    private MessageBusConnection f514byte;
    public static final Map<String, String> CURRENT_REPO = new ConcurrentHashMap();

    /* renamed from: enum, reason: not valid java name */
    private static final Map<String, String> f515enum = new ConcurrentHashMap();
    public static final Key GIT_STATUS = Key.create(PropertyUtils.H("\u0011H\u0013O\u001fO\u0006D\u0005T"));
    public static final Key GIT_CODE_KNOWLEDGE_REPO_STATUS = Key.create(InlineChatStatusServiceKt.H("\u0007,?\u0011\u0003*\r)\u0019(\u0001%\u001a$!\u0005\u0006!\u0004,\u0019)\u00027\u00157\f<\u000f,"));
    public static final Key<Boolean> NOTICE_CODE_KNOWLEDGE_REPO_STATUS = Key.create(PropertyUtils.H("U\u000eB\u0003^$i\tR\u0007Q\u0013P\u000b]\u0010\\+}\fY\u000eT\u0013Q\bO\u001fO\u0006D\u0005T"));

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m248enum(int a) {
        throw new IllegalArgumentException(String.format(InlineChatStatusServiceKt.H("��\u0016<\u000b1\u001c#\u001cf\u0005\"\u001az?HL\u001b\u00044\b0Y\u001a.(\u001e$\t2\u00069Ng@\u0018i`\n/Lc\u0010aO>H\t42\u0010{\u00103\rm\n#C#\u001d6\u0013"), PropertyUtils.H("`>t-u3s"), InlineChatStatusServiceKt.H("\u00073\u0014E.3\u001c&\b#L'\u00073\u0011\u000e %\u0017f+/\u0017\r\u0018,\u0006\u0007)\u0002\f:\u0010;\u001c\u0001\u00015\u0017(\u0006?\r"), PropertyUtils.H("pr)y$9")));
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    public static void handleGitRepoStatus(String a, JsonObject a2, Project a3) {
        JsonObject jsonObject;
        try {
            int asInt = a2.get(InlineChatStatusServiceKt.H(")\t.\u001e")).getAsJsonObject().get(PropertyUtils.H("?o&d%t")).getAsInt();
            MessageDto messageDto = (MessageDto) PluginWebsocketClient.AGENT_REQUEST.get(a);
            String str = "";
            String str2 = "";
            if (messageDto != null && (jsonObject = (JsonObject) messageDto.getData()) != null) {
                if (jsonObject.has(InlineChatStatusServiceKt.H("$\u0011,\u00069\u0017"))) {
                    str2 = jsonObject.get(PropertyUtils.H(".i&~3o")).getAsString();
                }
                if (jsonObject.has(InlineChatStatusServiceKt.H("\u001a#\u0013\"=(\u0013"))) {
                    str = jsonObject.get(PropertyUtils.H("b)k(E\"k")).getAsString();
                }
            }
            va(a, a3, asInt, str, str2);
            PluginWebsocketClient.AGENT_REQUEST.remove(a);
        } catch (Throwable th) {
            PluginWebsocketClient.AGENT_REQUEST.remove(a);
            throw th;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static void va(String a, Project a2, int a3, String a4, String a5) {
        if (AICodeSettingsState.getInstance().ignoreGitAuth) {
            if (BasicActionsBundle.message(InlineChatStatusServiceKt.H("\u0002&\t\"\f\u0001o1\b.\u00195\u0017c\u001e#\u0011>\u00015\u0011"), new Object[0]).equals(AICodeSettingsState.getInstance().ignoreVersion)) {
                return;
            } else {
                AICodeSettingsState.getInstance().ignoreGitAuth = false;
            }
        }
        WebRequestDto webRequestDto = (WebRequestDto) PluginWebsocketClient.WEB_REQUEST.get(a);
        if (null != webRequestDto && WebViewDataTypeEnum.GIT_GET_STATUS.getType().equals(webRequestDto.getType())) {
            PluginWebsocketClient.WEB_REQUEST.remove(a);
            return;
        }
        GitRepoStatusEnum gitRepoStatusEnum = GitRepoStatusEnum.getGitRepoStatusEnum(a3);
        if (gitRepoStatusEnum != null) {
            String message = gitRepoStatusEnum.getMessage();
            if (GitRepoStatusEnum.UNAUTHORIZED.getCode() == a3) {
                message = String.format(message, StringUtils.isNotBlank(a4) ? a4.substring(a4.lastIndexOf(PropertyUtils.H("(")) + 1, a4.lastIndexOf(InlineChatStatusServiceKt.H("c\u000f3\u000b"))) : "", a5);
            }
            Notification content = NotificationGroupManager.getInstance().getNotificationGroup(PropertyUtils.H("}8e9e\">\"t3y3b")).createNotification(BasicActionsBundle.message(InlineChatStatusServiceKt.H("\u000b4\f>\u001en\u0004\u0002-/\u0001,B\u0003\u0007&\u001e\"\u001a%\"5\r4\u0010\u001b\u000b\"\u001d6M9\r\"\u000b"), new Object[0]), MessageType.INFO).setTitle(BasicActionsBundle.message(PropertyUtils.H("s>t4fd|\bU%y&:\t\u007f,f(b/Z?u>h\u0011s(e<53u(s"), new Object[0])).setContent(message);
            if (gitRepoStatusEnum.isNeedSkipWeb() && StringUtils.isNotBlank(AICodeSettingsState.getInstance().codeKnowledgeWebUrl)) {
                content.addAction(new R(BasicActionsBundle.message(InlineChatStatusServiceKt.H("/)\u0006&\b#M$\u0004\"\u001f\b$%\u0003>P1\u0018#\t!\u0006 \r4\u000b"), new Object[0])));
            }
            dC(a, gitRepoStatusEnum, content, a2);
            content.addAction(new H(PropertyUtils.H("徭畢"), a2));
            content.notify(a2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: vc */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/listener/GitBranchChangeListener$b.class */
    public class b extends NotificationAction {

        /* renamed from: float, reason: not valid java name */
        public final /* synthetic */ String f517float;

        /* renamed from: byte, reason: not valid java name */
        public final /* synthetic */ String f518byte;

        /* renamed from: enum, reason: not valid java name */
        public final /* synthetic */ Project f519enum;

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m252enum(int a) {
            String H = AICodeStringUtil.H("}EJSKHCR\u001aW\t\u001f/DoEHy\u007fmV\u0011YCTLFETNy \u0007\u000eZ\u0005\u0006BI\u0004\bU**R\nVEOC\rHIY\rD_\u0011\u0002\u0012FM");
            Object[] objArr = new Object[3];
            switch (a) {
                case 0:
                default:
                    objArr[0] = FileService.H("$");
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    objArr[0] = AICodeStringUtil.H("CIRDKOYP\u0018\u000eEO");
                    break;
            }
            objArr[1] = FileService.H("4\u001c\fl09!0)7o5\"\u0001\u0014<%50p\n?0\u00164\u001c\u0001;\"\u00018$93#\u0013$'2&?puwr");
            objArr[2] = AICodeStringUtil.H("Q_CDIH}HT\\^\u001e\nOE");
            throw new IllegalArgumentException(String.format(H, objArr));
        }

        public void actionPerformed(@NotNull AnActionEvent e, @NotNull Notification a) {
            if (e == null) {
                m252enum(0);
            }
            if (a == null) {
                m252enum(1);
            }
            a.hideBalloon();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(AICodeStringUtil.H("T_A\u00032XM"), this.f517float);
            jsonObject.addProperty(FileService.H("!#ti0)"), this.f518byte);
            PluginWebsocketClient.sendWsMessage(CommandEnum.GIT_REPO_AUTHORIZE, jsonObject, this.f519enum);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public b(String a, String str, String str2, Project project) {
            super(a);
            this.f517float = str;
            this.f518byte = str2;
            this.f519enum = project;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: vc */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/listener/GitBranchChangeListener$R.class */
    public class R extends NotificationAction {
        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m251enum(int a) {
            String H = GenericUtils.H("\u0005>\u007fe\u001c\u001c1#b,\u001b\u000ed\f\u00190,\u001e 12v\u0005\u001c,7::,5#yt~\tU\u007f8#mg9}~+p>.782t\u000b\u0018s9=p->7?");
            Object[] objArr = new Object[3];
            switch (a) {
                case 0:
                default:
                    objArr[0] = FileService.H("\u0014");
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    objArr[0] = GenericUtils.H("|u\u0010\u000552;17\"4=");
                    break;
            }
            objArr[1] = FileService.H("4\u001c\fl09!0)7o5\"\u0001\u0014<%50p\n?0\u00164\u001c\u0001;\"\u00018$93#\u0013$'2&?puG@");
            objArr[2] = GenericUtils.H(":'8{u\n<6)>?1&>7");
            throw new IllegalArgumentException(String.format(H, objArr));
        }

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        public void actionPerformed(@NotNull AnActionEvent e, @NotNull Notification a) {
            String str;
            if (e == null) {
                m251enum(0);
            }
            if (a == null) {
                m251enum(1);
            }
            a.hideBalloon();
            String str2 = "";
            if (StringUtils.isNotBlank(AICodeSettingsState.getInstance().codeKnowledgeWebUrl)) {
                if (!AICodeSettingsState.getInstance().codeKnowledgeWebUrl.endsWith(GenericUtils.H("/<8><g"))) {
                    if (!AICodeSettingsState.getInstance().codeKnowledgeWebUrl.endsWith(FileService.H("N"))) {
                        str = AICodeSettingsState.getInstance().codeKnowledgeWebUrl + "?tab=my&token=" + PluginStartupActivity.getApiKey();
                    } else {
                        str2 = AICodeSettingsState.getInstance().codeKnowledgeWebUrl;
                    }
                } else {
                    str = AICodeSettingsState.getInstance().codeKnowledgeWebUrl + PluginStartupActivity.getApiKey();
                }
                BrowserUtil.browse(str);
            }
            str = str2;
            BrowserUtil.browse(str);
        }

        public R(String a) {
            super(a);
        }
    }

    public GitBranchChangeListener(@NotNull Project a) {
        if (a == null) {
            m248enum(0);
        }
        this.f513float = a;
        jC();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: vc */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/listener/GitBranchChangeListener$H.class */
    public class H extends NotificationAction {

        /* renamed from: enum, reason: not valid java name */
        public final /* synthetic */ Project f516enum;

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m250enum(int a) {
            String H = IndentLineUtil.H("/C\u0018U\u0019N\u0011THQ[\u0019}B=C\u001a\u007f-k\u0004\u0017\u000bE\u0006J\u0014C\u0006H+&U\b\b\u0003TD\u001b\u0002ZSx,��\f\u0004C\u001dE_N\u001b__B\r\u0017P\u0014>a");
            Object[] objArr = new Object[3];
            switch (a) {
                case 0:
                default:
                    objArr[0] = HandleCacheUtil.H("\u0017");
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    objArr[0] = IndentLineUtil.H("\u0011O��B\u0019I\u000bVJ\b=c");
                    break;
            }
            objArr[1] = HandleCacheUtil.H("-H\u00158)m8d0cva;U\rh<a)$\u0013k)B-H\u0018o;U!p g:G=s+r&$l\t@");
            objArr[2] = IndentLineUtil.H("W\rE\u0016O\u001a{\u001aR\u000eXL\f7i");
            throw new IllegalArgumentException(String.format(H, objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public H(String a, Project project) {
            super(a);
            this.f516enum = project;
        }

        public void actionPerformed(@NotNull AnActionEvent e, @NotNull Notification a) {
            if (e == null) {
                m250enum(0);
            }
            if (a == null) {
                m250enum(1);
            }
            a.hideBalloon();
            CommonService.messageBus(this.f516enum, IndentLineUtil.H("妠靿彳创仪硲庿絋弣皖兂撲佼ｸ诜刲徠掺仁讀缏頧靯"), MessageType.INFO);
            AICodeSettingsState.getInstance().ignoreGitAuth = true;
            AICodeSettingsState.getInstance().ignoreVersion = BasicActionsBundle.message(HandleCacheUtil.H(">@\u0015c7sga\"u8b:.)r:2wB\u001c"), new Object[0]);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void handleGitResponse(String a, JsonObject a2, Project a3, CommandEnum a4) {
        if (a4 == CommandEnum.GIT_SAVE_TOKEN) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(PropertyUtils.H("3i b"), WebViewDataTypeEnum.COMMON_SHOW_MESSAGE_IN_WEB.getType());
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty(InlineChatStatusServiceKt.H("9\u0011*\u001a"), PropertyUtils.H("c9x$u#t"));
            jsonObject2.addProperty(InlineChatStatusServiceKt.H("\u0005#\u0010>\t=\u001a"), PropertyUtils.H("俆嬟戀勏&"));
            jsonObject2.addProperty(InlineChatStatusServiceKt.H(")\u001d4\u00029\u00015\u0011"), 2000);
            jsonObject2.addProperty(PropertyUtils.H("r/\u007f;X+\u007f#b"), true);
            jsonObject.add(InlineChatStatusServiceKt.H("\u0015,\u0004/\u001a"), jsonObject2);
            SocketMessageHandleListener.send2Web(a3, jsonObject);
            return;
        }
        getCurrentGitInfo(a3);
        JsonObject asJsonObject = a2.get(PropertyUtils.H("#q$f")).getAsJsonObject();
        JsonObject jsonObject3 = new JsonObject();
        jsonObject3.addProperty(InlineChatStatusServiceKt.H("9\u0011*\u001a"), PropertyUtils.H("\u0011H\u0013*\u001fO\u0006D\u0005T"));
        JsonObject a5 = new JsonObject();
        a5.addProperty(InlineChatStatusServiceKt.H("$\u0011,\u00069\u0017"), CURRENT_REPO.get(PropertyUtils.H(".i&~3o")));
        a5.addProperty(InlineChatStatusServiceKt.H("\u001a#\u0013\"=(\u0013"), CURRENT_REPO.get(PropertyUtils.H("b)k(E\"k")));
        a5.addProperty(InlineChatStatusServiceKt.H(".\u0007>\u001a"), PropertyUtils.H("\"`7"));
        a5.addProperty(InlineChatStatusServiceKt.H("\u000b)\u000e \t4\u001b"), a4.getType());
        a5.addProperty(PropertyUtils.H("5u<t\tq=b"), getRepositoryNameFromUrl(CURRENT_REPO.get(InlineChatStatusServiceKt.H("\u001a#\u0013\"=(\u0013"))));
        if (CommandEnum.GIT_CODE_KNOWLEDGE_REPO_STATUS != a4 || ChatService.isCurrentBranchRemote(a3)) {
            if (null != asJsonObject) {
                a5.addProperty(PropertyUtils.H("?o&d%t"), Integer.valueOf(asJsonObject.get(InlineChatStatusServiceKt.H("5\u0017,\u001c/\f")).getAsInt()));
                if (asJsonObject.get(PropertyUtils.H(">~7\u007f\u0019c")) != null && asJsonObject.get(InlineChatStatusServiceKt.H("4\u0006=\u0007\u0013\u001b")).isJsonPrimitive()) {
                    a5.addProperty(PropertyUtils.H(">~7\u007f\u0019c"), asJsonObject.get(InlineChatStatusServiceKt.H("4\u0006=\u0007\u0013\u001b")).getAsString());
                }
            }
            jsonObject3.add(PropertyUtils.H("m&|%b"), a5);
            dc(a3, jsonObject3);
            PluginWebsocketClient.AGENT_REQUEST.remove(a);
            return;
        }
        sendNoAuthStatusToWeb(a3);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static void dC(String a, GitRepoStatusEnum a2, Notification a3, Project a4) {
        MessageDto messageDto;
        if (!a2.isNeedAuthorize() || (messageDto = (MessageDto) PluginWebsocketClient.AGENT_REQUEST.get(a)) == null) {
            return;
        }
        JsonObject jsonObject = (JsonObject) messageDto.getData();
        String a5 = jsonObject.get(InlineChatStatusServiceKt.H("\u001a#\u0013\"=(\u0013")).getAsString();
        String asString = jsonObject.get(PropertyUtils.H(".i&~3o")).getAsString();
        PluginWebsocketClient.AGENT_REQUEST.remove(a);
        if (!StringUtils.isBlank(a5) && !StringUtils.isBlank(asString)) {
            a3.addAction(new b(BasicActionsBundle.message(InlineChatStatusServiceKt.H("!\f\b!$��g\u0007(\f8\u0006(\f\u0003$o\u0005.\n4\u0016?\u0001<\u00029\u00015\u0011"), new Object[0]), a5, asString, a4));
        }
    }

    public void dispose() {
        if (this.f514byte == null) {
            return;
        }
        this.f514byte.disconnect();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void handleGitException(String a, String a2, Project a3, CommandEnum a4, String a5) {
        if (a4 == CommandEnum.GIT_SAVE_TOKEN) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(InlineChatStatusServiceKt.H("9\u0011*\u001a"), WebViewDataTypeEnum.COMMON_SHOW_MESSAGE_IN_WEB.getType());
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty(PropertyUtils.H("3i b"), InlineChatStatusServiceKt.H("\u0006?\u001a5\r"));
            jsonObject2.addProperty(PropertyUtils.H("})h4q7b"), a5);
            jsonObject2.addProperty(InlineChatStatusServiceKt.H(")\u001d4\u00029\u00015\u0011"), 2000);
            jsonObject2.addProperty(PropertyUtils.H("r/\u007f;X+\u007f#b"), true);
            jsonObject.add(InlineChatStatusServiceKt.H("\u0015,\u0004/\u001a"), jsonObject2);
            SocketMessageHandleListener.send2Web(a3, jsonObject);
            return;
        }
        JsonObject jsonObject3 = new JsonObject();
        jsonObject3.addProperty(PropertyUtils.H("3i b"), InlineChatStatusServiceKt.H("\u001b0\u0019R\u00157\f<\u000f,"));
        JsonObject jsonObject4 = new JsonObject();
        jsonObject4.addProperty(PropertyUtils.H(".i&~3o"), CURRENT_REPO.get(InlineChatStatusServiceKt.H("$\u0011,\u00069\u0017")));
        jsonObject4.addProperty(PropertyUtils.H("b)k(E\"k"), CURRENT_REPO.get(InlineChatStatusServiceKt.H("\u001a#\u0013\"=(\u0013")));
        jsonObject4.addProperty(PropertyUtils.H("$\u007f4b"), a2);
        jsonObject4.addProperty(InlineChatStatusServiceKt.H("\u000b)\u000e \t4\u001b"), a4.getType());
        jsonObject4.addProperty(PropertyUtils.H("5u<t\tq=b"), getRepositoryNameFromUrl(CURRENT_REPO.get(InlineChatStatusServiceKt.H("\u001a#\u0013\"=(\u0013"))));
        jsonObject3.add(PropertyUtils.H("m&|%b"), jsonObject4);
        dc(a3, jsonObject3);
        PluginWebsocketClient.AGENT_REQUEST.remove(a);
    }

    private static void dc(Project a, JsonObject a2) {
        ApplicationManager.getApplication().invokeLater(() -> {
            try {
                WebViewWindowPanel webViewWindowPanel = (WebViewWindowPanel) a.getUserData(WebViewWindowPanel.WEB_VIEW_PANEL);
                if (Objects.isNull(webViewWindowPanel) || !webViewWindowPanel.isLoaded.get()) {
                    a.putUserData(GIT_STATUS, a2);
                } else {
                    SocketMessageHandleListener.send2Web(a, a2);
                }
            } catch (Exception e) {
                a.putUserData(GIT_STATUS, a2);
            }
        });
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void codeKnowledgeNotification(Project a) {
        if (!AICodeSettingsState.getInstance().permissions.contains(PermissionEnum.CODE_KNOWLEDGE_BASE.getPermission())) {
            return;
        }
        if (Boolean.TRUE.equals(a.getUserData(NOTICE_CODE_KNOWLEDGE_REPO_STATUS))) {
            return;
        }
        a.putUserData(NOTICE_CODE_KNOWLEDGE_REPO_STATUS, true);
        GitRepositoryManager gitRepositoryManager = GitRepositoryManager.getInstance(a);
        if (gitRepositoryManager.getRepositories() == null || gitRepositoryManager.getRepositories().size() == 0) {
            return;
        }
        Iterator it = gitRepositoryManager.getRepositories().iterator();
        while (it.hasNext()) {
            GitRepository gitRepository = (GitRepository) it.next();
            it = it;
            Zb(a, gitRepository);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static String getRepositoryNameFromUrl(String str) {
        String str2 = str;
        if (str2 != null && !str2.isEmpty()) {
            if (str2.endsWith(InlineChatStatusServiceKt.H("c\u000f3\u000b"))) {
                str2 = str2.substring(0, str2.length() - 4);
            }
            int lastIndexOf = str2.lastIndexOf(47);
            if (lastIndexOf == -1) {
                return null;
            }
            return str2.substring(lastIndexOf + 1);
        }
        return null;
    }

    public static void sendNoAuthStatusToWeb(Project a) {
        getCurrentGitInfo(a);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(InlineChatStatusServiceKt.H("9\u0011*\u001a"), PropertyUtils.H("\u0011H\u0013*\u001fO\u0006D\u0005T"));
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty(InlineChatStatusServiceKt.H("$\u0011,\u00069\u0017"), CURRENT_REPO.get(PropertyUtils.H("j9b&|\u000ei&~3o")));
        jsonObject2.addProperty(InlineChatStatusServiceKt.H("\u001a#\u0013\"=(\u0013"), CURRENT_REPO.get(PropertyUtils.H("=i5`+B)k(E\"k")));
        jsonObject2.addProperty(InlineChatStatusServiceKt.H(".\u0007>\u001a"), PropertyUtils.H("\"`7"));
        jsonObject2.addProperty(InlineChatStatusServiceKt.H("?\r6\f\u0003\t7\u001a"), getRepositoryNameFromUrl(CURRENT_REPO.get(PropertyUtils.H("=i5`+B)k(E\"k"))));
        jsonObject2.addProperty(InlineChatStatusServiceKt.H("5\u0017,\u001c/\f"), -5);
        jsonObject.add(PropertyUtils.H("m&|%b"), jsonObject2);
        dc(a, jsonObject);
    }

    private void jC() {
        this.f514byte = this.f513float.getMessageBus().connect();
        this.f514byte.subscribe(GitRepository.GIT_REPO_CHANGE, a -> {
            if (!AICodeSettingsState.getInstance().permissions.contains(PermissionEnum.CODE_KNOWLEDGE_BASE.getPermission())) {
                return;
            }
            Zb(this.f513float, a);
        });
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    public static void getCurrentGitInfo(Project a) {
        String str;
        try {
            for (GitRepository gitRepository : GitRepositoryManager.getInstance(a).getRepositories()) {
                String name = gitRepository.getCurrentBranch() != null ? gitRepository.getCurrentBranch().getName() : null;
                String str2 = null;
                Iterator it = gitRepository.getRemotes().iterator();
                if (it.hasNext()) {
                    str2 = ((GitRemote) it.next()).getFirstUrl();
                    str = name;
                } else {
                    str = name;
                }
                if (StringUtils.isNotBlank(str) && StringUtils.isNotBlank(str2)) {
                    CURRENT_REPO.put(InlineChatStatusServiceKt.H("\u001a#\u0013\"=(\u0013"), str2);
                    CURRENT_REPO.put(PropertyUtils.H(".i&~3o"), name);
                }
                CURRENT_REPO.put(InlineChatStatusServiceKt.H("7\u0011?\u0018!:#\u0013\"=(\u0013"), str2);
                CURRENT_REPO.put(PropertyUtils.H("j9b&|\u000ei&~3o"), name);
            }
        } catch (Exception unused) {
        }
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    private static void Zb(Project a, GitRepository a2) {
        GitRepository gitRepository;
        boolean z;
        String name = a2.getCurrentBranch() != null ? a2.getCurrentBranch().getName() : null;
        Collection remotes = a2.getRemotes();
        if (CollectionUtils.isEmpty(remotes)) {
            return;
        }
        String str = null;
        Iterator it = remotes.iterator();
        if (!it.hasNext()) {
            gitRepository = a2;
        } else {
            str = ((GitRemote) it.next()).getFirstUrl();
            gitRepository = a2;
        }
        for (GitRemoteBranch gitRemoteBranch : gitRepository.getBranches().getRemoteBranches()) {
            if (StringUtils.equals("refs/remotes/" + name, gitRemoteBranch.getFullName()) || StringUtils.equals("refs/remotes/origin/" + name, gitRemoteBranch.getFullName())) {
                z = true;
                break;
            }
        }
        z = false;
        if (!z) {
            sendNoAuthStatusToWeb(a);
            return;
        }
        if (!StringUtils.isBlank(str) && !StringUtils.isBlank(name)) {
            if (!f515enum.containsKey(InlineChatStatusServiceKt.H("\u001a#\u0013\"=(\u0013")) || !StringUtils.equals(str, f515enum.get(PropertyUtils.H("b)k(E\"k"))) || !f515enum.containsKey(InlineChatStatusServiceKt.H("$\u0011,\u00069\u0017")) || !StringUtils.equals(name, f515enum.get(PropertyUtils.H(".i&~3o")))) {
                f515enum.put(InlineChatStatusServiceKt.H("\u001a#\u0013\"=(\u0013"), str);
                f515enum.put(PropertyUtils.H(".i&~3o"), name);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(InlineChatStatusServiceKt.H("$\u0011,\u00069\u0017"), name);
                jsonObject.addProperty(PropertyUtils.H("b)k(E\"k"), str);
                PluginWebsocketClient.sendWsMessage(CommandEnum.GIT_CODE_KNOWLEDGE_REPO_STATUS, jsonObject, a);
            }
        }
    }
}
