## 4. WebView→Java JS Bridge 消息格式

### 4.1 消息发送机制 (三种 IDE)

#### IDEA (JCEF)

```javascript
// JS → Java
window.myObject.sendMessage(JSON.stringify(&#123; type, value &#125;))

// Java → JS
CefBrowser.executeJavaScript("window.receiveData(" + json + ")")
```

#### VSCode

```javascript
// JS → Java
vscode.postMessage(&#123; type, value: JSON.stringify(value) &#125;)

// Java → JS
window.addEventListener("message", (event) => &#123;
  handlerReceivedMsg(event.data.type, event.data.value)
&#125;)
```

#### Eclipse

```javascript
// JS → Java
window.sendMessage(JSON.stringify(&#123; type, value: JSON.stringify(value) &#125;))

// Java → JS
window.receiveData = function(data) &#123;
  handlerReceivedMsg(JSON.parse(data).type, JSON.parse(data).value)
&#125;
```

### 4.2 JS→Java 消息完整映射表

从 WebView 源码提取的所有 JS→Java 消息类型:

#### CHAT 模块 (14 条)

| 消息类型 | value 格式 | 敏感数据 | 文件来源 |
|---------|-----------|---------|---------|
| `CHAT:SEND_MSG` | `&#123;inputText, type, intelligent, sessionId, knowledge, relatedFiles, code&#125;` | 用户输入, 代码 | sendMsgMode |
| `CHAT:RESEND` | `&#123;id, sessionId, type&#125;` | 无 | sendMsgMode |
| `CHAT:STOP_RESPONSE` | `&#123;sessionId&#125;` | 无 | sendMsgMode |
| `CHAT:SET_MODEL` | `modelCode` | 无 | sendMsgMode |
| `CHAT:REFRESH_MODEL` | 无 | 无 | sendMsgMode |
| `CHAT:DELETE_MSG` | `&#123;messageId&#125;` | 无 | sendMsgMode |
| `CHAT:DELETE_HISTORY_ITEM` | `&#123;sessionId&#125;` | 无 | index-f0296668 |
| `CHAT:DELETE_HISTORY_ITEM_ALL` | `&#123;&#125;` | 无 | index-f0296668 |
| `CHAT:GET_HISTORY_LIST` | 无 | 无 | index-f0296668 |
| `CHAT:CHOOSE_HISTORY_ITEM` | `sessionId` | 无 | index-df569310 |
| `CHAT:NEW_CHAT` | 无 | 无 | index-f0296668 |
| `CHAT:GET_IDE_FILE_STATE` | `&#123;isRecommend?, isGetData?&#125;` | 无 | sendMsgMode |
| `CHAT:GET_CODE_KNOWLEDGE_LIST` | 无 | 无 | sendMsgMode |
| `CHAT:GET_DOC_KNOWLEDGE_LIST` | 无 | 无 | sendMsgMode |
| `CHAT:CHOOSE_FILE` | 无 | 无 | sendMsgMode |
| `CHAT:GET_OPEN_DIR_LIST` | 无 | 无 | sendMsgMode |
| `CHAT:VALID_WEBSITE` | `url` | URL | sendMsgMode |
| `CHAT:AGENT_REFRESH` | 无 | 无 | index-df569310 |

#### SQL_CHAT 模块 (8 条)

| 消息类型 | value 格式 | 敏感数据 | 文件来源 |
|---------|-----------|---------|---------|
| `SQL_CHAT:SEND_MSG` | `&#123;intelligent, params: &#123;sqlInfo&#125;&#125;` | SQL 查询 | sendMsgMode |
| `SQL_CHAT:SQL_LINK_TEST` | `&#123;client, host, port, user, password, database&#125;` | **数据库密码** | index-3c7ef179 |
| `SQL_CHAT:SQL_SAVE` | `&#123;sourceId, client, host, port, user, password, database&#125;` | **数据库密码** | index-3c7ef179 |
| `SQL_CHAT:SOURCE_LIST` | `&#123;sourceId?, refreshFlag?&#125;` | 无 | index-3c7ef179 |
| `SQL_CHAT:TABLE_LIST` | `&#123;sourceId, database&#125;` | 无 | index-3c7ef179 |
| `SQL_CHAT:REQUEST_SOURCE_TYPES` | 无 | 无 | index-3c7ef179 |
| `SQL_CHAT:SOURCE_DELETE` | `sourceId` | 无 | index-3c7ef179 |
| `SQL_CHAT:NEW_CHAT` | 无 | 无 | index-3c7ef179 |
| `SQL_CHAT:STOP_RESPONSE` | 无 | 无 | sendMsgMode |

