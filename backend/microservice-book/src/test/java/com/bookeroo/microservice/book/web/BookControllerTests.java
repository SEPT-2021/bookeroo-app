package com.bookeroo.microservice.book.web;

import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.model.Book.BookCategory;
import com.bookeroo.microservice.book.model.Book.BookCondition;
import com.bookeroo.microservice.book.model.BookFormData;
import com.bookeroo.microservice.book.model.User;
import com.bookeroo.microservice.book.repository.UserRepository;
import com.bookeroo.microservice.book.service.BookService;
import com.bookeroo.microservice.book.service.S3Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class BookControllerTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    BookService bookService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    S3Service s3Service;
    private int uniqueId;

    Book setupBook() {
        Book book = new Book();
        Random random = new Random();
        book.setTitle("testTitle" + uniqueId++);
        book.setAuthor("testAuthor" + uniqueId++);
        book.setPageCount("100");
        book.setIsbn(String.valueOf((long) Math.floor(Math.random() * 9_000_000_000_000L) + 1_000_000_000_000L));
        book.setDescription("testDescription");
        book.setBookCategory(BookCategory.values()[random.nextInt(BookCategory.values().length)].name());
        try {
            book.setCover(s3Service.uploadFile(new URL("https://picsum.photos/360/640"), book.getTitle()));
        } catch (IOException ignore) {
        }

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
        bookFormData.setCondition(BookCondition.values()[random.nextInt(BookCondition.values().length)]);
        bookFormData.setCategory(BookCategory.valueOf(book.getBookCategory()));
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
    void givenBookFormData_whenBookAdded_thenReturnStatusCreated() throws Exception {
        BookFormData bookFormData = setupBookFormData();
        mockMvc.perform(post("/api/books/add")
                .param("title", bookFormData.getTitle())
                .param("author", bookFormData.getAuthor())
                .param("pageCount", String.valueOf(bookFormData.getPageCount()))
                .param("isbn", bookFormData.getIsbn())
                .param("description", bookFormData.getDescription())
                .param("price", bookFormData.getPrice())
                .param("condition", bookFormData.getCondition().name())
                .param("category", bookFormData.getCategory().name())
                .param("coverUrl", bookFormData.getCoverUrl())
                .flashAttr("formData", bookFormData)).andExpect(status().isCreated());
    }

    @Test
    void givenBookFormData_whenBookAdded_thenReturnBookAsResponseBody() throws Exception {
        BookFormData bookFormData = setupBookFormData();
        String response = mockMvc.perform(post("/api/books/add")
                .param("title", bookFormData.getTitle())
                .param("author", bookFormData.getAuthor())
                .param("pageCount", String.valueOf(bookFormData.getPageCount()))
                .param("isbn", bookFormData.getIsbn())
                .param("description", bookFormData.getDescription())
                .param("price", bookFormData.getPrice())
                .param("condition", bookFormData.getCondition().name())
                .param("category", bookFormData.getCategory().name())
                .param("coverUrl", bookFormData.getCoverUrl())
                .flashAttr("formData", bookFormData)).andReturn().getResponse().getContentAsString();

        assertEquals(objectMapper.readValue(response, Book.class).getTitle(), bookFormData.getTitle());
    }

    @Test
    void givenSavedBook_whenGivenId_returnBook() throws Exception {
        BookFormData formData = setupBookFormData();
        User user = setupUser();
        Book book = bookService.saveBook(formData, user.getUsername());

        String response = mockMvc.perform(get("/api/books/" + book.getId()))
                .andReturn().getResponse().getContentAsString();

        Book responseBook = objectMapper.readValue(response, Book.class);
        assertEquals(responseBook, book);    }

    @Test
    void givenSavedBooks_whenAllBooksRequested_returnBooks() throws Exception {
        BookFormData formData1 = setupBookFormData();
        User user1 = setupUser();
        Book book1 = bookService.saveBook(formData1, user1.getUsername());
        BookFormData formData2 = setupBookFormData();
        User user2 = setupUser();
        Book book2 = bookService.saveBook(formData2, user2.getUsername());

        String response = mockMvc.perform(get("/api/books/all"))
                .andReturn().getResponse().getContentAsString();

        List<Book> books = Arrays.asList(objectMapper.readValue(response, Book[].class));
        assertTrue(books.contains(book1) && books.contains(book2));
    }

    @Test
    void givenBookId_whenDeleteIsRequested_deleteBook() throws Exception {
        BookFormData formData = setupBookFormData();
        User user = setupUser();
        Book book = bookService.saveBook(formData, user.getUsername());

        mockMvc.perform(delete("/api/books/" + book.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void givenBooksPresent_whenSearchedByKeyword_returnMatchingBooks() throws Exception {
        BookFormData formData1 = setupBookFormData();
        User user1 = setupUser();
        String searchString = BookCategory.BILDUNGSROMAN.name();
        formData1.setTitle(searchString);
        Book book1 = bookService.saveBook(formData1, user1.getUsername());

        String searchResult = mockMvc.perform(get("/api/books")
                        .param("search", searchString)
                        .param("type", "keyword"))
                .andReturn().getResponse().getContentAsString();

        List<Book> books = Arrays.asList(objectMapper.readValue(searchResult, Book[].class));
        assertTrue(books.contains(book1));
    }

    @Test
    void givenBooksPresent_whenSearchedByTitle_returnMatchingBooks() throws Exception {
        BookFormData formData1 = setupBookFormData();
        User user1 = setupUser();
        String searchString = "uniqueSearchedTitle";
        formData1.setTitle(searchString);
        Book book1 = bookService.saveBook(formData1, user1.getUsername());

        BookFormData formData2 = setupBookFormData();
        User user2 = setupUser();
        Book book2 = bookService.saveBook(formData2, user2.getUsername());
        String searchResult = mockMvc.perform(get("/api/books")
                        .param("search", searchString)
                        .param("type", "title"))
                .andReturn().getResponse().getContentAsString();

        List<Book> books = Arrays.asList(objectMapper.readValue(searchResult, Book[].class));
        assertTrue(books.contains(book1) && !books.contains(book2));
    }

    @Test
    void givenBooksPresent_whenSearchedByAuthor_returnMatchingBooks() throws Exception {
        BookFormData formData1 = setupBookFormData();
        User user1 = setupUser();
        String searchString = "uniqueSearchedAuthor";
        formData1.setAuthor(searchString);
        Book book1 = bookService.saveBook(formData1, user1.getUsername());

        BookFormData formData2 = setupBookFormData();
        User user2 = setupUser();
        Book book2 = bookService.saveBook(formData2, user2.getUsername());

        String searchResult = mockMvc.perform(get("/api/books")
                        .param("search", searchString)
                        .param("type", "author"))
                .andReturn().getResponse().getContentAsString();

        List<Book> books = Arrays.asList(objectMapper.readValue(searchResult, Book[].class));
        assertTrue(books.contains(book1) && !books.contains(book2));
    }

}
