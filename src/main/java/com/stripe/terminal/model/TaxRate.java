package com.stripe.terminal.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a tax rate applied to an order.
 */
public class TaxRate {
    @SerializedName("display_name")
    private String displayName;

    @SerializedName("jurisdiction")
    private String jurisdiction;

    @SerializedName("percentage")
    private Double percentage;

    // Getters and Setters
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "TaxRate{" +
                "displayName='" + displayName + '\'' +
                ", jurisdiction='" + jurisdiction + '\'' +
                ", percentage=" + percentage +
                '}';
    }
}
