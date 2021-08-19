package com.bookeroo.microservice.login.payload;

public class LoginSuccessResponse {

    private boolean success;
    private String token;

    public LoginSuccessResponse(boolean success, String token) {
        this.success = success;
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return String.format("LoginSuccessResponse {\n\tsuccess: %b,\n\ttoken: %s\n}", success, token);
    }

}
