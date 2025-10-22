package com.stripe.terminal.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a shipping address for a Terminal Hardware Order.
 */
public class Address {
    @SerializedName("line1")
    private String line1; // Required: 3-40 characters

    @SerializedName("line2")
    private String line2; // Optional: max 40 characters

    @SerializedName("city")
    private String city; // Optional: max 35 characters

    @SerializedName("state")
    private String state; // Optional: max 29 characters

    @SerializedName("postal_code")
    private String postalCode; // Required: max 10 characters

    @SerializedName("country")
    private String country; // Required: ISO 3166-1 alpha-2

    public Address() {}

    public Address(String line1, String city, String state, String postalCode, String country) {
        this.line1 = line1;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
    }

    // Getters and Setters
    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "line1='" + line1 + '\'' +
                ", line2='" + line2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
