package com.aicode.util;

import com.aicode.content.util.OverlayUtils;
import com.aicode.message.BasicActionsBundle;
import com.intellij.ide.lightEdit.LightEdit;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.extensions.PluginDescriptor;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ta */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/PluginInfoUtils.class */
public final class PluginInfoUtils {
    public static final /* synthetic */ PluginId AICODE_ID;

    /* renamed from: enum, reason: not valid java name */
    public static final /* synthetic */ boolean f715enum;

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m420enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            default:
                H = OverlayUtils.H("\n\u0014 \u001f(\rh_i\u0002.\u001ek&\t\u00058/8\fj\u000b-\u0011.\u0010,\t0\ft\u000b_p7N}\u001f)Bd\u001fhN\u0012l+\u001e=\u0017g\u0004\u0012$|\u0013$L*\u001c7\u001a");
                i = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                H = Maps.H("-\u001f@g\u001f\u0018\u0001=T%\u0003.��;\u000bsm\u0007Av\u0014{\u0003''\u001cU'\u0007 M#\u0017:\u0011*\u0001s\u001a=��<");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            default:
                i2 = 3;
                break;
            case 1:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = OverlayUtils.H("6\u0007;\u0004.\u000495/\u00123\u00054\u001d4\u0004");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = Maps.H("9\u00079@2!\u0017��7\u0002t\u001b&=\u0004Z\u0019\u0004!\n8\u001c\u0007\n>��\u0006��!��#");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            default:
                objArr[1] = OverlayUtils.H("H\u00178k\b4\u0013 \u0006$C3\u001f\b i;\"\u0016 \u0003\u0013\u00192\u0017.90��7\u0005");
                i4 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[1] = Maps.H("\u0015+\u0010\u000e\n!\u0007!\u0003>");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            default:
                objArr[2] = OverlayUtils.H("'\u0010\u0006#>?8\u0014\u0011��1\u000e2\u0018");
                break;
            case 1:
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            default:
                throw new IllegalArgumentException(format);
            case 1:
                throw new IllegalStateException(format);
        }
    }

    public static /* synthetic */ boolean isRemoteIDE() {
        return OverlayUtils.H("0\u001b.\u0013").equals(System.getProperty(Maps.H("<\u0015<@81\u001c\u0017;\t=\u0003\"\\>\u001a;\u0018+0\u001b\u000b*B#\u000b \u0003,\u0016v-\u001a$\u001b(\u001d")));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ boolean isSupportedIDE(@Nullable Project project) {
        if (!isRemoteIDE() && LightEdit.owns(project)) {
            return false;
        }
        return true;
    }

    public static /* synthetic */ boolean isAICodePlugin(@NotNull PluginDescriptor pluginDescriptor) {
        if (pluginDescriptor == null) {
            m420enum(0);
        }
        return pluginDescriptor.getPluginId().equals(AICODE_ID);
    }

    public static /* synthetic */ Path getPluginBasePath() {
        IdeaPluginDescriptor plugin = PluginManagerCore.getPlugin(AICODE_ID);
        if (f715enum || plugin != null) {
            return plugin.getPluginPath();
        }
        throw new AssertionError();
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ String getJetBrainsIDEVersion(String a) {
        ApplicationInfo applicationInfo = ApplicationInfo.getInstance();
        String a2 = "";
        try {
            if (!a.equalsIgnoreCase(OverlayUtils.H("\u0001%\u00034\u0004"))) {
                a2 = applicationInfo.getFullVersion();
            } else {
                a2 = applicationInfo.getMajorVersion();
            }
        } catch (Exception unused) {
        }
        return a2;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static {
        f715enum = !PluginInfoUtils.class.desiredAssertionStatus();
        AICODE_ID = PluginId.getId(BasicActionsBundle.message(Maps.H("\\h\u00011\t4F$\u0003&\u000f=\u001bgVg"), new Object[0]));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NotNull
    public static /* synthetic */ String getVersion() {
        IdeaPluginDescriptor plugin = PluginManagerCore.getPlugin(AICODE_ID);
        String H = plugin == null ? Maps.H(";\u0006?��=\u0018=") : plugin.getVersion();
        if (H == null) {
            throw new RuntimeException();
        }
        if (H == null) {
            m420enum(1);
        }
        return H;
    }
}
