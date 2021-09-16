package com.bookeroo.microservice.payment.service;

import com.bookeroo.microservice.payment.model.*;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.bookeroo.microservice.payment.config.PaymentConstants.*;

@Service
public class PayPalService {

    private final PayPalHttpClient payPalClient;

    @Autowired
    public PayPalService(PayPalHttpClient payPalClient) {
        this.payPalClient = payPalClient;
    }

    public HttpResponse<Order> createOrder(User user, CartCheckout cartCheckout) throws IOException {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.prefer("return=representation");
        request.requestBody(buildOrderRequestBody(user, cartCheckout));
        return payPalClient.execute(request);
    }

    private OrderRequest buildOrderRequestBody(User user, CartCheckout cartCheckout) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent(CHECKOUT_PAYMENT_INTENT);

        ApplicationContext applicationContext = new ApplicationContext()
                .brandName(BRAND_NAME)
                .landingPage(LANDING_PAGE)
                .returnUrl("http://localhost:3000/")
                .shippingPreference(SHIPPING_PREFERENCE);
        orderRequest.applicationContext(applicationContext);

        List<Item> items = new ArrayList<>();
        ShippingAddress address = cartCheckout.getShippingAddress();
        double orderTotal = 0.0;
        double shipping = 0.0;

        for (OrderItem item: cartCheckout.getOrderItems()) {
            Book book = item.getBook();
            items.add(new Item()
                    .name(book.getTitle())
                    .description(book.getIsbn())
                    .unitAmount(new Money()
                            .currencyCode(CURRENCY_CODE)
                            .value(String.valueOf(round(book.getPrice()))))
                    .quantity(String.valueOf(item.getQuantity()))
                    .category(ITEM_CATEGORY));

            double itemTotal = book.getPrice() * item.getQuantity();
            orderTotal += itemTotal;
            shipping += itemTotal * SHIPPING_PERCENTAGE;
        }

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .description(DESCRIPTION)
                .amountWithBreakdown(new AmountWithBreakdown()
                        .currencyCode(CURRENCY_CODE)
                        .value(String.valueOf(round(orderTotal + shipping)))
                        .amountBreakdown(new AmountBreakdown()
                                .itemTotal(new Money().currencyCode(CURRENCY_CODE).value(String.valueOf(round(orderTotal))))
                                .shipping(new Money().currencyCode(CURRENCY_CODE).value(String.valueOf(round(shipping))))))
                .items(items)
                .shippingDetail(new ShippingDetail()
                        .name(new Name().fullName(user.getFirstName() + " " + user.getLastName()))
                        .addressPortable(new AddressPortable()
                                .addressLine1(address.getAddressLine1())
                                .addressLine2(address.getAddressLine2())
                                .adminArea1(address.getState())
                                .adminArea2(address.getCity())
                                .postalCode(address.getPostalCode())
                                .countryCode(COUNTRY_CODE)));

        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }

    public HttpResponse<Order> captureOrder(String orderId) throws IOException {
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        request.prefer("return=representation");
        request.requestBody(new OrderRequest());
        return payPalClient.execute(request);
    }

    public HttpResponse<Order> getOrder(String orderId) throws IOException {
        OrdersGetRequest request = new OrdersGetRequest(orderId);
        return payPalClient.execute(request);
    }

    private double round(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

}
