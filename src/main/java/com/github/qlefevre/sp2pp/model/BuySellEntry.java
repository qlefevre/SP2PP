package com.github.qlefevre.sp2pp.model;


public class BuySellEntry implements CrossEntry
{
    private Portfolio portfolio;
    private PortfolioTransaction portfolioTransaction;
    private Account account;
    private AccountTransaction accountTransaction;

    public BuySellEntry(Portfolio portfolio, PortfolioTransaction portfolioTx, Account account,
                    AccountTransaction accountTx)
    {
        this.portfolio = portfolio;
        this.portfolioTransaction = portfolioTx;
        this.portfolioTransaction.setCrossEntry(this);

        this.account = account;
        this.accountTransaction = accountTx;
        this.accountTransaction.setCrossEntry(this);
    }

    public void setPortfolio(Portfolio portfolio)
    {
        this.portfolio = portfolio;
    }

    public Portfolio getPortfolio()
    {
        return this.portfolio;
    }

    public void setAccount(Account account)
    {
        this.account = account;
    }

    public Account getAccount()
    {
        return this.account;
    }

    public PortfolioTransaction getPortfolioTransaction()
    {
        return portfolioTransaction;
    }

    public AccountTransaction getAccountTransaction()
    {
        return accountTransaction;
    }
}
