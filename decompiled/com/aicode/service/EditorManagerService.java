package com.aicode.service;

import com.aicode.PluginStartupActivity;
import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.enums.CodeTipRequestType;
import com.aicode.enums.OperateActionEnum;
import com.aicode.exception.RequestTimeoutException;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.util.StringUtils;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.util.concurrency.annotations.RequiresEdt;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/EditorManagerService.class */
public interface EditorManagerService extends Disposable {
    public static final Logger LOG = Logger.getInstance(EditorManagerService.class);

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m262enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            default:
                H = RequestTimeoutException.H("[t\u001f%\u001f\u00050\u0011a\r?\u000f/\t\u001cyq\u0006u_'U?\u0006#\u0005g\b\t3p\u0003\"\u00126\u00102]8\u0002<\u001d");
                i = a;
                break;
            case 1:
            case 2:
            case 3:
                do {
                } while (0 != 0);
                H = OpenTelemetryUtil.H("D3c5\"n|\"$&|%7\u0013F#v\bq,#+T\u0001w e)a4|j4r_O +ik%7(gw`~\"A\u0002$.|#71mll3h,");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            default:
                i2 = 2;
                break;
            case 1:
            case 2:
            case 3:
                i2 = 3;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = RequestTimeoutException.H("2>\u001ds\u001c(\u00035\u001f\"I\u000b<&\u00032\u00191Z\u0017\u00179\u0005(\u0014+&>\u0010 \u0003119\u000f \u001e3\u0014");
                i3 = a;
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                objArr[0] = OpenTelemetryUtil.H("m(k2k2");
                i3 = a;
                break;
            case 3:
                objArr[0] = RequestTimeoutException.H("\u0003\"\u00176\u0007/\t\u0002\u000e \u0014");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            default:
                objArr[1] = OpenTelemetryUtil.H("'v#^={8c(g%");
                i4 = a;
                break;
            case 1:
            case 2:
            case 3:
                do {
                } while (0 != 0);
                objArr[1] = RequestTimeoutException.H("2>\u001ds\u001c(\u00035\u001f\"I\u000b<&\u00032\u00191Z\u0017\u00179\u0005(\u0014+&>\u0010 \u0003119\u000f \u001e3\u0014");
                i4 = a;
                break;
        }
        switch (i4) {
            case 1:
                do {
                } while (0 != 0);
                objArr[2] = OpenTelemetryUtil.H("l!`\u0003~#A\"n'}3");
                break;
            case 2:
            case 3:
                objArr[2] = RequestTimeoutException.H("\"4\u00183\t1!4\u001c8\u00105\u0015");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            default:
                throw new IllegalStateException(format);
            case 1:
            case 2:
            case 3:
                throw new IllegalArgumentException(format);
        }
    }

    @RequiresEdt
    boolean isAvailable(@NotNull Editor editor);

    void showNextInlaySet(@NotNull Editor editor);

    boolean hasPreviousInlaySet(@NotNull Editor editor);

    boolean hasNextInlaySet(@NotNull Editor editor);

    @RequiresEdt
    void editorChanged(@NotNull Editor editor, int i, @NotNull CodeTipRequestType codeTipRequestType, boolean z);

    @RequiresEdt
    boolean acceptTip(@NotNull Editor editor);

    void showPreviousInlaySet(@NotNull Editor editor);

    @RequiresEdt
    @NotNull
    List<TipRenderer> getInlays(@NotNull Editor editor, int i, int i2);

    @RequiresEdt
    void cancelTipRequests(@NotNull Editor editor);

    @RequiresEdt
    boolean acceptTipForLine(@NotNull Editor editor);

    @RequiresEdt
    boolean acceptWordTip(@NotNull Editor editor);

    @RequiresEdt
    void disposeTips(@NotNull Editor editor, @NotNull OperateActionEnum operateActionEnum);

    @RequiresEdt
    void acceptTip(@NotNull Project project, @NotNull Editor editor, @NotNull EditorRequestService editorRequestService, @NotNull CodeInlayList codeInlayList);

    @RequiresEdt
    int countTipInlays(@NotNull Editor editor, @NotNull TextRange textRange, boolean z, boolean z2, boolean z3, boolean z4);

    @RequiresEdt
    boolean hasCacheData(@NotNull Editor editor, char c);

    @RequiresEdt
    void acceptWordTip(@NotNull Project project, @NotNull Editor editor, @NotNull EditorRequestService editorRequestService, @NotNull CodeInlayList codeInlayList);

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    default void editorChanged(@NotNull Editor editor, @NotNull CodeTipRequestType requestType, boolean z) {
        if (editor == null) {
            m262enum(2);
        }
        if (requestType == null) {
            m262enum(3);
        }
        if (!StringUtils.isBlank(PluginStartupActivity.getApiKey())) {
            if (!AICodeSettingsState.getInstance().enableCodeComplete) {
                LOG.info(OpenTelemetryUtil.H("B\u0007 4j9m-u1m/}wT\u0019v`p8z#d)v/k."));
                return;
            } else {
                ApplicationManager.getApplication().runReadAction(() -> {
                    try {
                        editorChanged(editor, editor.getCaretModel().getOffset(), requestType, z);
                    } catch (Throwable th) {
                        LOG.info("editorChanged error" + th.getMessage(), th);
                    }
                });
                return;
            }
        }
        LOG.info(RequestTimeoutException.H("?\u001f|\u0011.\u00073\u0015g\u000f\u0016?;\u00076\u001b \u001c=\u001dp\u00152\u0014\u000f)7Q$\t.\u00120\u0018\"\u001e?\u001f"));
    }

    @NotNull
    static EditorManagerService getInstance() {
        EditorManagerService editorManagerService = (EditorManagerService) ApplicationManager.getApplication().getService(EditorManagerService.class);
        if (editorManagerService == null) {
            m262enum(0);
        }
        return editorManagerService;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @RequiresEdt
    default boolean hasTipInlays(@NotNull Editor a) {
        if (a == null) {
            m262enum(1);
        }
        if (isAvailable(a) && countTipInlays(a, TextRange.from(0, a.getDocument().getTextLength()), true, true, true, true) > 0) {
            return true;
        }
        return false;
    }
}
