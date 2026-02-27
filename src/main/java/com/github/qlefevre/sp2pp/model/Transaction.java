package com.github.qlefevre.sp2pp.model;

import java.time.LocalDateTime;

public abstract class Transaction
{

    private LocalDateTime date;
    private String currencyCode;
    private long amount;

    private Security security;
    private long shares;
    private String note;
    private String source;


    public Transaction(LocalDateTime date, String currencyCode, long amount)
    {
        this(date, currencyCode, amount, null, 0, null);
    }

    public Transaction(LocalDateTime date, String currencyCode, long amount, Security security, long shares,
                    String note)
    {
        this.date = date;
        this.currencyCode = currencyCode;
        this.amount = amount;
        this.security = security;
        this.shares = shares;
        this.note = note;
    }



    public LocalDateTime getDateTime()
    {
        return date;
    }

    public void setDateTime(LocalDateTime date)
    {
        this.date = date;

    }

    public String getCurrencyCode()
    {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode)
    {
        this.currencyCode = currencyCode;

    }

    /**
     * Returns the net value of the transaction denoted in its
     * {@link #getCurrencyCode() currency}. Possible taxes and fees will have
     * already been deducted or added, respectively.
     *
     * @see #getMonetaryAmount
     * @see #getGrossValue
     */
    public long getAmount()
    {
        return amount;
    }

    public void setAmount(long amount)
    {
        this.amount = amount;
  
    }

    public Security getSecurity()
    {
        return security;
    }

   
    public void setSecurity(Security security)
    {
        this.security = security;

    }

    public long getShares()
    {
        return shares;
    }

    public void setShares(long shares)
    {
        this.shares = shares;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
  }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

}