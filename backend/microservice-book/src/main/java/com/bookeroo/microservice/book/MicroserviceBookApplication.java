package com.bookeroo.microservice.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroserviceBookApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "microservice-book");
        SpringApplication.run(MicroserviceBookApplication.class, args);
    }

}
