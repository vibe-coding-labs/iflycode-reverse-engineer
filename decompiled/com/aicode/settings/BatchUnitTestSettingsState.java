package com.aicode.settings;

import com.aicode.enums.BatchTestUnitLimt;
import com.aicode.enums.DuplicateRule;
import com.aicode.enums.GenaratebyTemplateSwitchEnum;
import com.aicode.enums.TestGenerationProcess;
import com.aicode.enums.UnitTestBaseEnum;
import com.aicode.enums.UnitTestMockEnum;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "com.aicode.settings.BatchUnitTestSettingsState", storages = {@Storage("BatchUnitTestSettingsPlugin.xml")})
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/settings/BatchUnitTestSettingsState.class */
public class BatchUnitTestSettingsState implements PersistentStateComponent<BatchUnitTestSettingsState> {
    public String testFramework = UnitTestBaseEnum.JUNIT_FOUR.getName();
    public String mockFramework = UnitTestMockEnum.POWER_MOCK.getName();
    public TestGenerationProcess testGenerationProcess = TestGenerationProcess.GENERATION;
    public GenaratebyTemplateSwitchEnum enabledGenerateByTemplate = GenaratebyTemplateSwitchEnum.DISABLED;
    public boolean testPrivate = false;
    public DuplicateRule duplicateRule = DuplicateRule.COEXIST;
    public String testModuleDirectory = null;
    public boolean savePath = true;
    public BatchTestUnitLimt batchTestUnitLimt = BatchTestUnitLimt.FIVE;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "state", "com/aicode/settings/BatchUnitTestSettingsState", "loadState"));
    }

    public static BatchUnitTestSettingsState getInstance() {
        return (BatchUnitTestSettingsState) ApplicationManager.getApplication().getService(BatchUnitTestSettingsState.class);
    }

    @Nullable
    /* renamed from: getState, reason: merged with bridge method [inline-methods] */
    public BatchUnitTestSettingsState m302getState() {
        return this;
    }

    public void loadState(@NotNull BatchUnitTestSettingsState state) {
        if (state == null) {
            $$$reportNull$$$0(0);
        }
        XmlSerializerUtil.copyBean(state, this);
    }
}
