package com.bookeroo.microservice.book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown if a book is being added with an existing ISBN.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ISBNAlreadyExistsException extends RuntimeException {

    public ISBNAlreadyExistsException(String message) {
        super(message);
    }

}
