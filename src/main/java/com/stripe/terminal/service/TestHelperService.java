package com.stripe.terminal.service;

import com.stripe.terminal.client.StripeHttpClient;
import com.stripe.terminal.exception.StripeException;
import com.stripe.terminal.model.HardwareOrder;

import java.util.HashMap;

/**
 * Service for sandbox testing helpers.
 * These endpoints only work in test mode to simulate order status transitions.
 */
public class TestHelperService {
    private final StripeHttpClient httpClient;
    private static final String BASE_PATH = "/v1/test_helpers/terminal/hardware_orders";

    public TestHelperService(StripeHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Marks a test order as ready to ship.
     * Only works in sandbox/test mode.
     *
     * @param orderId The ID of the order
     * @return The updated Hardware Order
     * @throws StripeException if the API request fails
     */
    public HardwareOrder markReadyToShip(String orderId) throws StripeException {
        String path = BASE_PATH + "/" + orderId + "/mark_ready_to_ship";
        String response = httpClient.post(path, new HashMap<>());
        return httpClient.getGson().fromJson(response, HardwareOrder.class);
    }

    /**
     * Marks a test order as shipped with optional tracking information.
     * Only works in sandbox/test mode.
     *
     * @param orderId        The ID of the order
     * @param carrier        Optional. The shipping carrier (e.g., "fedex", "ups", "usps")
     * @param trackingNumber Optional. The tracking number
     * @return The updated Hardware Order
     * @throws StripeException if the API request fails
     */
    public HardwareOrder ship(String orderId, String carrier, String trackingNumber) throws StripeException {
        String path = BASE_PATH + "/" + orderId + "/ship";

        HashMap<String, Object> params = new HashMap<>();
        if (carrier != null) {
            params.put("carrier", carrier);
        }
        if (trackingNumber != null) {
            params.put("tracking_number", trackingNumber);
        }

        String response = httpClient.post(path, params);
        return httpClient.getGson().fromJson(response, HardwareOrder.class);
    }

    /**
     * Marks a test order as shipped without tracking information.
     * Only works in sandbox/test mode.
     *
     * @param orderId The ID of the order
     * @return The updated Hardware Order
     * @throws StripeException if the API request fails
     */
    public HardwareOrder ship(String orderId) throws StripeException {
        return ship(orderId, null, null);
    }

    /**
     * Marks a test order as delivered.
     * Only works in sandbox/test mode.
     *
     * @param orderId The ID of the order
     * @return The updated Hardware Order
     * @throws StripeException if the API request fails
     */
    public HardwareOrder deliver(String orderId) throws StripeException {
        String path = BASE_PATH + "/" + orderId + "/deliver";
        String response = httpClient.post(path, new HashMap<>());
        return httpClient.getGson().fromJson(response, HardwareOrder.class);
    }

    /**
     * Marks a test order as undeliverable.
     * Only works in sandbox/test mode.
     *
     * @param orderId The ID of the order
     * @return The updated Hardware Order
     * @throws StripeException if the API request fails
     */
    public HardwareOrder markUndeliverable(String orderId) throws StripeException {
        String path = BASE_PATH + "/" + orderId + "/mark_undeliverable";
        String response = httpClient.post(path, new HashMap<>());
        return httpClient.getGson().fromJson(response, HardwareOrder.class);
    }
}