#### CODE_CHECK 模块 (2 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `CODE_CHECK:REQUEST_CODE_CHECK_LIST` | 无 | 无 |
| `CODE_CHECK:FIX` | `&#123;issueId, fixCode&#125;` | 修复代码 |

#### CODE_REVIEW 模块 (4 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `CODE_REVIEW:PAGE_READY` | 无 | 无 |
| `CODE_REVIEW:GET_CODEREVIEW_LIST` | `&#123;path&#125;` | 无 |
| `CODE_REVIEW:GET_CHANGE_RESULT` | `&#123;filePath, changeId&#125;` | 无 |
| `CODE_REVIEW:GET_CHANGE_RESULT_END` | `true` | 无 |

#### CODE_SEARCH 模块 (3 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `CODE_SEARCH:REQUEST_CODESEARCH_CODE_LIST` | `&#123;query, repo, language&#125;` | 无 |
| `CODE_SEARCH:REQUEST_CODESEARCH_REPOSITORY_LIST` | 无 | 无 |
| `CODE_SEARCH:REQUEST_CODESEARCH_LANGUAGE_LIST` | 无 | 无 |

#### UNIT_TEST 模块 (7 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `UNIT_TEST:PAGE_READY` | 无 | 无 |
| `UNIT_TEST:FUNCTION_CASE` | `&#123;filePath, className, methodName&#125;` | 无 |
| `UNIT_TEST:FUNCTION_CASE_CODE` | `&#123;caseId&#125;` | 无 |
| `UNIT_TEST:SAVE_CODE` | `&#123;filePath, testCode&#125;` | 测试代码 |
| `UNIT_TEST:REGENERATE` | `&#123;params&#125;` | 无 |
| `UNIT_TESTING:MAPPING_FILE` | `&#123;params&#125;` | 无 |
| `UNIT_TEST:SAVE` | `&#123;params&#125;` | 无 |

#### BATCH_UNIT_TEST 模块 (4 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `BATCH_UNIT_TEST:CREATE` | `&#123;filePaths, options&#125;` | 无 |
| `BATCH_UNIT_TEST:GET_LIST` | 无 | 无 |
| `BATCH_UNIT_TEST:DOWNLOAD` | `taskId` | 无 |
| `BATCH_UNIT_TEST:DELETE` | `taskId` | 无 |

#### UNIT_TEST_BANK 模块 (2 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `UNIT_TEST_BANK:PAGE_READY` | 无 | 无 |
| `UNIT_TEST_BANK:SAVE` | `&#123;params&#125;` | 无 |

#### GIT 模块 (4 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `GIT:GET_STATUS` | `&#123;&#125;` | 无 |
| `GIT:SAVE_TOKEN` | `&#123;token, repoType&#125;` | **Git Token** |
| `GIT:AUTHORIZE` | `&#123;token, repoType&#125;` | **Git Token** |
| `GIT:RE_INDEX` | `&#123;params&#125;` | 无 |

#### LOGIN 模块 (4 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `LOGIN:INIT` | `&#123;showInfo: true&#125;` | 无 |
| `LOGIN:LOGIN_ABORT` | 无 | 无 |
| `LOGIN:LOGIN_CHECK` | 无 | 无 |
| `LOGIN:LOGOUT` | 无 | 无 |
| `LOGIN:CLOSE_QR_CODE` | 无 | 无 |

#### SETTING 模块 (4 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `SETTING:UPDATE_CONFIG` | `&#123;key, value&#125;` | 可能含配置 |
| `SETTING:GET_CAN_OPEN_CODE_ENHANCE` | 无 | 无 |
| `SETTING:SAVE_SHOW_OPERATE_GUIDANCE` | `&#123;isShowOperateGuide&#125;` | 无 |
| `SETTING:POPUP_KEYMAP_SETTINGS` | 无 | 无 |

#### COMMON 模块 (8 条)

| 消息类型 | value 格式 | 敏感数据 |
|---------|-----------|---------|
| `COMMON:PAGE_READY` | 无 | 无 |
| `COMMON:OPEN_URL` | `&#123;url&#125;` | URL |
| `COMMON:FOCUS_FILE` | `&#123;filePath, line&#125;` | 无 |
| `COMMON:FOCUS_FILE_LINE` | `&#123;filePath, line&#125;` | 无 |
| `COMMON:CODE_CLICK_ACTION` | `&#123;type, content&#125;` | 代码内容 |
| `COMMON:OPEN_FILE_DIALOG` | `&#123;params&#125;` | 无 |
| `COMMON:EVALUATION` | `&#123;messageId, type&#125;` | 无 |
| `COMMON:FEEDBACK` | `&#123;messageId, type, content&#125;` | 无 |
| `COMMON:DOWNLOAD_TABLE` | `&#123;params&#125;` | 无 |

