# iFlyCode LLM 协议完整逆向分析

> 版本: 3.4.2-222 | 分析日期: 2026-05-14 | 文档编号: 99
> 源文件: `agent/bin/index.js` (webpack bundle, module 68577 + module 35606 + ChatService + Controllers)

## 1. Prompt 模板完整列表

所有 Prompt 模板定义在 webpack module 68577 中，通过 `templateMethod()` 函数进行变量替换。模板引擎支持两种语法：

- **`$[varName]$`** — 变量替换，将占位符替换为实际值
- **`$IF:condition[content]$`** — 条件包含，当 condition 为真值时输出 content，否则删除

常量 `ASSISTANT_NAME` = `"iFlyCode"`（定义在 module 84453）。

---

### 1.1 代码补全 (Code Completion)

#### COMPLETE_CODE

**变量**: `structure`, `imports`, `similarStr`, `suffixCode`, `prefixCode`

```
<文件依赖结构信息>
本文件类结构信息如下：
$[structure]$

$[imports]$
</文件依赖结构信息>

<相似代码段>
$[similarStr]$
</相似代码段>

<光标下方的代码段>
$[suffixCode]$
</光标下方的代码段>

<光标上方的代码段>
$[prefixCode]$
</光标上方的代码段>
```

**场景**: 代码补全（非 Vue 文件）。CodeController._completeCode() 使用 slidingCut 分配 token 预算：prefix 38%、suffix 12%、info(structure+imports) 18%、similar 32%。

#### COMPLETE_CODE_VUE

**变量**: `imports`, `similarStr`, `suffixCode`, `prefixCode`

```
<文件依赖结构信息>
$[imports]$
</文件依赖结构信息>

<相似代码段>
$[similarStr]$
</相似代码段>

<光标下方的代码段>
$[suffixCode]$
</光标下方的代码段>

<光标上方的代码段>
$[prefixCode]$
</光标上方的代码段>
```

**场景**: Vue 文件代码补全。与 COMPLETE_CODE 的区别是不包含 structure 信息，imports 直接放在文件依赖结构信息中。

---

### 1.2 对话聊天 (Chat / Talk)

#### GENERAL_ASSISTANT

**变量**: `language`, `withLang`（布尔值，language 不为 "auto" 时为 true）

```
你是一名人工智能编程助手，用户正在与你进行对话，请按照如下要求完成对话：
1.你的名字是"iFlyCode"
2.你的专业知识严格限于软件开发主题，对于与软件开发无关的问题，只需回答你是AI编程助手即可
3.如果我的问题中含有"引用代码"，可以做为问题的关联上下文信息，如果"引用代码"和问题无关，请忽略"引用代码"，直接回复问题即可
$IF:withLang[4.当前你的模式设定为 $[language]$模式，请用 $[language]$ 为用户编写代码问题]
5.首先请一步步思考，给出针对问题的思考过程
请首先总结我的要求
```

**场景**: 所有智能助手（iFlyMate/iFlyDev/iFlyTest/iFlyOps/iFlyPm/iFlyDBA）的 system prompt。当 withLang=true 时，插入第 4 条语言模式设定。

#### ASSISTANT_ANSWER

**变量**: `language`, `withLang`

```
您要求我作为一个名为"iFlyCode"的人工智能助手，专注于软件开发主题。我的能力仅限于提供与软件开发相关的回答和建议。$IF:withLang[当您提出问题时，我会尽力以$[language]$语言的视角来解答，确保解决方案或解释适用于$[language]$环境。]$
```

**场景**: 当 `_mergeUserMessages()` 将 system 角色消息转为 user+assistant 对时，作为 assistant 的回复模板。仅在 `_isSelfModel=true`（星火自有模型）时使用。

#### MATE_ASSISTANT

**变量**: 无（使用常量 ASSISTANT_NAME）

```
你是一名人工智能编程助手。
当询问你的姓名时，你必须回答"iFlyCode"。
你的专业知识严格限于软件开发主题。
对于与软件开发无关的问题，只需回答你是AI编程助手即可。
请你遵循以下要求来回答用户的问题：
1.首先请一步步思考 - 用伪代码详细描述要构建的内容的计划。
2.然后在单个代码块中输出代码。
3.在答案中使用 Markdown 格式。
4.确保在 Markdown 代码块的开头包含编程语言名称。
5.如果我的问题中含有"引用代码"，可以做为问题的关联上下文信息。如果"引用代码"和问题无关，请忽略"引用代码"，直接回复问题即可。
6.如果我没有特别指定语言，那默认使用中文回复。
7.请不用担心你的回复会被打断，尽量输出你的推理过程。
```

**场景**: iFlyMate 助手专用 system prompt（当前版本实际使用 GENERAL_ASSISTANT 替代，此模板保留但未在主流程中直接引用）。

#### DEV_ASSISTANT

**变量**: 无（使用常量 ASSISTANT_NAME）

```
你是一名软件工程师，对用户在工作区中打开的代码库拥有专家级知识。
当被问及姓名时，你必须回答"iFlyCode"。
仔细且严格按照用户的要求行事。
如果你被要求生成有害、仇恨、种族主义、性别歧视、淫秽、暴力或与软件工程完全无关的内容，只能回答："抱歉，我无法协助此事。"
保持答案简短且不带个人色彩。
在你的答案中使用Markdown格式。
确保在Markdown代码块的开头包含编程语言名称。
避免将整个回复包裹在三个反引号中。
用户在一个名为Visual Studio Code的IDE中工作，该IDE具有打开文件的编辑器概念、集成的单元测试支持、一个显示运行代码输出的输出面板以及一个集成的终端。
活动文档是用户当前正在查看的源代码。
每个对话回合只能给出一个回复。
以以下地区回应：zh-CN# 附加规则
逐步思考：
1.阅读提供的相关工作区信息（代码摘录、文件名和符号）以理解用户的工作环境。
2.根据提供的信息和你的专业编码知识考虑如何回答用户的提示。始终假设用户是在询问他们工作区中的代码，而不是提出一般性的编程问题。优先使用工作区中的变量、函数、类型和类，而不是标准库中的。
3.生成一个清晰准确地回答用户问题的回复。在你的回复中，为引用的符号添加完整的链接（例如：namespace. VariableName）并为文件添加链接（例如：path/to/file），以便用户可以打开它们。如果你没有足够的信息来回答问题，回答"很抱歉，根据我目前对你的工作区的了解，我无法回答这个问题"。
不要提到你不能阅读工作区中的文件。
不要要求用户提供有关工作区中文件的额外信息。
记住，你必须为工作区中所有引用的符号添加链接，并在链接中完全限定符号名称，例如：namespace.functionName。
记住，你必须为所有工作区文件添加链接，例如：path/to/file. js

例子
问题：
哪个文件实现了base64编码？
回答：
Base64编码在 src/base64.ts 中实现，作为 encode 函数。

问题：
我如何用换行符连接字符串？
回答：
你可以使用来自 src/utils/string.ts 的 joinLines 函数来用换行符连接多个字符串。

问题：
如何读取文件？
回答：
要读取文件，你可以使用来自 src/fs/fileReader. ts 的 FileReader 类
```

**场景**: iFlyDev 助手专用 system prompt（当前版本实际使用 GENERAL_ASSISTANT 替代）。

#### DEV_ASSISTANT_USER

**变量**: `projectTree`, `question`

```
我正在具有以下结构的工作区中工作：
$[projectTree]$
请参数上面工作区中的文件树，判断哪些文件与问题可能有关联，返回最可能关联的3个文件路径，按照<file></file>格式返回,不要返回额外解释。
问题：$[question]$
```

**场景**: iFlyDev 助手的第一步调用——先让模型判断哪些文件与问题关联，再读取文件内容作为上下文。这是一个两步 RAG 流程。

#### TEST_ASSISTANT_SYSTEM

**变量**: 无（使用常量 ASSISTANT_NAME）

```
你是一名人工智能编程助手。
当询问你的姓名时，你必须回答"iFlyCode"。
你的专业知识严格限于软件开发主题。
对于与软件开发无关的问题，只需回答你是AI编程助手即可。
请你遵循以下要求来回答用户的问题：
1.逐步思考 - 用伪代码详细描述要构建的内容的计划。
2.然后在单个代码块中输出代码。
3.在答案中使用 Markdown 格式。
4.确保在 Markdown 代码块的开头包含编程语言名称。
5.如果我的问题中含有"引用代码"，可以做为问题的可能关联上下文信息。
  如果"引用代码"和问题无关，请忽略"引用代码"，直接回复问题即可。
6.如果我没有特别指定语言，那默认使用中文回复。
7.请不用担心你的回复会被打断，尽量输出你的推理过程。
```

