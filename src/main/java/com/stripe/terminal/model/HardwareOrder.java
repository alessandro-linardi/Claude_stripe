package com.stripe.terminal.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/**
 * Represents a Terminal Hardware Order.
 */
public class HardwareOrder {
    @SerializedName("id")
    private String id;

    @SerializedName("object")
    private String object;

    @SerializedName("amount")
    private Long amount;

    @SerializedName("created")
    private Long created;

    @SerializedName("currency")
    private String currency;

    @SerializedName("hardware_order_items")
    private List<HardwareOrderItem> hardwareOrderItems;

    @SerializedName("livemode")
    private Boolean livemode;

    @SerializedName("metadata")
    private Map<String, String> metadata;

    @SerializedName("payment_type")
    private String paymentType; // "monthly_invoice"

    @SerializedName("po_number")
    private String poNumber;

    @SerializedName("shipment_tracking")
    private List<ShipmentTracking> shipmentTracking;

    @SerializedName("shipping")
    private ShippingDetails shipping;

    @SerializedName("shipping_method")
    private String shippingMethod; // Can be string ID or object

    @SerializedName("status")
    private String status; // "pending", "ready_to_ship", "shipped", "delivered", "canceled", "undeliverable"

    @SerializedName("tax")
    private Long tax;

    @SerializedName("total_tax_amounts")
    private List<TaxAmount> totalTaxAmounts;

    @SerializedName("updated")
    private Long updated;

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

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<HardwareOrderItem> getHardwareOrderItems() {
        return hardwareOrderItems;
    }

    public void setHardwareOrderItems(List<HardwareOrderItem> hardwareOrderItems) {
        this.hardwareOrderItems = hardwareOrderItems;
    }

    public Boolean getLivemode() {
        return livemode;
    }

    public void setLivemode(Boolean livemode) {
        this.livemode = livemode;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public List<ShipmentTracking> getShipmentTracking() {
        return shipmentTracking;
    }

    public void setShipmentTracking(List<ShipmentTracking> shipmentTracking) {
        this.shipmentTracking = shipmentTracking;
    }

    public ShippingDetails getShipping() {
        return shipping;
    }

    public void setShipping(ShippingDetails shipping) {
        this.shipping = shipping;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTax() {
        return tax;
    }

    public void setTax(Long tax) {
        this.tax = tax;
    }

    public List<TaxAmount> getTotalTaxAmounts() {
        return totalTaxAmounts;
    }

    public void setTotalTaxAmounts(List<TaxAmount> totalTaxAmounts) {
        this.totalTaxAmounts = totalTaxAmounts;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "HardwareOrder{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", amount=" + amount +
                ", created=" + created +
                ", currency='" + currency + '\'' +
                ", hardwareOrderItems=" + hardwareOrderItems +
                ", livemode=" + livemode +
                ", metadata=" + metadata +
                ", paymentType='" + paymentType + '\'' +
                ", poNumber='" + poNumber + '\'' +
                ", shipmentTracking=" + shipmentTracking +
                ", shipping=" + shipping +
                ", shippingMethod='" + shippingMethod + '\'' +
                ", status='" + status + '\'' +
                ", tax=" + tax +
                ", totalTaxAmounts=" + totalTaxAmounts +
                ", updated=" + updated +
                '}';
    }
}
