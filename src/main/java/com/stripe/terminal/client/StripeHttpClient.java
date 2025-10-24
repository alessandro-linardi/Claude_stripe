package com.stripe.terminal.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.stripe.terminal.exception.StripeException;
import okhttp3.*;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * HTTP client for making requests to the Stripe API.
 */
public class StripeHttpClient {
    private final OkHttpClient httpClient;
    private final StripeConfig config;
    private final Gson gson;

    public StripeHttpClient(StripeConfig config) {
        this.config = config;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Makes a GET request to the Stripe API.
     */
    public String get(String path, Map<String, String> params) throws StripeException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(config.getBaseUrl() + path).newBuilder();

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .headers(buildHeaders())
                .build();

        return executeRequest(request);
    }

    /**
     * Makes a GET request to the Stripe API with complex parameters.
     */
    public String getWithParams(String path, Map<String, Object> params) throws StripeException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(config.getBaseUrl() + path).newBuilder();

        if (params != null) {
            addParamsToQuery(urlBuilder, params, "");
        }

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .headers(buildHeaders())
                .build();

        return executeRequest(request);
    }

    /**
     * Makes a POST request to the Stripe API.
     */
    public String post(String path, Map<String, Object> params) throws StripeException {
        FormBody.Builder formBuilder = new FormBody.Builder();

        if (params != null) {
            addParamsToForm(formBuilder, params, "");
        }

        Request request = new Request.Builder()
                .url(config.getBaseUrl() + path)
                .post(formBuilder.build())
                .headers(buildHeaders())
                .build();

        return executeRequest(request);
    }

    /**
     * Recursively adds parameters to query URL, handling nested objects and arrays.
     */
    private void addParamsToQuery(HttpUrl.Builder urlBuilder, Map<String, Object> params, String prefix) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = prefix.isEmpty() ? entry.getKey() : prefix + "[" + entry.getKey() + "]";
            Object value = entry.getValue();

            if (value == null) {
                continue;
            }

            if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> nestedMap = (Map<String, Object>) value;
                addParamsToQuery(urlBuilder, nestedMap, key);
            } else if (value instanceof java.util.List) {
                @SuppressWarnings("unchecked")
                java.util.List<Object> list = (java.util.List<Object>) value;
                for (int i = 0; i < list.size(); i++) {
                    Object item = list.get(i);
                    if (item instanceof Map) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> itemMap = (Map<String, Object>) item;
                        addParamsToQuery(urlBuilder, itemMap, key + "[" + i + "]");
                    } else {
                        urlBuilder.addQueryParameter(key + "[]", item.toString());
                    }
                }
            } else {
                urlBuilder.addQueryParameter(key, value.toString());
            }
        }
    }

    /**
     * Recursively adds parameters to form body, handling nested objects and arrays.
     */
    private void addParamsToForm(FormBody.Builder formBuilder, Map<String, Object> params, String prefix) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = prefix.isEmpty() ? entry.getKey() : prefix + "[" + entry.getKey() + "]";
            Object value = entry.getValue();

            if (value == null) {
                continue;
            }

            if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> nestedMap = (Map<String, Object>) value;
                addParamsToForm(formBuilder, nestedMap, key);
            } else if (value instanceof java.util.List) {
                @SuppressWarnings("unchecked")
                java.util.List<Object> list = (java.util.List<Object>) value;
                for (int i = 0; i < list.size(); i++) {
                    Object item = list.get(i);
                    if (item instanceof Map) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> itemMap = (Map<String, Object>) item;
                        addParamsToForm(formBuilder, itemMap, key + "[" + i + "]");
                    } else {
                        formBuilder.add(key + "[]", item.toString());
                    }
                }
            } else {
                formBuilder.add(key, value.toString());
            }
        }
    }

    /**
     * Builds headers for Stripe API requests.
     */
    private Headers buildHeaders() {
        String authValue = "Basic " + Base64.getEncoder().encodeToString((config.getApiKey() + ":").getBytes());

        return new Headers.Builder()
                .add("Authorization", authValue)
                .add("Stripe-Version", config.getApiVersion())
                .add("Content-Type", "application/x-www-form-urlencoded")
                .add("Accept", "application/json")
                .build();
    }

    /**
     * Executes an HTTP request and handles the response.
     */
    private String executeRequest(Request request) throws StripeException {
        try (Response response = httpClient.newCall(request).execute()) {
            String responseBody = response.body() != null ? response.body().string() : "";

            if (!response.isSuccessful()) {
                handleErrorResponse(response, responseBody);
            }

            return responseBody;
        } catch (IOException e) {
            throw new StripeException("Network error: " + e.getMessage(), 0);
        }
    }

    /**
     * Handles error responses from the Stripe API.
     */
    private void handleErrorResponse(Response response, String responseBody) throws StripeException {
        String requestId = response.header("Request-Id");
        int statusCode = response.code();

        try {
            JsonObject json = gson.fromJson(responseBody, JsonObject.class);
            if (json != null && json.has("error")) {
                JsonObject error = json.getAsJsonObject("error");
                String message = error.has("message") ? error.get("message").getAsString() : "Unknown error";
                String type = error.has("type") ? error.get("type").getAsString() : null;
                String code = error.has("code") ? error.get("code").getAsString() : null;

                throw new StripeException(message, statusCode, requestId, type, code);
            }
        } catch (Exception e) {
            // If we can't parse the error, throw a generic one
            if (e instanceof StripeException) {
                throw (StripeException) e;
            }
        }

        throw new StripeException("HTTP " + statusCode + ": " + responseBody, statusCode, requestId, null, null);
    }

    public Gson getGson() {
        return gson;
    }
}
