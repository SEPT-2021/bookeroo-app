package com.bookeroo.microservice.book.repository;

import com.bookeroo.microservice.book.model.Book;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    Book getById(Long id);

    Optional<Book> findById(Long id);

    Book findByIsbn(String isbn);

    Iterable<Book> findByTitleContains(String keyword);

    Iterable<Book> findByAuthorContains(String keyword);

}
