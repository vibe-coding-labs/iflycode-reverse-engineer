package com.aicode.complete;

import com.aicode.agent.service.GitReviewService;
import com.aicode.inline.ide.IdeAction;
import com.aicode.util.Maps;
import com.aicode.util.PropertyUtils;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.codeInsight.hint.HintManagerImpl;
import com.intellij.codeInsight.hint.HintUtil;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.ui.LightweightHint;
import com.intellij.ui.SimpleColoredComponent;
import com.intellij.ui.SimpleColoredText;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.util.ui.JBUI;
import java.awt.BorderLayout;
import java.awt.Point;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.jetbrains.annotations.NotNull;

/* compiled from: ie */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/complete/InlayCompletionHintFactory.class */
public class InlayCompletionHintFactory {

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f201enum = Logger.getInstance(InlayCompletionHintFactory.class);

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m111enum(int a) {
        String H = PropertyUtils.H("\u000fk e;d)d,=\u0004Nl[��v7Z.`\r\u0016={=y?`:|50q$8;mu*;ONe9?;;t\u0017Gcz.bvc(:>r7`");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            case 1:
            default:
                objArr[0] = GitReviewService.H("/\u00158\u001e&��");
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[0] = PropertyUtils.H("w4\u007f");
                break;
        }
        objArr[1] = GitReviewService.H("*\u001d9@)\u001a\"\u00154\u000eb\u0015$\u001d:\u001d\t#(Y\u0003\u001f<\n\u001b\u001a*\u00137\u00105\u001f\"\u001f8%4\b5<+\u0012%\u0005;\u000b");
        switch (a) {
            case 0:
            default:
                objArr[2] = PropertyUtils.H("r\f\\4\\(x\"@9Y1u>x");
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                objArr[2] = GitReviewService.H("4\u0014?\u001c\u0003\u00198\u0019\u001c\u0012\u0011\u00159\u0018%\u0003&\u001c");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void showHintAtCaret(@NotNull Editor editor) {
        if (editor == null) {
            m111enum(0);
        }
        try {
            f201enum.info(GitReviewService.H("(\u00026\t\u00182\f\u0018.\"8\u0004\u0012\u00169\u0005\u0013|5\u0011%\u00045\u0013?\u0005"));
            showEditorHint(new LightweightHint(Df()), editor, (short) 1, 42, 0, false);
        } catch (Throwable th) {
            f201enum.warn(PropertyUtils.H("\u000b{&t7anm(0%i$kms\"w\u0003S.<'~/!\u0006Z-p(x1rmr9i/\u007f"), th);
        }
    }

    private static JComponent Df() {
        SimpleColoredComponent createInformationComponent = HintUtil.createInformationComponent();
        createInformationComponent.setIconOnTheRight(true);
        new SimpleColoredText(ve(), SimpleTextAttributes.REGULAR_ATTRIBUTES).appendToComponent(createInformationComponent);
        return new InlineKeybindingHintComponent(createInformationComponent);
    }

    private static String ve() {
        String H = PropertyUtils.H("@!np,{W");
        String H2 = GitReviewService.H("\u0017\u0001)FyI\u001cZ");
        return String.format(GitReviewService.H("揵厼X|6^偞遵纃柷剌挒lH.F扄Id\t"), PropertyUtils.H("S:n"), H, H2);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void showHintAtPosition(@NotNull Editor editor, @NotNull Point pos) {
        if (editor == null) {
            m111enum(1);
        }
        if (pos == null) {
            m111enum(2);
        }
        try {
            f201enum.info("createAndShowHint position:" + pos);
            HintManagerImpl.getInstanceImpl().showEditorHint(new LightweightHint(Df()), editor, pos, 42, 0, false);
        } catch (Throwable th) {
            f201enum.warn(GitReviewService.H("\u0010\f4\n2\bl\u0003$P ��\u0003 m\u001f$\u001d9\u0005��~;\u000e\ro)\u00199\b$\u00187\u0018m\u001e\"\u001e>\u0002"), th);
        }
    }

    /* compiled from: ie */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/complete/InlayCompletionHintFactory$InlineKeybindingHintComponent.class */
    public static class InlineKeybindingHintComponent extends JPanel {
        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m112enum(int a) {
            throw new IllegalArgumentException(String.format(IdeAction.H("2JI\u0010.m\n[FK\fZDo!K5D\u001aH\u001f\u0018\u0018B\u0011I\tJ\u001a@\u0011\bC\n\u001e\u0001bf\u0005\bA\\P\u0010:\"\b[\u0014XZ_\nZDM\n\u0004\nZ\u0012Y"), Maps.H("*\u000b5\u001f<\u001a-\u0002$"), IdeAction.H("\u001dZ!(\nI\nM\u001aPJM\u0017^\u0003TK\u0011&'-A\nL\u001ak\u000bB\u001fH$~\u0006K\u001dp\u0001M\u0017n\u0005L\u001aJ\u0011Q@f\u0003J+g\u0006c\u0001V\u001c\\'f\f@��d\u0013_\u0011m\u000bB\u001fK\nJ\u0010A"), Maps.H("S:\u001a!\u0018n")));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public InlineKeybindingHintComponent(@NotNull SimpleColoredComponent a) {
            super(new BorderLayout());
            if (a == null) {
                m112enum(0);
            }
            setBorder(JBUI.Borders.empty());
            add(a, Maps.H("+1\u001b=Zq"));
            setOpaque(true);
            setBackground(a.getBackground());
            revalidate();
            repaint();
        }
    }

    public static void showEditorHint(LightweightHint hint, Editor editor, @HintManager.PositionFlags short s, @HintManager.HideFlags int flags, int timeout, boolean z) {
        Point hintPosition = HintManagerImpl.getHintPosition(hint, editor, editor.getCaretModel().getLogicalPosition(), s);
        HintManagerImpl.getInstanceImpl().showEditorHint(hint, editor, hintPosition, flags, timeout, z, HintManagerImpl.createHintHint(editor, hintPosition, hint, s));
    }
}
