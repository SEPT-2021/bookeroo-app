package com.bookeroo.microservice.payment;

import com.bookeroo.microservice.payment.repository.ListingRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = {ListingRepository.class})
public class MicroservicePaymentApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "microservice-payment");
        SpringApplication.run(MicroservicePaymentApplication.class, args);
    }

}
