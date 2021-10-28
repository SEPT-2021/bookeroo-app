package com.bookeroo.microservice.seller.validator;

import com.bookeroo.microservice.seller.model.SellerDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Implementation of the {@link Validator} for the {@link SellerDetails} data model validation.
 */
@Component
public class SellerDetailsValidator implements Validator {

    public static final int ABN_LENGTH = 11;


    @Override
    public boolean supports(Class<?> aClass) {
        return SellerDetails.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        SellerDetails details = (SellerDetails) object;

        if (details.getAbn() != null && details.getAbn().length() != ABN_LENGTH)
            errors.rejectValue("abn", "Length",
                    String.format("ABN must be exactly %d digits", ABN_LENGTH));
    }

}
