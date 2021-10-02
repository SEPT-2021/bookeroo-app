package com.bookeroo.microservice.book.service;

import com.bookeroo.microservice.book.exception.BookNotFoundException;
import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Book> searchBookByIsbn(String author) {
        List<Book> results = new ArrayList<>();
        bookRepository.findByIsbnContaining(author).forEach(results::add);

        return results;
    }

    public List<Book> searchBookByCategory(String author) {
        List<Book> results = new ArrayList<>();
        bookRepository.findByBookCategoryContaining(author).forEach(results::add);

        return results;
    }

    public List<Book> searchBookByKeyword(String keyword) {
        List<Book> results = new ArrayList<>();
        bookRepository.findByTitleContainingOrAuthorContainingOrIsbnContainingOrBookCategoryContaining(
                keyword, keyword, keyword, keyword).forEach(results::add);

        return results;
    }

}
