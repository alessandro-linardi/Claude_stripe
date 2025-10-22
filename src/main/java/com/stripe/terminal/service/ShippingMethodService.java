package com.stripe.terminal.service;

import com.google.gson.reflect.TypeToken;
import com.stripe.terminal.client.StripeHttpClient;
import com.stripe.terminal.exception.StripeException;
import com.stripe.terminal.model.ShippingMethod;
import com.stripe.terminal.model.StripeList;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for interacting with Terminal Hardware Shipping Method endpoints.
 */
public class ShippingMethodService {
    private final StripeHttpClient httpClient;
    private static final String BASE_PATH = "/v1/terminal/hardware_shipping_methods";

    public ShippingMethodService(StripeHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Lists all available Terminal Hardware Shipping Methods.
     *
     * @param country Required. The country to filter shipping methods by (ISO 3166-1 alpha-2)
     * @return A list of Shipping Methods
     * @throws StripeException if the API request fails
     */
    public StripeList<ShippingMethod> list(String country) throws StripeException {
        return list(country, null, null, null);
    }

    /**
     * Lists all available Terminal Hardware Shipping Methods with optional filters.
     *
     * @param country  Required. The country to filter shipping methods by (ISO 3166-1 alpha-2)
     * @param name     Optional. Filter by shipping method name (e.g., "standard", "expedited")
     * @param provider Optional. Filter by provider (default: "stripe")
     * @param limit    Optional. Limit the number of results
     * @return A list of Shipping Methods
     * @throws StripeException if the API request fails
     */
    public StripeList<ShippingMethod> list(String country, String name, String provider, Integer limit) throws StripeException {
        Map<String, String> params = new HashMap<>();
        params.put("country", country);

        if (name != null) {
            params.put("name", name);
        }
        if (provider != null) {
            params.put("provider", provider);
        }
        if (limit != null) {
            params.put("limit", limit.toString());
        }

        String response = httpClient.get(BASE_PATH, params);
        Type type = new TypeToken<StripeList<ShippingMethod>>() {}.getType();
        return httpClient.getGson().fromJson(response, type);
    }

    /**
     * Retrieves a specific Terminal Hardware Shipping Method by ID.
     *
     * @param methodId The ID of the shipping method to retrieve
     * @return The Shipping Method
     * @throws StripeException if the API request fails
     */
    public ShippingMethod retrieve(String methodId) throws StripeException {
        String response = httpClient.get(BASE_PATH + "/" + methodId, null);
        return httpClient.getGson().fromJson(response, ShippingMethod.class);
    }
}
