package com.bookeroo.microservice.book;

import com.bookeroo.microservice.book.repository.BookRepository;
import com.bookeroo.microservice.book.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = { BookRepository.class, UserRepository.class })
public class MicroserviceBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceBookApplication.class, args);
    }

}
