package com.bookeroo.microservice.seller.web;

import com.bookeroo.microservice.seller.model.CustomUserDetails;
import com.bookeroo.microservice.seller.model.SellerDetails;
import com.bookeroo.microservice.seller.model.User;
import com.bookeroo.microservice.seller.repository.UserRepository;
import com.bookeroo.microservice.seller.security.JWTTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.bookeroo.microservice.seller.security.SecurityConstant.AUTHORIZATION_HEADER;
import static com.bookeroo.microservice.seller.security.SecurityConstant.JWT_SCHEME;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class SellerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JWTTokenProvider jwtTokenProvider;

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
    void givenExistingUser_whenGivenSellerDetails_registerSeller() throws Exception {
        User user = setupUser();
        user = userRepository.save(user);
        SellerDetails sellerDetails = setupSellerDetails();
        sellerDetails.setUser(user);

        String token = jwtTokenProvider.generateToken(new CustomUserDetails(user));

        String response = mockMvc.perform(post("/api/sellers/register")
                        .header(AUTHORIZATION_HEADER, JWT_SCHEME + token)
                        .content(objectMapper.writeValueAsString(sellerDetails))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        SellerDetails responseBody = objectMapper.readValue(response, SellerDetails.class);
        assertTrue(responseBody.getAbn().equals(sellerDetails.getAbn())
                && responseBody.getBusinessName().equals(sellerDetails.getBusinessName())
                && responseBody.getBusinessPhone().equals(sellerDetails.getBusinessPhone()));
    }

}
