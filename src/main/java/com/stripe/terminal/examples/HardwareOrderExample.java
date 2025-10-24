package com.stripe.terminal.examples;

import com.stripe.terminal.StripeTerminal;
import com.stripe.terminal.exception.StripeException;
import com.stripe.terminal.model.*;
import com.stripe.terminal.service.HardwareOrderService;

/**
 * Example demonstrating how to use the Stripe Terminal Hardware Orders API.
 */
public class HardwareOrderExample {

    public static void main(String[] args) {
        // Initialize the Stripe Terminal client with your API key
        // For testing, use a test API key (sk_test_...)
        String apiKey = System.getenv("STRIPE_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("Please set STRIPE_API_KEY environment variable");
            System.exit(1);
        }

        StripeTerminal stripe = new StripeTerminal(apiKey);

        try {
            // Step 1: Retrieve available SKUs for GB market (change to your country code: US, GB, CA, etc.)
            String country = "GB"; // Change this to your country code
            System.out.println("=== Retrieving available SKUs for " + country + " ===");
            StripeList<HardwareSku> skus = stripe.hardwareSkus().list(country);
            System.out.println("Found " + skus.getData().size() + " SKUs");

            if (skus.getData().isEmpty()) {
                System.out.println("No SKUs available. Exiting.");
                return;
            }

            // Display first SKU
            HardwareSku firstSku = skus.getData().get(0);
            System.out.println("Example SKU: " + firstSku.getId());
            System.out.println("  Price: " + firstSku.getAmount() + " " + firstSku.getCurrency());
            System.out.println("  Product: " + firstSku.getProduct());
            System.out.println("  Orderable: " + firstSku.getOrderable());
            System.out.println();

            // Step 2: Retrieve available shipping methods
            System.out.println("=== Retrieving shipping methods ===");
            StripeList<ShippingMethod> shippingMethods = stripe.shippingMethods().list(country);
            System.out.println("Found " + shippingMethods.getData().size() + " shipping methods");

            if (shippingMethods.getData().isEmpty()) {
                System.out.println("No shipping methods available. Exiting.");
                return;
            }

            // Display first shipping method
            ShippingMethod firstMethod = shippingMethods.getData().get(0);
            System.out.println("Example Shipping Method: " + firstMethod.getId());
            System.out.println("  Name: " + firstMethod.getName());
            System.out.println();

            // Step 3: Create shipping details (update to match your country)
            Address address = new Address(
                "123 High Street",
                "London",
                null, // No state/province for GB
                "EC1A 1BB",
                "GB"
            );

            ShippingDetails shipping = new ShippingDetails(
                "John Smith",
                address,
                "test@example.com",
                "+447700900000"
            );
            shipping.setCompany("Test Company Ltd");

            // Step 4: Preview the order (optional but recommended)
            // Note: Preview endpoint may not be available for all regions/accounts
            System.out.println("=== Previewing order ===");
            try {
                HardwareOrder preview = stripe.hardwareOrders().preview(
                    HardwareOrderService.HardwareOrderCreateParams.builder()
                        .addHardwareOrderItem(firstSku.getId(), 2)
                        .shippingMethod(firstMethod.getId())
                        .shipping(shipping)
                        .poNumber("PO-2024-001")
                        .addMetadata("customer_id", "cus_123")
                        .build()
                );

                System.out.println("Order Preview:");
                System.out.println("  Subtotal: " + preview.getAmount() + " " + preview.getCurrency());
                System.out.println("  Tax: " + preview.getTax() + " " + preview.getCurrency());
                System.out.println("  Total: " + (preview.getAmount() + preview.getTax()) + " " + preview.getCurrency());
                System.out.println();
            } catch (StripeException e) {
                System.out.println("Preview endpoint not available (this is normal for some regions)");
                System.out.println("  Error: " + e.getMessage());
                System.out.println("  Proceeding to create order directly...");
                System.out.println();
            }

            // Step 5: Create the actual order
            System.out.println("=== Creating order ===");
            HardwareOrder order = stripe.hardwareOrders().create(
                HardwareOrderService.HardwareOrderCreateParams.builder()
                    .addHardwareOrderItem(firstSku.getId(), 2)
                    .shippingMethod(firstMethod.getId())
                    .shipping(shipping)
                    .poNumber("PO-2024-001")
                    .addMetadata("customer_id", "cus_123")
                    .build()
            );

            System.out.println("Order created successfully!");
            System.out.println("  Order ID: " + order.getId());
            System.out.println("  Status: " + order.getStatus());
            System.out.println("  Total Amount: " + (order.getAmount() + order.getTax()) + " " + order.getCurrency());
            System.out.println();

            // Step 6: Retrieve the order
            System.out.println("=== Retrieving order ===");
            HardwareOrder retrievedOrder = stripe.hardwareOrders().retrieve(order.getId());
            System.out.println("Retrieved order: " + retrievedOrder.getId());
            System.out.println("  Status: " + retrievedOrder.getStatus());
            System.out.println();

            // Step 7: List all orders
            System.out.println("=== Listing recent orders ===");
            StripeList<HardwareOrder> orders = stripe.hardwareOrders().list(10);
            System.out.println("Found " + orders.getData().size() + " orders");
            for (HardwareOrder o : orders.getData()) {
                System.out.println("  - " + o.getId() + " (" + o.getStatus() + ")");
            }
            System.out.println();

            // Step 8: Test helpers (only works in sandbox/test mode)
            if (apiKey.startsWith("sk_test_")) {
                System.out.println("=== Testing order status transitions (sandbox only) ===");

                // Mark as ready to ship
                HardwareOrder readyOrder = stripe.testHelpers().markReadyToShip(order.getId());
                System.out.println("Order marked as ready_to_ship: " + readyOrder.getStatus());

                // Mark as shipped
                HardwareOrder shippedOrder = stripe.testHelpers().ship(order.getId(), "fedex", "1234567890");
                System.out.println("Order marked as shipped: " + shippedOrder.getStatus());

                // Mark as delivered
                HardwareOrder deliveredOrder = stripe.testHelpers().deliver(order.getId());
                System.out.println("Order marked as delivered: " + deliveredOrder.getStatus());
            }

        } catch (StripeException e) {
            System.err.println("Error occurred:");
            System.err.println("  Status: " + e.getStatusCode());
            System.err.println("  Message: " + e.getMessage());
            System.err.println("  Type: " + e.getErrorType());
            System.err.println("  Code: " + e.getErrorCode());
            e.printStackTrace();
        }
    }
}
