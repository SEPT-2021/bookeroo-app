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
            user1.setEnabled(true);
            user1.setRoles("ROLE_USER");
            userRepository.save(user1);

            User user2 = new User();
            user2.setUsername("user2@test.com");
            user2.setFirstName("user2FirstName");
            user2.setLastName("user2LastName");
            user2.setPassword(passwordEncoder.encode("password"));
            user2.setEnabled(true);
            user2.setRoles("ROLE_USER");
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
        user.setEnabled(true);
        user.setRoles("ROLE_USER");
        return user;
    }

}
