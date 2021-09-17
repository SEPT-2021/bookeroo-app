package com.bookeroo.microservice.book.web;

import com.bookeroo.microservice.book.exception.BookFormDataValidationException;
import com.bookeroo.microservice.book.exception.BookNotFoundException;
import com.bookeroo.microservice.book.exception.S3UploadFailureException;
import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.model.BookFormData;
import com.bookeroo.microservice.book.service.BookService;
import com.bookeroo.microservice.book.service.S3Service;
import com.bookeroo.microservice.book.service.ValidationErrorService;
import com.bookeroo.microservice.book.validator.BookFormDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URL;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final S3Service s3Service;
    private final ValidationErrorService validationErrorService;
    private final BookFormDataValidator bookFormDataValidator;

    @Autowired
    public BookController(
            BookService bookService,
            S3Service s3Service,
            ValidationErrorService validationErrorService,
            BookFormDataValidator bookFormDataValidator) {
        this.bookService = bookService;
        this.s3Service = s3Service;
        this.validationErrorService = validationErrorService;
        this.bookFormDataValidator = bookFormDataValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewBook(@Valid @ModelAttribute BookFormData formData, BindingResult result) {
        bookFormDataValidator.validate(formData, result);
        ResponseEntity<?> errorMap = validationErrorService.mapValidationErrors(result);
        if (errorMap != null)
            return errorMap;

        Book book = new Book();
        book.setTitle(formData.getTitle());
        book.setAuthor(formData.getAuthor());
        book.setPageCount(Long.parseLong(formData.getPageCount()));
        book.setIsbn(formData.getIsbn());
        book.setPrice(Double.parseDouble(formData.getPrice()));
        book.setDescription(formData.getDescription());

        try {
            MultipartFile coverFile = formData.getCoverFile();
            if (coverFile != null)
                book.setCover(s3Service.uploadFile(formData.getCoverFile(), formData.getTitle()));
            else
                book.setCover(s3Service.uploadFile(new URL(formData.getCoverUrl()), formData.getTitle()));

            book = bookService.saveBook(book);
        } catch (ConstraintViolationException exception) {
            throw new BookFormDataValidationException(
                    exception.getConstraintViolations().iterator().next().getMessage());
        } catch (IOException exception) {
            throw new S3UploadFailureException(exception.getMessage());
        } catch (Exception exception) {
            throw new BookFormDataValidationException(exception.getMessage());
        }

        return new ResponseEntity<>(book, HttpStatus.CREATED);
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
        if (type == null && value == null)
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
