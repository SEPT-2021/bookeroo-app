package com.bookeroo.microservice.seller.repository;

import com.bookeroo.microservice.seller.model.Book;
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

    Iterable<Book> findByTitleContains(String keyword);

    Iterable<Book> findByAuthorContains(String keyword);

    Iterable<Book> findByTitleContainsOrAuthorContainsOrDescriptionContains(
            String title, String author, String description);

    List<Book> findAll();

}
