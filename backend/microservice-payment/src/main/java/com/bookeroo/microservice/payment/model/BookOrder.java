package com.bookeroo.microservice.payment.model;

import com.paypal.orders.AddressPortable;

import java.util.List;

public class BookOrder {

    private String buyerUsername;
    private List<BookListing> bookListings;
    private AddressPortable addressPortable;

    public BookOrder() {
    }

    public BookOrder(String buyerUsername, List<BookListing> bookListings, AddressPortable addressPortable) {
        this.buyerUsername = buyerUsername;
        this.bookListings = bookListings;
        this.addressPortable = addressPortable;
    }

    public String getBuyerUsername() {
        return buyerUsername;
    }

    public void setBuyerUsername(String buyerUsername) {
        this.buyerUsername = buyerUsername;
    }

    public List<BookListing> getBookListings() {
        return bookListings;
    }

    public void setBookListings(List<BookListing> bookListings) {
        this.bookListings = bookListings;
    }

    public AddressPortable getAddressPortable() {
        return addressPortable;
    }

    public void setAddressPortable(AddressPortable addressPortable) {
        this.addressPortable = addressPortable;
    }

}
