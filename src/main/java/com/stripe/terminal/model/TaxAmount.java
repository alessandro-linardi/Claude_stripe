package com.stripe.terminal.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a tax amount applied to an order.
 */
public class TaxAmount {
    @SerializedName("amount")
    private Long amount;

    @SerializedName("inclusive")
    private Boolean inclusive;

    @SerializedName("rate")
    private TaxRate rate;

    // Getters and Setters
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Boolean getInclusive() {
        return inclusive;
    }

    public void setInclusive(Boolean inclusive) {
        this.inclusive = inclusive;
    }

    public TaxRate getRate() {
        return rate;
    }

    public void setRate(TaxRate rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "TaxAmount{" +
                "amount=" + amount +
                ", inclusive=" + inclusive +
                ", rate=" + rate +
                '}';
    }
}
