package com.bookeroo.microservice.login.payload;

import com.bookeroo.microservice.login.model.User;

public class RegistrationResponse {

    private User user;
    private String jwt;

    public RegistrationResponse() {
    }

    public RegistrationResponse(User user, String jwt) {
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
