package com.bookeroo.microservice.admin.web;

import com.bookeroo.microservice.admin.model.CustomUserDetails;
import com.bookeroo.microservice.admin.model.SellerDetails;
import com.bookeroo.microservice.admin.model.User;
import com.bookeroo.microservice.admin.repository.SellerDetailsRepository;
import com.bookeroo.microservice.admin.repository.UserRepository;
import com.bookeroo.microservice.admin.security.JWTTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bookeroo.microservice.admin.security.SecurityConstant.AUTHORIZATION_HEADER;
import static com.bookeroo.microservice.admin.security.SecurityConstant.JWT_SCHEME;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SellerDetailsRepository sellerDetailsRepository;
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

    User setupAdmin() {
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
        user.setRole(User.UserRole.ADMIN.name);
        user.setEnabled(true);
        return user;
    }

    SellerDetails setupSellerDetails() {
        SellerDetails sellerDetails = new SellerDetails();
        sellerDetails.setAbn(String.valueOf((long) Math.floor(Math.random() * 9000_000_000_0L) + 1000_000_000_0L));
        sellerDetails.setBusinessName("testCompany");
        sellerDetails.setBusinessPhone("+(61) 413 170 399");
        return sellerDetails;
    }

    @Test
    void givenAdminPresent_whenAuthenticated_allowViewingAdminProfile() throws Exception {
        User admin = setupAdmin();
        admin = userRepository.save(admin);
        String token = jwtTokenProvider.generateToken(new CustomUserDetails(admin));

        String response = mockMvc.perform(get("/api/admins/profile")
                        .header(AUTHORIZATION_HEADER, JWT_SCHEME + token))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        User responseBody = objectMapper.readValue(response, User.class);
        assertEquals(responseBody.getId(), admin.getId());
    }

    @Test
    void inspectAllUsers() throws Exception {
        User admin = setupAdmin();
        admin = userRepository.save(admin);
        String token = jwtTokenProvider.generateToken(new CustomUserDetails(admin));
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            User user = setupUser();
            user = userRepository.save(user);
            users.add(user);
        }

        String response = mockMvc.perform(get("/api/admins/inspect-users")
                        .header(AUTHORIZATION_HEADER, JWT_SCHEME + token))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<User> responseBody = Arrays.asList(objectMapper.readValue(response, User[].class));
        assertTrue(responseBody.containsAll(users));
    }

    @Test
    void inspectUser() throws Exception {
        User admin = setupAdmin();
        admin = userRepository.save(admin);
        String token = jwtTokenProvider.generateToken(new CustomUserDetails(admin));
        User user = setupUser();
        user = userRepository.save(user);

        String response = mockMvc.perform(get("/api/admins/inspect-users/" + user.getId())
                        .header(AUTHORIZATION_HEADER, JWT_SCHEME + token))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        User responseBody = objectMapper.readValue(response, User.class);
        assertEquals(responseBody.getId(), user.getId());
    }

    @Test
    void banUser() throws Exception {
        User admin = setupAdmin();
        admin = userRepository.save(admin);
        String token = jwtTokenProvider.generateToken(new CustomUserDetails(admin));
        User user = setupUser();
        user = userRepository.save(user);

        String response = mockMvc.perform(post("/api/admins/toggle-ban/" + user.getId())
                        .header(AUTHORIZATION_HEADER, JWT_SCHEME + token))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        User responseBody = objectMapper.readValue(response, User.class);
        assertFalse(responseBody.isEnabled());
    }

    @Test
    void deleteUser() throws Exception {
        User admin = setupAdmin();
        admin = userRepository.save(admin);
        String token = jwtTokenProvider.generateToken(new CustomUserDetails(admin));
        User user = setupUser();
        user = userRepository.save(user);

        mockMvc.perform(delete("/api/admins/delete-users/" + user.getId())
                        .header(AUTHORIZATION_HEADER, JWT_SCHEME + token))
                .andExpect(status().isOk());

        assertFalse(userRepository.findById(user.getId()).isPresent());
    }

    @Test
    @Transactional
    void inspectAllSellers() throws Exception {
        User admin = setupAdmin();
        admin = userRepository.save(admin);
        String token = jwtTokenProvider.generateToken(new CustomUserDetails(admin));
        List<SellerDetails> sellerDetailsList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            User seller = setupUser();
            seller = userRepository.save(seller);

            SellerDetails sellerDetails = setupSellerDetails();
            sellerDetails.setUser(seller);
            sellerDetails = sellerDetailsRepository.save(sellerDetails);
            sellerDetailsList.add(sellerDetails);
        }

        String response = mockMvc.perform(get("/api/admins/inspect-sellers")
                        .header(AUTHORIZATION_HEADER, JWT_SCHEME + token))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<SellerDetails> responseBody = Arrays.asList(objectMapper.readValue(response, SellerDetails[].class));
        assertTrue(responseBody.containsAll(sellerDetailsList));
    }

    @Test
    @Transactional
    void approveSeller() throws Exception {
        User admin = setupAdmin();
        admin = userRepository.save(admin);
        String token = jwtTokenProvider.generateToken(new CustomUserDetails(admin));
        User seller = setupUser();
        seller = userRepository.save(seller);
        SellerDetails sellerDetails = setupSellerDetails();
        sellerDetails.setUser(seller);
        sellerDetails = sellerDetailsRepository.save(sellerDetails);

        String response = mockMvc.perform(post("/api/admins/approve-seller/" + seller.getId())
                        .header(AUTHORIZATION_HEADER, JWT_SCHEME + token))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        User responseBody = objectMapper.readValue(response, User.class);
        assertEquals(responseBody.getRole(), User.UserRole.SELLER.name);
        assertTrue(sellerDetailsRepository.findById(sellerDetails.getId()).isPresent());
    }

    @Test
    @Transactional
    void rejectSeller() throws Exception {
        User admin = setupAdmin();
        admin = userRepository.save(admin);
        String token = jwtTokenProvider.generateToken(new CustomUserDetails(admin));
        User seller = setupUser();
        seller = userRepository.save(seller);
        SellerDetails sellerDetails = setupSellerDetails();
        sellerDetails.setUser(seller);
        sellerDetails = sellerDetailsRepository.save(sellerDetails);

        String response = mockMvc.perform(post("/api/admins/reject-seller/" + seller.getId())
                        .header(AUTHORIZATION_HEADER, JWT_SCHEME + token))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        User responseBody = objectMapper.readValue(response, User.class);
        assertNotEquals(responseBody.getRole(), User.UserRole.SELLER.name);
        assertFalse(sellerDetailsRepository.findById(sellerDetails.getId()).isPresent());
    }

}
