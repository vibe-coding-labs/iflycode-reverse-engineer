package com.aicode.template.builder;

import com.aicode.template.context.domain.Field;
import com.aicode.template.context.domain.Method;
import com.aicode.template.context.domain.Param;
import com.aicode.template.context.domain.Type;
import java.util.List;
import java.util.Set;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/builder/MockBuilder.class */
public interface MockBuilder {
    boolean isMockable(Field field, Type type);

    boolean isMockableType(Type type, Type type2);

    String getImmockabiliyReason(String str, Field field);

    String buildArgsTypes(List<Param> list);

    String buildStaticTypeNames(Type type);

    String buildMockArgsMatchers(List<Param> list);

    Set<String> mockStaticClass(Method method);

    Boolean isMockStatic(Method method);

    String resolveExceptions(Method method);

    boolean isMockExpected(Field field);
}
