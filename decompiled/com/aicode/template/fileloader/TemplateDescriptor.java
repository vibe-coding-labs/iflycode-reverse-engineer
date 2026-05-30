package com.aicode.template.fileloader;

import java.util.Objects;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/fileloader/TemplateDescriptor.class */
public class TemplateDescriptor {
    private String htmlDisplayName;
    private String displayName;
    private String tokenizedName;
    private String filename;
    private TemplateRole templateRole;
    private String framework;
    private String mockFramework;
    public static final String LANGUAGE_JAVA = "java";

    TemplateDescriptor() {
        this.mockFramework = "";
    }

    public TemplateDescriptor(String htmlDisplayName, String tokenizedName, String filename, TemplateRole templateRole) {
        this.mockFramework = "";
        this.htmlDisplayName = htmlDisplayName;
        this.displayName = htmlDisplayName;
        this.tokenizedName = tokenizedName;
        this.filename = filename;
        this.templateRole = templateRole;
        String[] temp = filename.split("&");
        if (temp.length > 1) {
            this.mockFramework = temp[1];
            this.mockFramework = this.mockFramework.replaceAll("." + "java".toLowerCase(), "");
            this.framework = temp[0];
        } else if (temp.length == 1) {
            this.framework = temp[0];
        }
    }

    public String getHtmlDisplayName() {
        return this.htmlDisplayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getFilename() {
        return this.filename;
    }

    public boolean isEnabled() {
        return true;
    }

    public String getFramework() {
        return this.framework;
    }

    public String getMockFramework() {
        return this.mockFramework;
    }

    public String getTokenizedName() {
        return this.tokenizedName;
    }

    public String getLanguage() {
        return "java".toLowerCase();
    }

    public String toString() {
        return "TemplateDescriptor{language=java, htmlDisplayName='" + this.htmlDisplayName + "', displayName='" + this.displayName + "', tokenizedName='" + this.tokenizedName + "', filename='" + this.filename + "'}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TemplateDescriptor that = (TemplateDescriptor) o;
        return Objects.equals(this.htmlDisplayName, that.htmlDisplayName) && Objects.equals(this.filename, that.filename);
    }

    public int hashCode() {
        return Objects.hash(this.htmlDisplayName, this.filename);
    }

    public TemplateRole getTemplateRole() {
        return this.templateRole;
    }
}
