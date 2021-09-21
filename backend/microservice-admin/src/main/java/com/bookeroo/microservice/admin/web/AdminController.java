package com.bookeroo.microservice.admin.web;

import com.bookeroo.microservice.admin.exception.UserNotFoundException;
import com.bookeroo.microservice.admin.model.User;
import com.bookeroo.microservice.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;

/**
 * REST Controller to hold the microservice's admin endpoint implementations.
 */
@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
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

    @PostMapping("/ban-users/{id}")
    public ResponseEntity<?> banUser(@PathVariable long id) {
        return new ResponseEntity<>(setUserBanned(id, false), HttpStatus.OK);
    }

    @PostMapping("/unban-users/{id}")
    public ResponseEntity<?> unbanUser(@PathVariable long id) {
        return new ResponseEntity<>(setUserBanned(id, true), HttpStatus.OK);
    }

    @PostMapping("/approve-sellers/{id}")
    public ResponseEntity<?> approveSeller(@PathVariable long id) {
        return new ResponseEntity<>(setSellerApproved(id, true), HttpStatus.OK);
    }

    @PostMapping("/reject-sellers/{id}")
    public ResponseEntity<?> rejectSeller(@PathVariable long id) {
        return new ResponseEntity<>(setSellerApproved(id, false), HttpStatus.OK);
    }

    @PutMapping("/update-users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody User updatedUser) {
        User user = userService.getUserById(id);
        for (Field field : User.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                field.set(user, (!field.getType().isPrimitive() && field.get(updatedUser) != null)
                        ? field.get(updatedUser)
                        : field.get(user));
            } catch (IllegalAccessException ignored) {}
        }

        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    @DeleteMapping("/delete-users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private User setUserBanned(long id, boolean isBanned) {
        User user = userService.getUserById(id);
        user.setEnabled(isBanned);
        return userService.updateUser(user);
    }

    private User setSellerApproved(long id, boolean isApproved) {
        User user = userService.getSellerById(id);
        user.setEnabled(isApproved);
        return userService.updateUser(user);
    }

}
