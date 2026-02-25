package com.github.qlefevre.sp2pp.model;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private String id;
    private String version;
    private String baseCurrency;
    private List<Security> securities;

    public Client(String id) {
        this.id = id;
        this.version = "68";
        this.baseCurrency = "EUR";
        this.securities = new ArrayList<>();
    }

    public void addSecurity(Security security) {
        this.securities.add(security);
    }

    // Getters
    public String getId() { return id; }
    public String getVersion() { return version; }
    public String getBaseCurrency() { return baseCurrency; }
    public List<Security> getSecurities() { return securities; }
}
