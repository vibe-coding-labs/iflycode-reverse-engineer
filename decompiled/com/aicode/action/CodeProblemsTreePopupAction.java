package com.aicode.action;

import com.aicode.action.click.PluginAnAction;
import com.aicode.agent.service.CommonService;
import com.aicode.diff.GenericUtils;
import com.aicode.error.search.Presentation;
import com.aicode.message.BasicActionsBundle;
import com.aicode.util.IndentLineUtil;
import com.aicode.util.ReflectUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.ui.treeStructure.Tree;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: ik */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/CodeProblemsTreePopupAction.class */
public class CodeProblemsTreePopupAction extends PluginAnAction {

    /* renamed from: enum, reason: not valid java name */
    private static Logger f7enum = Logger.getInstance(CodeProblemsTreePopupAction.class);

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m16enum(int a) {
        throw new IllegalArgumentException(String.format(IndentLineUtil.H("2^\u0012_\u0019NZ\u001fy`\u001aX^a1O>[\u001d[\u0019\n\bF\u0004H\u0012E\u0003M\u001b\u0016i4\u001b\u0010_O\u0019��of[\u000f\u0019\u0015\u0002E\u0019A{j\u0016RNS\u0016\f\u001b_\u0018G"), GenericUtils.H("67\u0010;$:46\u00155.5'"), IndentLineUtil.H("v\u0007ZZK\u0011D\u0019M\u001a\u000f\u0016K\u001d_!\u007fGt\u0010D\u001ap8z\u0017F\u000fX\u001cd\u0018P>T\u0016V\u001bA2O\u0001C\u001bE"), GenericUtils.H("+<#0>6��6)>?1&>7")));
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void actionPerformed(@NotNull AnActionEvent a) {
        if (a == null) {
            m16enum(0);
        }
        Object data = a.getData(PlatformDataKeys.CONTEXT_COMPONENT);
        Project project = a.getProject();
        if (data == null) {
            CommonService.messageBus(project, BasicActionsBundle.message(GenericUtils.H("<8=4v3\u000e\u000b00j)=(-(j/4='><."), new Object[0]), MessageType.INFO);
            return;
        }
        Object lastPathComponent = ((Tree) data).getSelectionPath().getLastPathComponent();
        if (lastPathComponent != null) {
            xf(a, lastPathComponent);
        } else {
            CommonService.messageBus(project, BasicActionsBundle.message(IndentLineUtil.H("\u001cO.p[I\u0002P\f[DP6t\r_@R\u001cB\u0001O\u001a_"), new Object[0]), MessageType.INFO);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private String nE(Object a) {
        String str;
        String str2 = "";
        try {
            str2 = (String) ReflectUtil.getObjField(a, GenericUtils.H(".:$:#1 ,9\u001c\u0015"));
            str = str2;
        } catch (IllegalAccessException e) {
            f7enum.warn("getErrorMessage IllegalAccessException:" + e.getMessage());
            str = str2;
        } catch (NoSuchFieldException e2) {
            f7enum.warn("getErrorMessage NoSuchFieldException:" + e2.getMessage());
            str = str2;
        }
        if (StringUtils.isBlank(str)) {
            try {
                str2 = (String) ReflectUtil.getObjField(a, IndentLineUtil.H("\u0001O\f_"));
                return str2;
            } catch (IllegalAccessException e3) {
                f7enum.warn("getErrorMessage IllegalAccessException:" + e3.getMessage());
            } catch (NoSuchFieldException e4) {
                f7enum.warn("getErrorMessage NoSuchFieldException:" + e4.getMessage());
                return str2;
            }
        }
        return str2;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private void xf(AnActionEvent a, Object a2) {
        try {
            Project project = a.getProject();
            PsiFile psiFile = (PsiFile) a.getData(CommonDataKeys.PSI_FILE);
            int intValue = ((Integer) ReflectUtil.getObjField(a2, GenericUtils.H("\"/>="))).intValue();
            Document document = PsiDocumentManager.getInstance(a.getProject()).getDocument(psiFile);
            Presentation.handleDebug(project, psiFile.getVirtualFile().getPath(), document.getText().substring(document.getLineStartOffset(intValue), document.getLineEndOffset(intValue)), nE(a2), intValue + 1);
        } catch (IllegalAccessException e) {
            f7enum.warn("getCode IllegalAccessException:" + e.getMessage());
        } catch (NoSuchFieldException e2) {
            f7enum.warn("getCode NoSuchFieldException:" + e2.getMessage());
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public void update(AnActionEvent a) {
        Object data = a.getData(PlatformDataKeys.CONTEXT_COMPONENT);
        if (data != null) {
            Object lastPathComponent = ((Tree) data).getSelectionPath().getLastPathComponent();
            if (lastPathComponent == null) {
                a.getPresentation().setVisible(false);
                return;
            } else if (StringUtils.isBlank(nE(lastPathComponent))) {
                a.getPresentation().setVisible(false);
                return;
            } else {
                a.getPresentation().setVisible(true);
                return;
            }
        }
        a.getPresentation().setVisible(false);
    }
}
