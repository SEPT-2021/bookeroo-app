package com.bookeroo.microservice.book.validator;

import com.bookeroo.microservice.book.model.ListingFormData;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.bookeroo.microservice.book.validator.BookFormDataValidator.MAXIMUM_BOOK_PRICE;
import static com.bookeroo.microservice.book.validator.BookFormDataValidator.MINIMUM_BOOK_PRICE;

@Component
public class ListingFormDataValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ListingFormData.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        ListingFormData data = (ListingFormData) object;
        validatePrice(data.getPrice(), errors);
    }

    static void validatePrice(String priceString, Errors errors) {
        try {
            double price = Double.parseDouble(priceString);
            if (price < MINIMUM_BOOK_PRICE)
                errors.rejectValue("price", "Value",
                        String.format("Price must be at least %.2f AUD", MINIMUM_BOOK_PRICE));

            if (price > MAXIMUM_BOOK_PRICE)
                errors.rejectValue("price", "Value",
                        String.format("Price must be less than %.2f AUD", MAXIMUM_BOOK_PRICE));
        } catch (NumberFormatException exception) {
            errors.rejectValue("price", "Value",
                    "Price must be a number");
        }
    }

}
