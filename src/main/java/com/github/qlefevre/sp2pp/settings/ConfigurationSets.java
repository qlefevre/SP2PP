package com.github.qlefevre.sp2pp.settings;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationSets {

    private List<ConfigEntry> entry = new ArrayList<>();

    public ConfigurationSets() {
    }

    public List<ConfigEntry> getEntry() {
        return entry;
    }

    public void setEntry(List<ConfigEntry> entry) {
        this.entry = entry;
    }
}