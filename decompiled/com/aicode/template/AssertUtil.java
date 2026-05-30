package com.aicode.template;

import cn.hutool.core.collection.CollUtil;
import com.aicode.template.context.domain.Field;
import com.aicode.template.context.domain.Type;
import com.aicode.util.StringUtils;
import com.aicode.util.TypeUtils;
import com.intellij.openapi.diagnostic.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/AssertUtil.class */
public class AssertUtil {
    public static final String J4_TEMPLATE = "J4";
    public static final String J4_ASSERT = "Assert";
    public static final String J5_ASSERTIONS = "Assertions";
    public static boolean J5;
    public static final String KONG_WORDS = "空";
    public static final String KONG_LIST_WORDS = "[]";
    public static final String[] NULL_KEY_WORDS = {"为null", "为空", "输入null", "返回null", "输入空", "输入为空", "返回空", "输入无效", "期望抛出"};
    public static final List<String> BASIC_TYPES = Arrays.asList("byte", "short", "int", "long", "float", "double", "char", "boolean", "java.lang.Byte", "java.lang.Short", "java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double", "java.lang.Character", "java.lang.Boolean", "java.lang.String");
    private static final Logger LOG = Logger.getInstance(AssertUtil.class.getName());

    public static String assertResult(Type returnType, String assertStr, String assertResult, String assertMessage, boolean assertNull, String outputMessage) {
        List<Type> composedTypes = returnType.getComposedTypes();
        List<Field> fields = returnType.getFields();
        if (StringUtils.isBlank(assertResult)) {
            LOG.info("期望返回值是空，不进行断言");
            return "";
        }
        boolean array = returnType.isArray();
        if (array) {
            int dimensions = returnType.getArrayDimensions();
            return arrayAssert(fields, dimensions, assertResult, assertMessage, assertStr, assertNull);
        }
        boolean basicType = isBasicType(returnType);
        if (basicType) {
            return basicAssert(assertResult, assertMessage, assertStr);
        }
        boolean collection = TypeUtils.isCollectionType(returnType);
        if (collection) {
            return collectionAssert(assertResult, assertMessage, composedTypes, assertStr, assertNull, outputMessage);
        }
        boolean isMap = TypeUtils.isMap(returnType.getCanonicalName()).booleanValue();
        if (isMap) {
            return mapAssert(assertResult, assertMessage, assertStr, assertNull);
        }
        boolean isJson = TypeUtils.isJSONObject(returnType.getCanonicalName()).booleanValue();
        if (isJson) {
            return jsonAssert(assertResult, assertMessage, assertStr, assertNull);
        }
        if (CollUtil.isNotEmpty(fields)) {
            return entityAssert(fields, assertResult, assertMessage, assertStr, assertNull);
        }
        LOG.info("未知的类型");
        return basicAssert(assertResult, assertMessage, assertStr);
    }

    private static String arrayAssert(List<Field> fields, int dimensions, String assertResult, String assertMessage, String assertStr, boolean assertNull) {
        StringBuilder arrayStr = new StringBuilder();
        if (StringUtils.isNotBlank(assertMessage)) {
            if (assertNull) {
                if (J5) {
                    if (assertMessage.contains("空")) {
                        arrayStr.append(assertStr).append(".assertArrayEquals(").append(assertResult).append(", result,\"").append(assertMessage).append("\");");
                    } else {
                        arrayStr.append(assertStr).append(".assertNull(result, \"").append(assertMessage).append("\");");
                    }
                } else if (assertMessage.contains("空")) {
                    arrayStr.append(assertStr).append(".assertArrayEquals(\"").append(assertMessage).append("\",").append(assertResult).append(", result);");
                } else {
                    arrayStr.append(assertStr).append(".assertNull(\"").append(assertMessage).append("\",result);");
                }
            } else if (CollUtil.isNotEmpty(fields) && dimensions == 1) {
                if (J5) {
                    arrayStr.append(assertStr).append(".assertNotNull(result, \"").append(assertMessage).append("\");");
                } else {
                    arrayStr.append(assertStr).append(".assertNotNull(\"").append(assertMessage).append("\",result);");
                }
                List<String> fieldNames = handFields(fields);
                arrayStr.append(handAssert(fieldNames, true, assertStr));
            } else if (J5) {
                arrayStr.append(assertStr).append(".assertArrayEquals(").append(assertResult).append(", result,\"").append(assertMessage).append("\");");
            } else {
                arrayStr.append(assertStr).append(".assertArrayEquals(\"").append(assertMessage).append("\",").append(assertResult).append(", result);");
            }
        } else if (CollUtil.isNotEmpty(fields) && dimensions == 1) {
            arrayStr.append(assertStr).append(".assertNotNull(result);");
        } else {
            arrayStr.append(assertStr).append(".assertArrayEquals(").append(assertResult).append(", result);");
        }
        return arrayStr.toString();
    }

