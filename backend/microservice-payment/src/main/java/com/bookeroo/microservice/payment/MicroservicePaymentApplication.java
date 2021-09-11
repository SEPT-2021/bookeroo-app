package com.bookeroo.microservice.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroservicePaymentApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "microservice-payment");
        SpringApplication.run(MicroservicePaymentApplication.class, args);
    }

}
