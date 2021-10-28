package com.bookeroo.microservice.login.repository;

import com.bookeroo.microservice.login.model.User;
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

    User findById(long id);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    List<User> findAllByRoleNot(String role);

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean existsById(long id);

    @Transactional
    void deleteUserById(long id);

    @Transactional
    void deleteUserByUsername(String username);

}
