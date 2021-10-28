package com.bookeroo.microservice.book.repository;

import com.bookeroo.microservice.book.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    User getByUsername(String username);

    List<User> findAll();

    @Transactional
    void deleteUserByUsername(String username);

    @Transactional
    void deleteAllByUsernameContaining(String username);

}
