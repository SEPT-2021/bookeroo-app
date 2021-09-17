package com.bookeroo.microservice.book.validator;

import com.bookeroo.microservice.book.model.BookFormData;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookFormDataValidator implements Validator {

    public static final int MINIMUM_ISBN_LENGTH = 13;
    public static final double MINIMUM_BOOK_PRICE = 0.01;
    public static final int MINIMUM_PAGE_COUNT = 1;

    @Override
    public boolean supports(Class<?> aClass) {
        return BookFormData.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        BookFormData book = (BookFormData) object;

        if (book.getIsbn() == null)
            errors.rejectValue("isbn", "Value", "ISBN cannot be null");

        if (book.getIsbn() != null && book.getIsbn().length() < MINIMUM_ISBN_LENGTH)
            errors.rejectValue("isbn", "Length",
                    String.format("ISBN must be at least %d digits", MINIMUM_ISBN_LENGTH));

        if (book.getIsbn() != null && !book.getIsbn().chars().allMatch(Character::isDigit))
            errors.rejectValue("isbn", "Value", "ISBN must not contain anything other than 0-9");

        try {
            if (Double.parseDouble(book.getPrice()) < MINIMUM_BOOK_PRICE)
                errors.rejectValue("price", "Value",
                        String.format("Price must be at least %.2f AUD", MINIMUM_BOOK_PRICE));
        } catch (NumberFormatException exception) {
            errors.rejectValue("price", "Value",
                    "Price must be a number");
        }

        try {
            if (Long.parseLong(book.getPageCount()) < MINIMUM_PAGE_COUNT)
                errors.rejectValue("pageCount", "Value",
                        String.format("Page count must be at least %d", MINIMUM_PAGE_COUNT));
        } catch (NumberFormatException exception) {
            errors.rejectValue("pageCount", "Value",
                    "Page count must be a whole number");
        }
    }

}
