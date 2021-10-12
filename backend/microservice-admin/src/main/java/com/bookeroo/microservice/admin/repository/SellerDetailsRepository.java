package com.bookeroo.microservice.admin.repository;

import com.bookeroo.microservice.admin.model.SellerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * JPA repository for the {@link SellerDetails} entity.
 */
@Repository
public interface SellerDetailsRepository extends JpaRepository<SellerDetails, Long> {

    SellerDetails findByUser_Username(String username);

    List<SellerDetails> findAllByUser_RoleAndUser_RoleNot(String role, String roleNot);

    @Transactional
    void deleteByUser_Id(long id);

}
