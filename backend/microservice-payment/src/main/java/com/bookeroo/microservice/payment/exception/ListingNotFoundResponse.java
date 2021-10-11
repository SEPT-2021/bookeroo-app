package com.bookeroo.microservice.payment.exception;

/**
 * Response body presented when {@link ListingNotFoundException} is thrown.
 */
public class ListingNotFoundResponse {

    private String id;

    public ListingNotFoundResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
