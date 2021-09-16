package com.bookeroo.microservice.payment.service;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public HttpResponse<Order> createOrder() throws IOException {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.prefer("return=representation");
        request.requestBody(buildOrderRequestBody());


        return payPalClient.execute(request);
    }

    private OrderRequest buildOrderRequestBody() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent(CHECKOUT_PAYMENT_INTENT);

        ApplicationContext applicationContext = new ApplicationContext()
                .brandName(BRAND_NAME)
                .landingPage(LANDING_PAGE)
                .shippingPreference(SHIPPING_PREFERENCE);
        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .description(DESCRIPTION)
                .amountWithBreakdown(new AmountWithBreakdown()
                        .currencyCode(CURRENCY_CODE)
                        .value("10006.00")
                        .amountBreakdown(new AmountBreakdown()
                                .itemTotal(new Money().currencyCode(CURRENCY_CODE).value("10004.00"))
                                .shipping(new Money().currencyCode(CURRENCY_CODE).value("2.00"))))
                .items(new ArrayList<Item>() {
                    {
                        add(new Item()
                                .name("The Brothers Karamazov")
                                .description("9780192835093")
                                .unitAmount(new Money()
                                        .currencyCode(CURRENCY_CODE)
                                        .value("10000.00"))
                                .quantity("1")
                                .category("PHYSICAL_GOODS"));
                        add(new Item()
                                .name("The Old Man and the Sea")
                                .description("9788804325963")
                                .unitAmount(new Money()
                                        .currencyCode(CURRENCY_CODE)
                                        .value("2.00"))
                                .quantity("2")
                                .category("PHYSICAL_GOODS"));
                    }
                })
                .shippingDetail(new ShippingDetail()
                        .name(new Name()
                                .fullName("John Doe"))
                        .addressPortable(new AddressPortable()
                                .addressLine1("123 Bookeroo St")
                                .addressLine2("Floor 1")
                                .adminArea2("Melbourne>")
                                .adminArea1("VIC")
                                .postalCode("3001")
                                .countryCode("AU")));

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

}
