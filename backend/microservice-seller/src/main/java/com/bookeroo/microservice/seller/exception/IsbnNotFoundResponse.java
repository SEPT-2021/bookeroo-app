package com.bookeroo.microservice.seller.exception;

/**
 * Response body presented when {@link IsbnNotFoundException} is thrown.
 */
public class IsbnNotFoundResponse {

    private String isbn;

    public IsbnNotFoundResponse(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}
