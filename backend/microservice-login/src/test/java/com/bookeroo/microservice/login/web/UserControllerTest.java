package com.bookeroo.microservice.login.web;

import com.bookeroo.microservice.login.model.User;
import com.bookeroo.microservice.login.payload.LoginRequest;
import com.bookeroo.microservice.login.security.SecurityConstant;
import com.bookeroo.microservice.login.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    UserService service;

    @Test
    void givenUser_whenRegistered_thenReturnStatusCreated() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername@test.com");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setPassword("testPassword");
        user.setConfirmPassword("testPassword");

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    void givenUser_whenRegistered_thenReturnUserAsResponseBody() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername@test.com");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setPassword("testPassword");
        user.setConfirmPassword("testPassword");

        String response = mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andReturn().getResponse().getContentAsString();

        assertTrue(response.contains("testUsername@test.com"));
    }

    @Test
    void givenUser_whenRegistrationDetailsIncorrect_thenReturn() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername@test.com");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setPassword("testPassword");
        user.setConfirmPassword("testPassword");

        String response = mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andReturn().getResponse().getContentAsString();

        assertTrue(response.contains("testUsername@test.com"));
    }

    @Test
    void givenCredentials_whenAuthenticated_thenReturnStatusOk() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername@test.com");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setPassword("testPassword");
        user.setConfirmPassword("testPassword");
        service.saveUser(user);

        LoginRequest request = new LoginRequest();
        request.setUsername("testUsername@test.com");
        request.setPassword("testPassword");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void givenCredentials_whenEmailIncorrect_thenReturnUnauthorised() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername@test.com");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setPassword("testPassword");
        user.setConfirmPassword("testPassword");
        service.saveUser(user);

        LoginRequest request = new LoginRequest();
        request.setUsername("wrongEmail@test.com");
        request.setPassword("testPassword");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void givenCredentials_whenPasswordIncorrect_thenReturnUnauthorised() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername@test.com");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setPassword("testPassword");
        user.setConfirmPassword("testPassword");
        service.saveUser(user);

        LoginRequest request = new LoginRequest();
        request.setUsername("testUsername@test.com");
        request.setPassword("wrongPassword");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void givenCredentials_whenAuthenticated_thenReturnJWTToken() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername@test.com");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setPassword("testPassword");
        user.setConfirmPassword("testPassword");
        service.saveUser(user);

        LoginRequest request = new LoginRequest();
        request.setUsername("testUsername@test.com");
        request.setPassword("testPassword");

        String response = mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        assertTrue(response.contains(SecurityConstant.TOKEN_PREFIX));
    }

}