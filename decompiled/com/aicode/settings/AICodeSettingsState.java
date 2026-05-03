/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.application.ApplicationManager
 *  com.intellij.openapi.components.PersistentStateComponent
 *  com.intellij.openapi.components.State
 *  com.intellij.openapi.components.Storage
 *  com.intellij.util.xmlb.XmlSerializerUtil
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.aicode.settings;

import com.aicode.PluginStartupActivity;
import com.aicode.agent.dto.FunctionModelInfo;
import com.aicode.enums.LineToolsTypeEnum;
import com.aicode.enums.PyUnitTestBaseEnum;
import com.aicode.enums.PyUnitTestMockEnum;
import com.aicode.enums.SendKeyEnum;
import com.aicode.enums.TipTypeEnum;
import com.aicode.enums.UnitTestBaseEnum;
import com.aicode.enums.UnitTestMockEnum;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name="com.aicode.settings.AICodeSettingsState", storages={@Storage(value="AICodeSettingsPlugin.xml")})
public class AICodeSettingsState
implements PersistentStateComponent<AICodeSettingsState> {
    public boolean autoTrigger = true;
    public String tipType = TipTypeEnum.INTELLIGENT_MODE.name();
    public String sendKey = SendKeyEnum.ENTER_KEY.getText();
    public String modelCode = "";
    public List<FunctionModelInfo> modelInfoList;
    public String inlineChatModelCode = "";
    public Integer triggerTime = 200;
    public String loginUrl = "";
    public String feedbackUrl = "";
    public String maintainRepoUrl = "";
    public String codeSearchServerUrl = "";
    public String officialWebsiteUrl = "";
    public String codeKnowledgeWebUrl = "";
    public String userCenterWebUrl = "";
    public String enterpriseId = "";
    public String enterpriseName = "";
    public String userId = "";
    public String userName = "";
    public boolean isUpdater = false;
    public String testFramework = UnitTestBaseEnum.JUNIT_FOUR.getName();
    public String mockFramework = UnitTestMockEnum.POWER_MOCK.getName();
    public boolean modifyTestFrame = false;
    public Integer modifyTestFramenNum = 0;
    public String pyTestFramework = PyUnitTestBaseEnum.UNITTEST.getName();
    public String pyMockFramework = PyUnitTestMockEnum.UNITTESTMOCK.getName();
    public boolean pyModifyTestFrame = false;
    public Integer pyModifyTestFramenNum = 0;
    public Map<String, String> modelList = new HashMap<String, String>();
    public String[] codeCompleteDisableLang = new String[]{"txt", "md"};
    public String generateUnitTestFile = "";
    public Integer unitRequestInterval = 8;
    public String lineToolsType = LineToolsTypeEnum.ICON.getCode();
    public boolean lineToolsPermissionDocComments = true;
    public boolean lineToolsPermissionLineComments = true;
    public boolean lineToolsPermissionComments = true;
    public boolean lineToolsPermissionFunctionSplit = true;
    public boolean lineToolsPermissionCodeOptimization = true;
    public boolean lineToolsPermissionUnitTesting = true;
    public boolean openFunctionSplit = true;
    public boolean openCodeOptimization = true;
    public boolean openIFlyTest = true;
    public boolean openInlineChat = true;
    public boolean openIFlyDBA = true;
    public boolean openIFlyOps = true;
    public boolean openIFlyPm = true;
    public LinkedHashSet<String> permissions = new LinkedHashSet();
    public boolean enableCodeDebug = true;
    public boolean enableCodeComplete = false;
    public boolean openAutoUpdate = true;
    public Boolean apmEnable;
    public String apmUrl;
    public boolean streamOutputConfig = false;
    public boolean openCodeEnhance = true;
    public boolean enableCodeEnhance = false;
    public String inlineCompletionInputStyle = "DISPOSABLE";
    public String defaultLanguage = "auto";
    public List<String> languages = new ArrayList<String>();
    public boolean showSaasQrCode = false;
    public boolean ignoreGitAuth = false;
    public String ignoreVersion;

    public static AICodeSettingsState getInstance() {
        return (AICodeSettingsState)ApplicationManager.getApplication().getService(AICodeSettingsState.class);
    }

    @Nullable
    public AICodeSettingsState getState() {
        return this;
    }

    public void loadState(@NotNull AICodeSettingsState state) {
        if (state == null) {
            AICodeSettingsState.$$$reportNull$$$0(0);
        }
        XmlSerializerUtil.copyBean((Object)state, (Object)this);
    }

    public void clear() {
        PluginStartupActivity.setApiKey("");
        this.userName = "";
        this.loginUrl = "";
        this.feedbackUrl = "";
        this.maintainRepoUrl = "";
        this.codeSearchServerUrl = "";
        this.officialWebsiteUrl = "";
        this.enterpriseId = "";
        this.enterpriseName = "";
        this.userId = "";
        this.generateUnitTestFile = "";
        this.unitRequestInterval = 5;
        this.modelList.clear();
        this.permissions.clear();
    }

    public void setUnitRequestInterval(int newInterval) {
        this.unitRequestInterval = (Math.min(5, newInterval) + this.unitRequestInterval) / 2;
    }

    public int getUnitRequestInterval() {
        if (this.unitRequestInterval < 5) {
            this.unitRequestInterval = 5;
        }
        return this.unitRequestInterval;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "state", "com/aicode/settings/AICodeSettingsState", "loadState"));
    }
}
