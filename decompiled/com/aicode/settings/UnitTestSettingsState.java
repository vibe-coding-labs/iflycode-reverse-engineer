package com.aicode.settings;

import com.aicode.enums.GenaratebyTemplateSwitchEnum;
import com.aicode.enums.UnitTestBaseEnum;
import com.aicode.enums.UnitTestMockEnum;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "com.aicode.settings.UnitTestSettingsState", storages = {@Storage("UnitTestSettingsPlugin.xml")})
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/settings/UnitTestSettingsState.class */
public class UnitTestSettingsState implements PersistentStateComponent<UnitTestSettingsState> {
    public String testFramework = UnitTestBaseEnum.JUNIT_FOUR.getName();
    public String mockFramework = UnitTestMockEnum.POWER_MOCK.getName();
    public GenaratebyTemplateSwitchEnum enabledGenerateByTemplate = GenaratebyTemplateSwitchEnum.DISABLED;
    public boolean testPrivate = false;
    public String testClasPath = "";
    public boolean savePath = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "state", "com/aicode/settings/UnitTestSettingsState", "loadState"));
    }

    public static UnitTestSettingsState getInstance() {
        return (UnitTestSettingsState) ApplicationManager.getApplication().getService(UnitTestSettingsState.class);
    }

    @Nullable
    /* renamed from: getState, reason: merged with bridge method [inline-methods] */
    public UnitTestSettingsState m304getState() {
        return this;
    }

    public void loadState(@NotNull UnitTestSettingsState state) {
        if (state == null) {
            $$$reportNull$$$0(0);
        }
        XmlSerializerUtil.copyBean(state, this);
    }
}
