package com.bookeroo.microservice.book.exception;

/**
 * Response body presented when {@link ISBNAlreadyExistsException} is thrown.
 */
public class ISBNAlreadyExistsResponse {

    private String isbn;

    public ISBNAlreadyExistsResponse() {
    }

    public ISBNAlreadyExistsResponse(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}
