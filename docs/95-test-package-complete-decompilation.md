# Doc 95: test Package Complete Decompilation & Analysis

## Overview

The `com.aicode.test` package implements the **unit test generation system** for iFlyCode. It contains 28 classes organized into 3 layers:

- **Service Layer** (4 classes): UnitTestService, BatchUnitTestService, CppTestService + inner classes
- **DTO Layer** (19 classes): Nested data transfer objects for test generation requests/responses
- **UI Layer** (1 class): UnitTestDialog for user configuration

**Total bytecode size**: ~120KB (UnitTestService alone is 66KB)

---

## 1. Class Inventory (28 Classes)

### 1.1 Service Classes

| # | Class | Source File | Size | Role |
|---|-------|------------|------|------|
| 1 | `UnitTestService` | eb | 66,280 bytes | Core Java unit test generation orchestrator |
| 2 | `UnitTestService$d` | eb | - | TypeToken for `List<String>` (Gson deserialization) |
| 3 | `UnitTestService$e` | eb | - | ArrayList<CaseBranch> wrapper for single branch |
| 4 | `UnitTestService$h` | eb | - | CommandEnum/WebViewDataTypeEnum switch map |
| 5 | `BatchUnitTestService` | gc | - | Batch (multi-file) unit test generation |
| 6 | `BatchUnitTestService$g` | gc | - | TypeToken for `List<BatchUnitTestDto>` |
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
  |-- data: List<DataDTO>            (per-class test data)
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
        |     |-- functionData: List<FunctionDataDTO>
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
        |           |     |-- codeList: List<CodeList>
        |           |     |-- templateAttr: TemplateAttr (transient)
        |           |     |-- testClassAbsolutePath: String
        |           |     |-- testClasPath: String
        |           |     |-- testClassName: String
        |           |     |-- path: String
        |           |     |-- privateMethod: boolean
        |           |     |-- range: List<RangeDTO>
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
        |           |           |     |-- importStructures: List<String>
        |           |           |     |-- cases: List<Cases>
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
  |-- branches: List<CaseBranch>           (from template package)
  |-- dependencies: List<ToMockMethod>     (from template package)
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
  |-- methodImportClass: Set<String>
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
| `testCollectionGenerate` | `(Project, List<MethodUnitTestData>, String)` | Send test collection statistics to backend |
| `javaUnitTestAnalysis` | `(JsonObject, MessageDto, Project)` | Handle Java unit test analysis response from agent |
| `notice` | `(Project)` | Show error notification (module not found) |
| `handleJavaUnitTest` | `(Project, Editor)` | Entry point: generate unit tests for Java file |
| `handleJavaUnitTestByElement` | `(Project, Editor, PsiElement)` | Entry point: generate tests for specific element |
| `handleAction` | `(WebViewDataTypeEnum, String, Project)` | Dispatch WebView actions |
| `handleAgentAction` | `(CommandEnum, JsonObject, MessageDto, String, Project)` | Dispatch agent responses |
| `startMethodGenerate` | `(Project, MethodGeneratorConfig, String, String, String, PsiClass, List<PsiMethod>, boolean)` | Start template-based method generation |
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
| `ZA` | `(Method, PsiMethod, ArrayList<CodeList>, StringBuffer)` | Add template case to list |
| `cb` | `(String, Project, JsonObject, MessageDto, JsonObject)` | Handle case code response |
| `gb` | `(Project, DataDTO, String, String)` | Generate test for class |
| `Vc` | `(String, PsiAnnotation)` | Check annotation value |
| `yc` | `(MessageDto, RequestResult)` | Validate request result |
| `eb` | `(ToMockMethod)` | Process mock method |
| `aA` | `(Project, FunctionDataDTO)` | Request function case |
| `Qc` | `(List<Integer>, List<Integer>)` | Compare line ranges |
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

## 4. Unit Test Generation Complete Flow

### 4.1 Java Unit Test Generation Flow

