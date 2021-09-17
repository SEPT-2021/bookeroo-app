package com.bookeroo.microservice.book.web;

import com.bookeroo.microservice.book.exception.BookNotFoundException;
import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.model.BookFormData;
import com.bookeroo.microservice.book.service.BookService;
import com.bookeroo.microservice.book.service.S3Service;
import com.bookeroo.microservice.book.service.ValidationErrorService;
import com.bookeroo.microservice.book.validator.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final S3Service s3Service;
    private final ValidationErrorService validationErrorService;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(
            BookService bookService,
            S3Service s3Service,
            ValidationErrorService validationErrorService,
            BookValidator bookValidator) {
        this.bookService = bookService;
        this.s3Service = s3Service;
        this.validationErrorService = validationErrorService;
        this.bookValidator = bookValidator;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addNewBook(@ModelAttribute(name = "formData") BookFormData formData, BindingResult result) {
        Book book = new Book();
        book.setTitle(formData.getTitle());
        book.setAuthor(formData.getAuthor());
        book.setPageCount(formData.getPageCount());
        book.setIsbn(formData.getIsbn());
        book.setPrice(formData.getPrice());
        book.setDescription(formData.getDescription());
        bookValidator.validate(book, result);

        ResponseEntity<?> errorMap = validationErrorService.mapValidationErrors(result);
        if (errorMap != null)
            return errorMap;

        try {
            MultipartFile coverFile = formData.getCoverFile();
            if (coverFile != null)
                book.setCover(s3Service.uploadFile(formData.getCoverFile(), formData.getTitle()));
            else
                book.setCover(s3Service.uploadFile(new URL(formData.getCoverUrl()), formData.getTitle()));
        } catch (IOException | URISyntaxException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

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
                // TODO redundant, if search by keyword is desired simply omit the "type" param
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

    // TODO what follows are temporary testing methods to verify S3 is configured properly
    /* @PostMapping("/upload-file")
    public ResponseEntity<?> uploadImageByFile(@RequestPart(value = "file") MultipartFile file, @RequestParam("name") String name) {
        try {
            System.out.println(s3Service.uploadFile(file, name));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BookNotFoundException | IOException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/upload-url")
    public ResponseEntity<?> uploadImageByUrl(@RequestParam("url") URL url, @RequestParam("name") String name) {
        try {
            System.out.println(s3Service.uploadFile(url, name));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BookNotFoundException | IOException | URISyntaxException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<?> upload(@RequestParam("file") String file) {
        try {
            byte[] byteArray = s3Service.downloadFile(file).toByteArray();
            return ResponseEntity.ok()
                    .contentType(deduceContentType(file))
                    .body(byteArray);
        } catch (BookNotFoundException | IOException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private MediaType deduceContentType(String fileName) {
        String[] splitByPeriod = fileName.split("\\.");
        String fileExtension = splitByPeriod[splitByPeriod.length - 1];
        switch (fileExtension) {
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            case "gif":
                return MediaType.IMAGE_GIF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    } */

}
