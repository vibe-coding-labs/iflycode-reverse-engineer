package com.aicode.template.builder;

import com.aicode.template.TestSubjectInspector;
import com.aicode.template.context.domain.Field;
import com.aicode.template.context.domain.Method;
import com.aicode.template.context.domain.Type;
import org.jetbrains.annotations.Nullable;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/builder/PowerMockBuilder.class */
public class PowerMockBuilder extends MockitoMockBuilder {
    private final boolean renderInternalMethodCallStubs;

    public PowerMockBuilder(boolean isMockitoMockMakerInlineOn, boolean stubMockMethodCallsReturnValues, TestSubjectInspector testSubjectInspector, @Nullable String mockitoCoreVersion, boolean renderInternalMethodCallStubs) {
        super(isMockitoMockMakerInlineOn, stubMockMethodCallsReturnValues, testSubjectInspector, mockitoCoreVersion);
        this.renderInternalMethodCallStubs = renderInternalMethodCallStubs;
    }

    public boolean hasInternalMethodCall(Method method, Type testedClass) {
        return this.renderInternalMethodCallStubs && method.getMethodCalls().stream().anyMatch(methodCall -> {
            return testedClass.getMethods().stream().anyMatch(classMethod -> {
                return classMethod.getMethodId().equals(methodCall.getMethod().getMethodId());
            });
        });
    }

    @Override // com.aicode.template.builder.MockitoMockBuilder
    protected boolean isMockableByMockFramework(Field field) {
        return field == null || field.getType().getCanonicalName().length() != 1;
    }
}