```
User Action (Right-click / Shortcut)
    |
    v
handleJavaUnitTest(Project, Editor)     OR     handleJavaUnitTestByElement(Project, Editor, PsiElement)
    |
    v
RC() -- Get selection range from editor
    |
    v
Create MessageDto with CommandEnum.CODE_TEST_ANALYSIS
    |-- Set path, lang, range
    |-- Set pid (process ID for tracking)
    |-- Set data (JsonObject with testFrame, mockFrame, structure)
    |
    v
PluginWebsocketClient.sendWsMessage()  -->  Backend Agent
    |
    v
[Backend processes: analyzes code, generates test cases]
    |
    v
handleAgentAction() dispatches by CommandEnum:
    |
    +-- CODE_TEST_CASE --> getTestCase()
    |       Parse UnitTestAgentDto from response
    |       Build UnitTestDto with DataDTO/FunctionDataDTO
    |       Call receiveFunction() --> send to WebView
    |
    +-- CODE_TEST_ANALYSIS --> javaUnitTestAnalysis()
    |       Parse UnitTestAgentDto from response
    |       Call DB() on EDT
    |       Build DataDTO with FunctionDataDTO list
    |       Call QA() to process results
    |
    +-- CODE_TEST_CODE --> getTestCode()
    |       Parse case code from response
    |       Update FunctionDataDTO with test content
    |       Send to WebView
    |
    +-- CODE_TEST_SAVE --> testSave()
            Save test file to disk
            Open in editor
```

### 4.2 QA() Core Processing Logic (Private Method)

```
QA(DataDTO, Project)
    |
    v
1. Find VirtualFile by path
2. Get PsiFile, Module, content root
3. Create MethodGeneratorConfig
    |-- Set testFramework from DataDTO.testFrame
    |-- Set mockFramework from DataDTO.mockFrame
    |-- If modifyTestFrame: update AICodeSettingsState
4. Set PsiFile on config
5. Compute test path via BatchUnitTestTemplateService.getTestPath()
6. Generate UUID for DataDTO.id
7. Set operationTime (formatted timestamp)
8. For each FunctionDataDTO in DataDTO.functionData:
    |-- Get range (start line, end line)
    |-- Convert to document offsets
    |-- Find PsiMethod list in range
    |-- For each PsiMethod:
        |   |-- Find matching template Method
        |   |-- If template match found:
        |   |       Build CodeList from CaseResult
        |   |       Set templateAttr on FunctionDataDTO
        |   |-- Else:
        |   |       Use agent-based generation
        |   |-- Set testClassAbsolutePath, testClasPath, testClassName
    |-- Add to processed list
9. Set DataDTO.functionData = processed list
10. Send result to WebView via SocketMessageHandleListener.send2Web()
```

### 4.3 Template-Based Generation Flow (startMethodGenerate)

```
startMethodGenerate(Project, MethodGeneratorConfig, String, String, String, PsiClass, List<PsiMethod>, boolean)
    |
    v
1. For each PsiMethod in method list:
    |-- Check if method is testable (uc())
    |-- Find matching template Method
    |-- If match found:
    |       |-- Call CreateTestMethodTask.execute()
    |       |-- This populates cacheFileTemplateMap
    |       |-- Call hc() to resend cached messages
    |-- Else:
    |       |-- Send CODE_TEST_CASE request to agent
    |       |-- Agent generates cases via LLM
2. After cases received:
    |-- For each CodeList in FunctionDataDTO:
    |       |-- Generate test method code from template
    |       |-- Set caseCode, caseMethodName
3. Save test file via saveUnitTestFile()
```

### 4.4 C++/Python Test Generation Flow

```
resolveCppTest(Project, Editor, String, PsiElement)
    |
    v
1. Get selection range (or PsiElement range)
2. Get PsiFile from document
3. Get VirtualFile path, find Module
4. Build RangeDTO (start/end line + character)
5. Create MessageDto with CommandEnum.CODE_TEST_ANALYSIS
    |-- Set path, lang, range
    |-- Set pid (tracking ID)
    |-- Set data (JsonObject with testFrame, mockFrame from settings)
6. Store MessageDto in Project userData (UNIT_TEST_MESSAGE_DATA key)
7. Open UNIT_TEST page via CommonService.openPage()
8. Send via PluginWebsocketClient.sendWsMessage()

[Backend processes]

cppTestAnalysis(JsonObject, MessageDto, Project)
    |
    v
1. Parse UnitTestAgentDto from response
2. Create DataDTO:
    |-- Set language, path, className, structure from agent DTO
    |-- Handle Python testFrame modification (pyModifyTestFrame)
    |-- Set testFrame, mockFrame
3. For each method in UnitTestAgentDto.methods:
    |-- Create FunctionDataDTO
    |-- Set id (UUID), functionName, code, range
4. Call UnitTestService.receiveFunction() --> send to WebView

resolveFunctionCase(Project, FunctionDataDTO)
    |
    v
1. Create MessageDto with CommandEnum.TEST_MAKE_CASE
    |-- Set id, path, lang, text (StringBuffer)
2. Build JsonObject data:
    |-- stream (boolean), version (6), code
    |-- testFrame, mockFrame, structure
3. Send via PluginWebsocketClient.sendWsMessage()

getTestCode(Project, FunctionDataDTO)
    |
    v
1. Create MessageDto with CommandEnum.TEST_MAKE_CODE
    |-- Set id (UUID), pid, path, lang, text
    |-- Set data (FunctionDataDTO.data)
2. Send via PluginWebsocketClient.sendWsMessage()
```

