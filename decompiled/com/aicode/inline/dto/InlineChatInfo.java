/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.intellij.openapi.editor.Editor
 */
package com.aicode.inline.dto;

import com.aicode.inline.controller.SessionController;
import com.intellij.openapi.editor.Editor;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InlineChatInfo {
    private String message = "";
    private Editor editor;
    private SessionController sessionController;
    public int inlineChatVersion;
    private String requestId;
    private String content = "";
    private List<String> lineList;
    private boolean trimPrefix;
    private AtomicInteger handleLineIndex = new AtomicInteger(0);

    public void setLineList() {
        this.lineList = Arrays.asList(this.content.split("\n"));
    }

    public String getMessage() {
        return this.message;
    }

    public Editor getEditor() {
        return this.editor;
    }

    public SessionController getSessionController() {
        return this.sessionController;
    }

    public int getInlineChatVersion() {
        return this.inlineChatVersion;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public String getContent() {
        return this.content;
    }

    public List<String> getLineList() {
        return this.lineList;
    }

    public boolean isTrimPrefix() {
        return this.trimPrefix;
    }

    public AtomicInteger getHandleLineIndex() {
        return this.handleLineIndex;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public void setInlineChatVersion(int inlineChatVersion) {
        this.inlineChatVersion = inlineChatVersion;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLineList(List<String> lineList) {
        this.lineList = lineList;
    }

    public void setTrimPrefix(boolean trimPrefix) {
        this.trimPrefix = trimPrefix;
    }

    public void setHandleLineIndex(AtomicInteger handleLineIndex) {
        this.handleLineIndex = handleLineIndex;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof InlineChatInfo)) {
            return false;
        }
        InlineChatInfo other = (InlineChatInfo)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getInlineChatVersion() != other.getInlineChatVersion()) {
            return false;
        }
        if (this.isTrimPrefix() != other.isTrimPrefix()) {
            return false;
        }
        String this$message = this.getMessage();
        String other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) {
            return false;
        }
        Editor this$editor = this.getEditor();
        Editor other$editor = other.getEditor();
        if (this$editor == null ? other$editor != null : !this$editor.equals(other$editor)) {
            return false;
        }
        SessionController this$sessionController = this.getSessionController();
        SessionController other$sessionController = other.getSessionController();
        if (this$sessionController == null ? other$sessionController != null : !this$sessionController.equals(other$sessionController)) {
            return false;
        }
        String this$requestId = this.getRequestId();
        String other$requestId = other.getRequestId();
        if (this$requestId == null ? other$requestId != null : !this$requestId.equals(other$requestId)) {
            return false;
        }
        String this$content = this.getContent();
        String other$content = other.getContent();
        if (this$content == null ? other$content != null : !this$content.equals(other$content)) {
            return false;
        }
        List<String> this$lineList = this.getLineList();
        List<String> other$lineList = other.getLineList();
        if (this$lineList == null ? other$lineList != null : !((Object)this$lineList).equals(other$lineList)) {
            return false;
        }
        AtomicInteger this$handleLineIndex = this.getHandleLineIndex();
        AtomicInteger other$handleLineIndex = other.getHandleLineIndex();
        return !(this$handleLineIndex == null ? other$handleLineIndex != null : !this$handleLineIndex.equals(other$handleLineIndex));
    }

    protected boolean canEqual(Object other) {
        return other instanceof InlineChatInfo;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getInlineChatVersion();
        result = result * 59 + (this.isTrimPrefix() ? 79 : 97);
        String $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Editor $editor = this.getEditor();
        result = result * 59 + ($editor == null ? 43 : $editor.hashCode());
        SessionController $sessionController = this.getSessionController();
        result = result * 59 + ($sessionController == null ? 43 : $sessionController.hashCode());
        String $requestId = this.getRequestId();
        result = result * 59 + ($requestId == null ? 43 : $requestId.hashCode());
        String $content = this.getContent();
        result = result * 59 + ($content == null ? 43 : $content.hashCode());
        List<String> $lineList = this.getLineList();
        result = result * 59 + ($lineList == null ? 43 : ((Object)$lineList).hashCode());
        AtomicInteger $handleLineIndex = this.getHandleLineIndex();
        result = result * 59 + ($handleLineIndex == null ? 43 : $handleLineIndex.hashCode());
        return result;
    }

    public String toString() {
        return "InlineChatInfo(message=" + this.getMessage() + ", editor=" + this.getEditor() + ", sessionController=" + this.getSessionController() + ", inlineChatVersion=" + this.getInlineChatVersion() + ", requestId=" + this.getRequestId() + ", content=" + this.getContent() + ", lineList=" + this.getLineList() + ", trimPrefix=" + this.isTrimPrefix() + ", handleLineIndex=" + this.getHandleLineIndex() + ")";
    }
}
