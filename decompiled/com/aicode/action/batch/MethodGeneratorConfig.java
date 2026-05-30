package com.aicode.action.batch;

import com.aicode.enums.UnitTestBaseEnum;
import com.aicode.enums.UnitTestMockEnum;
import com.aicode.template.context.domain.Method;
import com.aicode.template.request.dto.CaseResult;
import com.aicode.test.dto.UnitTestDto;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import java.util.ArrayList;
import java.util.List;

/* compiled from: pk */
/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/action/batch/MethodGeneratorConfig.class */
public class MethodGeneratorConfig {

    /* renamed from: true, reason: not valid java name */
    public UnitTestDto.DataDTO.FunctionDataDTO f91true;

    /* renamed from: this, reason: not valid java name */
    public boolean f92this;

    /* renamed from: else, reason: not valid java name */
    public String f93else;

    /* renamed from: char, reason: not valid java name */
    public boolean f94char;

    /* renamed from: int, reason: not valid java name */
    public List<Method> f95int;

    /* renamed from: new, reason: not valid java name */
    public List<String> f96new;

    /* renamed from: long, reason: not valid java name */
    public PsiFile f97long;

    /* renamed from: super, reason: not valid java name */
    public UnitTestBaseEnum f98super;

    /* renamed from: for, reason: not valid java name */
    public boolean f99for;

    /* renamed from: if, reason: not valid java name */
    public String f100if;

    /* renamed from: case, reason: not valid java name */
    public UnitTestMockEnum f101case;

    /* renamed from: final, reason: not valid java name */
    public List<CaseResult> f102final = new ArrayList();

    /* renamed from: try, reason: not valid java name */
    public UnitTestDto.DataDTO f103try;

    /* renamed from: float, reason: not valid java name */
    public PsiClass f104float;

    /* renamed from: byte, reason: not valid java name */
    public List<PsiMethod> f105byte;

    /* renamed from: enum, reason: not valid java name */
    public String f106enum;

    public static String H(Object a) {
        StackTraceElement stackTraceElement = new LinkageError().getStackTrace()[1];
        String stringBuffer = new StringBuffer(stackTraceElement.getMethodName()).insert(0, stackTraceElement.getClassName()).toString();
        int length = stringBuffer.length() - 1;
        int i = ((2 ^ 5) << 4) ^ (2 ^ 5);
        int i2 = ((2 ^ 5) << 3) ^ 2;
        int i3 = ((3 ^ 5) << 3) ^ 4;
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

    public List<PsiMethod> getMethods() {
        return this.f105byte;
    }

    public String getModulePath() {
        return this.f106enum;
    }

    public PsiClass getPsiClass() {
        return this.f104float;
    }

    public String getPath() {
        return this.f100if;
    }

    public boolean isMethodUt() {
        return this.f94char;
    }

    public String getTestDirectoryPath() {
        return this.f93else;
    }

    public boolean isEnabledGenerateByTemplate() {
        return this.f92this;
    }

    public List<Method> getTemplateMethods() {
        return this.f95int;
    }

    public UnitTestDto.DataDTO getUnitTestDto() {
        return this.f103try;
    }

    public PsiFile getPsiFile() {
        return this.f97long;
    }

    public UnitTestBaseEnum getTestFramework() {
        return this.f98super;
    }

    public List<String> getExcludeMethodList() {
        return this.f96new;
    }

    public boolean isTestPrivate() {
        return this.f99for;
    }

    public UnitTestMockEnum getMockFramework() {
        return this.f101case;
    }

    public UnitTestDto.DataDTO.FunctionDataDTO getFunctionDataDTO() {
        return this.f91true;
    }

    public void setTestFramework(UnitTestBaseEnum a) {
        this.f98super = a;
    }

    public void setEnabledGenerateByTemplate(boolean z) {
        this.f92this = z;
    }

    public void setPath(String a) {
        this.f100if = a;
    }

    public void setPsiClass(PsiClass a) {
        this.f104float = a;
    }

    public void setUnitTestDto(UnitTestDto.DataDTO a) {
        this.f103try = a;
    }

    public void setMethods(List<PsiMethod> list) {
        this.f105byte = list;
    }

    public void setModulePath(String a) {
        this.f106enum = a;
    }

    public void setCaseResults(List<CaseResult> list) {
        this.f102final = list;
    }

    public void setPsiFile(PsiFile a) {
        this.f97long = a;
    }

    public void setMockFramework(UnitTestMockEnum a) {
        this.f101case = a;
    }

    public void setTestDirectoryPath(String a) {
        this.f93else = a;
    }

    public void setExcludeMethodList(List<String> list) {
        this.f96new = list;
    }

    public void setFunctionDataDTO(UnitTestDto.DataDTO.FunctionDataDTO a) {
        this.f91true = a;
    }

    public void setMethodUt(boolean z) {
        this.f94char = z;
    }

    public void setTemplateMethods(List<Method> list) {
        this.f95int = list;
    }

    public void setTestPrivate(boolean z) {
        this.f99for = z;
    }

    public List<CaseResult> getCaseResults() {
        return this.f102final;
    }
}