    private static String basicAssert(String assertResult, String assertMessage, String assertStr) {
        StringBuilder baseStr = new StringBuilder();
        if (StringUtils.isNotBlank(assertMessage)) {
            if (J5) {
                baseStr.append(assertStr).append(".assertEquals(").append(assertResult).append(", result, \"").append(assertMessage).append("\");");
            } else {
                baseStr.append(assertStr).append(".assertEquals(\"").append(assertMessage).append("\",").append(assertResult).append(",result);");
            }
        } else {
            baseStr.append(assertStr).append(".assertEquals(").append(assertResult).append(", result);");
        }
        return baseStr.toString();
    }

    private static String mapAssert(String assertResult, String assertMessage, String assertStr, boolean assertNull) {
        StringBuilder mapStr = new StringBuilder();
        if (StringUtils.isNotBlank(assertMessage)) {
            if (assertNull) {
                if (J5) {
                    if (assertMessage.contains("空")) {
                        mapStr.append(assertStr).append(".assertEquals(").append(assertResult).append(".size(), result.size(),\"").append(assertMessage).append("\");");
                    } else {
                        mapStr.append(assertStr).append(".assertNull(result, \"").append(assertMessage).append("\");");
                    }
                } else if (assertMessage.contains("空")) {
                    mapStr.append(assertStr).append(".assertEquals(\"").append(assertMessage).append("\",").append(assertResult).append(".size(), result.size());");
                } else {
                    mapStr.append(assertStr).append(".assertNull(\"").append(assertMessage).append("\",result);");
                }
            } else if (J5) {
                mapStr.append(assertStr).append(".assertNotNull(result, \"").append(assertMessage).append("\");");
                mapStr.append(assertStr).append(".assertEquals(").append(assertResult).append(".size(), result.size());");
            } else {
                mapStr.append(assertStr).append(".assertNotNull(\"").append(assertMessage).append("\",result);");
                mapStr.append(assertStr).append(".assertEquals(").append(assertResult).append(".size(), result.size());");
            }
        } else {
            mapStr.append(assertStr).append(".assertNotNull(result);");
        }
        return mapStr.toString();
    }

    private static String jsonAssert(String assertResult, String assertMessage, String assertStr, boolean assertNull) {
        StringBuilder jsonStr = new StringBuilder();
        if (StringUtils.isNotBlank(assertMessage)) {
            if (assertNull) {
                if (J5) {
                    jsonStr.append(assertStr).append(".assertNull(result, \"").append(assertMessage).append("\");");
                } else {
                    jsonStr.append(assertStr).append(".assertNull(\"").append(assertMessage).append("\",result);");
                }
            } else if (J5) {
                jsonStr.append(assertStr).append(".assertNotNull(result, \"").append(assertMessage).append("\");");
                jsonStr.append(assertStr).append(".assertEquals(").append(assertResult).append(".size(), result.size());");
            } else {
                jsonStr.append(assertStr).append(".assertNotNull(\"").append(assertMessage).append("\",result);");
                jsonStr.append(assertStr).append(".assertEquals(").append(assertResult).append(".size(), result.size());");
            }
        } else {
            jsonStr.append(assertStr).append(".assertNotNull(result);");
        }
        return jsonStr.toString();
    }

