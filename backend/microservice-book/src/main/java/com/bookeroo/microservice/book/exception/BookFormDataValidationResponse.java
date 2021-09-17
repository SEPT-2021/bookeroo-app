package com.bookeroo.microservice.book.exception;

public class BookFormDataValidationResponse {

    private String what;

    public BookFormDataValidationResponse(String what) {
        this.what = what;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

}
