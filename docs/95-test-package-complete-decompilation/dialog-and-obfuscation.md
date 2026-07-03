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
  |-- range: List&lt;RangeDTO&gt;          (code range)
  |-- methods: List&lt;method&gt;          (method list)
  |-- structure: String              (class structure)
  |-- testFrame: String              (test framework)
  |-- mockFrame: String              (mock framework)
  |-- msg: String                    (message)
  |-- text: String                   (text content)
  |-- reason: String                 (reason)
  |-- error: String                  (error message)
  |-- ended: boolean                 (stream ended flag)
  |-- methodUnitTestDataList: List&lt;MethodUnitTestData&gt;
  |-- collectScheme: String          (collection scheme)
  |-- clientName: String             (IDE name)
  |-- clientVersion: String          (IDE version)
  |-- pluginVersion: String          (plugin version)
```

### 7.2 UnitTestAgentDto$method (Method in Agent Request)

```
method
  |-- name: String                   (method name)
  |-- range: List&lt;RangeDTO&gt;         (code range)
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
  |-- methodUnitTestDataList: List&lt;CommitChangeDto&gt;
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
  |-- methodRange: List&lt;Integer&gt;
  |-- methodLine: Integer
  |-- hasChange: Boolean
  |-- changeContent: List&lt;String&gt;
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
template.request.dto.CaseBranch    -->  CodeList.branches (List&lt;CaseBranch&gt;)
template.request.dto.ToMockMethod  -->  CodeList.dependencies (List&lt;ToMockMethod&gt;)
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
