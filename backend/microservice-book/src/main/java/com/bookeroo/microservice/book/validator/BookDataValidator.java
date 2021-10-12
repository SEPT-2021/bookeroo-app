package com.bookeroo.microservice.book.validator;

import com.bookeroo.microservice.book.model.Book;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Implementation of the {@link Validator} for the {@link Book} data model validation.
 */
@Component
public class BookDataValidator implements Validator {

    public static final int ISBN_LENGTH = 13;
    public static final int MINIMUM_PAGE_COUNT = 1;
    public static final int MAXIMUM_PAGE_COUNT = 1_000_000_000;

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Book book = (Book) object;

        try {
            long pageCount = Long.parseLong(book.getPageCount());
            if (pageCount < MINIMUM_PAGE_COUNT)
                errors.rejectValue("pageCount", "Value",
                        String.format("Page count must be at least %d", MINIMUM_PAGE_COUNT));

            if (pageCount > MAXIMUM_PAGE_COUNT)
                errors.rejectValue("pageCount", "Value",
                        String.format("Page count cannot be larger than %d", MAXIMUM_PAGE_COUNT));
        } catch (NumberFormatException exception) {
            errors.rejectValue("pageCount", "Value",
                    "Page count must be a number");
        }

        if (book.getIsbn().length() != ISBN_LENGTH)
            errors.rejectValue("isbn", "Length",
                    String.format("ISBN must be exactly %d digits", ISBN_LENGTH));

        if (!book.getIsbn().chars().allMatch(Character::isDigit))
            errors.rejectValue("isbn", "Value", "ISBN must not contain anything other than 0-9");
    }
}
