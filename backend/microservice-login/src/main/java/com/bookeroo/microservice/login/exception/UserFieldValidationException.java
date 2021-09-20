package com.bookeroo.microservice.login.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a user data model field is invalid.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserFieldValidationException extends RuntimeException {

    public UserFieldValidationException(String message) {
        super(message);
    }

}
