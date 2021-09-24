package com.bookeroo.microservice.seller.validator;

import com.bookeroo.microservice.seller.model.ListingFormData;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Implementation of the {@link Validator} for the {@link ListingFormData} data model validation.
 */
@Component
public class ListingFormDataValidator implements Validator {

    public static final int MINIMUM_ISBN_LENGTH = 13;
    public static final double MINIMUM_BOOK_PRICE = 0.01;
    public static final double MAXIMUM_BOOK_PRICE = 10000.00;

    @Override
    public boolean supports(Class<?> aClass) {
        return ListingFormData.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        ListingFormData data = (ListingFormData) object;

        if (data.getIsbn() != null && data.getIsbn().length() < MINIMUM_ISBN_LENGTH)
            errors.rejectValue("isbn", "Length",
                    String.format("ISBN must be at least %d digits", MINIMUM_ISBN_LENGTH));

        if (data.getIsbn() != null && !data.getIsbn().chars().allMatch(Character::isDigit))
            errors.rejectValue("isbn", "Value", "ISBN must not contain anything other than 0-9");

        try {
            double price = Double.parseDouble(data.getPrice());
            if (price < MINIMUM_BOOK_PRICE)
                errors.rejectValue("price", "Value",
                        String.format("Price must be at least %.2f AUD", MINIMUM_BOOK_PRICE));

            if (price > MAXIMUM_BOOK_PRICE)
                errors.rejectValue("price", "Value",
                        String.format("Price must be less than %.2f AUD", MAXIMUM_BOOK_PRICE));
        } catch (NumberFormatException exception) {
            errors.rejectValue("price", "Value",
                    "Price must only be a number");
        }
    }

}
