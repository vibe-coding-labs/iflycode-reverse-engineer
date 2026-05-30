package com.aicode.action.batch;

import com.aicode.action.batch.node.FileNode;
import com.aicode.agent.PluginWebsocketClient;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.content.util.OverlayUtils;
import com.aicode.message.BasicActionsBundle;
import com.aicode.util.NewFileUtils;
import com.aicode.util.PsiUtils;
import com.google.gson.JsonObject;
import com.intellij.compiler.impl.ModuleCompileScope;
import com.intellij.openapi.compiler.CompilationStatusListener;
import com.intellij.openapi.compiler.CompileScope;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.compiler.CompilerTopics;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.impl.VirtualDirectoryImpl;
import com.intellij.openapi.vfs.newvfs.impl.VirtualFileImpl;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/* compiled from: ol */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/batch/BatchUnitTestTemplateService.class */
public class BatchUnitTestTemplateService {

    /* renamed from: float, reason: not valid java name */
    private static final String f52float = NewFileUtils.H("7O\u0001\u001cF");

    /* renamed from: final, reason: not valid java name */
    private static final Logger f50final = Logger.getInstance(BatchUnitTestTemplateService.class);

    /* renamed from: byte, reason: not valid java name */
    private static AtomicReference<BatchUnitTestDialog> f53byte = new AtomicReference<>(null);

    /* renamed from: try, reason: not valid java name */
    private static AtomicInteger f51try = new AtomicInteger(0);

    /* renamed from: enum, reason: not valid java name */
    private static String f54enum = OverlayUtils.H("\u0002\"\u0001%");

    /* renamed from: enum, reason: not valid java name */
    private static /* synthetic */ void m43enum(int a) {
        throw new IllegalStateException(String.format(NewFileUtils.H("}!U\u001cT=G\u0015\u0003\u001c[\u0018J\u001fK]\u001f\u001b\u0005\\noF\fO\u001a\u0018\u0004H\u0001\r\rN\r^\u000bNRU\u001cO\u001d"), OverlayUtils.H("?\u001e&I!\u0004\u001e?\"\u000en\r?\u0005\u0015>#O9\u0017(\u0012\u0014~\u000f\u00011\u000b0 *��=09\u000294\u001e;=\f;\u0003; $\u001e=\u000f.\u0005"), NewFileUtils.H("H\u000bY\u000fL\u000fr\u001bD\u000b\u007f\u001cX\rd\u001bZ\u0005L\u0016")));
    }

