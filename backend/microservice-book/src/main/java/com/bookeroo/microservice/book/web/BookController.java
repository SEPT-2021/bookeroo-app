package com.bookeroo.microservice.book.web;

import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.model.BookFormData;
import com.bookeroo.microservice.book.model.Review;
import com.bookeroo.microservice.book.security.JWTTokenProvider;
import com.bookeroo.microservice.book.service.BookService;
import com.bookeroo.microservice.book.service.ValidationErrorService;
import com.bookeroo.microservice.book.validator.BookDataValidator;
import com.bookeroo.microservice.book.validator.BookFormDataValidator;
import com.bookeroo.microservice.book.validator.ReviewValidator;
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
    private final BookDataValidator bookDataValidator;
    private final BookFormDataValidator bookFormDataValidator;
    private final ReviewValidator reviewValidator;
    private final ValidationErrorService validationErrorService;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public BookController(BookService bookService,
                          BookDataValidator bookDataValidator,
                          BookFormDataValidator bookFormDataValidator,
                          ReviewValidator reviewValidator,
                          ValidationErrorService validationErrorService,
                          JWTTokenProvider jwtTokenProvider) {
        this.bookService = bookService;
        this.bookDataValidator = bookDataValidator;
        this.bookFormDataValidator = bookFormDataValidator;
        this.reviewValidator = reviewValidator;
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
    public ResponseEntity<Book> getBook(@PathVariable("id") long id) {
        return new ResponseEntity<>(bookService.getBook(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable("id") long id,
                                        @RequestBody Book updatedBook,
                                        BindingResult result) {
        Book book = bookService.updateBook(id, updatedBook);
        bookDataValidator.validate(book, result);
        ResponseEntity<?> errorMap = validationErrorService.mapValidationErrors(result);
        if (errorMap != null)
            return errorMap;

        return new ResponseEntity<>(bookService.saveBook(book), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") long id) {
        bookService.removeBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<?> postReview(@PathVariable long id, @Valid @RequestBody Review review, BindingResult result) {
        reviewValidator.validate(review, result);
        ResponseEntity<?> errorMap = validationErrorService.mapValidationErrors(result);
        if (errorMap != null)
            return errorMap;

        return new ResponseEntity<>(bookService.addReview(id, review), HttpStatus.OK);
    }

    @GetMapping
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
