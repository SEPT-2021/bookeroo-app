package com.bookeroo.microservice.seller.service;

import com.bookeroo.microservice.seller.exception.SellerExistsException;
import com.bookeroo.microservice.seller.model.SellerDetails;
import com.bookeroo.microservice.seller.model.User;
import com.bookeroo.microservice.seller.repository.UserRepository;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SellerDetailsServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SellerDetailsService sellerDetailsService;

    User setupUser() {
        User user = new User();
        user.setUsername(RandomString.make(8) + "@test.com");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setPassword("testPassword");
        user.setAddressLine1("123 Bookeroo St");
        user.setAddressLine2("Apartment 1");
        user.setCity("Melbourne");
        user.setState("VIC");
        user.setPostalCode("3001");
        user.setPhoneNumber("+(61) 413 170 399");
        user.setRole(User.UserRole.USER.name);
        user.setEnabled(true);
        return user;
    }

    SellerDetails setupSellerDetails() {
        SellerDetails sellerDetails = new SellerDetails();
        sellerDetails.setAbn(String.valueOf((long) Math.floor(Math.random() * 9_000_000_000_0L) + 1_000_000_000_0L));
        sellerDetails.setBusinessName("Bookeroo Inc.");
        sellerDetails.setBusinessPhone("+(61) 413 170 399");
        return sellerDetails;
    }

    @Test
    @Transactional
    void givenExistingUser_whenGivenSellerDetails_saveSellerDetails() {
        User user = setupUser();
        user = userRepository.save(user);

        SellerDetails sellerDetails = setupSellerDetails();
        sellerDetails.setUser(user);
        sellerDetails = sellerDetailsService.saveSellerDetails(sellerDetails);

        assertNotNull(sellerDetails);
    }

    @Test
    @Transactional
    void givenExistingSeller_whenGivenSellerDetails_throwException() {
        User user = setupUser();
        SellerDetails sellerDetails = setupSellerDetails();
        sellerDetails.setUser(user);
        sellerDetailsService.saveSellerDetails(sellerDetails);

        assertThrows(SellerExistsException.class, () -> sellerDetailsService.saveSellerDetails(sellerDetails));
    }

    @Test
    @Transactional
    void givenSellerUsername_whenRequested_returnSellerUsername() {
        User user = setupUser();
        user = userRepository.save(user);

        SellerDetails sellerDetails = setupSellerDetails();
        sellerDetails.setUser(user);
        sellerDetails = sellerDetailsService.saveSellerDetails(sellerDetails);

        assertEquals(sellerDetailsService.getSellerDetailsByUsername(user.getUsername()), sellerDetails);
    }

}