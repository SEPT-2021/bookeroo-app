package com.bookeroo.microservice.login.web;

import com.bookeroo.microservice.login.model.NewsletterRecipient;
import com.bookeroo.microservice.login.service.NewsletterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/newsletter")
public class NewsletterController {

    private final NewsletterService newsletterService;

    @Autowired
    public NewsletterController(NewsletterService newsletterService) {
        this.newsletterService = newsletterService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody NewsletterRecipient newsletterRecipient) {
        newsletterService.sendMessage(newsletterRecipient.getEmail(), "Welcome to Bookeroo!",
                "Congratulations for signing up to our newsletter");
        return new ResponseEntity<>(newsletterService.saveRecipient(newsletterRecipient), HttpStatus.OK);
    }

}
