package com.aicode.template.generator;

import cn.hutool.core.text.CharSequenceUtil;
import com.aicode.util.StringUtils;
import com.intellij.codeInsight.CodeInsightBundle;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.SourceFolder;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiPackage;
import com.intellij.refactoring.PackageWrapper;
import com.intellij.refactoring.util.RefactoringUtil;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.containers.ContainerUtil;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.model.java.JavaModuleSourceRootTypes;
import org.jetbrains.jps.model.java.JavaResourceRootProperties;
import org.jetbrains.jps.model.java.JavaSourceRootProperties;
import org.jetbrains.jps.model.java.JavaSourceRootType;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/generator/TargetDirectoryLocator.class */
public class TargetDirectoryLocator {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 0:
            case 1:
            default:
                objArr[0] = "project";
                break;
            case 2:
            case 4:
            case 5:
                objArr[0] = "module";
                break;
            case 3:
                objArr[0] = "mainModule";
                break;
            case 6:
                objArr[0] = "result";
                break;
        }
        objArr[1] = "com/aicode/template/generator/TargetDirectoryLocator";
        switch (i) {
            case 0:
            default:
                objArr[2] = "getOrCreateDirectory";
                break;
            case 1:
            case 2:
                objArr[2] = "checkAndCreateTestDirectory";
                break;
            case 3:
                objArr[2] = "computeTestRoots";
                break;
            case 4:
                objArr[2] = "suitableTestSourceFolders";
                break;
            case 5:
            case 6:
                objArr[2] = "collectSuitableDestinationSourceRoots";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Nullable
    public PsiDirectory getOrCreateDirectory(@NotNull Project project, PsiPackage targetPackage, Module targetModule, String targetDirectory) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        VirtualFile dir = checkAndCreateTestDirectory(project, targetModule, targetDirectory);
        if (dir != null) {
            String packageQualifiedName = targetPackage.getQualifiedName();
            return selectTargetDirectory(packageQualifiedName, project, targetModule);
        }
        return null;
    }

    public VirtualFile checkAndCreateTestDirectory(@NotNull Project project, @NotNull Module module, String targetDirectory) {
        if (project == null) {
            $$$reportNull$$$0(1);
        }
        if (module == null) {
            $$$reportNull$$$0(2);
        }
        String moduleFilePath = null;
        try {
            Method method = module.getClass().getMethod("getModuleFilePath", new Class[0]);
            moduleFilePath = (String) method.invoke(module, new Object[0]);
        } catch (Exception e) {
        }
        if (StringUtils.isBlank(moduleFilePath)) {
            return null;
        }
        String moduleFilePath2 = moduleFilePath.replace(".idea/modules/", "").replace(".idea", "");
        String modulePath = moduleFilePath2.substring(0, moduleFilePath2.lastIndexOf("/"));
        String targetDirectory2 = targetDirectory.replace('\\', '/');
        VirtualFile moduleDir = LocalFileSystem.getInstance().findFileByPath(modulePath.replace('\\', '/'));
        if (moduleDir == null) {
            return null;
        }
        String path = targetDirectory2.replaceAll(modulePath, "");
        List<String> pathComponents = (List) Arrays.asList(path.split("/")).stream().filter((v0) -> {
            return CharSequenceUtil.isNotBlank(v0);
        }).collect(Collectors.toList());
        VirtualFile currentDir = moduleDir;
        for (int i = 0; i < pathComponents.size(); i++) {
            String component = pathComponents.get(i);
            VirtualFile newDir = currentDir.findChild(component);
            if (newDir == null) {
                newDir = createDirectory(component, currentDir, module, component.equals("java"));
                if (newDir == null) {
                    return null;
                }
            }
            currentDir = newDir;
        }
        return currentDir;
    }

    private VirtualFile createDirectory(final String path, final VirtualFile moduleDir, final Module module, final boolean makeTest) {
        return (VirtualFile) WriteCommandAction.runWriteCommandAction(module.getProject(), new Computable<VirtualFile>() { // from class: com.aicode.template.generator.TargetDirectoryLocator.1
            /* renamed from: compute, reason: merged with bridge method [inline-methods] */
            public VirtualFile m348compute() {
                try {
                    VirtualFile createdTestDir = moduleDir.createChildDirectory(this, path);
                    if (makeTest) {
                        TargetDirectoryLocator.this.markAsTestSourceRoot(module, createdTestDir);
                    }
                    createdTestDir.refresh(false, true);
                    return createdTestDir;
                } catch (IOException e) {
                    return null;
                }
            }
        });
    }

    private void markAsTestSourceRoot(Module module, VirtualFile javaDir) {
        WriteCommandAction.runWriteCommandAction(module.getProject(), () -> {
            ModuleRootManager rootManager = ModuleRootManager.getInstance(module);
            ModifiableRootModel modifiableModel = rootManager.getModifiableModel();
            ContentEntry contentEntry = modifiableModel.getContentEntries()[0];
            boolean alreadyMarked = false;
            SourceFolder[] sourceFolders = contentEntry.getSourceFolders();
            int length = sourceFolders.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    SourceFolder sourceFolder = sourceFolders[i];
                    if (sourceFolder == null || sourceFolder.getFile() == null || !sourceFolder.getFile().equals(javaDir)) {
                        i++;
                    } else {
                        alreadyMarked = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!alreadyMarked) {
                contentEntry.addSourceFolder(javaDir, true);
                modifiableModel.commit();
            } else {
                modifiableModel.dispose();
            }
        });
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.aicode.template.generator.TargetDirectoryLocator$2] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.aicode.template.generator.TargetDirectoryLocator$3] */
    @Nullable
    private PsiDirectory selectTargetDirectory(String packageName, Project myProject, final Module myTargetModule) throws IncorrectOperationException {
        final PackageWrapper targetPackage = new PackageWrapper(PsiManager.getInstance(myProject), packageName);
        final VirtualFile selectedRoot = (VirtualFile) new ReadAction<VirtualFile>() { // from class: com.aicode.template.generator.TargetDirectoryLocator.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "result", "com/aicode/template/generator/TargetDirectoryLocator$2", "run"));
            }

            protected void run(@NotNull Result<? super VirtualFile> result) throws Throwable {
                List<VirtualFile> roots;
                if (result == null) {
                    $$$reportNull$$$0(0);
                }
                List<VirtualFile> testFolders = TargetDirectoryLocator.computeTestRoots(myTargetModule);
                if (testFolders == null || testFolders.isEmpty()) {
                    roots = new ArrayList<>();
                    List<String> urls = CreateTestFileTask.computeSuitableTestRootUrls(myTargetModule);
                    for (String url : urls) {
                        ContainerUtil.addIfNotNull(roots, LocalFileSystem.getInstance().findFileByPath(url));
                    }
                    if (roots.isEmpty()) {
                        TargetDirectoryLocator.collectSuitableDestinationSourceRoots(myTargetModule, roots);
                    }
                    if (roots.isEmpty()) {
                        return;
                    }
                } else {
                    roots = new ArrayList<>(testFolders);
                }
                if (roots.size() == 1) {
                    result.setResult(roots.get(0));
                } else if (roots.size() > 1) {
                    roots.sort((a, b) -> {
                        if (StringUtils.isNotBlank(a.getCanonicalPath()) && StringUtils.isNotBlank(b.getCanonicalPath())) {
                            return b.getCanonicalPath().length() - a.getCanonicalPath().length();
                        }
                        return 0;
                    });
                    result.setResult(roots.get(0));
                } else {
                    result.setResult((Object) null);
                }
            }
        }.execute().getResultObject();
        if (selectedRoot == null) {
            return null;
        }
        return (PsiDirectory) new WriteCommandAction<PsiDirectory>(myProject, CodeInsightBundle.message("create.directory.command", new Object[0]), new PsiFile[0]) { // from class: com.aicode.template.generator.TargetDirectoryLocator.3
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "result", "com/aicode/template/generator/TargetDirectoryLocator$3", "run"));
            }

            protected void run(@NotNull Result<? super PsiDirectory> result) {
                if (result == null) {
                    $$$reportNull$$$0(0);
                }
                result.setResult(RefactoringUtil.createPackageDirectoryInSourceRoot(targetPackage, selectedRoot));
            }
        }.execute().getResultObject();
    }

    public static List<VirtualFile> computeTestRoots(@NotNull Module mainModule) {
        if (mainModule == null) {
            $$$reportNull$$$0(3);
        }
        ArrayList<VirtualFile> virtualFiles = new ArrayList<>();
        List<SourceFolder> sourceFolders = suitableTestSourceFolders(mainModule);
        if (!sourceFolders.isEmpty()) {
            for (SourceFolder sourceFolder : sourceFolders) {
                if (sourceFolder.getFile() != null) {
                    virtualFiles.add(sourceFolder.getFile());
                }
            }
        } else {
            HashSet<Module> modules = new HashSet<>();
            ModuleUtilCore.collectModulesDependsOn(mainModule, modules);
            Iterator<Module> it = modules.iterator();
            while (it.hasNext()) {
                Module module = it.next();
                List<SourceFolder> folders = suitableTestSourceFolders(module);
                for (SourceFolder sourceFolder2 : folders) {
                    if (sourceFolder2.getFile() != null) {
                        virtualFiles.add(sourceFolder2.getFile());
                    }
                }
            }
        }
        return virtualFiles;
    }

    private static List<SourceFolder> suitableTestSourceFolders(@NotNull Module module) {
        if (module == null) {
            $$$reportNull$$$0(4);
        }
        ArrayList<SourceFolder> sourceFolders = new ArrayList<>();
        for (ContentEntry contentEntry : ModuleRootManager.getInstance(module).getContentEntries()) {
            List<SourceFolder> testSourceFolders = contentEntry.getSourceFolders(JavaSourceRootType.TEST_SOURCE);
            for (SourceFolder sourceFolder : testSourceFolders) {
                if (!isForGeneratedSources(sourceFolder)) {
                    sourceFolders.add(sourceFolder);
                }
            }
        }
        return sourceFolders;
    }

    @Nullable
    private PsiDirectory chooseDefaultDirectory(PsiDirectory[] directories, List<VirtualFile> roots, Module myTargetModule, Project myProject) {
        PsiDirectory rootDir;
        List<PsiDirectory> dirs = new ArrayList<>();
        for (VirtualFile file : ModuleRootManager.getInstance(myTargetModule).getSourceRoots(JavaSourceRootType.TEST_SOURCE)) {
            PsiDirectory dir = PsiManager.getInstance(myProject).findDirectory(file);
            if (dir != null) {
                dirs.add(dir);
            }
        }
        if (!dirs.isEmpty()) {
            for (PsiDirectory dir2 : dirs) {
                String dirName = dir2.getVirtualFile().getPath();
                if (!dirName.contains("generated")) {
                    return dir2;
                }
            }
            return dirs.get(0);
        }
        for (PsiDirectory psiDirectory : directories) {
            VirtualFile file2 = psiDirectory.getVirtualFile();
            for (VirtualFile root : roots) {
                if (VfsUtilCore.isAncestor(root, file2, false) && (rootDir = PsiManager.getInstance(myProject).findDirectory(root)) != null) {
                    return rootDir;
                }
            }
        }
        return null;
    }

    public static void collectSuitableDestinationSourceRoots(@NotNull Module module, @NotNull List<VirtualFile> result) {
        if (module == null) {
            $$$reportNull$$$0(5);
        }
        if (result == null) {
            $$$reportNull$$$0(6);
        }
        for (ContentEntry entry : ModuleRootManager.getInstance(module).getContentEntries()) {
            for (SourceFolder sourceFolder : entry.getSourceFolders(JavaModuleSourceRootTypes.SOURCES)) {
                if (!isForGeneratedSources(sourceFolder)) {
                    ContainerUtil.addIfNotNull(result, sourceFolder.getFile());
                }
            }
        }
    }

    public static boolean isForGeneratedSources(SourceFolder sourceFolder) {
        JavaSourceRootProperties properties = sourceFolder.getJpsElement().getProperties(JavaModuleSourceRootTypes.SOURCES);
        JavaResourceRootProperties resourceProperties = sourceFolder.getJpsElement().getProperties(JavaModuleSourceRootTypes.RESOURCES);
        return (properties != null && properties.isForGeneratedSources()) || (resourceProperties != null && resourceProperties.isForGeneratedSources());
    }
}
