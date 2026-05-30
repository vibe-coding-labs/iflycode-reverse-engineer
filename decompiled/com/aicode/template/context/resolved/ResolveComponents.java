package com.aicode.template.context.resolved;

import com.aicode.template.context.domain.Method;
import com.aicode.template.context.domain.Param;
import com.aicode.template.context.domain.Type;
import com.aicode.template.request.dto.CaseResult;
import com.aicode.util.StringUtils;
import com.aicode.util.TypeUtils;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/resolved/ResolveComponents.class */
public class ResolveComponents {
    private Set<ResolveVarible> input = new LinkedHashSet();
    private Set<ResolveVarible> output = new LinkedHashSet();
    private Set<ResolveVarible> mockData = new LinkedHashSet();
    private Set<ResolveVarible> hasRenders = new LinkedHashSet();

    public Set<ResolveVarible> getInput() {
        return this.input;
    }

    public void setInput(Set<ResolveVarible> input) {
        this.input = input;
    }

    public Set<ResolveVarible> getOutput() {
        return this.output;
    }

    public Set<ResolveVarible> getMockData() {
        return this.mockData;
    }

    public void setMockData(Set<ResolveVarible> mockData) {
        this.mockData = mockData;
    }

    public Set<ResolveVarible> getHasRenders() {
        return this.hasRenders;
    }

    public boolean hasInRender(ResolveVarible varible) {
        if (varible == null) {
            return true;
        }
        if (varible.getVaribleType() == 0) {
            return this.hasRenders.stream().anyMatch(vb -> {
                return vb.getVaribleType() == varible.getVaribleType() && vb.getName().equals(varible.getName()) && vb.getResolveType() == varible.getResolveType();
            });
        }
        return this.hasRenders.stream().anyMatch(vb2 -> {
            return vb2.getResolveType() == varible.getResolveType() && vb2.getName().equals(varible.getName());
        });
    }

    public Optional<ResolveVarible> getRendered(Type resolveType, int varibleType, String name) {
        if (resolveType == null) {
            return null;
        }
        String copyName = StringUtils.deCapitalizeFirstLetter(name);
        if (name.endsWith("Response")) {
            Optional<ResolveVarible> optional = this.hasRenders.stream().filter(vb -> {
                return vb.getVaribleType() == varibleType && vb.getResolveType() == resolveType && vb.getName().equals(copyName);
            }).findFirst();
            if (optional.isPresent()) {
                return optional;
            }
            Optional<ResolveVarible> optional2 = this.hasRenders.stream().filter(vb2 -> {
                return vb2.getResolveType() == resolveType && vb2.getName().equals(copyName);
            }).findFirst();
            if (optional2.isPresent()) {
                return optional2;
            }
            if (TypeUtils.isBasicType(resolveType) || resolveType.getComposedTypes().stream().anyMatch(TypeUtils::isBasicType)) {
                return optional2;
            }
            return this.hasRenders.stream().filter(vb3 -> {
                return vb3.getResolveType() == resolveType;
            }).findFirst();
        }
        if (varibleType == 0) {
            return Optional.empty();
        }
        for (ResolveVarible hasRender : this.hasRenders) {
            String hasRenderName = hasRender.getName();
            if (hasRenderName.equals(copyName)) {
                Type renderResolveType = hasRender.getResolveType();
                if (renderResolveType == resolveType) {
                    return Optional.of(hasRender);
                }
                List<Type> typeImplemented = renderResolveType.getImplementedInterfaces();
                for (Type type : typeImplemented) {
                    if (type.getCanonicalName().equals(resolveType.getCanonicalName())) {
                        return Optional.of(hasRender);
                    }
                }
            }
        }
        return Optional.empty();
    }

    public static void reset(Method method, CaseResult caseResult) {
        if (!TypeUtils.isBasicType(method.getReturnType()) || TypeUtils.isInArray(method.getReturnType())) {
            tryInsertRender(method.getReturnType(), "expectedResult", 2, method, "");
        }
        for (Param methodParam : method.getMethodParams()) {
            if (!TypeUtils.isBasicType(methodParam.getType()) || TypeUtils.isInArray(methodParam.getType())) {
                tryInsertRender(methodParam.getType(), StringUtils.deCapitalizeFirstLetter(methodParam.getName()), 1, method, "");
            }
        }
    }

    private static void tryInsertRender(Type composedType, String variableName, int varibleType, Method method, String calledMethodId) {
        if (!TypeUtils.isBasicType(composedType) || TypeUtils.isInArray(composedType)) {
            boolean needInsert = (StringUtils.isNotBlank(calledMethodId) && !TypeUtils.isNoMockStaticType(calledMethodId, composedType)) || StringUtils.isBlank(calledMethodId);
            if (needInsert) {
                method.getResolveComponents().tryInsertRender(StringUtils.deCapitalizeFirstLetter(variableName), composedType, varibleType);
            }
        }
    }

    public void insertRender(ResolveVarible varible) {
        this.hasRenders.add(varible);
    }

    public void tryInsertRender(String name, Type resolveType, int renderType) {
        ResolveVarible resolveVarible = new ResolveVarible(name, resolveType, renderType);
        resolveVarible.setName(tryGetResolveName(name, 0));
        if (renderType == 0) {
            this.mockData.add(resolveVarible);
        } else if (renderType == 1) {
            this.input.add(resolveVarible);
        } else if (renderType == 2) {
            this.output.add(resolveVarible);
        }
    }

    public String tryGetResolveName(String name, int sub) {
        String testName = sub == 0 ? name : name + sub;
        int nextSub = sub;
        if (this.mockData.stream().anyMatch(varible -> {
            return varible.getName().equals(testName);
        })) {
            nextSub = sub + 1;
        } else if (this.input.stream().anyMatch(varible2 -> {
            return varible2.getName().equals(testName);
        })) {
            nextSub = sub + 1;
        } else if (this.output.stream().anyMatch(varible3 -> {
            return varible3.getName().equals(testName);
        })) {
            nextSub = sub + 1;
        }
        if (nextSub > sub) {
            return tryGetResolveName(name, nextSub);
        }
        return testName;
    }

    public void tryRemoveResolveByName(String name) {
        this.hasRenders.removeIf(varible -> {
            return varible.getName().equals(name);
        });
    }
}
