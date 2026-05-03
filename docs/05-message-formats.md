# 05 消息格式定义 (DTO)

## 核心消息 DTO

### MessageDto — WebSocket 请求信封

> 详见 [04-websocket-protocol.md](04-websocket-protocol.md)

### ResponseDto — WebSocket 响应信封

```java
public class ResponseDto {
    String id;       // 请求关联 ID
    String code;     // 状态码 ("0" = 成功)
    String msg;      // 状态消息
    String command;  // 响应命令
    Object data;     // 响应数据
}
```

### ResponseStreamDto — 流式响应

```java
public class ResponseStreamDto {
    String id;
    String code;
    String msg;
    ResponseData data;

    public class ResponseData {
        boolean ended;            // 是否结束
        String text = "";         // 增量文本
        boolean showKeyMapTipFlag; // 是否显示快捷键提示
    }
}
```

### BizResponse<T> — 通用业务响应

```java
public class BizResponse<T> {
    String resCode;  // "0" = 成功
    String msg;
    T obj;

    boolean isSuccess() { return "0".equals(resCode); }
}
```

## 用户相关 DTO

### UserInfoDto

```java
public class UserInfoDto {
    String clientId;                  // 客户端标识
    String user;                      // 用户名
    String token;                     // 认证 Token
    List<CodeModel> codeModelDtoList; // 可用 AI 模型列表
    EnterpriseDto enterpriseDto;      // 企业信息
    String tokenPath;                 // Token 存储路径
    SysUrlDto sysUrls;                // 系统 URL 集合
    String packageCode;               // 套餐代码
    String packageName;               // 套餐名称
    boolean reLogin;                  // 强制重新登录
}
```

### LoginInfo

```java
public class LoginInfo {
    String current;  // 当前版本
    String update;   // 更新版本
    String name;     // 文件名
    String file;     // 下载路径
    String dir;      // 目录
    String md5;      // MD5 校验
}
```

### EnterpriseDto

```java
public class EnterpriseDto {
    String enterpriseId;
    String enterpriseName;
    String userId;
}
```

### SysUrlDto — 系统 URL 配置

```java
public class SysUrlDto {
    String feedbackUrl;           // 反馈 URL
    String maintainRepoUrl;       // 仓库管理 URL
    String codeSearchServerUrl;   // 代码搜索服务 URL
    String officialWebsiteUrl;    // 官网 URL
    String codeKnowledgeWebUrl;   // 代码知识库 URL
    String userCenterWebUrl;      // 用户中心 URL
}
```

## 模型 DTO

### CodeModel

```java
public class CodeModel {
    @SerializedName("modelId")    String modelId;
    @SerializedName("modelCode")  String modelCode;
    @SerializedName("modelName")  String modelName;
    boolean checked;
    String originalModelName;
    boolean tokenExhausted;
}
```

### FunctionModelInfo

```java
public class FunctionModelInfo {
    @SerializedName("permissionCode") String permissionCode;
    @SerializedName("permissionName") String permissionName;
    @SerializedName("language")       String language;
    @SerializedName("codeModelList")  List<CodeModel> codeModelList;
}
```

## 代码上下文 DTO

### CodeInfoDto — 代码上下文信息

```java
public class CodeInfoDto {
    String content;              // 选中的代码内容
    List<RangeDTO> range;        // 选择范围 (2元素: start/end)
    List<RangeDTO> bodyRange;    // 函数体范围 (transient)
    String fileName;             // 文件名
    String path;                 // 完整路径
    String language;             // 语言 (文件扩展名)
    String allContent;           // 完整文件内容

    public class RangeDTO {
        Integer line;            // 0-based 行号
        Integer character;       // 0-based 字符偏移
    }
}
```

### TipInfoDto — 补全提示信息

```java
public class TipInfoDto {
    String user;                      // 用户标识
    String platform;                  // IDE 平台版本
    Boolean isShowOperateGuide;       // 是否显示操作指南
}
```

## 对话 DTO

### FirstChatMessage — 对话消息

```java
public class FirstChatMessage {
    String type;           // WebViewDataTypeEnum 类型
    ValueDTO value;

    public class ValueDTO {
        String inputText;           // 用户输入
        String id;                  // 消息 ID
        String sessionId;           // 会话 ID
        String type;                // 命令类型
        CodeInfoDto codeInfo;       // 代码上下文
        SqlInfoDto sqlInfo;         // SQL 上下文
        JsonArray knowledge;        // 知识库引用
        boolean errorType;          // 错误类型标记
        String errorMessage;        // 错误消息
        JsonArray intelligent;      // 智能选项 (assistant+command)
        JsonArray relatedFiles;     // 相关文件
        JsonObject data;            // 附加数据
        String language;            // 语言
        String code;                // 代码
    }
}
```

### CommentContext / CommentInfo — 注释上下文

