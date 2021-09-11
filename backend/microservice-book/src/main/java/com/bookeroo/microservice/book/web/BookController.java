package com.bookeroo.microservice.book.web;

import com.bookeroo.microservice.book.exception.BookNotFoundException;
import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.service.BookService;
import com.bookeroo.microservice.book.service.S3Service;
import com.bookeroo.microservice.book.service.ValidationErrorService;
import com.bookeroo.microservice.book.validator.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final S3Service s3Service;
    private final ValidationErrorService validationErrorService;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookService bookService, S3Service s3Service, ValidationErrorService validationErrorService, BookValidator bookValidator) {
        this.bookService = bookService;
        this.s3Service = s3Service;
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
        Iterable<Book> searchResults = null;
        if (type != null) {
            switch (type) {
                case "title":
                    searchResults = bookService.searchBookByTitle(value);
                    break;
                case "author":
                    searchResults = bookService.searchBookByAuthor(value);
                    break;
                // TODO redundant, if search by keyword is required simply omit the "type" param
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

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestPart(value = "file") MultipartFile file) {
        try {
            System.out.println(s3Service.uploadFile(file));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BookNotFoundException | IOException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<?> upload(@RequestParam("file") String file) {
        try {
            ByteArrayOutputStream downloadInputStream = s3Service.downloadFile(file);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(downloadInputStream.toByteArray());
        } catch (BookNotFoundException | IOException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
