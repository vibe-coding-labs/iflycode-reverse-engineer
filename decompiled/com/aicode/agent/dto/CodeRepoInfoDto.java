/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto;

import com.aicode.agent.dto.search.PageInfo;
import com.aicode.agent.dto.search.ReposInfoDto;
import java.util.List;

public class CodeRepoInfoDto
extends PageInfo {
    private List<ReposInfoDto> content;

    public CodeRepoInfoDto() {
    }

    public CodeRepoInfoDto(List<ReposInfoDto> content) {
        this.content = content;
    }

    public CodeRepoInfoDto(Integer currentPage, Integer pageSize, Integer total, Integer totalPage, List<ReposInfoDto> content) {
        super(currentPage, pageSize, total, totalPage);
        this.content = content;
    }

    public List<ReposInfoDto> getContent() {
        return this.content;
    }

    public void setContent(List<ReposInfoDto> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CodeRepoInfoDto{content=" + this.content + "}";
    }
}
