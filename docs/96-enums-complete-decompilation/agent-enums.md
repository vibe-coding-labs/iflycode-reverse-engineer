## 2. com.aicode.agent.enums Package (5 classes)

### 2.1 CommandEnum (CRITICAL - 100+ values)

**Source file:** `cl` (obfuscated)
**The central command routing enum - drives ALL plugin functionality**

**Fields:**
- `private AgentModuleEnum case` (obfuscated: module reference)
- `private String final` (obfuscated: type string)
- `private String try` (obfuscated: description)
- `private String byte` (obfuscated: additional data)
- `private javax.swing.Icon enum` (obfuscated: icon)

**Constructors:** 4 overloads with varying parameters

**Complete Enum Values (by functional group):**

**LOG (Code Completion) Group:**
| Enum Value |
|-----------|
| `LOG_ACCEPT` |
| `LOG_ACCEPT_LINE` |
| `LOG_ACCEPT_WORD` |
| `LOG_ACCEPT_COUNT` |
| `LOG_REJECT` |
| `LOG_REJECT_ESC` |
| `LOG_DISPLAY` |
| `LOG_TIP_SETTING` |
| `LOG_OPERATE` |
| `LOG_EVALUATION` |
| `LOG_FEEDBACK` |
| `LOG_IMITATIVE_WRITE` |
| `LOG_TEST_COLLECTION_GENERATE` |
| `LOG_TEST_COLLECTION_COMMIT` |

**INIT Group:**
| Enum Value |
|-----------|
| `INIT` |
| `UPDATE` |
| `SERVER_RESOURCE` |

**LOGIN Group:**
| Enum Value |
|-----------|
| `USER_LOGIN` |
| `USER_LOGIN_CHECK` |
| `USER_LOGIN_ABORT` |
| `USER_LOGOUT` |
| `LOGIN_INFO` |
| `USER_PERMISSION` |
| `USER_VERSION` |
| `USER_MODEL_LIST` |
| `USER_PARSE_WEB_URL` |
| `USER_KNOWLEDGE_LIST` |
| `USER_FEEDBACK_CATEGORY` |
| `USER_CAN_CODE_ENHANCE` |

**CHAT Group:**
| Enum Value |
|-----------|
| `TALK_ASK` |
| `TALK_INTELLIGENT` |
| `TALK_PREDICT` |
| `TALK_RESEND` |
| `TALK_HISTORY` |
| `TALK_LIST` |
| `TALK_DELETE` |
| `TALK_CLEAR` |
| `TALK_RECOMMEND_GAMEPLAY` |
| `TALK_DOWNLOAD_MARKDOWN_TABLE` |
| `TALK_KNOWLEDGE` |

**SQL Chat Group:**
| Enum Value |
|-----------|
| `SQL_GENERATE` |
| `SQL_GENERATE_TALK` |
| `SQL_OPTIMIZE` |
| `SQL_OPTIMIZE_TALK` |
| `SQL_TABLE_LIST` |
| `SQL_SOURCE_LIST` |
| `SQL_SOURCE_TYPES` |
| `SQL_SOURCE_EDIT` |
| `SQL_SOURCE_DELETE` |
| `SQL_TEST_CONNECT` |

**CODE_COMPLETE Group:**
| Enum Value |
|-----------|
| `CODE_COMPLETE` |
| `RAG_LANGUAGES` |
| `MODEL_LIST_TIMER` |

**CODE_CHECK Group:**
| Enum Value |
|-----------|
| `CODE_CHECK` |
| `CODE_FAULT_ANALYSIS` |

**GIT_REVIEW Group:**
| Enum Value |
|-----------|
| `GIT_REVIEW` |
| `GIT_DIFF` |
| `GIT_SEARCH` |
| `GIT_USER_REPOS` |
| `GIT_LANG_LIST` |
| `GIT_COMMIT_MESSAGE` |
| `GIT_SAVE_TOKEN` |
| `GIT_REPO_AUTHORIZE` |
| `GIT_REPOSITORY_STATUS` |
| `GIT_CODE_KNOWLEDGE_RE_INDEX` |
| `GIT_CODE_KNOWLEDGE_REPO_STATUS` |

**UNIT_TEST Group:**
| Enum Value |
|-----------|
| `CODE_TEST` |
| `CODE_TEST_CODE` |
| `CODE_TEST_CASE` |
| `CODE_TEST_SAVE` |
| `CODE_TEST_ANALYSIS` |
| `CODE_GENERATE_TEST_CASE` |
| `CODE_TEST_TEMPLATE` |
| `CODE_TEST_MAKE_CASE_JAVA` |
| `TEST_MAKE_CODE` |
| `TEST_MAKE_CASE` |

**BATCH_UNIT_TEST Group:**
| Enum Value |
|-----------|
| `CODE_BATCH_UNIT_TEST_LIST` |
| `CODE_BATCH_UNIT_TEST_CREATE` |
| `CODE_BATCH_UNIT_TEST_DELETE` |
| `CODE_BATCH_UNIT_TEST_DOWNLOAD` |
| `CODE_BATCH_UNIT_TEST_CANCEL` |

**CODE_SEARCH Group:**
| Enum Value |
|-----------|
| `CODE_SEARCH` (implied by module) |

**INLINE_CHAT Group:**
| Enum Value |
|-----------|
| `INLINECHAT_CATEGORY` |
| `INLINECHAT_DIRECT` |
| `INLINECHAT_GET_FUNC_RANGE` |

