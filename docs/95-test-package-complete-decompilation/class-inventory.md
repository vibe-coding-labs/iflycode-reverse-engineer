## 1. Class Inventory (28 Classes)

### 1.1 Service Classes

| # | Class | Source File | Size | Role |
|---|-------|------------|------|------|
| 1 | `UnitTestService` | eb | 66,280 bytes | Core Java unit test generation orchestrator |
| 2 | `UnitTestService$d` | eb | - | TypeToken for `List&lt;String&gt;` (Gson deserialization) |
| 3 | `UnitTestService$e` | eb | - | ArrayList&lt;CaseBranch&gt; wrapper for single branch |
| 4 | `UnitTestService$h` | eb | - | CommandEnum/WebViewDataTypeEnum switch map |
| 5 | `BatchUnitTestService` | gc | - | Batch (multi-file) unit test generation |
| 6 | `BatchUnitTestService$g` | gc | - | TypeToken for `List&lt;BatchUnitTestDto&gt;` |
| 7 | `BatchUnitTestService$l` | gc | - | CommandEnum/WebViewDataTypeEnum switch map |
| 8 | `CppTestService` | qc | - | C++/Python test generation service |
| 9 | `UnitTestDialog` | hc | - | Settings dialog for test framework selection |

### 1.2 DTO Classes

| # | Class | Source File | Role |
|---|-------|------------|------|
| 10 | `UnitTestDto` | UnitTestDto.java | Top-level response DTO |
| 11 | `UnitTestDto$DataDTO` | UnitTestDto.java | Per-class test data |
| 12 | `UnitTestDto$DataDTO$FunctionDataDTO` | UnitTestDto.java | Per-method test data |
| 13 | `UnitTestDto$DataDTO$FunctionDataDTO$Data` | UnitTestDto.java | Agent response data (stream/code/cases) |
| 14 | `UnitTestDto$DataDTO$FunctionDataDTO$Data$Cases` | UnitTestDto.java | Individual test case (input/output/description) |
| 15 | `UnitTestDto$DataDTO$FunctionDataDTO$CodeList` | UnitTestDto.java | Template-based test case with code |
| 16 | `UnitTestDto$DataDTO$FunctionDataDTO$TemplateAttr` | UnitTestDto.java | Template attributes for code generation |
| 17 | `UnitTestAgentDto` | UnitTestAgentDto.java | Agent request DTO |
| 18 | `UnitTestAgentDto$method` | UnitTestAgentDto.java | Method info within agent request |
| 19 | `UnitTestMethodDto` | UnitTestMethodDto.java | PSI method + change tracking |
| 20 | `UnitTestPromptDto` | UnitTestPromptDto.java | Method/test content pair for prompt |
| 21 | `RequestCaseCodeDto` | RequestCaseCodeDto.java | Case code request wrapper |
| 22 | `RequestCaseCodeDto$ValueDTO` | RequestCaseCodeDto.java | Case code request value |
| 23 | `UnitTestCollectDto` | UnitTestCollectDto.java | Test collection statistics |
| 24 | `ChangeInfoDto` | ChangeInfoDto.java | Change line info |
| 25 | `CommitChangeDto` | CommitChangeDto.java | Per-method commit change stats |
| 26 | `FunctionDataDto` | FunctionDataDto.java | Function data for batch display |
| 27 | `MethodUnitTestData` | MethodUnitTestData.java | Method ID + test count pair |
| 28 | `BatchUnitTestDto` | BatchUnitTestDto.java | Batch task DTO (git repo info) |

---

## 2. UnitTestDto 4-Layer Nested Structure

### 2.1 Structure Diagram

