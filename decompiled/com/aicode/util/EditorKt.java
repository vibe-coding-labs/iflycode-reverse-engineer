package com.aicode.util;

import com.aicode.inline.InlineChatService;
import com.aicode.inline.dto.InlineChatInfo;
import com.aicode.inline.render.InlineChatBtnPanelRenderer;
import com.aicode.inline.render.InlineChatCategoryPanelRenderer;
import com.aicode.inline.render.InlineChatErrorPanelRenderer;
import com.aicode.inline.render.InlineChatStopPanelRenderer;
import com.aicode.message.BasicActionsBundle;
import com.aicode.service.EditorRequestService;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.Inlay;
import com.intellij.openapi.editor.event.CaretListener;
import com.intellij.openapi.editor.event.SelectionListener;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.annotations.NotNull;

/* compiled from: vb */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/util/EditorKt.class */
public class EditorKt {
    public static final /* synthetic */ AtomicInteger inlineChatVersion = new AtomicInteger(0);
    public static final /* synthetic */ Map<String, InlineChatInfo> inlineChatCacheData = new ConcurrentHashMap();
    public static final /* synthetic */ Map<Editor, Inlay<?>> inlineChatBtnCache = new ConcurrentHashMap();

    @NotNull
    public static final /* synthetic */ Map<String, Object> rendererCollection = new ConcurrentHashMap();

    @NotNull
    public static final /* synthetic */ Map<String, InlineChatStopPanelRenderer> stopRendererCollection = new ConcurrentHashMap();

    @NotNull
    public static final /* synthetic */ Map<String, InlineChatCategoryPanelRenderer> categoryRendererCollection = new ConcurrentHashMap();
    public static final /* synthetic */ Map<String, String> commentSymbols = Map.of(Maps.H("\u001f(Ib"), PropertyUtils.H("y."), Maps.H("?Jf"), PropertyUtils.H("y."), Maps.H("\u00025\u00192\u001b7\u0007 Ow"), PropertyUtils.H("y."), Maps.H("\u0018-\u0001!Pm"), PropertyUtils.H("\""), Maps.H("`"), PropertyUtils.H("y."), Maps.H("*\u0014("), PropertyUtils.H("y."), Maps.H("\u001d=Ro"), PropertyUtils.H(";H\u0013{!"), Maps.H("*Lp"), PropertyUtils.H("\u0011|!"), Maps.H("\u001c-\u001f6\u001b7\u0007 Ow"), PropertyUtils.H("y."));
    public static final /* synthetic */ Map<String, String> endSymbols = Map.of(Maps.H("\u001d=Ro"), PropertyUtils.H("I\u0013{?"), Maps.H("*Lp"), PropertyUtils.H("\u001e|."));
    public static final /* synthetic */ Map<String, String> FILE_LANG = Map.ofEntries(Map.entry(Maps.H("\u001f(Ib"), PropertyUtils.H("#_ `")), Map.entry(Maps.H("Up"), PropertyUtils.H("\u001c`;{\u0003d\u001bW&u")), Map.entry(Maps.H("$Up"), PropertyUtils.H("\u001c`;{\u0003d\u001bW&u")), Map.entry(Maps.H("*Up"), PropertyUtils.H("\u001c`;{\u0003d\u001bW&u")), Map.entry(Maps.H("\u001f:Pm"), PropertyUtils.H("\u001c`;{\u0003d\u001bW&u")), Map.entry(Maps.H("Kp"), PropertyUtils.H("\u0002x=\u007f\u0003d\u001bW&u")), Map.entry(Maps.H("=L{"), PropertyUtils.H("j\u0005Y")), Map.entry(Maps.H("`"), PropertyUtils.H("B")), Map.entry(Maps.H("\\`"), PropertyUtils.H("}}*")), Map.entry(Maps.H("*Os"), PropertyUtils.H("}}*")), Map.entry(Maps.H("!Os"), PropertyUtils.H("}}*")), Map.entry(Maps.H("k"), PropertyUtils.H("}}*")), Map.entry(Maps.H("!G{"), PropertyUtils.H("}}*")), Map.entry(Maps.H("@"), PropertyUtils.H("}}*")), Map.entry(Maps.H("*G{"), PropertyUtils.H("}}*")), Map.entry(Maps.H("\\p"), PropertyUtils.H("\u0015\"")), Map.entry(Maps.H("\u001d=Ro"), PropertyUtils.H("!j\u001bM")), Map.entry(Maps.H("!Kn"), PropertyUtils.H("!j\u001bM")), Map.entry(Maps.H("9Ws"), PropertyUtils.H("n\u001eQ")), Map.entry(Maps.H("Oz"), PropertyUtils.H("��~\u001dV9o")), Map.entry(Maps.H(":No"), PropertyUtils.H("m\u0007M")), Map.entry(Maps.H("Ma"), PropertyUtils.H(";K4x")), Map.entry(Maps.H("'\u0002 Yw"), PropertyUtils.H("T\u001eW0u")), Map.entry(Maps.H("Xl"), PropertyUtils.H("\u0011n")), Map.entry(Maps.H("\u0007<Lw"), PropertyUtils.H(";K%u")), Map.entry(Maps.H("\u0003;\u0001%Vm"), PropertyUtils.H("\u001bh\u001dR?o")), Map.entry(Maps.H("'\u0016(Sb"), PropertyUtils.H("T\n_:`")), Map.entry(Maps.H("\u0005,Mo"), PropertyUtils.H("9[$m")), Map.entry(Maps.H("%Jb"), PropertyUtils.H("r#`")), Map.entry(Maps.H("q"), PropertyUtils.H("S")), Map.entry(Maps.H("\u0011(Mw"), PropertyUtils.H("-_$u")), Map.entry(Maps.H("\u000f&\u001a&Iz"), PropertyUtils.H("\u0017u\u0006Q x")), Map.entry(Maps.H("\u0017(Lk"), PropertyUtils.H("+_%i")), Map.entry(Maps.H("1Ro"), PropertyUtils.H("f\u001bM")), Map.entry(Maps.H("?Jf"), PropertyUtils.H("h#d")), Map.entry(Maps.H("#L{"), PropertyUtils.H("U\f_5u")), Map.entry(Maps.H("*Lp"), PropertyUtils.H("}%r")));

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: enum */
    private static /* synthetic */ void m399enum(int a) {
        String H = Maps.H(".!\u0013=\u00015\f*N4\u0003\"\u000fS.3\u001c\u001a\u00172\u0002r\u00158\u00169Bv��-\u0007iOq\u001ev\u000f|\"X@*Fq\u0006i\u0003'��;j\u0018\u001a=D:\ns\u001a=��<");
        Object[] objArr = new Object[3];
        switch (a) {
            case 0:
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            default:
                objArr[0] = PropertyUtils.H("5c��J9s");
                break;
            case 1:
            case 3:
                do {
                } while (0 != 0);
                objArr[0] = Maps.H("\b1\u001c'\u0011&\t\"");
                break;
        }
        objArr[1] = PropertyUtils.H("5n&3/po4\u0003Uid?u:.\b~9s\u0006L\u001du");
        switch (a) {
            case 0:
            case 1:
            default:
                objArr[2] = Maps.H("\t0\u0011\u001a\u000b>\u0016,>\u001f\u001a'(1\u001c'\u0011&\t\"");
                break;
            case 2:
            case 3:
                do {
                } while (0 != 0);
                objArr[2] = PropertyUtils.H(":\u0003T\u0005d9o9s\u0001s#s\fP3s");
                break;
            case 4:
                objArr[2] = Maps.H("5\u0016;\u0002\u0017\u0006\u001a\u00014\n0��!\u0003>");
                break;
            case 5:
                objArr[2] = PropertyUtils.H("%}$o3R9u W\bP3m");
                break;
            case 6:
                objArr[2] = Maps.H("\u0016%\u0001!\u0016\f+\u0002\u0010.\u000b*\u0016\u0003\u0015&\t<");
                break;
            case 7:
                objArr[2] = PropertyUtils.H("\u0004\\)b.^#u9u>W\bP3m");
                break;
        }
        throw new IllegalArgumentException(String.format(H, objArr));
    }

