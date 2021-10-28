package com.bookeroo.microservice.seller.repository;

import com.bookeroo.microservice.seller.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * JPA repository for the {@link Book} entity.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(long id);

    Book findByIsbn(String isbn);

    List<Book> findAll();

    @Transactional
    void deleteAll();

}