```
UnitTestDto                          (top-level response)
  |-- tabName: String                (tab identifier)
  |-- type: String                   (request type)
  |-- language: String               (programming language)
  |-- level: String                  (test level)
  |-- id: String                     (request ID)
  |-- packagePath: String            (package path)
  |-- absolutePath: String           (absolute file path)
  |-- errMessage: String             (error message)
  |-- data: List&lt;DataDTO&gt;            (per-class test data)
        |
        +-- DataDTO                  (class-level test data)
        |     |-- className: String
        |     |-- operationTime: String
        |     |-- id: String
        |     |-- language: String
        |     |-- path: String       (source file path)
        |     |-- testClassAbsolutePath: String
        |     |-- testClasPath: String
        |     |-- testClassName: String
        |     |-- structure: String  (class structure JSON)
        |     |-- testFrame: String  (e.g., "JUnit4", "JUnit5")
        |     |-- mockFrame: String  (e.g., "Mockito", "PowerMock")
        |     |-- modifyTestFrame: boolean
        |     |-- testFrameAlert: boolean
        |     |-- testTemplate: String
        |     |-- reason: String
        |     |-- message: String
        |     |-- functionData: List&lt;FunctionDataDTO&gt;
        |           |
        |           +-- FunctionDataDTO  (method-level test data)
        |           |     |-- functionName: String
        |           |     |-- id: String
        |           |     |-- language: String
        |           |     |-- unitTest: String
        |           |     |-- unitMock: String
        |           |     |-- xmlCase: String
        |           |     |-- methodContent: String
        |           |     |-- testContent: String
        |           |     |-- codeContent: String
        |           |     |-- caseCode: String
        |           |     |-- code: String
        |           |     |-- reason: String
        |           |     |-- testTemplate: String
        |           |     |-- codeList: List&lt;CodeList&gt;
        |           |     |-- templateAttr: TemplateAttr (transient)
        |           |     |-- testClassAbsolutePath: String
        |           |     |-- testClasPath: String
        |           |     |-- testClassName: String
        |           |     |-- path: String
        |           |     |-- privateMethod: boolean
        |           |     |-- range: List&lt;RangeDTO&gt;
        |           |     |-- data: Data
        |           |           |
        |           |           +-- Data  (agent response data)
        |           |           |     |-- stream: boolean
        |           |           |     |-- code: String
        |           |           |     |-- structure: String
        |           |           |     |-- testFrame: String
        |           |           |     |-- mockFrame: String
        |           |           |     |-- modifyTestFrame: boolean
        |           |           |     |-- testFrameAlert: boolean
        |           |           |     |-- importStructures: List&lt;String&gt;
        |           |           |     |-- cases: List&lt;Cases&gt;
        |           |           |           |
        |           |           |           +-- Cases  (individual test case)
        |           |           |           |     |-- description: String
        |           |           |           |     |-- input: String
        |           |           |           |     |-- output: String
        |           |           |           |     +-- (inner class of Data)
        |           |           |           +-- (inner class of FunctionDataDTO)
        |           |           +-- (inner class of FunctionDataDTO)
        |           +-- (inner class of DataDTO)
        +-- (field of UnitTestDto)
```

### 2.2 Nesting Summary

| Level | Class | Contains | Purpose |
|-------|-------|----------|---------|
| 0 | UnitTestDto | List\<DataDTO\> | Top-level response envelope |
| 1 | DataDTO | List\<FunctionDataDTO\> | Per-class: test framework, path, structure |
| 2 | FunctionDataDTO | Data, List\<CodeList\>, TemplateAttr | Per-method: test code, cases, template |
| 3a | Data | List\<Cases\> | Agent response: stream code, cases |
| 3b | CodeList | Map\<CaseParam\>, List\<CaseBranch\> | Template case: code, branches, mocks |
| 3c | TemplateAttr | TreeMap, Map, Set | Template metadata: imports, fields |
| 4 | Cases | - | Individual test case: input/output/description |

### 2.3 CodeList Detail (Template-Based Cases)

```
CodeList
  |-- caseDescription: String
  |-- inputValue: String
  |-- outputValue: String
  |-- originCode: String
  |-- caseCode: String
  |-- caseMethodName: String
  |-- path: String
  |-- case_input: String
  |-- case_mock_all: String
  |-- result: String
  |-- type: String
  |-- caseBranches: String
  |-- exception: String
  |-- input: Map<String, CaseParam>       (from template package)
  |-- branches: List&lt;CaseBranch&gt;           (from template package)
  |-- dependencies: List&lt;ToMockMethod&gt;     (from template package)
  |-- asserts: Map<String, String>
```

### 2.4 TemplateAttr Detail

```
TemplateAttr
  |-- staticMethod: boolean
  |-- className: String
  |-- methodName: String
  |-- classPackage: String
  |-- prepareForTestImport: TreeMap<String, String>
  |-- fieldClass: Map<String, String>
  |-- methodImportClass: Set&lt;String&gt;
  |-- template: String
```

---

## 3. Service Classes: Complete Method Signatures

### 3.1 UnitTestService (74 methods)

