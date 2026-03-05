package com.github.qlefevre.sp2pp.settings;

import java.util.ArrayList;
import java.util.List;

public class Configurations {
    private List<Config> config = new ArrayList<>();

    public Configurations() {
    }

    public List<Config> getConfig() {
        return config;
    }

    public void setConfig(List<Config> config) {
        this.config = config;
    }
}