package com.bookeroo.microservice.seller;

import com.bookeroo.microservice.seller.model.Book;
import com.bookeroo.microservice.seller.model.Book.BookCategory;
import com.bookeroo.microservice.seller.model.Book.BookCondition;
import com.bookeroo.microservice.seller.model.User;
import com.bookeroo.microservice.seller.repository.BookRepository;
import com.bookeroo.microservice.seller.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

@Component
public class DbInitialiser {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Value("#{new Boolean('${db.initialise}')}")
    private boolean postConstruct;

    @Autowired
    public DbInitialiser(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
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
            admin.setRoles("ROLE_ADMIN");
            admin.setEnabled(true);
            userRepository.save(admin);

            User user = new User();
            user.setUsername("user@test.com");
            user.setFirstName("userFirstName");
            user.setLastName("userLastName");
            user.setPassword(passwordEncoder.encode("password"));
            user.setAddressLine1("123 Bookeroo St");
            user.setAddressLine2("Apartment 2");
            user.setCity("Melbourne");
            user.setState("VIC");
            user.setPostalCode("3001");
            user.setPhoneNumber("+(61) 413 170 399");
            user.setRoles("ROLE_USER");
            user.setEnabled(true);
            userRepository.save(user);

            User seller = new User();
            seller.setUsername("seller@test.com");
            seller.setFirstName("sellerFirstName");
            seller.setLastName("sellerLastName");
            seller.setPassword(passwordEncoder.encode("password"));
            seller.setAddressLine1("123 Bookeroo St");
            seller.setAddressLine2("Apartment 3");
            seller.setCity("Melbourne");
            seller.setState("VIC");
            seller.setPostalCode("3001");
            seller.setPhoneNumber("+(61) 413 170 399");
            seller.setRoles("ROLE_USER,ROLE_SELLER");
            seller.setEnabled(true);
            userRepository.save(seller);

//            for (int i = 0; i < 6; i++)
//                userRepository.save(getRandomUser());

            for (int i = 0; i < 6; i++)
                bookRepository.save(getRandomBook());
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
        user.setRoles("ROLE_USER");
        user.setEnabled(true);
        return user;
    }

    private Book getRandomBook() {
        Book book = new Book();
        Random random = new Random();
        book.setTitle("randomTitle");
        book.setAuthor("randomAuthor");
        book.setPageCount(String.valueOf(new Random().nextInt(100)));
        book.setIsbn(String.valueOf((long) Math.floor(Math.random() * 9_000_000_000_000L) + 1_000_000_000_000L));
        book.setDescription("testDescription");
        book.setPrice(String.valueOf(random.nextFloat() % 10.0f));
        book.setBookCondition(BookCondition.values()[random.nextInt(BookCondition.values().length)].name());
        book.setBookCategory(BookCategory.values()[random.nextInt(BookCategory.values().length)].name());
        book.setCover("https://picsum.photos/200");
        return book;
    }

}
