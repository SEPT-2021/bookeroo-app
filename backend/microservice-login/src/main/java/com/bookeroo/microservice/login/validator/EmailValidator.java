package com.bookeroo.microservice.login.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.Email;

public class EmailValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Email.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
    }

}
