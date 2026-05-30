package com.aicode.template.fileloader;

import com.intellij.ide.fileTemplates.impl.FileTemplateBase;
import org.jetbrains.annotations.NotNull;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/fileloader/UnitFileTemplate.class */
public class UnitFileTemplate extends FileTemplateBase {
    private String name;
    private String displayName;
    private boolean isDefault;
    private String description;
    private String extension;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 0:
            case 2:
            case 3:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 1:
            case 4:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 0:
            case 2:
            case 3:
            default:
                i2 = 2;
                break;
            case 1:
            case 4:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 0:
            case 2:
            case 3:
            default:
                objArr[0] = "com/aicode/template/fileloader/UnitFileTemplate";
                break;
            case 1:
                objArr[0] = "name";
                break;
            case 4:
                objArr[0] = "extension";
                break;
        }
        switch (i) {
            case 0:
            default:
                objArr[1] = "getName";
                break;
            case 1:
            case 4:
                objArr[1] = "com/aicode/template/fileloader/UnitFileTemplate";
                break;
            case 2:
                objArr[1] = "getDescription";
                break;
            case 3:
                objArr[1] = "getExtension";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "setName";
                break;
            case 4:
                objArr[2] = "setExtension";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 0:
            case 2:
            case 3:
            default:
                throw new IllegalStateException(format);
            case 1:
            case 4:
                throw new IllegalArgumentException(format);
        }
    }

    public UnitFileTemplate(String name, String extension, boolean isDefault) {
        this.name = name;
        this.extension = extension;
        this.isDefault = isDefault;
    }

    @NotNull
    public String getName() {
        String str = this.name;
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        return str;
    }

    public void setName(@NotNull String name) {
        if (name == null) {
            $$$reportNull$$$0(1);
        }
        this.name = name;
    }

    public boolean isDefault() {
        return this.isDefault;
    }

    @NotNull
    public String getDescription() {
        String str = this.description;
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return str;
    }

    @NotNull
    public String getExtension() {
        String str = this.extension;
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return str;
    }

    public void setExtension(@NotNull String extension) {
        if (extension == null) {
            $$$reportNull$$$0(4);
        }
        this.extension = extension;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName == null ? this.name : this.displayName;
    }

    public String toString() {
        return "UnitFileTemplate{name='" + this.name + "', displayName='" + this.displayName + "', isDefault=" + this.isDefault + ", extension='" + this.extension + "'}";
    }
}
