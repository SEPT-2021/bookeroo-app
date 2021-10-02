package com.bookeroo.microservice.admin;

import com.bookeroo.microservice.admin.model.User;
import com.bookeroo.microservice.admin.repository.UserRepository;
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
            User admin = new User();
            admin.setUsername("admin@test.com");
            admin.setFirstName("adminFirstName");
            admin.setLastName("adminLastName");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setAddressLine1("123 Bookeroo St");
            admin.setAddressLine2("Apartment 1");
            admin.setCity("Melbourne");
            admin.setState("VIC");
            admin.setPostalCode("3001");
            admin.setPhoneNumber("+(61) 413 170 399");
            admin.setEnabled(true);
            admin.setRoles("ROLE_ADMIN");
            userRepository.save(admin);

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
        user.setAddressLine2("Apartment 1");
        user.setCity("Melbourne");
        user.setState("VIC");
        user.setPostalCode("3001");
        user.setPhoneNumber("+(61) 413 170 399");
        user.setEnabled(true);
        user.setEnabled(true);
        user.setRoles("ROLE_USER");
        return user;
    }

}
