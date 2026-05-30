package com.aicode.action.batch;

import com.aicode.enums.DuplicateRule;
import com.aicode.enums.TestGenerationProcess;
import com.aicode.enums.UnitTestBaseEnum;
import com.aicode.enums.UnitTestMockEnum;
import com.intellij.openapi.module.Module;
import java.util.List;

/* compiled from: sh */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/batch/GeneratorConfig.class */
public class GeneratorConfig {

    /* renamed from: break, reason: not valid java name */
    public Boolean f73break;

    /* renamed from: class, reason: not valid java name */
    public boolean f74class;

    /* renamed from: true, reason: not valid java name */
    public List<String> f75true;
    public String testFileName;

    /* renamed from: this, reason: not valid java name */
    public List<String> f76this;

    /* renamed from: else, reason: not valid java name */
    public List<String> f77else;

    /* renamed from: char, reason: not valid java name */
    public String f78char;

    /* renamed from: int, reason: not valid java name */
    public boolean f79int;

    /* renamed from: new, reason: not valid java name */
    public DuplicateRule f80new;

    /* renamed from: long, reason: not valid java name */
    public Integer f81long;

    /* renamed from: super, reason: not valid java name */
    public Boolean f82super = true;

    /* renamed from: for, reason: not valid java name */
    public UnitTestBaseEnum f83for;

    /* renamed from: if, reason: not valid java name */
    public String f84if;

    /* renamed from: case, reason: not valid java name */
    public boolean f85case;

    /* renamed from: final, reason: not valid java name */
    public Module f86final;

    /* renamed from: try, reason: not valid java name */
    public Module f87try;

    /* renamed from: float, reason: not valid java name */
    public UnitTestMockEnum f88float;

    /* renamed from: byte, reason: not valid java name */
    public String f89byte;

    /* renamed from: enum, reason: not valid java name */
    public TestGenerationProcess f90enum;

    public static String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getClassName()).insert(0, stackTraceElement.getMethodName()).toString();
        int length = stringBuffer.length() - 1;
        int i = ((2 ^ 5) << 4) ^ (4 << 1);
        int i2 = (3 << 3) ^ 5;
        int i3 = (1 << 3) ^ 3;
        String str = (String) a;
        int length2 = str.length();
        char[] cArr = new char[length2];
        int i4 = length2 - 1;
        int i5 = i4;
        int i6 = length;
        while (i4 >= 0) {
            int i7 = i5;
            int i8 = i5 - 1;
            cArr[i7] = (char) (i2 ^ (str.charAt(i7) ^ stringBuffer.charAt(i6)));
            if (i8 < 0) {
                break;
            }
            char charAt = (char) (i3 ^ (str.charAt(i8) ^ stringBuffer.charAt(i6)));
            i5 = i8 - 1;
            i6--;
            cArr[i8] = charAt;
            if (i6 < 0) {
                i6 = length;
            }
        }
        return new String(cArr);
    }

    public String getTestModuleDirectory() {
        return this.f89byte;
    }

    public Integer getTestUnitLimit() {
        return this.f81long;
    }

    public UnitTestMockEnum getMockFramework() {
        return this.f88float;
    }

    public String getActionId() {
        return this.f78char;
    }

    public Module getTestModule() {
        return this.f86final;
    }

    public void setMockFramework(UnitTestMockEnum a) {
        this.f88float = a;
    }

    public void setEnabledGenerateByTemplate(Boolean a) {
        this.f73break = a;
    }

    public void setTestGenerationProcess(TestGenerationProcess a) {
        this.f90enum = a;
    }

    public void setSingleFile(boolean z) {
        this.f85case = z;
    }

    public void setExecPath(String a) {
        this.f84if = a;
    }

    public void setDuplicateRule(DuplicateRule a) {
        this.f80new = a;
    }

    public void setActionId(String a) {
        this.f78char = a;
    }

    public void setTestModuleDirectory(String a) {
        this.f89byte = a;
    }

    public void setTestUnitLimit(Integer a) {
        this.f81long = a;
    }

    public void setTestFramework(UnitTestBaseEnum a) {
        this.f83for = a;
    }

    public void setOverwrite(boolean z) {
        this.f74class = z;
    }

    public void setTestFileName(String a) {
        this.testFileName = a;
    }

    public void setRequestAi(Boolean a) {
        this.f82super = a;
    }

    public void setModule(Module a) {
        this.f87try = a;
    }

    public void setTestPrivate(boolean z) {
        this.f79int = z;
    }

    public void setTestModule(Module a) {
        this.f86final = a;
    }

    public void setGeneratorFilePathList(List<String> list) {
        this.f76this = list;
    }

    public void setFileAbsolutePathList(List<String> list) {
        this.f77else = list;
    }

    public void setExcludeMethodList(List<String> list) {
        this.f75true = list;
    }

    public String getTestFileName() {
        return this.testFileName;
    }

    public Boolean getRequestAi() {
        return this.f82super;
    }

    public TestGenerationProcess getTestGenerationProcess() {
        return this.f90enum;
    }

    public DuplicateRule getDuplicateRule() {
        return this.f80new;
    }

    public Boolean getEnabledGenerateByTemplate() {
        return this.f73break;
    }

    public boolean isOverwrite() {
        return this.f74class;
    }

    public List<String> getGeneratorFilePathList() {
        return this.f76this;
    }

    public boolean isSingleFile() {
        return this.f85case;
    }

    public boolean isTestPrivate() {
        return this.f79int;
    }

    public String getExecPath() {
        return this.f84if;
    }

    public List<String> getFileAbsolutePathList() {
        return this.f77else;
    }

    public Module getModule() {
        return this.f87try;
    }

    public List<String> getExcludeMethodList() {
        return this.f75true;
    }

    public String toString() {
        return "GeneratorConfig(module=" + getModule() + ", isSingleFile=" + isSingleFile() + ", execPath=" + getExecPath() + ", fileAbsolutePathList=" + getFileAbsolutePathList() + ", testFramework=" + getTestFramework() + ", mockFramework=" + getMockFramework() + ", testPrivate=" + isTestPrivate() + ", overwrite=" + isOverwrite() + ", duplicateRule=" + getDuplicateRule() + ", excludeMethodList=" + getExcludeMethodList() + ", testModuleDirectory=" + getTestModuleDirectory() + ", testModule=" + getTestModule() + ", requestAi=" + getRequestAi() + ", enabledGenerateByTemplate=" + getEnabledGenerateByTemplate() + ", testGenerationProcess=" + getTestGenerationProcess() + ", generatorFilePathList=" + getGeneratorFilePathList() + ", testFileName=" + getTestFileName() + ", actionId=" + getActionId() + ", testUnitLimit=" + getTestUnitLimit() + ")";
    }

    public UnitTestBaseEnum getTestFramework() {
        return this.f83for;
    }
}
