package com.bookeroo.microservice.book.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ListingFormData {

    private long bookId;
    @NotBlank(message = "Price cannot be blank")
    private String price;
    @NotNull(message = "Book condition cannot be null")
    private Book.BookCondition condition;

    public ListingFormData() {
    }

    public long getBookId() {
        return bookId;
    }

    public String getPrice() {
        return price;
    }

    public Book.BookCondition getCondition() {
        return condition;
    }

}
