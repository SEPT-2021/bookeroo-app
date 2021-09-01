package com.bookeroo.microservice.book.validator;




import com.bookeroo.microservice.book.model.Book;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class BookValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Book book = (Book) object;
        if ((book.getISBN()).length() >= 12)
            errors.rejectValue("ISBN", "Length", ("ISBN must be at least 13 characters"));
    }

}
