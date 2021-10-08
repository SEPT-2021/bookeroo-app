package com.bookeroo.microservice.login.web;

import com.bookeroo.microservice.login.model.User;
import com.bookeroo.microservice.login.payload.AuthenticationRequest;
import com.bookeroo.microservice.login.payload.AuthenticationResponse;
import com.bookeroo.microservice.login.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

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
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        return user;
    }

    @Test
    void givenUser_whenRegistered_thenReturnStatusCreated() throws Exception {
        User user = setupUser();

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    void givenUser_whenRegistered_thenReturnUserAsResponseBody() throws Exception {
        User user = setupUser();

        String response = mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andReturn().getResponse().getContentAsString();

        assertTrue(response.contains(user.getUsername()));
    }

    @Test
    void givenUser_whenRegistrationDetailsIncorrect_thenReturnError() throws Exception {
        User user = setupUser();
        user.setLastName("");
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenCredentials_whenAuthenticated_thenReturnStatusOk() throws Exception {
        User user = setupUser();
        userService.saveUser(user);

        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(user.getUsername());
        request.setPassword("testPassword");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void givenCredentials_whenEmailIncorrect_thenReturnUnauthorised() throws Exception {
        User user = setupUser();
        userService.saveUser(user);

        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("wrongEmail@test.com");
        request.setPassword("testPassword");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void givenCredentials_whenPasswordIncorrect_thenReturnUnauthorised() throws Exception {
        User user = setupUser();
        userService.saveUser(user);

        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(user.getUsername());
        request.setPassword("wrongPassword");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void givenCredentials_whenAuthenticated_thenReturnJWTToken() throws Exception {
        User user = setupUser();
        userService.saveUser(user);

        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(user.getUsername());
        request.setPassword("testPassword");

        String json = mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        System.out.println(json);
        AuthenticationResponse response = new Gson().fromJson(json, AuthenticationResponse.class);
        assertFalse(response.getJwt().isEmpty());
    }

}
