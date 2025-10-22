package com.stripe.terminal.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents shipment tracking information.
 */
public class ShipmentTracking {
    @SerializedName("carrier")
    private String carrier; // e.g., "fedex", "ups", "usps", "dhl", "canada_post", "dpd"

    @SerializedName("tracking_number")
    private String trackingNumber;

    @SerializedName("tracking_url")
    private String trackingUrl;

    // Getters and Setters
    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingUrl() {
        return trackingUrl;
    }

    public void setTrackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }

    @Override
    public String toString() {
        return "ShipmentTracking{" +
                "carrier='" + carrier + '\'' +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", trackingUrl='" + trackingUrl + '\'' +
                '}';
    }
}
