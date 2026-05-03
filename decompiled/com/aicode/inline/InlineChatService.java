/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.util.IdUtil
 *  com.intellij.openapi.Disposable
 *  com.intellij.openapi.application.ApplicationManager
 *  com.intellij.openapi.editor.Editor
 *  com.intellij.openapi.editor.Inlay
 *  com.intellij.openapi.editor.LogicalPosition
 *  com.intellij.openapi.editor.ScrollType
 *  com.intellij.openapi.fileEditor.FileDocumentManager
 *  com.intellij.openapi.project.Project
 *  com.intellij.openapi.vfs.VirtualFile
 *  com.intellij.openapi.wm.IdeFocusManager
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.aicode.inline;

import cn.hutool.core.util.IdUtil;
import com.aicode.PluginStartupActivity;
import com.aicode.action.batch.MethodGeneratorConfig;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.dto.MessageDto;
import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.service.InlineChatCommandService;
import com.aicode.exception.RequestCancelException;
import com.aicode.inline.InlineChatInlay;
import com.aicode.inline.InlineChatPanel;
import com.aicode.inline.KeyStrokeHandler;
import com.aicode.inline.dto.InlineChatInfo;
import com.aicode.inline.dto.LastChatQuestionInfo;
import com.aicode.inline.ide.ConditionalActionConfiguration;
import com.aicode.inline.ide.IdeEditorActionRouter;
import com.aicode.inline.status.InlineChatStatusService;
import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.util.AICodeUtils;
import com.aicode.util.Application;
import com.aicode.util.EditorKt;
import com.aicode.util.StringUtils;
import com.aicode.util.VirtualFileUtils;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.Inlay;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.IdeFocusManager;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.SwingUtilities;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class InlineChatService
implements Disposable {
    @NotNull
    private final InlineChatStatusService byte;
    @NotNull
    private static final Map<String, InlineChatPanel> enum = new ConcurrentHashMap<String, InlineChatPanel>();

    /*
     * WARNING - void declaration
     */
    private /* synthetic */ void ia(Editor editor, LastChatQuestionInfo lastChatQuestionInfo, VirtualFile virtualFile) {
        void a;
        void a2;
        InlineChatService a3 = editor;
        InlineChatService a4 = this;
        InlineChatService.cleanLastData((Editor)a3);
        SwingUtilities.invokeLater(() -> a4.SA((LastChatQuestionInfo)a2, (Editor)a3, (VirtualFile)a));
    }

    public static final void closeInlineChat(@NotNull Editor editor) {
        Editor editor2 = editor;
        if (editor2 == null) {
            InlineChatService.enum(1);
        }
        if (!enum.containsKey(InlineChatService.getVirtualFile(editor2).getUrl())) {
            return;
        }
        InlineChatPanel editor3 = enum.get(InlineChatService.getVirtualFile(editor2).getUrl());
        Inlay<?> inlay = editor3.getInlay();
        if (inlay != null) {
            inlay.dispose();
        }
        editor3.setInlay(null);
        Editor editor4 = editor2;
        editor4.getContentComponent().remove(editor3);
        editor4.getContentComponent().revalidate();
        editor4.getContentComponent().repaint();
        enum.remove(InlineChatService.getVirtualFile(editor2).getUrl());
    }

    public static void handleUndoAction(Editor editor) {
        InlineChatInfo a;
        block6: {
            Editor editor2 = editor;
            a = EditorKt.getInfoByEditor(editor2);
            if (a == null) {
                return;
            }
            if (a.getSessionController() != null) {
                a.getSessionController().setInlineChatOperateEnum(null);
            }
            try {
                InlineChatInlay.disposeInlay();
                if (a != null) break block6;
                return;
            }
            catch (Throwable throwable) {}
        }
        if (a.getSessionController() != null && a.getEditor() != null) {
            a.getSessionController().handleUndo(a.getEditor());
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    private void Ec(Editor editor, LastChatQuestionInfo lastChatQuestionInfo) {
        void a;
        void a2;
        InlineChatService inlineChatService = this;
        PluginWebsocketClient.sendWsMessage(CommandEnum.USER_MODEL_LIST, a2.getProject());
        void v0 = a2;
        v0.putUserData(InlineChatCommandService.RANGE_KEY, null);
        v0.putUserData(InlineChatCommandService.BODY_RANGE_KEY, null);
        InlineChatService a3 = InlineChatService.getVirtualFile((Editor)v0);
        if (a3 == null) {
            return;
        }
        String string = VirtualFileUtils.from((VirtualFile)a3);
        inlineChatService.byte.ifEnabledForFile(string, () -> inlineChatService.fA((Editor)a2, (LastChatQuestionInfo)a, (VirtualFile)a3));
    }

    public final void closeInlineChat(@NotNull InlineChatPanel inlineChatPanel) {
        InlineChatService inlineChatService = inlineChatPanel2;
        InlineChatPanel inlineChatPanel2 = inlineChatPanel;
        InlineChatService a = inlineChatService;
        if (inlineChatPanel2 == null) {
            InlineChatService.enum(0);
        }
        InlineChatService.closeInlineChat(inlineChatPanel2.getEditor());
    }

    /*
     * WARNING - void declaration
     */
    private /* synthetic */ Object fA(Editor editor, LastChatQuestionInfo lastChatQuestionInfo, VirtualFile virtualFile) {
        void a;
        void a2;
        InlineChatService a3 = virtualFile;
        InlineChatService a4 = this;
        Application.runOnEdtJava(() -> a4.ia((Editor)a2, (LastChatQuestionInfo)a, (VirtualFile)a3));
        return Unit.INSTANCE;
    }

    public static void cleanRender(Editor editor) {
        Editor editor2 = editor;
        InlineChatInfo a = EditorKt.getInfoByEditor(editor2);
        if (a != null) {
            if (a.getSessionController() != null) {
                a.getSessionController().setInlineChatOperateEnum(null);
            }
            InlineChatService.cleanLastData(editor2);
        }
    }

    /*
     * WARNING - void declaration
     */
    public InlineChatPanel getInlineChat(Editor editor) {
        void a;
        InlineChatService inlineChatService = this;
        InlineChatService a2 = InlineChatService.getVirtualFile((Editor)a);
        if (a2 == null) {
            return null;
        }
        a2.getUrl();
        return enum.get(InlineChatService.getVirtualFile((Editor)a).getUrl());
    }

    public static void cleanLastData(Editor editor) {
        Editor editor2 = editor;
        InlineChatInfo a = EditorKt.getInfoByEditor(editor2);
        if (a != null && a.getSessionController() == null) {
            InlineChatService.closeInlineChat(editor2);
        }
        InlineChatService.cleanLastData(a);
    }

    private void Ab() {
    }

    /*
     * WARNING - void declaration
     */
    private /* synthetic */ void SA(LastChatQuestionInfo lastChatQuestionInfo, Editor editor, VirtualFile virtualFile) {
        void a;
        void a2;
        LastChatQuestionInfo a222 = lastChatQuestionInfo;
        InlineChatService a3 = this;
        InlineChatInfo inlineChatInfo = new InlineChatInfo();
        if (a222 != null) {
            inlineChatInfo.setMessage(a222.getQuestion());
        }
        int a222 = EditorKt.inlineChatVersion.incrementAndGet();
        void v0 = a2;
        InlineChatInfo inlineChatInfo2 = inlineChatInfo;
        inlineChatInfo2.setInlineChatVersion(a222);
        EditorKt.addInfoByEditor((Editor)v0, inlineChatInfo2);
        a3.Ga((Editor)v0, (VirtualFile)a, a222);
    }

    /*
     * WARNING - void declaration
     */
    private void kb(Editor editor, VirtualFile virtualFile, int n, int n2, int n3, int n4, int n5) {
        void a;
        void a2;
        void a3;
        void a4;
        void a222;
        void a5;
        CodeInfoDto.RangeDTO a6;
        InlineChatService inlineChatService = this;
        MessageDto a7 = new MessageDto(IdUtil.fastSimpleUUID(), CommandEnum.INLINECHAT_GET_FUNC_RANGE.getType());
        a7.setPath(a6.getPath());
        CodeInfoDto.RangeDTO rangeDTO = a6 = new CodeInfoDto.RangeDTO();
        rangeDTO.setLine((int)a5);
        rangeDTO.setCharacter((int)a222);
        CodeInfoDto.RangeDTO rangeDTO2 = a5 = new CodeInfoDto.RangeDTO();
        rangeDTO2.setLine((int)a4);
        rangeDTO2.setCharacter((int)a3);
        ArrayList<CodeInfoDto.RangeDTO> a222 = new ArrayList<CodeInfoDto.RangeDTO>();
        a222.add(a6);
        a222.add(a5);
        Object object = a7;
        Object object2 = a7;
        a7.setRange(a222);
        ((MessageDto)object2).setContent(a2.getDocument().getText());
        ((MessageDto)object2).setOtherObject(inlineChatService);
        ((MessageDto)object).setInlineChatVersion((int)a);
        PluginWebsocketClient.sendWsMessage((MessageDto)object, a2.getProject());
    }

    public static VirtualFile getVirtualFile(Editor a) {
        return FileDocumentManager.getInstance().getFile(a.getDocument());
    }

    public void toggleInlineChat(Editor editor) {
        int n;
        InlineChatService inlineChatService;
        Object a = editor;
        InlineChatService a2 = this;
        InlineChatPanel inlineChatPanel = new InlineChatPanel(a2, (Editor)a);
        InlineChatService inlineChatService2 = a;
        a.getContentComponent().add(inlineChatPanel);
        inlineChatPanel.setInlineContainer(inlineChatService2.getContentComponent());
        if (inlineChatService2.getSelectionModel().hasSelection()) {
            InlineChatService inlineChatService3 = a;
            inlineChatService = inlineChatService3;
            n = inlineChatService3.getSelectionModel().getSelectionStart();
        } else {
            InlineChatService inlineChatService4 = a;
            inlineChatService = inlineChatService4;
            n = inlineChatService4.getCaretModel().getPrimaryCaret().getOffset();
        }
        InlineChatService.scrollToLines((Editor)inlineChatService, n, true);
        InlineChatInfo inlineChatInfo = EditorKt.getInfoByEditor((Editor)a);
        if (inlineChatInfo != null) {
            inlineChatPanel.getInlineChatInputPanel().getInputComponent().setText(inlineChatInfo.getMessage());
        }
        inlineChatPanel.createInlay(n);
        IdeFocusManager.getInstance((Project)a.getProject()).requestFocus((Component)((Object)inlineChatPanel.getChatInputPanel().getInputComponent()), true);
        a = InlineChatService.getVirtualFile((Editor)a).getUrl();
        enum.put((String)a, inlineChatPanel);
    }

    public void dispose() {
    }

    public InlineChatService() {
        InlineChatService a;
        InlineChatService inlineChatService = a;
        inlineChatService.byte = InlineChatStatusServiceKt.InlineChatStatusService();
        inlineChatService.byte.onGloballyDisabled((Function0<Unit>)((Function0)() -> {
            InlineChatService a;
            a.Ab();
            return Unit.INSTANCE;
        }));
        InlineChatService inlineChatService2 = a;
        new IdeEditorActionRouter(inlineChatService2, editor -> {
            InlineChatService a = editor;
            InlineChatService a2 = this;
            return InlineChatService.Sc(a2, (Editor)a);
        }).init();
    }

    private static KeyStrokeHandler Sc(InlineChatService inlineChatService, Editor editor) {
        InlineChatService a = editor;
        InlineChatService a2 = inlineChatService;
        return enum.get(InlineChatService.getVirtualFile((Editor)a).getUrl());
    }

    private static /* synthetic */ void enum(int a) {
        Object[] objectArray;
        String string = RequestCancelException.H("t S&\u0012}V+\u001f>R(\u0018\u001fq7@\u001d\ntt_O9O;U:K=Fs5PJy\u00108Cb'\u0016\u001avPdJ5K+\u0012;['\u00141ZxJ6P7");
        Object[] objectArray2 = new Object[3];
        switch (a) {
            default: {
                objectArray = objectArray2;
                objectArray2[0] = ConditionalActionConfiguration.H("\u0014\u0001\u0006\u0002\u0000\u00112\u0016\u001a\f>\u0017\u0010\u0003\u0019\u0006\b\u001e\u0001");
                break;
            }
            case 1: {
                objectArray = objectArray2;
                while (false) {
                }
                objectArray2[0] = RequestCancelException.H("Z<M7S)");
                break;
            }
        }
        objectArray[1] = ConditionalActionConfiguration.H("\u0012\u001c\u001bW\u001c78\u001a\u0014\u0019V\u0000\u0002\"\"\u0016\u0018@#\u0005\u0002\u001d\u001f\u001b8\u0010\u001c\f.\u0016\u0004\u001e\u0004\u0013\u0010");
        objectArray[2] = RequestCancelException.H("#T0A0}=X:Q=g+]/");
        throw new IllegalArgumentException(String.format(string, objectArray));
    }

    public static void cleanLastData(InlineChatInfo a) {
        block4: {
            try {
                InlineChatInlay.disposeInlay();
                if (a != null) break block4;
                return;
            }
            catch (Throwable throwable) {}
        }
        if (a.getSessionController() != null && a.getEditor() != null) {
            a.getSessionController().clear(a.getEditor());
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    private void Ga(Editor editor, VirtualFile virtualFile, int n) {
        void a;
        void a2;
        void a3;
        InlineChatService inlineChatService = this;
        void v0 = a3;
        InlineChatService a4 = v0.getDocument();
        if (v0.getSelectionModel().hasSelection()) {
            void v1 = a3;
            int n2 = v1.getSelectionModel().getSelectionStart();
            int n3 = v1.getSelectionModel().getSelectionEnd();
            int n4 = a4.getLineNumber(n2);
            int n5 = a4.getLineNumber(n3);
            int n6 = n2 - a4.getLineStartOffset(n4);
            int n7 = n3 - a4.getLineStartOffset(n5);
            inlineChatService.kb((Editor)a3, (VirtualFile)a2, n4, n6, n5, n7, (int)a);
            return;
        }
        int n8 = a3.getCaretModel().getOffset();
        int n9 = a4.getLineNumber(n8);
        int n10 = n8 - a4.getLineStartOffset(n9);
        int n11 = n9;
        inlineChatService.kb((Editor)a3, (VirtualFile)a2, n11, n10, n11, n10, (int)a);
    }

    public static void scrollToLines(Editor editor, int n, boolean bl) {
        int a;
        int a2 = n;
        Editor a3 = editor;
        if (a3 == null) {
            return;
        }
        Editor editor2 = a3;
        a2 = editor2.getDocument().getLineNumber(a2);
        Rectangle rectangle = editor2.getScrollingModel().getVisibleArea();
        if (a != 0) {
            Editor editor3 = a3;
            a = editor3.logicalPositionToOffset(editor3.xyToLogicalPosition(rectangle.getLocation()));
            int n2 = editor3.getDocument().getLineNumber(a);
            if (a2 < n2) {
                LogicalPosition logicalPosition = new LogicalPosition(a2, 0);
                a3.getScrollingModel().scrollTo(logicalPosition, ScrollType.CENTER_UP);
                return;
            }
        } else {
            Editor editor4 = a3;
            Rectangle rectangle2 = rectangle;
            Rectangle rectangle3 = rectangle;
            a = a3.logicalPositionToOffset(editor4.xyToLogicalPosition(new Point(rectangle2.x + rectangle2.width, rectangle3.y + rectangle3.height)));
            int n3 = editor4.getDocument().getLineNumber(a);
            if (a2 > n3) {
                LogicalPosition logicalPosition = new LogicalPosition(a2, 0);
                a3.getScrollingModel().scrollTo(logicalPosition, ScrollType.CENTER_UP);
            }
        }
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    public static final class Companion {
        public static void closeInlineChat(@NotNull InlineChatPanel inlineChatComponent) {
            if (inlineChatComponent == null) {
                Companion.enum(5);
            }
            Companion.ra().closeInlineChat(inlineChatComponent);
        }

        private static InlineChatService ra() {
            InlineChatService inlineChatService = (InlineChatService)ApplicationManager.getApplication().getService(InlineChatService.class);
            if (inlineChatService != null) {
                return inlineChatService;
            }
            throw new RuntimeException("Cannot find service " + InlineChatService.class.getName() + " (classloader: " + InlineChatService.class.getClassLoader());
        }

        public static void removeFlag(@NotNull Editor editor) {
            if (editor == null) {
                Companion.enum(6);
            }
            Companion.ra();
            if (enum.containsKey(InlineChatService.getVirtualFile(editor).getUrl())) {
                EditorKt.removeEditor(editor);
            }
        }

        @Nullable
        public InlineChatPanel getInlineChat(@NotNull Editor editor) {
            Companion companion = companion2;
            Companion companion2 = editor;
            Companion a = companion;
            if (companion2 == null) {
                Companion.enum(0);
            }
            return Companion.ra().getInlineChat((Editor)companion2);
        }

        private static /* synthetic */ void enum(int a) {
            Object[] objectArray;
            Object[] objectArray2;
            String string = MethodGeneratorConfig.H("<\u0001=!533'z2>-W9\u0012=!\u00155\"\u000bI!>4)/))6%yvzc9W\u0016=u|$jo&{7!.'{;5 q=?t/:5;");
            Object[] objectArray3 = new Object[3];
            switch (a) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = AICodeUtils.H("clizn}");
                    break;
                }
                case 3: {
                    objectArray2 = objectArray3;
                    while (false) {
                    }
                    objectArray3[0] = MethodGeneratorConfig.H("7 ,12");
                    break;
                }
                case 5: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = AICodeUtils.H(",BNi`gOwpzCnbvgnko{");
                    break;
                }
            }
            objectArray2[1] = MethodGeneratorConfig.H("8/#H\b8<),'c4=;0?:?W\u0019\u00152;<\u0014,+!\b?&+:80~\u0017>2*5/&69");
            switch (a) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[2] = AICodeUtils.H("kzeGnmfhmCf`{");
                    break;
                }
                case 1: 
                case 2: 
                case 3: {
                    objectArray = objectArray2;
                    while (false) {
                    }
                    objectArray2[2] = MethodGeneratorConfig.H("2#>;\u0013:=641\u0002'8#");
                    break;
                }
                case 4: 
                case 5: {
                    objectArray = objectArray2;
                    objectArray2[2] = AICodeUtils.H("mncltGnmfhmCf`{");
                    break;
                }
                case 6: {
                    objectArray = objectArray2;
                    objectArray2[2] = MethodGeneratorConfig.H("(1<0,1\u0007#80");
                    break;
                }
            }
            throw new IllegalArgumentException(String.format(string, objectArray));
        }

        public static void closeInlineChat(@NotNull Editor editor) {
            if (editor == null) {
                Companion.enum(4);
            }
            Companion.ra();
            InlineChatService.closeInlineChat(editor);
        }

        public static void openInlineChat(@NotNull Editor editor, @NotNull LastChatQuestionInfo lastChatQuestionInfo) {
            Object editor2 = lastChatQuestionInfo;
            Editor cache = editor;
            if (cache == null) {
                Companion.enum(2);
            }
            if (editor2 == null) {
                Companion.enum(3);
            }
            Companion.ra().Ec(cache, (LastChatQuestionInfo)editor2);
        }

        public static void openInlineChat(@NotNull Editor editor) {
            if (editor == null) {
                Companion.enum(1);
            }
            if (StringUtils.isBlank((CharSequence)PluginStartupActivity.getApiKey())) {
                return;
            }
            if (!AICodeUtils.hasInlineChat()) {
                return;
            }
            if (!AICodeSettingsState.getInstance().openInlineChat) {
                return;
            }
            Companion.ra().Ec(editor, null);
        }

        private Companion() {
            Companion a;
        }
    }
}
