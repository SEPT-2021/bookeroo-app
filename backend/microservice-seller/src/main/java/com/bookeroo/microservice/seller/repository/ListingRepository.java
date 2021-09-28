package com.bookeroo.microservice.seller.repository;

import com.bookeroo.microservice.seller.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for the {@link Listing} entity.
 */
@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    Listing getById(long id);

    List<Listing> getByBook_Id(long id);

}
