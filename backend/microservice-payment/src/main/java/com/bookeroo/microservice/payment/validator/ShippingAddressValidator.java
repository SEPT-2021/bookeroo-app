package com.bookeroo.microservice.payment.validator;

import com.bookeroo.microservice.payment.model.ShippingAddress;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ShippingAddressValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ShippingAddress.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        ShippingAddress shippingAddress = (ShippingAddress) object;

        if (!shippingAddress.getCity().chars().allMatch(Character::isAlphabetic))
            errors.rejectValue("city", "Value", "City name may only contain alphabets");

        if (!shippingAddress.getState().chars().allMatch(Character::isAlphabetic))
            errors.rejectValue("state", "Value", "State name may only contain alphabets");

        if (!shippingAddress.getPostalCode().chars().allMatch(Character::isDigit))
            errors.rejectValue("postalCode", "Value", "Postal code must only contain numerals");
    }

}