**Source file**: `eb` (obfuscated)
**Extends**: `Object` (final class)
**Logger**: `Logger enum` (obfuscated field name)

#### Public API Methods

| Method | Signature | Purpose |
|--------|-----------|---------|
| `testCollectionGenerate` | `(Project, List&lt;MethodUnitTestData&gt;, String)` | Send test collection statistics to backend |
| `javaUnitTestAnalysis` | `(JsonObject, MessageDto, Project)` | Handle Java unit test analysis response from agent |
| `notice` | `(Project)` | Show error notification (module not found) |
| `handleJavaUnitTest` | `(Project, Editor)` | Entry point: generate unit tests for Java file |
| `handleJavaUnitTestByElement` | `(Project, Editor, PsiElement)` | Entry point: generate tests for specific element |
| `handleAction` | `(WebViewDataTypeEnum, String, Project)` | Dispatch WebView actions |
| `handleAgentAction` | `(CommandEnum, JsonObject, MessageDto, String, Project)` | Dispatch agent responses |
| `startMethodGenerate` | `(Project, MethodGeneratorConfig, String, String, String, PsiClass, List&lt;PsiMethod&gt;, boolean)` | Start template-based method generation |
| `requestMethodCase` | `(String, Project)` | Request method test cases from agent |
| `requestCaseCode` | `(String, Project)` | Request case code from agent |
| `sendUnitTestErrInfo` | `(Project, WebViewDataTypeEnum, String, String)` | Send error info to WebView |
| `sendUnitTestBankData` | `(Project, FunctionDataDTO)` | Send test bank data to WebView |
| `handleUnitTestBankData` | `(Project, MethodGeneratorConfig)` | Handle test bank data response |
| `getTestCase` | `(JsonObject, MessageDto)` | Get test case from agent response |
| `getTestCode` | `(JsonObject, MessageDto)` | Get test code from agent response |
| `testSave` | `(Project, String, MessageDto)` | Save test file |
| `openSaveTestFile` | `(Project, String)` | Open saved test file in editor |
| `saveUnitTestFile` | `(Project, DataDTO)` | Save unit test file to disk |
| `generateUnitTestFile` | `(Project, String)` | Generate unit test file |
| `mappingUnitTestFile` | `(Project, String)` | Map unit test file |
| `mergeFields` | `(PsiJavaFile, PsiJavaFile)` | Merge fields between source and test file |
| `copyCaseCode` | `(String)` | Copy case code to clipboard |
| `receiveFunction` | `(DataDTO)` | Receive function data from agent |
| `testAnalysisErr` | `(Project, ResponseDto)` | Handle test analysis error |
| `requestTestCaseErr` | `(ResponseDto, CommandEnum, MessageDto)` | Handle request test case error |
| `findCommonPrefix` | `(String, String)` | Find common path prefix |
| `getTestPath` | `(Project, String, String, String)` | Compute test file path |
| `getPsiMethodList` | `(PsiFile, int, int)` | Get PSI methods in line range |

#### Private Helper Methods (obfuscated names)

