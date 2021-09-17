package com.bookeroo.microservice.login.exception;

public class UserFieldValidationResponse {

    private String field;

    public UserFieldValidationResponse(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

}
