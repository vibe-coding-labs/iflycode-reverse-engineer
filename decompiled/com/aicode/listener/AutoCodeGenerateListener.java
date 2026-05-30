package com.aicode.listener;

import com.aicode.PluginStartupActivity;
import com.aicode.action.batch.MethodGeneratorConfig;
import com.aicode.agent.dto.CodeTipRequestDto;
import com.aicode.apm.enums.SpanAttrEnum;
import com.aicode.domain.CommandCache;
import com.aicode.enums.CodeTipRequestType;
import com.aicode.enums.OperateActionEnum;
import com.aicode.service.EditorManagerService;
import com.aicode.service.editor.DocumentActionTracker;
import com.aicode.service.editor.RequestTipServiceImpl;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.ui.FontKt;
import com.aicode.util.ApplicationUtil;
import com.aicode.util.HandleCacheUtil;
import com.aicode.util.JComponentKt;
import com.aicode.util.StringUtils;
import com.intellij.openapi.command.CommandEvent;
import com.intellij.openapi.command.CommandListener;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.editor.ex.DocumentEx;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.EmptyRunnable;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import io.opentelemetry.api.trace.Span;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: qn */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/listener/AutoCodeGenerateListener.class */
public class AutoCodeGenerateListener implements CommandListener {

    /* renamed from: case, reason: not valid java name */
    private final AtomicReference<T> f490case;

    /* renamed from: final, reason: not valid java name */
    private final Project f491final;

    /* renamed from: try, reason: not valid java name */
    private final AtomicBoolean f492try;
    public final AtomicInteger atomicOperate;

    /* renamed from: byte, reason: not valid java name */
    private static final Logger f494byte = LoggerFactory.getLogger(AutoCodeGenerateListener.class);

    /* renamed from: enum, reason: not valid java name */
    private static final Key<Q> f495enum = Key.create(JComponentKt.H("\u000e7\u001b\u007fR*G/\u0016 \u0011#\u0010 0,\u001ffQ"));
    public static final AtomicBoolean ignoreApply = new AtomicBoolean(false);
    public static final AtomicBoolean ignoreLookupApply = new AtomicBoolean(false);
    public static final AtomicBoolean commandName = new AtomicBoolean(false);
    public static final AtomicBoolean commandNameCtrlZ = new AtomicBoolean(false);
    public static final AtomicBoolean isImitationDealFlag = new AtomicBoolean(false);
    public static final AtomicBoolean isImitationBuryingPoint = new AtomicBoolean(false);
    public static final AtomicBoolean commandNameTab = new AtomicBoolean(false);
    public static final AtomicBoolean inlineChatOperate = new AtomicBoolean(false);

