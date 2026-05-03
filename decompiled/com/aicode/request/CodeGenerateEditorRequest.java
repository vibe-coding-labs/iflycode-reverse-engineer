/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.intellij.openapi.Disposable
 *  com.intellij.openapi.diagnostic.Logger
 *  com.intellij.openapi.editor.Document
 *  com.intellij.openapi.editor.Editor
 *  com.intellij.openapi.fileEditor.FileEditorManager
 *  com.intellij.openapi.project.Project
 *  com.intellij.openapi.util.Disposer
 *  com.intellij.psi.PsiDocumentManager
 *  com.intellij.psi.PsiFile
 *  org.apache.commons.collections.MapUtils
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.aicode.request;

import com.aicode.agent.service.CodeCompleteService;
import com.aicode.domain.LineInfo;
import com.aicode.domain.VirtualFileUri;
import com.aicode.enums.TipType;
import com.aicode.inline.controller.SessionController;
import com.aicode.language.AICodeLanguageInfo;
import com.aicode.language.LanguageInfoManager;
import com.aicode.request.RequestId;
import com.aicode.service.EditorRequestService;
import com.aicode.service.editor.EditorUtil;
import com.aicode.util.EditorCacheUtil;
import com.aicode.util.IndentLineUtil;
import com.google.common.collect.Maps;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.collections.MapUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class CodeGenerateEditorRequest
implements EditorRequestService,
Disposable {
    private final TipType break;
    private boolean class;
    private int true;
    private final String this;
    private final long else;
    private static final Logger char = Logger.getInstance(CodeGenerateEditorRequest.class);
    private final boolean int;
    private final String new;
    private final int long;
    private volatile boolean super;
    private final Project for;
    private final String if;
    @NotNull
    private final LineInfo case;
    private final AICodeLanguageInfo final;
    @NotNull
    private final VirtualFileUri try;
    private SessionController float;
    private final int byte;
    private final long enum;

    @Override
    public long getRequestTimestamp() {
        CodeGenerateEditorRequest a;
        return a.else;
    }

    public void dispose() {
        char.debug(CodeCompleteService.H("YMeKuJOM|PeLn\u0012}JuOuZi"));
        a.super = true;
    }

    @NotNull
    public VirtualFileUri getUri() {
        CodeGenerateEditorRequest a;
        VirtualFileUri virtualFileUri = a.try;
        if (virtualFileUri == null) {
            CodeGenerateEditorRequest.enum(2);
        }
        return virtualFileUri;
    }

    @Override
    public String getFileNameSuffix() {
        CodeGenerateEditorRequest a;
        return a.new;
    }

    @Override
    public void cancel() {
        CodeGenerateEditorRequest a;
        if (!a.super) {
            a.super = true;
            Disposer.dispose((Disposable)a);
        }
    }

    @Override
    public int getOffset() {
        CodeGenerateEditorRequest a;
        return a.true;
    }

    @Override
    public boolean isCancelled() {
        CodeGenerateEditorRequest a;
        return a.super;
    }

    @Override
    public int getRequestId() {
        CodeGenerateEditorRequest a;
        return a.long;
    }

    @Override
    public boolean isUseTabIndents() {
        CodeGenerateEditorRequest a;
        return a.int;
    }

    public String toString() {
        CodeGenerateEditorRequest a;
        return "CodeGenerateEditorRequest(completionType=" + a.getCompletionType() + ", useTabIndents=" + a.isUseTabIndents() + ", tabWidth=" + a.getTabWidth() + ", requestId=" + a.getRequestId() + ", fileLanguage=" + a.getFileLanguage() + ", uri=" + a.getUri() + ", documentContent=" + a.getDocumentContent() + ", offset=" + a.getOffset() + ", lineInfo=" + a.getLineInfo() + ", requestTimestamp=" + a.getRequestTimestamp() + ", documentModificationSequence=" + a.getDocumentModificationSequence() + ", isCancelled=" + a.isCancelled() + ")";
    }

    @Override
    public Project getProject() {
        CodeGenerateEditorRequest a;
        return a.for;
    }

    /*
     * WARNING - void declaration
     */
    public CodeGenerateEditorRequest(Project project, TipType tipType, boolean bl, int n, int n2, AICodeLanguageInfo aICodeLanguageInfo, @NotNull VirtualFileUri virtualFileUri, String string, int n3, @NotNull LineInfo lineInfo, long l, String string2, String string3, boolean bl2) {
        void a;
        void a2;
        void fileName;
        void documentModificationSequence;
        void offset;
        void documentContent;
        void fileLanguage;
        void requestId;
        void tabWidth;
        void useTabIndents;
        void completionType;
        void project2;
        void uri;
        CodeGenerateEditorRequest codeGenerateEditorRequest = lineInfo2;
        LineInfo lineInfo2 = lineInfo;
        CodeGenerateEditorRequest lineInfo3 = codeGenerateEditorRequest;
        if (uri == null) {
            CodeGenerateEditorRequest.enum(0);
        }
        if (lineInfo2 == null) {
            CodeGenerateEditorRequest.enum(1);
        }
        CodeGenerateEditorRequest codeGenerateEditorRequest2 = lineInfo3;
        CodeGenerateEditorRequest codeGenerateEditorRequest3 = lineInfo3;
        CodeGenerateEditorRequest codeGenerateEditorRequest4 = lineInfo3;
        CodeGenerateEditorRequest codeGenerateEditorRequest5 = lineInfo3;
        CodeGenerateEditorRequest codeGenerateEditorRequest6 = lineInfo3;
        CodeGenerateEditorRequest codeGenerateEditorRequest7 = lineInfo3;
        lineInfo3.else = System.currentTimeMillis();
        lineInfo3.for = project2;
        codeGenerateEditorRequest7.break = completionType;
        codeGenerateEditorRequest7.int = useTabIndents;
        codeGenerateEditorRequest6.byte = tabWidth;
        codeGenerateEditorRequest6.long = requestId;
        codeGenerateEditorRequest5.final = fileLanguage;
        codeGenerateEditorRequest5.try = uri;
        codeGenerateEditorRequest4.this = documentContent;
        codeGenerateEditorRequest4.true = offset;
        codeGenerateEditorRequest3.case = lineInfo2;
        codeGenerateEditorRequest3.enum = documentModificationSequence;
        codeGenerateEditorRequest2.if = fileName;
        codeGenerateEditorRequest2.new = a2;
        lineInfo3.class = a;
    }

    @Override
    public Disposable getDisposable() {
        CodeGenerateEditorRequest a;
        return a;
    }

    @Override
    public String getDocumentContent() {
        CodeGenerateEditorRequest a;
        return a.this;
    }

    @Override
    public boolean equalsRequest(@NotNull EditorRequestService editorRequestService) {
        CodeGenerateEditorRequest codeGenerateEditorRequest = editorRequestService2;
        EditorRequestService editorRequestService2 = editorRequestService;
        CodeGenerateEditorRequest a = codeGenerateEditorRequest;
        if (editorRequestService2 == null) {
            CodeGenerateEditorRequest.enum(6);
        }
        if (a.long == editorRequestService2.getRequestId()) {
            return true;
        }
        return false;
    }

    @Override
    public AICodeLanguageInfo getFileLanguage() {
        CodeGenerateEditorRequest a;
        return a.final;
    }

    public static Map<String, String> getFileExtension(Project project) {
        Project project2 = project;
        Object a = FileEditorManager.getInstance((Project)project2).getSelectedEditor();
        if (Objects.isNull(a)) {
            return Collections.emptyMap();
        }
        if (Objects.isNull(a = a.getFile())) {
            return Collections.emptyMap();
        }
        a = a.getName();
        HashMap hashMap = Maps.newHashMap();
        int n = ((String)a).lastIndexOf(IndentLineUtil.H("\u001f"));
        String string = hashMap.put(CodeCompleteService.H("YsIemgOb"), ((String)a).substring(0, n));
        a = ((String)a).substring(n + 1);
        HashMap hashMap2 = hashMap;
        hashMap2.put(IndentLineUtil.H(".~\u0013E%U\u0002U,U\u000fP\u0007I"), a);
        return hashMap2;
    }

    @Override
    public SessionController getSessionController() {
        CodeGenerateEditorRequest a;
        return a.float;
    }

    @Override
    @NotNull
    public LineInfo getLineInfo() {
        CodeGenerateEditorRequest a;
        LineInfo lineInfo = a.case;
        if (lineInfo == null) {
            CodeGenerateEditorRequest.enum(3);
        }
        return lineInfo;
    }

    @Override
    public boolean isSelected() {
        CodeGenerateEditorRequest a;
        return a.class;
    }

    @Override
    public int getTabWidth() {
        CodeGenerateEditorRequest a;
        return a.byte;
    }

    @Override
    public String getFileName() {
        CodeGenerateEditorRequest a;
        return a.if;
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public static EditorRequestService create(@NotNull Editor editor, int n, @NotNull TipType tipType) {
        Project project;
        void completionType;
        Editor editor2 = editor;
        if (editor2 == null) {
            CodeGenerateEditorRequest.enum(4);
        }
        if (completionType == null) {
            CodeGenerateEditorRequest.enum(5);
        }
        if ((project = editor2.getProject()) == null) {
            return null;
        }
        Document document = editor2.getDocument();
        PsiFile psiFile = PsiDocumentManager.getInstance((Project)project).getPsiFile(document);
        if (psiFile == null) {
            return null;
        }
        try {
            void offset;
            Editor editor3 = editor2;
            boolean bl = editor3.getSettings().isUseTabCharacter(project);
            int n2 = editor3.getSettings().getTabSize(project);
            LineInfo lineInfo = LineInfo.create(document, (int)offset);
            VirtualFileUri virtualFileUri = VirtualFileUri.from(psiFile.getVirtualFile());
            Map<String, String> editor222 = CodeGenerateEditorRequest.getFileExtension(project);
            String string = "";
            String string2 = "";
            if (MapUtils.isNotEmpty((Map)editor222) && editor222.containsKey(CodeCompleteService.H("OeAmv|Di")) && editor222.containsKey(IndentLineUtil.H(".~\u0013E%U\u0002U,U\u000fP\u0007I"))) {
                string = (String)editor222.get(CodeCompleteService.H("OeAmv|Di"));
                string2 = (String)editor222.get(IndentLineUtil.H(".~\u0013E%U\u0002U,U\u000fP\u0007I"));
            }
            boolean editor222 = editor2.getSelectionModel().hasSelection();
            if (Boolean.TRUE.equals(EditorCacheUtil.getEditCache(editor2))) {
                editor222 = true;
            }
            return new CodeGenerateEditorRequest(project, (TipType)completionType, bl, n2, RequestId.incrementAndGet(), LanguageInfoManager.findLanguageMapping(psiFile), virtualFileUri, document.getText(), (int)offset, lineInfo, EditorUtil.getDocumentModificationStamp(document), string, string2, editor222);
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public void setSessionController(SessionController sessionController) {
        Disposable a = sessionController;
        CodeGenerateEditorRequest a2 = this;
        a2.float = a;
    }

    public boolean canEqual(Object object) {
        Object a = object;
        CodeGenerateEditorRequest a2 = this;
        return a instanceof CodeGenerateEditorRequest;
    }

    @Override
    public long getDocumentModificationSequence() {
        CodeGenerateEditorRequest a;
        return a.enum;
    }

    @Override
    public TipType getCompletionType() {
        CodeGenerateEditorRequest a;
        return a.break;
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
                string = CodeCompleteService.H("yo\u00052bOMr\bkF~+ngcVI\\`R;]iJ|Di}IZ-\u0002%K:\u0003iX;;H\u0007)N8TiZx\u001ftWi\tnG'WiMh");
                n4 = a;
                break;
            }
            case 2: 
            case 3: {
                string = IndentLineUtil.H("\u001dL\u0010T:^\u0013LHZ\u001eP\u0006^\u001b\u0000zsP\u0004\u0000\f\u0003D\u0006^HY'c_R\u000e@\u001aB\u0011\u0000\u0007C\u0002]");
                n4 = a;
                break;
            }
        }
        switch (n4) {
            default: {
                n3 = 3;
                break;
            }
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
                objectArray3[0] = CodeCompleteService.H("iSm");
                n2 = a;
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = IndentLineUtil.H("\u0003Y\u0011E X\b^");
                n2 = a;
                break;
            }
            case 2: 
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = CodeCompleteService.H("JcS4La[rMi&^M|PeKi\fEQ\u007f{|LbXjXhLI[sLr[^GvLyRp");
                n2 = a;
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = IndentLineUtil.H("\u001aD\u0000B\u0001C");
                n2 = a;
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = CodeCompleteService.H("JcRjTx]eMimeQa");
                n2 = a;
                break;
            }
            case 6: {
                objectArray2 = objectArray3;
                objectArray3[0] = IndentLineUtil.H("^");
                n2 = a;
                break;
            }
        }
        switch (n2) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = CodeCompleteService.H("JcS4La[rMi&^M|PeKi\fEQ\u007f{|LbXjXhLI[sLr[^GvLyRp");
                n = a;
                break;
            }
            case 2: {
                objectArray = objectArray2;
                objectArray2[1] = IndentLineUtil.H("\u0018E\u001dc\u001cX");
                n = a;
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = CodeCompleteService.H("}]ieeLbprGk");
                n = a;
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = IndentLineUtil.H("CI\u0007_\u001a\u000f");
                break;
            }
            case 2: 
            case 3: {
                break;
            }
            case 4: 
            case 5: {
                objectArray = objectArray;
                objectArray[2] = CodeCompleteService.H("Au\\}Ua");
                break;
            }
            case 6: {
                objectArray = objectArray;
                objectArray[2] = IndentLineUtil.H("r\u000eU\nX\u001cb\u001aQ\u001cS\u001dE");
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (a) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                throw runtimeException;
            }
            case 2: 
            case 3: 
        }
        runtimeException = new IllegalStateException(string2);
        throw runtimeException;
    }

    @Override
    public void setOffset(int n) {
        int a = n;
        CodeGenerateEditorRequest a2 = this;
        a2.true = a;
    }

    public void setSelected(boolean bl) {
        boolean a = bl;
        CodeGenerateEditorRequest a2 = this;
        a2.class = a;
    }

    public void setCancelled(boolean bl) {
        boolean a = bl;
        CodeGenerateEditorRequest a2 = this;
        a2.super = a;
    }
}
