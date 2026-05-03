/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto.search;

public class PageInfo {
    private Integer currentPage;
    private Integer pageSize;
    private Integer total;
    private Integer totalPage;

    public PageInfo() {
    }

    public PageInfo(Integer currentPage, Integer pageSize, Integer total, Integer totalPage) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.total = total;
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (currentPage > this.totalPage) {
            currentPage = this.totalPage;
        }
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null) {
            pageSize = 10;
        }
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public String toString() {
        return "PageInfo{, currentPage=" + this.currentPage + ", pageSize=" + this.pageSize + ", total=" + this.total + ", totalPage=" + this.totalPage + "}";
    }
}
