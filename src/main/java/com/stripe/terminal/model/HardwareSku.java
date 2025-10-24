package com.stripe.terminal.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a Terminal Hardware SKU.
 */
public class HardwareSku {
    @SerializedName("id")
    private String id;

    @SerializedName("object")
    private String object;

    @SerializedName("amount")
    private Long amount;

    @SerializedName("country")
    private String country;

    @SerializedName("currency")
    private String currency;

    @SerializedName("product")
    private String product; // Hardware Product ID

    @SerializedName("orderable")
    private Integer orderable;

    @SerializedName("status")
    private String status; // "available" or "unavailable"

    @SerializedName("unavailable_after")
    private Long unavailableAfter; // Unix timestamp

    @SerializedName("provider")
    private String provider;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getOrderable() {
        return orderable;
    }

    public void setOrderable(Integer orderable) {
        this.orderable = orderable;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUnavailableAfter() {
        return unavailableAfter;
    }

    public void setUnavailableAfter(Long unavailableAfter) {
        this.unavailableAfter = unavailableAfter;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "HardwareSku{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", amount=" + amount +
                ", country='" + country + '\'' +
                ", currency='" + currency + '\'' +
                ", product='" + product + '\'' +
                ", orderable=" + orderable +
                ", status='" + status + '\'' +
                ", unavailableAfter=" + unavailableAfter +
                ", provider='" + provider + '\'' +
                '}';
    }
}
