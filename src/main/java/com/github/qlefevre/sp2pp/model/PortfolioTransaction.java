package com.github.qlefevre.sp2pp.model;

import java.time.LocalDate;

public class PortfolioTransaction extends Transaction
{
    public enum Type
    {
        /** Records the purchase of a security. */
        BUY(true),
        /** Records the sale of a security. */
        SELL(false),
        /** Records the transfer of assets from another portfolio. */
        TRANSFER_IN(true),
        /** Records the transfer of assets to another portfolio. */
        TRANSFER_OUT(false),
        /** Records the transfer of assets into the portfolio. */
        DELIVERY_INBOUND(true),
        /** Records the transfer of assets out of a portfolio. */
        DELIVERY_OUTBOUND(false);

        private final boolean isPurchase;

        private Type(boolean isPurchase)
        {
            this.isPurchase = isPurchase;
        }

        /**
         * True if the transaction is one of the purchase types such as buy,
         * transfer in, or an inbound delivery.
         */
        public boolean isPurchase()
        {
            return isPurchase;
        }

        /**
         * True if the transaction is one of the liquidation types such as sell,
         * transfer out, or an outbound delivery.
         */
        public boolean isLiquidation()
        {
            return !isPurchase;
        }

        @Override
        public String toString()
        {
            return name();
        }
    }

    private Type type;

    @Deprecated
    /* package */transient long fees;

    @Deprecated
    /* package */transient long taxes;

  

    public PortfolioTransaction(LocalDate date, String currencyCode, long amount, Security security, long shares,
                    Type type)
    {
        super(date, currencyCode, amount, security, shares, null);
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