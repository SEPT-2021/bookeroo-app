package com.bookeroo.microservice.login.exception;

/**
 * Response body presented when {@link UsernameAlreadyExistsException} is thrown.
 */
public class UsernameAlreadyExistsResponse {

    private String username;

    public UsernameAlreadyExistsResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