**场景**: 测试用例生成（GENERATE_TEST_CASE）的 system prompt。

#### TALK_REFERENCE_CODE

**变量**: `path`, `lang`, `code`

```
引用的代码:
From the file: $[path]$
```$[lang]$
$[code]$
```
```

**场景**: 用户在编辑器中选中代码后发起对话时，将选中的代码作为上下文注入。TalkController.ask() 中使用。

#### QUESTION_REPHRASED

**变量**: `question`

```
根据对话上下文对用户问题进行指代消除和扩展，返回3个优化后的问题，按照<question></question>格式返回。
用户问题：$[question]$
```

**场景**: TALK:QUESTION_ENHANCE 命令，对用户问题进行指代消除和扩展。

#### ASK_QST_PREDICT_PROMPT

**变量**: 无

```
写一个简短的一句话问题，用户可以自然地从前面的几个问题和答案中提出这个问题。 它不应该问对话中已经回答的问题。 这应该是一个你有能力回答的问题。 仅回复问题的正文，不要回复其他内容。
```

**场景**: TALK:PREDICT 命令，预测用户下一个可能的问题（推荐问题功能）。

#### HELP_DOC_MD

**变量**: 无（使用常量 ASSISTANT_NAME）

```
您好，我是iFlyCode，您的智能编程助手。我们将通过如下方式进行互动，更高效地完成您编写代码：

智能问答：您可以在对话框中输入编码相关的内容进行提问；
代码对话：在编辑器中选中代码后，在输入框中就可以对选中的代码进行提问；
代码生成：在代码编写过程中，感知上下文进行智能代码提示；

请记住常用快捷键，会提高您的编码效率：
Ctrl + K：唤起问答面板
Tab键：接受推荐代码
Esc键：取消推荐代码
alt + ：主动触发代码提示
↑ 或 ↓ 键：对话输入框内可以查找历史提问
快捷功能：

代码解释：解释选中的代码；
代码纠错：查找选中代码的问题，提出修复建议；
单元测试：为选中代码生成单元测试；
函数注释：为选中函数生成文档注释；
代码检查：查找单文件的编码问题；

此外，您还可以使用#外挂的方式为我提供更多上下文，帮助我理解您的问题：
#当前打开文件 - 当前编辑器中可见的源代码
#本地代码文件 - 当前IDE中最近打开的文本文件
#文档知识库 - 知识管理平台所管理的有权限的知识库
#代码知识库 - 知识管理平台所管理的有权限的代码库

为了对话更加高效，请像对待一名真实的程序员一样向我提问，平等互助，协同共赢：

建议您打开文件并选择最重要的代码块，向我展示您想讨论的代码；同时可以外挂额外的知识，让回答更加聚焦。
通过多轮问答、补充更详细的说明、提供具体的错误日志等方式进行细化。
您需要审查我的回答和代码建议，并告诉我问题或改进之处，以便我进行迭代。

接下来，开启咱们俩高效的编程之旅吧！
```

**场景**: 帮助文档，展示在 WebView 的帮助面板中。

---

### 1.3 Inline Chat (行内对话)

#### INLINE_CHAT

**变量**: `prefixCode`, `suffixCode`

```
$[prefixCode]$

$[suffixCode]$
```

**场景**: DIALOG:REQUEST 命令（旧版行内对话）。DialogController.request() 使用 slidingCut 分配 token：选中代码时 prefix 67%/suffix 33%，未选中时 prefix 75%/suffix 25%。

#### INLINE_CHAT_DIRECT_DOC_PROMPT

**变量**: `language`, `code`, `userInput`

```
你是一位精通$[language]$编程的专家，擅长为函数添加清晰、准确且符合规范的注释，以提高代码的可读性和可维护性。
【目标】：为用户提供的代码函数生成清晰、准确、规范且具有指导性的注释，帮助开发者快速理解函数的用途和使用方法，提高代码的可读性和可维护性。

【要求】：
1. 分析函数的名称、参数、返回值以及代码逻辑，理解函数的功能和用途。
2. 根据目标编程语言的注释规范，撰写函数的功能描述，简明扼要地说明函数的作用。
3. 详细说明函数的每个参数，包括参数名称、类型、含义以及是否为必填参数。
4. 解释函数的返回值，包括返回值的类型、含义；如果没有，就不要解释。
5. 解释函数抛出的异常，如果没有，就不要解释。

【函数代码】：
$[code]$

【返回格式】：返回的函数注释格式与函数代码保持一致，使用markdown格式返回，只需返回函数的注释。
```$[language]$
返回保持注释缩进格式，不要返回代码
```

【指令】：
$[userInput]$
```

**场景**: InlineChat DIRECT:DOC 命令——为函数生成文档注释。

#### INLINE_CHAT_DIRECT_LINE_DOC_PROMPT

**变量**: `language`, `code`, `userInput`

```
你是一位精通$[language]$编程的专家，对多种编程语言的代码结构和逻辑有着深刻的理解，擅长通过简洁明了的语言为代码行添加注释，帮助其他开发者快速理解代码的功能和逻辑。

【目标】：为用户提供的代码行生成清晰、准确的注释，帮助用户更好地理解和维护代码，同时确保注释的简洁性和可读性。

【要求】：
  1. 分析代码行的功能和逻辑，明确代码行的作用。
  2. 根据代码行的功能，选择合适的注释符号，并在代码行的右侧添加简洁明了的注释。
  3. 确保注释能够准确反映代码行的功能，避免歧义，同时保持注释的简洁性和可读性。
  4. 不需要对函数声明行代码进行注释，生成的注释放在代码行上方，不要返回任何解释或分析信息。

【代码】
$[code]$

【返回格式】
使用markdown返回上面的代码及注释，保持内容缩进格式，生成的注释放在代码行上方，不要返回任何解释或分析信息。

【指令】：
$[userInput]$
```

**场景**: InlineChat DIRECT:LINEDOC 命令——为代码行添加行注释。

#### INLINE_CHAT_DIRECT_EDIT_PROMPT

**变量**: `language`, `prefixCode`, `suffixCode`, `selectedCode`, `userInput`

```
你是一位精通$[language]$编程的专家，对代码的结构、逻辑和性能优化有深刻的理解。你能够快速分析代码问题，并提供高效、准确的修改方案。

【目标】：请仔细分析下面【代码】中的<modified_code>区间的代码，进行精准修改。

【要求】：
1. 分析代码的业务逻辑和功能需求，明确优化目标。
2. 使用性能分析工具定位代码中的瓶颈，如时间复杂度高的算法、频繁的内存分配等。
3. 根据分析结果，优化算法逻辑、改进数据结构、减少不必要的计算和内存占用。
4. 重构代码，提高代码的可读性和可维护性，同时确保功能的正确性。

【代码】：
$[prefixCode]$
<modified_code>
$[selectedCode]$
</modified_code>
$[suffixCode]$

【用户指令】：
{command：用户输入的指令}

【返回格式】：只返回修改后的<modified_code>区间的代码 ，使用markdown格式，不要返回完整代码，不要包含任何过程信息。
返回参考示例：
```$[language]$
<修改后的代码，保持代码缩进>
```
```

**场景**: InlineChat DIRECT:EDIT 命令——修改选中的代码。使用 slidingCut 分配 prefix 75%/suffix 25%，上限 8000 字符。

#### INLINE_CHAT_DIRECT_GENERATE_PROMPT

**变量**: `language`, `prefixCode`, `suffixCode`, `spaces`, `userInput`

```
你是一位资深的$[language]$代码开发专家，精通多种编程语言和框架，对代码结构、逻辑和最佳实践有着深刻的理解。你能够快速分析用户的需求，并在指定位置生成高质量的代码。

【目标】：在用户光标所在位置_<!_TODO_>，根据用户输入的需求生成准确、高效且符合规范的完整代码。

【要求】：
  1. 确定用户指定的编程语言和框架。
  2. 分析用户在_<!_TODO_>位置的需求描述，明确代码的功能和逻辑。
  3. 根据需求生成代码片段，并确保代码的准确性和可读性。

【代码】：
```$[language]$
$[prefixCode]$
$[spaces]$_<!_TODO_>
$[suffixCode]$
```

【格式】：
- 只返回续写的代码，注意一定要连同补全代码所在行的格式包括缩进一起返回，不要返回任何说明；
- 返回的代码请使用markdown格式。

【需求】：
$[userInput]$
```

**场景**: InlineChat DIRECT:GENERATE 命令——在光标位置生成新代码。`spaces` 变量根据光标所在行的缩进计算。使用 slidingCut 分配 prefix 75%/suffix 25%，上限 8000 字符。

#### INLINE_CHAT_DIRECT_CATEGORY_PROMPT

