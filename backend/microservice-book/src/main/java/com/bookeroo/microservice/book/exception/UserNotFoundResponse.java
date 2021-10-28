package com.bookeroo.microservice.book.exception;

/**
 * Response body presented when {@link UserNotFoundException} is thrown.
 */
public class UserNotFoundResponse {

    private String id;

    public UserNotFoundResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
