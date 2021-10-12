package com.bookeroo.microservice.login.web;

import com.bookeroo.microservice.login.exception.UserFieldValidationException;
import com.bookeroo.microservice.login.exception.UserNotFoundException;
import com.bookeroo.microservice.login.exception.UsernameAlreadyExistsException;
import com.bookeroo.microservice.login.model.User;
import com.bookeroo.microservice.login.payload.AuthenticationRequest;
import com.bookeroo.microservice.login.payload.AuthenticationResponse;
import com.bookeroo.microservice.login.security.JWTTokenProvider;
import com.bookeroo.microservice.login.service.CustomUserDetailsService;
import com.bookeroo.microservice.login.service.UserService;
import com.bookeroo.microservice.login.service.ValidationErrorService;
import com.bookeroo.microservice.login.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST Controller to hold the microservice's user endpoint implementations.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final CustomUserDetailsService userDetailsService;
    private final UserValidator userValidator;
    private final ValidationErrorService validationErrorService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider tokenProvider;

    @Autowired
    public UserController(UserService userService,
                          CustomUserDetailsService userDetailsService,
                          UserValidator userValidator,
                          ValidationErrorService validationErrorService,
                          AuthenticationManager authenticationManager,
                          JWTTokenProvider tokenProvider) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.userValidator = userValidator;
        this.validationErrorService = validationErrorService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        userValidator.validate(user, result);
        ResponseEntity<?> errorMap = validationErrorService.mapValidationErrors(result);
        if (errorMap != null)
            return errorMap;

        try {
            user = userService.saveUser(user);
        } catch (Exception exception) {
            if (exception.getMessage().contains("UK"))
                throw new UsernameAlreadyExistsException(
                        String.format("Username %s already exists", user.getUsername()));
            else
                throw new UserFieldValidationException(exception.getMessage());
        }

        String jwt = tokenProvider.generateToken(userDetailsService.loadUserByUsername(user.getUsername()));
        return new ResponseEntity<>(new AuthenticationResponse(user, jwt), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthenticationRequest authenticationRequest,
                                              BindingResult result) {
        ResponseEntity<?> errorMap = validationErrorService.mapValidationErrors(result);
        if (errorMap != null)
            return errorMap;

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
        ));

        String jwt = tokenProvider.generateToken(
                userDetailsService.loadUserByUsername(authenticationRequest.getUsername()));
        return new ResponseEntity<>(new AuthenticationResponse(
                userService.getUserByUsername(authenticationRequest.getUsername()), jwt), HttpStatus.OK);
    }


    @PostMapping("/update")
    public ResponseEntity<?> updateprofile(@RequestBody String name){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User target = userService.getUserByUsername(userDetails.getUsername());
        target.setFirstName(name);

        userService.saveUser(target);
        return new ResponseEntity<>(userService.getUserByUsername(userDetails.getUsername()), HttpStatus.OK);
    }


    @GetMapping("/profile")
    public ResponseEntity<?> viewProfile() throws UserNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(userService.getUserByUsername(userDetails.getUsername()), HttpStatus.OK);
    }


}
