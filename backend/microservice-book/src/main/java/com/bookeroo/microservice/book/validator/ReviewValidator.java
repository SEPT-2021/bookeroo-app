package com.bookeroo.microservice.book.validator;

import com.bookeroo.microservice.book.model.Review;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.bookeroo.microservice.book.model.Review.RATING_VALUES;

/**
 * Implementation of the {@link Validator} for the {@link Review} data model validation.
 */
@Component
public class ReviewValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Review.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Review review = (Review) object;

        if (!RATING_VALUES.contains(review.getRating())) {
            errors.rejectValue("rating", "Value", "Rating can only be an integer from 1-5 inclusive");
        }

    }

}
