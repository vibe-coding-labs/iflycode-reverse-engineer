package com.aicode.settings;

import com.intellij.util.xmlb.annotations.OptionTag;
import java.awt.Color;
import javax.annotation.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/settings/CodeGenerateRequestState.class */
public class CodeGenerateRequestState {

    @Nullable
    @OptionTag(value = "inlayTextColor", converter = ColorConverter.class)
    public Color inlayTextColor = null;

    @OptionTag("showIdeCompletions")
    private boolean showIdeCodeTips = false;

    @OptionTag("disableHttpCache")
    public transient boolean internalDisableHttpCache = false;
    public boolean requestLimitNotificationShown = false;

    public boolean isShowIdeCodeTips() {
        return this.showIdeCodeTips;
    }
}
