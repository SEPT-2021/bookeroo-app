package com.bookeroo.microservice.payment.validator;

import com.bookeroo.microservice.payment.model.ShippingAddress;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ShippingAddressValidator implements Validator {

    public static int STATE_LENGTH = 3;
    public static int POSTAL_CODE_LENGTH = 4;

    @Override
    public boolean supports(Class<?> aClass) {
        return ShippingAddress.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        ShippingAddress shippingAddress = (ShippingAddress) object;

        if (shippingAddress.getAddressLine1().isEmpty())
            errors.rejectValue("addressLine1", "Value", "Address line 1 must not be blank");

        if (!shippingAddress.getCity().chars().allMatch(Character::isAlphabetic))
            errors.rejectValue("city", "Value", "City name may only contain alphabets");

        if (!shippingAddress.getState().chars().allMatch(Character::isAlphabetic))
            errors.rejectValue("state", "Value", "State name may only contain alphabets");

        if (!shippingAddress.getPostalCode().chars().allMatch(Character::isDigit))
            errors.rejectValue("postalCode", "Value", "Postal code must only contain numerals");

        if (shippingAddress.getPostalCode().length() != POSTAL_CODE_LENGTH)
            errors.rejectValue("postalCode", "Length", String.format(
                    "Postal code must be of length %d", POSTAL_CODE_LENGTH
            ));

        if (shippingAddress.getState().length() != STATE_LENGTH)
            errors.rejectValue("postalCode", "Length", String.format(
                    "State must be of length %d", STATE_LENGTH
            ));
    }

}
