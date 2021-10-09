package com.bookeroo.microservice.book.service;

import com.bookeroo.microservice.book.exception.BookNotFoundException;
import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.model.Book.BookCategory;
import com.bookeroo.microservice.book.model.BookFormData;
import com.bookeroo.microservice.book.model.User;
import com.bookeroo.microservice.book.repository.UserRepository;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookServiceTests {

    @Autowired
    BookService bookService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    S3Service s3Service;
    private int uniqueId;

    Book setupBook() {
        Random random = new Random();
        Book book = new Book();
        book.setTitle("testTitle" + uniqueId++);
        book.setAuthor("testAuthor" + uniqueId++);
        book.setPageCount("100");
        book.setIsbn(String.valueOf((long) Math.floor(Math.random() * 9_000_000_000_000L) + 1_000_000_000_000L));
        book.setDescription("testDescription");
        book.setBookCategory(Book.BookCategory.values()[random.nextInt(Book.BookCategory.values().length)].name());
        try {
            book.setCover(s3Service.uploadFile(new URL("https://picsum.photos/360/640"), book.getTitle()));
        } catch (IOException ignore) {}
        return book;
    }

    BookFormData setupBookFormData() {
        Book book = setupBook();
        BookFormData bookFormData = new BookFormData();
        Random random = new Random();
        bookFormData.setTitle(book.getTitle());
        bookFormData.setAuthor(book.getAuthor());
        bookFormData.setPageCount(Long.parseLong(book.getPageCount()));
        bookFormData.setIsbn(book.getIsbn());
        bookFormData.setDescription(book.getDescription());
        bookFormData.setPrice(String.valueOf(random.nextFloat() % 100.0f));
        bookFormData.setCondition(Book.BookCondition.values()[random.nextInt(Book.BookCondition.values().length)]);
        bookFormData.setCategory(Book.BookCategory.valueOf(book.getBookCategory()));
        bookFormData.setCoverUrl(book.getCover());
        return bookFormData;
    }

    User setupUser() {
        User user = new User();
        user.setUsername(RandomString.make(8) + "@test.com");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setPassword("testPassword");
        user.setAddressLine1("123 Bookeroo St");
        user.setAddressLine2("Apartment 1");
        user.setCity("Melbourne");
        user.setState("VIC");
        user.setPostalCode("3001");
        user.setPhoneNumber("+(61) 413 170 399");
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @Test
    void givenValidBook_whenSaved_returnBook() {
        BookFormData formData = setupBookFormData();
        User user = setupUser();

        assertEquals(formData.getTitle(), bookService.saveBook(formData, user.getUsername()).getTitle());
    }

    @Test
    void givenBookPresent_whenGivenBookId_returnBook() {
        BookFormData formData = setupBookFormData();
        User user = setupUser();
        Book book = bookService.saveBook(formData, user.getUsername());

        assertEquals(book.getTitle(), bookService.getBook(book.getId()).getTitle());
    }

    @Test
    void givenBooksPresent_whenAllBooksFetched_returnBooks() {
        BookFormData formData1 = setupBookFormData();
        User user1 = setupUser();
        Book book1 = bookService.saveBook(formData1, user1.getUsername());
        BookFormData formData2 = setupBookFormData();
        User user2 = setupUser();
        Book book2 = bookService.saveBook(formData2, user2.getUsername());

        List<Book> books = bookService.getAllBooks();
        assertTrue(books.contains(book1) && books.contains(book2));
    }

    @Test
    void givenBookId_whenDeleteRequested_deleteBook() {
        BookFormData formData = setupBookFormData();
        User user = setupUser();
        Book book = bookService.saveBook(formData, user.getUsername());
        bookService.removeBook(book.getId());

        long id = book.getId();
        assertThrows(BookNotFoundException.class, () -> bookService.getBook(id));
    }

    @Test
    void givenBooksPresent_whenSearchedByKeyword_returnMatchingBooks() {
        BookFormData formData1 = setupBookFormData();
        User user1 = setupUser();
        String searchString = BookCategory.BILDUNGSROMAN.name();
        formData1.setTitle(searchString);
        Book book1 = bookService.saveBook(formData1, user1.getUsername());

        Set<Book> searchResult = bookService.searchBookByKeyword(searchString);
        assertTrue(searchResult.contains(book1));
    }

    @Test
    void givenBooksPresent_whenSearchedByTitle_returnMatchingBooks() {
        BookFormData formData1 = setupBookFormData();
        User user1 = setupUser();
        String searchString = "uniqueSearchedTitle";
        formData1.setTitle(searchString);
        Book book1 = bookService.saveBook(formData1, user1.getUsername());

        BookFormData formData2 = setupBookFormData();
        User user2 = setupUser();
        Book book2 = bookService.saveBook(formData2, user2.getUsername());

        List<Book> searchResult = bookService.searchBookByTitle(searchString);
        assertTrue(searchResult.contains(book1) && !searchResult.contains(book2));
    }

    @Test
    void givenBooksPresent_whenSearchedByAuthor_returnMatchingBooks() {
        BookFormData formData1 = setupBookFormData();
        User user1 = setupUser();
        String searchString = "uniqueSearchedAuthor";
        formData1.setAuthor(searchString);
        Book book1 = bookService.saveBook(formData1, user1.getUsername());

        BookFormData formData2 = setupBookFormData();
        User user2 = setupUser();
        Book book2 = bookService.saveBook(formData2, user2.getUsername());

        List<Book> searchResult = bookService.searchBookByAuthor(searchString);
        assertTrue(searchResult.contains(book1) && !searchResult.contains(book2));
    }

}
