package com.aicode.template.fileloader;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplatesScheme;
import com.intellij.ide.fileTemplates.InternalTemplateBean;
import com.intellij.ide.fileTemplates.impl.CustomFileTemplate;
import com.intellij.ide.fileTemplates.impl.FileTemplateBase;
import com.intellij.openapi.application.ApplicationNamesInfo;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.ex.FileTypeManagerEx;
import com.intellij.openapi.project.DefaultProjectFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtilRt;
import com.intellij.project.ProjectKt;
import com.intellij.util.SystemProperties;
import com.intellij.util.text.DateFormatUtil;
import java.net.URL;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/fileloader/UnitTemplateManager.class */
public class UnitTemplateManager extends FileTemplateManager {
    private static final Logger LOG = Logger.getInstance("#UnitTemplateManager");
    public static final String TEST_TEMPLATES_CATEGORY = "Tests";
    private final FileTemplatesLoader myFileTemplatesLoader;
    private static volatile UnitTemplateManager instance;
    private final Project myProject;
    private final FileTemplatesScheme myProjectScheme;
    private boolean myInitialized;
    private Date myTestDate;
    private FileTemplatesScheme myScheme = FileTemplatesScheme.DEFAULT;
    private final TemplateRegistry templateRegistry = new TemplateRegistry();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 0:
            case 2:
            case 3:
            case 5:
            case 9:
            case 10:
            case 11:
            case 13:
            case 17:
            case 20:
            case 21:
            case 22:
            case 23:
            case 26:
            case 28:
            case 30:
            case 31:
            case 34:
            case 36:
            case 37:
            case 38:
            case 41:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 1:
            case 4:
            case 6:
            case 7:
            case 8:
            case 12:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 24:
            case 25:
            case 27:
            case 29:
            case 32:
            case 33:
            case 35:
            case 39:
            case 40:
            case 42:
            case 43:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 0:
            case 2:
            case 3:
            case 5:
            case 9:
            case 10:
            case 11:
            case 13:
            case 17:
            case 20:
            case 21:
            case 22:
            case 23:
            case 26:
            case 28:
            case 30:
            case 31:
            case 34:
            case 36:
            case 37:
            case 38:
            case 41:
            default:
                i2 = 3;
                break;
            case 1:
            case 4:
            case 6:
            case 7:
            case 8:
            case 12:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 24:
            case 25:
            case 27:
            case 29:
            case 32:
            case 33:
            case 35:
            case 39:
            case 40:
            case 42:
            case 43:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 0:
            default:
                objArr[0] = "project";
                break;
            case 1:
            case 4:
            case 6:
            case 7:
            case 8:
            case 12:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 24:
            case 25:
            case 27:
            case 29:
            case 32:
            case 33:
            case 35:
            case 39:
            case 40:
            case 42:
            case 43:
                objArr[0] = "com/aicode/template/fileloader/UnitTemplateManager";
                break;
            case 2:
            case 3:
                objArr[0] = "scheme";
                break;
            case 5:
                objArr[0] = "category";
                break;
            case 9:
            case 20:
            case 21:
            case 22:
            case 23:
            case 26:
            case 28:
            case 30:
                objArr[0] = "templateName";
                break;
            case 10:
            case 17:
            case 34:
            case 38:
            case 41:
                objArr[0] = "name";
                break;
            case 11:
                objArr[0] = "extension";
                break;
            case 13:
                objArr[0] = "template";
                break;
            case 31:
                objArr[0] = "ftManager";
                break;
            case 36:
                objArr[0] = "templatesCategory";
                break;
            case 37:
                objArr[0] = "templates";
                break;
        }
        switch (i) {
            case 0:
            case 2:
            case 3:
            case 5:
            case 9:
            case 10:
            case 11:
            case 13:
            case 17:
            case 20:
            case 21:
            case 22:
            case 23:
            case 26:
            case 28:
            case 30:
            case 31:
            case 34:
            case 36:
            case 37:
            case 38:
            case 41:
            default:
                objArr[1] = "com/aicode/template/fileloader/UnitTemplateManager";
                break;
            case 1:
                objArr[1] = "getCurrentScheme";
                break;
            case 4:
                objArr[1] = "checkInitialized";
                break;
            case 6:
            case 7:
                objArr[1] = "getTemplates";
                break;
            case 8:
                objArr[1] = "getAllTemplates";
                break;
            case 12:
                objArr[1] = "addTemplate";
                break;
            case 14:
                objArr[1] = "getDefaultProperties";
                break;
            case 15:
            case 16:
                objArr[1] = "getCalendarValue";
                break;
            case 18:
                objArr[1] = "getInternalTemplates";
                break;
            case 19:
                objArr[1] = "getTestTemplates";
                break;
            case 24:
            case 25:
                objArr[1] = "internalTemplateToSubject";
                break;
            case 27:
                objArr[1] = "getCodeTemplate";
                break;
            case 29:
                objArr[1] = "getJ2eeTemplate";
                break;
            case 32:
            case 33:
                objArr[1] = "getTemplateFromManager";
                break;
            case 35:
                objArr[1] = "getDefaultTemplate";
                break;
            case 39:
                objArr[1] = "getQualifiedName";
                break;
            case 40:
                objArr[1] = "getAllPatterns";
                break;
            case 42:
                objArr[1] = "getAllCodeTemplates";
                break;
            case 43:
                objArr[1] = "getAllJ2eeTemplates";
                break;
        }
        switch (i) {
            case 0:
            default:
                objArr[2] = "getInstance";
                break;
            case 1:
            case 4:
            case 6:
            case 7:
            case 8:
            case 12:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 24:
            case 25:
            case 27:
            case 29:
            case 32:
            case 33:
            case 35:
            case 39:
            case 40:
            case 42:
            case 43:
                break;
            case 2:
                objArr[2] = "setCurrentScheme";
                break;
            case 3:
                objArr[2] = "setScheme";
                break;
            case 5:
                objArr[2] = "getTemplates";
                break;
            case 9:
                objArr[2] = "getTemplate";
                break;
            case 10:
            case 11:
                objArr[2] = "addTemplate";
                break;
            case 13:
                objArr[2] = "removeTemplate";
                break;
            case 17:
                objArr[2] = "addRecentName";
                break;
            case 20:
                objArr[2] = "getInternalTemplate";
                break;
            case 21:
                objArr[2] = "findInternalTemplate";
                break;
            case 22:
                objArr[2] = "findCustomTestTemplate";
                break;
            case 23:
                objArr[2] = "internalTemplateToSubject";
                break;
            case 26:
                objArr[2] = "getCodeTemplate";
                break;
            case 28:
                objArr[2] = "getJ2eeTemplate";
                break;
            case 30:
            case 31:
                objArr[2] = "getTemplateFromManager";
                break;
            case 34:
                objArr[2] = "getDefaultTemplate";
                break;
            case 36:
            case 37:
                objArr[2] = "setTemplates";
                break;
            case 38:
                objArr[2] = "getQualifiedName";
                break;
            case 41:
                objArr[2] = "getPattern";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 0:
            case 2:
            case 3:
            case 5:
            case 9:
            case 10:
            case 11:
            case 13:
            case 17:
            case 20:
            case 21:
            case 22:
            case 23:
            case 26:
            case 28:
            case 30:
            case 31:
            case 34:
            case 36:
            case 37:
            case 38:
            case 41:
            default:
                throw new IllegalArgumentException(format);
            case 1:
            case 4:
            case 6:
            case 7:
            case 8:
            case 12:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 24:
            case 25:
            case 27:
            case 29:
            case 32:
            case 33:
            case 35:
            case 39:
            case 40:
            case 42:
            case 43:
                throw new IllegalStateException(format);
        }
    }

    public static UnitTemplateManager getInstance(@NotNull Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        if (instance == null) {
            synchronized (UnitTemplateManager.class) {
                if (instance == null) {
                    instance = new UnitTemplateManager(project);
                }
            }
        }
        return instance;
    }

    public static UnitTemplateManager getDefaultInstance() {
        return getInstance(DefaultProjectFactory.getInstance().getDefaultProject());
    }

    UnitTemplateManager(final Project project) {
        this.myProject = project;
        this.myFileTemplatesLoader = new FileTemplatesLoader(project);
        this.myProjectScheme = (project == null || !project.isDefault()) ? new FileTemplatesScheme("Project") { // from class: com.aicode.template.fileloader.UnitTemplateManager.1
            static final /* synthetic */ boolean $assertionsDisabled;

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[2];
                objArr[0] = "com/aicode/template/fileloader/UnitTemplateManager$1";
                switch (i) {
                    case 0:
                    default:
                        objArr[1] = "getTemplatesDir";
                        break;
                    case 1:
                        objArr[1] = "getProject";
                        break;
                }
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
            }

            static {
                $assertionsDisabled = !UnitTemplateManager.class.desiredAssertionStatus();
            }

            @NotNull
            public String getTemplatesDir() {
                if (!$assertionsDisabled && project == null) {
                    throw new AssertionError();
                }
                String templateDir = FileUtilRt.toSystemDependentName(((Path) Objects.requireNonNull(ProjectKt.getStateStore(project).getDirectoryStorePath())).toFile().getPath() + "/fileTemplates");
                if (templateDir == null) {
                    $$$reportNull$$$0(0);
                }
                return templateDir;
            }

            @NotNull
            public Project getProject() {
                Project project2 = project;
                if (project2 == null) {
                    $$$reportNull$$$0(1);
                }
                return project2;
            }
        } : null;
    }

    private FileTemplatesLoader getSettings() {
        return this.myFileTemplatesLoader;
    }

    @NotNull
    public FileTemplatesScheme getCurrentScheme() {
        FileTemplatesScheme fileTemplatesScheme = this.myScheme;
        if (fileTemplatesScheme == null) {
            $$$reportNull$$$0(1);
        }
        return fileTemplatesScheme;
    }

    public void setCurrentScheme(@NotNull FileTemplatesScheme scheme) {
        if (scheme == null) {
            $$$reportNull$$$0(2);
        }
        for (FTManager child : getAllManagers()) {
            child.saveTemplates();
        }
        setScheme(scheme);
    }

    private void setScheme(@NotNull FileTemplatesScheme scheme) {
        if (scheme == null) {
            $$$reportNull$$$0(3);
        }
        this.myScheme = scheme;
        this.myInitialized = true;
    }

    @NotNull
    protected FileTemplateManager checkInitialized() {
        if (!this.myInitialized) {
            setScheme(this.myScheme);
        }
        if (this == null) {
            $$$reportNull$$$0(4);
        }
        return this;
    }

    @Nullable
    public FileTemplatesScheme getProjectScheme() {
        return this.myProjectScheme;
    }

    @NotNull
    public FileTemplate[] getTemplates(@NotNull String category) {
        if (category == null) {
            $$$reportNull$$$0(5);
        }
        if ("Tests".equals(category)) {
            FileTemplate[] internalTemplates = getInternalTemplates();
            if (internalTemplates == null) {
                $$$reportNull$$$0(6);
            }
            return internalTemplates;
        }
        if (!"Includes".equals(category)) {
            throw new IllegalArgumentException("Unknown category: " + category);
        }
        FileTemplate[] allPatterns = getAllPatterns();
        if (allPatterns == null) {
            $$$reportNull$$$0(7);
        }
        return allPatterns;
    }

    @NotNull
    public FileTemplate[] getAllTemplates() {
        FileTemplate[] fileTemplateArr = FileTemplate.EMPTY_ARRAY;
        if (fileTemplateArr == null) {
            $$$reportNull$$$0(8);
        }
        return fileTemplateArr;
    }

    public FileTemplate getTemplate(@NotNull String templateName) {
        if (templateName == null) {
            $$$reportNull$$$0(9);
            return null;
        }
        return null;
    }

    @NotNull
    public FileTemplate addTemplate(@NotNull String name, @NotNull String extension) {
        if (name == null) {
            $$$reportNull$$$0(10);
        }
        if (extension == null) {
            $$$reportNull$$$0(11);
        }
        FileTemplateBase addTemplate = getSettings().getCustomTestTemplatesManager().addTemplate(name, extension);
        if (addTemplate == null) {
            $$$reportNull$$$0(12);
        }
        return addTemplate;
    }

    public void removeTemplate(@NotNull FileTemplate template) {
        if (template == null) {
            $$$reportNull$$$0(13);
        }
    }

    @NotNull
    public Properties getDefaultProperties() {
        Properties props = new Properties();
        Calendar calendar = Calendar.getInstance();
        Date date = this.myTestDate == null ? calendar.getTime() : this.myTestDate;
        SimpleDateFormat sdfMonthNameShort = new SimpleDateFormat("MMM");
        SimpleDateFormat sdfMonthNameFull = new SimpleDateFormat("MMMM");
        SimpleDateFormat sdfDayNameShort = new SimpleDateFormat("EEE");
        SimpleDateFormat sdfDayNameFull = new SimpleDateFormat("EEEE");
        SimpleDateFormat sdfYearFull = new SimpleDateFormat("yyyy");
        props.setProperty("DATE", DateFormatUtil.formatDate(date));
        props.setProperty("TIME", DateFormatUtil.formatTime(date));
        props.setProperty("YEAR", sdfYearFull.format(date));
        props.setProperty("MONTH", getCalendarValue(calendar, 2));
        props.setProperty("MONTH_NAME_SHORT", sdfMonthNameShort.format(date));
        props.setProperty("MONTH_NAME_FULL", sdfMonthNameFull.format(date));
        props.setProperty("DAY", getCalendarValue(calendar, 5));
        props.setProperty("DAY_NAME_SHORT", sdfDayNameShort.format(date));
        props.setProperty("DAY_NAME_FULL", sdfDayNameFull.format(date));
        props.setProperty("HOUR", getCalendarValue(calendar, 11));
        props.setProperty("MINUTE", getCalendarValue(calendar, 12));
        props.setProperty("SECOND", getCalendarValue(calendar, 13));
        props.setProperty("USER", SystemProperties.getUserName());
        props.setProperty("PRODUCT_NAME", ApplicationNamesInfo.getInstance().getFullProductName());
        props.setProperty("DS", "$");
        props.setProperty("PROJECT_NAME", this.myProject.getName());
        if (props == null) {
            $$$reportNull$$$0(14);
        }
        return props;
    }

    @NotNull
    private static String getCalendarValue(Calendar calendar, int field) {
        int val = calendar.get(field);
        if (field == 2) {
            val++;
        }
        String result = Integer.toString(val);
        if (result.length() == 1) {
            String str = "0" + result;
            if (str == null) {
                $$$reportNull$$$0(15);
            }
            return str;
        }
        if (result == null) {
            $$$reportNull$$$0(16);
        }
        return result;
    }

    @NotNull
    public Collection<String> getRecentNames() {
        return new ArrayList();
    }

    public void addRecentName(@NotNull @NonNls String name) {
        if (name == null) {
            $$$reportNull$$$0(17);
        }
        LOG.info(name);
    }

    @NotNull
    public FileTemplate[] getInternalTemplates() {
        Collection<FileTemplateBase> allTemplates = getSettings().getInternalTestTemplatesManager().getAllTemplates(true);
        FileTemplate[] fileTemplateArr = (FileTemplate[]) allTemplates.toArray(FileTemplate.EMPTY_ARRAY);
        if (fileTemplateArr == null) {
            $$$reportNull$$$0(18);
        }
        return fileTemplateArr;
    }

    @NotNull
    public List<TemplateDescriptor> getTestTemplates() {
        Collection<FileTemplateBase> allTemplates = getSettings().getInternalTestTemplatesManager().getAllTemplates(true);
        Map<String, List<TemplateDescriptor>> oobEnabledTemplates = (Map) this.templateRegistry.getEnabledTemplateDescriptors().stream().collect(Collectors.groupingBy((v0) -> {
            return v0.getFilename();
        }));
        List<TemplateDescriptor> list = (List) allTemplates.stream().filter(t -> {
            return (t instanceof CustomFileTemplate) || oobEnabledTemplates.containsKey(t.getQualifiedName());
        }).map(t2 -> {
            return (TemplateDescriptor) Optional.ofNullable((List) oobEnabledTemplates.get(t2.getQualifiedName())).map(l -> {
                return (TemplateDescriptor) l.get(0);
            }).orElse(new TemplateDescriptor(t2.getName(), t2.getName(), t2.getQualifiedName(), TemplateRole.Tester));
        }).collect(Collectors.toList());
        if (list == null) {
            $$$reportNull$$$0(19);
        }
        return list;
    }

    public FileTemplate getInternalTemplate(@NotNull @NonNls String templateName) {
        if (templateName == null) {
            $$$reportNull$$$0(20);
        }
        return findInternalTemplate(templateName);
    }

    public FileTemplate findInternalTemplate(@NotNull @NonNls String templateName) {
        if (templateName == null) {
            $$$reportNull$$$0(21);
        }
        return getSettings().getInternalTestTemplatesManager().findTemplateByName(templateName);
    }

    public FileTemplate findCustomTestTemplate(@NotNull @NonNls String templateName) {
        if (templateName == null) {
            $$$reportNull$$$0(22);
        }
        return getSettings().getCustomTestTemplatesManager().findTemplateByName(templateName);
    }

    @NotNull
    public String internalTemplateToSubject(@NotNull @NonNls String templateName) {
        if (templateName == null) {
            $$$reportNull$$$0(23);
        }
        for (InternalTemplateBean bean : InternalTemplateBean.EP_NAME.getExtensionList()) {
            if (bean.name.equals(templateName) && bean.subject != null) {
                String str = bean.subject;
                if (str == null) {
                    $$$reportNull$$$0(24);
                }
                return str;
            }
        }
        String lowerCase = templateName.toLowerCase();
        if (lowerCase == null) {
            $$$reportNull$$$0(25);
        }
        return lowerCase;
    }

    @NotNull
    public FileTemplate getCodeTemplate(@NotNull @NonNls String templateName) {
        if (templateName == null) {
            $$$reportNull$$$0(26);
        }
        if (0 == 0) {
            $$$reportNull$$$0(27);
        }
        return null;
    }

    @NotNull
    public FileTemplate getJ2eeTemplate(@NotNull @NonNls String templateName) {
        if (templateName == null) {
            $$$reportNull$$$0(28);
        }
        if (0 == 0) {
            $$$reportNull$$$0(29);
        }
        return null;
    }

    @NotNull
    private static FileTemplate getTemplateFromManager(@NotNull String templateName, @NotNull FTManager ftManager) {
        if (templateName == null) {
            $$$reportNull$$$0(30);
        }
        if (ftManager == null) {
            $$$reportNull$$$0(31);
        }
        FileTemplateBase template = ftManager.getTemplate(templateName);
        if (template != null) {
            if (template == null) {
                $$$reportNull$$$0(32);
            }
            return template;
        }
        FileTemplateBase template2 = ftManager.findTemplateByName(templateName);
        if (template2 != null) {
            if (template2 == null) {
                $$$reportNull$$$0(33);
            }
            return template2;
        }
        String message = "Template not found: " + templateName;
        LOG.warn(message);
        throw new IllegalStateException(message);
    }

    @NotNull
    public FileTemplate getDefaultTemplate(@NotNull String name) {
        if (name == null) {
            $$$reportNull$$$0(34);
        }
        String templateQName = getQualifiedName(name);
        for (FTManager manager : getAllManagers()) {
            FileTemplateBase template = manager.getTemplate(templateQName);
            if (template != null) {
                if (template == null) {
                    $$$reportNull$$$0(35);
                }
                return template;
            }
        }
        String message = "Default template not found: " + name;
        LOG.warn(message);
        throw new RuntimeException(message);
    }

    public void setTemplates(@NotNull String templatesCategory, @NotNull Collection<? extends FileTemplate> templates) {
        if (templatesCategory == null) {
            $$$reportNull$$$0(36);
        }
        if (templates == null) {
            $$$reportNull$$$0(37);
        }
        for (FTManager manager : getAllManagers()) {
            if (templatesCategory.equals(manager.getName())) {
                manager.updateTemplates(templates);
                return;
            }
        }
    }

    @NotNull
    private String getQualifiedName(@NotNull String name) {
        if (name == null) {
            $$$reportNull$$$0(38);
        }
        String qualifiedName = FileTypeManagerEx.getInstanceEx().getExtension(name).isEmpty() ? FileTemplateBase.getQualifiedName(name, "java") : name;
        if (qualifiedName == null) {
            $$$reportNull$$$0(39);
        }
        return qualifiedName;
    }

    @NotNull
    public FileTemplate[] getAllPatterns() {
        Collection<FileTemplateBase> allTemplates = getSettings().getPatternsManager().getAllTemplates(false);
        FileTemplate[] fileTemplateArr = (FileTemplate[]) allTemplates.toArray(FileTemplate.EMPTY_ARRAY);
        if (fileTemplateArr == null) {
            $$$reportNull$$$0(40);
        }
        return fileTemplateArr;
    }

    public FileTemplate getPattern(@NotNull String name) {
        if (name == null) {
            $$$reportNull$$$0(41);
        }
        return getSettings().getPatternsManager().findTemplateByName(name);
    }

    @NotNull
    public FileTemplate[] getAllCodeTemplates() {
        FileTemplate[] fileTemplateArr = FileTemplate.EMPTY_ARRAY;
        if (fileTemplateArr == null) {
            $$$reportNull$$$0(42);
        }
        return fileTemplateArr;
    }

    @NotNull
    public FileTemplate[] getAllJ2eeTemplates() {
        FileTemplate[] fileTemplateArr = FileTemplate.EMPTY_ARRAY;
        if (fileTemplateArr == null) {
            $$$reportNull$$$0(43);
        }
        return fileTemplateArr;
    }

    public void saveAllTemplates() {
        for (FTManager manager : getAllManagers()) {
            manager.saveTemplates();
        }
    }

    public URL getDefaultTemplateDescription() {
        return this.myFileTemplatesLoader.getDefaultTemplateDescription();
    }

    URL getDefaultIncludeDescription() {
        return this.myFileTemplatesLoader.getDefaultIncludeDescription();
    }

    @TestOnly
    public void setTestDate(Date testDate) {
        this.myTestDate = testDate;
    }

    private FTManager[] getAllManagers() {
        return getSettings().getAllManagers();
    }
}
