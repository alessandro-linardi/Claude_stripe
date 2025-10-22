# Stripe Terminal Hardware Orders API - Java Client

A comprehensive Java client library for the Stripe Terminal Hardware Orders API (Preview). This library enables programmatic purchasing and management of Terminal readers and accessories.

## Features

- Complete API coverage for Hardware Orders, SKUs, Products, and Shipping Methods
- Type-safe request/response models
- Comprehensive error handling
- Preview orders before creating them
- Test helpers for sandbox testing
- Builder pattern for easy order creation

## Requirements

- Java 11 or higher
- Maven 3.6+
- Stripe Account with Terminal Hardware Orders API access (Preview)

## Important Note

The Terminal Hardware Orders API is currently in **preview**. To qualify for preview access, you must:

- Have a Stripe Account manager
- Agree to monthly invoice billing
- Understand this is a preview which might require timely updates

Contact your sales representative to assess your eligibility.

## Installation

### Maven

Add this dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.stripe.terminal</groupId>
    <artifactId>stripe-hardware-orders</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### Build from Source

```bash
git clone https://github.com/yourusername/stripe-hardware-orders.git
cd stripe-hardware-orders
mvn clean install
```

## Quick Start

```java
import com.stripe.terminal.StripeTerminal;
import com.stripe.terminal.model.*;
import com.stripe.terminal.service.HardwareOrderService;

// Initialize the client
StripeTerminal stripe = new StripeTerminal("sk_test_...");

// 1. Retrieve available SKUs for a country
StripeList<HardwareSku> skus = stripe.hardwareSkus().list("US");

// 2. Retrieve shipping methods
StripeList<ShippingMethod> methods = stripe.shippingMethods().list("US");

// 3. Create shipping address
Address address = new Address(
    "1234 Main Street",
    "San Francisco",
    "CA",
    "94111",
    "US"
);

ShippingDetails shipping = new ShippingDetails(
    "Jenny Rosen",
    address,
    "test@example.com",
    "15555555555"
);
shipping.setCompany("Rocket Rides");

// 4. Preview the order (optional)
HardwareOrder preview = stripe.hardwareOrders().preview(
    HardwareOrderService.HardwareOrderCreateParams.builder()
        .addHardwareOrderItem("thsku_...", 2)
        .shippingMethod("thsm_...")
        .shipping(shipping)
        .build()
);

System.out.println("Total: " + (preview.getAmount() + preview.getTax()));

// 5. Create the order
HardwareOrder order = stripe.hardwareOrders().create(
    HardwareOrderService.HardwareOrderCreateParams.builder()
        .addHardwareOrderItem("thsku_...", 2)
        .shippingMethod("thsm_...")
        .shipping(shipping)
        .poNumber("PO-2024-001")
        .addMetadata("customer_id", "cus_123")
        .build()
);

System.out.println("Order created: " + order.getId());
```

## API Reference

### StripeTerminal Client

Main client for accessing all services:

```java
StripeTerminal stripe = new StripeTerminal("sk_test_...");

// Access services
stripe.hardwareSkus()       // HardwareSkuService
stripe.hardwareProducts()   // HardwareProductService
stripe.shippingMethods()    // ShippingMethodService
stripe.hardwareOrders()     // HardwareOrderService
stripe.testHelpers()        // TestHelperService
```

### Hardware SKUs

Retrieve available SKUs for ordering:

```java
// List SKUs for a country
StripeList<HardwareSku> skus = stripe.hardwareSkus().list("US");

// Filter by product
StripeList<HardwareSku> skus = stripe.hardwareSkus()
    .list("US", "thpr_ProductId", null, 10);

// Retrieve specific SKU
HardwareSku sku = stripe.hardwareSkus().retrieve("thsku_...");
```

### Hardware Products

Retrieve product information:

```java
// List all products
StripeList<HardwareProduct> products = stripe.hardwareProducts().list();

// Retrieve specific product
HardwareProduct product = stripe.hardwareProducts().retrieve("thpr_...");
```

### Shipping Methods

Retrieve available shipping methods:

```java
// List shipping methods for a country
StripeList<ShippingMethod> methods = stripe.shippingMethods().list("US");

// Filter by name
StripeList<ShippingMethod> methods = stripe.shippingMethods()
    .list("US", "standard", null, null);

// Retrieve specific method
ShippingMethod method = stripe.shippingMethods().retrieve("thsm_...");
```

### Hardware Orders

Create and manage orders:

