package com.bookeroo.microservice.book.repository;

import com.bookeroo.microservice.book.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    List<Listing> findByUser_Id(long id);

    List<Listing> findByBook_Id(long id);

}
