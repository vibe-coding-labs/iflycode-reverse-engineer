package com.aicode.complete;

import com.aicode.agent.service.CodeCompleteService;
import com.aicode.enums.OperateActionEnum;
import com.aicode.icons.Icons;
import com.aicode.message.BasicActionsBundle;
import com.aicode.service.EditorRequestService;
import com.aicode.service.TipRenderer;
import com.aicode.util.PropertyUtils;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.Inlay;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.ui.GotItTooltip;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import javax.swing.JComponent;
import org.jetbrains.annotations.NotNull;

/* compiled from: og */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/complete/InlayGotItListener.class */
public class InlayGotItListener implements InlayListener {
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m113enum(int a) {
        String H = PropertyUtils.H("\fh!d*ub/ap\"hoX\u001cj:W2|:!7q~:\u0006Y8~<9d1(+E]+:srE\u0019%!\u0003L8hqh9ugr);)e<k");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = CodeCompleteService.H("~GvLyRp");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = PropertyUtils.H("s#u3u(s");
                break;
            case 2:
                objArr[0] = CodeCompleteService.H("GcPhNv");
                break;
            case 3:
                objArr[0] = PropertyUtils.H("\"r\"c$u\"t\u0005u+q)t");
                break;
            case 4:
                objArr[0] = CodeCompleteService.H("xFaRhWyOp");
                break;
            case 5:
                objArr[0] = PropertyUtils.H("d#t+d9w");
                break;
            case 6:
                objArr[0] = CodeCompleteService.H("EcAfMuNj");
                break;
        }
        objArr[1] = PropertyUtils.H("o4\u0006\u0013-r-v'qto\n_=v3u\u000e\u0013\u001fo\u0002X2[>r\u001fu\u000by?o\"~5u");
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 3:
            default:
                objArr[2] = CodeCompleteService.H("nG`_bZYRcXhD`");
                break;
            case 4:
            case 5:
            case 6:
                do {
                } while (0 != 0);
                objArr[2] = PropertyUtils.H("u>n0D#t+d9w");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // com.aicode.complete.InlayListener
    public void inlaysUpdated(@NotNull EditorRequestService request, @NotNull OperateActionEnum context, @NotNull Editor editor, @NotNull List<Inlay<TipRenderer>> list) {
        if (request == null) {
            m113enum(0);
        }
        if (context == null) {
            m113enum(1);
        }
        if (editor == null) {
            m113enum(2);
        }
        if (list == null) {
            m113enum(3);
        }
        if (!list.isEmpty() && !request.isCancelled()) {
            GotItTooltip andShowCloseShortcut = new GotItTooltip(BasicActionsBundle.message(PropertyUtils.H("[>t;imu2o\nV(4\u0013e\u0002H9s/Z?u>h\u0011s(e<53u(s"), new Object[0]) + ".inlayGotIt", CodeCompleteService.H("挶:\u0004\u007f\u0017XCe\u0015#\\%\u0005将仁砆揲兠缻辙噝〒\u0003xk\u0002挵9\u0014o\u0013MK~\u0015#J3\u000f叜涫揖礛仧硣ぅ\u0011jW>挦*\u001fd\u0016LExBl\u000fQ\u001f)C:\u001c戏��9K2\u0018\\ExBl%}\u001e(B;\r刏捗倉适绽枿〄\u0004\u007fw\u001e挱=<G\u001bASn\u00186\tP\u001e(K2\u001e戍\u0005<@9��DA|\u0015;\u001fy%\u0013^'\b強创觮叩仾砨衩兀』"), request.getDisposable()).withHeader(BasicActionsBundle.message(PropertyUtils.H("[>t;imu2o\nV(4\u0013e\u0002H9s/Z?u>h\u0011s(e<53u(s"), new Object[0]) + "快捷引导").withPosition(Balloon.Position.atLeft).withIcon(Icons.ToolWindowIcon).andShowCloseShortcut();
            Rectangle bounds = list.get(0).getBounds();
            if (bounds == null || !andShowCloseShortcut.canShow()) {
                return;
            }
            try {
                wf(editor.getContentComponent(), andShowCloseShortcut, bounds.getLocation());
            } catch (Exception e) {
                Logger.getInstance(getClass()).error(CodeCompleteService.H("IPuF~\u001ehMoUnNb\ra[|^c9{Smay\r|WrExA}"), e);
            }
        }
    }

    private void wf(@NotNull JComponent component, @NotNull GotItTooltip tooltip, @NotNull Point a) {
        if (component == null) {
            m113enum(4);
        }
        if (tooltip == null) {
            m113enum(5);
        }
        if (a == null) {
            m113enum(6);
        }
    }
}
