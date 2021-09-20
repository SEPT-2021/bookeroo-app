package com.bookeroo.microservice.login.exception;

/**
 * Response body presented when {@link UserFieldValidationException} is thrown.
 */
public class UserFieldValidationResponse {

    private String what;

    public UserFieldValidationResponse(String what) {
        this.what = what;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

}
