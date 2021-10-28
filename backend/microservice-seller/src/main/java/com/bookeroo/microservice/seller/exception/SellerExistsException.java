package com.bookeroo.microservice.seller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a seller with specified id already exists.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class SellerExistsException extends RuntimeException {

    public SellerExistsException(String message) {
        super(message);
    }

}
