package com.bookeroo.microservice.login.service;

import com.bookeroo.microservice.login.exception.UserNotFoundException;
import com.bookeroo.microservice.login.model.User;
import com.bookeroo.microservice.login.model.User.UserRole;
import com.bookeroo.microservice.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Service layer for the {@link User} JPA entity.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(@Valid User user, boolean isNew) {
        if (isNew) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled(true);
        }
        return userRepository.save(user);
    }

    public User getUserById(long id) {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException(String.format("User by id %d not found\n", id));

        return userRepository.findById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("User by username %s not found\n", username)));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String username, User updatedUser) throws UserNotFoundException {
        User user = getUserByUsername(username);
        for (Field field : User.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (field.get(updatedUser) != null) {
                    field.set(user, (!field.getType().isPrimitive()
                            && !Arrays.asList("role", "createdAt", "updatedAt").contains(field.getName()))
                            ? field.get(updatedUser)
                            : field.get(user));
                    if (field.getName().equals("password"))
                        field.set(user, passwordEncoder.encode(field.get(updatedUser).toString()));
                }
            } catch (IllegalAccessException ignored) {}
        }

        return user;
    }

    public void deleteUser(long id) {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException(String.format("User by id %d not found\n", id));

        userRepository.deleteUserById(id);
    }

    public List<User> getAllNonAdminUsers() {
        return userRepository.findAllByRoleNot(UserRole.ADMIN.name);
    }

    @Transactional
    public void deleteUserByUsername(String username) {
        userRepository.deleteUserByUsername(username);
    }

}
