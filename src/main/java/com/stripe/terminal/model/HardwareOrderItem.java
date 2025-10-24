package com.stripe.terminal.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a line item in a Terminal Hardware Order.
 */
public class HardwareOrderItem {
    @SerializedName("terminal_hardware_sku")
    private Object terminalHardwareSku; // String for request, HardwareSku object for response

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
    /**
     * Gets the terminal hardware SKU.
     * @return String ID if this is a request object, or HardwareSku object if from response
     */
    public Object getTerminalHardwareSku() {
        return terminalHardwareSku;
    }

    /**
     * Gets the terminal hardware SKU as a String ID.
     * @return SKU ID string, or null if the object is a HardwareSku
     */
    public String getTerminalHardwareSkuId() {
        if (terminalHardwareSku instanceof String) {
            return (String) terminalHardwareSku;
        } else if (terminalHardwareSku instanceof com.google.gson.JsonObject) {
            com.google.gson.JsonObject obj = (com.google.gson.JsonObject) terminalHardwareSku;
            return obj.has("id") ? obj.get("id").getAsString() : null;
        }
        return null;
    }

    /**
     * Gets the full HardwareSku object if available.
     * @return HardwareSku object from response, or null if this is just an ID
     */
    public com.google.gson.JsonObject getTerminalHardwareSkuObject() {
        if (terminalHardwareSku instanceof com.google.gson.JsonObject) {
            return (com.google.gson.JsonObject) terminalHardwareSku;
        }
        return null;
    }

    public void setTerminalHardwareSku(String terminalHardwareSku) {
        this.terminalHardwareSku = terminalHardwareSku;
    }

    public void setTerminalHardwareSkuObject(Object terminalHardwareSku) {
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
        String skuStr = terminalHardwareSku instanceof String
            ? "'" + terminalHardwareSku + "'"
            : terminalHardwareSku != null ? terminalHardwareSku.toString() : "null";
        return "HardwareOrderItem{" +
                "terminalHardwareSku=" + skuStr +
                ", quantity=" + quantity +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
