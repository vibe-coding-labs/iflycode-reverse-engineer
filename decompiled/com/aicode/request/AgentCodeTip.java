/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.aicode.request;

import com.aicode.agent.service.GitReviewService;
import com.aicode.domain.GetTipsResult;
import com.aicode.domain.Position;
import com.aicode.domain.Range;
import com.aicode.service.CodeTip;
import com.aicode.util.AICodeStringUtil;
import com.aicode.util.PositionUtil;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class AgentCodeTip
implements CodeTip {
    private String final;
    private final List<String> try;
    private String float;
    private String byte;
    private final GetTipsResult.Tip enum;

    public AgentCodeTip(GetTipsResult.Tip tip) {
        AgentCodeTip a;
        GetTipsResult.Tip a2 = tip;
        AgentCodeTip agentCodeTip = a = this;
        agentCodeTip.enum = a2;
        agentCodeTip.try = List.of(AICodeStringUtil.splitLines(a2.getDisplayText()));
    }

    public GetTipsResult.Tip getAgentData() {
        AgentCodeTip a;
        return a.enum;
    }

    public String toString() {
        AgentCodeTip a;
        AgentCodeTip agentCodeTip = a;
        AgentCodeTip agentCodeTip2 = a;
        return "AgentCodeTip{agentData=" + a.getAgentData() + ", completion=" + agentCodeTip.try + ", requestId='" + agentCodeTip.float + "', scene='" + agentCodeTip2.byte + "', language='" + agentCodeTip2.final + "'}";
    }

    @Override
    @NotNull
    public CodeTip asCached() {
        AgentCodeTip a;
        AgentCodeTip agentCodeTip = a.Ub();
        if (agentCodeTip == null) {
            AgentCodeTip.enum(2);
        }
        return agentCodeTip;
    }

    public void setScene(String string) {
        String a = string;
        AgentCodeTip a2 = this;
        a2.byte = a;
    }

    @Override
    @NotNull
    public AgentCodeTip withCompletion(@NotNull List<String> list) {
        AgentCodeTip agentCodeTip = list2;
        List<String> list2 = list;
        AgentCodeTip a = agentCodeTip;
        if (list2 == null) {
            AgentCodeTip.enum(0);
        }
        AgentCodeTip agentCodeTip2 = a.try == list2 ? a : new AgentCodeTip(a.enum, list2, false);
        if (agentCodeTip2 == null) {
            AgentCodeTip.enum(1);
        }
        return agentCodeTip2;
    }

    @Override
    public boolean isCached() {
        return false;
    }

    @Override
    @NotNull
    public List<String> getTip() {
        AgentCodeTip a;
        List<String> list = a.try;
        if (list == null) {
            AgentCodeTip.enum(3);
        }
        return list;
    }

    public static AgentCodeTip FromString(String string) {
        String string2 = string;
        String string3 = string2;
        GetTipsResult.Tip a = new GetTipsResult.Tip(PositionUtil.H("G7R/"), string3, Range.of(Position.of(0, 0), Position.of(0, 0)), string3, Position.of(0, 0));
        return new AgentCodeTip(a);
    }

    public String getScene() {
        AgentCodeTip a;
        return a.byte;
    }

    private AgentCodeTip Ub() {
        AgentCodeTip a;
        AgentCodeTip agentCodeTip;
        AgentCodeTip agentCodeTip2 = agentCodeTip = this;
        AgentCodeTip agentCodeTip3 = a = new AgentCodeTip(agentCodeTip2.enum, agentCodeTip2.try, true);
        AgentCodeTip agentCodeTip4 = agentCodeTip;
        a.setRequestId(agentCodeTip4.float);
        agentCodeTip3.setScene(agentCodeTip4.byte);
        agentCodeTip3.setLanguage(agentCodeTip.final);
        return agentCodeTip3;
    }

    public void setLanguage(String string) {
        String a = string;
        AgentCodeTip a2 = this;
        a2.final = a;
    }

    public String getLanguage() {
        AgentCodeTip a;
        return a.final;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static /* synthetic */ void enum(int a) {
        RuntimeException runtimeException;
        int n;
        Object[] objectArray;
        int n2;
        Object[] objectArray2;
        int n3;
        int n4;
        String string;
        switch (a) {
            default: {
                string = GitReviewService.H("\u0004\f*\u0003*\u0019%\u0004`\u001d.\b*q\u0018\u000254 \u0002=J1\u001b%\r=\u000e~T\u0017~d]2]j\u001e6KB/eU3[,\u000f\u0003?m\u0018;\u001ba\u0018/Q?\u001f%\u001e");
                n4 = a;
                break;
            }
            case 1: 
            case 2: 
            case 3: {
                string = PositionUtil.H("i\u0017V=a*D4Rox\u0019S$]i\u00171\u0006}lO^6K<\u0019'g\f\u00157I(L;\\bG,]-");
                n4 = a;
                break;
            }
        }
        switch (n4) {
            default: {
                n3 = 3;
                break;
            }
            case 1: 
            case 2: 
            case 3: {
                n3 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n3];
        switch (a) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = GitReviewService.H("7\u0000,\n&\u0014%\u0003&\u001c");
                n2 = a;
                break;
            }
            case 1: 
            case 2: 
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = PositionUtil.H("\u000eT&\u0016([!G<z@A&I=\\:|Wt\"I2M\n]&L\rX1");
                n2 = a;
                break;
            }
        }
        switch (n2) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = GitReviewService.H("=,\u0015n\u001b#\u0012?\u000f\u0002s9\u00151\u000e$\t\u0004d\f\u00111\u000159%\u00154> \u0002");
                n = a;
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[1] = PositionUtil.H("\u007f\u0011A-o3T9^']0^/");
                n = a;
                break;
            }
            case 2: {
                objectArray = objectArray2;
                objectArray2[1] = GitReviewService.H(" \t\t\u00102\u0002,\u0016");
                n = a;
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = PositionUtil.H("U']\rX1");
                n = a;
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = GitReviewService.H("\u0007\"9\u001e\u0017\u0000,\n&\u0014%\u0003&\u001c");
                break;
            }
            case 1: 
            case 2: 
            case 3: {
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (a) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                throw runtimeException;
            }
            case 1: 
            case 2: 
            case 3: 
        }
        runtimeException = new IllegalStateException(string2);
        throw runtimeException;
    }

    public void setRequestId(String string) {
        String a = string;
        AgentCodeTip a2 = this;
        a2.float = a;
    }

    public String getRequestId() {
        AgentCodeTip a;
        return a.float;
    }

    /*
     * WARNING - void declaration
     */
    public AgentCodeTip(GetTipsResult.Tip tip, List<String> list, boolean bl) {
        void a;
        AgentCodeTip a2;
        List<String> a3 = list;
        AgentCodeTip agentCodeTip = a2 = this;
        agentCodeTip.enum = a;
        agentCodeTip.try = a3;
    }
}
