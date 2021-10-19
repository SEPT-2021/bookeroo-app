package com.bookeroo.microservice.book.web;

import com.bookeroo.microservice.book.exception.BookNotFoundException;
import com.bookeroo.microservice.book.model.Listing;
import com.bookeroo.microservice.book.model.User;
import com.bookeroo.microservice.book.repository.UserRepository;
import com.bookeroo.microservice.book.security.JWTTokenProvider;
import com.bookeroo.microservice.book.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bookeroo.microservice.book.security.SecurityConstant.AUTHORIZATION_HEADER;
import static com.bookeroo.microservice.book.security.SecurityConstant.JWT_SCHEME;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingService listingService;
    private final UserRepository userRepository;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public ListingController(ListingService listingService,
                             UserRepository userRepository,
                             JWTTokenProvider jwtTokenProvider) {
        this.listingService = listingService;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getListing(@PathVariable long id) {
        return new ResponseEntity<>(listingService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateListing(@PathVariable long id, @RequestBody Listing updatedListing) {
        return new ResponseEntity<>(listingService.updateListing(id, updatedListing), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteListing(@PathVariable long id) {
        listingService.removeListing(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?> getAllByBook(@PathVariable("id") long id) {
        return new ResponseEntity<>(listingService.getAllByBook(id), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllByUser(
        @RequestHeader(name = AUTHORIZATION_HEADER, required = false) String tokenHeader) {
        try {
            if (tokenHeader == null)
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

            String jwt = tokenHeader.substring(JWT_SCHEME.length());
            User user = userRepository.getByUsername(jwtTokenProvider.extractUsername(jwt));
            return new ResponseEntity<>(listingService.getAllByUser(user.getId()), HttpStatus.OK);
        } catch (BookNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
