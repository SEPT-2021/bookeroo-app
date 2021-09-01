package com.bookeroo.microservice.book.web;

import com.bookeroo.microservice.book.exception.BookNotFoundException;
import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.payload.searchrequest;
import com.bookeroo.microservice.book.payload.searchsuccessresponse;
import com.bookeroo.microservice.book.service.BookDetailsService;
import com.bookeroo.microservice.book.service.BookService;
import com.bookeroo.microservice.book.service.ValidationErrorService;

import com.bookeroo.microservice.book.validator.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/book")
public class BookController {


    private final BookService BookService;
    private final BookValidator BookValidator;
    private final ValidationErrorService validationErrorService;
    private final BookDetailsService bookDetailsService;


    @Autowired
    public BookController(BookService BookService, BookValidator BookValidator, ValidationErrorService validationErrorService, BookDetailsService BookDetailsService) {
        this.validationErrorService = validationErrorService;
        this.bookDetailsService =  BookDetailsService;
        this.BookService = BookService;
        this.BookValidator = BookValidator;


    }
    @PostMapping("/register")
    public ResponseEntity<?>  newbook(@Valid @RequestBody Book book, BindingResult result) {
        BookValidator.validate(book, result);

        ResponseEntity<?> errorMap = validationErrorService.mapValidationErrors(result);
        if (errorMap != null)
            return errorMap;

        return new ResponseEntity<>(BookService.SaveBook(book), HttpStatus.CREATED);
    }
        

    //turn to title, return json list of books
    @PostMapping("/idsearch")
    public ResponseEntity idsearchbook(@RequestBody searchrequest searchrequest) throws BookNotFoundException {
        Book book = bookDetailsService.loadBookById(searchrequest.getId());
        System.out.println(book.getTitle());
        return ResponseEntity.ok(new searchsuccessresponse(true, book.getTitle()));

    }



}
