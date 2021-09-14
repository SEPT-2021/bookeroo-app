package com.bookeroo.microservice.book.service;



import com.bookeroo.microservice.book.exception.BookNotFoundException;
import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BookDetailsService  {

    private final BookRepository BookRepository;

    @Autowired
    public BookDetailsService(BookRepository BookRepository) {
        this.BookRepository = BookRepository;
    }

    @Transactional
    public Book loadBookById(Long id) throws BookNotFoundException {
        try{
            return BookRepository.getById(id);
        }
        catch (Exception e){
            throw new BookNotFoundException("id isnt in table");
        }

    }

}
