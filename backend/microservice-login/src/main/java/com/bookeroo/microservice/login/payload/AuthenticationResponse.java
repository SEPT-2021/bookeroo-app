package com.bookeroo.microservice.login.payload;

import com.bookeroo.microservice.login.model.User;

/**
 * Response body presented based on authentication result.
 */
public class AuthenticationResponse {

    private User user;
    private String jwt;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(User user, String jwt) {
        this.user = user;
        this.jwt = jwt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}
