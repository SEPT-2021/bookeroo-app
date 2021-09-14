package com.bookeroo.microservice.payment.repository;

import com.bookeroo.microservice.payment.model.BookListing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<BookListing, Long> {
}
