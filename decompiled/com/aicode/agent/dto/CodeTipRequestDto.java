/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.opentelemetry.api.trace.Span
 */
package com.aicode.agent.dto;

import com.aicode.service.CodeInlayList;
import com.aicode.service.EditorRequestService;
import io.opentelemetry.api.trace.Span;
import java.util.List;
import java.util.concurrent.Flow;

public class CodeTipRequestDto {
    private EditorRequestService request;
    private Flow.Subscriber<List<CodeInlayList>> codeSubScriber;
    private Span parentSpan;
    private Long startTime;
    private String lastReplacementText = "";
    private long firstAgentDuration = 0L;

    public CodeTipRequestDto() {
    }

    public CodeTipRequestDto(EditorRequestService request, Flow.Subscriber<List<CodeInlayList>> codeSubScriber, Span parentSpan, Long startTime) {
        this.request = request;
        this.codeSubScriber = codeSubScriber;
        this.parentSpan = parentSpan;
        this.startTime = startTime;
    }

    public EditorRequestService getRequest() {
        return this.request;
    }

    public void setRequest(EditorRequestService request) {
        this.request = request;
    }

    public Flow.Subscriber<List<CodeInlayList>> getCodeSubScriber() {
        return this.codeSubScriber;
    }

    public void setCodeSubScriber(Flow.Subscriber<List<CodeInlayList>> codeSubScriber) {
        this.codeSubScriber = codeSubScriber;
    }

    public Span getParentSpan() {
        return this.parentSpan;
    }

    public void setParentSpan(Span parentSpan) {
        this.parentSpan = parentSpan;
    }

    public Long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public String getLastReplacementText() {
        return this.lastReplacementText;
    }

    public void setLastReplacementText(String lastReplacementText) {
        this.lastReplacementText = lastReplacementText;
    }

    public long getFirstAgentDuration() {
        return this.firstAgentDuration;
    }

    public void setFirstAgentDuration(long firstAgentDuration) {
        if (this.firstAgentDuration == 0L) {
            this.firstAgentDuration = firstAgentDuration;
        }
    }
}
