package com.bookeroo.microservice.seller.service;

import com.bookeroo.microservice.seller.exception.BookNotFoundException;
import com.bookeroo.microservice.seller.exception.IsbnNotFoundException;
import com.bookeroo.microservice.seller.model.Book;
import com.bookeroo.microservice.seller.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for the {@link Book} JPA entity.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getBookById(long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent())
            throw new BookNotFoundException(String.format("Book with id %d not found", id));

        return book.get();
    }

    public Book getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null)
            throw new IsbnNotFoundException(String.format("Book with ISBN %s not found", isbn));

        return book;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

}
