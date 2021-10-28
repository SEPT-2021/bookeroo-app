package com.bookeroo.microservice.book.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ListingFormData {

    private String bookId;
    @NotBlank(message = "Price cannot be blank")
    private String price;
    @NotNull(message = "Book condition cannot be null")
    private Book.BookCondition condition;

    public ListingFormData() {
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Book.BookCondition getCondition() {
        return condition;
    }

    public void setCondition(Book.BookCondition condition) {
        this.condition = condition;
    }

}
