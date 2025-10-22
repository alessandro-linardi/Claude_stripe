package com.stripe.terminal.service;

import com.google.gson.reflect.TypeToken;
import com.stripe.terminal.client.StripeHttpClient;
import com.stripe.terminal.exception.StripeException;
import com.stripe.terminal.model.HardwareProduct;
import com.stripe.terminal.model.StripeList;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for interacting with Terminal Hardware Product endpoints.
 */
public class HardwareProductService {
    private final StripeHttpClient httpClient;
    private static final String BASE_PATH = "/v1/terminal/hardware_products";

    public HardwareProductService(StripeHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Lists all Terminal Hardware Products.
     *
     * @return A list of Hardware Products
     * @throws StripeException if the API request fails
     */
    public StripeList<HardwareProduct> list() throws StripeException {
        return list(null);
    }

    /**
     * Lists all Terminal Hardware Products with pagination.
     *
     * @param limit Optional. Limit the number of results
     * @return A list of Hardware Products
     * @throws StripeException if the API request fails
     */
    public StripeList<HardwareProduct> list(Integer limit) throws StripeException {
        Map<String, String> params = new HashMap<>();
        if (limit != null) {
            params.put("limit", limit.toString());
        }

        String response = httpClient.get(BASE_PATH, params);
        Type type = new TypeToken<StripeList<HardwareProduct>>() {}.getType();
        return httpClient.getGson().fromJson(response, type);
    }

    /**
     * Retrieves a specific Terminal Hardware Product by ID.
     *
     * @param productId The ID of the product to retrieve
     * @return The Hardware Product
     * @throws StripeException if the API request fails
     */
    public HardwareProduct retrieve(String productId) throws StripeException {
        String response = httpClient.get(BASE_PATH + "/" + productId, null);
        return httpClient.getGson().fromJson(response, HardwareProduct.class);
    }
}
