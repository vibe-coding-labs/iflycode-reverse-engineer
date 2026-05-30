package com.aicode.service.editor;

import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.aicode.language.AICodeLanguageInfo;
import com.aicode.util.Maps;
import com.intellij.ide.ui.AntialiasingType;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.editor.impl.FontInfo;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.util.Key;
import com.intellij.ui.paint.EffectPainter2D;
import com.intellij.util.ui.GraphicsUtil;
import com.intellij.util.ui.UIUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: oc */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/editor/InlayRendering.class */
public final class InlayRendering {

    /* renamed from: byte, reason: not valid java name */
    private static final Key<Map<Font, FontMetrics>> f590byte = Key.create(AICodeLanguageInfo.H("\u0019 ��\u001d \u0010c\u0019rN=\u001741,\u001c08&\u0006,\u0006wV"));

    /* renamed from: enum, reason: not valid java name */
    @Nullable
    private static final Method f591enum;

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m289enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            default:
                H = AICodeLanguageInfo.H("\u0019\u001b;\u0018.\u0017'\fo\u0018kGo>��\u00107<+\u0003)T(\bvT\u000e70\u00104Wl_ EX&)^a\u0006`Z<^5\u001c0\u0006d\u001b\"\bo\u001c!U1\u001b+\u001a");
                i = a;
                break;
            case 17:
                do {
                } while (0 != 0);
                H = InlineChatStatusServiceKt.H("\r&#\u001d\u000f\u00110\u0015g\u000f?\u000bnL\u0005dc\u0010jD:L<\u0001\t+m\u0006)\u0017l\u001b(\u001c/\r/D(\u0016#\u0006");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            default:
                i2 = 3;
                break;
            case 17:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            case 3:
            case 13:
            case 15:
            case 18:
            default:
                objArr[0] = AICodeLanguageInfo.H("!\u00116\u001a(\u0004");
                i3 = a;
                break;
            case 1:
            case 16:
                do {
                } while (0 != 0);
                objArr[0] = InlineChatStatusServiceKt.H("2\u00067\u001e");
                i3 = a;
                break;
            case 2:
                objArr[0] = AICodeLanguageInfo.H("\b*\u0006096��\"\u0005");
                i3 = a;
                break;
            case 4:
                objArr[0] = InlineChatStatusServiceKt.H("\u001c.\n2\u0006!\u001e");
                i3 = a;
                break;
            case 5:
                objArr[0] = AICodeLanguageInfo.H("'\u001a#\b*\u0010096��\"\u0005");
                i3 = a;
                break;
            case 6:
            case 9:
            case 11:
                objArr[0] = InlineChatStatusServiceKt.H("\r");
                i3 = a;
                break;
            case 7:
                objArr[0] = AICodeLanguageInfo.H("6\u00108\u0007(\u0018");
                i3 = a;
                break;
            case 8:
            case 10:
                objArr[0] = InlineChatStatusServiceKt.H(",\u001c.\r(\u00063\u0017*\u0019");
                i3 = a;
                break;
            case 12:
                objArr[0] = AICodeLanguageInfo.H("7\u0017<\u0001\f\b;\f-\u0017*\u001a\"\u0005");
                i3 = a;
                break;
            case 14:
                objArr[0] = InlineChatStatusServiceKt.H(" \f!\u001e");
                i3 = a;
                break;
            case 17:
                objArr[0] = AICodeLanguageInfo.H(";\u0006i\u001a\u0002;'\u001a\"\u0012d\t6\u0010\u000e ,\u001bk\u0010*\u0016;\u0011*F\n\u001c(\u00144.*\u0010 \u0010-\u0007)\u0011");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            default:
                objArr[1] = InlineChatStatusServiceKt.H("/\u0006,K=\u0010$\r>\u001a)P\u000460\n'\u0004f\t5\u001d\u000e0?G\u000f\r \b4:?\u0011%\u00014\n!\r");
                i4 = a;
                break;
            case 17:
                do {
                } while (0 != 0);
                objArr[1] = AICodeLanguageInfo.H("\u0019!\u0001\u0019\u0001)\u0002");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            case 1:
            case 2:
            default:
                objArr[2] = InlineChatStatusServiceKt.H("%\u0002 \n8\u0004;\u000b$3/\u0007;\u0002");
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                do {
                } while (0 != 0);
                objArr[2] = AICodeLanguageInfo.H("\u001b&\u001c \u0010?? \u001a!73\u0001$\u001d");
                break;
            case 9:
            case 10:
                objArr[2] = InlineChatStatusServiceKt.H("?\r(\u0007)\u001b\u000f\t9\u0014&\u0016)\u0016!\u000e");
                break;
            case 11:
            case 12:
                objArr[2] = AICodeLanguageInfo.H("��!\u001b)\u0019=;\"\u0013:\r3\u0005");
                break;
            case 13:
            case 14:
                objArr[2] = InlineChatStatusServiceKt.H("\u000f\"\u0006.2$\u00104\n,\u0019");
                break;
            case 15:
            case 16:
                objArr[2] = AICodeLanguageInfo.H("\u0019!\u0001\u0019\u0001)\u0002");
                break;
            case 17:
                break;
            case 18:
                objArr[2] = InlineChatStatusServiceKt.H("<\u0010/\u0010\u0015\n5\u000f");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            default:
                throw new IllegalArgumentException(format);
            case 17:
                throw new IllegalStateException(format);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void renderCodeBlock(@NotNull Editor editor, @NotNull String content, @NotNull List<String> list, @NotNull Graphics g, @NotNull Rectangle2D region, @NotNull TextAttributes attributes) {
        if (editor == null) {
            m289enum(3);
        }
        if (content == null) {
            m289enum(4);
        }
        if (list == null) {
            m289enum(5);
        }
        if (g == null) {
            m289enum(6);
        }
        if (region == null) {
            m289enum(7);
        }
        if (attributes == null) {
            m289enum(8);
        }
        if (content.isEmpty() || list.isEmpty()) {
            return;
        }
        Rectangle clipBounds = g.getClipBounds();
        Graphics2D create = g.create();
        GraphicsUtil.setupAAPainting(create);
        Font Wc = Wc(editor, content);
        create.setFont(Wc);
        FontMetrics qc = qc(editor, Wc);
        double lineHeight = editor.getLineHeight();
        double ceil = Math.ceil(Wc.createGlyphVector(qc.getFontRenderContext(), InlineChatStatusServiceKt.H("\"#\b")).getVisualBounds().getHeight());
        double d = (lineHeight - ceil) / 2.0d;
        double x = region.getX();
        double y = region.getY() + ceil + d;
        int i = 0;
        create.setClip((clipBounds == null || clipBounds.equals(region)) ? region : region.createIntersection(clipBounds));
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String next = it.next();
            PB(create, attributes, x, region.getY() + i, region.getWidth(), lineHeight);
            create.setColor(attributes.getForegroundColor());
            create.drawString(next, (float) x, (float) (y + i));
            if (editor instanceof EditorImpl) {
                ic(create, x, y + i, qc.stringWidth(next), ((EditorImpl) editor).getCharHeight(), ((EditorImpl) editor).getDescent(), attributes, Wc);
            }
            i = (int) (i + lineHeight);
            it = it;
        }
        create.dispose();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static float KC(@NotNull Editor editor) {
        if (editor == null) {
            m289enum(18);
        }
        EditorColorsScheme colorsScheme = editor.getColorsScheme();
        if (f591enum != null) {
            try {
                return ((Float) f591enum.invoke(colorsScheme, new Object[0])).floatValue();
            } catch (IllegalAccessException | InvocationTargetException unused) {
            }
        }
        return colorsScheme.getEditorFontSize();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static int IA(@NotNull Editor editor, @NotNull String text, @NotNull List<String> list) {
        if (editor == null) {
            m289enum(0);
        }
        if (text == null) {
            m289enum(1);
        }
        if (list == null) {
            m289enum(2);
        }
        FontMetrics qc = qc(editor, Wc(editor, text));
        int i = 0;
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            i = Math.max(i, qc.stringWidth(it.next()));
            it = it;
        }
        return i;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static void ic(@NotNull Graphics2D g, double x, double baseline, double width, int charHeight, int a, @NotNull TextAttributes a2, @Nullable Font a3) {
        EffectType effectType;
        if (g == null) {
            m289enum(11);
        }
        if (a2 == null) {
            m289enum(12);
        }
        Color effectColor = a2.getEffectColor();
        if (effectColor == null || (effectType = a2.getEffectType()) == null) {
            return;
        }
        g.setColor(effectColor);
        switch (G.f592enum[effectType.ordinal()]) {
            case 1:
                EffectPainter2D effectPainter2D = EffectPainter2D.LINE_UNDERSCORE;
                do {
                } while (0 != 0);
                effectPainter2D.paint(g, x, baseline, width, a, a3);
                return;
            case 2:
                EffectPainter2D.BOLD_LINE_UNDERSCORE.paint(g, x, baseline, width, a, a3);
                return;
            case 3:
                EffectPainter2D.STRIKE_THROUGH.paint(g, x, baseline, width, charHeight, a3);
                return;
            case 4:
                EffectPainter2D.WAVE_UNDERSCORE.paint(g, x, baseline, width, a, a3);
                return;
            case 5:
                EffectPainter2D.BOLD_DOTTED_UNDERSCORE.paint(g, x, baseline, width, a, a3);
                return;
            default:
                return;
        }
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: SSATransform
        java.lang.IndexOutOfBoundsException: bitIndex < 0: -1
        	at java.base/java.util.BitSet.get(BitSet.java:626)
        	at jadx.core.dex.visitors.ssa.LiveVarAnalysis.fillBasicBlockInfo(LiveVarAnalysis.java:65)
        	at jadx.core.dex.visitors.ssa.LiveVarAnalysis.runAnalysis(LiveVarAnalysis.java:36)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:58)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:44)
        */
    private static void PB(@org.jetbrains.annotations.NotNull java.awt.Graphics2D r10, @org.jetbrains.annotations.NotNull com.intellij.openapi.editor.markup.TextAttributes r11, double r12, double r14, double r16, double r18) {
        /*  JADX ERROR: IndexOutOfBoundsException in pass: SSATransform
            java.lang.IndexOutOfBoundsException: bitIndex < 0: -1
            	at java.base/java.util.BitSet.get(BitSet.java:626)
            	at jadx.core.dex.visitors.ssa.LiveVarAnalysis.fillBasicBlockInfo(LiveVarAnalysis.java:65)
            	at jadx.core.dex.visitors.ssa.LiveVarAnalysis.runAnalysis(LiveVarAnalysis.java:36)
            	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:58)
            */
        /*  JADX ERROR: Method code generation error
            java.lang.NullPointerException: Cannot invoke "jadx.core.dex.nodes.IContainer.get(jadx.api.plugins.input.data.attributes.IJadxAttrType)" because "cont" is null
            	at jadx.core.codegen.RegionGen.declareVars(RegionGen.java:70)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:65)
            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:281)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
            */
        /*
            r0 = r10
            r1 = r11
            r10 = r1
            r11 = r0
            r0 = r11
            if (r0 != 0) goto L10
            r0 = 9
            r1 = 1
            r2 = r1
            m289enum(r0)
        L10:
            r0 = r10
            if (r0 != 0) goto L1c
            r0 = 10
            r1 = 1
            r2 = r1
            m289enum(r0)
        L1c:
            r0 = r10
            java.awt.Color r0 = r0.getBackgroundColor()
            r1 = r0
            r10 = r1
            if (r0 == 0) goto L41
            r0 = r12
            r1 = r11
            r2 = r1; r1 = r0; r0 = r-1; r-1 = r2; 
            r3 = r10
            r2.setColor(r3)
            int r1 = (int) r1
            r2 = r14
            int r2 = (int) r2
            r3 = r16
            int r3 = (int) r3
            r4 = r18
            int r4 = (int) r4
            r5 = 1
            r6 = r5
            r5 = 1
            r6 = r5
            r7 = r6; r6 = r5; r5 = r7; 
            r8 = r7
            r0.fillRoundRect(r1, r2, r3, r4, r5, r6)
        L41:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aicode.service.editor.InlayRendering.PB(java.awt.Graphics2D, com.intellij.openapi.editor.markup.TextAttributes, double, double, double, double):void");
    }

    private static FontMetrics qc(@NotNull Editor editor, @NotNull Font font) {
        if (editor == null) {
            m289enum(13);
        }
        if (font == null) {
            m289enum(14);
        }
        FontRenderContext fontRenderContext = FontInfo.getFontRenderContext(editor.getContentComponent());
        FontRenderContext fontRenderContext2 = new FontRenderContext(fontRenderContext.getTransform(), AntialiasingType.getKeyForCurrentScope(false), fontRenderContext.getFractionalMetricsHint());
        Map map = (Map) f590byte.get(editor, Collections.emptyMap());
        FontMetrics fontMetrics = (FontMetrics) map.get(font);
        FontMetrics fontMetrics2 = fontMetrics;
        if (fontMetrics == null || !fontRenderContext2.equals(fontMetrics2.getFontRenderContext())) {
            fontMetrics2 = FontInfo.getFontMetrics(font, fontRenderContext2);
            f590byte.set(editor, Maps.merge(map, Map.of(font, fontMetrics2)));
        }
        return fontMetrics2;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static {
        Method method;
        Method method2 = null;
        if (ApplicationInfo.getInstance().getBuild().getBaselineVersion() >= 221) {
            try {
                method2 = EditorColorsScheme.class.getMethod(InlineChatStatusServiceKt.H("\u0013\u001f+\b\f/\u0017#\u001b\u000b\u00074\u000b\u0012\r<\u0006}."), new Class[0]);
                method = method2;
            } catch (NoSuchMethodException unused) {
            }
            f591enum = method;
        }
        method = method2;
        f591enum = method;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: oc */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/editor/InlayRendering$G.class */
    public static /* synthetic */ class G {

        /* renamed from: enum, reason: not valid java name */
        public static final /* synthetic */ int[] f592enum = new int[EffectType.values().length];

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        static {
            try {
                f592enum[EffectType.LINE_UNDERSCORE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f592enum[EffectType.BOLD_LINE_UNDERSCORE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f592enum[EffectType.STRIKEOUT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f592enum[EffectType.WAVE_UNDERSCORE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f592enum[EffectType.BOLD_DOTTED_LINE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    @NotNull
    private static Font Wc(@NotNull Editor editor, @NotNull String text) {
        if (editor == null) {
            m289enum(15);
        }
        if (text == null) {
            m289enum(16);
        }
        Font deriveFont = UIUtil.getFontWithFallbackIfNeeded(editor.getColorsScheme().getFont(EditorFontType.PLAIN).deriveFont(2), text).deriveFont(KC(editor));
        if (deriveFont == null) {
            m289enum(17);
        }
        return deriveFont;
    }
}
