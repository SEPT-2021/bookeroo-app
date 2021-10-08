package com.bookeroo.microservice.login;

import com.bookeroo.microservice.login.model.User;
import com.bookeroo.microservice.login.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
            User user1 = new User();
            user1.setUsername("user1@test.com");
            user1.setFirstName("user1FirstName");
            user1.setLastName("user1LastName");
            user1.setPassword(passwordEncoder.encode("password"));
            user1.setAddressLine1("123 Bookeroo St");
            user1.setAddressLine2("Apartment 1");
            user1.setCity("Melbourne");
            user1.setState("VIC");
            user1.setPostalCode("3001");
            user1.setPhoneNumber("+(61) 413 170 399");
            user1.setRole("ROLE_USER");
            user1.setEnabled(true);
            userRepository.save(user1);

            User user2 = new User();
            user2.setUsername("user2@test.com");
            user2.setFirstName("user2FirstName");
            user2.setLastName("user2LastName");
            user2.setPassword(passwordEncoder.encode("password"));
            user2.setAddressLine1("123 Bookeroo St");
            user2.setAddressLine2("Apartment 2");
            user2.setCity("Melbourne");
            user2.setState("VIC");
            user2.setPostalCode("3001");
            user2.setPhoneNumber("+(61) 413 170 399");
            user2.setRole("ROLE_USER");
            user2.setEnabled(true);
            userRepository.save(user2);

//            for (int i = 0; i < 6; i++)
//                userRepository.save(getRandomUser());
        }
    }

    private User getRandomUser() {
        User user = new User();
        user.setUsername(RandomString.make(8) + "@test.com");
        user.setFirstName("randomFirstName");
        user.setLastName("randomLastName");
        user.setPassword(passwordEncoder.encode("password"));
        user.setAddressLine1("123 Bookeroo St");
        user.setAddressLine2("Apartment 3");
        user.setCity("Melbourne");
        user.setState("VIC");
        user.setPostalCode("3001");
        user.setPhoneNumber("+(61) 413 170 399");
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        return user;
    }

}
