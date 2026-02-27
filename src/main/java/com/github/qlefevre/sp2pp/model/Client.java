package com.github.qlefevre.sp2pp.model;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private String id;
    private String version;
    private String baseCurrency;
    private List<Security> securities;
    private List<Account> accounts = new ArrayList<>();
    private List<Portfolio> portfolios = new ArrayList<>();

    public Client(String id) {
        this.id = id;
        this.version = "68";
        this.baseCurrency = "EUR";
        this.securities = new ArrayList<>();
    }

    public void addSecurity(Security security) {
        this.securities.add(security);
    }

    public void addPortfolio(Portfolio portfolio) {
        this.portfolios.add(portfolio);
    }

     public void addAccount(Account account)
    {
        accounts.add(account);
    }

    public List<Account> getAccounts()
    {
        return accounts;
    }


    // Getters
    public String getId() { return id; }
    public String getVersion() { return version; }
    public String getBaseCurrency() { return baseCurrency; }
    public List<Security> getSecurities() { return securities; }
    public List<Portfolio> getPortfolios() { return portfolios; }
   
}
