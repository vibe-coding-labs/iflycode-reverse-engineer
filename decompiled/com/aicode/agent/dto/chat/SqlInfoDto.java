/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto.chat;

import java.util.List;

public class SqlInfoDto {
    private String database;
    private String inputText;
    private String sourceId;
    private List<String> tables;

    public SqlInfoDto() {
    }

    public SqlInfoDto(String database, String inputText, String sourceId, List<String> tables) {
        this.database = database;
        this.inputText = inputText;
        this.sourceId = sourceId;
        this.tables = tables;
    }

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getInputText() {
        return this.inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public List<String> getTables() {
        return this.tables;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }
}
