package com.bookeroo.microservice.book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Controller to perform Spring based exception handling.
 */
@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<?> handleInvalidFormData(BookFormDataValidationException exception){
        BookFormDataValidationResponse exceptionResponse = new BookFormDataValidationResponse(exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<?> handleS3UploadFailure(S3UploadFailureException exception){
        S3UploadFailureResponse exceptionResponse = new S3UploadFailureResponse(exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<?> handleBookNotFound(BookNotFoundException exception){
        BookNotFoundResponse exceptionResponse = new BookNotFoundResponse(exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<?> handleUserNotFound(UserNotFoundException exception) {
        UserNotFoundResponse exceptionResponse = new UserNotFoundResponse(exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

}
