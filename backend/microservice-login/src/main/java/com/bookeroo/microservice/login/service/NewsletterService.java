package com.bookeroo.microservice.login.service;

import com.bookeroo.microservice.login.model.NewsletterRecipient;
import com.bookeroo.microservice.login.repository.NewsletterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NewsletterService {

    private final JavaMailSender emailSender;
    private final NewsletterRepository newsletterRepository;

    @Autowired
    public NewsletterService(JavaMailSender emailSender, NewsletterRepository newsletterRepository) {
        this.emailSender = emailSender;
        this.newsletterRepository = newsletterRepository;
    }

    public NewsletterRecipient saveRecipient(NewsletterRecipient newsletterRecipient) {
        return newsletterRepository.save(newsletterRecipient);
    }

    public void sendMessage(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("noreply@bookeroo.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }

}
