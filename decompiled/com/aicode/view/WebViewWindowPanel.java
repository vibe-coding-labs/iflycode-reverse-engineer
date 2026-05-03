/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.intellij.ide.BrowserUtil
 *  com.intellij.openapi.Disposable
 *  com.intellij.openapi.application.ApplicationInfo
 *  com.intellij.openapi.project.Project
 *  com.intellij.openapi.util.Disposer
 *  com.intellij.openapi.util.Key
 *  com.intellij.ui.jcef.JBCefApp
 *  com.intellij.ui.jcef.JBCefBrowser
 *  com.intellij.ui.jcef.JBCefJSQuery
 *  org.cef.CefApp
 *  org.cef.browser.CefBrowser
 *  org.cef.browser.CefFrame
 *  org.cef.callback.CefSchemeHandlerFactory
 *  org.cef.handler.CefLifeSpanHandler
 *  org.cef.handler.CefLifeSpanHandlerAdapter
 *  org.cef.handler.CefLoadHandler
 *  org.cef.handler.CefLoadHandlerAdapter
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.aicode.view;

import com.aicode.PluginStartupActivity;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.service.ChatService;
import com.aicode.agent.service.CodeCheckService;
import com.aicode.agent.service.CodeSearchService;
import com.aicode.agent.service.CommonService;
import com.aicode.agent.service.GitReviewService;
import com.aicode.agent.service.RestartableAgentProcessService;
import com.aicode.agent.service.SqlService;
import com.aicode.agent.service.UserService;
import com.aicode.content.util.file.FileExtensionLanguageDetails;
import com.aicode.diff.FileService;
import com.aicode.enums.PluginSceneEnum;
import com.aicode.enums.WebViewDataTypeEnum;
import com.aicode.exception.RequestCancelException;
import com.aicode.icons.Icons;
import com.aicode.inline.ide.IdeAction;
import com.aicode.listener.GitBranchChangeListener;
import com.aicode.listener.ThemeChangeListener;
import com.aicode.message.BasicActionsBundle;
import com.aicode.test.BatchUnitTestService;
import com.aicode.test.UnitTestService;
import com.aicode.ui.FontKt;
import com.aicode.util.LogUtil;
import com.aicode.util.Maps;
import com.aicode.view.CustomSchemeHandlerFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Key;
import com.intellij.ui.jcef.JBCefApp;
import com.intellij.ui.jcef.JBCefBrowser;
import com.intellij.ui.jcef.JBCefJSQuery;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.cef.CefApp;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.callback.CefSchemeHandlerFactory;
import org.cef.handler.CefLifeSpanHandler;
import org.cef.handler.CefLifeSpanHandlerAdapter;
import org.cef.handler.CefLoadHandler;
import org.cef.handler.CefLoadHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class WebViewWindowPanel
extends JPanel {
    private static final Logger final = LoggerFactory.getLogger(WebViewWindowPanel.class);
    public static final Key UNIT_TEST_METHOD_DATA;
    private JBCefJSQuery try;
    private final Project float;
    public static final Key CODE_DEBUG_AGENT_DATA;
    private JBCefBrowser byte;
    public static final Key UNIT_TEST_MESSAGE_DATA;
    public static final Key CODE_DEBUG_MESSAGE_DATA;
    public static final Key OPEN_PAGE_DATA;
    public final AtomicBoolean isLoaded;
    private static String enum;
    public static final Key CODE_MESSAGE_DATA;
    public static final Key WEB_VIEW_PANEL;

    private JBCefBrowser true() {
        WebViewWindowPanel webViewWindowPanel = this;
        if (ApplicationInfo.getInstance().getBuild().getBaselineVersion() > 211) {
            try {
                Class<?> clazz = Class.forName(RequestCancelException.H("0I,(\bV+Z4Y;TwS(/\fS2Yv~\u0011~?C\u0000Q+S0Q!"));
                Object a = clazz.getDeclaredMethod(Maps.H("0\u0016=\f%\u0010\u000b\u0006&\u0018,\u0001*"), new Class[0]).invoke(null, new Object[0]);
                Class[] classArray = new Class[1];
                classArray[0] = Boolean.TYPE;
                Object[] objectArray = new Object[1];
                objectArray[0] = false;
                a.getClass().getDeclaredMethod(RequestCancelException.H("*C5N\u0000V\u0004\\*Q6S\b@,G!V*Z4"), classArray).invoke(a, objectArray);
                a = a.getClass().getDeclaredMethod(Maps.H("0\u0016=\f%\u0010\u000b\u0001 \u0003;\u0001*"), new Class[0]).invoke(a, new Object[0]);
                final.info("[" + BasicActionsBundle.message(RequestCancelException.H(" h\u0005_3ZvD?H=L,\r0M7X6"), new Object[0]) + "] loadBrowser by reflection success.");
                return (JBCefBrowser)a;
            }
            catch (Exception exception) {
                final.info("[" + BasicActionsBundle.message(Maps.H("+8\u000e\u000f8\n}\u00144\u00186\u001c'];\u001d<\b="), new Object[0]) + "] loadBrowser error," + exception.getMessage());
            }
        }
        JBCefBrowser jBCefBrowser = new JBCefBrowser();
        jBCefBrowser.getCefBrowser().createImmediately();
        return jBCefBrowser;
    }

    private void final(JBCefBrowser jBCefBrowser) {
        WebViewWindowPanel a = jBCefBrowser;
        WebViewWindowPanel a2 = this;
        a.getJBCefClient().addLoadHandler((CefLoadHandler)new CefLoadHandlerAdapter(a2){
            public final /* synthetic */ WebViewWindowPanel enum;
            {
                Object a = webViewWindowPanel;
                M a2 = this;
                a2.enum = a;
            }

            /*
             * WARNING - void declaration
             */
            public void onLoadEnd(CefBrowser cefBrowser, CefFrame cefFrame, int n) {
                void a;
                M a2 = cefBrowser;
                M a3 = this;
                if (a == 200) {
                    a2.executeJavaScript("window.myObject = {sendMessage : function(data) {" + a3.enum.try.inject(FileService.H("qf\b\u000f")) + "}};", a2.getURL(), 0);
                    if (!a3.enum.isLoaded.get() && !UserService.isGoTo()) {
                        PluginWebsocketClient.sendWsMessage(CommandEnum.USER_LOGIN, a3.enum.float);
                    }
                    a3.enum.isLoaded.set(true);
                    ThemeChangeListener.initTheme();
                    if (RestartableAgentProcessService.pushAgentRefresh.get()) {
                        RestartableAgentProcessService.pushAgentRefreshToWebView();
                        RestartableAgentProcessService.pushAgentRefresh.set(false);
                    }
                    M m = a3;
                    m.enum.goto();
                    CommonService.getPluginInfo(m.enum.float);
                }
            }
        }, a.getCefBrowser());
    }

    public void addHtmlPanel() {
        WebViewWindowPanel webViewWindowPanel;
        WebViewWindowPanel webViewWindowPanel2 = this;
        try {
            webViewWindowPanel2.byte = webViewWindowPanel2.true();
        }
        catch (Throwable a) {
            final.info("[" + BasicActionsBundle.message(RequestCancelException.H("3p\u001dJ&YuM6t\u0001Y9\u0011,]'Q?"), new Object[0]) + "] create browser error," + a);
            return;
        }
        Object a = new CefLifeSpanHandlerAdapter(webViewWindowPanel2){
            public final /* synthetic */ WebViewWindowPanel enum;
            {
                Object a = webViewWindowPanel;
                D a2 = this;
                a2.enum = a;
            }

            public void onAfterCreated(CefBrowser cefBrowser) {
                D a = cefBrowser;
                D a2 = this;
                CefApp.getInstance().registerSchemeHandlerFactory(IdeAction.H("F\u0011:u"), FontKt.H("1!7*"), (CefSchemeHandlerFactory)new CustomSchemeHandlerFactory(a2.enum.float));
            }
        };
        webViewWindowPanel2.byte.getJBCefClient().addLifeSpanHandler((CefLifeSpanHandler)a, webViewWindowPanel2.byte.getCefBrowser());
        WebViewWindowPanel webViewWindowPanel3 = webViewWindowPanel2;
        webViewWindowPanel3.add((Component)webViewWindowPanel3.byte.getComponent(), Maps.H(",6\n,\b#"));
        webViewWindowPanel3.byte.loadURL(enum);
        try {
            Class[] classArray = new Class[1];
            classArray[0] = JBCefBrowser.class;
            a = Class.forName(RequestCancelException.H("\u0005_:\u00111Z'X6\\>_|@;7\u0014F'Zuw\u0018B\u0003V\u001dl\tA6O#")).getDeclaredMethod(Maps.H("\f!\u00019\u00194"), classArray);
            Object[] objectArray = new Object[1];
            objectArray[0] = webViewWindowPanel2.byte;
            webViewWindowPanel2.try = (JBCefJSQuery)((Method)a).invoke(null, objectArray);
            webViewWindowPanel = webViewWindowPanel2;
        }
        catch (Exception exception) {
            webViewWindowPanel = webViewWindowPanel2;
        }
        webViewWindowPanel.try.addHandler(string -> {
            String a = string;
            WebViewWindowPanel a2 = this;
            if (Objects.isNull(a)) {
                return null;
            }
            PluginStartupActivity.handleExecutorService.execute(() -> {
                String a = a;
                WebViewWindowPanel a2 = this;
                a2.handleRequest(a);
            });
            return null;
        });
        WebViewWindowPanel webViewWindowPanel4 = webViewWindowPanel2;
        webViewWindowPanel4.final(webViewWindowPanel4.byte);
        Disposer.register((Disposable)webViewWindowPanel4.float, (Disposable)webViewWindowPanel2.byte);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void handleRequest(String string) {
        void a;
        WebViewWindowPanel webViewWindowPanel = this;
        try {
            LogUtil.info(RequestCancelException.H("W\"F\u0011Q0G,S'"), (String)a);
            JsonObject jsonObject = JsonParser.parseString((String)a).getAsJsonObject();
            if (!jsonObject.has(Maps.H("\u00067\u0005,"))) {
                return;
            }
            Object a2 = WebViewDataTypeEnum.getByType(jsonObject.get(RequestCancelException.H("V<U'")).getAsString());
            if (Objects.isNull(a2)) {
                return;
            }
            switch (((WebViewDataTypeEnum)((Object)a2)).getModule()) {
                case CODE_SEARCH: {
                    CodeSearchService.handleAction(webViewWindowPanel, (WebViewDataTypeEnum)((Object)a2), jsonObject, (String)a, webViewWindowPanel.float);
                    return;
                }
                case UNIT_TEST: {
                    UnitTestService.handleAction((WebViewDataTypeEnum)((Object)a2), (String)a, webViewWindowPanel.float);
                    return;
                }
                case BATCH_UNIT_TEST: {
                    BatchUnitTestService.handleAction((WebViewDataTypeEnum)((Object)a2), (String)a, webViewWindowPanel.float);
                    return;
                }
                case UNIT_TESTING: {
                    UnitTestService.handleAction((WebViewDataTypeEnum)((Object)a2), (String)a, webViewWindowPanel.float);
                }
                case LOG: {
                    final.info((String)a);
                    return;
                }
                case CHAT: {
                    ChatService.handleAction((WebViewDataTypeEnum)((Object)a2), jsonObject, (String)a, webViewWindowPanel.float);
                    return;
                }
                case LOGIN: {
                    UserService.handleAction((WebViewDataTypeEnum)((Object)a2), webViewWindowPanel.float);
                    return;
                }
                case COMMON: 
                case SETTING: {
                    CommonService.handleAction((WebViewDataTypeEnum)((Object)a2), jsonObject, (String)a, webViewWindowPanel.float);
                    return;
                }
                case SQL_CHAT: {
                    SqlService.handleAction((WebViewDataTypeEnum)((Object)a2), jsonObject, webViewWindowPanel.float);
                    return;
                }
                case CODE_CHECK: {
                    CodeCheckService.handleAction(webViewWindowPanel, (WebViewDataTypeEnum)((Object)a2), jsonObject, webViewWindowPanel.float);
                    return;
                }
                case GIT_VIEW: {
                    GitReviewService.handleAction((WebViewDataTypeEnum)((Object)a2), jsonObject, webViewWindowPanel.float);
                    return;
                }
            }
            return;
        }
        catch (Exception exception) {
            final.info((String)a);
            return;
        }
    }

    private void goto() {
        WebViewWindowPanel webViewWindowPanel;
        WebViewWindowPanel webViewWindowPanel2;
        Object a2;
        WebViewWindowPanel webViewWindowPanel3 = this;
        try {
            a2 = webViewWindowPanel3.float.getUserData(GitBranchChangeListener.GIT_STATUS);
            if (a2 != null) {
                webViewWindowPanel3.sendMessage2webView(a2);
            }
            webViewWindowPanel2 = webViewWindowPanel3;
        }
        catch (Throwable a2) {
            WebViewWindowPanel webViewWindowPanel4 = webViewWindowPanel3;
            webViewWindowPanel = webViewWindowPanel4;
            webViewWindowPanel4.float.putUserData(GitBranchChangeListener.GIT_STATUS, null);
        }
        catch (Throwable throwable) {
            webViewWindowPanel3.float.putUserData(GitBranchChangeListener.GIT_STATUS, null);
            throw throwable;
        }
        webViewWindowPanel = webViewWindowPanel2;
        webViewWindowPanel2.float.putUserData(GitBranchChangeListener.GIT_STATUS, null);
        try {
            a2 = webViewWindowPanel.float.getUserData(GitBranchChangeListener.GIT_CODE_KNOWLEDGE_REPO_STATUS);
            if (a2 != null) {
                MessageDto messageDto = (MessageDto)a2;
                PluginWebsocketClient.sendWsMessage(messageDto, webViewWindowPanel3.float);
            }
            return;
        }
        catch (Throwable a2) {
            return;
        }
        finally {
            webViewWindowPanel3.float.putUserData(GitBranchChangeListener.GIT_CODE_KNOWLEDGE_REPO_STATUS, null);
        }
    }

    public WebViewWindowPanel(Project project) {
        WebViewWindowPanel a = project;
        WebViewWindowPanel a2 = this;
        WebViewWindowPanel webViewWindowPanel = a2;
        a2.isLoaded = new AtomicBoolean(false);
        a2.float = a;
        WebViewWindowPanel webViewWindowPanel2 = a2;
        a2.setLayout(new BorderLayout());
        if (!JBCefApp.isSupported()) {
            a2.notSupportCefTip();
            return;
        }
        a2.addHtmlPanel();
        a.putUserData(WEB_VIEW_PANEL, a2);
    }

    public void sendMessage2webView(Object object) {
        Object a = object;
        WebViewWindowPanel a2 = this;
        if (!a2.isLoaded.get() || a2.byte == null || a == null) {
            return;
        }
        a = new Gson().toJson(a);
        a2.byte.getCefBrowser().executeJavaScript("receiveData(" + (String)a + ");", a2.byte.getCefBrowser().getURL(), 0);
    }

    public void notSupportCefTip() {
        int a;
        JPanel jPanel;
        String[] stringArray;
        WebViewWindowPanel webViewWindowPanel;
        WebViewWindowPanel webViewWindowPanel2 = webViewWindowPanel = this;
        WebViewWindowPanel webViewWindowPanel3 = webViewWindowPanel;
        webViewWindowPanel2.setLayout(new BoxLayout(webViewWindowPanel, 1));
        webViewWindowPanel2.add(Box.createVerticalStrut(40));
        String[] stringArray2 = stringArray = new JLabel(Icons.PluginIconLogo);
        stringArray2.setAlignmentX(0.5f);
        stringArray.setForeground(Color.WHITE);
        webViewWindowPanel.add((Component)stringArray2);
        webViewWindowPanel.add(Box.createVerticalStrut(20));
        String[] stringArray3 = stringArray = new JLabel("\u6b22\u8fce\u4f7f\u7528" + BasicActionsBundle.message(Maps.H("?\u001f>\u001a#@3\u001c*=\n\u0011f4)\u00189\u0001 2,\u0001 -\u0010#*\b.%G\u001c1\t9"), new Object[0]));
        String[] stringArray4 = stringArray;
        stringArray3.setFont(new Font(RequestCancelException.H("\u0000V>v1]-H "), 1, 18));
        stringArray3.setForeground(Color.WHITE);
        stringArray3.setAlignmentX(0.5f);
        String[] stringArray5 = stringArray;
        stringArray.setBorder(new EmptyBorder(0, 20, 0, 0));
        webViewWindowPanel.add((Component)stringArray);
        webViewWindowPanel.add(Box.createVerticalStrut(20));
        String[] stringArray6 = new String[5];
        stringArray6[0] = Maps.H("\u8b84\u6346\u7112\u4e42\u8fb2\u6b1b\u9ac0\u5477\u754f\u0011\u0016,.\u7e90\u4e87\uff57");
        stringArray6[1] = RequestCancelException.H("\u245ey\u83f9\u5317\u70bb\u519e\u2038\u000bD*Q\u205b\u001et\u001dd\u2039\u0004{\u001bPsv3q\u000bW1\u203c}");
        stringArray6[2] = Maps.H("\u2438N\u8fc1\u5113j|S@\u2040,;\u000b7\u001e4O\u0011\u0001=\u0001i\u0018\u000f\u0002)Q?\u0004#\u001a;\u001e*U/-\fD,\u000f>u ,\u0011\u206cv");
        stringArray6[3] = RequestCancelException.H("\u2407\u0004\u4e8d\u4e2a\u628f\u6867\u4e6b\u9037\u62b0\u650c\u6345o\u0001W3\u7ef0\u4ea5\u76b3\u0003A)\u8fe8\u8813\u65d7}");
        stringArray6[4] = Maps.H("\u242eN\u70eb\u5188o\u2069\u0006\t\u2063D\u5e2e\u91aa\u65eb\u547a\u52c1!\u00104\u304f");
        stringArray = stringArray6;
        JPanel jPanel2 = jPanel = new JPanel();
        JPanel jPanel3 = jPanel;
        jPanel2.setLayout(new BoxLayout(jPanel, 1));
        jPanel2.setOpaque(true);
        int n = a = 0;
        while (n < stringArray.length) {
            JLabel jLabel;
            JLabel jLabel2 = jLabel = new JLabel(stringArray[a]);
            JLabel jLabel3 = jLabel;
            JLabel jLabel4 = jLabel;
            jLabel3.setFont(new Font(RequestCancelException.H("\u0000V>v1]-H "), 0, 14));
            jLabel3.setAlignmentX(0.0f);
            jLabel2.setForeground(Color.LIGHT_GRAY);
            jLabel.setBorder(new EmptyBorder(0, 80, 20, 0));
            jPanel.add(jLabel2);
            n = ++a;
        }
        if (!PluginSceneEnum.PLUGIN_PRIVATE.getScene().equals(BasicActionsBundle.message(Maps.H(",\u00071\u001c+\u0010g2\u0012\u0011?\u000e5{\u001a\u000b1\u001f("), new Object[0]))) {
            webViewWindowPanel.add(Box.createVerticalStrut(20));
            JLabel jLabel = a = new JLabel(RequestCancelException.H("\u21a6s\u708e\u51ab\u67e0\u7769\u8bde\u7e99\u8bd5\u6648"));
            JLabel jLabel5 = a;
            a.setBorder(new EmptyBorder(10, 80, 20, 0));
            jLabel5.addMouseListener(new MouseAdapter(webViewWindowPanel){
                public final /* synthetic */ WebViewWindowPanel enum;

                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    MouseEvent a = mouseEvent;
                    c a2 = this;
                    BrowserUtil.browse((String)BasicActionsBundle.message(FileExtensionLanguageDetails.H("nttjTG/uom+`i|j#q}"), new Object[0]));
                }
                {
                    WebViewWindowPanel a = webViewWindowPanel;
                    c a2 = this;
                    a2.enum = a;
                }
            });
            JLabel jLabel6 = a;
            a.setFont(new Font(Maps.H("\u000b\u00065&:\r&\u0018+"), 0, 15));
            JLabel jLabel7 = a;
            jLabel5.setForeground(new Color(100, 149, 237));
            jLabel.setAlignmentX(0.0f);
            jLabel.setOpaque(true);
            jPanel.add(jLabel);
        }
        JPanel jPanel4 = a = new JPanel();
        jPanel4.setLayout(new BoxLayout(a, 0));
        jPanel4.add(Box.createHorizontalGlue());
        a.add(jPanel);
        a.add(Box.createHorizontalGlue());
        webViewWindowPanel.add(Box.createVerticalStrut(30));
        JPanel jPanel5 = a;
        jPanel5.setOpaque(true);
        webViewWindowPanel.add(jPanel5);
        webViewWindowPanel.add(Box.createVerticalStrut(50));
        webViewWindowPanel.add(Box.createVerticalGlue());
    }

    static {
        enum = Maps.H("9\u001c \u001fiG{\u0016&[f");
        WEB_VIEW_PANEL = Key.create((String)RequestCancelException.H("j?Z\tV=O\u000fD,\nd"));
        UNIT_TEST_MESSAGE_DATA = Key.create((String)Maps.H("\r\u00038IU\u0007-\u0019\u001c\r'\u001c2\u000f11(Kb"));
        UNIT_TEST_METHOD_DATA = Key.create((String)RequestCancelException.H("h4\u0004~f0N.u:K0W;a#\u001bi"));
        CODE_MESSAGE_DATA = Key.create((String)Maps.H("B\r:\b\u001c\r'\u001c2\u000f11(Kb"));
        CODE_DEBUG_MESSAGE_DATA = Key.create((String)RequestCancelException.H("w<Y?)oP Z\u0017],L9_:a#\u001bi"));
        CODE_DEBUG_AGENT_DATA = Key.create((String)Maps.H(".>Yd&;\u000f$\u000f\u0015\b6\u0006 1(Kb"));
        OPEN_PAGE_DATA = Key.create((String)RequestCancelException.H("r*]1o9_:a#\u001bi"));
    }
}
