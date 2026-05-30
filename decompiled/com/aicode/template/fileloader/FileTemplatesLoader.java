package com.aicode.template.fileloader;

import com.aicode.util.AICodeUtils;
import com.aicode.util.FileUtils;
import com.aicode.util.PluginInfoUtils;
import com.aicode.util.PsiUtils;
import com.intellij.ide.fileTemplates.impl.DefaultTemplate;
import com.intellij.ide.fileTemplates.impl.UrlUtil;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.PluginDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.io.FileUtilRt;
import com.intellij.project.ProjectKt;
import com.intellij.util.UriUtil;
import com.intellij.util.containers.MultiMap;
import gnu.trove.THashSet;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/fileloader/FileTemplatesLoader.class */
class FileTemplatesLoader {
    private static final Logger LOG;
    static final String TEMPLATES_DIR = "fileTemplates";
    private static final String DEFAULT_TEMPLATES_ROOT = "fileTemplates";
    private static final String DESCRIPTION_FILE_EXTENSION = "html";
    private static final String DESCRIPTION_EXTENSION_SUFFIX = ".html";
    private static final String DEFAULT_TEMPLATE_DESCRIPTION_FILENAME = "default.html";
    private final FTManager myTestTemplatesManager;
    private final FTManager myIncludesManager;
    private final FTManager[] myAllManagers;
    private static final String TESTS_DIR = "unitTests";
    public static final String INCLUDES_DIR = "unitIncludes";
    private final URL myDefaultTemplateDescription;
    private final URL myDefaultIncludeDescription;
    static final /* synthetic */ boolean $assertionsDisabled;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 0:
            case 2:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 0:
            case 2:
            default:
                i2 = 2;
                break;
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 0:
            case 2:
            default:
                objArr[0] = "com/aicode/template/fileloader/FileTemplatesLoader";
                break;
            case 1:
            case 4:
                objArr[0] = "prefixes";
                break;
            case 3:
            case 6:
                objArr[0] = "root";
                break;
            case 5:
                objArr[0] = "result";
                break;
            case 7:
                objArr[0] = "path";
                break;
            case 8:
                objArr[0] = "prefix";
                break;
            case 9:
                objArr[0] = "pathPrefix";
                break;
            case 10:
                objArr[0] = "templateName";
                break;
            case 11:
                objArr[0] = "templateExtension";
                break;
            case 12:
                objArr[0] = "descriptionPaths";
                break;
        }
        switch (i) {
            case 0:
            default:
                objArr[1] = "getAllManagers";
                break;
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                objArr[1] = "com/aicode/template/fileloader/FileTemplatesLoader";
                break;
            case 2:
                objArr[1] = "loadDefaultTemplates";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "loadDefaultTemplates";
                break;
            case 3:
            case 4:
            case 5:
                objArr[2] = "loadDefaultsFromRoot";
                break;
            case 6:
                objArr[2] = "toFullPath";
                break;
            case 7:
            case 8:
                objArr[2] = "matchesPrefix";
                break;
            case 9:
            case 10:
            case 11:
            case 12:
                objArr[2] = "getDescriptionPath";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 0:
            case 2:
            default:
                throw new IllegalStateException(format);
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                throw new IllegalArgumentException(format);
        }
    }

    static {
        $assertionsDisabled = !FileTemplatesLoader.class.desiredAssertionStatus();
        LOG = Logger.getInstance(FileTemplatesLoader.class);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FileTemplatesLoader(@Nullable Project project) {
        String configPath;
        if (project == null || project.isDefault()) {
            configPath = PathManager.getConfigPath();
        } else {
            configPath = UriUtil.trimTrailingSlashes(((Path) Objects.requireNonNull(ProjectKt.getStateStore(project).getDirectoryStorePath())).toFile().getPath());
        }
        Path configDir = Paths.get(configPath, "fileTemplates");
        this.myTestTemplatesManager = new FTManager("Tests", configDir.resolve("unitTests"), true);
        this.myIncludesManager = new FTManager("Includes", configDir.resolve("unitIncludes"));
        this.myAllManagers = new FTManager[]{this.myTestTemplatesManager, this.myIncludesManager};
        Map<FTManager, String> managerToPrefix = new LinkedHashMap<>();
        for (FTManager manager : this.myAllManagers) {
            Path managerRoot = manager.getConfigRoot();
            String relativePath = configDir.equals(managerRoot) ? "" : FileUtilRt.toSystemIndependentName(configDir.relativize(managerRoot).toString()) + "/";
            managerToPrefix.put(manager, relativePath);
        }
        FileTemplateLoadResult result = loadDefaultTemplates(new ArrayList(managerToPrefix.values()));
        this.myDefaultTemplateDescription = result.getDefaultTemplateDescription();
        this.myDefaultIncludeDescription = result.getDefaultIncludeDescription();
        for (FTManager manager2 : this.myAllManagers) {
            manager2.setDefaultTemplates(result.getResult().get(managerToPrefix.get(manager2)));
            manager2.loadCustomizedContent();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NotNull
    public FTManager[] getAllManagers() {
        FTManager[] fTManagerArr = this.myAllManagers;
        if (fTManagerArr == null) {
            $$$reportNull$$$0(0);
        }
        return fTManagerArr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NotNull
    public FTManager getInternalTestTemplatesManager() {
        return new FTManager(this.myTestTemplatesManager);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NotNull
    public FTManager getCustomTestTemplatesManager() {
        return new FTManager(this.myTestTemplatesManager);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NotNull
    public FTManager getPatternsManager() {
        return new FTManager(this.myIncludesManager);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public URL getDefaultTemplateDescription() {
        return this.myDefaultTemplateDescription;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public URL getDefaultIncludeDescription() {
        return this.myDefaultIncludeDescription;
    }

    @NotNull
    private static FileTemplateLoadResult loadDefaultTemplates(@NotNull List<String> prefixes) {
        if (prefixes == null) {
            $$$reportNull$$$0(1);
        }
        FileTemplateLoadResult result = new FileTemplateLoadResult(new MultiMap());
        THashSet tHashSet = new THashSet();
        Set<ClassLoader> processedLoaders = new HashSet<>();
        for (PluginDescriptor plugin : PluginManagerCore.getPlugins()) {
            if (PsiUtils.instanceOf(plugin, "com.intellij.ide.plugins.IdeaPluginDescriptorImpl") && plugin.isEnabled()) {
                ClassLoader loader = plugin.getPluginClassLoader();
                if (PsiUtils.instanceOf(loader, "com.intellij.ide.plugins.cl.PluginClassLoader")) {
                    Object getUrls = null;
                    try {
                        Class<?> pluginClassLoaderClass = Class.forName("com.intellij.ide.plugins.cl.PluginClassLoader");
                        Method method = pluginClassLoaderClass.getMethod("getUrls", new Class[0]);
                        getUrls = method.invoke(loader, new Object[0]);
                    } catch (Exception e) {
                    }
                    if (getUrls != null) {
                        if (((List) getUrls).isEmpty()) {
                        }
                    }
                    if (!processedLoaders.add(loader)) {
                    }
                }
                try {
                    Enumeration<URL> systemResources = loader.getResources("fileTemplates");
                    if (systemResources.hasMoreElements()) {
                        while (systemResources.hasMoreElements()) {
                            URL url = systemResources.nextElement();
                            if (tHashSet.add(url)) {
                                loadDefaultsFromRoot(url, prefixes, result);
                            }
                        }
                    }
                } catch (IOException e2) {
                    LOG.warn(e2);
                }
            }
        }
        if (result == null) {
            $$$reportNull$$$0(2);
        }
        return result;
    }

    private static void loadDefaultsFromRoot(@NotNull URL root, @NotNull List<String> prefixes, @NotNull FileTemplateLoadResult result) throws IOException {
        String sourcePath;
        if (root == null) {
            $$$reportNull$$$0(3);
        }
        if (prefixes == null) {
            $$$reportNull$$$0(4);
        }
        if (result == null) {
            $$$reportNull$$$0(5);
        }
        List<String> children = UrlUtil.getChildrenRelativePaths(root);
        if (children.isEmpty()) {
            return;
        }
        Set<String> descriptionPaths = new HashSet<>();
        for (String path : children) {
            if (path.equals("unitTests/default.html")) {
                result.setDefaultTemplateDescription(toFullPath(root, path));
            } else if (path.equals("unitIncludes/default.html")) {
                result.setDefaultIncludeDescription(toFullPath(root, path));
            } else if (path.endsWith(".html")) {
                descriptionPaths.add(path);
            }
        }
        for (String path2 : children) {
            if (path2.endsWith(".ft")) {
                for (String prefix : prefixes) {
                    if (matchesPrefix(path2, prefix)) {
                        String filename = path2.substring(prefix.length(), path2.length() - ".ft".length());
                        String extension = FileUtilRt.getExtension(filename);
                        String templateName = filename.substring(0, (filename.length() - extension.length()) - 1);
                        URL templateUrl = toFullPath(root, path2);
                        String descriptionPath = getDescriptionPath(prefix, templateName, extension, descriptionPaths);
                        URL descriptionUrl = descriptionPath == null ? null : toFullPath(root, descriptionPath);
                        if (!$assertionsDisabled && templateUrl == null) {
                            throw new AssertionError();
                        }
                        DefaultTemplate defaultTemplate = null;
                        try {
                            Class<?> defaultTemplateClass = Class.forName("com.intellij.ide.fileTemplates.impl.DefaultTemplate");
                            if (ApplicationInfo.getInstance().getBuild().getBaselineVersion() > 241) {
                                Function textLoader = filePath -> {
                                    try {
                                        return UrlUtil.loadText(templateUrl);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        return null;
                                    }
                                };
                                Function descriptionLoader = descriptionUrl == null ? null : filePath2 -> {
                                    try {
                                        return UrlUtil.loadText(descriptionUrl);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        return null;
                                    }
                                };
                                Path localPath = FileUtils.getLocalPath();
                                if (localPath != null) {
                                    sourcePath = localPath + File.separator + FileUtils.FILE_TEMPLATES;
                                } else {
                                    Path basePath = Paths.get(AICodeUtils.getAgentDirectoryPath(), new String[0]);
                                    sourcePath = basePath + File.separator + FileUtils.AGENT_DIR + File.separator + FileUtils.FILE_TEMPLATES;
                                    LOG.info("模板地址: " + sourcePath);
                                }
                                Path templatePath = Path.of(sourcePath + templateUrl.getPath().substring(templateUrl.getPath().indexOf("!") + 1), new String[0]);
                                if (templatePath == null) {
                                    LOG.info("模板地址为空");
                                } else {
                                    LOG.info("templatePath " + templatePath.getFileName());
                                    Constructor<?> constructor = defaultTemplateClass.getDeclaredConstructor(String.class, String.class, Function.class, Function.class, String.class, Path.class, PluginDescriptor.class);
                                    constructor.setAccessible(true);
                                    IdeaPluginDescriptor plugin = PluginManagerCore.getPlugin(PluginInfoUtils.AICODE_ID);
                                    defaultTemplate = (DefaultTemplate) constructor.newInstance(templateName, extension, textLoader, descriptionLoader, descriptionPath, templatePath, plugin);
                                    LOG.info("template load end ");
                                }
                            } else {
                                Constructor<?> constructor2 = defaultTemplateClass.getDeclaredConstructor(String.class, String.class, URL.class, URL.class);
                                constructor2.setAccessible(true);
                                defaultTemplate = (DefaultTemplate) constructor2.newInstance(templateName, extension, templateUrl, descriptionUrl);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        result.getResult().putValue(prefix, defaultTemplate);
                        break;
                    }
                }
            }
        }
    }

    private static URL toFullPath(@NotNull URL root, String path) throws MalformedURLException {
        if (root == null) {
            $$$reportNull$$$0(6);
        }
        return FileUtils.internProtocol(new URL(UriUtil.trimTrailingSlashes(root.toExternalForm()) + "/" + path));
    }

    private static boolean matchesPrefix(@NotNull String path, @NotNull String prefix) {
        if (path == null) {
            $$$reportNull$$$0(7);
        }
        if (prefix == null) {
            $$$reportNull$$$0(8);
        }
        return prefix.isEmpty() ? path.indexOf(47) == -1 : FileUtil.startsWith(path, prefix) && path.indexOf(47, prefix.length()) == -1;
    }

    @Nullable
    private static String getDescriptionPath(@NotNull String pathPrefix, @NotNull String templateName, @NotNull String templateExtension, @NotNull Set<String> descriptionPaths) {
        if (pathPrefix == null) {
            $$$reportNull$$$0(9);
        }
        if (templateName == null) {
            $$$reportNull$$$0(10);
        }
        if (templateExtension == null) {
            $$$reportNull$$$0(11);
        }
        if (descriptionPaths == null) {
            $$$reportNull$$$0(12);
        }
        Locale locale = Locale.getDefault();
        String descName = MessageFormat.format("{0}.{1}_{2}_{3}.html", templateName, templateExtension, locale.getLanguage(), locale.getCountry());
        String descPath = pathPrefix.isEmpty() ? descName : pathPrefix + descName;
        if (descriptionPaths.contains(descPath)) {
            return descPath;
        }
        String descName2 = MessageFormat.format("{0}.{1}_{2}.html", templateName, templateExtension, locale.getLanguage());
        String descPath2 = pathPrefix.isEmpty() ? descName2 : pathPrefix + descName2;
        if (descriptionPaths.contains(descPath2)) {
            return descPath2;
        }
        String descName3 = templateName + "." + templateExtension + ".html";
        String descPath3 = pathPrefix.isEmpty() ? descName3 : pathPrefix + descName3;
        if (descriptionPaths.contains(descPath3)) {
            return descPath3;
        }
        return null;
    }
}
