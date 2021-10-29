package com.bookeroo.microservice.admin.service;

import com.bookeroo.microservice.admin.exception.BookNotFoundException;
import com.bookeroo.microservice.admin.model.Listing;
import com.bookeroo.microservice.admin.repository.BookRepository;
import com.bookeroo.microservice.admin.repository.ListingRepository;
import com.bookeroo.microservice.admin.repository.ReviewRepository;
import com.bookeroo.microservice.admin.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final ListingRepository listingRepository;
    private final TransactionRepository transactionRepository;

    public BookService(BookRepository bookRepository,
                       ReviewRepository reviewRepository,
                       ListingRepository listingRepository,
                       TransactionRepository transactionRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.listingRepository = listingRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void deleteBook(long id) {
        if (!bookRepository.existsById(id))
            throw new BookNotFoundException(String.format("Book by id %s not found", id));

        List<Listing> listings = listingRepository.findAllByBook_Id(id);
        listings.forEach(listing -> transactionRepository.deleteAllByListing_Id(listing.getId()));
        listingRepository.deleteAllByBook_Id(id);
        reviewRepository.deleteAllByBook_Id(id);
        bookRepository.deleteById(id);
    }

}
