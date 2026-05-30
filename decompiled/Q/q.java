package Q;

import com.aicode.enums.CodeTipType;
import com.aicode.inline.controller.ChatInputController;
import com.aicode.inline.ide.ConditionalActionConfiguration;
import com.aicode.service.CodeEditorInlay;
import java.util.List;
import javax.annotation.concurrent.Immutable;
import org.jetbrains.annotations.NotNull;

/* compiled from: cg */
@Immutable
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:Q/q.class */
public final class q implements CodeEditorInlay {

    /* renamed from: float, reason: not valid java name */
    private int f0float;

    /* renamed from: byte, reason: not valid java name */
    @NotNull
    private List<String> f1byte;

    /* renamed from: enum, reason: not valid java name */
    @NotNull
    private CodeTipType f2enum;

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m1enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 3:
            case 4:
            default:
                H = ConditionalActionConfiguration.H("\f\u0007\\B\"\u001c\u0014\u0011S\u001b\r\u0019\u0018\u001a(i\u0016EBL\u000b]\u001e\u0003\u001b\u0019P\u001b#=\u0013D\t\u001d\r\u000f\u001dV\u0006\u0018\u001c\u0019");
                i = a;
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                H = ChatInputController.H("8\u0007\u0005\u001b\u0017\u0013(>\u0019S\t\u0018R>7\u001a\u0016 \u000f\u001a*jIT\u0014\u000b\u001f\u001b\r\u0010\u0010N]S5m\u0019Z��JW\rWP\u0011N\u0017\u00035>\u0019[\t\u001eR\u001c\u001cU\f\u001b\u0016\u001a");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 3:
            case 4:
            default:
                i2 = 2;
                break;
            case 1:
            case 2:
                i2 = 3;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            case 3:
            case 4:
            default:
                objArr[0] = ConditionalActionConfiguration.H("\u0013\u001a!fR_\u000f\u0006\u001c\u0018\\\u0011\r\u0003\u0015\u0007-=V\u0019(\f\u001e\u001c\u0006\u001a\u001c,96#-Vs\b��\f\u0012\u0001?\u0006\u0001\u0011\f");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = ChatInputController.H("\u0016\u0017\n\u0013");
                i3 = a;
                break;
            case 2:
                objArr[0] = ConditionalActionConfiguration.H("PY\b\f,\u0014\u0003:\u0001\u0003\u0015\u0006");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            default:
                objArr[1] = ChatInputController.H("\u0015\u001b\r9\u000b��\u001f\u0005");
                i4 = a;
                break;
            case 1:
            case 2:
                do {
                } while (0 != 0);
                objArr[1] = ConditionalActionConfiguration.H("\u0013\u001a!fR_\u000f\u0006\u001c\u0018\\\u0011\r\u0003\u0015\u0007-=V\u0019(\f\u001e\u001c\u0006\u001a\u001c,96#-Vs\b��\f\u0012\u0001?\u0006\u0001\u0011\f");
                i4 = a;
                break;
            case 3:
                objArr[1] = ChatInputController.H("\u0019\u001c\u00016\u0017\n\u0013");
                i4 = a;
                break;
            case 4:
                objArr[1] = ConditionalActionConfiguration.H("\u0012)=pY\b\f,\u0014\u0003:\u0001\u0003\u0015\u0006");
                i4 = a;
                break;
        }
        switch (i4) {
            case 1:
            case 2:
                do {
                } while (0 != 0);
                objArr[2] = ChatInputController.H("E\u001c\f\u0007\u000eH");
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 3:
            case 4:
            default:
                throw new IllegalStateException(format);
            case 1:
            case 2:
                throw new IllegalArgumentException(format);
        }
    }

    @Override // com.aicode.service.CodeEditorInlay
    @NotNull
    public List<String> getLines() {
        List<String> list = this.f1byte;
        if (list == null) {
            m1enum(0);
        }
        return list;
    }

    @Override // com.aicode.service.CodeEditorInlay
    @NotNull
    public void setLines(List<String> list) {
        this.f1byte = list;
    }

    @Override // com.aicode.service.CodeEditorInlay
    public int getEditorOffset() {
        return this.f0float;
    }

    @Override // com.aicode.service.CodeEditorInlay
    @NotNull
    public void setType(CodeTipType a) {
        this.f2enum = a;
    }

    @NotNull
    public List<String> ze() {
        List<String> list = this.f1byte;
        if (list == null) {
            m1enum(4);
        }
        return list;
    }

    @Override // com.aicode.service.CodeEditorInlay
    public void setEditorOffset(int a) {
        this.f0float = a;
    }

    public String toString() {
        return "DefaultAICodeEditorInlay(type=" + getType() + ", editorOffset=" + getEditorOffset() + ", completionLines=" + ze() + ")";
    }

    public q(@NotNull CodeTipType type, int editorOffset, @NotNull List<String> list) {
        if (type == null) {
            m1enum(1);
        }
        if (list == null) {
            m1enum(2);
        }
        if (type == null) {
            throw new NullPointerException(ConditionalActionConfiguration.H("=JF\tIHWT\u001c\u0012\u0004\u001f\u0014\rLMI\"d]C��\u0005\u0001F\u0001\u0005S\u001f\u0007Q\u0007\u0019OJ"));
        }
        if (list != null) {
            this.f2enum = type;
            this.f0float = editorOffset;
            this.f1byte = list;
            return;
        }
        throw new NullPointerException(ChatInputController.H("\u0016\u0011\u0016\u0006;@U\n#WP\u0015JBT^\u001f\u0018\u0007\u0015\u0017\u0007OGJ(gW@\n\u0006\u000bE\u000b\u0006Y\u001c\rR\r\u001aEI"));
    }

    @Override // com.aicode.service.CodeEditorInlay
    @NotNull
    public CodeTipType getType() {
        CodeTipType codeTipType = this.f2enum;
        if (codeTipType == null) {
            m1enum(3);
        }
        return codeTipType;
    }
}
