package com.stripe.terminal.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents shipping details for a Terminal Hardware Order.
 */
public class ShippingDetails {
    @SerializedName("name")
    private String name; // Required: min 3, first name max 15, last name max 20

    @SerializedName("address")
    private Address address; // Required

    @SerializedName("email")
    private String email; // Required

    @SerializedName("phone")
    private String phone; // Required: max 14 characters

    @SerializedName("company")
    private String company; // Optional: 3-40 characters

    @SerializedName("amount")
    private Long amount; // Shipping cost (read-only)

    @SerializedName("currency")
    private String currency; // Currency (read-only)

    public ShippingDetails() {}

    public ShippingDetails(String name, Address address, String email, String phone) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "ShippingDetails{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", company='" + company + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