### 4.5 Batch Unit Test Generation Flow

```
batchUnitTestCreate(String, Project)
    |
    v
1. Parse JsonObject from input string
2. Extract BatchUnitTestDto from "data" key (H-obfuscated)
3. Create MessageDto with CommandEnum.CODE_BATCH_UNIT_TEST_CREATE
    |-- Set data = BatchUnitTestDto
4. Send via PluginWebsocketClient.sendWsMessage()

[Backend processes entire git repository]

handleAgentAction() dispatches:
    |
    +-- CODE_BATCH_UNIT_TEST_CREATE:
    |       Remove from AGENT_REQUEST map
    |       Build success message via batchUnitTestMessage(true, null)
    |       Send to WebView
    |
    +-- CODE_BATCH_UNIT_TEST_LIST:
    |       Call codeBatchUnitTestList() to parse response
    |       Send to WebView
    |
    +-- CODE_BATCH_UNIT_TEST_DOWNLOAD:
    |       Call batchUnitTestDownload() to handle download
    |       If status == "H" (complete):
    |           Open directory in file manager
    |       Else:
    |           Show error message
    |
    +-- CODE_BATCH_UNIT_TEST_CANCEL / CODE_BATCH_UNIT_TEST_DELETE:
            Build failure message via batchUnitTestMessage(false, errorMsg)
            Send to WebView
```

---

## 5. Action Dispatch Tables

### 5.1 UnitTestService$h - CommandEnum Switch Map

Maps `CommandEnum` ordinals to switch case indices:

| Case | CommandEnum |
|------|-------------|
| 1 | CODE_TEST_CASE |
| 2 | CODE_TEST_ANALYSIS |
| 3 | CODE_TEST_CODE |
| 4 | CODE_TEST_SAVE |
| 5 | TEST_MAKE_CASE |
| 6 | TEST_MAKE_CODE |
| 7 | CODE_TEST_MAKE_CASE_JAVA |

### 5.2 UnitTestService$h - WebViewDataTypeEnum Switch Map

| Case | WebViewDataTypeEnum |
|------|---------------------|
| 1 | UNIT_TEST_REQUEST_UT_INFO |
| 2 | UNIT_TEST_REQUEST_METHOD_CASE |
| 3 | UNIT_TEST_REQUEST_CASE_CODE |
| 4 | UNIT_TEST_REQUEST_ALL_CODE_FILE |
| 5 | UNIT_TEST_COPY_CASE_CODE |
| 6 | UNIT_TEST_PAGE_READY |
| 7 | UNIT_TEST_SAVE_CODE |
| 8 | UNIT_TESTING_MAPPING_FILE |
| 9 | UNIT_TEST_FUNCTION_CASE |
| 10 | UNIT_TEST_FUNCTION_CASE_CODE |
| 11 | UNIT_TEST_REGENERATE |

### 5.3 BatchUnitTestService$l - CommandEnum Switch Map

| Case | CommandEnum |
|------|-------------|
| 1 | CODE_BATCH_UNIT_TEST_CREATE |
| 2 | CODE_BATCH_UNIT_TEST_LIST |
| 3 | CODE_BATCH_UNIT_TEST_DOWNLOAD |
| 4 | CODE_BATCH_UNIT_TEST_CANCEL |
| 5 | CODE_BATCH_UNIT_TEST_DELETE |

### 5.4 BatchUnitTestService$l - WebViewDataTypeEnum Switch Map

| Case | WebViewDataTypeEnum |
|------|---------------------|
| 1 | BATCH_UNIT_TEST_CREATE |
| 2 | BATCH_UNIT_TEST_GET_LIST |
| 3 | BATCH_UNIT_TEST_DOWNLOAD |
| 4 | BATCH_UNIT_TEST_DELETE |

### 5.5 BatchUnitTestService.handleAction Dispatch

