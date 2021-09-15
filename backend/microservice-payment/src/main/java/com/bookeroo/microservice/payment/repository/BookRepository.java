package com.bookeroo.microservice.payment.repository;

import com.bookeroo.microservice.payment.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book getById(Long id);

    Optional<Book> findById(Long id);

    Book findByIsbn(String isbn);

    Iterable<Book> findByTitleContains(String keyword);

    Iterable<Book> findByAuthorContains(String keyword);

    List<Book> findAll();

}
