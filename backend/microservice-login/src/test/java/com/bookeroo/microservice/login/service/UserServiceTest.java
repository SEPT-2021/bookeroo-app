package com.bookeroo.microservice.login.service;

import com.bookeroo.microservice.login.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService service;

    @Test
    void givenUser_whenUserSaved_returnUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername@test.com");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setPassword("testPassword");
        user.setConfirmPassword("");

        assertEquals(service.saveUser(user).getUsername(), user.getUsername());
    }

}