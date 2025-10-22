package com.stripe.terminal.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a Terminal Hardware Shipping Method.
 */
public class ShippingMethod {
    @SerializedName("id")
    private String id;

    @SerializedName("object")
    private String object;

    @SerializedName("name")
    private String name; // e.g., "standard", "expedited"

    @SerializedName("country")
    private String country;

    @SerializedName("status")
    private String status; // "available" or "unavailable"

    @SerializedName("unavailable_after")
    private Long unavailableAfter; // Unix timestamp

    @SerializedName("provider")
    private String provider;

    @SerializedName("estimated_delivery_days")
    private Integer estimatedDeliveryDays;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public Integer getEstimatedDeliveryDays() {
        return estimatedDeliveryDays;
    }

    public void setEstimatedDeliveryDays(Integer estimatedDeliveryDays) {
        this.estimatedDeliveryDays = estimatedDeliveryDays;
    }

    @Override
    public String toString() {
        return "ShippingMethod{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", status='" + status + '\'' +
                ", unavailableAfter=" + unavailableAfter +
                ", provider='" + provider + '\'' +
                ", estimatedDeliveryDays=" + estimatedDeliveryDays +
                '}';
    }
}
