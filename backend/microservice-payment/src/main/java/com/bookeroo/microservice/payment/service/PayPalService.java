package com.bookeroo.microservice.payment.service;

import com.bookeroo.microservice.payment.exception.ListingNotFoundException;
import com.bookeroo.microservice.payment.model.*;
import com.bookeroo.microservice.payment.repository.ListingRepository;
import com.bookeroo.microservice.payment.repository.TransactionRepository;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import com.paypal.payments.CapturesRefundRequest;
import com.paypal.payments.Refund;
import com.paypal.payments.RefundRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.bookeroo.microservice.payment.config.PaymentConstants.*;

/**
 * Service layer for {@link PayPalHttpClient}.
 */
@Service
public class PayPalService {

    private final ListingRepository listingRepository;
    private final TransactionRepository transactionRepository;
    private final PayPalHttpClient payPalClient;

    @Autowired
    public PayPalService(ListingRepository listingRepository,
                         TransactionRepository transactionRepository,
                         PayPalHttpClient payPalClient) {
        this.listingRepository = listingRepository;
        this.transactionRepository = transactionRepository;
        this.payPalClient = payPalClient;
    }

    public HttpResponse<Order> createOrder(CartCheckout cartCheckout, User user) throws IOException {
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
                .returnUrl(RETURN_URL)
                .cancelUrl(CANCEL_URL)
                .shippingPreference(SHIPPING_PREFERENCE);
        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();

        for (Listing listing : cartCheckout.getOrderItems()) {
            long listingId = listing.getId();
            Optional<Listing> existing = listingRepository.findById(listingId);
            existing.orElseThrow(() ->
                    new ListingNotFoundException(String.format("Listing by id %d not found", listingId)));
            listing = existing.get();
            if (!listing.isAvailable())
                throw new ListingNotFoundException(String.format("Listing by id %d is no longer available", listingId));

            double itemCost = Double.parseDouble(listing.getPrice());
            double shippingCost = itemCost * SHIPPING_PERCENTAGE;
            Book book = listing.getBook();
            ShippingAddress address = cartCheckout.getShippingAddress();
            PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                    .referenceId(String.valueOf(listingId))
                    .description(DESCRIPTION)
                    .amountWithBreakdown(new AmountWithBreakdown()
                            .currencyCode(CURRENCY_CODE)
                            .value(String.valueOf(round(itemCost) + round(shippingCost)))
                            .amountBreakdown(new AmountBreakdown()
                                    .itemTotal(new Money()
                                            .currencyCode(CURRENCY_CODE).value(String.valueOf(round(itemCost))))
                                    .shipping(new Money()
                                            .currencyCode(CURRENCY_CODE).value(String.valueOf(round(shippingCost))))))
                    .items(Collections.singletonList(
                            new Item()
                                    .name(book.getTitle())
                                    .description(book.getIsbn())
                                    .unitAmount(new Money()
                                            .currencyCode(CURRENCY_CODE)
                                            .value(String.valueOf(round(itemCost))))
                                    .quantity("1")
                                    .category(ITEM_CATEGORY)))
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
        }

        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }

    public HttpResponse<Order> captureOrder(String orderId) throws IOException {
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        request.prefer("return=representation");
        request.requestBody(new OrderRequest());
        HttpResponse<Order> response = payPalClient.execute(request);

        if (response.statusCode() == HttpStatus.CREATED.value()) {
            for (Transaction transaction : transactionRepository.findByOrderId(orderId)) {
                for (PurchaseUnit purchaseUnit : response.result().purchaseUnits()) {
                    if (purchaseUnit.referenceId().equals(String.valueOf(transaction.getId().getListingId())))
                        transaction.setCaptureId(purchaseUnit.payments().captures().iterator().next().id());
                    transaction.setRefundable(true);
                    transactionRepository.save(transaction);

                    Listing listing = listingRepository.getById(transaction.getId().getListingId());
                    listing.setAvailable(false);
                    listingRepository.save(listing);
                }
            }
        }

        return response;
    }

    public HttpResponse<Refund> refundOrder(long listingId) throws IOException {
        Transaction transaction = transactionRepository.findByListing_Id(listingId);
        CapturesRefundRequest request = new CapturesRefundRequest(transaction.getCaptureId());
        request.prefer("return=representation");
        request.requestBody(buildRefundRequestBody(transaction.getListing()));

        HttpResponse<Refund> response = payPalClient.execute(request);
        if (response.statusCode() == HttpStatus.CREATED.value()) {
            transaction.setRefundable(false);
            transactionRepository.save(transaction);

            Listing listing = listingRepository.getById(transaction.getId().getListingId());
            listing.setAvailable(true);
            listingRepository.save(listing);
        }

        return response;
    }

    public RefundRequest buildRefundRequestBody(Listing listing) {
        RefundRequest refundRequest = new RefundRequest();
        double itemCost = Double.parseDouble(listing.getPrice());
        double shippingCost = itemCost * SHIPPING_PERCENTAGE;
        double totalCost = itemCost + shippingCost;
        refundRequest.amount(new com.paypal.payments.Money()
                .currencyCode(CURRENCY_CODE)
                .value(String.valueOf(round(totalCost))));

        return refundRequest;
    }

    public HttpResponse<Order> getOrder(String orderId) throws IOException {
        OrdersGetRequest request = new OrdersGetRequest(orderId);
        return payPalClient.execute(request);
    }

    private double round(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

}
