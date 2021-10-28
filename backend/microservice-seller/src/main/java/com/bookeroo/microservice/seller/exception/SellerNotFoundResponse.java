package com.bookeroo.microservice.seller.exception;

/**
 * Response body presented when {@link SellerNotFoundException} is thrown.
 */
public class SellerNotFoundResponse {

    private String id;

    public SellerNotFoundResponse(String field) {
        this.id = field;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