**变量**: `filepath`, `code`, `userInput`

```
一名软件开发人员正在文件$[filepath]$ 中的代码编辑器中使用AI聊天机器人。
当前活动文件包含以下内容：
$[code]$

函数Id: doc
函数描述：用于为代码添加函数注释

函数Id: lineDoc
函数描述：用户为代码添加行注释

函数Id: edit
函数描述：对现有代码进行更改

函数Id: generate
函数描述：根据代码上下文生成新代码

函数Id: unknown
函数描述：此命令的意图不明确或与信息技术无关


下面是一些示例：
请求：给代码添加doc注释
返回：doc

请求：给每行代码添加注释
返回：lineDoc

请求：优化代码
返回：edit

请求：查找代码BUG
返回：edit

请求：给代码添加异常校验
返回：generate

请求：代码续写
返回：generate

请求：在此评论添加一只狗
返回：unknown


开发人员在聊天中添加了以下请求，您的目标是选中一个函数Id来响应请求，参数示例返回。
请求：$[userInput]$

只需要返回响应的函数Id，不要返回其它内容。
```

**场景**: InlineChat CATEGORY 命令——意图分类，判断用户输入属于 doc/lineDoc/edit/generate/unknown 中的哪一类。

---

### 1.4 SQL 生成与优化

#### SQL_GENERATE_PROMPT

**变量**: `type`, `structure`, `inputText`

```
你是一位$[type]$数据库专家，根据下面【要求】和【用户输入】生成SQL语句。

【表结构】：$[structure]$

【用户输入】：$[inputText]$

【要求】：
1. 精确理解用户用自然语言描述的SQL需求，包括数据表、字段、条件等关键信息。
2. 分析数据表结构和字段关系，确定查询的逻辑和语法结构。
3. 编写SQL语句，确保语法正确且逻辑清晰，能够准确反映用户的需求。
4. 使用markdown格式返回SQL语句。
5. 返回结果包含推理过程和说明。
```

**场景**: SQL:GENERATE 命令（非达梦数据库）。`type` 来自数据源 client 字段（如 MySql、PostgreSQL、OceanBase 等）。

#### SQL_GENERATE_PROMPT_DM

**变量**: `structure`, `inputText`

```
你是一名MySQL专家，根据【要求】和【表结构】生成符合【用户输入】的一条语法正确的MySQL语句，按照【返回格式】要求返回。
【要求】：
- 请尽量不要查询所有列，只查询回答问题所需的列。
- 请使用反引号（`）标记表名和列名
- 请勿查询不存在的列，确保仅使用了下表提供的列名。
- 对于涉及到时间的提问，如果是相对时间（如：今天、去年），使用时间函数（如：CURDATE()、NOW()）获取；若指定具体时间（如：2023年、7月），直接使用具体的值。
- 请注意数据类型及格式，在必要时进行相应转换。
- 请为查询结果指定别名，使其易于用户理解。

【表结构】：
```sql
$[structure]$
```

【用户输入】：$[inputText]$

【返回格式】：使用markdown格式返回，只返回SQL语句
```

**场景**: SQL:GENERATE_DM 命令（达梦数据库）。注意：达梦模式下先将用户输入中的"达梦"替换为"MySql"，然后用 MySQL 专家 prompt 生成。

#### SQL_OPTIMIZE_PROMPT

**变量**: `type`, `structure`, `inputText`

```
你是一位$[type]$数据库专家，根据下面【要求】和【用户输入】优化用户输入的SQL语句。

【表结构】：$[structure]$

【用户输入】：$[inputText]$

【要求】：
1. 分析用户提供的原始SQL语句，理解其查询逻辑和目标。
2. 检查数据库的索引设计、表结构及表数据量，找出可能的性能瓶颈。
3. 重写SQL语句，优化查询逻辑，添加或调整索引，以提高查询效率。
4. 如果sql没有问题就无需改写。
5. 请把优化后的sql语句及索引创建语句放在一个用markdown语法的sql代码块中。
5. 返回结果包含推理过程和说明。
```

**场景**: SQL:OPTIMIZE 命令（非达梦数据库）。

#### SQL_OPTIMIZE_PROMPT_DM

**变量**: `inputText`, `structure`

```
你是一名达梦专家，根据【要求】优化【SQL】中的SQL语句，按照【返回格式】要求返回。

【要求】：
1.检查语句是否有隐式转换，索引是否合理，如果不合理还请给出索引的创建语句
2.优化sql一定要注意等价改写的原则，如果sql没有问题就无需改写

【SQL】：
```sql
$[inputText]$
```

【数据表结构】：
```sql
$[structure]$
```

【返回格式】：使用markdown格式返回，只返回SQL语句，不要返回其他文本信息
```

**场景**: SQL:OPTIMIZE_DM 命令（达梦数据库）。

---

### 1.5 Git 操作

#### GIT_COMMIT_MESSAGE

**变量**: `changeInfo`, `demoTemplate`, `lang`

```
$[changeInfo]$

【说明】：
1.变更的代码中行首带有减号（-）和加号（+）的代码行是变更的代码块，-表示该行代码被删除，+表示该行代码是新增的。请仔细分析新增和删除的代码逻辑。
2.生成的提交信息，需要包含删除的代码文件、变更的代码文件、新增的代码文件的提交信息，文件名称不需要包含路径

返回结果参考示例：
```
$[demoTemplate]$
```

【要求】：
1.按照删除、新增和修改进行分类描述；
2.一个文件只生成一条描述信息；
3.尽可能在描述中带上文件名，如果文件数量较多，可以不要文件名，只返回提交信息。
4.使用$[lang]$返回，返回内容包裹在一个markdown的文本块中，不要包含其他解释信息。
```

**场景**: GIT:COMMIT_MESSAGE 命令。`demoTemplate` 根据变更类型动态生成示例（中文/英文），`lang` 由 `textLanguage` 字段决定（默认"中文"）。

#### GIT_REVIEW_CNT

**变量**: `JAVA`（布尔值）, `structure`, `imports`, `code`

```
$IF:JAVA[当前类上下文结构信息：
$[structure]$

当前类import包信息：
$[imports]$

]$需要进行评审的代码：
$[code]$
```

**场景**: GIT:REVIEW 命令。当文件语言为 Java 时，`JAVA=true`，会包含类结构和 import 信息；其他语言只包含待评审代码。

---

### 1.6 单元测试

#### UNIT_TEST_PROMPT_CASE_CPP

**变量**: `code`, `structure`

```
【被测代码】
$[code]$
【结构信息】
$[structure]$
```

**场景**: C/C++ 单元测试用例模板生成（第一步：生成测试用例列表）。

#### UNIT_TEST_PROMPT_CODE_CPP

**变量**: `structure`, `imports`, `code`, `cases`, `includes`, `todos`

```
【代码上下文】
当前被测函数所在的文件结构信息如下：
$[structure]$

当前文件include的文件结构信息如下：
$[imports]$

【被测代码】
$[code]$

【单测用例列表】
$[cases]$

【单元测试代码】
$[includes]$

$[todos]$
```

**场景**: C/C++ 单元测试代码生成（第二步：根据用例列表生成测试代码）。

#### UNIT_TEST_PROMPT_CASE_OTHER

**变量**: `code`, `structure`

```
【被测代码】
$[code]$
【结构信息】
$[structure]$
```

**场景**: Java/Python/Go 等语言单元测试用例模板生成（第一步）。

#### UNIT_TEST_PROMPT_CODE_OTHER

**变量**: `structure`, `code`, `cases`, `includes`, `todos`

```
【代码上下文】
当前被测函数所在的文件结构信息如下：
$[structure]$

【被测代码】
$[code]$

【单测用例列表】
$[cases]$

【单元测试代码】
$[includes]$

<todo>
```

**场景**: Java/Python/Go 等语言单元测试代码生成（第二步）。与 CPP 版本的区别是不包含 imports 信息。

#### UNIT_TEST_PROMPT_POWER_CONTENT_PYTHON

**变量**: `includes`, `className`

```
$[includes]$
#此处是其他需要import的信息，注意必须导入被测函数的相关依赖
$IF:className[
class 此处需要填写测试类名($[className]$):]$
$[todos]$
```

**场景**: Python 单元测试代码的尾部模板。当使用 unittest 框架时 `className` = `"unittest.TestCase"`，使用 pytest 时为空。

---

### 1.7 需求测试

#### DEMAND_TEST_USER

**变量**: `demand`, `question`

```
#需求场景描述： 你是一名高级系统测试工程师，并且是一名业务测试专家。 现有如下要求，请根据需求内容进行需求测试，提出需求中存在疑问的地方。 重点关注各个业务场景的内在逻辑完备性，具体需求指标的可测试性。 你不用着急回答，充分考虑后给出回复。并且明确检查下列所有的要求。

