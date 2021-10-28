package com.bookeroo.microservice.admin.service;

import com.bookeroo.microservice.admin.exception.SellerNotFoundException;
import com.bookeroo.microservice.admin.exception.UserNotFoundException;
import com.bookeroo.microservice.admin.model.User;
import com.bookeroo.microservice.admin.model.User.UserRole;
import com.bookeroo.microservice.admin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for the {@link User} JPA entity.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ListingRepository listingRepository;
    private final TransactionRepository transactionRepository;
    private final SellerDetailsRepository sellerDetailsRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       ListingRepository listingRepository,
                       ReviewRepository reviewRepository,
                       TransactionRepository transactionRepository,
                       SellerDetailsRepository sellerDetailsRepository) {
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
        this.reviewRepository = reviewRepository;
        this.transactionRepository = transactionRepository;
        this.sellerDetailsRepository = sellerDetailsRepository;
    }

    public User getUserById(long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        user.orElseThrow(() -> new UserNotFoundException(String.format("User by id %d not found", id)));

        return user.get();
    }

    public User getSellerById(long id) throws SellerNotFoundException {
        User user = getUserById(id);
        if (!user.getRole().contains(UserRole.SELLER.name()))
            throw new UserNotFoundException(String.format("Seller by id %d not found", id));

        return user;
    }

    public User approveSeller(long id) {
        User user = getUserById(id);
        user.setRole("ROLE_" + UserRole.SELLER.name());
        return userRepository.save(user);
    }

    @Transactional
    public void rejectSeller(long id) {
        User user = getUserById(id);
        sellerDetailsRepository.deleteById(user.getId());
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("User by username %s not found", username)));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(long id) throws UserNotFoundException {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException(String.format("User by id %d not found", id));

        reviewRepository.deleteAllByUser_Id(id);
        listingRepository.deleteAllByUser_Id(id);
        transactionRepository.deleteByBuyer_Id(id);
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