    private static String collectionAssert(String assertResult, String assertMessage, List<Type> composedTypes, String assertStr, boolean assertNull, String outputMessage) {
        StringBuilder collectionStr = new StringBuilder();
        if (StringUtils.isNotBlank(assertMessage)) {
            if (assertNull) {
                if (J5) {
                    if (outputMessage.contains("[]")) {
                        collectionStr.append(assertStr).append(".assertEquals(").append("0, result.size(),\"").append(assertMessage).append("\");");
                    } else {
                        collectionStr.append(assertStr).append(".assertNull(result, \"").append(assertMessage).append("\");");
                    }
                } else if (outputMessage.contains("[]")) {
                    collectionStr.append(assertStr).append(".assertEquals(\"").append(assertMessage).append("\",").append("0, result.size());");
                } else {
                    collectionStr.append(assertStr).append(".assertNull(\"").append(assertMessage).append("\",result);");
                }
            } else if (J5) {
                collectionStr.append(assertStr).append(".assertNotNull(result, \"").append(assertMessage).append("\");");
            } else {
                collectionStr.append(assertStr).append(".assertNotNull(\"").append(assertMessage).append("\",result);");
            }
        } else {
            collectionStr.append(assertStr).append(".assertNotNull(result);");
        }
        return collectionStr.toString();
    }

    private static void handCollection(String assertResult, List<Type> composedTypes, String assertStr, StringBuilder collectionStr) {
        collectionStr.append(assertStr).append(".assertEquals(").append(assertResult).append(".size(), result.size());");
        if (CollUtil.isNotEmpty(composedTypes)) {
            Type composedType = composedTypes.get(0);
            List<Field> fields = composedType.getFields();
            boolean array = composedType.isArray();
            if (CollUtil.isNotEmpty(fields) && !array) {
                List<String> fieldNames = handFields(fields);
                collectionStr.append(handAssert(fieldNames, false, assertStr));
            }
        }
    }

    private static String entityAssert(List<Field> fields, String assertResult, String assertMessage, String assertStr, boolean assertNull) {
        StringBuilder entityStr = new StringBuilder();
        if (StringUtils.isNotBlank(assertMessage)) {
            if (assertNull) {
                if (J5) {
                    entityStr.append(assertStr).append(".assertNull(result, \"").append(assertMessage).append("\");");
                } else {
                    entityStr.append(assertStr).append(".assertNull(\"").append(assertMessage).append("\",result);");
                }
            } else if (J5) {
                entityStr.append(assertStr).append(".assertNotNull(result, \"").append(assertMessage).append("\");");
            } else {
                entityStr.append(assertStr).append(".assertNotNull(\"").append(assertMessage).append("\",result);");
            }
        } else {
            entityStr.append(assertStr).append(".assertNotNull(result);");
        }
        return entityStr.toString();
    }

    private static void handEntity(List<Field> fields, String assertResult, String assertStr, StringBuilder entityStr) {
        List<String> fieldNames = handFields(fields);
        if (!fieldNames.isEmpty()) {
            for (String fieldName : fieldNames) {
                entityStr.append("   ").append(assertStr).append(".assertEquals(").append(assertResult).append(".").append(fieldName).append(", result.").append(fieldName).append(");");
            }
        }
    }

    private static List<String> handFields(List<Field> fields) {
        List<String> fieldNames = new ArrayList<>();
        for (Field field : fields) {
            if (fieldNames.size() > 4) {
                break;
            }
            if (!"serialVersionUID".equals(field.getName()) && field.isGetProperty() && field.isSetProperty()) {
                Type type = field.getType();
                String name = field.getName();
                if (isBasicType(type) && !name.startsWith("is")) {
                    fieldNames.add("get" + getName(name) + "()");
                }
            }
        }
        return fieldNames;
    }

    public static String getName(String fieldName) {
        char[] chars = fieldName.toCharArray();
        if ('a' <= chars[0] && chars[0] <= 'z') {
            chars[0] = (char) (chars[0] ^ ' ');
        }
        return String.valueOf(chars);
    }