```
WebViewDataTypeEnum ordinal --> switch:
  1: BATCH_UNIT_TEST_CREATE     --> batchUnitTestCreate(data, project)
  2: BATCH_UNIT_TEST_GET_LIST   --> batchUnitTestList(project)
  3: BATCH_UNIT_TEST_DOWNLOAD   --> batchUnitTestDownload(data, project)
  4: BATCH_UNIT_TEST_DELETE     --> batchUnitTestDelete(data, project)
  default: no-op
```

### 5.6 BatchUnitTestService.handleAgentAction Dispatch

```
CommandEnum ordinal --> switch:
  1: CODE_BATCH_UNIT_TEST_CREATE:
       Remove from AGENT_REQUEST
       Send success message to WebView
  2: CODE_BATCH_UNIT_TEST_LIST:
       Parse list response
       Send to WebView
  3: CODE_BATCH_UNIT_TEST_DOWNLOAD:
       Handle download (open directory if complete)
  4: CODE_BATCH_UNIT_TEST_CANCEL:
  5: CODE_BATCH_UNIT_TEST_DELETE:
       Send failure message to WebView
  default: no-op
```

---

## 6. UnitTestDialog UI Analysis

### 6.1 Field Mapping (Obfuscated Names)

| Obfuscated | Actual Type | Purpose |
|------------|-------------|---------|
| `char` | JBCheckBox | "Test private methods" checkbox |
| `int` | JRadioButton | "Generate by template: ENABLED" radio |
| `new` | String | Comment text for template option |
| `long` | ComboBox | Test framework selector (JUnit4/5/TestNG) |
| `super` | ComboBox | Mock framework selector (PowerMock/Mockito/Off) |
| `for` | JRadioButton | "Generate by template: DISABLED" radio |
| `if` | JLabel | Status label |
| `case` | ExcludeMethodConfigurable | Method exclusion panel |
| `final` | Logger (static) | Logger instance |
| `try` | String | Selected test framework name |
| `float` | JPanel | Template option panel |
| `byte` | Project | Current project |
| `enum` | JPanel | Main settings panel |

### 6.2 UI Components

**Test Framework ComboBox** (`long`):
- Populated from `UnitTestBaseEnum.values()` (excluding "FY[K" / default)
- Dimension: 230 x preferred height
- ItemListener triggers mock framework update

**Mock Framework ComboBox** (`super`):
- Items: POWER_MOCK, MOCKITO, OFF (dependency string)
- Dimension: 230 x preferred height
- ItemListener for selection changes

**Template Generation Radio Buttons**:
- `int` (ENABLED): "Generate by template" enabled option
- `for` (DISABLED): "Generate by template" disabled option
- ButtonGroup ensures mutual exclusion
- ActionListeners: `kC()` sets ENABLED, `wB()` sets DISABLED

**Private Method CheckBox** (`char`):
- Text: i18n message (obfuscated)
- Initial state from `UnitTestSettingsState.testPrivate`

**Exclude Method Configurable** (`case`):
- Custom panel for excluding specific methods from test generation

### 6.3 Key Methods

| Method | Purpose |
|--------|---------|
| `createCenterPanel()` | Build main dialog UI |
| `za()` | Build template option sub-panel |
| `Dc(ComboBox, ComboBox)` | Build framework selector panel |
| `RA(JBCheckBox)` | Build private method option panel |
| `getSelectedValue(MethodGeneratorConfig)` | Extract selected values into config |
| `changeGenerateByTemplateComponent(String)` | Update UI when template switch changes |
| `setGenerateByTemplateSwitch(GenaratebyTemplateSwitchEnum)` | Set template generation mode |
| `hB()` | Get current template switch enum |
| `createActions()` | Create dialog action buttons |
| `Ic(ItemEvent)` | Handle test framework combo change |
| `Mb(ItemEvent)` | Handle mock framework combo change (static) |

### 6.4 Settings Persistence

- `UnitTestSettingsState.getInstance()` provides persistent settings:
  - `testFramework`: Selected test framework name
  - `mockFramework`: Selected mock framework name
  - `testPrivate`: Whether to test private methods
  - `enabledGenerateByTemplate`: Template generation mode

---

## 7. DTO Classes: Complete Field Reference

### 7.1 UnitTestAgentDto (Agent Request)

