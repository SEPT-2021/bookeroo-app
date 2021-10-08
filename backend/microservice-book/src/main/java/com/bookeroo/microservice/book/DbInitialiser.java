package com.bookeroo.microservice.book;


import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.model.Book.BookCategory;
import com.bookeroo.microservice.book.model.Book.BookCondition;
import com.bookeroo.microservice.book.repository.BookRepository;
import com.bookeroo.microservice.book.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

@Component
public class DbInitialiser {

    private final BookRepository bookRepository;
    private final S3Service s3Service;

    @Value("#{new Boolean('${db.initialise}')}")
    private boolean postConstruct;

    @Autowired
    public DbInitialiser(BookRepository bookRepository, S3Service s3Service) {
        this.bookRepository = bookRepository;
        this.s3Service = s3Service;
    }

    @PostConstruct
    private void initialise() {
        if (postConstruct) {
            bookRepository.deleteAll();
            for (int i = 0; i < 8; i++) {
                try {
                    bookRepository.save(getRandomBook());
                } catch (Exception ignore) {}
            }
        }
    }

    private Book getRandomBook() {
        Random random = new Random();
        Book book = new Book();
        book.setTitle("randomTitle");
        book.setAuthor("randomAuthor");
        book.setPageCount(String.valueOf(random.nextInt(1000)));
        book.setIsbn(String.valueOf((long) Math.floor(Math.random() * 9000_000_000_000L) + 1000_000_000_000L));
        book.setDescription("randomDescription");
        book.setPrice(String.valueOf(random.nextFloat() % 10.0f));
        book.setBookCondition(BookCondition.values()[random.nextInt(BookCondition.values().length)].name());
        book.setBookCategory(BookCategory.values()[random.nextInt(BookCategory.values().length)].name());
        try {
            book.setCover(s3Service.uploadFile(new URL("https://picsum.photos/360/640"), book.getTitle()));
        } catch (IOException ignore) {}
        return book;
    }

}
