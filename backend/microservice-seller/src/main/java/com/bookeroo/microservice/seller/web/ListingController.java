package com.bookeroo.microservice.seller.web;

import com.bookeroo.microservice.seller.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getListingFor(@PathVariable long id) {
        return new ResponseEntity<>(listingRepository.getByBook_Id(id), HttpStatus.OK);
    }

}
