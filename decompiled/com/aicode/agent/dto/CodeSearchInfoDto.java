/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto;

import com.aicode.agent.dto.search.CodeSearchDto;
import com.aicode.agent.dto.search.PageInfo;
import java.util.List;

public class CodeSearchInfoDto
extends PageInfo {
    private List<CodeSearchDto> content;
    private String type;
    private Integer count;

    public CodeSearchInfoDto() {
    }

    public CodeSearchInfoDto(String type, List<CodeSearchDto> content, Integer count) {
        this.type = type;
        this.content = content;
        this.count = count;
    }

    public CodeSearchInfoDto(Integer currentPage, Integer pageSize, Integer total, Integer totalPage, String type, List<CodeSearchDto> content, Integer count) {
        super(currentPage, pageSize, total, totalPage);
        this.type = type;
        this.content = content;
        this.count = count;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CodeSearchDto> getContent() {
        return this.content;
    }

    public void setContent(List<CodeSearchDto> content) {
        this.content = content;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CodeSearchInfoDto{content=" + this.content + ", type='" + this.type + "', count=" + this.count + "}";
    }
}
