package com.bookeroo.microservice.payment.service;

import com.bookeroo.microservice.payment.model.*;
import com.bookeroo.microservice.payment.model.Book.BookCondition;
import com.bookeroo.microservice.payment.repository.BookRepository;
import com.bookeroo.microservice.payment.repository.ListingRepository;
import com.bookeroo.microservice.payment.repository.UserRepository;
import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PaymentServiceTests {

    @Autowired
    private PayPalService payPalService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ListingRepository listingRepository;

    User setupUser() {
        User user = new User();
        user.setUsername(RandomString.make(8) + "@test.com");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setPassword("testPassword");
        user.setAddressLine1("123 Bookeroo St");
        user.setAddressLine2("Apartment 1");
        user.setCity("Melbourne");
        user.setState("VIC");
        user.setPostalCode("3001");
        user.setPhoneNumber("+(61) 413 170 399");
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        return user;
    }

    Book setupBook() {
        Random random = new Random();
        Book book = new Book();
        book.setTitle("testTitle");
        book.setAuthor("testAuthor");
        book.setPageCount("100");
        book.setIsbn(String.valueOf((long) Math.floor(Math.random() * 9_000_000_000_000L) + 1_000_000_000_000L));
        book.setDescription("testDescription");
        book.setBookCategory(Book.BookCategory.values()[random.nextInt(Book.BookCategory.values().length)].name());
        book.setCover("https://picsum.photos/360/640");
        return book;
    }

    CartCheckout setupCartCheckout() {
        User user = setupUser();
        user = userRepository.save(user);
        Book book = setupBook();
        book = bookRepository.save(book);

        Listing listing = new Listing();
        Random random = new Random();
        listing.setUser(user);
        listing.setBook(book);
        listing.setPrice(BigDecimal.valueOf(random.nextFloat() * 100.0f).setScale(2, RoundingMode.HALF_EVEN).toString());
        listing.setBookCondition(BookCondition.values()[random.nextInt(BookCondition.values().length)].name());
        listing.setAvailable(true);
        listing = listingRepository.save(listing);

        ShippingAddress address = new ShippingAddress();
        address.setAddressLine1("123 Test St");
        address.setAddressLine2("Unit test");
        address.setCity("Melbourne");
        address.setState("VIC");
        address.setPostalCode("3001");

        CartCheckout cartCheckout = new CartCheckout();
        cartCheckout.setOrderItems(Collections.singletonList(listing));
        cartCheckout.setShippingAddress(address);
        return cartCheckout;
    }

    @Test
    void givenUserAndCartCheckout_whenOrderIsCreated_returnStatusCreated() throws IOException {
        User buyer = setupUser();
        CartCheckout cartCheckout = setupCartCheckout();
        HttpResponse<Order> order = payPalService.createOrder(cartCheckout, buyer);

        assertEquals(HttpStatus.CREATED.value(), order.statusCode());
    }

}
