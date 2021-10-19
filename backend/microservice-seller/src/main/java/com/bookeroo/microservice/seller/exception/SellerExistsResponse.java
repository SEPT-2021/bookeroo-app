package com.bookeroo.microservice.seller.exception;

/**
 * Response body presented when {@link SellerExistsException} is thrown.
 */
public class SellerExistsResponse {

    private String id;

    public SellerExistsResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
