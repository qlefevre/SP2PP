package com.github.qlefevre.sp2pp.model;

import java.util.ArrayList;
import java.util.List;

public class Watchlist {
    private String name;
    private List<Security> securities = new ArrayList<>();

    public Watchlist() {
    }

    public Watchlist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Security> getSecurities() {
        return securities;
    }

    public void setSecurities(List<Security> securities) {
        this.securities = securities;
    }

    public void addSecurity(Security security) {
        this.securities.add(security);
    }
}