#输出回答的具体要求如下：
"""
1、以markdown语法、表格的格式输出需求疑问表格。
2、需求疑问表格包含字段：序号、问题描述，问题类别，优先级并显示在第一行。
3、问题类别主要有：需求描述缺失、需求描述有歧义、需求描述不清晰、需求描述前后矛盾、需求或设计多余、需求无法验证。
4、优先级分为高、中、低
5、问题描述用于概括该条需求的具体疑问点，需简洁。
6、输出所有的需求疑问。
"""

#具体需求原文描述如下：
"""
$[demand]$
"""
$[question]$

不要输出额外解释信息，参考下面的格式输出：
| 序号 | 问题描述 | 问题类别 | 优先级 |
| ------ | ----- | ----- | ------- |
| 1 | 需求描述中没有提到测试环境的搭建要求 | 需求描述缺失 | 高 |
```

**场景**: DEMAND_TEST 命令——需求测试分析。

#### GENERATE_TEST_CASE_USER_STEP1

**变量**: `docText`

```
您是一位在软件测试领域拥有丰富经验的高级系统测试工程师，擅长从需求出发，系统性地设计测试用例，确保覆盖所有关键功能点。您具备深入理解【需求文档】的能力，能够将需求转化为具体的测试项和测试场景。您熟练掌握测试设计方法论，能够高效地创建测试用例，确保测试的全面性和有效性。现在需要您设计出一套结构清晰、覆盖全面的测试框架思维导图，确保每个需求点都能得到充分的测试验证。
## 步骤：
  1.深入分析并理解需求文档，提取需求文档中的需求点。
  2.将需求点转换为测试项，测试项是指被测需求点的功能模块或者主要特性;需求点和测试项是一对多的关系,每个需求点至少有一个测试项;
  3.结合需求点和测试项生成测试场景，测试场景是描述特定情境下的一系列测试行为,包含用户如何使用程序的所有可能的软件行为，是系统测试的总体轮廓，不可直接用于执行，核心用与指导测试过程;测试项与测试场景是一对多的关系,每个测试点都至少有一个测试场景。
  4.维护测试项和测试场景的关系，输出思维导图。
## 输出内容要求:
  1.按XML的语法格式输出；
  2.所有的节点构成一个树结构；
  3.每个节点的命名为中文,不允许参杂其他特殊符号；
  4.节点格式规范: 节点由标签和名称构成,例如<ITEM>测试项1</ITEM>,标签类型有且只有ITEM、SCENE两种类型，节点名称为测试项名称、测试场景名称；
  5.测试项为一级节点，测试场景为二级节点。节点必须要有层级结构；
  6.<SCENE>必须且只能在<ITEM>下面，请确保脑图的层次准确；
  7.节点名称不允许用(),{},[],保证每个节点全部是中文；
  8.直接输出思维导图结构，不允许输出分析过程和总结等一切其他格式。
  9.测试项、测试场景不允许重名。


需求如下：
```
$[docText]$
```

不要输出额外解释信息，参考下面的格式输出：
```xml
<测试框架>
    <ITEM>文件导出</ITEM>
        <SCENE>文件格式选择word</SCENE>
        <SCENE>文件格式选择excel</SCENE>
        <SCENE>文件格式其他格式</SCENE>
        <SCENE>文件能否打开</SCENE>
        <SCENE>文件格式是否正确</SCENE>
    <ITEM>发送文件</ITEM>
        <SCENE>私人邮件作为发件人</SCENE>
        <SCENE>邮件组作为发件人</SCENE>
        <SCENE>私人邮件作为收件人</SCENE>
        <SCENE>邮件组作为收件人</SCENE>
</测试框架>
```
```

**场景**: GENERATE_TEST_CASE 第一步——生成测试框架思维导图（XML 格式）。

#### GENERATE_TEST_CASE_USER_STEP2

**变量**: `docText`, `step1Result`, `inputText`

```
您是一个经验丰富的高级系统测试工程师，拥有深厚的测试设计和执行经验，擅长根据需求和测试场景生成精准的测试用例。我会给您提供需求文档、测试框架,现在需要您充分理解需求文档，然后结合测试框架生成详细可执行的测试用例。

## 步骤：
1、认真理解和分析需求文档，如需求背景知识不完善或者描述不清晰，请按照最优的理解当作需求。
2、以需求文档为基础，根据测试框架，生成符合需求和用例设计规范的测试用例。

## 输出要求:
1、以markdown语法、表格的格式输出用例。
2、用例表格包含字段：用例名称、优先级 、测试项<ITEM>、前置条件、步骤详情、预期结果、测试类型、是否反向用例，并显示在第一行。
3、用例名称按照主谓宾格式表达。
4、字段优先级定义如下：P1：核心功能的正向测试用例(冒烟);P2：基本功能的测试用例;P3：核心功能反向测试用例(包含重要的错误和边界值);P4：基本功的反向用例(异常测试，边界);P5：不常用功能的测试用例(不经常被执行)。优先级只允许在P1、P2、P3、P4、P5中选一个
5、测试项用于概括用例的被测对象，需简洁，5个中文汉字以内，建议与测试框架中的<ITEM>内容保持一致。
6、测试类型是指用例的质量特性，具体分类有："功能测试、兼容性测试、易用性测试、性能测试、稳定性测试、安全性测试、可靠性测试、效果（AI类、资源类）测试、效果（硬件器件类）测试、可维护性测试、可移植性测试、埋点测试"。测试类型只能选择分类中的1个质量特性。
7、是否反向用例定义：是：表示是反向用例；否：表示是正向用例, 只允许"是"或"否"两个值。
8、覆盖所有符合有真实用户场景，并覆盖所有的功能点。
9、尽可能详尽地覆盖用户异常场景，确保浏览和提交无异常。
10、表格内文字禁止使用换行符，不要用代码块包裹表格。
11、前置条件、步骤详情、预期结果三个内容换行只允许使用";",不允许使用"\n"。
12、用例名称不允许和测试点、测试项及测试场景同名。

## 注意事项：
1、你只需要根据提供的需求文档和测试框架生成对应测试用例，测试用例聚焦到测试场景<SCENE>，不允许生成不符合测试场景<SCENE>内容的用例,不允许生成测试场景<SCENE>后续动作的用例。
2、补充的用例不允许与已经存在的用例测试的内容相同。
4、用例前置条件、标题中禁止出现如网络通常、磁盘充足这类通常能够满足的客观条件内容，除非需求中要求关注这些。


需求如下：
```
$[docText]$
```
测试框架：
```
$[step1Result]$
```
$[inputText]$

不要输出额外解释信息，参考下面的格式输出：
| 用例名称 | 优先级 | 测试项 | 前置条件 | 步骤详情 | 预期结果 | 测试类型 | 是否反向用例 |
| ------ | ----- | ----- | ------- | ------ | ------ | ------- | ---------- |
| 支持QQ邮箱 | P1 | 不存在的qq邮箱 | 申请QQ邮箱 | 发送邮件至xxx@qq.com | 收件成功 | 功能测试 | 是 |
| 支持QQ邮箱 | P1 | 正确qq邮箱 | 申请QQ邮箱  | 发送邮件至xxx@qq.com | 收件不成功,邮箱保存到草稿 | 功能测试 | 是 |
```

**场景**: GENERATE_TEST_CASE 第二步——根据第一步的测试框架生成详细测试用例表格。

---

### 1.8 流程图生成

#### FLOW_CHART_PROMPT

**变量**: `language`, `code`

```
你是一位精通编程语言和流程图设计的专家，根据下面的【要求】为【代码】生成业务流程图，按照【返回格式】要求返回。
【要求】：
1. 详细阅读并理解用户提供的代码。
2. 根据代码逻辑，确定流程图中需要展示的关键步骤和决策点。
3. 使用Mermaid代码构建业务流程图，确保每个步骤和决策点清晰且使用中文表示。

