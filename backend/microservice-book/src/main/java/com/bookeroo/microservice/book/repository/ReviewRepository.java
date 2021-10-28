package com.bookeroo.microservice.book.repository;

import com.bookeroo.microservice.book.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select avg(r.rating) from Review r where r.book.id = ?1")
    float getAverageByBook_Id(long bookId);

    @Transactional
    void deleteAllByUser_Username(String username);

    @Transactional
    void deleteAllByBook_Id(long id);

}
