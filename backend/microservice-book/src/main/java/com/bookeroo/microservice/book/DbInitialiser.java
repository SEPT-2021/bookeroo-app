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
    private final Faker faker = new Faker(new Locale("en-AU"));
    private BCryptPasswordEncoder passwordEncoder;

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

    void initialise() {
        if (postConstruct) {
            try {
                reviewRepository.deleteAll();
                listingRepository.deleteAll();
                bookRepository.deleteAll();
                userRepository.deleteAllByUsernameContaining("@random.com");
            } catch (Exception ignore) {
            }

            Random random = new Random();
            for (int i = 0; i < 8; i++) {
                Book book;
                try {
                    book = bookRepository.save(getRandomBook());
                } catch (Exception exception) {
                    continue;
                }

                for (int j = 0; j < (random.nextInt(5) + 1); j++) {
                    Review review = new Review();
                    User reviewer = userRepository.save(getRandomUser());
                    review.setUser(reviewer);
                    review.setUserFullName(faker.funnyName().name());
                    review.setBook(book);
                    String text = faker.yoda().quote();
                    review.setText(text.length() < 280 ? text : text.substring(0, 280));
                    review.setRating(random.nextInt(5) + 1);
                    reviewRepository.save(review);
                }
                book.setRating(reviewRepository.getAverageByBook_Id(book.getId()));
                book = bookRepository.save(book);

                Listing listing = new Listing();
                User seller = userRepository.save(getRandomUser());
                listing.setUser(seller);
                listing.setUserFullName(faker.funnyName().name());
                listing.setBook(book);
                listing.setPrice(BigDecimal.valueOf(random.nextFloat() * 100.0f).setScale(2, RoundingMode.HALF_EVEN).toString());
                listing.setBookCondition(BookCondition.values()[random.nextInt(BookCondition.values().length)].name());
                listing.setAvailable(true);
                listingRepository.save(listing);
            }
        }
    }

    private Book getRandomBook() throws IOException {
        Random random = new Random();
        Book book = new Book();
        book.setTitle(faker.book().title());
        book.setAuthor(faker.book().author());
        book.setPageCount(String.valueOf(random.nextInt(1000)));
        book.setIsbn(String.valueOf((long) Math.floor(Math.random() * 9000_000_000_000L) + 1000_000_000_000L));
        book.setDescription(faker.yoda().quote());
        book.setBookCategory(BookCategory.values()[random.nextInt(BookCategory.values().length)].name());
        book.setCover(s3Service.uploadFile(new URL("https://picsum.photos/360/640"), book.getTitle()));
        return book;
    }

    private User getRandomUser() {
        User user = new User();
        user.setUsername(faker.name().username() + "@random.com");
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setPassword(passwordEncoder.encode("password"));
        user.setAddressLine1(faker.address().streetAddress());
        user.setAddressLine2(faker.address().secondaryAddress());
        user.setCity(faker.address().city());
        user.setState(faker.address().stateAbbr());
        user.setPostalCode(faker.address().zipCode());
        user.setPhoneNumber("+(61) 413 170 399");
        user.setEnabled(true);
        user.setRole("ROLE_USER");
        return user;
    }

}
