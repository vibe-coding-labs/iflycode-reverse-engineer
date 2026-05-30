package com.aicode.generate;

import com.aicode.agent.dto.ResponseStreamDto;
import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.service.CodeEditorInlay;
import com.aicode.service.CodeInlayList;
import com.aicode.service.CodeTip;
import com.aicode.util.JComponentKt;
import com.intellij.openapi.util.TextRange;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* compiled from: el */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/generate/DefaultInlayList.class */
public class DefaultInlayList implements CodeInlayList {

    /* renamed from: final, reason: not valid java name */
    public final List<CodeEditorInlay> f298final;

    /* renamed from: try, reason: not valid java name */
    @NotNull
    public TextRange f299try;

    /* renamed from: float, reason: not valid java name */
    @NotNull
    public final CodeTip f300float;

    /* renamed from: byte, reason: not valid java name */
    @NotNull
    public String f301byte;

    /* renamed from: enum, reason: not valid java name */
    private boolean f302enum;

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m160enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 4:
            case 5:
            case 6:
            default:
                H = JComponentKt.H("Bj\u0007:\u0007\u001a&��m\u0006<\u000b(\t<^@0lA3F \u001e&\u0007@(*\u0017\u007f\u000b=\n<\u001d,D7\n-\u000b");
                i = a;
                break;
            case 1:
            case 2:
            case 3:
                do {
                } while (0 != 0);
                H = OpenTelemetryUtil.H("I>e3c/k5$& y&\u0002J/{\u0005q,\u007fwp%g0i%;nWA#etd +rp(:;t[Lb>~= *w(\r\u000bml|#y=");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 4:
            case 5:
            case 6:
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
            case 4:
            case 5:
            case 6:
            default:
                objArr[0] = JComponentKt.H(")\u0003 D8\u0016#\t<\u001bJ$'\n%\u0014,\u001f0\\$##\u0002*\u0015,7'\u0003#\u001d\u0015\u00162\u0013");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = OpenTelemetryUtil.H("n\"N&d![3@\u0019d)f?z?");
                i3 = a;
                break;
            case 2:
                objArr[0] = JComponentKt.H("\u0012#5\u000f>\u001a=\u0013,\u0001668\u0011&\u0002");
                i3 = a;
                break;
            case 3:
                objArr[0] = OpenTelemetryUtil.H("9h9l%{9@\ff8F3m%");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            default:
                objArr[1] = JComponentKt.H(" \u001b'\u00168\u000b.\u0015");
                i4 = a;
                break;
            case 1:
            case 2:
            case 3:
                do {
                } while (0 != 0);
                objArr[1] = OpenTelemetryUtil.H("g/\"$D\bg/c&/#q>h;t%MCK.k(u(l\u0015C\u0005i5^?f%");
                i4 = a;
                break;
            case 4:
                objArr[1] = JComponentKt.H("8\u001c,?��,-��<+(\u0017");
                i4 = a;
                break;
            case 5:
                objArr[1] = OpenTelemetryUtil.H("6M\u0018].}%a'}1H\u0007|\u001es8r4");
                i4 = a;
                break;
            case 6:
                objArr[1] = JComponentKt.H("2\u0016\u0014\u0014 \u00133\u0018;\u001b$\n,\u0010\r\u001a9\u0013");
                i4 = a;
                break;
        }
        switch (i4) {
            case 1:
            case 2:
            case 3:
                do {
                } while (0 != 0);
                objArr[2] = OpenTelemetryUtil.H("4%|?ao");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 4:
            case 5:
            case 6:
            default:
                throw new IllegalStateException(format);
            case 1:
            case 2:
            case 3:
                throw new IllegalArgumentException(format);
        }
    }

    public String toString() {
        return "DefaultInlayList(codeTip=" + getAICodeTip() + ", replacementRange=" + getReplacementRange() + ", replacementText=" + getReplacementText() + ", inlays=" + getInlays() + ")";
    }

    public boolean canEqual(Object a) {
        return a instanceof DefaultInlayList;
    }

    @Override // com.aicode.service.CodeInlayList
    public ResponseStreamDto.ResponseData getData() {
        return null;
    }

    @Override // com.aicode.service.CodeInlayList
    public void setRemoveBlank(boolean z) {
        this.f302enum = z;
    }

    @Override // com.aicode.service.CodeInlayList
    public List<CodeEditorInlay> getInlays() {
        return this.f298final;
    }

    @Override // com.aicode.service.CodeInlayList
    @NotNull
    public void setReplacementText(String a) {
        this.f301byte = a;
    }

    public DefaultInlayList(@NotNull CodeTip aiCodeCompletion, @NotNull TextRange replacementRange, @NotNull String replacementText, List<CodeEditorInlay> list) {
        if (aiCodeCompletion == null) {
            m160enum(1);
        }
        if (replacementRange == null) {
            m160enum(2);
        }
        if (replacementText == null) {
            m160enum(3);
        }
        this.f302enum = false;
        if (aiCodeCompletion == null) {
            throw new NullPointerException(OpenTelemetryUtil.H("!f\bk$v\u0014o)e=a4&dKAm3'.a6\u007f5ii{>FAa>a% &m(\r��{l|#y="));
        }
        if (replacementRange == null) {
            throw new NullPointerException(JComponentKt.H("\u001dgT\u0004/*\n'\t#\u001f\u000b\u001e.\u0001=^\f0b\t!\u0014&\u000e1S\u000e)+N1\f4\u00120T0\u0017b\r6C6\u000b~X"));
        }
        if (replacementText != null) {
            this.f300float = aiCodeCompletion;
            this.f299try = replacementRange;
            this.f301byte = replacementText;
            this.f298final = list;
            return;
        }
        throw new NullPointerException(OpenTelemetryUtil.H("}.t,r4e)p?p\u0014*sQAm3'.a6\u007f5ii{>FAa>a% &m(\r��{l|#y="));
    }

    @Override // com.aicode.service.CodeInlayList
    @NotNull
    public void setReplacementRange(TextRange a) {
        this.f299try = a;
    }

    @Override // com.aicode.service.CodeInlayList
    @NotNull
    public TextRange getReplacementRange() {
        TextRange textRange = this.f299try;
        if (textRange == null) {
            m160enum(5);
        }
        return textRange;
    }

    @Override // com.aicode.service.CodeInlayList
    @NotNull
    public CodeTip getAICodeTip() {
        CodeTip codeTip = this.f300float;
        if (codeTip == null) {
            m160enum(4);
        }
        return codeTip;
    }

    @Override // java.lang.Iterable
    @NotNull
    public Iterator<CodeEditorInlay> iterator() {
        Iterator<CodeEditorInlay> it = this.f298final.iterator();
        if (it == null) {
            m160enum(0);
        }
        return it;
    }

    @Override // com.aicode.service.CodeInlayList
    @NotNull
    public String getReplacementText() {
        String str = this.f301byte;
        if (str == null) {
            m160enum(6);
        }
        return str;
    }

    @Override // com.aicode.service.CodeInlayList
    @NotNull
    public int getOffset() {
        return 0;
    }

    @Override // com.aicode.service.CodeInlayList
    public void setData(ResponseStreamDto.ResponseData responseData) {
    }

    @Override // com.aicode.service.CodeInlayList
    public boolean isRemoveBlank() {
        return this.f302enum;
    }

    @Override // com.aicode.service.CodeInlayList
    public boolean isEmpty() {
        return this.f298final.isEmpty();
    }
}
