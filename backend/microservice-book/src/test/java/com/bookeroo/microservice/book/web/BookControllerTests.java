package com.bookeroo.microservice.book.web;

import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.model.Book.BookCategory;
import com.bookeroo.microservice.book.model.Book.BookCondition;
import com.bookeroo.microservice.book.model.BookFormData;
import com.bookeroo.microservice.book.service.BookService;
import com.bookeroo.microservice.book.service.S3Service;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    S3Service s3Service;

    Book setupBook() {
        Book book = new Book();
        Random random = new Random();
        book.setTitle("testTitle");
        book.setAuthor("testAuthor");
        book.setPageCount("100");
        book.setIsbn(String.valueOf((long) Math.floor(Math.random() * 9_000_000_000_000L) + 1_000_000_000_000L));
        book.setDescription("testDescription");
        book.setPrice(String.valueOf(random.nextFloat() % 10.0f));
        book.setBookCondition(BookCondition.values()[random.nextInt(BookCondition.values().length)].name());
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
        bookFormData.setTitle(book.getTitle());
        bookFormData.setAuthor(book.getAuthor());
        bookFormData.setPageCount(Long.parseLong(book.getPageCount()));
        bookFormData.setIsbn(book.getIsbn());
        bookFormData.setDescription(book.getDescription());
        bookFormData.setPrice(book.getPrice());
        bookFormData.setCondition(BookCondition.valueOf(book.getBookCondition()));
        bookFormData.setCategory(BookCategory.valueOf(book.getBookCategory()));
        bookFormData.setCoverUrl(book.getCover());
        return bookFormData;
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
        Book book = setupBook();
        book = bookService.saveBook(book);

        String response = mockMvc.perform(get("/api/books/" + book.getId()))
                .andReturn().getResponse().getContentAsString();

        Book responseBook = objectMapper.readValue(response, Book.class);
        assertEquals(responseBook, book);    }

    @Test
    void givenSavedBooks_whenAllBooksRequested_returnBooks() throws Exception {
        Book book1 = setupBook();
        book1 = bookService.saveBook(book1);
        Book book2 = setupBook();
        book2 = bookService.saveBook(book2);

        String response = mockMvc.perform(get("/api/books/all"))
                .andReturn().getResponse().getContentAsString();

        List<Book> books = Arrays.asList(objectMapper.readValue(response, Book[].class));
        assertTrue(books.contains(book1) && books.contains(book2));
    }

    @Test
    void givenBookId_whenDeleteIsRequested_deleteBook() throws Exception {
        Book book = setupBook();
        book = bookService.saveBook(book);

        mockMvc.perform(delete("/api/books/" + book.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void givenBooksPresent_whenSearchedByKeyword_returnMatchingBooks() throws Exception {
        Book book1 = setupBook();
        String searchString = BookCategory.BILDUNGSROMAN.name();
        book1.setBookCategory(searchString);
        book1 = bookService.saveBook(book1);
        Book book2 = setupBook();
        book2 = bookService.saveBook(book2);

        String searchResult = mockMvc.perform(get("/api/books")
                        .param("search", searchString)
                        .param("type", "keyword"))
                .andReturn().getResponse().getContentAsString();

        List<Book> books = Arrays.asList(objectMapper.readValue(searchResult, Book[].class));
        assertTrue(books.contains(book1) && !books.contains(book2));
    }

    @Test
    void givenBooksPresent_whenSearchedByTitle_returnMatchingBooks() throws Exception {
        Book book1 = setupBook();
        String searchString = "uniqueSearchedTitle";
        book1.setTitle(searchString);
        book1 = bookService.saveBook(book1);
        Book book2 = setupBook();
        book2 = bookService.saveBook(book2);

        String searchResult = mockMvc.perform(get("/api/books")
                        .param("search", searchString)
                        .param("type", "title"))
                .andReturn().getResponse().getContentAsString();

        List<Book> books = Arrays.asList(objectMapper.readValue(searchResult, Book[].class));
        assertTrue(books.contains(book1) && !books.contains(book2));
    }

    @Test
    void givenBooksPresent_whenSearchedByAuthor_returnMatchingBooks() throws Exception {
        Book book1 = setupBook();
        String searchString = "uniqueSearchedAuthor";
        book1.setAuthor(searchString);
        book1 = bookService.saveBook(book1);
        Book book2 = setupBook();
        book2 = bookService.saveBook(book2);

        String searchResult = mockMvc.perform(get("/api/books")
                        .param("search", searchString)
                        .param("type", "author"))
                .andReturn().getResponse().getContentAsString();

        List<Book> books = Arrays.asList(objectMapper.readValue(searchResult, Book[].class));
        assertTrue(books.contains(book1) && !books.contains(book2));
    }

}
