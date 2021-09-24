package com.bookeroo.microservice.seller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a book with the specified ISBN is not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class IsbnNotFoundException extends RuntimeException {

    public IsbnNotFoundException(String message) {
        super(message);
    }

}
