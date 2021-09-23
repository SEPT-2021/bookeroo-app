package com.bookeroo.microservice.seller.service;

import com.bookeroo.microservice.seller.model.Listing;
import com.bookeroo.microservice.seller.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListingService {

    private final ListingRepository listingRepository;

    @Autowired
    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public Listing addListing(Listing listing) {
        return listingRepository.save(listing);
    }

    public Listing getListingById(long id) {
        return listingRepository.getById(id);
    }

}
