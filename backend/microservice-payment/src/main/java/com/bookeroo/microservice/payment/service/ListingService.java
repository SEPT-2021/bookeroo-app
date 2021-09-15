package com.bookeroo.microservice.payment.service;

import com.bookeroo.microservice.payment.model.BookListing;
import com.bookeroo.microservice.payment.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ListingService {

    private final ListingRepository listingRepository;

    @Autowired
    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public BookListing saveListing(BookListing listing) {
        return listingRepository.save(listing);
    }

    @Transactional
    public BookListing getListingById(long id) {
        return listingRepository.getById(id);
    }

}
