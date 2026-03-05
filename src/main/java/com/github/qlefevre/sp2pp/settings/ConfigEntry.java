package com.github.qlefevre.sp2pp.settings;

public class ConfigEntry {
    private String string;
    private ConfigSet configSet;

    public ConfigEntry() {
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public ConfigSet getConfigSet() {
        return configSet;
    }

    public void setConfigSet(ConfigSet configSet) {
        this.configSet = configSet;
    }
}