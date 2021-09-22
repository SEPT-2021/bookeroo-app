package com.bookeroo.microservice.book.service;

import com.bookeroo.microservice.book.exception.BookNotFoundException;
import com.bookeroo.microservice.book.model.Book;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.bookeroo.microservice.book.validator.BookFormDataValidator.MINIMUM_ISBN_LENGTH;
import static org.junit.jupiter.api.Assertions.*;

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
        book.setDescription("testDescription");
        book.setCover("https://picsum.photos/200");
        return book;
    }

    @Test
    void givenValidBook_whenSaved_returnBook() {
        Book book = setupBook();

        assertEquals(book.getTitle(), bookService.saveBook(book).getTitle());
    }

    @Test
    void givenBookPresent_whenGivenBookId_returnBook() {
        Book book = setupBook();
        book = bookService.saveBook(book);

        assertEquals(book.getTitle(), bookService.getBook(book.getId()).getTitle());
    }

    @Test
    void givenBooksPresent_whenAllBooksFetched_returnBooks() {
        Book book1 = setupBook();
        bookService.saveBook(book1);
        Book book2 = setupBook();
        bookService.saveBook(book2);

        assertFalse(bookService.getAllBooks().isEmpty());
    }

    @Test
    void givenBookId_whenDeleteRequested_deleteBook() {
        Book book = setupBook();
        book = bookService.saveBook(book);
        bookService.removeBook(book.getId());

        long id = book.getId();
        assertThrows(BookNotFoundException.class, () -> bookService.getBook(id));
    }

    @Test
    void givenBooksPresent_whenSearchedByKeyword_returnMatchingBooks() {
        Book book1 = setupBook();
        String searchString = "uniqueSearchedKeyword";
        book1.setDescription(searchString);
        book1 = bookService.saveBook(book1);
        Book book2 = setupBook();
        book2 = bookService.saveBook(book2);

        List<Book> searchResult = bookService.searchBookByKeyword(searchString);
        assertTrue(searchResult.contains(book1) && !searchResult.contains(book2));
    }

    @Test
    void givenBooksPresent_whenSearchedByTitle_returnMatchingBooks() {
        Book book1 = setupBook();
        String searchString = "uniqueSearchedTitle";
        book1.setTitle(searchString);
        book1 = bookService.saveBook(book1);
        Book book2 = setupBook();
        book2 = bookService.saveBook(book2);

        List<Book> searchResult = bookService.searchBookByTitle(searchString);
        assertTrue(searchResult.contains(book1) && !searchResult.contains(book2));
    }

    @Test
    void givenBooksPresent_whenSearchedByAuthor_returnMatchingBooks() {
        Book book1 = setupBook();
        String searchString = "uniqueSearchedAuthor";
        book1.setAuthor(searchString);
        book1 = bookService.saveBook(book1);
        Book book2 = setupBook();
        book2 = bookService.saveBook(book2);

        List<Book> searchResult = bookService.searchBookByAuthor(searchString);
        assertTrue(searchResult.contains(book1) && !searchResult.contains(book2));
    }

}
