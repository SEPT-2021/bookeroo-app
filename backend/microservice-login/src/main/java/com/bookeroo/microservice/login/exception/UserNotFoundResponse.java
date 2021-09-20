package com.bookeroo.microservice.login.exception;

/**
 * Response body presented when {@link UserNotFoundException} is thrown.
 */
public class UserNotFoundResponse {

    private String id;

    public UserNotFoundResponse(String field) {
        this.id = field;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
