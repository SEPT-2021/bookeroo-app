package com.bookeroo.microservice.book.web;

import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.model.BookFormData;
import com.bookeroo.microservice.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

    Book setupBook() {
        Book book = new Book();
        book.setTitle("testTitle");
        book.setAuthor("testAuthor");
        book.setPageCount(100);
        book.setIsbn(RandomString.make(14));
        book.setPrice(100.0);
        book.setDescription("testDescription");
        book.setCover("https://picsum.photos/200");
        return book;
    }

    @Test
    void givenBookFormData_whenBookAdded_thenReturnStatusCreated() throws Exception {
        Book book = setupBook();
        BookFormData bookFormData = new BookFormData();
        bookFormData.setTitle(book.getTitle());
        bookFormData.setAuthor(book.getAuthor());
        bookFormData.setPageCount(book.getPageCount());
        bookFormData.setIsbn(book.getIsbn());
        bookFormData.setPrice(book.getPrice());
        bookFormData.setDescription(book.getDescription());
        bookFormData.setCoverUrl(book.getCover());

        String obj = objectMapper.writeValueAsString(bookFormData);
        System.out.println(obj);

        mockMvc.perform(post("/api/books/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(obj))
                .andExpect(status().isCreated());
    }

    @Test
    void givenBookFormData_whenBookAdded_thenReturnUserAsResponseBody() throws Exception {
        Book book = setupBook();
        BookFormData bookFormData = new BookFormData();
        bookFormData.setTitle(book.getTitle());
        bookFormData.setAuthor(book.getAuthor());
        bookFormData.setPageCount(book.getPageCount());
        bookFormData.setIsbn(book.getIsbn());
        bookFormData.setPrice(book.getPrice());
        bookFormData.setDescription(book.getDescription());
        bookFormData.setCoverUrl(book.getCover());

        String response = mockMvc.perform(post("/api/books/add")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .content(objectMapper.writeValueAsString(bookFormData)))
                .andReturn().getResponse().getContentAsString();

        System.out.println(response);
        assertTrue(response.contains(book.getTitle()));
    }

    @Test
    void givenSavedBook_whenGivenId_returnBook() throws Exception {
        Book book = setupBook();
        book = bookService.saveBook(book);

        String response = mockMvc.perform(get("/api/books/" + book.getId()))
                .andReturn().getResponse().getContentAsString();

        assertTrue(response.contains(book.getTitle()));
    }

    @Test
    void givenSavedBooks_whenAllBooksRequested_returnBooks() throws Exception {
        Book book1 = setupBook();
        book1 = bookService.saveBook(book1);
        Book book2 = setupBook();
        book2 = bookService.saveBook(book2);

        String response = mockMvc.perform(get("/api/books/all"))
                .andReturn().getResponse().getContentAsString();

        assertTrue(response.contains(book1.getTitle()) && response.contains(book2.getTitle()));
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
        String searchString = "uniqueSearchedKeyword";
        book1.setDescription(searchString);
        book1 = bookService.saveBook(book1);
        Book book2 = setupBook();
        book2 = bookService.saveBook(book2);

        String searchResult = mockMvc.perform(get("/api/books/search")
                .param("search", searchString)
                .param("type", "keyword"))
                .andReturn().getResponse().getContentAsString();

        assertTrue(searchResult.contains(book1.getDescription()) && !searchResult.contains(book2.getDescription()));
    }

    @Test
    void givenBooksPresent_whenSearchedByTitle_returnMatchingBooks() throws Exception {
        Book book1 = setupBook();
        String searchString = "uniqueSearchedTitle";
        book1.setTitle(searchString);
        book1 = bookService.saveBook(book1);
        Book book2 = setupBook();
        book2 = bookService.saveBook(book2);

        String searchResult = mockMvc.perform(get("/api/books/search")
                        .param("search", searchString)
                        .param("type", "title"))
                .andReturn().getResponse().getContentAsString();

        assertTrue(searchResult.contains(book1.getTitle()) && !searchResult.contains(book2.getTitle()));
    }

    @Test
    void givenBooksPresent_whenSearchedByAuthor_returnMatchingBooks() throws Exception {
        Book book1 = setupBook();
        String searchString = "uniqueSearchedAuthor";
        book1.setTitle(searchString);
        book1 = bookService.saveBook(book1);
        Book book2 = setupBook();
        book2 = bookService.saveBook(book2);

        String searchResult = mockMvc.perform(get("/api/books/search")
                        .param("search", searchString)
                        .param("type", "author"))
                .andReturn().getResponse().getContentAsString();

        assertTrue(searchResult.contains(book1.getAuthor()) && !searchResult.contains(book2.getAuthor()));
    }

}