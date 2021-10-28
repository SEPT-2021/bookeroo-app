package com.bookeroo.microservice.seller.exception;

/**
 * Response body presented when {@link UserIsNotSellerException} is thrown.
 */
public class UserIsNotSellerResponse {

    private String username;

    public UserIsNotSellerResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
