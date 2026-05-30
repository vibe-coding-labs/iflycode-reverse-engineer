package com.aicode.inline;

import cn.hutool.core.util.StrUtil;
import com.aicode.PluginStartupActivity;
import com.aicode.agent.enums.PermissionEnum;
import com.aicode.content.util.EditorUtils;
import com.aicode.content.util.OverlayUtils;
import com.aicode.diff.GenericUtils;
import com.aicode.exception.RequestTimeoutException;
import com.aicode.service.editor.InlayRendering;
import com.aicode.service.editor.RequestResultList;
import com.aicode.settings.AICodeRequestSettings;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.util.AICodeUtils;
import com.aicode.util.Application;
import com.aicode.util.PropertyUtils;
import com.aicode.util.StringUtils;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorCustomElementRenderer;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.Inlay;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.editor.event.SelectionEvent;
import com.intellij.openapi.editor.event.SelectionListener;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.util.TextRange;
import com.intellij.ui.JBColor;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.annotations.NotNull;

/* compiled from: dm */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/InlineChatInlay.class */
public class InlineChatInlay {

    /* renamed from: byte, reason: not valid java name */
    private static Inlay<?> f317byte;

    @NotNull
    public static final InlineChatInlay INSTANCE = new InlineChatInlay();

    /* renamed from: enum, reason: not valid java name */
    @NotNull
    private static final AtomicBoolean f318enum = new AtomicBoolean(false);

    @NotNull
    public static final Map<Editor, Balloon> balloons = new ConcurrentHashMap();

