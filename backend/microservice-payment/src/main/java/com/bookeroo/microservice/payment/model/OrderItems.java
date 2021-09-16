package com.bookeroo.microservice.payment.model;

import com.paypal.orders.AddressPortable;

import java.util.List;

public class OrderItems {

    private List<OrderItem> items;
    private AddressPortable addressPortable;

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public AddressPortable getAddressPortable() {
        return addressPortable;
    }

    public void setAddressPortable(AddressPortable addressPortable) {
        this.addressPortable = addressPortable;
    }

}
