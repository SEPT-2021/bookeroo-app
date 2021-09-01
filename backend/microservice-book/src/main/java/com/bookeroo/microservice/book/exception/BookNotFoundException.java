package com.bookeroo.microservice.book.exception;



public class BookNotFoundException extends Exception {
    public BookNotFoundException(String msg) {
        super(msg);
    }

    public BookNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
