package com.aicode.inline.action;

import com.aicode.PluginStartupActivity;
import com.aicode.action.click.PluginAnAction;
import com.aicode.content.util.file.FileExtensionLanguageDetails;
import com.aicode.exception.RequestTimeoutException;
import com.aicode.inline.InlineChatService;
import com.aicode.util.HandleCacheUtil;
import com.aicode.util.Maps;
import com.aicode.util.StringUtils;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.KeyboardShortcut;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.keymap.KeymapManager;
import com.intellij.openapi.project.Project;
import java.util.Map;
import javax.swing.KeyStroke;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: yn */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/action/OpenInlineChatAction.class */
public final class OpenInlineChatAction extends PluginAnAction {

    @NotNull
    public static final String ID = Maps.H("!\"\n=to\u000e7\u00034+<\u000e')7\u0001 Pm");

    /* renamed from: enum, reason: not valid java name */
    public static Logger f368enum = Logger.getInstance(OpenInlineChatAction.class);

    @NotNull
    public static final Companion Companion = new Companion();

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m184enum(int a) {
        throw new IllegalArgumentException(String.format(HandleCacheUtil.H("M!l!m:%`$=i+1\u000eB<~\u001b~8'4Z\u0014g+m:\u007f1^S,qzq,<mt%,\b\\~ri.b:\u0004\u0015i-1,isd g8"), Maps.H("6\u0002-\u0002$"), HandleCacheUtil.H("6d9duC\u0016z.epb:@\u001ae1&7o'b;npi\th<M5}'J\u001eE1p:M0~<d:"), Maps.H("(\u000b \u0007=\u0001\u0003\u0001*\t<\u0006%\t4")));
    }

