/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonObject
 *  com.intellij.openapi.project.Project
 */
package com.aicode.agent.dto;

import com.aicode.agent.dto.TipInfoDto;
import com.aicode.agent.dto.chat.CodeInfoDto;
import com.aicode.agent.enums.CommandEnum;
import com.aicode.agent.enums.PermissionEnum;
import com.aicode.settings.AICodeSettingsState;
import com.aicode.test.dto.RequestCaseCodeDto;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.intellij.openapi.project.Project;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MessageDto {
    private String traceparent;
    private String id;
    private boolean stream = true;
    private long timeStamp = Instant.now().toEpochMilli();
    private String command;
    private String path;
    private String lang;
    private String content;
    private String sessionId;
    private String modelCode;
    private String permissionCode;
    private Object data;
    private Integer docChangeCount;
    private List<CodeInfoDto.RangeDTO> range;
    private transient boolean chatTest;
    private transient String pid;
    private transient String taskId;
    private transient RequestCaseCodeDto requestCaseCodeDto;
    private transient Project project;
    private Object knowledge;
    private transient StringBuffer text;
    private JsonArray intelligent;
    private JsonArray relatedFiles;
    private String language;
    private TipInfoDto tipinfo;
    private String requestion;
    private String md5;
    private transient int currentLength = 1;
    private transient int streamStep = 1;
    private AtomicBoolean isDisplay = new AtomicBoolean(false);
    private transient Object otherObject;
    private String directName;
    private transient int inlineChatVersion;

    public MessageDto() {
    }

    public MessageDto(String id, String command) {
        this.id = id;
        this.command = command;
    }

    public String getTraceparent() {
        return this.traceparent;
    }

    public void setTraceparent(String traceparent) {
        this.traceparent = traceparent;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLang() {
        return this.lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<CodeInfoDto.RangeDTO> getRange() {
        return this.range;
    }

    public void setRange(List<CodeInfoDto.RangeDTO> range) {
        this.range = range;
    }

    public boolean isChatTest() {
        return this.chatTest;
    }

    public void setChatTest(boolean chatTest) {
        this.chatTest = chatTest;
    }

    public String getModelCode() {
        return this.modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public RequestCaseCodeDto getRequestCaseCodeDto() {
        return this.requestCaseCodeDto;
    }

    public void setRequestCaseCodeDto(RequestCaseCodeDto requestCaseCodeDto) {
        this.requestCaseCodeDto = requestCaseCodeDto;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setDocChangeCount(Integer docChangeCount) {
        this.docChangeCount = docChangeCount;
    }

    public Integer getDocChangeCount() {
        return this.docChangeCount;
    }

    public void initModelInfo() {
        try {
            CommandEnum commandEnum = CommandEnum.getByType(this.command);
            if (commandEnum == null) {
                return;
            }
            if (CommandEnum.TALK_INTELLIGENT != commandEnum) {
                this.setModelInfo(commandEnum);
            } else {
                JsonArray jsonArray = this.getIntelligent();
                if (jsonArray == null) {
                    return;
                }
                String command = null;
                for (int i = 0; i < jsonArray.size(); ++i) {
                    JsonObject asJsonObject = jsonArray.get(i).getAsJsonObject();
                    if (asJsonObject == null || !asJsonObject.has("type") || !"command".equals(asJsonObject.get("type").getAsString())) continue;
                    command = asJsonObject.get("value").getAsString();
                    break;
                }
                if ((commandEnum = command == null ? CommandEnum.TALK_INTELLIGENT : CommandEnum.getByType(command)) == null) {
                    return;
                }
                this.setModelInfo(commandEnum);
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private void setModelInfo(CommandEnum commandEnum) {
        String permission = commandEnum.getPermission();
        if (permission == null) {
            return;
        }
        PermissionEnum permissionEnum = PermissionEnum.getPermissionEnum(permission);
        if (permissionEnum == null) {
            return;
        }
        this.permissionCode = permissionEnum.getPermission();
        if (permissionEnum == PermissionEnum.TALK_INTELLIGENT) {
            this.modelCode = AICodeSettingsState.getInstance().modelCode;
        } else if (permissionEnum == PermissionEnum.INLINE_CHAT) {
            this.modelCode = AICodeSettingsState.getInstance().inlineChatModelCode;
        }
    }

    public Object getKnowledge() {
        return this.knowledge;
    }

    public void setKnowledge(Object knowledge) {
        this.knowledge = knowledge;
    }

    public StringBuffer getText() {
        return this.text;
    }

    public void setText(StringBuffer text) {
        this.text = text;
    }

    public TipInfoDto getTipinfo() {
        return this.tipinfo;
    }

    public void setTipinfo(TipInfoDto tipinfo) {
        this.tipinfo = tipinfo;
    }

    public JsonArray getIntelligent() {
        return this.intelligent;
    }

    public void setIntelligent(JsonArray intelligent) {
        this.intelligent = intelligent;
    }

    public JsonArray getRelatedFiles() {
        return this.relatedFiles;
    }

    public void setRelatedFiles(JsonArray relatedFiles) {
        this.relatedFiles = relatedFiles;
    }

    public boolean isStream() {
        return this.stream;
    }

    public void setStream(boolean stream) {
        this.stream = stream;
    }

    public String getRequestion() {
        return this.requestion;
    }

    public void setRequestion(String requestion) {
        this.requestion = requestion;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getStreamStep() {
        return this.streamStep;
    }

    public void setStreamStep(int streamStep) {
        this.streamStep = streamStep;
    }

    public int getCurrentLength() {
        return this.currentLength;
    }

    public void setCurrentLength(int currentLength) {
        this.currentLength = currentLength;
    }

    public AtomicBoolean getIsDisplay() {
        return this.isDisplay;
    }

    public void setIsDisplay(AtomicBoolean isDisplay) {
        this.isDisplay = isDisplay;
    }

    public Object getOtherObject() {
        return this.otherObject;
    }

    public void setOtherObject(Object otherObject) {
        this.otherObject = otherObject;
    }

    public String getDirectName() {
        return this.directName;
    }

    public void setDirectName(String directName) {
        this.directName = directName;
    }

    public int getInlineChatVersion() {
        return this.inlineChatVersion;
    }

    public void setInlineChatVersion(int inlineChatVersion) {
        this.inlineChatVersion = inlineChatVersion;
    }

    public String getPermissionCode() {
        return this.permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }
}
