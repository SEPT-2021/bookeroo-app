package com.bookeroo.microservice.seller.service;

import com.bookeroo.microservice.seller.exception.UserIsNotSellerException;
import com.bookeroo.microservice.seller.model.SellerDetails;
import com.bookeroo.microservice.seller.repository.SellerDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerDetailsService {

    private final SellerDetailsRepository sellerDetailsRepository;

    @Autowired
    public SellerDetailsService(SellerDetailsRepository sellerDetailsRepository) {
        this.sellerDetailsRepository = sellerDetailsRepository;
    }

    public SellerDetails saveSellerDetails(SellerDetails sellerDetails) {
        return sellerDetailsRepository.save(sellerDetails);
    }

    public SellerDetails getSellerDetailsByUsername(String username) {
        SellerDetails sellerDetails;
        if ((sellerDetails = sellerDetailsRepository.findByUser_Username(username)) == null)
            throw new UserIsNotSellerException(String.format("User with username %s is not a seller", username));

        return sellerDetails;
    }

}
