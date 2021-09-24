package com.bookeroo.microservice.book;


import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

@Component
public class DbInitialiser {

    private final BookRepository bookRepository;

    @Value("#{new Boolean('${db.initialise}')}")
    private boolean postConstruct;

    @Autowired
    public DbInitialiser(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    private void initialise() {
        if (postConstruct) {
            Book book = new Book();
            book.setTitle("randomTitle");
            book.setAuthor("randomAuthor");
            book.setPageCount(100);
            book.setPrice(10.0);
            book.setIsbn("1234567891011");
            book.setDescription("randomDescription");
            book.setCover("https://picsum.photos/200");
            bookRepository.save(book);

//            for (int i = 0; i < 6; i++)
//                bookRepository.save(getRandomBook());
        }
    }

    private Book getRandomBook() {
        Book book = new Book();
        book.setTitle("randomTitle");
        book.setAuthor("randomAuthor");
        book.setPageCount(new Random().nextInt(1000));
        book.setPrice(10.0);
        book.setIsbn(String.valueOf((long) Math.floor(Math.random() * 9000_000_000_000L) + 1000_000_000_000L));
        book.setDescription("randomDescription");
        book.setCover("https://picsum.photos/200");
        return book;
    }

}
