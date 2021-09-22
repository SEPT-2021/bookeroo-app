package com.bookeroo.microservice.seller.repository;

import com.bookeroo.microservice.seller.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean existsById(long id);

    void deleteUserById(long id);

    List<User> findAllByRolesNotContaining(String role);

}
