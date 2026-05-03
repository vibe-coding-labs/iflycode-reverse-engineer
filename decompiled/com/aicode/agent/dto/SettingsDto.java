/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto;

public class SettingsDto {
    private boolean autoTriggerOnPause;
    private Integer autoTriggerTimeDelay;
    private String generateCodeMode;
    private String[] codeCompleteDisableLang;
    private String sendMessageType;
    private String javaTestFramework;
    private String javaMockFramework;
    private String lineToolsType;
    private boolean lineToolsPermissionDocComments;
    private boolean lineToolsPermissionLineComments;
    private boolean lineToolsPermissionComments;
    private boolean lineToolsPermissionFunctionSplit;
    private boolean lineToolsPermissionCodeOptimization;
    private boolean lineToolsPermissionUnitTesting;
    private boolean openFunctionSplit;
    private boolean openCodeOptimization;
    private boolean openIFlyTest;
    private boolean openInlineChat;
    private boolean openIFlyDBA;
    private boolean openIFlyOps;
    private boolean openIFlyPm;
    private boolean openCodeEnhance;
    private String inlineCompletionInputStyle;
    private boolean openAutoUpdate;
    private String defaultLanguage;

    public String getInlineCompletionInputStyle() {
        return this.inlineCompletionInputStyle;
    }

    public void setInlineCompletionInputStyle(String inlineCompletionInputStyle) {
        this.inlineCompletionInputStyle = inlineCompletionInputStyle;
    }

    public String getLineToolsType() {
        return this.lineToolsType;
    }

    public void setLineToolsType(String lineToolsType) {
        this.lineToolsType = lineToolsType;
    }

    public boolean isAutoTriggerOnPause() {
        return this.autoTriggerOnPause;
    }

    public void setAutoTriggerOnPause(boolean autoTriggerOnPause) {
        this.autoTriggerOnPause = autoTriggerOnPause;
    }

    public Integer getAutoTriggerTimeDelay() {
        return this.autoTriggerTimeDelay;
    }

    public void setAutoTriggerTimeDelay(Integer autoTriggerTimeDelay) {
        this.autoTriggerTimeDelay = autoTriggerTimeDelay;
    }

    public String getGenerateCodeMode() {
        return this.generateCodeMode;
    }

    public void setGenerateCodeMode(String generateCodeMode) {
        this.generateCodeMode = generateCodeMode;
    }

    public String[] getCodeCompleteDisableLang() {
        return this.codeCompleteDisableLang;
    }

    public void setCodeCompleteDisableLang(String[] codeCompleteDisableLang) {
        this.codeCompleteDisableLang = codeCompleteDisableLang;
    }

    public String getSendMessageType() {
        return this.sendMessageType;
    }

    public void setSendMessageType(String sendMessageType) {
        this.sendMessageType = sendMessageType;
    }

    public String getJavaTestFramework() {
        return this.javaTestFramework;
    }

    public void setJavaTestFramework(String javaTestFramework) {
        this.javaTestFramework = javaTestFramework;
    }

    public String getJavaMockFramework() {
        return this.javaMockFramework;
    }

    public void setJavaMockFramework(String javaMockFramework) {
        this.javaMockFramework = javaMockFramework;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public String getDefaultLanguage() {
        return this.defaultLanguage;
    }

    public boolean isLineToolsPermissionDocComments() {
        return this.lineToolsPermissionDocComments;
    }

    public void setLineToolsPermissionDocComments(boolean lineToolsPermissionDocComments) {
        this.lineToolsPermissionDocComments = lineToolsPermissionDocComments;
    }

    public boolean isLineToolsPermissionLineComments() {
        return this.lineToolsPermissionLineComments;
    }

    public void setLineToolsPermissionLineComments(boolean lineToolsPermissionLineComments) {
        this.lineToolsPermissionLineComments = lineToolsPermissionLineComments;
    }

    public boolean isLineToolsPermissionComments() {
        return this.lineToolsPermissionComments;
    }

    public void setLineToolsPermissionComments(boolean lineToolsPermissionComments) {
        this.lineToolsPermissionComments = lineToolsPermissionComments;
    }

    public boolean isLineToolsPermissionFunctionSplit() {
        return this.lineToolsPermissionFunctionSplit;
    }

    public void setLineToolsPermissionFunctionSplit(boolean lineToolsPermissionFunctionSplit) {
        this.lineToolsPermissionFunctionSplit = lineToolsPermissionFunctionSplit;
    }

    public boolean isLineToolsPermissionCodeOptimization() {
        return this.lineToolsPermissionCodeOptimization;
    }

    public void setLineToolsPermissionCodeOptimization(boolean lineToolsPermissionCodeOptimization) {
        this.lineToolsPermissionCodeOptimization = lineToolsPermissionCodeOptimization;
    }

    public boolean isLineToolsPermissionUnitTesting() {
        return this.lineToolsPermissionUnitTesting;
    }

    public void setLineToolsPermissionUnitTesting(boolean lineToolsPermissionUnitTesting) {
        this.lineToolsPermissionUnitTesting = lineToolsPermissionUnitTesting;
    }

    public boolean isOpenCodeEnhance() {
        return this.openCodeEnhance;
    }

    public void setOpenCodeEnhance(boolean openCodeEnhance) {
        this.openCodeEnhance = openCodeEnhance;
    }

    public boolean isOpenFunctionSplit() {
        return this.openFunctionSplit;
    }

    public void setOpenFunctionSplit(boolean openFunctionSplit) {
        this.openFunctionSplit = openFunctionSplit;
    }

    public boolean isOpenCodeOptimization() {
        return this.openCodeOptimization;
    }

    public void setOpenCodeOptimization(boolean openCodeOptimization) {
        this.openCodeOptimization = openCodeOptimization;
    }

    public boolean isOpenIFlyTest() {
        return this.openIFlyTest;
    }

    public void setOpenIFlyTest(boolean openIFlyTest) {
        this.openIFlyTest = openIFlyTest;
    }

    public boolean isOpenInlineChat() {
        return this.openInlineChat;
    }

    public void setOpenInlineChat(boolean openInlineChat) {
        this.openInlineChat = openInlineChat;
    }

    public boolean isOpenIFlyDBA() {
        return this.openIFlyDBA;
    }

    public void setOpenIFlyDBA(boolean openIFlyDBA) {
        this.openIFlyDBA = openIFlyDBA;
    }

    public boolean isOpenIFlyOps() {
        return this.openIFlyOps;
    }

    public void setOpenIFlyOps(boolean openIFlyOps) {
        this.openIFlyOps = openIFlyOps;
    }

    public boolean isOpenIFlyPm() {
        return this.openIFlyPm;
    }

    public void setOpenIFlyPm(boolean openIFlyPm) {
        this.openIFlyPm = openIFlyPm;
    }

    public boolean isOpenAutoUpdate() {
        return this.openAutoUpdate;
    }

    public void setOpenAutoUpdate(boolean openAutoUpdate) {
        this.openAutoUpdate = openAutoUpdate;
    }
}
