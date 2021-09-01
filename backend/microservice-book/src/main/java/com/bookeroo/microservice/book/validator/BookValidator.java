package com.bookeroo.microservice.book.validator;

import com.bookeroo.microservice.book.model.Book;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {

    public static final int MINIMUM_ISBN_LENGTH = 13;

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Book book = (Book) object;
        if ((book.getIsbn()).length() < MINIMUM_ISBN_LENGTH)
            errors.rejectValue("isbn", "Length", String.format("ISBN must be at least %d characters", MINIMUM_ISBN_LENGTH));
    }

}
