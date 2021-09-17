package com.bookeroo.microservice.login.service;

import com.bookeroo.microservice.login.exception.UserNotFoundException;
import com.bookeroo.microservice.login.model.User;
import com.bookeroo.microservice.login.repository.UserRepository;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService service;

    @Autowired
    UserRepository repository;

    User setupUser() {
        User user = new User();
        user.setUsername(RandomString.make(8) + "@test.com");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setPassword("testPassword");
        user.setRoles("ROLE_USER");
        user.setEnabled(true);
        return user;
    }

    @Test
    void givenUser_whenUserSaved_returnUser() {
        User user = setupUser();

        assertEquals(service.saveUser(user).getUsername(), user.getUsername());
    }

    @Test
    void givenSavedUser_whenGivenId_returnUser() throws UserNotFoundException {
        User user = setupUser();
        long id = service.saveUser(user).getId();

        assertNotNull(service.getUserById(id));
    }

    @Test
    void givenSavedUser_whenGivenUsername_returnUser() throws UserNotFoundException {
        User user = setupUser();
        String username = service.saveUser(user).getUsername();

        assertNotNull(service.getUserByUsername(username));
    }

    @Test
    void givenSavedUsers_whenAllUsersFetched_returnUsers() {
        User user = setupUser();
        service.saveUser(user);

        assertNotEquals(service.getAllUsers().size(), 0);
    }

    @Test
    void givenSavedUser_whenSavingAgain_updateUser() {
        User user = setupUser();
        user = service.saveUser(user);
        String userLastName = RandomString.make(8);
        user.setLastName(userLastName);
        user = service.saveUser(user);

        assertEquals(user.getLastName(), userLastName);
    }

    @Test
    void givenUserId_whenDeleteIsAsked_deleteUser() throws UserNotFoundException {
        User user = setupUser();
        long id = service.saveUser(user).getId();
        service.deleteUser(id);

        assertThrows(UserNotFoundException.class, () -> service.getUserById(id));
    }

    @Test
    void givenUsersPresent_whenNonAdminUsersFetched_doNotReturnAdmin() {
        User user = setupUser();
        User admin = setupUser();
        admin.setRoles("ROLE_ADMIN");

        service.saveUser(user);
        service.saveUser(admin);

        assertFalse(service.getAllNonAdminUsers().contains(admin));
    }

    @Test
    void givenUserFieldEmpty_whenUserSaved_throwsException() {
        User user = setupUser();
        user.setLastName("");

        assertThrows(Exception.class, () -> repository.save(user));
    }

}
