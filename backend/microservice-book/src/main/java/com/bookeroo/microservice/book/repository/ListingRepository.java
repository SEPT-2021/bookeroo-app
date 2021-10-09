package com.bookeroo.microservice.book.repository;

import com.bookeroo.microservice.book.model.Listing;
import com.bookeroo.microservice.book.model.ListingKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepository extends JpaRepository<Listing, ListingKey> {
}