**COMMON Group:**
| Enum Value |
|-----------|
| `ACTION_INIT` |
| `ACTION_ABORT` |
| `ACTION_OPEN_DOCUMENT` |
| `ACTION_SYNC_DOCUMENT_LIST` |
| `DIALOG_ACCEPT` |
| `DIALOG_REJECT` |
| `DIALOG_ABORT` |
| `DIALOG_EDIT` |
| `DIALOG_DIFF` |
| `GENERAL_SETTING` |
| `REPO_STATUS` |
| `ERROR` |

**Code Action Group:**
| Enum Value |
|-----------|
| `CODE_EXPLAIN` |
| `CODE_COMMENT` |
| `CODE_COMMENT_RANGE` |
| `CODE_INLINE_COMMENT` |
| `CODE_OPTIMIZE` |
| `CODE_DEBUG` |
| `CODE_DEBUG_DUPLICATE` |
| `CODE_SPLIT` |
| `CODE_HELP` |
| `CODE_DEMAND_ANALYSIS` |
| `CODE_DEMAND_SPLITTING` |
| `CODE_DEMAND_TEST` |

---

### 2.2 AgentModuleEnum

**Source file:** `ho` (obfuscated)
**14 module routing values**

| Ordinal | Enum Value | Decoded Name |
|---------|-----------|-------------|
| 0 | `LOG` | Decoded |
| 1 | `INIT` | Decoded |
| 2 | `LOGIN` | Decoded |
| 3 | `COMMON` | Decoded |
| 4 | `CHAT` | Decoded |
| 5 | `SQL_CHAT` | Decoded |
| 6 | `CODE_COMPLETE` | Decoded |
| 7 | `CODE_SEARCH` | Decoded |
| 8 | `CODE_CHECK` | Decoded |
| 9 | `GIT_REVIEW` | Decoded |
| 10 | `UNIT_TEST` | Decoded |
| 11 | `BATCH_UNIT_TEST` | Decoded |
| 12 | `CODE_TEST_TEMPLATE` | Decoded |
| 13 | `SERVER_RESOURCE` | Decoded |
| 14 | `INLINE_CHAT` | Decoded |

**Key Methods:** Standard enum methods only

---

### 2.3 ModuleEnum

**Source file:** `ch` (obfuscated)
**12 module values for WebView routing**

| Ordinal | Enum Value |
|---------|-----------|
| 0 | `LOG` |
| 1 | `LOGIN` |
| 2 | `COMMON` |
| 3 | `SETTING` |
| 4 | `CHAT` |
| 5 | `SQL_CHAT` |
| 6 | `CODE_SEARCH` |
| 7 | `CODE_CHECK` |
| 8 | `GIT_VIEW` |
| 9 | `UNIT_TEST` |
| 10 | `BATCH_UNIT_TEST` |
| 11 | `UNIT_TESTING` |

**Key Methods:**
- `getType()` - Returns type string
- `getByType(String)` - Lookup by type, returns null if not found

---

### 2.4 PageEnum

**Source file:** `ri` (obfuscated)
**6 page values for WebView navigation**

| Ordinal | Enum Value | Type |
|---------|-----------|------|
| 0 | `CHAT_VIEW` | Decoded |
| 1 | `SETTING_PAGE` | Decoded |
| 2 | `CODE_CHECK` | Decoded |
| 3 | `CODE_REVIEW` | Decoded |
| 4 | `UNIT_TEST` | Decoded |
| 5 | `UNIT_TESTING` | Decoded |

**Key Methods:**
- `getType()` - Returns type string
- `getByType(String)` - Lookup by type, returns null if not found

---

### 2.5 PermissionEnum

**Source file:** `ud` (obfuscated)
**24 permission values controlling feature access**

| Ordinal | Enum Value |
|---------|-----------|
| 0 | `DEMAND_TEST` |
| 1 | `SQL_OPTIMIZATION` |
| 2 | `CHAT_SQL_GENERATION` |
| 3 | `INLINE_CHAT` |
| 4 | `DEMAND_SPLIT` |
| 5 | `FUNCTION_SPLIT` |
| 6 | `TALK_INTELLIGENT` |
| 7 | `FAILURE_ANALYSIS` |
| 8 | `DEMAND_ANALYSIS` |
| 9 | `CODE_OPTIMIZATION` |
| 10 | `REVIEW` |
| 11 | `GENERATE_TEST_CASE` |
| 12 | `COMMENTS` |
| 13 | `CODE_KNOWLEDGE_BASE` |
| 14 | `CHAT_MODULE` |
| 15 | `CHAT_SQL_OPTIMIZATION` |
| 16 | `LINE_COMMENTS` |
| 17 | `UNIT_TESTING` |
| 18 | `CODE_DEBUG` |
| 19 | `GENERATE_COMMIT` |
| 20 | `DOC_COMMENTS` |
| 21 | `SQL_GENERATION` |
| 22 | `BATCH_UNITTEST` |

**Fields:**
- `private final String enum` (obfuscated: permission code)
- `private final AnAction byte` (obfuscated: associated IntelliJ action)
- `public static final List&lt;String&gt; PERMISSION_ORDER_LIST` - Ordered list of permission string codes
- `public static final List&lt;PermissionEnum&gt; RIGHT_PERMISSION_ORDER_LIST` - Ordered list for right panel

**Key Methods:**
- `getPermissionCode()` - Returns permission code string
- `getAction()` - Returns associated `AnAction`

---
