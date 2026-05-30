package com.aicode.template.context.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aicode.inline.status.InlineChatStatusServiceKt;
import com.aicode.service.editor.RequestResultList;
import com.aicode.template.AssertUtil;
import com.aicode.template.FileTemplateConfig;
import com.aicode.template.TypeDictionary;
import com.aicode.template.context.domain.Field;
import com.aicode.template.context.domain.Method;
import com.aicode.template.context.domain.MethodCall;
import com.aicode.template.context.domain.Node;
import com.aicode.template.context.domain.Param;
import com.aicode.template.context.domain.Reference;
import com.aicode.template.context.domain.SyntheticParam;
import com.aicode.template.context.domain.Type;
import com.aicode.template.context.resolved.ResolveVarible;
import com.aicode.template.context.service.LangTestBuilder;
import com.aicode.template.context.service.TestBuilder;
import com.aicode.template.request.DataUtils;
import com.aicode.template.request.dto.CaseParam;
import com.aicode.template.request.dto.CaseResult;
import com.aicode.template.request.dto.ToMockMethod;
import com.aicode.util.ClassNameUtils;
import com.aicode.util.JavaPsiUtils;
import com.aicode.util.PropertyUtils;
import com.aicode.util.StringUtils;
import com.aicode.util.TypeUtils;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.util.lang.JavaVersion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/context/service/impl/JavaTestBuilderImpl.class */
public class JavaTestBuilderImpl implements LangTestBuilder {
    private static final Logger LOG = Logger.getInstance(JavaTestBuilderImpl.class.getName());
    private static final Type DEFAULT_STRING_TYPE = new Type("java.lang.String", "String", "java.lang", false, false, false, false, 0, false, new ArrayList());
    private static final Type DEFAULT_Object_TYPE = new Type("java.lang.Object", "Object", "java.lang", false, false, false, false, 0, false, new ArrayList());
    private static final Type DEFAULT_Map_TYPE = new Type("java.util.HashMap<java.lang.String,java.lang.Object>", "HashMap", "java.util", false, false, false, false, 0, false, List.of(DEFAULT_STRING_TYPE, DEFAULT_Object_TYPE));
    private static final Type DEFAULT_List_TYPE = new Type("java.util.ArrayList<java.lang.Object>", "ArrayList", "java.util", false, false, false, false, 0, false, List.of(DEFAULT_Object_TYPE));
    private static final String[] SPEC_ARRAY_VALUE = {"java.util.Arrays.asList(null)", "new java.util.ArrayList(Arrays.asList(null))", "new java.util.ArrayList(java.util.Arrays.asList(null))"};
    public static final LinkedHashMap<String, String> DEFAULT_TYPE_TO_MATCHERS = new LinkedHashMap<>();
    public static final Map<String, String> DEFAULT_TYPE_TO_BOCOM;
    private static final int JAVA_9_VERSION = 9;
    private final TestBuilder.ParamRole paramRole;
    private final Method testedMethod;
    protected final String NEW_INITIALIZER = "new ";
    private final Module srcModule;
    private final TypeDictionary typeDictionary;
    protected FileTemplateConfig fileTemplateConfig;

