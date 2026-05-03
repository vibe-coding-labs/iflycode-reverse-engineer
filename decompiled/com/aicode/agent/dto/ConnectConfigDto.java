/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto;

public class ConnectConfigDto {
    private String id;
    private String client;
    private String host;
    private String port;
    private String user;
    private String password;
    private String database;

    public ConnectConfigDto() {
    }

    public ConnectConfigDto(String client, String host, String port) {
        this.client = client;
        this.host = host;
        this.port = port;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient() {
        return this.client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
