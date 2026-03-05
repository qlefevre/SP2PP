package com.github.qlefevre.sp2pp.settings;

public class Config {
    private String uuid;
    private String name;
    private String data;

    public Config() {
    }

    public Config(String uuid, String name, String data) {
        this.uuid = uuid;
        this.name = name;
        this.data = data;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}