    @Nullable
    private final JavaVersion javaVersion;
    private final Map<String, String> defaultTypeValues;
    private final Map<String, String> typesOverrides;
    private final Integer renderType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 0:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 1:
            case 2:
            case 3:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 0:
            default:
                i2 = 2;
                break;
            case 1:
            case 2:
            case 3:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 0:
            default:
                objArr[0] = "com/aicode/template/context/service/impl/JavaTestBuilderImpl";
                break;
            case 1:
                objArr[0] = "testedMethod";
                break;
            case 2:
            case 3:
                objArr[0] = "method";
                break;
        }
        switch (i) {
            case 0:
            default:
                objArr[1] = "resolveInitializerKeyword";
                break;
            case 1:
            case 2:
            case 3:
                objArr[1] = "com/aicode/template/context/service/impl/JavaTestBuilderImpl";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "isPropertyUsed";
                break;
            case 2:
                objArr[2] = "isPropertyUsedIndirectly";
                break;
            case 3:
                objArr[2] = "isReferencedInMethod";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 0:
            default:
                throw new IllegalStateException(format);
            case 1:
            case 2:
            case 3:
                throw new IllegalArgumentException(format);
        }
    }

    static {
        DEFAULT_TYPE_TO_MATCHERS.put("java.lang.String", "new java.lang.String(\"%s\")");
        DEFAULT_TYPE_TO_MATCHERS.put("java.lang.Long", "java.lang.Long.valueOf(\"%s\")");
        DEFAULT_TYPE_TO_MATCHERS.put("java.lang.Integer", "java.lang.Integer.valueOf(\"%s\")");
        DEFAULT_TYPE_TO_MATCHERS.put("java.lang.Float", "java.lang.Float.valueOf(\"%s\")");
        DEFAULT_TYPE_TO_MATCHERS.put("java.lang.Double", "java.lang.Double.valueOf(\"%s\")");
        DEFAULT_TYPE_TO_MATCHERS.put("java.lang.Short", "java.lang.Short.valueOf(\"%s\")");
        DEFAULT_TYPE_TO_MATCHERS.put("java.lang.Character", "java.lang.Character.valueOf(\"%s\")");
        DEFAULT_TYPE_TO_MATCHERS.put("java.lang.Boolean", "java.lang.Boolean.valueOf(\"%s\")");
        DEFAULT_TYPE_TO_MATCHERS.put("java.lang.Byte", "java.lang.Byte.valueOf(\"%s\")");
        DEFAULT_TYPE_TO_MATCHERS.put("java.io.Serializable", "Long.valueOf(1)");
        DEFAULT_TYPE_TO_MATCHERS.put("java.lang.Number", "Integer.valueOf(\"%s\")");
        DEFAULT_TYPE_TO_MATCHERS.put("java.lang.Comparable", "\"%s\"");
        DEFAULT_TYPE_TO_MATCHERS.put("java.math.BigDecimal", "new java.math.BigDecimal(\"%s\")");
        DEFAULT_TYPE_TO_MATCHERS.put("java.util.Date", "new java.util.Date(%dL)");
        DEFAULT_TYPE_TO_MATCHERS.put("java.time.LocalDate", "java.time.LocalDate.of(%d,%d,%d)");
        DEFAULT_TYPE_TO_MATCHERS.put("java.time.LocalDateTime", "java.time.LocalDateTime.of(%d,%d,%d,%d,%d,%d)");
        DEFAULT_TYPE_TO_MATCHERS.put("java.time.LocalTime", "java.time.LocalTime.of(%d,%d,%d)");
        DEFAULT_TYPE_TO_MATCHERS.put("byte", "(byte) %s");
        DEFAULT_TYPE_TO_MATCHERS.put("short", "(short) %s");
        DEFAULT_TYPE_TO_MATCHERS.put("int", "%s");
        DEFAULT_TYPE_TO_MATCHERS.put("long", "%sL");
        DEFAULT_TYPE_TO_MATCHERS.put("float", "%sf");
        DEFAULT_TYPE_TO_MATCHERS.put("double", "%sd");
        DEFAULT_TYPE_TO_MATCHERS.put("char", "%s");
        DEFAULT_TYPE_TO_MATCHERS.put("boolean", "%s");
        DEFAULT_TYPE_TO_BOCOM = new HashMap();
        DEFAULT_TYPE_TO_BOCOM.put("org.springframework.web.multipart.MultipartFile", "org.springframework.mock.web.MockMultipartFile");
        DEFAULT_TYPE_TO_BOCOM.put("javax.servlet.http.HttpServletRequest", "org.springframework.mock.web.MockHttpServletRequest");
        DEFAULT_TYPE_TO_BOCOM.put("javax.servlet.ServletRequest", "org.springframework.mock.web.MockHttpServletRequest");
        DEFAULT_TYPE_TO_BOCOM.put("javax.servlet.http.HttpServletResponse", "org.springframework.mock.web.MockHttpServletResponse");
        DEFAULT_TYPE_TO_BOCOM.put("javax.servlet.ServletResponse", "org.springframework.mock.web.MockHttpServletResponse");
        DEFAULT_TYPE_TO_BOCOM.put("org.apache.http.impl.client.CloseableHttpClient", "org.apache.http.impl.client.CloseableHttpClient");
    }

    public JavaTestBuilderImpl(Method testedMethod, TestBuilder.ParamRole paramRole, FileTemplateConfig fileTemplateConfig, Module srcModule, TypeDictionary typeDictionary, JavaVersion javaVersion, Map<String, String> defaultTypeValues, Map<String, String> typesOverrides, int renderType) {
        this.testedMethod = testedMethod;
        this.srcModule = srcModule;
        this.typeDictionary = typeDictionary;
        this.paramRole = paramRole;
        this.fileTemplateConfig = fileTemplateConfig;
        this.javaVersion = javaVersion;
        this.defaultTypeValues = defaultTypeValues;
        this.typesOverrides = typesOverrides;
        this.renderType = Integer.valueOf(renderType);
    }

    @Override // com.aicode.template.context.service.LangTestBuilder
    public String renderJavaCallParams(List<Param> params, CaseResult caseResult) {
        StringBuilder stringBuilder = new StringBuilder();
        JSONObject jsonObject = null;
        if (caseResult != null && !DataUtils.isEmptyData(caseResult)) {
            if (this.renderType.intValue() == 0 && this.testedMethod != null) {
                ToMockMethod toMockMethod = caseResult.getMockMethods().stream().filter(m -> {
                    return m.getMethodName().equalsIgnoreCase(this.testedMethod.getName()) || m.getMethodName().equalsIgnoreCase(this.testedMethod.getPropertyName());
                }).findFirst().orElse(null);
                if (toMockMethod != null) {
                    jsonObject = JSONUtil.parseObj(toMockMethod.getReturnValue().getData(), JSONConfig.create().setIgnoreError(true));
                }
            } else if (this.renderType.intValue() == 1) {
                if (caseResult.getInput() != null) {
                    jsonObject = new JSONObject();
                    for (Map.Entry<String, CaseParam> stringCaseParamEntry : caseResult.getInput().entrySet()) {
                        for (Param param : params) {
                            try {
                                if (param.getName().equalsIgnoreCase(stringCaseParamEntry.getKey())) {
                                    if (param.getType().isArray() || param.getType().isCollection()) {
                                        jsonObject.putOpt(param.getName(), stringCaseParamEntry.getValue().getData());
                                    } else {
                                        jsonObject.putOpt(param.getName(), stringCaseParamEntry.getValue().getData());
                                    }
                                }
                            } catch (Exception e) {
                                LOG.warn("转换用例数据失败", e);
                            }
                        }
                    }
                }
            } else if (this.renderType.intValue() == 2) {
            }
        }
        buildCallParams(null, params, stringBuilder, new Node<>(null, null, 0), jsonObject, 2);
        return stringBuilder.toString();
    }

    @Override // com.aicode.template.context.service.LangTestBuilder
    public String renderJavaCallParam(Type type, String strValue, CaseResult caseResult) {
        StringBuilder stringBuilder = new StringBuilder();
        CaseParam caseParam = null;
        if (caseResult != null && !DataUtils.isEmptyData(caseResult)) {
            if (this.renderType.intValue() == 0 && this.testedMethod != null) {
                String propertyName = StrUtil.replaceLast(strValue, "Response", "");
                ToMockMethod toMockMethod = caseResult.getMockMethods().stream().filter(m -> {
                    return StringUtils.isNotBlank(m.getMethodName()) && m.getMethodName().equalsIgnoreCase(propertyName);
                }).findFirst().orElse(null);
                if (toMockMethod != null) {
                    caseParam = toMockMethod.getReturnValue();
                }
            } else if (this.renderType.intValue() == 1) {
                if (caseResult.getInput() != null && caseResult.getInput().containsKey(strValue)) {
                    caseParam = caseResult.getInput().get(strValue);
                }
            } else if (this.renderType.intValue() == 2 && caseResult.getOutput() != null) {
                caseParam = caseResult.getOutput();
            }
        }
        buildCallParam(stringBuilder, new Node<>(new SyntheticParam(type, strValue), null, 0), caseParam, 2);
        if (stringBuilder.length() > 1 && stringBuilder.lastIndexOf(";") == stringBuilder.length() - 1) {
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        } else if (stringBuilder.length() > 1 && stringBuilder.lastIndexOf("null") == stringBuilder.length() - 1) {
            stringBuilder.append(";");
        }
        return stringBuilder.toString();
    }

    @Override // com.aicode.template.context.service.LangTestBuilder
    public String renderJavaVariable(Type type, String strValue, CaseResult caseResult) {
        StringBuilder stringBuilder = new StringBuilder();
        CaseParam caseParam = null;
        if (caseResult != null && !DataUtils.isEmptyData(caseResult) && (this.renderType.intValue() != 0 || this.testedMethod == null)) {
            if (this.renderType.intValue() == 1) {
                if (caseResult.getInput() != null && caseResult.getInput().containsKey(strValue)) {
                    caseParam = caseResult.getInput().get(strValue);
                }
            } else if (this.renderType.intValue() == 2 && caseResult.getOutput() != null) {
                caseParam = caseResult.getOutput();
            }
        }
        buildVaribleCallParam(stringBuilder, new Node<>(new SyntheticParam(type, strValue), null, 0), caseParam, 2);
        String string = stringBuilder.toString();
        if (StringUtils.isEmpty(string)) {
            this.testedMethod.getResolveComponents().tryRemoveResolveByName(strValue);
            return string;
        }
        this.testedMethod.getResolveComponents().getRendered(type, this.renderType.intValue(), StringUtils.deCapitalizeFirstLetter(strValue)).orElse(null);
        type.getCanonicalName();
        String canName = resolveGenericTypeName(type.getCanonicalName(), null);
        if (type.isArray()) {
            int arrayDimensions = type.getArrayDimensions();
            for (int i = arrayDimensions; i > 0; i--) {
                canName = canName + "[]";
            }
        }
        String string2 = canName + " " + strValue + " = " + string;
        if (!string2.trim().endsWith(";")) {
            string2 = string2 + ";";
        }
        return string2;
    }

    @Override // com.aicode.template.context.service.LangTestBuilder
    public String renderJavaMethodCaseBody(Type testedClass, Method testedMethod) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.toString();
    }

    private String convertDefault(Object data, String canonicalName, Node<Param> node) {
        boolean contains;
        boolean contains2;
        Type type = node.getData().getType();
        if (DEFAULT_TYPE_TO_MATCHERS.get(canonicalName) != null) {
            String value = "null";
            if (data != null && !data.toString().startsWith("{") && !data.toString().startsWith("[")) {
                value = String.valueOf(data);
            }
            if (DataUtils.isNumberType(type)) {
                if (DataUtils.checkNumberData(value)) {
                    return ((value.length() > 1 && value.endsWith("L")) || value.endsWith("l") || value.endsWith("d") || value.endsWith("D") || value.endsWith("f") || value.endsWith("F")) ? value : String.format(DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), value);
                }
                if (DataUtils.canSetNullValue(canonicalName) && DataUtils.isNull(data)) {
                    return value;
                }
                return convertCustomParamValue(canonicalName, resolveName(node));
            }
            if (DataUtils.isBooleanType(type)) {
                return DataUtils.convertToBoolean(value, DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), canonicalName);
            }
            if (DataUtils.isDateType(type)) {
                try {
                    Date date = Convert.toDate(data);
                    if (date != null) {
                        if (canonicalName.equalsIgnoreCase("java.util.Date")) {
                            return String.format(DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), Long.valueOf(date.getTime()));
                        }
                        if (canonicalName.equalsIgnoreCase("java.time.LocalDate")) {
                            return String.format(DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), Integer.valueOf(1900 + date.getYear()), Integer.valueOf(date.getMonth() + 1), Integer.valueOf(Math.max(date.getDate(), 1)));
                        }
                        if (canonicalName.equalsIgnoreCase("java.time.LocalDateTime")) {
                            return String.format(DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), Integer.valueOf(1900 + date.getYear()), Integer.valueOf(date.getMonth() + 1), Integer.valueOf(Math.max(date.getDate(), 1)), Integer.valueOf(date.getHours()), Integer.valueOf(date.getMinutes()), Integer.valueOf(date.getSeconds()));
                        }
                        if (canonicalName.equalsIgnoreCase("java.time.LocalTime")) {
                            return String.format(DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), Integer.valueOf(date.getHours()), Integer.valueOf(date.getMinutes()), Integer.valueOf(date.getSeconds()));
                        }
                    }
                    return data == null ? "null" : this.defaultTypeValues.get(canonicalName);
                } catch (Exception e) {
                    LOG.warn("转换时间异常", e);
                }
            } else if (type.isArray() || type.isCollection()) {
                if (!(data instanceof JSONArray)) {
                    return String.format(DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), value);
                }
                JSONArray jsonArray = (JSONArray) data;
                if (jsonArray != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    Iterator it = jsonArray.iterator();
                    while (it.hasNext()) {
                        Object o = it.next();
                        stringBuilder.append(String.format(DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), o)).append(", ");
                    }
                    if (stringBuilder.length() > 0) {
                        stringBuilder.delete(stringBuilder.length() - ", ".length(), stringBuilder.length());
                    }
                    return stringBuilder.toString();
                }
            } else if (data == null && DataUtils.canSetNullValue(canonicalName)) {
                return value;
            }
            return data == null ? this.defaultTypeValues.get(canonicalName) : String.format(DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), value);
        }
        if (this.defaultTypeValues.get(canonicalName) != null) {
            return this.defaultTypeValues.get(canonicalName);
        }
        contains = TypeUtils.f739if.contains(canonicalName);
        if (contains) {
            Object[] objArr = new Object[1];
            objArr[0] = data == null ? "data" : String.valueOf(data);
            return String.format("\"%s\"", objArr);
        }
        contains2 = TypeUtils.f738for.contains(canonicalName);
        if (contains2) {
            return "new java.text.SimpleDateFormat(\"yyyy-MM-dd\")";
        }
        if (hasEnumValues(type)) {
            return renderEnumValue(type, data);
        }
        if (canonicalName.equals(DEFAULT_Object_TYPE.getCanonicalName()) && data != null && canConvertDefault(data)) {
            Type convertToType = null;
            if (data instanceof JSONObject) {
                convertToType = DEFAULT_Map_TYPE;
            } else if (data instanceof JSONArray) {
                convertToType = DEFAULT_List_TYPE;
            }
            if (convertToType != null) {
                StringBuilder objectStringBuild = new StringBuilder();
                renderMapOrList(convertToType.getCanonicalName(), objectStringBuild, convertToType, new Node<>(new SyntheticParam(convertToType, node.getData().getName()), null, 0), new CaseParam(convertToType.getName(), convertToType.getName(), convertToType.getCanonicalName(), data), new ArrayList(), node.getDepth());
                return objectStringBuild.toString();
            }
            String newCanonName = getConvertDefaultName(data);
            if (DEFAULT_TYPE_TO_MATCHERS.containsKey(data.getClass().getCanonicalName())) {
                newCanonName = data.getClass().getCanonicalName();
            }
            if (newCanonName != null) {
                return convertDefault(data, newCanonName, node);
            }
            return "";
        }
        return "null";
    }

    private boolean canConvertDefault(Object data) {
        for (String key : DEFAULT_TYPE_TO_MATCHERS.keySet()) {
            try {
                Object a = Convert.convertByClassName(key, data);
                return a != null;
            } catch (Exception e) {
            }
        }
        return false;
    }

    private String getConvertDefaultName(Object data) {
        for (String key : DEFAULT_TYPE_TO_MATCHERS.keySet()) {
            try {
                Object a = Convert.convertByClassName(key, data);
                if (a == null) {
                    return null;
                }
                return key;
            } catch (Exception e) {
            }
        }
        return null;
    }

    protected String renderEnumValue(Type type, Object data) {
        String enumValue = type.getEnumValues().get(0);
        String dataStr = data.toString();
        String canonicalName = type.getCanonicalName();
        for (String value : type.getEnumValues()) {
            if (value.equalsIgnoreCase(dataStr)) {
                return canonicalName + "." + value;
            }
        }
        return canonicalName + "." + enumValue;
    }

    protected void buildCallParam(StringBuilder testCodeString, Node<Param> paramNode, CaseParam caseResult, int dept) {
        Type type = paramNode.getData().getType();
        boolean isDataNull = caseResult != null && caseResult.getData() == null;
        Optional<ResolveVarible> optional = this.testedMethod != null ? this.testedMethod.getResolveComponents().getRendered(type, this.renderType.intValue(), paramNode.getData().getName()) : Optional.empty();
        if (optional.isPresent()) {
            testCodeString.append(optional.get().getName());
            return;
        }
        int arrayDimensions = type.getArrayDimensions();
        if (!isDataNull && type.isArray()) {
            for (int i = arrayDimensions; i > 0; i--) {
                testCodeString.append("new ").append(type.getCanonicalName()).append("[]".repeat(i)).append("{");
            }
        }
        Type parentContainerClass = type.getParentContainerClass();
        if (!isDataNull && parentContainerClass != null && !type.isStatic() && !type.isEnum()) {
            Node<Param> parentContainerNode = new Node<>(new SyntheticParam(parentContainerClass, parentContainerClass.getName()), null, paramNode.getDepth());
            buildCallParam(testCodeString, parentContainerNode, caseResult, dept);
            if (!StrUtil.endWith(testCodeString.toString(), "null")) {
                testCodeString.append(".");
            } else {
                testCodeString.delete(testCodeString.length() - 4, testCodeString.length());
            }
        }
        if (!isDataNull && type.isArray() && caseResult != null && (caseResult.getData() instanceof JSONArray)) {
            JSONArray array = (JSONArray) caseResult.getData();
            for (int i2 = 0; i2 < array.size(); i2++) {
                buildJavaParam(testCodeString, paramNode, new CaseParam("", "", "", array.get(i2)), dept);
                if (i2 < array.size() - 1) {
                    testCodeString.append(",");
                }
            }
        } else if (isDataNull && type.isArray()) {
            testCodeString.append("(");
            for (int i3 = arrayDimensions; i3 > 0; i3--) {
                testCodeString.append(type.getCanonicalName()).append("[]".repeat(i3));
            }
            testCodeString.append(")");
            testCodeString.append("null");
        } else {
            buildJavaParam(testCodeString, paramNode, caseResult, dept);
        }
        if (!isDataNull && type.isArray()) {
            deleteLastSpec(testCodeString);
            testCodeString.append("}".repeat(arrayDimensions));
        }
    }

    protected void buildVaribleCallParam(StringBuilder testCodeString, Node<Param> paramNode, CaseParam caseResult, int dept) {
        Type type = paramNode.getData().getType();
        boolean isDataNull = caseResult != null && caseResult.getData() == null;
        int arrayDimensions = type.getArrayDimensions();
        if (!isDataNull && type.isArray()) {
            testCodeString.append("new ").append(type.getCanonicalName()).append("[]".repeat(arrayDimensions)).append("{");
        }
        Type parentContainerClass = type.getParentContainerClass();
        if (!isDataNull && parentContainerClass != null && !type.isStatic() && !type.isEnum()) {
            Node<Param> parentContainerNode = new Node<>(new SyntheticParam(parentContainerClass, parentContainerClass.getName()), null, paramNode.getDepth());
            buildVaribleCallParam(testCodeString, parentContainerNode, caseResult, dept);
            testCodeString.append(".");
        }
        if (!isDataNull && type.isArray() && caseResult != null && (caseResult.getData() instanceof JSONArray)) {
            JSONArray array = (JSONArray) caseResult.getData();
            if (arrayDimensions > 1) {
                Object data = caseResult.getData();
                if (Objects.nonNull(data)) {
                    for (int i = 0; i < array.size(); i++) {
                        Object caseData = array.get(i);
                        if (caseData == null) {
                            testCodeString.append("null");
                        } else {
                            Type childType = new Type(type.getCanonicalName(), type.getName(), type.getPackageName(), type.isPrimitive(), type.isInterface(), type.isAbstract(), type.isArray(), type.getArrayDimensions() - 1, type.isVarargs(), type.getComposedTypes());
                            Node childNode = new Node(new SyntheticParam(childType, paramNode.getData().getName()), null, dept);
                            buildVaribleCallParam(testCodeString, childNode, new CaseParam("", "", "", array.get(i)), dept);
                        }
                        if (i < array.size() - 1) {
                            testCodeString.append(",");
                        }
                    }
                }
            } else {
                for (int i2 = 0; i2 < array.size(); i2++) {
                    buildJavaVarible(testCodeString, paramNode, new CaseParam("", "", "", array.get(i2)), dept, i2);
                    if (i2 < array.size() - 1) {
                        testCodeString.append(",");
                    }
                }
            }
        } else if (isDataNull && type.isArray()) {
            testCodeString.append("null");
        } else if (arrayDimensions > 1) {
            Type childType2 = new Type(type.getCanonicalName(), type.getName(), type.getPackageName(), type.isPrimitive(), type.isInterface(), type.isAbstract(), type.isArray(), type.getArrayDimensions() - 1, type.isVarargs(), type.getComposedTypes());
            Node childNode2 = new Node(new SyntheticParam(childType2, paramNode.getData().getName()), null, dept);
            buildVaribleCallParam(testCodeString, childNode2, caseResult, dept);
        } else {
            buildJavaVarible(testCodeString, paramNode, caseResult, dept, 0);
        }
        if (!isDataNull && type.isArray()) {
            deleteLastSpec(testCodeString);
            testCodeString.append("}");
        }
        if (type.isArray() && this.testedMethod != null) {
            this.testedMethod.getResolveComponents().insertRender(new ResolveVarible(StringUtils.deCapitalizeFirstLetter(paramNode.getData().getName()), type, this.renderType.intValue()));
        }
    }

    private String convertCustomParamValue(String canonicalName, String resolveName) {
        if (this.testedMethod != null) {
            String methodName = this.testedMethod.getName();
            if (methodName.equalsIgnoreCase("handle")) {
                if (canonicalName.equals("java.util.HashMap")) {
                    return new String("new java.util.HashMap(){{put(\"\",\"\");}}");
                }
            } else if (methodName.contains("count") || methodName.contains("delete") || methodName.contains("save") || methodName.contains("status") || methodName.contains("state") || methodName.contains("num") || methodName.contains("update")) {
                if (canonicalName.equals("java.lang.Integer") || canonicalName.equals("int")) {
                    return String.format(DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), Integer.valueOf(RandomUtil.randomInt(0, 9)));
                }
                if (canonicalName.equals("java.lang.Double") || canonicalName.equals("double") || canonicalName.equals("java.lang.Float") || canonicalName.equals("float")) {
                    return String.format(DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), Double.valueOf(RandomUtil.randomDouble()));
                }
                if (canonicalName.equals("java.lang.Long") || canonicalName.equals("long")) {
                    return String.format(DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), Long.valueOf(RandomUtil.randomLong(0L, 9L)));
                }
                if (DataUtils.isBooleanType(canonicalName)) {
                    return String.format(DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), Boolean.valueOf(RandomUtil.randomBoolean()));
                }
            } else if (methodName.contains("score") || methodName.contains("duration")) {
                return (canonicalName.equals("java.lang.Double") || canonicalName.equals("double") || canonicalName.equals("java.lang.Float") || canonicalName.equals("float")) ? String.format(DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), Double.valueOf(RandomUtil.randomDouble())) : String.format(DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), 10);
            }
        }
        return (resolveName == null || !(resolveName.equalsIgnoreCase("pageSize") || resolveName.equalsIgnoreCase("limit") || resolveName.equalsIgnoreCase("pageLimit"))) ? (resolveName == null || !(resolveName.equalsIgnoreCase("pageStart") || resolveName.equalsIgnoreCase("page"))) ? ((resolveName == null || !resolveName.equalsIgnoreCase("deleted")) && !resolveName.equalsIgnoreCase("isdeleted")) ? canonicalName.equals("java.lang.Number") ? String.format(DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), 1) : this.defaultTypeValues.get(canonicalName) : (canonicalName.equals("java.lang.Boolean") || canonicalName.equals("boolean")) ? String.format(DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), false) : String.format(DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), 0) : String.format(DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), 1) : String.format(DEFAULT_TYPE_TO_MATCHERS.get(canonicalName), 10);
    }

    void buildJavaParam(StringBuilder testBuilder, Node<Param> paramNode, CaseParam caseParam, int dept) {
        boolean contains;
        boolean contains2;
        try {
            Type type = paramNode.getData().getType();
            String canonicalName = type.getCanonicalName();
            String name = this.testedMethod != null ? this.testedMethod.getName() : "";
            Optional<ResolveVarible> optional = this.testedMethod != null ? this.testedMethod.getResolveComponents().getRendered(type, this.renderType.intValue(), paramNode.getData().getName()) : Optional.empty();
            if (optional.isPresent()) {
                testBuilder.append(optional.get().getName());
            } else if (this.defaultTypeValues.get(canonicalName) == null) {
                contains = TypeUtils.f739if.contains(canonicalName);
                if (contains) {
                    if (caseParam != null) {
                        if (caseParam.getData() != null && ((caseParam.getData() instanceof JSONObject) || (caseParam.getData() instanceof JSONArray))) {
                            testBuilder.append(String.format("\"%s\"", JSONUtil.toJsonStr(caseParam.getData()).replaceAll("\"", "\\\"").replaceAll("(?<!\\\\)\"", "\\\\\"")));
                        } else if (caseParam.getData() != null && (caseParam.getData() instanceof String)) {
                            String data = caseParam.getData().toString();
                            if (data.startsWith("\"")) {
                                testBuilder.append(data);
                            } else {
                                testBuilder.append(String.format("\"%s\"", data.replaceAll("\\\\\"", "\\\\\\\\\\\\\"").replaceAll("(?<!\\\\)\"", "\\\\\"")));
                            }
                        } else {
                            testBuilder.append("null");
                        }
                    } else {
                        testBuilder.append("\"").append(resolveName(paramNode)).append("\"");
                    }
                } else {
                    contains2 = TypeUtils.f738for.contains(canonicalName);
                    if (contains2) {
                        testBuilder.append("new java.text.SimpleDateFormat(\"yyyy-MM-dd\")");
                    } else if (hasEnumValues(type)) {
                        renderEnumValue(testBuilder, type);
                    } else {
                        Type resolvedType = JavaPsiUtils.resolveChildTypeIfNeeded(type, this.fileTemplateConfig.isReplaceInterfaceParamsWithConcreteTypes(), this.fileTemplateConfig.getMaxNumOfConcreteCandidatesToReplaceInterfaceParam(), this.srcModule, this.typeDictionary, DEFAULT_TYPE_TO_BOCOM);
                        Map<String, Method> usedMethods = new HashMap<>();
                        if (this.testedMethod != null) {
                            filterGetMethodCall(this.testedMethod, type.getCanonicalName()).stream().forEach(mc -> {
                                String key = CollectionUtils.isEmpty(mc.getMethodCallArguments()) ? PropertyUtils.inferFieldNameFromAccessor(mc.getMethod().getName()) : mc.getMethodCallArguments().get(0).getText();
                                usedMethods.put(key, mc.getMethod());
                            });
                            if (caseParam != null && caseParam.getData() != null && (caseParam.getData() instanceof JSONObject)) {
                                JSONObject jsonObject = (JSONObject) caseParam.getData();
                                resolvedType.getMethods().stream().filter((v0) -> {
                                    return v0.isGetter();
                                }).forEach(method -> {
                                    if (StringUtils.isNotBlank(method.getPropertyName()) && jsonObject.containsKey(method.getPropertyName())) {
                                        usedMethods.put(PropertyUtils.inferFieldNameFromAccessor(method.getName()), method);
                                    }
                                });
                            }
                            if (!this.testedMethod.getMethodReferences().isEmpty()) {
                                this.testedMethod.getMethodReferences().forEach(mr -> {
                                    usedMethods.put(PropertyUtils.inferFieldNameFromAccessor(mr.getName()), mr);
                                });
                            }
                        } else {
                            usedMethods.clear();
                        }
                        if (!resolvedType.equals(type)) {
                            paramNode = new Node<>(new Param(resolvedType, paramNode.getData().getName(), paramNode.getData().getAssignedToFields()), paramNode.getParent(), paramNode.getDepth());
                        }
                        String typeName = resolveTypeName(resolvedType);
                        if (!resolvedType.getCanonicalName().equals(typeName)) {
                            if (caseParam == null || caseParam.getData() != null) {
                                renderMapOrList(canonicalName, testBuilder, resolvedType, paramNode, caseParam, filterGetMethodCall(this.testedMethod, type.getCanonicalName()), Math.max(dept, 2));
                            } else {
                                testBuilder.append("null");
                            }
                        } else if (shouldContinueRecursion(paramNode)) {
                            boolean hasEmptyConstructor = TypeUtils.hasValidEmptyConstructor(resolvedType);
                            Method foundCtor = findValidConstructor(resolvedType, hasEmptyConstructor);
                            LOG.debug(dept + "::" + canonicalName + ":" + hasEmptyConstructor + ":foundCtor" + (foundCtor == null ? "" : foundCtor.getMethodId()));
                            if (foundCtor == null && !hasEmptyConstructor) {
                                if (canonicalName.contains("java.lang.Class") && this.testedMethod != null && (this.testedMethod.getName().contains("getBean") || this.testedMethod.getName().contains("getbean"))) {
                                    testBuilder.append("Object.class");
                                } else if (caseParam != null && caseParam.getData() != null) {
                                    testBuilder.append(convertDefault(caseParam.getData(), canonicalName, paramNode));
                                } else if (resolvedType.getMethods().stream().anyMatch(method2 -> {
                                    return method2.isStatic() && method2.isPublic() && method2.getReturnType().typeEquals(resolvedType);
                                })) {
                                    resolveMethod(resolvedType.getMethods().stream().filter(method3 -> {
                                        return method3.isStatic() && method3.isPublic() && method3.getReturnType().typeEquals(resolvedType);
                                    }).findFirst().orElse(null), paramNode, caseParam, testBuilder, type, resolvedType, typeName, Boolean.valueOf(hasEmptyConstructor), usedMethods, dept);
                                } else {
                                    String genericTypeName = resolveGenericTypeName(typeName, null);
                                    if (TypeUtils.isLanguageBaseClass(genericTypeName)) {
                                        if (this.typesOverrides.get(genericTypeName) != null) {
                                            testBuilder.append(this.typesOverrides.get(genericTypeName));
                                        } else if (caseParam != null && caseParam.getData() == null) {
                                            testBuilder.append("null");
                                        } else if (this.paramRole == TestBuilder.ParamRole.Mock && (type.isInterface() || type.isAbstract())) {
                                            testBuilder.append("Mockito.mock(" + type.getCanonicalName() + ".class)");
                                        } else {
                                            testBuilder.append("\"//TODO: 请手动补全参数\"");
                                        }
                                    } else {
                                        testBuilder.append("null");
                                    }
                                }
                            } else if ("java.lang.Object".equals(canonicalName)) {
                                if (caseParam != null && caseParam.getData() != null) {
                                    testBuilder.append(convertDefault(caseParam.getData(), canonicalName, paramNode));
                                } else {
                                    testBuilder.append("null");
                                }
                            } else {
                                resolveMethod(foundCtor, paramNode, caseParam, testBuilder, type, resolvedType, typeName, Boolean.valueOf(hasEmptyConstructor), usedMethods, dept);
                            }
                        } else if (this.testedMethod != null && (this.testedMethod.getName().contains("getBean") || this.testedMethod.getName().contains("getbean"))) {
                            testBuilder.append("Object.class");
                        } else {
                            String genericTypeName2 = resolveGenericTypeName(typeName, null);
                            if (TypeUtils.isLanguageBaseClass(genericTypeName2)) {
                                if (this.typesOverrides.get(genericTypeName2) != null) {
                                    testBuilder.append(this.typesOverrides.get(genericTypeName2));
                                } else if (this.paramRole == TestBuilder.ParamRole.Mock && (type.isInterface() || type.isAbstract())) {
                                    testBuilder.append("Mockito.mock(").append(type.getCanonicalName()).append(".class)");
                                } else if (caseParam != null && caseParam.getData() == null) {
                                    testBuilder.append("null");
                                } else {
                                    testBuilder.append("\"//TODO: 请手动补全参数\"");
                                }
                            } else {
                                testBuilder.append("null");
                            }
                        }
                    }
                }
            } else if (caseParam != null) {
                testBuilder.append(convertDefault(caseParam.getData(), canonicalName, paramNode));
            } else {
                testBuilder.append(convertCustomParamValue(canonicalName, resolveName(paramNode)));
            }
        } catch (Throwable e) {
            LOG.warn("[buildJavaParam] fail : " + e.getMessage());
            LOG.warn("[buildJavaParam] fail : " + e.getCause());
            LOG.warn("[buildJavaParam] fail : " + e.getStackTrace());
        }
    }

    private void resolveMethod(Method foundCtor, Node<Param> paramNode, CaseParam caseParam, StringBuilder testBuilder, Type type, Type resolvedType, String typeName, Boolean hasEmptyConstructor, Map<String, Method> usedMethods, int dept) {
        JSONObject jsonObject = null;
        if (caseParam != null && caseParam.getData() == null) {
            testBuilder.append("null");
            return;
        }
        testBuilder.append(resolveInitializerKeyword(type, foundCtor));
        if (resolvedType.getParentContainerClass() != null && !resolvedType.isStatic() && !resolvedType.isEnum()) {
            typeName = resolveNestedClassTypeName(typeName);
        }
        testBuilder.append(resolveGenericTypeName(typeName, foundCtor)).append("(");
        List<Param> methodParams = (hasEmptyConstructor.booleanValue() || foundCtor == null) ? new ArrayList<>() : foundCtor.getMethodParams();
        if (caseParam != null) {
            if (caseParam.getData() instanceof JSONObject) {
                jsonObject = (JSONObject) caseParam.getData();
            } else if (caseParam.getData() != null && methodParams.size() == 1) {
                jsonObject = new JSONObject();
                jsonObject.put(methodParams.get(0).getName(), caseParam.getData());
            }
        }
        if (!hasEmptyConstructor.booleanValue()) {
            buildCallParams(foundCtor, methodParams, testBuilder, paramNode, jsonObject, dept - 1);
        }
        testBuilder.append(")");
        if (!resolvedType.isFinal() && dept > 0 && methodParams.isEmpty()) {
            StringBuilder methodFiledSet = new StringBuilder();
            renderSetMethod(methodFiledSet, usedMethods, resolvedType, paramNode, jsonObject, "", dept);
            if (methodFiledSet.length() > 0) {
                testBuilder.append("{{");
                testBuilder.append(methodFiledSet.toString());
                testBuilder.append("}}");
            }
        }
    }

    private void renderSetMethod(StringBuilder methodFiledSet, Map<String, Method> usedMethods, Type resolvedType, Node<Param> finalParamNode, JSONObject finalJsonObject, String callVarName, int maxRecursionDepth) {
        Set<String> keys = new HashSet<>();
        for (Map.Entry<String, Method> entry : usedMethods.entrySet()) {
            Method usedMethod = entry.getValue();
            String usedPropertyName = PropertyUtils.inferFieldNameFromAccessor(usedMethod.getName());
            String keyName = entry.getKey();
            resolvedType.getMethods().stream().filter(method1 -> {
                return Objects.nonNull(method1) && StrUtil.isNotEmpty(usedPropertyName) && usedPropertyName.equalsIgnoreCase(PropertyUtils.inferFieldNameFromAccessor(method1.getName())) && !keys.contains(usedMethod.getMethodIdAndType()) && PropertyUtils.isSampleSetMethod(resolvedType.getCanonicalName(), method1);
            }).findFirst().ifPresent(method -> {
                JSONObject copyJson = new JSONObject();
                if (method.getMethodParams().size() == 2) {
                    Object data = null;
                    if (finalJsonObject != null && finalJsonObject.containsKey(usedPropertyName)) {
                        data = finalJsonObject.get(usedPropertyName);
                    } else if (resolvedType.getFields().stream().anyMatch(field -> {
                        return finalJsonObject != null && finalJsonObject.containsKey(field.getName());
                    })) {
                        Field fieldData = resolvedType.getFields().stream().filter(field2 -> {
                            return finalJsonObject.containsKey(field2.getName());
                        }).findFirst().orElse(null);
                        data = finalJsonObject.get(fieldData.getName());
                    }
                    if ((data instanceof JSONObject) && ((JSONObject) data).containsKey(keyName)) {
                        final JSONObject cData = (JSONObject) data;
                        data = new JSONObject() { // from class: com.aicode.template.context.service.impl.JavaTestBuilderImpl.1
                            {
                                putOpt(keyName, cData.get(keyName));
                            }
                        };
                    } else if (finalJsonObject != null && StringUtils.isNotEmpty(keyName) && finalJsonObject.containsKey(keyName)) {
                        data = new JSONObject() { // from class: com.aicode.template.context.service.impl.JavaTestBuilderImpl.2
                            {
                                putOpt(keyName, finalJsonObject.get(keyName));
                            }
                        };
                    } else if (finalJsonObject != null) {
                        Iterator it = finalJsonObject.entrySet().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Map.Entry<String, Object> caseResultJson = (Map.Entry) it.next();
                            if (caseResultJson.getValue() instanceof JSONObject) {
                                JSONObject jsonObject = (JSONObject) caseResultJson.getValue();
                                if (jsonObject.containsKey(keyName)) {
                                    data = new JSONObject() { // from class: com.aicode.template.context.service.impl.JavaTestBuilderImpl.3
                                        {
                                            putOpt(keyName, finalJsonObject.get(keyName));
                                        }
                                    };
                                    break;
                                }
                            }
                        }
                    }
                    if (data instanceof JSONObject) {
                        JSONObject jsonData = (JSONObject) data;
                        if (!jsonData.isEmpty()) {
                            for (Map.Entry<String, Object> stringObjectEntry : jsonData.entrySet()) {
                                if (keyName.equals(stringObjectEntry.getKey())) {
                                    if (StringUtils.isNotBlank(callVarName)) {
                                        methodFiledSet.append(callVarName).append(".");
                                    }
                                    methodFiledSet.append(method.getName()).append("(");
                                    List<Param> methodParams = new ArrayList<>();
                                    copyJson.clear();
                                    copyJson.putOpt(method.getMethodParams().get(0).getName(), stringObjectEntry.getKey());
                                    methodParams.add(method.getMethodParams().get(0));
                                    if (usedMethod.hasReturn() && !method.getMethodParams().get(1).getType().typeEquals(usedMethod.getReturnType())) {
                                        Param copyParam = method.getMethodParams().get(1);
                                        Param param = new Param(usedMethod.getReturnType(), copyParam.getName(), copyParam.getAssignedToFields());
                                        methodParams.add(param);
                                    } else {
                                        methodParams.add(method.getMethodParams().get(1));
                                    }
                                    copyJson.putOpt(method.getMethodParams().get(1).getName(), stringObjectEntry.getValue());
                                    buildCallParams(method, methodParams, methodFiledSet, finalParamNode, copyJson, 2);
                                    deleteLastSpec(methodFiledSet);
                                    methodFiledSet.append(");");
                                }
                            }
                        }
                    } else {
                        copyJson.clear();
                        if (StringUtils.isNotBlank(callVarName)) {
                            methodFiledSet.append(callVarName).append(".");
                        }
                        methodFiledSet.append(method.getName()).append("(");
                        copyJson.putOpt(method.getMethodParams().get(0).getName(), keyName);
                        List<Param> methodParams2 = new ArrayList<>();
                        methodParams2.add(method.getMethodParams().get(0));
                        if (usedMethod.hasReturn() && !method.getMethodParams().get(1).getType().typeEquals(usedMethod.getReturnType())) {
                            Param copyParam2 = method.getMethodParams().get(1);
                            Param param2 = new Param(usedMethod.getReturnType(), copyParam2.getName(), copyParam2.getAssignedToFields());
                            methodParams2.add(param2);
                        } else {
                            methodParams2.add(method.getMethodParams().get(1));
                        }
                        buildCallParams(method, methodParams2, methodFiledSet, finalParamNode, copyJson, 2);
                        deleteLastSpec(methodFiledSet);
                        methodFiledSet.append(");");
                    }
                } else if (method.getMethodParams().size() == 1) {
                    if (StringUtils.isNotBlank(callVarName)) {
                        methodFiledSet.append(callVarName).append(".");
                    }
                    methodFiledSet.append(method.getName()).append("(");
                    JSONObject data2 = null;
                    if (finalJsonObject != null && finalJsonObject.containsKey(usedPropertyName) && finalJsonObject.get(usedPropertyName) != null) {
                        data2 = new JSONObject();
                        data2.putOpt(method.getMethodParams().get(0).getName(), finalJsonObject.get(usedPropertyName));
                    }
                    buildCallParams(method, method.getMethodParams(), methodFiledSet, finalParamNode, data2 == null ? finalJsonObject : data2, 2);
                    deleteLastSpec(methodFiledSet);
                    methodFiledSet.append(");");
                }
                keys.add(usedMethod.getMethodIdAndType() + ((String) entry.getKey()));
            });
        }
    }

    List<MethodCall> filterGetMethodCall(Method method, String canonicalName) {
        if (method == null) {
            return new ArrayList();
        }
        if (TypeUtils.isMap(canonicalName).booleanValue()) {
            List<String> getMethods = Arrays.asList("getValue", "get", "getKey");
            return (List) method.getMethodCalls().stream().filter(mc -> {
                return TypeUtils.isMap(mc.getMethod().getOwnerClassCanonicalType()).booleanValue() && getMethods.contains(mc.getMethod().getName());
            }).collect(Collectors.toList());
        }
        List<String> specGetMethods = Arrays.asList("getData");
        return (List) method.getMethodCalls().stream().filter(mc2 -> {
            return PropertyUtils.isSampleGetMethod(canonicalName, mc2.getMethod()) || specGetMethods.contains(mc2.getMethod().getName());
        }).collect(Collectors.toList());
    }

    void buildJavaVarible(StringBuilder testBuilder, Node<Param> paramNode, CaseParam caseParam, int dept, int times) {
        boolean contains;
        boolean contains2;
        Boolean valueOf;
        try {
            Type type = paramNode.getData().getType();
            String canonicalName = type.getCanonicalName();
            String name = this.testedMethod != null ? this.testedMethod.getName() : "";
            Optional<ResolveVarible> optional = this.testedMethod == null ? Optional.empty() : this.testedMethod.getResolveComponents().getRendered(type, this.renderType.intValue(), paramNode.getData().getName());
            if (optional.isPresent() && times == 0) {
                testBuilder.append(optional.get().getName());
            } else if (this.defaultTypeValues.get(canonicalName) == null) {
                contains = TypeUtils.f739if.contains(canonicalName);
                if (!contains) {
                    contains2 = TypeUtils.f738for.contains(canonicalName);
                    if (contains2) {
                        testBuilder.append("new java.text.SimpleDateFormat(\"yyyy-MM-dd\")");
                    } else if (!hasEnumValues(type)) {
                        type.getArrayDimensions();
                        valueOf = Boolean.valueOf(StringUtils.endWith(a, RequestResultList.H("\\h")) && a > 1);
                        if (valueOf.booleanValue()) {
                            testBuilder.append("{");
                            if (caseParam.getData() instanceof JSONArray) {
                                JSONArray data = (JSONArray) caseParam.getData();
                                for (int i = 0; i < data.size(); i++) {
                                    if (i < data.size() - 1) {
                                        testBuilder.append(data.get(i) + ",");
                                    } else {
                                        testBuilder.append(data.get(i) + "}");
                                    }
                                }
                            }
                        } else {
                            Map<String, Method> usedMethods = new HashMap<>();
                            Type resolvedType = JavaPsiUtils.resolveChildTypeIfNeeded(type, this.fileTemplateConfig.isReplaceInterfaceParamsWithConcreteTypes(), this.fileTemplateConfig.getMaxNumOfConcreteCandidatesToReplaceInterfaceParam(), this.srcModule, this.typeDictionary, DEFAULT_TYPE_TO_BOCOM);
                            if (this.testedMethod != null) {
                                filterGetMethodCall(this.testedMethod, type.getCanonicalName()).stream().forEach(mc -> {
                                    String key = CollectionUtils.isEmpty(mc.getMethodCallArguments()) ? PropertyUtils.inferFieldNameFromAccessor(mc.getMethod().getName()) : mc.getMethodCallArguments().get(0).getText();
                                    usedMethods.put(key, mc.getMethod());
                                });
                                if (!this.testedMethod.getMethodReferences().isEmpty()) {
                                    this.testedMethod.getMethodReferences().forEach(mr -> {
                                        usedMethods.put(PropertyUtils.inferFieldNameFromAccessor(mr.getName()), mr);
                                    });
                                }
                                if (caseParam != null && caseParam.getData() != null && (caseParam.getData() instanceof JSONObject)) {
                                    JSONObject jsonObject = (JSONObject) caseParam.getData();
                                    resolvedType.getMethods().stream().filter(m -> {
                                        return PropertyUtils.isSampleGetMethod(resolvedType.getCanonicalName(), m);
                                    }).forEach(method -> {
                                        String propertyName = PropertyUtils.inferFieldNameFromAccessor(method.getName());
                                        if (StringUtils.isNotBlank(propertyName) && jsonObject.containsKey(propertyName)) {
                                            usedMethods.put(propertyName, method);
                                        }
                                    });
                                }
                            } else {
                                usedMethods.clear();
                            }
                            if (!resolvedType.equals(type)) {
                                paramNode = new Node<>(new Param(resolvedType, paramNode.getData().getName(), paramNode.getData().getAssignedToFields()), paramNode.getParent(), paramNode.getDepth());
                            }
                            String typeName = resolveTypeName(resolvedType);
                            boolean buildMethod = false;
                            if (!resolvedType.getCanonicalName().equals(typeName)) {
                                if (caseParam != null && caseParam.getData() == null) {
                                    testBuilder.append("null");
                                } else {
                                    renderMapOrList(canonicalName, testBuilder, resolvedType, paramNode, caseParam, filterGetMethodCall(this.testedMethod, type.getCanonicalName()), Math.max(dept, 2));
                                }
                                if (this.testedMethod != null) {
                                    this.testedMethod.getResolveComponents().insertRender(new ResolveVarible(StringUtils.deCapitalizeFirstLetter(paramNode.getData().getName()), resolvedType, this.renderType.intValue()));
                                }
                            } else if (shouldContinueRecursion(paramNode)) {
                                boolean hasEmptyConstructor = TypeUtils.hasValidEmptyConstructor(resolvedType);
                                Method foundCtor = findValidConstructor(resolvedType, hasEmptyConstructor);
                                LOG.debug(dept + "::" + canonicalName + ":" + hasEmptyConstructor + ":foundCtor" + (foundCtor == null ? "" : foundCtor.getMethodId()));
                                if (foundCtor == null && !hasEmptyConstructor) {
                                    if (canonicalName.contains("java.lang.Class") && this.testedMethod != null && (this.testedMethod.getName().contains("getBean") || this.testedMethod.getName().contains("getbean"))) {
                                        testBuilder.append("Object.class");
                                    } else if (resolvedType.getMethods().stream().anyMatch(method2 -> {
                                        return method2.isStatic() && method2.isPublic() && method2.getReturnType().typeEquals(resolvedType);
                                    })) {
                                        foundCtor = resolvedType.getMethods().stream().filter(method3 -> {
                                            return method3.isStatic() && method3.isPublic() && method3.getReturnType().typeEquals(resolvedType);
                                        }).findFirst().orElse(null);
                                        if (foundCtor != null) {
                                            Optional<MethodCall> optionalMethod = this.testedMethod.getMethodCalls().stream().filter(methodCall -> {
                                                return methodCall.getMethod().methodEquals(foundCtor);
                                            }).findFirst();
                                            if (optionalMethod.isPresent()) {
                                                foundCtor = optionalMethod.get().getMethod();
                                            }
                                        }
                                        buildMethod = true;
                                    } else {
                                        String genericTypeName = resolveGenericTypeName(typeName, null);
                                        if (TypeUtils.isLanguageBaseClass(genericTypeName)) {
                                            if (this.typesOverrides.get(genericTypeName) != null) {
                                                testBuilder.append(this.typesOverrides.get(genericTypeName));
                                            } else if (caseParam != null) {
                                                testBuilder.append(convertDefault(caseParam.getData(), canonicalName, paramNode)).append(";");
                                            } else {
                                                testBuilder.append("\"//TODO: 请手动补全参数\"");
                                            }
                                        } else if (StringUtils.equals("org.apache.http.impl.client.CloseableHttpClient", DEFAULT_TYPE_TO_BOCOM.get(canonicalName))) {
                                            testBuilder.append("HttpClientBuilder.create().disableAutomaticRetries().build()");
                                        } else {
                                            testBuilder.append("null");
                                        }
                                    }
                                    if (this.testedMethod != null) {
                                        this.testedMethod.getResolveComponents().insertRender(new ResolveVarible(StringUtils.deCapitalizeFirstLetter(paramNode.getData().getName()), type, this.renderType.intValue()));
                                    }
                                } else if ("java.lang.Object".equals(canonicalName)) {
                                    if (caseParam != null && caseParam.getData() != null) {
                                        testBuilder.append(convertDefault(caseParam.getData(), canonicalName, paramNode));
                                    } else {
                                        testBuilder.append("null");
                                    }
                                    testBuilder.append(";");
                                } else {
                                    buildMethod = true;
                                }
                                if (buildMethod) {
                                    JSONObject jsonObject2 = null;
                                    boolean appendNull = false;
                                    if (caseParam != null && caseParam.getData() == null) {
                                        appendNull = true;
                                    }
                                    if (appendNull) {
                                        testBuilder.append("null").append(";");
                                    } else {
                                        testBuilder.append(resolveInitializerKeyword(type, foundCtor));
                                        if (resolvedType.getParentContainerClass() != null && !resolvedType.isStatic() && !resolvedType.isEnum()) {
                                            typeName = resolveNestedClassTypeName(typeName);
                                            LOG.debug(canonicalName + " has parentContainerClass:" + typeName);
                                        }
                                        LOG.debug(canonicalName + ":" + typeName);
                                        testBuilder.append(resolveGenericTypeName(typeName, foundCtor)).append("(");
                                        List<Param> methodParams = (hasEmptyConstructor || foundCtor == null) ? new ArrayList<>() : foundCtor.getMethodParams();
                                        if (caseParam != null) {
                                            if (caseParam.getData() instanceof JSONObject) {
                                                jsonObject2 = (JSONObject) caseParam.getData();
                                            } else if (caseParam.getData() != null && methodParams.size() == 1) {
                                                jsonObject2 = new JSONObject();
                                                jsonObject2.put(methodParams.get(0).getName(), caseParam.getData());
                                            }
                                        }
                                        if (!hasEmptyConstructor) {
                                            buildCallParams(foundCtor, methodParams, testBuilder, paramNode, jsonObject2, dept - 1);
                                        }
                                        testBuilder.append(")");
                                        if (type.isArray()) {
                                            testBuilder.append("{{");
                                        } else {
                                            testBuilder.append(";");
                                        }
                                        if (this.testedMethod != null) {
                                            if (!this.testedMethod.hasReturn() && type.isInterface()) {
                                                this.testedMethod.getResolveComponents().insertRender(new ResolveVarible(StringUtils.deCapitalizeFirstLetter(paramNode.getData().getName()), resolvedType, this.renderType.intValue()));
                                            } else {
                                                this.testedMethod.getResolveComponents().insertRender(new ResolveVarible(StringUtils.deCapitalizeFirstLetter(paramNode.getData().getName()), type, this.renderType.intValue()));
                                            }
                                        }
                                        if (!type.isFinal() && dept > 0 && methodParams.isEmpty()) {
                                            StringBuilder methodFiledSet = new StringBuilder();
                                            renderSetMethod(methodFiledSet, usedMethods, resolvedType, paramNode, jsonObject2, type.isArray() ? "" : paramNode.getData().getName(), dept);
                                            if (methodFiledSet.length() > 0) {
                                                testBuilder.append(methodFiledSet.toString());
                                            }
                                        }
                                        if (type.isArray()) {
                                            testBuilder.append("}}");
                                        }
                                    }
                                }
                            } else if ((this.testedMethod != null && this.testedMethod.getName().contains("getBean")) || this.testedMethod.getName().contains("getbean")) {
                                testBuilder.append("Object.class");
                            } else {
                                String genericTypeName2 = resolveGenericTypeName(typeName, null);
                                if (TypeUtils.isLanguageBaseClass(genericTypeName2)) {
                                    if (this.typesOverrides.get(genericTypeName2) != null) {
                                        testBuilder.append(this.typesOverrides.get(genericTypeName2));
                                    } else if (caseParam != null && caseParam.getData() == null) {
                                        testBuilder.append("null");
                                    } else {
                                        testBuilder.append("\"//TODO: 请手动补全参数\"");
                                    }
                                } else {
                                    testBuilder.append("null");
                                }
                            }
                        }
                    } else {
                        renderEnumValue(testBuilder, type);
                        if (!TypeUtils.isBasicType(type) && this.testedMethod != null) {
                            this.testedMethod.getResolveComponents().insertRender(new ResolveVarible(StringUtils.deCapitalizeFirstLetter(paramNode.getData().getName()), type, this.renderType.intValue()));
                        }
                    }
                } else if (caseParam != null && caseParam.getData() != null) {
                    if ((caseParam.getData() instanceof JSONObject) || (caseParam.getData() instanceof JSONArray)) {
                        testBuilder.append(String.format("\"%s\"", JSONUtil.toJsonStr(caseParam.getData()).replaceAll("\"", "\\\"").replaceAll("(?<!\\\\)\"", "\\\\\"")));
                    } else if (caseParam.getData() instanceof String) {
                        testBuilder.append(String.format("\"%s\"", caseParam.getData()));
                    } else {
                        testBuilder.append("null");
                    }
                } else {
                    testBuilder.append("\"").append(resolveName(paramNode)).append("\"");
                }
            } else {
                if (caseParam != null) {
                    testBuilder.append(convertDefault(caseParam.getData(), canonicalName, paramNode));
                } else {
                    testBuilder.append(convertCustomParamValue(canonicalName, resolveName(paramNode)));
                }
                if (!TypeUtils.isBasicType(type) && this.testedMethod != null) {
                    this.testedMethod.getResolveComponents().insertRender(new ResolveVarible(StringUtils.deCapitalizeFirstLetter(paramNode.getData().getName()), type, this.renderType.intValue()));
                }
            }
        } catch (Throwable e) {
            LOG.warn("[buildJavaVarible] fail : " + e.getMessage());
            LOG.warn("[buildJavaVarible] fail : " + e.getCause());
            LOG.warn("[buildJavaVarible] fail : ", e);
        }
    }

    protected void renderMapOrList(String canonicalName, StringBuilder testBuilder, Type resolvedType, Node<Param> paramNode, CaseParam caseParam, List<MethodCall> usedMethods, int dept) {
        Boolean valueOf;
        String typeName = resolveTypeName(resolvedType);
        String[] typeInitExp = typeName.split("<VAL>");
        if (typeInitExp.length == 0) {
            Type genericTypeParam = safeGetComposedTypeAtIndex(resolvedType, 0);
            buildCallParam(testBuilder, new Node<>(new SyntheticParam(genericTypeParam, genericTypeParam.getName(), SyntheticParam.UsageContext.Generic), paramNode, paramNode.getDepth()), null, dept - 1);
            return;
        }
        if (TypeUtils.isMap(canonicalName).booleanValue() && caseParam != null && caseParam.getData() != null && (caseParam.getData() instanceof JSONObject)) {
            String mapType = typeName.contains("(") ? typeName.substring(0, typeName.indexOf("(")) : typeName;
            if (mapType.contains("?")) {
                mapType = resolveGenericTypeName(mapType, null);
            }
            testBuilder.append(mapType + "(){{");
            JSONObject data = (JSONObject) caseParam.getData();
            int i = 0;
            for (Map.Entry<String, Object> stringObjectEntry : data.entrySet()) {
                testBuilder.append("put(");
                Type genericTypeParam2 = safeGetComposedTypeAtIndex(resolvedType, 0);
                buildCallParam(testBuilder, new Node<>(new SyntheticParam(genericTypeParam2, genericTypeParam2.getName(), SyntheticParam.UsageContext.Generic), paramNode, paramNode.getDepth()), new CaseParam(resolveTypeName(genericTypeParam2) + i, genericTypeParam2.getName(), genericTypeParam2.getCanonicalName(), stringObjectEntry.getKey()), dept);
                deleteLastSpec(testBuilder);
                testBuilder.append(",");
                Type valueGenericTypeParam = safeGetComposedTypeAtIndex(resolvedType, 1);
                String name = valueGenericTypeParam.getName() + (i == 0 ? "" : Integer.valueOf(i));
                buildCallParam(testBuilder, new Node<>(new SyntheticParam(valueGenericTypeParam, name, SyntheticParam.UsageContext.Generic), paramNode, paramNode.getDepth()), new CaseParam(resolveTypeName(valueGenericTypeParam) + i, valueGenericTypeParam.getName(), valueGenericTypeParam.getCanonicalName(), stringObjectEntry.getValue()), dept);
                deleteLastSpec(testBuilder);
                testBuilder.append(");");
                i++;
            }
            testBuilder.append("}}");
            return;
        }
        if (!TypeUtils.isMap(canonicalName).booleanValue()) {
            valueOf = Boolean.valueOf(StringUtils.startWith(canonicalName, PropertyUtils.H("o4\u001b\u000f:`;g&r\u0016\u000e0`8h$j>h")));
            if (valueOf.booleanValue()) {
                if (caseParam == null || !(caseParam.getData() instanceof JSONObject)) {
                    testBuilder.append("new com.alibaba.fastjson.JSONObject()");
                    return;
                }
                String value = caseParam.getData().toString();
                String replaceVal = StringUtils.replace(value, "\"", "\\\"");
                testBuilder.append(StringUtils.replace(typeName, "<VAL>", "\"" + replaceVal + "\""));
                return;
            }
            StringBuilder listBuilder = new StringBuilder();
            listBuilder.append(typeInitExp[0]);
            JSONArray array = (caseParam == null || !(caseParam.getData() instanceof JSONArray)) ? null : (JSONArray) caseParam.getData();
            for (int i2 = 1; i2 < typeInitExp.length; i2++) {
                Type genericTypeParam3 = safeGetComposedTypeAtIndex(resolvedType, i2 - 1);
                if (array == null) {
                    buildCallParam(listBuilder, new Node<>(new SyntheticParam(genericTypeParam3, genericTypeParam3.getName(), SyntheticParam.UsageContext.Generic), paramNode, paramNode.getDepth()), null, dept);
                    deleteLastSpec(listBuilder);
                } else if (array.isEmpty()) {
                    listBuilder.append("null");
                } else {
                    for (int j = 0; j < array.size(); j++) {
                        buildCallParam(listBuilder, new Node<>(new SyntheticParam(genericTypeParam3, genericTypeParam3.getName() + i2, SyntheticParam.UsageContext.Generic), paramNode, paramNode.getDepth()), new CaseParam(resolveTypeName(genericTypeParam3) + i2, genericTypeParam3.getName(), genericTypeParam3.getCanonicalName(), array.get(j)), dept);
                        deleteLastSpec(listBuilder);
                        if (j < array.size() - 1) {
                            listBuilder.append(",");
                        }
                    }
                }
                listBuilder.append(typeInitExp[i2]);
                if (Arrays.stream(SPEC_ARRAY_VALUE).anyMatch(str -> {
                    return str.equalsIgnoreCase(listBuilder.toString());
                })) {
                    testBuilder.append("new java.util.ArrayList<>()");
                } else {
                    testBuilder.append((CharSequence) listBuilder);
                }
                testBuilder.append(";");
            }
            return;
        }
        Type valueGenericTypeParam2 = safeGetComposedTypeAtIndex(resolvedType, 1);
        List<MethodCall> mapPutKeys = new ArrayList<>((Collection<? extends MethodCall>) usedMethods.stream().filter(mc -> {
            if (valueGenericTypeParam2 == null) {
                return !mc.getMethodCallArguments().isEmpty() && (mc.getMethod().getMethodId().startsWith("java.util.Map#get") || mc.getMethod().getMethodId().startsWith("java.util.Map.Entry#getKey"));
            }
            return valueGenericTypeParam2.typeEquals(mc.getMethod().getReturnType());
        }).distinct().collect(Collectors.toList()));
        String mapType2 = typeName.contains("(") ? typeName.substring(0, typeName.indexOf("(")) : typeName;
        if (mapType2.contains("?")) {
            mapType2 = resolveGenericTypeName(mapType2, null);
        }
        testBuilder.append(mapType2 + "()");
        if (!mapPutKeys.isEmpty()) {
            testBuilder.append("{{");
        }
        Set<String> keys = new HashSet<>();
        int i3 = 0;
        for (MethodCall mapPutKey : mapPutKeys) {
            String paramKey = guestMapKey(CollectionUtils.isEmpty(mapPutKey.getMethodCallArguments()) ? mapPutKey.getVariableName() : mapPutKey.getMethodCallArguments().get(0).getText());
            StringBuilder stringBuilder = new StringBuilder();
            try {
                try {
                    Type genericTypeParam4 = safeGetComposedTypeAtIndex(resolvedType, 0);
                    if (StringUtils.isBlank(paramKey)) {
                        paramKey = genericTypeParam4.getName();
                    }
                    if (keys.contains(paramKey)) {
                        keys.add(paramKey);
                    } else {
                        stringBuilder.append("put(");
                        buildCallParam(stringBuilder, new Node<>(new SyntheticParam(genericTypeParam4, paramKey, SyntheticParam.UsageContext.Generic), paramNode, paramNode.getDepth()), new CaseParam(paramKey, genericTypeParam4.getName(), genericTypeParam4.getCanonicalName(), DataUtils.isNumberType(genericTypeParam4) ? Integer.valueOf(i3) : paramKey), dept);
                        deleteLastSpec(stringBuilder);
                        stringBuilder.append(",");
                        valueGenericTypeParam2 = valueGenericTypeParam2 == null ? mapPutKey.getMethod().getReturnType() : valueGenericTypeParam2;
                        String name2 = valueGenericTypeParam2.getName() + (i3 == 0 ? "" : Integer.valueOf(i3));
                        buildCallParam(stringBuilder, new Node<>(new SyntheticParam(valueGenericTypeParam2, name2, SyntheticParam.UsageContext.Generic), paramNode, paramNode.getDepth()), new CaseParam(paramKey, genericTypeParam4.getName(), genericTypeParam4.getCanonicalName(), DataUtils.isNumberType(genericTypeParam4) ? Integer.valueOf(i3) : paramKey), dept);
                        deleteLastSpec(stringBuilder);
                        stringBuilder.append(");");
                        testBuilder.append(stringBuilder.toString());
                        i3++;
                        keys.add(paramKey);
                    }
                } catch (Exception e) {
                    LOG.warn("map key 设置失败", e);
                    keys.add(paramKey);
                }
            } catch (Throwable th) {
                keys.add(paramKey);
                throw th;
            }
        }
        if (mapPutKeys.isEmpty()) {
            return;
        }
        testBuilder.append("}}");
    }

    protected String guestMapKey(String key) {
        if (key.contains(".")) {
            if (key.contains("(")) {
                return guestMapKey(key.split("\\.")[1]);
            }
            return key.split("\\.")[0];
        }
        if (key.contains("(")) {
            Pattern pattern = Pattern.compile("\\((.*?)\\)");
            Matcher matcher = pattern.matcher(key);
            if (matcher.find()) {
                String contentInsideParentheses = matcher.group(1);
                if (contentInsideParentheses.isEmpty()) {
                    return guestMapKey(key.split("\\(")[0]);
                }
                return guestMapKey(contentInsideParentheses);
            }
        }
        return ClassNameUtils.extractTargetPropertyName(key, true, true);
    }

    protected boolean hasEnumValues(Type type) {
        return !type.getEnumValues().isEmpty();
    }

    protected void renderEnumValue(StringBuilder testBuilder, Type type) {
        String enumValue = type.getEnumValues().get(0);
        String canonicalName = type.getCanonicalName();
        testBuilder.append(canonicalName).append(".").append(enumValue);
    }

    protected void renderEnumValue(StringBuilder testBuilder, Type type, String defaultValue) {
        Optional<String> findEnum = type.getEnumValues().stream().filter(item -> {
            return item.equalsIgnoreCase(defaultValue);
        }).findFirst();
        String enumValue = type.getEnumValues().get(0);
        if (findEnum.isPresent()) {
            enumValue = findEnum.get();
        }
        String canonicalName = type.getCanonicalName();
        testBuilder.append(canonicalName).append(".").append(enumValue);
    }

    private String resolveName(Node<Param> paramNode) {
        if ((paramNode.getData() instanceof SyntheticParam) && ((SyntheticParam) paramNode.getData()).getUsageContext() == SyntheticParam.UsageContext.Generic && paramNode.getParent() != null) {
            return resolveName(paramNode.getParent());
        }
        return paramNode.getData().getName();
    }

    private Type safeGetComposedTypeAtIndex(Type resolvedType, int i) {
        Type genericTypeParam;
        if (resolvedType.getComposedTypes().size() > i) {
            genericTypeParam = resolvedType.getComposedTypes().get(i);
        } else {
            genericTypeParam = DEFAULT_STRING_TYPE;
        }
        return genericTypeParam;
    }

    @NotNull
    protected String resolveInitializerKeyword(Type type, Method foundCtor) {
        if (foundCtor != null && foundCtor.isStatic() && foundCtor.isPublic()) {
            String str = foundCtor.getOwnerClassCanonicalType() == null ? "" : foundCtor.getOwnerClassCanonicalType() + ".";
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            return str;
        }
        return "new ";
    }

    protected void buildCallParams(Method constructor, List<? extends Param> params, StringBuilder testBuilder, Node<Param> ownerParamNode, JSONObject paramData, int dept) {
        int origLength = testBuilder.length();
        if (params != null) {
            Type ownerType = ownerParamNode.getData() == null ? null : ownerParamNode.getData().getType();
            LOG.debug("fileTemplateConfig.isIgnoreUnusedProperties:" + this.fileTemplateConfig.isIgnoreUnusedProperties() + ":" + (ownerType == null ? "" : ownerType.getCanonicalName()));
            for (Param param : params) {
                Node<Param> paramNode = new Node<>(param, ownerParamNode, ownerParamNode.getDepth() + 1);
                if (param == null || param.getType() == null) {
                    testBuilder.append("null, ");
                } else {
                    if (this.fileTemplateConfig.isIgnoreUnusedProperties() && this.testedMethod != null) {
                        if (isPropertyParam(paramNode.getData()) && ownerType != null && !isPropertyUsed(this.testedMethod, paramNode.getData(), ownerType)) {
                            LOG.debug("property unused " + paramNode.getData());
                        } else {
                            boolean shouldOptimizeConstructorInitialization = (ownerType == null || constructor == null || !isShouldOptimizeConstructorInitialization(ownerType, constructor, params, ownerType.getCanonicalName())) ? false : true;
                            if (shouldOptimizeConstructorInitialization && !param.getType().isPrimitive() && isUnused(ownerType, this.testedMethod, deductAssignedToFields(constructor, param))) {
                                testBuilder.append("(" + ownerType.getCanonicalName() + ")null, ");
                                LOG.debug("unused param " + param);
                            }
                        }
                    }
                    CaseParam caseParam = null;
                    if (paramData != null) {
                        caseParam = new CaseParam(paramNode.getData().getName(), ownerType == null ? "" : ownerType.getName(), ownerType == null ? "" : ownerType.getCanonicalName(), paramData.get(param.getName()));
                    }
                    buildCallParam(testBuilder, paramNode, caseParam, dept - 1);
                    String string = testBuilder.toString();
                    if (!param.getType().isArray() && string.endsWith("null")) {
                        testBuilder.delete(testBuilder.length() - 4, testBuilder.length());
                        testBuilder.append("(").append(param.getType().getCanonicalName()).append(")").append("null");
                    }
                    deleteLastSpec(testBuilder);
                    testBuilder.append(", ");
                }
            }
            if (origLength < testBuilder.length()) {
                testBuilder.delete(testBuilder.length() - ", ".length(), testBuilder.length());
            }
        }
    }

    @Nullable
    protected Method findValidConstructor(Type type, boolean hasEmptyConstructor) {
        Method foundCtor = null;
        Iterator<Method> it = type.findConstructors().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Method method = it.next();
            LOG.debug(method.getMethodId() + ",isAccessible:" + method.isAccessible() + ",isInInterface:" + method.isInInterface() + ",isAbstract:" + method.isAbstract() + ",hasEmptyConstructor:" + hasEmptyConstructor);
            if (isValidConstructor(type, method, hasEmptyConstructor)) {
                foundCtor = method;
                break;
            }
        }
        return foundCtor;
    }

    public boolean isValidConstructor(Type type, Method constructor, boolean hasEmptyConstructor) {
        Type methodParamType;
        String replaceFirst;
        if (!constructor.isAccessible() || type.isInterface() || type.isAbstract()) {
            return false;
        }
        List<Param> methodParams = constructor.getMethodParams();
        for (Param methodParam : methodParams) {
            if (methodParam == null || (methodParamType = methodParam.getType()) == null) {
                return false;
            }
            if (methodParamType.equals(type) && hasEmptyConstructor) {
                return false;
            }
            String canonicalName = methodParamType.getCanonicalName();
            if (methodParamType.isInterface() || methodParamType.isAbstract()) {
                replaceFirst = canonicalName.replaceFirst(InlineChatStatusServiceKt.H("]uT"), "");
                if (resolveConcreteType(replaceFirst) == null && hasEmptyConstructor) {
                    return false;
                }
            }
        }
        return true;
    }

    String resolveTypeName(Type type) {
        String replaceFirst;
        String canonicalName = type.getCanonicalName();
        replaceFirst = canonicalName.replaceFirst(InlineChatStatusServiceKt.H("]uT"), "");
        String replacementType = resolveConcreteType(replaceFirst);
        if (replacementType == null) {
            return canonicalName;
        }
        new HashMap();
        if (canonicalName.length() > 1) {
            String[] arr = canonicalName.split(",");
            if (arr.length > 1) {
                for (String s : arr) {
                    if (s.trim().length() == 1) {
                        return replacementType.replace("<TYPES>", "");
                    }
                }
            }
            return replacementType.replace("<TYPES>", ClassNameUtils.extractGenerics(canonicalName));
        }
        return replacementType.replace("<TYPES>", "");
    }

    private String resolveGenericTypeName(String input, Method method) {
        if (method != null && method.isStatic() && method.isPublic()) {
            return method.getName();
        }
        if (StrUtil.isNotEmpty(input) && DEFAULT_TYPE_TO_BOCOM.containsKey(input)) {
            return DEFAULT_TYPE_TO_BOCOM.get(input);
        }
        return input;
    }

    private String resolveConcreteType(String canonicalTypeName) {
        if (this.typesOverrides != null && this.typesOverrides.get(canonicalTypeName) != null) {
            return this.typesOverrides.get(canonicalTypeName);
        }
        if (this.paramRole == TestBuilder.ParamRole.Output) {
            return resolveConcreteTypeForReturn(canonicalTypeName);
        }
        String input = resolveConcreteTypeForInput(canonicalTypeName);
        return input;
    }

    private String resolveConcreteTypeForInput(String canonicalTypeName) {
        Map<String, String> map;
        Map<String, String> map2;
        if (this.javaVersion != null && this.javaVersion.isAtLeast(9)) {
            map2 = TypeUtils.f728break;
            return map2.get(canonicalTypeName);
        }
        map = TypeUtils.f744byte;
        return map.get(canonicalTypeName);
    }

    private String resolveConcreteTypeForReturn(String canonicalTypeName) {
        Map<String, String> map;
        Map<String, String> map2;
        if (this.javaVersion != null && this.javaVersion.isAtLeast(9)) {
            map2 = TypeUtils.f742try;
            return map2.get(canonicalTypeName);
        }
        map = TypeUtils.f735new;
        return map.get(canonicalTypeName);
    }

    private void deleteLastSpec(StringBuilder stringBuilder) {
        if (stringBuilder.length() > 2 && stringBuilder.lastIndexOf(";") > 0) {
            if (stringBuilder.lastIndexOf(";") == stringBuilder.length() - 1) {
                stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
            } else if (stringBuilder.lastIndexOf("; ") == stringBuilder.length() - 2) {
                stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
            }
        }
    }

    boolean isPropertyParam(Param param) {
        return (param instanceof SyntheticParam) && ((SyntheticParam) param).getUsageContext() == SyntheticParam.UsageContext.Property;
    }

    private boolean isShouldOptimizeConstructorInitialization(Type ownerType, Method constructor, List<? extends Param> params, String ownerTypeCanonicalName) {
        boolean shouldOptimizeConstructorInitialization = false;
        if (this.testedMethod != null && params.size() > 0) {
            int nBeanUsages = 0;
            for (Param param : params) {
                if (!isUnused(ownerType, this.testedMethod, deductAssignedToFields(constructor, param))) {
                    nBeanUsages++;
                }
            }
            int nTypeReferences = countTypeReferences(ownerTypeCanonicalName, this.testedMethod);
            int nMethodCalls = 0;
            for (MethodCall methodCall : this.testedMethod.getMethodCalls()) {
                if (!methodCall.getMethod().isConstructor() && isSharedType(ownerType, methodCall.getMethod())) {
                    nMethodCalls++;
                }
            }
            shouldOptimizeConstructorInitialization = shouldOptimizeConstructorInitialization(nTypeReferences + nMethodCalls, nBeanUsages);
        }
        return shouldOptimizeConstructorInitialization;
    }

    boolean shouldOptimizeConstructorInitialization(int nTotalTypeUsages, int nBeanUsages) {
        return 0 < nBeanUsages && ((float) nTotalTypeUsages) * (((float) this.fileTemplateConfig.getMinPercentOfInteractionWithPropertiesToTriggerConstructorOptimization()) / 100.0f) <= ((float) nBeanUsages);
    }

    private boolean isUnused(Type ownerType, Method testedMethod, List<Field> fields) {
        if (fields.isEmpty()) {
            return false;
        }
        for (Field field : fields) {
            if (isPropertyUsed(testedMethod, new SyntheticParam(field.getType(), field.getName(), SyntheticParam.UsageContext.Property), ownerType)) {
                return false;
            }
        }
        return true;
    }

    private boolean isPropertyUsed(@NotNull Method testedMethod, Param propertyParam, Type ownerType) {
        if (testedMethod == null) {
            $$$reportNull$$$0(1);
        }
        String paramOwnerCanonicalName = ownerType.getCanonicalName();
        if (isReferencedInMethod(testedMethod, propertyParam, paramOwnerCanonicalName) || isPropertyUsedIndirectly(null, testedMethod, propertyParam, ownerType)) {
            return true;
        }
        for (MethodCall methodCall : testedMethod.getMethodCalls()) {
            if (isReferencedInMethod(methodCall.getMethod(), propertyParam, paramOwnerCanonicalName) || isPropertyUsedIndirectly(methodCall, methodCall.getMethod(), propertyParam, ownerType)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPropertyUsedIndirectly(MethodCall methodCall, @NotNull Method method, Param propertyParam, Type paramOwner) {
        if (method == null) {
            $$$reportNull$$$0(2);
        }
        String paramOwnerCanonicalName = paramOwner.getCanonicalName();
        if (methodCall != null && isSharedType(paramOwner, methodCall.getMethod()) && isConstructorArgumentUsed(propertyParam, paramOwnerCanonicalName, methodCall, methodCall.getMethod())) {
            return true;
        }
        for (MethodCall methodCallArg : method.getMethodCalls()) {
            Method methodCalled = methodCallArg.getMethod();
            if (isSharedType(paramOwner, methodCalled) && (isGetterUsed(propertyParam, methodCalled) || isSetterUsed(propertyParam, methodCalled))) {
                LOG.debug("getter or setter are used " + paramOwnerCanonicalName + " - " + propertyParam + " in methodCall " + methodCalled);
                return true;
            }
        }
        for (Method methodRef : method.getMethodReferences()) {
            if (isSharedType(paramOwner, methodRef) && (isGetterUsed(propertyParam, methodRef) || isSetterUsed(propertyParam, methodRef))) {
                LOG.debug("getter or setter are used in method ref " + paramOwnerCanonicalName + " - " + propertyParam + " in methodCall " + methodRef);
                return true;
            }
        }
        return false;
    }

    private boolean isSharedType(Type ownerType, Method methodCalled) {
        for (Method method : ownerType.getMethods()) {
            if (method.getMethodId().equals(methodCalled.getMethodId())) {
                return true;
            }
        }
        return false;
    }

    private boolean isConstructorArgumentUsed(Param propertyParam, String paramOwnerCanonicalName, MethodCall methodCall, Method method) {
        return method.isConstructor() && hasNonNullFieldMapping(methodCall, propertyParam, paramOwnerCanonicalName);
    }

    private boolean isSetterUsed(Param propertyParam, Method calledMethod) {
        return calledMethod.isSetter() && calledMethod.getMethodParams().size() == 1 && calledMethod.getMethodParams().get(0).getType().equals(propertyParam.getType()) && propertyParam.getName().equals(calledMethod.getPropertyName());
    }

    private boolean isGetterUsed(Param propertyParam, Method calledMethod) {
        return calledMethod.isGetter() && calledMethod.getReturnType().getCanonicalName().equals(propertyParam.getType().getCanonicalName()) && propertyParam.getName().equals(calledMethod.getPropertyName());
    }

    private boolean hasNonNullFieldMapping(MethodCall methodCall, Param propertyParam, String paramOwnerCanonicalName) {
        for (int i = 0; i < methodCall.getMethod().getMethodParams().size(); i++) {
            for (Field field : deductAssignedToFields(methodCall.getMethod(), methodCall.getMethod().getMethodParams().get(i))) {
                if (methodCall.getMethodCallArguments() != null && methodCall.getMethodCallArguments().size() > i && !"null".equals(methodCall.getMethodCallArguments().get(i).getText()) && field.getName().equals(propertyParam.getName()) && field.getType().equals(propertyParam.getType()) && field.getOwnerClassCanonicalName().equals(paramOwnerCanonicalName)) {
                    LOG.debug("param has non-null field mapping -" + paramOwnerCanonicalName + "#" + propertyParam + "   -  field - " + field);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isReferencedInMethod(@NotNull Method method, Param propertyParam, String paramOwnerTypeCanonicalName) {
        if (method == null) {
            $$$reportNull$$$0(3);
        }
        if (method.isConstructor()) {
            return false;
        }
        for (Reference internalReference : method.getInternalReferences()) {
            if (paramOwnerTypeCanonicalName.equals(internalReference.getOwnerType().getCanonicalName()) && propertyParam.getType().equals(internalReference.getReferenceType()) && propertyParam.getName().equals(internalReference.getReferenceName())) {
                LOG.debug("property referenced in method " + paramOwnerTypeCanonicalName + "#" + propertyParam + " - method - " + internalReference);
                return true;
            }
        }
        return false;
    }

    private List<Field> deductAssignedToFields(Method constructor, Param param) {
        ArrayList<Field> assignedToFields = param.getAssignedToFields();
        if (CollectionUtils.isEmpty(assignedToFields)) {
            return deductAffectedFields(constructor, param);
        }
        return assignedToFields;
    }

    private List<Field> deductAffectedFields(Method constructor, Param param) {
        List<Field> affectedFields = new ArrayList<>();
        for (Field field : constructor.getIndirectlyAffectedFields()) {
            if (field.getType().equals(param.getType())) {
                affectedFields.add(field);
            }
        }
        return affectedFields;
    }

    private int countTypeReferences(String ownerTypeCanonicalName, Method method) {
        int nTotalTypeReferences = 0;
        if (method != null) {
            for (Reference internalReference : method.getInternalReferences()) {
                if (ownerTypeCanonicalName.equals(internalReference.getOwnerType().getCanonicalName())) {
                    nTotalTypeReferences++;
                }
            }
        }
        return nTotalTypeReferences;
    }

    protected String resolveNestedClassTypeName(String typeName) {
        return ClassNameUtils.extractClassName(typeName);
    }

    private boolean shouldContinueRecursion(Node<Param> paramNode) {
        LOG.debug("recursionDepth:" + paramNode.getDepth() + ". maxRecursionDepth " + this.fileTemplateConfig.getMaxRecursionDepth());
        return paramNode.getDepth() <= this.fileTemplateConfig.getMaxRecursionDepth() && !paramNode.hasSameAncestor();
    }

    @Override // com.aicode.template.context.service.LangTestBuilder
    public String renderJavaMethodAssert(Method method, CaseResult caseResult, String template) {
        String assertStr;
        try {
            if ("J4".equals(template)) {
                AssertUtil.J5 = false;
                assertStr = "Assert";
            } else {
                AssertUtil.J5 = true;
                assertStr = "Assertions";
            }
            boolean hasReturn = method.hasReturn();
            if (hasReturn) {
                Type returnType = method.getReturnType();
                String assertResult = renderJavaCallParam(returnType, "expectedResult", caseResult);
                String assertMessage = caseResult.getMessage();
                boolean assertNull = false;
                String outputMessage = "";
                if ("null".equals(assertResult)) {
                    assertNull = true;
                } else {
                    CaseParam output = caseResult.getOutput();
                    if (output != null && output.getData() != null) {
                        outputMessage = JSONUtil.toJsonStr(output.getData());
                        if ("[]".equals(outputMessage) || "{}".equals(outputMessage)) {
                            assertNull = true;
                        }
                    }
                    if (!assertNull && StringUtils.isNotBlank(assertMessage) && !assertMessage.contains("不为null") && !assertMessage.contains("不为空")) {
                        String[] strArr = AssertUtil.NULL_KEY_WORDS;
                        int length = strArr.length;
                        int i = 0;
                        while (true) {
                            if (i >= length) {
                                break;
                            }
                            String nullKeyWord = strArr[i];
                            if (!assertMessage.contains(nullKeyWord)) {
                                i++;
                            } else {
                                assertNull = true;
                                break;
                            }
                        }
                    }
                }
                return AssertUtil.assertResult(returnType, assertStr, assertResult, assertMessage, assertNull, outputMessage);
            }
            return "";
        } catch (Exception e) {
            LOG.warn("断言生成异常", e);
            return "";
        }
    }
}
