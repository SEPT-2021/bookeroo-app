package com.bookeroo.microservice.login.exception;

/**
 * Response body presented when authentication is invalid.
 */
public class InvalidLoginResponse {

    private String username;
    private String password;

    public InvalidLoginResponse() {
        this.username = "Invalid username";
        this.password = "Invalid password";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
