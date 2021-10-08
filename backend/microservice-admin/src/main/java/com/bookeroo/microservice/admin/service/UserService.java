package com.bookeroo.microservice.admin.service;

import com.bookeroo.microservice.admin.exception.SellerNotFoundException;
import com.bookeroo.microservice.admin.exception.UserNotFoundException;
import com.bookeroo.microservice.admin.model.User;
import com.bookeroo.microservice.admin.model.User.UserRole;
import com.bookeroo.microservice.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Service layer for the {@link User} JPA entity.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserById(long id) throws UserNotFoundException {
        User user;
        if ((user = userRepository.findById(id)) == null)
            throw new UserNotFoundException(String.format("User by id %d not found", id));

        return user;
    }

    public User getSellerById(long id) throws SellerNotFoundException {
        User user;
        if ((user = userRepository.findById(id)) == null || !user.getRole().contains(UserRole.SELLER.name()))
            throw new UserNotFoundException(String.format("Seller by id %d not found", id));

        return user;
    }

    public User approveSeller(long id) {
        User user = getUserById(id);
        user.setRole("ROLE_" + UserRole.SELLER.name());
        return userRepository.save(user);
    }

    public User rejectSeller(long id) {
        // TODO emailClient.send(user.getUsername(), "Seller request rejected", "Cause ...");

        return null;
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("User by username %s not found", username)));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(long id, User updatedUser) throws UserNotFoundException {
        User user = getUserById(id);
        for (Field field : User.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                field.set(user, (!field.getType().isPrimitive() && field.get(updatedUser) != null)
                        ? field.get(updatedUser)
                        : field.get(user));
                if (field.getName().equals("password"))
                    field.set(user, passwordEncoder.encode(field.get(updatedUser).toString()));
            } catch (IllegalAccessException ignored) {}
        }

        return userRepository.save(user);
    }

    public void deleteUser(long id) throws UserNotFoundException {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException(String.format("User by id %d not found", id));

        userRepository.deleteUserById(id);
    }

    public List<User> getAllNonAdminUsers() {
        return userRepository.findAllByRoleNotContaining(UserRole.ADMIN.name());
    }

    public User toggleUserBan(long id) {
        User user = getUserById(id);
        user.setEnabled(!user.isEnabled());
        return userRepository.save(user);
    }

}
