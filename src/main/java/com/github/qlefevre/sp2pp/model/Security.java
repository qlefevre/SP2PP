package com.github.qlefevre.sp2pp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Security {
    private String id;
    private String name;
    private String currencyCode;
    private String isin;
    private String feed;
    private List<Price> prices;
    private Map<String, String> attributes;
    private boolean isRetired;

    public Security(String id, String name, String isin, String issuer) {
        this.id = id;
        this.name = name;
        this.isin = isin;
        this.currencyCode = "EUR";
        this.feed = "MANUAL";
        this.attributes = new HashMap<>();
        this.attributes.put("issuer", issuer);
        this.isRetired = false;
        this.prices = new ArrayList<>();
        this.prices.add(new Price("2026-02-22", "100000000000")); 
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCurrencyCode() { return currencyCode; }
    public String getIsin() { return isin; }
    public String getFeed() { return feed; }
    public List<Price> getPrices() { return prices; }
    public Map<String, String> getAttributes() { return attributes; }
    public boolean isRetired() { return isRetired; }
}
