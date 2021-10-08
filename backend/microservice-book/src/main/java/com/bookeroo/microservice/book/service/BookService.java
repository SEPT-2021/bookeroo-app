package com.bookeroo.microservice.book.service;

import com.bookeroo.microservice.book.exception.BookNotFoundException;
import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBook(long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent())
            throw new BookNotFoundException(String.format("Book with id %s not found", id));

        return book.get();
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void removeBook(long id) {
        if (!bookRepository.existsById(id))
            throw new BookNotFoundException(String.format("Book with id %s not found", id));

        bookRepository.deleteById(id);
    }

    public List<Book> searchBookByTitle(String title) {
        List<Book> results = new ArrayList<>();
        bookRepository.findByTitleContaining(title).forEach(results::add);

        return results;
    }

    public List<Book> searchBookByAuthor(String author) {
        List<Book> results = new ArrayList<>();
        bookRepository.findByAuthorContaining(author).forEach(results::add);

        return results;
    }

    public List<Book> searchBookByIsbn(String isbn) {
        List<Book> results = new ArrayList<>();
        bookRepository.findByIsbnContaining(isbn).forEach(results::add);

        return results;
    }

    public List<Book> searchBookByCategory(String category) {
        List<Book> results = new ArrayList<>();
        bookRepository.findByBookCategoryContaining(category).forEach(results::add);

        return results;
    }

    public Set<Book> searchBookByKeyword(String keyword) {
        Set<Book> results = new TreeSet<>(Comparator.comparingLong(Book::getId));
        results.addAll(searchBookByTitle(keyword));
        results.addAll(searchBookByAuthor(keyword));
        results.addAll(searchBookByIsbn(keyword));
        results.addAll(searchBookByCategory(keyword));

        return results;
    }

}
