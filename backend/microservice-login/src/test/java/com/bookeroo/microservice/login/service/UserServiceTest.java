package com.bookeroo.microservice.login.service;

import com.bookeroo.microservice.login.exception.UserNotFoundException;
import com.bookeroo.microservice.login.model.User;
import com.bookeroo.microservice.login.repository.UserRepository;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService service;

    @Autowired
    UserRepository repository;

    final List<String> testUsers = new ArrayList<>();

    User setupUser() {
        User user = new User();
        user.setUsername(RandomString.make(8) + "@test.com");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setPassword("testPassword");
        user.setAddressLine1("123 Bookeroo St");
        user.setAddressLine2("Apartment 1");
        user.setCity("Melbourne");
        user.setState("VIC");
        user.setPostalCode("3001");
        user.setPhoneNumber("+(61) 413 170 399");
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        testUsers.add(user.getUsername());
        return user;
    }

    @Test
    void givenUser_whenUserSaved_returnUser() {
        User user = setupUser();

        assertEquals(service.saveUser(user, true).getUsername(), user.getUsername());
    }

    @Test
    void givenSavedUser_whenGivenId_returnUser() throws UserNotFoundException {
        User user = setupUser();
        long id = service.saveUser(user, true).getId();

        assertNotNull(service.getUserById(id));
    }

    @Test
    void givenSavedUser_whenGivenUsername_returnUser() throws UserNotFoundException {
        User user = setupUser();
        String username = service.saveUser(user, true).getUsername();

        assertNotNull(service.getUserByUsername(username));
    }

    @Test
    void givenSavedUsers_whenAllUsersFetched_returnUsers() {
        User user = setupUser();
        service.saveUser(user, true);

        assertNotEquals(service.getAllUsers().size(), 0);
    }

    @Test
    void givenSavedUser_whenSavingAgain_updateUser() {
        User user = setupUser();
        user = service.saveUser(user, true);
        String userLastName = "updatedLastName";
        user.setLastName(userLastName);
        user = service.saveUser(user, true);

        assertEquals(user.getLastName(), userLastName);
    }

    @Test
    void givenUserId_whenDeleteIsAsked_deleteUser() throws UserNotFoundException {
        User user = setupUser();
        long id = service.saveUser(user, true).getId();
        service.deleteUser(id);

        assertThrows(UserNotFoundException.class, () -> service.getUserById(id));
    }

    @Test
    void givenUsersPresent_whenNonAdminUsersFetched_doNotReturnAdmin() {
        User user = setupUser();
        User admin = setupUser();
        admin.setRole("ROLE_ADMIN");

        service.saveUser(user, true);
        service.saveUser(admin, true);

        assertFalse(service.getAllNonAdminUsers().contains(admin));
    }

    @Test
    void givenUserFieldEmpty_whenUserSaved_throwsException() {
        User user = setupUser();
        user.setLastName("");

        assertThrows(Exception.class, () -> repository.save(user));
    }

}