```
UnitTestAgentDto
  |-- name: String                    (class name)
  |-- range: List<RangeDTO>          (code range)
  |-- methods: List<method>          (method list)
  |-- structure: String              (class structure)
  |-- testFrame: String              (test framework)
  |-- mockFrame: String              (mock framework)
  |-- msg: String                    (message)
  |-- text: String                   (text content)
  |-- reason: String                 (reason)
  |-- error: String                  (error message)
  |-- ended: boolean                 (stream ended flag)
  |-- methodUnitTestDataList: List<MethodUnitTestData>
  |-- collectScheme: String          (collection scheme)
  |-- clientName: String             (IDE name)
  |-- clientVersion: String          (IDE version)
  |-- pluginVersion: String          (plugin version)
```

### 7.2 UnitTestAgentDto$method (Method in Agent Request)

```
method
  |-- name: String                   (method name)
  |-- range: List<RangeDTO>         (code range)
  |-- code: String                   (method source code)
  |-- inputValue: String             (input value)
  |-- outputValue: String            (output value)
  |-- testContent: String            (test content)
  |-- unitTest: String               (unit test annotation)
  |-- unitMock: String               (mock annotation)
  |-- methodContent: String          (method content)
```

### 7.3 RequestCaseCodeDto + ValueDTO

```
RequestCaseCodeDto
  |-- type: String
  |-- value: ValueDTO

ValueDTO
  |-- id: String
  |-- caseDescription: String
  |-- inputValue: String
  |-- outputValue: String
  |-- pid: String
  |-- type: String
  |-- language: String
  |-- testContent: String
  |-- unitTest: String
  |-- unitMock: String
  |-- methodContent: String
  |-- code: String
  |-- originCode: String
  |-- url: String
  |-- absolutePath: String
  |-- className: String
  |-- methodName: String
  |-- content: String
  |-- codeInfo: CodeInfoDto
  |-- needToken: boolean
  |-- append: String
  |-- context: CommentContext
```

### 7.4 UnitTestCollectDto (Statistics)

```
UnitTestCollectDto
  |-- commitId: String
  |-- commitTotal: Integer
  |-- commitIncrementTotal: Integer
  |-- commitUnitTestTotal: Integer
  |-- commitUnitTestIncrementTotal: Integer
  |-- collectScheme: String
  |-- clientName: String
  |-- clientVersion: String
  |-- pluginVersion: String
  |-- methodUnitTestDataList: List<CommitChangeDto>
```

### 7.5 CommitChangeDto

```
CommitChangeDto
  |-- methodId: String
  |-- commitChangeUnitTestTotal: Integer
  +-- toString(): methodId + commitChangeUnitTestTotal
```

### 7.6 ChangeInfoDto

```
ChangeInfoDto
  |-- changeLine: Integer
  |-- content: String
```

### 7.7 MethodUnitTestData

```
MethodUnitTestData
  |-- methodId: String
  |-- generateUnitTestTotal: Integer
```

### 7.8 FunctionDataDto

```
FunctionDataDto
  |-- functionName: String
  |-- id: String
  |-- xmlCase: String
  |-- methodContent: String
  |-- testContent: String
  |-- caseContent: String
  |-- caseInput: String
  |-- caseResult: String
  |-- unitTest: String
  |-- unitMock: String
```

### 7.9 UnitTestMethodDto

```
UnitTestMethodDto
  |-- psiMethod: PsiMethod
  |-- methodRange: List<Integer>
  |-- methodLine: Integer
  |-- hasChange: Boolean
  |-- changeContent: List<String>
  |-- unitTestMethod: Boolean
  |-- methodId: String
  |-- increment: Integer
```

### 7.10 UnitTestPromptDto

```
UnitTestPromptDto
  |-- methodContent: String
  |-- testContent: String
```

### 7.11 BatchUnitTestDto

```
BatchUnitTestDto
  |-- taskId: String
  |-- gitUrl: String
  |-- gitBranch: String
  |-- gitType: String
  |-- gitToken: String
  |-- unitTestDirectory: String
  |-- testFramework: String
  |-- unitTestLanguage: String
  |-- taskStatus: String
  |-- description: String
  |-- completion: String
  |-- total: String
  |-- modifyTime: String
  |-- remark: String
```

---

## 8. H() Obfuscation Call Sites

### 8.1 UnitTestService H() Calls (20+)

