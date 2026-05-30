package com.aicode.test.dto;

import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.template.request.dto.CaseBranch;
import com.aicode.template.request.dto.CaseParam;
import com.aicode.template.request.dto.ToMockMethod;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/dto/UnitTestDto.class */
public class UnitTestDto {
    private String tabName;
    private String type;
    private String language;
    private String level;
    private String id;
    private String packagePath;
    private String absolutePath;
    private String errMessage;
    private List<DataDTO> data;

    public String getTabName() {
        return this.tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackagePath() {
        return this.packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getAbsolutePath() {
        return this.absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getErrMessage() {
        return this.errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public List<DataDTO> getData() {
        return this.data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/dto/UnitTestDto$DataDTO.class */
    public static class DataDTO {
        private String className;
        private String operationTime;
        private String id;
        private String language;
        private String path;
        private String testClassAbsolutePath;
        private String testClasPath;
        private String testClassName;
        private String structure;
        private String testFrame;
        private String mockFrame;
        private boolean modifyTestFrame;
        private boolean testFrameAlert;
        private List<FunctionDataDTO> functionData;
        private String testTemplate;
        private String reason;
        private String message;

        public String getClassName() {
            return this.className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getOperationTime() {
            return this.operationTime;
        }

        public void setOperationTime(String operationTime) {
            this.operationTime = operationTime;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLanguage() {
            return this.language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getPath() {
            return this.path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getTestClassAbsolutePath() {
            return this.testClassAbsolutePath;
        }

        public void setTestClassAbsolutePath(String testClassAbsolutePath) {
            this.testClassAbsolutePath = testClassAbsolutePath;
        }

        public String getTestClasPath() {
            return this.testClasPath;
        }

        public void setTestClasPath(String testClasPath) {
            this.testClasPath = testClasPath;
        }

        public String getTestClassName() {
            return this.testClassName;
        }

        public void setTestClassName(String testClassName) {
            this.testClassName = testClassName;
        }

        public String getStructure() {
            return this.structure;
        }

        public void setStructure(String structure) {
            this.structure = structure;
        }

        public String getTestFrame() {
            return this.testFrame;
        }

        public void setTestFrame(String testFrame) {
            this.testFrame = testFrame;
        }

        public String getMockFrame() {
            return this.mockFrame;
        }

        public void setMockFrame(String mockFrame) {
            this.mockFrame = mockFrame;
        }

        public boolean isModifyTestFrame() {
            return this.modifyTestFrame;
        }

        public void setModifyTestFrame(boolean modifyTestFrame) {
            this.modifyTestFrame = modifyTestFrame;
        }

        public boolean isTestFrameAlert() {
            return this.testFrameAlert;
        }

        public void setTestFrameAlert(boolean testFrameAlert) {
            this.testFrameAlert = testFrameAlert;
        }

        public List<FunctionDataDTO> getFunctionData() {
            return this.functionData;
        }

        public void setFunctionData(List<FunctionDataDTO> functionData) {
            this.functionData = functionData;
        }

        public String getTestTemplate() {
            return this.testTemplate;
        }

        public void setTestTemplate(String testTemplate) {
            this.testTemplate = testTemplate;
        }

        public String getReason() {
            return this.reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/dto/UnitTestDto$DataDTO$FunctionDataDTO.class */
        public static class FunctionDataDTO {
            private String functionName;
            private String id;
            private String language;
            private String unitTest;
            private String unitMock;
            private String xmlCase;
            private String methodContent;
            private String testContent;
            private String codeContent;
            private String caseCode;
            private String code;
            private String reason;
            private String testTemplate;
            private List<CodeList> codeList;
            private transient TemplateAttr templateAttr;
            private String testClassAbsolutePath;
            private String testClasPath;
            private String testClassName;
            private String path;
            private boolean privateMethod;
            List<CodeInfoDto.RangeDTO> range;
            private Data data;

            public String getFunctionName() {
                return this.functionName;
            }

            public void setFunctionName(String functionName) {
                this.functionName = functionName;
            }

            public String getId() {
                return this.id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUnitTest() {
                return this.unitTest;
            }

            public void setUnitTest(String unitTest) {
                this.unitTest = unitTest;
            }

            public String getUnitMock() {
                return this.unitMock;
            }

            public void setUnitMock(String unitMock) {
                this.unitMock = unitMock;
            }

            public String getXmlCase() {
                return this.xmlCase;
            }

            public void setXmlCase(String xmlCase) {
                this.xmlCase = xmlCase;
            }

            public String getMethodContent() {
                return this.methodContent;
            }

            public void setMethodContent(String methodContent) {
                this.methodContent = methodContent;
            }

            public String getTestContent() {
                return this.testContent;
            }

            public void setTestContent(String testContent) {
                this.testContent = testContent;
            }

            public String getCodeContent() {
                return this.codeContent;
            }

            public void setCodeContent(String codeContent) {
                this.codeContent = codeContent;
            }

            public List<CodeList> getCodeList() {
                return this.codeList;
            }

            public void setCodeList(List<CodeList> codeList) {
                this.codeList = codeList;
            }

            public String getTestTemplate() {
                return this.testTemplate;
            }

            public void setTestTemplate(String testTemplate) {
                this.testTemplate = testTemplate;
            }

            public TemplateAttr getTemplateAttr() {
                return this.templateAttr;
            }

            public void setTemplateAttr(TemplateAttr templateAttr) {
                this.templateAttr = templateAttr;
            }

            public boolean isPrivateMethod() {
                return this.privateMethod;
            }

            public void setPrivateMethod(boolean privateMethod) {
                this.privateMethod = privateMethod;
            }

            public String getTestClassAbsolutePath() {
                return this.testClassAbsolutePath;
            }

            public void setTestClassAbsolutePath(String testClassAbsolutePath) {
                this.testClassAbsolutePath = testClassAbsolutePath;
            }

            public String getTestClasPath() {
                return this.testClasPath;
            }

            public void setTestClasPath(String testClasPath) {
                this.testClasPath = testClasPath;
            }

            public String getTestClassName() {
                return this.testClassName;
            }

            public void setTestClassName(String testClassName) {
                this.testClassName = testClassName;
            }

            public String getPath() {
                return this.path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public List<CodeInfoDto.RangeDTO> getRange() {
                return this.range;
            }

            public void setRange(List<CodeInfoDto.RangeDTO> range) {
                this.range = range;
            }

            public String getCaseCode() {
                return this.caseCode;
            }

            public void setCaseCode(String caseCode) {
                this.caseCode = caseCode;
            }

            public String getCode() {
                return this.code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getLanguage() {
                return this.language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }

            public String getReason() {
                return this.reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public Data getData() {
                return this.data;
            }

            public void setData(Data data) {
                this.data = data;
            }

            /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/dto/UnitTestDto$DataDTO$FunctionDataDTO$CodeList.class */
            public static class CodeList {
                private String caseDescription;
                private String inputValue;
                private String outputValue;
                private String originCode;
                private String caseCode;
                private String caseMethodName;
                private String path;
                private String case_input;
                private String case_mock_all;
                private String result;
                private String type;
                private String caseBranches;
                private String exception;
                private Map<String, CaseParam> input;
                private List<CaseBranch> branches;
                private List<ToMockMethod> dependencies;
                private Map<String, String> asserts;

                public String getCaseDescription() {
                    return this.caseDescription;
                }

                public void setCaseDescription(String caseDescription) {
                    this.caseDescription = caseDescription;
                }

                public String getInputValue() {
                    return this.inputValue;
                }

                public void setInputValue(String inputValue) {
                    this.inputValue = inputValue;
                }

                public String getOutputValue() {
                    return this.outputValue;
                }

                public void setOutputValue(String outputValue) {
                    this.outputValue = outputValue;
                }

                public String getOriginCode() {
                    return this.originCode;
                }

                public void setOriginCode(String originCode) {
                    this.originCode = originCode;
                }

                public String getCaseCode() {
                    return this.caseCode;
                }

                public void setCaseCode(String caseCode) {
                    this.caseCode = caseCode;
                }

                public String getCaseMethodName() {
                    return this.caseMethodName;
                }

                public void setCaseMethodName(String caseMethodName) {
                    this.caseMethodName = caseMethodName;
                }

                public Map<String, CaseParam> getInput() {
                    return this.input;
                }

                public void setInput(Map<String, CaseParam> input) {
                    this.input = input;
                }

                public List<CaseBranch> getBranches() {
                    return this.branches;
                }

                public void setBranches(List<CaseBranch> branches) {
                    this.branches = branches;
                }

                public List<ToMockMethod> getDependencies() {
                    return this.dependencies;
                }

                public void setDependencies(List<ToMockMethod> dependencies) {
                    this.dependencies = dependencies;
                }

                public Map<String, String> getAsserts() {
                    return this.asserts;
                }

                public void setAsserts(Map<String, String> asserts) {
                    this.asserts = asserts;
                }

                public String getPath() {
                    return this.path;
                }

                public void setPath(String path) {
                    this.path = path;
                }

                public String getCase_input() {
                    return this.case_input;
                }

                public void setCase_input(String case_input) {
                    this.case_input = case_input;
                }

                public String getCase_mock_all() {
                    return this.case_mock_all;
                }

                public void setCase_mock_all(String case_mock_all) {
                    this.case_mock_all = case_mock_all;
                }

                public String getResult() {
                    return this.result;
                }

                public void setResult(String result) {
                    this.result = result;
                }

                public String getType() {
                    return this.type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getCaseBranches() {
                    return this.caseBranches;
                }

                public void setCaseBranches(String caseBranches) {
                    this.caseBranches = caseBranches;
                }

                public String getException() {
                    return this.exception;
                }

                public void setException(String exception) {
                    this.exception = exception;
                }
            }

            /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/dto/UnitTestDto$DataDTO$FunctionDataDTO$TemplateAttr.class */
            public static class TemplateAttr {
                private boolean staticMethod;
                private String className;
                private String methodName;
                private String classPackage;
                private TreeMap<String, String> prepareForTestImport;
                private Map<String, String> fieldClass;
                private Set<String> methodImportClass;
                private String template;

                public boolean isStaticMethod() {
                    return this.staticMethod;
                }

                public void setStaticMethod(boolean staticMethod) {
                    this.staticMethod = staticMethod;
                }

                public String getClassName() {
                    return this.className;
                }

                public void setClassName(String className) {
                    this.className = className;
                }

                public String getMethodName() {
                    return this.methodName;
                }

                public void setMethodName(String methodName) {
                    this.methodName = methodName;
                }

                public TreeMap<String, String> getPrepareForTestImport() {
                    return this.prepareForTestImport;
                }

                public void setPrepareForTestImport(TreeMap<String, String> prepareForTestImport) {
                    this.prepareForTestImport = prepareForTestImport;
                }

                public Map<String, String> getFieldClass() {
                    return this.fieldClass;
                }

                public void setFieldClass(Map<String, String> fieldClass) {
                    this.fieldClass = fieldClass;
                }

                public Set<String> getMethodImportClass() {
                    return this.methodImportClass;
                }

                public void setMethodImportClass(Set<String> methodImportClass) {
                    this.methodImportClass = methodImportClass;
                }

                public String getClassPackage() {
                    return this.classPackage;
                }

                public void setClassPackage(String classPackage) {
                    this.classPackage = classPackage;
                }

                public String getTemplate() {
                    return this.template;
                }

                public void setTemplate(String template) {
                    this.template = template;
                }
            }

            /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/dto/UnitTestDto$DataDTO$FunctionDataDTO$Data.class */
            public class Data {
                private boolean stream;
                private String code;
                private String structure;
                private String testFrame;
                private String mockFrame;
                private boolean modifyTestFrame;
                private boolean testFrameAlert;
                private List<String> importStructures;
                private List<Cases> cases;

                public Data() {
                }

                public boolean isStream() {
                    return this.stream;
                }

                public void setStream(boolean stream) {
                    this.stream = stream;
                }

                public String getCode() {
                    return this.code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getStructure() {
                    return this.structure;
                }

                public void setStructure(String structure) {
                    this.structure = structure;
                }

                public String getTestFrame() {
                    return this.testFrame;
                }

                public void setTestFrame(String testFrame) {
                    this.testFrame = testFrame;
                }

                public String getMockFrame() {
                    return this.mockFrame;
                }

                public boolean isModifyTestFrame() {
                    return this.modifyTestFrame;
                }

                public void setModifyTestFrame(boolean modifyTestFrame) {
                    this.modifyTestFrame = modifyTestFrame;
                }

                public boolean isTestFrameAlert() {
                    return this.testFrameAlert;
                }

                public void setTestFrameAlert(boolean testFrameAlert) {
                    this.testFrameAlert = testFrameAlert;
                }

                public void setMockFrame(String mockFrame) {
                    this.mockFrame = mockFrame;
                }

                public List<String> getImportStructures() {
                    return this.importStructures;
                }

                public void setImportStructures(List<String> importStructures) {
                    this.importStructures = importStructures;
                }

                public List<Cases> getCases() {
                    return this.cases;
                }

                public void setCases(List<Cases> cases) {
                    this.cases = cases;
                }

                /* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/test/dto/UnitTestDto$DataDTO$FunctionDataDTO$Data$Cases.class */
                public class Cases {
                    private String description;
                    private String input;
                    private String output;

                    public Cases() {
                    }

                    public String getDescription() {
                        return this.description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getInput() {
                        return this.input;
                    }

                    public void setInput(String input) {
                        this.input = input;
                    }

                    public String getOutput() {
                        return this.output;
                    }

                    public void setOutput(String output) {
                        this.output = output;
                    }
                }
            }
        }
    }
}
