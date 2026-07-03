## 10. WebSocket Command/Response Mapping

### 10.1 Outgoing Commands (Plugin --> Backend)

| Command | Trigger | Data Payload |
|---------|---------|-------------|
| CODE_TEST_ANALYSIS | handleJavaUnitTest / resolveCppTest | &#123;path, lang, range, testFrame, mockFrame, structure&#125; |
| CODE_TEST_CASE | requestMethodCase | UnitTestAgentDto |
| CODE_TEST_CODE | requestCaseCode | RequestCaseCodeDto |
| CODE_TEST_SAVE | testSave | &#123;path, content&#125; |
| TEST_MAKE_CASE | resolveFunctionCase | &#123;stream, version, code, testFrame, mockFrame, structure&#125; |
| TEST_MAKE_CODE | getTestCode | FunctionDataDTO.Data |
| CODE_TEST_MAKE_CASE_JAVA | (template path) | MethodGeneratorConfig |
| LOG_TEST_COLLECTION_GENERATE | testCollectionGenerate | UnitTestAgentDto |
| CODE_BATCH_UNIT_TEST_CREATE | batchUnitTestCreate | BatchUnitTestDto |
| CODE_BATCH_UNIT_TEST_LIST | batchUnitTestList | (none) |
| CODE_BATCH_UNIT_TEST_DOWNLOAD | batchUnitTestDownload | &#123;id, taskId&#125; |
| CODE_BATCH_UNIT_TEST_DELETE | batchUnitTestDelete | &#123;id&#125; |

### 10.2 Incoming Responses (Backend --> Plugin)

| Command | Handler | Response DTO |
|---------|---------|-------------|
| CODE_TEST_CASE | getTestCase() | UnitTestAgentDto |
| CODE_TEST_ANALYSIS | javaUnitTestAnalysis() / cppTestAnalysis() | UnitTestAgentDto |
| CODE_TEST_CODE | getTestCode() | &#123;caseCode, testContent&#125; |
| CODE_TEST_SAVE | testSave() | &#123;path, content&#125; |
| CODE_BATCH_UNIT_TEST_CREATE | handleAgentAction() | &#123;status&#125; |
| CODE_BATCH_UNIT_TEST_LIST | codeBatchUnitTestList() | List&lt;BatchUnitTestDto&gt; |
| CODE_BATCH_UNIT_TEST_DOWNLOAD | batchUnitTestDownload() | &#123;status, path&#125; |
| CODE_BATCH_UNIT_TEST_CANCEL | handleAgentAction() | &#123;error&#125; |
| CODE_BATCH_UNIT_TEST_DELETE | handleAgentAction() | &#123;error&#125; |

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
