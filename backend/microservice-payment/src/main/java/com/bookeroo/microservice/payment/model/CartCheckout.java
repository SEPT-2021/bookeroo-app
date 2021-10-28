package com.bookeroo.microservice.payment.model;

import javax.validation.Valid;
import java.util.List;

/**
 * Cart checkout data model, contains information required to check out an order.
 */
public class CartCheckout {

    private List<Listing> orderItems;
    @Valid
    private ShippingAddress shippingAddress;

    public CartCheckout() {
    }

    public List<Listing> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<Listing> orderItems) {
        this.orderItems = orderItems;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

}