| Method | H() Target | Purpose |
|--------|------------|---------|
| `testCollectionGenerate` | CodeCompleteService.H | Decode collect scheme |
| `testCollectionGenerate` | EditorUtils.H | Decode plugin version key |
| `hc` | EditorUtils.H | Decode "paramMaps" key |
| `hc` | CodeCompleteService.H | Decode warning message |
| `notice` | EditorUtils.H | Decode notification group ID |
| `notice` | CodeCompleteService.H | Decode notification title/content |
| `javaUnitTestAnalysis` | EditorUtils.H | Decode "data" JSON key |
| `QA` | CodeCompleteService.H | Decode date format pattern |
| `openSaveTestFile` | EditorUtils.H | Decode path separator |
| `handleJavaUnitTestByElement` | CodeCompleteService.H | Decode pid value |
| `RB` | EditorUtils.H | Decode multiple JSON keys |
| `RB` | CodeCompleteService.H | Decode multiple JSON keys |

### 8.2 CppTestService H() Calls

| Method | H() Target | Purpose |
|--------|------------|---------|
| `resolveFunctionCase` | FileExtensionLanguageDetails.H | Decode JSON keys (stream, code, structure, etc.) |
| `resolveFunctionCase` | CodeCompleteService.H | Decode version, structure keys |
| `cppTestAnalysis` | FileExtensionLanguageDetails.H | Decode "data" JSON key |
| `resolveCppTest` | CodeCompleteService.H | Decode pid, testFrame, mockFrame keys |
| `resolveCppTest` | FileExtensionLanguageDetails.H | Decode default values |

### 8.3 BatchUnitTestService H() Calls

| Method | H() Target | Purpose |
|--------|------------|---------|
| `batchUnitTestCreate` | GeneratorConfig.H | Decode "data" JSON key |
| `batchUnitTestDelete` | EditorUtils.H | Decode "id" JSON key |
| `codeBatchUnitTestList` | GeneratorConfig.H | Decode "data" JSON key |
| `codeBatchUnitTestList` | EditorUtils.H | Decode "type" and "data" keys |
| `batchUnitTestMessage` | EditorUtils.H | Decode "type" key |
| `batchUnitTestMessage` | GeneratorConfig.H | Decode "success", "message", "data" keys |
| `batchUnitTestDownload` | GeneratorConfig.H | Decode "data" key |
| `batchUnitTestDownload` | EditorUtils.H | Decode "id", "status", "path" keys |

### 8.4 UnitTestDialog H() Calls

| Method | H() Target | Purpose |
|--------|------------|---------|
| `za` | GenericUtils.H | Decode label text |
| `za` | AICodeStringUtil.H | Decode tooltip, comment text |
| `createCenterPanel` | AICodeStringUtil.H | Decode radio button text |
| `createCenterPanel` | GenericUtils.H | Decode section titles, checkbox text |
| `Dc` | AICodeStringUtil.H | Decode framework labels |
| `Dc` | GenericUtils.H | Decode framework comments |
| `changeGenerateByTemplateComponent` | GenericUtils.H | Decode separator text |
| `changeGenerateByTemplateComponent` | AICodeStringUtil.H | Decode option text |
| `createActions` | AICodeStringUtil.H | Decode button text |
| `createActions` | GenericUtils.H | Decode button text |

---

## 9. Interaction with template Package

### 9.1 Template System Integration Points

| UnitTestService Method | Template Package Class | Purpose |
|------------------------|----------------------|---------|
| `QA()` | `CreateTestMethodTask.cacheFileTemplateMap` | Read cached template results |
| `hc()` | `CacheFileTemplate.getParamMaps()` | Get template parameters |
| `startMethodGenerate()` | `CreateTestMethodTask.execute()` | Execute template generation |
| `ec()` | `MethodGeneratorConfig` | Configure template generation |
| `aa()` | `CaseResult`, `CaseParam`, `CaseBranch` | Build template case data |
| `ZA()` | `Method`, `CaseResult` | Add template case to list |
| `FA()` | `Method`, `CaseResult` | Match method with template |
| `Ia()` | `Method` | Check method match |
| `dB()` | `ToMockMethod` | Validate mock method |
| `eb()` | `ToMockMethod` | Process mock method |

### 9.2 Data Flow: template --> test

```
template.request.dto.CaseParam     -->  CodeList.input (Map<String, CaseParam>)
template.request.dto.CaseBranch    -->  CodeList.branches (List<CaseBranch>)
template.request.dto.ToMockMethod  -->  CodeList.dependencies (List<ToMockMethod>)
template.request.dto.CaseResult    -->  Used in aa() to build CodeList
template.context.domain.Method     -->  Used in FA() for method matching
```

