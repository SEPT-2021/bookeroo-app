package com.bookeroo.microservice.login.web;

import com.bookeroo.microservice.login.exception.UserNotFoundException;
import com.bookeroo.microservice.login.model.User;
import com.bookeroo.microservice.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        try {
            return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/enable-users/{id}")
    public ResponseEntity<?> enableUser(@PathVariable long id) {
        try {
            User user = userService.getUserById(id);
            user.setEnabled(true);
            userService.updateUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/disable-users/{id}")
    public ResponseEntity<?> disableUser(@PathVariable long id) {
        try {
            User user = userService.getUserById(id);
            user.setEnabled(false);
            userService.updateUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete-users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