```java
public class CommentContext {
    String md5;                // 文件哈希
    List<CommentInfo> methods; // 方法列表
}

public class CommentInfo {
    String name;        // 方法名
    String textContext; // 注释文本
    int index;          // 排序
    JsonArray range;    // 代码范围
    JsonArray bodyRange;// 函数体范围
}
```

## SQL DTO

### SqlInfoDto

```java
public class SqlInfoDto {
    String database;       // 数据库名
    String inputText;      // 用户输入
    String sourceId;       // 数据源 ID
    List<String> tables;   // 表名列表
}
```

### ConnectConfigDto — 数据库连接配置

```java
public class ConnectConfigDto {
    String id;
    String client;     // 数据库类型
    String host;
    String port;
    String user;
    String password;
    String database;
}
```

### DatabaseDto — 数据库实例

```java
public class DatabaseDto {
    String id;
    ConnectConfigDto formData;  // 连接配置
    List<String> databases;     // 数据库列表
    Boolean status;             // 连接状态
    String errMsg;              // 错误消息
    Long createTime;
    Long updateTime;
}
```

## 代码搜索 DTO

### CodeSearchDto

```java
public class CodeSearchDto {
    String id, repoUrl, repoName, repoType, branch;
    String filePath, fileName, language;
    Integer isOpen, isPublic, startRow, endRow;
    BigDecimal score;
    String code;
    Integer codeLength;
    Double codeVector;
    Long createTime;
}
```

### ReposInfoDto

```java
public class ReposInfoDto {
    String id;
    String repoUrl;
    String repoName;
    String branch;
    String repoType;
}
```

### PageInfo (基类)

```java
public class PageInfo {
    Integer currentPage = 1;  // 当前页
    Integer pageSize = 10;    // 每页大小
    Integer total;            // 总数
    Integer totalPage;        // 总页数
}
```

## 代码检查 DTO

### CodeCheckDto

```java
public class CodeCheckDto {
    String codeFragment;    // 代码片段
    String errorType;       // 错误类型
    String errorMessage;    // 错误消息
    CodeInfoDto codeInfo;   // 代码上下文
}
```

### CodeCheckOriginDto

```java
public class CodeCheckOriginDto {
    String path;
    String name;
    List<ErrListDTO> errList;

    public class ErrListDTO {
        String codeFragment;
        String errorType;
        String errorMessage;
        List<RangeDTO> range;
    }
}
```

## 代码补全请求 DTO

### CodeTipRequestDto

```java
public class CodeTipRequestDto {
    EditorRequestService request;       // 补全请求
    Flow.Subscriber subscriber;         // 响应式订阅者
    Span parentSpan;                    // OpenTelemetry Span
    Long startTime;                     // 请求开始时间
    String lastReplacementText = "";    // 上次替换文本
    long firstAgentDuration = 0;        // Agent 首次响应耗时
}
```

### CodeGenerateEditorRequest

```java
public class CodeGenerateEditorRequest {
    TipType completionType;              // 补全类型
    boolean useTabIndents;
    int tabWidth;
    int requestId;                       // 请求计数器
    AICodeLanguageInfo fileLanguage;     // 文件语言
    VirtualFileUri uri;                  // 文件 URI
    String documentContent;              // 完整文档内容
    int offset;                          // 光标位置
    LineInfo lineInfo;                   // 行上下文
    long requestTimestamp;               // 请求时间戳
    long documentModificationSequence;   // 文档版本号
    String fileName;
    String fileNameSuffix;
    boolean isSelected;                  // 是否有选中
}
```

## 设置 DTO

### SettingsDto

```java
public class SettingsDto {
    boolean autoTriggerOnPause;
    Integer autoTriggerTimeDelay;
    String generateCodeMode;            // SINGLE_LINE / INTELLIGENT_MODE
    String[] codeCompleteDisableLang;
    String sendMessageType;             // ENTER_KEY / ENTER_SHIFT_KEY
    String javaTestFramework;
    String javaMockFramework;
    String lineToolsType;               // ICON / TEXT
    boolean lineToolsPermissionDocComments;
    boolean lineToolsPermissionLineComments;
    boolean lineToolsPermissionComments;
    boolean lineToolsPermissionFunctionSplit;
    boolean lineToolsPermissionCodeOptimization;
    boolean lineToolsPermissionUnitTesting;
    boolean openFunctionSplit;
    boolean openCodeOptimization;
    boolean openIFlyTest;
    boolean openInlineChat;
    boolean openIFlyDBA;
    boolean openIFlyOps;
    boolean openIFlyPm;
    boolean openCodeEnhance;
    String inlineCompletionInputStyle;  // DISPOSABLE
    boolean openAutoUpdate;
    String defaultLanguage;
}
```

## WebView 通信 DTO

### WebRequestDto

```java
public class WebRequestDto<T> {
    String type;   // 消息类型
    T value;       // 泛型载荷
}
```

### PresentationDataDto — Gutter 图标数据

```java
public class PresentationDataDto {
    int line;
    int character;
    String type;           // METHOD / CLASS
    CodeInfoDto codeInfoDto;
}
```
