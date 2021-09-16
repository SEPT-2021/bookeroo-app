package com.bookeroo.microservice.login.validator;

import com.bookeroo.microservice.login.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    public static final int MINIMUM_PASSWORD_LENGTH = 6;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH)
            errors.rejectValue("password", "Length",
                    String.format("Password must be at least %d characters", MINIMUM_PASSWORD_LENGTH));
    }

}
