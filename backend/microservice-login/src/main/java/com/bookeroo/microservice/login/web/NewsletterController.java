package com.bookeroo.microservice.login.web;

import com.bookeroo.microservice.login.model.NewsletterRecipient;
import com.bookeroo.microservice.login.service.NewsletterService;
import com.bookeroo.microservice.login.service.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/newsletter")
public class NewsletterController {

    private final NewsletterService newsletterService;
    private final ValidationErrorService validationErrorService;

    @Autowired
    public NewsletterController(NewsletterService newsletterService, ValidationErrorService validationErrorService) {
        this.newsletterService = newsletterService;
        this.validationErrorService = validationErrorService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody NewsletterRecipient newsletterRecipient,
                                      BindingResult result) {
        ResponseEntity<?> errorMap = validationErrorService.mapValidationErrors(result);
        if (errorMap != null)
            return errorMap;

        newsletterService.sendMessage(
                newsletterRecipient.getEmail(),
                "Welcome to Bookeroo!",
                "Congratulations for signing up to our newsletter");

        return new ResponseEntity<>(newsletterService.saveRecipient(newsletterRecipient), HttpStatus.OK);
    }

}