### 9.3 Template Generation Modes

1. **Template-based** (GenaratebyTemplateSwitchEnum.ENABLED):
   - Uses `CreateTestMethodTask` to generate tests from templates
   - Results cached in `cacheFileTemplateMap`
   - CodeList populated with CaseParam, CaseBranch, ToMockMethod

2. **Agent-based** (GenaratebyTemplateSwitchEnum.DISABLED):
   - Sends CODE_TEST_CASE request to backend agent
   - Agent uses LLM to generate test cases
   - Results come back as FunctionDataDTO.Data.Cases

3. **Hybrid** (default):
   - Template-based for supported frameworks
   - Falls back to agent-based for unsupported cases

---

## 10. WebSocket Command/Response Mapping

### 10.1 Outgoing Commands (Plugin --> Backend)

| Command | Trigger | Data Payload |
|---------|---------|-------------|
| CODE_TEST_ANALYSIS | handleJavaUnitTest / resolveCppTest | {path, lang, range, testFrame, mockFrame, structure} |
| CODE_TEST_CASE | requestMethodCase | UnitTestAgentDto |
| CODE_TEST_CODE | requestCaseCode | RequestCaseCodeDto |
| CODE_TEST_SAVE | testSave | {path, content} |
| TEST_MAKE_CASE | resolveFunctionCase | {stream, version, code, testFrame, mockFrame, structure} |
| TEST_MAKE_CODE | getTestCode | FunctionDataDTO.Data |
| CODE_TEST_MAKE_CASE_JAVA | (template path) | MethodGeneratorConfig |
| LOG_TEST_COLLECTION_GENERATE | testCollectionGenerate | UnitTestAgentDto |
| CODE_BATCH_UNIT_TEST_CREATE | batchUnitTestCreate | BatchUnitTestDto |
| CODE_BATCH_UNIT_TEST_LIST | batchUnitTestList | (none) |
| CODE_BATCH_UNIT_TEST_DOWNLOAD | batchUnitTestDownload | {id, taskId} |
| CODE_BATCH_UNIT_TEST_DELETE | batchUnitTestDelete | {id} |

### 10.2 Incoming Responses (Backend --> Plugin)

| Command | Handler | Response DTO |
|---------|---------|-------------|
| CODE_TEST_CASE | getTestCase() | UnitTestAgentDto |
| CODE_TEST_ANALYSIS | javaUnitTestAnalysis() / cppTestAnalysis() | UnitTestAgentDto |
| CODE_TEST_CODE | getTestCode() | {caseCode, testContent} |
| CODE_TEST_SAVE | testSave() | {path, content} |
| CODE_BATCH_UNIT_TEST_CREATE | handleAgentAction() | {status} |
| CODE_BATCH_UNIT_TEST_LIST | codeBatchUnitTestList() | List<BatchUnitTestDto> |
| CODE_BATCH_UNIT_TEST_DOWNLOAD | batchUnitTestDownload() | {status, path} |
| CODE_BATCH_UNIT_TEST_CANCEL | handleAgentAction() | {error} |
| CODE_BATCH_UNIT_TEST_DELETE | handleAgentAction() | {error} |

---

## 11. File I/O Operations

### 11.1 Test File Path Resolution

`getTestPath(Project, String, String, String)` computes the test file path:
- Uses `BatchUnitTestTemplateService.getTestPath()` for Java
- Resolves relative to module content root
- Follows Maven/Gradle convention: `src/test/java/...`

### 11.2 Test File Save Flow

```
saveUnitTestFile(Project, DataDTO)
    |
    v
1. Get test file path from DataDTO
2. Create directories if needed
3. Write test content via jB()
    |-- Merge imports (SB for static, Ob for regular)
    |-- Add missing imports via iA()
    |-- Format code via CodeStyleManager
4. Call LocalFileSystem.refreshAndFindFileByPath()
5. Open file in editor via openSaveTestFile()
```

### 11.3 Import Merging

```
mergeFields(PsiJavaFile source, PsiJavaFile testFile)
    |
    v
SB() -- Merge static imports
    |-- For each import in source not in testFile:
    |       Add to testFile
Ob() -- Merge regular imports
    |-- For each import in source not in testFile:
    |       Add to testFile
iA() -- Add missing imports
    |-- For each import needed by test code:
    |       Check if already exists (Ib())
    |       If not, add import statement
xb() -- Merge with formatting
    |-- Merge imports + reformat via CodeStyleManager
```