    private static void HE(Project a, CompilationStatusListener a2) {
        a.getMessageBus().connect().subscribe(CompilerTopics.COMPILATION_STATUS, a2);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @NotNull
    public static BatchUnitTestDialog createUnitTestDialog(Project a, Module a2, String a3, List<FileNode> list, List<String> list2, StringBuilder a4) {
        if (f53byte.get() != null && !f53byte.get().isShowing()) {
            f53byte.set(null);
        }
        BatchUnitTestDialog batchUnitTestDialog = new BatchUnitTestDialog(a, a2, a3, OverlayUtils.H("%?\u0003u\u001a?\u001a/C!\u0007;\u0001"), f54enum, list, list2, a4 == null ? "" : a4.toString());
        f53byte.set(batchUnitTestDialog);
        if (CollectionUtils.isEmpty(list) || list.get(0).getChildCount() <= 0) {
            batchUnitTestDialog.setOKActionEnabled(false);
        }
        batchUnitTestDialog.show();
        if (batchUnitTestDialog == null) {
            m43enum(0);
        }
        return batchUnitTestDialog;
    }

    private static CompileScope TC(Project a) {
        return new ModuleCompileScope(a, ModuleManager.getInstance(a).getModules(), false);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void doCompile(Project a, boolean z, GeneratorConfig a2) {
        CompilerManager compilerManager = CompilerManager.getInstance(a);
        if (compilerManager != null) {
            ad(a, compilerManager, TC(a), z, a2);
        } else {
            f50final.warn(NewFileUtils.H("l\u0012O\u0004OYs _Y[\u000bLJd\u001a@\u000fB\u0015O\nl\u0012c>F\u0016QQW\u0002T\u0001C\u001eH\u001c"));
        }
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    public static String getTestPath(Project a, VirtualFile a2) {
        int indexOf;
        VirtualFile contentRootForFile = ProjectFileIndex.getInstance(a).getContentRootForFile(a2);
        if (!Objects.nonNull(contentRootForFile)) {
            return null;
        }
        VirtualFile findChild = contentRootForFile.findChild(OverlayUtils.H("\u0015?\u0003"));
        if (!Objects.nonNull(findChild)) {
            VirtualFile findChild2 = contentRootForFile.getParent().findChild(OverlayUtils.H("?\u0003>\u0014"));
            if (Objects.nonNull(findChild2)) {
                VirtualFile findChild3 = findChild2.findChild(NewFileUtils.H("P\tP\u0015"));
                if (!Objects.nonNull(findChild3)) {
                    return contentRootForFile.getPath();
                }
                return findChild3.getPath();
            }
            String path = a2.getPath();
            if (StringUtils.isNotBlank(path) && StringUtils.contains(path, OverlayUtils.H("\u0015?\u0003")) && (indexOf = path.indexOf(NewFileUtils.H("\u001bT\u0017"))) > 0) {
                return path.substring(0, indexOf - 1);
            }
            return null;
        }
        VirtualFile findChild4 = findChild.findChild(NewFileUtils.H("N\rU��"));
        if (!Objects.nonNull(findChild4)) {
            VirtualFile findChild5 = findChild.findChild(NewFileUtils.H("W\tO\u001a"));
            if (!Objects.nonNull(findChild5)) {
                return null;
            }
            VirtualFile findChild6 = findChild5.findChild(OverlayUtils.H("?\u0003>\u0014"));
            if (Objects.nonNull(findChild6)) {
                VirtualFile findChild7 = findChild6.findChild(NewFileUtils.H("P\tP\u0015"));
                if (Objects.nonNull(findChild7)) {
                    return findChild7.getPath();
                }
                return contentRootForFile.getPath();
            }
            return contentRootForFile.getPath();
        }
        VirtualFile findChild8 = findChild4.findChild(OverlayUtils.H("!\u0007;\u0001"));
        if (!Objects.nonNull(findChild8)) {
            return contentRootForFile.getPath();
        }
        return findChild8.getPath();
    }

    private static void ad(Project a, CompilerManager a2, CompileScope a3, boolean z, GeneratorConfig a4) {
        CoverageCompileStatusNotification coverageCompileStatusNotification = new CoverageCompileStatusNotification(ToolWindowManager.getInstance(a).getToolWindow(OverlayUtils.H(".>\u000f!\u0004")), a2, Boolean.valueOf(z), a, a4, 3);
        a2.make(a3, coverageCompileStatusNotification);
        HE(a, coverageCompileStatusNotification);
    }

    public static void queryServerResource(Project a) {
        PluginWebsocketClient.sendWsMessage(CommandEnum.SERVER_RESOURCE, a);
    }

    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    public static void handleClassFiles(Project a, List<VirtualFile> list, List<FileNode> list2, List<String> list3, GeneratorConfig a2, String a3, Module a4, StringBuilder a5) {
        queryServerResource(a);
        List<VirtualFile> virtualFile = getVirtualFile(a, list);
        if (virtualFile.isEmpty()) {
            createUnitTestDialog(a, a4, a3, list2, list3, a5);
            return;
        }
        if (virtualFile.size() == 1) {
            a5.append(virtualFile.get(0).getNameWithoutExtension()).append(NewFileUtils.H("\u007f\u001cN\u001b"));
            a2.setSingleFile(true);
        }
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(4);
        Iterator<VirtualFile> it = virtualFile.iterator();
        while (it.hasNext()) {
            PsiJavaFile findFile = PsiManager.getInstance(a).findFile(it.next());
            String packageName = findFile.getPackageName();
            if (!concurrentHashMap.containsKey(packageName)) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(findFile);
                concurrentHashMap.put(packageName, arrayList);
            } else {
                ((List) concurrentHashMap.get(packageName)).add(findFile);
            }
        }
        for (Map.Entry entry : concurrentHashMap.entrySet()) {
            String str = (String) entry.getKey();
            List list4 = (List) entry.getValue();
            FileNode fileNode = new FileNode(str + (BasicActionsBundle.message(OverlayUtils.H("\u0005/\u0003\u001b9!E#\r(\u0012\u0014\u007f8\u000e2\u0002r\u0005\u0019\"9N6\r4\u0010'\u001dg\u00070\u0010>\u0013U88\r8\u0012,]1\u001e.��$\u0018"), new Object[0]) + list4.size() + BasicActionsBundle.message(NewFileUtils.H("\u000bD\u0017{&LW^\u000fL\tO[X\u0011B\r\b��J\u000eT\\Y\u001dN\u0015H\r#<N\u0011\\\u000e\u0013\u0001H\u0002j?U[Q\u0005M\u001fT\u0017"), new Object[0])));
            Iterator it2 = list4.iterator();
            while (it2.hasNext()) {
                PsiJavaFile psiJavaFile = (PsiJavaFile) it2.next();
                FileNode fileNode2 = new FileNode(psiJavaFile.getName());
                it2 = it2;
                fileNode.add(fileNode2);
                list2.add(fileNode);
                list3.add(psiJavaFile.getVirtualFile().getCanonicalPath());
            }
        }
        a2.setModule(a4);
        a2.setFileAbsolutePathList(list3);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static List<VirtualFile> getVirtualFile(Project a, List<VirtualFile> list) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<VirtualFile> it = list.iterator();
        while (it.hasNext()) {
            recursion(it.next(), a, linkedHashSet);
            it = it;
        }
        return new ArrayList(linkedHashSet);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void changeServerStatus(JsonObject a) {
        if (f53byte.get() != null) {
            f54enum = a.get(NewFileUtils.H("_\bI\u000e")).getAsJsonObject().get(OverlayUtils.H("2\u0018*\u00128\u0013")).getAsString();
            f51try.set(0);
            Ud();
        }
    }

    private static void Ud() {
        CompletableFuture.delayedExecutor(300L, TimeUnit.MILLISECONDS).execute(() -> {
            BatchUnitTestDialog batchUnitTestDialog = f53byte.get();
            if (f51try.incrementAndGet() >= 5) {
                return;
            }
            if (!batchUnitTestDialog.isShowing()) {
                Ud();
            } else {
                batchUnitTestDialog.changeGenerateByTemplateComponent(f54enum);
            }
        });
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static void recursion(VirtualFile a, Project a2, Set<VirtualFile> set) {
        PsiDirectory findDirectory;
        if (a instanceof VirtualFileImpl) {
            if (a.getFileType().getName().equals(NewFileUtils.H("k2v3")) && a.getName().endsWith(OverlayUtils.H("B!\u0007;\u0001")) && PsiUtils.instanceOf(PsiManager.getInstance(a2).findFile(a), NewFileUtils.H("_\u0001UDN\u001bY\u001aG\u0015U\u0004\u0005\t^\u0016\u00159O\u0007w\u000eQ\u0014g\u001aL\u0017"))) {
                set.add(a);
                return;
            }
            return;
        }
        if ((a instanceof VirtualDirectoryImpl) && (findDirectory = PsiManager.getInstance(a2).findDirectory(a)) != null) {
            PsiFile[] children = findDirectory.getChildren();
            int length = children.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                PsiFile psiFile = children[i2];
                if (psiFile instanceof PsiFile) {
                    PsiFile psiFile2 = psiFile;
                    if (PsiUtils.instanceOf(psiFile2, OverlayUtils.H(".\u000f(F1\u001b0\f%\b5\u001bc\u0010\b?c0)\u001e\u0014\u00127\r\r\u000f!\u0005"))) {
                        set.add(psiFile2.getVirtualFile());
                    }
                } else if (psiFile instanceof PsiDirectory) {
                    recursion(((PsiDirectory) psiFile).getVirtualFile(), a2, set);
                }
                i2++;
                i = i2;
            }
        }
    }
}
