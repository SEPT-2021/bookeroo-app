package com.bookeroo.microservice.book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class S3UploadFailureException extends RuntimeException {

    public S3UploadFailureException(String message) {
        super(message);
    }

}
