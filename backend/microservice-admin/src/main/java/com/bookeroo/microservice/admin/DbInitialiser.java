package com.bookeroo.microservice.admin;

import com.bookeroo.microservice.admin.model.User;
import com.bookeroo.microservice.admin.repository.ListingRepository;
import com.bookeroo.microservice.admin.repository.ReviewRepository;
import com.bookeroo.microservice.admin.repository.TransactionRepository;
import com.bookeroo.microservice.admin.repository.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Locale;

@Component
public class DbInitialiser {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ListingRepository listingRepository;
    private final TransactionRepository transactionRepository;
    private final Faker faker = new Faker(new Locale("en-AU"));
    private BCryptPasswordEncoder passwordEncoder;

    @Value("#{new Boolean('${db.initialise}')}")
    private boolean postConstruct;

    @Autowired
    public DbInitialiser(UserRepository userRepository,
                         ReviewRepository reviewRepository,
                         ListingRepository listingRepository,
                         TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.listingRepository = listingRepository;
        this.transactionRepository = transactionRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    @Transactional
    void initialise() {
        if (postConstruct) {
            User admin = new User();
            admin.setUsername("admin@test.com");
            admin.setFirstName("adminFirstName");
            admin.setLastName("adminLastName");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setAddressLine1("123 Admin St");
            admin.setAddressLine2("Apartment 1");
            admin.setCity("Melbourne");
            admin.setState("VIC");
            admin.setPostalCode("3001");
            admin.setPhoneNumber("+(61) 413 170 399");
            admin.setEnabled(true);
            admin.setRole("ROLE_ADMIN");
            transactionRepository.deleteByBuyer_Username(admin.getUsername());
            listingRepository.deleteAllByUser_Username(admin.getUsername());
            reviewRepository.deleteAllByUser_Username(admin.getUsername());
            userRepository.deleteUserByUsername(admin.getUsername());
            userRepository.save(admin);

            transactionRepository.deleteAllByBuyer_UsernameContaining("@random.com");
            listingRepository.deleteAllByUser_UsernameContaining("@random.com");
            reviewRepository.deleteAllByUser_UsernameContaining("@random.com");
            userRepository.deleteAllByUsernameContaining("@random.com");
            try {
                for (int i = 0; i < 4; i++)
                    userRepository.save(getRandomUser());
            } catch (Exception ignore) {
            }
        }
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
        user.setPhoneNumber(faker.phoneNumber().phoneNumber());
        user.setEnabled(true);
        user.setRole("ROLE_USER");
        return user;
    }

}
