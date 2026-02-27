package com.github.qlefevre.sp2pp.model;

import java.util.ArrayList;
import java.util.List;

public class Account
{
    private String id;
    private String name;
    private String currencyCode;
    private String note;

    private List<AccountTransaction> transactions = new ArrayList<>();


    public Account(String name, String id)
    {
        this.name = name;
        this.id = id;
        this.currencyCode = "EUR";
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getCurrencyCode()
    {
        return currencyCode;
    }


    public void setCurrencyCode(String currencyCode)
    {
        this.currencyCode = currencyCode;
       
    }


    public String getNote()
    {
        return note;
    }


    public void setNote(String note)
    {
        this.note = note;
    }

    public List<AccountTransaction> getTransactions()
    {
        return transactions;
    }

    public void addTransaction(AccountTransaction transaction)
    {
        this.transactions.add(transaction);
    }

    @Override
    public String toString()
    {
        return getName();
    }

     public String getId() { return id; }

}