package com.bookeroo.microservice.seller.web;

import com.bookeroo.microservice.seller.model.Listing;
import com.bookeroo.microservice.seller.security.JWTTokenProvider;
import com.bookeroo.microservice.seller.service.ListingService;
import com.bookeroo.microservice.seller.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bookeroo.microservice.seller.security.SecurityConstant.AUTHORIZATION_HEADER;
import static com.bookeroo.microservice.seller.security.SecurityConstant.JWT_SCHEME;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    private final UserService userService;
    private final ListingService listingService;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public SellerController(UserService userService, ListingService listingService, JWTTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.listingService = listingService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/add-listing")
    public ResponseEntity<?> addListing(@RequestHeader(AUTHORIZATION_HEADER) String tokenHeader,
                                        @RequestBody Listing listing) {
        try {
            String jwt = tokenHeader.substring(JWT_SCHEME.length());
            listing.setSeller(userService.getUserByUsername(jwtTokenProvider.extractUsername(jwt)));
            return new ResponseEntity<>(listingService.addListing(listing), HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-listing/{id}")
    public ResponseEntity<?> getListing(@PathVariable long id) {
        return new ResponseEntity<>(listingService.getListingById(id), HttpStatus.OK);
    }

}
