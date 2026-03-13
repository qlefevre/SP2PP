package com.github.qlefevre.sp2pp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Security implements InvestmentVehicle {
    private String id;
    private String name;
    private String currencyCode;
    private String isin;
    private String feed;
    private List<Price> prices;
    private Map<String, String> attributes;
    private boolean isRetired;
    private String uuid;

    public Security(String id, String name, String isin, String issuer, String strikeDate) {
        this.id = id;
        this.name = name;
        this.isin = isin;
        this.currencyCode = "EUR";
        this.feed = "MANUAL";
        this.attributes = new HashMap<>();
        this.attributes.put("issuer", issuer);
        this.attributes.put("strike date", strikeDate);
        this.isRetired = false;
        this.prices = new ArrayList<>();
        this.prices.add(new Price("2026-02-22", "100000000000"));
        this.uuid = UUID.randomUUID().toString();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getIsin() {
        return isin;
    }

    public String getFeed() {
        return feed;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public boolean isRetired() {
        return isRetired;
    }

    public String getUUID() {
        return uuid;
    }
}
