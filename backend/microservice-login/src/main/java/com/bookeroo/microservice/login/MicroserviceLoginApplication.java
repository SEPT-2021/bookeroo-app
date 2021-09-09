package com.bookeroo.microservice.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroserviceLoginApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "microservice-login");
        SpringApplication.run(MicroserviceLoginApplication.class, args);
    }

}
