package com.bookeroo.microservice.seller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a seller user being fetched does not exist.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class SellerNotFoundException extends RuntimeException {

    public SellerNotFoundException(String message) {
        super(message);
    }

}
