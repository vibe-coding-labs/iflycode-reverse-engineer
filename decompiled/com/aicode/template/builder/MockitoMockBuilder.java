package com.aicode.template.builder;

import com.aicode.template.TestSubjectInspector;
import com.aicode.template.context.domain.Field;
import com.aicode.template.context.domain.Method;
import com.aicode.template.context.domain.MethodCall;
import com.aicode.template.context.domain.Param;
import com.aicode.template.context.domain.Type;
import com.aicode.util.TypeUtils;
import com.intellij.openapi.diagnostic.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/builder/MockitoMockBuilder.class */
public class MockitoMockBuilder implements MockBuilder {
    private static final Logger LOG = Logger.getInstance(MockitoMockBuilder.class.getName());
    private static final Pattern SEMVER_PATTERN = Pattern.compile("^(\\d*)\\.(\\d*)\\.*");
    public static final Pattern LOGGER_PATTERN = Pattern.compile("(?i).*log.*");
    private final boolean isMockitoMockMakerInlineOn;
    private final boolean stubMockMethodCallsReturnValues;
    protected final TestSubjectInspector testSubjectInspector;

    @Nullable
    private final String mockitoCoreVersion;
    private final Integer mockitoCoreMajorVersion;
    private final Integer mockitoCoreMinorVersion;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "com/aicode/template/builder/MockitoMockBuilder", "deductMatcherTypeMethod"));
    }

    public MockitoMockBuilder(boolean isMockitoMockMakerInlineOn, boolean stubMockMethodCallsReturnValues, TestSubjectInspector testSubjectInspector, @Nullable String mockitoCoreVersion) {
        this.isMockitoMockMakerInlineOn = isMockitoMockMakerInlineOn;
        this.stubMockMethodCallsReturnValues = stubMockMethodCallsReturnValues;
        this.testSubjectInspector = testSubjectInspector;
        this.mockitoCoreVersion = mockitoCoreVersion;
        if (mockitoCoreVersion != null) {
            Matcher matcher = SEMVER_PATTERN.matcher(mockitoCoreVersion);
            if (matcher.find()) {
                this.mockitoCoreMajorVersion = safeParseInteger(matcher.group(1));
                this.mockitoCoreMinorVersion = safeParseInteger(matcher.group(2));
                return;
            }
        }
        this.mockitoCoreMajorVersion = null;
        this.mockitoCoreMinorVersion = null;
    }

    private Integer safeParseInteger(String intStr) {
        if (intStr != null) {
            try {
                return Integer.valueOf(Integer.parseInt(intStr));
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Deprecated
    public boolean isMockable(Field field) {
        return isMockable(field, (Type) null);
    }

    @Override // com.aicode.template.builder.MockBuilder
    public boolean isMockable(Field field, Type testedClass) {
        boolean isMockable = isMockableCommonChecks(field, testedClass) && isMockableByMockFramework(field);
        LOG.debug("field " + field.getType().getCanonicalName() + " " + field.getName() + " is mockable:" + isMockable);
        return isMockable;
    }

    @Override // com.aicode.template.builder.MockBuilder
    public boolean isMockableType(Type type, Type testedClass) {
        boolean isMockable = isMockableCommonChecks(type, testedClass) && isMockableByMockFramework(type);
        return isMockable;
    }

    protected boolean isMockableByMockFramework(Field field) {
        return isMockableByMockFramework(field.getType());
    }

    protected boolean isMockableByMockFramework(Type field) {
        return !field.isFinal() || this.isMockitoMockMakerInlineOn;
    }

    protected boolean isMockableCommonChecks(Field field, Type testedClass) {
        if (field.getType().getCanonicalName().contains("java.lang.Class<")) {
            return false;
        }
        try {
            Pattern pattern = Pattern.compile("<([^>]+)>");
            Matcher matcher = pattern.matcher(field.getType().getCanonicalName());
            while (matcher.find()) {
                String content = matcher.group(1).trim();
                if (content.length() == 1) {
                    return false;
                }
                int index = content.lastIndexOf("<");
                if (index > 0) {
                    content = content.substring(index);
                }
                if (content.trim().length() == 1) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (field.getType().isPrimitive() || isWrapperType(field.getType()) || field.isOverridden() || field.getType().isArray() || field.getType().isEnum() || this.testSubjectInspector.isNotInjectedInDiClass(field, testedClass) || isInitInline(field)) ? false : true;
    }

    protected boolean isMockableCommonChecks(Type type, Type testedClass) {
        if (type.getCanonicalName().contains("java.lang.Class<")) {
            return false;
        }
        try {
            Pattern pattern = Pattern.compile("<([^>]+)>");
            Matcher matcher = pattern.matcher(type.getCanonicalName());
            while (matcher.find()) {
                String content = matcher.group(1).trim();
                if (content.length() == 1) {
                    return false;
                }
                int index = content.lastIndexOf("<");
                if (index > 0) {
                    content = content.substring(index);
                }
                if (content.trim().length() == 1) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (type.isPrimitive() || isWrapperType(type) || type.isArray() || type.isEnum()) ? false : true;
    }

    private static boolean isInitInline(Field field) {
        return (field.isInitializedInline() && !field.isHasSetter()) || LOGGER_PATTERN.matcher(field.getName()).matches();
    }

    public boolean isMockable(Param param, Map<String, String> defaultTypes) {
        boolean z;
        boolean contains;
        Type type = param.getType();
        if (!type.isPrimitive()) {
            contains = TypeUtils.f739if.contains(type.getCanonicalName());
            if (!contains && !isWrapperType(type) && ((!type.isFinal() || this.isMockitoMockMakerInlineOn) && !type.isArray() && !type.isEnum() && defaultTypes.get(type.getCanonicalName()) == null)) {
                z = true;
                boolean isMockable = z;
                return isMockable;
            }
        }
        z = false;
        boolean isMockable2 = z;
        return isMockable2;
    }

    public boolean hasMockable(List<Field> fields, Type testedClass) {
        for (Field field : fields) {
            if (isMockable(field, testedClass)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasMockableMethod(List<Method> methods, Type testedClass) {
        for (Method method : methods) {
            if (!method.getStaticMethodCalls().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasMocks(Type testedClass) {
        boolean result = this.testSubjectInspector.hasAccessibleCtor(testedClass) && (hasMockable(testedClass.getFields(), testedClass) || hasMockableMethod(testedClass.getMethods(), testedClass));
        return result;
    }

    public boolean needMock(Type classType) {
        return (!hasMocks(classType) || classType.isInterface() || classType.isEnum()) ? false : true;
    }

    public boolean hasMocks(Method ctor, Map<String, String> defaultTypes) {
        if (ctor == null) {
            return false;
        }
        List<Param> params = ctor.getMethodParams();
        for (Param param : params) {
            if (isMockable(param, defaultTypes)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.aicode.template.builder.MockBuilder
    public String getImmockabiliyReason(String prefix, Field field) {
        String reasonMsgPrefix = prefix + "Field " + field.getName() + " of type " + field.getType().getName();
        if (field.getType().isFinal() && !this.isMockitoMockMakerInlineOn && isMockExpected(field)) {
            return reasonMsgPrefix + " - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set";
        }
        if (field.getType().isArray()) {
            return reasonMsgPrefix + "[] - was not mocked since Mockito doesn't mock arrays";
        }
        return "";
    }

    @Override // com.aicode.template.builder.MockBuilder
    public String buildMockArgsMatchers(List<Param> params) {
        StringBuilder sb = new StringBuilder();
        for (Param param : params) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(deductMatcherTypeMethod(param));
        }
        return sb.toString();
    }

    @Override // com.aicode.template.builder.MockBuilder
    public Set<String> mockStaticClass(Method method) {
        Set<String> staticClasses = new HashSet<>();
        method.getStaticMethodCalls().forEach(staticMethodCall -> {
            if (staticMethodCall.getMethod().hasReturn() && !staticMethodCall.getMethod().getMethodParams().isEmpty()) {
                staticClasses.add(staticMethodCall.getOwnerClass());
            }
        });
        return staticClasses;
    }

    @Override // com.aicode.template.builder.MockBuilder
    public Boolean isMockStatic(Method method) {
        return Boolean.valueOf(!mockStaticClass(method).isEmpty());
    }

    @Override // com.aicode.template.builder.MockBuilder
    public String resolveExceptions(Method method) {
        Function<MethodCall, String> mapFunc = methodCall -> {
            return methodCall.getMethod().getMethodExceptionTypes();
        };
        List<String> exceptions = (List) method.getDirectMethodCalls().stream().map(mapFunc).collect(Collectors.toList());
        exceptions.addAll((Collection) method.getStaticMethodCalls().stream().map(mapFunc).collect(Collectors.toList()));
        List<String> allExceptions = new ArrayList<>();
        append(allExceptions, method.getMethodExceptionTypes());
        appendMethodExceptionTypes(allExceptions, exceptions);
        allExceptions.removeIf((v0) -> {
            return StringUtils.isEmpty(v0);
        });
        if (!allExceptions.isEmpty()) {
            if (allExceptions.contains("java.lang.Exception")) {
                return "throws java.lang.Exception";
            }
            String methodExceptionTypes = allExceptions.size() > 3 ? "java.lang.Exception" : (String) allExceptions.stream().distinct().collect(Collectors.joining(","));
            return "throws " + methodExceptionTypes;
        }
        return "";
    }

    public void appendMethodExceptionTypes(List<String> allExceptions, List<String> appendExceptions) {
        for (String appendException : appendExceptions) {
            append(allExceptions, appendException);
        }
    }

    private void append(List<String> exceptions, String appends) {
        if (appends == null || appends.length() == 1) {
            return;
        }
        exceptions.addAll(Arrays.asList(appends.split(",")));
    }

    @Override // com.aicode.template.builder.MockBuilder
    public String buildArgsTypes(List<Param> params) {
        StringBuilder sb = new StringBuilder();
        for (Param param : params) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            String canonicalName = getCanonicalNamePre(param.getType());
            sb.append(canonicalName + ".class");
        }
        return sb.toString();
    }

    private String getCanonicalNamePre(Type type) {
        String canonicalName = type.getCanonicalName();
        if (canonicalName.indexOf("<") > 0) {
            int first = canonicalName.indexOf("<");
            canonicalName.lastIndexOf(">");
            canonicalName = canonicalName.substring(0, first);
        }
        int arrayDimensions = type.getArrayDimensions();
        if (type.isArray()) {
            for (int i = arrayDimensions; i > 0; i--) {
                canonicalName = canonicalName + "[]";
            }
        }
        return canonicalName;
    }

    private String getCanonicalNameBetween(Type type) {
        String canonicalName = type.getCanonicalName();
        if (canonicalName.indexOf("<") > 0) {
            int first = canonicalName.indexOf("<");
            int end = canonicalName.lastIndexOf(">");
            canonicalName = canonicalName.substring(first + 1, end);
        }
        return canonicalName;
    }

    @Override // com.aicode.template.builder.MockBuilder
    public String buildStaticTypeNames(Type testClass) {
        if (testClass.getStaticClassNames().size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (String staticClassName : testClass.getStaticClassNames()) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(staticClassName).append(".class");
            }
            return sb.toString();
        }
        return "";
    }

    @NotNull
    private String deductMatcherTypeMethod(Param param) {
        String result;
        Type type = param.getType();
        String matcherMethod = resolveMatcherMethod(type);
        if (!type.isPrimitive() && "any".equals(matcherMethod)) {
            if (type.getCanonicalName().startsWith("java.util.Collection") || type.getCanonicalName().startsWith("java.util.List")) {
                result = "anyList()";
            } else if (type.getCanonicalName().startsWith("java.util.Map")) {
                result = "anyMap()";
            } else if (type.getCanonicalName().contains("Wrapper")) {
                result = matcherMethod + "(" + type.getCanonicalName().replaceAll("<.*?>", "") + ".class)";
            } else if (type.getCanonicalName().equals("java.lang.Object")) {
                result = matcherMethod + "()";
            } else if (type.isArray()) {
                result = matcherMethod + "(" + type.getCanonicalName().replaceAll("<.*?>", "") + "[].class)";
            } else if (type.getCanonicalName().startsWith("sun.reflect.generics.tree.Tree")) {
                result = "eq(" + getCanonicalNameBetween(type) + ".class)";
            } else if (type.getCanonicalName().startsWith("java.lang.Class")) {
                String canonicalName = getCanonicalNameBetween(type);
                if (StringUtils.isBlank(canonicalName)) {
                    String str = matcherMethod + "(" + type.getCanonicalName().replace("<>", "") + ".class)";
                    if (str == null) {
                        $$$reportNull$$$0(0);
                    }
                    return str;
                }
                if (containSpecialToken(canonicalName)) {
                    return "eq(Class.class)";
                }
                result = "eq(" + canonicalName + ".class)";
            } else {
                String canonicalName2 = getCanonicalNamePre(type);
                if (StringUtils.isBlank(canonicalName2)) {
                    result = matcherMethod + "()";
                } else {
                    if (containSpecialToken(canonicalName2)) {
                        return "eq(Class.class)";
                    }
                    result = matcherMethod + "(" + canonicalName2 + ".class)";
                }
            }
        } else if (type.isArray()) {
            result = matcherMethod + "(" + type.getCanonicalName().replaceAll("<.*?>", "") + "[].class)";
        } else {
            result = matcherMethod + "()";
        }
        String str2 = result;
        if (str2 == null) {
            $$$reportNull$$$0(1);
        }
        return str2;
    }

    private boolean containSpecialToken(String canonicalName) {
        return canonicalName.contains("<") || canonicalName.contains(">") || canonicalName.contains("?");
    }

    private static String resolveMatcherMethod(Type type) {
        if (type.isVarargs()) {
            return "anyVararg";
        }
        if (!type.isArray() && TypeUtils.TYPE_TO_ARG_MATCHERS.containsKey(type.getCanonicalName())) {
            return TypeUtils.TYPE_TO_ARG_MATCHERS.get(type.getCanonicalName());
        }
        return "any";
    }

    @Deprecated
    public boolean shouldStub(Method testMethod, List<Field> testedClassFields) {
        return callsMockMethod(testMethod, testedClassFields, (v0) -> {
            return v0.hasReturn();
        }, null);
    }

    public boolean shouldStub(Method testMethod, Type testedClass) {
        return callsMockMethod(testMethod, testedClass.getFields(), (v0) -> {
            return v0.hasReturn();
        }, testedClass);
    }

    @Deprecated
    public boolean shouldVerify(Method testMethod, List<Field> testedClassFields) {
        return callsMockMethod(testMethod, testedClassFields, method -> {
            return !method.hasReturn();
        }, null);
    }

    public boolean shouldVerify(Method testMethod, Type testedClass) {
        return false;
    }

    private boolean callsMockMethod(Method testMethod, List<Field> testedClassFields, Predicate<Method> mockMethodRelevant, Type testedClass) {
        if (!this.stubMockMethodCallsReturnValues) {
            return false;
        }
        for (Field testedClassField : testedClassFields) {
            if (isMockable(testedClassField, testedClass) && !testedClassField.isNotInBuilder()) {
                for (Method fieldMethod : testedClassField.getType().getMethods()) {
                    if (mockMethodRelevant.test(fieldMethod) && this.testSubjectInspector.isMethodCalled(fieldMethod, testMethod, null)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean shouldStub(Method testMethod, Method ctor, Map<String, String> defaultTypes) {
        boolean shouldStub = false;
        if (ctor == null || !this.stubMockMethodCallsReturnValues) {
            return false;
        }
        List<Param> ctorParams = ctor.getMethodParams();
        for (Param param : ctorParams) {
            if (isMockable(param, defaultTypes)) {
                Iterator<Method> it = param.getType().getMethods().iterator();
                while (true) {
                    if (it.hasNext()) {
                        Method method = it.next();
                        if (method.getReturnType() != null && !"void".equals(method.getReturnType().getCanonicalName()) && this.testSubjectInspector.isMethodCalled(method, testMethod, null)) {
                            shouldStub = true;
                            break;
                        }
                    }
                }
            }
        }
        return shouldStub;
    }

    @Override // com.aicode.template.builder.MockBuilder
    public boolean isMockExpected(Field field) {
        return (field.getType().isPrimitive() || isWrapperType(field.getType()) || field.isStatic() || field.isOverridden()) ? false : true;
    }

    public boolean isWrapperType(Type type) {
        return TypeUtils.WRAPPER_TYPES.contains(type.getCanonicalName());
    }

    @Nullable
    public String getMockitoCoreVersion() {
        return this.mockitoCoreVersion;
    }

    public String getInitMocksMethod() {
        if (this.mockitoCoreMajorVersion == null || this.mockitoCoreMinorVersion == null) {
            return "initMocks";
        }
        if ((this.mockitoCoreMajorVersion.intValue() == 3 && this.mockitoCoreMinorVersion.intValue() >= 4) || this.mockitoCoreMajorVersion.intValue() > 3) {
            return "openMocks";
        }
        return "initMocks";
    }
}
