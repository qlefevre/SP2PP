package com.github.qlefevre.sp2pp.model;

import java.util.ArrayList;
import java.util.List;

public class Portfolio
{

    private String id;
    private String name;
    private String note;

    private Account referenceAccount;

    private List<PortfolioTransaction> transactions = new ArrayList<>();


    public String getId() { return id; }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public Account getReferenceAccount()
    {
        return referenceAccount;
    }

    public void setReferenceAccount(Account referenceAccount)
    {
        this.referenceAccount = referenceAccount;
    }

    public List<PortfolioTransaction> getTransactions()
    {
        return transactions;
    }

    public void addAllTransaction(List<PortfolioTransaction> transactions)
    {
        this.transactions.addAll(transactions);
    }

    @Override
    public String toString()
    {
        return name;
    }
}