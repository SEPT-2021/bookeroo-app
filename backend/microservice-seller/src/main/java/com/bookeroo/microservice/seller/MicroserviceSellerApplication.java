package com.bookeroo.microservice.seller;

import com.bookeroo.microservice.seller.repository.BookRepository;
import com.bookeroo.microservice.seller.repository.SellerDetailsRepository;
import com.bookeroo.microservice.seller.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = {UserRepository.class, BookRepository.class, SellerDetailsRepository.class})
public class MicroserviceSellerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceSellerApplication.class, args);
    }

}
