package com.bookeroo.microservice.admin.web;

import com.bookeroo.microservice.admin.exception.UserNotFoundException;
import com.bookeroo.microservice.admin.model.Book;
import com.bookeroo.microservice.admin.model.User;
import com.bookeroo.microservice.admin.security.JWTTokenProvider;
import com.bookeroo.microservice.admin.service.BookService;
import com.bookeroo.microservice.admin.service.SellerDetailsService;
import com.bookeroo.microservice.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bookeroo.microservice.admin.security.SecurityConstant.AUTHORIZATION_HEADER;
import static com.bookeroo.microservice.admin.security.SecurityConstant.JWT_SCHEME;

/**
 * REST Controller to hold the microservice's admin endpoint implementations.
 */
@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final UserService userService;
    private final SellerDetailsService sellerDetailsService;
    private final JWTTokenProvider jwtTokenProvider;
    private final BookService bookService;

    @Autowired
    public AdminController(UserService userService,
                           SellerDetailsService sellerDetailsService,
                           BookService bookService,
                           JWTTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.sellerDetailsService = sellerDetailsService;
        this.bookService = bookService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> viewAdminProfile(
            @RequestHeader(name = AUTHORIZATION_HEADER, required = false) String tokenHeader)
            throws UserNotFoundException {
        if (tokenHeader == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        String jwt = tokenHeader.substring(JWT_SCHEME.length());
        return new ResponseEntity<>(userService.getUserByUsername((jwtTokenProvider.extractUsername(jwt))),
                HttpStatus.OK);
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

    @DeleteMapping("/delete-users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-books/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
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
        userService.rejectSeller(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
