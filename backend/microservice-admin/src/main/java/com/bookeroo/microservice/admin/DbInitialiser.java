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
            admin.setEnabled(true);
            admin.setRoles("ROLE_ADMIN");
            userRepository.save(admin);

            User user = new User();
            user.setUsername("user@test.com");
            user.setFirstName("userFirstName");
            user.setLastName("userLastName");
            user.setPassword(passwordEncoder.encode("password"));
            user.setEnabled(true);
            user.setRoles("ROLE_USER");
            userRepository.save(user);

            User seller = new User();
            seller.setUsername("seller@test.com");
            seller.setFirstName("sellerFirstName");
            seller.setLastName("sellerLastName");
            seller.setPassword(passwordEncoder.encode("password"));
            seller.setEnabled(true);
            seller.setRoles("ROLE_USER,ROLE_SELLER");
            userRepository.save(seller);

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
