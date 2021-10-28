package com.bookeroo.microservice.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a transaction is not refundable.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TransactionNotRefundableException extends RuntimeException {

    public TransactionNotRefundableException(String message) {
        super(message);
    }

}
