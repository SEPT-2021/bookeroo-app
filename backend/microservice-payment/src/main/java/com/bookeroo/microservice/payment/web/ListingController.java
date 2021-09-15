package com.bookeroo.microservice.payment.web;

import com.bookeroo.microservice.payment.model.BookListing;
import com.bookeroo.microservice.payment.repository.ListingRepository;
import com.bookeroo.microservice.payment.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingService listingService;

    @Autowired
    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @PostMapping("/new")
    public ResponseEntity<?> createNewListing(@RequestBody BookListing bookListing) {
        return new ResponseEntity<>(listingService.saveListing(bookListing), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getListing(@PathVariable long id) {
        BookListing listing = listingService.getListingById(id);
        System.out.println(listing.getBook().getId());
        System.out.println(listing.getSeller().getId());

        return new ResponseEntity<>(listing, HttpStatus.OK);
    }

}
