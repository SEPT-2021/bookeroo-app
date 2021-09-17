package com.bookeroo.microservice.book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookFormDataValidationException extends RuntimeException {

    public BookFormDataValidationException(String message) {
        super(message);
    }

}