```java
// Create an order
HardwareOrder order = stripe.hardwareOrders().create(
    HardwareOrderService.HardwareOrderCreateParams.builder()
        .addHardwareOrderItem("thsku_...", 2)
        .shippingMethod("thsm_...")
        .shipping(shippingDetails)
        .poNumber("PO-2024-001")
        .addMetadata("key", "value")
        .build()
);

// Preview an order
HardwareOrder preview = stripe.hardwareOrders().preview(params);

// Retrieve an order
HardwareOrder order = stripe.hardwareOrders().retrieve("thor_...");

// List orders
StripeList<HardwareOrder> orders = stripe.hardwareOrders().list(10);
```

### Test Helpers

Simulate order status transitions in sandbox:

```java
// Only works with test API keys (sk_test_...)

// Mark as ready to ship
HardwareOrder order = stripe.testHelpers().markReadyToShip("thor_...");

// Ship the order
HardwareOrder order = stripe.testHelpers().ship("thor_...", "fedex", "1234567890");

// Deliver the order
HardwareOrder order = stripe.testHelpers().deliver("thor_...");

// Mark as undeliverable
HardwareOrder order = stripe.testHelpers().markUndeliverable("thor_...");
```

## Order Statuses

- `pending` - Order created, awaiting fulfillment
- `ready_to_ship` - Order is ready to ship (not cancelable)
- `shipped` - Order has been shipped
- `delivered` - Order has been delivered
- `canceled` - Order was canceled
- `undeliverable` - Order could not be delivered

## Important Considerations

### API Version Header

This library automatically includes the required beta header:
```
Stripe-Version: 2025-09-30.clover;terminal_hardware_orders_beta=v5
```

### SKU and Product Availability

SKUs and Products can become obsolete over time. Always:

- Check the `status` field (available/unavailable)
- Monitor the `unavailable_after` timestamp
- Query SKUs dynamically rather than hardcoding IDs

### Phone Numbers

International phone numbers must be prefixed with `+`:
```java
// US number (no prefix needed)
shipping.setPhone("15555555555");

// International number (+ prefix required)
shipping.setPhone("+358131234567");
```

### Email Notifications

The email address in `shipping.email` will receive Stripe-branded notifications when order status changes.

### Taxes

Stripe automatically calculates taxes based on the shipping address. The tax amounts are returned in the `tax` and `total_tax_amounts` fields.

### Monthly Invoicing

During preview, all orders are billed via monthly invoice. Invoices are sent to the email configured in your Stripe Dashboard.

## Error Handling

```java
try {
    HardwareOrder order = stripe.hardwareOrders().create(params);
} catch (StripeException e) {
    System.err.println("Status: " + e.getStatusCode());
    System.err.println("Message: " + e.getMessage());
    System.err.println("Type: " + e.getErrorType());
    System.err.println("Code: " + e.getErrorCode());
    System.err.println("Request ID: " + e.getRequestId());
}
```

## Examples

See the `src/main/java/com/stripe/terminal/examples/` directory for complete examples:

- `HardwareOrderExample.java` - Complete order creation workflow

Run the example:

```bash
export STRIPE_API_KEY=sk_test_...
mvn exec:java -Dexec.mainClass="com.stripe.terminal.examples.HardwareOrderExample"
```

## Webhooks

Set up webhooks to receive order status updates. Add the required header version to your webhook endpoints:

```
Stripe-Version: 2025-09-30.clover;terminal_hardware_orders_beta=v5
```

Supported events:
- `terminal.hardware_order.created`
- `terminal.hardware_order.canceled`
- `terminal.hardware_order.ready_to_ship`
- `terminal.hardware_order.shipped`
- `terminal.hardware_order.delivered`
- `terminal.hardware_order.undeliverable`

## Development

### Build

```bash
mvn clean compile
```

### Run Tests

```bash
mvn test
```

### Package

```bash
mvn package
```

## Documentation

- [Stripe Terminal Hardware Orders API](https://docs.stripe.com/terminal/fleet/order-and-return-readers#use-the-hardware-orders-api)
- [API Reference](https://docs.stripe.com/api/terminal/hardware_orders/create)

## Support

For issues and questions:
- GitHub Issues: [Report an issue](https://github.com/yourusername/stripe-hardware-orders/issues)
- Stripe Support: Contact your account manager

## License

See LICENSE file for details.

## Changelog

### v1.0.0-SNAPSHOT
- Initial implementation
- Support for all Hardware Orders API endpoints
- SKU, Product, and Shipping Method retrieval
- Order preview and creation
- Test helpers for sandbox testing
- Complete Java models and type safety
