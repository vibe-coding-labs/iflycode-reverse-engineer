package com.aicode.service.editor;

import com.aicode.agent.dto.ResponseStreamDto;
import com.aicode.agent.service.CodeCompleteService;
import com.aicode.domain.Range;
import com.aicode.exception.RequestCancelException;
import com.aicode.generate.CodeTipUtil;
import com.aicode.request.AgentCodeTip;
import com.aicode.service.CodeEditorInlay;
import com.aicode.service.CodeInlayList;
import com.aicode.service.CodeTip;
import com.aicode.service.EditorRequestService;
import com.intellij.openapi.util.TextRange;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: tc */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/editor/AgentCodeTipList.class */
public class AgentCodeTipList implements CodeInlayList {

    /* renamed from: long, reason: not valid java name */
    private ResponseStreamDto.ResponseData f551long;

    /* renamed from: super, reason: not valid java name */
    private String f552super;

    /* renamed from: for, reason: not valid java name */
    public static final /* synthetic */ boolean f553for;

    /* renamed from: if, reason: not valid java name */
    @NotNull
    private final EditorRequestService f554if;

    /* renamed from: case, reason: not valid java name */
    private String f555case;

    /* renamed from: final, reason: not valid java name */
    private boolean f556final;

    /* renamed from: try, reason: not valid java name */
    private final AgentCodeTip f557try;

    /* renamed from: float, reason: not valid java name */
    private String f558float;

    /* renamed from: byte, reason: not valid java name */
    private String f559byte;

    /* renamed from: enum, reason: not valid java name */
    private final CodeInlayList f560enum;

    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m276enum(int a) {
        String H;
        int i;
        int i2;
        int i3;
        int i4;
        switch (a) {
            case 0:
            case 1:
            default:
                H = RequestCancelException.H("y-U Y6\u0011l\u00145Z \u0018\u001fk-J\u0017V(\u00138`\u0016D0Y6K=Wb5PM~\u0015=Rs \u0011\u0016zRfp\u000fK+\u0002+J6\u00141ZxJ6P7");
                i = a;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                do {
                } while (0 != 0);
                H = CodeCompleteService.H("\"\tb\\e{E`\u0002j]igEG&\r~\u0007)k\u001dHuOm KJt\u001fh]i\\~L'WiMh");
                i = a;
                break;
        }
        switch (i) {
            case 0:
            case 1:
            default:
                i2 = 3;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                i2 = 2;
                do {
                } while (0 != 0);
        }
        Object[] objArr = new Object[i2];
        switch (a) {
            case 0:
            default:
                objArr[0] = RequestCancelException.H("C\"@,@\u0010P<A\u0017U+");
                i3 = a;
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = CodeCompleteService.H("~GvLyRp");
                i3 = a;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[0] = RequestCancelException.H("\u001cw}XW8W<[=\n1w\u0007H0V7\u001b6a\u000bL0Si\\\u001d]1V\u0006J&Q\u0007V(h*O/");
                i3 = a;
                break;
        }
        switch (i3) {
            case 0:
            case 1:
            default:
                objArr[1] = CodeCompleteService.H("\u0001(`\u0007JgJcFb\u0017njXUoKh\u0006i|TQoN6AB@nKYWyLXKwuuRp");
                i4 = a;
                break;
            case 2:
                do {
                } while (0 != 0);
                objArr[1] = RequestCancelException.H("E Q\u0003}\u0010P<A\u0017U+");
                i4 = a;
                break;
            case 3:
            case 4:
                objArr[1] = CodeCompleteService.H("gYmR@Ul^y]pLbVUXrFa");
                i4 = a;
                break;
            case 5:
                objArr[1] = RequestCancelException.H("F#i(]/N$F'Y6Q,p&D/");
                i4 = a;
                break;
            case 6:
                objArr[1] = CodeCompleteService.H("zLxkiU}Xw");
                i4 = a;
                break;
            case 7:
                objArr[1] = RequestCancelException.H("]'Z*E7S)");
                i4 = a;
                break;
        }
        switch (i4) {
            case 0:
            case 1:
            default:
                objArr[2] = CodeCompleteService.H("\u001enWuU:");
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                break;
        }
        String format = String.format(H, objArr);
        switch (a) {
            case 0:
            case 1:
            default:
                throw new IllegalArgumentException(format);
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                throw new IllegalStateException(format);
        }
    }

