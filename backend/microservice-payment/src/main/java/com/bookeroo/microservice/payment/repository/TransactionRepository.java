package com.bookeroo.microservice.payment.repository;

import com.bookeroo.microservice.payment.model.Transaction;
import com.bookeroo.microservice.payment.model.TransactionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, TransactionKey> {

    Transaction findByListing_Id(long id);

    List<Transaction> findByBuyer_Id(long id);

    List<Transaction> findByOrderId(String id);

}
