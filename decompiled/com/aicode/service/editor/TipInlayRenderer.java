package com.aicode.service.editor;

import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.enums.CodeTipType;
import com.aicode.service.EditorRequestService;
import com.aicode.service.TipRenderer;
import com.aicode.settings.AICodeRequestSettings;
import com.aicode.util.StringUtils;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.Inlay;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.JBColor;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: dc */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/editor/TipInlayRenderer.class */
public class TipInlayRenderer implements TipRenderer {

    /* renamed from: if, reason: not valid java name */
    @Nullable
    private Inlay<TipRenderer> f605if;

    /* renamed from: case, reason: not valid java name */
    private int f606case;

    /* renamed from: final, reason: not valid java name */
    @NotNull
    private final String f607final;

    /* renamed from: try, reason: not valid java name */
    private int f608try;

    /* renamed from: float, reason: not valid java name */
    @NotNull
    private final CodeTipType f609float;

    /* renamed from: byte, reason: not valid java name */
    @NotNull
    private final List<String> f610byte;

    /* renamed from: enum, reason: not valid java name */
    @NotNull
    private final TextAttributes f611enum;

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m295enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 1:
            case 7:
            case 8:
            case 21:
            case 22:
            default:
                H = CancelRequestTip.H("jd?$#\u0018\u0018\u0018m \u000f\u001e��\u0007\u0001EX\u000exs\u0012A\u0007\u001f\u0013\u0014A\u000f\u0019\u0002A\u0013\u0013\u0002\u0014\u0013\u0004J\u001f\u0004\u0005\u0005");
                i = a;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                do {
                } while (0 != 0);
                H = OpenTelemetryUtil.H("S$c5~2y'(*m4$��\u0001dp\u000ep-dle0|+~2;nGQ/ibr\b\u0003ik(:.ak|^\u0002w4/%j5$\"vwj5\u007f;");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 7:
            case 8:
            case 21:
            case 22:
            default:
                i2 = 2;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                i2 = 3;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            case 1:
            case 7:
            case 8:
            case 21:
            case 22:
            default:
                objArr[0] = CancelRequestTip.H("IE=\u007f\f\u0004\u0017\u001b)(E\u0019\r\u001a\u0013\f\u001e\u0018y3\u0005\b\u001e\u0005\u0012O5\b\u0006?\u000f\r\u0017\u000f3\u0004\u0004\u000e\u0014\u0003\f\u001b");
                i3 = a;
                break;
            case 2:
            case 20:
                do {
                } while (0 != 0);
                objArr[0] = OpenTelemetryUtil.H("v3m4|%");
                i3 = a;
                break;
            case 3:
            case 17:
                objArr[0] = CancelRequestTip.H("\u0013\u000f\u001b\u0004\u0014\u001a\u001d");
                i3 = a;
                break;
            case 4:
                objArr[0] = OpenTelemetryUtil.H("p9c2");
                i3 = a;
                break;
            case 5:
            case 16:
                objArr[0] = CancelRequestTip.H("\u0006\u0018\u001f\f\u001a");
                i3 = a;
                break;
            case 6:
            case 9:
            case 10:
            case 11:
            case 12:
                objArr[0] = OpenTelemetryUtil.H(">j,r.");
                i3 = a;
                break;
            case 13:
                objArr[0] = CancelRequestTip.H("\u000e");
                i3 = a;
                break;
            case 14:
                objArr[0] = OpenTelemetryUtil.H("a2c)|9");
                i3 = a;
                break;
            case 15:
                objArr[0] = CancelRequestTip.H("\u000e#$\u0013\u000e\u001f\u0004\u0004\t\u000f\u0006\"\u0013\u0019\u00157\u0002\u0015\u0013\u0003\b\u0004\u0005\f\u001a");
                i3 = a;
                break;
            case 18:
            case 19:
                objArr[0] = OpenTelemetryUtil.H("p%k#");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            default:
                objArr[1] = CancelRequestTip.H("\u0006\u0004\u001e&\u0018\u001f\f\u001a");
                i4 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[1] = OpenTelemetryUtil.H("b$p\u0003|9p%}#");
                i4 = a;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                objArr[1] = CancelRequestTip.H("IE=\u007f\f\u0004\u0017\u001b)(E\u0019\r\u001a\u0013\f\u001e\u0018y3\u0005\b\u001e\u0005\u0012O5\b\u0006?\u000f\r\u0017\u000f3\u0004\u0004\u000e\u0014\u0003\f\u001b");
                i4 = a;
                break;
            case 7:
                objArr[1] = OpenTelemetryUtil.H("'v#P9c2");
                i4 = a;
                break;
            case 8:
                objArr[1] = CancelRequestTip.H("\u0006\u0013\u0002\"\u000e\u0018\u0002\u0004\u000f\u001e&\u0018\u001f\f\u001a");
                i4 = a;
                break;
            case 21:
            case 22:
                objArr[1] = OpenTelemetryUtil.H(";V\u0003P%w?D5p2z5q4v$");
                i4 = a;
                break;
        }
        switch (i4) {
            case 2:
            case 3:
            case 4:
            case 5:
                do {
                } while (0 != 0);
                objArr[2] = CancelRequestTip.H("V\u0003\u001f\u0018\u001dW");
                break;
            case 6:
                objArr[2] = OpenTelemetryUtil.H("w%g\u001ej,r.");
                break;
            case 9:
                objArr[2] = CancelRequestTip.H("\u0006\u000f\u001e#\u000f\u000f\u0015\u0013\u000e\u0015,\u0013\u0018\u0014&\u0018\u0005\u0004\u0001 \r");
                break;
            case 10:
                objArr[2] = OpenTelemetryUtil.H("{=_\u0014L%f,m5M.C>|%\u007f$");
                break;
            case 11:
                objArr[2] = CancelRequestTip.H("\u0003��\r\u0015!\b\u0005\u0002\u001e(\u000f:\u0003\t\u0014\u0005\u001a");
                break;
            case 12:
            case 13:
            case 14:
            case 15:
                objArr[2] = OpenTelemetryUtil.H("'e)}#");
                break;
            case 16:
            case 17:
                objArr[2] = CancelRequestTip.H("\u0012\u0005\u0011\r\u0017\u0015\u0004-\u0013\u0017\u0005\b\u0004\r%\u0010\u000b\u001a");
                break;
            case 18:
                objArr[2] = OpenTelemetryUtil.H("P\u0018q.{\u0007` `)}0P!q$");
                break;
            case 19:
                objArr[2] = CancelRequestTip.H("5\u000e\u0014\u0004\u001e,\u0005��\u0005\u001f\u0018\u00066\u001e\u001f\u0015\u00049\u001a\u0010\u0012\f\u001a");
                break;
            case 20:
                objArr[2] = OpenTelemetryUtil.H(";V\u0003P%w?D5p2z5q4v$");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            case 7:
            case 8:
            case 21:
            case 22:
            default:
                throw new IllegalStateException(format);
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                throw new IllegalArgumentException(format);
        }
    }

    @Override // com.aicode.service.TipRenderer
    @NotNull
    public List<String> getContentLines() {
        List<String> list = this.f610byte;
        if (list == null) {
            m295enum(8);
        }
        return list;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public int calcWidthInPixels(@NotNull Inlay a) {
        if (a == null) {
            m295enum(11);
        }
        if (this.f606case < 0) {
            int max = Math.max(1, InlayRendering.IA(a.getEditor(), this.f607final, this.f610byte));
            this.f606case = max;
            return max;
        }
        return this.f606case;
    }

    public String toString() {
        return "AICodeDefaultInlayRenderer(lines=" + getLines() + ", content=" + getContent() + ", type=" + getType() + ", textAttributes=" + this.f611enum + ", cachedWidth=" + this.f606case + ", cachedHeight=" + this.f608try + ")";
    }

    public TipInlayRenderer(@NotNull Editor editor, @NotNull EditorRequestService request, @NotNull CodeTipType type, @NotNull List<String> list) {
        if (editor == null) {
            m295enum(2);
        }
        if (request == null) {
            m295enum(3);
        }
        if (type == null) {
            m295enum(4);
        }
        if (list == null) {
            m295enum(5);
        }
        this.f606case = -1;
        this.f608try = -1;
        this.f610byte = replaceLeadingTabs(list, request);
        this.f609float = type;
        this.f607final = StringUtils.join(CancelRequestTip.H("0"), list);
        this.f611enum = AC(editor);
    }

    public void setCachedHeight(int a) {
        this.f608try = a;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static int EB(@NotNull CharSequence text, char c, int start, int end, boolean z) {
        int i;
        int i2;
        boolean z2;
        if (text == null) {
            m295enum(18);
        }
        boolean z3 = start <= end;
        int start2 = z3 ? Math.max(0, start) : Math.min(text.length(), start);
        int end2 = z3 ? Math.min(text.length(), end) : Math.max(0, end);
        int i3 = 0;
        int i4 = z3 ? start2 : start2 - 1;
        while (true) {
            int i5 = i4;
            if (z3 == (i5 < end2)) {
                if (text.charAt(i5) != c) {
                    if (text.charAt(i5) != ' ' && text.charAt(i5) != '\n' && z) {
                        return i3;
                    }
                    i = i5;
                } else {
                    i = i5;
                    i3++;
                }
                if (z3) {
                    i2 = 1;
                    z2 = true;
                } else {
                    i2 = -1;
                    z2 = true;
                }
                i4 = i + i2;
            } else {
                return i3;
            }
        }
    }

    @NotNull
    public String getContent() {
        String str = this.f607final;
        if (str == null) {
            m295enum(1);
        }
        return str;
    }

    @NotNull
    public List<String> getLines() {
        List<String> list = this.f610byte;
        if (list == null) {
            m295enum(0);
        }
        return list;
    }

    @Nullable
    @NonNls
    public String getContextMenuGroupId(@NotNull Inlay a) {
        if (a == null) {
            m295enum(9);
        }
        return OpenTelemetryUtil.H("\nD\no }rZ\u0019h!v\bj/p%k#I%}\"");
    }

    public void paint(@NotNull Inlay inlay, @NotNull Graphics g, @NotNull Rectangle region, @NotNull TextAttributes a) {
        if (inlay == null) {
            m295enum(12);
        }
        if (g == null) {
            m295enum(13);
        }
        if (region == null) {
            m295enum(14);
        }
        if (a == null) {
            m295enum(15);
        }
        Editor editor = inlay.getEditor();
        if (!editor.isDisposed()) {
            InlayRendering.renderCodeBlock(editor, this.f607final, this.f610byte, g, region, this.f611enum);
        }
    }

    @Override // com.aicode.service.TipRenderer
    @NotNull
    public CodeTipType getType() {
        CodeTipType codeTipType = this.f609float;
        if (codeTipType == null) {
            m295enum(7);
        }
        return codeTipType;
    }

    public void setCachedWidth(int a) {
        this.f606case = a;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @NotNull
    private static TextAttributes AC(@NotNull Editor editor) {
        Color color;
        if (editor == null) {
            m295enum(20);
        }
        Color color2 = AICodeRequestSettings.settings().inlayTextColor;
        EditorColorsScheme colorsScheme = editor.getColorsScheme();
        TextAttributes textAttributes = null;
        try {
            textAttributes = colorsScheme.getAttributes((TextAttributesKey) Class.forName(CancelRequestTip.H("\u0011\u001d��C\u000e\t\u0015\u0004FF\b\u000bN\u000f\u001d\b\u001e\u0011\u001b\u0002X\u0013NC$?\u001fC0\u0011+,\u001f\u0006\u001c$\u0004\u000b\u001a\b71\u0004)\u0003\r\b\f\b\u0006\u001e\u0002\u0004\u00135\u0019)*54")).getField(OpenTelemetryUtil.H("\u0005_\u0019i5P\u001fH\u0011T\u001bO\u0015g?K\u0015[\u0014G��G\u000bT\u0005K\u0015]\u0013")).get(null));
            color = color2;
        } catch (Exception e) {
            color = color2;
        }
        if (color == null && textAttributes != null && textAttributes.getForegroundColor() != null) {
            TextAttributes textAttributes2 = textAttributes;
            if (textAttributes2 == null) {
                m295enum(21);
            }
            return textAttributes2;
        }
        TextAttributes clone = textAttributes != null ? textAttributes.clone() : new TextAttributes();
        if (color2 != null) {
            clone.setForegroundColor(color2);
        }
        if (clone.getForegroundColor() == null) {
            clone.setForegroundColor(JBColor.GRAY);
        }
        if (clone == null) {
            m295enum(22);
        }
        return clone;
    }

    @Override // com.aicode.service.TipRenderer
    @Nullable
    public Inlay<TipRenderer> getInlay() {
        return this.f605if;
    }

    public void setInlay(@NotNull Inlay<TipRenderer> inlay) {
        if (inlay == null) {
            m295enum(6);
        }
        this.f605if = inlay;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v37 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static int ya(@NotNull CharSequence text, int start, int end, boolean z) {
        int i;
        int i2;
        boolean z2;
        if (text == null) {
            m295enum(19);
        }
        CharSequence text2 = start <= end ? 1 : 0;
        int start2 = text2 != null ? Math.max(0, start) : Math.min(text.length(), start);
        int end2 = text2 != null ? Math.min(text.length(), end) : Math.max(0, end);
        int i3 = 0;
        int i4 = text2 != null ? start2 : start2 - 1;
        while (true) {
            int i5 = i4;
            if (text2 == (i5 < end2)) {
                if (Character.isWhitespace(text.charAt(i5))) {
                    i = i5;
                    i3++;
                } else {
                    if (z) {
                        return i3;
                    }
                    i = i5;
                }
                if (text2 != null) {
                    i2 = 1;
                    z2 = true;
                } else {
                    i2 = -1;
                    z2 = true;
                }
                i4 = i + i2;
            } else {
                return i3;
            }
        }
    }

    public static List<String> replaceLeadingTabs(@NotNull List<String> list, @NotNull EditorRequestService request) {
        if (list == null) {
            m295enum(16);
        }
        if (request == null) {
            m295enum(17);
        }
        return (List) list.stream().map(a -> {
            int countChars = StringUtil.countChars(a, '\t', 0, true);
            if (countChars <= 0) {
                return a;
            }
            String repeatSymbol = StringUtil.repeatSymbol(' ', countChars * request.getTabWidth());
            return repeatSymbol + repeatSymbol;
        }).collect(Collectors.toList());
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public int calcHeightInPixels(@NotNull Inlay a) {
        if (a == null) {
            m295enum(10);
        }
        if (this.f608try >= 0) {
            return this.f608try;
        }
        int lineHeight = a.getEditor().getLineHeight() * this.f610byte.size();
        this.f608try = lineHeight;
        return lineHeight;
    }
}
