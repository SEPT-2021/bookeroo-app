package com.bookeroo.microservice.payment.repository;

import com.bookeroo.microservice.payment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getById(long id);

    User findById(long id);

    User getByUsername(String username);

    Optional<User> findByUsername(String username);

    List<User> findAll();
}