| Method | Signature | Purpose |
|--------|-----------|---------|
| `Ja` | `(Application, DataDTO, Project)` | Run read action with QA() |
| `QA` | `(DataDTO, Project)` | Process test analysis result (core logic) |
| `hc` | `(Project, FunctionDataDTO)` | Resend cached template messages |
| `CB` | `(PsiJavaFile, List)` | Collect test method IDs from file |
| `ob` | `(PsiJavaFile)` | Get all methods from Java file |
| `Ta` | `(PsiJavaFile, PsiField)` | Check if field exists in class |
| `fa` | `(String, PsiMethod, FunctionDataDTO, Method)` | Match method by name and function |
| `vb` | `(PsiStatement, PsiStatement)` | Compare statement text equality |
| `dB` | `(ToMockMethod)` | Check if mock method is valid |
| `sB` | `(String, PsiAnnotation)` | Check annotation by name |
| `tc` | `(PsiElement)` | Get line range for element |
| `DB` | `(Application, MessageDto, Project, UnitTestAgentDto)` | Process agent analysis response |
| `SB` | `(PsiJavaFile, PsiJavaFile)` | Merge static imports |
| `Ob` | `(PsiJavaFile, PsiJavaFile)` | Merge regular imports |
| `nb` | `(Project, MethodGeneratorConfig, MessageDto, String, String, PsiClass, List)` | Generate method with template |
| `RB` | `(String, Project, JsonArray)` | Handle batch test results |
| `Ia` | `(PsiMethod, Method)` | Check method match |
| `sa` | `(PsiMethod, CaseResult)` | Check case result match |
| `ec` | `(Project, MethodGeneratorConfig, DataDTO, String, String, PsiClass, List)` | Execute template generation |
| `FA` | `(PsiMethod, Method, FunctionDataDTO, CaseResult)` | Full method match check |
| `AB` | `(Application, Project, DataDTO, String, String)` | Apply test file changes |
| `RC` | `(Project, Editor, int, int, PsiFile)` | Handle range selection |
| `QC` | `(Project, JsonObject, MessageDto)` | Process test case response |
| `IC` | `(Project, UnitTestAgentDto, UnitTestDto, DataDTO)` | Build FunctionDataDTO list |
| `aa` | `(PsiMethod, CaseResult, StringBuffer)` | Build CodeList from case result |
| `EA` | `(Project, String)` | Execute after test generation |
| `iA` | `(PsiJavaFile, PsiJavaFile, Project, Set, JavaCodeStyleManager, String)` | Add missing imports |
| `jB` | `(String, DataDTO, Project, String, JavaCodeStyleManager, String)` | Write test file content |
| `Ib` | `(PsiJavaFile, PsiImportStatementBase)` | Check if import exists |
| `uc` | `(PsiMethod)` | Check if method is testable |
| `ZA` | `(Method, PsiMethod, ArrayList&lt;CodeList&gt;, StringBuffer)` | Add template case to list |
| `cb` | `(String, Project, JsonObject, MessageDto, JsonObject)` | Handle case code response |
| `gb` | `(Project, DataDTO, String, String)` | Generate test for class |
| `Vc` | `(String, PsiAnnotation)` | Check annotation value |
| `yc` | `(MessageDto, RequestResult)` | Validate request result |
| `eb` | `(ToMockMethod)` | Process mock method |
| `aA` | `(Project, FunctionDataDTO)` | Request function case |
| `Qc` | `(List&lt;Integer&gt;, List&lt;Integer&gt;)` | Compare line ranges |
| `w` | `(int, int)` | Create line range list |
| `EC` | `(String)` | Extract class name from path |
| `xb` | `(Project, PsiJavaFile, PsiJavaFile, Set, JavaCodeStyleManager, String, CodeStyleManager)` | Merge imports with formatting |
| `Yb` | `(Application, String, Project, JsonObject, MessageDto, JsonObject)` | Process case code in read action |
| `UA` | `(MessageDto, Project, UnitTestAgentDto)` | Send analysis to WebView |
| `enum` | `(int)` | Static switch helper |

### 3.2 BatchUnitTestService (9 methods)

**Source file**: `gc` (obfuscated)
**Logger**: `Logger enum` (obfuscated field name)

| Method | Signature | Purpose |
|--------|-----------|---------|
| `batchUnitTestCreate` | `(String, Project)` | Create batch unit test task |
| `batchUnitTestDelete` | `(String, Project)` | Delete batch unit test task |
| `batchUnitTestList` | `(Project)` | List batch unit test tasks |
| `batchUnitTestDownload` | `(String, Project)` | Download batch test results |
| `codeBatchUnitTestList` | `(JsonObject)` | Parse batch test list response |
| `batchUnitTestMessage` | `(boolean, String)` | Build batch test message for WebView |
| `batchUnitTestDownload` | `(JsonObject, MessageDto)` | Handle download response from agent |
| `handleAction` | `(WebViewDataTypeEnum, String, Project)` | Dispatch WebView actions |
| `handleAgentAction` | `(CommandEnum, JsonObject, MessageDto, String, Project)` | Dispatch agent responses |

### 3.3 CppTestService (5 methods)

**Source file**: `qc` (obfuscated)
**Logger**: `Logger enum` (obfuscated field name)

| Method | Signature | Purpose |
|--------|-----------|---------|
| `resolveCppTest` | `(Project, Editor, String, PsiElement)` | Entry point: start C++/Python test generation |
| `resolveFunctionCase` | `(Project, FunctionDataDTO)` | Request function test cases from agent |
| `cppTestAnalysis` | `(JsonObject, MessageDto, Project)` | Handle C++ test analysis response |
| `getTestCode` | `(Project, FunctionDataDTO)` | Request test code from agent |

---
