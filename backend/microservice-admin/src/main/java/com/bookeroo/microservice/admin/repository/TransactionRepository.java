package com.bookeroo.microservice.admin.repository;

import com.bookeroo.microservice.admin.model.Transaction;
import com.bookeroo.microservice.admin.model.TransactionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, TransactionKey> {

    @Transactional
    void deleteByBuyer_Id(long id);

    @Transactional
    void deleteByBuyer_Username(String username);

    @Transactional
    void deleteAllByBuyer_UsernameContaining(String username);

    @Transactional
    void deleteAllByListing_Id(long listingId);

}
