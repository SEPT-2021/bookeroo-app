package com.bookeroo.microservice.admin.repository;

import com.bookeroo.microservice.admin.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * JPA repository for the {@link Book} entity.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Transactional
    void deleteById(long id);

}
