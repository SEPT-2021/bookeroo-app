package com.bookeroo.microservice.login;

import com.bookeroo.microservice.login.repository.NewsletterRepository;
import com.bookeroo.microservice.login.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableJpaRepositories(basePackageClasses = {UserRepository.class, NewsletterRepository.class})
public class MicroserviceLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceLoginApplication.class, args);
    }

}
