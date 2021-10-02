package com.bookeroo.microservice.login.validator;

import com.bookeroo.microservice.login.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Implementation of the {@link Validator} for the {@link User} data model validation.
 */
@Component
public class UserValidator implements Validator {

    public static final int MINIMUM_PASSWORD_LENGTH = 6;
    public static int STATE_LENGTH = 3;
    public static int POSTAL_CODE_LENGTH = 4;

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

        if (user.getRoles().contains(User.UserRole.ADMIN.name()))
            errors.rejectValue("roles", "Value", "User role cannot be ADMIN");


        if (!user.getCity().chars().allMatch(Character::isAlphabetic))
            errors.rejectValue("shippingAddress.city", "Value", "City name may only contain alphabets");

        if (!user.getState().chars().allMatch(Character::isAlphabetic))
            errors.rejectValue("shippingAddress.state", "Value", "State name may only contain alphabets");

        if (!user.getPostalCode().chars().allMatch(Character::isDigit))
            errors.rejectValue("shippingAddress.postalCode", "Value", "Postal code must only contain numerals");

        if (user.getPostalCode().length() != POSTAL_CODE_LENGTH)
            errors.rejectValue("shippingAddress.postalCode", "Length", String.format(
                    "Postal code must be of length %d", POSTAL_CODE_LENGTH
            ));

        if (user.getState().length() != STATE_LENGTH)
            errors.rejectValue("shippingAddress.state", "Length", String.format(
                    "State must be of length %d", STATE_LENGTH
            ));
    }

}
