package com.bookeroo.microservice.payment.model;

/**
 * An individual unit of order item.
 */
public class OrderItem {

    private Book book;
    private int quantity;

    public OrderItem() {
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
