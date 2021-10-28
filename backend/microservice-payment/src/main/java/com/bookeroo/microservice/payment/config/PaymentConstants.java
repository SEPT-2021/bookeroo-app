package com.bookeroo.microservice.payment.config;

/**
 * Contains payment related constants.
 */
public class PaymentConstants {

    public static final String BRAND_NAME = "Bookeroo LLC";
    public static final String DESCRIPTION = "Consumer Goods";
    public static final String COUNTRY_CODE = "AU";
    public static final String CURRENCY_CODE = "AUD";
    public static final String SHIPPING_PREFERENCE = "SET_PROVIDED_ADDRESS";
    public static final String CHECKOUT_PAYMENT_INTENT = "CAPTURE";
    public static final String LANDING_PAGE = "BILLING";
    public static final double SHIPPING_PERCENTAGE = 0.05;
    public static final String ITEM_CATEGORY = "PHYSICAL_GOODS";
    public static final String RETURN_URL = "http://bookeroo.surge.sh/paymentSuccess";
    public static final String CANCEL_URL = "http://bookeroo.surge.sh/paymentFailure";
    public static final int REFUND_EXPIRATION_TIME_HOURS = 2;

}
