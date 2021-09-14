package com.bookeroo.microservice.payment.web;

import com.bookeroo.microservice.payment.model.BookListing;
import com.bookeroo.microservice.payment.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingRepository listingRepository;

    @Autowired
    public ListingController(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    @PostMapping("/new")
    public ResponseEntity<?> createNewListing(@RequestBody BookListing bookListing) {
        return new ResponseEntity<>(listingRepository.save(bookListing), HttpStatus.OK);
    }

}
