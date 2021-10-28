package com.bookeroo.microservice.book.service;

import com.bookeroo.microservice.book.exception.BookNotFoundException;
import com.bookeroo.microservice.book.exception.ListingNotFoundException;
import com.bookeroo.microservice.book.exception.UserNotFoundException;
import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.model.Listing;
import com.bookeroo.microservice.book.model.ListingFormData;
import com.bookeroo.microservice.book.model.User;
import com.bookeroo.microservice.book.repository.BookRepository;
import com.bookeroo.microservice.book.repository.ListingRepository;
import com.bookeroo.microservice.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public ListingService(ListingRepository listingRepository,
                          BookRepository bookRepository,
                          UserRepository userRepository) {
        this.listingRepository = listingRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Listing getById(long id) {
        Optional<Listing> listing = listingRepository.findById(id);
        listing.orElseThrow(() -> new ListingNotFoundException(String.format("Listing by id %s not found", id)));
        return listing.get();
    }

    public List<Listing> getAllByBook(long id) {
        Optional<Book> book = bookRepository.findById(id);
        book.orElseThrow(() -> new BookNotFoundException(String.format("Book by id %s not found", id)));
        return listingRepository.findByBook_Id(id);
    }

    public List<Listing> getAllByUser(long id) {
        Optional<User> user = userRepository.findById(id);
        user.orElseThrow(() -> new UserNotFoundException(String.format("User by id %s not found", id)));
        return listingRepository.findByUser_Id(id);
    }

    public Listing saveListing(ListingFormData formData, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UserNotFoundException(String.format("User by username %s not found", username)));
        Optional<Book> book = bookRepository.findById(Long.parseLong(formData.getBookId()));
        book.orElseThrow(() -> new BookNotFoundException(String.format("Book by id %s not found", formData.getBookId())));

        Listing listing = new Listing();
        listing.setUser(user.get());
        listing.setUserFullName(user.get().getFirstName() + " " + user.get().getLastName());
        listing.setBook(book.get());
        listing.setPrice(formData.getPrice());
        listing.setBookCondition(formData.getCondition().name());
        listing.setAvailable(true);
        return listingRepository.save(listing);
    }

    public Listing updateListing(long id, Listing updatedListing) {
        Optional<Listing> existing = listingRepository.findById(id);
        existing.orElseThrow(() -> new ListingNotFoundException(String.format("Listing by id %d not found", id)));
        Listing listing = existing.get();

        for (Field field : Listing.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                field.set(listing, (!field.getType().isPrimitive() && field.get(updatedListing) != null)
                        ? field.get(updatedListing)
                        : field.get(listing));
            } catch (IllegalAccessException ignored) {
            }
        }

        return listingRepository.save(listing);
    }

    @Transactional
    public void removeListing(long id) {
        Optional<Listing> existing = listingRepository.findById(id);
        existing.orElseThrow(() -> new ListingNotFoundException(String.format("Listing by id %d not found", id)));
        listingRepository.deleteById(id);
    }

}
