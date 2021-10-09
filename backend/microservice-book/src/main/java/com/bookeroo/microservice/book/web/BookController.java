package com.bookeroo.microservice.book.web;

import com.bookeroo.microservice.book.exception.BookNotFoundException;
import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.model.BookFormData;
import com.bookeroo.microservice.book.security.JWTTokenProvider;
import com.bookeroo.microservice.book.service.BookService;
import com.bookeroo.microservice.book.service.ValidationErrorService;
import com.bookeroo.microservice.book.validator.BookFormDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.bookeroo.microservice.book.security.SecurityConstant.AUTHORIZATION_HEADER;
import static com.bookeroo.microservice.book.security.SecurityConstant.JWT_SCHEME;

/**
 * REST Controller to hold the microservice's endpoint implementations.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final BookFormDataValidator bookFormDataValidator;
    private final ValidationErrorService validationErrorService;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public BookController(BookService bookService,
                          BookFormDataValidator bookFormDataValidator,
                          ValidationErrorService validationErrorService,
                          JWTTokenProvider jwtTokenProvider) {
        this.bookService = bookService;
        this.bookFormDataValidator = bookFormDataValidator;
        this.validationErrorService = validationErrorService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewBook(
            @RequestHeader(name = AUTHORIZATION_HEADER, required = false) String tokenHeader,
            @Valid @ModelAttribute BookFormData formData, BindingResult result) {
        if (tokenHeader == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        String username = jwtTokenProvider.extractUsername(tokenHeader.substring(JWT_SCHEME.length()));
        bookFormDataValidator.validate(formData, result);
        ResponseEntity<?> errorMap = validationErrorService.mapValidationErrors(result);
        if (errorMap != null)
            return errorMap;

        return new ResponseEntity<>(bookService.saveBook(formData, username), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(bookService.getBook(id), HttpStatus.OK);
        } catch (BookNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
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
    public ResponseEntity<Iterable<Book>> searchForBook(
            @RequestParam("search") String value,
            @RequestParam(name = "type", required = false) String type) {

        if (value == null || value.isEmpty())
            return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);

        Iterable<Book> searchResults = null;
        if (type != null) {
            switch (type) {
                case "title":
                    searchResults = bookService.searchBookByTitle(value);
                    break;
                case "author":
                    searchResults = bookService.searchBookByAuthor(value);
                    break;
                case "isbn":
                    searchResults = bookService.searchBookByIsbn(value);
                    break;
                case "category":
                    searchResults = bookService.searchBookByCategory(value);
                    break;
                case "keyword":
                    searchResults = bookService.searchBookByKeyword(value);
                    break;
                default:
                    break;
            }
        } else {
            searchResults = bookService.searchBookByKeyword(value);
        }

        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

}
