package com.bookeroo.microservice.book;


import com.bookeroo.microservice.book.model.Book;
import com.bookeroo.microservice.book.model.Book.BookCategory;
import com.bookeroo.microservice.book.model.Book.BookCondition;
import com.bookeroo.microservice.book.model.Listing;
import com.bookeroo.microservice.book.model.Review;
import com.bookeroo.microservice.book.model.User;
import com.bookeroo.microservice.book.repository.BookRepository;
import com.bookeroo.microservice.book.repository.ListingRepository;
import com.bookeroo.microservice.book.repository.ReviewRepository;
import com.bookeroo.microservice.book.repository.UserRepository;
import com.bookeroo.microservice.book.service.S3Service;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Locale;
import java.util.Random;

@Component
public class DbInitialiser implements ApplicationListener<ApplicationReadyEvent> {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ListingRepository listingRepository;
    private final S3Service s3Service;
    private BCryptPasswordEncoder passwordEncoder;
    private final Faker faker = new Faker(new Locale("en-AU"));

    @Value("#{new Boolean('${db.initialise}')}")
    private boolean postConstruct;

    public DbInitialiser(BookRepository bookRepository,
                         UserRepository userRepository,
                         ReviewRepository reviewRepository,
                         ListingRepository listingRepository,
                         S3Service s3Service) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.listingRepository = listingRepository;
        this.s3Service = s3Service;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        initialise();
    }

    private void initialise() {
        if (postConstruct) {
            User user = new User();
            user.setUsername("listingUser@test.com");
            user.setFirstName("user1FirstName");
            user.setLastName("user1LastName");
            user.setPassword(passwordEncoder.encode("password"));
            user.setAddressLine1("123 Bookeroo St");
            user.setAddressLine2("Apartment 1");
            user.setCity("Melbourne");
            user.setState("VIC");
            user.setPostalCode("3001");
            user.setPhoneNumber("+(61) 411 170 399");
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            userRepository.deleteUserByUsername(user.getUsername());
            userRepository.save(user);

            bookRepository.deleteAll();
            Random random = new Random();
            for (int i = 0; i < 8; i++) {
                try {
                    Book book = bookRepository.save(getRandomBook());
                    for (int j = 0; j < (random.nextInt(5) + 1); j++) {
                        Review review = new Review();
                        User reviewer = getRandomReviewer();
                        review.setUser(reviewer);
                        review.setUserFullName(faker.funnyName().name());
                        review.setBook(book);
                        review.setText(faker.yoda().quote());
                        review.setRating(random.nextInt(5) + 1);
                        reviewRepository.save(review);
                    }
                    book.setRating(reviewRepository.getAverageByBook_Id(book.getId()));
                    book = bookRepository.save(book);

                    Listing listing = new Listing();
                    listing.setUser(user);
                    listing.setUserFullName(faker.funnyName().name());
                    listing.setBook(book);
                    listing.setPrice(BigDecimal.valueOf(random.nextFloat() * 100.0f).setScale(2, RoundingMode.HALF_EVEN).toString());
                    listing.setBookCondition(BookCondition.values()[random.nextInt(BookCondition.values().length)].name());
                    listing.setAvailable(true);
                    listingRepository.save(listing);
                } catch (Exception ignore) {}
            }
        }
    }

    private Book getRandomBook() {
        Random random = new Random();
        Book book = new Book();
        book.setTitle(faker.book().title());
        book.setAuthor(faker.book().author());
        book.setPageCount(String.valueOf(random.nextInt(1000)));
        book.setIsbn(String.valueOf((long) Math.floor(Math.random() * 9000_000_000_000L) + 1000_000_000_000L));
        book.setDescription(faker.yoda().quote());
        book.setBookCategory(BookCategory.values()[random.nextInt(BookCategory.values().length)].name());
        try {
            book.setCover(s3Service.uploadFile(new URL("https://picsum.photos/360/640"), book.getTitle()));
        } catch (IOException ignore) {}
        return book;
    }

    private User getRandomReviewer() {
        User reviewer = new User();
        reviewer.setUsername(faker.internet().emailAddress());
        reviewer.setFirstName(faker.name().firstName());
        reviewer.setLastName(faker.name().lastName());
        reviewer.setPassword(passwordEncoder.encode("password"));
        reviewer.setState("VIC");
        reviewer.setCity("Melbourne");
        reviewer.setAddressLine1("123 Bookeroo St");
        reviewer.setAddressLine2("Apartment 1");
        reviewer.setPostalCode("3001");
        reviewer.setPhoneNumber("+(61) 411 170 399");
        reviewer.setRole("ROLE_USER");
        reviewer.setEnabled(true);
        userRepository.deleteUserByUsername(reviewer.getUsername());
        reviewer = userRepository.save(reviewer);
        return reviewer;
    }

}
