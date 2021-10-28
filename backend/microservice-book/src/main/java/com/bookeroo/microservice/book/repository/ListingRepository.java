package com.bookeroo.microservice.book.repository;

import com.bookeroo.microservice.book.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    List<Listing> findByUser_Id(long id);

    List<Listing> findByBook_Id(long id);

    @Transactional
    void deleteById(long id);

    @Transactional
    void deleteAllByUser_Username(String username);

    @Transactional
    void deleteAllByBook_Id(long id);

}