    public static /* synthetic */ void addCursorListener(@NotNull Editor editor, @NotNull CaretListener listener) {
        if (editor == null) {
            m399enum(2);
        }
        if (listener == null) {
            m399enum(3);
        }
        editor.getCaretModel().addCaretListener(listener);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ String getErrorRendererTip(Editor a, EditorRequestService editorRequestService) {
        String fileType = getFileType(a);
        if (fileType.isEmpty()) {
            return null;
        }
        return "    " + ((commentSymbols.get(fileType) == null ? Maps.H("^b") : commentSymbols.get(fileType)) + BasicActionsBundle.message(PropertyUtils.H("2\t\\/\u007f.25i,n~b\u001bL9s"), new Object[0]) + (endSymbols.get(fileType) == null ? "" : endSymbols.get(fileType)));
    }

    public static /* synthetic */ boolean getHasSelection(@NotNull Editor editor) {
        if (editor == null) {
            m399enum(4);
        }
        return editor.getSelectionModel().hasSelection();
    }

    public static /* synthetic */ void addSelectionListener(@NotNull Editor editor, @NotNull SelectionListener listener) {
        if (editor == null) {
            m399enum(0);
        }
        if (listener == null) {
            m399enum(1);
        }
        editor.getSelectionModel().addSelectionListener(listener);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ void closeStopPanel(@NotNull Editor editor) {
        if (editor == null) {
            m399enum(5);
        }
        InlineChatStopPanelRenderer inlineChatStopPanelRenderer = stopRendererCollection.get(InlineChatService.getVirtualFile(editor).getUrl());
        if (inlineChatStopPanelRenderer != null) {
            Inlay<?> inlay = inlineChatStopPanelRenderer.getInlay();
            if (inlay != null) {
                inlay.dispose();
            }
            inlineChatStopPanelRenderer.setInlay(null);
            editor.getContentComponent().remove(inlineChatStopPanelRenderer);
            editor.getContentComponent().revalidate();
            editor.getContentComponent().repaint();
            stopRendererCollection.remove(InlineChatService.getVirtualFile(editor).getUrl());
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ void closeButtonPanel(@NotNull Editor editor) {
        Editor editor2;
        if (editor == null) {
            m399enum(7);
        }
        Object obj = rendererCollection.get(InlineChatService.getVirtualFile(editor).getUrl());
        if (obj != null) {
            if (!(obj instanceof InlineChatBtnPanelRenderer)) {
                if (obj instanceof InlineChatErrorPanelRenderer) {
                    InlineChatErrorPanelRenderer inlineChatErrorPanelRenderer = (InlineChatErrorPanelRenderer) obj;
                    if (inlineChatErrorPanelRenderer == null) {
                        return;
                    }
                    Inlay<?> inlay = inlineChatErrorPanelRenderer.getInlay();
                    if (inlay != null) {
                        inlay.dispose();
                    }
                    inlineChatErrorPanelRenderer.setInlay(null);
                    editor.getContentComponent().remove(inlineChatErrorPanelRenderer);
                }
                editor2 = editor;
            } else {
                InlineChatBtnPanelRenderer inlineChatBtnPanelRenderer = (InlineChatBtnPanelRenderer) obj;
                if (inlineChatBtnPanelRenderer == null) {
                    return;
                }
                Inlay<?> inlay2 = inlineChatBtnPanelRenderer.getInlay();
                if (inlay2 != null) {
                    inlay2.dispose();
                }
                inlineChatBtnPanelRenderer.setInlay(null);
                editor2 = editor;
                editor2.getContentComponent().remove(inlineChatBtnPanelRenderer);
            }
            editor2.getMarkupModel().removeAllHighlighters();
            editor.getContentComponent().revalidate();
            editor.getContentComponent().repaint();
            rendererCollection.remove(InlineChatService.getVirtualFile(editor).getUrl());
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ String getFileType(Editor a) {
        VirtualFile file = FileDocumentManager.getInstance().getFile(a.getDocument());
        if (file != null) {
            String name = file.getName();
            String str = "";
            int lastIndexOf = name.lastIndexOf(46);
            if (lastIndexOf != -1 && lastIndexOf < name.length() - 1) {
                str = name.substring(lastIndexOf + 1);
            }
            return FILE_LANG.getOrDefault(str, "").toLowerCase();
        }
        return "";
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ void closeCategoryPanel(@NotNull Editor editor) {
        if (editor == null) {
            m399enum(6);
        }
        InlineChatCategoryPanelRenderer inlineChatCategoryPanelRenderer = categoryRendererCollection.get(InlineChatService.getVirtualFile(editor).getUrl());
        if (inlineChatCategoryPanelRenderer == null) {
            return;
        }
        Inlay<?> inlay = inlineChatCategoryPanelRenderer.getInlay();
        if (inlay != null) {
            inlay.dispose();
        }
        inlineChatCategoryPanelRenderer.setInlay(null);
        editor.getContentComponent().remove(inlineChatCategoryPanelRenderer);
        editor.getContentComponent().revalidate();
        editor.getContentComponent().repaint();
        categoryRendererCollection.remove(InlineChatService.getVirtualFile(editor).getUrl());
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ InlineChatInfo getInfoByVirtualFile(VirtualFile a) {
        if (a != null && inlineChatCacheData.containsKey(a.getPath())) {
            return inlineChatCacheData.get(a.getPath());
        }
        return null;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ void addInfoByEditor(Editor a, InlineChatInfo a2) {
        VirtualFile virtualFile;
        if (a == null || (virtualFile = ((EditorImpl) a).getVirtualFile()) == null) {
            return;
        }
        inlineChatCacheData.put(virtualFile.getPath(), a2);
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ InlineChatInfo getInfoByEditor(Editor a) {
        VirtualFile virtualFile;
        if (a == null || (virtualFile = ((EditorImpl) a).getVirtualFile()) == null || !inlineChatCacheData.containsKey(virtualFile.getPath())) {
            return null;
        }
        return inlineChatCacheData.get(virtualFile.getPath());
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ boolean containEditor(Editor a) {
        VirtualFile virtualFile;
        if (a != null && (virtualFile = ((EditorImpl) a).getVirtualFile()) != null) {
            return inlineChatCacheData.containsKey(virtualFile.getPath());
        }
        return false;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static /* synthetic */ void removeEditor(Editor a) {
        VirtualFile virtualFile;
        if (a != null && (virtualFile = ((EditorImpl) a).getVirtualFile()) != null) {
            String path = virtualFile.getPath();
            if (!inlineChatCacheData.containsKey(path)) {
                return;
            }
            inlineChatCacheData.remove(path);
        }
    }
}
