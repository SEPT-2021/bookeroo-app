package com.bookeroo.microservice.admin.service;

import com.bookeroo.microservice.admin.exception.SellerNotFoundException;
import com.bookeroo.microservice.admin.exception.UserNotFoundException;
import com.bookeroo.microservice.admin.model.User;
import com.bookeroo.microservice.admin.model.User.UserRole;
import com.bookeroo.microservice.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service layer for the {@link User} JPA entity.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public User updateUser(User user) throws UserNotFoundException {
        if (!userRepository.existsById(user.getId()))
            throw new UserNotFoundException(String.format("User %s not found", user.getUsername()));

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(long id) throws UserNotFoundException {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException(String.format("User by id %d not found", id));

        userRepository.deleteUserById(id);
    }

    public List<User> getAllNonAdminUsers() {
        return userRepository.findAllByRole(UserRole.ADMIN.name());
    }

    public List<User> getAllNonApprovedSellers() {
        return userRepository.findAllByRoleAndRoleNot(UserRole.ADMIN.name(), UserRole.SELLER.name());
    }

    public User toggleUserBan(long id) {
        User user = getUserById(id);
        user.setEnabled(!user.isEnabled());
        return userRepository.save(user);
    }

}
