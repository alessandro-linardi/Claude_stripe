package com.stripe.terminal;

import com.stripe.terminal.client.StripeConfig;
import com.stripe.terminal.client.StripeHttpClient;
import com.stripe.terminal.service.*;

/**
 * Main client for interacting with the Stripe Terminal Hardware Orders API.
 *
 * <p>Example usage:</p>
 * <pre>
 * StripeTerminal stripe = new StripeTerminal("sk_test_...");
 *
 * // List available SKUs
 * StripeList&lt;HardwareSku&gt; skus = stripe.hardwareSkus().list("US");
 *
 * // Create an order
 * HardwareOrder order = stripe.hardwareOrders().create(
 *     HardwareOrderService.HardwareOrderCreateParams.builder()
 *         .addHardwareOrderItem("thsku_...", 2)
 *         .shippingMethod("thsm_...")
 *         .shipping(shippingDetails)
 *         .build()
 * );
 * </pre>
 */
public class StripeTerminal {
    private final StripeHttpClient httpClient;
    private final HardwareSkuService hardwareSkuService;
    private final HardwareProductService hardwareProductService;
    private final ShippingMethodService shippingMethodService;
    private final HardwareOrderService hardwareOrderService;
    private final TestHelperService testHelperService;

    /**
     * Creates a new Stripe Terminal client with the given API key.
     *
     * @param apiKey Your Stripe API secret key
     */
    public StripeTerminal(String apiKey) {
        this(StripeConfig.builder(apiKey).build());
    }

    /**
     * Creates a new Stripe Terminal client with custom configuration.
     *
     * @param config The Stripe configuration
     */
    public StripeTerminal(StripeConfig config) {
        this.httpClient = new StripeHttpClient(config);
        this.hardwareSkuService = new HardwareSkuService(httpClient);
        this.hardwareProductService = new HardwareProductService(httpClient);
        this.shippingMethodService = new ShippingMethodService(httpClient);
        this.hardwareOrderService = new HardwareOrderService(httpClient);
        this.testHelperService = new TestHelperService(httpClient);
    }

    /**
     * Returns the Hardware SKU service for listing and retrieving SKUs.
     *
     * @return HardwareSkuService instance
     */
    public HardwareSkuService hardwareSkus() {
        return hardwareSkuService;
    }

    /**
     * Returns the Hardware Product service for listing and retrieving products.
     *
     * @return HardwareProductService instance
     */
    public HardwareProductService hardwareProducts() {
        return hardwareProductService;
    }

    /**
     * Returns the Shipping Method service for listing and retrieving shipping methods.
     *
     * @return ShippingMethodService instance
     */
    public ShippingMethodService shippingMethods() {
        return shippingMethodService;
    }

    /**
     * Returns the Hardware Order service for creating and managing orders.
     *
     * @return HardwareOrderService instance
     */
    public HardwareOrderService hardwareOrders() {
        return hardwareOrderService;
    }

    /**
     * Returns the Test Helper service for sandbox testing.
     * Only works with test API keys.
     *
     * @return TestHelperService instance
     */
    public TestHelperService testHelpers() {
        return testHelperService;
    }
}