    /* renamed from: float, reason: not valid java name */
    private static final Key<CommandCache> f493float = Key.create(MethodGeneratorConfig.H(">/+gb2w;90>;:9\u0010!-ba"));

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m235enum(int a) {
        String H = JComponentKt.H("\b\u001d9\roA\u0003?y\u00197\fc%!&7+=\u0002%O\u001b,;\u000e/\u0001=\n,XjN+Yi��\u0006f`\u0010q\\+^$\u001a1\u0010i\u00011\fi\r'D7\n-\u000b");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            case 5:
            default:
                objArr[0] = MethodGeneratorConfig.H("/(;+*:#");
                break;
            case 1:
            case 2:
            case 3:
                do {
                } while (0 != 0);
                objArr[0] = JComponentKt.H("\u0001/\u001a/\u0013");
                break;
            case 4:
                objArr[0] = MethodGeneratorConfig.H("509!,*7#");
                break;
            case 6:
            case 7:
                objArr[0] = JComponentKt.H("'��0\u000b.\u0015");
                break;
            case 8:
            case 10:
                objArr[0] = MethodGeneratorConfig.H("2(=*#");
                break;
            case 9:
            case 11:
                objArr[0] = JComponentKt.H("1\u0001:\u0010/\u0003");
                break;
        }
        objArr[1] = MethodGeneratorConfig.H("31<p\u0012\u001420>1~3/;!>.+#p9\u0003)<\u0004&$+\u0016:41#>2-\u001d6) $!<%");
        switch (a) {
            case 0:
            default:
                objArr[2] = JComponentKt.H("~\r7\u00165Y");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = MethodGeneratorConfig.H("9;<2'&5\f.53;<3");
                break;
            case 2:
                objArr[2] = JComponentKt.H(" \u001c\u0007\n8\u001d-\u0002");
                break;
            case 3:
                objArr[2] = MethodGeneratorConfig.H("<59<>(,\u001764=2'<3");
                break;
            case 4:
                objArr[2] = JComponentKt.H(".\n6 &\f+\u0015,\u000167-\u001e,\u0017");
                break;
            case 5:
                objArr[2] = MethodGeneratorConfig.H(")4+\t1=:%<4;\u001f0(;6%");
                break;
            case 6:
                objArr[2] = JComponentKt.H(";\f,\u000e6\u0001\n��3\u0015(\u0001&7-\u001e5\u0002");
                break;
            case 7:
                objArr[2] = MethodGeneratorConfig.H("2-\u001d\u0017)6\u0012'$!\u0005-;:\"/':41.\u00075.-2");
                break;
            case 8:
            case 9:
                objArr[2] = JComponentKt.H(",\u0010\u001b\u0016;\u000b$\n,\u0010\u0004��:\u0011/\u0006!\u0005-\u0016.\t");
                break;
            case 10:
            case 11:
                objArr[2] = MethodGeneratorConfig.H(":4\n!<4+\n;\"62!>1\u0019< !>2");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private void bb(Editor a) {
        try {
            if (commandNameCtrlZ.get()) {
                String str = RequestTipServiceImpl.LATEST_RESPONSE_DATA.get(a.getProject());
                if (!StringUtils.isNotBlank(str) || !RequestTipServiceImpl.CODE_TIP_MAP.containsKey(str)) {
                    return;
                }
                CodeTipRequestDto codeTipRequestDto = RequestTipServiceImpl.CODE_TIP_MAP.get(str);
                Span parentSpan = codeTipRequestDto.getParentSpan();
                parentSpan.setAttribute(SpanAttrEnum.COMPLETE_RESULT.getText(), codeTipRequestDto.getLastReplacementText());
                parentSpan.end();
                RequestTipServiceImpl.CODE_TIP_MAP.remove(str);
                RequestTipServiceImpl.LAST_REQUEST.get(a.getProject()).remove(str);
            }
        } catch (Throwable th) {
            f494byte.error(MethodGeneratorConfig.H("-=:\u0019\u0004\u000f66<%=%\u001c;79:f-$*9*"), th);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void commandStarted(@NotNull CommandEvent a) {
        AutoCodeGenerateListener autoCodeGenerateListener;
        if (a == null) {
            m235enum(1);
        }
        if (a.getCommandName() != null) {
            commandNameTab.set(MethodGeneratorConfig.H("\u000b1<").equalsIgnoreCase(a.getCommandName()));
        }
        if (this.atomicOperate.getAndIncrement() > 0) {
            f494byte.info(JComponentKt.H(";\u0011$\u0002\u0001(!0+\u0018*\n,\u000bb\u0005=��3\u0011,&3��3\u00065\u0002m\f'\u0010\t��\u001b\u00106\u001d?\u000e3\u001d6\niQh^"));
            return;
        }
        if (!ignoreApply.get()) {
            Editor uB = uB(this.f491final);
            if (uB == null) {
                return;
            }
            if (uB.getSelectionModel().hasSelection()) {
                CommandCache commandCache = new CommandCache();
                autoCodeGenerateListener = this;
                commandCache.setStartSelected(true);
                commandCache.setStartSelectedStartOffset(uB.getSelectionModel().getSelectionStart());
                f493float.set(uB, commandCache);
            } else {
                f493float.set(uB, (Object) null);
                autoCodeGenerateListener = this;
            }
            if (autoCodeGenerateListener.BB(a)) {
                return;
            }
            if (!ApplicationUtil.isSupportLanguage(uB).booleanValue()) {
                f494byte.info(JComponentKt.H("=\u0017\"\u0004\"\u000b%45\u0006?\u001f'��h\u0007\f\n-\u000e=\u0004,\fx\u0018(\u0003;\u000b"));
                return;
            }
            this.f492try.set(true);
            f495enum.set(uB, Bb(uB));
            commandName.set(MethodGeneratorConfig.H("\u000b?+5,").equalsIgnoreCase(a.getCommandName()));
            isImitationDealFlag.set(JComponentKt.H(";'\u001b-\u001c").equalsIgnoreCase(a.getCommandName()));
            if (a.getCommandName() != null) {
                commandNameCtrlZ.set(a.getCommandName().startsWith(MethodGeneratorConfig.H("\u0004141")) || a.getCommandName().startsWith(JComponentKt.H("擬淦")));
            }
            bb(uB);
            return;
        }
        f494byte.info(MethodGeneratorConfig.H("%':4:;=\u0004-6'/?0p7��\u0007/<0\u001a68,7q85*"));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void commandFinished(@NotNull CommandEvent a) {
        Q q;
        if (a == null) {
            m235enum(3);
        }
        if (this.atomicOperate.decrementAndGet() > 0) {
            f494byte.info(JComponentKt.H("\u001d&\u0002\r'+\u0007\u0019\u00106\u0017:\u0007'��i\u000e*\u0017\"�� *1\u00023\u00069\u000eb\u0003-\u001a+\"!*,\u00077\u00062\u001c*\u0016iQh^"));
            return;
        }
        if (ignoreLookupApply.get()) {
            f494byte.info(MethodGeneratorConfig.H("<59<>(,\u001105<*?<3u2=:?,\u001702<1!-\u0012790+q85*"));
            ignoreLookupApply.set(false);
            ignoreApply.set(false);
            return;
        }
        if (!ignoreApply.get()) {
            if (!inlineChatOperate.get()) {
                Editor uB = uB(this.f491final);
                if (uB == null) {
                    return;
                }
                if (!uB.getSelectionModel().hasSelection() && f493float.isIn(uB)) {
                    CommandCache commandCache = (CommandCache) f493float.get(uB);
                    if (commandCache.isStartSelected() && uB.getCaretModel().getOffset() > commandCache.getStartSelectedStartOffset()) {
                        if (JComponentKt.H("N").equals(uB.getDocument().getText(TextRange.create(commandCache.getStartSelectedStartOffset(), uB.getCaretModel().getOffset())))) {
                            f493float.set(uB, (Object) null);
                            return;
                        }
                    }
                }
                EditorManagerService editorManagerService = EditorManagerService.getInstance();
                boolean z = commandName.get();
                commandName.set(false);
                if (BB(a)) {
                    return;
                }
                if (!DocumentActionTracker.getInstance().getExecutingForcedCodeGenerateAction()) {
                    Runnable command = a.getCommand();
                    if (commandNameTab.get()) {
                        commandNameTab.set(false);
                        return;
                    }
                    if (!this.f492try.get() || uB == null) {
                        return;
                    }
                    if (!ApplicationUtil.isSupportLanguage(uB).booleanValue()) {
                        f494byte.info(MethodGeneratorConfig.H("+846473\u001f>;2)<5:R\u0015.��/$-<5=|403#;"));
                        return;
                    }
                    if (editorManagerService.isAvailable(uB) && (q = (Q) f495enum.get(uB)) != null) {
                        Q Bb = Bb(uB);
                        if (!xA(q, Bb)) {
                            if (command instanceof EmptyRunnable) {
                                editorManagerService.editorChanged(uB, CodeTipRequestType.Automatic, false);
                                return;
                            } else {
                                if (!Va(q, Bb)) {
                                    return;
                                }
                                editorManagerService.disposeTips(uB, OperateActionEnum.CaretChange);
                                return;
                            }
                        }
                        isImitationBuryingPoint.set(true);
                        editorManagerService.editorChanged(uB, CodeTipRequestType.Automatic, z);
                        return;
                    }
                    return;
                }
                editorManagerService.disposeTips(uB, OperateActionEnum.CaretChange);
                DocumentActionTracker.getInstance().exitForcedCodeGenerateAction();
                return;
            }
            f494byte.info(MethodGeneratorConfig.H(">7;<5?\u00178?\u0006\\2#?&<'\"i55?0\";"));
            inlineChatOperate.set(false);
            return;
        }
        f494byte.info(JComponentKt.H("\u001b \u0004.\u0004/\u0003\u0007\u000e#\u00021\f-\nJ%\"\r-\u0016 \"/\t(\u001bi\b-\u001a"));
        ignoreApply.set(false);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private boolean BB(@NotNull CommandEvent a) {
        if (a == null) {
            m235enum(2);
        }
        if (a != null && !StringUtils.isBlank(PluginStartupActivity.getApiKey()) && AICodeSettingsState.getInstance().autoTrigger) {
            return false;
        }
        return true;
    }

    public AutoCodeGenerateListener(@NotNull Project a) {
        if (a == null) {
            m235enum(0);
        }
        this.atomicOperate = new AtomicInteger();
        this.f492try = new AtomicBoolean(false);
        this.f490case = new AtomicReference<>();
        this.f491final = a;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Nullable
    private static Editor uB(@NotNull Project project) {
        if (project == null) {
            m235enum(5);
        }
        FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
        if (fileEditorManager != null) {
            return fileEditorManager.getSelectedTextEditor();
        }
        return null;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void undoTransparentActionStarted() {
        Editor uB = uB(this.f491final);
        this.f490case.set(uB != null ? Rc(uB) : null);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static boolean xA(@NotNull Q second, @NotNull Q first) {
        if (second == null) {
            m235enum(8);
        }
        if (first == null) {
            m235enum(9);
        }
        return second.f497enum != first.f497enum;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: qn */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/listener/AutoCodeGenerateListener$T.class */
    public static final class T {

        /* renamed from: byte, reason: not valid java name */
        @NotNull
        private final Editor f498byte;

        /* renamed from: enum, reason: not valid java name */
        private final long f499enum;

        /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m236enum(int a) {
            String H;
            int i;
            int i2;
            int i3;
            int i4;
            switch (a) {
                case 0:
                default:
                    H = HandleCacheUtil.H("A-l!m:y<krK\t0\u000f_!~\u001bS\u0015fuq?r>O\u0018t:yt'zdo$4wn%,\u0007S\u007fs{<b: 1d  =rh/k]\u0002");
                    i = a;
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    H = FontKt.H("\u0012��61\u001c;)5v'&+:!\u001fG{1j}0\u007f?;*1r *-3}\u0006\u000b'<7e,+6*");
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
                    objArr[0] = HandleCacheUtil.H("r,(j^\u001c");
                    i3 = a;
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    objArr[0] = FontKt.H("<=#v$;-*=|*\u001a\u00031*&1=6[)-0<\f=*\u0015+7 <73: \u0015?97:<+\tC\u000b, 7\u0017-3 *53< 7gN��\u000b;!7\u00166?.#");
                    i3 = a;
                    break;
            }
            switch (i3) {
                case 0:
                default:
                    objArr[1] = HandleCacheUtil.H("wf;#2\u007f*~*epg=s+r&.f\u000b:e;~\re1C>o;d,a+G1i,\u007f1n:elQ5u!T-H\u0018\u007f#w;t t\u001eh i0y\u001b5\u007fE\u000b");
                    i4 = a;
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    objArr[1] = FontKt.H("\u00187:\u001c!+*54");
                    i4 = a;
                    break;
            }
            switch (i4) {
                case 0:
                default:
                    objArr[2] = HandleCacheUtil.H("+!/wEP");
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

        @NotNull
        public Editor WA() {
            Editor editor = this.f498byte;
            if (editor == null) {
                m236enum(1);
            }
            return editor;
        }

        public T(@NotNull Editor editor, long a) {
            if (editor == null) {
                m236enum(0);
            }
            this.f498byte = editor;
            this.f499enum = a;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static boolean Va(@NotNull Q second, @NotNull Q first) {
        if (second == null) {
            m235enum(10);
        }
        if (first == null) {
            m235enum(11);
        }
        return !second.f496byte.equals(first.f496byte);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: qn */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/listener/AutoCodeGenerateListener$Q.class */
    public static final class Q {

        /* renamed from: byte, reason: not valid java name */
        private final VisualPosition f496byte;

        /* renamed from: enum, reason: not valid java name */
        private final long f497enum;

        public Q(long a, VisualPosition a2) {
            this.f497enum = a;
            this.f496byte = a2;
        }
    }

    public void undoTransparentActionFinished() {
        T t = this.f490case.get();
        this.f490case.set(null);
        Editor uB = uB(this.f491final);
        if (uB != null && t != null && uB == t.f498byte && wb(uB.getDocument()) != t.f499enum) {
            EditorManagerService editorManagerService = EditorManagerService.getInstance();
            if (editorManagerService.isAvailable(uB) && editorManagerService.hasTipInlays(uB)) {
                editorManagerService.editorChanged(uB, CodeTipRequestType.Forced, false);
            }
        }
    }

    @NotNull
    private static T Rc(@NotNull Editor editor) {
        if (editor == null) {
            m235enum(7);
        }
        return new T(editor, wb(editor.getDocument()));
    }

    @NotNull
    private static Q Bb(@NotNull Editor editor) {
        if (editor == null) {
            m235enum(6);
        }
        return new Q(wb(editor.getDocument()), editor.getCaretModel().getVisualPosition());
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static long wb(@NotNull Document document) {
        if (document == null) {
            m235enum(4);
        }
        return document instanceof DocumentEx ? ((DocumentEx) document).getModificationSequence() : document.getModificationStamp();
    }
}
