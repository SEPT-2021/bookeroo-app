package com.bookeroo.microservice.book.web;

import com.bookeroo.microservice.book.exception.BookNotFoundException;
import com.bookeroo.microservice.book.model.Listing;
import com.bookeroo.microservice.book.service.ListingService;
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

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAllByUser(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(listingService.getAllByUser(id), HttpStatus.OK);
        } catch (BookNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