    private static String handAssert(List<String> fieldNames, boolean array, String assertStr) {
        StringBuilder entityStr = new StringBuilder();
        if (!fieldNames.isEmpty()) {
            if (array) {
                entityStr.append("for (int i = 0; i < result.length; i++) {");
            } else {
                entityStr.append("for (int i = 0; i < result.size(); i++) {");
            }
            for (String fieldName : fieldNames) {
                if (array) {
                    entityStr.append("   ").append(assertStr).append(".assertEquals(expectedResult[i].").append(fieldName).append(", result[i].").append(fieldName).append(");");
                } else {
                    entityStr.append("   ").append(assertStr).append(".assertEquals(expectedResult.get(i).").append(fieldName).append(", result.get(i).").append(fieldName).append(");");
                }
            }
            entityStr.append("}");
        }
        return entityStr.toString();
    }

    public static String assertParams(Type resolveType, String parmaName, String assertStr) {
        List<Field> fields = resolveType.getFields();
        boolean array = resolveType.isArray();
        if (array) {
            int dimensions = resolveType.getArrayDimensions();
            return arrayAssertParams(fields, parmaName, dimensions, assertStr);
        }
        boolean basicType = isBasicType(resolveType);
        if (basicType) {
            return "";
        }
        boolean collection = TypeUtils.isCollectionType(resolveType);
        if (collection) {
            return "";
        }
        boolean isMap = TypeUtils.isMap(resolveType.getCanonicalName()).booleanValue();
        if (isMap) {
            return "";
        }
        boolean isJson = TypeUtils.isJSONObject(resolveType.getCanonicalName()).booleanValue();
        if (!isJson && CollUtil.isNotEmpty(fields)) {
            return entityAssertParams(fields, parmaName, assertStr);
        }
        return "";
    }

    private static String arrayAssertParams(List<Field> fields, String parmaName, int dimensions, String assertStr) {
        StringBuilder arrayStr = new StringBuilder();
        arrayStr.append(assertStr).append(".assertNotNull(").append(parmaName).append(");");
        if (CollUtil.isNotEmpty(fields) && dimensions == 1) {
            List<String> fieldNames = handFields(fields);
            arrayStr.append(handAssertParams(fieldNames, parmaName, true, assertStr));
        }
        return arrayStr.toString();
    }

    private static String collectionAssertParams(String assertResult, List<Type> composedTypes, String assertStr) {
        StringBuilder collectionStr = new StringBuilder();
        collectionStr.append(assertStr).append(".assertEquals(").append(assertResult).append(".size(), 0);");
        if (CollUtil.isNotEmpty(composedTypes)) {
            Type composedType = composedTypes.get(0);
            List<Field> fields = composedType.getFields();
            boolean array = composedType.isArray();
            if (CollUtil.isNotEmpty(fields) && !array) {
                List<String> fieldNames = handFields(fields);
                collectionStr.append(handAssertParams(fieldNames, assertResult, false, assertStr));
            }
        }
        return collectionStr.toString();
    }

    private static String entityAssertParams(List<Field> fields, String parmaName, String assertStr) {
        StringBuilder entityStr = new StringBuilder();
        entityStr.append(assertStr).append(".assertNotNull(").append(parmaName).append(");");
        return entityStr.toString();
    }

    private static String handAssertParams(List<String> fieldNames, String parmaName, boolean array, String assertStr) {
        StringBuilder entityStr = new StringBuilder();
        if (!fieldNames.isEmpty()) {
            if (array) {
                entityStr.append("for (int i = 0; i < ").append(parmaName).append(".length; i++) {");
            } else {
                entityStr.append("for (int i = 0; i < ").append(parmaName).append(".size(); i++) {");
            }
            for (String fieldName : fieldNames) {
                if (array) {
                    entityStr.append("   ").append(assertStr).append(".assertNotNull(").append(parmaName).append("[i].").append(fieldName).append(");");
                } else {
                    entityStr.append("   ").append(assertStr).append(".assertNotNull(").append(parmaName).append(".get(i).").append(fieldName).append(");");
                }
            }
            entityStr.append("}");
        }
        return entityStr.toString();
    }

    private static boolean isBasicType(Type type) {
        return BASIC_TYPES.contains(type.getCanonicalName());
    }
}
