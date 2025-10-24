package com.stripe.terminal.service;

import com.google.gson.reflect.TypeToken;
import com.stripe.terminal.client.StripeHttpClient;
import com.stripe.terminal.exception.StripeException;
import com.stripe.terminal.model.*;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Service for interacting with Terminal Hardware Order endpoints.
 */
public class HardwareOrderService {
    private final StripeHttpClient httpClient;
    private static final String BASE_PATH = "/v1/terminal/hardware_orders";

    public HardwareOrderService(StripeHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a new Terminal Hardware Order.
     *
     * @param params The order parameters
     * @return The created Hardware Order
     * @throws StripeException if the API request fails
     */
    public HardwareOrder create(HardwareOrderCreateParams params) throws StripeException {
        Map<String, Object> requestParams = buildCreateParams(params);
        String response = httpClient.post(BASE_PATH, requestParams);
        return httpClient.getGson().fromJson(response, HardwareOrder.class);
    }

    /**
     * Previews a Terminal Hardware Order without creating it.
     * Useful for validation and showing total costs including taxes.
     * Note: This endpoint uses GET instead of POST.
     *
     * @param params The order parameters
     * @return The previewed Hardware Order
     * @throws StripeException if the API request fails
     */
    public HardwareOrder preview(HardwareOrderCreateParams params) throws StripeException {
        Map<String, Object> requestParams = buildCreateParams(params);
        String response = httpClient.getWithParams(BASE_PATH + "/preview", requestParams);
        return httpClient.getGson().fromJson(response, HardwareOrder.class);
    }

    /**
     * Retrieves a specific Terminal Hardware Order by ID.
     *
     * @param orderId The ID of the order to retrieve
     * @return The Hardware Order
     * @throws StripeException if the API request fails
     */
    public HardwareOrder retrieve(String orderId) throws StripeException {
        String response = httpClient.get(BASE_PATH + "/" + orderId, null);
        return httpClient.getGson().fromJson(response, HardwareOrder.class);
    }

    /**
     * Lists all Terminal Hardware Orders.
     *
     * @return A list of Hardware Orders
     * @throws StripeException if the API request fails
     */
    public StripeList<HardwareOrder> list() throws StripeException {
        return list(null);
    }

    /**
     * Lists all Terminal Hardware Orders with pagination.
     *
     * @param limit Optional. Limit the number of results
     * @return A list of Hardware Orders
     * @throws StripeException if the API request fails
     */
    public StripeList<HardwareOrder> list(Integer limit) throws StripeException {
        Map<String, String> params = new HashMap<>();
        if (limit != null) {
            params.put("limit", limit.toString());
        }

        String response = httpClient.get(BASE_PATH, params);
        Type type = new TypeToken<StripeList<HardwareOrder>>() {}.getType();
        return httpClient.getGson().fromJson(response, type);
    }

    /**
     * Builds the request parameters map from HardwareOrderCreateParams.
     */
    private Map<String, Object> buildCreateParams(HardwareOrderCreateParams params) {
        Map<String, Object> requestParams = new HashMap<>();

        // Hardware order items (required)
        if (params.getHardwareOrderItems() != null && !params.getHardwareOrderItems().isEmpty()) {
            List<Map<String, Object>> items = new ArrayList<>();
            for (HardwareOrderItem item : params.getHardwareOrderItems()) {
                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("terminal_hardware_sku", item.getTerminalHardwareSku());
                itemMap.put("quantity", item.getQuantity());
                items.add(itemMap);
            }
            requestParams.put("hardware_order_items", items);
        }

        // Payment type (required)
        requestParams.put("payment_type", params.getPaymentType());

        // Shipping method (required)
        requestParams.put("shipping_method", params.getShippingMethod());

        // Shipping details (required)
        if (params.getShipping() != null) {
            Map<String, Object> shipping = new HashMap<>();
            ShippingDetails shippingDetails = params.getShipping();

            shipping.put("name", shippingDetails.getName());
            shipping.put("email", shippingDetails.getEmail());
            shipping.put("phone", shippingDetails.getPhone());

            if (shippingDetails.getCompany() != null) {
                shipping.put("company", shippingDetails.getCompany());
            }

            if (shippingDetails.getAddress() != null) {
                Map<String, Object> address = new HashMap<>();
                Address addr = shippingDetails.getAddress();

                address.put("line1", addr.getLine1());
                address.put("country", addr.getCountry());
                address.put("postal_code", addr.getPostalCode());

                if (addr.getLine2() != null) {
                    address.put("line2", addr.getLine2());
                }
                if (addr.getCity() != null) {
                    address.put("city", addr.getCity());
                }
                if (addr.getState() != null) {
                    address.put("state", addr.getState());
                }

                shipping.put("address", address);
            }

            requestParams.put("shipping", shipping);
        }

        // PO number (optional)
        if (params.getPoNumber() != null) {
            requestParams.put("po_number", params.getPoNumber());
        }

        // Metadata (optional)
        if (params.getMetadata() != null && !params.getMetadata().isEmpty()) {
            requestParams.put("metadata", params.getMetadata());
        }

        return requestParams;
    }

    /**
     * Parameters for creating a Hardware Order.
     */
    public static class HardwareOrderCreateParams {
        private List<HardwareOrderItem> hardwareOrderItems;
        private String paymentType = "monthly_invoice";
        private String shippingMethod;
        private ShippingDetails shipping;
        private String poNumber;
        private Map<String, String> metadata;

        public List<HardwareOrderItem> getHardwareOrderItems() {
            return hardwareOrderItems;
        }

        public void setHardwareOrderItems(List<HardwareOrderItem> hardwareOrderItems) {
            this.hardwareOrderItems = hardwareOrderItems;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public String getShippingMethod() {
            return shippingMethod;
        }

        public void setShippingMethod(String shippingMethod) {
            this.shippingMethod = shippingMethod;
        }

        public ShippingDetails getShipping() {
            return shipping;
        }

        public void setShipping(ShippingDetails shipping) {
            this.shipping = shipping;
        }

        public String getPoNumber() {
            return poNumber;
        }

        public void setPoNumber(String poNumber) {
            this.poNumber = poNumber;
        }

        public Map<String, String> getMetadata() {
            return metadata;
        }

        public void setMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private final HardwareOrderCreateParams params = new HardwareOrderCreateParams();

            public Builder addHardwareOrderItem(String skuId, int quantity) {
                if (params.hardwareOrderItems == null) {
                    params.hardwareOrderItems = new ArrayList<>();
                }
                params.hardwareOrderItems.add(new HardwareOrderItem(skuId, quantity));
                return this;
            }

            public Builder hardwareOrderItems(List<HardwareOrderItem> items) {
                params.hardwareOrderItems = items;
                return this;
            }

            public Builder paymentType(String paymentType) {
                params.paymentType = paymentType;
                return this;
            }

            public Builder shippingMethod(String shippingMethod) {
                params.shippingMethod = shippingMethod;
                return this;
            }

            public Builder shipping(ShippingDetails shipping) {
                params.shipping = shipping;
                return this;
            }

            public Builder poNumber(String poNumber) {
                params.poNumber = poNumber;
                return this;
            }

            public Builder metadata(Map<String, String> metadata) {
                params.metadata = metadata;
                return this;
            }

            public Builder addMetadata(String key, String value) {
                if (params.metadata == null) {
                    params.metadata = new HashMap<>();
                }
                params.metadata.put(key, value);
                return this;
            }

            public HardwareOrderCreateParams build() {
                // Validation
                if (params.hardwareOrderItems == null || params.hardwareOrderItems.isEmpty()) {
                    throw new IllegalArgumentException("At least one hardware order item is required");
                }
                if (params.shippingMethod == null || params.shippingMethod.isEmpty()) {
                    throw new IllegalArgumentException("Shipping method is required");
                }
                if (params.shipping == null) {
                    throw new IllegalArgumentException("Shipping details are required");
                }

                return params;
            }
        }
    }
}
