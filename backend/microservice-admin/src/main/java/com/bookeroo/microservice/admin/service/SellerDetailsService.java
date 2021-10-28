package com.bookeroo.microservice.admin.service;

import com.bookeroo.microservice.admin.model.SellerDetails;
import com.bookeroo.microservice.admin.model.User;
import com.bookeroo.microservice.admin.model.User.UserRole;
import com.bookeroo.microservice.admin.repository.SellerDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<User> getAllPendingSellers() {
        List<User> sellers = new ArrayList<>();
        sellerDetailsRepository.findAllByUser_RoleAndUser_RoleNot(UserRole.USER.name, UserRole.SELLER.name).forEach(
                sellerDetails -> sellers.add(sellerDetails.getUser())
        );

        return sellers;
    }

}