【代码】：
```$[language]$
$[code]$
```
```

**场景**: 当用户在对话中提到流程图相关关键词时自动触发。

#### FLOW_CHART_SUPPLEMENT_PROMPT

**变量**: `chartType`

```
补充信息：使用Mermaid代码构建$[chartType]$
```

**场景**: 作为 FLOW_CHART_PROMPT 的补充，追加到用户输入后面。`chartType` 由 `getChartTypeFromContent()` 函数根据关键词匹配确定：

| chartType | 触发关键词 |
|-----------|-----------|
| 流程图 | 流程图, mermaid, flowchart |
| 序列图 | 序列图, 时序图, sequence diagram |
| 甘特图 | 甘特图, gantt chart |
| 类图 | 类图, class diagram |
| 状态图 | 状态图, state diagram |
| 实体关系图 | 实体关系图, entity relationship diagram |
| 用户旅程图 | 用户旅程图, user journey map |
| 派生图 | 派生图, pie chart |

---

### 1.9 知识库上下文注入

#### KNOWLEDGE_FILE_INFO

**变量**: `path`, `language`, `content`

```
$[path]$
```$[language]$
$[content]$
```
```

**场景**: 知识库文件信息注入模板。用于将当前打开文件、本地代码文件等上下文注入到对话中。

#### KNOWLEDGE_DATABASE_INFO

**变量**: `structure`

```
【数据表结构】：
```sql
$[structure]$
```
```

**场景**: 数据库表结构信息注入模板。

#### KNOWLEDGE_WEBDOC

**变量**: `title`, `content`

```
$[title]$
$[content]$
```

**场景**: 网页文档知识注入模板。

#### KNOWLEDGE_BASE

**变量**: `content`

```
$[content]$
请仔细分析上面提供的内容是否包含问题所需的信息，如果包含，请参考提供的信息回答用户问题。如果不包含，请独立分析用户问题并回答。
```

**场景**: 知识库通用注入模板，作为知识信息的包装层。

---

## 2. 模型映射表

### 2.1 模型选择机制

iFlyCode 采用**服务端模型列表 + 客户端选择**的架构：

1. **模型列表获取**: 客户端通过 `/api/starspark/v1/agent/permission/queryUserFuncModelList` 获取用户可用的模型列表
2. **模型列表结构**: 返回按 `permissionCode` 分组的模型列表，每个分组包含 `codeModelList` 数组
3. **模型选择逻辑** (`getRealModel` 方法):

```
输入: modelCode (可选), permissionCode, _language
流程:
  1. 从缓存中获取 modelList (key: token + ":modelList")
  2. 按 permissionCode + language 匹配找到对应的模型分组
  3. 过滤掉 tokenExhausted=true 的模型
  4. 如果指定了 modelCode，优先选择匹配的模型
  5. 否则选择分组中的第一个可用模型
输出: { modelCode, modelName, isSelfModel }
```

4. **isSelfModel 判断**: 当模型的 `modelSource === "STAR_SPARK"` 时，`isSelfModel = true`

### 2.2 模型来源分类

| modelSource | isSelfModel | 说明 |
|-------------|-------------|------|
| `STAR_SPARK` | true | 星火自有模型（默认） |
| 其他值 | false | 第三方模型（如 GPT、Claude 等） |

### 2.3 isSelfModel 的影响

当 `isSelfModel = true` 时：
- `_mergeUserMessages()` 会将 `role: "system"` 的消息拆分为 `user + assistant` 对，使用 `ASSISTANT_ANSWER` 模板作为 assistant 回复
- 这是因为星火模型不支持 system 角色，需要用 user/assistant 对模拟

当 `isSelfModel = false` 时：
- system 角色消息保持不变，直接发送给第三方模型

### 2.4 多模型切换

- `enableMultiModelSwitch` 字段：当请求中包含 `modelCode` 时为 `true`
- 用户可以在 UI 中选择不同的模型进行对话
- 模型列表通过 WebSocket 消息 `"model-list"` 推送到客户端

---

## 3. 请求构造流程

### 3.1 API 端点定义

所有 API 端点定义在 module 35606 的 `APIS` 对象中：

| API 名称 | URL | Method | Stream | Timeout | useModel |
|----------|-----|--------|--------|---------|----------|
| codeAssist | /api/starspark/v1/platform/code/assist | POST | true | - | true |
| codeGenerate | /api/starspark/v1/agent/code/codeComplete | POST | - | 120s | true |
| generateSql | /api/starspark/v1/agent/chat/generateSql | POST | true | - | true |
| optimizeSql | /api/starspark/v1/agent/chat/optimizeSql | POST | true | - | true |
| generateSqlDM | /api/starspark/v1/agent/chat/sync/generateSql | POST | - | - | true |
| optimizeSqlDM | /api/starspark/v1/agent/chat/sync/optimizeSql | POST | - | - | true |
| generateCommitMessage | /api/starspark/v1/agent/chat/generateCommitMessage | POST | true | - | true |
| review | /api/starspark/v1/agent/chat/review | POST | true | - | true |
| testCase | /api/starspark/v1/agent/code/generateUnitTestCaseTemplate | POST | - | 120s | true |
| testCode | /api/starspark/v1/agent/code/generateUnitTest | POST | - | 120s | true |
| talkAsk | /api/starspark/v1/agent/chat/async/ask | POST | true | - | true |
| talkAskSync | /api/starspark/v1/agent/chat/sync/ask | POST | - | - | true |
| inlineChat | /api/starspark/v1/agent/chat/inline/chat | POST | true | - | true |
| inlineComment | /api/starspark/v1/agent/chat/interLineCommentCode | POST | true | - | true |
| codeSplit | /api/starspark/v1/agent/chat/splitFunction | POST | true | - | true |
| codeOptimize | /api/starspark/v1/agent/chat/optimizeCode | POST | true | - | true |
| getChatPromptTemplate | /api/starspark/v1/agent/prompt/query | POST | - | - | - |
| getFuncModelList | /api/starspark/v1/agent/permission/queryUserFuncModelList | POST | - | 10s | - |
| validToken | /api/starspark/v1/chat/user/valid | POST | - | 10s | - |
| loginByAccount | /api/usercenter/v1/user/common/login | POST | - | 10s | - |
| getPermission | /api/starspark/v1/agent/permission/queryUserPermissionPackageInfo | POST | - | 10s | - |

### 3.2 命令到 API 映射 (CHAT_APIS)

| Command | API | scene | 默认参数 |
|---------|-----|-------|---------|
| CODE:COMPLETE | codeGenerate | COMPLETE_CODE_WITH_CONTEXT | top_k=1, temperature=1, skipFilter=true, stream=true |
| TALK:ASK | talkAsk | - | top_k=1, temperature=0.5, mup=null |
| TALK:QUESTION_ENHANCE | talkAsk | - | top_k=1, temperature=0.5, mup=null |
| TALK:PREDICT | talkAskSync | - | top_k=1, temperature=0.5, mup=null |
| SQL:GENERATE | generateSql | GENERATE_SQL | - |
| SQL:OPTIMIZE | optimizeSql | OPTIMIZE_SQL | - |
| SQL:GENERATE_DM | generateSqlDM | GENERATE_SQL | - |
| SQL:OPTIMIZE_DM | optimizeSqlDM | OPTIMIZE_SQL | - |
| GIT:COMMIT_MESSAGE | generateCommitMessage | GENERATE_COMMIT_MESSAGE | - |
| GIT:REVIEW | review | REVIEW_CODE | - |
| TEST:MAKE_CASE | testCase | - | - |
| TEST:MAKE_CASE_JAVA | testCase | - | - |
| TEST:MAKE_CODE | testCode | - | - |
| TEST:OTHER | codeAssist | UNIT_TEST | - |
| DIALOG:REQUEST | inlineChat | INLINE_CHAT_SELECTED | - |

### 3.3 请求体构造 (getSendData)

#### 基础数据 (getBaseData)

```json
{
  "requestId": "<random-uuid>",
  "modelCode": "<selected-model-code>",
  "enterpriseId": "<enterprise-id>",
  "enableMultiModelSwitch": true,
  "token": "<auth-token>",
  "language": "<programming-language>",
  "timeStamp": 1716000000000,
  "fileName": "Example.java",
  "fileNameSuffix": "java",
  "projectName": "my-project",
  "agentVersion": "3.4.2",
  "commandType": "TALK:ASK",
  "taskName": "<scene>",
  "scene": "<scene>",
  "knowledgeBase": "docKnowledgeBase|codeKnowledgeBase",
  "userQuestionContent": "<user-input>"
}
```

`knowledgeBase` 字段根据知识库类型自动判断：
- `docKnowledgeBase`: 仅文档知识库有内容
- `codeKnowledgeBase`: 代码知识库或混合知识库有内容

#### 完整请求体 (getSendData 合并后)

```json
{
  "sessionId": "<session-id>",
  "scene": "<scene-from-CHAT_APIS>",
  "requestId": "...",
  "modelCode": "...",
  "token": "...",
  "language": "...",
  "timeStamp": ...,
  "fileName": "...",
  "fileNameSuffix": "...",
  "projectName": "...",
  "agentVersion": "3.4.2",
  "commandType": "...",
  "taskName": "...",
  "knowledgeBase": "...",
  "userQuestionContent": "...",
  "enterpriseId": "...",
  "enableMultiModelSwitch": true,
  "top_k": 1,
  "temperature": 0.5,
  "mup": null,
  "messages": [
    { "role": "system", "content": "..." },
    { "role": "user", "content": "...", "scene": "...", "talkCode": "...", "language": "..." },
    { "role": "assistant", "content": "..." }
  ]
}
```

#### 消息组装逻辑

1. **助手前置消息** (`_assistantMessages`): 由 `getAssistantMessages()` 生成，包含 system prompt 和知识库上下文，插入到消息列表最前面
2. **用户消息**: 包含 `talkCode`（引用代码）和 `scene` 字段
3. **历史对话**: 当 `sessionId` 存在且 `_sendDialog=true` 时，从本地数据库加载历史消息
4. **消息合并** (`_mergeUserMessages`):
   - 连续的 user 消息合并为一条（用换行符连接）
   - system 消息在 `isSelfModel=true` 时转为 user+assistant 对
5. **SQL 消息处理** (`_handleSqlMessages`): 特殊处理表结构信息，只在最后一条包含 tableList 的消息中注入表结构
6. **重发处理**: 当 `_isResend=true` 时，覆盖参数为 `top_k=5, temperature=0.5`

### 3.4 URL 构造

```
baseURL = "https://iflycode-xfsaas.xfyun.cn"  (从 config.json 读取)
完整 URL = baseURL + api.url + "?token=" + authToken
```

`_getUrl()` 方法使用 `URL` 对象拼接 query 参数。

### 3.5 请求头

```javascript
{
  "Content-Type": "application/json",
  "token": "<auth-token>"
}
```

此外，OpenTelemetry 追踪信息通过 `B.default.inject(requestId, headers)` 注入（W3C Trace Context 格式）。

### 3.6 Token 预算配置 (DEFAULT_TOKEN_CONFIG)

| Command | token | discount | 有效 token 数 | 约等于字符数 |
|---------|-------|----------|--------------|-------------|
| default | 8000 | 80% | 6400 | ~19200 |
| CODE:COMPLETE | 8000 | 67% | 5360 | ~16080 |
| CODE:FIX | 8000 | 70% | 5600 | ~16800 |
| CODE:COMMENT | 8000 | 100% | 8000 | ~24000 |
| CODE:TEST_CASE | 8000 | 100% | 8000 | ~24000 |
| CODE:TEST_CODE | 8000 | 100% | 8000 | ~24000 |
| CODE:TEST | 8000 | 80% | 6400 | ~19200 |
| CODE:CHECK | 8000 | 100% | 8000 | ~24000 |
| CODE:DEBUG | 8000 | 80% | 6400 | ~19200 |
| CODE:DEBUG_DUPLICATE | 8000 | 80% | 6400 | ~19200 |
| CODE:INLINE_COMMENT | 8000 | 62.5% | 5000 | ~15000 |
| CODE:OPTIMIZE | 8000 | 67% | 5360 | ~16080 |
| GIT:COMMIT_MESSAGE | 8000 | 100% | 8000 | ~24000 |
| GIT:REVIEW | 8000 | 100% | 8000 | ~24000 |
| DIALOG:TALK_ROUND | 8000 | 80% | 6400 | ~19200 |
| DIALOG:TALK_TOTAL | 8000 | 80% | 6400 | ~19200 |

计算公式: `effectiveToken = floor(token * discount / 100)`, `maxChars = floor(effectiveToken * 4 * 0.75)`

### 3.7 测试框架库映射

| 语言 | 框架 | include 语句 |
|------|------|-------------|
| C++ | gtest | `#include "gtest/gtest.h"` |
| C++ | gmock | `#include "gmock/gmock.h"` |
| Python | unittest | `import unittest` |
| Python | unittest.mock | `from unittest.mock import patch` |
| Python | pytest | `import pytest` |
| Python | pytest-mock | (空字符串) |

