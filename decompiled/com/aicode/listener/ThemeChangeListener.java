package com.aicode.listener;

import com.aicode.agent.SocketMessageHandleListener;
import com.aicode.content.util.file.FileExtensionLanguageDetails;
import com.aicode.enums.WebViewResponseTypeEnum;
import com.aicode.icons.Icons;
import com.aicode.message.BasicActionsBundle;
import com.aicode.service.editor.CancelRequestTip;
import com.aicode.statusBar.StatusBarPopup;
import com.aicode.util.ApplicationUtil;
import com.aicode.util.StringUtils;
import com.google.gson.JsonObject;
import com.intellij.ide.ui.LafManagerListener;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.colors.EditorColorsListener;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.util.messages.MessageBusConnection;
import javax.swing.UIManager;
import org.jetbrains.annotations.NotNull;

/* compiled from: ic */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/listener/ThemeChangeListener.class */
public class ThemeChangeListener implements ApplicationComponent {

    /* renamed from: float, reason: not valid java name */
    private int f521float;

    /* renamed from: byte, reason: not valid java name */
    private String f522byte;

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f523enum = Logger.getInstance(ThemeChangeListener.class);

    public void disposeComponent() {
    }

    public void initComponent() {
        LafManagerListener lafManagerListener = a -> {
            ThemeChangeListener themeChangeListener;
            String str = null;
            try {
                str = ((UIManager.LookAndFeelInfo) a.getClass().getMethod(FileExtensionLanguageDetails.H("{baFb^LlugM{inV`xCrwl"), new Class[0]).invoke(a, new Object[0])).getName();
                themeChangeListener = this;
            } catch (Exception e) {
                themeChangeListener = this;
            }
            if (StringUtils.isBlank(themeChangeListener.f522byte)) {
                this.f522byte = UIManager.getLookAndFeel().getName();
            }
            if (this.f521float == 0) {
                this.f521float = EditorColorsManager.getInstance().getGlobalScheme().getConsoleFontSize();
            }
            if (str != null && !str.toLowerCase().contains(this.f522byte.toLowerCase())) {
                changeTheme(str, this.f521float);
            }
            this.f522byte = str;
        };
        EditorColorsListener editorColorsListener = editorColorsScheme -> {
            int consoleFontSize = EditorColorsManager.getInstance().getGlobalScheme().getConsoleFontSize();
            if (this.f521float != consoleFontSize) {
                changeTheme(this.f522byte, consoleFontSize);
            }
            this.f521float = consoleFontSize;
        };
        MessageBusConnection connect = ApplicationManager.getApplication().getMessageBus().connect();
        connect.subscribe(LafManagerListener.TOPIC, lafManagerListener);
        connect.subscribe(EditorColorsManager.TOPIC, editorColorsListener);
    }

    public static void changeTheme(String a, int a2) {
        ApplicationManager.getApplication().invokeLater(() -> {
            String message = BasicActionsBundle.message(CancelRequestTip.H("\u000e\u0013\u000e27B\r\f\u0006\u0005\u000e\u0006M$\u0005!<\u0002\u001f6\u0014\u0004\u0019\u000e\u000f-\u0018\u000e\u0014\u0006X\u0015\u0004\u001d\u0011"), new Object[0]);
            for (Project project : ApplicationUtil.findValidProjects()) {
                if (!project.isDisposed()) {
                    JsonObject jsonObject = new JsonObject();
                    JsonObject jsonObject2 = new JsonObject();
                    jsonObject2.addProperty(FileExtensionLanguageDetails.H("qjswl"), getTheme(a, ToolWindowManager.getInstance(project).getToolWindow(message)));
                    jsonObject2.addProperty(CancelRequestTip.H("\u0007\u000e\u0018\u00022\b\u001f��"), Integer.valueOf(a2));
                    jsonObject.addProperty(FileExtensionLanguageDetails.H("qnbe"), WebViewResponseTypeEnum.SETTING_CHANGE_THEME.getType());
                    jsonObject.add(CancelRequestTip.H("����\r\u0010��"), jsonObject2);
                    SocketMessageHandleListener.send2Web(project, jsonObject);
                    StatusBarPopup.update(project);
                }
            }
        });
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static String getTheme(String a, ToolWindow a2) {
        if (StringUtils.isNotBlank(a) && a.toLowerCase().contains(FileExtensionLanguageDetails.H("plpzt"))) {
            String H = CancelRequestTip.H("\r��\u000e\t\u0015");
            Icons.StatusBarIcon = IconLoader.getIcon(FileExtensionLanguageDetails.H("*~OQgh<m{ajH?*+ddg"), Icons.class);
            a2.setIcon(IconLoader.getIcon(CancelRequestTip.H("N\b\t\u0005\u000f\u0012Y\u0012\n\u0010��\u0003\u001c\u00154\u000f\b\r\u0013\nG\u001a\u0017\u0006"), Icons.class));
            return H;
        }
        String H2 = FileExtensionLanguageDetails.H("av`k");
        Icons.StatusBarIcon = IconLoader.getIcon(CancelRequestTip.H("N\u0003\t\u000e\u000f\u0005Y\u000f\f\u0006\u000e/Af\u000f\b\r\u0013\nG\u001a\u0017\u0006"), Icons.class);
        a2.setIcon(IconLoader.getIcon(FileExtensionLanguageDetails.H("'sb|`o(ajx@i`uwncYav|w+ddg"), Icons.class));
        return H2;
    }

    public static void initTheme() {
        EditorColorsScheme globalScheme = EditorColorsManager.getInstance().getGlobalScheme();
        changeTheme(globalScheme.getName(), globalScheme.getConsoleFontSize());
    }

    @NotNull
    public String getComponentName() {
        return CancelRequestTip.H(" \u0003\u000e\u0007\u000f\"\t\u000b\u0004\u0017\u0015\u0006#\u0016\u0011\f\u0007\u0004\u0013");
    }
}
