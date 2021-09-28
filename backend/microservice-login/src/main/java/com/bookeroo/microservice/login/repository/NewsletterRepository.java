package com.bookeroo.microservice.login.repository;

import com.bookeroo.microservice.login.model.NewsletterRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsletterRepository extends JpaRepository<NewsletterRecipient, Long> {
}
