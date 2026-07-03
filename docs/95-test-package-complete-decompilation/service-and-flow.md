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
startMethodGenerate(Project, MethodGeneratorConfig, String, String, String, PsiClass, List&lt;PsiMethod&gt;, boolean)
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
