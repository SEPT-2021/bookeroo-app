package com.bookeroo.microservice.admin;

import com.bookeroo.microservice.admin.model.User;
import com.bookeroo.microservice.admin.repository.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Component
public class DbInitialiser {

    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Value("#{new Boolean('${db.initialise}')}")
    private boolean postConstruct;

    @Autowired
    public DbInitialiser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void initialise() {
        if (postConstruct) {
            try {
                Faker faker = new Faker(new Locale("en-AU"));
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
                userRepository.deleteUserByUsername(admin.getUsername());
                userRepository.save(admin);

                userRepository.deleteAllByUsernameContaining("random");
                for (int i = 0; i < 4; i++) {
                    User user = getRandomUser();
                    userRepository.save(user);
                }
            } catch (Exception ignore) {}
        }
    }

    private User getRandomUser() {
        Faker faker = new Faker(new Locale("en-AU"));
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
