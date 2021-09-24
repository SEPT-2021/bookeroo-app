package com.bookeroo.microservice.seller;

import com.bookeroo.microservice.seller.model.Book;
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

            Book book = new Book();
            book.setTitle("randomTitle");
            book.setAuthor("randomAuthor");
            book.setPageCount(100);
            book.setIsbn("1234567891011");
            book.setDescription("randomDescription");
            book.setCover("https://picsum.photos/200");
            bookRepository.save(book);

//            for (int i = 0; i < 6; i++)
//                userRepository.save(getRandomUser());
//            for (int i = 0; i < 6; i++)
//                bookRepository.save(getRandomBook());
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

    private Book getRandomBook() {
        Book book = new Book();
        book.setTitle("randomTitle");
        book.setAuthor("randomAuthor");
        book.setPageCount(new Random().nextInt(1000));
        book.setIsbn(String.valueOf((long) Math.floor(Math.random() * 9000_000_000_000L) + 1000_000_000_000L));
        book.setDescription("randomDescription");
        book.setCover("https://picsum.photos/200");
        return book;
    }

}