    private void fe(final Editor a) {
        final SelectionModel selectionModel = a.getSelectionModel();
        selectionModel.addSelectionListener(new SelectionListener() { // from class: com.aicode.inline.InlineChatInlay.02
            /* renamed from: enum, reason: not valid java name */
            private static /* synthetic */ void m168enum(int a2) {
                throw new IllegalArgumentException(String.format(EditorUtils.H("[3w>q\"y86+2t:\u0001S)k\no-qff,/gW\u0004i#md=dna6\"VK>3<lt|W\u0014n2?*}=*32,-m-v"), Application.H("4"), EditorUtils.H("\u0002r+0%s\"r\"sbY\u0005w)|,(\u0015T\rt(z\u0007z(~\u00189`\"ae("), Application.H("ZObho{hmwY,&>475")));
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v23 */
            /* JADX WARN: Type inference failed for: r0v24 */
            /* JADX WARN: Type inference failed for: r0v40 */
            /* JADX WARN: Type inference failed for: r0v41 */
            /* JADX WARN: Type inference failed for: r0v46 */
            /* JADX WARN: Type inference failed for: r0v50 */
            /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
            public void selectionChanged(@NotNull SelectionEvent a2) {
                boolean contains;
                if (a2 == null) {
                    m168enum(0);
                }
                try {
                    InlineChatInlay.disposeInlay();
                    if (a2.getEditor() == FileEditorManager.getInstance(a2.getEditor().getProject()).getSelectedTextEditor()) {
                        String selectedText = a2.getNewRange().isEmpty() ? null : a2.getEditor().getSelectionModel().getSelectedText();
                        if (selectedText != null && !StringUtils.isBlank(PluginStartupActivity.getApiKey())) {
                            contains = AICodeSettingsState.getInstance().permissions.contains(PermissionEnum.INLINE_CHAT.getPermission());
                            if (contains && AICodeSettingsState.getInstance().openInlineChat && a.getSelectionModel().hasSelection()) {
                                if (((selectedText == null || StringUtils.isBlank(selectedText)) ? 1 : 0) == null) {
                                    int selectionStart = selectionModel.getSelectionStart();
                                    int selectionEnd = selectionModel.getSelectionEnd();
                                    int lineNumber = a.getDocument().getLineNumber(selectionStart);
                                    boolean equals = lineNumber != a.getDocument().getLineNumber(selectionEnd) ? true : a.getDocument().getText(new TextRange(a.getDocument().getLineStartOffset(lineNumber), a.getDocument().getLineEndOffset(lineNumber))).trim().equals(selectedText.trim());
                                    int offset = a.getCaretModel().getOffset();
                                    AnonymousClass02 anonymousClass02 = (offset == selectionStart || offset == selectionEnd) ? 1 : 0;
                                    if (!equals || anonymousClass02 == null) {
                                        return;
                                    }
                                    InlineChatInlay.INSTANCE.addInlay(a);
                                }
                            }
                        }
                    }
                } catch (Throwable th) {
                }
            }
        });
    }

    private InlineChatInlay() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: dm */
    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/inline/InlineChatInlay$u.class */
    public static class u implements EditorCustomElementRenderer {

        /* renamed from: enum, reason: not valid java name */
        private final String f323enum;

        /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
        /* renamed from: enum, reason: not valid java name */
        private static /* synthetic */ void m169enum(int a) {
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
                default:
                    H = OverlayUtils.H("\b\u0016&\u0019&\u0003)\u001el\u0007\"\u0012&k\u000f\u00032%1\u0005-L6\n?\u0001kN\u0015)4KcL2Kf\u0004+@N5nH:D1\u0004\u00128f\u0005+\u001di\u00064\\bT1\u001c");
                    i = a;
                    break;
                case 5:
                case 6:
                    do {
                    } while (0 != 0);
                    H = RequestResultList.H("]au\\_V6\u0004\u001db\u007f\\pEy\u000f?[?\u0006D%q[fS \\R{:Z}^`Uc\u001f>\u0017m_");
                    i = a;
                    break;
            }
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                default:
                    i2 = 3;
                    break;
                case 5:
                case 6:
                    i2 = 2;
                    do {
                    } while (0 != 0);
            }
            Object[] objArr = new Object[i2];
            switch (a) {
                case 0:
                default:
                    objArr[0] = OverlayUtils.H("\u0015bM<\t");
                    i3 = a;
                    break;
                case 1:
                    do {
                    } while (0 != 0);
                    objArr[0] = RequestResultList.H("T");
                    i3 = a;
                    break;
                case 2:
                    objArr[0] = OverlayUtils.H("#\u0019kH2\u001e");
                    i3 = a;
                    break;
                case 3:
                    objArr[0] = RequestResultList.H("[dQEji@qNnUijb\\Y^aUd]%\u0016d@");
                    i3 = a;
                    break;
                case 4:
                    objArr[0] = OverlayUtils.H("4\u0018eU2\u0002");
                    i3 = a;
                    break;
                case 5:
                case 6:
                    objArr[0] = RequestResultList.H("yGu\u0005|FyGuFu\u0001ScsF}\u0005TAvA\u007fFtm}Z\\IlSD+RAv^GBc[5\u0010dA");
                    i3 = a;
                    break;
            }
            switch (i3) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                default:
                    objArr[1] = OverlayUtils.H("%\u0004)F \u0005%\u0004)\u0005)B\u000f /\u0005!F\b\u0002*\u0002#\u0005(.!\u0019��\n0\u0010\u0018h\u000e\u0002*\u001d\u001b\u0001?\u0018iS8\u0002");
                    i4 = a;
                    break;
                case 5:
                case 6:
                    do {
                    } while (0 != 0);
                    objArr[1] = RequestResultList.H("@eFijb\\Y^aUd]%\u0016d@");
                    i4 = a;
                    break;
            }
            switch (i4) {
                case 0:
                case 1:
                case 2:
                case 3:
                default:
                    objArr[2] = OverlayUtils.H("\fmH3\u0004");
                    break;
                case 4:
                    do {
                    } while (0 != 0);
                    objArr[2] = RequestResultList.H("@eFijb\\Y^aUd]%\u0016d@");
                    break;
                case 5:
                case 6:
                    break;
            }
            String format = String.format(H, objArr);
            switch (a) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                default:
                    throw new IllegalArgumentException(format);
                case 5:
                case 6:
                    throw new IllegalStateException(format);
            }
        }

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        @NotNull
        private static TextAttributes AC(@NotNull Editor editor) {
            Color color;
            if (editor == null) {
                m169enum(4);
            }
            Color color2 = AICodeRequestSettings.settings().inlayTextColor;
            EditorColorsScheme colorsScheme = editor.getColorsScheme();
            TextAttributes textAttributes = null;
            try {
                textAttributes = colorsScheme.getAttributes((TextAttributesKey) Class.forName(OverlayUtils.H("\"\u0003&H.\u00048\u0004!\foAo\u00036\u000e*\b1\u0005h\u000e)\trD\u0013b\u0002\u000e\"\b4��2',\u000e\f3!\n,,5\u0016\t /\f,\u001d,\u0016\u0012\u0013`N/\u0003")).getField(RequestResultList.H("cSc[qNwr]HqBnTzrZNwZkVlJm\u001f7Ow")).get(null));
                color = color2;
            } catch (Exception e) {
                color = color2;
            }
            if (color != null || textAttributes == null || textAttributes.getForegroundColor() == null) {
                TextAttributes clone = textAttributes != null ? textAttributes.clone() : new TextAttributes();
                if (color2 != null) {
                    clone.setForegroundColor(color2);
                }
                if (clone.getForegroundColor() == null) {
                    clone.setForegroundColor(JBColor.GRAY);
                }
                if (clone == null) {
                    m169enum(6);
                }
                return clone;
            }
            TextAttributes textAttributes2 = textAttributes;
            if (textAttributes2 == null) {
                m169enum(5);
            }
            return textAttributes2;
        }

        public u(String a) {
            this.f323enum = a;
        }

        public int calcWidthInPixels(Inlay a) {
            return a.getEditor().getContentComponent().getFontMetrics(EditorColorsManager.getInstance().getGlobalScheme().getFont(EditorFontType.PLAIN)).stringWidth(this.f323enum) + 100;
        }

        public void paint(@NotNull Inlay inlay, @NotNull Graphics g, @NotNull Rectangle region, @NotNull TextAttributes a) {
            if (inlay == null) {
                m169enum(0);
            }
            if (g == null) {
                m169enum(1);
            }
            if (region == null) {
                m169enum(2);
            }
            if (a == null) {
                m169enum(3);
            }
            Editor editor = inlay.getEditor();
            if (editor.isDisposed()) {
                return;
            }
            InlayRendering.renderCodeBlock(editor, this.f323enum, Arrays.asList(this.f323enum), g, region, AC(editor));
        }
    }

    private void tf() {
        EditorFactory.getInstance().addEditorFactoryListener(new EditorFactoryListener() { // from class: com.aicode.inline.InlineChatInlay.01
            /* renamed from: enum, reason: not valid java name */
            private static /* synthetic */ void m167enum(int a) {
                throw new IllegalArgumentException(String.format(AICodeUtils.H("Gzkwmkeq*b.=&HO`wCsdm/ze3.KMujq-!-r(*kJ\u0002\"z %h5K]r{#cat6z.e1$2<"), PropertyUtils.H("4d }0"), AICodeUtils.H("Knb,loknko+ELk``e4\\HDhafNfabQ%)>(za"), PropertyUtils.H("~*p7{)Ot4s1v ")));
            }

            /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
            public void editorCreated(@NotNull EditorFactoryEvent a) {
                if (a == null) {
                    m167enum(0);
                }
                if (a.getEditor() != null && !StringUtils.isBlank(a.getEditor().toString()) && !a.getEditor().toString().contains(AICodeUtils.H("!$-kq\u007f")) && !a.getEditor().toString().contains(PropertyUtils.H("\u0013x.ko?V,u\""))) {
                    InlineChatInlay.INSTANCE.fe(a.getEditor());
                }
            }
        }, ApplicationManager.getApplication());
    }

    public final void register() {
        if (f318enum.compareAndSet(false, true)) {
            tf();
        }
    }

    public static void disposeInlay() {
        if (f317byte == null) {
            return;
        }
        f317byte.dispose();
        f317byte = null;
    }

    public void addInlay(Editor a) {
        CaretModel caretModel = a.getCaretModel();
        f317byte = a.getInlayModel().addInlineElement(a.getDocument().getLineEndOffset(a.getDocument().getLineNumber(caretModel.getOffset())), true, new u("  " + StrUtil.blankToDefault(KeymapUtil.getFirstKeyboardShortcutText(GenericUtils.H("\u0018/*)\u001e1>3<?<\u001f9$\u001b1#6 )")), KeymapUtil.getFirstMouseShortcutText(RequestTimeoutException.H("\u001f\u0001\u00139\u0014\u00128\u001c/\u0005?5:\u000e\u0018\u001b \u001c#\u0003"))) + "基于选中代码唤起对话"));
    }
}
