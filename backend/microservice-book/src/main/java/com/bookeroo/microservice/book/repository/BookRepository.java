package com.bookeroo.microservice.book.repository;

import com.bookeroo.microservice.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository for the {@link Book} entity.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(long id);

    Book findByIsbn(String isbn);

    Book findByTitleAndAuthor(String title, String author);

    Iterable<Book> findByTitleContaining(String title);

    Iterable<Book> findByAuthorContaining(String author);

    Iterable<Book> findByIsbnContaining(String isbn);

    Iterable<Book> findByBookCategoryContaining(String category);

    List<Book> findAll();

}
