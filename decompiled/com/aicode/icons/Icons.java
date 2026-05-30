package com.aicode.icons;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.AnimatedIcon;
import java.lang.reflect.Method;
import javax.swing.Icon;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/icons/Icons.class */
public class Icons {
    private static final Logger LOG = Logger.getInstance(Icons.class);
    public static Icon PluginIconLogo = IconLoader.getIcon("/icons/indexIcon.svg", Icons.class);
    public static Icon LOGO = IconLoader.getIcon("/icons/toolWindow.svg", Icons.class);
    public static Icon ToolWindowIcon;
    public static Icon PluginIcon;
    public static Icon ReplaceAll;
    public static Icon DebugIcon;
    public static Icon DebugDarkIcon;
    public static Icon StatusBarIcon;
    public static Icon StatusBarIconDisabled;
    public static Icon StatusBarIconNotSignedIn;
    public static Icon StatusBarIconError;
    public static Icon StatusBarCompletionInProgress;
    public static final Icon I_FLY_CODE;
    public static Icon AirPlane;
    public static Icon STOP;

    static {
        ToolWindowIcon = isUnderDarcula() ? IconLoader.getIcon("/icons/toolWindow_dark.svg", Icons.class) : IconLoader.getIcon("/icons/toolWindow.svg", Icons.class);
        PluginIcon = isUnderDarcula() ? IconLoader.getIcon("/icons/toolWindow_dark.svg", Icons.class) : IconLoader.getIcon("/icons/disabled_dark.svg", Icons.class);
        ReplaceAll = IconLoader.getIcon("/svg/replaceAll_dark.svg", Icons.class);
        DebugIcon = IconLoader.getIcon("/icons/debug.svg", Icons.class);
        DebugDarkIcon = IconLoader.getIcon("/icons/debug_dark.svg", Icons.class);
        StatusBarIcon = isUnderDarcula() ? IconLoader.getIcon("/icons/logo_16_dark.svg", Icons.class) : IconLoader.getIcon("/icons/logo_16.svg", Icons.class);
        StatusBarIconDisabled = isUnderDarcula() ? IconLoader.getIcon("/icons/disabled_dark.svg", Icons.class) : IconLoader.getIcon("/icons/disabled.svg", Icons.class);
        StatusBarIconNotSignedIn = IconLoader.getIcon("/icons/not_sign_in.svg", Icons.class);
        StatusBarIconError = StatusBarIconDisabled;
        StatusBarCompletionInProgress = new AnimatedIcon.Default();
        I_FLY_CODE = IconLoader.getIcon("/icons/logo_16.svg", Icons.class);
        AirPlane = IconLoader.getIcon("/icons/air_plane.svg", Icons.class);
        STOP = IconLoader.getIcon("/icons/stop.svg", Icons.class);
    }

    public static Icon getIcon(String path) {
        return IconLoader.getIcon(path, Icons.class);
    }

    public static boolean isUnderDarcula() {
        try {
            Class<?> aClass = Class.forName("com.intellij.util.ui.StartupUiUtil");
            Method method = aClass.getDeclaredMethod("isUnderDarcula", new Class[0]);
            Object object = method.invoke(aClass, new Object[0]);
            return ((Boolean) object).booleanValue();
        } catch (Exception exception) {
            LOG.info("get isUnderDarcula is error" + exception.getMessage());
            return false;
        }
    }

    public static Icon getCurrentIcon() {
        return isUnderDarcula() ? IconLoader.getIcon("/icons/toolWindow_dark.svg", Icons.class) : IconLoader.getIcon("/icons/toolWindow.svg", Icons.class);
    }
}
