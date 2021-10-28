package com.bookeroo.microservice.seller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a seller operation is attempted on a non-seller user.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserIsNotSellerException extends RuntimeException {

    public UserIsNotSellerException(String message) {
        super(message);
    }

}
