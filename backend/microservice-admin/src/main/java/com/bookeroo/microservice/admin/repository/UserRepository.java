package com.bookeroo.microservice.admin.repository;

import com.bookeroo.microservice.admin.model.User;
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

    List<User> findAllByRoleNotContaining(String role);

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean existsById(long id);

    @Transactional
    void deleteUserById(long id);

    @Transactional
    void deleteUserByUsername(String username);

    @Transactional
    void deleteAllByUsernameContaining(String username);

}