    /* compiled from: yn */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/action/OpenInlineChatAction$Companion.class */
    public static final class Companion {
        /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m186enum(int a) {
            String H;
            int i;
            int i2;
            int i3;
            int i4;
            switch (a) {
                case 0:
                case 1:
                case 2:
                default:
                    H = FileExtensionLanguageDetails.H("\u000f.HH}gkc.zFI.\\Bq}U{pi7SPz{lv`cS\u0013$4g!)ti=+ojsP\u0011bh~k0lng.~l;ahbp");
                    i = a;
                    break;
                case 3:
                case 4:
                    do {
                    } while (0 != 0);
                    H = RequestTimeoutException.H("\u0010?\u0019#\u0013\t8\u0019a\r\u0011!>\u0018%@y\u000et^(Z|E\u0005#z\u00157\re\u00161\u0001.\b2]4\u000e7\u0016");
                    i = a;
                    break;
            }
            switch (i) {
                case 0:
                case 1:
                case 2:
                default:
                    i2 = 3;
                    break;
                case 3:
                case 4:
                    i2 = 2;
                    do {
                    } while (0 != 0);
            }
            Object[] objArr = new Object[i2];
            switch (a) {
                case 0:
                default:
                    objArr[0] = FileExtensionLanguageDetails.H("o\u007f}r`sGx");
                    i3 = a;
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    objArr[0] = RequestTimeoutException.H("3?\u001d9\f)\u0010\u001f\u0010\"85\u0013>\u00125\u001d");
                    i3 = a;
                    break;
                case 2:
                    objArr[0] = FileExtensionLanguageDetails.H("pol");
                    i3 = a;
                    break;
                case 3:
                case 4:
                    objArr[0] = RequestTimeoutException.H("\u00124\u0017S<2\u00196\u001c9R2\u0014<\u0018\u00182r\u001d7\u0001(\u000f\u001az\u0019\u0007$\u000e\u0015\u00136\u00125\u001fRX\u0017#\u001b\u0018,\u0010*\np64\u0017,\u001c4\u00124\u0014");
                    i3 = a;
                    break;
            }
            switch (i3) {
                case 0:
                case 1:
                case 2:
                default:
                    objArr[1] = FileExtensionLanguageDetails.H("taq\u0006Zg\u007fczl4gri~MT'{bg}iO\u001cLaqh@uct`y\u0007>BEN~yv\u007fl%Paqyzatar");
                    i4 = a;
                    break;
                case 3:
                case 4:
                    do {
                    } while (0 != 0);
                    objArr[1] = RequestTimeoutException.H("W\u0013#\b\u001e?\u00106\u00101\u0007>\u001e\u001d\u001e.\u00124\u0014");
                    i4 = a;
                    break;
            }
            switch (i4) {
                case 0:
                case 1:
                case 2:
                default:
                    objArr[2] = FileExtensionLanguageDetails.H("PkyL|dkn}]tfi{~{h");
                    break;
                case 3:
                case 4:
                    break;
            }
            String format = String.format(H, objArr);
            switch (a) {
                case 0:
                case 1:
                case 2:
                default:
                    throw new IllegalArgumentException(format);
                case 3:
                case 4:
                    throw new IllegalStateException(format);
            }
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        public void addActionShortcut(@NotNull String actionId, @NotNull String defaultKeyBinding, @NotNull Map<String, String> map) {
            if (actionId == null) {
                m186enum(0);
            }
            if (defaultKeyBinding == null) {
                m186enum(1);
            }
            if (map == null) {
                m186enum(2);
            }
            try {
                KeymapManager keymapManager = KeymapManager.getInstance();
                String str = map.get(keymapManager.getActiveKeymap().getName());
                String str2 = str;
                if (str == null) {
                    str2 = defaultKeyBinding;
                }
                KeyboardShortcut keyboardShortcut = new KeyboardShortcut(KeyStroke.getKeyStroke(str2), (KeyStroke) null);
                if (keymapManager.getActiveKeymap().getConflicts(actionId, keyboardShortcut).isEmpty()) {
                    keymapManager.getActiveKeymap().addShortcut(actionId, keyboardShortcut);
                }
            } catch (Exception e) {
                OpenInlineChatAction.f368enum.warn("Failed to register default shortcut for " + actionId, e);
            }
        }

        private Companion() {
        }

        public void register() {
            ActionManager.getInstance().replaceAction(RequestTimeoutException.H("^@\u00139\u0013\u00154\u0010+\u0001\u0017\u001d:\u000e\u001d\u001e.\u00124\u0014"), new OpenInlineChatAction());
            addActionShortcut(FileExtensionLanguageDetails.H("\u000b&F_Fsav~gB{ohHx{tar"), RequestTimeoutException.H("\u001c6\u000f{6"), MapsKt.mapOf(new Pair[]{TuplesKt.to(FileExtensionLanguageDetails.H("C}j;@N.D"), RequestTimeoutException.H("\u001c6\u000f{6")), TuplesKt.to(FileExtensionLanguageDetails.H("@~s\"N@.D)*?3;7"), RequestTimeoutException.H("\u001c6\u000f{6"))}));
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        @NotNull
        public AnAction getRegisteredAction() {
            AnAction action = ActionManager.getInstance().getAction(FileExtensionLanguageDetails.H("\u000b&F_Fsav~gB{ohHx{tar"));
            if (action == null) {
                register();
                AnAction action2 = ActionManager.getInstance().getAction(RequestTimeoutException.H("^@\u00139\u0013\u00154\u0010+\u0001\u0017\u001d:\u000e\u001d\u001e.\u00124\u0014"));
                if (action2 == null) {
                    m186enum(4);
                }
                return action2;
            }
            if (action == null) {
                m186enum(3);
            }
            return action;
        }
    }

    public OpenInlineChatAction() {
        super(Maps.H("!\"\n=\u001dH\u00068\u0006=\rt6!^w"), HandleCacheUtil.H("6}7j{p \u0004\u0012h5x isi=j "), AllIcons.General.Add);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void actionPerformed(@NotNull AnActionEvent a) {
        Editor selectedTextEditor;
        if (a == null) {
            m184enum(0);
        }
        Project project = a.getProject();
        if (project == null || (selectedTextEditor = FileEditorManager.getInstance(project).getSelectedTextEditor()) == null || StringUtils.isBlank(PluginStartupActivity.getApiKey())) {
            return;
        }
        InlineChatService.Companion.openInlineChat(selectedTextEditor);
    }
}