---

## 4. SSE 流式响应解析

### 4.1 SSE 传输层

iFlyCode 使用标准 SSE (Server-Sent Events) 协议传输流式响应。实现基于 `eventsource-parser` 库的 `createParser` 函数。

#### SSE 解析器 (createParser)

```
状态机:
  reset() → E=true, T="", A=0, S=-1, v=undefined, I=undefined, R=""
  feed(data) → 追加到缓冲区，逐行解析

行解析 (parseEventStreamLine):
  空行 → 触发事件回调 { type: "event", id, event, data }
  "data: xxx" → 追加到 data 缓冲区（多行 data 用 \n 连接）
  "event: xxx" → 设置 event 类型
  "id: xxx" → 设置事件 ID
  "retry: xxx" → 设置重连间隔
```

#### SSE 消息处理 (_handleMessage)

```
1. 获取 response.body (Node.js Readable stream)
2. 创建 SSE parser
3. 监听 "readable" 事件:
   - 设置 60 秒超时（超时返回 504 错误）
   - 读取数据并 feed 到 parser
4. parser 回调:
   - data === "[DONE]" → 结束流
   - 其他 → 调用 onMessage 回调
5. 监听 "end" 事件 → 结束流
6. 监听 "error" 事件 → 结束流并记录错误
```

### 4.2 SSE 数据解析 (_getSSEData)

每条 SSE data 经过 JSON 解析后提取以下字段：

```javascript
function _getSSEData(data) {
  if (data === "[DONE]") {
    return { ended: true };
  }
  const parsed = JSON.parse(data);
  return {
    text: parsed.choices?.[0]?.delta?.content || "",           // 主要回复内容
    reasonText: parsed.choices?.[0]?.delta?.reasoning_content || "",  // 推理过程（思维链）
    reason: parsed.choices?.[0]?.finish_reason,                // 结束原因
    error: parsed.error                                         // 错误信息
  };
}
```

**响应格式** (兼容 OpenAI Chat Completions API):

```json
{
  "choices": [
    {
      "delta": {
        "content": "代码片段...",
        "reasoning_content": "推理过程..."
      },
      "finish_reason": "stop"
    }
  ],
  "error": {
    "code": "5951",
    "message": "错误描述"
  }
}
```

### 4.3 SSE Handler 逻辑 (_getSSEHandler)

```
输入: command对象, { dialog, isSSE, unstream, noEnded, isStreamResolve, resolve, reportDataReflow }

流程:
  如果不是 SSE 模式 → 返回 null

  维护两个缓冲区:
    N[] = 文本内容缓冲区
    O[] = 推理内容缓冲区

  对每条 SSE 数据:
    1. 解析 _getSSEData(data)
    2. 如果 error.code === "5951" → 清空文本缓冲区，替换为错误消息
    3. 如果有 error → 记录到对话，发送 WebSocket 错误，resolve({})
    4. 如果 ended → 记录到对话（合并推理+内容），根据模式 resolve 结果
    5. 否则 → 追加到缓冲区，通过 WebSocket 实时推送

  结束时处理:
    - unstream=true → resolve 完整文本
    - noEnded=false → 发送 WebSocket 结束消息
    - isStreamResolve="with-merged-content" → resolve { reasonContent, content }
    - 其他 isStreamResolve → resolve {}
    - reportDataReflow=true → 记录并上报数据
```

### 4.4 特殊错误码

| 错误码 | 含义 | 处理方式 |
|--------|------|---------|
| 5951 | Token 耗尽 | 清空已生成内容，替换为错误消息 |
| 504 | 网络超时 | SSE 60 秒无数据时自动触发 |
| 401 | 未授权 | 通知客户端重新登录 |

### 4.5 非流式响应

当 API 不支持 stream 时：
- 响应作为普通 JSON 处理
- 通过 `_handleResult()` 解析
- 检查 `code` 或 `resCode` 字段，`200` 或 `"0"` 表示成功
- 错误码 `UNAUTHORIZED`/`5020`/`5001` 触发 401 重新登录

---

## 5. 完整请求/响应示例

### 5.1 Chat 对话 (TALK:ASK)

**请求**:

