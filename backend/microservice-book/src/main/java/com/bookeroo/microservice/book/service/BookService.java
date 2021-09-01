package com.bookeroo.microservice.book.service;


import com.bookeroo.microservice.book.exception.ISBNAlreadyExistsException;
import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book SaveBook(Book book) {
        try {
            System.out.println(book.getTitle());
            System.out.println(book.getPages());
            System.out.println(book.getAuthor());
            System.out.println(book.getISBN());
            return bookRepository.save(book);
        } catch (Exception exception) {
            throw new ISBNAlreadyExistsException(String.format("ISBN\"%s\" already exists", book.getISBN()));
        }
    }

}
