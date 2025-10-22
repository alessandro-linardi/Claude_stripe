package com.stripe.terminal.client;

/**
 * Configuration class for Stripe API client.
 */
public class StripeConfig {
    private final String apiKey;
    private final String apiVersion;
    private final String baseUrl;

    public static final String DEFAULT_BASE_URL = "https://api.stripe.com";
    public static final String DEFAULT_API_VERSION = "2025-09-30.clover;terminal_hardware_orders_beta=v5";

    private StripeConfig(Builder builder) {
        this.apiKey = builder.apiKey;
        this.apiVersion = builder.apiVersion != null ? builder.apiVersion : DEFAULT_API_VERSION;
        this.baseUrl = builder.baseUrl != null ? builder.baseUrl : DEFAULT_BASE_URL;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public static Builder builder(String apiKey) {
        return new Builder(apiKey);
    }

    public static class Builder {
        private final String apiKey;
        private String apiVersion;
        private String baseUrl;

        public Builder(String apiKey) {
            if (apiKey == null || apiKey.trim().isEmpty()) {
                throw new IllegalArgumentException("API key cannot be null or empty");
            }
            this.apiKey = apiKey;
        }

        public Builder apiVersion(String apiVersion) {
            this.apiVersion = apiVersion;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public StripeConfig build() {
            return new StripeConfig(this);
        }
    }
}
