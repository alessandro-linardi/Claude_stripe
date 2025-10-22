package com.stripe.terminal.service;

import com.google.gson.reflect.TypeToken;
import com.stripe.terminal.client.StripeHttpClient;
import com.stripe.terminal.exception.StripeException;
import com.stripe.terminal.model.HardwareSku;
import com.stripe.terminal.model.StripeList;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for interacting with Terminal Hardware SKU endpoints.
 */
public class HardwareSkuService {
    private final StripeHttpClient httpClient;
    private static final String BASE_PATH = "/v1/terminal/hardware_skus";

    public HardwareSkuService(StripeHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Lists all available Terminal Hardware SKUs.
     *
     * @param country Required. The country to filter SKUs by (ISO 3166-1 alpha-2)
     * @return A list of Hardware SKUs
     * @throws StripeException if the API request fails
     */
    public StripeList<HardwareSku> list(String country) throws StripeException {
        return list(country, null, null, null);
    }

    /**
     * Lists all available Terminal Hardware SKUs with optional filters.
     *
     * @param country  Required. The country to filter SKUs by (ISO 3166-1 alpha-2)
     * @param product  Optional. Filter by hardware product ID
     * @param provider Optional. Filter by provider (default: "stripe")
     * @param limit    Optional. Limit the number of results
     * @return A list of Hardware SKUs
     * @throws StripeException if the API request fails
     */
    public StripeList<HardwareSku> list(String country, String product, String provider, Integer limit) throws StripeException {
        Map<String, String> params = new HashMap<>();
        params.put("country", country);

        if (product != null) {
            params.put("product", product);
        }
        if (provider != null) {
            params.put("provider", provider);
        }
        if (limit != null) {
            params.put("limit", limit.toString());
        }

        String response = httpClient.get(BASE_PATH, params);
        Type type = new TypeToken<StripeList<HardwareSku>>() {}.getType();
        return httpClient.getGson().fromJson(response, type);
    }

    /**
     * Retrieves a specific Terminal Hardware SKU by ID.
     *
     * @param skuId The ID of the SKU to retrieve
     * @return The Hardware SKU
     * @throws StripeException if the API request fails
     */
    public HardwareSku retrieve(String skuId) throws StripeException {
        String response = httpClient.get(BASE_PATH + "/" + skuId, null);
        return httpClient.getGson().fromJson(response, HardwareSku.class);
    }
}
