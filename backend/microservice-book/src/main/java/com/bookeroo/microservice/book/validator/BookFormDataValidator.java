package com.bookeroo.microservice.book.validator;

import com.bookeroo.microservice.book.model.BookFormData;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Implementation of the {@link Validator} for the {@link BookFormData} data model validation.
 */
@Component
public class BookFormDataValidator implements Validator {

    public static final int MINIMUM_ISBN_LENGTH = 13;
    public static final int MINIMUM_PAGE_COUNT = 1;
    public static final int MAXIMUM_PAGE_COUNT = 1_000_000_000;

    @Override
    public boolean supports(Class<?> aClass) {
        return BookFormData.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        BookFormData data = (BookFormData) object;

        if (data.getIsbn().length() < MINIMUM_ISBN_LENGTH)
            errors.rejectValue("isbn", "Length",
                    String.format("ISBN must be at least %d digits", MINIMUM_ISBN_LENGTH));

        if (!data.getIsbn().chars().allMatch(Character::isDigit))
            errors.rejectValue("isbn", "Value", "ISBN must not contain anything other than 0-9");

        if (data.getPageCount() < MINIMUM_PAGE_COUNT)
            errors.rejectValue("pageCount", "Value",
                    String.format("Page count must be at least %d", MINIMUM_PAGE_COUNT));

        if (data.getPageCount() > MAXIMUM_PAGE_COUNT)
            errors.rejectValue("pageCount", "Value",
                    String.format("Page count cannot be larger than %d", MAXIMUM_PAGE_COUNT));
    }

}
