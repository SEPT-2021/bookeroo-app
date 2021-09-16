package com.bookeroo.microservice.payment.model;

import java.util.List;

public class CartCheckout {

    private List<OrderItem> orderItems;
    private ShippingAddress shippingAddress;

    public CartCheckout() {
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

}
