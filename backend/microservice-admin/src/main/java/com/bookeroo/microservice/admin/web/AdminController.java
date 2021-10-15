package com.bookeroo.microservice.admin.web;

import com.bookeroo.microservice.admin.exception.UserNotFoundException;
import com.bookeroo.microservice.admin.model.User;
import com.bookeroo.microservice.admin.service.SellerDetailsService;
import com.bookeroo.microservice.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller to hold the microservice's admin endpoint implementations.
 */
@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final UserService userService;
    private final SellerDetailsService sellerDetailsService;

    @Autowired
    public AdminController(UserService userService, SellerDetailsService sellerDetailsService) {
        this.userService = userService;
        this.sellerDetailsService = sellerDetailsService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> viewAdminProfile() throws UserNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(userService.getUserByUsername(userDetails.getUsername()), HttpStatus.OK);
    }

    @GetMapping("/inspect-users")
    public ResponseEntity<?> inspectAllUsers() {
        List<User> users = userService.getAllNonAdminUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/inspect-users/{id}")
    public ResponseEntity<?> inspectUser(@PathVariable long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("/toggle-ban/{id}")
    public ResponseEntity<?> banUser(@PathVariable long id) {
        return new ResponseEntity<>(userService.toggleUserBan(id), HttpStatus.OK);
    }

    @GetMapping("/inspect-sellers")
    public ResponseEntity<?> inspectAllSellers() {
        return new ResponseEntity<>(sellerDetailsService.getAllPendingSellers(), HttpStatus.OK);
    }

    @PostMapping("/approve-seller/{id}")
    public ResponseEntity<?> approveSeller(@PathVariable long id) {
        return new ResponseEntity<>(userService.approveSeller(id), HttpStatus.OK);
    }

    @PostMapping("/reject-seller/{id}")
    public ResponseEntity<?> rejectSeller(@PathVariable long id) {
        return new ResponseEntity<>(userService.rejectSeller(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete-users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
