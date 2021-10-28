package com.bookeroo.microservice.payment.repository;

import com.bookeroo.microservice.payment.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    @Transactional
    void deleteById(long id);

}