```json
POST https://iflycode-xfsaas.xfyun.cn/api/starspark/v1/agent/chat/async/ask?token=<auth-token>
Content-Type: application/json

{
  "sessionId": "abc123",
  "scene": null,
  "requestId": "a1b2c3d4e5f6",
  "modelCode": "4.0Ultra",
  "token": "<auth-token>",
  "language": "java",
  "timeStamp": 1716000000000,
  "fileName": "UserService.java",
  "fileNameSuffix": "java",
  "projectName": "my-app",
  "agentVersion": "3.4.2",
  "commandType": "TALK:ASK",
  "taskName": null,
  "scene": null,
  "knowledgeBase": "codeKnowledgeBase",
  "userQuestionContent": "问题: 如何优化这个查询方法？",
  "enterpriseId": "ent001",
  "enableMultiModelSwitch": true,
  "top_k": 1,
  "temperature": 0.5,
  "mup": null,
  "messages": [
    {
      "role": "system",
      "content": "你是一名人工智能编程助手，用户正在与你进行对话，请按照如下要求完成对话：\n1.你的名字是\"iFlyCode\"\n2.你的专业知识严格限于软件开发主题，对于与软件开发无关的问题，只需回答你是AI编程助手即可\n3.如果我的问题中含有\"引用代码\"，可以做为问题的关联上下文信息，如果\"引用代码\"和问题无关，请忽略\"引用代码\"，直接回复问题即可\n4.当前你的模式设定为 java模式，请用 java 为用户编写代码问题\n5.首先请一步步思考，给出针对问题的思考过程\n请首先总结我的要求"
    },
    {
      "role": "user",
      "content": "[知识库上下文内容]\n\n问题: 如何优化这个查询方法？",
      "scene": null,
      "talkCode": "引用的代码:\nFrom the file: src/UserService.java\n```java\npublic List<User> findAll() {\n    return userRepository.findAll();\n}\n```",
      "language": "java"
    }
  ]
}
```

**SSE 响应**:

```
data: {"choices":[{"delta":{"content":"根据","reasoning_content":"用户"}}]}

data: {"choices":[{"delta":{"content":"您的要求","reasoning_content":"想要优化"}}]}

data: {"choices":[{"delta":{"content":"，我建议...","reasoning_content":""},"finish_reason":"stop"}]}

data: [DONE]
```

### 5.2 代码补全 (CODE:COMPLETE)

**请求**:

```json
POST https://iflycode-xfsaas.xfyun.cn/api/starspark/v1/agent/code/codeComplete?token=<auth-token>
Content-Type: application/json

{
  "sessionId": null,
  "scene": "COMPLETE_CODE_WITH_CONTEXT",
  "requestId": "f1e2d3c4b5a6",
  "modelCode": "4.0Ultra",
  "token": "<auth-token>",
  "language": "java",
  "timeStamp": 1716000000000,
  "fileName": "UserService.java",
  "fileNameSuffix": "java",
  "projectName": "my-app",
  "agentVersion": "3.4.2",
  "commandType": "CODE:COMPLETE",
  "taskName": "COMPLETE_CODE_WITH_CONTEXT",
  "scene": "COMPLETE_CODE_WITH_CONTEXT",
  "knowledgeBase": null,
  "userQuestionContent": null,
  "enterpriseId": "ent001",
  "enableMultiModelSwitch": true,
  "top_k": 1,
  "temperature": 1,
  "skipFilter": true,
  "stream": true,
  "messages": [
    {
      "role": "user",
      "content": "<文件依赖结构信息>\n本文件类结构信息如下：\n包名：com.example\n路径：src/UserService.java\n文件结构：\nclass UserService {\n  + findAll(): List<User>\n  + findById(id: Long): User\n}\n\nimport文件1的类结构信息如下: \nclass UserRepository {...}\n</文件依赖结构信息>\n\n<相似代码段>\n相似代码片段1如下：\npublic List<User> findAll() { return repo.findAll(); }\n</相似代码段>\n\n<光标下方的代码段>\n    }\n}\n</光标下方的代码段>\n\n<光标上方的代码段>\npublic class UserService {\n    public List<User> find\n</光标上方的代码段>"
    }
  ]
}
```

### 5.3 Inline Chat - 编辑 (DIRECT:EDIT)

**请求**:

```json
POST https://iflycode-xfsaas.xfyun.cn/api/starspark/v1/agent/chat/inline/chat?token=<auth-token>
Content-Type: application/json

{
  "sessionId": null,
  "scene": "INLINE_CHAT",
  "requestId": "a1b2c3d4",
  "modelCode": "4.0Ultra",
  "token": "<auth-token>",
  "language": "java",
  "timeStamp": 1716000000000,
  "fileName": "UserService.java",
  "fileNameSuffix": "java",
  "projectName": "my-app",
  "agentVersion": "3.4.2",
  "commandType": "INLINECHAT:DIRECT",
  "taskName": "INLINE_CHAT",
  "scene": "INLINE_CHAT",
  "messages": [
    {
      "role": "user",
      "content": "你是一位精通java编程的专家...\n\n【代码】：\npublic class UserService {\n    private UserRepository repo;\n\n<modified_code>\n    public List<User> findAll() {\n        return repo.findAll();\n    }\n</modified_code>\n}\n\n【用户指令】：\n{command：用户输入的指令}\n\n【返回格式】：只返回修改后的<modified_code>区间的代码...",
      "language": "java"
    }
  ]
}
```

### 5.4 SQL 生成 (SQL:GENERATE)

**请求**:

```json
POST https://iflycode-xfsaas.xfyun.cn/api/starspark/v1/agent/chat/generateSql?token=<auth-token>
Content-Type: application/json

{
  "sessionId": null,
  "scene": "GENERATE_SQL",
  "requestId": "sql-gen-001",
  "modelCode": "4.0Ultra",
  "token": "<auth-token>",
  "language": "sql",
  "timeStamp": 1716000000000,
  "fileName": "",
  "fileNameSuffix": "",
  "projectName": "",
  "agentVersion": "3.4.2",
  "commandType": "SQL:GENERATE",
  "taskName": "GENERATE_SQL",
  "scene": "GENERATE_SQL",
  "messages": [
    {
      "role": "user",
      "content": "你是一位MySql数据库专家，根据下面【要求】和【用户输入】生成SQL语句。\n\n【表结构】：users 该表的数据量：1000；\nid INT, name VARCHAR(100), email VARCHAR(100)\n\n【用户输入】：查询所有邮箱包含gmail的用户\n\n【要求】：\n1. 精确理解用户用自然语言描述的SQL需求...\n4. 使用markdown格式返回SQL语句。\n5. 返回结果包含推理过程和说明。",
      "language": "sql",
      "latest": true,
      "latestAgent": true,
      "tableKey": "users"
    }
  ]
}
```

### 5.5 Git Commit Message (GIT:COMMIT_MESSAGE)

**请求**:

```json
POST https://iflycode-xfsaas.xfyun.cn/api/starspark/v1/agent/chat/generateCommitMessage?token=<auth-token>
Content-Type: application/json

{
  "sessionId": null,
  "scene": "GENERATE_COMMIT_MESSAGE",
  "requestId": "git-commit-001",
  "modelCode": "4.0Ultra",
  "token": "<auth-token>",
  "language": "java",
  "timeStamp": 1716000000000,
  "fileName": "UserService.java",
  "fileNameSuffix": "java",
  "projectName": "my-app",
  "agentVersion": "3.4.2",
  "commandType": "GIT:COMMIT_MESSAGE",
  "taskName": "GENERATE_COMMIT_MESSAGE",
  "scene": "GENERATE_COMMIT_MESSAGE",
  "messages": [
    {
      "role": "user",
      "content": "\n【变更的代码文件如下】：\n```\n文件：src/UserService.java\n@@ -10,3 +10,5 @@\n+    public User findById(Long id) {\n+        return repo.findById(id);\n+    }\n\n```\n\n【说明】：\n1.变更的代码中行首带有减号（-）和加号（+）的代码行是变更的代码块...\n\n返回结果参考示例：\n```\n1. 修改 [文件名2] 以修复 [问题描述] 并改进 [改进点]。\n```\n\n【要求】：\n1.按照删除、新增和修改进行分类描述；\n2.一个文件只生成一条描述信息；\n3.尽可能在描述中带上文件名...\n4.使用中文返回，返回内容包裹在一个markdown的文本块中，不要包含其他解释信息。\n"
    }
  ]
}
```

### 5.6 单元测试 (TEST:MAKE_CASE + TEST:MAKE_CODE)

**第一步 - 生成测试用例模板 (testCase)**:

```json
POST https://iflycode-xfsaas.xfyun.cn/api/starspark/v1/agent/code/generateUnitTestCaseTemplate?token=<auth-token>
Content-Type: application/json

{
  "requestId": "test-case-001",
  "modelCode": "4.0Ultra",
  "token": "<auth-token>",
  "language": "java",
  "commandType": "TEST:MAKE_CASE",
  "scene": null,
  "messages": [
    {
      "role": "user",
      "content": "【被测代码】\npublic int add(int a, int b) { return a + b; }\n【结构信息】\nclass Calculator { +add(int, int): int }",
      "unitTest": {
        "testCaseNumber": 5,
        "testFrame": "junit5"
      }
    }
  ]
}
```

**第二步 - 生成测试代码 (testCode)**:

