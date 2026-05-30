package com.aicode.statusBar;

import com.aicode.PluginStartupActivity;
import com.aicode.enums.AICodeStatus;
import com.aicode.icons.Icons;
import com.aicode.language.AICodeLanguageInfo;
import com.aicode.message.BasicActionsBundle;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.status.AICodeStatusService;
import com.aicode.util.Maps;
import com.aicode.util.StringUtils;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.openapi.wm.impl.status.EditorBasedStatusBarPopup;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: jc */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/statusBar/StatusBarPopup.class */
public class StatusBarPopup extends EditorBasedStatusBarPopup {

    /* renamed from: enum, reason: not valid java name */
    private static final String f620enum = AICodeLanguageInfo.H(">\u0007t\byC,V6\u001b6\u0015-\u001b\u0014\u001b:\bqQ");

    /* renamed from: byte, reason: not valid java name */
    public static String f619byte = "";

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m308enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 8:
            case 9:
            case 10:
            default:
                H = Maps.H("$+\u0003-Bv\u001c:U/\u000f.U\t:'\u0006��6\u0013\f|\u0003.]r?\u000b\u0001,\u0012|Rl\u0007oR!%_E/]j\"M\u0003'\u00029T&\u001e9D:\ns\u001a=��<");
                i = a;
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                do {
                } while (0 != 0);
                H = AICodeLanguageInfo.H("\u000b47\u001dJ@\u0015$~\u0002.\u000e6��;N|\u001bF|8Z5\u001c\t?e\u001a5\u001f\u007f\u001c?\u001f:\f*U1\u001b+\u001a");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 8:
            case 9:
            case 10:
            default:
                i2 = 3;
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 9:
            case 10:
            default:
                objArr[0] = Maps.H("(\u001d<\u001e-\u000f$");
                i3 = a;
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                do {
                } while (0 != 0);
                objArr[0] = AICodeLanguageInfo.H("+1\u0002d\u001b7\f0\n<G\u001b-*\u000e-\u001a8*7[\t\u001f>\u001a/\u0018\r\u001f6%0\u001e2\u0006");
                i3 = a;
                break;
            case 8:
                objArr[0] = Maps.H(".\u0001:\u0003(\n,<'\u0015<\u0019#");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            case 2:
            case 8:
            case 9:
            case 10:
            default:
                objArr[1] = AICodeLanguageInfo.H("+1\u0002d\u001b7\f0\n<G\u001b-*\u000e-\u001a8*7[\t\u001f>\u001a/\u0018\r\u001f6%0\u001e2\u0006");
                i4 = a;
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                do {
                } while (0 != 0);
                objArr[1] = Maps.H("\u0016(��\u001f\u0018)\u0003=\u001b����)\u00185");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            case 1:
            default:
                objArr[2] = AICodeLanguageInfo.H("1\u0005;\u000f3\u0013");
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[2] = Maps.H("S:\u001a!\u0018n");
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                break;
            case 8:
                objArr[2] = AICodeLanguageInfo.H("\u00123\u0005;>5\u001b:\u000e\t\u00101\u001b\u000e\u0012");
                break;
            case 9:
                objArr[2] = Maps.H("\u0012?\u0011)\u0005(-6\u001c'\u0015&\u000f5");
                break;
            case 10:
                objArr[2] = AICodeLanguageInfo.H("<\u0002!\u001a\u0013\u001c;\t\"\u0002");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 8:
            case 9:
            case 10:
            default:
                throw new IllegalArgumentException(format);
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                throw new IllegalStateException(format);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @Nullable
    private ListPopup Oa(DataContext a, boolean z) {
        AnAction anAction;
        try {
            AICodeStatus aICodeStatus = (AICodeStatus) AICodeStatusService.getCurrentStatus().first;
            if (aICodeStatus != AICodeStatus.Unsupported) {
                AnAction action = ActionManager.getInstance().getAction(pC(aICodeStatus));
                if (!(action instanceof ActionGroup)) {
                    return null;
                }
                if (!z) {
                    anAction = action;
                } else {
                    AnAction defaultActionGroup = new DefaultActionGroup();
                    defaultActionGroup.addAll(new AnAction[]{action});
                    anAction = defaultActionGroup;
                }
                return JBPopupFactory.getInstance().createActionGroupPopup(BasicActionsBundle.message(Maps.H("\u001e\t?\u001c+4C\u001e>\u0004*\u001d&_9'\u0006\f9"), new Object[0]), (ActionGroup) anAction, a, JBPopupFactory.ActionSelectionAid.SPEEDSEARCH, z);
            }
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Nullable
    public ListPopup createPopup(DataContext a) {
        try {
            return Oa(a, false);
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @NotNull
    public EditorBasedStatusBarPopup.WidgetState getWidgetState(@Nullable VirtualFile a) {
        EditorBasedStatusBarPopup.WidgetState widgetState;
        Pair<AICodeStatus, String> currentStatus = AICodeStatusService.getCurrentStatus();
        AICodeStatus aICodeStatus = (AICodeStatus) currentStatus.first;
        if (StringUtils.isBlank(PluginStartupActivity.getApiKey())) {
            EditorBasedStatusBarPopup.WidgetState widgetState2 = new EditorBasedStatusBarPopup.WidgetState(BasicActionsBundle.message(Maps.H("5\u0003\"\u00018_,\u000f9\u000b<\u0010g\u0013\u000e\u0001 \n+'9\u00101\u001a'\u0015\u001c\u001a<\u0010r\u0001,\u001c,"), new Object[0]), AICodeStatus.NotSignedIn.getPresentableText(), true);
            widgetState2.setIcon(Icons.StatusBarIconNotSignedIn);
            if (widgetState2 == null) {
                m308enum(3);
            }
            return widgetState2;
        }
        if (!aICodeStatus.isIconAlwaysShown()) {
            if (a != null) {
                Boolean XB = XB();
                if (XB == null) {
                    EditorBasedStatusBarPopup.WidgetState widgetState3 = EditorBasedStatusBarPopup.WidgetState.HIDDEN;
                    if (widgetState3 == null) {
                        m308enum(6);
                    }
                    return widgetState3;
                }
                EditorBasedStatusBarPopup.WidgetState widgetState4 = new EditorBasedStatusBarPopup.WidgetState(BasicActionsBundle.message(AICodeLanguageInfo.H("\u0013(\u0004*\u001et\n$\u001f \u001a;A8(*\u0006!\r\f\u001f;\u00171\u0001>:1\u001a;T*\n7\n"), new Object[0]), f619byte, true);
                if (aICodeStatus.equals(AICodeStatus.CompletionInProgress)) {
                    widgetState4.setIcon(Icons.StatusBarCompletionInProgress);
                    widgetState = widgetState4;
                } else {
                    widgetState4.setIcon(XB.booleanValue() ? aICodeStatus.getIcon() : Icons.StatusBarIconDisabled);
                    widgetState = widgetState4;
                }
                if (widgetState == null) {
                    m308enum(7);
                }
                return widgetState;
            }
            EditorBasedStatusBarPopup.WidgetState widgetState5 = EditorBasedStatusBarPopup.WidgetState.HIDDEN;
            if (widgetState5 == null) {
                m308enum(5);
            }
            return widgetState5;
        }
        String str = (String) currentStatus.second;
        EditorBasedStatusBarPopup.WidgetState widgetState6 = new EditorBasedStatusBarPopup.WidgetState(str == null ? aICodeStatus.getPresentableText() : aICodeStatus.getPresentableText() + ":" + str, f619byte, true);
        widgetState6.setIcon(aICodeStatus.getIcon());
        if (widgetState6 == null) {
            m308enum(4);
        }
        return widgetState6;
    }

    public static void update(@NotNull Project project, String statusBarText) {
        if (project == null) {
            m308enum(1);
        }
        if (statusBarText != null) {
            f619byte = statusBarText;
        }
        StatusBarPopup Wb = Wb(project);
        if (Wb != null) {
            Wb.update(() -> {
                Wb.myStatusBar.updateWidget(AICodeLanguageInfo.H("\t0e\u00197\r\u001fe5\u0018/\f6��\r\u0002(\u001a,\f"));
            });
        }
    }

    @Nullable
    private Boolean XB() {
        return Boolean.valueOf(AICodeSettingsState.getInstance().autoTrigger);
    }

    @NonNls
    @NotNull
    public String ID() {
        return AICodeLanguageInfo.H("\t0e\u00197\r\u001fe5\u0018/\f6��\r\u0002\u00075\u000b+");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StatusBarPopup(@NotNull Project a) {
        super(a, false);
        if (a == null) {
            m308enum(2);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Nullable
    private static StatusBarPopup Wb(@NotNull Project project) {
        if (project == null) {
            m308enum(10);
        }
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);
        if (statusBar != null) {
            StatusBarPopup widget = statusBar.getWidget(Maps.H("\"\u0016N?\u001c+4C\u001e>\u0004*\u001d&&$2\r\u0006+"));
            if (widget instanceof StatusBarPopup) {
                return widget;
            }
            return null;
        }
        return null;
    }

    public static void update(@NotNull Project project) {
        if (project == null) {
            m308enum(0);
        }
        StatusBarPopup Wb = Wb(project);
        if (Wb == null) {
            return;
        }
        Wb.update(() -> {
            Wb.myStatusBar.updateWidget(Maps.H("\"\u0016N?\u001c+4C\u001e>\u0004*\u001d&&$\u0015*\u0005("));
        });
    }

    @NotNull
    public StatusBarWidget createInstance(@NotNull Project a) {
        if (a == null) {
            m308enum(9);
        }
        return new StatusBarPopup(a);
    }

    @NotNull
    private String pC(@NotNull AICodeStatus a) {
        if (a == null) {
            m308enum(8);
        }
        return AICodeLanguageInfo.H(")!\u001a$\u001e=G\t?$��/\u0018\u001d\u000f(;5\u001b\u001c(");
    }
}
