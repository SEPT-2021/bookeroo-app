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

    }

}
