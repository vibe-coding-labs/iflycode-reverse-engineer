package com.aicode.inline.ide;

import com.aicode.inline.InlineChatService;
import com.aicode.inline.KeyStrokeExecutorProvider;
import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.intellij.openapi.actionSystem.KeyboardShortcut;
import com.intellij.openapi.actionSystem.Shortcut;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.keymap.KeymapManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import javax.swing.KeyStroke;
import org.jetbrains.annotations.NotNull;

/* compiled from: jf */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/ide/IdeEditorActionRouter.class */
public final class IdeEditorActionRouter {

    /* renamed from: float, reason: not valid java name */
    @NotNull
    private final InlineChatService f415float;

    /* renamed from: byte, reason: not valid java name */
    private static final Logger f416byte = Logger.getInstance(IdeEditorActionRouter.class);

    /* renamed from: enum, reason: not valid java name */
    @NotNull
    private final KeyStrokeExecutorProvider f417enum;

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m207enum(int a) {
        String H = ConditionalActionConfiguration.H("9\u000fTC\u0019\u0014\u0017\bX\u001b\\Dt\u00117\u0013\f3-1\u0015\\\u0004\u0010\u001b\r\u001f\u0012\u001b\u000f.yY^\u001aKT\u001e\u0014WV\u0005ao\u0001W\u0005\u0018\u001a\u0018X\u0013��\u001eX\u001f\u0016V\u0006\u0018\u001c\u0019");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = InlineChatStatusServiceKt.H("\r)\u000e/\r\u001f\u001c/\u0003)+9\u000b;\u00019\u001a");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = ConditionalActionConfiguration.H("\u001a\u0017\u000e \u0002=%\u0019\u0012-\u0015\f\u000f\r\t��\u0018(\u000f\u001c��\u0001\t\u0015\u0007");
                break;
        }
        objArr[1] = InlineChatStatusServiceKt.H("@\u000e)c\b$\u000b\u0002,)F(\n0\u0010)\u0007u\u0016\r)d'8\u001c\u0004��.\u0016)\u0011;<3\u000b2\u0016\u000e\u00168\u001c?\r");
        objArr[2] = ConditionalActionConfiguration.H("O\u001f\u0006\u0004\u0004K");
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    public IdeEditorActionRouter(@NotNull InlineChatService inlineChatService, @NotNull KeyStrokeExecutorProvider a) {
        if (inlineChatService == null) {
            m207enum(0);
        }
        if (a == null) {
            m207enum(1);
        }
        this.f415float = inlineChatService;
        this.f417enum = a;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void init() {
        Iterator<IdeAction> it = IdeActionService.INSTANCE.getIdeActions().iterator();
        while (it.hasNext()) {
            IdeAction next = it.next();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Shortcut[] shortcuts = KeymapManager.getInstance().getActiveKeymap().getShortcuts(next.getActionId());
            ArrayList arrayList = new ArrayList();
            int length = shortcuts.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                Shortcut shortcut = shortcuts[i2];
                if (shortcut instanceof KeyboardShortcut) {
                    arrayList.add(shortcut);
                }
                i2++;
                i = i2;
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                KeyboardShortcut keyboardShortcut = (KeyboardShortcut) it2.next();
                linkedHashSet.add(keyboardShortcut.getFirstKeyStroke());
                KeyStroke secondKeyStroke = keyboardShortcut.getSecondKeyStroke();
                if (secondKeyStroke != null) {
                    linkedHashSet.add(secondKeyStroke);
                }
                f416byte.debug("Registered action " + next + " with keyStrokes " + keyboardShortcut.getFirstKeyStroke());
                it2 = it2;
            }
            ConditionalActionConfiguration conditionalActionConfiguration = new ConditionalActionConfiguration(next.getScope(), this.f417enum, linkedHashSet);
            it = it;
            IdeEditorActionRouterKt.replaceWithConditionalAction(EditorActionManager.getInstance(), next.getActionId(), conditionalActionConfiguration, this.f415float);
        }
    }
}
