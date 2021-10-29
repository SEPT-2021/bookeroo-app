package com.bookeroo.microservice.payment.repository;

import com.bookeroo.microservice.payment.model.Transaction;
import com.bookeroo.microservice.payment.model.TransactionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, TransactionKey> {

    Optional<Transaction> findByListing_Id(long id);

    List<Transaction> findByBuyer_Id(long id);

    List<Transaction> findByOrderId(String id);

    @Transactional
    void delete(Transaction transaction);

}
