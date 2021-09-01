package com.bookeroo.microservice.book.repository;
import com.bookeroo.microservice.book.model.Book;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    Book findByISBN(int ISBN);

    Book getById(Long id);

}
