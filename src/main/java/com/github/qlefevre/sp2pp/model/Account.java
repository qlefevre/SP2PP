package com.github.qlefevre.sp2pp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Account {
    private String id;
    private String name;
    private String currencyCode;
    private String note;

    private List<AccountTransaction> transactions = new ArrayList<>();

    private Map<String, String> attributes;
    private String uuid;

    public Account(String name, String id) {
        this.name = name;
        this.id = id;
        this.currencyCode = "EUR";
        this.attributes = new HashMap<>();
        this.uuid = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;

    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<AccountTransaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(AccountTransaction transaction) {
        this.transactions.add(transaction);
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String getId() {
        return id;
    }

    public String getUUID() {
        return uuid;
    }

    @Override
    public String toString() {
        return getName();
    }

}