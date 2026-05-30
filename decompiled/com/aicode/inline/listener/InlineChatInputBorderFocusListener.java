package com.aicode.inline.listener;

import com.aicode.agent.service.GitReviewService;
import com.aicode.content.util.EditorUtils;
import com.aicode.diff.GenericUtils;
import com.aicode.ui.RoundLineBorder;
import com.aicode.ui.Style;
import com.aicode.util.IndentLineUtil;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

/* compiled from: lg */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/listener/InlineChatInputBorderFocusListener.class */
public class InlineChatInputBorderFocusListener implements FocusListener {

    /* renamed from: try, reason: not valid java name */
    @NotNull
    private final Function1<FocusEvent, Unit> f420try;

    /* renamed from: byte, reason: not valid java name */
    @NotNull
    private final Function1<FocusEvent, Unit> f422byte;

    /* renamed from: enum, reason: not valid java name */
    @NotNull
    private final JComponent f423enum;

    @NotNull
    public static final Companion Companion = new Companion();

    /* renamed from: float, reason: not valid java name */
    @NotNull
    private static final Border f421float = new RoundLineBorder(Style.Colors.INSTANCE.getBLUE(), 2, true);

    /* renamed from: final, reason: not valid java name */
    @NotNull
    private static final Border f419final = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1), new RoundLineBorder(Style.Colors.INSTANCE.getGREY(), 1, true));

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m211enum(int a) {
        String H = GitReviewService.H("\r\u0005\"\u000b=\u000e\u0003\"j\u0017;\u001dq*\u001e\u0004\u0012\u0013>\u001c:M0\u001a3\u001b;\b\u0016<9P`Y\"Mw\u0003\u000esh\u0005yI#K,\u000f9\u0005a\u00149\u0019a\u0018/Q?\u001f%\u001e");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = EditorUtils.H("?y m)x(o.");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = GitReviewService.H("\r.\u0019?\u0002\u0006\u001b?\u0003$\u001e\u000b\u0012%\u0003&\u001c");
                break;
            case 2:
                objArr[0] = EditorUtils.H("\u0002u\"u(K3e9\\%b$n4");
                break;
        }
        objArr[1] = GitReviewService.H("\u0014*\u0013\u007f\n\u00045%\u00151@8\u0004<\u0002\b8d\u001c?\u001e4\u001e/\u001f$B+7'\u0019)\u0019\u0012\u00026\u0018!==\u0003#.?\u0019%\u001f87.\u0019#\u001e\r\u00139\u00054\u0004,��");
        objArr[2] = EditorUtils.H("!/x$ud");
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* compiled from: lg */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/listener/InlineChatInputBorderFocusListener$Companion.class */
    public static final class Companion {
        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m212enum(int a) {
            String H = GenericUtils.H("\u001f\u0019*9\f??7x=6/,#v:P\u000ewt(s+;$+x>0#y#=$&)6p->7?");
            Object[] objArr = new Object[2];
            objArr[0] = IndentLineUtil.H("G\u0001\\|m\u001dH\u0005Q\n\u001f\u0007_4n\u001bOG[\u0017R\u000bE\u0006R.,<D\u0015O\u0001U*^7}:B\u0019C\u001as\u0010R\u0010N\rf\u0007TK\u0012\u0015o\u0006^\u0012F\u000fG_g\u001bF\u0003M\u001bC\u001bE");
            switch (a) {
                case 0:
                default:
                    objArr[1] = GenericUtils.H("82-\u001773&(\u001a?1/>!");
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    objArr[1] = IndentLineUtil.H(">c\u0001\u007f\u0019N\u0005V\u000eW\u0011O1C\u0007N\u0011Y");
                    break;
            }
            throw new IllegalStateException(String.format(H, objArr));
        }

        @NotNull
        public Border getUnfocusedBorder() {
            Border border = InlineChatInputBorderFocusListener.f419final;
            if (border == null) {
                m212enum(1);
            }
            return border;
        }

        private Companion() {
        }

        @NotNull
        public Border getFocusBorder() {
            Border border = InlineChatInputBorderFocusListener.f421float;
            if (border == null) {
                m212enum(0);
            }
            return border;
        }
    }

    public void focusGained(FocusEvent a) {
        this.f420try.invoke(a);
    }

    public void focusLost(FocusEvent a) {
        this.f422byte.invoke(a);
    }

    public InlineChatInputBorderFocusListener(@NotNull JComponent component, @NotNull Function1<FocusEvent, Unit> function1, @NotNull Function1<FocusEvent, Unit> function12) {
        if (component == null) {
            m211enum(0);
        }
        if (function1 == null) {
            m211enum(1);
        }
        if (function12 == null) {
            m211enum(2);
        }
        this.f423enum = component;
        this.f420try = function1;
        this.f422byte = function12;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public InlineChatInputBorderFocusListener(JComponent a, Function1 a2, Function1 a3, int a4) {
        this(a, r2, (r3 & 4) != 0 ? focusEvent -> {
            a.setBorder(Companion.getUnfocusedBorder());
            return Unit.INSTANCE;
        } : a3);
        Function1 function1;
        int i;
        if ((a4 & 2) != 0) {
            function1 = focusEvent2 -> {
                a.setBorder(Companion.getFocusBorder());
                return Unit.INSTANCE;
            };
            i = a4;
        } else {
            function1 = a2;
            i = a4;
        }
    }
}
