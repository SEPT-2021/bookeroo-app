package com.bookeroo.microservice.admin;

import com.bookeroo.microservice.admin.repository.SellerDetailsRepository;
import com.bookeroo.microservice.admin.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = {UserRepository.class, SellerDetailsRepository.class})
public class MicroserviceAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceAdminApplication.class, args);
    }

}
