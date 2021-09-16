package com.bookeroo.microservice.payment.model;

public class OrderItem {

    private Book books;
    private int quantity;

    public OrderItem() {
    }

    public Book getBooks() {
        return books;
    }

    public void setBooks(Book books) {
        this.books = books;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
