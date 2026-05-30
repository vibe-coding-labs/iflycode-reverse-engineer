package com.aicode.service;

import com.aicode.apm.OpenTelemetryUtil;
import com.aicode.language.AICodeLanguageInfo;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: d */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/service/LanguageInfoSupport.class */
public interface LanguageInfoSupport {
    public static final ExtensionPointName<LanguageInfoSupport> EP = new ExtensionPointName<>(OpenTelemetryUtil.H("g+:3o clt,]\u000bf%)/o$U\u0003u7t\u001cu\u0006{:c8g%"));

    @Nullable
    AICodeLanguageInfo findVSCodeLanguageMapping(@NotNull PsiFile psiFile);
}
