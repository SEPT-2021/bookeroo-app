package com.bookeroo.microservice.seller;

import com.bookeroo.microservice.seller.model.User;
import com.bookeroo.microservice.seller.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

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
    @Transactional
    void initialise() {
        if (postConstruct) {
            try {
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
                admin.setRole("ROLE_ADMIN");
                admin.setEnabled(true);
                userRepository.deleteUserByUsername(admin.getUsername());
                userRepository.save(admin);

                User user = new User();
                user.setUsername("user1@test.com");
                user.setFirstName("user1FirstName");
                user.setLastName("user1LastName");
                user.setPassword(passwordEncoder.encode("password"));
                user.setAddressLine1("123 Bookeroo St");
                user.setAddressLine2("Apartment 2");
                user.setCity("Melbourne");
                user.setState("VIC");
                user.setPostalCode("3001");
                user.setPhoneNumber("+(61) 413 170 399");
                user.setRole("ROLE_USER");
                user.setEnabled(true);
                userRepository.deleteUserByUsername(user.getUsername());
                userRepository.save(user);

                User seller = new User();
                seller.setUsername("seller1@test.com");
                seller.setFirstName("seller1FirstName");
                seller.setLastName("seller1LastName");
                seller.setPassword(passwordEncoder.encode("password"));
                seller.setAddressLine1("123 Bookeroo St");
                seller.setAddressLine2("Apartment 3");
                seller.setCity("Melbourne");
                seller.setState("VIC");
                seller.setPostalCode("3001");
                seller.setPhoneNumber("+(61) 413 170 399");
                seller.setRole("ROLE_SELLER");
                seller.setEnabled(true);
                userRepository.deleteUserByUsername(seller.getUsername());
                userRepository.save(seller);
            } catch (Exception ignore) {}
        }
    }

}
