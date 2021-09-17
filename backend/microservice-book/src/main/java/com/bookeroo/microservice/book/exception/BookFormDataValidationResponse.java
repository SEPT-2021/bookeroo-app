package com.bookeroo.microservice.book.exception;

public class BookFormDataValidationResponse {

    private String author;

    public BookFormDataValidationResponse(String what) {
        this.author = what;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
