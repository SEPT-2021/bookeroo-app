package com.bookeroo.microservice.seller.repository;

import com.bookeroo.microservice.seller.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    Listing getById(long id);

    List<Listing> getByBook_Id(long id);

}
