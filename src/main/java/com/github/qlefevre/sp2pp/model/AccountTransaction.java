package com.github.qlefevre.sp2pp.model;

import java.time.LocalDate;

public class AccountTransaction extends Transaction{

    public enum Type
    {
        DEPOSIT(false), REMOVAL(true), //
        INTEREST(false), INTEREST_CHARGE(true), //
        DIVIDENDS(false), //
        FEES(true), FEES_REFUND(false), //
        TAXES(true), TAX_REFUND(false), //
        BUY(true), SELL(false), //
        TRANSFER_IN(false), TRANSFER_OUT(true);

        private final boolean isDebit;

        private Type(boolean isDebit)
        {
            this.isDebit = isDebit;
        }

        public boolean isDebit()
        {
            return isDebit;
        }

        public boolean isCredit()
        {
            return !isDebit;
        }

        @Override
        public String toString()
        {
            return name(); //$NON-NLS-1$
        }
    }

    private Type type;

    public AccountTransaction(LocalDate date, String currencyCode, long amount, Security security, Type type)
    {
        super(date, currencyCode, amount, security, 0, null);
        this.type = type;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;

    }

}