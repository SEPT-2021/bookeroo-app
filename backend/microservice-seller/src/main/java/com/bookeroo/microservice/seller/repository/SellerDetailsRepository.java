package com.bookeroo.microservice.seller.repository;

import com.bookeroo.microservice.seller.model.SellerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for the {@link SellerDetails} entity.
 */
@Repository
public interface SellerDetailsRepository extends JpaRepository<SellerDetails, Long> {

    SellerDetails findByUser_Username(String username);

}
