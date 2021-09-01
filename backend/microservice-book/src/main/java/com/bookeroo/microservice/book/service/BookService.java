package com.bookeroo.microservice.book.service;

import com.bookeroo.microservice.book.exception.BookNotFoundException;
import com.bookeroo.microservice.book.exception.ISBNAlreadyExistsException;
import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(Book book) { // throws runtime exception resulting in a 400 http bad request
        System.out.println(book);
        if (bookRepository.findByIsbn(book.getIsbn()) != null)
            throw new ISBNAlreadyExistsException(String.format("ISBN \"%s\" already exists", book.getIsbn()));

        return bookRepository.save(book);
    }

    // TODO extend to further getBy"?" methods as necessary
    public Book getBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent())
            throw new BookNotFoundException(String.format("Book with id %s not found", id));

        return book.get();
    }

    public void removeBook(Long id) {
        if (!bookRepository.existsById(id))
            throw new BookNotFoundException(String.format("Book with id %s not found", id));

        bookRepository.deleteById(id);
    }

    // TODO extend to further searchBy"?" methods as necessary
    public List<Book> searchBook(String keyword) {
        List<Book> results = new ArrayList<>();
        bookRepository.findByTitleContains(keyword).forEach(results::add);
        bookRepository.findByAuthorContains(keyword).forEach(results::add);

        return results;
    }

}
