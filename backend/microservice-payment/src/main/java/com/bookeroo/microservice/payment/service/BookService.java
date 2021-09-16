package com.bookeroo.microservice.payment.service;

import com.bookeroo.microservice.payment.exception.BookNotFoundException;
import com.bookeroo.microservice.payment.model.Book;
import com.bookeroo.microservice.payment.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getBook(long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent())
            throw new BookNotFoundException(String.format("Book with id %s not found", id));

        return book.get();
    }

    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void removeBook(long id) {
        if (!bookRepository.existsById(id))
            throw new BookNotFoundException(String.format("Book with id %s not found", id));

        bookRepository.deleteById(id);
    }

}
