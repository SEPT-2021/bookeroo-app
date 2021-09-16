package com.bookeroo.microservice.book.validator;

import com.bookeroo.microservice.book.model.Book;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {

    public static final int MINIMUM_ISBN_LENGTH = 13;
    public static final double MINIMUM_BOOK_PRICE = 0.01;
    public static final int MINIMUM_PAGE_COUNT = 1;

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Book book = (Book) object;
        System.out.println(book.getIsbn());
        if (book.getIsbn()==null|| book.getIsbn().length() < MINIMUM_ISBN_LENGTH)
            errors.rejectValue("isbn", "Length",
                    String.format("ISBN must be at least %d characters", MINIMUM_ISBN_LENGTH));

        if (book.getPrice() < MINIMUM_BOOK_PRICE)
            errors.rejectValue("price", "Value",
                    String.format("Price must be at least %.2f AUD", MINIMUM_BOOK_PRICE));

        if (book.getPageCount() < MINIMUM_PAGE_COUNT)
            errors.rejectValue("pageCount", "Value",
                    String.format("Page count must be at least %d", MINIMUM_PAGE_COUNT));
    }

}
