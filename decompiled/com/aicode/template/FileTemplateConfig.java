package com.aicode.template;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/template/FileTemplateConfig.class */
public class FileTemplateConfig {
    public static final int DEFAULT_MAX_RECURSION_DEPTH = 9;
    private int maxRecursionDepth;
    private boolean reformatCode;
    private boolean replaceFqn;
    private boolean optimizeImports;
    private boolean stubMockMethodCallsReturnValues;
    private boolean ignoreUnusedProperties;
    private boolean replaceInterfaceParamsWithConcreteTypes;
    private int maxNumOfConcreteCandidatesToReplaceInterfaceParam;
    private int minPercentOfExcessiveSettersToPreferMapCtor;
    private int minPercentOfInteractionWithPropertiesToTriggerConstructorOptimization;
    private boolean generateTestsForInternalMethods;
    private boolean renderInternalMethodCallStubs;
    private boolean throwSpecificExceptionTypes;

    public FileTemplateConfig(boolean generateTestsForInternalMethods, boolean renderInternalMethodCallStubs, boolean throwSpecificExceptionTypes) {
        this(9, true, true, true, generateTestsForInternalMethods, renderInternalMethodCallStubs, throwSpecificExceptionTypes, true, true, true, 4, 50, 66);
    }

    private FileTemplateConfig(int maxRecursionDepth, boolean reformatCode, boolean replaceFqn, boolean optimizeImports, boolean generateTestsForInternalMethods, boolean renderInternalMethodCallStubs, boolean throwSpecificExceptionTypes, boolean ignoreUnusedProperties, boolean replaceInterfaceParamsWithConcreteTypes, boolean stubMockMethodCallsReturnValues, int maxNumOfConcreteCandidatesToReplaceInterfaceParam, int minPercentOfExcessiveSettersToPreferMapCtor, int minPercentOfInteractionWithPropertiesToTriggerConstructorOptimization) {
        this.maxRecursionDepth = 9;
        this.reformatCode = true;
        this.replaceFqn = true;
        this.optimizeImports = true;
        this.stubMockMethodCallsReturnValues = true;
        this.ignoreUnusedProperties = true;
        this.replaceInterfaceParamsWithConcreteTypes = true;
        this.maxNumOfConcreteCandidatesToReplaceInterfaceParam = 3;
        this.minPercentOfExcessiveSettersToPreferMapCtor = 50;
        this.minPercentOfInteractionWithPropertiesToTriggerConstructorOptimization = 66;
        this.generateTestsForInternalMethods = false;
        this.renderInternalMethodCallStubs = false;
        this.throwSpecificExceptionTypes = true;
        this.maxRecursionDepth = maxRecursionDepth;
        this.reformatCode = reformatCode;
        this.replaceFqn = replaceFqn;
        this.optimizeImports = optimizeImports;
        this.generateTestsForInternalMethods = generateTestsForInternalMethods;
        this.renderInternalMethodCallStubs = renderInternalMethodCallStubs;
        this.throwSpecificExceptionTypes = throwSpecificExceptionTypes;
        this.stubMockMethodCallsReturnValues = stubMockMethodCallsReturnValues;
        this.ignoreUnusedProperties = ignoreUnusedProperties;
        this.replaceInterfaceParamsWithConcreteTypes = replaceInterfaceParamsWithConcreteTypes;
        this.maxNumOfConcreteCandidatesToReplaceInterfaceParam = maxNumOfConcreteCandidatesToReplaceInterfaceParam;
        this.minPercentOfExcessiveSettersToPreferMapCtor = minPercentOfExcessiveSettersToPreferMapCtor;
        this.minPercentOfInteractionWithPropertiesToTriggerConstructorOptimization = minPercentOfInteractionWithPropertiesToTriggerConstructorOptimization;
    }

    public boolean isReformatCode() {
        return this.reformatCode;
    }

    public boolean isOptimizeImports() {
        return this.optimizeImports;
    }

    public int getMaxRecursionDepth() {
        return this.maxRecursionDepth;
    }

    public boolean isReplaceFqn() {
        return this.replaceFqn;
    }

    public boolean isIgnoreUnusedProperties() {
        return this.ignoreUnusedProperties;
    }

    public boolean isReplaceInterfaceParamsWithConcreteTypes() {
        return this.replaceInterfaceParamsWithConcreteTypes;
    }

    public int getMinPercentOfExcessiveSettersToPreferMapCtor() {
        return this.minPercentOfExcessiveSettersToPreferMapCtor;
    }

    public void setMaxRecursionDepth(int maxRecursionDepth) {
        this.maxRecursionDepth = maxRecursionDepth;
    }

    public void setReplaceInterfaceParamsWithConcreteTypes(boolean replaceInterfaceParamsWithConcreteTypes) {
        this.replaceInterfaceParamsWithConcreteTypes = replaceInterfaceParamsWithConcreteTypes;
    }

    public void setMinPercentOfExcessiveSettersToPreferMapCtor(int minPercentOfExcessiveSettersToPreferMapCtor) {
        this.minPercentOfExcessiveSettersToPreferMapCtor = minPercentOfExcessiveSettersToPreferMapCtor;
    }

    public int getMinPercentOfInteractionWithPropertiesToTriggerConstructorOptimization() {
        return this.minPercentOfInteractionWithPropertiesToTriggerConstructorOptimization;
    }

    public void setMinPercentOfInteractionWithPropertiesToTriggerConstructorOptimization(int minPercentOfInteractionWithPropertiesToTriggerConstructorOptimization) {
        this.minPercentOfInteractionWithPropertiesToTriggerConstructorOptimization = minPercentOfInteractionWithPropertiesToTriggerConstructorOptimization;
    }

    public boolean isStubMockMethodCallsReturnValues() {
        return this.stubMockMethodCallsReturnValues;
    }

    public int getMaxNumOfConcreteCandidatesToReplaceInterfaceParam() {
        return this.maxNumOfConcreteCandidatesToReplaceInterfaceParam;
    }

    public void setStubMockMethodCallsReturnValues(boolean stubMockMethodCallsReturnValues) {
        this.stubMockMethodCallsReturnValues = stubMockMethodCallsReturnValues;
    }

    public void setIgnoreUnusedProperties(boolean ignoreUnusedProperties) {
        this.ignoreUnusedProperties = ignoreUnusedProperties;
    }

    public void setMaxNumOfConcreteCandidatesToReplaceInterfaceParam(int maxNumOfConcreteCandidatesToReplaceInterfaceParam) {
        this.maxNumOfConcreteCandidatesToReplaceInterfaceParam = maxNumOfConcreteCandidatesToReplaceInterfaceParam;
    }

    public boolean isGenerateTestsForInternalMethods() {
        return this.generateTestsForInternalMethods;
    }

    public boolean isRenderInternalMethodCallStubs() {
        return this.renderInternalMethodCallStubs;
    }

    public void setRenderInternalMethodCallStubs(boolean renderInternalMethodCallStubs) {
        this.renderInternalMethodCallStubs = renderInternalMethodCallStubs;
    }

    public boolean isThrowSpecificExceptionTypes() {
        return this.throwSpecificExceptionTypes;
    }
}
