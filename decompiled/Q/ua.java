package Q;

import com.aicode.agent.service.GitReviewService;
import com.aicode.enums.OperateActionEnum;
import com.aicode.service.EditorManagerService;
import com.aicode.util.PropertyUtils;
import com.intellij.codeInsight.lookup.LookupManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: yl */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:Q/ua.class */
public class ua extends EditorActionHandler {

    /* renamed from: enum, reason: not valid java name */
    @Nullable
    private final EditorActionHandler f3enum;

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m3enum(int a) {
        String H = PropertyUtils.H("\ri0u\"}\u001dP,=8rcT\t\u007f8U\"l#8\u0003E~::e7q50k>$'ow\u0015\u0004)(y%04*e?own lSFi{9u/x");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            case 2:
            case 3:
            case 4:
            default:
                objArr[0] = GitReviewService.H("/\u00158\u001e&��");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = PropertyUtils.H("86r&`");
                break;
        }
        objArr[1] = GitReviewService.H("2\u0005(Q \u0013)\u001e5\u000ff\u0013\u0016:c^?E\u0001\u00172\n%\u00024#'\u001e\u00147yt5\u00031\u001132+\u001f5\u0006,��");
        switch (a) {
            case 0:
            case 1:
            default:
                objArr[2] = PropertyUtils.H("}4U\"z5l*|5K~\u00186r&`");
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[2] = GitReviewService.H("\u00106oR$\u001e 7/9%\u001c<\u000b'\u0016");
                break;
            case 3:
                objArr[2] = PropertyUtils.H("|\u001cat>4u7q");
                break;
            case 4:
                objArr[2] = GitReviewService.H("\u001b\u0006\u000bnX%\u00057-4\n:\u001e#\u001e,\u0016");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public boolean executeInCommand(@NotNull Editor editor, DataContext a) {
        if (editor == null) {
            m3enum(2);
        }
        return Objects.nonNull(this.f3enum) && this.f3enum.executeInCommand(editor, a);
    }

    public void doExecute(@NotNull Editor editor, @Nullable Caret caret, DataContext a) {
        if (editor == null) {
            m3enum(3);
        }
        if (Yf(editor)) {
            EditorManagerService.getInstance().disposeTips(editor, OperateActionEnum.EscReject);
        }
        if (!Objects.nonNull(this.f3enum) || !this.f3enum.isEnabled(editor, caret, a)) {
            return;
        }
        this.f3enum.execute(editor, caret, a);
    }

    public ua(@Nullable EditorActionHandler a) {
        this.f3enum = a;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public boolean isEnabledForCaret(@NotNull Editor editor, @NotNull Caret caret, DataContext a) {
        if (editor == null) {
            m3enum(0);
        }
        if (caret == null) {
            m3enum(1);
        }
        return Yf(editor) || (Objects.nonNull(this.f3enum) && this.f3enum.isEnabled(editor, caret, a));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static boolean Yf(@NotNull Editor editor) {
        if (editor == null) {
            m3enum(4);
        }
        EditorManagerService editorManagerService = EditorManagerService.getInstance();
        return editorManagerService.isAvailable(editor) && editorManagerService.hasTipInlays(editor) && LookupManager.getActiveLookup(editor) == null;
    }
}
