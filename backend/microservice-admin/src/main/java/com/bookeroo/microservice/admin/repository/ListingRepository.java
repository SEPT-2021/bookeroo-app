package com.bookeroo.microservice.admin.repository;

import com.bookeroo.microservice.admin.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    @Transactional
    void deleteAllByUser_Username(String username);

    @Transactional
    void deleteAllByUser_Id(long id);

    @Transactional
    void deleteAllByUser_UsernameContaining(String username);

}
