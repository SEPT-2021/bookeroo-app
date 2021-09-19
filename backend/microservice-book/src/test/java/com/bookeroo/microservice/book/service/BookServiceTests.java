package com.bookeroo.microservice.book.service;

import com.bookeroo.microservice.book.exception.BookNotFoundException;
import com.bookeroo.microservice.book.exception.ISBNAlreadyExistsException;
import com.bookeroo.microservice.book.model.Book;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.bookeroo.microservice.book.validator.BookFormDataValidator.MINIMUM_ISBN_LENGTH;

@SpringBootTest
public class BookServiceTests {

    @Autowired
    BookService bookService;

    Book setupBook() {
        Book book = new Book();
        book.setTitle("testTitle");
        book.setAuthor("testAuthor");
        book.setPageCount(100);
        book.setIsbn(RandomString.make(MINIMUM_ISBN_LENGTH));
        book.setPrice(100.0);
        book.setDescription("testDescription");
        book.setCover("https://picsum.photos/200");
        return book;
    }

    void givenValidBook_whenSaved_returnBook() {

    }

    void givenBookPresent_whenGivenBookId_returnBook() {

    }

    void givenBooksPresent_whenAllBooksFetched_returnBooks() {

    }

    void givenBookId_whenDeleteRequested_deleteBook() {

    }

    void givenBooksPresent_whenSearchedByTitle_returnMatchingBooks() {

    }

    void givenBooksPresent_whenSearchedByAuthor_returnMatchingBooks() {

    }

    void givenBooksPresent_whenSearchedByKeyword_returnMatchingBooks() {

    }

}