---

## 5. Java→WebView 回调格式

### 5.1 回调发送机制

```java
// Java 端
void sendMessage2webView(String type, Object data) &#123;
    String json = new Gson().toJson(Map.of("type", type, "data", data));
    browser.getCefBrowser().executeJavaScript(
        "window.receiveData(" + json + ")",
        browser.getCefBrowser().getURL(),
        0
    );
&#125;
```

### 5.2 Java→JS 消息完整映射表

从 `handlerReceivedMsg()` switch/case 提取的所有 Java→JS 消息类型:

| 消息类型 | 处理方法 | 数据内容 | 含加密数据 |
|---------|---------|---------|-----------|
| `CHAT:AGENT_ERROR` | hanldeAgentError | `&#123;error, message&#125;` | 无 |
| `CHAT:GET_USER_INFO` | receiveUserInfo | `&#123;userName, avatar, ...&#125;` | 无 |
| `CHAT:GET_CONVERSATION` | getConversation | `&#123;messages: [...]&#125;` | 无 |
| `CHAT:UPDATE_CONVERSATION_LIST` | updateConversationList | `&#123;conversation&#125;` | 无 |
| `CHAT:UPDATE_SELECT_CODE` | updateSelectCode | `&#123;code, range&#125;` | 无 |
| `CHAT:SET_SEND_MESSAGE_TYPE` | setSendMessageType | `&#123;type&#125;` | 无 |
| `CHAT:GET_MODEL_LIST` | getModelList | `&#123;models: [...]&#125;` | 无 |
| `CHAT:PREDICT` | updatePredictList | `&#123;predictions&#125;` | 无 |
| `CHAT:RECEIVER_IDE_FILE_STATE` | getIdeFileState | `&#123;files, isRecommend&#125;` | 无 |
| `CHAT:RECEIVER_DOC_KNOWLEDGE_LIST` | getDocKnowledgeInfo | `&#123;documents&#125;` | 无 |
| `CHAT:RECEIVER_CODE_KNOWLEDGE_LIST` | getCodeKnowledgeInfo | `&#123;repositories&#125;` | 无 |
| `CHAT:SEND_OPEN_DIR_LIST` | getOpenDirListInfo | `&#123;dirs&#125;` | 无 |
| `CHAT:RECEIVER_HISTORY_LIST` | receiveHistoryList | `&#123;conversations&#125;` | 无 |
| `CHAT:GET_FEEDBACK_LIST` | receiveFeedBackCheckList | `&#123;categories&#125;` | 无 |
| `CHAT:CHOOSE_FILE` | receiveUploadFile | `&#123;filePath&#125;` | 无 |
| `CHAT:SEND_VALID_WEBSITE_RESULT` | getValidWebsiteResult | `&#123;isValid, url&#125;` | 无 |
| `CHAT_TALK:RECEIVER_RECOMMEND_GAMEPLAY` | receiveRecommendList | `&#123;recommendations&#125;` | 无 |
| `LOGIN:RECEIVER_LOGIN_IFRAME_SRC` | receiveLoginIframeSrc | `&#123;url&#125;` | 无 |
| `LOGIN:LOGIN_SUCCEED` | receiveLoginSuccess | `&#123;token?, userInfo?&#125;` | **可能含 Token** |
| `LOGIN:GO_LOGIN` | goLoginClickPage | 无 | 无 |
| `SETTING:GET_CONFIG` | getSettingInfo | `&#123;settings&#125;` | 无 |
| `SETTING:CHANGE_THEME` | changeTheme | `&#123;theme&#125;` | 无 |
| `SETTING:GET_CAN_OPEN_CODE_ENHANCE` | changeCodeEnhanceEnabled | `&#123;enabled&#125;` | 无 |
| `SETTING:RECEIVE_REPO_STATUS` | receiveRepoStatus | `&#123;status&#125;` | 无 |
| `SETTING:SEND_SHOW_OPERATE_GUIDANCE` | receiveOperateGuideData | `&#123;isShow&#125;` | 无 |
| `COMMON:OPEN_PAGE` | openPage | `&#123;page&#125;` | 无 |
| `COMMON:SHOW_MESSAGE_IN_WEB` | showMessageInWeb | `&#123;message&#125;` | 无 |
| `COMMON:PLUGIN_BASE_INFO` | getPluginBaseInfo | `&#123;version, ...&#125;` | 无 |
| `CODE_CHECK:GET_CODE_CHECK_LIST` | getCodeCheckList | `&#123;issues&#125;` | 无 |
| `CODE_CHECK:UPDATE_CODE_CHECK` | updateCodeCheckList | `&#123;issue&#125;` | 无 |
| `CODE_REVIEW:RECEIVER_PAGE_INIT` | receiveCodeReviewInit | `&#123;initData&#125;` | 无 |
| `CODE_REVIEW:RECEIVER_CODE_REVIEW` | receiveCodeReview | `&#123;review&#125;` | 无 |
| `CODE_REVIEW:RECEIVER_CHANGE_RESULT` | receiveChangeResult | `&#123;change&#125;` | 无 |
| `CODE_SEARCH:GET_CODESEARCH_CODE_LIST` | getCodeSearchCodeList | `&#123;results&#125;` | 无 |
| `CODE_SEARCH:GET_CODESEARCH_REPOSITORY_LIST` | getCodeSearchRepositoryList | `&#123;repos&#125;` | 无 |
| `CODE_SEARCH:GET_CODESEARCH_LANGUAGE_LIST` | getCodeSearchLanguageList | `&#123;languages&#125;` | 无 |
| `SQL_CHAT:RECEIVE_SOURCE_TYPES` | sqlReceiveSourceTypes | `&#123;types&#125;` | 无 |
| `SQL_CHAT:RECEIVE_LINK_TEST` | sqlReceiveLinkTest | `&#123;success, message&#125;` | 无 |
| `SQL_CHAT:RECEIVE_SAVE` | sqlReceiveSave | `&#123;success&#125;` | 无 |
| `SQL_CHAT:SOURCE_REFRESH_SAVE` | sqlSourceRefreshReceiveSave | `&#123;success&#125;` | 无 |
| `SQL_CHAT:RECEIVE_SOURCE_LIST` | sqlReceiveSourceList | `&#123;sources&#125;` | **含连接信息** |
| `SQL_CHAT:RECEIVE_TABLE_LIST` | sqlReceiveTableList | `&#123;tables&#125;` | 无 |
| `SQL_CHAT:GET_CONVERSATION` | sqlGetConversation | `&#123;messages&#125;` | 无 |
| `SQL_CHAT:UPDATE_CONVERSATION_LIST` | sqlUpdateConversationList | `&#123;conversation&#125;` | 无 |
| `UNIT_TEST:GET_UT_INFO` | addUTContent | `&#123;testInfo&#125;` | 无 |
| `UNIT_TEST:GET_METHOD_CASE` | receiveClassCaseData | `&#123;cases&#125;` | 无 |
| `UNIT_TEST:GET_CASE_CODE` | receiveCaseCode | `&#123;code&#125;` | 无 |
| `UNIT_TEST:GET_ALL_CODE_FILE` | receiveSaveMessage | `&#123;files&#125;` | 无 |
| `UNIT_TEST:FUNCTION_LIST` | unitTestFunctionList | `&#123;functions&#125;` | 无 |
| `UNIT_TEST:RECEIVE_FUNCTION_CASE` | unitTestFunctionCase | `&#123;case&#125;` | 无 |
| `UNIT_TEST:RECEIVE_FUNCTION_CASE_CODE` | unitTestFunctionCode | `&#123;code&#125;` | 无 |
| `BATCH_UNIT_TEST:GET_TASK_LIST` | getMultiTestTaskList | `&#123;tasks&#125;` | 无 |
| `BATCH_UNIT_TEST:MESSAGE` | multiTestMessage | `&#123;message&#125;` | 无 |
| `BATCH_UNIT_TEST:REFRESH_TASK_DOWNLOAD_STATUS` | multiTestRefreshTaskDownloadStatus | `&#123;status&#125;` | 无 |
| `UNIT_TEST_BANK:RECEIVE_FUNCTION` | receiveTestBankFunctionData | `&#123;functions&#125;` | 无 |
| `UNIT_TEST_BANK:RECEIVE_DATA` | receiveTestBankData | `&#123;data&#125;` | 无 |
| `UNIT_TEST_BANK:RESPONSE_SAVE` | receiveTestBankSave | `&#123;success&#125;` | 无 |
| `UNIT_TEST_BANK:IDEA_STOP` | stopUnitTestBank | 无 | 无 |
| `USER:PERMISSION_LIST` | getPermissionCodeList | `&#123;permissions&#125;` | 无 |
| `GIT:STATUS` | updateGitStatusList | `&#123;status&#125;` | 无 |

**结论: 所有 Java→JS 回调均不包含加密数据。** 数据以明文 JSON 推送到 WebView。

---
