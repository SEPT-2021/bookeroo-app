package com.bookeroo.microservice.login.service;

import com.bookeroo.microservice.login.exception.UserNotFoundException;
import com.bookeroo.microservice.login.model.User;
import com.bookeroo.microservice.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUserById(long id) throws UserNotFoundException {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException(String.format("User by id %d not found\n", id));

        return userRepository.findById(id);
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("User by username %s not found\n", username)));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(User user) throws UserNotFoundException {
        if (!userRepository.existsById(user.getId()))
            throw new UserNotFoundException(String.format("User %s not found\n", user.getUsername()));

        userRepository.save(user);
    }

    public void deleteUser(long id) throws UserNotFoundException {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException(String.format("User by id %d not found\n", id));

        userRepository.deleteUserById(id);
    }

    public List<User> getAllNonAdminUsers() {
        return userRepository.findAllByRolesNotContaining(User.Role.ADMIN.name());
    }

}
