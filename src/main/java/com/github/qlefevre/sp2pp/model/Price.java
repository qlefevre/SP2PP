package com.github.qlefevre.sp2pp.model;

public class Price {
    private String t;
    private String v;

    public Price(String date, String value) {
        this.t = date;
        this.v = value;
    }

    // Getters
    public String getT() { return t; }
    public String getV() { return v; }
}
