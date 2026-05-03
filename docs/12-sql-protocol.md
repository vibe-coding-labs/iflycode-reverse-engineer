# 12 SQL 生成/优化协议

## 数据源管理

### 获取数据源列表

```
W→J: sql_chat_source_list
  └─► CommandEnum.SQL_SOURCE_LIST
      └─► Agent 返回 DatabaseDto 列表
          └─► J→W: sql_chat_receive_source_list
              {
                "type": "sql_chat_receive_source_list",
                "value": [
                  {
                    "id": "ds-1",
                    "formData": {
                      "client": "MySQL",
                      "host": "localhost",
                      "port": "3306",
                      "user": "root",
                      "database": "mydb"
                    },
                    "databases": ["mydb"],
                    "status": true,
                    "errMsg": "",
                    "createTime": 1713744000000,
                    "updateTime": 1713744000000
                  }
                ]
              }
```

### 获取数据库类型

```
W→J: sql_chat_request_source_types
  └─► CommandEnum.SQL_SOURCE_TYPES
      └─► J→W: sql_chat_receive_source_types
          {
            "type": "sql_chat_receive_source_types",
            "value": ["MySQL", "PostgreSQL", "Oracle", "TxSQL"]
          }
```

### 测试连接

```
W→J: sql_chat_sql_link_test
  └─► CommandEnum.SQL_TEST_CONNECT
      {
        "command": "sql_test_connect",
        "data": {
          "client": "MySQL",
          "host": "localhost",
          "port": "3306",
          "user": "root",
          "password": "***",
          "database": "mydb"
        }
      }
      └─► J→W: sql_chat_receive_link_test
          { "status": true, "message": "连接成功" }
```

### 保存/编辑数据源

```
W→J: sql_chat_sql_save
  └─► CommandEnum.SQL_SOURCE_EDIT
      {
        "command": "sql_source_edit",
        "data": { ConnectConfigDto }
      }
      └─► J→W: sql_chat_receive_save
```

### 删除数据源

```
W→J: sql_chat_source_delete
  └─► CommandEnum.SQL_SOURCE_DELETE
```

### 获取表列表

```
W→J: sql_chat_table_list
  └─► CommandEnum.SQL_TABLE_LIST
      {
        "command": "sql_table_list",
        "data": { "sourceId": "ds-1", "database": "mydb" }
      }
      └─► J→W: sql_chat_receive_table_list
          {
            "value": ["users", "orders", "products"]
          }
```

## SQL 对话

### SQL 生成对话

```
W→J: sql_chat_send_msg
  {
    "type": "sql_chat_send_msg",
    "value": {
      "inputText": "查询所有活跃用户的订单",
      "sqlInfo": {
        "database": "mydb",
        "inputText": "查询所有活跃用户的订单",
        "sourceId": "ds-1",
        "tables": ["users", "orders"]
      }
    }
  }
  └─► CommandEnum.SQL_GENERATE_TALK (走 CHAT 模块)
      {
        "command": "sql_generate_talk",
        "sessionId": "sql-session-uuid",
        "data": { SqlInfoDto }
      }
      └─► 流式返回 SQL
```

### SQL 优化对话

```
W→J: sql_chat_send_msg
  {
    "value": {
      "inputText": "优化这个SQL",
      "sqlInfo": {
        "database": "mydb",
        "inputText": "SELECT * FROM users WHERE ...",
        "sourceId": "ds-1"
      }
    }
  }
  └─► CommandEnum.SQL_OPTIMIZE_TALK (走 CHAT 模块)
```

### 独立 SQL 生成

```
CommandEnum.SQL_GENERATE
  {
    "command": "sql_generate",
    "data": { SqlInfoDto }
  }
  └─► 流式返回 SQL
```

### 独立 SQL 优化

```
CommandEnum.SQL_OPTIMIZE
  {
    "command": "sql_optimize",
    "data": { SqlInfoDto }
  }
  └─► 流式返回优化后的 SQL
```

## SQL 会话管理

每个项目维护独立的 SQL 会话：

```java
// SqlService 内部
ConcurrentSkipListMap<String, String> SQL_SESSION_ID
// key = project.getBasePath(), value = sessionId
```

### 新建 SQL 对话

```
W→J: sql_chat_new_chat
  └─► 清空当前项目的 SQL_SESSION_ID
      └─► J→W: sql_chat_update_conversation_list (空)
```

### 停止响应

```
W→J: sql_chat_stop_response
  └─► CommandEnum.ACTION_ABORT
```

## 错误处理

连接测试失败时：

```json
{
  "status": false,
  "message": "Connection refused: localhost:3306"
}
```

SQL 生成/优化失败时，Agent 返回错误响应，Plugin 推送错误消息到 WebView。
