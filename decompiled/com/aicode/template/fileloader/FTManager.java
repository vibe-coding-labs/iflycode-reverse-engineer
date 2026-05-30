package com.aicode.template.fileloader;

import com.intellij.application.options.CodeStyle;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.impl.CustomFileTemplate;
import com.intellij.ide.fileTemplates.impl.DefaultTemplate;
import com.intellij.ide.fileTemplates.impl.FileTemplateBase;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.text.StringUtilRt;
import com.intellij.util.io.PathKt;
import gnu.trove.THashMap;
import gnu.trove.THashSet;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/fileloader/FTManager.class */
public class FTManager {
    private static final Logger LOG = Logger.getInstance("#FTManager");
    private static final String DEFAULT_TEMPLATE_EXTENSION = "ft";
    static final String TEMPLATE_EXTENSION_SUFFIX = ".ft";
    private static final String ENCODED_NAME_EXT_DELIMITER = "༏ext༏.";
    private final String myName;
    private final boolean myInternal;
    private final Path myTemplatesDir;

    @Nullable
    private final FTManager myOriginal;
    private final Map<String, FileTemplateBase> myTemplates;
    private volatile List<FileTemplateBase> mySortedTemplates;
    private final List<DefaultTemplate> myDefaultTemplates;
    private final TemplateRegistry templateRegistry;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 26:
            case 27:
            case 29:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 5:
            case 6:
            case 7:
            case 12:
            case 16:
            case 23:
            case 24:
            case 25:
            case 28:
            case 30:
            case 31:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 26:
            case 27:
            case 29:
            default:
                i2 = 3;
                break;
            case 5:
            case 6:
            case 7:
            case 12:
            case 16:
            case 23:
            case 24:
            case 25:
            case 28:
            case 30:
            case 31:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 0:
            case 2:
            case 10:
            default:
                objArr[0] = "name";
                break;
            case 1:
            case 3:
                objArr[0] = "defaultTemplatesDirName";
                break;
            case 4:
                objArr[0] = "original";
                break;
            case 5:
            case 6:
            case 7:
            case 12:
            case 16:
            case 23:
            case 24:
            case 25:
            case 28:
            case 30:
            case 31:
                objArr[0] = "com/aicode/template/fileloader/FTManager";
                break;
            case 8:
                objArr[0] = "templateQname";
                break;
            case 9:
            case 26:
                objArr[0] = "templateName";
                break;
            case 11:
            case 27:
                objArr[0] = "extension";
                break;
            case 13:
                objArr[0] = "newTemplates";
                break;
            case 14:
                objArr[0] = "templates";
                break;
            case 15:
            case 20:
                objArr[0] = "template";
                break;
            case 17:
            case 29:
                objArr[0] = "fileName";
                break;
            case 18:
                objArr[0] = "file";
                break;
            case 19:
                objArr[0] = "parentDir";
                break;
            case 21:
                objArr[0] = "lineSeparator";
                break;
            case 22:
                objArr[0] = "templateFile";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 26:
            case 27:
            case 29:
            default:
                objArr[1] = "com/aicode/template/fileloader/FTManager";
                break;
            case 5:
                objArr[1] = "getName";
                break;
            case 6:
                objArr[1] = "getAllTemplates";
                break;
            case 7:
                objArr[1] = "sortTemplates";
                break;
            case 12:
                objArr[1] = "addTemplate";
                break;
            case 16:
                objArr[1] = "createAndStoreBundledTemplate";
                break;
            case 23:
            case 24:
                objArr[1] = "startWriteOrCreate";
                break;
            case 25:
                objArr[1] = "getConfigRoot";
                break;
            case 28:
                objArr[1] = "encodeFileName";
                break;
            case 30:
                objArr[1] = "decodeFileName";
                break;
            case 31:
                objArr[1] = "getTemplates";
                break;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            default:
                objArr[2] = "<init>";
                break;
            case 5:
            case 6:
            case 7:
            case 12:
            case 16:
            case 23:
            case 24:
            case 25:
            case 28:
            case 30:
            case 31:
                break;
            case 8:
                objArr[2] = "getTemplate";
                break;
            case 9:
                objArr[2] = "findTemplateByName";
                break;
            case 10:
            case 11:
                objArr[2] = "addTemplate";
                break;
            case 13:
                objArr[2] = "updateTemplates";
                break;
            case 14:
                objArr[2] = "setDefaultTemplates";
                break;
            case 15:
                objArr[2] = "createAndStoreBundledTemplate";
                break;
            case 17:
            case 18:
                objArr[2] = "addTemplateFromFile";
                break;
            case 19:
            case 20:
            case 21:
                objArr[2] = "saveTemplate";
                break;
            case 22:
                objArr[2] = "startWriteOrCreate";
                break;
            case 26:
            case 27:
                objArr[2] = "encodeFileName";
                break;
            case 29:
                objArr[2] = "decodeFileName";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 8:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 26:
            case 27:
            case 29:
            default:
                throw new IllegalArgumentException(format);
            case 5:
            case 6:
            case 7:
            case 12:
            case 16:
            case 23:
            case 24:
            case 25:
            case 28:
            case 30:
            case 31:
                throw new IllegalStateException(format);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FTManager(@NotNull @NonNls String name, @NotNull @NonNls Path defaultTemplatesDirName) {
        this(name, defaultTemplatesDirName, false);
        if (name == null) {
            $$$reportNull$$$0(0);
        }
        if (defaultTemplatesDirName == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FTManager(@NotNull @NonNls String name, @NotNull @NonNls Path defaultTemplatesDirName, boolean internal) {
        if (name == null) {
            $$$reportNull$$$0(2);
        }
        if (defaultTemplatesDirName == null) {
            $$$reportNull$$$0(3);
        }
        this.myTemplates = new HashMap();
        this.myDefaultTemplates = new ArrayList();
        this.templateRegistry = new TemplateRegistry();
        this.myName = name;
        this.myInternal = internal;
        this.myTemplatesDir = defaultTemplatesDirName;
        this.myOriginal = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FTManager(@NotNull FTManager original) {
        if (original == null) {
            $$$reportNull$$$0(4);
        }
        this.myTemplates = new HashMap();
        this.myDefaultTemplates = new ArrayList();
        this.templateRegistry = new TemplateRegistry();
        this.myOriginal = original;
        this.myName = original.getName();
        this.myTemplatesDir = original.myTemplatesDir;
        this.myInternal = original.myInternal;
        this.myTemplates.putAll(original.myTemplates);
        this.myDefaultTemplates.addAll(original.myDefaultTemplates);
    }

    @NotNull
    public String getName() {
        String str = this.myName;
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NotNull
    public Collection<FileTemplateBase> getAllTemplates(boolean includeDisabled) {
        List<FileTemplateBase> sorted = this.mySortedTemplates;
        if (sorted == null) {
            sorted = sortTemplates();
            this.mySortedTemplates = sorted;
        }
        List<FileTemplateBase> list = sorted;
        if (list == null) {
            $$$reportNull$$$0(6);
        }
        return list;
    }

    @NotNull
    private List<FileTemplateBase> sortTemplates() {
        List<FileTemplateBase> oobTemplates = (List) getTemplates().values().stream().filter((v0) -> {
            return v0.isDefault();
        }).collect(Collectors.toList());
        sortOobTemplates(oobTemplates);
        List<FileTemplateBase> customTemplates = (List) getTemplates().values().stream().filter(fileTemplateBase -> {
            return !fileTemplateBase.isDefault();
        }).collect(Collectors.toList());
        sort(customTemplates);
        oobTemplates.addAll(customTemplates);
        if (oobTemplates == null) {
            $$$reportNull$$$0(7);
        }
        return oobTemplates;
    }

    private void sortOobTemplates(List<FileTemplateBase> oobTemplates) {
        List<String> sortedOobTemplateNames = (List) this.templateRegistry.getEnabledTemplateDescriptors().stream().map((v0) -> {
            return v0.getFilename();
        }).collect(Collectors.toList());
        oobTemplates.sort(Comparator.comparing(o -> {
            return indexOf(sortedOobTemplateNames, o);
        }));
    }

    private Integer indexOf(List<String> sortedOobTemplateNames, FileTemplateBase template) {
        return Integer.valueOf(sortedOobTemplateNames.indexOf(template.getQualifiedName()));
    }

    private void sort(List<FileTemplateBase> sorted) {
        sorted.sort((t1, t2) -> {
            return t1.getName().compareToIgnoreCase(t2.getName());
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public FileTemplateBase getTemplate(@NotNull String templateQname) {
        if (templateQname == null) {
            $$$reportNull$$$0(8);
        }
        return getTemplates().get(templateQname);
    }

    @Nullable
    public FileTemplateBase findTemplateByName(@NotNull String templateName) {
        if (templateName == null) {
            $$$reportNull$$$0(9);
        }
        FileTemplateBase template = getTemplates().get(templateName);
        if (template != null) {
            return template;
        }
        for (FileTemplateBase t : getAllTemplates(false)) {
            String qName = t.getQualifiedName();
            if (qName.startsWith(templateName) && qName.length() > templateName.length()) {
                String remainder = qName.substring(templateName.length());
                if (remainder.startsWith("༏ext༏.") || remainder.charAt(0) == '.') {
                    return t;
                }
            }
        }
        return null;
    }

    @NotNull
    public FileTemplateBase addTemplate(@NotNull String name, @NotNull String extension) {
        if (name == null) {
            $$$reportNull$$$0(10);
        }
        if (extension == null) {
            $$$reportNull$$$0(11);
        }
        String qName = FileTemplateBase.getQualifiedName(name, extension);
        FileTemplateBase template = getTemplate(qName);
        if (template == null) {
            template = new CustomFileTemplate(name, extension);
            getTemplates().put(qName, template);
            this.mySortedTemplates = null;
        }
        FileTemplateBase fileTemplateBase = template;
        if (fileTemplateBase == null) {
            $$$reportNull$$$0(12);
        }
        return fileTemplateBase;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void updateTemplates(@NotNull Collection<? extends FileTemplate> newTemplates) {
        if (newTemplates == null) {
            $$$reportNull$$$0(13);
        }
        restoreDefaults();
        for (FileTemplate template : newTemplates) {
            FileTemplateBase _template = addTemplate(template.getName(), template.getExtension());
            _template.setText(template.getText());
            _template.setReformatCode(template.isReformatCode());
            _template.setLiveTemplateEnabled(template.isLiveTemplateEnabled());
        }
        saveTemplates(true);
    }

    private void restoreDefaults() {
        getTemplates().clear();
        this.mySortedTemplates = null;
        for (DefaultTemplate template : this.myDefaultTemplates) {
            if (template != null) {
                createAndStoreBundledTemplate(template);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setDefaultTemplates(@NotNull Collection<DefaultTemplate> templates) {
        if (templates == null) {
            $$$reportNull$$$0(14);
        }
        this.myDefaultTemplates.clear();
        this.myDefaultTemplates.addAll(templates);
        for (DefaultTemplate template : templates) {
            if (template != null) {
                createAndStoreBundledTemplate(template);
            }
        }
    }

    @NotNull
    private UnitFileTemplate createAndStoreBundledTemplate(@NotNull DefaultTemplate template) {
        if (template == null) {
            $$$reportNull$$$0(15);
        }
        UnitFileTemplate testMeFileTemplate = new UnitFileTemplate(template.getName(), template.getExtension(), true);
        testMeFileTemplate.setText(template.getText());
        testMeFileTemplate.setDescription(template.getDescriptionText());
        Optional<U> map = this.templateRegistry.getEnabledTemplateDescriptors().stream().filter(t -> {
            return t.getFilename().equals(template.getName() + "." + template.getExtension());
        }).findAny().map((v0) -> {
            return v0.getHtmlDisplayName();
        });
        Objects.requireNonNull(testMeFileTemplate);
        map.ifPresent(testMeFileTemplate::setDisplayName);
        String qName = testMeFileTemplate.getQualifiedName();
        FileTemplateBase previous = getTemplates().put(qName, testMeFileTemplate);
        this.mySortedTemplates = null;
        LOG.assertTrue(previous == null, "Duplicate bundled template " + qName + " [" + previous + "]");
        if (testMeFileTemplate == null) {
            $$$reportNull$$$0(16);
        }
        return testMeFileTemplate;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void loadCustomizedContent() {
        List<Path> templateWithDefaultExtension = new ArrayList<>();
        THashSet tHashSet = new THashSet();
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(getConfigRoot(), (DirectoryStream.Filter<? super Path>) file -> {
                return (Files.isDirectory(file, new LinkOption[0]) || Files.isHidden(file)) ? false : true;
            });
            try {
                for (Path file2 : stream) {
                    String fileName = file2.getFileName().toString();
                    if (!FileTypeManager.getInstance().isFileIgnored(fileName)) {
                        if (fileName.endsWith(".ft")) {
                            templateWithDefaultExtension.add(file2);
                        } else {
                            tHashSet.add(fileName);
                            addTemplateFromFile(fileName, file2);
                        }
                    }
                }
                if (stream != null) {
                    stream.close();
                }
            } catch (Throwable th) {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (NoSuchFileException e) {
        } catch (IOException e2) {
            LOG.warn(e2);
            return;
        }
        for (Path file3 : templateWithDefaultExtension) {
            String name = file3.getFileName().toString();
            String name2 = name.substring(0, name.length() - ".ft".length());
            if (!tHashSet.contains(name2)) {
                addTemplateFromFile(name2, file3);
            }
            try {
                Files.delete(file3);
            } catch (IOException e3) {
                LOG.warn(e3);
            }
        }
    }

    private void addTemplateFromFile(@NotNull String fileName, @NotNull Path file) {
        if (fileName == null) {
            $$$reportNull$$$0(17);
        }
        if (file == null) {
            $$$reportNull$$$0(18);
        }
        Pair<String, String> nameExt = decodeFileName(fileName);
        String extension = (String) nameExt.second;
        String templateQName = (String) nameExt.first;
        if (templateQName.isEmpty()) {
            return;
        }
        try {
            addTemplate(templateQName, extension).setText(PathKt.readText(file));
        } catch (IOException e) {
            LOG.warn(e);
        }
    }

    public void saveTemplates() {
        saveTemplates(false);
    }

    private void saveTemplates(boolean removeDeleted) {
        THashSet<String> tHashSet = new THashSet();
        Path configRoot = getConfigRoot();
        THashMap tHashMap = new THashMap();
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(getConfigRoot(), (DirectoryStream.Filter<? super Path>) file -> {
                return (Files.isDirectory(file, new LinkOption[0]) || Files.isHidden(file)) ? false : true;
            });
            try {
                for (Path file2 : stream) {
                    String fileName = file2.getFileName().toString();
                    tHashMap.put(fileName, file2);
                    tHashSet.add(fileName);
                }
                if (stream != null) {
                    stream.close();
                }
            } catch (Throwable th) {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (NoSuchFileException e) {
        } catch (IOException e2) {
            LOG.warn(e2);
        }
        THashMap tHashMap2 = new THashMap();
        for (FileTemplateBase template : getAllTemplates(true)) {
            String name = template.getQualifiedName();
            if (template instanceof CustomFileTemplate) {
                tHashMap2.put(name, template);
                tHashSet.add(name);
            }
        }
        if (tHashSet.isEmpty()) {
            return;
        }
        try {
            Files.createDirectories(this.myTemplatesDir, new FileAttribute[0]);
        } catch (IOException e3) {
            LOG.info("Cannot create directory: " + this.myTemplatesDir);
        }
        String lineSeparator = CodeStyle.getDefaultSettings().getLineSeparator();
        for (String name2 : tHashSet) {
            Path customizedTemplateFile = (Path) tHashMap.get(name2);
            FileTemplateBase templateToSave = (FileTemplateBase) tHashMap2.get(name2);
            if (customizedTemplateFile == null) {
                try {
                    saveTemplate(configRoot, templateToSave, lineSeparator);
                } catch (IOException e4) {
                    LOG.warn("Unable to save template " + name2, e4);
                }
            } else if (templateToSave == null) {
                if (removeDeleted) {
                    try {
                        Files.delete(customizedTemplateFile);
                    } catch (IOException e5) {
                        LOG.warn(e5);
                    }
                }
            } else {
                try {
                    String diskText = StringUtilRt.convertLineSeparators(PathKt.readText(customizedTemplateFile));
                    String templateText = templateToSave.getText();
                    if (!diskText.equals(templateText)) {
                        saveTemplate(configRoot, templateToSave, lineSeparator);
                    }
                } catch (IOException e6) {
                    LOG.warn("Unable to save template " + name2, e6);
                }
            }
        }
    }

    private static void saveTemplate(@NotNull Path parentDir, @NotNull FileTemplateBase template, @NotNull String lineSeparator) throws IOException {
        if (parentDir == null) {
            $$$reportNull$$$0(19);
        }
        if (template == null) {
            $$$reportNull$$$0(20);
        }
        if (lineSeparator == null) {
            $$$reportNull$$$0(21);
        }
        String name = template.getName();
        String extension = template.getExtension();
        String extSuffix = "." + extension;
        if (name.endsWith(extSuffix)) {
            name = name.substring(0, name.length() - extSuffix.length());
        }
        String fileName = name;
        Path templateFile = parentDir.resolve(encodeFileName(fileName, extension));
        OutputStream fileOutputStream = startWriteOrCreate(templateFile);
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            try {
                String content = template.getText();
                if (!lineSeparator.equals("\n")) {
                    content = StringUtilRt.convertLineSeparators(content, lineSeparator);
                }
                outputStreamWriter.write(content);
                outputStreamWriter.close();
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } finally {
            }
        } catch (Throwable th) {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @NotNull
    private static OutputStream startWriteOrCreate(@NotNull Path templateFile) throws IOException {
        if (templateFile == null) {
            $$$reportNull$$$0(22);
        }
        try {
            OutputStream newOutputStream = Files.newOutputStream(templateFile, new OpenOption[0]);
            if (newOutputStream == null) {
                $$$reportNull$$$0(23);
            }
            return newOutputStream;
        } catch (NoSuchFileException e) {
            PathKt.delete(templateFile);
            OutputStream newOutputStream2 = Files.newOutputStream(templateFile, new OpenOption[0]);
            if (newOutputStream2 == null) {
                $$$reportNull$$$0(24);
            }
            return newOutputStream2;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NotNull
    public Path getConfigRoot() {
        Path path = this.myTemplatesDir;
        if (path == null) {
            $$$reportNull$$$0(25);
        }
        return path;
    }

    public String toString() {
        return this.myName + " file template manager";
    }

    @NotNull
    public static String encodeFileName(@NotNull String templateName, @NotNull String extension) {
        if (templateName == null) {
            $$$reportNull$$$0(26);
        }
        if (extension == null) {
            $$$reportNull$$$0(27);
        }
        String nameExtDelimiter = extension.contains(".") ? "༏ext༏." : ".";
        String str = templateName + nameExtDelimiter + extension;
        if (str == null) {
            $$$reportNull$$$0(28);
        }
        return str;
    }

    @NotNull
    private static Pair<String, String> decodeFileName(@NotNull String fileName) {
        if (fileName == null) {
            $$$reportNull$$$0(29);
        }
        String name = fileName;
        String ext = "";
        String nameExtDelimiter = fileName.contains("༏ext༏.") ? "༏ext༏." : ".";
        int extIndex = fileName.lastIndexOf(nameExtDelimiter);
        if (extIndex >= 0) {
            name = fileName.substring(0, extIndex);
            ext = fileName.substring(extIndex + nameExtDelimiter.length());
        }
        Pair<String, String> create = Pair.create(name, ext);
        if (create == null) {
            $$$reportNull$$$0(30);
        }
        return create;
    }

    @NotNull
    public Map<String, FileTemplateBase> getTemplates() {
        Map<String, FileTemplateBase> map = this.myOriginal != null ? this.myOriginal.myTemplates : this.myTemplates;
        if (map == null) {
            $$$reportNull$$$0(31);
        }
        return map;
    }
}
