package com.aicode.inline.ide;

import com.aicode.content.util.file.LanguageFileExtensionDetails;
import com.aicode.diff.FileInfo;
import com.aicode.inline.InlineChatService;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import org.jetbrains.annotations.NotNull;

/* compiled from: ai */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/ide/IdeEditorActionRouterKt.class */
public final class IdeEditorActionRouterKt {

    /* renamed from: enum, reason: not valid java name */
    private static final Logger f418enum = Logger.getInstance(IdeEditorActionRouterKt.class);

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m208enum(int a) {
        String H = FileInfo.H("3i\u0012i\u0013r[(R}\u0010d^W{3&u\nz\u00127.V\rw\u001f~\u001bc\u0006=N%)\u0014X~\t&WhZ8\u0006<$U\u0007iNi��r^u\f >L\u0003j");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            default:
                objArr[0] = LanguageFileExtensionDetails.H("\u0007j\nF0}#v\f}\u0016k%s\u0011J!q\u000b");
                break;
            case 1:
                do {
                } while (0 != 0);
                objArr[0] = FileInfo.H("\u001ft\u001di?W&b");
                break;
            case 2:
                objArr[0] = LanguageFileExtensionDetails.H("q\u0010O(j\u001a}\u0016g\u0005c#m\u0017[0a!z\u0016r\u0010b\u001d`\u001e_/{\u0017");
                break;
            case 3:
                objArr[0] = FileInfo.H("u'L\u001ds\u000bD\u0007g\nD\fr&P\fc");
                break;
        }
        objArr[1] = LanguageFileExtensionDetails.H("'k\u0004*\tL+k\rlK}\u0017c\u000b|\u001a\u000e%g\u000b;0m\u0001J\u0006g\u0017]-N\u0001a\u0011{\u0017W\u0007g\u000bN4_\r");
        objArr[2] = FileInfo.H("(V\b}\u000ee\u0017L\u001di\u001d_&N\u0010t\u001an��h\u001f{(c$P��h");
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void replaceWithConditionalAction(@NotNull EditorActionManager editorActionManager, @NotNull String actionId, @NotNull ConditionalActionConfiguration conditionalActionConfiguration, @NotNull InlineChatService inlineChatService) {
        if (editorActionManager == null) {
            m208enum(0);
        }
        if (actionId == null) {
            m208enum(1);
        }
        if (conditionalActionConfiguration == null) {
            m208enum(2);
        }
        if (inlineChatService == null) {
            m208enum(3);
        }
        try {
            if (ActionManager.getInstance().getAction(actionId) == null) {
                f418enum.debug("Skipping action " + actionId + " because it is not registered in the action manager");
                return;
            }
            EditorActionHandler actionHandler = editorActionManager.getActionHandler(actionId);
            if (actionHandler == null) {
                return;
            }
            editorActionManager.setActionHandler(actionId, new ConditionalEditorActionHandler(actionHandler, conditionalActionConfiguration, inlineChatService));
        } catch (ClassCastException unused) {
            f418enum.warn("Failed to add condition to action " + actionId);
        }
    }
}
