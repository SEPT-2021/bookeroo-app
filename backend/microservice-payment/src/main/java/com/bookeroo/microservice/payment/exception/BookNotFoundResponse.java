package com.bookeroo.microservice.payment.exception;

/**
 * Response body presented when {@link BookNotFoundException} is thrown.
 */
public class BookNotFoundResponse {

    private String id;

    public BookNotFoundResponse(String what) {
        this.id = what;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
