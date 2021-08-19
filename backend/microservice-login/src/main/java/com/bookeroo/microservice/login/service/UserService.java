package com.bookeroo.microservice.login.service;

import com.bookeroo.microservice.login.exception.UsernameAlreadyExistsException;
import com.bookeroo.microservice.login.model.User;
import com.bookeroo.microservice.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setConfirmPassword("");

            return userRepository.save(user);
        } catch (Exception exception) {
            throw new UsernameAlreadyExistsException(String.format("Username \"%s\" already exists", user.getUsername()));
        }
    }

}
