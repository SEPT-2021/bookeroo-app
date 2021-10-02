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

    Iterable<Book> findByTitleContaining(String keyword);

    Iterable<Book> findByAuthorContaining(String keyword);

    Iterable<Book> findByIsbnContaining(String keyword);

    Iterable<Book> findByBookCategoryContaining(String keyword);

    Iterable<Book> findByTitleContainingOrAuthorContainingOrIsbnContainingOrBookCategoryContaining(
            String title, String author, String isbn, String category);

    List<Book> findAll();

}
