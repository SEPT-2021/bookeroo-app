package com.bookeroo.microservice.payment.service;

import com.bookeroo.microservice.payment.model.*;
import com.bookeroo.microservice.payment.model.Book.BookCategory;
import com.bookeroo.microservice.payment.model.Book.BookCondition;
import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PaymentServiceTests {

    @Autowired
    private PayPalService payPalService;

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

    CartCheckout setupCartCheckout() {
        Book book = new Book();
        book.setTitle("testTitle");
        book.setAuthor("testAuthor");
        book.setPageCount("100");
        book.setIsbn("1234567891011");
        book.setDescription("testDescription");
        book.setPrice("10.0");
        book.setBookCondition(BookCondition.FAIR.name());
        book.setBookCategory(BookCategory.LITERARY_FICTION.name());
        book.setCover("https://picsum.photos/360/640");

        List<OrderItem> items = new ArrayList<>();
        OrderItem item = new OrderItem();

        item.setBook(book);
        item.setQuantity(1);
        items.add(item);

        book.setTitle("testTitle2");
        item.setBook(book);
        item.setQuantity(2);
        items.add(item);

        ShippingAddress address = new ShippingAddress();
        address.setAddressLine1("123 Test St");
        address.setAddressLine2("Unit test");
        address.setCity("Melbourne");
        address.setState("VIC");
        address.setPostalCode("3001");

        CartCheckout cartCheckout = new CartCheckout();
        cartCheckout.setOrderItems(items);
        cartCheckout.setShippingAddress(address);
        return cartCheckout;
    }

    @Test
    void givenUserAndCartCheckout_whenOrderIsCreated_returnStatusCreated() throws IOException {
        User user = setupUser();
        CartCheckout cartCheckout = setupCartCheckout();
        HttpResponse<Order> order = payPalService.createOrder(user, cartCheckout);

        assertEquals("CREATED", order.result().status());
    }

}