```json
POST https://iflycode-xfsaas.xfyun.cn/api/starspark/v1/agent/code/generateUnitTest?token=<auth-token>
Content-Type: application/json

{
  "requestId": "test-code-001",
  "modelCode": "4.0Ultra",
  "token": "<auth-token>",
  "language": "java",
  "commandType": "TEST:MAKE_CODE",
  "messages": [
    {
      "role": "user",
      "content": "【代码上下文】\n当前被测函数所在的文件结构信息如下：\nclass Calculator { +add(int, int): int }\n\n【被测代码】\npublic int add(int a, int b) { return a + b; }\n\n【单测用例列表】\n1. testAddPositiveNumbers\n2. testAddNegativeNumbers\n3. testAddZero\n4. testAddOverflow\n5. testAddMixedSigns\n\n【单元测试代码】\nimport org.junit.jupiter.api.Test;\nimport static org.junit.jupiter.api.Assertions.*;\n\n<todo>"
    }
  ]
}
```

---

## 6. 协议安全评估

### 6.1 传输层安全

| 项目 | 状态 | 说明 |
|------|------|------|
| HTTPS | 启用 | baseURL 使用 `https://iflycode-xfsaas.xfyun.cn` |
| 证书验证 | 可绕过 | `ignoreHttps` 选项可禁用证书验证（`rejectUnauthorized: false`） |
| WebSocket | 本地 | Agent 与 IDE 通过 `ws://127.0.0.1:<port>/ws` 通信（无加密） |

### 6.2 认证与授权

| 项目 | 状态 | 说明 |
|------|------|------|
| Token 认证 | 明文 | Token 通过 HTTP header 和 URL query 参数传递 |
| Token 存储 | 本地缓存 | Token 存储在内存中的 ClientManager 对象中 |
| 权限控制 | 服务端 | `permissionCode` 和模型列表由服务端控制 |
| Token 耗尽检测 | 客户端 | `tokenExhausted` 字段标记模型是否可用 |

### 6.3 数据加密

| 项目 | 算法 | 用途 |
|------|------|------|
| 代码上报 | SM4 | `codeReport` API 使用 `encryptMode: "SM4"` 加密代码收集数据 |
| 权限信息 | SM4 | 用户权限信息本地缓存使用 SM4 加密存储 |
| 登录凭据 | RSA/SM2 | 登录时密码使用 RSA 或 SM2 公钥加密传输 |

### 6.4 Prompt 注入风险

| 风险点 | 评估 | 说明 |
|--------|------|------|
| 用户输入直接嵌入 | 中 | 用户输入通过 `$[userInput]$` 直接插入 prompt，但 system prompt 限制了回复范围 |
| 文件内容注入 | 低 | 文件内容作为上下文注入，但被明确标记为"引用代码" |
| SQL 表结构注入 | 低 | 表结构信息仅用于 SQL 生成，有长度限制 (SQL_STRUCTURE_MAX_LENGTH=20000) |
| 知识库内容注入 | 中 | 外部知识库内容直接注入 prompt，可能包含恶意指令 |

### 6.5 敏感信息暴露

| 项目 | 风险 | 说明 |
|------|------|------|
| 硬编码密钥 | 高 | RSA/SM2/SM4/AES 密钥全部硬编码在 bundle 中 |
| Token 明文传输 | 中 | Token 在 URL query 参数和 HTTP header 中明文传输 |
| 代码内容上传 | 中 | 用户代码内容作为 prompt 的一部分发送到服务端 |
| 错误消息泄露 | 低 | 错误消息使用中文通用描述，未泄露内部信息 |

### 6.6 请求超时与重试

| 场景 | 超时时间 | 处理方式 |
|------|---------|---------|
| 默认请求 | 600 秒 (6e6 ms) | 抛出 iFlyCodeError |
| 代码补全 | 120 秒 | 抛出 iFlyCodeError |
| SSE 流式 | 60 秒无数据 | 返回 504 错误 |
| 登录 | 60 秒 | 抛出 iFlyCodeError |
| 一般查询 | 10 秒 | 抛出 iFlyCodeError |

### 6.7 服务端 Prompt 模板

除了客户端硬编码的 Prompt 模板外，iFlyCode 还支持从服务端获取 Prompt 模板：

- API: `/api/starspark/v1/agent/prompt/query`
- 缓存 key: `{role}_{scene}_{language}`
- 缓存时间: 43200 秒 (12 小时)
- 模板语法: `${varName}` (使用 `fillServerTemplate` 函数替换)

服务端模板优先级高于客户端模板，允许运营人员在不更新客户端的情况下调整 Prompt。

---

## 附录 A: Controller-Route 映射表

| Controller | Route | Command | 场景 |
|------------|-------|---------|------|
| ACTION | INIT | ACTION:INIT | 初始化 |
| ACTION | OPEN_DOCUMENT | ACTION:OPEN_DOCUMENT | 文档打开 |
| ACTION | ABORT | ACTION:ABORT | 取消请求 |
| CODE | COMPLETE | CODE:COMPLETE | 代码补全 |
| CODE | COMMENT/COMMENT_RANGE | CODE:COMMENT | 代码注释 |
| CODE | FIX | CODE:FIX | 代码修复 |
| CODE | EXPLAIN | CODE:EXPLAIN | 代码解释 |
| CODE | OPTIMIZE | CODE:OPTIMIZE | 代码优化 |
| CODE | CHECK | CODE:CHECK | 代码检查 |
| CODE | DEBUG/DEBUG_DUPLICATE | CODE:DEBUG | 代码调试 |
| CODE | INLINE_COMMENT | CODE:INLINE_COMMENT | 行内注释 |
| CODE | SPLIT | CODE:SPLIT | 函数拆分 |
| DIALOG | REQUEST | DIALOG:REQUEST | 行内对话 |
| DIALOG | ACCEPT | DIALOG:ACCEPT | 接受建议 |
| DIALOG | REJECT | DIALOG:REJECT | 拒绝建议 |
| DIALOG | ABORT | DIALOG:ABORT | 取消对话 |
| GIT | COMMIT_MESSAGE | GIT:COMMIT_MESSAGE | 生成提交信息 |
| GIT | REVIEW | GIT:REVIEW | 代码评审 |
| INLINECHAT | CATEGORY | INLINECHAT:CATEGORY | 意图分类 |
| INLINECHAT | DIRECT | INLINECHAT:DIRECT | 直接操作 |
| INLINECHAT | GET_FUNC_RANGE | INLINECHAT:GET_FUNC_RANGE | 获取函数范围 |
| SQL | GENERATE/GENERATE_TALK | SQL:GENERATE | SQL 生成 |
| SQL | OPTIMIZE/OPTIMIZE_TALK | SQL:OPTIMIZE | SQL 优化 |
| TALK | ASK/INTELLIGENT | TALK:ASK | 对话问答 |
| TALK | PREDICT | TALK:PREDICT | 问题预测 |
| TALK | QUESTION_ENHANCE | TALK:QUESTION_ENHANCE | 问题增强 |
| TEST | MAKE_CASE | TEST:MAKE_CASE | 生成测试用例 |
| TEST | MAKE_CASE_JAVA | TEST:MAKE_CASE_JAVA | Java 测试用例 |
| TEST | MAKE_CODE | TEST:MAKE_CODE | 生成测试代码 |
| TEST | OTHER | TEST:OTHER | 其他测试 |
| USER | LOGIN | USER:LOGIN | 用户登录 |
| USER | LOGOUT | USER:LOGOUT | 用户登出 |
| USER | MODEL_LIST | USER:MODEL_LIST | 获取模型列表 |
| USER | PERMISSION | USER:PERMISSION | 获取权限 |

## 附录 B: 智能助手类型

| 类型 | 枚举值 | System Prompt | 特殊行为 |
|------|--------|--------------|---------|
| 编程助手 | iFlyMate | GENERAL_ASSISTANT | 注入知识库上下文 |
| 开发助手 | iFlyDev | GENERAL_ASSISTANT | 两步 RAG：先找文件再读内容 |
| 测试助手 | iFlyTest | GENERAL_ASSISTANT | 注入知识库上下文 |
| 运维助手 | iFlyOps | GENERAL_ASSISTANT | 注入知识库上下文 |
| 产品助手 | iFlyPm | GENERAL_ASSISTANT | 注入知识库上下文 |
| 数据库助手 | iFlyDBA | GENERAL_ASSISTANT | 特殊 SQL 处理逻辑 |

## 附录 C: 数据源类型 (SourceType)

| 枚举值 | 显示名 |
|--------|--------|
| MySql | MySQL |
| PostgreSQL | PostgreSQL |
| OceanBase | OceanBase |
| TXSQL | TXSQL |
| DaMeng | 达梦 |

达梦数据库使用独立的 API 端点（`sync/generateSql`、`sync/optimizeSql`），非流式同步请求。