    @Override // com.aicode.service.CodeInlayList
    public void setReplacementText(String a) {
        this.f555case = a;
    }

    @Override // com.aicode.service.CodeInlayList
    public void setReplacementRange(TextRange a) {
        this.f560enum.setReplacementRange(a);
    }

    public String getScene() {
        return this.f552super;
    }

    public void setLanguage(String a) {
        this.f558float = a;
    }

    public AgentCodeTipList(@Nullable CodeInlayList inlays, @NotNull AgentCodeTip agentCodeTip, @NotNull EditorRequestService a) {
        if (agentCodeTip == null) {
            m276enum(0);
        }
        if (a == null) {
            m276enum(1);
        }
        this.f556final = false;
        this.f560enum = inlays;
        this.f557try = agentCodeTip;
        this.f554if = a;
        this.f555case = CodeTipUtil.dropOverlappingTrailingLines(agentCodeTip.getAgentData().getText(), a.getDocumentContent(), a.getOffset());
    }

    public void setRequestId(String a) {
        this.f559byte = a;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.aicode.service.CodeInlayList
    public int getOffset() {
        if (this.f554if == null) {
            return 0;
        }
        return this.f554if.getOffset();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.aicode.service.CodeInlayList
    @NotNull
    public List<CodeEditorInlay> getInlays() {
        List<CodeEditorInlay> emptyList = this.f560enum == null ? Collections.emptyList() : this.f560enum.getInlays();
        if (emptyList == null) {
            m276enum(6);
        }
        return emptyList;
    }

    @Override // com.aicode.service.CodeInlayList
    public void setRemoveBlank(boolean z) {
        this.f556final = z;
    }

    @Override // com.aicode.service.CodeInlayList
    public ResponseStreamDto.ResponseData getData() {
        return this.f551long;
    }

    @Override // com.aicode.service.CodeInlayList
    @NotNull
    public String getReplacementText() {
        String str = this.f555case;
        if (str != null) {
            if (str == null) {
                m276enum(5);
            }
            return str;
        }
        throw new RuntimeException();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.aicode.service.CodeInlayList
    @NotNull
    public TextRange getReplacementRange() {
        if (this.f560enum.getReplacementRange() == null) {
            String documentContent = this.f554if.getDocumentContent();
            Range range = this.f557try.getAgentData().getRange();
            int offset = range.getStart().toOffset(documentContent);
            int offset2 = range.getEnd().toOffset(documentContent);
            if (!f553for && offset < 0) {
                throw new AssertionError();
            }
            if (!f553for && offset2 < offset) {
                throw new AssertionError();
            }
            TextRange create = TextRange.create(offset, offset2);
            if (create == null) {
                throw new RuntimeException();
            }
            if (create == null) {
                m276enum(4);
            }
            return create;
        }
        TextRange replacementRange = this.f560enum.getReplacementRange();
        if (replacementRange == null) {
            throw new RuntimeException();
        }
        if (replacementRange == null) {
            m276enum(3);
        }
        return replacementRange;
    }

    @Override // com.aicode.service.CodeInlayList
    public boolean isRemoveBlank() {
        return this.f556final;
    }

    @Override // com.aicode.service.CodeInlayList
    public void setData(ResponseStreamDto.ResponseData a) {
        this.f551long = a;
    }

    public String getLanguage() {
        return this.f558float;
    }

    @Override // com.aicode.service.CodeInlayList
    @NotNull
    public CodeTip getAICodeTip() {
        AgentCodeTip agentCodeTip = this.f557try;
        if (agentCodeTip != null) {
            if (agentCodeTip == null) {
                m276enum(2);
            }
            return agentCodeTip;
        }
        throw new RuntimeException();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.aicode.service.CodeInlayList
    public boolean isEmpty() {
        return this.f560enum == null || this.f560enum.isEmpty();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // java.lang.Iterable
    @NotNull
    public Iterator<CodeEditorInlay> iterator() {
        Iterator<CodeEditorInlay> it = this.f560enum != null ? this.f560enum.iterator() : Collections.emptyIterator();
        if (it == null) {
            m276enum(7);
        }
        return it;
    }

    public String getRequestId() {
        return this.f559byte;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static {
        f553for = !AgentCodeTipList.class.desiredAssertionStatus();
    }

    public void setScene(String a) {
        this.f552super = a;
    }
}
