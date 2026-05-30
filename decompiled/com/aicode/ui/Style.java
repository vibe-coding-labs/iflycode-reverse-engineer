package com.aicode.ui;

import com.aicode.agent.service.CodeCompleteService;
import com.aicode.content.util.EditorUtils;
import com.aicode.content.util.OverlayUtils;
import com.aicode.content.util.file.FileExtensionLanguageDetails;
import com.aicode.service.editor.RequestResultList;
import com.aicode.util.AICodeStringUtil;
import com.aicode.util.JComponentKt;
import com.intellij.ui.Gray;
import com.intellij.ui.JBColor;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import org.jetbrains.annotations.NotNull;

/* compiled from: sb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/ui/Style.class */
public final class Style {
    public static final Style INSTANCE = new Style();

    /* compiled from: sb */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/ui/Style$Colors.class */
    public static final class Colors {
        public static final Colors INSTANCE = new Colors();

        /* renamed from: enum, reason: not valid java name */
        private static final JBColor f653enum = new JBColor(5083390, 5083390);

        /* renamed from: float, reason: not valid java name */
        @NotNull
        private static final JBColor f651float = new JBColor(13290708, 5198166);

        /* renamed from: byte, reason: not valid java name */
        @NotNull
        private static final JBColor f652byte = JBColor.namedColor(CodeCompleteService.H("cjNvQuZt\u0004{\\oPdWaVhWCWq\u001d%"), new JBColor(Gray.xCD, Gray.x4D));

        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m376enum(int a) {
            throw new IllegalStateException(String.format(EditorUtils.H("Z\u000f\u007f?R2{ 6 8rn2~axu\u000e^t|g$l06#8x\u0010\u0019y3j6rgo/l7"), CodeCompleteService.H("CjD#\t$lEGc\u000fpJ)moFvL(ahUsSw"), EditorUtils.H("#y3F\bE\u0002")));
        }

        /* compiled from: sb */
        /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/ui/Style$Colors$InlineChat.class */
        public static final class InlineChat {

            @NotNull
            public static final InlineChat INSTANCE = new InlineChat();

            /* renamed from: enum, reason: not valid java name */
            @NotNull
            private static final JBColor f655enum = new JBColor(16382715, JBColor.namedColor(FileExtensionLanguageDetails.H("\\\u007fg~b2gv@Zohnfzb"), new JBColor(Gray.x99, Gray.x78)).getRGB());

            /* renamed from: byte, reason: not valid java name */
            private static final JBColor f654byte = new JBColor(Colors.INSTANCE.getSEPARATOR_COLOR().darker(), Colors.INSTANCE.getSEPARATOR_COLOR());

            /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
            /* renamed from: enum, reason: not valid java name */
            private static /* synthetic */ void m378enum(int a) {
                String H = AICodeStringUtil.H("qtK[cS��\u000b+mBXL@C\f\u001fB\u0015\u0015\u001fGl\u007fUY\u0004AN^\u0006_HR~rN\u000bGWP[");
                Object[] objArr = new Object[2];
                objArr[0] = FileExtensionLanguageDetails.H("t+;\fPf~czj2gi<R0/E^*_crfi}8LyOXf\u007fB{ur");
                switch (a) {
                    case 0:
                    default:
                        objArr[1] = AICodeStringUtil.H("MCYoGhkGYFWRS");
                        break;
                    case 1:
                        do {
                        } while (0 != 0);
                        objArr[1] = FileExtensionLanguageDetails.H("pFEJuswqt");
                        break;
                }
                throw new IllegalStateException(String.format(H, objArr));
            }

            @NotNull
            public JBColor getBorder() {
                JBColor jBColor = f654byte;
                if (jBColor == null) {
                    m378enum(1);
                }
                return jBColor;
            }

            private InlineChat() {
            }

            @NotNull
            public JBColor getBackground() {
                JBColor jBColor = f655enum;
                if (jBColor == null) {
                    m378enum(0);
                }
                return jBColor;
            }
        }

        public JBColor getSEPARATOR_COLOR() {
            return f652byte;
        }

        @NotNull
        public JBColor getGREY() {
            JBColor jBColor = f651float;
            if (jBColor == null) {
                m376enum(0);
            }
            return jBColor;
        }

        public JBColor getBLUE() {
            return f653enum;
        }

        private Colors() {
        }
    }

    private Style() {
        throw new AssertionError(JComponentKt.H("1*CQP\u00065=^<\u001d3\u0012(\u0001sS6C$\u000b7C!\u0011g\u0015"));
    }

    /* compiled from: sb */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/ui/Style$Borders.class */
    public static final class Borders {

        @NotNull
        public static final Borders INSTANCE = new Borders();

        /* renamed from: byte, reason: not valid java name */
        @NotNull
        private static final Border f649byte = BorderFactory.createEmptyBorder(16, 12, 12, 12);

        /* renamed from: enum, reason: not valid java name */
        @NotNull
        private static final Border f650enum = BorderFactory.createEmptyBorder(0, 0, 16, 0);

        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m374enum(int a) {
            throw new IllegalStateException(String.format(RequestResultList.H("Wkt]^W}Oz\u0005dGu@>H\u0002f.\u0017~\u001fu_bWp\fYp;[c@eP\u007f\u0003hAkY"), OverlayUtils.H("H\u0014;s\u00108\u001f+\r(OyHE\u00143\u00136\u0012h#\"\u0012>\u0012)\u0005"), RequestResultList.H("jZlgtP#\u0003QaSLgPuPSLtPbG")));
        }

        public Border getTopMessageBorder() {
            return f650enum;
        }

        private Borders() {
        }

        @NotNull
        public Border getMessageHeaderBorder() {
            Border border = f649byte;
            if (border == null) {
                m374enum(0);
            }
            return border;
        }
    }
}