---

## 12. Error Handling

### 12.1 Error Notification

`notice(Project)` shows an IntelliJ notification:
- Group: H-obfuscated notification group ID
- Type: MessageType.ERROR
- Title: "Module not found" (i18n)
- Content: Guidance message (i18n)

### 12.2 Error Reporting to WebView

`sendUnitTestErrInfo(Project, WebViewDataTypeEnum, String, String)`:
- Builds JsonObject with error type and message
- Sends to WebView via SocketMessageHandleListener.send2Web()

### 12.3 Test Analysis Error

`testAnalysisErr(Project, ResponseDto)`:
- Handles errors from test analysis requests
- Sends error info to WebView

### 12.4 Request Test Case Error

`requestTestCaseErr(ResponseDto, CommandEnum, MessageDto)`:
- Handles errors from test case generation
- Returns JsonObject with error details

---

## 13. Settings Integration

### 13.1 AICodeSettingsState Fields Used

| Field | Used In | Purpose |
|-------|---------|---------|
| `testFramework` | QA(), UnitTestDialog | Default Java test framework |
| `mockFramework` | QA(), UnitTestDialog | Default Java mock framework |
| `modifyTestFrame` | QA() | Whether to persist framework changes |
| `pyTestFramework` | CppTestService | Python test framework |
| `pyMockFramework` | CppTestService | Python mock framework |
| `pyModifyTestFrame` | CppTestService | Whether to persist Python framework changes |
| `pyModifyTestFramenNum` | CppTestService | Counter for Python framework modification |

### 13.2 UnitTestSettingsState Fields Used

| Field | Used In | Purpose |
|-------|---------|---------|
| `testFramework` | UnitTestDialog | Selected test framework |
| `mockFramework` | UnitTestDialog | Selected mock framework |
| `testPrivate` | UnitTestDialog | Test private methods flag |
| `enabledGenerateByTemplate` | UnitTestDialog | Template generation mode |

---

## 14. Key Design Patterns

### 14.1 Dual Generation Strategy

The system supports two test generation strategies:
1. **Template-based**: Uses pre-defined templates with CaseParam/CaseBranch/ToMockMethod
2. **Agent-based**: Sends code to backend LLM for generation

The `GenaratebyTemplateSwitchEnum` (ENABLED/DISABLED) controls which strategy is used.

### 14.2 WebView Communication

All UI updates go through `SocketMessageHandleListener.send2Web()` with:
- `WebViewDataTypeEnum` for action type
- `JsonObject` for data payload

### 14.3 Obfuscated String Decoding

All string constants use H() deobfuscation via:
- `CodeCompleteService.H()` - Primary decoder
- `EditorUtils.H()` - Secondary decoder
- `GeneratorConfig.H()` - Batch-specific decoder
- `FileExtensionLanguageDetails.H()` - C++/Python-specific decoder
- `GenericUtils.H()` - UI text decoder
- `AICodeStringUtil.H()` - UI text decoder

### 14.4 Caching

`CreateTestMethodTask.cacheFileTemplateMap` caches template generation results:
- Key: file path (String)
- Value: CacheFileTemplate with paramMaps
- Used in hc() to resend cached messages for retry

### 14.5 Streaming Support

FunctionDataDTO.Data has a `stream` field:
- When true: agent sends incremental test code updates
- When false: agent sends complete test code at once
- Used in CppTestService.resolveFunctionCase() to set stream flag

---

## 15. Summary Statistics

| Metric | Value |
|--------|-------|
| Total classes | 28 |
| Service classes | 4 (UnitTestService, BatchUnitTestService, CppTestService, UnitTestDialog) |
| Inner helper classes | 5 ($d, $e, $h, $g, $l) |
| DTO classes | 19 |
| Total methods (UnitTestService) | 74 |
| Total methods (BatchUnitTestService) | 9 |
| Total methods (CppTestService) | 5 |
| Total methods (UnitTestDialog) | 15 |
| UnitTestService bytecode size | 66,280 bytes |
| H() obfuscation calls (test package) | 40+ |
| WebSocket commands used | 12 |
| WebView data types used | 15+ |
| DTO nesting depth | 4 levels (Dto -> DataDTO -> FunctionDataDTO -> Data -> Cases) |
| Template package dependencies | 6 classes (CaseParam, CaseBranch, ToMockMethod, CaseResult, Method, CacheFileTemplate) |
