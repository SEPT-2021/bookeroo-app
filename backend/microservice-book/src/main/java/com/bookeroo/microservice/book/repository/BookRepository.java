package com.bookeroo.microservice.book.repository;

import com.bookeroo.microservice.book.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book getById(long id);

    Optional<Book> findById(long id);

    Book findByIsbn(String isbn);

    Iterable<Book> findByTitleContains(String keyword);

    Iterable<Book> findByAuthorContains(String keyword);

    Iterable<Book> findByDescriptionContains(String keyword);

    Iterable<Book> findByTitleContainsOrAuthorContainsOrDescriptionContains(
            String title, String author, String description);

    List<Book> findAll();

}
