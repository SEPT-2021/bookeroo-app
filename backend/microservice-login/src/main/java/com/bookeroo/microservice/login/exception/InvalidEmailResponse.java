package com.bookeroo.microservice.login.exception;

/**
 * Response body presented when authentication is invalid.
 */
public class InvalidEmailResponse {

    private String email;

    public InvalidEmailResponse(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
