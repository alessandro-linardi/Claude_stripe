package com.stripe.terminal.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a line item in a Terminal Hardware Order.
 */
public class HardwareOrderItem {
    @SerializedName("terminal_hardware_sku")
    private String terminalHardwareSku; // Required for creation, can be object in response

    @SerializedName("quantity")
    private Integer quantity; // Required

    @SerializedName("amount")
    private Long amount; // Total amount for this line item (read-only)

    @SerializedName("currency")
    private String currency; // Currency (read-only)

    public HardwareOrderItem() {}

    public HardwareOrderItem(String terminalHardwareSku, Integer quantity) {
        this.terminalHardwareSku = terminalHardwareSku;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getTerminalHardwareSku() {
        return terminalHardwareSku;
    }

    public void setTerminalHardwareSku(String terminalHardwareSku) {
        this.terminalHardwareSku = terminalHardwareSku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
        return "HardwareOrderItem{" +
                "terminalHardwareSku='" + terminalHardwareSku + '\'' +
                ", quantity=" + quantity +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
