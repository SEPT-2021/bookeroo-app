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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final BookDetailsService bookDetailsService;
    private final ValidationErrorService validationErrorService;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookService bookService, BookDetailsService bookDetailsService, ValidationErrorService validationErrorService, BookValidator bookValidator) {
        this.bookService = bookService;
        this.bookDetailsService = bookDetailsService;
        this.validationErrorService = validationErrorService;
        this.bookValidator = bookValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewBook(@Valid @RequestBody Book book, BindingResult result) {
        bookValidator.validate(book, result);

        ResponseEntity<?> errorMap = validationErrorService.mapValidationErrors(result);
        if (errorMap != null)
            return errorMap;

        return new ResponseEntity<>(bookService.saveBook(book), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(bookService.getBook(id), HttpStatus.OK);
        } catch (BookNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") Long id) {
        try {
            bookService.removeBook(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BookNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    public ResponseEntity<Iterable<Book>> searchByKeyword(@RequestParam("search") String search) {
        return new ResponseEntity<>(bookService.searchBook(search), HttpStatus.OK);
    }

    //turn to title, return json list of books
    @PostMapping("/idsearch")
    public ResponseEntity<?> idsearchbook(@RequestBody searchrequest searchrequest) throws BookNotFoundException {
        Book book = bookDetailsService.loadBookById(searchrequest.getId());
        System.out.println(book.getTitle());
        return ResponseEntity.ok(new searchsuccessresponse(true, book.getTitle()));

    }

}
